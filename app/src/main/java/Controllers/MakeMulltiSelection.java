package Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.icddrb.kalaazar_pkdl.R;

import java.util.ArrayList;

import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;

/**
 * Created by tasrul on 4/4/2019.
 */

public class MakeMulltiSelection {
    Context context;

    String [] item_list;//=new ArrayList<>();
    boolean [] checkedItems;
    ArrayList<Integer> SelectedItems=new ArrayList<>();

    public MakeMulltiSelection(Context context) {
        this.context = context;
    }

    public View make_mullti_selection(final module_variable_DataModel varlist, final int position) {

        String option_list[] = varlist.getvariable_option().split("#");

        item_list=new String[option_list.length];

        for (int i = 0; i < option_list.length; i++) {
            item_list[i]=option_list[i].split("-")[1];
        }

//        item_list=getResources().getStringArray(R.array.item_list);
        checkedItems=new boolean[item_list.length];

        final EditText tvdpicker = new EditText(context);
        tvdpicker.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.touch), null);
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
            String data[]=varlist.getvariable_data().split(",");

            for(int i=0;i<data.length;i++)
            {
                for(int j=0;j<item_list.length;j++)
                {
                    if(item_list[j].contains(data[i]))
                    {
                        checkedItems[j]=true;
                    }
//                    else
//                    {
//                        checkedItems[j]=false;
//                    }

                }

            }
        }




        tvdpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Please select applicable items");
                builder.setMultiChoiceItems(item_list, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){

                            if(!SelectedItems.contains(position)){
                                SelectedItems.add(position);
                            }
                        }else {
                            if(SelectedItems.contains(position)){
                                SelectedItems.remove(new Integer(position));
                            }
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String items="";
                        for(int i=0;i<SelectedItems.size();i++)
                        {
                            if(i==0 | i==SelectedItems.size())
                            {
                                items+=item_list[SelectedItems.get(i)];
                            }else
                            {
                                items+=","+item_list[SelectedItems.get(i)];
                            }
                        }

                        tvdpicker.setText(items);

                        MyInterface objInterface = (MyInterface) ((Activity) context);

                        objInterface.savesentdata(tvdpicker.getText().toString(), varlist);
                        if (objInterface.runflag(varlist, tvdpicker.getText().toString(), position).equals("1")) {
                            objInterface.savesentdata("", varlist);
                        } else {
                            objInterface.derive(tvdpicker.getText().toString(), varlist);
                            objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
                        }
                        objInterface.counttaotanswered();
//                        MyInterface objInterface =  (MyInterface) ((Activity) context);
//                        objInterface.changeview(varlist.getskip_rule(),tvdpicker.getText().toString(),varlist);
//                        objInterface.savesentdata(items,varlist);
//
//                        objInterface.counttaotanswered();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0;i<checkedItems.length;i++)
                        {
                            checkedItems[i]=false;
                            SelectedItems.clear();
                            tvdpicker.setText("");

                        }
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();




            }
        });

        return tvdpicker;
    }
}
