package com.market.sdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.appfunctions.schema.nearbysharing.v1.NearbyDevice;
import com.market.sdk.SystemProperties;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class Client {
    public static String ANDROID_ID = null;
    private static String DEFAULT_MIUI_BIG_VERSION_CODE = "-1";
    public static int DISPLAY_DENSITY = 0;
    public static int DISPLAY_HEIGHT = 0;
    public static String DISPLAY_RESOLUTION = null;
    public static int DISPLAY_WIDTH = 0;
    public static ArrayList<String> FEATURE = null;
    public static String GLES_VERSION = null;
    public static ArrayList<String> GL_EXTENSION = null;
    public static ArrayList<String> LIBRARY = null;
    public static String RELEASE = null;
    public static int SDK_VERSION = 0;
    public static String SYSTEM_VERSION = null;
    private static final String TAG = "MarketSdkClient";
    public static int TOUCH_SCREEN = 0;
    public static final int XIAOMI_SDK_VERSION_CODE = 11;
    private static boolean mIsInited = false;
    private static final Object mLock = new Object();
    private static String miuiBigVersionCode;

    public static void init(Context context) {
        if (mIsInited) {
            return;
        }
        acquireScreenAttr(context);
        acquireDeviceConfig(context);
        acquireFeature(context);
        acquireLibrary(context);
        acquireGLExtensions();
        acquireSystemInfo(context);
        acquireIdentity(context);
        mIsInited = true;
    }

    private static void acquireScreenAttr(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        DISPLAY_HEIGHT = displayMetrics.heightPixels;
        DISPLAY_WIDTH = displayMetrics.widthPixels;
        DISPLAY_RESOLUTION = DISPLAY_HEIGHT + "*" + DISPLAY_WIDTH;
        DISPLAY_DENSITY = displayMetrics.densityDpi;
    }

    private static void acquireDeviceConfig(Context context) {
        ConfigurationInfo deviceConfigurationInfo = ((ActivityManager) context.getSystemService("activity")).getDeviceConfigurationInfo();
        TOUCH_SCREEN = deviceConfigurationInfo.reqTouchScreen;
        GLES_VERSION = deviceConfigurationInfo.getGlEsVersion();
    }

    private static void acquireFeature(Context context) {
        FeatureInfo[] systemAvailableFeatures = context.getPackageManager().getSystemAvailableFeatures();
        synchronized (mLock) {
            FEATURE = new ArrayList<>();
            if (systemAvailableFeatures != null) {
                for (FeatureInfo featureInfo : systemAvailableFeatures) {
                    if (!TextUtils.isEmpty(featureInfo.name)) {
                        FEATURE.add(featureInfo.name);
                    }
                }
            }
            Collections.sort(FEATURE);
        }
    }

    private static void acquireLibrary(Context context) {
        String[] systemSharedLibraryNames = context.getPackageManager().getSystemSharedLibraryNames();
        synchronized (mLock) {
            LIBRARY = new ArrayList<>();
            if (systemSharedLibraryNames != null) {
                for (String str : systemSharedLibraryNames) {
                    if (!TextUtils.isEmpty(str)) {
                        LIBRARY.add(str);
                    }
                }
            }
            Collections.sort(LIBRARY);
        }
    }

    private static void acquireGLExtensions() {
        String gLExtensions = getGLExtensions();
        synchronized (mLock) {
            GL_EXTENSION = new ArrayList<>();
            if (!TextUtils.isEmpty(gLExtensions)) {
                for (String str : TextUtils.split(gLExtensions, StringUtils.SPACE)) {
                    if (!TextUtils.isEmpty(str)) {
                        GL_EXTENSION.add(str);
                    }
                }
            }
            Collections.sort(GL_EXTENSION);
        }
    }

    private static void acquireSystemInfo(Context context) {
        RELEASE = android.os.Build.VERSION.RELEASE;
        SYSTEM_VERSION = android.os.Build.VERSION.INCREMENTAL;
        SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    }

    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getRegion() {
        return SystemProperties.getString("ro.miui.region", "CN");
    }

    public static String getMiuiBigVersionCode() {
        return SystemProperties.getString("ro.miui.ui.version.code", DEFAULT_MIUI_BIG_VERSION_CODE);
    }

    public static boolean isAlphaBuild() {
        return miui.os.Build.IS_ALPHA_BUILD;
    }

    public static boolean isStableBuild() {
        return miui.os.Build.IS_STABLE_VERSION;
    }

    public static String getMiuiBigVersionName() {
        String string = SystemProperties.getString("ro.miui.ui.version.name", "");
        if (TextUtils.isEmpty(string) || isStableBuild()) {
            return string;
        }
        if (isAlphaBuild()) {
            return string + "-alpha";
        }
        return string + "-dev";
    }

    public static String getModel() {
        return android.os.Build.MODEL;
    }

    public static String getDevice() {
        return android.os.Build.DEVICE;
    }

    public static int getDeviceType() {
        return isTablet() ? 1 : 0;
    }

    public static boolean isTablet() {
        return SystemProperties.getString("ro.build.characteristics", "").contains(NearbyDevice.DEVICE_TYPE_TABLET);
    }

    public static String getCpuArch() {
        ArrayList arrayList = new ArrayList();
        if (isLaterThanLollipop()) {
            String string = SystemProperties.getString("ro.product.cpu.abilist", "");
            if (!TextUtils.isEmpty(string)) {
                arrayList = new ArrayList(Arrays.asList(TextUtils.split(string, ",")));
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(android.os.Build.CPU_ABI);
            arrayList.add(android.os.Build.CPU_ABI2);
        }
        return TextUtils.join(",", arrayList);
    }

    private static void acquireIdentity(Context context) {
        ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getImeiMd5() {
        try {
            String deviceId = ((TelephonyManager) AppGlobal.getContext().getSystemService("phone")).getDeviceId();
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = getUUid();
            }
            return Coder.encodeMD5(deviceId);
        } catch (Exception e) {
            android.util.Log.e(TAG, "getImeiMd5 error: " + e.getMessage());
            return "";
        }
    }

    public static String getUUid() {
        String string = PrefUtils.getString("uuid", "", new PrefUtils.PrefFile[0]);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String strValueOf = String.valueOf(UUID.randomUUID());
        PrefUtils.setString("uuid", strValueOf, new PrefUtils.PrefFile[0]);
        return strValueOf;
    }

    private static String getGLExtensions() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY || !egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
            return null;
        }
        int[] iArr = new int[1];
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!egl10.eglChooseConfig(eGLDisplayEglGetDisplay, new int[]{R2.id.ALT, 1, R2.drawable.v8_theme_white_share_bg, 8, R2.drawable.v8_theme_star_grid_fg, 8, R2.drawable.v8_theme_star_bg_top, 8, R2.drawable.v8_theme_star_bg_bottom, 8, R2.id.META}, eGLConfigArr, 1, iArr)) {
            return null;
        }
        EGLConfig eGLConfig = iArr[0] > 0 ? eGLConfigArr[0] : null;
        EGLContext eGLContextEglCreateContext = egl10.eglCreateContext(eGLDisplayEglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, null);
        EGLSurface eGLSurfaceEglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplayEglGetDisplay, eGLConfig, new int[]{R2.id.accessibility_custom_action_24, 480, R2.id.accessibility_custom_action_23, 800, R2.id.META});
        if (eGLSurfaceEglCreatePbufferSurface != null && eGLSurfaceEglCreatePbufferSurface != EGL10.EGL_NO_SURFACE) {
            egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface, eGLSurfaceEglCreatePbufferSurface, eGLContextEglCreateContext);
            if (!egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface, eGLSurfaceEglCreatePbufferSurface, eGLContextEglCreateContext)) {
                return null;
            }
            String strGlGetString = ((GL10) eGLContextEglCreateContext.getGL()).glGetString(R2.dimen.material_input_text_to_prefix_suffix_padding);
            egl10.eglDestroySurface(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface);
            egl10.eglDestroyContext(eGLDisplayEglGetDisplay, eGLContextEglCreateContext);
            egl10.eglTerminate(eGLDisplayEglGetDisplay);
            if (strGlGetString != null) {
                return strGlGetString.trim();
            }
        }
        return null;
    }

    public static boolean isLaterThanHoneycomb() {
        return SDK_VERSION >= 11;
    }

    public static int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static boolean isLaterThanLollipop() {
        return getSdkVersion() >= 21;
    }

    public static boolean isLaterThanN() {
        return SDK_VERSION >= 24;
    }

    public static boolean isMiui() {
        if (miuiBigVersionCode == null) {
            miuiBigVersionCode = getMiuiBigVersionCode();
        }
        return (TextUtils.isEmpty(miuiBigVersionCode) || DEFAULT_MIUI_BIG_VERSION_CODE.equals(miuiBigVersionCode)) ? false : true;
    }

    public static boolean isInternationalMiui() {
        return isMiui() && SystemProperties.getString("ro.product.mod_device", "").contains("_global");
    }
}
