package xc.engine.data.pagedata;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.CurveGenTask;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.FeatureManager;

/* JADX INFO: loaded from: classes6.dex */
public class CurveRecognizeManager implements ICurveChangeListener {
    private static final String TAG = "CurveRecognizeManager";
    private CurveProp curveProp;
    private final Handler mBackgroundHandler;
    private PenEngine mPenEngine;
    private HandlerThread mRecognizeThread;
    private final MutableLiveData<Pair<List<TouchPoint>, Integer>> strokeLiveDataWithKey = new MutableLiveData<>();
    private final ConcurrentHashMap<Integer, String[]> strokeIdMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CurveProp> mathBlockCurvePropMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, DataCurve> pendingCurveMap = new ConcurrentHashMap<>();
    private final Map<Integer, Runnable> pendingRunnables = new HashMap();
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public CurveRecognizeManager(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        HandlerThread handlerThread = new HandlerThread("CurveRecognize");
        this.mRecognizeThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mRecognizeThread.getLooper());
    }

    public void setStrokeData(List<TouchPoint> list, int i) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.strokeLiveDataWithKey.setValue(new Pair<>(list, Integer.valueOf(i)));
    }

    public void setStrokeIds(String[] strArr, int i, String str) {
        if (strArr != null) {
            this.strokeIdMap.put(Integer.valueOf(i), strArr);
        }
        DataCurve dataCurveRemove = this.pendingCurveMap.remove(Integer.valueOf(i));
        if (dataCurveRemove == null || strArr == null || strArr.length <= 0) {
            return;
        }
        dataCurveRemove.strokeId = strArr[0];
        dataCurveRemove.recordID = str;
        Log.d("yjg", "CurveRecognizeManager - Save  strokeId to DataCurve: " + strArr[0]);
        CurveRecognizeData curveRecognizeDataBuildRecognizeData = buildRecognizeData(dataCurveRemove, "ADD");
        curentCurveProp(dataCurveRemove, "ADD");
        CurveProp curveProp = new CurveProp(dataCurveRemove.getPenType(), -32768, dataCurveRemove.getCurvePenWidth(), dataCurveRemove.getAlpha());
        for (String str2 : strArr) {
            this.mathBlockCurvePropMap.put(str2, curveProp);
        }
        Log.d("CurveRecognize", "RecognizeData: " + curveRecognizeDataBuildRecognizeData.toString());
    }

    public String getStrokeId(int i) {
        String[] strArr = this.strokeIdMap.get(Integer.valueOf(i));
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        return strArr[0];
    }

    public LiveData<Pair<List<TouchPoint>, Integer>> getStrokeLiveDataWithKey() {
        return this.strokeLiveDataWithKey;
    }

    @Override // xc.engine.data.pagedata.ICurveChangeListener
    public void onCurveChanged(final DataCurve dataCurve, final String str) {
        this.mBackgroundHandler.post(new Runnable() { // from class: xc.engine.data.pagedata.CurveRecognizeManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4620x2a238b5d(dataCurve, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onCurveChanged$0$xc-engine-data-pagedata-CurveRecognizeManager, reason: not valid java name */
    /* synthetic */ void m4620x2a238b5d(DataCurve dataCurve, String str) {
        try {
            processCurveRecognize(dataCurve, str);
        } catch (Exception e) {
            Log.e(TAG, "onCurveChanged FAIL: " + e.getMessage());
        }
    }

    private void processCurveRecognize(final DataCurve dataCurve, String str) {
        Log.d(TAG, "processCurveRecognize  dataState:" + dataCurve.mDataState);
        if (!FeatureManager.getInstance().isEnableMathRecognition()) {
            Log.d(TAG, "MathRecognition is disabled, skip convertCurveToTouchPoints");
            return;
        }
        final int iIdentityHashCode = System.identityHashCode(dataCurve);
        if ("ADD".equals(str)) {
            if (dataCurve.strokeId != null && !dataCurve.strokeId.isEmpty()) {
                Log.d(TAG, "processCurveRecognize: curve already has strokeId[" + dataCurve.strokeId + "], skip sending to SDK");
                return;
            }
            PenType penType = dataCurve.getPenType();
            if (penType == PenType.PENCIL || penType == PenType.INK || penType == PenType.BALL) {
                ensureOriginPointsGenerated(dataCurve);
                final List<TouchPoint> listConvertCurveToTouchPoints = convertCurveToTouchPoints(dataCurve);
                if (listConvertCurveToTouchPoints.isEmpty()) {
                    return;
                }
                Log.d(TAG, "processCurveRecognize: Add:" + iIdentityHashCode);
                this.pendingCurveMap.put(Integer.valueOf(iIdentityHashCode), dataCurve);
                Runnable runnable = new Runnable() { // from class: xc.engine.data.pagedata.CurveRecognizeManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m4621xab7646e(iIdentityHashCode, dataCurve, listConvertCurveToTouchPoints);
                    }
                };
                this.pendingRunnables.put(Integer.valueOf(iIdentityHashCode), runnable);
                this.mBackgroundHandler.postDelayed(runnable, 50L);
                return;
            }
            return;
        }
        if ("REMOVE".equals(str)) {
            this.pendingCurveMap.remove(Integer.valueOf(iIdentityHashCode));
            Runnable runnableRemove = this.pendingRunnables.remove(Integer.valueOf(iIdentityHashCode));
            Log.d(TAG, "processCurveRecognize: Remove:" + iIdentityHashCode + ",isHave:" + (runnableRemove != null));
            if (runnableRemove != null) {
                this.mMainHandler.removeCallbacks(runnableRemove);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$processCurveRecognize$1$xc-engine-data-pagedata-CurveRecognizeManager, reason: not valid java name */
    /* synthetic */ void m4621xab7646e(int i, DataCurve dataCurve, List list) {
        if (this.pendingCurveMap.containsKey(Integer.valueOf(i)) && dataCurve.mDataState == 1) {
            try {
                MiCurveEngine curveEngine = this.mPenEngine.getCurveEngine();
                if (curveEngine != null) {
                    curveEngine.getRecognitionModel().setStrokeData(list, i, dataCurve.pageIndex);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to set stroke data", e);
            }
        }
        this.pendingRunnables.remove(Integer.valueOf(i));
    }

    private void ensureOriginPointsGenerated(DataCurve dataCurve) {
        if (dataCurve.size(dataCurve.mOriginPoints) != 0 || dataCurve.size(dataCurve.mSavePoints) <= 0 || this.mPenEngine == null) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new CurveGenTask(dataCurve, this.mPenEngine, countDownLatch).run();
        try {
            countDownLatch.await();
            Log.d(TAG, "mOriginPoints count: " + dataCurve.size(dataCurve.mOriginPoints));
        } catch (InterruptedException e) {
            Log.e(TAG, "mOriginPoints load error: " + e.getMessage());
        }
    }

    private List<TouchPoint> convertCurveToTouchPoints(DataCurve dataCurve) {
        ArrayList arrayList = new ArrayList();
        try {
            synchronized (dataCurve.mLockObj) {
                try {
                    ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                    int size = curvePointList.size();
                    if (size == 0) {
                        Log.e(TAG, "Get mOriginPoints data error,return null");
                        return arrayList;
                    }
                    for (int i = 0; i < size; i++) {
                        TouchPoint touchPoint = new TouchPoint(curvePointList.getX(i), curvePointList.getY(i), curvePointList.getPressure(i), curvePointList.getTime(i), curvePointList.getVelocity(i));
                        try {
                            touchPoint.setOrientation(curvePointList.getOrientation(i));
                            touchPoint.setTilt(curvePointList.getTilt(i));
                        } catch (Exception unused) {
                            touchPoint.setOrientation(0.0f);
                            touchPoint.setTilt(0.0f);
                        }
                        arrayList.add(touchPoint);
                    }
                    Log.d(TAG, "convertCurveToTouchPoints SUCCESSFUL");
                    return arrayList;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "convertCurveToTouchPoints FAIL: " + e.getMessage(), e);
        }
    }

    private CurveRecognizeData buildRecognizeData(DataCurve dataCurve, String str) {
        CurveRecognizeData curveRecognizeData = new CurveRecognizeData();
        curveRecognizeData.curveId = dataCurve.mId;
        curveRecognizeData.action = str;
        curveRecognizeData.timestamp = System.currentTimeMillis();
        try {
            synchronized (dataCurve.mLockObj) {
                ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                int size = curvePointList.size();
                float[] fArr = new float[size];
                float[] fArr2 = new float[size];
                for (int i = 0; i < size; i++) {
                    fArr[i] = curvePointList.getX(i);
                    fArr2[i] = curvePointList.getY(i);
                }
                curveRecognizeData.xPoints = fArr;
                curveRecognizeData.yPoints = fArr2;
                curveRecognizeData.pointCount = size;
                curveRecognizeData.penType = dataCurve.getPenType().getValue();
                curveRecognizeData.penSize = dataCurve.getCurvePenWidth();
                curveRecognizeData.penColor = dataCurve.getCurveColor();
            }
            return curveRecognizeData;
        } catch (Exception e) {
            Log.e(TAG, "buildRecognizeData: " + e.getMessage());
            return curveRecognizeData;
        }
    }

    public void clearStrokeData() {
        this.mMainHandler.post(new Runnable() { // from class: xc.engine.data.pagedata.CurveRecognizeManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4619xf005de70();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clearStrokeData$2$xc-engine-data-pagedata-CurveRecognizeManager, reason: not valid java name */
    /* synthetic */ void m4619xf005de70() {
        Iterator<Integer> it = this.pendingCurveMap.keySet().iterator();
        while (it.hasNext()) {
            clearStroke(it.next().intValue());
        }
        this.pendingCurveMap.clear();
    }

    public void clearStroke(int i) {
        this.strokeIdMap.remove(Integer.valueOf(i));
        this.strokeLiveDataWithKey.setValue(null);
    }

    public void curentCurveProp(DataCurve dataCurve, String str) {
        PenType penType = dataCurve.getPenType();
        dataCurve.getCurveColor();
        this.curveProp = new CurveProp(penType, -32768, dataCurve.getCurvePenWidth(), dataCurve.getAlpha());
    }

    public CurveProp getcurentCurveProp() {
        return this.curveProp;
    }

    public CurveProp getCurvePropByStrokeId(String str) {
        CurveProp curveProp = this.mathBlockCurvePropMap.get(str);
        return curveProp != null ? curveProp : this.curveProp;
    }

    public void destroy() {
        this.mBackgroundHandler.removeCallbacksAndMessages(null);
        this.mMainHandler.removeCallbacksAndMessages(null);
        HandlerThread handlerThread = this.mRecognizeThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mRecognizeThread = null;
        }
        this.pendingCurveMap.clear();
        this.pendingRunnables.clear();
        this.strokeIdMap.clear();
        this.mathBlockCurvePropMap.clear();
        this.curveProp = null;
        this.mPenEngine = null;
    }
}
