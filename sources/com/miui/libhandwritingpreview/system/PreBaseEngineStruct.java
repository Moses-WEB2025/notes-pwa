package com.miui.libhandwritingpreview.system;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.ScreenTexture;

/* JADX INFO: compiled from: PreBaseEngineStruct.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0004R\u0014\u0010\u0006\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreBaseEngineStruct;", "Lcom/miui/libhandwritingpreview/system/BaseStruct;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "curveEngine", "getCurveEngine", "()Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", "onScreenResize", "", "acquireScreenTexture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PreBaseEngineStruct extends BaseStruct {
    private final PreMiCurveEngine curveEngine;

    public void onScreenResize() {
    }

    public PreBaseEngineStruct(PreMiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.curveEngine = engine;
        engine.getComponents().add(this);
    }

    protected final PreMiCurveEngine getCurveEngine() {
        return this.curveEngine;
    }

    protected final ScreenTexture acquireScreenTexture() {
        return this.curveEngine.getTexturePool().acquireScreenTexture((int) this.curveEngine.getCamera().getSizeInPixel().x, (int) this.curveEngine.getCamera().getSizeInPixel().y);
    }
}
