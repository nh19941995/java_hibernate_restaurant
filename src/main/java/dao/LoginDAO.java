package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.DishType;
import model.Person;
import utils.PersistenceManager;

public class LoginDAO {
    private EntityManagerFactory entityManagerFactory;
    public static LoginDAO getInstance(){
        return new LoginDAO();
    }
    public LoginDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }


    public boolean checkLogin(String password, String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            Query query = entityManager.createQuery("SELECT d FROM Person d WHERE d.password = :password AND  d.username =:username");
            query.setParameter("password", password);
            query.setParameter("username", username);
            Person person = (Person) query.getSingleResult();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }
}
