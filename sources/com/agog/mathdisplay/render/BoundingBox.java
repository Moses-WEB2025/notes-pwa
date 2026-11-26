package com.agog.mathdisplay.render;

import ch.qos.logback.core.CoreConstants;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFontMathTable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u0004\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007B\u0015\b\u0016\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bB\u0005¢\u0006\u0002\u0010\fJ\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0003J\b\u0010#\u001a\u00020$H\u0016R\u0011\u0010\r\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000f\"\u0004\b\u0016\u0010\u0013R\u001a\u0010\u0017\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0013R\u001a\u0010\u001a\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000f\"\u0004\b\u001c\u0010\u0013R\u0011\u0010\u001d\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u000f¨\u0006%"}, d2 = {"Lcom/agog/mathdisplay/render/BoundingBox;", "", "minX", "", "minY", "maxX", "maxY", "(FFFF)V", "numbers", "", "", "(Ljava/util/List;)V", "()V", Layer.KEY_HEIGHT, "getHeight", "()F", "lowerLeftX", "getLowerLeftX", "setLowerLeftX", "(F)V", "lowerLeftY", "getLowerLeftY", "setLowerLeftY", "upperRightX", "getUpperRightX", "setUpperRightX", "upperRightY", "getUpperRightY", "setUpperRightY", Layer.KEY_WIDTH, "getWidth", "contains", "", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "toString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class BoundingBox {
    private float lowerLeftX;
    private float lowerLeftY;
    private float upperRightX;
    private float upperRightY;

    public BoundingBox() {
    }

    public final float getLowerLeftX() {
        return this.lowerLeftX;
    }

    public final void setLowerLeftX(float f) {
        this.lowerLeftX = f;
    }

    public final float getLowerLeftY() {
        return this.lowerLeftY;
    }

    public final void setLowerLeftY(float f) {
        this.lowerLeftY = f;
    }

    public final float getUpperRightX() {
        return this.upperRightX;
    }

    public final void setUpperRightX(float f) {
        this.upperRightX = f;
    }

    public final float getUpperRightY() {
        return this.upperRightY;
    }

    public final void setUpperRightY(float f) {
        this.upperRightY = f;
    }

    public final float getWidth() {
        return this.upperRightX - this.lowerLeftX;
    }

    public final float getHeight() {
        return this.upperRightY - this.lowerLeftY;
    }

    public BoundingBox(float f, float f2, float f3, float f4) {
        this();
        this.lowerLeftX = f;
        this.lowerLeftY = f2;
        this.upperRightX = f3;
        this.upperRightY = f4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BoundingBox(List<? extends Number> numbers) {
        this();
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        this.lowerLeftX = numbers.get(0).floatValue();
        this.lowerLeftY = numbers.get(1).floatValue();
        this.upperRightX = numbers.get(2).floatValue();
        this.upperRightY = numbers.get(3).floatValue();
    }

    public final boolean contains(float x, float y) {
        return x >= this.lowerLeftX && x <= this.upperRightX && y >= this.lowerLeftY && y <= this.upperRightY;
    }

    public String toString() {
        return "[" + this.lowerLeftX + CoreConstants.COMMA_CHAR + this.lowerLeftY + CoreConstants.COMMA_CHAR + this.upperRightX + CoreConstants.COMMA_CHAR + this.upperRightY + ']';
    }
}
