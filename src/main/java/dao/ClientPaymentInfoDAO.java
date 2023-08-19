package dao;

import jakarta.persistence.*;
import model.BookingsInfo;
import model.ClientPaymentInfo;
import utils.PersistenceManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientPaymentInfoDAO implements DAOInterface<ClientPaymentInfo,Integer>{
    private static EntityManagerFactory entityManagerFactory;
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

    public static Double getTotalQuantityByBookingInfoID(int bookingInfoID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryStr = "SELECT SUM(t.quantity) FROM Transaction t " +
                    "JOIN ClientPaymentInfo cpi ON t.id = cpi.transaction.id " +
                    "WHERE cpi.bookingInfo.id = :bookingInfoID";
            TypedQuery<Double> query = entityManager.createQuery(queryStr, Double.class);
            query.setParameter("bookingInfoID", bookingInfoID);

            Double totalQuantity = query.getSingleResult();
            return totalQuantity != null ? totalQuantity : 0.0; // Trả về 0.0 thay vì 0 nếu không có kết quả
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            entityManager.close();
        }
    }


    public static ClientPaymentInfo getClientPaymentInfo(int bookingInfoID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryStr = "SELECT c FROM ClientPaymentInfo c " +
                    "WHERE c.bookingInfo.id = :bookingInfoID AND c.flag = 1";

            TypedQuery<ClientPaymentInfo> query = entityManager.createQuery(queryStr, ClientPaymentInfo.class);
            query.setParameter("bookingInfoID", bookingInfoID);

            return query.setMaxResults(1).getResultList().stream().findFirst().orElse(null);
        } finally {
            entityManager.close();
        }
    }


}
