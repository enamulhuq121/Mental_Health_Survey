package org.icddrb.mental_health_survey;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Common.Connection;
import Common.Global;
import Controllers.MakeBarcode;
import Controllers.ViewGenrator;
import Interfaces.MyInterface;
import Utility.MySharedPreferences;
import forms_datamodel.module_data_DataModel;
import forms_datamodel.module_variable_DataModel;

public class data_from_content extends AppCompatActivity implements MyInterface {
    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event) {
        if (iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        } else {
            return true;
        }
    }

    Bundle IDbundle;

    static String LANGUAGE = "";
    static String MODULEID = "";
    static String DATAID = "";
    static int SERIAL = 0;
    static int VISIT = 0;

    static String SECTIONID = "";
    static String TITLE = "";

    static String VARIABLENAME = "";
    static String DEVICEID = "";
    static String ENTRYUSER = "";
    static String NAME = "";
    static String ID = "";

    static String PRETERM_AGE = "";
    static String START_DATE = "";
    private List<module_variable_DataModel> variableList = new ArrayList<>();
    private List<module_variable_DataModel> variableList2 = new ArrayList<>();


    Global g;
    Connection C;
    MySharedPreferences sp;
    LinearLayout start;
    //
    TextView title, name, age, sid, questionanswered;
    private static MediaPlayer mp;
    TextView lblHeadingMsg, lblFooterMsg;
    static String COUNTRYCODE = "";
    static String FACICODE = "";

    private static int FRONT_FLAG;
    private static int BACK_FLAG;
    Button btnPrint;

    public void prepareVariableListData(String Module_Id, String Data_Id, int Serial, int Visit, String section) {

//        C.SaveData("DELETE from module_" + Module_Id + "");

//        C.SaveData("INSERT OR REPLACE INTO module_" + Module_Id + " (data_id)VALUES ('" + Data_Id + "')");
        C.SaveData("INSERT INTO data_module_" + Module_Id + "(data_id) \n" +
                "SELECT '" + DATAID + "' \n" +
                "WHERE NOT EXISTS(SELECT 1 FROM data_module_" + Module_Id + " WHERE data_id = '" + DATAID + "');");

        String sql = "select variable_name,ifnull(variable_data,'') as variable_data from module_data where module_id='" + Module_Id + "' and data_id = " + DATAID + "";


        String SQL = "";
        //Populate data for update
        SQL = "Insert into module_data(module_id, variable_name, data_id, variable_data, data_desc, status, entry_date, first_entry_time, final_entry_time, DeviceId, EntryUser, Upload, modifyDate)\n" +
                " select module_id, variable_name, '" + Data_Id + "' data_id, '' variable_data, '' data_desc, v.active status, null entry_date, null first_entry_time, null final_entry_time, " +
                " '" + DEVICEID + "' DeviceId, '" + ENTRYUSER + "' EntryUser, 2 Upload, '" + Global.DateTimeNowYMDHMS() + "' modifyDate\n" +
                " from module_variable v where control_type<>8 and control_type<>7 and" +
                " module_id='" + Module_Id + "'" +
                " and not exists(select * from module_data where module_id=v.module_id and variable_name=v.variable_name and " +
                " data_id='" + Data_Id + "')\n" +
                " order by variable_seq";

        String resp = C.SaveData(SQL);
        if (resp.length() > 0) {
            Connection.MessageBox(this, resp.toString());
            return;
        }

        //Populate data for form generate
        SQL = "Select c.content_folder, v.module_id,v.section,v. variable_name,v. variable_label,ifnull(l.variable_desc,v.variable_desc)variable_desc,v. variable_seq,v. control_type,\n" +
                "ifnull(l. variable_option,v.variable_option)variable_option ,v. variable_length,v. data_type,v. skip_rule,v. color,v.active,\n" +
                "ifnull(m1.file_name,'') variable_image,ifnull(m2.file_name,'') variable_audio,ifnull(m3.file_name,'') variable_video,\n" +
                "ifnull(d.variable_data,'')variable_data,ifnull(d.data_desc,'')data_desc, ifnull(d.data_id,'')data_id,ifnull(d.note,'')note, ifnull(d.status,'')status,\n" +
                " ifnull(age_start,'')age_start,ifnull(age_end,'')age_end,ifnull(age_start1,'')age_start1,ifnull(age_end1,'')age_end1,ifnull(age_start2,'')age_start2,ifnull(age_end2,'')age_end2," +
                " v.note_visible, v.flag_rule,v.flag_message, v.flag_status" +
                " from module_variable v\n" +
                " left outer join module_mediafile m1 on v.module_id=m1.module_id and v.variable_name=m1.variable_name and m1.file_type=1 and m1.file_seq=1\n" +
                " left outer join module_mediafile m2 on v.module_id=m2.module_id and v.variable_name=m2.variable_name and m2.file_type=2 and m2.file_seq=1\n" +
                " left outer join module_mediafile m3 on v.module_id=m3.module_id and v.variable_name=m3.variable_name and m3.file_type=3 and m3.file_seq=1\n" +
                " left outer join module_data d on v.module_id=d.module_id and v.variable_name=d.variable_name and d.data_id='" + Data_Id + "'" +
                " left outer join module_content c on v.module_id=c.module_id\n" +
                " left outer join module_variable_language l on v.module_id=l.module_id and v.variable_name=l.variable_name and l.language_id='" + LANGUAGE + "'\n" +
                " left outer join module_variable_agegrp ag on v.module_id=ag.module_id and v.variable_name=ag.variable_name\n" +

                " where v.control_type<>13 and v.module_id='" + Module_Id + "' and v.section='" + section + "' \n";
        SQL += " order by variable_seq asc";

        module_variable_DataModel d = new module_variable_DataModel();
        List<module_variable_DataModel> data = d.SelectAll_WithVariableData(this, SQL);

        variableList.clear();
        variableList.addAll(data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_form_content);

        g = Global.getInstance();
        C = new Connection(this);

        LANGUAGE = sp.getValue(this, "language");
        DEVICEID = sp.getValue(this, "deviceid");
        ENTRYUSER = sp.getValue(this, "userid");

        IDbundle = getIntent().getExtras();
        COUNTRYCODE = sp.getValue(this, "countrycode");
        FACICODE = sp.getValue(this, "facicode");
        MODULEID = IDbundle.getString("moduleid");
        DATAID = IDbundle.getString("dataid");

        SECTIONID = IDbundle.getString("section");
        TITLE = IDbundle.getString("Title");//preterm consider
        START_DATE = IDbundle.getString("start_date");

        lblHeadingMsg = (TextView) findViewById(R.id.lblHeadingMsg);
        lblFooterMsg = (TextView) findViewById(R.id.lblFooterMsg);
        lblHeadingMsg.setText("");
        lblFooterMsg.setText("");
        lblHeadingMsg.setVisibility(View.GONE);
        lblFooterMsg.setVisibility(View.GONE);


        lblHeadingMsg.setText(IDbundle.getString("HeaderMsg"));
        if (IDbundle.getString("HeaderMsg").length() > 0) lblHeadingMsg.setVisibility(View.VISIBLE);
        lblFooterMsg.setText(IDbundle.getString("FooterMsg"));
        if (IDbundle.getString("FooterMsg").length() > 0) lblFooterMsg.setVisibility(View.VISIBLE);

        mp = new MediaPlayer();
        start = (LinearLayout) findViewById(R.id.start);
        questionanswered = (TextView) findViewById(R.id.lblanswered);

        //-----------------------------------------------------------------------------------------------------------PREPARES VARIABLE MODEL

        prepareVariableListData(MODULEID, DATAID, SERIAL, VISIT, SECTIONID);

        for (int i = 0; i < variableList.size(); i++) {
            View cardchild = new ViewGenrator(data_from_content.this).generate(variableList.get(i), i);
            start.addView(cardchild);
            if (variableList.get(i).getstatus().equals("1")) {

                start.getChildAt(i).setVisibility(View.VISIBLE);
            } else if (variableList.get(i).getstatus().equals("2")) {

                //---------------------------------------------------------------------------------------------------GENRATES VIEW BASED ON PREDEFINED DESIGN
                View v = new ViewGenrator(data_from_content.this).generate(variableList.get(i), i);
                start.removeViewAt(i);
                start.addView(v, i);
                start.getChildAt(i).setVisibility(View.GONE);
            }
        }
        countansweredquestion();
    }

    public void showBarcodedata(module_variable_DataModel model)
    {
        for (int i = 0; i < start.getChildCount(); i++) {
            if (start.getChildAt(i).getTag().equals(model.getvariable_name())) {

                C.SaveData("UPDATE data_module_" + model.getmodule_id() + " set " + model.getvariable_name() + "='" + model.getvariable_data() + "' where data_id='" + model.get_data_id() + "'");
                View v = new ViewGenrator(data_from_content.this).generate(model, i);
                start.removeViewAt(i);
                start.addView(v, i);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== CommonStatusCodes.SUCCESS & data!=null & requestCode == MakeBarcode.MY_REQUEST_CODE_SCAN)
        {
            Intent intent = data;
            module_variable_DataModel module_data = (module_variable_DataModel) intent.getSerializableExtra("variablebarcode");
            savesentdata(module_data.getvariable_data(),module_data);
            showBarcodedata(module_data);
            counttaotanswered();
//            Barcode barcode=data.getParcelableExtra("barcode");
//            String xml_data = barcode.displayValue;
//            Toast.makeText(this, ""+module_data.getvariable_data(), Toast.LENGTH_LONG).show();

        }else
        {
//            Intent intent = new Intent();
//            setResult(Activity.RESULT_CANCELED,intent);
//            finish();
            if(resultCode== Activity.RESULT_CANCELED)
            {
                Connection.MessageBox(this,"Your scan has been canceled");
            }
        }
    }

    public void changeview(String skip_rule, String data, module_variable_DataModel varlist) {

        String SQL = "select variable_name,skip_rule from module_variable where skip_rule like '%" + varlist.getvariable_name() + "%'";
        Cursor cur = C.ReadData(SQL);
        try {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                String skip_string = "select case when ";

                String VARIABLE_NAME = cur.getString(cur.getColumnIndex("variable_name"));
                String SKIP_RULE = cur.getString(cur.getColumnIndex("skip_rule"));

                skip_string += SKIP_RULE;
                skip_string += " then '1' else 2 end as 'result' from data_module_" + MODULEID + " where data_id='" + DATAID + "'";

                String value = C.ReturnSingleValue(skip_string);

                letsskip(VARIABLE_NAME, value);

                cur.moveToNext();
            }

            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void savesentdata(String data, module_variable_DataModel varlist) {
        saveData(varlist, data);
        varlist.set_variable_data(data);
    }

    @Override
    public void showflag(String action, module_variable_DataModel varlist, int position) {
        int size = 0;
        for (int i = 0; i < myviewlist.size(); i++) {
            if (myviewlist.get(i).getTag().toString().contains(varlist.getvariable_name())) {
                if (action.equals("1"))
                    myviewlist.get(i).setVisibility(View.VISIBLE);
                else
                    myviewlist.get(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public String runflag(module_variable_DataModel varlist, String data,int position) {
        if(varlist.get_flag_rule().length()>0)
        {
            String SQL = "select variable_name,flag_rule from module_variable where module_id='"+varlist.getmodule_id()+"' and variable_name='"+varlist.getvariable_name()+"'";
            Cursor cur = C.ReadData(SQL);
            try {
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    String flag_string = "select case when ";

                    String VARIABLE_NAME = cur.getString(cur.getColumnIndex("variable_name"));
                    String flag_rule = cur.getString(cur.getColumnIndex("flag_rule"));

                    flag_string += flag_rule;
                    flag_string += " then '1' else 2 end as 'result' from data_module_" + varlist.getmodule_id() + "";

                    String value = C.ReturnSingleValue(flag_string);

                    if(value.equals("1"))
                    {
                        C.SaveData("Update module_variable set flag_status=1 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"' where data_id='" + DATAID + "'");
                        showflag("1",varlist,position);
                        return "1";

                    }else if(value.equals("2"))
                    {
                        C.SaveData("Update module_variable set flag_status=2 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"' where data_id='" + DATAID + "'");
                        showflag("2",varlist,position);
                        return "2";
                    }

                    cur.moveToNext();
                }

                cur.close();
            } catch (Exception e) {
                e.printStackTrace();
                Connection.MessageBox(getApplicationContext(),"Flag Error=="+e.getMessage());
            }

        }
        return "";
    }

    ArrayList<View> myviewlist = new ArrayList<>();

    @Override
    public void addviews(View addview, module_variable_DataModel varlist) {
        myviewlist.add(addview);
    }

    @Override
    public void derive(String data, module_variable_DataModel varlist) {
        String SQL = "select variable_name,derivation_rule from module_variable where derivation_rule like '%" + varlist.getvariable_name() + "%'";
        Cursor cur = C.ReadData(SQL);
        try {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                String derive_string = "select case ";

                String VARIABLE_NAME = cur.getString(cur.getColumnIndex("variable_name"));
                String DERIVATION_RULE = cur.getString(cur.getColumnIndex("derivation_rule"));

                derive_string += DERIVATION_RULE;
                derive_string += "  end as 'result' from data_module_view where data_id='" + DATAID + "'";

                String value = C.ReturnSingleValue(derive_string);
                if (value == null)
                    value="";

                letsderive(VARIABLE_NAME, value);

                cur.moveToNext();
            }

            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void counttaotanswered() {
        countansweredquestion();
    }

    public void letsderive(String variable_name, String value) {
        for (int i = 0; i < start.getChildCount(); i++) {
            if (start.getChildAt(i).getTag().equals(variable_name)) {
                variableList.get(i).set_variable_data(value);
                C.SaveData("UPDATE data_module_" + MODULEID + " set " + variable_name + "='" + value + "' where data_id='" + DATAID + "'");
                View v = new ViewGenrator(data_from_content.this).generate(variableList.get(i), i);
                start.removeViewAt(i);
                start.addView(v, i);
            }

        }
    }

    public void letsskip(String variable_name, String value) {

        for (int i = 0; i < start.getChildCount(); i++) {
            if (start.getChildAt(i).getTag().equals(variable_name)) {
                if (value.equals("1")) {
                    start.getChildAt(i).setVisibility(View.VISIBLE);
                    C.SaveData("update module_data set status ='1' where variable_name='" + variable_name + "' and module_id='" + MODULEID + "' and data_id='" + DATAID + "'");
                } else if (value.equals("2")) {
                    View v = new ViewGenrator(data_from_content.this).generate(variableList.get(i), i);
                    start.removeViewAt(i);
                    start.addView(v, i);
                    start.getChildAt(i).setVisibility(View.GONE);
                    savesentdata("",variableList.get(i));
                    C.SaveData("update module_data set variable_data='', status ='" + value + "' where variable_name='" + variable_name + "' and module_id='" + MODULEID + "' and data_id='" + DATAID + "'");

                }
            }
        }
    }

    public void saveData(module_variable_DataModel varlist, String data) {
        C.SaveData("UPDATE data_module_" + MODULEID + " set " + varlist.getvariable_name() + "='" + data + "' where data_id='" + DATAID + "'");

        module_data_DataModel module_data_dataModel = new module_data_DataModel();
        module_data_dataModel.setmodule_id(MODULEID);
        module_data_dataModel.setvariable_name(varlist.getvariable_name());
        module_data_dataModel.setdata_id(DATAID);
        module_data_dataModel.setvariable_data(data);
        module_data_dataModel.setdata_desc("");
        module_data_dataModel.setstatus(varlist.getstatus());
        module_data_dataModel.setentry_date(Global.DateNowYMD());
        module_data_dataModel.setfirst_entry_time(millsToDateFormat(new Date().getTime()));
        module_data_dataModel.setfinal_entry_time(millsToDateFormat(new Date().getTime()));
        module_data_dataModel.setDeviceID(DEVICEID);
        module_data_dataModel.setEntryUser(ENTRYUSER);
        module_data_dataModel.setmodifyDate(Global.DateTimeNowYMDHMS());
        module_data_dataModel.set_note(varlist.get_note());
        module_data_dataModel.SaveUpdateData(data_from_content.this);
    }

    //************************** Count Answered Question

    public void countansweredquestion() {

        Cursor cur = C.ReadData("\n" +
                "select count(*) answered," +
                "(select count(*) as total from module_data where module_id=" + MODULEID + " and data_id=" + DATAID + " and status !=2) " +
                "total  from module_data where module_id=" + MODULEID + " and data_id=" + DATAID + " and variable_data !='' and status !=2");
        try {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                String answered = cur.getString(cur.getColumnIndex("answered"));
                String total = cur.getString(cur.getColumnIndex("total"));
                if (answered == total) {
                    questionanswered.setTextColor(Color.parseColor("#006400"));
                } else {

                    questionanswered.setTextColor(Color.parseColor("#C50000"));
                }
                questionanswered.setText(answered + "/" + total);
                cur.moveToNext();
            }

            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String millsToDateFormat(long mills) {
        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(date);
        return dateFormatted; //note that it will give you the time in GMT+0
    }

    public static String getCurrentTimeStamp() {
        String result = "";

        Long tsLong = System.currentTimeMillis() / 1000;
        result = tsLong.toString();

        return result;
    }
}
