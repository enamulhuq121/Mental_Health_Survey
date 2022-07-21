package org.icddrb.standard_v3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.cast.CastRemoteDisplayLocalService;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import forms_activity.Indicator_List;

public class HomeFragment extends Fragment {
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
                        IBundle.putString("typeid", "%");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
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

                                                // Content_Download();
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
                                                getActivity().finish();
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
                MyView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 140));
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
                            String DBREQUEST = "1";
                            if (DBREQUEST.equals("1")){
                                getActivity().startService(new Intent(getActivity(),DatabaseFileSync_Service.class));
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

}
