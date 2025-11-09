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