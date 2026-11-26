package cn.kuaipan.android.kss.download;

import cn.kuaipan.android.utils.FileUtils;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class KssDownloadFile {
    public final long currentSize;
    public final File innerCacheFile;

    public abstract boolean exists();

    public abstract KssAccessor getKssAccessor() throws InterruptedException, IOException;

    public abstract void resetFileIfNeeded(boolean z, long j);

    public abstract void setLastModifyTime(long j);

    private KssDownloadFile(File file, long j) {
        this.innerCacheFile = file;
        this.currentSize = j;
    }

    public static KssDownloadFile createByFile(final File file) {
        return new KssDownloadFile(file, file.length()) { // from class: cn.kuaipan.android.kss.download.KssDownloadFile.1
            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public KssAccessor getKssAccessor() throws InterruptedException, IOException {
                return new KssFileAccessor(file);
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public void resetFileIfNeeded(boolean z, long j) {
                if (file.exists()) {
                    if ((!z || file.isDirectory() || file.length() > j) && !FileUtils.deletes(file)) {
                        throw new SecurityException("Failed delete target file. Can't download to dest path: " + file);
                    }
                    return;
                }
                file.getParentFile().mkdirs();
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public void setLastModifyTime(long j) {
                if (j > 0) {
                    file.setLastModified(j);
                }
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public boolean exists() {
                return file.exists();
            }
        };
    }

    public static KssDownloadFile createByFileDescriptor(final DownloadDescriptorFile downloadDescriptorFile) {
        return new KssDownloadFile(downloadDescriptorFile.innerCacheFile, downloadDescriptorFile.currentSize) { // from class: cn.kuaipan.android.kss.download.KssDownloadFile.2
            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public void setLastModifyTime(long j) {
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public KssAccessor getKssAccessor() throws IOException {
                return new KssFileDescriptorAccessor(downloadDescriptorFile);
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public void resetFileIfNeeded(boolean z, long j) throws Throwable {
                if (!z || downloadDescriptorFile.currentSize > j) {
                    try {
                        downloadDescriptorFile.reset();
                    } catch (IOException unused) {
                        throw new SecurityException("Failed delete target file. Can't download to dest path: " + downloadDescriptorFile.fileName);
                    }
                }
            }

            @Override // cn.kuaipan.android.kss.download.KssDownloadFile
            public boolean exists() {
                return downloadDescriptorFile.currentSize > 0;
            }
        };
    }
}
