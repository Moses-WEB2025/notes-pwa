package androidx.graphics.lowlatency;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.graphics.opengl.GLFrameBufferRenderer;
import androidx.graphics.opengl.GLRenderer;
import androidx.graphics.opengl.egl.EGLManager;
import com.miui.doodle.document.Layer;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J(\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0010¨\u0006\u0013"}, d2 = {"androidx/graphics/lowlatency/GLFrontBufferedRenderer$mSurfaceCallbacks$1", "Landroid/view/SurfaceHolder$Callback2;", "surfaceCreated", "", "holder", "Landroid/view/SurfaceHolder;", "surfaceChanged", "format", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "surfaceDestroyed", "p0", "surfaceRedrawNeeded", "surfaceRedrawNeededAsync", "drawingFinished", "Ljava/lang/Runnable;", "requestDraw", "onComplete", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrontBufferedRenderer$mSurfaceCallbacks$1 implements SurfaceHolder.Callback2 {
    final /* synthetic */ GLFrontBufferedRenderer<T> this$0;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
    }

    GLFrontBufferedRenderer$mSurfaceCallbacks$1(GLFrontBufferedRenderer<T> gLFrontBufferedRenderer) {
        this.this$0 = gLFrontBufferedRenderer;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        SurfaceView surfaceView = ((GLFrontBufferedRenderer) this.this$0).mSurfaceView;
        if (surfaceView != null) {
            this.this$0.update$libXCEngine_release(surfaceView, width, height);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        GLFrontBufferedRenderer.detachTargets$libXCEngine_release$default(this.this$0, true, null, 2, null);
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeeded(SurfaceHolder p0) throws InterruptedException {
        Intrinsics.checkNotNullParameter(p0, "p0");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        requestDraw(new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mSurfaceCallbacks$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(SurfaceHolder holder, Runnable drawingFinished) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(drawingFinished, "drawingFinished");
        requestDraw(drawingFinished);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.graphics.lowlatency.GLFrontBufferedRenderer$mSurfaceCallbacks$1$requestDraw$eglCallback$1] */
    public final void requestDraw(final Runnable onComplete) {
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        if (((GLFrontBufferedRenderer) this.this$0).mMultiBufferedRenderer != null) {
            final GLFrontBufferedRenderer<T> gLFrontBufferedRenderer = this.this$0;
            final ?? r0 = new GLRenderer.EGLContextCallback() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mSurfaceCallbacks$1$requestDraw$eglCallback$1
                @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
                public void onEGLContextCreated(EGLManager eglManager) {
                    Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                }

                @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
                public void onEGLContextDestroyed(EGLManager eglManager) {
                    Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                    onComplete.run();
                    ((GLFrontBufferedRenderer) gLFrontBufferedRenderer).mGLRenderer.unregisterEGLContextCallback(this);
                }
            };
            ((GLFrontBufferedRenderer) this.this$0).mGLRenderer.registerEGLContextCallback((GLRenderer.EGLContextCallback) r0);
            GLFrameBufferRenderer gLFrameBufferRenderer = ((GLFrontBufferedRenderer) this.this$0).mMultiBufferedRenderer;
            if (gLFrameBufferRenderer != null) {
                gLFrameBufferRenderer.render();
            }
            GLRenderer gLRenderer = ((GLFrontBufferedRenderer) this.this$0).mGLRenderer;
            final GLFrontBufferedRenderer<T> gLFrontBufferedRenderer2 = this.this$0;
            gLRenderer.execute(new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mSurfaceCallbacks$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GLFrontBufferedRenderer$mSurfaceCallbacks$1.requestDraw$lambda$2(onComplete, gLFrontBufferedRenderer2, r0);
                }
            });
            return;
        }
        XLog.w(GLFrontBufferedRenderer.TAG, "---- Multi buffered renderer not initialized & execute drawingFinishedTransaction directly", new Object[0]);
        onComplete.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestDraw$lambda$2(Runnable runnable, GLFrontBufferedRenderer gLFrontBufferedRenderer, GLFrontBufferedRenderer$mSurfaceCallbacks$1$requestDraw$eglCallback$1 gLFrontBufferedRenderer$mSurfaceCallbacks$1$requestDraw$eglCallback$1) {
        runnable.run();
        gLFrontBufferedRenderer.mGLRenderer.unregisterEGLContextCallback(gLFrontBufferedRenderer$mSurfaceCallbacks$1$requestDraw$eglCallback$1);
    }
}
