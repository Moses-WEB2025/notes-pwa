package com.miui.notes.ai;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import androidx.hardware.DataSpace;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.account.AccountListenerHolder;
import com.miui.notes.ai.cta.CTATransparentActivity;
import com.miui.notes.ai.cta.CtaListenerHolder;
import com.miui.notes.ai.cta.CtaResultListener;
import com.miui.notes.ai.cta.GlobalPermissionActivity;
import com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlobalAiTextWidgetService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\"\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H\u0016J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\b\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0005H\u0002J\b\u0010\u0012\u001a\u00020\u0005H\u0014¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/GlobalAiTextWidgetService;", "Lcom/miui/notes/ai/AiTextWidgetService;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", "onStartCommand", "", "intent", "Landroid/content/Intent;", "flags", "startId", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onDestroy", "initAiTextWidgetServiceHelper", "editGuide", "registerReceiver", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextWidgetService extends AiTextWidgetService {
    public static final String TAG = "GlobalAiTextWidgetService";

    @Override // com.miui.notes.ai.AiTextWidgetService
    protected void registerReceiver() {
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.d(TAG, "onStartCommand: ");
        if ((intent != null ? intent.getExtras() : null) == null) {
            return 2;
        }
        editGuide();
        GlobalAiTextWidgetService globalAiTextWidgetService = this;
        AiTextWidgetService.mIsNightMode = DisplayUtil.isNightMode(globalAiTextWidgetService);
        if (hasPassCTA()) {
            this.mAiTextWidgetServiceHelper.onStartCommand(intent, flags, startId);
            return 2;
        }
        CtaListenerHolder.setInstance(new CtaResultListener() { // from class: com.miui.notes.ai.GlobalAiTextWidgetService$onStartCommand$1$1
            @Override // com.miui.notes.ai.cta.CtaResultListener
            public void agree() {
                this.this$0.mAiTextWidgetServiceHelper.onStartCommand(intent, flags, startId);
            }

            @Override // com.miui.notes.ai.cta.CtaResultListener
            public void reject() {
                this.this$0.mAiTextWidgetServiceHelper.stopService();
            }
        });
        if (!RomUtils.isInternationalBuild()) {
            Log.i(TAG, "CTATransparentActivity__ enter..");
            Intent intent2 = new Intent(globalAiTextWidgetService, (Class<?>) CTATransparentActivity.class);
            intent2.addFlags(DataSpace.RANGE_LIMITED);
            startActivity(intent2);
            return 2;
        }
        Intent intent3 = new Intent(globalAiTextWidgetService, (Class<?>) GlobalPermissionActivity.class);
        intent3.addFlags(DataSpace.RANGE_LIMITED);
        startActivity(intent3);
        return 2;
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        boolean zIsNightMode = DisplayUtil.isNightMode(this);
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper;
        Intrinsics.checkNotNull(aiTextWidgetServiceHelper, "null cannot be cast to non-null type com.miui.notes.ai.GlobalAiTextWidgetServiceHelper");
        GlobalAiTextWidgetServiceHelper globalAiTextWidgetServiceHelper = (GlobalAiTextWidgetServiceHelper) aiTextWidgetServiceHelper;
        if (AiTextWidgetService.mIsNightMode == zIsNightMode || globalAiTextWidgetServiceHelper.getMGlobalAiTextWidgetWindow() == null) {
            return;
        }
        Log.e(TAG, "onConfigurationChanged");
        GlobalAiTextWidgetWindow mGlobalAiTextWidgetWindow = globalAiTextWidgetServiceHelper.getMGlobalAiTextWidgetWindow();
        if (mGlobalAiTextWidgetWindow != null) {
            mGlobalAiTextWidgetWindow.dismissWindow();
        }
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        AccountListenerHolder.setInstance(null);
        CtaListenerHolder.setInstance(null);
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper;
        if (aiTextWidgetServiceHelper != null) {
            aiTextWidgetServiceHelper.onDestroy();
        }
    }

    @Override // com.miui.notes.ai.AiTextWidgetService
    public void initAiTextWidgetServiceHelper() {
        if (this.mAiTextWidgetServiceHelper == null) {
            this.mAiTextWidgetServiceHelper = new GlobalAiTextWidgetServiceHelper(this);
        }
    }

    private final void editGuide() {
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper;
        if (aiTextWidgetServiceHelper != null) {
            aiTextWidgetServiceHelper.showAiTextWidgetServiceHelper(true);
        }
    }
}
