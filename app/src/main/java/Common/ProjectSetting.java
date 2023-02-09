package Common;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanvirHossain on 19/07/2016.
 * Last Update on 28 Jul 2022
 */
public class ProjectSetting {

    //Project and Server link Information
    //==============================================================================================
    public static String ProjectName    = "mental_health_survey";
    public static String ServerURL      = "http://mchdweb.icddrb.org/";

    //Database Location
    //==============================================================================================
    public static String DatabaseName   = ProjectName.toUpperCase() +"Database.db";
    public static String DatabaseFolder = ProjectName.toUpperCase() +"DB";
    //public static final String Database_Folder_URL = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder;
    public static final String Database_Folder_URL = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ProjectSetting.DatabaseFolder;
    //Internal Memory
    //public static final String Database_Location = DatabaseName;

    //External Storage
    public static final String Database_Location = Database_Folder_URL + File.separator + DatabaseName;
    //public static final String Database_Location = Database_Folder_URL + File.separator + DatabaseName;

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
        tableList_Up.add("Patient");
        tableList_Up.add("Provider");
        tableList_Up.add("SectionA");
        tableList_Up.add("SectionB");
        tableList_Up.add("SectionC");
        tableList_Up.add("SectionD");
        tableList_Up.add("SectionE");
        tableList_Up.add("SectionF");
        tableList_Up.add("SectionG");
        tableList_Up.add("SectionH");
        tableList_Up.add("Women");
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
    public static boolean Tab_Database_Upload = false;

    //UI Design Attributes
    //==============================================================================================
    public static boolean Show_Bottom_Navigation_Bar = true;
    public static boolean Show_Floating_Button_Navigation_Bar = true;

    //System Variables: Don't need to change
    //==============================================================================================
    public static String Namespace      = "http://chu.icddrb.org/";
    public static String apiName        = ProjectName.toLowerCase();
    public static String NewVersionName = ProjectName.toLowerCase() +"_update";
    public static String zipDatabaseName= ProjectName.toUpperCase() +"Database.zip";
    public static String DBSecurityPass = "a";
    public static String Organization   = "ICDDR,B";
    public static String Soap_Address    = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/datasync_v3.asmx";
    public static String UpdatedSystem   = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/Update/"+ ProjectSetting.NewVersionName +".txt";
    //==============================================================================================
}
