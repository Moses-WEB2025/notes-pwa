package com.agog.mathdisplay.render;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTCharset.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J1\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020!HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017¨\u0006\""}, d2 = {"Lcom/agog/mathdisplay/render/CGGlyph;", "", "gid", "", "glyphAscent", "", "glyphDescent", "glyphWidth", "(IFFF)V", "getGid", "()I", "setGid", "(I)V", "getGlyphAscent", "()F", "setGlyphAscent", "(F)V", "getGlyphDescent", "setGlyphDescent", "getGlyphWidth", "setGlyphWidth", "isValid", "", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class CGGlyph {
    private int gid;
    private float glyphAscent;
    private float glyphDescent;
    private float glyphWidth;

    public CGGlyph() {
        this(0, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public static /* synthetic */ CGGlyph copy$default(CGGlyph cGGlyph, int i, float f, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = cGGlyph.gid;
        }
        if ((i2 & 2) != 0) {
            f = cGGlyph.glyphAscent;
        }
        if ((i2 & 4) != 0) {
            f2 = cGGlyph.glyphDescent;
        }
        if ((i2 & 8) != 0) {
            f3 = cGGlyph.glyphWidth;
        }
        return cGGlyph.copy(i, f, f2, f3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getGid() {
        return this.gid;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getGlyphAscent() {
        return this.glyphAscent;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getGlyphDescent() {
        return this.glyphDescent;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getGlyphWidth() {
        return this.glyphWidth;
    }

    public final CGGlyph copy(int gid, float glyphAscent, float glyphDescent, float glyphWidth) {
        return new CGGlyph(gid, glyphAscent, glyphDescent, glyphWidth);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CGGlyph)) {
            return false;
        }
        CGGlyph cGGlyph = (CGGlyph) other;
        return this.gid == cGGlyph.gid && Intrinsics.areEqual((Object) Float.valueOf(this.glyphAscent), (Object) Float.valueOf(cGGlyph.glyphAscent)) && Intrinsics.areEqual((Object) Float.valueOf(this.glyphDescent), (Object) Float.valueOf(cGGlyph.glyphDescent)) && Intrinsics.areEqual((Object) Float.valueOf(this.glyphWidth), (Object) Float.valueOf(cGGlyph.glyphWidth));
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.gid) * 31) + Float.hashCode(this.glyphAscent)) * 31) + Float.hashCode(this.glyphDescent)) * 31) + Float.hashCode(this.glyphWidth);
    }

    public String toString() {
        return "CGGlyph(gid=" + this.gid + ", glyphAscent=" + this.glyphAscent + ", glyphDescent=" + this.glyphDescent + ", glyphWidth=" + this.glyphWidth + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public CGGlyph(int i, float f, float f2, float f3) {
        this.gid = i;
        this.glyphAscent = f;
        this.glyphDescent = f2;
        this.glyphWidth = f3;
    }

    public /* synthetic */ CGGlyph(int i, float f, float f2, float f3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3);
    }

    public final int getGid() {
        return this.gid;
    }

    public final float getGlyphAscent() {
        return this.glyphAscent;
    }

    public final float getGlyphDescent() {
        return this.glyphDescent;
    }

    public final float getGlyphWidth() {
        return this.glyphWidth;
    }

    public final void setGid(int i) {
        this.gid = i;
    }

    public final void setGlyphAscent(float f) {
        this.glyphAscent = f;
    }

    public final void setGlyphDescent(float f) {
        this.glyphDescent = f;
    }

    public final void setGlyphWidth(float f) {
        this.glyphWidth = f;
    }

    public final boolean isValid() {
        return this.gid != 0;
    }
}
