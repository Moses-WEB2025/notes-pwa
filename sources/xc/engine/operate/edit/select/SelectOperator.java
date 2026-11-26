package xc.engine.operate.edit.select;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.math.iink.manager.MathDataManager;
import xc.engine.operate.edit.IEditListener;
import xc.engine.operate.edit.select.selectobject.IEditSelectShapeObject;
import xc.engine.operate.edit.select.selectobject.ISelectCurveObject;
import xc.engine.operate.edit.select.selectobject.ISelectObject;
import xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject;
import xc.engine.operate.edit.select.selectobject.ShapeSelectObject;
import xc.engine.operate.step.type.StepDeleteLine;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.PageSizeConfig;
import xc.engine.utils.TouchEventUtils;
import xc.engine.utils.XLog;

/* JADX INFO: loaded from: classes6.dex */
public class SelectOperator implements ISelectOperator, IEditListener {
    public static final float DUPLICATE_OFFSET = 50.0f;
    private static final String RECEIVER_ACTION_RENDER = "com.miui.creation.render";
    private static final String TAG = "SelectOperator";
    private List<DataCurve> deleteRecognizeCurveList;
    private RectF deleteRectF;
    private int mCopyPointCount;
    private byte[] mCurPasteInfo;
    private float mCurSelectLimit;
    private OnSelectModelListener mItemViewSelectModelListener;
    private onSelectStepChangedListener mOnSelectStepChangedListener;
    private final PenEngine mPenEngine;
    private SelectSystem.RenderTaskListener mRenderTaskListener;
    private ISelectObject mSelectObject;
    private final SRectF mSelectDataRectF = new SRectF();
    private final SRectF mSelectDataRectFDst = new SRectF();
    private Path mSelectOutPath = null;
    private final List<TouchPoint> mTouchPoints = new ArrayList();
    private final Matrix mDrawMatrix = new Matrix();
    private volatile int mCurSelectAction = 0;
    private int mShapeEditType = 0;
    private final PointF mOffsetPointF = new PointF();
    private final PointF mTotalOffsetPointF = new PointF();
    private PointF mStartShapeEditPoint = new PointF();
    private PointF mCenterShapeEditPoint = new PointF();
    private final PointF mDownPoint = new PointF();
    private final PointF mLastPoint = new PointF();
    private float mScaleShapeValue = 1.0f;
    private float mDownAngle = 0.0f;
    private float mPreRotateAngle = 0.0f;
    private float mCurRotateAngle = 0.0f;
    private float mScaleValue = 1.0f;
    private int mObjectAction = 0;
    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;
    private boolean isEnablePointSelect = true;
    private boolean isDeleteCallBack = false;
    private boolean cancelIsUndo = false;
    private boolean hasDown = false;
    private boolean isEnableSelect = true;
    private boolean cancelSelect = false;
    private boolean isSelectLimit = false;
    private boolean mHadPointSelected = false;
    public float mAngle = 0.0f;
    private final int LIMIT_RENDER_MIN_CURVE_COUNT = 500;
    private boolean selectState = false;

