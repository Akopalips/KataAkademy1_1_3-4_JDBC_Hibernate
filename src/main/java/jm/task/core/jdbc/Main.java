package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Андрей", "Андреев", (byte)35);
        service.saveUser("Саша", "Великолепная", (byte)21);
        service.saveUser("Эмилия", "Серьёзных", (byte)32);
        service.saveUser("Жук", "Берия", (byte)45);
        service.getAllUsers().forEach(System.out::println);
        service.removeUserById(4);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.getAllUsers().forEach(System.out::println);
        service.dropUsersTable();
    }
//    Создание таблицы User(ов)
//    Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль
//      ( User с именем – name добавлен в базу данных )
//    Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
//    Очистка таблицы User(ов)
//    Удаление таблицы
}
