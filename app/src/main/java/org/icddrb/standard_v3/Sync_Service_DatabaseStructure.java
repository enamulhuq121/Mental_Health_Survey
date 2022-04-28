package org.icddrb.standard_v3;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import Common.Connection;
import Utility.MySharedPreferences;

/*
 * Created by TanvirHossain on 08/03/2015.
 */
public class Sync_Service_DatabaseStructure extends Service {
    public Sync_Service_DatabaseStructure m_service;
    MySharedPreferences sp;

    public class MyBinder extends Binder {
        public Sync_Service_DatabaseStructure getService() {
            return Sync_Service_DatabaseStructure.this;
        }
    }

    private ServiceConnection m_serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            m_service = ((MyBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            m_service = null;
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    private void handleIntent(Intent intent) {
        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }

        // do the actual work, in a separate thread
        new DataSyncTask().execute();
    }


    //@SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        handleIntent(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        //return START_NOT_STICKY;
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    private class DataSyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                new Thread() {
                    public void run() {
                        try {
                            Connection.SyncDataService_DatabaseStructure();

                        } catch (Exception ignored) {

                        }
                    }
                }.start();

            } catch (Exception ignored) {

            }
            // do stuff!
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            stopSelf();
        }
    }
}
