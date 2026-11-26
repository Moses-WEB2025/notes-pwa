package com.miui.notes.ai;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import com.google.gson.Gson;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.Logger;
import com.miui.hybirdeditor.EditorActionListener;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.hybirdeditor.WebRichEditorClient;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.richeditor.util.ResourceParser;

/* JADX INFO: loaded from: classes4.dex */
public class AiEditor {
    private static final String TAG = "AiEditor";
    private EditorActionListener mActionListener;
    private Object mBridge;
    protected Context mContext;
    protected int mFontSizeId;
    WebRichEditorClient.onRenderProcessGoneListener mOnRenderProcessGoneListener;
    protected RichEditorWebView mWebCore;
    protected boolean mIsKeyboardVisible = false;
    protected String mTitle = "";
    protected String mRichTextData = "";
    protected String mRichTextOriginData = "";
    protected Theme mEditorTheme = null;
    protected Gson mGson = new Gson();

    public AiEditor(RichEditorWebView richEditorWebView, Object obj, EditorActionListener editorActionListener, int i, Context context) {
        this.mFontSizeId = 1;
        this.mWebCore = richEditorWebView;
        richEditorWebView.setUseNativeActionMode(true);
        this.mBridge = obj;
        this.mActionListener = editorActionListener;
        this.mFontSizeId = i;
        this.mContext = context;
    }

