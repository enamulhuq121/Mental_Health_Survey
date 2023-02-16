
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
 import forms_datamodel.SectionC_DataModel;

 public class SectionC extends AppCompatActivity {
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
    LinearLayout seclblC1a;
    View linelblC1a;
    LinearLayout seclblC1b;
    View linelblC1b;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secPatientCat;
    View linePatientCat;
    TextView VlblPatientCat;
    RadioGroup rdogrpPatientCat;
    RadioButton rdoPatientCat1;
    RadioButton rdoPatientCat2;
    LinearLayout secC1;
    View lineC1;
    TextView VlblC1;
    EditText txtC1;
    LinearLayout secC2;
    View lineC2;
    TextView VlblC2;
    EditText txtC2;
    LinearLayout secC3;
    View lineC3;
    TextView VlblC3;
    EditText txtC3;
    LinearLayout secC4;
    View lineC4;
    TextView VlblC4;
    RadioGroup rdogrpC4;
    RadioButton rdoC41;
    RadioButton rdoC42;
    RadioButton rdoC43;
    RadioButton rdoC44;
    LinearLayout secC5;
    View lineC5;
    TextView VlblC5;
    RadioGroup rdogrpC5;
    RadioButton rdoC51;
    RadioButton rdoC52;
    LinearLayout secC6;
    View lineC6;
    TextView VlblC6;
    RadioGroup rdogrpC6;
    RadioButton rdoC61;
    RadioButton rdoC62;
    LinearLayout secC7;
    View lineC7;
    TextView VlblC7;
    RadioGroup rdogrpC7;
    RadioButton rdoC71;
    RadioButton rdoC72;
    LinearLayout seclblC8;
    View linelblC8;
    LinearLayout secC8a;
    View lineC8a;
    TextView VlblC8a;
    CheckBox chkC8a;
    LinearLayout secC8b;
    View lineC8b;
    TextView VlblC8b;
    CheckBox chkC8b;
    LinearLayout secC8c;
    View lineC8c;
    TextView VlblC8c;
    CheckBox chkC8c;
    LinearLayout secC8d;
    View lineC8d;
    TextView VlblC8d;
    CheckBox chkC8d;
    LinearLayout secC8e;
    View lineC8e;
    TextView VlblC8e;
    CheckBox chkC8e;
    LinearLayout secC8f;
    View lineC8f;
    TextView VlblC8f;
    CheckBox chkC8f;
    LinearLayout secC8g;
    View lineC8g;
    TextView VlblC8g;
    CheckBox chkC8g;
    LinearLayout secC8h;
    View lineC8h;
    TextView VlblC8h;
    CheckBox chkC8h;
    LinearLayout secC8i;
    View lineC8i;
    TextView VlblC8i;
    CheckBox chkC8i;
    LinearLayout secC8j;
    View lineC8j;
    TextView VlblC8j;
    CheckBox chkC8j;
    LinearLayout secC8k;
    View lineC8k;
    TextView VlblC8k;
    CheckBox chkC8k;
    LinearLayout secC8l;
    View lineC8l;
    TextView VlblC8l;
    CheckBox chkC8l;
    LinearLayout secC8m;
    View lineC8m;
    TextView VlblC8m;
    CheckBox chkC8m;
    LinearLayout secC8n;
    View lineC8n;
    TextView VlblC8n;
    CheckBox chkC8n;
    LinearLayout secC8o;
    View lineC8o;
    TextView VlblC8o;
    CheckBox chkC8o;
    LinearLayout secC8p;
    View lineC8p;
    TextView VlblC8p;
    CheckBox chkC8p;
    LinearLayout secC8q;
    View lineC8q;
    TextView VlblC8q;
    CheckBox chkC8q;
    LinearLayout secC8x;
    View lineC8x;
    TextView VlblC8x;
    CheckBox chkC8x;
    LinearLayout secC8xSp;
    View lineC8xSp;
    TextView VlblC8xSp;
    EditText txtC8xSp;
    LinearLayout secC8z;
    View lineC8z;
    TextView VlblC8z;
    CheckBox chkC8z;

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
     static String PatCatPreg = "";
     static String PatCatDeliv = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.sectionc);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");
         PatCatPreg = IDbundle.getString("PatCatPreg");
         PatCatDeliv = IDbundle.getString("PatCatDeliv");

         TableName = "SectionC";
         MODULEID = 3;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionC.this);
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
        Connection.LocalizeLanguage(SectionC.this, MODULEID, LANGUAGEID);





         //Hide all skip variables
