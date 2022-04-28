package DataSync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.DataClass;
import Common.DataClassProperty;
import Common.DownloadClass;
import Common.DownloadDataJSON;
import Common.UploadDataJSON;

public class Sync_Download {
    private SQLiteDatabase database;
    private Context context;

    public Sync_Download(Context context, String TableName) {
        this.context = context;
        database = new Connection(context).getWritableDatabase();

        insertData(TableName);
    }

    public String[] Table_Parameter(Context con, String TableName)
    {
        String[] returnValue = new String[4];
        String VariableList = "";
        String UniqueField  = "";
        String SQLStr       = "";

        Connection C = new Connection(con);
        Cursor cur_H = C.ReadData("Select * from DatabaseTab where tablename='"+ TableName +"'");
        cur_H.moveToFirst();

        while(!cur_H.isAfterLast())
        {
            SQLStr       = "Select "+ cur_H.getString(cur_H.getColumnIndex("columnlist")) +" from "+ TableName;
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField  = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();
        returnValue[0] = SQLStr;
        returnValue[1] = VariableList;
        returnValue[2] = UniqueField;

        //Res = C.DownloadJSON(SQLStr,TableName,VariableList,UniqueField);
        return returnValue;
    }

    private void insertData(String TableName) {
        /*DownloadDataJSON dload = new DownloadDataJSON();
        String SQL = "";
        String response=dload.execute(SQL).get();

        //Process Response
        DownloadClass d = new DownloadClass();
        Gson gson = new Gson();
        Type collType = new TypeToken<DownloadClass>(){}.getType();
        DownloadClass responseData = gson.fromJson(response,collType);*/



        /*database.beginTransaction();
        try {
            //**************************************************************************************
            String SQL = "";
            String VarList[] = ColumnList.split(",");
            String[] VarData;
            ContentValues contentValues = new ContentValues();
            for(int r=0; r<responseData.getdata().size(); r++) {
                VarData = Connection.split(responseData.getdata().get(r).toString(), '^');
                for (int i = 0; i < VarList.length; i++) {
                    contentValues.put(VarList[i], VarData[i].toString());
                }

                database.insert(TableName, null, contentValues);
            }
            database.setTransactionSuccessful();
            //**************************************************************************************
        } catch (Exception e) {
            Log.e("Area info", "insertData: error");
        } finally {
            database.endTransaction();

            closeDatabase();
        }*/
    }

    private void openDatabase() {
        database = new Connection(context).getWritableDatabase();
    }

    private void closeDatabase() {
        database.close();
    }

    public  String DownloadJSON_Tran1(String SQL,String TableName,String ColumnList, String UniqueField)
    {
        Connection C = new Connection(context);
        String WhereClause="";
        int varPos=0;

        String response = "";
        String resp = "";
        openDatabase();
        try{
            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>(){}.getType();
            DownloadClass responseData = gson.fromJson(response,collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            database.beginTransaction();
            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = Connection.split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = C.VarPosition(UField[j].toString(),VarList);
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
                if(C.Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
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

                    database.execSQL(SQL);
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

                    database.execSQL(SQL);
                }

                dataStatus.add(WhereClause);
            }
            database.setTransactionSuccessful();

            //Status back to server
            if(dataStatus.size()>0)
            {

            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }finally {
            database.endTransaction();
            database.close();
        }
        return resp;
    }


    //**********************************************************************************************
    public void Sync_Download(String TableName, String UserId, String WhereClause)
    {
        Connection C = new Connection(context);
        //Retrieve sync parameter
        //------------------------------------------------------------------------------------------
        String[] SyncParam = C.Sync_Parameter(TableName);

        String SQLStr       = SyncParam[0];
        String VariableList = SyncParam[1];
        String UniqueField  = SyncParam[2];
        String SQL_VariableList  = SyncParam[3];
        String Res = "";
        String SQL = "";

        //Generate Unique ID field
        //------------------------------------------------------------------------------------------
        String[] U = UniqueField.split(",");
        String UID = "";
        //String UID_Sync = "";
        for(int i=0; i<U.length; i++){
            if(i==0)
                UID = "cast(t."+ U[i] +" as varchar(50))";
            else
                UID += "+cast(t."+ U[i] +" as varchar(50))";
        }

        //calculate total records
        //------------------------------------------------------------------------------------------
        Integer totalRecords = 0;
        SQL  = "Select Count(*)totalRec from "+ TableName +" as t";
        SQL += " where not exists(select * from Sync_Management where";
        SQL += " lower(TableName)  = lower('"+ TableName +"') and";
        SQL += " UniqueID   = "+ UID +" and";
        SQL += " convert(varchar(19),modifydate,120) = convert(varchar(19),t.modifydate,120) and";

        SQL += " UserId   ='"+ UserId +"')";
        if(WhereClause.length()>0)
        {
            SQL += " and "+ WhereClause;
        }

        String totalRec = C.ReturnResult("ReturnSingleValue",SQL);
        if(totalRec==null)
            totalRecords =0;
        else
            totalRecords = Integer.valueOf(totalRec);

        //Calculate batch size
        //------------------------------------------------------------------------------------------
        //0(zero) means all selected data
        Integer batchSize = Integer.valueOf(C.ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='"+ TableName +"'"));
        Integer totalBatch   = 1;

        if(batchSize==0) {
            totalBatch = 1;
            batchSize = totalRecords;
        }
        else if(batchSize > 0) {
            totalBatch = totalRecords/batchSize;
            if(totalRecords%batchSize>0)
                totalBatch += 1;
        }

        //Execute batch download
        //------------------------------------------------------------------------------------------
        for(int i = 0; i < totalBatch; i++) {
            SQL  = "Select top "+ batchSize +" "+ SQL_VariableList +" from "+ TableName +" as t";
            SQL += " where not exists(select * from Sync_Management where";
            SQL += " lower(TableName)  = lower('"+ TableName +"') and";
            SQL += " UniqueID   = "+ UID +" and";
            SQL += " convert(varchar(19),modifydate,120) = convert(varchar(19),t.modifydate,120) and";
            SQL += " UserId   ='"+ UserId +"')";
            if(WhereClause.length()>0)
            {
                SQL += " and "+ WhereClause;
            }

            Res = Sync_Download_Sync_Management(SQL, TableName, VariableList, UniqueField, UserId);
        }
    }

    //download data from server and include those id's into Table: Sync_Management
    private String Sync_Download_Sync_Management(String SQL, String TableName,String ColumnList, String UniqueField, String UserId)
    {
        Connection C = new Connection(context);
        String WhereClause="";
        int varPos=0;

        String response = "";
        String resp = "";
        openDatabase();
        try{

            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>(){}.getType();
            DownloadClass responseData = gson.fromJson(response,collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();
            String modifyDate = "";
            String UID        = "";
            String USID       = "";
            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();

            database.beginTransaction();
            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = Connection.split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = C.VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString().replace("'","") +"'";
                        UID = VarData[varPos].toString();
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString().replace("'","") +"'";
                        UID += VarData[varPos].toString();
                    }
                }

                //Update command
                Cursor cur=database.rawQuery("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause, null);
                if(cur.getCount()>0)
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                            }
                        }

                        if(VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();
                    }

                    database.execSQL(SQL);
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString().replace("'","") +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString().replace("'","") +"'";
                        }

                        if(VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();

                    }
                    SQL += ")";

                    database.execSQL(SQL);
                }
                cur.close();

                database.execSQL(SQL);

                DataList = TableName + "^" + UID + "^"+ UserId + "^" + modifyDate;
                dd = new DataClassProperty();
                dd.setdatalist(DataList);
                dd.setuniquefieldwithdata("" +
                        "TableName='"+ TableName +"' and " +
                        "UniqueID='"+ UID +"' and " +
                        "UserId='"+ UserId +"' and " +
                        "modifyDate='"+ modifyDate +"'");
                data.add(dd);
            }
            database.setTransactionSuccessful();

            DataClass dt = new DataClass();
            dt.settablename("Sync_Management");
            dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
            dt.setdata(data);

            Gson gson1   = new Gson();
            String json1 = gson1.toJson(dt);
            String resp1 = "";

            UploadDataJSON u = new UploadDataJSON();

            try{
                resp1=u.execute(json1).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }finally {
            database.endTransaction();
            database.close();
        }

        return resp;
    }
}
