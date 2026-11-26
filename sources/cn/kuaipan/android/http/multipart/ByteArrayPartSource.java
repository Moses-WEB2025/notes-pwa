package cn.kuaipan.android.http.multipart;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ByteArrayPartSource implements PartSource {
    private byte[] bytes;
    private String fileName;

    public ByteArrayPartSource(String str, byte[] bArr) {
        this.fileName = str;
        this.bytes = bArr;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        return this.bytes.length;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public String getFileName() {
        return this.fileName;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }
}
