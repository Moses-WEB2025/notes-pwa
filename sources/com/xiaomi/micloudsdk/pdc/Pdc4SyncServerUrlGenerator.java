package com.xiaomi.micloudsdk.pdc;

import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.pdc.DeleteOperation;
import com.xiaomi.opensdk.pdc.FetchChildrenOperation;
import com.xiaomi.opensdk.pdc.FetchRecordOperation;
import com.xiaomi.opensdk.pdc.UpdateOperation;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes4.dex */
public class Pdc4SyncServerUrlGenerator {
    public String getCreateRequestUrl(String str, String str2) {
        return MiCloudConstants.PDC.URL_SYNC_BASE + getCreateRequestPath(str, str2);
    }

    public String getCreateRequestPath(String str, String str2) {
        return String.format("/mic/pdc/v4.1/apps/%s/spaces/%s/records", URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getDeleteRequestUrl(String str, String str2, DeleteOperation deleteOperation) {
        return MiCloudConstants.PDC.URL_SYNC_BASE + getDeleteRequestPath(str, str2, deleteOperation);
    }

    public String getDeleteRequestPath(String str, String str2, DeleteOperation deleteOperation) {
        return String.format(MiCloudConstants.PDC.DELETE_RECORD_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(deleteOperation.getId()));
    }

    public String getUpdateRequestUrl(String str, String str2, UpdateOperation updateOperation) {
        return MiCloudConstants.PDC.URL_SYNC_BASE + getUpdateRequestPath(str, str2, updateOperation);
    }

    public String getUpdateRequestPath(String str, String str2, UpdateOperation updateOperation) {
        return String.format(MiCloudConstants.PDC.UPDATE_RECORD_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(updateOperation.getId()));
    }

    public String getDownloadRequestUrl(String str, String str2) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + "/mic/pdc/v4.1/apps/%s/spaces/%s/records", URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getBatchUploadRequestUrl(String str, String str2) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.BATCH_OPERATION_PATH, URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getFetchRecordRequestUrl(String str, String str2, FetchRecordOperation fetchRecordOperation) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.FETCH_RECORD_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(fetchRecordOperation.getId()));
    }

    public String getFetchChildrenRequestUrl(String str, String str2, FetchChildrenOperation fetchChildrenOperation) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.FETCH_CHILDREN_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(fetchChildrenOperation.getParentId()));
    }

    public String getFetchRecordsByIndexRequestUrl(String str, String str2) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.FETCH_RECORDS_BY_INDEX_PATH, URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getRequestUploadAssetRequestUrl(String str, String str2) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.ASSET_REQUEST_UPLOAD_PATH, URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getCommitAssetRequestUrl(String str, String str2) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.ASSET_REQUEST_COMMIT_PATH, URLEncoder.encode(str), URLEncoder.encode(str2));
    }

    public String getRequestDownloadRequestUrl(String str, String str2, AssetEntity assetEntity) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.ASSET_REQUEST_DOWNLOAD_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(assetEntity.assetID));
    }

    public String getAssetUrlRequestUrl(String str, String str2, AssetEntity assetEntity) {
        return String.format(MiCloudConstants.PDC.URL_SYNC_BASE + MiCloudConstants.PDC.ASSET_GET_ASSET_URL_PATH, URLEncoder.encode(str), URLEncoder.encode(str2), URLEncoder.encode(assetEntity.assetID));
    }
}
