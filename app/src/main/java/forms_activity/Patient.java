
 package forms_activity;


 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.SimpleAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.widget.ArrayAdapter;
 import android.graphics.Color;
 import android.view.WindowManager;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;
 import org.icddrb.mental_health_survey.R;
 import forms_datamodel.Patient_DataModel;
 import Utility.*;
 import Common.*;

 public class Patient extends AppCompatActivity {
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
    LinearLayout secreg_date;
    View linereg_date;
    TextView Vlblreg_date;
    EditText dtpreg_date;
    LinearLayout secreg_time;
    View linereg_time;
    TextView Vlblreg_time;
    EditText txtreg_time;
    LinearLayout secpat_name;
    View linepat_name;
    TextView Vlblpat_name;
    EditText txtpat_name;
    LinearLayout secpat_age;
    View linepat_age;
    TextView Vlblpat_age;
    EditText txtpat_age;
    LinearLayout secmobile;
    View linemobile;
    TextView Vlblmobile;
    EditText txtmobile;

     LinearLayout secpat_sex;
     View linepat_sex;
     TextView Vlblpat_sex;
     RadioGroup rdogrppat_sex;
     RadioButton rdopat_sex1;
     RadioButton rdopat_sex2;
     LinearLayout secpat_cat;
     View linepat_cat;
     TextView Vlblpat_cat;
     RadioGroup rdogrppat_cat;
     RadioButton rdopat_cat1;
     RadioButton rdopat_cat2;
     RadioButton rdopat_cat3;

     LinearLayout secProvID;
     View lineProvID;
     TextView VlblProvID;
     EditText txtProvID;

    LinearLayout secrecv_service;
    View linerecv_service;
    TextView Vlblrecv_service;
    RadioGroup rdogrprecv_service;
    RadioButton rdorecv_service1;
    RadioButton rdorecv_service2;

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
    static String WoName = "";
    static String PatCat = "";
    static String RecvService = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.patient);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         PATIENTID = IDbundle.getString("PatientID");
         FACILITYID = IDbundle.getString("FacilityID");
         WoName = IDbundle.getString("WoName");
         RecvService = IDbundle.getString("recv_service");

         TableName = "Patient";
         MODULEID = 10;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Patient.this);
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
        Connection.LocalizeLanguage(Patient.this, MODULEID, LANGUAGEID);


         dtpreg_date.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                      VariableID = "btnreg_date"; showDialog(DATE_DIALOG);
                      return true;
                 }
                 return false;
             }
         });


         txtreg_time.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
             if(event.getAction() == MotionEvent.ACTION_UP) {
                  VariableID = "btnreg_time"; showDialog(TIME_DIALOG);
                  return true;
             }
             return false;
           }
         });

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
         Connection.MessageBox(Patient.this, e.getMessage());
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

         if(PATIENTID.length()==0) txtPatientID.setText(C.NewPatientID(DEVICEID));
         else txtPatientID.setText(PATIENTID);
         txtPatientID.setEnabled(false);

         secFacilityID=(LinearLayout)findViewById(R.id.secFacilityID);
         lineFacilityID=(View)findViewById(R.id.lineFacilityID);
         VlblFacilityID=(TextView) findViewById(R.id.VlblFacilityID);
         txtFacilityID=(EditText) findViewById(R.id.txtFacilityID);
         txtFacilityID.setText(FACILITYID);
         txtFacilityID.setEnabled(false);
         secreg_date=(LinearLayout)findViewById(R.id.secreg_date);
         linereg_date=(View)findViewById(R.id.linereg_date);
         Vlblreg_date=(TextView) findViewById(R.id.Vlblreg_date);
         dtpreg_date=(EditText) findViewById(R.id.dtpreg_date);
         secreg_time=(LinearLayout)findViewById(R.id.secreg_time);
         linereg_time=(View)findViewById(R.id.linereg_time);
         Vlblreg_time=(TextView) findViewById(R.id.Vlblreg_time);
         txtreg_time=(EditText) findViewById(R.id.txtreg_time);
         secpat_name=(LinearLayout)findViewById(R.id.secpat_name);
         linepat_name=(View)findViewById(R.id.linepat_name);
         Vlblpat_name=(TextView) findViewById(R.id.Vlblpat_name);
         txtpat_name=(EditText) findViewById(R.id.txtpat_name);
         secpat_age=(LinearLayout)findViewById(R.id.secpat_age);
         linepat_age=(View)findViewById(R.id.linepat_age);
         Vlblpat_age=(TextView) findViewById(R.id.Vlblpat_age);
         txtpat_age=(EditText) findViewById(R.id.txtpat_age);
         secmobile=(LinearLayout)findViewById(R.id.secmobile);
         linemobile=(View)findViewById(R.id.linemobile);
         Vlblmobile=(TextView) findViewById(R.id.Vlblmobile);
         txtmobile=(EditText) findViewById(R.id.txtmobile);
         secpat_sex=(LinearLayout)findViewById(R.id.secpat_sex);
         linepat_sex=(View)findViewById(R.id.linepat_sex);
         Vlblpat_sex = (TextView) findViewById(R.id.Vlblpat_sex);
         rdogrppat_sex = (RadioGroup) findViewById(R.id.rdogrppat_sex);
         rdopat_sex1 = (RadioButton) findViewById(R.id.rdopat_sex1);
         rdopat_sex2 = (RadioButton) findViewById(R.id.rdopat_sex2);
         secpat_cat=(LinearLayout)findViewById(R.id.secpat_cat);
         linepat_cat=(View)findViewById(R.id.linepat_cat);
         Vlblpat_cat = (TextView) findViewById(R.id.Vlblpat_cat);
         rdogrppat_cat = (RadioGroup) findViewById(R.id.rdogrppat_cat);
         rdopat_cat1 = (RadioButton) findViewById(R.id.rdopat_cat1);
         rdopat_cat2 = (RadioButton) findViewById(R.id.rdopat_cat2);
         rdopat_cat3 = (RadioButton) findViewById(R.id.rdopat_cat3);
         secrecv_service=(LinearLayout)findViewById(R.id.secrecv_service);
         linerecv_service=(View)findViewById(R.id.linerecv_service);
         Vlblrecv_service = (TextView) findViewById(R.id.Vlblrecv_service);
         rdogrprecv_service = (RadioGroup) findViewById(R.id.rdogrprecv_service);
         rdorecv_service1 = (RadioButton) findViewById(R.id.rdorecv_service1);
         rdorecv_service2 = (RadioButton) findViewById(R.id.rdorecv_service2);

         rdogrprecv_service.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
             @Override
             public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
                 String rbData = "";
                 RadioButton rb;
                 String[] d_rdogrpPNC2RespRDW = new String[] {"1","2"};
                 for (int i = 0; i < rdogrprecv_service.getChildCount(); i++)
                 {
                     rb = (RadioButton)rdogrprecv_service.getChildAt(i);
                     if (rb.isChecked()) rbData = d_rdogrpPNC2RespRDW[i];
                 }
                 if(rbData.equalsIgnoreCase("1"))
                 {
                     secProvID.setVisibility(View.VISIBLE);
                     lineProvID.setVisibility(View.VISIBLE);
                     txtProvID.setText("");
                 }
                 else
                 {
                     secProvID.setVisibility(View.GONE);
                     lineProvID.setVisibility(View.GONE);
                     txtProvID.setText("");
                 }
             }
             public void onNothingSelected(AdapterView<?> adapterView) {
                 return;
             }
         });

         secProvID=(LinearLayout)findViewById(R.id.secProvID);
         lineProvID=(View)findViewById(R.id.lineProvID);
         VlblProvID=(TextView) findViewById(R.id.VlblProvID);
         txtProvID=(EditText) findViewById(R.id.txtProvID);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Patient.this, e.getMessage());
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
         	Connection.MessageBox(Patient.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Patient_DataModel objSave = new Patient_DataModel();
         objSave.setPatientID(txtPatientID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setreg_date(dtpreg_date.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpreg_date.getText().toString()) : dtpreg_date.getText().toString());
         objSave.setreg_time(txtreg_time.getText().toString());
         objSave.setpat_name(txtpat_name.getText().toString());
         objSave.setpat_age(txtpat_age.getText().toString());
         objSave.setmobile(txtmobile.getText().toString());
         String[] d_rdogrprecv_service = new String[] {"1","2"};
         objSave.setrecv_service("");
         for (int i = 0; i < rdogrprecv_service.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrprecv_service.getChildAt(i);
             if (rb.isChecked()) objSave.setrecv_service(d_rdogrprecv_service[i]);
         }
         objSave.setProvID(txtProvID.getText().toString());
         String[] d_rdogrppat_sex = new String[] {"1","2"};
         objSave.setpat_sex("");
         for (int i = 0; i < rdogrppat_sex.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrppat_sex.getChildAt(i);
             if (rb.isChecked()) objSave.setpat_sex(d_rdogrppat_sex[i]);
         }

         String[] d_rdogrppat_cat = new String[] {"1","2","7"};
         objSave.setpat_cat("");
         for (int i = 0; i < rdogrppat_cat.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrppat_cat.getChildAt(i);
             if (rb.isChecked()) objSave.setpat_cat(d_rdogrppat_cat[i]);
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
             Connection.MessageBox(Patient.this, "Saved Successfully");
             finish();
             String PatCatPreg = rdopat_cat1.isChecked() ? "1" : "2";
             String PatCatDeliv = rdopat_cat2.isChecked() ? "1" : "2";
             String RecvService = rdorecv_service1.isChecked() ? "1" : "2";

             Bundle IDbundle = new Bundle();
             IDbundle.putString("PatientID", txtPatientID.getText().toString());
             IDbundle.putString("FacilityID", FACILITYID);
             IDbundle.putString("WoName", txtpat_name.getText().toString());
             IDbundle.putString("PatCatPreg", PatCatPreg);
             IDbundle.putString("PatCatDeliv", PatCatDeliv);
             IDbundle.putString("recv_service", RecvService);
             Intent f1 = new Intent(getApplicationContext(), Menu_Patient.class);
             f1.putExtras(IDbundle);
             startActivityForResult(f1, 1);
         }
         else{
             Connection.MessageBox(Patient.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Patient.this, e.getMessage());
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
         DV = Global.DateValidate(dtpreg_date.getText().toString());
         if(DV.length()!=0 & secreg_date.isShown())
           {
             ValidationMsg += "\n1. Required field/Not a valid date format: রেজিস্ট্রেশনের তারিখ.";
             secreg_date.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtreg_time.getText().length()==0 & secreg_time.isShown())
           {
             ValidationMsg += "\n2. Required field: রেজিস্ট্রেশনের সময়.";
             secreg_time.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtpat_name.getText().toString().length()==0 & secpat_name.isShown())
           {
             ValidationMsg += "\n3. Required field: নাম.";
             secpat_name.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtpat_age.getText().toString().length()==0 & secpat_age.isShown())
           {
             ValidationMsg += "\n4. Required field: বয়স (বছরে).";
             secpat_age.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(secpat_age.isShown() & (Integer.valueOf(txtpat_age.getText().toString().length()==0 ? "10" : txtpat_age.getText().toString()) < 10 || Integer.valueOf(txtpat_age.getText().toString().length()==0 ? "99" : txtpat_age.getText().toString()) > 99))
           {
             ValidationMsg += "\n4. Value should be between 10 and 99(বয়স (বছরে)).";
             secpat_age.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         /*if(txtmobile.getText().toString().length()==0 & secmobile.isShown())
           {
             ValidationMsg += "\n5. Required field: মোবাইল নাম্বার.";
             secmobile.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }*/
         if(!rdorecv_service1.isChecked() & !rdorecv_service2.isChecked() & secrecv_service.isShown())
           {
             ValidationMsg += "\n6. Required field: মন-স্বাস্থ্য কেন্দ্র থেকে সেবা গ্রহন.";
             secrecv_service.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdopat_sex1.isChecked() & !rdopat_sex2.isChecked() & secpat_sex.isShown())
         {
             ValidationMsg += "\nRequired field: লিঙ্গ.";
             secpat_sex.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
         }
         if(!rdopat_cat1.isChecked() & !rdopat_cat2.isChecked() & !rdopat_cat3.isChecked() & secpat_cat.isShown())
         {
             ValidationMsg += "\nRequired field: রোগীর ধরন.";
             secpat_cat.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
         }
         if(rdopat_sex1.isChecked() & rdopat_cat1.isChecked() & secpat_sex.isShown())
         {
             ValidationMsg += "\n লিঙ্গ পুরুষ হলে রোগীর ধরনঃ গর্ভবতী / প্রসবোত্তর হবেনা ";
             secpat_sex.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
         }
         if(rdopat_sex1.isChecked() & rdopat_cat2.isChecked() & secpat_sex.isShown())
         {
             ValidationMsg += "\n লিঙ্গ পুরুষ হলে রোগীর ধরনঃ গর্ভবতী / প্রসবোত্তর হবেনা ";
             secpat_sex.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secreg_date.setBackgroundColor(Color.WHITE);
             secreg_time.setBackgroundColor(Color.WHITE);
             secpat_name.setBackgroundColor(Color.WHITE);
             secpat_age.setBackgroundColor(Color.WHITE);
             secpat_age.setBackgroundColor(Color.WHITE);
             secmobile.setBackgroundColor(Color.WHITE);
             secrecv_service.setBackgroundColor(Color.WHITE);
             secpat_sex.setBackgroundColor(Color.WHITE);
             secpat_cat.setBackgroundColor(Color.WHITE);
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
           Patient_DataModel d = new Patient_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PatientID='"+ PatientID +"' and FacilityID='"+ FacilityID +"'";
           List<Patient_DataModel> data = d.SelectAll(this, SQL);
           for(Patient_DataModel item : data){
             txtPatientID.setText(item.getPatientID());
             txtFacilityID.setText(item.getFacilityID());
             dtpreg_date.setText(item.getreg_date().toString().length()==0 ? "" : Global.DateConvertDMY(item.getreg_date()));
             txtreg_time.setText(item.getreg_time());
             txtpat_name.setText(item.getpat_name());
             txtpat_age.setText(String.valueOf(item.getpat_age()));
             txtmobile.setText(item.getmobile());
             String[] d_rdogrprecv_service = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrprecv_service.length; i++)
             {
                 if (String.valueOf(item.getrecv_service()).equals(String.valueOf(d_rdogrprecv_service[i])))
                 {
                     rb = (RadioButton)rdogrprecv_service.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
               String[] d_rdogrppat_sex = new String[] {"1","2"};
               for (int i = 0; i < d_rdogrppat_sex.length; i++)
               {
                   if (String.valueOf(item.getpat_sex()).equals(String.valueOf(d_rdogrppat_sex[i])))
                   {
                       rb = (RadioButton)rdogrppat_sex.getChildAt(i);
                       rb.setChecked(true);
                   }
               }
               String[] d_rdogrppat_cat = new String[] {"1","2","7"};
               for (int i = 0; i < d_rdogrppat_cat.length; i++)
               {
                   if (String.valueOf(item.getpat_cat()).equals(String.valueOf(d_rdogrppat_cat[i])))
                   {
                       rb = (RadioButton)rdogrppat_cat.getChildAt(i);
                       rb.setChecked(true);
                   }
               }
               txtProvID.setText(item.getProvID());

           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Patient.this, e.getMessage());
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


      dtpDate = (EditText)findViewById(R.id.dtpreg_date);
      if (VariableID.equals("btnreg_date"))
      {
          dtpDate = (EditText)findViewById(R.id.dtpreg_date);
      }
      dtpDate.setText(new StringBuilder()
      .append(Global.Right("00"+mDay,2)).append("/")
      .append(Global.Right("00"+mMonth,2)).append("/")
      .append(mYear));
      }
  };

 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
       hour = selectedHour; minute = selectedMinute;
       EditText tpTime;

              tpTime = (EditText)findViewById(R.id.txtreg_time);
             if (VariableID.equals("btnreg_time"))
              {
                  tpTime = (EditText)findViewById(R.id.txtreg_time);
              }
       tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));
    }
  };


 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
 }
}