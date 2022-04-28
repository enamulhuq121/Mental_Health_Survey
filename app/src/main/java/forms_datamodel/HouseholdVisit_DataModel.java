package forms_datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;

public class HouseholdVisit_DataModel {

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
       private int _VisitNo = 0;
       public int getVisitNo(){
             return _VisitNo;
        }
       public void setVisitNo(int newValue){
             _VisitNo = newValue;
        }
       private String _VisitDate = "";
       public String getVisitDate(){
             return _VisitDate;
        }
       public void setVisitDate(String newValue){
             _VisitDate = newValue;
        }
       private int _VisitStatus = 0;
       public int getVisitStatus(){
             return _VisitStatus;
        }
       public void setVisitStatus(int newValue){
             _VisitStatus = newValue;
        }
       private String _VisitStatusOT = "";
       public String getVisitStatusOT(){
             return _VisitStatusOT;
        }
       public void setVisitStatusOT(String newValue){
             _VisitStatusOT = newValue;
        }

        private int _TotalMember = 0;
        public int getTotalMember(){
            return _TotalMember;
        }
        public void setTotalMember(int newValue){
            _TotalMember = newValue;
        }

        private String _EMWRA = "";
        public String getEMWRA(){
            return _EMWRA;
        }
        public void setEMWRA(String newValue){
            _EMWRA = newValue;
        }

        private int _CMWRATotal = 0;
        public int getCMWRATotal(){
            return _CMWRATotal;
        }
        public void setCMWRATotal(int newValue){
            _CMWRATotal = newValue;
        }

       private int _RoundNo = 0;
       public int getRoundNo(){
        return _RoundNo;
    }
       public void setRoundNo(int newValue){
        _RoundNo = newValue;
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

       String TableName = "HouseholdVisit";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where DCode='"+ _DCode +"' and UPCode='"+ _UPCode +"' and UNCode='"+ _UNCode +"' and Cluster='"+ _Cluster +"' and VCode='"+ _VCode +"' and Bari='"+ _Bari +"' and HHNo='"+ _HHNo +"' and VisitNo='"+ _VisitNo +"' "))
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
                contentValues.put("VisitNo", _VisitNo);
                contentValues.put("VisitDate", _VisitDate);
                contentValues.put("VisitStatus", _VisitStatus);
                contentValues.put("VisitStatusOT", _VisitStatusOT);
                contentValues.put("TotalMember", _TotalMember);
                 contentValues.put("EMWRA", _EMWRA);
                contentValues.put("EMWRATotal", _CMWRATotal);
                 contentValues.put("Rnd", _RoundNo);

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
                contentValues.put("VisitNo", _VisitNo);
                contentValues.put("VisitDate", _VisitDate);
                contentValues.put("VisitStatus", _VisitStatus);
                contentValues.put("VisitStatusOT", _VisitStatusOT);
                contentValues.put("TotalMember", _TotalMember);
                 contentValues.put("EMWRA", _EMWRA);
                contentValues.put("EMWRATotal", _CMWRATotal);
                contentValues.put("Rnd", _RoundNo);
                /*if(ProjectSetting.ProjectTitle.length()>0){
                    contentValues.put("DeviceID", _DeviceID);
                    contentValues.put("EntryUser", _EntryUser);
                    contentValues.put("EnDt", _EnDt);
                }*/
                contentValues.put("Upload", _Upload);
                contentValues.put("modifyDate", _modifyDate);
                C.UpdateData(TableName, "DCode,UPCode,UNCode,Cluster,VCode,Bari,HHNo,VisitNo", (""+ _DCode +", "+ _UPCode +", "+ _UNCode +","+ _Cluster +", "+ _VCode +", "+ _Bari +", "+ _HHNo +", "+ _VisitNo +""), contentValues);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<HouseholdVisit_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<HouseholdVisit_DataModel> data = new ArrayList<HouseholdVisit_DataModel>();
           HouseholdVisit_DataModel d = new HouseholdVisit_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new HouseholdVisit_DataModel();
               d._DCode = cur.getString(cur.getColumnIndex("DCode"));
               d._UPCode = cur.getString(cur.getColumnIndex("UPCode"));
               d._UNCode = cur.getString(cur.getColumnIndex("UNCode"));
               d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
               d._VCode = cur.getString(cur.getColumnIndex("VCode"));
               d._Bari = cur.getString(cur.getColumnIndex("Bari"));
               d._HHNo = cur.getString(cur.getColumnIndex("HHNo"));
               d._VisitNo = Integer.valueOf(cur.getString(cur.getColumnIndex("VisitNo")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("VisitNo")));
               d._VisitDate = cur.getString(cur.getColumnIndex("VisitDate"));
               d._VisitStatus = Integer.valueOf(cur.getString(cur.getColumnIndex("VisitStatus")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("VisitStatus")));
               d._VisitStatusOT = cur.getString(cur.getColumnIndex("VisitStatusOT"));
               d._TotalMember = Integer.valueOf(cur.getString(cur.getColumnIndex("TotalMember")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("TotalMember")));
               d._EMWRA = cur.getString(cur.getColumnIndex("EMWRA"));
               d._CMWRATotal = Integer.valueOf(cur.getString(cur.getColumnIndex("EMWRATotal")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("EMWRATotal")));

               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}