package com.xiaomi.opensdk.pdc;

import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class FetchRecordsByIndexOperation implements SyncOperation {
    private final JSONObject mBeginKey;
    private final JSONObject mEndKey;
    private final String mIndexName;
    private final boolean mIsReverse;
    private final SyncServerAdapter mSyncServer;

    public FetchRecordsByIndexOperation(SyncServerAdapter syncServerAdapter, String str, JSONObject jSONObject, JSONObject jSONObject2, boolean z) {
        if (syncServerAdapter == null) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mBeginKey = jSONObject;
        this.mEndKey = jSONObject2;
        this.mIndexName = str;
        this.mIsReverse = z;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public Result execute() throws SyncException {
        return this.mSyncServer.fetchRecordsByIndex(this);
    }

    public SyncServerAdapter getServerConnector() {
        return this.mSyncServer;
    }

    public JSONObject getBeginKey() {
        return this.mBeginKey;
    }

    public JSONObject getEndKey() {
        return this.mEndKey;
    }

    public String getIndexName() {
        return this.mIndexName;
    }

    public boolean isReverse() {
        return this.mIsReverse;
    }

    public static class Result extends SyncOperation.Result {
        private final JSONObject mBeginKey;
        private final boolean mHasMore;
        private final ArrayList<SyncRecord> mRecords;

        public JSONObject getBeginKey() {
            return this.mBeginKey;
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

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, JSONObject jSONObject, boolean z2, ArrayList<SyncRecord> arrayList) {
            super(z, str, errorType, i, j, str2);
            this.mBeginKey = jSONObject;
            this.mHasMore = z2;
            this.mRecords = arrayList;
        }
    }
}
