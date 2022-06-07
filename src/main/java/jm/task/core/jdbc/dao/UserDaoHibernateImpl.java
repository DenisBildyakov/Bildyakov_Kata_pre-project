package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory SESSIONFACTORY;

    public UserDaoHibernateImpl() {
        SESSIONFACTORY = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users"
                    + " (id BIGINT AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, "
                    + "age TINYINT NOT NULL, PRIMARY KEY (id));");
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS Users;");
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(new User(name, lastName, age));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.delete(session.get(User.class, id));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                list = session.createQuery("SELECT u FROM User u", User.class).getResultList();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = SESSIONFACTORY.openSession();) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Query query = session.createSQLQuery("TRUNCATE TABLE Users;");
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }
}
