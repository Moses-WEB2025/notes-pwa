package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import com.agog.mathdisplay.parse.NSRange;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010.\u001a\u00020/H\u0016J\u0006\u00100\u001a\u000201J\u0010\u00102\u001a\u00020/2\u0006\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u00020/H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR$\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u001c@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\f\"\u0004\b(\u0010\u000eR$\u0010)\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0019\"\u0004\b+\u0010\u001bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\f\"\u0004\b-\u0010\u000e¨\u00066"}, d2 = {"Lcom/agog/mathdisplay/render/MTDisplay;", "", "ascent", "", "descent", Layer.KEY_WIDTH, "range", "Lcom/agog/mathdisplay/parse/NSRange;", "hasScript", "", "(FFFLcom/agog/mathdisplay/parse/NSRange;Z)V", "getAscent", "()F", "setAscent", "(F)V", "getDescent", "setDescent", "getHasScript", "()Z", "setHasScript", "(Z)V", "value", "", "localTextColor", "getLocalTextColor", "()I", "setLocalTextColor", "(I)V", "Lcom/agog/mathdisplay/render/CGPoint;", "position", "getPosition", "()Lcom/agog/mathdisplay/render/CGPoint;", "setPosition", "(Lcom/agog/mathdisplay/render/CGPoint;)V", "getRange", "()Lcom/agog/mathdisplay/parse/NSRange;", "setRange", "(Lcom/agog/mathdisplay/parse/NSRange;)V", "shiftDown", "getShiftDown", "setShiftDown", "textColor", "getTextColor", "setTextColor", "getWidth", "setWidth", "colorChanged", "", "displayBounds", "Lcom/agog/mathdisplay/render/CGRect;", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class MTDisplay {
    private float ascent;
    private float descent;
    private boolean hasScript;
    private int localTextColor;
    private CGPoint position;
    private NSRange range;
    private float shiftDown;
    private int textColor;
    private float width;

    public MTDisplay() {
        this(0.0f, 0.0f, 0.0f, null, false, 31, null);
    }

    public void colorChanged() {
    }

    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public void positionChanged() {
    }

    public MTDisplay(float f, float f2, float f3, NSRange range, boolean z) {
        Intrinsics.checkNotNullParameter(range, "range");
        this.ascent = f;
        this.descent = f2;
        this.width = f3;
        this.range = range;
        this.hasScript = z;
        this.position = new CGPoint(0.0f, 0.0f, 3, null);
        this.textColor = -16777216;
        this.range = NSRange.copy$default(this.range, 0, 0, 3, null);
    }

    public float getAscent() {
        return this.ascent;
    }

    public float getDescent() {
        return this.descent;
    }

    public float getWidth() {
        return this.width;
    }

    public void setAscent(float f) {
        this.ascent = f;
    }

    public void setDescent(float f) {
        this.descent = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public /* synthetic */ MTDisplay(float f, float f2, float f3, NSRange nSRange, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? new NSRange(0, 0, 3, null) : nSRange, (i & 16) != 0 ? false : z);
    }

    public final boolean getHasScript() {
        return this.hasScript;
    }

    public final NSRange getRange() {
        return this.range;
    }

    public final void setHasScript(boolean z) {
        this.hasScript = z;
    }

    public final void setRange(NSRange nSRange) {
        Intrinsics.checkNotNullParameter(nSRange, "<set-?>");
        this.range = nSRange;
    }

    public final float getShiftDown() {
        return this.shiftDown;
    }

    public final void setShiftDown(float f) {
        this.shiftDown = f;
    }

    public final CGPoint getPosition() {
        return this.position;
    }

    public final void setPosition(CGPoint value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.position = CGPoint.copy$default(value, 0.0f, 0.0f, 3, null);
        positionChanged();
    }

    public final int getTextColor() {
        return this.textColor;
    }

    public final void setTextColor(int i) {
        this.textColor = i;
        colorChanged();
    }

    public final int getLocalTextColor() {
        return this.localTextColor;
    }

    public final void setLocalTextColor(int i) {
        this.localTextColor = i;
        colorChanged();
    }

    public final CGRect displayBounds() {
        return new CGRect(this.position.getX(), this.position.getY() - getDescent(), getWidth(), getAscent() + getDescent());
    }
}
