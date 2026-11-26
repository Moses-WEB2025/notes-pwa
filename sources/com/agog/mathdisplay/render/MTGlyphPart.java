package com.agog.mathdisplay.render;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFontMathTable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\tHÆ\u0003J;\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010 \u001a\u00020\t2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\f\"\u0004\b\u0019\u0010\u000e¨\u0006%"}, d2 = {"Lcom/agog/mathdisplay/render/MTGlyphPart;", "", "glyph", "", "fullAdvance", "", "startConnectorLength", "endConnectorLength", "isExtender", "", "(IFFFZ)V", "getEndConnectorLength", "()F", "setEndConnectorLength", "(F)V", "getFullAdvance", "setFullAdvance", "getGlyph", "()I", "setGlyph", "(I)V", "()Z", "setExtender", "(Z)V", "getStartConnectorLength", "setStartConnectorLength", "component1", "component2", "component3", "component4", "component5", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class MTGlyphPart {
    private float endConnectorLength;
    private float fullAdvance;
    private int glyph;
    private boolean isExtender;
    private float startConnectorLength;

    public MTGlyphPart() {
        this(0, 0.0f, 0.0f, 0.0f, false, 31, null);
    }

    public static /* synthetic */ MTGlyphPart copy$default(MTGlyphPart mTGlyphPart, int i, float f, float f2, float f3, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = mTGlyphPart.glyph;
        }
        if ((i2 & 2) != 0) {
            f = mTGlyphPart.fullAdvance;
        }
        if ((i2 & 4) != 0) {
            f2 = mTGlyphPart.startConnectorLength;
        }
        if ((i2 & 8) != 0) {
            f3 = mTGlyphPart.endConnectorLength;
        }
        if ((i2 & 16) != 0) {
            z = mTGlyphPart.isExtender;
        }
        boolean z2 = z;
        float f4 = f2;
        return mTGlyphPart.copy(i, f, f4, f3, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getGlyph() {
        return this.glyph;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getFullAdvance() {
        return this.fullAdvance;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getStartConnectorLength() {
        return this.startConnectorLength;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getEndConnectorLength() {
        return this.endConnectorLength;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsExtender() {
        return this.isExtender;
    }

    public final MTGlyphPart copy(int glyph, float fullAdvance, float startConnectorLength, float endConnectorLength, boolean isExtender) {
        return new MTGlyphPart(glyph, fullAdvance, startConnectorLength, endConnectorLength, isExtender);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MTGlyphPart)) {
            return false;
        }
        MTGlyphPart mTGlyphPart = (MTGlyphPart) other;
        return this.glyph == mTGlyphPart.glyph && Intrinsics.areEqual((Object) Float.valueOf(this.fullAdvance), (Object) Float.valueOf(mTGlyphPart.fullAdvance)) && Intrinsics.areEqual((Object) Float.valueOf(this.startConnectorLength), (Object) Float.valueOf(mTGlyphPart.startConnectorLength)) && Intrinsics.areEqual((Object) Float.valueOf(this.endConnectorLength), (Object) Float.valueOf(mTGlyphPart.endConnectorLength)) && this.isExtender == mTGlyphPart.isExtender;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [int] */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    public int hashCode() {
        int iHashCode = ((((((Integer.hashCode(this.glyph) * 31) + Float.hashCode(this.fullAdvance)) * 31) + Float.hashCode(this.startConnectorLength)) * 31) + Float.hashCode(this.endConnectorLength)) * 31;
        boolean z = this.isExtender;
        ?? r2 = z;
        if (z) {
            r2 = 1;
        }
        return iHashCode + r2;
    }

    public String toString() {
        return "MTGlyphPart(glyph=" + this.glyph + ", fullAdvance=" + this.fullAdvance + ", startConnectorLength=" + this.startConnectorLength + ", endConnectorLength=" + this.endConnectorLength + ", isExtender=" + this.isExtender + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public MTGlyphPart(int i, float f, float f2, float f3, boolean z) {
        this.glyph = i;
        this.fullAdvance = f;
        this.startConnectorLength = f2;
        this.endConnectorLength = f3;
        this.isExtender = z;
    }

    public /* synthetic */ MTGlyphPart(int i, float f, float f2, float f3, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3, (i2 & 16) != 0 ? false : z);
    }

    public final int getGlyph() {
        return this.glyph;
    }

    public final void setGlyph(int i) {
        this.glyph = i;
    }

    public final float getFullAdvance() {
        return this.fullAdvance;
    }

    public final void setFullAdvance(float f) {
        this.fullAdvance = f;
    }

    public final float getStartConnectorLength() {
        return this.startConnectorLength;
    }

    public final void setStartConnectorLength(float f) {
        this.startConnectorLength = f;
    }

    public final float getEndConnectorLength() {
        return this.endConnectorLength;
    }

    public final void setEndConnectorLength(float f) {
        this.endConnectorLength = f;
    }

    public final boolean isExtender() {
        return this.isExtender;
    }

    public final void setExtender(boolean z) {
        this.isExtender = z;
    }
}
