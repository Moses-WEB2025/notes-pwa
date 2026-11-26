package miuix.internal.hybrid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import miuix.hybrid.Callback;
import miuix.hybrid.HybridChromeClient;
import miuix.hybrid.HybridFeature;
import miuix.hybrid.HybridSettings;
import miuix.hybrid.HybridView;
import miuix.hybrid.HybridViewClient;
import miuix.hybrid.LifecycleListener;
import miuix.hybrid.NativeInterface;
import miuix.hybrid.PageContext;
import miuix.hybrid.Request;
import miuix.hybrid.Response;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes5.dex */
public class HybridManager {
    public static final String TAG = "hybrid";
    private static ExecutorService sPool = Executors.newCachedThreadPool();
    private static String sUserAgent;
    private Activity mActivity;
    private boolean mDetached;
    private FeatureManager mFM;
    private NativeInterface mNativeInterface;
    private PermissionManager mPM;
    private PageContext mPageContext;
    private HybridView mView;
    private Set<LifecycleListener> mListeners = new CopyOnWriteArraySet();
    private ConcurrentHashMap<String, Request> mReqMap = new ConcurrentHashMap<>();

    public HybridManager(Activity activity, HybridView hybridView) {
        this.mActivity = activity;
        this.mView = hybridView;
    }

    public void init(int i, String str) {
        this.mNativeInterface = new NativeInterface(this);
        Config configLoadConfig = loadConfig(i);
        config(configLoadConfig, false);
        initView();
        if (str == null && !TextUtils.isEmpty(configLoadConfig.getContent())) {
            str = resolveUri(configLoadConfig.getContent());
        }
        if (str != null) {
            this.mView.loadUrl(str);
        }
    }

    public void clear() {
        this.mReqMap.clear();
    }

    private Config loadConfig(int i) {
        XmlConfigParser xmlConfigParserCreateFromResId;
        try {
            if (i == 0) {
                xmlConfigParserCreateFromResId = XmlConfigParser.create(this.mActivity);
            } else {
                xmlConfigParserCreateFromResId = XmlConfigParser.createFromResId(this.mActivity, i);
            }
            return xmlConfigParserCreateFromResId.parse(null);
        } catch (HybridException e) {
            throw new RuntimeException("cannot load config: " + e.getMessage());
        }
    }

    private String config(Config config, boolean z) {
        if (z) {
            SecurityManager securityManager = new SecurityManager(config, this.mActivity.getApplicationContext());
            if (securityManager.isExpired() || !securityManager.isValidSignature()) {
                return new Response(202).toString();
            }
        }
        this.mFM = new FeatureManager(config, this.mActivity.getClassLoader());
        this.mPM = new PermissionManager(config);
        return new Response(0).toString();
    }

    public String config(String str) {
        try {
            return config(JsonConfigParser.createFromString(str).parse(null), true);
        } catch (HybridException e) {
            return new Response(201, e.getMessage()).toString();
        }
    }

    public void setPageContext(PageContext pageContext) {
        this.mPageContext = pageContext;
    }

