package xc.engine.math.iink.api;

import kotlin.Metadata;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IInkEngineFactory;", "", "createEngine", "Lxc/engine/math/iink/api/IInkEngine;", "createPointerEvent", "Lxc/engine/math/iink/api/IPointerEvent;", "createItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "engine", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IInkEngineFactory {
    IInkEngine createEngine();

    IItemIdHelper createItemIdHelper(IInkEngine engine);

    IPointerEvent createPointerEvent();
}
