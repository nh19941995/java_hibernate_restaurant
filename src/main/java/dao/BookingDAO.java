package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.*;
import utils.PersistenceManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class BookingDAO implements DAOInterface<Booking,Integer>{
    private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public static BookingDAO getInstance(){
        return new BookingDAO();
    }

    public BookingDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(Booking booking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(booking);
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
    public int update(Booking booking) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(booking);
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
    public ArrayList<Booking> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT t FROM Booking t JOIN FETCH t.table b JOIN FETCH t.menuName c WHERE t.flag > 0 AND c.flag > 0";

            ArrayList<Booking> bookings  =  new ArrayList<>(entityManager.createQuery(queryStr, Booking.class).getResultList());
            for (Booking a : bookings) {
                String types = a.getTable().getType().getName();
                LocalDateTime end = a.getInfo().getEnd();
                LocalDateTime start = a.getInfo().getStart();
                String status = a.getTable().getStatus().getName();

            }

//            a.stream().forEach(s-> System.out.println(s.toString()) );
            return bookings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }

    }

    @Override
    public Booking getById(int bookingId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Booking.class, bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }


}
