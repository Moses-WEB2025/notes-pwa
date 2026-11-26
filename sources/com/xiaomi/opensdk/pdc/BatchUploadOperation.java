package com.xiaomi.opensdk.pdc;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class BatchUploadOperation extends ArrayList<UploadOperation> implements SyncOperation {
    private static final long serialVersionUID = 1;
    private final SyncServerAdapter mSyncServer;

    public BatchUploadOperation(SyncServerAdapter syncServerAdapter) {
        if (syncServerAdapter == null) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public Result execute() throws SyncException {
        return this.mSyncServer.batchUpload(this);
    }

    public static class Result extends SyncOperation.Result {
        private ArrayList<UploadOperation.Result> mSubResults;

        public int getSubResultCount() {
            ArrayList<UploadOperation.Result> arrayList = this.mSubResults;
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        public UploadOperation.Result getSubResultAt(int i) {
            return this.mSubResults.get(i);
        }

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, ArrayList<UploadOperation.Result> arrayList) {
            super(z, str, errorType, i, j, str2);
            this.mSubResults = arrayList;
        }
    }
}
