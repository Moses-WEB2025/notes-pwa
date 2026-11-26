package cn.kuaipan.android.kss.download;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface KssAccessor {
    void close() throws IOException;

    void inflate(long j) throws IOException;

    void lock();

    String sha1(long j, long j2) throws IOException;

    void unlock();

    int write(byte[] bArr, int i, int i2, LoadRecorder loadRecorder) throws IOException;
}
