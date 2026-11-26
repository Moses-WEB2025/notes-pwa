package com.miui.pc.richeditor;

import android.graphics.drawable.Drawable;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.render.DefaultEditorImageRender;

/* JADX INFO: loaded from: classes4.dex */
public class PcEditorImageRender extends DefaultEditorImageRender {
    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender, com.miui.richeditor.style.render.IEditorImageRender
    public float getImagePreviewMaxSizeScale() {
        return 0.7f;
    }

    public PcEditorImageRender(IEditorContext iEditorContext, int i) {
        super(iEditorContext, i);
    }

    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender
    protected void initImageParams() {
        this.mImageRadius = 8;
        this.mImageSelectBorderWidth = 2.0f;
    }

    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender, com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getBulletDrawable() {
        if (this.mBulletDrawableCache == null) {
            this.mBulletDrawableCache = this.mLastEditorTheme.getEditorStyle(this.mContext).getBulletsIcon();
        }
        return this.mBulletDrawableCache;
    }

    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender, com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getCheckboxDrawable() {
        if (this.mCheckboxDrawableCache == null) {
            this.mCheckboxDrawableCache = this.mContext.getDrawable(R.drawable.pc_ic_richtext_check);
        }
        return this.mCheckboxDrawableCache;
    }
}
