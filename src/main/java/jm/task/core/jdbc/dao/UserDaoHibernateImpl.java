package jm.task.core.jdbc.dao;
import exception.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user"
                    + "(ID BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "last_Name VARCHAR(50) NOT NULL,"
                    + "age TINYINT NOT NULL)");
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DaoException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            tx.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw new DaoException(e);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DaoException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) getSessionFactory().openSession();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession();) {
            tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user;").executeUpdate();
            tx.commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(e);
        }
    }
}
