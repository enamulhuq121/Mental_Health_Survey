package org.icddrb.kalaazar_pkdl;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;

/*
 * Created by TanvirHossain on 08/03/2015.
 */
public class DataSyncService extends Service
{
    Global g;
    MySharedPreferences sp;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*@Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }*/

    private void handleIntent(Intent intent) {
        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }

        // do the actual work, in a separate thread
        new DataSyncTask().execute(MySharedPreferences.getValue(this,"deviceid"));
    }

    private class DataSyncTask extends AsyncTask<String, Void, Void> {
        String UniqueID = "";
        String SiteCode = "";
        String CountryCode = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            UniqueID = params[0].toString();

            try {
                new Thread() {
                    public void run() {
                        try {
                            Connection.SyncDataService();
                        } catch (Exception e) {

                        }
                    }
                }.start();
                //Sync Database

            } catch (Exception e) {

            }

            // do stuff!
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // handle your data
            stopSelf();
        }
    }

    //@SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        handleIntent(intent);
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

