
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

 import forms_datamodel.SectionG_DataModel;

 public class SectionG extends AppCompatActivity {
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
    LinearLayout seclblG1a;
    View linelblG1a;
    LinearLayout seclblG1b;
    View linelblG1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secG1;
    View lineG1;
    TextView VlblG1;
    RadioGroup rdogrpG1;
    RadioButton rdoG11;
    RadioButton rdoG12;
    RadioButton rdoG13;
    RadioButton rdoG14;
    RadioButton rdoG15;
    LinearLayout secG2;
    View lineG2;
    TextView VlblG2;
    RadioGroup rdogrpG2;
    RadioButton rdoG21;
    RadioButton rdoG22;
    RadioButton rdoG23;
    RadioButton rdoG24;
    RadioButton rdoG25;
    LinearLayout secG3;
    View lineG3;
    TextView VlblG3;
    RadioGroup rdogrpG3;
    RadioButton rdoG31;
    RadioButton rdoG32;
    RadioButton rdoG33;
    RadioButton rdoG34;
    RadioButton rdoG35;
    LinearLayout secG4;
    View lineG4;
    TextView VlblG4;
    RadioGroup rdogrpG4;
    RadioButton rdoG41;
    RadioButton rdoG42;
    RadioButton rdoG43;
    RadioButton rdoG44;
    RadioButton rdoG45;

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
         setContentView(R.layout.sectiong);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionG";
         MODULEID = 7;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionG.this);
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
        Connection.LocalizeLanguage(SectionG.this, MODULEID, LANGUAGEID);





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
         Connection.MessageBox(SectionG.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblG1a=(LinearLayout)findViewById(R.id.seclblG1a);
         linelblG1a=(View)findViewById(R.id.linelblG1a);
         seclblG1b=(LinearLayout)findViewById(R.id.seclblG1b);
         linelblG1b=(View)findViewById(R.id.linelblG1b);
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
         secG1=(LinearLayout)findViewById(R.id.secG1);
         lineG1=(View)findViewById(R.id.lineG1);
         VlblG1 = (TextView) findViewById(R.id.VlblG1);
         rdogrpG1 = (RadioGroup) findViewById(R.id.rdogrpG1);
         rdoG11 = (RadioButton) findViewById(R.id.rdoG11);
         rdoG12 = (RadioButton) findViewById(R.id.rdoG12);
         rdoG13 = (RadioButton) findViewById(R.id.rdoG13);
         rdoG14 = (RadioButton) findViewById(R.id.rdoG14);
         rdoG15 = (RadioButton) findViewById(R.id.rdoG15);
         secG2=(LinearLayout)findViewById(R.id.secG2);
         lineG2=(View)findViewById(R.id.lineG2);
         VlblG2 = (TextView) findViewById(R.id.VlblG2);
         rdogrpG2 = (RadioGroup) findViewById(R.id.rdogrpG2);
         rdoG21 = (RadioButton) findViewById(R.id.rdoG21);
         rdoG22 = (RadioButton) findViewById(R.id.rdoG22);
         rdoG23 = (RadioButton) findViewById(R.id.rdoG23);
         rdoG24 = (RadioButton) findViewById(R.id.rdoG24);
         rdoG25 = (RadioButton) findViewById(R.id.rdoG25);
         secG3=(LinearLayout)findViewById(R.id.secG3);
         lineG3=(View)findViewById(R.id.lineG3);
         VlblG3 = (TextView) findViewById(R.id.VlblG3);
         rdogrpG3 = (RadioGroup) findViewById(R.id.rdogrpG3);
         rdoG31 = (RadioButton) findViewById(R.id.rdoG31);
         rdoG32 = (RadioButton) findViewById(R.id.rdoG32);
         rdoG33 = (RadioButton) findViewById(R.id.rdoG33);
         rdoG34 = (RadioButton) findViewById(R.id.rdoG34);
         rdoG35 = (RadioButton) findViewById(R.id.rdoG35);
         secG4=(LinearLayout)findViewById(R.id.secG4);
         lineG4=(View)findViewById(R.id.lineG4);
         VlblG4 = (TextView) findViewById(R.id.VlblG4);
         rdogrpG4 = (RadioGroup) findViewById(R.id.rdogrpG4);
         rdoG41 = (RadioButton) findViewById(R.id.rdoG41);
         rdoG42 = (RadioButton) findViewById(R.id.rdoG42);
         rdoG43 = (RadioButton) findViewById(R.id.rdoG43);
         rdoG44 = (RadioButton) findViewById(R.id.rdoG44);
         rdoG45 = (RadioButton) findViewById(R.id.rdoG45);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionG.this, e.getMessage());
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
         	Connection.MessageBox(SectionG.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionG_DataModel objSave = new SectionG_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpG1 = new String[] {"1","2","3","4","5"};
         objSave.setG1("");
         for (int i = 0; i < rdogrpG1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpG1.getChildAt(i);
             if (rb.isChecked()) objSave.setG1(d_rdogrpG1[i]);
         }

         String[] d_rdogrpG2 = new String[] {"1","2","3","4","5"};
         objSave.setG2("");
         for (int i = 0; i < rdogrpG2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpG2.getChildAt(i);
             if (rb.isChecked()) objSave.setG2(d_rdogrpG2[i]);
         }

         String[] d_rdogrpG3 = new String[] {"1","2","3","4","5"};
         objSave.setG3("");
         for (int i = 0; i < rdogrpG3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpG3.getChildAt(i);
             if (rb.isChecked()) objSave.setG3(d_rdogrpG3[i]);
         }

         String[] d_rdogrpG4 = new String[] {"1","2","3","4","5"};
         objSave.setG4("");
         for (int i = 0; i < rdogrpG4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpG4.getChildAt(i);
             if (rb.isChecked()) objSave.setG4(d_rdogrpG4[i]);
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
             Connection.MessageBox(SectionG.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionH.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);
         }
         else{
             Connection.MessageBox(SectionG.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionG.this, e.getMessage());
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
         if(!rdoG11.isChecked() & !rdoG12.isChecked() & !rdoG13.isChecked() & !rdoG14.isChecked() & !rdoG15.isChecked() & secG1.isShown())
           {
             ValidationMsg += "\nG1. Required field: মন-স্বাস্থ্য কেন্দ্রে আমাকে মনস্বাস্থ্য সেবা পেতে সহযোগীতা করেছে.";
             secG1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoG21.isChecked() & !rdoG22.isChecked() & !rdoG23.isChecked() & !rdoG24.isChecked() & !rdoG25.isChecked() & secG2.isShown())
           {
             ValidationMsg += "\nG2. Required field: ডিজিটাল সিস্টেম ব্যাবহার করে আমি ভিডিও কলের মাধ্যমে সেবা নিতে পারি কিন্তু যদি সরাসরি সেবা নেয়া যেত তাহলে ভাল হত.";
             secG2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoG31.isChecked() & !rdoG32.isChecked() & !rdoG33.isChecked() & !rdoG34.isChecked() & !rdoG35.isChecked() & secG3.isShown())
           {
             ValidationMsg += "\nG3. Required field: এই মন-স্বাস্থ্য কেন্দ্রে আমার মানসিক স্বাস্থ্য সুরক্ষার জন্য আমার প্রয়োজনীয় সব কিছু সরবরাহ করতে সক্ষম.";
             secG3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoG41.isChecked() & !rdoG42.isChecked() & !rdoG43.isChecked() & !rdoG44.isChecked() & !rdoG45.isChecked() & secG4.isShown())
           {
             ValidationMsg += "\nG4. Required field: আমি মন-স্বাস্থ্য কেন্দ্রকে প্রয়োজনীয় মনে করি.";
             secG4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secG1.setBackgroundColor(Color.WHITE);
             secG2.setBackgroundColor(Color.WHITE);
             secG3.setBackgroundColor(Color.WHITE);
             secG4.setBackgroundColor(Color.WHITE);
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
           SectionG_DataModel d = new SectionG_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionG_DataModel> data = d.SelectAll(this, SQL);
           for(SectionG_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpG1 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpG1.length; i++)
             {
                 if (String.valueOf(item.getG1()).equals(String.valueOf(d_rdogrpG1[i])))
                 {
                     rb = (RadioButton)rdogrpG1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpG2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpG2.length; i++)
             {
                 if (String.valueOf(item.getG2()).equals(String.valueOf(d_rdogrpG2[i])))
                 {
                     rb = (RadioButton)rdogrpG2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpG3 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpG3.length; i++)
             {
                 if (String.valueOf(item.getG3()).equals(String.valueOf(d_rdogrpG3[i])))
                 {
                     rb = (RadioButton)rdogrpG3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpG4 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpG4.length; i++)
             {
                 if (String.valueOf(item.getG4()).equals(String.valueOf(d_rdogrpG4[i])))
                 {
                     rb = (RadioButton)rdogrpG4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionG.this, e.getMessage());
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