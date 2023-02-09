package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class Women_DataModel{

        private String _WomenID = "";
        public String getWomenID(){
              return _WomenID;
         }
        public void setWomenID(String newValue){
              _WomenID = newValue;
         }
        private String _Q1 = "";
        public String getQ1(){
              return _Q1;
         }
        public void setQ1(String newValue){
              _Q1 = newValue;
         }
        private String _Q2 = "";
        public String getQ2(){
              return _Q2;
         }
        public void setQ2(String newValue){
              _Q2 = newValue;
         }
        private String _Q3 = "";
        public String getQ3(){
              return _Q3;
         }
        public void setQ3(String newValue){
              _Q3 = newValue;
         }
        private String _Q4 = "";
        public String getQ4(){
              return _Q4;
         }
        public void setQ4(String newValue){
              _Q4 = newValue;
         }
        private String _Q5 = "";
        public String getQ5(){
              return _Q5;
         }
        public void setQ5(String newValue){
              _Q5 = newValue;
         }
        private String _Q6 = "";
        public String getQ6(){
              return _Q6;
         }
        public void setQ6(String newValue){
              _Q6 = newValue;
         }
        private String _Q7 = "";
        public String getQ7(){
              return _Q7;
         }
        public void setQ7(String newValue){
              _Q7 = newValue;
         }
        private String _Q8 = "";
        public String getQ8(){
              return _Q8;
         }
        public void setQ8(String newValue){
              _Q8 = newValue;
         }
        private String _Q9 = "";
        public String getQ9(){
              return _Q9;
         }
        public void setQ9(String newValue){
              _Q9 = newValue;
         }
        private String _Q10 = "";
        public String getQ10(){
              return _Q10;
         }
        public void setQ10(String newValue){
              _Q10 = newValue;
         }
        private String _Q11 = "";
        public String getQ11(){
              return _Q11;
         }
        public void setQ11(String newValue){
              _Q11 = newValue;
         }
        private String _Q12 = "";
        public String getQ12(){
              return _Q12;
         }
        public void setQ12(String newValue){
              _Q12 = newValue;
         }
        private String _Q13 = "";
        public String getQ13(){
              return _Q13;
         }
        public void setQ13(String newValue){
              _Q13 = newValue;
         }
        private String _Q14 = "";
        public String getQ14(){
              return _Q14;
         }
        public void setQ14(String newValue){
              _Q14 = newValue;
         }
        private String _Q15 = "";
        public String getQ15(){
              return _Q15;
         }
        public void setQ15(String newValue){
              _Q15 = newValue;
         }
        private String _Q16 = "";
        public String getQ16(){
              return _Q16;
         }
        public void setQ16(String newValue){
              _Q16 = newValue;
         }
        private String _Q17 = "";
        public String getQ17(){
              return _Q17;
         }
        public void setQ17(String newValue){
              _Q17 = newValue;
         }
        private String _Q18 = "";
        public String getQ18(){
              return _Q18;
         }
        public void setQ18(String newValue){
              _Q18 = newValue;
         }
        private String _Q19 = "";
        public String getQ19(){
              return _Q19;
         }
        public void setQ19(String newValue){
              _Q19 = newValue;
         }
        private String _Q20 = "";
        public String getQ20(){
              return _Q20;
         }
        public void setQ20(String newValue){
              _Q20 = newValue;
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

        String TableName = "Women";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where WomenID='"+ _WomenID +"' "))
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
                 contentValues.put("WomenID", _WomenID);
                 contentValues.put("Q1", _Q1);
                 contentValues.put("Q2", _Q2);
                 contentValues.put("Q3", _Q3);
                 contentValues.put("Q4", _Q4);
                 contentValues.put("Q5", _Q5);
                 contentValues.put("Q6", _Q6);
                 contentValues.put("Q7", _Q7);
                 contentValues.put("Q8", _Q8);
                 contentValues.put("Q9", _Q9);
                 contentValues.put("Q10", _Q10);
                 contentValues.put("Q11", _Q11);
                 contentValues.put("Q12", _Q12);
                 contentValues.put("Q13", _Q13);
                 contentValues.put("Q14", _Q14);
                 contentValues.put("Q15", _Q15);
                 contentValues.put("Q16", _Q16);
                 contentValues.put("Q17", _Q17);
                 contentValues.put("Q18", _Q18);
                 contentValues.put("Q19", _Q19);
                 contentValues.put("Q20", _Q20);
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
                 contentValues.put("WomenID", _WomenID);
                 contentValues.put("Q1", _Q1);
                 contentValues.put("Q2", _Q2);
                 contentValues.put("Q3", _Q3);
                 contentValues.put("Q4", _Q4);
                 contentValues.put("Q5", _Q5);
                 contentValues.put("Q6", _Q6);
                 contentValues.put("Q7", _Q7);
                 contentValues.put("Q8", _Q8);
                 contentValues.put("Q9", _Q9);
                 contentValues.put("Q10", _Q10);
                 contentValues.put("Q11", _Q11);
                 contentValues.put("Q12", _Q12);
                 contentValues.put("Q13", _Q13);
                 contentValues.put("Q14", _Q14);
                 contentValues.put("Q15", _Q15);
                 contentValues.put("Q16", _Q16);
                 contentValues.put("Q17", _Q17);
                 contentValues.put("Q18", _Q18);
                 contentValues.put("Q19", _Q19);
                 contentValues.put("Q20", _Q20);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "WomenID", (""+ _WomenID +""), contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

          int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
        public List<Women_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Women_DataModel> data = new ArrayList<Women_DataModel>();
            Women_DataModel d = new Women_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new Women_DataModel();
                d._Count = Count;
                d._WomenID = cur.getString(cur.getColumnIndex("WomenID"));
                d._Q1 = cur.getString(cur.getColumnIndex("Q1"));
                d._Q2 = cur.getString(cur.getColumnIndex("Q2"));
                d._Q3 = cur.getString(cur.getColumnIndex("Q3"));
                d._Q4 = cur.getString(cur.getColumnIndex("Q4"));
                d._Q5 = cur.getString(cur.getColumnIndex("Q5"));
                d._Q6 = cur.getString(cur.getColumnIndex("Q6"));
                d._Q7 = cur.getString(cur.getColumnIndex("Q7"));
                d._Q8 = cur.getString(cur.getColumnIndex("Q8"));
                d._Q9 = cur.getString(cur.getColumnIndex("Q9"));
                d._Q10 = cur.getString(cur.getColumnIndex("Q10"));
                d._Q11 = cur.getString(cur.getColumnIndex("Q11"));
                d._Q12 = cur.getString(cur.getColumnIndex("Q12"));
                d._Q13 = cur.getString(cur.getColumnIndex("Q13"));
                d._Q14 = cur.getString(cur.getColumnIndex("Q14"));
                d._Q15 = cur.getString(cur.getColumnIndex("Q15"));
                d._Q16 = cur.getString(cur.getColumnIndex("Q16"));
                d._Q17 = cur.getString(cur.getColumnIndex("Q17"));
                d._Q18 = cur.getString(cur.getColumnIndex("Q18"));
                d._Q19 = cur.getString(cur.getColumnIndex("Q19"));
                d._Q20 = cur.getString(cur.getColumnIndex("Q20"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }