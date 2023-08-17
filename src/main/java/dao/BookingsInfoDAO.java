package dao;

import controller.ControllerTime;
import jakarta.persistence.*;
import model.BookingsInfo;
import model.Dish;
import utils.PersistenceManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingsInfoDAO implements DAOInterface<BookingsInfo,Integer>{
    private EntityManagerFactory entityManagerFactory;


    public static BookingsInfoDAO getInstance(){
        return new BookingsInfoDAO();
    }

    public BookingsInfoDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }

    @Override
    public boolean insert(BookingsInfo bookingsInfo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(bookingsInfo);
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
    public int update(BookingsInfo bookingsInfo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(bookingsInfo);
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
    public ArrayList<BookingsInfo> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM BookingsInfo d";
            ArrayList<BookingsInfo> info  =  new ArrayList<>(entityManager.createQuery(queryStr, BookingsInfo.class).getResultList());

            for (BookingsInfo s : info) {
                String name = s.getPerson().getName();
                String lastName = s.getPerson().getLastName();
                String fname = s.getPerson().getName();
                String phone = s.getPerson().getPhone();
                String info1 = s.getInfo();
                Double deposit = s.getDeposit();
                LocalDateTime dateCreat = s.getDateCreat();
                int flag = s.getFlag();
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
    public BookingsInfo getById(int bookingsInfoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(BookingsInfo.class, bookingsInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Double getTotalPriceByInfoBookingID(int idInforBooking){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryStr = "SELECT SUM(n.quantity * n.unitPrice)\n" +
                    "FROM BookingsInfo bi\n" +
                    "JOIN bi.bookings b\n" +
                    "JOIN b.menuName m\n" +
                    "JOIN m.menus n\n" +

                    "WHERE bi.id = :infoId\n";
            TypedQuery<Double> query = entityManager.createQuery(queryStr, Double.class);
            query.setParameter("infoId", idInforBooking);
            Double totalPrice = query.getSingleResult();
            return totalPrice != null ? totalPrice : 0.0; // Trả về 0.0 thay vì 0 nếu không có kết quả
        } catch (NoResultException e) {
            return 0.0; // Trả về 0.0 nếu không có kết quả
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            entityManager.close();
        }
    }

    public static void main(String[] args) {
//        Double bill = BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(bookingsInfo.getId());
        Double n    = BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(31);
        System.out.println(n);
    }
}
