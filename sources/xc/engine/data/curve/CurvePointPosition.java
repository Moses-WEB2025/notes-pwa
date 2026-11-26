package xc.engine.data.curve;

import java.util.Arrays;

/* JADX INFO: loaded from: classes6.dex */
public class CurvePointPosition implements ICurvePointPosition {
    private long id;
    private int[] positions = new int[8];
    private int size = 0;

    public CurvePointPosition(long j) {
        this.id = j;
    }

    @Override // xc.engine.data.curve.ICurvePointPosition
    public long getCurvePointList() {
        return this.id;
    }

    @Override // xc.engine.data.curve.ICurvePointPosition
    public int get(int i) {
        return this.positions[i];
    }

    @Override // xc.engine.data.curve.ICurvePointPosition
    public void addPointPosition(int i) {
        if (containsPosition(i)) {
            return;
        }
        int i2 = this.size + 1;
        int[] iArr = this.positions;
        if (i2 > iArr.length) {
            int length = iArr.length;
            int i3 = length + (length >> 1);
            if (i3 - i2 >= 0) {
                i2 = i3;
            }
            this.positions = Arrays.copyOf(iArr, i2);
        }
        int i4 = this.size;
        this.size = i4 + 1;
        this.positions[i4] = i;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0012 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:9:0x0010 A[RETURN] */
    @Override // xc.engine.data.curve.ICurvePointPosition
    public boolean containsPosition(int i) {
        int i2 = 0;
        while (i2 < this.size) {
            if (this.positions[i2] == i) {
                if (i2 >= 0) {
                    return true;
                }
                return false;
            }
            i2++;
        }
        i2 = -1;
        if (i2 >= 0) {
            return true;
        }
        return false;
    }

    @Override // xc.engine.data.curve.ICurvePointPosition
    public int size() {
        return this.size;
    }
}
