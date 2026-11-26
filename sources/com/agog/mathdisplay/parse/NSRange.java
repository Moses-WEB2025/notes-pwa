package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0000J\u0013\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u0000R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007¨\u0006\u001b"}, d2 = {"Lcom/agog/mathdisplay/parse/NSRange;", "", "location", "", "length", "(II)V", "getLength", "()I", "setLength", "(I)V", "getLocation", "setLocation", "maxrange", "getMaxrange", "component1", "component2", "copy", "equal", "", "cmp", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "union", HtmlParser.LinkCardElement.TAG, "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class NSRange {
    private int length;
    private int location;

    /* JADX WARN: Illegal instructions before constructor call */
    public NSRange() {
        int i = 0;
        this(i, i, 3, null);
    }

    public static /* synthetic */ NSRange copy$default(NSRange nSRange, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = nSRange.location;
        }
        if ((i3 & 2) != 0) {
            i2 = nSRange.length;
        }
        return nSRange.copy(i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getLocation() {
        return this.location;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLength() {
        return this.length;
    }

    public final NSRange copy(int location, int length) {
        return new NSRange(location, length);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NSRange)) {
            return false;
        }
        NSRange nSRange = (NSRange) other;
        return this.location == nSRange.location && this.length == nSRange.length;
    }

    public int hashCode() {
        return (Integer.hashCode(this.location) * 31) + Integer.hashCode(this.length);
    }

    public String toString() {
        return "NSRange(location=" + this.location + ", length=" + this.length + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public NSRange(int i, int i2) {
        this.location = i;
        this.length = i2;
    }

    public /* synthetic */ NSRange(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? 0 : i2);
    }

    public final int getLength() {
        return this.length;
    }

    public final int getLocation() {
        return this.location;
    }

    public final void setLength(int i) {
        this.length = i;
    }

    public final void setLocation(int i) {
        this.location = i;
    }

    public final boolean equal(NSRange cmp) {
        Intrinsics.checkNotNullParameter(cmp, "cmp");
        return cmp.location == this.location && cmp.length == this.length;
    }

    public final int getMaxrange() {
        return this.location + this.length;
    }

    public final NSRange union(NSRange a) {
        Intrinsics.checkNotNullParameter(a, "a");
        int iMax = Math.max(a.getMaxrange(), getMaxrange());
        int iMin = Math.min(a.location, this.location);
        return new NSRange(iMin, iMax - iMin);
    }
}
