package Controllers;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import Common.Connection;
import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeEditText {
    Context context;
    Connection C;

    public MakeEditText(Context context) {
        this.context = context;
        C = new Connection(context);
    }

    public View makeedittext(final module_variable_DataModel varlist, final int position) {

        final EditText editText = new EditText(context);

        editText.setText(varlist.getvariable_data().toString());
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);

        layout.setMargins(0, 0, 0, 0);
        editText.setId(generateViewId());
        editText.setLayoutParams(layout);
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Text
        //------------------------------------------------------------------------------------------
        if (varlist.getdata_type().equals("1")) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            layout.width = 300;
        }
        //Numeric
        //------------------------------------------------------------------------------------------
        else if (varlist.getdata_type().equals("2")) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.width = 150;
        }
        //Decimal
        //------------------------------------------------------------------------------------------
        else if (varlist.getdata_type().equals("3")) {
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.width = 150;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MyInterface objInterface = (MyInterface) ((Activity) context);

                objInterface.savesentdata(editText.getText().toString(), varlist);
                if (objInterface.runflag(varlist, editText.getText().toString(), position).equals("1")) {
                    objInterface.savesentdata("", varlist);
                } else {
                    objInterface.derive(editText.getText().toString(), varlist);
                    objInterface.changeview(varlist.getskip_rule(),editText.getText().toString(),varlist);
                }
                objInterface.counttaotanswered();

//                varlist.set_variable_data(editText.getText().toString());
//
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.savesentdata(editText.getText().toString(),varlist);
//                objInterface.derive(editText.getText().toString(),varlist);
//
//
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
//                                C.ReadData("Update module_variable set flag_status=1 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"'");
//                                objInterface.savesentdata("",varlist);
//                                objInterface.showflag("1",varlist,position);
//
//                            }else if(value.equals("2"))
//                            {
//                                C.ReadData("Update module_variable set flag_status=2 where module_id="+varlist.getmodule_id()+" and variable_name='"+varlist.getvariable_name()+"'");
//                                objInterface.changeview(varlist.getskip_rule(),editText.getText().toString(),varlist);
//                                objInterface.showflag("2",varlist,position);
//                            }
//
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
//
//                objInterface.counttaotanswered();
            }
        });
        return editText;
    }
}
