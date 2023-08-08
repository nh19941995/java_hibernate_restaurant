package utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManager {
    private static EntityManagerFactory entityManagerFactory;

    private PersistenceManager() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}

