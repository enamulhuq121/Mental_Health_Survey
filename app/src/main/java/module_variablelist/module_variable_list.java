package module_variablelist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.icddrb.mental_health_survey.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;

public class module_variable_list extends AppCompatActivity {
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
    private List<module_variable_DataModel> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    static String TableName;

    TextView lblHeading;
    TextView lblTotal;
    Button btnAdd;
    ImageButton btnSearch;
    EditText txtSearch;
    EditText txtFDate;
    EditText txtTDate;

    static String STARTTIME = "";
    static String DEVICEID  = "";

    static String ENTRYUSER = "";

    ConstraintLayout ll_no_data;
    static String UUID = "";
    static String DATA_ID = "";
    static String MODULE_ID = "";
    static String MODULE_TITLE = "";
    Bundle IDbundle;

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.module_variable_list);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         DEVICEID = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         MODULE_ID = IDbundle.getString("module_id");
         MODULE_TITLE = IDbundle.getString("module_title");
         DATA_ID = IDbundle.getString("data_id");

         TextView lblmodule_title = findViewById(R.id.lblmodule_title);
         lblmodule_title.setText(MODULE_TITLE);

         TableName = "module_variable";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblTotal = (TextView)findViewById(R.id.lblTotal);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 finish();
             }});

         btnAdd = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view) {
                         /*Bundle IDbundle = new Bundle();
                         IDbundle.putString("uuid", "");
                         Intent intent = new Intent(getApplicationContext(), module_variable.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);*/

             }});
         txtSearch = (EditText)findViewById(R.id.txtSearch);
         txtSearch.addTextChangedListener(new TextWatcher() {

             public void afterTextChanged(Editable s) {}

             public void beforeTextChanged(CharSequence s, int start,
                                           int count, int after) {
             }

             public void onTextChanged(CharSequence s, int start,
                                       int before, int count) {
                 DataSearch(txtSearch.getText().toString());
             }
         });

         btnSearch = (ImageButton) findViewById(R.id.btnSearch);
         btnSearch.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         DataSearch(txtSearch.getText().toString());

             }});


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        ll_no_data = findViewById(R.id.ll_no_data);
        mAdapter = new DataAdapter(dataList);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        txtFDate = (EditText) findViewById(R.id.txtFDate);
        txtTDate = (EditText) findViewById(R.id.txtTDate);
        txtFDate.setText(Global.DateNowDMY());
        txtTDate.setText(Global.DateNowDMY());
        txtFDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (txtFDate.getRight() - txtFDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        VariableID = "txtFDate";
                        showDialog(DATE_DIALOG);
                        return true;
                    }
                }
                return false;
            }
        });
        
        txtTDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (txtTDate.getRight() - txtTDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        VariableID = "txtTDate";
                        showDialog(DATE_DIALOG);
                        return true;
                    }
                }
                return false;
            }
        });

        C.Sync_Download("module_variable","module_variable", "");

        DataSearch(txtSearch.getText().toString());


     }
     catch(Exception  e)
     {
         Connection.MessageBox(module_variable_list.this, e.getMessage());
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
     
           module_variable_DataModel d = new module_variable_DataModel();
             String SQL = "Select v.*,ifnull(d.variable_data,'0')data,ifnull(d.uuid,'')uuid_data from module_variable v" +
                     " left outer join module_data d on v.uuid=d.uuid_variable and d.data_id='"+ DATA_ID +"'" +
                     " Where v.uuid_module='"+ MODULE_ID +"' and v.isdelete=2 and v.active='1'" +
                     " and v.variable_desc like('%"+ txtSearch.getText().toString() +"%')" +
                     " order by v.variable_desc";
             List<module_variable_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataList.addAll(data);
             if (dataList != null && !dataList.isEmpty())
             {
                 recyclerView.setVisibility(View.VISIBLE);
                 ll_no_data.setVisibility(View.GONE);
             }
             else
             {
                 recyclerView.setVisibility(View.GONE);
                 ll_no_data.setVisibility(View.VISIBLE);
             }
             try {
                 mAdapter.notifyDataSetChanged();
                 lblTotal.setText(" (Total: "+ dataList.size() +")");
             }catch ( Exception ex){
                 Connection.MessageBox(module_variable_list.this,ex.getMessage());
             }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(module_variable_list.this, e.getMessage());
            return;
        }
     }



     public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
         private List<module_variable_DataModel> _dataList;
         public class MyViewHolder extends RecyclerView.ViewHolder {
         LinearLayout secListRow;
         TextView variable_serial;
         TextView variable_desc;
         CardView card_view;
         public MyViewHolder(View convertView) {
             super(convertView);
             secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);
             variable_serial = (TextView)convertView.findViewById(R.id.variable_serial);
             variable_desc = (TextView)convertView.findViewById(R.id.variable_desc);
             card_view = (CardView) convertView.findViewById(R.id.card_view);
             }
         }
         public DataAdapter(List<module_variable_DataModel> datalist) {
             this._dataList = datalist;
         }
         @Override
         public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.module_variable_row, parent, false);
             return new MyViewHolder(itemView);
         }
         @Override
         public void onBindViewHolder(MyViewHolder holder, int position) {
             final module_variable_DataModel data = dataList.get(position);
             holder.variable_serial.setText(data.getvariable_serial());
             holder.variable_desc.setText(data.getvariable_desc());

             if(data.getdata().equals(("1"))){
                 holder.card_view.setBackgroundResource(R.drawable.style_completed_square_shape);
             }else{
                 holder.card_view.setBackgroundResource(R.drawable.style_circle_line_normal);
             }

             holder.secListRow.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     module_data_DataModel objSave = new module_data_DataModel();
                     //uuid for module_data table
                     if(data.getuuid_data().length()==0) objSave.setuuid(Global.Get_UUID());
                     else objSave.setuuid(data.getuuid_data());

                     objSave.setuuid_variable(data.getuuid());
                     objSave.setdata_id(DATA_ID);
                     if(data.getdata().equals("1")) objSave.setvariable_data("0");
                     else objSave.setvariable_data("1");
                     objSave.setdata_desc("");
                     objSave.setstatus("1");
                     objSave.setnote("");
                     objSave.setStartTime(STARTTIME);
                     objSave.setEndTime(g.CurrentTime24());
                     objSave.setDeviceID(DEVICEID);
                     objSave.setEntryUser(ENTRYUSER); //from data entry user list
                     objSave.setLat(MySharedPreferences.getValue(module_variable_list.this, "lat"));
                     objSave.setLon(MySharedPreferences.getValue(module_variable_list.this, "lon"));

                     String status = objSave.SaveUpdateData(module_variable_list.this);

                     if(status.length()==0) {
                         if (data.getdata().equals("1")) data.setdata("0");
                         else data.setdata("1");

                         dataList.set(position, data);

                         mAdapter.notifyItemChanged(position);
                     }
                 }
             });
         }
         @Override
         public int getItemCount() {
             return dataList.size();
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
            EditText txtDate;
            txtDate = findViewById(R.id.txtFDate);
            if (VariableID.equals("txtFDate"))
            {
                txtDate = findViewById(R.id.txtFDate);
            }
            else if (VariableID.equals("txtTDate"))
            {
                txtDate = findViewById(R.id.txtTDate);
            }
            txtDate.setText(new StringBuilder()
                .append(Global.Right("00"+mDay,2)).append("/")
                .append(Global.Right("00"+mMonth,2)).append("/")
                .append(mYear));
        }
     };


}