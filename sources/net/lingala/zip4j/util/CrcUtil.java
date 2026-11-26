package net.lingala.zip4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

/* JADX INFO: loaded from: classes6.dex */
public class CrcUtil {
    private static final int BUF_SIZE = 16384;

    public static long computeFileCrc(File file, ProgressMonitor progressMonitor) throws IOException {
        if (file == null || !file.exists() || !file.canRead()) {
            throw new ZipException("input file is null or does not exist or cannot read. Cannot calculate CRC for the file");
        }
        byte[] bArr = new byte[16384];
        CRC32 crc32 = new CRC32();
        FileInputStream fileInputStream = new FileInputStream(file);
        while (true) {
            try {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    crc32.update(bArr, 0, i);
                    if (progressMonitor != null) {
                        progressMonitor.updateWorkCompleted(i);
                        if (progressMonitor.isCancelAllTasks()) {
                            progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
                            progressMonitor.setState(ProgressMonitor.State.READY);
                            fileInputStream.close();
                            return 0L;
                        }
                    }
                } else {
                    long value = crc32.getValue();
                    fileInputStream.close();
                    return value;
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        }
    }
}
