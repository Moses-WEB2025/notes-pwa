package com.miui.richeditor.style;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Size;
import android.view.MotionEvent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.util.EditImageUtils;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class BaseImageSpan extends ImageSpan implements HtmlParser.IElementSpan, ClickableElement {

    @Deprecated
    private static final float MISSING_PIC_ASPECT_RATION = 1.6f;

    @Deprecated
    private static final float MISSING_VIDEO_ASPECT_RATION = 5.3f;
    private static final String TAG = "BaseImageSpan";
    protected StaticLayout mAnnotateLayout;
    protected String mAnnotation;
    protected int mAnnotationHeight;
    protected int mAnnotationLineHeight;
    protected int mAnnotationLineSpaceAdd;
    protected int mAnnotationMarginBottom;
    protected int mAnnotationMarginTop;
    protected int mAnnotationMaxLine;
    protected int mAnnotationY;
    protected final Rect mBounds;
    protected final Rect mBoundsInParent;
    protected Drawable mDrawable;
    protected int mEditorViewY;
    protected final HtmlParser.GeneralElement mElement;
    protected boolean mHasAnnotation;
    protected WeakReference<IEditorContext> mIEditorContextWeakReference;
    protected Paint mImageSelectBorderPaint;
    protected Paint mImageUnSelectBorderPaint;
    protected boolean mIsEditAnno;
    protected int mMarginBottom;
    protected int mMarginTop;
    protected boolean mMissed;
    protected int mShowAlphaInt;
    protected boolean mShowBig;
    protected float mShowScale;
    protected int mSpanContentHeight;
    protected int mSpanContentWidth;
    public int mSpanLeft;
    protected int mSpanTop;
    protected Bitmap mSrcBitmap;
    protected Point mSrcBmSize;
    protected Rect mSrcBounds;
    protected String mSrcFileId;
    protected int mSrcHeight;
    protected int mSrcWidth;
    protected TextPaint mTextPaint;
    protected int nScale;

    public Drawable getDrawableForShadow() {
        return null;
    }

    protected void initMargin() {
    }

    public void onClick(int i, int i2) {
    }

    public void onDelete() {
    }

    public void onDestroy() {
    }

    public void onLongClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onRightClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onSelected(boolean z) {
    }

    public void onTouchPoint(int i, int i2, MotionEvent motionEvent) {
    }

    protected void prepareDrawable() {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setPressed(boolean z) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setSelected(boolean z) {
    }

    public void startDismissAnim() {
    }

    public void startInsertAnim() {
    }

    public BaseImageSpan(IEditorContext iEditorContext, Bitmap bitmap, HtmlParser.GeneralElement generalElement) {
        super(NoteApp.getInstance(), bitmap, 1);
        this.mIEditorContextWeakReference = null;
        this.mEditorViewY = 0;
        this.mSpanLeft = 0;
        this.mSpanTop = 0;
        this.mImageSelectBorderPaint = new Paint();
        this.mImageUnSelectBorderPaint = new Paint();
        Rect rect = new Rect();
        this.mBounds = rect;
        Rect rect2 = new Rect();
        this.mBoundsInParent = rect2;
        this.mSrcBounds = new Rect();
        this.mShowScale = 1.0f;
        this.mShowAlphaInt = 255;
        this.mShowBig = true;
        this.mSpanContentWidth = 0;
        this.mSpanContentHeight = 0;
        this.mSrcBmSize = new Point();
        this.mSrcBitmap = null;
        this.mMarginTop = 0;
        this.mMarginBottom = 0;
        this.mTextPaint = null;
        this.mIsEditAnno = false;
        this.mAnnotateLayout = null;
        this.mAnnotationHeight = 80;
        this.mHasAnnotation = false;
        this.mAnnotationMaxLine = 3;
        this.mAnnotationMarginTop = 0;
        this.mAnnotationMarginBottom = 0;
        this.mAnnotationLineHeight = 0;
        this.mAnnotationLineSpaceAdd = 0;
        this.mMissed = false;
        if (iEditorContext != null) {
            this.mIEditorContextWeakReference = new WeakReference<>(iEditorContext);
        }
        this.mElement = generalElement;
        rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rect2.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        initMargin();
        initImageBorderPaint();
        initAnnotationParams();
    }

    public BaseImageSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement) {
        super(NoteApp.getInstance(), 0, 1);
        this.mIEditorContextWeakReference = null;
        this.mEditorViewY = 0;
        this.mSpanLeft = 0;
        this.mSpanTop = 0;
        this.mImageSelectBorderPaint = new Paint();
        this.mImageUnSelectBorderPaint = new Paint();
        this.mBounds = new Rect();
        this.mBoundsInParent = new Rect();
        this.mSrcBounds = new Rect();
        this.mShowScale = 1.0f;
        this.mShowAlphaInt = 255;
        this.mShowBig = true;
        this.mSpanContentWidth = 0;
        this.mSpanContentHeight = 0;
        this.mSrcBmSize = new Point();
        this.mSrcBitmap = null;
        this.mMarginTop = 0;
        this.mMarginBottom = 0;
        this.mTextPaint = null;
        this.mIsEditAnno = false;
        this.mAnnotateLayout = null;
        this.mAnnotationHeight = 80;
        this.mHasAnnotation = false;
        this.mAnnotationMaxLine = 3;
        this.mAnnotationMarginTop = 0;
        this.mAnnotationMarginBottom = 0;
        this.mAnnotationLineHeight = 0;
        this.mAnnotationLineSpaceAdd = 0;
        this.mMissed = true;
        if (iEditorContext != null) {
            this.mIEditorContextWeakReference = new WeakReference<>(iEditorContext);
        }
        this.mElement = generalElement;
        initMargin();
        initImageBorderPaint();
        initAnnotationParams();
    }

    public Context getContext() {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        return iEditorContext == null ? NoteApp.getInstance() : iEditorContext.getContext();
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        updateDrawParams();
        return this.mBounds.width();
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        this.mBoundsInParent.set(this.mBounds);
        if (iEditorContext != null) {
            this.mBoundsInParent.offset(iEditorContext.getView().getPaddingLeft(), i5 - this.mBoundsInParent.height());
            this.mBounds.left = ((int) f) + iEditorContext.getView().getCompoundPaddingLeft();
            this.mEditorViewY = (int) iEditorContext.getView().getY();
            this.mBounds.bottom = iEditorContext.getView().getCompoundPaddingTop() + i5 + this.mEditorViewY;
        }
        if (getDrawable() != null) {
            super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
        }
    }

    public void doDraw(Canvas canvas) {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        Drawable drawable = null;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (this.mBounds.isEmpty() || iEditorContext == null) {
            return;
        }
        this.mSpanTop += ((int) iEditorContext.getView().getY()) - this.mEditorViewY;
        this.mEditorViewY = (int) iEditorContext.getView().getY();
        this.mBounds.offsetTo(this.mSpanLeft, this.mSpanTop);
        if (isInRtl()) {
            int iWidth = this.mBounds.width();
            this.mBounds.right = iEditorContext.getContentWidth() + this.mSpanLeft;
            Rect rect = this.mBounds;
            rect.left = rect.right - iWidth;
        }
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && (drawable2 instanceof BitmapDrawable) && ((BitmapDrawable) drawable2).getBitmap().isRecycled()) {
            this.mDrawable = null;
        } else {
            drawable = drawable2;
        }
        if (drawable != null) {
            canvas.save();
            drawable.setBounds(this.mBounds);
            drawable.setAlpha(this.mShowAlphaInt);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    public boolean isTouchIn(int i, int i2) {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            i2 = (int) (i2 + iEditorContext.getView().getScrollY() + iEditorContext.getView().getY());
        }
        return i2 >= this.mBounds.top && i2 <= this.mBounds.bottom && i >= this.mBounds.left && i <= this.mBounds.right;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.GeneralElement getElement() {
        return this.mElement;
    }

    public int getImageWidth() {
        return this.mBounds.width();
    }

    public int getImageHeight() {
        return this.mBounds.height();
    }

    public boolean isMissed() {
        return this.mMissed;
    }

    public String getSrcFileId() {
        return this.mSrcFileId;
    }

    public void setAlpha(int i) {
        this.mShowAlphaInt = i;
    }

    protected boolean isInRtl() {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            return iEditorContext.getView().getLayoutDirection() == 1;
        }
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public Rect getBoundsInParent() {
        return this.mBoundsInParent;
    }

    @Override // android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.snippet_image_stub);
    }

    public void clearDrawable() {
        this.mDrawable = null;
    }

    protected void initImageBorderPaint() {
        this.mImageSelectBorderPaint.setAntiAlias(true);
        this.mImageSelectBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.smartimagespan_border_color_s));
        this.mImageSelectBorderPaint.setStyle(Paint.Style.STROKE);
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            this.mImageSelectBorderPaint.setStrokeWidth(iEditorContext.getImageRender().getImageSelectBorderWidth());
        }
    }

    protected void initAnnotationParams() {
        this.mAnnotation = "";
        this.mAnnotationMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.image_annotation_content_margin_top);
        this.mAnnotationLineHeight = getContext().getResources().getDimensionPixelSize(R.dimen.image_annotation_line_height);
    }

    protected void updateDrawParams() {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext == null) {
            return;
        }
        this.mBounds.set(0, 0, 0, 0);
        this.mSrcBounds.set(0, 0, 0, 0);
        this.mBounds.right = (int) (this.mSrcBounds.width() * this.mShowScale);
        this.mBounds.bottom = (int) (this.mSrcBounds.height() * this.mShowScale);
        this.mAnnotationY = this.mBounds.bottom;
        Point point = new Point();
        Size size = new Size(this.mSrcWidth, this.mSrcHeight);
        iEditorContext.getImageRender().measureBitmapSize(getContext(), size, iEditorContext.getRootContainer().getWidth(), this.mSrcBmSize);
        this.nScale = iEditorContext.getImageRender().measureBitmapSize(getContext(), size, iEditorContext.getContentWidth(), point);
        this.mSrcBounds.right = Math.max(point.x, iEditorContext.getImageRender().getImageCoverDrawable().getIntrinsicWidth());
        if (isMissed()) {
            computeMissedBounds();
        } else {
            this.mSrcBounds.bottom = Math.max(point.y, iEditorContext.getImageRender().getImageCoverDrawable().getIntrinsicHeight());
        }
        this.mBounds.right = (int) (this.mSrcBounds.width() * this.mShowScale);
        if (TextUtils.isEmpty(this.mAnnotation)) {
            this.mBounds.bottom = (int) (this.mSrcBounds.height() * this.mShowScale);
            this.mAnnotationY = this.mBounds.bottom;
        } else {
            updateAnnotation(this.mAnnotation, true);
            int iCeil = (int) Math.ceil(this.mSrcBounds.height() * this.mShowScale * iEditorContext.getImageRender().getImagePreviewMaxSizeScale());
            this.mAnnotationY = iCeil;
            this.mBounds.bottom = iCeil + getAnnotationHeight();
        }
        this.mBounds.offset(this.mSpanLeft, this.mSpanTop + ((int) iEditorContext.getView().getY()));
        if (isInRtl()) {
            int iWidth = this.mBounds.width();
            this.mBounds.right = iWidth;
            Rect rect = this.mBounds;
            rect.left = rect.right - iWidth;
        }
        this.mSpanContentWidth = iEditorContext.getContentWidth();
    }

    protected void computeMissedBounds() {
        if (this.mElement instanceof HtmlParser.VideoElement) {
            Rect rect = this.mSrcBounds;
            rect.bottom = (int) (rect.right / MISSING_VIDEO_ASPECT_RATION);
        } else {
            Rect rect2 = this.mSrcBounds;
            rect2.bottom = (int) (rect2.right / 1.6f);
        }
    }

    protected void initMissedDrawableSize() {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            this.mSrcHeight = iEditorContext.getImageRender().getDefaultImageDrawable().getIntrinsicHeight();
            this.mSrcWidth = iEditorContext.getImageRender().getDefaultImageDrawable().getIntrinsicWidth();
        }
    }

    protected void prepareMissedDrawable() {
        this.mDrawable = EditImageUtils.getBitmapDrawable(getContext(), Math.max(this.mBounds.width(), 2), Math.max(this.mBounds.height(), 2), R.drawable.picture_missing);
    }

    public void prepare() {
        updateDrawParams();
        Drawable drawable = this.mDrawable;
        if (drawable != null && (drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap().isRecycled()) {
            this.mDrawable = null;
        }
        if (this.mDrawable == null) {
            WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
            IEditorContext iEditorContext = weakReference != null ? weakReference.get() : null;
            if (isMissed()) {
                prepareMissedDrawable();
            } else {
                prepareDrawable();
            }
            if (iEditorContext != null) {
                iEditorContext.refreshContainer();
                return;
            }
            return;
        }
        prepareDrawable();
    }

    public void recycle() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            if (drawable instanceof RoundedBitmapDrawable) {
                ((RoundedBitmapDrawable) drawable).getBitmap().recycle();
            } else {
                ((BitmapDrawable) drawable).getBitmap().recycle();
            }
            this.mDrawable = null;
        }
    }

    public boolean isShowBig() {
        return this.mShowBig;
    }

    public void setAnnotation(String str) {
        boolean z = this.mHasAnnotation;
        if (TextUtils.isEmpty(str)) {
            this.mHasAnnotation = false;
            if (getElement() instanceof HtmlParser.ImageElement) {
                ((HtmlParser.ImageElement) getElement()).setAttribute(HtmlParser.ImageElement.KEY_DES, "");
            }
        } else {
            this.mHasAnnotation = true;
            if (getElement() instanceof HtmlParser.ImageElement) {
                ((HtmlParser.ImageElement) getElement()).setAttribute(HtmlParser.ImageElement.KEY_DES, str);
            }
        }
        updateAnnotation(str, false);
        if (z != this.mHasAnnotation) {
            this.mSpanContentWidth = 0;
            this.mBounds.set(0, 0, 0, 0);
            this.mBoundsInParent.set(0, 0, 0, 0);
            prepare();
        }
    }

    public String getAnnotation() {
        return this.mAnnotation;
    }

    protected void drawAnnotation(Canvas canvas, String str) {
        canvas.save();
        canvas.translate(this.mBounds.left, (this.mBounds.bottom - getAnnotationHeight()) + this.mAnnotationMarginTop);
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            this.mAnnotateLayout.getPaint().setColor(iEditorContext.getImageRender().getAnnotationColor());
        }
        this.mAnnotateLayout.getPaint().setAlpha((int) (this.mShowAlphaInt * 0.4f));
        this.mAnnotateLayout.draw(canvas);
        canvas.restore();
    }

    public int getAnnotationHeight() {
        if (this.mHasAnnotation || this.mIsEditAnno) {
            return this.mAnnotationHeight;
        }
        return 0;
    }

    protected void updateAnnotation(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String str2 = str;
        if (!this.mAnnotation.equals(str2) || z) {
            if (!this.mAnnotation.equals(str2)) {
                this.mAnnotation = str2;
            }
            StaticLayout staticLayoutCreateStaticLayout = createStaticLayout(str2, 0, str2.length(), this.mTextPaint, Math.max(0, this.mBounds.width()), TextUtils.TruncateAt.END, this.mAnnotationMaxLine, this.mAnnotationLineSpaceAdd);
            this.mAnnotateLayout = staticLayoutCreateStaticLayout;
            int height = staticLayoutCreateStaticLayout.getHeight() + this.mAnnotationMarginTop + this.mAnnotationMarginBottom;
            this.mBounds.bottom += height - this.mAnnotationHeight;
            this.mAnnotationHeight = height;
        }
    }

    protected void initAnnotationTextPaint() {
        if (this.mTextPaint == null) {
            TextPaint textPaint = new TextPaint();
            this.mTextPaint = textPaint;
            textPaint.setAntiAlias(true);
            this.mTextPaint.setStyle(Paint.Style.FILL);
            this.mTextPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.image_annotation_text_size));
            this.mAnnotationLineSpaceAdd = Math.max(this.mAnnotationLineHeight - ((int) (this.mTextPaint.descent() - this.mTextPaint.ascent())), 0);
        }
    }

    protected void deleteSelf() {
        WeakReference<IEditorContext> weakReference = this.mIEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            iEditorContext.deleteImageSpanAfterDragDrop(this);
        }
    }

    protected StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4, float f) {
        return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, Math.max(i3, 0)).setMaxLines(i4).setEllipsize(truncateAt).setLineSpacing(f, 1.0f).setAlignment(isInRtl() ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL).build();
    }
}
