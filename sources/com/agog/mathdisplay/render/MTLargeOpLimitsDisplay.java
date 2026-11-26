package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u001e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020&H\u0016J\u0006\u0010+\u001a\u00020&J\u0006\u0010,\u001a\u00020&J\u0006\u0010-\u001a\u00020&R$\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\r\"\u0004\b\u0014\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\r\"\u0004\b\u0016\u0010\u000fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR$\u0010\u001b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\r\"\u0004\b\u001d\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0018\"\u0004\b!\u0010\u001aR$\u0010\"\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\r\"\u0004\b$\u0010\u000f¨\u0006."}, d2 = {"Lcom/agog/mathdisplay/render/MTLargeOpLimitsDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "nucleus", "upperLimit", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "lowerLimit", "limitShift", "", "extraPadding", "(Lcom/agog/mathdisplay/render/MTDisplay;Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/render/MTMathListDisplay;FF)V", "value", "ascent", "getAscent", "()F", "setAscent", "(F)V", "descent", "getDescent", "setDescent", "getExtraPadding", "setExtraPadding", "getLimitShift", "setLimitShift", "getLowerLimit", "()Lcom/agog/mathdisplay/render/MTMathListDisplay;", "setLowerLimit", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;)V", "lowerLimitGap", "getLowerLimitGap", "setLowerLimitGap", "getNucleus", "()Lcom/agog/mathdisplay/render/MTDisplay;", "getUpperLimit", "setUpperLimit", "upperLimitGap", "getUpperLimitGap", "setUpperLimitGap", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "updateLowerLimitPosition", "updateNucleusPosition", "updateUpperLimitPosition", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTLargeOpLimitsDisplay extends MTDisplay {
    private float extraPadding;
    private float limitShift;
    private MTMathListDisplay lowerLimit;
    private float lowerLimitGap;
    private final MTDisplay nucleus;
    private MTMathListDisplay upperLimit;
    private float upperLimitGap;

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setAscent(float f) {
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setDescent(float f) {
    }

    public final float getExtraPadding() {
        return this.extraPadding;
    }

    public final float getLimitShift() {
        return this.limitShift;
    }

    public final MTMathListDisplay getLowerLimit() {
        return this.lowerLimit;
    }

    public final MTDisplay getNucleus() {
        return this.nucleus;
    }

    public final MTMathListDisplay getUpperLimit() {
        return this.upperLimit;
    }

    public final void setExtraPadding(float f) {
        this.extraPadding = f;
    }

    public final void setLimitShift(float f) {
        this.limitShift = f;
    }

    public final void setLowerLimit(MTMathListDisplay mTMathListDisplay) {
        this.lowerLimit = mTMathListDisplay;
    }

    public final void setUpperLimit(MTMathListDisplay mTMathListDisplay) {
        this.upperLimit = mTMathListDisplay;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTLargeOpLimitsDisplay(MTDisplay nucleus, MTMathListDisplay mTMathListDisplay, MTMathListDisplay mTMathListDisplay2, float f, float f2) {
        super(0.0f, 0.0f, 0.0f, null, false, 31, null);
        Intrinsics.checkNotNullParameter(nucleus, "nucleus");
        this.nucleus = nucleus;
        this.upperLimit = mTMathListDisplay;
        this.lowerLimit = mTMathListDisplay2;
        this.limitShift = f;
        this.extraPadding = f2;
        float width = nucleus.getWidth();
        MTMathListDisplay mTMathListDisplay3 = this.upperLimit;
        if (mTMathListDisplay3 != null) {
            Intrinsics.checkNotNull(mTMathListDisplay3);
            width = Math.max(width, mTMathListDisplay3.getWidth());
        }
        MTMathListDisplay mTMathListDisplay4 = this.lowerLimit;
        if (mTMathListDisplay4 != null) {
            Intrinsics.checkNotNull(mTMathListDisplay4);
            width = Math.max(width, mTMathListDisplay4.getWidth());
        }
        setWidth(width);
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getAscent() {
        if (this.upperLimit != null) {
            float ascent = this.nucleus.getAscent() + this.extraPadding;
            MTMathListDisplay mTMathListDisplay = this.upperLimit;
            Intrinsics.checkNotNull(mTMathListDisplay);
            float ascent2 = ascent + mTMathListDisplay.getAscent() + this.upperLimitGap;
            MTMathListDisplay mTMathListDisplay2 = this.upperLimit;
            Intrinsics.checkNotNull(mTMathListDisplay2);
            return ascent2 + mTMathListDisplay2.getDescent();
        }
        return this.nucleus.getAscent();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getDescent() {
        if (this.lowerLimit != null) {
            float descent = this.nucleus.getDescent() + this.extraPadding + this.lowerLimitGap;
            MTMathListDisplay mTMathListDisplay = this.lowerLimit;
            Intrinsics.checkNotNull(mTMathListDisplay);
            float descent2 = descent + mTMathListDisplay.getDescent();
            MTMathListDisplay mTMathListDisplay2 = this.lowerLimit;
            Intrinsics.checkNotNull(mTMathListDisplay2);
            return descent2 + mTMathListDisplay2.getAscent();
        }
        return this.nucleus.getDescent();
    }

    public final float getLowerLimitGap() {
        return this.lowerLimitGap;
    }

    public final void setLowerLimitGap(float f) {
        this.lowerLimitGap = f;
        updateLowerLimitPosition();
    }

    public final float getUpperLimitGap() {
        return this.upperLimitGap;
    }

    public final void setUpperLimitGap(float f) {
        this.upperLimitGap = f;
        updateUpperLimitPosition();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void positionChanged() {
        updateLowerLimitPosition();
        updateUpperLimitPosition();
        updateNucleusPosition();
    }

    public final void updateLowerLimitPosition() {
        MTMathListDisplay mTMathListDisplay = this.lowerLimit;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setPosition(new CGPoint((getPosition().getX() - this.limitShift) + ((getWidth() - mTMathListDisplay.getWidth()) / 2), ((getPosition().getY() - this.nucleus.getDescent()) - this.lowerLimitGap) - mTMathListDisplay.getAscent()));
        }
    }

    public final void updateUpperLimitPosition() {
        MTMathListDisplay mTMathListDisplay = this.upperLimit;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setPosition(new CGPoint(getPosition().getX() + this.limitShift + ((getWidth() - mTMathListDisplay.getWidth()) / 2), getPosition().getY() + this.nucleus.getAscent() + this.upperLimitGap + mTMathListDisplay.getDescent()));
        }
    }

    public final void updateNucleusPosition() {
        this.nucleus.setPosition(new CGPoint(getPosition().getX() + ((getWidth() - this.nucleus.getWidth()) / 2), getPosition().getY()));
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        this.nucleus.setTextColor(getTextColor());
        MTMathListDisplay mTMathListDisplay = this.upperLimit;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setTextColor(getTextColor());
        }
        MTMathListDisplay mTMathListDisplay2 = this.lowerLimit;
        if (mTMathListDisplay2 != null) {
            mTMathListDisplay2.setTextColor(getTextColor());
        }
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        MTMathListDisplay mTMathListDisplay = this.upperLimit;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.draw(canvas);
        }
        MTMathListDisplay mTMathListDisplay2 = this.lowerLimit;
        if (mTMathListDisplay2 != null) {
            mTMathListDisplay2.draw(canvas);
        }
        this.nucleus.draw(canvas);
    }
}
