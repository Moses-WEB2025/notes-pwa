package xc.richtext.data.bean;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class CsBufferBean implements IBufferBean {
    private final List<byte[]> mBuffers;
    private final int[] mTypes;

    public CsBufferBean(int i) {
        this.mTypes = new int[i];
        this.mBuffers = new ArrayList(i);
    }

    public void addTypeAndBuffer(int i, int i2, byte[] bArr) {
        this.mTypes[i] = i2;
        this.mBuffers.add(bArr);
    }

    @Override // xc.richtext.data.bean.IBufferBean
    public int[] getTypes() {
        return this.mTypes;
    }

    @Override // xc.richtext.data.bean.IBufferBean
    public List<byte[]> getBuffers() {
        return this.mBuffers;
    }
}
