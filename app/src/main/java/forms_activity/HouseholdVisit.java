
package forms_activity;


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
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.icddrb.kalaazar_pkdl.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;
import forms_datamodel.HouseholdVisit_DataModel;


public class HouseholdVisit extends AppCompatActivity {
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
    LinearLayout secDCode;
    View lineDCode;
    TextView VlblDCode;
    EditText txtDCode;
    EditText txtDName;
    LinearLayout secUPCode;
    View lineUPCode;
    TextView VlblUPCode;
    EditText txtUPCode;
    EditText txtUPName;
    LinearLayout secUNCode;
    View lineUNCode;
    TextView VlblUNCode;
    EditText txtUNCode;
    EditText txtUNName;
    LinearLayout secVCode;
    View lineVCode;
    TextView VlblVCode;
    EditText txtVCode;
    EditText txtVName;
    LinearLayout secBari;
    View lineBari;
    TextView VlblBari;
    EditText txtBari;
    LinearLayout secHHNo;
    View lineHHNo;
    TextView VlblHHNo;
    EditText txtHHNo;
    LinearLayout secVisitNo;
    View lineVisitNo;
    TextView VlblVisitNo;
    EditText txtVisitNo;
    LinearLayout secVisitDate;
    View lineVisitDate;
    TextView VlblVisitDate;
    EditText dtpVisitDate;
    LinearLayout secVisitStatus;
    View lineVisitStatus;
    TextView VlblVisitStatus;
    Spinner spnVisitStatus;
    LinearLayout secVisitStatusOT;
    View lineVisitStatusOT;
    TextView VlblVisitStatusOT;
    EditText txtVisitStatusOT;
    LinearLayout secCMWRATotal;
    EditText txtCMWRATotal;
    EditText txtTotalMember;
    LinearLayout secTotalMember;

    RadioGroup rdogrpEMWRA;
    RadioButton rdoEMWRA1;
    RadioButton rdoEMWRA2;
    LinearLayout secEMWRA;
    EditText txtCluster;

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
    static String CLUSTER = "";
    static String VCODE = "";
    static String BARI = "";
    static String HHNO = "";
    static String VISITNO = "";
    static String ROUNDNO = "";
    static String BARINAME = "";

