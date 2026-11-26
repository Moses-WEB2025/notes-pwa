package androidx.graphics.opengl;

import android.view.SurfaceHolder;
import com.miui.doodle.document.Layer;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GLRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000A\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J(\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fH\u0016J\u0010\u0010\"\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0017H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006#"}, d2 = {"androidx/graphics/opengl/GLRenderer$attach$callback$1", "Landroid/view/SurfaceHolder$Callback2;", "isAttached", "", "()Z", "setAttached", "(Z)V", "renderLatch", "Ljava/util/concurrent/CountDownLatch;", "getRenderLatch", "()Ljava/util/concurrent/CountDownLatch;", "setRenderLatch", "(Ljava/util/concurrent/CountDownLatch;)V", "detachLatch", "getDetachLatch", "onDetachComplete", "", "renderTarget", "Landroidx/graphics/opengl/GLRenderer$RenderTarget;", "getRenderTarget", "()Landroidx/graphics/opengl/GLRenderer$RenderTarget;", "surfaceRedrawNeeded", "p0", "Landroid/view/SurfaceHolder;", "surfaceRedrawNeededAsync", "holder", "drawingFinished", "Ljava/lang/Runnable;", "surfaceCreated", "surfaceChanged", "format", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "surfaceDestroyed", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLRenderer$attach$callback$1 implements SurfaceHolder.Callback2 {
    final /* synthetic */ GLRenderer.RenderCallback $renderer;
    final /* synthetic */ GLThread $thread;
    final /* synthetic */ int $token;
    private final CountDownLatch detachLatch = new CountDownLatch(1);
    private boolean isAttached;
    private volatile CountDownLatch renderLatch;
    private final GLRenderer.RenderTarget renderTarget;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
    }

    GLRenderer$attach$callback$1(int i, GLRenderer gLRenderer, final SurfaceHolder surfaceHolder, GLThread gLThread, GLRenderer.RenderCallback renderCallback) {
        this.$token = i;
        this.$thread = gLThread;
        this.$renderer = renderCallback;
        this.renderTarget = new GLRenderer.RenderTarget(i, gLRenderer, new Function0() { // from class: androidx.graphics.opengl.GLRenderer$attach$callback$1$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return GLRenderer$attach$callback$1.renderTarget$lambda$0(surfaceHolder, this);
            }
        });
    }

    /* JADX INFO: renamed from: isAttached, reason: from getter */
    public final boolean getIsAttached() {
        return this.isAttached;
    }

    public final void setAttached(boolean z) {
        this.isAttached = z;
    }

    public final CountDownLatch getRenderLatch() {
        return this.renderLatch;
    }

    public final void setRenderLatch(CountDownLatch countDownLatch) {
        this.renderLatch = countDownLatch;
    }

    public final CountDownLatch getDetachLatch() {
        return this.detachLatch;
    }

    public final void onDetachComplete() {
        this.isAttached = false;
        CountDownLatch countDownLatch = this.renderLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        this.detachLatch.countDown();
    }

    public final GLRenderer.RenderTarget getRenderTarget() {
        return this.renderTarget;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderTarget$lambda$0(SurfaceHolder surfaceHolder, GLRenderer$attach$callback$1 gLRenderer$attach$callback$1) {
        surfaceHolder.removeCallback(gLRenderer$attach$callback$1);
        gLRenderer$attach$callback$1.onDetachComplete();
        return Unit.INSTANCE;
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeeded(SurfaceHolder p0) throws InterruptedException {
        Intrinsics.checkNotNullParameter(p0, "p0");
        if (this.detachLatch.getCount() > 0) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            this.renderLatch = countDownLatch;
            this.renderTarget.requestRender(new Function1() { // from class: androidx.graphics.opengl.GLRenderer$attach$callback$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GLRenderer$attach$callback$1.surfaceRedrawNeeded$lambda$2(countDownLatch, (GLRenderer.RenderTarget) obj);
                }
            });
            countDownLatch.await();
            this.renderLatch = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit surfaceRedrawNeeded$lambda$2(CountDownLatch countDownLatch, GLRenderer.RenderTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        countDownLatch.countDown();
        return Unit.INSTANCE;
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(SurfaceHolder holder, final Runnable drawingFinished) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(drawingFinished, "drawingFinished");
        this.renderTarget.requestRender(new Function1() { // from class: androidx.graphics.opengl.GLRenderer$attach$callback$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GLRenderer$attach$callback$1.surfaceRedrawNeededAsync$lambda$3(drawingFinished, (GLRenderer.RenderTarget) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit surfaceRedrawNeededAsync$lambda$3(Runnable runnable, GLRenderer.RenderTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        runnable.run();
        return Unit.INSTANCE;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (!this.isAttached) {
            this.$thread.attachSurface(this.$token, holder.getSurface(), width, height, this.$renderer);
            this.isAttached = true;
        } else {
            GLRenderer.RenderTarget.resize$default(this.renderTarget, width, height, null, 4, null);
        }
        GLRenderer.RenderTarget.requestRender$default(this.renderTarget, null, 1, null);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) throws InterruptedException {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.renderTarget.detachInternal$libXCEngine_release(true, new Function1() { // from class: androidx.graphics.opengl.GLRenderer$attach$callback$1$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GLRenderer$attach$callback$1.surfaceDestroyed$lambda$4(this.f$0, (GLRenderer.RenderTarget) obj);
            }
        });
        this.detachLatch.await();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit surfaceDestroyed$lambda$4(GLRenderer$attach$callback$1 gLRenderer$attach$callback$1, GLRenderer.RenderTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        gLRenderer$attach$callback$1.onDetachComplete();
        return Unit.INSTANCE;
    }
}
