package com.xiaomi.passport.webview;

import android.R;
import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xiaomi.accountsdk.account.XMPassportUserAgent;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.UIUtils;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodInvoker;
import com.xiaomi.passport.jsb.method_impl.PassportWebChromeClientWrapper;
import com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.utils.AnyUrl;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbWebView extends WebView {
    public static final String TAG = "PassportJsbWebView";
    private static Integer sTextZoom;
    private Bundle mDestroyedStateBundle;
    private boolean mIsDestroyed;
    private boolean mIsFloatingWindow;
    private PassportJsbMethodInvoker mJsbMethodInvoker;
    private RectF mLayer;
    Path mPath;
    private float[] mRadii;
    private float mRadius;
    private BgTask mUrlLoadPrepareTask;
    private List<Runnable> mViewPostRunnables;
    private PassportJsbWebChromeClient mWebChromeClient;
    private PassportJsbWebViewClient mWebViewClient;

    public PassportJsbWebView(Context context) {
        super(context);
        this.mLayer = new RectF();
        this.mRadius = 0.0f;
        this.mPath = new Path();
        this.mIsFloatingWindow = false;
        this.mDestroyedStateBundle = new Bundle();
        init(context);
    }

    public PassportJsbWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayer = new RectF();
        this.mRadius = 0.0f;
        this.mPath = new Path();
        this.mIsFloatingWindow = false;
        this.mDestroyedStateBundle = new Bundle();
        init(context);
    }

    public PassportJsbWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayer = new RectF();
        this.mRadius = 0.0f;
        this.mPath = new Path();
        this.mIsFloatingWindow = false;
        this.mDestroyedStateBundle = new Bundle();
        init(context);
    }

    public PassportJsbWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayer = new RectF();
        this.mRadius = 0.0f;
        this.mPath = new Path();
        this.mIsFloatingWindow = false;
        this.mDestroyedStateBundle = new Bundle();
        init(context);
    }

    public PassportJsbWebView(Context context, AttributeSet attributeSet, int i, boolean z) {
        super(context, attributeSet, i, z);
        this.mLayer = new RectF();
        this.mRadius = 0.0f;
        this.mPath = new Path();
        this.mIsFloatingWindow = false;
        this.mDestroyedStateBundle = new Bundle();
        init(context);
    }

    private void init(Context context) {
        if ((context.getApplicationInfo().flags & 2) != 0) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        PassportJsbMethodInvoker passportJsbMethodInvoker = new PassportJsbMethodInvoker(this, null);
        this.mJsbMethodInvoker = passportJsbMethodInvoker;
        addJavascriptInterface(passportJsbMethodInvoker, PassportJsbMethodInvoker.WEBVIEW_JS_INTERFACE_NAME);
        this.mViewPostRunnables = new ArrayList();
        this.mIsDestroyed = false;
        initSettings(context);
        initStyle(context);
        PassportJsbWebViewClient passportJsbWebViewClient = new PassportJsbWebViewClient();
        this.mWebViewClient = passportJsbWebViewClient;
        super.setWebViewClient(passportJsbWebViewClient);
        PassportJsbWebChromeClient passportJsbWebChromeClient = new PassportJsbWebChromeClient();
        this.mWebChromeClient = passportJsbWebChromeClient;
        super.setWebChromeClient(passportJsbWebChromeClient);
    }

    private void initSettings(Context context) {
        WebSettings settings = getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setJavaScriptEnabled(true);
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
        context.getCacheDir().getAbsolutePath();
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccess(false);
        settings.setUserAgentString(XMPassportUserAgent.getWebViewUserAgent(this, context) + " WebViewType/PassportJSBWebView");
        Integer num = sTextZoom;
        if (num != null) {
            settings.setTextZoom(num.intValue());
        }
    }

    private void initStyle(Context context) {
        int color;
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        if (UIUtils.isSystemNightMode(getContext())) {
            color = getResources().getColor(R.color.black);
        } else {
            color = getResources().getColor(R.color.white);
        }
        setBackgroundColor(color);
        UIUtils.adaptForceDarkInApi29(context, this);
    }

    public void addUrlInterceptor(UrlInterceptor urlInterceptor) {
        this.mWebViewClient.addUrlInterceptor(urlInterceptor);
    }

    public void addJsbMethod(PassportJsbMethod passportJsbMethod) {
        this.mJsbMethodInvoker.addJsbMethod(passportJsbMethod);
    }

    public void setUrlLoadListener(UrlLoadListener urlLoadListener) {
        this.mWebViewClient.setUrlLoadListener(urlLoadListener);
        this.mWebChromeClient.setUrlLoadListener(urlLoadListener);
    }

    public void updateFloatingWindow(boolean z) {
        this.mIsFloatingWindow = z;
    }

    public void setRadius(float f) {
        this.mRadius = f;
        this.mRadii = new float[]{f, f, f, f, f, f, f, f};
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mLayer.set(0.0f, 0.0f, i, i2);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mRadius > 0.0f && this.mIsFloatingWindow) {
            this.mPath.reset();
            this.mPath.addRoundRect(new RectF(0.0f, getScrollY(), this.mLayer.width(), getScrollY() + this.mLayer.height()), this.mRadii, Path.Direction.CW);
            canvas.clipPath(this.mPath);
        }
        super.onDraw(canvas);
    }

    public void loadUrl(final String str, final Map<String, String> map, List<UrlLoadPrepareTask> list) {
        if (list.isEmpty()) {
            AccountLogger.log(TAG, "request load url " + str);
            super.loadUrl(str, map);
            return;
        }
        BgTask bgTask = this.mUrlLoadPrepareTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
        }
        AccountLogger.log(TAG, "request prepare tasks=" + list.size());
        BgTask bgTask2 = new BgTask(new BgUrlLoadPrepareRunnable(getContext(), list), new BgTask.SuccessResultRunnable<UrlLoadPrepareResult>() { // from class: com.xiaomi.passport.webview.PassportJsbWebView.1
            @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
            public void run(UrlLoadPrepareResult urlLoadPrepareResult) {
                map.putAll(urlLoadPrepareResult.headers);
                AccountLogger.log(PassportJsbWebView.TAG, "request load url " + str);
                PassportJsbWebView.super.loadUrl(AnyUrl.make(str, urlLoadPrepareResult.urlParams), (Map<String, String>) map);
            }
        }, null);
        this.mUrlLoadPrepareTask = bgTask2;
        bgTask2.execute();
    }

    public void loadUrl(String str, List<UrlLoadPrepareTask> list) {
        loadUrl(str, new HashMap(), list);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str, Map<String, String> map) {
        loadUrl(str, map, new ArrayList());
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        loadUrl(str, new HashMap());
    }

    @Override // android.webkit.WebView
    public void setWebViewClient(WebViewClient webViewClient) {
        this.mWebViewClient.setBaseWebViewClient(webViewClient);
    }

    @Override // android.webkit.WebView
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.mWebChromeClient.setBaseWebChromeClient(webChromeClient);
    }

    private void saveStateBeforeDestroyed() {
        saveState(this.mDestroyedStateBundle);
    }

    public Bundle getDestroyedStateBundle() {
        return this.mDestroyedStateBundle;
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    @Override // android.webkit.WebView
    public void destroy() {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }
        clearCache(true);
        loadUrl("about:blank");
        onPause();
        destroyDrawingCache();
        clearHistory();
        removeJavascriptInterface(PassportJsbMethodInvoker.WEBVIEW_JS_INTERFACE_NAME);
        removeAllViews();
        this.mWebViewClient.release();
        this.mJsbMethodInvoker.release();
        Iterator<Runnable> it = this.mViewPostRunnables.iterator();
        while (it.hasNext()) {
            removeCallbacks(it.next());
        }
        this.mViewPostRunnables.clear();
        BgTask bgTask = this.mUrlLoadPrepareTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mUrlLoadPrepareTask = null;
        }
        saveStateBeforeDestroyed();
        removeJavascriptInterface(PassportJsbMethodInvoker.WEBVIEW_JS_INTERFACE_NAME);
        super.destroy();
        this.mIsDestroyed = true;
    }

    @Override // android.view.View
    public boolean post(Runnable runnable) {
        this.mViewPostRunnables.add(runnable);
        return super.post(runnable);
    }

    @Override // android.view.View
    public boolean postDelayed(Runnable runnable, long j) {
        this.mViewPostRunnables.add(runnable);
        return super.postDelayed(runnable, j);
    }

    private static class PassportJsbWebViewClient extends PassportWebViewClientWrapper {
        private BgTask<String> mRequestLoginTask;
        private List<UrlInterceptor> mUrlInterceptors;
        private UrlLoadListener mUrlLoadListener;

        private PassportJsbWebViewClient() {
            this.mUrlInterceptors = new ArrayList();
        }

        public void addUrlInterceptor(UrlInterceptor urlInterceptor) {
            this.mUrlInterceptors.add(urlInterceptor);
        }

        public void setUrlLoadListener(UrlLoadListener urlLoadListener) {
            this.mUrlLoadListener = urlLoadListener;
        }

        public void release() {
            Iterator<UrlInterceptor> it = this.mUrlInterceptors.iterator();
            while (it.hasNext()) {
                it.next().release();
            }
            this.mUrlInterceptors.clear();
            BgTask<String> bgTask = this.mRequestLoginTask;
            if (bgTask != null) {
                bgTask.cancelAndRelease();
                this.mRequestLoginTask = null;
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Iterator<UrlInterceptor> it = this.mUrlInterceptors.iterator();
            while (it.hasNext()) {
                if (it.next().shouldIntercept(webView.getContext(), str)) {
                    AccountLogger.log(PassportJsbWebView.TAG, "shouldOverrideUrlLoading: intercepted, url=" + str);
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(webView, str);
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (this.mUrlLoadListener != null) {
                AccountLogger.log(PassportJsbWebView.TAG, "onPageStarted: url=" + str);
                this.mUrlLoadListener.onLoadMainFrameStart((PassportJsbWebView) webView, str, bitmap);
            } else {
                super.onPageStarted(webView, str, bitmap);
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            if (this.mUrlLoadListener != null) {
                AccountLogger.log(PassportJsbWebView.TAG, "onPageFinished: url=" + str);
                this.mUrlLoadListener.onLoadMainFrameFinish((PassportJsbWebView) webView, str);
            } else {
                super.onPageFinished(webView, str);
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            if (this.mUrlLoadListener == null) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                return;
            }
            AccountLogger.log(PassportJsbWebView.TAG, "onReceivedHttpError: url=" + webResourceRequest.getUrl() + ", error=" + webResourceResponse.getStatusCode());
            if (webResourceRequest.isForMainFrame()) {
                this.mUrlLoadListener.onLoadMainFrameError((PassportJsbWebView) webView, webResourceRequest);
            } else {
                this.mUrlLoadListener.onLoadResourceError((PassportJsbWebView) webView, webResourceRequest);
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            if (this.mUrlLoadListener == null) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                return;
            }
            AccountLogger.log(PassportJsbWebView.TAG, "onReceivedError: url" + webResourceRequest.getUrl() + ", error=" + webResourceError.getErrorCode() + ":" + ((Object) webResourceError.getDescription()));
            if (webResourceRequest.isForMainFrame()) {
                this.mUrlLoadListener.onLoadMainFrameError((PassportJsbWebView) webView, webResourceRequest);
            } else {
                this.mUrlLoadListener.onLoadResourceError((PassportJsbWebView) webView, webResourceRequest);
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebViewClientWrapper, android.webkit.WebViewClient
        public void onReceivedLoginRequest(final WebView webView, String str, String str2, String str3) {
            if (!TextUtils.equals(str, "com.xiaomi")) {
                super.onReceivedLoginRequest(webView, str, str2, str3);
                return;
            }
            XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(webView.getContext());
            Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
            if (xiaomiAccount == null) {
                AccountLogger.log(PassportJsbWebView.TAG, "onReceivedLoginRequest: no xiaomi account, skip");
                return;
            }
            BgTask<String> bgTask = this.mRequestLoginTask;
            if (bgTask != null) {
                bgTask.cancelAndRelease();
            }
            BgTask<String> bgTask2 = new BgTask<>(new _RequestLoginRunnable(xiaomiAccountManager, xiaomiAccount, str3), new BgTask.SuccessResultRunnable<String>() { // from class: com.xiaomi.passport.webview.PassportJsbWebView.PassportJsbWebViewClient.1
                @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
                public void run(String str4) {
                    if (TextUtils.isEmpty(str4)) {
                        AccountLogger.log(PassportJsbWebView.TAG, "onReceivedLoginRequest: get empty redirect url, skip");
                    } else {
                        webView.loadUrl(str4);
                    }
                }
            }, new BgTask.ErrorResultRunnable() { // from class: com.xiaomi.passport.webview.PassportJsbWebView.PassportJsbWebViewClient.2
                @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
                public void run(Throwable th) {
                    AccountLogger.log(PassportJsbWebView.TAG, "onReceivedLoginRequest: get redirect url failed", th);
                }
            });
            this.mRequestLoginTask = bgTask2;
            bgTask2.execute();
        }

        private static class _RequestLoginRunnable implements BgTask.BgTaskRunnable<String> {
            private static final String WEB_LOGIN_PREFIX = "weblogin:";
            public final Account account;
            public final XiaomiAccountManager accountManager;
            public final String args;

            public _RequestLoginRunnable(XiaomiAccountManager xiaomiAccountManager, Account account, String str) {
                this.accountManager = xiaomiAccountManager;
                this.account = account;
                this.args = str;
            }

            @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
            public String run() throws Throwable {
                return this.accountManager.getServiceToken(this.account, "weblogin:" + this.args, null).get().serviceToken;
            }
        }
    }

    private static class PassportJsbWebChromeClient extends PassportWebChromeClientWrapper {
        private UrlLoadListener mUrlLoadListener;

        private PassportJsbWebChromeClient() {
        }

        public void setUrlLoadListener(UrlLoadListener urlLoadListener) {
            this.mUrlLoadListener = urlLoadListener;
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebChromeClientWrapper, android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            UrlLoadListener urlLoadListener = this.mUrlLoadListener;
            if (urlLoadListener != null) {
                urlLoadListener.onReceiveUrlTitle((PassportJsbWebView) webView, str);
            } else {
                super.onReceivedTitle(webView, str);
            }
        }

        @Override // com.xiaomi.passport.jsb.method_impl.PassportWebChromeClientWrapper, android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            UrlLoadListener urlLoadListener;
            super.onProgressChanged(webView, i);
            if (i != 100 || (urlLoadListener = this.mUrlLoadListener) == null) {
                return;
            }
            urlLoadListener.onLoadMainFrameFinish((PassportJsbWebView) webView, webView.getUrl());
        }
    }

    private static class BgUrlLoadPrepareRunnable implements BgTask.BgTaskRunnable<UrlLoadPrepareResult> {
        private final Context mAppContext;
        private final WeakReference<Context> mCurrentContextRef;
        private final List<UrlLoadPrepareTask> mUrlLoadPrepareTasks;

        public BgUrlLoadPrepareRunnable(Context context, List<UrlLoadPrepareTask> list) {
            this.mAppContext = context.getApplicationContext();
            this.mCurrentContextRef = new WeakReference<>(context);
            this.mUrlLoadPrepareTasks = list;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public UrlLoadPrepareResult run() {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            List<UrlLoadPrepareTask> list = this.mUrlLoadPrepareTasks;
            if (list == null || list.isEmpty()) {
                return new UrlLoadPrepareResult(map, map2);
            }
            Iterator<UrlLoadPrepareTask> it = this.mUrlLoadPrepareTasks.iterator();
            while (it.hasNext()) {
                it.next().execute(this.mAppContext, this.mCurrentContextRef, map, map2);
            }
            return new UrlLoadPrepareResult(map, map2);
        }
    }

    public static void setTextZoom(int i) {
        sTextZoom = Integer.valueOf(i);
    }
}
