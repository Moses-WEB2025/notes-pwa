package com.miui.notes.excerpt;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MediaUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007J\u0012\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/excerpt/MediaUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MEDIA_PREFIX_URL", "", "MEDIA_QUERY_URL", "Landroid/net/Uri;", "getMEDIA_QUERY_URL", "()Landroid/net/Uri;", "getNewPhotoImg", "Lcom/miui/notes/excerpt/PicInfo;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "isMediaUri", "", CallMethod.ARG_URI, "isSkipImg", "filePath", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MediaUtils {
    public static final MediaUtils INSTANCE = new MediaUtils();
    public static final String MEDIA_PREFIX_URL = "content://media/";
    private static final Uri MEDIA_QUERY_URL;

    private MediaUtils() {
    }

    static {
        Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
        MEDIA_QUERY_URL = EXTERNAL_CONTENT_URI;
    }

    public final Uri getMEDIA_QUERY_URL() {
        return MEDIA_QUERY_URL;
    }

    public final PicInfo getNewPhotoImg(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Cursor cursorQuery = context.getContentResolver().query(MEDIA_QUERY_URL, new String[]{"_id", "_data"}, null, null, "date_modified  desc");
        PicInfo picInfo = null;
        if (cursorQuery != null) {
            if (cursorQuery.getCount() != 0) {
                cursorQuery.moveToPosition(0);
                int columnIndex = cursorQuery.getColumnIndex("_data");
                int columnIndex2 = cursorQuery.getColumnIndex("_id");
                if (columnIndex == columnIndex2 || columnIndex == -1) {
                    Logger.INSTANCE.e("MediaUtils", "MediaUtils getNewPhotoImg with index -1,file not exist");
                } else {
                    PicInfo picInfo2 = new PicInfo(cursorQuery.getString(columnIndex), cursorQuery.getString(columnIndex2));
                    Log.d("MediaUtils", "getNewPhotoImg picInfo: " + picInfo2.getFilePath());
                    picInfo = picInfo2;
                }
            }
            cursorQuery.close();
        }
        return picInfo;
    }

    public final boolean isMediaUri(Uri uri) {
        return StringsKt.contains$default((CharSequence) new StringBuilder().append(uri).toString(), (CharSequence) MEDIA_PREFIX_URL, false, 2, (Object) null);
    }

    @JvmStatic
    public static final boolean isSkipImg(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return Intrinsics.areEqual(options.outMimeType, "image/gif") || (options.outWidth < 200 && options.outHeight < 200);
    }
}
