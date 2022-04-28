package Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanvirHossain on 19/07/2016.
 */
public class apiProjectSetting {
    public static String ProjectName     = "standard";
    private static final String Server_URL        = "http://chu.icddrb.org";
    public static final String USER_AGENT_WebAPI  = "Mozilla/5.0";

    public static String apiName         = ProjectName.toLowerCase();
    public static String NewVersionName  = ProjectName.toLowerCase() +"_update";
    public static String DatabaseFolder  = ProjectName.toUpperCase() +"DB";
    public static String DatabaseName    = ProjectName.toUpperCase() +"Database.db";
    public static String zipDatabaseName = ProjectName.toUpperCase() +"Database.zip";
    public static String Organization    = "ICDDR,B";

    public static String VersionDate     = "21082016"; //Format: DDMMYYYY
    public static final String apps_Document_Folder = "apps_document";
    public static String UpdatedSystem   = Server_URL + "/" + apps_Document_Folder + "/" + NewVersionName + ".txt";

    public static final String URL_WebAPI = Server_URL + "/" + apiName + "/apidata/DataSync";
    public static final String NewVersionDate = "15/02/2018";

}
