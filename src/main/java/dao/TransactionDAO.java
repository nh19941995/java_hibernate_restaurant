package dao;

import controller.ControllerTime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.TableList;
import model.Transaction;
import utils.PersistenceManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TransactionDAO implements DAOInterface<Transaction, Integer>{
    private EntityManagerFactory entityManagerFactory;


    public static TransactionDAO getInstance(){
        return new TransactionDAO();
    }

    public TransactionDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }

    @Override
    public boolean insert(Transaction trans) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(trans);
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
    public int update(Transaction trans) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(trans);
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
    public ArrayList<Transaction> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM Transaction d";
            ArrayList<Transaction> transactions  =  new ArrayList<>(entityManager.createQuery(queryStr, Transaction.class).getResultList());

            for (Transaction a : transactions) {
                String comment = a.getComment();
                String type = a.getType().getType();
                Double quantity = a.getQuantity();
                LocalDateTime dateCreat = a.getDateCreat();
                LocalDateTime dateUpdate = a.getDateUpdate();
                String name = a.getPerson().getName();
                String lastName = a.getPerson().getLastName();
                String phone = a.getPerson().getPhone();
                int flag = a.getFlag();
            }
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
//
//    s.getId(),
//    s.getComment(),
//    s.getType().getType(),
//    s.getQuantity(),
//    ControllerTime.formatDateTime(1,s.getDateCreat()),
//    ControllerTime.formatDateTime(2,s.getDateCreat()),
//    s.getPerson().getName()+" "+s.getPerson().getLastName(),
//    s.getPerson().getPhone(),
//    s.getFlag(),

    @Override
    public Transaction getById(int transactionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Transaction.class, transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
