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
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: PageMaskRender.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\b\u0010\f\u001a\u00020\tH\u0014J&\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/PageMaskRender;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "programId", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "render", "viewportArea", "Landroid/graphics/RectF;", "pageArea", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "cornerRadius", "", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PageMaskRender extends BaseEngineStruct {
    private static final String TAG = "PageMaskRender";
    private int programId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageMaskRender(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.programId = GLUtils.INSTANCE.createShaderProgramFromSStore(210, 321);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.programId);
    }

    public final void render(RectF viewportArea, RectF pageArea, Color bgColor, float cornerRadius) {
        Intrinsics.checkNotNullParameter(viewportArea, "viewportArea");
        Intrinsics.checkNotNullParameter(pageArea, "pageArea");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        GLES30.glUseProgram(this.programId);
        GLES30.glUniform4f(0, viewportArea.left, viewportArea.top, viewportArea.right, viewportArea.bottom);
        GLES30.glUniform4f(1, pageArea.left, pageArea.top, pageArea.right, pageArea.bottom);
        GLES30.glUniform4f(2, bgColor.getR(), bgColor.getG(), bgColor.getB(), bgColor.getA());
        GLES30.glUniform1f(3, cornerRadius);
        GLES30.glUniform1f(5, 0.0f);
        GLES30.glDrawArrays(5, 0, 4);
    }
}
