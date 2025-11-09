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