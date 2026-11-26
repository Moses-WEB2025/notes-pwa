package com.xiaomi.micloudsdk.micloudrichmedia;

import android.text.TextUtils;
import java.io.IOException;
import java.util.List;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public abstract class UploadEntity {
    private static final int EXT_MAX_LEN = 5;
    protected List<String> mBlockSha1Hex;
    protected String mExt;
    protected String mHexDigest;
    protected String mHostingServer;
    protected int mOffset;
    protected String mTempId;
    protected String mType;

    void close() throws IOException {
    }

    abstract byte[] getData(int i) throws IOException;

    public abstract String getEncryptedSha1Hex(Cipher cipher);

    abstract int getLength() throws IOException;

    void open() throws IOException {
    }

    public UploadEntity(String str, String str2) throws IOException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The upload file parameters should not be null");
        }
        if (!MiCloudRichMediaSupportedFileType.isSupported(str)) {
            throw new IllegalArgumentException(String.format("The type %s is not supported", str));
        }
        if (str2.length() > 5) {
            throw new IllegalArgumentException(String.format("The ext's %s length should not exceeds %d", str2, 5));
        }
        this.mType = str;
        this.mExt = str2;
        this.mTempId = null;
        this.mHostingServer = null;
    }

    public String getExt() {
        return this.mExt;
    }

    public String getType() {
        return this.mType;
    }

    public String getHexDigest() {
        return this.mHexDigest;
    }

    public List<String> getBlockSha1Hex() {
        return this.mBlockSha1Hex;
    }

    String getHostingServer() {
        return this.mHostingServer;
    }

    void setHostingServer(String str) {
        this.mHostingServer = str;
    }

    String getTempId() {
        return this.mTempId;
    }

    void setTempId(String str) {
        this.mTempId = str;
    }

    int getOffset() {
        return this.mOffset;
    }

    boolean isFirstChunk() {
        return this.mOffset == 0;
    }

    boolean isLastChunk(int i) throws IOException {
        return this.mOffset < getLength() && this.mOffset + i >= getLength();
    }

    void resetOffset() {
        this.mOffset = 0;
    }
}
