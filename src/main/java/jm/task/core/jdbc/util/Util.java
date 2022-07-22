package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.logging.Level;

public class Util {
    private static Connection connectionCache = null;
    private static String user = "testuser";
    private static String pass = "kiparis351";
    private static String host = "localhost";
    private static String db = "testdb";
    private static int port = 3306;

    public static Connection JDBCGetTestConnection() {
        if (connectionCache == null) {
            try {
                System.out.println("Соединение с БД.");
                connectionCache = DriverManager.getConnection("jdbc:mysql://" + host + ':' + port + '/' + db, user, pass);
            } catch (SQLTimeoutException e) {
                System.out.println("timeout has been exceeded " + e);
            } catch (SQLException e) {
                System.out.println("database access error occurs or the url is null " + e);
            }
        }
        return connectionCache;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static SessionFactory sessionFactory = null;

    public static Session HibernateGetSession() {
        if (sessionFactory == null) {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                    .applySetting(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                    .applySetting(Environment.URL, "jdbc:mysql://" + host + ':' + port + '/' + db)
                    .applySetting(Environment.USER, user)
                    .applySetting(Environment.PASS, pass)
                    .applySetting(Environment.SHOW_SQL, "false")
                    .applySetting(Environment.HBM2DDL_AUTO, "create-drop")//update?
                    .applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread").build())
                    .addAnnotatedClass(User.class).buildMetadata().getSessionFactoryBuilder().build();
        }
        return sessionFactory.getCurrentSession();
    }
}
