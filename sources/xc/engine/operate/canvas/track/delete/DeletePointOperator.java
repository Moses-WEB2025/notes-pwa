package xc.engine.operate.canvas.track.delete;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.algorithm.recognize.data.RecognizeData;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.curve.info.CurveInfo;
import xc.engine.core.graphics.pen.BasePenPropConfig;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CsPoint;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.curve.ICurvePointPosition;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.DataShape;
import xc.engine.math.iink.manager.EraserOperationManager;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.IOperator;
import xc.engine.operate.step.type.IStep;
import xc.engine.operate.step.type.StepDeleteLine;
import xc.engine.operate.step.type.StepDeletePoint;
import xc.engine.operate.touch.HoverPoint;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.XLog;

/* JADX INFO: loaded from: classes6.dex */
public class DeletePointOperator extends CurveInfo implements IOperator {
    private static final String TAG = "DeletePointOperator";
    private CanvasOperator mCanvasOperator;
    private CurveProp mCurveProp;
    private DataCurve mDataCurve;
    private DeleteProp mDeleteProp;
    private GLFrontBufferedWrapperRenderer mGlFastRenderer;
    private long mLastTouchPointTime;
    private final PenEngine mPenEngine;
    private final RectF tempDelBound = new RectF();
    private final PointF startTouchPoint = new PointF();
    private final RectF rectFA = new RectF();
    private long mRefreshInterval = 20;
    private final RectF delPointsRectUnion = new RectF();
    private Region mTouchPointDelAreaIntersect = new Region();
    private List<DataCurve> intersectDataCurveList = new ArrayList();
    private final RectF mNeedRefreshRect = new RectF();
    private final List<DataCurve> mStepIntersectDataCurveList = new ArrayList();
    private final EraserOperationManager eraserOperationManager = EraserOperationManager.getInstance();
    private float erasingMaxPenSize = 0.0f;

