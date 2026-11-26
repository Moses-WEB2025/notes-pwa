package com.miui.pc.richeditor;

import android.view.View;
import com.miui.richeditor.EditorActionListener;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.NormalImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public interface PcEditorActionListener extends EditorActionListener {
    void onImageDragged();

    void onImageMenuClick(View view, BaseImageSpan baseImageSpan);

    void onSaveToSynergy(NormalImageSpan normalImageSpan);
}
