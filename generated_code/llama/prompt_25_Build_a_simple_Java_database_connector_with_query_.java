**Database Connector**
======================

### DatabaseConnector.java

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private Connection connection;

    public DatabaseConnector(String dbUrl, String username, String password) {
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public void executeQuery(String query) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
    }

    public void executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }

    public void executeInsert(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing insert: " + e.getMessage());
        }
    }

    public List<String> query(String query) {
        List<String> results = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return results;
    }

    public void queryWithParams(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query with params: " + e.getMessage());
        }
    }
}
```

### DatabaseQuery.java

```java
public class DatabaseQuery {
    private String query;
    private Object[] params;

    public DatabaseQuery(String query) {
        this.query = query;
        this.params = new Object[0];
    }

    public DatabaseQuery(String query, Object... params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object... params) {
        this.params = params;
    }
}
```

### Main.java

```java
public class Main {
    public static void main(String[] args) {
        DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/mydb", "username", "password");

        DatabaseQuery query = new DatabaseQuery("SELECT * FROM mytable");
        connector.executeQuery(query.getQuery());

        query = new DatabaseQuery("INSERT INTO mytable (name, age) VALUES (?, ?)", "John", 30);
        connector.executeInsert(query.getQuery(), query.getParams());

        query = new DatabaseQuery("SELECT * FROM mytable WHERE name = ?", "John");
        connector.queryWithParams(query.getQuery(), query.getParams());

        connector.close();
    }
}
```