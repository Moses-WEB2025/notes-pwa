package com.xiaomi.opensdk.pdc;

import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import com.xiaomi.opensdk.pdc.asset.AssetException;
import com.xiaomi.opensdk.pdc.asset.AssetResult;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public interface SyncServerAdapter {
    BatchUploadOperation.Result batchUpload(BatchUploadOperation batchUploadOperation) throws SyncException;

    AssetResult commitAsset(String str, CommitParameter commitParameter) throws AssetException;

    UploadOperation.Result create(CreateOperation createOperation) throws SyncException;

    UploadOperation.Result delete(DeleteOperation deleteOperation) throws SyncException;

    DownloadOperation.Result download(DownloadOperation downloadOperation) throws SyncException;

    FetchChildrenOperation.Result fetchChildren(FetchChildrenOperation fetchChildrenOperation) throws SyncException;

    FetchRecordOperation.Result fetchRecord(FetchRecordOperation fetchRecordOperation) throws SyncException;

    FetchRecordsByIndexOperation.Result fetchRecordsByIndex(FetchRecordsByIndexOperation fetchRecordsByIndexOperation) throws SyncException;

    String getAppId();

    AssetResult getAssetUrl(String str, AssetEntity assetEntity) throws AssetException;

    JSONObject getCreateJson(CreateOperation createOperation) throws SyncException;

    JSONObject getDeleteJson(DeleteOperation deleteOperation) throws SyncException;

    String getSpaceId();

    JSONObject getUpdateJson(UpdateOperation updateOperation) throws SyncException;

    AssetResult requestDownload(String str, AssetEntity assetEntity) throws AssetException;

    AssetResult requestUploadAsset(String str, RequestUploadParameter requestUploadParameter) throws AssetException;

    UploadOperation.Result update(UpdateOperation updateOperation) throws SyncException;
}
