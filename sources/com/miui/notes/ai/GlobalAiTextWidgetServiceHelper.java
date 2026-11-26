package com.miui.notes.ai;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.floatingwindow.AiFloatingWindow;
import com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow;
import com.miui.notes.ai.utils.ContextWrapperUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlobalAiTextWidgetServiceHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000  2\u00020\u0001:\u0001 B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\"\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0011H\u0016JJ\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u00112\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u0002R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006!"}, d2 = {"Lcom/miui/notes/ai/GlobalAiTextWidgetServiceHelper;", "Lcom/miui/notes/ai/AiTextWidgetServiceHelper;", NotificationCompat.CATEGORY_SERVICE, "Landroid/app/Service;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Service;)V", "mGlobalAiTextWidgetWindow", "Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow;", "getMGlobalAiTextWidgetWindow", "()Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow;", "setMGlobalAiTextWidgetWindow", "(Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow;)V", "showAiTextWidgetServiceHelper", "", "hasPassPermissionCheck", "", "onStartCommand", "", "intent", "Landroid/content/Intent;", "flags", "startId", "startFloatWindow", GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, "", "packageName", "panelType", "styLe", "wordNum", "emoticonsNum", "comment", "aiTextWidgetServiceHelper", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextWidgetServiceHelper extends AiTextWidgetServiceHelper {
    public static final String KEY_COMMAND_METHOD = "key_command_method";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_EMOTICONS_NUM = "emoticonsNums";
    public static final String KEY_PACKAGE_NAME = "packageName";
    public static final String KEY_PAGE_TYPE = "pageType";
    public static final String KEY_QUERY_ID = "queryID";
    public static final String KEY_STYLE = "style";
    public static final String KEY_TITLE = "title";
    public static final String KEY_WORD_NUM = "wordNums";
    public static final String TAG = "GlobalAiTextWidgetServiceHelper";
    private GlobalAiTextWidgetWindow mGlobalAiTextWidgetWindow;

    public GlobalAiTextWidgetServiceHelper(Service service) {
        super(service);
    }

    public final GlobalAiTextWidgetWindow getMGlobalAiTextWidgetWindow() {
        return this.mGlobalAiTextWidgetWindow;
    }

    public final void setMGlobalAiTextWidgetWindow(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        this.mGlobalAiTextWidgetWindow = globalAiTextWidgetWindow;
    }

    @Override // com.miui.notes.ai.AiTextWidgetServiceHelper
    public void showAiTextWidgetServiceHelper(boolean hasPassPermissionCheck) {
        this.mHasPassPermissionCheck = hasPassPermissionCheck;
        this.wrapperContext = ContextWrapperUtil.wrapperWindowContext(this.mAiTextWidgetService);
        Context wrapperContext = this.wrapperContext;
        Intrinsics.checkNotNullExpressionValue(wrapperContext, "wrapperContext");
        Service service = this.mAiTextWidgetService;
        Intrinsics.checkNotNull(service, "null cannot be cast to non-null type com.miui.notes.ai.GlobalAiTextWidgetService");
        this.mGlobalAiTextWidgetWindow = new GlobalAiTextWidgetWindow(wrapperContext, (GlobalAiTextWidgetService) service);
    }

    @Override // com.miui.notes.ai.AiTextWidgetServiceHelper
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent != null ? intent.getExtras() : null;
        if (extras == null) {
            return 2;
        }
        String string = extras.getString(KEY_QUERY_ID);
        String string2 = extras.getString("packageName");
        String string3 = extras.getString("pageType");
        int i = extras.getInt("style");
        int i2 = extras.getInt("wordNums");
        int i3 = extras.getInt("emoticonsNums");
        int i4 = extras.getInt("comment");
        Log.d(TAG, "onStartCommand: queryID " + string + ",  packageName " + string2 + ", panelType " + string3 + ", style " + i + ", wordNum " + i2 + ", emoticonsNum " + i3 + ", comment " + i4);
        if (string == null || string2 == null || string3 == null) {
            return 2;
        }
        startFloatWindow(string, string2, string3, i, i2, i3, i4, this);
        return 2;
    }

    private final void startFloatWindow(String queryID, String packageName, String panelType, int styLe, int wordNum, int emoticonsNum, int comment, AiTextWidgetServiceHelper aiTextWidgetServiceHelper) {
        GlobalAiTextWidgetWindow globalAiTextWidgetWindow;
        GlobalAiTextWidgetWindow globalAiTextWidgetWindow2;
        if (aiTextWidgetServiceHelper != null && (globalAiTextWidgetWindow2 = this.mGlobalAiTextWidgetWindow) != null) {
            globalAiTextWidgetWindow2.setWindowCloseListener(new AiFloatingWindow.WindowCloseListener() { // from class: com.miui.notes.ai.GlobalAiTextWidgetServiceHelper$$ExternalSyntheticLambda0
                @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow.WindowCloseListener
                public final void onWindowClosed() {
                    this.f$0.stopService();
                }
            });
        }
        if (Intrinsics.areEqual(panelType, "copy_publish")) {
            GlobalAiTextWidgetWindow globalAiTextWidgetWindow3 = this.mGlobalAiTextWidgetWindow;
            if (globalAiTextWidgetWindow3 != null) {
                globalAiTextWidgetWindow3.showAiFloatingWindow(queryID, packageName, panelType, styLe, wordNum, emoticonsNum);
                return;
            }
            return;
        }
        if (!Intrinsics.areEqual(panelType, GlobalAiTextWidgetWindow.TYPE_PRODUCT_COMMENT) || (globalAiTextWidgetWindow = this.mGlobalAiTextWidgetWindow) == null) {
            return;
        }
        globalAiTextWidgetWindow.showAiFloatingWindow(queryID, packageName, panelType, comment, wordNum);
    }
}
