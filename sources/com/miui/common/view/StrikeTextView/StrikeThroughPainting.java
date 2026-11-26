package com.miui.common.view.StrikeTextView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class StrikeThroughPainting implements IPainting {
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_LINES_TOGETHER = 1;
    private static final float STRIKE_THROUGH_BASE_LINE_TOP = 0.2857143f;
    private static final int STRIKE_THROUGH_COLOR = -16777216;
    private static final boolean STRIKE_THROUGH_CUT_TEXT_EDGE = true;
    private static final float STRIKE_THROUGH_FIRST_LINE_POSITION = 0.6f;
    private static final int STRIKE_THROUGH_MODE = 0;
    private static final float STRIKE_THROUGH_POSITION = 0.65f;
    private static final float STRIKE_THROUGH_STROKE_WIDTH = 2.0f;
    private static final long STRIKE_THROUGH_TOTAL_TIME = 1000;
    private List<Rect> lineRects;
    private StrikeTextView targetView;
    private StrikeThroughPaintingCallback strikeThroughPaintingCallback = null;
    private Paint paint = new Paint();
    private boolean drawStrikeThrough = false;
    private float textHeight = 0.0f;
    private float strikeThroughProgress = 0.0f;
    private float strikeThroughPosition = STRIKE_THROUGH_POSITION;
    private float strikeThroughFirstLinePosition = STRIKE_THROUGH_FIRST_LINE_POSITION;
    private float strikeThroughBaseLineTop = STRIKE_THROUGH_BASE_LINE_TOP;
    private int strikeThroughColor = -16777216;
    private float strikeThroughStrokeWidth = 2.0f;
    private long strikeThroughTotalTime = 1000;
    private int strikeThroughMode = 0;
    private boolean strikeThroughCutTextEdge = true;
    private Paint bgPaint = new Paint() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.4
        {
            setColor(-205);
        }
    };
    private int animTotalDistance = 0;

    public interface StrikeThroughPaintingCallback {
        void onStrikeThroughEnd();
    }

    public StrikeThroughPainting(StrikeTextView strikeTextView) {
        this.targetView = strikeTextView;
        strikeTextView.addPainting(this);
        this.paint.setColor(this.strikeThroughColor);
        this.paint.setStrokeWidth(this.strikeThroughStrokeWidth);
    }

    public StrikeThroughPainting linePosition(float f) {
        this.strikeThroughPosition = f;
        return this;
    }

    public StrikeThroughPainting firstLinePosition(float f) {
        this.strikeThroughFirstLinePosition = f;
        return this;
    }

    public StrikeThroughPainting color(int i) {
        this.strikeThroughColor = i;
        this.paint.setColor(i);
        return this;
    }

    public StrikeThroughPainting strokeWidth(float f) {
        this.strikeThroughStrokeWidth = f;
        this.paint.setStrokeWidth(f);
        return this;
    }

    public StrikeThroughPainting totalTime(long j) {
        this.strikeThroughTotalTime = j;
        return this;
    }

    public StrikeThroughPainting callback(StrikeThroughPaintingCallback strikeThroughPaintingCallback) {
        this.strikeThroughPaintingCallback = strikeThroughPaintingCallback;
        return this;
    }

    public StrikeThroughPainting mode(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Mode must be one of MODE_DEFAULT and MODE_LINES_TOGETHER");
        }
        this.strikeThroughMode = i;
        return this;
    }

    public StrikeThroughPainting cutTextEdge(boolean z) {
        this.strikeThroughCutTextEdge = z;
        return this;
    }

    public void strikeThrough() {
        this.targetView.post(new Runnable() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.1
            @Override // java.lang.Runnable
            public void run() {
                StrikeThroughPainting.this.prepareAnimation();
                StrikeThroughPainting.this.startAnimation();
            }
        });
    }

    public void clearStrikeThrough() {
        this.drawStrikeThrough = false;
        this.strikeThroughProgress = 0.0f;
        this.targetView.invalidate();
    }

    public void changeStrikeStyle(long j, final int i) {
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        final int i2 = this.strikeThroughColor;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.strikeThroughStrokeWidth, 1.0f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                StrikeThroughPainting.this.paint.setStrokeWidth(((Float) valueAnimator.getAnimatedValue()).floatValue());
                StrikeThroughPainting.this.paint.setColor(((Integer) argbEvaluator.evaluate(animatedFraction, Integer.valueOf(i2), Integer.valueOf(i))).intValue());
                StrikeThroughPainting.this.targetView.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.3
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
                StrikeThroughPainting.this.clearStrikeThrough();
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // com.miui.common.view.StrikeTextView.IPainting
    public void onDraw(Canvas canvas) {
        if (!this.drawStrikeThrough || this.textHeight == 0.0f || this.lineRects == null) {
            return;
        }
        canvas.save();
        int i = this.strikeThroughMode;
        if (i == 0) {
            drawDefault(canvas);
        } else if (i == 1) {
            drawAllTogether(canvas);
        }
        canvas.restore();
    }

    private int maxDrawingLineNum(float f) {
        if (this.lineRects == null) {
            return -1;
        }
        float fWidth = 0.0f;
        for (int i = 0; i < this.lineRects.size(); i++) {
            fWidth += this.lineRects.get(i).width();
            if (fWidth >= f) {
                return i;
            }
        }
        return -1;
    }

    private float drawStrikeThroughLine(Canvas canvas, int i) {
        return drawStrikeThroughLine(canvas, i, -1.0f);
    }

    private float drawStrikeThroughLine(Canvas canvas, int i, float f) {
        Rect rect = this.lineRects.get(i);
        float textSize = (this.targetView.getPaint().getFontMetrics().top * (-1.0f)) - (this.targetView.getPaint().getTextSize() * this.strikeThroughBaseLineTop);
        float fHeight = 0.0f;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            fHeight += this.lineRects.get(i).height();
        }
        float f2 = fHeight + textSize;
        if (f == -1.0f) {
            f = rect.width();
        }
        canvas.drawLine(rect.left, f2, rect.left + f, f2, this.paint);
        return f;
    }

    private void drawAllTogether(Canvas canvas) {
        for (int i = 0; i < this.lineRects.size(); i++) {
            drawStrikeThroughLine(canvas, i, this.lineRects.get(i).width() * this.strikeThroughProgress);
        }
    }

    private void drawDefault(Canvas canvas) {
        float f = this.animTotalDistance * this.strikeThroughProgress;
        int iMaxDrawingLineNum = maxDrawingLineNum(f);
        if (iMaxDrawingLineNum < 0) {
            return;
        }
        float fDrawStrikeThroughLine = 0.0f;
        for (int i = 0; i < iMaxDrawingLineNum; i++) {
            fDrawStrikeThroughLine += drawStrikeThroughLine(canvas, i);
        }
        drawStrikeThroughLine(canvas, iMaxDrawingLineNum, f - fDrawStrikeThroughLine);
    }

    private void fillLineRects() {
        int lineCount = this.targetView.getLineCount();
        this.lineRects = new ArrayList();
        Layout layout = this.targetView.getLayout();
        for (int i = 0; i < lineCount; i++) {
            Rect rect = new Rect();
            if (this.strikeThroughCutTextEdge) {
                rect.set((int) layout.getLineLeft(i), layout.getLineTop(i), (int) layout.getLineRight(i), layout.getLineBottom(i));
            } else {
                this.targetView.getLineBounds(i, rect);
            }
            this.lineRects.add(rect);
        }
    }

    private void calcAnimTotalDistance() {
        this.animTotalDistance = 0;
        List<Rect> list = this.lineRects;
        if (list == null) {
            return;
        }
        Iterator<Rect> it = list.iterator();
        while (it.hasNext()) {
            this.animTotalDistance += it.next().width();
        }
    }

    private void getLineSpacing() {
        this.textHeight = 0.0f;
        Paint.FontMetrics fontMetrics = this.targetView.getPaint().getFontMetrics();
        if (fontMetrics == null) {
            return;
        }
        this.textHeight = fontMetrics.bottom - fontMetrics.top;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareAnimation() {
        getLineSpacing();
        fillLineRects();
        if (this.strikeThroughMode != 0) {
            return;
        }
        calcAnimTotalDistance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation() {
        this.strikeThroughProgress = 0.0f;
        this.drawStrikeThrough = true;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                StrikeThroughPainting.this.strikeThroughProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                StrikeThroughPainting.this.targetView.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.common.view.StrikeTextView.StrikeThroughPainting.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (StrikeThroughPainting.this.strikeThroughPaintingCallback != null) {
                    StrikeThroughPainting.this.strikeThroughPaintingCallback.onStrikeThroughEnd();
                }
            }
        });
        valueAnimatorOfFloat.setDuration(this.strikeThroughTotalTime);
        valueAnimatorOfFloat.start();
    }
}
