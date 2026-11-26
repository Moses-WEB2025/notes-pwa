package xc.engine.core.algorithm.outline;

import android.graphics.Path;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import defpackage.R2;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.algorithm.TouchPoint;
import xc.engine.core.algorithm.bean.OBean;

/* JADX INFO: loaded from: classes6.dex */
public class OutlineHelper {
    private Path mPath = null;
    public final ReentrantLock lock = new ReentrantLock();
    public ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Integer.MAX_VALUE, 12, TimeUnit.SECONDS, new SynchronousQueue());

    private static boolean isDistanceOutSide(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return ((float) Math.sqrt((double) ((f5 * f5) + (f6 * f6)))) >= 2.0f;
    }

    public void cancel() {
        OutlineThreadPool outlineThreadPool = OutlineThreadPool.INSTANCE.POOL;
        while (outlineThreadPool.mExecutor.getQueue().peek() != null) {
            Runnable runnablePoll = outlineThreadPool.mExecutor.getQueue().poll();
            if (runnablePoll instanceof FutureTask) {
                ((FutureTask) runnablePoll).cancel(false);
            } else if (runnablePoll instanceof ContourLineTask) {
                ((ContourLineTask) runnablePoll).isCancel = false;
                runnablePoll.run();
            }
        }
        AlgorithmNative.getInstance().cancelContourLine(true);
    }

    public Path extractContourLine(List<TouchPoint> list, float f) {
        int i = 0;
        AlgorithmNative.getInstance().cancelContourLine(false);
        this.mPath = null;
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList();
        if (size > 6000) {
            int i2 = size / 6000;
            int i3 = size % 6000;
            if (i3 > 0) {
                i2++;
            }
            CountDownLatch countDownLatch = new CountDownLatch(i2);
            int i4 = 0;
            int i5 = 0;
            while (i4 < i2) {
                int i6 = (i4 != i2 + (-1) || i3 == 0) ? i5 + R2.color.theme_shadow_cursor_handle_color : (i5 + i3) - 1;
                if (i5 != i6) {
                    OutlineThreadPool.INSTANCE.POOL.mExecutor.execute(new ContourLineTask(arrayList, list.subList(i5, i6 + 1), f, countDownLatch));
                }
                i4++;
                i5 += 6000;
            }
            try {
                countDownLatch.await();
                if (arrayList.size() > 60000) {
                    return extractContourLine(arrayList, f);
                }
                ArrayList arrayList2 = new ArrayList();
                while (i < arrayList.size()) {
                    if (arrayList.get(i) != null) {
                        arrayList2.add(new MotionPoint(((TouchPoint) arrayList.get(i)).x, ((TouchPoint) arrayList.get(i)).y));
                    }
                    i++;
                }
                FutureTask futureTask = new FutureTask(new ContourLineCallable(arrayList2, f));
                OutlineThreadPool.INSTANCE.POOL.mExecutor.submit(futureTask);
                try {
                    this.mPath = (Path) futureTask.get();
                } catch (Exception e) {
                    arrayList.clear();
                    e.printStackTrace();
                    return this.mPath;
                }
            } catch (InterruptedException e2) {
                arrayList.clear();
                e2.printStackTrace();
                return this.mPath;
            }
        } else {
            ArrayList arrayList3 = new ArrayList();
            while (i < size) {
                arrayList3.add(new MotionPoint(list.get(i).x, list.get(i).y));
                i++;
            }
            this.mPath = convertContourPointToPath(AlgorithmNative.getInstance().getContourLine(arrayList3, f));
        }
        Path path = this.mPath;
        if (path != null) {
            path.close();
        }
        return this.mPath;
    }

    public Path extractHighlight(ArrayList<TouchPoint> arrayList, float f) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        if (size > 12000) {
            int i2 = size / 12000;
            int i3 = size % 12000;
            if (i3 > 0) {
                i2++;
            }
            this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() - 1, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS, new SynchronousQueue());
            int i4 = 0;
            while (i < i2) {
                int i5 = (i != i2 + (-1) || i3 == 0) ? i4 + R2.drawable.pc_edit_folder_background : (i4 + i3) - 1;
                if (i4 != i5) {
                    int size2 = arrayList.subList(i4, i5 + 1).size() + i4;
                    ArrayList arrayList3 = new ArrayList();
                    for (int i6 = i4; i6 < size2; i6++) {
                        arrayList3.add(new MotionPoint(arrayList.get(i6).x, arrayList.get(i6).y));
                    }
                    FutureTask futureTask = new FutureTask(new HighlightLineCallable(this.lock, arrayList3, f));
                    this.executor.execute(futureTask);
                    try {
                        List list = (List) futureTask.get();
                        if (list != null && !list.isEmpty()) {
                            arrayList2.addAll(list);
                        }
                    } catch (Exception unused) {
                    }
                }
                i++;
                i4 += 12000;
            }
        } else {
            while (i < size) {
                arrayList2.add(new MotionPoint(arrayList.get(i).x, arrayList.get(i).y));
                i++;
            }
        }
        try {
            Path pathConvertContourPointToPath = convertContourPointToPath(AlgorithmNative.getInstance().getHighlightLine(arrayList2, f));
            if (pathConvertContourPointToPath != null) {
                return pathConvertContourPointToPath;
            }
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Path convertContourPointToPath(String str) {
        OBean oBean;
        try {
            oBean = (OBean) new Gson().fromJson(str, OBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            oBean = null;
        }
        if (oBean == null) {
            return null;
        }
        String[] strArrSplit = oBean.getResult().split(",");
        Path path = new Path();
        try {
            int length = strArrSplit.length / 2;
            if (oBean.getValid() != 0) {
                if (length > 1) {
                    float f = Float.parseFloat(strArrSplit[0]);
                    float f2 = Float.parseFloat(strArrSplit[1]);
                    path.moveTo(f, f2);
                    for (int i = 1; i < length; i++) {
                        int i2 = i * 2;
                        float f3 = Float.parseFloat(strArrSplit[i2]);
                        float f4 = Float.parseFloat(strArrSplit[i2 + 1]);
                        if (isDistanceOutSide(f, f2, f3, f4)) {
                            path.quadTo(f, f2, (f + f3) / 2.0f, (f2 + f4) / 2.0f);
                            f2 = f4;
                            f = f3;
                        } else if (i == length - 1) {
                            path.quadTo(f, f2, (f3 + f) / 2.0f, (f4 + f2) / 2.0f);
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return path;
    }

    public static List<MotionPoint> convertContourToPoints(String str) {
        OBean oBean;
        try {
            oBean = (OBean) new Gson().fromJson(str, OBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            oBean = null;
        }
        if (oBean == null || oBean.getValid() == 0) {
            return null;
        }
        String[] strArrSplit = oBean.getResult().split(",");
        try {
            int length = strArrSplit.length / 2;
            if (length <= 1) {
                return null;
            }
            ArrayList arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                float f = Float.parseFloat(strArrSplit[i2]);
                float f2 = Float.parseFloat(strArrSplit[i2 + 1]);
                if (f != 0.0f || f2 != 0.0f) {
                    arrayList.add(new MotionPoint(f, f2));
                }
            }
            return arrayList;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static List convertContourPointToList(String str) {
        OBean oBean;
        try {
            oBean = (OBean) new Gson().fromJson(str, OBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            oBean = null;
        }
        if (oBean == null) {
            return null;
        }
        String[] strArrSplit = oBean.getResult().split(",");
        ArrayList arrayList = new ArrayList();
        int length = strArrSplit.length / 2;
        if (oBean.getValid() != 0 && length > 1) {
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                arrayList.add(new TouchPoint(Float.parseFloat(strArrSplit[i2]), Float.parseFloat(strArrSplit[i2 + 1])));
            }
        }
        return arrayList;
    }
}
