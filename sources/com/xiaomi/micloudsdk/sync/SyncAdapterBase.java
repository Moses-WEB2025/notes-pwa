package com.xiaomi.micloudsdk.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.SyncLocalException;
import com.xiaomi.micloudsdk.request.utils.SyncRequestParam;
import com.xiaomi.micloudsdk.stat.MiCloudErrorStatReason;
import com.xiaomi.micloudsdk.sync.utils.LegacySyncRecordUtils;
import com.xiaomi.micloudsdk.sync.utils.SyncExceptionUtils;
import com.xiaomi.micloudsdk.sync.utils.SyncTimeUtils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.ReflectUtils;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.lang.reflect.Field;
import micloud.compat.v18.sync.SyncAdapterBaseInjectorCompat;
import miui.cloud.log.PrivacyFilter;
import miui.cloud.util.SyncAutoSettingUtil;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SyncAdapterBase extends AbstractThreadedSyncAdapter {
    private static final String SYNC_EXTRA_KEY_TOKEN_INVALID_RETRY = "token_invalid_retry";
    protected static final String TAG = "MiCloudSyncAdapterBase";
    protected boolean isForceSync;
    protected boolean isIgnoreBackoff;
    protected boolean isIgnoreBatteryLow;
    protected boolean isIgnoreSettings;
    protected boolean isIgnoreTemperature;
    protected boolean isIgnoreWifiSettings;
    protected boolean isManualSync;
    protected Account mAccount;
    protected final String mAuthType;
    protected String mAuthority;
    protected Context mContext;
    protected ExtendedAuthToken mExtToken;
    protected String mExtTokenStr;
    protected String[] mNumbers;
    protected ContentResolver mResolver;
    private SyncLogSender mSyncLogSender;
    protected SyncResult mSyncResult;
    protected String[] mTickets;

    protected abstract String getErrorTitle();

    protected abstract String getForbiddenErrorText();

    protected abstract String getNotAcceptableErrorText();

    protected abstract String getUnauthorizedErrorText();

    protected abstract void onPerformMiCloudSync(Bundle bundle) throws CloudServerException, SyncLocalException;

    protected void onPerformMiCloudSyncComplete(MiCloudSyncResult miCloudSyncResult) {
    }

    protected void onTransformExtAuthToken(ExtendedAuthToken extendedAuthToken) {
    }

    public SyncAdapterBase(Context context, boolean z, String str) {
        super(context, z);
        this.mTickets = new String[2];
        this.mNumbers = new String[2];
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mAuthType = str;
    }

    @Override // android.content.AbstractThreadedSyncAdapter
    public void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        long j;
        Log.d(TAG, "check_sync_error_refactor");
        this.mAccount = account;
        this.mAuthority = str;
        this.mSyncResult = syncResult;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str2 = "SyncLog[" + str + "]";
        Log.d(TAG, "onPerformSync: ---sync start---");
        Log.d(TAG, "authority: " + str + ", extras: " + bundle.toString());
        this.isForceSync = bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false);
        this.isIgnoreTemperature = bundle.getBoolean("micloud_ignore_temperature", false);
        this.isIgnoreWifiSettings = bundle.getBoolean("micloud_ignore_wifi_settings", false);
        this.isIgnoreBatteryLow = bundle.getBoolean("micloud_ignore_battery_low", false);
        this.isManualSync = bundle.getBoolean(Constants.FORCE, false);
        this.isIgnoreBackoff = bundle.getBoolean("ignore_backoff", false);
        boolean z = bundle.getBoolean("ignore_settings", false);
        this.isIgnoreSettings = z;
        boolean z2 = this.isForceSync || this.isManualSync || z;
        SyncStatUtil.statOnSyncStart(this.mContext, z2);
        acquireLogger(str);
        syncLog(str2, new SyncLogInfo.SyncStartLogInfo(str, jCurrentTimeMillis, bundle).generateLogInfoString());
        SyncDataInfo syncDataInfoGenerateCurrentSyncDataInfo = SyncDataInfo.generateCurrentSyncDataInfo(this.mContext, str);
        syncLog(str2, "Before Sync: " + syncDataInfoGenerateCurrentSyncDataInfo.toString());
        MiCloudSyncResult miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createSuccessResult();
        if (!SyncAdapterBaseInjectorCompat.isGdprPermissionGranted(this.mContext, syncResult)) {
            Log.i(TAG, "Gdpr Permission deny: " + str);
            internalSetGDPRError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2000), 2000, MiCloudErrorStatReason.GDPR_DENY);
        } else if (!SyncAutoSettingUtil.getXiaomiGlobalSyncAutomatically() || !ContentResolver.getSyncAutomatically(account, str)) {
            Log.e(TAG, "onPerformSync: sync switch off. do not sync!!");
            internalSetSyncSwitchOffError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2003), LegacySyncRecordUtils.getSyncSwitchOffErrorLegacyCode(), "switch_off");
        } else if (MiCloudResolver.isSyncPausing(this.mContext, this.mAccount, this.mAuthority)) {
            Log.e(TAG, "onPerformSync: sync is set to pause. do not sync!!");
            internalSetPauseSyncError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2004), LegacySyncRecordUtils.getPauseLimitErrorLegacyCode(), "pause_limit");
        } else {
            ExtTokenResult extTokenResult = getExtTokenResult(this.mContext, this.mAccount, this.mAuthority);
            this.mExtTokenStr = extTokenResult.extTokenStr;
            if (extTokenResult.error != null) {
                Log.e(TAG, "onPerformSync: Tried 3 times, but get null token.");
                internalSetAuthError(syncResult);
                miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(extTokenResult.error, LegacySyncRecordUtils.getAuthTokenErrorLegacyCode(), MiCloudErrorStatReason.AUTH_TOKEN_ERROR);
            } else {
                ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(this.mExtTokenStr);
                this.mExtToken = extendedAuthToken;
                onTransformExtAuthToken(extendedAuthToken);
                if (this.mExtToken == null) {
                    Log.e(TAG, "onPerformSync: Cannot parse ext token");
                    internalSetAuthError(syncResult);
                    invalidTokenAndReSyncNowIfFirstTimeFailed(this.mContext, account, str, this.mExtTokenStr, bundle);
                    miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(100), LegacySyncRecordUtils.getAuthTokenErrorLegacyCode(), MiCloudErrorStatReason.AUTH_TOKEN_ERROR);
                } else {
                    boolean z3 = this.isForceSync || this.isManualSync || this.isIgnoreSettings;
                    if (z3) {
                        SyncRequestParam.incrementManualSyncCountAndRecordCurMillis();
                    }
                    try {
                        try {
                            try {
                                onPerformMiCloudSync(bundle);
                                if (z3) {
                                    SyncRequestParam.decrementManualSyncCount();
                                }
                                if (syncResult.hasError()) {
                                    Log.w(TAG, "onPerformSync: Error without throw CloudException: " + SyncExceptionUtils.getErrorStringForLog(syncResult));
                                    miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(syncResult.hasHardError() ? 2 : 1), LegacySyncRecordUtils.getErrorCodeBySyncResult(syncResult), MiCloudErrorStatReason.getSyncResultErrorReasonForStats(syncResult));
                                } else {
                                    Log.d(TAG, "onPerformSync: No error found. ---Success---");
                                }
                            } catch (CloudServerException e) {
                                setSyncResultByServerException(e, syncResult);
                                if (e.statusCode == 401) {
                                    invalidTokenAndReSyncNowIfFirstTimeFailed(this.mContext, account, str, this.mExtTokenStr, bundle);
                                }
                                long jMin = e.is5xxServerException() ? Math.min(e.get5xxServerExceptionRetryTime() / 1000, 1800L) : 0L;
                                String serverExceptionReasonForStats = MiCloudErrorStatReason.getServerExceptionReasonForStats(e);
                                Log.e(TAG, "onPerformSync server exception, reason: " + serverExceptionReasonForStats);
                                miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(e, LegacySyncRecordUtils.getErrorCodeByServerException(e), serverExceptionReasonForStats);
                                if (z3) {
                                    SyncRequestParam.decrementManualSyncCount();
                                }
                                j = jMin;
                            } catch (SecurityException e2) {
                                e2.printStackTrace();
                                syncLog(str2, "SecurityException: " + e2.getMessage());
                                throw e2;
                            }
                        } catch (SyncLocalException e3) {
                            setSyncResultByLocalException(e3, syncResult);
                            String localExceptionReasonForStats = MiCloudErrorStatReason.getLocalExceptionReasonForStats(e3);
                            Log.e(TAG, "onPerformSync local exception, reason: " + localExceptionReasonForStats);
                            handleExceptionIfKeyBagRelated(e3);
                            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(e3, LegacySyncRecordUtils.getErrorCodeByLocalException(e3), localExceptionReasonForStats);
                            if (z3) {
                                SyncRequestParam.decrementManualSyncCount();
                            }
                        }
                    } catch (Throwable th) {
                        if (z3) {
                            SyncRequestParam.decrementManualSyncCount();
                        }
                        throw th;
                    }
                }
            }
        }
        j = 0;
        SyncDataInfo syncDataInfoGenerateCurrentSyncDataInfo2 = SyncDataInfo.generateCurrentSyncDataInfo(this.mContext, str);
        syncLog(str2, "After Sync: " + syncDataInfoGenerateCurrentSyncDataInfo2.toString());
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        syncLog(str2, new SyncLogInfo.SyncEndLogInfo(str, miCloudSyncResultCreateSuccessResult.isSuccess, jCurrentTimeMillis, jCurrentTimeMillis2, getExtraErrorMsgForResultOrNullIfFieldNotExists(syncResult)).generateLogInfoString());
        long j2 = j;
        SyncStatUtil.statOnSyncEnd(this.mContext, z2, miCloudSyncResultCreateSuccessResult.isSuccess, miCloudSyncResultCreateSuccessResult.errorReasonForStats, str, syncDataInfoGenerateCurrentSyncDataInfo.getUnSyncedDataCount(), syncDataInfoGenerateCurrentSyncDataInfo2.getUnSyncedDataCount(), jCurrentTimeMillis, jCurrentTimeMillis2);
        if (!miCloudSyncResultCreateSuccessResult.isSuccess) {
            LegacySyncRecordUtils.recordSyncResult(this.mContext, str, miCloudSyncResultCreateSuccessResult.errorCodeLegacy);
        } else {
            LegacySyncRecordUtils.recordSyncResultSuccess(this.mContext, str);
        }
        syncResult.delayUntil = Math.max(syncResult.delayUntil, 0L);
        syncResult.delayUntil = Math.max(syncResult.delayUntil, SyncTimeUtils.getGuardDelayUntilInSec(this.mContext, this.mAuthority));
        if (!miCloudSyncResultCreateSuccessResult.isSuccess) {
            syncResult.delayUntil = Math.max(syncResult.delayUntil, j2);
        }
        onPerformMiCloudSyncComplete(miCloudSyncResultCreateSuccessResult);
        releaseLogger();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0048 A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:23:0x0050 A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:25:0x0058 A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:26:0x005f A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:28:0x006b A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0072 A[Catch: InterruptedException -> 0x0011, IOException -> 0x0078, AuthenticatorException -> 0x007a, OperationCanceledException -> 0x007c, TRY_LEAVE, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x0042, B:22:0x0048, B:23:0x0050, B:25:0x0058, B:26:0x005f, B:28:0x006b, B:29:0x0072), top: B:55:0x000d }] */
    private ExtTokenResult getExtTokenResult(Context context, Account account, String str) {
        Account account2;
        AccountManagerFuture<Bundle> authToken;
        Bundle result;
        String string;
        int i = 0;
        Throwable e = null;
        while (i < 3) {
            if (i == 0) {
                Log.v(TAG, "onPerformSync: getting auth token. authority: " + str);
                account2 = account;
                authToken = AccountManager.get(context).getAuthToken(account2, this.mAuthType, true, null, null);
                if (authToken == null) {
                    Log.e(TAG, "onPerformSync: getExtTokenStr null future.");
                } else {
                    result = authToken.getResult();
                    if (result == null) {
                        Log.e(TAG, "onPerformSync: getExtTokenStr null future result.");
                    } else {
                        string = result.getString("authtoken");
                        if (TextUtils.isEmpty(string)) {
                            Log.e(TAG, "onPerformSync: getExtTokenStr future result textEmpty.");
                        } else {
                            return new ExtTokenResult(string, null);
                        }
                    }
                }
            } else {
                try {
                    try {
                        Thread.sleep(1000L);
                        try {
                            Log.v(TAG, "onPerformSync: getting auth token. authority: " + str);
                            account2 = account;
                            try {
                                authToken = AccountManager.get(context).getAuthToken(account2, this.mAuthType, true, null, null);
                                if (authToken == null) {
                                    Log.e(TAG, "onPerformSync: getExtTokenStr null future.");
                                } else {
                                    result = authToken.getResult();
                                    if (result == null) {
                                        Log.e(TAG, "onPerformSync: getExtTokenStr null future result.");
                                    } else {
                                        string = result.getString("authtoken");
                                        if (TextUtils.isEmpty(string)) {
                                            Log.e(TAG, "onPerformSync: getExtTokenStr future result textEmpty.");
                                        } else {
                                            return new ExtTokenResult(string, null);
                                        }
                                    }
                                }
                            } catch (AuthenticatorException e2) {
                                e = e2;
                                Log.e(TAG, "onPerformSync getExtTokenStr AuthenticatorException: ", e);
                            } catch (OperationCanceledException e3) {
                                e = e3;
                                Log.e(TAG, "onPerformSync getExtTokenStr OperationCanceledException: ", e);
                            } catch (IOException e4) {
                                e = e4;
                                Log.e(TAG, "onPerformSync getExtTokenStr IOException: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e)));
                            }
                        } catch (AuthenticatorException e5) {
                            e = e5;
                            account2 = account;
                        } catch (OperationCanceledException e6) {
                            e = e6;
                            account2 = account;
                        } catch (IOException e7) {
                            e = e7;
                            account2 = account;
                        }
                    } catch (AuthenticatorException e8) {
                        e = e8;
                        account2 = account;
                        Log.e(TAG, "onPerformSync getExtTokenStr AuthenticatorException: ", e);
                        i++;
                        account = account2;
                    } catch (OperationCanceledException e9) {
                        e = e9;
                        account2 = account;
                        Log.e(TAG, "onPerformSync getExtTokenStr OperationCanceledException: ", e);
                        i++;
                        account = account2;
                    } catch (IOException e10) {
                        e = e10;
                        account2 = account;
                        Log.e(TAG, "onPerformSync getExtTokenStr IOException: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e)));
                        i++;
                        account = account2;
                    }
                } catch (InterruptedException e11) {
                    e = e11;
                    Log.e(TAG, "onPerformSync getExtTokenStr InterruptedException: ", e);
                    Thread.currentThread().interrupt();
                }
            }
            i++;
            account = account2;
        }
        if (e == null) {
            e = new SyncLocalException(100);
        }
        return new ExtTokenResult(null, e);
    }

    private static void setSyncResultByServerException(CloudServerException cloudServerException, SyncResult syncResult) {
        int i = cloudServerException.statusCode;
        if (i == -10001) {
            setSyncResultByLocalExceptionCode(cloudServerException.code, syncResult);
            return;
        }
        if (i == 403 || i == 406 || i == 400) {
            internalSetRequestError(syncResult);
            return;
        }
        if (i == 401) {
            internalSetAuthError(syncResult);
            return;
        }
        if (cloudServerException.code == 52003) {
            internalSetGDPRError(syncResult);
        } else if (cloudServerException.is5xxServerException()) {
            internalSetRequestError(syncResult);
        } else {
            internalSetUnknownError(syncResult);
        }
    }

    private static void setSyncResultByLocalException(SyncLocalException syncLocalException, SyncResult syncResult) {
        setSyncResultByLocalExceptionCode(syncLocalException.getErrorCode(), syncResult);
    }

    private static void setSyncResultByLocalExceptionCode(int i, SyncResult syncResult) {
        switch (i) {
            case 1000:
                internalSetCTAError(syncResult);
                break;
            case 1001:
                internalSetSimActivatedError(syncResult);
                break;
            case 1002:
                internalSetPermissionLimit(syncResult);
                break;
            case 1003:
                internalSetSecureSpaceLimitError(syncResult);
                break;
            default:
                switch (i) {
                    case 2000:
                        internalSetGDPRError(syncResult);
                        break;
                    case 2001:
                        internalSetCloudStorageFullError(syncResult);
                        break;
                    case 2002:
                        internalSetWlanOnlyError(syncResult);
                        break;
                    default:
                        internalSetUnknownError(syncResult);
                        break;
                }
                break;
        }
    }

    private static void invalidTokenAndReSyncNowIfFirstTimeFailed(Context context, Account account, String str, String str2, Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        if (bundle2.getBoolean(SYNC_EXTRA_KEY_TOKEN_INVALID_RETRY, false)) {
            Log.e(TAG, "Already retry with invalid token but still failed");
            return;
        }
        bundle2.putBoolean(SYNC_EXTRA_KEY_TOKEN_INVALID_RETRY, true);
        Log.w(TAG, "AuthToken expired. Invalid and retry now");
        AccountManager.get(context).invalidateAuthToken(account.type, str2);
        ContentResolver.requestSync(account, str, bundle2);
    }

    protected boolean isManualSync() {
        return this.isManualSync;
    }

    protected boolean isForceSync() {
        return this.isForceSync;
    }

    protected boolean isIgnoreTemperature() {
        return this.isForceSync || this.isIgnoreTemperature;
    }

    protected boolean isIgnoreWifiSettings() {
        return this.isForceSync || this.isIgnoreWifiSettings;
    }

    protected boolean isIgnoreBatteryLow() {
        return this.isForceSync || this.isIgnoreBatteryLow;
    }

    protected static void setRequestError(SyncResult syncResult) {
        internalSetRequestError(syncResult);
    }

    private static void internalSetRequestError(SyncResult syncResult) {
        syncResult.stats.numIoExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_REQUEST_ERROR);
    }

    private static void internalSetUnknownError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
    }

    private static void internalSetAuthError(SyncResult syncResult) {
        syncResult.stats.numAuthExceptions++;
    }

    private static void internalSetGDPRError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_GDPR_ERROR);
    }

    private static void internalSetSyncSwitchOffError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "switch_off");
    }

    private static void internalSetPauseSyncError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "pause_limit");
    }

    protected static void setCloudStorageFullError(SyncResult syncResult) {
        internalSetCloudStorageFullError(syncResult);
    }

    private static void internalSetCloudStorageFullError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_CLOUD_STORAGE_FULL);
    }

    protected static void setPermissionError(SyncResult syncResult) {
        internalSetCTAError(syncResult);
    }

    private static void internalSetCTAError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_CTA_ERROR);
    }

    private static void internalSetSimActivatedError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_SIM_ACTIVATED_ERROR);
    }

    private static void internalSetPermissionLimit(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "permission_limit");
    }

    protected static void setSecureSpaceLimitError(SyncResult syncResult) {
        internalSetSecureSpaceLimitError(syncResult);
    }

    private static void internalSetSecureSpaceLimitError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "secure_space_limit");
    }

    private static void internalSetWlanOnlyError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, MiSyncConstants.ResultMessage.MESG_WLAN_ONLY);
    }

    private void acquireLogger(String str) {
        SyncLogSender syncLogSenderNewSyncLogSender = SyncLogSenderFactory.newSyncLogSender(this.mContext, str);
        this.mSyncLogSender = syncLogSenderNewSyncLogSender;
        syncLogSenderNewSyncLogSender.openSyncLog();
    }

    private void releaseLogger() {
        this.mSyncLogSender.release();
        this.mSyncLogSender = null;
    }

    protected void syncLog(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("tag or msg is null!");
        }
        this.mSyncLogSender.sendLog(str, str2);
    }

    private void handleExceptionIfKeyBagRelated(SyncLocalException syncLocalException) {
        if (syncLocalException.getErrorCode() == 3000) {
            this.mContext.sendBroadcast(new Intent().setPackage("com.miui.cloudservice").setAction("com.miui.cloudservice.keybag.UPDATE"));
        } else if (syncLocalException.getErrorCode() == 3001) {
            this.mContext.sendBroadcast(new Intent().setPackage("com.miui.cloudservice").setAction("com.miui.cloudservice.keybag.DOWNLOAD"));
        }
    }

    private static void setExtraErrorMsgForResultIfFieldExists(SyncResult syncResult, String str) {
        ResultMessage resultMessageOrNullIfFieldNotExists = getResultMessageOrNullIfFieldNotExists(syncResult);
        if (resultMessageOrNullIfFieldNotExists == null) {
            return;
        }
        resultMessageOrNullIfFieldNotExists.set(str);
    }

    private static String getExtraErrorMsgForResultOrNullIfFieldNotExists(SyncResult syncResult) {
        ResultMessage resultMessageOrNullIfFieldNotExists = getResultMessageOrNullIfFieldNotExists(syncResult);
        if (resultMessageOrNullIfFieldNotExists == null) {
            return null;
        }
        return resultMessageOrNullIfFieldNotExists.get();
    }

    private static ResultMessage getResultMessageOrNullIfFieldNotExists(SyncResult syncResult) {
        Field field = ReflectUtils.getField(syncResult.getClass(), "miSyncResult");
        if (field == null) {
            return null;
        }
        try {
            Object obj = field.get(syncResult);
            Field field2 = ReflectUtils.getField(obj.getClass(), "resultMessage");
            if (field2 == null) {
                throw new RuntimeException("resultMessageField is null: Please file a bug to CloudService!!");
            }
            return new ResultMessage(obj, field2);
        } catch (IllegalAccessException unused) {
            throw new RuntimeException("get miSyncResultField: Please file a bug to CloudService!!");
        }
    }

    static class ResultMessage {
        private final Object miSyncResult;
        private final Field resultMessageField;

        ResultMessage(Object obj, Field field) {
            this.miSyncResult = obj;
            this.resultMessageField = field;
        }

        public void set(String str) {
            try {
                this.resultMessageField.set(this.miSyncResult, str);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("set miSyncResult: Please file a bug to CloudService!!", e);
            }
        }

        public String get() {
            try {
                return (String) this.resultMessageField.get(this.miSyncResult);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("get resultMessageField: Please file a bug to CloudService!!", e);
            }
        }
    }

    private class ExtTokenResult {
        private final Throwable error;
        private final String extTokenStr;

        public ExtTokenResult(String str, Throwable th) {
            this.extTokenStr = str;
            this.error = th;
        }
    }
}
