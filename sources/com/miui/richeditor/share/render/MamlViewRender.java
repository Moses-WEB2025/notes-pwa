package com.miui.richeditor.share.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.view.View;
import android.view.ViewGroup;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.render.common.ElementRender;
import com.miui.richeditor.share.render.common.ImageElementRender;
import com.miui.richeditor.share.render.common.LinkCardElementRender;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MamlViewRender extends Render {
    private static final String TAG = "MamlViewRender";
    private View mBackgroundView;
    private Runnable mBgTask;
    private Runnable mCallback;

    @Override // com.miui.richeditor.share.render.Render
    protected int getImageQuality() {
        return 100;
    }

    static {
        System.loadLibrary("duokantext");
    }

    public MamlViewRender(Context context, List<Element> list, Drawable drawable, View view, int i, Runnable runnable, long j) {
        super(context, list, null, i, j);
        this.mBgTask = null;
        drawable.getPadding(this.mPadding);
        this.mBackgroundView = view;
        this.mCallback = runnable;
        layout();
    }

    @Override // com.miui.richeditor.share.render.Render
    public void destroy() {
        View view = this.mBackgroundView;
        if (view != null) {
            view.removeCallbacks(this.mBgTask);
            this.mBackgroundView = null;
            this.mBgTask = null;
        }
        super.destroy();
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
        Logger.INSTANCE.i(TAG, "layout() mWidth = " + this.mWidth + " mHeight = " + this.mHeight);
        if (this.mHeight > 65500) {
            this.mHeight = 65500;
        }
        ViewGroup.LayoutParams layoutParams = this.mBackgroundView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(this.mWidth, this.mHeight);
        } else {
            layoutParams.width = this.mWidth;
            layoutParams.height = this.mHeight;
        }
        this.mBgTask = new Runnable() { // from class: com.miui.richeditor.share.render.MamlViewRender.1
            @Override // java.lang.Runnable
            public void run() {
                if (MamlViewRender.this.mContext != null) {
                    MamlViewRender mamlViewRender = MamlViewRender.this;
                    mamlViewRender.mBackground = mamlViewRender.getBackgroundFromView(mamlViewRender.mContext, MamlViewRender.this.mBackgroundView);
                    MamlViewRender.this.mBackground.setBounds(0, 0, MamlViewRender.this.mWidth, MamlViewRender.this.mHeight);
                    if (MamlViewRender.this.mCallback != null) {
                        MamlViewRender.this.mCallback.run();
                    }
                }
            }
        };
        this.mBackgroundView.setLayoutParams(layoutParams);
        this.mBackgroundView.post(this.mBgTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getBackgroundFromView(Context context, View view) {
        Bitmap bitmapCreateBitmap;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            bitmapCreateBitmap = null;
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.translate(-view.getScrollX(), -view.getScrollY());
            view.draw(canvas);
        }
        if (bitmapCreateBitmap == null) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_share_inset);
        return new LayerDrawable(new Drawable[]{bitmapDrawable, new InsetDrawable(context.getResources().getDrawable(R.drawable.v8_theme_white_share_bg), 0, dimensionPixelSize, 0, dimensionPixelSize)});
    }
}
