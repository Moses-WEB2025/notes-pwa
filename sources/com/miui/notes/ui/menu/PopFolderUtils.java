package com.miui.notes.ui.menu;

import android.database.Cursor;
import com.miui.notes.NoteApp;
import com.miui.notes.provider.Notes;

/* JADX INFO: loaded from: classes4.dex */
public class PopFolderUtils {
    public static final int NEW_FOLDER_ID = -10000;
    public static final String[] PROJECTION = {"_id", "subject", Notes.Note.NOTES_COUNT};
    public static final String QUERY_SORT_ORDER = "subject ASC";
    private static final String SELECTION_FOLDER_USER_AND_SYSTEM = "parent_id=0 AND (type=1 OR _id=-3 OR (_id=-2 AND notes_count<>0) OR (_id=-5 AND notes_count<>0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0))";

    public static Cursor queryFolder() {
        return NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PROJECTION, SELECTION_FOLDER_USER_AND_SYSTEM, null, QUERY_SORT_ORDER);
    }
}
