package Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import Common.Connection;
import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeRadioGroup {
    Context context;
    Connection C;

    public MakeRadioGroup(Context context) {
        this.context = context;
        C = new Connection(context);
    }
    public View makeradiogroup(final module_variable_DataModel varlist, final int position) {

        String option_list[] = varlist.getvariable_option().split("#");


        final RadioGroup radiogroup = new RadioGroup(context);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        radiogroup.setPadding(15, 0, 0, 0);
        radiogroup.setId(generateViewId());
        radiogroup.setLayoutParams(layout);

        RadioButton rb;

        for (int j = 0; j < option_list.length; j++) {

            final RadioButton[] radbutton = {new RadioButton(context)};
            radbutton[0].setText(option_list[j].split("-")[1]);
            radbutton[0].setGravity(Gravity.TOP);
            radbutton[0].setId(generateViewId());
            radbutton[0].setTag("rad" + j);

            radiogroup.addView(radbutton[0]);
            String selected[] = option_list[j].trim().toString().split("-");

            if (selected[0].toString().trim().equals(varlist.getvariable_data())) {

                RadioButton btn = (RadioButton) radiogroup.getChildAt(j);
                btn.setChecked(true);
            }
        }
//        new MyInterface().changeview();
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radiogroup.getCheckedRadioButtonId();
                RadioButton rdobtn = (RadioButton) ((Activity) context).findViewById(selectedId);
                int idx = radiogroup.indexOfChild(rdobtn);
                String sendata[] = varlist.getvariable_option().split("#");
                String data = null;


                for (int i = 0; i < sendata.length; i++) {
                    String arr[] = sendata[i].split("-");
                    if (arr[1].trim().equals(rdobtn.getText().toString().trim())) {
                        data = arr[0].trim();
                    }
                }
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//
//                if(objInterface.runflag(varlist,data,position).equals("1")){
//                    radiogroup.clearCheck();
//                    objInterface.savesentdata("",varlist);
//                }else{
//                    objInterface.derive(data,varlist);
//                    objInterface.changeview(varlist.getskip_rule(),data,varlist);
//                }
                MyInterface objInterface = (MyInterface) ((Activity) context);

                objInterface.savesentdata(data, varlist);
                if (objInterface.runflag(varlist, data, position).equals("1")) {
                    objInterface.savesentdata("", varlist);
                } else {
                    objInterface.derive(data, varlist);
                    objInterface.changeview(varlist.getskip_rule(),data,varlist);
                }
                objInterface.counttaotanswered();
////                saveData(varlist, data);
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.savesentdata(data,varlist);
//
////                objInterface.changeview(varlist.getskip_rule(),data,varlist);
//
//                //****************************** Sakib ****************************
//                if(varlist.get_flag_rule().length()>0)
//                {
//                    String SQL = "select variable_name,flag_rule from module_variable where module_id='"+varlist.getmodule_id()+"' and variable_name='"+varlist.getvariable_name()+"'";
//                    Cursor cur = C.ReadData(SQL);
//                    try {
//                        cur.moveToFirst();
//                        while (!cur.isAfterLast()) {
//                            String flag_string = "select case when ";
//
//                            String VARIABLE_NAME = cur.getString(cur.getColumnIndex("variable_name"));
//                            String flag_rule = cur.getString(cur.getColumnIndex("flag_rule"));
//
//                            flag_string += flag_rule;
//                            flag_string += " then '1' else 2 end as 'result' from module_" + varlist.getmodule_id() + "";
//
//                            String value = C.ReturnSingleValue(flag_string);
//
//                            if(value.equals("1"))
//                            {
////                            C.ReadData("Update modulte variable set flag_status=1 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"'");
//                                String s="Update module_variable set flag_status=1 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"'";
////                            objInterface.savesentdata("",varlist);
//                                radiogroup.clearCheck();
//
//                            }else if(value.equals("2"))
//                            {
//                                objInterface.changeview(varlist.getskip_rule(),data,varlist);
//                            }
//                            cur.moveToNext();
//                        }
//
//                        cur.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Connection.MessageBox(context,"Flag Error=="+e.getMessage());
//                    }
//
//                }
//                objInterface.changeview(varlist.getskip_rule(),data,varlist);

//                objInterface.counttaotanswered();
                //****************************** Sakib ****************************

                //-----------------------------FLAGRULE

                //IF 1 status = 1 SET '1' objInterface.savesentdata('',varlist);
                //rdogrp.clearcheck()
            }
        });
        return radiogroup;
    }
}
