package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes4.dex */
public class FetchRecordOperation implements SyncOperation {
    private final String mId;
    private final SyncServerAdapter mSyncServer;

    public FetchRecordOperation(SyncServerAdapter syncServerAdapter, String str) {
        if (syncServerAdapter == null || str == null) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mId = str;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public Result execute() throws SyncException {
        return this.mSyncServer.fetchRecord(this);
    }

    public SyncServerAdapter getServerConnector() {
        return this.mSyncServer;
    }

    public String getId() {
        return this.mId;
    }

    public static class Result extends SyncOperation.Result {
        private final SyncRecord mRecord;

        public SyncRecord getRecord() {
            return this.mRecord;
        }

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, SyncRecord syncRecord) {
            super(z, str, errorType, i, j, str2);
            this.mRecord = syncRecord;
        }
    }
}
