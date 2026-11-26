package xc.engine.core.graphics.engine.core;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;

/* JADX INFO: compiled from: BaseEngineStruct.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0004R\u0014\u0010\u0006\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "curveEngine", "getCurveEngine", "()Lxc/engine/core/graphics/engine/MiCurveEngine;", "onScreenResize", "", "acquireScreenTexture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseEngineStruct extends BaseStruct {
    private final MiCurveEngine curveEngine;

    public void onScreenResize() {
    }

    public BaseEngineStruct(MiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.curveEngine = engine;
        engine.getComponents().add(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final MiCurveEngine getCurveEngine() {
        return this.curveEngine;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ScreenTexture acquireScreenTexture() {
        return this.curveEngine.getTexturePool().acquireScreenTexture((int) this.curveEngine.getCamera().getSizeInPixel().x, (int) this.curveEngine.getCamera().getSizeInPixel().y);
    }
}
