
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
// import android.text.Editable;
// import android.text.TextWatcher;
 import org.icddrb.mental_health_survey.R;

 import forms_datamodel.SectionD_DataModel;

 public class SectionD extends AppCompatActivity {
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
    LinearLayout seclblD1a;
    View linelblD1a;
    LinearLayout seclblD1b;
    View linelblD1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secD1;
    View lineD1;
    TextView VlblD1;
    EditText txtD1;
    LinearLayout secD2;
    View lineD2;
    TextView VlblD2;
    RadioGroup rdogrpD2;
    RadioButton rdoD21;
    RadioButton rdoD22;
    RadioButton rdoD23;
    RadioButton rdoD24;
    RadioButton rdoD25;
    LinearLayout secD2Oth;
    View lineD2Oth;
    TextView VlblD2Oth;
    AutoCompleteTextView txtD2Oth;
    LinearLayout secD3;
    View lineD3;
    TextView VlblD3;
    RadioGroup rdogrpD3;
    RadioButton rdoD31;
    RadioButton rdoD32;
    RadioButton rdoD33;
    RadioButton rdoD34;
    LinearLayout secD3Oth;
    View lineD3Oth;
    TextView VlblD3Oth;
    AutoCompleteTextView txtD3Oth;
    LinearLayout secD4;
    View lineD4;
    TextView VlblD4;
    RadioGroup rdogrpD4;
    RadioButton rdoD41;
    RadioButton rdoD42;
    RadioButton rdoD43;
    RadioButton rdoD44;
    RadioButton rdoD45;
    RadioButton rdoD46;
    LinearLayout secD4Oth;
    View lineD4Oth;
    TextView VlblD4Oth;
    AutoCompleteTextView txtD4Oth;
    LinearLayout secD5;
    View lineD5;
    TextView VlblD5;
    EditText txtD5;
    LinearLayout secD6;
    View lineD6;
    TextView VlblD6;
    EditText txtD6;

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
         setContentView(R.layout.sectiond);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionD";
         MODULEID = 4;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionD.this);
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
        Connection.LocalizeLanguage(SectionD.this, MODULEID, LANGUAGEID);





         //Hide all skip variables
         secD2Oth.setVisibility(View.GONE);
         lineD2Oth.setVisibility(View.GONE);
         secD3Oth.setVisibility(View.GONE);
         lineD3Oth.setVisibility(View.GONE);
         secD4Oth.setVisibility(View.GONE);
         lineD4Oth.setVisibility(View.GONE);


        DataSearch(PATIENTID,FACILITYID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionD.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblD1a=(LinearLayout)findViewById(R.id.seclblD1a);
         linelblD1a=(View)findViewById(R.id.linelblD1a);
         seclblD1b=(LinearLayout)findViewById(R.id.seclblD1b);
         linelblD1b=(View)findViewById(R.id.linelblD1b);
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
         secD1=(LinearLayout)findViewById(R.id.secD1);
         lineD1=(View)findViewById(R.id.lineD1);
         VlblD1=(TextView) findViewById(R.id.VlblD1);
         txtD1=(EditText) findViewById(R.id.txtD1);
         secD2=(LinearLayout)findViewById(R.id.secD2);
         lineD2=(View)findViewById(R.id.lineD2);
         VlblD2 = (TextView) findViewById(R.id.VlblD2);
         rdogrpD2 = (RadioGroup) findViewById(R.id.rdogrpD2);
         rdoD21 = (RadioButton) findViewById(R.id.rdoD21);
         rdoD22 = (RadioButton) findViewById(R.id.rdoD22);
         rdoD23 = (RadioButton) findViewById(R.id.rdoD23);
         rdoD24 = (RadioButton) findViewById(R.id.rdoD24);
         rdoD25 = (RadioButton) findViewById(R.id.rdoD25);
         rdogrpD2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpD2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < rdogrpD2.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpD2.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpD2[i];
             }

             if(rbData.equalsIgnoreCase("5"))
             {
                 secD2Oth.setVisibility(View.VISIBLE);
                 lineD2Oth.setVisibility(View.VISIBLE);
             }
             else
             {
                 secD2Oth.setVisibility(View.GONE);
                 lineD2Oth.setVisibility(View.GONE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secD2Oth=(LinearLayout)findViewById(R.id.secD2Oth);
         lineD2Oth=(View)findViewById(R.id.lineD2Oth);
         VlblD2Oth=(TextView) findViewById(R.id.VlblD2Oth);
         txtD2Oth=(AutoCompleteTextView) findViewById(R.id.txtD2Oth);
         txtD2Oth.setAdapter(C.getArrayAdapter("Select distinct D2Oth from "+ TableName +" order by D2Oth"));

         txtD2Oth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

             }
         });
         txtD2Oth.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT = 2;
         
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (txtD2Oth.getRight() - txtD2Oth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         ((EditText)v).setText("");
                         return true;
                     }
                 }
                 return false;
             }
         });
         secD3=(LinearLayout)findViewById(R.id.secD3);
         lineD3=(View)findViewById(R.id.lineD3);
         VlblD3 = (TextView) findViewById(R.id.VlblD3);
         rdogrpD3 = (RadioGroup) findViewById(R.id.rdogrpD3);
         rdoD31 = (RadioButton) findViewById(R.id.rdoD31);
         rdoD32 = (RadioButton) findViewById(R.id.rdoD32);
         rdoD33 = (RadioButton) findViewById(R.id.rdoD33);
         rdoD34 = (RadioButton) findViewById(R.id.rdoD34);
         rdogrpD3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpD3 = new String[] {"1","2","3","4"};
             for (int i = 0; i < rdogrpD3.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpD3.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpD3[i];
             }

             if(rbData.equalsIgnoreCase("3"))
             {
                 secD3Oth.setVisibility(View.VISIBLE);
                 lineD3Oth.setVisibility(View.VISIBLE);
             }
             else
             {
                 secD3Oth.setVisibility(View.GONE);
                 lineD3Oth.setVisibility(View.GONE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secD3Oth=(LinearLayout)findViewById(R.id.secD3Oth);
         lineD3Oth=(View)findViewById(R.id.lineD3Oth);
         VlblD3Oth=(TextView) findViewById(R.id.VlblD3Oth);
         txtD3Oth=(AutoCompleteTextView) findViewById(R.id.txtD3Oth);
         txtD3Oth.setAdapter(C.getArrayAdapter("Select distinct D3Oth from "+ TableName +" order by D3Oth"));

         txtD3Oth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

             }
         });
         txtD3Oth.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT = 2;
         
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (txtD3Oth.getRight() - txtD3Oth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         ((EditText)v).setText("");
                         return true;
                     }
                 }
                 return false;
             }
         });
         secD4=(LinearLayout)findViewById(R.id.secD4);
         lineD4=(View)findViewById(R.id.lineD4);
         VlblD4 = (TextView) findViewById(R.id.VlblD4);
         rdogrpD4 = (RadioGroup) findViewById(R.id.rdogrpD4);
         rdoD41 = (RadioButton) findViewById(R.id.rdoD41);
         rdoD42 = (RadioButton) findViewById(R.id.rdoD42);
         rdoD43 = (RadioButton) findViewById(R.id.rdoD43);
         rdoD44 = (RadioButton) findViewById(R.id.rdoD44);
         rdoD45 = (RadioButton) findViewById(R.id.rdoD45);
         rdoD46 = (RadioButton) findViewById(R.id.rdoD46);
         rdogrpD4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpD4 = new String[] {"1","2","3","4","5","6"};
             for (int i = 0; i < rdogrpD4.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpD4.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpD4[i];
             }

             if(rbData.equalsIgnoreCase("6"))
             {
                 secD4Oth.setVisibility(View.VISIBLE);
                 lineD4Oth.setVisibility(View.VISIBLE);
             }
             else
             {
                 secD4Oth.setVisibility(View.GONE);
                 lineD4Oth.setVisibility(View.GONE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secD4Oth=(LinearLayout)findViewById(R.id.secD4Oth);
         lineD4Oth=(View)findViewById(R.id.lineD4Oth);
         VlblD4Oth=(TextView) findViewById(R.id.VlblD4Oth);
         txtD4Oth=(AutoCompleteTextView) findViewById(R.id.txtD4Oth);
         txtD4Oth.setAdapter(C.getArrayAdapter("Select distinct D4Oth from "+ TableName +" order by D4Oth"));

         txtD4Oth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

             }
         });
         txtD4Oth.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT = 2;
         
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (txtD4Oth.getRight() - txtD4Oth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         ((EditText)v).setText("");
                         return true;
                     }
                 }
                 return false;
             }
         });
         secD5=(LinearLayout)findViewById(R.id.secD5);
         lineD5=(View)findViewById(R.id.lineD5);
         VlblD5=(TextView) findViewById(R.id.VlblD5);
         txtD5=(EditText) findViewById(R.id.txtD5);
         secD6=(LinearLayout)findViewById(R.id.secD6);
         lineD6=(View)findViewById(R.id.lineD6);
         VlblD6=(TextView) findViewById(R.id.VlblD6);
         txtD6=(EditText) findViewById(R.id.txtD6);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionD.this, e.getMessage());
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
         	Connection.MessageBox(SectionD.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionD_DataModel objSave = new SectionD_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setD1(txtD1.getText().toString());
         String[] d_rdogrpD2 = new String[] {"1","2","3","4","5"};
         objSave.setD2("");
         for (int i = 0; i < rdogrpD2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpD2.getChildAt(i);
             if (rb.isChecked()) objSave.setD2(d_rdogrpD2[i]);
         }

         objSave.setD2Oth(txtD2Oth.getText().toString());
         String[] d_rdogrpD3 = new String[] {"1","2","3","4"};
         objSave.setD3("");
         for (int i = 0; i < rdogrpD3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpD3.getChildAt(i);
             if (rb.isChecked()) objSave.setD3(d_rdogrpD3[i]);
         }

         objSave.setD3Oth(txtD3Oth.getText().toString());
         String[] d_rdogrpD4 = new String[] {"1","2","3","4","5","6"};
         objSave.setD4("");
         for (int i = 0; i < rdogrpD4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpD4.getChildAt(i);
             if (rb.isChecked()) objSave.setD4(d_rdogrpD4[i]);
         }

         objSave.setD4Oth(txtD4Oth.getText().toString());
         objSave.setD5(txtD5.getText().toString());
         objSave.setD6(txtD6.getText().toString());
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
             Connection.MessageBox(SectionD.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionE.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);
         }
         else{
             Connection.MessageBox(SectionD.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionD.this, e.getMessage());
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
         if(txtD1.getText().toString().length()==0 & secD1.isShown())
           {
             ValidationMsg += "\nD1. Required field: বয়স  (বছরে).";
             secD1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secD1.isShown() & (Integer.valueOf(txtD1.getText().toString().length()==0 ? "13" : txtD1.getText().toString()) < 13 || Integer.valueOf(txtD1.getText().toString().length()==0 ? "99" : txtD1.getText().toString()) > 99))
           {
             ValidationMsg += "\nD1. Value should be between 13 and 99(বয়স  (বছরে)).";
             secD1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoD21.isChecked() & !rdoD22.isChecked() & !rdoD23.isChecked() & !rdoD24.isChecked() & !rdoD25.isChecked() & secD2.isShown())
           {
             ValidationMsg += "\nD2. Required field: ধর্ম.";
             secD2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtD2Oth.getText().toString().length()==0 & secD2Oth.isShown())
           {
             ValidationMsg += "\nRequired field: অন্যান্য (নির্দিষ্ট করুন).";
             secD2Oth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoD31.isChecked() & !rdoD32.isChecked() & !rdoD33.isChecked() & !rdoD34.isChecked() & secD3.isShown())
           {
             ValidationMsg += "\nD3. Required field: বর্তমান বৈবাহিক অবস্থা.";
             secD3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtD3Oth.getText().toString().length()==0 & secD3Oth.isShown())
           {
             ValidationMsg += "\nRequired field: অন্যান্য (নির্দিষ্ট করুন).";
             secD3Oth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoD41.isChecked() & !rdoD42.isChecked() & !rdoD43.isChecked() & !rdoD44.isChecked() & !rdoD45.isChecked() & !rdoD46.isChecked() & secD4.isShown())
           {
             ValidationMsg += "\nD4. Required field: পেশা.";
             secD4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtD4Oth.getText().toString().length()==0 & secD4Oth.isShown())
           {
             ValidationMsg += "\nRequired field: অন্যান্য (নির্দিষ্ট করুন).";
             secD4Oth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtD5.getText().toString().length()==0 & secD5.isShown())
           {
             ValidationMsg += "\nD5. Required field: মোট যত বছর শিক্ষা সম্পন্ন করেছেন (বছরে).";
             secD5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secD5.isShown() & (Integer.valueOf(txtD5.getText().toString().length()==0 ? "00" : txtD5.getText().toString()) < 00 || Integer.valueOf(txtD5.getText().toString().length()==0 ? "24" : txtD5.getText().toString()) > 24))
           {
             ValidationMsg += "\nD5. Value should be between 00 and 24(মোট যত বছর শিক্ষা সম্পন্ন করেছেন (বছরে)).";
             secD5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtD6.getText().toString().length()==0 & secD6.isShown())
           {
             ValidationMsg += "\nD6. Required field: পরিবারের মোট মাসিক আয় (টাকা).";
             secD6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secD6.isShown() & (Integer.valueOf(txtD6.getText().toString().length()==0 ? "00" : txtD6.getText().toString()) < 00 || Integer.valueOf(txtD6.getText().toString().length()==0 ? "999999" : txtD6.getText().toString()) > 999999))
           {
             ValidationMsg += "\nD6. Value should be between 00 and 999999(পরিবারের মোট মাসিক আয় (টাকা)).";
             secD6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secD1.setBackgroundColor(Color.WHITE);
             secD1.setBackgroundColor(Color.WHITE);
             secD2.setBackgroundColor(Color.WHITE);
             secD2Oth.setBackgroundColor(Color.WHITE);
             secD3.setBackgroundColor(Color.WHITE);
             secD3Oth.setBackgroundColor(Color.WHITE);
             secD4.setBackgroundColor(Color.WHITE);
             secD4Oth.setBackgroundColor(Color.WHITE);
             secD5.setBackgroundColor(Color.WHITE);
             secD5.setBackgroundColor(Color.WHITE);
             secD6.setBackgroundColor(Color.WHITE);
             secD6.setBackgroundColor(Color.WHITE);
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
           SectionD_DataModel d = new SectionD_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionD_DataModel> data = d.SelectAll(this, SQL);
           for(SectionD_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             txtD1.setText(String.valueOf(item.getD1()));
             String[] d_rdogrpD2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpD2.length; i++)
             {
                 if (String.valueOf(item.getD2()).equals(String.valueOf(d_rdogrpD2[i])))
                 {
                     rb = (RadioButton)rdogrpD2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtD2Oth.setText(item.getD2Oth());
             String[] d_rdogrpD3 = new String[] {"1","2","3","4"};
             for (int i = 0; i < d_rdogrpD3.length; i++)
             {
                 if (String.valueOf(item.getD3()).equals(String.valueOf(d_rdogrpD3[i])))
                 {
                     rb = (RadioButton)rdogrpD3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtD3Oth.setText(item.getD3Oth());
             String[] d_rdogrpD4 = new String[] {"1","2","3","4","5","6"};
             for (int i = 0; i < d_rdogrpD4.length; i++)
             {
                 if (String.valueOf(item.getD4()).equals(String.valueOf(d_rdogrpD4[i])))
                 {
                     rb = (RadioButton)rdogrpD4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtD4Oth.setText(item.getD4Oth());
             txtD5.setText(String.valueOf(item.getD5()));
             txtD6.setText(String.valueOf(item.getD6()));
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionD.this, e.getMessage());
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