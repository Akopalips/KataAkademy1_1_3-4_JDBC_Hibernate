package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.logging.Level;

public class Util {
    private final static String url = "jdbc:mysql://localhost:3306/testdb";
    private final static String user = "testuser";
    private final static String pass = "kiparis351";
    private static SessionFactory sessionFactory;

    public static Session hibernateGetSession() {
        if (sessionFactory == null) {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                    .applySetting(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                    .applySetting(Environment.URL, url)
                    .applySetting(Environment.USER, user)
                    .applySetting(Environment.PASS, pass)
                    .applySetting(Environment.SHOW_SQL, "false")
                    .applySetting(Environment.HBM2DDL_AUTO, "create-drop")//update?
                    .applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread").build())
                    .addAnnotatedClass(User.class).buildMetadata().getSessionFactoryBuilder().build();
        }
        return sessionFactory.openSession();
    }
}
