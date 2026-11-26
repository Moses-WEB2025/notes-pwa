package xc.engine.math.iink.api;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StubInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lxc/engine/math/iink/api/StubItemIdHelper;", "Lxc/engine/math/iink/api/IItemIdHelper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getItemsByBlockId", "", "", "blockId", "(Ljava/lang/String;)[Ljava/lang/String;", "getItemParentId", "itemId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StubItemIdHelper implements IItemIdHelper {
    @Override // xc.engine.math.iink.api.IItemIdHelper
    public String getItemParentId(String itemId) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        return null;
    }

    @Override // xc.engine.math.iink.api.IItemIdHelper
    public String[] getItemsByBlockId(String blockId) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        return null;
    }
}
