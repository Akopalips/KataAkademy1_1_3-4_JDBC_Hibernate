package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    /* В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
 Service на этот раз использует реализацию dao через Hibernate
 Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.*/

    private Transaction transaction;
    private Session session;

    public void openCurrentSessionWithTransaction() {
        session = Util.HibernateGetSession();
        transaction = session.beginTransaction();
    }

    public void closeCurrentSessionWithTransaction() {
        transaction.commit();
    }

    @Override
    @Transactional
    public void createUsersTable() {
        openCurrentSessionWithTransaction();
        session.createSQLQuery(
                "create table if not exists " + table +
                        "(name text not null, " +
                        "lastName text not null, " +
                        "age tinyint not null, " +
                        "id serial primary key);").executeUpdate();
        closeCurrentSessionWithTransaction();
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        openCurrentSessionWithTransaction();
        session.createSQLQuery("drop table if exists testUserTable;").executeUpdate();
        closeCurrentSessionWithTransaction();
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        openCurrentSessionWithTransaction();
        session.save(new User(name, lastName, age));
        closeCurrentSessionWithTransaction();
    }

    @Override
    public void removeUserById(long id) {
        openCurrentSessionWithTransaction();
        session.delete(session.get(User.class, id));
        closeCurrentSessionWithTransaction();
    }

    public User getUserById(long id) {
        openCurrentSessionWithTransaction();
        User out = session.get(User.class, id);
        closeCurrentSessionWithTransaction();
        return out;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            openCurrentSessionWithTransaction();
            return session.createQuery("from testUserTable").list();
        } finally {
            closeCurrentSessionWithTransaction();
        }
    }

    @Override
    public void cleanUsersTable() {
        openCurrentSessionWithTransaction();
        session.createSQLQuery("truncate table testUserTable;").executeUpdate();
        closeCurrentSessionWithTransaction();
    }
}
