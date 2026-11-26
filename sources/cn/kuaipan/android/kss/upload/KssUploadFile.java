package cn.kuaipan.android.kss.upload;

import cn.kuaipan.android.utils.RandomFileDescriptorInputStream;
import cn.kuaipan.android.utils.RandomFileInputStream;
import cn.kuaipan.android.utils.RandomInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class KssUploadFile {
    public final String fileAbsPath;
    public final String filePath;
    public final long fileSize;

    public abstract InputStream getInputStream() throws IOException;

    public abstract RandomInputStream getRandomFileInputStream() throws IOException;

    public abstract boolean isFileInvalid();

    private KssUploadFile(String str, String str2, long j) {
        this.fileAbsPath = str;
        this.filePath = str2;
        this.fileSize = j;
    }

    public static KssUploadFile createByFile(final File file) {
        return new KssUploadFile(file.getAbsolutePath(), file.getPath(), file.length()) { // from class: cn.kuaipan.android.kss.upload.KssUploadFile.1
            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public boolean isFileInvalid() {
                return (file.isFile() && file.canRead() && file.length() > 0) ? false : true;
            }

            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public InputStream getInputStream() throws FileNotFoundException {
                return new FileInputStream(file);
            }

            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public RandomInputStream getRandomFileInputStream() throws FileNotFoundException {
                return new RandomFileInputStream(file);
            }
        };
    }

    public static KssUploadFile createByFileDescriptor(final UploadDescriptorFile uploadDescriptorFile) {
        return new KssUploadFile(uploadDescriptorFile.filePath, uploadDescriptorFile.fileName, uploadDescriptorFile.fileSize) { // from class: cn.kuaipan.android.kss.upload.KssUploadFile.2
            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public boolean isFileInvalid() {
                return uploadDescriptorFile.fileSize <= 0;
            }

            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public InputStream getInputStream() throws IOException {
                return uploadDescriptorFile.openFileInputStream();
            }

            @Override // cn.kuaipan.android.kss.upload.KssUploadFile
            public RandomInputStream getRandomFileInputStream() throws IOException {
                return new RandomFileDescriptorInputStream(uploadDescriptorFile);
            }
        };
    }
}
