
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
// import android.widget.AutoCompleteTextView;
// import android.support.v4.content.ContextCompat;
// import android.support.v7.app.AppCompatActivity;
// import android.text.Editable;
// import android.text.TextWatcher;
 import org.icddrb.mental_health_survey.R;

 import forms_datamodel.SectionB_DataModel;

 public class SectionB extends AppCompatActivity {
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
    LinearLayout seclblB1a;
    View linelblB1a;
    LinearLayout seclblB1b;
    View linelblB1b;
    LinearLayout seclblB1c;
    View linelblB1c;
    LinearLayout secPatientID;
    View linePatientID;
    TextView VlblPatientID;
    EditText txtPatientID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secB1;
    View lineB1;
    TextView VlblB1;
    RadioGroup rdogrpB1;
    RadioButton rdoB11;
    RadioButton rdoB12;
    RadioButton rdoB13;
    RadioButton rdoB14;
    LinearLayout secB2;
    View lineB2;
    TextView VlblB2;
    RadioGroup rdogrpB2;
    RadioButton rdoB21;
    RadioButton rdoB22;
    RadioButton rdoB23;
    RadioButton rdoB24;
    LinearLayout secB3;
    View lineB3;
    TextView VlblB3;
    RadioGroup rdogrpB3;
    RadioButton rdoB31;
    RadioButton rdoB32;
    RadioButton rdoB33;
    RadioButton rdoB34;
    LinearLayout secB4;
    View lineB4;
    TextView VlblB4;
    RadioGroup rdogrpB4;
    RadioButton rdoB41;
    RadioButton rdoB42;
    RadioButton rdoB43;
    RadioButton rdoB44;
    LinearLayout secB5;
    View lineB5;
    TextView VlblB5;
    RadioGroup rdogrpB5;
    RadioButton rdoB51;
    RadioButton rdoB52;
    RadioButton rdoB53;
    RadioButton rdoB54;
    LinearLayout secB6;
    View lineB6;
    TextView VlblB6;
    RadioGroup rdogrpB6;
    RadioButton rdoB61;
    RadioButton rdoB62;
    RadioButton rdoB63;
    RadioButton rdoB64;
    LinearLayout secB7;
    View lineB7;
    TextView VlblB7;
    RadioGroup rdogrpB7;
    RadioButton rdoB71;
    RadioButton rdoB72;
    RadioButton rdoB73;
    RadioButton rdoB74;
    LinearLayout secBScore;
    View lineBScore;
    TextView VlblBScore;
    EditText txtBScore;

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
         setContentView(R.layout.sectionb);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "SectionB";
         MODULEID = 2;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SectionB.this);
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
        Connection.LocalizeLanguage(SectionB.this, MODULEID, LANGUAGEID);





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
         Connection.MessageBox(SectionB.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         seclblB1a=(LinearLayout)findViewById(R.id.seclblB1a);
         linelblB1a=(View)findViewById(R.id.linelblB1a);
         seclblB1b=(LinearLayout)findViewById(R.id.seclblB1b);
         linelblB1b=(View)findViewById(R.id.linelblB1b);
         seclblB1c=(LinearLayout)findViewById(R.id.seclblB1c);
         linelblB1c=(View)findViewById(R.id.linelblB1c);
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
         secB1=(LinearLayout)findViewById(R.id.secB1);
         lineB1=(View)findViewById(R.id.lineB1);
         VlblB1 = (TextView) findViewById(R.id.VlblB1);
         rdogrpB1 = (RadioGroup) findViewById(R.id.rdogrpB1);
         rdoB11 = (RadioButton) findViewById(R.id.rdoB11);
         rdoB12 = (RadioButton) findViewById(R.id.rdoB12);
         rdoB13 = (RadioButton) findViewById(R.id.rdoB13);
         rdoB14 = (RadioButton) findViewById(R.id.rdoB14);
         secB2=(LinearLayout)findViewById(R.id.secB2);
         lineB2=(View)findViewById(R.id.lineB2);
         VlblB2 = (TextView) findViewById(R.id.VlblB2);
         rdogrpB2 = (RadioGroup) findViewById(R.id.rdogrpB2);
         rdoB21 = (RadioButton) findViewById(R.id.rdoB21);
         rdoB22 = (RadioButton) findViewById(R.id.rdoB22);
         rdoB23 = (RadioButton) findViewById(R.id.rdoB23);
         rdoB24 = (RadioButton) findViewById(R.id.rdoB24);
         secB3=(LinearLayout)findViewById(R.id.secB3);
         lineB3=(View)findViewById(R.id.lineB3);
         VlblB3 = (TextView) findViewById(R.id.VlblB3);
         rdogrpB3 = (RadioGroup) findViewById(R.id.rdogrpB3);
         rdoB31 = (RadioButton) findViewById(R.id.rdoB31);
         rdoB32 = (RadioButton) findViewById(R.id.rdoB32);
         rdoB33 = (RadioButton) findViewById(R.id.rdoB33);
         rdoB34 = (RadioButton) findViewById(R.id.rdoB34);
         secB4=(LinearLayout)findViewById(R.id.secB4);
         lineB4=(View)findViewById(R.id.lineB4);
         VlblB4 = (TextView) findViewById(R.id.VlblB4);
         rdogrpB4 = (RadioGroup) findViewById(R.id.rdogrpB4);
         rdoB41 = (RadioButton) findViewById(R.id.rdoB41);
         rdoB42 = (RadioButton) findViewById(R.id.rdoB42);
         rdoB43 = (RadioButton) findViewById(R.id.rdoB43);
         rdoB44 = (RadioButton) findViewById(R.id.rdoB44);
         secB5=(LinearLayout)findViewById(R.id.secB5);
         lineB5=(View)findViewById(R.id.lineB5);
         VlblB5 = (TextView) findViewById(R.id.VlblB5);
         rdogrpB5 = (RadioGroup) findViewById(R.id.rdogrpB5);
         rdoB51 = (RadioButton) findViewById(R.id.rdoB51);
         rdoB52 = (RadioButton) findViewById(R.id.rdoB52);
         rdoB53 = (RadioButton) findViewById(R.id.rdoB53);
         rdoB54 = (RadioButton) findViewById(R.id.rdoB54);
         secB6=(LinearLayout)findViewById(R.id.secB6);
         lineB6=(View)findViewById(R.id.lineB6);
         VlblB6 = (TextView) findViewById(R.id.VlblB6);
         rdogrpB6 = (RadioGroup) findViewById(R.id.rdogrpB6);
         rdoB61 = (RadioButton) findViewById(R.id.rdoB61);
         rdoB62 = (RadioButton) findViewById(R.id.rdoB62);
         rdoB63 = (RadioButton) findViewById(R.id.rdoB63);
         rdoB64 = (RadioButton) findViewById(R.id.rdoB64);
         secB7=(LinearLayout)findViewById(R.id.secB7);
         lineB7=(View)findViewById(R.id.lineB7);
         VlblB7 = (TextView) findViewById(R.id.VlblB7);
         rdogrpB7 = (RadioGroup) findViewById(R.id.rdogrpB7);
         rdoB71 = (RadioButton) findViewById(R.id.rdoB71);
         rdoB72 = (RadioButton) findViewById(R.id.rdoB72);
         rdoB73 = (RadioButton) findViewById(R.id.rdoB73);
         rdoB74 = (RadioButton) findViewById(R.id.rdoB74);
         secBScore=(LinearLayout)findViewById(R.id.secBScore);
         lineBScore=(View)findViewById(R.id.lineBScore);
         VlblBScore=(TextView) findViewById(R.id.VlblBScore);
         txtBScore=(EditText) findViewById(R.id.txtBScore);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionB.this, e.getMessage());
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
         	Connection.MessageBox(SectionB.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         SectionB_DataModel objSave = new SectionB_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         String[] d_rdogrpB1 = new String[] {"0","1","2","3"};
         objSave.setB1("");
         for (int i = 0; i < rdogrpB1.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB1.getChildAt(i);
             if (rb.isChecked()) objSave.setB1(d_rdogrpB1[i]);
         }

         String[] d_rdogrpB2 = new String[] {"0","1","2","3"};
         objSave.setB2("");
         for (int i = 0; i < rdogrpB2.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB2.getChildAt(i);
             if (rb.isChecked()) objSave.setB2(d_rdogrpB2[i]);
         }

         String[] d_rdogrpB3 = new String[] {"0","1","2","3"};
         objSave.setB3("");
         for (int i = 0; i < rdogrpB3.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB3.getChildAt(i);
             if (rb.isChecked()) objSave.setB3(d_rdogrpB3[i]);
         }

         String[] d_rdogrpB4 = new String[] {"0","1","2","3"};
         objSave.setB4("");
         for (int i = 0; i < rdogrpB4.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB4.getChildAt(i);
             if (rb.isChecked()) objSave.setB4(d_rdogrpB4[i]);
         }

         String[] d_rdogrpB5 = new String[] {"0","1","2","3"};
         objSave.setB5("");
         for (int i = 0; i < rdogrpB5.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB5.getChildAt(i);
             if (rb.isChecked()) objSave.setB5(d_rdogrpB5[i]);
         }

         String[] d_rdogrpB6 = new String[] {"0","1","2","3"};
         objSave.setB6("");
         for (int i = 0; i < rdogrpB6.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB6.getChildAt(i);
             if (rb.isChecked()) objSave.setB6(d_rdogrpB6[i]);
         }

         String[] d_rdogrpB7 = new String[] {"0","1","2","3"};
         objSave.setB7("");
         for (int i = 0; i < rdogrpB7.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpB7.getChildAt(i);
             if (rb.isChecked()) objSave.setB7(d_rdogrpB7[i]);
         }

         objSave.setBScore(txtBScore.getText().toString());
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
             Connection.MessageBox(SectionB.this, "Saved Successfully");
             finish();
             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", PATIENTID);
             IDbundle.putString("FacilityID", FACILITYID);
             Intent f1 = new Intent(getApplicationContext(), SectionC.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);*/

             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);
             Connection.MessageBox(SectionB.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(SectionB.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(SectionB.this, e.getMessage());
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
         if(!rdoB11.isChecked() & !rdoB12.isChecked() & !rdoB13.isChecked() & !rdoB14.isChecked() & secB1.isShown())
           {
             ValidationMsg += "\nB1. Required field: চিন্তিত বা উদ্বিগ্ন অনুভব করা.";
             secB1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB21.isChecked() & !rdoB22.isChecked() & !rdoB23.isChecked() & !rdoB24.isChecked() & secB2.isShown())
           {
             ValidationMsg += "\nB2. Required field: চিন্তা বা উদ্বিগ্নতা নিয়ন্ত্রণ করতে ব্যর্থ হওয়া.";
             secB2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB31.isChecked() & !rdoB32.isChecked() & !rdoB33.isChecked() & !rdoB34.isChecked() & secB3.isShown())
           {
             ValidationMsg += "\nB3. Required field: নানান ধরণের বিষয় নিয়ে চিন্তা করা.";
             secB3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB41.isChecked() & !rdoB42.isChecked() & !rdoB43.isChecked() & !rdoB44.isChecked() & secB4.isShown())
           {
             ValidationMsg += "\nB4. Required field: শান্ত হয়ে বিশ্রাম নিতে না পারা.";
             secB4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB51.isChecked() & !rdoB52.isChecked() & !rdoB53.isChecked() & !rdoB54.isChecked() & secB5.isShown())
           {
             ValidationMsg += "\nB5. Required field: অস্থিরতার কারণে কোথাও সুস্থির ভাবে বেশিক্ষণ বসতে না পারা.";
             secB5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB61.isChecked() & !rdoB62.isChecked() & !rdoB63.isChecked() & !rdoB64.isChecked() & secB6.isShown())
           {
             ValidationMsg += "\nB6. Required field: সহজেই রেগে যাওয়া বা মেজাজ খিটখিটে হয়ে যাওয়া.";
             secB6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdoB71.isChecked() & !rdoB72.isChecked() & !rdoB73.isChecked() & !rdoB74.isChecked() & secB7.isShown())
           {
             ValidationMsg += "\nB7. Required field: ভয় লাগা বা সামনে ভয়ংকর কিছু হতে পারে এমনটা মনে হওয়া.";
             secB7.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtBScore.getText().toString().length()==0 & secBScore.isShown())
           {
             ValidationMsg += "\nTotal Score. Required field: Score.";
             secBScore.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secBScore.isShown() & (Integer.valueOf(txtBScore.getText().toString().length()==0 ? "00" : txtBScore.getText().toString()) < 00 || Integer.valueOf(txtBScore.getText().toString().length()==0 ? "21" : txtBScore.getText().toString()) > 21))
           {
             ValidationMsg += "\nTotal Score. Value should be between 00 and 21(Score).";
             secBScore.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secB1.setBackgroundColor(Color.WHITE);
             secB2.setBackgroundColor(Color.WHITE);
             secB3.setBackgroundColor(Color.WHITE);
             secB4.setBackgroundColor(Color.WHITE);
             secB5.setBackgroundColor(Color.WHITE);
             secB6.setBackgroundColor(Color.WHITE);
             secB7.setBackgroundColor(Color.WHITE);
             secBScore.setBackgroundColor(Color.WHITE);
             secBScore.setBackgroundColor(Color.WHITE);
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
           SectionB_DataModel d = new SectionB_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<SectionB_DataModel> data = d.SelectAll(this, SQL);
           for(SectionB_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             String[] d_rdogrpB1 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB1.length; i++)
             {
                 if (String.valueOf(item.getB1()).equals(String.valueOf(d_rdogrpB1[i])))
                 {
                     rb = (RadioButton)rdogrpB1.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB2 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB2.length; i++)
             {
                 if (String.valueOf(item.getB2()).equals(String.valueOf(d_rdogrpB2[i])))
                 {
                     rb = (RadioButton)rdogrpB2.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB3 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB3.length; i++)
             {
                 if (String.valueOf(item.getB3()).equals(String.valueOf(d_rdogrpB3[i])))
                 {
                     rb = (RadioButton)rdogrpB3.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB4 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB4.length; i++)
             {
                 if (String.valueOf(item.getB4()).equals(String.valueOf(d_rdogrpB4[i])))
                 {
                     rb = (RadioButton)rdogrpB4.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB5 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB5.length; i++)
             {
                 if (String.valueOf(item.getB5()).equals(String.valueOf(d_rdogrpB5[i])))
                 {
                     rb = (RadioButton)rdogrpB5.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB6 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB6.length; i++)
             {
                 if (String.valueOf(item.getB6()).equals(String.valueOf(d_rdogrpB6[i])))
                 {
                     rb = (RadioButton)rdogrpB6.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             String[] d_rdogrpB7 = new String[] {"0","1","2","3"};
             for (int i = 0; i < d_rdogrpB7.length; i++)
             {
                 if (String.valueOf(item.getB7()).equals(String.valueOf(d_rdogrpB7[i])))
                 {
                     rb = (RadioButton)rdogrpB7.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtBScore.setText(String.valueOf(item.getBScore()));
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SectionB.this, e.getMessage());
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