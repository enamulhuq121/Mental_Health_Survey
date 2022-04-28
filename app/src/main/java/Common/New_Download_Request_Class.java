package Common;

public class New_Download_Request_Class {
    private String TableName;
    public void setTableName(String _TableName){this.TableName=_TableName;}
    public String getTableName(){return this.TableName;}

    private String Server_TableName;
    public void setServer_TableName(String _Server_TableName){this.Server_TableName=_Server_TableName;}
    public String getServer_TableName(){return this.Server_TableName;}

    private String DeviceId;
    public void setDeviceId(String _DeviceId){this.DeviceId=_DeviceId;}
    public String getDeviceId(){return this.DeviceId;}

    private String WhereClause;
    public void setWhereClause(String _WhereClause){this.WhereClause=_WhereClause;}
    public String getWhereClause(){return this.WhereClause;}

    private String TimeStamp;
    public void setTimeStamp(String _TimeStamp){this.TimeStamp=_TimeStamp;}
    public String getTimeStamp(){return this.TimeStamp;}
}
