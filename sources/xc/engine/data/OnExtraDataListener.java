package xc.engine.data;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface OnExtraDataListener {
    void onLoadListener(int i, short[] sArr, int i2, List<byte[]> list);

    CsBufferBean onSaveExtraData(int i);
}
