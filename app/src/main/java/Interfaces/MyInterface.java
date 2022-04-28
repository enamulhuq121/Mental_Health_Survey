package Interfaces;

import android.view.View;

import forms_datamodel.module_variable_DataModel;

public interface MyInterface {
    void changeview(String getskip_rule, String data, module_variable_DataModel varlist);
    void savesentdata(String data, module_variable_DataModel varlist);
    void showflag(String action, module_variable_DataModel varlist,int position);
    String runflag(module_variable_DataModel varlist,String data,int position);
    void addviews(View addview, module_variable_DataModel varlist);
    void derive(String data,module_variable_DataModel varlist);
    void counttaotanswered();
}
