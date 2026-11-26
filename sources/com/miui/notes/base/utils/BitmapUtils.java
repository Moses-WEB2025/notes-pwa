package com.miui.notes.base.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.ExifInterface;
import android.view.View;
import android.view.ViewConfiguration;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BitmapUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0006J\u001a\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u0014\u0010\r\u001a\u0004\u0018\u00010\u000b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/base/utils/BitmapUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "captureViewSnapshot", "Landroid/graphics/Bitmap;", "Landroid/view/View;", "writeExifMetaData", "", "Ljava/io/File;", "key", "", "value", "getMetaExifData", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BitmapUtils {
    public static final BitmapUtils INSTANCE = new BitmapUtils();

    private BitmapUtils() {
    }

    public final Bitmap captureViewSnapshot(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        int width = view.getWidth();
        int height = view.getHeight();
        long j = ((long) width) * ((long) height) * ((long) 4);
        long scaledMaximumDrawingCacheSize = ViewConfiguration.get(view.getContext()).getScaledMaximumDrawingCacheSize();
        Bitmap bitmap = null;
        if (width <= 0 || height <= 0 || j > scaledMaximumDrawingCacheSize) {
            if (width > 0 && height > 0) {
                Logger.INSTANCE.d("BitmapUtils", "too large to create a bitmap!");
            }
            return null;
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), width, height, Bitmap.Config.ARGB_8888);
            try {
                bitmapCreateBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                view.computeScroll();
                int iSave = canvas.save();
                canvas.translate(-view.getScrollX(), -view.getScaleY());
                view.draw(canvas);
                canvas.restoreToCount(iSave);
                canvas.setBitmap(null);
                return bitmapCreateBitmap;
            } catch (OutOfMemoryError unused) {
                bitmap = bitmapCreateBitmap;
                Logger.INSTANCE.d("BitmapUtils", "too large to create a bitmap!");
                return bitmap;
            }
        } catch (OutOfMemoryError unused2) {
        }
    }

    public final boolean writeExifMetaData(File file, String key, String value) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            ExifInterface exifInterface = new ExifInterface(file.getPath());
            exifInterface.setAttribute(key, value);
            Logger.INSTANCE.d("BitmapUtils", "set " + key + " with value: " + value);
            exifInterface.saveAttributes();
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.e("BitmapUtils", "set meta data with exception:" + e.getMessage());
            return false;
        }
    }

    public final String getMetaExifData(File file, String key) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            return new ExifInterface(file.getPath()).getAttribute(key);
        } catch (Exception e) {
            Logger.INSTANCE.e("BitmapUtils", "set meta data with exception:" + e.getMessage());
            return null;
        }
    }
}
