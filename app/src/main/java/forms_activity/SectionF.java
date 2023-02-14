
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

 import forms_datamodel.SectionF_DataModel;

 public class SectionF extends AppCompatActivity {
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
    LinearLayout seclblF1a;
    View linelblF1a;
    LinearLayout seclblF1b;
    View linelblF1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secF1;
    View lineF1;
    TextView VlblF1;
    RadioGroup rdogrpF1;
    RadioButton rdoF11;
    RadioButton rdoF12;
    RadioButton rdoF13;
    RadioButton rdoF14;
    RadioButton rdoF15;
    LinearLayout secF2;
    View lineF2;
    TextView VlblF2;
    RadioGroup rdogrpF2;
    RadioButton rdoF21;
    RadioButton rdoF22;
    RadioButton rdoF23;
    RadioButton rdoF24;
    RadioButton rdoF25;
    LinearLayout secF3;
    View lineF3;
    TextView VlblF3;
    RadioGroup rdogrpF3;
    RadioButton rdoF31;
    RadioButton rdoF32;
    RadioButton rdoF33;
    RadioButton rdoF34;
    RadioButton rdoF35;
    LinearLayout secF4;
    View lineF4;
    TextView VlblF4;
    RadioGroup rdogrpF4;
    RadioButton rdoF41;
    RadioButton rdoF42;
    RadioButton rdoF43;
    RadioButton rdoF44;
    RadioButton rdoF45;
    LinearLayout secF5;
    View lineF5;
    TextView VlblF5;
    RadioGroup rdogrpF5;
    RadioButton rdoF51;
    RadioButton rdoF52;
    RadioButton rdoF53;
    RadioButton rdoF54;
    RadioButton rdoF55;
    LinearLayout secF6;
    View lineF6;
    TextView VlblF6;
    RadioGroup rdogrpF6;
    RadioButton rdoF61;
    RadioButton rdoF62;
    RadioButton rdoF63;
    RadioButton rdoF64;
    RadioButton rdoF65;
    LinearLayout secF7;
    View lineF7;
    TextView VlblF7;
    RadioGroup rdogrpF7;
    RadioButton rdoF71;
    RadioButton rdoF72;
    RadioButton rdoF73;
    RadioButton rdoF74;
    RadioButton rdoF75;
    LinearLayout secF8;
    View lineF8;
    TextView VlblF8;
    RadioGroup rdogrpF8;
    RadioButton rdoF81;
    RadioButton rdoF82;
    RadioButton rdoF83;
    RadioButton rdoF84;
    RadioButton rdoF85;
    LinearLayout secF9;
    View lineF9;
    TextView VlblF9;
    RadioGroup rdogrpF9;
    RadioButton rdoF91;
    RadioButton rdoF92;
    RadioButton rdoF93;
    RadioButton rdoF94;
    RadioButton rdoF95;
    LinearLayout secF10;
    View lineF10;
    TextView VlblF10;
    RadioGroup rdogrpF10;
    RadioButton rdoF101;
    RadioButton rdoF102;
    RadioButton rdoF103;
    RadioButton rdoF104;
    RadioButton rdoF105;
    LinearLayout secF11;
    View lineF11;
    TextView VlblF11;
    RadioGroup rdogrpF11;
    RadioButton rdoF111;
    RadioButton rdoF112;
    RadioButton rdoF113;
    RadioButton rdoF114;
    RadioButton rdoF115;
    LinearLayout secF12;
    View lineF12;
    TextView VlblF12;
    RadioGroup rdogrpF12;
    RadioButton rdoF121;
    RadioButton rdoF122;
    RadioButton rdoF123;
    RadioButton rdoF124;
    RadioButton rdoF125;
    LinearLayout secF13;
    View lineF13;
    TextView VlblF13;
    RadioGroup rdogrpF13;
    RadioButton rdoF131;
    RadioButton rdoF132;
    RadioButton rdoF133;
    RadioButton rdoF134;
    RadioButton rdoF135;

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
         setContentView(R.layout.sectionf);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionF";
         MODULEID = 6;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionF.this);
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
        Connection.LocalizeLanguage(SectionF.this, MODULEID, LANGUAGEID);





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
         Connection.MessageBox(SectionF.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblF1a=(LinearLayout)findViewById(R.id.seclblF1a);
         linelblF1a=(View)findViewById(R.id.linelblF1a);
         seclblF1b=(LinearLayout)findViewById(R.id.seclblF1b);
         linelblF1b=(View)findViewById(R.id.linelblF1b);
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
         secF1=(LinearLayout)findViewById(R.id.secF1);
         lineF1=(View)findViewById(R.id.lineF1);
         VlblF1 = (TextView) findViewById(R.id.VlblF1);
         rdogrpF1 = (RadioGroup) findViewById(R.id.rdogrpF1);
         rdoF11 = (RadioButton) findViewById(R.id.rdoF11);
         rdoF12 = (RadioButton) findViewById(R.id.rdoF12);
         rdoF13 = (RadioButton) findViewById(R.id.rdoF13);
         rdoF14 = (RadioButton) findViewById(R.id.rdoF14);
         rdoF15 = (RadioButton) findViewById(R.id.rdoF15);
         secF2=(LinearLayout)findViewById(R.id.secF2);
         lineF2=(View)findViewById(R.id.lineF2);
         VlblF2 = (TextView) findViewById(R.id.VlblF2);
         rdogrpF2 = (RadioGroup) findViewById(R.id.rdogrpF2);
         rdoF21 = (RadioButton) findViewById(R.id.rdoF21);
         rdoF22 = (RadioButton) findViewById(R.id.rdoF22);
         rdoF23 = (RadioButton) findViewById(R.id.rdoF23);
         rdoF24 = (RadioButton) findViewById(R.id.rdoF24);
         rdoF25 = (RadioButton) findViewById(R.id.rdoF25);
         secF3=(LinearLayout)findViewById(R.id.secF3);
         lineF3=(View)findViewById(R.id.lineF3);
         VlblF3 = (TextView) findViewById(R.id.VlblF3);
         rdogrpF3 = (RadioGroup) findViewById(R.id.rdogrpF3);
         rdoF31 = (RadioButton) findViewById(R.id.rdoF31);
         rdoF32 = (RadioButton) findViewById(R.id.rdoF32);
         rdoF33 = (RadioButton) findViewById(R.id.rdoF33);
         rdoF34 = (RadioButton) findViewById(R.id.rdoF34);
         rdoF35 = (RadioButton) findViewById(R.id.rdoF35);
         secF4=(LinearLayout)findViewById(R.id.secF4);
         lineF4=(View)findViewById(R.id.lineF4);
         VlblF4 = (TextView) findViewById(R.id.VlblF4);
         rdogrpF4 = (RadioGroup) findViewById(R.id.rdogrpF4);
         rdoF41 = (RadioButton) findViewById(R.id.rdoF41);
         rdoF42 = (RadioButton) findViewById(R.id.rdoF42);
         rdoF43 = (RadioButton) findViewById(R.id.rdoF43);
         rdoF44 = (RadioButton) findViewById(R.id.rdoF44);
         rdoF45 = (RadioButton) findViewById(R.id.rdoF45);
         secF5=(LinearLayout)findViewById(R.id.secF5);
         lineF5=(View)findViewById(R.id.lineF5);
         VlblF5 = (TextView) findViewById(R.id.VlblF5);
         rdogrpF5 = (RadioGroup) findViewById(R.id.rdogrpF5);
         rdoF51 = (RadioButton) findViewById(R.id.rdoF51);
         rdoF52 = (RadioButton) findViewById(R.id.rdoF52);
         rdoF53 = (RadioButton) findViewById(R.id.rdoF53);
         rdoF54 = (RadioButton) findViewById(R.id.rdoF54);
         rdoF55 = (RadioButton) findViewById(R.id.rdoF55);
         secF6=(LinearLayout)findViewById(R.id.secF6);
         lineF6=(View)findViewById(R.id.lineF6);
         VlblF6 = (TextView) findViewById(R.id.VlblF6);
         rdogrpF6 = (RadioGroup) findViewById(R.id.rdogrpF6);
         rdoF61 = (RadioButton) findViewById(R.id.rdoF61);
         rdoF62 = (RadioButton) findViewById(R.id.rdoF62);
         rdoF63 = (RadioButton) findViewById(R.id.rdoF63);
         rdoF64 = (RadioButton) findViewById(R.id.rdoF64);
         rdoF65 = (RadioButton) findViewById(R.id.rdoF65);
         secF7=(LinearLayout)findViewById(R.id.secF7);
         lineF7=(View)findViewById(R.id.lineF7);
         VlblF7 = (TextView) findViewById(R.id.VlblF7);
         rdogrpF7 = (RadioGroup) findViewById(R.id.rdogrpF7);
         rdoF71 = (RadioButton) findViewById(R.id.rdoF71);
         rdoF72 = (RadioButton) findViewById(R.id.rdoF72);
         rdoF73 = (RadioButton) findViewById(R.id.rdoF73);
         rdoF74 = (RadioButton) findViewById(R.id.rdoF74);
         rdoF75 = (RadioButton) findViewById(R.id.rdoF75);
         secF8=(LinearLayout)findViewById(R.id.secF8);
         lineF8=(View)findViewById(R.id.lineF8);
         VlblF8 = (TextView) findViewById(R.id.VlblF8);
         rdogrpF8 = (RadioGroup) findViewById(R.id.rdogrpF8);
         rdoF81 = (RadioButton) findViewById(R.id.rdoF81);
         rdoF82 = (RadioButton) findViewById(R.id.rdoF82);
         rdoF83 = (RadioButton) findViewById(R.id.rdoF83);
         rdoF84 = (RadioButton) findViewById(R.id.rdoF84);
         rdoF85 = (RadioButton) findViewById(R.id.rdoF85);
         secF9=(LinearLayout)findViewById(R.id.secF9);
         lineF9=(View)findViewById(R.id.lineF9);
         VlblF9 = (TextView) findViewById(R.id.VlblF9);
         rdogrpF9 = (RadioGroup) findViewById(R.id.rdogrpF9);
         rdoF91 = (RadioButton) findViewById(R.id.rdoF91);
         rdoF92 = (RadioButton) findViewById(R.id.rdoF92);
         rdoF93 = (RadioButton) findViewById(R.id.rdoF93);
         rdoF94 = (RadioButton) findViewById(R.id.rdoF94);
         rdoF95 = (RadioButton) findViewById(R.id.rdoF95);
         secF10=(LinearLayout)findViewById(R.id.secF10);
         lineF10=(View)findViewById(R.id.lineF10);
         VlblF10 = (TextView) findViewById(R.id.VlblF10);
         rdogrpF10 = (RadioGroup) findViewById(R.id.rdogrpF10);
         rdoF101 = (RadioButton) findViewById(R.id.rdoF101);
         rdoF102 = (RadioButton) findViewById(R.id.rdoF102);
         rdoF103 = (RadioButton) findViewById(R.id.rdoF103);
         rdoF104 = (RadioButton) findViewById(R.id.rdoF104);
         rdoF105 = (RadioButton) findViewById(R.id.rdoF105);
         secF11=(LinearLayout)findViewById(R.id.secF11);
         lineF11=(View)findViewById(R.id.lineF11);
         VlblF11 = (TextView) findViewById(R.id.VlblF11);
         rdogrpF11 = (RadioGroup) findViewById(R.id.rdogrpF11);
         rdoF111 = (RadioButton) findViewById(R.id.rdoF111);
         rdoF112 = (RadioButton) findViewById(R.id.rdoF112);
         rdoF113 = (RadioButton) findViewById(R.id.rdoF113);
         rdoF114 = (RadioButton) findViewById(R.id.rdoF114);
         rdoF115 = (RadioButton) findViewById(R.id.rdoF115);
         secF12=(LinearLayout)findViewById(R.id.secF12);
         lineF12=(View)findViewById(R.id.lineF12);
         VlblF12 = (TextView) findViewById(R.id.VlblF12);
         rdogrpF12 = (RadioGroup) findViewById(R.id.rdogrpF12);
         rdoF121 = (RadioButton) findViewById(R.id.rdoF121);
         rdoF122 = (RadioButton) findViewById(R.id.rdoF122);
         rdoF123 = (RadioButton) findViewById(R.id.rdoF123);
         rdoF124 = (RadioButton) findViewById(R.id.rdoF124);
         rdoF125 = (RadioButton) findViewById(R.id.rdoF125);
         secF13=(LinearLayout)findViewById(R.id.secF13);
         lineF13=(View)findViewById(R.id.lineF13);
         VlblF13 = (TextView) findViewById(R.id.VlblF13);
         rdogrpF13 = (RadioGroup) findViewById(R.id.rdogrpF13);
         rdoF131 = (RadioButton) findViewById(R.id.rdoF131);
         rdoF132 = (RadioButton) findViewById(R.id.rdoF132);
         rdoF133 = (RadioButton) findViewById(R.id.rdoF133);
         rdoF134 = (RadioButton) findViewById(R.id.rdoF134);
         rdoF135 = (RadioButton) findViewById(R.id.rdoF135);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionF.this, e.getMessage());
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
         	Connection.MessageBox(SectionF.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionF_DataModel objSave = new SectionF_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpF1 = new String[] {"1","2","3","4","5"};
         objSave.setF1("");
         for (int i = 0; i < rdogrpF1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF1.getChildAt(i);
             if (rb.isChecked()) objSave.setF1(d_rdogrpF1[i]);
         }

         String[] d_rdogrpF2 = new String[] {"1","2","3","4","5"};
         objSave.setF2("");
         for (int i = 0; i < rdogrpF2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF2.getChildAt(i);
             if (rb.isChecked()) objSave.setF2(d_rdogrpF2[i]);
         }

         String[] d_rdogrpF3 = new String[] {"1","2","3","4","5"};
         objSave.setF3("");
         for (int i = 0; i < rdogrpF3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF3.getChildAt(i);
             if (rb.isChecked()) objSave.setF3(d_rdogrpF3[i]);
         }

         String[] d_rdogrpF4 = new String[] {"1","2","3","4","5"};
         objSave.setF4("");
         for (int i = 0; i < rdogrpF4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF4.getChildAt(i);
             if (rb.isChecked()) objSave.setF4(d_rdogrpF4[i]);
         }

         String[] d_rdogrpF5 = new String[] {"1","2","3","4","5"};
         objSave.setF5("");
         for (int i = 0; i < rdogrpF5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF5.getChildAt(i);
             if (rb.isChecked()) objSave.setF5(d_rdogrpF5[i]);
         }

         String[] d_rdogrpF6 = new String[] {"1","2","3","4","5"};
         objSave.setF6("");
         for (int i = 0; i < rdogrpF6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF6.getChildAt(i);
             if (rb.isChecked()) objSave.setF6(d_rdogrpF6[i]);
         }

         String[] d_rdogrpF7 = new String[] {"1","2","3","4","5"};
         objSave.setF7("");
         for (int i = 0; i < rdogrpF7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF7.getChildAt(i);
             if (rb.isChecked()) objSave.setF7(d_rdogrpF7[i]);
         }

         String[] d_rdogrpF8 = new String[] {"1","2","3","4","5"};
         objSave.setF8("");
         for (int i = 0; i < rdogrpF8.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF8.getChildAt(i);
             if (rb.isChecked()) objSave.setF8(d_rdogrpF8[i]);
         }

         String[] d_rdogrpF9 = new String[] {"1","2","3","4","5"};
         objSave.setF9("");
         for (int i = 0; i < rdogrpF9.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF9.getChildAt(i);
             if (rb.isChecked()) objSave.setF9(d_rdogrpF9[i]);
         }

         String[] d_rdogrpF10 = new String[] {"1","2","3","4","5"};
         objSave.setF10("");
         for (int i = 0; i < rdogrpF10.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF10.getChildAt(i);
             if (rb.isChecked()) objSave.setF10(d_rdogrpF10[i]);
         }

         String[] d_rdogrpF11 = new String[] {"1","2","3","4","5"};
         objSave.setF11("");
         for (int i = 0; i < rdogrpF11.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF11.getChildAt(i);
             if (rb.isChecked()) objSave.setF11(d_rdogrpF11[i]);
         }

         String[] d_rdogrpF12 = new String[] {"1","2","3","4","5"};
         objSave.setF12("");
         for (int i = 0; i < rdogrpF12.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF12.getChildAt(i);
             if (rb.isChecked()) objSave.setF12(d_rdogrpF12[i]);
         }

         String[] d_rdogrpF13 = new String[] {"1","2","3","4","5"};
         objSave.setF13("");
         for (int i = 0; i < rdogrpF13.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpF13.getChildAt(i);
             if (rb.isChecked()) objSave.setF13(d_rdogrpF13[i]);
         }

         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         objSave.setLat(MySharedPreferences.getValue(this, "lat"));
         objSave.setLon(MySharedPreferences.getValue(this, "lon"));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
/*             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);
             Connection.MessageBox(SectionF.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionG.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);*/

             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);
             Connection.MessageBox(SectionF.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(SectionF.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionF.this, e.getMessage());
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
         if(!rdoF11.isChecked() & !rdoF12.isChecked() & !rdoF13.isChecked() & !rdoF14.isChecked() & !rdoF15.isChecked() & secF1.isShown())
           {
             ValidationMsg += "\nF1. Required field: মন-স্বাস্থ্য কেন্দ্রে কাউন্সিলিং সেবা নেয়ার জন্য ডিজিটাল সিস্টেম ব্যাবহার করা সহজ ছিলো.";
             secF1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF21.isChecked() & !rdoF22.isChecked() & !rdoF23.isChecked() & !rdoF24.isChecked() & !rdoF25.isChecked() & secF2.isShown())
           {
             ValidationMsg += "\nF2. Required field: আমি যখন টেকনিক্যাল/প্রযুক্তিগত সমস্যা বোধ করেছি তখন একজন আমাকে সমস্যার সমাধান করতে সহযোগীতা করেছে.";
             secF2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF31.isChecked() & !rdoF32.isChecked() & !rdoF33.isChecked() & !rdoF34.isChecked() & !rdoF35.isChecked() & secF3.isShown())
           {
             ValidationMsg += "\nF3. Required field: মন-স্বাস্থ্য কেন্দ্রে ডিজিটাল সিস্টেম ব্যাবহার করে কিভাবে মনস্বাস্থ্য সেবা নেয়া যায় তা শেখা সহজ ছিলো.";
             secF3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF41.isChecked() & !rdoF42.isChecked() & !rdoF43.isChecked() & !rdoF44.isChecked() & !rdoF45.isChecked() & secF4.isShown())
           {
             ValidationMsg += "\nF4. Required field: মন-স্বাস্থ্য কেন্দ্রে ডিজিটাল সিস্টেম ব্যবহার করে আমি সহজেই কাউন্সিলরের সাথে কথা বলতে পেরেছি.";
             secF4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF51.isChecked() & !rdoF52.isChecked() & !rdoF53.isChecked() & !rdoF54.isChecked() & !rdoF55.isChecked() & secF5.isShown())
           {
             ValidationMsg += "\nF5. Required field: মন-স্বাস্থ্য কেন্দ্রে আমি কাউন্সিলরের কথা ভালভাবে শুনতে পাচ্ছিলাম.";
             secF5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF61.isChecked() & !rdoF62.isChecked() & !rdoF63.isChecked() & !rdoF64.isChecked() & !rdoF65.isChecked() & secF6.isShown())
           {
             ValidationMsg += "\nF6. Required field: আমি মনে করি, এই সেবার মাধ্যমে আমার সমস্যাটি কাউন্সিলরের কাছে ভালভাবে প্রকাশ করতে পেরেছি.";
             secF6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF71.isChecked() & !rdoF72.isChecked() & !rdoF73.isChecked() & !rdoF74.isChecked() & !rdoF75.isChecked() & secF7.isShown())
           {
             ValidationMsg += "\nF7. Required field: আমি মনে করি, কাউন্সিলর আমার কথাগুলি মনোযোগ দিয়ে শুনেছেন.";
             secF7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF81.isChecked() & !rdoF82.isChecked() & !rdoF83.isChecked() & !rdoF84.isChecked() & !rdoF85.isChecked() & secF8.isShown())
           {
             ValidationMsg += "\nF8. Required field: আমার কাছে মন-স্বাস্থ্য কেন্দ্রের মনস্বাস্থ্য সেবাটি গ্রহনযোগ্য.";
             secF8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF91.isChecked() & !rdoF92.isChecked() & !rdoF93.isChecked() & !rdoF94.isChecked() & !rdoF95.isChecked() & secF9.isShown())
           {
             ValidationMsg += "\nF9. Required field: আমি মন-স্বাস্থ্য কেন্দ্রে ডিজিটাল সিস্টেম ব্যবহার করে কাউন্সিলরের সাথে কথা বলতে স্বাচ্ছন্দ্য বোধ করেছি.";
             secF9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF101.isChecked() & !rdoF102.isChecked() & !rdoF103.isChecked() & !rdoF104.isChecked() & !rdoF105.isChecked() & secF10.isShown())
           {
             ValidationMsg += "\nF10. Required field: আমি মন-স্বাস্থ্য কেন্দ্রে গোপনীয়তা রক্ষার প্রক্রিয়াটিতে স্বাচ্ছন্দ্য বোধ করেছি.";
             secF10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF111.isChecked() & !rdoF112.isChecked() & !rdoF113.isChecked() & !rdoF114.isChecked() & !rdoF115.isChecked() & secF11.isShown())
           {
             ValidationMsg += "\nF11. Required field: মন-স্বাস্থ্য কেন্দ্রে ডিজিটাল সিস্টেম ব্যবহার করে কাউন্সিলিং সেবা নিয়ে আরো ভাল বোধ করছি.";
             secF11.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF121.isChecked() & !rdoF122.isChecked() & !rdoF123.isChecked() & !rdoF124.isChecked() & !rdoF125.isChecked() & secF12.isShown())
           {
             ValidationMsg += "\nF12. Required field: আমি মন-স্বাস্থ্য কেন্দ্রে ডিজিটাল সিস্টেম ব্যবহার করে কাউন্সিলিং সেবা  নিতে পছন্দ করি.";
             secF12.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoF131.isChecked() & !rdoF132.isChecked() & !rdoF133.isChecked() & !rdoF134.isChecked() & !rdoF135.isChecked() & secF13.isShown())
           {
             ValidationMsg += "\nF13. Required field: সামগ্রিকভাবে, আমি মন-স্বাস্থ্য কেন্দ্র মনস্বাস্থ্য সেবা ব্যবহার করে সুন্তুষ্ট.";
             secF13.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secF1.setBackgroundColor(Color.WHITE);
             secF2.setBackgroundColor(Color.WHITE);
             secF3.setBackgroundColor(Color.WHITE);
             secF4.setBackgroundColor(Color.WHITE);
             secF5.setBackgroundColor(Color.WHITE);
             secF6.setBackgroundColor(Color.WHITE);
             secF7.setBackgroundColor(Color.WHITE);
             secF8.setBackgroundColor(Color.WHITE);
             secF9.setBackgroundColor(Color.WHITE);
             secF10.setBackgroundColor(Color.WHITE);
             secF11.setBackgroundColor(Color.WHITE);
             secF12.setBackgroundColor(Color.WHITE);
             secF13.setBackgroundColor(Color.WHITE);
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
           SectionF_DataModel d = new SectionF_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionF_DataModel> data = d.SelectAll(this, SQL);
           for(SectionF_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpF1 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF1.length; i++)
             {
                 if (String.valueOf(item.getF1()).equals(String.valueOf(d_rdogrpF1[i])))
                 {
                     rb = (RadioButton)rdogrpF1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF2.length; i++)
             {
                 if (String.valueOf(item.getF2()).equals(String.valueOf(d_rdogrpF2[i])))
                 {
                     rb = (RadioButton)rdogrpF2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF3 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF3.length; i++)
             {
                 if (String.valueOf(item.getF3()).equals(String.valueOf(d_rdogrpF3[i])))
                 {
                     rb = (RadioButton)rdogrpF3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF4 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF4.length; i++)
             {
                 if (String.valueOf(item.getF4()).equals(String.valueOf(d_rdogrpF4[i])))
                 {
                     rb = (RadioButton)rdogrpF4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF5 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF5.length; i++)
             {
                 if (String.valueOf(item.getF5()).equals(String.valueOf(d_rdogrpF5[i])))
                 {
                     rb = (RadioButton)rdogrpF5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF6 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF6.length; i++)
             {
                 if (String.valueOf(item.getF6()).equals(String.valueOf(d_rdogrpF6[i])))
                 {
                     rb = (RadioButton)rdogrpF6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF7 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF7.length; i++)
             {
                 if (String.valueOf(item.getF7()).equals(String.valueOf(d_rdogrpF7[i])))
                 {
                     rb = (RadioButton)rdogrpF7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF8 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF8.length; i++)
             {
                 if (String.valueOf(item.getF8()).equals(String.valueOf(d_rdogrpF8[i])))
                 {
                     rb = (RadioButton)rdogrpF8.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF9 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF9.length; i++)
             {
                 if (String.valueOf(item.getF9()).equals(String.valueOf(d_rdogrpF9[i])))
                 {
                     rb = (RadioButton)rdogrpF9.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF10 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF10.length; i++)
             {
                 if (String.valueOf(item.getF10()).equals(String.valueOf(d_rdogrpF10[i])))
                 {
                     rb = (RadioButton)rdogrpF10.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF11 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF11.length; i++)
             {
                 if (String.valueOf(item.getF11()).equals(String.valueOf(d_rdogrpF11[i])))
                 {
                     rb = (RadioButton)rdogrpF11.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF12 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF12.length; i++)
             {
                 if (String.valueOf(item.getF12()).equals(String.valueOf(d_rdogrpF12[i])))
                 {
                     rb = (RadioButton)rdogrpF12.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpF13 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpF13.length; i++)
             {
                 if (String.valueOf(item.getF13()).equals(String.valueOf(d_rdogrpF13[i])))
                 {
                     rb = (RadioButton)rdogrpF13.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionF.this, e.getMessage());
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