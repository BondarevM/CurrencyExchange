package by.mikhail.currencyexchange.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";

    static {
        loadDriver();
    }

    private ConnectionManager() {
    }

    private static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.getProperty(URL_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
