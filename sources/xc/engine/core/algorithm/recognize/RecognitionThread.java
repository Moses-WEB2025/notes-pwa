package xc.engine.core.algorithm.recognize;

import android.content.Context;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import com.xiaomi.android.bsp.ui.pen.algorithm.recognizelib.sdk.RecognizeListener;
import java.util.ArrayList;
import java.util.List;
import xc.engine.core.algorithm.AlgorithmNative;

/* JADX INFO: loaded from: classes6.dex */
public class RecognitionThread extends Thread {
    private final String TAG;
    private volatile boolean isLoad;

    public RecognitionThread(Runnable runnable, Context context) {
        super(runnable);
        this.TAG = getClass().getSimpleName();
        this.isLoad = false;
        AlgorithmNative.getInstance().setContent(context);
        createEditor();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
    }

    public void createEditor() {
        AlgorithmNative.getInstance().setRecognizeEngineListener(Thread.currentThread().getId(), new RecognizeListener() { // from class: xc.engine.core.algorithm.recognize.RecognitionThread.1
            @Override // com.xiaomi.android.bsp.ui.pen.algorithm.recognizelib.sdk.RecognizeListener
            public void onSuccess(long j) {
                RecognitionThread.this.isLoad = true;
            }

            @Override // com.xiaomi.android.bsp.ui.pen.algorithm.recognizelib.sdk.RecognizeListener
            public void onError(long j, int i) {
                RecognitionThread.this.isLoad = false;
            }
        });
    }

    public String syncRecognize(ArrayList<RecognizePoint> arrayList) {
        long id = Thread.currentThread().getId();
        if (!this.isLoad) {
            return null;
        }
        AlgorithmNative.getInstance().openEngine(id);
        addPoints(arrayList, id);
        AlgorithmNative.getInstance().recognizePage(id);
        AlgorithmNative.getInstance().awaitIdle(id);
        String recognizeContent = AlgorithmNative.getInstance().getRecognizeContent(id);
        AlgorithmNative.getInstance().clearRecognizeContent(id);
        AlgorithmNative.getInstance().closeEngine(id);
        return recognizeContent;
    }

    public int[] addPoints(List<RecognizePoint> list, long j) {
        int size = list.size();
        if (size <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            arrayList.add(new MotionPoint(list.get(i).x, list.get(i).y, list.get(i).t, list.get(i).action));
        }
        return AlgorithmNative.getInstance().addRecognizePoints(j, arrayList);
    }
}
