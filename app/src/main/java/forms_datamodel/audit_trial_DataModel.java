package forms_datamodel;

import android.content.ContentValues;
import android.content.Context;

import Common.Connection;
import Common.Global;

public class audit_trial_DataModel {

        private String _audit_id = "";
        public String getaudit_id(){
              return _audit_id;
         }
        public void setaudit_id(String newValue){
              _audit_id = newValue;
         }
        private String _table_name = "";
        public String gettable_name(){
              return _table_name;
         }
        public void settable_name(String newValue){
              _table_name = newValue;
         }
        private String _data_id = "";
        public String getdata_id(){
              return _data_id;
         }
        public void setdata_id(String newValue){
              _data_id = newValue;
         }
        private String _variable_name = "";
        public String getvariable_name(){
              return _variable_name;
         }
        public void setvariable_name(String newValue){
              _variable_name = newValue;
         }
        private String _old_value = "";
        public String getold_value(){
              return _old_value;
         }
        public void setold_value(String newValue){
              _old_value = newValue;
         }
        private String _new_value = "";
        public String getnew_value(){
              return _new_value;
         }
        public void setnew_value(String newValue){
              _new_value = newValue;
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
        private String _EnDt = Global.DateTimeNowYMDHMS();
        private int _Upload = 2;
        private String _modifyDate = Global.DateTimeNowYMDHMS();

        public final static String TableName = "audit_trial";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where audit_id='"+ _audit_id +"' "))
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
            try
              {
                 ContentValues contentValues = new ContentValues();
                 contentValues.put("audit_id", _audit_id);
                 contentValues.put("table_name", _table_name);
                 contentValues.put("data_id", _data_id);
                 contentValues.put("variable_name", _variable_name);
                 contentValues.put("old_value", _old_value);
                 contentValues.put("new_value", _new_value);
                 contentValues.put("StartTime", _StartTime);
                 contentValues.put("EndTime", _EndTime);
                 contentValues.put("DeviceID", _DeviceID);
                 contentValues.put("EntryUser", _EntryUser);
                 contentValues.put("Lat", _Lat);
                 contentValues.put("Lon", _Lon);
                 contentValues.put("EnDt", _EnDt);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.InsertData(TableName, contentValues);
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
            try
              {
                 ContentValues contentValues = new ContentValues();
                 contentValues.put("audit_id", _audit_id);
                 contentValues.put("table_name", _table_name);
                 contentValues.put("data_id", _data_id);
                 contentValues.put("variable_name", _variable_name);
                 contentValues.put("old_value", _old_value);
                 contentValues.put("new_value", _new_value);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "audit_id", (""+ _audit_id +""), contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

    @Override
    public String toString() {
        return  "('" + _audit_id + '\'' +
                ",'" + _table_name + '\'' +
                ",'" + _data_id + '\'' +
                ",'" + _variable_name + '\'' +
                ",'" + _old_value + '\'' +
                ",'" + _new_value + '\'' +
                ",'" + _StartTime + '\'' +
                ",'" + _EndTime + '\'' +
                ",'" + _DeviceID + '\'' +
                ",'" + _EntryUser + '\'' +
                ",'" + _Lat + '\'' +
                ",'" + _Lon + '\'' +
                ",'" + _EnDt + '\'' +
                "," + _Upload +
                ",'" + _modifyDate + '\''+
                ")";
    }

}