package xc.engine.operate.edit;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.AsyncTask;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.PageSystem;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.math.iink.ui.RecognitionModel;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.CanvasSizeInfo;
import xc.engine.operate.canvas.draw.DrawerWrapper;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;
import xc.engine.operate.edit.select.SelectCopyData;
import xc.engine.operate.edit.select.SelectTouchPoints;
import xc.engine.operate.edit.select.limit.SelectLimit;
import xc.engine.operate.edit.select.selectobject.BaseSelectObject;
import xc.engine.operate.edit.select.selectobject.MultiSelectCurveObject;
import xc.engine.operate.edit.select.selectobject.ShapeSelectObject;
import xc.engine.operate.step.type.StepEditClear;
import xc.engine.operate.step.type.StepEditDelete;
import xc.engine.operate.step.type.StepEditWrite;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScaleDataUtil;
import xc.engine.utils.SkRegionHelper;

/* JADX INFO: loaded from: classes6.dex */
public class EditOperator implements IEditOperator {
    public SelectCopyData mCopyData;
    public IEditListener mEditListener;
    public final PenEngine mPenEngine;
    public SelectLimit mSelectLimit;
    public BaseSelectObject mSelectObject;
    private final String TAG = "EditOperator";
    public AlgorithmAntLineAsyncTask mAsyncTask = null;
    public boolean i = false;
    public int j = 0;
    public int k = 0;
    public int l = 0;
    public int m = 0;
    public int mPointMaxLimit = 200000;
    public PathMeasure mPathMeasure = new PathMeasure();
    public final float mDefaultPathDistance = 20.0f;
    public List<TouchPoint> mTouchPoints = new ArrayList();

    private static class AlgorithmAntLineAsyncTask extends AsyncTask<Void, Void, Path> {
        public final int d;
        public List<DataCurve> mDataList;
        public WeakReference<EditOperator> mOperatorWeakReference;
        public ArrayList<xc.engine.core.algorithm.TouchPoint> mPoints = new ArrayList<>();

        public AlgorithmAntLineAsyncTask(EditOperator editOperator, List<DataCurve> list, int i) {
            this.mOperatorWeakReference = new WeakReference<>(editOperator);
            this.mDataList = list;
            this.d = i;
        }

        @Override // android.os.AsyncTask
        public void onCancelled() {
            super.onCancelled();
            this.mPoints.clear();
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Path path) {
            this.mPoints.clear();
            if (this.mOperatorWeakReference.get() == null || path == null || this.d != this.mOperatorWeakReference.get().m) {
                return;
            }
            EditOperator editOperator = this.mOperatorWeakReference.get();
            AlgorithmAntLineAsyncTask algorithmAntLineAsyncTask = editOperator.mAsyncTask;
            if (editOperator.mEditListener == null || algorithmAntLineAsyncTask == null || algorithmAntLineAsyncTask.isCancelled()) {
                return;
            }
            editOperator.mTouchPoints.clear();
            editOperator.mPathMeasure.setPath(path, true);
            int length = (int) (editOperator.mPathMeasure.getLength() / editOperator.mDefaultPathDistance);
            for (int i = 0; i < length; i++) {
                float[] fArr = new float[2];
                editOperator.mPathMeasure.getPosTan(i * editOperator.mDefaultPathDistance, fArr, null);
                editOperator.mTouchPoints.add(new TouchPoint(fArr[0], fArr[1], System.currentTimeMillis()));
            }
            SelectTouchPoints extraSelectPath = editOperator.getExtraSelectPath(editOperator.mTouchPoints);
            if (editOperator.mSelectObject != null) {
                editOperator.mSelectObject.selectTouchComplete(extraSelectPath);
                editOperator.onCanvasDataChanged();
            }
        }

        private void getCurvePoints(DataCurve dataCurve) {
            float drawRatio = this.mOperatorWeakReference.get().mPenEngine.mCanvasOperator.getDrawRatio();
            ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
            int size = curvePointList.size() - 1;
            for (int i = 0; i < size; i++) {
                if (i % 4 == 0) {
                    this.mPoints.add(new xc.engine.core.algorithm.TouchPoint(curvePointList.getX(i) * drawRatio, curvePointList.getY(i) * drawRatio));
                }
            }
            this.mPoints.add(new xc.engine.core.algorithm.TouchPoint(curvePointList.getX(size) * drawRatio, curvePointList.getY(size) * drawRatio));
            if (size == 0) {
                this.mPoints.add(new xc.engine.core.algorithm.TouchPoint((curvePointList.getX(size) + 1.0f) * drawRatio, (curvePointList.getY(size) + 1.0f) * drawRatio));
            }
        }

