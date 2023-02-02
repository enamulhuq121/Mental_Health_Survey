package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionH_DataModel{

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
        private String _H1 = "";
        public String getH1(){
              return _H1;
         }
        public void setH1(String newValue){
              _H1 = newValue;
         }
        private String _H2 = "";
        public String getH2(){
              return _H2;
         }
        public void setH2(String newValue){
              _H2 = newValue;
         }
        private String _H3 = "";
        public String getH3(){
              return _H3;
         }
        public void setH3(String newValue){
              _H3 = newValue;
         }
        private String _H4 = "";
        public String getH4(){
              return _H4;
         }
        public void setH4(String newValue){
              _H4 = newValue;
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

        String TableName = "SectionH";

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
                 contentValues.put("H1", _H1);
                 contentValues.put("H2", _H2);
                 contentValues.put("H3", _H3);
                 contentValues.put("H4", _H4);
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
                 contentValues.put("H1", _H1);
                 contentValues.put("H2", _H2);
                 contentValues.put("H3", _H3);
                 contentValues.put("H4", _H4);
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
        public List<SectionH_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionH_DataModel> data = new ArrayList<SectionH_DataModel>();
            SectionH_DataModel d = new SectionH_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionH_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._H1 = cur.getString(cur.getColumnIndex("H1"));
                d._H2 = cur.getString(cur.getColumnIndex("H2"));
                d._H3 = cur.getString(cur.getColumnIndex("H3"));
                d._H4 = cur.getString(cur.getColumnIndex("H4"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }