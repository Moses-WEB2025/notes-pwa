package com.miui.libhandwritingpreview.interfaces;

import android.view.MotionEvent;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.manager.PrePenEngineHandlerWrapper;
import xc.engine.core.algorithm.IAlgorithmInterface;
import xc.engine.data.IDataAccess;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.rule.IGlobalRulerOperator;
import xc.engine.operate.step.IStepManager;

/* JADX INFO: loaded from: classes3.dex */
public interface PreICreationEngine {
    float[] calcAllCurveBound();

    IAlgorithmInterface getAlgorithmInterface();

    ICanvasOperator getCanvasOperator();

    PreMiCurveEngine getCurveEngine();

    IDataAccess getDataAccess();

    PrePenEngineHandlerWrapper getEngineHandler();

    ISelectOperator getSelectOperator();

    IStepManager getStepManager();

    void moveAllCurve(float f, float f2);

    void moveAllCurveAfterPage(int i, int i2, int i3);

    boolean onHandleEvent(MotionEvent motionEvent, boolean z);

    void onPageDataLoaded(int i);

    void onSurfaceDestory();

    void release();

    void releaseAsync(Runnable runnable);

    void resetCache();

    void resetEngine();

    void setGlobalRulerOperator(IGlobalRulerOperator iGlobalRulerOperator);

    void waitForIdle();
}
