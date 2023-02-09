
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

 import forms_datamodel.Women_DataModel;

 public class Women extends AppCompatActivity {
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
    LinearLayout secWomenID;
    View lineWomenID;
    TextView VlblWomenID;
    EditText txtWomenID;
    LinearLayout seclbl01;
    View linelbl01;
    LinearLayout seclbl02;
    View linelbl02;
    LinearLayout seclbl03;
    View linelbl03;
    LinearLayout secQ1;
    View lineQ1;
    TextView VlblQ1;
    RadioGroup rdogrpQ1;
    RadioButton rdoQ11;
    RadioButton rdoQ12;
    LinearLayout secQ2;
    View lineQ2;
    TextView VlblQ2;
    RadioGroup rdogrpQ2;
    RadioButton rdoQ21;
    RadioButton rdoQ22;
    LinearLayout secQ3;
    View lineQ3;
    TextView VlblQ3;
    RadioGroup rdogrpQ3;
    RadioButton rdoQ31;
    RadioButton rdoQ32;
    LinearLayout secQ4;
    View lineQ4;
    TextView VlblQ4;
    RadioGroup rdogrpQ4;
    RadioButton rdoQ41;
    RadioButton rdoQ42;
    LinearLayout secQ5;
    View lineQ5;
    TextView VlblQ5;
    RadioGroup rdogrpQ5;
    RadioButton rdoQ51;
    RadioButton rdoQ52;
    LinearLayout secQ6;
    View lineQ6;
    TextView VlblQ6;
    RadioGroup rdogrpQ6;
    RadioButton rdoQ61;
    RadioButton rdoQ62;
    LinearLayout secQ7;
    View lineQ7;
    TextView VlblQ7;
    RadioGroup rdogrpQ7;
    RadioButton rdoQ71;
    RadioButton rdoQ72;
    LinearLayout secQ8;
    View lineQ8;
    TextView VlblQ8;
    RadioGroup rdogrpQ8;
    RadioButton rdoQ81;
    RadioButton rdoQ82;
    LinearLayout secQ9;
    View lineQ9;
    TextView VlblQ9;
    RadioGroup rdogrpQ9;
    RadioButton rdoQ91;
    RadioButton rdoQ92;
    LinearLayout seclbl04;
    View linelbl04;
    LinearLayout secQ10;
    View lineQ10;
    TextView VlblQ10;
    RadioGroup rdogrpQ10;
    RadioButton rdoQ101;
    RadioButton rdoQ102;
    RadioButton rdoQ103;
    RadioButton rdoQ104;
    LinearLayout seclbl05;
    View linelbl05;
    LinearLayout secQ11;
    View lineQ11;
    TextView VlblQ11;
    RadioGroup rdogrpQ11;
    RadioButton rdoQ111;
    RadioButton rdoQ112;
    LinearLayout secQ12;
    View lineQ12;
    TextView VlblQ12;
    RadioGroup rdogrpQ12;
    RadioButton rdoQ121;
    RadioButton rdoQ122;
    LinearLayout secQ13;
    View lineQ13;
    TextView VlblQ13;
    RadioGroup rdogrpQ13;
    RadioButton rdoQ131;
    RadioButton rdoQ132;
    LinearLayout secQ14;
    View lineQ14;
    TextView VlblQ14;
    RadioGroup rdogrpQ14;
    RadioButton rdoQ141;
    RadioButton rdoQ142;
    LinearLayout secQ15;
    View lineQ15;
    TextView VlblQ15;
    RadioGroup rdogrpQ15;
    RadioButton rdoQ151;
    RadioButton rdoQ152;
    LinearLayout secQ16;
    View lineQ16;
    TextView VlblQ16;
    RadioGroup rdogrpQ16;
    RadioButton rdoQ161;
    RadioButton rdoQ162;
    LinearLayout secQ17;
    View lineQ17;
    TextView VlblQ17;
    RadioGroup rdogrpQ17;
    RadioButton rdoQ171;
    RadioButton rdoQ172;
    LinearLayout secQ18;
    View lineQ18;
    TextView VlblQ18;
    RadioGroup rdogrpQ18;
    RadioButton rdoQ181;
    RadioButton rdoQ182;
    LinearLayout secQ19;
    View lineQ19;
    TextView VlblQ19;
    RadioGroup rdogrpQ19;
    RadioButton rdoQ191;
    RadioButton rdoQ192;
    LinearLayout secQ20;
    View lineQ20;
    TextView VlblQ20;
    RadioGroup rdogrpQ20;
    RadioButton rdoQ201;
    RadioButton rdoQ202;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String WOMENID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.women);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         WOMENID = IDbundle.getString("WomenID");

         TableName = "Women";
         MODULEID = 11;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Women.this);
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
        Connection.LocalizeLanguage(Women.this, MODULEID, LANGUAGEID);





         //Hide all skip variables


        DataSearch(WOMENID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Women.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         secWomenID=(LinearLayout)findViewById(R.id.secWomenID);
         lineWomenID=(View)findViewById(R.id.lineWomenID);
         VlblWomenID=(TextView) findViewById(R.id.VlblWomenID);
         txtWomenID=(EditText) findViewById(R.id.txtWomenID);
         if(WOMENID.length()==0) txtWomenID.setText(Global.GetDATE_ID(DEVICEID));
         else txtWomenID.setText(WOMENID);
         txtWomenID.setEnabled(false);

         seclbl01=(LinearLayout)findViewById(R.id.seclbl01);
         linelbl01=(View)findViewById(R.id.linelbl01);
         seclbl02=(LinearLayout)findViewById(R.id.seclbl02);
         linelbl02=(View)findViewById(R.id.linelbl02);
         seclbl03=(LinearLayout)findViewById(R.id.seclbl03);
         linelbl03=(View)findViewById(R.id.linelbl03);
         secQ1=(LinearLayout)findViewById(R.id.secQ1);
         lineQ1=(View)findViewById(R.id.lineQ1);
         VlblQ1 = (TextView) findViewById(R.id.VlblQ1);
         rdogrpQ1 = (RadioGroup) findViewById(R.id.rdogrpQ1);
         rdoQ11 = (RadioButton) findViewById(R.id.rdoQ11);
         rdoQ12 = (RadioButton) findViewById(R.id.rdoQ12);
         secQ2=(LinearLayout)findViewById(R.id.secQ2);
         lineQ2=(View)findViewById(R.id.lineQ2);
         VlblQ2 = (TextView) findViewById(R.id.VlblQ2);
         rdogrpQ2 = (RadioGroup) findViewById(R.id.rdogrpQ2);
         rdoQ21 = (RadioButton) findViewById(R.id.rdoQ21);
         rdoQ22 = (RadioButton) findViewById(R.id.rdoQ22);
         secQ3=(LinearLayout)findViewById(R.id.secQ3);
         lineQ3=(View)findViewById(R.id.lineQ3);
         VlblQ3 = (TextView) findViewById(R.id.VlblQ3);
         rdogrpQ3 = (RadioGroup) findViewById(R.id.rdogrpQ3);
         rdoQ31 = (RadioButton) findViewById(R.id.rdoQ31);
         rdoQ32 = (RadioButton) findViewById(R.id.rdoQ32);
         secQ4=(LinearLayout)findViewById(R.id.secQ4);
         lineQ4=(View)findViewById(R.id.lineQ4);
         VlblQ4 = (TextView) findViewById(R.id.VlblQ4);
         rdogrpQ4 = (RadioGroup) findViewById(R.id.rdogrpQ4);
         rdoQ41 = (RadioButton) findViewById(R.id.rdoQ41);
         rdoQ42 = (RadioButton) findViewById(R.id.rdoQ42);
         secQ5=(LinearLayout)findViewById(R.id.secQ5);
         lineQ5=(View)findViewById(R.id.lineQ5);
         VlblQ5 = (TextView) findViewById(R.id.VlblQ5);
         rdogrpQ5 = (RadioGroup) findViewById(R.id.rdogrpQ5);
         rdoQ51 = (RadioButton) findViewById(R.id.rdoQ51);
         rdoQ52 = (RadioButton) findViewById(R.id.rdoQ52);
         secQ6=(LinearLayout)findViewById(R.id.secQ6);
         lineQ6=(View)findViewById(R.id.lineQ6);
         VlblQ6 = (TextView) findViewById(R.id.VlblQ6);
         rdogrpQ6 = (RadioGroup) findViewById(R.id.rdogrpQ6);
         rdoQ61 = (RadioButton) findViewById(R.id.rdoQ61);
         rdoQ62 = (RadioButton) findViewById(R.id.rdoQ62);
         secQ7=(LinearLayout)findViewById(R.id.secQ7);
         lineQ7=(View)findViewById(R.id.lineQ7);
         VlblQ7 = (TextView) findViewById(R.id.VlblQ7);
         rdogrpQ7 = (RadioGroup) findViewById(R.id.rdogrpQ7);
         rdoQ71 = (RadioButton) findViewById(R.id.rdoQ71);
         rdoQ72 = (RadioButton) findViewById(R.id.rdoQ72);
         secQ8=(LinearLayout)findViewById(R.id.secQ8);
         lineQ8=(View)findViewById(R.id.lineQ8);
         VlblQ8 = (TextView) findViewById(R.id.VlblQ8);
         rdogrpQ8 = (RadioGroup) findViewById(R.id.rdogrpQ8);
         rdoQ81 = (RadioButton) findViewById(R.id.rdoQ81);
         rdoQ82 = (RadioButton) findViewById(R.id.rdoQ82);
         secQ9=(LinearLayout)findViewById(R.id.secQ9);
         lineQ9=(View)findViewById(R.id.lineQ9);
         VlblQ9 = (TextView) findViewById(R.id.VlblQ9);
         rdogrpQ9 = (RadioGroup) findViewById(R.id.rdogrpQ9);
         rdoQ91 = (RadioButton) findViewById(R.id.rdoQ91);
         rdoQ92 = (RadioButton) findViewById(R.id.rdoQ92);
         seclbl04=(LinearLayout)findViewById(R.id.seclbl04);
         linelbl04=(View)findViewById(R.id.linelbl04);
         secQ10=(LinearLayout)findViewById(R.id.secQ10);
         lineQ10=(View)findViewById(R.id.lineQ10);
         VlblQ10 = (TextView) findViewById(R.id.VlblQ10);
         rdogrpQ10 = (RadioGroup) findViewById(R.id.rdogrpQ10);
         rdoQ101 = (RadioButton) findViewById(R.id.rdoQ101);
         rdoQ102 = (RadioButton) findViewById(R.id.rdoQ102);
         rdoQ103 = (RadioButton) findViewById(R.id.rdoQ103);
         rdoQ104 = (RadioButton) findViewById(R.id.rdoQ104);
         seclbl05=(LinearLayout)findViewById(R.id.seclbl05);
         linelbl05=(View)findViewById(R.id.linelbl05);
         secQ11=(LinearLayout)findViewById(R.id.secQ11);
         lineQ11=(View)findViewById(R.id.lineQ11);
         VlblQ11 = (TextView) findViewById(R.id.VlblQ11);
         rdogrpQ11 = (RadioGroup) findViewById(R.id.rdogrpQ11);
         rdoQ111 = (RadioButton) findViewById(R.id.rdoQ111);
         rdoQ112 = (RadioButton) findViewById(R.id.rdoQ112);
         secQ12=(LinearLayout)findViewById(R.id.secQ12);
         lineQ12=(View)findViewById(R.id.lineQ12);
         VlblQ12 = (TextView) findViewById(R.id.VlblQ12);
         rdogrpQ12 = (RadioGroup) findViewById(R.id.rdogrpQ12);
         rdoQ121 = (RadioButton) findViewById(R.id.rdoQ121);
         rdoQ122 = (RadioButton) findViewById(R.id.rdoQ122);
         secQ13=(LinearLayout)findViewById(R.id.secQ13);
         lineQ13=(View)findViewById(R.id.lineQ13);
         VlblQ13 = (TextView) findViewById(R.id.VlblQ13);
         rdogrpQ13 = (RadioGroup) findViewById(R.id.rdogrpQ13);
         rdoQ131 = (RadioButton) findViewById(R.id.rdoQ131);
         rdoQ132 = (RadioButton) findViewById(R.id.rdoQ132);
         secQ14=(LinearLayout)findViewById(R.id.secQ14);
         lineQ14=(View)findViewById(R.id.lineQ14);
         VlblQ14 = (TextView) findViewById(R.id.VlblQ14);
         rdogrpQ14 = (RadioGroup) findViewById(R.id.rdogrpQ14);
         rdoQ141 = (RadioButton) findViewById(R.id.rdoQ141);
         rdoQ142 = (RadioButton) findViewById(R.id.rdoQ142);
         secQ15=(LinearLayout)findViewById(R.id.secQ15);
         lineQ15=(View)findViewById(R.id.lineQ15);
         VlblQ15 = (TextView) findViewById(R.id.VlblQ15);
         rdogrpQ15 = (RadioGroup) findViewById(R.id.rdogrpQ15);
         rdoQ151 = (RadioButton) findViewById(R.id.rdoQ151);
         rdoQ152 = (RadioButton) findViewById(R.id.rdoQ152);
         secQ16=(LinearLayout)findViewById(R.id.secQ16);
         lineQ16=(View)findViewById(R.id.lineQ16);
         VlblQ16 = (TextView) findViewById(R.id.VlblQ16);
         rdogrpQ16 = (RadioGroup) findViewById(R.id.rdogrpQ16);
         rdoQ161 = (RadioButton) findViewById(R.id.rdoQ161);
         rdoQ162 = (RadioButton) findViewById(R.id.rdoQ162);
         secQ17=(LinearLayout)findViewById(R.id.secQ17);
         lineQ17=(View)findViewById(R.id.lineQ17);
         VlblQ17 = (TextView) findViewById(R.id.VlblQ17);
         rdogrpQ17 = (RadioGroup) findViewById(R.id.rdogrpQ17);
         rdoQ171 = (RadioButton) findViewById(R.id.rdoQ171);
         rdoQ172 = (RadioButton) findViewById(R.id.rdoQ172);
         secQ18=(LinearLayout)findViewById(R.id.secQ18);
         lineQ18=(View)findViewById(R.id.lineQ18);
         VlblQ18 = (TextView) findViewById(R.id.VlblQ18);
         rdogrpQ18 = (RadioGroup) findViewById(R.id.rdogrpQ18);
         rdoQ181 = (RadioButton) findViewById(R.id.rdoQ181);
         rdoQ182 = (RadioButton) findViewById(R.id.rdoQ182);
         secQ19=(LinearLayout)findViewById(R.id.secQ19);
         lineQ19=(View)findViewById(R.id.lineQ19);
         VlblQ19 = (TextView) findViewById(R.id.VlblQ19);
         rdogrpQ19 = (RadioGroup) findViewById(R.id.rdogrpQ19);
         rdoQ191 = (RadioButton) findViewById(R.id.rdoQ191);
         rdoQ192 = (RadioButton) findViewById(R.id.rdoQ192);
         secQ20=(LinearLayout)findViewById(R.id.secQ20);
         lineQ20=(View)findViewById(R.id.lineQ20);
         VlblQ20 = (TextView) findViewById(R.id.VlblQ20);
         rdogrpQ20 = (RadioGroup) findViewById(R.id.rdogrpQ20);
         rdoQ201 = (RadioButton) findViewById(R.id.rdoQ201);
         rdoQ202 = (RadioButton) findViewById(R.id.rdoQ202);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Women.this, e.getMessage());
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
         	Connection.MessageBox(Women.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Women_DataModel objSave = new Women_DataModel();
         objSave.setWomenID(txtWomenID.getText().toString());
         String[] d_rdogrpQ1 = new String[] {"1","2"};
         objSave.setQ1("");
         for (int i = 0; i < rdogrpQ1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ1.getChildAt(i);
             if (rb.isChecked()) objSave.setQ1(d_rdogrpQ1[i]);
         }

         String[] d_rdogrpQ2 = new String[] {"1","2"};
         objSave.setQ2("");
         for (int i = 0; i < rdogrpQ2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ2.getChildAt(i);
             if (rb.isChecked()) objSave.setQ2(d_rdogrpQ2[i]);
         }

         String[] d_rdogrpQ3 = new String[] {"1","2"};
         objSave.setQ3("");
         for (int i = 0; i < rdogrpQ3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ3.getChildAt(i);
             if (rb.isChecked()) objSave.setQ3(d_rdogrpQ3[i]);
         }

         String[] d_rdogrpQ4 = new String[] {"1","2"};
         objSave.setQ4("");
         for (int i = 0; i < rdogrpQ4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ4.getChildAt(i);
             if (rb.isChecked()) objSave.setQ4(d_rdogrpQ4[i]);
         }

         String[] d_rdogrpQ5 = new String[] {"1","2"};
         objSave.setQ5("");
         for (int i = 0; i < rdogrpQ5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ5.getChildAt(i);
             if (rb.isChecked()) objSave.setQ5(d_rdogrpQ5[i]);
         }

         String[] d_rdogrpQ6 = new String[] {"1","2"};
         objSave.setQ6("");
         for (int i = 0; i < rdogrpQ6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ6.getChildAt(i);
             if (rb.isChecked()) objSave.setQ6(d_rdogrpQ6[i]);
         }

         String[] d_rdogrpQ7 = new String[] {"1","2"};
         objSave.setQ7("");
         for (int i = 0; i < rdogrpQ7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ7.getChildAt(i);
             if (rb.isChecked()) objSave.setQ7(d_rdogrpQ7[i]);
         }

         String[] d_rdogrpQ8 = new String[] {"1","2"};
         objSave.setQ8("");
         for (int i = 0; i < rdogrpQ8.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ8.getChildAt(i);
             if (rb.isChecked()) objSave.setQ8(d_rdogrpQ8[i]);
         }

         String[] d_rdogrpQ9 = new String[] {"1","2"};
         objSave.setQ9("");
         for (int i = 0; i < rdogrpQ9.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ9.getChildAt(i);
             if (rb.isChecked()) objSave.setQ9(d_rdogrpQ9[i]);
         }

         String[] d_rdogrpQ10 = new String[] {"1","2","3","4"};
         objSave.setQ10("");
         for (int i = 0; i < rdogrpQ10.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ10.getChildAt(i);
             if (rb.isChecked()) objSave.setQ10(d_rdogrpQ10[i]);
         }

         String[] d_rdogrpQ11 = new String[] {"1","2"};
         objSave.setQ11("");
         for (int i = 0; i < rdogrpQ11.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ11.getChildAt(i);
             if (rb.isChecked()) objSave.setQ11(d_rdogrpQ11[i]);
         }

         String[] d_rdogrpQ12 = new String[] {"1","2"};
         objSave.setQ12("");
         for (int i = 0; i < rdogrpQ12.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ12.getChildAt(i);
             if (rb.isChecked()) objSave.setQ12(d_rdogrpQ12[i]);
         }

         String[] d_rdogrpQ13 = new String[] {"1","2"};
         objSave.setQ13("");
         for (int i = 0; i < rdogrpQ13.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ13.getChildAt(i);
             if (rb.isChecked()) objSave.setQ13(d_rdogrpQ13[i]);
         }

         String[] d_rdogrpQ14 = new String[] {"1","2"};
         objSave.setQ14("");
         for (int i = 0; i < rdogrpQ14.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ14.getChildAt(i);
             if (rb.isChecked()) objSave.setQ14(d_rdogrpQ14[i]);
         }

         String[] d_rdogrpQ15 = new String[] {"1","2"};
         objSave.setQ15("");
         for (int i = 0; i < rdogrpQ15.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ15.getChildAt(i);
             if (rb.isChecked()) objSave.setQ15(d_rdogrpQ15[i]);
         }

         String[] d_rdogrpQ16 = new String[] {"1","2"};
         objSave.setQ16("");
         for (int i = 0; i < rdogrpQ16.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ16.getChildAt(i);
             if (rb.isChecked()) objSave.setQ16(d_rdogrpQ16[i]);
         }

         String[] d_rdogrpQ17 = new String[] {"1","2"};
         objSave.setQ17("");
         for (int i = 0; i < rdogrpQ17.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ17.getChildAt(i);
             if (rb.isChecked()) objSave.setQ17(d_rdogrpQ17[i]);
         }

         String[] d_rdogrpQ18 = new String[] {"1","2"};
         objSave.setQ18("");
         for (int i = 0; i < rdogrpQ18.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ18.getChildAt(i);
             if (rb.isChecked()) objSave.setQ18(d_rdogrpQ18[i]);
         }

         String[] d_rdogrpQ19 = new String[] {"1","2"};
         objSave.setQ19("");
         for (int i = 0; i < rdogrpQ19.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ19.getChildAt(i);
             if (rb.isChecked()) objSave.setQ19(d_rdogrpQ19[i]);
         }

         String[] d_rdogrpQ20 = new String[] {"1","2"};
         objSave.setQ20("");
         for (int i = 0; i < rdogrpQ20.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpQ20.getChildAt(i);
             if (rb.isChecked()) objSave.setQ20(d_rdogrpQ20[i]);
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

             Connection.MessageBox(Women.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(Women.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Women.this, e.getMessage());
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
         if(txtWomenID.getText().toString().length()==0 & secWomenID.isShown())
           {
             ValidationMsg += "\nRequired field: Women ID.";
             secWomenID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ11.isChecked() & !rdoQ12.isChecked() & secQ1.isShown())
           {
             ValidationMsg += "\n1. Required field: আপনি কি জানেন যে প্রজনন এবং যৌন স্বাস্থ্য অধিকার মানবাধিকারের উপাদানগুলির অংশ.";
             secQ1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ21.isChecked() & !rdoQ22.isChecked() & secQ2.isShown())
           {
             ValidationMsg += "\n2. Required field: আপনি কি বিবাহের বৈধ ন্যূনতম বয়স জানেন? (নম্বর জিজ্ঞাসা করুন এবং ভেরিফাই করুন).";
             secQ2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ31.isChecked() & !rdoQ32.isChecked() & secQ3.isShown())
           {
             ValidationMsg += "\n3. Required field: আপনি কি স্বাস্থ্য কেন্দ্রে নিরাপদ গর্ভপাত এবং গর্ভপাতের পরে মায়ের যত্ন নেওয়ার কথা জানেন?.";
             secQ3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ41.isChecked() & !rdoQ42.isChecked() & secQ4.isShown())
           {
             ValidationMsg += "\n4. Required field: আপনি কি পরিবার পরিকল্পনা পদ্ধতির তথ্য ও সেবা সম্পর্কে জানেন?.";
             secQ4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ51.isChecked() & !rdoQ52.isChecked() & secQ5.isShown())
           {
             ValidationMsg += "\n5. Required field: আপনার গর্ভকালীন এবং যৌন স্বাস্থ্য সেবা নেয়ার অধিকার আছে, তা সম্পর্কে জানেন?.";
             secQ5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ61.isChecked() & !rdoQ62.isChecked() & secQ6.isShown())
           {
             ValidationMsg += "\n6. Required field: আপনি কি জানেন যে প্রতিটি মহিলার যৌন নির্যাতন, প্রজনন ক্ষতি এবং যৌন বৈষম্য থেকে সুরক্ষার অধিকার রয়েছে?.";
             secQ6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ71.isChecked() & !rdoQ72.isChecked() & secQ7.isShown())
           {
             ValidationMsg += "\n7. Required field: আপনি কি গর্ভকালীন এবং যৌন স্বাস্থ্য সেবা নেয়ার তথ্য এবং শিক্ষার অধিকার জানেন?.";
             secQ7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ81.isChecked() & !rdoQ82.isChecked() & secQ8.isShown())
           {
             ValidationMsg += "\n8. Required field: আপনি কি সম্মতিক্রমে বিয়ে করার অধিকার জানেন?.";
             secQ8.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ91.isChecked() & !rdoQ92.isChecked() & secQ9.isShown())
           {
             ValidationMsg += "\n9. Required field: সন্তান নিতে হবে কিনা এবং কখন নিতে হবে তা সিদ্ধান্ত নেওয়ার অধিকার জানেন?.";
             secQ9.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ101.isChecked() & !rdoQ102.isChecked() & !rdoQ103.isChecked() & !rdoQ104.isChecked() & secQ10.isShown())
           {
             ValidationMsg += "\n10. Required field: আপনি কতবার ফেসমাস্ক ব্যবহার করেন?.";
             secQ10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ111.isChecked() & !rdoQ112.isChecked() & secQ11.isShown())
           {
             ValidationMsg += "\n11. Required field: কোভিড-১৯ কি বাতাসের মাধ্যমে ছড়ায়?.";
             secQ11.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ121.isChecked() & !rdoQ122.isChecked() & secQ12.isShown())
           {
             ValidationMsg += "\n12. Required field: আপনি কি জানেন যে সংক্রমণের 3-14 দিনের মধ্যে কোভিড -19 এর লক্ষণগুলি দৃশ্যমান হয়ে ওঠে?.";
             secQ12.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ131.isChecked() & !rdoQ132.isChecked() & secQ13.isShown())
           {
             ValidationMsg += "\n13. Required field: আপনি কি জানেন যে সাধারণত একজন কোভিড-১৯ রোগী ২৮ দিনের মধ্যে সুস্থ হয়ে ওঠেন?.";
             secQ13.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ141.isChecked() & !rdoQ142.isChecked() & secQ14.isShown())
           {
             ValidationMsg += "\n14. Required field: কোভিড-১৯ থেকে সুস্থ হয়ে ওঠার পর কি কেউ সংক্রমিত হতে পারেন?.";
             secQ14.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ151.isChecked() & !rdoQ152.isChecked() & secQ15.isShown())
           {
             ValidationMsg += "\n15. Required field: কোভিড-১৯ এর চিকিৎসা কি ঘরে বসেই করা যায়?.";
             secQ15.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ161.isChecked() & !rdoQ162.isChecked() & secQ16.isShown())
           {
             ValidationMsg += "\n16. Required field: ফেসমাস্ক ব্যবহারের মাধ্যমে কি কোভিড-১৯ প্রতিরোধ করা যায়?.";
             secQ16.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ171.isChecked() & !rdoQ172.isChecked() & secQ17.isShown())
           {
             ValidationMsg += "\n17. Required field: যথাযথ জীবাণুমুক্তকরণ ছাড়া, কাপড়ের মাস্ক ব্যবহার সংক্রমণের ঝুঁকি বাড়িয়ে তুলতে পারে?.";
             secQ17.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ181.isChecked() & !rdoQ182.isChecked() & secQ18.isShown())
           {
             ValidationMsg += "\n18. Required field: ঘন ঘন হাত ধোয়া কি কোভিড-১৯ প্রতিরোধ করতে পারে?.";
             secQ18.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ191.isChecked() & !rdoQ192.isChecked() & secQ19.isShown())
           {
             ValidationMsg += "\n19. Required field: সংক্রমিত ব্যক্তি থেকে ১ মিটার বা ৩ ফুট দূরত্ব বজায় রাখা কি কোভিড-১৯ প্রতিরোধে সহায়ক?.";
             secQ19.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoQ201.isChecked() & !rdoQ202.isChecked() & secQ20.isShown())
           {
             ValidationMsg += "\n20. Required field: স্যানিটাইজার/সাবান/ডিটারজেন্ট দিয়ে অফিস, বাসা এবং নিয়মিত ব্যবহারের জিনিস পরিষ্কার করা কি কোভিড-১৯ এর ঝুঁকি কমাতে পারে?.";
             secQ20.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secWomenID.setBackgroundColor(Color.WHITE);
             secQ1.setBackgroundColor(Color.WHITE);
             secQ2.setBackgroundColor(Color.WHITE);
             secQ3.setBackgroundColor(Color.WHITE);
             secQ4.setBackgroundColor(Color.WHITE);
             secQ5.setBackgroundColor(Color.WHITE);
             secQ6.setBackgroundColor(Color.WHITE);
             secQ7.setBackgroundColor(Color.WHITE);
             secQ8.setBackgroundColor(Color.WHITE);
             secQ9.setBackgroundColor(Color.WHITE);
             secQ10.setBackgroundColor(Color.WHITE);
             secQ11.setBackgroundColor(Color.WHITE);
             secQ12.setBackgroundColor(Color.WHITE);
             secQ13.setBackgroundColor(Color.WHITE);
             secQ14.setBackgroundColor(Color.WHITE);
             secQ15.setBackgroundColor(Color.WHITE);
             secQ16.setBackgroundColor(Color.WHITE);
             secQ17.setBackgroundColor(Color.WHITE);
             secQ18.setBackgroundColor(Color.WHITE);
             secQ19.setBackgroundColor(Color.WHITE);
             secQ20.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String WomenID)
     {
       try
        {     
           RadioButton rb;
           Women_DataModel d = new Women_DataModel();
           String SQL = "Select * from "+ TableName +"  Where WomenID='"+ WomenID +"'";
           List<Women_DataModel> data = d.SelectAll(this, SQL);
           for(Women_DataModel item : data){
             txtWomenID.setText(item.getWomenID());
             String[] d_rdogrpQ1 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ1.length; i++)
             {
                 if (String.valueOf(item.getQ1()).equals(String.valueOf(d_rdogrpQ1[i])))
                 {
                     rb = (RadioButton)rdogrpQ1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ2 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ2.length; i++)
             {
                 if (String.valueOf(item.getQ2()).equals(String.valueOf(d_rdogrpQ2[i])))
                 {
                     rb = (RadioButton)rdogrpQ2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ3 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ3.length; i++)
             {
                 if (String.valueOf(item.getQ3()).equals(String.valueOf(d_rdogrpQ3[i])))
                 {
                     rb = (RadioButton)rdogrpQ3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ4 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ4.length; i++)
             {
                 if (String.valueOf(item.getQ4()).equals(String.valueOf(d_rdogrpQ4[i])))
                 {
                     rb = (RadioButton)rdogrpQ4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ5 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ5.length; i++)
             {
                 if (String.valueOf(item.getQ5()).equals(String.valueOf(d_rdogrpQ5[i])))
                 {
                     rb = (RadioButton)rdogrpQ5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ6 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ6.length; i++)
             {
                 if (String.valueOf(item.getQ6()).equals(String.valueOf(d_rdogrpQ6[i])))
                 {
                     rb = (RadioButton)rdogrpQ6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ7 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ7.length; i++)
             {
                 if (String.valueOf(item.getQ7()).equals(String.valueOf(d_rdogrpQ7[i])))
                 {
                     rb = (RadioButton)rdogrpQ7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ8 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ8.length; i++)
             {
                 if (String.valueOf(item.getQ8()).equals(String.valueOf(d_rdogrpQ8[i])))
                 {
                     rb = (RadioButton)rdogrpQ8.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ9 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ9.length; i++)
             {
                 if (String.valueOf(item.getQ9()).equals(String.valueOf(d_rdogrpQ9[i])))
                 {
                     rb = (RadioButton)rdogrpQ9.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ10 = new String[] {"1","2","3","4"};
             for (int i = 0; i < d_rdogrpQ10.length; i++)
             {
                 if (String.valueOf(item.getQ10()).equals(String.valueOf(d_rdogrpQ10[i])))
                 {
                     rb = (RadioButton)rdogrpQ10.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ11 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ11.length; i++)
             {
                 if (String.valueOf(item.getQ11()).equals(String.valueOf(d_rdogrpQ11[i])))
                 {
                     rb = (RadioButton)rdogrpQ11.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ12 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ12.length; i++)
             {
                 if (String.valueOf(item.getQ12()).equals(String.valueOf(d_rdogrpQ12[i])))
                 {
                     rb = (RadioButton)rdogrpQ12.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ13 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ13.length; i++)
             {
                 if (String.valueOf(item.getQ13()).equals(String.valueOf(d_rdogrpQ13[i])))
                 {
                     rb = (RadioButton)rdogrpQ13.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ14 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ14.length; i++)
             {
                 if (String.valueOf(item.getQ14()).equals(String.valueOf(d_rdogrpQ14[i])))
                 {
                     rb = (RadioButton)rdogrpQ14.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ15 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ15.length; i++)
             {
                 if (String.valueOf(item.getQ15()).equals(String.valueOf(d_rdogrpQ15[i])))
                 {
                     rb = (RadioButton)rdogrpQ15.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ16 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ16.length; i++)
             {
                 if (String.valueOf(item.getQ16()).equals(String.valueOf(d_rdogrpQ16[i])))
                 {
                     rb = (RadioButton)rdogrpQ16.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ17 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ17.length; i++)
             {
                 if (String.valueOf(item.getQ17()).equals(String.valueOf(d_rdogrpQ17[i])))
                 {
                     rb = (RadioButton)rdogrpQ17.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ18 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ18.length; i++)
             {
                 if (String.valueOf(item.getQ18()).equals(String.valueOf(d_rdogrpQ18[i])))
                 {
                     rb = (RadioButton)rdogrpQ18.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ19 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ19.length; i++)
             {
                 if (String.valueOf(item.getQ19()).equals(String.valueOf(d_rdogrpQ19[i])))
                 {
                     rb = (RadioButton)rdogrpQ19.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpQ20 = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpQ20.length; i++)
             {
                 if (String.valueOf(item.getQ20()).equals(String.valueOf(d_rdogrpQ20[i])))
                 {
                     rb = (RadioButton)rdogrpQ20.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Women.this, e.getMessage());
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