package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    /* В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
 Service на этот раз использует реализацию dao через Hibernate
 Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.*/

    private Session session;
    private Transaction transaction;


    public void createUsersTable() {
        session = Util.HibernateGetSession();
        transaction = session.beginTransaction();
        session.createSQLQuery(
                "create table if not exists " + table +
                        "(name text not null, " +
                        "lastName text not null, " +
                        "age tinyint not null, " +
                        "id serial primary key)").executeUpdate();
        transaction.commit();
    }

    public void dropUsersTable() {
        session = Util.HibernateGetSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("drop table if exists testUserTable").executeUpdate();
        transaction.commit();
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.HibernateGetSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } finally {
                transaction.rollback();
        }
    }

    public void removeUserById(long id) {
        try {
            session = Util.HibernateGetSession();
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } finally {
            transaction.rollback();
        }
    }

    public List<User> getAllUsers() {
        session = Util.HibernateGetSession();
        transaction = session.beginTransaction();
        List <User> result = session.createQuery("from testUserTable", User.class).list();
        transaction.commit();
        return result;
    }

    public void cleanUsersTable() {
        session = Util.HibernateGetSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("truncate table testUserTable").executeUpdate();
        transaction.commit();
    }
}
