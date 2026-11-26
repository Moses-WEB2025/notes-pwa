package com.miui.maml.elements;

import android.content.Context;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.Variables;
import com.miui.maml.util.TextFormatter;
import com.xiaomi.mirror.synergy.CallMethod;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class WebViewScreenElement extends AnimatedScreenElement {
    private static final String LOG_TAG = "MAML.WebViewScreenE";
    public static final String TAG_NAME = "WebView";
    private static final int USE_NETWORK_ALL = 2;
    private static final int USE_NETWORK_WIFI = 1;
    private boolean mCachePage;
    private String mCurUrl;
    private Handler mHandler;
    private ViewGroup.LayoutParams mLayoutParams;
    private TextFormatter mUriFormatter;
    private int mUseNetwork;
    private Expression mUseNetworkExp;
    private boolean mViewAdded;
    private WebView mWebView;
    private Context mWindowContext;

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void render(Canvas canvas) {
    }

    private class MamlInterface {
        private MamlInterface() {
        }

        @JavascriptInterface
        public void putObj(String str, Object obj) {
            WebViewScreenElement.this.getVariables().put(str, obj);
        }

        @JavascriptInterface
        public void putString(String str, String str2) {
            WebViewScreenElement.this.getVariables().put(str, str2);
        }

        @JavascriptInterface
        public void putDouble(String str, double d) {
            WebViewScreenElement.this.getVariables().put(str, d);
        }

        @JavascriptInterface
        public void putInt(String str, int i) {
            WebViewScreenElement.this.getVariables().put(str, i);
        }

        @JavascriptInterface
        public int registerDoubleVariable(String str) {
            return WebViewScreenElement.this.getVariables().registerDoubleVariable(str);
        }

        @JavascriptInterface
        public int registerVariable(String str) {
            return WebViewScreenElement.this.getVariables().registerVariable(str);
        }

        @JavascriptInterface
        public double getDouble(int i) {
            return WebViewScreenElement.this.getVariables().getDouble(i);
        }

        @JavascriptInterface
        public double getDouble(String str) {
            return WebViewScreenElement.this.getVariables().getDouble(str);
        }

        @JavascriptInterface
        public String getString(int i) {
            return WebViewScreenElement.this.getVariables().getString(i);
        }

        @JavascriptInterface
        public String getString(String str) {
            return WebViewScreenElement.this.getVariables().getString(str);
        }

        @JavascriptInterface
        public Object getObj(String str) {
            return WebViewScreenElement.this.getVariables().get(str);
        }

        @JavascriptInterface
        public Object getObj(int i) {
            return WebViewScreenElement.this.getVariables().get(i);
        }

        @JavascriptInterface
        public void doAction(String str) {
            WebViewScreenElement.this.performAction(str);
        }
    }

    public WebViewScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mUseNetwork = 2;
        this.mWindowContext = screenElementRoot.getContext().mContext;
        WebView webView = new WebView(this.mWindowContext);
        this.mWebView = webView;
        webView.setWebViewClient(new WebViewClient() { // from class: com.miui.maml.elements.WebViewScreenElement.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                webView2.loadUrl(str);
                return true;
            }
        });
        this.mWebView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.maml.elements.WebViewScreenElement.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                WebViewScreenElement.this.mRoot.onUIInteractive(WebViewScreenElement.this, "touch");
                return false;
            }
        });
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setInitialScale(100);
        String attribute = element.getAttribute("userAgent");
        if (!TextUtils.isEmpty(attribute)) {
            this.mWebView.getSettings().setUserAgentString(attribute);
        }
        this.mWebView.addJavascriptInterface(new MamlInterface(), "maml");
        this.mLayoutParams = new ViewGroup.LayoutParams(-1, -1);
        this.mHandler = getContext().getHandler();
        Variables variables = getVariables();
        this.mUriFormatter = new TextFormatter(variables, element.getAttribute(CallMethod.ARG_URI), Expression.build(variables, element.getAttribute("uriExp")));
        this.mCachePage = Boolean.parseBoolean(element.getAttribute("cachePage"));
        String attribute2 = element.getAttribute("useNetwork");
        if (TextUtils.isEmpty(attribute2) || "all".equalsIgnoreCase(attribute2)) {
            this.mUseNetwork = 2;
        } else if ("wifi".equalsIgnoreCase(attribute2)) {
            this.mUseNetwork = 1;
        } else {
            this.mUseNetworkExp = Expression.build(variables, attribute2);
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        Expression expression = this.mUseNetworkExp;
        if (expression != null) {
            this.mUseNetwork = (int) expression.evaluate();
        }
        if (this.mRoot.getViewManager() != null) {
            initWebView();
        } else {
            Log.e(LOG_TAG, "ViewManager must be set before init");
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        finishWebView();
        if (this.mCachePage) {
            return;
        }
        this.mCurUrl = null;
    }

    public void loadUrl(final String str) {
        if (!canUseNetwork() && str.startsWith("http")) {
            Log.d(LOG_TAG, "loadUrl canceled due to useNetwork setting." + str);
        } else {
            this.mCurUrl = str;
            this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.3
                @Override // java.lang.Runnable
                public void run() {
                    WebViewScreenElement.this.mWebView.loadUrl(str);
                }
            });
        }
    }

    public void runjs(final String str) {
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.4
            @Override // java.lang.Runnable
            public void run() {
                WebViewScreenElement.this.mWebView.loadUrl("javascript:" + str);
            }
        });
    }

    public void reload() {
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.5
            @Override // java.lang.Runnable
            public void run() {
                WebViewScreenElement.this.mWebView.reload();
            }
        });
    }

    public void goBack() {
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m904lambda$goBack$0$commiuimamlelementsWebViewScreenElement();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$goBack$0$com-miui-maml-elements-WebViewScreenElement, reason: not valid java name */
    /* synthetic */ void m904lambda$goBack$0$commiuimamlelementsWebViewScreenElement() {
        this.mWebView.goBack();
    }

    private boolean canUseNetwork() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        int i = this.mUseNetwork;
        if (i == 2) {
            return true;
        }
        return i == 1 && (activeNetworkInfo = (connectivityManager = (ConnectivityManager) getContext().mContext.getSystemService("connectivity")).getActiveNetworkInfo()) != null && !connectivityManager.isActiveNetworkMetered() && activeNetworkInfo.isConnected();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        String text = this.mUriFormatter.getText();
        if (!TextUtils.isEmpty(text) && !TextUtils.equals(this.mCurUrl, text)) {
            Log.d(LOG_TAG, "loadUrl: " + text);
            loadUrl(text);
        }
        updateView();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onVisibilityChange(final boolean z) {
        super.onVisibilityChange(z);
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.6
            @Override // java.lang.Runnable
            public void run() {
                WebViewScreenElement.this.mWebView.setVisibility(z ? 0 : 4);
            }
        });
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        if (this.mViewAdded) {
            pauseWebView(true);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        if (this.mViewAdded) {
            pauseWebView(false);
        }
    }

    private void pauseWebView(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.7
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    WebViewScreenElement.this.mWebView.onPause();
                } else {
                    WebViewScreenElement.this.mWebView.onResume();
                }
            }
        });
    }

    private final void initWebView() {
        if (!this.mViewAdded || this.mCachePage) {
            this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.8
                @Override // java.lang.Runnable
                public void run() {
                    if (WebViewScreenElement.this.mViewAdded) {
                        if (WebViewScreenElement.this.mCachePage) {
                            WebViewScreenElement.this.mWebView.onResume();
                        }
                    } else {
                        WebViewScreenElement webViewScreenElement = WebViewScreenElement.this;
                        webViewScreenElement.updateLayoutParams(webViewScreenElement.mLayoutParams);
                        Log.d(WebViewScreenElement.LOG_TAG, "addWebView");
                        WebViewScreenElement.this.mRoot.getViewManager().addView(WebViewScreenElement.this.mWebView, WebViewScreenElement.this.mLayoutParams);
                        WebViewScreenElement.this.mViewAdded = true;
                    }
                }
            });
        }
    }

    private final void finishWebView() {
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.elements.WebViewScreenElement.9
            @Override // java.lang.Runnable
            public void run() {
                WebViewScreenElement.this.mRoot.getViewManager().removeView(WebViewScreenElement.this.mWebView);
                WebViewScreenElement.this.mViewAdded = false;
                if (WebViewScreenElement.this.mCachePage) {
                    WebViewScreenElement.this.mWebView.onPause();
                } else {
                    WebViewScreenElement.this.mWebView.loadUrl("about:blank");
                }
            }
        });
    }

    private final void updateView() {
        if (this.mViewAdded) {
            this.mWebView.setX(getAbsoluteLeft());
            this.mWebView.setY(getAbsoluteTop());
            if (updateLayoutParams(this.mLayoutParams)) {
                this.mWebView.setLayoutParams(this.mLayoutParams);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean z;
        int width = (int) getWidth();
        if (layoutParams.width != width) {
            layoutParams.width = width;
            z = true;
        } else {
            z = false;
        }
        int height = (int) getHeight();
        if (layoutParams.height == height) {
            return z;
        }
        layoutParams.height = height;
        return true;
    }
}
