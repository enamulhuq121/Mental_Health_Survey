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

import org.icddrb.mental_health_survey.R;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;
import forms_datamodel.Household_DataModel;
import gps.GPS_Data;

public class Mapping_Household_list extends AppCompatActivity {
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
    Button btnNewBari;
    Button btnLandmark;
    Bundle IDbundle;

    Spinner spnDCode;
    Spinner spnUPCode;
    Spinner spnUNCode;
    Spinner spnCluster;
    Spinner spnVCode;

    static String STARTTIME = "";
    static String DCODE = "";
    static String UPCODE = "";
    static String UNCODE = "";
    static String CLUSTER = "";
    static String VCODE = "";
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
         setContentView(R.layout.mapping_household_list);
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

         spnDCode=(Spinner)findViewById(R.id.spnDCode);
         spnUPCode=(Spinner)findViewById(R.id.spnUPCode) ;
         spnUNCode=(Spinner)findViewById(R.id.spnUNCode) ;
         spnCluster=findViewById(R.id.spnCluster);
         spnVCode=(Spinner)findViewById(R.id.spnVCode) ;

         spnDCode.setAdapter(C.getArrayAdapter("Select '"+ DCODE +"-"+ DNAME +"'"));
         spnUPCode.setAdapter(C.getArrayAdapter("Select '"+ UPCODE +"-"+ UPNAME +"'"));
         spnUNCode.setAdapter(C.getArrayAdapter("Select '"+ UNCODE +"-"+ UNNAME +"'"));
         spnCluster.setAdapter(C.getArrayAdapter("Select '"+ CLUSTER +"'"));
         spnVCode.setAdapter(C.getArrayAdapter("Select '' union select v.vcode||'-'||v.VName from Village v inner join Cluster c on v.dcode=c.dcode and v.upcode=c.upcode and v.uncode=c.uncode and v.vcode=c.vcode and c.cluster='"+ spnCluster.getSelectedItem().toString().split("-")[0] +"'" +
                 " where v.DCode='"+ spnDCode.getSelectedItem().toString().split("-")[0] +"' and v.UPCode='"+ spnUPCode.getSelectedItem().toString().split("-")[0] +"' and v.UNCode='"+ spnUNCode.getSelectedItem().toString().split("-")[0] +"'"));

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
                DataSearch();
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

                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("DCode", DCODE);
                 IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UPCode", UPCODE);
                 IDbundle.putString("UPName", spnUPCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("UNCode", UNCODE);
                 IDbundle.putString("UNName", spnUNCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("VCode", VCODE);
                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                 IDbundle.putString("Bari", "");
                 IDbundle.putString("HHNo", "");
                 IDbundle.putString("Cluster", spnCluster.getSelectedItem().toString());
                 Intent intent = new Intent(getApplicationContext(), Bari.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);

             }});


         btnLandmark = (Button) findViewById(R.id.btnLandmark);
         btnLandmark.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 if(spnVCode.getSelectedItemPosition()==0){
                     spnVCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
                     Connection.MessageBox(Mapping_Household_list.this,"Select a valid Village from the list.");
                     spnVCode.requestFocus();
                     return;
                 }else{
                     spnVCode.setBackgroundColor(Color.parseColor("#FFFFFF"));
                 }

                 DCODE = spnDCode.getSelectedItem().toString().split("-")[0];
                 UPCODE = spnUPCode.getSelectedItem().toString().split("-")[0];
                 UNCODE = spnUNCode.getSelectedItem().toString().split("-")[0];
                 VCODE = spnVCode.getSelectedItem().toString().split("-")[0];

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

        Connection.LocalizeLanguage(Mapping_Household_list.this, MODULEID, LANGUAGEID);






     }
     catch(Exception  e)
     {
         Connection.MessageBox(Mapping_Household_list.this, e.getMessage());
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

           Household_DataModel d = new Household_DataModel();
             String SQL = "Select b.DCode, b.UPCode, b.UNCode,b.Cluster, b.VCode, b.Bari, '' HHNo, '' HHHead, '' Mobile1, '' Mobile2, '' VisitStatus, ifnull(b.BariName,'')BariName,ifnull(b.BariLoc,'')BariLoc," +
                     " 0 totalmember, " +
                     " 0 cmwratotal," +
                     " '' indexhh, " +
                     " ifnull(g.Bari,'') gps," +
                     " ifnull(b.TotalHH,0)TotalHH " +
                     " from Bari b\n" +
                     " left outer join GPS_Data g on b.DCode=g.DCode and b.UPCode=g.UPCode and b.UNCode=g.UNCode and b.Cluster=g.Cluster and b.VCode=g.VCode\n" +
                     " Where b.DCode='"+ DCODE +"' and b.UPCode='"+ UPCODE +"' and b.UNCode='"+ UNCODE +"' and b.Cluster='"+ CLUSTER +"' and b.VCode='"+ VCODE +"' " +
                     " order by b.vcode, b.bari";
             List<Household_DataModel> data = d.SelectAll_Mapping(this, SQL);
             dataList.clear();

             dataList.addAll(data);
             try {
                 mAdapter.notifyDataSetChanged();
                 lblHeading.setText("বাড়ির তালিকা (মোট বাড়ি: "+ String.valueOf(dataList.size()) +")");
             }catch ( Exception ex){
                 Connection.MessageBox(Mapping_Household_list.this,ex.getMessage());
             }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Mapping_Household_list.this, e.getMessage());
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
         Button btnGPS;
         TextView lblBari;
         TextView lblBariLoc;
         TextView lblTotalHH;

         public MyViewHolder(View convertView) {
             super(convertView);
             secListRow = (RelativeLayout)convertView.findViewById(R.id.secListRow);
             secBariName = (LinearLayout)convertView.findViewById(R.id.secBariName);
             secHHDetail = (LinearLayout)convertView.findViewById(R.id.secHHDetail);

             btnGPS = (Button)convertView.findViewById(R.id.btnGPS);
             lblBari = (TextView) convertView.findViewById(R.id.lblBari);
             lblTotalHH = (TextView) convertView.findViewById(R.id.lblTotalHH);
             lblBariLoc = (TextView) convertView.findViewById(R.id.lblBariLoc);
         }
         }
         public DataAdapter(List<Household_DataModel> datalist) {
             this.dataList = datalist;
         }

         @Override
         public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mapping_household_row, parent, false);
             return new MyViewHolder(itemView);
         }

         @Override
         public void onBindViewHolder(MyViewHolder holder, int position) {
             final Household_DataModel data = dataList.get(position);
             holder.lblTotalHH.setText("মোট খানা ("+ data.getTotalHH()+")");

             //GPS
             if(data.getGPS().length()>0){
                 holder.btnGPS.setBackgroundResource(R.drawable.button_style_oval_green);
                 holder.btnGPS.setTextColor(Color.parseColor("#FFFFFF"));
             }
             else{
                 holder.btnGPS.setBackgroundResource(R.drawable.button_style_circle_line);
                 holder.btnGPS.setTextColor(Color.parseColor("#000000"));
             }

             //holder.lblBari.setText("বাড়ি: "+ data.getBari() +"-"+ data.getBariName());
             holder.lblBari.setText(data.getBari() +" : "+ data.getBariName());
             holder.lblBariLoc.setText(data.getBariLoc());

             holder.secListRow.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     final ProgressDialog progDailog = ProgressDialog.show(Mapping_Household_list.this, "", "Please Wait . . .", true);
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
                                 IDbundle.putString("Cluster", data.getCluster());
                                 Intent intent = new Intent(getApplicationContext(), Bari.class);
                                 intent.putExtras(IDbundle);
                                 startActivityForResult(intent, 1);
                             } catch (Exception e) {
                             }
                             progDailog.dismiss();
                         }
                     }.start();
                 }
             });

             holder.btnGPS.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     final ProgressDialog progDailog = ProgressDialog.show(Mapping_Household_list.this, "", "Please Wait . . .", true);
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
                                 IDbundle.putString("Cluster", data.getCluster());
                                 IDbundle.putString("VCode", data.getVCode());
                                 IDbundle.putString("VName", spnVCode.getSelectedItem().toString().split("-")[1]);
                                 IDbundle.putString("Bari", data.getBari());
                                 Intent intent = new Intent(getApplicationContext(), GPS_Data.class);
                                 intent.putExtras(IDbundle);
                                 startActivityForResult(intent, 1);
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