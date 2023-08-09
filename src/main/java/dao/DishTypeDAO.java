package dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Dish;
import model.DishType;
import model.TransactionsType;
import utils.PersistenceManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DishTypeDAO implements DAOInterface<DishType,Integer>{
    private EntityManagerFactory entityManagerFactory;
    public static DishTypeDAO getInstance(){
        return new DishTypeDAO();
    }
    public DishTypeDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(DishType dishType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(dishType);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public int update(DishType type) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(type);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public ArrayList<DishType> getAll() {
//        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM DishType d";
            ArrayList<DishType> a  =  new ArrayList<>(entityManager.createQuery(queryStr, DishType.class).getResultList());
//            a.stream().forEach(s-> System.out.println(s.toString()) );
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public DishType getById(int dishTypeId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(DishType.class, dishTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public DishType getByString(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            Query query = entityManager.createQuery("SELECT d FROM DishType d WHERE d.name = :type");
            query.setParameter("type", name);
            DishType typex = (DishType) query.getSingleResult();
            // Merge the entity back into the session
            DishType mergedTypex = entityManager.merge(typex);

            return mergedTypex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }


}
