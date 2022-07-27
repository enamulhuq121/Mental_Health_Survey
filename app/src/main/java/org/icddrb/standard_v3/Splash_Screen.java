package org.icddrb.standard_v3;

/**
 * Created by thossain on 02/12/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import Common.Connection;

public class Splash_Screen extends Activity {
    Connection C;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static final String SECURITY_TAG = "Security Permission";
    private static final int REQUEST_Code = 0;
    private static final String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        C = new Connection(this);

        checkPermission();

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // There are no request codes
            //Intent data = result.getData();

            Intent mainIntent = new Intent(Splash_Screen.this,Bottom_Navigation_MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }*/

    private void Activity_Load()
    {
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                String TotalTab = C.ReturnSingleValue("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence'");
                Intent mainIntent;
                if (Integer.parseInt(TotalTab) == 0) {
                    mainIntent = new Intent(Splash_Screen.this, PreparingDatabase.class);
                    //someActivityResultLauncher.launch(mainIntent);
                }else{

                    if (isServiceRunning(Sync_Service.class)) {
                        stopService(new Intent(getApplicationContext(), Sync_Service.class));
                    }
                    startService(new Intent(getApplicationContext(), Sync_Service.class));

                    mainIntent = new Intent(Splash_Screen.this,Bottom_Navigation_MainActivity.class);
                    Splash_Screen.this.finish();
                    Splash_Screen.this.startActivity(mainIntent);
                }

                Splash_Screen.this.startActivity(mainIntent);
                Splash_Screen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkPermission() {
        Log.e(SECURITY_TAG, "Checking Permission.");
        if (
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &
                        (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &
                        (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &
                        (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ) {
            Log.e(SECURITY_TAG, "Calling Requesting Permission!!!");
            requestPermission();
        } else {
            Log.e(SECURITY_TAG, "Your permission has already been granted.");

            Activity_Load();
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.e(SECURITY_TAG, "Requesting Permission to User.");
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, REQUEST_Code);
        } else {
            Log.e(SECURITY_TAG, "Requesting Permission Directly.");
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, REQUEST_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == PERMISSIONS_LIST.length && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //********* Granted ********
            Activity_Load();
        } else {
            //********* Not Granted ********
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, REQUEST_Code);
        }
    }
}