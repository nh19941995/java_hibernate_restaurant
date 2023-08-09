package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Permission;
import model.Person;
import utils.PersistenceManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PersonDAO implements DAOInterface<Person,Integer>{

    private EntityManagerFactory entityManagerFactory;

    public static PersonDAO getInstance(){
        return new PersonDAO();
    }
    public PersonDAO() {
        entityManagerFactory = PersistenceManager.getEntityManagerFactory();
    }
    @Override
    public boolean insert(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(person);
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
    public int update(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(person);
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
    public ArrayList<Person> getAll() {
        //        Với truy vấn đọc (SELECT), bạn không cần bắt EntityTransaction.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Sử dụng JPQL (Java Persistence Query Language) để truy vấn danh sách DishType
            String queryStr = "SELECT d FROM Person d";
            ArrayList<Person> people  =  new ArrayList<>(entityManager.createQuery(queryStr, Person.class).getResultList());

            for (Person a : people) {
                String name = a.getName();
                String lastName = a.getLastName();
                String phone = a.getPhone();
                String email = a.getEmail();
                String address = a.getAddress();
                LocalDate birth = a.getDateOfBirth();
                String permission = a.getPermission().getPermissionName();
            }
            return people;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Person getById(int t) {
        return null;
    }
}
