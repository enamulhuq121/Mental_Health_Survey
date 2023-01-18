package org.icddrb.kalaazar_pkdl;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import Common.Connection;
import Common.ProjectSetting;
import forms_activity.Household_list;
import forms_activity.Mapping_Household_list;

public class Fragment_Home extends Fragment {
    public static Fragment_Home newInstance() {
        Fragment_Home fragment = new Fragment_Home();
        return fragment;
    }

    Context thiscontext;
    Bundle IBundle;
    Connection C;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        thiscontext=container.getContext();
        IBundle = new Bundle();
        C = new Connection(thiscontext);

        GridView gv = rootView.findViewById(R.id.gridview);
        gv.setAdapter(new menuAdapter(thiscontext));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try
                {
                    if(position==0)
                    {
                        //Household Listing
                        //--------------------------------------------------------------------------
                        SelectVillageForm();

                        //Household Listing for Mapping
                        //--------------------------------------------------------------------------
                        //SelectVillageForm_Mapping();

                        /*
                        //Activity Call
                        //--------------------------------------------------------------------------
                        //Parameter
                        //IBundle.putString("typeid", "%");
                        //IBundle.putString("themeid", "%");
                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);*/
                    }
                    else if(position==1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
                        builder
                                .setTitle("Data Sync")
                                .setMessage("Do you want to synchronize data to server[Y/N]?")
                                .setIcon(R.drawable.data_sync)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:

                                                if (Connection.haveNetworkConnection(thiscontext)) {
                                                } else {
                                                    Connection.MessageBox(thiscontext,"Internet connection is not available for Data Sync.");
                                                    return;
                                                }

                                                new DataSyncTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)	//Do nothing on no
                                .show();
                    }
                    else if(position==2)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
                        builder
                                .setTitle("Exit")
                                .setIcon(R.drawable.exit)
                                .setMessage("Do you want to exit from the system[Y/N]?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //getActivity().finish();
                                                getActivity().finishAffinity();
                                                System.exit(0);
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)	//Do nothing on no
                                .show();
                    }


                }
                catch(Exception ex)
                {
                    Connection.MessageBox(thiscontext, ex.getMessage());
                }
            }
        });

        return rootView; //inflater.inflate(R.layout.fragment_home, container, false);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) thiscontext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static class menuAdapter extends BaseAdapter {
        private Context mContext;

        public menuAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return menu_list_image.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        //create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                MyView = li.inflate(R.layout.mainmenugriditem, null);

                try {
                    // Add The Image!!!
                    ImageView iv = (ImageView) MyView.findViewById(R.id.itemImage);
                    iv.setImageResource(menu_list_image[position]);

                    // Add The Text!!!
                    TextView tv = (TextView) MyView.findViewById(R.id.itemDesc);
                    tv.setTextSize(18);
                    tv.setText(menu_list[position]);

                }catch (Exception ex){
                    //String a = ex.getMessage();
                }
                MyView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 190));
            }
            return MyView;
        }

        private final String[] menu_list={
                "Data Entry",
                "Data Sync",
                "Exit"
        };

        //references to our images
        private final Integer[] menu_list_image = {
                R.drawable.new_entry,
                R.drawable.data_sync,
                R.drawable.exit,
        };
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
            dialog = new ProgressDialog(thiscontext);
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
        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                new Thread() {
                    public void run() {
                        try {
                            int count = 0;
                            int progressCount = 0;

                            //Stop Sync Service if still running
                            //----------------------------------------------------------------------
                            if (isServiceRunning(Sync_Service.class)) {
                                thiscontext.stopService(new Intent(thiscontext, Sync_Service.class));
                            }

                            //Update database level change
                            //----------------------------------------------------------------------
                            C.Sync_DatabaseStructure();

                            Connection.Populate_Index_Table();

                            //Upload data to server
                            //----------------------------------------------------------------------
                            List<String> tableList_upload = ProjectSetting.TableList_Upload();

                            if(tableList_upload.size()!=0)
                                progressCount = 50/tableList_upload.size();
                            for (int i = 0; i < tableList_upload.size(); i++) {
                                try {
                                    C.Sync_Upload_Process(tableList_upload.get(i));
                                    count +=progressCount;
                                    onProgressUpdate(tableList_upload.get(i)+","+ String.valueOf(count));
                                }catch(Exception ignored){

                                }
                            }
                            count = 50;

                            //Download data from server
                            //----------------------------------------------------------------------
                            List<String> tableList_download = ProjectSetting.TableList_Download();
                            if(tableList_download.size()!=0)
                                progressCount = 50/tableList_download.size();

                            for (int j = 0; j < tableList_download.size(); j++) {
                                try {
                                    C.Sync_Download(tableList_download.get(j),tableList_download.get(j), "");

                                    count +=progressCount;
                                    onProgressUpdate(tableList_download.get(j)+","+ String.valueOf(count));
                                }catch(Exception ignored){

                                }
                            }

                            onProgressUpdate(","+ "100");

                            //Database File Upload
                            if (ProjectSetting.Tab_Database_Upload){
                                requireActivity().startService(new Intent(getActivity(),DatabaseFileSync_Service.class));
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
                Connection.MessageBox(thiscontext, "Data Sync successfully completed.");
                dialog.dismiss();
            }
        }
    }

    private void SelectVillageForm()
    {
        final Dialog dialog = new Dialog(thiscontext);
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
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক জেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUPCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক উপজেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUNCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক ইউনিয়ন এর  নাম সিলেক্ট করুন");
                    return;
                }else if(spnCluster.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক ক্লাস্টার এর নম্বর সিলেক্ট করুন");
                    return;
                }
                else if(spnVCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক গ্রামের নাম সিলেক্ট করুন");
                    return;
                }

                AlertDialog.Builder adb = new AlertDialog.Builder(thiscontext);
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

                        Intent intent = new Intent(thiscontext, Household_list.class);
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
        final Dialog dialog = new Dialog(thiscontext);
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
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক জেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUPCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক উপজেলার নাম সিলেক্ট করুন");
                    return;
                }else if(spnUNCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক ইউনিয়ন এর  নাম সিলেক্ট করুন");
                    return;
                }else if(spnCluster.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক ক্লাস্টার এর নম্বর সিলেক্ট করুন");
                    return;
                }
                else if(spnVCode.getSelectedItemPosition()==0){
                    Connection.MessageBox(thiscontext,"তালিকা থেকে সঠিক গ্রামের নাম সিলেক্ট করুন");
                    return;
                }

                AlertDialog.Builder adb = new AlertDialog.Builder(thiscontext);
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

                        Intent intent = new Intent(thiscontext, Mapping_Household_list.class);
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
