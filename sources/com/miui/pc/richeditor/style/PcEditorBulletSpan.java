package com.miui.pc.richeditor.style;

import android.content.Context;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.EditorBulletSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PcEditorBulletSpan extends EditorBulletSpan {
    public PcEditorBulletSpan(IEditorContext iEditorContext) {
        this(iEditorContext.getContext(), iEditorContext);
    }

    public PcEditorBulletSpan(Context context, IEditorContext iEditorContext) {
        super(iEditorContext);
        this.mSpScale = 1.0f;
        this.mMarginEnd = 10;
    }

    public PcEditorBulletSpan(IEditorContext iEditorContext, HtmlParser.BulletElement bulletElement) {
        this(iEditorContext.getContext(), iEditorContext, bulletElement);
    }

    public PcEditorBulletSpan(Context context, IEditorContext iEditorContext, HtmlParser.BulletElement bulletElement) {
        super(iEditorContext, bulletElement);
        this.mSpScale = 1.0f;
        this.mMarginEnd = 10;
    }

    @Override // com.miui.richeditor.style.EditorBulletSpan
    protected int getImageSize(int i) {
        return (int) (this.mSpScale * 24.0f);
    }
}
