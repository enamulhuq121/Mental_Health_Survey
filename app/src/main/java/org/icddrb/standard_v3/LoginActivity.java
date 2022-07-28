package org.icddrb.standard_v3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import Common.Connection;
import Common.Global;
import Common.ProjectSetting;
import Utility.MySharedPreferences;

public class LoginActivity extends AppCompatActivity {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    Connection C;
    boolean networkAvailable = false;
    int count = 0;
    TextView lblStaffType;
    String SystemUpdateDT = "";
    private ProgressDialog dialog;
    private String Password = "";
    MySharedPreferences sp;
    TextView Country;
    TextView Facility;
    EditText pass;

    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event) {
        if (iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) {
            AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this);
            adb.setTitle("Close");
            adb.setMessage("Do you want to exist from the system?");
            adb.setNegativeButton("No", null);
            adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            adb.show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView (R.layout.login_activity);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            //SQLiteDatabase.loadLibs(this);

            Activity_Load();

        } catch (Exception ex) {
            Connection.MessageBox(LoginActivity.this, ex.getMessage());
        }
    }

    private void Activity_Load() {
        try {
            C = new Connection(this);
            sp = new MySharedPreferences();
            sp.save(this, "deviceid", "");
            sp.save(this, "userid", "");
            sp.save(this, "languageid", "0");
            sp.save(this, "lat", "");
            sp.save(this, "lon", "");
            sp.save(this, "bdbrequest", "");

            final TextView UniqueUserId = (TextView) findViewById(R.id.UniqueUserId);
            final Spinner uid = (Spinner) findViewById(R.id.userId);
            final EditText pass = (EditText) findViewById(R.id.pass);
            final Spinner spnLanguage = (Spinner) findViewById(R.id.spnLanguage);
            TextView lblSystemDate = (TextView) findViewById(R.id.lblSystemDate);

            SystemUpdateDT = ProjectSetting.VersionDate;
            lblSystemDate.setText("Version: 1.0, Built on: " + SystemUpdateDT);

            //Check for Internet connectivity
            networkAvailable = Connection.haveNetworkConnection(LoginActivity.this);

            //Device Unique ID
            final String UniqueID = C.ReturnSingleValue("Select DeviceId from DeviceList");
            UniqueUserId.setText("Unique ID :" + UniqueID);
            sp.save(this, "deviceid", UniqueID);

            uid.setAdapter(C.getArrayAdapter("select UserId||'-'||UserName User from DataCollector order by UserName"));
            spnLanguage.setAdapter(C.getArrayAdapter("select language_id||'-'||language_name from Language order by language_id"));

            //Login -----------------------------------------------------------------------
            Button loginButton = (Button) findViewById(R.id.btnLogin);
            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(!C.Existence("select Approval from DeviceList where DeviceID='"+ UniqueID +"' and Approval='1'")){
                        Connection.MessageBox(LoginActivity.this, "Device ID: "+ UniqueID +" is not currently authorized to access the system.");
                        return;
                    }
                    try {
                        String[] U = Connection.split(uid.getSelectedItem().toString(), '-');
                        String[] L = Connection.split(spnLanguage.getSelectedItem().toString(), '-');
                        sp.save(LoginActivity.this, "userid", U[0]);
                        sp.save(LoginActivity.this,"username",U[1]);
                        sp.save(LoginActivity.this, "languageid", L[0]);

                        if (!C.Existence("Select * from DataCollector where UserId='" + U[0] + "' and Pass='" + pass.getText().toString() + "'")) {
                            Connection.MessageBox(LoginActivity.this, "This is not a valid user id or password");
                            return;
                        }

                        //Store Last Login information
                        String response = C.SaveData("Delete from LastLogin");
                        String response1 = C.SaveData("Insert into LastLogin(UserId)Values('" + U[0] + "')");

                        //Download Updated System
                        //...................................................................................
                        if (networkAvailable) {
                            //Retrieve data from server for checking local device
                            String[] ServerVal = Connection.split(C.ReturnResult("ReturnSingleValue", "sp_ServerCheck '" + UniqueID + "'"), ',');
                            String ServerDate = ServerVal[0];
                            String UpdateDT = ServerVal[1];

                            //Check for New Version
                            if (!UpdateDT.equals(SystemUpdateDT)) {
                                SystemDownload d = new SystemDownload();
                                d.setContext(getApplicationContext());
                                d.execute(ProjectSetting.UpdatedSystem);
                            } else {
                                //check for system date
                                if (!ServerDate.equals(Global.TodaysDateforCheck())) {
                                    Connection.MessageBox(LoginActivity.this, "System date is incorrect [" + Global.DateNowDMY() + "]");
                                    startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                                    return;
                                }

                                final ProgressDialog progDailog = ProgressDialog.show(LoginActivity.this, "", "Please Wait . . .", true);

                                new Thread() {
                                    public void run() {
                                        try {
                                            finish();
                                            Intent f1 = new Intent(getApplicationContext(), Fragment_Main.class);
                                            startActivity(f1);
                                        } catch (Exception e) {

                                        }
                                        progDailog.dismiss();
                                    }
                                }.start();
                            }
                        } else {
                            final ProgressDialog progDailog = ProgressDialog.show(LoginActivity.this, "", "Please Wait . . .", true);

                            new Thread() {
                                public void run() {
                                    try {
                                        finish();
                                        Intent f1 = new Intent(getApplicationContext(), Fragment_Main.class);
                                        startActivity(f1);
                                    } catch (Exception e) {

                                    }
                                    progDailog.dismiss();
                                }
                            }.start();
                        }
                    } catch (Exception ex) {
                        final ProgressDialog progDailog = ProgressDialog.show(LoginActivity.this, "", "Please Wait . . .", true);

                        new Thread() {
                            public void run() {
                                try {
                                    finish();
                                    Intent f1 = new Intent(getApplicationContext(), Fragment_Main.class);
                                    startActivity(f1);
                                } catch (Exception e) {

                                }
                                progDailog.dismiss();
                            }
                        }.start();
                    }
                }
            });
        } catch (Exception ex) {
            Connection.MessageBox(LoginActivity.this, ex.getMessage());
            ex.printStackTrace();
        }
    }


    //Install application
    private void InstallApplication() {
        File apkfile = new File(Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.NewVersionName + ".apk");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            /*Uri apkuri = FileProvider.getUriForFile(LoginActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    apkfile);*/
            Uri apkuri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    Objects.requireNonNull(getApplicationContext()).getPackageName() + ".provider", apkfile);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            intent.setDataAndType(apkuri, "application/vnd.android.package-archive");

            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");

            startActivity(intent);
        }
    }

    //Downloading updated system from the central server
    class SystemDownload extends AsyncTask<String, String, Void> {
        private Context context;

        public void setContext(Context contextf) {
            context = contextf;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Downloading Updated System...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }


        protected void onProgressUpdate(String... progress) {
            dialog.setProgress(Integer.parseInt(progress[0]));
            //publishProgress(progress);

        }

        //@Override
        protected void onPostExecute(String unused) {
            dialog.dismiss();
        }


        @Override
        protected Void doInBackground(String... arg0) {
            try {
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();
                int lenghtOfFile = c.getContentLength();

                File file = Environment.getExternalStorageDirectory();

                file.mkdirs();
                File outputFile = new File(file.getAbsolutePath() + File.separator + ProjectSetting.NewVersionName + ".apk");

                if (outputFile.exists()) {
                    outputFile.delete();
                } else {
                    outputFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();


                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    count++;
                }
                fos.close();
                is.close();

                InstallApplication();

                dialog.dismiss();

            } catch (IOException e) {
                //Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
    }
}