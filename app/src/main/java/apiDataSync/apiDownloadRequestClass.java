package apiDataSync;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TanvirHossain on 03/12/2015.
 */
public class apiDownloadRequestClass
{
    @SerializedName("method")
    String Method;
    public void setmethodname(String _Method) {this.Method=_Method;}
    public String getmethodname() {return this.Method;}

    @SerializedName("SQL")
    String SQL;
    public void setSQL(String _SQL){this.SQL=_SQL;}
    public String getSQL(){return this.SQL;}
}