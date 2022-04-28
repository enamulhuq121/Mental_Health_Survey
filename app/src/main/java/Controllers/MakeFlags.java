package Controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Interfaces.FlagInterface;
import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

/**
 * Created by tasrul on 4/1/2019.
 */

public class MakeFlags{
    Context context;
    List<View> view_lisst;

    public MakeFlags(Context context) {
        this.context = context;
    }
    TextView tvradio;
    public View makeflag(final module_variable_DataModel varlist, int position) {
        tvradio = new TextView(context);

        view_lisst = new ArrayList<View>();
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        layout.setMargins(25, 0, 0, 0);
        tvradio.setId(generateViewId());
        tvradio.setLayoutParams(layout);
        tvradio.setTag(varlist.getvariable_name()+"MakeFLAG");
        tvradio.setText(varlist.get_flag_message());
        tvradio.setTextColor(Color.RED);

        tvradio.setVisibility(View.GONE);

        MyInterface objInterface =  (MyInterface) ((Activity) context);
        objInterface.addviews(tvradio,varlist);

        view_lisst.add(tvradio);
        return tvradio;
    }

//    @Override
//    public void action(String action) {
//        if(action.equals("1")){
//            Toast.makeText(context, " "+tvradio.getTag(), Toast.LENGTH_SHORT).show();
//
////            tvradio.setVisibility(View.VISIBLE);
//        }else if(action.equals("2")){
//            Toast.makeText(context, "NOT Naice", Toast.LENGTH_SHORT).show();
//        }
//    }
}
