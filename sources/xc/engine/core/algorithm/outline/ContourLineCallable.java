package xc.engine.core.algorithm.outline;

import android.graphics.Path;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import xc.engine.core.algorithm.AlgorithmNative;

/* JADX INFO: loaded from: classes6.dex */
public class ContourLineCallable implements Callable<Path> {
    public final float interval;
    private List<MotionPoint> points;

    public ContourLineCallable(List<MotionPoint> list, float f) {
        ArrayList arrayList = new ArrayList();
        this.points = arrayList;
        arrayList.addAll(list);
        this.interval = f;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public Path call() {
        try {
            return OutlineHelper.convertContourPointToPath(AlgorithmNative.getInstance().getContourLine(this.points, this.interval));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
