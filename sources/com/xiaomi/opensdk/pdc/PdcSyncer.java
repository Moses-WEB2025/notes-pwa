package com.xiaomi.opensdk.pdc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import java.util.ArrayList;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PdcSyncer {
    private static final int MAX_BATCH_SIZE = 10;
    private static final String PREF_KEY_PDC_SYNCTOKEN = "pdc_syncToken_";
    private static final String PREF_KEY_PDC_WATERMARK = "pdc_watermark_";
    private static final String PREF_KEY_QUOTA_COUNT = "pdc_quota_count";
    private static final String PREF_KEY_QUOTA_DAY = "pdc_quota_day";
    private static final String QN_DOWNLOAD = "download";
    private static final String QN_UPLOAD = "upload";
    private static final int QT_DOWNLOAD = 1000;
    private static final int QT_UPLOAD = 1000;
    private static final int[] RETRY_INTERVALS = {5000, 10000, 10000};
    private static final String TAG = "PdcSyncer";
    private Context mContext;
    private final SharedPreferences mPrefs;

    public interface DownloadClient {
        boolean onBeginDownload();

        void onEndDownload();

        boolean onRecordDownloaded(SyncRecord syncRecord);

        void onResync();

        void onServiceUnavailable(long j);
    }

    public interface UploadClient {
        Pair<Long, SyncRecord> getNextLocalRecord();

        boolean onBeginUpload();

        void onEndUpload();

        SyncRecord onInvalidRecord(Long l, SyncRecord syncRecord, Constants.ErrorType errorType);

        void onRecordUploaded(Long l, SyncRecord syncRecord, SyncRecord syncRecord2);

        void onServiceUnavailable(long j);

        SyncRecord onUploadConflict(Long l, Constants.ConflictType conflictType, SyncRecord syncRecord, SyncRecord syncRecord2);
    }

    public static SyncRecord newSyncRecordForCreate(String str, String str2, String str3, String str4, JSONObject jSONObject, AssetEntity[] assetEntityArr) {
        return new SyncRecord(-1L, str, str2, str3, str4, jSONObject, assetEntityArr, Constants.RecordStatus.NORMAL);
    }

    public static SyncRecord newSyncRecordForUpdate(long j, String str, String str2, String str3, String str4, JSONObject jSONObject, AssetEntity[] assetEntityArr) {
        return new SyncRecord(j, str, str2, str3, str4, jSONObject, assetEntityArr, Constants.RecordStatus.NORMAL);
    }

    public static SyncRecord newSyncRecordForDelete(long j, String str, String str2) {
        return new SyncRecord(j, str, null, null, str2, null, null, Constants.RecordStatus.DELETED);
    }

    public static class SyncRecordBuilder {
        private AssetEntity[] assetEntities;
        private JSONObject contentJson;
        private long eTag;
        private String id;
        private String parentId;
        private Constants.RecordStatus status;
        private String type;
        private String uniqueKey;

        public static SyncRecordBuilder buildFrom(SyncRecord syncRecord) {
            SyncRecordBuilder syncRecordBuilder = new SyncRecordBuilder();
            syncRecordBuilder.eTag = syncRecord.eTag;
            syncRecordBuilder.id = syncRecord.id;
            syncRecordBuilder.uniqueKey = syncRecord.uniqueKey;
            syncRecordBuilder.parentId = syncRecord.parentId;
            syncRecordBuilder.type = syncRecord.type;
            syncRecordBuilder.contentJson = syncRecord.contentJson;
            syncRecordBuilder.assetEntities = syncRecord.assetEntities;
            syncRecordBuilder.status = syncRecord.status;
            return syncRecordBuilder;
        }

        public SyncRecordBuilder setETag(long j) {
            this.eTag = j;
            return this;
        }

        public SyncRecordBuilder setId(String str) {
            this.id = str;
            return this;
        }

        public SyncRecordBuilder setUniqueKey(String str) {
            this.uniqueKey = str;
            return this;
        }

        public SyncRecordBuilder setParentId(String str) {
            this.parentId = str;
            return this;
        }

        public SyncRecordBuilder setType(String str) {
            this.type = str;
            return this;
        }

        public SyncRecordBuilder setContentJson(JSONObject jSONObject) {
            this.contentJson = jSONObject;
            return this;
        }

        public SyncRecordBuilder setAssetEntities(AssetEntity[] assetEntityArr) {
            this.assetEntities = assetEntityArr;
            return this;
        }

        public SyncRecordBuilder setStatus(Constants.RecordStatus recordStatus) {
            this.status = recordStatus;
            return this;
        }

        public SyncRecord build() {
            return new SyncRecord(this.eTag, this.id, this.uniqueKey, this.parentId, this.type, this.contentJson, this.assetEntities, this.status);
        }
    }

    public PdcSyncer(Context context) {
        this.mContext = context;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static class Batch {
        public BatchUploadOperation batchOp;
        public ArrayList<Long> localIds;
        public ArrayList<SyncRecord> localRecords;

        public Batch(SyncServerAdapter syncServerAdapter, int i) {
            this.batchOp = new BatchUploadOperation(syncServerAdapter);
            this.localRecords = new ArrayList<>(i);
            this.localIds = new ArrayList<>(i);
        }
    }

    public boolean upload(SyncServerAdapter syncServerAdapter, UploadClient uploadClient) {
        return upload(syncServerAdapter, 10, uploadClient);
    }

    public boolean upload(SyncServerAdapter syncServerAdapter, int i, UploadClient uploadClient) throws Throwable {
        UploadClient uploadClient2;
        if (!ensureQuotaNotExceeded(QN_UPLOAD, 1000)) {
            return false;
        }
        if (i > 10) {
            throw new IllegalArgumentException("batch size cannot exceed 10");
        }
        HashSet<SyncRecord> hashSet = new HashSet<>();
        if (!uploadClient.onBeginUpload()) {
            return false;
        }
        try {
            Batch batch = new Batch(syncServerAdapter, i);
            while (!Thread.currentThread().isInterrupted()) {
                Pair<Long, SyncRecord> nextLocalRecord = uploadClient.getNextLocalRecord();
                if (nextLocalRecord != null) {
                    PdcSyncer pdcSyncer = this;
                    SyncServerAdapter syncServerAdapter2 = syncServerAdapter;
                    int i2 = i;
                    uploadClient2 = uploadClient;
                    try {
                        Long l = (Long) nextLocalRecord.first;
                        SyncRecord syncRecord = (SyncRecord) nextLocalRecord.second;
                        batch.batchOp.add(pdcSyncer.createUploadOp(syncServerAdapter2, syncRecord));
                        batch.localRecords.add(syncRecord);
                        batch.localIds.add(l);
                        while (batch.batchOp.size() >= i2) {
                            if (!pdcSyncer.commitBatch(syncServerAdapter2, uploadClient2, batch, i2, hashSet)) {
                                uploadClient2.onEndUpload();
                                return false;
                            }
                        }
                        this = pdcSyncer;
                        syncServerAdapter = syncServerAdapter2;
                        uploadClient = uploadClient2;
                        i = i2;
                    } catch (Throwable th) {
                        th = th;
                    }
                } else {
                    while (!batch.batchOp.isEmpty()) {
                        PdcSyncer pdcSyncer2 = this;
                        SyncServerAdapter syncServerAdapter3 = syncServerAdapter;
                        int i3 = i;
                        uploadClient2 = uploadClient;
                        if (!pdcSyncer2.commitBatch(syncServerAdapter3, uploadClient2, batch, i3, hashSet)) {
                            uploadClient2.onEndUpload();
                            return false;
                        }
                        this = pdcSyncer2;
                        syncServerAdapter = syncServerAdapter3;
                        uploadClient = uploadClient2;
                        i = i3;
                    }
                    uploadClient.onEndUpload();
                    return true;
                }
                th = th;
                Throwable th2 = th;
                uploadClient2.onEndUpload();
                throw th2;
            }
            uploadClient.onEndUpload();
            return false;
        } catch (Throwable th3) {
            th = th3;
            uploadClient2 = uploadClient;
        }
    }

    private UploadOperation createUploadOp(SyncServerAdapter syncServerAdapter, SyncRecord syncRecord) {
        SyncFactory syncFactory = new SyncFactory(syncServerAdapter);
        if (syncRecord.eTag <= 0) {
            if (syncRecord.type == null) {
                throw new IllegalStateException("type is required for create");
            }
            if (syncRecord.status != Constants.RecordStatus.NORMAL) {
                throw new IllegalStateException("Cannot create a deleted record");
            }
            return syncFactory.newCreateOp(syncRecord.type, syncRecord.id, syncRecord.uniqueKey, syncRecord.parentId, syncRecord.contentJson, syncRecord.assetEntities);
        }
        if (syncRecord.status == Constants.RecordStatus.DELETED) {
            if (syncRecord.type == null) {
                throw new IllegalStateException("type is required for delete");
            }
            if (syncRecord.id == null) {
                throw new IllegalStateException("id is required for delete");
            }
            if (syncRecord.uniqueKey != null || syncRecord.assetEntities != null || syncRecord.contentJson != null || syncRecord.parentId != null) {
                throw new IllegalStateException("do not set data fields for delete");
            }
            return syncFactory.newDeleteOp(syncRecord.type, syncRecord.id, syncRecord.eTag);
        }
        if (syncRecord.type == null) {
            throw new IllegalStateException("type is required for update");
        }
        if (syncRecord.id == null) {
            throw new IllegalStateException("id is required for update");
        }
        return syncFactory.newUpdateOp(syncRecord.type, syncRecord.id, syncRecord.uniqueKey, syncRecord.parentId, syncRecord.contentJson, syncRecord.eTag, syncRecord.assetEntities);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [int] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.xiaomi.opensdk.pdc.BatchUploadOperation$Result] */
    private boolean commitBatch(SyncServerAdapter syncServerAdapter, UploadClient uploadClient, Batch batch, int i, HashSet<SyncRecord> hashSet) {
        boolean z;
        boolean z2;
        int i2;
        int i3;
        SyncRecord syncRecordOnInvalidRecord;
        ?? r12;
        boolean z3 = false;
        int i4 = 0;
        while (i4 < RETRY_INTERVALS.length && !Thread.currentThread().isInterrupted()) {
            try {
                ?? Execute = batch.batchOp.execute();
                if (Execute.isSuccess()) {
                    Batch batch2 = new Batch(syncServerAdapter, i);
                    boolean z4 = z3;
                    ?? r13 = z4;
                    ?? r11 = z4;
                    while (r11 < Execute.getSubResultCount()) {
                        UploadOperation.Result subResultAt = Execute.getSubResultAt(r11);
                        if (subResultAt.isSuccess()) {
                            z = z3;
                            try {
                                if (subResultAt.getConflictType() == Constants.ConflictType.NONE) {
                                    uploadClient.onRecordUploaded(batch.localIds.get(r11), batch.localRecords.get(r11), subResultAt.getRecord());
                                    i3 = i4;
                                    syncRecordOnInvalidRecord = null;
                                    r12 = r13;
                                } else {
                                    hashSet.add(batch.localRecords.get(r11));
                                    i3 = i4;
                                    syncRecordOnInvalidRecord = uploadClient.onUploadConflict(batch.localIds.get(r11), subResultAt.getConflictType(), batch.localRecords.get(r11), subResultAt.getConflictRecord());
                                    r12 = r13;
                                    if (hashSet.contains(syncRecordOnInvalidRecord)) {
                                        Log.wtf(TAG, "onUploadConflict: Repetitive bad record");
                                        r12 = r13;
                                    }
                                }
                            } catch (SyncException e) {
                                e = e;
                                Log.w(TAG, "SyncException in commitBatch()", e);
                                return z;
                            } catch (InterruptedException e2) {
                                e = e2;
                                Log.w(TAG, "InterruptedException in commitBatch()", e);
                                return z;
                            }
                        } else {
                            z = z3;
                            i3 = i4;
                            if (subResultAt.isRetriable()) {
                                syncRecordOnInvalidRecord = batch.localRecords.get(r11);
                                r12 = r13 + 1;
                            } else if (isInvalidRecord(subResultAt.getErrorType())) {
                                hashSet.add(batch.localRecords.get(r11));
                                syncRecordOnInvalidRecord = uploadClient.onInvalidRecord(batch.localIds.get(r11), batch.localRecords.get(r11), subResultAt.getErrorType());
                                r12 = r13;
                                if (hashSet.contains(syncRecordOnInvalidRecord)) {
                                    Log.wtf(TAG, "onUploadConflict: Repetitive bad record");
                                    r12 = r13;
                                }
                            } else {
                                syncRecordOnInvalidRecord = null;
                                r12 = r13;
                            }
                        }
                        if (syncRecordOnInvalidRecord != null) {
                            batch2.batchOp.add(createUploadOp(syncServerAdapter, syncRecordOnInvalidRecord));
                            batch2.localRecords.add(syncRecordOnInvalidRecord);
                            batch2.localIds.add(batch.localIds.get(r11));
                        }
                        z3 = z;
                        i4 = i3;
                        r11++;
                        r13 = r12;
                    }
                    z2 = z3;
                    i2 = i4;
                    if (r13 != Execute.getSubResultCount()) {
                        batch.batchOp = batch2.batchOp;
                        batch.localRecords = batch2.localRecords;
                        batch.localIds = batch2.localIds;
                        return true;
                    }
                    Thread.sleep(RETRY_INTERVALS[i2]);
                } else {
                    z2 = z3;
                    i2 = i4;
                    if (!Execute.isRetriable()) {
                        Log.w(TAG, "BatchResult Error:" + Execute.getErrorType() + " description:" + Execute.getDescription());
                        return z2;
                    }
                }
                i4 = i2 + 1;
                z3 = z2;
            } catch (SyncException e3) {
                e = e3;
                z = z3;
            } catch (InterruptedException e4) {
                e = e4;
                z = z3;
            }
        }
        return z3;
    }

    public boolean download(SyncServerAdapter syncServerAdapter, DownloadClient downloadClient) {
        PdcSyncer pdcSyncer;
        if (!ensureQuotaNotExceeded(QN_DOWNLOAD, 1000)) {
            return false;
        }
        try {
            if (!downloadClient.onBeginDownload()) {
                return false;
            }
            String syncToken = getSyncToken(syncServerAdapter.getAppId(), syncServerAdapter.getSpaceId());
            getWatermark(syncServerAdapter.getAppId(), syncServerAdapter.getSpaceId());
            DownloadOperation.Result resultExecute = null;
            while (!Thread.currentThread().isInterrupted()) {
                int i = 0;
                while (true) {
                    int[] iArr = RETRY_INTERVALS;
                    if (i >= iArr.length) {
                        break;
                    }
                    resultExecute = new DownloadOperation(syncServerAdapter, syncToken).execute();
                    if (resultExecute.isSuccess()) {
                        break;
                    }
                    if (!resultExecute.isRetriable()) {
                        return false;
                    }
                    Thread.sleep(iArr[i]);
                    i++;
                }
                if (resultExecute.isSuccess()) {
                    boolean z = true;
                    for (int i2 = 0; i2 < resultExecute.getRecordCount(); i2++) {
                        if (!downloadClient.onRecordDownloaded(resultExecute.getRecordAt(i2))) {
                            z = false;
                        }
                    }
                    if (!z) {
                        return false;
                    }
                    String syncToken2 = resultExecute.getSyncToken();
                    pdcSyncer = this;
                    pdcSyncer.saveSyncToken(syncServerAdapter.getAppId(), syncServerAdapter.getSpaceId(), syncToken2, resultExecute.getWatermark());
                    syncToken = syncToken2;
                } else {
                    pdcSyncer = this;
                    if (pdcSyncer.shouldResync(resultExecute.getErrorType())) {
                        downloadClient.onResync();
                        pdcSyncer.clearSyncToken(syncServerAdapter.getAppId(), syncServerAdapter.getSpaceId());
                    }
                }
                if (!resultExecute.hasMore()) {
                    return true;
                }
                this = pdcSyncer;
            }
            return false;
        } catch (InterruptedException e) {
            Log.w(TAG, "InterruptedException in download()", e);
            return false;
        } catch (SyncException e2) {
            Log.w(TAG, "SyncException in download()", e2);
            return false;
        } finally {
            downloadClient.onEndDownload();
        }
    }

    public void clearSyncToken(String str, String[] strArr) {
        SharedPreferences.Editor editorEdit = this.mPrefs.edit();
        for (String str2 : strArr) {
            editorEdit.remove(PREF_KEY_PDC_SYNCTOKEN + str + "_" + str2);
            editorEdit.remove(PREF_KEY_PDC_WATERMARK + str + "_" + str2);
        }
        editorEdit.commit();
    }

    public void clearSyncToken(String str, String str2) {
        clearSyncToken(str, new String[]{str2});
    }

    private void saveSyncToken(String str, String str2, String str3, long j) {
        this.mPrefs.edit().putString(PREF_KEY_PDC_SYNCTOKEN + str + "_" + str2, str3).putLong(PREF_KEY_PDC_WATERMARK + str + "_" + str2, j).commit();
    }

    private String getSyncToken(String str, String str2) {
        return this.mPrefs.getString(PREF_KEY_PDC_SYNCTOKEN + str + "_" + str2, null);
    }

    private long getWatermark(String str, String str2) {
        return this.mPrefs.getLong(PREF_KEY_PDC_WATERMARK + str + "_" + str2, -1L);
    }

    private boolean ensureQuotaNotExceeded(String str, int i) {
        String str2 = PREF_KEY_QUOTA_COUNT + str;
        String str3 = PREF_KEY_QUOTA_DAY + str;
        long j = this.mPrefs.getLong(str3, 0L);
        long jCurrentTimeMillis = System.currentTimeMillis() / 86400000;
        int i2 = (jCurrentTimeMillis == j ? this.mPrefs.getInt(str2, 0) : 0) + 1;
        if (i2 > i) {
            Log.wtf(TAG, "quota " + str + "exceeds limit " + i);
            return false;
        }
        this.mPrefs.edit().putLong(str3, jCurrentTimeMillis).putInt(str2, i2).commit();
        return true;
    }

    public boolean shouldResync(Constants.ErrorType errorType) {
        return errorType == Constants.ErrorType.NEED_RESYNC;
    }

    public boolean isInvalidRecord(Constants.ErrorType errorType) {
        return errorType == Constants.ErrorType.UNRETRIABLE_ERROR;
    }
}
