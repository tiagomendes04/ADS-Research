```java
import java.sql.*;
import java.util.*;

public class DatabaseConnector {
    private Connection connection;
    private String dbUrl;
    private String username;
    private String password;

    public DatabaseConnector(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl, username, password);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    public void query(String query, Object[] params) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            statement.executeUpdate();
        }
    }

    public ResultSet queryWithResult(String query, Object[] params) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            return statement.executeQuery();
        }
    }
}
```

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    public static List<String> queryWithResultList(String query, Object[] params) {
        List<String> results = new ArrayList<>();
        try (DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/mydb", "user", "password");
             ResultSet resultSet = connector.queryWithResult(query, params)) {
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            // Handle exception
        }
        return results;
    }

    public static int queryExecuteUpdate(String query, Object[] params) {
        try (DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/mydb", "user", "password")) {
            return connector.query(query, params);
        } catch (SQLException e) {
            // Handle exception
            return 0;
        }
    }
}
```

```java
public class ExampleUsage {
    public static void main(String[] args) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        String name = "John Doe";
        String email = "john.doe@example.com";
        DatabaseHelper.queryExecuteUpdate(query, new Object[]{name, email});

        query = "SELECT * FROM users WHERE name = ?";
        List<String> results = DatabaseHelper.queryWithResultList(query, new Object[]{name});
        System.out.println(results);
    }
}
```