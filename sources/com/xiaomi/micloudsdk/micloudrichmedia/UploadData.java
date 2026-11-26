package com.xiaomi.micloudsdk.micloudrichmedia;

import com.xiaomi.micloudsdk.utils.CloudCoder;
import java.io.IOException;
import java.security.MessageDigest;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public class UploadData extends UploadEntity {
    private byte[] mData;

    public UploadData(byte[] bArr, String str, String str2) throws IOException {
        super(str, str2);
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("The data to be uploaded should not be null");
        }
        this.mData = bArr;
        this.mHexDigest = CloudCoder.getDataSha1Digest(bArr);
        if (this.mHexDigest == null) {
            throw new IOException("Calculate file sha-1 digest error");
        }
        this.mBlockSha1Hex = CloudCoder.getBlockSha1Hex(this.mData);
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    public String getEncryptedSha1Hex(Cipher cipher) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(cipher.doFinal(this.mData));
            return CloudCoder.getHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    byte[] getData(int i) {
        byte[] bArr;
        if (this.mOffset >= getLength()) {
            return null;
        }
        if (i < getLength() - this.mOffset) {
            bArr = new byte[i];
        } else {
            bArr = new byte[getLength() - this.mOffset];
        }
        System.arraycopy(this.mData, this.mOffset, bArr, 0, bArr.length);
        this.mOffset += bArr.length;
        return bArr;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadEntity
    int getLength() {
        return this.mData.length;
    }
}
