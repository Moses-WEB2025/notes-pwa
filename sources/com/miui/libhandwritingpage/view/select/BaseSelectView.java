package com.miui.libhandwritingpage.view.select;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.libhandwritingpage.utils.MathUtils;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.ArrayList;
import java.util.List;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.touch.TouchPoint;
import xc.richtext.api.SpannedSelectModel;

/* JADX INFO: loaded from: classes3.dex */
public class BaseSelectView extends AbsSelectView {
    protected boolean enableSelectedContour;
    protected boolean isAiModel;
    public int isAloneDelete;
    public Boolean isDownSelectRect;
    public boolean isReady;
    public boolean isSingleBitmap;
    public ObjectAnimator mAnimator;
    private final Context mContext;
    protected FeatureGesListener mCropGestureListener;
    public int mCurSelectAction;
    protected Path mCurSelectedOutPath;
    protected int mCurvePasteCount;
    public PointF mDownPoint;
    public boolean mIsEdit;
    public boolean mIsLassoPen;
    protected boolean mIsShowButton;
    public boolean mIsSingleText;
    public float mLayoutOffsetX;
    public float mLayoutOffsetY;
    protected RectF mLayoutRectF;
    public List<TouchPoint> mListPoint;
    public SRectF mMultiSelectRectF;
    public SelectViewGroup mMultiSelectViewGroup;
    public List<Point> mOriginTouchPoint;
    protected PageInkModel mPageInkViewModel;
    public PageItemView mPageItemView;
    private int mRadius;
    private Drawable mRotateDrawable;
    protected List<RectF> mRotateList;
    private Drawable mScaleDrawable;
    protected List<RectF> mScaleList;
    public PageTextSelectListener mSelectListener;
    public Path mSelectTouchPath;
    private Paint mShadowPaint;
    public RectF mShapeSelectRectF;
    public boolean mShowSelectPopWindow;
    public SpannedSelectModel mSpannedMode;
    public RectF mSpannedSelectedRectF;
    private final RectF mTempCropRectFRate;
    public List<TouchPosition> mTouchPoints;

    public interface FeatureGesListener {
        void onActionUp();

        void onDown(MotionEvent motionEvent);

        void onMove(MotionEvent motionEvent);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public Bitmap getBitmapBySize(boolean z, int i) {
        return null;
    }

    protected boolean isSelectEnableFollow() {
        return true;
    }

    public BaseSelectView(Context context) {
        super(context);
        this.mOriginTouchPoint = new ArrayList();
        this.mIsShowButton = true;
        this.mRadius = 40;
        this.mTempCropRectFRate = new RectF();
        this.isDownSelectRect = false;
        this.mContext = context;
        this.mCurSelectAction = 0;
        this.mDownPoint = new PointF();
        this.mSelectTouchPath = null;
        this.mCurSelectedOutPath = null;
        this.mSpannedMode = null;
        this.mIsEdit = false;
        this.isSingleBitmap = false;
        this.mIsSingleText = false;
        this.mShowSelectPopWindow = false;
        this.isReady = true;
        this.isAloneDelete = 0;
        this.mListPoint = new ArrayList();
        this.mTouchPoints = new ArrayList();
        this.mLayoutRectF = new RectF();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "offset", 0.0f, 360.0f);
        this.mAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setRepeatCount(-1);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.setDuration(8000L);
        this.mRotateDrawable = getResources().getDrawable(R.drawable.miui_penengine_select_pop_rotate);
        this.mScaleDrawable = getResources().getDrawable(R.drawable.miui_penengine_select_pop_scale);
        Paint paint = new Paint();
        this.mShadowPaint = paint;
        paint.setColor(-1);
        this.mShadowPaint.setShadowLayer(26.666666f, 0.0f, 8.888889f, 671088640);
    }

