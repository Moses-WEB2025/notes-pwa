package xc.engine.operate.canvas.draw.helper;

import android.util.SparseArray;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes6.dex */
public class RemoveBitmapPool {
    public SparseArray<RemovedCache> removedCaches = new SparseArray<>();

    public static class RemovedCache {
        public final ArrayList<BitmapIndex> cachedBitmapIndexList = new ArrayList<>();
        public int maxSize = 6;
    }
}
