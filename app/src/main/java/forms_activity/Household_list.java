package forms_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.icddrb.kalaazar_pkdl.R;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;
import forms_datamodel.Household_DataModel;
import gps.GPS_Data;

public class Household_list extends AppCompatActivity {
    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude;

    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override 
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) 
             { return false; }
        else { return true;  }
    }
    String VariableID;
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;
    Global g;
    private List<Household_DataModel> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    static String TableName;

    TextView lblHeading;
    Button btnUpdateBari;
    Button btnNewBari;
    Button btnNewHH;
    Button btnGPS;
    Button btnLandmark;

    //EditText txtSearch;
    Bundle IDbundle;

    Spinner spnDCode;
    Spinner spnUPCode;
    Spinner spnUNCode;
    Spinner spnCluster;
    Spinner spnVCode;
    Spinner spnBari;
    RelativeLayout secBari;

    static String STARTTIME = "";
    static String DCODE = "";
    static String UPCODE = "";
    static String UNCODE = "";
    static String CLUSTER = "";
    static String VCODE = "";
    static String BARI = "";
    static String HHNO = "";
    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    MySharedPreferences sp;

    static String D = "";
    static String DNAME = "";
    static String UP = "";
    static String UPNAME = "";
    static String UN = "";
    static String UNNAME = "";
    static String CL = "";
    static String V = "";
    static String VNAME = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.household_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         IDbundle = getIntent().getExtras();
         DCODE    = IDbundle.getString("DCode");
         DNAME    = IDbundle.getString("DName");
         UPCODE   = IDbundle.getString("UPCode");
         UPNAME   = IDbundle.getString("UPName");
         UNCODE   = IDbundle.getString("UNCode");
         UNNAME   = IDbundle.getString("UNName");
         CLUSTER  = IDbundle.getString("Cluster");
         VCODE    = IDbundle.getString("VCode");
         VNAME    = IDbundle.getString("VName");

         MODULEID = 1;
         LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
         secBari = (RelativeLayout)findViewById(R.id.secBari);
         spnDCode=(Spinner)findViewById(R.id.spnDCode);
         spnUPCode=(Spinner)findViewById(R.id.spnUPCode) ;
         spnUNCode=(Spinner)findViewById(R.id.spnUNCode) ;
         spnCluster=findViewById(R.id.spnCluster);
         spnVCode=(Spinner)findViewById(R.id.spnVCode) ;
         spnBari=(Spinner)findViewById(R.id.spnBari) ;

         spnDCode.setAdapter(C.getArrayAdapter("Select '"+ DCODE +"-"+ DNAME +"'"));
         spnUPCode.setAdapter(C.getArrayAdapter("Select '"+ UPCODE +"-"+ UPNAME +"'"));
         spnUNCode.setAdapter(C.getArrayAdapter("Select '"+ UNCODE +"-"+ UNNAME +"'"));
         spnCluster.setAdapter(C.getArrayAdapter("Select '"+ CLUSTER +"'"));
         spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                 " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));
         if(spnVCode.getCount()==2) spnVCode.setSelection(1);

         spnBari.setAdapter(C.getArrayAdapter("Select ' All Bari' union Select Bari||'-'||BariName from Bari where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"' and Cluster='"+ spnCluster.getSelectedItem().toString() +"' and VCode='"+ spnVCode.getSelectedItem().toString().split("-")[0] +"'"));
         spnDCode.setEnabled(false);
         spnUPCode.setEnabled(false);
         spnUNCode.setEnabled(false);
         spnCluster.setEnabled(false);
         if(spnVCode.getCount()==1)
            spnVCode.setEnabled(false);
         else
            spnVCode.setEnabled(true);

         lblHeading = (TextView)findViewById(R.id.lblHeading);

         spnDCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });
         spnUPCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });
         spnUNCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });
         spnVCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 spnBari.setAdapter(C.getArrayAdapter("Select ' All Bari' union Select Bari||'-'||BariName from Bari where DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"' and Cluster='"+ spnCluster.getSelectedItem().toString() +"' and VCode='"+ spnVCode.getSelectedItem().toString().split("-")[0] +"'"));
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });
         spnBari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                 if(spnBari.getSelectedItemPosition()>0) secBari.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 DataSearch();
                 BARI =  spnBari.getSelectedItem().toString().split("-")[0];
                 if(C.Existence("Select * from GPS_Data where DCode='"+ DCODE +"' and UPCode='"+ UPCODE +"' and UNCode='"+ UNCODE +"' and Cluster='"+ CLUSTER +"' and VCode='"+ VCODE +"' and Bari='"+ BARI +"'")){
                     btnGPS.setBackgroundResource(R.drawable.button_style_oval_green);
                 }else{
                     btnGPS.setBackgroundResource(R.drawable.button_style_skyblue);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 finish();
             }});

         btnNewBari = (Button) findViewById(R.id.btnNewBari);
         btnNewBari.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];
                 BARI =  spnBari.getSelectedItem().toString().split("-")[0];

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VillageName", spnVCode.getSelectedItem().toString());
                 IDbundle.putString("Bari", "");
                 IDbundle.putString("HHNo", "");
                 IDbundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                 Intent intent = new Intent(getApplicationContext(), Bari.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});

         btnUpdateBari   = (Button) findViewById(R.id.btnUpdateBari);
         btnUpdateBari.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 if(spnBari.getSelectedItemPosition()==0){
                     secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
                     Connection.MessageBox(Household_list.this,"Select a valid Bari from the list.");
                     spnBari.requestFocus();
                     return;
                 }else{
                     secBari.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 }

                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];
                 BARI =  spnBari.getSelectedItem().toString().split("-")[0];

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VillageName", spnVCode.getSelectedItem().toString());
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("HHNo", "");
                 IDbundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                 Intent intent = new Intent(getApplicationContext(), Bari.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});

         btnNewHH   = (Button) findViewById(R.id.btnNewHH);
         btnNewHH.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 if(spnBari.getSelectedItemPosition()==0){
                     secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
                     Connection.MessageBox(Household_list.this,"Select a valid Bari from the list.");
                     spnBari.requestFocus();
                     return;
                 }else{
                     secBari.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 }

                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];
                 BARI =  spnBari.getSelectedItem().toString().split("-")[0];
                 String BARINAME =  spnBari.getSelectedItem().toString();

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("BariName", BARINAME);
                 IDbundle.putString("HHNo", "");
                 IDbundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                 Intent intent = new Intent(getApplicationContext(), Household.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});

         btnGPS   = (Button) findViewById(R.id.btnGPS);
         btnGPS.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 if(spnBari.getSelectedItemPosition()==0){
                     secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
                     Connection.MessageBox(Household_list.this,"Select a valid Bari from the list.");
                     spnBari.requestFocus();
                     return;
                 }else{
                     secBari.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 }

                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];
                 BARI =  spnBari.getSelectedItem().toString().split("-")[0];

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Bari", BARI);
                 Intent intent = new Intent(getApplicationContext(), GPS_Data.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});

         btnLandmark = findViewById(R.id.btnLandmark);
         btnLandmark.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 if(spnVCode.getSelectedItemPosition()==0){
                     spnVCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
                     Connection.MessageBox(Household_list.this,"Select a valid Village from the list.");
                     spnVCode.requestFocus();
                     return;
                 }else{
                     spnVCode.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 }

                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 CLUSTER = spnCluster.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Cluster", CLUSTER);
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Landmark", "");
                 Intent intent = new Intent(getApplicationContext(), Mapping_Landmark_list.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mAdapter = new DataAdapter(dataList);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Connection.LocalizeLanguage(Household_list.this, MODULEID, LANGUAGEID);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_list.this, e.getMessage());
         return;
     }
 }

    @Override
    protected void onResume() {
        super.onResume();
        tmpBariNo = "";
        DataSearch();
    }

    @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         if(resultCode == Activity.RESULT_OK) {
             String bari = data.getStringExtra("res");
             spnBari.setAdapter(C.getArrayAdapter("Select ' All Bari' union Select Bari||'-'||BariName from Bari where DCode='" + spnDCode.getSelectedItem().toString().split("-")[0] + "' and UPCode='" + spnUPCode.getSelectedItem().toString().split("-")[0] + "' and UNCode='" + spnUNCode.getSelectedItem().toString().split("-")[0] + "' and Cluster='"+ spnCluster.getSelectedItem().toString() +"' and VCode='" + spnVCode.getSelectedItem().toString().split("-")[0] + "'"));
             spnBari.setSelection(Global.SpinnerItemPositionAnyLength(spnBari, String.valueOf(bari)));
         }
        // DataSearch();
     }
 }

 private void DataSearch()
     {
       try
        {
            tmpBariNo = "";
            DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
            UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
            UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
            VCODE = spnVCode.getSelectedItem().toString().split("-")[0];
            BARI = spnBari.getSelectedItemPosition()==0 ?"%" : spnBari.getSelectedItem().toString().split("-")[0];

           Household_DataModel d = new Household_DataModel();
             String SQL = "Select h.DCode, h.UPCode, h.UNCode,h.Cluster, h.VCode, h.Bari, h.HHNo, h.HHHead, h.Mobile1, h.Mobile2, h.VisitStatus, ifnull(bn.BariName,'')BariName,b.BariLoc," +
                     " (case when h.totalmember is null or length(ifnull(h.totalmember,''))=0 then 0 else h.totalmember end) totalmember, " +
                     " (case when h.cmwratotal is null or length(ifnull(h.cmwratotal,''))=0 then 0 else h.cmwratotal end) cmwratotal," +
                     " '' indexhh " +
                     //" ifnull(i.hhno,'')indexhh " +
                     " from Household h\n" +
                     " left outer join (select b.DCode,b.UPCode,b.UNCode,b.cluster,b.VCode,b.Bari,b.BariName,min(h.HHNo)hhno from Bari b inner join Household h on b.DCode=h.DCode and b.UPCode=h.UPCode and b.Cluster=h.Cluster and b.UNCode=h.UNCode and b.VCode=h.VCode and b.Bari=h.Bari\n" +
                     "      where b.DCode='"+ DCODE +"' and b.UPCode='"+ UPCODE +"' and b.UNCode='"+ UNCODE +"' and b.VCode='"+ VCODE +"'\n" +
                     "      group by b.DCode,b.UPCode,b.UNCode,b.VCode,b.Bari,b.BariName)bn on h.dcode=bn.dcode and h.upcode=bn.upcode and h.uncode=bn.uncode and h.cluster=bn.cluster and h.vcode=bn.vcode and h.bari=bn.bari and h.hhno=bn.hhno" +
                     " inner join Bari b on h.DCode=b.DCode and h.UPCode=b.UPCode and h.UNCode=b.UNCode and h.Cluster=b.Cluster and h.VCode=b.VCode and h.Bari=b.Bari\n" +
                     //" left outer join Index_Household i on h.DCode=i.DCode and h.UPCode=i.UPCode and h.UNCode=i.UNCode and h.Cluster=i.Cluster and h.VCode=i.VCode and h.Bari=i.Bari and h.hhno=i.hhno" +
                     " Where h.DCode='"+ DCODE +"' and h.UPCode='"+ UPCODE +"' and h.UNCode='"+ UNCODE +"' and h.Cluster='"+ CLUSTER +"' and h.VCode='"+ VCODE +"' and h.Bari like('"+ BARI +"') " +
                     " order by h.vcode, h.bari,h.hhno";
             List<Household_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataList.addAll(data);
             try {
                 mAdapter.notifyDataSetChanged();
                 lblHeading.setText("খানার তালিকা (মোট খানা: "+ String.valueOf(dataList.size()) +")");
             }catch ( Exception ex){
                 Connection.MessageBox(Household_list.this,ex.getMessage());
             }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household_list.this, e.getMessage());
            return;
        }
     }


     String tmpBariName = "";
     String tmpBariNo = "";
     public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
         private List<Household_DataModel> dataList;
         public class MyViewHolder extends RecyclerView.ViewHolder {
         RelativeLayout secListRow;
         LinearLayout secBariName;
         LinearLayout secHHDetail;
         TextView HHNo;
         TextView HHHead;
         TextView Mobile;
         Button btnUpdateHH;
         Button btnHHVisit;
         TextView lblBari;
         TextView lblTotalMem;
         TextView lblTotalEMWRA;

         public MyViewHolder(View convertView) {
             super(convertView);
             secListRow = (RelativeLayout)convertView.findViewById(R.id.secListRow);
             secBariName = (LinearLayout)convertView.findViewById(R.id.secBariName);
             secHHDetail = (LinearLayout)convertView.findViewById(R.id.secHHDetail);

             HHNo = (TextView)convertView.findViewById(R.id.HHNo);
             HHHead = (TextView)convertView.findViewById(R.id.HHHead);
             Mobile = (TextView)convertView.findViewById(R.id.Mobile);
             btnUpdateHH = (Button)convertView.findViewById(R.id.btnUpdateHH);
             btnHHVisit = (Button)convertView.findViewById(R.id.btnHHVisit);
             lblBari = (TextView) convertView.findViewById(R.id.lblBari);
             lblTotalMem = (TextView) convertView.findViewById(R.id.lblTotalMem);
             lblTotalEMWRA = (TextView) convertView.findViewById(R.id.lblTotalEMWRA);
         }
         }
         public DataAdapter(List<Household_DataModel> datalist) {
             this.dataList = datalist;
         }

         @Override
         public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.household_row, parent, false);
             return new MyViewHolder(itemView);
         }

         @Override
         public void onBindViewHolder(MyViewHolder holder, int position) {
             final Household_DataModel data = dataList.get(position);
             holder.HHNo.setText(data.getHHNo());
             holder.HHHead.setText("খানা প্রধান: "+ data.getHHHead());
             holder.Mobile.setText("মোবাইল নম্বর: "+ data.getMobile1() + (data.getMobile2().length()>0?", "+ data.getMobile2():""));
             holder.lblTotalMem.setText("সদস্য ("+ data.gettotalmember()+ ")");
             holder.lblTotalEMWRA.setText("EMWRA ("+ data.getcmwratotal()+")");

             //Completed
             if(data.getVisitStatus().equals("1")){
                 holder.btnHHVisit.setBackgroundResource(R.drawable.button_style_oval_green);
                 holder.btnHHVisit.setTextColor(Color.parseColor("#FFFFFF"));
             }else if(data.getVisitStatus().equals("2")|data.getVisitStatus().equals("6")|data.getVisitStatus().equals("7")){
                 holder.btnHHVisit.setBackgroundResource(R.drawable.button_style_oval_blue);
                 holder.btnHHVisit.setTextColor(Color.parseColor("#FFFFFF"));
             }else if(data.getVisitStatus().equals("3")|data.getVisitStatus().equals("4")|data.getVisitStatus().equals("5")|data.getVisitStatus().equals("8")){
                 holder.btnHHVisit.setBackgroundResource(R.drawable.button_style_oval_red);
                 holder.btnHHVisit.setTextColor(Color.parseColor("#FFFFFF"));
             }else if(data.getVisitStatus().equals("88")){
                 holder.btnHHVisit.setBackgroundResource(R.drawable.button_style_oval_yellow);
                 holder.btnHHVisit.setTextColor(Color.parseColor("#000000"));
             }
             else{
                 holder.btnHHVisit.setBackgroundResource(R.drawable.button_style_circle_line);
                 holder.btnHHVisit.setTextColor(Color.parseColor("#000000"));
             }

             holder.lblBari.setText("বাড়ি: "+ data.getBari() +"-"+ data.getBariName());
             /*if(data.getBari().equals(tmpBariNo)){
                 holder.secBariName.setVisibility(View.GONE);
             }else{
                 holder.secBariName.setVisibility(View.VISIBLE);
             }
             tmpBariNo = data.getBari();*/

             if(data.getBariName().length()>0) holder.secBariName.setVisibility(View.VISIBLE);
             else holder.secBariName.setVisibility(View.GONE);


             //Index Household
             if(data.getIndexHH().length()>0){
                holder.HHNo.setBackgroundColor(Color.GREEN);
                 holder.HHNo.setTextColor(Color.WHITE);
             }else{
                 holder.HHNo.setBackgroundColor(Color.parseColor("#D7D7D7"));
                 holder.HHNo.setTextColor(Color.BLACK);
             }

             holder.secListRow.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     final ProgressDialog progDailog = ProgressDialog.show(Household_list.this, "", "Please Wait . . .", true);
                     new Thread() {
                         public void run() {
                             try {
                                 Bundle IDbundle = new Bundle();
                                 IDbundle.putString("DCode", data.getDCode());
                                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("UPCode", data.getUPCode());
                                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("UNCode", data.getUNCode());
                                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("VCode", data.getVCode());
                                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("Bari", data.getBari());
                                 IDbundle.putString("BariName", data.getBariName());
                                 IDbundle.putString("HHNo", data.getHHNo());
                                 IDbundle.putString("Cluster", data.getCluster());
                                 Intent f1 = new Intent(getApplicationContext(), Household.class);
                                 f1.putExtras(IDbundle);
                                 startActivityForResult(f1,1);
                             } catch (Exception e) {
                             }
                             progDailog.dismiss();
                         }
                     }.start();
                 }
             });

             holder.btnHHVisit.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     final ProgressDialog progDailog = ProgressDialog.show(Household_list.this, "", "Please Wait . . .", true);
                     new Thread() {
                         public void run() {
                             try {
                                 Bundle IDbundle = new Bundle();
                                 IDbundle.putString("DCode", data.getDCode());
                                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("UPCode", data.getUPCode());
                                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("UNCode", data.getUNCode());
                                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("VCode", data.getVCode());
                                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("Bari", data.getBari());
                                 IDbundle.putString("BariName", data.getBariName());
                                 IDbundle.putString("HHNo", data.getHHNo());
                                 IDbundle.putString("VisitNo", "");
                                 IDbundle.putString("Cluster", data.getCluster());
                                 Intent f1 = new Intent(getApplicationContext(), HouseholdVisit.class);
                                 f1.putExtras(IDbundle);
                                 startActivityForResult(f1,1);
                             } catch (Exception e) {
                             }
                             progDailog.dismiss();
                         }
                     }.start();
                 }
             });
         }
         @Override
         public int getItemCount() {
             return dataList.size();
         }
     }

     public class DividerItemDecoration extends RecyclerView.ItemDecoration {
             private final int[] ATTRS = new int[]{
                     android.R.attr.listDivider
             };
             public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
             public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
             private Drawable mDivider;
             private int mOrientation;
             public DividerItemDecoration(Context context, int orientation) {
                 final TypedArray a = context.obtainStyledAttributes(ATTRS);
                 mDivider = a.getDrawable(0);
                 a.recycle();
                 setOrientation(orientation);
             }
             public void setOrientation(int orientation) {
                 if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                     throw new IllegalArgumentException("invalid orientation");
                 }
                 mOrientation = orientation;
             }
             @Override
             public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                 if (mOrientation == VERTICAL_LIST) {
                     drawVertical(c, parent);
                 } else {
                     drawHorizontal(c, parent);
                 }
             }
             public void drawVertical(Canvas c, RecyclerView parent) {
                 final int left = parent.getPaddingLeft();
                 final int right = parent.getWidth() - parent.getPaddingRight();

                 final int childCount = parent.getChildCount();
                 for (int i = 0; i < childCount; i++) {
                     final View child = parent.getChildAt(i);
                     final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                             .getLayoutParams();
                     final int top = child.getBottom() + params.bottomMargin;
                     final int bottom = top + mDivider.getIntrinsicHeight();
                     mDivider.setBounds(left, top, right, bottom);
                     mDivider.draw(c);
                 }
             }
             public void drawHorizontal(Canvas c, RecyclerView parent) {
                 final int top = parent.getPaddingTop();
                 final int bottom = parent.getHeight() - parent.getPaddingBottom();
                 final int childCount = parent.getChildCount();
                 for (int i = 0; i < childCount; i++) {
                     final View child = parent.getChildAt(i);
                     final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                             .getLayoutParams();
                     final int left = child.getRight() + params.rightMargin;
                     final int right = left + mDivider.getIntrinsicHeight();
                     mDivider.setBounds(left, top, right, bottom);
                     mDivider.draw(c);
                 }
             }
             @Override
             public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                 if (mOrientation == VERTICAL_LIST) {
                     outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
                 } else {
                     outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
                 }
             }
     }

     public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
             private GestureDetector gestureDetector;
             private ClickListener clickListener;
             public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
                 this.clickListener = clickListener;
                 gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                     @Override
                     public boolean onSingleTapUp(MotionEvent e) {
                         return true;
                     }
                     @Override
                     public void onLongPress(MotionEvent e) {
                         View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                         if (child != null && clickListener != null) {
                             clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                         }
                     }
                 });
             }
             @Override
             public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                 View child = rv.findChildViewUnder(e.getX(), e.getY());
                 if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                     clickListener.onClick(child, rv.getChildPosition(child));
                 }
                 return false;
             }
             @Override
             public void onTouchEvent(RecyclerView rv, MotionEvent e) {
             }
             @Override
             public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
             }
             interface ClickListener {
                 void onClick(View view, int position);
                 void onLongClick(View view, int position);
             }
     }

}