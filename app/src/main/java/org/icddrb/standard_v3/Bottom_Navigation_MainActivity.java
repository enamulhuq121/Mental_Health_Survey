package org.icddrb.standard_v3;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Utility.MySharedPreferences;
import forms_activity.Household_list;
import forms_activity.Mapping_Household_list;

public class Bottom_Navigation_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Connection C;
    MySharedPreferences sp;
    Fragment selectedFragment = null;

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean networkAvailable = Connection.haveNetworkConnection(Bottom_Navigation_MainActivity.this);
        if (networkAvailable) {
            if (isServiceRunning(org.icddrb.standard_v3.Sync_Service.class)) {
                stopService(new Intent(getApplicationContext(), org.icddrb.standard_v3.Sync_Service.class));
            }
            startService(new Intent(getApplicationContext(), org.icddrb.standard_v3.Sync_Service.class));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_mainactivity);

        Activity_Load();
    }

    boolean networkAvailable = false;
    private void Activity_Load()
    {
        C = new Connection(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.bringToFront();
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Synchronize Data
        /*if (!Connection.haveNetworkConnection(Bottom_Navigation_MainActivity.this)) {
            networkAvailable = true;
            Connection.MessageBox(Bottom_Navigation_MainActivity.this, "Internet connection is not available for data sync.");
            return;
        }else{
            new DataSyncTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"");
        }*/

        if (networkAvailable) {
            if (isServiceRunning(org.icddrb.standard_v3.Sync_Service.class)) {
                stopService(new Intent(getApplicationContext(), Sync_Service.class));
            }
            startService(new Intent(getApplicationContext(), Sync_Service.class));
        }

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                /*switch (item.getItemId()) {
                    case R.id.btnGraveyard:
                        selectedFragment =  Graveyard_Fragment.newInstance();
                        break;
                    case R.id.btnAmbulance:
                        selectedFragment =  Ambulance_Fragment.newInstance();
                        break;
                    case R.id.btnBath:
                        selectedFragment =  Bath_Fragment.newInstance();
                        break;
                    default:
                        //selectedFragment =  Graveyard_Fragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, selectedFragment);
                transaction.commit();*/
                return true;
            }
        });
        selectedFragment =  HomeFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, selectedFragment);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    private class DataSyncTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        private Context context;
        String resp = "";
        public void setContext(Context contextf){
            context = contextf;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Bottom_Navigation_MainActivity.this);
            dialog.setTitle("Data Sync");
            dialog.setMessage("Data Sync in Progress, Please wait ...");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.data_sync);
            dialog.show();
        }

        //@Override
        protected void onProgressUpdate(String... values) {
            dialog.setProgress(Integer.parseInt(values[0].toString().split(",")[1]));
            //dialog.setMessage(values[0].toString().split(",")[2]);
        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                new Thread() {
                    public void run() {
                        try {
                            int count = 0;
                            int progressCount = 0;

                            C.Sync_DatabaseStructure();
                            //C.Sync_Download("PatientInfo","PatientInfo", "");

                            List<String> tableList = new ArrayList<String>();
                            tableList.add("Cluster");
                            tableList.add("village");

                            if(tableList.size()!=0)
                                progressCount = 50/tableList.size();
                            for (int i = 0; i < tableList.size(); i++) {
                                try {
                                    C.Sync_Upload_Process(tableList.get(i));
                                    count +=progressCount;
                                    onProgressUpdate(tableList.get(i)+","+ String.valueOf(count));
                                }catch(Exception ignored){

                                }
                            }
                            count = 50;

                            //Download
                            //----------------------------------------------------------------------
                            if(tableList.size()!=0)
                                progressCount = 50/tableList.size();

                            for (int j = 0; j < tableList.size(); j++) {
                                try {
                                    C.Sync_Download(tableList.get(j),tableList.get(j), "");

                                    count +=progressCount;
                                    onProgressUpdate(tableList.get(j)+","+ String.valueOf(count));
                                }catch(Exception ignored){

                                }
                            }

                            onProgressUpdate(","+ "100");


                            //Database File Upload
                            //String DBREQUEST = Connection.split(C.ReturnResult("ReturnSingleValue", "sp_ServerCheck '" + DEVICEID + "'"), ',')[2];
                            String DBREQUEST = "1";
                            if (DBREQUEST.equals("1")){
                                Intent syncService = new Intent(Bottom_Navigation_MainActivity.this, DatabaseFileSync_Service.class);
                                startService(syncService);
                            }


                            dialog.dismiss();

                        } catch (Exception e) {
                            resp = e.getMessage();
                            dialog.dismiss();
                        }
                        finally {
                            dialog.dismiss();
                        }
                    }
                }.start();

            } catch (Exception e) {

            }
            // do stuff!
            return null;
        }

        //@Override
        protected void onPostExecute(String result) {
            if(result.length()!=0) {
                Connection.MessageBox(Bottom_Navigation_MainActivity.this, "Data Sync successfully completed.");
                dialog.dismiss();
            }
        }
    }

    private void SelectVillageForm()
    {
        final Dialog dialog = new Dialog(Bottom_Navigation_MainActivity.this);
        dialog.setContentView(R.layout.select_village);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        final Spinner spnDCode;
        final Spinner spnUPCode;
        final Spinner spnUNCode;
        final Spinner spnCluster;
        final Spinner spnVCode;

        spnDCode=(Spinner)dialog.findViewById(R.id.spnDCode);
        spnUPCode=(Spinner)dialog.findViewById(R.id.spnUPCode) ;
        spnUNCode=(Spinner)dialog.findViewById(R.id.spnUNCode) ;
        spnCluster=(Spinner)dialog.findViewById(R.id.spnCluster) ;
        spnVCode=(Spinner)dialog.findViewById(R.id.spnVCode) ;
        spnDCode.setAdapter(C.getArrayAdapter("Select '' union Select DCode||'-'||DName from zilla"));
        spnUPCode.setAdapter(C.getArrayAdapter("Select '' union Select UPCode||'-'||UPName from Upazila where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnUNCode.setAdapter(C.getArrayAdapter("Select '' union Select UNCode||'-'||UNName from Unions where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnCluster.setAdapter(C.getArrayAdapter("Select '' union Select '000' union Select Cluster from Cluster where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));

        spnDCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnUPCode.setAdapter(C.getArrayAdapter("Select '' union Select UPCode||'-'||UPName from Upazila where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnUPCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnUNCode.setAdapter(C.getArrayAdapter("Select '' union Select UNCode||'-'||UNName from Unions where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnUNCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnCluster.setAdapter(C.getArrayAdapter("Select '' union Select '000' union Select Cluster from Cluster where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnCluster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                        " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        ImageButton cmdBack = (ImageButton) dialog.findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }});

        Button cmdHHListing = (Button)dialog.findViewById(R.id.cmdHHListing);
        cmdHHListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(spnDCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক জেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUPCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক উপজেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUNCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক ইউনিয়ন এর  নাম সিলেক্ট করুন");
                    return;
                }else if(spnCluster.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক ক্লাস্টার এর নম্বর সিলেক্ট করুন");
                    return;
                }
                else if(spnVCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক গ্রামের নাম সিলেক্ট করুন");
                    return;
                }

                AlertDialog.Builder adb = new AlertDialog.Builder(Bottom_Navigation_MainActivity.this);
                adb.setTitle("নিশ্চিত করুন");
                adb.setMessage("আপনি যেই গ্রাম সিলেক্ট করেছেন সেটা সঠিক কিনা পুনরায় নিশ্চিত হন এবং হ্যাঁ বাটন সিলেক্ট করুন অন্যথায় না সিলেক্ট করুন। ");
                adb.setNegativeButton("না", null);
                adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle Ibundle = new Bundle();

                        Ibundle.putString("DCode", spnDCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("UPCode", spnUPCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("UNCode", spnUNCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                        Ibundle.putString("VCode", spnVCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);

                        Intent intent = new Intent(getApplicationContext(), Household_list.class);
                        intent.putExtras(Ibundle);
                        startActivity(intent);

                        dialog.dismiss();
                    }});
                adb.show();
            }
        });

        dialog.show();
    }

    private void SelectVillageForm_Mapping()
    {
        final Dialog dialog = new Dialog(Bottom_Navigation_MainActivity.this);
        dialog.setContentView(R.layout.select_village);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        final Spinner spnDCode;
        final Spinner spnUPCode;
        final Spinner spnUNCode;
        final Spinner spnCluster;
        final Spinner spnVCode;
        spnDCode=(Spinner)dialog.findViewById(R.id.spnDCode);
        spnUPCode=(Spinner)dialog.findViewById(R.id.spnUPCode) ;
        spnUNCode=(Spinner)dialog.findViewById(R.id.spnUNCode) ;
        spnCluster=(Spinner)dialog.findViewById(R.id.spnCluster) ;
        spnVCode=(Spinner)dialog.findViewById(R.id.spnVCode) ;
        spnDCode.setAdapter(C.getArrayAdapter("Select '' union Select DCode||'-'||DName from zilla"));
        spnUPCode.setAdapter(C.getArrayAdapter("Select '' union Select UPCode||'-'||UPName from Upazila where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnUNCode.setAdapter(C.getArrayAdapter("Select '' union Select UNCode||'-'||UNName from Unions where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnCluster.setAdapter(C.getArrayAdapter("Select '' union Select '000' union Select Cluster from Cluster where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
        spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));

        spnDCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnUPCode.setAdapter(C.getArrayAdapter("Select '' union Select UPCode||'-'||UPName from Upazila where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spnUPCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnUNCode.setAdapter(C.getArrayAdapter("Select '' union Select UNCode||'-'||UNName from Unions where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnUNCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnCluster.setAdapter(C.getArrayAdapter("Select '' union Select '000' union Select Cluster from Cluster where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spnCluster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                        " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ImageButton cmdBack = (ImageButton) dialog.findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }});

        Button cmdHHListing = (Button)dialog.findViewById(R.id.cmdHHListing);
        cmdHHListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(spnDCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক জেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUPCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক উপজেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUNCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক ইউনিয়ন এর  নাম সিলেক্ট করুন");
                    return;
                }else if(spnCluster.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক ক্লাস্টার এর নম্বর সিলেক্ট করুন");
                    return;
                }
                else if(spnVCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(Bottom_Navigation_MainActivity.this,"তালিকা থেকে সঠিক গ্রামের নাম সিলেক্ট করুন");
                    return;
                }

                AlertDialog.Builder adb = new AlertDialog.Builder(Bottom_Navigation_MainActivity.this);
                adb.setTitle("নিশ্চিত করুন");
                adb.setMessage("আপনি যেই গ্রাম সিলেক্ট করেছেন সেটা সঠিক কিনা পুনরায় নিশ্চিত হন এবং হ্যাঁ বাটন সিলেক্ট করুন অন্যথায় না সিলেক্ট করুন। ");
                adb.setNegativeButton("না", null);
                adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle Ibundle = new Bundle();

                        Ibundle.putString("DCode", spnDCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("UPCode", spnUPCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("UNCode", spnUNCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                        Ibundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                        Ibundle.putString("VCode", spnVCode.getSelectedItem().toString().split("-")[0]);
                        Ibundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);

                        Intent intent = new Intent(getApplicationContext(), Mapping_Household_list.class);
                        intent.putExtras(Ibundle);
                        startActivity(intent);

                        dialog.dismiss();
                    }});
                adb.show();
            }
        });

        dialog.show();
    }
}