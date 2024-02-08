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

 public class module_variable_DataModel{

        private String _uuid = "";
        public String getuuid(){
              return _uuid;
         }
        public void setuuid(String newValue){
              _uuid = newValue;
         }
        private String _uuid_module = "";
        public String getuuid_module(){
              return _uuid_module;
         }
        public void setuuid_module(String newValue){
              _uuid_module = newValue;
         }
        private String _variable_name = "";
        public String getvariable_name(){
              return _variable_name;
         }
        public void setvariable_name(String newValue){
              _variable_name = newValue;
         }
        private String _variable_serial = "";
        public String getvariable_serial(){
              return _variable_serial;
         }
        public void setvariable_serial(String newValue){
              _variable_serial = newValue;
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
        private String _min_value = "";
        public String getmin_value(){
              return _min_value;
         }
        public void setmin_value(String newValue){
              _min_value = newValue;
         }
        private String _max_value = "";
        public String getmax_value(){
              return _max_value;
         }
        public void setmax_value(String newValue){
              _max_value = newValue;
         }
        private String _exception = "";
        public String getexception(){
              return _exception;
         }
        public void setexception(String newValue){
              _exception = newValue;
         }
        private String _instruction = "";
        public String getinstruction(){
              return _instruction;
         }
        public void setinstruction(String newValue){
              _instruction = newValue;
         }
        private String _note_visible = "";
        public String getnote_visible(){
              return _note_visible;
         }
        public void setnote_visible(String newValue){
              _note_visible = newValue;
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
        private String _section = "";
        public String getsection(){
              return _section;
         }
        public void setsection(String newValue){
              _section = newValue;
         }

        private String _data = "";
        public String getdata(){
         return _data;
     }
        public void setdata(String newValue){
            _data = newValue;
     }

     private String _uuid_data = "";
     public String getuuid_data(){
         return _uuid_data;
     }
     public void setuuid_data(String newValue){
         _uuid_data = newValue;
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

        String TableName = "module_variable";

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
                 contentValues.put("uuid_module", _uuid_module);
                 contentValues.put("variable_name", _variable_name);
                 contentValues.put("variable_serial", _variable_serial);
                 contentValues.put("variable_desc", _variable_desc);
                 contentValues.put("variable_seq", _variable_seq);
                 contentValues.put("control_type", _control_type);
                 contentValues.put("variable_option", _variable_option);
                 contentValues.put("variable_length", _variable_length);
                 contentValues.put("data_type", _data_type);
                 contentValues.put("skip_rule", _skip_rule);
                 contentValues.put("min_value", _min_value);
                 contentValues.put("max_value", _max_value);
                 contentValues.put("exception", _exception);
                 contentValues.put("instruction", _instruction);
                 contentValues.put("note_visible", _note_visible);
                 contentValues.put("color", _color);
                 contentValues.put("active", _active);
                 contentValues.put("section", _section);
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
                 contentValues.put("uuid_module", _uuid_module);
                 contentValues.put("variable_name", _variable_name);
                 contentValues.put("variable_serial", _variable_serial);
                 contentValues.put("variable_desc", _variable_desc);
                 contentValues.put("variable_seq", _variable_seq);
                 contentValues.put("control_type", _control_type);
                 contentValues.put("variable_option", _variable_option);
                 contentValues.put("variable_length", _variable_length);
                 contentValues.put("data_type", _data_type);
                 contentValues.put("skip_rule", _skip_rule);
                 contentValues.put("min_value", _min_value);
                 contentValues.put("max_value", _max_value);
                 contentValues.put("exception", _exception);
                 contentValues.put("instruction", _instruction);
                 contentValues.put("note_visible", _note_visible);
                 contentValues.put("color", _color);
                 contentValues.put("active", _active);
                 contentValues.put("section", _section);
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
        public List<module_variable_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<module_variable_DataModel> data = new ArrayList<module_variable_DataModel>();
            module_variable_DataModel d = new module_variable_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new module_variable_DataModel();
                d._Count = Count;
                d._uuid = cur.getString(cur.getColumnIndex("uuid"));
                d._uuid_module = cur.getString(cur.getColumnIndex("uuid_module"));
                d._variable_name = cur.getString(cur.getColumnIndex("variable_name"));
                d._variable_serial = cur.getString(cur.getColumnIndex("variable_serial"));
                d._variable_desc = cur.getString(cur.getColumnIndex("variable_desc"));
                d._variable_seq = cur.getString(cur.getColumnIndex("variable_seq"));
                d._control_type = cur.getString(cur.getColumnIndex("control_type"));
                d._variable_option = cur.getString(cur.getColumnIndex("variable_option"));
                d._variable_length = cur.getString(cur.getColumnIndex("variable_length"));
                d._data_type = cur.getString(cur.getColumnIndex("data_type"));
                d._skip_rule = cur.getString(cur.getColumnIndex("skip_rule"));
                d._min_value = cur.getString(cur.getColumnIndex("min_value"));
                d._max_value = cur.getString(cur.getColumnIndex("max_value"));
                d._exception = cur.getString(cur.getColumnIndex("exception"));
                d._instruction = cur.getString(cur.getColumnIndex("instruction"));
                d._note_visible = cur.getString(cur.getColumnIndex("note_visible"));
                d._color = cur.getString(cur.getColumnIndex("color"));
                d._active = cur.getString(cur.getColumnIndex("active"));
                d._section = cur.getString(cur.getColumnIndex("section"));
                d._data = cur.getString(cur.getColumnIndex("data"));
                d._uuid_data = cur.getString(cur.getColumnIndex("uuid_data"));
                data.add(d);

                manageAudit(context,d,AuditTrial.KEY_SELECT);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }



        static Map<String, Object> firstStateMap;
        public void manageAudit(Context context, module_variable_DataModel ob, String key) {
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
        public Map<String, Object> getMapData(module_variable_DataModel ob) {
            Map<String, Object> data = new HashMap<>();

            if (ob != null) {
                 data.put("uuid", ob._uuid);
                 data.put("uuid_module", ob._uuid_module);
                 data.put("variable_name", ob._variable_name);
                 data.put("variable_serial", ob._variable_serial);
                 data.put("variable_desc", ob._variable_desc);
                 data.put("variable_seq", ob._variable_seq);
                 data.put("control_type", ob._control_type);
                 data.put("variable_option", ob._variable_option);
                 data.put("variable_length", ob._variable_length);
                 data.put("data_type", ob._data_type);
                 data.put("skip_rule", ob._skip_rule);
                 data.put("min_value", ob._min_value);
                 data.put("max_value", ob._max_value);
                 data.put("exception", ob._exception);
                 data.put("instruction", ob._instruction);
                 data.put("note_visible", ob._note_visible);
                 data.put("color", ob._color);
                 data.put("active", ob._active);
                 data.put("section", ob._section);
            
            }
            return data;
        }
 }