    @Override // xc.engine.operate.canvas.track.IOperator
    public void applyAdsorbent(boolean z) {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverExit() {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverMove(HoverPoint hoverPoint, boolean z, float f, int i) {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDraw(Canvas canvas) {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void setEraseTime(long j) {
    }

    public DeletePointOperator(PenEngine penEngine, DeleteProp deleteProp, boolean z, CurveProp curveProp) {
        this.mPenEngine = penEngine;
        enableScale(z);
        init(curveProp);
        this.mDeleteProp = deleteProp;
    }

    private void init(CurveProp curveProp) {
        this.mTouchPointDelAreaIntersect = new Region();
        this.intersectDataCurveList = new ArrayList();
        if (PenEngineUtils.isSmallPad()) {
            this.mRefreshInterval = 100L;
        }
        this.mCurveProp = curveProp;
        this.mCanvasOperator = this.mPenEngine.getCanvasOperator();
        this.mDataCurve = new DataCurve(this.mCurveProp, System.currentTimeMillis());
        ScaleInfo scaleInfo = this.mCanvasOperator.getScaleInfo();
        this.mDataCurve.setValue(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), this.mCanvasOperator.getDrawRatio());
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer = this.mPenEngine.mFrontBufferedRenderProcessor.getMFrontBufferRenderer();
        }
    }

    private void processPointsErasing(boolean z, TouchPoint touchPoint) {
        this.intersectDataCurveList.clear();
        DeleteUtils.calDeleteRegion(this.tempDelBound, this.startTouchPoint, touchPoint, this.mDeleteProp.getDeleteRadius(), this.mTouchPointDelAreaIntersect, false, 0.0f);
        if (needDelPoint(touchPoint.getX(), touchPoint.getY(), this.tempDelBound)) {
            this.mStepIntersectDataCurveList.removeAll(this.intersectDataCurveList);
            this.mStepIntersectDataCurveList.addAll(this.intersectDataCurveList);
        }
        if (z) {
            if (this.mStepIntersectDataCurveList.isEmpty()) {
                this.mNeedRefreshRect.set(this.delPointsRectUnion);
            } else {
                this.mNeedRefreshRect.union(this.delPointsRectUnion);
            }
            this.delPointsRectUnion.setEmpty();
            this.mPenEngine.getCurveEngine().getCurveSystem().updateAreaLocallyForEraser(new RectF(this.tempDelBound), this.erasingMaxPenSize);
            this.erasingMaxPenSize = 0.0f;
        }
    }

    private void splitCurves() {
        IStep stepDeletePoint;
        Iterator<DataCurve> it;
        int size;
        RectF rectF;
        PointF pointF;
        ArrayList<DataCurve> arrayList = new ArrayList();
        Iterator<DataCurve> it2 = this.mStepIntersectDataCurveList.iterator();
        while (it2.hasNext()) {
            DataCurve next = it2.next();
            if (next instanceof DataShape) {
                it = it2;
            } else {
                float f = 1.0f / next.mDrawRatio;
                ArrayList arrayList2 = new ArrayList(8);
                ICurvePointList iCurvePointListNewCurvePointList = CurvePointListOperator.newCurvePointList(next.mOriginPoints);
                ArrayList arrayList3 = new ArrayList(4);
                int size2 = iCurvePointListNewCurvePointList.size();
                int i = 0;
                int i2 = 0;
                DataCurve dataCurve = null;
                RectF rectF2 = null;
                while (i < size2) {
                    CsPoint csPoint = new CsPoint(iCurvePointListNewCurvePointList.get(i));
                    if (csPoint.getDataState() == 1) {
                        size2 = size2;
                        rectF = rectF2;
                        if (dataCurve == null) {
                            DataCurve dataCurve2 = new DataCurve(next.getCurveProp(), next.mTime);
                            dataCurve2.mDrawRatio = next.mDrawRatio;
                            dataCurve2.mPenTypeValue = next.mPenTypeValue;
                            dataCurve2.mRotateDegrees = next.mRotateDegrees;
                            dataCurve2.mTableFlag = next.mTableFlag;
                            DataSet dataSet = this.mPenEngine.mDataAccess.getDataSet();
                            dataCurve2.mId = dataSet.mId;
                            dataSet.addId(dataCurve2.mId);
                            dataCurve2.mCount = next.mCount;
                            dataCurve2.r = next.r;
                            dataCurve2.e = -1;
                            dataCurve2.mScaleRate = this.mPenEngine.getCanvasOperator().getScaleInfo().getScaleRate();
                            dataCurve2.mCurveProp.setSensitivity(next.mCurveProp.getSensitivity());
                            dataCurve2.mCurveProp.setTipSize(next.mCurveProp.getTipSize());
                            arrayList2.add(dataCurve2);
                            if (next.mStartPoint == null) {
                                CsPoint csPoint2 = new CsPoint(CurvePointListOperator.newCurvePointList(next.mOriginPoints).get(0));
                                pointF = new PointF(csPoint2.getX() * f, csPoint2.getY() * f);
                            } else {
                                pointF = new PointF(next.mStartPoint.x, next.mStartPoint.y);
                            }
                            dataCurve2.mStartPoint = pointF;
                            it2 = it2;
                            dataCurve2.insertPoint(dataCurve2.mOriginPoints, csPoint.getX(), csPoint.getY(), csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), csPoint.getVelocity(), csPoint.getTime(), (short) csPoint.getDataState(), csPoint.getRotateAngle());
                            CsPoint csPointClonePointWithScale = PenEngineUtils.clonePointWithScale(csPoint, f);
                            dataCurve2.insertPoint(dataCurve2.mSavePoints, csPointClonePointWithScale.getX(), csPointClonePointWithScale.getY(), csPointClonePointWithScale.getPressure(), csPointClonePointWithScale.getTilt(), csPointClonePointWithScale.getOrientation(), csPointClonePointWithScale.getVelocity(), csPointClonePointWithScale.getTime(), (short) csPointClonePointWithScale.getDataState(), csPointClonePointWithScale.getRotateAngle());
                            rectF2 = new RectF(csPointClonePointWithScale.getX(), csPointClonePointWithScale.getY(), csPointClonePointWithScale.getX(), csPointClonePointWithScale.getY());
                            i2++;
                            dataCurve = dataCurve2;
                        } else {
                            dataCurve.insertPoint(dataCurve.mOriginPoints, csPoint.getX(), csPoint.getY(), csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), csPoint.getVelocity(), csPoint.getTime(), (short) csPoint.getDataState(), csPoint.getRotateAngle());
                            if (i2 % 2 == 0) {
                                CsPoint csPointClonePointWithScale2 = PenEngineUtils.clonePointWithScale(csPoint, f);
                                dataCurve.insertPoint(dataCurve.mSavePoints, csPointClonePointWithScale2.getX(), csPointClonePointWithScale2.getY(), csPointClonePointWithScale2.getPressure(), csPointClonePointWithScale2.getTilt(), csPointClonePointWithScale2.getOrientation(), csPointClonePointWithScale2.getVelocity(), csPointClonePointWithScale2.getTime(), (short) csPointClonePointWithScale2.getDataState(), csPointClonePointWithScale2.getRotateAngle());
                                rectF.union(csPointClonePointWithScale2.getX(), csPointClonePointWithScale2.getY());
                                PenEngineUtils.processTempPointsAndUpdateBounds(dataCurve, rectF, arrayList3, f);
                            } else {
                                arrayList3.add(csPoint);
                            }
                            i2++;
                        }
                        i++;
                        size2 = size2;
                        it2 = it2;
                    } else if (i2 > 0) {
                        CsPoint csPointClonePointWithScale3 = PenEngineUtils.clonePointWithScale(new CsPoint(CurvePointListOperator.newCurvePointList(dataCurve.mOriginPoints).get(i2 - 1)), f);
                        rectF = rectF2;
                        dataCurve.insertPoint(dataCurve.mSavePoints, csPointClonePointWithScale3.getX(), csPointClonePointWithScale3.getY(), csPointClonePointWithScale3.getPressure(), csPointClonePointWithScale3.getTilt(), csPointClonePointWithScale3.getOrientation(), csPointClonePointWithScale3.getVelocity(), csPointClonePointWithScale3.getTime(), (short) csPointClonePointWithScale3.getDataState(), csPointClonePointWithScale3.getRotateAngle());
                        rectF.union(csPointClonePointWithScale3.getX(), csPointClonePointWithScale3.getY());
                        PenEngineUtils.processTempPointsAndUpdateBounds(dataCurve, rectF, arrayList3, f);
                        dataCurve.initRectF(rectF);
                        i2 = 0;
                        dataCurve = null;
                    } else {
                        rectF = rectF2;
                    }
                    rectF2 = rectF;
                    i++;
                    size2 = size2;
                    it2 = it2;
                }
                it = it2;
                RectF rectF3 = rectF2;
                if (i2 > 0) {
                    CsPoint csPointClonePointWithScale4 = PenEngineUtils.clonePointWithScale(new CsPoint(CurvePointListOperator.newCurvePointList(dataCurve.mOriginPoints).get(i2 - 1)), f);
                    dataCurve.insertPoint(dataCurve.mSavePoints, csPointClonePointWithScale4.getX(), csPointClonePointWithScale4.getY(), csPointClonePointWithScale4.getPressure(), csPointClonePointWithScale4.getTilt(), csPointClonePointWithScale4.getOrientation(), csPointClonePointWithScale4.getVelocity(), csPointClonePointWithScale4.getTime(), (short) csPointClonePointWithScale4.getDataState(), csPointClonePointWithScale4.getRotateAngle());
                    rectF3.union(csPointClonePointWithScale4.getX(), csPointClonePointWithScale4.getY());
                    PenEngineUtils.processTempPointsAndUpdateBounds(dataCurve, rectF3, arrayList3, f);
                    dataCurve.initRectF(rectF3);
                }
                if (RecognizeData.isClose(next.mCurveType)) {
                    CsPoint csPoint3 = new CsPoint(CurvePointListOperator.newCurvePointList(next.mOriginPoints).get(0));
                    CsPoint csPoint4 = new CsPoint(CurvePointListOperator.newCurvePointList(next.mOriginPoints).get(CurvePointListOperator.newCurvePointList(next.mOriginPoints).size() - 1));
                    if (csPoint3.getDataState() != 3 && csPoint4.getDataState() != 3 && (size = arrayList2.size()) >= 2) {
                        DataCurve dataCurve3 = (DataCurve) arrayList2.get(0);
                        DataCurve dataCurve4 = (DataCurve) arrayList2.get(size - 1);
                        dataCurve4.addAllPoints(dataCurve4.mSavePoints, dataCurve3.mSavePoints);
                        dataCurve4.addAllPoints(dataCurve4.mOriginPoints, dataCurve3.mOriginPoints);
                        dataCurve4.initRectF(PenEngineUtils.calculateUnionRectF(dataCurve4.getSaveRect(), dataCurve3.getSaveRect()));
                        dataCurve3.clearPoints(dataCurve3.mSavePoints);
                        dataCurve3.mDataState = 3;
                        dataCurve3.clearPoints(dataCurve3.mOriginPoints);
                        arrayList2.remove(dataCurve3);
                        dataCurve3.releasePointList();
                    }
                }
                arrayList.addAll(arrayList2);
            }
            it2 = it;
        }
        if (arrayList.size() != 0) {
            for (DataCurve dataCurve5 : arrayList) {
                dataCurve5.clearPoints(dataCurve5.mOriginPoints);
            }
        }
        for (DataCurve dataCurve6 : this.mStepIntersectDataCurveList) {
            if (!(dataCurve6 instanceof DataShape)) {
                dataCurve6.mDataState = 3;
                CurvePointListOperator.resetPointsState(CurvePointListOperator.createCurvePointsPosition(dataCurve6.mOriginPoints).getCurvePointList());
                float curvePenWidth = dataCurve6.getCurvePenWidth() * dataCurve6.mDrawRatio;
                RectF rectF4 = new RectF(dataCurve6.getSaveRect());
                rectF4.left *= dataCurve6.mDrawRatio;
                rectF4.right *= dataCurve6.mDrawRatio;
                rectF4.top *= dataCurve6.mDrawRatio;
                rectF4.bottom *= dataCurve6.mDrawRatio;
                RectF rectF5 = new RectF(rectF4.left - curvePenWidth, rectF4.top - curvePenWidth, rectF4.right + curvePenWidth, rectF4.bottom + curvePenWidth);
                if (this.rectFA.isEmpty()) {
                    this.rectFA.set(rectF5);
                } else {
                    this.rectFA.union(rectF5);
                }
            }
        }
        if (this.mStepIntersectDataCurveList.size() == 0) {
            return;
        }
        RectF rectF6 = new RectF();
        Iterator<DataCurve> it3 = this.mStepIntersectDataCurveList.iterator();
        while (it3.hasNext()) {
            rectF6.union(it3.next().mRect);
        }
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        int iAddUnstableArea = this.mPenEngine.getCurveEngine().getCurveSystem().addUnstableArea(new RectF(rectF6.left / saveRatio, rectF6.top / saveRatio, rectF6.right / saveRatio, rectF6.bottom / saveRatio));
        this.rectFA.union(this.mPenEngine.getCurveDataManager().removeDataAndGetRectF(this.mStepIntersectDataCurveList));
        if (arrayList.isEmpty()) {
            stepDeletePoint = new StepDeleteLine(this.mPenEngine, this.mStepIntersectDataCurveList);
        } else {
            Iterator it4 = arrayList.iterator();
            while (it4.hasNext()) {
                this.mPenEngine.mDataAccess.getDataSet().insertDataCurve((DataCurve) it4.next());
            }
            this.mPenEngine.getCurveDataManager().addDataAndGetRectF(arrayList);
            this.mPenEngine.updateDataCurvePageIndex(this.mStepIntersectDataCurveList);
            this.mPenEngine.updateDataCurvePageIndex(arrayList);
            stepDeletePoint = new StepDeletePoint(this.mPenEngine, this.mStepIntersectDataCurveList, arrayList);
        }
        this.mPenEngine.getCurveEngine().getCurveSystem().removeUnstableArea(iAddUnstableArea);
        this.mPenEngine.mStepManager.addStep(stepDeletePoint);
        this.mStepIntersectDataCurveList.clear();
        arrayList.clear();
    }

