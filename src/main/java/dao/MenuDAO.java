package dao;

import jakarta.persistence.*;
import model.Dish;
import model.DishType;
import model.Menu;
import utils.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

public class MenuDAO implements DAOInterface<Menu,Integer>{
    private EntityManagerFactory entityManagerFactory;
    public static MenuDAO getInstance(){
        return new MenuDAO();
    }
    public MenuDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(Menu menu) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(menu);
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
    public int update(Menu menu) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(menu);
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
    public ArrayList<Menu> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM Menu d";
            ArrayList<Menu> a  =  new ArrayList<>(entityManager.createQuery(queryStr, Menu.class).getResultList());
//            a.stream().forEach(s-> System.out.println(s.toString()) );
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Menu getById(int menuId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Menu.class, menuId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Double getTotalPriceByMenuNameID(Integer menuNameID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryStr = "SELECT SUM(m.quantity * m.unitPrice) FROM Menu m WHERE m.menuName.id = :id";
            TypedQuery<Double> query = entityManager.createQuery(queryStr, Double.class);
            query.setParameter("id", menuNameID);
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
    public List<Menu> getMenuByMenuNameID(int menuNameId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryStr = "SELECT m FROM Menu m WHERE m.menuName.id = :menuNameId";
            TypedQuery<Menu> query = entityManager.createQuery(queryStr, Menu.class);
            query.setParameter("menuNameId", menuNameId);
            List<Menu> result = query.getResultList();
            for (Menu dish : result) {
                String types = dish.getDish().getDishName();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean deleteById(int menuId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Menu menuToDelete = entityManager.find(Menu.class, menuId);
            if (menuToDelete != null) {
                transaction.begin();
                entityManager.remove(menuToDelete);
                transaction.commit();
                return true;
            }
            return false; // Trả về false nếu không tìm thấy menu theo ID
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
