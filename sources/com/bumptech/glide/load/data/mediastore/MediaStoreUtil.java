package com.bumptech.glide.load.data.mediastore;

import android.net.Uri;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes.dex */
public final class MediaStoreUtil {
    private static final int MINI_THUMB_HEIGHT = 384;
    private static final int MINI_THUMB_WIDTH = 512;

    public static boolean isThumbnailSize(int i, int i2) {
        return i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE && i <= 512 && i2 <= 384;
    }

    private MediaStoreUtil() {
    }

    public static boolean isMediaStoreUri(Uri uri) {
        return uri != null && "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    public static boolean isAndroidPickerUri(Uri uri) {
        return isMediaStoreUri(uri) && uri.getPathSegments().contains("picker");
    }

    private static boolean isVideoUri(Uri uri) {
        return uri.getPathSegments().contains(HtmlParser.VideoElement.TAG);
    }

    public static boolean isMediaStoreVideoUri(Uri uri) {
        return isMediaStoreUri(uri) && isVideoUri(uri);
    }

    public static boolean isMediaStoreImageUri(Uri uri) {
        return isMediaStoreUri(uri) && !isVideoUri(uri);
    }
}
