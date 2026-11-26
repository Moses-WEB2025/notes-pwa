package com.miui.notes.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.mishow.MiShowPreferenceHelper;
import com.miui.notes.mishow.MiShowReceiver;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.PreferenceUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteProviderAccessUtils {
    static final String[] PROJECTION = {"_id", "parent_id", Notes.Note.BG_COLOR_ID, Notes.Note.HAS_ATTACHMENT, "snippet", "type", "widget_id", "widget_type", "created_date", "modified_date", Notes.Note.ALERTED_DATE, Notes.Note.STICK_DATE, "theme_id", "origin_parent_id", "subject", Notes.Note.NOTES_COUNT, "plain_text", "title", Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT, Notes.Note.MIND_CONTENT_PLAIN_TEXT, "thumbnail"};
    private static final String QUERY_FLOAT_NOTES_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id <> -5 AND parent_id <> -2";
    private static final String QUERY_NOTE_BY_ID_SELECTION = "_id=";
    private static final String QUERY_NOTE_BY_SYNC_ID_SELECTION = "sync_id=";
    public static final String SORT_BY_CREATED_DATE = "stick_date DESC, created_date DESC,modified_date DESC";
    public static final String SORT_BY_MODIFIED_DATE = "stick_date DESC, modified_date DESC";
    public static final String SORT_LIMIT = " limit ";

    public static Cursor queryAllItems(Context context) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, QUERY_FLOAT_NOTES_SELECTION, null, "stick_date DESC, modified_date DESC");
    }

    public static Cursor queryFloatNoteItems(Context context, int i) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, QUERY_FLOAT_NOTES_SELECTION, null, (PreferenceUtils.getNoteSortWay(context, 1) == 0 ? "stick_date DESC, created_date DESC,modified_date DESC" : "stick_date DESC, modified_date DESC") + SORT_LIMIT + i);
    }

    public static Cursor queryNote(Context context, long j) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, QUERY_NOTE_BY_ID_SELECTION + j, null, null);
    }

    public static Cursor queryPrivateNote(Context context, long j) {
        return context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), PROJECTION, QUERY_NOTE_BY_ID_SELECTION + j, null, null);
    }

    public static Cursor queryNoteBySyncId(Context context, long j) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, QUERY_NOTE_BY_SYNC_ID_SELECTION + j, null, null);
    }

    public static Cursor queryPrivateNoteBySyncId(Context context, long j) {
        return context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), PROJECTION, QUERY_NOTE_BY_SYNC_ID_SELECTION + j, null, null);
    }

    public static long getExtraNoteIdFromIntent(Intent intent) {
        long j = 0;
        long longExtra = intent.getLongExtra("android.intent.extra.UID", 0L);
        if (longExtra <= 0) {
            if (intent.getStringExtra("mishow_name") != null) {
                long noteId = MiShowPreferenceHelper.getNoteId(intent.getStringExtra("mishow_name"));
                if (WorkingNote.loadNote(QUERY_NOTE_BY_ID_SELECTION + noteId, null).getFolderId() == -3) {
                    Logger.INSTANCE.i(MiShowReceiver.TAG, "noteId is in trash folder");
                } else {
                    j = noteId;
                }
                Logger.INSTANCE.i(MiShowReceiver.TAG, "intent get noteId: " + j);
                return j;
            }
            Uri data = intent.getData();
            if (data == null || !"notes".equals(data.getAuthority())) {
                Logger.INSTANCE.e("TAG", "Invalid data uri: " + (data != null ? data.toString() : null));
            } else {
                try {
                    return ContentUris.parseId(data);
                } catch (Exception e) {
                    Logger.INSTANCE.e("TAG", "Fail to parse note id from uri: " + data.toString() + ",exception:" + e);
                }
            }
        }
        return longExtra;
    }
}
