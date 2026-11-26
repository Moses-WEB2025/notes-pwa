package androidx.opengl;

import android.hardware.HardwareBuffer;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.graphics.utils.JniVisible;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: EGLExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/opengl/EGLBindings;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
@JniVisible
public final class EGLBindings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    @JniVisible
    public static final native int nClientWaitSyncKHR(long j, long j2, int i, long j3);

    @JvmStatic
    @JniVisible
    public static final native long nCreateImageFromHardwareBuffer(long j, HardwareBuffer hardwareBuffer);

    @JvmStatic
    @JniVisible
    public static final native long nCreateSyncKHR(long j, int i, int[] iArr);

    @JvmStatic
    @JniVisible
    public static final native boolean nDestroyImageKHR(long j, long j2);

    @JvmStatic
    @JniVisible
    public static final native boolean nDestroySyncKHR(long j, long j2);

    @JvmStatic
    @JniVisible
    public static final native int nDupNativeFenceFDANDROID(long j, long j2);

    @JvmStatic
    @JniVisible
    public static final native boolean nEqualToNativeForeverTimeout(long j);

    @JvmStatic
    @JniVisible
    public static final native boolean nGetSyncAttribKHR(long j, long j2, int i, int[] iArr, int i2);

    @JvmStatic
    @JniVisible
    public static final native void nImageTargetTexture2DOES(int i, long j);

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsDupNativeFenceFDANDROID();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglClientWaitSyncKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglCreateImageKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglCreateSyncKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglDestroyImageKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglDestroySyncKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglGetNativeClientBufferAndroid();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsEglGetSyncAttribKHR();

    @JvmStatic
    @JniVisible
    public static final native boolean nSupportsGlImageTargetTexture2DOES();

    /* JADX INFO: compiled from: EGLExt.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0087 J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0087 J\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0087 J#\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0087 J1\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\fH\u0087 J)\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u0005H\u0087 J\u0019\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0087 J\u0019\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0005H\u0087 J\t\u0010\u001e\u001a\u00020\u0015H\u0087 J\t\u0010\u001f\u001a\u00020\u0015H\u0087 J\t\u0010 \u001a\u00020\u0015H\u0087 J\t\u0010!\u001a\u00020\u0015H\u0087 J\t\u0010\"\u001a\u00020\u0015H\u0087 J\t\u0010#\u001a\u00020\u0015H\u0087 J\t\u0010$\u001a\u00020\u0015H\u0087 J\t\u0010%\u001a\u00020\u0015H\u0087 J\t\u0010&\u001a\u00020\u0015H\u0087 J\u0011\u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u0005H\u0087 ¨\u0006)"}, d2 = {"Landroidx/opengl/EGLBindings$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "nCreateImageFromHardwareBuffer", "", "eglDisplayPtr", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "nImageTargetTexture2DOES", "", TypedValues.AttributesType.S_TARGET, "", "eglImagePtr", "nDupNativeFenceFDANDROID", "syncPtr", "nCreateSyncKHR", "type", "attrs", "", "nGetSyncAttribKHR", "", "attrib", "result", "offset", "nClientWaitSyncKHR", "flags", "timeout", "nDestroySyncKHR", "nDestroyImageKHR", "nSupportsEglGetNativeClientBufferAndroid", "nSupportsDupNativeFenceFDANDROID", "nSupportsEglCreateImageKHR", "nSupportsEglDestroyImageKHR", "nSupportsGlImageTargetTexture2DOES", "nSupportsEglCreateSyncKHR", "nSupportsEglGetSyncAttribKHR", "nSupportsEglClientWaitSyncKHR", "nSupportsEglDestroySyncKHR", "nEqualToNativeForeverTimeout", "timeoutNanos", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @JniVisible
        public final int nClientWaitSyncKHR(long eglDisplayPtr, long syncPtr, int flags, long timeout) {
            return EGLBindings.nClientWaitSyncKHR(eglDisplayPtr, syncPtr, flags, timeout);
        }

        @JvmStatic
        @JniVisible
        public final long nCreateImageFromHardwareBuffer(long eglDisplayPtr, HardwareBuffer hardwareBuffer) {
            return EGLBindings.nCreateImageFromHardwareBuffer(eglDisplayPtr, hardwareBuffer);
        }

        @JvmStatic
        @JniVisible
        public final long nCreateSyncKHR(long eglDisplayPtr, int type, int[] attrs) {
            return EGLBindings.nCreateSyncKHR(eglDisplayPtr, type, attrs);
        }

        @JvmStatic
        @JniVisible
        public final boolean nDestroyImageKHR(long eglDisplayPtr, long eglImagePtr) {
            return EGLBindings.nDestroyImageKHR(eglDisplayPtr, eglImagePtr);
        }

        @JvmStatic
        @JniVisible
        public final boolean nDestroySyncKHR(long eglDisplayPtr, long syncPtr) {
            return EGLBindings.nDestroySyncKHR(eglDisplayPtr, syncPtr);
        }

        @JvmStatic
        @JniVisible
        public final int nDupNativeFenceFDANDROID(long eglDisplayPtr, long syncPtr) {
            return EGLBindings.nDupNativeFenceFDANDROID(eglDisplayPtr, syncPtr);
        }

        @JvmStatic
        @JniVisible
        public final boolean nEqualToNativeForeverTimeout(long timeoutNanos) {
            return EGLBindings.nEqualToNativeForeverTimeout(timeoutNanos);
        }

        @JvmStatic
        @JniVisible
        public final boolean nGetSyncAttribKHR(long eglDisplayPtr, long syncPtr, int attrib, int[] result, int offset) {
            return EGLBindings.nGetSyncAttribKHR(eglDisplayPtr, syncPtr, attrib, result, offset);
        }

        @JvmStatic
        @JniVisible
        public final void nImageTargetTexture2DOES(int target, long eglImagePtr) {
            EGLBindings.nImageTargetTexture2DOES(target, eglImagePtr);
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsDupNativeFenceFDANDROID() {
            return EGLBindings.nSupportsDupNativeFenceFDANDROID();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglClientWaitSyncKHR() {
            return EGLBindings.nSupportsEglClientWaitSyncKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglCreateImageKHR() {
            return EGLBindings.nSupportsEglCreateImageKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglCreateSyncKHR() {
            return EGLBindings.nSupportsEglCreateSyncKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglDestroyImageKHR() {
            return EGLBindings.nSupportsEglDestroyImageKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglDestroySyncKHR() {
            return EGLBindings.nSupportsEglDestroySyncKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglGetNativeClientBufferAndroid() {
            return EGLBindings.nSupportsEglGetNativeClientBufferAndroid();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsEglGetSyncAttribKHR() {
            return EGLBindings.nSupportsEglGetSyncAttribKHR();
        }

        @JvmStatic
        @JniVisible
        public final boolean nSupportsGlImageTargetTexture2DOES() {
            return EGLBindings.nSupportsGlImageTargetTexture2DOES();
        }

        private Companion() {
        }
    }

    static {
        System.loadLibrary("graphics-core");
    }
}
