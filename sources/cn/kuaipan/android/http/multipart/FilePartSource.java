package cn.kuaipan.android.http.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class FilePartSource implements PartSource {
    private File file;
    private String fileName;

    public FilePartSource(File file) throws FileNotFoundException {
        this.fileName = null;
        this.file = file;
        if (file != null) {
            if (!file.isFile()) {
                throw new FileNotFoundException("File is not a normal file.");
            }
            if (!file.canRead()) {
                throw new FileNotFoundException("File is not readable.");
            }
            this.fileName = file.getName();
        }
    }

    public FilePartSource(String str, File file) throws FileNotFoundException {
        this(file);
        if (str != null) {
            this.fileName = str;
        }
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        File file = this.file;
        if (file != null) {
            return file.length();
        }
        return 0L;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public String getFileName() {
        String str = this.fileName;
        return str == null ? "noname" : str;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        return new ByteArrayInputStream(new byte[0]);
    }
}
