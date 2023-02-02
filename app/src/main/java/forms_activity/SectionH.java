
 package forms_activity;


 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.database.Cursor;
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.net.Uri;
 import android.provider.Settings;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.View.OnFocusChangeListener;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.ListView;
 import android.widget.SimpleAdapter;
 import android.widget.BaseAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.widget.ArrayAdapter;
 import android.widget.CompoundButton;
 import android.graphics.Color;
 import android.view.WindowManager;
 import Utility.*;
 import Common.*;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;

 import android.widget.AutoCompleteTextView;
// import android.support.v4.content.ContextCompat;
// import android.support.v7.app.AppCompatActivity;
 import android.text.Editable;
 import android.text.TextWatcher;

 import org.icddrb.mental_health_survey.R;

 import forms_datamodel.SectionH_DataModel;

 public class SectionH extends AppCompatActivity {
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
    LinearLayout seclblH1a;
    View linelblH1a;
    LinearLayout seclblH1b;
    View linelblH1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secH1;
    View lineH1;
    TextView VlblH1;
    RadioGroup rdogrpH1;
    RadioButton rdoH11;
    RadioButton rdoH12;
    RadioButton rdoH13;
    RadioButton rdoH14;
    RadioButton rdoH15;
    LinearLayout secH2;
    View lineH2;
    TextView VlblH2;
    RadioGroup rdogrpH2;
    RadioButton rdoH21;
    RadioButton rdoH22;
    RadioButton rdoH23;
    RadioButton rdoH24;
    RadioButton rdoH25;
    LinearLayout secH3;
    View lineH3;
    TextView VlblH3;
    RadioGroup rdogrpH3;
    RadioButton rdoH31;
    RadioButton rdoH32;
    RadioButton rdoH33;
    RadioButton rdoH34;
    RadioButton rdoH35;
    LinearLayout secH4;
    View lineH4;
    TextView VlblH4;
    RadioGroup rdogrpH4;
    RadioButton rdoH41;
    RadioButton rdoH42;
    RadioButton rdoH43;
    RadioButton rdoH44;
    RadioButton rdoH45;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String PATIENTID = "";
    static String FACILITYID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.sectionh);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionH";
         MODULEID = 8;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionH.this);
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
        Connection.LocalizeLanguage(SectionH.this, MODULEID, LANGUAGEID);





         //Hide all skip variables


        DataSearch(PATIENTID,FACILITYID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionH.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblH1a=(LinearLayout)findViewById(R.id.seclblH1a);
         linelblH1a=(View)findViewById(R.id.linelblH1a);
         seclblH1b=(LinearLayout)findViewById(R.id.seclblH1b);
         linelblH1b=(View)findViewById(R.id.linelblH1b);
         secPatientID=(LinearLayout)findViewById(R.id.secPatientID);
         linePatientID=(View)findViewById(R.id.linePatientID);
         VlblPatientID=(TextView) findViewById(R.id.VlblPatientID);
         txtPatientID=(EditText) findViewById(R.id.txtPatientID);
         txtPatientID.setText(PATIENTID);
         txtPatientID.setEnabled(false);
         secFacilityID=(LinearLayout)findViewById(R.id.secFacilityID);
         lineFacilityID=(View)findViewById(R.id.lineFacilityID);
         VlblFacilityID=(TextView) findViewById(R.id.VlblFacilityID);
         txtFacilityID=(EditText) findViewById(R.id.txtFacilityID);
         txtFacilityID.setText(FACILITYID);
         txtFacilityID.setEnabled(false);
         secH1=(LinearLayout)findViewById(R.id.secH1);
         lineH1=(View)findViewById(R.id.lineH1);
         VlblH1 = (TextView) findViewById(R.id.VlblH1);
         rdogrpH1 = (RadioGroup) findViewById(R.id.rdogrpH1);
         rdoH11 = (RadioButton) findViewById(R.id.rdoH11);
         rdoH12 = (RadioButton) findViewById(R.id.rdoH12);
         rdoH13 = (RadioButton) findViewById(R.id.rdoH13);
         rdoH14 = (RadioButton) findViewById(R.id.rdoH14);
         rdoH15 = (RadioButton) findViewById(R.id.rdoH15);
         secH2=(LinearLayout)findViewById(R.id.secH2);
         lineH2=(View)findViewById(R.id.lineH2);
         VlblH2 = (TextView) findViewById(R.id.VlblH2);
         rdogrpH2 = (RadioGroup) findViewById(R.id.rdogrpH2);
         rdoH21 = (RadioButton) findViewById(R.id.rdoH21);
         rdoH22 = (RadioButton) findViewById(R.id.rdoH22);
         rdoH23 = (RadioButton) findViewById(R.id.rdoH23);
         rdoH24 = (RadioButton) findViewById(R.id.rdoH24);
         rdoH25 = (RadioButton) findViewById(R.id.rdoH25);
         secH3=(LinearLayout)findViewById(R.id.secH3);
         lineH3=(View)findViewById(R.id.lineH3);
         VlblH3 = (TextView) findViewById(R.id.VlblH3);
         rdogrpH3 = (RadioGroup) findViewById(R.id.rdogrpH3);
         rdoH31 = (RadioButton) findViewById(R.id.rdoH31);
         rdoH32 = (RadioButton) findViewById(R.id.rdoH32);
         rdoH33 = (RadioButton) findViewById(R.id.rdoH33);
         rdoH34 = (RadioButton) findViewById(R.id.rdoH34);
         rdoH35 = (RadioButton) findViewById(R.id.rdoH35);
         secH4=(LinearLayout)findViewById(R.id.secH4);
         lineH4=(View)findViewById(R.id.lineH4);
         VlblH4 = (TextView) findViewById(R.id.VlblH4);
         rdogrpH4 = (RadioGroup) findViewById(R.id.rdogrpH4);
         rdoH41 = (RadioButton) findViewById(R.id.rdoH41);
         rdoH42 = (RadioButton) findViewById(R.id.rdoH42);
         rdoH43 = (RadioButton) findViewById(R.id.rdoH43);
         rdoH44 = (RadioButton) findViewById(R.id.rdoH44);
         rdoH45 = (RadioButton) findViewById(R.id.rdoH45);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionH.this, e.getMessage());
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
         	Connection.MessageBox(SectionH.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionH_DataModel objSave = new SectionH_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpH1 = new String[] {"1","2","3","4","5"};
         objSave.setH1("");
         for (int i = 0; i < rdogrpH1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpH1.getChildAt(i);
             if (rb.isChecked()) objSave.setH1(d_rdogrpH1[i]);
         }

         String[] d_rdogrpH2 = new String[] {"1","2","3","4","5"};
         objSave.setH2("");
         for (int i = 0; i < rdogrpH2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpH2.getChildAt(i);
             if (rb.isChecked()) objSave.setH2(d_rdogrpH2[i]);
         }

         String[] d_rdogrpH3 = new String[] {"1","2","3","4","5"};
         objSave.setH3("");
         for (int i = 0; i < rdogrpH3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpH3.getChildAt(i);
             if (rb.isChecked()) objSave.setH3(d_rdogrpH3[i]);
         }

         String[] d_rdogrpH4 = new String[] {"1","2","3","4","5"};
         objSave.setH4("");
         for (int i = 0; i < rdogrpH4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpH4.getChildAt(i);
             if (rb.isChecked()) objSave.setH4(d_rdogrpH4[i]);
         }

         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         objSave.setLat(MySharedPreferences.getValue(this, "lat"));
         objSave.setLon(MySharedPreferences.getValue(this, "lon"));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(SectionH.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(SectionH.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionH.this, e.getMessage());
         return;
     }
 }

 private String ValidationCheck()
 {
   String ValidationMsg = "";
   String DV = "";
   try
     {
         ResetSectionColor();
         if(txtPatientID.getText().toString().length()==0 & secPatientID.isShown())
           {
             ValidationMsg += "\nRequired field: Patient ID.";
             secPatientID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtFacilityID.getText().toString().length()==0 & secFacilityID.isShown())
           {
             ValidationMsg += "\nRequired field: Facility ID.";
             secFacilityID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoH11.isChecked() & !rdoH12.isChecked() & !rdoH13.isChecked() & !rdoH14.isChecked() & !rdoH15.isChecked() & secH1.isShown())
           {
             ValidationMsg += "\nH1. Required field: প্রয়োজন হলে ভবিষ্যতে আবার আমি মন-স্বাস্থ্য কেন্দ্র থেকে মনস্বাস্থ্য সেবা গ্রহন করবো.";
             secH1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoH21.isChecked() & !rdoH22.isChecked() & !rdoH23.isChecked() & !rdoH24.isChecked() & !rdoH25.isChecked() & secH2.isShown())
           {
             ValidationMsg += "\nH2. Required field: আমি এই মন-স্বাস্থ্য কেন্দ্রের মনস্বাস্থ্য সেবা সম্পর্কে আমার বন্ধু বা পরিবারের সদস্যদেরকে জানাবো.";
             secH2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoH31.isChecked() & !rdoH32.isChecked() & !rdoH33.isChecked() & !rdoH34.isChecked() & !rdoH35.isChecked() & secH3.isShown())
           {
             ValidationMsg += "\nH3. Required field: আমি আমার বন্ধু বা পরিবারের সদস্যদেরকে এই মন-স্বাস্থ্য কেন্দ্র থেকে মনস্বাস্থ্য সেবা নিতে বলবো প্রয়োজনে.";
             secH3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoH41.isChecked() & !rdoH42.isChecked() & !rdoH43.isChecked() & !rdoH44.isChecked() & !rdoH45.isChecked() & secH4.isShown())
           {
             ValidationMsg += "\nH4. Required field: আমি মন-স্বাস্থ্য কেন্দ্র্র এর মনস্বাস্থ্য সেবাকে কার্যকারী বলে মনে করি.";
             secH4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
     }
     catch(Exception  e)
     {
         ValidationMsg += "\n"+ e.getMessage();
     }

     return ValidationMsg;
 }

 private void ResetSectionColor()
 {
   try
     {
             secPatientID.setBackgroundColor(Color.WHITE);
             secFacilityID.setBackgroundColor(Color.WHITE);
             secH1.setBackgroundColor(Color.WHITE);
             secH2.setBackgroundColor(Color.WHITE);
             secH3.setBackgroundColor(Color.WHITE);
             secH4.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String PatientID, String FacilityID)
     {
       try
        {     
           RadioButton rb;
           SectionH_DataModel d = new SectionH_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionH_DataModel> data = d.SelectAll(this, SQL);
           for(SectionH_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpH1 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpH1.length; i++)
             {
                 if (String.valueOf(item.getH1()).equals(String.valueOf(d_rdogrpH1[i])))
                 {
                     rb = (RadioButton)rdogrpH1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpH2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpH2.length; i++)
             {
                 if (String.valueOf(item.getH2()).equals(String.valueOf(d_rdogrpH2[i])))
                 {
                     rb = (RadioButton)rdogrpH2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpH3 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpH3.length; i++)
             {
                 if (String.valueOf(item.getH3()).equals(String.valueOf(d_rdogrpH3[i])))
                 {
                     rb = (RadioButton)rdogrpH3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpH4 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpH4.length; i++)
             {
                 if (String.valueOf(item.getH4()).equals(String.valueOf(d_rdogrpH4[i])))
                 {
                     rb = (RadioButton)rdogrpH4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionH.this, e.getMessage());
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


      }
  };

 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
       hour = selectedHour; minute = selectedMinute;
       EditText tpTime;

    }
  };


 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
 }
}