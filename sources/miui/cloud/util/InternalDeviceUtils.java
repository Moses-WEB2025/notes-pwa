package miui.cloud.util;

import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes5.dex */
public class InternalDeviceUtils {
    public static final String MARKET_NAME = DeviceUtils.getAndroidSystemProperties("ro.product.marketname", null);

    public static boolean isRedmiNoteSeries() {
        return isRedmiNoteSeries(getPhoneModel());
    }

    private static String getPhoneModel() {
        String str = MARKET_NAME;
        return !TextUtils.isEmpty(str) ? str : Build.MODEL;
    }

    public static boolean isRedmiNoteSeries(String str) {
        return str.matches("(?i)^Redmi[\\s]*Note[\\s]*[0-9].*$");
    }

    public static boolean isRedmiDigitSeries() {
        return isRedmiDigitSeries(getPhoneModel());
    }

    public static boolean isRedmiDigitSeries(String str) {
        return str.matches("(?i)^Redmi[\\s]*[0-9]+[^X]*$");
    }
}
