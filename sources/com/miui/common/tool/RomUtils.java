package com.miui.common.tool;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.utils.PreferenceUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.util.DeviceUtils;
import miui.os.Build;
import miuix.core.util.SystemProperties;
import miuix.os.DeviceHelper;

/* JADX INFO: compiled from: RomUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000e\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010:\u001a\u00020\u001d2\b\u0010;\u001a\u0004\u0018\u00010<H\u0007J\u0012\u0010=\u001a\u00020\u001d2\b\u0010;\u001a\u0004\u0018\u00010<H\u0007J\u0010\u0010>\u001a\u00020\u001d2\u0006\u0010?\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u00020B2\u0006\u0010;\u001a\u00020<H\u0007J\b\u0010U\u001a\u00020\u001dH\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u0012\u0010\u0016\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\tR\u0012\u0010\u001a\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R!\u0010\u001c\u001a\u00020\u001d8FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b \u0010!\u0012\u0004\b\u001e\u0010\u0003\u001a\u0004\b\u001c\u0010\u001fR\u001a\u0010\"\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b#\u0010\u0003\u001a\u0004\b\"\u0010\u001fR\u001a\u0010$\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0003\u001a\u0004\b$\u0010\u001fR\u001a\u0010&\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b'\u0010\u0003\u001a\u0004\b&\u0010\u001fR\u001a\u0010(\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u0003\u001a\u0004\b(\u0010\u001fR\u001a\u0010*\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b+\u0010\u0003\u001a\u0004\b*\u0010\u001fR\u001a\u0010,\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b-\u0010\u0003\u001a\u0004\b,\u0010\u001fR\u001a\u0010.\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b/\u0010\u0003\u001a\u0004\b.\u0010\u001fR\u001a\u00100\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b1\u0010\u0003\u001a\u0004\b2\u0010\u0007R\u001a\u00103\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b4\u0010\u0003\u001a\u0004\b5\u0010\u0007R\u001a\u00106\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b7\u0010\u0003\u001a\u0004\b6\u0010\u001fR\u001a\u00108\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b9\u0010\u0003\u001a\u0004\b8\u0010\u001fR\u001a\u0010C\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bD\u0010\u0003\u001a\u0004\bC\u0010\u001fR\u001a\u0010E\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bF\u0010\u0003\u001a\u0004\bE\u0010\u001fR\u001b\u0010G\u001a\u00020H8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bK\u0010!\u001a\u0004\bI\u0010JR\u001a\u0010L\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bM\u0010\u0003\u001a\u0004\bL\u0010\u001fR\u001a\u0010N\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bO\u0010\u0003\u001a\u0004\bN\u0010\u001fR\u001a\u0010P\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bQ\u0010\u0003\u001a\u0004\bP\u0010\u001fR\u001b\u0010R\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bT\u0010!\u001a\u0004\bS\u0010\u0007¨\u0006V"}, d2 = {"Lcom/miui/common/tool/RomUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "LEVEL_SUPER_LITE", "", "getLEVEL_SUPER_LITE", "()Ljava/lang/String;", "setLEVEL_SUPER_LITE", "(Ljava/lang/String;)V", "LEVEL_MIDDLE", "getLEVEL_MIDDLE", "setLEVEL_MIDDLE", "LEVEL_NEW_LITE", "getLEVEL_NEW_LITE", "setLEVEL_NEW_LITE", "LEVEL_LITE", "getLEVEL_LITE", "setLEVEL_LITE", "LEVEL_MIUI", "getLEVEL_MIUI", "setLEVEL_MIUI", "DEVICE_FOLD", "DEVICE_PAD", "getDEVICE_PAD", "setDEVICE_PAD", "DEVICE_PHONE", "ONE_HANDED_MODE_ACTIVATED", "isPadMode", "", "isPadMode$annotations", "()Z", "isPadMode$delegate", "Lkotlin/Lazy;", "isPadDevice", "isPadDevice$annotations", "isFoldDevice", "isFoldDevice$annotations", "isFlipFoldDevice", "isFlipFoldDevice$annotations", "isJ18", "isJ18$annotations", "isO88", "isO88$annotations", "isRedmiDigitSeries", "isRedmiDigitSeries$annotations", "isInternationalBuild", "isInternationalBuild$annotations", "deviceLevel", "getDeviceLevel$annotations", "getDeviceLevel", "modelType", "getModelType$annotations", "getModelType", "isNewTablet", "isNewTablet$annotations", "isSupportAi", "isSupportAi$annotations", "isShowAiTextIcon", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "isSupportAiText", "getOneHandedModeActivated", "resolver", "Landroid/content/ContentResolver;", "getTranslationFraction", "", "isHyperOS2Rom", "isHyperOS2Rom$annotations", "isHyperOS3Rom", "isHyperOS3Rom$annotations", "hyperOsVersion", "", "getHyperOsVersion", "()I", "hyperOsVersion$delegate", "isSupportMiuiLayoutEngine", "isSupportMiuiLayoutEngine$annotations", "isMiuiLayoutEngineOpen", "isMiuiLayoutEngineOpen$annotations", "isRuRom", "isRuRom$annotations", "fingerprint", "getFingerprint", "fingerprint$delegate", "isOS31Support", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RomUtils {
    public static final String ONE_HANDED_MODE_ACTIVATED = "one_handed_mode_activated";
    public static final RomUtils INSTANCE = new RomUtils();
    private static String LEVEL_SUPER_LITE = "superLite";
    private static String LEVEL_MIDDLE = "middle";
    private static String LEVEL_NEW_LITE = "newLite";
    private static String LEVEL_LITE = "lite";
    private static String LEVEL_MIUI = "miui";
    public static String DEVICE_FOLD = "fold";
    private static String DEVICE_PAD = "pad";
    public static String DEVICE_PHONE = "phone";

    /* JADX INFO: renamed from: isPadMode$delegate, reason: from kotlin metadata */
    private static final Lazy isPadMode = LazyKt.lazy(new Function0() { // from class: com.miui.common.tool.RomUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(RomUtils.isPadMode_delegate$lambda$0());
        }
    });

    /* JADX INFO: renamed from: hyperOsVersion$delegate, reason: from kotlin metadata */
    private static final Lazy hyperOsVersion = LazyKt.lazy(new Function0() { // from class: com.miui.common.tool.RomUtils$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(miuix.core.util.RomUtils.getHyperOsVersion());
        }
    });

    /* JADX INFO: renamed from: fingerprint$delegate, reason: from kotlin metadata */
    private static final Lazy fingerprint = LazyKt.lazy(new Function0() { // from class: com.miui.common.tool.RomUtils$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return RomUtils.fingerprint_delegate$lambda$2();
        }
    });

    @JvmStatic
    public static /* synthetic */ void getDeviceLevel$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getModelType$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isFlipFoldDevice$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isFoldDevice$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isHyperOS2Rom$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isHyperOS3Rom$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isInternationalBuild$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isJ18$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiuiLayoutEngineOpen$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isNewTablet$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isO88$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isPadDevice$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isPadMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isRedmiDigitSeries$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isRuRom$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSupportAi$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSupportMiuiLayoutEngine$annotations() {
    }

    private RomUtils() {
    }

    public final String getLEVEL_SUPER_LITE() {
        return LEVEL_SUPER_LITE;
    }

    public final void setLEVEL_SUPER_LITE(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        LEVEL_SUPER_LITE = str;
    }

    public final String getLEVEL_MIDDLE() {
        return LEVEL_MIDDLE;
    }

    public final void setLEVEL_MIDDLE(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        LEVEL_MIDDLE = str;
    }

    public final String getLEVEL_NEW_LITE() {
        return LEVEL_NEW_LITE;
    }

    public final void setLEVEL_NEW_LITE(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        LEVEL_NEW_LITE = str;
    }

    public final String getLEVEL_LITE() {
        return LEVEL_LITE;
    }

    public final void setLEVEL_LITE(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        LEVEL_LITE = str;
    }

    public final String getLEVEL_MIUI() {
        return LEVEL_MIUI;
    }

    public final void setLEVEL_MIUI(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        LEVEL_MIUI = str;
    }

    public final String getDEVICE_PAD() {
        return DEVICE_PAD;
    }

    public final void setDEVICE_PAD(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        DEVICE_PAD = str;
    }

    public static final boolean isPadMode() {
        return ((Boolean) isPadMode.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isPadMode_delegate$lambda$0() {
        return isFoldDevice() || isPadDevice();
    }

    public static final boolean isPadDevice() {
        return Build.IS_TABLET;
    }

    public static final boolean isFoldDevice() {
        return miuix.os.Build.IS_FOLDABLE && !isFlipFoldDevice();
    }

    public static final boolean isFlipFoldDevice() {
        return DeviceHelper.detectType(BaseNoteApp.INSTANCE.getInstance()) == 4;
    }

    public static final boolean isJ18() {
        return Intrinsics.areEqual(android.os.Build.DEVICE, "cetus");
    }

    public static final boolean isO88() {
        return Intrinsics.areEqual(android.os.Build.DEVICE, "turner");
    }

    public static final boolean isRedmiDigitSeries() {
        return DeviceUtils.isRedmiDigitSeries();
    }

    public static final boolean isInternationalBuild() {
        return miuix.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static final String getDeviceLevel() {
        if (LiteUtils.isSuperLite()) {
            return LEVEL_SUPER_LITE;
        }
        if (LiteUtils.isMiddle()) {
            return LEVEL_MIDDLE;
        }
        if (LiteUtils.INSTANCE.isNewLite()) {
            return LEVEL_NEW_LITE;
        }
        if (LiteUtils.isLite()) {
            return LEVEL_LITE;
        }
        return LEVEL_MIUI;
    }

    public static final String getModelType() {
        if (isFoldDevice()) {
            return DEVICE_FOLD;
        }
        if (isPadDevice()) {
            return DEVICE_PAD;
        }
        return DEVICE_PHONE;
    }

    public static final boolean isNewTablet() {
        return !LiteUtils.isLiteOrMiddle();
    }

    public static final boolean isSupportAi() {
        if (isSupportAiText(BaseNoteApp.INSTANCE.getInstance())) {
            return false;
        }
        String str = android.os.Build.DEVICE;
        if (!isInternationalBuild()) {
            return false;
        }
        if (Intrinsics.areEqual("ruyi", str) || Intrinsics.areEqual("rothko", str) || Intrinsics.areEqual("degas", str) || Intrinsics.areEqual("XIG07", str) || Intrinsics.areEqual("aurora", str) || Intrinsics.areEqual("houji", str) || Intrinsics.areEqual("vermeer", str) || Intrinsics.areEqual("chenfeng", str) || Intrinsics.areEqual("peridot", str) || Intrinsics.areEqual("amethyst", str) || Intrinsics.areEqual("rodin", str)) {
            return true;
        }
        return isRuRom() && com.miui.notes.ai.utils.RomUtils.isSupportAiText(BaseNoteApp.INSTANCE.getInstance());
    }

    @JvmStatic
    public static final boolean isShowAiTextIcon(Context context) {
        if (context == null) {
            return false;
        }
        return isSupportAiText(context) || PreferenceUtils.isAcceptedMode(context);
    }

    @JvmStatic
    public static final boolean isSupportAiText(Context context) {
        if (isRuRom()) {
            return false;
        }
        return com.miui.notes.ai.utils.RomUtils.isSupportAiText(context);
    }

    @JvmStatic
    public static final boolean getOneHandedModeActivated(ContentResolver resolver) {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        return Settings.Secure.getInt(resolver, ONE_HANDED_MODE_ACTIVATED, 0) == 1;
    }

    @JvmStatic
    public static final float getTranslationFraction(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getFraction(R.fraction.config_one_handed_offset, 1, 1);
    }

    public static final boolean isHyperOS2Rom() {
        return miuix.core.util.RomUtils.getHyperOsVersion() >= 2;
    }

    public static final boolean isHyperOS3Rom() {
        return miuix.core.util.RomUtils.getHyperOsVersion() >= 3;
    }

    public final int getHyperOsVersion() {
        return ((Number) hyperOsVersion.getValue()).intValue();
    }

    public static final boolean isSupportMiuiLayoutEngine() {
        Object objInvoke = null;
        try {
            objInvoke = Class.forName("android.provider.DeviceConfig").getMethod("getProperty", String.class, String.class).invoke(null, "text", "com.android.text.flags.miui_layout_engine_second_version");
            Logger.INSTANCE.d("DeviceConfig", "getProperties: " + objInvoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Intrinsics.areEqual("true", objInvoke);
    }

    public static final boolean isMiuiLayoutEngineOpen() {
        return Intrinsics.areEqual("1", Settings.System.getString(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "miui_layout_engine"));
    }

    public static final boolean isRuRom() {
        return Intrinsics.areEqual(Language.LA_RU, SystemProperties.get("ro.miui.build.region"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String fingerprint_delegate$lambda$2() {
        return SystemProperties.get("ro.build.fingerprint");
    }

    public final String getFingerprint() {
        Object value = fingerprint.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (String) value;
    }

    @JvmStatic
    public static final boolean isOS31Support() {
        try {
            ApplicationInfo applicationInfo = BaseNoteApp.INSTANCE.getInstance().getPackageManager().getApplicationInfo("com.android.intentresolver", 128);
            boolean z = (applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.getBoolean("support_gallery_notes_access", false)) ? false : true;
            Logger.INSTANCE.d("isOS31Support", "isGalleryNotesAccess = " + z);
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            Logger.INSTANCE.e("isOS31Support", "com.android.intentresolver not installed");
            return false;
        } catch (Exception e) {
            Logger.INSTANCE.e("isOS31Support", "Unexpected error: " + e.getMessage());
            return false;
        }
    }
}
