package com.xiaomi.opensdk.pdc;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class FetchChildrenOperation implements SyncOperation {
    private final String mCursor;
    private final String mParentId;
    private final String mParentType;
    private final SyncServerAdapter mSyncServer;

    public FetchChildrenOperation(SyncServerAdapter syncServerAdapter, String str, String str2, String str3) {
        if (syncServerAdapter == null) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mParentType = str;
        this.mParentId = str2;
        this.mCursor = str3;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public Result execute() throws SyncException {
        return this.mSyncServer.fetchChildren(this);
    }

    public SyncServerAdapter getServerConnector() {
        return this.mSyncServer;
    }

    public String getParentType() {
        return this.mParentType;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public String getCursor() {
        return this.mCursor;
    }

    public static class Result extends SyncOperation.Result {
        private final String mCursor;
        private final boolean mHasMore;
        private final ArrayList<SyncRecord> mRecords;

        public String getCursor() {
            return this.mCursor;
        }

        public boolean hasMore() {
            return this.mHasMore;
        }

        public int getRecordCount() {
            ArrayList<SyncRecord> arrayList = this.mRecords;
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        public SyncRecord getRecordAt(int i) {
            return this.mRecords.get(i);
        }

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, String str3, boolean z2, ArrayList<SyncRecord> arrayList) {
            super(z, str, errorType, i, j, str2);
            this.mCursor = str3;
            this.mHasMore = z2;
            this.mRecords = arrayList;
        }
    }
}
