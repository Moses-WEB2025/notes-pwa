package com.miui.notes.cloudservice;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import com.miui.common.tool.Logger;
import com.miui.notes.ai.utils.LiteUtils;
import com.miui.notes.base.utils.ThreadPoolUtils;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.todo.cloudservice.ReSyncException;
import com.miui.todo.cloudservice.RetriableSyncException;
import com.miui.todo.cloudservice.TodoServiceAdapter;
import com.miui.todo.utils.SerializableUtils;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.opensdk.pdc.SyncException;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import miui.cloud.CloudSyncUtils;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public class NotesSyncAdapter extends CloudServiceSyncAdapterBase {
    private static final String AUTH_TOKEN_TYPE = "micloud";
    private static final String CLOUD_SER_URL = "content://com.miui.notes.cloudservices";
    static final boolean DEBUG_ENABLE = false;
    private static final int DELETION_TAG = 1;
    private static final String IS_ACCEPT = "is_accept";
    public static final String MICLOUD_NOTE_SYNC = "micloud.note.sync";
    private static final String Method = "onPerformMiCloudSync";
    private static final int SYNC_RESULT_CLOUD_ERROR = 100000;
    private static final int SYNC_RESULT_INNER_ERROR = 400;
    private static final int SYNC_RESULT_OK = 200;
    private static final int SYNC_RESULT_RETURN = 300;
    private static final String TAG = "NotesSyncAdapter";
    private boolean mCanceled;
    private TodoServiceAdapter mTodoServiceAdapter;

    public NotesSyncAdapter(Context context, boolean z) {
        super(context, z, "micloud");
        this.mCanceled = false;
        this.mTodoServiceAdapter = new TodoServiceAdapter(context);
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncAdapterBase
    protected void onPerformMiCloudSync(final Bundle bundle) throws CloudServerException {
        int iIntValue;
        Logger.INSTANCE.i(TAG, "start onPerformMiCloudSync");
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.notes.cloudservice.NotesSyncAdapter$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$onPerformMiCloudSync$0(bundle);
            }
        });
        ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(futureTask);
        try {
            iIntValue = ((Integer) futureTask.get()).intValue();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "syncError" + e.getMessage());
            iIntValue = 401;
        }
        Logger.INSTANCE.i(TAG, "end onPerformMiCloudSync result " + iIntValue);
        SyncUtils.setReSynced(false);
        if (iIntValue <= 100000) {
            return;
        }
        int i = iIntValue - 100000;
        Logger.INSTANCE.i(TAG, "throw code:" + i);
        throw new CloudServerException(CloudServerException.DEFAULT_STATUS_CODE, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$onPerformMiCloudSync$0(Bundle bundle) throws Exception {
        Logger.INSTANCE.i(TAG, "do onPerformMiCloudSync");
        if (ActivityManager.isUserAMonkey() || LiteUtils.isMishowRom()) {
            return 300;
        }
        boolean z = false;
        try {
            Bundle bundleCall = getContext().getContentResolver().call(Uri.parse(CLOUD_SER_URL), Method, (String) null, (Bundle) null);
            if (bundleCall == null || bundleCall.getBoolean("is_accept")) {
                z = true;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error:" + e.getMessage());
        }
        Logger.INSTANCE.w(TAG, "isCTAAccepted " + z);
        try {
            if (PermissionUtils.supportNewPermissionStyle()) {
                if (PreferenceUtils.getAllowNetwork(getContext())) {
                    performMiCloudSync(bundle);
                } else {
                    return 101000;
                }
            } else if (z) {
                performMiCloudSync(bundle);
            } else {
                return 101000;
            }
            return 200;
        } catch (CloudServerException e2) {
            return Integer.valueOf(e2.getCode() + 100000);
        }
    }

    private synchronized void performMiCloudSync(Bundle bundle) throws CloudServerException {
        AccountEntity accountEntity;
        Logger.INSTANCE.i(TAG, "start performMiCloudSync");
        Thread.interrupted();
        this.mCanceled = false;
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                AccountEntity account = AccountEntity.getAccount(this.mContext, this.mAccount);
                                try {
                                    if (account == null) {
                                        Logger.INSTANCE.e(TAG, "Fail to get account");
                                        return;
                                    }
                                    int i = bundle.getInt("push_type", 0);
                                    try {
                                        Logger.INSTANCE.i(TAG, "pushType:" + i);
                                        if (i == 2 || i == 0) {
                                            this.mTodoServiceAdapter.setAccount(this.mAccount);
                                            this.mTodoServiceAdapter.onPerformMiCloudSync(bundle);
                                        }
                                        if (i == 1 || i == 0) {
                                            if (!hasSyncedBefore(account)) {
                                                doPreSync(account);
                                            }
                                            if (this.mCanceled) {
                                                Logger.INSTANCE.i(TAG, "Current sync is canceled");
                                                return;
                                            }
                                            doFullSync(account);
                                            if (this.mCanceled) {
                                                Logger.INSTANCE.w(TAG, "Current sync is canceled");
                                                return;
                                            }
                                            updateWelcomeNotes();
                                            if (this.mCanceled) {
                                                Logger.INSTANCE.w(TAG, "Current sync is canceled");
                                                return;
                                            }
                                            deleteTagedTrashNotes();
                                        }
                                    } catch (JSONException e) {
                                        e = e;
                                        Logger.INSTANCE.e(TAG, "error:" + e.getMessage());
                                        this.mSyncResult.stats.numParseExceptions++;
                                    }
                                } catch (NoteSyncException e2) {
                                    e = e2;
                                    accountEntity = account;
                                    Logger.INSTANCE.e(TAG, "error:" + e.getMessage());
                                    if (!fixRecoverableException(accountEntity, e)) {
                                        handleCommonException(accountEntity, e);
                                    }
                                }
                            } catch (ReSyncException e3) {
                                Logger.INSTANCE.e(TAG, "error:" + e3.getMessage());
                                SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY, null);
                                this.mSyncResult.stats.numIoExceptions++;
                            } catch (SecurityException e4) {
                                Logger.INSTANCE.e(TAG, "error:" + e4.getMessage());
                                syncLog(TAG, e4.getMessage());
                                throw e4;
                            }
                        } catch (RetriableSyncException e5) {
                            Logger.INSTANCE.e(TAG, "error:" + e5.getMessage());
                            this.mSyncResult.delayUntil = e5.mRetryTime;
                            this.mSyncResult.stats.numIoExceptions++;
                        } catch (IllegalArgumentException e6) {
                            Logger.INSTANCE.e(TAG, "error:" + e6.getMessage());
                        }
                    } catch (SQLiteException e7) {
                        Logger.INSTANCE.e(TAG, "error:" + e7.getMessage());
                        this.mSyncResult.stats.numParseExceptions++;
                    } catch (SyncException e8) {
                        Logger.INSTANCE.e(TAG, "error:" + e8.getMessage());
                        this.mSyncResult.stats.numParseExceptions++;
                    }
                } catch (NoteSyncException e9) {
                    e = e9;
                    accountEntity = null;
                }
            } catch (CloudServerException e10) {
                Logger.INSTANCE.e(TAG, "error:" + e10.getMessage());
                this.mSyncResult.stats.numParseExceptions++;
            }
        } catch (JSONException e11) {
            e = e11;
        }
    }

    private boolean hasSyncedBefore(AccountEntity accountEntity) {
        if (accountEntity.hasSyncedBefore()) {
            return NotesUtils.getSyncedBefore(this.mContext);
        }
        NotesUtils.setSyncedBefore(this.mContext, false);
        return false;
    }

    private void doPreSync(AccountEntity accountEntity) throws Throwable {
        Logger.INSTANCE.i(TAG, "start doPreSync");
        CloudSyncUtils.clearLocalWatermark(this.mContext, "micloud.note.sync");
        NoteSyncContext noteSyncContext = new NoteSyncContext(this.mContext, accountEntity, transformAuthToken(this.mExtToken));
        SyncManager syncManager = new SyncManager(noteSyncContext);
        noteSyncContext.setIsResync(true);
        syncManager.pull();
        syncManager.down();
        NotesUtils.setSyncedBefore(this.mContext, true);
        syncManager.fireException();
        Logger.INSTANCE.i(TAG, "end doPreSync");
    }

    private void doFullSync(AccountEntity accountEntity) throws JSONException, CloudServerException, NoteSyncException {
        Logger.INSTANCE.i(TAG, "start doFullSync");
        SyncManager syncManager = new SyncManager(new NoteSyncContext(this.mContext, accountEntity, transformAuthToken(this.mExtToken)));
        if (accountEntity.getSyncExtraInfo() == null) {
            syncManager.upgrade();
        }
        if (PreferenceUtils.getIsNeedFullUpgrade(this.mContext)) {
            syncManager.fullUpgrade();
        }
        if (PreferenceUtils.getIsNeedFullUpgradeWithHandwrite(this.mContext)) {
            syncManager.fullUpgradeWithHandwrite();
        }
        boolean isNeedFullUpgradeWithMultiHandwrite = PreferenceUtils.getIsNeedFullUpgradeWithMultiHandwrite(this.mContext);
        Logger.INSTANCE.i(TAG, "IsNeedFullUpgradeWithMultiHandwrite : " + isNeedFullUpgradeWithMultiHandwrite + "");
        if (isNeedFullUpgradeWithMultiHandwrite) {
            syncManager.fullUpgradeWithMultiHandwrite();
        }
        try {
            if (!this.mCanceled) {
                syncManager.push();
            }
            if (!this.mCanceled) {
                syncManager.pull();
            }
            Notes.Utils.updateAllAlarms(this.mContext);
            Notes.Utils.updateAllWidgets(this.mContext);
            syncManager.down();
            syncManager.fireException();
            accountEntity.setUnrecoverableCount(0);
            accountEntity.persist(this.mContext);
            Logger.INSTANCE.i(TAG, "end doFullSync");
        } catch (Throwable th) {
            Notes.Utils.updateAllAlarms(this.mContext);
            Notes.Utils.updateAllWidgets(this.mContext);
            throw th;
        }
    }

    private boolean fixRecoverableException(AccountEntity accountEntity, NoteSyncException noteSyncException) {
        if (accountEntity == null || noteSyncException.isRecoverable()) {
            return false;
        }
        Logger.INSTANCE.e(TAG, "A unrecoverable error occurs");
        if (accountEntity.increaseUnrecoverableCount()) {
            Exception innerException = noteSyncException.getInnerException();
            NoteSyncException innerException2 = noteSyncException;
            if (innerException != null) {
                innerException2 = noteSyncException.getInnerException();
            }
            Logger.INSTANCE.e(TAG, "Sync note failed. exception:" + innerException2);
            Logger.INSTANCE.e(TAG, "Try to recover sync error");
            accountEntity.fixSyncError(this.mContext);
            return true;
        }
        Logger.INSTANCE.e(TAG, "Try to update error count: " + accountEntity.getUnrecoverableCount());
        try {
            accountEntity.persist(this.mContext);
            return false;
        } catch (NoteSyncException e) {
            Logger.INSTANCE.e(TAG, "Fail to persist account " + e);
            return false;
        }
    }

    private void handleCommonException(AccountEntity accountEntity, NoteSyncException noteSyncException) throws CloudServerException {
        Exception innerException = noteSyncException.getInnerException();
        if (innerException == null) {
            Logger.INSTANCE.e(TAG, "Sync note failed. " + noteSyncException);
            this.mSyncResult.stats.numParseExceptions++;
            return;
        }
        if (innerException instanceof CloudServerException) {
            Logger.INSTANCE.e(TAG, "Sync note failed.exception:" + innerException);
            throw ((CloudServerException) innerException);
        }
        if (innerException instanceof ServerResponseException) {
            Logger.INSTANCE.e(TAG, "Sync note failed.exception:" + innerException);
            if (((ServerResponseException) innerException).getCode() == 52000) {
                Logger.INSTANCE.e(TAG, "Sync failed, need to resyncexception:" + innerException);
                NoteEntity.setupResync(this.mContext);
                FolderEntity.setupResync(this.mContext);
                accountEntity.setupResync(this.mContext);
                return;
            }
            return;
        }
        Logger.INSTANCE.e(TAG, "Sync note failed.exception:" + innerException);
        if (innerException instanceof IOException) {
            this.mSyncResult.stats.numIoExceptions++;
        } else {
            if (innerException instanceof AuthenticationFailureException) {
                this.mSyncResult.stats.numAuthExceptions++;
                throw new CloudServerException(401);
            }
            if (innerException instanceof AccessDeniedException) {
                this.mSyncResult.stats.numAuthExceptions++;
                throw new CloudServerException(403);
            }
            this.mSyncResult.stats.numParseExceptions++;
        }
    }

    private void updateWelcomeNotes() throws NoteSyncException {
        Uri uri = Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER;
        if (hasSyncedNotes()) {
            this.mContext.getContentResolver().delete(uri, "local_modified=0 AND sync_id=0 AND version=0", null);
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        this.mContext.getContentResolver().update(uri, contentValues, "local_modified=0 AND sync_id=0 AND version=0", null);
    }

    private boolean hasSyncedNotes() throws NoteSyncException {
        Cursor cursorQuery = this.mContext.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"_id"}, "sync_id>0", null, null);
        if (cursorQuery == null) {
            throw new NoteSyncException("Fail to check whether Synced Notes exists , cursor is null.");
        }
        try {
            return cursorQuery.getCount() > 0;
        } finally {
            cursorQuery.close();
        }
    }

    private void deleteTagedTrashNotes() {
        Logger.INSTANCE.i(TAG, "Delete notes whose deletion_tag is 1 or is empty notes in trash folder:" + this.mContext.getContentResolver().delete(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, "parent_id=? AND type=0 AND (deletion_tag= ? OR (snippet='' AND note_content_type is not 'mind' AND local_modified=0))", new String[]{String.valueOf(-3), String.valueOf(1)}));
    }

    private static ExtendedAuthToken transformAuthToken(com.xiaomi.micloudsdk.data.ExtendedAuthToken extendedAuthToken) {
        if (extendedAuthToken == null) {
            return null;
        }
        return ExtendedAuthToken.build(extendedAuthToken.authToken, extendedAuthToken.security);
    }

    @Override // android.content.AbstractThreadedSyncAdapter
    public void onSyncCanceled() {
        Logger.INSTANCE.d(TAG, "onSyncCanceled");
        super.onSyncCanceled();
        this.mCanceled = true;
        this.mTodoServiceAdapter.setSyncCanceled(true);
    }
}
