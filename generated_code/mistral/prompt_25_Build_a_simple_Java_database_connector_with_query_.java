```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public List<String[]> executeQuery(String query, Object... params) throws SQLException {
        List<String[]> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getString(i);
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector(
            "jdbc:mysql://localhost:3306/your_database",
            "username",
            "password"
        );

        try {
            db.connect();

            // Example query
            List<String[]> results = db.executeQuery("SELECT * FROM users WHERE age > ?", 25);
            for (String[] row : results) {
                System.out.println(String.join(", ", row));
            }

            // Example update
            int affectedRows = db.executeUpdate("UPDATE users SET name = ? WHERE id = ?", "New Name", 1);
            System.out.println("Updated rows: " + affectedRows);

            db.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```