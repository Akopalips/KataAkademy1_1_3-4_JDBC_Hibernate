package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {

    private final static Connection connection = Util.JDBCGetTestConnection();

    public void createUsersTable() {
        try (Statement createTable = connection.createStatement()) {
            connection.setAutoCommit(false);//неявное начало транзакции == start transaction
            createTable.executeUpdate("create table if not exists " + table +
                            "(name text not null, " +
                            "lastName text not null, " +
                            "age tinyint not null, " +
                            "id serial primary key)");
            connection.commit();//явное окончание транзакции
        } catch (SQLException e) {
            System.out.println("При создании таблицы возникла ошибка: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackE) {
                System.out.println("При откате изменений возникла ошибка: " + rollbackE);
            }
        } finally {
            try{
                connection.setAutoCommit(true);//неявное окончание транзакции == commit
            }catch (SQLException e){
                System.out.println("Транзакция не была завершена.");
            }
        }
    }

    public void dropUsersTable() {
        try (Statement dropTable = connection.createStatement()) {
            connection.setAutoCommit(false);//неявное начало транзакции == start transaction
            dropTable.executeUpdate("drop table if exists " + table);
            connection.commit();//явное окончание транзакции
        } catch (SQLException e) {
            System.out.println("При удалении таблицы возникла ошибка: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackE) {
                System.out.println("При откате изменений возникла ошибка: " + rollbackE);
            }
        } finally {
            try{
                connection.setAutoCommit(true);//неявное окончание транзакции == commit
            }catch (SQLException e){
                System.out.println("Транзакция не была завершена.");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement save = connection.prepareStatement("insert into " + table + " (name, lastName, age) values(?, ?, ?)")) {
            save.setString(1, name);
            save.setString(2, lastName);
            save.setByte(3, age);
            connection.setAutoCommit(false);//неявное начало транзакции == start transaction
            save.executeUpdate();
            connection.commit();//явное окончание транзакции
        } catch (SQLException e) {
            System.out.println("При добавлении данных в таблицу возникла ошибка: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackE) {
                System.out.println("При откате изменений возникла ошибка: " + rollbackE);
            }
        } finally {
            try{
                connection.setAutoCommit(true);//неявное окончание транзакции == commit
            }catch (SQLException e){
                System.out.println("Транзакция не была завершена.");
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement remove = connection.prepareStatement("delete from " + table + " where id = ?")) {
            remove.setLong(1, id);
            connection.setAutoCommit(false);//неявное начало транзакции == start transaction
            remove.executeUpdate();
            connection.commit();//явное окончание транзакции
        } catch (SQLException e) {
            System.out.println("При удалении данных из таблицы возникла ошибка: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackE) {
                System.out.println("При откате изменений возникла ошибка: " + rollbackE);
            }
        } finally {
            try{
                connection.setAutoCommit(true);//неявное окончание транзакции == commit
            }catch (SQLException e){
                System.out.println("Транзакция не была завершена.");
            }
        }
    }

    public List<User> getAllUsers() {
        Connection con = Util.JDBCGetTestConnection();
        List<User> result = new ArrayList<>();
        try {
            ResultSet queryResponse = con.createStatement().executeQuery("select * from " + table);
            while (queryResponse.next()) {
                result.add(new User(
                        queryResponse.getString("name"),
                        queryResponse.getString("lastName"),
                        queryResponse.getByte("age"),
                        queryResponse.getLong("id"))
                );
            }
        } catch (SQLException e) {
            System.out.println("При выгрузке таблицы возникла ошибка: " + e.getMessage());
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Statement remove = connection.createStatement()) {
            connection.setAutoCommit(false);//неявное начало транзакции == start transaction
            remove.executeUpdate("truncate table " + table);
            connection.commit();//явное окончание транзакции
        } catch (SQLException e) {
            System.out.println("При очищении таблицы возникла ошибка: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackE) {
                System.out.println("При откате изменений возникла ошибка: " + rollbackE);
            }
        } finally {
            try{
                connection.setAutoCommit(true);//неявное окончание транзакции == commit
            }catch (SQLException e){
                System.out.println("Транзакция не была завершена.");
            }
        }
    }
}
