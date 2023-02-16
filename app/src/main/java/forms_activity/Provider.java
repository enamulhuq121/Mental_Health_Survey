
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

 import forms_datamodel.Provider_DataModel;

 public class Provider extends AppCompatActivity {
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
    LinearLayout seclblA1f;
    View linelblA1f;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secProviderID;
    View lineProviderID;
    TextView VlblProviderID;
    EditText txtProviderID;
    LinearLayout secName;
    View lineName;
    TextView VlblName;
    EditText txtName;
    LinearLayout secProfeTitle;
    View lineProfeTitle;
    TextView VlblProfeTitle;
    EditText txtProfeTitle;
    LinearLayout secWard;
    View lineWard;
    TextView VlblWard;
    EditText txtWard;
    LinearLayout seclblA1c;
    View linelblA1c;
    LinearLayout secExpeMonth;
    View lineExpeMonth;
    TextView VlblExpeMonth;
    EditText txtExpeMonth;
    LinearLayout secExpeYear;
    View lineExpeYear;
    TextView VlblExpeYear;
    EditText txtExpeYear;
    LinearLayout seclblA1d;
    View linelblA1d;
    LinearLayout secExpeFaciMonth;
    View lineExpeFaciMonth;
    TextView VlblExpeFaciMonth;
    EditText txtExpeFaciMonth;
    LinearLayout secExpeFaciYera;
    View lineExpeFaciYera;
    TextView VlblExpeFaciYera;
    EditText txtExpeFaciYera;
    LinearLayout seclblA1e;
    View linelblA1e;
    LinearLayout secExpeWardMonth;
    View lineExpeWardMonth;
    TextView VlblExpeWardMonth;
    EditText txtExpeWardMonth;
    LinearLayout secExpeWardYera;
    View lineExpeWardYera;
    TextView VlblExpeWardYera;
    EditText txtExpeWardYera;
    LinearLayout secMobileNo;
    View lineMobileNo;
    TextView VlblMobileNo;
    EditText txtMobileNo;
    LinearLayout seclblA1a;
    View linelblA1a;
    LinearLayout seclblA1b;
    View linelblA1b;
    LinearLayout secA1;
    View lineA1;
    TextView VlblA1;
    RadioGroup rdogrpA1;
    RadioButton rdoA11;
    RadioButton rdoA12;
    RadioButton rdoA13;
    RadioButton rdoA14;
    RadioButton rdoA15;
    LinearLayout secA2;
    View lineA2;
    TextView VlblA2;
    RadioGroup rdogrpA2;
    RadioButton rdoA21;
    RadioButton rdoA22;
    RadioButton rdoA23;
    RadioButton rdoA24;
    RadioButton rdoA25;
    LinearLayout secA3;
    View lineA3;
    TextView VlblA3;
    RadioGroup rdogrpA3;
    RadioButton rdoA31;
    RadioButton rdoA32;
    RadioButton rdoA33;
    RadioButton rdoA34;
    RadioButton rdoA35;
    LinearLayout secA4;
    View lineA4;
    TextView VlblA4;
    RadioGroup rdogrpA4;
    RadioButton rdoA41;
    RadioButton rdoA42;
    RadioButton rdoA43;
    RadioButton rdoA44;
    RadioButton rdoA45;
    LinearLayout secA5;
    View lineA5;
    TextView VlblA5;
    RadioGroup rdogrpA5;
    RadioButton rdoA51;
    RadioButton rdoA52;
    RadioButton rdoA53;
    RadioButton rdoA54;
    RadioButton rdoA55;
    LinearLayout secA6;
    View lineA6;
    TextView VlblA6;
    RadioGroup rdogrpA6;
    RadioButton rdoA61;
    RadioButton rdoA62;
    RadioButton rdoA63;
    RadioButton rdoA64;
    RadioButton rdoA65;
    LinearLayout secA7;
    View lineA7;
    TextView VlblA7;
    RadioGroup rdogrpA7;
    RadioButton rdoA71;
    RadioButton rdoA72;
    RadioButton rdoA73;
    RadioButton rdoA74;
    RadioButton rdoA75;
    LinearLayout secA8;
    View lineA8;
    TextView VlblA8;
    RadioGroup rdogrpA8;
    RadioButton rdoA81;
    RadioButton rdoA82;
    RadioButton rdoA83;
    RadioButton rdoA84;
    RadioButton rdoA85;
    LinearLayout secA9;
    View lineA9;
    TextView VlblA9;
    RadioGroup rdogrpA9;
    RadioButton rdoA91;
    RadioButton rdoA92;
    RadioButton rdoA93;
    RadioButton rdoA94;
    RadioButton rdoA95;
    LinearLayout secA10;
    View lineA10;
    TextView VlblA10;
    RadioGroup rdogrpA10;
    RadioButton rdoA101;
    RadioButton rdoA102;
    RadioButton rdoA103;
    RadioButton rdoA104;
    RadioButton rdoA105;
    LinearLayout secA11;
    View lineA11;
    TextView VlblA11;
    RadioGroup rdogrpA11;
    RadioButton rdoA111;
    RadioButton rdoA112;
    RadioButton rdoA113;
    RadioButton rdoA114;
    RadioButton rdoA115;
    LinearLayout secA12;
    View lineA12;
    TextView VlblA12;
    RadioGroup rdogrpA12;
    RadioButton rdoA121;
    RadioButton rdoA122;
    RadioButton rdoA123;
    RadioButton rdoA124;
    RadioButton rdoA125;
    LinearLayout secA13;
    View lineA13;
    TextView VlblA13;
    RadioGroup rdogrpA13;
    RadioButton rdoA131;
    RadioButton rdoA132;
    RadioButton rdoA133;
    RadioButton rdoA134;
    RadioButton rdoA135;
    LinearLayout secA14;
    View lineA14;
    TextView VlblA14;
    RadioGroup rdogrpA14;
    RadioButton rdoA141;
    RadioButton rdoA142;
    RadioButton rdoA143;
    RadioButton rdoA144;
    RadioButton rdoA145;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String FACILITYID = "";
    static String PROVIDERID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.provider);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         FACILITYID = IDbundle.getString("FacilityID");
         PROVIDERID = IDbundle.getString("ProviderID");

         TableName = "Provider";
         MODULEID = 9;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Provider.this);
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
        Connection.LocalizeLanguage(Provider.this, MODULEID, LANGUAGEID);





         //Hide all skip variables


        DataSearch(FACILITYID,PROVIDERID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Provider.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblA1f=(LinearLayout)findViewById(R.id.seclblA1f);
         linelblA1f=(View)findViewById(R.id.linelblA1f);
         secFacilityID=(LinearLayout)findViewById(R.id.secFacilityID);
         lineFacilityID=(View)findViewById(R.id.lineFacilityID);
         VlblFacilityID=(TextView) findViewById(R.id.VlblFacilityID);
         txtFacilityID=(EditText) findViewById(R.id.txtFacilityID);
         txtFacilityID.setText(FACILITYID);
         txtFacilityID.setEnabled(false);
         secProviderID=(LinearLayout)findViewById(R.id.secProviderID);
         lineProviderID=(View)findViewById(R.id.lineProviderID);
         VlblProviderID=(TextView) findViewById(R.id.VlblProviderID);
         txtProviderID=(EditText) findViewById(R.id.txtProviderID);

         if(PROVIDERID.length()==0) txtProviderID.setText(C.NewPatientID(DEVICEID));
         else txtProviderID.setText(PROVIDERID);
         txtProviderID.setEnabled(false);

         secName=(LinearLayout)findViewById(R.id.secName);
         lineName=(View)findViewById(R.id.lineName);
         VlblName=(TextView) findViewById(R.id.VlblName);
         txtName=(EditText) findViewById(R.id.txtName);
         secProfeTitle=(LinearLayout)findViewById(R.id.secProfeTitle);
         lineProfeTitle=(View)findViewById(R.id.lineProfeTitle);
         VlblProfeTitle=(TextView) findViewById(R.id.VlblProfeTitle);
         txtProfeTitle=(EditText) findViewById(R.id.txtProfeTitle);
         secWard=(LinearLayout)findViewById(R.id.secWard);
         lineWard=(View)findViewById(R.id.lineWard);
         VlblWard=(TextView) findViewById(R.id.VlblWard);
         txtWard=(EditText) findViewById(R.id.txtWard);
         seclblA1c=(LinearLayout)findViewById(R.id.seclblA1c);
         linelblA1c=(View)findViewById(R.id.linelblA1c);
         secExpeMonth=(LinearLayout)findViewById(R.id.secExpeMonth);
         lineExpeMonth=(View)findViewById(R.id.lineExpeMonth);
         VlblExpeMonth=(TextView) findViewById(R.id.VlblExpeMonth);
         txtExpeMonth=(EditText) findViewById(R.id.txtExpeMonth);
         secExpeYear=(LinearLayout)findViewById(R.id.secExpeYear);
         lineExpeYear=(View)findViewById(R.id.lineExpeYear);
         VlblExpeYear=(TextView) findViewById(R.id.VlblExpeYear);
         txtExpeYear=(EditText) findViewById(R.id.txtExpeYear);
         seclblA1d=(LinearLayout)findViewById(R.id.seclblA1d);
         linelblA1d=(View)findViewById(R.id.linelblA1d);
         secExpeFaciMonth=(LinearLayout)findViewById(R.id.secExpeFaciMonth);
         lineExpeFaciMonth=(View)findViewById(R.id.lineExpeFaciMonth);
         VlblExpeFaciMonth=(TextView) findViewById(R.id.VlblExpeFaciMonth);
         txtExpeFaciMonth=(EditText) findViewById(R.id.txtExpeFaciMonth);
         secExpeFaciYera=(LinearLayout)findViewById(R.id.secExpeFaciYera);
         lineExpeFaciYera=(View)findViewById(R.id.lineExpeFaciYera);
         VlblExpeFaciYera=(TextView) findViewById(R.id.VlblExpeFaciYera);
         txtExpeFaciYera=(EditText) findViewById(R.id.txtExpeFaciYera);
         seclblA1e=(LinearLayout)findViewById(R.id.seclblA1e);
         linelblA1e=(View)findViewById(R.id.linelblA1e);
         secExpeWardMonth=(LinearLayout)findViewById(R.id.secExpeWardMonth);
         lineExpeWardMonth=(View)findViewById(R.id.lineExpeWardMonth);
         VlblExpeWardMonth=(TextView) findViewById(R.id.VlblExpeWardMonth);
         txtExpeWardMonth=(EditText) findViewById(R.id.txtExpeWardMonth);
         secExpeWardYera=(LinearLayout)findViewById(R.id.secExpeWardYera);
         lineExpeWardYera=(View)findViewById(R.id.lineExpeWardYera);
         VlblExpeWardYera=(TextView) findViewById(R.id.VlblExpeWardYera);
         txtExpeWardYera=(EditText) findViewById(R.id.txtExpeWardYera);
         secMobileNo=(LinearLayout)findViewById(R.id.secMobileNo);
         lineMobileNo=(View)findViewById(R.id.lineMobileNo);
         VlblMobileNo=(TextView) findViewById(R.id.VlblMobileNo);
         txtMobileNo=(EditText) findViewById(R.id.txtMobileNo);
         seclblA1a=(LinearLayout)findViewById(R.id.seclblA1a);
         linelblA1a=(View)findViewById(R.id.linelblA1a);
         seclblA1b=(LinearLayout)findViewById(R.id.seclblA1b);
         linelblA1b=(View)findViewById(R.id.linelblA1b);
         secA1=(LinearLayout)findViewById(R.id.secA1);
         lineA1=(View)findViewById(R.id.lineA1);
         VlblA1 = (TextView) findViewById(R.id.VlblA1);
         rdogrpA1 = (RadioGroup) findViewById(R.id.rdogrpA1);
         rdoA11 = (RadioButton) findViewById(R.id.rdoA11);
         rdoA12 = (RadioButton) findViewById(R.id.rdoA12);
         rdoA13 = (RadioButton) findViewById(R.id.rdoA13);
         rdoA14 = (RadioButton) findViewById(R.id.rdoA14);
         rdoA15 = (RadioButton) findViewById(R.id.rdoA15);
         secA2=(LinearLayout)findViewById(R.id.secA2);
         lineA2=(View)findViewById(R.id.lineA2);
         VlblA2 = (TextView) findViewById(R.id.VlblA2);
         rdogrpA2 = (RadioGroup) findViewById(R.id.rdogrpA2);
         rdoA21 = (RadioButton) findViewById(R.id.rdoA21);
         rdoA22 = (RadioButton) findViewById(R.id.rdoA22);
         rdoA23 = (RadioButton) findViewById(R.id.rdoA23);
         rdoA24 = (RadioButton) findViewById(R.id.rdoA24);
         rdoA25 = (RadioButton) findViewById(R.id.rdoA25);
         secA3=(LinearLayout)findViewById(R.id.secA3);
         lineA3=(View)findViewById(R.id.lineA3);
         VlblA3 = (TextView) findViewById(R.id.VlblA3);
         rdogrpA3 = (RadioGroup) findViewById(R.id.rdogrpA3);
         rdoA31 = (RadioButton) findViewById(R.id.rdoA31);
         rdoA32 = (RadioButton) findViewById(R.id.rdoA32);
         rdoA33 = (RadioButton) findViewById(R.id.rdoA33);
         rdoA34 = (RadioButton) findViewById(R.id.rdoA34);
         rdoA35 = (RadioButton) findViewById(R.id.rdoA35);
         secA4=(LinearLayout)findViewById(R.id.secA4);
         lineA4=(View)findViewById(R.id.lineA4);
         VlblA4 = (TextView) findViewById(R.id.VlblA4);
         rdogrpA4 = (RadioGroup) findViewById(R.id.rdogrpA4);
         rdoA41 = (RadioButton) findViewById(R.id.rdoA41);
         rdoA42 = (RadioButton) findViewById(R.id.rdoA42);
         rdoA43 = (RadioButton) findViewById(R.id.rdoA43);
         rdoA44 = (RadioButton) findViewById(R.id.rdoA44);
         rdoA45 = (RadioButton) findViewById(R.id.rdoA45);
         secA5=(LinearLayout)findViewById(R.id.secA5);
         lineA5=(View)findViewById(R.id.lineA5);
         VlblA5 = (TextView) findViewById(R.id.VlblA5);
         rdogrpA5 = (RadioGroup) findViewById(R.id.rdogrpA5);
         rdoA51 = (RadioButton) findViewById(R.id.rdoA51);
         rdoA52 = (RadioButton) findViewById(R.id.rdoA52);
         rdoA53 = (RadioButton) findViewById(R.id.rdoA53);
         rdoA54 = (RadioButton) findViewById(R.id.rdoA54);
         rdoA55 = (RadioButton) findViewById(R.id.rdoA55);
         secA6=(LinearLayout)findViewById(R.id.secA6);
         lineA6=(View)findViewById(R.id.lineA6);
         VlblA6 = (TextView) findViewById(R.id.VlblA6);
         rdogrpA6 = (RadioGroup) findViewById(R.id.rdogrpA6);
         rdoA61 = (RadioButton) findViewById(R.id.rdoA61);
         rdoA62 = (RadioButton) findViewById(R.id.rdoA62);
         rdoA63 = (RadioButton) findViewById(R.id.rdoA63);
         rdoA64 = (RadioButton) findViewById(R.id.rdoA64);
         rdoA65 = (RadioButton) findViewById(R.id.rdoA65);
         secA7=(LinearLayout)findViewById(R.id.secA7);
         lineA7=(View)findViewById(R.id.lineA7);
         VlblA7 = (TextView) findViewById(R.id.VlblA7);
         rdogrpA7 = (RadioGroup) findViewById(R.id.rdogrpA7);
         rdoA71 = (RadioButton) findViewById(R.id.rdoA71);
         rdoA72 = (RadioButton) findViewById(R.id.rdoA72);
         rdoA73 = (RadioButton) findViewById(R.id.rdoA73);
         rdoA74 = (RadioButton) findViewById(R.id.rdoA74);
         rdoA75 = (RadioButton) findViewById(R.id.rdoA75);
         secA8=(LinearLayout)findViewById(R.id.secA8);
         lineA8=(View)findViewById(R.id.lineA8);
         VlblA8 = (TextView) findViewById(R.id.VlblA8);
         rdogrpA8 = (RadioGroup) findViewById(R.id.rdogrpA8);
         rdoA81 = (RadioButton) findViewById(R.id.rdoA81);
         rdoA82 = (RadioButton) findViewById(R.id.rdoA82);
         rdoA83 = (RadioButton) findViewById(R.id.rdoA83);
         rdoA84 = (RadioButton) findViewById(R.id.rdoA84);
         rdoA85 = (RadioButton) findViewById(R.id.rdoA85);
         secA9=(LinearLayout)findViewById(R.id.secA9);
         lineA9=(View)findViewById(R.id.lineA9);
         VlblA9 = (TextView) findViewById(R.id.VlblA9);
         rdogrpA9 = (RadioGroup) findViewById(R.id.rdogrpA9);
         rdoA91 = (RadioButton) findViewById(R.id.rdoA91);
         rdoA92 = (RadioButton) findViewById(R.id.rdoA92);
         rdoA93 = (RadioButton) findViewById(R.id.rdoA93);
         rdoA94 = (RadioButton) findViewById(R.id.rdoA94);
         rdoA95 = (RadioButton) findViewById(R.id.rdoA95);
         secA10=(LinearLayout)findViewById(R.id.secA10);
         lineA10=(View)findViewById(R.id.lineA10);
         VlblA10 = (TextView) findViewById(R.id.VlblA10);
         rdogrpA10 = (RadioGroup) findViewById(R.id.rdogrpA10);
         rdoA101 = (RadioButton) findViewById(R.id.rdoA101);
         rdoA102 = (RadioButton) findViewById(R.id.rdoA102);
         rdoA103 = (RadioButton) findViewById(R.id.rdoA103);
         rdoA104 = (RadioButton) findViewById(R.id.rdoA104);
         rdoA105 = (RadioButton) findViewById(R.id.rdoA105);
         secA11=(LinearLayout)findViewById(R.id.secA11);
         lineA11=(View)findViewById(R.id.lineA11);
         VlblA11 = (TextView) findViewById(R.id.VlblA11);
         rdogrpA11 = (RadioGroup) findViewById(R.id.rdogrpA11);
         rdoA111 = (RadioButton) findViewById(R.id.rdoA111);
         rdoA112 = (RadioButton) findViewById(R.id.rdoA112);
         rdoA113 = (RadioButton) findViewById(R.id.rdoA113);
         rdoA114 = (RadioButton) findViewById(R.id.rdoA114);
         rdoA115 = (RadioButton) findViewById(R.id.rdoA115);
         secA12=(LinearLayout)findViewById(R.id.secA12);
         lineA12=(View)findViewById(R.id.lineA12);
         VlblA12 = (TextView) findViewById(R.id.VlblA12);
         rdogrpA12 = (RadioGroup) findViewById(R.id.rdogrpA12);
         rdoA121 = (RadioButton) findViewById(R.id.rdoA121);
         rdoA122 = (RadioButton) findViewById(R.id.rdoA122);
         rdoA123 = (RadioButton) findViewById(R.id.rdoA123);
         rdoA124 = (RadioButton) findViewById(R.id.rdoA124);
         rdoA125 = (RadioButton) findViewById(R.id.rdoA125);
         secA13=(LinearLayout)findViewById(R.id.secA13);
         lineA13=(View)findViewById(R.id.lineA13);
         VlblA13 = (TextView) findViewById(R.id.VlblA13);
         rdogrpA13 = (RadioGroup) findViewById(R.id.rdogrpA13);
         rdoA131 = (RadioButton) findViewById(R.id.rdoA131);
         rdoA132 = (RadioButton) findViewById(R.id.rdoA132);
         rdoA133 = (RadioButton) findViewById(R.id.rdoA133);
         rdoA134 = (RadioButton) findViewById(R.id.rdoA134);
         rdoA135 = (RadioButton) findViewById(R.id.rdoA135);
         secA14=(LinearLayout)findViewById(R.id.secA14);
         lineA14=(View)findViewById(R.id.lineA14);
         VlblA14 = (TextView) findViewById(R.id.VlblA14);
         rdogrpA14 = (RadioGroup) findViewById(R.id.rdogrpA14);
         rdoA141 = (RadioButton) findViewById(R.id.rdoA141);
         rdoA142 = (RadioButton) findViewById(R.id.rdoA142);
         rdoA143 = (RadioButton) findViewById(R.id.rdoA143);
         rdoA144 = (RadioButton) findViewById(R.id.rdoA144);
         rdoA145 = (RadioButton) findViewById(R.id.rdoA145);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Provider.this, e.getMessage());
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
         	Connection.MessageBox(Provider.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Provider_DataModel objSave = new Provider_DataModel();
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setProviderID(txtProviderID.getText().toString());
         objSave.setName(txtName.getText().toString());
         objSave.setProfeTitle(txtProfeTitle.getText().toString());
         objSave.setWard(txtWard.getText().toString());
         objSave.setExpeMonth(txtExpeMonth.getText().toString());
         objSave.setExpeYear(txtExpeYear.getText().toString());
         objSave.setExpeFaciMonth(txtExpeFaciMonth.getText().toString());
         objSave.setExpeFaciYera(txtExpeFaciYera.getText().toString());
         objSave.setExpeWardMonth(txtExpeWardMonth.getText().toString());
         objSave.setExpeWardYera(txtExpeWardYera.getText().toString());
         objSave.setMobileNo(txtMobileNo.getText().toString());
         String[] d_rdogrpA1 = new String[] {"1","2","3","4","5"};
         objSave.setA1("");
         for (int i = 0; i < rdogrpA1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA1.getChildAt(i);
             if (rb.isChecked()) objSave.setA1(d_rdogrpA1[i]);
         }

         String[] d_rdogrpA2 = new String[] {"1","2","3","4","5"};
         objSave.setA2("");
         for (int i = 0; i < rdogrpA2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA2.getChildAt(i);
             if (rb.isChecked()) objSave.setA2(d_rdogrpA2[i]);
         }

         String[] d_rdogrpA3 = new String[] {"1","2","3","4","5"};
         objSave.setA3("");
         for (int i = 0; i < rdogrpA3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA3.getChildAt(i);
             if (rb.isChecked()) objSave.setA3(d_rdogrpA3[i]);
         }

         String[] d_rdogrpA4 = new String[] {"1","2","3","4","5"};
         objSave.setA4("");
         for (int i = 0; i < rdogrpA4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA4.getChildAt(i);
             if (rb.isChecked()) objSave.setA4(d_rdogrpA4[i]);
         }

         String[] d_rdogrpA5 = new String[] {"1","2","3","4","5"};
         objSave.setA5("");
         for (int i = 0; i < rdogrpA5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA5.getChildAt(i);
             if (rb.isChecked()) objSave.setA5(d_rdogrpA5[i]);
         }

         String[] d_rdogrpA6 = new String[] {"1","2","3","4","5"};
         objSave.setA6("");
         for (int i = 0; i < rdogrpA6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA6.getChildAt(i);
             if (rb.isChecked()) objSave.setA6(d_rdogrpA6[i]);
         }

         String[] d_rdogrpA7 = new String[] {"1","2","3","4","5"};
         objSave.setA7("");
         for (int i = 0; i < rdogrpA7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA7.getChildAt(i);
             if (rb.isChecked()) objSave.setA7(d_rdogrpA7[i]);
         }

         String[] d_rdogrpA8 = new String[] {"1","2","3","4","5"};
         objSave.setA8("");
         for (int i = 0; i < rdogrpA8.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA8.getChildAt(i);
             if (rb.isChecked()) objSave.setA8(d_rdogrpA8[i]);
         }

         String[] d_rdogrpA9 = new String[] {"1","2","3","4","5"};
         objSave.setA9("");
         for (int i = 0; i < rdogrpA9.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA9.getChildAt(i);
             if (rb.isChecked()) objSave.setA9(d_rdogrpA9[i]);
         }

         String[] d_rdogrpA10 = new String[] {"1","2","3","4","5"};
         objSave.setA10("");
         for (int i = 0; i < rdogrpA10.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA10.getChildAt(i);
             if (rb.isChecked()) objSave.setA10(d_rdogrpA10[i]);
         }

         String[] d_rdogrpA11 = new String[] {"1","2","3","4","5"};
         objSave.setA11("");
         for (int i = 0; i < rdogrpA11.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA11.getChildAt(i);
             if (rb.isChecked()) objSave.setA11(d_rdogrpA11[i]);
         }

         String[] d_rdogrpA12 = new String[] {"1","2","3","4","5"};
         objSave.setA12("");
         for (int i = 0; i < rdogrpA12.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA12.getChildAt(i);
             if (rb.isChecked()) objSave.setA12(d_rdogrpA12[i]);
         }

         String[] d_rdogrpA13 = new String[] {"1","2","3","4","5"};
         objSave.setA13("");
         for (int i = 0; i < rdogrpA13.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA13.getChildAt(i);
             if (rb.isChecked()) objSave.setA13(d_rdogrpA13[i]);
         }

         String[] d_rdogrpA14 = new String[] {"1","2","3","4","5"};
         objSave.setA14("");
         for (int i = 0; i < rdogrpA14.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpA14.getChildAt(i);
             if (rb.isChecked()) objSave.setA14(d_rdogrpA14[i]);
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

             Connection.MessageBox(Provider.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(Provider.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Provider.this, e.getMessage());
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
         if(txtFacilityID.getText().toString().length()==0 & secFacilityID.isShown())
           {
             ValidationMsg += "\nRequired field: Facility ID.";
             secFacilityID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtProviderID.getText().toString().length()==0 & secProviderID.isShown())
           {
             ValidationMsg += "\nRequired field: Provider ID.";
             secProviderID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtName.getText().toString().length()==0 & secName.isShown())
           {
             ValidationMsg += "\nRequired field: নাম:.";
             secName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtProfeTitle.getText().toString().length()==0 & secProfeTitle.isShown())
           {
             ValidationMsg += "\nRequired field: পেশাগত পদবী:.";
             secProfeTitle.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtWard.getText().toString().length()==0 & secWard.isShown())
           {
             ValidationMsg += "\nRequired field: কর্মরত ওয়ার্ড:.";
             secWard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secWard.isShown() & (Integer.valueOf(txtWard.getText().toString().length()==0 ? "1" : txtWard.getText().toString()) < 1 || Integer.valueOf(txtWard.getText().toString().length()==0 ? "99" : txtWard.getText().toString()) > 99))
           {
             ValidationMsg += "\nValue should be between 1 and 99(কর্মরত ওয়ার্ড:).";
             secWard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeMonth.getText().toString().length()==0 & secExpeMonth.isShown())
           {
             ValidationMsg += "\nRequired field: মাসে লিখুন.";
             secExpeMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeMonth.isShown() & (Integer.valueOf(txtExpeMonth.getText().toString().length()==0 ? "0" : txtExpeMonth.getText().toString()) < 0 || Integer.valueOf(txtExpeMonth.getText().toString().length()==0 ? "11" : txtExpeMonth.getText().toString()) > 11))
           {
             ValidationMsg += "\nValue should be between 0 and 11(মাসে লিখুন).";
             secExpeMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeYear.getText().toString().length()==0 & secExpeYear.isShown())
           {
             ValidationMsg += "\nRequired field: পূর্ণ বছর.";
             secExpeYear.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeYear.isShown() & (Integer.valueOf(txtExpeYear.getText().toString().length()==0 ? "0" : txtExpeYear.getText().toString()) < 0 || Integer.valueOf(txtExpeYear.getText().toString().length()==0 ? "99" : txtExpeYear.getText().toString()) > 99))
           {
             ValidationMsg += "\nValue should be between 0 and 99(পূর্ণ বছর).";
             secExpeYear.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeFaciMonth.getText().toString().length()==0 & secExpeFaciMonth.isShown())
           {
             ValidationMsg += "\nRequired field: মাসে লিখুন.";
             secExpeFaciMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeFaciMonth.isShown() & (Integer.valueOf(txtExpeFaciMonth.getText().toString().length()==0 ? "0" : txtExpeFaciMonth.getText().toString()) < 0 || Integer.valueOf(txtExpeFaciMonth.getText().toString().length()==0 ? "11" : txtExpeFaciMonth.getText().toString()) > 11))
           {
             ValidationMsg += "\nValue should be between 0 and 11(মাসে লিখুন).";
             secExpeFaciMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeFaciYera.getText().toString().length()==0 & secExpeFaciYera.isShown())
           {
             ValidationMsg += "\nRequired field: পূর্ণ বছর.";
             secExpeFaciYera.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeFaciYera.isShown() & (Integer.valueOf(txtExpeFaciYera.getText().toString().length()==0 ? "0" : txtExpeFaciYera.getText().toString()) < 0 || Integer.valueOf(txtExpeFaciYera.getText().toString().length()==0 ? "99" : txtExpeFaciYera.getText().toString()) > 99))
           {
             ValidationMsg += "\nValue should be between 0 and 99(পূর্ণ বছর).";
             secExpeFaciYera.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeWardMonth.getText().toString().length()==0 & secExpeWardMonth.isShown())
           {
             ValidationMsg += "\nRequired field: মাসে লিখুন.";
             secExpeWardMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeWardMonth.isShown() & (Integer.valueOf(txtExpeWardMonth.getText().toString().length()==0 ? "0" : txtExpeWardMonth.getText().toString()) < 0 || Integer.valueOf(txtExpeWardMonth.getText().toString().length()==0 ? "11" : txtExpeWardMonth.getText().toString()) > 11))
           {
             ValidationMsg += "\nValue should be between 0 and 11(মাসে লিখুন).";
             secExpeWardMonth.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeWardYera.getText().toString().length()==0 & secExpeWardYera.isShown())
           {
             ValidationMsg += "\nRequired field: পূর্ণ বছর.";
             secExpeWardYera.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secExpeWardYera.isShown() & (Integer.valueOf(txtExpeWardYera.getText().toString().length()==0 ? "0" : txtExpeWardYera.getText().toString()) < 0 || Integer.valueOf(txtExpeWardYera.getText().toString().length()==0 ? "99" : txtExpeWardYera.getText().toString()) > 99))
           {
             ValidationMsg += "\nValue should be between 0 and 99(পূর্ণ বছর).";
             secExpeWardYera.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtMobileNo.getText().toString().length()==0 & secMobileNo.isShown())
           {
             ValidationMsg += "\nRequired field: মোবাইল নাম্বার:.";
             secMobileNo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA11.isChecked() & !rdoA12.isChecked() & !rdoA13.isChecked() & !rdoA14.isChecked() & !rdoA15.isChecked() & secA1.isShown())
           {
             ValidationMsg += "\nA1. Required field: এই সেবা প্রতিষ্ঠানের বিদ্যমান অবকাঠামোর সাথে মন-স্বাস্থ্য কেন্দ্র্র মানানসই.";
             secA1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA21.isChecked() & !rdoA22.isChecked() & !rdoA23.isChecked() & !rdoA24.isChecked() & !rdoA25.isChecked() & secA2.isShown())
           {
             ValidationMsg += "\nA2. Required field: এই সেবা প্রতিষ্ঠানের বিদ্যমান পলিসি এবং পরিকল্পনার সাথে মন-স্বাস্থ্য কেন্দ্র মানানসই.";
             secA2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA31.isChecked() & !rdoA32.isChecked() & !rdoA33.isChecked() & !rdoA34.isChecked() & !rdoA35.isChecked() & secA3.isShown())
           {
             ValidationMsg += "\nA3. Required field: এই মন-স্বাস্থ্য কেন্দ্র সেবা গ্রহনকারীদের মানবিক অধিকার এবং গোপনীয়তা রক্ষা করে.";
             secA3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA41.isChecked() & !rdoA42.isChecked() & !rdoA43.isChecked() & !rdoA44.isChecked() & !rdoA45.isChecked() & secA4.isShown())
           {
             ValidationMsg += "\nA4. Required field: এই মন-স্বাস্থ্য কেন্দ্র স্থানীয়দের মানসিক স্বাস্থ্য সেবার চাহিদা পূরন করবে.";
             secA4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA51.isChecked() & !rdoA52.isChecked() & !rdoA53.isChecked() & !rdoA54.isChecked() & !rdoA55.isChecked() & secA5.isShown())
           {
             ValidationMsg += "\nA5. Required field: সেবাগ্রহনকারীরা এই মন-স্বাস্থ্য কেন্দ্র থেকে সুবিধা পাবে.";
             secA5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA61.isChecked() & !rdoA62.isChecked() & !rdoA63.isChecked() & !rdoA64.isChecked() & !rdoA65.isChecked() & secA6.isShown())
           {
             ValidationMsg += "\nA6. Required field: এই মন-স্বাস্থ্য কেন্দ্র স্থানীয়দের মধ্যে সচেতনতা বাড়াতে সহায়তা করবে.";
             secA6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA71.isChecked() & !rdoA72.isChecked() & !rdoA73.isChecked() & !rdoA74.isChecked() & !rdoA75.isChecked() & secA7.isShown())
           {
             ValidationMsg += "\nA7. Required field: এই সেবা প্রতিষ্ঠানে পর্যাপ্ত জনবল আছে এই মন-স্বাস্থ্য কেন্দ্র কে চালিয়ে যাবার জন্য.";
             secA7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA81.isChecked() & !rdoA82.isChecked() & !rdoA83.isChecked() & !rdoA84.isChecked() & !rdoA85.isChecked() & secA8.isShown())
           {
             ValidationMsg += "\nA8. Required field: এই সেবা প্রতিষ্ঠানে পর্যাপ্ত দক্ষ স্বাস্থ্য সেবা প্রদানকারী আছে যারা এই মন-স্বাস্থ্য কেন্দ্র চালিয়ে যেতে পারবে.";
             secA8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA91.isChecked() & !rdoA92.isChecked() & !rdoA93.isChecked() & !rdoA94.isChecked() & !rdoA95.isChecked() & secA9.isShown())
           {
             ValidationMsg += "\nA9. Required field: এই সেবা প্রতিষ্ঠানে মন-স্বাস্থ্য কেন্দ্র পরিচালনার জন্য ক্রমাগত তহবিল বরাদ্ধ করা দরকার.";
             secA9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA101.isChecked() & !rdoA102.isChecked() & !rdoA103.isChecked() & !rdoA104.isChecked() & !rdoA105.isChecked() & secA10.isShown())
           {
             ValidationMsg += "\nA10. Required field: মানুষ তাদের মানসিক সুস্থতার জন্য এই মন-স্বাস্থ্য কেন্দ্রকে ব্যবহার করবে.";
             secA10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA111.isChecked() & !rdoA112.isChecked() & !rdoA113.isChecked() & !rdoA114.isChecked() & !rdoA115.isChecked() & secA11.isShown())
           {
             ValidationMsg += "\nA11. Required field: আমি মনে করি এই মন-স্বাস্থ্য কেন্দ্র বাংলাদেশের অন্যান্য স্বাস্থ্য সেবা কেন্দ্রে চালু করা যেতে পারে.";
             secA11.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA121.isChecked() & !rdoA122.isChecked() & !rdoA123.isChecked() & !rdoA124.isChecked() & !rdoA125.isChecked() & secA12.isShown())
           {
             ValidationMsg += "\nA12. Required field: এই মন-স্বাস্থ্য কেন্দ্রটি এই স্বাস্থ্যকেন্দ্রে বাস্তবায়নযোগ্য.";
             secA12.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA131.isChecked() & !rdoA132.isChecked() & !rdoA133.isChecked() & !rdoA134.isChecked() & !rdoA135.isChecked() & secA13.isShown())
           {
             ValidationMsg += "\nA13. Required field: মন-স্বাস্থ্য কেন্দ্র পেসেন্টদের মনস্বাস্থ্য সেবার এক্সেস উন্নত করেছে.";
             secA13.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoA141.isChecked() & !rdoA142.isChecked() & !rdoA143.isChecked() & !rdoA144.isChecked() & !rdoA145.isChecked() & secA14.isShown())
           {
             ValidationMsg += "\nA14. Required field: মন-স্বাস্থ্য কেন্দ্র পেসেন্টদের মানসিক স্বাস্থ্য বিষয়ক সমস্যাগুলিকে সাহায্য করতে সক্ষম.";
             secA14.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secFacilityID.setBackgroundColor(Color.WHITE);
             secProviderID.setBackgroundColor(Color.WHITE);
             secName.setBackgroundColor(Color.WHITE);
             secProfeTitle.setBackgroundColor(Color.WHITE);
             secWard.setBackgroundColor(Color.WHITE);
             secWard.setBackgroundColor(Color.WHITE);
             secExpeMonth.setBackgroundColor(Color.WHITE);
             secExpeMonth.setBackgroundColor(Color.WHITE);
             secExpeYear.setBackgroundColor(Color.WHITE);
             secExpeYear.setBackgroundColor(Color.WHITE);
             secExpeFaciMonth.setBackgroundColor(Color.WHITE);
             secExpeFaciMonth.setBackgroundColor(Color.WHITE);
             secExpeFaciYera.setBackgroundColor(Color.WHITE);
             secExpeFaciYera.setBackgroundColor(Color.WHITE);
             secExpeWardMonth.setBackgroundColor(Color.WHITE);
             secExpeWardMonth.setBackgroundColor(Color.WHITE);
             secExpeWardYera.setBackgroundColor(Color.WHITE);
             secExpeWardYera.setBackgroundColor(Color.WHITE);
             secMobileNo.setBackgroundColor(Color.WHITE);
             secA1.setBackgroundColor(Color.WHITE);
             secA2.setBackgroundColor(Color.WHITE);
             secA3.setBackgroundColor(Color.WHITE);
             secA4.setBackgroundColor(Color.WHITE);
             secA5.setBackgroundColor(Color.WHITE);
             secA6.setBackgroundColor(Color.WHITE);
             secA7.setBackgroundColor(Color.WHITE);
             secA8.setBackgroundColor(Color.WHITE);
             secA9.setBackgroundColor(Color.WHITE);
             secA10.setBackgroundColor(Color.WHITE);
             secA11.setBackgroundColor(Color.WHITE);
             secA12.setBackgroundColor(Color.WHITE);
             secA13.setBackgroundColor(Color.WHITE);
             secA14.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String FacilityID, String ProviderID)
     {
       try
        {     
           RadioButton rb;
           Provider_DataModel d = new Provider_DataModel();
           String SQL = "Select * from "+ TableName +"  Where FacilityID='"+ FacilityID +"' and ProviderID='"+ ProviderID +"'";
           List<Provider_DataModel> data = d.SelectAll(this, SQL);
           for(Provider_DataModel item : data){
             txtFacilityID.setText(item.getFacilityID());
             txtProviderID.setText(item.getProviderID());
             txtName.setText(item.getName());
             txtProfeTitle.setText(item.getProfeTitle());
             txtWard.setText(String.valueOf(item.getWard()));
             txtExpeMonth.setText(String.valueOf(item.getExpeMonth()));
             txtExpeYear.setText(String.valueOf(item.getExpeYear()));
             txtExpeFaciMonth.setText(String.valueOf(item.getExpeFaciMonth()));
             txtExpeFaciYera.setText(String.valueOf(item.getExpeFaciYera()));
             txtExpeWardMonth.setText(String.valueOf(item.getExpeWardMonth()));
             txtExpeWardYera.setText(String.valueOf(item.getExpeWardYera()));
             txtMobileNo.setText(item.getMobileNo());
             String[] d_rdogrpA1 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA1.length; i++)
             {
                 if (String.valueOf(item.getA1()).equals(String.valueOf(d_rdogrpA1[i])))
                 {
                     rb = (RadioButton)rdogrpA1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA2 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA2.length; i++)
             {
                 if (String.valueOf(item.getA2()).equals(String.valueOf(d_rdogrpA2[i])))
                 {
                     rb = (RadioButton)rdogrpA2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA3 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA3.length; i++)
             {
                 if (String.valueOf(item.getA3()).equals(String.valueOf(d_rdogrpA3[i])))
                 {
                     rb = (RadioButton)rdogrpA3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA4 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA4.length; i++)
             {
                 if (String.valueOf(item.getA4()).equals(String.valueOf(d_rdogrpA4[i])))
                 {
                     rb = (RadioButton)rdogrpA4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA5 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA5.length; i++)
             {
                 if (String.valueOf(item.getA5()).equals(String.valueOf(d_rdogrpA5[i])))
                 {
                     rb = (RadioButton)rdogrpA5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA6 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA6.length; i++)
             {
                 if (String.valueOf(item.getA6()).equals(String.valueOf(d_rdogrpA6[i])))
                 {
                     rb = (RadioButton)rdogrpA6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA7 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA7.length; i++)
             {
                 if (String.valueOf(item.getA7()).equals(String.valueOf(d_rdogrpA7[i])))
                 {
                     rb = (RadioButton)rdogrpA7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA8 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA8.length; i++)
             {
                 if (String.valueOf(item.getA8()).equals(String.valueOf(d_rdogrpA8[i])))
                 {
                     rb = (RadioButton)rdogrpA8.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA9 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA9.length; i++)
             {
                 if (String.valueOf(item.getA9()).equals(String.valueOf(d_rdogrpA9[i])))
                 {
                     rb = (RadioButton)rdogrpA9.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA10 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA10.length; i++)
             {
                 if (String.valueOf(item.getA10()).equals(String.valueOf(d_rdogrpA10[i])))
                 {
                     rb = (RadioButton)rdogrpA10.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA11 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA11.length; i++)
             {
                 if (String.valueOf(item.getA11()).equals(String.valueOf(d_rdogrpA11[i])))
                 {
                     rb = (RadioButton)rdogrpA11.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA12 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA12.length; i++)
             {
                 if (String.valueOf(item.getA12()).equals(String.valueOf(d_rdogrpA12[i])))
                 {
                     rb = (RadioButton)rdogrpA12.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA13 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA13.length; i++)
             {
                 if (String.valueOf(item.getA13()).equals(String.valueOf(d_rdogrpA13[i])))
                 {
                     rb = (RadioButton)rdogrpA13.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpA14 = new String[] {"1","2","3","4","5"};
             for (int i = 0; i < d_rdogrpA14.length; i++)
             {
                 if (String.valueOf(item.getA14()).equals(String.valueOf(d_rdogrpA14[i])))
                 {
                     rb = (RadioButton)rdogrpA14.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Provider.this, e.getMessage());
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