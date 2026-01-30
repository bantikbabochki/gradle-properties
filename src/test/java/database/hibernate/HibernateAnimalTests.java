package database.hibernate;

import database.hibernate.entities.Animal;
import database.hibernate.entities.Places;
import database.hibernate.entities.Sex;
import database.hibernate.entities.Type;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // упорядочивай тесты
// по аннотации @Order, которую вы ставите над каждым методом.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
PER_METHOD (по умолчанию):
→ Новый экземпляр класса создаётся перед каждым тестовым методом.
→ Это обеспечивает полную изоляцию между тестами.
PER_CLASS:
→ Один экземпляр класса создаётся на весь набор тестов.
→ Все тестовые методы вызываются на одном и том же объекте.
 */
public class HibernateAnimalTests {
    @BeforeAll
    void init() {
        // Hibernate создаст таблицы автоматически (hbm2ddl.auto=create)
        // Создаём справочные данные
        createReferenceData();
        System.out.println("✅ Test environment initialized");
    }

    @AfterAll
    void tearDown() {
        HibernateUtil.shutdown();
        System.out.println("✅ Hibernate shut down");
    }

    @BeforeEach
    void setUp() {
        // Очистка тестовых данных
        cleanTestData();
    }

    /**
     * Создаём справочные данные (Sex, Type, Places)
     */
    private void createReferenceData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Sex
        session.persist(Sex.builder().id(1).name("Мужской").build());
        session.persist(Sex.builder().id(2).name("Женский").build());

        // Type
        session.persist(Type.builder().id(1).name("Кошка").build());
        session.persist(Type.builder().id(2).name("Собака").build());

        // Places
        session.persist(Places.builder().id(1).row(1).placeNum(100).name("Загон 1").build());
        session.persist(Places.builder().id(2).row(2).placeNum(200).name("Загон 2").build());

        tx.commit();
        session.close();
    }

    private void cleanTestData() {
        // Удаляем тестовых животных
        try {
            AnimalRepository.deleteById(99);
            AnimalRepository.deleteById(100);
        } catch (Exception e) {
            // Игнорируем
        }
    }

    @Test
    @Order(1)
    void testCreateAnimal() {
        // Создаём животное
        Animal animal = Animal.builder()
                .id(99)
                .name("Тестовый кот")
                .age(3)
                .build();

        // Устанавливаем связи
        animal.setType(Type.builder().id(1).build());  // Кошка
        animal.setSex(Sex.builder().id(1).build());    // Мужской
        animal.setPlace(Places.builder().id(1).build()); // Загон 1

        // Сохраняем
        AnimalRepository.save(animal);

        // Проверяем
        Animal found = AnimalRepository.findById(99);
        assertNotNull(found);
        assertEquals("Тестовый кот", found.getName());
        assertEquals(3, found.getAge());

        // Проверяем связи
        assertNotNull(found.getType());
        assertEquals("Кошка", found.getType().getName());

        System.out.println("✅ Test passed: createAnimal");
    }

    @Test
    @Order(2)
    void testFindAll() {
        // Создаём несколько животных
        Animal cat = Animal.builder().id(100).name("Кот").age(2).build();
        cat.setType(Type.builder().id(1).build());
        cat.setSex(Sex.builder().id(1).build());
        cat.setPlace(Places.builder().id(1).build());
        AnimalRepository.save(cat);

        Animal dog = Animal.builder().id(101).name("Пёс").age(5).build();
        dog.setType(Type.builder().id(2).build());
        dog.setSex(Sex.builder().id(1).build());
        dog.setPlace(Places.builder().id(2).build());
        AnimalRepository.save(dog);

        // Получаем всех
        List<Animal> animals = AnimalRepository.findAll();

        assertTrue(animals.size() >= 2);
        System.out.println("✅ Test passed: findAll (" + animals.size() + " animals)");
    }

    @Test
    @Order(3)
    void testUpdate() {
        // Создаём
        Animal animal = Animal.builder().id(99).name("Старое имя").age(1).build();
        animal.setType(Type.builder().id(1).build());
        animal.setSex(Sex.builder().id(1).build());
        animal.setPlace(Places.builder().id(1).build());
        AnimalRepository.save(animal);

        // Обновляем
        Animal found = AnimalRepository.findById(99);
        found.setName("Новое имя");
        found.setAge(5);
        AnimalRepository.update(found);

        // Проверяем
        Animal updated = AnimalRepository.findById(99);
        assertEquals("Новое имя", updated.getName());
        assertEquals(5, updated.getAge());

        System.out.println("✅ Test passed: update");
    }

    @Test
    @Order(4)
    void testDelete() {
        // Создаём
        Animal animal = Animal.builder().id(99).name("Для удаления").age(1).build();
        animal.setType(Type.builder().id(1).build());
        animal.setSex(Sex.builder().id(1).build());
        animal.setPlace(Places.builder().id(1).build());
        AnimalRepository.save(animal);

        // Проверяем существование
        assertNotNull(AnimalRepository.findById(99));

        // Удаляем
        AnimalRepository.deleteById(99);

        // Проверяем удаление
        assertNull(AnimalRepository.findById(99));

        System.out.println("✅ Test passed: delete");
    }

    @Test
    @Order(5)
    void testFindByName() {
        // Создаём животных
        Animal cat1 = Animal.builder().id(100).name("Мурка").age(2).build();
        cat1.setType(Type.builder().id(1).build());
        cat1.setSex(Sex.builder().id(2).build());
        cat1.setPlace(Places.builder().id(1).build());
        AnimalRepository.save(cat1);

        Animal cat2 = Animal.builder().id(121).name("Мурка").age(3).build();
        cat2.setType(Type.builder().id(1).build());
        cat2.setSex(Sex.builder().id(2).build());
        cat2.setPlace(Places.builder().id(2).build());
        AnimalRepository.save(cat2);

        // Ищем по имени
        List<Animal> murkas = AnimalRepository.findByName("Мурка");

        assertEquals(2, murkas.size());
        assertTrue(murkas.stream().allMatch(a -> a.getName().equals("Мурка")));

        System.out.println("✅ Test passed: findByName");
    }
}
