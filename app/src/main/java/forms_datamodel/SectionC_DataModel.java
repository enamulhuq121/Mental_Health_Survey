package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SectionC_DataModel{

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
        private String _PatientCat = "";
        public String getPatientCat(){
              return _PatientCat;
         }
        public void setPatientCat(String newValue){
              _PatientCat = newValue;
         }
        private String _C1 = "";
        public String getC1(){
              return _C1;
         }
        public void setC1(String newValue){
              _C1 = newValue;
         }
        private String _C2 = "";
        public String getC2(){
              return _C2;
         }
        public void setC2(String newValue){
              _C2 = newValue;
         }
        private String _C3 = "";
        public String getC3(){
              return _C3;
         }
        public void setC3(String newValue){
              _C3 = newValue;
         }
        private String _C4 = "";
        public String getC4(){
              return _C4;
         }
        public void setC4(String newValue){
              _C4 = newValue;
         }
        private String _C5 = "";
        public String getC5(){
              return _C5;
         }
        public void setC5(String newValue){
              _C5 = newValue;
         }
        private String _C6 = "";
        public String getC6(){
              return _C6;
         }
        public void setC6(String newValue){
              _C6 = newValue;
         }
        private String _C7 = "";
        public String getC7(){
              return _C7;
         }
        public void setC7(String newValue){
              _C7 = newValue;
         }
        private String _C8a = "";
        public String getC8a(){
              return _C8a;
         }
        public void setC8a(String newValue){
              _C8a = newValue;
         }
        private String _C8b = "";
        public String getC8b(){
              return _C8b;
         }
        public void setC8b(String newValue){
              _C8b = newValue;
         }
        private String _C8c = "";
        public String getC8c(){
              return _C8c;
         }
        public void setC8c(String newValue){
              _C8c = newValue;
         }
        private String _C8d = "";
        public String getC8d(){
              return _C8d;
         }
        public void setC8d(String newValue){
              _C8d = newValue;
         }
        private String _C8e = "";
        public String getC8e(){
              return _C8e;
         }
        public void setC8e(String newValue){
              _C8e = newValue;
         }
        private String _C8f = "";
        public String getC8f(){
              return _C8f;
         }
        public void setC8f(String newValue){
              _C8f = newValue;
         }
        private String _C8g = "";
        public String getC8g(){
              return _C8g;
         }
        public void setC8g(String newValue){
              _C8g = newValue;
         }
        private String _C8h = "";
        public String getC8h(){
              return _C8h;
         }
        public void setC8h(String newValue){
              _C8h = newValue;
         }
        private String _C8i = "";
        public String getC8i(){
              return _C8i;
         }
        public void setC8i(String newValue){
              _C8i = newValue;
         }
        private String _C8j = "";
        public String getC8j(){
              return _C8j;
         }
        public void setC8j(String newValue){
              _C8j = newValue;
         }
        private String _C8k = "";
        public String getC8k(){
              return _C8k;
         }
        public void setC8k(String newValue){
              _C8k = newValue;
         }
        private String _C8l = "";
        public String getC8l(){
              return _C8l;
         }
        public void setC8l(String newValue){
              _C8l = newValue;
         }
        private String _C8m = "";
        public String getC8m(){
              return _C8m;
         }
        public void setC8m(String newValue){
              _C8m = newValue;
         }
        private String _C8n = "";
        public String getC8n(){
              return _C8n;
         }
        public void setC8n(String newValue){
              _C8n = newValue;
         }
        private String _C8o = "";
        public String getC8o(){
              return _C8o;
         }
        public void setC8o(String newValue){
              _C8o = newValue;
         }
        private String _C8p = "";
        public String getC8p(){
              return _C8p;
         }
        public void setC8p(String newValue){
              _C8p = newValue;
         }
        private String _C8q = "";
        public String getC8q(){
              return _C8q;
         }
        public void setC8q(String newValue){
              _C8q = newValue;
         }
        private String _C8x = "";
        public String getC8x(){
              return _C8x;
         }
        public void setC8x(String newValue){
              _C8x = newValue;
         }
        private String _C8xSp = "";
        public String getC8xSp(){
              return _C8xSp;
         }
        public void setC8xSp(String newValue){
              _C8xSp = newValue;
         }
        private String _C8z = "";
        public String getC8z(){
              return _C8z;
         }
        public void setC8z(String newValue){
              _C8z = newValue;
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

        String TableName = "SectionC";

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
                 contentValues.put("PatientCat", _PatientCat);
                 contentValues.put("C1", _C1);
                 contentValues.put("C2", _C2);
                 contentValues.put("C3", _C3);
                 contentValues.put("C4", _C4);
                 contentValues.put("C5", _C5);
                 contentValues.put("C6", _C6);
                 contentValues.put("C7", _C7);
                 contentValues.put("C8a", _C8a);
                 contentValues.put("C8b", _C8b);
                 contentValues.put("C8c", _C8c);
                 contentValues.put("C8d", _C8d);
                 contentValues.put("C8e", _C8e);
                 contentValues.put("C8f", _C8f);
                 contentValues.put("C8g", _C8g);
                 contentValues.put("C8h", _C8h);
                 contentValues.put("C8i", _C8i);
                 contentValues.put("C8j", _C8j);
                 contentValues.put("C8k", _C8k);
                 contentValues.put("C8l", _C8l);
                 contentValues.put("C8m", _C8m);
                 contentValues.put("C8n", _C8n);
                 contentValues.put("C8o", _C8o);
                 contentValues.put("C8p", _C8p);
                 contentValues.put("C8q", _C8q);
                 contentValues.put("C8x", _C8x);
                 contentValues.put("C8xSp", _C8xSp);
                 contentValues.put("C8z", _C8z);
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
                 contentValues.put("PatientCat", _PatientCat);
                 contentValues.put("C1", _C1);
                 contentValues.put("C2", _C2);
                 contentValues.put("C3", _C3);
                 contentValues.put("C4", _C4);
                 contentValues.put("C5", _C5);
                 contentValues.put("C6", _C6);
                 contentValues.put("C7", _C7);
                 contentValues.put("C8a", _C8a);
                 contentValues.put("C8b", _C8b);
                 contentValues.put("C8c", _C8c);
                 contentValues.put("C8d", _C8d);
                 contentValues.put("C8e", _C8e);
                 contentValues.put("C8f", _C8f);
                 contentValues.put("C8g", _C8g);
                 contentValues.put("C8h", _C8h);
                 contentValues.put("C8i", _C8i);
                 contentValues.put("C8j", _C8j);
                 contentValues.put("C8k", _C8k);
                 contentValues.put("C8l", _C8l);
                 contentValues.put("C8m", _C8m);
                 contentValues.put("C8n", _C8n);
                 contentValues.put("C8o", _C8o);
                 contentValues.put("C8p", _C8p);
                 contentValues.put("C8q", _C8q);
                 contentValues.put("C8x", _C8x);
                 contentValues.put("C8xSp", _C8xSp);
                 contentValues.put("C8z", _C8z);
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
        public List<SectionC_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SectionC_DataModel> data = new ArrayList<SectionC_DataModel>();
            SectionC_DataModel d = new SectionC_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SectionC_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._PatientCat = cur.getString(cur.getColumnIndex("PatientCat"));
                d._C1 = cur.getString(cur.getColumnIndex("C1"));
                d._C2 = cur.getString(cur.getColumnIndex("C2"));
                d._C3 = cur.getString(cur.getColumnIndex("C3"));
                d._C4 = cur.getString(cur.getColumnIndex("C4"));
                d._C5 = cur.getString(cur.getColumnIndex("C5"));
                d._C6 = cur.getString(cur.getColumnIndex("C6"));
                d._C7 = cur.getString(cur.getColumnIndex("C7"));
                d._C8a = cur.getString(cur.getColumnIndex("C8a"));
                d._C8b = cur.getString(cur.getColumnIndex("C8b"));
                d._C8c = cur.getString(cur.getColumnIndex("C8c"));
                d._C8d = cur.getString(cur.getColumnIndex("C8d"));
                d._C8e = cur.getString(cur.getColumnIndex("C8e"));
                d._C8f = cur.getString(cur.getColumnIndex("C8f"));
                d._C8g = cur.getString(cur.getColumnIndex("C8g"));
                d._C8h = cur.getString(cur.getColumnIndex("C8h"));
                d._C8i = cur.getString(cur.getColumnIndex("C8i"));
                d._C8j = cur.getString(cur.getColumnIndex("C8j"));
                d._C8k = cur.getString(cur.getColumnIndex("C8k"));
                d._C8l = cur.getString(cur.getColumnIndex("C8l"));
                d._C8m = cur.getString(cur.getColumnIndex("C8m"));
                d._C8n = cur.getString(cur.getColumnIndex("C8n"));
                d._C8o = cur.getString(cur.getColumnIndex("C8o"));
                d._C8p = cur.getString(cur.getColumnIndex("C8p"));
                d._C8q = cur.getString(cur.getColumnIndex("C8q"));
                d._C8x = cur.getString(cur.getColumnIndex("C8x"));
                d._C8xSp = cur.getString(cur.getColumnIndex("C8xSp"));
                d._C8z = cur.getString(cur.getColumnIndex("C8z"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }