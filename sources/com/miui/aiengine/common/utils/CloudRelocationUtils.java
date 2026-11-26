package com.miui.aiengine.common.utils;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.cloud.CloudServerException;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CloudRelocationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/aiengine/common/utils/CloudRelocationUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sNeedUpdateHostList", "", "sNeedInitHostList", "setNeedUpdateHostsList", "", "needUpdate", "checkRedirect", "", ah.H, "redirectCount", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudRelocationUtils {
    public static final CloudRelocationUtils INSTANCE = new CloudRelocationUtils();
    private static volatile Object sNeedInitHostList;
    private static volatile boolean sNeedUpdateHostList;

    private CloudRelocationUtils() {
    }

    private final void setNeedUpdateHostsList(boolean needUpdate) {
        sNeedUpdateHostList = needUpdate;
    }

    @JvmStatic
    public static final String checkRedirect(String response, int redirectCount) throws CloudServerException {
        Intrinsics.checkNotNullParameter(response, "response");
        if (redirectCount >= 15) {
            throw new CloudServerException(503, R2.drawable.abc_ic_menu_selectall_mtrl_alpha, 10);
        }
        try {
            JSONObject jSONObject = new JSONObject(response);
            if (jSONObject.getInt("code") == 308) {
                if (jSONObject.getJSONObject("data").optBoolean("isPermanent")) {
                    INSTANCE.setNeedUpdateHostsList(true);
                }
                return jSONObject.getJSONObject("data").getString("redirectUrl");
            }
            if (jSONObject.getInt("code") == 503) {
                throw new CloudServerException(503, 503, jSONObject.getJSONObject("data").getInt("retryAfter"));
            }
            if (jSONObject.getInt("code") != 10034) {
                return null;
            }
            throw new CloudServerException(503, R2.drawable.abc_ic_menu_selectall_mtrl_alpha, jSONObject.getJSONObject("data").getInt("retryAfter"));
        } catch (JSONException e) {
            Log.e("Micloud", "JSONException in checkRedirect():" + response, e);
            return null;
        }
    }
}
