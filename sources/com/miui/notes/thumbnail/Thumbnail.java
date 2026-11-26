package com.miui.notes.thumbnail;

import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.ThumbnailCache;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;

/* JADX INFO: loaded from: classes4.dex */
public class Thumbnail {
    public static final String GRID = "grid";
    public static final String LIST = "list";
    private Context mContext;

    public Thumbnail(Context context) {
        this.mContext = context;
        context.getContentResolver().registerContentObserver(Notes.Note.CONTENT_URI, true, new ContentObserver(null) { // from class: com.miui.notes.thumbnail.Thumbnail.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                if (uri == null || !TextUtils.isDigitsOnly(uri.getLastPathSegment())) {
                    return;
                }
                ThumbnailCache thumbnail = CacheManager.getDefault().getThumbnail(ContentUris.parseId(uri));
                if (thumbnail != null) {
                    thumbnail.setExpired(true);
                }
            }
        });
    }

    private static class Holder {
        static Thumbnail INSTANCE = new Thumbnail(NoteApp.getInstance());

        private Holder() {
        }
    }

    public static Thumbnail instance() {
        return Holder.INSTANCE;
    }

    public ThumbnailLoader load(NoteModel noteModel) {
        return new ThumbnailLoader(this.mContext, noteModel);
    }
}
