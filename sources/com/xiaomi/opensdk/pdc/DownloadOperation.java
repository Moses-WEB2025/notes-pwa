package com.xiaomi.opensdk.pdc;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class DownloadOperation implements SyncOperation {
    private final SyncServerAdapter mSyncServer;
    private final String mSyncToken;

    public DownloadOperation(SyncServerAdapter syncServerAdapter, String str) {
        if (syncServerAdapter == null) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mSyncToken = str;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public Result execute() throws SyncException {
        return this.mSyncServer.download(this);
    }

    public SyncServerAdapter getServerConnector() {
        return this.mSyncServer;
    }

    public String getSyncToken() {
        return this.mSyncToken;
    }

    public static class Result extends SyncOperation.Result {
        private boolean mHasMore;
        private ArrayList<SyncRecord> mRecords;
        private String mSyncToken;
        private long mWatermark;

        public long getWatermark() {
            return this.mWatermark;
        }

        public String getSyncToken() {
            return this.mSyncToken;
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

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, long j2, String str3, boolean z2, ArrayList<SyncRecord> arrayList) {
            super(z, str, errorType, i, j, str2);
            this.mWatermark = j2;
            this.mSyncToken = str3;
            this.mHasMore = z2;
            this.mRecords = arrayList;
        }
    }
}
