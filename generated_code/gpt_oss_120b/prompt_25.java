```java
import java.sql.*;
import java.util.*;

public class SimpleDbConnector {
    private final String url;
    private final String user;
    private final String password;

    public SimpleDbConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                return resultSetToList(rs);
            }
        }
    }

    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    // ---------- Query Builder ----------
    public static class QueryBuilder {
        private final StringBuilder sql = new StringBuilder();
        private final List<Object> params = new ArrayList<>();

        public QueryBuilder select(String... columns) {
            sql.append("SELECT ");
            sql.append(columns.length == 0 ? "*" : String.join(", ", columns));
            sql.append(" ");
            return this;
        }

        public QueryBuilder from(String table) {
            sql.append("FROM ").append(table).append(" ");
            return this;
        }

        public QueryBuilder where(String condition, Object... values) {
            sql.append("WHERE ").append(condition).append(" ");
            Collections.addAll(params, values);
            return this;
        }

        public QueryBuilder and(String condition, Object... values) {
            sql.append("AND ").append(condition).append(" ");
            Collections.addAll(params, values);
            return this;
        }

        public QueryBuilder or(String condition, Object... values) {
            sql.append("OR ").append(condition).append(" ");
            Collections.addAll(params, values);
            return this;
        }

        public QueryBuilder orderBy(String... columns) {
            sql.append("ORDER BY ").append(String.join(", ", columns)).append(" ");
            return this;
        }

        public QueryBuilder limit(int limit) {
            sql.append("LIMIT ? ");
            params.add(limit);
            return this;
        }

        public QueryBuilder insertInto(String table, String... columns) {
            sql.append("INSERT INTO ").append(table);
            if (columns.length > 0) {
                sql.append(" (").append(String.join(", ", columns)).append(")");
            }
            sql.append(" VALUES (");
            sql.append(String.join(", ", Collections.nCopies(columns.length, "?")));
            sql.append(") ");
            return this;
        }

        public QueryBuilder update(String table) {
            sql.append("UPDATE ").append(table).append(" SET ");
            return this;
        }

        public QueryBuilder set(String column, Object value) {
            sql.append(column).append(" = ?, ");
            params.add(value);
            return this;
        }

        public QueryBuilder deleteFrom(String table) {
            sql.append("DELETE FROM ").append(table).append(" ");
            return this;
        }

        public String build() {
            // Clean trailing commas/spaces
            String built = sql.toString().trim();
            if (built.endsWith(",")) {
                built = built.substring(0, built.length() - 1);
            }
            return built;
        }

        public Object[] getParams() {
            return params.toArray();
        }
    }

    // Example usage:
    // SimpleDbConnector db = new SimpleDbConnector("jdbc:mysql://localhost:3306/db", "user", "pass");
    // QueryBuilder qb = new QueryBuilder()
    //         .select("id", "name")
    //         .from("users")
    //         .where("age > ?", 18)
    //         .orderBy("name")
    //         .limit(10);
    // List<Map<String, Object>> rows = db.executeQuery(qb.build(), qb.getParams());
}
```