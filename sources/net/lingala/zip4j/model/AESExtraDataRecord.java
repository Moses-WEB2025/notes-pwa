package net.lingala.zip4j.model;

import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;

/* JADX INFO: loaded from: classes6.dex */
public class AESExtraDataRecord extends ZipHeader {
    private AesKeyStrength aesKeyStrength;
    private AesVersion aesVersion;
    private CompressionMethod compressionMethod;
    private int dataSize;
    private String vendorID;

    public AESExtraDataRecord() {
        setSignature(HeaderSignature.AES_EXTRA_DATA_RECORD);
        this.dataSize = 7;
        this.aesVersion = AesVersion.TWO;
        this.vendorID = "AE";
        this.aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256;
        this.compressionMethod = CompressionMethod.DEFLATE;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public void setDataSize(int i) {
        this.dataSize = i;
    }

    public AesVersion getAesVersion() {
        return this.aesVersion;
    }

    public void setAesVersion(AesVersion aesVersion) {
        this.aesVersion = aesVersion;
    }

    public String getVendorID() {
        return this.vendorID;
    }

    public void setVendorID(String str) {
        this.vendorID = str;
    }

    public AesKeyStrength getAesKeyStrength() {
        return this.aesKeyStrength;
    }

    public void setAesKeyStrength(AesKeyStrength aesKeyStrength) {
        this.aesKeyStrength = aesKeyStrength;
    }

    public CompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }
}
