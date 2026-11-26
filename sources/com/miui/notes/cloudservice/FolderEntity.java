package com.miui.notes.cloudservice;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.provider.Notes;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class FolderEntity extends BaseEntity {
    private static final int FOLDER_COLUMN_CREATED_DATE_INDEX = 2;
    private static final int FOLDER_COLUMN_ID_INDEX = 0;
    private static final int FOLDER_COLUMN_LOCAL_MODIFIED_INDEX = 5;
    private static final int FOLDER_COLUMN_MODIFIED_DATE_INDEX = 3;
    private static final int FOLDER_COLUMN_NOTES_COUNT_INDEX = 9;
    private static final int FOLDER_COLUMN_PARENT_ID_INDEX = 1;
    private static final int FOLDER_COLUMN_RETRIABLE_DATE_INDEX = 10;
    private static final int FOLDER_COLUMN_STICK_DATE_INDEX = 11;
    private static final int FOLDER_COLUMN_SUBJECT_INDEX = 7;
    private static final int FOLDER_COLUMN_SYNC_ID_INDEX = 4;
    private static final int FOLDER_COLUMN_SYNC_TAG_INDEX = 8;
    private static final int FOLDER_COLUMN_VERSION_INDEX = 6;
    private static final String FOLDER_SELECTION_ALL = "type=1";
    private static final String FOLDER_SELECTION_CLEAN_AND_RESYNC = "type=1 AND sync_data2>0";
    private static final String FOLDER_SELECTION_CLEAN_AND_RESYNC_AND_EMPTY = "type=1 AND sync_data2>0 AND notes_count=0";
    private static final String FOLDER_SELECTION_CLEAN_AND_RESYNC_AND_NOT_EMPTY = "type=1 AND sync_data2>0 AND notes_count>0";
    private static final String FOLDER_SELECTION_DIRTY = "type=1 AND local_modified=1";
    private static final String FOLDER_SELECTION_DIRTY_AND_RETRIABLE = "type=1 AND local_modified=1 AND (sync_data1>? OR sync_data1<?)";
    private static final String FOLDER_SELECTION_DIRTY_AND_UNRETRIABLE = "type=1 AND local_modified=1 AND sync_data1>0";
    private static final String FOLDER_SELECTION_SYNCED = "type=1 AND sync_id>0";
    private static final String FOLDER_SELECTION_UNRETRIABLE = "type=1 AND sync_data1>0";
    private static final String TAG = "FolderEntity";
    private long mCreatedDate;
    private long mEndpointType;
    private long mId;
    private int mLocalModified;
    private long mModifiedDate;
    private int mNotesCount;
    private long mParentId;
    private long mRetriableDate;
    private long mStickDate;
    private String mSubject;
    private final NoteSyncContext mSyncContext;
    private long mSyncId;
    private String mSyncTag;
    private int mVersion;
    private static final String[] FOLDER_PROJECTIONS = {"_id", "parent_id", "created_date", "modified_date", "sync_id", Notes.Note.LOCAL_MODIFIED, "version", "subject", "sync_tag", Notes.Note.NOTES_COUNT, Notes.Note.SYNC_DATA1, Notes.Note.STICK_DATE};
    private static final long[] LOCAL_PREDEFINED_IDS = {0, -2, -4, -5, -6, -7, -8};
    private static final long[] SERVER_PREDEFINED_IDS = {0, 1, 2, 3, 4, 5, 6};

    public static long serverIdToLocalId(NoteSyncContext noteSyncContext, long j) throws NoteSyncException {
        if (j == 0) {
            return 0L;
        }
        if (j == 1) {
            return -2L;
        }
        if (j == 2) {
            return -4L;
        }
        if (j == 3) {
            return -5L;
        }
        if (j == 4) {
            return -6L;
        }
        if (j == 5) {
            return -7L;
        }
        if (j == 6) {
            return -8L;
        }
        if (j <= 10) {
            return -3L;
        }
        return noteSyncContext.getFolderCache().getFolderIdBySyncId(j);
    }

    public static FolderEntity valueOf(NoteSyncContext noteSyncContext, Cursor cursor) {
        FolderEntity folderEntity = new FolderEntity(noteSyncContext, 1);
        folderEntity.mId = cursor.getLong(0);
        folderEntity.mParentId = cursor.getLong(1);
        folderEntity.mCreatedDate = cursor.getLong(2);
        folderEntity.mModifiedDate = cursor.getLong(3);
        folderEntity.mSyncId = cursor.getLong(4);
        folderEntity.mLocalModified = cursor.getInt(5);
        folderEntity.mVersion = cursor.getInt(6);
        folderEntity.mSubject = cursor.getString(7);
        folderEntity.mSyncTag = cursor.getString(8);
        folderEntity.mNotesCount = cursor.getInt(9);
        folderEntity.mRetriableDate = cursor.getLong(10);
        folderEntity.mStickDate = cursor.getLong(11);
        return folderEntity;
    }

    public static FolderEntity valueOf(NoteSyncContext noteSyncContext, JSONObject jSONObject) throws JSONException {
        FolderEntity folderEntity = new FolderEntity(noteSyncContext, 2);
        folderEntity.mSyncId = jSONObject.optLong("id");
        folderEntity.mSyncTag = NotesUtils.formatLong(jSONObject.optLong("tag"));
        folderEntity.mCreatedDate = jSONObject.getLong(NotesConstants.JSON_KEY_CREATE_DATE);
        folderEntity.mModifiedDate = jSONObject.getLong(NotesConstants.JSON_KEY_MODIFY_DATE);
        folderEntity.mSubject = jSONObject.getString("subject");
        folderEntity.mLocalModified = 0;
        if ("normal".equals(jSONObject.getString("status"))) {
            folderEntity.mParentId = 0L;
        } else {
            folderEntity.mParentId = -3L;
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("setting");
        if (jSONObjectOptJSONObject != null) {
            long jOptLong = jSONObjectOptJSONObject.optLong(NotesConstants.JSON_KEY_STICK_DATE, 0L);
            folderEntity.mStickDate = jOptLong;
            if (jOptLong < 0) {
                folderEntity.mStickDate = 0L;
            }
        }
        return folderEntity;
    }

    public static Cursor getDirtyCursor(Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return context.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, FOLDER_PROJECTIONS, FOLDER_SELECTION_DIRTY_AND_RETRIABLE, new String[]{Long.toString(jCurrentTimeMillis + 86400000), Long.toString(jCurrentTimeMillis - 86400000)}, null);
    }

    public static Set<Long> getUnRetriableFolders(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"_id"}, FOLDER_SELECTION_UNRETRIABLE, null, null);
        if (cursorQuery == null) {
            return Collections.emptySet();
        }
        try {
            HashSet hashSet = new HashSet(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                hashSet.add(Long.valueOf(cursorQuery.getLong(0)));
            }
            cursorQuery.close();
            return hashSet;
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    public static int moveNotes(Context context, long j, long j2) throws NoteSyncException {
        if (checkFolderExists(context, j2)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("parent_id", Long.valueOf(j2));
            contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
            return context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "parent_id=" + j, null);
        }
        throw new NoteSyncException("Can't find destination folder, folderId=" + j2);
    }

    public static void markFolderDirty(Context context, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "_id=" + j, null);
    }

    public static void setupResync(Context context) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(Notes.Note.SYNC_DATA2, Long.valueOf(System.currentTimeMillis()));
        context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, FOLDER_SELECTION_SYNCED, null);
    }

    public static void completeResync(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        int iDelete = contentResolver.delete(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, FOLDER_SELECTION_CLEAN_AND_RESYNC_AND_EMPTY, null);
        if (iDelete > 0) {
            Logger.INSTANCE.e(TAG, "completeResync: delete " + iDelete + " empty folders");
        }
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        int iUpdate = contentResolver.update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, FOLDER_SELECTION_CLEAN_AND_RESYNC_AND_NOT_EMPTY, null);
        if (iUpdate > 0) {
            Logger.INSTANCE.e(TAG, "completeResync: update " + iUpdate + " folders as dirty");
        }
    }

    private static boolean checkFolderExists(Context context, long j) throws NoteSyncException {
        Cursor cursorQuery = context.getContentResolver().query(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, j), new String[]{"_id"}, null, null, null);
        if (cursorQuery == null) {
            throw new NoteSyncException("Fail to checkFolderExists, cursor is null, folderId=" + j);
        }
        try {
            return cursorQuery.getCount() > 0;
        } finally {
            cursorQuery.close();
        }
    }

    public static long getFolderId(NoteSyncContext noteSyncContext, long j) throws NoteSyncException {
        long localPredefinedId = getLocalPredefinedId(j);
        return localPredefinedId == Long.MIN_VALUE ? noteSyncContext.getFolderCache().getFolderIdBySyncId(j) : localPredefinedId;
    }

    public static long getSyncId(NoteSyncContext noteSyncContext, long j) throws NoteSyncException {
        long serverPredefinedId = getServerPredefinedId(j);
        return serverPredefinedId == Long.MIN_VALUE ? noteSyncContext.getFolderCache().getSyncIdByFolderId(j) : serverPredefinedId;
    }

    private static long getLocalPredefinedId(long j) {
        int i = 0;
        while (true) {
            long[] jArr = SERVER_PREDEFINED_IDS;
            if (i >= jArr.length) {
                return Long.MIN_VALUE;
            }
            if (jArr[i] == j) {
                return LOCAL_PREDEFINED_IDS[i];
            }
            i++;
        }
    }

    private static long getServerPredefinedId(long j) {
        int i = 0;
        while (true) {
            long[] jArr = LOCAL_PREDEFINED_IDS;
            if (i >= jArr.length) {
                return Long.MIN_VALUE;
            }
            if (jArr[i] == j) {
                return SERVER_PREDEFINED_IDS[i];
            }
            i++;
        }
    }

    private FolderEntity(NoteSyncContext noteSyncContext, int i) {
        this.mSyncContext = noteSyncContext;
        this.mEndpointType = i;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public String getCkey(String str) {
        return String.format(Locale.US, "%d-%d-%s", Long.valueOf(this.mId), Integer.valueOf(this.mVersion), str);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public void setCkey(String str) {
        String[] strArrSplit = str.split("-");
        this.mId = Long.parseLong(strArrSplit[0]);
        this.mVersion = Integer.parseInt(strArrSplit[1]);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public long getSyncId() {
        return this.mSyncId;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public String getSyncTag() {
        return this.mSyncTag;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public String getPath(String str) {
        if (isDeleted()) {
            return NotesUtils.formatUrl("/mic/note/v3/user/full/%d/delete", Long.valueOf(this.mSyncId));
        }
        long j = this.mSyncId;
        if (j > 0) {
            return NotesUtils.formatUrl(NotesConstants.URL_FOLDER_ITEM, Long.valueOf(j));
        }
        return NotesUtils.formatUrl(NotesConstants.URL_FOLDER, new Object[0]);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean isDeleted() {
        return this.mParentId == -3;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean shouldDeleteOnServer() {
        return isDeleted();
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean readySync() throws NoteSyncException {
        if (!isDeleted()) {
            return true;
        }
        if (this.mSyncId <= 0) {
            persist();
            return false;
        }
        return isDeletedEmptyFolder();
    }

    private boolean isDeletedEmptyFolder() throws NoteSyncException {
        Cursor cursorQuery = this.mSyncContext.getContext().getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"COUNT(*)"}, "origin_parent_id=?  AND parent_id=?  AND type=?  AND local_modified!=0  AND sync_id>0", new String[]{String.valueOf(this.mId), String.valueOf(-3), String.valueOf(0)}, null);
        if (cursorQuery == null) {
            throw new NoteSyncException("isDeletedEmptyFolder: cursor is null");
        }
        try {
            if (cursorQuery.moveToNext()) {
                boolean z = cursorQuery.getInt(0) == 0;
                cursorQuery.close();
                return z;
            }
            throw new NoteSyncException("isDeletedEmptyFolder: cursor is empty");
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        long j = this.mSyncId;
        if (j > 0) {
            jSONObject.put("id", j);
        }
        if (!TextUtils.isEmpty(this.mSyncTag)) {
            jSONObject.put("tag", this.mSyncTag);
        }
        jSONObject.put("type", "folder");
        if (isDeleted()) {
            jSONObject.put("status", "deleted");
        } else {
            jSONObject.put("status", "normal");
        }
        jSONObject.put(NotesConstants.JSON_KEY_CREATE_DATE, this.mCreatedDate);
        jSONObject.put(NotesConstants.JSON_KEY_MODIFY_DATE, this.mModifiedDate);
        jSONObject.put("subject", this.mSubject);
        jSONObject.put(NotesConstants.JSON_KEY_STICK_DATE, this.mStickDate);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(NotesConstants.JSON_KEY_STICK_DATE, this.mStickDate);
        if (jSONObject2.length() > 0) {
            jSONObject.put("setting", jSONObject2);
        }
        return jSONObject;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public void resolveConflict(JSONObject jSONObject) throws JSONException, NoteSyncException {
        int i = jSONObject.getInt("code");
        this.mSyncContext.getContext();
        if (i == 51001) {
            Logger.INSTANCE.e(TAG, "Can't find folder, sync_id=" + this.mSyncId);
            if (isDeleted()) {
                Logger.INSTANCE.e(TAG, "Folder is not on server, delete it on local");
                persist();
                return;
            }
            Logger.INSTANCE.e(TAG, "Folder is not on server, mark it as new on local to sync next time");
            this.mSyncId = 0L;
            this.mSyncTag = "";
            this.mRetriableDate = 0L;
            persistSync();
            NoteEntity.markNoteDirty(this.mSyncContext.getContext(), this.mId);
            throw new NoteSyncException("Can't find folder, sync_id=" + this.mSyncId);
        }
        if (i == 51005) {
            Logger.INSTANCE.e(TAG, "Folder is not empty, sync_id=" + this.mSyncId);
            if (isDeleted()) {
                Logger.INSTANCE.e(TAG, "Folder has been deleted on local, but is not empty on server, restore it on local");
                this.mParentId = 0L;
                persist();
                return;
            }
            updateRetriableDate(jSONObject);
            throw new NoteSyncException("Folder has not been deleted, but receive a RESULT_CODE_FOLDER_NOT_EMPTY error", false);
        }
        if (i == 0) {
            Logger.INSTANCE.e(TAG, "Result code is ok, sync_id=" + this.mSyncId);
            JSONObject responseDataObject = NoteSchemaUtils.getResponseDataObject(jSONObject);
            boolean zOptBoolean = responseDataObject.optBoolean(NotesConstants.JSON_KEY_CONFLICT, false);
            this.mRetriableDate = 0L;
            if (!zOptBoolean) {
                if (this.mSyncId > 0) {
                    this.mSyncTag = responseDataObject.getString("tag");
                    this.mLocalModified = 0;
                    try {
                        persist();
                        return;
                    } catch (NoteSyncException e) {
                        Logger.INSTANCE.i(TAG, "Fail to persist modified flag" + e);
                        persistSync();
                        return;
                    }
                }
                JSONObject jSONObject2 = responseDataObject.getJSONObject(NotesConstants.JSON_KEY_ENTRY);
                this.mSyncId = jSONObject2.getLong("id");
                this.mSyncTag = jSONObject2.getString("tag");
                this.mLocalModified = 0;
                try {
                    persist();
                    return;
                } catch (NoteSyncException e2) {
                    Logger.INSTANCE.i(TAG, "Fail to persist modified flag" + e2.getMessage());
                    persistSync();
                    return;
                }
            }
            Logger.INSTANCE.e(TAG, "Conflict with folder on server, id=" + this.mId);
            FolderEntity folderEntityValueOf = valueOf(this.mSyncContext, responseDataObject.getJSONObject(NotesConstants.JSON_KEY_CONFLICT_ENTRY));
            if (isDeleted()) {
                Logger.INSTANCE.e(TAG, "Folder has been deleted on local");
                if (folderEntityValueOf.isDeleted()) {
                    Logger.INSTANCE.e(TAG, "Resolve conflict: server folder is deleted, delete folder " + this.mId);
                    persist();
                    return;
                } else {
                    updateRetriableDate(jSONObject);
                    throw new NoteSyncException("Folder has not been deleted on server, but has been deleted on local, sync_id=" + this.mSyncId, false);
                }
            }
            Logger.INSTANCE.e(TAG, "Folder has not been deleted on local");
            long folderIdBySyncId = this.mSyncContext.getFolderCache().getFolderIdBySyncId(folderEntityValueOf.mSyncId);
            if (folderIdBySyncId > 0) {
                Logger.INSTANCE.e(TAG, "Resolve conflict: move notes from folder " + this.mId + " to folder " + folderIdBySyncId);
                int iMoveNotes = moveNotes(this.mSyncContext.getContext(), this.mId, folderIdBySyncId);
                Logger.INSTANCE.e(TAG, "Resolve conflict: " + iMoveNotes + " notes has moved");
                if (iMoveNotes <= 0) {
                    Logger.INSTANCE.e(TAG, "Resolve conflict: mark folder " + this.mId + " as deleted");
                    markDelete();
                    return;
                }
                return;
            }
            Logger.INSTANCE.e(TAG, "Resolve conflict: overwrite notes in folder " + this.mId + " with sync_id " + folderEntityValueOf.mSyncId);
            folderEntityValueOf.persist();
            int iMoveNotes2 = moveNotes(this.mSyncContext.getContext(), this.mId, folderEntityValueOf.mId);
            Logger.INSTANCE.e(TAG, "Resolve conflict: " + iMoveNotes2 + " notes has moved");
            if (iMoveNotes2 <= 0) {
                Logger.INSTANCE.e(TAG, "Resolve conflict: mark folder " + this.mId + " deleted");
                markDelete();
                return;
            }
            return;
        }
        updateRetriableDate(jSONObject);
        throw new NoteSyncException("Received unknown code: " + i);
    }

    private void updateRetriableDate(JSONObject jSONObject) throws JSONException, NoteSyncException {
        if (jSONObject.getBoolean("retriable")) {
            this.mRetriableDate = 0L;
        } else {
            this.mRetriableDate = System.currentTimeMillis();
        }
        persistSync();
    }

    private void persistSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", Long.valueOf(this.mSyncId));
        contentValues.put("sync_tag", this.mSyncTag);
        contentValues.put(Notes.Note.SYNC_DATA1, Long.valueOf(this.mRetriableDate));
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        this.mSyncContext.getContext().getContentResolver().update(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), contentValues, null, null);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public void persist() throws NoteSyncException {
        long j = this.mEndpointType;
        if (j == 1) {
            if (isDeleted()) {
                delete(false, true);
                return;
            } else {
                update(false, true);
                return;
            }
        }
        if (j == 2) {
            long folderIdBySyncId = this.mSyncContext.getFolderCache().getFolderIdBySyncId(this.mSyncId);
            this.mId = folderIdBySyncId;
            if (folderIdBySyncId > 0) {
                if (isDeleted()) {
                    delete(true, false);
                    return;
                } else {
                    update(true, false);
                    return;
                }
            }
            long folderIdBySubject = this.mSyncContext.getFolderCache().getFolderIdBySubject(this.mSubject);
            this.mId = folderIdBySubject;
            if (folderIdBySubject > 0) {
                if (isDeleted()) {
                    return;
                }
                update(false, false);
                return;
            } else {
                if (isDeleted()) {
                    return;
                }
                insert();
                return;
            }
        }
        throw new NoteSyncException("Can't persist temporary note", false);
    }

    private void insert() throws NoteSyncException {
        Uri uriInsert = this.mSyncContext.getContext().getContentResolver().insert(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, getContentValues());
        long j = Long.parseLong(uriInsert != null ? uriInsert.getLastPathSegment() : "0");
        this.mId = j;
        if (j <= 0) {
            throw new NoteSyncException("Fail to update folder, sync_id=" + this.mSyncId);
        }
        if (this.mSyncId > 0) {
            this.mSyncContext.getFolderCache().add(this.mId, this.mSyncId);
        }
    }

    private void update(boolean z, boolean z2) throws NoteSyncException {
        ContentValues contentValues = getContentValues();
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId);
        Object[] selectionParams = getSelectionParams(z, z2);
        String str = (String) selectionParams[0];
        String[] strArr = (String[]) selectionParams[1];
        this.mSyncContext.getContext();
        if (this.mSyncContext.getContext().getContentResolver().update(uriWithAppendedId, contentValues, str, strArr) <= 0) {
            if (this.mEndpointType == 2) {
                if (resolveRetriableConflict(uriWithAppendedId, contentValues)) {
                    updateCacheAfterUpdate();
                    return;
                } else if (this.mSyncContext.isResync()) {
                    Logger.INSTANCE.e(TAG, "Ignore update error while resync, mId=" + this.mId);
                    return;
                }
            }
            throw new NoteSyncException("Fail to update folder, id=" + this.mId);
        }
        updateCacheAfterUpdate();
    }

    private boolean resolveRetriableConflict(Uri uri, ContentValues contentValues) throws NoteSyncException {
        Cursor cursorQuery = this.mSyncContext.getContext().getContentResolver().query(uri, FOLDER_PROJECTIONS, FOLDER_SELECTION_DIRTY_AND_UNRETRIABLE, null, null);
        boolean z = false;
        if (cursorQuery == null) {
            return false;
        }
        try {
            if (cursorQuery.getCount() > 0) {
                this.mSyncContext.getContext().getContentResolver().update(uri, contentValues, null, null);
                z = true;
            }
            return z;
        } finally {
            cursorQuery.close();
        }
    }

    private void updateCacheAfterUpdate() throws NoteSyncException {
        if (this.mSyncId > 0) {
            this.mSyncContext.getFolderCache().add(this.mId, this.mSyncId);
        }
    }

    private void delete(boolean z, boolean z2) throws NoteSyncException {
        Object[] selectionParams = getSelectionParams(z, z2);
        String str = (String) selectionParams[0];
        if (this.mSyncContext.getContext().getContentResolver().delete(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), "(" + str + ") AND notes_count=0", (String[]) selectionParams[1]) <= 0) {
            Logger.INSTANCE.e(TAG, "Fail to delete folder, id=" + this.mId);
        }
        this.mSyncContext.getFolderCache().remove(this.mId, this.mSyncId);
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", Long.valueOf(this.mSyncId));
        contentValues.put("sync_tag", this.mSyncTag);
        contentValues.put("created_date", Long.valueOf(this.mCreatedDate));
        contentValues.put("modified_date", Long.valueOf(this.mModifiedDate));
        contentValues.put("subject", this.mSubject);
        contentValues.put("parent_id", Long.valueOf(this.mParentId));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, Integer.valueOf(this.mLocalModified));
        contentValues.put(Notes.Note.SYNC_DATA1, Long.valueOf(this.mRetriableDate));
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        contentValues.put("type", (Integer) 1);
        contentValues.put("account_id", Long.valueOf(AccountEntity.getAccountId()));
        contentValues.put(Notes.Note.STICK_DATE, Long.valueOf(this.mStickDate));
        return contentValues;
    }

    private Object[] getSelectionParams(boolean z, boolean z2) {
        String str;
        String[] strArr = null;
        String str2 = z ? "local_modified=0" : null;
        if (z2) {
            if (str2 == null) {
                str = "";
            } else {
                str = str2 + " AND ";
            }
            str2 = str + "version=" + this.mVersion;
        }
        if (str2 == null) {
            strArr = new String[]{this.mSubject, Long.toString(this.mParentId)};
            str2 = "subject=? AND parent_id=?";
        }
        return new Object[]{str2, strArr};
    }

    private void markDelete() {
        String[] strArr = {Long.toString(this.mId), Long.toString(this.mVersion)};
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", (Integer) (-3));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        this.mSyncContext.getContext().getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "_id=? AND notes_count=0 AND version=?", strArr);
    }

    public String toString() {
        return String.format("[%s]: id=%d, parent_id=%d, sync_id=%d, sync_tag=%s, modifiedDate=%d, size=%d", TAG, Long.valueOf(this.mId), Long.valueOf(this.mParentId), Long.valueOf(this.mSyncId), this.mSyncTag, Long.valueOf(this.mModifiedDate), Integer.valueOf(this.mSubject.length()));
    }
}
