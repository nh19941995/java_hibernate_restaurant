package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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
            entityManager.persist(booking); // Đối tượng detached, chuyển về managed rồi lưu
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
            Booking booking = entityManager.find(Booking.class, bookingId);
            booking.getTable().getId();
            return booking;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public ArrayList<Booking> getByInfoId(int inforID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách Booking
            String queryStr = "SELECT t FROM Booking t WHERE t.info.id = :inforID";
            TypedQuery<Booking> query = entityManager.createQuery(queryStr, Booking.class);
            query.setParameter("inforID", inforID);

            ArrayList<Booking> bookings = new ArrayList<>(query.getResultList());
            for (Booking a : bookings) {
                if (a.getTable() != null) {
                    int id = a.getTable().getId();
                    int seatingCapacity = a.getTable().getSeatingCapacity();
                }
                if (a.getInfo() != null) {
                    LocalDateTime end = a.getInfo().getEnd();
                    LocalDateTime start = a.getInfo().getStart();
                }
                if (a.getTable() != null && a.getTable().getStatus() != null) {
                    String status = a.getTable().getStatus().getName();
                }
                if (a.getTable() != null && a.getTable().getType() != null) {
                    String types = a.getTable().getType().getName();
                }
                if (a.getInfo() != null && a.getInfo().getInfo() != null) {
                    String info = a.getInfo().getInfo();
                }
                if (a.getMenuName() != null ) {
                    String info = a.getMenuName().getName();
                }

            }
            return bookings;
        } catch (Exception e) {
            System.err.println("Error while fetching bookings: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean saveDetachedAsNew(Booking detachedBooking) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Booking newBooking = new Booking();
            newBooking.setFlag(detachedBooking.getFlag());
            newBooking.setInfo(detachedBooking.getInfo());
            newBooking.setTable(detachedBooking.getTable());
            newBooking.setMenuName(detachedBooking.getMenuName());
            newBooking.setId(null); // Set ID to null to make it a new object

            transaction.begin();
            entityManager.persist(newBooking); // Save as a new object
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




}
