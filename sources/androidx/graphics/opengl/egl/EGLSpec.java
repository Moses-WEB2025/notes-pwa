package androidx.graphics.opengl.egl;

import android.hardware.HardwareBuffer;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.Surface;
import androidx.opengl.EGLExt;
import androidx.opengl.EGLImageKHR;
import androidx.opengl.EGLSyncKHR;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EGLSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bf\u0018\u0000 :2\u00020\u0001:\u0001:J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\"\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u0007H&J \u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0007H&J\b\u0010\u0016\u001a\u00020\u0007H&J\b\u0010\u0017\u001a\u00020\u0007H&J\b\u0010\u0018\u001a\u00020\u0019H&J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0013H&J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u0007H&J(\u0010 \u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0005H&J\b\u0010%\u001a\u00020\u0005H&J\b\u0010&\u001a\u00020\u0003H\u0016J\u0012\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*H'J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010,\u001a\u00020(H&J\u001c\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\u00052\b\u00100\u001a\u0004\u0018\u00010\u000bH&J*\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u00020.2\b\b\u0001\u0010!\u001a\u00020\u00052\u0006\u00103\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0005H&J\u0010\u00104\u001a\u00020\u00102\u0006\u00102\u001a\u00020.H&J%\u00105\u001a\u00070\u0005¢\u0006\u0002\b62\u0006\u00102\u001a\u00020.2\u0006\u00107\u001a\u00020\u00052\u0006\u00108\u001a\u000209H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006;À\u0006\u0003"}, d2 = {"Landroidx/graphics/opengl/egl/EGLSpec;", "", "eglQueryString", "", "nameId", "", "eglCreatePBufferSurface", "Landroid/opengl/EGLSurface;", "config", "Landroid/opengl/EGLConfig;", "configAttributes", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "eglCreateWindowSurface", "surface", "Landroid/view/Surface;", "eglDestroySurface", "", "eglMakeCurrent", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/opengl/EGLContext;", "drawSurface", "readSurface", "eglGetCurrentReadSurface", "eglGetCurrentDrawSurface", "eglInitialize", "Landroidx/graphics/opengl/egl/EGLVersion;", "loadConfig", "eglCreateContext", "eglDestroyContext", "", "eglContext", "eglSwapBuffers", "eglQuerySurface", "attribute", "result", "", "offset", "eglGetError", "getErrorMessage", "eglCreateImageFromHardwareBuffer", "Landroidx/opengl/EGLImageKHR;", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "eglDestroyImageKHR", AITextWidgetStat.TYPE_IMAGE, "eglCreateSyncKHR", "Landroidx/opengl/EGLSyncKHR;", "type", "attributes", "eglGetSyncAttribKHR", "sync", "value", "eglDestroySyncKHR", "eglClientWaitSyncKHR", "Landroidx/opengl/EGLExt$Companion$EGLClientWaitResult;", "flags", "timeoutNanos", "", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface EGLSpec {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final EGLSpec V14 = new EGLSpec() { // from class: androidx.graphics.opengl.egl.EGLSpec$Companion$V14$1
        private final int[] contextAttributes = {R2.id.action_sheet_arrow_view, 2, R2.id.META};
        private final EGLConfigAttributes DefaultWindowSurfaceConfig = new EGLConfigAttributes.Builder().build();

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLVersion eglInitialize() {
            int[] iArr = {1};
            int[] iArr2 = {1};
            if (EGL14.eglInitialize(getDefaultDisplay(), iArr, 0, iArr2, 0)) {
                return new EGLVersion(iArr[0], iArr2[0]);
            }
            throw new EGLException(EGL14.eglGetError(), "Unable to initialize default display");
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLSurface eglGetCurrentReadSurface() {
            EGLSurface eGLSurfaceEglGetCurrentSurface = EGL14.eglGetCurrentSurface(R2.id.accessibility_custom_action_27);
            Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglGetCurrentSurface, "eglGetCurrentSurface(...)");
            return eGLSurfaceEglGetCurrentSurface;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLSurface eglGetCurrentDrawSurface() {
            EGLSurface eGLSurfaceEglGetCurrentSurface = EGL14.eglGetCurrentSurface(R2.id.accessibility_custom_action_26);
            Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglGetCurrentSurface, "eglGetCurrentSurface(...)");
            return eGLSurfaceEglGetCurrentSurface;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public String eglQueryString(int nameId) {
            String strEglQueryString = EGL14.eglQueryString(getDefaultDisplay(), nameId);
            Intrinsics.checkNotNullExpressionValue(strEglQueryString, "eglQueryString(...)");
            return strEglQueryString;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLSurface eglCreatePBufferSurface(EGLConfig config, EGLConfigAttributes configAttributes) {
            Intrinsics.checkNotNullParameter(config, "config");
            EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(getDefaultDisplay(), config, configAttributes != null ? configAttributes.getAttrs() : null, 0);
            Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglCreatePbufferSurface, "eglCreatePbufferSurface(...)");
            return eGLSurfaceEglCreatePbufferSurface;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLSurface eglCreateWindowSurface(EGLConfig config, Surface surface, EGLConfigAttributes configAttributes) {
            int[] attrs;
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(surface, "surface");
            EGLDisplay defaultDisplay = getDefaultDisplay();
            if (configAttributes == null || (attrs = configAttributes.getAttrs()) == null) {
                attrs = this.DefaultWindowSurfaceConfig.getAttrs();
            }
            EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(defaultDisplay, config, surface, attrs, 0);
            Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglCreateWindowSurface, "eglCreateWindowSurface(...)");
            return eGLSurfaceEglCreateWindowSurface;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglSwapBuffers(EGLSurface surface) {
            Intrinsics.checkNotNullParameter(surface, "surface");
            return EGL14.eglSwapBuffers(getDefaultDisplay(), surface);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglQuerySurface(EGLSurface surface, int attribute, int[] result, int offset) {
            Intrinsics.checkNotNullParameter(surface, "surface");
            Intrinsics.checkNotNullParameter(result, "result");
            return EGL14.eglQuerySurface(getDefaultDisplay(), surface, attribute, result, offset);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglDestroySurface(EGLSurface surface) {
            Intrinsics.checkNotNullParameter(surface, "surface");
            return EGL14.eglDestroySurface(getDefaultDisplay(), surface);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglMakeCurrent(EGLContext context, EGLSurface drawSurface, EGLSurface readSurface) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(drawSurface, "drawSurface");
            Intrinsics.checkNotNullParameter(readSurface, "readSurface");
            return EGL14.eglMakeCurrent(getDefaultDisplay(), drawSurface, readSurface, context);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLConfig loadConfig(EGLConfigAttributes configAttributes) {
            Intrinsics.checkNotNullParameter(configAttributes, "configAttributes");
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (EGL14.eglChooseConfig(getDefaultDisplay(), configAttributes.getAttrs(), 0, eGLConfigArr, 0, 1, new int[]{1}, 0)) {
                return eGLConfigArr[0];
            }
            return null;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLContext eglCreateContext(EGLConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(getDefaultDisplay(), config, EGL14.EGL_NO_CONTEXT, this.contextAttributes, 0);
            Intrinsics.checkNotNullExpressionValue(eGLContextEglCreateContext, "eglCreateContext(...)");
            return eGLContextEglCreateContext;
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public void eglDestroyContext(EGLContext eglContext) {
            Intrinsics.checkNotNullParameter(eglContext, "eglContext");
            if (!EGL14.eglDestroyContext(getDefaultDisplay(), eglContext)) {
                throw new EGLException(EGL14.eglGetError(), "Unable to destroy EGLContext");
            }
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLImageKHR eglCreateImageFromHardwareBuffer(HardwareBuffer hardwareBuffer) {
            Intrinsics.checkNotNullParameter(hardwareBuffer, "hardwareBuffer");
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglCreateImageFromHardwareBuffer(defaultDisplay, hardwareBuffer);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglDestroyImageKHR(EGLImageKHR image) {
            Intrinsics.checkNotNullParameter(image, "image");
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglDestroyImageKHR(defaultDisplay, image);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public EGLSyncKHR eglCreateSyncKHR(int type, EGLConfigAttributes attributes) {
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglCreateSyncKHR(defaultDisplay, type, attributes);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglGetSyncAttribKHR(EGLSyncKHR sync, int attribute, int[] value, int offset) {
            Intrinsics.checkNotNullParameter(sync, "sync");
            Intrinsics.checkNotNullParameter(value, "value");
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglGetSyncAttribKHR(defaultDisplay, sync, attribute, value, offset);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public boolean eglDestroySyncKHR(EGLSyncKHR sync) {
            Intrinsics.checkNotNullParameter(sync, "sync");
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglDestroySyncKHR(defaultDisplay, sync);
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public int eglGetError() {
            return EGL14.eglGetError();
        }

        @Override // androidx.graphics.opengl.egl.EGLSpec
        public int eglClientWaitSyncKHR(EGLSyncKHR sync, int flags, long timeoutNanos) {
            Intrinsics.checkNotNullParameter(sync, "sync");
            EGLExt.Companion companion = EGLExt.INSTANCE;
            EGLDisplay defaultDisplay = getDefaultDisplay();
            Intrinsics.checkNotNullExpressionValue(defaultDisplay, "getDefaultDisplay(...)");
            return companion.eglClientWaitSyncKHR(defaultDisplay, sync, flags, timeoutNanos);
        }

        private final EGLDisplay getDefaultDisplay() {
            return EGL14.eglGetDisplay(0);
        }
    };

    @JvmStatic
    static String getStatusString(int i) {
        return INSTANCE.getStatusString(i);
    }

    int eglClientWaitSyncKHR(EGLSyncKHR sync, int flags, long timeoutNanos);

    EGLContext eglCreateContext(EGLConfig config);

    EGLImageKHR eglCreateImageFromHardwareBuffer(HardwareBuffer hardwareBuffer);

    EGLSurface eglCreatePBufferSurface(EGLConfig config, EGLConfigAttributes configAttributes);

    EGLSyncKHR eglCreateSyncKHR(int type, EGLConfigAttributes attributes);

    EGLSurface eglCreateWindowSurface(EGLConfig config, Surface surface, EGLConfigAttributes configAttributes);

    void eglDestroyContext(EGLContext eglContext);

    boolean eglDestroyImageKHR(EGLImageKHR image);

    boolean eglDestroySurface(EGLSurface surface);

    boolean eglDestroySyncKHR(EGLSyncKHR sync);

    EGLSurface eglGetCurrentDrawSurface();

    EGLSurface eglGetCurrentReadSurface();

    int eglGetError();

    boolean eglGetSyncAttribKHR(EGLSyncKHR sync, @EGLExt.Companion.EGLSyncAttribute int attribute, int[] value, int offset);

    EGLVersion eglInitialize();

    boolean eglMakeCurrent(EGLContext context, EGLSurface drawSurface, EGLSurface readSurface);

    String eglQueryString(int nameId);

    boolean eglQuerySurface(EGLSurface surface, int attribute, int[] result, int offset);

    boolean eglSwapBuffers(EGLSurface surface);

    EGLConfig loadConfig(EGLConfigAttributes configAttributes);

    /* JADX INFO: compiled from: EGLSpec.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static String getErrorMessage(EGLSpec eGLSpec) {
            return EGLSpec.super.getErrorMessage();
        }
    }

    default String getErrorMessage() {
        return INSTANCE.getStatusString(eglGetError());
    }

    /* JADX INFO: compiled from: EGLSpec.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u0013\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0001¨\u0006\n"}, d2 = {"Landroidx/graphics/opengl/egl/EGLSpec$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "V14", "Landroidx/graphics/opengl/egl/EGLSpec;", "getStatusString", "", SNSAuthProvider.VALUE_SNS_ERROR, "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        @JvmStatic
        public final String getStatusString(int error) {
            switch (error) {
                case R2.drawable.v8_theme_antique_decoration_1 /* 12288 */:
                    return "EGL_SUCCESS";
                case R2.drawable.v8_theme_antique_decoration_2 /* 12289 */:
                    return "EGL_NOT_INITIALIZED";
                case R2.drawable.v8_theme_antique_decoration_3 /* 12290 */:
                    return "EGL_BAD_ACCESS";
                case R2.drawable.v8_theme_antique_decoration_4 /* 12291 */:
                    return "EGL_BAD_ALLOC";
                case R2.drawable.v8_theme_antique_h_1 /* 12292 */:
                    return "EGL_BAD_ATTRIBUTE";
                case R2.drawable.v8_theme_antique_h_2 /* 12293 */:
                    return "EGL_BAD_CONFIG";
                case R2.drawable.v8_theme_antique_h_3 /* 12294 */:
                    return "EGL_BAD_CONTEXT";
                case R2.drawable.v8_theme_antique_v /* 12295 */:
                    return "EGL_BAD_CURRENT_SURFACE";
                case R2.drawable.v8_theme_arty_bg_bottom_left /* 12296 */:
                    return "EGL_BAD_DISPLAY";
                case R2.drawable.v8_theme_arty_bg_bottom_right /* 12297 */:
                    return "EGL_BAD_MATCH";
                case R2.drawable.v8_theme_arty_bg_left /* 12298 */:
                    return "EGL_BAD_NATIVE_PIXMAP";
                case R2.drawable.v8_theme_arty_bg_left_inset /* 12299 */:
                    return "EGL_BAD_NATIVE_WINDOW";
                case R2.drawable.v8_theme_arty_bg_right /* 12300 */:
                    return "EGL_BAD_PARAMETER";
                case R2.drawable.v8_theme_arty_bg_right_inset /* 12301 */:
                    return "EGL_BAD_SURFACE";
                case R2.drawable.v8_theme_arty_bg_top_left /* 12302 */:
                    return "EGL_CONTEXT_LOST";
                default:
                    String hexString = Integer.toHexString(error);
                    Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                    return hexString;
            }
        }
    }
}
