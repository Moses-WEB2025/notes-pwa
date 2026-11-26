package miuix.hybrid;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class Response {
    private static final String CODE = "code";
    public static final int CODE_ACTION_ERROR = 205;
    public static final int CODE_ASYNC = 2;
    public static final int CODE_CALLBACK = 3;
    public static final int CODE_CANCEL = 100;
    public static final int CODE_CONFIG_ERROR = 201;
    public static final int CODE_FEATURE_ERROR = 204;
    public static final int CODE_GENERIC_ERROR = 200;
    public static final int CODE_PERMISSION_ERROR = 203;
    public static final int CODE_SIGNATURE_ERROR = 202;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_SYNC = 1;
    private static final String CONTENT = "content";
    private int mCode;
    private String mContent;
    private JSONObject mJson;

    public Response(int i) {
        this(i, "");
    }

    public Response(String str) {
        this(0, str);
    }

    public Response(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        this.mJson = jSONObject;
        this.mCode = i;
        this.mContent = str;
        try {
            jSONObject.put("code", i);
            this.mJson.put("content", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Response(JSONObject jSONObject) {
        this(0, jSONObject);
    }

    public Response(int i, JSONObject jSONObject) {
        this.mJson = new JSONObject();
        this.mCode = i;
        this.mContent = jSONObject.toString();
        try {
            this.mJson.put("code", this.mCode);
            this.mJson.put("content", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCode() {
        return this.mCode;
    }

    public String getContent() {
        return this.mContent;
    }

    public JSONObject getJson() {
        return this.mJson;
    }

    public String toString() {
        return this.mJson.toString();
    }
}
