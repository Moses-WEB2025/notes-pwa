package com.xiaomi.accountsdk.account.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes4.dex */
public class AppSignatureHash {

    @SerializedName("md5")
    @Expose
    public final String md5;

    @SerializedName("sha1")
    @Expose
    public final String sha1;

    private AppSignatureHash(Builder builder) {
        this.sha1 = builder.sha1;
        this.md5 = builder.md5;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("AppSignatureHash{sha1='");
        stringBuffer.append(this.sha1).append("', md5='");
        stringBuffer.append(this.md5).append("'}");
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSignatureHash)) {
            return false;
        }
        AppSignatureHash appSignatureHash = (AppSignatureHash) obj;
        String str = this.sha1;
        if (str == null ? appSignatureHash.sha1 != null : !str.equals(appSignatureHash.sha1)) {
            return false;
        }
        String str2 = this.md5;
        return str2 == null ? appSignatureHash.md5 == null : str2.equals(appSignatureHash.md5);
    }

    public int hashCode() {
        String str = this.sha1;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.md5;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public static final class Builder {
        private String md5;
        private String sha1;

        public Builder sha1(String str) {
            this.sha1 = str;
            return this;
        }

        public Builder md5(String str) {
            this.md5 = str;
            return this;
        }

        public AppSignatureHash build() {
            return new AppSignatureHash(this);
        }
    }
}
