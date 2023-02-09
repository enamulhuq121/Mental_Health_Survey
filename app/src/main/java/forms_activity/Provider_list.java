package forms_activity;

import java.util.ArrayList;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
 import android.widget.LinearLayout;
 import android.widget.TextView;
 import android.widget.Button;
 import android.widget.ImageButton;
 import Common.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
 import android.graphics.Canvas;
 import android.graphics.Rect;
 import android.graphics.drawable.Drawable;
// import android.support.v7.widget.LinearLayoutManager;
 import android.view.GestureDetector;
// import android.support.v7.widget.DefaultItemAnimator;
 import android.widget.EditText;
 import android.view.WindowManager;
 import Utility.*;
import forms_datamodel.Provider_DataModel;

import java.util.Calendar;
 import android.widget.DatePicker;

import org.icddrb.mental_health_survey.R;

public class Provider_list extends AppCompatActivity {
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
    private List<Provider_DataModel> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    static String TableName;

    TextView lblHeading;
    Button btnAdd;
    EditText txtSearch;
    EditText dtpFDate;
    EditText dtpTDate;

    static String STARTTIME = "";
    static String DEVICEID  = "";

    static String ENTRYUSER = "";
    static String FACILITYID = "";
    static String PROVIDERID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.provider_list);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         DEVICEID = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");
         FACILITYID = MySharedPreferences.getValue(this, "facilityid");

         TableName = "Provider";
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Provider_list.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("ProviderID", "");
                         IDbundle.putString("FacilityID", FACILITYID);
                         Intent intent = new Intent(getApplicationContext(), Provider.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});
        txtSearch = (EditText)findViewById(R.id.txtSearch);


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

        dtpFDate = (EditText) findViewById(R.id.dtpFDate);
        dtpTDate = (EditText) findViewById(R.id.dtpTDate);
        dtpFDate.setText(Global.DateNowDMY());
        dtpTDate.setText(Global.DateNowDMY());
        dtpFDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (dtpFDate.getRight() - dtpFDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        VariableID = "dtpFDate";
                        showDialog(DATE_DIALOG);
                        return true;
                    }
                }
                return false;
            }
        });
        
        dtpTDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (dtpTDate.getRight() - dtpTDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        VariableID = "dtpTDate";
                        showDialog(DATE_DIALOG);
                        return true;
                    }
                }
                return false;
            }
        });


        DataSearch(txtSearch.getText().toString());


     }
     catch(Exception  e)
     {
         Connection.MessageBox(Provider_list.this, e.getMessage());
         return;
     }
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         DataSearch(txtSearch.getText().toString());
     }
 }

 private void DataSearch(String SearchText)
     {
       try
        {
     
           Provider_DataModel d = new Provider_DataModel();
             String SQL = "Select * from "+ TableName +"  Where DeviceID like('"+ DEVICEID +"')";
//             String SQL = "Select * from "+ TableName +"  Where FacilityID like('"+ SearchText +"%') or ProviderID like('"+ SearchText +"%')";
             List<Provider_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataList.addAll(data);
             try {
                 lblHeading.setText("মোট সেবা প্রদানকারী: ( "+ dataList.size() +" )");
                 mAdapter.notifyDataSetChanged();
             }catch ( Exception ex){
                 Connection.MessageBox(Provider_list.this,ex.getMessage());
             }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Provider_list.this, e.getMessage());
            return;
        }
     }



     public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
         private List<Provider_DataModel> dataList;
         public class MyViewHolder extends RecyclerView.ViewHolder {
         LinearLayout secListRow;
         TextView FacilityID;
         TextView ProviderID;
         TextView Name;
         TextView ProfeTitle;
         TextView Ward;
         TextView Expe;
         TextView ExpeFaci;
         TextView ExpeWard;
         TextView MobileNo;
         TextView A1;
         TextView A2;
         TextView A3;
         TextView A4;
         TextView A5;
         TextView A6;
         TextView A7;
         TextView A8;
         TextView A9;
         TextView A10;
         TextView A11;
         TextView A12;
         TextView A13;
         TextView A14;
         public MyViewHolder(View convertView) {
             super(convertView);
             secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);
             FacilityID = (TextView)convertView.findViewById(R.id.FacilityID);
             ProviderID = (TextView)convertView.findViewById(R.id.ProviderID);
             Name = (TextView)convertView.findViewById(R.id.Name);
//             ProfeTitle = (TextView)convertView.findViewById(R.id.ProfeTitle);
//             Ward = (TextView)convertView.findViewById(R.id.Ward);
//             Expe = (TextView)convertView.findViewById(R.id.Expe);
//             ExpeFaci = (TextView)convertView.findViewById(R.id.ExpeFaci);
//             ExpeWard = (TextView)convertView.findViewById(R.id.ExpeWard);
             MobileNo = (TextView)convertView.findViewById(R.id.MobileNo);
//             A1 = (TextView)convertView.findViewById(R.id.A1);
//             A2 = (TextView)convertView.findViewById(R.id.A2);
//             A3 = (TextView)convertView.findViewById(R.id.A3);
//             A4 = (TextView)convertView.findViewById(R.id.A4);
//             A5 = (TextView)convertView.findViewById(R.id.A5);
//             A6 = (TextView)convertView.findViewById(R.id.A6);
//             A7 = (TextView)convertView.findViewById(R.id.A7);
//             A8 = (TextView)convertView.findViewById(R.id.A8);
//             A9 = (TextView)convertView.findViewById(R.id.A9);
//             A10 = (TextView)convertView.findViewById(R.id.A10);
//             A11 = (TextView)convertView.findViewById(R.id.A11);
//             A12 = (TextView)convertView.findViewById(R.id.A12);
//             A13 = (TextView)convertView.findViewById(R.id.A13);
//             A14 = (TextView)convertView.findViewById(R.id.A14);
             }
         }
         public DataAdapter(List<Provider_DataModel> datalist) {
             this.dataList = datalist;
         }
         @Override
         public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.provider_row, parent, false);
             return new MyViewHolder(itemView);
         }
         @Override
         public void onBindViewHolder(MyViewHolder holder, int position) {
             final Provider_DataModel data = dataList.get(position);
             holder.FacilityID.setText(data.getFacilityID());
             holder.ProviderID.setText(data.getProviderID());
             holder.Name.setText(data.getName());
//             holder.ProfeTitle.setText(data.getProfeTitle());
//             holder.Ward.setText(String.valueOf(data.getWard()));
//             holder.Expe.setText(String.valueOf(data.getExpe()));
//             holder.ExpeFaci.setText(String.valueOf(data.getExpeFaci()));
//             holder.ExpeWard.setText(String.valueOf(data.getExpeWard()));
             holder.MobileNo.setText(data.getMobileNo());
//             holder.A1.setText(String.valueOf(data.getA1()));
//             holder.A2.setText(String.valueOf(data.getA2()));
//             holder.A3.setText(String.valueOf(data.getA3()));
//             holder.A4.setText(String.valueOf(data.getA4()));
//             holder.A5.setText(String.valueOf(data.getA5()));
//             holder.A6.setText(String.valueOf(data.getA6()));
//             holder.A7.setText(String.valueOf(data.getA7()));
//             holder.A8.setText(String.valueOf(data.getA8()));
//             holder.A9.setText(String.valueOf(data.getA9()));
//             holder.A10.setText(String.valueOf(data.getA10()));
//             holder.A11.setText(String.valueOf(data.getA11()));
//             holder.A12.setText(String.valueOf(data.getA12()));
//             holder.A13.setText(String.valueOf(data.getA13()));
//             holder.A14.setText(String.valueOf(data.getA14()));


             holder.secListRow.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     final ProgressDialog progDailog = ProgressDialog.show(Provider_list.this, "", "Please Wait . . .", true);
                     new Thread() {
                         public void run() {
                             try {
                                 Bundle IDbundle = new Bundle();
                                 IDbundle.putString("FacilityID", data.getFacilityID());
                                 IDbundle.putString("ProviderID", data.getProviderID());
                                 Intent f1 = new Intent(getApplicationContext(), Provider.class);
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


     protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateSetListener,g.mYear,g.mMonth-1,g.mDay);
        }
        return null;
     }


     private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year; mMonth = monthOfYear+1; mDay = dayOfMonth;
            EditText dtpDate;
            dtpDate = (EditText)findViewById(R.id.dtpFDate);
            if (VariableID.equals("dtpFDate"))
            {
                dtpDate = (EditText)findViewById(R.id.dtpFDate);
            }
            else if (VariableID.equals("dtpTDate"))
            {
                dtpDate = (EditText)findViewById(R.id.dtpTDate);
            }
            dtpDate.setText(new StringBuilder()
                .append(Global.Right("00"+mDay,2)).append("/")
                .append(Global.Right("00"+mMonth,2)).append("/")
                .append(mYear));
        }
     };


}