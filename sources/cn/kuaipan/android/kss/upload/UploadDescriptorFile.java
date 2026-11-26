package cn.kuaipan.android.kss.upload;

import android.os.ParcelFileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class UploadDescriptorFile {
    public final String fileName;
    public final String filePath;
    public final long fileSize;
    public final ParcelFileDescriptor pFd;

    public UploadDescriptorFile(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, long j) {
        this.pFd = parcelFileDescriptor;
        this.fileName = str;
        this.filePath = str2;
        this.fileSize = j;
    }

    public FileInputStream openFileInputStream() throws IOException {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.pFd.dup());
        autoCloseInputStream.getChannel().position(0L);
        return autoCloseInputStream;
    }

    public void close() throws IOException {
        this.pFd.close();
    }
}
