package org.icddrb.mental_health_survey;

import static Common.Common.BarcodeReaderHelper.CARD_TYPE;
import static Common.Common.BarcodeReaderHelper.HID_CARD;
import static Common.Common.BarcodeReaderHelper.Old_NID_CARD;
import static Common.Common.BarcodeReaderHelper.SMART_CARD;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import barcode.Barcode_Data;
import barcode.New_NID;
import barcode.Old_NID;

public class ScanRequestActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE_SCAN = 100;
    public static final int MY_REQUEST_CODE_SMART = 101;
    public static final int MY_REQUEST_CODE_OLD = 102;
    public static final int MY_REQUEST_CODE_HID = 103;
    public static final int MY_REQUEST_CODE_WRONG_CARD = 999;

    private static String xml_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_request);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.scan_popup_layout, null);

        final AlertDialog dialog = new AlertDialog.Builder(ScanRequestActivity.this)
                .setCancelable(true)
                .setView(dialogView)
                .create();
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                //your logic
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        Button btnSmart = dialogView.findViewById(R.id.btnSmart);
        Button btnOld = dialogView.findViewById(R.id.btnOld);
        Button btnHid = dialogView.findViewById(R.id.btnHid);

        btnSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent=new Intent(ScanRequestActivity.this,Scan_Barcode_Activity.class);
                startActivityForResult(intent,MY_REQUEST_CODE_SMART);*/
                Intent intent = new Intent(ScanRequestActivity.this, org.icddrb.mental_health_survey.ScanBarcode.class);
                intent.putExtra(CARD_TYPE, SMART_CARD);
                startActivityForResult(intent, MY_REQUEST_CODE_SMART);
            }
        });

        btnOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanRequestActivity.this, org.icddrb.mental_health_survey.ScanBarcode.class);
                intent.putExtra(CARD_TYPE, Old_NID_CARD);
                startActivityForResult(intent, MY_REQUEST_CODE_OLD);
            }
        });

        btnHid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanRequestActivity.this, org.icddrb.mental_health_survey.ScanBarcode.class);
                intent.putExtra(CARD_TYPE, HID_CARD);
                startActivityForResult(intent, MY_REQUEST_CODE_HID);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonStatusCodes.CANCELED & data == null) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

//        TextView txtData= findViewById(R.id.txtData);

        if (resultCode == CommonStatusCodes.SUCCESS & data != null) {
            Barcode barcode = data.getParcelableExtra("barcode");
            xml_data = barcode.displayValue;

            Barcode_Data barcode_data = new Barcode_Data(xml_data);

            Boolean aBoolean = xml_check(xml_data);

            if (requestCode == MY_REQUEST_CODE_SMART) {
                if (aBoolean.equals(false) & xml_data.length() > 11) {
                    New_NID new_nid = barcode_data.dataNewID();

                    if (new_nid == null) {
                        Intent intent = new Intent();
                        setResult(MY_REQUEST_CODE_WRONG_CARD, intent);
                        finish();
                    }

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("smartcard", new_nid);
                    intent.putExtras(bundle);
                    setResult(MY_REQUEST_CODE_SMART, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(MY_REQUEST_CODE_WRONG_CARD, intent);
                    finish();
                }
            } else if (requestCode == MY_REQUEST_CODE_OLD) {
                if (aBoolean.equals(true)) {
                    Old_NID old_nid = barcode_data.dataOldID();

                    if (old_nid == null) {
                        Intent intent = new Intent();
                        setResult(MY_REQUEST_CODE_WRONG_CARD, intent);
                        finish();
                    }

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("oldcard", old_nid);
                    intent.putExtras(bundle);
                    setResult(MY_REQUEST_CODE_OLD, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(MY_REQUEST_CODE_WRONG_CARD, intent);
                    finish();
                }
            } else if (requestCode == MY_REQUEST_CODE_HID) {
                if (xml_data.length() <= 11) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("hid", xml_data);
                    intent.putExtras(bundle);
                    setResult(MY_REQUEST_CODE_HID, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(MY_REQUEST_CODE_WRONG_CARD, intent);
                    finish();
                }
            }

        } else {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
    }

    private Boolean xml_check(String xml_data) {
        if (xml_data.contains("<pin>")) {
            return true;
        } else
            return false;
    }
}
