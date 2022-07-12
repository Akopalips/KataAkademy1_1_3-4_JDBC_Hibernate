package jm.task.core.jdbc.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class Util {
    private static Connection connectionCache = null;

    public static Connection getMySQLConnection(
            String host, String db, String user, String pass, int port
    ) {
        try {
            return (connectionCache == null ?
                    connectionCache = DriverManager.getConnection("jdbc:mysql://" + host + ':' + port + '/' + db, user, pass) :
                    connectionCache);
        } catch (SQLTimeoutException e) {
            System.out.println("timeout has been exceeded");
        } catch (SQLException e) {
            System.out.println("database access error occurs or the url is null");
        }
        return null;
    }

    public static Connection getMySQLConnection(
            String host, String db, String user, String pass
    ) {
        return getMySQLConnection(host, db, user, pass, 3306);
    }

    public static Connection getTestConnection() {
        return getMySQLConnection("localhost", "testdb", "testuser", "kiparis351");
    }
    // реализуйте настройку соеденения с БД
}