    private void initView() {
        initSettings(this.mView.getSettings());
        this.mView.setHybridViewClient(new HybridViewClient());
        this.mView.setHybridChromeClient(new HybridChromeClient());
        this.mView.addJavascriptInterface(new JsInterface(this), JsInterface.INTERFACE_NAME);
        this.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: miuix.internal.hybrid.HybridManager.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                HybridManager.this.mDetached = false;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                HybridManager.this.mDetached = true;
            }
        });
    }

    private void initSettings(HybridSettings hybridSettings) {
        hybridSettings.setJavaScriptEnabled(true);
        hybridSettings.setUserAgentString(getUserAgent(hybridSettings.getUserAgentString()));
    }

    private String getUserAgent(String str) {
        if (sUserAgent == null) {
            StringBuilder sb = new StringBuilder(str);
            sb.append(" XiaoMi/HybridView/");
            Activity activity = this.mActivity;
            sb.append(getPackageInfo(activity, activity.getApplication().getPackageName()).versionName);
            sb.append(StringUtils.SPACE);
            sb.append(this.mActivity.getPackageName());
            sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
            Activity activity2 = this.mActivity;
            sb.append(getPackageInfo(activity2, activity2.getPackageName()).versionName);
            sUserAgent = sb.toString();
        }
        return sUserAgent;
    }

    private static PackageInfo getPackageInfo(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String resolveUri(String str) {
        if (Pattern.compile("^[a-z-]+://").matcher(str).find()) {
            return str;
        }
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        return "file:///android_asset/hybrid/" + str;
    }

    private HybridFeature buildFeature(String str) throws HybridException {
        if (!this.mPM.isValid(this.mPageContext.getUrl())) {
            throw new HybridException(203, "feature not permitted: " + str);
        }
        return this.mFM.lookupFeature(str);
    }

    private Request buildRequest(String str, String str2, String str3) {
        Request request = new Request();
        request.setAction(str2);
        request.setRawParams(str3);
        request.setPageContext(this.mPageContext);
        request.setView(this.mView);
        request.setNativeInterface(this.mNativeInterface);
        return request;
    }

    public String lookup(String str, String str2) {
        try {
            if (buildFeature(str).getInvocationMode(buildRequest(str, str2, null)) == null) {
                return new Response(205, "action not supported: " + str2).toString();
            }
            return new Response(0).toString();
        } catch (HybridException e) {
            return e.getResponse().toString();
        }
    }

    public String invoke(String str, String str2, String str3, String str4) {
        try {
            HybridFeature hybridFeatureBuildFeature = buildFeature(str);
            Request requestBuildRequest = buildRequest(str, str2, str3);
            HybridFeature.Mode invocationMode = hybridFeatureBuildFeature.getInvocationMode(requestBuildRequest);
            if (invocationMode == HybridFeature.Mode.SYNC) {
                callback(new Response(1), this.mPageContext, str4);
                return hybridFeatureBuildFeature.invoke(requestBuildRequest).toString();
            }
            String key = getKey(str, str2, str3, str4);
            this.mReqMap.put(key, requestBuildRequest);
            if (invocationMode == HybridFeature.Mode.ASYNC) {
                sPool.execute(new AsyncInvocation(this, hybridFeatureBuildFeature, key, str4));
                return new Response(2).toString();
            }
            requestBuildRequest.setCallback(new Callback(this, this.mPageContext, str4));
            sPool.execute(new AsyncInvocation(this, hybridFeatureBuildFeature, key, str4));
            return new Response(3).toString();
        } catch (HybridException e) {
            Response response = e.getResponse();
            callback(response, this.mPageContext, str4);
            return response.toString();
        }
    }

    private String getKey(String str, String str2, String str3, String str4) {
        return str + str2 + str3 + str4;
    }

    public boolean isDetached() {
        return this.mDetached;
    }

    private static class AsyncInvocation implements Runnable {
        private HybridFeature mFeature;
        private WeakReference<HybridManager> mHybridManager;
        private String mJsCallback;
        private String mRequestKey;

        public AsyncInvocation(HybridManager hybridManager, HybridFeature hybridFeature, String str, String str2) {
            this.mHybridManager = new WeakReference<>(hybridManager);
            this.mFeature = hybridFeature;
            this.mRequestKey = str;
            this.mJsCallback = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Request request;
            HybridManager hybridManager = this.mHybridManager.get();
            if (hybridManager == null || (request = (Request) hybridManager.mReqMap.remove(this.mRequestKey)) == null || hybridManager.getActivity().isFinishing() || hybridManager.getActivity().isDestroyed()) {
                return;
            }
            Response responseInvoke = this.mFeature.invoke(request);
            if (this.mFeature.getInvocationMode(request) == HybridFeature.Mode.ASYNC) {
                hybridManager.callback(responseInvoke, request.getPageContext(), this.mJsCallback);
            }
        }
    }

    public void callback(Response response, PageContext pageContext, String str) {
        if (response == null || TextUtils.isEmpty(str) || !pageContext.equals(this.mPageContext) || this.mDetached || this.mActivity.isFinishing()) {
            return;
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "non-blocking response is " + response.toString());
        }
        this.mActivity.runOnUiThread(new JsInvocation(response, str));
    }

    private class JsInvocation implements Runnable {
        private String mJsCallback;
        private Response mResponse;

        public JsInvocation(Response response, String str) {
            this.mResponse = response;
            this.mJsCallback = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            HybridManager.this.mView.loadUrl("javascript:" + HybridManager.this.buildCallbackJavascript(this.mResponse, this.mJsCallback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String buildCallbackJavascript(Response response, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str + "('" + response.toString().replace("\\", "\\\\").replace("'", "\\'") + "');";
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        this.mListeners.add(lifecycleListener);
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        this.mListeners.remove(lifecycleListener);
    }

    public void onPageChange() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPageChange();
        }
    }

    public void onStart() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStart();
        }
    }

    public void onResume() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onResume();
        }
    }

    public void onPause() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPause();
        }
    }

    public void onStop() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStop();
        }
    }

    public void onDestroy() {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestroy();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Iterator<LifecycleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onActivityResult(i, i2, intent);
        }
    }

    public HybridView getHybridView() {
        return this.mView;
    }
}
