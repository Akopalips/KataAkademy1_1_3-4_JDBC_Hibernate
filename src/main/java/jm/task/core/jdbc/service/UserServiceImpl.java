package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl DaoJDBC = new UserDaoJDBCImpl();

    public void createUsersTable() {
        DaoJDBC.createUsersTable();
    }

    public void dropUsersTable() {
        DaoJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        DaoJDBC.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        DaoJDBC.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return DaoJDBC.getAllUsers();
    }

    public void cleanUsersTable() {
        DaoJDBC.cleanUsersTable();
    }
}
