package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    EntityManager em = Util.JDBCGetTestConnection();

    public void createUsersTable() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createNativeQuery("create table if not exists " + table +
                    "(name text not null, " +
                    "lastName text not null, " +
                    "age tinyint not null, " +
                    "id serial primary key)").executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        //Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
    }

    public void dropUsersTable() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createNativeQuery("drop table if exists " + table).executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        //Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
    }

    public void saveUser(String name, String lastName, byte age) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(new User(name, lastName, age));
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void removeUserById(long id) {
        User target = em.find(User.class, id);
        if (target != null) {
            try {
                em.getTransaction().begin();
                em.remove(target);
                em.getTransaction().commit();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No User with id = " + id);
        }
    }

    public List<User> getAllUsers() {
        try {
            return em.createQuery("SELECT e FROM User e").getResultList();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public void cleanUsersTable() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createNativeQuery("truncate table " + table).executeUpdate();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }//Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
}
