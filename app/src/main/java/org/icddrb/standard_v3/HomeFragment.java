package org.icddrb.standard_v3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import Common.Connection;
import forms_activity.Indicator_List;

public class HomeFragment extends Fragment {
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    Context thiscontext;
    Bundle IBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        thiscontext=container.getContext();
        IBundle = new Bundle();

        GridView gv = rootView.findViewById(R.id.gridview);
        gv.setAdapter(new menuAdapter(thiscontext));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try
                {
                    if(position==0)
                    {
                        IBundle.putString("typeid", "%");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }
                    else if(position==1)
                    {
                        IBundle.putString("typeid", "1");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }
                    else if(position==2)
                    {
                        IBundle.putString("typeid", "2");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }
                    else if(position==3)
                    {
                        IBundle.putString("typeid", "3");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }
                    else if(position==4)
                    {
                        IBundle.putString("typeid", "4");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }
                    else if(position==5)
                    {
                        IBundle.putString("typeid", "5");
                        IBundle.putString("themeid", "%");

                        Intent I = new Intent(thiscontext, Indicator_List.class);
                        I.putExtras(IBundle);
                        startActivity(I);
                    }


                }
                catch(Exception ex)
                {
                    Connection.MessageBox(thiscontext, ex.getMessage());
                }
            }
        });

        return rootView; //inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static class menuAdapter extends BaseAdapter {
        private Context mContext;

        public menuAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return menu_list_image.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        //create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                MyView = li.inflate(R.layout.mainmenugriditem, null);

                try {
                    // Add The Image!!!
                    ImageView iv = (ImageView) MyView.findViewById(R.id.itemImage);
                    iv.setImageResource(menu_list_image[position]);

                    // Add The Text!!!
                    TextView tv = (TextView) MyView.findViewById(R.id.itemDesc);
                    tv.setTextSize(18);
                    tv.setText(menu_list[position]);

                }catch (Exception ex){
                    //String a = ex.getMessage();
                }
                MyView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 140));
            }
            return MyView;
        }

        private final String[] menu_list={
                "Data Entry",
                "Data Sync",
                "Exit"
        };

        //references to our images
        private final Integer[] menu_list_image = {
                R.drawable.new_entry,
                R.drawable.data_sync,
                R.drawable.exit,
        };
    }
}
