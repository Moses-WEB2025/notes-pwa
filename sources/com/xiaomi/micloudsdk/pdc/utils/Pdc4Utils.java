package com.xiaomi.micloudsdk.pdc.utils;

import android.util.Log;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.pdc.BatchUploadOperation;
import com.xiaomi.opensdk.pdc.Constants;
import com.xiaomi.opensdk.pdc.DownloadOperation;
import com.xiaomi.opensdk.pdc.FetchChildrenOperation;
import com.xiaomi.opensdk.pdc.FetchRecordOperation;
import com.xiaomi.opensdk.pdc.FetchRecordsByIndexOperation;
import com.xiaomi.opensdk.pdc.SyncException;
import com.xiaomi.opensdk.pdc.SyncRecord;
import com.xiaomi.opensdk.pdc.UploadOperation;
import com.xiaomi.opensdk.pdc.asset.AssetUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class Pdc4Utils {
    private static final int DEFAULT_RETRY_TIME = 300000;
    private static final String TAG = "Pdc4Utils";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Constants.ErrorType convertErrorCode(int i) throws SyncException {
        if (i == 0) {
            return Constants.ErrorType.OK;
        }
        if (i != 10001 && i != 10011) {
            if (i != 50017) {
                if (i == 52000) {
                    return Constants.ErrorType.NEED_RESYNC;
                }
                if (i != 53000) {
                    if (i != 10008) {
                        if (i != 10009) {
                            switch (i) {
                                case 40000:
                                case MiCloudConstants.PDC.ERROR_CODE_CHILD_EXIST /* 40001 */:
                                case MiCloudConstants.PDC.ERROR_CODE_ILLEGAL_RECORD_TYPE /* 40002 */:
                                case MiCloudConstants.PDC.ERROR_CODE_NODE_NOT_EXIST /* 40003 */:
                                case MiCloudConstants.PDC.ERROR_CODE_NODE_STATUS_INVALID /* 40004 */:
                                case MiCloudConstants.PDC.ERROR_CODE_SCHEMA_NOT_EXIST /* 40005 */:
                                    break;
                                case MiCloudConstants.PDC.ERROR_CODE_ASSET_NOT_EXIST /* 40006 */:
                                    return Constants.ErrorType.UNRETRIABLE_ERROR;
                                default:
                                    switch (i) {
                                        case MiCloudConstants.PDC.ERROR_CODE_SYSTEM_BATCH /* 50002 */:
                                            break;
                                        case MiCloudConstants.PDC.ERROR_CODE_INVALID_BATCH_URL /* 50003 */:
                                            break;
                                        default:
                                            return Constants.ErrorType.UNRETRIABLE_ERROR;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
            return Constants.ErrorType.UNRETRIABLE_ERROR;
        }
        return Constants.ErrorType.RETRIABLE_ERROR;
    }

    public static class SyncOperationResult {
        public int code;
        public JSONObject data;
        public String description;
        public Constants.ErrorType errorType;
        public boolean isSuccess;
        public String reason;
        public String result;
        public long retryTime;

        public SyncOperationResult() {
        }

        public SyncOperationResult(JSONObject jSONObject) throws SyncException, JSONException {
            this.result = jSONObject.optString("reason");
            this.description = jSONObject.optString("description");
            int i = jSONObject.getInt("code");
            this.code = i;
            Constants.ErrorType errorTypeConvertErrorCode = Pdc4Utils.convertErrorCode(i);
            this.errorType = errorTypeConvertErrorCode;
            this.isSuccess = errorTypeConvertErrorCode == Constants.ErrorType.OK;
            if (jSONObject.optBoolean("retriable")) {
                this.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                this.retryTime = DesktopRecommendInfo.DEFAULT_CACHE_TIME;
            }
            this.data = jSONObject.optJSONObject("data");
        }
    }

    public static SyncOperationResult requestServer(boolean z, String str, Map<String, String> map, Map<String, String> map2) throws SyncException {
        try {
            Log.v(TAG, "requestServer started at " + str + "  isGet : " + z);
            String strSecureGet = z ? Request.secureGet(str, map, map2, null) : Request.securePost(str, map, map2, null);
            SyncOperationResult syncOperationResult = new SyncOperationResult(new JSONObject(strSecureGet));
            if (!syncOperationResult.isSuccess) {
                Log.w(TAG, "Got error: " + strSecureGet);
            }
            return syncOperationResult;
        } catch (CloudServerException e) {
            Log.e(TAG, "requestServer error:" + e.getStatusCode(), e);
            SyncOperationResult syncOperationResult2 = new SyncOperationResult();
            int statusCode = e.getStatusCode();
            if (statusCode == 401 || statusCode == 403) {
                syncOperationResult2.isSuccess = false;
                syncOperationResult2.errorType = Constants.ErrorType.AUTH_FAILED;
            } else if (statusCode == 500) {
                syncOperationResult2.isSuccess = false;
                syncOperationResult2.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                syncOperationResult2.retryTime = DesktopRecommendInfo.DEFAULT_CACHE_TIME;
            } else if (statusCode != 503) {
                syncOperationResult2.isSuccess = false;
                syncOperationResult2.errorType = Constants.ErrorType.UNRETRIABLE_ERROR;
                syncOperationResult2.description = e.getMessage();
            } else {
                syncOperationResult2.isSuccess = false;
                syncOperationResult2.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                syncOperationResult2.retryTime = e.retryTime;
            }
            return syncOperationResult2;
        } catch (UnsupportedEncodingException e2) {
            Log.e(TAG, "requestServer error", e2);
            throw new SyncException("requestServer error:" + e2);
        } catch (IOException e3) {
            Log.e(TAG, "requestServer error", e3);
            if (!RetriableException.isRetriableException(e3)) {
                throw new SyncException("requestServer error:" + e3);
            }
            SyncOperationResult syncOperationResult3 = new SyncOperationResult();
            syncOperationResult3.isSuccess = false;
            syncOperationResult3.errorType = Constants.ErrorType.RETRIABLE_ERROR;
            syncOperationResult3.retryTime = DesktopRecommendInfo.DEFAULT_CACHE_TIME;
            return syncOperationResult3;
        } catch (BadPaddingException e4) {
            Log.e(TAG, "requestServer error", e4);
            throw new SyncException("requestServer error:" + e4);
        } catch (IllegalBlockSizeException e5) {
            Log.e(TAG, "requestServer error", e5);
            throw new SyncException("requestServer error:" + e5);
        } catch (JSONException e6) {
            Log.e(TAG, "requestServer error", e6);
            throw new SyncException("requestServer error:" + e6);
        }
    }

    public static SyncOperationResult requestServer(boolean z, String str, Map<String, String> map) throws SyncException {
        return requestServer(z, str, null, map);
    }

    public static SyncRecord parseSyncRecord(JSONObject jSONObject) throws SyncException {
        Constants.RecordStatus recordStatus;
        long jOptLong = jSONObject.optLong("eTag", -1L);
        String strOptString = jSONObject.optString("id", null);
        String strOptString2 = jSONObject.optString("uniqueKey", null);
        String strOptString3 = jSONObject.optString(MiCloudConstants.PDC.J_PARENT_ID, null);
        String strOptString4 = jSONObject.optString("type", null);
        String strOptString5 = jSONObject.optString("status", null);
        if ("normal".equals(strOptString5)) {
            recordStatus = Constants.RecordStatus.NORMAL;
        } else if ("deleted".equals(strOptString5)) {
            recordStatus = Constants.RecordStatus.DELETED;
        } else {
            throw new SyncException("Unknown status " + strOptString5 + " for " + strOptString);
        }
        Constants.RecordStatus recordStatus2 = recordStatus;
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(MiCloudConstants.PDC.J_CONTENT_JSON);
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(MiCloudConstants.PDC.J_ASSET_LIST);
        if (jSONArrayOptJSONArray != null) {
            return new SyncRecord(jOptLong, strOptString, strOptString2, strOptString3, strOptString4, jSONObjectOptJSONObject, AssetUtils.parseJSONArrayToEntities(jSONArrayOptJSONArray, strOptString4), recordStatus2);
        }
        return new SyncRecord(jOptLong, strOptString, strOptString2, strOptString3, strOptString4, jSONObjectOptJSONObject, null, recordStatus2);
    }

    public static UploadOperation.Result parseUploadResult(SyncOperationResult syncOperationResult) throws SyncException {
        Constants.ConflictType conflictType;
        SyncRecord syncRecord;
        SyncRecord syncRecord2;
        if (syncOperationResult.isSuccess) {
            try {
                String strOptString = syncOperationResult.data.optString(MiCloudConstants.PDC.J_CONFLICT_TYPE);
                if ("none".equals(strOptString)) {
                    conflictType = Constants.ConflictType.NONE;
                } else if ("eTag".equals(strOptString)) {
                    conflictType = Constants.ConflictType.ETAG;
                } else if ("id".equals(strOptString)) {
                    conflictType = Constants.ConflictType.ID;
                } else if ("uniqueKey".equals(strOptString)) {
                    conflictType = Constants.ConflictType.UNIQUE_KEY;
                } else {
                    throw new SyncException("Unknown conflict type " + strOptString);
                }
                Constants.ConflictType conflictType2 = conflictType;
                if (conflictType2 == Constants.ConflictType.NONE) {
                    syncRecord2 = parseSyncRecord(syncOperationResult.data.getJSONObject("record"));
                    syncRecord = null;
                } else {
                    syncRecord = parseSyncRecord(syncOperationResult.data.getJSONObject(MiCloudConstants.PDC.J_CONFLICT_RECORD));
                    syncRecord2 = null;
                }
                return new UploadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, conflictType2, syncRecord2, syncRecord);
            } catch (JSONException e) {
                Log.e(TAG, "parseUploadResult error ", e);
                throw new SyncException("parseUploadResult error " + e);
            }
        }
        return new UploadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, Constants.ConflictType.NONE, null, null);
    }

    public static DownloadOperation.Result parseDownloadResult(SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess) {
            try {
                long jOptLong = syncOperationResult.data.optLong("watermark");
                String string = syncOperationResult.data.getString("syncToken");
                boolean z = syncOperationResult.data.getBoolean(MiCloudConstants.PDC.J_HAS_MORE);
                JSONArray jSONArray = syncOperationResult.data.getJSONArray("records");
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(parseSyncRecord(jSONArray.getJSONObject(i)));
                }
                return new DownloadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, jOptLong, string, z, arrayList);
            } catch (JSONException e) {
                Log.e(TAG, "parseDownloadResult error ", e);
                throw new SyncException("parseDownloadResult error " + e);
            }
        }
        return new DownloadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, -1L, null, false, null);
    }

    public static BatchUploadOperation.Result parseBatchUploadResult(SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess) {
            try {
                JSONArray jSONArrayOptJSONArray = syncOperationResult.data.optJSONArray("list");
                if (jSONArrayOptJSONArray != null) {
                    ArrayList arrayList = new ArrayList(jSONArrayOptJSONArray.length());
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i);
                        UploadOperation.Result uploadResult = parseUploadResult(new SyncOperationResult(jSONObject));
                        if (!uploadResult.isSuccess()) {
                            Log.w(TAG, "Sub item " + i + " got error: " + jSONObject, new Throwable());
                        }
                        arrayList.add(uploadResult);
                    }
                    return new BatchUploadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, arrayList);
                }
            } catch (JSONException e) {
                Log.e(TAG, "parseBatchUploadResult error ", e);
                throw new SyncException("parseBatchUploadResult error " + e);
            }
        }
        return new BatchUploadOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, null);
    }

    public static FetchRecordOperation.Result parseFetchRecordResult(SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess) {
            JSONObject jSONObjectOptJSONObject = syncOperationResult.data.optJSONObject("record");
            return new FetchRecordOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, jSONObjectOptJSONObject != null ? parseSyncRecord(jSONObjectOptJSONObject) : null);
        }
        return new FetchRecordOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, null);
    }

    public static FetchChildrenOperation.Result parseFetchChildrenResult(SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess && syncOperationResult.data != null) {
            try {
                String strOptString = syncOperationResult.data.optString("cursor", null);
                boolean z = syncOperationResult.data.getBoolean(MiCloudConstants.PDC.J_HAS_MORE);
                JSONArray jSONArray = syncOperationResult.data.getJSONArray("records");
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(parseSyncRecord(jSONArray.getJSONObject(i)));
                }
                return new FetchChildrenOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, strOptString, z, arrayList);
            } catch (JSONException e) {
                Log.e(TAG, "FetchChildrenOperation error ", e);
                throw new SyncException("FetchChildrenOperation error " + e);
            }
        }
        return new FetchChildrenOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, null, false, null);
    }

    public static FetchRecordsByIndexOperation.Result parseFetchRecordsByIndexResult(SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess && syncOperationResult.data != null) {
            try {
                boolean z = syncOperationResult.data.getBoolean(MiCloudConstants.PDC.J_HAS_MORE);
                JSONArray jSONArray = syncOperationResult.data.getJSONArray("records");
                JSONObject jSONObjectOptJSONObject = syncOperationResult.data.optJSONObject("beginKey");
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(parseSyncRecord(jSONArray.getJSONObject(i)));
                }
                return new FetchRecordsByIndexOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, jSONObjectOptJSONObject, z, arrayList);
            } catch (JSONException e) {
                Log.e(TAG, "FetchChildrenOperation error ", e);
                throw new SyncException("FetchChildrenOperation error " + e);
            }
        }
        return new FetchRecordsByIndexOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, null, false, null);
    }
}
