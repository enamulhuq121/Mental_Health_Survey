package org.icddrb.kalaazar_pkdl;

import static android.view.View.generateViewId;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import forms_datamodel.module_variable_DataModel;

public class data_form_control {
    private Context Context;
    public data_form_control(Context con){
        Context = con;
    }
    public View make_AutoCompleteText(final module_variable_DataModel varlist, final int position) {
        final AutoCompleteTextView autoComplete_Text = new AutoCompleteTextView(Context);
        autoComplete_Text.setText(varlist.getvariable_data().toString());

        LinearLayout.LayoutParams layout_192 = new LinearLayout.LayoutParams(0, 0);
        layout_192.width = 150;
        layout_192.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        layout_192.setMargins(25, 0, 0, 0);
        autoComplete_Text.setId(generateViewId());
        autoComplete_Text.setLayoutParams(layout_192);

        autoComplete_Text.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //skip_rules(varlist, position, autoComplete_Text.getText().toString() + "");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                varlist.set_variable_data(autoComplete_Text.getText().toString());
                //saveData(varlist, autoComplete_Text.getText().toString());
                //countansweredquestion();
            }
        });
        return autoComplete_Text;
    }
}
