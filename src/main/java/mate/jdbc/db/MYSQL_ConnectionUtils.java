package mate.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MYSQL_ConnectionUtils {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/taxiservicedb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "132455567Ig@";
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database");
        }
    }
}
