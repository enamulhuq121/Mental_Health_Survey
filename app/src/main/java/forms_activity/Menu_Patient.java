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
                Intent f1 = new Intent(getApplicationContext(), SectionC.class);
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
        cmdWomen.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    private void DataStatus()
    {
            /*cmdANC.setEnabled(true);
            cmdANC.setBackgroundResource(R.drawable.button_style_gray);

            if(C.Existence("Select PNo from PSS_ANC where PNo='"+ PNO +"' and PGN='"+ PGN +"'")){
                cmdMatHealth.setEnabled(true);
                cmdMatHealth.setBackgroundResource(R.drawable.button_style_gray);
            }
            if(C.Existence("Select PNo from PSS_P2MatHealthInf where PNo='"+ PNO +"' and PGN='"+ PGN +"'")){
                cmdPNC.setEnabled(true);
                cmdPNC.setBackgroundResource(R.drawable.button_style_gray);
            }
            if(C.Existence("Select PNo from PSS_PNC where PNo='"+ PNO +"' and PGN='"+ PGN +"'") & total_birth !=0){
                cmdBabyHealth.setEnabled(true);
                cmdBabyHealth.setBackgroundResource(R.drawable.button_style_gray);
            }*/
//        }


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
        if(C.Existence("Select * from Women  where PatientID='"+ PATIENTID +"' and FacilityID='"+ FACILITYID +"'")){
            cmdWomen.setBackgroundResource(R.drawable.button_style_green);
        }
    }
}
