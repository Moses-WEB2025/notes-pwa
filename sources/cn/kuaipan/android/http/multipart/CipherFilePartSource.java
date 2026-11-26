package cn.kuaipan.android.http.multipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NullCipher;

/* JADX INFO: loaded from: classes.dex */
public class CipherFilePartSource extends FilePartSource {
    private Cipher cipher;
    private long length;

    public CipherFilePartSource(File file, Cipher cipher) throws FileNotFoundException {
        super(file);
        this.length = -1L;
        this.cipher = cipher;
    }

    public CipherFilePartSource(String str, File file, Cipher cipher) throws FileNotFoundException {
        super(str, file);
        this.length = -1L;
        this.cipher = cipher;
    }

    @Override // cn.kuaipan.android.http.multipart.FilePartSource, cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() throws IOException {
        InputStream inputStreamCreateInputStream = super.createInputStream();
        if (this.cipher == null) {
            return new CipherInputStream(inputStreamCreateInputStream, new NullCipher());
        }
        return new CipherInputStream(inputStreamCreateInputStream, this.cipher);
    }

    @Override // cn.kuaipan.android.http.multipart.FilePartSource, cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        if (this.length == -1) {
            byte[] bArr = new byte[1024];
            InputStream inputStreamCreateInputStream = null;
            try {
                try {
                    inputStreamCreateInputStream = createInputStream();
                    int i = 0;
                    while (true) {
                        int i2 = inputStreamCreateInputStream.read(bArr);
                        if (i2 < 0) {
                            break;
                        }
                        i += i2;
                    }
                    this.length = i;
                } catch (IOException unused) {
                    this.length = 0L;
                }
            } finally {
                try {
                    inputStreamCreateInputStream.close();
                } catch (Exception unused2) {
                }
            }
        }
        return this.length;
    }
}
