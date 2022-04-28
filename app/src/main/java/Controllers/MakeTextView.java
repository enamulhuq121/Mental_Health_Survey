package Controllers;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import forms_datamodel.module_variable_DataModel;

public class MakeTextView {
    Context context;

    public MakeTextView(Context context) {
        this.context = context;
    }
    public View maketextview(module_variable_DataModel varlist, int position) {
        TextView textview = new TextView(context);
        textview.setText("NOTE : ");
        textview.setPadding(25, 0, 0, 0);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        textview.setLayoutParams(layout);

        return textview;
    }
}
