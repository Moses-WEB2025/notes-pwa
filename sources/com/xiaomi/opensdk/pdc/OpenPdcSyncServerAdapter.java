package com.xiaomi.opensdk.pdc;

import android.util.Log;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import com.xiaomi.opensdk.pdc.asset.AssetException;
import com.xiaomi.opensdk.pdc.asset.AssetResult;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OpenPdcSyncServerAdapter implements SyncServerAdapter {
    private static final String TAG = "OpenPdcSyncServerAdapter";
    private final String mAppId;
    private final String mClientId;
    private final Environment mEnvironment;
    private final String mSpaceId;
    private final String mToken;

    public interface Environment {
        String getHost();

        String getUserAgent();

        String getUserCountry();

        void setHost(String str);
    }

    public OpenPdcSyncServerAdapter(Environment environment, String str, String str2, String str3, String str4) {
        this.mEnvironment = environment;
        this.mClientId = str;
        this.mToken = str2;
        this.mAppId = str3;
        this.mSpaceId = str4;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public String getAppId() {
        return this.mAppId;
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public String getSpaceId() {
        return this.mSpaceId;
    }

    private String getFullHost() {
        StringBuilder sb = new StringBuilder("https://");
        Environment environment = this.mEnvironment;
        return sb.append(environment == null ? "micpdc.openapi.xiaomi.net" : environment.getHost()).toString();
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result create(CreateOperation createOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        return OpenPdcUtils.parseUploadResult(OpenPdcRequest.request(this.mEnvironment, getFullHost() + getCreateRequestPath(), PassportSimpleRequest.HTTP_METHOD_POST, map, null, getCreateBodyJson(createOperation).toString()));
    }

    private String getCreateRequestPath() {
        return String.format("/mic/open/pdc/apps/%s/spaces/%s/records", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId));
    }

    private JSONObject getCreateBodyJson(CreateOperation createOperation) throws SyncException {
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
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("record", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "getCreateRecordJson error", e);
            throw new SyncException("getCreateRecordJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result delete(DeleteOperation deleteOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        map.put("eTag", Long.toString(deleteOperation.getETag()));
        map.put("type", deleteOperation.getType());
        return OpenPdcUtils.parseUploadResult(OpenPdcRequest.request(this.mEnvironment, getFullHost() + getDeleteRequestPath(deleteOperation), "DELETE", map, null, null));
    }

    private String getDeleteRequestPath(DeleteOperation deleteOperation) {
        return String.format("/mic/open/pdc/apps/%s/spaces/%s/records/%s", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId), URLEncoder.encode(deleteOperation.getId()));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public UploadOperation.Result update(UpdateOperation updateOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        return OpenPdcUtils.parseUploadResult(OpenPdcRequest.request(this.mEnvironment, getFullHost() + getUpdateRequestPath(updateOperation), "PUT", map, null, getUpdateBodyJson(updateOperation).toString()));
    }

    private String getUpdateRequestPath(UpdateOperation updateOperation) {
        return String.format("/mic/open/pdc/apps/%s/spaces/%s/records/%s", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId), URLEncoder.encode(updateOperation.getId()));
    }

    private JSONObject getUpdateBodyJson(UpdateOperation updateOperation) throws SyncException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", updateOperation.getType());
            jSONObject.putOpt("id", updateOperation.getId());
            jSONObject.putOpt("uniqueKey", updateOperation.getUniqueKey());
            jSONObject.putOpt(MiCloudConstants.PDC.J_PARENT_ID, updateOperation.getParentId());
            jSONObject.put(MiCloudConstants.PDC.J_CONTENT_JSON, updateOperation.getContentJson());
            if (updateOperation.getAssetList() != null && updateOperation.getAssetList().length() != 0) {
                jSONObject.put(MiCloudConstants.PDC.J_ASSET_LIST, updateOperation.getAssetList());
            }
            jSONObject.put("status", "normal");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("eTag", updateOperation.getETag());
            jSONObject2.put("record", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "getUpdateBodyJson error", e);
            throw new SyncException("getUpdateBodyJson error");
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public DownloadOperation.Result download(DownloadOperation downloadOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        if (downloadOperation.getSyncToken() != null) {
            map.put("syncToken", downloadOperation.getSyncToken());
        }
        map.put("limit", String.valueOf(200));
        return OpenPdcUtils.parseDownloadResult(OpenPdcRequest.request(this.mEnvironment, String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId)), PassportSimpleRequest.HTTP_METHOD_GET, map, null, null));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchRecordOperation.Result fetchRecord(FetchRecordOperation fetchRecordOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        return OpenPdcUtils.parseFetchRecordResult(OpenPdcRequest.request(this.mEnvironment, String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/%s", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId), URLEncoder.encode(fetchRecordOperation.getId())), PassportSimpleRequest.HTTP_METHOD_GET, map, null, null));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchChildrenOperation.Result fetchChildren(FetchChildrenOperation fetchChildrenOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        map.put("type", fetchChildrenOperation.getParentType());
        if (fetchChildrenOperation.getCursor() != null) {
            map.put("cursor", fetchChildrenOperation.getCursor());
        }
        map.put("limit", String.valueOf(200));
        return OpenPdcUtils.parseFetchChildrenResult(OpenPdcRequest.request(this.mEnvironment, String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/%s/children", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId), URLEncoder.encode(fetchChildrenOperation.getParentId())), PassportSimpleRequest.HTTP_METHOD_GET, map, null, null));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public FetchRecordsByIndexOperation.Result fetchRecordsByIndex(FetchRecordsByIndexOperation fetchRecordsByIndexOperation) throws SyncException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        map.put(MiCloudConstants.PDC.PARAM_INDEX_NAME, fetchRecordsByIndexOperation.getIndexName());
        map.put("beginKey", fetchRecordsByIndexOperation.getBeginKey().toString());
        map.put(MiCloudConstants.PDC.PARAM_END_KEY, fetchRecordsByIndexOperation.getEndKey().toString());
        map.put(MiCloudConstants.PDC.PARAM_IS_REVERSE, Boolean.toString(fetchRecordsByIndexOperation.isReverse()));
        map.put("limit", String.valueOf(200));
        return OpenPdcUtils.parseFetchRecordsByIndexResult(OpenPdcRequest.request(this.mEnvironment, String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/index", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId)), PassportSimpleRequest.HTTP_METHOD_GET, map, null, null));
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getCreateJson(CreateOperation createOperation) throws SyncException {
        throw new NoSuchMethodError("OpenPDC doesn't support batch operations");
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getDeleteJson(DeleteOperation deleteOperation) throws SyncException {
        throw new NoSuchMethodError("OpenPDC doesn't support batch operations");
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public JSONObject getUpdateJson(UpdateOperation updateOperation) throws SyncException {
        throw new NoSuchMethodError("OpenPDC doesn't support batch operations");
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public BatchUploadOperation.Result batchUpload(BatchUploadOperation batchUploadOperation) throws SyncException {
        ArrayList arrayList = new ArrayList(batchUploadOperation.size());
        for (int i = 0; i < batchUploadOperation.size(); i++) {
            arrayList.add((UploadOperation.Result) batchUploadOperation.get(i).execute());
        }
        return new BatchUploadOperation.Result(true, null, Constants.ErrorType.OK, 0, 0L, null, arrayList);
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult requestUploadAsset(String str, RequestUploadParameter requestUploadParameter) throws AssetException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        String str2 = String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/assets/upload", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(FileSDKUtils.J_STORAGE, requestUploadParameter.toJsonObject());
            jSONObject.put("type", str);
            OpenPdcRequest.SyncOperationResult syncOperationResultRequest = OpenPdcRequest.request(this.mEnvironment, str2, PassportSimpleRequest.HTTP_METHOD_POST, map, null, jSONObject.toString());
            return new AssetResult(syncOperationResultRequest.isSuccess, syncOperationResultRequest.description, syncOperationResultRequest.errorType, syncOperationResultRequest.code, syncOperationResultRequest.retryTime, syncOperationResultRequest.result, syncOperationResultRequest.code, syncOperationResultRequest.data);
        } catch (SyncException e) {
            Log.e(TAG, "requestUploadAsset SyncException", e);
            throw new AssetException("SyncException in requestUploadAsset", e);
        } catch (JSONException e2) {
            Log.e(TAG, "JSONException in requestUploadAsset", e2);
            throw new AssetException("JSONException in getAssetRequsetUploadBodyJson", e2);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult commitAsset(String str, CommitParameter commitParameter) throws AssetException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        String str2 = String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/assets/commit", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(FileSDKUtils.J_STORAGE, commitParameter.toJsonObject());
            jSONObject.put("type", str);
            OpenPdcRequest.SyncOperationResult syncOperationResultRequest = OpenPdcRequest.request(this.mEnvironment, str2, PassportSimpleRequest.HTTP_METHOD_POST, map, null, jSONObject.toString());
            return new AssetResult(syncOperationResultRequest.isSuccess, syncOperationResultRequest.description, syncOperationResultRequest.errorType, syncOperationResultRequest.code, syncOperationResultRequest.retryTime, syncOperationResultRequest.result, syncOperationResultRequest.code, syncOperationResultRequest.data);
        } catch (SyncException e) {
            Log.e(TAG, "commitAsset SyncException", e);
            throw new AssetException("SyncException in commitAsset", e);
        } catch (JSONException e2) {
            Log.e(TAG, "JSONException in requestUploadAsset", e2);
            throw new AssetException("JSONException in getAssetRequsetUploadBodyJson", e2);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult requestDownload(String str, AssetEntity assetEntity) throws AssetException {
        HashMap map = new HashMap();
        map.put("clientId", this.mClientId);
        map.put(com.xiaomi.verificationsdk.internal.Constants.VERIFY_SUCESS_TOKEN, this.mToken);
        map.put("type", assetEntity.type);
        map.put("id", str);
        try {
            OpenPdcRequest.SyncOperationResult syncOperationResultRequest = OpenPdcRequest.request(this.mEnvironment, String.format(getFullHost() + "/mic/open/pdc/apps/%s/spaces/%s/records/assets/%s", URLEncoder.encode(this.mAppId), URLEncoder.encode(this.mSpaceId), URLEncoder.encode(assetEntity.assetID)), PassportSimpleRequest.HTTP_METHOD_GET, map, null, null);
            return new AssetResult(syncOperationResultRequest.isSuccess, syncOperationResultRequest.description, syncOperationResultRequest.errorType, syncOperationResultRequest.code, syncOperationResultRequest.retryTime, syncOperationResultRequest.result, syncOperationResultRequest.code, syncOperationResultRequest.data);
        } catch (SyncException e) {
            Log.e(TAG, "requestDownload SyncException", e);
            throw new AssetException("SyncException in requestDownload", e);
        }
    }

    @Override // com.xiaomi.opensdk.pdc.SyncServerAdapter
    public AssetResult getAssetUrl(String str, AssetEntity assetEntity) throws AssetException {
        throw new AssetException("方法未实现", Constants.ErrorType.UNRETRIABLE_ERROR, -1, false, 0L);
    }
}
