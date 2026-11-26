package com.xiaomi.verificationsdk.internal;

import android.content.Context;
import android.opengl.GLES20;
import android.os.Build;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.io.File;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class EmulatorDetector {
    private static final String TAG = "EmulatorDetector";
    private static int rating = -1;

    public static boolean isEmulatorAbsoluly(Context context) {
        return mayOnEmulatorViaQEMU(context) || Build.PRODUCT.contains("sdk") || Build.PRODUCT.contains("sdk_x86") || Build.PRODUCT.contains("sdk_google") || Build.PRODUCT.contains("Andy") || Build.PRODUCT.contains("Droid4X") || Build.PRODUCT.contains("nox") || Build.PRODUCT.contains("vbox86p") || Build.PRODUCT.contains("aries") || Build.MANUFACTURER.equals("Genymotion") || Build.MANUFACTURER.contains("Andy") || Build.MANUFACTURER.contains("nox") || Build.MANUFACTURER.contains("TiantianVM") || Build.BRAND.contains("Andy") || Build.DEVICE.contains("Andy") || Build.DEVICE.contains("Droid4X") || Build.DEVICE.contains("nox") || Build.DEVICE.contains("vbox86p") || Build.DEVICE.contains("aries") || Build.MODEL.contains("Emulator") || Build.MODEL.equals("google_sdk") || Build.MODEL.contains("Droid4X") || Build.MODEL.contains("TiantianVM") || Build.MODEL.contains("Andy") || Build.MODEL.equals("Android SDK built for x86_64") || Build.MODEL.equals("Android SDK built for x86") || Build.HARDWARE.equals("vbox86") || Build.HARDWARE.contains("nox") || Build.HARDWARE.contains("ttVM_x86") || Build.FINGERPRINT.contains("generic/sdk/generic") || Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") || Build.FINGERPRINT.contains("Andy") || Build.FINGERPRINT.contains("ttVM_Hdragon") || Build.FINGERPRINT.contains("generic/google_sdk/generic") || Build.FINGERPRINT.contains("vbox86p") || Build.FINGERPRINT.contains("generic/vbox86p/vbox86p");
    }

    public static boolean isEmulator(Context context) {
        if (isEmulatorAbsoluly(context)) {
            return true;
        }
        if (rating < 0) {
            int i = (Build.PRODUCT.contains("sdk") || Build.PRODUCT.contains("Andy") || Build.PRODUCT.contains("ttVM_Hdragon") || Build.PRODUCT.contains("google_sdk") || Build.PRODUCT.contains("Droid4X") || Build.PRODUCT.contains("nox") || Build.PRODUCT.contains("sdk_x86") || Build.PRODUCT.contains("sdk_google") || Build.PRODUCT.contains("vbox86p") || Build.PRODUCT.contains("aries")) ? 1 : 0;
            if (Build.MANUFACTURER.equals("unknown") || Build.MANUFACTURER.equals("Genymotion") || Build.MANUFACTURER.contains("Andy") || Build.MANUFACTURER.contains("MIT") || Build.MANUFACTURER.contains("nox") || Build.MANUFACTURER.contains("TiantianVM")) {
                i++;
            }
            if (Build.BRAND.equals("generic") || Build.BRAND.equals("generic_x86") || Build.BRAND.equals("TTVM") || Build.BRAND.contains("Andy")) {
                i++;
            }
            if (Build.DEVICE.contains("generic") || Build.DEVICE.contains("generic_x86") || Build.DEVICE.contains("Andy") || Build.DEVICE.contains("ttVM_Hdragon") || Build.DEVICE.contains("Droid4X") || Build.DEVICE.contains("nox") || Build.DEVICE.contains("generic_x86_64") || Build.DEVICE.contains("vbox86p") || Build.DEVICE.contains("aries")) {
                i++;
            }
            if (Build.MODEL.equals("sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.equals("google_sdk") || Build.MODEL.contains("Droid4X") || Build.MODEL.contains("TiantianVM") || Build.MODEL.contains("Andy") || Build.MODEL.equals("Android SDK built for x86_64") || Build.MODEL.equals("Android SDK built for x86")) {
                i++;
            }
            if (Build.HARDWARE.equals("goldfish") || Build.HARDWARE.equals("vbox86") || Build.HARDWARE.contains("nox") || Build.HARDWARE.contains("ttVM_x86")) {
                i++;
            }
            if (Build.FINGERPRINT.contains("generic/sdk/generic") || Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") || Build.FINGERPRINT.contains("Andy") || Build.FINGERPRINT.contains("ttVM_Hdragon") || Build.FINGERPRINT.contains("generic_x86_64") || Build.FINGERPRINT.contains("generic/google_sdk/generic") || Build.FINGERPRINT.contains("vbox86p") || Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
                i++;
            }
            try {
                String strGlGetString = GLES20.glGetString(R2.dimen.material_helper_text_font_1_3_padding_horizontal);
                if (strGlGetString != null && (strGlGetString.contains("Bluestacks") || strGlGetString.contains("Translator"))) {
                    i += 10;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (new File(XMPassportSettings.getApplicationContext().getExternalFilesDir(null).toString() + File.separatorChar + "windows" + File.separatorChar + "BstSharedFolder").exists()) {
                    i += 10;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            rating = i;
        }
        return rating > 3;
    }

    private static final boolean mayOnEmulatorViaQEMU(Context context) {
        return "1".equals(getProp(context, "ro.kernel.qemu"));
    }

    public static String getDeviceListing() {
        return "Build.PRODUCT: " + Build.PRODUCT + "\nBuild.MANUFACTURER: " + Build.MANUFACTURER + "\nBuild.BRAND: " + Build.BRAND + "\nBuild.DEVICE: " + Build.DEVICE + "\nBuild.MODEL: " + Build.MODEL + "\nBuild.HARDWARE: " + Build.HARDWARE + "\nBuild.FINGERPRINT: " + Build.FINGERPRINT + "\nBuild.TAGS: " + Build.TAGS + "\nGL_RENDERER: " + GLES20.glGetString(R2.dimen.material_helper_text_font_1_3_padding_horizontal) + "\nGL_VENDOR: " + GLES20.glGetString(R2.dimen.material_helper_text_default_padding_top) + "\nGL_VERSION: " + GLES20.glGetString(R2.dimen.material_helper_text_font_1_3_padding_top) + "\nGL_EXTENSIONS: " + GLES20.glGetString(R2.dimen.material_input_text_to_prefix_suffix_padding) + StringUtils.LF;
    }

    private static final String getProp(Context context, String str) {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) clsLoadClass.getMethod(CallMethod.METHOD_GET, String.class).invoke(clsLoadClass, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void logcat() {
        AccountLogger.log(TAG, getDeviceListing());
    }
}
