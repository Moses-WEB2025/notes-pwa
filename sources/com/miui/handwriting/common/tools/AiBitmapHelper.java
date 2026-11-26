package com.miui.handwriting.common.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.provider.Notes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiBitmapHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0005J \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005¨\u0006\u000e"}, d2 = {"Lcom/miui/handwriting/common/tools/AiBitmapHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "saveBitmapToFileAndGetPath", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "saveImageToGallery", "Landroid/net/Uri;", "displayName", "imagePath", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiBitmapHelper {
    public static final AiBitmapHelper INSTANCE = new AiBitmapHelper();

    private AiBitmapHelper() {
    }

    public final String saveBitmapToFileAndGetPath(Context context, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_" + System.currentTimeMillis() + com.miui.common.tool.Utils.JPG_SUFFIX);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Uri saveImageToGallery(Context context, Bitmap bitmap, String displayName) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        ContentResolver contentResolver = context.getContentResolver();
        Uri contentUri = MediaStore.Images.Media.getContentUri("external_primary");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", displayName + com.miui.common.tool.Utils.JPG_SUFFIX);
        contentValues.put(Notes.Data.MIME_TYPE, "image/jpeg");
        contentValues.put("relative_path", Environment.DIRECTORY_PICTURES);
        contentValues.put(NoteIntent.KEY_IS_PENDING, (Integer) 1);
        Uri uriInsert = contentResolver.insert(contentUri, contentValues);
        if (uriInsert != null) {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
            if (outputStreamOpenOutputStream != null) {
                OutputStream outputStream = outputStreamOpenOutputStream;
                try {
                    Boolean.valueOf(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream));
                    CloseableKt.closeFinally(outputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(outputStream, th);
                        throw th2;
                    }
                }
            }
            contentValues.clear();
            contentValues.put(NoteIntent.KEY_IS_PENDING, (Integer) 0);
            contentResolver.update(uriInsert, contentValues, null, null);
        }
        return uriInsert;
    }

    public final Uri saveImageToGallery(Context context, String imagePath, String displayName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(imagePath);
        if (bitmapDecodeFile != null) {
            return saveImageToGallery(context, bitmapDecodeFile, displayName);
        }
        return null;
    }
}
