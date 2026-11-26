package androidx.graphics.opengl.egl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import androidx.opengl.EGLExt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EGLManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\u0018\u0000 82\u00020\u0001:\u00018B\u0011\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u0007J\u0006\u0010\u001d\u001a\u00020\u0017J\u000e\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u0011J\u001a\u0010+\u001a\u00020\u00132\u0006\u0010,\u001a\u00020\t2\b\b\u0002\u0010-\u001a\u00020\tH\u0007J\u0006\u0010.\u001a\u00020\u0017J\u0010\u00106\u001a\u00020\u00172\u0006\u00107\u001a\u00020\tH\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020\r8G¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0013\u0010#\u001a\u0004\u0018\u00010\u000b8G¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0013\u0010&\u001a\u0004\u0018\u00010\u00078G¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0011\u0010/\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b0\u00101R\u0011\u00102\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b3\u00101R\u0011\u00104\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b5\u00101¨\u00069"}, d2 = {"Landroidx/graphics/opengl/egl/EGLManager;", "", "eglSpec", "Landroidx/graphics/opengl/egl/EGLSpec;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/opengl/egl/EGLSpec;)V", "mEglConfig", "Landroid/opengl/EGLConfig;", "mPBufferSurface", "Landroid/opengl/EGLSurface;", "mEglContext", "Landroid/opengl/EGLContext;", "mEglVersion", "Landroidx/graphics/opengl/egl/EGLVersion;", "mEglSpec", "mEglExtensions", "", "", "mIsSingleBuffered", "", "mQueryResult", "", "initialize", "", "loadConfig", "configAttributes", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "createContext", "config", "release", "getEGLSpec", "()Landroidx/graphics/opengl/egl/EGLSpec;", "eglVersion", "getEGLVersion", "()Landroidx/graphics/opengl/egl/EGLVersion;", "eglContext", "getEGLContext", "()Landroid/opengl/EGLContext;", "eglConfig", "getEGLConfig", "()Landroid/opengl/EGLConfig;", "isExtensionSupported", "extensionName", "makeCurrent", "drawSurface", "readSurface", "swapAndFlushBuffers", "defaultSurface", "getDefaultSurface", "()Landroid/opengl/EGLSurface;", "currentDrawSurface", "getCurrentDrawSurface", "currentReadSurface", "getCurrentReadSurface", "querySurface", "surface", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EGLManager {
    private static final String TAG = "EglManager";
    private EGLConfig mEglConfig;
    private EGLContext mEglContext;
    private Set<String> mEglExtensions;
    private final EGLSpec mEglSpec;
    private EGLVersion mEglVersion;
    private boolean mIsSingleBuffered;
    private EGLSurface mPBufferSurface;
    private int[] mQueryResult;

    public EGLManager() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final boolean makeCurrent(EGLSurface drawSurface) {
        Intrinsics.checkNotNullParameter(drawSurface, "drawSurface");
        return makeCurrent$default(this, drawSurface, null, 2, null);
    }

    public EGLManager(EGLSpec eglSpec) {
        Intrinsics.checkNotNullParameter(eglSpec, "eglSpec");
        EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
        this.mPBufferSurface = EGL_NO_SURFACE;
        EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
        this.mEglContext = EGL_NO_CONTEXT;
        this.mEglVersion = EGLVersion.Unknown;
        this.mEglSpec = eglSpec;
    }

    public /* synthetic */ EGLManager(EGLSpec eGLSpec, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EGLSpec.V14 : eGLSpec);
    }

    public final void initialize() {
        if (this.mEglContext == EGL14.EGL_NO_CONTEXT) {
            this.mEglVersion = getMEglSpec().eglInitialize();
            this.mEglExtensions = EGLExt.INSTANCE.parseExtensions(getMEglSpec().eglQueryString(R2.id.accessibility_custom_action_22));
        }
    }

    public final EGLConfig loadConfig(EGLConfigAttributes configAttributes) {
        Intrinsics.checkNotNullParameter(configAttributes, "configAttributes");
        return getMEglSpec().loadConfig(configAttributes);
    }

    public final EGLContext createContext(EGLConfig config) {
        EGLSurface eGLSurfaceEglCreatePBufferSurface;
        Intrinsics.checkNotNullParameter(config, "config");
        EGLContext eGLContextEglCreateContext = getMEglSpec().eglCreateContext(config);
        if (eGLContextEglCreateContext != EGL14.EGL_NO_CONTEXT) {
            if (isExtensionSupported(EGLExt.EGL_KHR_SURFACELESS_CONTEXT)) {
                eGLSurfaceEglCreatePBufferSurface = EGL14.EGL_NO_SURFACE;
                Intrinsics.checkNotNull(eGLSurfaceEglCreatePBufferSurface);
            } else {
                EGLConfigAttributes.Builder builder = new EGLConfigAttributes.Builder();
                builder.to(R2.id.accessibility_custom_action_24, 1);
                builder.to(R2.id.accessibility_custom_action_23, 1);
                eGLSurfaceEglCreatePBufferSurface = getMEglSpec().eglCreatePBufferSurface(config, builder.build());
            }
            if (!getMEglSpec().eglMakeCurrent(eGLContextEglCreateContext, eGLSurfaceEglCreatePBufferSurface, eGLSurfaceEglCreatePBufferSurface)) {
                throw new EGLException(getMEglSpec().eglGetError(), "Unable to make default surface current");
            }
            this.mPBufferSurface = eGLSurfaceEglCreatePBufferSurface;
            this.mEglContext = eGLContextEglCreateContext;
            this.mEglConfig = config;
            return eGLContextEglCreateContext;
        }
        EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
        this.mPBufferSurface = EGL_NO_SURFACE;
        EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
        this.mEglContext = EGL_NO_CONTEXT;
        this.mEglConfig = null;
        return eGLContextEglCreateContext;
    }

    public final void release() {
        EGLContext eGLContext = this.mEglContext;
        if (Intrinsics.areEqual(eGLContext, EGL14.EGL_NO_CONTEXT)) {
            return;
        }
        getMEglSpec().eglDestroyContext(eGLContext);
        EGLSurface eGLSurface = this.mPBufferSurface;
        if (!Intrinsics.areEqual(eGLSurface, EGL14.EGL_NO_SURFACE)) {
            getMEglSpec().eglDestroySurface(eGLSurface);
        }
        EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
        this.mPBufferSurface = EGL_NO_SURFACE;
        EGLSpec mEglSpec = getMEglSpec();
        EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
        EGLSurface EGL_NO_SURFACE2 = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE2, "EGL_NO_SURFACE");
        EGLSurface EGL_NO_SURFACE3 = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE3, "EGL_NO_SURFACE");
        mEglSpec.eglMakeCurrent(EGL_NO_CONTEXT, EGL_NO_SURFACE2, EGL_NO_SURFACE3);
        this.mEglVersion = EGLVersion.Unknown;
        EGLContext EGL_NO_CONTEXT2 = EGL14.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT2, "EGL_NO_CONTEXT");
        this.mEglContext = EGL_NO_CONTEXT2;
        this.mEglConfig = null;
        this.mEglExtensions = null;
    }

    /* JADX INFO: renamed from: getEGLSpec, reason: from getter */
    public final EGLSpec getMEglSpec() {
        return this.mEglSpec;
    }

    /* JADX INFO: renamed from: getEGLVersion, reason: from getter */
    public final EGLVersion getMEglVersion() {
        return this.mEglVersion;
    }

    /* JADX INFO: renamed from: getEGLContext, reason: from getter */
    public final EGLContext getMEglContext() {
        return this.mEglContext;
    }

    /* JADX INFO: renamed from: getEGLConfig, reason: from getter */
    public final EGLConfig getMEglConfig() {
        return this.mEglConfig;
    }

    public final boolean isExtensionSupported(String extensionName) {
        Intrinsics.checkNotNullParameter(extensionName, "extensionName");
        Set<String> set = this.mEglExtensions;
        if (set != null) {
            return set.contains(extensionName);
        }
        return false;
    }

    public static /* synthetic */ boolean makeCurrent$default(EGLManager eGLManager, EGLSurface eGLSurface, EGLSurface eGLSurface2, int i, Object obj) {
        if ((i & 2) != 0) {
            eGLSurface2 = eGLSurface;
        }
        return eGLManager.makeCurrent(eGLSurface, eGLSurface2);
    }

    public final boolean makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
        Intrinsics.checkNotNullParameter(drawSurface, "drawSurface");
        Intrinsics.checkNotNullParameter(readSurface, "readSurface");
        boolean zEglMakeCurrent = getMEglSpec().eglMakeCurrent(this.mEglContext, drawSurface, readSurface);
        if (zEglMakeCurrent) {
            querySurface(drawSurface);
        }
        return zEglMakeCurrent;
    }

    public final void swapAndFlushBuffers() {
        if (this.mIsSingleBuffered) {
            GLES20.glFlush();
        }
        getMEglSpec().eglSwapBuffers(getCurrentDrawSurface());
    }

    /* JADX INFO: renamed from: getDefaultSurface, reason: from getter */
    public final EGLSurface getMPBufferSurface() {
        return this.mPBufferSurface;
    }

    public final EGLSurface getCurrentDrawSurface() {
        return getMEglSpec().eglGetCurrentDrawSurface();
    }

    public final EGLSurface getCurrentReadSurface() {
        return getMEglSpec().eglGetCurrentReadSurface();
    }

    private final void querySurface(EGLSurface surface) {
        if (Intrinsics.areEqual(surface, EGL14.EGL_NO_SURFACE)) {
            this.mIsSingleBuffered = false;
            return;
        }
        int[] iArr = this.mQueryResult;
        if (iArr == null) {
            iArr = new int[1];
            this.mQueryResult = iArr;
        }
        if (getMEglSpec().eglQuerySurface(surface, R2.id.action_folder_delete, iArr, 0)) {
            this.mIsSingleBuffered = iArr[0] == 12421;
        }
    }
}
