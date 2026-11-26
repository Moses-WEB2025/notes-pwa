package com.miui.notes.cloudservice;

import android.database.Cursor;
import com.miui.notes.provider.Notes;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class FolderCache {
    private static final int FOLDER_COLUMN_ID_INDEX = 0;
    private static final int FOLDER_COLUMN_SUBJECT_INDEX = 2;
    private static final int FOLDER_COLUMN_SYNC_ID_INDEX = 1;
    private static final String[] FOLDER_PROJECTIONS = {"_id", "sync_id", "subject"};
    private static final String FOLDER_SELECTION_ALL = "type=1";
    private NoteSyncContext mSyncContext;
    private HashMap<String, Long> sDigestMap = new HashMap<>();
    private HashMap<Long, Long> sSyncMap = new HashMap<>();
    private HashMap<Long, Long> sIdMap = new HashMap<>();

    public static FolderCache create(NoteSyncContext noteSyncContext) throws NoteSyncException {
        FolderCache folderCache = new FolderCache(noteSyncContext);
        folderCache.load();
        return folderCache;
    }

    private FolderCache(NoteSyncContext noteSyncContext) {
        this.mSyncContext = noteSyncContext;
    }

    private void load() throws NoteSyncException {
        Cursor cursorQuery = this.mSyncContext.getContext().getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, FOLDER_PROJECTIONS, FOLDER_SELECTION_ALL, null, null);
        if (cursorQuery == null) {
            throw new NoteSyncException("Fail to load cache for folder, cursor=null");
        }
        while (cursorQuery.moveToNext()) {
            try {
                long j = cursorQuery.getLong(0);
                String string = cursorQuery.getString(2);
                long j2 = cursorQuery.getLong(1);
                this.sDigestMap.put(string, Long.valueOf(j));
                if (j2 > 0) {
                    this.sSyncMap.put(Long.valueOf(j2), Long.valueOf(j));
                    this.sIdMap.put(Long.valueOf(j), Long.valueOf(j2));
                }
            } finally {
                cursorQuery.close();
            }
        }
    }

    public void add(long j, long j2) {
        this.sSyncMap.put(Long.valueOf(j2), Long.valueOf(j));
        this.sIdMap.put(Long.valueOf(j), Long.valueOf(j2));
    }

    public void remove(long j, long j2) {
        this.sSyncMap.remove(Long.valueOf(j2));
        this.sIdMap.remove(Long.valueOf(j));
    }

    public long getFolderIdBySubject(String str) {
        Long l = this.sDigestMap.get(str);
        if (l == null) {
            return Long.MIN_VALUE;
        }
        return l.longValue();
    }

    public long getFolderIdBySyncId(long j) {
        Long l = this.sSyncMap.get(Long.valueOf(j));
        if (l == null) {
            return Long.MIN_VALUE;
        }
        return l.longValue();
    }

    public long getSyncIdByFolderId(long j) {
        Long l = this.sIdMap.get(Long.valueOf(j));
        if (l == null) {
            return Long.MIN_VALUE;
        }
        return l.longValue();
    }
}
