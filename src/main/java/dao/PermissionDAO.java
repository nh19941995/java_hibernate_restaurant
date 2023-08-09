package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Permission;
import model.Person;
import model.TableList;
import utils.PersistenceManager;

import java.util.ArrayList;

public class PermissionDAO implements DAOInterface<Permission, Integer>{
    private EntityManagerFactory entityManagerFactory;

    public static PermissionDAO getInstance(){
        return new PermissionDAO();
    }
    public PermissionDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(Permission permission) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(permission);
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
    public int update(Permission permission) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(permission);
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
    public ArrayList<Permission> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM Permission d";
            ArrayList<Permission> permissions  =  new ArrayList<>(entityManager.createQuery(queryStr, Permission.class).getResultList());

            for (Permission a : permissions) {
                String types = a.getPermissionName();
            }
            return permissions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Permission getById(int permissionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Permission permission = entityManager.find(Permission.class, permissionId);
            return permission;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
