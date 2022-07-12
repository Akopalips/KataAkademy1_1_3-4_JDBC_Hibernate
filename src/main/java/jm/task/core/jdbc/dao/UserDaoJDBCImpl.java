package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

public class UserDaoJDBCImpl implements UserDao {

    private void simpleVoidQuery(String query) {
        Connection con = Util.getTestConnection();
        try {
            con.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        simpleVoidQuery("create table if not exists testUserTable(" +
                "name text not null, " +
                "lastName text not null, " +
                "age tinyint not null, " +
                "id serial primary key);");//потом переписать чтобы забирать поля из класса для имен и типов столбцов
    }

    public void dropUsersTable() {
        simpleVoidQuery("drop table if exists testUserTable;");
    }

    public void saveUser(String name, String lastName, byte age) {
        simpleVoidQuery("insert into testUserTable (name, lastName, age) values('" + name + "','" + lastName + "'," + age + ");");
    }

    public void removeUserById(long id) {
        simpleVoidQuery("delete from testUserTable where id ='" + id + "';");
    }

    public List<User> getAllUsers() {
        Connection con = Util.getTestConnection();
        List<User> result = new ArrayList<>();
        try {
            ResultSet queryResponse = con.createStatement().executeQuery("select * from testUserTable;");
            while (queryResponse.next()) {
                result.add(new User(
                        queryResponse.getString("name"),
                        queryResponse.getString("lastName"),
                        queryResponse.getByte("age"),
                        queryResponse.getLong("id"))
                );
            }
        } catch (SQLException e) {
            System.out.println("connection closed unexpectedly");
        }
        return result;
    }

    public void cleanUsersTable() {
        simpleVoidQuery("truncate table testUserTable;");
    }
}
