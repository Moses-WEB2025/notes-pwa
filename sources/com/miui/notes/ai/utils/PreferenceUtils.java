package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class PreferenceUtils {
    private static final String AI_FLOATING_WINDOW_PREF_NAME = "ai_floating_window_pref";
    private static final String AI_MODEL_SWITCH = "ai_model_switch";
    private static final String CTA_SHA_PREF_NAME = "cta_sha_pref";
    private static final String INTERNAL_TEST_INVITE_PREF_NAME = "internal_test_invite_pref";
    private static final String MODEL_SWITCH = "model_switch";
    private static final String PREF_INTERNAL_TEST_INVITE_KEY = "internal_test_invite_key";
    private static final String PREF_INTERNAL_TEST_INVITE_KEY_FOR_SPECIAL = "internal_test_invite_key_for_special";
    private static final String PREF_KEY_ACCEPTED = "cta.pref.accepted";
    private static final String PREF_KEY_AI_FLOATING_WINDOW_SHOWING = "ai_floating_window_showing";
    private static final String PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA = "first_enter_note_for_cta";
    private static final String PREF_KEY_GLOBAL_ACCEPTED = "global.cta.pref.accepted";
    private static final String PREF_KEY_IS_FIRST_HANDLE = "key_is_first_handle_cta_dialog";

    public static void setGlobalPermissionAccepted(Context context, boolean z) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_GLOBAL_ACCEPTED, z).apply();
    }

    public static void setPermissionAccepted(Context context, boolean z) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_ACCEPTED, z).apply();
    }

    private static SharedPreferences getCTASharedPreferences(Context context) {
        return context.getSharedPreferences(CTA_SHA_PREF_NAME, 0);
    }

    public static void setFirstEnterNote(Context context) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, false).apply();
    }

    public static void setFirstEnterNote(Context context, boolean z) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, z).apply();
    }

    public static boolean isFirstEnterNote(Context context) {
        return getCTASharedPreferences(context).getBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, true);
    }

    public static boolean isCTAAccepted(Context context) {
        if (RomUtils.isInternationalBuild() && getCTASharedPreferences(context).getBoolean(PREF_KEY_GLOBAL_ACCEPTED, false)) {
            return true;
        }
        return !RomUtils.isInternationalBuild() && getCTASharedPreferences(context).getBoolean(PREF_KEY_ACCEPTED, false);
    }

    public static boolean isFirstHandle(Context context) {
        return getCTASharedPreferences(context).getBoolean(PREF_KEY_IS_FIRST_HANDLE, true) && !isCTAAccepted(context);
    }

    private static SharedPreferences getInternalTestSharedPreferences(Context context) {
        return context.getSharedPreferences(INTERNAL_TEST_INVITE_PREF_NAME, 0);
    }

    public static void setInternalTestInviteAccepted(Context context, boolean z) {
        getInternalTestSharedPreferences(context).edit().putBoolean(PREF_INTERNAL_TEST_INVITE_KEY, z).commit();
    }

    public static boolean isInternalTestInviteAccepted(Context context) {
        if (RomUtils.isInternationalBuild()) {
            return true;
        }
        return getInternalTestSharedPreferences(context).getBoolean(PREF_INTERNAL_TEST_INVITE_KEY, false);
    }

    public static void setInternalTestInviteAcceptedForSpecial(Context context, boolean z) {
        getInternalTestSharedPreferences(context).edit().putBoolean(PREF_INTERNAL_TEST_INVITE_KEY_FOR_SPECIAL, z).commit();
    }

    public static boolean isInternalTestInviteAcceptedForSpecial(Context context) {
        if (RomUtils.isInternationalBuild()) {
            return true;
        }
        return getInternalTestSharedPreferences(context).getBoolean(PREF_INTERNAL_TEST_INVITE_KEY_FOR_SPECIAL, false);
    }

    public static void setModelSwitch(Context context, boolean z) {
        getModelSwitchPreferences(context).edit().putBoolean(MODEL_SWITCH, z).apply();
    }

    public static void setAiModelSwitch(Context context, boolean z) {
        getAiModelSwitchPreferences(context).edit().putBoolean(AI_MODEL_SWITCH, z).apply();
    }

    public static boolean getAiModelSwitch(Context context) {
        return getAiModelSwitchPreferences(context).getBoolean(AI_MODEL_SWITCH, false);
    }

    public static SharedPreferences getAiModelSwitchPreferences(Context context) {
        return context.getSharedPreferences(AI_MODEL_SWITCH, 0);
    }

    private static SharedPreferences getModelSwitchPreferences(Context context) {
        return context.getSharedPreferences(MODEL_SWITCH, 0);
    }

    public static boolean isAcceptedMode(Context context) {
        return getModelSwitchPreferences(context).getBoolean(MODEL_SWITCH, false);
    }

    private static SharedPreferences getAiFloatingWindowPreferences(Context context) {
        return context.getSharedPreferences(AI_FLOATING_WINDOW_PREF_NAME, 0);
    }

    public static void setAiFloatingWindowShowing(Context context, boolean z) {
        if (context == null) {
            return;
        }
        try {
            getAiFloatingWindowPreferences(context).edit().putBoolean(PREF_KEY_AI_FLOATING_WINDOW_SHOWING, z).apply();
        } catch (Exception e) {
            Logger.INSTANCE.e("PreferenceUtils", "setAiFloatingWindowShowing failed,e:" + e);
        }
    }

    public static boolean isAiFloatingWindowShowing(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return getAiFloatingWindowPreferences(context).getBoolean(PREF_KEY_AI_FLOATING_WINDOW_SHOWING, false);
        } catch (Exception e) {
            Logger.INSTANCE.e("PreferenceUtils", "isAiFloatingWindowShowing failed,e:" + e);
            return false;
        }
    }
}
