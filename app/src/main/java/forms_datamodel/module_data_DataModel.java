package forms_datamodel;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class module_data_DataModel{
    private String _module_id = "";
    public String getmodule_id(){
         return _module_id;
    }
   public void setmodule_id(String newValue){
         _module_id = newValue;
    }
   private String _variable_name = "";
   public String getvariable_name(){
         return _variable_name;
    }
   public void setvariable_name(String newValue){
         _variable_name = newValue;
    }
   private String _data_id = "";
   public String getdata_id(){
         return _data_id;
    }
   public void setdata_id(String newValue){
         _data_id = newValue;
    }

    /*private int _serial = 0;
    public int getserial(){
        return _serial;
    }
    public void setserial(int newValue){
        _serial = newValue;
    }

    private int _visit = 0;
    public int getvisit(){
        return _visit;
    }
    public void setvisit(int newValue){
        _visit = newValue;
    }*/

    private String _variable_data = "";
   public String getvariable_data(){
         return _variable_data;
    }
   public void setvariable_data(String newValue){
         _variable_data = newValue;
    }
   private String _data_desc = "";
   public String getdata_desc(){
         return _data_desc;
    }
   public void setdata_desc(String newValue){
         _data_desc = newValue;
    }
   private String _status = "";
   public String getstatus(){
         return _status;
    }
   public void setstatus(String newValue){
         _status = newValue;
    }
   private String _entry_date = "";
   public String getentry_date(){
         return _entry_date;
    }
   public void setentry_date(String newValue){
         _entry_date = newValue;
    }
   private String _first_entry_time = "";
   public String getfirst_entry_time(){
         return _first_entry_time;
    }
   public void setfirst_entry_time(String newValue){
         _first_entry_time = newValue;
    }
   private String _final_entry_time = "";
   public String getfinal_entry_time(){
         return _final_entry_time;
    }
   public void setfinal_entry_time(String newValue){
         _final_entry_time = newValue;
    }

   private String _DeviceID = "";
   public void setDeviceID(String newValue){
         _DeviceID = newValue;
    }
   private String _EntryUser = "";
   public void setEntryUser(String newValue){
         _EntryUser = newValue;
    }
   private String _Upload = "2";
   private String _modifyDate = "";
   public void setmodifyDate(String newValue){
   _modifyDate = newValue;
   }

    private String _note = "";
    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

       String TableName = "module_data";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where module_id='"+ _module_id +"' and variable_name='"+ _variable_name +"' and data_id='"+ _data_id +"'"))
                   response = UpdateData(context);
                else
                   response = SaveData(context);
           }
           catch(Exception e)
           {
                response = e.getMessage();
           }
          return response;
       }
       Connection C;


       private String SaveData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Insert into "+ TableName +" (module_id,variable_name,data_id,variable_data,data_desc,status,entry_date,first_entry_time,final_entry_time,DeviceID,EntryUser,Upload,modifyDate,note)Values('"+ _module_id +"', '"+ _variable_name +"', '"+ _data_id +"','"+ _variable_data +"', '"+ _data_desc +"', '"+ _status +"', '"+ _entry_date +"', '"+ _first_entry_time +"', '"+ _final_entry_time +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Upload +"', '"+ _modifyDate+"', '"+ _note+"')";
                response = C.SaveData(SQL);
                C.close();
             }
             catch(Exception e)
             {
                response = e.getMessage();
             }
          return response;
       }

       private String UpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,variable_data = '"+ _variable_data +"',data_desc = '"+ _data_desc +"',status = '"+ _status +"',entry_date = '"+ _entry_date +"'," +
                        "first_entry_time = (case when first_entry_time is null or length(ifnull(first_entry_time,''))=0 then '"+ _first_entry_time +"' else first_entry_time end)," +
                        "final_entry_time = '"+ _final_entry_time +"',note='"+_note+"'" +
                        " Where module_id='"+ _module_id +"' and variable_name='"+ _variable_name +"' and data_id='"+ _data_id+"'";

                response = C.SaveData(SQL);
                C.close();
             }
             catch(Exception e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<module_data_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<module_data_DataModel> data = new ArrayList<module_data_DataModel>();
           module_data_DataModel d = new module_data_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new module_data_DataModel();
               d._module_id = cur.getString(cur.getColumnIndex("module_id"));
               d._variable_name = cur.getString(cur.getColumnIndex("variable_name"));
               d._data_id = cur.getString(cur.getColumnIndex("data_id"));
               d._variable_data = cur.getString(cur.getColumnIndex("variable_data"));
               d._data_desc = cur.getString(cur.getColumnIndex("data_desc"));
               d._status = cur.getString(cur.getColumnIndex("status"));
               d._entry_date = cur.getString(cur.getColumnIndex("entry_date"));
               d._first_entry_time = cur.getString(cur.getColumnIndex("first_entry_time"));
               d._final_entry_time = cur.getString(cur.getColumnIndex("final_entry_time"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}