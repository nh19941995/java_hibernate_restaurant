package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.BookingsInfo;
import model.ClientPaymentInfo;
import utils.PersistenceManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientPaymentInfoDAO implements DAOInterface<ClientPaymentInfo,Integer>{
    private EntityManagerFactory entityManagerFactory;
    public static ClientPaymentInfoDAO getInstance(){
        return new ClientPaymentInfoDAO();
    }
    public ClientPaymentInfoDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }

    @Override
    public boolean insert(ClientPaymentInfo clientPaymentInfo) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(clientPaymentInfo);
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
    public int update(ClientPaymentInfo clientPaymentInfo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(clientPaymentInfo);
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
    public ArrayList<ClientPaymentInfo> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM ClientPaymentInfo d";
            ArrayList<ClientPaymentInfo> info  =  new ArrayList<>(entityManager.createQuery(queryStr, ClientPaymentInfo.class).getResultList());

            for (ClientPaymentInfo s : info) {
                int BookingInfoId = s.getBookingInfo().getId();
                int TransactionId = s.getTransaction().getId();
            }
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public ClientPaymentInfo getById(int clientPaymentInfoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            ClientPaymentInfo clientPaymentInfo = entityManager.find(ClientPaymentInfo.class, clientPaymentInfoId);
//            String name = bookingsInfo.getPerson().getName();
//            String lastName = bookingsInfo.getPerson().getLastName();
//            String phone = bookingsInfo.getPerson().getPhone();
            return clientPaymentInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
