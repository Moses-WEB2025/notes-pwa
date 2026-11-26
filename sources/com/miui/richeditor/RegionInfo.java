package com.miui.richeditor;

import defpackage.R2;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
class RegionInfo implements Serializable, Comparable<RegionInfo> {
    private static final long serialVersionUID = 6516963486300414108L;
    private int mEnd;
    private int mFlag;
    private int mStart;

    public RegionInfo(int i, int i2) {
        this.mStart = i;
        this.mEnd = i2;
    }

    public RegionInfo(int i, int i2, int i3) {
        this.mStart = i;
        this.mEnd = i2;
        this.mFlag = i3;
    }

    public int getStart() {
        return this.mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public int getFlag() {
        return this.mFlag;
    }

    public boolean isEmpty() {
        return this.mStart == this.mEnd;
    }

    public boolean isValid() {
        return this.mStart <= this.mEnd;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RegionInfo regionInfo = (RegionInfo) obj;
        return this.mStart == regionInfo.getStart() && this.mEnd == regionInfo.getEnd();
    }

    public int hashCode() {
        long j = this.mStart;
        int i = R2.attr.checkedButton + ((int) (j ^ (j >>> 32)));
        long j2 = this.mEnd;
        return (i * 37) + ((int) (j2 ^ (j2 >>> 32)));
    }

    public String toString() {
        return "[" + this.mStart + ", " + this.mEnd + "]";
    }

    @Override // java.lang.Comparable
    public int compareTo(RegionInfo regionInfo) {
        int i = this.mStart;
        int i2 = regionInfo.mStart;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        int i3 = this.mEnd;
        int i4 = regionInfo.mEnd;
        if (i3 == i4) {
            return 0;
        }
        return i3 < i4 ? -1 : 1;
    }

    public void setStart(int i) {
        this.mStart = i;
    }

    public void setEnd(int i) {
        this.mEnd = i;
    }
}
