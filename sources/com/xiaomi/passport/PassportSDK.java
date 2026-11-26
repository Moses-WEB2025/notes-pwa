package com.xiaomi.passport;

import android.content.Context;
import com.xiaomi.accounts.secure.SecureDataManager;
import com.xiaomi.accountsdk.account.XMPassportUserAgent;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.interfaces.AuthenticatorIntentInterface;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.internal.AuthenticatorIntent;
import com.xiaomi.passport.ui.serverpass.ServerPassThroughErrorControl;
import com.xiaomi.passport.utils.AccountLoginUploader;
import com.xiaomi.passport.utils.AppVersionCheckHelper;

/* JADX INFO: loaded from: classes4.dex */
public class PassportSDK {
    private static volatile boolean sIsInternational = false;

    public static class SDKInitParams {
        private boolean mIsInternational;
        private AccountLogger.Logger mLogger;
        private AccountStatInterface mStatImpl;
        private AccountStatInterface.AccountStatType mStatType;
        private boolean mUseSystemAccountFirst;

        public SDKInitParams(boolean z, boolean z2) {
            this.mUseSystemAccountFirst = z;
            this.mIsInternational = z2;
        }

        public SDKInitParams statType(AccountStatInterface.AccountStatType accountStatType) {
            this.mStatType = accountStatType;
            return this;
        }

        public SDKInitParams logger(AccountLogger.Logger logger) {
            this.mLogger = logger;
            return this;
        }

        public SDKInitParams statImpl(AccountStatInterface accountStatInterface) {
            this.mStatImpl = accountStatInterface;
            return this;
        }
    }

    public static void init(final Context context, SDKInitParams sDKInitParams) {
        AppVersionCheckHelper.checkAppVersion(context);
        sIsInternational = sDKInitParams.mIsInternational;
        if (sDKInitParams.mStatImpl != null) {
            AccountStatInterface.init(sDKInitParams.mStatImpl);
        } else if (sDKInitParams.mStatType != null) {
            AccountStatInterface.init(context, sDKInitParams.mStatType, sDKInitParams.mIsInternational, true);
        } else {
            AccountStatInterface.init(context, AccountStatInterface.AccountStatType.NONE, sDKInitParams.mIsInternational, true);
        }
        XiaomiAccountManager.setup(context, sDKInitParams.mUseSystemAccountFirst);
        if (sDKInitParams.mLogger != null) {
            AccountLogger.setInstance(sDKInitParams.mLogger);
        }
        ServerPassThroughErrorHandler.setControl(new ServerPassThroughErrorControl());
        XMPassportUserAgent.addExtendedUserAgent("PassportSDK/5.3.0.release.94");
        XMPassportUserAgent.addExtendedUserAgent("passport-ui/5.3.0.release.94");
        XMPassportUserAgent.addExtendedUserAgent("XiaomiAccountSSO/5.3.0.release.94");
        AuthenticatorIntentInterface.set(new AuthenticatorIntent());
        new BgTask(new BgTask.BgTaskRunnable<Void>() { // from class: com.xiaomi.passport.PassportSDK.1
            @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
            public Void run() throws Throwable {
                SecureDataManager.safeGenerateOrInitKey(context);
                return null;
            }
        }, null, null).execute();
        AccountLoginUploader.retryLocalUpload();
    }

    public static boolean isInternational() {
        return sIsInternational;
    }
}
