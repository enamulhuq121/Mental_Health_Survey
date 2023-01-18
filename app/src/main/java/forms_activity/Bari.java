
 package forms_activity;


 import android.app.Activity;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.graphics.Color;
 import android.location.Location;
 import android.os.Bundle;
 import androidx.core.content.ContextCompat;
 import androidx.appcompat.app.AppCompatActivity;
 import android.view.KeyEvent;
 import android.view.View;
 import android.view.WindowManager;
 import android.widget.Button;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RelativeLayout;
 import android.widget.SimpleAdapter;
 import android.widget.TextView;
 import android.widget.TimePicker;

 import org.icddrb.kalaazar_pkdl.R;

 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;

 import Common.Connection;
 import Common.Global;
 import Utility.MySharedPreferences;
 import forms_datamodel.Bari_DataModel;

 public class Bari extends AppCompatActivity {
    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override 
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) 
             { return false; }
        else { return true;  }
    }

    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude; 
    String VariableID;
    private int hour;
    private int minute;
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;
    Global g;
    SimpleAdapter dataAdapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    TextView lblHeading;
    LinearLayout secDCode;
    View lineDCode;
    TextView VlblDCode;
    EditText txtDCode;
     EditText txtDName;
    LinearLayout secUPCode;
    View lineUPCode;
    TextView VlblUPCode;
    EditText txtUPCode;
     EditText txtUPName;
    LinearLayout secUNCode;
    View lineUNCode;
    TextView VlblUNCode;
    EditText txtUNCode;
     EditText txtUNName;
    LinearLayout secVCode;
    View lineVCode;
    TextView VlblVCode;
    EditText txtVCode;
     EditText txtVName;
    LinearLayout secBari;
    View lineBari;
    TextView VlblBari;
    EditText txtBari;
    RelativeLayout secBariName;
    View lineBariName;
    TextView VlblBariName;
    EditText txtBariName;
     RelativeLayout secBariLoc;
    View lineBariLoc;
    TextView VlblBariLoc;
    EditText txtBariLoc;
    EditText txtCluster;
    EditText txtTotalHH;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String DCODE = "";
    static String UPCODE = "";
    static String UNCODE = "";
    static String CLUSTER = "";
    static String VCODE = "";
    static String BARI = "";
    static String VILLAGE = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.bari);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         DCODE = IDbundle.getString("DCode");
         UPCODE = IDbundle.getString("UPCode");
         UNCODE = IDbundle.getString("UNCode");
         CLUSTER = IDbundle.getString("Cluster");
         VCODE = IDbundle.getString("VCode");
         VILLAGE = IDbundle.getString("VillageName");
         BARI = IDbundle.getString("Bari");

         TableName = "Bari";
         MODULEID = 1;
         LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Bari.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

        Initialization();
        Connection.LocalizeLanguage(Bari.this, MODULEID, LANGUAGEID);




         //Hide all skip variables
        DataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Bari.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         TextView lblvillage=findViewById(R.id.lblvillage);
         lblvillage.setText("গ্রাম: "+ VILLAGE);
         secDCode=(LinearLayout)findViewById(R.id.secDCode);
         lineDCode=(View)findViewById(R.id.lineDCode);
         VlblDCode=(TextView) findViewById(R.id.VlblDCode);
         txtDCode=(EditText) findViewById(R.id.txtDCode);
         txtDCode.setText(DCODE);
         txtDName=(EditText) findViewById(R.id.txtDName);
         txtDName.setText(IDbundle.getString("DName"));
         txtDCode.setEnabled(false);
         secUPCode=(LinearLayout)findViewById(R.id.secUPCode);
         lineUPCode=(View)findViewById(R.id.lineUPCode);
         VlblUPCode=(TextView) findViewById(R.id.VlblUPCode);
         txtUPCode=(EditText) findViewById(R.id.txtUPCode);
         txtUPCode.setText(UPCODE);
         txtUPName=(EditText) findViewById(R.id.txtUPName);
         txtUPName.setText(IDbundle.getString("UPName"));
         txtUPCode.setEnabled(false);
         secUNCode=(LinearLayout)findViewById(R.id.secUNCode);
         lineUNCode=(View)findViewById(R.id.lineUNCode);
         VlblUNCode=(TextView) findViewById(R.id.VlblUNCode);
         txtUNCode=(EditText) findViewById(R.id.txtUNCode);
         txtUNCode.setText(UNCODE);
         txtUNName=(EditText) findViewById(R.id.txtUNName);
         txtUNName.setText(IDbundle.getString("UNName"));
         txtUNCode.setEnabled(false);

         txtCluster=findViewById(R.id.txtCluster);
         txtCluster.setText(CLUSTER);
         secVCode=(LinearLayout)findViewById(R.id.secVCode);
         lineVCode=(View)findViewById(R.id.lineVCode);
         VlblVCode=(TextView) findViewById(R.id.VlblVCode);
         txtVCode=(EditText) findViewById(R.id.txtVCode);
         txtVCode.setText(VCODE);
         txtVName=(EditText) findViewById(R.id.txtVName);
         txtVName.setText(IDbundle.getString("VName"));
         txtVCode.setEnabled(false);
         secBari=(LinearLayout)findViewById(R.id.secBari);
         lineBari=(View)findViewById(R.id.lineBari);
         VlblBari=(TextView) findViewById(R.id.VlblBari);
         txtBari=(EditText) findViewById(R.id.txtBari);
         if(BARI.length()==0)
             txtBari.setText(C.NewBariNumber_ByCluster(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,3));
         else
            txtBari.setText(BARI);

         txtBari.setEnabled(false);
         secBariName=(RelativeLayout)findViewById(R.id.secBariName);
         lineBariName=(View)findViewById(R.id.lineBariName);
         VlblBariName=(TextView) findViewById(R.id.VlblBariName);
         txtBariName=(EditText) findViewById(R.id.txtBariName);
         secBariLoc=(RelativeLayout)findViewById(R.id.secBariLoc);
         lineBariLoc=(View)findViewById(R.id.lineBariLoc);
         VlblBariLoc=(TextView) findViewById(R.id.VlblBariLoc);
         txtBariLoc=(EditText) findViewById(R.id.txtBariLoc);
         txtTotalHH=(EditText)findViewById(R.id.txtTotalHH);
         txtBariName.requestFocus();
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Bari.this, e.getMessage());
         return;
     }
 }

 private void DataSave()
 {
   try
     {
         String ValidationMSG = ValidationCheck();
         if(ValidationMSG.length()>0)
         {
         	Connection.MessageBox(Bari.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Bari_DataModel objSave = new Bari_DataModel();
         objSave.setDCode(txtDCode.getText().toString());
         objSave.setUPCode(txtUPCode.getText().toString());
         objSave.setUNCode(txtUNCode.getText().toString());
         objSave.setCluster(txtCluster.getText().toString());
         objSave.setVCode(txtVCode.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setBariName(txtBariName.getText().toString());
         objSave.setBariLoc(txtBariLoc.getText().toString());
         objSave.setTotalHH(txtTotalHH.getText().toString());
         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         //objSave.setLat(Double.toString(currentLatitude));
         //objSave.setLon(Double.toString(currentLongitude));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", txtBari.getText().toString());
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(Bari.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(Bari.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Bari.this, e.getMessage());
         return;
     }
 }

 private String ValidationCheck()
 {
   String ValidationMsg = "";
   try
     {
         ResetSectionColor();
         if(txtDCode.getText().toString().length()==0 & secDCode.isShown())
           {
             ValidationMsg += "\n1. Required field: District.";
             secDCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtUPCode.getText().toString().length()==0 & secUPCode.isShown())
           {
             ValidationMsg += "\n2. Required field: Upazila.";
             secUPCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtUNCode.getText().toString().length()==0 & secUNCode.isShown())
           {
             ValidationMsg += "\n3. Required field: Unions.";
             secUNCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtVCode.getText().toString().length()==0 & secVCode.isShown())
           {
             ValidationMsg += "\n4. Required field: Village.";
             secVCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             ValidationMsg += "\n5. Required field: Bari Number.";
             secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtBariName.getText().toString().length()==0 & secBariName.isShown())
           {
             ValidationMsg += "\n6. Required field: Bari Name.";
             secBariName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         /*if(txtBariLoc.getText().toString().length()==0 & secBariLoc.isShown())
           {
             ValidationMsg += "\n7. Required field: Bari Location.";
             secBariLoc.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }*/
     }
     catch(Exception  e)
     {
     }

     return ValidationMsg;
 }

 private void ResetSectionColor()
 {
   try
     {
             secDCode.setBackgroundColor(Color.WHITE);
             secUPCode.setBackgroundColor(Color.WHITE);
             secUNCode.setBackgroundColor(Color.WHITE);
             secVCode.setBackgroundColor(Color.WHITE);
             secBari.setBackgroundColor(Color.WHITE);
             secBariName.setBackgroundColor(Color.WHITE);
             secBariLoc.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String DCode, String UPCode, String UNCode,String Cluster, String VCode, String Bari)
     {
       try
        {     
           RadioButton rb;
           Bari_DataModel d = new Bari_DataModel();
           String SQL = "Select * from "+ TableName +"  Where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"'";
           List<Bari_DataModel> data = d.SelectAll(this, SQL);
           for(Bari_DataModel item : data){
               txtBariName.setText(item.getBariName());
               txtBariLoc.setText(item.getBariLoc());
               txtTotalHH.setText(item.getTotalHH());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(forms_activity.Bari.this, e.getMessage());
            return;
        }
     }



 protected Dialog onCreateDialog(int id) {
   final Calendar c = Calendar.getInstance();
   hour = c.get(Calendar.HOUR_OF_DAY);
   minute = c.get(Calendar.MINUTE);
   switch (id) {
       case DATE_DIALOG:
           return new DatePickerDialog(this, mDateSetListener,g.mYear,g.mMonth-1,g.mDay);
       case TIME_DIALOG:
           return new TimePickerDialog(this, timePickerListener, hour, minute,false);
       }
     return null;
 }

 private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      mYear = year; mMonth = monthOfYear+1; mDay = dayOfMonth;
      EditText dtpDate;


      /*dtpDate.setText(new StringBuilder()
      .append(Global.Right("00"+mDay,2)).append("/")
      .append(Global.Right("00"+mMonth,2)).append("/")
      .append(mYear));*/
      }
  };

 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
       hour = selectedHour; minute = selectedMinute;
       EditText tpTime;


          //tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));

    }
  };


 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
 }


}