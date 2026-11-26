package com.miui.handwriting.common.listener;

import android.graphics.RectF;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;

/* JADX INFO: loaded from: classes3.dex */
public interface CanvasElementTransactCallback {
    void onTransactCanvasElement(RectF rectF, CanvasMaskEntity canvasMaskEntity, float f, int i, String str, boolean z, String str2, boolean z2);

    void onTransactCanvasElementFromText(RectF rectF, CanvasMaskEntity canvasMaskEntity, String str);
}
