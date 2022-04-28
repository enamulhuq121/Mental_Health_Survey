package Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import Interfaces.MyInterface;
import forms_datamodel.module_variable_DataModel;

import static android.view.View.generateViewId;
import static android.widget.LinearLayout.VERTICAL;

public class MakeCheckBox {

    Context context;

    public MakeCheckBox(Context x) {
        this.context = x;
    }
    public View makececkbox(final module_variable_DataModel varlist, final int position) {

        final List<String> checklist = new ArrayList<String>();
        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layoutparam = new LinearLayout.LayoutParams(0, 0);
        layoutparam.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutparam.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.setLayoutParams(layoutparam);

        if (varlist.getvariable_option().contains("#")) {
            String splitme[] = varlist.getvariable_option().split("#");

            for (int i = 0; i < splitme.length; i++) {
                final String chekboxes[] = splitme[i].split("-");

                final CheckBox checkbox = new CheckBox(context);
                checkbox.setText(chekboxes[1].toString());
                if (varlist.getvariable_data().toString().contains(chekboxes[0] + "")) {
                    checkbox.setChecked(true);
                    checklist.add(chekboxes[0]);
                }

                LinearLayout.LayoutParams layout_child = new LinearLayout.LayoutParams(0, 0);
                layout_child.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layout_child.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                checkbox.setLayoutParams(layout_child);
                checkbox.setId(generateViewId());
                layout.addView(checkbox);

                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (checkbox.isChecked()) {
                                checklist.add(chekboxes[0]);
                            } else {
                                checklist.remove(chekboxes[0]);
                            }
                            String value = "";
                            for (int i = 0; i < checklist.size(); i++) {
                                value = value + checklist.get(i) + ",";
                            }
                            varlist.set_variable_data(value);

                            MyInterface objInterface =  (MyInterface) ((Activity) context);
                            objInterface.changeview(varlist.getskip_rule(),value,varlist);

                            objInterface.counttaotanswered();
//                                                                saveData(varlist, value);
                        }
                    }
                );
            }
        } else {
            final CheckBox checkbox = new CheckBox(context);
            checkbox.setText(varlist.getvariable_option());
            if (varlist.getvariable_data().toString().contains("true")) {
                checkbox.setChecked(true);
            }else
            {
                checkbox.setChecked(false);
            }
            LinearLayout.LayoutParams layout_child = new LinearLayout.LayoutParams(0, 0);
            layout_child.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layout_child.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            checkbox.setLayoutParams(layout_child);
            checkbox.setId(generateViewId());
            layout.addView(checkbox);

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checklist.clear();
                        if (checkbox.isChecked()) {
                            checklist.add("true");
                        } else {
                            checklist.add("false");
                        }
                        String value = "";
                        for (int i = 0; i < checklist.size(); i++) {
                            if(i==0 | i==checklist.size())
                            {
                                value = value +  checklist.get(i);
                            }else
                            {
                                value = value + "," + checklist.get(i);
                            }

                        }
                        varlist.set_variable_data(value);
                        MyInterface objInterface =  (MyInterface) ((Activity) context);
                        objInterface.savesentdata(value,varlist);
                        objInterface.changeview(varlist.getskip_rule(),value,varlist);


//                                                            saveData(varlist, value);
//                        Toast.makeText(context, value + "", Toast.LENGTH_SHORT).show();
                    }
                }
            );
        }


        return layout;
    }
}
