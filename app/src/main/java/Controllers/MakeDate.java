package Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.icddrb.kalaazar_pkdl.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeDate {
    Context context;

    public MakeDate(Context context) {
        this.context = context;
    }
    public View makedate(final module_variable_DataModel varlist, final int position) {

        final EditText tvdpicker = new EditText(context);
        tvdpicker.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.calendersmall), null);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
        layout.width = 210;//LinearLayout.LayoutParams.WRAP_CONTENT;
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

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tvdpicker.setText(sdf.format(myCalendar.getTime()));

                MyInterface objInterface = (MyInterface) ((Activity) context);

                objInterface.savesentdata(tvdpicker.getText().toString(), varlist);
                if (objInterface.runflag(varlist, tvdpicker.getText().toString(), position).equals("1")) {
                    objInterface.savesentdata("", varlist);
                } else {
                    objInterface.derive(tvdpicker.getText().toString(), varlist);
                    objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
                }
                objInterface.counttaotanswered();
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
//                objInterface.savesentdata(tvdpicker.getText().toString(),varlist);
//
//                objInterface.counttaotanswered();
//                saveData(varlist, tvdpicker.getText().toString());
//                countansweredquestion();
            }

        };


        tvdpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return tvdpicker;
    }
}
