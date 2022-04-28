package DataSync;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.DataClass;
import Common.DataClassProperty;
import Common.DownloadClass;
import Common.ResponseClass;

public class DataUploadDownload{

    public String SyncUpload(String TableName, String ColumnList, String UniqueFields, Context db_context) {
        Connection C = new Connection(db_context);
        String response = "";
        List<DataClassProperty> data = C.GetDataListJSON(ColumnList, TableName, UniqueFields);

        if (data.size() > 0) {
            DataClass dt = new DataClass();
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            dt.setuniquefields(UniqueFields);

            dt.setdata(data);
            Gson gson = new Gson();
            String json = gson.toJson(dt);
            UploadData u = new UploadData();
            try {
                response = u.execute(json).get();

                //Process Response
                if (response != null) {
                    Type collType = new TypeToken<ResponseClass>() {
                    }.getType();

                    ResponseClass responseData = gson.fromJson(response, collType);
                    String resp = C.SaveData(responseData.getdata().toString());

                    //upload all records as successfull upload then update status of upload=2 for unsuccessfull
                    /*String UpdateSQL = "";
                    for (int i = 0; i < responseData.getdata().size(); i++) {
                        UpdateSQL += "Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString() +";";
                        //Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    }
                    Save(UpdateSQL);*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public String SyncDownload(String SQL, String TableName, String ColumnList, String UniqueField, String UserId, Context db_context) {
        Connection C = new Connection(db_context);
        String response = "";
        String final_response = "";
        int varPos = 0;

        //1. Select all the tables
        String table_list[] = C.getArrayList("Select tablename from databasetab order by tablename");

        //2. populate the table wise id list (modifydate with milisecond)
        List<String> id_list = new ArrayList<>();
        DownloadRequest_Class down_req = null;
        for(String tn : table_list){
            id_list = C.getDataList("Select cast(modifydate as text)idlist from "+ tn +"");
            down_req = new DownloadRequest_Class();
            down_req.settablename(tn);
            down_req.setdata(id_list);
        }

        //3. prepare json with the tablename and id list
        Gson gson = new Gson();
        String json = gson.toJson(down_req);

        //4. send a request to server with json for downloading the remaining data
        UploadData u = new UploadData();
        try {
            response = u.execute(json).get();

            DownloadClass d = new DownloadClass();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            DataClassProperty dd;
            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data     = new ArrayList<DataClassProperty>();

            String downloadSyncStatus = "";

            if (responseData != null & responseData.getdata().size()>0) {
                SQL = "Insert or replace into "+ TableName +"("+ ColumnList +")Values";
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    if (i == 0) {
                        SQL += "('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    } else {
                        SQL += ",('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    }
                }

                //5. get the data from the server and process in local database
                downloadSyncStatus = C.SaveData(SQL);
                if(downloadSyncStatus.length()==0){
                    final_response = "";
                }else{
                    final_response = downloadSyncStatus;
                }
            }
        }catch (Exception ex){

        }

        return final_response;
    }

}