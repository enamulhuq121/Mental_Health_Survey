
 package gps;


 import android.Manifest;
 import android.app.Activity;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.graphics.Color;
 import android.location.GnssStatus;
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.os.Build;
 import android.os.Bundle;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.core.content.ContextCompat;
 import android.util.Log;
 import android.view.KeyEvent;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.WindowManager;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.AutoCompleteTextView;
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
 import android.widget.Toast;

 import org.icddrb.mental_health_survey.R;

 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;

 import Common.Connection;
 import Common.Global;
 import Utility.MySharedPreferences;

 public class GPS_Data extends AppCompatActivity {
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
    boolean GPS_SCAN_START = false;

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
    LinearLayout secDCode;
    View lineDCode;
    TextView VlblDCode;
    EditText txtDCode;
    LinearLayout secUPCode;
    View lineUPCode;
    TextView VlblUPCode;
    EditText txtUPCode;
    LinearLayout secUNCode;
    View lineUNCode;
    TextView VlblUNCode;
    EditText txtUNCode;
    LinearLayout secVCode;
    View lineVCode;
    TextView VlblVCode;
    EditText txtVCode;
    LinearLayout secBari;
    View lineBari;
    TextView VlblBari;
    EditText txtBari;
    LinearLayout secLongitude;
    View lineLongitude;
    TextView VlblLongitude;
    EditText txtLongitude;
    LinearLayout secLatitude;
    View lineLatitude;
    TextView VlblLatitude;
    EditText txtLatitude;
    EditText txtAltitude;
    LinearLayout secAccuracy;
    View lineAccuracy;
    TextView VlblAccuracy;
    EditText txtAccuracy;
    LinearLayout secSatelites;
    View lineSatelites;
    TextView VlblSatelites;
    EditText txtSatelites;
    LinearLayout secGPSType;
    View lineGPSType;
    TextView VlblGPSType;
    RadioGroup rdogrpGPSType;
    RadioButton rdoGPSType1;
    RadioButton rdoGPSType2;
    LinearLayout secLandmarkType;
    View lineLandmarkType;
    TextView VlblLandmarkType;
    Spinner spnLandmarkType;
    LinearLayout secLandmarkName;
    View lineLandmarkName;
    TextView VlblLandmarkName;
    AutoCompleteTextView txtLandmarkName;
    LinearLayout secRemarks;
    View lineRemarks;
    TextView VlblRemarks;
    AutoCompleteTextView txtRemarks;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String DCODE = "";
    static String UPCODE = "";
    static String UNCODE = "";
    static String VCODE = "";
    static String BARI = "";

     public static final String BROADCAST_ACTION = "gps_data";
     private static final int TWO_MINUTES = 1000 * 60 * 1;
     public LocationManager locationManager;
     public GPSLocationListener listener;
     public Location previousBestLocation = null;

     Context context;

     Intent intent;
     int counter = 0;

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     intent = new Intent(BROADCAST_ACTION);
     context = this;
   try
     {
         setContentView(R.layout.gps_data);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         DCODE = IDbundle.getString("DCode");
         UPCODE = IDbundle.getString("UPCode");
         UNCODE = IDbundle.getString("UNCode");
         VCODE = IDbundle.getString("VCode");
         BARI = IDbundle.getString("Bari");

         TableName = "GPS_Data";
         MODULEID = 0;
         LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(GPS_Data.this);
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
        Connection.LocalizeLanguage(GPS_Data.this, MODULEID, LANGUAGEID);

         //Hide all skip variables
         secLandmarkType.setVisibility(View.GONE);
         lineLandmarkType.setVisibility(View.GONE);
         secLandmarkName.setVisibility(View.GONE);
         lineLandmarkName.setVisibility(View.GONE);


        DataSearch(DCODE,UPCODE,UNCODE,VCODE,BARI);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(GPS_Data.this, e.getMessage());
         return;
     }


     //GPS Data Update
     if(!C.Existence("Select * from GPS_Data where DCode='"+ DCODE +"' and UPCode='"+ UPCODE +"' and UNCode='"+ UNCODE +"' and VCode='"+ VCODE +"' and Bari='"+ BARI +"'")){
         GPS_SCAN_START = true;
     }

     GPS_Start();

     Button cmdGPSScan = (Button) findViewById(R.id.cmdGPSScan);
     cmdGPSScan.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
             GPS_SCAN_START = true;
         }});
 }

     GnssStatus.Callback mGnssStatusCallback;
     private void GPS_Start(){
         try {
             locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
             listener = new GPSLocationListener();
             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 return;
             }
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 mGnssStatusCallback = new GnssStatus.Callback() {
                     @Override
                     public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
                         super.onSatelliteStatusChanged(status);
                         Log.d("TAG", "onSatelliteStatusChanged: gps_ count "+status.getSatelliteCount());
                         int satelliteCount = status.getSatelliteCount();
                         int usedCount = 0;
                         for (int i = 0; i < satelliteCount; ++i) {
                             if (status.usedInFix(i))
                                 ++usedCount;
                         }

                         Log.d("TAG", "onSatelliteStatusChanged: gps_ count fix "+status.getSatelliteCount());

                         String satCount = txtSatelites.getText().toString();

                         if (!satCount.isEmpty()){
                             try {
                                 int satCountInt = Integer.parseInt(satCount);
                                 if(usedCount > satCountInt){
                                     txtSatelites.setText(String.valueOf(usedCount));
                                 }
                             } catch (NumberFormatException e) {
                                 e.printStackTrace();
                             }
                         }else{
                             txtSatelites.setText(String.valueOf(usedCount));
                         }
                     }
                 };
                 locationManager.registerGnssStatusCallback(mGnssStatusCallback);
             }
             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
         }catch (Exception ex){

         }
     }

 private void Initialization()
 {
   try
     {
         secDCode=(LinearLayout)findViewById(R.id.secDCode);
         lineDCode=(View)findViewById(R.id.lineDCode);
         VlblDCode=(TextView) findViewById(R.id.VlblDCode);
         txtDCode=(EditText) findViewById(R.id.txtDCode);
         txtDCode.setText(DCODE);
         txtDCode.setEnabled(false);
         secUPCode=(LinearLayout)findViewById(R.id.secUPCode);
         lineUPCode=(View)findViewById(R.id.lineUPCode);
         VlblUPCode=(TextView) findViewById(R.id.VlblUPCode);
         txtUPCode=(EditText) findViewById(R.id.txtUPCode);
         txtUPCode.setText(UPCODE);
         txtUPCode.setEnabled(false);
         secUNCode=(LinearLayout)findViewById(R.id.secUNCode);
         lineUNCode=(View)findViewById(R.id.lineUNCode);
         VlblUNCode=(TextView) findViewById(R.id.VlblUNCode);
         txtUNCode=(EditText) findViewById(R.id.txtUNCode);
         txtUNCode.setText(UNCODE);
         txtUNCode.setEnabled(false);
         secVCode=(LinearLayout)findViewById(R.id.secVCode);
         lineVCode=(View)findViewById(R.id.lineVCode);
         VlblVCode=(TextView) findViewById(R.id.VlblVCode);
         txtVCode=(EditText) findViewById(R.id.txtVCode);
         txtVCode.setText(VCODE);
         txtVCode.setEnabled(false);
         secBari=(LinearLayout)findViewById(R.id.secBari);
         lineBari=(View)findViewById(R.id.lineBari);
         VlblBari=(TextView) findViewById(R.id.VlblBari);
         txtBari=(EditText) findViewById(R.id.txtBari);
         txtBari.setText(BARI);
         txtBari.setEnabled(false);
         secLongitude=(LinearLayout)findViewById(R.id.secLongitude);
         lineLongitude=(View)findViewById(R.id.lineLongitude);
         VlblLongitude=(TextView) findViewById(R.id.VlblLongitude);
         txtLongitude=(EditText) findViewById(R.id.txtLongitude);
         secLatitude=(LinearLayout)findViewById(R.id.secLatitude);
         lineLatitude=(View)findViewById(R.id.lineLatitude);
         VlblLatitude=(TextView) findViewById(R.id.VlblLatitude);
         txtLatitude=(EditText) findViewById(R.id.txtLatitude);
         txtAltitude=(EditText) findViewById(R.id.txtAltitude);
         secAccuracy=(LinearLayout)findViewById(R.id.secAccuracy);
         lineAccuracy=(View)findViewById(R.id.lineAccuracy);
         VlblAccuracy=(TextView) findViewById(R.id.VlblAccuracy);
         txtAccuracy=(EditText) findViewById(R.id.txtAccuracy);
         secSatelites=(LinearLayout)findViewById(R.id.secSatelites);
         lineSatelites=(View)findViewById(R.id.lineSatelites);
         VlblSatelites=(TextView) findViewById(R.id.VlblSatelites);
         txtSatelites=(EditText) findViewById(R.id.txtSatelites);
         secGPSType=(LinearLayout)findViewById(R.id.secGPSType);
         lineGPSType=(View)findViewById(R.id.lineGPSType);
         VlblGPSType = (TextView) findViewById(R.id.VlblGPSType);
         rdogrpGPSType = (RadioGroup) findViewById(R.id.rdogrpGPSType);
         rdoGPSType1 = (RadioButton) findViewById(R.id.rdoGPSType1);
         rdoGPSType2 = (RadioButton) findViewById(R.id.rdoGPSType2);
         rdogrpGPSType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpGPSType = new String[] {"1","2"};
             for (int i = 0; i < rdogrpGPSType.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpGPSType.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpGPSType[i];
             }

             if(rbData.equalsIgnoreCase("1"))
             {
                    secLandmarkType.setVisibility(View.GONE);
                    lineLandmarkType.setVisibility(View.GONE);
                    spnLandmarkType.setSelection(0);
                    secLandmarkName.setVisibility(View.GONE);
                    lineLandmarkName.setVisibility(View.GONE);
                    txtLandmarkName.setText("");
             }
             else
             {
                    secLandmarkType.setVisibility(View.VISIBLE);
                    lineLandmarkType.setVisibility(View.VISIBLE);
                    secLandmarkName.setVisibility(View.VISIBLE);
                    lineLandmarkName.setVisibility(View.VISIBLE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secLandmarkType=(LinearLayout)findViewById(R.id.secLandmarkType);
         lineLandmarkType=(View)findViewById(R.id.lineLandmarkType);
         VlblLandmarkType=(TextView) findViewById(R.id.VlblLandmarkType);
         spnLandmarkType=(Spinner) findViewById(R.id.spnLandmarkType);
         List<String> listLandmarkType = new ArrayList<String>();
         
         listLandmarkType.add("");
         listLandmarkType.add("01-Medical college hospital");
         listLandmarkType.add("02-UH&amp;FPC");
         listLandmarkType.add("03-Union Sub-center");
         listLandmarkType.add("04-FWC");
         listLandmarkType.add("05-CC");
         listLandmarkType.add("06-Private clinic/Hospital");
         listLandmarkType.add("07-NGO Clinic");
         listLandmarkType.add("08-Charitable/Trust Run Hospital");
         listLandmarkType.add("09-School");
         listLandmarkType.add("10-College");
         listLandmarkType.add("11-Madrasa");
         listLandmarkType.add("12-Mosque");
         listLandmarkType.add("13-Grave yard");
         listLandmarkType.add("14-Temple");
         listLandmarkType.add("15-Bridge/culvert");
         listLandmarkType.add("16-Bazar");
         listLandmarkType.add("17-Pharmacy");
         listLandmarkType.add("18-Shop");
         listLandmarkType.add("19-Filling station");
         listLandmarkType.add("20-Bus stand");
         listLandmarkType.add("21-Tempo stand");
         listLandmarkType.add("22-Rail station");
         listLandmarkType.add("77-Other");
         ArrayAdapter<String> adptrLandmarkType= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLandmarkType);
         spnLandmarkType.setAdapter(adptrLandmarkType);

         secLandmarkName=(LinearLayout)findViewById(R.id.secLandmarkName);
         lineLandmarkName=(View)findViewById(R.id.lineLandmarkName);
         VlblLandmarkName=(TextView) findViewById(R.id.VlblLandmarkName);
         txtLandmarkName=(AutoCompleteTextView) findViewById(R.id.txtLandmarkName);
         txtLandmarkName.setAdapter(C.getArrayAdapter("Select distinct LandmarkName from "+ TableName +" order by LandmarkName"));

         txtLandmarkName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

             }
         });
         txtLandmarkName.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT = 2;
         
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (txtLandmarkName.getRight() - txtLandmarkName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         ((EditText)v).setText("");
                         return true;
                     }
                 }
                 return false;
             }
         });
         secRemarks=(LinearLayout)findViewById(R.id.secRemarks);
         lineRemarks=(View)findViewById(R.id.lineRemarks);
         VlblRemarks=(TextView) findViewById(R.id.VlblRemarks);
         txtRemarks=(AutoCompleteTextView) findViewById(R.id.txtRemarks);
         txtRemarks.setAdapter(C.getArrayAdapter("Select distinct Remarks from "+ TableName +" order by Remarks"));

         txtRemarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

             }
         });
         txtRemarks.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT = 2;
         
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (txtRemarks.getRight() - txtRemarks.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         ((EditText)v).setText("");
                         return true;
                     }
                 }
                 return false;
             }
         });
     }
     catch(Exception  e)
     {
         Connection.MessageBox(GPS_Data.this, e.getMessage());
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
         	Connection.MessageBox(GPS_Data.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         GPS_Data_DataModel objSave = new GPS_Data_DataModel();
         objSave.setDCode(txtDCode.getText().toString());
         objSave.setUPCode(txtUPCode.getText().toString());
         objSave.setUNCode(txtUNCode.getText().toString());
         objSave.setVCode(txtVCode.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setLongitude(txtLongitude.getText().toString());
         objSave.setLatitude(txtLatitude.getText().toString());
         objSave.setAltitude(txtAltitude.getText().toString());
         objSave.setAccuracy(txtAccuracy.getText().toString().length()==0?"0":txtAccuracy.getText().toString());
         objSave.setSatelites(txtSatelites.getText().toString().length()==0?"0":txtSatelites.getText().toString());
         String[] d_rdogrpGPSType = new String[] {"1","2"};
         objSave.setGPSType(0);
         for (int i = 0; i < rdogrpGPSType.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpGPSType.getChildAt(i);
             if (rb.isChecked()) objSave.setGPSType(Integer.valueOf(d_rdogrpGPSType[i]));
         }

         objSave.setLandmarkType(Integer.valueOf(spnLandmarkType.getSelectedItemPosition() == 0 ? "0" : Connection.SelectedSpinnerValue(spnLandmarkType.getSelectedItem().toString(), "-")));
         objSave.setLandmarkName(txtLandmarkName.getText().toString());
         objSave.setRemarks(txtRemarks.getText().toString());
         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         //objSave.setLat(Double.toString(currentLatitude));
         //objSave.setLon(Double.toString(currentLongitude));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(GPS_Data.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(GPS_Data.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(GPS_Data.this, e.getMessage());
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
         if(txtDCode.getText().toString().length()==0 & secDCode.isShown())
           {
             ValidationMsg += "\nRequired field: District.";
             secDCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtUPCode.getText().toString().length()==0 & secUPCode.isShown())
           {
             ValidationMsg += "\nRequired field: Upazila.";
             secUPCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtUNCode.getText().toString().length()==0 & secUNCode.isShown())
           {
             ValidationMsg += "\nRequired field: Union.";
             secUNCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtVCode.getText().toString().length()==0 & secVCode.isShown())
           {
             ValidationMsg += "\nRequired field: Village.";
             secVCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             ValidationMsg += "\nRequired field: Bari.";
             secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtLongitude.getText().toString().length()==0 & secLongitude.isShown())
           {
             ValidationMsg += "\nRequired field: Longitude.";
             secLongitude.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtLatitude.getText().toString().length()==0 & secLatitude.isShown())
           {
             ValidationMsg += "\nRequired field: Latitude.";
             secLatitude.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtAccuracy.getText().toString().length()==0 & secAccuracy.isShown())
           {
             ValidationMsg += "\nRequired field: Accuracy.";
             secAccuracy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(secAccuracy.isShown() & Integer.valueOf(txtAccuracy.getText().toString().length()==0 ? "88" : txtAccuracy.getText().toString()) > 8)
           {
             ValidationMsg += "\nValue should be less than or equal 8 meter(Accuracy).";
             secAccuracy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtSatelites.getText().toString().length()==0 & secSatelites.isShown())
           {
             ValidationMsg += "\nRequired field: Satellites Connected.";
             secSatelites.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(secSatelites.isShown() & Integer.valueOf(txtSatelites.getText().toString().length()==0 ? "0" : txtSatelites.getText().toString()) <3 )
           {
             ValidationMsg += "\nValue should be greater or equal 3(Satellites).";
             secSatelites.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(!rdoGPSType1.isChecked() & !rdoGPSType2.isChecked() & secGPSType.isShown())
           {
             ValidationMsg += "\nRequired field: Landmark Type.";
             secGPSType.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(spnLandmarkType.getSelectedItemPosition()==0  & secLandmarkType.isShown())
           {
             ValidationMsg += "\nRequired field: Landmark Type.";
             secLandmarkType.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
           }
         if(txtLandmarkName.getText().toString().length()==0 & secLandmarkName.isShown())
           {
             ValidationMsg += "\nRequired field: Landmark Name.";
             secLandmarkName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
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
             secDCode.setBackgroundColor(Color.WHITE);
             secUPCode.setBackgroundColor(Color.WHITE);
             secUNCode.setBackgroundColor(Color.WHITE);
             secVCode.setBackgroundColor(Color.WHITE);
             secBari.setBackgroundColor(Color.WHITE);
             secLongitude.setBackgroundColor(Color.WHITE);
             secLatitude.setBackgroundColor(Color.WHITE);
             secAccuracy.setBackgroundColor(Color.WHITE);
             secAccuracy.setBackgroundColor(Color.WHITE);
             secSatelites.setBackgroundColor(Color.WHITE);
             secSatelites.setBackgroundColor(Color.WHITE);
             secGPSType.setBackgroundColor(Color.WHITE);
             secLandmarkType.setBackgroundColor(Color.WHITE);
             secLandmarkName.setBackgroundColor(Color.WHITE);
             secRemarks.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String DCode, String UPCode, String UNCode, String VCode, String Bari)
     {
       try
        {     
           RadioButton rb;
           GPS_Data_DataModel d = new GPS_Data_DataModel();
           String SQL = "Select * from "+ TableName +"  Where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"'";
           List<GPS_Data_DataModel> data = d.SelectAll(this, SQL);
           for(GPS_Data_DataModel item : data){
             txtDCode.setText(item.getDCode());
             txtUPCode.setText(item.getUPCode());
             txtUNCode.setText(item.getUNCode());
             txtVCode.setText(item.getVCode());
             txtBari.setText(item.getBari());
             txtLongitude.setText(item.getLongitude());
             txtLatitude.setText(item.getLatitude());
             txtAccuracy.setText(String.valueOf(item.getAccuracy()));
             txtSatelites.setText(String.valueOf(item.getSatelites()));
             String[] d_rdogrpGPSType = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpGPSType.length; i++)
             {
                 if (String.valueOf(item.getGPSType()).equals(String.valueOf(d_rdogrpGPSType[i])))
                 {
                     rb = (RadioButton)rdogrpGPSType.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             spnLandmarkType.setSelection(Global.SpinnerItemPositionAnyLength(spnLandmarkType, String.valueOf(item.getLandmarkType())));
             txtLandmarkName.setText(item.getLandmarkName());
             txtRemarks.setText(item.getRemarks());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(GPS_Data.this, e.getMessage());
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


     protected boolean isBetterLocation(Location location, Location currentBestLocation) {
         if (currentBestLocation == null) {
             // A new location is always better than no location
             return true;
         }

         // Check whether the new location fix is newer or older
         long timeDelta = location.getTime() - currentBestLocation.getTime();
         boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
         boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
         boolean isNewer = timeDelta > 0;

         // If it's been more than two minutes since the current location, use the new location
         // because the user has likely moved
         if (isSignificantlyNewer) {
             return true;
             // If the new location is more than two minutes older, it must be worse
         } else if (isSignificantlyOlder) {
             return false;
         }

         // Check whether the new location fix is more or less accurate
         int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
         boolean isLessAccurate = accuracyDelta > 0;
         boolean isMoreAccurate = accuracyDelta < 0;
         boolean isSignificantlyLessAccurate = accuracyDelta > 200;

         // Check if the old and new location are from the same provider
         boolean isFromSameProvider = isSameProvider(location.getProvider(),
                 currentBestLocation.getProvider());

         // Determine location quality using a combination of timeliness and accuracy
         if (isMoreAccurate) {
             return true;
         } else if (isNewer && !isLessAccurate) {
             return true;
         } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
             return true;
         }
         return false;
     }

     /** Checks whether two providers are the same */
     private boolean isSameProvider(String provider1, String provider2) {
         if (provider1 == null) {
             return provider2 == null;
         }
         return provider1.equals(provider2);
     }

     // turning off the GPS if its in on state. to avoid the battery drain.
     @Override
     protected void onDestroy() {
         // TODO Auto-generated method stub
         super.onDestroy();
         locationManager.removeUpdates(listener);
     }

     @Override
     protected void onStop() {

         locationManager.removeUpdates(listener);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             locationManager.unregisterGnssStatusCallback(
                     mGnssStatusCallback
             );
         }
         super.onStop();
     }

 public static Thread performOnBackgroundThread(final Runnable runnable) {
     final Thread t = new Thread() {
         @Override
         public void run() {
             try {
                 runnable.run();
             } finally {

             }
         }
     };
     t.start();
     return t;
 }

     public class GPSLocationListener implements LocationListener {
         public void onLocationChanged(final Location loc) {
             try{
                 Log.i("**********", "Location changed");
                 if (isBetterLocation(loc, previousBestLocation)) {
                     if(GPS_SCAN_START) {
                         txtLatitude.setText(String.valueOf(loc.getLatitude()));
                         txtLongitude.setText(String.valueOf(loc.getLongitude()));
                         txtAltitude.setText(String.valueOf(loc.getAltitude()));
                         txtAccuracy.setText(String.valueOf(loc.getAccuracy()));
                         if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                             txtSatelites.setText(String.valueOf(loc.getExtras().getInt("satellites", 0)));
                         }
                     }
                     Toast.makeText(context, "Latitude" + loc.getLatitude() + "\nLongitude" + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                     intent.putExtra("Latitude", loc.getLatitude());
                     intent.putExtra("Longitude", loc.getLongitude());
                     intent.putExtra("Provider", loc.getProvider());
                     sendBroadcast(intent);
                 }
             }catch (Exception ex){
                 ex.printStackTrace();
                 //Log.d("TAG", "onLocationChanged: gps_ exception_ "+ex.toString());
             }
         }

         public void onProviderDisabled(String provider) {
             Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
         }

         public void onProviderEnabled(String provider) {
             Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
         }

         public void onStatusChanged(String provider, int status, Bundle extras) {
             Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
         }
     }
 }