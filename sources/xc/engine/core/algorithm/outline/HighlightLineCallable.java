package xc.engine.core.algorithm.outline;

import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.core.algorithm.AlgorithmNative;

/* JADX INFO: loaded from: classes6.dex */
public class HighlightLineCallable implements Callable<List<MotionPoint>> {
    private final float curveWidth;
    public final ReentrantLock lock;
    private List<MotionPoint> points;

    public HighlightLineCallable(ReentrantLock reentrantLock, List<MotionPoint> list, float f) {
        ArrayList arrayList = new ArrayList();
        this.points = arrayList;
        this.lock = reentrantLock;
        arrayList.addAll(list);
        this.curveWidth = f;
    }

    @Override // java.util.concurrent.Callable
    public List<MotionPoint> call() {
        this.lock.lock();
        List<MotionPoint> listConvertContourToPoints = OutlineHelper.convertContourToPoints(AlgorithmNative.getInstance().getHighlightLine(this.points, this.curveWidth));
        this.lock.unlock();
        return listConvertContourToPoints;
    }
}
