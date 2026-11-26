package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes4.dex */
class OpenPdcConsts {
    static final String ASSET_REQUEST_COMMIT_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/assets/commit";
    static final String ASSET_REQUEST_DOWNLOAD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/assets/%s";
    static final String ASSET_REQUEST_UPLOAD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/assets/upload";
    static final String CONFLICT_ETAG = "eTag";
    static final String CONFLICT_ID = "id";
    static final String CONFLICT_NONE = "none";
    static final String CONFLICT_UNIQUE_KEY = "uniqueKey";
    static final String CREATE_RECORD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records";
    static final String DEFAULT_HOST = "micpdc.openapi.xiaomi.net";
    static final String DELETE_RECORD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/%s";
    static final String FETCH_CHILDREN_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/%s/children";
    static final String FETCH_RECORDS_BY_INDEX_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/index";
    static final String FETCH_RECORD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/%s";
    static final String J_ASSET_LIST = "assetList";
    static final String J_BEGIN_KEY = "beginKey";
    static final String J_CODE = "code";
    static final String J_CONFLICT_RECORD = "conflictRecord";
    static final String J_CONFLICT_TYPE = "conflictType";
    static final String J_CONTENT_JSON = "contentJson";
    static final String J_CURSOR = "cursor";
    static final String J_DATA = "data";
    static final String J_DESCRIPTION = "description";
    static final String J_ETAG = "eTag";
    static final String J_HAS_MORE = "hasMore";
    static final String J_ID = "id";
    static final String J_LIST = "list";
    static final String J_PARAMS = "params";
    static final String J_PARENT_ID = "parentId";
    static final String J_PATH = "path";
    static final String J_REASON = "reason";
    static final String J_RECORD = "record";
    static final String J_RECORDS = "records";
    static final String J_RESULT = "result";
    static final String J_RETRIABLE = "retriable";
    static final String J_STATUS = "status";
    static final String J_SYNC_TOKEN = "syncToken";
    static final String J_TYPE = "type";
    static final String J_UNIQUE_KEY = "uniqueKey";
    static final String J_WATERMARK = "watermark";
    static final int MAX_RECORDS_FETCH_SIZE = 200;
    public static final String OPEN_PDC_HOST_KEY = "com.xiaomi.opensdk.pdc.host";
    static final String PARAM_BATCH_CONTENT = "batchContent";
    static final String PARAM_BEGIN_KEY = "beginKey";
    static final String PARAM_BODY = "body";
    static final String PARAM_CLIENT_ID = "clientId";
    static final String PARAM_CURSOR = "cursor";
    static final String PARAM_END_KEY = "endKey";
    static final String PARAM_ETAG = "eTag";
    static final String PARAM_ID = "id";
    static final String PARAM_INDEX_NAME = "indexName";
    static final String PARAM_IS_REVERSE = "isReverse";
    static final String PARAM_LIMIT = "limit";
    static final String PARAM_SYNC_TOKEN = "syncToken";
    static final String PARAM_TOKEN = "token";
    static final String PARAM_TYPE = "type";
    static final String STATUS_DELETED = "deleted";
    static final String STATUS_NORMAL = "normal";
    static final String SYNC_PATH_BASE = "/mic/open/pdc/apps/%s/spaces/%s/records";
    static final String UPDATE_RECORD_PATH = "/mic/open/pdc/apps/%s/spaces/%s/records/%s";

    OpenPdcConsts() {
    }
}
