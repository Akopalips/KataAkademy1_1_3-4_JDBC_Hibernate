package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.EntityManager;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    EntityManager em = Util.JDBCGetTestConnection();

    public void createUsersTable() {
        try{
            em.getTransaction().begin();
            em.createNativeQuery("create table if not exists " + table +
                    "(name text not null, " +
                    "lastName text not null, " +
                    "age tinyint not null, " +
                    "id serial primary key)").executeUpdate();
            em.flush();
            em.getTransaction().commit();
        }
        finally {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
        //Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
    }

    public void dropUsersTable() {
        try{
            em.getTransaction().begin();
            em.createNativeQuery("drop table if exists " + table).executeUpdate();
            em.flush();
            em.getTransaction().commit();
        }
        finally {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
        //Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
    }

    public void saveUser(String name, String lastName, byte age) {
        try{
            em.getTransaction().begin();
            em.persist(new User(name, lastName, age));
            em.flush();
            em.getTransaction().commit();
        }
        finally {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
    }

    public void removeUserById(long id) {
        em.remove(em.find(User.class, id));
    }

    public List<User> getAllUsers() {
        return  em.createQuery("SELECT e FROM User e").getResultList();
    }

    public void cleanUsersTable() {
        try{
            em.getTransaction().begin();
            em.createNativeQuery("truncate table " + table).executeUpdate();
            em.flush();
            em.getTransaction().commit();
        }
        finally {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }//Операция атомарна. Откат невозможен: https://dev.mysql.com/doc/refman/8.0/en/implicit-commit.html
    }
}
