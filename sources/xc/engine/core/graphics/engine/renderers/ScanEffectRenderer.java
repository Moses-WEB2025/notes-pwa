package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.systems.MathSystem;

/* JADX INFO: compiled from: ScanEffectRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\b\u0010\f\u001a\u00020\tH\u0014J\"\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\n\u0010\u0010\u001a\u00060\u0011R\u00020\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/ScanEffectRenderer;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "programId", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "updateEffect", "targetFBO", "inputTexture", "scanEffectInfo", "Lxc/engine/core/graphics/engine/systems/MathSystem$ScannerEffectInfo;", "Lxc/engine/core/graphics/engine/systems/MathSystem;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ScanEffectRenderer extends BaseEngineStruct {
    private static final String TAG = "ScanEffectRenderer";
    private int programId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScanEffectRenderer(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.programId = GLUtils.INSTANCE.createShaderProgramFromSStore(330, 330);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.programId);
    }

    public final void updateEffect(int targetFBO, int inputTexture, MathSystem.ScannerEffectInfo scanEffectInfo) {
        Intrinsics.checkNotNullParameter(scanEffectInfo, "scanEffectInfo");
        GLES30.glBindFramebuffer(36160, targetFBO);
        GLES30.glEnable(R2.color.common_folder_dialog_bg);
        if (scanEffectInfo.getCurrentProcess() >= scanEffectInfo.getResultEndProcess()) {
            getCurveEngine().getMathSystem().moveCurveToRenderList();
        }
        GLES30.glScissor((int) scanEffectInfo.getMathRect().left, (int) scanEffectInfo.getMathRect().top, (int) scanEffectInfo.getMathRect().width(), (int) scanEffectInfo.getMathRect().height());
        GLES30.glUseProgram(this.programId);
        GLES30.glUniform1i(0, 0);
        GLES30.glActiveTexture(33984);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, inputTexture);
        GLES30.glUniform4f(1, scanEffectInfo.getColor1()[0], scanEffectInfo.getColor1()[1], scanEffectInfo.getColor1()[2], scanEffectInfo.getColor1()[3]);
        GLES30.glUniform4f(2, scanEffectInfo.getColor2()[0], scanEffectInfo.getColor2()[1], scanEffectInfo.getColor2()[2], scanEffectInfo.getShowWidth2());
        GLES30.glUniform4f(3, scanEffectInfo.getColor3()[0], scanEffectInfo.getColor3()[1], scanEffectInfo.getColor3()[2], scanEffectInfo.getShadowR());
        GLES30.glUniform4f(4, scanEffectInfo.getCurrentProcess(), scanEffectInfo.getShowWidth1(), scanEffectInfo.getTextureChannel(), scanEffectInfo.getShadowL());
        GLES30.glDrawArrays(5, 0, 4);
        GLES30.glDisable(R2.color.common_folder_dialog_bg);
    }
}
