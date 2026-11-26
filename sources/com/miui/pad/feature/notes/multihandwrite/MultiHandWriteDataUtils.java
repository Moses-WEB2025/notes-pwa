package com.miui.pad.feature.notes.multihandwrite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class MultiHandWriteDataUtils {
    public static final String DOWNLOAD_HANDWRITE_ACTION = "com.miui.notes.handwrite.download";
    public static final String ENT_FILE_TYPE = "ent/file";
    public static final String ENT_SUFFIX = ".ent";
    private static final String[] NOTE_PROJECTION = {"_id", "parent_id", "ent_name", "snippet", "title", Notes.Note.IMAGES, "thumbnail", "recognized_file_names", "created_date", "modified_date", Notes.Note.IS_EMPTY, Notes.Note.WEB_IMAGES, "current_page_num", Notes.Note.CURRENT_PAGE_SCALE, Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT};
    private static final String TAG = "MultiHandWriteDataUtils";

    public static MultiHandWriteEntity valueOf(Cursor cursor) {
        MultiHandWriteEntity multiHandWriteEntity = new MultiHandWriteEntity();
        multiHandWriteEntity.id = cursor.getLong(0);
        multiHandWriteEntity.folderId = cursor.getLong(1);
        multiHandWriteEntity.entName = cursor.getString(2);
        multiHandWriteEntity.snippet = cursor.getString(3);
        multiHandWriteEntity.title = cursor.getString(4);
        multiHandWriteEntity.images = (List) new Gson().fromJson(cursor.getString(5), new TypeToken<List<String>>() { // from class: com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils.1
        }.getType());
        multiHandWriteEntity.thumbnail = cursor.getString(6);
        multiHandWriteEntity.recognizedFile = (List) new Gson().fromJson(cursor.getString(7), new TypeToken<List<String>>() { // from class: com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils.2
        }.getType());
        multiHandWriteEntity.createdTime = cursor.getLong(8);
        multiHandWriteEntity.modifiedTime = cursor.getLong(9);
        multiHandWriteEntity.isEmpty = cursor.getInt(10) == 0;
        multiHandWriteEntity.webImages = (List) new Gson().fromJson(cursor.getString(11), new TypeToken<List<WebImage>>() { // from class: com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils.3
        }.getType());
        multiHandWriteEntity.setCurrentPageNum(cursor.getInt(cursor.getColumnIndexOrThrow("current_page_num")));
        multiHandWriteEntity.setCurrentPageScale(cursor.getFloat(cursor.getColumnIndexOrThrow(Notes.Note.CURRENT_PAGE_SCALE)));
        String string = cursor.getString(14);
        String string2 = cursor.getString(15);
        multiHandWriteEntity.setNoteType(string);
        multiHandWriteEntity.setMindContent(string2);
        multiHandWriteEntity.setIsSuniaSignalPage("handwrite".equals(string));
        return multiHandWriteEntity;
    }

    public static MultiHandWriteEntity createNewNote(long j) {
        MultiHandWriteEntity multiHandWriteEntity = new MultiHandWriteEntity();
        multiHandWriteEntity.folderId = j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        multiHandWriteEntity.createdTime = jCurrentTimeMillis;
        multiHandWriteEntity.modifiedTime = jCurrentTimeMillis;
        multiHandWriteEntity.snippet = "";
        multiHandWriteEntity.isEmpty = true;
        multiHandWriteEntity.setMindContent("");
        multiHandWriteEntity.setNoteType("multi_handwrite");
        String str = MultiHandWritePathUtils.generateEngineAndPageEntDir() + File.separator + "multi_page_ent.ent";
        Log.d(TAG, "createNewNote:" + str);
        try {
            new File(str).createNewFile();
        } catch (IOException e) {
            Logger.INSTANCE.d(TAG, "create ent:" + e);
        }
        multiHandWriteEntity.entName = MultiHandWritePathUtils.getRelativePathByComplete(str);
        multiHandWriteEntity.id = Long.parseLong(Notes.Utils.insertNoteAtomic(NoteApp.getInstance(), getContentValues(multiHandWriteEntity), null).getPathSegments().get(2));
        return multiHandWriteEntity;
    }

    public static MultiHandWriteEntity createNewNote(long j, String str) {
        MultiHandWriteEntity multiHandWriteEntity = new MultiHandWriteEntity();
        multiHandWriteEntity.folderId = j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        multiHandWriteEntity.createdTime = jCurrentTimeMillis;
        multiHandWriteEntity.modifiedTime = jCurrentTimeMillis;
        multiHandWriteEntity.snippet = "";
        multiHandWriteEntity.isEmpty = true;
        multiHandWriteEntity.entName = str;
        multiHandWriteEntity.id = Long.parseLong(Notes.Utils.insertNoteAtomic(NoteApp.getInstance(), getContentValues(multiHandWriteEntity), null).getPathSegments().get(2));
        return multiHandWriteEntity;
    }

    public static MultiHandWriteEntity loadNote(long j) {
        Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), NOTE_PROJECTION, "type=0 AND _id=" + j, null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return valueOf(cursorQuery);
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    public static long insertNewNote(MultiHandWriteEntity multiHandWriteEntity) {
        return Long.parseLong(Notes.Utils.insertNoteAtomic(NoteApp.getInstance(), getContentValues(multiHandWriteEntity), null).getPathSegments().get(2));
    }

    public static String resetSnippet(String str, boolean z) {
        if (z) {
            return "";
        }
        return !TextUtils.isEmpty(str) ? str : NoteApp.getInstance().getString(R.string.pad_note_list_not_text_stub);
    }

    public static boolean updateHandWrite(long j, String str, long j2, List<String> list, List<String> list2, String str2, String str3, boolean z, String str4, String str5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", str);
        contentValues.put("snippet", str3);
        contentValues.put(Notes.Note.IS_EMPTY, Integer.valueOf(!z ? 1 : 0));
        contentValues.put("modified_date", Long.valueOf(j2));
        contentValues.put(Notes.Note.IMAGES, new Gson().toJson(list));
        contentValues.put(Notes.Note.DELETE_IMAGES, new Gson().toJson(list2));
        contentValues.put("ent_name", str2);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, str4);
        contentValues.put(Notes.Note.MIND_CONTENT, str5);
        return updateValues(contentValues, j);
    }

    public static boolean updateHandWrite(long j, String str, long j2, List<String> list, List<String> list2, String str2, String str3, String str4, boolean z, String str5, String str6) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", str);
        contentValues.put("snippet", str4);
        contentValues.put(Notes.Note.IS_EMPTY, Integer.valueOf(!z ? 1 : 0));
        contentValues.put("modified_date", Long.valueOf(j2));
        contentValues.put(Notes.Note.IMAGES, new Gson().toJson(list));
        contentValues.put(Notes.Note.DELETE_IMAGES, new Gson().toJson(list2));
        contentValues.put("ent_name", str2);
        contentValues.put("thumbnail", str3);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, str5);
        contentValues.put(Notes.Note.MIND_CONTENT, str6);
        return updateValues(contentValues, j);
    }

    public static boolean updateHandWrite(long j, String str, long j2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", str);
        contentValues.put("modified_date", Long.valueOf(j2));
        return updateValues(contentValues, j);
    }

    public static boolean updateValues(ContentValues contentValues, long j) {
        Logger.INSTANCE.d(TAG, "updateValues: SNIPPET");
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        return Notes.Utils.updateNoteMultiAtomic(NoteApp.getInstance(), j, contentValues, null, null, null) > 0;
    }

    public static boolean deleteNote(long j) {
        return NoteStore.delete(NoteApp.getInstance(), new long[]{j}) > 0;
    }

    public static ContentValues getContentValues(MultiHandWriteEntity multiHandWriteEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", Long.valueOf(multiHandWriteEntity.folderId));
        if (multiHandWriteEntity.createdTime != 0) {
            contentValues.put("created_date", Long.valueOf(multiHandWriteEntity.createdTime));
        }
        contentValues.put("modified_date", Long.valueOf(multiHandWriteEntity.modifiedTime));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("type", (Integer) 0);
        contentValues.put("title", multiHandWriteEntity.title);
        contentValues.put("snippet", multiHandWriteEntity.snippet);
        contentValues.put("ent_name", multiHandWriteEntity.entName);
        contentValues.put("recognized_file_names", new Gson().toJson(multiHandWriteEntity.recognizedFile));
        String json = new Gson().toJson(multiHandWriteEntity.images);
        contentValues.put("thumbnail", multiHandWriteEntity.thumbnail);
        contentValues.put(Notes.Note.IMAGES, json);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, multiHandWriteEntity.getNoteType());
        contentValues.put(Notes.Note.IS_EMPTY, Integer.valueOf(!multiHandWriteEntity.isEmpty ? 1 : 0));
        return contentValues;
    }

    private static ArrayList<ContentValues> buildDataValuesList(long j, ContentValues contentValues) {
        MultiHandWriteEntity multiHandWriteEntityLoadNote = loadNote(j);
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        if (contentValues.containsKey("thumbnail")) {
            String asString = contentValues.getAsString("thumbnail");
            if (!TextUtils.isEmpty(asString)) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("content", asString);
                contentValues2.put(Notes.Data.MIME_TYPE, "image/jpeg");
                arrayList.add(contentValues2);
            }
        }
        if (contentValues.containsKey("ent_name")) {
            String asString2 = contentValues.getAsString("ent_name");
            if (!TextUtils.isEmpty(asString2)) {
                ContentValues contentValues3 = new ContentValues();
                contentValues3.put(Notes.Data.MIME_TYPE, "ent/file");
                contentValues3.put("content", asString2);
                arrayList.add(contentValues3);
            }
        }
        if (contentValues.containsKey("recognized_file_names")) {
            List list = (List) new Gson().fromJson(contentValues.getAsString("recognized_file_names"), new TypeToken<List<String>>() { // from class: com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils.4
            }.getType());
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (!TextUtils.isEmpty((CharSequence) list.get(i))) {
                        ContentValues contentValues4 = new ContentValues();
                        contentValues4.put("content", (String) list.get(i));
                        contentValues4.put(Notes.Data.MIME_TYPE, "ent/file");
                        arrayList.add(contentValues4);
                    }
                }
            }
        }
        if (contentValues.containsKey(Notes.Note.IMAGES)) {
            List<String> list2 = multiHandWriteEntityLoadNote != null ? multiHandWriteEntityLoadNote.images : null;
            List list3 = (List) new Gson().fromJson(contentValues.getAsString(Notes.Note.IMAGES), new TypeToken<List<String>>() { // from class: com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils.5
            }.getType());
            if (list3 != null) {
                for (int i2 = 0; i2 < list3.size(); i2++) {
                    if (!TextUtils.isEmpty((CharSequence) list3.get(i2))) {
                        if (list2 == null || list2.isEmpty() || !list2.contains(list3.get(i2))) {
                            ContentValues contentValues5 = new ContentValues();
                            contentValues5.put("content", (String) list3.get(i2));
                            contentValues5.put(Notes.Data.MIME_TYPE, "image/jpeg");
                            arrayList.add(contentValues5);
                        } else {
                            list2.remove(list3.get(i2));
                        }
                    }
                }
            }
            if (list2 != null) {
                for (int i3 = 0; i3 < list2.size(); i3++) {
                    ContentValues contentValues6 = new ContentValues();
                    contentValues6.put("content", list2.get(i3));
                    contentValues6.put(Notes.Data.MIME_TYPE, "image/jpeg");
                    arrayList.add(contentValues6);
                }
            }
        }
        Iterator<ContentValues> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().put(Notes.Data.DIRTY, (Integer) 1);
        }
        return arrayList;
    }

    public static void deleteFile(Context context, String str) {
        try {
            new File(AttachmentUtils.getAttachmentPath(context, str)).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getSortDate(long j, long j2) {
        return PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0 ? j : j2;
    }

    public static MultiHandWriteEntity loadNoteFromPath(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            File file = new File(str);
            if (!file.exists()) {
                Log.w(TAG, "Ent file does not exist: " + str);
                return null;
            }
            MultiHandWriteEntity multiHandWriteEntity = new MultiHandWriteEntity();
            multiHandWriteEntity.setTitle(StringUtils.SPACE);
            multiHandWriteEntity.setModifiedTime(System.currentTimeMillis());
            multiHandWriteEntity.setCreatedTime(System.currentTimeMillis());
            String parent = file.getParent();
            if (parent != null) {
                multiHandWriteEntity.entName = parent + File.separator + "temp_note.ent";
            }
            Log.d(TAG, "Loaded note from path: " + str);
            return multiHandWriteEntity;
        } catch (Exception e) {
            Log.e(TAG, "Error loading note from path: " + str, e);
            return null;
        }
    }
}
