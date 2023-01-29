
 package forms_activity;


 import java.io.File;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;

 import android.annotation.SuppressLint;
 import android.app.*;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.location.Location;
 import android.os.Environment;
 import android.os.Handler;
 import android.os.Message;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
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
 import Utility.*;
 import Common.*;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;

 import org.icddrb.kalaazar_pkdl.Fragment_Main;
 import org.icddrb.kalaazar_pkdl.R;

 import forms_datamodel.data_registration_DataModel;

 public class data_registration extends AppCompatActivity {
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
    LinearLayout secuserid;
    View lineuserid;
    TextView Vlbluserid;
    EditText txtuserid;
    LinearLayout secusername;
    View lineusername;
    TextView Vlblusername;
    EditText txtusername;
    LinearLayout secage;
    View lineage;
    TextView Vlblage;
    Spinner spnage;
    LinearLayout secsex;
    View linesex;
    TextView Vlblsex;
    RadioGroup rdogrpsex;
    RadioButton rdosex1;
    RadioButton rdosex2;
    RadioButton rdosex3;
    LinearLayout secocp;
    View lineocp;
    TextView Vlblocp;
    EditText txtocp;
    LinearLayout secinstitute;
    View lineinstitute;
    TextView Vlblinstitute;
    EditText txtinstitute;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String USERID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.data_registration);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         USERID = "";//IDbundle.getString("userid");

         TableName = "data_registration";
         MODULEID = 0;
         //LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(data_registration.this);
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
        //Connection.LocalizeLanguage(data_registration.this, MODULEID, LANGUAGEID);





         //Hide all skip variables


        //DataSearch(USERID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(data_registration.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         secuserid=(LinearLayout)findViewById(R.id.secuserid);
         lineuserid=(View)findViewById(R.id.lineuserid);
         Vlbluserid=(TextView) findViewById(R.id.Vlbluserid);
         txtuserid=(EditText) findViewById(R.id.txtuserid);
         //txtuserid.setText(USERID);
         //txtuserid.setEnabled(false);
         secusername=(LinearLayout)findViewById(R.id.secusername);
         lineusername=(View)findViewById(R.id.lineusername);
         Vlblusername=(TextView) findViewById(R.id.Vlblusername);
         txtusername=(EditText) findViewById(R.id.txtusername);
         secage=(LinearLayout)findViewById(R.id.secage);
         lineage=(View)findViewById(R.id.lineage);
         Vlblage=(TextView) findViewById(R.id.Vlblage);
         spnage=(Spinner) findViewById(R.id.spnage);
         List<String> listage = new ArrayList<String>();
         
         listage.add("");
         listage.add("1-< 18");
         listage.add("2-18 to 24");
         listage.add("3-25 to 30");
         listage.add("4-31 to 40");
         listage.add("5-41 to 50");
         listage.add("6-51 to 70");
         listage.add("7-> 70");
         ArrayAdapter<String> adptrage= new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, listage);
         spnage.setAdapter(adptrage);

         secsex=(LinearLayout)findViewById(R.id.secsex);
         linesex=(View)findViewById(R.id.linesex);
         Vlblsex = (TextView) findViewById(R.id.Vlblsex);
         rdogrpsex = (RadioGroup) findViewById(R.id.rdogrpsex);
         rdosex1 = (RadioButton) findViewById(R.id.rdosex1);
         rdosex2 = (RadioButton) findViewById(R.id.rdosex2);
         rdosex3 = (RadioButton) findViewById(R.id.rdosex3);
         secocp=(LinearLayout)findViewById(R.id.secocp);
         lineocp=(View)findViewById(R.id.lineocp);
         Vlblocp=(TextView) findViewById(R.id.Vlblocp);
         txtocp=(EditText) findViewById(R.id.txtocp);
         secinstitute=(LinearLayout)findViewById(R.id.secinstitute);
         lineinstitute=(View)findViewById(R.id.lineinstitute);
         Vlblinstitute=(TextView) findViewById(R.id.Vlblinstitute);
         txtinstitute=(EditText) findViewById(R.id.txtinstitute);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(data_registration.this, e.getMessage());
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
         	Connection.MessageBox(data_registration.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         data_registration_DataModel objSave = new data_registration_DataModel();
         objSave.setuserid(txtuserid.getText().toString());
         objSave.setusername(txtusername.getText().toString());
         objSave.setage(spnage.getSelectedItemPosition() == 0 ? "" : spnage.getSelectedItem().toString().split("-")[0]);
         String[] d_rdogrpsex = new String[] {"1","2","3"};
         objSave.setsex("");
         for (int i = 0; i < rdogrpsex.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpsex.getChildAt(i);
             if (rb.isChecked()) objSave.setsex(d_rdogrpsex[i]);
         }

         objSave.setocp(txtocp.getText().toString());
         objSave.setinstitute(txtinstitute.getText().toString());
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

             Connection.MessageBox(data_registration.this, "Saved Successfully");
             ProcessDatabase();
         }
         else{
             Connection.MessageBox(data_registration.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(data_registration.this, e.getMessage());
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
         if(txtuserid.getText().toString().length()==0 & secuserid.isShown())
           {
             ValidationMsg += "\nRequired field: Mobile Number.";
             secuserid.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtusername.getText().toString().length()==0 & secusername.isShown())
           {
             ValidationMsg += "\nRequired field: Name .";
             secusername.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(spnage.getSelectedItemPosition()==0  & secage.isShown())
           {
             ValidationMsg += "\nRequired field: Age.";
             secage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(!rdosex1.isChecked() & !rdosex2.isChecked() & !rdosex3.isChecked() & secsex.isShown())
           {
             ValidationMsg += "\nRequired field: Sex.";
             secsex.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtocp.getText().toString().length()==0 & secocp.isShown())
           {
             ValidationMsg += "\nRequired field: Occupation.";
             secocp.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtinstitute.getText().toString().length()==0 & secinstitute.isShown())
           {
             ValidationMsg += "\nRequired field: Institution .";
             secinstitute.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
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
             secuserid.setBackgroundColor(Color.WHITE);
             secusername.setBackgroundColor(Color.WHITE);
             secage.setBackgroundColor(Color.WHITE);
             secsex.setBackgroundColor(Color.WHITE);
             secocp.setBackgroundColor(Color.WHITE);
             secinstitute.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String userid)
     {
       try
        {     
           RadioButton rb;
           data_registration_DataModel d = new data_registration_DataModel();
           String SQL = "Select * from "+ TableName +"  Where userid='"+ userid +"'";
           List<data_registration_DataModel> data = d.SelectAll(this, SQL);
           for(data_registration_DataModel item : data){
             txtuserid.setText(item.getuserid());
             txtusername.setText(item.getusername());
             spnage.setSelection(Global.SpinnerItemPositionAnyLength(spnage, String.valueOf(item.getage())));
             String[] d_rdogrpsex = new String[] {"1","2","3"};
             for (int i = 0; i < d_rdogrpsex.length; i++)
             {
                 if (String.valueOf(item.getsex()).equals(String.valueOf(d_rdogrpsex[i])))
                 {
                     rb = (RadioButton)rdogrpsex.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtocp.setText(item.getocp());
             txtinstitute.setText(item.getinstitute());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(data_registration.this, e.getMessage());
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


     private void ProcessDatabase()
     {
         String zipFile_URL = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder + File.separator + ProjectSetting.zipDatabaseName;
         String dbFile_URL = Environment.getExternalStorageDirectory() + File.separator + ProjectSetting.DatabaseFolder + File.separator + ProjectSetting.DatabaseName;
         networkAvailable = Connection.haveNetworkConnection(data_registration.this);
         if (new File(zipFile_URL).exists()) {
             File dbfile = new File(dbFile_URL);
             if (dbfile.exists()) {
                 Boolean.valueOf(dbfile.delete());
             }
             CompressZip decompressZip = new CompressZip();
             StringBuilder sb = new StringBuilder();
             sb.append(Environment.getExternalStorageDirectory());
             sb.append(File.separator);
             sb.append(ProjectSetting.DatabaseFolder);
             decompressZip.unzipDB(zipFile_URL, sb.toString());
         } else {
             //lblMessage.setVisibility(View.GONE);
             //btnTryAgain.setVisibility(View.GONE);
             if (this.networkAvailable) {
                 String DeviceID = C.ReturnResult("ReturnSingleValue","sp_Request_DeviceID '"+ C.getDeviceUniqueID(this) +"',''");
                 String Setting = C.ReturnResult("Existence", "Select DeviceId from DeviceList where DeviceId='"+ DeviceID +"' and Setting='3' and Active='1'");
                 if (Setting.equals("2")) {
                     //lblMessage.setText("Device ID :"+ DeviceID +" is not allowed to configure a mobile device, contact with administrator.");
                     //lblMessage.setVisibility(View.VISIBLE);
                     //btnTryAgain.setVisibility(View.VISIBLE);
                     return;
                 }

                 RebuildDatabase(DeviceID);

             }else {
                 //lblMessage.setText("Internet connection is not available for building initial database.");
                 //lblMessage.setVisibility(View.VISIBLE);
                 //btnTryAgain.setVisibility(View.VISIBLE);
             }
         }
     }

     ProgressDialog progDailog;
     private void RebuildDatabase(String DeviceID)
     {
         //lblMessage.setVisibility(View.GONE);
         //btnTryAgain.setVisibility(View.GONE);
         progDailog = new ProgressDialog(data_registration.this);
         progDailog.setTitle("Preparing database");
         progDailog.setMessage("Please Wait . . .");
         progDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
         progDailog.setIndeterminate(false);
         progDailog.setCancelable(false);
         progDailog.setIcon(R.drawable.data_sync);
         progDailog.setProgress(0);
         progDailog.show();

        /* new Thread() {
             public void run() {
                 try {
                     if(C.RebuildDatabase(progDailog, progressHandler, DeviceID)){
                         progDailog.dismiss();
                         Intent mainIntent = new Intent(data_registration.this, Fragment_Main.class);
                         startActivity(mainIntent);
                     }else{
                         progDailog.dismiss();
                         //lblMessage.setText("Process execution failed. Please try again with internet connection.");
                         //lblMessage.setVisibility(View.VISIBLE);
                         //btnTryAgain.setVisibility(View.VISIBLE);
                     }

                 } catch (Exception e) {

                 }
             }
         }.start();*/


     }

     @SuppressLint("HandlerLeak")
     Handler progressHandler = new Handler() {
         public void handleMessage(Message msg) {
             progDailog.setMessage(Global.getInstance().getProgressMessage());
             progDailog.incrementProgressBy(0);
         }
     };
}