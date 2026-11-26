package com.miui.richeditor.style;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Size;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.async.Task;
import com.miui.common.tool.async.TaskManager;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.BitmapTask;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.SpannableImageTask;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.render.IEditorImageRender;
import java.io.File;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class NormalImageSpan extends SelectableImageSpan {
    private static final String TAG = "CommonImageSpan";
    protected Rect mDrawBmRect;
    protected BitmapTask mImageLoadTask;
    private final Rect mLastDrawBound;
    protected Path mRoundPath;
    protected RectF mRoundRectF;
    private Task.Listener mTaskListener;
    private ValueAnimator mTransitAnimator;

    public NormalImageSpan(IEditorContext iEditorContext, HtmlParser.ImageElement imageElement) {
        this(iEditorContext, imageElement, iEditorContext.getSelectableSpanListener());
    }

    public NormalImageSpan(IEditorContext iEditorContext, HtmlParser.ImageElement imageElement, SelectableSpanListener selectableSpanListener) {
        super(iEditorContext, imageElement, selectableSpanListener);
        this.mDrawBmRect = new Rect();
        this.mRoundRectF = new RectF();
        this.mRoundPath = new Path();
        this.mLastDrawBound = new Rect();
        this.mTransitAnimator = null;
        this.mTaskListener = new Task.Listener() { // from class: com.miui.richeditor.style.NormalImageSpan.1
            @Override // com.miui.common.tool.async.Task.Listener
            public void onCanceled(TaskManager taskManager, Task<?> task) {
            }

            @Override // com.miui.common.tool.async.Task.Listener
            public void onException(TaskManager taskManager, Task<?> task, Exception exc) {
            }

            @Override // com.miui.common.tool.async.Task.Listener
            public void onPrepare(TaskManager taskManager, Task<?> task) {
            }

            @Override // com.miui.common.tool.async.Task.Listener
            public void onProgress(TaskManager taskManager, Task<?> task, int i, int i2) {
            }

            @Override // com.miui.common.tool.async.Task.Listener
            public Object onResult(TaskManager taskManager, Task<?> task, Object obj) {
                NormalImageSpan.this.setImage((Bitmap) obj);
                return obj;
            }

            @Override // com.miui.common.tool.async.Task.Listener
            public void onFinalize(TaskManager taskManager, Task<?> task) {
                NormalImageSpan.this.mImageLoadTask = null;
            }
        };
        initSmoothDrawable();
        this.mSrcFileId = imageElement.getFileId();
        if (!TextUtils.isEmpty(imageElement.getImgShow())) {
            this.mShowBig = "0".equals(imageElement.getImgShow());
        }
        if (!TextUtils.isEmpty(imageElement.getImgDes())) {
            this.mHasAnnotation = true;
            this.mAnnotation = imageElement.getImgDes();
        }
        updateAnnotation(this.mAnnotation, false);
        IEditorContext iEditorContext2 = this.mIEditorContextWeakReference != null ? this.mIEditorContextWeakReference.get() : null;
        if (this.mShowBig && iEditorContext2 != null) {
            this.mShowScale = iEditorContext2.getImageRender().getShowBigScale();
        } else if (iEditorContext2 != null) {
            this.mShowScale = iEditorContext2.getImageRender().getShowSmallScale();
        }
        initAnnotationTextPaint();
    }

    public void initByContainer() {
        String imagePath = getImagePath();
        if (imagePath == null || !new File(imagePath).exists()) {
            initMissedDrawableSize();
            this.mMissed = true;
            return;
        }
        try {
            BitmapFactory.Options bitmapSize = miuix.graphics.BitmapFactory.getBitmapSize(imagePath);
            this.mSrcWidth = bitmapSize.outWidth;
            this.mSrcHeight = bitmapSize.outHeight;
            this.mMissed = false;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Fail to load image: " + this.mSrcFileId + ",exception:" + e);
            this.mSrcHeight = 0;
            this.mSrcWidth = 0;
            this.mMissed = true;
        }
    }

    protected void initSmoothDrawable() {
        this.mImageUnSelectBorderPaint.setAntiAlias(true);
        this.mImageUnSelectBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.smartimagespan_border_color));
        this.mImageUnSelectBorderPaint.setStyle(Paint.Style.STROKE);
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            this.mImageUnSelectBorderPaint.setStrokeWidth(iEditorContext.getImageRender().getImageSelectBorderWidth());
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void initMargin() {
        this.mMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.image_content_margin_top);
        this.mMarginBottom = getContext().getResources().getDimensionPixelSize(R.dimen.image_content_margin_bottom);
    }

    public void toggleShowBig() {
        this.mShowBig = !this.mShowBig;
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (this.mShowBig && iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowBigScale();
        } else if (iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowSmallScale();
        }
        if (getElement() instanceof HtmlParser.ImageElement) {
            ((HtmlParser.ImageElement) getElement()).setAttribute(HtmlParser.ImageElement.KEY_IMGSHOW, this.mShowBig ? "0" : "1");
        }
        startResizeAnim(this.mShowBig);
    }

    public void enterEditAnnotation() {
        this.mIsEditAnno = true;
    }

    public void exitEditAnnotation() {
        this.mIsEditAnno = false;
    }

    public HtmlParser.ImageElement getDisplayElement() {
        return (HtmlParser.ImageElement) getElement();
    }

    protected String getImagePath() {
        return AttachmentUtils.getAttachmentPath(getContext(), this.mSrcFileId);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void initMissedDrawableSize() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            this.mSrcHeight = iEditorContext.getImageRender().getDefaultImageDrawable().getIntrinsicHeight();
            this.mSrcWidth = iEditorContext.getImageRender().getDefaultImageDrawable().getIntrinsicWidth();
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void prepareMissedDrawable() {
        super.prepareMissedDrawable();
        initSmoothDrawable();
    }

    public void restoreImage() {
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.removeAllUpdateListeners();
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        IEditorContext iEditorContext = this.mIEditorContextWeakReference != null ? this.mIEditorContextWeakReference.get() : null;
        if (this.mShowBig && iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowBigScale();
        } else if (iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowSmallScale();
        }
        setAlpha(255);
        this.mDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), Bitmap.createBitmap(this.mSrcBitmap));
        initSmoothDrawable();
        this.mBounds.set(0, 0, 0, 0);
        this.mSrcBounds.set(0, 0, 0, 0);
        updateDrawParams();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void recycle() {
        Logger.INSTANCE.d(TAG, " recycle");
        BitmapTask bitmapTask = this.mImageLoadTask;
        if (bitmapTask != null) {
            bitmapTask.removeListener(this.mTaskListener);
            this.mImageLoadTask.cancel();
            this.mImageLoadTask = null;
        }
        super.recycle();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void prepare() {
        updateDrawParams();
        if (this.mDrawable != null && (this.mDrawable instanceof BitmapDrawable) && ((BitmapDrawable) this.mDrawable).getBitmap().isRecycled()) {
            this.mDrawable = null;
        }
        if (this.mDrawable == null) {
            if (isMissed()) {
                prepareMissedDrawable();
                IEditorContext iEditorContext = this.mIEditorContextWeakReference != null ? this.mIEditorContextWeakReference.get() : null;
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                    return;
                }
                return;
            }
            if (this.mImageLoadTask == null) {
                SpannableImageTask spannableImageTask = new SpannableImageTask(getContext(), this.mSrcFileId, this.mSrcBmSize.x, this.mSrcBmSize.y, this.nScale);
                this.mImageLoadTask = spannableImageTask;
                spannableImageTask.addListener(this.mTaskListener);
                this.mImageLoadTask.commit();
            }
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        super.getSize(paint, charSequence, i, i2, fontMetricsInt);
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = (-this.mBounds.height()) - this.mMarginTop;
            fontMetricsInt.descent = this.mMarginBottom;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.descent;
            Logger.INSTANCE.d(TAG, "SmartImageSpan.draw0: getSize lineHeight = " + (fontMetricsInt.descent - fontMetricsInt.ascent) + " mMarginTop = " + this.mMarginTop + " nBounds.height() = " + this.mBounds.height());
        }
        Logger.INSTANCE.d(TAG, "getSize mBounds.width() = " + this.mBounds.width());
        return this.mBounds.width();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void updateDrawParams() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null) {
            return;
        }
        int width = iEditorContext.getRootContainer().getWidth();
        if (width == 0) {
            Logger.INSTANCE.d(TAG, "updateDrawParams error rootContainerWidth is 0");
            return;
        }
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && this.mSpanContentWidth == Math.ceil(iEditorContext.getContentWidth() * this.mShowScale * iEditorContext.getImageRender().getImagePreviewMaxSizeScale()) && this.mEditorViewY == ((int) iEditorContext.getView().getY())) {
            return;
        }
        this.mBounds.set(0, 0, 0, 0);
        BitmapTask bitmapTask = this.mImageLoadTask;
        if (bitmapTask != null) {
            bitmapTask.removeListener(this.mTaskListener);
            this.mImageLoadTask.cancel();
            this.mImageLoadTask = null;
        }
        ValueAnimator valueAnimator2 = this.mTransitAnimator;
        if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
            this.mSrcBounds.set(0, 0, 0, 0);
            Point point = new Point();
            Size size = new Size(this.mSrcWidth, this.mSrcHeight);
            iEditorContext.getImageRender().measureBitmapSize(getContext(), size, width, this.mSrcBmSize);
            this.nScale = iEditorContext.getImageRender().measureBitmapSize(getContext(), size, iEditorContext.getContentWidth(), point);
            this.mSrcBounds.right = Math.max(point.x, iEditorContext.getImageRender().getImageCoverDrawable().getIntrinsicWidth());
            if (isMissed()) {
                computeMissedBounds();
            } else {
                this.mSrcBounds.bottom = Math.max(point.y, iEditorContext.getImageRender().getImageCoverDrawable().getIntrinsicHeight());
            }
        }
        this.mBounds.right = (int) Math.ceil(this.mSrcBounds.width() * this.mShowScale * iEditorContext.getImageRender().getImagePreviewMaxSizeScale());
        updateAnnotation(this.mAnnotation, true);
        this.mAnnotationY = (int) Math.ceil(this.mSrcBounds.height() * this.mShowScale * iEditorContext.getImageRender().getImagePreviewMaxSizeScale());
        this.mBounds.bottom = this.mAnnotationY + getAnnotationHeight();
        this.mSpanTop += ((int) iEditorContext.getView().getY()) - this.mEditorViewY;
        this.mEditorViewY = (int) iEditorContext.getView().getY();
        this.mBounds.offset(this.mSpanLeft, this.mSpanTop);
        if (isInRtl()) {
            int iWidth = this.mBounds.width();
            this.mBounds.right = iWidth;
            this.mBounds.left = this.mBounds.right - iWidth;
        }
        this.mSpanContentWidth = this.mBounds.width();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null) {
            return;
        }
        int paddingLeft = iEditorContext.getEditAreaView() != null ? iEditorContext.getEditAreaView().getPaddingLeft() : (int) f;
        int iHeight = i5 - this.mBounds.height();
        if (this.mVerticalAlignment == 1) {
            iHeight -= paint.getFontMetricsInt().descent + this.mMarginBottom;
        }
        this.mBoundsInParent.set(this.mBounds);
        this.mBoundsInParent.offsetTo(iEditorContext.getView().getPaddingLeft(), iHeight);
        int y = iHeight + ((int) iEditorContext.getView().getY());
        this.mSpanLeft = paddingLeft + iEditorContext.getView().getPaddingLeft();
        this.mEditorViewY = (int) iEditorContext.getView().getY();
        this.mSpanTop = y;
        this.mBounds.offsetTo(this.mSpanLeft, this.mSpanTop);
        if (isInRtl()) {
            int iWidth = this.mBounds.width();
            this.mBounds.right = iEditorContext.getContentWidth() + this.mSpanLeft;
            this.mBounds.left = this.mBounds.right - iWidth;
        }
        this.mAnnotationY = this.mBounds.bottom - getAnnotationHeight();
        Logger.INSTANCE.d(TAG, "SmartImageSpan.draw0: nBounds = " + this.mBounds.toString() + " top = " + i3 + " bottom = " + i5 + " y = " + i4 + " x = " + f);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void doDraw(Canvas canvas) {
        Drawable drawable = null;
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (this.mBounds.isEmpty() || iEditorContext == null) {
            return;
        }
        this.mSpanTop += ((int) iEditorContext.getView().getY()) - this.mEditorViewY;
        this.mEditorViewY = (int) iEditorContext.getView().getY();
        this.mBounds.offsetTo(this.mSpanLeft, this.mSpanTop);
        Logger.INSTANCE.d(TAG, "updateDrawParams h = " + this.mBounds.height() + " w = " + this.mBounds.width() + " top = " + this.mBounds.top);
        if (isInRtl()) {
            int iWidth = this.mBounds.width();
            this.mBounds.right = iEditorContext.getContentWidth() + this.mSpanLeft;
            this.mBounds.left = this.mBounds.right - iWidth;
        }
        Drawable drawable2 = this.mDrawable;
        if (this.mDrawBmRect == null) {
            this.mDrawBmRect = new Rect();
        }
        if (drawable2 != null && (drawable2 instanceof BitmapDrawable) && ((BitmapDrawable) drawable2).getBitmap().isRecycled()) {
            Logger.INSTANCE.e(TAG, "SmartImageSpan.draw: drawable is recycled");
            this.mDrawable = null;
        } else {
            drawable = drawable2;
        }
        if (drawable == null || this.mDrawable == null) {
            return;
        }
        if (isMissed()) {
            int iHeight = ((this.mBounds.height() - getAnnotationHeight()) / 4) * 4;
            int iWidth2 = (this.mBounds.width() / 4) * 4;
            this.mDrawBmRect.set(this.mBounds.left, this.mBounds.top, this.mBounds.left + iWidth2, this.mBounds.top + iHeight);
            this.mDrawable.setBounds(this.mDrawBmRect);
            this.mDrawable.setAlpha(this.mShowAlphaInt);
            if (this.mDrawable instanceof RoundedBitmapDrawable) {
                ((RoundedBitmapDrawable) this.mDrawable).setCornerRadius(NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius));
            }
            this.mDrawable.draw(canvas);
            this.mRoundPath.reset();
            this.mRoundRectF.set(this.mBounds.left, this.mBounds.top, this.mBounds.left + iWidth2, this.mBounds.top + iHeight);
            this.mRoundPath.addRoundRect(this.mRoundRectF, NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius), NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius), Path.Direction.CW);
            canvas.drawPath(this.mRoundPath, this.mImageUnSelectBorderPaint);
            canvas.save();
            canvas.restore();
            return;
        }
        if (this.mLastDrawBound.width() != this.mBounds.width()) {
            this.mLastDrawBound.set(this.mBounds);
        }
        int iHeight2 = ((this.mBounds.height() - getAnnotationHeight()) / 4) * 4;
        int iWidth3 = (this.mBounds.width() / 4) * 4;
        this.mDrawBmRect.set(this.mBounds.left, this.mBounds.top, this.mBounds.left + iWidth3, this.mBounds.top + iHeight2);
        this.mDrawable.setBounds(this.mDrawBmRect);
        this.mDrawable.setAlpha(this.mShowAlphaInt);
        if (this.mDrawable instanceof RoundedBitmapDrawable) {
            ((RoundedBitmapDrawable) this.mDrawable).setCornerRadius(NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius));
        }
        this.mDrawable.draw(canvas);
        this.mRoundPath.reset();
        this.mRoundRectF.set(this.mBounds.left, this.mBounds.top, this.mBounds.left + iWidth3, this.mBounds.top + iHeight2);
        this.mRoundPath.addRoundRect(this.mRoundRectF, NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius), NoteApp.getInstance().getResources().getDimension(R.dimen.note_edit_image_span_shape_radius), Path.Direction.CW);
        canvas.drawPath(this.mRoundPath, this.mSelected ? this.mImageSelectBorderPaint : this.mImageUnSelectBorderPaint);
        canvas.save();
        if (this.mHasAnnotation) {
            drawAnnotation(canvas, this.mAnnotation);
        }
        this.mAnnotationY = this.mBounds.bottom - getAnnotationHeight();
        canvas.restore();
    }

    public void copyImage(Bitmap bitmap) {
        if (bitmap != null) {
            this.mSrcBitmap = Bitmap.createBitmap(bitmap);
            this.mDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), Bitmap.createBitmap(this.mSrcBitmap));
            this.mDrawable.setAlpha(255);
        }
    }

    public Bitmap getOriginBitmap() {
        return this.mSrcBitmap;
    }

    void setImage(Bitmap bitmap) {
        Logger.INSTANCE.d(TAG, "smartImageSpanDebug setImage");
        if (bitmap != null) {
            float fWidth = (this.mSrcBounds.width() * 1.0f) / bitmap.getWidth();
            if (((int) (bitmap.getHeight() * fWidth)) > this.mSrcBounds.height()) {
                Rect rect = new Rect();
                rect.right = bitmap.getWidth();
                rect.bottom = (int) (this.mSrcBounds.height() / fWidth);
                this.mSrcBitmap = UIUtils.cropBitmap(bitmap, rect);
            } else {
                this.mSrcBitmap = bitmap;
            }
            Logger.INSTANCE.d(TAG, "smartImageSpanDebug setImage nOriginBitmap is recycled = " + this.mSrcBitmap.isRecycled());
            IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
            if (this.mSrcBitmap.isRecycled() || iEditorContext == null) {
                return;
            }
            updateScaledBitmapDrawable(this.mSrcBitmap, this.mShowScale);
            Rect visibleRect = iEditorContext.getVisibleRect();
            if ((this.mBounds.top < visibleRect.top || this.mBounds.top > visibleRect.bottom) && (this.mBounds.bottom > visibleRect.bottom || this.mBounds.bottom < visibleRect.top)) {
                return;
            }
            iEditorContext.refreshContainer();
        }
    }

    void updateScaledBitmapDrawable(Bitmap bitmap, float f) {
        Bitmap bitmapScaleBitmap;
        if (bitmap == null) {
            return;
        }
        int iWidth = (int) (this.mSrcBounds.width() * f);
        int iHeight = (int) (this.mSrcBounds.height() * f);
        if (iWidth == 0) {
            iWidth = 1;
        }
        if (iHeight == 0) {
            iHeight = 1;
        }
        if (this.mDrawable != null) {
            ((BitmapDrawable) this.mDrawable).getBitmap().recycle();
        }
        if (f == 1.0f) {
            bitmapScaleBitmap = Bitmap.createBitmap(bitmap);
        } else {
            bitmapScaleBitmap = UIUtils.scaleBitmap(bitmap, iWidth, iHeight, false);
        }
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            bitmapScaleBitmap.setDensity(iEditorContext.getViewDensityDpi());
        }
        this.mDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), Bitmap.createBitmap(this.mSrcBitmap));
        initSmoothDrawable();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public Drawable getDrawableForShadow() {
        if (this.mSrcBitmap == null) {
            return null;
        }
        int iWidth = (int) (this.mSrcBounds.width() * this.mShowScale);
        int iHeight = (int) (this.mSrcBounds.height() * this.mShowScale);
        if (iWidth == 0) {
            iWidth = 1;
        }
        if (iHeight == 0) {
            iHeight = 1;
        }
        Bitmap bitmapScaleBitmap = UIUtils.scaleBitmap(this.mSrcBitmap, iWidth, iHeight, false);
        IEditorContext iEditorContext = this.mIEditorContextWeakReference != null ? this.mIEditorContextWeakReference.get() : null;
        if (iEditorContext != null) {
            bitmapScaleBitmap.setDensity(iEditorContext.getViewDensityDpi());
        }
        return new BitmapDrawable(getContext().getResources(), bitmapScaleBitmap);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            i2 = (int) (i2 + iEditorContext.getView().getScrollY() + iEditorContext.getView().getY());
        }
        return i2 >= this.mBounds.top && i2 <= this.mBounds.bottom && this.mSpanLeft + i >= this.mBounds.left && i + this.mSpanLeft <= this.mBounds.right;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null) {
            return;
        }
        int[] iArr = {i, i2};
        if (i2 + iEditorContext.getView().getY() > this.mBounds.bottom - getAnnotationHeight()) {
            iEditorContext.onImageAnnotationClick(this, iArr);
        } else {
            iEditorContext.onImageSpanClick(this, iArr);
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onRightClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null) {
            return;
        }
        int[] iArr = {i, i2};
        if (i2 + iEditorContext.getView().getY() <= this.mBounds.bottom - getAnnotationHeight()) {
            iEditorContext.onImageSpanRightClick(this, iArr);
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null || !iEditorContext.getView().isEnabled()) {
            return;
        }
        super.onLongClick(i, i2);
        if (this.mSrcBitmap == null || this.mSpanLeft + i < this.mBounds.left || this.mSpanLeft + i > this.mBounds.right) {
            return;
        }
        iEditorContext.startDragSpanAnim(this, i + iEditorContext.getEditAreaView().getPaddingLeft(), i2);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public int getImageWidth() {
        return this.mBounds.width();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public int getImageHeight() {
        return this.mBounds.height() - getAnnotationHeight();
    }

    public int getAnnotationEditorY() {
        Logger.INSTANCE.d(TAG, "span.getAnnotationEditorY() nAnnotationY = " + this.mAnnotationY);
        return this.mAnnotationY;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void startDismissAnim() {
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.removeAllUpdateListeners();
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mShowScale, this.mShowScale * 0.0f);
        this.mTransitAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator());
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.NormalImageSpan.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                Logger.INSTANCE.d(NormalImageSpan.TAG, "updateAnim dismiss scale = " + fFloatValue);
                NormalImageSpan.this.mShowScale = fFloatValue;
                NormalImageSpan.this.setAlpha((int) ((1.0f - valueAnimator2.getAnimatedFraction()) * 255.0f));
                IEditorContext iEditorContext = NormalImageSpan.this.mIEditorContextWeakReference == null ? null : NormalImageSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.NormalImageSpan.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                NormalImageSpan.this.setAlpha(0);
                NormalImageSpan.this.deleteSelf();
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void startInsertAnim() {
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.removeAllUpdateListeners();
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mShowScale * 0.7f, this.mShowScale);
        this.mTransitAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.NormalImageSpan.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                Logger.INSTANCE.d(NormalImageSpan.TAG, "updateAnim insert scale = " + fFloatValue);
                NormalImageSpan.this.mShowScale = fFloatValue;
                NormalImageSpan.this.setAlpha((int) (valueAnimator2.getAnimatedFraction() * 255.0f));
                IEditorContext iEditorContext = NormalImageSpan.this.mIEditorContextWeakReference == null ? null : NormalImageSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.NormalImageSpan.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                IEditorContext iEditorContext = NormalImageSpan.this.mIEditorContextWeakReference == null ? null : NormalImageSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    NormalImageSpan normalImageSpan = NormalImageSpan.this;
                    boolean zIsShowBig = normalImageSpan.isShowBig();
                    IEditorImageRender imageRender = iEditorContext.getImageRender();
                    normalImageSpan.mShowScale = zIsShowBig ? imageRender.getShowBigScale() : imageRender.getShowSmallScale();
                }
                NormalImageSpan.this.setAlpha(255);
            }
        });
        valueAnimatorOfFloat.start();
    }

    public void startResizeAnim(final boolean z) {
        ValueAnimator valueAnimatorOfFloat;
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.removeAllUpdateListeners();
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        IEditorContext iEditorContext = this.mIEditorContextWeakReference != null ? this.mIEditorContextWeakReference.get() : null;
        if (z && iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowBigScale();
        } else if (iEditorContext != null) {
            this.mShowScale = iEditorContext.getImageRender().getShowSmallScale();
        }
        if (!z) {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        this.mTransitAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.NormalImageSpan.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NormalImageSpan.this.mShowScale = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                IEditorContext iEditorContext2 = NormalImageSpan.this.mIEditorContextWeakReference == null ? null : NormalImageSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext2 != null) {
                    iEditorContext2.getView().requestLayout();
                    iEditorContext2.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.NormalImageSpan.7
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (z) {
                    NormalImageSpan.this.mShowScale = 1.0f;
                } else {
                    NormalImageSpan.this.mShowScale = 0.5f;
                }
                IEditorContext iEditorContext2 = NormalImageSpan.this.mIEditorContextWeakReference == null ? null : NormalImageSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext2 != null) {
                    iEditorContext2.getView().requestLayout();
                    iEditorContext2.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.snippet_image_stub) + this.mAnnotation;
    }
}
