
 package forms_system;


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
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.SimpleAdapter;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.graphics.Color;
 import android.view.WindowManager;
 import Utility.*;
 import Common.*;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;

 import org.icddrb.standard_v3.R;

 public class DeviceList extends AppCompatActivity {
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
    LinearLayout secDeviceID;
    View lineDeviceID;
    TextView VlblDeviceID;
    EditText txtDeviceID;
    LinearLayout secDeviceUniqueID;
    View lineDeviceUniqueID;
    TextView VlblDeviceUniqueID;
    EditText txtDeviceUniqueID;
    LinearLayout secUpdateDT;
    View lineUpdateDT;
    TextView VlblUpdateDT;
    EditText txtUpdateDT;
    LinearLayout secSetting;
    View lineSetting;
    TextView VlblSetting;
    RadioGroup rdogrpSetting;
    RadioButton rdoSetting1;
    RadioButton rdoSetting2;
    LinearLayout secDBRequest;
    View lineDBRequest;
    TextView VlblDBRequest;
    CheckBox chkDBRequest;
    LinearLayout secActive;
    View lineActive;
    TextView VlblActive;
    CheckBox chkActive;
    LinearLayout secApproval;
    View lineApproval;
    TextView VlblApproval;
    CheckBox chkApproval;
    LinearLayout secApprovalDT;
    View lineApprovalDT;
    TextView VlblApprovalDT;
    EditText dtpApprovalDT;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String DeviceID = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.system_devicelist);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         DeviceID = IDbundle.getString("DeviceID");

         TableName = "DeviceList";
         MODULEID = 0;
         LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(DeviceList.this);
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
        Connection.LocalizeLanguage(DeviceList.this, MODULEID, LANGUAGEID);


         dtpApprovalDT.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                      VariableID = "btnApprovalDT"; showDialog(DATE_DIALOG);
                      return true;
                 }
                 return false;
             }
         });



         //Hide all skip variables


        DataSearch(DeviceID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(DeviceList.this, e.getMessage());
         return;
     }
 }

 private void Initialization()
 {
   try
     {
         secDeviceID=(LinearLayout)findViewById(R.id.secDeviceID);
         lineDeviceID=(View)findViewById(R.id.lineDeviceID);
         VlblDeviceID=(TextView) findViewById(R.id.VlblDeviceID);
         txtDeviceID=(EditText) findViewById(R.id.txtDeviceID);
         txtDeviceID.setText(DeviceID);
         txtDeviceID.setEnabled(false);
         secDeviceUniqueID=(LinearLayout)findViewById(R.id.secDeviceUniqueID);
         lineDeviceUniqueID=(View)findViewById(R.id.lineDeviceUniqueID);
         VlblDeviceUniqueID=(TextView) findViewById(R.id.VlblDeviceUniqueID);
         txtDeviceUniqueID=(EditText) findViewById(R.id.txtDeviceUniqueID);
         secUpdateDT=(LinearLayout)findViewById(R.id.secUpdateDT);
         lineUpdateDT=(View)findViewById(R.id.lineUpdateDT);
         VlblUpdateDT=(TextView) findViewById(R.id.VlblUpdateDT);
         txtUpdateDT=(EditText) findViewById(R.id.txtUpdateDT);
         secSetting=(LinearLayout)findViewById(R.id.secSetting);
         lineSetting=(View)findViewById(R.id.lineSetting);
         VlblSetting = (TextView) findViewById(R.id.VlblSetting);
         rdogrpSetting = (RadioGroup) findViewById(R.id.rdogrpSetting);
         rdoSetting1 = (RadioButton) findViewById(R.id.rdoSetting1);
         rdoSetting2 = (RadioButton) findViewById(R.id.rdoSetting2);
         secDBRequest=(LinearLayout)findViewById(R.id.secDBRequest);
         lineDBRequest=(View)findViewById(R.id.lineDBRequest);
         VlblDBRequest=(TextView) findViewById(R.id.VlblDBRequest);
         chkDBRequest=(CheckBox) findViewById(R.id.chkDBRequest);
         secActive=(LinearLayout)findViewById(R.id.secActive);
         lineActive=(View)findViewById(R.id.lineActive);
         VlblActive=(TextView) findViewById(R.id.VlblActive);
         chkActive=(CheckBox) findViewById(R.id.chkActive);
         secApproval=(LinearLayout)findViewById(R.id.secApproval);
         lineApproval=(View)findViewById(R.id.lineApproval);
         VlblApproval=(TextView) findViewById(R.id.VlblApproval);
         chkApproval=(CheckBox) findViewById(R.id.chkApproval);
         secApprovalDT=(LinearLayout)findViewById(R.id.secApprovalDT);
         lineApprovalDT=(View)findViewById(R.id.lineApprovalDT);
         VlblApprovalDT=(TextView) findViewById(R.id.VlblApprovalDT);
         dtpApprovalDT=(EditText) findViewById(R.id.dtpApprovalDT);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(DeviceList.this, e.getMessage());
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
         	Connection.MessageBox(DeviceList.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         DeviceList_DataModel objSave = new DeviceList_DataModel();
         objSave.setDeviceID(txtDeviceID.getText().toString());
         objSave.setDeviceUniqueID(txtDeviceUniqueID.getText().toString());
         objSave.setUpdateDT(txtUpdateDT.getText().toString());
         String[] d_rdogrpSetting = new String[] {"1","2"};
         objSave.setSetting("");
         for (int i = 0; i < rdogrpSetting.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpSetting.getChildAt(i);
             if (rb.isChecked()) objSave.setSetting(d_rdogrpSetting[i]);
         }

         objSave.setDBRequest((chkDBRequest.isChecked()?"1":"2"));
         objSave.setActive((chkActive.isChecked()?"1":"2"));
         objSave.setApproval((chkApproval.isChecked()?"1":"2"));
         objSave.setApprovalDT(dtpApprovalDT.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpApprovalDT.getText().toString()) : dtpApprovalDT.getText().toString());

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(DeviceList.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(DeviceList.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(DeviceList.this, e.getMessage());
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
         if(txtDeviceID.getText().toString().length()==0 & secDeviceID.isShown())
           {
             ValidationMsg += "\nRequired field: Device ID.";
             secDeviceID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtDeviceUniqueID.getText().toString().length()==0 & secDeviceUniqueID.isShown())
           {
             ValidationMsg += "\nRequired field: Device Unique ID.";
             secDeviceUniqueID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtUpdateDT.getText().toString().length()==0 & secUpdateDT.isShown())
           {
             ValidationMsg += "\nRequired field: System Update Date.";
             secUpdateDT.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(!rdoSetting1.isChecked() & !rdoSetting2.isChecked() & secSetting.isShown())
           {
             ValidationMsg += "\nRequired field: Allow Device Setting.";
             secSetting.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         DV = Global.DateValidate(dtpApprovalDT.getText().toString());
         if(DV.length()!=0 & secApprovalDT.isShown())
           {
             ValidationMsg += "\nRequired field/Not a valid date format: Approval Date.";
             secApprovalDT.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
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
             secDeviceID.setBackgroundColor(Color.WHITE);
             secDeviceUniqueID.setBackgroundColor(Color.WHITE);
             secUpdateDT.setBackgroundColor(Color.WHITE);
             secSetting.setBackgroundColor(Color.WHITE);
             secApprovalDT.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String DeviceID)
     {
       try
        {     
           RadioButton rb;
           DeviceList_DataModel d = new DeviceList_DataModel();
           String SQL = "Select * from "+ TableName +"  Where DeviceID='"+ DeviceID +"'";
           List<DeviceList_DataModel> data = d.SelectAll(this, SQL);
           for(DeviceList_DataModel item : data){
             txtDeviceID.setText(item.getDeviceID());
             txtDeviceUniqueID.setText(item.getDeviceUniqueID());
             txtUpdateDT.setText(item.getUpdateDT());
             String[] d_rdogrpSetting = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpSetting.length; i++)
             {
                 if (String.valueOf(item.getSetting()).equals(String.valueOf(d_rdogrpSetting[i])))
                 {
                     rb = (RadioButton)rdogrpSetting.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             if(String.valueOf(item.getDBRequest()).equals("1"))
             {
                chkDBRequest.setChecked(true);
             }
             else if(String.valueOf(item.getDBRequest()).equals("2"))
             {
                chkDBRequest.setChecked(false);
             }
             if(String.valueOf(item.getActive()).equals("1"))
             {
                chkActive.setChecked(true);
             }
             else if(String.valueOf(item.getActive()).equals("2"))
             {
                chkActive.setChecked(false);
             }
             if(String.valueOf(item.getApproval()).equals("1"))
             {
                chkApproval.setChecked(true);
             }
             else if(String.valueOf(item.getApproval()).equals("2"))
             {
                chkApproval.setChecked(false);
             }
             dtpApprovalDT.setText(item.getApprovalDT().toString().length()==0 ? "" : Global.DateConvertDMY(item.getApprovalDT()));
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(DeviceList.this, e.getMessage());
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


      dtpDate = (EditText)findViewById(R.id.dtpApprovalDT);
      if (VariableID.equals("btnApprovalDT"))
      {
          dtpDate = (EditText)findViewById(R.id.dtpApprovalDT);
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

    }
  };


 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
 }
}