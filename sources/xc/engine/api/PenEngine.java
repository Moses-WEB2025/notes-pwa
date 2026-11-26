package xc.engine.api;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda4;
import com.miui.libhandwritingpreview.engine.PenPreEngine$$ExternalSyntheticLambda5;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import xc.engine.api.listener.IEngineErrorListener;
import xc.engine.api.listener.IErrorListener;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.algorithm.AlgorithmImpl;
import xc.engine.core.algorithm.IAlgorithmInterface;
import xc.engine.core.algorithm.recognize.RecognizeEngine;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.GlLowLatencyRenderProcessor;
import xc.engine.core.fast.draw.FrontBufferedMultiDrawer;
import xc.engine.core.graphics.curve.CurveDataListManager;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.DataAccessImpl;
import xc.engine.data.IDataAccess;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.CurveRecognizeManager;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.DrawStateListenerManager;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.DrawerWrapper;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.canvas.track.delete.DeleteType;
import xc.engine.operate.edit.EditOperator;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.edit.select.SelectOperator;
import xc.engine.operate.rule.GlobalRulerOperator;
import xc.engine.operate.rule.IGlobalRulerOperator;
import xc.engine.operate.rule.RulerOperator;
import xc.engine.operate.step.IStepManager;
import xc.engine.operate.touch.ITouchOperator;
import xc.engine.operate.touch.TouchEventOperator;
import xc.engine.storage.ent.file_io.InkEntFileIO;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.PressureUtil;
import xc.engine.utils.ScreenInfo;

/* JADX INFO: loaded from: classes6.dex */
public class PenEngine implements ICreationEngine, IErrorListener {
    public static final int DEFAULT_CURVE_COLOR = -16776961;
    public static boolean isEmptyCreationFromCreate = true;
    private boolean isEnable;
    public final AlgorithmImpl mAlgorithm;
    public final CanvasOperator mCanvasOperator;
    public final Context mContext;
    public long mCurrentTime;
    private final CurveDataListManager mCurveDataListManager;
    public final ICurveDataManager mCurveDataManager;
    public CurveRecognizeManager mCurveRecognizeManager;
    public final DataAccessImpl mDataAccess;
    public float mDensity;
    public DrawStateListenerManager mDrawStateManager;
    public final DrawerWrapper mDrawerWrapper;
    public final EditOperator mEditOperator;
    private IEngineErrorListener mEngineErrorListener;
    public IErrorListener mErrorListener;
    public FrontBufferedMultiDrawer mFrontBufferedDrawer;
    public GlLowLatencyRenderProcessor mFrontBufferedRenderProcessor;
    public GlobalRulerOperator mGlobalRulerOperator;
    public long mLastUptime;
    public final InkEntFileIO mLoadEntFile;
    public final PenEngineHandlerWrapper mPenEngineHandlerWrapper;
    private final RecognizeEngine mRecognizeEngine;
    public final ScreenInfo mScreenInfo;
    private SelectOperator mSelectOperator;
    public boolean mSinglePage;
    public final IStepManager mStepManager;
    private final Object mToken;
    public final TouchEventOperator mTouchEventOperator;
    private final String TAG = "PenEngine";
    private DeleteProp mDeleteProp = new DeleteProp(DeleteType.POINT, 6.0f);
    private int mOperatedModeType = 0;
    public boolean hasBindSurfaceView = false;
    public boolean isSurfaceView = false;
    public boolean isSelectingMoving = false;

    static /* synthetic */ Object lambda$waitForIdle$0() throws Exception {
        return null;
    }

    @Override // xc.engine.api.ICreationEngine
    public void onSurfaceChanged(int i, int i2, int i3) {
    }

    @Override // xc.engine.api.ICreationEngine
    public void onSurfaceDestory() {
    }

    @Override // xc.engine.api.ICreationEngine
    public MiCurveEngine getCurveEngine() {
        return this.mFrontBufferedDrawer.getCurveEngine();
    }

