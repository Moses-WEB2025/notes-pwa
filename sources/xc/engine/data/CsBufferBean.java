package xc.engine.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class CsBufferBean {
    public int[] a;
    public List<byte[]> b;

    public CsBufferBean(int[] iArr, List<byte[]> list) {
        this.a = iArr;
        this.b = new ArrayList(list);
    }

    public int[] getTypes() {
        return this.a;
    }

    public List<byte[]> getByteList() {
        return this.b;
    }

    public int dataSize() {
        return this.b.size();
    }
}
