package com.miui.richeditor.style.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Size;
import com.miui.richeditor.util.SmoothDrawHelper;

/* JADX INFO: loaded from: classes4.dex */
public interface IEditorImageRender {
    void clearSpanDrawableCache();

    int getAnnotationColor();

    Drawable getBulletDrawable();

    Drawable getCheckboxDrawable();

    Bitmap getContactImage(String str);

    BitmapDrawable getDefaultImageDrawable();

    BitmapDrawable getDefaultVideoDrawable();

    Drawable getImageCoverDrawable();

    float getImagePreviewMaxSizeScale();

    int getImageRadius();

    float getImageSelectBorderWidth();

    SmoothDrawHelper getRadiusDrawHelper();

    float getShowBigScale();

    float getShowSmallScale();

    int measureBitmapSize(Context context, Size size, int i, Point point);

    void onThemeChanged();

    void updateContentWidth(int i);
}
