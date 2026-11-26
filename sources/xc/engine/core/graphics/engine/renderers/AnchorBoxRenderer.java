package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: AnchorBoxRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 )2\u00020\u0001:\u0001)B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0014J\u001e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bJ\u001e\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bJ\u0006\u0010\u001b\u001a\u00020\u0012JR\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\b\b\u0002\u0010&\u001a\u00020\b2\b\b\u0002\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\bR\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/AnchorBoxRenderer;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "anchorColor", "", "", "[Ljava/lang/Float;", "edgeLineColor", "controlPointColor", "offset", "Landroid/graphics/PointF;", "lineProgramId", "", "anchorProgramId", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "setLineColor", Constants.RANDOM_LONG, CloudPushConstants.WATERMARK_TYPE.GLOBAL, "b", "setControlPointColor", "resetOffsetInfo", "render", "vertexBuffer", "Ljava/nio/FloatBuffer;", "vertexCount", "indexBuffer", "Ljava/nio/ShortBuffer;", "indexCount", "isShowEditPoint", "", "isClose", "offsetX", "offsetY", "editPointSize", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AnchorBoxRenderer extends BaseEngineStruct {
    private static final String TAG = "AnchorBoxRenderer";
    private final Float[] anchorColor;
    private int anchorProgramId;
    private final Float[] controlPointColor;
    private final Float[] edgeLineColor;
    private int lineProgramId;
    private final PointF offset;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchorBoxRenderer(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        Float fValueOf = Float.valueOf(0.05098f);
        Float fValueOf2 = Float.valueOf(0.51765f);
        Float fValueOf3 = Float.valueOf(1.0f);
        this.anchorColor = new Float[]{fValueOf, fValueOf2, fValueOf3, fValueOf3};
        this.edgeLineColor = new Float[]{fValueOf, fValueOf2, fValueOf3, fValueOf3};
        Float fValueOf4 = Float.valueOf(0.0f);
        this.controlPointColor = new Float[]{fValueOf4, fValueOf3, fValueOf4, fValueOf3};
        this.offset = new PointF(0.0f, 0.0f);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.lineProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(201, 201);
        this.anchorProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(312, 312);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.lineProgramId);
        GLES30.glDeleteProgram(this.anchorProgramId);
    }

    public final void setLineColor(float r, float g, float b) {
        this.edgeLineColor[0] = Float.valueOf(r);
        this.edgeLineColor[1] = Float.valueOf(g);
        this.edgeLineColor[2] = Float.valueOf(b);
    }

    public final void setControlPointColor(float r, float g, float b) {
        this.anchorColor[0] = Float.valueOf(r);
        this.anchorColor[1] = Float.valueOf(g);
        this.anchorColor[2] = Float.valueOf(b);
    }

    public final void resetOffsetInfo() {
        this.offset.set(0.0f, 0.0f);
    }

    public static /* synthetic */ void render$default(AnchorBoxRenderer anchorBoxRenderer, FloatBuffer floatBuffer, int i, ShortBuffer shortBuffer, int i2, boolean z, boolean z2, float f, float f2, float f3, int i3, Object obj) {
        if ((i3 & 64) != 0) {
            f = 0.0f;
        }
        if ((i3 & 128) != 0) {
            f2 = 0.0f;
        }
        anchorBoxRenderer.render(floatBuffer, i, shortBuffer, i2, z, z2, f, f2, f3);
    }

    public final void render(FloatBuffer vertexBuffer, int vertexCount, ShortBuffer indexBuffer, int indexCount, boolean isShowEditPoint, boolean isClose, float offsetX, float offsetY, float editPointSize) {
        Intrinsics.checkNotNullParameter(vertexBuffer, "vertexBuffer");
        Intrinsics.checkNotNullParameter(indexBuffer, "indexBuffer");
        this.offset.set(offsetX, offsetY);
        float[] fArrCalculateMatrixTranslateM2D = MiMathUtils.INSTANCE.calculateMatrixTranslateM2D(0.0f, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel(), 0.0f, this.offset.x, this.offset.y);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        if (indexCount != 0) {
            GLES30.glEnable(R2.color.audio_share_time_color_green);
            GLES30.glUseProgram(this.lineProgramId);
            GLES30.glUniformMatrix4fv(0, 1, false, fArrCalculateMatrixTranslateM2D, 0);
            GLES30.glUniform4f(2, this.edgeLineColor[0].floatValue(), this.edgeLineColor[1].floatValue(), this.edgeLineColor[2].floatValue(), this.edgeLineColor[3].floatValue());
            vertexBuffer.position(0);
            GLES30.glEnableVertexAttribArray(0);
            FloatBuffer floatBuffer = vertexBuffer;
            GLES30.glVertexAttribPointer(0, 2, R2.color.miuix_default_color_on_secondary_light, false, 16, (Buffer) floatBuffer);
            GLES30.glEnableVertexAttribArray(1);
            vertexBuffer.position(2);
            Unit unit = Unit.INSTANCE;
            GLES30.glVertexAttribPointer(1, 2, R2.color.miuix_default_color_on_secondary_light, false, 16, (Buffer) floatBuffer);
            indexBuffer.position(0);
            GLES30.glDrawElements(5, indexCount, R2.color.miuix_default_color_on_primary_dark, indexBuffer);
            GLES30.glDisableVertexAttribArray(1);
        }
        GLES30.glUseProgram(this.anchorProgramId);
        GLES30.glUniformMatrix4fv(0, 1, false, fArrCalculateMatrixTranslateM2D, 0);
        GLES30.glUniformMatrix2fv(3, 1, false, getCurveEngine().getSelectSystem().getAnchorBoxDrawer().getEditPointRotateM(), 0);
        GLES30.glUniform4f(2, this.anchorColor[0].floatValue(), this.anchorColor[1].floatValue(), this.anchorColor[2].floatValue(), this.anchorColor[3].floatValue());
        GLES30.glUniform1f(1, editPointSize);
        GLES30.glEnableVertexAttribArray(0);
        if (isClose) {
            vertexBuffer.position(vertexCount * 4);
            GLES30.glVertexAttribPointer(0, 2, R2.color.miuix_default_color_on_secondary_light, false, 8, (Buffer) vertexBuffer);
            GLES30.glDrawArrays(0, 0, 8);
        } else {
            vertexBuffer.position((vertexCount * 4) + 2);
            GLES30.glVertexAttribPointer(0, 2, R2.color.miuix_default_color_on_secondary_light, false, 16, (Buffer) vertexBuffer);
            GLES30.glDrawArrays(0, 0, 2);
        }
        if (isShowEditPoint && isClose) {
            vertexBuffer.position((vertexCount * 4) + 16);
            GLES30.glVertexAttribPointer(0, 2, R2.color.miuix_default_color_on_secondary_light, false, 8, (Buffer) vertexBuffer);
            GLES30.glUniform4f(2, this.controlPointColor[0].floatValue(), this.controlPointColor[1].floatValue(), this.controlPointColor[2].floatValue(), this.controlPointColor[3].floatValue());
            GLES30.glDrawArrays(0, 0, 1);
        }
    }
}
