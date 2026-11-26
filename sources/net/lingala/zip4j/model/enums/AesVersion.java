package net.lingala.zip4j.model.enums;

/* JADX INFO: loaded from: classes6.dex */
public enum AesVersion {
    ONE(1),
    TWO(2);

    private int versionNumber;

    AesVersion(int i) {
        this.versionNumber = i;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public static AesVersion getFromVersionNumber(int i) {
        for (AesVersion aesVersion : values()) {
            if (aesVersion.versionNumber == i) {
                return aesVersion;
            }
        }
        throw new IllegalArgumentException("Unsupported Aes version");
    }
}
