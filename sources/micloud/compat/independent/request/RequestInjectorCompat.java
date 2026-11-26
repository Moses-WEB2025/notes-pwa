package micloud.compat.independent.request;

import android.content.Context;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import micloud.compat.independent.sync.GdprUtilsCompat;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class RequestInjectorCompat {
    private static IRequestInjectorCompat sRequestInjectorCompat = new RequestInjectorCompactImpl();

    private RequestInjectorCompat() {
    }

    public static void setRequestInjectorCompat(IRequestInjectorCompat iRequestInjectorCompat) {
        sRequestInjectorCompat = iRequestInjectorCompat;
    }

    public static void handleCloudServerException(Context context, CloudServerException cloudServerException) {
        if (cloudServerException.code == 503 && cloudServerException.retryTime > 0) {
            sRequestInjectorCompat.sendDataInTransferBroadcast(context, cloudServerException.retryTime);
        } else if (cloudServerException.code == 52003) {
            GdprUtilsCompat.notifyPrivacyDenied(context);
        }
    }

    public static void checkResponse(Context context, String str) {
        if (str != null && isPrivacyError(str)) {
            GdprUtilsCompat.notifyPrivacyDenied(context);
        }
    }

    private static boolean isPrivacyError(String str) {
        try {
            return new JSONObject(str).optInt("code", 0) == 52003;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
