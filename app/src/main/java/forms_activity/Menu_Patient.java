package forms_activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.icddrb.mental_health_survey.R;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;

public class Menu_Patient extends Activity {
    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME)
        { return false; }
        else { return true;  }
    }

    TextView txtWName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    static String WoName = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String PATIENTID = "";
    static String FACILITYID = "";
    static String PatCatPreg = "";
    static String PatCatDeliv = "";
    static String RecvService = "";

    Button cmdSecA,cmdSecB,cmdSecC,cmdSecD,cmdSecE,cmdSecF,cmdSecG,cmdSecH,cmdWomen;

    LinearLayout secMenuConsent;
    Connection C;
    Global g;

    @Override
    protected void onResume()
    {
        super.onResume();
        DataStatus();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_patient);
        C = new Connection(this);

        DEVICEID    = sp.getValue(this, "deviceid");
        ENTRYUSER   = sp.getValue(this, "userid");

        g = Global.getInstance();

        STARTTIME = g.CurrentTime24();
        DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
        ENTRYUSER = MySharedPreferences.getValue(this, "userid");

        IDbundle = getIntent().getExtras();
        PATIENTID = IDbundle.getString("PatientID");
        FACILITYID = IDbundle.getString("FacilityID");
        PatCatPreg = IDbundle.getString("PatCatPreg");
        PatCatDeliv = IDbundle.getString("PatCatDeliv");
        RecvService = IDbundle.getString("recv_service");


        IDbundle = getIntent().getExtras();
        WoName = IDbundle.getString("WoName");

        ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Menu_Patient.this);
                adb.setTitle("Close");
                adb.setMessage("Do you want to close this form[Yes/No]?");
                adb.setNegativeButton("No", null);
                adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }});
                adb.show();
            }});
        txtWName=(TextView) findViewById(R.id.txtWName);
        txtWName.setText(WoName);

        cmdSecA=findViewById(R.id.cmdSecA);
        cmdSecA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionA.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });

        cmdSecB=findViewById(R.id.cmdSecB);
        cmdSecB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Bundle IDbundle = new Bundle();
                    IDbundle.putString("PatientID", PATIENTID);
                    IDbundle.putString("FacilityID", FACILITYID);
                    IDbundle.putString("WoName", WoName);
                    Intent f1 = new Intent(getApplicationContext(), SectionB.class);
                    f1.putExtras(IDbundle);
                    startActivityForResult(f1,1);
            }
        });
        cmdSecC=findViewById(R.id.cmdSecC);
        cmdSecC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
             //   IDbundle.putString("PatCatPreg", PatCatPreg);
              //  IDbundle.putString("PatCatDeliv", PatCatDeliv);
                Intent f1 = new Intent(getApplicationContext(), SPECIFICVAR.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdSecD=findViewById(R.id.cmdSecD);
        cmdSecD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionD.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdSecE=findViewById(R.id.cmdSecE);
        cmdSecE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionE.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdSecF=findViewById(R.id.cmdSecF);
        cmdSecF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionF.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdSecG=findViewById(R.id.cmdSecG);
        cmdSecG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionG.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdSecH=findViewById(R.id.cmdSecH);
        cmdSecH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), SectionH.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });
        cmdWomen=findViewById(R.id.cmdWomen);
        cmdWomen.setEnabled(false);
        cmdWomen.setVisibility(View.GONE);

        /*cmdWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle IDbundle = new Bundle();
                IDbundle.putString("PatientID", PATIENTID);
                IDbundle.putString("FacilityID", FACILITYID);
                IDbundle.putString("WoName", WoName);
                Intent f1 = new Intent(getApplicationContext(), Women.class);
                f1.putExtras(IDbundle);
                startActivityForResult(f1,1);
            }
        });*/

    }

    private void DataStatus()
    {
        cmdSecC.setEnabled(false);
        cmdSecC.setBackgroundResource(R.drawable.button_style);
        if (PatCatPreg.equals("1") | PatCatDeliv.equals("1"))
        {
            cmdSecC.setEnabled(true);
            cmdSecC.setBackgroundResource(R.drawable.button_style_gray);
        }else {
            cmdSecC.setEnabled(false);
            cmdSecC.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionA where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")))
        {
            cmdSecB.setVisibility(View.VISIBLE);
            cmdSecB.setEnabled(true);
            cmdSecB.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecB.setEnabled(false);
            cmdSecB.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionB where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")))
        {
            cmdSecD.setVisibility(View.VISIBLE);
            cmdSecD.setEnabled(true);
            cmdSecD.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecD.setEnabled(false);
            cmdSecD.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionD where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")))
        {
            cmdSecE.setVisibility(View.VISIBLE);
            cmdSecE.setEnabled(true);
            cmdSecE.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecE.setEnabled(false);
            cmdSecE.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionE where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'"))  &  RecvService.equals("1"))
        {
            cmdSecF.setVisibility(View.VISIBLE);
            cmdSecF.setEnabled(true);
            cmdSecF.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecF.setEnabled(false);
            cmdSecF.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionF where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")) &  RecvService.equals("1"))
        {
            cmdSecG.setVisibility(View.VISIBLE);
            cmdSecG.setEnabled(true);
            cmdSecG.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecG.setEnabled(false);
            cmdSecG.setBackgroundResource(R.drawable.button_style);
        }

        if((C.Existence("Select * from SectionG where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'"))&  RecvService.equals("1"))
        {
            cmdSecH.setVisibility(View.VISIBLE);
            cmdSecH.setEnabled(true);
            cmdSecH.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdSecH.setEnabled(false);
            cmdSecH.setBackgroundResource(R.drawable.button_style);
        }
       /* if((C.Existence("Select * from SectionC where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")))
        {
            cmdWomen.setVisibility(View.VISIBLE);
            cmdWomen.setEnabled(true);
            cmdWomen.setBackgroundResource(R.drawable.button_style_gray);
        }else{
            cmdWomen.setEnabled(false);
            cmdWomen.setBackgroundResource(R.drawable.button_style);
        }*/

        if(C.Existence("Select * from SectionA  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecA.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionB  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecB.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionC  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecC.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionD  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecD.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionE  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecE.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionF  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecF.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionG  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecG.setBackgroundResource(R.drawable.button_style_green);
        }
        if(C.Existence("Select * from SectionH  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdSecH.setBackgroundResource(R.drawable.button_style_green);
        }
        /*if(C.Existence("Select * from Women  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdWomen.setBackgroundResource(R.drawable.button_style_green);
        }*/
    }
}
