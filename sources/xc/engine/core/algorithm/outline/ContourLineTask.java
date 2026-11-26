package xc.engine.core.algorithm.outline;

import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.algorithm.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public class ContourLineTask implements Runnable {
    public float interval;
    public boolean isCancel = true;
    public CountDownLatch latch;
    public List<TouchPoint> points;
    public List<TouchPoint> subList;

    public ContourLineTask(List<TouchPoint> list, List<TouchPoint> list2, float f, CountDownLatch countDownLatch) {
        this.points = list;
        this.subList = list2;
        this.interval = f;
        this.latch = countDownLatch;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                if (this.isCancel) {
                    int size = this.subList.size();
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < size; i++) {
                        arrayList.add(new MotionPoint(this.subList.get(i).x, this.subList.get(i).y));
                    }
                    this.points.addAll(OutlineHelper.convertContourPointToList(AlgorithmNative.getInstance().getContourLine(arrayList, this.interval)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            this.latch.countDown();
        }
    }
}
