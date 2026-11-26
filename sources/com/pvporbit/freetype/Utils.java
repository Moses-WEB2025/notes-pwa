package com.pvporbit.freetype;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public class Utils {
    public static native void deleteBuffer(ByteBuffer byteBuffer);

    public static native void fillBuffer(byte[] bArr, ByteBuffer byteBuffer, int i);

    public static native ByteBuffer newBuffer(int i);

    public static class Pointer {
        protected long pointer;

        public Pointer(long j) {
            this.pointer = j;
        }

        public long getPointer() {
            return this.pointer;
        }
    }

    public static byte[] loadFileToByteArray(String str) throws IOException {
        File file = new File(str);
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.read(bArr, 0, length);
        bufferedInputStream.close();
        return bArr;
    }
}
