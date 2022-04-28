package gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;

public class GPS_Landmark_Data_DataModel {

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
       private String _Landmark = "";
       public String getLandmark(){
       return _Landmark;
       }
       public void setLandmark(String newValue){
           _Landmark = newValue;
       }
       private String _Longitude = "";
       public String getLongitude(){
       return _Longitude;
       }
       public void setLongitude(String newValue){
       _Longitude = newValue;
       }
       private String _Latitude = "";
       public String getLatitude(){
       return _Latitude;
       }
       public void setLatitude(String newValue){
       _Latitude = newValue;
       }

       private String _Altitude = "";
       public String getAltitude(){
       return _Altitude;
       }
       public void setAltitude(String newValue){
       _Altitude = newValue;
       }

       private String _Accuracy = "0";
       public String getAccuracy(){
       return _Accuracy;
       }
       public void setAccuracy(String newValue){
       _Accuracy = newValue;
       }
       private String _Satelites = "0";
       public String getSatelites(){
       return _Satelites;
       }
       public void setSatelites(String newValue){
       _Satelites = newValue;
       }
       private String _GPSType = "0";
       public String getGPSType(){
       return _GPSType;
       }
       public void setGPSType(String newValue){
       _GPSType = newValue;
       }
       private String _LandmarkType = "0";
       public String getLandmarkType(){
       return _LandmarkType;
       }
       public void setLandmarkType(String newValue){
       _LandmarkType = newValue;
       }
       private String _LandmarkName = "";
       public String getLandmarkName(){
       return _LandmarkName;
       }
       public void setLandmarkName(String newValue){
       _LandmarkName = newValue;
       }
       private String _Remarks = "";
       public String getRemarks(){
       return _Remarks;
       }
       public void setRemarks(String newValue){
       _Remarks = newValue;
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

       String TableName = "GPS_Landmark";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where DCode='"+ _DCode +"' and UPCode='"+ _UPCode +"' and UNCode='"+ _UNCode +"' and Cluster='"+ _Cluster +"' and VCode='"+ _VCode +"' and Landmark='"+ _Landmark +"' "))
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
                contentValues.put("Landmark", _Landmark);
                contentValues.put("Longitude", _Longitude);
                contentValues.put("Latitude", _Latitude);
                contentValues.put("Altitude", _Altitude);
                contentValues.put("Accuracy", _Accuracy);
                contentValues.put("Satelites", _Satelites);
                contentValues.put("GPSType", _GPSType);
                contentValues.put("LandmarkType", _LandmarkType);
                contentValues.put("LandmarkName", _LandmarkName);
                contentValues.put("Remarks", _Remarks);
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
                 contentValues.put("Landmark", _Landmark);
                contentValues.put("Longitude", _Longitude);
                contentValues.put("Latitude", _Latitude);
                contentValues.put("Altitude", _Altitude);
                contentValues.put("Accuracy", _Accuracy);
                contentValues.put("Satelites", _Satelites);
                contentValues.put("GPSType", _GPSType);
                contentValues.put("LandmarkType", _LandmarkType);
                contentValues.put("LandmarkName", _LandmarkName);
                contentValues.put("Remarks", _Remarks);
                contentValues.put("Upload", _Upload);
                contentValues.put("modifyDate", _modifyDate);
                C.UpdateData(TableName, "DCode,UPCode,UNCode,Cluster,VCode,Landmark", (""+ _DCode +", "+ _UPCode +", "+ _UNCode +","+ _Cluster +", "+ _VCode +", "+ _Landmark +""), contentValues);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }

         int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
       public List<GPS_Landmark_Data_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<GPS_Landmark_Data_DataModel> data = new ArrayList<GPS_Landmark_Data_DataModel>();
           GPS_Landmark_Data_DataModel d = new GPS_Landmark_Data_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               Count += 1;
               d = new GPS_Landmark_Data_DataModel();
               d._Count = Count;
               d._DCode = cur.getString(cur.getColumnIndex("DCode"));
               d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
               d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
               d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
               d._VCode = cur.getString(cur.getColumnIndex("VCode"));
               d._Landmark = cur.getString(cur.getColumnIndex("Landmark"));
               d._Longitude = cur.getString(cur.getColumnIndex("Longitude"));
               d._Latitude = cur.getString(cur.getColumnIndex("Latitude"));
               d._Altitude = cur.getString(cur.getColumnIndex("Altitude"));

               d._Accuracy = cur.getString(cur.getColumnIndex("Accuracy"));
               d._Satelites = cur.getString(cur.getColumnIndex("Satelites"));
               d._GPSType = cur.getString(cur.getColumnIndex("GPSType"));
               d._LandmarkType = cur.getString(cur.getColumnIndex("LandmarkType"));
               d._LandmarkName = cur.getString(cur.getColumnIndex("LandmarkName"));
               d._Remarks = cur.getString(cur.getColumnIndex("Remarks"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}