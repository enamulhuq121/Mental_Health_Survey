package org.icddrb.kalaazar_pkdl;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

import Common.Connection;
import Utility.MySharedPreferences;

/*
 * Created by TanvirHossain on 08/03/2015.
 */
public class DatabaseFileSync_Service extends Service {
    public DatabaseFileSync_Service m_service;
    MySharedPreferences sp;

    public class MyBinder extends Binder {
        public DatabaseFileSync_Service getService() {
            return DatabaseFileSync_Service.this;
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

    private NotificationManager mManager;
    PowerManager.WakeLock wakeLock;
    PowerManager c;
    Bundle IDbundle;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // obtain the wake lock
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        //wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "MyWakelockTag");
    }

    static String DEVICEID  = "";


    private void handleIntent(Intent intent) {
        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }

        DEVICEID    = sp.getValue(this, "deviceid");

        // do the actual work, in a separate thread
        new DataSyncTask().execute(DEVICEID);
    }


    //@SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        handleIntent(intent);
        wakeLock.acquire();
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
        wakeLock.release();
    }


    private class DataSyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            final String[] ID = params[0].toString().split("-");
            try {
                new Thread() {
                    public void run() {
                        try {
                            Connection.DatabaseUploadZip(DEVICEID);
                            
                        } catch (Exception e) {

                        }
                    }
                }.start();

            } catch (Exception e) {

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
