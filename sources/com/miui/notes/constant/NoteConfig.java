package com.miui.notes.constant;

import com.miui.notes.NoteApp;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class NoteConfig {
    public static final int ARRAY_BY_COLUMNS_MODE = 0;
    public static final int ARRAY_BY_FLAT_MODE = 1;
    public static final int ARRAY_BY_GRID_MODE = 1;
    public static final int ARRAY_BY_LIST_MODE = 0;
    public static final int DEFAULT_NOTE_ARRAY_WAY = 0;
    public static final int DEFAULT_NOTE_SORT_WAY = 1;
    public static final int DEFAULT_PHONE_NOTE_ARRAY_WAY = 0;
    public static final String NOTES_PACKAGE_NAME = "com.miui.notes";
    public static final int SORT_BY_CREATION_TIME = 0;
    public static final int SORT_BY_MODIFICATION_TIME = 1;
    public static final int LIST_THUMBNAIL_SIZE = NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.v8_list_item_thumbnail_size);
    public static final String[] NOTE_SORT_ENTRY_VALUE = {String.valueOf(0), String.valueOf(1)};
    public static final String[] NOTE_ARRAY_ENTRY_VALUE = {String.valueOf(0), String.valueOf(1)};
    public static final String[] PHONE_NOTE_ARRAY_ENTRY_VALUE = {String.valueOf(0), String.valueOf(1)};

    public static long getDefaultFolderId() {
        return -4097L;
    }
}
