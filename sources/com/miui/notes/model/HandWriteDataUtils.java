package com.miui.notes.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class HandWriteDataUtils {
    public static final String DOWNLOAD_HANDWRITE_ACTION = "com.miui.notes.handwrite.download";
    public static final String DOWNLOAD_IMG_HANDWRITE_ACTION = "com.miui.notes.handwrite.downloadImg";
    public static final String ENT_FILE_TYPE = "ent/file";
    public static final String ENT_SUFFIX = ".ent";
    public static final String IINK_FILE_TYPE = "iink/file";
    public static final String IINK_SUFFIX = ".iink";
    private static final String[] NOTE_PROJECTION = {"_id", "parent_id", "created_date", "modified_date", "snippet", Notes.Note.LOCAL_MODIFIED, "type", Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT, "title", "sync_id"};

    public static long createNewNote(long j, HandWriteEntity handWriteEntity, long j2) {
        return Long.parseLong(Notes.Utils.insertNoteAtomic(NoteApp.getInstance(), getContentValues(handWriteEntity.title, handWriteEntity.toNoteContent(), handWriteEntity.textContent, j, j2, j2), buildDataValuesList("", null, null, null, null)).getPathSegments().get(2));
    }

    public static boolean deleteNote(long j) {
        return NoteStore.delete(NoteApp.getInstance(), new long[]{j}) > 0;
    }

    public static void deleteData(Context context, long j, String str) {
        context.getContentResolver().delete(ContentUris.withAppendedId(Notes.Data.CONTENT_URI, j), "content=?", new String[]{str});
    }

    public static HandWriteNoteInfo query(Context context, String str) {
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), NOTE_PROJECTION, "type=0 AND " + str, null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return HandWriteNoteInfo.valueOf(cursorQuery);
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    private static ContentValues getContentValues(String str, String str2, String str3, long j, long j2, long j3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", Long.valueOf(j));
        if (j2 != 0) {
            contentValues.put("created_date", Long.valueOf(j2));
        }
        contentValues.put("modified_date", Long.valueOf(j3));
        contentValues.put(Notes.Note.MIND_CONTENT, str2);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("type", (Integer) 0);
        contentValues.put("title", str);
        contentValues.put("snippet", str3);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, "handwrite");
        return contentValues;
    }

    private static ArrayList<ContentValues> buildDataValuesList(String str, String str2, List<String> list, String str3, String str4) {
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        contentValues.put("content", str);
        arrayList.add(contentValues);
        if (!TextUtils.isEmpty(str3)) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Notes.Data.MIME_TYPE, "ent/file");
            contentValues2.put("content", str3);
            arrayList.add(contentValues2);
        }
        if (!TextUtils.isEmpty(str4)) {
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put(Notes.Data.MIME_TYPE, "ent/file");
            contentValues3.put("content", str4);
            arrayList.add(contentValues3);
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i))) {
                    ContentValues contentValues4 = new ContentValues();
                    contentValues4.put("content", list.get(i));
                    contentValues4.put(Notes.Data.MIME_TYPE, "image/jpeg");
                    arrayList.add(contentValues4);
                }
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            ContentValues contentValues5 = new ContentValues();
            contentValues5.put("content", str2);
            contentValues5.put(Notes.Data.MIME_TYPE, "image/jpeg");
            arrayList.add(contentValues5);
        }
        Iterator<ContentValues> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().put(Notes.Data.DIRTY, (Integer) 1);
        }
        return arrayList;
    }

    public static class HandWriteNoteInfo {
        private String mContentType;
        private long mCreatedDate;
        private String mHandWriteContent;
        private int mLocalModified;
        private long mModifiedDate;
        private long mParentId;
        private String mSnippet;
        private String mTitle;
        private int mType;
        private long mId = -2;
        private long mSyncId = -1;

        public static HandWriteNoteInfo valueOf(Cursor cursor) {
            HandWriteNoteInfo handWriteNoteInfo = new HandWriteNoteInfo();
            handWriteNoteInfo.mId = cursor.getLong(0);
            handWriteNoteInfo.mParentId = cursor.getLong(1);
            handWriteNoteInfo.mCreatedDate = cursor.getLong(2);
            handWriteNoteInfo.mModifiedDate = cursor.getLong(3);
            handWriteNoteInfo.mSnippet = cursor.getString(4);
            handWriteNoteInfo.mLocalModified = cursor.getInt(5);
            handWriteNoteInfo.mType = cursor.getInt(6);
            handWriteNoteInfo.mContentType = cursor.getString(7);
            handWriteNoteInfo.mHandWriteContent = cursor.getString(8);
            handWriteNoteInfo.mTitle = cursor.getString(9);
            handWriteNoteInfo.mSyncId = cursor.getLong(10);
            return handWriteNoteInfo;
        }

        public long getId() {
            return this.mId;
        }

        public long getParentId() {
            return this.mParentId;
        }

        public long getSyncId() {
            return this.mSyncId;
        }

        public long getCreatedDate() {
            return this.mCreatedDate;
        }

        public long getModifiedDate() {
            return this.mModifiedDate;
        }

        public String getSnippet() {
            return this.mSnippet;
        }

        public String getHandWriteContent() {
            return this.mHandWriteContent;
        }

        public int getLocalModified() {
            return this.mLocalModified;
        }

        public int getType() {
            return this.mType;
        }

        public String getContentType() {
            return this.mContentType;
        }
    }

    public static List<String> getImages(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i))) {
                arrayList.add(new File(list.get(i)).getName());
            }
        }
        return arrayList;
    }

    public static String getEntFileName() {
        return System.currentTimeMillis() + ".ent";
    }

    public static String reNameEnt(Context context, String str) {
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, str);
        try {
            File file = new File(attachmentPath);
            String attachmentPath2 = AttachmentUtils.getAttachmentPath(context, AttachmentUtils.getFileSha1Digest(attachmentPath) + ".ent");
            if (!TextUtils.equals(attachmentPath2, attachmentPath)) {
                File file2 = new File(attachmentPath2);
                if (file.renameTo(file2)) {
                    file2.setReadable(true, false);
                    String name = file2.getName();
                    if (!TextUtils.isEmpty(name)) {
                        return name;
                    }
                }
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static void deleteFile(Context context, String str) {
        try {
            new File(AttachmentUtils.getAttachmentPath(context, str)).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIinkFileName(String str) {
        if (!TextUtils.isEmpty(str) && str.endsWith(".ent")) {
            return str.substring(0, str.length() - ".ent".length()) + IINK_SUFFIX;
        }
        return null;
    }

    public static long getSortDate(long j, long j2) {
        return PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0 ? j : j2;
    }
}
