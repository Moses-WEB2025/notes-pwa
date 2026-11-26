package xc.engine.operate.edit.select.selectobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.manager.MathDataManager;
import xc.engine.operate.DefaultDrawStateListener;
import xc.engine.operate.IDrawStateListener;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.edit.EditOperator;
import xc.engine.operate.edit.IEditListener;
import xc.engine.operate.edit.select.SelectCopyData;
import xc.engine.operate.edit.select.SelectTouchPoints;
import xc.engine.operate.edit.select.limit.DefaultPenSizeLimitInfo;
import xc.engine.operate.edit.select.limit.ILimitInfo;
import xc.engine.operate.edit.select.limit.PenSizeLimitInfo;
import xc.engine.operate.edit.select.limit.SelectLimit;
import xc.engine.operate.edit.select.selectInfo.AbsSelectInfo;
import xc.engine.operate.edit.select.selectInfo.BitmapRectInfo;
import xc.engine.operate.edit.select.selectInfo.MultiSelectInfo;
import xc.engine.operate.step.type.IStep;
import xc.engine.operate.step.type.StepDeleteLine;
import xc.engine.operate.step.type.StepMixed;
import xc.engine.operate.step.type.StepMove;
import xc.engine.operate.step.type.StepShapeEdit;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class MultiSelectCurveObject extends BaseSelectObject implements ISelectCurveObject {
    public final float RECT_AREA_LIMIT;
    private final String TAG;
    public boolean doSelectedContour;
    public int mAllOriginPoints;
    public int mAllPointSize;
    public int mAllScaledPoints;
    public Rect mAreaRect;
    public Rect mBitmapRect;
    public List<BitmapRectInfo> mBitmapRectInfoList;
    public Bitmap mCurrentBitmap;
    public final List<DataCurve> mDataCurveList;
    public ILimitInfo mDefaultPenSizeLimitInfo;
    protected IDrawStateListener mDrawStateChangeCallback;
    public boolean mEnableScale;
    public Bitmap mLastBitmap;
    public Matrix mMatrix;
    public boolean mNotInSelecting;
    public final Paint mPaint;
    public ILimitInfo mPenSizeLimitInfo;
    public boolean mRectAreaGreaterThanLimit;
    public Rect mScaledAndTransRect;
    public List<Bitmap> mSegmentBitmapList;
    public Rect mVisibleBitmapRect;
    public float mVisibleHeight;
    public float mVisibleWidth;
    private int selectOffState;

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public short getDataType() {
        return (short) 1;
    }

    public MultiSelectCurveObject(List<DataCurve> list, PenEngine penEngine, boolean z) {
        super(list.get(0), penEngine);
        this.TAG = "MultiSelectCurveObject";
        this.RECT_AREA_LIMIT = 6250000.0f;
        this.selectOffState = 0;
        this.mDrawStateChangeCallback = new DefaultDrawStateListener() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject.1
            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasVisible(boolean z2) {
                MultiSelectCurveObject.this.isCanvasVisible = z2;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasRelease() {
                if (MultiSelectCurveObject.this.mSelectInfo != null && (MultiSelectCurveObject.this.mSelectInfo instanceof MultiSelectInfo)) {
                    MultiSelectCurveObject.this.release();
                }
                MultiSelectCurveObject.this.mSelectInfo = null;
                MultiSelectCurveObject.this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(MultiSelectCurveObject.this.mDrawStateChangeCallback);
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onRenderByCanvas(Canvas canvas) {
                if (MultiSelectCurveObject.this.mSelectInfo == null || !(MultiSelectCurveObject.this.mSelectInfo instanceof MultiSelectInfo)) {
                    return;
                }
                if ((!MultiSelectCurveObject.this.mNotDeleted || MultiSelectCurveObject.this.mFinishSelect) && MultiSelectCurveObject.this.mEnableRefreshing) {
                    canvas.save();
                    MultiSelectCurveObject.this.drawSelectBitmap(canvas);
                    canvas.restore();
                }
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScale(Matrix matrix) {
                MultiSelectCurveObject.this.mMatrix = matrix;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasShowSelectBitmap() {
                MultiSelectCurveObject.this.mEnableRefreshing = true;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScaleModeChange(boolean z2) {
                MultiSelectCurveObject.this.mEnableScale = z2;
            }
        };
        this.mPenEngine.mDrawStateManager.addDrawerStateChangeListener(this.mDrawStateChangeCallback);
        this.mAllPointSize = 0;
        this.mAllOriginPoints = 0;
        this.mAllScaledPoints = 0;
        this.mDataCurveList = new ArrayList(list);
        this.mSelectObjectRect = getMultiSelectObjectRect();
        this.mNotInSelecting = false;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        paint.setDither(true);
        SelectLimit selectLimit = penEngine.getEditOperator().getSelectLimit();
        if (selectLimit != null && selectLimit.getLimitMode() == 1) {
            this.mPenSizeLimitInfo = new PenSizeLimitInfo(penEngine, PenEngineUtils.getCopyRectF(this.mSelectObjectRect));
        }
        this.mDefaultPenSizeLimitInfo = new DefaultPenSizeLimitInfo(penEngine);
        initSelectInfo();
        this.doSelectedContour = z;
    }

    public MultiSelectCurveObject(List<DataCurve> list, PenEngine penEngine, boolean z, boolean z2) {
        super(list.get(0), penEngine);
        this.TAG = "MultiSelectCurveObject";
        this.RECT_AREA_LIMIT = 6250000.0f;
        this.selectOffState = 0;
        this.mDrawStateChangeCallback = new DefaultDrawStateListener() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject.1
            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasVisible(boolean z3) {
                MultiSelectCurveObject.this.isCanvasVisible = z3;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasRelease() {
                if (MultiSelectCurveObject.this.mSelectInfo != null && (MultiSelectCurveObject.this.mSelectInfo instanceof MultiSelectInfo)) {
                    MultiSelectCurveObject.this.release();
                }
                MultiSelectCurveObject.this.mSelectInfo = null;
                MultiSelectCurveObject.this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(MultiSelectCurveObject.this.mDrawStateChangeCallback);
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onRenderByCanvas(Canvas canvas) {
                if (MultiSelectCurveObject.this.mSelectInfo == null || !(MultiSelectCurveObject.this.mSelectInfo instanceof MultiSelectInfo)) {
                    return;
                }
                if ((!MultiSelectCurveObject.this.mNotDeleted || MultiSelectCurveObject.this.mFinishSelect) && MultiSelectCurveObject.this.mEnableRefreshing) {
                    canvas.save();
                    MultiSelectCurveObject.this.drawSelectBitmap(canvas);
                    canvas.restore();
                }
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScale(Matrix matrix) {
                MultiSelectCurveObject.this.mMatrix = matrix;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasShowSelectBitmap() {
                MultiSelectCurveObject.this.mEnableRefreshing = true;
            }

            @Override // xc.engine.operate.DefaultDrawStateListener, xc.engine.operate.IDrawStateListener
            public void onCanvasScaleModeChange(boolean z3) {
                MultiSelectCurveObject.this.mEnableScale = z3;
            }
        };
        this.mPenEngine.mDrawStateManager.addDrawerStateChangeListener(this.mDrawStateChangeCallback);
        this.mAllPointSize = 0;
        this.mAllOriginPoints = 0;
        this.mAllScaledPoints = 0;
        this.mDataCurveList = new ArrayList(list);
        this.mSelectObjectRect = getMultiSelectObjectRect();
        this.mNotInSelecting = false;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        paint.setDither(true);
        SelectLimit selectLimit = penEngine.getEditOperator().getSelectLimit();
        if (selectLimit != null && selectLimit.getLimitMode() == 1) {
            this.mPenSizeLimitInfo = new PenSizeLimitInfo(penEngine, PenEngineUtils.getCopyRectF(this.mSelectObjectRect));
        }
        this.mDefaultPenSizeLimitInfo = new DefaultPenSizeLimitInfo(penEngine);
        initSelectInfo(z2);
        this.doSelectedContour = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleChangeVisibleSize, reason: merged with bridge method [inline-methods] */
    public void m4645xcf8f979a(float f) {
        if (this.mSelectInfo != null) {
            float f2 = f / this.mVisibleWidth;
            this.mVisibleWidth = f;
            Matrix matrix = new Matrix();
            matrix.postScale(f2, f2);
            this.mOriginSelectTouchPoints.mPath.transform(matrix);
            if (this.mNotInSelecting) {
                return;
            }
            if (this.mSelectInfo instanceof MultiSelectInfo) {
                this.mSelectInfo.doScale(0.0f, 0.0f, f2);
            }
            this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(this);
            this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(this.mDataCurveList);
            if (this.mPenEngine.mTouchEventOperator.isScale()) {
                this.mCurveDataListManager.genCurveScaledPoints(this.mDataCurveList, this.mPenEngine.mCanvasOperator.getScaleInfo());
            }
            this.mSelectObjectRect = getMultiSelectObjectRect();
            this.mSelectInfo.reset();
            this.mPenEngine.mDrawerWrapper.refreshSelectCurveData(getRatioRectF());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDoDeleteJustCurve() {
        Log.d("yjg", "handleDoDeleteJustCurve called");
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : this.mDataCurveList) {
            if (dataCurve.mCurveType == 0 && dataCurve.getType() != 28) {
                dataCurve.mDataState = 3;
                arrayList.add(dataCurve);
            }
        }
        if (arrayList.size() == 0) {
            return;
        }
        if (this.mNotInSelecting) {
            this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(arrayList);
        }
        this.mPenEngine.mDrawerWrapper.refreshSelectCurveData(getRatioRectF());
        this.mPenEngine.mStepManager.addStep(new StepDeleteLine(this.mPenEngine, arrayList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoDelete, reason: merged with bridge method [inline-methods] */
    public void m4646x9f271986(boolean z) {
        Iterator<DataCurve> it = this.mDataCurveList.iterator();
        while (it.hasNext()) {
            it.next().mDataState = 3;
        }
        if (this.mNotInSelecting) {
            this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurveList);
        }
        this.mPenEngine.mDrawerWrapper.refreshSelectCurveData(getRatioRectF());
        if (z) {
            this.mPenEngine.mStepManager.addStepForAI(new StepDeleteLine(this.mPenEngine, this.mDataCurveList));
        } else {
            this.mPenEngine.mStepManager.addStep(new StepDeleteLine(this.mPenEngine, this.mDataCurveList));
        }
        ((IEditListener) this.mPenEngine.getSelectOperator()).onSelectedDeletedEnd();
        this.mPenEngine.getCurveEngine().getRecognitionModel().doDelete();
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
            this.selectOffState = 2;
            this.mPenEngine.getCurveEngine().getMathDataManager().setCopyAndPasteStatus(1);
            this.mPenEngine.mEditOperator.mEditListener.onDataCopiedAndPaste(getCopyByteData(), this.mAllPointSize);
        } catch (Throwable th) {
            EditOperator editOperator = this.mPenEngine.mEditOperator;
            editOperator.a((SelectCopyData) null, true);
            editOperator.mEditListener.onDataCopied(null, 0);
            this.mPenEngine.mErrorListener.onError(new Exception(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:14:0x002b  */
    /* JADX WARN: Code duplicated, block: B:23:0x0056  */
    public void handleStopAction() {
        PointF pointF = this.mMovePointF;
        if (pointF.x == 0.0f && pointF.y == 0.0f && this.mDegrees == 0.0f && this.mScale == 1.0f) {
            PointF pointF2 = this.e;
            if (pointF2.x != 0.0f || pointF2.y != 0.0f) {
                c(this.mSelectActionType);
                this.mSelectObjectRect = getMultiSelectObjectRect();
                if (this.mSelectActionType == 4) {
                    this.mAreaRect.set(PenEngineUtils.toIntRect(getRatioRectF()));
                }
                if (this.mSelectInfo != null) {
                    reset(getRatioRectF(), this.mDataCurveList);
                    this.mSelectInfo.applyOffsetToPoint();
                }
                this.e.set(0.0f, 0.0f);
                this.mMovePointF.set(0.0f, 0.0f);
                this.mCenterPointF.set(0.0f, 0.0f);
                this.mDegrees = 0.0f;
                this.mScale = 1.0f;
            }
        } else {
            c(this.mSelectActionType);
            this.mSelectObjectRect = getMultiSelectObjectRect();
            if (this.mSelectActionType == 4 && (this.mSelectInfo == null || isRectWithinLimit())) {
                this.mAreaRect.set(PenEngineUtils.toIntRect(getRatioRectF()));
            }
            if (this.mSelectInfo != null) {
                reset(getRatioRectF(), this.mDataCurveList);
                this.mSelectInfo.applyOffsetToPoint();
            }
            this.e.set(0.0f, 0.0f);
            this.mMovePointF.set(0.0f, 0.0f);
            this.mCenterPointF.set(0.0f, 0.0f);
            this.mDegrees = 0.0f;
            this.mScale = 1.0f;
        }
        this.mIsReady = true;
        if (this.p) {
            return;
        }
        this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(this);
    }

    private void updateAntLinePath() {
        if (this.mOriginSelectTouchPoints == null || this.mOriginSelectTouchPoints.mPath == null) {
            return;
        }
        SelectTouchPoints selectTouchPoints = new SelectTouchPoints();
        selectTouchPoints.copyTouchPoints(this.mOriginSelectTouchPoints);
        Matrix matrix = new Matrix();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
        matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
        selectTouchPoints.mPath.transform(matrix);
        this.mPenEngine.mEditOperator.mEditListener.onOutLineComplete(selectTouchPoints.mPath, (short) 1);
    }

    private void initSelectInfo(boolean z) {
        MathDataManager mathDataManager = this.mPenEngine.getCurveEngine().getMathDataManager();
        if (mathDataManager.getCopyAndPasteStatus() == 1) {
            mathDataManager.setCopyAndPasteStatus(2);
        } else {
            mathDataManager.setSelect(true);
        }
        boolean z2 = false;
        this.mIsReady = false;
        this.mVisibleWidth = this.mPenEngine.mCanvasOperator.getVisibleWidth();
        this.mVisibleHeight = this.mPenEngine.mCanvasOperator.getVisibleHeight();
        this.mSelectInfo = !this.mPenEngine.mDrawerWrapper.isInitialized ? null : createMultiSelectInfo(getRatioRectF(), this.mDataCurveList);
        List<DataCurve> list = this.mDataCurveList;
        if (list != null && list.size() == 1 && this.mDataCurveList.get(0).mCurveType != 0 && this.mPenEngine.mTouchEventOperator.enableShapeEdit) {
            z2 = true;
        }
        this.isShape = z2;
        if (this.mSelectInfo != null) {
            reset(getRatioRectF(), this.mDataCurveList);
        }
        Iterator<DataCurve> it = this.mDataCurveList.iterator();
        while (it.hasNext()) {
            it.next().mDataState = 2;
        }
        if (z) {
            this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurveList);
        }
        this.l = true;
        this.mPenEngine.mDrawerWrapper.refreshSelectCurveData(getRatioRectF());
        this.mIsReady = true;
    }

    private void initSelectInfo() {
        initSelectInfo(true);
    }

    private AbsSelectInfo createMultiSelectInfo(RectF rectF, List<DataCurve> list) {
        if (!this.mPenEngine.mDrawerWrapper.isInitialized || !this.isCanvasVisible) {
            return null;
        }
        this.mSelectInfo = new MultiSelectInfo(list);
        this.mAreaRect = PenEngineUtils.toIntRect(rectF);
        this.mEnableRefreshing = false;
        this.mNotDeleted = true;
        this.mFinishSelect = false;
        this.mEnableScale = this.mPenEngine.mCanvasOperator.isScale;
        this.mScaledAndTransRect = new Rect();
        return this.mSelectInfo;
    }

    private byte[] getCopyByteData() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byteArray = null;
        try {
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeFloat(this.mPenEngine.mCanvasOperator.getDrawRatio());
            RectF ratioRectF = getRatioRectF();
            dataOutputStream.writeFloat(ratioRectF.left);
            dataOutputStream.writeFloat(ratioRectF.top);
            dataOutputStream.writeFloat(ratioRectF.right);
            dataOutputStream.writeFloat(ratioRectF.bottom);
            byte[] bArrConvertTouchPointsToByteArray = SelectTouchPoints.convertTouchPointsToByteArray(this.mOriginSelectTouchPoints);
            dataOutputStream.writeInt(bArrConvertTouchPointsToByteArray.length);
            dataOutputStream.write(bArrConvertTouchPointsToByteArray);
            CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
            dataOutputStream.writeFloat(canvasOperator.isScale ? canvasOperator.getScaleVisibleTop() : 0.0f);
            dataOutputStream.writeInt(this.mDataCurveList.size());
            for (DataCurve dataCurve : this.mDataCurveList) {
                SelectCopyData.writeDataToStream(dataCurve, dataOutputStream);
                this.mAllPointSize += dataCurve.size(dataCurve.mSavePoints);
                this.mAllOriginPoints += dataCurve.size(dataCurve.mOriginPoints);
                this.mAllScaledPoints += dataCurve.size(dataCurve.mScaledPoints);
            }
            byteArray = byteArrayOutputStream.toByteArray();
            try {
                dataOutputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException unused) {
            }
            return byteArray;
        } catch (Throwable th) {
            try {
                dataOutputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException unused2) {
            }
            try {
                try {
                    throw th;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException unused3) {
                dataOutputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    private void addCurveDataToPool() {
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : this.mDataCurveList) {
            if (dataCurve.mDataState == 2) {
                dataCurve.mDataState = 1;
                arrayList.add(dataCurve);
            }
        }
        if (this.mNotInSelecting || arrayList.isEmpty()) {
            return;
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishSelect() throws InterruptedException {
        MathDataManager mathDataManager = this.mPenEngine.getCurveEngine().getMathDataManager();
        if (this.mPenEngine.getCurveEngine().getMathDataManager().getCopyAndPasteStatus() == 2) {
            mathDataManager.setCopyAndPasteStatus(0);
        }
        mathDataManager.setSelect(false);
        this.mPenEngine.mEditOperator.cancelOutlineGenerator();
        this.l = false;
        if (this.mSelectInfo != null) {
            RectF ratioRectF = getRatioRectF();
            if (this.mPenEngine.mDrawerWrapper.isInitialized) {
                deleteSelectOperate(ratioRectF, this.mSelectInfo);
                this.mPenEngine.mDrawStateManager.removeDrawerStateChangeListener(this.mDrawStateChangeCallback);
            }
        }
        this.mNotInSelecting = true;
        this.mSelectInfo = null;
        this.mDataCurveList.clear();
        if (this.mPenEngine.mEditOperator.mEditListener != null) {
            this.mPenEngine.mEditOperator.mEditListener.onObjectSelect(null);
        }
        this.mPenEngine.getCurveEngine().getSelectSystem().selectOff(null, false, this.selectOffState);
        this.selectOffState = 0;
    }

    private void c(int i) {
        IStep stepMove;
        StepMixed stepMixed;
        if (this.mSelectActionType == 8 || this.mSelectActionType == 28) {
            if (this.mDefaultPenSizeLimitInfo.a(this.mDataCurveList, this.mScale, this.mSelectActionType)) {
                this.mScale = this.mDefaultPenSizeLimitInfo.getScaleValue();
                this.mPenEngine.mEditOperator.mEditListener.onSelectLimit(this.mDefaultPenSizeLimitInfo.getLimitType(), this.mScale);
            } else {
                ILimitInfo iLimitInfo = this.mPenSizeLimitInfo;
                if (iLimitInfo != null && iLimitInfo.a(this.mDataCurveList, this.mScale, this.mSelectActionType)) {
                    this.mScale = this.mPenSizeLimitInfo.getScaleValue();
                    this.mPenEngine.mEditOperator.mEditListener.onSelectLimit(this.mPenSizeLimitInfo.getLimitType(), this.mScale);
                }
            }
        }
        RectF ratioRectF = getRatioRectF();
        Log.d(this.TAG, "selectActionType " + i);
        if (i == 4) {
            RectF rectF = new RectF(ratioRectF);
            rectF.offset(this.mMovePointF.x, this.mMovePointF.y);
            ratioRectF.union(rectF);
            stepMove = new StepMove(this.mPenEngine, this.mDataCurveList, getRatioRectF(ratioRectF), getRatioPointF(this.mMovePointF));
            this.mOriginSelectTouchPoints.doMove(this.mMovePointF.x, this.mMovePointF.y);
        } else {
            if (i == 8) {
                RectF rectF2 = new RectF(ratioRectF);
                Matrix matrix = new Matrix();
                matrix.setScale(this.mScale, this.mScale, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix.mapRect(rectF2);
                matrix.setRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix.mapRect(rectF2);
                ratioRectF.union(rectF2);
                stepMixed = new StepMixed(this.mPenEngine, this.mDataCurveList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mScale, this.mDegrees);
                this.mOriginSelectTouchPoints.doScale(this.mCenterPointF.x, this.mCenterPointF.y, this.mScale, this.mScale);
            } else if (i == 16) {
                RectF rectF3 = new RectF(ratioRectF);
                Matrix matrix2 = new Matrix();
                matrix2.setScale(this.mScale, this.mScale, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix2.mapRect(rectF3);
                matrix2.setRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix2.mapRect(rectF3);
                ratioRectF.union(rectF3);
                stepMixed = new StepMixed(this.mPenEngine, this.mDataCurveList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mScale, this.mDegrees);
                this.mOriginSelectTouchPoints.doRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
            } else if (i == 28) {
                RectF rectF4 = new RectF(ratioRectF);
                Matrix matrix3 = new Matrix();
                matrix3.setScale(this.mScale, this.mScale, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix3.mapRect(rectF4);
                matrix3.setRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
                matrix3.mapRect(rectF4);
                ratioRectF.union(rectF4);
                stepMixed = new StepMixed(this.mPenEngine, this.mDataCurveList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.mScale, this.mDegrees);
                this.mOriginSelectTouchPoints.doScale(this.mCenterPointF.x, this.mCenterPointF.y, this.mScale, this.mScale);
                this.mOriginSelectTouchPoints.doRotate(this.mDegrees, this.mCenterPointF.x, this.mCenterPointF.y);
            } else if (i == 132) {
                Matrix matrix4 = new Matrix();
                PointF pointF = this.e;
                matrix4.setScale(pointF.x, pointF.y, this.mCenterPointF.x, this.mCenterPointF.y);
                RectF rectF5 = new RectF(ratioRectF);
                matrix4.mapRect(rectF5);
                ratioRectF.union(rectF5);
                StepShapeEdit stepShapeEdit = new StepShapeEdit(this.mPenEngine, this.mDataCurveList, getRatioRectF(ratioRectF), getRatioPointF(this.mCenterPointF), this.e);
                PointF pointF2 = this.e;
                this.mOriginSelectTouchPoints.doScale(this.mCenterPointF.x, this.mCenterPointF.y, pointF2.x, pointF2.y);
                stepMove = stepShapeEdit;
            } else {
                stepMove = null;
            }
            stepMove = stepMixed;
        }
        if (stepMove != null) {
            this.mPenEngine.mStepManager.addStep(stepMove);
        }
        if (i == 4) {
            this.mCurveDataListManager.doMove(this.mDataCurveList, this.mMovePointF.x, this.mMovePointF.y);
        } else if (i == 28 || i == 16 || i == 8) {
            this.mCurveDataListManager.doScaleAndRotate(this.mDataCurveList, this.mCenterPointF.x, this.mCenterPointF.y, this.mScale, this.mDegrees);
            this.mTotalScale *= this.mScale;
            this.mTotalDegress += this.mDegrees;
        } else {
            if (i != 132) {
                return;
            }
            RectF ratioRectF2 = getRatioRectF();
            float fMax = Math.max(this.mVisibleWidth, this.mVisibleHeight);
            if (ratioRectF2.width() * this.e.x <= fMax) {
                float fHeight = ratioRectF2.height();
                PointF pointF3 = this.e;
                if (fHeight * pointF3.y <= fMax) {
                    this.mCurveDataListManager.pointsInterpolation(this.mDataCurveList, this.mCenterPointF, pointF3, this.mPenEngine.mTouchEventOperator.isScale(), this.mPenEngine.mCanvasOperator.getScaleInfo());
                    return;
                }
            }
            this.mPenEngine.mEditOperator.mEditListener.onSelectLimit(3, 1.0f);
        }
        this.mPenEngine.updateDataCurvePageIndex(this.mDataCurveList);
    }

    private RectF getMultiSelectObjectRect() {
        RectF rectF = new RectF();
        boolean z = false;
        for (DataCurve dataCurve : this.mDataCurveList) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (z) {
                rectF.union(saveRect);
            } else {
                rectF.set(saveRect);
                z = true;
            }
        }
        return rectF;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void startAction(int i) {
        super.startAction(i);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void stopAction() {
        super.stopAction();
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleStopAction();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopy() {
        this.mAllPointSize = 0;
        this.mAllOriginPoints = 0;
        this.mAllScaledPoints = 0;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoCopy();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopyAndPaste() {
        this.mAllPointSize = 0;
        this.mAllOriginPoints = 0;
        this.mAllScaledPoints = 0;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoCopyAndPaste();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doDelete(final boolean z) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4646x9f271986(z);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doDeleteJustCurve() {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoDeleteJustCurve();
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public Point getSelectBitmapSize() {
        Bitmap screenShotBitmap;
        Point point = new Point();
        if (this.mSelectInfo != null && (screenShotBitmap = getScreenShotBitmap()) != null) {
            point.x = screenShotBitmap.getWidth();
            point.y = screenShotBitmap.getHeight();
        }
        return new Point(point.x, point.y);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean checkCurveSelectLimit(float f) {
        if ((this.mSelectActionType & 8) != 8) {
            return false;
        }
        if (this.mDefaultPenSizeLimitInfo.a(this.mDataCurveList, f, this.mSelectActionType)) {
            return true;
        }
        ILimitInfo iLimitInfo = this.mPenSizeLimitInfo;
        return iLimitInfo != null && iLimitInfo.a(this.mDataCurveList, f, this.mSelectActionType);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public float getCurScaleValue() {
        float f = this.mScale;
        if ((this.mSelectActionType & 8) == 8) {
            if (this.mDefaultPenSizeLimitInfo.a(this.mDataCurveList, f, this.mSelectActionType)) {
                return this.mDefaultPenSizeLimitInfo.getScaleValue();
            }
            ILimitInfo iLimitInfo = this.mPenSizeLimitInfo;
            if (iLimitInfo != null && iLimitInfo.a(this.mDataCurveList, this.mScale, this.mSelectActionType)) {
                return this.mPenSizeLimitInfo.getScaleValue();
            }
        }
        return f;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean onScreenshots(Bitmap bitmap) {
        Bitmap screenShotBitmap;
        if (this.mSelectInfo == null || bitmap == null || (screenShotBitmap = getScreenShotBitmap()) == null) {
            return false;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = screenShotBitmap.getWidth();
        int height2 = screenShotBitmap.getHeight();
        Canvas canvas = new Canvas(bitmap);
        canvas.scale((width * 1.0f) / width2, (height * 1.0f) / height2);
        canvas.drawBitmap(screenShotBitmap, 0.0f, 0.0f, this.mPaint);
        screenShotBitmap.recycle();
        canvas.setBitmap(null);
        return true;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect() throws InterruptedException {
        this.mFinishSelect = true;
        finishSelect(false);
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectCurveObject
    public List<DataCurve> getCurveList() {
        return this.mDataCurveList;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public List<DataCurve> getSelectData() {
        return this.mDataCurveList;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectCurveObject
    public RectF getCurveListRect() {
        RectF ratioRectF = getRatioRectF();
        CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
        if (canvasOperator.isScale) {
            ScaleInfo scaleInfo = canvasOperator.getScaleInfo();
            Matrix matrix = new Matrix();
            matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
            matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
            matrix.mapRect(ratioRectF);
        }
        return PenEngineUtils.getCopyRectF(PenEngineUtils.toIntRectF(ratioRectF));
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectCurveObject
    public boolean doSelectedContour() {
        return this.doSelectedContour;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void onDraw(Canvas canvas) {
        if (this.mSelectInfo == null || !this.mEnableRefreshing || !this.mNotDeleted || this.mFinishSelect) {
            return;
        }
        canvas.save();
        drawSelectBitmap(canvas);
        canvas.restore();
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void forceRedraw() {
        if (this.mSelectInfo != null) {
            reset(getRatioRectF(), this.mDataCurveList);
        }
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    public void changeVisibleSize(final float f) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4645xcf8f979a(f);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    public void setSelectTouchPoint(SelectTouchPoints selectTouchPoints) {
        this.mOriginSelectTouchPoints = selectTouchPoints;
        SelectTouchPoints selectTouchPoints2 = new SelectTouchPoints();
        this.mCopiedSelectTouchPoints = selectTouchPoints2;
        selectTouchPoints2.copyTouchPoints(selectTouchPoints);
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject, xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect(boolean z) throws InterruptedException {
        if (this.mSelectInfo == null || this.p) {
            return;
        }
        super.finishSelect(z);
        addCurveDataToPool();
        if (z) {
            handleFinishSelect();
        } else {
            this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    this.f$0.handleFinishSelect();
                }
            });
        }
        this.mIsReady = true;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    public void selectTouchComplete(SelectTouchPoints selectTouchPoints) {
        RectF rectF = new RectF();
        RectF rectF2 = new RectF();
        this.mOriginSelectTouchPoints.mPath.computeBounds(rectF, true);
        SelectTouchPoints selectTouchPoints2 = this.mCopiedSelectTouchPoints;
        if (selectTouchPoints2 != null) {
            selectTouchPoints2.mPath.computeBounds(rectF2, true);
        }
        float fCenterX = rectF.centerX();
        float fCenterY = rectF.centerY();
        float fCenterX2 = rectF.centerX() - rectF2.centerX();
        float fCenterY2 = rectF.centerY() - rectF2.centerY();
        float f = this.mTotalDegress;
        float f2 = this.mTotalScale;
        selectTouchPoints.doMove(fCenterX2, fCenterY2 + (this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f));
        selectTouchPoints.doRotate(f, fCenterX, fCenterY);
        selectTouchPoints.doScale(fCenterX, fCenterY, f2, f2);
        Matrix matrix = new Matrix();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        matrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), scaleInfo.getScaleCenter().x, scaleInfo.getScaleCenter().y);
        matrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
        selectTouchPoints.mPath.transform(matrix);
        Matrix matrix2 = new Matrix();
        matrix.invert(matrix2);
        this.mOriginSelectTouchPoints.copyTouchPoints(selectTouchPoints);
        this.mOriginSelectTouchPoints.mPath.transform(matrix2);
        this.mPenEngine.mEditOperator.mEditListener.onOutLineComplete(selectTouchPoints.mPath, (short) 1);
        this.mCopiedSelectTouchPoints = null;
    }

    @Override // xc.engine.operate.edit.select.selectobject.BaseSelectObject
    protected void deleteSelectOperate(RectF rectF, AbsSelectInfo absSelectInfo) {
        super.deleteSelectOperate(rectF, absSelectInfo);
        if (absSelectInfo instanceof MultiSelectInfo) {
            release();
        }
        this.mEnableRefreshing = false;
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawSelectBitmap(Canvas canvas) {
        Bitmap bitmap;
        float f;
        float f2;
        Matrix matrix;
        int i;
        Bitmap bitmap2 = this.mCurrentBitmap;
        if (bitmap2 != null) {
            synchronized (bitmap2) {
                Bitmap bitmap3 = this.mCurrentBitmap;
                if (bitmap3 != null && !bitmap3.isRecycled()) {
                    int i2 = this.mScaledAndTransRect.left;
                    int i3 = this.mScaledAndTransRect.top;
                    if (!this.mRectAreaGreaterThanLimit || this.mVisibleBitmapRect == null || this.mSegmentBitmapList.isEmpty()) {
                        bitmap = null;
                    } else {
                        synchronized (this.mSegmentBitmapList) {
                            i2 = this.mVisibleBitmapRect.left;
                            i = this.mVisibleBitmapRect.top;
                            bitmap = this.mSegmentBitmapList.get(0);
                        }
                        i3 = i;
                    }
                    Bitmap bitmap4 = bitmap;
                    PointF offsetPointF = ((MultiSelectInfo) this.mSelectInfo).getOffsetPointF();
                    float offsetX = offsetPointF.x + i2 + this.mSelectInfo.getOffsetX();
                    float offsetY = offsetPointF.y + i3 + this.mSelectInfo.getOffsetY();
                    float mCenterX = this.mSelectInfo.getMCenterX() - offsetX;
                    float mCenterY = this.mSelectInfo.getMCenterY() - offsetY;
                    if (!this.mEnableScale || (matrix = this.mMatrix) == null) {
                        canvas.translate(offsetX, offsetY);
                        canvas.rotate(this.mDegrees, mCenterX, mCenterY);
                        canvas.scale(this.mScale, this.mScale, mCenterX, mCenterY);
                        f = 0.0f;
                        f2 = 0.0f;
                    } else {
                        canvas.concat(matrix);
                        f2 = offsetY;
                        f = offsetX;
                    }
                    if (bitmap4 != null) {
                        drawSegmentBitmapOnCanvas(canvas, true, bitmap4, f, f2);
                    } else {
                        canvas.drawBitmap(this.mCurrentBitmap, f, f2, this.mPaint);
                    }
                }
            }
        }
    }

    private void drawSegmentBitmapOnCanvas(Canvas canvas, boolean z, Bitmap bitmap, float f, float f2) {
        float f3;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (z) {
            Bitmap bitmap2 = this.mCurrentBitmap;
            if (bitmap2 != null) {
                synchronized (bitmap2) {
                    drawSegmentedBitmap(canvas, bitmap, this.mCurrentBitmap, f, f2, 4);
                }
            }
            f3 = 1.5f / this.mScale;
        } else {
            f3 = 0.0f;
        }
        canvas.drawBitmap(bitmap, f + f3, f3 + f2, (Paint) null);
        for (int i = 1; i < this.mSegmentBitmapList.size(); i++) {
            if (!z || i != 4) {
                synchronized (this.mSegmentBitmapList) {
                    drawSegmentedBitmap(canvas, bitmap, this.mSegmentBitmapList.get(i), f, f2, i);
                }
            }
        }
    }

    private void drawSegmentedBitmap(Canvas canvas, Bitmap bitmap, Bitmap bitmap2, float f, float f2, int i) {
        float f3;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        int i2 = i % 3;
        int i3 = i / 3;
        float width = f + (bitmap.getWidth() * i2);
        float height = f2 + (bitmap.getHeight() * i3);
        if (i != 4) {
            float f4 = 1.5f / this.mScale;
            if (i2 == 0) {
                f3 = f4;
            } else {
                f3 = i2 == 2 ? -f4 : 0.0f;
            }
            if (i3 != 0 && i3 == 2) {
                f4 = -f4;
            }
            canvas.drawBitmap(bitmap2, width + f3, f4 + height, this.mPaint);
        } else {
            f3 = 0.0f;
        }
        canvas.drawBitmap(bitmap2, width + f3, height + 0.0f, this.mPaint);
    }

    private Bitmap getScreenShotBitmap() {
        List<Bitmap> list;
        if (!this.mRectAreaGreaterThanLimit || (list = this.mSegmentBitmapList) == null || list.isEmpty()) {
            Bitmap bitmap = this.mCurrentBitmap;
            if (bitmap == null) {
                return null;
            }
            synchronized (bitmap) {
                if (this.mCurrentBitmap.isRecycled()) {
                    return null;
                }
                return this.mCurrentBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
        }
        synchronized (this.mSegmentBitmapList) {
            Bitmap bitmap2 = this.mSegmentBitmapList.get(0);
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
                composeBeyondLimitOutputBitmap(this.mSegmentBitmapList, bitmapCreateBitmap);
                return bitmapCreateBitmap;
            }
            return null;
        }
    }

    private void composeBeyondLimitOutputBitmap(List<Bitmap> list, Bitmap bitmap) {
        float fMin = Math.min(this.mVisibleWidth / this.mVisibleBitmapRect.width(), this.mVisibleHeight / this.mVisibleBitmapRect.height());
        Log.d(this.TAG, "composeOutputBitmap, scale: " + fMin + ", outputBitmap size: [" + bitmap.getWidth() + ", " + bitmap.getHeight() + "]");
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmapTransformScaledBitmap = transformScaledBitmap(list.get(i), fMin);
            int width = bitmapTransformScaledBitmap.getWidth();
            int height = bitmapTransformScaledBitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmapTransformScaledBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            int[] iArr2 = new int[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(iArr2, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int width2 = ((i / 3) * height * bitmap.getWidth()) + ((i % 3) * width);
            for (int i2 = 0; i2 < height; i2++) {
                System.arraycopy(iArr, i2 * width, iArr2, width2, width);
                width2 += bitmap.getWidth();
            }
            bitmap.setPixels(iArr2, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    private Bitmap transformScaledBitmap(Bitmap bitmap, float f) {
        float width = bitmap.getWidth() * f;
        float height = bitmap.getHeight() * f;
        new Matrix().setScale(f, f);
        return Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);
    }

    private boolean isRectWithinLimit() {
        if (!this.mRectAreaGreaterThanLimit || this.mSelectInfo == null) {
            return true;
        }
        return this.mBitmapRect.contains(this.mScaledAndTransRect.left + ((int) ((MultiSelectInfo) this.mSelectInfo).getOffsetPointF().x), this.mScaledAndTransRect.top + ((int) ((MultiSelectInfo) this.mSelectInfo).getOffsetPointF().y));
    }

    private boolean isRectAreaGreaterThanLimit(Rect rect) {
        boolean z = ((float) (((long) rect.height()) * ((long) rect.width()))) > 6250000.0f;
        this.mRectAreaGreaterThanLimit = z;
        return z;
    }

    private void releaseSegmentBitmap() {
        List<Bitmap> list = this.mSegmentBitmapList;
        if (list == null || list.isEmpty()) {
            return;
        }
        synchronized (this.mSegmentBitmapList) {
            for (Bitmap bitmap : this.mSegmentBitmapList) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            this.mSegmentBitmapList.clear();
        }
    }

    private void updateBitmaps() {
        List<BitmapRectInfo> list;
        releaseCurBitmap();
        this.mCurrentBitmap = this.mLastBitmap;
        releaseSegmentBitmap();
        if (!this.mRectAreaGreaterThanLimit || (list = this.mBitmapRectInfoList) == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mBitmapRectInfoList.size(); i++) {
            this.mSegmentBitmapList.add(this.mBitmapRectInfoList.get(i).mBitmap);
        }
    }

    private BitmapRectInfo createBitmapRectInfo(List<DataCurve> list, int i, int i2, int i3, int i4, int i5, boolean z) {
        Rect rect = new Rect(i2, i3, i4, i5);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        if (i == 4) {
            this.mLastBitmap = bitmapCreateBitmap;
        }
        BitmapRectInfo bitmapRectInfo = new BitmapRectInfo();
        bitmapRectInfo.mBitmap = bitmapCreateBitmap;
        bitmapRectInfo.mRect = rect;
        this.mBitmapRectInfoList.add(bitmapRectInfo);
        return bitmapRectInfo;
    }

    private void calculateBitmapRect(List<DataCurve> list, boolean z) {
        if (this.mSegmentBitmapList == null) {
            this.mSegmentBitmapList = new ArrayList(9);
        }
        float f = this.mVisibleWidth;
        float f2 = this.mVisibleHeight;
        this.mBitmapRect = new Rect((int) (-f), (int) (-f2), (int) (f * 2.0f), (int) (f2 * 2.0f));
        this.mVisibleBitmapRect = new Rect(this.mBitmapRect);
        Rect rect = new Rect(this.mVisibleBitmapRect);
        rect.offset((int) (-this.mPenEngine.mCanvasOperator.getScaleInfo().getTranslate().x), (int) (-this.mPenEngine.mCanvasOperator.getScaleInfo().getTranslate().y));
        if (this.mBitmapRectInfoList == null) {
            this.mBitmapRectInfoList = new ArrayList(9);
        }
        this.mBitmapRectInfoList.clear();
        Rect rect2 = createBitmapRectInfo(list, 0, rect.left, rect.top, rect.left + ((int) Math.ceil(rect.width() / 3.0f)), rect.top + ((int) Math.ceil(rect.height() / 3.0f)), z).mRect;
        for (int i = 1; i < 9; i++) {
            int iWidth = rect2.left + (rect2.width() * (i % 3));
            int iHeight = rect2.top + (rect2.height() * (i / 3));
            createBitmapRectInfo(list, i, iWidth, iHeight, iWidth + rect2.width(), iHeight + rect2.height(), z);
        }
    }

    private void reset(RectF rectF, List<DataCurve> list) {
        this.mAreaRect.set(PenEngineUtils.toIntRect(rectF));
        MultiSelectInfo multiSelectInfo = (MultiSelectInfo) this.mSelectInfo;
        multiSelectInfo.setDataList(list);
        Rect intRect = this.mEnableScale ? PenEngineUtils.toIntRect(ScaleDataUtil.toScaleNotOffsetRectF(rectF, this.mPenEngine.mCanvasOperator.getScaleInfo())) : this.mAreaRect;
        Rect intRect2 = PenEngineUtils.toIntRect(ScaleDataUtil.getScaleAndTranslateRect(rectF, this.mPenEngine.mCanvasOperator.getScaleInfo()));
        if (isRectAreaGreaterThanLimit(intRect2)) {
            calculateBitmapRect(list, this.mEnableScale);
        } else {
            this.mLastBitmap = Bitmap.createBitmap(intRect.width(), intRect.height(), Bitmap.Config.ARGB_8888);
        }
        this.mMatrix = null;
        this.mReentrantLock.lock();
        updateBitmaps();
        this.mScaledAndTransRect.set(intRect2);
        multiSelectInfo.reset();
        this.mReentrantLock.unlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release() {
        this.mReentrantLock.lock();
        Bitmap bitmap = this.mLastBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mLastBitmap.recycle();
            this.mLastBitmap = null;
        }
        releaseCurBitmap();
        releaseSegmentBitmap();
        List<BitmapRectInfo> list = this.mBitmapRectInfoList;
        if (list != null && !list.isEmpty()) {
            for (BitmapRectInfo bitmapRectInfo : this.mBitmapRectInfoList) {
                Bitmap bitmap2 = bitmapRectInfo.mBitmap;
                if (bitmap2 != null && !bitmap2.isRecycled()) {
                    bitmapRectInfo.mBitmap.recycle();
                }
            }
            this.mBitmapRectInfoList.clear();
        }
        this.mReentrantLock.unlock();
    }

    private void releaseCurBitmap() {
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            synchronized (bitmap) {
                if (!this.mCurrentBitmap.isRecycled()) {
                    this.mCurrentBitmap.recycle();
                }
                this.mCurrentBitmap = null;
            }
        }
    }
}
