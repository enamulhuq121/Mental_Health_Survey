package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class Provider_DataModel{

        private String _FacilityID = "";
        public String getFacilityID(){
              return _FacilityID;
         }
        public void setFacilityID(String newValue){
              _FacilityID = newValue;
         }
        private String _ProviderID = "";
        public String getProviderID(){
              return _ProviderID;
         }
        public void setProviderID(String newValue){
              _ProviderID = newValue;
         }
        private String _Name = "";
        public String getName(){
              return _Name;
         }
        public void setName(String newValue){
              _Name = newValue;
         }
        private String _ProfeTitle = "";
        public String getProfeTitle(){
              return _ProfeTitle;
         }
        public void setProfeTitle(String newValue){
              _ProfeTitle = newValue;
         }
        private String _Ward = "";
        public String getWard(){
              return _Ward;
         }
        public void setWard(String newValue){
              _Ward = newValue;
         }
        private String _ExpeMonth = "";
        public String getExpeMonth(){
              return _ExpeMonth;
         }
        public void setExpeMonth(String newValue){
              _ExpeMonth = newValue;
         }
        private String _ExpeYear = "";
        public String getExpeYear(){
              return _ExpeYear;
         }
        public void setExpeYear(String newValue){
              _ExpeYear = newValue;
         }
        private String _ExpeFaciMonth = "";
        public String getExpeFaciMonth(){
              return _ExpeFaciMonth;
         }
        public void setExpeFaciMonth(String newValue){
              _ExpeFaciMonth = newValue;
         }
        private String _ExpeFaciYera = "";
        public String getExpeFaciYera(){
              return _ExpeFaciYera;
         }
        public void setExpeFaciYera(String newValue){
              _ExpeFaciYera = newValue;
         }
        private String _ExpeWardMonth = "";
        public String getExpeWardMonth(){
              return _ExpeWardMonth;
         }
        public void setExpeWardMonth(String newValue){
              _ExpeWardMonth = newValue;
         }
        private String _ExpeWardYera = "";
        public String getExpeWardYera(){
              return _ExpeWardYera;
         }
        public void setExpeWardYera(String newValue){
              _ExpeWardYera = newValue;
         }
        private String _MobileNo = "";
        public String getMobileNo(){
              return _MobileNo;
         }
        public void setMobileNo(String newValue){
              _MobileNo = newValue;
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
        private String _A8 = "";
        public String getA8(){
              return _A8;
         }
        public void setA8(String newValue){
              _A8 = newValue;
         }
        private String _A9 = "";
        public String getA9(){
              return _A9;
         }
        public void setA9(String newValue){
              _A9 = newValue;
         }
        private String _A10 = "";
        public String getA10(){
              return _A10;
         }
        public void setA10(String newValue){
              _A10 = newValue;
         }
        private String _A11 = "";
        public String getA11(){
              return _A11;
         }
        public void setA11(String newValue){
              _A11 = newValue;
         }
        private String _A12 = "";
        public String getA12(){
              return _A12;
         }
        public void setA12(String newValue){
              _A12 = newValue;
         }
        private String _A13 = "";
        public String getA13(){
              return _A13;
         }
        public void setA13(String newValue){
              _A13 = newValue;
         }
        private String _A14 = "";
        public String getA14(){
              return _A14;
         }
        public void setA14(String newValue){
              _A14 = newValue;
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

        String TableName = "Provider";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where FacilityID='"+ _FacilityID +"' and ProviderID='"+ _ProviderID +"' "))
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
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("ProviderID", _ProviderID);
                 contentValues.put("Name", _Name);
                 contentValues.put("ProfeTitle", _ProfeTitle);
                 contentValues.put("Ward", _Ward);
                 contentValues.put("ExpeMonth", _ExpeMonth);
                 contentValues.put("ExpeYear", _ExpeYear);
                 contentValues.put("ExpeFaciMonth", _ExpeFaciMonth);
                 contentValues.put("ExpeFaciYera", _ExpeFaciYera);
                 contentValues.put("ExpeWardMonth", _ExpeWardMonth);
                 contentValues.put("ExpeWardYera", _ExpeWardYera);
                 contentValues.put("MobileNo", _MobileNo);
                 contentValues.put("A1", _A1);
                 contentValues.put("A2", _A2);
                 contentValues.put("A3", _A3);
                 contentValues.put("A4", _A4);
                 contentValues.put("A5", _A5);
                 contentValues.put("A6", _A6);
                 contentValues.put("A7", _A7);
                 contentValues.put("A8", _A8);
                 contentValues.put("A9", _A9);
                 contentValues.put("A10", _A10);
                 contentValues.put("A11", _A11);
                 contentValues.put("A12", _A12);
                 contentValues.put("A13", _A13);
                 contentValues.put("A14", _A14);
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
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("ProviderID", _ProviderID);
                 contentValues.put("Name", _Name);
                 contentValues.put("ProfeTitle", _ProfeTitle);
                 contentValues.put("Ward", _Ward);
                 contentValues.put("ExpeMonth", _ExpeMonth);
                 contentValues.put("ExpeYear", _ExpeYear);
                 contentValues.put("ExpeFaciMonth", _ExpeFaciMonth);
                 contentValues.put("ExpeFaciYera", _ExpeFaciYera);
                 contentValues.put("ExpeWardMonth", _ExpeWardMonth);
                 contentValues.put("ExpeWardYera", _ExpeWardYera);
                 contentValues.put("MobileNo", _MobileNo);
                 contentValues.put("A1", _A1);
                 contentValues.put("A2", _A2);
                 contentValues.put("A3", _A3);
                 contentValues.put("A4", _A4);
                 contentValues.put("A5", _A5);
                 contentValues.put("A6", _A6);
                 contentValues.put("A7", _A7);
                 contentValues.put("A8", _A8);
                 contentValues.put("A9", _A9);
                 contentValues.put("A10", _A10);
                 contentValues.put("A11", _A11);
                 contentValues.put("A12", _A12);
                 contentValues.put("A13", _A13);
                 contentValues.put("A14", _A14);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "FacilityID,ProviderID", (""+ _FacilityID +", "+ _ProviderID +""), contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

          int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
        public List<Provider_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Provider_DataModel> data = new ArrayList<Provider_DataModel>();
            Provider_DataModel d = new Provider_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new Provider_DataModel();
                d._Count = Count;
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._ProviderID = cur.getString(cur.getColumnIndex("ProviderID"));
                d._Name = cur.getString(cur.getColumnIndex("Name"));
                d._ProfeTitle = cur.getString(cur.getColumnIndex("ProfeTitle"));
                d._Ward = cur.getString(cur.getColumnIndex("Ward"));
                d._ExpeMonth = cur.getString(cur.getColumnIndex("ExpeMonth"));
                d._ExpeYear = cur.getString(cur.getColumnIndex("ExpeYear"));
                d._ExpeFaciMonth = cur.getString(cur.getColumnIndex("ExpeFaciMonth"));
                d._ExpeFaciYera = cur.getString(cur.getColumnIndex("ExpeFaciYera"));
                d._ExpeWardMonth = cur.getString(cur.getColumnIndex("ExpeWardMonth"));
                d._ExpeWardYera = cur.getString(cur.getColumnIndex("ExpeWardYera"));
                d._MobileNo = cur.getString(cur.getColumnIndex("MobileNo"));
                d._A1 = cur.getString(cur.getColumnIndex("A1"));
                d._A2 = cur.getString(cur.getColumnIndex("A2"));
                d._A3 = cur.getString(cur.getColumnIndex("A3"));
                d._A4 = cur.getString(cur.getColumnIndex("A4"));
                d._A5 = cur.getString(cur.getColumnIndex("A5"));
                d._A6 = cur.getString(cur.getColumnIndex("A6"));
                d._A7 = cur.getString(cur.getColumnIndex("A7"));
                d._A8 = cur.getString(cur.getColumnIndex("A8"));
                d._A9 = cur.getString(cur.getColumnIndex("A9"));
                d._A10 = cur.getString(cur.getColumnIndex("A10"));
                d._A11 = cur.getString(cur.getColumnIndex("A11"));
                d._A12 = cur.getString(cur.getColumnIndex("A12"));
                d._A13 = cur.getString(cur.getColumnIndex("A13"));
                d._A14 = cur.getString(cur.getColumnIndex("A14"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }