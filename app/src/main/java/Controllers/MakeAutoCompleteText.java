package Controllers;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import Common.Connection;
import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeAutoCompleteText {
    Context context;
    Connection C;

    public MakeAutoCompleteText(Context context) {
        this.context = context;
        this.C=new Connection(context);
    }

    public View make_AutoCompleteText(final module_variable_DataModel varlist, final int position) {
        final AutoCompleteTextView autoComplete_Text = new AutoCompleteTextView(context);
        autoComplete_Text.setText(varlist.getvariable_data());

//      style="@style/customEdit" android:layout_marginRight="10sp" android:textSize="20sp" android:maxLines="1" android:completionThreshold="1" android:scrollHorizontally="true" android:dropDownSelector="#D7D7D7"/>

        LinearLayout.LayoutParams layout_192 = new LinearLayout.LayoutParams(0, 0);
        layout_192.width = 300;
        layout_192.height = LinearLayout.LayoutParams.WRAP_CONTENT;

//        layout_192.setMargins(25, 0, 0, 0);
        autoComplete_Text.setId(generateViewId());
        autoComplete_Text.setLayoutParams(layout_192);
        autoComplete_Text.setInputType(InputType.TYPE_CLASS_TEXT);
        autoComplete_Text.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(androidx.mediarouter.R.drawable.ic_dialog_close_dark), null);
        autoComplete_Text.setThreshold(1);



        autoComplete_Text.setAdapter(C.getArrayAdapter("select variable_data from module_data where variable_name='"+varlist.getvariable_name()+"'"));

//        autoComplete_Text.setAdapter(C.getArrayAdapter("select variable_data from module_9"));

        autoComplete_Text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        autoComplete_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (autoComplete_Text.getRight() - autoComplete_Text.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ((EditText)v).setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        /*txtBariName=(AutoCompleteTextView) findViewById(R.id.txtBariName);
        txtBariName.setAdapter(C.getArrayAdapter("Select distinct BariName from "+ TableName +" order by BariName"));

        txtBariName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {

            }
        });
        txtBariName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (txtBariName.getRight() - txtBariName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ((EditText)v).setText("");
                        return true;
                    }
                }
                return false;
            }
        });*/
        autoComplete_Text.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
//                skip_rules(varlist, position, autoComplete_Text.getText().toString() + "");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                varlist.set_variable_data(autoComplete_Text.getText().toString());
                MyInterface objInterface = (MyInterface) ((Activity) context);

                objInterface.savesentdata(autoComplete_Text.getText().toString(), varlist);
                if (objInterface.runflag(varlist, autoComplete_Text.getText().toString(), position).equals("1")) {
                    objInterface.savesentdata("", varlist);
                } else {
                    objInterface.derive(autoComplete_Text.getText().toString(), varlist);
                    objInterface.changeview(varlist.getskip_rule(),autoComplete_Text.getText().toString(),varlist);
                }
                objInterface.counttaotanswered();
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.savesentdata(autoComplete_Text.getText().toString(),varlist);
//
//                objInterface.counttaotanswered();
//                varlist.set_variable_data(autoComplete_Text.getText().toString());
//                saveData(varlist, autoComplete_Text.getText().toString());
//                countansweredquestion();
            }
        });
        return autoComplete_Text;
    }
}
