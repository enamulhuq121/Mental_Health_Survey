package Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.icddrb.standard_v3.R;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.concurrent.ExecutionException;

import DataSync.api_DownloadClass;
import DataSync.api_DownloadJSONData;
import DataSync.api_DownloadRequestClass;
import Utility.CompressZip;

//--------------------------------------------------------------------------------------------------
// Created by TanvirHossain on 17/03/2015.
//--------------------------------------------------------------------------------------------------
public class Connection extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DB_NAME    = ProjectSetting.DatabaseName;
    private static final String DBLocation = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder + File.separator + DB_NAME;
    //private static final String DBLocation = DB_NAME;
    //public static final String DBLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + ProjectSetting.DatabaseFolder + File.separator + DB_NAME;

    // Todo table name
    private static final String TABLE_TODO = "todo_items";
    private static Context dbContext;

    public Connection(Context context) {
        super(context, DBLocation, null, DATABASE_VERSION);
        dbContext = context;
    }

    //Split function
    //----------------------------------------------------------------------------------------------
    public static String[] split(String s, char separator) {
        ArrayList<String> d = new ArrayList<String>();
        for (int ini = 0, end = 0; ini <= s.length(); ini = end + 1) {
            end = s.indexOf(separator, ini);
            if (end == -1) {
                end = s.length();
            }

            String st = s.substring(ini, end).trim();

            if (st.length() > 0) {
                d.add(st);
            } else {
                d.add("");
            }
        }

        String[] temp = new String[d.size()];
        temp = d.toArray(temp);
        return temp;
    }

    public static void MessageBox(final Context ClassName, final String Msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassName, R.style.CustomAlertDialog);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        LayoutInflater inflater = ((Activity) ClassName).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        TextView lblTitle = dialogView.findViewById(R.id.lblTitle);
        TextView lblDescription = dialogView.findViewById(R.id.lblDescription);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        btnOk.setText("Ok");
        lblTitle.setText("Message");
        lblDescription.setText(Msg);
        alertDialog.getWindow().setContentView(dialogView);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Msg.equals("Saved Successfully")) {
                    ((Activity) ClassName).finish();
                }
                alertDialog.dismiss();
            }
        });
    }

    public static void MessageBox_CloseButton(final Context ClassName,String title, String Msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassName, R.style.CustomAlertDialog);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        LayoutInflater inflater = ((Activity) ClassName).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_info_dialog, null);
        TextView lblTitle = dialogView.findViewById(R.id.lblTitle);
        TextView lblDescription = dialogView.findViewById(R.id.lblDescription);
        ImageView btnClose = dialogView.findViewById(R.id.btnClose);
        lblTitle.setText(title);
        lblDescription.setText(Msg);
        alertDialog.getWindow().setContentView(dialogView);

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public static void ConfirmationDialog(final Context ClassName, final String Msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassName, R.style.CustomAlertDialog);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        LayoutInflater inflater = ((Activity) ClassName).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        TextView lblTitle = dialogView.findViewById(R.id.lblTitle);
        TextView lblDescription = dialogView.findViewById(R.id.lblDescription);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        btnOk.setText("Ok");
        lblTitle.setText("Message");
        lblDescription.setText(Msg);
        alertDialog.getWindow().setContentView(dialogView);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Msg.equals("Saved Successfully")) {
                    ((Activity) ClassName).finish();
                }
                alertDialog.dismiss();
            }
        });
    }

    public static void MessageBoxNotClose(Context ClassName, String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassName);
        builder.setMessage(Msg)
                .setTitle("Message")
                .setCancelable(true)
                //.setIcon(R.drawable.logo)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", null);
        builder.show();
    }

    //Check whether internet connectivity available or not
    //----------------------------------------------------------------------------------------------
    public static boolean haveNetworkConnection(Context con) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception ignored) {

        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static String SelectedSpinnerValue(String SelectedTest, String SplitValue) {
        String[] D = SelectedTest.split(SplitValue);
        return D[0];
    }

    // Creating our initial tables
    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("Create Table abc(sid varchar(10))");
    }

    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    //Check the existence of database table
    //----------------------------------------------------------------------------------------------
    public boolean TableExists(String TableName) {
        Cursor c = null;
        boolean tableExists = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            c = db.rawQuery("Select * from " + TableName, null);
            tableExists = true;
            c.close();
            db.close();
        } catch (Exception ignored) {
        }
        return tableExists;
    }

    //Create database tables
    //----------------------------------------------------------------------------------------------
    public void CreateTable(String TableName, String SQL) {
        //SQLiteDatabase db = getWritableDatabase();
        try {
            if (!TableExists(TableName)) {
                SaveData(SQL);
            }
        }catch(Exception ignored){

        }
        /*finally {
            db.close();
        }*/
    }

    //Read data from database and return to Cursor variable
    //----------------------------------------------------------------------------------------------
    public Cursor ReadData(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cur = db.rawQuery(SQL, null);
        //db.close();
        return db.rawQuery(SQL, null);
    }

    //Check existence of data in database
    //----------------------------------------------------------------------------------------------
    public boolean Existence(String SQL) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery(SQL, null);
            if (cur.getCount() == 0) {
                cur.close();
                db.close();
                return false;
            } else {
                cur.close();
                db.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Return single result based on the SQL query
    //----------------------------------------------------------------------------------------------
    public String ReturnSingleValue(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            retValue = cur.getString(0);
            cur.moveToNext();
        }
        cur.close();
        db.close();
        return retValue;
    }

    //Save/Update/Delete data in to database
    //----------------------------------------------------------------------------------------------
    public String SaveData(String SQL) {
        String response = "";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(SQL);
        }catch(Exception ex){
            response = ex.getMessage();
        }finally {
            db.close();
        }
        return response;
    }

    //Generate data list
    //----------------------------------------------------------------------------------------------
    public List<String> getDataList(String SQL) {
        List<String> data = new ArrayList<String>();
        Cursor cursor = ReadData(SQL);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return data;
    }

    public String[] getArrayList(String SQL) {
        List<String> data = new ArrayList<String>();
        Cursor cursor = ReadData(SQL);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        String[] mStringArray = new String[data.size()];
        mStringArray = data.toArray(mStringArray);

        cursor.close();
        return mStringArray;
    }

    //Array adapter for spinner item
    //----------------------------------------------------------------------------------------------
    public ArrayAdapter<String> getArrayAdapter(String SQL) {
        List<String> dataList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Creating adapter for spinner
        return new ArrayAdapter<String>(this.dbContext, R.layout.multiline_spinner_dropdown_item, dataList);
    }

    //Find the variable positions in an array list
    //----------------------------------------------------------------------------------------------
    public int VarPosition(String VariableName, String[] ColumnList) {
        int pos = 0;
        for (int i = 0; i < ColumnList.length; i++) {
            if (VariableName.trim().equalsIgnoreCase(ColumnList[i].trim())) {
                pos = i;
                i = ColumnList.length;
            }
        }
        return pos;
    }

    public List<String> DataListJSON(String SQL) {
        Gson gson = new Gson();
        DownloadDataJSON dload = new DownloadDataJSON();
        String response = null;
        try {
            response = dload.execute(SQL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> data;
        DownloadClass responseData = gson.fromJson(response, DownloadClass.class);
        data = responseData.getdata();
        return data;
    }

    public List<DataClassProperty> GetDataListJSON(String VariableList, String TableName, String UniqueField) {
        String SQL = "";
        SQL = "Select " + VariableList + " from " + TableName + " where Upload='2'";

        Cursor cur_H = ReadData(SQL);
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        StringBuilder DataList = new StringBuilder();
        String[] Count = VariableList.split(",");
        String[] UField = UniqueField.split(",");

        StringBuilder WhereClause = new StringBuilder();
        String[] VarData;
        int varPos = 0;
        while (!cur_H.isAfterLast()) {

            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    DataList=new StringBuilder();
                    if (cur_H.getString(c) == null) {
                    }
                    else if (cur_H.getString(c).equalsIgnoreCase("null")) {
                    }
                    else {
                        DataList.append(cur_H.getString(c).trim());
                    }

                } else {
                    if (cur_H.getString(c) == null) {
                        DataList.append("^");
                    }
                    else if (cur_H.getString(c).equalsIgnoreCase("null")) {
                        DataList.append("^");
                    }
                    else {
                        DataList.append("^").append(cur_H.getString(c).trim());
                    }
                }
            }

            //Prepare Where Clause
            VarData = DataList.toString().split("\\^");
            varPos = 0;


            WhereClause=new StringBuilder();
            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j], Count);
                if (j == 0) {
                    WhereClause.append(UField[j]).append("=").append("'").append(VarData[varPos]).append("'");
                } else {
                    WhereClause.append(" and ").append(UField[j]).append("=").append("'").append(VarData[varPos]).append("'");
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList.toString());
            d.setuniquefieldwithdata(WhereClause.toString());
            data.add(d);

            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    private String Get_TimeStamp(String Table_Name){
        return ReturnSingleValue("Select timestamp from local_index_datasync where table_name='"+ Table_Name +"'");
    }

    public void Sync_Download(String TableName, String Server_TableName, String WhereClause) {
        //Populate Index Table
        SaveData("Insert into local_index_datasync(table_name,timestamp,modifydate) Select TableName,'','"+ Global.DateTimeNowYMDHMS() +"' from DatabaseTab where not exists(select * from local_index_datasync where table_name=DatabaseTab.TableName)");
        String TIMESTAMP = Get_TimeStamp(TableName);

        //Request for Download Parameter from Server
        //------------------------------------------------------------------------------------------
        New_Download_Request_Class request = new New_Download_Request_Class();
        request.setTableName(TableName);
        request.setServer_TableName(Server_TableName);
        request.setDeviceId("");
        request.setWhereClause(WhereClause);
        request.setTimeStamp(TIMESTAMP==null?"":TIMESTAMP);

        Gson gson = new Gson();
        String request_json = gson.toJson(request);

        Download_Request down_parameter_request = new Download_Request();
        String response = null;
        try {
            response = down_parameter_request.execute(request_json).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(response!=null) {
            //Data Download Parameter
            //------------------------------------------------------------------------------------------
            Type collType = new TypeToken<New_Download_Response_Class>() {}.getType();

            New_Download_Response_Class responseData = gson.fromJson(response, collType);
            String RequestSQL   = responseData.getRequestSQL();
            int totalBatch      = responseData.getTotal_batch();
            int batchSize       = responseData.getBatch_Size();
            String VariableList = responseData.getVariableList();
            String UniqueField  = responseData.getUniqueField();

            //Execute batch download
            //------------------------------------------------------------------------------------------
            String Res = "";
            String Final_RequestSQL = "";

            try {
                for (int i = 0; i < totalBatch; i++) {
                    Final_RequestSQL  = RequestSQL.replace("~", String.valueOf(batchSize*(i)));
                    if(i==totalBatch-1) TIMESTAMP = Global.DateTimeNowYMDHMS();

                    Res = DownloadJSON_New(Final_RequestSQL, TableName, VariableList, UniqueField);

                    if(i==totalBatch-1){
                        SaveData("Update local_index_datasync set timestamp='"+ TIMESTAMP +"' where table_name='"+ TableName +"'");
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public String DownloadJSON_New(String SQL, String TableName, String ColumnList, String UniqueField) {
        String response = "";
        String resp = "";
        //String TIME_STAMP = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            Gson gson = new Gson();
            Type collType = new TypeToken<New_DownloadClass>() {}.getType();
            New_DownloadClass responseData = gson.fromJson(response, collType);

            String[] UField = UniqueField.split(",");
            String[] VarList = ColumnList.split(",");

            StringBuilder Position_UID = new StringBuilder();
            for (String s : UField) {
                Position_UID.append(Position_UID.length()==0?VarPosition(s, VarList):","+VarPosition(s, VarList));
            }
            String[] VarPos_UID =  Position_UID.toString().split(",");

            //--------------------------------------------------------------------------------------
            String modifyDate = "";
            StringBuilder UID = new StringBuilder();

            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data     = new ArrayList<DataClassProperty>();

            String downloadSyncStatus = "";

            assert responseData != null;
            String temp = "";
            if (responseData.getdata().size() > 0) {
                StringBuilder SQLBuilder = new StringBuilder("Insert or replace into " + TableName + "(" + ColumnList + ")Values");
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String[] VarData = split(responseData.dataString.get(i).getData(), '^');

                    temp = responseData.dataString.get(i).getData().replace("'","''");
                    if (i == 0) {
                        SQLBuilder.append("('").append(temp.replace("^", "','").replace("null", "")).append("')");
                    } else {
                        SQLBuilder.append(",('").append(temp.replace("^", "','").replace("null", "")).append("')");
                    }

                    /*if(i==responseData.getdata().size()-1){
                        TIME_STAMP = "TS#"+ responseData.dataString.get(i).getUploadDT();
                    }*/
                }
                SQL = SQLBuilder.toString();

                //If there have no error then response send back to server
                downloadSyncStatus = SaveData(SQL);

                if(downloadSyncStatus.length()>0){
                    //Process each record separately
                    StringBuilder SQLBuilder_Single;
                    sync_log objlog;
                    sync_log_class objsynclog_data = new sync_log_class();
                    List<sync_log> objList = new ArrayList<>();
                    StringBuilder WhereClause;

                    for (int i = 0; i < responseData.getdata().size(); i++) {
                        SQLBuilder_Single = new StringBuilder("Insert or replace into " + TableName + "(" + ColumnList + ")Values");

                        temp = responseData.dataString.get(i).getData().replace("'","''");
                        SQLBuilder_Single.append("('").append(temp.replace("^", "','").replace("null", "")).append("')");

                        SQL = SQLBuilder_Single.toString();
                        downloadSyncStatus = SaveData(SQL);

                        String[] VarData = split(responseData.dataString.get(i).getData(), '^');
                        /*if(i==responseData.getdata().size()-1){
                            TIME_STAMP = "TS#"+ responseData.dataString.get(i).getUploadDT();
                        }*/

                        if(downloadSyncStatus.length()>0){
                            int vpos = 0;
                            WhereClause = new StringBuilder();
                            for(String pos: VarPos_UID){
                                if(vpos==0) WhereClause.append(" where " + VarList[Integer.parseInt(pos)] +"="+ VarData[Integer.parseInt(pos)]);
                                else  WhereClause.append(" and " + VarList[Integer.parseInt(pos)] +"="+ VarData[Integer.parseInt(pos)]);

                                vpos +=1;
                            }
                            objlog = new sync_log();

                            objlog.setUserId("");
                            objlog.setTableName(TableName);
                            objlog.setStatement(SQLBuilder_Single.toString());
                            objlog.setError(downloadSyncStatus);
                            objlog.setWhereClause(WhereClause.toString());
                            objList.add(objlog);
                        }
                    }
                    objsynclog_data.setdata(objList);

                    //If there have any error then response back to server
                    if(objList.size()>0) {
                        resp = "found error";
                        Gson gson1 = new Gson();
                        String json1 = gson1.toJson(objsynclog_data);

                        UploadDataJSON_ErrorLog u = new UploadDataJSON_ErrorLog();
                        try {
                            String resp1 = u.execute(json1).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        resp = "";
                    }
                }
            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }

    //Download list item from server based on SQl query
    public List<String> DownloadJSONList(String SQL) {
        String response = "";
        String resp = "";

        List<String> dataStatus = new ArrayList<>();
        try {

            DownloadDataList dload = new DownloadDataList();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);
            dataStatus = responseData.getdata();

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return dataStatus;
    }

    //15 Mar 2020
    private void UploadProcess(String JSON_Data, String TableName, Gson gson){
        String response = "";
        UploadDataJSON u = new UploadDataJSON();
        try {
            response = u.execute(JSON_Data).get();

            //Process Response
            if (response != null) {
                Type collType = new TypeToken<DownloadClass>() {
                }.getType();

                DownloadClass responseData = gson.fromJson(response, collType);

                //upload all records as successfull upload then update status of upload=2 for unsuccessfull
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    SaveData("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //23 Jun 2017
    public String UploadJSON(String TableName, String ColumnList, String UniqueFields) {
        String response = "";
        List<DataClassProperty> data = GetDataListJSON(ColumnList, TableName, UniqueFields);

        List<DataClassProperty> TempData = null;
        DataClassProperty d = null;
        DataClass dt = null;
        Gson gson = null;

        String json = "";
        int LoopStart = 0;
        int LoopEnd = 1000;
        if(data.size()>1000){

            while(LoopEnd < data.size()+1) {

                TempData = new ArrayList<DataClassProperty>();
                for (int i = LoopStart; i < LoopEnd; i++) {
                    d = new DataClassProperty();
                    d.setdatalist(data.get(i).getdatalist());
                    d.setuniquefieldwithdata(data.get(i).getuniquefieldwithdata());
                    TempData.add(d);
                }

                //----------------------------------------------------------------------------------
                dt = new DataClass();
                dt.settablename(TableName);
                dt.setcolumnlist(ColumnList);
                dt.setuniquefields(UniqueFields);

                dt.setdata(TempData);
                gson = new Gson();
                json = gson.toJson(dt);
                UploadProcess(json,TableName,gson);
                //----------------------------------------------------------------------------------
                LoopStart = LoopEnd;


                if(LoopEnd == data.size()) LoopEnd+= 1;
                else {
                    LoopEnd += 1000;
                    //LoopEnd = LoopEnd > data.size() ? data.size() : LoopEnd;
                    LoopEnd = Math.min(LoopEnd, data.size());
                }

            }
        }else if(data.size()==0){

        }else if(data.size()<=1000){
            dt = new DataClass();
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            dt.setuniquefields(UniqueFields);

            dt.setdata(data);
            gson = new Gson();
            json = gson.toJson(dt);
            UploadProcess(json,TableName,gson);
        }

        return response;
    }

    // Getting result from database server based on SQL
    //----------------------------------------------------------------------------------------------
    public String ReturnResult(String MethodName, String SQL) {
        ReturnResult r = new ReturnResult();
        String response = "";
        r.Method_Name = MethodName;
        r.SQLStr = SQL;
        try {
            response = r.execute("").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
        return response;
    }

    public boolean RebuildDatabase(final ProgressDialog progDialog, Handler progHandler, String DeviceID) {
        boolean process_successfull = false;
        try {
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(1);
                    progDialog.setMessage("Rebuilding database ...");
                }
            });
            List<String> listItem = new ArrayList<String>();
            listItem = DownloadJSONList("Select TableName+'^'+TableScript from DatabaseTab where TableScript is not null or len(TableScript)>0");
            for (int i = 0; i < listItem.size(); i++) {
                String[] VarData = Connection.split(listItem.get(i), '^');
                CreateTable(VarData[0], VarData[1]);
            }

            //Master Database Sync (Required for any database system)
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(5);
                    progDialog.setMessage("Downloading Master Data ...");
                }
            });

            this.Sync_Download("DeviceList", "DeviceList","DeviceID='"+ DeviceID +"'");
            this.Sync_Download("DatabaseTab", "DatabaseTab","");
            this.Sync_Download("DataCollector", "DataCollector", "");
            this.Sync_Download("Language", "Language", "");

            //Master Database Sync (Required for any database system)
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(5);
                    progDialog.setMessage("Downloading Data ...");
                }
            });

            //--------------------------------------------------------------------------------------

            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(15);
                    progDialog.setMessage("Downloading Master Data ...");
                }
            });
            this.Sync_Download("module_variable","module_variable", "");
            this.Sync_Download("module_variable_agegrp","module_variable_agegrp", "");
            this.Sync_Download("module_variable_language","module_variable_language", "");
            this.Sync_Download("module_variable_list","module_variable_list", "");
            this.Sync_Download("module_content","module_content", "");
            this.Sync_Download("module_control_type","module_control_type", "");

            //Download data from server
            //------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(20);
                    progDialog.setMessage("Downloading Catchment Area Data ...");
                }
            });
            this.Sync_Download("zilla","zilla aa", "");
            this.Sync_Download("upazila","upazila", "");
            this.Sync_Download("unions","unions", "");
            this.Sync_Download("Cluster","Cluster", "");
            this.Sync_Download("mouza","mouza", "");
            this.Sync_Download("village","village", "");

            //Update status on server
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(100);
                    progDialog.setMessage("Finishing Setting ...");
                }
            });

            //String DoneDeviceSetting = Done_Device_Setting(DeviceID);
            //ExecuteCommandOnServer("Update DeviceList set Setting='2' where DeviceId='" + DeviceID + "'");
            process_successfull = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return process_successfull;
    }



    public String[] Sync_Parameter(String TableName) {
        String VariableList = "";
        String UniqueField = "";
        String SQLStr = "";
        String SQL_VariableList = "";

        Cursor cur_H = ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='" + TableName + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            SQLStr = "Select " + cur_H.getString(cur_H.getColumnIndex("columnlist")) + " from " + TableName + " Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            SQL_VariableList = Convert_VariableList(TableName, VariableList);
            UniqueField = cur_H.getString(cur_H.getColumnIndex("uniqueid"));

            cur_H.moveToNext();
        }
        cur_H.close();

        return new String[]{SQLStr,VariableList,UniqueField,SQL_VariableList
        };
    }

    private String Convert_VariableList(String TableName, String VariableList) {
        StringBuilder finalVariableList = new StringBuilder();
        String[] tempList = VariableList.split(",");
        String tempVar = "";
        String temp = "";
        String[] DateVarList = DateVariableList(TableName).split(",");
        int matched = 2;
        for (String s : tempList) {
            temp = s;
            matched = 2;

            for (String value : DateVarList) {
                if (temp.equalsIgnoreCase(value)) {
                    matched = 1;
                    break;
                }
            }

            if (matched == 1) {
                if (temp.equalsIgnoreCase("endt") | temp.equalsIgnoreCase("modifydate") | temp.equalsIgnoreCase("uploaddt"))
                    finalVariableList.append(finalVariableList.length() == 0 ? "Convert(varchar(19)," + s + ",120)" : ", Convert(varchar(19)," + s + ",120)");
                else
                    finalVariableList.append(finalVariableList.length() == 0 ? "Convert(varchar(10)," + s + ",120)" : ", Convert(varchar(10)," + s + ",120)");
            } else {
                if (temp.equalsIgnoreCase("upload"))
                    finalVariableList.append(finalVariableList.length() == 0 ? "'1'" : ", '1'");
                else
                    finalVariableList.append(finalVariableList.length() == 0 ? s : ", " + s);
            }
        }
        return finalVariableList.toString();
    }

    private String DateVariableList(String TableName) {
        Cursor cur_H = ReadData("PRAGMA table_info('" + TableName + "')");
        cur_H.moveToFirst();
        String temp = "";
        String type = "";
        String name = "";
        StringBuilder dateVariable = new StringBuilder();
        while (!cur_H.isAfterLast()) {
            type = cur_H.getString(cur_H.getColumnIndex("type"));
            name = cur_H.getString(cur_H.getColumnIndex("name")).toLowerCase();

            if (type.equalsIgnoreCase("date") | type.equalsIgnoreCase("datetime")) {
                dateVariable.append(dateVariable.length() == 0 ? cur_H.getString(cur_H.getColumnIndex("name")) : "," + cur_H.getString(cur_H.getColumnIndex("name")));
            }

            cur_H.moveToNext();
        }
        cur_H.close();

        return dateVariable.toString();
    }

    //Upload data to server
    public void Sync_Upload(List<String> tableList) {
        for (int i = 0; i < tableList.size(); i++) {
            Sync_Upload_Process(tableList.get(i).toString());
        }
    }

    public void Sync_Upload_Process(String TableName) {
        String VariableList = "";
        String UniqueField = "";
        String SQLStr = "";
        String Res = "";

        Cursor cur_H = ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='" + TableName + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            SQLStr = "Select " + cur_H.getString(cur_H.getColumnIndex("columnlist")) + " from " + TableName + " Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();

        Res = UploadJSON(TableName, VariableList, UniqueField);
    }



    //24 Mar 2022
    public void Sync_DatabaseStructure()
    {
        String response = "";
        String TableName = "DatabaseTab";
        String TIMESTAMP = Get_TimeStamp(TableName);

        //Request for Download Parameter from Server
        //------------------------------------------------------------------------------------------
        New_Download_Request_Class request = new New_Download_Request_Class();
        request.setTableName(TableName);
        request.setServer_TableName(TableName);
        request.setDeviceId("");
        request.setWhereClause("");
        request.setTimeStamp(TIMESTAMP==null?"":TIMESTAMP);

        Gson gson = new Gson();
        String request_json = gson.toJson(request);

        Download_Request down_parameter_request = new Download_Request();
        try {
            response = down_parameter_request.execute(request_json).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(response!=null) {
            Type collType = new TypeToken<New_Download_Response_Class>() {}.getType();

            New_Download_Response_Class responseData = gson.fromJson(response, collType);
            String RequestSQL   = responseData.getRequestSQL();
            //int totalBatch      = responseData.getTotal_batch();
            //int batchSize       = responseData.getBatch_Size();
            String VariableList = responseData.getVariableList();
            String UniqueField  = responseData.getUniqueField();

            String Final_RequestSQL = RequestSQL.replace("~", String.valueOf(0));
            Sync_DatabaseTab(Final_RequestSQL, TableName, VariableList, UniqueField);
        }
    }

    private void Sync_DatabaseTab(String SQL,String TableName,String ColumnList, String UniqueField)
    {
        String response = "";
        String TIMESTAMP = Global.DateTimeNowYMDHMS();
        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            Gson gson = new Gson();
            Type collType = new TypeToken<New_DownloadClass>() {}.getType();
            New_DownloadClass responseData = gson.fromJson(response, collType);

            String[] UField = UniqueField.split(",");
            String[] VarList = ColumnList.split(",");

            StringBuilder Position_UID = new StringBuilder();
            for (String s : UField) {
                Position_UID.append(Position_UID.length()==0?VarPosition(s, VarList):","+VarPosition(s, VarList));
            }

            String downloadSyncStatus = "";

            assert responseData != null;
            String temp = "";
            if (responseData.getdata().size() > 0) {
                StringBuilder SQLBuilder_Single;

                int VarPos_TabName = VarPosition("TableName",VarList);
                int VarPos_TableScript = VarPosition("TableScript",VarList);
                int VarPos_ColumnList = VarPosition("ColumnList",VarList);
                for (int i = 0; i < responseData.getdata().size(); i++) {

                    SQLBuilder_Single = new StringBuilder("Insert or replace into " + TableName + "(" + ColumnList + ")Values");

                    temp = responseData.dataString.get(i).getData().replace("'","''");
                    SQLBuilder_Single.append("('").append(temp.replace("^", "','").replace("null", "")).append("')");

                    SQL = SQLBuilder_Single.toString();

                    try {
                        downloadSyncStatus = SaveData(SQL);
                        TableStructureSync(
                                Connection.split(responseData.dataString.get(i).getData(),'^')[VarPos_TabName],
                                Connection.split(responseData.dataString.get(i).getData(),'^')[VarPos_ColumnList].split(","),
                                Connection.split(responseData.dataString.get(i).getData(),'^')[VarPos_TableScript]);
                    }catch (Exception ignored){

                    }
                }
                String resp = SaveData("Update local_index_datasync set timestamp='"+ TIMESTAMP +"' where table_name='"+ TableName +"'");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TableStructureSync
    public void TableStructureSync(String TableName, String[] Server, String tableScript) {
        //Creating Table if not exists
        try {
            CreateTable(TableName, tableScript);
        }catch (Exception ignored){

        }

        //Server database
        String[] local = GetColumnListArray(TableName);

        String[] C;
        boolean matched = false;
        String newVariable = "";

        //matched database columns(local and server)
        for (String s : Server) {
            matched = false;
            for (int j = 0; j < local.length; j++) {
                newVariable = s;
                if (s.trim().equalsIgnoreCase(local[j].trim())) {
                    matched = true;
                    j = local.length;
                }
            }
            if (!matched) {
                String SaveResp = SaveData("Alter table " + TableName + " add column " + newVariable + " varchar(50) default ''");
            }
        }
    }

    //To get the list of columns(string array) in table
    //----------------------------------------------------------------------------------------------
    public String[] GetColumnListArray(String TableName)
    {
        Cursor cur = ReadData("SELECT * FROM " + TableName + " WHERE 0");
        String[] columnNames;
        try {
            columnNames = cur.getColumnNames();
        } finally {
            cur.close();
        }
        return columnNames;
    }

    //02 Dec 2018
    public long InsertData(String TableName, ContentValues content_value) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insertOrThrow(TableName, null, content_value);
    }

    public boolean UpdateData_Old(String TableName, String UniqueID_Field, String UniqueID, ContentValues content_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TableName, content_value, UniqueID_Field + " = ? ", new String[]{UniqueID});
        return true;
    }

    //02 Dec 2018
    public boolean UpdateData(String TableName, String UniqueID_Field_Name, String UniqueID_Field_Data, ContentValues content_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String ID_STRING = "";
        String[] ID_LIST = UniqueID_Field_Name.split(",");
        String[] DATA_LIST = UniqueID_Field_Data.split(",");
        String[] FINAL_DATA_LIST = new String[DATA_LIST.length];

        for (int i=0;i<ID_LIST.length;i++)
        {
            ID_STRING += ID_STRING.length()==0 ? ID_LIST[i].trim()+"=?" : " and "+ ID_LIST[i].trim()+"=?";
            FINAL_DATA_LIST[i] = DATA_LIST[i].trim();
        }
        db.update(TableName, content_value, ID_STRING, FINAL_DATA_LIST);
        return true;
    }

    //02 Dec 2018
    public int DeleteData(String TableName, String UniqueID_Field_Name, String UniqueID_Field_Data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String ID_STRING = "";
        String DATA_STRING = "";
        String[] ID_LIST = UniqueID_Field_Name.split(",");
        String[] DATA_LIST = UniqueID_Field_Data.split(",");

        for (int i=0;i<ID_LIST.length;i++)
        {
            ID_STRING += ID_STRING.length()==0 ? ID_LIST[i].trim()+"=?" : " and "+ ID_LIST[i].trim()+"=?";
            DATA_STRING += DATA_STRING.length()==0 ? DATA_LIST[i].trim() : ", "+ DATA_LIST[i].trim();
        }
        return db.delete(TableName, ID_STRING, new String[]{DATA_STRING});
    }

    public Cursor GetData(String TableName, String UniqueID_Field_Name, String UniqueID_Field_Data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String ID_STRING = "";
        String DATA_STRING = "";
        String[] ID_LIST = UniqueID_Field_Name.split(",");
        String[] DATA_LIST = UniqueID_Field_Data.split(",");

        for (int i=0;i<ID_LIST.length;i++)
        {
            ID_STRING += ID_STRING.length()==0 ? ID_LIST[i].trim()+"=?" : " and "+ ID_LIST[i].trim()+"=?";
            DATA_STRING += DATA_STRING.length()==0 ? DATA_LIST[i].trim() : ", "+ DATA_LIST[i].trim();
        }
        Cursor res = db.rawQuery("SELECT * FROM " + TableName + " WHERE " + ID_STRING, new String[]{DATA_STRING});
        return res;
    }

    //02 Dec 2018
    public Cursor GetData_All(String TableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName, null);
        return res;
    }

    public void DatabaseUpload(String DeviceID) {
        //Upload File from Specific Folder
        String[] FilePathStrings;
        String[] FileNameStrings;
        File[] listFile;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder);
        file.mkdirs();
        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();

                //Upload file to server
                FileUpload myTask = new FileUpload();
                String[] params = new String[2];
                if (listFile[i].getName().equalsIgnoreCase(ProjectSetting.DatabaseName)) {
                    params[0] = listFile[i].getName();
                    params[1] = DeviceID + "_" + Global.CurrentDMY() + "_" + listFile[i].getName();
                    myTask.execute(params);
                }
            }
        }
    }

    private static void zipDatabase(String DeviceID)
    {
        CompressZip compressZip = new CompressZip();
        String[] dbFile = new String[1];
        dbFile[0] = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder + File.separator + ProjectSetting.DatabaseName;
        String dbFolder = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder;
        String output   = ProjectSetting.zipDatabaseName;
        compressZip.zip(dbFile, dbFolder, output);
    }

    public static void DatabaseUploadZip(String DeviceID) {

        //Compress database
        zipDatabase(DeviceID);

        //Upload File from Specific Folder
        String[] FilePathStrings;
        String[] FileNameStrings;
        File[] listFile;

        //
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder);
        file.mkdirs();
        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();

                //Upload file to server
                FileUpload myTask = new FileUpload();
                String[] params = new String[2];

                if (listFile[i].getName().equalsIgnoreCase(ProjectSetting.zipDatabaseName)) {
                    params[0] = listFile[i].getName();
                    params[1] = DeviceID + "_" + Global.CurrentDMY() + "_" + listFile[i].getName();
                    myTask.execute(params);
                }
            }
        }
    }

    public static void SyncDataService()
    {
        try {
            Connection C = new Connection(dbContext);
            C.Sync_DatabaseStructure();
            C.Sync_Download("PatientInfo","PatientInfo", "");
            //C.Sync_Download("data_chart","data_chart", "");

            //Regular data sync
            //--------------------------------------------------------------------------------------
            /*C.Sync_Download("DeviceList","DeviceList", UniqueID,"DeviceID='"+ UniqueID +"'");

            C.Sync_Download("module_variable","module_variable", UniqueID, "");
            C.Sync_Download("module_variable_list","module_variable_list", UniqueID, "");
            C.Sync_Download("module_variable_language","module_variable_language", UniqueID, "");*/
        }
        catch(Exception ex)
        {
        }

    }

    public static void SyncDataService_DatabaseStructure()
    {
        try {
            Connection C = new Connection(dbContext);
            C.Sync_DatabaseStructure();
        }
        catch(Exception ex)
        {
        }

    }



    public void DataSync(String DeviceID,final ProgressDialog progDialog, Handler progHandler){
        List<String> download_tableList = new ArrayList<String>();
        List<String> upload_tableList   = new ArrayList<String>();

        Cursor cur = ReadData("Select TableName,Sync_Download,Sync_Upload from DatabaseTab where Sync_Download='Y' or Sync_Upload='Y'");
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            try {
                if(cur.getString(cur.getColumnIndex("Sync_Download")).equals("Y"))
                    download_tableList.add(cur.getString(cur.getColumnIndex("TableName")));
                if(cur.getString(cur.getColumnIndex("Sync_Upload")).equals("Y"))
                    upload_tableList.add(cur.getString(cur.getColumnIndex("TableName")));
            }catch (Exception ignored){

            }
            cur.moveToNext();
        }
        cur.close();

        int total_updown_count = upload_tableList.size() + download_tableList.size();
        int progress_count = 100/total_updown_count;
        int count = 0;

        //Upload
        for (int i = 0; i < upload_tableList.size(); i++) {
            count += progress_count;
            try {
                final int finalCount = count;
                progHandler.post(new Runnable() {
                    public void run() {
                        progDialog.setProgress(finalCount);
                        progDialog.setMessage("Uploading data ...");
                    }
                });
                DataSync_Upload(upload_tableList.get(i).toString());
            }catch(Exception ignored){

            }
        }

        //Download
        for (int i = 0; i < download_tableList.size(); i++) {
            count += progress_count;
            try {
                final int finalCount = count;
                progHandler.post(new Runnable() {
                    public void run() {
                        progDialog.setProgress(finalCount);
                        progDialog.setMessage("Downloading data ...");
                    }
                });
                DataSync_Download(download_tableList.get(i).toString(), DeviceID, "");
            }catch(Exception ignored){

            }
        }

        progHandler.post(new Runnable() {
            public void run() {
                progDialog.setProgress(100);
                progDialog.setMessage("Finishing data synchronization ...");
            }
        });
    }


    public void DataSync_Download(String TableName, String UserId, String WhereClause) {
        String SQL = "";
        String Res = "";

        //Retrieve sync parameter
        //------------------------------------------------------------------------------------------
        //0-TableName, 1-TableScript, 2-ColumnList, 3-UniqueID, 4-Sync_Upload, 5-Sync_Download, 6-BatchSize, 7-indexid
        String[] SyncParam = DataSync_Parameter(TableName);
        int batchSize  = Integer.valueOf((SyncParam[6].length()==0 | SyncParam[6].equals("0") ? "500" :SyncParam[6]));
        String IndexId     = SyncParam[7];
        String VariableList = SyncParam[2];
        String UniqueField  = SyncParam[3];

        //Calculate Batch Size
        int totalRecords = 0;
        int totalBatch = 1;

        if(IndexId.length()==0) {
            SQL = "Select count(*)totalRec from " + TableName + "";
            if(WhereClause.length()>0)
                SQL += " where "+ WhereClause;
        }
        else {
            SQL = "Select count(*)totalRec from " + TableName + " where modifyDate>'" + IndexId + "'";
            if(WhereClause.length()>0)
                SQL += " and "+ WhereClause;
        }

        String totalRec = ReturnResult("ReturnSingleValue", SQL);
        if(Integer.parseInt(totalRec) > 0){
            totalRecords = Integer.parseInt(totalRec);
            totalBatch = totalRecords / batchSize;

            /*if ((totalRecords % batchSize) > 0) {
                totalBatch = totalBatch + 1;
            }*/
            totalBatch = totalBatch + 1;

            //Execute batch download
            //------------------------------------------------------------------------------------------
            for (int i = 0; i < totalBatch; i++) {
                if(IndexId.length()>0) {
                    SQL = "Select top " + batchSize + " " + VariableList + " from " + TableName + " where modifyDate>'" + IndexId + "'";

                    if(WhereClause.length()>0)
                        SQL += " and "+ WhereClause;
                }else{
                    SQL = "Select top " + batchSize + " " + VariableList + " from " + TableName + "";
                    if(WhereClause.length()>0)
                        SQL += " where "+ WhereClause;
                }

                SQL += " order by modifydate asc";


                Res = DataSync_DownloadJSON(SQL, TableName, VariableList, IndexId);
            }
        }
    }

    public String DataSync_DownloadJSON(String SQL, String TableName, String ColumnList, String IndexId) {
        String response = "";
        String resp = "";

        try {
            DataSync_DownloadDataJSON dload = new DataSync_DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String downloadSyncStatus = "";
            String modifyDate = "";
            String[] VarList = ColumnList.split(",");
            int varPos_modifyDate = 0;

            assert responseData != null;
            if (responseData.getdata().size() > 0) {
                varPos_modifyDate = VarPosition("modifyDate", VarList);

                StringBuilder SQLBuilder = new StringBuilder("Insert or replace into " + TableName + "(" + ColumnList + ")Values");
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String[] VarData = split(responseData.getdata().get(i).toString(), '^');
                    if (i == 0) {
                        SQLBuilder.append("('").append(responseData.getdata().get(i).toString().replace("^", "','").replace("null", "")).append("')");
                    } else {
                        SQLBuilder.append(",('").append(responseData.getdata().get(i).toString().replace("^", "','").replace("null", "")).append("')");
                    }
                    modifyDate = VarData[varPos_modifyDate].toString();

                }
                SQL = SQLBuilder.toString();

                downloadSyncStatus = SaveData(SQL);
                if(downloadSyncStatus.length()==0){
                    SaveData("Update sync_index_id set indexid='"+ modifyDate +"' where TableName='"+ TableName +"'");
                }else{
                    resp = downloadSyncStatus;
                }
            }


        } catch (Exception e) {
            resp += e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }


    public void DataSync_Upload(String TableName) {
        String Res = "";

        //Retrieve sync parameter
        //------------------------------------------------------------------------------------------
        //0-TableName, 1-TableScript, 2-ColumnList, 3-UniqueID, 4-Sync_Upload, 5-Sync_Download, 6-BatchSize, 7-indexid
        String[] SyncParam   = DataSync_Parameter(TableName);
        String ColumnList    = SyncParam[2];
        String UniqueFields  = SyncParam[3];
        int batchSize = Integer.valueOf((SyncParam[6].length()==0 | SyncParam[6].equals("0") ? "500" :SyncParam[6]));

        int totalRecords = 0;
        int totalBatch = 1;
        totalRecords = Integer.parseInt(ReturnSingleValue("Select count(*)totalrecord from " + TableName + " where Upload='2'"));
        totalBatch = totalRecords / batchSize;
        totalBatch += 1;

        for(int i=0; i<totalBatch;i++) {
            Res = DataSync_UploadJSON(TableName, ColumnList, UniqueFields, batchSize);
        }
    }

    public String DataSync_UploadJSON(String TableName, String ColumnList, String UniqueFields, Integer BatchSize) {
        String response = "";
        List<DataClassProperty> data = DataSync_GetDataListJSON(ColumnList, TableName, UniqueFields,BatchSize);

        if (data.size() > 0) {
            DataClass dt = new DataClass();
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            dt.setuniquefields(UniqueFields);
            dt.setdata(data);
            Gson gson = new Gson();
            String json = gson.toJson(dt);
            UploadDataJSON u = new UploadDataJSON();

            try {
                response = u.execute(json).get();

                //Process Response
                if (response != null) {
                    DownloadClass d = new DownloadClass();
                    Type collType = new TypeToken<DownloadClass>() {
                    }.getType();
                    DownloadClass responseData = gson.fromJson(response, collType);

                    //upload all records as successfull upload then update status of upload=2 for unsuccessfull
                    for (int i = 0; i < responseData.getdata().size(); i++) {
                        SaveData("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    }

                    /*String UpdateSQL = "";
                    for (int i = 0; i < responseData.getdata().size(); i++) {
                        UpdateSQL += "Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString() +";";
                    }
                    SaveDT(UpdateSQL);*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }


    public String[] DataSync_Parameter(String TableName) {
        String[] ParaList = null;
        Cursor cur_H = ReadData("Select t.TableName, t.TableScript, t.ColumnList, t.UniqueID, t.Sync_Upload, t.Sync_Download, t.BatchSize,ifnull(i.indexid,'')indexid " +
                " from DatabaseTab t" +
                " left outer join sync_index_id i on t.TableName=i.TableName where t.tablename='" + TableName + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            ParaList = new String[]{
                    cur_H.getString(cur_H.getColumnIndex("TableName")),
                    cur_H.getString(cur_H.getColumnIndex("TableScript")),
                    cur_H.getString(cur_H.getColumnIndex("ColumnList")),
                    cur_H.getString(cur_H.getColumnIndex("UniqueID")),
                    cur_H.getString(cur_H.getColumnIndex("Sync_Upload")),
                    cur_H.getString(cur_H.getColumnIndex("Sync_Download")),
                    cur_H.getString(cur_H.getColumnIndex("BatchSize")),
                    cur_H.getString(cur_H.getColumnIndex("indexid"))
            };

            cur_H.moveToNext();
        }
        cur_H.close();

        return ParaList;
    }

    public List<DataClassProperty> DataSync_GetDataListJSON(String VariableList, String TableName, String UniqueField, Integer BatchSize) {
        String SQL = "";
        SQL = "Select " + VariableList + " from " + TableName + " where Upload='2' limit "+ BatchSize;

        Cursor cur_H = ReadData(SQL);
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        String[] Count  = VariableList.toString().split(",");
        String[] UField = UniqueField.toString().split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString().trim();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString().trim();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    public static void LocalizeLanguage(Activity con, int MODULEID, int LANGUAGEID){
        String SQL = "select l.variable_name variablename,v.VariableLabel,l.variable_desc variabledesc,\n" +
                " ifnull(v.ControlType,'') controltype,ifnull(v.Heading,'') heading,l.variable_option variableoption" +
                " from module_variable_language l\n" +
                " left outer join module_variable_list v on l.module_id=v.module_id and l.variable_name=v.variable_name\n" +
                " where l.module_id="+ MODULEID +" and l.language_id="+ LANGUAGEID +"\n" +
                " order by v.VarSl";

        String CONTROL_TYPE = "";
        String HEADING = "";
        String VARIABLE_NAME = "";
        String VARIABLE_OPTION = "";
        String VARIABLE_DESC = "";
        String RESOURCE_NAME = "";
        String RESOURCE_NAME_RADIO = "";
        int RESOURCE_ID = 0;
        int RESOURCE_ID_RADIO = 0;
        String RESOURCE_NAME_SPINNER = "";
        int RESOURCE_ID_SPINNER = 0;
        TextView tv;
        RadioButton rb;
        Spinner sp;
        Connection C = new Connection(con);
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            try {
                VARIABLE_NAME = cur.getString(cur.getColumnIndex("variablename"));
                CONTROL_TYPE = cur.getString(cur.getColumnIndex("controltype"));
                VARIABLE_DESC = cur.getString(cur.getColumnIndex("variabledesc"));
                VARIABLE_OPTION = cur.getString(cur.getColumnIndex("variableoption"));

                if (CONTROL_TYPE.equalsIgnoreCase("label") | CONTROL_TYPE.length() == 0) {
                    RESOURCE_NAME = "lblH" + VARIABLE_NAME;
                    RESOURCE_ID = con.getResources().getIdentifier(RESOURCE_NAME, "id", con.getPackageName());
                    if (RESOURCE_ID != 0) {
                        tv = (TextView) con.findViewById(RESOURCE_ID);
                        if (tv != null) {
                            if (VARIABLE_DESC.length() > 0)
                                tv.setText(VARIABLE_DESC);
                        }
                    }
                } else {
                    RESOURCE_NAME = "Vlbl" + VARIABLE_NAME;
                    RESOURCE_ID = con.getResources().getIdentifier(RESOURCE_NAME, "id", con.getPackageName());
                    if (CONTROL_TYPE.equalsIgnoreCase("RadioButton") & VARIABLE_OPTION.length() > 0) {
                        String[] radio_item = VARIABLE_OPTION.split("#");
                        for (int i = 0; i < radio_item.length; i++) {
                            RESOURCE_NAME_RADIO = "rdo" + VARIABLE_NAME + String.valueOf(i + 1);
                            RESOURCE_ID_RADIO = con.getResources().getIdentifier(RESOURCE_NAME_RADIO, "id", con.getPackageName());
                            if (RESOURCE_ID_RADIO != 0) {
                                rb = (RadioButton) con.findViewById(RESOURCE_ID_RADIO);
                                if (rb != null) {
                                    rb.setText(radio_item[i].toString().split("-")[1].toString());
                                }
                            }
                        }

                    } else if (CONTROL_TYPE.equalsIgnoreCase("DropDown")) {
                        if(VARIABLE_OPTION.length()>0) {
                            String[] spinner_item = VARIABLE_OPTION.split("#");
                            List<String> listSpinner = new ArrayList<String>();
                            ArrayAdapter<String> adptrSpinner = new ArrayAdapter<String>(con, android.R.layout.simple_spinner_item, listSpinner);
                            RESOURCE_NAME_SPINNER = "spn" + VARIABLE_NAME;
                            RESOURCE_ID_SPINNER = con.getResources().getIdentifier(RESOURCE_NAME_SPINNER, "id", con.getPackageName());
                            sp = (Spinner) con.findViewById(RESOURCE_ID_SPINNER);
                            sp.setAdapter(adptrSpinner);

                            for (int i = 0; i < spinner_item.length; i++) {
                                adptrSpinner.add(spinner_item[i].toString());
                            }
                            adptrSpinner.notifyDataSetChanged();
                        }
                    } else {

                    }
                    if (RESOURCE_ID != 0) {
                        tv = (TextView) con.findViewById(RESOURCE_ID);
                        if (tv != null) {
                            if (VARIABLE_DESC.length() > 0)
                                tv.setText(VARIABLE_DESC);
                        }
                    }
                }

                int resourceID = con.getResources().getIdentifier(RESOURCE_NAME, "id", con.getPackageName());
                if (resourceID != 0) {
                    tv = (TextView) con.findViewById(resourceID);
                    if (tv != null) {
                        if (VARIABLE_DESC.length() > 0)
                            tv.setText(VARIABLE_DESC);
                    }
                }
            }catch (Exception ex){

            }
            cur.moveToNext();
        }
        cur.close();
    }

    public String getDeviceUniqueID(Activity activity){
        return Settings.Secure.getString(activity.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    /*public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        try{
            TelephonyManager tm = (TelephonyManager)dbContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (tm.getPhoneCount() == 2) {
                            deviceUniqueIdentifier = tm.getImei(0);// + "##"+tm.getImei(1);
                        }else{
                            deviceUniqueIdentifier = tm.getImei();
                        }
                    }else{
                        if (tm.getPhoneCount() == 2) {
                            deviceUniqueIdentifier = tm.getDeviceId(0);//+ "##"+tm.getDeviceId(1);
                        } else {
                            deviceUniqueIdentifier = tm.getDeviceId();
                        }
                    }
                } else {
                    deviceUniqueIdentifier = tm.getDeviceId();
                }
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = Settings.Secure.getString(dbContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

        }catch (SecurityException ex){

        }

        return deviceUniqueIdentifier;
    }*/


    public String api_DownloadJSON(String SQL, String TableName, String ColumnList, String UniqueField, String UserId) {
        api_DownloadRequestClass dr = new api_DownloadRequestClass();
        dr.sethost_address(ProjectSetting.host_address);
        dr.setdatabase_name(ProjectSetting.database_name);
        dr.setmethod_name("DownloadData");
        dr.setsql(SQL);
        dr.setdevice_id(UserId);

        Gson gson = new Gson();
        String json = gson.toJson(dr);

        int varPos = 0;
        int varPos_modifyDate = 0;

        String response = "";
        String resp = "";
        String IDNO = "";
        String SaveResp = "";

        try {
            //Download from server
            //--------------------------------------------------------------------------------------
            response = new api_DownloadJSONData().execute(json).get();

            Type collType = new TypeToken<api_DownloadClass>() {}.getType();
            //api_DownloadClass responseData = gson.fromJson(response, api_DownloadClass.class);
            api_DownloadClass responseData = gson.fromJson(response, collType);

            String[] UField = UniqueField.trim().split(",");
            String[] VarList = ColumnList.trim().split(",");

            //Position modifydate in table : 15 Sep 2021
            //--------------------------------------------------------------------------------------
            varPos_modifyDate = VarPosition("modifyDate", VarList);

            //Position Unique ID's : 15 Sep 2021
            //--------------------------------------------------------------------------------------
            StringBuilder Position_UID = new StringBuilder();
            for (String s : UField) {
                Position_UID.append(Position_UID.length()==0?VarPosition(s, VarList):","+VarPosition(s, VarList));
            }
            String[] VarPos_UID =  Position_UID.toString().split(",");

            //--------------------------------------------------------------------------------------
            String modifyDate = "";
            StringBuilder UID = new StringBuilder();

            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data     = new ArrayList<DataClassProperty>();

            String downloadSyncStatus = "";

            assert responseData != null;
            String temp = "";
            if (responseData.getdata().size() > 0) {
                StringBuilder SQLBuilder = new StringBuilder("Insert or replace into " + TableName + "(" + ColumnList + ")Values");
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String[] VarData = split(responseData.getdata().get(i), '^');

                    //Generate Unique ID
                    //------------------------------------------------------------------------------
                    for(String pos: VarPos_UID){
                        UID.append(VarData[Integer.parseInt(pos)]);
                    }

                    modifyDate = VarData[varPos_modifyDate];

                    //------------------------------------------------------------------------------
                    //06 Jan 2021
                    temp = responseData.getdata().get(i).replace("'","''");
                    if (i == 0) {
                        SQLBuilder.append("('").append(temp.replace("^", "','").replace("null", "")).append("')");
                    } else {
                        SQLBuilder.append(",('").append(temp.replace("^", "','").replace("null", "")).append("')");
                    }

                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    DataList = TableName + "^" + UID + "^" + UserId + "^" + modifyDate;
                    dd = new DataClassProperty();
                    dd.setdatalist(DataList);
                    dd.setuniquefieldwithdata("" +
                            "TableName='" + TableName + "' and " +
                            "UniqueID='" + UID + "' and " +
                            "UserId='" + UserId + "' and " +
                            "modifyDate='" + modifyDate + "'");
                    dataTemp.add(dd);

                    UID = new StringBuilder();
                }
                SQL = SQLBuilder.toString();

                //If there have no error then response send back to server
                downloadSyncStatus = SaveData(SQL);
                if(downloadSyncStatus.length()==0){
                    data = dataTemp;
                }else{
                    resp = downloadSyncStatus;
                }

                //Update data to Server on sync management
                //------------------------------------------------------------------------------
                DataClass dt = new DataClass();
                dt.settablename("Sync_Management");
                dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
                dt.setuniquefields("TableName, UniqueID, UserId, modifyDate");
                dt.setdata(data);

                Gson gson1 = new Gson();
                String json1 = gson1.toJson(dt);
                String resp1 = "";

                UploadDataJSON u = new UploadDataJSON();

                try {
                    resp1 = u.execute(json1).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }

    //Bari Number
    public String NewBariNumber_ByCluster(String DCode, String UPCode, String UNCode,String Cluster, String VCode, int TotalDigit)
    {
        String BNO = ReturnSingleValue("Select (ifnull(max(cast(Bari as numeric(10))),0)+1)MaxId from Bari where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"'");
        return Global.Right("0000000000"+BNO,TotalDigit);
    }

    public String NewBariNumber_ByVillage(String DCode, String UPCode, String UNCode,String Cluster, String VCode, int TotalDigit)
    {
        String BNO = ReturnSingleValue("Select (ifnull(max(cast(Bari as numeric(10))),0)+1)MaxId from Bari where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"'");
        return Global.Right("0000000000"+BNO,TotalDigit);
    }

    //Household
    public String NewHHNumber(String DCode, String UPCode, String UNCode,String Cluster, String VCode, String Bari, int TotalDigit)
    {
        String HHNO = ReturnSingleValue("Select (ifnull(max(cast(HHNo as numeric(10))),0)+1)MaxId from Household where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"'");
        return Global.Right("0000000000"+HHNO,TotalDigit);
    }

    public String NewLandmarkNumber(String DCode, String UPCode, String UNCode,String Cluster, String VCode)
    {
        String BNO = ReturnSingleValue("Select (ifnull(max(cast(Landmark as numeric(10))),0)+1)MaxId from GPS_Landmark where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"'");
        return Global.Right("0000000000"+BNO,3);
    }
}