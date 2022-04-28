package apiDataSync;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TanvirHossain on 28/11/2015.
 */
public class apiDataClass
{
    @SerializedName("method")
    String method;
    public void setmethodname(String _method) {this.method=_method;}
    public String getmethodname() {return this.method;}

    @SerializedName("tablename")
    String tablename;
    public void settablename(String _tablename){this.tablename=_tablename;}
    public String gettablename(){return this.tablename;}

    @SerializedName("columnlist")
    String columnlist;
    public void setcolumnlist(String _columnlist){this.columnlist=_columnlist;}
    public String getcolumnlist(){return this.columnlist;}

    @SerializedName("data")
    List<apiDataClassProperty> data;
    public void setdata(List<apiDataClassProperty> _data){this.data=_data;}
    public List<apiDataClassProperty> getdata(){return this.data;}
}