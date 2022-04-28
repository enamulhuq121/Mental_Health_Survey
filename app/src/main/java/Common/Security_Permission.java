package Common;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import androidx.annotation.NonNull;
        import androidx.core.app.ActivityCompat;
        import android.util.Log;


/**
 * Created by tasrul on 07/12/2017.
 */

public class Security_Permission implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Context context;
    private Activity activity;

    public static final String TAG = "Security Permission";

    private static final int REQUEST_Code = 0;

    private static String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public  Security_Permission(Context context,Activity activity)
    {
        this.context=context;
        this.activity=activity;
        checkPermission();
    }


    private void checkPermission()
    {
        Log.e(TAG,"Checking Permission.");
        if ((ActivityCompat.checkSelfPermission(this.context, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) &
                (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) {

            Log.e(TAG,"Calling Requesting Permission!!!");
            requestPermission();

        } else {

            Log.e(TAG,"Your permission has already been granted.");

        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity,
                Manifest.permission.CAMERA)) {

            Log.e(TAG,"Requesting Permission to User.");

            ActivityCompat.requestPermissions(this.activity,PERMISSIONS_LIST,REQUEST_Code);

        } else {

            Log.e(TAG,"Requesting Permission Directly.");
            ActivityCompat.requestPermissions(this.activity,PERMISSIONS_LIST,REQUEST_Code);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_Code) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.e(TAG, "Received response for permission request.");

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.e(TAG, "Your Permission has now been granted.");

            } else {
                Log.e(TAG, "Your Permission was NOT granted.");
            }
        }

    }
}
