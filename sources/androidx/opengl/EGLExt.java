package androidx.opengl;

import android.hardware.HardwareBuffer;
import android.opengl.EGLDisplay;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.graphics.opengl.egl.EGLConfigAttributes;
import androidx.hardware.SyncFenceCompat;
import androidx.hardware.SyncFenceV19;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationTarget;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: EGLExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/opengl/EGLExt;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EGLExt {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String EGL_ANDROID_CLIENT_BUFFER = "EGL_ANDROID_get_native_client_buffer";
    public static final String EGL_ANDROID_IMAGE_NATIVE_BUFFER = "EGL_ANDROID_image_native_buffer";
    public static final String EGL_ANDROID_NATIVE_FENCE_SYNC = "EGL_ANDROID_native_fence_sync";
    public static final int EGL_CONDITION_SATISFIED_KHR = 12534;
    public static final String EGL_EXT_BUFFER_AGE = "EGL_EXT_buffer_age";
    public static final String EGL_EXT_GL_COLORSPACE_BT2020_PQ = "EGL_EXT_gl_colorspace_bt2020_pq";
    public static final String EGL_EXT_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH = "EGL_EXT_gl_colorspace_display_p3_passthrough";
    public static final String EGL_EXT_GL_COLORSPACE_SCRGB = "EGL_EXT_gl_colorspace_scrgb";
    public static final String EGL_EXT_PIXEL_FORMAT_FLOAT = "EGL_EXT_pixel_format_float";
    public static final int EGL_FALSE = 0;
    public static final long EGL_FOREVER_KHR = -1;
    public static final String EGL_IMG_CONTEXT_PRIORITY = "EGL_IMG_context_priority";
    public static final String EGL_KHR_FENCE_SYNC = "EGL_KHR_fence_sync";
    public static final String EGL_KHR_GL_COLORSPACE = "EGL_KHR_gl_colorspace";
    public static final String EGL_KHR_IMAGE = "EGL_KHR_image";
    public static final String EGL_KHR_IMAGE_BASE = "EGL_KHR_image_base";
    public static final String EGL_KHR_NO_CONFIG_CONTEXT = "EGL_KHR_no_config_context";
    public static final String EGL_KHR_PARTIAL_UPDATE = "EGL_KHR_partial_update";
    public static final String EGL_KHR_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    public static final String EGL_KHR_SWAP_BUFFERS_WITH_DAMAGE = "EGL_KHR_swap_buffers_with_damage";
    public static final String EGL_KHR_WAIT_SYNC = "EGL_KHR_wait_sync";
    public static final int EGL_SIGNALED_KHR = 12530;
    public static final int EGL_SYNC_CONDITION_KHR = 12536;
    public static final int EGL_SYNC_FENCE_KHR = 12537;
    public static final int EGL_SYNC_FLUSH_COMMANDS_BIT_KHR = 1;
    public static final int EGL_SYNC_NATIVE_FENCE_ANDROID = 12612;
    public static final int EGL_SYNC_PRIOR_COMMANDS_COMPLETE_KHR = 12528;
    public static final int EGL_SYNC_STATUS_KHR = 12529;
    public static final int EGL_SYNC_TYPE_KHR = 12535;
    public static final int EGL_TIMEOUT_EXPIRED_KHR = 12533;
    public static final int EGL_TRUE = 1;
    public static final int EGL_UNSIGNALED_KHR = 12531;

    @JvmStatic
    public static final int eglClientWaitSyncKHR(EGLDisplay eGLDisplay, EGLSyncKHR eGLSyncKHR, int i, long j) {
        return INSTANCE.eglClientWaitSyncKHR(eGLDisplay, eGLSyncKHR, i, j);
    }

    @JvmStatic
    public static final EGLImageKHR eglCreateImageFromHardwareBuffer(EGLDisplay eGLDisplay, HardwareBuffer hardwareBuffer) {
        return INSTANCE.eglCreateImageFromHardwareBuffer(eGLDisplay, hardwareBuffer);
    }

    @JvmStatic
    public static final EGLSyncKHR eglCreateSyncKHR(EGLDisplay eGLDisplay, @Companion.EGLFenceType int i, EGLConfigAttributes eGLConfigAttributes) {
        return INSTANCE.eglCreateSyncKHR(eGLDisplay, i, eGLConfigAttributes);
    }

    @JvmStatic
    public static final boolean eglDestroyImageKHR(EGLDisplay eGLDisplay, EGLImageKHR eGLImageKHR) {
        return INSTANCE.eglDestroyImageKHR(eGLDisplay, eGLImageKHR);
    }

    @JvmStatic
    public static final boolean eglDestroySyncKHR(EGLDisplay eGLDisplay, EGLSyncKHR eGLSyncKHR) {
        return INSTANCE.eglDestroySyncKHR(eGLDisplay, eGLSyncKHR);
    }

    @JvmStatic
    public static final boolean eglGetSyncAttribKHR(EGLDisplay eGLDisplay, EGLSyncKHR eGLSyncKHR, @Companion.EGLSyncAttribute int i, int[] iArr, int i2) {
        return INSTANCE.eglGetSyncAttribKHR(eGLDisplay, eGLSyncKHR, i, iArr, i2);
    }

    @JvmStatic
    public static final void glEGLImageTargetTexture2DOES(int i, EGLImageKHR eGLImageKHR) {
        INSTANCE.glEGLImageTargetTexture2DOES(i, eGLImageKHR);
    }

    @JvmStatic
    public static final Set<String> parseExtensions(String str) {
        return INSTANCE.parseExtensions(str);
    }

    private EGLExt() {
    }

    /* JADX INFO: compiled from: EGLExt.kt */
    @Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0003KLMB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0007J\u0018\u0010-\u001a\u00020.2\u0006\u0010)\u001a\u00020*2\u0006\u0010/\u001a\u00020(H\u0007J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00182\u0006\u0010/\u001a\u00020(H\u0007J&\u00103\u001a\u0004\u0018\u0001042\u0006\u0010)\u001a\u00020*2\b\b\u0001\u00105\u001a\u00020\u00182\b\u00106\u001a\u0004\u0018\u000107H\u0007J2\u00108\u001a\u00020.2\u0006\u0010)\u001a\u00020*2\u0006\u00109\u001a\u0002042\b\b\u0001\u0010:\u001a\u00020\u00182\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0018H\u0007J-\u0010>\u001a\u00070\u0018¢\u0006\u0002\b?2\u0006\u0010)\u001a\u00020*2\u0006\u00109\u001a\u0002042\u0006\u0010@\u001a\u00020\u00182\u0006\u0010A\u001a\u00020!H\u0007J\u001d\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020*2\u0006\u00109\u001a\u000204H\u0001¢\u0006\u0002\bEJ\u0018\u0010F\u001a\u00020.2\u0006\u0010)\u001a\u00020*2\u0006\u0010G\u001a\u000204H\u0007J\u0016\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00050I2\u0006\u0010J\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Landroidx/opengl/EGLExt$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "EGL_EXT_BUFFER_AGE", "", "EGL_KHR_PARTIAL_UPDATE", "EGL_KHR_SWAP_BUFFERS_WITH_DAMAGE", "EGL_KHR_GL_COLORSPACE", "EGL_KHR_NO_CONFIG_CONTEXT", "EGL_EXT_PIXEL_FORMAT_FLOAT", "EGL_EXT_GL_COLORSPACE_SCRGB", "EGL_EXT_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH", "EGL_EXT_GL_COLORSPACE_BT2020_PQ", "EGL_IMG_CONTEXT_PRIORITY", "EGL_KHR_SURFACELESS_CONTEXT", "EGL_KHR_FENCE_SYNC", "EGL_KHR_WAIT_SYNC", "EGL_ANDROID_NATIVE_FENCE_SYNC", "EGL_ANDROID_IMAGE_NATIVE_BUFFER", "EGL_KHR_IMAGE_BASE", "EGL_ANDROID_CLIENT_BUFFER", "EGL_KHR_IMAGE", "EGL_SYNC_TYPE_KHR", "", "EGL_SYNC_STATUS_KHR", "EGL_SYNC_CONDITION_KHR", "EGL_SIGNALED_KHR", "EGL_UNSIGNALED_KHR", "EGL_SYNC_PRIOR_COMMANDS_COMPLETE_KHR", "EGL_SYNC_FENCE_KHR", "EGL_SYNC_NATIVE_FENCE_ANDROID", "EGL_FOREVER_KHR", "", "EGL_TIMEOUT_EXPIRED_KHR", "EGL_CONDITION_SATISFIED_KHR", "EGL_SYNC_FLUSH_COMMANDS_BIT_KHR", "EGL_TRUE", "EGL_FALSE", "eglCreateImageFromHardwareBuffer", "Landroidx/opengl/EGLImageKHR;", "eglDisplay", "Landroid/opengl/EGLDisplay;", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "eglDestroyImageKHR", "", AITextWidgetStat.TYPE_IMAGE, "glEGLImageTargetTexture2DOES", "", TypedValues.AttributesType.S_TARGET, "eglCreateSyncKHR", "Landroidx/opengl/EGLSyncKHR;", "type", "attributes", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "eglGetSyncAttribKHR", "sync", "attribute", "value", "", "offset", "eglClientWaitSyncKHR", "Landroidx/opengl/EGLExt$Companion$EGLClientWaitResult;", "flags", "timeoutNanos", "eglDupNativeFenceFDANDROID", "Landroidx/hardware/SyncFenceCompat;", "display", "eglDupNativeFenceFDANDROID$libXCEngine_release", "eglDestroySyncKHR", "eglSync", "parseExtensions", "", "queryString", "EGLSyncAttribute", "EGLFenceType", "EGLClientWaitResult", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: EGLExt.kt */
        @Target({ElementType.TYPE_USE})
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/opengl/EGLExt$Companion$EGLClientWaitResult;", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        @kotlin.annotation.Target(allowedTargets = {AnnotationTarget.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface EGLClientWaitResult {
        }

        /* JADX INFO: compiled from: EGLExt.kt */
        @Retention(RetentionPolicy.RUNTIME)
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/opengl/EGLExt$Companion$EGLFenceType;", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public @interface EGLFenceType {
        }

        /* JADX INFO: compiled from: EGLExt.kt */
        @Retention(RetentionPolicy.RUNTIME)
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/opengl/EGLExt$Companion$EGLSyncAttribute;", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public @interface EGLSyncAttribute {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final EGLImageKHR eglCreateImageFromHardwareBuffer(EGLDisplay eglDisplay, HardwareBuffer hardwareBuffer) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            Intrinsics.checkNotNullParameter(hardwareBuffer, "hardwareBuffer");
            long jNCreateImageFromHardwareBuffer = EGLBindings.INSTANCE.nCreateImageFromHardwareBuffer(eglDisplay.getNativeHandle(), hardwareBuffer);
            if (jNCreateImageFromHardwareBuffer == 0) {
                return null;
            }
            return new EGLImageKHR(jNCreateImageFromHardwareBuffer);
        }

        @JvmStatic
        public final boolean eglDestroyImageKHR(EGLDisplay eglDisplay, EGLImageKHR image) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            Intrinsics.checkNotNullParameter(image, "image");
            return EGLBindings.INSTANCE.nDestroyImageKHR(eglDisplay.getNativeHandle(), image.getNativeHandle());
        }

        @JvmStatic
        public final void glEGLImageTargetTexture2DOES(int target, EGLImageKHR image) {
            Intrinsics.checkNotNullParameter(image, "image");
            EGLBindings.INSTANCE.nImageTargetTexture2DOES(target, image.getNativeHandle());
        }

        @JvmStatic
        public final EGLSyncKHR eglCreateSyncKHR(EGLDisplay eglDisplay, @EGLFenceType int type, EGLConfigAttributes attributes) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            long jNCreateSyncKHR = EGLBindings.INSTANCE.nCreateSyncKHR(eglDisplay.getNativeHandle(), type, attributes != null ? attributes.getAttrs() : null);
            if (jNCreateSyncKHR == 0) {
                return null;
            }
            return new EGLSyncKHR(jNCreateSyncKHR);
        }

        @JvmStatic
        public final boolean eglGetSyncAttribKHR(EGLDisplay eglDisplay, EGLSyncKHR sync, @EGLSyncAttribute int attribute, int[] value, int offset) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            Intrinsics.checkNotNullParameter(sync, "sync");
            Intrinsics.checkNotNullParameter(value, "value");
            return EGLBindings.INSTANCE.nGetSyncAttribKHR(eglDisplay.getNativeHandle(), sync.getNativeHandle(), attribute, value, offset);
        }

        @JvmStatic
        public final int eglClientWaitSyncKHR(EGLDisplay eglDisplay, EGLSyncKHR sync, int flags, long timeoutNanos) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            Intrinsics.checkNotNullParameter(sync, "sync");
            return EGLBindings.INSTANCE.nClientWaitSyncKHR(eglDisplay.getNativeHandle(), sync.getNativeHandle(), flags, timeoutNanos);
        }

        @JvmStatic
        public final SyncFenceCompat eglDupNativeFenceFDANDROID$libXCEngine_release(EGLDisplay display, EGLSyncKHR sync) {
            Intrinsics.checkNotNullParameter(display, "display");
            Intrinsics.checkNotNullParameter(sync, "sync");
            int iNDupNativeFenceFDANDROID = EGLBindings.INSTANCE.nDupNativeFenceFDANDROID(display.getNativeHandle(), sync.getNativeHandle());
            if (iNDupNativeFenceFDANDROID >= 0) {
                return new SyncFenceCompat(new SyncFenceV19(iNDupNativeFenceFDANDROID));
            }
            return new SyncFenceCompat(new SyncFenceV19(-1));
        }

        @JvmStatic
        public final boolean eglDestroySyncKHR(EGLDisplay eglDisplay, EGLSyncKHR eglSync) {
            Intrinsics.checkNotNullParameter(eglDisplay, "eglDisplay");
            Intrinsics.checkNotNullParameter(eglSync, "eglSync");
            return EGLBindings.INSTANCE.nDestroySyncKHR(eglDisplay.getNativeHandle(), eglSync.getNativeHandle());
        }

        @JvmStatic
        public final Set<String> parseExtensions(String queryString) {
            Intrinsics.checkNotNullParameter(queryString, "queryString");
            HashSet hashSet = new HashSet();
            hashSet.addAll(StringsKt.split$default((CharSequence) queryString, new char[]{' '}, false, 0, 6, (Object) null));
            return hashSet;
        }
    }
}
