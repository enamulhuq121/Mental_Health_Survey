package module_variablelist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Connection;
import Common.Global;
import Utility.AuditTrial;

 public class module_data_DataModel{

        private String _uuid = "";
        public String getuuid(){
              return _uuid;
         }
        public void setuuid(String newValue){
              _uuid = newValue;
         }
        private String _uuid_variable = "";
        public String getuuid_variable(){
              return _uuid_variable;
         }
        public void setuuid_variable(String newValue){
              _uuid_variable = newValue;
         }
        private String _data_id = "";
        public String getdata_id(){
              return _data_id;
         }
        public void setdata_id(String newValue){
              _data_id = newValue;
         }
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
        private String _note = "";
        public String getnote(){
              return _note;
         }
        public void setnote(String newValue){
              _note = newValue;
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

        String TableName = "module_data";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where uuid='"+ _uuid +"' "))
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
                 contentValues.put("uuid", _uuid);
                 contentValues.put("uuid_variable", _uuid_variable);
                 contentValues.put("data_id", _data_id);
                 contentValues.put("variable_data", _variable_data);
                 contentValues.put("data_desc", _data_desc);
                 contentValues.put("status", _status);
                 contentValues.put("note", _note);
                 contentValues.put("isdelete", 2);
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
                 contentValues.put("uuid", _uuid);
                 contentValues.put("uuid_variable", _uuid_variable);
                 contentValues.put("data_id", _data_id);
                 contentValues.put("variable_data", _variable_data);
                 contentValues.put("data_desc", _data_desc);
                 contentValues.put("status", _status);
                 contentValues.put("note", _note);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "uuid", (""+ _uuid +""), contentValues);

                 manageAudit(context,this,AuditTrial.KEY_UPDATE);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        int Count = 0;
        private int _Count = 0;
        public int getCount(){ return _Count; }

        @SuppressLint("Range")
        public List<module_data_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<module_data_DataModel> data = new ArrayList<module_data_DataModel>();
            module_data_DataModel d = new module_data_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new module_data_DataModel();
                d._Count = Count;
                d._uuid = cur.getString(cur.getColumnIndex("uuid"));
                d._uuid_variable = cur.getString(cur.getColumnIndex("uuid_variable"));
                d._data_id = cur.getString(cur.getColumnIndex("data_id"));
                d._variable_data = cur.getString(cur.getColumnIndex("variable_data"));
                d._data_desc = cur.getString(cur.getColumnIndex("data_desc"));
                d._status = cur.getString(cur.getColumnIndex("status"));
                d._note = cur.getString(cur.getColumnIndex("note"));
                data.add(d);

                manageAudit(context,d,AuditTrial.KEY_SELECT);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }



        static Map<String, Object> firstStateMap;
        public void manageAudit(Context context, module_data_DataModel ob, String key) {
            if (key.equalsIgnoreCase(AuditTrial.KEY_SELECT)) {
                //store old state
                firstStateMap = getMapData(ob);
            } else if (key.equalsIgnoreCase(AuditTrial.KEY_UPDATE)) {
                //store new state
                Map<String, Object> secondStateMap = getMapData(ob);
                AuditTrial auditTrial = new AuditTrial(context,TableName);
                // run audit
                if (firstStateMap != null && !firstStateMap.isEmpty() && secondStateMap != null && !secondStateMap.isEmpty()) {
                    auditTrial.audit(firstStateMap, secondStateMap);
                }
            }
        }



        /**
        * get object state
        * @param ob
        * @return
        */
        public Map<String, Object> getMapData(module_data_DataModel ob) {
            Map<String, Object> data = new HashMap<>();

            if (ob != null) {
                 data.put("uuid", ob._uuid);
                 data.put("uuid_variable", ob._uuid_variable);
                 data.put("data_id", ob._data_id);
                 data.put("variable_data", ob._variable_data);
                 data.put("data_desc", ob._data_desc);
                 data.put("status", ob._status);
                 data.put("note", ob._note);
            
            }
            return data;
        }
 }