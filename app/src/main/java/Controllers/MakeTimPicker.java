package Controllers;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import org.icddrb.standard_v3.R;

import java.util.Calendar;

import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

public class MakeTimPicker {
    Context context;

    public MakeTimPicker(Context context) {
        this.context = context;
    }

    public View maketime(final module_variable_DataModel varlist, final int position) {
//        TimePicker dpicker = new TimePicker(context);
//        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, 0);
//        layout.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        dpicker.setId(generateViewId());
//        dpicker.setLayoutParams(layout);

        final EditText tvdpicker = new EditText(context);
        tvdpicker.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.timepicker_dg24), null);
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


        tvdpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                tvdpicker.setText(sHour + ":" + sMinute);
//                                MyInterface objInterface = (MyInterface) ((Activity) context);
//                                objInterface.changeview(varlist.getskip_rule(), tvdpicker.getText().toString(), varlist);
//                                objInterface.savesentdata(tvdpicker.getText().toString(),varlist);
//
//                                objInterface.counttaotanswered();
                                MyInterface objInterface = (MyInterface) ((Activity) context);
                                objInterface.savesentdata(tvdpicker.getText().toString(), varlist);

                                if (objInterface.runflag(varlist, tvdpicker.getText().toString(), position).equals("1")) {
                                    objInterface.savesentdata("", varlist);
                                } else {
                                    objInterface.derive(tvdpicker.getText().toString(), varlist);
                                    objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
                                }
                                objInterface.counttaotanswered();
                            }
                        }, hour, minutes, true);
                picker.show();

//                new TimePickerDialog(context, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        final TimePickerDialog.setOnClickListener date = new eReminderTime.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        eReminderTime.setText( selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//
//            }
//        });

//        final Calendar myCalendar = Calendar.getInstance();
//
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                String myFormat = "dd/MM/yyyy"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                tvdpicker.setText(sdf.format(myCalendar.getTime()));
//
//                MyInterface objInterface =  (MyInterface) ((Activity) context);
//                objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
////                saveData(varlist, tvdpicker.getText().toString());
////                countansweredquestion();
//            }
//
//        };
//
//
//        tvdpicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(context, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

        return tvdpicker;
    }
}
