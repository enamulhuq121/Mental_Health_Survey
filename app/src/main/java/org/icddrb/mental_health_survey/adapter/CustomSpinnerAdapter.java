package org.icddrb.mental_health_survey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.icddrb.mental_health_survey.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter {


    public CustomSpinnerAdapter(@NonNull Context context, ArrayList<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_custom_view,parent, false);
        }
        String name = (String) getItem(position);
        TextView tv = convertView.findViewById(R.id.weekofday);
        if (name != null) {
            tv.setText(name);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_custom_dropdown_item,parent, false);
        }
        String name = (String) getItem(position);
        TextView tv = convertView.findViewById(R.id.tv_dropdown);
        if (name != null) {
            tv.setText(name);
        }

        return convertView;
    }
}
