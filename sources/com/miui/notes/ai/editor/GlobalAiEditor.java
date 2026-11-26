package com.miui.notes.ai.editor;

import android.content.Context;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.floatingwindow.GlobalAiStateManager;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: GlobalAiEditor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0014J\u0016\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eJ\u0012\u0010\u0012\u001a\u00020\u000b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000e¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/ai/editor/GlobalAiEditor;", "Lcom/miui/notes/ai/editor/AiEditor;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "webCore", "Landroid/webkit/WebView;", "webViewListener", "Lcom/miui/notes/ai/editor/IWebViewListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/webkit/WebView;Lcom/miui/notes/ai/editor/IWebViewListener;)V", "initWebView", "", "toggleEmptyLoading", "data", "", "isHidden", "", "updateEmptyLoadingText", "doCopy", "listener", "Lcom/miui/notes/ai/editor/GlobalAiEditor$GlobalAiEditorListener;", "htmlUpdate", "htmlString", "Companion", "GlobalAiEditorListener", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiEditor extends AiEditor {
    public static final String TAG = "GlobalAiEditor";

    /* JADX INFO: compiled from: GlobalAiEditor.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/editor/GlobalAiEditor$GlobalAiEditorListener;", "", "onEditorCopy", "", "content", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface GlobalAiEditorListener {
        void onEditorCopy(String content);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlobalAiEditor(Context context, WebView webView, IWebViewListener webViewListener) {
        super(context, webView, webViewListener);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(webViewListener, "webViewListener");
    }

    @Override // com.miui.notes.ai.editor.AiEditor
    protected void initWebView() {
        WebSettings settings;
        WebSettings settings2;
        WebSettings settings3;
        WebSettings settings4;
        WebSettings settings5;
        WebSettings settings6;
        WebSettings settings7;
        WebSettings settings8;
        AiWebViewClient aiWebViewClient = new AiWebViewClient();
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.setWebViewClient(aiWebViewClient);
        }
        WebView webView2 = this.mWebCore;
        if (webView2 != null && (settings8 = webView2.getSettings()) != null) {
            settings8.setJavaScriptEnabled(true);
        }
        WebView webView3 = this.mWebCore;
        if (webView3 != null && (settings7 = webView3.getSettings()) != null) {
            settings7.setTextZoom(100);
        }
        WebView webView4 = this.mWebCore;
        if (webView4 != null && (settings6 = webView4.getSettings()) != null) {
            settings6.setJavaScriptCanOpenWindowsAutomatically(true);
        }
        WebView webView5 = this.mWebCore;
        if (webView5 != null && (settings5 = webView5.getSettings()) != null) {
            settings5.setAllowFileAccess(false);
        }
        WebView webView6 = this.mWebCore;
        if (webView6 != null && (settings4 = webView6.getSettings()) != null) {
            settings4.setDomStorageEnabled(true);
        }
        WebView webView7 = this.mWebCore;
        if (webView7 != null && (settings3 = webView7.getSettings()) != null) {
            settings3.setAllowContentAccess(false);
        }
        WebView webView8 = this.mWebCore;
        if (webView8 != null && (settings2 = webView8.getSettings()) != null) {
            settings2.setUseWideViewPort(true);
        }
        WebView webView9 = this.mWebCore;
        if (webView9 != null && (settings = webView9.getSettings()) != null) {
            settings.setBlockNetworkImage(false);
        }
        WebView.setWebContentsDebuggingEnabled(false);
        WebView webView10 = this.mWebCore;
        if (webView10 != null) {
            webView10.loadUrl("file:///android_asset/noteeditor/index.html" + getInitParam());
        }
        WebView webView11 = this.mWebCore;
        if (webView11 != null) {
            webView11.setWebChromeClient(new WebChromeClient() { // from class: com.miui.notes.ai.editor.GlobalAiEditor.initWebView.1
                @Override // android.webkit.WebChromeClient
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Intrinsics.checkNotNullParameter(consoleMessage, "consoleMessage");
                    Logger.INSTANCE.i(GlobalAiEditor.TAG, "onConsoleMessage: " + consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);
                }
            });
        }
        GlobalAiEditorScriptInterface globalAiEditorScriptInterface = new GlobalAiEditorScriptInterface();
        globalAiEditorScriptInterface.setWebViewListener(this);
        WebView webView12 = this.mWebCore;
        if (webView12 != null) {
            webView12.addJavascriptInterface(globalAiEditorScriptInterface, "rich_editor_interface");
        }
    }

    public final void toggleEmptyLoading(String data, boolean isHidden) {
        Intrinsics.checkNotNullParameter(data, "data");
        WebView webView = this.mWebCore;
        if (webView != null) {
            Logger.INSTANCE.i(GlobalAiStateManager.TAG, "toggleEmptyLoading-------------------toggleEmptyLoading");
            webView.evaluateJavascript("javascript:toggleEmptyLoading(" + this.mGson.toJson(Boolean.valueOf(isHidden)) + ", " + this.mGson.toJson((Object) false) + ", " + this.mGson.toJson(data) + ")", null);
        }
    }

    public final void updateEmptyLoadingText(String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:updateEmptyLoadingText(" + this.mGson.toJson(data) + ")", null);
        }
    }

    public static /* synthetic */ void doCopy$default(GlobalAiEditor globalAiEditor, GlobalAiEditorListener globalAiEditorListener, int i, Object obj) {
        if ((i & 1) != 0) {
            globalAiEditorListener = null;
        }
        globalAiEditor.doCopy(globalAiEditorListener);
    }

    public final void doCopy(final GlobalAiEditorListener listener) {
        WebView webView = this.mWebCore;
        if (webView != null) {
            webView.evaluateJavascript("javascript:getCopiedHtml(true)", new ValueCallback() { // from class: com.miui.notes.ai.editor.GlobalAiEditor$$ExternalSyntheticLambda0
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    GlobalAiEditor.doCopy$lambda$3$lambda$2(this.f$0, listener, (String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doCopy$lambda$3$lambda$2(GlobalAiEditor globalAiEditor, GlobalAiEditorListener globalAiEditorListener, String str) {
        if (str != null) {
            String strReplace$default = (String) new Gson().fromJson(str, String.class);
            if (RomUtils.isInternationalBuild()) {
                Intrinsics.checkNotNull(strReplace$default);
                String string = globalAiEditor.mContext.getString(R.string.ai_content_generates_reminders);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                strReplace$default = StringsKt.replace$default(strReplace$default, string, "", false, 4, (Object) null);
            }
            Intrinsics.checkNotNull(strReplace$default);
            String strHtmlUpdate = globalAiEditor.htmlUpdate(strReplace$default);
            if (globalAiEditorListener != null) {
                globalAiEditorListener.onEditorCopy(strHtmlUpdate);
            }
        }
    }

    public final String htmlUpdate(String htmlString) {
        Intrinsics.checkNotNullParameter(htmlString, "htmlString");
        ArrayList<ClipManager.IClipData> arrayList = new ArrayList();
        ClipManager.INSTANCE.htmlParseInner(StringsKt.replace$default(htmlString, "<p class=\"\" data-indentation=\"1\"> </p>", "", false, 4, (Object) null), arrayList);
        for (ClipManager.IClipData iClipData : arrayList) {
            if (iClipData instanceof ClipManager.TextElement) {
                String string = ((ClipManager.TextElement) iClipData).getMText().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }
        return "";
    }
}
