package DataSync;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Common.DataClass;
import Common.DataClassProperty;
import Common.DataClass_SQL_Update;
import Common.DownloadDataJSON;
import Common.ExecuteCommand;
import Common.Global;
import Common.ProjectSetting;
import Common.ReturnResult;
import Common.UploadDataJSON;
import Common.UploadDataSQLJSON;
import Common.DownloadClass;
//--------------------------------------------------------------------------------------------------
// Created by TanvirHossain on 17/03/2015.
//--------------------------------------------------------------------------------------------------

public class Sync_Manager extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DB_NAME = ProjectSetting.DatabaseName;
    private static final String DBLocation= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ProjectSetting.DatabaseFolder + File.separator +  DB_NAME;

    // Todo table name
    private static final String TABLE_TODO = "todo_items";

    private Context dbContext;

    public Sync_Manager(Context context) {
        super(context, DBLocation, null, DATABASE_VERSION);
        dbContext=context;

        try
        {

        }
        catch(Exception ex)
        {

        }
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

    //Message Box
    //----------------------------------------------------------------------------------------------
    public static void MessageBox(Context ClassName, String Msg) {
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
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {

        }
        return haveConnectedWifi || haveConnectedMobile;
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
    public boolean TableExists(String TableName)
    {
        Cursor c = null;
        boolean tableExists = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try
        {
            c = db.rawQuery("Select * from "+TableName,null);
            tableExists = true;
            c.close();
            db.close();
        }
        catch (Exception e) {
        }
        return tableExists;
    }

    //Create database tables
    //----------------------------------------------------------------------------------------------
    public void CreateTable(String TableName,String SQL)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(!TableExists(TableName))
        {
            db.execSQL(SQL);
        }
    }

    //Read data from database and return to Cursor variable
    //----------------------------------------------------------------------------------------------
    public Cursor ReadData(String SQL)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur=db.rawQuery(SQL, null);
        return cur;
    }

    //Check existence of data in database
    //----------------------------------------------------------------------------------------------
    public boolean Existence(String SQL)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur=db.rawQuery(SQL, null);
        if(cur.getCount()==0)
        {
            cur.close();
            db.close();
            return false;
        }
        else
        {
            cur.close();
            db.close();
            return true;
        }
    }

    //Return single result based on the SQL query
    //----------------------------------------------------------------------------------------------
    public String ReturnSingleValue(String SQL)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur=db.rawQuery(SQL, null);
        String retValue="";
        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            retValue=cur.getString(0);
            cur.moveToNext();
        }
        cur.close();
        db.close();
        return retValue;
    }

    //Save/Update/Delete data in to database
    //----------------------------------------------------------------------------------------------
    public void Save(String SQL)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        db.close();
    }

    //Generate data list
    //----------------------------------------------------------------------------------------------
    public List<String> getDataList(String SQL){
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

    //Array adapter for spinner item
    //----------------------------------------------------------------------------------------------
    public ArrayAdapter<String> getArrayAdapter(String SQL){
        List<String> dataList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.dbContext,
                android.R.layout.simple_spinner_item, dataList);

        return dataAdapter;
    }


    // Download data from Database Server
    //----------------------------------------------------------------------------------------------
    /*public String DownloadData(String SQLStr, String TableName,String ColumnList, String UniqueField)
    {
        String rep = "";
        String SQL = "";

        int totalDownload=0;
        String DownloadStatus="";
        String WhereClause="";
        int varPos=0;

        try
        {
            DownloadData d=new DownloadData();
            d.Method_Name="DownloadData";
            d.SQLStr=SQLStr;

            String DataArray[] = null;
            DataArray = d.execute("").get();

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            for(int i=0;i<DataArray.length;i++)
            {
                String VarData[] = split(DataArray[i],'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                            }
                        }
                    }

                    Save(SQL);
                    totalDownload+=1;
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString() +"'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                    totalDownload+=1;
                }

                //update download status on server
                //rep = ExecuteCommandOnServer("Update "+ TableName +" set Download='1', DownloadDt='"+ Global.DateTimeNowYMDHMS() +"' Where "+ WhereClause);
            }

            DownloadStatus="Total download completed: "+ totalDownload +" of "+ DataArray.length;

            return DownloadStatus;
        }
        catch(Exception e)
        {
            return "Download Error:"+ e.getMessage();
        }
    }*/


    // Data Update
    //----------------------------------------------------------------------------------------------
    /*public String DataUpdate(String SQLStr, String TableName,String ColumnList, String UniqueField)
    {
        String rep = "";
        String SQL = "";

        int totalDownload=0;
        String DownloadStatus="";
        String WhereClause="";
        int varPos=0;

        try
        {
            DownloadData d=new DownloadData();
            d.Method_Name="DownloadData";
            d.SQLStr=SQLStr;

            String DataArray[]=null;
            DataArray=d.execute("").get();

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            for(int i=0;i<DataArray.length;i++)
            {
                String VarData[] = split(DataArray[i],'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                            }
                        }
                    }

                    Save(SQL);
                    totalDownload+=1;
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString() +"'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                    totalDownload+=1;
                }

                //update download status on server
                rep = ExecuteCommandOnServer("Update "+ TableName +" set Upload='2' Where "+ WhereClause);
            }

            DownloadStatus="Total download completed: "+ totalDownload +" of "+ DataArray.length;

            return DownloadStatus;
        }
        catch(Exception e)
        {
            return "Download Error:"+ e.getMessage();
        }
    }*/

    //Execute command on Database Server
    //----------------------------------------------------------------------------------------------
    public String ExecuteCommandOnServer(String SQLStr)
    {
        String response="";
        String result="";
        ExecuteCommand e=new ExecuteCommand();

        try {
            response = e.execute(SQLStr).get();
            if(response.equals("done"))
            {
                result = "done";
            }
            else
            {
                result = "not";
            }
        }
        catch (Exception e1){
            result = "not";
        }

        return result;
    }

    // Data Upload to Database Server
    //----------------------------------------------------------------------------------------------
    /*public String UploadData(String[] DataArray,String TableName,String ColumnList,String UniqueFields)
    {
        String[] D=new String[DataArray.length];
        String[] Col=ColumnList.split(",");
        String VarName[]=UniqueFields.split(",");
        int varPos=0;
        String WhereClause="";

        String response="";
        int totalRec=0;
        for(int i=0;i<DataArray.length;i++)
        {
            //Generate Where Clause
            String VarData[]=DataArray[i].toString().split("\\^");
            varPos=0;
            WhereClause="";

            for(int j=0; j< VarName.length; j++)
            {
                varPos=VarPosition(VarName[j].toString(),Col);
                if(j==0)
                {
                    WhereClause = VarName[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                }
                else
                {
                    WhereClause += " and "+VarName[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                }
            }

            //Calling web service through class: UploadData
            UploadData u = new UploadData();
            u.TableName			  = TableName;
            u.ColumnList		  = ColumnList;
            u.UniqueFieldWithData = WhereClause;
            try{
                response=u.execute(DataArray[i]).get();
                if(response.equalsIgnoreCase("done"))
                {
                    Save("Update " + TableName + " Set Upload='1' where " + WhereClause);
                    totalRec+=1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Integer.toString(totalRec);
    }*/

    //Find the variable positions in an array list
    //----------------------------------------------------------------------------------------------
    private int VarPosition(String VariableName, String[] ColumnList)
    {
        int pos=0;
        for(int i=0; i< ColumnList.length; i++)
        {
            if(VariableName.trim().equalsIgnoreCase(ColumnList[i].toString().trim()))
            {
                pos=i;
                i=ColumnList.length;
            }
        }
        return pos;
    }


    // Getting array list for Upload with ^ separator from Cursor
    //----------------------------------------------------------------------------------------------
    public String[] GenerateArrayList(String VariableList,String TableName)
    {
        Cursor cur_H;
        cur_H = ReadData("Select "+ VariableList +" from "+ TableName +" where Upload='2'");

        cur_H.moveToFirst();
        String[] Data    = new String[cur_H.getCount()];
        String DataList = "";
        String[] Count=VariableList.toString().split(",");
        int RecordCount=0;

        while(!cur_H.isAfterLast())
        {
            for(int c=0; c<Count.length; c++)
            {
                if(c==0)
                {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if(cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                }
                else
                {
                    if (cur_H.getString(c) == null)
                        DataList+="^"+"";
                    else if(cur_H.getString(c).equals("null"))
                        DataList+="^"+"";
                    else
                        DataList+="^"+cur_H.getString(c).toString();
                }
            }
            Data[RecordCount]=DataList;
            RecordCount+=1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return Data;
    }

    public List<String> DataListJSON(String SQL)
    {
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
        List<String> data = new ArrayList<String>();
        DownloadClass responseData = gson.fromJson(response, DownloadClass.class);
        data = responseData.getdata();
        return data;
    }


    public List<DataClassProperty> GetDataListJSON(String VariableList,String TableName,String UniqueField)
    {
        Cursor cur_H = ReadData("Select "+ VariableList +" from "+ TableName +" where Upload='2'");
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        String[] Count=VariableList.toString().split(",");
        String[] UField = UniqueField.toString().split(",");
        int RecordCount=0;

        String WhereClause="";
        String VarData[];
        int varPos=0;
        while(!cur_H.isAfterLast())
        {
            //Prepare Data List
            for(int c=0; c < Count.length; c++)
            {
                if(c==0)
                {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if(cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString().trim();

                }
                else
                {
                    if (cur_H.getString(c) == null)
                        DataList+="^"+"";
                    else if(cur_H.getString(c).equals("null"))
                        DataList+="^"+"";
                    else
                        DataList+="^"+cur_H.getString(c).toString().trim();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos=0;


            for(int j=0; j< UField.length; j++)
            {
                varPos=VarPosition(UField[j].toString(),Count);
                if(j==0)
                {
                    WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                }
                else
                {
                    WhereClause += " and "+UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount+=1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    public  String DownloadJSON(String SQL,String TableName,String ColumnList, String UniqueField)
    {
        String WhereClause="";
        int varPos=0;

        String response = "";
        String resp = "";

        try{

            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                            }
                        }
                    }

                    Save(SQL);
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString() +"'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                }

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if(dataStatus.size()>0)
            {

            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    public  String DownloadJSON_OutsideChild(String SQL,String TableName,String ColumnList, String UniqueField)
    {
        String WhereClause="";
        int varPos=0;

        String response = "";
        String resp = "";

        try{

            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                TableName = "Child_Outside";

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                            }
                        }
                    }

                    Save(SQL);
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString() +"'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                }

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if(dataStatus.size()>0)
            {

            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    public  String DownloadJSON_UpdateServer(String SQL,String TableName,String ColumnList, String UniqueField)
    {
        String WhereClause = "";
        int varPos         = 0;

        String response = "";
        String resp     = "";

        try{
            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString() +"'";
                            }
                        }
                    }

                    Save(SQL);
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString() +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString() +"'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                }

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if(dataStatus.size()>0)
            {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for(String data : dataStatus){
                    sqlString.add("Update "+ TableName +" Set Upload='1' Where "+ data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try
                {
                    response=u.execute(json).get();
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

    //Remove data on local device
    public  String DownloadJSON_Delete_UpdateServer(String SQL,String LocalTableName, String ServerTableName, String ColumnList, String UniqueField)
    {
        String WhereClause = "";
        int varPos         = 0;

        String response = "";
        String resp     = "";

        try{
            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Delete command
                SQL = "Delete from "+ LocalTableName +" Where "+ WhereClause;

                Save(SQL);

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if(dataStatus.size()>0)
            {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for(String data : dataStatus){
                    sqlString.add("Update "+ ServerTableName +" Set Upload='2' Where "+ data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try
                {
                    response=u.execute(json).get();
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


    public  String DownloadJSON_BlockUpdate_UpdateServer(String SQL,String LocalTableName,String ColumnList, String UniqueField)
    {
        String WhereClause = "";
        int varPos         = 0;

        String response = "";
        String resp     = "";

        try{
            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString() +"'";
                    }
                }

                //Delete command
                SQL = "Delete from "+ LocalTableName +" Where "+ WhereClause;

                Save(SQL);

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if(dataStatus.size()>0)
            {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for(String data : dataStatus){
                    sqlString.add("Update BariRemove Set Upload='2' Where "+ data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try
                {
                    response=u.execute(json).get();
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



    //Download list item from server based on SQl query
    public  List<String> DownloadJSONList(String SQL)
    {
        String response = "";
        String resp = "";

        List<String> dataStatus = new ArrayList<>();
        try{

            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

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


    public String UploadJSON(String TableName,String ColumnList,String UniqueFields)
    {
        DataClass dt = new DataClass();
        dt.settablename(TableName);
        dt.setcolumnlist(ColumnList);
        List<DataClassProperty> data = GetDataListJSON(ColumnList, TableName, UniqueFields);
        dt.setdata(data);

        Gson gson = new Gson();
        String json = gson.toJson(dt);
        String response="";
        UploadDataJSON u = new UploadDataJSON();
        try{
            response=u.execute(json).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            //upload all records as successfull upload then update status of upload=2 for unsuccessfull
            for(int i=0; i<responseData.getdata().size(); i++)
            {
                Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    // Getting result from database server based on SQL
    //----------------------------------------------------------------------------------------------
    public String ReturnResult(String MethodName, String SQL)
    {
        ReturnResult r=new ReturnResult();
        String response="";
        r.Method_Name = MethodName;
        r.SQLStr=SQL;
        try {
            response=r.execute("").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
        return response;
    }


    //Rebuild Local Database from Server
    //----------------------------------------------------------------------------------------------
    public void RebuildDatabase(String DeviceID) {
        List<String> listItem = new ArrayList<String>();
        listItem = DownloadJSONList("Select TableName+'^'+TableScript from DatabaseTab");

        for (int i = 0; i < listItem.size(); i++) {
            String VarData[] = split(listItem.get(i),'^');
            CreateTable(VarData[0], VarData[1]);
        }

        //------------------------------------------------------------------------------------------
        //Data Sync: Download data from server
        //------------------------------------------------------------------------------------------
        String Res = "";
        String TableName;
        String VariableList;
        String UniqueField;
        String SQLStr;

        try {

            Sync_Data S = new Sync_Data();

            //Master Database Sync (Required for any database system)
            //--------------------------------------------------------------------------------------
            SQLStr       = "Select TableName, TableScript, ColumnList, UniqueID from DatabaseTab";
            TableName    = "DatabaseTab";
            VariableList = "TableName, TableScript, ColumnList, UniqueID";
            UniqueField  = "TableName";
            Res = DownloadJSON(SQLStr,TableName,VariableList,UniqueField);

            S.SyncTable_Download(this.dbContext,"Device"      ,"DeviceId='"+ DeviceID +"'");
            S.SyncTable_Download(this.dbContext,"Login"       ,"UserId='"+ DeviceID +"'");
            //--------------------------------------------------------------------------------------



            //Project Specific Database Sync
            //--------------------------------------------------------------------------------------
            S.SyncTable_Download(this.dbContext,"DeviceFirm"  ,"DeviceId='"+ DeviceID +"'");
            S.SyncTable_Download(this.dbContext,"ClusterType" ,"");

            String firmID = ReturnSingleValue("Select FirmId from DeviceFirm where DeviceID='"+ DeviceID +"'");
            S.SyncTable_Download(this.dbContext,"Cluster"     ,"FirmId='"+ firmID +"'");

            //Update status on server
            //--------------------------------------------------------------------------------------
            ExecuteCommandOnServer("Update Device set Setting='2' where DeviceId='"+ DeviceID +"'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Upload data to server
    public void DataSync_Upload(String[] TableList)
    {
        Sync_Data S = new Sync_Data();
        for(int i=0; i< TableList.length; i++)
        {
            S.SyncTable_Upload(this.dbContext,TableList[i].toString());
        }
    }

    //download data from server if any changes happen
    public void DataSync_Download_Change(String[] TableList)
    {
        Sync_Data S = new Sync_Data();
        for(int i=0; i< TableList.length; i++)
        {
            //S.SyncTable_Download_Change(this.dbContext,TableList[i].toString());
        }
    }


    //To get the list of columns(string) in table
    //----------------------------------------------------------------------------------------------
    public String GetColumnList(String TableName)
    {
        String CList = "";
        Cursor cur_H;
        cur_H = ReadData("pragma table_info('"+ TableName +"')");

        cur_H.moveToFirst();
        int RecordCount=0;

        while(!cur_H.isAfterLast())
        {
            if(RecordCount==0)
                CList +=  cur_H.getString(cur_H.getColumnIndex("name"));
            else
                CList +=  ","+ cur_H.getString(cur_H.getColumnIndex("name"));

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return CList;
    }

    public int GetTotalColumn(String TableName)
    {
        int totalCol = 0;
        Cursor cur_H;
        cur_H = ReadData("pragma table_info('"+ TableName +"')");
        totalCol = cur_H.getColumnCount();
        cur_H.close();

        return totalCol;
    }

    //To get the list of columns(string array) in table
    //----------------------------------------------------------------------------------------------
    public String[] GetColumnListArray(String TableName)
    {
        Cursor cur = ReadData("SELECT * FROM "+ TableName +" WHERE 0");
        String[] columnNames;
        try {
            columnNames = cur.getColumnNames();
        } finally {
            cur.close();
        }
        return columnNames;
    }

    //Download Table List from server
    /*private String[] TableListServer()
    {
        String SQLStr= "";
        DownloadData d = new DownloadData();
        d.Method_Name = "DownloadData";
        d.SQLStr = "Select TableName from DatabaseTab";

        String DataArray[] = null;

        try {
            DataArray = d.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return DataArray;
    }*/

    /*public void TableStructureSync() {
        String TableList[] = TableListServer();
        String SQL = "";
        String TableName = "";
        for (int t = 0; t < TableList.length; t++) {
            TableName = TableList[t];
            SQL = "select (c.name+'^'+cast(c.length as varchar(10)))ColwithLength from sysobjects t,syscolumns c";
            SQL += " where t.id=c.id and t.name='" + TableName + "' and lower(t.xtype)='u' order by colid";

            //Local database
            String[] local = GetColumnListArray(TableName);

            //Server database
            String[] server = DownloadArrayList(SQL);

            String[] C;
            Boolean matched = false;

            //matched database columns(local and server)
            for (int i = 0; i < server.length; i++) {
                matched = false;
                C = Connection.split(server[i], '^');
                for (int j = 0; j < local.length; j++) {
                    if (C[0].toString().toLowerCase().equals(local[j].toString().toLowerCase())) {
                        matched = true;
                        j = local.length;
                    }
                }
                if (matched == false) {
                    Save("Alter table " + TableName + " add column " + C[0].toString() + " varchar(" + C[1].toString() + ") default ''");
                }
            }
        }

    }*/

    /*private String[] DownloadArrayList(String SQL)
    {
        DownloadData d = new DownloadData();
        d.Method_Name="DownloadData";
        d.SQLStr=SQL;

        String DataArray[] = new String[0];
        try {
            DataArray = d.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return DataArray;
    }
*/




    /*
    // Insert record into the database
    public void addTodoItem(TodoItem item) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        // Insert Row
        db.insertOrThrow(TABLE_TODO, null, values);
        db.close(); // Closing database connection
    }


    // Returns a single todo item by id
    public TodoItem getTodoItem(int id) {
        // Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE_TODO,  // TABLE
                new String[] { KEY_ID, KEY_BODY, KEY_PRIORITY }, // SELECT
                KEY_ID + "= ?", new String[] { String.valueOf(id) },  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        // return todo item
        return item;
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
                item.setId(cursor.getInt(0));
                // Adding todo item to list
                todoItems.add(item);
            } while (cursor.moveToNext());
        }

        // return todo list
        return todoItems;
    }

    public int getTodoItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateTodoItem(TodoItem item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        // Updating row
        int result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        // Close the database
        db.close();
        return result;
    }

    public void deleteTodoItem(TodoItem item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        // Close the database
        db.close();
    }
    */




    public boolean InsertData(String TableName, ContentValues content_value) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TableName, null, content_value);
        return true;
    }

    public boolean UpdateData(String TableName,String UniqueID_Field, String UniqueID, ContentValues content_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TableName, content_value, UniqueID_Field + " = ? ", new String[] { UniqueID } );
        return true;
    }

    public Integer DeleteData(String TableName, String UniqueID_Field, String UniqueID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,
                UniqueID_Field + " = ? ",
                new String[] { UniqueID });
    }

    public Cursor GetData(String TableName, String UniqueID_Field, String UniqueID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TableName + " WHERE " +
                UniqueID_Field + "=?", new String[] { UniqueID } );
        return res;
    }
    public Cursor GetAllData(String TableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TableName, null );
        return res;
    }
}