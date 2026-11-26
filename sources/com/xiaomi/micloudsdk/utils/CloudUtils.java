package com.xiaomi.micloudsdk.utils;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.CloudRelocationUtils;
import com.xiaomi.micloudsdk.request.utils.Request;
import java.util.Collection;
import miui.accounts.ExtraAccountManager;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class CloudUtils {
    public static final int CODE_IS_INTERNATIONAL_ACCOUNT = 1;
    public static final int CODE_NOT_INTERNATIONAL_ACCOUNT = 0;
    public static final int CODE_UNKOWN_ERROR = 2;

    public static boolean checkWifiAvailability(Context context) {
        return NetworkUtils.checkWifiAvailability(context);
    }

    public static String getUserAgent() {
        return Request.getUserAgent();
    }

    public static int isInternationalAccount(boolean z) {
        return CloudRelocationUtils.isInternationalAccount(z);
    }

    public static String getRelocatedHost(String str, boolean z) throws CloudServerException {
        return CloudRelocationUtils.getRelocatedHost(str, z);
    }

    public static String updateRequestHost(String str, boolean z) throws CloudServerException {
        return CloudRelocationUtils.updateRequestHost(str, z);
    }

    public static Account getXiaomiAccount() {
        return getXiaomiAccount(Request.getContext());
    }

    public static Account getXiaomiAccount(Context context) {
        return ExtraAccountManager.getXiaomiAccount(context);
    }

    public static long getLongAttributeFromJson(JSONObject jSONObject, String str) throws JSONException {
        return JsonUtils.getLongAttributeFromJson(jSONObject, str);
    }

    public static <T> String concatAll(Collection<T> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T t : collection) {
            if (t != null) {
                String string = t.toString();
                if (!TextUtils.isEmpty(string)) {
                    if (sb.length() > 0) {
                        sb.append(str);
                    }
                    sb.append(string);
                }
            }
        }
        return sb.toString();
    }

    public static int handle5xx(Context context, CloudServerException cloudServerException) {
        if (cloudServerException.statusCode / 100 == 5) {
            int i = (cloudServerException.statusCode != 503 || cloudServerException.retryTime <= 0) ? 10000 : cloudServerException.retryTime;
            Log.w("Micloud", "Http 5xx error. retryTime: " + i);
            return i;
        }
        Log.e("Micloud", "Unrecognized server error " + cloudServerException.statusCode);
        return 0;
    }
}
