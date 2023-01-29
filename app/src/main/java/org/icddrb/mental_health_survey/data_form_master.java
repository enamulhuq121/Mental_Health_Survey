package org.icddrb.mental_health_survey;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import Common.Connection;
import Common.Global;
import Utility.MySharedPreferences;

public class data_form_master extends TabActivity implements AppCompatCallback {
    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME)
        { return false; }
        else { return true;  }
    }

    Bundle IDbundle;
    static String MODULEID = "";
    static String DATAID = "";
    static int SERIAL = 0;
    static int VISIT = 0;

    static String DEVICEID = "";
    static String ENTRYUSER = "";
    static String NAME = "";
    static String ID = "";
    static String TITLE = "";
    static String START_DATE = "";
    static String BARCODE = "";

    Global g;
    Connection C;
    MySharedPreferences sp;

    TextView title, name, age, sid, questionanswered;
    TabHost tabHost1;
    private AppCompatDelegate delegate;
    private LinearLayout llRoot;
    RelativeLayout header;

    Button btnComplete;

    private static String REQUIRED_MESSAGE="";

//    RadioGroup rdogrpComplete;
//    RadioButton rdoComplete1,rdoComplete2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.data_form_master);
        llRoot = findViewById(R.id.secTab);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar3);
        delegate.setSupportActionBar(toolbar);
        header = (RelativeLayout)findViewById(R.id.header) ;


        g = Global.getInstance();
        C = new Connection(this);
        DEVICEID  = sp.getValue(this, "deviceid");
        ENTRYUSER = sp.getValue(this, "userid");

        IDbundle = getIntent().getExtras();
        MODULEID = IDbundle.getString("moduleid");
        DATAID   = IDbundle.getString("dataid");
        SERIAL   = IDbundle.getString("serial")==null? 0 : Integer.valueOf(IDbundle.getString("serial"));
        VISIT    = IDbundle.getString("visit")==null? 0 : Integer.valueOf(IDbundle.getString("visit"));

        NAME = IDbundle.getString("name");
        ID = IDbundle.getString("id");
        START_DATE = IDbundle.getString("start_date");
        TITLE = IDbundle.getString("Title");
        if( ID == null )
            header.setVisibility(View.GONE);

        //-----------------------------------------------------------------------------DESIGN FOR LAYOUT
        title = (TextView) findViewById(R.id.lbltitle);
        name = (TextView) findViewById(R.id.txtName);
        age = (TextView) findViewById(R.id.txtAge);
        sid = (TextView) findViewById(R.id.txtStudyID);
        questionanswered = (TextView) findViewById(R.id.lblanswered);

        title.setText(TITLE);
        name.setText(ID);
        age.setText( START_DATE +"");
        sid.setText("Visit date : " );

        findViewById(R.id.cmdBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRequiredField();
                AlertDialog.Builder adb = new AlertDialog.Builder(data_form_master.this);
                adb.setTitle("Validation");
                if(REQUIRED_MESSAGE.equals(""))
                {
                    adb.setMessage("Do you want to close this form[Yes/No]?");
                }else {
                    adb.setMessage(Html.fromHtml("<font color='#ff0000'>"+REQUIRED_MESSAGE+"</font>"+"<br>Do you want to close this form[Yes/No]?"));
                }
                adb.setNegativeButton("No", null);
                adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("res", "");
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        Create_Screening_Table();
                    }
                });
                adb.show();
            }
        });

        btnComplete=findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRequiredField();

                if(REQUIRED_MESSAGE.equals(""))
                {
                    String ModuleInfo = C.ReturnSingleValue("select cast(m.module_id as text)||','||m.module_name||','||cast(m.nextModule as text)||','||ifnull(m1.module_name,'') next_module_name from module_list m\n" +
                            " left outer join module_list m1 on m.nextModule=m1.module_id\n" +
                            " where m.module_id='"+ MODULEID +"'");
                    String NextModule = Connection.split(ModuleInfo,',')[2];    //C.ReturnSingleValue("select (ifnull(module_name,'')||','||ifnull(nextModule,''))mod from module_list where module_id='"+ MODULEID +"'");
                    String NextModuleName = Connection.split(ModuleInfo,',')[3];//C.ReturnSingleValue("select module_name from module_list where module_id='"+ MODULEID +"'");

                    finish();
                    if(NextModule.length()>0) {
                        IDbundle.putString("moduleid", NextModule);
                        IDbundle.putString("dataid", DATAID);
                        IDbundle.putString("name", NAME);
                        IDbundle.putString("start_date", START_DATE);
                        IDbundle.putString("id", ID);
                        IDbundle.putString("moduleName", "test form");
                        IDbundle.putString("Title", NextModuleName);

                        Intent intent = new Intent(getApplicationContext(), data_form_master.class);
                        intent.putExtras(IDbundle);
                        startActivityForResult(intent, 1);
                    }
                }else {

                    AlertDialog.Builder adb = new AlertDialog.Builder(data_form_master.this);
                    adb.setTitle("Validation");
                    adb.setMessage(Html.fromHtml("<font color='#ff0000'>"+REQUIRED_MESSAGE+"</font>"));
                    adb.setNegativeButton("Ok", null);
                    adb.show();
                }

            }
        });

        PopulateTab();
    }



    public void checkRequiredField()
    {
        REQUIRED_MESSAGE = "";
        String sql="select d.variable_name,v.variable_label,v.variable_desc from module_data d \n" +
                "  left join module_variable v on d.module_id=v.module_id and d.variable_name=v.variable_name\n" +
                "  where v.control_type not in(7,8,13) and d.status='1' and d.variable_data='' and d.module_id='"+ MODULEID +"' and d.data_id='"+ DATAID +"' order by v.variable_seq";

        String msg="You havn't answered: <br>";
        Cursor cur=C.ReadData(sql);
        cur.moveToFirst();
        while (!cur.isAfterLast())
        {

            String VARIABLE_NAME  = cur.getString(cur.getColumnIndex("variable_name"));
            String VARIABLE_LABEL = cur.getString(cur.getColumnIndex("variable_label"));
            String VARIABLE_DESC  = cur.getString(cur.getColumnIndex("variable_desc"));
            if(cur.isFirst() | cur.isLast())
            {
                msg+="<br>"+ (VARIABLE_LABEL.length()>0 ? VARIABLE_LABEL + " " + VARIABLE_DESC : VARIABLE_DESC);
            }else
            {
                msg+="<br>"+ (VARIABLE_LABEL.length()>0 ? VARIABLE_LABEL + " " + VARIABLE_DESC : VARIABLE_DESC);
            }

            cur.moveToNext();
        }
        msg+="\n"+". <br><br>Please fill up these question. <br> ";
        REQUIRED_MESSAGE=msg;
        if(cur.getCount()==0)
        {
            REQUIRED_MESSAGE="";
        }

        cur.close();

    }

    public void Create_Screening_Table() {
        String SQL = "select * from data_module_6 where screen_id<>'' and date_start <>''";
        if(C.Existence(SQL)){
            C.SaveData("INSERT OR REPLACE INTO screening (screen_id, staff_id,date_start,time_start,location,data_id)\n" +
                    "                    SELECT screen_id ,staff_id,date_start, time_start,location,data_id\n" +
                    "                    FROM   data_module_6 where screen_id<> '' and staff_id<>'' and date_start<> '' and time_start <> '' and location <>''");
        }
    }
    public static void setTabColor(TabHost tabhost) {
        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.repeat_bg); // unselected
            //.setBackgroundColor(Color.WHITE);
        }
        tabhost.getTabWidget().setCurrentTab(0);
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                //.setBackgroundResource(R.drawable.repeat_bg); // selected
                .setBackgroundColor(Color.YELLOW);
    }

