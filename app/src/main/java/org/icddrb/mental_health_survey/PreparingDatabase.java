package org.icddrb.mental_health_survey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.icddrb.mental_health_survey.adapter.CustomSpinnerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;
import Common.ProjectSetting;
import Utility.CompressZip;

public class PreparingDatabase extends AppCompatActivity {
    Connection C;
    Spinner spnFacility;
    Spinner spnDistrict;
    EditText password;
    TextView lblMessage;
    MaterialButton btnSetup;
    MaterialButton btnTryAgain;
    Context mContext;
    boolean networkAvailable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparing_database);
        mContext = this;
        networkAvailable = Connection.haveNetworkConnection(PreparingDatabase.this);
        C = new Connection(this);
        lblMessage = findViewById(R.id.lblMessage);
        password = findViewById(R.id.password);
        btnSetup = findViewById(R.id.btnSetup);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        spnFacility =findViewById(R.id.spnFacility);
        spnDistrict=findViewById(R.id.spnDistrict);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkAvailable = Connection.haveNetworkConnection(PreparingDatabase.this);
                Activity_Load();
            }
        });
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



    private void Activity_Load()
    {

        if (!networkAvailable){
            Connection.MessageBox(PreparingDatabase.this,"Internet connection is not available");
            btnTryAgain.setVisibility(View.VISIBLE);
            btnSetup.setVisibility(View.GONE);
            return;
        }else{
            btnTryAgain.setVisibility(View.GONE);
            btnSetup.setVisibility(View.VISIBLE);
        }


        SpinnerItem(spnDistrict,"select dcode+'-'+dname district from zilla");

        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String dcode = spnDistrict.getSelectedItemPosition()==0?"":spnDistrict.getSelectedItem().toString().split("-")[0];
                if (dcode != null && !dcode.isEmpty()) {
                    SpinnerItem(spnFacility,"select facilityid+'-'+facility_name from facility where dcode='"+ dcode +"'");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnSetup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(spnDistrict.getSelectedItemPosition()==0){
                    Connection.MessageBox(PreparingDatabase.this,"Select a valid district name from the list.");
                    return;
                }
                if(spnFacility.getSelectedItemPosition()==0){
                    Connection.MessageBox(PreparingDatabase.this,"Select a valid facility name from the list.");
                    return;
                }
                String FacilityID = spnFacility.getSelectedItem().toString().split("-")[0];
                String Pass = password.getText().toString();

                if(Pass.equals("123"))
                    ProcessDatabase(spnFacility.getSelectedItem().toString().split("-")[0]);
                else{
                    Connection.MessageBox(PreparingDatabase.this,"You are not authorized to configure the device. Try again with a valid password.");
                    password.requestFocus();
                }

                //ProcessDatabase();
            }});

        //ProcessDatabase();
    }
    private void SpinnerItem(Spinner SpinnerName, String SQL)
    {
        List<String> listItem = new ArrayList<String>();
        listItem = C.DataListJSON(SQL);
        if(listItem != null){
            listItem.add(0,"Select");
        }
        //ArrayAdapter<String> adptrList= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listItem);
        ArrayAdapter<String> adptrList= new CustomSpinnerAdapter(mContext,new ArrayList<>(listItem));
        SpinnerName.setAdapter(adptrList);
    }

    private void ProcessDatabase(String facilityid)
    {
        String zipFile_URL = ProjectSetting.Database_Folder_URL + File.separator + ProjectSetting.zipDatabaseName;
        String dbFile_URL = ProjectSetting.Database_Folder_URL + File.separator + ProjectSetting.DatabaseName;

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
            btnSetup.setVisibility(View.GONE);
            if (this.networkAvailable) {
                String DeviceID = C.ReturnResult("ReturnSingleValue","sp_Request_DeviceID '"+ C.getDeviceUniqueID(this) +"',''");
                //Log.d("TAG", "ProcessDatabase: _test "+DeviceID);
                String Setting = C.ReturnResult("Existence", "Select DeviceId from DeviceList where DeviceId='"+ DeviceID +"' and Setting='3' and Active='1'");

                if (Setting.equals("2")) {
                    lblMessage.setText("Device ID :"+ DeviceID +" is not allowed to configure in mobile device, please contact with administrator.");
                    lblMessage.setVisibility(View.VISIBLE);
                    btnSetup.setVisibility(View.VISIBLE);
                    return;
                }

                RebuildDatabase(DeviceID,facilityid);

            }else {
                lblMessage.setText("Internet connection is not available for initial database creation.");
                lblMessage.setVisibility(View.VISIBLE);
                btnSetup.setVisibility(View.VISIBLE);
            }
        }
    }

    ProgressDialog progDailog;
    private void RebuildDatabase(String DeviceID, String facilityid)
    {
        lblMessage.setVisibility(View.GONE);
        btnSetup.setVisibility(View.GONE);
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
                    if(C.RebuildDatabase(progDailog, progressHandler, DeviceID,facilityid)){
                        progDailog.dismiss();
                        finish();
                        Intent mainIntent = new Intent(PreparingDatabase.this, LoginActivity.class);
                        startActivity(mainIntent);
                    }else{
                        progDailog.dismiss();
                        lblMessage.setText("Process execution failed. Please try again with internet connection.");
                        lblMessage.setVisibility(View.VISIBLE);
                        btnSetup.setVisibility(View.VISIBLE);
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