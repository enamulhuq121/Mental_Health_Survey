
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
 import android.widget.AutoCompleteTextView;
 //import android.support.v4.content.ContextCompat;
 //import android.support.v7.app.AppCompatActivity;
 // import android.text.Editable;
 //import android.text.TextWatcher;
 import android.widget.AutoCompleteTextView;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.constraintlayout.widget.ConstraintLayout;
 import androidx.core.content.ContextCompat;

 import android.widget.AutoCompleteTextView;
 import android.widget.Toast;

 import org.icddrb.mental_health_survey.R;

 import forms_datamodel.SPECIFICVAR_DataModel;

 public class SPECIFICVAR extends AppCompatActivity {
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
    LinearLayout seclblCC01;
    View linelblCC01;
    LinearLayout seclblCC02;
    View linelblCC02;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secCC2;
    View lineCC2;
    TextView VlblCC2;
    EditText txtCC2;
    LinearLayout secCC3;
    View lineCC3;
    TextView VlblCC3;
    RadioGroup rdogrpCC3;
    RadioButton rdoCC31;
    RadioButton rdoCC32;
    LinearLayout secCC5;
    View lineCC5;
    TextView VlblCC5;
    EditText txtCC5;
    LinearLayout secCC6;
    View lineCC6;
    TextView VlblCC6;
    RadioGroup rdogrpCC6;
    RadioButton rdoCC61;
    RadioButton rdoCC62;
    LinearLayout secCC8;
    View lineCC8;
    TextView VlblCC8;
    EditText txtCC8;
    LinearLayout seclblCC03;
    View linelblCC03;
    LinearLayout secCC9;
    View lineCC9;
    TextView VlblCC9;
    EditText txtCC9;
    LinearLayout secCC10;
    View lineCC10;
    TextView VlblCC10;
    RadioGroup rdogrpCC10;
    RadioButton rdoCC101;
    RadioButton rdoCC102;
    LinearLayout secCC11;
    View lineCC11;
    TextView VlblCC11;
    RadioGroup rdogrpCC11;
    RadioButton rdoCC111;
    RadioButton rdoCC112;
    RadioButton rdoCC113;

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
         setContentView(R.layout.specificvar);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SPECIFICVAR";
         MODULEID = 13;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SPECIFICVAR.this);
                 adb.setTitle("Close");
               //  adb.setIcon(R.drawable.favicon);
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

        Initialization();
        Connection.LocalizeLanguage(SPECIFICVAR.this, MODULEID, LANGUAGEID);





         //Hide all skip variables


        DataSearch(PATIENTID,FACILITYID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});

       // Global.RoleManagement(cmdSave, MySharedPreferences.getValue(this, "userrole"));
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SPECIFICVAR.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblCC01=(LinearLayout)findViewById(R.id.seclblCC01);
         linelblCC01=(View)findViewById(R.id.linelblCC01);
         seclblCC02=(LinearLayout)findViewById(R.id.seclblCC02);
         linelblCC02=(View)findViewById(R.id.linelblCC02);
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
         secCC2=(LinearLayout)findViewById(R.id.secCC2);
         lineCC2=(View)findViewById(R.id.lineCC2);
         VlblCC2=(TextView) findViewById(R.id.VlblCC2);
         txtCC2=(EditText) findViewById(R.id.txtCC2);
         secCC3=(LinearLayout)findViewById(R.id.secCC3);
         lineCC3=(View)findViewById(R.id.lineCC3);
         VlblCC3 = (TextView) findViewById(R.id.VlblCC3);
         rdogrpCC3 = (RadioGroup) findViewById(R.id.rdogrpCC3);
         rdoCC31 = (RadioButton) findViewById(R.id.rdoCC31);
         rdoCC32 = (RadioButton) findViewById(R.id.rdoCC32);
         secCC5=(LinearLayout)findViewById(R.id.secCC5);
         lineCC5=(View)findViewById(R.id.lineCC5);
         VlblCC5=(TextView) findViewById(R.id.VlblCC5);
         txtCC5=(EditText) findViewById(R.id.txtCC5);
         secCC6=(LinearLayout)findViewById(R.id.secCC6);
         lineCC6=(View)findViewById(R.id.lineCC6);
         VlblCC6 = (TextView) findViewById(R.id.VlblCC6);
         rdogrpCC6 = (RadioGroup) findViewById(R.id.rdogrpCC6);
         rdoCC61 = (RadioButton) findViewById(R.id.rdoCC61);
         rdoCC62 = (RadioButton) findViewById(R.id.rdoCC62);
         secCC8=(LinearLayout)findViewById(R.id.secCC8);
         lineCC8=(View)findViewById(R.id.lineCC8);
         VlblCC8=(TextView) findViewById(R.id.VlblCC8);
         txtCC8=(EditText) findViewById(R.id.txtCC8);
         seclblCC03=(LinearLayout)findViewById(R.id.seclblCC03);
         linelblCC03=(View)findViewById(R.id.linelblCC03);
         secCC9=(LinearLayout)findViewById(R.id.secCC9);
         lineCC9=(View)findViewById(R.id.lineCC9);
         VlblCC9=(TextView) findViewById(R.id.VlblCC9);
         txtCC9=(EditText) findViewById(R.id.txtCC9);
         secCC10=(LinearLayout)findViewById(R.id.secCC10);
         lineCC10=(View)findViewById(R.id.lineCC10);
         VlblCC10 = (TextView) findViewById(R.id.VlblCC10);
         rdogrpCC10 = (RadioGroup) findViewById(R.id.rdogrpCC10);
         rdoCC101 = (RadioButton) findViewById(R.id.rdoCC101);
         rdoCC102 = (RadioButton) findViewById(R.id.rdoCC102);
         secCC11=(LinearLayout)findViewById(R.id.secCC11);
         lineCC11=(View)findViewById(R.id.lineCC11);
         VlblCC11 = (TextView) findViewById(R.id.VlblCC11);
         rdogrpCC11 = (RadioGroup) findViewById(R.id.rdogrpCC11);
         rdoCC111 = (RadioButton) findViewById(R.id.rdoCC111);
         rdoCC112 = (RadioButton) findViewById(R.id.rdoCC112);
         rdoCC113 = (RadioButton) findViewById(R.id.rdoCC113);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SPECIFICVAR.this, e.getMessage());
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
         	Connection.MessageBox(SPECIFICVAR.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SPECIFICVAR_DataModel objSave = new SPECIFICVAR_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setCC2(txtCC2.getText().toString());
         String[] d_rdogrpCC3 = new String[] {"1","2"};
         objSave.setCC3("");
         for (int i = 0; i < rdogrpCC3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpCC3.getChildAt(i);
             if (rb.isChecked()) objSave.setCC3(d_rdogrpCC3[i]);
         }

         objSave.setCC5(txtCC5.getText().toString());
         String[] d_rdogrpCC6 = new String[] {"1","2"};
         objSave.setCC6("");
         for (int i = 0; i < rdogrpCC6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpCC6.getChildAt(i);
             if (rb.isChecked()) objSave.setCC6(d_rdogrpCC6[i]);
         }

         objSave.setCC8(txtCC8.getText().toString());
         objSave.setCC9(txtCC9.getText().toString());
         String[] d_rdogrpCC10 = new String[] {"1","2"};
         objSave.setCC10("");
         for (int i = 0; i < rdogrpCC10.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpCC10.getChildAt(i);
             if (rb.isChecked()) objSave.setCC10(d_rdogrpCC10[i]);
         }

         String[] d_rdogrpCC11 = new String[] {"1","2","3"};
         objSave.setCC11("");
         for (int i = 0; i < rdogrpCC11.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpCC11.getChildAt(i);
             if (rb.isChecked()) objSave.setCC11(d_rdogrpCC11[i]);
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

             Toast.makeText(SPECIFICVAR.this,"Save Successfully...",Toast.LENGTH_SHORT).show();
             finish();
         }
         else{
             Connection.MessageBox(SPECIFICVAR.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SPECIFICVAR.this, e.getMessage());
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
             ValidationMsg += "\nPatientID Required field: PatientID.";
             secPatientID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtFacilityID.getText().toString().length()==0 & secFacilityID.isShown())
           {
             ValidationMsg += "\nFacilityID Required field: FacilityID.";
             secFacilityID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtCC2.getText().toString().length()==0 & secCC2.isShown())
           {
             ValidationMsg += "\nCC2. Required field: রোগী মোট কয়টি রোগের কারনে ভর্তি (সংখ্যা).";
             secCC2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secCC2.isShown() & (Integer.valueOf(txtCC2.getText().toString().length()==0 ? "00" : txtCC2.getText().toString()) < 00 || Integer.valueOf(txtCC2.getText().toString().length()==0 ? "70" : txtCC2.getText().toString()) > 70))
           {
             ValidationMsg += "\nCC2. Value should be between 00 and 70(রোগী মোট কয়টি রোগের কারনে ভর্তি (সংখ্যা)).";
             secCC2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoCC31.isChecked() & !rdoCC32.isChecked() & secCC3.isShown())
           {
             ValidationMsg += "\nCC3. Required field: রোগী ১২ মাসের বেশি সময় যাবৎ ভুগছে এরকম কোন রোগ আছে কি?.";
             secCC3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtCC5.getText().toString().length()==0 & secCC5.isShown())
           {
             ValidationMsg += "\nCC5. Required field: রোগী মোট কয়টি দীর্ঘস্থায়ী রোগ দ্বারা আক্রান্ত(সংখ্যা).";
             secCC5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secCC5.isShown() & (Integer.valueOf(txtCC5.getText().toString().length()==0 ? "00" : txtCC5.getText().toString()) < 00 || Integer.valueOf(txtCC5.getText().toString().length()==0 ? "70" : txtCC5.getText().toString()) > 70))
           {
             ValidationMsg += "\nCC5. Value should be between 00 and 70(রোগী মোট কয়টি দীর্ঘস্থায়ী রোগ দ্বারা আক্রান্ত(সংখ্যা)).";
             secCC5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoCC61.isChecked() & !rdoCC62.isChecked() & secCC6.isShown())
           {
             ValidationMsg += "\nCC6. Required field: রোগী যে কারনে ভর্তি এবং দীর্ঘস্থায়ী রোগ ব্যাতীত অন্যান্য রোগ আছে কি?.";
             secCC6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtCC8.getText().toString().length()==0 & secCC8.isShown())
           {
             ValidationMsg += "\nCC8. Required field: রোগী মোট কয়টি অন্যান্য রোগ দ্বারা আক্রান্ত(সংখ্যা).";
             secCC8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secCC8.isShown() & (Integer.valueOf(txtCC8.getText().toString().length()==0 ? "00" : txtCC8.getText().toString()) < 00 || Integer.valueOf(txtCC8.getText().toString().length()==0 ? "995" : txtCC8.getText().toString()) > 995))
           {
             ValidationMsg += "\nCC8. Value should be between 00 and 995(রোগী মোট কয়টি অন্যান্য রোগ দ্বারা আক্রান্ত(সংখ্যা)).";
             secCC8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtCC9.getText().toString().length()==0 & secCC9.isShown())
           {
             ValidationMsg += "\nCC9. Required field: কত দিন যাবত হাসপাতালে ভর্তি? (ঘন্টা).";
             secCC9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secCC9.isShown() & (Integer.valueOf(txtCC9.getText().toString().length()==0 ? "00" : txtCC9.getText().toString()) < 00 || Integer.valueOf(txtCC9.getText().toString().length()==0 ? "990" : txtCC9.getText().toString()) > 990))
           {
             ValidationMsg += "\nCC9. Value should be between 00 and 990(কত দিন যাবত হাসপাতালে ভর্তি? (ঘন্টা)).";
             secCC9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoCC101.isChecked() & !rdoCC102.isChecked() & secCC10.isShown())
           {
             ValidationMsg += "\nCC10. Required field: এর আগে হাসপাতালে ভর্তির হওয়ার অভিজ্ঞতা আছে কি?.";
             secCC10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoCC111.isChecked() & !rdoCC112.isChecked() & !rdoCC113.isChecked() & secCC11.isShown())
           {
             ValidationMsg += "\nCC11. Required field: চলাচলে নির্ভরশীল?.";
             secCC11.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secCC2.setBackgroundColor(Color.WHITE);
             secCC2.setBackgroundColor(Color.WHITE);
             secCC3.setBackgroundColor(Color.WHITE);
             secCC5.setBackgroundColor(Color.WHITE);
             secCC5.setBackgroundColor(Color.WHITE);
             secCC6.setBackgroundColor(Color.WHITE);
             secCC8.setBackgroundColor(Color.WHITE);
             secCC8.setBackgroundColor(Color.WHITE);
             secCC9.setBackgroundColor(Color.WHITE);
             secCC9.setBackgroundColor(Color.WHITE);
             secCC10.setBackgroundColor(Color.WHITE);
             secCC11.setBackgroundColor(Color.WHITE);
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
           SPECIFICVAR_DataModel d = new SPECIFICVAR_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SPECIFICVAR_DataModel> data = d.SelectAll(this, SQL);
           for(SPECIFICVAR_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             txtCC2.setText(String.valueOf(item.getCC2()));
             String[] d_rdogrpCC3 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpCC3.length; i++)
             {
                 if (String.valueOf(item.getCC3()).equals(String.valueOf(d_rdogrpCC3[i])))
                 {
                     rb = (RadioButton)rdogrpCC3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtCC5.setText(String.valueOf(item.getCC5()));
             String[] d_rdogrpCC6 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpCC6.length; i++)
             {
                 if (String.valueOf(item.getCC6()).equals(String.valueOf(d_rdogrpCC6[i])))
                 {
                     rb = (RadioButton)rdogrpCC6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtCC8.setText(String.valueOf(item.getCC8()));
             txtCC9.setText(String.valueOf(item.getCC9()));
             String[] d_rdogrpCC10 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpCC10.length; i++)
             {
                 if (String.valueOf(item.getCC10()).equals(String.valueOf(d_rdogrpCC10[i])))
                 {
                     rb = (RadioButton)rdogrpCC10.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpCC11 = new String[] {"1","2","3"};
             for (int i = 0; i < d_rdogrpCC11.length; i++)
             {
                 if (String.valueOf(item.getCC11()).equals(String.valueOf(d_rdogrpCC11[i])))
                 {
                     rb = (RadioButton)rdogrpCC11.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SPECIFICVAR.this, e.getMessage());
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