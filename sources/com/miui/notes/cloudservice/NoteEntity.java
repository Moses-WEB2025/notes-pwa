package com.miui.notes.cloudservice;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.miui.common.tool.Logger;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.xiaomi.e2ee.appkey.KeyEntry;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import miuix.security.DigestUtils;
import miuix.text.utilities.ExtraTextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NoteEntity extends BaseEntity {
    private static final int NOTE_COLUMN_ALERTED_DATE_INDEX = 2;
    private static final int NOTE_COLUMN_ALERT_TAG_INDEX = 15;
    private static final int NOTE_COLUMN_BG_COLOR_ID_INDEX = 3;
    private static final int NOTE_COLUMN_CREATED_DATE_INDEX = 4;
    private static final int NOTE_COLUMN_ENT_NAME_INDEX = 20;
    private static final int NOTE_COLUMN_ID_INDEX = 0;
    private static final int NOTE_COLUMN_IMAGES_INDEX = 21;
    private static final int NOTE_COLUMN_IS_EMPTY_INDEX = 24;
    private static final int NOTE_COLUMN_LOCAL_MODIFIED_INDEX = 7;
    private static final int NOTE_COLUMN_MIND_CONTENT_INDEX = 18;
    private static final int NOTE_COLUMN_MIND_CONTENT_PLAIN_TEXT_INDEX = 19;
    private static final int NOTE_COLUMN_MODIFIED_DATE_INDEX = 5;
    private static final int NOTE_COLUMN_NOTE_TYPE_INDEX = 17;
    private static final int NOTE_COLUMN_ORIGIN_PARENT_ID_INDEX = 8;
    private static final int NOTE_COLUMN_PARENT_ID_INDEX = 1;
    private static final int NOTE_COLUMN_RECOGNIZED_FILE_NAMES_INDEX = 23;
    private static final int NOTE_COLUMN_RETRIABLE_DATE_INDEX = 13;
    private static final int NOTE_COLUMN_SNIPPET_INDEX = 11;
    private static final int NOTE_COLUMN_STICK_DATE_INDEX = 12;
    private static final int NOTE_COLUMN_SYNC_ID_INDEX = 6;
    private static final int NOTE_COLUMN_SYNC_TAG_INDEX = 10;
    private static final int NOTE_COLUMN_THEME_ID_INDEX = 14;
    private static final int NOTE_COLUMN_THUMBNAIL_INDEX = 22;
    private static final int NOTE_COLUMN_TITLE_INDEX = 16;
    private static final int NOTE_COLUMN_VERSION_INDEX = 9;
    private static final int NOTE_COLUMN_WEB_IMAGES_INDEX = 25;
    private static final String[] NOTE_PROJECTIONS = {"_id", "parent_id", Notes.Note.ALERTED_DATE, Notes.Note.BG_COLOR_ID, "created_date", "modified_date", "sync_id", Notes.Note.LOCAL_MODIFIED, "origin_parent_id", "version", "sync_tag", "snippet", Notes.Note.STICK_DATE, Notes.Note.SYNC_DATA1, "theme_id", Notes.Note.ALERT_TAG, "title", Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT, Notes.Note.MIND_CONTENT_PLAIN_TEXT, "ent_name", Notes.Note.IMAGES, "thumbnail", "recognized_file_names", Notes.Note.IS_EMPTY, Notes.Note.WEB_IMAGES};
    private static final String NOTE_SELECTION_ALL = "type=0";
    private static final String NOTE_SELECTION_CLEAN_AND_RESYNC = "type=0 AND local_modified=0 AND sync_data2>0";
    private static final String NOTE_SELECTION_DIRTY = "type=0 AND local_modified=1";
    private static final String NOTE_SELECTION_DIRTY_AND_RETRIABLE = "type=0 AND local_modified=1 AND (sync_data1>? OR sync_data1<?)";
    private static final String NOTE_SELECTION_DIRTY_AND_UNRETRIABLE = "type=0 AND local_modified=1 AND sync_data1>0";
    private static final String NOTE_SELECTION_ISOLATED = "type=0 AND parent_id=-1";
    private static final String NOTE_SELECTION_SYNCED = "type=0 AND sync_id>0";
    private static final String TAG = "NoteEntity";
    private boolean isEmpty;
    private long mAlertDate;
    private int mAlertTag;
    private int mBgColorId;
    private long mCreatedDate;
    private ArrayList<DataEntity> mDatas;
    private final int mEndpointType;
    long mId;
    private int mLocalModified;
    private long mModifiedDate;
    private long mOriginParentId;
    private long mParentId;
    private long mRetriableDate;
    private String mSnippet;
    private long mStickDate;
    private final NoteSyncContext mSyncContext;
    private long mSyncId;
    private String mSyncTag;
    private int mThemeId;
    private String mTitle;
    int mVersion;
    private String mNoteType = "common";
    private String mMindContent = "";
    private String mMindContentPlainText = "";
    private String mEntName = "";
    private String mImages = "";
    private String mThumbnail = "";
    private String mRecognizedText = "";
    private String mWebImages = "";

    public static NoteEntity valueOf(NoteSyncContext noteSyncContext, Cursor cursor) {
        NoteEntity noteEntity = new NoteEntity(noteSyncContext, 1);
        noteEntity.mId = cursor.getLong(0);
        noteEntity.mParentId = cursor.getLong(1);
        noteEntity.mAlertDate = cursor.getLong(2);
        noteEntity.mBgColorId = cursor.getInt(3);
        noteEntity.mThemeId = cursor.getInt(14);
        noteEntity.mCreatedDate = cursor.getLong(4);
        noteEntity.mModifiedDate = cursor.getLong(5);
        noteEntity.mSyncId = cursor.getLong(6);
        noteEntity.mLocalModified = cursor.getInt(7);
        noteEntity.mOriginParentId = cursor.getLong(8);
        noteEntity.mVersion = cursor.getInt(9);
        noteEntity.mSyncTag = cursor.getString(10);
        noteEntity.mSnippet = cursor.getString(11);
        noteEntity.mStickDate = cursor.getLong(12);
        noteEntity.mRetriableDate = cursor.getLong(13);
        noteEntity.mAlertTag = cursor.getInt(15);
        noteEntity.mTitle = cursor.getString(16);
        noteEntity.mNoteType = cursor.getString(17);
        noteEntity.mMindContent = cursor.getString(18);
        noteEntity.mMindContentPlainText = cursor.getString(19);
        noteEntity.mEntName = cursor.getString(20);
        noteEntity.mImages = cursor.getString(21);
        noteEntity.mThumbnail = cursor.getString(22);
        noteEntity.mRecognizedText = cursor.getString(23);
        noteEntity.isEmpty = cursor.getInt(24) == 0;
        noteEntity.mWebImages = cursor.getString(25);
        return noteEntity;
    }

    public static NoteEntity valueOf(NoteSyncContext noteSyncContext, JSONObject jSONObject) throws JSONException, NoteSyncException {
        DataEntity dataEntityNewTextData;
        NoteEntity noteEntity = new NoteEntity(noteSyncContext, 2);
        noteEntity.mSyncId = jSONObject.optLong("id");
        noteEntity.mSyncTag = NotesUtils.formatLong(jSONObject.optLong("tag"));
        noteEntity.mAlertDate = jSONObject.optLong(NotesConstants.JSON_KEY_ALERT_DATE);
        noteEntity.mBgColorId = jSONObject.getInt(NotesConstants.JSON_KEY_COLOR_ID);
        noteEntity.mCreatedDate = jSONObject.getLong(NotesConstants.JSON_KEY_CREATE_DATE);
        noteEntity.mModifiedDate = jSONObject.getLong(NotesConstants.JSON_KEY_MODIFY_DATE);
        noteEntity.mAlertTag = jSONObject.getInt(NotesConstants.JSON_KEY_ALERT_TAG);
        if (jSONObject.has("extraInfo")) {
            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("extraInfo"));
            noteEntity.mTitle = jSONObject2.optString("title", "");
            if (jSONObject2.has(Notes.Note.NOTE_CONTENT_TYPE)) {
                noteEntity.mNoteType = jSONObject2.optString(Notes.Note.NOTE_CONTENT_TYPE);
            }
            if (jSONObject2.has(Notes.Note.MIND_CONTENT)) {
                noteEntity.mMindContent = jSONObject2.optString(Notes.Note.MIND_CONTENT);
            }
            if (jSONObject2.has(Notes.Note.MIND_CONTENT_PLAIN_TEXT)) {
                noteEntity.mMindContentPlainText = jSONObject2.optString(Notes.Note.MIND_CONTENT_PLAIN_TEXT);
            }
            if (noteEntity.isMultiNode()) {
                if (jSONObject2.has("ent_name")) {
                    noteEntity.mEntName = jSONObject2.getString("ent_name");
                }
                if (jSONObject2.has(Notes.Note.IMAGES)) {
                    noteEntity.mImages = jSONObject2.getString(Notes.Note.IMAGES);
                }
                if (jSONObject2.has("thumbnail")) {
                    noteEntity.mThumbnail = jSONObject2.getString("thumbnail");
                }
                if (jSONObject2.has("recognized_file_names")) {
                    noteEntity.mRecognizedText = jSONObject2.getString("recognized_file_names");
                }
                if (jSONObject2.has(Notes.Note.IS_EMPTY)) {
                    noteEntity.isEmpty = jSONObject2.getBoolean(Notes.Note.IS_EMPTY);
                }
                if (jSONObject2.has(Notes.Note.WEB_IMAGES)) {
                    noteEntity.mWebImages = jSONObject2.getString(Notes.Note.WEB_IMAGES);
                }
            }
        }
        noteEntity.mDatas = new ArrayList<>();
        Pair<String, Boolean> pairDecryptData = NoteE2EEManager.decryptData(jSONObject.optString("content", ""), jSONObject.optJSONObject("encryptInfo"));
        String str = (String) pairDecryptData.first;
        if (!((Boolean) pairDecryptData.second).booleanValue()) {
            return null;
        }
        if (noteEntity.isMultiNode()) {
            dataEntityNewTextData = DataEntity.newMultiTextData(str);
            noteEntity.mDatas.add(dataEntityNewTextData);
        } else {
            dataEntityNewTextData = DataEntity.newTextData(str);
            noteEntity.mDatas.add(dataEntityNewTextData);
        }
        String string = jSONObject.getString("status");
        long jOptLong = jSONObject.optLong("folderId");
        long jServerIdToLocalId = FolderEntity.serverIdToLocalId(noteSyncContext, jOptLong);
        if (!"normal".equals(string)) {
            if (jServerIdToLocalId != Long.MIN_VALUE) {
                noteEntity.mOriginParentId = jServerIdToLocalId;
            } else {
                noteEntity.mOriginParentId = jOptLong;
            }
            noteEntity.mParentId = -3L;
        } else if (jServerIdToLocalId != Long.MIN_VALUE) {
            noteEntity.mParentId = jServerIdToLocalId;
        } else {
            noteEntity.mOriginParentId = jOptLong;
            noteEntity.mParentId = -1L;
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("setting");
        if (jSONObjectOptJSONObject != null) {
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("call");
            if (jSONObjectOptJSONObject2 != null) {
                noteEntity.mDatas.add(DataEntity.newCallData(jSONObjectOptJSONObject2.getString("phone"), jSONObjectOptJSONObject2.getLong("time")));
            }
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("data");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = jSONArrayOptJSONArray.getJSONObject(i);
                    String string2 = jSONObject3.getString("fileId");
                    String string3 = jSONObject3.getString("mimeType");
                    String string4 = jSONObject3.getString("digest");
                    if (string3.equals("image/jpg")) {
                        string3 = "image/jpeg";
                    }
                    if (noteEntity.isMultiNode()) {
                        noteEntity.mDatas.add(DataEntity.newMultiNoteFileData(string3, string2, string4));
                    } else {
                        noteEntity.mDatas.add(DataEntity.newFileData(string3, string2, string4));
                    }
                    if (TextUtils.isEmpty(string2)) {
                        Logger.INSTANCE.w(TAG, "valueOf: fileid is empty, digest=" + string4);
                    } else if (!noteEntity.isMultiNode()) {
                        dataEntityNewTextData.setContent(dataEntityNewTextData.getContent().replaceAll(string2, string4));
                    }
                }
            }
            long jOptLong2 = jSONObjectOptJSONObject.optLong(NotesConstants.JSON_KEY_STICK_DATE, 0L);
            noteEntity.mStickDate = jOptLong2;
            if (jOptLong2 < 0) {
                noteEntity.mStickDate = 0L;
            }
            noteEntity.mThemeId = jSONObjectOptJSONObject.optInt("themeId", 0);
            if (!noteEntity.isDeleted()) {
                noteEntity.verify();
            }
        }
        if (noteEntity.isMultiNode()) {
            noteEntity.mSnippet = str;
            return noteEntity;
        }
        if (dataEntityNewTextData != null) {
            noteEntity.mSnippet = dataEntityNewTextData.getContent();
        }
        return noteEntity;
    }

    public static void resolveIsolated(NoteSyncContext noteSyncContext) throws NoteSyncException {
        Cursor cursorQuery = noteSyncContext.getContext().getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, NOTE_PROJECTIONS, NOTE_SELECTION_ISOLATED, null, null);
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    NoteEntity noteEntityValueOf = valueOf(noteSyncContext, cursorQuery);
                    long folderIdBySyncId = noteSyncContext.getFolderCache().getFolderIdBySyncId(noteEntityValueOf.mOriginParentId);
                    if (folderIdBySyncId > 0) {
                        noteEntityValueOf.mParentId = folderIdBySyncId;
                    } else {
                        Logger.INSTANCE.e(TAG, "Cann't find folder(sync_id=" + noteEntityValueOf.mOriginParentId + ") for note(sync_id=" + noteEntityValueOf.mSyncId + ")");
                        noteEntityValueOf.mParentId = 0L;
                        noteEntityValueOf.mLocalModified = 1;
                    }
                    noteEntityValueOf.mOriginParentId = 0L;
                    noteEntityValueOf.persist();
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
            cursorQuery.close();
        }
    }

    public static void markNoteDirty(Context context, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "parent_id=" + j, null);
    }

    public static void setupResync(Context context) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(Notes.Note.SYNC_DATA2, Long.valueOf(System.currentTimeMillis()));
        context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, NOTE_SELECTION_SYNCED, null);
    }

    public static void completeResync(Context context) {
        int iDelete = context.getContentResolver().delete(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, NOTE_SELECTION_CLEAN_AND_RESYNC, null);
        if (iDelete > 0) {
            Logger.INSTANCE.e(TAG, "completeResync: delete " + iDelete + " notes");
        }
    }

    public static CursorProxy getAllCursor(Context context, CursorProxy.CallBack callBack) {
        try {
            return new CursorProxy(context, callBack, Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, NOTE_PROJECTIONS, NOTE_SELECTION_ALL, null, null);
        } catch (CursorProxy.CallBackNullException | CursorProxy.CursorNullException e) {
            Logger.INSTANCE.e(TAG, "getAllCursor with exception:" + e.getMessage());
            return null;
        }
    }

    public static Cursor getDirtyCursor(Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return context.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, NOTE_PROJECTIONS, NOTE_SELECTION_DIRTY_AND_RETRIABLE, new String[]{Long.toString(jCurrentTimeMillis + 86400000), Long.toString(jCurrentTimeMillis - 86400000)}, null);
    }

    private static String getSyncTagById(Context context, long j) {
        Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"sync_tag"}, "_id=" + j, null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return cursorQuery.getString(0);
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    private static boolean clearSyncInfo(NoteSyncContext noteSyncContext, long j, long j2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", (Long) 0L);
        contentValues.put("sync_tag", "");
        contentValues.put(Notes.Note.SYNC_DATA1, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA3, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA4, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA5, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA6, "");
        contentValues.put(Notes.Note.SYNC_DATA7, "");
        contentValues.put(Notes.Note.SYNC_DATA8, "");
        contentValues.put("origin_parent_id", (Integer) 0);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        Context context = noteSyncContext.getContext();
        if (context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "_id=" + j, null) <= 0) {
            return false;
        }
        DataEntity.markDataDirty(context, j);
        try {
            noteSyncContext.getNoteCache().remove(j, j2);
        } catch (NoteSyncException e) {
            Logger.INSTANCE.w(TAG, "Fail to remove cache " + e);
        }
        return true;
    }

    static String getDigest(String str, long j) throws NoteSyncException {
        try {
            return ExtraTextUtils.toHexReadable(DigestUtils.get((NotesUtils.sqliteTrim(str) + "|" + j).getBytes("UTF-8"), "SHA-1"));
        } catch (UnsupportedEncodingException e) {
            throw new NoteSyncException(e);
        }
    }

    private NoteEntity(NoteSyncContext noteSyncContext, int i) {
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
    public String getPath(String str) throws NoteSyncException {
        if (shouldDeleteOnServer()) {
            return NotesUtils.formatUrl("/mic/note/v3/user/full/%d/delete", Long.valueOf(this.mSyncId));
        }
        long j = this.mSyncId;
        if (j > 0) {
            return NotesUtils.formatUrl(NotesConstants.URL_NOTE_ITEM, Long.valueOf(j));
        }
        return NotesUtils.formatUrl(NotesConstants.URL_NOTE, new Object[0]);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean isDeleted() {
        Logger.INSTANCE.d(TAG, "mSyncId" + this.mSyncId + "isDeleted: " + this.mParentId);
        return this.mParentId == -3;
    }

    private List<DataEntity> getDatas() throws NoteSyncException {
        if (this.mDatas == null) {
            this.mDatas = DataEntity.getDatasByNote(this.mSyncContext.getContext(), this.mId);
        }
        return this.mDatas;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean readySync() throws NoteSyncException {
        boolean zShouldSync = shouldSync();
        if (zShouldSync) {
            uploadFiles(this.mSyncContext.getMediaManager());
        }
        return zShouldSync;
    }

    private boolean shouldSync() throws NoteSyncException {
        return isContentValid() && getFolderSyncId() != Long.MIN_VALUE;
    }

    protected boolean isContentValid() {
        if (this.mSyncId <= 0 && TextUtils.isEmpty(this.mSnippet) && TextUtils.isEmpty(this.mTitle)) {
            return this.mNoteType.equals("multi_handwrite") && !this.isEmpty;
        }
        return true;
    }

    private boolean isDataDirty() throws NoteSyncException {
        for (DataEntity dataEntity : getDatas()) {
            NotesUtils.checkInterrupted();
            if (dataEntity.isDirty()) {
                return true;
            }
            if (dataEntity.isFileData() && TextUtils.isEmpty(dataEntity.getFileId()) && dataEntity.isFileExisted(this.mSyncContext.getContext())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public boolean shouldDeleteOnServer() throws NoteSyncException {
        return isDeleted() && !isDataDirty();
    }

    public void uploadFiles(NoteMediaManager noteMediaManager) throws NoteSyncException {
        for (DataEntity dataEntity : getDatas()) {
            NotesUtils.checkInterrupted();
            String accountName = this.mSyncContext.getAccountEntity().getAccountName();
            if (!TextUtils.isEmpty(dataEntity.getFileId()) && !TextUtils.isEmpty(accountName) && !dataEntity.getFileId().startsWith(accountName)) {
                dataEntity.updateFileId(this.mSyncContext.getContext(), "");
            }
            if (dataEntity.isFileData() && (TextUtils.isEmpty(dataEntity.getFileId()) || dataEntity.isNeedPush())) {
                if (dataEntity.isFileExisted(this.mSyncContext.getContext())) {
                    noteMediaManager.upload(dataEntity);
                }
            }
        }
    }

    public long getFolderId() {
        return this.mParentId;
    }

    public long getFolderSyncId() throws NoteSyncException {
        return FolderEntity.getSyncId(this.mSyncContext, isDeleted() ? this.mOriginParentId : this.mParentId);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public JSONObject toJSON() throws JSONException, NoteSyncException {
        String content;
        JSONObject jSONObject = new JSONObject();
        long j = this.mSyncId;
        if (j > 0) {
            jSONObject.put("id", j);
        }
        if (!TextUtils.isEmpty(this.mSyncTag)) {
            jSONObject.put("tag", this.mSyncTag);
        }
        jSONObject.put("type", "note");
        long folderSyncId = getFolderSyncId();
        if (folderSyncId < 0) {
            throw new NoteSyncException("Can't find folder sync id for note, id=" + this.mId, false);
        }
        jSONObject.put("folderId", folderSyncId);
        if (shouldDeleteOnServer()) {
            jSONObject.put("status", "deleted");
        } else {
            jSONObject.put("status", "normal");
        }
        jSONObject.put(NotesConstants.JSON_KEY_ALERT_DATE, this.mAlertDate);
        jSONObject.put(NotesConstants.JSON_KEY_COLOR_ID, this.mBgColorId);
        jSONObject.put(NotesConstants.JSON_KEY_CREATE_DATE, this.mCreatedDate);
        jSONObject.put(NotesConstants.JSON_KEY_MODIFY_DATE, this.mModifiedDate);
        jSONObject.put(NotesConstants.JSON_KEY_ALERT_TAG, this.mAlertTag);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("title", this.mTitle);
        jSONObject2.put(Notes.Note.NOTE_CONTENT_TYPE, this.mNoteType);
        jSONObject2.put(Notes.Note.MIND_CONTENT, getMindContent());
        jSONObject2.put(Notes.Note.MIND_CONTENT_PLAIN_TEXT, this.mMindContentPlainText);
        jSONObject2.put(Notes.Note.WEB_IMAGES, this.mWebImages);
        if (isMultiNode()) {
            jSONObject2.put("ent_name", this.mEntName);
            jSONObject2.put(Notes.Note.IMAGES, this.mImages);
            jSONObject2.put("thumbnail", this.mThumbnail);
            jSONObject2.put("recognized_file_names", this.mRecognizedText);
            jSONObject2.put(Notes.Note.IS_EMPTY, this.isEmpty);
        }
        jSONObject.put("extraInfo", jSONObject2);
        jSONObject.put("extraInfo", jSONObject.getJSONObject("extraInfo").toString());
        if (isMultiNode()) {
            content = this.mSnippet;
        } else {
            Iterator<DataEntity> it = this.mDatas.iterator();
            while (true) {
                if (!it.hasNext()) {
                    content = null;
                    break;
                }
                DataEntity next = it.next();
                if (next.isTextData() && next.isDirty()) {
                    content = next.getContent();
                    break;
                }
            }
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(NotesConstants.JSON_KEY_STICK_DATE, this.mStickDate);
        jSONObject3.put("themeId", this.mThemeId);
        JSONArray jSONArray = new JSONArray();
        for (DataEntity dataEntity : this.mDatas) {
            if (!dataEntity.isTextData()) {
                if (dataEntity.isCallData()) {
                    jSONObject3.put("call", dataEntity.toJSON());
                } else {
                    jSONArray.put(dataEntity.toJSON());
                    if (content != null && !TextUtils.isEmpty(dataEntity.getContent()) && !TextUtils.isEmpty(dataEntity.getFileId())) {
                        content = content.replaceAll(dataEntity.getContent(), dataEntity.getFileId());
                    }
                }
            }
        }
        if (jSONArray.length() > 0) {
            jSONObject3.put("data", jSONArray);
        }
        if (jSONObject3.length() > 0) {
            jSONObject.put("setting", jSONObject3);
        }
        if (content == null) {
            if (this.mSyncId > 0) {
                return jSONObject;
            }
            throw new NoteSyncException("content is null, id=" + this.mId);
        }
        jSONObject.put(NotesConstants.JSON_KEY_CONTENTHA1, NoteE2EEManager.sha1(content));
        if (NoteE2EEManager.isE2EEOpen()) {
            KeyEntry keyEntry = NoteE2EEManager.getKeyEntry();
            if (keyEntry != null) {
                jSONObject.put("encryptInfo", keyEntry.toServerJson());
                jSONObject.put("snippet", NoteE2EEManager.calculateSnippet(keyEntry, content));
                content = NoteE2EEManager.encryptData(keyEntry, content);
            } else {
                jSONObject.put("encryptInfo", "");
            }
        }
        jSONObject.put("content", content);
        return jSONObject;
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public void resolveConflict(JSONObject jSONObject) throws Throwable {
        int i = jSONObject.getInt("code");
        this.mSyncContext.getContext();
        if (i == 51001) {
            Logger.INSTANCE.e(TAG, "Can't find note, sync_id=" + this.mSyncId);
            if (shouldDeleteOnServer()) {
                Logger.INSTANCE.e(TAG, "Note is not on server, mark clean on local");
                this.mLocalModified = 0;
                persist();
                return;
            } else {
                Logger.INSTANCE.e(TAG, "Note is not on server, mark it as new on local to sync next time");
                clearSyncInfo(this.mSyncContext, this.mId, this.mSyncId);
                this.mSyncId = 0L;
                this.mSyncTag = "";
                return;
            }
        }
        if (i == 51002) {
            Logger.INSTANCE.e(TAG, "Can't find folder, mParentId=" + this.mParentId);
            Logger.INSTANCE.e(TAG, "Folder is not on server, mark note as dirty on local to sync next time");
            if (isDeleted()) {
                FolderEntity.markFolderDirty(this.mSyncContext.getContext(), this.mOriginParentId);
                return;
            } else {
                FolderEntity.markFolderDirty(this.mSyncContext.getContext(), this.mParentId);
                return;
            }
        }
        if (i == 51003) {
            Logger.INSTANCE.e(TAG, "Folder is deleted, mParentId=" + this.mParentId);
            Logger.INSTANCE.e(TAG, "Folder has been deleted on server, mark folder as dirty on local to sync next time");
            if (isDeleted()) {
                FolderEntity.markFolderDirty(this.mSyncContext.getContext(), this.mOriginParentId);
                return;
            } else {
                FolderEntity.markFolderDirty(this.mSyncContext.getContext(), this.mParentId);
                return;
            }
        }
        if (i == 0) {
            Logger.INSTANCE.e(TAG, "Result code is ok, sync_id=" + this.mSyncId);
            JSONObject responseDataObject = NoteSchemaUtils.getResponseDataObject(jSONObject);
            boolean zOptBoolean = responseDataObject.optBoolean(NotesConstants.JSON_KEY_CONFLICT, false);
            this.mRetriableDate = 0L;
            if (zOptBoolean) {
                Logger.INSTANCE.e(TAG, "Conflict with note on server, id=" + this.mId);
                NoteEntity noteEntityValueOf = valueOf(this.mSyncContext, responseDataObject.getJSONObject(NotesConstants.JSON_KEY_CONFLICT_ENTRY));
                if (noteEntityValueOf != null) {
                    Logger.INSTANCE.e(TAG, "Conflict entity: " + noteEntityValueOf.toString());
                }
                if (shouldDeleteOnServer() && noteEntityValueOf != null) {
                    noteEntityValueOf.mId = this.mId;
                    noteEntityValueOf.mLocalModified = 0;
                    noteEntityValueOf.mVersion = this.mVersion;
                    noteEntityValueOf.update(false, true);
                    return;
                }
                if (noteEntityValueOf != null && getDigest().equals(noteEntityValueOf.getDigest())) {
                    Logger.INSTANCE.e(TAG, "The content on local is the same as conflict entry, persist sync info only");
                    this.mSyncTag = noteEntityValueOf.getSyncTag();
                    persistSync();
                    return;
                } else {
                    Logger.INSTANCE.e(TAG, "The content on local is different than conflict entry, mark note as new  to sync next time");
                    clearSyncInfo(this.mSyncContext, this.mId, this.mSyncId);
                    this.mSyncId = 0L;
                    this.mSyncTag = "";
                    return;
                }
            }
            Logger.INSTANCE.i(TAG, "resolveConflict() not conflict=== mSyncTag:" + this.mSyncTag + " mSyncId:" + this.mSyncId);
            if (this.mSyncId > 0) {
                this.mSyncTag = responseDataObject.getString("tag");
                onPrePersistSuccess();
                Logger.INSTANCE.i(TAG, "update note that has synced before,mLocalModified should be 0:" + this.mLocalModified);
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
            onPrePersistSuccess();
            Logger.INSTANCE.i(TAG, "update note that has not synced before,mLocalModified should be 0:" + this.mLocalModified);
            try {
                persist();
                return;
            } catch (NoteSyncException e2) {
                Logger.INSTANCE.i(TAG, "Fail to persist modified flag" + e2);
                persistSync();
                return;
            }
        }
        updateRetriableDate(jSONObject);
        throw new NoteSyncException("Received unknown code: " + i);
    }

    private void onPrePersistSuccess() throws NoteSyncException {
        Logger.INSTANCE.i(TAG, "onPrePersistSuccess() mSyncTag:" + this.mSyncTag + " mSyncId:" + this.mSyncId + "mLocalModified" + this.mLocalModified);
        if (!isDeleted() || shouldDeleteOnServer()) {
            this.mLocalModified = 0;
        }
        Logger.INSTANCE.i(TAG, "onPrePersistSuccess() mSyncTag:" + this.mSyncTag + " mSyncId:" + this.mSyncId + "mLocalModified" + this.mLocalModified);
        Iterator<DataEntity> it = getDatas().iterator();
        while (it.hasNext()) {
            it.next().setDirty(false);
        }
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
        contentValues.put("origin_parent_id", Long.valueOf(this.mOriginParentId));
        this.mSyncContext.getContext().getContentResolver().update(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), contentValues, null, null);
    }

    @Override // com.miui.notes.cloudservice.BaseEntity
    public void persist() throws Throwable {
        Logger.INSTANCE.i(TAG, "persist() mEndpointType=" + this.mEndpointType + " mId=" + this.mId + " content=" + this.mSnippet.length() + " mSyncTag:" + this.mSyncTag + " mSyncId:" + this.mSyncId);
        int i = this.mEndpointType;
        if (i == 1) {
            update(false, true);
            return;
        }
        if (i == 2) {
            long cachedNote = this.mSyncContext.getNoteCache().getCachedNote(this.mSyncId);
            this.mId = cachedNote;
            if (cachedNote > 0) {
                update(true, false);
                return;
            }
            long cachedNote2 = this.mSyncContext.getNoteCache().getCachedNote(getEffectiveContent(), this.mCreatedDate);
            this.mId = cachedNote2;
            if (cachedNote2 > 0) {
                if (isDeleted()) {
                    return;
                }
                if (!this.mSyncContext.getNoteCache().exists(this.mId)) {
                    Integer cachedVersion = this.mSyncContext.getNoteCache().getCachedVersion(this.mId);
                    if (cachedVersion != null) {
                        this.mVersion = cachedVersion.intValue();
                        Logger.INSTANCE.i(TAG, "Try to overwrite unsynced note with same digest: mSyncId=" + this.mSyncId);
                        update(false, true);
                        return;
                    } else {
                        Logger.INSTANCE.i(TAG, "Get cachedVersion failed,overwrite unsynced note with same digest: mSyncId=" + this.mSyncId);
                        update(false, false);
                        return;
                    }
                }
                Logger.INSTANCE.i(TAG, "Try to insert new note with same digest: mSyncId=" + this.mSyncId);
                this.mId = 0L;
                insert();
                return;
            }
            if (isDeleted()) {
                return;
            }
            this.mId = 0L;
            insert();
            return;
        }
        throw new NoteSyncException("Can't persist temporary note", false);
    }

    private void insert() throws Throwable {
        ArrayList arrayList;
        ContentValues contentValues = getContentValues();
        ArrayList<DataEntity> arrayList2 = this.mDatas;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.mDatas.size());
            Iterator<DataEntity> it = this.mDatas.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getContentValues());
            }
        }
        Logger.INSTANCE.i(TAG, "insert note, sync_id=" + this.mSyncId + " mId=" + this.mId + " mSnippet=" + this.mSnippet.length());
        Uri uriInsertNoteAtomic = Notes.Utils.insertNoteAtomic(this.mSyncContext.getContext(), contentValues, arrayList, true);
        long j = uriInsertNoteAtomic == null ? 0L : Long.parseLong(uriInsertNoteAtomic.getLastPathSegment());
        this.mId = j;
        if (j > 0 || j == -1) {
            if (j == -1 || this.mSyncId <= 0) {
                return;
            }
            this.mSyncContext.getNoteCache().add(this.mId, this.mSyncId);
            return;
        }
        String str = this.mMindContentPlainText;
        int length = str != null ? str.length() : 0;
        String mindContent = getMindContent();
        Logger.INSTANCE.e(TAG, "Fail to insert note, sync_id=" + this.mSyncId + ", uri=" + uriInsertNoteAtomic + " mId=" + this.mId + " mindContentSize:" + (mindContent != null ? mindContent.length() : 0) + " plainTextSize:" + length);
    }

    private void update(boolean z, boolean z2) throws Throwable {
        ArrayList arrayList;
        Context context = this.mSyncContext.getContext();
        Object[] selectionParams = getSelectionParams(z, z2);
        String str = (String) selectionParams[0];
        String[] strArr = (String[]) selectionParams[1];
        ContentValues contentValues = getContentValues();
        ArrayList<DataEntity> arrayList2 = this.mDatas;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.mDatas.size());
            Iterator<DataEntity> it = this.mDatas.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getContentValues());
            }
        }
        int iUpdateNoteAtomic = Notes.Utils.updateNoteAtomic(context, this.mId, contentValues, arrayList, str, strArr, true);
        Logger logger = Logger.INSTANCE;
        StringBuilder sbAppend = new StringBuilder("update() count=").append(iUpdateNoteAtomic).append(" mId=").append(this.mId).append(" parentId=").append(this.mParentId).append(" origin=").append(this.mOriginParentId).append(" mSyncId=").append(this.mSyncId).append(" checkModify=").append(z).append(" checkVersion=").append(z2).append(" selection=").append(str).append(" selectionArgs=").append(selectionParams[1]).append("mLocalModified=").append(this.mLocalModified).append(" mSnippet=");
        String str2 = this.mSnippet;
        logger.i(TAG, sbAppend.append(str2 != null ? Integer.valueOf(str2.length()) : "null").toString());
        if (iUpdateNoteAtomic <= 0) {
            if (this.mEndpointType == 2) {
                boolean zResolveRetriableConflict = resolveRetriableConflict();
                Logger.INSTANCE.i(TAG, "update() resolved=" + zResolveRetriableConflict);
                if (zResolveRetriableConflict) {
                    return;
                }
                Logger.INSTANCE.i(TAG, "update() mSyncContext.isResync()=" + this.mSyncContext.isResync());
                if (this.mSyncContext.isResync()) {
                    if (TextUtils.equals(this.mSyncTag, getSyncTagById(this.mSyncContext.getContext(), this.mId))) {
                        Logger.INSTANCE.e(TAG, "Ignore update error while resync with same syncTag, mId=" + this.mId);
                        return;
                    }
                    Logger.INSTANCE.i(TAG, "update() clearSyncInfo");
                    clearSyncInfo(this.mSyncContext, this.mId, this.mSyncId);
                    insert();
                    return;
                }
            }
            String str3 = this.mMindContentPlainText;
            int length = str3 != null ? str3.length() * 2 : 0;
            String mindContent = getMindContent();
            int length2 = mindContent != null ? mindContent.length() * 2 : 0;
            if (length + length2 > 1000000) {
                Logger.INSTANCE.i(TAG, "update fail by big mind size:" + length2 + " plainTextSize:" + length);
                exceedsLimitSync();
                return;
            }
            throw new NoteSyncException("Fail to update note, _id=" + this.mId);
        }
        updateCacheAfterUpdate();
    }

    private void exceedsLimitSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", Long.valueOf(this.mSyncId));
        contentValues.put("sync_tag", this.mSyncTag);
        contentValues.put(Notes.Note.SYNC_DATA1, Long.valueOf(this.mRetriableDate));
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        contentValues.put("origin_parent_id", Long.valueOf(this.mOriginParentId));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, Integer.valueOf(this.mLocalModified));
        this.mSyncContext.getContext().getContentResolver().update(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), contentValues, null, null);
    }

    private boolean resolveRetriableConflict() throws NoteSyncException {
        Cursor cursorQuery = this.mSyncContext.getContext().getContentResolver().query(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), NOTE_PROJECTIONS, NOTE_SELECTION_DIRTY_AND_UNRETRIABLE, null, null);
        boolean z = false;
        if (cursorQuery == null) {
            return false;
        }
        try {
            if (cursorQuery.getCount() > 0) {
                clearSyncInfo(this.mSyncContext, this.mId, this.mSyncId);
                insert();
                updateCacheAfterUpdate();
                z = true;
            }
            return z;
        } finally {
            cursorQuery.close();
        }
    }

    private void updateCacheAfterUpdate() throws NoteSyncException {
        if (this.mSyncId > 0) {
            this.mSyncContext.getNoteCache().add(this.mId, this.mSyncId);
        } else {
            this.mSyncContext.getNoteCache().remove(this.mId, this.mSyncId);
        }
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", Long.valueOf(this.mSyncId));
        contentValues.put("sync_tag", this.mSyncTag);
        contentValues.put("parent_id", Long.valueOf(this.mParentId));
        contentValues.put(Notes.Note.ALERTED_DATE, Long.valueOf(this.mAlertDate));
        contentValues.put(Notes.Note.BG_COLOR_ID, Integer.valueOf(this.mBgColorId));
        contentValues.put("theme_id", Integer.valueOf(this.mThemeId));
        contentValues.put("created_date", Long.valueOf(this.mCreatedDate));
        contentValues.put("modified_date", Long.valueOf(this.mModifiedDate));
        contentValues.put("origin_parent_id", Long.valueOf(this.mOriginParentId));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, Integer.valueOf(this.mLocalModified));
        contentValues.put("snippet", this.mSnippet);
        contentValues.put(Notes.Note.STICK_DATE, Long.valueOf(this.mStickDate));
        contentValues.put(Notes.Note.SYNC_DATA1, Long.valueOf(this.mRetriableDate));
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        contentValues.put("type", (Integer) 0);
        contentValues.put("account_id", Long.valueOf(AccountEntity.getAccountId()));
        contentValues.put(Notes.Note.ALERT_TAG, Integer.valueOf(this.mAlertTag));
        contentValues.put("title", this.mTitle);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, this.mNoteType);
        contentValues.put(Notes.Note.MIND_CONTENT, getMindContent());
        contentValues.put(Notes.Note.MIND_CONTENT_PLAIN_TEXT, this.mMindContentPlainText);
        contentValues.put("ent_name", this.mEntName);
        contentValues.put(Notes.Note.IMAGES, this.mImages);
        contentValues.put("thumbnail", this.mThumbnail);
        contentValues.put("recognized_file_names", this.mRecognizedText);
        contentValues.put(Notes.Note.IS_EMPTY, Integer.valueOf(!this.isEmpty ? 1 : 0));
        contentValues.put(Notes.Note.WEB_IMAGES, this.mWebImages);
        return contentValues;
    }

    private String getMindContent() {
        HandWriteEntity handWriteEntity;
        if (TextUtils.equals(this.mNoteType, "handwrite") && (handWriteEntity = HandWriteEntity.toHandWriteEntity(this.mMindContent)) != null) {
            handWriteEntity.recognizedText = "";
            this.mMindContent = handWriteEntity.toNoteContent();
        }
        return this.mMindContent;
    }

    private void delete(boolean z, boolean z2) throws NoteSyncException {
        deleteWithoutCacheUpdate(z, z2);
        this.mSyncContext.getNoteCache().remove(this.mId, this.mSyncId);
    }

    void deleteWithoutCacheUpdate(boolean z, boolean z2) throws NoteSyncException {
        Object[] selectionParams = getSelectionParams(z, z2);
        String str = (String) selectionParams[0];
        String[] strArr = (String[]) selectionParams[1];
        Logger.INSTANCE.i(TAG, "deleteWithoutCacheUpdate: mId=" + this.mId + " mCreatedDate=" + this.mCreatedDate + " content=" + this.mSnippet.length());
        if (this.mSyncContext.getContext().getContentResolver().delete(ContentUris.withAppendedId(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), str, strArr) <= 0) {
            throw new NoteSyncException("Fail to delete note, id=" + this.mId);
        }
    }

    private Object[] getSelectionParams(boolean z, boolean z2) throws NoteSyncException {
        String str;
        String str2 = z ? "local_modified=0" : null;
        if (z2) {
            if (str2 == null) {
                str = "";
            } else {
                str = str2 + " AND ";
            }
            str2 = str + "version=" + this.mVersion;
        }
        return new Object[]{str2, null};
    }

    private void verify() throws NoteSyncException {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        DataEntity dataEntity = null;
        for (DataEntity dataEntity2 : this.mDatas) {
            if (dataEntity2.isTextData()) {
                dataEntity = dataEntity2;
            } else if (dataEntity2.isFileData()) {
                String content = dataEntity2.getContent();
                Integer num = (Integer) map2.get(content);
                if (num == null) {
                    map2.put(content, 1);
                } else {
                    map2.put(content, Integer.valueOf(num.intValue() + 1));
                }
                map.put(content, dataEntity2);
            }
        }
        if (!isMultiNode()) {
            if (dataEntity == null) {
                throw new NoteSyncException("No text data");
            }
            for (String str : NotesUtils.retrieveMedias(dataEntity.getContent())) {
                Integer num2 = (Integer) map2.get(str);
                if (num2 == null) {
                    Logger.INSTANCE.e(TAG, "Find an isolated digest in content ");
                } else if (num2.intValue() <= 0) {
                    DataEntity dataEntity3 = (DataEntity) map.get(str);
                    this.mDatas.add(DataEntity.newFileData(dataEntity3.getMimeType(), dataEntity3.getFileId(), dataEntity3.getContent()));
                } else {
                    map2.put(str, Integer.valueOf(num2.intValue() - 1));
                }
            }
        }
        for (Map.Entry entry : map2.entrySet()) {
            if (((Integer) entry.getValue()).intValue() > 0) {
                Logger.INSTANCE.e(TAG, "Find an unreferenced digest in data: " + ((String) entry.getKey()) + ", sync_id=" + this.mSyncId);
            }
        }
    }

    String getDigest() throws NoteSyncException {
        return getDigest(getEffectiveContent(), this.mCreatedDate);
    }

    String getEffectiveContent() {
        if (TextUtils.equals(this.mNoteType, NoteModel.NoteType.TYPE_MIND)) {
            return this.mMindContentPlainText;
        }
        return this.mSnippet;
    }

    public String toString() {
        Long lValueOf = Long.valueOf(this.mId);
        Long lValueOf2 = Long.valueOf(this.mParentId);
        Long lValueOf3 = Long.valueOf(this.mOriginParentId);
        Long lValueOf4 = Long.valueOf(this.mSyncId);
        String str = this.mSyncTag;
        Long lValueOf5 = Long.valueOf(this.mCreatedDate);
        Long lValueOf6 = Long.valueOf(this.mModifiedDate);
        Integer numValueOf = Integer.valueOf(this.mVersion);
        Integer numValueOf2 = Integer.valueOf(this.mSnippet.length());
        String str2 = this.mTitle;
        return String.format("[%s]: id=%d, parent_id=%d, origin_parent_id=%d, sync_id=%d, sync_tag=%s, createdDate=%d, modifiedDate=%d, version=%d, size=%d, titleSize=%d, type=%s", TAG, lValueOf, lValueOf2, lValueOf3, lValueOf4, str, lValueOf5, lValueOf6, numValueOf, numValueOf2, Integer.valueOf(str2 != null ? str2.length() : 0), this.mNoteType);
    }

    private boolean isMultiNode() {
        return "multi_handwrite".equals(this.mNoteType);
    }
}
