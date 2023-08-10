package dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Dish;
import model.DishType;
import utils.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

public class DishDAO implements DAOInterface<Dish,Integer>{
    private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public static DishDAO getInstance(){
        return new DishDAO();
    }

    public DishDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(Dish dish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(dish);
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
    public int update(Dish dish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(dish);
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
    public ArrayList<Dish> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM Dish d";
            ArrayList<Dish> dishes  =  new ArrayList<>(entityManager.createQuery(queryStr, Dish.class).getResultList());

            for (Dish dish : dishes) {
                String types = dish.getType().getName();
            }
            return dishes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Dish getById(int dishId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Dish dish = entityManager.find(Dish.class, dishId);
            dish.getType().getName();
            return dish;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
