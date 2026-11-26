package com.miui.handwritecommon.api.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: BitmapEx.java */
/* JADX INFO: loaded from: classes2.dex */
class DataOutputStreamLittleEndian {
    private DataOutputStream systemStream;

    public DataOutputStreamLittleEndian(DataOutputStream dataOutputStream) {
        this.systemStream = dataOutputStream;
    }

    public void close() throws IOException {
        this.systemStream.close();
    }

    public void write(byte[] bArr) throws IOException {
        this.systemStream.write(bArr);
    }

    public void write(int[] iArr) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iArr.length * 4);
        byteBufferAllocate.asIntBuffer().put(iArr);
        write(byteBufferAllocate.array());
    }

    public void writeInt(int i) throws IOException {
        this.systemStream.writeInt(ByteOrder.reverse(i));
    }

    public void writeLong(long j) throws IOException {
        this.systemStream.writeLong(ByteOrder.reverse(j));
    }

    public void writeShort(short s) throws IOException {
        this.systemStream.writeShort(ByteOrder.reverse(s));
    }

    public void writeString(String str) throws IOException {
        this.systemStream.writeBytes(str);
    }
}
