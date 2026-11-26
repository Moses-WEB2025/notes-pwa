package com.miui.richeditor.style.render;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Size;
import android.util.SizeF;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.DefaultTheme;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.EditImageUtils;
import com.miui.richeditor.util.SmoothDrawHelper;
import java.io.IOException;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultEditorImageRender implements IEditorImageRender {
    private static final float IMAGE_MAX_SCREEN_HEIGHT_FACTOR = 2.0f;
    private static final float IMAGE_MAX_SCREEN_WIDTH_FACTOR = 1.0f;
    protected int mBitmapDataSizeLimit;
    protected SizeF mBitmapSizeConstraint;
    protected int mContentWidth;
    protected Context mContext;
    protected BitmapDrawable mDefaultImageDrawable;
    protected BitmapDrawable mDefaultVideoDrawable;
    protected IEditorContext mEditorContext;
    protected Drawable mImageCoverDrawable;
    protected int mImageRadius;
    protected float mImageSelectBorderWidth;
    protected Theme mLastEditorTheme;
    protected SmoothDrawHelper mSmoothDrawHelper = new SmoothDrawHelper();
    protected Drawable mCheckboxDrawableCache = null;
    protected Drawable mBulletDrawableCache = null;

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public float getImagePreviewMaxSizeScale() {
        return 1.0f;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public float getShowBigScale() {
        return 1.0f;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public float getShowSmallScale() {
        return 0.5f;
    }

    public DefaultEditorImageRender(IEditorContext iEditorContext, int i) {
        this.mEditorContext = iEditorContext;
        this.mContext = iEditorContext.getContext();
        this.mContentWidth = i;
        Theme editorTheme = iEditorContext.getEditorTheme();
        this.mLastEditorTheme = editorTheme;
        if (editorTheme == null) {
            this.mLastEditorTheme = new DefaultTheme(0);
        }
        this.mImageCoverDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.picture_frame);
        this.mBitmapDataSizeLimit = (((ActivityManager) this.mContext.getSystemService("activity")).getMemoryClass() * 1048576) / 32;
        this.mBitmapSizeConstraint = new SizeF(1.0f, 2.0f);
        initImageParams();
    }

    protected void initImageParams() {
        this.mImageRadius = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_image_span_shape_radius);
        this.mImageSelectBorderWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_image_span_select_border_width);
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public void onThemeChanged() {
        this.mLastEditorTheme = this.mEditorContext.getEditorTheme();
        clearSpanDrawableCache();
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public void clearSpanDrawableCache() {
        this.mCheckboxDrawableCache = null;
        this.mBulletDrawableCache = null;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getImageCoverDrawable() {
        return this.mImageCoverDrawable;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public BitmapDrawable getDefaultImageDrawable() throws Throwable {
        if (this.mDefaultImageDrawable == null) {
            Bitmap bitmapAdjustBitmapSize = adjustBitmapSize(R.drawable.picture_missing);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapAdjustBitmapSize);
            this.mDefaultImageDrawable = bitmapDrawable;
            bitmapDrawable.setBounds(0, 0, bitmapAdjustBitmapSize.getWidth(), bitmapAdjustBitmapSize.getHeight());
        }
        return this.mDefaultImageDrawable;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public BitmapDrawable getDefaultVideoDrawable() {
        Bitmap bitmapAdjustBitmapSize;
        if (this.mDefaultVideoDrawable == null && (bitmapAdjustBitmapSize = adjustBitmapSize(R.drawable.picture_video)) != null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapAdjustBitmapSize);
            this.mDefaultVideoDrawable = bitmapDrawable;
            bitmapDrawable.setBounds(0, 0, bitmapAdjustBitmapSize.getWidth(), bitmapAdjustBitmapSize.getHeight());
        }
        return this.mDefaultVideoDrawable;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getBulletDrawable() {
        if (this.mBulletDrawableCache == null) {
            this.mBulletDrawableCache = this.mLastEditorTheme.getEditorStyle(this.mContext).getBulletsIcon();
        }
        return this.mBulletDrawableCache;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public Drawable getCheckboxDrawable() {
        if (this.mCheckboxDrawableCache == null) {
            this.mCheckboxDrawableCache = this.mLastEditorTheme.getCheckboxResource(this.mContext);
        }
        return this.mCheckboxDrawableCache;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public Bitmap getContactImage(String str) {
        TextView textView = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.contact_span_view, (ViewGroup) null);
        textView.setText(str);
        textView.setTextSize(0, this.mEditorContext.getTextSize());
        FrameLayout frameLayout = new FrameLayout(this.mContext);
        frameLayout.addView(textView);
        ((FrameLayout.LayoutParams) textView.getLayoutParams()).bottomMargin = this.mEditorContext.getLineSpacing() - this.mEditorContext.getPaint().getFontMetricsInt().bottom;
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.measure(0, 0);
        frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
        return frameLayout.getDrawingCache();
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public void updateContentWidth(int i) {
        this.mContentWidth = i;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public SmoothDrawHelper getRadiusDrawHelper() {
        return this.mSmoothDrawHelper;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public int getImageRadius() {
        return this.mImageRadius;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public float getImageSelectBorderWidth() {
        return this.mImageSelectBorderWidth;
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public int getAnnotationColor() {
        Context context = this.mContext;
        return context.getColor(this.mLastEditorTheme.getEditorStyle(context).mTextColor);
    }

    @Override // com.miui.richeditor.style.render.IEditorImageRender
    public int measureBitmapSize(Context context, Size size, int i, Point point) {
        return EditImageUtils.measureBitmapSize(context, size, i, this.mBitmapSizeConstraint, this.mBitmapDataSizeLimit, point);
    }

    private Bitmap adjustBitmapSize(int i) throws Throwable {
        Uri uri = Uri.parse("android.resource://" + this.mContext.getPackageName() + InternalZipConstants.ZIP_FILE_SEPARATOR + i);
        try {
            BitmapFactory.Options bitmapSize = miuix.graphics.BitmapFactory.getBitmapSize(this.mContext, uri);
            Point point = new Point();
            if (measureBitmapSize(this.mContext, new Size(bitmapSize.outWidth, bitmapSize.outHeight), this.mContentWidth, point) <= 0) {
                return null;
            }
            return EditImageUtils.resizeImageByUri(this.mContext, point.x, point.y, uri);
        } catch (IOException e) {
            Logger.INSTANCE.e("DefaultImageStyle", "Fail to load image from resource: " + i + ",exception:" + e);
            return null;
        }
    }
}