    public void drawRotateAndScaleButton(Canvas canvas, int i, float[] fArr, Path path, boolean z) {
        if (z && this.mScaleDrawable.isVisible() && this.mRotateDrawable.isVisible()) {
            PointF pointF = new PointF(fArr[2], fArr[3]);
            PointF pointF2 = new PointF(fArr[4], fArr[5]);
            PointF pointF3 = new PointF();
            PointF pointF4 = new PointF();
            float f = pointF2.x - pointF.x;
            float f2 = pointF2.y - pointF.y;
            float fSqrt = (float) (Math.sqrt((f * f) + (f2 * f2)) * Math.sqrt(2.0d));
            if (fSqrt == 0.0f) {
                return;
            }
            pointF3.x = pointF.x - (((f - f2) * 100.0f) / fSqrt);
            float f3 = ((f2 + f) * 100.0f) / fSqrt;
            pointF3.y = pointF.y - f3;
            pointF4.x = pointF2.x + f3;
            pointF4.y = pointF2.y + (((f2 - f) * 100.0f) / fSqrt);
            int i2 = this.mRadius * 2;
            canvas.drawCircle(pointF3.x, pointF3.y, this.mRadius, this.mShadowPaint);
            float f4 = i2 / 2;
            int i3 = (int) (pointF3.x - f4);
            int i4 = (int) (pointF3.y - f4);
            int i5 = i3 + i2;
            int i6 = i4 + i2;
            RectF rectF = new RectF(i3, i4, i5, i6);
            this.mRotateDrawable.setBounds(i3, i4, i5, i6);
            this.mRotateDrawable.draw(canvas);
            canvas.drawCircle(pointF4.x, pointF4.y, this.mRadius, this.mShadowPaint);
            int i7 = (int) (pointF4.x - f4);
            int i8 = (int) (pointF4.y - f4);
            int i9 = i7 + i2;
            int i10 = i2 + i8;
            RectF rectF2 = new RectF(i7, i8, i9, i10);
            this.mScaleDrawable.setBounds(i7, i8, i9, i10);
            this.mScaleDrawable.draw(canvas);
            ArrayList arrayList = new ArrayList();
            arrayList.add(rectF2);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(rectF);
            setSelectMenuSize(arrayList, arrayList2);
        }
    }

    public void setSelectMenuSize(List<RectF> list, List<RectF> list2) {
        this.mScaleList = list;
        this.mRotateList = list2;
    }

    public void setOffset(float f) {
        postInvalidate();
    }

    public void setViewModel(PageItemView pageItemView, RectF rectF) {
        this.mPageItemView = pageItemView;
        this.mLayoutRectF.set(rectF);
        this.mLayoutOffsetX = Math.max(rectF.left, 0.0f);
        this.mLayoutOffsetY = Math.max(rectF.top, 0.0f);
    }

    public void setSelectEnable(boolean z, boolean z2) {
        PageItemView pageItemView;
        this.mIsLassoPen = z;
        this.isAiModel = z2;
        if (z || (pageItemView = this.mPageItemView) == null) {
            return;
        }
        pageItemView.cancelSelect(false);
    }

