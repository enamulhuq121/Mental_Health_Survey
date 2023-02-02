package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionB_DataModel{

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
        private String _B1 = "";
        public String getB1(){
              return _B1;
         }
        public void setB1(String newValue){
              _B1 = newValue;
         }
        private String _B2 = "";
        public String getB2(){
              return _B2;
         }
        public void setB2(String newValue){
              _B2 = newValue;
         }
        private String _B3 = "";
        public String getB3(){
              return _B3;
         }
        public void setB3(String newValue){
              _B3 = newValue;
         }
        private String _B4 = "";
        public String getB4(){
              return _B4;
         }
        public void setB4(String newValue){
              _B4 = newValue;
         }
        private String _B5 = "";
        public String getB5(){
              return _B5;
         }
        public void setB5(String newValue){
              _B5 = newValue;
         }
        private String _B6 = "";
        public String getB6(){
              return _B6;
         }
        public void setB6(String newValue){
              _B6 = newValue;
         }
        private String _B7 = "";
        public String getB7(){
              return _B7;
         }
        public void setB7(String newValue){
              _B7 = newValue;
         }
        private String _BScore = "";
        public String getBScore(){
              return _BScore;
         }
        public void setBScore(String newValue){
              _BScore = newValue;
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

        String TableName = "SectionB";

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
                 contentValues.put("B1", _B1);
                 contentValues.put("B2", _B2);
                 contentValues.put("B3", _B3);
                 contentValues.put("B4", _B4);
                 contentValues.put("B5", _B5);
                 contentValues.put("B6", _B6);
                 contentValues.put("B7", _B7);
                 contentValues.put("BScore", _BScore);
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
                 contentValues.put("B1", _B1);
                 contentValues.put("B2", _B2);
                 contentValues.put("B3", _B3);
                 contentValues.put("B4", _B4);
                 contentValues.put("B5", _B5);
                 contentValues.put("B6", _B6);
                 contentValues.put("B7", _B7);
                 contentValues.put("BScore", _BScore);
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
        public List<SectionB_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionB_DataModel> data = new ArrayList<SectionB_DataModel>();
            SectionB_DataModel d = new SectionB_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionB_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._B1 = cur.getString(cur.getColumnIndex("B1"));
                d._B2 = cur.getString(cur.getColumnIndex("B2"));
                d._B3 = cur.getString(cur.getColumnIndex("B3"));
                d._B4 = cur.getString(cur.getColumnIndex("B4"));
                d._B5 = cur.getString(cur.getColumnIndex("B5"));
                d._B6 = cur.getString(cur.getColumnIndex("B6"));
                d._B7 = cur.getString(cur.getColumnIndex("B7"));
                d._BScore = cur.getString(cur.getColumnIndex("BScore"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }