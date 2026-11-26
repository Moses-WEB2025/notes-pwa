package com.miui.floatwindow.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;

/* JADX INFO: loaded from: classes2.dex */
public class DockUtils {
    private static final String FEATURE_DOCK = "is_miui_dock_support";
    private static final String FEATURE_TOOL_BOX_SUPPORT = "is_integration_gt_vtb_support";
    public static final String KEY_CONVERSATION_MODE = "in_conversation_mode";
    public static final String KEY_DOCK_MODE_STATUS = "dock_switch_status";
    public static final String KEY_GAMEBOOSTER = "gb_boosting";
    public static final String KEY_VTB_BOOSTER = "vtb_boosting";
    public static final String PREF_DEFAULT_STR = "default";
    public static final String PREF_URI = "content://com.miui.securitycenter.remoteprovider";
    public static final String REMOTE_METHOD_NAME_GET_FEATURE_STATUS = "getFeatureStatus";
    public static final String REMOTE_METHOD_NAME_IS_FEATURE_SUPPORT = "isFeatureSupport";
    public static final String REMOTE_PROVIDER_AUTHORITIES = "com.miui.securitycenter.remoteprovider";

    public static int getDockPanelPositionY() {
        return R2.attr.buttonRectStyleBackground;
    }

    private DockUtils() {
    }

    public static boolean getSecurityCenterSettings(Context context, String str, String str2, boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("default", z);
            Bundle bundleCall = context.getContentResolver().call(Uri.parse(PREF_URI), str, str2, bundle);
            return bundleCall == null ? z : bundleCall.getBoolean(str2, z);
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public static boolean isDockSupport(Context context) {
        return getSecurityCenterSettings(context, REMOTE_METHOD_NAME_IS_FEATURE_SUPPORT, FEATURE_DOCK, false);
    }

    public static boolean isOldToolboxSupportQuickNote(Context context) {
        return getSecurityCenterSettings(context, REMOTE_METHOD_NAME_IS_FEATURE_SUPPORT, FEATURE_TOOL_BOX_SUPPORT, false);
    }

    public static boolean getDockMode(Context context) {
        if (!isDockSupport(context)) {
            Logger.INSTANCE.d("DockDebug", "isDockSupport(context) false");
            if (isOldToolboxSupportQuickNote(context)) {
                return isToolboxOpen(context);
            }
            return false;
        }
        Logger.INSTANCE.d("DockDebug", "isDockSupport(context) true");
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("default", false);
            Bundle bundleCall = context.getContentResolver().call(Uri.parse(PREF_URI), REMOTE_METHOD_NAME_GET_FEATURE_STATUS, FEATURE_DOCK, bundle);
            if (bundleCall == null) {
                boolean z = getDockModeOld(context) == 1;
                Logger.INSTANCE.d("DockDebug", "getDockModeOld(context) " + z);
                return z;
            }
            boolean zIsToolboxOpen = bundleCall.getBoolean(FEATURE_DOCK, false);
            if (!zIsToolboxOpen && isOldToolboxSupportQuickNote(context)) {
                zIsToolboxOpen = isToolboxOpen(context);
            }
            Logger.INSTANCE.d("DockDebug", "getDockModeNew(context) " + zIsToolboxOpen);
            return zIsToolboxOpen;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getDockModeOld(Context context) {
        if (isDockSupport(context)) {
            return Settings.Global.getInt(context.getContentResolver(), KEY_DOCK_MODE_STATUS, 0);
        }
        return 0;
    }

    public static boolean isToolboxOpen(Context context) {
        int i = Settings.Secure.getInt(context.getContentResolver(), KEY_GAMEBOOSTER, 0);
        if (i == 0) {
            i = Settings.Secure.getInt(context.getContentResolver(), KEY_VTB_BOOSTER, 0);
        }
        if (i == 0) {
            i = Settings.Global.getInt(context.getContentResolver(), KEY_CONVERSATION_MODE, 0);
        }
        return i == 1;
    }
}
