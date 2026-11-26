package com.xiaomi.micloudsdk.pdc;

import android.util.Log;
import com.xiaomi.micloudsdk.pdc.utils.Pdc4Utils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import com.xiaomi.opensdk.pdc.BatchUploadOperation;
import com.xiaomi.opensdk.pdc.CreateOperation;
import com.xiaomi.opensdk.pdc.DeleteOperation;
import com.xiaomi.opensdk.pdc.DownloadOperation;
import com.xiaomi.opensdk.pdc.FetchChildrenOperation;
import com.xiaomi.opensdk.pdc.FetchRecordOperation;
import com.xiaomi.opensdk.pdc.FetchRecordsByIndexOperation;
import com.xiaomi.opensdk.pdc.SyncException;
import com.xiaomi.opensdk.pdc.SyncServerAdapter;
import com.xiaomi.opensdk.pdc.UpdateOperation;
import com.xiaomi.opensdk.pdc.UploadOperation;
import com.xiaomi.opensdk.pdc.asset.AssetConsts;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import com.xiaomi.opensdk.pdc.asset.AssetException;
import com.xiaomi.opensdk.pdc.asset.AssetResult;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class Pdc4SyncServerAdapter implements SyncServerAdapter {
    private static final String TAG = "Pdc4SyncServerAdapter";
    private final String mAppId;
    private final String mSpaceId;
    private Pdc4SyncServerUrlGenerator mUrlGenerator;

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public String getAppId() {
        return this.mAppId;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public String getSpaceId() {
        return this.mSpaceId;
    }

    public Pdc4SyncServerAdapter(String str, String str2) {
        this.mAppId = str;
        this.mSpaceId = str2;
        this.mUrlGenerator = new Pdc4SyncServerUrlGenerator();
    }

    public Pdc4SyncServerAdapter(String str, String str2, Pdc4SyncServerUrlGenerator pdc4SyncServerUrlGenerator) {
        this.mAppId = str;
        this.mSpaceId = str2;
        this.mUrlGenerator = pdc4SyncServerUrlGenerator;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getCreateJson(CreateOperation createOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("record", getCreateRecordJson(createOperation));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("path", this.mUrlGenerator.getCreateRequestPath(this.mAppId, this.mSpaceId));
            jSONObject2.put("params", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "getCreateJson error", e);
            throw new SyncException("getCreateJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getDeleteJson(DeleteOperation deleteOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", deleteOperation.getType());
            jSONObject.put("id", deleteOperation.getId());
            jSONObject.put("eTag", deleteOperation.getETag());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("path", this.mUrlGenerator.getDeleteRequestPath(this.mAppId, this.mSpaceId, deleteOperation));
            jSONObject2.put("params", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "getDeleteJson error", e);
            throw new SyncException("getDeleteJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getUpdateJson(UpdateOperation updateOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("record", getUpdateRecordJson(updateOperation));
            jSONObject.put("eTag", updateOperation.getETag());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("path", this.mUrlGenerator.getUpdateRequestPath(this.mAppId, this.mSpaceId, updateOperation));
            jSONObject2.put("params", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "getUpdateJson error", e);
            throw new SyncException("getUpdateJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result create(CreateOperation createOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("record", getCreateRecordJson(createOperation).toString());
        return Pdc4Utils.parseUploadResult(Pdc4Utils.requestServer(false, this.mUrlGenerator.getCreateRequestUrl(this.mAppId, this.mSpaceId), map));
    }

    private JSONObject getCreateRecordJson(CreateOperation createOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", createOperation.getType());
            jSONObject.putOpt("id", createOperation.getId());
            jSONObject.putOpt("uniqueKey", createOperation.getUniqueKey());
            jSONObject.putOpt(MiCloudConstants.PDC.J_PARENT_ID, createOperation.getParentId());
            jSONObject.put(MiCloudConstants.PDC.J_CONTENT_JSON, createOperation.getContentJson());
            if (createOperation.getAssetList() != null && createOperation.getAssetList().length() != 0) {
                jSONObject.put(MiCloudConstants.PDC.J_ASSET_LIST, createOperation.getAssetList());
            }
            jSONObject.put("status", "normal");
            return jSONObject;
        } catch (JSONException e) {
            Log.e(TAG, "getCreateRecordJson error", e);
            throw new SyncException("getCreateRecordJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result delete(DeleteOperation deleteOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("type", deleteOperation.getType());
        map.put("id", deleteOperation.getId());
        map.put("eTag", Long.toString(deleteOperation.getETag()));
        return Pdc4Utils.parseUploadResult(Pdc4Utils.requestServer(false, this.mUrlGenerator.getDeleteRequestUrl(this.mAppId, this.mSpaceId, deleteOperation), map));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result update(UpdateOperation updateOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("record", getUpdateRecordJson(updateOperation).toString());
        map.put("eTag", Long.toString(updateOperation.getETag()));
        return Pdc4Utils.parseUploadResult(Pdc4Utils.requestServer(false, this.mUrlGenerator.getUpdateRequestUrl(this.mAppId, this.mSpaceId, updateOperation), map));
    }

    private JSONObject getUpdateRecordJson(UpdateOperation updateOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", updateOperation.getType());
            jSONObject.put("id", updateOperation.getId());
            jSONObject.putOpt("uniqueKey", updateOperation.getUniqueKey());
            jSONObject.putOpt(MiCloudConstants.PDC.J_PARENT_ID, updateOperation.getParentId());
            jSONObject.put(MiCloudConstants.PDC.J_CONTENT_JSON, updateOperation.getContentJson());
            if (updateOperation.getAssetList() != null && updateOperation.getAssetList().length() != 0) {
                jSONObject.put(MiCloudConstants.PDC.J_ASSET_LIST, updateOperation.getAssetList());
            }
            jSONObject.put("status", "normal");
            return jSONObject;
        } catch (JSONException e) {
            Log.e(TAG, "getUpdateRecordJson error", e);
            throw new SyncException("getUpdateRecordJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public DownloadOperation.Result download(DownloadOperation downloadOperation) throws SyncException {
        HashMap map = new HashMap();
        if (downloadOperation.getSyncToken() != null) {
            map.put("syncToken", downloadOperation.getSyncToken());
        }
        map.put("limit", String.valueOf(200));
        return Pdc4Utils.parseDownloadResult(Pdc4Utils.requestServer(true, this.mUrlGenerator.getDownloadRequestUrl(this.mAppId, this.mSpaceId), map));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public BatchUploadOperation.Result batchUpload(BatchUploadOperation batchUploadOperation) throws SyncException {
        HashMap map = new HashMap();
        JSONArray jSONArray = new JSONArray();
        Iterator<UploadOperation> it = batchUploadOperation.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().getOperationJson());
        }
        map.put("batchContent", jSONArray.toString());
        return Pdc4Utils.parseBatchUploadResult(Pdc4Utils.requestServer(false, this.mUrlGenerator.getBatchUploadRequestUrl(this.mAppId, this.mSpaceId), map));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchRecordOperation.Result fetchRecord(FetchRecordOperation fetchRecordOperation) throws SyncException {
        return Pdc4Utils.parseFetchRecordResult(Pdc4Utils.requestServer(true, this.mUrlGenerator.getFetchRecordRequestUrl(this.mAppId, this.mSpaceId, fetchRecordOperation), new HashMap()));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchChildrenOperation.Result fetchChildren(FetchChildrenOperation fetchChildrenOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("type", fetchChildrenOperation.getParentType());
        if (fetchChildrenOperation.getCursor() != null) {
            map.put("cursor", fetchChildrenOperation.getCursor());
        }
        map.put("limit", String.valueOf(200));
        return Pdc4Utils.parseFetchChildrenResult(Pdc4Utils.requestServer(true, this.mUrlGenerator.getFetchChildrenRequestUrl(this.mAppId, this.mSpaceId, fetchChildrenOperation), map));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchRecordsByIndexOperation.Result fetchRecordsByIndex(FetchRecordsByIndexOperation fetchRecordsByIndexOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put(MiCloudConstants.PDC.PARAM_INDEX_NAME, fetchRecordsByIndexOperation.getIndexName());
        map.put("beginKey", fetchRecordsByIndexOperation.getBeginKey().toString());
        map.put(MiCloudConstants.PDC.PARAM_END_KEY, fetchRecordsByIndexOperation.getEndKey().toString());
        map.put(MiCloudConstants.PDC.PARAM_IS_REVERSE, Boolean.toString(fetchRecordsByIndexOperation.isReverse()));
        map.put("limit", String.valueOf(200));
        return Pdc4Utils.parseFetchRecordsByIndexResult(Pdc4Utils.requestServer(true, this.mUrlGenerator.getFetchRecordsByIndexRequestUrl(this.mAppId, this.mSpaceId), map));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult requestUploadAsset(String str, RequestUploadParameter requestUploadParameter) throws AssetException {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map.put("type", str);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(FileSDKUtils.J_STORAGE, requestUploadParameter.toJsonObject());
            map.put(AssetConsts.J_FULL_JSON, jSONObject.toString());
            map2.put("X-SUPPORT-HTTPS", "true");
            Log.d(TAG, "requestUploadAsset:" + map);
            try {
                Pdc4Utils.SyncOperationResult syncOperationResultRequestServer = Pdc4Utils.requestServer(false, this.mUrlGenerator.getRequestUploadAssetRequestUrl(this.mAppId, this.mSpaceId), map2, map);
                return new AssetResult(syncOperationResultRequestServer.isSuccess, syncOperationResultRequestServer.description, syncOperationResultRequestServer.errorType, syncOperationResultRequestServer.code, syncOperationResultRequestServer.retryTime, syncOperationResultRequestServer.result, syncOperationResultRequestServer.code, syncOperationResultRequestServer.data);
            } catch (SyncException e) {
                Log.e(TAG, "requestUploadAsset SyncException", e);
                throw new AssetException("SyncException in requestUploadAsset", e);
            }
        } catch (JSONException e2) {
            throw new AssetException("JSONException in requestUploadAsset", e2);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult commitAsset(String str, CommitParameter commitParameter) throws AssetException {
        HashMap map = new HashMap();
        map.put("type", str);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(FileSDKUtils.J_STORAGE, commitParameter.toJsonObject());
            map.put(AssetConsts.J_FULL_JSON, jSONObject.toString());
            Log.d(TAG, "commitAsset:" + map);
            try {
                Pdc4Utils.SyncOperationResult syncOperationResultRequestServer = Pdc4Utils.requestServer(false, this.mUrlGenerator.getCommitAssetRequestUrl(this.mAppId, this.mSpaceId), map);
                return new AssetResult(syncOperationResultRequestServer.isSuccess, syncOperationResultRequestServer.description, syncOperationResultRequestServer.errorType, syncOperationResultRequestServer.code, syncOperationResultRequestServer.retryTime, syncOperationResultRequestServer.result, syncOperationResultRequestServer.code, syncOperationResultRequestServer.data);
            } catch (SyncException e) {
                Log.e(TAG, "commitAsset SyncException", e);
                throw new AssetException("SyncException in commitAsset", e);
            }
        } catch (JSONException e2) {
            throw new AssetException("JSONException in commitAsset", e2);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult requestDownload(String str, AssetEntity assetEntity) throws AssetException {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map.put("type", assetEntity.type);
        map.put("id", str);
        map2.put("X-SUPPORT-HTTPS", "true");
        Log.d(TAG, "requestDownload:" + map);
        try {
            Pdc4Utils.SyncOperationResult syncOperationResultRequestServer = Pdc4Utils.requestServer(true, this.mUrlGenerator.getRequestDownloadRequestUrl(this.mAppId, this.mSpaceId, assetEntity), map2, map);
            return new AssetResult(syncOperationResultRequestServer.isSuccess, syncOperationResultRequestServer.description, syncOperationResultRequestServer.errorType, syncOperationResultRequestServer.code, syncOperationResultRequestServer.retryTime, syncOperationResultRequestServer.result, syncOperationResultRequestServer.code, syncOperationResultRequestServer.data);
        } catch (SyncException e) {
            Log.e(TAG, "requestDownload SyncException", e);
            throw new AssetException("SyncException in requestDownload", e);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult getAssetUrl(String str, AssetEntity assetEntity) throws AssetException {
        HashMap map = new HashMap();
        map.put("type", assetEntity.type);
        map.put("id", str);
        Log.d(TAG, "getAssetUrl:" + map);
        try {
            Pdc4Utils.SyncOperationResult syncOperationResultRequestServer = Pdc4Utils.requestServer(true, this.mUrlGenerator.getAssetUrlRequestUrl(this.mAppId, this.mSpaceId, assetEntity), map);
            return new AssetResult(syncOperationResultRequestServer.isSuccess, syncOperationResultRequestServer.description, syncOperationResultRequestServer.errorType, syncOperationResultRequestServer.code, syncOperationResultRequestServer.retryTime, syncOperationResultRequestServer.result, syncOperationResultRequestServer.code, syncOperationResultRequestServer.data);
        } catch (SyncException e) {
            Log.e(TAG, "getAssetUrl SyncException", e);
            throw new AssetException("SyncException in getAssetUrl", e);
        }
    }
}
