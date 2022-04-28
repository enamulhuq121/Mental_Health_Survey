package DataSync;

import static android.util.Log.d;
import static android.util.Log.e;
import static android.util.Log.i;
import static android.util.Log.v;
import static android.util.Log.w;

/**
 * Created by TanvirHossain on 08/03/2015.
 */

public class Log {
    public static void logVerbose(String message) {
        v("RHIS", message);
    }

    public static void logInfo(String message) {
        i("RHIS", message);
    }

    public static void logDebug(String message) {
        d("RHIS", message);
    }

    public static void logWarn(String message) {
        w("RHIS", message);
    }

    public static void logError(String message) {
        e("RHIS", message);
    }
}