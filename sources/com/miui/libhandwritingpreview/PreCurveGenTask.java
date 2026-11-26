package com.miui.libhandwritingpreview;

import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.shape.PreShapeCurveInfo;
import com.miui.libhandwritingpreview.util.PreCurveFittingProcessor;
import java.util.concurrent.CountDownLatch;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: loaded from: classes3.dex */
public class PreCurveGenTask implements Runnable {
    public boolean isScale;
    private final CountDownLatch mCountDownLatch;
    private final DataCurve mDataCurve;
    private final PenPreEngine mPenEngine;

    public PreCurveGenTask(DataCurve dataCurve, PenPreEngine penPreEngine, CountDownLatch countDownLatch) {
        this.mPenEngine = penPreEngine;
        this.mDataCurve = dataCurve;
        this.mCountDownLatch = countDownLatch;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (!isNoNeedProcess()) {
                synchronized (this.mDataCurve.mLockObj) {
                    if (!isNoNeedProcess()) {
                        if (this.mDataCurve.getType() == 28) {
                            new PreShapeCurveInfo(this.mPenEngine, (DataShape) this.mDataCurve, this.isScale, false).optimizeAndTransformShapePath();
                        } else {
                            new PreCurveFittingProcessor(this.mPenEngine, this.mDataCurve, this.isScale).fillCurvePoints();
                        }
                    }
                }
            }
            this.mCountDownLatch.countDown();
        } catch (Throwable th) {
            this.mCountDownLatch.countDown();
            throw th;
        }
    }

    private boolean isNoNeedProcess() {
        if (this.isScale) {
            return false;
        }
        if (this.mDataCurve.getType() == 1) {
            DataCurve dataCurve = this.mDataCurve;
            if (dataCurve.size(dataCurve.mOriginPoints) == 0) {
                return false;
            }
        }
        return !(this.mDataCurve.getType() == 28 && ((DataShape) this.mDataCurve).mShapePath.isEmpty()) && this.mPenEngine.mCanvasOperator.getDrawRatio() == this.mDataCurve.mDrawRatio;
    }
}
