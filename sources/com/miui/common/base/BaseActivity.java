package com.miui.common.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.GlobalStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.LayoutUiModeHelper;

/* JADX INFO: compiled from: BaseActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0014J\u0012\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\b\u0010\u000b\u001a\u00020\u0007H\u0014J\b\u0010\f\u001a\u00020\u0007H\u0014J\b\u0010\r\u001a\u00020\u0007H\u0014J\b\u0010\u000e\u001a\u00020\u0007H\u0014J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/common/base/BaseActivity;", "Lmiuix/appcompat/app/AppCompatActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mHandoffSession", "Lcom/xiaomi/dist/handoff/sdk/HandoffSession;", "onStop", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onStart", "onPause", "onDestroy", "onNewIntent", "intent", "Landroid/content/Intent;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseActivity extends AppCompatActivity {
    protected HandoffSession mHandoffSession;

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onStop");
        GlobalStat.onStop();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseNoteApp.INSTANCE.setMultiWindowMode(isInMultiWindowMode());
        BaseNoteApp.INSTANCE.setFreeFormScreen(UIUtils.isFreeFormScreen(getResources().getConfiguration()));
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onCreate");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onResume");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onStart");
        GlobalStat.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onPause");
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        HandoffSession handoffSession = this.mHandoffSession;
        if (handoffSession != null) {
            Intrinsics.checkNotNull(handoffSession);
            handoffSession.cancel();
        }
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onDestroy");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onNewIntent");
        if (this.mHandoffSession != null) {
            HandoffSession handoffSession = this.mHandoffSession;
            Intrinsics.checkNotNull(handoffSession);
            handoffSession.onNewIntent(intent);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        BaseNoteApp.INSTANCE.setMultiWindowMode(isInMultiWindowMode());
        BaseNoteApp.INSTANCE.setFreeFormScreen(UIUtils.isFreeFormScreen(newConfig));
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onConfigurationChanged");
    }
}
