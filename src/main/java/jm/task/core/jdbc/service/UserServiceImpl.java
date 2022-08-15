package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();//UserDaoJDBCImpl UserDaoHibernateImpl

    public void createUsersTable() {
        System.out.println("Создаем таблицу testUserTable.");
        userDao.createUsersTable();
        System.out.println("Создана.");
    }

    public void dropUsersTable() {
        System.out.println("Удаляем таблицу testUserTable.");
        userDao.dropUsersTable();
        System.out.println("Удалена.");
    }

    public void saveUser(String name, String lastName, byte age) {
        System.out.println("Добавляем пользователя.");
        userDao.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        System.out.println("Удаляем пользователя.");
        userDao.removeUserById(id);
        System.out.printf("User с id – %d удален.\n", id);
    }

    public List<User> getAllUsers() {
        System.out.println("Забираем из таблицы всех пользователей.");
        List<User> list = userDao.getAllUsers();
        System.out.println("Список принят.");
        return list;
    }

    public void cleanUsersTable() {
        System.out.println("Очищаем таблицу.");
        userDao.cleanUsersTable();
        System.out.println("Таблица не содержит элементов, итератор сброшен.");
    }
}
