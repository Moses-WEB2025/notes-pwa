package xc.engine.math.iink.api;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StubInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016¨\u0006\u000b"}, d2 = {"Lxc/engine/math/iink/api/StubInkEngineFactory;", "Lxc/engine/math/iink/api/IInkEngineFactory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createEngine", "Lxc/engine/math/iink/api/IInkEngine;", "createPointerEvent", "Lxc/engine/math/iink/api/IPointerEvent;", "createItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "engine", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StubInkEngineFactory implements IInkEngineFactory {
    @Override // xc.engine.math.iink.api.IInkEngineFactory
    public IInkEngine createEngine() {
        return new StubInkEngine();
    }

    @Override // xc.engine.math.iink.api.IInkEngineFactory
    public IPointerEvent createPointerEvent() {
        return new StubPointerEvent();
    }

    @Override // xc.engine.math.iink.api.IInkEngineFactory
    public IItemIdHelper createItemIdHelper(IInkEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        return new StubItemIdHelper();
    }
}
