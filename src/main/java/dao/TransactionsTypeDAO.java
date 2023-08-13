package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.TableType;
import model.TransactionsType;
import utils.PersistenceManager;

import java.util.ArrayList;

public class TransactionsTypeDAO implements DAOInterface<TransactionsType,Integer>{
    private EntityManagerFactory entityManagerFactory;


    public static TransactionsTypeDAO getInstance(){
        return new TransactionsTypeDAO();
    }

    public TransactionsTypeDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }

    @Override
    public boolean insert(TransactionsType transType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(transType);
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
    public int update(TransactionsType transType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(transType);
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
    public ArrayList<TransactionsType> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM TransactionsType d";
            ArrayList<TransactionsType> a  =  new ArrayList<>(entityManager.createQuery(queryStr, TransactionsType.class).getResultList());
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public TransactionsType getById(int transTypeId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(TransactionsType.class, transTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public TransactionsType getByName(String typeName){
        TransactionsType type = TransactionsTypeDAO.getInstance().getAll().stream()
                .filter(s -> s.getType().equals(typeName))
                .findFirst()
                .orElse(null);
        return type;
    }

}
