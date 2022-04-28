package Utility;

import android.content.Context;
import android.content.SharedPreferences;

import Common.ProjectSetting;


/**
 * Created by SadiqMdAsif on 29/10/2016.
 */

public class MySharedPreferences {

    public static final String PREFS_NAME = ProjectSetting.ProjectName;
    public static final String PREFS_KEY = "AOP_PREFS_String";

    public MySharedPreferences() {
        super();
    }

    public void save(Context context, String key, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(key, text); //3

        editor.apply(); //4
    }

    public static String getValue(Context context, String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, "NULL");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    public void removeValue(Context context, String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.apply();
    }
}