package com.miui.handwriting.common.tools;

import android.content.Context;
import android.content.SharedPreferences;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.libhandwritingpage.utils.RomUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PreferenceUtils {
    private static final String FIRST_CTA_SHA_PREF_NAME = "first_cta_sha_pref";
    private static final String NETWORK_PERMISSION_SHA_PREF_NAME = "network_permission_sha_pref";
    public static final String PREF_FOR_MISHOW = "mishow";
    private static final String PREF_KEY_ACCEPTED = "network.permission.pref.accepted";
    public static final String PREF_KEY_AI_BRUSH_PERMISSION = "ai_brush_permission";
    private static final String PREF_KEY_DB_UPGRADE_SUCCESS = "creation_database_upgrade_success";
    private static final String PREF_KEY_FIRST_ACCEPTED = "cta.pref.first.accepted";
    private static final String PREF_KEY_WATER_MARKER = "creation_water_marker";

    private static SharedPreferences getNetworkPermissionSharedPreferences() {
        return HandWritingViewApplication.INSTANCE.getApplication().getSharedPreferences(NETWORK_PERMISSION_SHA_PREF_NAME, 4);
    }

    private static SharedPreferences getFirstCTASharedPreferences() {
        return HandWritingViewApplication.INSTANCE.getApplication().getSharedPreferences(FIRST_CTA_SHA_PREF_NAME, 4);
    }

    private static SharedPreferences getMishowPreferences() {
        return HandWritingViewApplication.INSTANCE.getApplication().getSharedPreferences(PREF_FOR_MISHOW, 4);
    }

    public static void setMishowId(Long l) {
        getMishowPreferences().edit().putLong(PREF_FOR_MISHOW, l.longValue()).apply();
    }

    public static long getMishowID() {
        return getMishowPreferences().getLong(PREF_FOR_MISHOW, -1L);
    }

    public static void resetMiShowID() {
        getMishowPreferences().edit().putLong(PREF_FOR_MISHOW, -1L).apply();
    }

    public static void setNetworkPermissionAccepted(boolean z) {
        getNetworkPermissionSharedPreferences().edit().putBoolean(PREF_KEY_ACCEPTED, z).apply();
    }

    public static boolean isNetworkPermissionAccepted() {
        return RomUtils.isInternationalBuild() || getNetworkPermissionSharedPreferences().getBoolean(PREF_KEY_ACCEPTED, false);
    }

    public static void setFirstCTAAccepted(boolean z) {
        getFirstCTASharedPreferences().edit().putBoolean(PREF_KEY_FIRST_ACCEPTED, z).apply();
    }

    public static boolean isFirstCTAAccepted() {
        return getFirstCTASharedPreferences().getBoolean(PREF_KEY_FIRST_ACCEPTED, true);
    }

    public static void setWaterMarker(long j) {
        setLongPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_WATER_MARKER, j);
    }

    public static long getWaterMarker() {
        return getLongPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_WATER_MARKER, 0L);
    }

    public static void setPrefKeyDbUpgrading(long j) {
        setLongPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_DB_UPGRADE_SUCCESS, j);
    }

    public static long getPrefKeyDbUpgrading() {
        return getLongPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_DB_UPGRADE_SUCCESS, 0L);
    }

    public static long getLongPreference(Context context, String str, long j) {
        return context.getSharedPreferences(Constants.CREATION_SHPREF, 4).getLong(str, j);
    }

    public static void setLongPreference(Context context, String str, long j) {
        context.getSharedPreferences(Constants.CREATION_SHPREF, 4).edit().putLong(str, j).apply();
    }

    public static void setAIBrushPermission(boolean z) {
        setBooleanPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_AI_BRUSH_PERMISSION, z);
    }

    public static boolean getAIBrushPermission() {
        return RomUtils.isN81A() || getBooleanPreference(HandWritingViewApplication.INSTANCE.getApplication(), PREF_KEY_AI_BRUSH_PERMISSION, false);
    }

    public static void setBooleanPreference(Context context, String str, boolean z) {
        context.getSharedPreferences(Constants.CREATION_SHPREF, 0).edit().putBoolean(str, z).apply();
    }

    public static boolean getBooleanPreference(Context context, String str, boolean z) {
        return context.getSharedPreferences(Constants.CREATION_SHPREF, 0).getBoolean(str, z);
    }
}
