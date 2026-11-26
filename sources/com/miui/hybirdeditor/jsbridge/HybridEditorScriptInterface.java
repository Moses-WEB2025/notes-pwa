package com.miui.hybirdeditor.jsbridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.provider.Notes;
import com.xiaomi.onetrack.c.j;
import java.lang.ref.WeakReference;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: HybridEditorScriptInterface.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b,\n\u0002\u0010\u0011\n\u0002\b4\u0018\u0000 |2\u00020\u0001:\u0002{|B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\rH\u0007J\u0010\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0015H\u0007J\u0018\u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0015H\u0007J\u0012\u0010)\u001a\u00020\r2\b\u0010*\u001a\u0004\u0018\u00010\u0015H\u0007J\u0018\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000fH\u0007J\b\u0010.\u001a\u00020\u000fH\u0007J\u0012\u0010/\u001a\u00020\r2\b\u00100\u001a\u0004\u0018\u00010\u0015H\u0007J\u0012\u00101\u001a\u00020\r2\b\u00102\u001a\u0004\u0018\u00010\u0015H\u0007J\u0012\u00103\u001a\u00020\r2\b\u00104\u001a\u0004\u0018\u00010\u0015H\u0007J\b\u00105\u001a\u00020\rH\u0007J\u0010\u00108\u001a\u00020\r2\u0006\u00109\u001a\u00020\u0005H\u0007J\u0010\u0010:\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u0005H\u0007J\u0010\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020\u0005H\u0007J\u0018\u0010>\u001a\u00020\r2\u0006\u0010?\u001a\u00020\u000f2\u0006\u0010@\u001a\u00020\u000fH\u0007J\u0012\u0010A\u001a\u00020\r2\b\u0010B\u001a\u0004\u0018\u00010\u0015H\u0007J\b\u0010C\u001a\u00020\rH\u0007J\b\u0010D\u001a\u00020\rH\u0007J\b\u0010E\u001a\u00020\rH\u0007J\b\u0010F\u001a\u00020\rH\u0007J\u001b\u0010G\u001a\u00020\r2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00150IH\u0007¢\u0006\u0002\u0010JJ\u0010\u0010K\u001a\u00020\r2\u0006\u0010L\u001a\u00020\u000fH\u0007J\b\u0010M\u001a\u00020\u000fH\u0007J\u0010\u0010N\u001a\u00020\r2\u0006\u0010O\u001a\u00020\u0005H\u0007J\b\u0010P\u001a\u00020\rH\u0007J\u0012\u0010Q\u001a\u00020\r2\b\u0010B\u001a\u0004\u0018\u00010\u0015H\u0007J\u0012\u0010R\u001a\u00020\r2\b\u0010S\u001a\u0004\u0018\u00010\u0015H\u0007J\u0010\u0010T\u001a\u00020\r2\u0006\u0010U\u001a\u00020\u0005H\u0007J<\u0010V\u001a\u00020\r2\u0006\u0010W\u001a\u00020\u000f2\u0006\u0010X\u001a\u00020\u000f2\u0006\u0010Y\u001a\u00020\u000f2\u0006\u0010Z\u001a\u00020\u000f2\b\u0010[\u001a\u0004\u0018\u00010\u00152\b\u0010\\\u001a\u0004\u0018\u00010\u0015H\u0007J\u001a\u0010]\u001a\u00020\r2\b\u0010^\u001a\u0004\u0018\u00010\u00152\u0006\u0010_\u001a\u00020\u0005H\u0007J\b\u0010`\u001a\u00020\rH\u0007J\u0012\u0010a\u001a\u00020\r2\b\u0010&\u001a\u0004\u0018\u00010\u0015H\u0007J\u0010\u0010b\u001a\u00020\r2\u0006\u0010c\u001a\u00020\u0005H\u0007J\b\u0010d\u001a\u00020\rH\u0007J\b\u0010e\u001a\u00020\rH\u0007J\u001c\u0010d\u001a\u00020\r2\b\u0010f\u001a\u0004\u0018\u00010\u00152\b\u0010g\u001a\u0004\u0018\u00010\u0015H\u0007J\u0012\u0010d\u001a\u00020\r2\b\u0010f\u001a\u0004\u0018\u00010\u0015H\u0007J\b\u0010h\u001a\u00020\rH\u0007J\b\u0010k\u001a\u00020\rH\u0007J\b\u0010l\u001a\u00020\rH\u0007J\"\u0010m\u001a\u00020\r2\b\u0010n\u001a\u0004\u0018\u00010\u00152\u0006\u0010o\u001a\u00020\u000f2\u0006\u0010p\u001a\u00020\u0005H\u0007J\b\u0010q\u001a\u00020\rH\u0007J\u0012\u0010r\u001a\u00020\r2\b\u0010s\u001a\u0004\u0018\u00010\u0015H\u0007J\b\u0010t\u001a\u00020\u000fH\u0007J\b\u0010u\u001a\u00020\u000fH\u0007J\b\u0010v\u001a\u00020\rH\u0007J\b\u0010w\u001a\u00020\u000fH\u0007J\b\u0010x\u001a\u00020\u000fH\u0007J\b\u0010y\u001a\u00020\rH\u0007J\b\u0010z\u001a\u00020\u000fH\u0007R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u00158G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0019\u001a\u00020\u00158G¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u001b\u001a\u00020\u001c8G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u000f8G¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u000f8G¢\u0006\u0006\u001a\u0004\b!\u0010 R\u0011\u0010\"\u001a\u00020\u00058G¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0011\u00106\u001a\u00020\u00158G¢\u0006\u0006\u001a\u0004\b7\u0010\u0017R\u0011\u0010i\u001a\u00020\u00158G¢\u0006\u0006\u001a\u0004\bj\u0010\u0017¨\u0006}"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface;", "", "fragment", "Lmiuix/appcompat/app/Fragment;", "noteType", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lmiuix/appcompat/app/Fragment;I)V", "mFragment", "Ljava/lang/ref/WeakReference;", "mGson", "Lcom/google/gson/Gson;", "setExtraInfo", "", "isHide", "", "hasReference", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", "getFragment", "()Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", "staticInfos", "", "getStaticInfos", "()Ljava/lang/String;", "onPagePrepared", "baseUri", "getBaseUri", "contentLength", "", "getContentLength", "()J", "isDarkMode", "()Z", "isNoteCreated", "deviceFlag", "getDeviceFlag", "()I", "getStringFromResource", "key", "getNumberAndStringFromResource", "number", "onTextStyleChanged", "statusJson", "onUndoRedoStateChanged", "undoEnable", "redoEnable", "showMarkdown", "onSaveData", "newRichTextData", "saveLog", "log", "onTitleChanged", "title", "onChangeToMindMap", "localeInfo", "getLocaleInfo", "onTextCountChanged", "textCount", "onStateChanged", "state", "onPlayAudioStateChanged", "stateCode", "onRichTextPanelStateChanged", "isOpen", "withAnim", "onEmbedElementClicked", "elementJson", "onPopMenuHide", "onAnnotateImageInDocStart", "hidePopMenu", "onAnnotateImageInDocEnd", "onShareImage", "imgBase64", "", "([Ljava/lang/String;)V", "onDisableScroll", "disable", "canCreateMindNote", "onContentOverLength", "overType", "onImageAnnotationOverLength", "onStartDragElement", "trackEvent", j.b, "performHaptic", "typeId", "onUpdateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "htmlData", "onSelectedDataCallback", "value", "action", "doPaste", "showToast", "onCorrectNumChanged", "changeCount", "onFlowDone", "onStopDone", "data", Notes.Data.DATA2, "notifyRefreshComplete", "viewMode", "getViewMode", "notifySetNewContent", "stopAIRecorder", "showAIRecorderComponent", "audioFileName", "isPlaying", "startTime", "showAIRecorderRecording", "requestAudioDesc", "fileName", "usingAIRecorder", "panelNeedTaller", "onRefreshDone", "preserveAiStatements", "getAccessibilityState", "resetTextStatusForWebView", "isNewNoteFromMiShow", "StaticInfo", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HybridEditorScriptInterface {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static String STATIC_INFOS = null;
    public static final String TAG = "HybridEditorScriptInterface";
    private final WeakReference<Fragment> mFragment;
    private final Gson mGson = new Gson();

    @JvmStatic
    public static final void initStaticInfo() {
        INSTANCE.initStaticInfo();
    }

    @JavascriptInterface
    public final void onSelectedDataCallback(String value, int action) {
    }

    @JavascriptInterface
    public final void trackEvent(String eventName) {
    }

    public HybridEditorScriptInterface(Fragment fragment, int i) {
        this.mFragment = new WeakReference<>(fragment);
    }

    /* JADX INFO: compiled from: HybridEditorScriptInterface.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface$StaticInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "INFO", "Lorg/json/JSONObject;", "getINFO", "()Lorg/json/JSONObject;", "setINFO", "(Lorg/json/JSONObject;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class StaticInfo {
        private static JSONObject INFO;
        public static final StaticInfo INSTANCE = new StaticInfo();

        private StaticInfo() {
        }

        public final JSONObject getINFO() {
            return INFO;
        }

        public final void setINFO(JSONObject jSONObject) {
            INFO = jSONObject;
        }
    }

    public final void setExtraInfo(boolean isHide) {
        try {
            if (StaticInfo.INSTANCE.getINFO() == null) {
                INSTANCE.initStaticInfo();
            }
            JSONObject info = StaticInfo.INSTANCE.getINFO();
            Intrinsics.checkNotNull(info);
            info.put("info_isHide", isHide);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "setExtraInfo error: " + e);
        }
    }

    private final boolean hasReference() {
        return this.mFragment.get() != null;
    }

    private final BaseHybridNoteEditFragment getFragment() {
        return (BaseHybridNoteEditFragment) this.mFragment.get();
    }

    @JavascriptInterface
    public final String getStaticInfos() {
        if (!hasReference()) {
            return "";
        }
        if (!isNoteCreated()) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("isNoteCreated", false);
                STATIC_INFOS = jSONObject.toString();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "setIsNoteCreated error: " + e);
            }
        } else {
            if (StaticInfo.INSTANCE.getINFO() == null) {
                INSTANCE.initStaticInfo();
            } else {
                INSTANCE.updateStaticInfo();
            }
            try {
                JSONObject info = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info);
                info.put("device_flag", getDeviceFlag());
            } catch (JSONException e2) {
                Logger.INSTANCE.e(TAG, "error: " + e2);
            }
            STATIC_INFOS = String.valueOf(StaticInfo.INSTANCE.getINFO());
        }
        return STATIC_INFOS;
    }

    @JavascriptInterface
    public final void onPagePrepared() {
        Logger.INSTANCE.i(TAG, "onPagePrepared");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onPagePrepared();
        }
    }

    @JavascriptInterface
    public final String getBaseUri() {
        return "content://com.miui.notes/";
    }

    @JavascriptInterface
    public final long getContentLength() {
        if (getFragment() == null) {
            return 0L;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        return fragment.getTextCount();
    }

    @JavascriptInterface
    public final boolean isDarkMode() {
        if (!hasReference()) {
            return false;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        return fragment.isDarkMode();
    }

    @JavascriptInterface
    public final boolean isNoteCreated() {
        if (!hasReference()) {
            return false;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        boolean zIsNoteCreated = fragment.isNoteCreated();
        Logger.INSTANCE.i(TAG, "isNoteCreated: " + zIsNoteCreated);
        return zIsNoteCreated;
    }

    @JavascriptInterface
    public final int getDeviceFlag() {
        if (!hasReference()) {
            return -1;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        return fragment.getDeviceFlag();
    }

    @JavascriptInterface
    public final String getStringFromResource(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = BaseNoteApp.INSTANCE.getInstance().getResources().getString(Utils.INSTANCE.getResId(key, R.string.class));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @JavascriptInterface
    public final String getNumberAndStringFromResource(int number, String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return Utils.obtainLocalInteger(number, Utils.INSTANCE.getResId(key, R.plurals.class));
    }

    @JavascriptInterface
    public final void onTextStyleChanged(String statusJson) {
        Logger.INSTANCE.d(TAG, "onTextStyleChanged " + statusJson);
        if (hasReference()) {
            TextStatusBean textStatusBean = (TextStatusBean) this.mGson.fromJson(statusJson, TextStatusBean.class);
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onTextStyleChanged(textStatusBean);
        }
    }

    @JavascriptInterface
    public final void onUndoRedoStateChanged(boolean undoEnable, boolean redoEnable) {
        Logger.INSTANCE.d(TAG, "onUndoRedoStateChanged");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onUndoRedoStateChanged(undoEnable, redoEnable);
        }
    }

    @JavascriptInterface
    public final boolean showMarkdown() {
        return RomUtils.isNewTablet();
    }

    @JavascriptInterface
    public final void onSaveData(String newRichTextData) {
        Logger.INSTANCE.d(TAG, "onSaveData");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onSaveData(newRichTextData);
        }
    }

    @JavascriptInterface
    public final void saveLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        Logger logger = Logger.INSTANCE;
        Intrinsics.checkNotNull(log);
        logger.i(TAG, log);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0046  */
    @JavascriptInterface
    public final void onTitleChanged(String title) {
        String string;
        Logger.INSTANCE.d(TAG, "onTitleChanged");
        if (title == null) {
            string = "";
        } else {
            String str = title;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    } else {
                        length--;
                    }
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            string = str.subSequence(i, length + 1).toString();
            if (string == null) {
                string = "";
            }
        }
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onTitleChanged(string);
        }
    }

    @JavascriptInterface
    public final void onChangeToMindMap() {
        Logger.INSTANCE.d(TAG, "onChangeToMindMap");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onChangeToMindMap();
        }
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
    public final void onTextCountChanged(int textCount) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onTextCountChanged(textCount);
        }
    }

    @JavascriptInterface
    public final void onStateChanged(int state) {
        Logger.INSTANCE.d(TAG, "onStateChanged: " + state);
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onStateChanged(state);
        }
    }

    @JavascriptInterface
    public final void onPlayAudioStateChanged(int stateCode) {
        Logger.INSTANCE.d(TAG, "onPlayAudioStateChanged");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onStateChanged(stateCode);
        }
    }

    @JavascriptInterface
    public final void onRichTextPanelStateChanged(boolean isOpen, boolean withAnim) {
        Logger.INSTANCE.d(TAG, "onRichTextPanelStateChanged");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onRichTextPanelStateChanged(isOpen, withAnim);
        }
    }

    @JavascriptInterface
    public final void onEmbedElementClicked(String elementJson) {
        Logger.INSTANCE.d(TAG, "onEmbedElementClicked");
        if (hasReference()) {
            EmbedElementInfoBean embedElementInfoBean = (EmbedElementInfoBean) this.mGson.fromJson(elementJson, EmbedElementInfoBean.class);
            if (embedElementInfoBean.type == 0) {
                BaseHybridNoteEditFragment fragment = getFragment();
                Intrinsics.checkNotNull(fragment);
                Intrinsics.checkNotNull(embedElementInfoBean);
                fragment.onImageElementClicked(embedElementInfoBean);
                return;
            }
            if (embedElementInfoBean.type == 1) {
                BaseHybridNoteEditFragment fragment2 = getFragment();
                Intrinsics.checkNotNull(fragment2);
                Intrinsics.checkNotNull(embedElementInfoBean);
                fragment2.onLinkElementClicked(embedElementInfoBean);
            }
        }
    }

    @JavascriptInterface
    public final void onPopMenuHide() {
        Logger.INSTANCE.d(TAG, "onPopMenuHide");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onPopMenuHide();
        }
    }

    @JavascriptInterface
    public final void onAnnotateImageInDocStart() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocStart");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onAnnotateImageInDocStart();
        }
    }

    @JavascriptInterface
    public final void hidePopMenu() {
        Logger.INSTANCE.d(TAG, "hidePopMenu");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.hidePopMenu();
        }
    }

    @JavascriptInterface
    public final void onAnnotateImageInDocEnd() {
        Logger.INSTANCE.d(TAG, "onAnnotateImageInDocEnd");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onAnnotateImageInDocEnd();
        }
    }

    @JavascriptInterface
    public final void onShareImage(String[] imgBase64) {
        Intrinsics.checkNotNullParameter(imgBase64, "imgBase64");
        Logger.INSTANCE.d(TAG, "onShareImage");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onShareImage(imgBase64);
        }
    }

    @JavascriptInterface
    public final void onDisableScroll(boolean disable) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onDisableScroll(disable);
        }
    }

    @JavascriptInterface
    public final boolean canCreateMindNote() {
        if (!hasReference()) {
            return false;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        return fragment.isSupportCreateMindNote();
    }

    @JavascriptInterface
    public final void onContentOverLength(int overType) {
        Logger.INSTANCE.d(TAG, "onContentOverLength: " + overType);
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onContentOverLength(overType);
        }
    }

    @JavascriptInterface
    public final void onImageAnnotationOverLength() {
        Logger.INSTANCE.d(TAG, "onImageAnnotationOverLength");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onImageAnnotationOverLength();
        }
    }

    @JavascriptInterface
    public final void onStartDragElement(String elementJson) {
        Logger.INSTANCE.d(TAG, "onStartDragElement");
        if (hasReference()) {
            EmbedElementInfoBean embedElementInfoBean = (EmbedElementInfoBean) this.mGson.fromJson(elementJson, EmbedElementInfoBean.class);
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            Intrinsics.checkNotNull(embedElementInfoBean);
            fragment.onStartDragElement(embedElementInfoBean);
        }
    }

    @JavascriptInterface
    public final void performHaptic(int typeId) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.performHaptic(typeId);
        }
    }

    @JavascriptInterface
    public final void onUpdateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String htmlData) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onUpdateViewState(canSelect, canQuery, canPhrase, canTranslate, selectData, htmlData);
        }
    }

    @JavascriptInterface
    public final void doPaste() {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.doPaste();
        }
    }

    @JavascriptInterface
    public final void showToast(String key) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.showToast(key);
        }
    }

    @JavascriptInterface
    public final void onCorrectNumChanged(int changeCount) {
        Logger.INSTANCE.i(TAG, "onCorrectNumChanged: " + changeCount);
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.onCorrectNumChanged(changeCount);
    }

    @JavascriptInterface
    public final void onFlowDone() {
        Logger.INSTANCE.i(TAG, "onFlowDone");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.onFlowDone();
    }

    @JavascriptInterface
    public final void onStopDone() {
        Logger.INSTANCE.i(TAG, "onStopDone");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.onStopDone();
    }

    @JavascriptInterface
    public final void onFlowDone(String data, String data2) {
        Logger.INSTANCE.i(TAG, "onFlowDone2");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.onFlowDone(data, data2);
    }

    @JavascriptInterface
    public final void onFlowDone(String data) {
        Logger.INSTANCE.i(TAG, "onFlowDone1");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.onFlowDone(data);
    }

    @JavascriptInterface
    public final void notifyRefreshComplete() {
        Logger.INSTANCE.i(TAG, "notifyRefreshComplete");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.notifyRefreshComplete();
    }

    @JavascriptInterface
    public final String getViewMode() {
        return "editor";
    }

    @JavascriptInterface
    public final void notifySetNewContent() {
        Logger.INSTANCE.i(TAG, "notifySetNewContent");
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        fragment.notifySetNewContent();
    }

    @JavascriptInterface
    public final void stopAIRecorder() {
        Logger.INSTANCE.i(TAG, "stopAIRecorder");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.stopAIRecord();
        }
    }

    @JavascriptInterface
    public final void showAIRecorderComponent(String audioFileName, boolean isPlaying, int startTime) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.showAIRecorderComponent(audioFileName, isPlaying, startTime);
        }
    }

    @JavascriptInterface
    public final void showAIRecorderRecording() {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.showAIRecorderRecording();
        }
    }

    @JavascriptInterface
    public final void requestAudioDesc(String fileName) {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.getAudioDescription(fileName);
        }
    }

    @JavascriptInterface
    public final boolean usingAIRecorder() {
        if (!hasReference()) {
            return false;
        }
        BaseHybridNoteEditFragment fragment = getFragment();
        Intrinsics.checkNotNull(fragment);
        return fragment.isSupportAIRecorder();
    }

    @JavascriptInterface
    public final boolean panelNeedTaller() {
        Logger.INSTANCE.i(TAG, "note panelNeedTaller");
        return false;
    }

    @JavascriptInterface
    public final void onRefreshDone() {
        Logger.INSTANCE.i(TAG, "onRefreshDone");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.onRefreshDone();
        }
    }

    @JavascriptInterface
    public final boolean preserveAiStatements() {
        BaseHybridNoteEditFragment fragment;
        BaseHybridNoteEditFragment fragment2;
        Logger.INSTANCE.i(TAG, "preserveAiStatements");
        if (com.miui.notes.ai.utils.RomUtils.isInternationalBuild()) {
            return false;
        }
        BaseHybridNoteEditFragment fragment3 = getFragment();
        return !(fragment3 == null || fragment3.isAiCheckType()) || ((fragment = getFragment()) != null && fragment.isAiCheckType() && (fragment2 = getFragment()) != null && fragment2.isAiCorrect());
    }

    @JavascriptInterface
    public final boolean getAccessibilityState() {
        BaseHybridNoteEditFragment fragment;
        WebRichEditor webRichEditor;
        Logger.INSTANCE.i(TAG, "getAccessibilityState");
        if (!hasReference() || (fragment = getFragment()) == null || (webRichEditor = fragment.getMEditor()) == null) {
            return false;
        }
        return webRichEditor.getAccessibilityState();
    }

    @JavascriptInterface
    public final void resetTextStatusForWebView() {
        Logger.INSTANCE.d(TAG, "resetTextStatusForWebView");
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            Intrinsics.checkNotNull(fragment);
            fragment.resetToolBarStaus();
        }
    }

    @JavascriptInterface
    public final boolean isNewNoteFromMiShow() {
        if (hasReference()) {
            BaseHybridNoteEditFragment fragment = getFragment();
            boolean zIsNewNoteFromMiShow = fragment != null ? fragment.isNewNoteFromMiShow() : false;
            Logger.INSTANCE.d(TAG, "isNewNoteFromMiShow: " + zIsNewNoteFromMiShow);
            if (zIsNewNoteFromMiShow) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: HybridEditorScriptInterface.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0007J\u0006\u0010\r\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u000e\u001a\u00020\u000f8G¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010¨\u0006\u0011"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "STATIC_INFOS", "getSTATIC_INFOS", "()Ljava/lang/String;", "setSTATIC_INFOS", "(Ljava/lang/String;)V", "initStaticInfo", "", "updateStaticInfo", "isInternational", "", "()Z", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getSTATIC_INFOS() {
            return HybridEditorScriptInterface.STATIC_INFOS;
        }

        public final void setSTATIC_INFOS(String str) {
            HybridEditorScriptInterface.STATIC_INFOS = str;
        }

        @JvmStatic
        public final void initStaticInfo() {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                StaticInfo.INSTANCE.setINFO(new JSONObject());
                JSONObject info = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info);
                info.put("edit_note_hint_title", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.edit_note_hint_title));
                JSONObject info2 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info2);
                info2.put("word_count", Utils.obtainLocalInteger(0, R.plurals.word_count));
                JSONObject info3 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info3);
                info3.put("image_annotation_edit_hint", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.image_annotation_edit_hint));
                JSONObject info4 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info4);
                info4.put("note_mindnote_entrance_hint_newversion", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.note_mindnote_entrance_hint_newversion));
                JSONObject info5 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info5);
                info5.put("note_mindnote_entrance_icon_text_newversion", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.note_mindnote_entrance_icon_text_newversion));
                JSONObject info6 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info6);
                info6.put("isNoteCreated", true);
                Locale locale = BaseNoteApp.INSTANCE.getInstance().getResources().getConfiguration().getLocales().get(0);
                JSONObject info7 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info7);
                info7.put("locale", locale.getLanguage());
                JSONObject info8 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info8);
                info8.put("countryCode", locale.getCountry());
                JSONObject info9 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info9);
                info9.put("isRtl", Utils.isRTL());
                JSONObject info10 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info10);
                info10.put("device_flag", 0);
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                String str = ((Object) DateFormatUtils.formatDateText(BaseNoteApp.INSTANCE.getInstance(), jCurrentTimeMillis2)) + "  " + ((Object) DateFormatUtils.formatTimeText(BaseNoteApp.INSTANCE.getInstance(), jCurrentTimeMillis2));
                JSONObject info11 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info11);
                info11.put("info_timeString", str);
                JSONObject info12 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info12);
                info12.put("info_time", jCurrentTimeMillis2);
                Logger.INSTANCE.d(HybridEditorScriptInterface.TAG, "initStaticInfo,ts cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
            } catch (Exception e) {
                Logger.INSTANCE.e(HybridEditorScriptInterface.TAG, "initStaticInfo: " + ExceptionsKt.stackTraceToString(e));
            }
        }

        public final void updateStaticInfo() {
            try {
                JSONObject info = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info);
                info.put("edit_note_hint_title", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.edit_note_hint_title));
                JSONObject info2 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info2);
                info2.put("word_count", Utils.obtainLocalInteger(0, R.plurals.word_count));
                JSONObject info3 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info3);
                info3.put("image_annotation_edit_hint", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.image_annotation_edit_hint));
                JSONObject info4 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info4);
                info4.put("note_mindnote_entrance_hint_newversion", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.note_mindnote_entrance_hint_newversion));
                JSONObject info5 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info5);
                info5.put("note_mindnote_entrance_icon_text_newversion", BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.note_mindnote_entrance_icon_text_newversion));
                Locale locale = BaseNoteApp.INSTANCE.getInstance().getResources().getConfiguration().getLocales().get(0);
                JSONObject info6 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info6);
                info6.put("locale", locale.getLanguage());
                JSONObject info7 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info7);
                info7.put("countryCode", locale.getCountry());
                JSONObject info8 = StaticInfo.INSTANCE.getINFO();
                Intrinsics.checkNotNull(info8);
                info8.put("isRtl", Utils.isRTL());
            } catch (Exception e) {
                Logger.INSTANCE.e(HybridEditorScriptInterface.TAG, "updateStaticInfo: " + ExceptionsKt.stackTraceToString(e));
            }
        }

        @JavascriptInterface
        public final boolean isInternational() {
            return RomUtils.isInternationalBuild();
        }
    }
}
