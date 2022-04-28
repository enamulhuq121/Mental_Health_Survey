package Common;

import java.io.File;
import java.io.FileInputStream;

import android.os.AsyncTask;
import android.os.Environment;


public class FileUpload extends AsyncTask<String, Integer, Void>
{
    @Override
    protected Void doInBackground(String... params) 
    {
        try
        {
            //Upload File
        	FileInputStream fstrm = new FileInputStream(Environment.getExternalStorageDirectory()+ File.separator + ProjectSetting.DatabaseFolder + File.separator + params[0].toString());

            HttpFileUpload hfu = new HttpFileUpload(
                    ProjectSetting.ServerURL + "/"+
                    ProjectSetting.ProjectName.toLowerCase() +"/fileup.aspx",
                    params[1].toString(),"description");

    	    hfu.Send_Now(fstrm);
        }

        catch(Exception ex)
        {

        }
        return null;
    }



    @Override
    protected void onCancelled() 
    {



        super.onCancelled();
    }

    @SuppressWarnings({ "static-access" })
    @Override
    protected void onPostExecute(Void result) 
    {

        //Make changes to UI from here
        super.onPostExecute(result);

    }

    @Override
    protected void onPreExecute() 
    {
        super.onPreExecute();
        //Do opertaions
    }

    @Override
    protected void onProgressUpdate(Integer... values) 
    {
        //Update if you have a progress bar
    }

}
