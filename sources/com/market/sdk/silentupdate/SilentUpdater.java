package com.market.sdk.silentupdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.market.sdk.Constants;
import com.market.sdk.XiaomiUpdateAgent;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Client;
import com.xiaomi.market.IAppDownloadManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SilentUpdater {
    private static final String ACTION_DOWNLOAD = "com.xiaomi.market.service.AppDownloadService";
    public static final int CODE_ALREADY_NEW = -5;
    public static final int CODE_CTA_REFUSE = -6;
    public static final int CODE_DENIED = -4;
    public static final int CODE_DOWNLOAD_FAIL = -2;
    public static final int CODE_DOWNLOAD_INSTALLING = 5;
    public static final int CODE_DOWNLOAD_START = 1;
    public static final int CODE_DOWNLOAD_SUCCESS = 2;
    public static final int CODE_EXISTS = -1;
    public static final int CODE_INSTALL_FAIL = -3;
    public static final int CODE_INSTALL_START = 3;
    public static final int CODE_INSTALL_SUCCESS = 4;
    public static final int CODE_TARGET_VERSION_MISSING = -7;
    public static final int ERROR_INSTALL_DELAYED = 19;
    private static final int OP_CANCEL = 4;
    private static final int OP_PAUSE = 2;
    private static final int OP_RESUME = 3;
    private static final int OP_UPDATE = 1;
    public static final String PKG_DISCOVER = "com.xiaomi.discover";
    public static final String PKG_MARKET = "com.xiaomi.market";
    public static final String PKG_MIPICKS = "com.xiaomi.mipicks";
    public static final int PROGRESS_STATUS_CANCEL = -100;
    public static final int PROGRESS_STATUS_CONNECTING = -5;
    public static final int PROGRESS_STATUS_DOWNLOADING = -2;
    public static final int PROGRESS_STATUS_INSTALLING = -4;
    public static final int PROGRESS_STATUS_NONE = 0;
    public static final int PROGRESS_STATUS_PAUSED = -3;
    public static final int PROGRESS_STATUS_PENDING = -1;
    private static final String REF_PREFIX = "selfupdatesdk_";
    private List<Integer> actionQueue;
    private IAppDownloadManager appDownloadManager;
    private String appId;
    private String appKey;
    private UpdateCallback callback;
    private final UpdateResultReceiver.Callback callbackWrapper;
    private final ServiceConnection conn;
    private IBinder.DeathRecipient deathRecipient;
    private Bundle extras;
    private boolean forceUpdate;
    private boolean hideUpdate;
    private boolean showUserAgreementIfNeeded;
    private String targetPkg;
    private int versionCode;
    private boolean wifiOnly;

    public interface UpdateCallback {
        void onError(int i, int i2);

        void onProgressChanged(int i);

        void onStageChanged(int i);

        void onStatusChanged(int i);
    }

    private SilentUpdater() {
        this.actionQueue = new ArrayList();
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.market.sdk.silentupdate.SilentUpdater.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                SilentUpdater.this.appDownloadManager = null;
            }
        };
        this.conn = new ServiceConnection() { // from class: com.market.sdk.silentupdate.SilentUpdater.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                SilentUpdater.this.appDownloadManager = IAppDownloadManager.Stub.asInterface(iBinder);
                try {
                    SilentUpdater.this.appDownloadManager.asBinder().linkToDeath(SilentUpdater.this.deathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                UpdateResultReceiver.get().register(SilentUpdater.this.callbackWrapper);
                SilentUpdater.this.processActions();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                IAppDownloadManager iAppDownloadManager = SilentUpdater.this.appDownloadManager;
                if (iAppDownloadManager != null) {
                    iAppDownloadManager.asBinder().unlinkToDeath(SilentUpdater.this.deathRecipient, 0);
                }
                SilentUpdater.this.appDownloadManager = null;
            }
        };
        this.callbackWrapper = new UpdateResultReceiver.Callback() { // from class: com.market.sdk.silentupdate.SilentUpdater.3
            private int lastErrorCode = 0;
            private int lastStatus = 0;
            private int lastProgress = -1;

            @Override // com.market.sdk.silentupdate.UpdateResultReceiver.Callback
            public void onResult(Bundle bundle) {
                if (SilentUpdater.this.callback == null || bundle == null) {
                    return;
                }
                int i = bundle.getInt("errorCode");
                if (this.lastErrorCode != i) {
                    this.lastErrorCode = i;
                    if (i < 0 || i == 4) {
                        UpdateResultReceiver.get().unregister();
                        SilentUpdater.this.unbindService();
                    }
                    if (i >= 0) {
                        SilentUpdater.this.callback.onStageChanged(this.lastErrorCode);
                    } else {
                        SilentUpdater.this.callback.onError(i, bundle.getInt("reason"));
                    }
                }
                if (i == 5) {
                    int i2 = bundle.getInt("status");
                    int i3 = bundle.getInt("progress");
                    if (this.lastStatus != i2) {
                        SilentUpdater.this.callback.onStatusChanged(i2);
                        this.lastStatus = i2;
                    }
                    if (this.lastProgress != i3) {
                        SilentUpdater.this.callback.onProgressChanged(i3);
                        this.lastProgress = i3;
                    }
                }
            }
        };
    }

    public void update() throws CheckedException {
        enqueAction(1);
    }

    public void forceUpdate() throws CheckedException {
        this.forceUpdate = true;
        update();
    }

    public void pause() throws CheckedException {
        enqueAction(2);
    }

    public void cancel() throws CheckedException {
        removeAction(1);
        removeAction(2);
        removeAction(3);
        enqueAction(4);
    }

    public void resume() throws CheckedException {
        enqueAction(3);
    }

    private synchronized void removeAction(int i) {
        Iterator<Integer> it = this.actionQueue.iterator();
        while (it.hasNext()) {
            if (it.next().intValue() == i) {
                it.remove();
            }
        }
    }

    private synchronized void enqueAction(int i) throws CheckedException {
        this.actionQueue.add(Integer.valueOf(i));
        if (this.appDownloadManager == null) {
            bindService();
        } else {
            processActions();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void processActions() {
        Iterator it = new ArrayList(this.actionQueue).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            if (iIntValue == 1) {
                handleUpdate();
            } else if (iIntValue == 2) {
                handlePause();
            } else if (iIntValue == 3) {
                handleResume();
            } else if (iIntValue == 4) {
                handleCancel();
            }
        }
        this.actionQueue.clear();
    }

    private void handleCancel() {
        try {
            this.appDownloadManager.cancel(AppGlobal.getContext().getPackageName(), AppGlobal.getContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleResume() {
        try {
            this.appDownloadManager.resume(AppGlobal.getContext().getPackageName(), AppGlobal.getContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePause() {
        try {
            this.appDownloadManager.pause(AppGlobal.getContext().getPackageName(), AppGlobal.getContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate() {
        Context context = AppGlobal.getContext();
        String strGenerateNonce = SigGenerator.generateNonce();
        String str = REF_PREFIX + context.getPackageName();
        try {
            String signatureString = SigGenerator.getSignatureString(strGenerateNonce, this.appId, context.getPackageName(), str, this.appKey);
            Bundle bundle = new Bundle();
            bundle.putString("packageName", context.getPackageName());
            bundle.putString(Constants.EXTRA_SENDER_PACKAGE_NAME, context.getPackageName());
            bundle.putString("ref", str);
            bundle.putString(com.xiaomi.verificationsdk.internal.Constants.NONCE, strGenerateNonce);
            bundle.putString("appSignature", signatureString);
            bundle.putString("appClientId", this.appId);
            bundle.putInt("ext_targetVersionCode", this.versionCode);
            bundle.putBoolean("show_cta", this.showUserAgreementIfNeeded);
            bundle.putInt(com.market.sdk.utils.Constants.JSON_AB_TEST_IDENTIFIER, XiaomiUpdateAgent.getAbTestIdentifier().ordinal());
            if (this.forceUpdate) {
                bundle.putBoolean("marketClientControlParam_force_update", true);
            }
            if (this.hideUpdate) {
                bundle.putBoolean("marketClientControlParam_hide_download", true);
            }
            if (this.wifiOnly) {
                bundle.putBoolean("marketClientControlParam_download_wifi_only", true);
            }
            if (this.extras != null) {
                Iterator<String> it = bundle.keySet().iterator();
                while (it.hasNext()) {
                    this.extras.remove(it.next());
                }
                bundle.putAll(this.extras);
            }
            this.appDownloadManager.download(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindService() throws CheckedException {
        if (!isSilentUpdateAvailable(this.targetPkg)) {
            throw new CheckedException("SilentUpdate check failed，This operation is not supported");
        }
        Intent intent = new Intent(ACTION_DOWNLOAD);
        intent.setPackage(this.targetPkg);
        AppGlobal.getContext().bindService(intent, this.conn, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindService() {
        IAppDownloadManager iAppDownloadManager = this.appDownloadManager;
        if (iAppDownloadManager != null) {
            try {
                iAppDownloadManager.asBinder().unlinkToDeath(this.deathRecipient, 0);
                AppGlobal.getContext().unbindService(this.conn);
                this.appDownloadManager = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isSilentUpdateAvailable() {
        if (Client.isInternationalMiui()) {
            return isSupportedInPkg("com.xiaomi.mipicks", -1) || isSupportedInPkg("com.xiaomi.discover", -1);
        }
        return isSupportedInPkg("com.xiaomi.market", -1);
    }

    public static boolean isPauseAavailable() {
        if (Client.isInternationalMiui()) {
            return isSupportedInPkg("com.xiaomi.mipicks", 1914114) || isSupportedInPkg("com.xiaomi.discover", 1914114);
        }
        return isSupportedInPkg("com.xiaomi.market", 1914114);
    }

    public static boolean isResumeAavailable() {
        return isPauseAavailable();
    }

    public static boolean isCancelAvailable() {
        return isPauseAavailable();
    }

    public static boolean isSilentUpdateAvailable(String str) {
        if (TextUtils.isEmpty(str)) {
            return isSilentUpdateAvailable();
        }
        return isSupportedInPkg(str, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSupportedInPkg(String str, int i) {
        List<ResolveInfo> listQueryIntentServices;
        if (i > 0) {
            try {
                if (AppGlobal.getPackageManager().getPackageInfo(str, 0).versionCode < i) {
                    return false;
                }
                Intent intent = new Intent(ACTION_DOWNLOAD);
                intent.setPackage(str);
                listQueryIntentServices = AppGlobal.getPackageManager().queryIntentServices(intent, 0);
                if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty()) {
                    return listQueryIntentServices.get(0).serviceInfo.isEnabled();
                }
            } catch (Exception unused) {
            }
        } else {
            Intent intent2 = new Intent(ACTION_DOWNLOAD);
            intent2.setPackage(str);
            listQueryIntentServices = AppGlobal.getPackageManager().queryIntentServices(intent2, 0);
            if (listQueryIntentServices != null) {
                return listQueryIntentServices.get(0).serviceInfo.isEnabled();
            }
        }
        return false;
    }

    private static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private String appId;
        private String appKey;
        private UpdateCallback callback;
        private Bundle extras;
        private boolean forceUpdate;
        private boolean hideUpdate;
        private boolean showUserAgreementIfNeeded;
        private String targetPkg;
        private int versionCode = -1;
        private boolean wifiOnly;

        public Builder setTargetPkg(String str) {
            this.targetPkg = str;
            return this;
        }

        public Builder setAppId(String str) {
            this.appId = str;
            return this;
        }

        public Builder setAppKey(String str) {
            this.appKey = str;
            return this;
        }

        public Builder setVersionCode(int i) {
            this.versionCode = i;
            return this;
        }

        public Builder setCallback(UpdateCallback updateCallback) {
            this.callback = updateCallback;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = new Bundle(bundle);
            return this;
        }

        public Builder setForceUpdate(boolean z) {
            this.forceUpdate = z;
            return this;
        }

        public Builder setShowUserAgreementIfNeeded(boolean z) {
            this.showUserAgreementIfNeeded = z;
            return this;
        }

        public Builder setWifiOnly(boolean z) {
            this.wifiOnly = z;
            return this;
        }

        public Builder setHideUpdate(boolean z) {
            this.hideUpdate = z;
            return this;
        }

        public SilentUpdater update() throws CheckedException {
            SilentUpdater silentUpdaterBuild = build();
            silentUpdaterBuild.update();
            return silentUpdaterBuild;
        }

        public SilentUpdater build() {
            SilentUpdater silentUpdater = new SilentUpdater();
            silentUpdater.appId = this.appId;
            silentUpdater.appKey = this.appKey;
            silentUpdater.versionCode = this.versionCode;
            if (TextUtils.isEmpty(this.targetPkg)) {
                if (Client.isInternationalMiui()) {
                    this.targetPkg = SilentUpdater.isSupportedInPkg("com.xiaomi.mipicks", -1) ? "com.xiaomi.mipicks" : "com.xiaomi.discover";
                } else {
                    this.targetPkg = "com.xiaomi.market";
                }
            }
            silentUpdater.targetPkg = this.targetPkg;
            silentUpdater.callback = this.callback;
            silentUpdater.extras = this.extras;
            silentUpdater.forceUpdate = this.forceUpdate;
            silentUpdater.wifiOnly = this.wifiOnly;
            silentUpdater.hideUpdate = this.hideUpdate;
            silentUpdater.showUserAgreementIfNeeded = this.showUserAgreementIfNeeded;
            return silentUpdater;
        }
    }
}
