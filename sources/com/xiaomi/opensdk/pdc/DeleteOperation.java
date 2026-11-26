package com.xiaomi.opensdk.pdc;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public final class DeleteOperation implements UploadOperation {
    private final long mETag;
    private final String mId;
    private JSONObject mOperationJson = null;
    private final SyncServerAdapter mSyncServer;
    private final String mType;

    public DeleteOperation(SyncServerAdapter syncServerAdapter, String str, String str2, long j) {
        if (syncServerAdapter == null || str == null || str2 == null || j == -1) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mType = str;
        this.mId = str2;
        this.mETag = j;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public UploadOperation.Result execute() throws SyncException {
        return this.mSyncServer.delete(this);
    }

    @Override // com.xiaomi.opensdk.pdc.UploadOperation
    public JSONObject getOperationJson() throws SyncException {
        if (this.mOperationJson == null) {
            this.mOperationJson = this.mSyncServer.getDeleteJson(this);
        }
        return this.mOperationJson;
    }

    public SyncServerAdapter getSyncServer() {
        return this.mSyncServer;
    }

    public String getType() {
        return this.mType;
    }

    public String getId() {
        return this.mId;
    }

    public long getETag() {
        return this.mETag;
    }
}
