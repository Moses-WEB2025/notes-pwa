package com.miui.notes.ai.editor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.floatingwindow.AiFloatingWindow;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class AiEditor implements IAiEditor, IWebViewListener {
    private static final long INPUT_THROTTLE_MS = 80;
    private static final long INPUT_THROTTLE_MS_MAX = 200;
    private static final String TAG = "AiEditor";
    protected Context mContext;
    protected String mData;
    protected boolean mPagePrepared;
    protected WebView mWebCore;
    protected IWebViewListener mWebViewListener;
    protected Gson mGson = new Gson();
    private final Handler mThrottleHandler = new Handler(Looper.getMainLooper());
    private final StringBuilder mInputBuffer = new StringBuilder();
    private boolean mInputIsThinkContent = false;
    private boolean mInputPending = false;
    private long mLastFlushCost = 0;
    private final Runnable mFlushInputRunnable = new Runnable() { // from class: com.miui.notes.ai.editor.AiEditor$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };

    private float getTextScale() {
        return 1.0f;
    }

    public int getFontWeight() {
        return 2;
    }

    public AiEditor(Context context, WebView webView, IWebViewListener iWebViewListener) {
        this.mData = "";
        this.mWebCore = webView;
        webView.setBackgroundColor(0);
        this.mContext = context;
        this.mData = "";
        this.mWebViewListener = iWebViewListener;
        initWebView();
    }

    protected void initWebView() {
        this.mWebCore.setWebViewClient(new AiWebViewClient());
        this.mWebCore.getSettings().setJavaScriptEnabled(true);
        this.mWebCore.getSettings().setTextZoom(100);
        this.mWebCore.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebCore.getSettings().setAllowFileAccess(false);
        this.mWebCore.getSettings().setDomStorageEnabled(true);
        this.mWebCore.getSettings().setAllowContentAccess(false);
        this.mWebCore.getSettings().setUseWideViewPort(true);
        this.mWebCore.getSettings().setBlockNetworkImage(false);
        WebView.setWebContentsDebuggingEnabled(false);
        this.mWebCore.loadUrl("file:///android_asset/noteeditor/index.html" + getInitParam());
        this.mWebCore.setWebChromeClient(new WebChromeClient() { // from class: com.miui.notes.ai.editor.AiEditor.1
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Logger.INSTANCE.i(AiEditor.TAG, "onConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        AiEditorScriptInterface aiEditorScriptInterface = new AiEditorScriptInterface();
        aiEditorScriptInterface.setWebViewListener(this);
        this.mWebCore.addJavascriptInterface(aiEditorScriptInterface, "rich_editor_interface");
    }

    protected String getInitParam() {
        float textScale = getTextScale();
        int fontWeight = getFontWeight();
        return String.format("?themeId=%s&textScale=%s&textPadding=%s&borderPadding=%s&fontWeight=%s", this.mGson.toJson((Object) 0), this.mGson.toJson(Float.valueOf(textScale)), this.mGson.toJson(new int[]{66, 0, 66, 0}), this.mGson.toJson(new int[]{0, 0, 0, 0}), this.mGson.toJson(Integer.valueOf(fontWeight)));
    }

    public void resetScale() {
        WebView webView = this.mWebCore;
        if (webView != null) {
            Logger.INSTANCE.d(TAG, "getScale: " + webView.getScale());
            this.mWebCore.setInitialScale(100);
        }
    }

    public void setEnableEdit(boolean z) {
        Logger.INSTANCE.i(TAG, "setEnableEdit: " + z);
        this.mWebCore.evaluateJavascript("javascript:setEnableEdit(" + z + ")", null);
    }

    public void destroy() {
        this.mThrottleHandler.removeCallbacks(this.mFlushInputRunnable);
        this.mInputPending = false;
        synchronized (this.mInputBuffer) {
            this.mInputBuffer.setLength(0);
        }
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);
            this.mWebCore.stopLoading();
            this.mWebCore.clearHistory();
            this.mWebCore.removeAllViewsInLayout();
            this.mWebCore.removeAllViews();
            this.mWebCore.setWebChromeClient(null);
            this.mWebCore.setWebViewClient(null);
            this.mWebCore.clearCache(true);
            this.mWebCore.destroy();
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void closeCorrectionPop() {
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:closeCorrectionPop()", null);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void doCopy() {
        if (this.mWebCore != null) {
            if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_CHECK) {
                this.mWebCore.evaluateJavascript("javascript:trimCorrection(true)", new ValueCallback<String>() { // from class: com.miui.notes.ai.editor.AiEditor.2
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(String str) {
                        if (str != null) {
                            String strReplace = (String) new Gson().fromJson(str, String.class);
                            if (RomUtils.isInternationalBuild()) {
                                strReplace = strReplace.replace(AiEditor.this.mContext.getString(R.string.ai_content_generates_reminders), "");
                            } else if (AiFloatingWindow.mCorrectMisNum == 0) {
                                strReplace = strReplace.replace(AiEditor.this.mContext.getString(R.string.ai_generate_tip), "");
                            }
                            Logger.INSTANCE.i(AiEditor.TAG, "doCopy AiFloatingWindow.mCorrectMisNum:" + AiFloatingWindow.mCorrectMisNum);
                            Logger.INSTANCE.i(AiEditor.TAG, "doCopy:" + strReplace.length());
                            ClipManager.INSTANCE.htmlUpdate(strReplace, AiEditor.this.mContext);
                        }
                    }
                });
            } else {
                this.mWebCore.evaluateJavascript("javascript:getCopiedHtml(true)", new ValueCallback<String>() { // from class: com.miui.notes.ai.editor.AiEditor.3
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(String str) {
                        if (str != null) {
                            String strReplace = (String) new Gson().fromJson(str, String.class);
                            if (RomUtils.isInternationalBuild()) {
                                strReplace = strReplace.replace(AiEditor.this.mContext.getString(R.string.ai_content_generates_reminders), "");
                            }
                            Logger.INSTANCE.i(AiEditor.TAG, "doCopy:" + strReplace.length());
                            ClipManager.INSTANCE.htmlUpdate(strReplace, AiEditor.this.mContext);
                        }
                    }
                });
            }
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void addText(String str, boolean z, boolean z2) {
        Logger.INSTANCE.i(TAG, "addText mPagePrepared: " + this.mPagePrepared + ", firstFlow:" + z);
        if (this.mPagePrepared) {
            insertSavedDataToPanelEnd(str, z, z2);
        } else {
            this.mData += str;
        }
        setEnableEdit(false);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void clearDoc() {
        this.mWebCore.evaluateJavascript("javascript:clearDoc()", null);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void finishFlow(boolean z, String str, String str2) {
        Logger.INSTANCE.i(TAG, "finishFlow");
        lambda$new$0();
        this.mWebCore.evaluateJavascript("javascript:finishFlow(" + z + "," + this.mGson.toJson(str) + ")", null);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void stopInput(String str) {
        Logger.INSTANCE.i(TAG, "stopInput, empty:" + TextUtils.isEmpty(str));
        lambda$new$0();
        this.mWebCore.evaluateJavascript("javascript:stopAiInput(" + this.mGson.toJson(str) + ")", null);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void onDataLoading() {
        toggleEmptyLoading();
    }

    public void setDataWithConfig(String str, String str2, Boolean bool) {
        this.mWebCore.evaluateJavascript("javascript:setDataWithConfig(" + this.mGson.toJson(str) + ", " + this.mGson.toJson(str2) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson(bool) + ")", null);
    }

    public void setData(String str, String str2, Boolean bool) {
        this.mWebCore.evaluateJavascript("javascript:setData(" + this.mGson.toJson(str) + ", " + this.mGson.toJson(str2) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson(bool) + ")", null);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onPagePrepared() {
        this.mPagePrepared = true;
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onPagePrepared();
        }
        if (TextUtils.isEmpty(this.mData)) {
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.miui.notes.ai.editor.AiEditor.4
            @Override // java.lang.Runnable
            public void run() {
                AiEditor aiEditor = AiEditor.this;
                aiEditor.addText(aiEditor.mData, true, false);
            }
        }, 0L);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onCorrectNumChanged(int i) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onCorrectNumChanged(i);
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onFlowDone() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onFlowDone();
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void notifyRefreshComplete() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.notifyRefreshComplete();
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean panelNeedTaller() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.panelNeedTaller();
        }
        return false;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean isNightMode() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.isNightMode();
        }
        return false;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onSaveData(String str) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onSaveData(str);
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void notifySetNewContent() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.notifySetNewContent();
        }
    }

    public void toggleEmptyLoading() {
        Logger.INSTANCE.i(TAG, "toggleEmptyLoading");
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:toggleEmptyLoading()", null);
            setEnableEdit(false);
        }
    }

    public void insertSavedDataToPanelEnd(String str, boolean z, boolean z2) {
        if (str == null) {
            str = "";
        }
        Logger.INSTANCE.i(TAG, "insertSavedDataToPanelEnd, size:" + str.length());
        if (z) {
            lambda$new$0();
            this.mWebCore.evaluateJavascript("javascript:insertSavedDataToPanelEnd(" + this.mGson.toJson(str) + ", true, " + z2 + ")", null);
            return;
        }
        synchronized (this.mInputBuffer) {
            this.mInputBuffer.append(str);
            this.mInputIsThinkContent = z2;
        }
        if (this.mInputPending) {
            return;
        }
        this.mInputPending = true;
        this.mThrottleHandler.postDelayed(this.mFlushInputRunnable, getAdaptiveThrottleMs());
    }

    private long getAdaptiveThrottleMs() {
        long j = this.mLastFlushCost;
        return j <= 0 ? INPUT_THROTTLE_MS : Math.min(Math.max(INPUT_THROTTLE_MS, j * 2), INPUT_THROTTLE_MS_MAX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: flushInputBuffer, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.mInputPending = false;
        this.mThrottleHandler.removeCallbacks(this.mFlushInputRunnable);
        synchronized (this.mInputBuffer) {
            if (this.mInputBuffer.length() == 0) {
                return;
            }
            String string = this.mInputBuffer.toString();
            boolean z = this.mInputIsThinkContent;
            this.mInputBuffer.setLength(0);
            WebView webView = this.mWebCore;
            if (webView != null) {
                webView.evaluateJavascript("javascript:insertSavedDataToPanelEnd(" + this.mGson.toJson(string) + ", false, " + z + ")", new ValueCallback() { // from class: com.miui.notes.ai.editor.AiEditor$$ExternalSyntheticLambda0
                    @Override // android.webkit.ValueCallback
                    public final void onReceiveValue(Object obj) {
                        this.f$0.lambda$flushInputBuffer$1(jCurrentTimeMillis, (String) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flushInputBuffer$1(long j, String str) {
        this.mLastFlushCost = System.currentTimeMillis() - j;
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void beforeRefreshNewContent() {
        Logger.INSTANCE.i(TAG, "beforeRefreshNewContent");
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:beforeRefreshNewContent()", null);
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getNumberAndStringFromResource(int i, String str) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.getNumberAndStringFromResource(i, str);
        }
        return "";
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getStringFromResource(String str) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.getStringFromResource(str);
        }
        return "";
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void setDataWithConfig(String str, String str2) {
        setDataWithConfig(str, str2, false);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void setData(String str, String str2) {
        setData(str, str2, false);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void refreshNewContent() {
        Logger.INSTANCE.i(TAG, "refreshNewContent");
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:refreshNewContent()", null);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void clearFlowStatus() {
        Logger.INSTANCE.i(TAG, "ai clearFlowStatus");
        this.mWebCore.evaluateJavascript("javascript:clearFlowStatus()", null);
    }
}
