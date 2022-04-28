package Common;

/**
 * Created by TanvirHossain on 25/01/2017.
 *
 *
 //Call from another Class
 //-------------------------------------------------------------------------------------------------
 final Tran_Download td = new Tran_Download(this);
 C.Save("Delete from SEAPVill");
 final ProgressDialog progDailog = ProgressDialog.show(LoginActivity.this, "", "Please Wait . . .", true);

 new Thread() {
 public void run() {
 try {
 td.Sync_Download("SEAPVill",UniqueID,"CCode='1'");

 } catch (Exception e) {

 }
 progDailog.dismiss();
 }
 }.start();
 //-------------------------------------------------------------------------------------------------
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import net.sqlcipher.database.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tran_Download {
    private static final String KEY_SL = "SL";
    private static final String TABLE_NAME = "AREA_INFO";
    private static final String KEY_TERRITORY = "TERRITORY";
    private static final String KEY_CE_NAME = "CE_NAME";
    private static final String KEY_DISTRIBUTOR = "DISTRIBUTOR";
    private static final String KEY_PSR_NAME = "PSR_NAME";

    private SQLiteDatabase database;
    private Context context;

    public Tran_Download(Context context) {
        this.context = context;
        //database = new Connection(context).getWritableDatabase(Connection.PASS_PHRASE);
        database = new Connection(context).getWritableDatabase();
    }

    public void insertData(ArrayList<ArrayList<String>> areaInfoList) {
        openDatabase();
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (ArrayList<String> areaInfo : areaInfoList
                    ) {
                contentValues.put(KEY_TERRITORY, areaInfo.get(0));
                contentValues.put(KEY_CE_NAME, areaInfo.get(1));
                contentValues.put(KEY_DISTRIBUTOR, areaInfo.get(2));
                contentValues.put(KEY_PSR_NAME, areaInfo.get(3));

                database.insert(TABLE_NAME, null, contentValues);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Area info", "insertData: error");
        } finally {
            database.endTransaction();

            closeDatabase();
        }
    }

    public void insertData(String TableName, String ColumnList, DownloadClass responseData) {
        openDatabase();
        database.beginTransaction();
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
        }
    }

    public ArrayList<String> getTerritory() {
        ArrayList<String> resultList = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery("Select DISTINCT " + KEY_TERRITORY + " from " + TABLE_NAME, new String[]{});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultList.add(cursor.getString(cursor.getColumnIndex(KEY_TERRITORY)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return resultList;
    }

    public ArrayList<String> getCEName(String territory) {
        ArrayList<String> resultList = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery("Select DISTINCT " + KEY_CE_NAME + " from " + TABLE_NAME + " WHERE TERRITORY=?", new String[]{territory});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultList.add(cursor.getString(cursor.getColumnIndex(KEY_CE_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return resultList;
    }

    public ArrayList<String> getDistributor(String territory, String ceName) {
        ArrayList<String> resultList = new ArrayList<>();
        openDatabase();
        Cursor cursor = ceName != null ?
                database.rawQuery("Select DISTINCT " + KEY_DISTRIBUTOR + " from " + TABLE_NAME + " WHERE TERRITORY=? and CE_NAME=?", new String[]{territory, ceName})
                : database.rawQuery("Select DISTINCT " + KEY_DISTRIBUTOR + " from " + TABLE_NAME + " WHERE TERRITORY=?", new String[]{territory});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultList.add(cursor.getString(cursor.getColumnIndex(KEY_DISTRIBUTOR)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return resultList;
    }



    public ArrayList<String> getPSRName(String territory, String ceName, String distributor) {
        ArrayList<String> resultList = new ArrayList<>();
        openDatabase();
        Cursor cursor = ceName != null ?
                database.rawQuery("Select DISTINCT " + KEY_PSR_NAME + " from " + TABLE_NAME + " WHERE TERRITORY=? and CE_NAME=? and DISTRIBUTOR=?", new String[]{territory, ceName, distributor})
                : database.rawQuery("Select DISTINCT " + KEY_PSR_NAME + " from " + TABLE_NAME + " WHERE TERRITORY=? and DISTRIBUTOR=?", new String[]{territory, distributor});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultList.add(cursor.getString(cursor.getColumnIndex(KEY_PSR_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return resultList;
    }

    public void deleteData(String SL) {
        openDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE SL=" + SL);
        closeDatabase();
    }

    private void openDatabase() {
        //database = new Connection(context).getWritableDatabase(Connection.PASS_PHRASE);
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
