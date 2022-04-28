package Common;

import java.util.ArrayList;
import java.util.List;

import Utility.MySharedPreferences;

/**
 * Created by hosen.sohrowardi on 3/20/2018.
 */

public class Common {

    private static MySharedPreferences sp;

    public static class BarcodeReaderHelper {
        public static final String CARD_TYPE = "card_type";
        public static final String SMART_CARD = "স্মার্ট কার্ড";
        public static final String Old_NID_CARD = "পুরাতন জাতীয় পরিচয় পত্র";
        public static final String HID_CARD = "হেলথ আইডি কার্ড";

    }
}
