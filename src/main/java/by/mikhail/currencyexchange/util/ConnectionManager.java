package by.mikhail.currencyexchange.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionManager {
    private static final String URL = "jdbc:sqlite:C:\\Users\\GigaPC\\Desktop\\Java\\PetProjects\\CurrencyExchange\\src\\main\\resources\\database";

    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;

        ConnectionManager.getConnection();
        System.out.println("kek");
    }
    static {
        loadDriver();
    }
    private ConnectionManager(){}
    private static void loadDriver(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(URL);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
