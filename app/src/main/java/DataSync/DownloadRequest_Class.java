package DataSync;

import java.util.List;

import Common.DataClassProperty;

/**
 * Created by TanvirHossain on 28/11/2015.
 */
public class DownloadRequest_Class
{
    private String tablename;
    public void settablename(String _tablename){this.tablename=_tablename;}
    public String gettablename(){return this.tablename;}

    List<String> data_id;
    public void setdata(List<String> _data){this.data_id=_data;}
    public List<String> getdata(){return this.data_id;}
}