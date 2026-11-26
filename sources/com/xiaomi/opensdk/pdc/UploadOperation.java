package com.xiaomi.opensdk.pdc;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public interface UploadOperation extends SyncOperation {
    JSONObject getOperationJson() throws SyncException;

    public static class Result extends SyncOperation.Result {
        private final SyncRecord mConflictRecord;
        private final Constants.ConflictType mConflictType;
        private final SyncRecord mRecord;

        public Constants.ConflictType getConflictType() {
            return this.mConflictType;
        }

        public SyncRecord getRecord() {
            return this.mRecord;
        }

        public SyncRecord getConflictRecord() {
            return this.mConflictRecord;
        }

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, Constants.ConflictType conflictType, SyncRecord syncRecord, SyncRecord syncRecord2) {
            super(z, str, errorType, i, j, str2);
            this.mConflictType = conflictType;
            this.mRecord = syncRecord;
            this.mConflictRecord = syncRecord2;
        }
    }
}
