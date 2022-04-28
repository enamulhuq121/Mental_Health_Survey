package DataSync;

import android.content.Context;
import android.database.Cursor;

import Common.Connection;

/**
 * Created by TanvirHossain on 13/06/2016.
 */
public class Sync_Data {

    //download data from server(working)
    public void SyncTable_Download(Context con, String TableName, String WhereCondition)
    {
        String VariableList = "";
        String UniqueField  = "";
        String SQLStr       = "";
        String Res          = "";

        Connection C = new Connection(con);

        Cursor cur_H = C.ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='"+ TableName +"'");
        cur_H.moveToFirst();

        while(!cur_H.isAfterLast())
        {
            if(WhereCondition.length()==0)
                SQLStr       = "Select "+ cur_H.getString(cur_H.getColumnIndex("columnlist")) +" from "+ TableName;
            else
                SQLStr       = "Select "+ cur_H.getString(cur_H.getColumnIndex("columnlist")) +" from "+ TableName +" Where "+ WhereCondition;

            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField  = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();

        //Res = C.DownloadJSON(SQLStr,TableName,VariableList,UniqueField);
    }

    //upload data to server(working)
    public void SyncTable_Upload(Context con, String TableName)
    {
        String VariableList = "";
        String UniqueField  = "";
        String SQLStr       = "";
        String Res          = "";

        Connection C = new Connection(con);

        Cursor cur_H = C.ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='"+ TableName +"'");
        cur_H.moveToFirst();

        while(!cur_H.isAfterLast())
        {
            SQLStr       = "Select "+ cur_H.getString(cur_H.getColumnIndex("columnlist")) +" from "+ TableName +" Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField  = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();

        Res = C.UploadJSON(TableName,VariableList,UniqueField);
    }


    //download data from server if any changes happen
    /*public void SyncTable_Download_Change(Context con, String TableName)
    {
        Connection C = new Connection(con);
        String[] SyncParam = Sync_Parameter(con,TableName);

        String SQLStr       = SyncParam[0];
        String VariableList = SyncParam[1];
        String UniqueField  = SyncParam[2];
        String Res = "";

        String[] U = UniqueField.split(",");
        String UID = "";
        String UID_Sync = "";
        for(int i=0; i<U.length; i++)
        {
            if(i==0)
                UID = "cast(t."+ U[0] +" as varchar(50))";
            else
                UID += "+cast(t."+ U[0] +" as varchar(50))";
        }
        UID_Sync += "+cast(t.modifyDate as varchar(30))";

        String SQL = "Select "+ VariableList +" from "+ TableName +" as t where not exists(Select * from sync_management " +
                " where TableName+UniqueID+UserId+cast(modifyDate as varchar(30))="+ UID_Sync + ")";

        Res = C.DownloadJSON_Update_Sync_Management(SQL, TableName,VariableList,UniqueField);
    }*/


    public String[] Sync_Parameter(Context con, String TableName)
    {
        String VariableList = "";
        String UniqueField  = "";
        String SQLStr       = "";

        Connection C = new Connection(con);

        Cursor cur_H = C.ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='"+ TableName +"'");
        cur_H.moveToFirst();

        while(!cur_H.isAfterLast())
        {
            SQLStr       = "Select "+ cur_H.getString(cur_H.getColumnIndex("columnlist")) +" from "+ TableName +" Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField  = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();
        String[] ParaList = new String[]{SQLStr,VariableList,UniqueField};

        return  ParaList;
    }
}
