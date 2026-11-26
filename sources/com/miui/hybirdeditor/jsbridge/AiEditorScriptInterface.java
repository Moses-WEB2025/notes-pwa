package com.miui.hybirdeditor.jsbridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.onetrack.c.j;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AiEditorScriptInterface.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b$\n\u0002\u0010\u0011\n\u0002\b\u001e\u0018\u0000 `2\u00020\u0001:\u0001`B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\nH\u0007J\b\u0010\u0014\u001a\u00020\u000eH\u0007J\u0010\u0010!\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0007J\u0018\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\nH\u0007J\b\u0010$\u001a\u00020\u001eH\u0007J\u0012\u0010%\u001a\u00020\u000e2\b\u0010&\u001a\u0004\u0018\u00010\nH\u0007J\b\u0010'\u001a\u00020\u001eH\u0007J\u0012\u0010(\u001a\u00020\u000e2\b\u0010)\u001a\u0004\u0018\u00010\nH\u0007J\u0018\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u001eH\u0007J\u0012\u0010-\u001a\u00020\u000e2\b\u0010.\u001a\u0004\u0018\u00010\nH\u0007J\u0012\u0010/\u001a\u00020\u000e2\b\u00100\u001a\u0004\u0018\u00010\nH\u0007J\b\u00101\u001a\u00020\u000eH\u0007J\u0010\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u0011H\u0007J\u0010\u00104\u001a\u00020\u000e2\u0006\u00105\u001a\u00020\u0011H\u0007J\u0010\u00106\u001a\u00020\u000e2\u0006\u00107\u001a\u00020\u0011H\u0007J\u0018\u00108\u001a\u00020\u000e2\u0006\u00109\u001a\u00020\u001e2\u0006\u0010:\u001a\u00020\u001eH\u0007J\u0012\u0010;\u001a\u00020\u000e2\b\u0010<\u001a\u0004\u0018\u00010\nH\u0007J\b\u0010=\u001a\u00020\u000eH\u0007J\b\u0010>\u001a\u00020\u000eH\u0007J\b\u0010?\u001a\u00020\u000eH\u0007J\b\u0010@\u001a\u00020\u000eH\u0007J\u001f\u0010A\u001a\u00020\u000e2\u0010\u0010B\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010CH\u0007¢\u0006\u0002\u0010DJ\u0010\u0010E\u001a\u00020\u000e2\u0006\u0010F\u001a\u00020\u001eH\u0007J\u0010\u0010G\u001a\u00020\u000e2\u0006\u0010H\u001a\u00020\u0011H\u0007J\b\u0010I\u001a\u00020\u000eH\u0007J\u0012\u0010J\u001a\u00020\u000e2\b\u0010<\u001a\u0004\u0018\u00010\nH\u0007J\u0012\u0010K\u001a\u00020\u000e2\b\u0010L\u001a\u0004\u0018\u00010\nH\u0007J\u0010\u0010M\u001a\u00020\u000e2\u0006\u0010N\u001a\u00020\u0011H\u0007J<\u0010O\u001a\u00020\u000e2\u0006\u0010P\u001a\u00020\u001e2\u0006\u0010Q\u001a\u00020\u001e2\u0006\u0010R\u001a\u00020\u001e2\u0006\u0010S\u001a\u00020\u001e2\b\u0010T\u001a\u0004\u0018\u00010\n2\b\u0010U\u001a\u0004\u0018\u00010\nH\u0007J\u001a\u0010V\u001a\u00020\u000e2\b\u0010W\u001a\u0004\u0018\u00010\n2\u0006\u0010X\u001a\u00020\u0011H\u0007J\b\u0010Y\u001a\u00020\u000eH\u0007J\b\u0010Z\u001a\u00020\u001eH\u0007J\u0010\u0010[\u001a\u00020\u000e2\u0006\u0010\\\u001a\u00020\u0011H\u0007J\b\u0010]\u001a\u00020\u000eH\u0007J\b\u0010^\u001a\u00020\u000eH\u0007J\b\u0010_\u001a\u00020\u000eH\u0007R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005R\u0011\u0010\t\u001a\u00020\n8G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0010\u001a\u00020\u00118G¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0015\u001a\u00020\n8G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\fR\u0011\u0010\u0017\u001a\u00020\n8G¢\u0006\u0006\u001a\u0004\b\u0018\u0010\fR\u0011\u0010\u0019\u001a\u00020\u001a8G¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e8G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0011\u0010 \u001a\u00020\u001e8G¢\u0006\u0006\u001a\u0004\b \u0010\u001f¨\u0006a"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/AiEditorScriptInterface;", "", "mEditorScriptInterfaceImpl", "Lcom/miui/hybirdeditor/jsbridge/EditorScriptInterface;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/hybirdeditor/jsbridge/EditorScriptInterface;)V", "getMEditorScriptInterfaceImpl", "()Lcom/miui/hybirdeditor/jsbridge/EditorScriptInterface;", "setMEditorScriptInterfaceImpl", "staticInfos", "", "getStaticInfos", "()Ljava/lang/String;", "showToast", "", "key", "deviceFlag", "", "getDeviceFlag", "()I", "onPagePrepared", "baseUri", "getBaseUri", "localeInfo", "getLocaleInfo", "contentLength", "", "getContentLength", "()J", "isDarkMode", "", "()Z", "isNoteCreated", "getStringFromResource", "getNumberAndStringFromResource", "number", "showMarkdown", "saveLog", "log", "canCreateMindNote", "onTextStyleChanged", "statusJson", "onUndoRedoStateChanged", "undoEnable", "redoEnable", "onSaveData", "newRichTextData", "onTitleChanged", "title", "onChangeToMindMap", "onTextCountChanged", "textCount", "onStateChanged", "state", "onPlayAudioStateChanged", "stateCode", "onRichTextPanelStateChanged", "isOpen", "withAnim", "onEmbedElementClicked", "elementJson", "onPopMenuHide", "onAnnotateImageInDocStart", "hidePopMenu", "onAnnotateImageInDocEnd", "onShareImage", "imgBase64", "", "([Ljava/lang/String;)V", "onDisableScroll", "disable", "onContentOverLength", "overType", "onImageAnnotationOverLength", "onStartDragElement", "trackEvent", j.b, "performHaptic", "typeId", "onUpdateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "htmlData", "onSelectedDataCallback", "value", "action", "doPaste", "showTitle", "onCorrectNumChanged", "changeCount", "onFlowDone", "notifySetNewContent", "notifyRefreshComplete", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiEditorScriptInterface {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String TAG = "Notes:HybridEditorScriptInterface";
    private EditorScriptInterface mEditorScriptInterfaceImpl;

    @JavascriptInterface
    public final boolean isNoteCreated() {
        return false;
    }

    @JavascriptInterface
    public final void onTextCountChanged(int textCount) {
    }

    @JavascriptInterface
    public final boolean showTitle() {
        return false;
    }

    public AiEditorScriptInterface(EditorScriptInterface editorScriptInterface) {
        this.mEditorScriptInterfaceImpl = editorScriptInterface;
    }

    public final EditorScriptInterface getMEditorScriptInterfaceImpl() {
        return this.mEditorScriptInterfaceImpl;
    }

    public final void setMEditorScriptInterfaceImpl(EditorScriptInterface editorScriptInterface) {
        this.mEditorScriptInterfaceImpl = editorScriptInterface;
    }

    @JavascriptInterface
    public final String getStaticInfos() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("isNoteCreated", false);
            String string = jSONObject.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        } catch (JSONException e) {
            Logger.INSTANCE.e(TAG, "getStaticInfos error: " + e);
            return "";
        }
    }

    @JavascriptInterface
    public final void showToast(String key) {
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.showToast(key);
        }
    }

    @JavascriptInterface
    public final int getDeviceFlag() {
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface == null) {
            return -1;
        }
        Intrinsics.checkNotNull(editorScriptInterface);
        return editorScriptInterface.getDeviceFlag();
    }

    @JavascriptInterface
    public final void onPagePrepared() {
        Logger.INSTANCE.i(TAG, "onPagePrepared");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.onPagePrepared();
        }
    }

    @JavascriptInterface
    public final String getBaseUri() {
        return "content://com.miui.notes/";
    }

    @JavascriptInterface
    public final String getLocaleInfo() {
        Logger.INSTANCE.d(TAG, "getLocaleInfo");
        JSONObject jSONObject = new JSONObject();
        try {
            Locale locale = BaseNoteApp.INSTANCE.getInstance().getResources().getConfiguration().getLocales().get(0);
            jSONObject.put("locale", locale.getLanguage());
            jSONObject.put("countryCode", locale.getCountry());
            jSONObject.put("isRtl", Utils.isRTL());
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getLocaleInfo error: " + e);
        }
        String string = jSONObject.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @JavascriptInterface
    public final long getContentLength() {
        Logger.INSTANCE.d(TAG, "getContentLength: ");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface == null) {
            return 0L;
        }
        Intrinsics.checkNotNull(editorScriptInterface);
        return editorScriptInterface.getContentLength();
    }

    @JavascriptInterface
    public final boolean isDarkMode() {
        return DisplayUtils.isNightMode();
    }

    @JavascriptInterface
    public final String getStringFromResource(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (Intrinsics.areEqual("original_text", key) || Intrinsics.areEqual("suggested_delete", key)) {
            String string = BaseNoteApp.INSTANCE.getInstance().getString(Utils.INSTANCE.getResId(key, R.string.class), new Object[]{"[[x]]"});
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        if (Intrinsics.areEqual("add_modifications", key)) {
            String string2 = BaseNoteApp.INSTANCE.getInstance().getString(Utils.INSTANCE.getResId(key, R.string.class), new Object[]{"[[y]]"});
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            return string2;
        }
        if (Intrinsics.areEqual("suggested_modifications", key)) {
            String string3 = BaseNoteApp.INSTANCE.getInstance().getString(Utils.INSTANCE.getResId(key, R.string.class), new Object[]{"[[x]]", "[[y]]"});
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return string3;
        }
        String string4 = BaseNoteApp.INSTANCE.getInstance().getResources().getString(Utils.INSTANCE.getResId(key, R.string.class));
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        return string4;
    }

    @JavascriptInterface
    public final String getNumberAndStringFromResource(int number, String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return Utils.obtainLocalInteger(number, Utils.INSTANCE.getResId(key, R.plurals.class));
    }

    @JavascriptInterface
    public final boolean showMarkdown() {
        return RomUtils.isNewTablet();
    }

    @JavascriptInterface
    public final void saveLog(String log) {
        Logger.INSTANCE.d(TAG, "saveLog");
        if (TextUtils.isEmpty(log)) {
            return;
        }
        Logger logger = Logger.INSTANCE;
        Intrinsics.checkNotNull(log);
        logger.i(TAG, log);
    }

    @JavascriptInterface
    public final boolean canCreateMindNote() {
        Logger.INSTANCE.d(TAG, "canCreateMindNote: ");
        return false;
    }

    @JavascriptInterface
    public final void onTextStyleChanged(String statusJson) {
        Logger.INSTANCE.d(TAG, "onTextStyleChanged");
    }

    @JavascriptInterface
    public final void onUndoRedoStateChanged(boolean undoEnable, boolean redoEnable) {
        Logger.INSTANCE.d(TAG, "onUndoRedoStateChanged");
    }

    @JavascriptInterface
    public final void onSaveData(String newRichTextData) {
        Logger.INSTANCE.d(TAG, "onSavaData");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.saveData(newRichTextData);
        }
    }

    @JavascriptInterface
    public final void onTitleChanged(String title) {
        Logger.INSTANCE.d(TAG, "onTitleChanged");
    }

    @JavascriptInterface
    public final void onChangeToMindMap() {
        Logger.INSTANCE.d(TAG, "onChangeToMindMap");
    }

    @JavascriptInterface
    public final void onStateChanged(int state) {
        Logger.INSTANCE.d(TAG, "onStateChanged: " + state);
    }

    @JavascriptInterface
    public final void onPlayAudioStateChanged(int stateCode) {
        Logger.INSTANCE.d(TAG, "onPlayAudioStateChanged");
    }

    @JavascriptInterface
    public final void onRichTextPanelStateChanged(boolean isOpen, boolean withAnim) {
        Logger.INSTANCE.d(TAG, "onRichTextPanelStateChanged");
    }

    @JavascriptInterface
    public final void onEmbedElementClicked(String elementJson) {
        Logger.INSTANCE.d(TAG, "onEmbedElementClicked");
    }

    @JavascriptInterface
    public final void onPopMenuHide() {
        Logger.INSTANCE.d(TAG, "onPopMenuHide");
    }

    @JavascriptInterface
    public final void onAnnotateImageInDocStart() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocStart");
    }

    @JavascriptInterface
    public final void hidePopMenu() {
        Logger.INSTANCE.d(TAG, "hidePopMenu");
    }

    @JavascriptInterface
    public final void onAnnotateImageInDocEnd() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocEnd");
    }

    @JavascriptInterface
    public final void onShareImage(String[] imgBase64) {
        Logger.INSTANCE.d(TAG, "onShareImage: ");
    }

    @JavascriptInterface
    public final void onDisableScroll(boolean disable) {
        Logger.INSTANCE.d(TAG, "onDisableScroll: ");
    }

    @JavascriptInterface
    public final void onContentOverLength(int overType) {
        Logger.INSTANCE.d(TAG, "onContentOverLength: ");
    }

    @JavascriptInterface
    public final void onImageAnnotationOverLength() {
        Logger.INSTANCE.d(TAG, "onImageAnnotationOverLength: ");
    }

    @JavascriptInterface
    public final void onStartDragElement(String elementJson) {
        Logger.INSTANCE.d(TAG, "onStartDragElement: ");
    }

    @JavascriptInterface
    public final void trackEvent(String eventName) {
        Logger.INSTANCE.d(TAG, "trackEvent: ");
    }

    @JavascriptInterface
    public final void performHaptic(int typeId) {
        Logger.INSTANCE.d(TAG, "performHaptic: ");
    }

    @JavascriptInterface
    public final void onUpdateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String htmlData) {
        Logger.INSTANCE.d(TAG, "onUpdateViewState: ");
    }

    @JavascriptInterface
    public final void onSelectedDataCallback(String value, int action) {
        Logger.INSTANCE.d(TAG, "onSelectedDataCallback: ");
    }

    @JavascriptInterface
    public final void doPaste() {
        Logger.INSTANCE.d(TAG, "doPaste: ");
    }

    @JavascriptInterface
    public final void onCorrectNumChanged(int changeCount) {
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.onCorrectNumChanged(changeCount);
        }
    }

    @JavascriptInterface
    public final void onFlowDone() {
        Logger.INSTANCE.i(TAG, "onFlowDone");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.onFlowDone();
        }
    }

    @JavascriptInterface
    public final void notifySetNewContent() {
        Logger.INSTANCE.i(TAG, "notifySetNewContent");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.notifySetNewContent();
        }
    }

    @JavascriptInterface
    public final void notifyRefreshComplete() {
        Logger.INSTANCE.i(TAG, "notifyRefreshComplete");
        EditorScriptInterface editorScriptInterface = this.mEditorScriptInterfaceImpl;
        if (editorScriptInterface != null) {
            Intrinsics.checkNotNull(editorScriptInterface);
            editorScriptInterface.notifyRefreshComplete();
        }
    }

    /* JADX INFO: compiled from: AiEditorScriptInterface.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/AiEditorScriptInterface$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "isInternational", "", "()Z", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JavascriptInterface
        public final boolean isInternational() {
            return RomUtils.isInternationalBuild();
        }
    }
}
