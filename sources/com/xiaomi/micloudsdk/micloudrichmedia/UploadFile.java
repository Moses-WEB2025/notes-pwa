package com.xiaomi.micloudsdk.micloudrichmedia;

import android.text.TextUtils;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public class UploadFile extends UploadEntity {
    private RandomAccessFile mFile;
    private String mFilePath;

    public UploadFile(String str, String str2, String str3) throws IOException {
        super(str2, str3);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The upload file parameters should not be null");
        }
        this.mFilePath = str;
        this.mHexDigest = CloudCoder.getFileSha1Digest(str);
        if (this.mHexDigest == null) {
            throw new IOException("Calculate file sha-1 digest error");
        }
        this.mBlockSha1Hex = CloudCoder.getBlockSha1Hex(this.mFilePath);
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    byte[] getData(int i) throws IOException {
        byte[] bArr;
        if (this.mFile == null || this.mOffset >= this.mFile.length()) {
            return null;
        }
        if (i < getLength() - this.mOffset) {
            bArr = new byte[i];
        } else {
            bArr = new byte[getLength() - this.mOffset];
        }
        this.mFile.seek(this.mOffset);
        this.mFile.read(bArr);
        this.mOffset += bArr.length;
        return bArr;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    int getLength() throws IOException {
        RandomAccessFile randomAccessFile = this.mFile;
        if (randomAccessFile == null) {
            return 0;
        }
        return (int) randomAccessFile.length();
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    void close() throws IOException {
        RandomAccessFile randomAccessFile = this.mFile;
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
        this.mFile = null;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0042  */
    /* JADX WARN: Code duplicated, block: B:29:0x004b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:42:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    public String getEncryptedSha1Hex(Cipher cipher) throws Throwable {
        FileInputStream fileInputStream;
        MessageDigest messageDigest;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                messageDigest = MessageDigest.getInstance("SHA1");
                try {
                    fileInputStream = new FileInputStream(new File(this.mFilePath));
                    try {
                        try {
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int i = fileInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                messageDigest.update(cipher.doFinal(bArr, 0, i));
                            }
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream2 = fileInputStream;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (messageDigest != null) {
                            return CloudCoder.getHexString(messageDigest.digest());
                        }
                        return null;
                    }
                } catch (Exception e4) {
                    e = e4;
                    fileInputStream = null;
                }
            } catch (Exception e5) {
                e = e5;
                fileInputStream = null;
                messageDigest = null;
            }
            fileInputStream.close();
            if (messageDigest != null) {
                return CloudCoder.getHexString(messageDigest.digest());
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    void open() throws IOException {
        this.mFile = new RandomAccessFile(this.mFilePath, Constants.RANDOM_LONG);
    }
}
