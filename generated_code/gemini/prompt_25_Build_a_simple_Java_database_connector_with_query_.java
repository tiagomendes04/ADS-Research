```java
import java.sql.*;
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
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public QueryBuilder query(String tableName) {
        return new QueryBuilder(this, tableName);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    public Connection getConnection() {
        return connection;
    }


    public static class QueryBuilder {
        private DatabaseConnector connector;
        private String tableName;
        private StringBuilder selectClause;
        private StringBuilder whereClause;
        private StringBuilder orderByClause;
        private List<Object> parameters;
        private boolean isSelect;

        public QueryBuilder(DatabaseConnector connector, String tableName) {
            this.connector = connector;
            this.tableName = tableName;
            this.selectClause = new StringBuilder();
            this.whereClause = new StringBuilder();
            this.orderByClause = new StringBuilder();
            this.parameters = new ArrayList<>();
            this.isSelect = true;  // Default to SELECT
        }

        public QueryBuilder select(String... columns) {
            selectClause.append("SELECT ");
            if (columns == null || columns.length == 0) {
                selectClause.append("*");
            } else {
                for (int i = 0; i < columns.length; i++) {
                    selectClause.append(columns[i]);
                    if (i < columns.length - 1) {
                        selectClause.append(", ");
                    }
                }
            }
            selectClause.append(" FROM ").append(tableName);
            return this;
        }


        public QueryBuilder where(String condition) {
            if (whereClause.length() == 0) {
                whereClause.append(" WHERE ").append(condition);
            } else {
                whereClause.append(" AND ").append(condition);
            }
            return this;
        }

        public QueryBuilder and(String condition) {
            if (whereClause.length() == 0) {
                whereClause.append(" WHERE ").append(condition);
            } else {
                whereClause.append(" AND ").append(condition);
            }
            return this;
        }

        public QueryBuilder or(String condition) {
            if (whereClause.length() == 0) {
                whereClause.append(" WHERE ").append(condition);
            } else {
                whereClause.append(" OR ").append(condition);
            }
            return this;
        }

        public QueryBuilder orderBy(String column) {
            if (orderByClause.length() == 0) {
                orderByClause.append(" ORDER BY ").append(column);
            } else {
                orderByClause.append(", ").append(column);
            }
            return this;
        }

        public QueryBuilder setParameters(Object... params) {
            for (Object param : params) {
                parameters.add(param);
            }
            return this;
        }

        public ResultSet get() throws SQLException {
            String sql = buildSelectQuery();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql);
            setPreparedStatementParameters(preparedStatement);
            return preparedStatement.executeQuery();
        }

         public int insert(String columns, String values) throws SQLException {
            isSelect = false;
            String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
            return connector.executeUpdate(sql);
        }

        public int update(String updates) throws SQLException {
            isSelect = false;
            String sql = "UPDATE " + tableName + " SET " + updates + whereClause.toString();
            return connector.executeUpdate(sql);
        }

        public int delete() throws SQLException {
            isSelect = false;
            String sql = "DELETE FROM " + tableName + whereClause.toString();
            return connector.executeUpdate(sql);
        }


        private String buildSelectQuery() {
            StringBuilder sql = new StringBuilder();
            if(selectClause.length() > 0){
                 sql.append(selectClause);
            }else{
                sql.append("SELECT * FROM ").append(tableName);
            }
            if (whereClause.length() > 0) {
                sql.append(whereClause);
            }
            if (orderByClause.length() > 0) {
                sql.append(orderByClause);
            }
            return sql.toString();
        }

        private void setPreparedStatementParameters(PreparedStatement preparedStatement) throws SQLException {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String username = "root";
        String password = "password";

        DatabaseConnector connector = new DatabaseConnector(url, username, password);

        try {
            connector.connect();

            // Example SELECT query
            ResultSet resultSet = connector.query("users")
                    .select("id", "name", "email")
                    .where("id > ?")
                    .and("name LIKE ?")
                    .setParameters(1, "J%")
                    .orderBy("name")
                    .get();


            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Email: " + resultSet.getString("email"));
            }

            // Example INSERT query
            int insertedRows = connector.query("users").insert("name, email", "'John Doe', 'john.doe@example.com'");
            System.out.println("Inserted rows: " + insertedRows);

             // Example UPDATE query
            int updatedRows = connector.query("users").where("id = 1").update("name = 'Updated Name'");
            System.out.println("Updated rows: " + updatedRows);

            // Example DELETE query
            int deletedRows = connector.query("users").where("id = 3").delete();
            System.out.println("Deleted rows: " + deletedRows);



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connector.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```