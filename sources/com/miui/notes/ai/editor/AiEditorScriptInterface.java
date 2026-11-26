package com.miui.notes.ai.editor;

import android.webkit.JavascriptInterface;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AiEditorScriptInterface {
    public static final String TAG = "AiEditorScriptInterface";
    IWebViewListener mWebViewListener;

    @JavascriptInterface
    public static boolean isInternational() {
        return false;
    }

    @JavascriptInterface
    public int getDeviceFlag() {
        return 0;
    }

    @JavascriptInterface
    public boolean isNoteCreated() {
        return false;
    }

    @JavascriptInterface
    public void onTextCountChanged(int i) {
    }

    @JavascriptInterface
    public boolean showMarkdown() {
        return false;
    }

    @JavascriptInterface
    public boolean showTitle() {
        return false;
    }

    @JavascriptInterface
    public void showToast(String str) {
    }

    public void setWebViewListener(IWebViewListener iWebViewListener) {
        this.mWebViewListener = iWebViewListener;
    }

    @JavascriptInterface
    public String getStaticInfos() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("isNoteCreated", false);
            return jSONObject.toString();
        } catch (JSONException e) {
            Logger.INSTANCE.e(TAG, "getStaticInfos error: " + e);
            return "";
        }
    }

    @JavascriptInterface
    public void onPagePrepared() {
        Logger.INSTANCE.i(TAG, "onPagePrepared");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onPagePrepared();
        }
    }

    @JavascriptInterface
    public String getBaseUri() {
        return "content://com.miui.notes/";
    }

    @JavascriptInterface
    public String getLocaleInfo() {
        Logger.INSTANCE.d(TAG, "getLocaleInfo");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("locale", "zh");
            jSONObject.put("countryCode", "cn");
            jSONObject.put("isRtl", false);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getLocaleInfo error: " + e);
        }
        return jSONObject.toString();
    }

    @JavascriptInterface
    public long getContentLength() {
        Logger.INSTANCE.d(TAG, "getContentLength: ");
        return 0L;
    }

    @JavascriptInterface
    public boolean isDarkMode() {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.isNightMode();
        }
        return false;
    }

    @JavascriptInterface
    public String getStringFromResource(String str) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.getStringFromResource(str);
        }
        return "";
    }

    @JavascriptInterface
    public String getNumberAndStringFromResource(int i, String str) {
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.getNumberAndStringFromResource(i, str);
        }
        return "";
    }

    @JavascriptInterface
    public void saveLog(String str) {
        Logger.INSTANCE.d(TAG, "saveLog");
    }

    @JavascriptInterface
    public boolean canCreateMindNote() {
        Logger.INSTANCE.d(TAG, "canCreateMindNote: ");
        return false;
    }

    @JavascriptInterface
    public void onTextStyleChanged(String str) {
        Logger.INSTANCE.d(TAG, "onTextStyleChanged");
    }

    @JavascriptInterface
    public void onUndoRedoStateChanged(boolean z, boolean z2) {
        Logger.INSTANCE.d(TAG, "onUndoRedoStateChanged");
    }

    @JavascriptInterface
    public void onSaveData(String str) {
        Logger.INSTANCE.d(TAG, "onSaveData");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onSaveData(str);
        }
    }

    @JavascriptInterface
    public void onTitleChanged(String str) {
        Logger.INSTANCE.d(TAG, "onTitleChanged");
    }

    @JavascriptInterface
    public void onChangeToMindMap() {
        Logger.INSTANCE.d(TAG, "onChangeToMindMap");
    }

    @JavascriptInterface
    public void onStateChanged(int i) {
        Logger.INSTANCE.d(TAG, "onStateChanged: " + i);
    }

    @JavascriptInterface
    public void onPlayAudioStateChanged(int i) {
        Logger.INSTANCE.d(TAG, "onPlayAudioStateChanged");
    }

    @JavascriptInterface
    public void onRichTextPanelStateChanged(boolean z, boolean z2) {
        Logger.INSTANCE.d(TAG, "onRichTextPanelStateChanged");
    }

    @JavascriptInterface
    public void onEmbedElementClicked(String str) {
        Logger.INSTANCE.d(TAG, "onEmbedElementClicked");
    }

    @JavascriptInterface
    public void onPopMenuHide() {
        Logger.INSTANCE.d(TAG, "onPopMenuHide");
    }

    @JavascriptInterface
    public void onAnnotateImageInDocStart() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocStart");
    }

    @JavascriptInterface
    public void hidePopMenu() {
        Logger.INSTANCE.d(TAG, "hidePopMenu");
    }

    @JavascriptInterface
    public void onAnnotateImageInDocEnd() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocEnd");
    }

    @JavascriptInterface
    public void onShareImage(String[] strArr) {
        Logger.INSTANCE.d(TAG, "onShareImage: ");
    }

    @JavascriptInterface
    public void onDisableScroll(boolean z) {
        Logger.INSTANCE.d(TAG, "onDisableScroll: ");
    }

    @JavascriptInterface
    public void onContentOverLength(int i) {
        Logger.INSTANCE.d(TAG, "onContentOverLength: ");
    }

    @JavascriptInterface
    public void onImageAnnotationOverLength() {
        Logger.INSTANCE.d(TAG, "onImageAnnotationOverLength: ");
    }

    @JavascriptInterface
    public void onStartDragElement(String str) {
        Logger.INSTANCE.d(TAG, "onStartDragElement: ");
    }

    @JavascriptInterface
    public void trackEvent(String str) {
        Logger.INSTANCE.d(TAG, "trackEvent: ");
    }

    @JavascriptInterface
    public void performHaptic(int i) {
        Logger.INSTANCE.d(TAG, "performHaptic: ");
    }

    @JavascriptInterface
    public void onUpdateViewState(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2) {
        Logger.INSTANCE.d(TAG, "onUpdateViewState: ");
    }

    @JavascriptInterface
    public void onSelectedDataCallback(String str, int i) {
        Logger.INSTANCE.d(TAG, "onSelectedDataCallback: ");
    }

    @JavascriptInterface
    public void doPaste() {
        Logger.INSTANCE.d(TAG, "doPaste: ");
    }

    @JavascriptInterface
    public void onCorrectNumChanged(int i) {
        Logger.INSTANCE.i(TAG, "onCorrectNumChanged:" + i);
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onCorrectNumChanged(i);
        }
    }

    @JavascriptInterface
    public void onFlowDone() {
        Logger.INSTANCE.i(TAG, "onFlowDone");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.onFlowDone();
        }
    }

    @JavascriptInterface
    public String getViewMode() {
        return "panel";
    }

    @JavascriptInterface
    public void notifySetNewContent() {
        Logger.INSTANCE.i(TAG, "notifySetNewContent");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.notifySetNewContent();
        }
    }

    @JavascriptInterface
    public void notifyRefreshComplete() {
        Logger.INSTANCE.i(TAG, "notifyRefreshComplete");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            iWebViewListener.notifyRefreshComplete();
        }
    }

    @JavascriptInterface
    public boolean panelNeedTaller() {
        Logger.INSTANCE.i(TAG, "panelNeedTaller");
        IWebViewListener iWebViewListener = this.mWebViewListener;
        if (iWebViewListener != null) {
            return iWebViewListener.panelNeedTaller();
        }
        return true;
    }

    @JavascriptInterface
    public boolean preserveAiStatements() {
        Logger.INSTANCE.i(TAG, "preserveAiStatements");
        return !RomUtils.isInternationalBuild();
    }
}