    public void setTouchEvent(MotionEvent motionEvent) {
        this.mOriginTouchPoint.add(new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mIsShowButton = false;
            this.mOriginTouchPoint.clear();
            this.mOriginTouchPoint.add(new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
            checkDown(motionEvent);
            onDown(motionEvent);
            return;
        }
        if (action != 1) {
            if (action == 2) {
                this.mIsShowButton = false;
                onMove(motionEvent);
                return;
            } else if (action != 3) {
                return;
            }
        }
        this.mIsShowButton = true;
        onUp(motionEvent);
        if (this.mCurSelectAction == 35) {
            this.mCropGestureListener.onActionUp();
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0040  */
    public void checkDown(MotionEvent motionEvent) {
        boolean z;
        RectF rectF = getRectF();
        if (rectF != null && this.mRotateDrawable != null && this.mScaleDrawable != null && motionEvent != null) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (!rectF.contains(x, y)) {
                int i = (int) x;
                int i2 = (int) y;
                z = this.mRotateDrawable.getBounds().contains(i, i2) || this.mScaleDrawable.getBounds().contains(i, i2) || findSelectTypeBy(x, y) == 4;
            }
            this.isDownSelectRect = Boolean.valueOf(z);
            return;
        }
        this.isDownSelectRect = false;
    }

    public void onDown(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.mDownPoint.set(x, y);
        setSpanSelectStatus(this.mShowSelectPopWindow);
        if (isSelectRectNotEmpty()) {
            if (this.mCurSelectAction != 35) {
                setAction(findSelectTypeBy(x, y));
                return;
            } else {
                this.mCropGestureListener.onDown(motionEvent);
                return;
            }
        }
        startSelect(x, y, motionEvent.getEventTime());
    }

    public void startSelect(float f, float f2, long j) {
        setAction(0);
        Path path = new Path();
        this.mSelectTouchPath = path;
        path.moveTo(f, f2);
        this.mTouchPoints.clear();
        PointF pointF = new PointF(f, f2);
        pointF.offset(-this.mLayoutOffsetX, -this.mLayoutOffsetY);
        transformAndScalePointF(pointF);
        this.mTouchPoints.add(new TouchPosition(pointF.x, pointF.y, j));
    }

    private void setSpanSelectStatus(boolean z) {
        SpannedSelectModel spannedSelectModel = this.mSpannedMode;
        if (spannedSelectModel != null) {
            spannedSelectModel.setSelectPopWindowShowing(z);
        }
    }

    public void onMove(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mCurSelectAction == 0 && !isSelectRectNotEmpty()) {
            doSelecting(x, y, motionEvent.getEventTime());
        } else if (this.mCurSelectAction == 35) {
            this.mCropGestureListener.onMove(motionEvent);
        }
        if (this.isDownSelectRect.booleanValue()) {
            postInvalidate();
        }
    }

    public void doSelecting(float f, float f2, long j) {
        Path path = this.mSelectTouchPath;
        if (path != null) {
            path.lineTo(f, f2);
        }
        PointF pointF = new PointF(f, f2);
        pointF.offset(-this.mLayoutOffsetX, -this.mLayoutOffsetY);
        transformAndScalePointF(pointF);
        this.mTouchPoints.add(new TouchPosition(pointF.x, pointF.y, j));
        postInvalidate();
    }

    public void reset() {
        this.mCurSelectedOutPath = null;
        this.mSelectTouchPath = null;
        this.isReady = true;
        setAction(0);
    }

    public boolean isNeedOperateAntLine() {
        if (this.mPageItemView == null) {
            return true;
        }
        int i = this.mCurSelectAction;
        return (i == 4 || i == 16 || i == 8 || i == 28) ? false : true;
    }

    public void onDataCopied(int i) {
        this.mCurvePasteCount = i;
        setAction(0);
    }

    public void onStartAction() {
        this.mShowSelectPopWindow = false;
        PageTextSelectListener pageTextSelectListener = this.mSelectListener;
        if (pageTextSelectListener != null) {
            pageTextSelectListener.dismissSelectPopWindow();
        }
    }

    public void setTextSelectListener(PageTextSelectListener pageTextSelectListener) {
        this.mSelectListener = pageTextSelectListener;
    }

    public boolean isSelectReady() {
        return this.isReady;
    }

    public void setEnableClick(boolean z) {
        PageItemView pageItemView = this.mPageItemView;
        if (pageItemView == null || pageItemView.getCreationEngine() == null) {
            return;
        }
        this.mPageItemView.getCreationEngine().getSelectOperator().setEnablePointSelect(z);
    }

    public void setEnableSpannedClick(boolean z) {
        SpannedSelectModel spannedSelectModel = this.mSpannedMode;
        if (spannedSelectModel != null) {
            spannedSelectModel.setEnableClick(z);
        }
    }

    public void setEnableSpanSelect(boolean z) {
        SpannedSelectModel spannedSelectModel = this.mSpannedMode;
        if (spannedSelectModel != null) {
            spannedSelectModel.setEnableSelect(z);
        }
    }

    public void transformAndScalePointF(PointF pointF) {
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        MathUtils.toTranAndScale(pointF, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate());
        float drawRatio = this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        pointF.x /= drawRatio;
        pointF.y /= drawRatio;
    }

    public void toDrawPointF(PointF pointF) {
        float drawRatio = this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        pointF.x *= drawRatio;
        pointF.y *= drawRatio;
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        MathUtils.inversePoint(pointF, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate());
    }

    public void toOriginPointFIgnoreScale(PointF pointF) {
        float drawRatio = this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        pointF.x *= drawRatio;
        pointF.y *= drawRatio;
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        pointF.x += canvasScaleInfo.getTranslate().x;
        pointF.y += canvasScaleInfo.getTranslate().y;
    }

    public RectF toOriginRectF(RectF rectF) {
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        return MathUtils.toTranMatrix(MathUtils.destFormMatrix(rectF, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate()), canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y);
    }

    public Bitmap toOriginBitmap(Bitmap bitmap) {
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f / canvasScaleInfo.getScaleRate(), 1.0f / canvasScaleInfo.getScaleRate());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void toDrawReactFIgnoreScale(RectF rectF) {
        float drawRatio = this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        rectF.left *= drawRatio;
        rectF.right *= drawRatio;
        rectF.top *= drawRatio;
        rectF.bottom *= drawRatio;
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        RectF tranMatrix = MathUtils.toTranMatrix(rectF, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y);
        rectF.left = tranMatrix.left;
        rectF.right = tranMatrix.right;
        rectF.top = tranMatrix.top;
        rectF.bottom = tranMatrix.bottom;
    }

    public RectF scaleAndTranslateRectF(RectF rectF) {
        ScaleInfo canvasScaleInfo = this.mPageItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        RectF rectFDestFormMatrix = MathUtils.destFormMatrix(rectF, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate());
        float drawRatio = this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        rectFDestFormMatrix.set(rectFDestFormMatrix.left / drawRatio, rectFDestFormMatrix.top / drawRatio, rectFDestFormMatrix.right / drawRatio, rectFDestFormMatrix.bottom / drawRatio);
        return rectFDestFormMatrix;
    }

    public void drawFollowAntLine(Canvas canvas) {
        PageTextSelectListener pageTextSelectListener = this.mSelectListener;
        if (pageTextSelectListener != null) {
            pageTextSelectListener.drawFollowAntLine(canvas, this.mSelectTouchPath);
        }
    }

    public void drawRotateAndScaleButton(Canvas canvas, int i, Path path, View view) {
        PageTextSelectListener pageTextSelectListener = this.mSelectListener;
        if (pageTextSelectListener != null) {
            pageTextSelectListener.drawRotateAndScaleButton(canvas, i, path, view, this.mIsShowButton);
        }
    }

    public void setViewModel(PageInkModel pageInkModel) {
        this.mPageInkViewModel = pageInkModel;
    }

    public void setTouchPositionList(List<TouchPosition> list) {
        this.mTouchPoints = new ArrayList(list);
    }

    public void setViewParent(SelectViewGroup selectViewGroup) {
        this.mMultiSelectViewGroup = selectViewGroup;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public CanvasMaskEntity getBitMapAndMask(boolean z, boolean z2, float f, float f2, int i) {
        SRectF sRectF;
        Bitmap bitmapCreateBitmap;
        Bitmap bitmapCreateBitmap2;
        Bitmap bitmapCreateBitmap3;
        RectF rectF;
        if (this instanceof SelectView) {
            sRectF = new SRectF(this.mMultiSelectRectF.left, this.mMultiSelectRectF.top, this.mMultiSelectRectF.right, this.mMultiSelectRectF.bottom, this.mMultiSelectRectF.getAngle());
            sRectF.setSelectHasCode(this.mMultiSelectRectF.mSelectHasCode);
        } else if (this instanceof ShapeSelectView) {
            sRectF = new SRectF(this.mShapeSelectRectF, 0.0f);
            sRectF.setSelectHasCode(1);
        } else {
            SRectF sRectF2 = new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            sRectF2.setSelectHasCode(1);
            sRectF = sRectF2;
        }
        float f3 = i;
        int i2 = (int) ((sRectF.right - sRectF.left) + 2.0f);
        float f4 = (sRectF.bottom - sRectF.top) + f3 + 2.0f;
        Bitmap bitmapCreateBitmap4 = Bitmap.createBitmap(i2, (int) (f4 - f3), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap4);
        Bitmap bitmapCreateBitmap5 = Bitmap.createBitmap(i2, (int) f4, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap5);
        if (z) {
            canvas.drawColor(this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor);
            canvas2.drawColor(this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor);
        }
        if (this.mSpannedMode != null) {
            rectF = new RectF(this.mSpannedMode.getSelectRectF());
            if (rectF.isEmpty()) {
                bitmapCreateBitmap = null;
                bitmapCreateBitmap2 = null;
                bitmapCreateBitmap3 = null;
            } else {
                bitmapCreateBitmap2 = Bitmap.createBitmap((int) ((rectF.right - rectF.left) + 2.0f), (int) ((rectF.bottom - rectF.top) + 2.0f), Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap3 = Bitmap.createBitmap((int) ((rectF.right - rectF.left) + 2.0f), (int) ((rectF.bottom - rectF.top) + 2.0f), Bitmap.Config.ARGB_8888);
                if (sRectF.mAngle != 0.0f) {
                    this.mSpannedMode.getSelectAndMaskBitmap(bitmapCreateBitmap2, bitmapCreateBitmap3, rectF, 2, this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor, true);
                } else {
                    this.mSpannedMode.getSelectAndMaskBitmap(bitmapCreateBitmap2, bitmapCreateBitmap3, rectF, 2, this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor, z2);
                }
                bitmapCreateBitmap = Bitmap.createBitmap((int) ((rectF.right - rectF.left) + 2.0f), (int) ((rectF.bottom - rectF.top) + 2.0f), Bitmap.Config.ARGB_8888);
                this.mSpannedMode.getSelectBitmap(bitmapCreateBitmap, rectF, 1);
                rectF.offset(this.mLayoutOffsetX - sRectF.left, this.mLayoutOffsetY - sRectF.top);
            }
        } else {
            bitmapCreateBitmap = null;
            bitmapCreateBitmap2 = null;
            bitmapCreateBitmap3 = null;
            rectF = null;
        }
        if (bitmapCreateBitmap2 != null) {
            canvas.drawBitmap(bitmapCreateBitmap2, rectF.left + f, rectF.top + f2, (Paint) null);
            canvas2.drawBitmap(bitmapCreateBitmap3, rectF.left + f, rectF.top + f2, (Paint) null);
        }
        if (this.mPageItemView != null) {
            RectF rectF2 = new RectF(this.mPageItemView.getSelectRect());
            if (!rectF2.isEmpty()) {
                rectF2.offset(this.mLayoutOffsetX - sRectF.left, this.mLayoutOffsetY - sRectF.top);
                Bitmap bitmap = this.mPageItemView.getBitmap();
                canvas.drawBitmap(bitmap, rectF2.left + f, rectF2.top + f2, (Paint) null);
                canvas2.drawBitmap(BitmapUtil.changeBitmapColor(bitmap, this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor), rectF2.left + f, rectF2.top + f2, (Paint) null);
            }
        }
        if (bitmapCreateBitmap != null) {
            canvas.drawBitmap(bitmapCreateBitmap, rectF.left + f, rectF.top + f2, (Paint) null);
            canvas2.drawBitmap(BitmapUtil.changeBitmapColor(bitmapCreateBitmap, this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor), rectF.left + f, rectF.top + f2, (Paint) null);
        }
        CanvasMaskEntity canvasMaskEntity = new CanvasMaskEntity();
        canvasMaskEntity.setContentBitmap(bitmapCreateBitmap4);
        canvasMaskEntity.setMaskBitmap(bitmapCreateBitmap5);
        canvasMaskEntity.setAngle(sRectF.mAngle);
        canvasMaskEntity.setBgColor(this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor);
        return canvasMaskEntity;
    }

    public float[] getSpanData() {
        SpannedSelectModel spannedSelectModel = this.mSpannedMode;
        return spannedSelectModel == null ? new float[10] : spannedSelectModel.getSelectData();
    }

    protected PointF getRotatePoint(float f, float f2, float f3, float f4, float f5) {
        return new PointF(f, f2);
    }
}
