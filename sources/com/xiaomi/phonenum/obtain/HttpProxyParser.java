package com.xiaomi.phonenum.obtain;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.utils.MapUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class HttpProxyParser extends Parser {
    private static final String TAG = "HttpProxyParser";
    private HttpFactory httpFactory;

    public HttpProxyParser(HttpFactory httpFactory) {
        this.httpFactory = httpFactory;
    }

    @Override // com.xiaomi.phonenum.obtain.Parser
    public Response parse(int i, String str) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject(str);
        if ("http".equals(jSONObject.getString("result"))) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("httpRequest");
            return excute(i, parseRequestJson(jSONObject2.getJSONObject(StatConstants.Event.REQUEST)), jSONObject2.getString("followup"));
        }
        if (this.next != null) {
            return this.next.parse(i, str);
        }
        throw new JSONException("result not support" + jSONObject);
    }

    private Response excute(int i, Request request, String str) throws IOException {
        return followUp(str, httpRequest(i, request));
    }

    private Response httpRequest(int i, Request request) {
        try {
            Response responseExcute = this.httpFactory.createHttpClient().excute(request);
            AccountLogger.log(TAG, "response " + responseExcute);
            return responseExcute;
        } catch (IOException e) {
            AccountLogger.log(TAG, "request ", e);
            return HttpError.CELLULAR_NETWORK_IO_EXCEPTION.result();
        }
    }

    private Response followUp(String str, Response response) throws IOException {
        HashMap map = new HashMap();
        map.put("requestTime", "" + response.time);
        map.put("code", "" + response.code);
        map.put("body", base64(response.body));
        map.put("headers", base64(headersToJsonString(response.headers)));
        return this.httpFactory.createHttpClient().excute(new Request.Builder().url(str).formBody(map).build());
    }

    private String base64(String str) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Base64.encodeToString(str.getBytes("utf-8"), 10);
    }

    private String headersToJsonString(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        try {
            JSONObject jSONObjectJoinToJson = MapUtil.joinToJson(map);
            if (jSONObjectJoinToJson != null) {
                return jSONObjectJoinToJson.toString().replace("\\", "");
            }
        } catch (JSONException e) {
            AccountLogger.log(TAG, "joinToJson", e);
        }
        return null;
    }

    private Request parseRequestJson(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString("url");
        return new Request.Builder().url(string).headers(MapUtil.jsonToMap(jSONObject.optJSONObject("headers"))).formBody(MapUtil.jsonToMap(jSONObject.optJSONObject("formBody"))).followRedirects(jSONObject.optInt("followRedirects") == 1).build();
    }
}
