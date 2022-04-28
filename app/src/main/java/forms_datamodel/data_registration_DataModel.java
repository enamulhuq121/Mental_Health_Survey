package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class data_registration_DataModel{

        private String _userid = "";
        public String getuserid(){
              return _userid;
         }
        public void setuserid(String newValue){
              _userid = newValue;
         }
        private String _username = "";
        public String getusername(){
              return _username;
         }
        public void setusername(String newValue){
              _username = newValue;
         }
        private String _age = "";
        public String getage(){
              return _age;
         }
        public void setage(String newValue){
              _age = newValue;
         }
        private String _sex = "";
        public String getsex(){
              return _sex;
         }
        public void setsex(String newValue){
              _sex = newValue;
         }
        private String _ocp = "";
        public String getocp(){
              return _ocp;
         }
        public void setocp(String newValue){
              _ocp = newValue;
         }
        private String _institute = "";
        public String getinstitute(){
              return _institute;
         }
        public void setinstitute(String newValue){
              _institute = newValue;
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

        String TableName = "data_registration";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where userid='"+ _userid +"' "))
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
                 contentValues.put("userid", _userid);
                 contentValues.put("username", _username);
                 contentValues.put("age", _age);
                 contentValues.put("sex", _sex);
                 contentValues.put("ocp", _ocp);
                 contentValues.put("institute", _institute);
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
                 contentValues.put("userid", _userid);
                 contentValues.put("username", _username);
                 contentValues.put("age", _age);
                 contentValues.put("sex", _sex);
                 contentValues.put("ocp", _ocp);
                 contentValues.put("institute", _institute);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "userid", (""+ _userid +""), contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

          int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
        public List<data_registration_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<data_registration_DataModel> data = new ArrayList<data_registration_DataModel>();
            data_registration_DataModel d = new data_registration_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new data_registration_DataModel();
                d._Count = Count;
                d._userid = cur.getString(cur.getColumnIndex("userid"));
                d._username = cur.getString(cur.getColumnIndex("username"));
                d._age = cur.getString(cur.getColumnIndex("age"));
                d._sex = cur.getString(cur.getColumnIndex("sex"));
                d._ocp = cur.getString(cur.getColumnIndex("ocp"));
                d._institute = cur.getString(cur.getColumnIndex("institute"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }