package com.xiaomi.e2ee.appkey;

/* JADX INFO: loaded from: classes4.dex */
public class DecryptKeyEntry {
    public final byte[] recordIV;
    public final byte[] recordKey;

    public DecryptKeyEntry(byte[] bArr, byte[] bArr2) {
        this.recordKey = bArr;
        this.recordIV = bArr2;
    }
}
