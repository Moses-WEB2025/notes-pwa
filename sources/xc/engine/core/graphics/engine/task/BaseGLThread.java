package xc.engine.core.graphics.engine.task;

import android.opengl.EGLContext;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.gl.GLEnv;
import xc.engine.core.graphics.engine.gl.KhronosGLEnv;

/* JADX INFO: compiled from: BaseGLThread.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 #2\u00020\u0001:\u0002#$B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0006\u0010\u0019\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u0018H\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0016J\u000e\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!J\u000e\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\"R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lxc/engine/core/graphics/engine/task/BaseGLThread;", "Ljava/lang/Thread;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "curveEngineRef", "Ljava/lang/ref/WeakReference;", "getCurveEngineRef", "()Ljava/lang/ref/WeakReference;", "setCurveEngineRef", "(Ljava/lang/ref/WeakReference;)V", "glEnv", "Lxc/engine/core/graphics/engine/gl/KhronosGLEnv;", "androidGlEnv", "Lxc/engine/core/graphics/engine/gl/GLEnv;", "bRunning", "", "getBRunning", "()Z", "setBRunning", "(Z)V", "isKhronosGL", "run", "", "terminate", "obtainGLEnv", "Lxc/engine/core/graphics/engine/task/BaseGLThread$IGLEnv;", "onInit", "onDestroy", "tick", "setSharedContext", "sharedContext", "Landroid/opengl/EGLContext;", "Ljavax/microedition/khronos/egl/EGLContext;", "Companion", "IGLEnv", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseGLThread extends Thread {
    private static final String TAG = "BaseGLThread";
    private GLEnv androidGlEnv;
    private boolean bRunning;
    private WeakReference<MiCurveEngine> curveEngineRef;
    private KhronosGLEnv glEnv;
    private boolean isKhronosGL;

    /* JADX INFO: compiled from: BaseGLThread.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/task/BaseGLThread$IGLEnv;", "", "initGLEnv", "", "onDestroy", "", "makeCurrent", "setSharedContext", "sharedContext", "Landroid/opengl/EGLContext;", "Ljavax/microedition/khronos/egl/EGLContext;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IGLEnv {
        boolean initGLEnv();

        void makeCurrent();

        void onDestroy();

        void setSharedContext(EGLContext sharedContext);

        void setSharedContext(javax.microedition.khronos.egl.EGLContext sharedContext);
    }

    public void onInit() {
    }

    public void tick() {
    }

    public BaseGLThread(MiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.curveEngineRef = new WeakReference<>(engine);
        this.glEnv = new KhronosGLEnv();
        this.androidGlEnv = new GLEnv();
    }

    public final WeakReference<MiCurveEngine> getCurveEngineRef() {
        return this.curveEngineRef;
    }

    public final void setCurveEngineRef(WeakReference<MiCurveEngine> weakReference) {
        Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
        this.curveEngineRef = weakReference;
    }

    protected final boolean getBRunning() {
        return this.bRunning;
    }

    protected final void setBRunning(boolean z) {
        this.bRunning = z;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (!obtainGLEnv().initGLEnv()) {
            Log.e(TAG, "xl- BaseGLThread gl env init failed!");
            return;
        }
        obtainGLEnv().makeCurrent();
        onInit();
        this.bRunning = true;
        while (this.bRunning) {
            tick();
        }
        onDestroy();
    }

    public final void terminate() {
        this.bRunning = false;
    }

    public final IGLEnv obtainGLEnv() {
        return this.isKhronosGL ? this.glEnv : this.androidGlEnv;
    }

    public void onDestroy() {
        obtainGLEnv().onDestroy();
    }

    public final void setSharedContext(EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
        this.isKhronosGL = false;
        this.androidGlEnv.setSharedContext(sharedContext);
    }

    public final void setSharedContext(javax.microedition.khronos.egl.EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
        this.isKhronosGL = true;
        this.glEnv.setSharedContext(sharedContext);
    }
}
