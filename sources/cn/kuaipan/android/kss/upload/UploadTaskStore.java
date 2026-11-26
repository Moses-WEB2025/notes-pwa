package cn.kuaipan.android.kss.upload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.kss.IDataFactory;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.KssMaster;
import cn.kuaipan.android.utils.OAuthTimeUtils;
import cn.kuaipan.android.utils.SQLUtility;
import cn.kuaipan.android.utils.SyncAccessor;

/* JADX INFO: loaded from: classes.dex */
public class UploadTaskStore implements KssDef {
    private static final String LOG_TAG = "UploadTaskStore";
    private static final int MSG_DELETE = 1;
    private static final int MSG_GET_INFO = 3;
    private static final int MSG_GET_POS = 2;
    private static final int MSG_UPDATE = 0;
    private static volatile Looper sLooper;
    private final SyncAccessor mAccessor = new SyncAccessor(getCommonLooper()) { // from class: cn.kuaipan.android.kss.upload.UploadTaskStore.1
        @Override // cn.kuaipan.android.utils.SyncAccessor
        public Object handleAccess(int i, Object... objArr) {
            if (i == 0) {
                UploadTaskStore.this.mDBHelper.update(((Number) objArr[0]).intValue(), (KssUploadInfo) objArr[1], (UploadBlockInfoPersist) objArr[2]);
                return null;
            }
            if (i == 1) {
                UploadTaskStore.this.mDBHelper.delete(((Number) objArr[0]).intValue());
                return null;
            }
            if (i == 2) {
                return UploadTaskStore.this.mDBHelper.queryPos(((Number) objArr[0]).intValue());
            }
            if (i == 3) {
                int iIntValue = ((Number) objArr[0]).intValue();
                UploadTaskStore.this.mDBHelper.deleteBefore(OAuthTimeUtils.currentTime() - KssDef.CHUNK_VALIDATE_DUR);
                try {
                    return UploadTaskStore.this.mDBHelper.queryKss(iIntValue, UploadTaskStore.this.mDataFactory);
                } catch (Throwable th) {
                    Log.w(UploadTaskStore.LOG_TAG, "Meet exception when parser kss from db", th);
                    return null;
                }
            }
            return super.handleAccess(i, objArr);
        }
    };
    private final DBHelper mDBHelper;
    private final IDataFactory mDataFactory;

    public UploadTaskStore(Context context, IDataFactory iDataFactory) {
        if (context == null || iDataFactory == null) {
            throw new KscRuntimeException(ErrorCode.NULL_PARAM, "context and dataFactory can't be null");
        }
        this.mDBHelper = DBHelper.getInstance(context);
        this.mDataFactory = iDataFactory;
    }

    public void putUploadInfo(int i, KssUploadInfo kssUploadInfo) throws InterruptedException {
        this.mAccessor.access(0, Integer.valueOf(i), kssUploadInfo, new UploadBlockInfoPersist());
    }

    public void removeUploadInfo(int i) throws InterruptedException {
        this.mAccessor.access(1, Integer.valueOf(i));
    }

    public KssUploadInfo getUploadInfo(int i) throws InterruptedException {
        return (KssUploadInfo) this.mAccessor.access(3, Integer.valueOf(i));
    }

    public UploadBlockInfoPersist getUploadPos(int i) throws InterruptedException {
        return (UploadBlockInfoPersist) this.mAccessor.access(2, Integer.valueOf(i));
    }

    public void updateUploadInfo(int i, KssUploadInfo kssUploadInfo, UploadBlockInfoPersist uploadBlockInfoPersist) {
        this.mDBHelper.update(i, kssUploadInfo, uploadBlockInfoPersist);
    }

    private static class DBHelper extends SQLiteOpenHelper {
        private static final String CHUNK_POS = "chunk_pos";
        private static final String DATABASE = "ksssdk_infos.db";
        private static final int DB_VERSION = 6;
        private static final String LOG_TAG = "DBHelper";
        private static final String TABLE_UPLOAD = "upload_chunks";
        private static final String UPLOAD_ID = "upload_id";
        private static volatile DBHelper sInstance;
        private static final String GEN_TIME = "gen_time";
        private static final String WHERE_DEL = SQLUtility.getSelectionWithTemplete(SQLUtility.WHERE_LESS, GEN_TIME);
        private static final String TASK_HASH = "task_hash";
        private static final String WHERE_QUERY = SQLUtility.getSelection(TASK_HASH);
        private static final String[] QUERY_POS = {"chunk_pos", "upload_id"};
        private static final String KSS_REQUEST = "kss_request";
        private static final String KSS_FILE_INFO = "kss_file_info";
        private static final String KSS_UPLOAD_ID = "kss_upload_id";
        private static final String RECORD_IV = "record_iv";
        private static final String APPKEY_VERSION = "appkey_version";
        private static final String ENCRYPTED_RECORD_KEY = "encrypted_record_key";
        private static final String[] QUERY_KSS = {KSS_REQUEST, KSS_FILE_INFO, KSS_UPLOAD_ID, GEN_TIME, RECORD_IV, APPKEY_VERSION, ENCRYPTED_RECORD_KEY};

        public static DBHelper getInstance(Context context) {
            DBHelper dBHelper;
            DBHelper dBHelper2 = sInstance;
            if (dBHelper2 != null) {
                return dBHelper2;
            }
            synchronized (DBHelper.class) {
                dBHelper = sInstance;
                if (dBHelper == null) {
                    if (context == null) {
                        throw new NullPointerException("Context should not be null.");
                    }
                    dBHelper = new DBHelper(context);
                    sInstance = dBHelper;
                }
            }
            return dBHelper;
        }

