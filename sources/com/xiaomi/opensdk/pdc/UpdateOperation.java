package com.xiaomi.opensdk.pdc;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public final class UpdateOperation implements UploadOperation {
    private final JSONArray mAssetList;
    private final JSONObject mContentJson;
    private final long mETag;
    private final String mId;
    private JSONObject mOperationJson;
    private final String mParentId;
    private final SyncServerAdapter mSyncServer;
    private final String mType;
    private final String mUniqueKey;

    public UpdateOperation(SyncServerAdapter syncServerAdapter, String str, String str2, String str3, String str4, JSONObject jSONObject, long j) {
        this(syncServerAdapter, str, str2, str3, str4, jSONObject, j, null);
    }

    public UpdateOperation(SyncServerAdapter syncServerAdapter, String str, String str2, String str3, String str4, JSONObject jSONObject, long j, JSONArray jSONArray) {
        this.mOperationJson = null;
        if (syncServerAdapter == null || str == null || str2 == null || jSONObject == null || j == -1) {
            throw new IllegalArgumentException("null parameters");
        }
        this.mSyncServer = syncServerAdapter;
        this.mType = str;
        this.mId = str2;
        this.mUniqueKey = str3;
        this.mParentId = str4;
        this.mContentJson = jSONObject;
        this.mETag = j;
        this.mAssetList = jSONArray;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncOperation
    public UploadOperation.Result execute() throws SyncException {
        return this.mSyncServer.update(this);
    }

    @Override // com.xiaomi.opensdk.pdc.UploadOperation
    public JSONObject getOperationJson() throws SyncException {
        if (this.mOperationJson == null) {
            this.mOperationJson = this.mSyncServer.getUpdateJson(this);
        }
        return this.mOperationJson;
    }

    public SyncServerAdapter getServerConnector() {
        return this.mSyncServer;
    }

    public String getType() {
        return this.mType;
    }

    public String getId() {
        return this.mId;
    }

    public String getUniqueKey() {
        return this.mUniqueKey;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public JSONObject getContentJson() {
        return this.mContentJson;
    }

    public long getETag() {
        return this.mETag;
    }

    public JSONArray getAssetList() {
        return this.mAssetList;
    }
}
