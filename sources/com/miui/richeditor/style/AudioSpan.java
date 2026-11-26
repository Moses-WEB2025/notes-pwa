package com.miui.richeditor.style;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.audio.AnimAudioSpanDrawable;
import com.miui.richeditor.style.audio.BubbleDrawable;
import com.miui.richeditor.style.audio.WaveBubbleDrawable;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class AudioSpan extends BaseAudioSpan {
    private boolean isGestureMoved;
    private boolean isTouchPlayIcon;
    private boolean isTouchWave;
    private boolean isTouchWaveLeft;
    private boolean isTouchWaveRight;
    private boolean isTrashIconClicked;
    protected int mAudioDrawableWidth;
    private float mAudioPlayPos;
    private int mPlayDuration;

    public AudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement) {
        this(iAudioEditorContext, displayElement, iAudioEditorContext.getSelectableSpanListener());
    }

    public AudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement, SelectableSpanListener selectableSpanListener) {
        super(iAudioEditorContext, displayElement, selectableSpanListener);
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext == null || !editorContext.isRecording()) {
            return;
        }
        editorContext.getView().post(new Runnable() { // from class: com.miui.richeditor.style.AudioSpan$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mIsPlayAnimRunning = true;
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext != null) {
            getAnimBubbleDrawable().startPlayRecordAnim(new AnimAudioPlayListener(editorContext, this));
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    protected void init() {
        this.mAudioBubbleDrawable = createAudioDrawable(this.recordTime, 1);
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    protected WaveBubbleDrawable createAudioDrawable(int i, int i2) {
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext != null) {
            return new AnimAudioSpanDrawable(i, i2, editorContext.getEditorTheme() != null ? editorContext.getEditorTheme().getId() : 0, editorContext.getContentWidth());
        }
        return new AnimAudioSpanDrawable(i, i2, 0, 0);
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    public void updateElement(String str) {
        this.isIncreaseAnimRunning = true;
        if (!(this.mElement instanceof HtmlParser.ImageElement) && (this.mElement instanceof HtmlParser.DisplayElement)) {
            ((HtmlParser.DisplayElement) this.mElement).setAttribute(str);
        }
        this.mSrcFileId = str;
        getAnimBubbleDrawable().cancelRecordAnim();
        this.recordTime = AudioUtils.parseTimeFromFile(AudioUtils.NOTE_MP3_DIR_NAME, str);
        if (this.mAudioDrawableWidth == 0) {
            refreshAudioDrawableWidth();
        }
        this.mAudioBubbleDrawable.startIncreaseAnim(this.recordTime, new BaseAudioPlayListener() { // from class: com.miui.richeditor.style.AudioSpan.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AudioSpan.this.mDrawable = null;
                IEditorContext iEditorContext = AudioSpan.this.mIEditorContextWeakReference != null ? AudioSpan.this.mIEditorContextWeakReference.get() : null;
                if (iEditorContext != null) {
                    iEditorContext.refreshAllImageSpans();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                AudioSpan.this.isIncreaseAnimRunning = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                AudioSpan.this.isIncreaseAnimRunning = false;
            }
        });
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    protected void initDrawableSize() {
        if (this.mDrawable == null) {
            this.mSrcHeight = 0;
            this.mSrcWidth = 0;
            IAudioEditorContext editorContext = getEditorContext();
            if (!isAnimRunning() && editorContext != null) {
                if (this.mElement instanceof HtmlParser.DisplayElement) {
                    this.recordTime = AudioUtils.getFileTime(((HtmlParser.DisplayElement) this.mElement).getFileId(), editorContext.getAudioFileDir());
                }
                this.mSpanContentWidth = editorContext.getContentWidth();
                refreshAudioDrawableWidth();
                this.mAudioBubbleDrawable = createAudioDrawable(this.recordTime, getDrawType());
                this.mAudioBubbleDrawable.setStyle(editorContext.getEditorTheme() != null ? editorContext.getEditorTheme().getId() : 0);
                this.mDrawable = BubbleDrawable.getBitmapDrawable(getContext(), this.mAudioBubbleDrawable);
            }
        }
        if (this.mDrawable != null) {
            this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
            this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan
    protected void updateDrawParams() {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext == null) {
            return;
        }
        int contentWidth = iEditorContext.getContentWidth();
        boolean z = true;
        if (this.mDrawable != null) {
            boolean z2 = this.mSpanContentWidth == 0 || this.mSpanContentWidth != contentWidth;
            if (this.mEditorViewY == ((int) iEditorContext.getView().getY())) {
                z = z2;
            }
        }
        if (z || (this.mTransitAnimator != null && this.mTransitAnimator.isRunning())) {
            reset();
            refreshAudioDrawableWidth();
            this.mSpanContentWidth = contentWidth;
            if (!isAnimRunning()) {
                initDrawableSize();
                this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
                this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
            }
            this.mBounds.set(0, 0, 0, 0);
            this.mBounds.right = (int) (this.mSrcWidth * this.mShowScale);
            this.mBounds.bottom = (int) (this.mSrcHeight * this.mShowScale);
            this.mSpanTop += ((int) iEditorContext.getView().getY()) - this.mEditorViewY;
            this.mBounds.offset(this.mSpanLeft, this.mSpanTop);
            this.mEditorViewY = (int) iEditorContext.getView().getY();
        }
        this.mSrcBounds.right = this.mSrcWidth;
        this.mSrcBounds.bottom = this.mSrcHeight;
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    public WaveBubbleDrawable getAudioDrawable() {
        return this.mAudioBubbleDrawable;
    }

    public AnimAudioSpanDrawable getAnimBubbleDrawable() {
        return (AnimAudioSpanDrawable) this.mAudioBubbleDrawable;
    }

    protected void refreshAudioDrawableWidth() {
        IAudioEditorContext editorContext = getEditorContext();
        this.mAudioDrawableWidth = editorContext == null ? 0 : editorContext.getContentWidth();
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan
    protected void prepareDrawable() {
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext != null && editorContext.getEditorTheme() != null && (this.mDrawable == null || this.mAudioBubbleDrawable.getStyleId() != editorContext.getEditorTheme().getId())) {
            refreshAudioDrawableWidth();
            if (!isAnimRunning()) {
                if (this.mElement instanceof HtmlParser.DisplayElement) {
                    this.recordTime = AudioUtils.getFileTime(((HtmlParser.DisplayElement) this.mElement).getFileId(), editorContext.getAudioFileDir());
                }
                this.mAudioBubbleDrawable = createAudioDrawable(this.recordTime, getDrawType());
                this.mAudioBubbleDrawable.setStyle(editorContext.getEditorTheme().getId());
            }
            this.mDrawable = BubbleDrawable.getBitmapDrawable(getContext(), this.mAudioBubbleDrawable);
        }
        if (this.mDrawable != null) {
            this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
            this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan
    protected void computeMissedBounds() {
        IAudioEditorContext editorContext = getEditorContext();
        int contentWidth = editorContext != null ? (editorContext.getContentWidth() - editorContext.getView().getPaddingEnd()) - editorContext.getView().getPaddingStart() : 0;
        if (this.mDrawable == null || this.mSpanContentWidth == 0 || this.mSpanContentWidth != contentWidth) {
            this.mSpanContentWidth = contentWidth;
            refreshAudioDrawableWidth();
            reset();
            initDrawableSize();
            if (this.mDrawable != null) {
                this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
                this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
            }
        }
        this.mSrcBounds.right = this.mSrcWidth;
        this.mSrcBounds.bottom = this.mSrcHeight;
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
        if (this.mAudioBubbleDrawable.getIntrinsicWidth() == 0 && this.recordTime > 0) {
            this.mAudioBubbleDrawable = createAudioDrawable(this.recordTime, getDrawType());
        }
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            this.mBoundsInParent.offsetTo(iEditorContext.getView().getPaddingLeft(), i5 - this.mBounds.height());
        }
        this.mSrcBitmap = UIUtils.drawableToBitmap(this.mAudioBubbleDrawable);
        this.mSrcBitmap.setDensity(getContext().getResources().getDisplayMetrics().densityDpi);
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext == null || editorContext.isRecording()) {
            return;
        }
        if (this.isTouchIn && this.isTouchPlayIcon) {
            String fileId = ((HtmlParser.DisplayElement) this.mElement).getFileId();
            if (TextUtils.isEmpty(editorContext.getLastPlayedAudioFile()) || fileId.equals(editorContext.getLastPlayedAudioFile())) {
                editorContext.setAudioGestureMoveInWave(false);
                if (editorContext.getMediaPlayer() != null) {
                    if (editorContext.getMediaPlayer().isPlaying()) {
                        editorContext.pauseAudio();
                        if (editorContext.getPlayingAudioSpan() == this) {
                            editorContext.pauseAudioAnim();
                            return;
                        }
                    } else {
                        editorContext.playAudio();
                        if (getAnimBubbleDrawable() != null) {
                            getAnimBubbleDrawable().setPlayStatus(true);
                            if (this.isGestureMoved) {
                                getAnimBubbleDrawable().startPlayAnimAfterMove(new AnimAudioPlayListener(editorContext, this), (int) (getPlayDuration() * (1.0f - this.mAudioPlayPos)));
                                this.isGestureMoved = false;
                            } else {
                                getAnimBubbleDrawable().restartPlayAnim(new AnimAudioPlayListener(editorContext, this));
                            }
                            this.mIsPlayAnimRunning = true;
                            return;
                        }
                        return;
                    }
                }
            }
            if (fileId == null || "temp_audio_file".equals(fileId)) {
                editorContext.onPlayAudioError(0);
                return;
            }
            if (AudioUtils.isNoteMp3FileExist(fileId)) {
                editorContext.onPlayAudioError(1);
                return;
            }
            editorContext.stopMediaPlayerIfNeed();
            editorContext.setLastPlayedAudioFile(fileId);
            getAnimBubbleDrawable().updateOffset(0);
            editorContext.cancelPlayAnimIfNeed();
            editorContext.startMediaPlayer(this, new File(AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME), fileId).getAbsolutePath());
            editorContext.playAudio();
            this.mPlayDuration = editorContext.getMediaPlayer().getDuration();
            if (getAnimBubbleDrawable() != null) {
                this.mIsPlayAnimRunning = true;
                getAnimBubbleDrawable().setPlayStatus(true);
                getAnimBubbleDrawable().setNeedShowGesturePointer(true);
                getAnimBubbleDrawable().startPlayAnim(new AnimAudioPlayListener(editorContext, this));
            }
        }
        if (!this.isTouchIn || !this.isTrashIconClicked || getAnimBubbleDrawable() == null || getAnimBubbleDrawable().mIsPlaying) {
            return;
        }
        editorContext.deleteImageSpan(this, true);
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onTouchPoint(int i, int i2, MotionEvent motionEvent) {
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext == null || editorContext.isRecording()) {
            return;
        }
        editorContext.requestDisallowInterceptTouchEvent(true);
        if (this.isTouchIn && this.isTouchWave) {
            String fileId = ((HtmlParser.DisplayElement) this.mElement).getFileId();
            if (TextUtils.isEmpty(editorContext.getLastPlayedAudioFile()) || fileId.equals(editorContext.getLastPlayedAudioFile())) {
                editorContext.setAudioGestureMoveInWave(true);
                if (editorContext.getMediaPlayer() != null) {
                    this.mIsPlayAnimRunning = true;
                    if (motionEvent.getAction() == 0) {
                        editorContext.pauseAudioAnimAndRemoveListener();
                        editorContext.pauseAudio();
                    }
                    if (editorContext.getPlayingAudioSpan() != null && getAnimBubbleDrawable() != null) {
                        this.isGestureMoved = true;
                        editorContext.pauseAudioAnimAndRemoveListener();
                        if (editorContext.getMediaPlayer() != null) {
                            editorContext.pauseAudio();
                        }
                        getAnimBubbleDrawable().updateOffset((int) (getAnimBubbleDrawable().getWaveTotalWidth() * this.mAudioPlayPos));
                    }
                    if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        getAnimBubbleDrawable().setPlayStatus(true);
                        getAnimBubbleDrawable().setNeedShowGesturePointer(true);
                        getAnimBubbleDrawable().startPlayAnimAfterMove(new AnimAudioPlayListener(editorContext, this), (int) (getPlayDuration() * (1.0f - this.mAudioPlayPos)));
                        editorContext.playAudio();
                        editorContext.getMediaPlayer().seekTo((int) (getPlayDuration() * this.mAudioPlayPos));
                        editorContext.setAudioGestureMoveInWave(false);
                    }
                    clearDrawable();
                    editorContext.refreshAllImageSpans();
                    return;
                }
            }
            if (fileId == null || "temp_audio_file".equals(fileId)) {
                editorContext.onPlayAudioError(0);
                return;
            }
            if (AudioUtils.isNoteMp3FileExist(fileId)) {
                editorContext.onPlayAudioError(1);
                return;
            }
            editorContext.stopMediaPlayerIfNeed();
            editorContext.cancelPlayAnimIfNeed();
            editorContext.setLastPlayedAudioFile(fileId);
            editorContext.startMediaPlayer(this, new File(AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME), fileId).getAbsolutePath());
            this.mPlayDuration = editorContext.getMediaPlayer().getDuration();
            if (getAnimBubbleDrawable() != null) {
                this.mIsPlayAnimRunning = true;
                getAnimBubbleDrawable().setPlayStatus(true);
                getAnimBubbleDrawable().setNeedShowGesturePointer(true);
                getAnimBubbleDrawable().updateOffset((int) (getAnimBubbleDrawable().getWaveTotalWidth() * this.mAudioPlayPos));
                editorContext.playAudio();
                editorContext.getMediaPlayer().seekTo((int) (getPlayDuration() * this.mAudioPlayPos));
                getAnimBubbleDrawable().startPlayAnimAfterMove(new AnimAudioPlayListener(editorContext, this), (int) (getPlayDuration() * (1.0f - this.mAudioPlayPos)));
                return;
            }
            return;
        }
        if (editorContext.isAudioGestureMoveInWave()) {
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                editorContext.setAudioGestureMoveInWave(false);
                if (this.isTouchWaveLeft) {
                    getAnimBubbleDrawable().setPlayStatus(true);
                    getAnimBubbleDrawable().setNeedShowGesturePointer(true);
                    getAnimBubbleDrawable().startPlayAnimAfterMoveRestart(new AnimAudioPlayListener(editorContext, this), getPlayDuration());
                    if (editorContext.getMediaPlayer() != null) {
                        editorContext.getMediaPlayer().seekTo(0);
                        editorContext.playAudio();
                        return;
                    }
                    return;
                }
                if (this.isTouchWaveRight) {
                    getAnimBubbleDrawable().setPlayStatus(false);
                    getAnimBubbleDrawable().setNeedShowGesturePointer(false);
                    editorContext.stopMediaPlayerIfNeed();
                    editorContext.cancelPlayAnimIfNeed();
                    this.mDrawable = null;
                    editorContext.refreshAllImageSpans();
                }
            }
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            i2 = (int) (i2 + iEditorContext.getView().getScrollY() + iEditorContext.getView().getY());
        }
        boolean z = i2 >= this.mBounds.top && i2 <= this.mBounds.bottom && i >= 0 && i <= this.mBounds.left + this.mSpanContentWidth;
        if (getEditorContext() != null) {
            i += getEditorContext().getEditAreaView().getPaddingLeft();
        }
        if (z) {
            this.isTouchIn = i <= this.mBounds.right && i >= this.mBounds.left;
            this.isTouchWave = getAnimBubbleDrawable().isTouchWave(i - this.mBounds.left);
            this.isTouchWaveLeft = getAnimBubbleDrawable().isTouchWaveLeft(i - this.mBounds.left);
            this.isTouchWaveRight = getAnimBubbleDrawable().isTouchWaveRight(i - this.mBounds.left);
            this.isTouchPlayIcon = getAnimBubbleDrawable().isTouchPlayIcon(i - this.mBounds.left);
            this.isTrashIconClicked = getAnimBubbleDrawable().isTrashIconClicked(i - this.mBounds.left);
            if (this.isTouchIn && this.isTouchWave) {
                float touchLeftPadding = ((i - this.mBounds.left) - getAnimBubbleDrawable().getTouchLeftPadding()) / getAnimBubbleDrawable().getWaveTotalWidth();
                this.mAudioPlayPos = touchLeftPadding;
                if (touchLeftPadding < 0.0f) {
                    this.mAudioPlayPos = 0.0f;
                    return z;
                }
                if (touchLeftPadding > 1.0f) {
                    this.mAudioPlayPos = 1.0f;
                }
            }
        }
        return z;
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
        if (getAnimBubbleDrawable().mIsPlaying || getEditorContext() == null) {
            return;
        }
        getEditorContext().stopMediaPlayerIfNeed();
        getEditorContext().startDragAudioSpan(this, new int[]{i + getEditorContext().getEditAreaView().getPaddingLeft(), i2});
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan
    public Drawable getDrawableForShadow() {
        int i = (int) (getAnimBubbleDrawable().mRectWidth * this.mShowScale);
        int i2 = (int) (getAnimBubbleDrawable().mRectHeight * this.mShowScale);
        if (i == 0) {
            i = 1;
        }
        if (i2 == 0) {
            i2 = 1;
        }
        Bitmap bitmapScaleBitmap = UIUtils.scaleBitmap(UIUtils.drawableToBitmap(getAnimBubbleDrawable()), i, i2, false);
        bitmapScaleBitmap.setDensity(getContext().getResources().getDisplayMetrics().densityDpi);
        return new BitmapDrawable(getContext().getResources(), bitmapScaleBitmap);
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    public void reset() {
        super.reset();
        this.mAudioDrawableWidth = 0;
    }

    public int getPlayDuration() {
        return this.mPlayDuration;
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    public void pauseAnim() {
        if (getAnimBubbleDrawable() != null) {
            getAnimBubbleDrawable().setPlayStatus(false);
            getAnimBubbleDrawable().pauseAnim();
            clearDrawable();
            IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
            if (iEditorContext != null) {
                iEditorContext.refreshAllImageSpans();
            }
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    public void pauseAudioAnimAndRemoveListener() {
        if (getAnimBubbleDrawable() != null) {
            getAnimBubbleDrawable().pauseAnimAndRemoveListener();
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan
    /* JADX INFO: renamed from: clone */
    public BaseAudioSpan mo2118clone() throws CloneNotSupportedException {
        return new AudioSpan(getEditorContext(), (HtmlParser.SoundElement) getElement(), this.mSelectSpanListener);
    }

    @Override // com.miui.richeditor.style.BaseAudioSpan, com.miui.richeditor.style.BaseImageSpan, android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.snippet_audio_stub);
    }
}
