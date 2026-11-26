package com.miui.notes.cloudservice;

import com.miui.notes.base.utils.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSchemaUtils {
    private static final String TAG = "NoteSchemaUtils";

    public static JSONArray getBatchResponseList(String str) throws JSONException, NoteSyncException {
        return getResponseDataObject(str).getJSONArray("list");
    }

    public static JSONObject getResponseDataObject(String str) throws JSONException, NoteSyncException {
        return getResponseDataObject(new JSONObject(str));
    }

    public static JSONObject getResponseDataObject(JSONObject jSONObject) throws JSONException, NoteSyncException {
        verifyResponseObject(jSONObject);
        return jSONObject.getJSONObject("data");
    }

    private static void verifyResponseObject(JSONObject jSONObject) throws JSONException, NoteSyncException {
        Logger.INSTANCE.d(TAG, "verifyResponseObject start ");
        int i = jSONObject.getInt("code");
        if (i != 0) {
            ServerResponseException serverResponseException = new ServerResponseException(jSONObject.optString("result", null), jSONObject.optString("reason", null), jSONObject.optString("description", null), i, jSONObject.optBoolean("retriable", true));
            String message = serverResponseException.getMessage();
            Logger.INSTANCE.e(TAG, message);
            if (i == 52000) {
                throw new NoteSyncException(serverResponseException);
            }
            throw new NoteSyncException(message, serverResponseException, false);
        }
    }
}
