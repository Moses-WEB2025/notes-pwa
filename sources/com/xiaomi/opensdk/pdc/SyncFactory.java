package com.xiaomi.opensdk.pdc;

import android.util.Log;
import com.xiaomi.opensdk.pdc.asset.AssetConsts;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SyncFactory {
    private static final String TAG = "SyncFactory";
    public final SyncServerAdapter mSyncServer;

    public SyncFactory(SyncServerAdapter syncServerAdapter) {
        this.mSyncServer = syncServerAdapter;
    }

    public DeleteOperation newDeleteOp(String str, String str2, long j) {
        return new DeleteOperation(this.mSyncServer, str, str2, j);
    }

    public CreateOperation newCreateOp(String str, String str2, String str3, String str4, JSONObject jSONObject) {
        return new CreateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject);
    }

    public CreateOperation newCreateOp(String str, String str2, String str3, String str4, JSONObject jSONObject, AssetEntity... assetEntityArr) {
        return new CreateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, getAssetJsonArray(assetEntityArr));
    }

    public UpdateOperation newUpdateOp(String str, String str2, String str3, String str4, JSONObject jSONObject, long j) {
        return new UpdateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, j);
    }

    public UpdateOperation newUpdateOp(String str, String str2, String str3, String str4, JSONObject jSONObject, long j, AssetEntity... assetEntityArr) {
        return new UpdateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, j, getAssetJsonArray(assetEntityArr));
    }

    public BatchUploadOperation newBatchUploadOp() {
        return new BatchUploadOperation(this.mSyncServer);
    }

    public UploadOperation.Result delete(String str, String str2, long j) throws SyncException {
        return new DeleteOperation(this.mSyncServer, str, str2, j).execute();
    }

    public UploadOperation.Result create(String str, String str2, String str3, String str4, JSONObject jSONObject) throws SyncException {
        return new CreateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject).execute();
    }

    public UploadOperation.Result create(String str, String str2, String str3, String str4, JSONObject jSONObject, AssetEntity... assetEntityArr) throws SyncException {
        return new CreateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, getAssetJsonArray(assetEntityArr)).execute();
    }

    public UploadOperation.Result update(String str, String str2, String str3, String str4, JSONObject jSONObject, long j) throws SyncException {
        return new UpdateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, j).execute();
    }

    public UploadOperation.Result update(String str, String str2, String str3, String str4, JSONObject jSONObject, long j, AssetEntity... assetEntityArr) throws SyncException {
        return new UpdateOperation(this.mSyncServer, str, str2, str3, str4, jSONObject, j, getAssetJsonArray(assetEntityArr)).execute();
    }

    private JSONArray getAssetJsonArray(AssetEntity... assetEntityArr) {
        if (assetEntityArr == null) {
            Log.w(TAG, "getAssetJsonArray() with null argument, handled.");
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (AssetEntity assetEntity : assetEntityArr) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AssetConsts.J_ASSET_ID, assetEntity.assetID);
                jSONObject.put("size", assetEntity.size);
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException in getAssetJsonArray()", e);
                jSONArray = null;
            }
        }
        return jSONArray;
    }

    public DownloadOperation.Result download(String str) throws SyncException {
        return new DownloadOperation(this.mSyncServer, str).execute();
    }

    public FetchRecordOperation.Result fetchRecord(String str) throws SyncException {
        return new FetchRecordOperation(this.mSyncServer, str).execute();
    }

    public FetchChildrenOperation.Result fetchChildren(String str, String str2, String str3) throws SyncException {
        return new FetchChildrenOperation(this.mSyncServer, str, str2, str3).execute();
    }

    public FetchRecordsByIndexOperation.Result fetchRecordsByIndex(String str, JSONObject jSONObject, JSONObject jSONObject2, boolean z) throws SyncException {
        return new FetchRecordsByIndexOperation(this.mSyncServer, str, jSONObject, jSONObject2, z).execute();
    }
}
