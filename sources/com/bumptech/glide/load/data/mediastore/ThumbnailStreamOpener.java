package com.bumptech.glide.load.data.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ThumbnailStreamOpener {
    private static final FileService DEFAULT_SERVICE = new FileService();
    private static final String TAG = "ThumbStreamOpener";
    private final ArrayPool byteArrayPool;
    private final ContentResolver contentResolver;
    private final List<ImageHeaderParser> parsers;
    private final ThumbnailQuery query;
    private final FileService service;

    ThumbnailStreamOpener(List<ImageHeaderParser> list, ThumbnailQuery thumbnailQuery, ArrayPool arrayPool, ContentResolver contentResolver) {
        this(list, DEFAULT_SERVICE, thumbnailQuery, arrayPool, contentResolver);
    }

    ThumbnailStreamOpener(List<ImageHeaderParser> list, FileService fileService, ThumbnailQuery thumbnailQuery, ArrayPool arrayPool, ContentResolver contentResolver) {
        this.service = fileService;
        this.query = thumbnailQuery;
        this.byteArrayPool = arrayPool;
        this.contentResolver = contentResolver;
        this.parsers = list;
    }

    int getOrientation(Uri uri) {
        InputStream inputStreamOpenInputStream = null;
        try {
            inputStreamOpenInputStream = this.contentResolver.openInputStream(uri);
            return ImageHeaderParserUtils.getOrientation(this.parsers, inputStreamOpenInputStream, this.byteArrayPool);
        } catch (IOException | NullPointerException e) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to open uri: " + uri, e);
            }
            if (inputStreamOpenInputStream == null) {
                return -1;
            }
            try {
                return -1;
            } catch (IOException unused) {
                return -1;
            }
        } finally {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException unused2) {
                }
            }
        }
    }

    public InputStream open(Uri uri) throws Throwable {
        String path = getPath(uri);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = this.service.get(path);
        if (!isValid(file)) {
            return null;
        }
        Uri uriFromFile = Uri.fromFile(file);
        try {
            return this.contentResolver.openInputStream(uriFromFile);
        } catch (NullPointerException e) {
            throw ((FileNotFoundException) new FileNotFoundException("NPE opening uri: " + uri + " -> " + uriFromFile).initCause(e));
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x004b  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r2v2 */
    private String getPath(Uri uri) throws Throwable {
        Cursor cursorQuery;
        ?? r2 = 0;
        try {
            try {
                cursorQuery = this.query.query(uri);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            String string = cursorQuery.getString(0);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return string;
                        }
                    } catch (SecurityException e) {
                        e = e;
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Failed to query for thumbnail for Uri: " + uri, e);
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (SecurityException e2) {
                e = e2;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (r2 != 0) {
                    r2.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r2 = this;
            if (r2 != 0) {
                r2.close();
            }
            throw th;
        }
    }

    private boolean isValid(File file) {
        return this.service.exists(file) && 0 < this.service.length(file);
    }
}
