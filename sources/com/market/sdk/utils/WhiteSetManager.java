package com.market.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.market.sdk.IMarketService;
import com.market.sdk.RemoteMethodInvoker;
import com.market.sdk.compat.FutureTaskCompat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class WhiteSetManager {
    private static final String DEFAULT_SERVICE_NAME = "defaultService";
    private static final String PREF_FILE_NAME_WHITE_SET = "pref_white_set";
    private static final String WHITE_SET_PKG_SUFFIX = "_pkg";
    private static final String WHITE_SET_SIG_SUFFIX = "_sig";

    public static boolean isInWhiteSetForApkCheck(Context context, String str, String str2) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String signatureWithPkgName = PkgUtils.getSignatureWithPkgName(context, str);
        if (TextUtils.isEmpty(signatureWithPkgName)) {
            return false;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = DEFAULT_SERVICE_NAME;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME_WHITE_SET, 0);
        Set<String> stringSet = sharedPreferences.getStringSet(getServicePkgPrefKey(str2), null);
        Set<String> stringSet2 = sharedPreferences.getStringSet(getServiceSigPrefKey(str2), null);
        if (stringSet != null && stringSet.contains(str) && stringSet2 != null && stringSet2.contains(signatureWithPkgName)) {
            z = true;
        }
        syncWhiteSetFromServer(context);
        return z;
    }

    private static String getServicePkgPrefKey(String str) {
        return str + WHITE_SET_PKG_SUFFIX;
    }

    private static String getServiceSigPrefKey(String str) {
        return str + WHITE_SET_SIG_SUFFIX;
    }

    private static void syncWhiteSetFromServer(Context context) {
        String whiteSetFromMarket = getWhiteSetFromMarket();
        if (TextUtils.isEmpty(whiteSetFromMarket)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(whiteSetFromMarket);
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            Iterator<String> itKeys = jSONObject.keys();
            while (true) {
                if (!itKeys.hasNext()) {
                    break;
                }
                String next = itKeys.next();
                JSONArray jSONArray = jSONObject.getJSONObject(next).getJSONArray("list");
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = new HashSet();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    String string = jSONObject2.getString("packageName");
                    String string2 = jSONObject2.getString(com.market.sdk.Constants.JSON_RELEASE_HASH);
                    hashSet.add(string);
                    hashSet2.add(string2);
                }
                map.put(next, hashSet);
                map2.put(next, hashSet2);
            }
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE_NAME_WHITE_SET, 0).edit();
            for (String str : map.keySet()) {
                Set<String> set = (Set) map.get(str);
                Set<String> set2 = (Set) map2.get(str);
                editorEdit.putStringSet(getServicePkgPrefKey(str), set);
                editorEdit.putStringSet(getServiceSigPrefKey(str), set2);
            }
            editorEdit.apply();
        } catch (Exception unused) {
        }
    }

    public static String getWhiteSetFromMarket() {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.utils.WhiteSetManager.1
            @Override // com.market.sdk.RemoteMethodInvoker
            public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                try {
                    iMarketService.getWhiteSetV2(new ResultReceiver(null) { // from class: com.market.sdk.utils.WhiteSetManager.1.1
                        @Override // android.os.ResultReceiver
                        public void onReceiveResult(int i, Bundle bundle) {
                            futureTaskCompat.set(bundle.getString("whiteSet"));
                        }
                    });
                } catch (RemoteException unused) {
                }
                return null;
            }
        }.invokeAsync();
        return (String) futureTaskCompat.get();
    }
}
