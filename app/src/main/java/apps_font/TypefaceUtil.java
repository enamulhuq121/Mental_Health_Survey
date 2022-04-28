package apps_font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by mchd.dms on 24-Sep-17.
 */

public class TypefaceUtil {
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {

        final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
        try {
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
            Log.e(TypefaceUtil.class.getSimpleName(), "Else is Running");
        } catch (Exception e) {
            Log.e(TypefaceUtil.class.getSimpleName(), "Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
       /* if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("serif", customFontTypeface);
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
                Log.e(TypefaceUtil.class.getSimpleName(), "If is Running");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
                defaultFontTypefaceField.setAccessible(true);
                defaultFontTypefaceField.set(null, customFontTypeface);
                Log.e(TypefaceUtil.class.getSimpleName(), "Else is Running");
            } catch (Exception e) {
                Log.e(TypefaceUtil.class.getSimpleName(), "Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
            }
        }*/
    }
}
