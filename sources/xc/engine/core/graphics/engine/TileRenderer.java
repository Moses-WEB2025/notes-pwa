package xc.engine.core.graphics.engine;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.util.a;
import defpackage.R2;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: TileRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u000e\u001a\u00020\u000bH\u0014J<\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u0018J,\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u0018J6\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0002J \u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u001e\u001a\u00020\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lxc/engine/core/graphics/engine/TileRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tileWithFixAngleProgramId", "", "tileWithoutFixAngleProgramId", "swapChannelProgramId", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "drawRect", a.e, "Landroid/graphics/RectF;", "viewportRect", "textureId", "flipY", "", "bShowBorder", "fixAngle", "", "drawFullScreen", "draw", "tileCoordScreen", "drawRGBAtoBGRA", "fbId", "bFlipY", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TileRenderer extends BaseStruct {
    private static final String TAG = "TileRenderer";
    private final ReentrantLock lock = new ReentrantLock();
    private int swapChannelProgramId;
    private int tileWithFixAngleProgramId;
    private int tileWithoutFixAngleProgramId;

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.tileWithFixAngleProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(202, 202);
        this.tileWithoutFixAngleProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2021, 202);
        this.swapChannelProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 322);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.tileWithFixAngleProgramId);
        GLES30.glDeleteProgram(this.tileWithoutFixAngleProgramId);
        GLES30.glDeleteProgram(this.swapChannelProgramId);
    }

    public static /* synthetic */ void drawRect$default(TileRenderer tileRenderer, RectF rectF, RectF rectF2, int i, boolean z, boolean z2, float f, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        if ((i2 & 16) != 0) {
            z2 = false;
        }
        if ((i2 & 32) != 0) {
            f = 0.0f;
        }
        tileRenderer.drawRect(rectF, rectF2, i, z, z2, f);
    }

    public final void drawRect(RectF area, RectF viewportRect, int textureId, boolean flipY, boolean bShowBorder, float fixAngle) {
        Intrinsics.checkNotNullParameter(area, "area");
        Intrinsics.checkNotNullParameter(viewportRect, "viewportRect");
        draw(new RectF(MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, area.left, viewportRect.left, viewportRect.right, 0.0f, 0.0f, 24, null), MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, area.top, viewportRect.top, viewportRect.bottom, 0.0f, 0.0f, 24, null), MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, area.right, viewportRect.left, viewportRect.right, 0.0f, 0.0f, 24, null), MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, area.bottom, viewportRect.top, viewportRect.bottom, 0.0f, 0.0f, 24, null)), textureId, flipY, bShowBorder, fixAngle);
    }

    public static /* synthetic */ void drawFullScreen$default(TileRenderer tileRenderer, int i, boolean z, boolean z2, float f, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        if ((i2 & 8) != 0) {
            f = 0.0f;
        }
        tileRenderer.drawFullScreen(i, z, z2, f);
    }

    public final void drawFullScreen(int textureId, boolean flipY, boolean bShowBorder, float fixAngle) {
        draw(new RectF(-1.0f, -1.0f, 1.0f, 1.0f), textureId, flipY, bShowBorder, fixAngle);
    }

    static /* synthetic */ void draw$default(TileRenderer tileRenderer, RectF rectF, int i, boolean z, boolean z2, float f, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        if ((i2 & 8) != 0) {
            z2 = false;
        }
        if ((i2 & 16) != 0) {
            f = 0.0f;
        }
        tileRenderer.draw(rectF, i, z, z2, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void draw(RectF tileCoordScreen, int textureId, boolean flipY, boolean bShowBorder, float fixAngle) {
        this.lock.lock();
        if (flipY) {
            float f = tileCoordScreen.top;
            tileCoordScreen.top = tileCoordScreen.bottom;
            tileCoordScreen.bottom = f;
        }
        byte b = Math.abs(fixAngle) > 0.01f;
        GLES30.glUseProgram(b != false ? this.tileWithFixAngleProgramId : this.tileWithoutFixAngleProgramId);
        GLES30.glUniform4f(0, tileCoordScreen.left, tileCoordScreen.right, tileCoordScreen.left, tileCoordScreen.right);
        GLES30.glUniform4f(1, tileCoordScreen.top, tileCoordScreen.top, tileCoordScreen.bottom, tileCoordScreen.bottom);
        if (b != false) {
            GLES30.glUniform1f(2, fixAngle);
        }
        GLES30.glUniform1i(3, 10);
        GLES30.glActiveTexture(33994);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, textureId);
        GLES30.glUniform1i(10, bShowBorder ? 1 : 0);
        GLES30.glDrawArrays(5, 0, 4);
        this.lock.unlock();
    }

    public static /* synthetic */ void drawRGBAtoBGRA$default(TileRenderer tileRenderer, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            z = false;
        }
        tileRenderer.drawRGBAtoBGRA(i, i2, z);
    }

    public final void drawRGBAtoBGRA(int fbId, int textureId, boolean bFlipY) {
        GLES30.glBindFramebuffer(36160, fbId);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glUseProgram(this.swapChannelProgramId);
        GLES30.glActiveTexture(33994);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, textureId);
        GLES30.glUniform1i(0, 10);
        GLES30.glUniform1i(1, bFlipY ? 1 : 0);
        GLES30.glDrawArrays(5, 0, 4);
    }
}
