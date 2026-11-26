package com.miui.richeditor.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Size;
import android.util.SizeF;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.LiteUtils;
import com.miui.notes.tool.BitmapDecoder;

/* JADX INFO: loaded from: classes4.dex */
public class EditImageUtils {
    public static BitmapDrawable getBitmapDrawable(Context context, int i, int i2, int i3) {
        Drawable drawable = ContextCompat.getDrawable(context, i3);
        if (drawable == null || i <= 0 || i2 <= 0) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
    }

    public static int measureBitmapSize(Context context, Size size, int i, SizeF sizeF, int i2, Point point) {
        if (size.getWidth() <= 0 || size.getHeight() <= 0) {
            return -1;
        }
        int width = (int) (i * sizeF.getWidth());
        int iMax = (int) (Math.max(CommonUtils.getRealHeight(context), CommonUtils.getRealWidth(context)) * sizeF.getHeight());
        int height = (size.getHeight() * width) / size.getWidth();
        if (height <= iMax) {
            iMax = height;
        }
        int iMax2 = Math.max(iMax, 1);
        int iMax3 = Math.max(width, 1);
        point.set(iMax3, iMax2);
        return computeSampleSize(iMax3, iMax2, size, i2);
    }

    private static int computeSampleSize(int i, int i2, Size size, int i3) {
        int iRoundSampleSize = roundSampleSize(Math.max(size.getWidth() / i, size.getHeight() / i2));
        return ((float) ((size.getWidth() * size.getHeight()) * 4)) / ((float) (iRoundSampleSize * iRoundSampleSize)) > ((float) i3) ? roundSampleSize(Math.round(Math.max(size.getWidth() / i, size.getHeight() / i2))) : iRoundSampleSize;
    }

    private static int roundSampleSize(int i) {
        if (i > 8) {
            return ((i + 7) / 8) * 8;
        }
        int i2 = 1;
        while (i2 < i) {
            i2 <<= 1;
        }
        return i2;
    }

    public static Bitmap resizeImageByUri(Context context, int i, int i2, Uri uri) {
        BitmapDecoder bitmapDecoder = new BitmapDecoder(context, uri);
        int height = (bitmapDecoder.getHeight() * i) / bitmapDecoder.getWidth();
        int iMax = Math.max(i, 1);
        int iMax2 = Math.max(height, 1);
        if (iMax2 <= i2) {
            i2 = iMax2;
        } else if (LiteUtils.isLiteOrMiddle()) {
            iMax = Math.max((bitmapDecoder.getWidth() * i2) / bitmapDecoder.getHeight(), 1);
            i2 = Math.max(i2, 1);
        }
        Bitmap scaledBitmap = bitmapDecoder.getScaledBitmap(iMax, i2, 48);
        return (scaledBitmap == null || scaledBitmap.isMutable()) ? scaledBitmap : scaledBitmap.copy(scaledBitmap.getConfig(), true);
    }
}
