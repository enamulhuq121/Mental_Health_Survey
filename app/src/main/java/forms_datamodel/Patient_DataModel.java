package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class Patient_DataModel{

        private String _PatientID = "";
        public String getPatientID(){
              return _PatientID;
         }
        public void setPatientID(String newValue){
              _PatientID = newValue;
         }
        private String _FacilityID = "";
        public String getFacilityID(){
              return _FacilityID;
         }
        public void setFacilityID(String newValue){
              _FacilityID = newValue;
         }
        private String _reg_date = "";
        public String getreg_date(){
              return _reg_date;
         }
        public void setreg_date(String newValue){
              _reg_date = newValue;
         }
        private String _reg_time = "";
        public String getreg_time(){
              return _reg_time;
         }
        public void setreg_time(String newValue){
              _reg_time = newValue;
         }
        private String _pat_name = "";
        public String getpat_name(){
              return _pat_name;
         }
        public void setpat_name(String newValue){
              _pat_name = newValue;
         }
        private String _pat_age = "";
        public String getpat_age(){
              return _pat_age;
         }
        public void setpat_age(String newValue){
              _pat_age = newValue;
         }
        private String _mobile = "";
        public String getmobile(){
              return _mobile;
         }
        public void setmobile(String newValue){
              _mobile = newValue;
         }

     private String _ProvID = "";
     public String getProvID(){
         return _ProvID;
     }
     public void setProvID(String newValue){
         _ProvID = newValue;
     }

        private String _recv_service = "";
        public String getrecv_service(){
              return _recv_service;
         }
        public void setrecv_service(String newValue){
              _recv_service = newValue;
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

        String TableName = "Patient";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where PatientID='"+ _PatientID +"' and FacilityID='"+ _FacilityID +"' "))
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
                 contentValues.put("PatientID", _PatientID);
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("reg_date", _reg_date);
                 contentValues.put("reg_time", _reg_time);
                 contentValues.put("pat_name", _pat_name);
                 contentValues.put("pat_age", _pat_age);
                 contentValues.put("mobile", _mobile);
                 contentValues.put("recv_service", _recv_service);
                  contentValues.put("ProvID", _ProvID);
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
                 contentValues.put("PatientID", _PatientID);
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("reg_date", _reg_date);
                 contentValues.put("reg_time", _reg_time);
                 contentValues.put("pat_name", _pat_name);
                 contentValues.put("pat_age", _pat_age);
                 contentValues.put("mobile", _mobile);
                  contentValues.put("ProvID", _ProvID);
                 contentValues.put("recv_service", _recv_service);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "PatientID,FacilityID", (""+ _PatientID +", "+ _FacilityID +""), contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

          int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
        public List<Patient_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Patient_DataModel> data = new ArrayList<Patient_DataModel>();
            Patient_DataModel d = new Patient_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new Patient_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._reg_date = cur.getString(cur.getColumnIndex("reg_date"));
                d._reg_time = cur.getString(cur.getColumnIndex("reg_time"));
                d._pat_name = cur.getString(cur.getColumnIndex("pat_name"));
                d._pat_age = cur.getString(cur.getColumnIndex("pat_age"));
                d._mobile = cur.getString(cur.getColumnIndex("mobile"));
                d._recv_service = cur.getString(cur.getColumnIndex("recv_service"));
                d._ProvID = cur.getString(cur.getColumnIndex("ProvID"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }