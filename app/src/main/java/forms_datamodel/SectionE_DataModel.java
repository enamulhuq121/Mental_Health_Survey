package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionE_DataModel{

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
        private String _E1 = "";
        public String getE1(){
              return _E1;
         }
        public void setE1(String newValue){
              _E1 = newValue;
         }
        private String _E1Dk = "";
        public String getE1Dk(){
              return _E1Dk;
         }
        public void setE1Dk(String newValue){
              _E1Dk = newValue;
         }
        private String _E2 = "";
        public String getE2(){
              return _E2;
         }
        public void setE2(String newValue){
              _E2 = newValue;
         }
        private String _E2Dk = "";
        public String getE2Dk(){
              return _E2Dk;
         }
        public void setE2Dk(String newValue){
              _E2Dk = newValue;
         }
        private String _E3 = "";
        public String getE3(){
              return _E3;
         }
        public void setE3(String newValue){
              _E3 = newValue;
         }
        private String _E4 = "";
        public String getE4(){
              return _E4;
         }
        public void setE4(String newValue){
              _E4 = newValue;
         }
        private String _E5 = "";
        public String getE5(){
              return _E5;
         }
        public void setE5(String newValue){
              _E5 = newValue;
         }
        private String _E6 = "";
        public String getE6(){
              return _E6;
         }
        public void setE6(String newValue){
              _E6 = newValue;
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

        String TableName = "SectionE";

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
                 contentValues.put("E1", _E1);
                 contentValues.put("E1Dk", _E1Dk);
                 contentValues.put("E2", _E2);
                 contentValues.put("E2Dk", _E2Dk);
                 contentValues.put("E3", _E3);
                 contentValues.put("E4", _E4);
                 contentValues.put("E5", _E5);
                 contentValues.put("E6", _E6);
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
                 contentValues.put("E1", _E1);
                 contentValues.put("E1Dk", _E1Dk);
                 contentValues.put("E2", _E2);
                 contentValues.put("E2Dk", _E2Dk);
                 contentValues.put("E3", _E3);
                 contentValues.put("E4", _E4);
                 contentValues.put("E5", _E5);
                 contentValues.put("E6", _E6);
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
        public List<SectionE_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionE_DataModel> data = new ArrayList<SectionE_DataModel>();
            SectionE_DataModel d = new SectionE_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionE_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._E1 = cur.getString(cur.getColumnIndex("E1"));
                d._E1Dk = cur.getString(cur.getColumnIndex("E1Dk"));
                d._E2 = cur.getString(cur.getColumnIndex("E2"));
                d._E2Dk = cur.getString(cur.getColumnIndex("E2Dk"));
                d._E3 = cur.getString(cur.getColumnIndex("E3"));
                d._E4 = cur.getString(cur.getColumnIndex("E4"));
                d._E5 = cur.getString(cur.getColumnIndex("E5"));
                d._E6 = cur.getString(cur.getColumnIndex("E6"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }