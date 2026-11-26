package com.miui.libhandwritingpreview.util;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.fileio.PreInkEntFileIO;
import com.miui.libhandwritingpreview.manager.PreCurveDataManager;
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
import java.util.List;
import java.util.function.Function;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.data.CsBufferBean;
import xc.engine.data.IDataAccess;
import xc.engine.data.IDataListener;
import xc.engine.data.IDataSwapListener;
import xc.engine.data.OnExtraDataListener;
import xc.engine.data.PathInfo;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.ICurve;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.step.type.IStep;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PreDataAccessImpl implements IDataAccess, IDataListener, IDataSwapListener {
    private static final String TAG = "PreDataAccessImpl";
    public ICurveDataManager mCurveDataManager;
    private IDataListener mDataListener;
    public DataSet mDataSet = new DataSet();
    private OnExtraDataListener mExtraDataListener;
    public IDataSwapListener mIDataSwapListener;
    public final PenPreEngine mPenEngine;

    private short getDataType(int i) {
        if (i != 7) {
            return i != 11 ? (short) 1 : (short) 11;
        }
        return (short) 7;
    }

    @Override // xc.engine.data.IDataAccess
    public List<IStep> getRedoList() {
        return null;
    }

    @Override // xc.engine.data.IDataAccess
    public List<IStep> getUndoList() {
        return null;
    }

    public PreDataAccessImpl(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
        this.mCurveDataManager = penPreEngine.getCurveDataManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleLoadEntFile, reason: merged with bridge method [inline-methods] */
    public void m897xe2c5fad0(String str, int i) {
        onDataLoaded(loadEntFile(str, this.mPenEngine.mLoadEntFile, i));
    }

    private void onLoadFinish(RectF rectF, int i) {
        ICanvasOperateListener iCanvasOperateListener = this.mPenEngine.mCanvasOperator.mCanvasOperateListener;
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(null, 13, i);
        }
    }

    private boolean syncSaveFile(String str, PreInkEntFileIO preInkEntFileIO, int i) {
        return (this.mPenEngine.isRelease() || !saveFile(str, PenEngineUtils.getTempFilePath(str), preInkEntFileIO, i) || this.mPenEngine.isRelease()) ? false : true;
    }

    private RectF f(RectF rectF) {
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        return new RectF(rectF.left * drawRatio, rectF.top * drawRatio, rectF.right * drawRatio, rectF.bottom * drawRatio);
    }

    private void clear() {
        this.mCurveDataManager.clear();
        this.mDataSet.clear();
    }

    @Override // xc.engine.data.IDataAccess
    public RectF getContentRange() {
        if (this.mPenEngine.isRelease()) {
            return new RectF();
        }
        RectF rectF = new RectF();
        RectF rectFCalculateDrawRect = this.mPenEngine.mCurveDataManager.calculateDrawRect();
        rectFCalculateDrawRect.isEmpty();
        rectF.set(rectFCalculateDrawRect);
        rectF.set(f(rectF));
        return PenEngineUtils.getCopyRectF(rectF);
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
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.util.PreDataAccessImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m897xe2c5fad0(str, i);
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
    public boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2) {
        return loadPathInfo(pathInfo, list, list2, 0);
    }

    @Override // xc.engine.data.IDataAccess
    public boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2, final int i) {
        PathInfo pathInfo2 = pathInfo == null ? new PathInfo() : pathInfo;
        final RectF rectFCalculateDrawRect = this.mCurveDataManager.calculateDrawRect();
        clear();
        final PreCurveDataManager preCurveDataManager = this.mPenEngine.mCurveDataManager;
        DataSet dataSet = getDataSet();
        List<BaseData> listComputeIfAbsent = pathInfo2.getAllDataList().computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.miui.libhandwritingpreview.util.PreDataAccessImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PreDataAccessImpl.lambda$loadPathInfo$1((Integer) obj);
            }
        });
        final ArrayList arrayList = new ArrayList(listComputeIfAbsent.size());
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
                arrayList.add(dataCurve);
                Log.i(TAG, String.format("[loadPathInfo] page[%d] dataCurveSize[%d]", Integer.valueOf(dataCurve.pageIndex), Integer.valueOf(dataCurve.getDataSize())));
                dataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(dataCurve.pageIndex), new Function() { // from class: com.miui.libhandwritingpreview.util.PreDataAccessImpl$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return PreDataAccessImpl.lambda$loadPathInfo$2((Integer) obj);
                    }
                }).add(dataCurve);
                preCurveDataManager.addDataToPool(dataCurve);
            }
        }
        dataSet.mDataCurves.addAll(arrayList);
        dataSet.mAllData.addAll(arrayList);
        dataSet.setDataAndPoint(size, size2, i2, i3, i);
        if (!arrayList.isEmpty()) {
            dataSet.addId(((DataCurve) arrayList.get(arrayList.size() - 1)).mId + 1);
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.util.PreDataAccessImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m898x1cbe47e5(preCurveDataManager, arrayList, rectFCalculateDrawRect, i);
            }
        });
        return true;
    }

    static /* synthetic */ List lambda$loadPathInfo$1(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$loadPathInfo$2(Integer num) {
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

    static /* synthetic */ List lambda$hasValidCurVeData$4(Integer num) {
        return new ArrayList();
    }

    @Override // xc.engine.data.IDataAccess
    public boolean hasValidCurVeData(int i) {
        for (BaseData baseData : new ArrayList(this.mDataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.miui.libhandwritingpreview.util.PreDataAccessImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PreDataAccessImpl.lambda$hasValidCurVeData$4((Integer) obj);
            }
        }))) {
            if (baseData != null && baseData.mDataState != 3) {
                return true;
            }
        }
        return false;
    }

    private boolean loadEntFile(String str, PreInkEntFileIO preInkEntFileIO, int i) {
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
                fileInputStream3.read(bArr);
                fileInputStream3.close();
                if (TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), "HWPENKITDATA")) {
                    try {
                        preInkEntFileIO.read(this.mDataSet, new DataInputStream(new BufferedInputStream(fileInputStream2)), i, FeatureManager.getInstance().isEnableMathRecognition());
                    } catch (Throwable unused) {
                    }
                }
                if (TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), PageEntConstants.SUNIA_OLD_SINGLE_PAGE_FORMAT_HEAD)) {
                    try {
                        preInkEntFileIO.readSinglePage(this.mDataSet, new DataInputStream(new BufferedInputStream(fileInputStream2)), new RandomAccessFile(str, Constants.RANDOM_LONG), i);
                    } catch (Exception e) {
                        Log.d(TAG, "image error: " + e.getMessage());
                    }
                }
                fileInputStream = fileInputStream2;
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

    private boolean saveFile(String str, String str2, PreInkEntFileIO preInkEntFileIO, int i) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            Log.d(TAG, "saveFile: FilePath:" + str);
            File file = new File(str);
            String fileNameFromPath = PenEngineUtils.getFileNameFromPath(str);
            try {
                preInkEntFileIO.write(this.mDataSet, dataOutputStream, i);
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
    public void m898x1cbe47e5(ICurveDataManager iCurveDataManager, List<DataCurve> list, RectF rectF, int i) {
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
