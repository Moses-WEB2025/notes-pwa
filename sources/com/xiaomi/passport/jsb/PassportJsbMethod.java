package com.xiaomi.passport.jsb;

import android.net.Uri;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.Debuggable;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PassportJsbMethod {
    private static final Set<String> ALLOWED_DOMAINS;
    public static final String TAG = "PassportJsbMethod";

    private interface BundleDataFiller {
        void fill(Bundle bundle, String str, String str2);

        void fill(Bundle bundle, String str, String[] strArr);
    }

    public abstract String getName();

    public abstract PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException;

    public void release(PassportJsbWebView passportJsbWebView) {
    }

    static {
        HashSet hashSet = new HashSet();
        ALLOWED_DOMAINS = hashSet;
        hashSet.add(".mi.com");
        hashSet.add(".xiaomi.com");
        hashSet.add(".xiaomi.net");
        hashSet.add(".xiaomiev.com");
    }

    public static void addAllowedDomains(List<String> list) {
        ALLOWED_DOMAINS.addAll(list);
    }

    public String getParamsStringFieldOrThrow(JSONObject jSONObject, String str) throws PassportJsbMethodException {
        try {
            return jSONObject.getString(str);
        } catch (JSONException e) {
            throw new PassportJsbMethodException(104, "missing field: " + str, e);
        }
    }

    public static void checkUrlDomainPermission(PassportJsbWebView passportJsbWebView) throws PassportJsbMethodException {
        Uri uri = Uri.parse(passportJsbWebView.getUrl());
        String lowerCase = uri.getHost() != null ? uri.getHost().toLowerCase() : "";
        String lowerCase2 = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "";
        if (!"https".equalsIgnoreCase(lowerCase2)) {
            if (Debuggable.is(passportJsbWebView.getContext())) {
                if ("http".equalsIgnoreCase(lowerCase2)) {
                    AccountLogger.log(TAG, "allow http when debuggable");
                } else {
                    throw new PassportJsbMethodException(105, "not allow non-http(s) when debuggable");
                }
            } else {
                throw new PassportJsbMethodException(105, "only allow https when not debuggable");
            }
        }
        for (String str : ALLOWED_DOMAINS) {
            if (lowerCase.endsWith(str)) {
                AccountLogger.log(TAG, "allowed domain: " + str);
                return;
            }
        }
        throw new PassportJsbMethodException(105, "not xiaomi domain, forbid targetHost: " + lowerCase);
    }

    public static Bundle jsonToBundle(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        if (jSONObject != null) {
            fill(bundle, jSONObject.optJSONObject(TypedValues.Custom.S_INT), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.1
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putInt(str, Integer.parseInt(str2));
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    int length = strArr.length;
                    int[] iArr = new int[length];
                    for (int i = 0; i < length; i++) {
                        iArr[i] = Integer.parseInt(strArr[i]);
                    }
                    bundle2.putIntArray(str, iArr);
                }
            });
            fill(bundle, jSONObject.optJSONObject("long"), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.2
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putLong(str, Long.parseLong(str2));
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    int length = strArr.length;
                    long[] jArr = new long[length];
                    for (int i = 0; i < length; i++) {
                        jArr[i] = Long.parseLong(strArr[i]);
                    }
                    bundle2.putLongArray(str, jArr);
                }
            });
            fill(bundle, jSONObject.optJSONObject(TypedValues.Custom.S_FLOAT), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.3
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putFloat(str, Float.parseFloat(str2));
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    int length = strArr.length;
                    float[] fArr = new float[length];
                    for (int i = 0; i < length; i++) {
                        fArr[i] = Float.parseFloat(strArr[i]);
                    }
                    bundle2.putFloatArray(str, fArr);
                }
            });
            fill(bundle, jSONObject.optJSONObject("double"), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.4
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putDouble(str, Double.parseDouble(str2));
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    int length = strArr.length;
                    double[] dArr = new double[length];
                    for (int i = 0; i < length; i++) {
                        dArr[i] = Double.parseDouble(strArr[i]);
                    }
                    bundle2.putDoubleArray(str, dArr);
                }
            });
            fill(bundle, jSONObject.optJSONObject(TypedValues.Custom.S_BOOLEAN), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.5
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putBoolean(str, Boolean.parseBoolean(str2));
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    int length = strArr.length;
                    boolean[] zArr = new boolean[length];
                    for (int i = 0; i < length; i++) {
                        zArr[i] = Boolean.parseBoolean(strArr[i]);
                    }
                    bundle2.putBooleanArray(str, zArr);
                }
            });
            fill(bundle, jSONObject.optJSONObject(TypedValues.Custom.S_STRING), new BundleDataFiller() { // from class: com.xiaomi.passport.jsb.PassportJsbMethod.6
                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String str2) {
                    bundle2.putString(str, str2);
                }

                @Override // com.xiaomi.passport.jsb.PassportJsbMethod.BundleDataFiller
                public void fill(Bundle bundle2, String str, String[] strArr) {
                    bundle2.putStringArray(str, strArr);
                }
            });
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("bundle");
            if (jSONObjectOptJSONObject != null) {
                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(next);
                    if (jSONObjectOptJSONObject2 != null) {
                        bundle.putBundle(next, jsonToBundle(jSONObjectOptJSONObject2));
                    }
                }
            }
        }
        return bundle;
    }

    private static void fill(Bundle bundle, JSONObject jSONObject, BundleDataFiller bundleDataFiller) {
        if (jSONObject == null) {
            return;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (objOpt instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) objOpt;
                int length = jSONArray.length();
                String[] strArr = new String[length];
                for (int i = 0; i < length; i++) {
                    strArr[i] = jSONArray.opt(i).toString();
                }
                bundleDataFiller.fill(bundle, next, strArr);
            } else {
                bundleDataFiller.fill(bundle, next, objOpt.toString());
            }
        }
    }
}
