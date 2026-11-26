package xc.engine.core.graphics.engine.structs;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import kotlin.Metadata;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: Tile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lxc/engine/core/graphics/engine/structs/CurveTask;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tiles", "Ljava/util/ArrayList;", "Lxc/engine/core/graphics/engine/structs/Tile;", "Lkotlin/collections/ArrayList;", "getTiles", "()Ljava/util/ArrayList;", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "getDataCurve", "()Lxc/engine/data/pagedata/DataCurve;", "setDataCurve", "(Lxc/engine/data/pagedata/DataCurve;)V", "screenTile", "Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "getScreenTile", "()Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "bHasFullScreenTile", "", "getBHasFullScreenTile", "()Z", "setBHasFullScreenTile", "(Z)V", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CurveTask {
    private boolean bHasFullScreenTile;
    private DataCurve dataCurve;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final FullscreenTile screenTile = new FullscreenTile();

    public final ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public final DataCurve getDataCurve() {
        return this.dataCurve;
    }

    public final void setDataCurve(DataCurve dataCurve) {
        this.dataCurve = dataCurve;
    }

    public final FullscreenTile getScreenTile() {
        return this.screenTile;
    }

    public final boolean getBHasFullScreenTile() {
        return this.bHasFullScreenTile;
    }

    public final void setBHasFullScreenTile(boolean z) {
        this.bHasFullScreenTile = z;
    }
}
