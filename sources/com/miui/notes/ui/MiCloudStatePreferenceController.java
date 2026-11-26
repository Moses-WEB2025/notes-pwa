package com.miui.notes.ui;

import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import miuix.micloudview.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudStatePreferenceController {
    private Context mContext = NoteApp.getInstance();
    private MiCloudPreferenceView mMiCloudStateView;
    private boolean mResumed;

    public MiCloudStatePreferenceController(MiCloudPreferenceView miCloudPreferenceView) {
        this.mMiCloudStateView = miCloudPreferenceView;
        miCloudPreferenceView.setSyncInfoProvider(new NoteSyncInfoProvider(this.mContext));
        this.mMiCloudStateView.setDisabledStatusText(this.mContext.getResources().getString(R.string.new_sync_desc_state_disable));
    }

    public void onResume() {
        this.mResumed = true;
        this.mMiCloudStateView.registerObserver();
        this.mMiCloudStateView.updateState(true);
    }

    public void onPause() {
        this.mResumed = false;
        this.mMiCloudStateView.unregisterObserver();
    }

    public void onDataSetChanged() {
        this.mMiCloudStateView.updateState();
    }

    private static class NoteSyncInfoProvider implements MiCloudPreferenceView.ISyncInfoProvider {
        private Context mContext;
        private int mNoteCount;
        private int mNoteFolderCount;
        private int mTodoCount;

        public NoteSyncInfoProvider(Context context) {
            this.mContext = context;
        }

        @Override // com.miui.notes.ui.MiCloudPreferenceView.ISyncInfoProvider
        public Account getXiaomiAccount() {
            return ExtraAccountManager.getXiaomiAccount(this.mContext);
        }

        @Override // com.miui.notes.ui.MiCloudPreferenceView.ISyncInfoProvider
        public String getAuthority() {
            if (PermissionUtils.supportNewPermissionStyle()) {
                return PreferenceUtils.getAllowNetwork(NoteApp.getInstance()) ? "notes" : "";
            }
            return PreferenceUtils.isCTAAccepted(this.mContext) ? "notes" : "";
        }

        @Override // com.miui.notes.ui.MiCloudPreferenceView.ISyncInfoProvider
        public int[] getUnsyncedCount(Context context) {
            int[] unsyncedCount = Notes.Utils.getUnsyncedCount(context);
            Cursor cursorQueryNeedShowItem = TodoProviderAccessUtils.queryNeedShowItem();
            int[] iArr = {unsyncedCount[0], unsyncedCount[1], cursorQueryNeedShowItem.getCount()};
            cursorQueryNeedShowItem.close();
            return iArr;
        }

        @Override // com.miui.notes.ui.MiCloudPreferenceView.ISyncInfoProvider
        public String getUnsyncedCountText(Context context, int[] iArr) {
            return createUnsyncedCountText(context, iArr);
        }

        @Override // com.miui.notes.ui.MiCloudPreferenceView.ISyncInfoProvider
        public String getSyncText(MiCloudPreferenceView.SyncState syncState) {
            if (syncState == MiCloudPreferenceView.SyncState.SYNC_PRE_START) {
                Context context = this.mContext;
                return createUnsyncedCountText(context, getUnsyncedCount(context));
            }
            if (syncState == MiCloudPreferenceView.SyncState.SYNC_RUNNING) {
                return this.mContext.getString(R.string.sync_desc_state_running);
            }
            if (syncState == MiCloudPreferenceView.SyncState.SYNC_COMPLETE) {
                return createSyncedText(this.mContext);
            }
            return null;
        }

        private String createUnsyncedCountText(Context context, int[] iArr) {
            int i = iArr[0];
            int i2 = iArr[1];
            int i3 = iArr[2];
            int i4 = i + i2 + i3;
            if (Utils.isUseChinese()) {
                return context.getResources().getQuantityString(R.plurals.cloud_state_unsynced_count, i4, Utils.getAllSyncItemTerms(i, i2, i3));
            }
            return context.getResources().getQuantityString(R.plurals.cloud_state_unsynced_count, i4, Integer.valueOf(i4));
        }

        private String createSyncedText(Context context) {
            updateLocalDataCount(context);
            if (Utils.isUseChinese()) {
                return context.getString(R.string.sync_desc_state_complete_china, Utils.getAllSyncItemTerms(this.mNoteCount, this.mNoteFolderCount, this.mTodoCount));
            }
            return context.getString(R.string.sync_desc_state_complete);
        }

        private void updateLocalDataCount(Context context) {
            this.mNoteFolderCount = 0;
            this.mNoteCount = 0;
            Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"_id", "type"}, "(parent_id>=0 OR parent_id=-2 OR parent_id=-4 OR parent_id=-5 OR parent_id=-6 OR parent_id=-7  OR parent_id=-8) AND type!=2", null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        int i = cursorQuery.getInt(1);
                        if (i == 1) {
                            this.mNoteFolderCount++;
                        } else if (i == 0) {
                            this.mNoteCount++;
                        }
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                cursorQuery.close();
            }
            Cursor cursorQueryAllItem = TodoProviderAccessUtils.queryAllItem();
            this.mTodoCount = cursorQueryAllItem.getCount();
            cursorQueryAllItem.close();
        }
    }
}
