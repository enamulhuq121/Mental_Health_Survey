package Utility;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Common.Connection;
import Common.Global;
import forms_datamodel.audit_trial_DataModel;

public class AuditTrial {

    Context mContext;
    String mAuditTableName;

    Connection mConnection;
    Global mGlobal;

    public AuditTrial(Context mContext, String tableName) {
        this.mContext = mContext;
        this.mAuditTableName = tableName;
        this.mAuditTrialDataModelList = new ArrayList<>();
        this.mConnection = new Connection(mContext);
        this.mGlobal = new Global();
    }

    public static final String KEY_SELECT="KEY_SELECT";
    public static final String KEY_UPDATE="KEY_UPDATE";

    List<audit_trial_DataModel> mAuditTrialDataModelList;

    public void audit(Map<String,Object> firstAudit, Map<String,Object> secondAudit){
        try {
            String uniqueColumns  = mConnection.ReturnSingleValue("Select UniqueID from DatabaseTab where tablename='" + mAuditTableName + "'");
            //Log.d("TAG", "audit: uniqueColumns: "+uniqueColumns);

            String dataId = getDataId(uniqueColumns,firstAudit);
            String deviceId  = MySharedPreferences.getValue(mContext, "deviceid");
            String entryUser = MySharedPreferences.getValue(mContext, "userid");

            for (String key : firstAudit.keySet()) {
                String auditId = getUUID();
                String firstAuditValue = getValueFromOb(firstAudit.get(key));
                String secondAuditValue = "";
                if (secondAudit.containsKey(key)){
                    secondAuditValue = getValueFromOb(secondAudit.get(key));
                }

                // check if value is different in old and new object
                if (!secondAudit.containsKey(key) || !firstAuditValue.equals(secondAuditValue)) {
                    audit_trial_DataModel dataModel = new audit_trial_DataModel();

                    dataModel.setaudit_id(auditId);
                    dataModel.settable_name(mAuditTableName);
                    dataModel.setdata_id(dataId);
                    dataModel.setvariable_name(key);
                    dataModel.setold_value(firstAuditValue);
                    dataModel.setnew_value(secondAuditValue);
                    //dataModel.setStartTime(STARTTIME);
                    dataModel.setEndTime(mGlobal.CurrentTime24());
                    dataModel.setDeviceID(deviceId);
                    dataModel.setEntryUser(entryUser); //from data entry user list
                    dataModel.setLat(MySharedPreferences.getValue(mContext, "lat"));
                    dataModel.setLon(MySharedPreferences.getValue(mContext, "lon"));

                    //Log.d("TAG", "manage: key "+key +" " +dataModel.toString());
                    mAuditTrialDataModelList.add(dataModel);
                }
            }

            saveAll(mAuditTrialDataModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDataId(String uniqueColumns, Map<String, Object> dataMap) {
        StringBuilder dataId = new StringBuilder();
        if (uniqueColumns != null && !uniqueColumns.isEmpty()){
            String[] uniqueColArr = uniqueColumns.split(",");
            for (String column: uniqueColArr
                 ) {
                dataId.append(dataMap.get(column.trim()));
            }
        }
        return dataId.toString();
    }

    private String getValueFromOb(Object ob){
        String result = "";
        try {
            if (ob != null){
                result = ob.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    public void saveAll( List<audit_trial_DataModel> dataModelList){

        try {
            if (dataModelList != null && !dataModelList.isEmpty()){
                String tableColumns  = mConnection.ReturnSingleValue("Select ColumnList from DatabaseTab where tablename='" + audit_trial_DataModel.TableName + "'");
                StringBuilder SQLBuilder = new StringBuilder("Insert or replace into " + audit_trial_DataModel.TableName + "(" + tableColumns + ")Values");

                for (audit_trial_DataModel dataModel: dataModelList
                ) {
                    if (dataModelList.indexOf(dataModel) != 0){
                        SQLBuilder.append(",");
                    }
                    SQLBuilder.append(dataModel.toString().replace("NULL",""));

                }

                String saveStatus = mConnection.SaveData(SQLBuilder.toString());
                if(saveStatus.length()==0){
                    // save successful
                    //Log.d("TAG", "saveAll: successful");
                }else{
                    // save unsuccessful
                    //Log.d("TAG", "saveAll: unsuccessful");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
