package database.jdbc;

import api.tests.base.BaseApiTest;
import database.DatabaseUtils;
import database.jdbc.models.Animal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ZooJdbcTests extends BaseApiTest {

    @BeforeAll
    static void init() {
        DatabaseUtils.createData();
        System.out.println("Database initialized");
    }

    @AfterAll
    static void tearDown() {
        DatabaseConnection.closeConnection();
        System.out.println("Database connection closed");
    }

    /**
     * Тест 1: Создание нового животного
     */
    @Test
    void testCreateAnimal() {
        // Создаём животное с уникальным ID
        Animal newAnimal = Animal.builder()
                .id(99)
                .name("Тестовый")
                .age(1)
                .type(1)
                .sex(1)
                .place(1)
                .build();

        // Создаём животное
        CRUDUtils.createAnimal(newAnimal);

        // Проверяем, что животное создано
        Animal found = CRUDUtils.getAnimalById(99);

        assertNotNull(found, "Животное должно быть найдено в БД");
        assertEquals("Тестовый", found.getName(), "Имя должно совпадать");
    }

    /**
     * Тест 2: Обновление существующего животного
     */
    @Test
    void updateAnimal() {
        Animal newAnimal = Animal.builder()
                .id(100)
                .name("Борис")
                .age(2)
                .type(2)
                .sex(1)
                .place(2)
                .build();

        // Создаём животное
        CRUDUtils.createAnimal(newAnimal);

        // Получаем животное из БД
        Animal animal = CRUDUtils.getAnimalById(100);
        assertNotNull(animal,"Животное должно существовать перед обновлением");

        //Меняем данные
        animal.setName("Жанна");
        animal.setSex(2);

        //Сохраняем изменения в БД
        CRUDUtils.updateAnimal(animal);

        // Проверяем, что изменения сохранились
        Animal updated = CRUDUtils.getAnimalById(100);

        assertNotNull(updated, "Обновлённое животное должно существовать");
        assertEquals("Жанна", updated.getName(), "Имя должно обновиться");
        assertEquals(2, updated.getSex(), "Пол должен обновиться");
    }

    /**
     * Тест 3: Чтение всех животных
     */
    @Test
    void testGetAllAnimals() {
        var animals = CRUDUtils.getAllAnimals();//→ Эквивалентно: List<Animal> animals = ...(Компилятор сам определяет тип)

        assertNotNull(animals, "Список не должен быть null");
        assertTrue(animals.size() >= 10, "В БД должно быть минимум 10 животных (из createData)");

        System.out.println("Всего животных в БД: " + animals.size());

        //Вывод имени каждого животного
        animals.forEach(a -> System.out.println("  - " + a.getName()));
        /*
        Аналог
        // С циклом for (классический стиль)
             for (Animal a : animals) {
                System.out.println("  - " + a.getName());
             }
        // С циклом for по индексу (самый старый стиль)
            for (int i = 0; i < animals.size(); i++) {
               Animal a = animals.get(i);
               System.out.println("  - " + a.getName());
           }
         */
    }

    /**
     * Тест 4: Удаление животного
     */
    @Test
    void testDeleteAnimal() {
        // Создаём животное для удаления
        Animal toDelete = Animal.builder()
                .id(101)  // Уникальный ID
                .name("Для удаления")
                .age(1)
                .type(1)
                .sex(1)
                .place(1)
                .build();

        CRUDUtils.createAnimal(toDelete);
        System.out.println("Создано животное для удаления: id=101");

        // Проверяем, что животное существует
        Animal exists = CRUDUtils.getAnimalById(101);
        assertNotNull(exists, "Животное должно существовать перед удалением");

        // Удаляем животное
        CRUDUtils.deleteAnimal(101);
        System.out.println("Животное удалено");

        // Проверяем, что животное удалено
        Animal deleted = CRUDUtils.getAnimalById(101);
        assertNull(deleted, "Животное должно быть удалено из БД");

        System.out.println("Проверено удаление: животное отсутствует");
    }

    /**
     * Тест 5: Попытка обновить с неправильным внешним ключом (ожидаем ошибку)
     */
    @Test
    void testUpdateWithInvalidForeignKey() {
        // Создаём животное
        Animal animal = Animal.builder()
                .id(102)
                .name("Тест FK")
                .age(1)
                .type(1)
                .sex(1)
                .place(1)
                .build();

        CRUDUtils.createAnimal(animal);

        // Получаем из БД
        Animal animalFromDb = CRUDUtils.getAnimalById(102);

        // Пытаемся установить несуществующий sex=6
        animalFromDb.setSex(6);  // Такого пола НЕТ в таблице sex!

        // Ожидаем исключение
        assertThrows(
                Exception.class,
                () -> CRUDUtils.updateAnimal(animalFromDb),
                "Должно быть исключение при нарушении Foreign Key"
        );

        System.out.println("Foreign Key constraint работает правильно");
    }

    /**
     * Тест 6: Попытка создать дубликат ID (ожидаем ошибку)
     */
    @Test
    void testCreateDuplicateId() {
        // Создаём первое животное
        Animal animal1 = Animal.builder()
                .id(103)
                .name("Первое")
                .age(1)
                .type(1)
                .sex(1)
                .place(1)
                .build();

        CRUDUtils.createAnimal(animal1);
        System.out.println("Создано первое животное с id=103");

        // Пытаемся создать второе с тем же ID
        Animal animal2 = Animal.builder()
                .id(103)  // ← Дубликат!
                .name("Второе")
                .age(2)
                .type(2)
                .sex(2)
                .place(2)
                .build();

        // Ожидаем исключение
        assertThrows(
                Exception.class,
                () -> CRUDUtils.createAnimal(animal2),
                "Должно быть исключение при дублировании Primary Key"
        );

        System.out.println("Primary Key constraint работает правильно");
    }

    /**
     * Тест 7: Подсчёт животных
     */
    @Test
    void testAnimalCount() {
        int count = CRUDUtils.getAnimalCount();

        assertTrue(count >= 10, "Должно быть минимум 10 животных");
        System.out.println("Подсчёт животных: " + count);
    }
}
