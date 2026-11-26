package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import com.agog.mathdisplay.parse.NSRange;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u0019R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\"\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001e"}, d2 = {"Lcom/agog/mathdisplay/render/MTMathListDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "displays", "", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "(Ljava/util/List;Lcom/agog/mathdisplay/parse/NSRange;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "subDisplays", "getSubDisplays", "()Ljava/util/List;", "setSubDisplays", "(Ljava/util/List;)V", "type", "Lcom/agog/mathdisplay/render/MTLinePosition;", "getType", "()Lcom/agog/mathdisplay/render/MTLinePosition;", "setType", "(Lcom/agog/mathdisplay/render/MTLinePosition;)V", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "recomputeDimensions", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathListDisplay extends MTDisplay {
    private int index;
    private List<? extends MTDisplay> subDisplays;
    private MTLinePosition type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTMathListDisplay(List<? extends MTDisplay> displays, NSRange range) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(displays, "displays");
        Intrinsics.checkNotNullParameter(range, "range");
        this.type = MTLinePosition.KMTLinePositionRegular;
        this.index = -1;
        this.subDisplays = displays;
        recomputeDimensions();
    }

    public final MTLinePosition getType() {
        return this.type;
    }

    public final void setType(MTLinePosition mTLinePosition) {
        Intrinsics.checkNotNullParameter(mTLinePosition, "<set-?>");
        this.type = mTLinePosition;
    }

    public final List<MTDisplay> getSubDisplays() {
        return this.subDisplays;
    }

    public final void setSubDisplays(List<? extends MTDisplay> list) {
        this.subDisplays = list;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        List<? extends MTDisplay> list = this.subDisplays;
        if (list != null) {
            for (MTDisplay mTDisplay : CollectionsKt.toList(list)) {
                if (mTDisplay.getLocalTextColor() == 0) {
                    mTDisplay.setTextColor(getTextColor());
                } else {
                    mTDisplay.setTextColor(mTDisplay.getLocalTextColor());
                }
            }
        }
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        canvas.translate(getPosition().getX(), getPosition().getY());
        List<? extends MTDisplay> list = this.subDisplays;
        if (list != null) {
            Iterator it = CollectionsKt.toList(list).iterator();
            while (it.hasNext()) {
                ((MTDisplay) it.next()).draw(canvas);
            }
        }
        canvas.restore();
    }

    public final void recomputeDimensions() {
        float f;
        float f2;
        List<? extends MTDisplay> list = this.subDisplays;
        float f3 = 0.0f;
        if (list != null) {
            float f4 = 0.0f;
            f = 0.0f;
            f2 = 0.0f;
            for (MTDisplay mTDisplay : CollectionsKt.toList(list)) {
                float fMax = Math.max(0.0f, mTDisplay.getPosition().getY() + mTDisplay.getAscent());
                if (fMax > f4) {
                    f4 = fMax;
                }
                float fMax2 = Math.max(0.0f, 0 - (mTDisplay.getPosition().getY() - mTDisplay.getDescent()));
                if (fMax2 > f) {
                    f = fMax2;
                }
                float width = mTDisplay.getWidth() + mTDisplay.getPosition().getX();
                if (width > f2) {
                    f2 = width;
                }
            }
            f3 = f4;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        setAscent(f3);
        setDescent(f);
        setWidth(f2);
    }
}
