package xc.engine.api;

import android.view.MotionEvent;
import android.view.SurfaceView;
import xc.engine.core.algorithm.IAlgorithmInterface;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.draw.FrontBufferedMultiDrawer;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.IDataAccess;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.edit.IEditOperator;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.rule.IGlobalRulerOperator;
import xc.engine.operate.step.IStepManager;
import xc.engine.operate.touch.ITouchOperator;

/* JADX INFO: loaded from: classes6.dex */
public interface ICreationEngine {
    float[] calcAllCurveBound();

    void deletePage(int i);

    IAlgorithmInterface getAlgorithmInterface();

    ICanvasOperator getCanvasOperator();

    MiCurveEngine getCurveEngine();

    IDataAccess getDataAccess();

    IEditOperator getEditOperator();

    FrontBufferedMultiDrawer getFrontBufferedDrawer();

    GLFrontBufferedWrapperRenderer getFrontBufferedRenderer();

    ISelectOperator getSelectOperator();

    boolean getSelectingState();

    IStepManager getStepManager();

    ITouchOperator getTouchOperator();

    void hasBindSurfaceViewState(boolean z);

    boolean hoverEvent(MotionEvent motionEvent, boolean z, float f);

    void isSurfaceView(boolean z);

    void moveAllCurve(float f, float f2);

    void moveAllCurveAfterPage(int i, int i2, int i3);

    boolean onHandleEvent(MotionEvent motionEvent);

    boolean onHandleEvent(MotionEvent motionEvent, boolean z);

    void onPageDataLoaded(int i);

    void onSurfaceChanged(int i, int i2, int i3);

    void onSurfaceDestory();

    void release();

    void releaseAsync(Runnable runnable);

    void resetCache();

    void resetEngine();

    void setDeleteProp(DeleteProp deleteProp);

    void setGlobalRulerOperator(IGlobalRulerOperator iGlobalRulerOperator);

    void setOperatedModeType(int i);

    void setSelecting(boolean z);

    void setSurfaceView(SurfaceView surfaceView);

    void waitForIdle();
}
