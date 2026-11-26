package com.miui.richeditor.style;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.R;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.audio.BubbleDrawable;
import com.miui.richeditor.style.audio.NormalAudioSpanDrawable;
import com.miui.richeditor.style.audio.WaveBubbleDrawable;
import com.miui.richeditor.style.render.IEditorImageRender;
import java.io.File;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class BaseAudioSpan extends SelectableImageSpan {
    public static final String AUDIO_DEFAULT_FILE_PATH = "temp_audio_file";
    protected boolean isIncreaseAnimRunning;
    protected boolean isTouchIn;
    protected boolean isTouchLeft;
    protected WaveBubbleDrawable mAudioBubbleDrawable;
    protected boolean mIsPlayAnimRunning;
    protected ValueAnimator mTransitAnimator;
    protected int recordTime;

    @Override // com.miui.richeditor.style.BaseImageSpan
    public Drawable getDrawableForShadow() {
        return null;
    }

    public void pauseAnim() {
    }

    public void pauseAudioAnimAndRemoveListener() {
    }

    public BaseAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement) {
        this(iAudioEditorContext, displayElement, iAudioEditorContext.getSelectableSpanListener());
    }

    public BaseAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement, SelectableSpanListener selectableSpanListener) {
        super(iAudioEditorContext, displayElement, selectableSpanListener);
        this.mTransitAnimator = null;
        this.mMissed = false;
        this.mSrcFileId = displayElement.getFileId();
        init();
    }

    protected void init() {
        this.mAudioBubbleDrawable = new NormalAudioSpanDrawable(0, 1);
        this.mBounds.set(0, 0, this.mAudioBubbleDrawable.getIntrinsicWidth(), this.mAudioBubbleDrawable.getIntrinsicHeight());
    }

    protected WaveBubbleDrawable createAudioDrawable(int i, int i2) {
        return new NormalAudioSpanDrawable(i, i2);
    }

    public void updateElement(String str) {
        this.isIncreaseAnimRunning = true;
        if (this.mElement instanceof HtmlParser.DisplayElement) {
            ((HtmlParser.DisplayElement) this.mElement).setAttribute(str);
        }
        if (getEditorContext() != null) {
            this.recordTime = AudioUtils.getFileTime(str, getEditorContext().getAudioFileDir());
        }
        this.mAudioBubbleDrawable.startIncreaseAnim(this.recordTime, new BaseAudioPlayListener() { // from class: com.miui.richeditor.style.BaseAudioSpan.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BaseAudioSpan.this.mDrawable = null;
                IEditorContext iEditorContext = BaseAudioSpan.this.mIEditorContextWeakReference != null ? BaseAudioSpan.this.mIEditorContextWeakReference.get() : null;
                if (iEditorContext != null) {
                    iEditorContext.refreshAllImageSpans();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                BaseAudioSpan.this.isIncreaseAnimRunning = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                BaseAudioSpan.this.isIncreaseAnimRunning = false;
            }
        });
    }

    protected void initDrawableSize() {
        if (this.mDrawable == null) {
            this.mSrcHeight = 0;
            this.mSrcWidth = 0;
            if (!isAnimRunning()) {
                if ((this.mElement instanceof HtmlParser.DisplayElement) && getEditorContext() != null) {
                    this.recordTime = AudioUtils.getFileTime(((HtmlParser.DisplayElement) this.mElement).getFileId(), getEditorContext().getAudioFileDir());
                }
                int i = this.recordTime;
                this.mAudioBubbleDrawable = new NormalAudioSpanDrawable(i, i <= 0 ? 2 : 0);
                this.mDrawable = BubbleDrawable.getBitmapDrawable(getContext(), this.mAudioBubbleDrawable);
            }
        }
        if (this.mDrawable != null) {
            this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
            this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        updateDrawParams();
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = (-this.mBounds.height()) - this.mMarginTop;
            fontMetricsInt.descent = this.mMarginBottom;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.descent;
        }
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            return iEditorContext.getContentWidth();
        }
        return 0;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        int paddingLeft = (iEditorContext == null || iEditorContext.getEditAreaView() == null) ? (int) f : iEditorContext.getEditAreaView().getPaddingLeft();
        if (iEditorContext != null) {
            this.mEditorViewY = (int) iEditorContext.getView().getY();
        }
        int iHeight = (i5 - this.mBounds.height()) + this.mEditorViewY;
        if (this.mVerticalAlignment == 1) {
            iHeight -= paint.getFontMetricsInt().descent + this.mMarginBottom;
        }
        if (iEditorContext != null) {
            this.mSpanLeft = paddingLeft + iEditorContext.getView().getPaddingLeft();
        }
        this.mSpanTop = iHeight;
        this.mBoundsInParent.set(this.mBounds);
        if (iEditorContext != null) {
            this.mBoundsInParent.offsetTo(iEditorContext.getView().getPaddingLeft(), this.mBounds.top);
        }
        this.mBounds.offsetTo(this.mSpanLeft, this.mSpanTop);
        if (!isInRtl() || iEditorContext == null) {
            return;
        }
        int iWidth = this.mBounds.width();
        this.mBounds.right = iEditorContext.getContentWidth() + this.mSpanLeft;
        this.mBounds.left = this.mBounds.right - iWidth;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void updateDrawParams() {
        ValueAnimator valueAnimator;
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        int contentWidth = iEditorContext != null ? iEditorContext.getContentWidth() : 0;
        if (this.mDrawable == null || this.mSpanContentWidth == 0 || this.mSpanContentWidth != contentWidth || ((valueAnimator = this.mTransitAnimator) != null && valueAnimator.isRunning())) {
            this.mSpanContentWidth = contentWidth;
            if (this.mDrawable != null) {
                this.mBounds.set(0, 0, 0, 0);
                this.mBounds.right = (int) (this.mDrawable.getIntrinsicWidth() * this.mShowScale);
                this.mBounds.bottom = (int) (this.mDrawable.getIntrinsicHeight() * this.mShowScale);
            } else {
                this.mBounds.set(0, 0, 0, 0);
                this.mBounds.right = (int) (this.mAudioBubbleDrawable.getIntrinsicWidth() * this.mShowScale);
                this.mBounds.bottom = (int) (this.mAudioBubbleDrawable.getIntrinsicHeight() * this.mShowScale);
            }
        }
    }

    public void reset() {
        this.mDrawable = null;
        this.mSpanContentWidth = 0;
    }

    public boolean isAnimRunning() {
        return this.isIncreaseAnimRunning || this.mIsPlayAnimRunning;
    }

    public void cancelPlayAnimIfNeed() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (getAudioDrawable() == null || iEditorContext == null) {
            return;
        }
        iEditorContext.refreshAllImageSpans();
        getAudioDrawable().stopAnim();
    }

    protected int getDrawType() {
        if (this.recordTime <= 0) {
            return (getEditorContext() == null || !getEditorContext().isRecording()) ? 2 : 1;
        }
        return 0;
    }

    public WaveBubbleDrawable getAudioDrawable() {
        return this.mAudioBubbleDrawable;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void prepareDrawable() {
        if (this.mDrawable == null) {
            if (!isAnimRunning()) {
                if ((this.mElement instanceof HtmlParser.DisplayElement) && getEditorContext() != null) {
                    this.recordTime = AudioUtils.getFileTime(((HtmlParser.DisplayElement) this.mElement).getFileId(), getEditorContext().getAudioFileDir());
                }
                int i = this.recordTime;
                int id = 0;
                WaveBubbleDrawable waveBubbleDrawableCreateAudioDrawable = createAudioDrawable(i, i > 0 ? 0 : 2);
                this.mAudioBubbleDrawable = waveBubbleDrawableCreateAudioDrawable;
                if (getEditorContext() != null && getEditorContext().getEditorTheme() != null) {
                    id = getEditorContext().getEditorTheme().getId();
                }
                waveBubbleDrawableCreateAudioDrawable.setStyle(id);
            }
            this.mDrawable = BubbleDrawable.getBitmapDrawable(getContext(), this.mAudioBubbleDrawable);
        }
        this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
        this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void prepareMissedDrawable() {
        prepareDrawable();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    protected void computeMissedBounds() {
        IAudioEditorContext editorContext = getEditorContext();
        int contentWidth = editorContext != null ? (editorContext.getContentWidth() - editorContext.getView().getPaddingEnd()) - editorContext.getView().getPaddingStart() : 0;
        if (this.mDrawable == null || this.mSpanContentWidth == 0 || this.mSpanContentWidth != contentWidth) {
            reset();
            initDrawableSize();
            this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
            this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
        }
        this.mSrcBounds.right = this.mSrcWidth;
        this.mSrcBounds.bottom = this.mSrcHeight;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        IAudioEditorContext editorContext = getEditorContext();
        if (!this.isTouchIn || editorContext == null) {
            return;
        }
        if (!this.isTouchLeft) {
            if (editorContext.getPlayingAudioSpan() == this) {
                editorContext.stopMediaPlayerIfNeed();
            }
            editorContext.deleteImageSpan(this, false);
            return;
        }
        if (editorContext.getMediaPlayer() != null && editorContext.getMediaPlayer().isPlaying()) {
            editorContext.getMediaPlayer().stop();
            editorContext.getMediaPlayer().release();
            editorContext.clearMediaPlayer();
            if (editorContext.getPlayingAudioSpan() == this) {
                editorContext.cancelPlayAnimIfNeed();
                return;
            }
            return;
        }
        String fileId = ((HtmlParser.DisplayElement) this.mElement).getFileId();
        if (fileId == null || "temp_audio_file".equals(fileId)) {
            editorContext.onPlayAudioError(0);
            return;
        }
        if (!AudioUtils.isTodoMp3FileExist(fileId)) {
            editorContext.onPlayAudioError(1);
            return;
        }
        editorContext.startMediaPlayer(this, new File(AudioUtils.obtainMp3FileDir(editorContext.getAudioFileDir()), fileId).getAbsolutePath());
        WaveBubbleDrawable waveBubbleDrawable = this.mAudioBubbleDrawable;
        if (waveBubbleDrawable != null) {
            this.mIsPlayAnimRunning = true;
            waveBubbleDrawable.startPlayAnim(new BaseAudioPlayListener() { // from class: com.miui.richeditor.style.BaseAudioSpan.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    BaseAudioSpan.this.mDrawable = null;
                    IEditorContext iEditorContext = BaseAudioSpan.this.mIEditorContextWeakReference != null ? BaseAudioSpan.this.mIEditorContextWeakReference.get() : null;
                    if (iEditorContext != null) {
                        iEditorContext.refreshAllImageSpans();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    BaseAudioSpan.this.mDrawable = null;
                    BaseAudioSpan.this.mIsPlayAnimRunning = false;
                    IEditorContext iEditorContext = BaseAudioSpan.this.mIEditorContextWeakReference != null ? BaseAudioSpan.this.mIEditorContextWeakReference.get() : null;
                    if (iEditorContext != null) {
                        iEditorContext.refreshAllImageSpans();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BaseAudioSpan.this.mIsPlayAnimRunning = false;
                }
            });
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            i2 = (int) (i2 + iEditorContext.getView().getScrollY() + iEditorContext.getView().getY());
        }
        boolean z = i2 >= this.mBounds.top && i2 <= this.mBounds.bottom && i >= 0 && i <= this.mBounds.left + this.mSpanContentWidth;
        if (z) {
            this.isTouchIn = i <= this.mBounds.right && i >= this.mBounds.left;
            this.isTouchLeft = this.mAudioBubbleDrawable.isTouchLeft(i - this.mBounds.left);
        }
        return z;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
        int[] iArr = {i, i2};
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.onImageSpanClick(this, iArr);
        }
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
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.BaseAudioSpan$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$startDismissAnim$0(valueAnimator2);
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.BaseAudioSpan.3
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
                BaseAudioSpan.this.setAlpha(0);
                BaseAudioSpan.this.deleteSelf();
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDismissAnim$0(ValueAnimator valueAnimator) {
        this.mShowScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        setAlpha((int) ((1.0f - valueAnimator.getAnimatedFraction()) * 255.0f));
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.refreshContainer();
        }
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
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.BaseAudioSpan$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$startInsertAnim$1(valueAnimator2);
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.style.BaseAudioSpan.4
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
                IEditorContext iEditorContext = BaseAudioSpan.this.mIEditorContextWeakReference == null ? null : BaseAudioSpan.this.mIEditorContextWeakReference.get();
                if (iEditorContext != null) {
                    BaseAudioSpan baseAudioSpan = BaseAudioSpan.this;
                    boolean zIsShowBig = baseAudioSpan.isShowBig();
                    IEditorImageRender imageRender = iEditorContext.getImageRender();
                    baseAudioSpan.mShowScale = zIsShowBig ? imageRender.getShowBigScale() : imageRender.getShowSmallScale();
                }
                BaseAudioSpan.this.setAlpha(255);
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInsertAnim$1(ValueAnimator valueAnimator) {
        this.mShowScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        setAlpha((int) (valueAnimator.getAnimatedFraction() * 255.0f));
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.refreshContainer();
        }
    }

    protected IAudioEditorContext getEditorContext() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            return (IAudioEditorContext) iEditorContext;
        }
        return null;
    }

    public void setAnimRunningState(boolean z) {
        this.mIsPlayAnimRunning = z;
    }

    @Override // 
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BaseAudioSpan mo2118clone() throws CloneNotSupportedException {
        return new BaseAudioSpan(getEditorContext(), (HtmlParser.SoundElement) getElement(), this.mSelectSpanListener);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.todo_audio_default_content);
    }

    public int getRecordTime() {
        return this.recordTime;
    }
}
