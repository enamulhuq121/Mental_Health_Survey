package DataSync;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TanvirHossain on 03/12/2015.
 */
public class api_DownloadRequestClass
{
    @SerializedName("host_address")
    String host_address;
    public void sethost_address(String _host_address) {this.host_address=_host_address;}
    public String gethost_address() {return this.host_address;}

    @SerializedName("method")
    String database_name;
    public void setdatabase_name(String _database_name) {this.database_name=_database_name;}
    public String getdatabase_name() {return this.database_name;}

    @SerializedName("method_name")
    String method_name;
    public void setmethod_name(String _method_name){this.method_name=_method_name;}
    public String getmethod_name(){return this.method_name;}

    @SerializedName("sql")
    String sql;
    public void setsql(String _sql){this.method_name=_sql;}
    public String getsql(){return this.sql;}

    @SerializedName("device_id")
    String device_id;
    public void setdevice_id(String _device_id){this.device_id=_device_id;}
    public String getdevice_id(){return this.device_id;}
}