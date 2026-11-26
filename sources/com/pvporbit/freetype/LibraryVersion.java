package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class LibraryVersion {
    private int major;
    private int minor;
    private int patch;

    public LibraryVersion(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.patch = i3;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getPatch() {
        return this.patch;
    }

    public String toString() {
        return this.major + "." + this.minor + "." + this.patch;
    }
}
