package xc.engine.math.iink.bean;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: BoundingBox.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\f¨\u0006\u001f"}, d2 = {"Lxc/engine/math/iink/bean/BoundingBox;", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, Layer.KEY_WIDTH, Layer.KEY_HEIGHT, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FFFF)V", "getX", "()F", "setX", "(F)V", "getY", "setY", "getWidth", "setWidth", "getHeight", "setHeight", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class BoundingBox {
    private float height;
    private float width;
    private float x;
    private float y;

    public static /* synthetic */ BoundingBox copy$default(BoundingBox boundingBox, float f, float f2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = boundingBox.x;
        }
        if ((i & 2) != 0) {
            f2 = boundingBox.y;
        }
        if ((i & 4) != 0) {
            f3 = boundingBox.width;
        }
        if ((i & 8) != 0) {
            f4 = boundingBox.height;
        }
        return boundingBox.copy(f, f2, f3, f4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getX() {
        return this.x;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getY() {
        return this.y;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getWidth() {
        return this.width;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getHeight() {
        return this.height;
    }

    public final BoundingBox copy(float x, float y, float width, float height) {
        return new BoundingBox(x, y, width, height);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BoundingBox)) {
            return false;
        }
        BoundingBox boundingBox = (BoundingBox) other;
        return Float.compare(this.x, boundingBox.x) == 0 && Float.compare(this.y, boundingBox.y) == 0 && Float.compare(this.width, boundingBox.width) == 0 && Float.compare(this.height, boundingBox.height) == 0;
    }

    public int hashCode() {
        return (((((Float.hashCode(this.x) * 31) + Float.hashCode(this.y)) * 31) + Float.hashCode(this.width)) * 31) + Float.hashCode(this.height);
    }

    public String toString() {
        return "BoundingBox(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ")";
    }

    public BoundingBox(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getHeight() {
        return this.height;
    }

    public final void setHeight(float f) {
        this.height = f;
    }
}
