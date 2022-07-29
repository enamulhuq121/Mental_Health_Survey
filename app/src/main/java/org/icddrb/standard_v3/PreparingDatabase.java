package org.icddrb.standard_v3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import Common.Connection;
import Common.Global;
import Common.ProjectSetting;
import Utility.CompressZip;

public class PreparingDatabase extends AppCompatActivity {
    Connection C;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparing_database);
        Activity_Load();
    }

    public static final String SECURITY_TAG = "Security Permission";
    private static final int REQUEST_Code = 0;
    private static final String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};

    boolean networkAvailable = false;
    TextView lblMessage;
    Button btnTryAgain;
    private void Activity_Load()
    {
        C = new Connection(this);
        lblMessage = findViewById(R.id.lblMessage);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessDatabase();
            }});

        ProcessDatabase();
    }

    private void ProcessDatabase()
    {
        String zipFile_URL = ProjectSetting.Database_Folder_URL + File.separator + ProjectSetting.zipDatabaseName;
        String dbFile_URL = ProjectSetting.Database_Folder_URL + File.separator + ProjectSetting.DatabaseName;
        networkAvailable = Connection.haveNetworkConnection(PreparingDatabase.this);
        if (new File(zipFile_URL).exists()) {
            File dbfile = new File(dbFile_URL);
            if (dbfile.exists()) {
                Boolean.valueOf(dbfile.delete());
            }
            CompressZip decompressZip = new CompressZip();
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append(ProjectSetting.DatabaseFolder);
            decompressZip.unzipDB(zipFile_URL, sb.toString());
        } else {
            lblMessage.setVisibility(View.GONE);
            btnTryAgain.setVisibility(View.GONE);
            if (this.networkAvailable) {
                String DeviceID = C.ReturnResult("ReturnSingleValue","sp_Request_DeviceID '"+ C.getDeviceUniqueID(this) +"',''");
                String Setting = C.ReturnResult("Existence", "Select DeviceId from DeviceList where DeviceId='"+ DeviceID +"' and Setting='3' and Active='1'");
                if (Setting.equals("2")) {
                    lblMessage.setText("Device ID :"+ DeviceID +" is not allowed to configure in mobile device, please contact with administrator.");
                    lblMessage.setVisibility(View.VISIBLE);
                    btnTryAgain.setVisibility(View.VISIBLE);
                    return;
                }

                RebuildDatabase(DeviceID);

            }else {
                lblMessage.setText("Internet connection is not available for initial database creation.");
                lblMessage.setVisibility(View.VISIBLE);
                btnTryAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    ProgressDialog progDailog;
    private void RebuildDatabase(String DeviceID)
    {
        lblMessage.setVisibility(View.GONE);
        btnTryAgain.setVisibility(View.GONE);
        progDailog = new ProgressDialog(PreparingDatabase.this);
        progDailog.setTitle("Preparing database");
        progDailog.setMessage("Please Wait . . .");
        progDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDailog.setIndeterminate(false);
        progDailog.setCancelable(false);
        progDailog.setIcon(R.drawable.data_sync);
        progDailog.setProgress(0);
        progDailog.show();

        new Thread() {
            public void run() {
                try {
                    if(C.RebuildDatabase(progDailog, progressHandler, DeviceID)){
                        progDailog.dismiss();
                        finish();
                        Intent mainIntent = new Intent(PreparingDatabase.this, Fragment_Main.class);
                        startActivity(mainIntent);
                    }else{
                        progDailog.dismiss();
                        lblMessage.setText("Process execution failed. Please try again with internet connection.");
                        lblMessage.setVisibility(View.VISIBLE);
                        btnTryAgain.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                }
            }
        }.start();


    }

    @SuppressLint("HandlerLeak")
    Handler progressHandler = new Handler() {
        public void handleMessage(Message msg) {
            progDailog.setMessage(Global.getInstance().getProgressMessage());
            progDailog.incrementProgressBy(0);
        }
    };


}