package com.miui.duokantext;

import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public interface Encoder {
    void createEncoder(String str, int i, int i2, int i3) throws IOException;

    void finishCompress();

    void savePixels(int[] iArr, int i);

    void startCompress();
}
