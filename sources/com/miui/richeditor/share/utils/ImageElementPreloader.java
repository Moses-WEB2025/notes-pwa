package com.miui.richeditor.share.utils;

import android.graphics.Bitmap;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.render.Render;

/* JADX INFO: loaded from: classes4.dex */
public interface ImageElementPreloader {
    void cancelTask();

    Bitmap createBitmap();

    Bitmap getBitmap();

    String getCacheKey();

    ImageElement getElement();

    void scheduleTask(Render.BitmapLoadListener bitmapLoadListener);

    void setBitmap(Bitmap bitmap);
}
