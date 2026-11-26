package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: DashedLineRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000fH\u0014J\u001e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\bJ\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\rJ*\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\r2\b\b\u0002\u0010\u001e\u001a\u00020\b2\b\b\u0002\u0010\u001f\u001a\u00020\bR\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/DashedLineRenderer;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "color", "", "", "[Ljava/lang/Float;", "dashedPointCount", "emptyPointCount", "programId", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "setLineColor", Constants.RANDOM_LONG, CloudPushConstants.WATERMARK_TYPE.GLOBAL, "b", "setLineParameter", "inDashedPointCount", "inEmptyPointCount", "render", "vertexBuffer", "Ljava/nio/FloatBuffer;", "vertexCount", "offsetX", "offsetY", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DashedLineRenderer extends BaseEngineStruct {
    private static final String TAG = "DashedLineRenderer";
    private final Float[] color;
    private float dashedPointCount;
    private float emptyPointCount;
    private int programId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DashedLineRenderer(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.color = new Float[]{Float.valueOf(0.019607844f), Float.valueOf(0.2784314f), Float.valueOf(0.84313726f), Float.valueOf(1.0f)};
        this.dashedPointCount = 20.0f;
        this.emptyPointCount = 16.0f;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.programId = GLUtils.INSTANCE.createShaderProgramFromSStore(311, 311);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.programId);
    }

    public final void setLineColor(float r, float g, float b) {
        this.color[0] = Float.valueOf(r);
        this.color[1] = Float.valueOf(g);
        this.color[2] = Float.valueOf(b);
    }

    public final void setLineParameter(int inDashedPointCount, int inEmptyPointCount) {
        this.dashedPointCount = inDashedPointCount;
        this.emptyPointCount = inEmptyPointCount;
    }

    public static /* synthetic */ void render$default(DashedLineRenderer dashedLineRenderer, FloatBuffer floatBuffer, int i, float f, float f2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            f = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f2 = 0.0f;
        }
        dashedLineRenderer.render(floatBuffer, i, f, f2);
    }

    public final void render(FloatBuffer vertexBuffer, int vertexCount, float offsetX, float offsetY) {
        Intrinsics.checkNotNullParameter(vertexBuffer, "vertexBuffer");
        float[] fArrCalculateMatrix$default = MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, 0.0f, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel(), 0.0f, 0.0f, 0.0f, 48, null);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        GLES30.glUseProgram(this.programId);
        GLES30.glUniformMatrix4fv(0, 1, false, fArrCalculateMatrix$default, 0);
        GLES30.glUniform4f(1, offsetX, offsetY, this.dashedPointCount, this.emptyPointCount);
        GLES30.glUniform1f(2, 6.0f);
        GLES30.glUniform4f(4, this.color[0].floatValue(), this.color[1].floatValue(), this.color[2].floatValue(), this.color[3].floatValue());
        vertexBuffer.position(0);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, R2.color.miuix_default_color_on_secondary_light, false, 8, (Buffer) vertexBuffer);
        GLES30.glDrawArrays(0, 0, vertexCount);
    }
}
