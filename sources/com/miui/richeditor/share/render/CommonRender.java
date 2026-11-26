package com.miui.richeditor.share.render;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.render.common.ElementRender;
import com.miui.richeditor.share.render.common.ImageElementRender;
import com.miui.richeditor.share.render.common.LinkCardElementRender;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class CommonRender extends Render {
    private static final String TAG = "CommonRender";

    static {
        System.loadLibrary("duokantext");
    }

    public CommonRender(Context context, List<Element> list, Drawable drawable, int i, long j) {
        super(context, list, drawable, i, j);
        drawable.getPadding(this.mPadding);
        layout();
    }

    private void layout() {
        int i = this.mPadding.top;
        int height = i;
        for (Element element : this.mElements) {
            ElementRender<? extends Element> elementRenderCreate = ElementRender.create(this.mContext, element, height, this.mWidth, this.mThemeId);
            if (element.getType() == 5) {
                ((LinkCardElementRender) elementRenderCreate).setDrawHelper(getLinkCardDrawHelper());
            }
            this.mRenders.add(elementRenderCreate);
            if (element.getType() == 1) {
                this.mImageRenders.add((ImageElementRender) elementRenderCreate);
            }
            height += elementRenderCreate.getHeight();
        }
        this.mHeight = height + this.mPadding.bottom;
        this.mBackground.setBounds(0, 0, this.mWidth, this.mHeight);
    }
}
