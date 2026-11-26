package com.miui.libhandwritingpreview.engine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.libhandwritingpreview.PreAlgorithmImpl;
import com.miui.libhandwritingpreview.fileio.PreInkEntFileIO;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.interfaces.PreICurveDataManager;
import com.miui.libhandwritingpreview.manager.PreCurveDataManager;
import com.miui.libhandwritingpreview.manager.PreDrawerWrapper;
import com.miui.libhandwritingpreview.manager.PrePenEngineHandlerWrapper;
import com.miui.libhandwritingpreview.operator.PreCanvasOperator;
import com.miui.libhandwritingpreview.util.PreDataAccessImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.listener.IErrorListener;
import xc.engine.core.algorithm.IAlgorithmInterface;
import xc.engine.core.algorithm.recognize.RecognizeEngine;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.data.IDataAccess;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.DrawStateListenerManager;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.rule.IGlobalRulerOperator;
import xc.engine.operate.step.IStepManager;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScreenInfo;

/* JADX INFO: loaded from: classes3.dex */
public class PenPreEngine implements PreICreationEngine {
    public static final int DEFAULT_CURVE_COLOR = -16776961;
    public static boolean isEmptyCreationFromCreate = true;
    private boolean isEnable;
    public final PreAlgorithmImpl mAlgorithm;
    public final PreCanvasOperator mCanvasOperator;
    public final Context mContext;
    public long mCurrentTime;
    public final PreCurveDataManager mCurveDataManager;
    private PreMiCurveEngine mCurveEngine;
    public final PreDataAccessImpl mDataAccess;
    public float mDensity;
    public DrawStateListenerManager mDrawStateManager;
    public final PreDrawerWrapper mDrawerWrapper;
    public IErrorListener mErrorListener;
    public long mLastUptime;
    public final PreInkEntFileIO mLoadEntFile;
    public final PrePenEngineHandlerWrapper mPenEngineHandlerWrapper;
    private BroadcastReceiver mReFreshBroadcastReceiver;
    private final RecognizeEngine mRecognizeEngine;
    public final ScreenInfo mScreenInfo;
    public boolean mSinglePage;
    private final Object mToken;
    private final String RECEIVER_ACTION_RENDER = "com.miui.creation.render";
    private final String TAG = "PenPreEngine";
    private int mOperatedModeType = 0;
    public boolean hasBindSurfaceView = false;

