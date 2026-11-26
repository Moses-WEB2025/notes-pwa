package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.agog.mathdisplay.parse.NSRange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0006\u0010\u0013\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lcom/agog/mathdisplay/render/MTAccentDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "accent", "Lcom/agog/mathdisplay/render/MTGlyphDisplay;", "accentee", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "(Lcom/agog/mathdisplay/render/MTGlyphDisplay;Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/parse/NSRange;)V", "getAccent", "()Lcom/agog/mathdisplay/render/MTGlyphDisplay;", "getAccentee", "()Lcom/agog/mathdisplay/render/MTMathListDisplay;", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "updateAccenteePosition", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTAccentDisplay extends MTDisplay {
    private final MTGlyphDisplay accent;
    private final MTMathListDisplay accentee;

    public final MTGlyphDisplay getAccent() {
        return this.accent;
    }

    public final MTMathListDisplay getAccentee() {
        return this.accentee;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTAccentDisplay(MTGlyphDisplay accent, MTMathListDisplay accentee, NSRange range) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(accent, "accent");
        Intrinsics.checkNotNullParameter(accentee, "accentee");
        Intrinsics.checkNotNullParameter(range, "range");
        this.accent = accent;
        this.accentee = accentee;
        accentee.setPosition(new CGPoint(0.0f, 0.0f, 3, null));
        super.setRange(NSRange.copy$default(range, 0, 0, 3, null));
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        this.accentee.setTextColor(getTextColor());
        this.accent.setTextColor(getTextColor());
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void positionChanged() {
        updateAccenteePosition();
    }

    public final void updateAccenteePosition() {
        this.accentee.setPosition(new CGPoint(getPosition().getX(), getPosition().getY()));
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.accentee.draw(canvas);
        canvas.save();
        canvas.translate(getPosition().getX(), getPosition().getY());
        this.accent.draw(canvas);
        canvas.restore();
    }
}
