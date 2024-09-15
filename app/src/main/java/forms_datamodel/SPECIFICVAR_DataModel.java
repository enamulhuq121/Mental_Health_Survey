package forms_datamodel;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Date;
 import Common.Global;
 import android.content.ContentValues;
 public class SPECIFICVAR_DataModel{

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
        private String _CC2 = "";
        public String getCC2(){
              return _CC2;
         }
        public void setCC2(String newValue){
              _CC2 = newValue;
         }
        private String _CC3 = "";
        public String getCC3(){
              return _CC3;
         }
        public void setCC3(String newValue){
              _CC3 = newValue;
         }
        private String _CC5 = "";
        public String getCC5(){
              return _CC5;
         }
        public void setCC5(String newValue){
              _CC5 = newValue;
         }
        private String _CC6 = "";
        public String getCC6(){
              return _CC6;
         }
        public void setCC6(String newValue){
              _CC6 = newValue;
         }
        private String _CC8 = "";
        public String getCC8(){
              return _CC8;
         }
        public void setCC8(String newValue){
              _CC8 = newValue;
         }
        private String _CC9 = "";
        public String getCC9(){
              return _CC9;
         }
        public void setCC9(String newValue){
              _CC9 = newValue;
         }
        private String _CC10 = "";
        public String getCC10(){
              return _CC10;
         }
        public void setCC10(String newValue){
              _CC10 = newValue;
         }
        private String _CC11 = "";
        public String getCC11(){
              return _CC11;
         }
        public void setCC11(String newValue){
              _CC11 = newValue;
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

        String TableName = "SPECIFICVAR";

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
                 contentValues.put("CC2", _CC2);
                 contentValues.put("CC3", _CC3);
                 contentValues.put("CC5", _CC5);
                 contentValues.put("CC6", _CC6);
                 contentValues.put("CC8", _CC8);
                 contentValues.put("CC9", _CC9);
                 contentValues.put("CC10", _CC10);
                 contentValues.put("CC11", _CC11);
              //   contentValues.put("isdelete", 2);
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
                 contentValues.put("CC2", _CC2);
                 contentValues.put("CC3", _CC3);
                 contentValues.put("CC5", _CC5);
                 contentValues.put("CC6", _CC6);
                 contentValues.put("CC8", _CC8);
                 contentValues.put("CC9", _CC9);
                 contentValues.put("CC10", _CC10);
                 contentValues.put("CC11", _CC11);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "PatientID,FacilityID", (""+ _PatientID +", "+ _FacilityID +""), contentValues);

             //    manageAudit(context,this,AuditTrial.KEY_UPDATE);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

          int Count = 0;          private int _Count = 0;          public int getCount(){ return _Count; }
        public List<SPECIFICVAR_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SPECIFICVAR_DataModel> data = new ArrayList<SPECIFICVAR_DataModel>();
            SPECIFICVAR_DataModel d = new SPECIFICVAR_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new SPECIFICVAR_DataModel();
                d._Count = Count;
                d._PatientID = cur.getString(cur.getColumnIndex("PatientID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._CC2 = cur.getString(cur.getColumnIndex("CC2"));
                d._CC3 = cur.getString(cur.getColumnIndex("CC3"));
                d._CC5 = cur.getString(cur.getColumnIndex("CC5"));
                d._CC6 = cur.getString(cur.getColumnIndex("CC6"));
                d._CC8 = cur.getString(cur.getColumnIndex("CC8"));
                d._CC9 = cur.getString(cur.getColumnIndex("CC9"));
                d._CC10 = cur.getString(cur.getColumnIndex("CC10"));
                d._CC11 = cur.getString(cur.getColumnIndex("CC11"));
                data.add(d);

            //    manageAudit(context,d,AuditTrial.KEY_SELECT);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }



     /*   static Map<String, Object> firstStateMap;
        public void manageAudit(Context context, SPECIFICVAR_DataModel ob, String key) {
            if (key.equalsIgnoreCase(AuditTrial.KEY_SELECT)) {
                //store old state
                firstStateMap = getMapData(ob);
            } else if (key.equalsIgnoreCase(AuditTrial.KEY_UPDATE)) {
                //store new state
                Map<String, Object> secondStateMap = getMapData(ob);
                AuditTrial auditTrial = new AuditTrial(context,TableName);
                // run audit
                if (firstStateMap != null && !firstStateMap.isEmpty() && secondStateMap != null && !secondStateMap.isEmpty()) {
                    auditTrial.audit(firstStateMap, secondStateMap);
                }
            }
        }*/



        /**
        * get object state
        * @param ob
        * @return
        */
        /*public Map<String, Object> getMapData(SPECIFICVAR_DataModel ob) {
            Map<String, Object> data = new HashMap<>();

            if (ob != null) {
                 data.put("PatientID", ob._PatientID);
                 data.put("FacilityID", ob._FacilityID);
                 data.put("CC2", ob._CC2);
                 data.put("CC3", ob._CC3);
                 data.put("CC5", ob._CC5);
                 data.put("CC6", ob._CC6);
                 data.put("CC8", ob._CC8);
                 data.put("CC9", ob._CC9);
                 data.put("CC10", ob._CC10);
                 data.put("CC11", ob._CC11);
            
            }
            return data;
        }*/
 }