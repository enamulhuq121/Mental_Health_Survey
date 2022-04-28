package org.icddrb.standard_v3;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import Common.Common;
import barcodeSourceRavi8x.BarcodeReader;

public class ScanBarcode extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {


    private BarcodeReader barcodeReader;
    private TextView cardType;
    private String cardTypegetExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_scan_barcode_frag);

            barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
            barcodeReader.setAutoFlash(true);
            barcodeReader.setAutoFocus(true);
            barcodeReader.setSceneModeBarcode(true);

            cardType = (TextView) findViewById(R.id.CardType);
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                cardTypegetExtra = "Scan Barcode";
            } else {
                cardTypegetExtra = extras.getString(Common.BarcodeReaderHelper.CARD_TYPE);
                cardType.setText(cardTypegetExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        Intent intent = new Intent();
        intent.putExtra("barcode", barcode);
        setResult(CommonStatusCodes.SUCCESS, intent);
        finish();

    }

    @Override
    public void onScannedMultiple(List<Barcode> detections) {
        final List<Barcode> barcodes = detections;

        if (barcodes.size() > 0) {

            onScanned(barcodes.get(0));

        }

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(CommonStatusCodes.CANCELED, intent);
        finish();

    }

}
