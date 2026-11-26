package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: BlendRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\b\u0010\n\u001a\u00020\u0007H\u0014J2\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010J2\u0010\u0012\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010J<\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "programId", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "doNormalBlend", "fb", "bgTexId", "fgTexId", "bFlipBgY", "", "bFlipFgY", "doMultiplyBlend", "doBlend", "type", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BlendRenderer extends BaseStruct {
    private static final String TAG = "BlendRenderer";
    private int programId;

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.programId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 320);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.programId);
    }

    public static /* synthetic */ void doNormalBlend$default(BlendRenderer blendRenderer, int i, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            z = false;
        }
        if ((i4 & 16) != 0) {
            z2 = false;
        }
        blendRenderer.doNormalBlend(i, i2, i3, z, z2);
    }

    public final void doNormalBlend(int fb, int bgTexId, int fgTexId, boolean bFlipBgY, boolean bFlipFgY) {
        doBlend(0, fb, bgTexId, fgTexId, bFlipBgY, bFlipFgY);
    }

    public static /* synthetic */ void doMultiplyBlend$default(BlendRenderer blendRenderer, int i, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            z = false;
        }
        if ((i4 & 16) != 0) {
            z2 = false;
        }
        blendRenderer.doMultiplyBlend(i, i2, i3, z, z2);
    }

    public final void doMultiplyBlend(int fb, int bgTexId, int fgTexId, boolean bFlipBgY, boolean bFlipFgY) {
        doBlend(1, fb, bgTexId, fgTexId, bFlipBgY, bFlipFgY);
    }

    static /* synthetic */ void doBlend$default(BlendRenderer blendRenderer, int i, int i2, int i3, int i4, boolean z, boolean z2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z = false;
        }
        if ((i5 & 32) != 0) {
            z2 = false;
        }
        blendRenderer.doBlend(i, i2, i3, i4, z, z2);
    }

    private final void doBlend(int type, int fb, int bgTexId, int fgTexId, boolean bFlipBgY, boolean bFlipFgY) {
        GLES30.glBindFramebuffer(36160, fb);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glUseProgram(this.programId);
        GLES30.glUniform1i(0, 1);
        GLES30.glUniform1i(1, 2);
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, bgTexId);
        GLES30.glActiveTexture(33986);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, fgTexId);
        GLES30.glUniform1i(3, bFlipBgY ? 1 : 0);
        GLES30.glUniform1i(4, bFlipFgY ? 1 : 0);
        GLES30.glUniform1i(5, type);
        GLES30.glDrawArrays(5, 0, 4);
    }
}
