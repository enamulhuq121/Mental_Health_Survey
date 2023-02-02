package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionF_DataModel{

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
        private String _F1 = "";
        public String getF1(){
              return _F1;
         }
        public void setF1(String newValue){
              _F1 = newValue;
         }
        private String _F2 = "";
        public String getF2(){
              return _F2;
         }
        public void setF2(String newValue){
              _F2 = newValue;
         }
        private String _F3 = "";
        public String getF3(){
              return _F3;
         }
        public void setF3(String newValue){
              _F3 = newValue;
         }
        private String _F4 = "";
        public String getF4(){
              return _F4;
         }
        public void setF4(String newValue){
              _F4 = newValue;
         }
        private String _F5 = "";
        public String getF5(){
              return _F5;
         }
        public void setF5(String newValue){
              _F5 = newValue;
         }
        private String _F6 = "";
        public String getF6(){
              return _F6;
         }
        public void setF6(String newValue){
              _F6 = newValue;
         }
        private String _F7 = "";
        public String getF7(){
              return _F7;
         }
        public void setF7(String newValue){
              _F7 = newValue;
         }
        private String _F8 = "";
        public String getF8(){
              return _F8;
         }
        public void setF8(String newValue){
              _F8 = newValue;
         }
        private String _F9 = "";
        public String getF9(){
              return _F9;
         }
        public void setF9(String newValue){
              _F9 = newValue;
         }
        private String _F10 = "";
        public String getF10(){
              return _F10;
         }
        public void setF10(String newValue){
              _F10 = newValue;
         }
        private String _F11 = "";
        public String getF11(){
              return _F11;
         }
        public void setF11(String newValue){
              _F11 = newValue;
         }
        private String _F12 = "";
        public String getF12(){
              return _F12;
         }
        public void setF12(String newValue){
              _F12 = newValue;
         }
        private String _F13 = "";
        public String getF13(){
              return _F13;
         }
        public void setF13(String newValue){
              _F13 = newValue;
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

        String TableName = "SectionF";

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
                 contentValues.put("F1", _F1);
                 contentValues.put("F2", _F2);
                 contentValues.put("F3", _F3);
                 contentValues.put("F4", _F4);
                 contentValues.put("F5", _F5);
                 contentValues.put("F6", _F6);
                 contentValues.put("F7", _F7);
                 contentValues.put("F8", _F8);
                 contentValues.put("F9", _F9);
                 contentValues.put("F10", _F10);
                 contentValues.put("F11", _F11);
                 contentValues.put("F12", _F12);
                 contentValues.put("F13", _F13);
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
                 contentValues.put("F1", _F1);
                 contentValues.put("F2", _F2);
                 contentValues.put("F3", _F3);
                 contentValues.put("F4", _F4);
                 contentValues.put("F5", _F5);
                 contentValues.put("F6", _F6);
                 contentValues.put("F7", _F7);
                 contentValues.put("F8", _F8);
                 contentValues.put("F9", _F9);
                 contentValues.put("F10", _F10);
                 contentValues.put("F11", _F11);
                 contentValues.put("F12", _F12);
                 contentValues.put("F13", _F13);
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
        public List<SectionF_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionF_DataModel> data = new ArrayList<SectionF_DataModel>();
            SectionF_DataModel d = new SectionF_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionF_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._F1 = cur.getString(cur.getColumnIndex("F1"));
                d._F2 = cur.getString(cur.getColumnIndex("F2"));
                d._F3 = cur.getString(cur.getColumnIndex("F3"));
                d._F4 = cur.getString(cur.getColumnIndex("F4"));
                d._F5 = cur.getString(cur.getColumnIndex("F5"));
                d._F6 = cur.getString(cur.getColumnIndex("F6"));
                d._F7 = cur.getString(cur.getColumnIndex("F7"));
                d._F8 = cur.getString(cur.getColumnIndex("F8"));
                d._F9 = cur.getString(cur.getColumnIndex("F9"));
                d._F10 = cur.getString(cur.getColumnIndex("F10"));
                d._F11 = cur.getString(cur.getColumnIndex("F11"));
                d._F12 = cur.getString(cur.getColumnIndex("F12"));
                d._F13 = cur.getString(cur.getColumnIndex("F13"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }