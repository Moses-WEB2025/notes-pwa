package com.miui.notes.cloudservice;

import android.database.Cursor;
import com.miui.notes.base.utils.Logger;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class NoteCache implements CursorProxy.CallBack {
    private NoteSyncContext mSyncContext;
    private HashMap<String, Long> sDigestMap = new HashMap<>();
    private HashMap<Long, Long> sSyncMap = new HashMap<>();
    private HashMap<Long, Long> sIdMap = new HashMap<>();
    private HashMap<Long, Integer> sVersionMap = new HashMap<>();

    public static NoteCache create(NoteSyncContext noteSyncContext) throws NoteSyncException {
        NoteCache noteCache = new NoteCache(noteSyncContext);
        noteCache.load();
        return noteCache;
    }

    private NoteCache(NoteSyncContext noteSyncContext) {
        this.mSyncContext = noteSyncContext;
    }

    public void load() throws NoteSyncException {
        AutoCloseable autoCloseable = null;
        try {
            CursorProxy allCursor = NoteEntity.getAllCursor(this.mSyncContext.getContext(), this);
            if (allCursor != null) {
                allCursor.traverse();
                if (allCursor != null) {
                    allCursor.close();
                    return;
                }
                return;
            }
            throw new NoteSyncException("Cursor illegalStateException,finish", false);
        } catch (Throwable th) {
            if (0 != 0) {
                autoCloseable.close();
            }
            throw th;
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

    public long getCachedNote(String str, long j) throws NoteSyncException {
        Long l = this.sDigestMap.get(NoteEntity.getDigest(str, j));
        if (l == null) {
            return Long.MIN_VALUE;
        }
        return l.longValue();
    }

    public long getCachedNote(long j) {
        Long l = this.sSyncMap.get(Long.valueOf(j));
        if (l == null) {
            return Long.MIN_VALUE;
        }
        return l.longValue();
    }

    public boolean exists(long j) {
        return this.sIdMap.containsKey(Long.valueOf(j));
    }

    @Override // com.miui.notes.cloudservice.CursorProxy.CallBack
    public void onReload() {
        this.sDigestMap.clear();
        this.sSyncMap.clear();
        this.sIdMap.clear();
        this.sVersionMap.clear();
    }

    @Override // com.miui.notes.cloudservice.CursorProxy.CallBack
    public void onMoveToNext(Cursor cursor) throws NoteSyncException {
        long j;
        NoteEntity noteEntityValueOf = NoteEntity.valueOf(this.mSyncContext, cursor);
        if (noteEntityValueOf.isContentValid()) {
            String digest = noteEntityValueOf.getDigest();
            Long l = this.sDigestMap.get(digest);
            Integer num = this.sVersionMap.get(l);
            if (l == null || num == null) {
                j = 0;
            } else {
                j = 0;
                if (noteEntityValueOf.getSyncId() > 0) {
                    Long l2 = this.sIdMap.get(l);
                    if (l2 == null) {
                        NoteEntity noteEntityValueOf2 = NoteEntity.valueOf(this.mSyncContext, cursor);
                        noteEntityValueOf2.mId = l.longValue();
                        noteEntityValueOf2.mVersion = num.intValue();
                        Logger.INSTANCE.i("NoteCache", "onMoveToNext digest=" + digest + " mId=" + noteEntityValueOf.mId + " existsId=" + l + " existVersion=" + num + " sIdMap.get(existsId)=" + l2 + " entity.getSyncId()=" + noteEntityValueOf.getSyncId());
                        noteEntityValueOf2.deleteWithoutCacheUpdate(false, true);
                    } else {
                        Logger.INSTANCE.i("NoteCache", "same digest");
                    }
                } else {
                    Logger.INSTANCE.i("NoteCache", "onMoveToNext digest=" + digest + " mId=" + noteEntityValueOf.mId + " existsId=" + l + " existVersion=" + num + " sIdMap.get(existsId)=" + this.sIdMap.get(l) + " entity.getSyncId()=" + noteEntityValueOf.getSyncId());
                    noteEntityValueOf.deleteWithoutCacheUpdate(false, true);
                    return;
                }
            }
            this.sDigestMap.put(digest, Long.valueOf(noteEntityValueOf.mId));
            this.sVersionMap.put(Long.valueOf(noteEntityValueOf.mId), Integer.valueOf(noteEntityValueOf.mVersion));
            if (noteEntityValueOf.getSyncId() > j) {
                this.sSyncMap.put(Long.valueOf(noteEntityValueOf.getSyncId()), Long.valueOf(noteEntityValueOf.mId));
                this.sIdMap.put(Long.valueOf(noteEntityValueOf.mId), Long.valueOf(noteEntityValueOf.getSyncId()));
            }
        }
    }

    @Override // com.miui.notes.cloudservice.CursorProxy.CallBack
    public void onCrash() throws CursorProxy.CursorCrashException {
        throw new CursorProxy.CursorCrashException();
    }

    public Integer getCachedVersion(long j) {
        return this.sVersionMap.get(Long.valueOf(j));
    }
}
