package com.xiaomi.ai.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes4.dex */
public class WaveMaker {
    public static void convertToWaveFile(long j, int i, long j2, InputStream inputStream, OutputStream outputStream) throws IOException {
        writeWaveFileHeader(outputStream, j2, j, i, ((16 * j) * ((long) i)) / 8);
        byte[] bArr = new byte[4096];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 <= 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i2);
            }
        }
    }

    public static void convertToWaveFile(long j, int i, long j2, InputStream inputStream, String str) throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                convertToWaveFile(j, i, j2, inputStream, fileOutputStream2);
                fileOutputStream2.close();
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream == null) {
                    throw th;
                }
                fileOutputStream.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void convertToWaveFile(long j, int i, String str, String str2) throws Throwable {
        Throwable th;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                convertToWaveFile(j, i, fileInputStream2.getChannel().size(), fileInputStream2, str2);
                fileInputStream2.close();
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                if (fileInputStream == null) {
                    throw th;
                }
                fileInputStream.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void fixHeaderLength(File file) throws Throwable {
        long length = file.length();
        long j = length - 44;
        long j2 = length - 8;
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            try {
                randomAccessFile2.seek(4L);
                byte[] bArr = {(byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255)};
                randomAccessFile2.write(bArr);
                bArr[0] = (byte) (j & 255);
                bArr[1] = (byte) ((j >> 8) & 255);
                bArr[2] = (byte) ((j >> 16) & 255);
                bArr[3] = (byte) ((j >> 24) & 255);
                randomAccessFile2.seek(40L);
                randomAccessFile2.write(bArr);
                randomAccessFile2.close();
            } catch (Throwable th) {
                th = th;
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void writeWaveFileHeader(OutputStream outputStream, long j, long j2, int i, long j3) throws IOException {
        long j4 = j + 36;
        outputStream.write(new byte[]{82, 73, 70, 70, (byte) (j4 & 255), (byte) ((j4 >> 8) & 255), (byte) ((j4 >> 16) & 255), (byte) ((j4 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i, 0, (byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), 2, 0, 16, 0, 100, 97, 116, 97, (byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)}, 0, 44);
    }
}
