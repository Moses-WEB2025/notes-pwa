package miui.cloud.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class XBundleSharedPerference {
    public static void savePreferencesBundle(SharedPreferences.Editor editor, String str, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        editor.putString(str, JSONObject.wrap(bundleToMap(bundle)).toString());
    }

    private static Map bundleToMap(Bundle bundle) {
        HashMap map = new HashMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null) {
                if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Boolean)) {
                    map.put(str, obj);
                } else if (obj instanceof CharSequence) {
                    map.put(str, ((CharSequence) obj).toString());
                } else if (obj instanceof Bundle) {
                    map.put(str, bundleToMap((Bundle) obj));
                }
            }
        }
        return map;
    }

    public static Bundle loadPreferencesBundle(SharedPreferences sharedPreferences, String str) {
        if (!sharedPreferences.contains(str)) {
            return null;
        }
        try {
            return JSONToBundle(new JSONObject(sharedPreferences.getString(str, null)));
        } catch (JSONException unused) {
            XLogger.log("Bad JSON stored in shared preference. ");
            return null;
        }
    }

    private static Bundle JSONToBundle(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (objOpt != null) {
                if (objOpt instanceof Integer) {
                    bundle.putInt(next, ((Integer) objOpt).intValue());
                } else if (objOpt instanceof Long) {
                    bundle.putLong(next, ((Long) objOpt).longValue());
                } else if (objOpt instanceof Boolean) {
                    bundle.putBoolean(next, ((Boolean) objOpt).booleanValue());
                } else if (objOpt instanceof CharSequence) {
                    bundle.putString(next, ((CharSequence) objOpt).toString());
                } else if (objOpt instanceof JSONObject) {
                    bundle.putBundle(next, JSONToBundle((JSONObject) objOpt));
                }
            }
        }
        return bundle;
    }
}
