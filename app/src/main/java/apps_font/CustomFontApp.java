package apps_font;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * Created by mchd.dms on 24-Sep-17.
 */
public class CustomFontApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(),
                "SERIF",
                "Roboto-Light.ttf");
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
