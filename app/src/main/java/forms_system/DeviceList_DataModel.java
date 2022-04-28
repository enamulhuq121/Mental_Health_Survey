package forms_system;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;

import Common.Global;
 import android.content.ContentValues;

public class DeviceList_DataModel{

       private String _DeviceID = "";
       public String getDeviceID(){
             return _DeviceID;
        }
       public void setDeviceID(String newValue){
             _DeviceID = newValue;
        }
       private String _DeviceUniqueID = "";
       public String getDeviceUniqueID(){
             return _DeviceUniqueID;
        }
       public void setDeviceUniqueID(String newValue){
             _DeviceUniqueID = newValue;
        }
       private String _UpdateDT = "";
       public String getUpdateDT(){
             return _UpdateDT;
        }
       public void setUpdateDT(String newValue){
             _UpdateDT = newValue;
        }
       private String _Setting = "";
       public String getSetting(){
             return _Setting;
        }
       public void setSetting(String newValue){
             _Setting = newValue;
        }
       private String _DBRequest = "";
       public String getDBRequest(){
             return _DBRequest;
        }
       public void setDBRequest(String newValue){
             _DBRequest = newValue;
        }
       private String _Active = "";
       public String getActive(){
             return _Active;
        }
       public void setActive(String newValue){
             _Active = newValue;
        }
       private String _Approval = "";
       public String getApproval(){
             return _Approval;
        }
       public void setApproval(String newValue){
             _Approval = newValue;
        }
       private String _ApprovalDT = "";
       public String getApprovalDT(){
             return _ApprovalDT;
        }
       public void setApprovalDT(String newValue){
             _ApprovalDT = newValue;
        }
       private String _EnDt = Global.DateTimeNowYMDHMS();
       private int _Upload = 2;
       private String _modifyDate = Global.DateTimeNowYMDHMS();

       String TableName = "DeviceList";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where DeviceID='"+ _DeviceID +"' "))
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
                contentValues.put("DeviceID", _DeviceID);
                contentValues.put("DeviceUniqueID", _DeviceUniqueID);
                contentValues.put("UpdateDT", _UpdateDT);
                contentValues.put("Setting", _Setting);
                contentValues.put("DBRequest", _DBRequest);
                contentValues.put("Active", _Active);
                contentValues.put("Approval", _Approval);
                contentValues.put("ApprovalDT", _ApprovalDT);
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
                contentValues.put("DeviceID", _DeviceID);
                contentValues.put("DeviceUniqueID", _DeviceUniqueID);
                contentValues.put("UpdateDT", _UpdateDT);
                contentValues.put("Setting", _Setting);
                contentValues.put("DBRequest", _DBRequest);
                contentValues.put("Active", _Active);
                contentValues.put("Approval", _Approval);
                contentValues.put("ApprovalDT", _ApprovalDT);
                contentValues.put("Upload", _Upload);
                contentValues.put("modifyDate", _modifyDate);
                C.UpdateData(TableName, "DeviceID", (""+ _DeviceID +""), contentValues);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }

         int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
       public List<DeviceList_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<DeviceList_DataModel> data = new ArrayList<DeviceList_DataModel>();
           DeviceList_DataModel d = new DeviceList_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               Count += 1;
               d = new DeviceList_DataModel();
               d._Count = Count;
               d._DeviceID = cur.getString(cur.getColumnIndex("DeviceID"));
               d._DeviceUniqueID = cur.getString(cur.getColumnIndex("DeviceUniqueID"));
               d._UpdateDT = cur.getString(cur.getColumnIndex("UpdateDT"));
               d._Setting = cur.getString(cur.getColumnIndex("Setting"));
               d._DBRequest = cur.getString(cur.getColumnIndex("DBRequest"));
               d._Active = cur.getString(cur.getColumnIndex("Active"));
               d._Approval = cur.getString(cur.getColumnIndex("Approval"));
               d._ApprovalDT = cur.getString(cur.getColumnIndex("ApprovalDT"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}