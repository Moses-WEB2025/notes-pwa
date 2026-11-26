package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: ImageRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\bH\u0014J.\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013JD\u0010\f\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0013JX\u0010\u0018\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u00132\b\b\u0002\u0010\u0019\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "normalProgramId", "", "cropProgramId", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "render", "framebufferId", "framebufferRectF", "Landroid/graphics/RectF;", "imgId", "imgRect", "angle", "", "flipY", "", "centerX", "centerY", "renderCrop", "cropRect", "cropState", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ImageRenderer extends BaseStruct {
    private static final String TAG = "ImageRenderer";
    private int cropProgramId;
    private int normalProgramId;

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.normalProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(310, 310);
        this.cropProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(310, 3102);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.normalProgramId);
        GLES30.glDeleteProgram(this.cropProgramId);
    }

    public final void render(int framebufferId, RectF framebufferRectF, int imgId, RectF imgRect, float angle) {
        Intrinsics.checkNotNullParameter(framebufferRectF, "framebufferRectF");
        Intrinsics.checkNotNullParameter(imgRect, "imgRect");
        GLES30.glBindFramebuffer(36160, framebufferId);
        render$default(this, framebufferRectF, imgId, imgRect, angle, false, 0.0f, 0.0f, 112, null);
    }

    public static /* synthetic */ void render$default(ImageRenderer imageRenderer, RectF rectF, int i, RectF rectF2, float f, boolean z, float f2, float f3, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            z = false;
        }
        imageRenderer.render(rectF, i, rectF2, f, z, (i2 & 32) != 0 ? 0.0f : f2, (i2 & 64) != 0 ? 0.0f : f3);
    }

    public final void render(RectF framebufferRectF, int imgId, RectF imgRect, float angle, boolean flipY, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter(framebufferRectF, "framebufferRectF");
        Intrinsics.checkNotNullParameter(imgRect, "imgRect");
        GLES30.glUseProgram(this.normalProgramId);
        float fRemapRange = MiMathUtils.INSTANCE.remapRange(framebufferRectF.left, imgRect.left, imgRect.right, -0.96f, 0.96f);
        float fRemapRange2 = MiMathUtils.INSTANCE.remapRange(framebufferRectF.right, imgRect.left, imgRect.right, -0.96f, 0.96f);
        float fRemapRange3 = MiMathUtils.INSTANCE.remapRange(framebufferRectF.top, imgRect.top, imgRect.bottom, -0.96f, 0.96f);
        GLES30.glUniformMatrix4fv(0, 1, false, MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, fRemapRange, fRemapRange2, MiMathUtils.INSTANCE.remapRange(framebufferRectF.bottom, imgRect.top, imgRect.bottom, -0.96f, 0.96f), fRemapRange3, 0.0f, 0.0f, 48, null), 0);
        GLES30.glUniform1f(1, angle);
        GLES30.glUniform2f(2, imgRect.width(), imgRect.height());
        GLES30.glUniform1i(3, 9);
        if (flipY) {
            GLES30.glUniform1i(4, 1);
        } else {
            if (flipY) {
                throw new NoWhenBranchMatchedException();
            }
            GLES30.glUniform1i(4, 0);
        }
        GLES30.glUniform2f(5, centerX, centerY);
        GLES30.glActiveTexture(33993);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, imgId);
        GLES30.glDrawArrays(5, 0, 4);
    }

    public static /* synthetic */ void renderCrop$default(ImageRenderer imageRenderer, RectF rectF, int i, RectF rectF2, float f, boolean z, float f2, float f3, RectF rectF3, int i2, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            z = false;
        }
        if ((i3 & 32) != 0) {
            f2 = 0.0f;
        }
        if ((i3 & 64) != 0) {
            f3 = 0.0f;
        }
        if ((i3 & 128) != 0) {
            rectF3 = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        }
        if ((i3 & 256) != 0) {
            i2 = 0;
        }
        imageRenderer.renderCrop(rectF, i, rectF2, f, z, f2, f3, rectF3, i2);
    }

    public final synchronized void renderCrop(RectF framebufferRectF, int imgId, RectF imgRect, float angle, boolean flipY, float centerX, float centerY, RectF cropRect, int cropState) {
        Intrinsics.checkNotNullParameter(framebufferRectF, "framebufferRectF");
        Intrinsics.checkNotNullParameter(imgRect, "imgRect");
        Intrinsics.checkNotNullParameter(cropRect, "cropRect");
        GLES30.glUseProgram(this.cropProgramId);
        float fRemapRange = MiMathUtils.INSTANCE.remapRange(framebufferRectF.left, imgRect.left, imgRect.right, -0.96f, 0.96f);
        float fRemapRange2 = MiMathUtils.INSTANCE.remapRange(framebufferRectF.right, imgRect.left, imgRect.right, -0.96f, 0.96f);
        float fRemapRange3 = MiMathUtils.INSTANCE.remapRange(framebufferRectF.top, imgRect.top, imgRect.bottom, -0.96f, 0.96f);
        GLES30.glUniformMatrix4fv(0, 1, false, MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, fRemapRange, fRemapRange2, MiMathUtils.INSTANCE.remapRange(framebufferRectF.bottom, imgRect.top, imgRect.bottom, -0.96f, 0.96f), fRemapRange3, 0.0f, 0.0f, 48, null), 0);
        GLES30.glUniform1f(1, angle);
        GLES30.glUniform2f(2, imgRect.width(), imgRect.height());
        GLES30.glUniform1i(3, 9);
        if (flipY) {
            GLES30.glUniform1i(4, 1);
        } else {
            if (flipY) {
                throw new NoWhenBranchMatchedException();
            }
            GLES30.glUniform1i(4, 0);
        }
        GLES30.glUniform2f(5, centerX, centerY);
        if (MiMathUtils.INSTANCE.isValidCropRect(cropRect)) {
            GLES30.glUniform4f(6, cropRect.left, cropRect.top, cropRect.right, cropRect.bottom);
        } else {
            GLES30.glUniform4f(6, 0.0f, 0.0f, 1.0f, 1.0f);
        }
        GLES30.glUniform1i(7, cropState);
        GLES30.glActiveTexture(33993);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, imgId);
        GLES30.glDrawArrays(5, 0, 4);
    }
}
