package Controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class Makelabel {
    Context context;

    public Makelabel(Context context) {
        this.context = context;
    }
    public View makelabel(final module_variable_DataModel varlist, int position) {
        TextView tvradio = new TextView(context);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        layout.setMargins(25, 0, 0, 0);
        tvradio.setId(generateViewId());
        tvradio.setLayoutParams(layout);
        tvradio.setTag(varlist.getvariable_name() + "tvradio");

        tvradio.setVisibility(View.GONE);


        return tvradio;
    }
}
