package com.miui.notes.cloudservice;

import android.content.Context;
import android.util.Pair;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.E2EEManager;
import com.xiaomi.e2ee.appkey.KeyEntry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import micloud.compat.independent.MiCloudLib;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NoteE2EEManager {
    public static final int APP_KEY_EXPIRED = 11001;
    private static final String TAG = "NoteE2EEManager";
    public static final String errorData = "E2EEException ErrorData";
    private static boolean sInit = false;

    public static synchronized void init(Context context) {
        if (!sInit) {
            MiCloudLib.init();
            E2EEManager.getInstance().init(context, "micloud");
            sInit = true;
        }
    }

    public static boolean isE2EEOpen() {
        try {
            return E2EEManager.getInstance().hasInitE2EE() && E2EEManager.getInstance().isE2EEOpen();
        } catch (IllegalStateException e) {
            Logger.INSTANCE.e(TAG, "E2EEManager not initialized " + e.getMessage());
            return false;
        }
    }

    public static void setAppKeyExpired() {
        E2EEManager.getInstance().setAppKeyExpired();
    }

    public static KeyEntry getKeyEntry() {
        try {
            return E2EEManager.getInstance().getKeyEntry();
        } catch (E2EEException e) {
            Logger.INSTANCE.e(TAG, "get key entry failed, errCode: " + e.errorCode + ", msg: " + e.getMessage());
            return null;
        } catch (InterruptedException e2) {
            Logger.INSTANCE.e(TAG, "get key entry failed, InterruptedException: " + e2);
            return null;
        }
    }

    public static String encryptData(KeyEntry keyEntry, String str) {
        if (keyEntry != null) {
            try {
                return E2EEManager.getInstance().encryptData(str, keyEntry);
            } catch (E2EEException e) {
                Logger.INSTANCE.e(TAG, "encryptData failed, errCode: " + e.errorCode + ", msg: " + e.getMessage());
            } catch (InterruptedException e2) {
                Logger.INSTANCE.e(TAG, "encryptData failed, interruptedException: " + e2);
            }
        }
        return str;
    }

    public static Pair<String, Boolean> decryptData(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            return new Pair<>(str, true);
        }
        try {
            return new Pair<>(E2EEManager.getInstance().decryptData(str, KeyEntry.fromServerJson(jSONObject)), true);
        } catch (E2EEException e) {
            Logger.INSTANCE.e(TAG, "decryptData failed, errCode: " + e.errorCode + ", msg: " + e.getMessage());
            return new Pair<>("", false);
        } catch (InterruptedException e2) {
            Logger.INSTANCE.e(TAG, "decryptData failed, interruptedException: " + e2);
            return new Pair<>("", false);
        } catch (JSONException e3) {
            Logger.INSTANCE.e(TAG, "decryptData failed, JSONException: " + e3);
            return new Pair<>("", false);
        }
    }

    public static void release() {
        init(NoteApp.getInstance());
        E2EEManager.getInstance().release();
    }

    public static String sha1(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA-1").digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String calculateSnippet(KeyEntry keyEntry, String str) {
        if (str == null) {
            return "";
        }
        return encryptData(keyEntry, StringUtils.substring(str, 0, 512));
    }
}
