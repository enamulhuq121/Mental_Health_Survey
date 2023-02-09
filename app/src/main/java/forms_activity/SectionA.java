
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
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;

 import org.icddrb.mental_health_survey.R;

 import Utility.*;
 import Common.*;
// import android.widget.AutoCompleteTextView;
// import android.support.v4.content.ContextCompat;
// import android.support.v7.app.AppCompatActivity;
// import android.text.Editable;
// import android.text.TextWatcher;
 import forms_datamodel.SectionA_DataModel;

 public class SectionA extends AppCompatActivity {
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
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout seclblA1a;
    View linelblA1a;
    LinearLayout seclblA1b;
    View linelblA1b;
    LinearLayout seclblA1c;
    View linelblA1c;
    LinearLayout secA1;
    View lineA1;
    TextView VlblA1;
    RadioGroup rdogrpA1;
    RadioButton rdoA11;
    RadioButton rdoA12;
    RadioButton rdoA13;
    RadioButton rdoA14;
    LinearLayout secA2;
    View lineA2;
    TextView VlblA2;
    RadioGroup rdogrpA2;
    RadioButton rdoA21;
    RadioButton rdoA22;
    RadioButton rdoA23;
    RadioButton rdoA24;
    LinearLayout secA3;
    View lineA3;
    TextView VlblA3;
    RadioGroup rdogrpA3;
    RadioButton rdoA31;
    RadioButton rdoA32;
    RadioButton rdoA33;
    RadioButton rdoA34;
    LinearLayout secA4;
    View lineA4;
    TextView VlblA4;
    RadioGroup rdogrpA4;
    RadioButton rdoA41;
    RadioButton rdoA42;
    RadioButton rdoA43;
    RadioButton rdoA44;
    LinearLayout secA5;
    View lineA5;
    TextView VlblA5;
    RadioGroup rdogrpA5;
    RadioButton rdoA51;
    RadioButton rdoA52;
    RadioButton rdoA53;
    RadioButton rdoA54;
    LinearLayout secA6;
    View lineA6;
    TextView VlblA6;
    RadioGroup rdogrpA6;
    RadioButton rdoA61;
    RadioButton rdoA62;
    RadioButton rdoA63;
    RadioButton rdoA64;
    LinearLayout secA7;
    View lineA7;
    TextView VlblA7;
    RadioGroup rdogrpA7;
    RadioButton rdoA71;
    RadioButton rdoA72;
    RadioButton rdoA73;
    RadioButton rdoA74;

     LinearLayout secA8;
     View lineA8;
     TextView VlblA8;
     RadioGroup rdogrpA8;
     RadioButton rdoA81;
     RadioButton rdoA82;
     RadioButton rdoA83;
     RadioButton rdoA84;

    LinearLayout secA9;
    View lineA9;
    TextView VlblA9;
    RadioGroup rdogrpA9;
    RadioButton rdoA91;
    RadioButton rdoA92;
    RadioButton rdoA93;
    RadioButton rdoA94;
    LinearLayout secAScore;
    View lineAScore;
    TextView VlblAScore;
    EditText txtAScore;

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
         setContentView(R.layout.sectiona);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionA";
         MODULEID = 1;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionA.this);
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
        Connection.LocalizeLanguage(SectionA.this, MODULEID, LANGUAGEID);

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
         Connection.MessageBox(SectionA.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
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
         seclblA1a=(LinearLayout)findViewById(R.id.seclblA1a);
         linelblA1a=(View)findViewById(R.id.linelblA1a);
         seclblA1b=(LinearLayout)findViewById(R.id.seclblA1b);
         linelblA1b=(View)findViewById(R.id.linelblA1b);
         seclblA1c=(LinearLayout)findViewById(R.id.seclblA1c);
         linelblA1c=(View)findViewById(R.id.linelblA1c);
         secA1=(LinearLayout)findViewById(R.id.secA1);
         lineA1=(View)findViewById(R.id.lineA1);
         VlblA1 = (TextView) findViewById(R.id.VlblA1);
         rdogrpA1 = (RadioGroup) findViewById(R.id.rdogrpA1);
         rdoA11 = (RadioButton) findViewById(R.id.rdoA11);
         rdoA12 = (RadioButton) findViewById(R.id.rdoA12);
         rdoA13 = (RadioButton) findViewById(R.id.rdoA13);
         rdoA14 = (RadioButton) findViewById(R.id.rdoA14);
         secA2=(LinearLayout)findViewById(R.id.secA2);
         lineA2=(View)findViewById(R.id.lineA2);
         VlblA2 = (TextView) findViewById(R.id.VlblA2);
         rdogrpA2 = (RadioGroup) findViewById(R.id.rdogrpA2);
         rdoA21 = (RadioButton) findViewById(R.id.rdoA21);
         rdoA22 = (RadioButton) findViewById(R.id.rdoA22);
         rdoA23 = (RadioButton) findViewById(R.id.rdoA23);
         rdoA24 = (RadioButton) findViewById(R.id.rdoA24);
         secA3=(LinearLayout)findViewById(R.id.secA3);
         lineA3=(View)findViewById(R.id.lineA3);
         VlblA3 = (TextView) findViewById(R.id.VlblA3);
         rdogrpA3 = (RadioGroup) findViewById(R.id.rdogrpA3);
         rdoA31 = (RadioButton) findViewById(R.id.rdoA31);
         rdoA32 = (RadioButton) findViewById(R.id.rdoA32);
         rdoA33 = (RadioButton) findViewById(R.id.rdoA33);
         rdoA34 = (RadioButton) findViewById(R.id.rdoA34);
         secA4=(LinearLayout)findViewById(R.id.secA4);
         lineA4=(View)findViewById(R.id.lineA4);
         VlblA4 = (TextView) findViewById(R.id.VlblA4);
         rdogrpA4 = (RadioGroup) findViewById(R.id.rdogrpA4);
         rdoA41 = (RadioButton) findViewById(R.id.rdoA41);
         rdoA42 = (RadioButton) findViewById(R.id.rdoA42);
         rdoA43 = (RadioButton) findViewById(R.id.rdoA43);
         rdoA44 = (RadioButton) findViewById(R.id.rdoA44);
         secA5=(LinearLayout)findViewById(R.id.secA5);
         lineA5=(View)findViewById(R.id.lineA5);
         VlblA5 = (TextView) findViewById(R.id.VlblA5);
         rdogrpA5 = (RadioGroup) findViewById(R.id.rdogrpA5);
         rdoA51 = (RadioButton) findViewById(R.id.rdoA51);
         rdoA52 = (RadioButton) findViewById(R.id.rdoA52);
         rdoA53 = (RadioButton) findViewById(R.id.rdoA53);
         rdoA54 = (RadioButton) findViewById(R.id.rdoA54);
         secA6=(LinearLayout)findViewById(R.id.secA6);
         lineA6=(View)findViewById(R.id.lineA6);
         VlblA6 = (TextView) findViewById(R.id.VlblA6);
         rdogrpA6 = (RadioGroup) findViewById(R.id.rdogrpA6);
         rdoA61 = (RadioButton) findViewById(R.id.rdoA61);
         rdoA62 = (RadioButton) findViewById(R.id.rdoA62);
         rdoA63 = (RadioButton) findViewById(R.id.rdoA63);
         rdoA64 = (RadioButton) findViewById(R.id.rdoA64);
         secA7=(LinearLayout)findViewById(R.id.secA7);
         lineA7=(View)findViewById(R.id.lineA7);
         VlblA7 = (TextView) findViewById(R.id.VlblA7);
         rdogrpA7 = (RadioGroup) findViewById(R.id.rdogrpA7);
         rdoA71 = (RadioButton) findViewById(R.id.rdoA71);
         rdoA72 = (RadioButton) findViewById(R.id.rdoA72);
         rdoA73 = (RadioButton) findViewById(R.id.rdoA73);
         rdoA74 = (RadioButton) findViewById(R.id.rdoA74);
         secA8=(LinearLayout)findViewById(R.id.secA8);
         lineA8=(View)findViewById(R.id.lineA8);
         VlblA8 = (TextView) findViewById(R.id.VlblA8);
         rdogrpA8 = (RadioGroup) findViewById(R.id.rdogrpA8);
         rdoA81 = (RadioButton) findViewById(R.id.rdoA81);
         rdoA82 = (RadioButton) findViewById(R.id.rdoA82);
         rdoA83 = (RadioButton) findViewById(R.id.rdoA83);
         rdoA84 = (RadioButton) findViewById(R.id.rdoA84);
         secA9=(LinearLayout)findViewById(R.id.secA9);
         lineA9=(View)findViewById(R.id.lineA9);
         VlblA9 = (TextView) findViewById(R.id.VlblA9);
         rdogrpA9 = (RadioGroup) findViewById(R.id.rdogrpA9);
         rdoA91 = (RadioButton) findViewById(R.id.rdoA91);
         rdoA92 = (RadioButton) findViewById(R.id.rdoA92);
         rdoA93 = (RadioButton) findViewById(R.id.rdoA93);
         rdoA94 = (RadioButton) findViewById(R.id.rdoA94);
         secAScore=(LinearLayout)findViewById(R.id.secAScore);
         lineAScore=(View)findViewById(R.id.lineAScore);
         VlblAScore=(TextView) findViewById(R.id.VlblAScore);
         txtAScore=(EditText) findViewById(R.id.txtAScore);

     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionA.this, e.getMessage());
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
         	Connection.MessageBox(SectionA.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionA_DataModel objSave = new SectionA_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpA1 = new String[] {"0","1","2","3"};
         objSave.setA1("");
         for (int i = 0; i < rdogrpA1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA1.getChildAt(i);
             if (rb.isChecked()) objSave.setA1(d_rdogrpA1[i]);
         }

         String[] d_rdogrpA2 = new String[] {"0","1","2","3"};
         objSave.setA2("");
         for (int i = 0; i < rdogrpA2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA2.getChildAt(i);
             if (rb.isChecked()) objSave.setA2(d_rdogrpA2[i]);
         }

         String[] d_rdogrpA3 = new String[] {"0","1","2","3"};
         objSave.setA3("");
         for (int i = 0; i < rdogrpA3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA3.getChildAt(i);
             if (rb.isChecked()) objSave.setA3(d_rdogrpA3[i]);
         }

         String[] d_rdogrpA4 = new String[] {"0","1","2","3"};
         objSave.setA4("");
         for (int i = 0; i < rdogrpA4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA4.getChildAt(i);
             if (rb.isChecked()) objSave.setA4(d_rdogrpA4[i]);
         }

         String[] d_rdogrpA5 = new String[] {"0","1","2","3"};
         objSave.setA5("");
         for (int i = 0; i < rdogrpA5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA5.getChildAt(i);
             if (rb.isChecked()) objSave.setA5(d_rdogrpA5[i]);
         }

         String[] d_rdogrpA6 = new String[] {"0","1","2","3"};
         objSave.setA6("");
         for (int i = 0; i < rdogrpA6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA6.getChildAt(i);
             if (rb.isChecked()) objSave.setA6(d_rdogrpA6[i]);
         }

         String[] d_rdogrpA7 = new String[] {"0","1","2","3"};
         objSave.setA7("");
         for (int i = 0; i < rdogrpA7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA7.getChildAt(i);
             if (rb.isChecked()) objSave.setA7(d_rdogrpA7[i]);
         }

         String[] d_rdogrpA8 = new String[] {"0","1","2","3"};
         objSave.setA8("");
         for (int i = 0; i < rdogrpA8.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA8.getChildAt(i);
             if (rb.isChecked()) objSave.setA8(d_rdogrpA8[i]);
         }

         String[] d_rdogrpA9 = new String[] {"0","1","2","3"};
         objSave.setA9("");
         for (int i = 0; i < rdogrpA9.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA9.getChildAt(i);
             if (rb.isChecked()) objSave.setA9(d_rdogrpA9[i]);
         }

         objSave.setAScore(txtAScore.getText().toString());
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
             Connection.MessageBox(SectionA.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionB.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);
         }
         else{
             Connection.MessageBox(SectionA.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionA.this, e.getMessage());
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
         if(!rdoA11.isChecked() & !rdoA12.isChecked() & !rdoA13.isChecked() & !rdoA14.isChecked() & secA1.isShown())
           {
             ValidationMsg += "\nA1. Required field: যে কোন কাজ করার ক্ষেত্রে কম আগ্রহ বা কম আনন্দ?.";
             secA1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA21.isChecked() & !rdoA22.isChecked() & !rdoA23.isChecked() & !rdoA24.isChecked() & secA2.isShown())
           {
             ValidationMsg += "\nA2. Required field: প্রতিটি কাজের ক্ষেত্রে হতাশ বা আশাহত অনুভব করা?.";
             secA2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA31.isChecked() & !rdoA32.isChecked() & !rdoA33.isChecked() & !rdoA34.isChecked() & secA3.isShown())
           {
             ValidationMsg += "\nA3. Required field: ঘুমাতে সমস্যা বা নিদ্রাহীনতা, কিংবা অতিরিক্ত ঘুমানো?.";
             secA3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA41.isChecked() & !rdoA42.isChecked() & !rdoA43.isChecked() & !rdoA44.isChecked() & secA4.isShown())
           {
             ValidationMsg += "\nA4. Required field: ক্লান্ত বোধ বা কাজকর্মে কম শক্তি অনুভব করা?.";
             secA4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA51.isChecked() & !rdoA52.isChecked() & !rdoA53.isChecked() & !rdoA54.isChecked() & secA5.isShown())
           {
             ValidationMsg += "\nA5. Required field: খাবার অরুচি অথবা অতিরিক্ত খাবার গ্রহণ করা?.";
             secA5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA61.isChecked() & !rdoA62.isChecked() & !rdoA63.isChecked() & !rdoA64.isChecked() & secA6.isShown())
           {
             ValidationMsg += "\nA6. Required field: আপনার নিজের সম্পর্কে খারাপ লাগছে— যেমন আপনি একজন ব্যর্থ মানুষ বা আপনি আপনার পরিবার ও সমাজের চোখে একজন ব্যর্থ ব্যক্তি?.";
             secA6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA71.isChecked() & !rdoA72.isChecked() & !rdoA73.isChecked() & !rdoA74.isChecked() & secA7.isShown())
           {
             ValidationMsg += "\nA7. Required field: যে কোন বিষয়ে মনোযোগ স্থাপনে অসুবিধা— যেমন পড়াশুনা ও অন্যান্য কাজ ইত্যাদি?.";
             secA7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA81.isChecked() & !rdoA82.isChecked() & !rdoA83.isChecked() & !rdoA84.isChecked() & secA8.isShown())
         {
             ValidationMsg += "\nA8. Required field: এত ধীরে ধীরে চলাফেরা বা কথা বলছেন যাতে অন্য লোকেরা লক্ষ্য করতে পারে অথবা, এত অস্থির বা দ্রুত কাজকর্ম বা কথাবার্তা বলছেন যা আপনার স্বাভাবিক মাত্রার চেয়ে অনেক বেশি?.";
             secA8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
         }
         if(!rdoA91.isChecked() & !rdoA92.isChecked() & !rdoA93.isChecked() & !rdoA94.isChecked() & secA9.isShown())
           {
             ValidationMsg += "\nA9. Required field: মনে হয়েছিল যেন আপনি এখন মারা গেলে কিংবা নিজেকে নিজে শেষ করে ফেলতে পারলে বেশ ভালো হতো?.";
             secA9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtAScore.getText().toString().length()==0 & secAScore.isShown())
           {
             ValidationMsg += "\nTotal Score Required field: Score.";
             secAScore.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secAScore.isShown() & (Integer.valueOf(txtAScore.getText().toString().length()==0 ? "00" : txtAScore.getText().toString()) < 00 || Integer.valueOf(txtAScore.getText().toString().length()==0 ? "27" : txtAScore.getText().toString()) > 27))
           {
             ValidationMsg += "\nTotal Score Value should be between 00 and 27(Score).";
             secAScore.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secA1.setBackgroundColor(Color.WHITE);
             secA2.setBackgroundColor(Color.WHITE);
             secA3.setBackgroundColor(Color.WHITE);
             secA4.setBackgroundColor(Color.WHITE);
             secA5.setBackgroundColor(Color.WHITE);
             secA6.setBackgroundColor(Color.WHITE);
             secA7.setBackgroundColor(Color.WHITE);
             secA8.setBackgroundColor(Color.WHITE);
             secA9.setBackgroundColor(Color.WHITE);
             secAScore.setBackgroundColor(Color.WHITE);
             secAScore.setBackgroundColor(Color.WHITE);
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
           SectionA_DataModel d = new SectionA_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionA_DataModel> data = d.SelectAll(this, SQL);
           for(SectionA_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpA1 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA1.length; i++)
             {
                 if (String.valueOf(item.getA1()).equals(String.valueOf(d_rdogrpA1[i])))
                 {
                     rb = (RadioButton)rdogrpA1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA2 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA2.length; i++)
             {
                 if (String.valueOf(item.getA2()).equals(String.valueOf(d_rdogrpA2[i])))
                 {
                     rb = (RadioButton)rdogrpA2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA3 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA3.length; i++)
             {
                 if (String.valueOf(item.getA3()).equals(String.valueOf(d_rdogrpA3[i])))
                 {
                     rb = (RadioButton)rdogrpA3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA4 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA4.length; i++)
             {
                 if (String.valueOf(item.getA4()).equals(String.valueOf(d_rdogrpA4[i])))
                 {
                     rb = (RadioButton)rdogrpA4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA5 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA5.length; i++)
             {
                 if (String.valueOf(item.getA5()).equals(String.valueOf(d_rdogrpA5[i])))
                 {
                     rb = (RadioButton)rdogrpA5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA6 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA6.length; i++)
             {
                 if (String.valueOf(item.getA6()).equals(String.valueOf(d_rdogrpA6[i])))
                 {
                     rb = (RadioButton)rdogrpA6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA7 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA7.length; i++)
             {
                 if (String.valueOf(item.getA7()).equals(String.valueOf(d_rdogrpA7[i])))
                 {
                     rb = (RadioButton)rdogrpA7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
               String[] d_rdogrpA8 = new String[] {"0","1","2","3"};
               for (int i = 0; i < d_rdogrpA8.length; i++)
               {
                   if (String.valueOf(item.getA8()).equals(String.valueOf(d_rdogrpA8[i])))
                   {
                       rb = (RadioButton)rdogrpA8.getChildAt(i);
                       rb.setChecked(true);
                   }
               }
             String[] d_rdogrpA9 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpA9.length; i++)
             {
                 if (String.valueOf(item.getA9()).equals(String.valueOf(d_rdogrpA9[i])))
                 {
                     rb = (RadioButton)rdogrpA9.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtAScore.setText(String.valueOf(item.getAScore()));
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionA.this, e.getMessage());
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