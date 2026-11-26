package com.miui.notes.cloudservice;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.ParcelFileDescriptor;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.FileNotFoundException;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteFileProvider {
    private static final String TAG = "NoteFileProvider";

    public static ParcelFileDescriptor getFile(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = contentResolver.query(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"_id"}, "file_id=?", new String[]{str}, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                long j = cursorQuery.getLong(0);
                try {
                    ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(ContentUris.withAppendedId(Notes.Data.MEDIA_URI, j), Constants.RANDOM_LONG);
                    cursorQuery.close();
                    return parcelFileDescriptorOpenFileDescriptor;
                } catch (FileNotFoundException e) {
                    Logger.INSTANCE.w(TAG, "Can't open data " + j + StringUtils.SPACE + e);
                    cursorQuery.close();
                    return null;
                }
            }
            cursorQuery.close();
            return null;
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }
}