        @Override // android.os.AsyncTask
        public Path doInBackground(Void[] voidArr) {
            try {
                this.mPoints.clear();
                Iterator<DataCurve> it = this.mDataList.iterator();
                float fMax = 0.0f;
                while (true) {
                    if (it.hasNext()) {
                        DataCurve next = it.next();
                        if (isCancelled()) {
                            return null;
                        }
                        fMax = Math.max(fMax, next.getOutlinePenPoint().x * 2.0f);
                        getCurvePoints(next);
                    } else if (this.mOperatorWeakReference.get() != null) {
                        return this.mOperatorWeakReference.get().mPenEngine.mAlgorithm.getOutlineHelper().extractContourLine(this.mPoints, Math.max(fMax, 2.0f));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public EditOperator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleClear, reason: merged with bridge method [inline-methods] */
    public void m4632lambda$clearI$8$xcengineoperateeditEditOperator(int i) {
        Iterator<BaseData> it = this.mPenEngine.mDataAccess.getDataSet().mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EditOperator.lambda$handleClear$0((Integer) obj);
            }
        }).iterator();
        while (it.hasNext()) {
            if (it.next().mDataState != 3) {
                StepEditClear stepEditClear = new StepEditClear(this.mPenEngine, i);
                for (BaseData baseData : this.mPenEngine.mDataAccess.getDataSet().mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda8
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return EditOperator.lambda$handleClear$1((Integer) obj);
                    }
                })) {
                    if (baseData.mDataState == 1) {
                        DataCurve dataCurve = (DataCurve) baseData;
                        if (!dataCurve.isMathResult) {
                            stepEditClear.mDataList.add(dataCurve);
                        }
                        stepEditClear.engine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, i);
                    }
                }
                List<DataCurve> list = stepEditClear.mDataList;
                if (list.isEmpty()) {
                    return;
                }
                this.mPenEngine.mStepManager.addStep(stepEditClear);
                RectF saveRect = list.get(0).getSaveRect();
                list.get(0).translateRectByPenPoint(saveRect, false);
                for (int i2 = 1; i2 < list.size(); i2++) {
                    RectF saveRect2 = list.get(i2).getSaveRect();
                    list.get(i2).translateRectByPenPoint(saveRect2, false);
                    saveRect.union(saveRect2);
                }
                stepEditClear.saveRect.set(saveRect);
                Iterator<DataCurve> it2 = list.iterator();
                while (it2.hasNext()) {
                    it2.next().mDataState = 3;
                }
                this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(list);
                this.mPenEngine.getCurveEngine().getMathSystem().setHasJustCleared(true);
                try {
                    if (this.mPenEngine.getCurveEngine().getMathSystem() != null) {
                        this.mPenEngine.getCurveEngine().getMathSystem().clearAllResults(Integer.valueOf(i));
                    }
                } catch (Exception e) {
                    Log.e("EditOperator", "handleClear: failed to clear math results", e);
                }
                try {
                    if (this.mPenEngine.getCurveEngine().getMathSystem() != null && this.mPenEngine.getCurveEngine().getPageMode() == MiCurveEngine.EPageMode.Infinite) {
                        Log.w("yjg", "handleClear: clear iink Content");
                        RecognitionModel recognitionModel = this.mPenEngine.getCurveEngine().getMathSystem().getRecognitionModel();
                        if (recognitionModel != null) {
                            recognitionModel.clearIinkContentIfNeeded();
                        }
                    }
                } catch (Exception e2) {
                    Log.e("EditOperator", "handleClear: failed to clear iink content", e2);
                }
                float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
                this.mPenEngine.mDrawerWrapper.refreshCurveData(new RectF(saveRect.left * drawRatio, saveRect.top * drawRatio, saveRect.right * drawRatio, (saveRect.bottom * drawRatio) + 10.0f), true);
                return;
            }
        }
    }

    static /* synthetic */ List lambda$handleClear$0(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$handleClear$1(Integer num) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDeletePage, reason: merged with bridge method [inline-methods] */
    public void m4633lambda$deletePage$9$xcengineoperateeditEditOperator(int i) {
        StepEditDelete stepEditDelete = new StepEditDelete(this.mPenEngine, i);
        List<DataCurve> list = (List) this.mPenEngine.mDataAccess.mDataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EditOperator.lambda$handleDeletePage$2((Integer) obj);
            }
        }).stream().map(new Function() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EditOperator.lambda$handleDeletePage$3((BaseData) obj);
            }
        }).collect(Collectors.toList());
        stepEditDelete.setDataCurveList(list);
        Log.i(this.TAG, "[handleDeletePage] page: " + i + ", add deletePageStep");
        this.mPenEngine.mStepManager.addStep(stepEditDelete);
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(list);
        list.clear();
    }

    static /* synthetic */ List lambda$handleDeletePage$2(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ DataCurve lambda$handleDeletePage$3(BaseData baseData) {
        return (DataCurve) baseData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCanvasDataChanged() {
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
    }

    private final int c() {
        int i = this.m + 1;
        this.m = i;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCanvasDataChanged() {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleOnCanvasDataChanged();
            }
        });
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void setEditListener(IEditListener iEditListener) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mEditListener = iEditListener;
    }

    public void a(SelectCopyData selectCopyData, boolean z) {
        SelectCopyData selectCopyData2;
        if (z && (selectCopyData2 = this.mCopyData) != null) {
            selectCopyData2.g();
        }
        this.mCopyData = selectCopyData;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void doPathSelect(final List<TouchPoint> list, final boolean z, final boolean z2) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final int iC = c();
        this.mPenEngine.mPenEngineHandlerWrapper.mHandler.removeMessages(21);
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessageDelayed(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4638lambda$doPathSelect$4$xcengineoperateeditEditOperator(list, iC, z, z2);
            }
        }, 21, 50L);
    }

    public void doDirectSelect(final List<TouchPoint> list, final List<DataCurve> list2, final boolean z, final boolean z2) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final int iC = c();
        this.mPenEngine.mPenEngineHandlerWrapper.mHandler.removeMessages(21);
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessageDelayed(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4635lambda$doDirectSelect$5$xcengineoperateeditEditOperator(list, list2, iC, z, z2);
            }
        }, 21, 50L);
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void doAutoPathSelect(final DataCurve dataCurve) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final int iC = c();
        this.mPenEngine.mPenEngineHandlerWrapper.mHandler.removeMessages(21);
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessageDelayed(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4634lambda$doAutoPathSelect$6$xcengineoperateeditEditOperator(dataCurve, iC);
            }
        }, 21, 50L);
    }

    /* JADX INFO: renamed from: lambda$doAutoPathSelect$6$xc-engine-operate-edit-EditOperator, reason: not valid java name */
    /* synthetic */ void m4634lambda$doAutoPathSelect$6$xcengineoperateeditEditOperator(DataCurve dataCurve, int i) {
        a(dataCurve, i);
        this.mPenEngine.getCurveEngine().getSelectSystem().selected();
    }

    public void cancelOutlineGenerator() {
        AlgorithmAntLineAsyncTask algorithmAntLineAsyncTask = this.mAsyncTask;
        if (algorithmAntLineAsyncTask == null || algorithmAntLineAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            return;
        }
        if (this.mPenEngine.mAlgorithm.getOutlineHelper() != null) {
            this.mPenEngine.mAlgorithm.getOutlineHelper().cancel();
        }
        this.mAsyncTask.cancel(true);
        this.mAsyncTask = null;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void doPointSelect(final TouchPoint touchPoint) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final int iC = c();
        this.mPenEngine.mPenEngineHandlerWrapper.mHandler.removeMessages(21);
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessageDelayed(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4639lambda$doPointSelect$7$xcengineoperateeditEditOperator(touchPoint, iC);
            }
        }, 21, 50L);
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void clearI() {
        clearI(0);
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void clearI(final int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4632lambda$clearI$8$xcengineoperateeditEditOperator(i);
            }
        });
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void deletePage(final int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4633lambda$deletePage$9$xcengineoperateeditEditOperator(i);
            }
        });
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public boolean isChanged() {
        return this.mPenEngine.mStepManager.getUndoSize() != 0;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void setSelectScaleLimit(SelectLimit selectLimit) {
        this.mSelectLimit = selectLimit;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void enableSelectedContour(boolean z) {
        this.i = z;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void clearRunnable() {
        DrawerWrapper drawerWrapper = this.mPenEngine.mDrawerWrapper;
        if (drawerWrapper.isInitialized) {
            drawerWrapper.mDrawer.clearRunnable();
        }
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public boolean isPointLimit(int i, int i2) {
        return !this.mPenEngine.mDataAccess.getDataSet().isPointLimit(i, CreationPageSDK.getMaxPoints(), i2);
    }

    public SelectLimit getSelectLimit() {
        return this.mSelectLimit;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public boolean isClickOnShape(float f, float f2) {
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        if (this.mPenEngine.mCanvasOperator.isScale) {
            PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(f, f2, scaleInfo);
            float f3 = pointFScaleInverseOperation.x;
            f2 = pointFScaleInverseOperation.y;
            f = f3;
        }
        float scaleVisibleTop = this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f;
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        RectF rectF = new RectF();
        float f4 = f / drawRatio;
        float f5 = f2 / drawRatio;
        rectF.set(f4 - 10.0f, f5 - 10.0f, f4 + 10.0f, f5 + 10.0f);
        for (DataCurve dataCurve : ((CurveDataManager) this.mPenEngine.mCurveDataManager).scanIntersectDataCurve(rectF, false)) {
            if (dataCurve.getDataState() == 0 || dataCurve.getDataState() == 1) {
                if ((dataCurve instanceof DataShape) && ((DataShape) dataCurve).containsPoint(f, f2, 0.0f, scaleVisibleTop)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void doInsertShape(final int i, final float f, final float f2, final float f3, final float f4, final ShapeProp shapeProp) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4636lambda$doInsertShape$10$xcengineoperateeditEditOperator(i, f4, shapeProp, f3, f, f2);
            }
        });
    }

    private void doPaste(RectF rectF, SelectCopyData selectCopyData, int i) {
        boolean z;
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio() / selectCopyData.mDrawRatio;
        float f = rectF.left - (selectCopyData.getRatioRectF().left * drawRatio);
        float f2 = rectF.top - (selectCopyData.getRatioRectF().top * drawRatio);
        ArrayList<DataCurve> arrayList = new ArrayList();
        int size = 0;
        for (BaseData baseData : selectCopyData.getDataList()) {
            if (baseData.getType() == 1 || baseData.getType() == 28) {
                DataCurve dataCurve = (DataCurve) baseData;
                dataCurve.mTime = System.currentTimeMillis();
                dataCurve.calculateBoundingRect();
                arrayList.add(dataCurve);
                size += dataCurve.size(dataCurve.mSavePoints);
                if (!this.mPenEngine.mDataAccess.getDataSet().isPointLimit(size, CreationPageSDK.getMaxPoints(), this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(rectF.left, rectF.top)))) {
                    this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.PAGE_POINT_LIMIT));
                    return;
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (this.mPenEngine.mTouchEventOperator.isScale()) {
            float scaleRate = this.mPenEngine.mCanvasOperator.getScaleInfo().getScaleRate();
            for (DataCurve dataCurve2 : arrayList) {
                if (dataCurve2.getType() != 1 || dataCurve2.mScaleRate != scaleRate) {
                    dataCurve2.mScaleRate = scaleRate;
                    dataCurve2.mBasePoint.set(0.0f, 0.0f);
                    this.mPenEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve2);
                }
            }
        }
        boolean z2 = true;
        for (DataCurve dataCurve3 : arrayList) {
            dataCurve3.doMove(f, f2);
            dataCurve3.mId = this.mPenEngine.mDataAccess.getDataSet().mId;
            dataCurve3.pageIndex = this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(dataCurve3.getSaveRect().left * dataCurve3.mDrawRatio, dataCurve3.getSaveRect().top * dataCurve3.mDrawRatio));
            dataCurve3.renderOffsetX = this.mPenEngine.getCurveEngine().getPageSystem().getPageData(dataCurve3.pageIndex).getOriginXInWS();
            dataCurve3.renderOffsetY = this.mPenEngine.getCurveEngine().getPageSystem().getPageData(dataCurve3.pageIndex).getOriginYInWS();
            this.mPenEngine.mDataAccess.getDataSet().insertDataCurvePro(dataCurve3);
            this.mPenEngine.mDataAccess.getDataSet().addId(1);
            if (dataCurve3.getType() == 28) {
                z2 = false;
            }
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(arrayList);
        if (size > this.mPointMaxLimit) {
            z2 = false;
        }
        if (arrayList.size() == 1 && ((DataCurve) arrayList.get(0)).getType() == 28) {
            this.mSelectObject = new ShapeSelectObject((DataShape) arrayList.get(0), this.mPenEngine);
            z = false;
        } else {
            this.mSelectObject = new MultiSelectCurveObject(arrayList, this.mPenEngine, z2);
            z = z2;
        }
        SelectTouchPoints selectPoints = selectCopyData.getSelectPoints();
        selectPoints.doScale(0.0f, 0.0f, drawRatio, drawRatio);
        selectPoints.doMove(f, f2);
        this.mSelectObject.setSelectTouchPoint(selectPoints);
        executeSelectObject(arrayList, i, z);
        this.mPenEngine.mStepManager.addStep(new StepEditWrite(this.mPenEngine, arrayList, 1));
    }

    @Override // xc.engine.operate.edit.IEditOperator
    public void doPaste(final byte[] bArr, final PointF pointF, boolean z) {
        if (bArr != null) {
            final int iC = c();
            this.mPenEngine.mPenEngineHandlerWrapper.mHandler.removeMessages(21);
            this.mPenEngine.mPenEngineHandlerWrapper.sendMessageDelayed(new Runnable() { // from class: xc.engine.operate.edit.EditOperator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4637lambda$doPaste$11$xcengineoperateeditEditOperator(pointF, bArr, iC);
                }
            }, 21, 50L);
        }
    }

    private void executeSelectObject(List<DataCurve> list, int i, boolean z) {
        executeSelectObject(list, i, z, false);
    }

    private void executeSelectObject(List<DataCurve> list, int i, boolean z, boolean z2) {
        cancelOutlineGenerator();
        if (i == this.m) {
            this.mEditListener.onObjectSelect(this.mSelectObject, z2);
            if (!this.i || this.mSelectObject == null || list.size() <= 0 || !z) {
                return;
            }
            AlgorithmAntLineAsyncTask algorithmAntLineAsyncTask = new AlgorithmAntLineAsyncTask(this, list, i);
            this.mAsyncTask = algorithmAntLineAsyncTask;
            algorithmAntLineAsyncTask.execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SelectTouchPoints getExtraSelectPath(List<TouchPoint> list) {
        SelectTouchPoints selectTouchPoints = new SelectTouchPoints();
        if (list.size() != 0) {
            TouchPoint touchPoint = list.get(0);
            float scaleVisibleTop = this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f;
            selectTouchPoints.startSelectPath(touchPoint.getX(), touchPoint.getY() - scaleVisibleTop);
            ArrayList arrayList = new ArrayList();
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getTime() == 0) {
                    arrayList.add(list.get(i));
                    selectTouchPoints.mTouchPoints.add(new TouchPoint(list.get(i).getX(), list.get(i).getY() - scaleVisibleTop, System.currentTimeMillis()));
                } else {
                    selectTouchPoints.selectPathLineTo(list.get(i).getX(), list.get(i).getY() - scaleVisibleTop);
                }
            }
            selectTouchPoints.mPath.close();
            if (!arrayList.isEmpty()) {
                Path path = selectTouchPoints.mPath;
                Path path2 = new Path();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    TouchPoint touchPoint2 = (TouchPoint) arrayList.get(i2);
                    int i3 = i2 % 5;
                    if (i3 == 0) {
                        path2.reset();
                        path2.moveTo(touchPoint2.getX(), touchPoint2.getY());
                    } else {
                        path2.lineTo(touchPoint2.getX(), touchPoint2.getY());
                    }
                    if (i3 == 4) {
                        path2.close();
                    }
                }
                selectTouchPoints.mPath = path;
                return selectTouchPoints;
            }
        }
        return selectTouchPoints;
    }

    private void a(DataCurve dataCurve, int i) {
        SelectTouchPoints selectTouchPoints = new SelectTouchPoints();
        if (dataCurve != null && dataCurve.size(dataCurve.mOriginPoints) != 0) {
            ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mOriginPoints);
            selectTouchPoints.startSelectPath(curvePointList.getX(0), curvePointList.getY(0));
            int i2 = dataCurve.size(dataCurve.mOriginPoints) > 50 ? 10 : 1;
            for (int i3 = 1; i3 < dataCurve.size(dataCurve.mOriginPoints); i3 += i2) {
                selectTouchPoints.selectPathLineTo(curvePointList.getX(i3), curvePointList.getY(i3));
            }
            selectTouchPoints.mPath.close();
        }
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        CanvasSizeInfo canvasSizeInfo = this.mPenEngine.mCanvasOperator.mCanvasSizeInfo;
        if (dataCurve == null || !ScaleDataUtil.validCanvasRect(ScaleDataUtil.getScaleAndTranslateRect(dataCurve.getDrawRect(), scaleInfo), scaleInfo, canvasSizeInfo, true)) {
            this.mEditListener.onObjectSelect(null);
            return;
        }
        boolean z = dataCurve.getType() != 28;
        ArrayList arrayList = new ArrayList();
        arrayList.add(dataCurve);
        BaseSelectObject baseSelectObject = this.mSelectObject;
        if (baseSelectObject != null) {
            baseSelectObject.finishSelect(true);
            this.mSelectObject = null;
        }
        int size = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            size += ((DataCurve) arrayList.get(i4)).size(((DataCurve) arrayList.get(i4)).mSavePoints);
        }
        if (size > this.mPointMaxLimit) {
            z = false;
        }
        if (arrayList.size() == 1 && ((DataCurve) arrayList.get(0)).getType() == 28) {
            this.mSelectObject = new ShapeSelectObject((DataShape) arrayList.get(0), this.mPenEngine);
        } else {
            this.mSelectObject = new MultiSelectCurveObject(arrayList, this.mPenEngine, z);
        }
        selectTouchPoints.doMove(0.0f, 0.0f);
        this.mSelectObject.setSelectTouchPoint(selectTouchPoints);
        executeSelectObject(arrayList, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoInsertShape, reason: merged with bridge method [inline-methods] */
    public void m4636lambda$doInsertShape$10$xcengineoperateeditEditOperator(int i, float f, ShapeProp shapeProp, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7 = f;
        PenEngine penEngine = this.mPenEngine;
        DataShape dataShape = new DataShape(shapeProp, 0L);
        dataShape.mTime = System.currentTimeMillis();
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        dataShape.mDrawRatio = drawRatio;
        ScaleInfo scaleInfo = penEngine.mCanvasOperator.getScaleInfo();
        dataShape.mScaleRate = scaleInfo.getScaleRate();
        if (this.mPenEngine.mCanvasOperator.isScale) {
            PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(f3, f4, scaleInfo);
            f5 = pointFScaleInverseOperation.x;
            f6 = pointFScaleInverseOperation.y;
        } else {
            f5 = f3;
            f6 = f4;
        }
        if (shapeProp.getShapeType() == 2) {
            float f8 = f2 / 2.22f;
            dataShape.mAnchorPoint4[0] = (f5 - f8) / drawRatio;
            dataShape.mAnchorPoint4[2] = (f5 + f8) / drawRatio;
            float f9 = f6 / drawRatio;
            dataShape.mAnchorPoint4[1] = f9;
            dataShape.mAnchorPoint4[3] = f9;
            dataShape.mRotateDegrees = -45.0f;
        } else {
            float f10 = shapeProp.getShapeType() == 4 ? 1.125f * f7 : f2;
            if (shapeProp.getShapeType() == 6) {
                f7 = (float) (((double) f7) * 0.9175d);
                f10 = (float) (((double) f10) * 0.9175d);
            }
            if (shapeProp.getShapeType() == 7) {
                f7 = (float) (((double) f7) * 0.75d);
                f10 = (float) (((double) f10) * 0.925d);
            }
            float f11 = f10 / 2.0f;
            dataShape.mAnchorPoint4[0] = (f5 - f11) / drawRatio;
            dataShape.mAnchorPoint4[2] = (f5 + f11) / drawRatio;
            float f12 = f7 / 2.0f;
            dataShape.mAnchorPoint4[1] = (f6 - f12) / drawRatio;
            dataShape.mAnchorPoint4[3] = (f6 + f12) / drawRatio;
        }
        ShapeCurveInfo shapeCurveInfo = new ShapeCurveInfo(penEngine, dataShape, penEngine.mCanvasOperator.isScale, true);
        shapeCurveInfo.a(dataShape.mAnchorPoint4);
        shapeCurveInfo.optimizeAndTransformShapePath();
        if (penEngine.mCanvasOperator.isScale) {
            penEngine.mCurveDataManager.executeCurveGenTask(dataShape);
        }
        PenEngineUtils.calculateShapeSavePoints(dataShape);
        if (20000000 < ((long) dataShape.size(dataShape.mSavePoints)) + this.mPenEngine.mDataAccess.getDataSet().mExtraTotalPoint) {
            this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.ALL_POINT_LIMIT));
            return;
        }
        if (!this.mPenEngine.mDataAccess.getDataSet().isPointLimit(dataShape.size(dataShape.mSavePoints), CreationPageSDK.getMaxPoints(), i)) {
            this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.PAGE_POINT_LIMIT));
            return;
        }
        DrawerWrapper drawerWrapper = this.mPenEngine.mDrawerWrapper;
        if (drawerWrapper.isInitialized) {
            drawerWrapper.mDrawer.drawShapeCurve(dataShape);
        }
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
        DataSet dataSet = this.mPenEngine.mDataAccess.getDataSet();
        dataShape.mId = dataSet.mId;
        dataShape.pageIndex = i;
        PageSystem.PageData pageData = this.mPenEngine.getCurveEngine().getPageSystem().getPageData(i);
        dataShape.renderOffsetX = pageData.getOriginXInWS();
        dataShape.renderOffsetY = pageData.getOriginYInWS();
        dataSet.insertDataCurve(dataShape);
        dataSet.addId(1);
        this.mPenEngine.mCurveDataManager.addDataToPool(dataShape);
        this.mPenEngine.mStepManager.addStep(new StepEditWrite(this.mPenEngine, dataShape, 1));
        doAutoPathSelect(dataShape);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:11:0x003b  */
    /* JADX INFO: renamed from: handleDoPaste, reason: merged with bridge method [inline-methods] */
    public void m4637lambda$doPaste$11$xcengineoperateeditEditOperator(PointF pointF, byte[] bArr, int i) {
        SelectCopyData dataFromBytes;
        try {
            BaseSelectObject baseSelectObject = this.mSelectObject;
            if (baseSelectObject != null) {
                baseSelectObject.finishSelect(true);
                this.mSelectObject = null;
            }
            if (this.mCopyData != null) {
                byte[] bArr2 = new byte[8];
                new ByteArrayInputStream(bArr).read(bArr2);
                if (new DataInputStream(new ByteArrayInputStream(bArr2)).readLong() == this.mCopyData.mCopyTime) {
                    dataFromBytes = new SelectCopyData(this.mCopyData, this.mPenEngine);
                } else {
                    dataFromBytes = null;
                }
            } else {
                dataFromBytes = null;
            }
            if (dataFromBytes == null) {
                dataFromBytes = SelectCopyData.readDataFromBytes(bArr, this.mPenEngine);
            }
            float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio() / dataFromBytes.mDrawRatio;
            float fWidth = (dataFromBytes.getRatioRectF().width() * drawRatio) / 2.0f;
            float fHeight = (dataFromBytes.getRatioRectF().height() * drawRatio) / 2.0f;
            CanvasOperator canvasOperator = this.mPenEngine.mCanvasOperator;
            if (canvasOperator.isScale) {
                PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(pointF.x, pointF.y, canvasOperator.getScaleInfo());
                pointF.set(pointFScaleInverseOperation.x, pointFScaleInverseOperation.y);
            }
            float f = pointF.x;
            float f2 = pointF.y;
            doPaste(new RectF(f - fWidth, f2 - fHeight, f + fWidth, f2 + fHeight), dataFromBytes, i);
            Log.e(this.TAG, "xl-- handleDoPaste selected");
        } catch (Throwable th) {
            this.mEditListener.onObjectSelect(null);
            this.mPenEngine.mErrorListener.onError(new Exception(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoDirectSelect, reason: merged with bridge method [inline-methods] */
    public void m4635lambda$doDirectSelect$5$xcengineoperateeditEditOperator(List<TouchPoint> list, List<DataCurve> list2, int i, boolean z, boolean z2) {
        boolean z3;
        BaseSelectObject baseSelectObject = this.mSelectObject;
        if (baseSelectObject != null) {
            baseSelectObject.finishSelect(true);
            this.mSelectObject = null;
        }
        List<TouchPoint> scaledPoints = getScaledPoints(list);
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        SelectTouchPoints extraSelectPath = getExtraSelectPath(scaledPoints);
        float scaleVisibleTop = this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f;
        CanvasSizeInfo canvasSizeInfo = this.mPenEngine.mCanvasOperator.mCanvasSizeInfo;
        RectF rectF = new RectF();
        extraSelectPath.mPath.computeBounds(rectF, false);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        Region region = new Region();
        PointF pointF = new PointF();
        extraSelectPath.mPath.computeBounds(rectF, false);
        Path path = new Path();
        path.set(extraSelectPath.mPath);
        if (!SkRegionHelper.LIMIT_RECT.contains(rectF)) {
            pointF.set(SkRegionHelper.calculatePathOffsetForClip(path));
            rectF.offset(pointF.x, pointF.y);
        }
        path.offset(pointF.x, pointF.y);
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        ArrayList arrayList = new ArrayList();
        RectF compatVisibleSaveRect = ScaleDataUtil.getCompatVisibleSaveRect(scaleInfo, canvasSizeInfo, drawRatio * scaleInfo.getScaleRate(), this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas);
        boolean z4 = true;
        int i2 = 0;
        for (DataCurve dataCurve : list2) {
            if (dataCurve.mDataState == 1) {
                if (!this.mPenEngine.mCanvasOperator.mCanSelectInvisibleData) {
                    RectF saveRect = dataCurve.getSaveRect();
                    dataCurve.translateRectByPenPoint(saveRect, false);
                    if (!RectF.intersects(saveRect, compatVisibleSaveRect)) {
                        continue;
                    }
                }
                if (dataCurve.hasIntersectionWithRegion(region, -pointF.x, scaleVisibleTop)) {
                    if (dataCurve.getType() == 28) {
                        z4 = false;
                    }
                    dataCurve.translateRectByPenPoint(dataCurve.getDrawRect(), true);
                    int size = dataCurve.size(dataCurve.mSavePoints) + i2;
                    if (((this.j & 1) == 1 && arrayList.size() >= this.l) || ((this.j & 2) == 2 && size > this.k && arrayList.size() > 0)) {
                        break;
                    }
                    arrayList.add(dataCurve);
                    i2 = size;
                } else {
                    continue;
                }
            }
        }
        boolean z5 = i2 > this.mPointMaxLimit ? false : z4;
        if (arrayList.isEmpty()) {
            z3 = z5;
        } else if (arrayList.size() == 1 && ((DataCurve) arrayList.get(0)).getType() == 28) {
            DataShape dataShape = (DataShape) arrayList.get(0);
            this.mSelectObject = new ShapeSelectObject(dataShape, this.mPenEngine, z2);
            RectF screenSpaceRectFromWorldSpaceRect = this.mPenEngine.getCurveEngine().getScreenSpaceRectFromWorldSpaceRect(dataShape.getDrawRect());
            Log.w(this.TAG, "xl- handleDoPathSelect: call select on");
            this.mPenEngine.getCurveEngine().getSelectSystem().selectOn(arrayList, screenSpaceRectFromWorldSpaceRect);
            z3 = false;
        } else {
            this.mSelectObject = new MultiSelectCurveObject(arrayList, this.mPenEngine, z5, z2);
            z3 = z5;
        }
        if (this.mSelectObject != null) {
            extraSelectPath.doMove(0.0f, scaleVisibleTop);
            this.mSelectObject.setSelectTouchPoint(extraSelectPath);
            this.mSelectObject.mHashCode = list.hashCode();
        }
        executeSelectObject(arrayList, i, z3, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoPathSelect, reason: merged with bridge method [inline-methods] */
    public void m4638lambda$doPathSelect$4$xcengineoperateeditEditOperator(List<TouchPoint> list, int i, boolean z, boolean z2) {
        boolean z3;
        BaseSelectObject baseSelectObject = this.mSelectObject;
        if (baseSelectObject != null) {
            baseSelectObject.finishSelect(true);
            this.mSelectObject = null;
        }
        List<TouchPoint> scaledPoints = getScaledPoints(list);
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        SelectTouchPoints extraSelectPath = getExtraSelectPath(scaledPoints);
        float scaleVisibleTop = this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f;
        CanvasSizeInfo canvasSizeInfo = this.mPenEngine.mCanvasOperator.mCanvasSizeInfo;
        RectF rectF = new RectF();
        extraSelectPath.mPath.computeBounds(rectF, false);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        List<DataCurve> curvesInArea = getCurvesInArea(list);
        Region region = new Region();
        PointF pointF = new PointF();
        extraSelectPath.mPath.computeBounds(rectF, false);
        Path path = new Path();
        path.set(extraSelectPath.mPath);
        if (!SkRegionHelper.LIMIT_RECT.contains(rectF)) {
            pointF.set(SkRegionHelper.calculatePathOffsetForClip(path));
            rectF.offset(pointF.x, pointF.y);
        }
        path.offset(pointF.x, pointF.y);
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        ArrayList arrayList = new ArrayList();
        RectF compatVisibleSaveRect = ScaleDataUtil.getCompatVisibleSaveRect(scaleInfo, canvasSizeInfo, drawRatio * scaleInfo.getScaleRate(), this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas);
        int i2 = 0;
        boolean z4 = true;
        for (DataCurve dataCurve : curvesInArea) {
            if (dataCurve.mDataState == 1) {
                if (!this.mPenEngine.mCanvasOperator.mCanSelectInvisibleData) {
                    RectF saveRect = dataCurve.getSaveRect();
                    dataCurve.translateRectByPenPoint(saveRect, false);
                    if (!RectF.intersects(saveRect, compatVisibleSaveRect)) {
                        continue;
                    }
                }
                if (dataCurve.hasIntersectionWithRegion(region, -pointF.x, scaleVisibleTop)) {
                    if (dataCurve.getType() == 28) {
                        z4 = false;
                    }
                    dataCurve.translateRectByPenPoint(dataCurve.getDrawRect(), true);
                    int size = dataCurve.size(dataCurve.mSavePoints) + i2;
                    if (((this.j & 1) == 1 && arrayList.size() >= this.l) || ((this.j & 2) == 2 && size > this.k && arrayList.size() > 0)) {
                        break;
                    }
                    arrayList.add(dataCurve);
                    i2 = size;
                } else {
                    continue;
                }
            }
        }
        boolean z5 = i2 > this.mPointMaxLimit ? false : z4;
        if (arrayList.isEmpty()) {
            z3 = z5;
        } else if (arrayList.size() == 1 && ((DataCurve) arrayList.get(0)).getType() == 28) {
            DataShape dataShape = (DataShape) arrayList.get(0);
            this.mSelectObject = new ShapeSelectObject(dataShape, this.mPenEngine, z2);
            RectF screenSpaceRectFromWorldSpaceRect = this.mPenEngine.getCurveEngine().getScreenSpaceRectFromWorldSpaceRect(dataShape.getDrawRect());
            Log.w(this.TAG, "xl- handleDoPathSelect: call select on");
            this.mPenEngine.getCurveEngine().getSelectSystem().selectOn(arrayList, screenSpaceRectFromWorldSpaceRect);
            z3 = false;
        } else {
            this.mSelectObject = new MultiSelectCurveObject(arrayList, this.mPenEngine, z5, z2);
            z3 = z5;
        }
        if (this.mSelectObject != null) {
            extraSelectPath.doMove(0.0f, scaleVisibleTop);
            this.mSelectObject.setSelectTouchPoint(extraSelectPath);
            this.mSelectObject.mHashCode = list.hashCode();
        }
        executeSelectObject(arrayList, i, z3, z);
    }

    private List<TouchPoint> getScaledPoints(List<TouchPoint> list) {
        ArrayList arrayList = new ArrayList();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        if (this.mPenEngine.mCanvasOperator.isScale) {
            for (int i = 0; i < list.size(); i++) {
                PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(list.get(i).getX(), list.get(i).getY(), scaleInfo);
                arrayList.add(new TouchPoint(pointFScaleInverseOperation.x, pointFScaleInverseOperation.y, list.get(i).getTime()));
            }
            return arrayList;
        }
        arrayList.addAll(list);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:22:0x0098  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a0 A[EDGE_INSN: B:36:0x00a0->B:26:0x00a0 BREAK  A[LOOP:0: B:13:0x0074->B:25:0x009d], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x009d A[SYNTHETIC] */
    /* JADX INFO: renamed from: handleDoPointSelect, reason: merged with bridge method [inline-methods] */
    public void m4639lambda$doPointSelect$7$xcengineoperateeditEditOperator(TouchPoint touchPoint, int i) {
        float x = touchPoint.getX();
        float y = touchPoint.getY();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        if (this.mPenEngine.mCanvasOperator.isScale) {
            PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(x, y, scaleInfo);
            float f = pointFScaleInverseOperation.x;
            y = pointFScaleInverseOperation.y;
            x = f;
        }
        BaseSelectObject baseSelectObject = this.mSelectObject;
        if (baseSelectObject != null) {
            baseSelectObject.finishSelect(true);
            this.mSelectObject = null;
        }
        float scaleVisibleTop = this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f;
        ArrayList arrayList = new ArrayList();
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        RectF rectF = new RectF();
        float f2 = x / drawRatio;
        float f3 = y / drawRatio;
        rectF.set(f2 - 10.0f, f3 - 10.0f, f2 + 10.0f, f3 + 10.0f);
        arrayList.addAll(((CurveDataManager) this.mPenEngine.mCurveDataManager).scanIntersectDataCurve(rectF, true));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            BaseData baseData = (BaseData) arrayList.get(size);
            int i2 = baseData.mDataState;
            if (i2 == 0 || i2 == 1) {
                if (baseData instanceof DataShape) {
                    DataShape dataShape = (DataShape) baseData;
                    if (dataShape.containsPoint(x, y, 0.0f, scaleVisibleTop)) {
                        this.mSelectObject = new ShapeSelectObject(dataShape, this.mPenEngine);
                        break;
                    } else if (this.mSelectObject != null) {
                        break;
                    }
                } else if (this.mSelectObject != null) {
                    break;
                    break;
                }
            }
        }
        BaseSelectObject baseSelectObject2 = this.mSelectObject;
        if (baseSelectObject2 != null) {
            baseSelectObject2.mHashCode = touchPoint.hashCode();
        }
        if (i == this.m) {
            this.mEditListener.onObjectSelect(this.mSelectObject);
            if (this.mSelectObject != null) {
                this.mPenEngine.getCurveEngine().getSelectSystem().selected();
            }
        }
    }

    private List<DataCurve> getCurvesInArea(List<TouchPoint> list) {
        SelectTouchPoints extraSelectPath = getExtraSelectPath(list);
        RectF rectF = new RectF();
        extraSelectPath.mPath.computeBounds(rectF, false);
        rectF.offset(0.0f, this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f);
        RectF worldSpaceRectFromScreenSpaceRect = this.mPenEngine.getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(rectF);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        return this.mPenEngine.mCurveDataManager.getCurvesInArea(new RectF(worldSpaceRectFromScreenSpaceRect.left * saveRatio, worldSpaceRectFromScreenSpaceRect.top * saveRatio, worldSpaceRectFromScreenSpaceRect.right * saveRatio, worldSpaceRectFromScreenSpaceRect.bottom * saveRatio));
    }
}