    public void initWebCore(Theme theme) {
        Logger.INSTANCE.d(TAG, "initWebCore, theme id: " + theme.getId());
        this.mWebCore.setFeatureConfig("{\n\t\"select\": 1,\n    \"query\": 1,\n\t\"phrase\": 1,\n\t\"translate\": 1\n}");
        WebRichEditorClient webRichEditorClient = new WebRichEditorClient(this.mContext);
        webRichEditorClient.setOnRenderProcessGoneListener(this.mOnRenderProcessGoneListener);
        this.mWebCore.setWebViewClient(webRichEditorClient);
        this.mWebCore.getSettings().setJavaScriptEnabled(true);
        this.mWebCore.getSettings().setTextZoom(100);
        this.mWebCore.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebCore.getSettings().setAllowFileAccess(false);
        this.mWebCore.getSettings().setDomStorageEnabled(true);
        this.mWebCore.getSettings().setAllowContentAccess(false);
        this.mWebCore.getSettings().setUseWideViewPort(true);
        this.mWebCore.getSettings().setBlockNetworkImage(false);
        RichEditorWebView.setWebContentsDebuggingEnabled(false);
        this.mWebCore.loadUrl("file:///android_asset/noteeditor/index.html" + getInitParam(theme));
        this.mWebCore.setWebChromeClient(new WebChromeClient() { // from class: com.miui.notes.ai.AiEditor.1
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Logger.INSTANCE.i(AiEditor.TAG, "onConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        this.mWebCore.addJavascriptInterface(this.mBridge, "rich_editor_interface");
    }

    private String getInitParam(Theme theme) {
        if (theme == null) {
            return "";
        }
        this.mEditorTheme = theme;
        Rect rect = new Rect();
        Drawable drawable = this.mEditorTheme.getEditorStyle(this.mContext).mForeground;
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        float dimension = this.mContext.getResources().getDimension(R.dimen.note_editor_padding);
        int[] iArr = {(int) (rect.left + dimension), rect.top, (int) (rect.right + dimension), rect.bottom};
        Rect foregroundBorderPadding = this.mEditorTheme.getForegroundBorderPadding(this.mContext);
        return String.format("?themeId=%s&textScale=%s&textPadding=%s&borderPadding=%s&fontWeight=%s", this.mGson.toJson(Integer.valueOf(theme.getId())), this.mGson.toJson(Float.valueOf(getTextScale())), this.mGson.toJson(iArr), this.mGson.toJson(new int[]{foregroundBorderPadding.left, foregroundBorderPadding.top, foregroundBorderPadding.right, foregroundBorderPadding.bottom}), this.mGson.toJson(Integer.valueOf(getFontWeight())));
    }

    public void setOnRenderProcessGoneListener(WebRichEditorClient.onRenderProcessGoneListener onrenderprocessgonelistener) {
        this.mOnRenderProcessGoneListener = onrenderprocessgonelistener;
    }

    public void resetScale() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Logger.INSTANCE.d(TAG, "getScale: " + richEditorWebView.getScale());
            this.mWebCore.setInitialScale(100);
        }
    }

    public int getFontWeight() {
        return Math.min((DisplayUtils.getFontWeight(this.mContext) / 33) + 1, 3);
    }

    private float getTextScale() {
        float f = this.mContext.getResources().getDisplayMetrics().scaledDensity / this.mContext.getResources().getDisplayMetrics().density;
        float f2 = ResourceParser.TextAppearanceResources.FONT_SCALE[this.mFontSizeId];
        Logger.INSTANCE.d(TAG, "fontSystemScale " + f + " fontAppSettingScale " + f2);
        return f * f2;
    }

    public void setData(String str, String str2, Boolean bool) {
        Logger.INSTANCE.i(TAG, "setData");
        String strConvertToWeb = NoteSchema.convertToWeb(str);
        this.mRichTextData = strConvertToWeb;
        this.mRichTextOriginData = strConvertToWeb;
        this.mWebCore.evaluateJavascript("javascript:setData(" + this.mGson.toJson(strConvertToWeb + "\n<hr />\n<text indent=\"1\"><s1-size>" + NoteApp.getInstance().getString(R.string.ai_result_warn) + "</s1-size></text>") + ", " + this.mGson.toJson(str2) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson(bool) + ")", null);
    }

    public void setEnableEdit(boolean z) {
        Logger.INSTANCE.i(TAG, "setEnableEdit: " + z);
        this.mWebCore.evaluateJavascript("javascript:setEnableEdit(" + z + ")", null);
    }

    public void onSaveData(String str) {
        this.mRichTextOriginData = str;
        this.mRichTextData = null;
    }

    public void destroy() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.getSettings().setJavaScriptEnabled(false);
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

    public void resetData() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:closeCorrectionPop()", null);
            this.mWebCore.evaluateJavascript("javascript:setData(" + this.mGson.toJson("") + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson((Object) true) + ")", null);
        }
    }

    public void doCopy(boolean z) {
        this.mWebCore.evaluateJavascript("javascript:getCopiedHtml(true)", new ValueCallback<String>() { // from class: com.miui.notes.ai.AiEditor.2
            @Override // android.webkit.ValueCallback
            public void onReceiveValue(String str) {
                String str2 = (String) new Gson().fromJson(str, String.class);
                if (str2 == null) {
                    return;
                }
                String strTrimAiTextForNotes = NoteSchema.trimAiTextForNotes(str2);
                NoteClipManager.INSTANCE.clipString(strTrimAiTextForNotes);
                NoteClipManager.INSTANCE.hybridCopy(strTrimAiTextForNotes, NoteApp.getInstance());
            }
        });
    }

    public void replaceDoc(String str, ValueCallback valueCallback) {
        this.mWebCore.evaluateJavascript("javascript:setData(" + this.mGson.toJson(str) + ", " + this.mGson.toJson(this.mTitle) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson((Object) true) + ", " + this.mGson.toJson((Object) true) + ")", valueCallback);
    }

    public void clearDoc() {
        Logger.INSTANCE.i(TAG, "clearDoc");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:clearDoc()", null);
        }
    }

    public void addText(String str, boolean z) {
        Logger.INSTANCE.i(TAG, "addText,   firstInvoke:" + z);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertSavedDataToEnd(" + this.mGson.toJson(str) + ", " + this.mGson.toJson(Boolean.valueOf(z)) + ")", null);
        }
    }

    public void finishFlow() {
        Logger.INSTANCE.i(TAG, "finishFlow");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:finishFlow()", null);
        }
    }

    public void beforeRefreshNewContent() {
        Logger.INSTANCE.d(TAG, "beforeRefreshNewContent: ");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:beforeRefreshNewContent()", null);
        }
    }

    public void setDataWithConfig(String str, String str2) {
        Logger.INSTANCE.d(TAG, "setDataWithConfig");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setDataWithConfig(" + this.mGson.toJson(str) + ", " + this.mGson.toJson(str2) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson((Object) false) + ")", null);
        }
    }

    public void refreshNewContent() {
        Logger.INSTANCE.d(TAG, "refreshNewContent");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:refreshNewContent()", null);
        }
    }

    public void stopInput() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:stopAiInput()", null);
        }
    }

    public void stopInput(String str) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:stopAiInput(" + this.mGson.toJson(str) + ")", null);
        }
    }
}