    @Override // xc.engine.api.ICreationEngine
    public boolean hoverEvent(MotionEvent motionEvent, boolean z, float f) {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "stylus_brush_preview_enable", 1) == 1 && this.mOperatedModeType == 0) {
            return this.mTouchEventOperator.hoverEvent(motionEvent, z, f);
        }
        return false;
    }

    public static ICreationEngine createEngine(Context context, Object obj, IEngineErrorListener iEngineErrorListener) {
        return new PenEngine(context, obj, true, true, iEngineErrorListener, true);
    }

    private PenEngine(Context context, Object obj, boolean z, boolean z2, IEngineErrorListener iEngineErrorListener, boolean z3) {
        FrontBufferedMultiDrawer frontBufferedMultiDrawer;
        this.mContext = context;
        this.mToken = obj;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mSinglePage = z;
        HandlerThread handlerThread = new HandlerThread(PenEngineUtils.TAG);
        handlerThread.setPriority(10);
        handlerThread.start();
        this.mPenEngineHandlerWrapper = new PenEngineHandlerWrapper(this, handlerThread.getLooper());
        this.mScreenInfo = new ScreenInfo(context);
        this.mCurveDataListManager = new CurveDataListManager(this);
        ICurveDataManager iCurveDataManagerCreateInstance = ICurveDataManager.createInstance(this);
        this.mCurveDataManager = iCurveDataManagerCreateInstance;
        this.mRecognizeEngine = new RecognizeEngine(context.getApplicationContext());
        this.mLoadEntFile = new InkEntFileIO(this);
        this.mDrawerWrapper = new DrawerWrapper(this);
        CanvasOperator canvasOperator = new CanvasOperator(this);
        this.mCanvasOperator = canvasOperator;
        EditOperator editOperator = new EditOperator(this);
        this.mEditOperator = editOperator;
        TouchEventOperator touchEventOperator = new TouchEventOperator(this);
        this.mTouchEventOperator = touchEventOperator;
        this.mDataAccess = new DataAccessImpl(this);
        this.mStepManager = IStepManager.createInstance(this);
        this.mAlgorithm = new AlgorithmImpl(this);
        this.mDrawStateManager = new DrawStateListenerManager();
        if (z3 && FeatureManager.getInstance().frontBufferEnable()) {
            this.mFrontBufferedRenderProcessor = new GlLowLatencyRenderProcessor();
            this.mFrontBufferedDrawer = new FrontBufferedMultiDrawer(this);
        }
        this.isEnable = true;
        if (z2) {
            this.mErrorListener = this;
            this.mEngineErrorListener = iEngineErrorListener;
            this.mSelectOperator = new SelectOperator(this);
            canvasOperator.initDrawer();
            canvasOperator.setMaxPage(500);
            touchEventOperator.setOperatorMode(2);
            touchEventOperator.setPenProp(new CurveProp(PenType.BALL, -16776961, 6.0f, 255));
            editOperator.setEditListener(this.mSelectOperator);
            CurveRecognizeManager curveRecognizeManager = new CurveRecognizeManager(this);
            this.mCurveRecognizeManager = curveRecognizeManager;
            iCurveDataManagerCreateInstance.addCurveChangeListener(curveRecognizeManager);
            GlLowLatencyRenderProcessor glLowLatencyRenderProcessor = this.mFrontBufferedRenderProcessor;
            if (glLowLatencyRenderProcessor == null || (frontBufferedMultiDrawer = this.mFrontBufferedDrawer) == null) {
                return;
            }
            glLowLatencyRenderProcessor.updateFrontLayerDrawer(frontBufferedMultiDrawer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleReleaseAsync, reason: merged with bridge method [inline-methods] */
    public void m4597lambda$releaseAsync$1$xcengineapiPenEngine(Runnable runnable) {
        handleRelease();
        if (runnable != null) {
            runnable.run();
        }
    }

    private void handleRelease() {
        Log.d(this.TAG, "handleRelease: ");
        this.mRecognizeEngine.release();
        this.mTouchEventOperator.mTouchPointEstimator.mVelocityTracker.recycle();
        CreationPageSDK.clearHandler(this.mToken);
        this.mDrawerWrapper.release();
        ExecutorService executorService = ((CurveDataManager) this.mCurveDataManager).mCurveGenExecutor.mExecutorService;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2L, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException unused) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        this.mDataAccess.getDataSet().clear();
        this.mStepManager.clearStep();
        this.mPenEngineHandlerWrapper.mHandler.getLooper().quitSafely();
        if (this.isSurfaceView) {
            GlLowLatencyRenderProcessor glLowLatencyRenderProcessor = this.mFrontBufferedRenderProcessor;
            if (glLowLatencyRenderProcessor != null) {
                glLowLatencyRenderProcessor.release();
            }
        } else {
            FrontBufferedMultiDrawer frontBufferedMultiDrawer = this.mFrontBufferedDrawer;
            if (frontBufferedMultiDrawer != null) {
                frontBufferedMultiDrawer.onDestroy();
            }
        }
        if (this.mAlgorithm.getOutlineHelper() != null) {
            this.mAlgorithm.getOutlineHelper().cancel();
        }
        CurveRecognizeManager curveRecognizeManager = this.mCurveRecognizeManager;
        if (curveRecognizeManager != null) {
            this.mCurveDataManager.removeCurveChangeListener(curveRecognizeManager);
            this.mCurveRecognizeManager.destroy();
        }
        this.isEnable = false;
        EditOperator editOperator = this.mEditOperator;
        if (editOperator != null && editOperator.mCopyData != null) {
            this.mEditOperator.mCopyData.g();
        }
        this.mCanvasOperator.shutdown();
        this.mSelectOperator = null;
        EditOperator editOperator2 = this.mEditOperator;
        if (editOperator2 != null) {
            editOperator2.mEditListener = null;
        }
        Log.d(this.TAG, "handleRelease end, nativeCurvePoolSize:" + MiCurveNative.getNativeCurvePoolSize());
    }

    public boolean isRelease() {
        return !this.isEnable;
    }

    float getDensity() {
        return this.mDensity;
    }

    public PenEngineHandlerWrapper getEngineHandler() {
        return this.mPenEngineHandlerWrapper;
    }

    public ScreenInfo getScreenInfo() {
        return this.mScreenInfo;
    }

    public ICurveDataManager getCurveDataManager() {
        return this.mCurveDataManager;
    }

    public CurveDataListManager getCurveDataListManager() {
        return this.mCurveDataListManager;
    }

    @Override // xc.engine.api.ICreationEngine
    public CanvasOperator getCanvasOperator() {
        return this.mCanvasOperator;
    }

    @Override // xc.engine.api.ICreationEngine
    public EditOperator getEditOperator() {
        return this.mEditOperator;
    }

    public DrawerWrapper getDrawerWrapper() {
        return this.mDrawerWrapper;
    }

    public void setTime(long j) {
        this.mCurrentTime = j;
        this.mLastUptime = SystemClock.uptimeMillis();
    }

    public RecognizeEngine getRecognizeEngine() {
        return this.mRecognizeEngine;
    }

    @Override // xc.engine.api.ICreationEngine
    public void waitForIdle() {
        if (isRelease()) {
            return;
        }
        FutureTask futureTask = new FutureTask(new Callable() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return PenEngine.lambda$waitForIdle$0();
            }
        });
        this.mPenEngineHandlerWrapper.sendMessage(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    @Override // xc.engine.api.ICreationEngine
    public void release() {
        handleRelease();
    }

    @Override // xc.engine.api.ICreationEngine
    public void releaseAsync(final Runnable runnable) {
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4597lambda$releaseAsync$1$xcengineapiPenEngine(runnable);
            }
        });
    }

    @Override // xc.engine.api.ICreationEngine
    public IAlgorithmInterface getAlgorithmInterface() {
        return this.mAlgorithm;
    }

    @Override // xc.engine.api.ICreationEngine
    public ITouchOperator getTouchOperator() {
        return this.mTouchEventOperator;
    }

    @Override // xc.engine.api.ICreationEngine
    public ISelectOperator getSelectOperator() {
        return this.mSelectOperator;
    }

    @Override // xc.engine.api.ICreationEngine
    public void setGlobalRulerOperator(IGlobalRulerOperator iGlobalRulerOperator) {
        GlobalRulerOperator globalRulerOperator = (GlobalRulerOperator) iGlobalRulerOperator;
        this.mGlobalRulerOperator = globalRulerOperator;
        if (globalRulerOperator != null) {
            if (globalRulerOperator.mRulerOperator == null) {
                this.mGlobalRulerOperator.mRulerOperator = new RulerOperator(this);
                return;
            }
            this.mGlobalRulerOperator.mRulerOperator.mPenEngine = this;
            if (this.mGlobalRulerOperator.mRulerOperator.mRuler == null) {
                this.mGlobalRulerOperator.mRulerOperator.enableRuler(this.mGlobalRulerOperator.mEnable);
            } else {
                this.mGlobalRulerOperator.mRulerOperator.mRuler.mPenEngine = this;
            }
            if (this.mGlobalRulerOperator.mRulerOperator.mRuler != null) {
                this.mGlobalRulerOperator.mRulerOperator.mRuler.mGlobalRulerListener = this.mGlobalRulerOperator.mGlobalRulerListener;
            }
        }
    }

    public static void setIsEmptyCreationFromCreate(boolean z) {
        isEmptyCreationFromCreate = z;
    }

    public static boolean isEmptyCreationFromCreate() {
        return isEmptyCreationFromCreate;
    }

    @Override // xc.engine.api.ICreationEngine
    public void resetEngine() {
        this.mEditOperator.clearRunnable();
        if (isRelease()) {
            return;
        }
        this.mDrawerWrapper.mDrawer.clearCanvas();
    }

    @Override // xc.engine.api.ICreationEngine
    public void resetCache() {
        if (isRelease()) {
            return;
        }
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4598lambda$resetCache$2$xcengineapiPenEngine();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$resetCache$2$xc-engine-api-PenEngine, reason: not valid java name */
    /* synthetic */ void m4598lambda$resetCache$2$xcengineapiPenEngine() {
        if (this.mDrawerWrapper.isInitialized) {
            return;
        }
        this.mDrawerWrapper.initDrawer();
    }

    @Override // xc.engine.api.ICreationEngine
    public IDataAccess getDataAccess() {
        return this.mDataAccess;
    }

    @Override // xc.engine.api.ICreationEngine
    public void setOperatedModeType(int i) {
        this.mOperatedModeType = i;
    }

    @Override // xc.engine.api.ICreationEngine
    public boolean onHandleEvent(MotionEvent motionEvent) {
        return onHandleEvent(motionEvent, true);
    }

    @Override // xc.engine.api.ICreationEngine
    public boolean onHandleEvent(MotionEvent motionEvent, boolean z) {
        TouchEventOperator touchEventOperator;
        int i = this.mOperatedModeType;
        if (i == 1) {
            return this.mSelectOperator.onTouchEvent(motionEvent, z);
        }
        if (i != 0 || (touchEventOperator = this.mTouchEventOperator) == null) {
            return false;
        }
        if (touchEventOperator.getOperatorMode() == 1) {
            setEraserProp(motionEvent);
        }
        GlobalRulerOperator globalRulerOperator = this.mGlobalRulerOperator;
        if (globalRulerOperator != null && globalRulerOperator.isEnable() && this.mGlobalRulerOperator.onTouchEvent(motionEvent)) {
            return true;
        }
        return this.mTouchEventOperator.onHandleEvent(motionEvent);
    }

    @Override // xc.engine.api.listener.IErrorListener
    public void onError(Exception exc) {
        if (exc == null) {
            return;
        }
        String message = exc.getMessage();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (message.contains(CreationPageSDK.PAGE_POINT_LIMIT)) {
            this.mEngineErrorListener.onError(0, message);
        } else if (message.contains(CreationPageSDK.ALL_POINT_LIMIT)) {
            this.mEngineErrorListener.onError(1, message);
        }
    }

    @Override // xc.engine.api.listener.IErrorListener
    public void onSuccess() {
        this.mEngineErrorListener.onSuccess();
    }

    private void setEraserProp(MotionEvent motionEvent) {
        DeleteProp deleteProp = new DeleteProp(this.mDeleteProp.getDeleteType(), this.mDeleteProp.getDeleteRadius());
        deleteProp.setDeleteRadius(PressureUtil.getEraserSizeByPressure(deleteProp.getDeleteRadius(), motionEvent.getPressure(0)));
        this.mTouchEventOperator.setDeleteProp(deleteProp);
    }

    @Override // xc.engine.api.ICreationEngine
    public void setDeleteProp(DeleteProp deleteProp) {
        this.mDeleteProp = deleteProp;
        this.mTouchEventOperator.setDeleteProp(deleteProp);
        this.mFrontBufferedDrawer.setDeleteProp(deleteProp);
    }

    @Override // xc.engine.api.ICreationEngine
    public IStepManager getStepManager() {
        return this.mStepManager;
    }

    @Override // xc.engine.api.ICreationEngine
    public void moveAllCurve(float f, float f2) {
        List<DataCurve> list = this.mDataAccess.getDataSet().mDataCurves;
        this.mCurveDataManager.removeDataAndGetRectF(list);
        for (DataCurve dataCurve : list) {
            dataCurve.setDrawRatio(this.mCanvasOperator.getDrawRatio());
            dataCurve.doMove(f, f2);
        }
        this.mCurveDataManager.addDataAndGetRectF(list);
    }

    @Override // xc.engine.api.ICreationEngine
    public float[] calcAllCurveBound() {
        float[] fArr = {-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
        Iterator<BaseData> it = this.mDataAccess.getDataSet().mAllData.iterator();
        while (it.hasNext()) {
            it.next().calcBound(fArr);
        }
        return fArr;
    }

    @Override // xc.engine.api.ICreationEngine
    public void setSurfaceView(SurfaceView surfaceView) {
        GlLowLatencyRenderProcessor glLowLatencyRenderProcessor = this.mFrontBufferedRenderProcessor;
        if (glLowLatencyRenderProcessor != null) {
            glLowLatencyRenderProcessor.initialize(surfaceView);
        }
    }

    @Override // xc.engine.api.ICreationEngine
    public GLFrontBufferedWrapperRenderer getFrontBufferedRenderer() {
        GlLowLatencyRenderProcessor glLowLatencyRenderProcessor = this.mFrontBufferedRenderProcessor;
        if (glLowLatencyRenderProcessor != null) {
            return glLowLatencyRenderProcessor.getMFrontBufferRenderer();
        }
        return null;
    }

    @Override // xc.engine.api.ICreationEngine
    public FrontBufferedMultiDrawer getFrontBufferedDrawer() {
        return this.mFrontBufferedDrawer;
    }

    @Override // xc.engine.api.ICreationEngine
    public void setSelecting(boolean z) {
        this.isSelectingMoving = z;
    }

    @Override // xc.engine.api.ICreationEngine
    public boolean getSelectingState() {
        return this.isSelectingMoving;
    }

    @Override // xc.engine.api.ICreationEngine
    public void onPageDataLoaded(int i) {
        if (isRelease()) {
            return;
        }
        DrawerWrapper drawerWrapper = this.mDrawerWrapper;
        if (drawerWrapper != null) {
            drawerWrapper.onPageDataLoaded();
        }
        getCurveEngine().onPageDataLoaded(i);
    }

    @Override // xc.engine.api.ICreationEngine
    public void hasBindSurfaceViewState(boolean z) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.hasBindSurfaceView = z;
        }
    }

    @Override // xc.engine.api.ICreationEngine
    public void isSurfaceView(boolean z) {
        this.isSurfaceView = z;
    }

    @Override // xc.engine.api.ICreationEngine
    public void deletePage(final int i) {
        this.mEditOperator.deletePage(i);
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4596lambda$deletePage$5$xcengineapiPenEngine(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$deletePage$5$xc-engine-api-PenEngine, reason: not valid java name */
    /* synthetic */ void m4596lambda$deletePage$5$xcengineapiPenEngine(final int i) {
        List<DataCurve> list = (List) this.mDataAccess.mDataSet.mAllDataMap.entrySet().stream().filter(new Predicate() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PenEngine.lambda$deletePage$3(i, (Map.Entry) obj);
            }
        }).map(new PenPreEngine$$ExternalSyntheticLambda4()).flatMap(new PenPreEngine$$ExternalSyntheticLambda5()).map(new Function() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PenEngine.lambda$deletePage$4((BaseData) obj);
            }
        }).collect(Collectors.toList());
        this.mCurveDataManager.removeDataAndGetRectF(list);
        if (this.mDataAccess.deletePageAndHaveShiftIndex(i)) {
            this.mDataAccess.shiftPage(i, getCurveEngine().getPageSystem().getPageData(i).getOriginXInWS(), getCurveEngine().getPageSystem().getPageData(i).getOriginYInWS());
        }
        this.mCurveDataManager.addDataAndGetRectF(list);
        getCurveEngine().getPageSystem().deletePage(i);
    }

    static /* synthetic */ boolean lambda$deletePage$3(int i, Map.Entry entry) {
        return ((Integer) entry.getKey()).intValue() > i;
    }

    static /* synthetic */ DataCurve lambda$deletePage$4(BaseData baseData) {
        return (DataCurve) baseData;
    }

    @Override // xc.engine.api.ICreationEngine
    public void moveAllCurveAfterPage(final int i, int i2, int i3) {
        Log.i(this.TAG, String.format("moveAllCurveAfterPage: page[%d] curPageOffsetX[%d] lastPageOffsetY[%d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        if (i2 != 0) {
            for (BaseData baseData : this.mDataAccess.mDataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PenEngine.lambda$moveAllCurveAfterPage$6((Integer) obj);
                }
            })) {
                ((DataCurve) baseData).setDrawRatio(this.mCanvasOperator.getDrawRatio());
                baseData.doMove(i2, 0.0f);
            }
        }
        List<DataCurve> list = (List) this.mDataAccess.mDataSet.mAllDataMap.entrySet().stream().filter(new Predicate() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PenEngine.lambda$moveAllCurveAfterPage$7(i, (Map.Entry) obj);
            }
        }).map(new PenPreEngine$$ExternalSyntheticLambda4()).flatMap(new PenPreEngine$$ExternalSyntheticLambda5()).map(new Function() { // from class: xc.engine.api.PenEngine$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PenEngine.lambda$moveAllCurveAfterPage$8((BaseData) obj);
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

    static /* synthetic */ List lambda$moveAllCurveAfterPage$6(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ boolean lambda$moveAllCurveAfterPage$7(int i, Map.Entry entry) {
        return ((Integer) entry.getKey()).intValue() > i;
    }

    static /* synthetic */ DataCurve lambda$moveAllCurveAfterPage$8(BaseData baseData) {
        return (DataCurve) baseData;
    }

    private boolean updateBaseDataPageIndex(int i, int i2, PointF pointF) {
        int pageIndexByWorldSpacePoint = getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(pointF);
        Log.i(this.TAG, String.format("updateBaseDataPageIndex: mid[%d] old[%d] new[%d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(pageIndexByWorldSpacePoint)));
        if (pageIndexByWorldSpacePoint == i2) {
            return false;
        }
        return getDataAccess().updateCurveDataPageIndex(i, i2, pageIndexByWorldSpacePoint);
    }

    private PointF getBasePoint(DataCurve dataCurve) {
        float drawRatio = this.mCanvasOperator.getDrawRatio();
        if (dataCurve.getType() != 1) {
            RectF saveRect = dataCurve.getSaveRect();
            return new PointF(saveRect.left * drawRatio, saveRect.top * drawRatio);
        }
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        if (curvePointList.size() == 0) {
            return new PointF();
        }
        return new PointF(curvePointList.getX(0) * drawRatio, curvePointList.getY(0) * drawRatio);
    }

    public void updateDataCurvePageIndex(List<DataCurve> list) {
        HashSet hashSet = new HashSet();
        for (DataCurve dataCurve : list) {
            int i = dataCurve.pageIndex;
            if (updateBaseDataPageIndex(dataCurve.mId, i, getBasePoint(dataCurve))) {
                hashSet.add(Integer.valueOf(i));
                hashSet.add(Integer.valueOf(dataCurve.pageIndex));
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.mEditOperator.mEditListener.onStepChanged(9, ((Integer) it.next()).intValue());
        }
    }
}
