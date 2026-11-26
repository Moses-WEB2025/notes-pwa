package xc.engine.operate.edit.select.selectobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.info.ShapeDrawInfo;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.DefaultDrawStateListener;
import xc.engine.operate.IDrawStateListener;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;
import xc.engine.operate.edit.EditOperator;
import xc.engine.operate.edit.IEditListener;
import xc.engine.operate.edit.select.SelectCopyData;
import xc.engine.operate.edit.select.SelectTouchPoints;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.operate.edit.select.limit.DefaultPenSizeLimitInfo;
import xc.engine.operate.edit.select.limit.ILimitInfo;
import xc.engine.operate.edit.select.limit.SelectLimit;
import xc.engine.operate.edit.select.selectInfo.AbsSelectInfo;
import xc.engine.operate.edit.select.selectInfo.ShapeSelectInfo;
import xc.engine.operate.step.type.IStep;
import xc.engine.operate.step.type.StepDeleteLine;
import xc.engine.operate.step.type.StepMixed;
import xc.engine.operate.step.type.StepMove;
import xc.engine.operate.step.type.StepRotate;
import xc.engine.operate.step.type.StepScale;
import xc.engine.operate.step.type.StepShapeChangeColor;
import xc.engine.operate.step.type.StepShapeDataEdit;
import xc.engine.operate.step.type.StepShapeRadiusEdit;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ShapeSelectObject extends BaseSelectObject implements IEditSelectShapeObject {
    private final String TAG;
    private boolean isDeleteShape;
    public int mAllPointSize;
    public final List<DataCurve> mDataCurveList;
    protected IDrawStateListener mDrawStateChangeCallback;
    public ILimitInfo mLimitInfo;
    public PointF mPointF;
    public ShapeDrawInfo mShapeDrawInfo;
    public int mShapeEditType;
    public float mShapeTotalAngle;
    public StepShapeChangeColor mStepShapeChangeColor;
    public PointF mTranslate;
    public float w;
    public float z;

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void forceRedraw() {
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public short getDataType() {
        return (short) 28;
    }

    public ShapeSelectObject(DataShape dataShape, PenEngine penEngine) {
        super(dataShape, penEngine);
        this.TAG = "ShapeSelectObject";
        this.isDeleteShape = false;
        this.mDrawStateChangeCallback = new DefaultDrawStateListener() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject.1
            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScale(Matrix matrix) {
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasVisible(boolean z) {
                ShapeSelectObject.this.isCanvasVisible = z;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasRelease() {
                ShapeSelectObject.this.mSelectInfo = null;
                ShapeSelectObject.this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(ShapeSelectObject.this.mDrawStateChangeCallback);
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onRenderByCanvas(Canvas canvas) {
                if (ShapeSelectObject.this.mSelectInfo == null || !(ShapeSelectObject.this.mSelectInfo instanceof ShapeSelectInfo)) {
                    return;
                }
                ShapeSelectInfo shapeSelectInfo = (ShapeSelectInfo) ShapeSelectObject.this.mSelectInfo;
                if (ShapeSelectObject.this.mEnableRefreshing) {
                    if (!ShapeSelectObject.this.mNotDeleted || ShapeSelectObject.this.mFinishSelect) {
                        try {
                            ShapeSelectObject.this.mReentrantLock.lock();
                            synchronized (shapeSelectInfo.getMShapeData().mLockObj) {
                                try {
                                    canvas.save();
                                    float offsetX = ShapeSelectObject.this.mTranslate.x + shapeSelectInfo.getOffsetX();
                                    float offsetX2 = ShapeSelectObject.this.mTranslate.y + shapeSelectInfo.getOffsetX();
                                    float centerX = shapeSelectInfo.getMCenterX() - offsetX;
                                    float centerY = shapeSelectInfo.getMCenterY() - offsetX2;
                                    canvas.translate(offsetX, offsetX2);
                                    canvas.rotate(shapeSelectInfo.getMDegrees(), centerX, centerY);
                                    float scale = shapeSelectInfo.getScale();
                                    canvas.scale(scale, scale, centerX, centerY);
                                    Matrix scaleMatrix = ShapeSelectObject.this.mPenEngine.mDrawerWrapper.getScaleMatrix();
                                    if (scaleMatrix != null) {
                                        canvas.concat(scaleMatrix);
                                    }
                                    ShapeSelectObject.this.mShapeDrawInfo.drawShape(canvas, shapeSelectInfo.getShapeData());
                                    canvas.restore();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            ShapeSelectObject.this.mReentrantLock.unlock();
                        }
                    }
                }
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasShowSelectBitmap() {
                ShapeSelectObject.this.mEnableRefreshing = true;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScaleModeChange(boolean z) {
                ShapeSelectObject.this.mShapeDrawInfo.mEnableScale = z;
                ShapeSelectObject.this.setScaleRect();
            }
        };
        this.mPenEngine.mDrawStateManager.addDrawerStateChangeListener(this.mDrawStateChangeCallback);
        this.z = 1.0f;
        this.mPointF = new PointF();
        this.mAllPointSize = 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add(dataShape);
        this.mDataCurveList = arrayList;
        RectF saveRect = dataShape.getSaveRect();
        this.mSelectObjectRect = saveRect;
        dataShape.translateRectByPenPoint(saveRect, false);
        createShapeSelectInfo(dataShape);
        dataShape.setDataState(2);
        if (this.mSelectInfo == null) {
            return;
        }
        this.mSelectInfo.reset();
        penEngine.getCurveDataManager().removeDataFromPool(dataShape);
        penEngine.getCanvasOperator().mCanvasOperateListener.onCanvasDataChanged(null, 11);
        this.mIsReady = true;
        this.mLimitInfo = new DefaultPenSizeLimitInfo(penEngine);
    }

    public ShapeSelectObject(DataShape dataShape, PenEngine penEngine, boolean z) {
        super(dataShape, penEngine);
        this.TAG = "ShapeSelectObject";
        this.isDeleteShape = false;
        this.mDrawStateChangeCallback = new DefaultDrawStateListener() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject.1
            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScale(Matrix matrix) {
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasVisible(boolean z2) {
                ShapeSelectObject.this.isCanvasVisible = z2;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasRelease() {
                ShapeSelectObject.this.mSelectInfo = null;
                ShapeSelectObject.this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(ShapeSelectObject.this.mDrawStateChangeCallback);
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onRenderByCanvas(Canvas canvas) {
                if (ShapeSelectObject.this.mSelectInfo == null || !(ShapeSelectObject.this.mSelectInfo instanceof ShapeSelectInfo)) {
                    return;
                }
                ShapeSelectInfo shapeSelectInfo = (ShapeSelectInfo) ShapeSelectObject.this.mSelectInfo;
                if (ShapeSelectObject.this.mEnableRefreshing) {
                    if (!ShapeSelectObject.this.mNotDeleted || ShapeSelectObject.this.mFinishSelect) {
                        try {
                            ShapeSelectObject.this.mReentrantLock.lock();
                            synchronized (shapeSelectInfo.getMShapeData().mLockObj) {
                                try {
                                    canvas.save();
                                    float offsetX = ShapeSelectObject.this.mTranslate.x + shapeSelectInfo.getOffsetX();
                                    float offsetX2 = ShapeSelectObject.this.mTranslate.y + shapeSelectInfo.getOffsetX();
                                    float centerX = shapeSelectInfo.getMCenterX() - offsetX;
                                    float centerY = shapeSelectInfo.getMCenterY() - offsetX2;
                                    canvas.translate(offsetX, offsetX2);
                                    canvas.rotate(shapeSelectInfo.getMDegrees(), centerX, centerY);
                                    float scale = shapeSelectInfo.getScale();
                                    canvas.scale(scale, scale, centerX, centerY);
                                    Matrix scaleMatrix = ShapeSelectObject.this.mPenEngine.mDrawerWrapper.getScaleMatrix();
                                    if (scaleMatrix != null) {
                                        canvas.concat(scaleMatrix);
                                    }
                                    ShapeSelectObject.this.mShapeDrawInfo.drawShape(canvas, shapeSelectInfo.getShapeData());
                                    canvas.restore();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            ShapeSelectObject.this.mReentrantLock.unlock();
                        }
                    }
                }
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasShowSelectBitmap() {
                ShapeSelectObject.this.mEnableRefreshing = true;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScaleModeChange(boolean z2) {
                ShapeSelectObject.this.mShapeDrawInfo.mEnableScale = z2;
                ShapeSelectObject.this.setScaleRect();
            }
        };
        this.mPenEngine.mDrawStateManager.addDrawerStateChangeListener(this.mDrawStateChangeCallback);
        this.z = 1.0f;
        this.mPointF = new PointF();
        this.mAllPointSize = 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add(dataShape);
        this.mDataCurveList = arrayList;
        RectF saveRect = dataShape.getSaveRect();
        this.mSelectObjectRect = saveRect;
        dataShape.translateRectByPenPoint(saveRect, false);
        createShapeSelectInfo(dataShape);
        dataShape.setDataState(2);
        if (this.mSelectInfo == null) {
            return;
        }
        this.mSelectInfo.reset();
        if (z) {
            penEngine.getCurveDataManager().removeDataFromPool(dataShape);
        }
        this.mIsReady = true;
        this.mLimitInfo = new DefaultPenSizeLimitInfo(penEngine);
    }

    private void createShapeSelectInfo(DataShape dataShape) {
        if (this.mPenEngine.mDrawerWrapper.isInitialized && this.isCanvasVisible) {
            this.mSelectInfo = new ShapeSelectInfo(dataShape);
            this.mShapeDrawInfo = new ShapeDrawInfo(this.mPenEngine);
            this.mNotDeleted = true;
            this.mFinishSelect = false;
            this.mEnableRefreshing = false;
            this.mShapeDrawInfo.mEnableScale = this.mPenEngine.mCanvasOperator.isScale;
            setScaleRect();
            this.mTranslate = this.mPenEngine.getCanvasOperator().getScaleInfo().getTranslate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScaleRect() {
        if (this.mSelectInfo != null) {
            ScaleDataUtil.getScaleAndTranslateRect(getScaledRectF(((ShapeSelectInfo) this.mSelectInfo).getShapeSaveRectF()), this.mPenEngine.mCanvasOperator.getScaleInfo());
        }
    }

    private RectF getScaledRectF(RectF rectF) {
        RectF rectF2 = new RectF(rectF);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        rectF2.left *= drawRatio;
        rectF2.top *= drawRatio;
        rectF2.right *= drawRatio;
        rectF2.bottom *= drawRatio;
        return rectF2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancelAction() {
        if (this.mSelectActionType != 132) {
            return;
        }
        if (this.mShapeEditType == 9) {
            this.mPenEngine.mCanvasOperator.getScaleInfo();
            this.mCurveDataListManager.a(this.mBaseData, -this.mPointF.x, -this.mPointF.y, this.mPenEngine.mTouchEventOperator.isScale());
        } else {
            this.mCurveDataListManager.doShapeEdit(this.mBaseData, this.mShapeEditType, this.mCenterPointF, 1.0f / this.z, -this.mShapeTotalAngle, this.mPenEngine.mTouchEventOperator.isScale());
        }
    }

    private void handelDoShapeEdit(float f, int i, float f2) {
        this.mShapeTotalAngle += f;
        this.z *= this.mCurveDataListManager.doShapeEdit(this.mBaseData, i, this.mCenterPointF, f2, f, this.mPenEngine.mTouchEventOperator.isScale());
        canvasDataChange();
        this.mPenEngine.getCurveEngine().getSelectSystem().handelDoShapeEdit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopAction() {
        if (this.mMovePointF.x != 0.0f || this.mMovePointF.y != 0.0f || this.mDegrees != 0.0f || this.mScale != 1.0f || this.z != 1.0f || this.mPointF.x != 0.0f || this.mPointF.y != 0.0f || this.mShapeTotalAngle != 0.0f) {
            handleSelectAction(this.mSelectActionType);
            if (this.mSelectInfo != null) {
                this.mReentrantLock.lock();
                DataCurve dataCurve = (DataCurve) this.mBaseData;
                RectF saveRect = this.mBaseData.getSaveRect();
                this.mSelectObjectRect = saveRect;
                dataCurve.translateRectByPenPoint(saveRect, false);
                if (this.mSelectActionType == 4 || this.mSelectActionType == 8 || this.mSelectActionType == 16 || this.mSelectActionType == 28 || this.mSelectActionType == 132) {
                    this.mSelectInfo.reset();
                }
                this.mReentrantLock.unlock();
            }
            this.mMovePointF.set(0.0f, 0.0f);
            this.mCenterPointF.set(0.0f, 0.0f);
            this.mDegrees = 0.0f;
            this.mScale = 1.0f;
            this.mShapeTotalAngle = 0.0f;
            this.z = 1.0f;
            this.mPointF.set(0.0f, 0.0f);
        }
        this.mIsReady = true;
        if (this.p) {
            return;
        }
        this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoDelete, reason: merged with bridge method [inline-methods] */
    public void m4650xfca91e2f(boolean z) {
        this.mBaseData.mDataState = 3;
        this.mPenEngine.mCurveDataManager.removeDataFromPool((DataCurve) this.mBaseData);
        this.isDeleteShape = true;
        this.mPenEngine.mDrawerWrapper.refreshCurveData(getRatioRectF());
        ArrayList arrayList = new ArrayList();
        arrayList.add((DataCurve) this.mBaseData);
        if (z) {
            this.mPenEngine.mStepManager.addStepForAI(new StepDeleteLine(this.mPenEngine, arrayList));
        } else {
            this.mPenEngine.mStepManager.addStep(new StepDeleteLine(this.mPenEngine, arrayList));
        }
        ((IEditListener) this.mPenEngine.getSelectOperator()).onSelectedDeletedEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDoCopy() {
        try {
            this.mPenEngine.mEditOperator.mEditListener.onDataCopied(getCopyByteData(), this.mAllPointSize);
        } catch (Throwable th) {
            EditOperator editOperator = this.mPenEngine.mEditOperator;
            editOperator.a((SelectCopyData) null, true);
            editOperator.mEditListener.onDataCopied(null, 0);
            this.mPenEngine.mErrorListener.onError(new Exception(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDoCopyAndPaste() {
        try {
            this.mPenEngine.mEditOperator.mEditListener.onDataCopiedAndPaste(getCopyByteData(), this.mAllPointSize);
        } catch (Throwable th) {
            EditOperator editOperator = this.mPenEngine.mEditOperator;
            editOperator.a((SelectCopyData) null, true);
            editOperator.mEditListener.onDataCopied(null, 0);
            this.mPenEngine.mErrorListener.onError(new Exception(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartAction() {
        ((DataShape) this.mBaseData).O = true;
    }

    private void addShapeDataToPool() {
        ArrayList arrayList = new ArrayList();
        if (this.mBaseData.mDataState == 2) {
            this.mBaseData.mDataState = 1;
            arrayList.add((DataCurve) this.mBaseData);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleFinishSelect() throws InterruptedException {
        this.mPenEngine.mEditOperator.cancelOutlineGenerator();
        this.l = false;
        if (this.mSelectInfo != null && this.mPenEngine.mDrawerWrapper.isInitialized) {
            deleteSelectOperate(getRatioRectF(), this.mSelectInfo);
            this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(this.mDrawStateChangeCallback);
        }
        this.mSelectInfo = null;
        List<DataCurve> list = this.mDataCurveList;
        if (list != null) {
            list.clear();
        }
        this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(null);
        this.mPenEngine.getCurveEngine().getSelectSystem().selectOff(null, this.isDeleteShape, 0);
        if (this.isDeleteShape) {
            this.isDeleteShape = false;
        }
    }

    private final void handleSelectAction(int i) {
        IStep stepShapeDataEdit;
        RectF ratioRectF = getRatioRectF();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mBaseData);
        if (i == 4) {
            RectF rectF = new RectF(ratioRectF);
            PointF pointF = this.mMovePointF;
            rectF.offset(pointF.x, pointF.y);
            ratioRectF.union(rectF);
            StepMove stepMove = new StepMove(this.mPenEngine, arrayList, getRatioRectF(ratioRectF), getRatioPointF(this.mMovePointF));
            SelectTouchPoints selectTouchPoints = this.mOriginSelectTouchPoints;
            if (selectTouchPoints != null) {
                PointF pointF2 = this.mMovePointF;
                selectTouchPoints.doMove(pointF2.x, pointF2.y);
            }
            this.mPenEngine.mStepManager.addStep(stepMove);
            this.mBaseData.doMove(this.mMovePointF.x, this.mMovePointF.y);
        } else if (i == 8) {
            Matrix matrix = new Matrix();
            matrix.setScale(this.mScale, this.mScale, this.mCenterPointF.x, this.mCenterPointF.y);
            RectF rectF2 = new RectF(ratioRectF);
            matrix.mapRect(rectF2);
            ratioRectF.union(rectF2);
            StepScale stepScale = new StepScale(this.mPenEngine, arrayList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mScale);
            SelectTouchPoints selectTouchPoints2 = this.mOriginSelectTouchPoints;
            if (selectTouchPoints2 != null) {
                PointF pointF3 = this.mCenterPointF;
                float f = pointF3.x;
                float f2 = pointF3.y;
                float f3 = this.mScale;
                selectTouchPoints2.doScale(f, f2, f3, f3);
            }
            this.mPenEngine.mStepManager.addStep(stepScale);
            this.mCurveDataListManager.doShapeScale(this.mBaseData, this.mCenterPointF.x, this.mCenterPointF.y, this.mScale);
            this.mTotalScale *= this.mScale;
        } else if (i == 16) {
            Matrix matrix2 = new Matrix();
            matrix2.setRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
            RectF rectF3 = new RectF(ratioRectF);
            matrix2.mapRect(rectF3);
            ratioRectF.union(rectF3);
            StepRotate stepRotate = new StepRotate(this.mPenEngine, arrayList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mDegrees);
            SelectTouchPoints selectTouchPoints3 = this.mOriginSelectTouchPoints;
            if (selectTouchPoints3 != null) {
                selectTouchPoints3.doRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
            }
            this.mPenEngine.mStepManager.addStep(stepRotate);
            this.mCurveDataListManager.doShapeRotate(this.mBaseData, this.mCenterPointF.x, this.mCenterPointF.y, this.mDegrees);
            this.mTotalDegress += this.mDegrees;
        } else if (i == 28) {
            RectF rectF4 = new RectF(ratioRectF);
            Matrix matrix3 = new Matrix();
            float f4 = this.mScale;
            PointF pointF4 = this.mCenterPointF;
            matrix3.setScale(f4, f4, pointF4.x, pointF4.y);
            matrix3.mapRect(rectF4);
            float f5 = this.mDegrees;
            PointF pointF5 = this.mCenterPointF;
            matrix3.setRotate(f5, pointF5.x, pointF5.y);
            matrix3.mapRect(rectF4);
            ratioRectF.union(rectF4);
            StepMixed stepMixed = new StepMixed(this.mPenEngine, arrayList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mScale, this.mDegrees);
            SelectTouchPoints selectTouchPoints4 = this.mOriginSelectTouchPoints;
            if (selectTouchPoints4 != null) {
                PointF pointF6 = this.mCenterPointF;
                float f6 = pointF6.x;
                float f7 = pointF6.y;
                float f8 = this.mScale;
                selectTouchPoints4.doScale(f6, f7, f8, f8);
            }
            SelectTouchPoints selectTouchPoints5 = this.mOriginSelectTouchPoints;
            if (selectTouchPoints5 != null) {
                double d = this.mDegrees;
                PointF pointF7 = this.mCenterPointF;
                selectTouchPoints5.doRotate(d, pointF7.x, pointF7.y);
            }
            this.mPenEngine.mStepManager.addStep(stepMixed);
            this.mCurveDataListManager.doShapeScale(this.mBaseData, this.mCenterPointF.x, this.mCenterPointF.y, this.mScale);
            this.mCurveDataListManager.doShapeRotate(this.mBaseData, this.mCenterPointF.x, this.mCenterPointF.y, this.mDegrees);
            this.mTotalScale *= this.mScale;
            this.mTotalDegress += this.mDegrees;
        } else if (i == 132) {
            if (this.mShapeEditType == 9) {
                stepShapeDataEdit = new StepShapeRadiusEdit(this.mPenEngine, (DataShape) this.mBaseData, getRatioRectF(ratioRectF), getRatioPointF(this.mPointF));
            } else {
                DataShape dataShape = (DataShape) this.mBaseData;
                RectF saveRect = this.mBaseData.getSaveRect();
                this.mSelectObjectRect = saveRect;
                dataShape.translateRectByPenPoint(saveRect, false);
                ratioRectF.union(getRatioRectF());
                stepShapeDataEdit = new StepShapeDataEdit(this.mPenEngine, (DataShape) this.mBaseData, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.z, this.mShapeTotalAngle, this.mShapeEditType);
            }
            this.mPenEngine.mStepManager.addStep(stepShapeDataEdit);
            if (this.mBaseData.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) this.mBaseData);
            }
        }
        this.mPenEngine.getCurveEngine().getSelectSystem().onShapeMoveEnd();
        this.mPenEngine.updateDataCurvePageIndex(this.mDataCurveList);
    }

    private byte[] getCopyByteData() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byteArray = null;
        try {
            try {
                dataOutputStream.writeLong(System.currentTimeMillis());
                dataOutputStream.writeFloat(this.mPenEngine.mCanvasOperator.getDrawRatio());
                RectF ratioRectF = getRatioRectF();
                dataOutputStream.writeFloat(ratioRectF.left);
                dataOutputStream.writeFloat(ratioRectF.top);
                dataOutputStream.writeFloat(ratioRectF.right);
                dataOutputStream.writeFloat(ratioRectF.bottom);
                if (this.mOriginSelectTouchPoints == null) {
                    dataOutputStream.writeInt(0);
                } else {
                    byte[] bArrConvertTouchPointsToByteArray = SelectTouchPoints.convertTouchPointsToByteArray(this.mOriginSelectTouchPoints);
                    dataOutputStream.writeInt(bArrConvertTouchPointsToByteArray.length);
                    dataOutputStream.write(bArrConvertTouchPointsToByteArray);
                }
                CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
                dataOutputStream.writeFloat(canvasOperator.isScale ? canvasOperator.getScaleVisibleTop() : 0.0f);
                dataOutputStream.writeInt(1);
                SelectCopyData.writeDataToStream(this.mBaseData, dataOutputStream);
                if (this.mBaseData instanceof DataCurve) {
                    this.mAllPointSize += ((DataCurve) this.mBaseData).size(((DataCurve) this.mBaseData).mSavePoints);
                }
                byteArray = byteArrayOutputStream.toByteArray();
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                throw th;
            }
        } catch (Exception unused2) {
            dataOutputStream.close();
            byteArrayOutputStream.close();
        }
        return byteArray;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void startAction(int i) {
        super.startAction(i);
        if (i == 132) {
            this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.handleStartAction();
                }
            });
        }
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopy() {
        this.mAllPointSize = 0;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoCopy();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopyAndPaste() {
        this.mAllPointSize = 0;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoCopyAndPaste();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doDelete(final boolean z) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4650xfca91e2f(z);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public Point getSelectBitmapSize() {
        Rect intRect = PenEngineUtils.toIntRect(getRatioRectF());
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            intRect = ScaleDataUtil.getToScaleRect(intRect, canvasOperator.getScaleInfo());
        }
        return new Point(intRect.width(), intRect.height());
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean onScreenshots(Bitmap bitmap) {
        if (this.mSelectInfo == null || bitmap == null) {
            return false;
        }
        Canvas canvas = new Canvas(bitmap);
        Rect intRect = PenEngineUtils.toIntRect(getScaledRectF(((ShapeSelectInfo) this.mSelectInfo).getShapeSaveRectF()));
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            intRect = ScaleDataUtil.getToScaleRect(intRect, canvasOperator.getScaleInfo());
        }
        canvas.translate(-intRect.left, -intRect.top);
        canvas.translate(this.mTranslate.x, this.mTranslate.y);
        this.mShapeDrawInfo.drawShape(canvas, ((ShapeSelectInfo) this.mSelectInfo).getShapeData());
        canvas.setBitmap(null);
        return true;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect() throws InterruptedException {
        this.mFinishSelect = true;
        finishSelect(false);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public List<DataCurve> getSelectData() {
        return this.mDataCurveList;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    protected void deleteSelectOperate(RectF rectF, AbsSelectInfo absSelectInfo) {
        super.deleteSelectOperate(rectF, absSelectInfo);
        this.mEnableRefreshing = false;
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void stopAction() {
        super.stopAction();
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleStopAction();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    public void changeVisibleSize(final float f) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4649x2787239b(f);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public RectF getShapeDrawRect() {
        RectF ratioRectF = getRatioRectF();
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            ScaleInfo scaleInfo = canvasOperator.getScaleInfo();
            Matrix matrix = new Matrix();
            matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
            matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
            matrix.mapRect(ratioRectF);
        }
        return PenEngineUtils.getCopyRectF(ratioRectF);
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public ShapeProp getShapeProp() {
        return ((DataShape) this.mBaseData).mShapeProp;
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public float[] getEditPoint() {
        float[] fArr = new float[14];
        System.arraycopy(((DataShape) this.mBaseData).getCornerPoints(), 0, fArr, 0, 8);
        System.arraycopy(((DataShape) this.mBaseData).getEditPoints(), 0, fArr, 8, 6);
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            ScaleInfo scaleInfo = canvasOperator.getScaleInfo();
            Matrix matrix = new Matrix();
            matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
            matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
            matrix.mapPoints(fArr);
        }
        return fArr;
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public Path getSelectPath() {
        float[] fArr = new float[8];
        System.arraycopy(((DataShape) this.mBaseData).getCornerPoints(), 0, fArr, 0, 8);
        float penSize = ((DataShape) this.mBaseData).mShapeProp.getPenSize();
        if (((DataShape) this.mBaseData).mShapeProp.getShapeType() == 2) {
            penSize = Math.max(penSize * 2.0f, 8.0f) * 2.0f;
        }
        float drawRatio = penSize * this.mBaseData.getDrawRatio();
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            ScaleInfo scaleInfo = canvasOperator.getScaleInfo();
            Matrix matrix = new Matrix();
            matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
            matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
            matrix.mapPoints(fArr);
            drawRatio *= scaleInfo.getScaleRate();
        }
        Path path = new Path();
        if (!((DataShape) this.mBaseData).isLineOrArrow()) {
            path.moveTo(fArr[0], fArr[1]);
            path.lineTo(fArr[2], fArr[3]);
            path.lineTo(fArr[4], fArr[5]);
            path.lineTo(fArr[6], fArr[7]);
            path.close();
            return path;
        }
        Matrix matrix2 = new Matrix();
        float degrees = (float) Math.toDegrees(Math.atan2(fArr[5] - fArr[1], fArr[4] - fArr[0]));
        matrix2.reset();
        matrix2.setRotate(-degrees, (fArr[0] + fArr[4]) / 2.0f, (fArr[1] + fArr[5]) / 2.0f);
        matrix2.mapPoints(fArr);
        float f = drawRatio / 2.0f;
        path.moveTo(fArr[0], fArr[1] - f);
        path.lineTo(fArr[0], fArr[1] + f);
        path.lineTo(fArr[4], fArr[5] + f);
        path.lineTo(fArr[4], fArr[5] - f);
        path.close();
        matrix2.reset();
        matrix2.setRotate(degrees, (fArr[0] + fArr[4]) / 2.0f, (fArr[1] + fArr[5]) / 2.0f);
        path.transform(matrix2);
        return path;
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public void doShapeEdit(int i, PointF pointF, float f, float f2) {
        PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(pointF.x, pointF.y, this.mPenEngine.mCanvasOperator.getScaleInfo());
        this.mCenterPointF.set(pointFScaleInverseOperation.x, pointFScaleInverseOperation.y);
        this.mShapeEditType = i;
        handelDoShapeEdit(f2, i, f);
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public void doRadiusEdit(float f, float f2) {
        this.mShapeEditType = 9;
        handleDoRadiusEdit(f, f2);
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public void doShapeAttrEdit(ShapeEditAttr shapeEditAttr, boolean z) {
        SelectLimit.PenSize penSizeLimit;
        if (shapeEditAttr == null) {
            return;
        }
        if (shapeEditAttr.getStrokeSize() >= 10.0f) {
            shapeEditAttr.setStrokeSize(10.0f);
        }
        SelectLimit selectLimit = this.mPenEngine.mEditOperator.mSelectLimit;
        if (selectLimit != null && (penSizeLimit = selectLimit.getPenSizeLimit(6)) != null && (shapeEditAttr.getStrokeSize() > penSizeLimit.getMaxSize() || shapeEditAttr.getStrokeSize() < penSizeLimit.getMinSize())) {
            shapeEditAttr.setStrokeSize(shapeEditAttr.getStrokeSize());
        }
        new ArrayList().add((DataCurve) this.mBaseData);
        float penSize = ((DataShape) this.mBaseData).mShapeProp.getPenSize();
        ((DataShape) this.mBaseData).mShapeProp.setPenSize(shapeEditAttr.getStrokeSize());
        RectF saveRect = this.mBaseData.getSaveRect();
        ((DataShape) this.mBaseData).translateRectByPenPoint(saveRect, false);
        ((DataShape) this.mBaseData).mShapeProp.setPenSize(penSize);
        saveRect.union(this.mSelectObjectRect);
        if (this.mStepShapeChangeColor == null) {
            this.mStepShapeChangeColor = new StepShapeChangeColor(this.mPenEngine, (DataShape) this.mBaseData);
        }
        if (z) {
            StepShapeChangeColor stepShapeChangeColor = this.mStepShapeChangeColor;
            int fillAlpha = shapeEditAttr.getFillAlpha();
            int fillColor = shapeEditAttr.getFillColor();
            int strokeAlpha = shapeEditAttr.getStrokeAlpha();
            int strokeColor = shapeEditAttr.getStrokeColor();
            float strokeSize = shapeEditAttr.getStrokeSize();
            byte strokeType = (byte) shapeEditAttr.getStrokeType();
            RectF rectF = stepShapeChangeColor.h;
            if (rectF == null) {
                stepShapeChangeColor.h = saveRect;
            } else {
                rectF.union(saveRect);
            }
            stepShapeChangeColor.b = fillAlpha;
            stepShapeChangeColor.c = fillColor;
            stepShapeChangeColor.f = strokeAlpha;
            stepShapeChangeColor.d = strokeColor;
            stepShapeChangeColor.e = strokeSize;
            stepShapeChangeColor.g = strokeType;
            this.mPenEngine.mStepManager.addStep(this.mStepShapeChangeColor);
            this.mStepShapeChangeColor = null;
        }
        if (this.mPenEngine.mCanvasOperator.isColorWheelMode()) {
            ((DataShape) this.mBaseData).mShapeProp.setFillColorWheel(shapeEditAttr.getFillColor());
            ((DataShape) this.mBaseData).mShapeProp.setPenColorWheel(shapeEditAttr.getStrokeColor());
        } else {
            ((DataShape) this.mBaseData).mShapeProp.setFillColor(shapeEditAttr.getFillColor());
            ((DataShape) this.mBaseData).mShapeProp.setPenColor(shapeEditAttr.getStrokeColor());
        }
        ((DataShape) this.mBaseData).mShapeProp.setFillAlpha(shapeEditAttr.getFillAlpha());
        ((DataShape) this.mBaseData).mShapeProp.setPenAlpha(shapeEditAttr.getStrokeAlpha());
        ((DataShape) this.mBaseData).setStrokeType((byte) shapeEditAttr.getStrokeType());
        if (((DataShape) this.mBaseData).mShapeProp.getShapeType() == 2 && ((DataShape) this.mBaseData).mShapeProp.getPenSize() != shapeEditAttr.getStrokeSize()) {
            ((DataShape) this.mBaseData).mShapeProp.setPenSize(shapeEditAttr.getStrokeSize());
            synchronized (((DataShape) this.mBaseData).mLockObj) {
                if (this.mPenEngine.mTouchEventOperator.isScale()) {
                    new ShapeCurveInfo(this.mPenEngine, (DataShape) this.mBaseData, true, false).optimizeAndTransformShapePath();
                }
                new ShapeCurveInfo(this.mPenEngine, (DataShape) this.mBaseData, false, false).optimizeAndTransformShapePath();
            }
            if (this.mBaseData.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) this.mBaseData);
            }
        } else {
            ((DataShape) this.mBaseData).mShapeProp.setPenSize(shapeEditAttr.getStrokeSize());
        }
        this.mSelectObjectRect.set(saveRect);
        if (this.mSelectInfo != null) {
            this.mSelectInfo.reset();
        }
        canvasDataChange();
        if (this.p) {
            return;
        }
        this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(this);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void cancelAction() {
        super.cancelAction();
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleCancelAction();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean checkCurveSelectLimit(float f) {
        return ((this.mSelectActionType & 8) == 8 || this.mSelectActionType == 132) && this.mLimitInfo.a(this.mDataCurveList, f, this.mSelectActionType);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public float getCurScaleValue() {
        float f = this.mScale;
        return (((this.mSelectActionType & 8) == 8 || this.mSelectActionType == 132) && this.mLimitInfo.a(this.mDataCurveList, f, this.mSelectActionType)) ? this.mLimitInfo.getScaleValue() : f;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void onDraw(Canvas canvas) {
        if (this.mSelectInfo == null || !this.mEnableRefreshing || !this.mNotDeleted || this.mFinishSelect) {
            return;
        }
        try {
            try {
                this.mReentrantLock.lock();
                synchronized (((ShapeSelectInfo) this.mSelectInfo).getShapeData().mLockObj) {
                    try {
                        canvas.save();
                        float offsetX = this.mTranslate.x + this.mSelectInfo.getOffsetX();
                        float offsetY = this.mTranslate.y + this.mSelectInfo.getOffsetY();
                        float mCenterX = this.mSelectInfo.getMCenterX() - offsetX;
                        float mCenterY = this.mSelectInfo.getMCenterY() - offsetY;
                        canvas.translate(offsetX, offsetY);
                        canvas.rotate(this.mDegrees, mCenterX, mCenterY);
                        canvas.scale(this.mScale, this.mScale, mCenterX, mCenterY);
                        Matrix scaleMatrix = this.mPenEngine.mDrawerWrapper.getScaleMatrix();
                        if (scaleMatrix != null) {
                            canvas.concat(scaleMatrix);
                        }
                        this.mShapeDrawInfo.drawShape(canvas, ((ShapeSelectInfo) this.mSelectInfo).getShapeData());
                        canvas.restore();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            this.mReentrantLock.unlock();
        }
    }

    @Override // xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject
    public boolean showEditPoint() {
        if (((DataShape) this.mBaseData).isLineOrArrow() || ((DataShape) this.mBaseData).mShapeProp.getShapeType() == 5) {
            return false;
        }
        float[] cornerPoints = ((DataShape) this.mBaseData).getCornerPoints();
        float[] fArr = {cornerPoints[0] / this.mBaseData.getDrawRatio(), cornerPoints[1] / this.mBaseData.getDrawRatio(), cornerPoints[2] / this.mBaseData.getDrawRatio(), cornerPoints[3] / this.mBaseData.getDrawRatio(), cornerPoints[4] / this.mBaseData.getDrawRatio(), cornerPoints[5] / this.mBaseData.getDrawRatio(), cornerPoints[6] / this.mBaseData.getDrawRatio(), cornerPoints[7] / this.mBaseData.getDrawRatio()};
        return Math.min((float) Math.abs(Math.sqrt(Math.pow((double) (fArr[2] - fArr[0]), 2.0d) + Math.pow((double) (fArr[3] - fArr[1]), 2.0d))), (float) Math.abs(Math.sqrt(Math.pow((double) (fArr[4] - fArr[2]), 2.0d) + Math.pow((double) (fArr[5] - fArr[3]), 2.0d)))) > 50.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void m4649x2787239b(float f) {
        if (this.mSelectInfo != null) {
            float f2 = f / this.w;
            this.w = f;
            Matrix matrix = new Matrix();
            matrix.postScale(f2, f2);
            SelectTouchPoints selectTouchPoints = this.mOriginSelectTouchPoints;
            if (selectTouchPoints != null) {
                selectTouchPoints.mPath.transform(matrix);
            }
            this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(this);
            this.mPenEngine.mCurveDataManager.executeCurveGenTask((DataCurve) this.mBaseData);
            RectF saveRect = this.mBaseData.getSaveRect();
            this.mSelectObjectRect = saveRect;
            ((DataCurve) this.mBaseData).translateRectByPenPoint(saveRect, false);
            this.mSelectInfo.reset();
            this.mPenEngine.mDrawerWrapper.refreshCurveData(getRatioRectF());
        }
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect(boolean z) throws InterruptedException {
        if (this.mSelectInfo == null) {
            return;
        }
        super.finishSelect(z);
        addShapeDataToPool();
        if (z) {
            handleFinishSelect();
        } else {
            this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.ShapeSelectObject$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    this.f$0.handleFinishSelect();
                }
            });
        }
        this.mIsReady = true;
    }

    private void handleDoRadiusEdit(float f, float f2) {
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        this.mMovePointF.set(f / scaleInfo.getScaleRate(), f2 / scaleInfo.getScaleRate());
        boolean zIsScale = this.mPenEngine.mTouchEventOperator.isScale();
        this.mPenEngine.mCanvasOperator.getScaleInfo();
        PointF pointFA = this.mCurveDataListManager.a(this.mBaseData, this.mMovePointF.x, this.mMovePointF.y, zIsScale);
        PointF pointF = this.mPointF;
        pointF.set(pointF.x + pointFA.x, this.mPointF.y + pointFA.y);
        canvasDataChange();
        this.mPenEngine.getCurveEngine().getSelectSystem().handelDoShapeEdit();
    }
}
