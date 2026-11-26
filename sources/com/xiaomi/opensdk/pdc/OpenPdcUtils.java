package com.xiaomi.opensdk.pdc;

import android.util.Log;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.pdc.asset.AssetUtils;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OpenPdcUtils {
    private static final String TAG = "OpenPdcUtils";

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

    public static UploadOperation.Result parseUploadResult(OpenPdcRequest.SyncOperationResult syncOperationResult) throws SyncException {
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

    public static DownloadOperation.Result parseDownloadResult(OpenPdcRequest.SyncOperationResult syncOperationResult) throws SyncException {
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

    public static FetchRecordOperation.Result parseFetchRecordResult(OpenPdcRequest.SyncOperationResult syncOperationResult) throws SyncException {
        if (syncOperationResult.isSuccess) {
            JSONObject jSONObjectOptJSONObject = syncOperationResult.data.optJSONObject("record");
            return new FetchRecordOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, jSONObjectOptJSONObject != null ? parseSyncRecord(jSONObjectOptJSONObject) : null);
        }
        return new FetchRecordOperation.Result(syncOperationResult.isSuccess, syncOperationResult.description, syncOperationResult.errorType, syncOperationResult.code, syncOperationResult.retryTime, syncOperationResult.result, null);
    }

    public static FetchChildrenOperation.Result parseFetchChildrenResult(OpenPdcRequest.SyncOperationResult syncOperationResult) throws SyncException {
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

    public static FetchRecordsByIndexOperation.Result parseFetchRecordsByIndexResult(OpenPdcRequest.SyncOperationResult syncOperationResult) throws SyncException {
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
