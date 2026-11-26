package com.miui.richeditor;

import android.net.Uri;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;

/* JADX INFO: loaded from: classes4.dex */
public interface EditorActionListener {
    void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr);

    void onImageSpanAnnotationClick(BaseImageSpan baseImageSpan, int[] iArr);

    void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr);

    void onInsertImages(Uri... uriArr);

    void onLinkSpanClick(LinkCardSpan linkCardSpan, int[] iArr);

    void onPlayingRecord(boolean z);

    void onRichTextLoaded();

    void onSaveData();

    default void onStopShowLoading() {
    }

    void onTrackEvent(String str);
}
