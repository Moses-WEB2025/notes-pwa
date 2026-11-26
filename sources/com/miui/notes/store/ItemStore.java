package com.miui.notes.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class ItemStore {
    static final int ALERTED_DATE_COLUMN = 10;
    static final int BG_COLOR_ID_COLUMN = 2;
    static final int CREATED_DATE_COLUMN = 8;
    static final int HAS_ATTACHMENT_COLUMN = 3;
    public static final int ID_COLUMN = 0;
    public static final int MAX_BATCH_SIZE = 500;
    public static final int MIND_CONTENT_COLUMN = 19;
    public static final int MIND_CONTENT_PLAIN_TEXT_COLUMN = 20;
    private static final String MIUI_XII_ALL_SELECTION = "( type=0 AND deletion_tag <> 1 AND parent_id = 0) OR (parent_id=0 AND (type=1 OR (_id=-2 AND notes_count<>0) OR (_id=-5 AND notes_count<>0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0)))";
    private static final String MIUI_XII_SPECFIED_ID_SELECTION = "_id IN (42) ";
    private static final String MIUI_XII_TRASH_SELECTION = "( type=0 AND deletion_tag <> 1 AND parent_id = 0) OR (parent_id=0 AND (type=1 OR (_id=-2 AND notes_count<>0) OR (_id=-5 AND notes_count<>0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0)))";
    public static final int MODIFIED_DATE_COLUMN = 9;
    public static final int NOTES_COUNT_COLUMN = 15;
    public static final int NOTE_CONTENT_TYPE_COLUMN = 18;
    static final int ORIGIN_PARENT_ID_COLUMN = 13;
    public static final int PARENT_ID_COLUMN = 1;
    static final int PLAIN_TEXT_COLUMN = 16;
    static final String[] PROJECTION = {"_id", "parent_id", Notes.Note.BG_COLOR_ID, Notes.Note.HAS_ATTACHMENT, "snippet", "type", "widget_id", "widget_type", "created_date", "modified_date", Notes.Note.ALERTED_DATE, Notes.Note.STICK_DATE, "theme_id", "origin_parent_id", "subject", Notes.Note.NOTES_COUNT, "plain_text", "title", Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT, Notes.Note.MIND_CONTENT_PLAIN_TEXT, "thumbnail"};
    private static final String QUERY_TRASH_ALL_SELECTION = "type=0 AND parent_id = -3";
    private static final String RANGE_SELECTION = "_id IN (%s) ";
    public static final int SNIPPET_COLUMN = 4;
    public static final int STICK_DATE_COLUMN = 11;
    public static final int SUBJECT_COLUMN = 14;
    private static final String TAG = "ItemStore";
    static final int THEME_ID_COLUMN = 12;
    public static final int THUMBNAIL_COLUMN = 21;
    static final int TITLE_COLUMN = 17;
    static final int TYPE_COLUMN = 5;
    public static final int WIDGET_ID_COLUMN = 6;
    public static final int WIDGET_TYPE_COLUMN = 7;

    public static int getType(Cursor cursor) {
        return cursor.getInt(5);
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(0);
    }

    public static long getStick(Cursor cursor) {
        return cursor.getLong(11);
    }

    public static long getFoldId(Cursor cursor) {
        return cursor.getLong(1);
    }

    public static FolderModel parseFolder(Cursor cursor) {
        FolderModel folderModel = new FolderModel();
        folderModel.setId(cursor.getLong(0));
        folderModel.setParentId(cursor.getLong(1));
        folderModel.setSubject(cursor.getString(14));
        folderModel.setCount(cursor.getInt(15));
        folderModel.setType(cursor.getInt(5));
        folderModel.setCreatedAt(cursor.getLong(8));
        folderModel.setUpdatedAt(cursor.getLong(9));
        folderModel.setStickAt(cursor.getLong(11));
        return folderModel;
    }

    public static NoteModel parseNote(Cursor cursor) {
        return new NoteModel(cursor.getLong(0), cursor.getLong(1), cursor.getInt(12), cursor.getInt(2), cursor.getString(4), cursor.getInt(6), cursor.getInt(7), cursor.getLong(8), cursor.getLong(9), cursor.getLong(10), cursor.getLong(11), cursor.getLong(13), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21));
    }

    public static int stick(Context context, long[] jArr, boolean z) {
        int i = 0;
        int iStick = 0;
        while (i < jArr.length) {
            int iMin = Math.min(i + 500, jArr.length);
            iStick += stick(context, jArr, z, i, iMin);
            i = iMin;
        }
        return iStick;
    }

    public static int stick(Context context, long[] jArr, boolean z, int i, int i2) {
        Logger.INSTANCE.d(TAG, "stick:" + z);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.STICK_DATE, Long.valueOf(z ? System.currentTimeMillis() : 0L));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        String str = String.format(Locale.US, RANGE_SELECTION, DataUtils.join(jArr, i, i2));
        int iUpdate = context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, str, null);
        Logger.INSTANCE.d(TAG, "update result: count = " + iUpdate + ", selection = " + str);
        return iUpdate;
    }

    public static int queryAllNotesNum() {
        Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, "type=0 AND deletion_tag <> 1", null, null);
        if (cursorQuery == null) {
            return 0;
        }
        int count = cursorQuery.getCount();
        cursorQuery.close();
        return count;
    }

    public static Loader<Cursor> query(LoaderManager loaderManager, int i, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks, boolean z) {
        String str;
        Uri uri = Notes.Note.CONTENT_URI;
        if (RomUtils.isPadMode()) {
            str = "type=0 AND deletion_tag <> 1";
        } else {
            str = "( type=0 AND deletion_tag <> 1 AND parent_id = 0) OR (parent_id=0 AND (type=1 OR (_id=-2 AND notes_count<>0) OR (_id=-5 AND notes_count<>0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0)))";
        }
        return new CursorLoaderBuilder(loaderManager, i).setUri(uri).setProjection(PROJECTION).setSelection(str).setSelectionArgs(null).setSortOrder(PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0 ? "stick_date DESC, created_date DESC,modified_date DESC" : "stick_date DESC, modified_date DESC").setCallbacks(loaderCallbacks).build();
    }
}