    private void setPenPropConfig(BasePenPropConfig basePenPropConfig, DataCurve dataCurve) {
        basePenPropConfig.updateProps(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        basePenPropConfig.mSensitivity = dataCurve.mCurveProp.getSensitivity();
        basePenPropConfig.mTipSize = dataCurve.mCurveProp.getTipSize();
    }

    private boolean needDelPoint(float f, float f2, RectF rectF) {
        Iterator<DataCurve> it;
        Rect rect;
        ArrayList arrayList;
        boolean z;
        ICurvePointPosition iCurvePointPositionCreateCurvePointsPosition;
        BasePenPropConfig basePenPropConfig;
        ICurvePointList iCurvePointList;
        ICurvePointPosition iCurvePointPosition;
        float fCalculatePenSize;
        float f3;
        float f4;
        float fCalculatePenSize2;
        int i;
        float saveRatio = this.mPenEngine.getCanvasOperator().getSaveRatio();
        List<DataCurve> listScanIntersectDataCurve = ((CurveDataManager) this.mPenEngine.getCurveDataManager()).scanIntersectDataCurve(new RectF(rectF.left * saveRatio, rectF.top * saveRatio, rectF.right * saveRatio, rectF.bottom * saveRatio), false);
        Rect bounds = this.mTouchPointDelAreaIntersect.getBounds();
        ArrayList arrayList2 = new ArrayList();
        Iterator<DataCurve> it2 = listScanIntersectDataCurve.iterator();
        boolean z2 = false;
        while (it2.hasNext()) {
            DataCurve next = it2.next();
            if (next.getType() == 28 || next.mDataState == 3 || next.mTableFlag >= 0) {
                it = it2;
                rect = bounds;
                arrayList = arrayList2;
                z = z2;
            } else {
                if (next.mCurveProp.getPenSize() > this.erasingMaxPenSize) {
                    this.erasingMaxPenSize = next.mCurveProp.getPenSize();
                }
                int iSqrt = (int) Math.sqrt((bounds.width() * bounds.width()) + (bounds.height() * bounds.height()));
                synchronized (next.mLockObj) {
                    iCurvePointPositionCreateCurvePointsPosition = CurvePointListOperator.createCurvePointsPosition(next.mOriginPoints);
                }
                Rect rect2 = new Rect();
                PenType penType = next.mCurveProp.getPenType();
                if (PenType.PENCIL == penType || PenType.INK == penType || PenType.WATER_COLOR == penType) {
                    basePenPropConfig = next.mPenPropConfig;
                    setPenPropConfig(basePenPropConfig, next);
                } else {
                    basePenPropConfig = null;
                }
                it = it2;
                ICurvePointList curvePointList = next.getCurvePointList(next.mOriginPoints);
                PointF penPoint = next.getPenPoint(true);
                short s = 3;
                int i2 = (int) penPoint.x;
                float f5 = iSqrt + i2;
                rect = bounds;
                int i3 = 0;
                while (i3 < curvePointList.size()) {
                    arrayList2.clear();
                    int i4 = i3;
                    ICurvePointPosition iCurvePointPosition2 = iCurvePointPositionCreateCurvePointsPosition;
                    PenType penType2 = penType;
                    double dSqrt = Math.sqrt(Math.pow(curvePointList.get(i3).getX() - f, 2.0d) + Math.pow(curvePointList.get(i3).getY() - f2, 2.0d));
                    while (true) {
                        float f6 = ((float) dSqrt) - f5;
                        if (f6 <= 0.0f || (i = (int) (f6 / i2)) <= 1) {
                            break;
                        }
                        int i5 = i4 + i;
                        if (i5 >= curvePointList.size()) {
                            i4 = i5 - i;
                            break;
                        }
                        i4 = i5;
                        dSqrt = Math.sqrt(Math.pow(curvePointList.get(i5).getX() - f, 2.0d) + Math.pow(curvePointList.get(i5).getY() - f2, 2.0d));
                    }
                    if (i2 > 16) {
                        for (int i6 = i4; i6 < Math.min(i4 + i2, curvePointList.size()); i6 += i2 / 4) {
                            arrayList2.add(curvePointList.get(i6));
                        }
                    } else if (i2 > 4) {
                        for (int i7 = i4; i7 < Math.min(i4 + i2, curvePointList.size()); i7 += i2 / 2) {
                            arrayList2.add(curvePointList.get(i7));
                        }
                    } else {
                        for (int i8 = i4; i8 < Math.min(i4 + 12, curvePointList.size()); i8++) {
                            arrayList2.add(curvePointList.get(i8));
                        }
                        i2 = 12;
                    }
                    RectF rectF2 = new RectF();
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        ICurvePoint iCurvePoint = (ICurvePoint) it3.next();
                        if (basePenPropConfig != null) {
                            fCalculatePenSize2 = basePenPropConfig.calculatePenSize(iCurvePoint.getPressure(), iCurvePoint.getTilt()) / 2.0f;
                        } else {
                            fCalculatePenSize2 = penPoint.x;
                        }
                        float x = iCurvePoint.getX();
                        float y = iCurvePoint.getY();
                        float f7 = fCalculatePenSize2;
                        rectF2.union(x - f7, y - f7, x + f7, y + f7);
                        it3 = it3;
                        arrayList2 = arrayList2;
                    }
                    ArrayList arrayList3 = arrayList2;
                    float f8 = -f;
                    float f9 = -f2;
                    rectF2.offset(f8, f9);
                    rect2.set((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
                    if (rect2.isEmpty()) {
                        rect2.inset(-1, -1);
                    }
                    Path path = new Path();
                    Region region = new Region();
                    next.mRegion.set(this.mTouchPointDelAreaIntersect);
                    if (next.mRegion.op(rect2, Region.Op.INTERSECT)) {
                        int i9 = 0;
                        while (i9 < i2) {
                            ICurvePoint iCurvePoint2 = curvePointList.get(i9 + i4);
                            if (iCurvePoint2 == null || iCurvePoint2.getState() != 1) {
                                iCurvePointPosition = iCurvePointPosition2;
                            } else {
                                iCurvePointPosition = iCurvePointPosition2;
                                if (!iCurvePointPosition.containsPosition(iCurvePoint2.getIndex())) {
                                    if (basePenPropConfig != null) {
                                        fCalculatePenSize = basePenPropConfig.calculatePenSize(iCurvePoint2.getPressure(), iCurvePoint2.getTilt()) / 2.0f;
                                    } else {
                                        fCalculatePenSize = penPoint.x;
                                    }
                                    path.reset();
                                    float f10 = fCalculatePenSize;
                                    penType2 = penType2;
                                    if (PenType.INK == penType2 || PenType.BALL == penType2) {
                                        path.addCircle(iCurvePoint2.getX(), iCurvePoint2.getY(), PenType.BALL == penType2 ? (f10 / 2.0f) * 0.6f : 1.2f * f10, Path.Direction.CCW);
                                    } else {
                                        float x2 = iCurvePoint2.getX();
                                        float y2 = iCurvePoint2.getY();
                                        if (PenType.MARK == penType2) {
                                            f4 = 0.4f;
                                        } else {
                                            if (PenType.WATER_COLOR == penType2) {
                                                f4 = 0.9f;
                                            } else {
                                                f3 = f10;
                                            }
                                            path.addRect(x2 - f3, y2 - f3, x2 + f3, y2 + f3, Path.Direction.CCW);
                                            path = path;
                                            i9 = i9;
                                        }
                                        f3 = f4 * f10;
                                        path.addRect(x2 - f3, y2 - f3, x2 + f3, y2 + f3, Path.Direction.CCW);
                                        path = path;
                                        i9 = i9;
                                    }
                                    path.offset(f8, f9);
                                    RectF rectF3 = new RectF();
                                    path.computeBounds(rectF3, true);
                                    f8 = f8;
                                    region.setPath(path, new Region((int) rectF3.left, (int) rectF3.top, (int) rectF3.right, (int) rectF3.bottom));
                                    next.mRegion.set(this.mTouchPointDelAreaIntersect);
                                    if (next.mRegion.op(region, Region.Op.INTERSECT)) {
                                        iCurvePointPosition.addPointPosition(iCurvePoint2.getIndex());
                                    }
                                }
                                i9++;
                                penType2 = penType2;
                                z2 = z2;
                                f5 = f5;
                                f8 = f8;
                                path = path;
                                curvePointList = curvePointList;
                                iCurvePointPosition2 = iCurvePointPosition;
                            }
                            z2 = z2;
                            i9++;
                            penType2 = penType2;
                            z2 = z2;
                            f5 = f5;
                            f8 = f8;
                            path = path;
                            curvePointList = curvePointList;
                            iCurvePointPosition2 = iCurvePointPosition;
                        }
                    }
                    i3 = i4 + i2;
                    f = f;
                    f2 = f2;
                    iCurvePointPositionCreateCurvePointsPosition = iCurvePointPosition2;
                    curvePointList = curvePointList;
                    penType = penType2;
                    arrayList2 = arrayList3;
                    z2 = z2;
                    f5 = f5;
                }
                arrayList = arrayList2;
                z = z2;
                ICurvePointPosition iCurvePointPosition3 = iCurvePointPositionCreateCurvePointsPosition;
                arrayList.clear();
                if (iCurvePointPosition3 != null && iCurvePointPosition3.size() > 0) {
                    long curvePointList2 = iCurvePointPosition3.getCurvePointList();
                    ICurvePointList iCurvePointListNewCurvePointList = CurvePointListOperator.newCurvePointList(curvePointList2);
                    PointF pointFCalCurveOutlineThickness = next.calCurveOutlineThickness(true, true);
                    int size = iCurvePointListNewCurvePointList.size();
                    short[] sArr = new short[size];
                    for (int i10 = 0; i10 < size; i10++) {
                        sArr[i10] = iCurvePointListNewCurvePointList.getState(i10);
                    }
                    int size2 = iCurvePointPosition3.size();
                    int i11 = 0;
                    CsPoint csPoint = null;
                    while (i11 < size2) {
                        int i12 = iCurvePointPosition3.get(i11);
                        ICurvePoint iCurvePoint3 = iCurvePointListNewCurvePointList.get(i12);
                        if (iCurvePoint3 != null) {
                            sArr[i12] = s;
                            if (csPoint == null) {
                                csPoint = new CsPoint(iCurvePoint3);
                            } else {
                                csPoint.setX(iCurvePoint3.getX());
                                csPoint.setY(iCurvePoint3.getY());
                                csPoint.setPressure((byte) (iCurvePoint3.getPressure() * 100.0f));
                                csPoint.setTilt((short) (iCurvePoint3.getTilt() * 100.0f));
                                csPoint.setTime(iCurvePoint3.getTime());
                                csPoint.setOrientation((short) (iCurvePoint3.getOrientation() * 100.0f));
                                csPoint.setVelocity((byte) (iCurvePoint3.getV() * 100.0f));
                                csPoint.setRotateAngle(iCurvePoint3.getRotateAngle());
                                csPoint.setDataState((byte) iCurvePoint3.getState());
                            }
                            csPoint.setDataState(s);
                            iCurvePointList = iCurvePointListNewCurvePointList;
                            RectF rectF4 = new RectF(csPoint.getX() - pointFCalCurveOutlineThickness.x, csPoint.getY() - pointFCalCurveOutlineThickness.y, csPoint.getX() + pointFCalCurveOutlineThickness.x, csPoint.getY() + pointFCalCurveOutlineThickness.y);
                            if (this.delPointsRectUnion.isEmpty()) {
                                this.delPointsRectUnion.set(rectF4);
                            } else {
                                this.delPointsRectUnion.union(rectF4);
                            }
                        } else {
                            iCurvePointList = iCurvePointListNewCurvePointList;
                        }
                        i11++;
                        iCurvePointListNewCurvePointList = iCurvePointList;
                        s = 3;
                    }
                    CurvePointListOperator.setPointsState(curvePointList2, sArr, 0, size);
                    this.intersectDataCurveList.add(next);
                    z2 = true;
                }
                it2 = it;
                bounds = rect;
                arrayList2 = arrayList;
            }
            z2 = z;
            it2 = it;
            bounds = rect;
            arrayList2 = arrayList;
        }
        return z2;
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onCancelII() {
        if (this.mStepIntersectDataCurveList.size() > 0) {
            for (DataCurve dataCurve : this.mStepIntersectDataCurveList) {
                dataCurve.mDataState = 1;
                if (!(dataCurve instanceof DataShape)) {
                    synchronized (dataCurve.mLockObj) {
                        CurvePointListOperator.resetPointsState(CurvePointListOperator.createCurvePointsPosition(dataCurve.mOriginPoints).getCurvePointList());
                    }
                }
            }
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(this.mNeedRefreshRect, true, true, true);
        }
        this.eraserOperationManager.forceEndAllEraserOperations();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDown(TouchPoint touchPoint, TouchPoint touchPoint2) {
        this.startTouchPoint.set(touchPoint.getX(), touchPoint.getY());
        this.mNeedRefreshRect.setEmpty();
        this.rectFA.setEmpty();
        this.mStepIntersectDataCurveList.clear();
        this.mLastTouchPointTime = touchPoint.getTime();
        pointEraserRender(touchPoint2);
        this.eraserOperationManager.startPointEraserOperation();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onMove(TouchPoint[] touchPointArr, TouchPoint[] touchPointArr2, TouchPoint touchPoint) {
        if (touchPointArr.length == 0) {
            return;
        }
        boolean z = true;
        TouchPoint touchPoint2 = touchPointArr[touchPointArr.length - 1];
        long time = touchPoint2.getTime();
        if (time - this.mLastTouchPointTime > this.mRefreshInterval) {
            this.mLastTouchPointTime = time;
        } else {
            z = false;
        }
        pointEraserRender(touchPoint);
        processPointsErasing(z, touchPoint2);
        this.startTouchPoint.set(touchPoint2.getX(), touchPoint2.getY());
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onUp(TouchPoint[] touchPointArr) {
        processPointsErasing(false, touchPointArr[touchPointArr.length - 1]);
        splitCurves();
        this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(this.rectFA, true, true, true);
        this.eraserOperationManager.endPointEraserOperation();
        try {
            if (this.mPenEngine.getCurveEngine().getMathDataManager().getHasUserStartedDrawing()) {
                return;
            }
            this.mPenEngine.getCurveEngine().getMathDataManager().setUserStartedDrawing(true);
            XLog.d(TAG, "Enable auto-selection after point eraser operation", new Object[0]);
        } catch (Exception unused) {
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void cancel() {
        onCancelII();
    }

    public void pointEraserRender(TouchPoint touchPoint) {
        postFastRendering(new CsPoint(touchPoint.getX(), touchPoint.getY(), touchPoint.getPressure()));
    }

    private void postFastRendering(CsPoint csPoint) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForEraserTouch(csPoint));
        }
    }
}
