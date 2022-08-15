package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction;
    private final Session session = Util.hibernateGetSession();

    public void createUsersTable() {
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "create table if not exists " + table +
                            "(name text not null, " +
                            "lastName text not null, " +
                            "age tinyint not null, " +
                            "id serial primary key);").executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void dropUsersTable() {
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists " + table).executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public List<User> getAllUsers() {
        try {
            transaction = session.beginTransaction();
            List<User> result = session.createQuery("from " + table).list();
            transaction.commit();
            return result;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void cleanUsersTable() {
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table " + table).executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
