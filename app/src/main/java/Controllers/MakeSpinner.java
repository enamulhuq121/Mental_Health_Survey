package Controllers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import DataSync.Log;
import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeSpinner {
    Context context;
    Connection C;

    public MakeSpinner(Context context) {
        this.context = context;
        this.C=new Connection(context);
    }
    public View makespinner(final module_variable_DataModel varlist, int position) {

        String option_list[] = varlist.getvariable_option().split("#");

        final List<String> spinnerArray = new ArrayList<String>();

        spinnerArray.add("");
        for (int i = 0; i < option_list.length; i++) {
            spinnerArray.add(option_list[i]);
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, spinnerArray);


        Spinner spn = new Spinner(context);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        spn.setId(generateViewId());
        spn.setAdapter(adapter);
        spn.setLayoutParams(layout);


        if(varlist.getvariable_data().length()>0)
        {
            for (int i=0;i<spinnerArray.size();i++)
            {
                if(spinnerArray.get(i).split("-")[0].equals(varlist.getvariable_data()))
                {
                    spn.setSelection(i);
                }
            }

        }

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                saveData(varlist, position + "");
//                countansweredquestion();

                String data=spinnerArray.get(position).split("-")[0];

                MyInterface objInterface = (MyInterface) ((Activity) context);

                objInterface.savesentdata(data, varlist);
                if (objInterface.runflag(varlist, data, position).equals("1")) {
                    objInterface.savesentdata("", varlist);
                } else {
                    objInterface.derive(data, varlist);
                    objInterface.changeview(varlist.getskip_rule(),data,varlist);
                }
                objInterface.counttaotanswered();

//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.savesentdata(data.trim(),varlist);
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
//
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
//
//                objInterface.counttaotanswered();
                //****************************** Sakib ****************************

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        return spn;
    }
}
