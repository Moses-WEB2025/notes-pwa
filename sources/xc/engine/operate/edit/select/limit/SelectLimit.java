package xc.engine.operate.edit.select.limit;

import java.util.HashMap;

/* JADX INFO: loaded from: classes6.dex */
public class SelectLimit {
    public static final int LIMIT_MODE_DEFAULT_PEN_SIZE = 0;
    public static final int LIMIT_MODE_PEN_SIZE = 1;
    public static final int TYPE_BALL = 2;
    public static final int TYPE_INK = 1;
    public static final int TYPE_MARK = 4;
    public static final int TYPE_PENCIL = 3;
    public static final int TYPE_SHAPE = 6;
    public static final int TYPE_WATER_COLOR = 5;
    private final HashMap<Integer, PenSize> mPenSizeMap = new HashMap<>();
    private int limitMode = 1;

    public static class PenSize {
        private final float maxSize;
        private final float minSize;

        public PenSize(float f, float f2) {
            this.maxSize = f;
            this.minSize = f2;
        }

        public float getMaxSize() {
            return this.maxSize;
        }

        public float getMinSize() {
            return this.minSize;
        }
    }

    public SelectLimit setPenSizeLimit(int i, float f, float f2) {
        this.mPenSizeMap.put(Integer.valueOf(i), new PenSize(f, f2));
        return this;
    }

    public SelectLimit setLimitMode(int i) {
        this.limitMode = i;
        return this;
    }

    public int getLimitMode() {
        return this.limitMode;
    }

    public PenSize getPenSizeLimit(int i) {
        return this.mPenSizeMap.get(Integer.valueOf(i));
    }
}