    public SelectOperator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setSelectListenerI(OnSelectModelListener onSelectModelListener) {
        this.mItemViewSelectModelListener = onSelectModelListener;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setOnSelectStepChangedListener(onSelectStepChangedListener onselectstepchangedlistener) {
        this.mOnSelectStepChangedListener = onselectstepchangedlistener;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setRenderTaskListener(SelectSystem.RenderTaskListener renderTaskListener) {
        this.mRenderTaskListener = renderTaskListener;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isShapeEditMode() {
        ISelectObject iSelectObject = this.mSelectObject;
        return iSelectObject != null && (iSelectObject instanceof IEditSelectShapeObject);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setSelectLimit(boolean z, float f) {
        this.isSelectLimit = z;
        this.mCurSelectLimit = f;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setPaste(byte[] bArr, float f, float f2) {
        this.mCurPasteInfo = bArr;
        doPaste(bArr, f, f2);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setSelectAction(int i) {
        this.mCurSelectAction = i;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void cancelSelect(boolean z) {
        this.hasDown = false;
        this.cancelIsUndo = z;
        Log.d(TAG, "cancelSelect: " + this.mObjectAction);
        int i = this.mObjectAction;
        if (i == 0) {
            startCancelSelect(z);
        } else if (i == 1) {
            this.mObjectAction = 3;
        }
        MathDataManager mathDataManager = this.mPenEngine.getCurveEngine().getMathDataManager();
        if (mathDataManager.isCanCancelMathSelect()) {
            mathDataManager.notifyCancelSelectCallBack();
        } else if (mathDataManager.getCurrentBehavior() == 2) {
            mathDataManager.setSelectState(0);
        }
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onSelectedDeletedEnd() {
        cancelSelect(false);
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener == null || !this.isDeleteCallBack) {
            return;
        }
        onSelectModelListener.onSelectedDeletedEnd();
    }

    private void startCancelSelect(boolean z) {
        if (z) {
            cancelAction();
        }
        finishSelect();
        reset();
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onCancelSelect();
        }
        Intent intent = new Intent();
        intent.setAction(RECEIVER_ACTION_RENDER);
        LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).sendBroadcast(intent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, boolean z) {
        int action = motionEvent.getAction();
        if (action == 0) {
            onDown(motionEvent);
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                onMove(motionEvent);
                return true;
            }
            if (action != 3) {
                return true;
            }
        }
        onUp(motionEvent, z);
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onTouchEvent(motionEvent, true);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doCopy(boolean z) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.doCopy();
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doCopyAndPaste(boolean z) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.doCopyAndPaste();
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doDelete(boolean z) {
        doDelete(z, false);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doDelete(boolean z, boolean z2) {
        this.isDeleteCallBack = z;
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.doDelete(z2);
        }
        cancelSelect(false);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doDeleteJustCurve() {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.SelectOperator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDoDeleteJustCurve();
            }
        });
        cancelSelect(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDoDeleteJustCurve() {
        ArrayList arrayList = new ArrayList();
        List<DataCurve> list = this.deleteRecognizeCurveList;
        if (list == null) {
            return;
        }
        for (DataCurve dataCurve : list) {
            if (dataCurve.mCurveType == 0 && dataCurve.getType() != 28) {
                dataCurve.mDataState = 3;
                arrayList.add(dataCurve);
            }
        }
        if (arrayList.size() == 0) {
            return;
        }
        if (this.selectState) {
            this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(arrayList);
        }
        this.mPenEngine.mDrawerWrapper.refreshSelectCurveData(getDeleteRatioRectF());
        this.mPenEngine.mStepManager.addStep(new StepDeleteLine(this.mPenEngine, arrayList));
    }

    public RectF getDeleteRatioRectF() {
        RectF rectF = new RectF(this.deleteRectF);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        rectF.left *= drawRatio;
        rectF.top *= drawRatio;
        rectF.right *= drawRatio;
        rectF.bottom *= drawRatio;
        return rectF;
    }

    private SRectF getSelectRect(ISelectObject iSelectObject) {
        if (iSelectObject == null) {
            return null;
        }
        if (iSelectObject.getDataType() == 1) {
            RectF curveListRect = ((ISelectCurveObject) iSelectObject).getCurveListRect();
            SRectF sRectF = new SRectF();
            if (curveListRect != null) {
                sRectF.set(curveListRect);
            }
            return sRectF;
        }
        if (iSelectObject.getDataType() != 28) {
            return null;
        }
        RectF shapeDrawRect = ((IEditSelectShapeObject) iSelectObject).getShapeDrawRect();
        SRectF sRectF2 = new SRectF();
        if (shapeDrawRect != null) {
            sRectF2.set(shapeDrawRect);
        }
        return sRectF2;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setMultiSelectRectF(SRectF sRectF) {
        this.mCenterX = sRectF.centerX();
        this.mCenterY = sRectF.centerY();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public List<ICurve> getCurrentSelectCurves() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof ISelectCurveObject) || ((ISelectCurveObject) iSelectObject).getCurveList().isEmpty()) {
            return null;
        }
        List<DataCurve> curveList = ((ISelectCurveObject) this.mSelectObject).getCurveList();
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : curveList) {
            if (dataCurve.getShapeType() == 0 && dataCurve.getType() != 28) {
                arrayList.add(dataCurve);
            }
        }
        return arrayList;
    }

    public List<DataCurve> getDeleteCurves() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof ISelectCurveObject) || ((ISelectCurveObject) iSelectObject).getCurveList().isEmpty()) {
            return null;
        }
        List<DataCurve> curveList = ((ISelectCurveObject) this.mSelectObject).getCurveList();
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : curveList) {
            if (dataCurve.getShapeType() == 0 && dataCurve.getType() != 28) {
                arrayList.add(dataCurve);
            }
        }
        return arrayList;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void copyTempSelectData() {
        this.deleteRecognizeCurveList = getDeleteCurves();
        this.selectState = ((MultiSelectCurveObject) this.mSelectObject).mNotInSelecting;
        this.deleteRectF = ((MultiSelectCurveObject) this.mSelectObject).mSelectObjectRect;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean checkSelectLimit(float f) {
        if (this.mSelectObject == null) {
            return false;
        }
        if (this.mCurSelectAction == 16 || this.mCurSelectAction == 8 || this.mCurSelectAction == 28 || this.mCurSelectAction == 132) {
            return this.mSelectObject.checkCurveSelectLimit(f);
        }
        return false;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public float getCurScaleValue() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            return iSelectObject.getCurScaleValue();
        }
        return 1.0f;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isPointSelected() {
        return this.mSelectObject != null && this.mHadPointSelected;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public Bitmap getSelectScreenshotBySize(int i) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null) {
            return null;
        }
        Point selectBitmapSize = iSelectObject.getSelectBitmapSize();
        if (selectBitmapSize.x > i || selectBitmapSize.y > i) {
            return getSelectScreenshot();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mSelectObject.onScreenshots(bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public Bitmap getSelectScreenshot() {
        return this.mPenEngine.getCurveEngine().getSelectSystem().getSelectContentBitmap();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public List<ICurve> getCurrentAllSelectCurves() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null) {
            return null;
        }
        if (!(iSelectObject instanceof MultiSelectCurveObject) && !(iSelectObject instanceof ShapeSelectObject)) {
            return null;
        }
        List<DataCurve> selectData = iSelectObject.getSelectData();
        ArrayList arrayList = new ArrayList();
        if (selectData != null) {
            arrayList.addAll(selectData);
        }
        return arrayList;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isSelectReady() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            return iSelectObject.isReady();
        }
        return true;
    }

    private void doPaste(byte[] bArr, float f, float f2) {
        PenEngine penEngine = this.mPenEngine;
        if (penEngine == null || bArr == null) {
            return;
        }
        penEngine.getCurveEngine().getMathDataManager().setBehavior(2);
        this.mPenEngine.getEditOperator().doPaste(bArr, new PointF(f, f2), true);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setExtraSelectPoint(List<TouchPoint> list, boolean z) {
        setExtraSelectPoint(list, z, false);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setExtraSelectPoint(List<TouchPoint> list, List<DataCurve> list2, boolean z, boolean z2) {
        if (this.mPenEngine != null) {
            this.mTouchPoints.addAll(list);
            if (!z || this.mTouchPoints.size() <= 1) {
                return;
            }
            doDirectSelect(this.mTouchPoints, list2, z2, true);
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setExtraSelectPoint(List<TouchPoint> list, boolean z, boolean z2) {
        if (this.mPenEngine != null) {
            this.mTouchPoints.addAll(list);
            if (z) {
                if (this.mTouchPoints.size() > 1) {
                    doPathSelect(this.mTouchPoints, z2, true);
                } else if (this.mTouchPoints.size() == 1) {
                    doPointSelect(this.mTouchPoints.get(0));
                }
            }
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doRectSelect(RectF rectF, List<DataCurve> list, boolean z) {
        if (rectF == null || rectF.isEmpty()) {
            Log.w("MathSelect", "doRectSelect: Rect NULL");
            OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
            if (onSelectModelListener != null) {
                onSelectModelListener.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList, z);
                return;
            }
            return;
        }
        Log.d("MathSelect", "  - isEnableSelect: " + this.isEnableSelect);
        if (!this.isEnableSelect) {
            Log.w("MathSelect", "doRectSelect: DisEnableSelect，setEnableSelect(true)");
            setEnableSelect(true);
        }
        List<TouchPoint> listConvertRectToTouchPoints = convertRectToTouchPoints(rectF);
        this.mTouchPoints.clear();
        setExtraSelectPoint(listConvertRectToTouchPoints, list, true, z);
        Log.d("MathSelect", "doRectSelect: success");
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doRectSelect(RectF rectF, boolean z) {
        if (rectF == null || rectF.isEmpty()) {
            Log.w("MathSelect", "doRectSelect: Rect NULL");
            OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
            if (onSelectModelListener != null) {
                onSelectModelListener.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList, z);
                return;
            }
            return;
        }
        Log.d("MathSelect", "  - isEnableSelect: " + this.isEnableSelect);
        if (!this.isEnableSelect) {
            Log.w("MathSelect", "doRectSelect: DisEnableSelect，setEnableSelect(true)");
            setEnableSelect(true);
        }
        List<TouchPoint> listConvertRectToTouchPoints = convertRectToTouchPoints(rectF);
        this.mTouchPoints.clear();
        setExtraSelectPoint(listConvertRectToTouchPoints, true, z);
        Log.d("MathSelect", "doRectSelect: success");
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void doRectSelect(RectF rectF) {
        doRectSelect(rectF, false);
    }

    private List<TouchPoint> convertRectToTouchPoints(RectF rectF) {
        ArrayList arrayList = new ArrayList();
        long jCurrentTimeMillis = System.currentTimeMillis();
        float fMax = Math.max(3.0f, Math.min(8.0f, ((rectF.width() + rectF.height()) * 2.0f) / 80.0f));
        int iMax = Math.max(3, (int) (rectF.width() / fMax));
        int i = 0;
        for (int i2 = 0; i2 <= iMax; i2++) {
            arrayList.add(new TouchPoint(rectF.left + ((rectF.width() * i2) / iMax), rectF.top, ((long) (i * 4)) + jCurrentTimeMillis));
            i++;
        }
        int iMax2 = Math.max(3, (int) (rectF.height() / fMax));
        for (int i3 = 1; i3 <= iMax2; i3++) {
            arrayList.add(new TouchPoint(rectF.right, rectF.top + ((rectF.height() * i3) / iMax2), ((long) (i * 4)) + jCurrentTimeMillis));
            i++;
        }
        int iMax3 = Math.max(3, (int) (rectF.width() / fMax));
        for (int i4 = 1; i4 <= iMax3; i4++) {
            arrayList.add(new TouchPoint(rectF.right - ((rectF.width() * i4) / iMax3), rectF.bottom, ((long) (i * 4)) + jCurrentTimeMillis));
            i++;
        }
        int iMax4 = Math.max(3, (int) (rectF.height() / fMax));
        for (int i5 = 1; i5 < iMax4; i5++) {
            arrayList.add(new TouchPoint(rectF.left, rectF.bottom - ((rectF.height() * i5) / iMax4), ((long) (i * 4)) + jCurrentTimeMillis));
            i++;
        }
        arrayList.add(new TouchPoint(rectF.left, rectF.top, jCurrentTimeMillis + ((long) (i * 4))));
        return arrayList;
    }

    private void doPathSelect(List<TouchPoint> list) {
        doPathSelect(list, false, true);
    }

    private void doPathSelect(List<TouchPoint> list, boolean z, boolean z2) {
        if (this.mPenEngine != null && this.isEnableSelect) {
            OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
            if (onSelectModelListener != null) {
                onSelectModelListener.onSelectedHashCode(list.hashCode());
            }
            ISelectObject iSelectObject = this.mSelectObject;
            if (iSelectObject != null) {
                iSelectObject.finishSelect();
                this.mSelectObject = null;
            }
            this.mHadPointSelected = false;
            this.mPenEngine.getEditOperator().doPathSelect(list, z, z2);
            return;
        }
        OnSelectModelListener onSelectModelListener2 = this.mItemViewSelectModelListener;
        if (onSelectModelListener2 != null) {
            onSelectModelListener2.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList);
        }
    }

    private void doDirectSelect(List<TouchPoint> list, List<DataCurve> list2, boolean z, boolean z2) {
        if (this.mPenEngine != null && this.isEnableSelect) {
            OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
            if (onSelectModelListener != null) {
                onSelectModelListener.onSelectedHashCode(list.hashCode());
            }
            ISelectObject iSelectObject = this.mSelectObject;
            if (iSelectObject != null) {
                iSelectObject.finishSelect();
                this.mSelectObject = null;
            }
            this.mHadPointSelected = false;
            this.mPenEngine.getEditOperator().doDirectSelect(list, list2, z, z2);
            return;
        }
        OnSelectModelListener onSelectModelListener2 = this.mItemViewSelectModelListener;
        if (onSelectModelListener2 != null) {
            onSelectModelListener2.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList);
        }
    }

    private void doPointSelect(TouchPoint touchPoint) {
        if (this.mPenEngine != null && this.isEnableSelect && this.isEnablePointSelect) {
            ISelectObject iSelectObject = this.mSelectObject;
            if (iSelectObject != null) {
                iSelectObject.finishSelect();
                this.mSelectObject = null;
            }
            this.mHadPointSelected = true;
            this.mPenEngine.getEditOperator().doPointSelect(touchPoint);
            return;
        }
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList);
        }
    }

    private void reset() {
        this.mSelectDataRectF.setEmpty();
        this.mSelectDataRectFDst.setEmpty();
        this.mDownAngle = 0.0f;
        this.mPreRotateAngle = 0.0f;
        this.mCurRotateAngle = 0.0f;
        this.mDrawMatrix.reset();
        this.mTouchPoints.clear();
        setSelectAction(0);
    }

    private void onUp(MotionEvent motionEvent, boolean z) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.hasDown) {
            if (isSelectObjectNotEmpty()) {
                int i = this.mCurSelectAction;
                if (i == 4) {
                    doMoving(x, y);
                    if (TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent)) {
                        this.cancelSelect = true;
                    }
                    doMoveEnd(motionEvent.getAction());
                    return;
                }
                if (i == 8) {
                    doMixingScale(x, y);
                    doMixedScaleEnd(motionEvent.getAction());
                    List<ICurve> currentAllSelectCurves = getCurrentAllSelectCurves();
                    if (this.mRenderTaskListener == null || currentAllSelectCurves == null || currentAllSelectCurves.size() <= 500) {
                        return;
                    }
                    this.mRenderTaskListener.onRendering();
                    return;
                }
                if (i != 16) {
                    if (i == 28) {
                        doMixing(x, y);
                        doMixedEnd(motionEvent.getAction());
                        return;
                    } else {
                        if (i != 132) {
                            return;
                        }
                        doShapeEdit(x, y);
                        doShapeEditEnd(motionEvent.getAction());
                        return;
                    }
                }
                doMixingRotate(x, y);
                doMixedRotateEnd(motionEvent.getAction());
                List<ICurve> currentAllSelectCurves2 = getCurrentAllSelectCurves();
                if (this.mRenderTaskListener == null || currentAllSelectCurves2 == null || currentAllSelectCurves2.size() <= 500) {
                    return;
                }
                this.mRenderTaskListener.onRendering();
                return;
            }
            if (this.mCurSelectAction == 0) {
                OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
                if (onSelectModelListener != null) {
                    onSelectModelListener.setSelectStateI(true);
                }
                doSelected(motionEvent, z);
            }
        }
    }

    private void onUp(MotionEvent motionEvent) {
        onUp(motionEvent, true);
    }

    private void onMove(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        long eventTime = motionEvent.getEventTime();
        if (this.hasDown) {
            if (isSelectObjectNotEmpty()) {
                int i = this.mCurSelectAction;
                if (i == 4) {
                    doMoving(x, y);
                    return;
                }
                if (i == 8) {
                    doMixingScale(x, y);
                    return;
                }
                if (i == 16) {
                    doMixingRotate(x, y);
                    return;
                } else if (i == 28) {
                    doMixing(x, y);
                    return;
                } else {
                    if (i != 132) {
                        return;
                    }
                    doShapeEdit(x, y);
                    return;
                }
            }
            if (this.mCurSelectAction == 0) {
                doSelecting(x, y, eventTime);
            }
        }
    }

    private void onDown(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        long eventTime = motionEvent.getEventTime();
        this.hasDown = true;
        this.mDownPoint.set(x, y);
        if (isSelectObjectNotEmpty()) {
            int i = this.mCurSelectAction;
            if (i == 4) {
                doMoveStart(x, y);
                return;
            }
            if (i == 8) {
                doMixedScaleStart(x, y);
                return;
            }
            if (i == 16) {
                doMixedRotateStart(x, y);
                return;
            } else if (i == 28) {
                doMixedStart(x, y);
                return;
            } else {
                if (i != 132) {
                    return;
                }
                doShapeEditStart(x, y);
                return;
            }
        }
        if (this.mCurSelectAction == 0) {
            startSelect(x, y, eventTime);
        }
    }

    private void doShapeEditEnd(int i) {
        if (i == 3) {
            cancelSelect(true);
        } else {
            stopAction();
        }
        this.mScaleShapeValue = 1.0f;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onEndAction(0);
        }
    }

