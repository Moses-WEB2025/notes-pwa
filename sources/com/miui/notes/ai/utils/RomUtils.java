package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.market.sdk.utils.Language;
import com.market.sdk.utils.Region;
import com.miui.ai.text.widget.R;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.aicr.llm.LLMManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import miuix.core.util.SystemProperties;
import miuix.os.Build;

/* JADX INFO: loaded from: classes4.dex */
public class RomUtils {
    public static String DEVICE_FOLD = "fold";
    public static String DEVICE_PAD = "pad";
    public static String DEVICE_PHONE = "phone";
    public static String LEVEL_LITE = "lite";
    public static String LEVEL_MIDDLE = "middle";
    public static String LEVEL_MIUI = "miui";
    public static String LEVEL_NEW_LITE = "newLite";
    public static String LEVEL_SUPER_LITE = "superLite";
    private static String TAG = "RomUtils";

    public static boolean isLLMAIWriting(Context context) {
        return false;
    }

    public static boolean isFoldDevice() {
        return Build.IS_FOLDABLE;
    }

    public static boolean isPadDevice() {
        return miui.os.Build.IS_TABLET;
    }

    public static boolean isPhoneDevice() {
        return (isFoldDevice() || isPadDevice()) ? false : true;
    }

    public static boolean isInternationalBuild() {
        return Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isSupportAiText(Context context) {
        String str = android.os.Build.DEVICE;
        if (context == null) {
            Logger.INSTANCE.d(TAG, "isSupportAiText context is null, return false");
            return false;
        }
        if (isInternationalBuild() && isP16UP()) {
            Logger.INSTANCE.d("RomUtils", "isSupportAiText: international P16UP does not support AI Text.");
            return false;
        }
        if (isAboveHyperOS2Rom()) {
            return !notSupportAiTextDeviceList(context).contains(str);
        }
        return false;
    }

    public static boolean isSupportLLMCotAI(Context context) {
        Logger.INSTANCE.d(TAG, "isSupportLLMCotAI :" + AIcrDownloadHelper.getCotConfig(context));
        return AIcrDownloadHelper.getCotConfig(context);
    }

    public static boolean isJustLLMAIWriting(Context context) {
        return (!isLLMAIWriting(context) || isSupportAiText(context) || PreferenceUtils.isAcceptedMode(context)) ? false : true;
    }

    public static String getModelType() {
        if (isFoldDevice()) {
            return DEVICE_FOLD;
        }
        if (isPadDevice()) {
            return DEVICE_PAD;
        }
        return DEVICE_PHONE;
    }

    public static String getDeviceLevel() {
        if (LiteUtils.isSuperLite()) {
            return LEVEL_SUPER_LITE;
        }
        if (LiteUtils.isMiddle()) {
            return LEVEL_MIDDLE;
        }
        if (LiteUtils.isNewLite()) {
            return LEVEL_NEW_LITE;
        }
        if (LiteUtils.isLite()) {
            return LEVEL_LITE;
        }
        return LEVEL_MIUI;
    }

    public static ArrayList<String> notSupportAiTextDeviceList(Context context) {
        String[] stringArray;
        Resources resources = context.getResources();
        if (isInternationalBuild()) {
            stringArray = resources.getStringArray(R.array.ai_widget_global_device_blacklist);
        } else {
            stringArray = resources.getStringArray(R.array.ai_widget_device_blacklist);
        }
        return new ArrayList<>(Arrays.asList(stringArray));
    }

    public static boolean isAboveHyperOS2Rom() {
        int hyperOsVersion = miuix.core.util.RomUtils.getHyperOsVersion();
        Logger.INSTANCE.d(TAG, "HyperOsVersion: " + hyperOsVersion);
        return hyperOsVersion >= 2;
    }

    public static boolean isAboveHyperOS3Rom() {
        int hyperOsVersion = miuix.core.util.RomUtils.getHyperOsVersion();
        Logger.INSTANCE.d(TAG, "HyperOsVersion: " + hyperOsVersion);
        return hyperOsVersion >= 3;
    }

    public static boolean isRussiaRegion() {
        String region = getRegion();
        return region.contains(Region.RU) || region.contains(Language.LA_RU);
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static boolean isAboveSDK34() {
        return android.os.Build.VERSION.SDK_INT >= 34;
    }

    public static boolean isAboveSDK35() {
        return android.os.Build.VERSION.SDK_INT >= 35;
    }

    public static boolean isSupportLocalAI(Context context) {
        if (isInternationalBuild()) {
            return false;
        }
        return LLMManager.isLLMSupport(context, 9003);
    }

    public static String getRegion() {
        String str = SystemProperties.get("ro.miui.region");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = SystemProperties.get("ro.product.locale.region");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        String str3 = SystemProperties.get("persist.sys.country");
        return TextUtils.isEmpty(str3) ? Locale.getDefault().getCountry() : str3;
    }

    public static boolean isMiniPad() {
        return android.os.Build.DEVICE.equals("turner") || android.os.Build.DEVICE.equals("yili");
    }

    public static boolean isM18() {
        return android.os.Build.DEVICE.equals("babylon");
    }

    public static boolean isP16UP() {
        return TextUtils.equals(android.os.Build.DEVICE, "flourite") && "POCO".equalsIgnoreCase(SystemProperties.get("ro.product.brand"));
    }
}
