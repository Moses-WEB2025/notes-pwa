package com.miui.pad.richeditor;

import android.graphics.drawable.Drawable;
import com.miui.notes.R;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.render.DefaultEditorImageRender;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PadEditorImageRender extends DefaultEditorImageRender {
    public PadEditorImageRender(IEditorContext iEditorContext, int i) {
        super(iEditorContext, i);
    }

    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender
    protected void initImageParams() {
        this.mImageRadius = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_note_edit_image_shape_radius);
        this.mImageSelectBorderWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_note_edit_image_select_border_width);
    }

    @Override // com.miui.richeditor.style.render.DefaultEditorImageRender, com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getBulletDrawable() {
        if (this.mBulletDrawableCache == null) {
            Theme editorTheme = this.mEditorContext.getEditorTheme();
            if (editorTheme == null) {
                Drawable bulletsIcon = ResourceManager.getTheme(0).getEditorStyle(this.mContext).getBulletsIcon();
                bulletsIcon.setAlpha(255);
                return bulletsIcon;
            }
            this.mBulletDrawableCache = editorTheme.getEditorStyle(this.mContext).getBulletsIcon();
            this.mBulletDrawableCache.setAlpha(255);
        }
        return this.mBulletDrawableCache;
    }
}
