package com.miui.richeditor;

import android.view.View;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.LinkCardSpan;

/* JADX INFO: loaded from: classes4.dex */
public interface IEditorPopMenu {

    public interface PopMenuListener {
        void onImageMenuClick(View view, BaseImageSpan baseImageSpan);

        void onLinkCardMenuClick(View view, LinkCardSpan linkCardSpan);
    }

    void dismiss();

    boolean isShowing();
}
