package com.miui.libhandwritingpreview.thread;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.manager.PreAsyncGLTaskManager;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.TexturePool;

/* JADX INFO: compiled from: PreBaseGLThread.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \"2\u00020\u0001:\u0002\"#B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0006\u0010\u0019\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u0018H\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0016J\u000e\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!R\u001f\u0010\u0006\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001f\u0010\f\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\r0\r0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006$"}, d2 = {"Lcom/miui/libhandwritingpreview/thread/PreBaseGLThread;", "Ljava/lang/Thread;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "taskManagerRef", "Ljava/lang/ref/WeakReference;", "Lcom/miui/libhandwritingpreview/manager/PreAsyncGLTaskManager;", "kotlin.jvm.PlatformType", "getTaskManagerRef", "()Ljava/lang/ref/WeakReference;", "texturePoolRef", "Lxc/engine/core/graphics/engine/core/TexturePool;", "getTexturePoolRef", "glEnv", "Lcom/miui/libhandwritingpreview/thread/PreKhronosGLEnv;", "bRunning", "", "getBRunning", "()Z", "setBRunning", "(Z)V", "run", "", "terminate", "obtainGLEnv", "Lcom/miui/libhandwritingpreview/thread/PreBaseGLThread$IGLEnv;", "onInit", "onDestroy", "tick", "setSharedContext", "sharedContext", "Ljavax/microedition/khronos/egl/EGLContext;", "Companion", "IGLEnv", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PreBaseGLThread extends Thread {
    private static final String TAG = "PreBaseGLThread";
    private boolean bRunning;
    private PreKhronosGLEnv glEnv;
    private final WeakReference<PreAsyncGLTaskManager> taskManagerRef;
    private final WeakReference<TexturePool> texturePoolRef;

    /* JADX INFO: compiled from: PreBaseGLThread.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&¨\u0006\nÀ\u0006\u0003"}, d2 = {"Lcom/miui/libhandwritingpreview/thread/PreBaseGLThread$IGLEnv;", "", "initGLEnv", "", "onDestroy", "", "makeCurrent", "setSharedContext", "sharedContext", "Ljavax/microedition/khronos/egl/EGLContext;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IGLEnv {
        boolean initGLEnv();

        void makeCurrent();

        void onDestroy();

        void setSharedContext(EGLContext sharedContext);
    }

    public void onInit() {
    }

    public void tick() {
    }

    public PreBaseGLThread(PreMiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.taskManagerRef = new WeakReference<>(engine.getTaskManager());
        this.texturePoolRef = new WeakReference<>(engine.getTexturePool());
        this.glEnv = new PreKhronosGLEnv();
    }

    public final WeakReference<PreAsyncGLTaskManager> getTaskManagerRef() {
        return this.taskManagerRef;
    }

    public final WeakReference<TexturePool> getTexturePoolRef() {
        return this.texturePoolRef;
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
        return this.glEnv;
    }

    public void onDestroy() {
        obtainGLEnv().onDestroy();
    }

    public final void setSharedContext(EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
        this.glEnv.setSharedContext(sharedContext);
    }
}
