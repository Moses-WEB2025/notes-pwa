package com.miui.common.tool;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import miui.os.Build;

/* JADX INFO: compiled from: JumpVerificationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0006\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007j\n\u0012\u0006\u0012\u0004\u0018\u00010\b`\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/common/tool/JumpVerificationUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "IS_TEST_BUILD", "", "MIUI_SIGNATURES", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "getMIUI_SIGNATURES", "()Ljava/util/HashSet;", "setMIUI_SIGNATURES", "(Ljava/util/HashSet;)V", "TAG", "getPackageSHA256", "pi", "Landroid/content/pm/PackageInfo;", "verifyCalculatorJumpSource", "packageName", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class JumpVerificationUtils {
    public static final JumpVerificationUtils INSTANCE = new JumpVerificationUtils();
    private static final boolean IS_TEST_BUILD;
    private static HashSet<String> MIUI_SIGNATURES = null;
    private static final String TAG = "JumpVerificationUtils";

    private JumpVerificationUtils() {
    }

    static {
        boolean zAreEqual = Intrinsics.areEqual("test-keys", Build.TAGS);
        IS_TEST_BUILD = zAreEqual;
        HashSet<String> hashSet = new HashSet<>();
        MIUI_SIGNATURES = hashSet;
        hashSet.add("C9:00:9D:01:EB:F9:F5:D0:30:2B:C7:1B:2F:E9:AA:9A:47:A4:32:BB:A1:73:08:A3:11:1B:75:D7:B2:14:90:25");
        MIUI_SIGNATURES.add("D4:5F:07:6F:E2:3A:1A:5B:7F:48:6E:3F:F4:15:47:A2:02:3D:BF:E1:FE:73:35:3B:1E:48:EB:DF:ED:72:CC:6F");
        if (zAreEqual) {
            MIUI_SIGNATURES.add("C8:A2:E9:BC:CF:59:7C:2F:B6:DC:66:BE:E2:93:FC:13:F2:FC:47:EC:77:BC:6B:2B:0D:52:C1:1F:51:19:2A:B8");
            MIUI_SIGNATURES.add("A4:0D:A8:0A:59:D1:70:CA:A9:50:CF:15:C1:8C:45:4D:47:A3:9B:26:98:9D:8B:64:0E:CD:74:5B:A7:1B:F5:DC");
        }
    }

    public final HashSet<String> getMIUI_SIGNATURES() {
        return MIUI_SIGNATURES;
    }

    public final void setMIUI_SIGNATURES(HashSet<String> hashSet) {
        Intrinsics.checkNotNullParameter(hashSet, "<set-?>");
        MIUI_SIGNATURES = hashSet;
    }

    private final String getPackageSHA256(PackageInfo pi) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            Signature[] signatureArr = pi.signatures;
            Intrinsics.checkNotNull(signatureArr);
            messageDigest.update(signatureArr[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            byte[] bArrDigest = messageDigest.digest();
            int length = bArrDigest.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    sb.append(":");
                }
                String string = Integer.toString((bArrDigest[i] & 255) + 256, CharsKt.checkRadix(16));
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                String strSubstring = string.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                sb.append(strSubstring);
            }
            String string2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            String upperCase = string2.toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            return upperCase;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "get package info exception!,exception:" + e);
            return "";
        }
    }

    @JvmStatic
    public static final boolean verifyCalculatorJumpSource(String packageName) {
        PackageInfo packageInfo;
        String packageSHA256;
        Logger.INSTANCE.d(TAG, "verifyCalculatorJumpSource: packageName=" + packageName);
        String str = packageName;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            PackageManager packageManager = BaseNoteApp.INSTANCE.getInstance().getPackageManager();
            Intrinsics.checkNotNull(packageName);
            packageInfo = packageManager.getPackageInfo(packageName, 64);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "get package info exception!,exception:" + e);
            packageInfo = null;
        }
        if (packageInfo == null || (packageSHA256 = INSTANCE.getPackageSHA256(packageInfo)) == null) {
            packageSHA256 = "";
        }
        Logger.INSTANCE.d(TAG, "verifyCalculatorJumpSource: packageSHA256=" + packageSHA256);
        return TextUtils.equals(str, "com.miui.calculator") || TextUtils.equals(str, "com.miui.calculator.debug") || TextUtils.equals(str, "com.xiaomi.mihomemanager");
    }
}
