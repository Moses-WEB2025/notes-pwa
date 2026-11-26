package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: DebugDrawRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\b\u0010\f\u001a\u00020\tH\u0014J\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/DebugDrawRenderer;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "shapeProgramId", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "debugDrawRect", "element", "Lxc/engine/core/graphics/engine/renderers/DebugElement;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DebugDrawRenderer extends BaseEngineStruct {
    private int shapeProgramId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DebugDrawRenderer(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.shapeProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(211, 3070);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.shapeProgramId);
    }

    public final void debugDrawRect(DebugElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.getBActive() && element.isValid()) {
            RectF screenAreaSS = getCurveEngine().getScreenAreaSS();
            float f = (element.getSize().x * 0.5f) + 4.0f;
            float f2 = (element.getSize().y * 0.5f) + 4.0f;
            float fRemapRange = MiMathUtils.INSTANCE.remapRange(element.getCenter().x - f, screenAreaSS.left, screenAreaSS.right, -1.0f, 1.0f);
            float fRemapRange2 = MiMathUtils.INSTANCE.remapRange(element.getCenter().x + f, screenAreaSS.left, screenAreaSS.right, -1.0f, 1.0f);
            float fRemapRange3 = MiMathUtils.INSTANCE.remapRange(element.getCenter().y - f2, screenAreaSS.top, screenAreaSS.bottom, -1.0f, 1.0f);
            float fRemapRange4 = MiMathUtils.INSTANCE.remapRange(element.getCenter().y + f2, screenAreaSS.top, screenAreaSS.bottom, -1.0f, 1.0f);
            GLES30.glUseProgram(this.shapeProgramId);
            GLES30.glUniform4f(0, fRemapRange, fRemapRange3, fRemapRange2, fRemapRange4);
            GLES30.glUniform4f(1, element.getColor().getR(), element.getColor().getG(), element.getColor().getB(), element.getColor().getA());
            GLES30.glUniform4f(2, element.getCenter().x, element.getCenter().y, element.getSize().x + 8.0f, element.getSize().y + 8.0f);
            GLES30.glUniform4f(3, element.getCenter().x, element.getCenter().y, element.getSize().x, element.getSize().y);
            GLES30.glUniform1f(4, element.getOutlineWidth());
            if (element.getDrawType() == EDebugDrawType.Outline) {
                GLES30.glUniform1i(5, 0);
            } else {
                GLES30.glUniform1i(5, 1);
            }
            if (element.getShape() == EDebugShape.Rect) {
                GLES30.glUniform1i(6, 0);
            } else {
                GLES30.glUniform1i(6, 1);
            }
            GLES30.glDrawArrays(5, 0, 4);
        }
    }
}
