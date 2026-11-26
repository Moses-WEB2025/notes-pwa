package xc.engine.core.graphics.curve;

import java.util.concurrent.CountDownLatch;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;

/* JADX INFO: loaded from: classes6.dex */
public class CurveGenTask implements Runnable {
    public boolean isScale;
    private final CountDownLatch mCountDownLatch;
    private final DataCurve mDataCurve;
    private final PenEngine mPenEngine;

    public CurveGenTask(DataCurve dataCurve, PenEngine penEngine, CountDownLatch countDownLatch) {
        this.mPenEngine = penEngine;
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
                            new ShapeCurveInfo(this.mPenEngine, (DataShape) this.mDataCurve, this.isScale, false).optimizeAndTransformShapePath();
                        } else {
                            new CurveFittingProcessor(this.mPenEngine, this.mDataCurve, this.isScale).fillCurvePoints();
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
