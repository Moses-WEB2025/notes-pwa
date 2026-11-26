package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.configurations.reducemotion.ReducedMotionMode;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieThreadFactory;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class LottieDrawable extends Drawable implements Drawable.Callback, Animatable {
    public static final int INFINITE = -1;
    private static final float MAX_DELTA_MS_ASYNC_SET_PROGRESS = 50.0f;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private int alpha;
    private final LottieValueAnimator animator;
    private AsyncUpdates asyncUpdates;
    private Rect canvasClipBounds;
    private RectF canvasClipBoundsRectF;
    private boolean clipTextToBoundingBox;
    private boolean clipToCompositionBounds;
    private LottieComposition composition;
    private CompositionLayer compositionLayer;
    String defaultFontFileExtension;
    FontAssetDelegate fontAssetDelegate;
    private FontAssetManager fontAssetManager;
    private Map<String, Typeface> fontMap;
    private boolean ignoreSystemAnimationsDisabled;
    private ImageAssetDelegate imageAssetDelegate;
    private ImageAssetManager imageAssetManager;
    private String imageAssetsFolder;
    private Runnable invalidateSelfRunnable;
    private boolean isApplyingOpacityToLayersEnabled;
    private boolean isApplyingShadowToLayersEnabled;
    private boolean isDirty;
    private float lastDrawnProgress;
    private final ArrayList<LazyCompositionTask> lazyCompositionTasks;
    private final LottieFeatureFlags lottieFeatureFlags;
    private Handler mainThreadHandler;
    private boolean maintainOriginalImageBounds;
    private OnVisibleAction onVisibleAction;
    private boolean outlineMasksAndMattes;
    private boolean performanceTrackingEnabled;
    private final ValueAnimator.AnimatorUpdateListener progressUpdateListener;
    private RenderMode renderMode;
    private final Matrix renderingMatrix;
    private boolean safeMode;
    private final Semaphore setProgressDrawLock;
    private Bitmap softwareRenderingBitmap;
    private Canvas softwareRenderingCanvas;
    private Rect softwareRenderingDstBoundsRect;
    private RectF softwareRenderingDstBoundsRectF;
    private Matrix softwareRenderingOriginalCanvasMatrix;
    private float[] softwareRenderingOriginalCanvasMatrixElements;
    private Matrix softwareRenderingOriginalCanvasMatrixInverse;
    private Paint softwareRenderingPaint;
    private Rect softwareRenderingSrcBoundsRect;
    private RectF softwareRenderingTransformedBounds;
    private boolean systemAnimationsEnabled;
    TextDelegate textDelegate;
    private final Runnable updateProgressRunnable;
    private boolean useSoftwareRendering;
    private static final boolean invalidateSelfOnMainThread = false;
    private static final List<String> ALLOWED_REDUCED_MOTION_MARKERS = Arrays.asList("reduced motion", "reduced_motion", "reduced-motion", "reducedmotion");
    private static final Executor setProgressExecutor = new ThreadPoolExecutor(0, 2, 35, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new LottieThreadFactory());

    /* JADX INFO: Access modifiers changed from: private */
    interface LazyCompositionTask {
        void run(LottieComposition lottieComposition);
    }

    private enum OnVisibleAction {
        NONE,
        PLAY,
        RESUME
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @Deprecated
    public void disableExtraScaleModeInFitXY() {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    /* JADX INFO: renamed from: lambda$new$0$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m327lambda$new$0$comairbnblottieLottieDrawable(ValueAnimator valueAnimator) {
        if (getAsyncUpdatesEnabled()) {
            invalidateSelf();
            return;
        }
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer != null) {
            compositionLayer.setProgress(this.animator.getAnimatedValueAbsolute());
        }
    }

    /* JADX INFO: renamed from: lambda$new$2$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m329lambda$new$2$comairbnblottieLottieDrawable() {
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            return;
        }
        try {
            this.setProgressDrawLock.acquire();
            compositionLayer.setProgress(this.animator.getAnimatedValueAbsolute());
            if (invalidateSelfOnMainThread && this.isDirty) {
                if (this.mainThreadHandler == null) {
                    this.mainThreadHandler = new Handler(Looper.getMainLooper());
                    this.invalidateSelfRunnable = new Runnable() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m328lambda$new$1$comairbnblottieLottieDrawable();
                        }
                    };
                }
                this.mainThreadHandler.post(this.invalidateSelfRunnable);
            }
        } catch (InterruptedException unused) {
        } finally {
            this.setProgressDrawLock.release();
        }
    }

    /* JADX INFO: renamed from: lambda$new$1$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m328lambda$new$1$comairbnblottieLottieDrawable() {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public LottieDrawable() {
        LottieValueAnimator lottieValueAnimator = new LottieValueAnimator();
        this.animator = lottieValueAnimator;
        this.systemAnimationsEnabled = true;
        this.ignoreSystemAnimationsDisabled = false;
        this.safeMode = false;
        this.onVisibleAction = OnVisibleAction.NONE;
        this.lazyCompositionTasks = new ArrayList<>();
        this.lottieFeatureFlags = new LottieFeatureFlags();
        this.maintainOriginalImageBounds = false;
        this.clipToCompositionBounds = true;
        this.alpha = 255;
        this.clipTextToBoundingBox = false;
        this.renderMode = RenderMode.AUTOMATIC;
        this.useSoftwareRendering = false;
        this.renderingMatrix = new Matrix();
        this.softwareRenderingOriginalCanvasMatrixElements = new float[9];
        this.isDirty = false;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda13
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m327lambda$new$0$comairbnblottieLottieDrawable(valueAnimator);
            }
        };
        this.progressUpdateListener = animatorUpdateListener;
        this.setProgressDrawLock = new Semaphore(1);
        this.updateProgressRunnable = new Runnable() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m329lambda$new$2$comairbnblottieLottieDrawable();
            }
        };
        this.lastDrawnProgress = -3.4028235E38f;
        lottieValueAnimator.addUpdateListener(animatorUpdateListener);
    }

    public boolean hasMasks() {
        CompositionLayer compositionLayer = this.compositionLayer;
        return compositionLayer != null && compositionLayer.hasMasks();
    }

    public boolean hasMatte() {
        CompositionLayer compositionLayer = this.compositionLayer;
        return compositionLayer != null && compositionLayer.hasMatte();
    }

    @Deprecated
    public boolean enableMergePathsForKitKatAndAbove() {
        return this.lottieFeatureFlags.isFlagEnabled(LottieFeatureFlag.MergePathsApi19);
    }

    @Deprecated
    public void enableMergePathsForKitKatAndAbove(boolean z) {
        boolean zEnableFlag = this.lottieFeatureFlags.enableFlag(LottieFeatureFlag.MergePathsApi19, z);
        if (this.composition == null || !zEnableFlag) {
            return;
        }
        buildCompositionLayer();
    }

    @Deprecated
    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.lottieFeatureFlags.isFlagEnabled(LottieFeatureFlag.MergePathsApi19);
    }

    public void enableFeatureFlag(LottieFeatureFlag lottieFeatureFlag, boolean z) {
        boolean zEnableFlag = this.lottieFeatureFlags.enableFlag(lottieFeatureFlag, z);
        if (this.composition == null || !zEnableFlag) {
            return;
        }
        buildCompositionLayer();
    }

    public boolean isFeatureFlagEnabled(LottieFeatureFlag lottieFeatureFlag) {
        return this.lottieFeatureFlags.isFlagEnabled(lottieFeatureFlag);
    }

    public void setClipToCompositionBounds(boolean z) {
        if (z != this.clipToCompositionBounds) {
            this.clipToCompositionBounds = z;
            CompositionLayer compositionLayer = this.compositionLayer;
            if (compositionLayer != null) {
                compositionLayer.setClipToCompositionBounds(z);
            }
            invalidateSelf();
        }
    }

    public boolean getClipToCompositionBounds() {
        return this.clipToCompositionBounds;
    }

    public void setImagesAssetsFolder(String str) {
        this.imageAssetsFolder = str;
    }

    public String getImageAssetsFolder() {
        return this.imageAssetsFolder;
    }

    public void setMaintainOriginalImageBounds(boolean z) {
        this.maintainOriginalImageBounds = z;
    }

    public boolean getMaintainOriginalImageBounds() {
        return this.maintainOriginalImageBounds;
    }

    public boolean setComposition(LottieComposition lottieComposition) {
        if (this.composition == lottieComposition) {
            return false;
        }
        this.isDirty = true;
        clearComposition();
        this.composition = lottieComposition;
        buildCompositionLayer();
        this.animator.setComposition(lottieComposition);
        setProgress(this.animator.getAnimatedFraction());
        Iterator it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            LazyCompositionTask lazyCompositionTask = (LazyCompositionTask) it.next();
            if (lazyCompositionTask != null) {
                lazyCompositionTask.run(lottieComposition);
            }
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        lottieComposition.setPerformanceTrackingEnabled(this.performanceTrackingEnabled);
        computeRenderMode();
        Drawable.Callback callback = getCallback();
        if (callback instanceof ImageView) {
            ImageView imageView = (ImageView) callback;
            imageView.setImageDrawable(null);
            imageView.setImageDrawable(this);
        }
        return true;
    }

    public void setRenderMode(RenderMode renderMode) {
        this.renderMode = renderMode;
        computeRenderMode();
    }

    public AsyncUpdates getAsyncUpdates() {
        AsyncUpdates asyncUpdates = this.asyncUpdates;
        return asyncUpdates != null ? asyncUpdates : L.getDefaultAsyncUpdates();
    }

    public boolean getAsyncUpdatesEnabled() {
        return getAsyncUpdates() == AsyncUpdates.ENABLED;
    }

    public void setAsyncUpdates(AsyncUpdates asyncUpdates) {
        this.asyncUpdates = asyncUpdates;
    }

    public RenderMode getRenderMode() {
        return this.useSoftwareRendering ? RenderMode.SOFTWARE : RenderMode.HARDWARE;
    }

    private void computeRenderMode() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return;
        }
        this.useSoftwareRendering = this.renderMode.useSoftwareRendering(Build.VERSION.SDK_INT, lottieComposition.hasDashPattern(), lottieComposition.getMaskAndMatteCount());
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTrackingEnabled = z;
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieComposition.setPerformanceTrackingEnabled(z);
        }
    }

    public void setOutlineMasksAndMattes(boolean z) {
        if (this.outlineMasksAndMattes == z) {
            return;
        }
        this.outlineMasksAndMattes = z;
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer != null) {
            compositionLayer.setOutlineMasksAndMattes(z);
        }
    }

    public PerformanceTracker getPerformanceTracker() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return lottieComposition.getPerformanceTracker();
        }
        return null;
    }

    public void setApplyingOpacityToLayersEnabled(boolean z) {
        this.isApplyingOpacityToLayersEnabled = z;
    }

    public void setApplyingShadowToLayersEnabled(boolean z) {
        this.isApplyingShadowToLayersEnabled = z;
    }

    public boolean isApplyingOpacityToLayersEnabled() {
        return this.isApplyingOpacityToLayersEnabled;
    }

    public boolean isApplyingShadowToLayersEnabled() {
        return this.isApplyingShadowToLayersEnabled;
    }

    public boolean getClipTextToBoundingBox() {
        return this.clipTextToBoundingBox;
    }

    public void setClipTextToBoundingBox(boolean z) {
        if (z != this.clipTextToBoundingBox) {
            this.clipTextToBoundingBox = z;
            invalidateSelf();
        }
    }

    private void buildCompositionLayer() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return;
        }
        CompositionLayer compositionLayer = new CompositionLayer(this, LayerParser.parse(lottieComposition), lottieComposition.getLayers(), lottieComposition);
        this.compositionLayer = compositionLayer;
        if (this.outlineMasksAndMattes) {
            compositionLayer.setOutlineMasksAndMattes(true);
        }
        this.compositionLayer.setClipToCompositionBounds(this.clipToCompositionBounds);
    }

    public void clearComposition() {
        if (this.animator.isRunning()) {
            this.animator.cancel();
            if (!isVisible()) {
                this.onVisibleAction = OnVisibleAction.NONE;
            }
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        this.lastDrawnProgress = -3.4028235E38f;
        this.animator.clearComposition();
        invalidateSelf();
    }

    public void setSafeMode(boolean z) {
        this.safeMode = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        Drawable.Callback callback;
        if (this.isDirty) {
            return;
        }
        this.isDirty = true;
        if ((!invalidateSelfOnMainThread || Looper.getMainLooper() == Looper.myLooper()) && (callback = getCallback()) != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.alpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Logger.warning("Use addColorFilter instead.");
    }

    private boolean shouldSetProgressBeforeDrawing() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return false;
        }
        float f = this.lastDrawnProgress;
        float animatedValueAbsolute = this.animator.getAnimatedValueAbsolute();
        this.lastDrawnProgress = animatedValueAbsolute;
        return Math.abs(animatedValueAbsolute - f) * lottieComposition.getDuration() >= 50.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            return;
        }
        boolean asyncUpdatesEnabled = getAsyncUpdatesEnabled();
        if (asyncUpdatesEnabled) {
            try {
                this.setProgressDrawLock.acquire();
            } catch (InterruptedException unused) {
                if (L.isTraceEnabled()) {
                    L.endSection("Drawable#draw");
                }
                if (!asyncUpdatesEnabled) {
                    return;
                }
                this.setProgressDrawLock.release();
                if (compositionLayer.getProgress() == this.animator.getAnimatedValueAbsolute()) {
                    return;
                }
            } catch (Throwable th) {
                if (L.isTraceEnabled()) {
                    L.endSection("Drawable#draw");
                }
                if (asyncUpdatesEnabled) {
                    this.setProgressDrawLock.release();
                    if (compositionLayer.getProgress() != this.animator.getAnimatedValueAbsolute()) {
                        setProgressExecutor.execute(this.updateProgressRunnable);
                    }
                }
                throw th;
            }
        }
        if (L.isTraceEnabled()) {
            L.beginSection("Drawable#draw");
        }
        if (asyncUpdatesEnabled && shouldSetProgressBeforeDrawing()) {
            setProgress(this.animator.getAnimatedValueAbsolute());
        }
        if (this.safeMode) {
            try {
                if (this.useSoftwareRendering) {
                    renderAndDrawAsBitmap(canvas, compositionLayer);
                } else {
                    drawDirectlyToCanvas(canvas);
                }
            } catch (Throwable th2) {
                Logger.error("Lottie crashed in draw!", th2);
            }
        } else if (this.useSoftwareRendering) {
            renderAndDrawAsBitmap(canvas, compositionLayer);
        } else {
            drawDirectlyToCanvas(canvas);
        }
        this.isDirty = false;
        if (L.isTraceEnabled()) {
            L.endSection("Drawable#draw");
        }
        if (asyncUpdatesEnabled) {
            this.setProgressDrawLock.release();
            if (compositionLayer.getProgress() == this.animator.getAnimatedValueAbsolute()) {
                return;
            }
            setProgressExecutor.execute(this.updateProgressRunnable);
        }
    }

    public void draw(Canvas canvas, Matrix matrix) {
        CompositionLayer compositionLayer = this.compositionLayer;
        LottieComposition lottieComposition = this.composition;
        if (compositionLayer == null || lottieComposition == null) {
            return;
        }
        boolean asyncUpdatesEnabled = getAsyncUpdatesEnabled();
        if (asyncUpdatesEnabled) {
            try {
                this.setProgressDrawLock.acquire();
                if (shouldSetProgressBeforeDrawing()) {
                    setProgress(this.animator.getAnimatedValueAbsolute());
                }
            } catch (InterruptedException unused) {
                if (!asyncUpdatesEnabled) {
                    return;
                }
                this.setProgressDrawLock.release();
                if (compositionLayer.getProgress() == this.animator.getAnimatedValueAbsolute()) {
                    return;
                }
            } catch (Throwable th) {
                if (asyncUpdatesEnabled) {
                    this.setProgressDrawLock.release();
                    if (compositionLayer.getProgress() != this.animator.getAnimatedValueAbsolute()) {
                        setProgressExecutor.execute(this.updateProgressRunnable);
                    }
                }
                throw th;
            }
        }
        if (this.safeMode) {
            try {
                draw(canvas, matrix, compositionLayer, this.alpha);
            } catch (Throwable th2) {
                Logger.error("Lottie crashed in draw!", th2);
            }
        } else {
            draw(canvas, matrix, compositionLayer, this.alpha);
        }
        this.isDirty = false;
        if (asyncUpdatesEnabled) {
            this.setProgressDrawLock.release();
            if (compositionLayer.getProgress() == this.animator.getAnimatedValueAbsolute()) {
                return;
            }
            setProgressExecutor.execute(this.updateProgressRunnable);
        }
    }

    private void draw(Canvas canvas, Matrix matrix, CompositionLayer compositionLayer, int i) {
        if (this.useSoftwareRendering) {
            canvas.save();
            canvas.concat(matrix);
            renderAndDrawAsBitmap(canvas, compositionLayer);
            canvas.restore();
            return;
        }
        compositionLayer.draw(canvas, matrix, i, null);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        Drawable.Callback callback = getCallback();
        if ((callback instanceof View) && ((View) callback).isInEditMode()) {
            return;
        }
        playAnimation();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        endAnimation();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return isAnimating();
    }

    public void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda15
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m330lambda$playAnimation$3$comairbnblottieLottieDrawable(lottieComposition);
                }
            });
            return;
        }
        computeRenderMode();
        if (animationsEnabled(getContext()) || getRepeatCount() == 0) {
            if (isVisible()) {
                this.animator.playAnimation();
                this.onVisibleAction = OnVisibleAction.NONE;
            } else {
                this.onVisibleAction = OnVisibleAction.PLAY;
            }
        }
        if (animationsEnabled(getContext())) {
            return;
        }
        Marker markerForAnimationsDisabled = getMarkerForAnimationsDisabled();
        if (markerForAnimationsDisabled != null) {
            setFrame((int) markerForAnimationsDisabled.startFrame);
        } else {
            setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        }
        this.animator.endAnimation();
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    /* JADX INFO: renamed from: lambda$playAnimation$3$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m330lambda$playAnimation$3$comairbnblottieLottieDrawable(LottieComposition lottieComposition) {
        playAnimation();
    }

    public Marker getMarkerForAnimationsDisabled() {
        Iterator<String> it = ALLOWED_REDUCED_MOTION_MARKERS.iterator();
        Marker marker = null;
        while (it.hasNext()) {
            marker = this.composition.getMarker(it.next());
            if (marker != null) {
                break;
            }
        }
        return marker;
    }

    public void endAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.endAnimation();
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    public void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda9
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m331lambda$resumeAnimation$4$comairbnblottieLottieDrawable(lottieComposition);
                }
            });
            return;
        }
        computeRenderMode();
        if (animationsEnabled(getContext()) || getRepeatCount() == 0) {
            if (isVisible()) {
                this.animator.resumeAnimation();
                this.onVisibleAction = OnVisibleAction.NONE;
            } else {
                this.onVisibleAction = OnVisibleAction.RESUME;
            }
        }
        if (animationsEnabled(getContext())) {
            return;
        }
        setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        this.animator.endAnimation();
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    /* JADX INFO: renamed from: lambda$resumeAnimation$4$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m331lambda$resumeAnimation$4$comairbnblottieLottieDrawable(LottieComposition lottieComposition) {
        resumeAnimation();
    }

    public void setMinFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda5
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m340lambda$setMinFrame$5$comairbnblottieLottieDrawable(i, lottieComposition);
                }
            });
        } else {
            this.animator.setMinFrame(i);
        }
    }

    /* JADX INFO: renamed from: lambda$setMinFrame$5$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m340lambda$setMinFrame$5$comairbnblottieLottieDrawable(int i, LottieComposition lottieComposition) {
        setMinFrame(i);
    }

    public float getMinFrame() {
        return this.animator.getMinFrame();
    }

    public void setMinProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda16
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m342lambda$setMinProgress$6$comairbnblottieLottieDrawable(f, lottieComposition2);
                }
            });
        } else {
            setMinFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f));
        }
    }

    /* JADX INFO: renamed from: lambda$setMinProgress$6$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m342lambda$setMinProgress$6$comairbnblottieLottieDrawable(float f, LottieComposition lottieComposition) {
        setMinProgress(f);
    }

    public void setMaxFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda4
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m334lambda$setMaxFrame$7$comairbnblottieLottieDrawable(i, lottieComposition);
                }
            });
        } else {
            this.animator.setMaxFrame(i + 0.99f);
        }
    }

    /* JADX INFO: renamed from: lambda$setMaxFrame$7$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m334lambda$setMaxFrame$7$comairbnblottieLottieDrawable(int i, LottieComposition lottieComposition) {
        setMaxFrame(i);
    }

    public float getMaxFrame() {
        return this.animator.getMaxFrame();
    }

    public void setMaxProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda7
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m335lambda$setMaxProgress$8$comairbnblottieLottieDrawable(f, lottieComposition2);
                }
            });
        } else {
            this.animator.setMaxFrame(MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f));
        }
    }

    /* JADX INFO: renamed from: lambda$setMaxProgress$8$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m335lambda$setMaxProgress$8$comairbnblottieLottieDrawable(float f, LottieComposition lottieComposition) {
        setMaxProgress(f);
    }

    public void setMinFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda12
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m341lambda$setMinFrame$9$comairbnblottieLottieDrawable(str, lottieComposition2);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
        }
        setMinFrame((int) marker.startFrame);
    }

    /* JADX INFO: renamed from: lambda$setMinFrame$9$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m341lambda$setMinFrame$9$comairbnblottieLottieDrawable(String str, LottieComposition lottieComposition) {
        setMinFrame(str);
    }

    public void setMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda11
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m333lambda$setMaxFrame$10$comairbnblottieLottieDrawable(str, lottieComposition2);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
        }
        setMaxFrame((int) (marker.startFrame + marker.durationFrames));
    }

    /* JADX INFO: renamed from: lambda$setMaxFrame$10$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m333lambda$setMaxFrame$10$comairbnblottieLottieDrawable(String str, LottieComposition lottieComposition) {
        setMaxFrame(str);
    }

    public void setMinAndMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m336lambda$setMinAndMaxFrame$11$comairbnblottieLottieDrawable(str, lottieComposition2);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
        }
        int i = (int) marker.startFrame;
        setMinAndMaxFrame(i, ((int) marker.durationFrames) + i);
    }

    /* JADX INFO: renamed from: lambda$setMinAndMaxFrame$11$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m336lambda$setMinAndMaxFrame$11$comairbnblottieLottieDrawable(String str, LottieComposition lottieComposition) {
        setMinAndMaxFrame(str);
    }

    public void setMinAndMaxFrame(final String str, final String str2, final boolean z) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda10
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m337lambda$setMinAndMaxFrame$12$comairbnblottieLottieDrawable(str, str2, z, lottieComposition2);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
        }
        int i = (int) marker.startFrame;
        Marker marker2 = this.composition.getMarker(str2);
        if (marker2 == null) {
            throw new IllegalArgumentException("Cannot find marker with name " + str2 + ".");
        }
        setMinAndMaxFrame(i, (int) (marker2.startFrame + (z ? 1.0f : 0.0f)));
    }

    /* JADX INFO: renamed from: lambda$setMinAndMaxFrame$12$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m337lambda$setMinAndMaxFrame$12$comairbnblottieLottieDrawable(String str, String str2, boolean z, LottieComposition lottieComposition) {
        setMinAndMaxFrame(str, str2, z);
    }

    public void setMinAndMaxFrame(final int i, final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda3
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m338lambda$setMinAndMaxFrame$13$comairbnblottieLottieDrawable(i, i2, lottieComposition);
                }
            });
        } else {
            this.animator.setMinAndMaxFrames(i, i2 + 0.99f);
        }
    }

    /* JADX INFO: renamed from: lambda$setMinAndMaxFrame$13$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m338lambda$setMinAndMaxFrame$13$comairbnblottieLottieDrawable(int i, int i2, LottieComposition lottieComposition) {
        setMinAndMaxFrame(i, i2);
    }

    public void setMinAndMaxProgress(final float f, final float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda2
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition2) {
                    this.f$0.m339lambda$setMinAndMaxProgress$14$comairbnblottieLottieDrawable(f, f2, lottieComposition2);
                }
            });
        } else {
            setMinAndMaxFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f), (int) MiscUtils.lerp(this.composition.getStartFrame(), this.composition.getEndFrame(), f2));
        }
    }

    /* JADX INFO: renamed from: lambda$setMinAndMaxProgress$14$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m339lambda$setMinAndMaxProgress$14$comairbnblottieLottieDrawable(float f, float f2, LottieComposition lottieComposition) {
        setMinAndMaxProgress(f, f2);
    }

    public void reverseAnimationSpeed() {
        this.animator.reverseAnimationSpeed();
    }

    public void setSpeed(float f) {
        this.animator.setSpeed(f);
    }

    public float getSpeed() {
        return this.animator.getSpeed();
    }

    public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.addUpdateListener(animatorUpdateListener);
    }

    public void removeAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.removeUpdateListener(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(this.progressUpdateListener);
    }

    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animator.removeListener(animatorListener);
    }

    public void removeAllAnimatorListeners() {
        this.animator.removeAllListeners();
    }

    public void addAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.animator.addPauseListener(animatorPauseListener);
    }

    public void removeAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.animator.removePauseListener(animatorPauseListener);
    }

    public void setFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda1
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m332lambda$setFrame$15$comairbnblottieLottieDrawable(i, lottieComposition);
                }
            });
        } else {
            this.animator.setFrame(i);
        }
    }

    /* JADX INFO: renamed from: lambda$setFrame$15$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m332lambda$setFrame$15$comairbnblottieLottieDrawable(int i, LottieComposition lottieComposition) {
        setFrame(i);
    }

    public int getFrame() {
        return (int) this.animator.getFrame();
    }

    public void setProgress(final float f) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda17
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m343lambda$setProgress$16$comairbnblottieLottieDrawable(f, lottieComposition);
                }
            });
            return;
        }
        if (L.isTraceEnabled()) {
            L.beginSection("Drawable#setProgress");
        }
        this.animator.setFrame(this.composition.getFrameForProgress(f));
        if (L.isTraceEnabled()) {
            L.endSection("Drawable#setProgress");
        }
    }

    /* JADX INFO: renamed from: lambda$setProgress$16$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m343lambda$setProgress$16$comairbnblottieLottieDrawable(float f, LottieComposition lottieComposition) {
        setProgress(f);
    }

    @Deprecated
    public void loop(boolean z) {
        this.animator.setRepeatCount(z ? -1 : 0);
    }

    public void setRepeatMode(int i) {
        this.animator.setRepeatMode(i);
    }

    public int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public void setRepeatCount(int i) {
        this.animator.setRepeatCount(i);
    }

    public int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public boolean isLooping() {
        return this.animator.getRepeatCount() == -1;
    }

    public boolean isAnimating() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.isRunning();
    }

    boolean isAnimatingOrWillAnimateOnVisible() {
        if (isVisible()) {
            return this.animator.isRunning();
        }
        return this.onVisibleAction == OnVisibleAction.PLAY || this.onVisibleAction == OnVisibleAction.RESUME;
    }

    public boolean animationsEnabled(Context context) {
        if (this.ignoreSystemAnimationsDisabled) {
            return true;
        }
        return this.systemAnimationsEnabled && L.getReducedMotionOption().getCurrentReducedMotionMode(context) == ReducedMotionMode.STANDARD_MOTION;
    }

    @Deprecated
    public void setSystemAnimationsAreEnabled(Boolean bool) {
        this.systemAnimationsEnabled = bool.booleanValue();
    }

    @Deprecated
    public void setIgnoreDisabledSystemAnimations(boolean z) {
        this.ignoreSystemAnimationsDisabled = z;
    }

    public void setUseCompositionFrameRate(boolean z) {
        this.animator.setUseCompositionFrameRate(z);
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.imageAssetDelegate = imageAssetDelegate;
        ImageAssetManager imageAssetManager = this.imageAssetManager;
        if (imageAssetManager != null) {
            imageAssetManager.setDelegate(imageAssetDelegate);
        }
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.fontAssetDelegate = fontAssetDelegate;
        FontAssetManager fontAssetManager = this.fontAssetManager;
        if (fontAssetManager != null) {
            fontAssetManager.setDelegate(fontAssetDelegate);
        }
    }

    public void setFontMap(Map<String, Typeface> map) {
        if (map == this.fontMap) {
            return;
        }
        this.fontMap = map;
        invalidateSelf();
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.textDelegate = textDelegate;
    }

    public TextDelegate getTextDelegate() {
        return this.textDelegate;
    }

    public boolean useTextGlyphs() {
        return this.fontMap == null && this.textDelegate == null && this.composition.getCharacters().size() > 0;
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    public void cancelAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.cancel();
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    public void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.pauseAnimation();
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    public float getProgress() {
        return this.animator.getAnimatedValueAbsolute();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return lottieComposition.getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return lottieComposition.getBounds().height();
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Logger.warning("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
        return arrayList;
    }

    public <T> void addValueCallback(final KeyPath keyPath, final T t, final LottieValueCallback<T> lottieValueCallback) {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda6
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run(LottieComposition lottieComposition) {
                    this.f$0.m326lambda$addValueCallback$17$comairbnblottieLottieDrawable(keyPath, t, lottieValueCallback, lottieComposition);
                }
            });
            return;
        }
        boolean zIsEmpty = true;
        if (keyPath == KeyPath.COMPOSITION) {
            this.compositionLayer.addValueCallback(t, lottieValueCallback);
        } else if (keyPath.getResolvedElement() != null) {
            keyPath.getResolvedElement().addValueCallback(t, lottieValueCallback);
        } else {
            List<KeyPath> listResolveKeyPath = resolveKeyPath(keyPath);
            for (int i = 0; i < listResolveKeyPath.size(); i++) {
                listResolveKeyPath.get(i).getResolvedElement().addValueCallback(t, lottieValueCallback);
            }
            zIsEmpty = true ^ listResolveKeyPath.isEmpty();
        }
        if (zIsEmpty) {
            invalidateSelf();
            if (t == LottieProperty.TIME_REMAP) {
                setProgress(getProgress());
            }
        }
    }

    /* JADX INFO: renamed from: lambda$addValueCallback$17$com-airbnb-lottie-LottieDrawable, reason: not valid java name */
    /* synthetic */ void m326lambda$addValueCallback$17$comairbnblottieLottieDrawable(KeyPath keyPath, Object obj, LottieValueCallback lottieValueCallback, LottieComposition lottieComposition) {
        addValueCallback(keyPath, obj, (LottieValueCallback<Object>) lottieValueCallback);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        addValueCallback(keyPath, t, new LottieValueCallback<T>(this) { // from class: com.airbnb.lottie.LottieDrawable.1
            final /* synthetic */ LottieDrawable this$0;

            {
                this.this$0 = this;
            }

            @Override // com.airbnb.lottie.value.LottieValueCallback
            public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return (T) simpleLottieValueCallback.getValue(lottieFrameInfo);
            }
        });
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        ImageAssetManager imageAssetManager = getImageAssetManager();
        if (imageAssetManager == null) {
            Logger.warning("Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        Bitmap bitmapUpdateBitmap = imageAssetManager.updateBitmap(str, bitmap);
        invalidateSelf();
        return bitmapUpdateBitmap;
    }

    @Deprecated
    public Bitmap getImageAsset(String str) {
        ImageAssetManager imageAssetManager = getImageAssetManager();
        if (imageAssetManager != null) {
            return imageAssetManager.bitmapForId(str);
        }
        LottieComposition lottieComposition = this.composition;
        LottieImageAsset lottieImageAsset = lottieComposition == null ? null : lottieComposition.getImages().get(str);
        if (lottieImageAsset != null) {
            return lottieImageAsset.getBitmap();
        }
        return null;
    }

    public Bitmap getBitmapForId(String str) {
        ImageAssetManager imageAssetManager = getImageAssetManager();
        if (imageAssetManager != null) {
            return imageAssetManager.bitmapForId(str);
        }
        return null;
    }

    public LottieImageAsset getLottieImageAssetForId(String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return null;
        }
        return lottieComposition.getImages().get(str);
    }

    private ImageAssetManager getImageAssetManager() {
        ImageAssetManager imageAssetManager = this.imageAssetManager;
        if (imageAssetManager != null && !imageAssetManager.hasSameContext(getContext())) {
            this.imageAssetManager = null;
        }
        if (this.imageAssetManager == null) {
            this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, this.imageAssetDelegate, this.composition.getImages());
        }
        return this.imageAssetManager;
    }

    public Typeface getTypeface(Font font) {
        Map<String, Typeface> map = this.fontMap;
        if (map != null) {
            String family = font.getFamily();
            if (map.containsKey(family)) {
                return map.get(family);
            }
            String name = font.getName();
            if (map.containsKey(name)) {
                return map.get(name);
            }
            String str = font.getFamily() + "-" + font.getStyle();
            if (map.containsKey(str)) {
                return map.get(str);
            }
        }
        FontAssetManager fontAssetManager = getFontAssetManager();
        if (fontAssetManager != null) {
            return fontAssetManager.getTypeface(font);
        }
        return null;
    }

    private FontAssetManager getFontAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.fontAssetManager == null) {
            FontAssetManager fontAssetManager = new FontAssetManager(getCallback(), this.fontAssetDelegate);
            this.fontAssetManager = fontAssetManager;
            String str = this.defaultFontFileExtension;
            if (str != null) {
                fontAssetManager.setDefaultFontFileExtension(str);
            }
        }
        return this.fontAssetManager;
    }

    public void setDefaultFontFileExtension(String str) {
        this.defaultFontFileExtension = str;
        FontAssetManager fontAssetManager = getFontAssetManager();
        if (fontAssetManager != null) {
            fontAssetManager.setDefaultFontFileExtension(str);
        }
    }

    private Context getContext() {
        Drawable.Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean zIsVisible = isVisible();
        boolean visible = super.setVisible(z, z2);
        if (z) {
            if (this.onVisibleAction == OnVisibleAction.PLAY) {
                playAnimation();
                return visible;
            }
            if (this.onVisibleAction == OnVisibleAction.RESUME) {
                resumeAnimation();
                return visible;
            }
        } else {
            if (this.animator.isRunning()) {
                pauseAnimation();
                this.onVisibleAction = OnVisibleAction.RESUME;
                return visible;
            }
            if (zIsVisible) {
                this.onVisibleAction = OnVisibleAction.NONE;
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.scheduleDrawable(this, runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.unscheduleDrawable(this, runnable);
    }

    private void drawDirectlyToCanvas(Canvas canvas) {
        CompositionLayer compositionLayer = this.compositionLayer;
        LottieComposition lottieComposition = this.composition;
        if (compositionLayer == null || lottieComposition == null) {
            return;
        }
        this.renderingMatrix.reset();
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            float fWidth = bounds.width() / lottieComposition.getBounds().width();
            float fHeight = bounds.height() / lottieComposition.getBounds().height();
            this.renderingMatrix.preTranslate(bounds.left, bounds.top);
            this.renderingMatrix.preScale(fWidth, fHeight);
        }
        compositionLayer.draw(canvas, this.renderingMatrix, this.alpha, null);
    }

    private void renderAndDrawAsBitmap(Canvas canvas, CompositionLayer compositionLayer) {
        if (this.composition == null || compositionLayer == null) {
            return;
        }
        ensureSoftwareRenderingObjectsInitialized();
        canvas.getMatrix(this.softwareRenderingOriginalCanvasMatrix);
        canvas.getClipBounds(this.canvasClipBounds);
        convertRect(this.canvasClipBounds, this.canvasClipBoundsRectF);
        this.softwareRenderingOriginalCanvasMatrix.mapRect(this.canvasClipBoundsRectF);
        convertRect(this.canvasClipBoundsRectF, this.canvasClipBounds);
        if (this.clipToCompositionBounds) {
            this.softwareRenderingTransformedBounds.set(0.0f, 0.0f, getIntrinsicWidth(), getIntrinsicHeight());
        } else {
            compositionLayer.getBounds(this.softwareRenderingTransformedBounds, null, false);
        }
        this.softwareRenderingOriginalCanvasMatrix.mapRect(this.softwareRenderingTransformedBounds);
        Rect bounds = getBounds();
        float fWidth = bounds.width() / getIntrinsicWidth();
        float fHeight = bounds.height() / getIntrinsicHeight();
        scaleRect(this.softwareRenderingTransformedBounds, fWidth, fHeight);
        if (!ignoreCanvasClipBounds()) {
            this.softwareRenderingTransformedBounds.intersect(this.canvasClipBounds.left, this.canvasClipBounds.top, this.canvasClipBounds.right, this.canvasClipBounds.bottom);
        }
        int iCeil = (int) Math.ceil(this.softwareRenderingTransformedBounds.width());
        int iCeil2 = (int) Math.ceil(this.softwareRenderingTransformedBounds.height());
        if (iCeil <= 0 || iCeil2 <= 0) {
            return;
        }
        ensureSoftwareRenderingBitmap(iCeil, iCeil2);
        if (this.isDirty) {
            this.softwareRenderingOriginalCanvasMatrix.getValues(this.softwareRenderingOriginalCanvasMatrixElements);
            float[] fArr = this.softwareRenderingOriginalCanvasMatrixElements;
            float f = fArr[0];
            float f2 = fArr[4];
            this.renderingMatrix.set(this.softwareRenderingOriginalCanvasMatrix);
            this.renderingMatrix.preScale(fWidth, fHeight);
            this.renderingMatrix.postTranslate(-this.softwareRenderingTransformedBounds.left, -this.softwareRenderingTransformedBounds.top);
            this.renderingMatrix.postScale(1.0f / f, 1.0f / f2);
            this.softwareRenderingBitmap.eraseColor(0);
            this.softwareRenderingCanvas.setMatrix(Utils.IDENTITY_MATRIX);
            this.softwareRenderingCanvas.scale(f, f2);
            compositionLayer.draw(this.softwareRenderingCanvas, this.renderingMatrix, this.alpha, null);
            this.softwareRenderingOriginalCanvasMatrix.invert(this.softwareRenderingOriginalCanvasMatrixInverse);
            this.softwareRenderingOriginalCanvasMatrixInverse.mapRect(this.softwareRenderingDstBoundsRectF, this.softwareRenderingTransformedBounds);
            convertRect(this.softwareRenderingDstBoundsRectF, this.softwareRenderingDstBoundsRect);
        }
        this.softwareRenderingSrcBoundsRect.set(0, 0, iCeil, iCeil2);
        canvas.drawBitmap(this.softwareRenderingBitmap, this.softwareRenderingSrcBoundsRect, this.softwareRenderingDstBoundsRect, this.softwareRenderingPaint);
    }

    private void ensureSoftwareRenderingObjectsInitialized() {
        if (this.softwareRenderingCanvas != null) {
            return;
        }
        this.softwareRenderingCanvas = new Canvas();
        this.softwareRenderingTransformedBounds = new RectF();
        this.softwareRenderingOriginalCanvasMatrix = new Matrix();
        this.softwareRenderingOriginalCanvasMatrixInverse = new Matrix();
        this.canvasClipBounds = new Rect();
        this.canvasClipBoundsRectF = new RectF();
        this.softwareRenderingPaint = new LPaint();
        this.softwareRenderingSrcBoundsRect = new Rect();
        this.softwareRenderingDstBoundsRect = new Rect();
        this.softwareRenderingDstBoundsRectF = new RectF();
    }

    private void ensureSoftwareRenderingBitmap(int i, int i2) {
        Bitmap bitmap = this.softwareRenderingBitmap;
        if (bitmap == null || bitmap.getWidth() < i || this.softwareRenderingBitmap.getHeight() < i2) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            this.softwareRenderingBitmap = bitmapCreateBitmap;
            this.softwareRenderingCanvas.setBitmap(bitmapCreateBitmap);
            this.isDirty = true;
            return;
        }
        if (this.softwareRenderingBitmap.getWidth() > i || this.softwareRenderingBitmap.getHeight() > i2) {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(this.softwareRenderingBitmap, 0, 0, i, i2);
            this.softwareRenderingBitmap = bitmapCreateBitmap2;
            this.softwareRenderingCanvas.setBitmap(bitmapCreateBitmap2);
            this.isDirty = true;
        }
    }

    private void convertRect(RectF rectF, Rect rect) {
        rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
    }

    private void convertRect(Rect rect, RectF rectF) {
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    private void scaleRect(RectF rectF, float f, float f2) {
        rectF.set(rectF.left * f, rectF.top * f2, rectF.right * f, rectF.bottom * f2);
    }

    private boolean ignoreCanvasClipBounds() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View)) {
            return false;
        }
        ViewParent parent = ((View) callback).getParent();
        if (parent instanceof ViewGroup) {
            return !((ViewGroup) parent).getClipChildren();
        }
        return false;
    }
}