        private DBHelper(Context context) {
            super(context, DATABASE, (SQLiteDatabase.CursorFactory) null, 6);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            SQLUtility.createTable(sQLiteDatabase, TABLE_UPLOAD, "_id INTEGER PRIMARY KEY AUTOINCREMENT, task_hash INTEGER NOT NULL UNIQUE, kss_request STRING NOT NULL, kss_file_info STRING NOT NULL, kss_upload_id STRING NOT NULL, chunk_pos LONG NOT NULL DEFAULT 0, upload_id STRING NOT NULL, gen_time LONG NOT NULL DEFAULT 0, record_iv STRING, appkey_version LONG DEFAULT 0, encrypted_record_key STRING");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i != 6) {
                Log.w(LOG_TAG, "Destroying all old data.");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS upload_chunks");
                onCreate(sQLiteDatabase);
            }
        }

        public void deleteBefore(long j) {
            getWritableDatabase().delete(TABLE_UPLOAD, WHERE_DEL, new String[]{String.valueOf(j)});
        }

        public UploadBlockInfoPersist queryPos(int i) {
            Cursor cursorQuery = getReadableDatabase().query(TABLE_UPLOAD, QUERY_POS, WHERE_QUERY, new String[]{String.valueOf(i)}, null, null, null);
            try {
                UploadBlockInfoPersist uploadBlockInfoPersist = new UploadBlockInfoPersist();
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    long j = cursorQuery.getInt(cursorQuery.getColumnIndex("chunk_pos"));
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("upload_id"));
                    if (!TextUtils.isEmpty(string)) {
                        uploadBlockInfoPersist.pos = j;
                        uploadBlockInfoPersist.upload_id = string;
                    }
                }
                return uploadBlockInfoPersist;
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }

        public KssUploadInfo queryKss(int i, IDataFactory iDataFactory) throws KscException {
            Cursor cursorQuery = getReadableDatabase().query(TABLE_UPLOAD, QUERY_KSS, WHERE_QUERY, new String[]{String.valueOf(i)}, null, null, null);
            KssUploadInfo kssUploadInfo = null;
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex(KSS_REQUEST));
                        String string2 = cursorQuery.getString(cursorQuery.getColumnIndex(KSS_FILE_INFO));
                        long j = cursorQuery.getLong(cursorQuery.getColumnIndex(GEN_TIME));
                        String string3 = cursorQuery.getString(cursorQuery.getColumnIndex(RECORD_IV));
                        long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex(APPKEY_VERSION));
                        String string4 = cursorQuery.getString(cursorQuery.getColumnIndex(ENCRYPTED_RECORD_KEY));
                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                            KssUploadInfo kssUploadInfo2 = new KssUploadInfo(new UploadFileInfo(string2), iDataFactory.createUploadRequestResult(string), j);
                            kssUploadInfo2.setUploadId(cursorQuery.getString(cursorQuery.getColumnIndex(KSS_UPLOAD_ID)));
                            kssUploadInfo2.setRecordIV(string3);
                            kssUploadInfo2.setAppKeyVersion(j2);
                            kssUploadInfo2.setEncryptedRecordKey(string4);
                            kssUploadInfo = kssUploadInfo2;
                        }
                        return null;
                    }
                } finally {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            }
            return kssUploadInfo;
        }

        public void update(int i, KssUploadInfo kssUploadInfo, UploadBlockInfoPersist uploadBlockInfoPersist) {
            if (kssUploadInfo == null) {
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(TASK_HASH, Integer.valueOf(i));
            contentValues.put(KSS_FILE_INFO, kssUploadInfo.getFileInfo().toString());
            contentValues.put(KSS_REQUEST, kssUploadInfo.getRequestResult().toString());
            if (TextUtils.isEmpty(kssUploadInfo.getUploadId())) {
                contentValues.put(KSS_UPLOAD_ID, "");
            } else {
                contentValues.put(KSS_UPLOAD_ID, kssUploadInfo.getUploadId());
            }
            contentValues.put("chunk_pos", Long.valueOf(uploadBlockInfoPersist.pos));
            if (TextUtils.isEmpty(uploadBlockInfoPersist.upload_id)) {
                contentValues.put("upload_id", "");
            } else {
                contentValues.put("upload_id", uploadBlockInfoPersist.upload_id);
            }
            contentValues.put(GEN_TIME, Long.valueOf(kssUploadInfo.getGenerateTime()));
            contentValues.put(RECORD_IV, kssUploadInfo.getRecordIV());
            contentValues.put(APPKEY_VERSION, Long.valueOf(kssUploadInfo.getAppKeyVersion()));
            contentValues.put(ENCRYPTED_RECORD_KEY, kssUploadInfo.getEncryptedRecordKey());
            getWritableDatabase().replace(TABLE_UPLOAD, null, contentValues);
        }

        public void delete(int i) {
            getWritableDatabase().delete(TABLE_UPLOAD, WHERE_QUERY, new String[]{String.valueOf(i)});
        }
    }

    private static Looper getCommonLooper() {
        Looper looper = sLooper;
        Looper looper2 = null;
        Thread thread = looper == null ? null : looper.getThread();
        if (thread == null || !thread.isAlive()) {
            looper = null;
        }
        if (looper != null) {
            return looper;
        }
        synchronized (KssMaster.class) {
            Looper looper3 = sLooper;
            Thread thread2 = looper3 == null ? null : looper3.getThread();
            if (thread2 != null && thread2.isAlive()) {
                looper2 = looper3;
            }
            if (looper2 == null) {
                HandlerThread handlerThread = new HandlerThread("KssMaster - UploadRecorder", 10);
                handlerThread.start();
                looper2 = handlerThread.getLooper();
            }
        }
        return looper2;
    }
}