//         secC1.setVisibility(View.GONE);
//         lineC1.setVisibility(View.GONE);
//         secC2.setVisibility(View.GONE);
//         lineC2.setVisibility(View.GONE);
//         secC3.setVisibility(View.GONE);
//         lineC3.setVisibility(View.GONE);
//         secC4.setVisibility(View.GONE);
//         lineC4.setVisibility(View.GONE);
//         secC5.setVisibility(View.GONE);
//         lineC5.setVisibility(View.GONE);
//         secC6.setVisibility(View.GONE);
//         lineC6.setVisibility(View.GONE);
//         secC7.setVisibility(View.GONE);
//         lineC7.setVisibility(View.GONE);
//         seclblC8.setVisibility(View.GONE);
//         linelblC8.setVisibility(View.GONE);
//         secC8a.setVisibility(View.GONE);
//         lineC8a.setVisibility(View.GONE);
//         secC8b.setVisibility(View.GONE);
//         lineC8b.setVisibility(View.GONE);
//         secC8c.setVisibility(View.GONE);
//         lineC8c.setVisibility(View.GONE);
//         secC8d.setVisibility(View.GONE);
//         lineC8d.setVisibility(View.GONE);
//         secC8e.setVisibility(View.GONE);
//         lineC8e.setVisibility(View.GONE);
//         secC8f.setVisibility(View.GONE);
//         lineC8f.setVisibility(View.GONE);
//         secC8g.setVisibility(View.GONE);
//         lineC8g.setVisibility(View.GONE);
//         secC8h.setVisibility(View.GONE);
//         lineC8h.setVisibility(View.GONE);
//         secC8i.setVisibility(View.GONE);
//         lineC8i.setVisibility(View.GONE);
//         secC8j.setVisibility(View.GONE);
//         lineC8j.setVisibility(View.GONE);
//         secC8k.setVisibility(View.GONE);
//         lineC8k.setVisibility(View.GONE);
//         secC8l.setVisibility(View.GONE);
//         lineC8l.setVisibility(View.GONE);
//         secC8m.setVisibility(View.GONE);
//         lineC8m.setVisibility(View.GONE);
//         secC8n.setVisibility(View.GONE);
//         lineC8n.setVisibility(View.GONE);
//         secC8o.setVisibility(View.GONE);
//         lineC8o.setVisibility(View.GONE);
//         secC8p.setVisibility(View.GONE);
//         lineC8p.setVisibility(View.GONE);
//         secC8q.setVisibility(View.GONE);
//         lineC8q.setVisibility(View.GONE);
//         secC8x.setVisibility(View.GONE);
//         lineC8x.setVisibility(View.GONE);
         secC8xSp.setVisibility(View.GONE);
         lineC8xSp.setVisibility(View.GONE);
