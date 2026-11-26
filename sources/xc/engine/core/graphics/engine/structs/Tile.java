package xc.engine.core.graphics.engine.structs;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Tile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0019\b\u0016\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u0006\u0010\u000bB\t\b\u0016¢\u0006\u0004\b\u0006\u0010\fJ\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001c"}, d2 = {"Lxc/engine/core/graphics/engine/structs/Tile;", "", "coord", "Lxc/engine/core/graphics/engine/structs/TileCoord;", "bDummy", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/structs/TileCoord;Z)V", "inX", "", "inY", "(II)V", "()V", "getCoord", "()Lxc/engine/core/graphics/engine/structs/TileCoord;", "setCoord", "(Lxc/engine/core/graphics/engine/structs/TileCoord;)V", "getBDummy", "()Z", "setBDummy", "(Z)V", "textureId", "getTextureId", "()I", "setTextureId", "(I)V", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Tile {
    private boolean bDummy;
    private TileCoord coord;
    private int textureId;

    public Tile(TileCoord coord, boolean z) {
        Intrinsics.checkNotNullParameter(coord, "coord");
        this.coord = coord;
        this.bDummy = z;
        this.textureId = -1;
    }

    public /* synthetic */ Tile(TileCoord tileCoord, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(tileCoord, (i & 2) != 0 ? false : z);
    }

    public final boolean getBDummy() {
        return this.bDummy;
    }

    public final TileCoord getCoord() {
        return this.coord;
    }

    public final void setBDummy(boolean z) {
        this.bDummy = z;
    }

    public final void setCoord(TileCoord tileCoord) {
        Intrinsics.checkNotNullParameter(tileCoord, "<set-?>");
        this.coord = tileCoord;
    }

    public final int getTextureId() {
        return this.textureId;
    }

    public final void setTextureId(int i) {
        this.textureId = i;
    }

    public Tile(int i, int i2) {
        this(new TileCoord(i, i2), false, 2, null);
    }

    public Tile() {
        this(new TileCoord(), false, 2, null);
    }

    public String toString() {
        return "Tile[" + this.coord.getX() + ", " + this.coord.getY() + "]";
    }
}
