package com.miui.duokantext;

import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class JPEGEncoder implements Encoder {
    private long mNativeHandle;

    private native long nativeCreateEncoder(String str, int i, int i2, int i3) throws IOException;

    private native void nativeFinishCompress(long j);

    private native void nativeSavePixels(long j, int[] iArr, int i);

    private native void nativeStartCompress(long j);

    @Override // com.miui.duokantext.Encoder
    public void createEncoder(String str, int i, int i2, int i3) throws IOException {
        this.mNativeHandle = nativeCreateEncoder(str, i, i2, i3);
    }

    @Override // com.miui.duokantext.Encoder
    public void startCompress() {
        nativeStartCompress(this.mNativeHandle);
    }

    @Override // com.miui.duokantext.Encoder
    public void savePixels(int[] iArr, int i) {
        nativeSavePixels(this.mNativeHandle, iArr, i);
    }

    @Override // com.miui.duokantext.Encoder
    public void finishCompress() {
        nativeFinishCompress(this.mNativeHandle);
    }
}