    static /* synthetic */ Object lambda$waitForIdle$0() throws Exception {
        return null;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public ISelectOperator getSelectOperator() {
        return null;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public IStepManager getStepManager() {
        return null;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public boolean onHandleEvent(MotionEvent motionEvent, boolean z) {
        return false;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void onSurfaceDestory() {
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void setGlobalRulerOperator(IGlobalRulerOperator iGlobalRulerOperator) {
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public PreMiCurveEngine getCurveEngine() {
        return this.mCurveEngine;
    }

    public static PreICreationEngine createEngine(Context context, Object obj) {
        return new PenPreEngine(context, obj, true, true);
    }

    private PenPreEngine(Context context, Object obj, boolean z, boolean z2) {
        this.mDensity = 1.0f;
        this.mContext = context;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mToken = obj;
        this.mSinglePage = z;
        HandlerThread handlerThread = new HandlerThread(PenEngineUtils.TAG);
        handlerThread.setPriority(10);
        handlerThread.start();
        this.mPenEngineHandlerWrapper = new PrePenEngineHandlerWrapper(this, handlerThread.getLooper());
        this.mScreenInfo = new ScreenInfo(context);
        this.mCurveDataManager = PreICurveDataManager.createInstance(this);
        this.mRecognizeEngine = new RecognizeEngine(context.getApplicationContext());
        this.mAlgorithm = new PreAlgorithmImpl(this);
        this.mLoadEntFile = new PreInkEntFileIO(this);
        this.mDrawerWrapper = new PreDrawerWrapper(this);
        PreCanvasOperator preCanvasOperator = new PreCanvasOperator(this);
        this.mCanvasOperator = preCanvasOperator;
        this.mDataAccess = new PreDataAccessImpl(this);
        this.mDrawStateManager = new DrawStateListenerManager();
        this.mCurveEngine = new PreMiCurveEngine(this);
        this.mReFreshBroadcastReceiver = new BroadcastReceiver() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent == null || intent.getAction() == null || !intent.getAction().equals("com.miui.creation.render")) {
                    return;
                }
                Log.d(PenPreEngine.this.TAG, "onReceive: onCanvasDataChanged");
                if (PenPreEngine.this.mCanvasOperator.mCanvasOperateListener != null) {
                    PenPreEngine.this.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
                }
            }
        };
        this.isEnable = true;
        if (z2) {
            preCanvasOperator.initDrawer();
            preCanvasOperator.setMaxPage(500);
        }
    }

    private void handleRelease() {
        this.mRecognizeEngine.release();
        CreationPageSDK.clearHandler(this.mToken);
        this.mDrawerWrapper.release();
        this.mCurveDataManager.mCurveGenExecutor.mExecutorService.shutdown();
        this.mDataAccess.getDataSet().clear();
        this.mPenEngineHandlerWrapper.getMHandler().getLooper().quitSafely();
        try {
            LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).unregisterReceiver(this.mReFreshBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(this.TAG, "Error unregistering receiver", e);
        }
        this.isEnable = false;
        this.mCanvasOperator.shutdown();
        if (this.mAlgorithm.getOutlineHelper() != null) {
            this.mAlgorithm.getOutlineHelper().cancel();
        }
    }

    public boolean isRelease() {
        return !this.isEnable;
    }

    float getDensity() {
        return this.mDensity;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public PrePenEngineHandlerWrapper getEngineHandler() {
        return this.mPenEngineHandlerWrapper;
    }

    public ScreenInfo getScreenInfo() {
        return this.mScreenInfo;
    }

    public ICurveDataManager getCurveDataManager() {
        return this.mCurveDataManager;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public PreCanvasOperator getCanvasOperator() {
        return this.mCanvasOperator;
    }

    public void setTime(long j) {
        this.mCurrentTime = j;
        this.mLastUptime = SystemClock.uptimeMillis();
    }

    public RecognizeEngine getRecognizeEngine() {
        return this.mRecognizeEngine;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void waitForIdle() {
        if (isRelease()) {
            return;
        }
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda7
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return PenPreEngine.lambda$waitForIdle$0();
            }
        });
        this.mPenEngineHandlerWrapper.sendMessage(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void release() {
        handleRelease();
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public IAlgorithmInterface getAlgorithmInterface() {
        return this.mAlgorithm;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void releaseAsync(final Runnable runnable) {
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m873x8c7894be(runnable);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$releaseAsync$1$com-miui-libhandwritingpreview-engine-PenPreEngine, reason: not valid java name */
    /* synthetic */ void m873x8c7894be(Runnable runnable) {
        handleRelease();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void setIsEmptyCreationFromCreate(boolean z) {
        isEmptyCreationFromCreate = z;
    }

    public static boolean isEmptyCreationFromCreate() {
        return isEmptyCreationFromCreate;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void resetEngine() {
        if (isRelease()) {
            return;
        }
        this.mDrawerWrapper.mDrawer.clearCanvas();
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void resetCache() {
        if (isRelease()) {
            return;
        }
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m874x3b4c21df();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$resetCache$2$com-miui-libhandwritingpreview-engine-PenPreEngine, reason: not valid java name */
    /* synthetic */ void m874x3b4c21df() {
        if (this.mDrawerWrapper.isInitialized) {
            return;
        }
        this.mDrawerWrapper.initDrawer();
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public IDataAccess getDataAccess() {
        return this.mDataAccess;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void moveAllCurve(float f, float f2) {
        List<DataCurve> list = this.mDataAccess.getDataSet().mDataCurves;
        this.mCurveDataManager.removeDataAndGetRectF(list);
        for (DataCurve dataCurve : list) {
            dataCurve.setDrawRatio(this.mCanvasOperator.getDrawRatio());
            dataCurve.doMove(f, f2);
        }
        this.mCurveDataManager.addDataAndGetRectF(list);
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public float[] calcAllCurveBound() {
        float[] fArr = {-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
        Iterator<BaseData> it = this.mDataAccess.getDataSet().mAllData.iterator();
        while (it.hasNext()) {
            it.next().calcBound(fArr);
        }
        return fArr;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void onPageDataLoaded(int i) {
        if (isRelease()) {
            return;
        }
        PreDrawerWrapper preDrawerWrapper = this.mDrawerWrapper;
        if (preDrawerWrapper != null) {
            preDrawerWrapper.onPageDataLoaded();
        }
        Log.d(this.TAG, "onPageDataLoaded: pageIndex:" + i);
        getCurveEngine().onPageDataLoaded(i);
    }

    @Override // com.miui.libhandwritingpreview.interfaces.PreICreationEngine
    public void moveAllCurveAfterPage(final int i, int i2, int i3) {
        Log.i(this.TAG, String.format("moveAllCurveAfterPage: page[%d] curPageOffsetX[%d] lastPageOffsetY[%d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        if (i2 != 0) {
            for (BaseData baseData : this.mDataAccess.mDataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PenPreEngine.lambda$moveAllCurveAfterPage$3((Integer) obj);
                }
            })) {
                ((DataCurve) baseData).setDrawRatio(this.mCanvasOperator.getDrawRatio());
                baseData.doMove(i2, 0.0f);
            }
        }
        List<DataCurve> list = (List) this.mDataAccess.mDataSet.mAllDataMap.entrySet().stream().filter(new Predicate() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PenPreEngine.lambda$moveAllCurveAfterPage$4(i, (Map.Entry) obj);
            }
        }).map(new PenPreEngine$$ExternalSyntheticLambda4()).flatMap(new PenPreEngine$$ExternalSyntheticLambda5()).map(new Function() { // from class: com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PenPreEngine.lambda$moveAllCurveAfterPage$5((BaseData) obj);
            }
        }).collect(Collectors.toList());
        this.mCurveDataManager.removeDataAndGetRectF(list);
        for (DataCurve dataCurve : list) {
            dataCurve.setDrawRatio(this.mCanvasOperator.getDrawRatio());
            dataCurve.doMove(0.0f, i3);
        }
        this.mCurveDataManager.addDataAndGetRectF(list);
        getCurveEngine().getCurveSystem().clearAllTiles();
        getCurveEngine().getCamera().refreshView(true);
    }

    static /* synthetic */ List lambda$moveAllCurveAfterPage$3(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ boolean lambda$moveAllCurveAfterPage$4(int i, Map.Entry entry) {
        return ((Integer) entry.getKey()).intValue() > i;
    }

    static /* synthetic */ DataCurve lambda$moveAllCurveAfterPage$5(BaseData baseData) {
        return (DataCurve) baseData;
    }
}
