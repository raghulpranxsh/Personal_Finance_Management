package Database;

import java.sql.*;

/**
 * DatabaseManager handles MySQL connection and queries for the PFMS app.
 */
public class DatabaseManager {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/pfmsdemo?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "finance_user";        // MySQL user you created
    private static final String PASSWORD = "finance123";      // User password

    private static Connection connection;
    private static Statement statement;

    /**
     * Connect to the database.
     */
    public static void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            System.out.println("Connected to the database successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
            e.printStackTrace();
        }
    }

    /**
     * Get the connection object.
     * @return Connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Execute a SELECT query and return the ResultSet.
     * @param query SQL SELECT query
     * @return ResultSet of query
     */
    public static ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            if (statement == null) {
                statement = connection.createStatement();
            }
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + query);
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Execute an INSERT, UPDATE, or DELETE query.
     * @param query SQL query
     * @return Number of affected rows
     */
    public static int executeUpdate(String query) {
        int result = 0;
        try {
            if (statement == null) {
                statement = connection.createStatement();
            }
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error executing update: " + query);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Close the database connection.
     */
    public static void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Example main method for quick test
    public static void main(String[] args) {
        connect();
        try {
            ResultSet rs = executeQuery("SELECT * FROM user LIMIT 5");
            while (rs != null && rs.next()) {
                System.out.println(rs.getInt("user_id") + " | " +
                                   rs.getString("username") + " | " +
                                   rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
