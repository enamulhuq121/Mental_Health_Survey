package Controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import forms_datamodel.module_variable_DataModel;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

import androidx.cardview.widget.CardView;

public class ViewGenrator {
    Context context;

    public ViewGenrator(Context context) {
        this.context = context;
    }

    public View generate(module_variable_DataModel varlist, int position) {
        CardView crd = new CardView(context);
//        crd.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        crd.setTag(varlist.getvariable_name());
        //crd.setPadding(0,0,0,20);

        //
        LinearLayout.LayoutParams crdparam = new LinearLayout.LayoutParams(0, 0);
        crdparam.width = LinearLayout.LayoutParams.MATCH_PARENT;
        crdparam.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        crdparam.setMargins(0, 5, 0, 5);
        crd.setLayoutParams(crdparam);

        LinearLayout linearLayout_87 = new LinearLayout(context);
        linearLayout_87.setTag(varlist.getvariable_name());

        linearLayout_87.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layout_467 = new LinearLayout.LayoutParams(0, 0);
        layout_467.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_467.height = LinearLayout.LayoutParams.MATCH_PARENT;
        linearLayout_87.setLayoutParams(layout_467);

        LinearLayout linearLayout_356 = new LinearLayout(context);
        linearLayout_356.setTag(varlist.getvariable_name());

        linearLayout_356.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layout_319 = new LinearLayout.LayoutParams(0, 0);
        layout_319.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_319.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        linearLayout_356.setLayoutParams(layout_319);

        TextView textView_978 = new TextView(context);
        textView_978.setTag(varlist.getvariable_name());

        //Ques: description
        if(varlist.get_variable_label().length()==0)
            textView_978.setText(varlist.getvariable_desc());
        else
            textView_978.setText(varlist.get_variable_label() + " " + varlist.getvariable_desc());

        textView_978.setPadding(10, 0, 0, 0);

        if(varlist.getcontrol_type().equals("8")) {
            textView_978.setTextColor(Color.parseColor("#006699"));
            crd.setBackgroundColor(Color.parseColor("#D7D7D7"));
        }
        else {
            textView_978.setTextColor(Color.parseColor("#000000"));
        }

        LinearLayout.LayoutParams layout_115 = new LinearLayout.LayoutParams(0, 0);
        layout_115.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_115.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        textView_978.setLayoutParams(layout_115);
        linearLayout_356.addView(textView_978);

        linearLayout_87.addView(linearLayout_356);

        LinearLayout linearLayout_519 = new LinearLayout(context);
        linearLayout_519.setTag(varlist.getvariable_name());
        linearLayout_519.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layout_611 = new LinearLayout.LayoutParams(0, 0);
        layout_611.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_611.height = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_611.weight = 2;
        linearLayout_519.setLayoutParams(layout_611);

        LinearLayout linearLayout_223 = new LinearLayout(context);
        linearLayout_223.setTag(varlist.getvariable_name());
        LinearLayout.LayoutParams layout_120 = new LinearLayout.LayoutParams(0, 0);
        layout_120.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_120.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_120.weight = 1;
        linearLayout_223.setLayoutParams(layout_120);
        linearLayout_223.setPadding(20, 0, 0, 10);

        if (varlist.getcontrol_type().equals("1")) {
            linearLayout_223.addView(new MakeEditText(context).makeedittext(varlist, position));//------------------------------------
        } else if (varlist.getcontrol_type().equals("2")) {
            linearLayout_223.addView(new MakeRadioGroup(context).makeradiogroup(varlist, position));
        } else if (varlist.getcontrol_type().equals("3")) {
            linearLayout_223.addView(new MakeSpinner(context).makespinner(varlist, position));//------------------------------------
        } else if (varlist.getcontrol_type().equals("4")) {
            linearLayout_223.addView(new MakeCheckBox(context).makececkbox(varlist, position));//------------------------------------
        } else if (varlist.getcontrol_type().equals("5")) {
            linearLayout_223.addView(new MakeDate(context).makedate(varlist, position));//------------------------------------
        } else if (varlist.getcontrol_type().equals("6")) {
            linearLayout_223.addView(new MakeTimPicker(context).maketime(varlist, position));//------------------------------------
        } else if (varlist.getcontrol_type().equals("9")) {
//                relativeLayout_323.addView(mak());
            linearLayout_223.addView(new MakeAutoCompleteText(context).make_AutoCompleteText(varlist, position));//------------------------------------
        }else if (varlist.getcontrol_type().equals("12")) {
//                relativeLayout_323.addView(mak());
            linearLayout_223.addView(new MakeMulltiSelection(context).make_mullti_selection(varlist,position));//------------------------------------
        }else if (varlist.getcontrol_type().equals("10")) {
//                relativeLayout_323.addView(mak());
            linearLayout_223.addView(new MakeBarcode(context).makebarcode(varlist, position));//------------------------------------
        }


        linearLayout_519.addView(linearLayout_223);

        LinearLayout linearLayout_582 = new LinearLayout(context);
        linearLayout_582.setTag(varlist.getvariable_name());
        linearLayout_582.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layout_511 = new LinearLayout.LayoutParams(0, 0);
        layout_511.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_511.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_511.weight = 1;

        linearLayout_582.setLayoutParams(layout_511);

        LinearLayout linearLayout_921 = new LinearLayout(context);
        linearLayout_921.setTag(varlist.getvariable_name());
        LinearLayout.LayoutParams layout_721 = new LinearLayout.LayoutParams(0, 0);
        layout_721.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_721.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_721.weight = 1;
        linearLayout_921.setLayoutParams(layout_721);

        //************************* IMAGE

        LinearLayout linearLayout_75 = new LinearLayout(context);
        LinearLayout.LayoutParams layout_490 = new LinearLayout.LayoutParams(0, 0);
        layout_490.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_490.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout_490.weight = 1;
        linearLayout_75.setLayoutParams(layout_490);
        linearLayout_75.setOrientation(VERTICAL);
        linearLayout_75.setTag(varlist.getvariable_name());

//        if (!varlist.get_image().toString().equals("")) {
//            linearLayout_75.addView(makeimageview(varlist, position));
//            getMediaList(varlist, 1);
//            TextView textView = new TextView(context);
//            textView.setTextSize(15);
//            textView.setText("Images: " + 1 + "/" + mediaImageCount);
//            textView.setPadding(0, 0, 0, 10);
//            linearLayout_75.addView(textView);
//        }
//        if (!varlist.get_audio().toString().equals("")) {
//            linearLayout_75.addView(makeaudio(varlist, position));
//            getMediaList(varlist, 2);
//            TextView textView = new TextView(context);
//            textView.setTextSize(15);
//            textView.setText("Audio: " + 1 + "/" + mediaAudioCount);
//            textView.setPadding(0, 0, 0, 10);
//            linearLayout_75.addView(textView);
//        }
//        if (!varlist.get_video().toString().equals("")) {
//            linearLayout_75.addView(makevideo(varlist, position));
//            getMediaList(varlist, 3);
//            TextView textView = new TextView(context);
//            textView.setTextSize(15);
//            textView.setText("Video: " + 1 + "/" + mediaVideoCount);
//            textView.setPadding(0, 0, 0, 10);
//            linearLayout_75.addView(textView);
//        }
        linearLayout_582.addView(linearLayout_75);
        linearLayout_519.addView(linearLayout_582);

        linearLayout_87.addView(linearLayout_519);
        linearLayout_87.addView(new Makelabel(context).makelabel(varlist, position));

        if(varlist.getnote_visible().equals("1"))
            linearLayout_87.addView(new MakeNote(context).makeeditnote(varlist, position));

        if(varlist.get_flag_rule().length()>0)
        {
            linearLayout_87.addView(new MakeFlags(context).makeflag(varlist, position));
        }

        crd.addView(linearLayout_87);
        return crd;

    }
}
