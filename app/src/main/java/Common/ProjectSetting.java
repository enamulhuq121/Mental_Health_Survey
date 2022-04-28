package Common;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by TanvirHossain on 19/07/2016.
 */
public class ProjectSetting {
    public static String ProjectName    = "standard_v3";
    public static String Namespace      = "http://chu.icddrb.org/";
    public static String ServerURL      = "http://chu.icddrb.org/";

    public static String apiName        = ProjectName.toLowerCase();
    public static String NewVersionName = ProjectName.toLowerCase() +"_update";
    public static String DatabaseFolder = ProjectName.toUpperCase() +"DB";
    public static String DatabaseName   = ProjectName.toUpperCase() +"Database.db";
    public static String zipDatabaseName= ProjectName.toUpperCase() +"Database.zip";
    public static String DBSecurityPass = "a";
    public static String Organization   = "ICDDR,B";
    public static String Soap_Address    = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/datasync_v3.asmx";
    public static String UpdatedSystem   = ProjectSetting.ServerURL + "/"+ ProjectSetting.apiName +"/Update/"+ ProjectSetting.NewVersionName +".txt";


    public static String VersionDate    = "14122021"; //Format: DDMMYYYY


    //Setting API
    public static final String USER_AGENT_WebAPI = "Mozilla/5.0";
    private static final String Server_URL = "http://emis.icddrb.org:8080";
    public static final String APIName = "emis_icddrb";
    public static final String URL_WebAPI     = Server_URL + "/" + APIName + "/apidata/DataSync";

    public static String host_address = "mchd.icddrb.org";
    public static String database_name = "test";
    public static String method_name = "DownloadData";
    public static String device_id = "";

    //AnyChart
    //----------------------------------------------------------------------------------------------
    public static String ANYCHART_LICENSE_KEY = "icddrb.org-e86f0f62-5d1ef68a";
}
