package Common;

public class Download_Request_Class {
    private String TableName;
    public void setTableName(String _TableName){this.TableName=_TableName;}
    public String getTableName(){return this.TableName;}

    private String DeviceId;
    public void setDeviceId(String _DeviceId){this.DeviceId=_DeviceId;}
    public String getDeviceId(){return this.DeviceId;}

    private String WhereClause;
    public void setWhereClause(String _WhereClause){this.WhereClause=_WhereClause;}
    public String getWhereClause(){return this.WhereClause;}
}
