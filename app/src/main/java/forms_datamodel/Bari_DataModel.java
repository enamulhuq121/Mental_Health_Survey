package forms_datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;

public class Bari_DataModel {

        private String _DCode = "";
        public String getDCode(){
        return _DCode;
        }
        public void setDCode(String newValue){
        _DCode = newValue;
        }
        private String _UPCode = "";
        public String getUPCode(){
        return _UPCode;
        }
        public void setUPCode(String newValue){
        _UPCode = newValue;
        }
        private String _UNCode = "";
        public String getUNCode(){
        return _UNCode;
        }
        public void setUNCode(String newValue){
        _UNCode = newValue;
        }

        private String _Cluster = "";
        public String getCluster(){
        return _Cluster;
        }
        public void setCluster(String newValue){
        _Cluster = newValue;
        }
        private String _VCode = "";
        public String getVCode(){
        return _VCode;
        }
        public void setVCode(String newValue){
        _VCode = newValue;
        }
        private String _Bari = "";
        public String getBari(){
        return _Bari;
        }
        public void setBari(String newValue){
        _Bari = newValue;
        }
        private String _BariName = "";
        public String getBariName(){
        return _BariName;
        }
        public void setBariName(String newValue){
        _BariName = newValue;
        }
        private String _BariLoc = "";
        public String getBariLoc(){
        return _BariLoc;
        }
        public void setBariLoc(String newValue){
        _BariLoc = newValue;
        }
        private String _TotalHH = "";
        public String getTotalHH(){
            return _TotalHH;
        }
        public void setTotalHH(String newValue){
            _TotalHH = newValue;
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

       String TableName = "Bari";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where DCode='"+ _DCode +"' and UPCode='"+ _UPCode +"' and UNCode='"+ _UNCode +"' and Cluster='"+ _Cluster +"' and VCode='"+ _VCode +"' and Bari='"+ _Bari +"' "))
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
                contentValues.put("DCode", _DCode);
                contentValues.put("UPCode", _UPCode);
                contentValues.put("UNCode", _UNCode);
                contentValues.put("Cluster", _Cluster);
                contentValues.put("VCode", _VCode);
                contentValues.put("Bari", _Bari);
                contentValues.put("BariName", _BariName);
                contentValues.put("BariLoc", _BariLoc);
                contentValues.put("TotalHH", _TotalHH);
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
                contentValues.put("DCode", _DCode);
                contentValues.put("UPCode", _UPCode);
                contentValues.put("UNCode", _UNCode);
                 contentValues.put("Cluster", _Cluster);
                contentValues.put("VCode", _VCode);
                contentValues.put("Bari", _Bari);
                contentValues.put("BariName", _BariName);
                contentValues.put("BariLoc", _BariLoc);
                contentValues.put("TotalHH", _TotalHH);
                contentValues.put("Upload", _Upload);
                contentValues.put("modifyDate", _modifyDate);
                C.UpdateData(TableName, "DCode,UPCode,UNCode,Cluster,VCode,Bari", (""+ _DCode +","+ _UPCode +","+ _UNCode +","+ _Cluster +","+ _VCode +","+ _Bari +""), contentValues);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<Bari_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<Bari_DataModel> data = new ArrayList<Bari_DataModel>();
           Bari_DataModel d = new Bari_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new Bari_DataModel();
               d._DCode = cur.getString(cur.getColumnIndex("DCode"));
               d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
               d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
               d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
               d._VCode = cur.getString(cur.getColumnIndex("VCode"));
               d._Bari = cur.getString(cur.getColumnIndex("Bari"));
               d._BariName = cur.getString(cur.getColumnIndex("BariName"));
               d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));
               d._TotalHH = cur.getString(cur.getColumnIndex("TotalHH"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}