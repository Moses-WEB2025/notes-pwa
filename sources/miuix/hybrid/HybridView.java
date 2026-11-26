package miuix.hybrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebBackForwardList;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.internal.hybrid.HybridManager;
import miuix.internal.hybrid.HybridProgressView;
import miuix.internal.hybrid.WebContainerView;
import miuix.internal.hybrid.provider.AbsWebView;
import miuix.internal.hybrid.provider.WebViewFactory;
import miuix.internal.hybrid.provider.WebViewFactoryProvider;

/* JADX INFO: loaded from: classes5.dex */
public class HybridView extends FrameLayout {
    private static final int PROGRESS_BAR_CIRCULAR = 1;
    private static final int PROGRESS_BAR_HORIZONTAL = 2;
    private static final int PROGRESS_BAR_NONE = 0;
    private Button mBtnRetry;
    private WebViewFactoryProvider mFactory;
    private HybridProgressView mHorizontalProgressView;
    private boolean mLoadingError;
    private HybridManager mManager;
    private int mProgressBarStyle;
    private ProgressBar mProgressView;
    private boolean mPullable;
    private ViewGroup mReloadView;
    private boolean mShowErrorPage;
    private TextView mTextProvider;
    private WebContainerView mWebContainer;
    private HybridSettings mWebSettings;
    private AbsWebView mWebView;

    public HybridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HybridViewStyle, 0, 0);
        this.mProgressBarStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.HybridViewStyle_hybridProgressBar, 0);
        this.mShowErrorPage = typedArrayObtainStyledAttributes.getBoolean(R.styleable.HybridViewStyle_hybridErrorPage, true);
        this.mPullable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.HybridViewStyle_hybridPullable, true);
        typedArrayObtainStyledAttributes.recycle();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.hybrid_view_layout, (ViewGroup) this, true);
        WebViewFactoryProvider provider = WebViewFactory.getProvider(context);
        this.mFactory = provider;
        this.mWebView = provider.createWebView(context, this);
        WebContainerView webContainerView = (WebContainerView) findViewById(R.id.webContainer);
        this.mWebContainer = webContainerView;
        webContainerView.setWebView(this.mWebView.getBaseWebView());
        int i = this.mProgressBarStyle;
        if (i == 1) {
            this.mProgressView = (ProgressBar) findViewById(R.id.progress_circular);
        } else if (i == 2) {
            this.mHorizontalProgressView = (HybridProgressView) findViewById(R.id.progress_horizontal);
        }
        TextView textView = (TextView) findViewById(R.id.hybrid_provider);
        this.mTextProvider = textView;
        if (this.mPullable) {
            textView.setVisibility(0);
        }
    }

    HybridManager getHybridManager() {
        return this.mManager;
    }

    void setHybridManager(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }

    AbsWebView getWebView() {
        return this.mWebView;
    }

    public void setHybridViewClient(HybridViewClient hybridViewClient) {
        hybridViewClient.setHybridManager(this.mManager);
        this.mWebView.setWebViewClient(this.mFactory.createWebViewClient(hybridViewClient, this));
    }

    public void setHybridChromeClient(HybridChromeClient hybridChromeClient) {
        hybridChromeClient.setHybridManager(this.mManager);
        this.mWebView.setWebChromeClient(this.mFactory.createWebChromeClient(hybridChromeClient, this));
    }

    public void loadUrl(String str) {
        this.mWebView.loadUrl(str);
    }

    public void addJavascriptInterface(Object obj, String str) {
        this.mWebView.addJavascriptInterface(obj, str);
    }

    public HybridSettings getSettings() {
        if (this.mWebSettings == null) {
            this.mWebSettings = this.mWebView.getSettings();
        }
        return this.mWebSettings;
    }

    public void destroy() {
        this.mManager.clear();
        this.mWebView.destroy();
    }

    public void reload() {
        this.mWebView.reload();
    }

    public void clearCache(boolean z) {
        this.mWebView.clearCache(z);
    }

    public boolean canGoBack() {
        return this.mWebView.canGoBack();
    }

    public void goBack() {
        this.mWebView.goBack();
    }

    public String getUrl() {
        return this.mWebView.getUrl();
    }

    public String getTitle() {
        return this.mWebView.getTitle();
    }

    public void drawWebView(Canvas canvas) {
        this.mWebView.draw(canvas);
    }

    void setProgress(int i) {
        HybridProgressView hybridProgressView;
        if (i > 80 && !this.mLoadingError) {
            hideReloadView();
        }
        if (i == 100) {
            this.mWebContainer.setBackground(null);
        }
        ProgressBar progressBar = this.mProgressView;
        if (progressBar == null && this.mHorizontalProgressView == null) {
            return;
        }
        int i2 = this.mProgressBarStyle;
        if (i2 == 1) {
            if (progressBar == null) {
                return;
            }
            if (progressBar.getVisibility() == 8) {
                this.mProgressView.setVisibility(0);
            }
            this.mProgressView.setProgress(i);
            if (i == this.mProgressView.getMax()) {
                this.mProgressView.setVisibility(8);
                return;
            }
            return;
        }
        if (i2 != 2 || (hybridProgressView = this.mHorizontalProgressView) == null) {
            return;
        }
        if (hybridProgressView.getVisibility() == 8) {
            this.mHorizontalProgressView.setVisibility(0);
        }
        this.mHorizontalProgressView.setProgress(i);
        if (i == this.mHorizontalProgressView.getMax()) {
            this.mHorizontalProgressView.setVisibility(8);
        }
    }

    void setLoadingError(boolean z) {
        this.mLoadingError = z;
    }

    void showReloadView() {
        if (this.mShowErrorPage) {
            if (this.mReloadView == null) {
                ViewGroup viewGroup = (ViewGroup) ((ViewStub) findViewById(R.id.webview_reload_stub)).inflate();
                this.mReloadView = viewGroup;
                Button button = (Button) viewGroup.findViewById(R.id.reload);
                this.mBtnRetry = button;
                button.setOnClickListener(new View.OnClickListener() { // from class: miuix.hybrid.HybridView.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        HybridView.this.reload();
                        HybridView.this.setReloadContentVisibility(8);
                    }
                });
            }
            this.mReloadView.setVisibility(0);
            setReloadContentVisibility(0);
            this.mWebView.setVisibility(8);
        }
    }

    void hideReloadView() {
        if (this.mShowErrorPage) {
            ViewGroup viewGroup = this.mReloadView;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
            }
            this.mWebView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setReloadContentVisibility(int i) {
        int childCount = this.mReloadView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            this.mReloadView.getChildAt(i2).setVisibility(i);
        }
    }

    void setWebProvider(String str) {
        String host = Uri.parse(str).getHost();
        if (TextUtils.isEmpty(host)) {
            this.mTextProvider.setText("");
        } else {
            this.mTextProvider.setText(String.format(getContext().getString(R.string.hybrid_provider), host));
        }
    }

    public int getContentHeight() {
        return this.mWebView.getContentHeight();
    }

    public float getScale() {
        return this.mWebView.getScale();
    }

    public HybridBackForwardList copyBackForwardList() {
        return this.mWebView.copyBackForwardList();
    }

    public WebBackForwardList saveState(Bundle bundle) {
        return this.mWebView.saveState(bundle);
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        return this.mWebView.restoreState(bundle);
    }
}
