package com.miui.common.view;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import java.util.Random;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class DoneSparkAnim extends ValueAnimator {
    private static final int[] COLOR_TABLES = {Color.parseColor("#02B2FD"), Color.parseColor("#FD1E77"), Color.parseColor("#FB95B9"), Color.parseColor("#FAC997"), Color.parseColor("#A6F18A"), Color.parseColor("#8960FF"), Color.parseColor("#FEC700"), Color.parseColor("#FB95B9"), Color.parseColor("#FFA27B"), Color.parseColor("#FCC799")};
    private static final float DEFAULT_RADIUS = 15.0f;
    private static final float DEFAULT_SIZE = 30.0f;
    private static final float GRAVITY = 9.8E-4f;
    private static final int POINT_COUNT = 200;
    private static final float Z_TRANS_PARAM = 0.005f;
    private Rect mArea;
    private View mHost;
    private float mPRaidus;
    private float mPSize;
    private SPoint[] mSPoints;
    private float mSpeedUp = -0.7f;
    private float mSpeedDown = 0.6f;
    private long mLastUpdateTime = 0;
    private EaseManager.SpringInterpolator mInterpolator = new EaseManager.SpringInterpolator();
    private FloatEvaluator mFloatEv = new FloatEvaluator();
    private Paint mPaint = new Paint();
    private int mPCounts = 200;
    long fractionTime = 0;
    long timeDiff = 0;
    long ts = 0;

    private class SPoint {
        float alpha;
        float alpha0;
        private long alpha0Duration;
        private float alpha0Fraction;
        private long alpha1Duration;
        private float alpha1Fraction;
        int color;
        float dx;
        float dyDown;
        float dyUp;
        int i;
        float r;
        float scale;
        float scale0;
        private long scaleDuration;
        private float scaleFraction;
        long subDuration;
        private float subFraction;
        float x;
        float y;
        float z;

        public SPoint(long j, float f) {
            this.r = DoneSparkAnim.DEFAULT_RADIUS;
            this.subDuration = j;
            long j2 = j / 4;
            this.alpha1Duration = j2;
            this.scaleDuration = j2;
            this.alpha0Duration = j / 8;
            this.r = f;
        }

        public void update(long j, long j2) {
            float f = j;
            float f2 = f * 1.0f;
            long j3 = this.subDuration;
            float f3 = f2 / j3;
            this.subFraction = f3;
            float f4 = f2 / this.alpha1Duration;
            this.alpha1Fraction = f4;
            float f5 = (f - (j3 * 0.167f)) / this.alpha0Duration;
            this.alpha0Fraction = f5;
            float f6 = (f - (j3 * 0.125f)) / this.scaleDuration;
            this.scaleFraction = f6;
            if (f4 > 1.0f) {
                this.alpha1Fraction = 1.0f;
            }
            if (f5 > 1.0f) {
                this.alpha0Fraction = 1.0f;
            }
            if (f6 > 1.0f) {
                this.scaleFraction = 1.0f;
            }
            if (f3 <= 1.0f) {
                this.x += this.dx;
                if (this.i % 2 == 0) {
                    this.y += DoneSparkAnim.this.mSpeedUp * this.dyUp;
                } else {
                    this.y += DoneSparkAnim.this.mSpeedDown * this.dyDown;
                }
                setAlpha();
                setScale();
            }
            if (this.subFraction > 1.0f) {
                this.subFraction = 1.0f;
            }
        }

        private void setAlpha() {
            double d = this.subFraction;
            Float fValueOf = Float.valueOf(1.0f);
            if (d < 0.167d) {
                this.alpha = DoneSparkAnim.this.mFloatEv.evaluate(DoneSparkAnim.this.mInterpolator.getInterpolation(this.alpha1Fraction), (Number) Float.valueOf(this.alpha0), (Number) fValueOf).floatValue();
            }
            if (this.subFraction >= 0.167d) {
                this.alpha = DoneSparkAnim.this.mFloatEv.evaluate(DoneSparkAnim.this.mInterpolator.getInterpolation(this.alpha0Fraction), (Number) fValueOf, (Number) Float.valueOf(0.0f)).floatValue();
            }
        }

        private void setScale() {
            if (this.subFraction > 0.125f) {
                this.scale = DoneSparkAnim.this.mFloatEv.evaluate(DoneSparkAnim.this.mInterpolator.getInterpolation(this.scaleFraction), (Number) Float.valueOf(this.scale0), (Number) Float.valueOf(0.1f)).floatValue();
            }
        }
    }

    public DoneSparkAnim(View view, Rect rect) {
        this.mPSize = DEFAULT_SIZE;
        this.mPRaidus = DEFAULT_RADIUS;
        setFloatValues(0.0f, 1.0f);
        this.mArea = new Rect(rect);
        this.mHost = view;
        float dimensionPixelSize = view.getContext().getResources().getDimensionPixelSize(R.dimen.spark_anim_point_size);
        this.mPSize = dimensionPixelSize;
        this.mPRaidus = dimensionPixelSize / 3.0f;
        if (isPcDensity(view.getContext())) {
            this.mPCounts *= 2;
        }
        this.mSPoints = new SPoint[this.mPCounts];
        generateSPoints();
    }

    private void generateSPoints() {
        int length = COLOR_TABLES.length;
        Random random = new Random();
        int iCenterX = this.mArea.centerX();
        int iCenterY = this.mArea.centerY();
        int iWidth = this.mArea.width();
        int iNextInt = 0;
        for (int i = 0; i < 200; i++) {
            SPoint sPoint = new SPoint((((long) random.nextInt(4)) * 1000) + 1000, this.mPRaidus);
            if (iWidth > 60) {
                iNextInt = random.nextInt(iWidth - 60);
            }
            sPoint.i = i;
            double d = ((((double) i) * 3.6d) * 3.141592653589793d) / 180.0d;
            sPoint.x = iNextInt + 60 + (this.mPSize * ((float) Math.cos(d)));
            sPoint.y = iCenterY + (this.mPSize * ((float) Math.sin(d)));
            sPoint.z = random.nextInt(41) + 10.0f;
            float fNextFloat = (random.nextFloat() * 0.3f) + 0.7f;
            sPoint.alpha0 = fNextFloat;
            sPoint.alpha = fNextFloat;
            float fNextFloat2 = (random.nextFloat() * 0.7f) + 0.2f;
            sPoint.scale0 = fNextFloat2;
            sPoint.scale = fNextFloat2;
            sPoint.color = COLOR_TABLES[random.nextInt(length)];
            float f = iCenterX;
            sPoint.dx = ((sPoint.x - f) * DEFAULT_RADIUS) / f;
            sPoint.dyUp = random.nextInt(11) + 15;
            sPoint.dyDown = random.nextInt(11) + 10;
            this.mSPoints[i] = sPoint;
        }
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public void start() {
        super.start();
        this.mSpeedDown = 0.6f;
        this.mSpeedUp = -0.7f;
    }

    public void draw(Canvas canvas) {
        this.ts = System.currentTimeMillis();
        if (isStarted()) {
            long currentPlayTime = getCurrentPlayTime();
            this.fractionTime = currentPlayTime;
            long j = this.mLastUpdateTime;
            long j2 = currentPlayTime - j;
            this.timeDiff = j2;
            this.mLastUpdateTime = j + j2;
            this.mSpeedUp += j2 * GRAVITY;
            this.mSpeedDown += j2 * GRAVITY;
            for (int i = 0; i < 200; i++) {
                SPoint sPoint = this.mSPoints[i];
                sPoint.update(this.fractionTime, this.timeDiff);
                this.mPaint.setAntiAlias(true);
                this.mPaint.setColor(sPoint.color);
                this.mPaint.setAlpha((int) (sPoint.alpha * 255.0f));
                canvas.drawCircle(sPoint.x, sPoint.y, sPoint.r * sPoint.scale, this.mPaint);
            }
            Logger.INSTANCE.d("DoneSparkAnim", "draw cost:" + (System.currentTimeMillis() - this.ts) + "ms");
        }
    }

    public boolean isPcDensity(Context context) {
        return context.getResources().getDisplayMetrics().density == 1.0f || ((double) context.getResources().getDisplayMetrics().density) <= 1.31d;
    }
}
