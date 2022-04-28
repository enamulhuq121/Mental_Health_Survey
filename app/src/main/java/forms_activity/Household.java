
 package forms_activity;


 import android.app.Activity;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.Intent;
 import android.graphics.Color;
 import android.location.Location;
 import android.os.Bundle;
 import androidx.core.content.ContextCompat;
 import androidx.appcompat.app.AppCompatActivity;
 import android.view.KeyEvent;
 import android.view.LayoutInflater;
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

 import org.icddrb.standard_v3.R;

 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;

 import Common.Connection;
 import Common.Global;
 import Utility.MySharedPreferences;
 import forms_datamodel.Household_DataModel;

 public class Household extends AppCompatActivity {
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
    LinearLayout secHHNo;
    View lineHHNo;
    TextView VlblHHNo;
    EditText txtHHNo;
    RelativeLayout secHHHead;
    View lineHHHead;
    TextView VlblHHHead;
    EditText txtHHHead;
     RelativeLayout secMobile1;
    View lineMobile1;
    TextView VlblMobile1;
    EditText txtMobile1;
     RelativeLayout secMobile2;
    View lineMobile2;
    TextView VlblMobile2;
    EditText txtMobile2;
    EditText txtCluster;

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
    static String HHNO = "";
    static String BARINAME = "";


 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.household);
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
         BARI = IDbundle.getString("Bari");
         BARINAME = IDbundle.getString("BariName");
         HHNO = IDbundle.getString("HHNo");

         TableName = "Household";
         MODULEID = 1;
         LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Household.this, R.style.CustomAlertDialog);
                 final AlertDialog alertDialog = dialogBuilder.create();
                 alertDialog.show();

                 LayoutInflater inflater = ((Activity) Household.this).getLayoutInflater();
                 View dialogView = inflater.inflate(R.layout.custom_yes_no_dialog, null);
                 TextView lblTitle = dialogView.findViewById(R.id.lblTitle);
                 TextView lblDescription = dialogView.findViewById(R.id.lblDescription);
                 Button btnOk = dialogView.findViewById(R.id.btnOk);
                 Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                 lblTitle.setText("Message");
                 lblDescription.setText("Do you want to close this form[Yes/No]?");
                 alertDialog.getWindow().setContentView(dialogView);
                 btnOk.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                         finish();
                         alertDialog.dismiss();
                     }
                 });
                 btnCancel.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                         alertDialog.dismiss();
                     }
                 });
             }});

        Initialization();
        Connection.LocalizeLanguage(Household.this, MODULEID, LANGUAGEID);

        DataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,HHNO);

        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         TextView lblBariname = findViewById(R.id.lblBariname);
         lblBariname.setText("বাড়ি: "+ BARINAME);
         secDCode=(LinearLayout)findViewById(R.id.secDCode);
         lineDCode=(View)findViewById(R.id.lineDCode);
         VlblDCode=(TextView) findViewById(R.id.VlblDCode);
         txtDCode=(EditText) findViewById(R.id.txtDCode);
         txtDName=(EditText) findViewById(R.id.txtDName);
         txtDName.setText(IDbundle.getString("DName"));
         txtDCode.setText(DCODE);
         txtDCode.setEnabled(false);
         secUPCode=(LinearLayout)findViewById(R.id.secUPCode);
         lineUPCode=(View)findViewById(R.id.lineUPCode);
         VlblUPCode=(TextView) findViewById(R.id.VlblUPCode);
         txtUPCode=(EditText) findViewById(R.id.txtUPCode);
         txtUPName=(EditText) findViewById(R.id.txtUPName);
         txtUPName.setText(IDbundle.getString("UPName"));
         txtUPCode.setText(UPCODE);
         txtUPCode.setEnabled(false);
         secUNCode=(LinearLayout)findViewById(R.id.secUNCode);
         lineUNCode=(View)findViewById(R.id.lineUNCode);
         VlblUNCode=(TextView) findViewById(R.id.VlblUNCode);
         txtUNCode=(EditText) findViewById(R.id.txtUNCode);
         txtUNName=(EditText) findViewById(R.id.txtUNName);
         txtUNName.setText(IDbundle.getString("UNName"));
         txtUNCode.setText(UNCODE);
         txtUNCode.setEnabled(false);

         txtCluster=findViewById(R.id.txtCluster);
         txtCluster.setText(CLUSTER);
         secVCode=(LinearLayout)findViewById(R.id.secVCode);
         lineVCode=(View)findViewById(R.id.lineVCode);
         VlblVCode=(TextView) findViewById(R.id.VlblVCode);
         txtVCode=(EditText) findViewById(R.id.txtVCode);
         txtVName=(EditText) findViewById(R.id.txtVName);
         txtVName.setText(IDbundle.getString("VName"));
         txtVCode.setText(VCODE);
         txtVCode.setEnabled(false);
         secBari=(LinearLayout)findViewById(R.id.secBari);
         lineBari=(View)findViewById(R.id.lineBari);
         VlblBari=(TextView) findViewById(R.id.VlblBari);
         txtBari=(EditText) findViewById(R.id.txtBari);
         txtBari.setText(BARI);
         txtBari.setEnabled(false);
         secHHNo=(LinearLayout)findViewById(R.id.secHHNo);
         lineHHNo=(View)findViewById(R.id.lineHHNo);
         VlblHHNo=(TextView) findViewById(R.id.VlblHHNo);
         txtHHNo=(EditText) findViewById(R.id.txtHHNo);
         if(HHNO.length()==0)
             txtHHNo.setText(C.NewHHNumber(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,3));
         else
            txtHHNo.setText(HHNO);

         txtHHNo.setEnabled(false);
         secHHHead=(RelativeLayout)findViewById(R.id.secHHHead);
         lineHHHead=(View)findViewById(R.id.lineHHHead);
         VlblHHHead=(TextView) findViewById(R.id.VlblHHHead);
         txtHHHead=(EditText) findViewById(R.id.txtHHHead);
         secMobile1=(RelativeLayout)findViewById(R.id.secMobile1);
         lineMobile1=(View)findViewById(R.id.lineMobile1);
         VlblMobile1=(TextView) findViewById(R.id.VlblMobile1);
         txtMobile1=(EditText) findViewById(R.id.txtMobile1);
         secMobile2=(RelativeLayout)findViewById(R.id.secMobile2);
         lineMobile2=(View)findViewById(R.id.lineMobile2);
         VlblMobile2=(TextView) findViewById(R.id.VlblMobile2);
         txtMobile2=(EditText) findViewById(R.id.txtMobile2);

         txtHHHead.requestFocus();
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household.this, e.getMessage());
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
         	Connection.MessageBox(Household.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Household_DataModel objSave = new Household_DataModel();
         objSave.setDCode(txtDCode.getText().toString());
         objSave.setUPCode(txtUPCode.getText().toString());
         objSave.setUNCode(txtUNCode.getText().toString());
         objSave.setCluster(txtCluster.getText().toString());
         objSave.setVCode(txtVCode.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setHHNo(txtHHNo.getText().toString());
         objSave.setHHHead(txtHHHead.getText().toString());
         objSave.setMobile1(txtMobile1.getText().toString());
         objSave.setMobile2(txtMobile2.getText().toString());
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

             Connection.MessageBox(Household.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(Household.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household.this, e.getMessage());
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
         if(txtHHNo.getText().toString().length()==0 & secHHNo.isShown())
           {
             ValidationMsg += "\n6. Required field: Household Number.";
             secHHNo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtHHHead.getText().toString().length()==0 & secHHHead.isShown())
           {
             ValidationMsg += "\n7. Required field: Household Head.";
             secHHHead.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtMobile1.getText().toString().length()>0 & txtMobile1.getText().toString().length()!=11 & secMobile1.isShown())
           {
             ValidationMsg += "\n8. Mobile Number should be 11 digit.";
             secMobile1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtMobile2.getText().toString().length()>0 & txtMobile2.getText().toString().length()!=11 & secMobile2.isShown())
           {
             ValidationMsg += "\n8. Mobile Number should be 11 digit.";
             secMobile2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
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
             secHHNo.setBackgroundColor(Color.WHITE);
             secHHHead.setBackgroundColor(Color.WHITE);
             secMobile1.setBackgroundColor(Color.WHITE);
             secMobile2.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String DCode, String UPCode, String UNCode,String Cluster, String VCode, String Bari, String HHNo)
     {
       try
        {     
           RadioButton rb;
           Household_DataModel d = new Household_DataModel();
           String SQL = "Select * from "+ TableName +"  Where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"' and HHNo='"+ HHNo +"'";
           List<Household_DataModel> data = d.SelectAll_Household(this, SQL);
           for(Household_DataModel item : data){
               txtHHHead.setText(item.getHHHead());
               txtMobile1.setText(item.getMobile1());
               txtMobile2.setText(item.getMobile2());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household.this, e.getMessage());
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