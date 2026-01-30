package database.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


/**
 * Утилита для управления Hibernate SessionFactory
 * SessionFactory - это фабрика Session (аналог Connection в JDBC)
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    /**
     * Создаёт и возвращает SessionFactory (Singleton)
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // 1. Загружаем свойства из hibernate.cfg.xml
                Configuration configuration = new Configuration().configure();
                Properties settings = configuration.getProperties();

                // 2. Создаём ServiceRegistry
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                // 3. Используем MetadataSources для регистрации entity
                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Animal.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Places.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Workman.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Zoo.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Sex.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Type.class);
                metadataSources.addAnnotatedClass(database.hibernate.entities.Position.class);

                // 4. Строим Metadata и SessionFactory
                Metadata metadata = metadataSources.getMetadataBuilder()
                        .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                        .build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

                System.out.println("✅ Hibernate SessionFactory created successfully");

            } catch (Exception e) {
                System.err.println("❌ Failed to create SessionFactory");
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    /**
     * Закрывает SessionFactory
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory closed");
        }
    }

    /**
     * Получить информацию о SessionFactory
     */
    public static String getSessionFactoryInfo() {
        if (sessionFactory == null) {
            return "SessionFactory not initialized";
        }
        return String.format(
                "SessionFactory: open=%b, entities=%d",
                !sessionFactory.isClosed(),
                sessionFactory.getMetamodel().getEntities().size()
        );
    }
}
