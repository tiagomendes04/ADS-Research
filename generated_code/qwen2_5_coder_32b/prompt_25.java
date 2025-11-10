```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleDatabaseConnector {
    private Connection connection;

    public SimpleDatabaseConnector(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public PreparedStatement buildQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        try {
            SimpleDatabaseConnector dbConnector = new SimpleDatabaseConnector("jdbc:mysql://localhost:3306/testdb", "user", "password");
            PreparedStatement statement = dbConnector.buildQuery("SELECT * FROM users WHERE age > ?");
            statement.setInt(1, 25);
            ResultSet resultSet = dbConnector.executeQuery(statement);

            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```