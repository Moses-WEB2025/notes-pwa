package com.miui.todo.cloudservice;

import com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator;
import com.xiaomi.opensdk.pdc.DeleteOperation;
import com.xiaomi.opensdk.pdc.FetchChildrenOperation;
import com.xiaomi.opensdk.pdc.FetchRecordOperation;
import com.xiaomi.opensdk.pdc.UpdateOperation;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes4.dex */
public class TodoUrlGenerator extends Pdc4SyncServerUrlGenerator {
    private static final String ASSET_GET_ASSET_URL_PATH = "";
    private static final String ASSET_REQUEST_COMMIT_PATH = "";
    private static final String ASSET_REQUEST_DOWNLOAD_PATH = "";
    private static final String ASSET_REQUEST_UPLOAD_PATH = "";
    private static final String BATCH_OPERATION_PATH = "";
    private static final String CREATE_RECORD_PATH = "/user/records";
    private static final String DELETE_RECORD_PATH = "/user/records/%s/delete";
    private static final String FETCH_CHILDREN_PATH = "";
    private static final String FETCH_RECORDS_BY_INDEX_PATH = "";
    private static final String FETCH_RECORD_PATH = "/user/records/%s";
    private static final String SYNC_PATH_BASE = "/user/records";
    private static final String UPDATE_RECORD_PATH = "/user/records/%s/update";
    private static final String URL_SYNC_BASE = " https://noteapi.micloud.xiaomi.net/mic/todo/v1";

    TodoUrlGenerator() {
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getCreateRequestUrl(String str, String str2) {
        return URL_SYNC_BASE + getCreateRequestPath(str, str2);
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getCreateRequestPath(String str, String str2) {
        return "/user/records";
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getDeleteRequestUrl(String str, String str2, DeleteOperation deleteOperation) {
        return URL_SYNC_BASE + getDeleteRequestPath(str, str2, deleteOperation);
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getDeleteRequestPath(String str, String str2, DeleteOperation deleteOperation) {
        return String.format(DELETE_RECORD_PATH, URLEncoder.encode(deleteOperation.getId()));
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getUpdateRequestUrl(String str, String str2, UpdateOperation updateOperation) {
        return URL_SYNC_BASE + getUpdateRequestPath(str, str2, updateOperation);
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getUpdateRequestPath(String str, String str2, UpdateOperation updateOperation) {
        return String.format(UPDATE_RECORD_PATH, URLEncoder.encode(updateOperation.getId()));
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getDownloadRequestUrl(String str, String str2) {
        return " https://noteapi.micloud.xiaomi.net/mic/todo/v1/user/records";
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getBatchUploadRequestUrl(String str, String str2) {
        return URL_SYNC_BASE;
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getFetchRecordRequestUrl(String str, String str2, FetchRecordOperation fetchRecordOperation) {
        return String.format(" https://noteapi.micloud.xiaomi.net/mic/todo/v1/user/records/%s", URLEncoder.encode(fetchRecordOperation.getId()));
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getFetchChildrenRequestUrl(String str, String str2, FetchChildrenOperation fetchChildrenOperation) {
        return String.format(URL_SYNC_BASE, URLEncoder.encode(fetchChildrenOperation.getParentId()));
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getFetchRecordsByIndexRequestUrl(String str, String str2) {
        return URL_SYNC_BASE;
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getRequestUploadAssetRequestUrl(String str, String str2) {
        return URL_SYNC_BASE;
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getCommitAssetRequestUrl(String str, String str2) {
        return URL_SYNC_BASE;
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getRequestDownloadRequestUrl(String str, String str2, AssetEntity assetEntity) {
        return String.format(URL_SYNC_BASE, URLEncoder.encode(assetEntity.assetID));
    }

    @Override // com.xiaomi.micloudsdk.pdc.Pdc4SyncServerUrlGenerator
    public String getAssetUrlRequestUrl(String str, String str2, AssetEntity assetEntity) {
        return String.format(URL_SYNC_BASE, URLEncoder.encode(assetEntity.assetID));
    }
}
