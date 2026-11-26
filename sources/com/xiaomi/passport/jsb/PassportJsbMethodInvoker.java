package com.xiaomi.passport.jsb;

import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.PackageUtils;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodClosePage;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetAppInfo;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetConfig;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetDeviceInfo;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetLoadHistory;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetSIMInfo;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetSystemInfo;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetTZSign;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGoBack;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGoBacks;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGoForward;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGoForwards;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodIsInDarkMode;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodIsInTalkBackMode;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodListenInboxSms;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodRemoveAccount;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodRequestAndSetLoginCookies;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodSetAccountInfo;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShareMiniProgramToFriend;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowToast;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodStartPage;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodInvoker {
    private static final String JSON_PARAM_CODE = "code";
    private static final String JSON_PARAM_MESSAGE = "message";
    private static final String JSON_PARAM_RESULT = "result";
    private static final String TAG = "PassportJsbMethodInvoker";
    public static final String WEBVIEW_JS_INTERFACE_NAME = "PASSPORT_JSB_METHOD_INVOKER";
    private final WeakReference<PassportJsbWebView> mHostWebViewWeakRef;
    private final Map<String, PassportJsbMethod> mJsbMethods = new HashMap();

    public PassportJsbMethodInvoker(PassportJsbWebView passportJsbWebView, List<PassportJsbMethod> list) {
        this.mHostWebViewWeakRef = new WeakReference<>(passportJsbWebView);
        addJsbMethod(new PassportJsbMethodIsInDarkMode());
        addJsbMethod(new PassportJsbMethodIsInTalkBackMode());
        addJsbMethod(new PassportJsbMethodStartPage());
        addJsbMethod(new PassportJsbMethodClosePage());
        addJsbMethod(new PassportJsbMethodShowDialog());
        addJsbMethod(new PassportJsbMethodShowToast());
        addJsbMethod(new PassportJsbMethodListenInboxSms());
        addJsbMethod(new PassportJsbMethodGetConfig());
        addJsbMethod(new PassportJsbMethodGetSIMInfo());
        addJsbMethod(new PassportJsbMethodGetDeviceInfo());
        addJsbMethod(new PassportJsbMethodGetSystemInfo());
        addJsbMethod(new PassportJsbMethodGetTZSign());
        addJsbMethod(new PassportJsbMethodRemoveAccount());
        addJsbMethod(new PassportJsbMethodSetAccountInfo());
        addJsbMethod(new PassportJsbMethodGetLoadHistory());
        addJsbMethod(new PassportJsbMethodGoBack());
        addJsbMethod(new PassportJsbMethodGoForward());
        addJsbMethod(new PassportJsbMethodGoBacks());
        addJsbMethod(new PassportJsbMethodGoForwards());
        if (!TextUtils.isEmpty(PackageUtils.getWeiXinAppIDFromMetaData(passportJsbWebView.getContext()))) {
            addJsbMethod(new PassportJsbMethodShareMiniProgramToFriend(PackageUtils.getWeiXinAppIDFromMetaData(passportJsbWebView.getContext())));
        }
        addJsbMethod(new PassportJsbMethodGetAppInfo());
        addJsbMethod(new PassportJsbMethodRequestAndSetLoginCookies());
        if (list != null) {
            Iterator<PassportJsbMethod> it = list.iterator();
            while (it.hasNext()) {
                addJsbMethod(it.next());
            }
        }
    }

    @JavascriptInterface
    public boolean isMethodSupported(String str) {
        return this.mJsbMethods.get(str) != null;
    }

    @JavascriptInterface
    public String invokeMethod(String str) {
        return invokeMethod(str, new JSONObject());
    }

    @JavascriptInterface
    public String invokeMethod(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return invokeMethod(str, new JSONObject());
        }
        try {
            return invokeMethod(str, new JSONObject(str2));
        } catch (JSONException e) {
            AccountLogger.log(TAG, "invoke method " + str, e);
            return makeException(102, "json params is error format");
        }
    }

    @JavascriptInterface
    public String invokeMethod(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        PassportJsbMethod passportJsbMethod = this.mJsbMethods.get(str);
        if (passportJsbMethod == null) {
            AccountLogger.log(TAG, "invoke method " + str + " not found");
            return makeException(100, String.format("method %s is undefined", str));
        }
        return blockingInvokeMethodInMainThread(passportJsbMethod, jSONObject);
    }

    private String blockingInvokeMethodInMainThread(final PassportJsbMethod passportJsbMethod, final JSONObject jSONObject) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final String[] strArr = new String[1];
        Runnable runnable = new Runnable() { // from class: com.xiaomi.passport.jsb.PassportJsbMethodInvoker.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    PassportJsbWebView passportJsbWebView = (PassportJsbWebView) PassportJsbMethodInvoker.this.mHostWebViewWeakRef.get();
                    if (passportJsbWebView == null) {
                        strArr[0] = PassportJsbMethodInvoker.this.makeException(201, "WebView is null");
                    } else {
                        PassportJsbMethod.checkUrlDomainPermission(passportJsbWebView);
                        PassportJsbMethodResult passportJsbMethodResultInvoke = passportJsbMethod.invoke(passportJsbWebView, jSONObject);
                        if (passportJsbMethodResultInvoke == null) {
                            strArr[0] = PassportJsbMethodInvoker.this.makeException(150, "null result for method " + passportJsbMethod.getName());
                        } else {
                            strArr[0] = PassportJsbMethodInvoker.this.makeResult(passportJsbMethodResultInvoke);
                        }
                    }
                } catch (PassportJsbMethodException e) {
                    strArr[0] = PassportJsbMethodInvoker.this.makeException(e.errorCode, e.errorMessage);
                } catch (Exception e2) {
                    strArr[0] = PassportJsbMethodInvoker.this.makeException(200, e2.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            AccountLogger.log(TAG, "invoke method:" + passportJsbMethod.getName() + ", params=" + jSONObject + ", result=" + strArr[0]);
            return strArr[0];
        }
        try {
            PassportJsbWebView passportJsbWebView = this.mHostWebViewWeakRef.get();
            if (passportJsbWebView != null) {
                passportJsbWebView.post(runnable);
                countDownLatch.await();
                AccountLogger.log(TAG, "invoke method " + passportJsbMethod.getName() + " with " + jSONObject + " result=" + strArr[0]);
                return strArr[0];
            }
            AccountLogger.log(TAG, "invoke method " + passportJsbMethod.getName() + " WebView is null");
            return makeException(201, "WebView is null");
        } catch (InterruptedException unused) {
            AccountLogger.log(TAG, "invoke method " + passportJsbMethod.getName() + " interrupted");
            return makeException(101, "interrupted invoke method");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String makeException(int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", i);
            jSONObject.put("message", str);
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String makeResult(PassportJsbMethodResult passportJsbMethodResult) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 0);
            jSONObject.put("message", "ok");
            passportJsbMethodResult.fillResultInJsonObject(jSONObject, "result");
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }

    public void release() {
        Iterator<PassportJsbMethod> it = this.mJsbMethods.values().iterator();
        while (it.hasNext()) {
            it.next().release(this.mHostWebViewWeakRef.get());
        }
        this.mJsbMethods.clear();
    }

    public void addJsbMethod(PassportJsbMethod passportJsbMethod) {
        this.mJsbMethods.put(passportJsbMethod.getName(), passportJsbMethod);
    }
}
