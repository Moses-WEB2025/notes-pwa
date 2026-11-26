package com.miui.notes.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.elements.MusicLyricParser;
import com.miui.notes.base.Constants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: DeviceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\t\u001a\u00020\u0005J\u0006\u0010\n\u001a\u00020\u0005J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000fJ\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u000e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000fJ\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ8\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2(\b\u0002\u0010\u001c\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001dj\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u001eJ*\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u001dj\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u001e2\u0006\u0010\u000e\u001a\u00020\u000fR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0014\u0010\u0018\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lcom/miui/notes/base/utils/DeviceUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "summaryInfo", "", "marketProps", "", "[Ljava/lang/String;", "getModel", "getOSVer", "isModelLikeMarketName", "", "getGlobalDeviceName", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getUid", "", "getScreenProp", "con", "getRAMUsage", "getSdcardUsage", "getVersionName", "getVersionCode", "lineFormat", "getLineFormat", "()Ljava/lang/String;", "getFormattedItemsInfo", CallMethod.ARG_EXTRA_STRING, "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "buildEnvInfo", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DeviceUtils {
    public static final DeviceUtils INSTANCE = new DeviceUtils();
    private static final String[] marketProps = {"ro.config.marketing_name", "ro.product.marketname", "ro.vivo.market.name", "ro.vendor.oplus.market.name"};
    private static String summaryInfo;

    private DeviceUtils() {
    }

    public final String getModel() {
        String strReplace$default = Build.MODEL;
        Intrinsics.checkNotNull(strReplace$default);
        if (strReplace$default.length() > 0) {
            Intrinsics.checkNotNull(strReplace$default);
            strReplace$default = StringsKt.replace$default(StringsKt.replace$default(strReplace$default, StringUtils.LF, "", false, 4, (Object) null), StringUtils.CR, "", false, 4, (Object) null);
        }
        Intrinsics.checkNotNull(strReplace$default);
        return strReplace$default;
    }

    public final String getOSVer() {
        String DISPLAY = Build.DISPLAY;
        Intrinsics.checkNotNullExpressionValue(DISPLAY, "DISPLAY");
        return DISPLAY;
    }

    private final boolean isModelLikeMarketName() {
        return StringsKt.equals(RemoteDeviceInfo.MANUFACTURER_XIAOMI, Build.BRAND, true);
    }

    public final String getGlobalDeviceName(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = Settings.Global.getString(context.getContentResolver(), "device_name");
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public final synchronized int getUid(Context context) {
        ApplicationInfo applicationInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return applicationInfo.uid;
    }

    public final String getScreenProp(Context con) {
        Intrinsics.checkNotNullParameter(con, "con");
        Object systemService = con.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        WindowManager windowManager = (WindowManager) systemService;
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        double dSqrt = Math.sqrt(Math.pow(point.x / displayMetrics.xdpi, 2.0d) + Math.pow(point.y / displayMetrics.ydpi, 2.0d));
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(dSqrt)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return "inches: " + str + ", widthPixels: " + displayMetrics.widthPixels + ", heightPixels: " + displayMetrics.heightPixels + ", density: " + displayMetrics.density;
    }

    private final String getRAMUsage(Context con) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        Object systemService = ContextCompat.getSystemService(con, ActivityManager.class);
        Intrinsics.checkNotNull(systemService);
        ((ActivityManager) systemService).getMemoryInfo(memoryInfo);
        long j = memoryInfo.totalMem;
        return Formatter.formatFileSize(con.getApplicationContext(), memoryInfo.availMem) + InternalZipConstants.ZIP_FILE_SEPARATOR + Formatter.formatFileSize(con.getApplicationContext(), j);
    }

    public final String getSdcardUsage(Context con) {
        Intrinsics.checkNotNullParameter(con, "con");
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return Formatter.formatFileSize(con.getApplicationContext(), statFs.getAvailableBytes()) + InternalZipConstants.ZIP_FILE_SEPARATOR + Formatter.formatFileSize(con.getApplicationContext(), statFs.getTotalBytes());
        } catch (IllegalArgumentException e) {
            return "external path is invalid " + e;
        }
    }

    private final String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Logger.INSTANCE.e(Constants.INSTANCE.getMODULE_TAG(), e.toString());
            return "";
        }
    }

    public final int getVersionCode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Logger.INSTANCE.e(Constants.INSTANCE.getMODULE_TAG(), e.toString());
            return -1;
        }
    }

    private final String getLineFormat() {
        return "%-18s : %s \r\n";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ String getFormattedItemsInfo$default(DeviceUtils deviceUtils, Context context, LinkedHashMap linkedHashMap, int i, Object obj) {
        if ((i & 2) != 0) {
            linkedHashMap = null;
        }
        return deviceUtils.getFormattedItemsInfo(context, linkedHashMap);
    }

    public final String getFormattedItemsInfo(Context context, LinkedHashMap<String, String> extra) {
        Set<Map.Entry<String, String>> setEntrySet;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(MusicLyricParser.CRLF);
            Set<Map.Entry<String, String>> setEntrySet2 = buildEnvInfo(context).entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet2, "<get-entries>(...)");
            Iterator<T> it = setEntrySet2.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String str = String.format(INSTANCE.getLineFormat(), Arrays.copyOf(new Object[]{entry.getKey(), entry.getValue()}, 2));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                sb.append(str);
            }
            if (extra != null && (setEntrySet = extra.entrySet()) != null) {
                Iterator<T> it2 = setEntrySet.iterator();
                while (it2.hasNext()) {
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    String str2 = String.format(INSTANCE.getLineFormat(), Arrays.copyOf(new Object[]{entry2.getKey(), entry2.getValue()}, 2));
                    Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                    sb.append(str2);
                }
            }
            summaryInfo = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.INSTANCE.e(Constants.INSTANCE.getMODULE_TAG(), e.toString());
        }
        String str3 = summaryInfo;
        return str3 == null ? "" : str3;
    }

    public final LinkedHashMap<String, String> buildEnvInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> linkedHashMap2 = linkedHashMap;
        linkedHashMap2.put("Product", "Manufacturer: " + Build.MANUFACTURER + "|Model: " + Build.MODEL + "|Device: " + Build.DEVICE);
        linkedHashMap2.put("Screen", getScreenProp(context));
        linkedHashMap2.put("HARDWARE", Build.HARDWARE);
        String string = Arrays.toString(Build.SUPPORTED_ABIS);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        linkedHashMap2.put("CPU ABI", string);
        linkedHashMap2.put("Memory Usage", getRAMUsage(context));
        linkedHashMap2.put("SDCard USage", getSdcardUsage(context));
        linkedHashMap2.put("BUILD DISPLAY", Build.DISPLAY);
        linkedHashMap2.put("Android OS", Build.VERSION.RELEASE);
        linkedHashMap2.put("Build SDK", String.valueOf(Build.VERSION.SDK_INT));
        linkedHashMap2.put("APP VersionName", getVersionName(context));
        linkedHashMap2.put("APP VersionCode", String.valueOf(getVersionCode(context)));
        linkedHashMap2.put("Up Time", DeviceUtilsKt.toDateString$default(System.currentTimeMillis() - SystemClock.elapsedRealtime(), null, 1, null));
        linkedHashMap2.put("Now Time", DeviceUtilsKt.toDateString$default(System.currentTimeMillis(), null, 1, null));
        return linkedHashMap;
    }
}
