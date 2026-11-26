package com.miui.pc.richeditor.style;

import android.content.Context;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.EditLineHeightSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PcEditLineHeightSpan extends EditLineHeightSpan {
    public PcEditLineHeightSpan(IEditorContext iEditorContext, int i) {
        this(iEditorContext.getContext(), iEditorContext, i);
    }

    public PcEditLineHeightSpan(Context context, IEditorContext iEditorContext, int i) {
        super(iEditorContext, i);
    }
}
