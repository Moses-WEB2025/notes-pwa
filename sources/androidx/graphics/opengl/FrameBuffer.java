package androidx.graphics.opengl;

import android.hardware.HardwareBuffer;
import android.opengl.GLES20;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.opengl.EGLExt;
import androidx.opengl.EGLImageKHR;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: FrameBuffer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u0019H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Landroidx/graphics/opengl/FrameBuffer;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "egl", "Landroidx/graphics/opengl/egl/EGLSpec;", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/opengl/egl/EGLSpec;Landroid/hardware/HardwareBuffer;)V", "getHardwareBuffer", "()Landroid/hardware/HardwareBuffer;", "eglImage", "Landroidx/opengl/EGLImageKHR;", "texture", "", "value", "frameBuffer", "getFrameBuffer$libXCEngine_release", "()I", "", "isClosed", "()Z", "tmpBuffer", "", "makeCurrent", "", SplitLayout.TAG_CLOSE, "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameBuffer implements AutoCloseable {
    private final EGLSpec egl;
    private EGLImageKHR eglImage;
    private int frameBuffer;
    private final HardwareBuffer hardwareBuffer;
    private boolean isClosed;
    private int texture;
    private final int[] tmpBuffer;

    public FrameBuffer(EGLSpec egl, HardwareBuffer hardwareBuffer) {
        Intrinsics.checkNotNullParameter(egl, "egl");
        Intrinsics.checkNotNullParameter(hardwareBuffer, "hardwareBuffer");
        this.egl = egl;
        this.hardwareBuffer = hardwareBuffer;
        this.texture = -1;
        this.frameBuffer = -1;
        int[] iArr = new int[1];
        this.tmpBuffer = iArr;
        EGLImageKHR eGLImageKHREglCreateImageFromHardwareBuffer = egl.eglCreateImageFromHardwareBuffer(hardwareBuffer);
        if (eGLImageKHREglCreateImageFromHardwareBuffer == null) {
            throw new IllegalArgumentException("Unable to create EGLImage from HardwareBuffer");
        }
        this.eglImage = eGLImageKHREglCreateImageFromHardwareBuffer;
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        this.texture = i;
        GLES20.glBindTexture(R2.color.m3_ref_palette_primary80, i);
        EGLExt.INSTANCE.glEGLImageTargetTexture2DOES(R2.color.m3_ref_palette_primary80, eGLImageKHREglCreateImageFromHardwareBuffer);
        GLES20.glGenFramebuffers(1, iArr, 0);
        int i2 = iArr[0];
        this.frameBuffer = i2;
        XLog.v("X", "Gen HWFrameBuffer FBOId:[%d] textureId:[%d]", Integer.valueOf(i2), Integer.valueOf(this.texture));
    }

    public final HardwareBuffer getHardwareBuffer() {
        return this.hardwareBuffer;
    }

    /* JADX INFO: renamed from: getFrameBuffer$libXCEngine_release, reason: from getter */
    public final int getFrameBuffer() {
        return this.frameBuffer;
    }

    /* JADX INFO: renamed from: isClosed, reason: from getter */
    public final boolean getIsClosed() {
        return this.isClosed;
    }

    public final void makeCurrent() {
        if (this.isClosed) {
            return;
        }
        GLES20.glBindFramebuffer(36160, this.frameBuffer);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.color.m3_ref_palette_primary80, this.texture, 0);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.isClosed) {
            return;
        }
        XLog.v("X", "delete HWFrameBuffer FBOId:%d textureId:%d", Integer.valueOf(this.frameBuffer), Integer.valueOf(this.texture));
        int[] iArr = this.tmpBuffer;
        iArr[0] = this.frameBuffer;
        GLES20.glDeleteFramebuffers(1, iArr, 0);
        this.frameBuffer = -1;
        int[] iArr2 = this.tmpBuffer;
        iArr2[0] = this.texture;
        GLES20.glDeleteTextures(1, iArr2, 0);
        this.texture = -1;
        EGLImageKHR eGLImageKHR = this.eglImage;
        if (eGLImageKHR != null) {
            this.egl.eglDestroyImageKHR(eGLImageKHR);
        }
        this.eglImage = null;
        this.hardwareBuffer.close();
        this.isClosed = true;
    }
}
