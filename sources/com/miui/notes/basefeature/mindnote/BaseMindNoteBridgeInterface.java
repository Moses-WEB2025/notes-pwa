package com.miui.notes.basefeature.mindnote;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import com.google.gson.JsonParser;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.AppGlobal;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.c.j;
import com.xiaomi.verificationsdk.internal.Constants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;

/* JADX INFO: compiled from: BaseMindNoteBridgeInterface.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b6\n\u0002\u0010%\n\u0002\b\u0004\b\u0016\u0018\u0000 `*\n\b\u0000\u0010\u0001*\u0004\u0018\u00010\u00022\u00020\u0003:\u0002_`B\u0011\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0005\u0010\u0006J\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u000b\u001a\u00020\fH\u0004J\b\u0010\u0010\u001a\u00020\nH\u0007J\b\u0010\u0011\u001a\u00020\nH\u0007J\u0012\u0010\u0016\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0007J&\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0007J&\u0010\u001b\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0007J&\u0010\u0016\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0007J&\u0010\u001c\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010\u001d\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0013H\u0007J\b\u0010\u001f\u001a\u00020\nH\u0007J\b\u0010 \u001a\u00020\nH\u0007J\b\u0010!\u001a\u00020\nH\u0007J\u0012\u0010*\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010,\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010-\u001a\u00020\n2\b\u0010.\u001a\u0004\u0018\u00010\u0013H\u0007J\b\u0010/\u001a\u00020\nH\u0007J\u0012\u00100\u001a\u00020\n2\b\u00101\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u00102\u001a\u00020\n2\b\u00103\u001a\u0004\u0018\u00010\u0013H\u0007J\b\u00104\u001a\u00020\nH\u0007J\b\u00105\u001a\u00020\nH\u0007J\u0012\u00106\u001a\u00020\n2\b\u0010.\u001a\u0004\u0018\u00010\u0013H\u0007J\n\u00107\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u00108\u001a\u00020\n2\b\u00109\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010:\u001a\u00020\n2\b\u00109\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010;\u001a\u00020\n2\b\u00109\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010@\u001a\u00020\n2\b\u0010.\u001a\u0004\u0018\u00010\u0013H\u0007J\b\u0010@\u001a\u00020\nH\u0007J\b\u0010B\u001a\u00020\nH\u0007J\b\u0010C\u001a\u00020\nH\u0007J\b\u0010D\u001a\u00020\nH\u0007J\b\u0010F\u001a\u00020\nH\u0007J\b\u0010G\u001a\u00020\nH\u0007J\b\u0010H\u001a\u00020\fH\u0007J\u0012\u0010I\u001a\u00020\n2\b\u0010J\u001a\u0004\u0018\u00010\u0013H\u0007J\u0014\u0010M\u001a\u0004\u0018\u00010\u00132\b\u0010N\u001a\u0004\u0018\u00010\u0013H\u0007J<\u0010O\u001a\u00020\n2\u0006\u0010P\u001a\u00020\f2\u0006\u0010Q\u001a\u00020\f2\u0006\u0010R\u001a\u00020\f2\u0006\u0010S\u001a\u00020\f2\b\u0010T\u001a\u0004\u0018\u00010\u00132\b\u0010U\u001a\u0004\u0018\u00010\u0013H\u0007J\u0010\u0010V\u001a\u00020\u00132\u0006\u0010W\u001a\u00020\u0013H\u0007J\u0018\u0010X\u001a\u00020\u00132\u0006\u0010Y\u001a\u00020&2\u0006\u0010W\u001a\u00020\u0013H\u0007J\u0012\u0010Z\u001a\u00020\n2\b\u0010[\u001a\u0004\u0018\u00010\u0013H\u0007J\u001e\u0010\\\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00030]2\u0006\u0010^\u001a\u00020\u0013R\u0018\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\r\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\u00138G¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\"\u001a\u00020\f8G¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020\f8G¢\u0006\u0006\u001a\u0004\b$\u0010#R\u0014\u0010%\u001a\u00020&8WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0011\u0010)\u001a\u00020\f8G¢\u0006\u0006\u001a\u0004\b)\u0010#R\u0011\u0010+\u001a\u00020\f8G¢\u0006\u0006\u001a\u0004\b+\u0010#R\u0011\u0010<\u001a\u00020&8G¢\u0006\u0006\u001a\u0004\b=\u0010(R\u0011\u0010>\u001a\u00020&8G¢\u0006\u0006\u001a\u0004\b?\u0010(R\u0013\u0010A\u001a\u0004\u0018\u00010\u00138G¢\u0006\u0006\u001a\u0004\bA\u0010\u0015R\u0011\u0010E\u001a\u00020\f8G¢\u0006\u0006\u001a\u0004\bE\u0010#R\u0013\u0010K\u001a\u0004\u0018\u00010\u00138G¢\u0006\u0006\u001a\u0004\bL\u0010\u0015¨\u0006a"}, d2 = {"Lcom/miui/notes/basefeature/mindnote/BaseMindNoteBridgeInterface;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/miui/notes/basefeature/mindnote/BaseWebMindNoteFragment;", "", "fragment", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/mindnote/BaseWebMindNoteFragment;)V", "mFragment", "Ljava/lang/ref/WeakReference;", "releaseReference", "", "hasReference", "", "baseFragment", "getBaseFragment", "()Lcom/miui/notes/basefeature/mindnote/BaseWebMindNoteFragment;", "onPagePrepare", "onDataPrepare", "localeInfo", "", "getLocaleInfo", "()Ljava/lang/String;", "trackEvent", j.b, "trackEventClick", "tips", "paramsJson", "trackEventOperate", "trackEventEnter", "savePicToGallery", "base64", "startSave", "stopShowDialog", CallMethod.METHOD_START_SHARE, "isSynergyMode", "()Z", "isLandScape", "uiMode", "", "getUiMode", "()I", "isMobile", "shareMind", "isFirstLoad", "setThumbnail", "setData", "jsonObject", "moveMindNoteFolder", "showToast", "message", "switchView", "currentPage", "deleteMindNote", "exitWithoutSaveData", "setInstantDataOnPause", "setTitle", "setInstantData", "dataJson", "onSaveInstantData", "saveEmptyDataOnSwitchView", "type", "getType", "editMarginTopInOverViewMode", "getEditMarginTopInOverViewMode", "onBackPressed", "isNightMode", "onViewMode", "onEditMode", "backToNormalNote", "isFirstEnterMind", "setHasEnterMind", "sendToDesktop", "showSendToDeskTop", "onLog", "logInfo", "createTime", "getCreateTime", "getModifiedTime", "modifiedTime", "onUpdateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "contextData", "getStringFromResource", "key", "getNumberAndStringFromResource", "number", "saveLog", "log", "jsonToMap", "", "json", "UiMode", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseMindNoteBridgeInterface<T extends BaseWebMindNoteFragment> {
    public static final String TAG = "BaseMindNoteBridgeInterface";
    private WeakReference<T> mFragment;

    public BaseMindNoteBridgeInterface(T t) {
        this.mFragment = new WeakReference<>(t);
    }

    public final void releaseReference() {
        this.mFragment = null;
    }

    protected final boolean hasReference() {
        WeakReference<T> weakReference = this.mFragment;
        if (weakReference == null) {
            return false;
        }
        Intrinsics.checkNotNull(weakReference);
        return weakReference.get() != null;
    }

    public final T getBaseFragment() {
        WeakReference<T> weakReference = this.mFragment;
        Intrinsics.checkNotNull(weakReference);
        return weakReference.get();
    }

    @JavascriptInterface
    public final void onPagePrepare() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onPagePrepare();
        }
    }

    @JavascriptInterface
    public final void onDataPrepare() {
        if (hasReference()) {
            Logger.INSTANCE.d(TAG, "onDataPrepare");
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onDataPrepare();
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

    @Deprecated(message = "")
    @JavascriptInterface
    public final void trackEvent(String eventName) {
        hasReference();
    }

    @JavascriptInterface
    public final void trackEventClick(String tips, String eventName, String paramsJson) {
        hasReference();
    }

    @JavascriptInterface
    public final void trackEventOperate(String tips, String eventName, String paramsJson) {
        hasReference();
    }

    @JavascriptInterface
    public final void trackEvent(String eventName, String tips, String paramsJson) {
        hasReference();
    }

    @JavascriptInterface
    public final void trackEventEnter(String eventName, String tips, String paramsJson) {
        hasReference();
    }

    @JavascriptInterface
    public final void savePicToGallery(String base64) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.checkPermissionSavePicToGallery(base64);
        }
    }

    @JavascriptInterface
    public final void startSave() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.showSaveToGalleryDialog();
        }
    }

    @JavascriptInterface
    public final void stopShowDialog() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.dismissProgressDialog();
        }
    }

    @JavascriptInterface
    public final void startShare() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.showShareDialog();
        }
    }

    @JavascriptInterface
    public final boolean isSynergyMode() {
        try {
            int i = Settings.Secure.getInt(AppGlobal.getContentResolver(), "synergy_mode", -1);
            Logger.INSTANCE.d(TAG, "isSynergyMode " + i);
            return i == 1;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "isSynergyMode error " + e.getMessage());
            return false;
        }
    }

    @JavascriptInterface
    public final boolean isLandScape() {
        if (!hasReference()) {
            return false;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.isLandScape();
    }

    @JavascriptInterface
    public int getUiMode() {
        if (!hasReference()) {
            return 0;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getUiMode();
    }

    @JavascriptInterface
    public final boolean isMobile() {
        if (!hasReference()) {
            return true;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.isMobile();
    }

    @JavascriptInterface
    public final void shareMind(final String base64) {
        BaseWebMindNoteFragment baseFragment;
        View view;
        if (!hasReference() || (baseFragment = getBaseFragment()) == null || (view = baseFragment.getView()) == null) {
            return;
        }
        view.post(new Runnable(this) { // from class: com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface.shareMind.1
            final /* synthetic */ BaseMindNoteBridgeInterface<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                BaseWebMindNoteFragment baseFragment2 = this.this$0.getBaseFragment();
                if (baseFragment2 != null) {
                    baseFragment2.shareMind(base64);
                }
            }
        });
    }

    @JavascriptInterface
    public final boolean isFirstLoad() {
        if (!hasReference()) {
            return false;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.isFirstLoad();
    }

    @JavascriptInterface
    public final void setThumbnail(String base64) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.setThumbnail(base64);
        }
    }

    @JavascriptInterface
    public final void setData(String jsonObject) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.asyncSaveNote(jsonObject);
        }
    }

    @JavascriptInterface
    public final void moveMindNoteFolder() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.startQueryFolder();
        }
    }

    @JavascriptInterface
    public final void showToast(String message) {
        if (hasReference()) {
            Logger.INSTANCE.d(TAG, "showToast: " + message);
            WeakReference<T> weakReference = this.mFragment;
            Intrinsics.checkNotNull(weakReference);
            T t = weakReference.get();
            Intrinsics.checkNotNull(t);
            Toast.makeText(t.getActivity(), message, 0).show();
        }
    }

    @JavascriptInterface
    public final void switchView(String currentPage) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.disableLandscape(currentPage);
        }
    }

    @JavascriptInterface
    public final void deleteMindNote() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.requestDeleteNote();
        }
    }

    @JavascriptInterface
    public final void exitWithoutSaveData() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.exitWithoutSave();
        }
    }

    @JavascriptInterface
    public final void setInstantDataOnPause(String jsonObject) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.asyncSaveNoteInstantlyOnPause(jsonObject);
        }
    }

    @JavascriptInterface
    public final String setTitle() {
        if (!hasReference()) {
            return "";
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getTitle();
    }

    @JavascriptInterface
    public final void setInstantData(String dataJson) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.asyncSaveNoteInstantlyIfNeeded(dataJson);
        }
    }

    @JavascriptInterface
    public final void onSaveInstantData(String dataJson) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.asyncSaveNoteInstantlyOnPause(dataJson);
        }
    }

    @JavascriptInterface
    public final void saveEmptyDataOnSwitchView(String dataJson) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.updateWorkingEmptyData(dataJson);
        }
    }

    @JavascriptInterface
    public final int getType() {
        if (!hasReference()) {
            return 0;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getEditMode();
    }

    @JavascriptInterface
    public final int getEditMarginTopInOverViewMode() {
        if (!hasReference()) {
            return 0;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getEditMarginTopInOverViewMode();
    }

    @JavascriptInterface
    public final void onBackPressed(String jsonObject) {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onBackPressed");
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.updateWorkingText(jsonObject);
            BaseWebMindNoteFragment baseFragment2 = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment2);
            baseFragment2.asyncSaveNoteInstantlyIfNeeded(jsonObject);
        }
    }

    @JavascriptInterface
    public final void onBackPressed() {
        BaseWebMindNoteFragment baseFragment;
        FragmentActivity fragmentActivityRequireActivity;
        if (!hasReference() || (baseFragment = getBaseFragment()) == null || (fragmentActivityRequireActivity = baseFragment.requireActivity()) == null) {
            return;
        }
        fragmentActivityRequireActivity.onBackPressed();
    }

    @JavascriptInterface
    public final String isNightMode() {
        if (!hasReference()) {
            Logger.INSTANCE.d(TAG, "activity == null");
            return Constants.LIGHT;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getNightMode();
    }

    @JavascriptInterface
    public final void onViewMode() {
        if (hasReference()) {
            Logger.INSTANCE.d(TAG, "onViewMode");
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onViewMode();
        }
    }

    @JavascriptInterface
    public final void onEditMode() {
        if (hasReference()) {
            Logger.INSTANCE.d(TAG, "onEditMode");
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onEditMode();
        }
    }

    @JavascriptInterface
    public final void backToNormalNote() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onBackToNormalNote();
        }
    }

    @JavascriptInterface
    public final boolean isFirstEnterMind() {
        if (!hasReference()) {
            return false;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.isFirstEnterMind();
    }

    @JavascriptInterface
    public final void setHasEnterMind() {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.setHasEnterMind();
        }
    }

    @JavascriptInterface
    public final void sendToDesktop() {
        BaseWebMindNoteFragment baseFragment;
        View view;
        if (!hasReference() || (baseFragment = getBaseFragment()) == null || (view = baseFragment.getView()) == null) {
            return;
        }
        view.post(new Runnable(this) { // from class: com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface.sendToDesktop.1
            final /* synthetic */ BaseMindNoteBridgeInterface<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                BaseWebMindNoteFragment baseFragment2;
                if (this.this$0.hasReference() && (baseFragment2 = this.this$0.getBaseFragment()) != null) {
                    baseFragment2.sendToDesktop();
                }
            }
        });
    }

    @JavascriptInterface
    public final boolean showSendToDeskTop() {
        if (!hasReference()) {
            return false;
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.showSendToDesktop();
    }

    @JavascriptInterface
    public final void onLog(String logInfo) {
        if (hasReference()) {
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onLog(logInfo);
        }
    }

    @JavascriptInterface
    public final String getCreateTime() {
        if (!hasReference()) {
            return "";
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getCreateTime();
    }

    @JavascriptInterface
    public final String getModifiedTime(String modifiedTime) {
        if (!hasReference() || TextUtils.isEmpty(modifiedTime)) {
            return "";
        }
        BaseWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getModifiedTime(modifiedTime);
    }

    @JavascriptInterface
    public final void onUpdateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String contextData) {
        boolean z;
        boolean z2;
        if (hasReference()) {
            if (selectData == null || selectData.length() <= 0) {
                z = canQuery;
                z2 = canTranslate;
            } else {
                z = true;
                z2 = true;
            }
            BaseWebMindNoteFragment baseFragment = getBaseFragment();
            Intrinsics.checkNotNull(baseFragment);
            baseFragment.onUpdateViewState(canSelect, z, canPhrase, z2, selectData, contextData);
        }
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
    public final void saveLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        Logger logger = Logger.INSTANCE;
        Intrinsics.checkNotNull(log);
        logger.i(TAG, log);
    }

    public final Map<String, Object> jsonToMap(String json) {
        List listEmptyList;
        List listEmptyList2;
        Intrinsics.checkNotNullParameter(json, "json");
        JsonParser.parseString(json).getAsJsonObject();
        HashMap map = new HashMap();
        List<String> listSplit = new Regex(",").split(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(json, "{", "", false, 4, (Object) null), StringSubstitutor.DEFAULT_VAR_END, "", false, 4, (Object) null), "\"", "", false, 4, (Object) null), 0);
        if (!listSplit.isEmpty()) {
            ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
            break;
        }
        for (String str : (String[]) listEmptyList.toArray(new String[0])) {
            List<String> listSplit2 = new Regex(":").split(str, 0);
            if (!listSplit2.isEmpty()) {
                ListIterator<String> listIterator2 = listSplit2.listIterator(listSplit2.size());
                while (true) {
                    if (listIterator2.hasPrevious()) {
                        if (listIterator2.previous().length() != 0) {
                            listEmptyList2 = CollectionsKt.take(listSplit2, listIterator2.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList2 = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList2 = CollectionsKt.emptyList();
                break;
                break;
            }
            String[] strArr = (String[]) listEmptyList2.toArray(new String[0]);
            map.put(strArr[0], strArr[1]);
        }
        return map;
    }

    /* JADX INFO: compiled from: BaseMindNoteBridgeInterface.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002¨\u0006\u0003À\u0006\u0003"}, d2 = {"Lcom/miui/notes/basefeature/mindnote/BaseMindNoteBridgeInterface$UiMode;", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface UiMode {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;
        public static final int MIUI_PLUS = 2;
        public static final int PAD = 1;
        public static final int PHONE = 0;

        /* JADX INFO: compiled from: BaseMindNoteBridgeInterface.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/miui/notes/basefeature/mindnote/BaseMindNoteBridgeInterface$UiMode$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PHONE", "", "PAD", "MIUI_PLUS", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();
            public static final int MIUI_PLUS = 2;
            public static final int PAD = 1;
            public static final int PHONE = 0;

            private Companion() {
            }
        }
    }
}