//    @Override
//    protected void onPause() {
//        Create_Screening_Table();
//    }//    @Override
//    protected void onPause() {
//        Create_Screening_Table();
//    }

    private void PopulateTab()
    {
        final HashMap<String, String> section_name=new HashMap<String, String>();
        tabHost1 = getTabHost();

        tabHost1.clearAllTabs();
        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(final String tabId) {
                setTabColor(tabHost1);
                try {
                    if(tabId.equals("ecd_status")){

                    }else {
                        org.icddrb.mental_health_survey.data_from_content activity = (org.icddrb.mental_health_survey.data_from_content) getLocalActivityManager().getActivity(tabId);
                        activity.prepareVariableListData(MODULEID, DATAID, SERIAL, VISIT, section_name.get(tabId));
                    }
                }catch (Exception ex){

                }
            }});


        String SQL="select distinct section from module_variable where control_type=8 and " +
                "module_id='"+ MODULEID +"' order by variable_seq";
        Connection C = new Connection(this);
        Cursor cur = C.ReadData(SQL);
        cur.moveToFirst();
        int rcount = 0;

        while(!cur.isAfterLast())
        {
            rcount +=1;
            section_name.put(cur.getString(cur.getColumnIndex("section")),cur.getString(cur.getColumnIndex("section")));

            TabHost.TabSpec birthObj = tabHost1.newTabSpec(cur.getString(cur.getColumnIndex("section")));
            birthObj.setIndicator(cur.getString(cur.getColumnIndex("section")));

            IDbundle.putString("moduleid", MODULEID);
            IDbundle.putString("section", cur.getString(cur.getColumnIndex("section")));
            IDbundle.putString("dataid", DATAID);
            IDbundle.putString("serial", String.valueOf(SERIAL));
            IDbundle.putString("visit", String.valueOf(VISIT));

            IDbundle.putString("Title", TITLE);
            IDbundle.putString("HeaderMsg", "");
            IDbundle.putString("FooterMsg", "");

            IDbundle.putString("original_age", START_DATE);

            //Headter and Footer Message based on the Section
            /*if(rcount==1){
                IDbundle.putString("HeaderMsg", "y");
                IDbundle.putString("FooterMsg", "n");
            }else if(rcount==cur.getCount()){
                IDbundle.putString("HeaderMsg", "n");
                IDbundle.putString("FooterMsg", "y");
            }*/

            Intent intbirthObj = new Intent(this, org.icddrb.mental_health_survey.data_from_content.class);
            intbirthObj.putExtras(IDbundle);

            birthObj.setContent(intbirthObj);
            tabHost1.addTab(birthObj);

            cur.moveToNext();
        }
        cur.close();

        if(tabHost1.getTabContentView().getChildCount()>0)
            setTabColor(tabHost1);
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    public static String getCurrentTimeStamp() {
        String result = "";

        Long tsLong = System.currentTimeMillis() / 1000;
        result = tsLong.toString();

        return result;
    }

}