    private void doShapeEdit(float f, float f2) {
        SelectOperator selectOperator;
        if (this.mShapeEditType == 9) {
            PointF pointF = new PointF(f, f2);
            if (((IEditSelectShapeObject) this.mSelectObject).getShapeProp().getShapeType() == 3 || ((IEditSelectShapeObject) this.mSelectObject).getShapeProp().getShapeType() == 6) {
                calculateProjectivePoint(this.mStartShapeEditPoint, this.mCenterShapeEditPoint, new PointF(f, f2), pointF);
            }
            float f3 = pointF.x - this.mLastPoint.x;
            float f4 = pointF.y - this.mLastPoint.y;
            this.mLastPoint.set(pointF);
            ((IEditSelectShapeObject) this.mSelectObject).doRadiusEdit(f3, f4);
            selectOperator = this;
        } else {
            selectOperator = this;
            float fCountScaleValue = selectOperator.countScaleValue(this.mCenterShapeEditPoint.x, this.mCenterShapeEditPoint.y, f, f2, this.mLastPoint);
            if (!selectOperator.checkSelectLimit(fCountScaleValue)) {
                selectOperator.mScaleShapeValue = fCountScaleValue;
                selectOperator.mLastPoint.set(f, f2);
            } else {
                selectOperator.mScaleShapeValue = 1.0f;
            }
            float fCountAngle = selectOperator.countAngle(f - selectOperator.mCenterShapeEditPoint.x, f2 - selectOperator.mCenterShapeEditPoint.y);
            float f5 = fCountAngle - selectOperator.mPreRotateAngle;
            selectOperator.mPreRotateAngle = fCountAngle;
            ((IEditSelectShapeObject) selectOperator.mSelectObject).doShapeEdit(selectOperator.mShapeEditType, selectOperator.mCenterShapeEditPoint, selectOperator.mScaleShapeValue, f5);
        }
        OnSelectModelListener onSelectModelListener = selectOperator.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDoingAction(132, selectOperator.mSelectDataRectFDst);
        }
        selectOperator.mPenEngine.getCurveEngine().requestMultiBufferRender(MiCurveEngine.ERefreshReason.Force);
    }

    private void doShapeEditStart(float f, float f2) {
        startAction();
        this.mPreRotateAngle = countAngle(f - this.mCenterShapeEditPoint.x, f2 - this.mCenterShapeEditPoint.y);
        this.mLastPoint.set(f, f2);
        if (this.mShapeEditType == 9 && (((IEditSelectShapeObject) this.mSelectObject).getShapeProp().getShapeType() == 3 || ((IEditSelectShapeObject) this.mSelectObject).getShapeProp().getShapeType() == 6)) {
            PointF pointF = new PointF(f, f2);
            PointF pointF2 = new PointF();
            calculateProjectivePoint(this.mStartShapeEditPoint, this.mCenterShapeEditPoint, pointF, pointF2);
            this.mLastPoint.set(pointF2);
        }
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onStartAction(132);
        }
    }

    private void calculateProjectivePoint(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        if (pointF.x == pointF2.x) {
            pointF4.x = pointF.x;
            pointF4.y = pointF3.y;
        } else if (pointF.y == pointF2.y) {
            pointF4.x = pointF3.x;
            pointF4.y = pointF.y;
        } else {
            float f = (pointF.y - pointF2.y) / (pointF.x - pointF2.x);
            float f2 = pointF.y - (pointF.x * f);
            pointF4.x = (((pointF3.y - f2) * f) + pointF3.x) / ((f * f) + 1.0f);
            pointF4.y = (f * pointF4.x) + f2;
        }
    }

    private void doMoveEnd(int i) {
        if (i == 3) {
            cancelSelect(true);
        } else {
            if (this.cancelSelect) {
                this.cancelSelect = false;
                doMove(0.0f, 0.0f);
                OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
                if (onSelectModelListener != null) {
                    onSelectModelListener.onObjectSelectedPath(-this.mOffsetPointF.x, -this.mOffsetPointF.y, this.mSelectOutPath, true);
                }
            }
            stopAction();
        }
        this.mTotalOffsetPointF.set(0.0f, 0.0f);
        OnSelectModelListener onSelectModelListener2 = this.mItemViewSelectModelListener;
        if (onSelectModelListener2 != null) {
            onSelectModelListener2.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            this.mItemViewSelectModelListener.onEndAction(0);
        }
    }

    private void doMoving(float f, float f2) {
        this.mTotalOffsetPointF.set(f - this.mDownPoint.x, f2 - this.mDownPoint.y);
        setMatrixTransform(4);
        if (this.mItemViewSelectModelListener != null) {
            this.mOffsetPointF.offset(f - this.mLastPoint.x, f2 - this.mLastPoint.y);
            this.mItemViewSelectModelListener.onObjectSelectedPath(f - this.mLastPoint.x, f2 - this.mLastPoint.y, this.mSelectOutPath, false);
            this.mItemViewSelectModelListener.onDoingAction(4, this.mSelectDataRectFDst);
        }
        this.mLastPoint.set(f, f2);
        doMove(this.mTotalOffsetPointF.x, this.mTotalOffsetPointF.y);
    }

    private void doMoveStart(float f, float f2) {
        startAction();
        this.mOffsetPointF.set(0.0f, 0.0f);
        this.mLastPoint.set(f, f2);
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, false);
            this.mItemViewSelectModelListener.onStartAction(4);
        }
    }

    private void doMixedEnd(int i) {
        if (i == 3) {
            cancelSelect(true);
        } else {
            OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
            if (onSelectModelListener != null) {
                onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            }
            stopAction();
        }
        this.mScaleValue = 1.0f;
        OnSelectModelListener onSelectModelListener2 = this.mItemViewSelectModelListener;
        if (onSelectModelListener2 != null) {
            onSelectModelListener2.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            this.mItemViewSelectModelListener.onEndAction(0);
        }
    }

    private void doMixedRotateEnd(int i) {
        if (i == 3) {
            cancelSelect(true);
        } else {
            if (this.mItemViewSelectModelListener != null) {
                Matrix matrix = new Matrix();
                matrix.postTranslate(-this.mCenterX, -this.mCenterY);
                matrix.postRotate(this.mCurRotateAngle);
                matrix.postTranslate(this.mCenterX, this.mCenterY);
                Path path = this.mSelectOutPath;
                if (path != null) {
                    path.transform(matrix);
                }
                this.mItemViewSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            }
            stopAction();
        }
        this.mScaleValue = 1.0f;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            this.mItemViewSelectModelListener.onEndAction(0);
        }
    }

    private void doMixedScaleEnd(int i) {
        if (i == 3) {
            cancelSelect(true);
        } else {
            if (this.mItemViewSelectModelListener != null) {
                Matrix matrix = new Matrix();
                matrix.postTranslate(-this.mCenterX, -this.mCenterY);
                float f = this.mScaleValue;
                matrix.postScale(f, f);
                matrix.postTranslate(this.mCenterX, this.mCenterY);
                Path path = this.mSelectOutPath;
                if (path != null) {
                    path.transform(matrix);
                }
                this.mItemViewSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            }
            stopAction();
        }
        this.mScaleValue = 1.0f;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, true);
            this.mItemViewSelectModelListener.onEndAction(0);
        }
    }

    private void doMixing(float f, float f2) {
        float fCountScaleValue = countScaleValue(this.mCenterX, this.mCenterY, f, f2, this.mDownPoint);
        if (this.isSelectLimit) {
            float f3 = this.mCurSelectLimit;
            if (fCountScaleValue != f3) {
                fCountScaleValue = f3;
            }
        }
        if (!checkSelectLimit(fCountScaleValue)) {
            this.mScaleValue = fCountScaleValue;
        }
        setMatrixTransform(28);
        float fCountAngle = countAngle(f - this.mCenterX, f2 - this.mCenterY);
        this.mCurRotateAngle = fCountAngle - this.mDownAngle;
        this.mPreRotateAngle = fCountAngle;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDoingAction(28, this.mSelectDataRectFDst);
        }
        doScale(this.mCenterX, this.mCenterY, this.mScaleValue);
        doRotate(this.mCenterX, this.mCenterY, this.mCurRotateAngle);
    }

    private void doMixingRotate(float f, float f2) {
        float fCountScaleValue = countScaleValue(this.mCenterX, this.mCenterY, f, f2, this.mDownPoint);
        if (this.isSelectLimit) {
            float f3 = this.mCurSelectLimit;
            if (fCountScaleValue != f3) {
                fCountScaleValue = f3;
            }
        }
        checkSelectLimit(fCountScaleValue);
        setMatrixTransform(28);
        float fCountAngle = countAngle(f - this.mCenterX, f2 - this.mCenterY);
        this.mCurRotateAngle = fCountAngle - this.mDownAngle;
        this.mPreRotateAngle = fCountAngle;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDoingAction(28, this.mSelectDataRectFDst);
        }
        doScale(this.mCenterX, this.mCenterY, this.mScaleValue);
        doRotate(this.mCenterX, this.mCenterY, this.mCurRotateAngle);
    }

    private void doMixingScale(float f, float f2) {
        float fCountScaleValue = countScaleValue(this.mCenterX, this.mCenterY, f, f2, this.mDownPoint);
        if (this.isSelectLimit) {
            float f3 = this.mCurSelectLimit;
            if (fCountScaleValue != f3) {
                fCountScaleValue = f3;
            }
        }
        if (!checkSelectLimit(fCountScaleValue)) {
            this.mScaleValue = fCountScaleValue;
        }
        setMatrixTransform(28);
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDoingAction(28, this.mSelectDataRectFDst);
        }
        doScale(this.mCenterX, this.mCenterY, this.mScaleValue);
        doRotate(this.mCenterX, this.mCenterY, this.mPreRotateAngle);
    }

    private void doMixedStart(float f, float f2) {
        startAction();
        float fCountAngle = countAngle(f - this.mCenterX, f2 - this.mCenterY);
        this.mDownAngle = fCountAngle;
        this.mPreRotateAngle = fCountAngle;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onStartAction(28);
        }
    }

    private void doMixedRotateStart(float f, float f2) {
        startAction();
        float fCountAngle = countAngle(f - this.mCenterX, f2 - this.mCenterY);
        this.mDownAngle = fCountAngle;
        this.mPreRotateAngle = fCountAngle;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, false);
            this.mItemViewSelectModelListener.onStartAction(28);
        }
    }

    private void doMixedScaleStart(float f, float f2) {
        startAction();
        this.mPreRotateAngle = 0.0f;
        this.mCurRotateAngle = 0.0f;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(0.0f, 0.0f, this.mSelectOutPath, false);
            this.mItemViewSelectModelListener.onStartAction(28);
        }
    }

    private void doSelected(MotionEvent motionEvent, boolean z) {
        MathDataManager mathDataManager = this.mPenEngine.getCurveEngine().getMathDataManager();
        this.mTouchPoints.add(new TouchPoint(motionEvent.getX(), motionEvent.getY(), motionEvent.getEventTime()));
        try {
            if (TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent)) {
                this.mTouchPoints.clear();
                TouchPoint touchPoint = new TouchPoint(motionEvent.getX(), motionEvent.getY(), motionEvent.getEventTime());
                this.mTouchPoints.add(touchPoint);
                int i = this.mObjectAction;
                if (i == 0) {
                    doPointSelect(touchPoint);
                    if (!mathDataManager.getHasUserStartedDrawing()) {
                        mathDataManager.setUserStartedDrawing(true);
                        XLog.d(TAG, "Enable auto-selection after manual select (point)", new Object[0]);
                    }
                } else if (i == 1) {
                    this.mObjectAction = 4;
                }
            } else {
                int i2 = this.mObjectAction;
                if (i2 == 0) {
                    doPathSelect(this.mTouchPoints, false, z);
                    if (!mathDataManager.getHasUserStartedDrawing()) {
                        mathDataManager.setUserStartedDrawing(true);
                        XLog.d(TAG, "Enable auto-selection after manual select (path)", new Object[0]);
                    }
                } else if (i2 == 1) {
                    this.mObjectAction = 5;
                }
            }
        } catch (Exception unused) {
        }
        setSelectAction(0);
    }

    private void doSelected(MotionEvent motionEvent) {
        doSelected(motionEvent, true);
    }

    private void doSelecting(float f, float f2, long j) {
        this.mTouchPoints.add(new TouchPoint(f, f2, j));
    }

    private void startSelect(float f, float f2, long j) {
        this.mSelectOutPath = null;
        this.mTouchPoints.clear();
        this.mTouchPoints.add(new TouchPoint(f, f2, j));
    }

    private void setMatrixTransform(int i) {
        this.mDrawMatrix.reset();
        if (i == 4) {
            this.mDrawMatrix.setTranslate(this.mTotalOffsetPointF.x, this.mTotalOffsetPointF.y);
        } else if (i == 8 || i == 28) {
            Matrix matrix = this.mDrawMatrix;
            float f = this.mScaleValue;
            matrix.setScale(f, f, this.mCenterX, this.mCenterY);
        }
        this.mDrawMatrix.mapRect(this.mSelectDataRectFDst, this.mSelectDataRectF);
    }

    private float countAngle(float f, float f2) {
        return (float) Math.toDegrees(Math.atan2(f2, f));
    }

    private float countScaleValue(float f, float f2, float f3, float f4, PointF pointF) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = pointF.x - f;
        float f8 = pointF.y - f2;
        return (float) (Math.sqrt((f5 * f5) + (f6 * f6)) / Math.sqrt((f7 * f7) + (f8 * f8)));
    }

    private boolean isSelectObjectNotEmpty() {
        return this.mSelectObject != null;
    }

    private void startAction() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.startAction(this.mCurSelectAction);
        }
    }

    private void doMove(float f, float f2) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.doMove(f, f2);
            this.mPenEngine.getCurveEngine().getSelectSystem().onMove(f, f2);
        }
    }

    private void doRotate(float f, float f2, float f3) {
        double d = f3;
        double dRound = Math.round(d / 45.0d) * 45.0d;
        if (Math.abs(d - dRound) <= 3.0d) {
            this.mAngle = (float) dRound;
        } else {
            this.mAngle = f3;
        }
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            float f4 = this.mAngle;
            this.mCurRotateAngle = f4;
            iSelectObject.doRotate(f, f2, f4);
            this.mPenEngine.getCurveEngine().getSelectSystem().onRotate(this.mAngle);
        }
    }

    private void doScale(float f, float f2, float f3) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.doScale(f, f2, f3);
            this.mPenEngine.getCurveEngine().getSelectSystem().onScale(f3, f, f2);
        }
    }

    private void stopAction() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.stopAction();
        }
    }

    private void cancelAction() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.cancelAction();
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void cancelSelectAction(float f, float f2) {
        this.cancelSelect = !this.mSelectDataRectFDst.isEmpty() && this.mSelectDataRectFDst.contains(f, f2);
    }

    private void finishSelect() {
        this.mHadPointSelected = false;
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.finishSelect();
        }
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.finishSelect();
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isSingleCurves() {
        List<DataCurve> curveList;
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof ISelectCurveObject) || (curveList = ((ISelectCurveObject) iSelectObject).getCurveList()) == null) {
            return false;
        }
        Iterator<DataCurve> it = curveList.iterator();
        while (it.hasNext()) {
            if (it.next().getType() == 28) {
                return false;
            }
        }
        return true;
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onObjectSelect(ISelectObject iSelectObject) {
        onObjectSelect(iSelectObject, false);
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onObjectSelect(ISelectObject iSelectObject, boolean z) {
        if (this.mCurSelectAction == 0 || this.mCurSelectAction == 4) {
            if (iSelectObject == null) {
                this.mSelectObject = null;
                reset();
                OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
                if (onSelectModelListener != null) {
                    onSelectModelListener.onObjectSelected(new SRectF(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), this.deleteRecognizeCurveList, z);
                    return;
                }
                return;
            }
            if (this.mObjectAction == 0) {
                this.mObjectAction = 1;
            }
            SRectF selectRect = getSelectRect(iSelectObject);
            setSelectDataRectF(selectRect);
            this.mSelectObject = iSelectObject;
            this.mPenEngine.getCurveEngine().getSelectSystem().selectOn(iSelectObject.getSelectData(), selectRect);
            if (this.mItemViewSelectModelListener != null) {
                this.mSelectDataRectFDst.setSelectHasCode(iSelectObject.getSelectedHashCode());
                this.mItemViewSelectModelListener.onObjectSelected(this.mSelectDataRectFDst, this.deleteRecognizeCurveList, z);
            }
            int i = this.mObjectAction;
            if (i == 2) {
                startPaste();
            } else if (i == 3) {
                startCancelSelect(this.cancelIsUndo);
            } else if (i == 4) {
                doPointSelect(this.mTouchPoints.get(0));
            } else if (i == 5) {
                doPathSelect(this.mTouchPoints);
            }
            this.mObjectAction = 0;
        }
    }

    private void setSelectDataRectF(SRectF sRectF) {
        if (sRectF == null) {
            return;
        }
        this.mSelectDataRectF.set(sRectF);
        this.mSelectDataRectF.setAngle(sRectF.mAngle);
        this.mSelectDataRectFDst.set(sRectF);
        this.mSelectDataRectFDst.setAngle(sRectF.mAngle);
        this.mCenterX = sRectF.centerX();
        this.mCenterY = sRectF.centerY();
        this.mDrawMatrix.reset();
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onOutLineComplete(Path path, short s) {
        this.mSelectOutPath = path;
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPathForOutLine(0.0f, 0.0f, path, true);
        }
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onDataCopied(byte[] bArr, int i) {
        this.mPenEngine.getCurveEngine().getMathDataManager().setBehavior(3);
        this.mCurPasteInfo = bArr;
        this.mCopyPointCount = i;
        if (this.mCurSelectAction == 65) {
            doDelete(false);
        }
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDataCopied(this.mCurSelectAction, this.mCurPasteInfo, i);
        }
        setSelectAction(0);
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onDataCopiedAndPaste(byte[] bArr, int i) {
        this.mCurPasteInfo = bArr;
        this.mCopyPointCount = i;
        int i2 = this.mObjectAction;
        if (i2 == 0) {
            startPaste();
        } else if (i2 == 1) {
            this.mObjectAction = 2;
        }
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onDataCopied(this.mCurSelectAction, this.mCurPasteInfo, i);
        }
        setSelectAction(0);
    }

    private void startPaste() {
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener == null || !onSelectModelListener.isPagePointLimit(this.mCopyPointCount)) {
            PenEngine penEngine = this.mPenEngine;
            if (penEngine != null && penEngine.getEditOperator().isPointLimit(this.mCopyPointCount, this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(this.mSelectDataRectFDst.left, this.mSelectDataRectF.top)))) {
                OnSelectModelListener onSelectModelListener2 = this.mItemViewSelectModelListener;
                if (onSelectModelListener2 != null) {
                    onSelectModelListener2.isPointLimit();
                    return;
                }
                return;
            }
            OnSelectModelListener onSelectModelListener3 = this.mItemViewSelectModelListener;
            if (onSelectModelListener3 != null) {
                onSelectModelListener3.onStartPasteFromCopy();
            }
            SRectF selectRect = getSelectRect(this.mSelectObject);
            if (selectRect == null) {
                return;
            }
            float fCenterX = selectRect.centerX() + 50.0f;
            float fCenterY = selectRect.centerY() + 50.0f;
            finishSelect();
            doPaste(this.mCurPasteInfo, fCenterX, fCenterY);
        }
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onStepChanged(int i, int i2) {
        onStepChanged(i, i2, true);
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onStepChanged(int i, int i2, boolean z) {
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onStepChanged(i, i2, z);
        }
        onSelectStepChangedListener onselectstepchangedlistener = this.mOnSelectStepChangedListener;
        if (onselectstepchangedlistener != null) {
            onselectstepchangedlistener.onStepChanged(i, i2, z);
        }
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
        String str = TAG;
        Log.d(str, "onPageSizeChanged mItemViewSelectModelListener " + this.mItemViewSelectModelListener);
        if (this.mItemViewSelectModelListener != null) {
            Log.d(str, "onPageSizeChanged pageSizeConfig " + pageSizeConfig);
            this.mItemViewSelectModelListener.onPageSizeChanged(pageSizeConfig);
            return;
        }
        Log.d(str, "onPageSizeChanged mOnSelectStepChangedListener " + this.mOnSelectStepChangedListener);
        onSelectStepChangedListener onselectstepchangedlistener = this.mOnSelectStepChangedListener;
        if (onselectstepchangedlistener != null) {
            onselectstepchangedlistener.onPageSizeChanged(pageSizeConfig);
        }
    }

    @Override // xc.engine.operate.edit.IEditListener
    public void onSelectLimit(int i, float f) {
        OnSelectModelListener onSelectModelListener = this.mItemViewSelectModelListener;
        if (onSelectModelListener != null) {
            onSelectModelListener.onSelectLimit(i, f);
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public float[] getEditPoint() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject)) {
            return null;
        }
        return ((IEditSelectShapeObject) iSelectObject).getEditPoint();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isCloseShape() {
        ShapeProp shapeProp;
        int shapeType;
        ISelectObject iSelectObject = this.mSelectObject;
        return (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject) || (shapeProp = ((IEditSelectShapeObject) iSelectObject).getShapeProp()) == null || (shapeType = shapeProp.getShapeType()) == 1 || shapeType == 2) ? false : true;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setShapeEditType(int i, PointF pointF, PointF pointF2) {
        this.mShapeEditType = i;
        this.mStartShapeEditPoint = pointF;
        this.mCenterShapeEditPoint = pointF2;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isSelectedObjectValid() {
        ISelectObject iSelectObject = this.mSelectObject;
        return iSelectObject != null && (iSelectObject instanceof ISelectCurveObject);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isSelectedContour() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof ISelectCurveObject)) {
            return false;
        }
        return ((ISelectCurveObject) iSelectObject).doSelectedContour();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public boolean isShowEditPoint() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject)) {
            return false;
        }
        return ((IEditSelectShapeObject) iSelectObject).showEditPoint();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public ShapeProp getSelectShapeProp() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject)) {
            return null;
        }
        return ((IEditSelectShapeObject) iSelectObject).getShapeProp();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject)) {
            return;
        }
        ((IEditSelectShapeObject) iSelectObject).doShapeAttrEdit(shapeEditAttr, z);
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setEnableSelect(boolean z) {
        this.isEnableSelect = z;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public RectF getSelectRect() {
        return this.mSelectDataRectFDst;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void setEnablePointSelect(boolean z) {
        this.isEnablePointSelect = z;
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public Path getSelectPath() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject == null || !(iSelectObject instanceof IEditSelectShapeObject)) {
            return null;
        }
        return ((IEditSelectShapeObject) iSelectObject).getSelectPath();
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void onDraw(Canvas canvas) {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.onDraw(canvas);
        }
    }

    @Override // xc.engine.operate.edit.select.ISelectOperator
    public void forceRedraw() {
        ISelectObject iSelectObject = this.mSelectObject;
        if (iSelectObject != null) {
            iSelectObject.forceRedraw();
        }
    }
}
