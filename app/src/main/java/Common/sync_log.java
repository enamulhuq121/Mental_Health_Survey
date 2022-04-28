package Common;

public class sync_log {
    private String user_id;
    public void setUserId(String _user_id){this.user_id=_user_id;}

    private String table_name;
    public void setTableName(String _table_name){this.table_name=_table_name;}

    private String statement;
    public void setStatement(String _statement){this.statement=_statement;}

    private String error;
    public void setError(String _error){this.error=_error;}

    private String where_clause;
    public void setWhereClause(String _where_clause){this.where_clause=_where_clause;}
}
