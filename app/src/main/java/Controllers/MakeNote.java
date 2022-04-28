package Controllers;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;
import static android.widget.LinearLayout.HORIZONTAL;

public class MakeNote {
    Context context;

    public MakeNote(Context context) {
        this.context = context;
    }

    public View makeeditnote(final module_variable_DataModel varlist, final int position) {

        //Note
        LinearLayout layout_p = new LinearLayout(context);

        layout_p.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layout_param1 = new LinearLayout.LayoutParams(0, 0);
        layout_param1.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_param1.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        layout_p.addView(new MakeTextView(context).maketextview(varlist, position));

        final EditText edittext = new EditText(context);

        LinearLayout.LayoutParams layout_param2 = new LinearLayout.LayoutParams(0, 0);
        layout_param2.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_param2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_param2.setMargins(0, 0, 0, 5);
        edittext.setId(generateViewId());
        edittext.setLayoutParams(layout_param2);


        if (!varlist.get_note().equals("")) {
            edittext.setText(varlist.get_note());
        }

        edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
//                skip_rules(varlist, position, edittext.getText().toString() + "");
//                varlist.set_note(edittext.getText().toString());

//                saveData(varlist, varlist.getvariable_data());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                MyInterface objInterface =  (MyInterface) ((Activity) context);
                objInterface.changeview(varlist.getskip_rule(),s.toString(),varlist);
//                varlist.set_variable_data(edittext.getText().toString());
//                saveData(varlist, edittext.getText().toString());
//                countansweredquestion();
            }
        });
        layout_p.addView(edittext);

        layout_p.setVisibility(View.INVISIBLE); //11 jul 2018, Tanvir

        return layout_p;
    }
}
