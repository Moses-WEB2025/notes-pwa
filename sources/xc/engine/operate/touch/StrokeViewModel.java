package xc.engine.operate.touch;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes6.dex */
public class StrokeViewModel extends ViewModel {
    private static final String TAG = "StrokeViewModel";
    private static volatile StrokeViewModel instance;
    private StrokeIdListener strokeIdListener;
    private final MutableLiveData<Pair<List<TouchPoint>, Integer>> strokeLiveDataWithKey = new MutableLiveData<>();
    private final ConcurrentHashMap<Integer, String[]> strokeIdMap = new ConcurrentHashMap<>();

    public interface StrokeIdListener {
        void onStrokeIdSet(String[] strArr, int i, String str);
    }

    public void setStrokeIdListener(StrokeIdListener strokeIdListener) {
        this.strokeIdListener = strokeIdListener;
    }

    public LiveData<Pair<List<TouchPoint>, Integer>> getStrokeLiveDataWithKey() {
        return this.strokeLiveDataWithKey;
    }

    public void setStrokeData(List<TouchPoint> list, int i) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.strokeLiveDataWithKey.setValue(new Pair<>(list, Integer.valueOf(i)));
    }

    public void setStrokeIds(String[] strArr, int i, String str) {
        if (strArr != null) {
            this.strokeIdMap.put(Integer.valueOf(i), strArr);
        }
        StrokeIdListener strokeIdListener = this.strokeIdListener;
        if (strokeIdListener != null) {
            strokeIdListener.onStrokeIdSet(strArr, i, str);
        }
    }

    public String getStrokeId(int i) {
        String[] strArr = this.strokeIdMap.get(Integer.valueOf(i));
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        return strArr[0];
    }

    public void clearStroke(int i) {
        this.strokeIdMap.remove(Integer.valueOf(i));
        this.strokeLiveDataWithKey.setValue(null);
    }

    public static StrokeViewModel getInstance() {
        if (instance == null) {
            synchronized (StrokeViewModel.class) {
                if (instance == null) {
                    instance = new StrokeViewModel();
                }
            }
        }
        return instance;
    }
}
