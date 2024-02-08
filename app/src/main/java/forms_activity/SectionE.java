
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

 import forms_datamodel.SectionE_DataModel;

 public class SectionE extends AppCompatActivity {
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
    LinearLayout seclblE1a;
    View linelblE1a;
    LinearLayout seclblE1b;
    View linelblE1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secE1;
    View lineE1;
    TextView VlblE1;
    EditText txtE1;
    LinearLayout secE1Dk;
    View lineE1Dk;
    TextView VlblE1Dk;
    CheckBox chkE1Dk;
    LinearLayout secE2;
    View lineE2;
    TextView VlblE2;
    EditText txtE2;
    LinearLayout secE2Dk;
    View lineE2Dk;
    TextView VlblE2Dk;
    CheckBox chkE2Dk;
    LinearLayout secE3;
    View lineE3;
    TextView VlblE3;
    RadioGroup rdogrpE3;
    RadioButton rdoE31;
    RadioButton rdoE32;
    RadioButton rdoE33;
    RadioButton rdoE34;
    RadioButton rdoE35;
    LinearLayout secE4;
    View lineE4;
    TextView VlblE4;
    RadioGroup rdogrpE4;
    RadioButton rdoE41;
    RadioButton rdoE42;
    RadioButton rdoE43;
    RadioButton rdoE44;
    RadioButton rdoE45;
    LinearLayout secE5;
    View lineE5;
    TextView VlblE5;
    RadioGroup rdogrpE5;
    RadioButton rdoE51;
    RadioButton rdoE52;
    RadioButton rdoE53;
    RadioButton rdoE54;
    RadioButton rdoE55;
    LinearLayout secE6;
    View lineE6;
    TextView VlblE6;
    RadioGroup rdogrpE6;
    RadioButton rdoE61;
    RadioButton rdoE62;
    RadioButton rdoE63;
    RadioButton rdoE64;
    RadioButton rdoE65;

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
         setContentView(R.layout.sectione);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionE";
         MODULEID = 5;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionE.this);
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
        Connection.LocalizeLanguage(SectionE.this, MODULEID, LANGUAGEID);





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
         Connection.MessageBox(SectionE.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblE1a=(LinearLayout)findViewById(R.id.seclblE1a);
         linelblE1a=(View)findViewById(R.id.linelblE1a);
         seclblE1b=(LinearLayout)findViewById(R.id.seclblE1b);
         linelblE1b=(View)findViewById(R.id.linelblE1b);
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
         secE1=(LinearLayout)findViewById(R.id.secE1);
         lineE1=(View)findViewById(R.id.lineE1);
         VlblE1=(TextView) findViewById(R.id.VlblE1);
         txtE1=(EditText) findViewById(R.id.txtE1);
         secE1Dk=(LinearLayout)findViewById(R.id.secE1Dk);
         lineE1Dk=(View)findViewById(R.id.lineE1Dk);
         VlblE1Dk=(TextView) findViewById(R.id.VlblE1Dk);
         chkE1Dk=(CheckBox) findViewById(R.id.chkE1Dk);
         secE2=(LinearLayout)findViewById(R.id.secE2);
         lineE2=(View)findViewById(R.id.lineE2);
         VlblE2=(TextView) findViewById(R.id.VlblE2);
         txtE2=(EditText) findViewById(R.id.txtE2);
         secE2Dk=(LinearLayout)findViewById(R.id.secE2Dk);
         lineE2Dk=(View)findViewById(R.id.lineE2Dk);
         VlblE2Dk=(TextView) findViewById(R.id.VlblE2Dk);
         chkE2Dk=(CheckBox) findViewById(R.id.chkE2Dk);
         secE3=(LinearLayout)findViewById(R.id.secE3);
         lineE3=(View)findViewById(R.id.lineE3);
         VlblE3 = (TextView) findViewById(R.id.VlblE3);
         rdogrpE3 = (RadioGroup) findViewById(R.id.rdogrpE3);
         rdoE31 = (RadioButton) findViewById(R.id.rdoE31);
         rdoE32 = (RadioButton) findViewById(R.id.rdoE32);
         rdoE33 = (RadioButton) findViewById(R.id.rdoE33);
         rdoE34 = (RadioButton) findViewById(R.id.rdoE34);
         rdoE35 = (RadioButton) findViewById(R.id.rdoE35);
         secE4=(LinearLayout)findViewById(R.id.secE4);
         lineE4=(View)findViewById(R.id.lineE4);
         VlblE4 = (TextView) findViewById(R.id.VlblE4);
         rdogrpE4 = (RadioGroup) findViewById(R.id.rdogrpE4);
         rdoE41 = (RadioButton) findViewById(R.id.rdoE41);
         rdoE42 = (RadioButton) findViewById(R.id.rdoE42);
         rdoE43 = (RadioButton) findViewById(R.id.rdoE43);
         rdoE44 = (RadioButton) findViewById(R.id.rdoE44);
         rdoE45 = (RadioButton) findViewById(R.id.rdoE45);
         secE5=(LinearLayout)findViewById(R.id.secE5);
         lineE5=(View)findViewById(R.id.lineE5);
         VlblE5 = (TextView) findViewById(R.id.VlblE5);
         rdogrpE5 = (RadioGroup) findViewById(R.id.rdogrpE5);
         rdoE51 = (RadioButton) findViewById(R.id.rdoE51);
         rdoE52 = (RadioButton) findViewById(R.id.rdoE52);
         rdoE53 = (RadioButton) findViewById(R.id.rdoE53);
         rdoE54 = (RadioButton) findViewById(R.id.rdoE54);
         rdoE55 = (RadioButton) findViewById(R.id.rdoE55);
         secE6=(LinearLayout)findViewById(R.id.secE6);
         lineE6=(View)findViewById(R.id.lineE6);
         VlblE6 = (TextView) findViewById(R.id.VlblE6);
         rdogrpE6 = (RadioGroup) findViewById(R.id.rdogrpE6);
         rdoE61 = (RadioButton) findViewById(R.id.rdoE61);
         rdoE62 = (RadioButton) findViewById(R.id.rdoE62);
         rdoE63 = (RadioButton) findViewById(R.id.rdoE63);
         rdoE64 = (RadioButton) findViewById(R.id.rdoE64);
         rdoE65 = (RadioButton) findViewById(R.id.rdoE65);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionE.this, e.getMessage());
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
         	Connection.MessageBox(SectionE.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionE_DataModel objSave = new SectionE_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setE1(txtE1.getText().toString());
         objSave.setE1Dk((chkE1Dk.isChecked() ? "1" : (secE1Dk.isShown() ? "2" : "")));
         objSave.setE2(txtE2.getText().toString());
         objSave.setE2Dk((chkE2Dk.isChecked() ? "1" : (secE2Dk.isShown() ? "2" : "")));
         String[] d_rdogrpE3 = new String[] {"1","2","3","4","5"};
         objSave.setE3("");
         for (int i = 0; i < rdogrpE3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpE3.getChildAt(i);
             if (rb.isChecked()) objSave.setE3(d_rdogrpE3[i]);
         }

         String[] d_rdogrpE4 = new String[] {"1","2","3","4","5"};
         objSave.setE4("");
         for (int i = 0; i < rdogrpE4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpE4.getChildAt(i);
             if (rb.isChecked()) objSave.setE4(d_rdogrpE4[i]);
         }

         String[] d_rdogrpE5 = new String[] {"1","2","3","4","5"};
         objSave.setE5("");
         for (int i = 0; i < rdogrpE5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpE5.getChildAt(i);
             if (rb.isChecked()) objSave.setE5(d_rdogrpE5[i]);
         }

         String[] d_rdogrpE6 = new String[] {"1","2","3","4","5"};
         objSave.setE6("");
         for (int i = 0; i < rdogrpE6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpE6.getChildAt(i);
             if (rb.isChecked()) objSave.setE6(d_rdogrpE6[i]);
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
             Connection.MessageBox(SectionE.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionF.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);
         }
         else{
             Connection.MessageBox(SectionE.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionE.this, e.getMessage());
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
         if(txtE1.getText().toString().length()==0 & secE1.isShown())
           {
             ValidationMsg += "\nE1. Required field: আপনার বাড়ি থেকে এই হাসপাতালের দুরত্ব (কিঃমিঃ).";
             secE1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secE1.isShown() & (Integer.valueOf(txtE1.getText().toString().length()==0 ? "00" : txtE1.getText().toString()) < 00 || Integer.valueOf(txtE1.getText().toString().length()==0 ? "999" : txtE1.getText().toString()) > 999))
           {
             ValidationMsg += "\nE1. Value should be between 00 and 999(আপনার বাড়ি থেকে এই হাসপাতালের দুরত্ব (কিঃমিঃ)).";
             secE1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtE2.getText().toString().length()==0 & secE2.isShown())
           {
             ValidationMsg += "\nE2. Required field: বাড়ি থেকে এই জেলা হাসপাতালে যাতায়াতের সময় (যে যানবাহনেই আসুক না কেন, সেটার জন্য) (মিনিট).";
             secE2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secE2.isShown() & (Integer.valueOf(txtE2.getText().toString().length()==0 ? "00" : txtE2.getText().toString()) < 00 || Integer.valueOf(txtE2.getText().toString().length()==0 ? "999" : txtE2.getText().toString()) > 999))
           {
             ValidationMsg += "\nE2. Value should be between 00 and 999(বাড়ি থেকে এই জেলা হাসপাতালে যাতায়াতের সময় (যে যানবাহনেই আসুক না কেন, সেটার জন্য) (মিনিট)).";
             secE2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoE31.isChecked() & !rdoE32.isChecked() & !rdoE33.isChecked() & !rdoE34.isChecked() & !rdoE35.isChecked() & secE3.isShown())
           {
             ValidationMsg += "\nE3. Required field: মন-স্বাস্থ্য কেন্দ্র আমার মনস্বাস্থ্য সেবা নেয়াকে সহজ করেছে.";
             secE3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoE41.isChecked() & !rdoE42.isChecked() & !rdoE43.isChecked() & !rdoE44.isChecked() & !rdoE45.isChecked() & secE4.isShown())
           {
             ValidationMsg += "\nE4. Required field: মন-স্বাস্থ্য কেন্দ্র আমার মনস্বাস্থ্য সেবার এক্সেস উন্নত করেছে.";
             secE4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoE51.isChecked() & !rdoE52.isChecked() & !rdoE53.isChecked() & !rdoE54.isChecked() & !rdoE55.isChecked() & secE5.isShown())
           {
             ValidationMsg += "\nE5. Required field: আগে আমি মনস্বাস্থের জন্য কোন সেবা নিতে পারতাম না, এখন মন-স্বাস্থ্য কেন্দ্র মাধ্যমে আমি মনস্বাস্থ্য সেবা নিতে পারছি.";
             secE5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoE61.isChecked() & !rdoE62.isChecked() & !rdoE63.isChecked() & !rdoE64.isChecked() & !rdoE65.isChecked() & secE6.isShown())
           {
             ValidationMsg += "\nE6. Required field: যখন প্রয়োজন, আমি সহজেই মন-স্বাস্থ্য কেন্দ্র থেকে মনস্বাস্থ্য সেবা নেয়ার জন্য আপোইয়েন্টমেন্ট ঠিক করতে পারি.";
             secE6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secE1.setBackgroundColor(Color.WHITE);
             secE1.setBackgroundColor(Color.WHITE);
             secE2.setBackgroundColor(Color.WHITE);
             secE2.setBackgroundColor(Color.WHITE);
             secE3.setBackgroundColor(Color.WHITE);
             secE4.setBackgroundColor(Color.WHITE);
             secE5.setBackgroundColor(Color.WHITE);
             secE6.setBackgroundColor(Color.WHITE);
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
           SectionE_DataModel d = new SectionE_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionE_DataModel> data = d.SelectAll(this, SQL);
           for(SectionE_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             txtE1.setText(String.valueOf(item.getE1()));
             if(String.valueOf(item.getE1Dk()).equals("1"))
             {
                chkE1Dk.setChecked(true);
             }
             else if(String.valueOf(item.getE1Dk()).equals("2"))
             {
                chkE1Dk.setChecked(false);
             }
             txtE2.setText(String.valueOf(item.getE2()));
             if(String.valueOf(item.getE2Dk()).equals("1"))
             {
                chkE2Dk.setChecked(true);
             }
             else if(String.valueOf(item.getE2Dk()).equals("2"))
             {
                chkE2Dk.setChecked(false);
             }
             String[] d_rdogrpE3 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpE3.length; i++)
             {
                 if (String.valueOf(item.getE3()).equals(String.valueOf(d_rdogrpE3[i])))
                 {
                     rb = (RadioButton)rdogrpE3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpE4 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpE4.length; i++)
             {
                 if (String.valueOf(item.getE4()).equals(String.valueOf(d_rdogrpE4[i])))
                 {
                     rb = (RadioButton)rdogrpE4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpE5 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpE5.length; i++)
             {
                 if (String.valueOf(item.getE5()).equals(String.valueOf(d_rdogrpE5[i])))
                 {
                     rb = (RadioButton)rdogrpE5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpE6 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpE6.length; i++)
             {
                 if (String.valueOf(item.getE6()).equals(String.valueOf(d_rdogrpE6[i])))
                 {
                     rb = (RadioButton)rdogrpE6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionE.this, e.getMessage());
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