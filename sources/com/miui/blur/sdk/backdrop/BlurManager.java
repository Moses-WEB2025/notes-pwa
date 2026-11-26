package com.miui.blur.sdk.backdrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.GraphicBuffer;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.renderscript.RenderScript;
import android.view.MiuiCompositionSamplingListener;
import android.view.ViewRootImpl;
import java.util.HashMap;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class BlurManager {
    static final boolean BACKDROP_SAMPLING_ENABLED;
    static final boolean GOOGLE_BACKGROUND_BLUR_SUPPORTED;
    private static final Handler RS_HANDLER;
    private static final HandlerThread RS_THREAD;
    private static final HashMap<ViewRootImpl, BlurLayerHolder> sBlurLayers;
    private static RenderScript sRenderScript;

    private static boolean isNecessaryClassExists() {
        Class<?> clsLoadClass;
        try {
            clsLoadClass = ClassLoader.getSystemClassLoader().loadClass("android.view.MiuiCompositionSamplingListener");
        } catch (ClassNotFoundException unused) {
            clsLoadClass = null;
        }
        return clsLoadClass != null;
    }

    static {
        BACKDROP_SAMPLING_ENABLED = Build.VERSION.SDK_INT == 30 && isNecessaryClassExists() && SystemProperties.getBoolean("ro.miui.backdrop_sampling_enabled", false) && Integer.parseInt(SystemProperties.get("ro.miui.ui.version.code")) >= 11;
        GOOGLE_BACKGROUND_BLUR_SUPPORTED = Build.VERSION.SDK_INT >= 31;
        HandlerThread handlerThread = new HandlerThread("rs_blur");
        RS_THREAD = handlerThread;
        handlerThread.start();
        RS_HANDLER = new Handler(handlerThread.getLooper());
        sBlurLayers = new HashMap<>();
    }

    private BlurManager() {
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0026  */
    public static void register(Context context, BlurDrawInfo blurDrawInfo) {
        boolean z;
        if (sRenderScript == null) {
            sRenderScript = RenderScript.create(context);
        }
        ViewRootImpl blurViewRootImpl = blurDrawInfo.getBlurViewRootImpl();
        if (blurViewRootImpl != null) {
            HashMap<ViewRootImpl, BlurLayerHolder> map = sBlurLayers;
            if (map.get(blurViewRootImpl) == null) {
                map.put(blurViewRootImpl, new BlurLayerHolder(context, blurViewRootImpl, sRenderScript, RS_HANDLER));
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        BlurLayerHolder blurLayerHolder = sBlurLayers.get(blurViewRootImpl);
        if (blurLayerHolder != null) {
            blurLayerHolder.register(blurDrawInfo);
        }
        if (z) {
            blurLayerHolder.start();
        }
    }

    public static void unregister(BlurDrawInfo blurDrawInfo) {
        ViewRootImpl blurViewRootImpl = blurDrawInfo.getBlurViewRootImpl();
        HashMap<ViewRootImpl, BlurLayerHolder> map = sBlurLayers;
        BlurLayerHolder blurLayerHolder = map.get(blurViewRootImpl);
        if (blurLayerHolder != null) {
            blurLayerHolder.unregister(blurDrawInfo);
            if (blurLayerHolder.isEmpty()) {
                map.remove(blurViewRootImpl);
                blurLayerHolder.destroy();
            }
        }
    }

    public static void draw(Canvas canvas, BlurDrawInfo blurDrawInfo) {
        BlurLayerHolder blurLayerHolder = sBlurLayers.get(blurDrawInfo.getBlurViewRootImpl());
        if (blurLayerHolder != null) {
            blurLayerHolder.draw(canvas, blurDrawInfo);
        }
    }

    public static boolean isBackdropBlurSupported() {
        return BACKDROP_SAMPLING_ENABLED;
    }

    static class CompositionSamplingListenerWrapper extends MiuiCompositionSamplingListener {
        private static final android.util.Pools.SynchronizedPool<CompositionSamplingListenerWrapper> POOL = new android.util.Pools.SynchronizedPool<>(2);
        private Consumer<GraphicBuffer> mCallback;

        private CompositionSamplingListenerWrapper() {
            super(new HandlerExecutor(BlurManager.RS_HANDLER));
        }

        public void onSampleCollected(GraphicBuffer graphicBuffer) {
            Consumer<GraphicBuffer> consumer = this.mCallback;
            if (consumer != null) {
                consumer.accept(graphicBuffer);
            }
        }

        public void setCallback(Consumer<GraphicBuffer> consumer) {
            this.mCallback = consumer;
        }

        private void reset() {
            this.mCallback = null;
        }

        public static CompositionSamplingListenerWrapper acquire() {
            CompositionSamplingListenerWrapper compositionSamplingListenerWrapper = (CompositionSamplingListenerWrapper) POOL.acquire();
            return compositionSamplingListenerWrapper == null ? new CompositionSamplingListenerWrapper() : compositionSamplingListenerWrapper;
        }

        public static void release(CompositionSamplingListenerWrapper compositionSamplingListenerWrapper) {
            compositionSamplingListenerWrapper.reset();
            if (POOL.release(compositionSamplingListenerWrapper)) {
                return;
            }
            compositionSamplingListenerWrapper.destroy();
        }
    }
}
