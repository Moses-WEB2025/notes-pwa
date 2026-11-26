package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.WindowManager;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.tool.BitmapDecoder;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.share.utils.BitmapPreloadTask;
import com.miui.richeditor.share.utils.ImageElementPreloader;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class ImageElementRender extends ElementRender<ImageElement> implements ImageElementPreloader {
    private final int MAX_IMAGE_HEIGHT;
    private StaticLayout mAnnotateLayout;
    protected int mAnnotationHeight;
    protected int mAnnotationMarginTop;
    protected int mAnnotationMaxLine;
    private Bitmap mBitmap;
    private BitmapDecoder mDecoder;
    private TextPaint mFontPaint;
    private int mImageRadius;
    private Path mRoundPath;
    private int mScreenHeight;
    private SmoothDrawHelper mSmoothDrawHelper;
    private BitmapPreloadTask mTask;

    public ImageElementRender(Context context, ImageElement imageElement, int i, int i2, long j) throws Throwable {
        super(context, imageElement, i, i2, j);
        this.MAX_IMAGE_HEIGHT = 65500;
        this.mFontPaint = new TextPaint();
        this.mAnnotateLayout = null;
        this.mAnnotationMaxLine = 3;
        this.mAnnotationHeight = 0;
        this.mAnnotationMarginTop = 0;
        if (((ImageElement) this.mElement).getUri() == null) {
            return;
        }
        this.mDecoder = new BitmapDecoder(this.mContext, ((ImageElement) this.mElement).getUri());
        this.mAnnotationMarginTop = context.getResources().getDimensionPixelSize(R.dimen.image_annotation_content_margin_top);
        this.mFontPaint.setAntiAlias(true);
        this.mFontPaint.setStyle(Paint.Style.FILL);
        this.mFontPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.image_annotation_text_size));
        this.mFontPaint.setColor(((ImageElement) this.mElement).getFontColor());
        this.mFontPaint.setAlpha(102);
        layout();
        this.mScreenHeight = ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getHeight();
        this.mImageRadius = ((ImageElement) this.mElement).getRadius();
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        this.mRoundPath = new Path();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public ImageElement getElement() {
        return (ImageElement) this.mElement;
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public void scheduleTask(Render.BitmapLoadListener bitmapLoadListener) {
        if (this.mTask == null) {
            BitmapPreloadTask bitmapPreloadTask = new BitmapPreloadTask(this, bitmapLoadListener);
            this.mTask = bitmapPreloadTask;
            bitmapPreloadTask.commit();
        }
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public void cancelTask() {
        BitmapPreloadTask bitmapPreloadTask = this.mTask;
        if (bitmapPreloadTask != null) {
            bitmapPreloadTask.cancel();
            this.mTask = null;
        }
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public Bitmap createBitmap() {
        Bitmap scaledBitmap;
        if (this.mDecoder == null || this.mIsDestroying || (scaledBitmap = this.mDecoder.getScaledBitmap(getBitmapWidth(), 0)) == null) {
            return null;
        }
        Bitmap bitmapCopy = scaledBitmap.copy(scaledBitmap.getConfig(), true);
        scaledBitmap.recycle();
        return bitmapCopy;
    }

    @Override // com.miui.richeditor.share.utils.ImageElementPreloader
    public String getCacheKey() {
        if (this.mIsDestroying) {
            return "";
        }
        return String.format(Locale.US, "%s:%d", this.mDecoder.getUri(), Integer.valueOf(getBitmapWidth()));
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
        Bitmap bitmap;
        if (this.mIsDestroying || (bitmap = this.mBitmap) == null) {
            return;
        }
        int height = bitmap.getHeight();
        int i = this.mScreenHeight;
        if (height <= i) {
            drawBitmap(canvas, this.mBitmap, ((ImageElement) this.mElement).getMarginLeft(), ((ImageElement) this.mElement).getMarginTop());
            drawAnnotation(canvas);
            return;
        }
        int i2 = height / i;
        int i3 = height % i;
        for (int i4 = 0; i4 < i2; i4++) {
            Bitmap bitmap2 = this.mBitmap;
            int i5 = i4 * i;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap2, 0, i5, bitmap2.getWidth(), i);
            drawBitmap(canvas, bitmapCreateBitmap, ((ImageElement) this.mElement).getMarginLeft(), ((ImageElement) this.mElement).getMarginTop() + i5);
            if (bitmapCreateBitmap != this.mBitmap) {
                bitmapCreateBitmap.recycle();
            }
        }
        if (i3 > 0) {
            Bitmap bitmap3 = this.mBitmap;
            int i6 = i2 * i;
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap3, 0, i6, bitmap3.getWidth(), i3);
            drawBitmap(canvas, bitmapCreateBitmap2, ((ImageElement) this.mElement).getMarginLeft(), ((ImageElement) this.mElement).getMarginTop() + i6);
            if (bitmapCreateBitmap2 != this.mBitmap) {
                bitmapCreateBitmap2.recycle();
            }
        }
        drawAnnotation(canvas);
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void render(Canvas canvas) {
        BitmapDecoder bitmapDecoder;
        if (this.mIsDestroying) {
            return;
        }
        Bitmap bitmap = this.mBitmap;
        if ((bitmap == null || bitmap.isRecycled()) && (bitmapDecoder = this.mDecoder) != null) {
            this.mBitmap = bitmapDecoder.getScaledBitmap(getBitmapWidth(), 0);
        }
        if (this.mBitmap == null) {
            return;
        }
        if (this.mBitmap.getHeight() >= 65500 - (UIUtils.dip2px(NoteApp.getInstance(), NoteApp.getInstance().getResources().getDimension(R.dimen.theme_white_share_frame_top_space)) * 2)) {
            renderBitmap(canvas, this.mBitmap, (this.mWidth - this.mBitmap.getWidth()) / 2, ((ImageElement) this.mElement).getMarginTop());
        } else {
            renderBitmap(canvas, this.mBitmap, ((ImageElement) this.mElement).getMarginLeft(), ((ImageElement) this.mElement).getMarginTop());
        }
        drawAnnotation(canvas);
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void destroy() {
        super.destroy();
        cancelTask();
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
        this.mDecoder = null;
    }

    private void drawAnnotation(Canvas canvas) {
        canvas.save();
        canvas.translate(((ImageElement) this.mElement).getMarginLeft(), ((this.mHeight - getAnnotationHeight()) + this.mAnnotationMarginTop) - ((ImageElement) this.mElement).getMarginBottom());
        StaticLayout staticLayout = this.mAnnotateLayout;
        if (staticLayout != null) {
            staticLayout.draw(canvas);
        }
        canvas.restore();
    }

    private void layout() throws Throwable {
        ImageElementRender imageElementRender;
        int scaleHeight = this.mDecoder.getScaleHeight(getBitmapWidth(), 0);
        if (TextUtils.isEmpty(((ImageElement) this.mElement).getAnnotation())) {
            imageElementRender = this;
        } else {
            imageElementRender = this;
            StaticLayout staticLayoutCreateStaticLayout = imageElementRender.createStaticLayout(((ImageElement) this.mElement).getAnnotation(), 0, ((ImageElement) this.mElement).getAnnotation().length(), this.mFontPaint, getBitmapWidth(), TextUtils.TruncateAt.END, this.mAnnotationMaxLine);
            imageElementRender.mAnnotateLayout = staticLayoutCreateStaticLayout;
            imageElementRender.mAnnotationHeight = staticLayoutCreateStaticLayout.getHeight() + imageElementRender.mAnnotationMarginTop;
        }
        imageElementRender.mHeight = scaleHeight + ((ImageElement) imageElementRender.mElement).getMarginTop() + imageElementRender.getAnnotationHeight() + ((ImageElement) imageElementRender.mElement).getMarginBottom();
    }

    private void drawBitmap(Canvas canvas, Bitmap bitmap, float f, float f2) {
        if (((ImageElement) this.mElement).isScaled()) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.setTranslate(f, f2);
            bitmapShader.setLocalMatrix(matrix);
            RectF rectF = new RectF(f, f2, bitmap.getWidth() + f, bitmap.getHeight() + f2);
            Paint paint = new Paint();
            paint.setShader(bitmapShader);
            Path path = this.mRoundPath;
            int i = this.mImageRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CW);
            canvas.drawPath(this.mRoundPath, paint);
            return;
        }
        canvas.drawBitmap(this.mBitmap, f, f2, (Paint) null);
    }

    private void renderBitmap(Canvas canvas, Bitmap bitmap, float f, float f2) {
        if (((ImageElement) this.mElement).isScaled()) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.setTranslate(f, f2);
            bitmapShader.setLocalMatrix(matrix);
            RectF rectF = new RectF(f, f2, bitmap.getWidth() + f, bitmap.getHeight() + f2);
            Paint paint = new Paint();
            paint.setShader(bitmapShader);
            Path path = this.mRoundPath;
            int i = this.mImageRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CW);
            canvas.drawPath(this.mRoundPath, paint);
            return;
        }
        canvas.drawBitmap(bitmap, f, f2, (Paint) null);
    }

    private int getBitmapWidth() {
        if (((ImageElement) this.mElement).isScaled()) {
            int marginLeft = (this.mWidth - ((ImageElement) this.mElement).getMarginLeft()) - ((ImageElement) this.mElement).getMarginRight();
            return ((ImageElement) this.mElement).isShowBig() ? marginLeft : marginLeft / 2;
        }
        return this.mDecoder.getWidth();
    }

    private int getAnnotationHeight() {
        if (TextUtils.isEmpty(((ImageElement) this.mElement).getAnnotation())) {
            return 0;
        }
        return this.mAnnotationHeight;
    }

    private StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
        return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setMaxLines(i4).setEllipsize(truncateAt).build();
    }
}
