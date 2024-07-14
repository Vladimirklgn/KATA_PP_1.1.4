package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.exception.DaoException;
import org.hibernate.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user"
                    + "(ID BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "last_Name VARCHAR(50) NOT NULL,"
                    + "age TINYINT NOT NULL)").executeUpdate();
            tx.commit();
            System.out.println("Table created");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            tx.commit();
            System.out.println("Table dropped");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
            System.out.println("User " + name + " has been saved");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            System.out.println("User " + id + " has been removed");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
            query.from(User.class);
            tx = session.beginTransaction();
            List<User> list = session.createQuery(query).getResultList();
            tx.commit();
            return list;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user;").executeUpdate();
            tx.commit();
            System.out.println("Table cleaned");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }
}
