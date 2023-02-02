package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionD_DataModel{

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
        private String _D1 = "";
        public String getD1(){
              return _D1;
         }
        public void setD1(String newValue){
              _D1 = newValue;
         }
        private String _D2 = "";
        public String getD2(){
              return _D2;
         }
        public void setD2(String newValue){
              _D2 = newValue;
         }
        private String _D2Oth = "";
        public String getD2Oth(){
              return _D2Oth;
         }
        public void setD2Oth(String newValue){
              _D2Oth = newValue;
         }
        private String _D3 = "";
        public String getD3(){
              return _D3;
         }
        public void setD3(String newValue){
              _D3 = newValue;
         }
        private String _D3Oth = "";
        public String getD3Oth(){
              return _D3Oth;
         }
        public void setD3Oth(String newValue){
              _D3Oth = newValue;
         }
        private String _D4 = "";
        public String getD4(){
              return _D4;
         }
        public void setD4(String newValue){
              _D4 = newValue;
         }
        private String _D4Oth = "";
        public String getD4Oth(){
              return _D4Oth;
         }
        public void setD4Oth(String newValue){
              _D4Oth = newValue;
         }
        private String _D5 = "";
        public String getD5(){
              return _D5;
         }
        public void setD5(String newValue){
              _D5 = newValue;
         }
        private String _D6 = "";
        public String getD6(){
              return _D6;
         }
        public void setD6(String newValue){
              _D6 = newValue;
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

        String TableName = "SectionD";

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
                 contentValues.put("D1", _D1);
                 contentValues.put("D2", _D2);
                 contentValues.put("D2Oth", _D2Oth);
                 contentValues.put("D3", _D3);
                 contentValues.put("D3Oth", _D3Oth);
                 contentValues.put("D4", _D4);
                 contentValues.put("D4Oth", _D4Oth);
                 contentValues.put("D5", _D5);
                 contentValues.put("D6", _D6);
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
                 contentValues.put("D1", _D1);
                 contentValues.put("D2", _D2);
                 contentValues.put("D2Oth", _D2Oth);
                 contentValues.put("D3", _D3);
                 contentValues.put("D3Oth", _D3Oth);
                 contentValues.put("D4", _D4);
                 contentValues.put("D4Oth", _D4Oth);
                 contentValues.put("D5", _D5);
                 contentValues.put("D6", _D6);
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
        public List<SectionD_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionD_DataModel> data = new ArrayList<SectionD_DataModel>();
            SectionD_DataModel d = new SectionD_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionD_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._D1 = cur.getString(cur.getColumnIndex("D1"));
                d._D2 = cur.getString(cur.getColumnIndex("D2"));
                d._D2Oth = cur.getString(cur.getColumnIndex("D2Oth"));
                d._D3 = cur.getString(cur.getColumnIndex("D3"));
                d._D3Oth = cur.getString(cur.getColumnIndex("D3Oth"));
                d._D4 = cur.getString(cur.getColumnIndex("D4"));
                d._D4Oth = cur.getString(cur.getColumnIndex("D4Oth"));
                d._D5 = cur.getString(cur.getColumnIndex("D5"));
                d._D6 = cur.getString(cur.getColumnIndex("D6"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }