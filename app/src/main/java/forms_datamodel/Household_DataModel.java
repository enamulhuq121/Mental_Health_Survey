package forms_datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;

public class Household_DataModel {
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
       private String _HHNo = "";
       public String getHHNo(){
             return _HHNo;
        }
       public void setHHNo(String newValue){
             _HHNo = newValue;
        }
       private String _HHHead = "";
       public String getHHHead(){
             return _HHHead;
        }
       public void setHHHead(String newValue){
             _HHHead = newValue;
        }
       private String _Mobile1 = "";
       public String getMobile1(){
             return _Mobile1;
        }
       public void setMobile1(String newValue){
             _Mobile1 = newValue;
        }
       private String _Mobile2 = "";
       public String getMobile2(){
             return _Mobile2;
        }
       public void setMobile2(String newValue){
             _Mobile2 = newValue;
        }
        private String _VisitStatus = "";
        public String getVisitStatus(){
            return _VisitStatus;
        }
        public void setVisitStatus(String newValue){
            _VisitStatus = newValue;
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

       String TableName = "Household";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where DCode='"+ _DCode +"' and UPCode='"+ _UPCode +"' and UNCode='"+ _UNCode +"' and Cluster='"+ _Cluster +"' and VCode='"+ _VCode +"' and Bari='"+ _Bari +"' and HHNo='"+ _HHNo +"' "))
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
                contentValues.put("HHNo", _HHNo);
                contentValues.put("HHHead", _HHHead);
                contentValues.put("Mobile1", _Mobile1);
                contentValues.put("Mobile2", _Mobile2);
                contentValues.put("VisitStatus", _VisitStatus);
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
                contentValues.put("HHNo", _HHNo);
                contentValues.put("HHHead", _HHHead);
                contentValues.put("Mobile1", _Mobile1);
                contentValues.put("Mobile2", _Mobile2);
                contentValues.put("Upload", _Upload);
                contentValues.put("modifyDate", _modifyDate);
                C.UpdateData(TableName, "DCode,UPCode,UNCode,Cluster,VCode,Bari,HHNo", (""+ _DCode +", "+ _UPCode +", "+ _UNCode +","+ _Cluster +", "+ _VCode +", "+ _Bari +", "+ _HHNo +""), contentValues);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       int Count = 0;
       private int _Count = 0;
       public int getCount(){
            return _Count;
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

    private String _totalmember = "";
    public String gettotalmember(){
        return _totalmember;
    }
    private String _cmwratotal = "";
    public String getcmwratotal(){
        return _cmwratotal;
    }

    private String _indexhh = "";
    public String getIndexHH(){
        return _indexhh;
    }
    public List<Household_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<Household_DataModel> data = new ArrayList<Household_DataModel>();
           Household_DataModel d = new Household_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               Count += 1;
               d = new Household_DataModel();
               d._Count = Count;
               d._DCode = cur.getString(cur.getColumnIndex("DCode"));
               d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
               d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
               d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
               d._VCode = cur.getString(cur.getColumnIndex("VCode"));
               d._Bari = cur.getString(cur.getColumnIndex("Bari"));
               d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
               d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
               d._Mobile1 = cur.getString(cur.getColumnIndex("Mobile1"));
               d._Mobile2 = cur.getString(cur.getColumnIndex("Mobile2"));
               d._VisitStatus = cur.getString(cur.getColumnIndex("VisitStatus"));
               d._BariName = cur.getString(cur.getColumnIndex("BariName"));
               d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));

               d._totalmember = cur.getString(cur.getColumnIndex("totalmember"));
               d._cmwratotal = cur.getString(cur.getColumnIndex("cmwratotal"));
               d._indexhh = cur.getString(cur.getColumnIndex("indexhh"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }

    private String _gps = "";
    public String getGPS(){
        return _gps;
    }
    private String _totalhh = "";
    public String getTotalHH(){
        return _totalhh;
    }
    public List<Household_DataModel> SelectAll_Mapping(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<Household_DataModel> data = new ArrayList<Household_DataModel>();
        Household_DataModel d = new Household_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            Count += 1;
            d = new Household_DataModel();
            d._Count = Count;
            d._DCode = cur.getString(cur.getColumnIndex("DCode"));
            d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
            d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
            d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
            d._VCode = cur.getString(cur.getColumnIndex("VCode"));
            d._Bari = cur.getString(cur.getColumnIndex("Bari"));
            d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
            d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
            d._Mobile1 = cur.getString(cur.getColumnIndex("Mobile1"));
            d._Mobile2 = cur.getString(cur.getColumnIndex("Mobile2"));
            d._VisitStatus = cur.getString(cur.getColumnIndex("VisitStatus"));
            d._BariName = cur.getString(cur.getColumnIndex("BariName"));
            d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));

            d._totalmember = cur.getString(cur.getColumnIndex("totalmember"));
            d._cmwratotal = cur.getString(cur.getColumnIndex("cmwratotal"));
            d._indexhh = cur.getString(cur.getColumnIndex("indexhh"));
            d._gps = cur.getString(cur.getColumnIndex("gps"));
            d._totalhh = cur.getString(cur.getColumnIndex("TotalHH"));
            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }

    private String _landmark = "";
    public String getLandmark(){
        return _landmark;
    }
    private String _landmarktype = "";
    public String getLandmarkType(){
        return _landmarktype;
    }
    private String _landmarkname = "";
    public String getLandmarkname(){
        return _landmarkname;
    }

    private String _LTypeName = "";
    public String getLTypeName(){
        return _LTypeName;
    }
    public List<Household_DataModel> SelectAll_Landmark(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<Household_DataModel> data = new ArrayList<Household_DataModel>();
        Household_DataModel d = new Household_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            Count += 1;
            d = new Household_DataModel();
            d._Count = Count;
            d._DCode = cur.getString(cur.getColumnIndex("DCode"));
            d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
            d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
            d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
            d._VCode = cur.getString(cur.getColumnIndex("VCode"));
            //d._landmark = cur.getString(cur.getColumnIndex("Landmark"));
            d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
            d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
            d._Mobile1 = cur.getString(cur.getColumnIndex("Mobile1"));
            d._Mobile2 = cur.getString(cur.getColumnIndex("Mobile2"));
            d._VisitStatus = cur.getString(cur.getColumnIndex("VisitStatus"));
            //d._BariName = cur.getString(cur.getColumnIndex("BariName"));
            //d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));

            d._totalmember = cur.getString(cur.getColumnIndex("totalmember"));
            d._cmwratotal = cur.getString(cur.getColumnIndex("cmwratotal"));
            d._indexhh = cur.getString(cur.getColumnIndex("indexhh"));
            d._gps = cur.getString(cur.getColumnIndex("gps"));
            d._totalhh = cur.getString(cur.getColumnIndex("TotalHH"));

            d._landmark = cur.getString(cur.getColumnIndex("Landmark"));
            d._landmarktype = cur.getString(cur.getColumnIndex("LandmarkType"));
            d._landmarkname = cur.getString(cur.getColumnIndex("LandmarkName"));
            d._LTypeName = cur.getString(cur.getColumnIndex("LTypeName"));
            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }

    public List<Household_DataModel> SelectAll_Household(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<Household_DataModel> data = new ArrayList<Household_DataModel>();
        Household_DataModel d = new Household_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            Count += 1;
            d = new Household_DataModel();
            d._Count = Count;
            d._DCode = cur.getString(cur.getColumnIndex("DCode"));
            d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
            d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
            d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
            d._VCode = cur.getString(cur.getColumnIndex("VCode"));
            d._Bari = cur.getString(cur.getColumnIndex("Bari"));
            d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
            d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
            d._Mobile1 = cur.getString(cur.getColumnIndex("Mobile1"));
            d._Mobile2 = cur.getString(cur.getColumnIndex("Mobile2"));
            d._VisitStatus = cur.getString(cur.getColumnIndex("VisitStatus"));
            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }

    public List<Household_DataModel> SelectAllHHList(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<Household_DataModel> data = new ArrayList<Household_DataModel>();
        Household_DataModel d = new Household_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            Count += 1;
            d = new Household_DataModel();
            d._Count = Count;
            d._DCode = cur.getString(cur.getColumnIndex("DCode"));
            d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
            d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
            d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
            d._VCode = cur.getString(cur.getColumnIndex("VCode"));
            d._Bari = cur.getString(cur.getColumnIndex("Bari"));
            d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
            d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
            d._Mobile1 = cur.getString(cur.getColumnIndex("Mobile1"));
            d._Mobile2 = cur.getString(cur.getColumnIndex("Mobile2"));
            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }
}