//         secC8z.setVisibility(View.GONE);
//         lineC8z.setVisibility(View.GONE);
         secC8xSp.setVisibility(View.GONE);
         lineC8xSp.setVisibility(View.GONE);


        DataSearch(PATIENTID,FACILITYID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionC.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblC1a=(LinearLayout)findViewById(R.id.seclblC1a);
         linelblC1a=(View)findViewById(R.id.linelblC1a);
         seclblC1b=(LinearLayout)findViewById(R.id.seclblC1b);
         linelblC1b=(View)findViewById(R.id.linelblC1b);
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
         secPatientCat=(LinearLayout)findViewById(R.id.secPatientCat);
         linePatientCat=(View)findViewById(R.id.linePatientCat);
         VlblPatientCat = (TextView) findViewById(R.id.VlblPatientCat);
         rdogrpPatientCat = (RadioGroup) findViewById(R.id.rdogrpPatientCat);
         rdoPatientCat1 = (RadioButton) findViewById(R.id.rdoPatientCat1);
         rdoPatientCat2 = (RadioButton) findViewById(R.id.rdoPatientCat2);
         rdogrpPatientCat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpPatientCat = new String[] {"1","2"};
             for (int i = 0; i < rdogrpPatientCat.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpPatientCat.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpPatientCat[i];
             }

             /*if(rbData.equalsIgnoreCase("2"))
             {
                 secC1.setVisibility(View.GONE);
                 lineC1.setVisibility(View.GONE);
                 txtC1.setText("");
                 secC2.setVisibility(View.GONE);
                 lineC2.setVisibility(View.GONE);
                 txtC2.setText("");
                 secC3.setVisibility(View.GONE);
                 lineC3.setVisibility(View.GONE);
                 txtC3.setText("");
                 secC4.setVisibility(View.GONE);
                 lineC4.setVisibility(View.GONE);
                 rdogrpC4.clearCheck();
                 secC5.setVisibility(View.GONE);
                 lineC5.setVisibility(View.GONE);
                 rdogrpC5.clearCheck();
                 secC6.setVisibility(View.GONE);
                 lineC6.setVisibility(View.GONE);
                 rdogrpC6.clearCheck();
                 secC7.setVisibility(View.GONE);
                 lineC7.setVisibility(View.GONE);
                 rdogrpC7.clearCheck();
                 seclblC8.setVisibility(View.GONE);
                 linelblC8.setVisibility(View.GONE);
                 secC8a.setVisibility(View.GONE);
                 lineC8a.setVisibility(View.GONE);
                 chkC8a.setChecked(false);
                 secC8b.setVisibility(View.GONE);
                 lineC8b.setVisibility(View.GONE);
                 chkC8b.setChecked(false);
                 secC8c.setVisibility(View.GONE);
                 lineC8c.setVisibility(View.GONE);
                 chkC8c.setChecked(false);
                 secC8d.setVisibility(View.GONE);
                 lineC8d.setVisibility(View.GONE);
                 chkC8d.setChecked(false);
                 secC8e.setVisibility(View.GONE);
                 lineC8e.setVisibility(View.GONE);
                 chkC8e.setChecked(false);
                 secC8f.setVisibility(View.GONE);
                 lineC8f.setVisibility(View.GONE);
                 chkC8f.setChecked(false);
                 secC8g.setVisibility(View.GONE);
                 lineC8g.setVisibility(View.GONE);
                 chkC8g.setChecked(false);
                 secC8h.setVisibility(View.GONE);
                 lineC8h.setVisibility(View.GONE);
                 chkC8h.setChecked(false);
                 secC8i.setVisibility(View.GONE);
                 lineC8i.setVisibility(View.GONE);
                 chkC8i.setChecked(false);
                 secC8j.setVisibility(View.GONE);
                 lineC8j.setVisibility(View.GONE);
                 chkC8j.setChecked(false);
                 secC8k.setVisibility(View.GONE);
                 lineC8k.setVisibility(View.GONE);
                 chkC8k.setChecked(false);
                 secC8l.setVisibility(View.GONE);
                 lineC8l.setVisibility(View.GONE);
                 chkC8l.setChecked(false);
                 secC8m.setVisibility(View.GONE);
                 lineC8m.setVisibility(View.GONE);
                 chkC8m.setChecked(false);
                 secC8n.setVisibility(View.GONE);
                 lineC8n.setVisibility(View.GONE);
                 chkC8n.setChecked(false);
                 secC8o.setVisibility(View.GONE);
                 lineC8o.setVisibility(View.GONE);
                 chkC8o.setChecked(false);
                 secC8p.setVisibility(View.GONE);
                 lineC8p.setVisibility(View.GONE);
                 chkC8p.setChecked(false);
                 secC8q.setVisibility(View.GONE);
                 lineC8q.setVisibility(View.GONE);
                 chkC8q.setChecked(false);
                 secC8x.setVisibility(View.GONE);
                 lineC8x.setVisibility(View.GONE);
                 chkC8x.setChecked(false);
                 secC8xSp.setVisibility(View.GONE);
                 lineC8xSp.setVisibility(View.GONE);
                 txtC8xSp.setText("");
                 secC8z.setVisibility(View.GONE);
                 lineC8z.setVisibility(View.GONE);
                 chkC8z.setChecked(false);
             }
             else
             {
                 secC1.setVisibility(View.VISIBLE);
                 lineC1.setVisibility(View.VISIBLE);
                 secC2.setVisibility(View.VISIBLE);
                 lineC2.setVisibility(View.VISIBLE);
                 secC3.setVisibility(View.VISIBLE);
                 lineC3.setVisibility(View.VISIBLE);
                 secC4.setVisibility(View.VISIBLE);
                 lineC4.setVisibility(View.VISIBLE);
                 secC5.setVisibility(View.VISIBLE);
                 lineC5.setVisibility(View.VISIBLE);
                 secC6.setVisibility(View.VISIBLE);
                 lineC6.setVisibility(View.VISIBLE);
                 secC7.setVisibility(View.VISIBLE);
                 lineC7.setVisibility(View.VISIBLE);
                 seclblC8.setVisibility(View.VISIBLE);
                 linelblC8.setVisibility(View.VISIBLE);
                 secC8a.setVisibility(View.VISIBLE);
                 lineC8a.setVisibility(View.VISIBLE);
                 secC8b.setVisibility(View.VISIBLE);
                 lineC8b.setVisibility(View.VISIBLE);
                 secC8c.setVisibility(View.VISIBLE);
                 lineC8c.setVisibility(View.VISIBLE);
                 secC8d.setVisibility(View.VISIBLE);
                 lineC8d.setVisibility(View.VISIBLE);
                 secC8e.setVisibility(View.VISIBLE);
                 lineC8e.setVisibility(View.VISIBLE);
                 secC8f.setVisibility(View.VISIBLE);
                 lineC8f.setVisibility(View.VISIBLE);
                 secC8g.setVisibility(View.VISIBLE);
                 lineC8g.setVisibility(View.VISIBLE);
                 secC8h.setVisibility(View.VISIBLE);
                 lineC8h.setVisibility(View.VISIBLE);
                 secC8i.setVisibility(View.VISIBLE);
                 lineC8i.setVisibility(View.VISIBLE);
                 secC8j.setVisibility(View.VISIBLE);
                 lineC8j.setVisibility(View.VISIBLE);
                 secC8k.setVisibility(View.VISIBLE);
                 lineC8k.setVisibility(View.VISIBLE);
                 secC8l.setVisibility(View.VISIBLE);
                 lineC8l.setVisibility(View.VISIBLE);
                 secC8m.setVisibility(View.VISIBLE);
                 lineC8m.setVisibility(View.VISIBLE);
                 secC8n.setVisibility(View.VISIBLE);
                 lineC8n.setVisibility(View.VISIBLE);
                 secC8o.setVisibility(View.VISIBLE);
                 lineC8o.setVisibility(View.VISIBLE);
                 secC8p.setVisibility(View.VISIBLE);
                 lineC8p.setVisibility(View.VISIBLE);
                 secC8q.setVisibility(View.VISIBLE);
                 lineC8q.setVisibility(View.VISIBLE);
                 secC8x.setVisibility(View.VISIBLE);
                 lineC8x.setVisibility(View.VISIBLE);
                 secC8z.setVisibility(View.VISIBLE);
                 lineC8z.setVisibility(View.VISIBLE);
             }*/
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secC1=(LinearLayout)findViewById(R.id.secC1);
         lineC1=(View)findViewById(R.id.lineC1);
         VlblC1=(TextView) findViewById(R.id.VlblC1);
         txtC1=(EditText) findViewById(R.id.txtC1);
         secC2=(LinearLayout)findViewById(R.id.secC2);
         lineC2=(View)findViewById(R.id.lineC2);
         VlblC2=(TextView) findViewById(R.id.VlblC2);
         txtC2=(EditText) findViewById(R.id.txtC2);
         secC3=(LinearLayout)findViewById(R.id.secC3);
         lineC3=(View)findViewById(R.id.lineC3);
         VlblC3=(TextView) findViewById(R.id.VlblC3);
         txtC3=(EditText) findViewById(R.id.txtC3);
         secC4=(LinearLayout)findViewById(R.id.secC4);
         lineC4=(View)findViewById(R.id.lineC4);
         VlblC4 = (TextView) findViewById(R.id.VlblC4);
         rdogrpC4 = (RadioGroup) findViewById(R.id.rdogrpC4);
         rdoC41 = (RadioButton) findViewById(R.id.rdoC41);
         rdoC42 = (RadioButton) findViewById(R.id.rdoC42);
         rdoC43 = (RadioButton) findViewById(R.id.rdoC43);
         rdoC44 = (RadioButton) findViewById(R.id.rdoC44);
         secC5=(LinearLayout)findViewById(R.id.secC5);
         lineC5=(View)findViewById(R.id.lineC5);
         VlblC5 = (TextView) findViewById(R.id.VlblC5);
         rdogrpC5 = (RadioGroup) findViewById(R.id.rdogrpC5);
         rdoC51 = (RadioButton) findViewById(R.id.rdoC51);
         rdoC52 = (RadioButton) findViewById(R.id.rdoC52);
         secC6=(LinearLayout)findViewById(R.id.secC6);
         lineC6=(View)findViewById(R.id.lineC6);
         VlblC6 = (TextView) findViewById(R.id.VlblC6);
         rdogrpC6 = (RadioGroup) findViewById(R.id.rdogrpC6);
         rdoC61 = (RadioButton) findViewById(R.id.rdoC61);
         rdoC62 = (RadioButton) findViewById(R.id.rdoC62);
         secC7=(LinearLayout)findViewById(R.id.secC7);
         lineC7=(View)findViewById(R.id.lineC7);
         VlblC7 = (TextView) findViewById(R.id.VlblC7);
         rdogrpC7 = (RadioGroup) findViewById(R.id.rdogrpC7);
         rdoC71 = (RadioButton) findViewById(R.id.rdoC71);
         rdoC72 = (RadioButton) findViewById(R.id.rdoC72);
         seclblC8=(LinearLayout)findViewById(R.id.seclblC8);
         linelblC8=(View)findViewById(R.id.linelblC8);
         secC8a=(LinearLayout)findViewById(R.id.secC8a);
         lineC8a=(View)findViewById(R.id.lineC8a);
         VlblC8a=(TextView) findViewById(R.id.VlblC8a);
         chkC8a=(CheckBox) findViewById(R.id.chkC8a);
         secC8b=(LinearLayout)findViewById(R.id.secC8b);
         lineC8b=(View)findViewById(R.id.lineC8b);
         VlblC8b=(TextView) findViewById(R.id.VlblC8b);
         chkC8b=(CheckBox) findViewById(R.id.chkC8b);
         secC8c=(LinearLayout)findViewById(R.id.secC8c);
         lineC8c=(View)findViewById(R.id.lineC8c);
         VlblC8c=(TextView) findViewById(R.id.VlblC8c);
         chkC8c=(CheckBox) findViewById(R.id.chkC8c);
         secC8d=(LinearLayout)findViewById(R.id.secC8d);
         lineC8d=(View)findViewById(R.id.lineC8d);
         VlblC8d=(TextView) findViewById(R.id.VlblC8d);
         chkC8d=(CheckBox) findViewById(R.id.chkC8d);
         secC8e=(LinearLayout)findViewById(R.id.secC8e);
         lineC8e=(View)findViewById(R.id.lineC8e);
         VlblC8e=(TextView) findViewById(R.id.VlblC8e);
         chkC8e=(CheckBox) findViewById(R.id.chkC8e);
         secC8f=(LinearLayout)findViewById(R.id.secC8f);
         lineC8f=(View)findViewById(R.id.lineC8f);
         VlblC8f=(TextView) findViewById(R.id.VlblC8f);
         chkC8f=(CheckBox) findViewById(R.id.chkC8f);
         secC8g=(LinearLayout)findViewById(R.id.secC8g);
         lineC8g=(View)findViewById(R.id.lineC8g);
         VlblC8g=(TextView) findViewById(R.id.VlblC8g);
         chkC8g=(CheckBox) findViewById(R.id.chkC8g);
         secC8h=(LinearLayout)findViewById(R.id.secC8h);
         lineC8h=(View)findViewById(R.id.lineC8h);
         VlblC8h=(TextView) findViewById(R.id.VlblC8h);
         chkC8h=(CheckBox) findViewById(R.id.chkC8h);
         secC8i=(LinearLayout)findViewById(R.id.secC8i);
         lineC8i=(View)findViewById(R.id.lineC8i);
         VlblC8i=(TextView) findViewById(R.id.VlblC8i);
         chkC8i=(CheckBox) findViewById(R.id.chkC8i);
         secC8j=(LinearLayout)findViewById(R.id.secC8j);
         lineC8j=(View)findViewById(R.id.lineC8j);
         VlblC8j=(TextView) findViewById(R.id.VlblC8j);
         chkC8j=(CheckBox) findViewById(R.id.chkC8j);
         secC8k=(LinearLayout)findViewById(R.id.secC8k);
         lineC8k=(View)findViewById(R.id.lineC8k);
         VlblC8k=(TextView) findViewById(R.id.VlblC8k);
         chkC8k=(CheckBox) findViewById(R.id.chkC8k);
         secC8l=(LinearLayout)findViewById(R.id.secC8l);
         lineC8l=(View)findViewById(R.id.lineC8l);
         VlblC8l=(TextView) findViewById(R.id.VlblC8l);
         chkC8l=(CheckBox) findViewById(R.id.chkC8l);
         secC8m=(LinearLayout)findViewById(R.id.secC8m);
         lineC8m=(View)findViewById(R.id.lineC8m);
         VlblC8m=(TextView) findViewById(R.id.VlblC8m);
         chkC8m=(CheckBox) findViewById(R.id.chkC8m);
         secC8n=(LinearLayout)findViewById(R.id.secC8n);
         lineC8n=(View)findViewById(R.id.lineC8n);
         VlblC8n=(TextView) findViewById(R.id.VlblC8n);
         chkC8n=(CheckBox) findViewById(R.id.chkC8n);
         secC8o=(LinearLayout)findViewById(R.id.secC8o);
         lineC8o=(View)findViewById(R.id.lineC8o);
         VlblC8o=(TextView) findViewById(R.id.VlblC8o);
         chkC8o=(CheckBox) findViewById(R.id.chkC8o);
         secC8p=(LinearLayout)findViewById(R.id.secC8p);
         lineC8p=(View)findViewById(R.id.lineC8p);
         VlblC8p=(TextView) findViewById(R.id.VlblC8p);
         chkC8p=(CheckBox) findViewById(R.id.chkC8p);
         secC8q=(LinearLayout)findViewById(R.id.secC8q);
         lineC8q=(View)findViewById(R.id.lineC8q);
         VlblC8q=(TextView) findViewById(R.id.VlblC8q);
         chkC8q=(CheckBox) findViewById(R.id.chkC8q);
         secC8x=(LinearLayout)findViewById(R.id.secC8x);
         lineC8x=(View)findViewById(R.id.lineC8x);
         VlblC8x=(TextView) findViewById(R.id.VlblC8x);
         chkC8x=(CheckBox) findViewById(R.id.chkC8x);
         chkC8x.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked) {
                    secC8xSp.setVisibility(View.VISIBLE);
                    lineC8xSp.setVisibility(View.VISIBLE);
                 }
                 else {
                    secC8xSp.setVisibility(View.GONE);
                    lineC8xSp.setVisibility(View.GONE);
                    txtC8xSp.setText("");
                 }
                 }
         });
         secC8xSp=(LinearLayout)findViewById(R.id.secC8xSp);
         lineC8xSp=(View)findViewById(R.id.lineC8xSp);
         VlblC8xSp=(TextView) findViewById(R.id.VlblC8xSp);
         txtC8xSp=(EditText) findViewById(R.id.txtC8xSp);
         secC8z=(LinearLayout)findViewById(R.id.secC8z);
         lineC8z=(View)findViewById(R.id.lineC8z);
         VlblC8z=(TextView) findViewById(R.id.VlblC8z);
         chkC8z=(CheckBox) findViewById(R.id.chkC8z);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionC.this, e.getMessage());
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
         	Connection.MessageBox(SectionC.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionC_DataModel objSave = new SectionC_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpPatientCat = new String[] {"1","2"};
         objSave.setPatientCat("");
         for (int i = 0; i < rdogrpPatientCat.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpPatientCat.getChildAt(i);
             if (rb.isChecked()) objSave.setPatientCat(d_rdogrpPatientCat[i]);
         }

         objSave.setC1(txtC1.getText().toString());
         objSave.setC2(txtC2.getText().toString());
         objSave.setC3(txtC3.getText().toString());
         String[] d_rdogrpC4 = new String[] {"1","2","3","4"};
         objSave.setC4("");
         for (int i = 0; i < rdogrpC4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpC4.getChildAt(i);
             if (rb.isChecked()) objSave.setC4(d_rdogrpC4[i]);
         }

         String[] d_rdogrpC5 = new String[] {"1","2"};
         objSave.setC5("");
         for (int i = 0; i < rdogrpC5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpC5.getChildAt(i);
             if (rb.isChecked()) objSave.setC5(d_rdogrpC5[i]);
         }

         String[] d_rdogrpC6 = new String[] {"1","2"};
         objSave.setC6("");
         for (int i = 0; i < rdogrpC6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpC6.getChildAt(i);
             if (rb.isChecked()) objSave.setC6(d_rdogrpC6[i]);
         }

         String[] d_rdogrpC7 = new String[] {"1","2"};
         objSave.setC7("");
         for (int i = 0; i < rdogrpC7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpC7.getChildAt(i);
             if (rb.isChecked()) objSave.setC7(d_rdogrpC7[i]);
         }

         objSave.setC8a((chkC8a.isChecked() ? "1" : (secC8a.isShown() ? "2" : "")));
         objSave.setC8b((chkC8b.isChecked() ? "1" : (secC8b.isShown() ? "2" : "")));
         objSave.setC8c((chkC8c.isChecked() ? "1" : (secC8c.isShown() ? "2" : "")));
         objSave.setC8d((chkC8d.isChecked() ? "1" : (secC8d.isShown() ? "2" : "")));
         objSave.setC8e((chkC8e.isChecked() ? "1" : (secC8e.isShown() ? "2" : "")));
         objSave.setC8f((chkC8f.isChecked() ? "1" : (secC8f.isShown() ? "2" : "")));
         objSave.setC8g((chkC8g.isChecked() ? "1" : (secC8g.isShown() ? "2" : "")));
         objSave.setC8h((chkC8h.isChecked() ? "1" : (secC8h.isShown() ? "2" : "")));
         objSave.setC8i((chkC8i.isChecked() ? "1" : (secC8i.isShown() ? "2" : "")));
         objSave.setC8j((chkC8j.isChecked() ? "1" : (secC8j.isShown() ? "2" : "")));
         objSave.setC8k((chkC8k.isChecked() ? "1" : (secC8k.isShown() ? "2" : "")));
         objSave.setC8l((chkC8l.isChecked() ? "1" : (secC8l.isShown() ? "2" : "")));
         objSave.setC8m((chkC8m.isChecked() ? "1" : (secC8m.isShown() ? "2" : "")));
         objSave.setC8n((chkC8n.isChecked() ? "1" : (secC8n.isShown() ? "2" : "")));
         objSave.setC8o((chkC8o.isChecked() ? "1" : (secC8o.isShown() ? "2" : "")));
         objSave.setC8p((chkC8p.isChecked() ? "1" : (secC8p.isShown() ? "2" : "")));
         objSave.setC8q((chkC8q.isChecked() ? "1" : (secC8q.isShown() ? "2" : "")));
         objSave.setC8x((chkC8x.isChecked() ? "1" : (secC8x.isShown() ? "2" : "")));
         objSave.setC8xSp(txtC8xSp.getText().toString());
         objSave.setC8z((chkC8z.isChecked() ? "1" : (secC8z.isShown() ? "2" : "")));
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
             Connection.MessageBox(SectionC.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionD.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);*/

             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);
             Connection.MessageBox(SectionC.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(SectionC.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionC.this, e.getMessage());
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
         if(!rdoPatientCat1.isChecked() & !rdoPatientCat2.isChecked() & secPatientCat.isShown())
           {
             ValidationMsg += "\nRequired field: Patient Category.";
             secPatientCat.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtC1.getText().toString().length()==0 & secC1.isShown())
           {
             ValidationMsg += "\nC1. Required field: কত সপ্তাহের গর্ভবতী.";
             secC1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secC1.isShown() & (Integer.valueOf(txtC1.getText().toString().length()==0 ? "04" : txtC1.getText().toString()) < 04 || Integer.valueOf(txtC1.getText().toString().length()==0 ? "40" : txtC1.getText().toString()) > 40))
           {
             ValidationMsg += "\nC1. Value should be between 04 and 40(কত সপ্তাহের গর্ভবতী).";
             secC1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtC2.getText().toString().length()==0 & secC2.isShown())
           {
             ValidationMsg += "\nC2. Required field: প্রসবোত্তর সপ্তাহ.";
             secC2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secC2.isShown() & (Integer.valueOf(txtC2.getText().toString().length()==0 ? "00" : txtC2.getText().toString()) < 00 || Integer.valueOf(txtC2.getText().toString().length()==0 ? "42" : txtC2.getText().toString()) > 42))
           {
             ValidationMsg += "\nC2. Value should be between 00 and 42(প্রসবোত্তর সপ্তাহ).";
             secC2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtC3.getText().toString().length()==0 & secC3.isShown())
           {
             ValidationMsg += "\nC3. Required field: মোট জিবীত সন্তানের সংখ্যা.";
             secC3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secC3.isShown() & (Integer.valueOf(txtC3.getText().toString().length()==0 ? "00" : txtC3.getText().toString()) < 00 || Integer.valueOf(txtC3.getText().toString().length()==0 ? "20" : txtC3.getText().toString()) > 20))
           {
             ValidationMsg += "\nC3. Value should be between 00 and 20(মোট জিবীত সন্তানের সংখ্যা).";
             secC3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoC41.isChecked() & !rdoC42.isChecked() & !rdoC43.isChecked() & !rdoC44.isChecked() & secC4.isShown())
           {
             ValidationMsg += "\nC4. Required field: সর্বশেষ কিভাবে সন্তান জন্মদান করেছেন?.";
             secC4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoC51.isChecked() & !rdoC52.isChecked() & secC5.isShown())
           {
             ValidationMsg += "\nC5. Required field: গর্ভপাতের ইতিহাস আছে?.";
             secC5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoC61.isChecked() & !rdoC62.isChecked() & secC6.isShown())
           {
             ValidationMsg += "\nC6. Required field: কখনো মৃত শিশু জন্ম নিয়েছে.";
             secC6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoC71.isChecked() & !rdoC72.isChecked() & secC7.isShown())
           {
             ValidationMsg += "\nC7. Required field: জীবিত জন্ম নেয়ার পর কখনো জন্মের ৫ বছরের মধ্যে সন্তান মারা গিয়েছে?.";
             secC7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtC8xSp.getText().toString().length()==0 & secC8xSp.isShown())
           {
             ValidationMsg += "\nRequired field: অন্যান্য হলে নির্দিষ্ট করুন.";
             secC8xSp.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }

         if (chkC8z.isChecked() & (chkC8a.isChecked() || chkC8b.isChecked() || chkC8c.isChecked()|| chkC8d.isChecked() || chkC8e.isChecked() || chkC8f.isChecked() || chkC8g.isChecked() || chkC8h.isChecked()|| chkC8i.isChecked()|| chkC8j.isChecked()|| chkC8k.isChecked()|| chkC8l.isChecked() || chkC8m.isChecked() || chkC8n.isChecked() || chkC8o.isChecked() || chkC8p.isChecked() || chkC8q.isChecked() || chkC8x.isChecked()) & chkC8a.isShown())
         {
             ValidationMsg += "\n C8. 98 জানিনা হয় তাহলে 1-18 হবেনা";
             chkC8a.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8c.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8d.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8e.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8f.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8g.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8h.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8i.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8j.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8k.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8l.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8m.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8n.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8o.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8p.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8q.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8x.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
             chkC8z.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secPatientCat.setBackgroundColor(Color.WHITE);
             secC1.setBackgroundColor(Color.WHITE);
             secC1.setBackgroundColor(Color.WHITE);
             secC2.setBackgroundColor(Color.WHITE);
             secC2.setBackgroundColor(Color.WHITE);
             secC3.setBackgroundColor(Color.WHITE);
             secC3.setBackgroundColor(Color.WHITE);
             secC4.setBackgroundColor(Color.WHITE);
             secC5.setBackgroundColor(Color.WHITE);
             secC6.setBackgroundColor(Color.WHITE);
             secC7.setBackgroundColor(Color.WHITE);
             secC8xSp.setBackgroundColor(Color.WHITE);
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
           SectionC_DataModel d = new SectionC_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionC_DataModel> data = d.SelectAll(this, SQL);
           for(SectionC_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpPatientCat = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpPatientCat.length; i++)
             {
                 if (String.valueOf(item.getPatientCat()).equals(String.valueOf(d_rdogrpPatientCat[i])))
                 {
                     rb = (RadioButton)rdogrpPatientCat.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtC1.setText(String.valueOf(item.getC1()));
             txtC2.setText(String.valueOf(item.getC2()));
             txtC3.setText(String.valueOf(item.getC3()));
             String[] d_rdogrpC4 = new String[] {"1","2","3","4"};
             for (int i = 0; i < d_rdogrpC4.length; i++)
             {
                 if (String.valueOf(item.getC4()).equals(String.valueOf(d_rdogrpC4[i])))
                 {
                     rb = (RadioButton)rdogrpC4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpC5 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpC5.length; i++)
             {
                 if (String.valueOf(item.getC5()).equals(String.valueOf(d_rdogrpC5[i])))
                 {
                     rb = (RadioButton)rdogrpC5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpC6 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpC6.length; i++)
             {
                 if (String.valueOf(item.getC6()).equals(String.valueOf(d_rdogrpC6[i])))
                 {
                     rb = (RadioButton)rdogrpC6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpC7 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpC7.length; i++)
             {
                 if (String.valueOf(item.getC7()).equals(String.valueOf(d_rdogrpC7[i])))
                 {
                     rb = (RadioButton)rdogrpC7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             if(String.valueOf(item.getC8a()).equals("1"))
             {
                chkC8a.setChecked(true);
             }
             else if(String.valueOf(item.getC8a()).equals("2"))
             {
                chkC8a.setChecked(false);
             }
             if(String.valueOf(item.getC8b()).equals("1"))
             {
                chkC8b.setChecked(true);
             }
             else if(String.valueOf(item.getC8b()).equals("2"))
             {
                chkC8b.setChecked(false);
             }
             if(String.valueOf(item.getC8c()).equals("1"))
             {
                chkC8c.setChecked(true);
             }
             else if(String.valueOf(item.getC8c()).equals("2"))
             {
                chkC8c.setChecked(false);
             }
             if(String.valueOf(item.getC8d()).equals("1"))
             {
                chkC8d.setChecked(true);
             }
             else if(String.valueOf(item.getC8d()).equals("2"))
             {
                chkC8d.setChecked(false);
             }
             if(String.valueOf(item.getC8e()).equals("1"))
             {
                chkC8e.setChecked(true);
             }
             else if(String.valueOf(item.getC8e()).equals("2"))
             {
                chkC8e.setChecked(false);
             }
             if(String.valueOf(item.getC8f()).equals("1"))
             {
                chkC8f.setChecked(true);
             }
             else if(String.valueOf(item.getC8f()).equals("2"))
             {
                chkC8f.setChecked(false);
             }
             if(String.valueOf(item.getC8g()).equals("1"))
             {
                chkC8g.setChecked(true);
             }
             else if(String.valueOf(item.getC8g()).equals("2"))
             {
                chkC8g.setChecked(false);
             }
             if(String.valueOf(item.getC8h()).equals("1"))
             {
                chkC8h.setChecked(true);
             }
             else if(String.valueOf(item.getC8h()).equals("2"))
             {
                chkC8h.setChecked(false);
             }
             if(String.valueOf(item.getC8i()).equals("1"))
             {
                chkC8i.setChecked(true);
             }
             else if(String.valueOf(item.getC8i()).equals("2"))
             {
                chkC8i.setChecked(false);
             }
             if(String.valueOf(item.getC8j()).equals("1"))
             {
                chkC8j.setChecked(true);
             }
             else if(String.valueOf(item.getC8j()).equals("2"))
             {
                chkC8j.setChecked(false);
             }
             if(String.valueOf(item.getC8k()).equals("1"))
             {
                chkC8k.setChecked(true);
             }
             else if(String.valueOf(item.getC8k()).equals("2"))
             {
                chkC8k.setChecked(false);
             }
             if(String.valueOf(item.getC8l()).equals("1"))
             {
                chkC8l.setChecked(true);
             }
             else if(String.valueOf(item.getC8l()).equals("2"))
             {
                chkC8l.setChecked(false);
             }
             if(String.valueOf(item.getC8m()).equals("1"))
             {
                chkC8m.setChecked(true);
             }
             else if(String.valueOf(item.getC8m()).equals("2"))
             {
                chkC8m.setChecked(false);
             }
             if(String.valueOf(item.getC8n()).equals("1"))
             {
                chkC8n.setChecked(true);
             }
             else if(String.valueOf(item.getC8n()).equals("2"))
             {
                chkC8n.setChecked(false);
             }
             if(String.valueOf(item.getC8o()).equals("1"))
             {
                chkC8o.setChecked(true);
             }
             else if(String.valueOf(item.getC8o()).equals("2"))
             {
                chkC8o.setChecked(false);
             }
             if(String.valueOf(item.getC8p()).equals("1"))
             {
                chkC8p.setChecked(true);
             }
             else if(String.valueOf(item.getC8p()).equals("2"))
             {
                chkC8p.setChecked(false);
             }
             if(String.valueOf(item.getC8q()).equals("1"))
             {
                chkC8q.setChecked(true);
             }
             else if(String.valueOf(item.getC8q()).equals("2"))
             {
                chkC8q.setChecked(false);
             }
             if(String.valueOf(item.getC8x()).equals("1"))
             {
                chkC8x.setChecked(true);
             }
             else if(String.valueOf(item.getC8x()).equals("2"))
             {
                chkC8x.setChecked(false);
             }
             txtC8xSp.setText(item.getC8xSp());
             if(String.valueOf(item.getC8z()).equals("1"))
             {
                chkC8z.setChecked(true);
             }
             else if(String.valueOf(item.getC8z()).equals("2"))
             {
                chkC8z.setChecked(false);
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionC.this, e.getMessage());
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