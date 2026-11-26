package com.aios.apptoolsdk;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.aios.apptoolsdk.aidl.IAppToolProvider;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class AppToolProviderService extends Service {
    private static final String TAG = "AppToolProvider";
    private final Map<String, AppTool> toolMap = new HashMap();
    private final IAppToolProvider.Stub binder = new IAppToolProvider.Stub() { // from class: com.aios.apptoolsdk.AppToolProviderService.1
        @Override // com.aios.apptoolsdk.aidl.IAppToolProvider
        public String handleRequest(String str) {
            AppResult appResultError;
            try {
                if (!AppToolProviderService.this.verifyCallerOrDebug()) {
                    return AppToolProviderService.this.buildErrorResponse(0, -32600, "Caller not authorized: requires system signature");
                }
                JSONObject jSONObject = new JSONObject(str);
                int iOptInt = jSONObject.optInt("id", 0);
                String strOptString = jSONObject.optString(XMLWriter.METHOD, "");
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("params");
                if (strOptString.hashCode() == 498659858 && strOptString.equals("tools/call")) {
                    if (jSONObjectOptJSONObject == null) {
                        return AppToolProviderService.this.buildErrorResponse(iOptInt, -32602, "缺少 params");
                    }
                    String strOptString2 = jSONObjectOptJSONObject.optString("name", null);
                    if (strOptString2 == null) {
                        return AppToolProviderService.this.buildErrorResponse(iOptInt, -32602, "缺少 tool name");
                    }
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("arguments");
                    if (jSONObjectOptJSONObject2 == null) {
                        jSONObjectOptJSONObject2 = new JSONObject();
                    }
                    AppTool appTool = (AppTool) AppToolProviderService.this.toolMap.get(strOptString2);
                    if (appTool == null) {
                        return AppToolProviderService.this.buildErrorResponse(iOptInt, -32602, "未知工具: " + strOptString2);
                    }
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            appResultError = appTool.execute(new ToolArgs(jSONObjectOptJSONObject2));
                        } catch (Exception e) {
                            Log.e(AppToolProviderService.TAG, "工具 " + strOptString2 + " 执行异常", e);
                            appResultError = AppResult.error("执行异常: " + e.getMessage());
                        }
                        AppToolProviderService appToolProviderService = AppToolProviderService.this;
                        return appToolProviderService.buildResponse(iOptInt, appToolProviderService.buildCallResult(appResultError));
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
                return AppToolProviderService.this.buildErrorResponse(iOptInt, -32601, "Method not found: " + strOptString);
            } catch (Exception e2) {
                Log.e(AppToolProviderService.TAG, "处理请求异常", e2);
                return AppToolProviderService.this.buildErrorResponse(0, -32603, "Internal error: " + e2.getMessage());
            }
        }
    };

    protected abstract List<AppTool> onCreateTools();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        List<AppTool> listOnCreateTools = onCreateTools();
        for (AppTool appTool : listOnCreateTools) {
            this.toolMap.put(appTool.getName(), appTool);
        }
        Log.i(TAG, "已注册 " + listOnCreateTools.size() + " 个工具");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!verifyCallerOrDebug()) {
            Log.w(TAG, "onBind 拒绝：调用方不具备系统签名");
            return null;
        }
        Log.i(TAG, "Service bound, action=" + intent.getAction());
        return this.binder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean verifyCallerOrDebug() {
        if ((getApplicationInfo().flags & 2) != 0) {
            return true;
        }
        return isCallerSystemSigned();
    }

    private boolean isCallerSystemSigned() {
        int callingUid = Binder.getCallingUid();
        PackageManager packageManager = getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        return (packagesForUid == null || packagesForUid.length == 0 || packageManager.checkSignatures(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME, packagesForUid[0]) != 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject buildCallResult(AppResult appResult) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "text");
        jSONObject.put("text", appResult.getText());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("content", jSONArray);
        jSONObject2.put("isError", appResult.isError());
        return jSONObject2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String buildResponse(int i, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i);
            jSONObject2.put("result", jSONObject);
            return jSONObject2.toString();
        } catch (Exception e) {
            return buildErrorResponse(i, -32603, "JSON build error: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String buildErrorResponse(int i, int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", i2);
            jSONObject.put("message", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i);
            jSONObject2.put(SNSAuthProvider.VALUE_SNS_ERROR, jSONObject);
            return jSONObject2.toString();
        } catch (Exception unused) {
            return "{\"jsonrpc\":\"2.0\",\"id\":" + i + ",\"error\":{\"code\":-32603,\"message\":\"Fatal error\"}}";
        }
    }
}
