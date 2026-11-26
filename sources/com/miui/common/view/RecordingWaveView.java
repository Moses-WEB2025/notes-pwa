package com.miui.common.view;

import android.animation.IntEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public class RecordingWaveView extends View {
    private static double AMP_DISPLAY_RATIO_HIGH = 0.05d;
    private static double AMP_DISPLAY_RATIO_LOW = 0.05d;
    private int lastVolume;
    private boolean mAddVolumeChange;
    private int mCount;
    private double mCurrentVolume;
    private int mDefaultColor;
    private int[] mDrawElementAlphaArray;
    private int[] mDrawElementAlphaDelayArray;
    protected Disposable mDrawTask;
    private int mDrawingCenterPosX;
    private int mDrawingPositionX;
    private LinkedList<Integer> mElementCache;
    private boolean mEnableDraw;
    private int mFilterCount;
    private int mFrameIndex;
    private int mIndicatorColor;
    private int mIndicatorMaxHeight;
    private int mIndicatorMinHeight;
    private int mIndicatorNum;
    private int mIndicatorRadius;
    private int mIndicatorWidth;
    private IntEvaluator mIntEvaluator;
    private int mInterval;
    private double mLastAddVolume;
    private int mMaxIndicatorNum;
    private int mMaxVolumeNum;
    Paint mPaint;
    private PhysicBasedInterpolator mPhysicBasedInterpolator;
    protected Disposable mVolumeTask;

    private void initParams(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecordingProgressView);
        this.mInterval = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 25);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 4);
        this.mIndicatorWidth = dimensionPixelSize;
        this.mIndicatorRadius = dimensionPixelSize / 2;
        this.mIndicatorMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 80);
        this.mIndicatorMinHeight = this.mIndicatorRadius * 2;
        int i = typedArrayObtainStyledAttributes.getInt(3, 5);
        this.mIndicatorNum = i;
        this.mMaxIndicatorNum = i;
        this.mMaxVolumeNum = (i / 2) + (i % 2);
        this.mDefaultColor = typedArrayObtainStyledAttributes.getColor(0, -7829368);
        this.mIndicatorColor = typedArrayObtainStyledAttributes.getColor(1, -16776961);
        typedArrayObtainStyledAttributes.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        int i2 = this.mInterval + this.mIndicatorWidth;
        int i3 = this.mMaxVolumeNum;
        this.mDrawingCenterPosX = i2 * (i3 - 1);
        int i4 = this.mIndicatorMaxHeight;
        AMP_DISPLAY_RATIO_HIGH = (i4 * 0.7f) / 10000.0f;
        AMP_DISPLAY_RATIO_LOW = (i4 * 0.3f) / 500.0f;
        this.mDrawElementAlphaArray = new int[i3];
        this.mDrawElementAlphaDelayArray = new int[i3];
        resetData();
    }

    public RecordingWaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFrameIndex = 1;
        this.mLastAddVolume = 0.0d;
        this.mCurrentVolume = 0.0d;
        this.mAddVolumeChange = false;
        this.mEnableDraw = false;
        this.mPaint = new Paint();
        this.mFilterCount = 10;
        this.mPhysicBasedInterpolator = new PhysicBasedInterpolator.Builder().setDamping(0.9f).setResponse(1.0f).build();
        this.mIntEvaluator = new IntEvaluator();
        this.mElementCache = new LinkedList<>();
        this.lastVolume = 0;
        initParams(context, attributeSet);
    }

    public RecordingWaveView(Context context) {
        super(context);
        this.mFrameIndex = 1;
        this.mLastAddVolume = 0.0d;
        this.mCurrentVolume = 0.0d;
        this.mAddVolumeChange = false;
        this.mEnableDraw = false;
        this.mPaint = new Paint();
        this.mFilterCount = 10;
        this.mPhysicBasedInterpolator = new PhysicBasedInterpolator.Builder().setDamping(0.9f).setResponse(1.0f).build();
        this.mIntEvaluator = new IntEvaluator();
        this.mElementCache = new LinkedList<>();
        this.lastVolume = 0;
    }

    public RecordingWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFrameIndex = 1;
        this.mLastAddVolume = 0.0d;
        this.mCurrentVolume = 0.0d;
        this.mAddVolumeChange = false;
        this.mEnableDraw = false;
        this.mPaint = new Paint();
        this.mFilterCount = 10;
        this.mPhysicBasedInterpolator = new PhysicBasedInterpolator.Builder().setDamping(0.9f).setResponse(1.0f).build();
        this.mIntEvaluator = new IntEvaluator();
        this.mElementCache = new LinkedList<>();
        this.lastVolume = 0;
        initParams(context, attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.mInterval;
        setMeasuredDimension(((this.mIndicatorWidth + i3) * this.mIndicatorNum) - i3, getDefaultSize(getSuggestedMinimumHeight(), i2));
    }

    private void resetData() {
        this.lastVolume = this.mIndicatorMinHeight;
        int i = 0;
        while (true) {
            int[] iArr = this.mDrawElementAlphaArray;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = i <= 2 ? 255 : 0;
            this.mDrawElementAlphaDelayArray[i] = 0;
            i++;
        }
        this.mElementCache.clear();
        for (int i2 = 0; i2 < 50; i2++) {
            this.mElementCache.add(Integer.valueOf(this.mIndicatorMinHeight));
        }
    }

    public void reset() {
        Disposable disposable = this.mDrawTask;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDrawTask.dispose();
        }
        Disposable disposable2 = this.mVolumeTask;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mVolumeTask.dispose();
        }
        resetData();
        this.mFrameIndex = 1;
        this.mCount = 0;
    }

    public void startDraw() {
        this.mEnableDraw = true;
        Observable.interval(16L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new DrawTimerTask(this));
        Observable.interval(160L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new UpdateVolumeTask(this));
    }

    public void stopDraw() {
        this.mEnableDraw = false;
        Disposable disposable = this.mDrawTask;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDrawTask.dispose();
        }
        Disposable disposable2 = this.mVolumeTask;
        if (disposable2 == null || disposable2.isDisposed()) {
            return;
        }
        this.mVolumeTask.dispose();
    }

    protected void doDraw() {
        postInvalidate();
        this.mFrameIndex++;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopDraw();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:32:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:34:0x00bb  */
    private void drawVolumesV2(Canvas canvas) {
        int[] iArr;
        int i;
        int[] iArr2;
        int i2;
        int i3;
        int i4 = this.mFrameIndex - 1;
        if (i4 > 9) {
            i4 = 9;
        }
        this.mPaint.setColor(isEnabled() ? this.mIndicatorColor : this.mDefaultColor);
        this.mDrawingPositionX = 0;
        int iIntValue = this.mElementCache.get(9 - i4).intValue();
        this.lastVolume = iIntValue;
        int height = (getHeight() - iIntValue) / 2;
        int i5 = this.mDrawingCenterPosX;
        float f = i5 + this.mIndicatorWidth;
        float f2 = height + iIntValue;
        int i6 = this.mIndicatorRadius;
        canvas.drawRoundRect(i5, height, f, f2, i6, i6, this.mPaint);
        for (int i7 = 1; i7 < this.mMaxVolumeNum; i7++) {
            int iPow = (int) (Math.pow(0.8d, i7) * ((double) this.mElementCache.get(((i7 * 3) + 10) - i4).intValue()));
            int i8 = this.mIndicatorMinHeight;
            if (iPow < i8) {
                iPow = i8;
            }
            this.mDrawingPositionX = this.mDrawingCenterPosX - ((this.mInterval + this.mIndicatorWidth) * i7);
            int height2 = (getHeight() - iPow) / 2;
            if (i7 > 2) {
                int i9 = this.mIndicatorMinHeight;
                if (iPow == i9 && (i2 = (iArr2 = this.mDrawElementAlphaArray)[i7]) > 0) {
                    int[] iArr3 = this.mDrawElementAlphaDelayArray;
                    int i10 = iArr3[i7];
                    if (i10 < 50) {
                        iArr3[i7] = i10 + 1;
                    } else {
                        int i11 = this.mMaxVolumeNum;
                        if (i7 < i11 - 1) {
                            int i12 = i7 + 1;
                            if (iArr2[i12] == 0 && iArr3[i12] == 0) {
                                i3 = i2 - 22;
                                if (i3 < 0) {
                                    iArr3[i7] = 0;
                                    i3 = 0;
                                }
                                iArr2[i7] = i3;
                                Logger.INSTANCE.i("WaveView", " mDrawElementAlphaArray[" + i7 + "] = " + this.mDrawElementAlphaArray[i7]);
                            } else if (i7 == i11 - 1) {
                                i3 = i2 - 22;
                                if (i3 < 0) {
                                    iArr3[i7] = 0;
                                    i3 = 0;
                                }
                                iArr2[i7] = i3;
                                Logger.INSTANCE.i("WaveView", " mDrawElementAlphaArray[" + i7 + "] = " + this.mDrawElementAlphaArray[i7]);
                            }
                        } else if (i7 == i11 - 1) {
                            i3 = i2 - 22;
                            if (i3 < 0) {
                                iArr3[i7] = 0;
                                i3 = 0;
                            }
                            iArr2[i7] = i3;
                            Logger.INSTANCE.i("WaveView", " mDrawElementAlphaArray[" + i7 + "] = " + this.mDrawElementAlphaArray[i7]);
                        }
                    }
                } else if (iPow > i9 && (i = (iArr = this.mDrawElementAlphaArray)[i7]) < 255) {
                    int i13 = i + 43;
                    if (i13 > 255) {
                        i13 = 255;
                    }
                    iArr[i7] = i13;
                    Logger.INSTANCE.i("WaveView", " mDrawElementAlphaArray[" + i7 + "] = " + this.mDrawElementAlphaArray[i7]);
                }
                this.mPaint.setAlpha(this.mDrawElementAlphaArray[i7]);
            }
            int i14 = this.mDrawingPositionX;
            float f3 = i14;
            float f4 = height2;
            float f5 = i14 + this.mIndicatorWidth;
            float f6 = height2 + iPow;
            int i15 = this.mIndicatorRadius;
            canvas.drawRoundRect(f3, f4, f5, f6, i15, i15, this.mPaint);
            int i16 = this.mDrawingCenterPosX;
            int i17 = this.mInterval;
            int i18 = this.mIndicatorWidth;
            int i19 = i16 + ((i17 + i18) * i7);
            float f7 = i19;
            float f8 = i19 + i18;
            int i20 = this.mIndicatorRadius;
            canvas.drawRoundRect(f7, f4, f8, f6, i20, i20, this.mPaint);
            this.mPaint.setAlpha(255);
        }
    }

    public void addVolumes(double d) {
        Logger.INSTANCE.i("WaveView", " volume = " + d);
        if (this.mEnableDraw) {
            this.mLastAddVolume = d;
            this.mAddVolumeChange = true;
        }
    }

    protected void updateVolume() {
        this.mFrameIndex = 1;
        if (this.mAddVolumeChange) {
            this.mAddVolumeChange = false;
            this.mCurrentVolume = this.mLastAddVolume;
        } else {
            this.mCurrentVolume *= 0.699999988079071d;
        }
        update(this.mCurrentVolume);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawVolumesV2(canvas);
    }

    private void update(double d) {
        invalidate();
        int iVolumeToPix = volumeToPix(d);
        int i = this.mIndicatorMinHeight;
        if (iVolumeToPix < i) {
            iVolumeToPix = i;
        }
        for (int i2 = 0; i2 < 10; i2++) {
            int iIntValue = this.mIntEvaluator.evaluate(this.mPhysicBasedInterpolator.getInterpolation(i2 * 0.1f), Integer.valueOf(this.lastVolume), Integer.valueOf(iVolumeToPix)).intValue();
            int i3 = this.mIndicatorMinHeight;
            if (iIntValue < i3) {
                iIntValue = i3;
            }
            if (this.mElementCache.size() > 49) {
                this.mElementCache.removeLast();
            }
            this.mElementCache.addFirst(Integer.valueOf(iIntValue));
        }
    }

    private int volumeToPix(double d) {
        double d2;
        if (d > 500.0d) {
            d2 = ((double) (this.mIndicatorMaxHeight * 0.3f)) + (d * AMP_DISPLAY_RATIO_HIGH);
        } else {
            d2 = 0.0d * AMP_DISPLAY_RATIO_LOW;
        }
        int i = this.mIndicatorMaxHeight;
        if (d2 > i) {
            d2 = i;
        }
        int i2 = this.mIndicatorMinHeight;
        if (d2 < i2) {
            d2 = i2;
        }
        return (int) d2;
    }

    private static class DrawTimerTask implements Observer<Long> {
        private WeakReference<RecordingWaveView> mWkView;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public DrawTimerTask(RecordingWaveView recordingWaveView) {
            this.mWkView = new WeakReference<>(recordingWaveView);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (this.mWkView.get() != null) {
                this.mWkView.get().mDrawTask = disposable;
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            if (this.mWkView.get() != null) {
                this.mWkView.get().doDraw();
            }
        }
    }

    private static class UpdateVolumeTask implements Observer<Long> {
        private WeakReference<RecordingWaveView> mWkView;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public UpdateVolumeTask(RecordingWaveView recordingWaveView) {
            this.mWkView = new WeakReference<>(recordingWaveView);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (this.mWkView.get() != null) {
                this.mWkView.get().mVolumeTask = disposable;
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            if (this.mWkView.get() != null) {
                this.mWkView.get().updateVolume();
            }
        }
    }
}
