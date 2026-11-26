package xc.engine.data;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface IDataSwapListener {
    void onLoadListener(int i, int[] iArr, int i2, List<byte[]> list);

    CsBufferBean onSaveListener(int i);
}
