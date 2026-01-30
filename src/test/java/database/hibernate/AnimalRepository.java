package database.hibernate;

import database.hibernate.entities.Animal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Repository для работы с Animal через Hibernate
 */
public class AnimalRepository {
    /**
     * CREATE - Сохранить животное
     */
    public static void save(Animal animal) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(animal); // или session.save(animal)
            transaction.commit();
            System.out.println("Animal saved: " + animal.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to save animal: " + e.getMessage());
            throw e;
        }
    }

    /**
     * READ - Получить животное по ID
     */
    public static Animal findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Animal.class, id);
        } catch (Exception e) {
            System.err.println("Failed to find animal: " + e.getMessage());
            return null;
        }
    }

    /**
     * READ - Получить всех животных
     */
    public static List<Animal> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL (Hibernate Query Language) - похож на SQL, но для объектов
            Query<Animal> query = session.createQuery("FROM Animal", Animal.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Failed to find animal: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * READ - Найти животных по имени
     */
    public static List<Animal> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL (Hibernate Query Language) - похож на SQL, но для объектов
            Query<Animal> query = session.createQuery(
                    "FROM Animal WHERE name = :name",
                    Animal.class);
            query.setParameter("name", name);
            return query.list();
        }
    }

    /**
     * READ - Найти животных по возрасту (больше)
     */
    public static List<Animal> findByAgeGreaterThan(Integer age) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL (Hibernate Query Language) - похож на SQL, но для объектов
            Query<Animal> query = session.createQuery(
                    "FROM Animal WHERE age > :age ORDER BY age",
                    Animal.class);
            query.setParameter("age", age);
            return query.list();
        }
    }

    /**
     * UPDATE - Обновить животное
     */
    public static void update(Animal animal) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(animal); // или session.update(animal)
            transaction.commit();
            System.out.println("Animal updated: " + animal.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to update animal: " + e.getMessage());
            throw e;
        }
    }

    /**
     * DELETE - Удалить животное по ID
     */
    public static void deleteById(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Animal animal = session.get(Animal.class, id);
            if (animal != null) {
                session.remove(animal);// или session.delete(animal)
                transaction.commit();
                System.out.println("Animal deleted: " + id);
            } else {
                System.out.println("⚠️ Animal not found: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("❌ Failed to delete animal: " + e.getMessage());
            throw e;
        }
    }

    /**
     * DELETE - Удалить животное
     */
    public static void delete(Animal animal) {
        deleteById(animal.getId());
    }

    /**
     * COUNT - Подсчитать всех животных
     */
    public static Long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM Animal a",
                    Long.class
            );
            return query.uniqueResult();
        }
    }
}
