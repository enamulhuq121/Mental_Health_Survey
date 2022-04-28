package DataSync;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;

//import com.data.rhis2.LoginActivity;

import Common.Connection;

/**
 * Created by TanvirHossain on 14/03/2015.
 */

public class SyncRebuildDatabase extends Service
{
    Connection C;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private void handleIntent(Intent intent) {
        // obtain the wake lock
        /*
                PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Const.TAG);
                mWakeLock.acquire();
        */

        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }

        C = new Connection(this);
        // do the actual work, in a separate thread
        new DataSyncTask().execute();
    }

    private class DataSyncTask extends AsyncTask<String, Void, String> {
        String Response = "";
        /**
         * This is where YOU do YOUR work. There's nothing for me to write here
         * you have to fill this in. Make your HTTP request(s) or whatever it is
         * you have to do to get your updates in here, because this is run in a
         * separate thread
         */
        @Override
        protected String doInBackground(String... params) {
            String[] P = Connection.split(params[0],'^');

            String SQLStr = "";
            SQLStr = "Select zillaid from ProviderDB where";
            SQLStr += " zillaid   ='" + P[0] + "' and";
            SQLStr += " upazilaid ='" + P[1] + "' and";
            SQLStr += " unionid   ='" + P[2] + "' and";
            SQLStr += " provtype  ='" + P[3] + "' and";
            SQLStr += " provcode  ='" + P[4] + "' and";
            SQLStr += " active='1' and DeviceSetting='1'";

            String AreaCode = "";//C.ReturnResult("Existence", SQLStr);
            if (AreaCode.equals("2")) {
                Response = "This is not a valid information for device setting or information not available for this provider.";
            }

            //Rebuild database
            //if(Response.length()==0)
            //    C.RebuildDatabase(P[0], P[1], P[2], P[3], P[4]);

            Response = "done";

            return Response;
        }

        /**
         * In here you should interpret whatever you fetched in doInBackground
         * and push any notifications you need to the status bar, using the
         * NotificationManager. I will not cover this here, go check the docs on
         * NotificationManager.
         *
         * What you HAVE to do is call stopSelf() after you've pushed your
         * notification(s). This will:
         * 1) Kill the service so it doesn't waste precious resources
         * 2) Call onDestroy() which will release the wake lock, so the device
         *    can go to sleep again and save precious battery.
         */
        @Override
        protected void onPostExecute(String result) {
            // handle your data
            stopSelf();
        }
    }


    //@SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        handleIntent(intent);
        /*
        super.onStart(intent, startId);
        String TableName = "";
        String VariableList = "";
        String ColumnList = "";
        String UniqueField = "";

        String ResponseString="Status:";
        String response;

        try {
            TableName = "Household";
            VariableList = "Div, Dist, Upz, UN, wardNew, wardOld, Mouza, FWAUnit, Vill, EPIBlock, PAddr, PermaAddress, HHNo, Religion, VGFCard, StartTime, EndTime, Lat, Lon, UserId, EnDt, Upload";
            String[] H = C.GenerateArrayList(VariableList, TableName);
            response = C.UploadData(H, TableName , VariableList , "Div, Dist, Upz, UN, Mouza, Vill, HHNo");

            TableName = "Member";
            VariableList = "Dist, Upz, UN, Mouza, Vill, HHNo, SNo, HealthID, NameEng, NameBang, Rth, HaveNID, NID, NIDStatus, HaveBR, BRID, BRIDStatus, MobileNo1, MobileNo2, DOB, Age, DOBSource, BPlace, FNo, Father, MNo, Mother, Sex, MS, SPNO1, SPNO2, SPNO3, SPNO4, ELCONo, ELCODontKnow, EDU, Rel, Nationality, OCP, StartTime, EnType, EnDate, ExType, ExDate, EndTime, Lat, Lon, UserId, EnDt, Upload";
            String[] M = C.GenerateArrayList(VariableList, TableName);
            response = C.UploadData(M, TableName , VariableList , "Dist, Upz, UN, Mouza, Vill, HHNo, SNo");

            //Sync Database

        } catch (Exception e) {

        }
        */
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        //mWakeLock.release();
    }

}

