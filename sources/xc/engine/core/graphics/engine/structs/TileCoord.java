package xc.engine.core.graphics.engine.structs;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Tile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\t\b\u0016¢\u0006\u0004\b\u0005\u0010\u0007J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\f¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/structs/TileCoord;", "", "inX", "", "inY", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(II)V", "()V", AnimatedProperty.PROPERTY_NAME_X, "getX", "()I", "setX", "(I)V", AnimatedProperty.PROPERTY_NAME_Y, "getY", "setY", "hashCode", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TileCoord {
    private int x;
    private int y;

    public TileCoord(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public final int getX() {
        return this.x;
    }

    public final void setX(int i) {
        this.x = i;
    }

    public final int getY() {
        return this.y;
    }

    public final void setY(int i) {
        this.y = i;
    }

    public TileCoord() {
        this(0, 0);
    }

    public int hashCode() {
        return (this.x * 10000) + this.y;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type xc.engine.core.graphics.engine.structs.TileCoord");
        TileCoord tileCoord = (TileCoord) other;
        return this.x == tileCoord.x && this.y == tileCoord.y;
    }

    public String toString() {
        return "TileCoord[" + this.x + ", " + this.y + "]";
    }
}
