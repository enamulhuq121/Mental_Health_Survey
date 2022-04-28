package form_design;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;

public class module_variable_DataModel{

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
       private String _variable_desc = "";
       public String getvariable_desc(){
             return _variable_desc;
        }
       public void setvariable_desc(String newValue){
             _variable_desc = newValue;
        }
       private String _variable_seq = "";
       public String getvariable_seq(){
             return _variable_seq;
        }
       public void setvariable_seq(String newValue){
             _variable_seq = newValue;
        }
       private String _control_type = "";
       public String getcontrol_type(){
             return _control_type;
        }
       public void setcontrol_type(String newValue){
             _control_type = newValue;
        }
       private String _variable_option = "";
       public String getvariable_option(){
             return _variable_option;
        }
       public void setvariable_option(String newValue){
             _variable_option = newValue;
        }
       private String _variable_length = "";
       public String getvariable_length(){
             return _variable_length;
        }
       public void setvariable_length(String newValue){
             _variable_length = newValue;
        }
       private String _data_type = "";
       public String getdata_type(){
             return _data_type;
        }
       public void setdata_type(String newValue){
             _data_type = newValue;
        }
       private String _skip_rule = "";
       public String getskip_rule(){
             return _skip_rule;
        }
       public void setskip_rule(String newValue){
             _skip_rule = newValue;
        }
       private String _color = "";
       public String getcolor(){
             return _color;
        }
       public void setcolor(String newValue){
             _color = newValue;
        }
       private String _active = "";
       public String getactive(){
             return _active;
        }
       public void setactive(String newValue){
             _active = newValue;
        }
       private String _StartTime = "";
       public void setStartTime(String newValue){
             _StartTime = newValue;
        }
       private String _EndTime = "";
       public void setEndTime(String newValue){
             _EndTime = newValue;
        }
       private String _DeviceID = "";
       public void setDeviceID(String newValue){
             _DeviceID = newValue;
        }
       private String _EntryUser = "";
       public void setEntryUser(String newValue){
             _EntryUser = newValue;
        }
       private String _Lat = "";
       public void setLat(String newValue){
             _Lat = newValue;
        }
       private String _Lon = "";
       public void setLon(String newValue){
             _Lon = newValue;
        }
       private String _EnDt = "";
       public void setEnDt(String newValue){
             _EnDt = newValue;
        }
       private String _Upload = "2";
       private String _modifyDate = "";
       public void setmodifyDate(String newValue){
       _modifyDate = newValue;
       }

       //*************** sakib start ********************
       private String _image = "";
       private String _audio= "";
       private String _video = "";
//        private String _language_type = "";

//     public String get_language_type() {
//         return _language_type;
//     }


    public String get_data_id() {
        return _data_id;
    }

    public void set_data_id(String _data_id) {
        this._data_id = _data_id;
    }

    private String _data_id = "";

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_audio() {
        return _audio;
    }

    public void set_audio(String _audio) {
        this._audio = _audio;
    }

    public String get_video() {
        return _video;
    }

    public void set_video(String _video) {
        this._video = _video;
    }

    //*************** sakib end **********************

       String TableName = "module_variable";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where module_id='"+ _module_id +"' and variable_name='"+ _variable_name +"' "))
                   response = UpdateData(context);
                else
                   response = SaveData(context);
           }
           catch(Exception  e)
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
                SQL = "Insert into "+ TableName +" (module_id,variable_name,variable_desc,variable_seq,control_type,variable_option,variable_length,data_type,skip_rule,color,active,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _module_id +"', '"+ _variable_name +"', '"+ _variable_desc +"', '"+ _variable_seq +"', '"+ _control_type +"', '"+ _variable_option +"', '"+ _variable_length +"', '"+ _data_type +"', '"+ _skip_rule +"', '"+ _color +"', '"+ _active +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
                response = C.SaveData(SQL);
                C.close();
             }
             catch(Exception  e)
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
                SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,module_id = '"+ _module_id +"',variable_name = '"+ _variable_name +"',variable_desc = '"+ _variable_desc +"',variable_seq = '"+ _variable_seq +"',control_type = '"+ _control_type +"',variable_option = '"+ _variable_option +"',variable_length = '"+ _variable_length +"',data_type = '"+ _data_type +"',skip_rule = '"+ _skip_rule +"',color = '"+ _color +"',active = '"+ _active +"'  Where module_id='"+ _module_id +"' and variable_name='"+ _variable_name +"'";
                 response = C.SaveData(SQL);
                C.close();
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<module_variable_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<module_variable_DataModel> data = new ArrayList<module_variable_DataModel>();
           module_variable_DataModel d = new module_variable_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new module_variable_DataModel();
               d._module_id = cur.getString(cur.getColumnIndex("module_id"));
               d._variable_name = cur.getString(cur.getColumnIndex("variable_name"));
               d._variable_desc = cur.getString(cur.getColumnIndex("variable_desc"));
               d._variable_seq = cur.getString(cur.getColumnIndex("variable_seq"));
               d._control_type = cur.getString(cur.getColumnIndex("control_type"));
               d._variable_option = cur.getString(cur.getColumnIndex("variable_option"));
               d._variable_length = cur.getString(cur.getColumnIndex("variable_length"));
               d._data_type = cur.getString(cur.getColumnIndex("data_type"));
               d._skip_rule = cur.getString(cur.getColumnIndex("skip_rule"));
               d._color = cur.getString(cur.getColumnIndex("color"));
               d._active = cur.getString(cur.getColumnIndex("active"));
//                d._language_type = cur.getString(cur.getColumnIndex("language_type"));

               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }


    private String _variable_data = "";
    public String getvariable_data(){
        return _variable_data;
    }

    public void set_variable_data(String _variable_data) {
        this._variable_data = _variable_data;
    }

    private String _data_desc = "";
    public String getdata_desc(){
        return _variable_option;
    }

    private String _status = "";
    public String getstatus(){
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public List<module_variable_DataModel> SelectAll_WithVariableData(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<module_variable_DataModel> data = new ArrayList<module_variable_DataModel>();
        module_variable_DataModel d = new module_variable_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            d = new module_variable_DataModel();
            d._module_id = cur.getString(cur.getColumnIndex("module_id"));
            d._variable_name = cur.getString(cur.getColumnIndex("variable_name"));
            d._variable_desc = cur.getString(cur.getColumnIndex("variable_desc"));
            d._variable_seq = cur.getString(cur.getColumnIndex("variable_seq"));
            d._control_type = cur.getString(cur.getColumnIndex("control_type"));
            d._variable_option = cur.getString(cur.getColumnIndex("variable_option"));
            d._variable_length = cur.getString(cur.getColumnIndex("variable_length"));
            d._data_type = cur.getString(cur.getColumnIndex("data_type"));
            d._skip_rule = cur.getString(cur.getColumnIndex("skip_rule"));
            d._color = cur.getString(cur.getColumnIndex("color"));
            d._active = cur.getString(cur.getColumnIndex("active"));


            d._variable_data = cur.getString(cur.getColumnIndex("variable_data"));
            d._data_desc = cur.getString(cur.getColumnIndex("data_desc"));
            d._status = cur.getString(cur.getColumnIndex("status"));

            //*************** sakib start ********************

            d._image=cur.getString(cur.getColumnIndex("variable_image"));
            d._audio=cur.getString(cur.getColumnIndex("variable_audio"));
            d._video=cur.getString(cur.getColumnIndex("variable_video"));
            d._data_id=cur.getString(cur.getColumnIndex("data_id"));
//             d._language_type=cur.getString(cur.getColumnIndex("language_type"));

            //*************** sakib end **********************


            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }


}