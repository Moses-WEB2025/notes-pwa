package com.xiaomi.micloudsdk.request;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RequestBase {
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATA = "data";

    protected abstract void addExtraParam(Map<String, String> map);

    protected abstract JSONObject getContentJSON() throws JSONException;

    public abstract boolean useGet();

    public Map<String, String> getParamsMap() throws JSONException {
        HashMap map = new HashMap();
        JSONObject contentJSON = getContentJSON();
        if (contentJSON != null) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("content", contentJSON);
            map.put("data", jSONObject.toString());
        }
        addExtraParam(map);
        return map;
    }
}
