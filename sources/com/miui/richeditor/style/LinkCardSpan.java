package com.miui.richeditor.style;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardSpan extends SelectableImageSpan {
    private static final String TAG = "LinkCardSpan";
    private String mContent;
    private String mLink;
    private StaticLayout mLinkTextLayout;
    private Drawable mSpanIcon;
    private String mTitle;
    private StaticLayout mTitleTextLayout;
    private ValueAnimator mTransitAnimator;
    private Drawable mWebIcon;

    private void drawWebContent(Canvas canvas, float f, int i) {
    }

    private void drawWebIcon(Canvas canvas, float f, int i) {
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void doDraw(Canvas canvas) {
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void prepare() {
    }

    public LinkCardSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement) {
        this(iEditorContext, generalElement, iEditorContext.getSelectableSpanListener());
    }

    public LinkCardSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement, SelectableSpanListener selectableSpanListener) {
        super(iEditorContext, generalElement, selectableSpanListener);
        this.mTitle = "";
        this.mContent = "";
        this.mLink = "";
        this.mTransitAnimator = null;
        this.mTitleTextLayout = null;
        this.mLinkTextLayout = null;
        init();
    }

    private void init() {
        HtmlParser.LinkCardElement linkCardElement = (HtmlParser.LinkCardElement) getElement();
        this.mLink = linkCardElement.getLink();
        this.mTitle = linkCardElement.getTitle();
        this.mSpanContentHeight = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_height);
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            this.mSpanContentWidth = iEditorContext.getContentWidth();
        }
        this.mBounds.set(0, 0, this.mSpanContentWidth, this.mSpanContentHeight);
        updateTextContent();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void initMargin() {
        this.mMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_margin_top);
        this.mMarginBottom = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_margin_bottom);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void updateDrawParams() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null && iEditorContext.getContentWidth() != this.mSpanContentWidth) {
            this.mBounds.set(0, 0, 0, 0);
            this.mSpanContentWidth = iEditorContext.getContentWidth();
            this.mBounds.set(0, 0, this.mSpanContentWidth, this.mSpanContentHeight);
            updateTextContent();
        }
        this.mBoundsInParent.set(this.mBounds);
        this.mBoundsInParent.right = (int) (this.mBoundsInParent.right * this.mShowScale);
        this.mBoundsInParent.bottom = (int) (this.mBoundsInParent.bottom * this.mShowScale);
    }

    private void updateTextContent() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null || iEditorContext.getLinkCardSpanHelper() == null || this.mLink == null) {
            return;
        }
        String str = this.mTitle;
        this.mTitleTextLayout = createStaticLayout(str, 0, str.length(), iEditorContext.getLinkCardSpanHelper().getTitlePaint(), Math.max(0, iEditorContext.getLinkCardSpanHelper().getTextContentWidth(this.mBounds)), TextUtils.TruncateAt.END, 1, 10.0f);
        String str2 = this.mLink;
        this.mLinkTextLayout = createStaticLayout(str2, 0, str2.length(), iEditorContext.getLinkCardSpanHelper().getLinkPaint(), Math.max(0, iEditorContext.getLinkCardSpanHelper().getTextContentWidth(this.mBounds)), TextUtils.TruncateAt.END, 1, 10.0f);
    }

    public String getLink() {
        return this.mLink;
    }

    public void setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mTitle = str;
        } else {
            this.mTitle = "";
        }
        ((HtmlParser.LinkCardElement) getElement()).setTitle(this.mTitle);
        updateTextContent();
    }

    public String getTitle() {
        return this.mTitle;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        return i2 >= this.mBoundsInParent.top && i2 <= this.mBoundsInParent.bottom;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (i > this.mBoundsInParent.right || i < this.mBoundsInParent.left || iEditorContext == null) {
            return;
        }
        iEditorContext.onLinkCardClick(this, new int[]{i, i2});
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (i > this.mBoundsInParent.right || i < this.mBoundsInParent.left || iEditorContext == null) {
            return;
        }
        iEditorContext.startDragLinkCardAnim(this, new int[]{i, i2});
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        updateDrawParams();
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = (-this.mBoundsInParent.height()) - this.mMarginTop;
            fontMetricsInt.descent = this.mMarginBottom;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.descent;
        }
        return this.mBounds.width();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        try {
            IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
            if (iEditorContext == null) {
                Logger.INSTANCE.e(TAG, "LinkCardSpan draw error: iEditorContext is null");
                return;
            }
            int paddingLeft = ((int) f) + iEditorContext.getView().getPaddingLeft();
            this.mSpanLeft = paddingLeft;
            this.mEditorViewY = (int) iEditorContext.getView().getY();
            this.mSpanTop = i3 + this.mMarginTop;
            this.mBoundsInParent.set(this.mBounds);
            if (isInRtl()) {
                this.mBounds.offsetTo(0, this.mSpanTop);
                this.mBoundsInParent.offsetTo(0, this.mSpanTop);
            } else {
                this.mBounds.offsetTo(paddingLeft, this.mSpanTop);
                this.mBoundsInParent.offsetTo(paddingLeft, this.mSpanTop);
            }
            float f2 = this.mShowScale;
            int i6 = this.mShowAlphaInt;
            if (iEditorContext.getLinkCardSpanHelper() != null) {
                drawBgRect(canvas, f2, i6);
                drawSpanIcon(canvas, f2, i6);
                drawWebTitle(canvas, f2, i6);
                drawWebIcon(canvas, f2, i6);
                drawWebLink(canvas, f2, i6);
            }
            Logger.INSTANCE.d(TAG, "draw mBound " + this.mBounds.toString());
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "LinkCardSpan draw error:" + e.getMessage());
        }
    }

    private void drawBgRect(Canvas canvas, float f, int i) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.getLinkCardSpanHelper().drawBg(this.mBoundsInParent, canvas, this.mSelected, f, i);
        }
    }

    private void drawSpanIcon(Canvas canvas, float f, int i) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.getLinkCardSpanHelper().drawSpanIcon(this.mBoundsInParent, canvas, f, i);
        }
    }

    private void drawWebTitle(Canvas canvas, float f, int i) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.getLinkCardSpanHelper().drawWebTitle(this.mTitleTextLayout, this.mBoundsInParent, canvas, f, i);
        }
    }

    private void drawWebLink(Canvas canvas, float f, int i) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.getLinkCardSpanHelper().drawWebLink(this.mLinkTextLayout, this.mBoundsInParent, canvas, f, i);
        }
    }

    public HtmlParser.DisplayElement getDisplayElement() {
        return (HtmlParser.DisplayElement) getElement();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public Drawable getDrawableForShadow() {
        Rect rect = new Rect(0, 0, this.mBounds.width(), this.mBounds.height());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.getLinkCardSpanHelper().drawBg(rect, canvas, false, 1.0f, 255);
            iEditorContext.getLinkCardSpanHelper().drawSpanIcon(rect, canvas, 1.0f, 255);
            iEditorContext.getLinkCardSpanHelper().drawWebTitle(this.mTitleTextLayout, rect, canvas, 1.0f, 255);
            iEditorContext.getLinkCardSpanHelper().drawWebLink(this.mLinkTextLayout, rect, canvas, 1.0f, 255);
        }
        return new BitmapDrawable(getContext().getResources(), bitmapCreateBitmap);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void startInsertAnim() {
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mShowScale * 0.7f, this.mShowScale);
        this.mTransitAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.LinkCardSpan.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                Logger.INSTANCE.i(LinkCardSpan.TAG, "updateAnim insert scale = " + fFloatValue);
                LinkCardSpan.this.mShowScale = fFloatValue;
                LinkCardSpan.this.setAlpha((int) (valueAnimator2.getAnimatedFraction() * 255.0f));
                IEditorContext iEditorContext = LinkCardSpan.this.mIEditorContextWeakReference == null ? null : LinkCardSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.LinkCardSpan.2
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
                LinkCardSpan.this.mShowScale = 1.0f;
                LinkCardSpan.this.setAlpha(255);
                IEditorContext iEditorContext = LinkCardSpan.this.mIEditorContextWeakReference == null ? null : LinkCardSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void startDismissAnim() {
        ValueAnimator valueAnimator = this.mTransitAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTransitAnimator.cancel();
            this.mTransitAnimator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mShowScale, this.mShowScale * 0.0f);
        this.mTransitAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator());
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.LinkCardSpan.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                Logger.INSTANCE.i(LinkCardSpan.TAG, "updateAnim dismiss scale = " + fFloatValue);
                LinkCardSpan.this.mShowScale = fFloatValue;
                LinkCardSpan.this.setAlpha((int) ((1.0f - valueAnimator2.getAnimatedFraction()) * 255.0f));
                IEditorContext iEditorContext = LinkCardSpan.this.mIEditorContextWeakReference == null ? null : LinkCardSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    iEditorContext.refreshContainer();
                }
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.LinkCardSpan.4
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
                LinkCardSpan.this.deleteSelf();
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public Rect getBounds() {
        return super.getBounds();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getTitle();
    }
}
