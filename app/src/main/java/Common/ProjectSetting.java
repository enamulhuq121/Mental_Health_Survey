package Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanvirHossain on 19/07/2016.
 * Last Update on 28 Jul 2022
 */
public class ProjectSetting {

    //Project and Server link Information
    //==============================================================================================
    public static String ProjectName    = "standard_v3";
    public static String DatabaseName   = ProjectName.toUpperCase() +"Database.db";
    public static String ServerURL      = "http://chu.icddrb.org/";

    //Database Location
    //==============================================================================================
    //public static final String Database_Location = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder + File.separator + DatabaseName;
    public static final String Database_Location = DatabaseName;
    //public static final String Database_Location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + ProjectSetting.DatabaseFolder + File.separator + DatabaseName;

    //New Release
    //==============================================================================================
    public static String VersionDate    = "14122021"; //Format: DDMMYYYY

    //==============================================================================================
    // Data Sync
    //==============================================================================================
    //Data Upload Tables
    //----------------------------------------------------------------------------------------------
    public static List<String> TableList_Upload(){
        List<String> tableList_Up   = new ArrayList<String>();
        //tableList_Up.add("patientinfo");

        return tableList_Up;
    }

    //Data Download Tables
    //----------------------------------------------------------------------------------------------
    public static List<String> TableList_Download(){
        List<String> tableList_Down   = new ArrayList<String>();
        //tableList_Down.add("patientinfo");

        return tableList_Down;
    }

    //Database Upload
    //==============================================================================================
    public static String Database_Upload = "no"; //yes/no


    //System Variables: Don't need to change
    //==============================================================================================
    public static String Namespace      = "http://chu.icddrb.org/";
    public static String apiName        = ProjectName.toLowerCase();
    public static String NewVersionName = ProjectName.toLowerCase() +"_update";
    public static String DatabaseFolder = ProjectName.toUpperCase() +"DB";
    public static String zipDatabaseName= ProjectName.toUpperCase() +"Database.zip";
    public static String DBSecurityPass = "a";
    public static String Organization   = "ICDDR,B";
    public static String Soap_Address    = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/datasync_v3.asmx";
    public static String UpdatedSystem   = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/Update/"+ ProjectSetting.NewVersionName +".txt";
    //==============================================================================================
}
