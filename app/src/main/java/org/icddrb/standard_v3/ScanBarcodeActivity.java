package org.icddrb.standard_v3;

import static Common.Common.BarcodeReaderHelper.CARD_TYPE;
import static Common.Common.BarcodeReaderHelper.HID_CARD;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class ScanBarcodeActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE_HID = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_request);

        Intent intent = new Intent(ScanBarcodeActivity.this, ScanBarcode.class);
        intent.putExtra(CARD_TYPE, HID_CARD);
        startActivityForResult(intent, MY_REQUEST_CODE_HID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonStatusCodes.CANCELED & data == null) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        if (resultCode == CommonStatusCodes.SUCCESS & data != null) {
            Barcode barcode = data.getParcelableExtra("barcode");
            String barcode_data = barcode.displayValue;
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("hid", barcode_data);
            intent.putExtras(bundle);
            setResult(MY_REQUEST_CODE_HID, intent);
            finish();

        } else {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
    }
}
