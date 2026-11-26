package com.miui.richeditor;

import android.view.View;
import com.miui.richeditor.style.BaseImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public interface SpanClickListener {
    void onImageAnnotationClick(View view, BaseImageSpan baseImageSpan);

    void onImageDragged(View view, BaseImageSpan baseImageSpan);

    void onImageSpanClick(View view, BaseImageSpan baseImageSpan);
}
