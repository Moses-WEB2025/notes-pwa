package com.miui.notes.ai;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.core.util.Pair;
import androidx.hardware.DataSpace;
import com.miui.ai.text.widget.AiLocalDownloadActivity;
import com.miui.ai.text.widget.R;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.notes.ai.account.AccountListenerHolder;
import com.miui.notes.ai.account.AccountResultListener;
import com.miui.notes.ai.account.AccountTransparentActivity;
import com.miui.notes.ai.account.ExtraAccountManager;
import com.miui.notes.ai.cta.CTATransparentActivity;
import com.miui.notes.ai.cta.CtaListenerHolder;
import com.miui.notes.ai.cta.CtaResultListener;
import com.miui.notes.ai.cta.GlobalPermissionActivity;
import com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow;
import com.miui.notes.ai.internaltest.InteralTestInviteActivity;
import com.miui.notes.ai.overlay.OverlayPermissionActivity;
import com.miui.notes.ai.utils.ContextWrapperUtil;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.NotificationUtils;
import com.miui.notes.ai.utils.PermissionUtils;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class AiTextWidgetService extends Service {
    private static final String ACTION_CLOSE_AI_Floating_WINDOW = "com.miui.note.action.close_ai_floating_window";
    private static final String TAG = "AiTextWidgetService";
    private static String mHostPackageName = null;
    protected static boolean mIsNightMode = false;
    protected AiTextWidgetServiceHelper mAiTextWidgetServiceHelper;
    private volatile String mSelectedText = null;
    private OnScreenChangeListener onScreenChangeListener = null;
    private final BroadcastReceiver mAiReceiver = new BroadcastReceiver() { // from class: com.miui.notes.ai.AiTextWidgetService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logger.INSTANCE.i(AiTextWidgetService.TAG, "mAiReceiver intent:" + intent.getAction());
            if (intent.getAction().equals(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE) && !PreferenceUtils.isInternalTestInviteAccepted(context)) {
                if (intent.getBooleanExtra(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE_KEY, false)) {
                    PreferenceUtils.setInternalTestInviteAccepted(AiTextWidgetService.this.getApplicationContext(), true);
                    if (!AiTextWidgetService.this.isXiaomiAccountLogin()) {
                        AiTextWidgetService.this.tryLoginXiaomiAccount();
                        return;
                    }
                    if (AiTextWidgetService.this.hasOverlayPermission()) {
                        if (TextUtils.isEmpty(AiTextWidgetService.mHostPackageName) || AiTextWidgetService.mHostPackageName.equals("com.miui.notes")) {
                            return;
                        }
                        if (TextUtils.isEmpty(AiTextWidgetService.this.mSelectedText) && PreferenceUtils.isAcceptedMode(AiTextWidgetService.this) && !RomUtils.isSupportLLMCotAI(AiTextWidgetService.this)) {
                            AiTextWidgetService aiTextWidgetService = AiTextWidgetService.this;
                            Toast.makeText(aiTextWidgetService, aiTextWidgetService.getString(R.string.select_text_before_using), 0).show();
                            return;
                        } else {
                            AiTextWidgetService.this.mAiTextWidgetServiceHelper.startFloatWindowFromCTA();
                            return;
                        }
                    }
                    AiTextWidgetService.this.tryApplyOverlayPermission();
                    return;
                }
                return;
            }
            if (!AiTextWidgetService.ACTION_CLOSE_AI_Floating_WINDOW.equals(intent.getAction()) || AiTextWidgetService.this.mAiTextWidgetServiceHelper == null) {
                return;
            }
            AiTextWidgetService.this.mAiTextWidgetServiceHelper.closeAiFloatingWindow();
        }
    };

    public interface OnScreenChangeListener {
        void onConfigChange();

        void setOnScreenChange(boolean z);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Logger.INSTANCE.d(TAG, "onCreate");
        Pair<Integer, Notification> notification = NotificationUtils.getNotification(getApplicationContext());
        startForeground(notification.first.intValue(), notification.second);
        initAiTextWidgetServiceHelper();
        registerReceiver();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (!isCallerTrusted()) {
            Logger.INSTANCE.e(TAG, "onStartCommand blocked: untrusted caller uid=" + Binder.getCallingUid());
            stopSelf(i2);
            return 2;
        }
        boolean zIsGlobalScreenType = isGlobalScreenType();
        Log.i(TAG, "isGlobalPanel=" + zIsGlobalScreenType);
        if (zIsGlobalScreenType) {
            startGlobal();
            return 2;
        }
        if (intent != null && intent.getExtras() != null) {
            Logger.INSTANCE.d(TAG, "onStartCommand: ");
            Bundle extras = intent.getExtras();
            String string = extras.getString("packageName");
            String string2 = extras.getString("selectedText");
            mHostPackageName = string;
            this.mSelectedText = string2;
            if (!RomUtils.isSupportLocalAI(this)) {
                Logger.INSTANCE.d(TAG, "onStartCommand cloudLLMConfig false");
                PreferenceUtils.setModelSwitch(this, false);
            }
            editGuide(string);
            mIsNightMode = DisplayUtil.isNightMode(this);
            AiTextWidgetServiceHelper aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper;
            if (aiTextWidgetServiceHelper != null) {
                return aiTextWidgetServiceHelper.onStartCommand(intent, i, i2);
            }
        }
        return 2;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.INSTANCE.i(TAG, " AiTextWidgetService destroy");
        AccountListenerHolder.setInstance(null);
        CtaListenerHolder.setInstance(null);
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper;
        if (aiTextWidgetServiceHelper != null) {
            aiTextWidgetServiceHelper.onDestroy();
        }
        getApplicationContext().unregisterReceiver(this.mAiReceiver);
    }

    public void initAiTextWidgetServiceHelper() {
        if (this.mAiTextWidgetServiceHelper == null) {
            this.mAiTextWidgetServiceHelper = new AiTextWidgetServiceHelper(this);
        }
    }

    private boolean isGlobalScreenType() {
        Uri uriBuild = new Uri.Builder().scheme("content").authority("com.hyperos.agent.runtime.AgentProvider").path("CreationAgent").build();
        ContentResolver contentResolver = getContentResolver();
        Bundle bundle = new Bundle();
        bundle.putString("key_request_method", "request_get_screen_type");
        try {
            Bundle bundleCall = contentResolver.call(uriBuild, "method_request", uriBuild.toString(), bundle);
            if (bundleCall != null) {
                String string = bundleCall.getString("screenType");
                Log.i(TAG, "isGlobalPanel,screenType=" + string);
                return TextUtils.equals(string, "copy_publish") || TextUtils.equals(string, GlobalAiTextWidgetWindow.TYPE_PRODUCT_COMMENT);
            }
        } catch (Exception unused) {
            Logger.INSTANCE.e(TAG, "request_get_screen_type exception.");
        }
        return false;
    }

    private void startGlobal() {
        Uri uriBuild = new Uri.Builder().scheme("content").authority("com.hyperos.agent.runtime.AgentProvider").path("CreationAgent").build();
        ContentResolver contentResolver = getContentResolver();
        Bundle bundle = new Bundle();
        bundle.putString(GlobalAiTextWidgetServiceHelper.KEY_COMMAND_METHOD, "start_notes");
        try {
            contentResolver.call(uriBuild, "method_command", uriBuild.toString(), bundle);
        } catch (Exception e) {
            Log.e(TAG, "isGlobalPanel Exception:" + e.getMessage());
        }
    }

    protected boolean hasPassCTA() {
        return PreferenceUtils.isCTAAccepted(this) || !PermissionUtils.supportNewPermissionStyle(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isXiaomiAccountLogin() {
        return ExtraAccountManager.isLogin(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasOverlayPermission() {
        return PermissionUtils.hasOverlayPermission(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryLoginXiaomiAccount() {
        AccountListenerHolder.setInstance(new AccountResultListener() { // from class: com.miui.notes.ai.AiTextWidgetService.1
            @Override // com.miui.notes.ai.account.AccountResultListener
            public void loginSuccess() {
                if (AiTextWidgetService.this.hasOverlayPermission()) {
                    return;
                }
                AiTextWidgetService.this.tryApplyOverlayPermission();
            }

            @Override // com.miui.notes.ai.account.AccountResultListener
            public void cancel() {
                AiTextWidgetService.this.mAiTextWidgetServiceHelper.stopService();
            }
        });
        Intent intent = new Intent(this, (Class<?>) AccountTransparentActivity.class);
        intent.addFlags(DataSpace.RANGE_LIMITED);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryApplyOverlayPermission() {
        Intent intent = new Intent(this, (Class<?>) OverlayPermissionActivity.class);
        intent.addFlags(DataSpace.RANGE_LIMITED);
        startActivity(intent);
        this.mAiTextWidgetServiceHelper.stopService();
    }

    private void editGuide(final String str) {
        Logger.INSTANCE.d(TAG, "editGuide");
        if (hasPassCTA() && ((PreferenceUtils.isInternalTestInviteAccepted(this) || PreferenceUtils.isInternalTestInviteAcceptedForSpecial(this)) && isXiaomiAccountLogin() && hasOverlayPermission() && !isNeedDownloadLocalAi())) {
            if (!str.equals("com.miui.notes") && TextUtils.isEmpty(this.mSelectedText) && PreferenceUtils.isAcceptedMode(this) && !RomUtils.isSupportLLMCotAI(this)) {
                Toast.makeText(this, getString(R.string.select_text_before_using), 0).show();
                return;
            } else {
                Logger.INSTANCE.i(TAG, "can use AI Text widget");
                this.mAiTextWidgetServiceHelper.showAiTextWidgetServiceHelper(true);
                return;
            }
        }
        this.mAiTextWidgetServiceHelper.showAiTextWidgetServiceHelper(false);
        if (!hasPassCTA()) {
            CtaListenerHolder.setInstance(new CtaResultListener() { // from class: com.miui.notes.ai.AiTextWidgetService.2
                @Override // com.miui.notes.ai.cta.CtaResultListener
                public void agree() {
                    if ((PreferenceUtils.isInternalTestInviteAccepted(AiTextWidgetService.this.getApplicationContext()) || PreferenceUtils.isInternalTestInviteAcceptedForSpecial(AiTextWidgetService.this.getApplicationContext())) && AiTextWidgetService.this.isXiaomiAccountLogin() && AiTextWidgetService.this.hasOverlayPermission()) {
                        if (!str.equals("com.miui.notes") && TextUtils.isEmpty(AiTextWidgetService.this.mSelectedText) && PreferenceUtils.isAcceptedMode(AiTextWidgetService.this) && !RomUtils.isSupportLLMCotAI(AiTextWidgetService.this)) {
                            AiTextWidgetService aiTextWidgetService = AiTextWidgetService.this;
                            Toast.makeText(aiTextWidgetService, aiTextWidgetService.getString(R.string.select_text_before_using), 0).show();
                            return;
                        } else {
                            AiTextWidgetService.this.mAiTextWidgetServiceHelper.startFloatWindowFromCTA();
                            return;
                        }
                    }
                    if (!PreferenceUtils.isInternalTestInviteAccepted(AiTextWidgetService.this.getApplicationContext())) {
                        if (PreferenceUtils.isInternalTestInviteAcceptedForSpecial(AiTextWidgetService.this.getApplicationContext())) {
                            return;
                        }
                        Intent intent = new Intent(AiTextWidgetService.this.getApplicationContext(), (Class<?>) InteralTestInviteActivity.class);
                        intent.addFlags(805306368);
                        AiTextWidgetService.this.startActivity(intent);
                        return;
                    }
                    if (!AiTextWidgetService.this.isXiaomiAccountLogin()) {
                        AiTextWidgetService.this.tryLoginXiaomiAccount();
                    } else {
                        if (AiTextWidgetService.this.isNeedDownloadLocalAi()) {
                            Intent intent2 = new Intent(AiTextWidgetService.this.getApplicationContext(), (Class<?>) AiLocalDownloadActivity.class);
                            intent2.addFlags(DataSpace.RANGE_LIMITED);
                            AiTextWidgetService.this.startActivity(intent2);
                            return;
                        }
                        AiTextWidgetService.this.tryApplyOverlayPermission();
                    }
                }

                @Override // com.miui.notes.ai.cta.CtaResultListener
                public void reject() {
                    AiTextWidgetService.this.mAiTextWidgetServiceHelper.stopService();
                }
            });
            if (!RomUtils.isInternationalBuild()) {
                Intent intent = new Intent(this, (Class<?>) CTATransparentActivity.class);
                intent.addFlags(DataSpace.RANGE_LIMITED);
                startActivity(intent);
                return;
            } else {
                Intent intent2 = new Intent(this, (Class<?>) GlobalPermissionActivity.class);
                intent2.addFlags(DataSpace.RANGE_LIMITED);
                startActivity(intent2);
                return;
            }
        }
        if (!PreferenceUtils.isInternalTestInviteAccepted(this)) {
            if (PreferenceUtils.isInternalTestInviteAcceptedForSpecial(this)) {
                return;
            }
            Intent intent3 = new Intent(this, (Class<?>) InteralTestInviteActivity.class);
            intent3.addFlags(805306368);
            startActivity(intent3);
            return;
        }
        if (!isXiaomiAccountLogin()) {
            tryLoginXiaomiAccount();
        } else {
            if (isNeedDownloadLocalAi()) {
                Intent intent4 = new Intent(getApplicationContext(), (Class<?>) AiLocalDownloadActivity.class);
                intent4.addFlags(DataSpace.RANGE_LIMITED);
                startActivity(intent4);
                return;
            }
            tryApplyOverlayPermission();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNeedDownloadLocalAi() {
        Logger.INSTANCE.d(TAG, "isNeedDownloadLocalAi isAcceptedMode:" + PreferenceUtils.isAcceptedMode(this));
        if (PreferenceUtils.isAcceptedMode(this)) {
            return (AIcrDownloadHelper.getModelStatus(this) && AIcrDownloadHelper.getCotConfig(this)) ? false : true;
        }
        return false;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper;
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.i(TAG, "onConfigurationChanged from service " + configuration + " orientation" + configuration.orientation);
        Logger.INSTANCE.i(TAG, "onConfigurationChanged from service isInNarrowScreen " + ScreenPosistionUtils.isInNarrowScreen(ContextWrapperUtil.wrapperWindowContext(this)));
        AiTextWidgetServiceHelper aiTextWidgetServiceHelper2 = this.mAiTextWidgetServiceHelper;
        if (aiTextWidgetServiceHelper2 != null && aiTextWidgetServiceHelper2.mAiFloatingWindow != null) {
            Logger.INSTANCE.i(TAG, "onConfigurationChanged: dismissing AI floating window");
            this.mAiTextWidgetServiceHelper.mAiFloatingWindow.dismissWindow();
        }
        if (this.onScreenChangeListener != null) {
            if (configuration.orientation == 2) {
                this.onScreenChangeListener.setOnScreenChange(true);
            } else if (configuration.orientation == 1) {
                this.onScreenChangeListener.setOnScreenChange(false);
            }
            OnScreenChangeListener onScreenChangeListener = this.onScreenChangeListener;
            if (onScreenChangeListener != null) {
                onScreenChangeListener.onConfigChange();
            }
        }
        if (mIsNightMode == DisplayUtil.isNightMode(this) || (aiTextWidgetServiceHelper = this.mAiTextWidgetServiceHelper) == null || aiTextWidgetServiceHelper.mAiFloatingWindow == null) {
            return;
        }
        this.mAiTextWidgetServiceHelper.mAiFloatingWindow.dismissWindow();
    }

    public void setOnScreenChangeListener(OnScreenChangeListener onScreenChangeListener) {
        this.onScreenChangeListener = onScreenChangeListener;
    }

    protected void registerReceiver() {
        Logger.INSTANCE.d(TAG, "registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE);
        intentFilter.addAction(ACTION_CLOSE_AI_Floating_WINDOW);
        if (getApplicationContext() != null) {
            if (RomUtils.isAboveSDK34()) {
                getApplicationContext().registerReceiver(this.mAiReceiver, intentFilter, 2);
            } else {
                getApplicationContext().registerReceiver(this.mAiReceiver, intentFilter);
            }
        }
    }

    private boolean isCallerTrusted() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == Process.myUid() || callingUid == 1000) {
            return true;
        }
        PackageManager packageManager = getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        if (packagesForUid == null) {
            return false;
        }
        for (String str : packagesForUid) {
            if (packageManager.checkSignatures(str, getPackageName()) == 0) {
                return true;
            }
        }
        return false;
    }
}
