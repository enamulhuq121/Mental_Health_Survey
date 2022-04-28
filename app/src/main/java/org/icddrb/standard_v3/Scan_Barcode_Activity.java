package org.icddrb.standard_v3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

import forms_datamodel.module_variable_DataModel;


public class Scan_Barcode_Activity extends AppCompatActivity {
    SurfaceView camera_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_scan__barcode);

        camera_preview = findViewById(R.id.camera_preview);
        createCameraSource();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(CommonStatusCodes.CANCELED,intent);
        finish();

    }

    private void createCameraSource() {
        //CALL FROM YOUR ACTIVITY**
        //RequestUserPermission requestUserPermission = new RequestUserPermission(this);
        //requestUserPermission.verifyStoragePermissions();

        Camera camera= Camera.open(0);    // For Back Camera
        Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = (Camera.Size) sizes.get(0);
        int height =result.height;
        int width = result.width;

        for (int i=0;i<sizes.size();i++)
        {
            result = (Camera.Size) sizes.get(i);
            if(width<result.width | height<result.height)
            {
                height =result.height;
                width = result.width;
            }

//            Log.e("Sakib png*******:",result.width+" * "+result.height);
        }

//        Log.e("Sakib hsadsd*******:",width+" * "+height);
        camera.release();

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(width , height).setRequestedFps(60).build();

        camera_preview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scan_Barcode_Activity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    cameraSource.start(camera_preview.getHolder());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();

                if(barcodes.size()>0) {

//                    Intent intent = new Intent();
                    Intent intent = getIntent();

                    // 2. get person object from intent
                    module_variable_DataModel module_data = (module_variable_DataModel) intent.getSerializableExtra("variablebarcode");

                    module_data.set_variable_data(barcodes.valueAt(0).displayValue);

//                    module_data.set_variable_data("1234");
                    intent.putExtra("variablebarcode", module_data);
//                    Bundle bundle = intent.getExtras();
//
//                    module_variable_DataModel module_data=
//                            (module_variable_DataModel) bundle.getSerializable("barcodevarlist");
////                    intent.putExtra("barcode",barcodes.valueAt(0));
//                    module_data.set_variable_data(barcodes.valueAt(0).toString());
//                    intent.putExtra("barcodevarlist", module_data);
                    setResult(CommonStatusCodes.SUCCESS,intent);
                    finish();
                }

            }
        });

    }
}
