package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoJDBCImpl();//UserDaoJDBCImpl UserDaoHibernateImpl

    public void createUsersTable() {
        System.out.println("Создаем таблицу testUserTable.");
        try{
            userDao.createUsersTable();
            System.out.println("Создана таблица testUserTable.");
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
        }
    }

    public void dropUsersTable() {
        System.out.println("Удаляем таблицу testUserTable.");
        try{
            userDao.dropUsersTable();
            System.out.println("Удалена таблица testUserTable.");
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        System.out.println("Добавляем пользователя.");
        try{
            userDao.saveUser(name, lastName, age);
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
        }
    }

    public void removeUserById(long id) {
        System.out.println("Удаляем пользователя.");
        try{
            userDao.removeUserById(id);
            System.out.printf("User с id – %d удален.\n", id);
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
        }
    }

    public List<User> getAllUsers() {
        System.out.println("Забираем из таблицы всех пользователей.");
        try{
            List <User> list = userDao.getAllUsers();
            System.out.println("Список принят успешно, содержит:");
            for (User eachUser : list){
                System.out.println(eachUser);
            }
            return list;
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
            return null;
        }
    }

    public void cleanUsersTable() {
        System.out.println("Очищаем таблицу.");
        try{
            userDao.cleanUsersTable();
            System.out.println("Таблица не содержит элементов, итератор сброшен.");
        } catch (Throwable t){
            System.out.println("Возникла ошибка:" + t.getMessage());
        }
    }
}
