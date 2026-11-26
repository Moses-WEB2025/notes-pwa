package xc.engine.data;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.ICurve;
import xc.engine.math.iink.manager.MathResultCurveManager;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.edit.select.selectobject.BaseSelectObject;
import xc.engine.operate.step.type.IStep;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.storage.ent.file_io.InkEntFileIO;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class DataAccessImpl implements IDataAccess, IDataListener, IDataSwapListener {
    private static final String TAG = "DataAccessImpl";
    public ICurveDataManager mCurveDataManager;
    private IDataListener mDataListener;
    private OnExtraDataListener mExtraDataListener;
    public IDataSwapListener mIDataSwapListener;
    public final PenEngine mPenEngine;
    public DataSet mDataSet = new DataSet();
    private final Object saveMathResultLock = new Object();

    public interface GetPageOffsetCallback {
        PointF getPageOffset(int i);
    }

    private short getDataType(int i) {
        if (i != 7) {
            return i != 11 ? (short) 1 : (short) 11;
        }
        return (short) 7;
    }

    public DataAccessImpl(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        this.mCurveDataManager = penEngine.getCurveDataManager();
        this.mDataSet.setGetPageOffsetCallback(new GetPageOffsetCallback() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda4
            @Override // xc.engine.data.DataAccessImpl.GetPageOffsetCallback
            public final PointF getPageOffset(int i) {
                return this.f$0.m4616lambda$new$0$xcenginedataDataAccessImpl(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$xc-engine-data-DataAccessImpl, reason: not valid java name */
    /* synthetic */ PointF m4616lambda$new$0$xcenginedataDataAccessImpl(int i) {
        return new PointF(this.mPenEngine.getCurveEngine().getPageSystem().getPageData(i).getOriginXInWS(), this.mPenEngine.getCurveEngine().getPageSystem().getPageData(i).getOriginYInWS());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleLoadEntFile, reason: merged with bridge method [inline-methods] */
    public void m4614lambda$loadEntFile$1$xcenginedataDataAccessImpl(String str, int i) {
        onDataLoaded(loadEntFile(str, this.mPenEngine.mLoadEntFile, i));
    }

    private void onLoadFinish(RectF rectF, int i) {
        ICanvasOperateListener iCanvasOperateListener = this.mPenEngine.mCanvasOperator.mCanvasOperateListener;
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(null, 13, i);
        }
    }

    private boolean syncSaveFile(String str, InkEntFileIO inkEntFileIO, int i) {
        return (this.mPenEngine.isRelease() || !saveFile(str, PenEngineUtils.getTempFilePath(str), inkEntFileIO, i) || this.mPenEngine.isRelease()) ? false : true;
    }

    private RectF f(RectF rectF) {
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        return new RectF(rectF.left * drawRatio, rectF.top * drawRatio, rectF.right * drawRatio, rectF.bottom * drawRatio);
    }

    private void clear() {
        this.mCurveDataManager.clear();
        this.mDataSet.clear();
        this.mPenEngine.mStepManager.clearStep();
    }

    private int countMathResultCurvesInDataSet() {
        return countMathResultCurvesInDataSet(getDataSet());
    }

    private int countMathResultCurvesInDataSet(DataSet dataSet) {
        int i = 0;
        if (dataSet != null && dataSet.mAllDataMap != null) {
            for (List<BaseData> list : dataSet.mAllDataMap.values()) {
                if (list != null) {
                    Iterator<BaseData> it = list.iterator();
                    while (it.hasNext()) {
                        if (isMathResultCurve(it.next())) {
                            i++;
                        }
                    }
                }
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0080  */
    @Override // xc.engine.data.IDataAccess
    public RectF getContentRange() {
        RectF rectF;
        RectF rectF2;
        if (this.mPenEngine.isRelease()) {
            return new RectF();
        }
        BaseSelectObject baseSelectObject = this.mPenEngine.mEditOperator.mSelectObject;
        if (baseSelectObject == null || !baseSelectObject.l) {
            rectF = null;
        } else {
            RectF rectF3 = baseSelectObject.mSelectObjectRect;
            if (rectF3 == null) {
                rectF2 = new RectF();
            } else {
                RectF rectF4 = new RectF();
                rectF4.set(this.mPenEngine.mCurveDataManager.limitRect(rectF3));
                rectF4.left -= 1.0f;
                rectF4.top -= 1.0f;
                rectF4.right += 1.0f;
                rectF4.bottom += 1.0f;
                rectF2 = rectF4;
            }
            rectF = new RectF(rectF2);
        }
        RectF rectF5 = new RectF();
        RectF rectFCalculateDrawRect = this.mPenEngine.mCurveDataManager.calculateDrawRect();
        if (rectFCalculateDrawRect.isEmpty()) {
            if (rectF != null && !rectF.isEmpty()) {
                rectF5.set(rectF);
            } else {
                rectF5.set(rectFCalculateDrawRect);
            }
        } else if (rectF != null && !rectF.isEmpty()) {
            rectF5.set(rectFCalculateDrawRect);
            rectF5.union(rectF);
        } else {
            rectF5.set(rectFCalculateDrawRect);
        }
        rectF5.set(f(rectF5));
        return PenEngineUtils.getCopyRectF(rectF5);
    }

    @Override // xc.engine.data.IDataAccess
    public RectF getVisibleRange() {
        return new RectF(0.0f, 0.0f, this.mPenEngine.mCanvasOperator.getVisibleWidth(), this.mPenEngine.mCanvasOperator.getVisibleHeight());
    }

    @Override // xc.engine.data.IDataAccess
    public DataSet getDataSet() {
        return this.mPenEngine.isRelease() ? new DataSet() : this.mDataSet;
    }

    @Override // xc.engine.data.IDataAccess
    public void loadEntFile(final String str, final int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4614lambda$loadEntFile$1$xcenginedataDataAccessImpl(str, i);
            }
        });
    }

    @Override // xc.engine.data.IDataAccess
    public void loadEntFile(String str) {
        loadEntFile(str, 0);
    }

    @Override // xc.engine.data.IDataAccess
    public boolean saveEntFileSync(String str) {
        return saveEntFileSync(str, 0);
    }

    @Override // xc.engine.data.IDataAccess
    public boolean saveEntFileSync(String str, int i) {
        return syncSaveFile(str, this.mPenEngine.mLoadEntFile, i);
    }

    @Override // xc.engine.data.IDataAccess
    public PathInfo getCurrentPathInfo() {
        return new PathInfo(getDataSet().mAllDataMap);
    }

    @Override // xc.engine.data.IDataAccess
    public List<IStep> getUndoList() {
        return this.mPenEngine.mStepManager.getUndoList();
    }

    @Override // xc.engine.data.IDataAccess
    public List<IStep> getRedoList() {
        return this.mPenEngine.mStepManager.getRedoList();
    }

    @Override // xc.engine.data.IDataAccess
    public boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2) {
        return loadPathInfo(pathInfo, list, list2, 0);
    }

    @Override // xc.engine.data.IDataAccess
    public boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2, final int i) {
        PathInfo pathInfo2 = pathInfo == null ? new PathInfo() : pathInfo;
        List<IStep> arrayList = list == null ? new ArrayList<>() : list;
        List<IStep> arrayList2 = list2 == null ? new ArrayList<>() : list2;
        final RectF rectFCalculateDrawRect = this.mCurveDataManager.calculateDrawRect();
        clear();
        final ICurveDataManager iCurveDataManager = this.mPenEngine.mCurveDataManager;
        Iterator<IStep> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().resetContext(this.mPenEngine);
        }
        Iterator<IStep> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            it2.next().resetContext(this.mPenEngine);
        }
        this.mPenEngine.mStepManager.addRedoList(arrayList);
        this.mPenEngine.mStepManager.addUndoList(arrayList2);
        DataSet dataSet = getDataSet();
        List<BaseData> listComputeIfAbsent = pathInfo2.getAllDataList().computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataAccessImpl.lambda$loadPathInfo$2((Integer) obj);
            }
        });
        final ArrayList arrayList3 = new ArrayList(listComputeIfAbsent.size());
        int size = 0;
        int size2 = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < listComputeIfAbsent.size(); i4++) {
            BaseData baseData = listComputeIfAbsent.get(i4);
            if (baseData.getType() == 1 || baseData.getType() == 28) {
                DataCurve dataCurve = (DataCurve) baseData;
                if (dataCurve.mDataState == 3) {
                    i3++;
                    size2 += dataCurve.size(dataCurve.mSavePoints);
                } else {
                    i2++;
                    size += dataCurve.size(dataCurve.mSavePoints);
                }
                arrayList3.add(dataCurve);
                Log.i(TAG, String.format("[loadPathInfo] page[%d] dataCurveSize[%d]", Integer.valueOf(dataCurve.pageIndex), Integer.valueOf(dataCurve.getDataSize())));
                dataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(dataCurve.pageIndex), new Function() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return DataAccessImpl.lambda$loadPathInfo$3((Integer) obj);
                    }
                }).add(dataCurve);
                iCurveDataManager.addDataToPool(dataCurve);
            }
        }
        dataSet.mDataCurves.addAll(arrayList3);
        dataSet.mAllData.addAll(arrayList3);
        dataSet.setDataAndPoint(size, size2, i2, i3, i);
        if (!arrayList3.isEmpty()) {
            dataSet.addId(((DataCurve) arrayList3.get(arrayList3.size() - 1)).mId + 1);
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4615lambda$loadPathInfo$4$xcenginedataDataAccessImpl(iCurveDataManager, arrayList3, rectFCalculateDrawRect, i);
            }
        });
        return true;
    }

    static /* synthetic */ List lambda$loadPathInfo$2(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$loadPathInfo$3(Integer num) {
        return new ArrayList();
    }

    @Override // xc.engine.data.IDataAccess
    public void setDataBufferListener(IDataSwapListener iDataSwapListener) {
        this.mIDataSwapListener = iDataSwapListener;
    }

    @Override // xc.engine.data.IDataAccess
    public List<ICurve> getCurveData(Rect rect) {
        return new ArrayList(getDataCurveList(rect));
    }

    @Override // xc.engine.data.IDataAccess
    public void clearEngineData() {
        this.mCurveDataManager.calculateDrawRect();
        clear();
        getDataSet().setDataAndPoint(0, 0, 0, 0, 0);
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 14);
    }

    static /* synthetic */ List lambda$hasValidCurVeData$5(Integer num) {
        return new ArrayList();
    }

    @Override // xc.engine.data.IDataAccess
    public boolean hasValidCurVeData(int i) {
        for (BaseData baseData : this.mDataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataAccessImpl.lambda$hasValidCurVeData$5((Integer) obj);
            }
        })) {
            if (baseData != null && baseData.mDataState != 3) {
                return true;
            }
        }
        return false;
    }

    private boolean loadEntFile(String str, InkEntFileIO inkEntFileIO, int i) {
        FileInputStream fileInputStream;
        RectF rectFCalculateDrawRect = this.mCurveDataManager.calculateDrawRect();
        if (i == 0) {
            clear();
        }
        try {
            if (TextUtils.isEmpty(str)) {
                this.mPenEngine.setTime(System.currentTimeMillis());
                fileInputStream = null;
            } else {
                FileInputStream fileInputStream2 = new FileInputStream(str);
                byte[] bArr = new byte[12];
                FileInputStream fileInputStream3 = new FileInputStream(str);
                try {
                    fileInputStream3.read(bArr);
                    fileInputStream3.close();
                    if (TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), "HWPENKITDATA")) {
                        try {
                            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(fileInputStream2));
                            try {
                                inkEntFileIO.read(this.mDataSet, dataInputStream, i, FeatureManager.getInstance().isEnableMathRecognition());
                                dataInputStream.close();
                            } catch (Throwable th) {
                                try {
                                    dataInputStream.close();
                                    throw th;
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                    throw th;
                                }
                            }
                        } catch (Throwable unused) {
                        }
                    }
                    if (TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), PageEntConstants.SUNIA_OLD_SINGLE_PAGE_FORMAT_HEAD)) {
                        try {
                            DataInputStream dataInputStream2 = new DataInputStream(new BufferedInputStream(fileInputStream2));
                            try {
                                RandomAccessFile randomAccessFile = new RandomAccessFile(str, Constants.RANDOM_LONG);
                                try {
                                    inkEntFileIO.readSinglePage(this.mDataSet, dataInputStream2, randomAccessFile, i);
                                    randomAccessFile.close();
                                    dataInputStream2.close();
                                } catch (Throwable th3) {
                                    try {
                                        randomAccessFile.close();
                                        throw th3;
                                    } catch (Throwable th4) {
                                        th3.addSuppressed(th4);
                                        throw th3;
                                    }
                                }
                            } catch (Throwable th5) {
                                try {
                                    dataInputStream2.close();
                                    throw th5;
                                } catch (Throwable th6) {
                                    th5.addSuppressed(th6);
                                    throw th5;
                                }
                            }
                        } catch (Exception e) {
                            Log.d(TAG, "image error: " + e.getMessage());
                        }
                    }
                    fileInputStream = fileInputStream2;
                } catch (Throwable th7) {
                    try {
                        fileInputStream3.close();
                        throw th7;
                    } catch (Throwable th8) {
                        th7.addSuppressed(th8);
                        throw th7;
                    }
                }
            }
            onLoadFinish(rectFCalculateDrawRect, i);
            if (fileInputStream == null) {
                return true;
            }
            try {
                fileInputStream.close();
                return true;
            } catch (IOException e2) {
                Log.e(TAG, "[loadEnt] call exception: " + e2.getMessage());
                return true;
            }
        } catch (IOException e3) {
            this.mPenEngine.setTime(System.currentTimeMillis());
            if (this.mPenEngine.mErrorListener != null) {
                this.mPenEngine.mErrorListener.onError(e3);
            }
            this.mDataSet.setDataAndPoint(0, 0, 0, 0, 0);
            onLoadFinish(rectFCalculateDrawRect, i);
            return false;
        }
    }

    private void saveMathResultCurves(int i) {
        synchronized (this.saveMathResultLock) {
            MathSystem mathSystem = this.mPenEngine.getCurveEngine().getMathSystem();
            DataSet dataSet = getDataSet();
            if (mathSystem.hasRenderContent()) {
                List<DataCurve> resultCurves = mathSystem.getResultCurves();
                if (!isNoNewCurves(resultCurves)) {
                    saveNewCurvesByPage(dataSet, resultCurves);
                } else {
                    Log.d(TAG, "saveMathResultCurves: cleared old math result curves, no new curves to save.");
                }
            } else {
                Log.d(TAG, "saveMathResultCurves: cleared old math result curves, no render content.");
            }
        }
    }

    private boolean isNoNewCurves(List<DataCurve> list) {
        return list == null || list.isEmpty();
    }

    private void removeAllSavedMathResultCurvesFromAllPages(DataSet dataSet, List<DataCurve> list) {
        final HashSet hashSet = new HashSet();
        Iterator<Map.Entry<Integer, List<BaseData>>> it = dataSet.mAllDataMap.entrySet().iterator();
        while (it.hasNext()) {
            List<BaseData> value = it.next().getValue();
            if (value != null) {
                for (BaseData baseData : value) {
                    if (isMathResultCurve(baseData)) {
                        hashSet.add(baseData);
                    }
                }
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        for (List<BaseData> list2 : dataSet.mAllDataMap.values()) {
            if (list2 != null) {
                list2.removeAll(hashSet);
            }
        }
        List<BaseData> list3 = dataSet.mAllData;
        Objects.requireNonNull(hashSet);
        list3.removeIf(new Predicate() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return hashSet.contains((BaseData) obj);
            }
        });
        Log.d("checkMathSave", "removeAllSavedMathResultCurvesFromAllPages: removed " + hashSet.size() + " curves");
    }

    private boolean isMathResultCurve(BaseData baseData) {
        if (!(baseData instanceof DataCurve)) {
            return false;
        }
        DataCurve dataCurve = (DataCurve) baseData;
        return (!dataCurve.isMathResult || dataCurve.mathBlockId == null || dataCurve.mathBlockId.isEmpty()) ? false : true;
    }

    private void saveNewCurvesByPage(DataSet dataSet, List<DataCurve> list) {
        HashMap map = new HashMap();
        for (DataCurve dataCurve : list) {
            MathResultCurveManager.markAsMathResult(dataCurve);
            int iDetermineCurvePageIndex = dataCurve.pageIndex;
            if (iDetermineCurvePageIndex < 0 && (iDetermineCurvePageIndex = determineCurvePageIndex(dataCurve)) < 0) {
                Log.w("checkMathSave", "saveNewCurvesByPage: cannot determine pageIndex, using 0");
                iDetermineCurvePageIndex = 0;
            }
            dataCurve.pageIndex = iDetermineCurvePageIndex;
            ((List) map.computeIfAbsent(Integer.valueOf(iDetermineCurvePageIndex), new Function() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DataAccessImpl.lambda$saveNewCurvesByPage$6((Integer) obj);
                }
            })).add(dataCurve);
        }
        for (Map.Entry entry : map.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            List list2 = (List) entry.getValue();
            dataSet.mAllData.addAll(list2);
            dataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(iIntValue), new Function() { // from class: xc.engine.data.DataAccessImpl$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DataAccessImpl.lambda$saveNewCurvesByPage$7((Integer) obj);
                }
            }).addAll(list2);
        }
        Log.d("checkMathSave", "saveNewCurvesByPage: saved " + list.size() + " curves, byPage=" + map.keySet());
    }

    static /* synthetic */ List lambda$saveNewCurvesByPage$6(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$saveNewCurvesByPage$7(Integer num) {
        return new ArrayList();
    }

    private int determineCurvePageIndex(DataCurve dataCurve) {
        ICurvePointList curvePointList;
        try {
            PenEngine penEngine = this.mPenEngine;
            if (penEngine != null && !penEngine.isRelease() && this.mPenEngine.getCurveEngine() != null) {
                if (dataCurve.mSavePoints != 0 && (curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints)) != null && curvePointList.size() > 0) {
                    int pageIndexByWorldSpacePoint = this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(curvePointList.getX(0), curvePointList.getY(0)));
                    if (pageIndexByWorldSpacePoint >= 0) {
                        return pageIndexByWorldSpacePoint;
                    }
                }
                RectF saveRect = dataCurve.getSaveRect();
                if (saveRect != null && !saveRect.isEmpty()) {
                    int pageIndexByWorldSpacePoint2 = this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(saveRect.centerX(), saveRect.centerY()));
                    if (pageIndexByWorldSpacePoint2 >= 0) {
                        return pageIndexByWorldSpacePoint2;
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "determineCurvePageIndex: error", e);
            return -1;
        }
    }

    private void logBeforeSave(DataSet dataSet, List<DataCurve> list) {
        int iCountMathResultCurvesInDataSet = countMathResultCurvesInDataSet(dataSet);
        Log.d("checkMathSave", "=== Before Save ===");
        Log.d("checkMathSave", "Existing: " + iCountMathResultCurvesInDataSet + ", New: " + list.size());
    }

    private void logAfterSave(DataSet dataSet, List<DataCurve> list) {
        int iCountMathResultCurvesInDataSet = countMathResultCurvesInDataSet(dataSet);
        int size = list.size();
        Log.d("checkMathSave", "=== After Save ===");
        Log.d("checkMathSave", "Final: " + iCountMathResultCurvesInDataSet + ", Expected: " + size);
        if (iCountMathResultCurvesInDataSet == size) {
            Log.d("checkMathSave", "Save successful");
        } else {
            Log.w("checkMathSave", "Mismatch: final=" + iCountMathResultCurvesInDataSet + ", expected=" + size);
        }
    }

    private Set<String> collectMathBlockIds(int i) {
        HashSet hashSet = new HashSet();
        List<BaseData> list = getDataSet().mAllDataMap.get(Integer.valueOf(i));
        if (list != null) {
            for (BaseData baseData : list) {
                if (baseData instanceof DataCurve) {
                    DataCurve dataCurve = (DataCurve) baseData;
                    if (dataCurve.isMathResult && dataCurve.mathBlockId != null && !dataCurve.mathBlockId.isEmpty()) {
                        hashSet.add(dataCurve.mathBlockId);
                    }
                }
            }
        }
        return hashSet;
    }

    private void saveMathBlockIdToRawContent(String str, String str2) throws Throwable {
        String strExtractNumericId;
        if (str == null || str2 == null || str2.isEmpty() || (strExtractNumericId = extractNumericId(str2)) == null || strExtractNumericId.isEmpty()) {
            return;
        }
        try {
            File parentFile = new File(str).getParentFile();
            if (parentFile != null && parentFile.exists()) {
                File file = new File(parentFile, "raw-content");
                if (!file.exists() && !file.mkdirs()) {
                    Log.e(TAG, "saveMathBlockIdToRawContent: failed to create raw-content directory");
                    return;
                }
                File file2 = new File(file, strExtractNumericId);
                FileOutputStream fileOutputStream = null;
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                    try {
                        fileOutputStream2.write(strExtractNumericId.getBytes("UTF-8"));
                        fileOutputStream2.flush();
                        Log.d(TAG, "saveMathBlockIdToRawContent: saved mathBlockId=" + str2 + ", numericId=" + strExtractNumericId + " to " + file2.getAbsolutePath());
                        try {
                            fileOutputStream2.close();
                            return;
                        } catch (IOException e) {
                            Log.e(TAG, "saveMathBlockIdToRawContent: failed to close file", e);
                            return;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                Log.e(TAG, "saveMathBlockIdToRawContent: failed to close file", e2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            Log.w(TAG, "saveMathBlockIdToRawContent: ent directory not found");
        } catch (Exception e3) {
            Log.e(TAG, "saveMathBlockIdToRawContent: error saving mathBlockId", e3);
        }
    }

    private String extractNumericId(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        try {
            Long.parseLong(str);
            return str;
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
            return null;
        }
    }

    private void restoreMathResultCurvesToMathSystem(int i) {
        try {
            MathSystem mathSystem = this.mPenEngine.getCurveEngine().getMathSystem();
            if (mathSystem == null) {
                Log.w(TAG, "restoreMathResultCurvesToMathSystem: MathSystem is null");
                return;
            }
            List<BaseData> list = getDataSet().mAllDataMap.get(Integer.valueOf(i));
            if (list != null && !list.isEmpty()) {
                for (BaseData baseData : list) {
                    if (baseData instanceof DataCurve) {
                        DataCurve dataCurve = (DataCurve) baseData;
                        if (dataCurve.isMathResult && dataCurve.mathBlockId != null && !dataCurve.mathBlockId.isEmpty()) {
                            String str = "raw-content/" + dataCurve.mathBlockId;
                            CurveProp curveProp = dataCurve.getCurveProp();
                            if (curveProp != null) {
                                mathSystem.syncCurvePropForMathBlock(str, curveProp);
                                Log.d(TAG, String.format("restoreMathResultCurvesToMathSystem: synced mathBlockId[%s] (full: %s) curveProp for curve mId[%d]", dataCurve.mathBlockId, str, Integer.valueOf(dataCurve.mId)));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "restoreMathResultCurvesToMathSystem: error restoring math result curves", e);
        }
    }

    private boolean saveFile(String str, String str2, InkEntFileIO inkEntFileIO, int i) {
        saveMathResultCurves(i);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            Log.d(TAG, "saveFile: FilePath:" + str);
            File file = new File(str);
            String fileNameFromPath = PenEngineUtils.getFileNameFromPath(str);
            try {
                inkEntFileIO.write(this.mDataSet, dataOutputStream, i);
                if (file.exists()) {
                    file.delete();
                }
                PenEngineUtils.renameFile(str2, fileNameFromPath);
                try {
                    fileOutputStream.close();
                    return true;
                } catch (IOException e) {
                    Log.e(TAG, "[saveFile] call exception: " + e.getMessage());
                    return true;
                }
            } catch (IOException e2) {
                File file2 = new File(str2);
                if (file2.exists()) {
                    file2.delete();
                }
                if (this.mPenEngine.mErrorListener != null) {
                    this.mPenEngine.mErrorListener.onError(e2);
                }
                try {
                    fileOutputStream.close();
                    return false;
                } catch (IOException e3) {
                    Log.e(TAG, "[saveFile] call exception: " + e3.getMessage());
                    return false;
                }
            }
        } catch (FileNotFoundException e4) {
            Log.e(TAG, "[saveFile] call exception: " + e4.getMessage());
            return false;
        }
    }

    public final List<DataCurve> getDataCurveList(Rect rect) {
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : new ArrayList(this.mDataSet.mDataCurves)) {
            if (dataCurve != null && dataCurve.mDataState == 1) {
                dataCurve.translateRectByPenPoint(dataCurve.getSaveRect(), false);
                if (RectF.intersects(dataCurve.getSaveRect(), new RectF(rect.left, rect.top, rect.right, rect.bottom))) {
                    arrayList.add(dataCurve);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: loadEntFile, reason: merged with bridge method [inline-methods] */
    public void m4615lambda$loadPathInfo$4$xcenginedataDataAccessImpl(ICurveDataManager iCurveDataManager, List<DataCurve> list, RectF rectF, int i) {
        iCurveDataManager.batchExecCurveGenTask(list);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            iCurveDataManager.batchExecScalableCurveGenTask(list);
        }
        onLoadFinish(rectF, i);
    }

    @Override // xc.engine.data.IDataAccess
    public void setIDataListener(IDataListener iDataListener) {
        this.mDataListener = iDataListener;
    }

    @Override // xc.engine.data.IDataListener
    public void onDataLoaded(boolean z) {
        IDataListener iDataListener = this.mDataListener;
        if (iDataListener != null) {
            iDataListener.onDataLoaded(z);
        }
    }

    @Override // xc.engine.data.IDataSwapListener
    public void onLoadListener(int i, int[] iArr, int i2, List<byte[]> list) {
        if (this.mExtraDataListener == null || iArr == null) {
            return;
        }
        int length = iArr.length;
        short[] sArr = new short[length];
        for (int i3 = 0; i3 < length; i3++) {
            sArr[i3] = getDataType(iArr[i3]);
        }
        this.mExtraDataListener.onLoadListener(i, sArr, i2, list);
    }

    @Override // xc.engine.data.IDataAccess
    public void setOnExtraDataListener(OnExtraDataListener onExtraDataListener) {
        this.mExtraDataListener = onExtraDataListener;
        setDataBufferListener(this);
    }

    @Override // xc.engine.data.IDataSwapListener
    public CsBufferBean onSaveListener(int i) {
        return this.mExtraDataListener.onSaveExtraData(i);
    }

    @Override // xc.engine.data.IDataAccess
    public void setExtraTotalPoint(long j) {
        getDataSet().setExtraTotalPoint(j);
    }

    @Override // xc.engine.data.IDataAccess
    public long getTotalSavePoint(int i) {
        return getDataSet().getTotalVisibleSavePoint(i);
    }

    @Override // xc.engine.data.IDataAccess
    public boolean deletePageAndHaveShiftIndex(int i) {
        DataSet dataSet = this.mDataSet;
        if (dataSet != null) {
            return dataSet.deletePageAndHaveShiftPage(i);
        }
        return false;
    }

    @Override // xc.engine.data.IDataAccess
    public void shiftPage(int i, float f, float f2) {
        DataSet dataSet = this.mDataSet;
        if (dataSet != null) {
            dataSet.shiftPages(i, f, f2);
        }
    }

    @Override // xc.engine.data.IDataAccess
    public boolean updateCurveDataPageIndex(int i, int i2, int i3) {
        if (this.mDataSet == null) {
            return false;
        }
        return this.mDataSet.updateCurveDataPageIndex(i, i2, i3, this.mPenEngine.getCurveEngine().getPageSystem().getPageData(i3).getOriginXInWS(), this.mPenEngine.getCurveEngine().getPageSystem().getPageData(i3).getOriginYInWS());
    }
}
