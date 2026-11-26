package net.lingala.zip4j.model.enums;

import com.xiaomi.verificationsdk.internal.Constants;

/* JADX INFO: loaded from: classes6.dex */
public enum RandomAccessFileMode {
    READ(Constants.RANDOM_LONG),
    WRITE("rw");

    private String value;

    RandomAccessFileMode(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
