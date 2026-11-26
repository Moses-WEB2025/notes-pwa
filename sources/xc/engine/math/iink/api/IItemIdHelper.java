package xc.engine.math.iink.api;

import kotlin.Metadata;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001d\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0004H&¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0004H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IItemIdHelper;", "", "getItemsByBlockId", "", "", "blockId", "(Ljava/lang/String;)[Ljava/lang/String;", "getItemParentId", "itemId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IItemIdHelper {
    String getItemParentId(String itemId);

    String[] getItemsByBlockId(String blockId);
}
