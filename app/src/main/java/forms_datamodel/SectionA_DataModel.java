package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionA_DataModel{

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
        private String _A1 = "";
        public String getA1(){
              return _A1;
         }
        public void setA1(String newValue){
              _A1 = newValue;
         }
        private String _A2 = "";
        public String getA2(){
              return _A2;
         }
        public void setA2(String newValue){
              _A2 = newValue;
         }
        private String _A3 = "";
        public String getA3(){
              return _A3;
         }
        public void setA3(String newValue){
              _A3 = newValue;
         }
        private String _A4 = "";
        public String getA4(){
              return _A4;
         }
        public void setA4(String newValue){
              _A4 = newValue;
         }
        private String _A5 = "";
        public String getA5(){
              return _A5;
         }
        public void setA5(String newValue){
              _A5 = newValue;
         }
        private String _A6 = "";
        public String getA6(){
              return _A6;
         }
        public void setA6(String newValue){
              _A6 = newValue;
         }
        private String _A7 = "";
        public String getA7(){
              return _A7;
         }
        public void setA7(String newValue){
              _A7 = newValue;
         }
        private String _A9 = "";
        public String getA9(){
              return _A9;
         }
        public void setA9(String newValue){
              _A9 = newValue;
         }
        private String _AScore = "";
        public String getAScore(){
              return _AScore;
         }
        public void setAScore(String newValue){
              _AScore = newValue;
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

        String TableName = "SectionA";

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
                 contentValues.put("A1", _A1);
                 contentValues.put("A2", _A2);
                 contentValues.put("A3", _A3);
                 contentValues.put("A4", _A4);
                 contentValues.put("A5", _A5);
                 contentValues.put("A6", _A6);
                 contentValues.put("A7", _A7);
                 contentValues.put("A9", _A9);
                 contentValues.put("AScore", _AScore);
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
                 contentValues.put("A1", _A1);
                 contentValues.put("A2", _A2);
                 contentValues.put("A3", _A3);
                 contentValues.put("A4", _A4);
                 contentValues.put("A5", _A5);
                 contentValues.put("A6", _A6);
                 contentValues.put("A7", _A7);
                 contentValues.put("A9", _A9);
                 contentValues.put("AScore", _AScore);
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
        public List<SectionA_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionA_DataModel> data = new ArrayList<SectionA_DataModel>();
            SectionA_DataModel d = new SectionA_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionA_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._A1 = cur.getString(cur.getColumnIndex("A1"));
                d._A2 = cur.getString(cur.getColumnIndex("A2"));
                d._A3 = cur.getString(cur.getColumnIndex("A3"));
                d._A4 = cur.getString(cur.getColumnIndex("A4"));
                d._A5 = cur.getString(cur.getColumnIndex("A5"));
                d._A6 = cur.getString(cur.getColumnIndex("A6"));
                d._A7 = cur.getString(cur.getColumnIndex("A7"));
                d._A9 = cur.getString(cur.getColumnIndex("A9"));
                d._AScore = cur.getString(cur.getColumnIndex("AScore"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }