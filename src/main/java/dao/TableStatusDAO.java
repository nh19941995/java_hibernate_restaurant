package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.DishType;
import model.TableList;
import model.TableStatus;
import utils.PersistenceManager;

import java.util.ArrayList;

public class TableStatusDAO implements DAOInterface<TableStatus, Integer>{
    private EntityManagerFactory entityManagerFactory;
    public static TableStatusDAO getInstance(){
        return new TableStatusDAO();
    }
    public TableStatusDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }

    @Override
    public boolean insert(TableStatus tableStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(tableStatus);
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
    public int update(TableStatus tableStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(tableStatus);
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
    public ArrayList<TableStatus> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM TableStatus d";
            ArrayList<TableStatus> tableStatuses  =  new ArrayList<>(entityManager.createQuery(queryStr, TableStatus.class).getResultList());

            for (TableStatus a : tableStatuses) {
                String types = a.getName();
            }
            return tableStatuses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public TableStatus getById(int tableStatusId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TableStatus tableStatus = entityManager.find(TableStatus.class, tableStatusId);
            return tableStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public TableStatus getByStringName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            Query query = entityManager.createQuery("SELECT d FROM TableStatus d WHERE d.name = :name");
            query.setParameter("name", name);
            TableStatus status = (TableStatus) query.getSingleResult();

            // Merge the entity back into the session
            TableStatus mergedTypex = entityManager.merge(status);
            return mergedTypex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