    public static final String BROADCAST_ACTION = "gps_data";
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    public LocationManager locationManager;
    public GPSLocationListener listener;
    public Location previousBestLocation = null;
    Intent intent;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(BROADCAST_ACTION);
        context = this;
        try
        {
            setContentView(R.layout.householdvisit);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            C = new Connection(this);
            g = Global.getInstance();

            STARTTIME = g.CurrentTime24();
            DEVICEID  = sp.getValue(this, "deviceid");
            ENTRYUSER = sp.getValue(this, "userid");
            ROUNDNO   = "0"; //sp.getValue(this, "roundno");
            IDbundle = getIntent().getExtras();
            DCODE = IDbundle.getString("DCode");
            UPCODE = IDbundle.getString("UPCode");
            UNCODE = IDbundle.getString("UNCode");
            CLUSTER = IDbundle.getString("Cluster");
            VCODE = IDbundle.getString("VCode");
            BARI = IDbundle.getString("Bari");
            BARINAME = IDbundle.getString("BariName");
            HHNO = IDbundle.getString("HHNo");
            VISITNO = IDbundle.getString("VisitNo");

            TableName = "HouseholdVisit";
            MODULEID = 0;
            LANGUAGEID = Integer.parseInt(sp.getValue(this, "languageid"));
            lblHeading = (TextView)findViewById(R.id.lblHeading);

            ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
            cmdBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(HouseholdVisit.this);
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
            Connection.LocalizeLanguage(HouseholdVisit.this, MODULEID, LANGUAGEID);


            dtpVisitDate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT  = 2;
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (dtpVisitDate.getRight() - dtpVisitDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            VariableID = "btnVisitDate"; showDialog(DATE_DIALOG);
                            return true;
                        }
                    }
                    return false;
                }
            });


            secTotalMember.setVisibility(View.GONE);
            secEMWRA.setVisibility(View.GONE);
            secCMWRATotal.setVisibility(View.GONE);

            //Hide all skip variables
            DataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,HHNO,VISITNO,ROUNDNO);
            HouseholdDataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,HHNO);
            GPS_Start();

            Button cmdSave = (Button) findViewById(R.id.cmdSave);
            cmdSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DataSave();
                }});
        }
        catch(Exception  e)
        {
            Connection.MessageBox(HouseholdVisit.this, e.getMessage());
            return;
        }
    }

    private void GPS_Start(){
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listener = new GPSLocationListener();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
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
            TextView lblBariname = findViewById(R.id.lblBariname);
            lblBariname.setText("বাড়ি: "+ BARINAME);
            secDCode=(LinearLayout)findViewById(R.id.secDCode);
            lineDCode=(View)findViewById(R.id.lineDCode);
            VlblDCode=(TextView) findViewById(R.id.VlblDCode);
            txtDCode=(EditText) findViewById(R.id.txtDCode);
            txtDName=(EditText) findViewById(R.id.txtDName);
            txtDName.setText(IDbundle.getString("DName"));
            txtDCode.setText(DCODE);
            txtDCode.setEnabled(false);
            secUPCode=(LinearLayout)findViewById(R.id.secUPCode);
            lineUPCode=(View)findViewById(R.id.lineUPCode);
            VlblUPCode=(TextView) findViewById(R.id.VlblUPCode);
            txtUPCode=(EditText) findViewById(R.id.txtUPCode);
            txtUPName=(EditText) findViewById(R.id.txtUPName);
            txtUPName.setText(IDbundle.getString("UPName"));
            txtUPCode.setText(UPCODE);
            txtUPCode.setEnabled(false);
            secUNCode=(LinearLayout)findViewById(R.id.secUNCode);
            lineUNCode=(View)findViewById(R.id.lineUNCode);
            VlblUNCode=(TextView) findViewById(R.id.VlblUNCode);
            txtUNCode=(EditText) findViewById(R.id.txtUNCode);
            txtUNName=(EditText) findViewById(R.id.txtUNName);
            txtUNName.setText(IDbundle.getString("UNName"));
            txtUNCode.setText(UNCODE);
            txtUNCode.setEnabled(false);

            txtCluster=findViewById(R.id.txtCluster);
            txtCluster.setText(CLUSTER);
            secVCode=(LinearLayout)findViewById(R.id.secVCode);
            lineVCode=(View)findViewById(R.id.lineVCode);
            VlblVCode=(TextView) findViewById(R.id.VlblVCode);
            txtVCode=(EditText) findViewById(R.id.txtVCode);
            txtVName=(EditText) findViewById(R.id.txtVName);
            txtVName.setText(IDbundle.getString("VName"));
            txtVCode.setText(VCODE);
            txtVCode.setEnabled(false);
            secBari=(LinearLayout)findViewById(R.id.secBari);
            lineBari=(View)findViewById(R.id.lineBari);
            VlblBari=(TextView) findViewById(R.id.VlblBari);
            txtBari=(EditText) findViewById(R.id.txtBari);
            txtBari.setText(BARI);
            txtBari.setEnabled(false);
            secHHNo=(LinearLayout)findViewById(R.id.secHHNo);
            lineHHNo=(View)findViewById(R.id.lineHHNo);
            VlblHHNo=(TextView) findViewById(R.id.VlblHHNo);
            txtHHNo=(EditText) findViewById(R.id.txtHHNo);
            txtHHNo.setText(HHNO);
            txtHHNo.setEnabled(false);
            secVisitNo=(LinearLayout)findViewById(R.id.secVisitNo);
            lineVisitNo=(View)findViewById(R.id.lineVisitNo);
            VlblVisitNo=(TextView) findViewById(R.id.VlblVisitNo);
            txtVisitNo=(EditText) findViewById(R.id.txtVisitNo);

            if(VISITNO.length()==0) {
                String VNO = C.ReturnSingleValue("Select VisitNo from HouseholdVisit where DCode='"+ DCODE +"' and UPCode='"+ UPCODE +"' and UNCode='"+ UNCODE +"' and Cluster='"+ CLUSTER +"' and VCode='"+ VCODE +"' and Bari='"+ BARI +"' and HHNo='"+ HHNO +"' and VisitStatus='1' and Rnd='"+ ROUNDNO +"'");
                if(VNO.length()>0) {
                    txtVisitNo.setText(VNO);
                    VISITNO = VNO;
                }
                else
                    txtVisitNo.setText(NewVisitNumber(DCODE, UPCODE, UNCODE, CLUSTER, VCODE, BARI, HHNO));
            }
            else
                txtVisitNo.setText("");


            txtVisitNo.setEnabled(false);
            secVisitDate=(LinearLayout)findViewById(R.id.secVisitDate);
            lineVisitDate=(View)findViewById(R.id.lineVisitDate);
            VlblVisitDate=(TextView) findViewById(R.id.VlblVisitDate);
            dtpVisitDate=(EditText) findViewById(R.id.dtpVisitDate);
            dtpVisitDate.setText(Global.DateNowDMY());
            secVisitStatus=(LinearLayout)findViewById(R.id.secVisitStatus);
            lineVisitStatus=(View)findViewById(R.id.lineVisitStatus);
            VlblVisitStatus=(TextView) findViewById(R.id.VlblVisitStatus);
            spnVisitStatus=(Spinner) findViewById(R.id.spnVisitStatus);
            List<String> listVisitStatus = new ArrayList<String>();

            listVisitStatus.add("");
            listVisitStatus.add("1-ইন্টারভিউ সমাপ্ত");
            listVisitStatus.add("2-বাড়ি পরিদর্শনের সময় খানার কোন সদস্যকে বা উপযুক্ত কাউকে পাওয়া যায় নাই");
            listVisitStatus.add("3-ইন্টারভিউ বাতিল");
            listVisitStatus.add("4-ইন্টারভিউ দিতে রাজী নয়");
            listVisitStatus.add("5-বাসস্থানটি খুঁজে পাওয়া যায় নাই");
            listVisitStatus.add("6-সম্প্রতি প্রসবকারী মহিলা অনুপস্থিত");
            listVisitStatus.add("7-গত ০১ আগষ্ট ২০১৯ থেকে ৩০ সেপ্টেম্বর ২০২০সময়ের মধ্যে এই খানার কোন মহিলার গর্ভ শেষ হয় নাই");
            listVisitStatus.add("8-অন্যান্য");
            ArrayAdapter<String> adptrVisitStatus= new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, listVisitStatus);
            spnVisitStatus.setAdapter(adptrVisitStatus);

            secVisitStatusOT=(LinearLayout)findViewById(R.id.secVisitStatusOT);
            lineVisitStatusOT=(View)findViewById(R.id.lineVisitStatusOT);
            VlblVisitStatusOT=(TextView) findViewById(R.id.VlblVisitStatusOT);
            txtVisitStatusOT=(EditText) findViewById(R.id.txtVisitStatusOT);
            spnVisitStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if(spnVisitStatus.getSelectedItemPosition()==0) return;
                    if(spnVisitStatus.getSelectedItem().toString().split("-")[0].equals("8")){
                        secVisitStatusOT.setVisibility(View.VISIBLE);
                    }else{
                        secVisitStatusOT.setVisibility(View.GONE);
                        txtVisitStatusOT.setText("");
                    }

                    if(spnVisitStatus.getSelectedItem().toString().split("-")[0].equals("1")){
                        //secTotalMember.setVisibility(View.VISIBLE);
                        secEMWRA.setVisibility(View.VISIBLE);
                        HouseholdDataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,HHNO);
                    }else{
                        secTotalMember.setVisibility(View.GONE);
                        secEMWRA.setVisibility(View.GONE);
                        rdogrpEMWRA.clearCheck();
                        secCMWRATotal.setVisibility(View.GONE);
                        txtCMWRATotal.setText("");
                        secTotalMember.setVisibility(View.GONE);
                        txtTotalMember.setText("");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            secCMWRATotal=(LinearLayout)findViewById(R.id.secCMWRATotal);
            txtCMWRATotal=(EditText)findViewById(R.id.txtCMWRATotal);
            txtTotalMember=findViewById(R.id.txtTotalMember);
            secTotalMember=findViewById(R.id.secTotalMember);
            rdogrpEMWRA=(RadioGroup)findViewById(R.id.rdogrpEMWRA) ;
            rdoEMWRA1=(RadioButton)findViewById(R.id.rdoEMWRA1) ;
            rdoEMWRA2=(RadioButton)findViewById(R.id.rdoEMWRA2) ;
            secEMWRA = (LinearLayout)findViewById(R.id.secEMWRA);

            rdogrpEMWRA.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
                    if(rdoEMWRA1.isChecked()){
                        secCMWRATotal.setVisibility(View.VISIBLE);
                        HouseholdDataSearch(DCODE,UPCODE,UNCODE,CLUSTER,VCODE,BARI,HHNO);
                    }else{
                        secCMWRATotal.setVisibility(View.GONE);
                        txtCMWRATotal.setText("");
                    }
                }
                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });


            secVisitStatusOT.setVisibility(View.GONE);
            secCMWRATotal.setVisibility(View.GONE);
        }
        catch(Exception  e)
        {
            Connection.MessageBox(HouseholdVisit.this, e.getMessage());
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
                Connection.MessageBox(HouseholdVisit.this, ValidationMSG);
                return;
            }

            String SQL = "";
            RadioButton rb;

            HouseholdVisit_DataModel objSave = new HouseholdVisit_DataModel();
            objSave.setDCode(txtDCode.getText().toString());
            objSave.setUPCode(txtUPCode.getText().toString());
            objSave.setUNCode(txtUNCode.getText().toString());
            objSave.setCluster(txtCluster.getText().toString());
            objSave.setVCode(txtVCode.getText().toString());
            objSave.setBari(txtBari.getText().toString());
            objSave.setHHNo(txtHHNo.getText().toString());
            objSave.setVisitNo(Integer.valueOf(txtVisitNo.getText().toString().length()==0?"0":txtVisitNo.getText().toString()));
            objSave.setVisitDate(dtpVisitDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpVisitDate.getText().toString()) : dtpVisitDate.getText().toString());
            objSave.setVisitStatus(Integer.valueOf(spnVisitStatus.getSelectedItemPosition() == 0 ? "0" : Connection.SelectedSpinnerValue(spnVisitStatus.getSelectedItem().toString(), "-")));
            objSave.setVisitStatusOT(txtVisitStatusOT.getText().toString());
            objSave.setCMWRATotal(Integer.valueOf(txtCMWRATotal.getText().toString().length()==0?"0":txtCMWRATotal.getText().toString()));

            if(rdoEMWRA1.isChecked()) objSave.setEMWRA("1");
            else if(rdoEMWRA2.isChecked()) objSave.setEMWRA("2");
            else objSave.setEMWRA("");

            objSave.setTotalMember(Integer.valueOf(txtTotalMember.getText().toString().length()==0?"0":txtTotalMember.getText().toString()));
            objSave.setRoundNo(Integer.parseInt(ROUNDNO));
            objSave.setStartTime(STARTTIME);
            objSave.setEndTime(g.CurrentTime24());
            objSave.setDeviceID(DEVICEID);
            objSave.setEntryUser(ENTRYUSER); //from data entry user list
            objSave.setLat(MySharedPreferences.getValue(this,"lat"));
            objSave.setLon(MySharedPreferences.getValue(this,"lon"));

            String status = objSave.SaveUpdateData(this);
            if(status.length()==0) {
                if(secCMWRATotal.isShown()) {
                    C.SaveData("Update Household set VisitStatus='" + (spnVisitStatus.getSelectedItemPosition() == 0 ? "0" : spnVisitStatus.getSelectedItem().toString().split("-")[0]) + "', " +
                            " TotalMember='" + (txtTotalMember.getText().toString().length() == 0 ? "0" : txtTotalMember.getText().toString()) + "'," +
                            " CMWRATotal='" + (txtCMWRATotal.getText().toString().length() == 0 ? "0" : txtCMWRATotal.getText().toString()) + "'," +
                            " Upload='2'" +
                            " where " +
                            " DCode='" + txtDCode.getText().toString() + "' " +
                            " and UPCode='" + txtUPCode.getText().toString() + "'" +
                            " and UNCode='" + txtUNCode.getText().toString() + "'" +
                            " and Cluster='" + txtCluster.getText().toString() + "'" +
                            " and VCode='" + txtVCode.getText().toString() + "'" +
                            " and Bari='" + txtBari.getText().toString() + "'" +
                            " and HHNo='" + txtHHNo.getText().toString() + "'");
                }else{
                    C.SaveData("Update Household set VisitStatus='"+ (spnVisitStatus.getSelectedItemPosition() == 0 ? "0" : spnVisitStatus.getSelectedItem().toString().split("-")[0]) +"'," +
                            " TotalMember='" + (txtTotalMember.getText().toString().length() == 0 ? "0" : txtTotalMember.getText().toString()) + "'," +
                            " Upload='2'" +
                            " where " +
                            " DCode='"+ txtDCode.getText().toString() +"' " +
                            " and UPCode='"+ txtUPCode.getText().toString() +"'" +
                            " and UNCode='"+ txtUNCode.getText().toString() +"'" +
                            " and Cluster='" + txtCluster.getText().toString() + "'" +
                            " and VCode='"+ txtVCode.getText().toString() +"'" +
                            " and Bari='"+ txtBari.getText().toString() +"'" +
                            " and HHNo='"+ txtHHNo.getText().toString() +"'");
                }


                int totalmwra = Integer.valueOf(txtCMWRATotal.getText().toString().length()==0?"0":txtCMWRATotal.getText().toString());
                if(totalmwra>0) {
                    finish();
                    /*Bundle IDbundle = new Bundle();
                    IDbundle.putString("DCode", txtDCode.getText().toString());
                    //IDbundle.putString("DName", spnDCode.getSelectedItem().toString().split("-")[1]);
                    IDbundle.putString("UPCode", txtUPCode.getText().toString());
                    IDbundle.putString("UPName", txtUPName.getText().toString());
                    IDbundle.putString("UNCode", txtUNCode.getText().toString());
                    IDbundle.putString("UNName", txtUNName.getText().toString());
                    IDbundle.putString("Cluster", txtCluster.getText().toString());
                    IDbundle.putString("VCode", txtVCode.getText().toString());
                    IDbundle.putString("VName", txtVName.getText().toString());
                    IDbundle.putString("Bari", txtBari.getText().toString());
                    IDbundle.putString("HHNo", txtHHNo.getText().toString());
                    IDbundle.putString("roundno", ROUNDNO);
                    IDbundle.putString("TotalEMWRA", txtCMWRATotal.getText().toString());
                    IDbundle.putString("visitstatus",  (spnVisitStatus.getSelectedItemPosition() == 0 ? "" : spnVisitStatus.getSelectedItem().toString().split("-")[0]));

                    Intent f1 = new Intent(getApplicationContext(), data_RDW_list.class);
                    f1.putExtras(IDbundle);
                    startActivityForResult(f1, 1);*/
                }else{
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("res", txtBari.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);
                    Connection.MessageBox(HouseholdVisit.this, "Saved Successfully");
                }
            }
            else{
                Connection.MessageBox(HouseholdVisit.this, status);
                return;
            }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(HouseholdVisit.this, e.getMessage());
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
                ValidationMsg += "\n3. Required field: Unions.";
                secUNCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(txtVCode.getText().toString().length()==0 & secVCode.isShown())
            {
                ValidationMsg += "\n4. Required field: Village.";
                secVCode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(txtBari.getText().toString().length()==0 & secBari.isShown())
            {
                ValidationMsg += "\n5. Required field: Bari Number.";
                secBari.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(txtHHNo.getText().toString().length()==0 & secHHNo.isShown())
            {
                ValidationMsg += "\n6. Required field: Household Number.";
                secHHNo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(txtVisitNo.getText().toString().length()==0 & secVisitNo.isShown())
            {
                ValidationMsg += "\n7. Required field: Visit Number.";
                secVisitNo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
         /*if(secVisitNo.isShown() & (Integer.valueOf(txtVisitNo.getText().toString().length()==0 ? "1" : txtVisitNo.getText().toString()) < 1 || Integer.valueOf(txtVisitNo.getText().toString().length()==0 ? "5" : txtVisitNo.getText().toString()) > 5))
           {
             ValidationMsg += "\n7. Value should be between 1 and 5(Visit Number).";
             secVisitNo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }*/
            DV = Global.DateValidate(dtpVisitDate.getText().toString());
            if(DV.length()!=0 & secVisitDate.isShown())
            {
                ValidationMsg += "\n8. Required field/Not a valid date format: Visit Date.";
                secVisitDate.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(spnVisitStatus.getSelectedItemPosition()==0  & secVisitStatus.isShown())
            {
                ValidationMsg += "\n9. Required field: Result of Household Visit.";
                secVisitStatus.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }
            if(txtVisitStatusOT.getText().toString().length()==0 & secVisitStatusOT.isShown())
            {
                ValidationMsg += "\nRequired field: Other Status.";
                secVisitStatusOT.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }

            /*if(txtTotalMember.getText().toString().length()==0 & secTotalMember.isShown())
            {
                ValidationMsg += "\nRequired field: 10. Total number of household member.";
                secTotalMember.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
            }

            int member = Integer.valueOf(txtTotalMember.getText().toString().length()==0?"0":txtTotalMember.getText().toString());
            int total_cmwra = Integer.valueOf(C.ReturnSingleValue("Select count(*)total from EMWRA where DCode='"+ DCODE +"' and UPCode='"+ UPCODE +"' and UNCode='"+ UNCODE +"' and VCode='"+ VCODE +"' and Bari='"+ BARI +"' and HHNo='"+ HHNO +"' and length(ExType)=0"));
            if(txtTotalMember.getText().toString().length()>0 & member==0 & total_cmwra>0 & secTotalMember.isShown())
            {
                ValidationMsg += "\n10. Total number of household member should be greater than zero(0).";
                secTotalMember.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
            }*/

            if(!rdoEMWRA1.isChecked() & !rdoEMWRA2.isChecked() & secEMWRA.isShown()){
                ValidationMsg += "\n5. খানায় ১৫-৪৯ বছর বয়সের মধ্যে কখনো বিবাহ হয়েছে এমন মহিলা/মহিলারা আছেন কিনা.";
                secEMWRA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }


            if(txtCMWRATotal.getText().toString().length()==0  & secCMWRATotal.isShown())
            {
                ValidationMsg += "\n6. Required field: কখনো বিবাহ হয়েছিল ১৫-৪৯ বছরে এমন কতজন মহিলা আছেন.";
                secCMWRATotal.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }

            int cmwra = Integer.valueOf(txtCMWRATotal.getText().toString().length()==0?"0":txtCMWRATotal.getText().toString());
         /*if(txtCMWRATotal.getText().toString().length()>0 & emwra==0 & secCMWRATotal.isShown())
         {
             ValidationMsg += "\n11. Number of currently  married women <50 years of age should be greater than zero(0).";
             secTotalMember.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
         }*/
            if(secCMWRATotal.isShown() & cmwra==0){
                ValidationMsg += "\n6. খানায় ১৫-৪৯ বছর বয়সের মধ্যে কখনো বিবাহ হয়েছে এমন মহিলা/মহিলারা সংখ্যা ০ এর বেশি হতে হবে.";
                secCMWRATotal.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_Section_Highlight));
            }

            /*if(cmwra>member){
                ValidationMsg += "\n10/12. Number of currently  married women 15 to 49 years of age should not be greater than the total number of household member.";
                secTotalMember.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
                secCMWRATotal.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
            }*/


        }
        catch(Exception  e)
        {
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
            secHHNo.setBackgroundColor(Color.WHITE);
            secVisitNo.setBackgroundColor(Color.WHITE);
            secVisitNo.setBackgroundColor(Color.WHITE);
            secVisitDate.setBackgroundColor(Color.WHITE);
            secVisitStatus.setBackgroundColor(Color.WHITE);
            secVisitStatusOT.setBackgroundColor(Color.WHITE);
            secCMWRATotal.setBackgroundColor(Color.WHITE);
            secTotalMember.setBackgroundColor(Color.WHITE);
            secEMWRA.setBackgroundColor(Color.WHITE);
        }
        catch(Exception  e)
        {
        }
    }

    private void DataSearch(String DCode, String UPCode, String UNCode, String Cluster, String VCode, String Bari, String HHNo, String VisitNo, String RoundNo)
    {
        try
        {
            RadioButton rb;
            HouseholdVisit_DataModel d = new HouseholdVisit_DataModel();
            String SQL = "Select * from "+ TableName +"  " +
                    " Where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"' and HHNo='"+ HHNo +"' and VisitNo='"+ VisitNo +"'" +
                    " and Rnd='"+ RoundNo +"'";
            List<HouseholdVisit_DataModel> data = d.SelectAll(this, SQL);
            for(HouseholdVisit_DataModel item : data){
                dtpVisitDate.setText(item.getVisitDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVisitDate()));
                spnVisitStatus.setSelection(Global.SpinnerItemPositionAnyLength(spnVisitStatus, String.valueOf(item.getVisitStatus())));
                txtVisitStatusOT.setText(item.getVisitStatusOT());
                txtTotalMember.setText(String.valueOf(item.getTotalMember()));

                if(item.getEMWRA().equals("1")) rdoEMWRA1.setChecked(true);
                else if(item.getEMWRA().equals("2")) rdoEMWRA2.setChecked(true);
                else rdogrpEMWRA.clearCheck();

                txtCMWRATotal.setText(String.valueOf(item.getCMWRATotal()));

            }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(HouseholdVisit.this, e.getMessage());
            return;
        }
    }

    private void HouseholdDataSearch(String DCode, String UPCode, String UNCode, String Cluster, String VCode, String Bari, String HHNo)
    {
        try
        {
            RadioButton rb;
            HouseholdVisit_DataModel d = new HouseholdVisit_DataModel();
            String SQL = "Select DCode, UPCode, UNCode, VCode, Bari, HHNo, HHHead, ifnull(TotalMember,'')TotalMember, ifnull(CMWRATotal,'')CMWRATotal from Household" +
                    " Where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"' and HHNo='"+ HHNo +"'";
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {

                int total_mem = Integer.parseInt(cur.getString(cur.getColumnIndex("TotalMember")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("TotalMember")));
                if(total_mem>0)
                    txtTotalMember.setText((cur.getString(cur.getColumnIndex("TotalMember")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("TotalMember"))));

                if(Integer.valueOf((cur.getString(cur.getColumnIndex("CMWRATotal")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("CMWRATotal"))))>0){
                    rdoEMWRA1.setChecked(true);
                    txtCMWRATotal.setText((cur.getString(cur.getColumnIndex("CMWRATotal")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("CMWRATotal"))));
                }

                if(secCMWRATotal.isShown())
                    txtCMWRATotal.setText((cur.getString(cur.getColumnIndex("CMWRATotal")).length() == 0 ? "0" : cur.getString(cur.getColumnIndex("CMWRATotal"))));

                cur.moveToNext();
            }
            cur.close();
        }
        catch(Exception  e)
        {
            Connection.MessageBox(HouseholdVisit.this, e.getMessage());
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


            dtpDate = (EditText)findViewById(R.id.dtpVisitDate);
            if (VariableID.equals("btnVisitDate"))
            {
                dtpDate = (EditText)findViewById(R.id.dtpVisitDate);
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


            //tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));

        }
    };



    // turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private String NewVisitNumber(String DCode, String UPCode, String UNCode,String Cluster, String VCode, String Bari, String HHNo)
    {
        String PID = C.ReturnSingleValue("Select (ifnull(max(cast(VisitNo as numeric(10))),0)+1)MaxId from HouseholdVisit where DCode='"+ DCode +"' and UPCode='"+ UPCode +"' and UNCode='"+ UNCode +"' and Cluster='"+ Cluster +"' and VCode='"+ VCode +"' and Bari='"+ Bari +"' and HHNo='"+ HHNo +"'");
        return PID;
    }



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

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(final Location loc) {
            Log.i("**********", "Location changed");
            if (isBetterLocation(loc, previousBestLocation)) {
                sp = new MySharedPreferences();
                sp.save(HouseholdVisit.this,"lat", String.valueOf(loc.getLatitude()));
                sp.save(HouseholdVisit.this,"lon", String.valueOf(loc.getLongitude()));

                //Toast.makeText(context, "Latitude" + loc.getLatitude() + "\nLongitude" + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());
                sendBroadcast(intent);
            }
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
        }
    }
}