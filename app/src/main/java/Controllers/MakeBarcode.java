package Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.icddrb.standard_v3.R;
import org.icddrb.standard_v3.Scan_Barcode_Activity;

import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

/**
 * Created by tasrul on 4/17/2019.
 */

public class MakeBarcode {
    public static final int MY_REQUEST_CODE_SCAN = 100 ;
    public static final int MY_REQUEST_CODE_WRONG_CARD = 999 ;
    Context context;
    Activity myActivity;

    public MakeBarcode(Context context) {
        this.context = context;
        this.myActivity = (Activity) context;
    }

    public View makebarcode(final module_variable_DataModel varlist, int position) {



        final EditText tvdpicker = new EditText(context);
        tvdpicker.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.barcodeimage), null);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = 300;//LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //layout.setMargins(92, 0, 0, 0);


        tvdpicker.setId(generateViewId());
        tvdpicker.setLayoutParams(layout);

        tvdpicker.setFocusable(false);

        if (varlist.getdata_type().equals("1"))
            tvdpicker.setInputType(InputType.TYPE_CLASS_TEXT);
        else if (varlist.getdata_type().equals("2"))
            tvdpicker.setInputType(InputType.TYPE_CLASS_NUMBER);
        else
            tvdpicker.setInputType(InputType.TYPE_CLASS_TEXT);

        if (varlist.getvariable_data().length() > 0) {
            tvdpicker.setText(varlist.getvariable_data());
        }



        tvdpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test",context.getClass().getSimpleName());
                Intent intent=new Intent(context,Scan_Barcode_Activity.class);
                intent.putExtra("variablebarcode", varlist);
                myActivity.startActivityForResult(intent,MY_REQUEST_CODE_SCAN);
            }
        });

        return tvdpicker;
    }


}
