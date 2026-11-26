package xc.engine.operate.rule;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;
import com.miui.richeditor.constant.MiuiFontName;
import defpackage.R2;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.DisplayUtils;
import xc.engine.utils.NumUtil;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class Ruler {
    private final int BOUNDS_MARGIN;
    private boolean mAngleTextShow;
    private float mDegreesDiff360;
    private float mDiagonalLength;
    public IGlobalRulerListener mGlobalRulerListener;
    private boolean mIsAdsorbent;
    public PenEngine mPenEngine;
    private final Paint mRulerBorderPaint;
    private final Paint mRulerFillPaint;
    private final int mRulerHeight;
    private final Paint mRulerStrokePaint;
    private final float mScaleInterval;
    private float mTextBaseToBottom;
    private final TextPaint mTextPaint;
    private float mVisibleHeight;
    private float mVisibleWidth;
    private final Path mRulePath = new Path();
    private final Path mRulePathCopy = new Path();
    private final Path mScaleLine = new Path();
    private final Path mScaleLineCopy = new Path();
    private final Path mTopAdsorbentPath = new Path();
    private final Path mBottomAdsorbentPath = new Path();
    private final PointF mACornerPoint = new PointF();
    private final PointF mBCornerPoint = new PointF();
    private final PointF mRulerTouchPoint = new PointF();
    private final PointF mRulerCenterPoint = new PointF();
    private final Region mLetTopRegion = new Region();
    private final Region mRightTopRegion = new Region();
    private final Region mLeftBottomRegion = new Region();
    private final Region mRightBottomRegion = new Region();
    private float mDegreesDiff = 0.0f;
    private float mAngle = 0.0f;
    private float mPressure = 0.0f;
    private final float mTilt = 0.2f;
    private float mAdsorbentWidth = 24.0f;
    private final RectF mCurrentPageArea = new RectF();
    private final RectF mPathToRect = new RectF();
    private final Region mTouchRulerRegion = new Region();
    private final Region mTouchTopRegion = new Region();
    private final Region mTouchBottomRegion = new Region();
    private final Region mRegion = new Region();
    private final Matrix mTransformMatrix = new Matrix();
    private final float[] defaultPoint = {0.0f, 0.0f, 1.0f, 0.0f};
    private final float[] currentPoint = {0.0f, 0.0f, 0.0f, 0.0f};
    private final float[] mRulerContourCords = new float[8];
    private final float[] mRulerContourCordsCopy = new float[8];
    private final float[] mMeetingPoints = new float[4];
    private final float[] mCenterPointsOffset = new float[2];
    private final RulerTouchHandler mTouchHandler = new RulerTouchHandler(this);
    private final RulerRenderer mRenderer = new RulerRenderer(this);

    private float mapMarkPenTilt(float f) {
        float f2;
        float f3;
        if (f < 0.3f) {
            return f * 0.7f;
        }
        if (f < 0.7f) {
            f2 = (f - 0.3f) * 1.2f;
            f3 = 0.21f;
        } else {
            f2 = (f - 0.7f) * 0.6f;
            f3 = 0.69f;
        }
        return f2 + f3;
    }

    static /* synthetic */ float access$1516(Ruler ruler, float f) {
        float f2 = ruler.mDegreesDiff + f;
        ruler.mDegreesDiff = f2;
        return f2;
    }

    public Ruler(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        int iDip2px = DisplayUtils.dip2px(this.mPenEngine.mContext, 100.0f);
        this.BOUNDS_MARGIN = iDip2px;
        this.mRulerHeight = DisplayUtils.dip2px(this.mPenEngine.mContext, 98.0f);
        this.mVisibleWidth = (int) (this.mPenEngine.getCanvasOperator().getVisibleWidth() + (iDip2px * 2));
        float visibleHeight = (int) (this.mPenEngine.getCanvasOperator().getVisibleHeight() + (iDip2px * 2));
        this.mVisibleHeight = visibleHeight;
        float f = this.mVisibleWidth;
        this.mDiagonalLength = (float) Math.sqrt((f * f) + (visibleHeight * visibleHeight));
        this.mScaleInterval = this.mPenEngine.mScreenInfo.getScaleInterval(1);
        Paint paint = new Paint();
        this.mRulerStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1.0f);
        paint.setColor(Color.argb(R2.array.ai_panel_widget_blend_colors, 0, 0, 0));
        Paint paint2 = new Paint();
        this.mRulerFillPaint = paint2;
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.argb(R2.array.note_sort_way, R2.attr.actionBarButtonIconViewStyle, R2.attr.actionBarButtonIconViewStyle, R2.attr.actionBarButtonIconViewStyle));
        Paint paint3 = new Paint();
        this.mRulerBorderPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setAntiAlias(true);
        paint3.setStrokeWidth(DisplayUtils.dip2px(this.mPenEngine.mContext, 0.5f));
        paint3.setColor(Color.argb(26, 0, 0, 0));
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(-16777216);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(DisplayUtils.dip2px(this.mPenEngine.mContext, 22.0f));
        textPaint.setTypeface(Typeface.create(MiuiFontName.NORMAL, 0));
        initialize();
    }

    private void initialize() {
        char c;
        char c2;
        float f;
        char c3;
        float[] fArr = this.mRulerContourCords;
        int i = this.BOUNDS_MARGIN;
        float f2 = -i;
        fArr[0] = f2;
        float f3 = 2.0f;
        float f4 = this.mVisibleHeight / 2.0f;
        int i2 = this.mRulerHeight;
        float f5 = (f4 - (i2 / 2.0f)) - i;
        fArr[1] = f5;
        float f6 = this.mDiagonalLength + f2 + i;
        char c4 = 2;
        fArr[2] = f6;
        fArr[3] = f5;
        fArr[4] = f6;
        float f7 = f5 + i2;
        char c5 = 5;
        fArr[5] = f7;
        char c6 = 6;
        fArr[6] = f2;
        char c7 = 7;
        fArr[7] = f7;
        System.arraycopy(fArr, 0, this.mRulerContourCordsCopy, 0, 8);
        this.mRulePath.reset();
        Path path = this.mRulePath;
        float[] fArr2 = this.mRulerContourCords;
        path.moveTo(fArr2[0], fArr2[1]);
        Path path2 = this.mRulePath;
        float[] fArr3 = this.mRulerContourCords;
        path2.lineTo(fArr3[2], fArr3[3]);
        Path path3 = this.mRulePath;
        float[] fArr4 = this.mRulerContourCords;
        path3.lineTo(fArr4[4], fArr4[5]);
        Path path4 = this.mRulePath;
        float[] fArr5 = this.mRulerContourCords;
        path4.lineTo(fArr5[6], fArr5[7]);
        this.mRulePath.close();
        this.mRulePath.offset(0.0f, 0.0f, this.mRulePathCopy);
        float[] fArr6 = this.mRulerContourCords;
        float f8 = fArr6[0];
        float f9 = fArr6[1];
        this.mScaleLine.reset();
        int i3 = 0;
        while (true) {
            c = c4;
            c2 = c7;
            if (i3 > (this.mDiagonalLength + this.BOUNDS_MARGIN) / this.mScaleInterval) {
                break;
            }
            this.mScaleLine.moveTo(f8, f9 + f3);
            boolean z = i3 % 10 == 0;
            boolean z2 = i3 % 5 == 0;
            if (z) {
                f = f3;
                c3 = c5;
                this.mScaleLine.lineTo(f8, f9 + 30.0f);
            } else {
                f = f3;
                c3 = c5;
                if (z2) {
                    this.mScaleLine.lineTo(f8, f9 + 18.0f);
                } else {
                    this.mScaleLine.lineTo(f8, f9 + 10.0f);
                }
            }
            float f10 = this.mRulerHeight + f9;
            char c8 = c6;
            this.mScaleLine.moveTo(f8, f10 - f);
            if (z) {
                this.mScaleLine.lineTo(f8, f10 - 30.0f);
            } else if (z2) {
                this.mScaleLine.lineTo(f8, f10 - 18.0f);
            } else {
                this.mScaleLine.lineTo(f8, f10 - 10.0f);
            }
            f8 += this.mScaleInterval;
            i3++;
            c4 = c;
            c7 = c2;
            f3 = f;
            c5 = c3;
            c6 = c8;
        }
        float f11 = f3;
        char c9 = c5;
        char c10 = c6;
        this.mScaleLine.offset(0.0f, 0.0f, this.mScaleLineCopy);
        this.mTopAdsorbentPath.reset();
        Path path5 = this.mTopAdsorbentPath;
        float[] fArr7 = this.mRulerContourCords;
        path5.moveTo(fArr7[0], fArr7[1] - this.mAdsorbentWidth);
        Path path6 = this.mTopAdsorbentPath;
        float[] fArr8 = this.mRulerContourCords;
        path6.lineTo(fArr8[c], fArr8[3] - this.mAdsorbentWidth);
        Path path7 = this.mTopAdsorbentPath;
        float[] fArr9 = this.mRulerContourCords;
        path7.lineTo(fArr9[c], fArr9[3]);
        Path path8 = this.mTopAdsorbentPath;
        float[] fArr10 = this.mRulerContourCords;
        path8.lineTo(fArr10[0], fArr10[1]);
        this.mTopAdsorbentPath.close();
        this.mBottomAdsorbentPath.reset();
        Path path9 = this.mBottomAdsorbentPath;
        float[] fArr11 = this.mRulerContourCords;
        path9.moveTo(fArr11[c10], fArr11[c2]);
        Path path10 = this.mBottomAdsorbentPath;
        float[] fArr12 = this.mRulerContourCords;
        path10.lineTo(fArr12[4], fArr12[c9]);
        Path path11 = this.mBottomAdsorbentPath;
        float[] fArr13 = this.mRulerContourCords;
        path11.lineTo(fArr13[4], fArr13[c9] + this.mAdsorbentWidth);
        Path path12 = this.mBottomAdsorbentPath;
        float[] fArr14 = this.mRulerContourCords;
        path12.lineTo(fArr14[c10], fArr14[c2] + this.mAdsorbentWidth);
        this.mBottomAdsorbentPath.close();
        this.mDegreesDiff360 = 0.0f;
        if (this.mAngle != 0.0f) {
            float[] fArr15 = this.mRulerContourCords;
            float f12 = (fArr15[0] + fArr15[4]) / f11;
            float f13 = (fArr15[1] + fArr15[c9]) / f11;
            this.mTransformMatrix.reset();
            this.mTransformMatrix.postRotate(360.0f - this.mAngle, f12, f13);
            this.mTransformMatrix.mapPoints(this.currentPoint, this.defaultPoint);
            float[] fArr16 = this.currentPoint;
            float fCalculateDegrees = calculateDegrees(fArr16[0] - fArr16[c], fArr16[1] - fArr16[3]);
            float[] fArr17 = this.defaultPoint;
            float fCalculateDegrees2 = fCalculateDegrees - calculateDegrees(fArr17[0] - fArr17[c], fArr17[1] - fArr17[3]);
            this.mDegreesDiff = fCalculateDegrees2;
            float f14 = fCalculateDegrees2 % 360.0f;
            this.mDegreesDiff360 = f14;
            this.mAngle = Math.abs(f14);
            updateCenterPointsOffset();
            Matrix matrix = this.mTransformMatrix;
            float[] fArr18 = this.mCenterPointsOffset;
            matrix.postTranslate(fArr18[0], fArr18[1]);
            this.mTransformMatrix.mapPoints(this.mRulerContourCords, this.mRulerContourCordsCopy);
            System.arraycopy(this.mRulerContourCords, 0, this.mRulerContourCordsCopy, 0, 8);
            this.mRulePath.reset();
            Path path13 = this.mRulePath;
            float[] fArr19 = this.mRulerContourCords;
            path13.moveTo(fArr19[0], fArr19[1]);
            Path path14 = this.mRulePath;
            float[] fArr20 = this.mRulerContourCords;
            path14.lineTo(fArr20[c], fArr20[3]);
            Path path15 = this.mRulePath;
            float[] fArr21 = this.mRulerContourCords;
            path15.lineTo(fArr21[4], fArr21[c9]);
            Path path16 = this.mRulePath;
            float[] fArr22 = this.mRulerContourCords;
            path16.lineTo(fArr22[c10], fArr22[c2]);
            this.mRulePath.close();
            this.mRulePath.offset(0.0f, 0.0f, this.mRulePathCopy);
            this.mScaleLine.transform(this.mTransformMatrix);
            this.mScaleLine.offset(0.0f, 0.0f, this.mScaleLineCopy);
            this.mTopAdsorbentPath.transform(this.mTransformMatrix);
            this.mBottomAdsorbentPath.transform(this.mTransformMatrix);
            this.mTransformMatrix.reset();
        }
        updateTouchRegion();
        float fPow = (float) (((double) (this.mRulerHeight / 2)) * Math.pow(2.0d, 0.5d));
        Path path17 = new Path();
        path17.moveTo(0.0f, 0.0f);
        path17.lineTo(0.0f, fPow);
        path17.lineTo(fPow, 0.0f);
        path17.close();
        path17.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mLetTopRegion.setEmpty();
        this.mLetTopRegion.setPath(path17, this.mRegion);
        Path path18 = new Path();
        path18.moveTo(this.mVisibleWidth - (this.BOUNDS_MARGIN * 2), 0.0f);
        path18.lineTo((this.mVisibleWidth - (this.BOUNDS_MARGIN * 2)) - fPow, 0.0f);
        path18.lineTo(this.mVisibleWidth - (this.BOUNDS_MARGIN * 2), fPow);
        path18.close();
        path18.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mRightTopRegion.setEmpty();
        this.mRightTopRegion.setPath(path18, this.mRegion);
        Path path19 = new Path();
        path19.moveTo(0.0f, this.mVisibleHeight - (this.BOUNDS_MARGIN * 2));
        path19.lineTo(0.0f, (this.mVisibleHeight - (this.BOUNDS_MARGIN * 2)) - fPow);
        path19.lineTo(fPow, this.mVisibleHeight - (this.BOUNDS_MARGIN * 2));
        path19.close();
        path19.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mLeftBottomRegion.setEmpty();
        this.mLeftBottomRegion.setPath(path19, this.mRegion);
        Path path20 = new Path();
        float f15 = this.mVisibleWidth;
        int i4 = this.BOUNDS_MARGIN;
        path20.moveTo(f15 - (i4 * 2), this.mVisibleHeight - (i4 * 2));
        float f16 = this.mVisibleWidth;
        int i5 = this.BOUNDS_MARGIN;
        path20.lineTo(f16 - (i5 * 2), (this.mVisibleHeight - (i5 * 2)) - fPow);
        float f17 = this.mVisibleWidth;
        int i6 = this.BOUNDS_MARGIN;
        path20.lineTo((f17 - (i6 * 2)) - fPow, this.mVisibleHeight - (i6 * 2));
        path20.close();
        path20.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mRightBottomRegion.setEmpty();
        this.mRightBottomRegion.setPath(path20, this.mRegion);
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        this.mTextBaseToBottom = ((fontMetrics.bottom - fontMetrics.top) / f11) - fontMetrics.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateDegrees(float f, float f2) {
        return (float) Math.toDegrees(Math.atan2(f2, f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateDistance(float f, float f2) {
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchRegion() {
        this.mRulePath.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mTouchRulerRegion.setEmpty();
        this.mTouchRulerRegion.setPath(this.mRulePath, this.mRegion);
        this.mTopAdsorbentPath.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mTouchTopRegion.setEmpty();
        this.mTouchTopRegion.setPath(this.mTopAdsorbentPath, this.mRegion);
        this.mBottomAdsorbentPath.computeBounds(this.mPathToRect, true);
        this.mRegion.setEmpty();
        this.mRegion.set(PenEngineUtils.toIntRect(this.mPathToRect));
        this.mTouchBottomRegion.setEmpty();
        this.mTouchBottomRegion.setPath(this.mBottomAdsorbentPath, this.mRegion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to calculate best type for var: r0v1 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v1 ??, new type: float[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v16 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v16 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v16 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v16 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v17 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v17 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v18 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v18 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v19 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v19 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v20 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v20 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v10 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v10 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v11 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v11 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v12 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v12 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v7 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v7 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v8 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v8 ??, new type: float[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v9 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v15 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v15 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v16 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v16 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v17 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v17 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v18 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v18 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v19 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v19 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v20 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v20 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v21 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v21 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v10 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v10 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v13 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v13 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v14 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v14 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v15 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v15 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v16 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v16 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v17 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v17 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v18 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v18 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v5 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v6 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v6 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v7 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v7 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v8 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v8 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v9 ??, new type: float
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r9v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r9v5 ??, new type: float[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r9v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r9v5 ??, new type: float[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v16 ??, new type: float
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
        	... 5 more
        */
    public void updateCenterPointsOffset() {
        /*
            Method dump skipped, instruction units count: 365
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xc.engine.operate.rule.Ruler.updateCenterPointsOffset():void");
    }

    public boolean convertTouchPoint(TouchPoint touchPoint) {
        if (this.mIsAdsorbent) {
            float tilt = this.mPenEngine.mTouchEventOperator.mCurveProp.getPenType() == PenType.MARK ? touchPoint.getTilt() : 0.2f;
            PointF pointF = new PointF(touchPoint.getX(), touchPoint.getY());
            pointF.offset(offsetX(), offsetY());
            PointF pointF2 = new PointF();
            PenEngineUtils.calculateRoundedCornerPoint(this.mACornerPoint, this.mBCornerPoint, pointF, pointF2);
            pointF2.offset(-offsetX(), -offsetY());
            PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(pointF2.x, pointF2.y, this.mPenEngine.mCanvasOperator.getScaleInfo());
            touchPoint.setX(pointFScaleInverseOperation.x);
            touchPoint.setY(pointFScaleInverseOperation.y);
            touchPoint.setTilt(tilt);
            touchPoint.setOrientation(0.5f);
            this.mAdsorbentWidth = 24.0f;
            return true;
        }
        this.mPressure = touchPoint.getPressure();
        PointF pointFScaleInverseOperation2 = ScaleDataUtil.scaleInverseOperation(touchPoint.getX(), touchPoint.getY(), this.mPenEngine.mCanvasOperator.getScaleInfo());
        touchPoint.setX(pointFScaleInverseOperation2.x);
        touchPoint.setY(pointFScaleInverseOperation2.y);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float offsetX() {
        return Math.max(0.0f, this.mCurrentPageArea.left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float offsetY() {
        return Math.max(0.0f, this.mCurrentPageArea.top);
    }

    public void setCurrentPageOffset(RectF rectF) {
        this.mCurrentPageArea.set(rectF);
    }

    public boolean isAdsorbent() {
        return this.mIsAdsorbent;
    }

    public void onSizeChanged(float f, float f2) {
        float f3 = this.mVisibleWidth;
        if (f3 != 0.0f) {
            float f4 = this.mVisibleHeight;
            if (f4 == 0.0f) {
                return;
            }
            int i = this.BOUNDS_MARGIN;
            float f5 = f4 - (i * 2);
            if (Math.abs((f3 - (i * 2)) - f) >= 0.01f || Math.abs(f5 - f2) >= 0.01f) {
                int i2 = this.BOUNDS_MARGIN;
                this.mVisibleWidth = (i2 * 2) + f;
                this.mVisibleHeight = (i2 * 2) + f2;
                this.mDiagonalLength = (float) Math.sqrt((f * f) + (f2 * f2));
                initialize();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mTouchHandler.handleTouch(motionEvent);
    }

    public void onDraw(Canvas canvas) {
        this.mRenderer.draw(canvas);
    }

    private class RulerTouchHandler {
        private boolean isTouchEdgeBottom;
        private boolean isTouchEdgeLeft;
        private boolean isTouchEdgeRight;
        private boolean isTouchEdgeTop;
        private float mDegrees;
        private boolean mIsDownOnRuler;
        private boolean mIsPointerDownOnRuler;
        private final Ruler mRuler;
        private final Matrix mMoveMatrix = new Matrix();
        private final PointF mCurrentDownPoint = new PointF();
        private final PointF mCenterDownPoint = new PointF();
        private final float[] tempCalcCordsA = new float[8];
        private final float[] tempCalcCordsB = new float[4];

        public RulerTouchHandler(Ruler ruler) {
            this.mRuler = ruler;
        }

        public boolean handleTouch(MotionEvent motionEvent) {
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
            motionEventObtain.offsetLocation(Ruler.this.offsetX(), Ruler.this.offsetY());
            int actionMasked = motionEventObtain.getActionMasked();
            if (actionMasked == 0) {
                onActionDown(motionEventObtain);
                return this.mIsDownOnRuler;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    onActionMove(motionEventObtain.getX(), motionEventObtain.getY(), motionEventObtain);
                    if (!this.mIsDownOnRuler || Ruler.this.mGlobalRulerListener == null) {
                        return this.mIsDownOnRuler;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        onPointerDown(motionEventObtain);
                        return this.mIsDownOnRuler;
                    }
                    if (actionMasked == 6) {
                        onPointerUp(motionEventObtain);
                        if (!this.mIsDownOnRuler || Ruler.this.mGlobalRulerListener == null) {
                            return this.mIsDownOnRuler;
                        }
                    } else {
                        return this.mIsDownOnRuler;
                    }
                }
                Ruler.this.mGlobalRulerListener.onGlobalRulerDataChanged();
                return this.mIsDownOnRuler;
            }
            onUpOrCancel(motionEventObtain);
            if (Ruler.this.mGlobalRulerListener == null) {
                return false;
            }
            Ruler.this.mGlobalRulerListener.hasDownOnRuler(false);
            return this.mIsDownOnRuler;
        }

        private void onActionDown(MotionEvent motionEvent) {
            Ruler.this.mTransformMatrix.reset();
            Ruler.this.mIsAdsorbent = false;
            this.mCurrentDownPoint.set(motionEvent.getX(), motionEvent.getY());
            if (Ruler.this.mTouchRulerRegion.contains((int) this.mCurrentDownPoint.x, (int) this.mCurrentDownPoint.y)) {
                Ruler.this.mRulerTouchPoint.set(this.mCurrentDownPoint);
                this.mIsDownOnRuler = true;
            }
            if (Ruler.this.mGlobalRulerListener != null) {
                Ruler.this.mGlobalRulerListener.hasDownOnRuler(this.mIsDownOnRuler);
            }
            if (!this.mIsDownOnRuler && Ruler.this.mPenEngine.mTouchEventOperator.mOperatorMode == 2) {
                if (motionEvent.getToolType(0) == 2) {
                    Ruler.this.mPressure = 0.3f;
                } else {
                    Ruler.this.mPressure = 0.6f;
                }
                float penSize = getPenSize(Ruler.this.mPenEngine.mTouchEventOperator.mCurveProp, Ruler.this.mPenEngine.mCanvasOperator.getDrawRatio(), Ruler.this.mAngle, Ruler.this.mPressure, 0.2f);
                if (!Ruler.this.mTouchTopRegion.contains((int) this.mCurrentDownPoint.x, (int) this.mCurrentDownPoint.y)) {
                    if (Ruler.this.mTouchBottomRegion.contains((int) this.mCurrentDownPoint.x, (int) this.mCurrentDownPoint.y)) {
                        setBottomAdsorbent(penSize);
                        return;
                    }
                    float halfPenWidth = getHalfPenWidth(penSize);
                    if (halfPenWidth > Ruler.this.mAdsorbentWidth) {
                        Ruler.this.mACornerPoint.set(Ruler.this.mRulerContourCords[0], Ruler.this.mRulerContourCords[1]);
                        Ruler.this.mBCornerPoint.set(Ruler.this.mRulerContourCords[2], Ruler.this.mRulerContourCords[3]);
                        PenEngineUtils.calculateRoundedCornerPoint(Ruler.this.mACornerPoint, Ruler.this.mBCornerPoint, this.mCurrentDownPoint, this.mCenterDownPoint);
                        float fCalculateDistance = Ruler.this.calculateDistance(this.mCurrentDownPoint.x - this.mCenterDownPoint.x, this.mCurrentDownPoint.y - this.mCenterDownPoint.y);
                        Ruler.this.mACornerPoint.set(Ruler.this.mRulerContourCords[4], Ruler.this.mRulerContourCords[5]);
                        Ruler.this.mBCornerPoint.set(Ruler.this.mRulerContourCords[6], Ruler.this.mRulerContourCords[7]);
                        PenEngineUtils.calculateRoundedCornerPoint(Ruler.this.mACornerPoint, Ruler.this.mBCornerPoint, this.mCurrentDownPoint, this.mCenterDownPoint);
                        float fCalculateDistance2 = Ruler.this.calculateDistance(this.mCurrentDownPoint.x - this.mCenterDownPoint.x, this.mCurrentDownPoint.y - this.mCenterDownPoint.y);
                        if (fCalculateDistance <= halfPenWidth) {
                            setTopAdsorbent(penSize);
                            return;
                        } else {
                            if (fCalculateDistance2 <= halfPenWidth) {
                                setBottomAdsorbent(penSize);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                setTopAdsorbent(penSize);
            }
        }

        private void onActionMove(float f, float f2, MotionEvent motionEvent) {
            if (!this.mIsDownOnRuler) {
                checkIsAdsorbent(motionEvent);
                return;
            }
            if (!this.mIsPointerDownOnRuler || motionEvent.getPointerCount() <= 1) {
                Ruler.this.mDegreesDiff = 0.0f;
            } else {
                f = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                f2 = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                float fCalculateDegrees = Ruler.this.calculateDegrees(motionEvent.getX(0) - motionEvent.getX(1), motionEvent.getY(0) - motionEvent.getY(1));
                Ruler.this.mDegreesDiff = fCalculateDegrees - this.mDegrees;
                this.mDegrees = fCalculateDegrees;
            }
            float f3 = f - Ruler.this.mRulerTouchPoint.x;
            float f4 = f2 - Ruler.this.mRulerTouchPoint.y;
            if (f3 < 0.0f && this.isTouchEdgeLeft) {
                f3 = 0.0f;
            }
            if (f3 > 0.0f && this.isTouchEdgeRight) {
                f3 = 0.0f;
            }
            if (f4 < 0.0f && this.isTouchEdgeTop) {
                f4 = 0.0f;
            }
            if (f4 > 0.0f && this.isTouchEdgeBottom) {
                f4 = 0.0f;
            }
            Ruler.this.mRulerTouchPoint.set(f, f2);
            Ruler.this.mTransformMatrix.postTranslate(f3, f4);
            Ruler.this.mTransformMatrix.postRotate(Ruler.this.mDegreesDiff, f, f2);
            Ruler.this.mTransformMatrix.mapPoints(Ruler.this.currentPoint, Ruler.this.defaultPoint);
            Ruler ruler = Ruler.this;
            float fCalculateDegrees2 = ruler.calculateDegrees(ruler.currentPoint[0] - Ruler.this.currentPoint[2], Ruler.this.currentPoint[1] - Ruler.this.currentPoint[3]);
            Ruler ruler2 = Ruler.this;
            ruler.mDegreesDiff = fCalculateDegrees2 - ruler2.calculateDegrees(ruler2.defaultPoint[0] - Ruler.this.defaultPoint[2], Ruler.this.defaultPoint[1] - Ruler.this.defaultPoint[3]);
            Ruler.this.mTransformMatrix.mapPoints(Ruler.this.mRulerContourCords, Ruler.this.mRulerContourCordsCopy);
            this.mMoveMatrix.reset();
            Ruler.this.updateCenterPointsOffset();
            this.mMoveMatrix.postTranslate(Ruler.this.mCenterPointsOffset[0], Ruler.this.mCenterPointsOffset[1]);
            float f5 = (Ruler.this.mDegreesDiff360 + Ruler.this.mDegreesDiff) % 180.0f;
            if (Math.abs(Math.abs(f5) - 90.0f) < 1.0f) {
                this.mMoveMatrix.postRotate(Math.abs(f5) - 90.0f, f, f2);
                Ruler.access$1516(Ruler.this, Math.abs(f5) - 90.0f);
            } else if (180.0f - Math.abs(f5) < 1.0f) {
                this.mMoveMatrix.postRotate(Math.abs(f5) - 180.0f, f, f2);
                Ruler.access$1516(Ruler.this, Math.abs(f5) - 180.0f);
            } else if (Math.abs(f5) < 1.0f) {
                this.mMoveMatrix.postRotate(Math.abs(f5), f, f2);
                Ruler.access$1516(Ruler.this, Math.abs(f5));
            }
            this.mMoveMatrix.mapPoints(Ruler.this.mRulerContourCords);
            Ruler.this.mScaleLine.transform(Ruler.this.mTransformMatrix, Ruler.this.mScaleLineCopy);
            Ruler.this.mScaleLineCopy.transform(this.mMoveMatrix);
            Ruler.this.mScaleLineCopy.offset(0.0f, 0.0f);
            Ruler.this.mRulePath.transform(Ruler.this.mTransformMatrix, Ruler.this.mRulePathCopy);
            Ruler.this.mRulePathCopy.transform(this.mMoveMatrix);
            Ruler ruler3 = Ruler.this;
            ruler3.mAngle = Math.abs((ruler3.mDegreesDiff360 + Ruler.this.mDegreesDiff) % 360.0f);
            if (this.mIsPointerDownOnRuler) {
                Ruler.this.mACornerPoint.set((Ruler.this.mRulerContourCords[0] + Ruler.this.mRulerContourCords[6]) / 2.0f, (Ruler.this.mRulerContourCords[1] + Ruler.this.mRulerContourCords[7]) / 2.0f);
                Ruler.this.mBCornerPoint.set((Ruler.this.mRulerContourCords[2] + Ruler.this.mRulerContourCords[4]) / 2.0f, (Ruler.this.mRulerContourCords[3] + Ruler.this.mRulerContourCords[5]) / 2.0f);
                PenEngineUtils.calculateRoundedCornerPoint(Ruler.this.mACornerPoint, Ruler.this.mBCornerPoint, Ruler.this.mRulerTouchPoint, Ruler.this.mRulerCenterPoint);
                Ruler.this.mAngleTextShow = true;
            } else {
                Ruler.this.mAngleTextShow = false;
            }
            float f6 = (Ruler.this.mRulerContourCords[0] + Ruler.this.mRulerContourCords[4]) / 2.0f;
            float f7 = (Ruler.this.mRulerContourCords[1] + Ruler.this.mRulerContourCords[5]) / 2.0f;
            this.isTouchEdgeLeft = f6 <= 0.0f;
            this.isTouchEdgeRight = f6 >= Ruler.this.mVisibleWidth - ((float) (Ruler.this.BOUNDS_MARGIN * 2));
            this.isTouchEdgeTop = f7 <= 0.0f;
            this.isTouchEdgeBottom = f7 >= Ruler.this.mVisibleHeight - ((float) (Ruler.this.BOUNDS_MARGIN * 2));
            int i = (int) f6;
            int i2 = (int) f7;
            if (Ruler.this.mLetTopRegion.contains(i, i2)) {
                this.isTouchEdgeLeft = true;
                this.isTouchEdgeTop = true;
            }
            if (Ruler.this.mRightTopRegion.contains(i, i2)) {
                this.isTouchEdgeRight = true;
                this.isTouchEdgeTop = true;
            }
            if (Ruler.this.mLeftBottomRegion.contains(i, i2)) {
                this.isTouchEdgeLeft = true;
                this.isTouchEdgeBottom = true;
            }
            if (Ruler.this.mRightBottomRegion.contains(i, i2)) {
                this.isTouchEdgeRight = true;
                this.isTouchEdgeBottom = true;
            }
        }

        private void checkIsAdsorbent(MotionEvent motionEvent) {
            if (!Ruler.this.mIsAdsorbent && Ruler.this.mPenEngine.mTouchEventOperator.mOperatorMode == 2) {
                Ruler.this.mAdsorbentWidth = 5.0f;
                this.tempCalcCordsB[0] = this.mCurrentDownPoint.x;
                this.tempCalcCordsB[1] = this.mCurrentDownPoint.y;
                this.tempCalcCordsB[2] = motionEvent.getX();
                this.tempCalcCordsB[3] = motionEvent.getY();
                System.arraycopy(Ruler.this.mRulerContourCords, 0, this.tempCalcCordsA, 0, 8);
                Ruler.this.mTransformMatrix.reset();
                Ruler.this.mTransformMatrix.postRotate(-Ruler.this.mDegreesDiff360, (Ruler.this.mRulerContourCords[0] + Ruler.this.mRulerContourCords[4]) / 2.0f, (Ruler.this.mRulerContourCords[1] + Ruler.this.mRulerContourCords[5]) / 2.0f);
                Ruler.this.mTransformMatrix.mapPoints(this.tempCalcCordsA);
                Ruler.this.mTransformMatrix.mapPoints(this.tempCalcCordsB);
                Ruler.this.mTransformMatrix.reset();
                float penSize = getPenSize(Ruler.this.mPenEngine.mTouchEventOperator.mCurveProp, Ruler.this.mPenEngine.mCanvasOperator.getDrawRatio(), Ruler.this.mAngle, Ruler.this.mPressure, 0.2f);
                float halfPenWidth = getHalfPenWidth(penSize);
                float fMax = this.tempCalcCordsA[1] - Math.max(Ruler.this.mAdsorbentWidth, halfPenWidth);
                float fMax2 = this.tempCalcCordsA[5] + Math.max(Ruler.this.mAdsorbentWidth, halfPenWidth);
                float[] fArr = this.tempCalcCordsB;
                float f = fArr[1];
                if (f < fMax && fArr[3] > fMax) {
                    setTopAdsorbent(penSize);
                } else {
                    if (f <= fMax2 || fArr[3] >= fMax2) {
                        return;
                    }
                    setBottomAdsorbent(penSize);
                }
            }
        }

        private float getHalfPenWidth(float f) {
            return (f * Ruler.this.mPenEngine.mCanvasOperator.getScaleInfo().getScaleRate()) / 2.0f;
        }

        private float getPenSize(CurveProp curveProp, float f, float f2, float f3, float f4) {
            double d;
            float f5;
            PenType penType = curveProp.getPenType();
            float penSize = curveProp.getPenSize();
            if (penType == PenType.INK) {
                float f6 = 0.05f * penSize;
                float sensitivity = curveProp.getSensitivity();
                float tipSize = curveProp.getTipSize();
                float fPow = f6 + ((penSize - f6) * ((float) Math.pow(f3, 1.5d - ((double) sensitivity))));
                if (tipSize > penSize) {
                    tipSize = penSize / 3.0f;
                }
                return Math.max(tipSize * f, Math.max(2.0f, fPow * f)) + 1.0f;
            }
            if (penType == PenType.BALL) {
                return Math.max(2.0f, penSize * f) + 0.5f;
            }
            if (penType == PenType.PENCIL) {
                float f7 = 0.5f * penSize;
                return Math.max(2.0f, (f7 + ((float) (((double) (penSize - f7)) * Math.cos(((double) (curveProp.getSensitivity() * f4)) - 1.5707963267948966d) * 2.0d))) * f);
            }
            if (penType != PenType.MARK) {
                return penType == PenType.WATER_COLOR ? Math.max(2.0f, penSize * f * f3) : penSize;
            }
            float fMax = Math.max(2.0f, penSize * f);
            float f8 = f2 % 180.0f;
            float f9 = fMax / 2.5f;
            if (Float.compare(f8, 90.0f) > 0) {
                d = 180.0f - f8;
                f5 = fMax / 3.0f;
            } else {
                d = f8;
                f5 = fMax / 2.0f;
            }
            return ((float) ((((double) fMax) * Math.cos(Math.toRadians(d))) + (((double) f9) * Math.sin(Math.toRadians(d))))) - (((float) (Math.sqrt((f5 * f5) * 2.0f) * Math.sin(Math.toRadians(d > 45.0d ? 45.0d + d : 45.0d + (90.0d - d))))) - f5);
        }

        private void setTopAdsorbent(float f) {
            Ruler.this.mIsAdsorbent = true;
            float fSin = (float) Math.sin(Math.toRadians(Ruler.this.mAngle));
            float fCos = (float) Math.cos(Math.toRadians(Ruler.this.mAngle));
            Ruler.this.mACornerPoint.set(Ruler.this.mRulerContourCords[0] - (getHalfPenWidth(f) * fSin), Ruler.this.mRulerContourCords[1] - (getHalfPenWidth(f) * fCos));
            Ruler.this.mBCornerPoint.set(Ruler.this.mRulerContourCords[2] - (getHalfPenWidth(f) * fSin), Ruler.this.mRulerContourCords[3] - (getHalfPenWidth(f) * fCos));
        }

        private void setBottomAdsorbent(float f) {
            Ruler.this.mIsAdsorbent = true;
            float fSin = (float) Math.sin(Math.toRadians(Ruler.this.mAngle));
            float fCos = (float) Math.cos(Math.toRadians(Ruler.this.mAngle));
            Ruler.this.mACornerPoint.set(Ruler.this.mRulerContourCords[4] + (getHalfPenWidth(f) * fSin), Ruler.this.mRulerContourCords[5] + (getHalfPenWidth(f) * fCos));
            Ruler.this.mBCornerPoint.set(Ruler.this.mRulerContourCords[6] + (getHalfPenWidth(f) * fSin), Ruler.this.mRulerContourCords[7] + (getHalfPenWidth(f) * fCos));
        }

        private void onUpOrCancel(MotionEvent motionEvent) {
            this.mIsPointerDownOnRuler = false;
            Ruler.this.mAngleTextShow = false;
            Ruler.this.mAdsorbentWidth = 24.0f;
            if (this.mIsDownOnRuler) {
                Ruler ruler = Ruler.this;
                ruler.mDegreesDiff360 = (ruler.mDegreesDiff360 + Ruler.this.mDegreesDiff) % 360.0f;
                System.arraycopy(Ruler.this.mRulerContourCords, 0, Ruler.this.mRulerContourCordsCopy, 0, 8);
                Ruler.this.mScaleLine.transform(Ruler.this.mTransformMatrix);
                Ruler.this.mScaleLine.transform(this.mMoveMatrix);
                Ruler.this.mScaleLine.offset(0.0f, 0.0f, Ruler.this.mScaleLineCopy);
                Ruler.this.mRulePath.reset();
                Ruler.this.mRulePath.moveTo(Ruler.this.mRulerContourCords[0], Ruler.this.mRulerContourCords[1]);
                Ruler.this.mRulePath.lineTo(Ruler.this.mRulerContourCords[2], Ruler.this.mRulerContourCords[3]);
                Ruler.this.mRulePath.lineTo(Ruler.this.mRulerContourCords[4], Ruler.this.mRulerContourCords[5]);
                Ruler.this.mRulePath.lineTo(Ruler.this.mRulerContourCords[6], Ruler.this.mRulerContourCords[7]);
                Ruler.this.mRulePath.close();
                Ruler.this.mRulePathCopy.set(Ruler.this.mRulePath);
                Ruler.this.mTopAdsorbentPath.transform(Ruler.this.mTransformMatrix);
                Ruler.this.mTopAdsorbentPath.transform(this.mMoveMatrix);
                Ruler.this.mBottomAdsorbentPath.transform(Ruler.this.mTransformMatrix);
                Ruler.this.mBottomAdsorbentPath.transform(this.mMoveMatrix);
                Ruler.this.updateTouchRegion();
                this.mMoveMatrix.reset();
                Ruler.this.mDegreesDiff = 0.0f;
                Ruler.this.mTransformMatrix.reset();
                Ruler.this.defaultPoint[0] = 0.0f;
                Ruler.this.defaultPoint[1] = 0.0f;
                Ruler.this.defaultPoint[2] = 1.0f;
                Ruler.this.defaultPoint[3] = 0.0f;
            }
            this.mIsDownOnRuler = false;
        }

        private void onPointerDown(MotionEvent motionEvent) {
            if (this.mIsDownOnRuler) {
                Ruler.this.mRulerTouchPoint.x = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                Ruler.this.mRulerTouchPoint.y = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                this.mDegrees = Ruler.this.calculateDegrees(motionEvent.getX(0) - motionEvent.getX(1), motionEvent.getY(0) - motionEvent.getY(1));
                this.mIsPointerDownOnRuler = true;
            }
        }

        private void onPointerUp(MotionEvent motionEvent) {
            this.mIsPointerDownOnRuler = false;
            Ruler.this.mAngleTextShow = false;
            int actionIndex = motionEvent.getActionIndex();
            if (actionIndex == 0) {
                Ruler.this.mRulerTouchPoint.x = motionEvent.getX(1);
                Ruler.this.mRulerTouchPoint.y = motionEvent.getY(1);
                return;
            }
            if (actionIndex == 1) {
                Ruler.this.mRulerTouchPoint.x = motionEvent.getX(0);
                Ruler.this.mRulerTouchPoint.y = motionEvent.getY(0);
            }
        }
    }

    private class RulerRenderer {
        private final Ruler mRuler;

        public RulerRenderer(Ruler ruler) {
            this.mRuler = ruler;
        }

        public void draw(Canvas canvas) {
            canvas.save();
            canvas.drawPath(Ruler.this.mRulePathCopy, Ruler.this.mRulerFillPaint);
            canvas.drawLine(Ruler.this.mRulerContourCords[0], Ruler.this.mRulerContourCords[1], Ruler.this.mRulerContourCords[2], Ruler.this.mRulerContourCords[3], Ruler.this.mRulerBorderPaint);
            canvas.drawLine(Ruler.this.mRulerContourCords[4], Ruler.this.mRulerContourCords[5], Ruler.this.mRulerContourCords[6], Ruler.this.mRulerContourCords[7], Ruler.this.mRulerBorderPaint);
            canvas.drawPath(Ruler.this.mScaleLineCopy, Ruler.this.mRulerStrokePaint);
            if (Ruler.this.mAngleTextShow) {
                canvas.save();
                canvas.translate(NumUtil.round(Ruler.this.mRulerCenterPoint.x), NumUtil.round(Ruler.this.mRulerCenterPoint.y));
                int i = (int) ((Ruler.this.mAngle % 180.0f) + 0.5f);
                canvas.drawText(String.format("%d°", Integer.valueOf(i != 180 ? i : 0)), 0.0f, Ruler.this.mTextBaseToBottom, Ruler.this.mTextPaint);
                canvas.rotate(Ruler.this.mAngle);
                canvas.restore();
            }
            canvas.restore();
        }
    }
}
