package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewParent;
import com.miui.libhandwritingpage.view.listenerProxy.CanvasOperateListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.GLViewportEventListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.PenPropTransformProxy;
import java.lang.ref.WeakReference;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.api.PenEngine;
import xc.engine.api.listener.IEngineErrorListener;
import xc.engine.api.listener.LoadingIndicatorCallback;
import xc.engine.core.graphics.engine.core.GLViewportEventListener;
import xc.engine.data.IPenPropTransform;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.utils.TransformHintInfoHelper;
import xc.richtext.api.SpannedEditLayout;

/* JADX INFO: loaded from: classes3.dex */
public class PageSurfaceView extends SurfaceView implements SurfaceHolder.Callback, ICanvasOperateListener, IPenPropTransform, GLViewportEventListener {
    private static final String TAG = "PageSurfaceView";
    private LoadingIndicatorCallback loadingIndicatorCallback;
    private ICreationEngine mCreationEngine;
    private int mPageIndex;
    private PageItemView mPageItemView;
    private SurfaceHolder mSurfaceHolder;

    @Override // xc.engine.data.IPenPropTransform
    public int transformColor(int i) {
        return i;
    }

    public PageSurfaceView(Context context) {
        super(context);
        setBackgroundColor((context.getResources().getConfiguration().uiMode & 48) == 32 ? -16777216 : -1);
        initialize();
    }

    private void initialize() {
        initLoadingIndicatorCallback();
        SurfaceHolder holder = getHolder();
        this.mSurfaceHolder = holder;
        holder.addCallback(this);
        setZOrderMediaOverlay(true);
        setFocusable(true);
        this.mSurfaceHolder.setFormat(-3);
        setFocusableInTouchMode(true);
        setTag("PaintingView");
    }

    public void bindPenEngine() {
        ICreationEngine iCreationEngineCreateEngine = PenEngine.createEngine(CreationPageSDK.sApplication, CreationPageSDK.getToken(getContext()), new EngineErrorListener(this));
        this.mCreationEngine = iCreationEngineCreateEngine;
        iCreationEngineCreateEngine.isSurfaceView(true);
        this.mCreationEngine.setSurfaceView(this);
        this.mCreationEngine.getCanvasOperator().setPenPropTransform(new PenPropTransformProxy(this));
        this.mCreationEngine.getCanvasOperator().setCanvasOperateListener(new CanvasOperateListenerProxy(this));
        this.mCreationEngine.hasBindSurfaceViewState(true);
        this.mCreationEngine.getCurveEngine().getCamera().setViewportEventListener(new GLViewportEventListenerProxy(this));
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "surfaceCreated");
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mSurfaceHolder = surfaceHolder;
        getCreationEngine().onSurfaceChanged(i2, i3, TransformHintInfoHelper.getBufferTransformHint(this));
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.v(TAG, "]-> surfaceDestroyed");
        getCreationEngine().onSurfaceDestory();
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int i) {
        onCanvasDataChanged(rectF, i, -1);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int i, int i2) {
        PageItemView pageItemView = this.mPageItemView;
        if (pageItemView != null) {
            pageItemView.onCanvasChanged(i, i2);
        }
        ICreationEngine iCreationEngine = this.mCreationEngine;
        if (iCreationEngine != null) {
            iCreationEngine.getCanvasOperator().renderingUpdate();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mCreationEngine == null) {
            return false;
        }
        ViewParent parent = getParent();
        if (parent instanceof SpannedEditLayout) {
            SpannedEditLayout spannedEditLayout = (SpannedEditLayout) parent;
            if (spannedEditLayout.getSpannedEditModel().isEnableEdit() || spannedEditLayout.getSpannedEditModel().isEnableEditTransient()) {
                return false;
            }
        }
        return this.mCreationEngine.onHandleEvent(motionEvent, true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, boolean z) {
        if (this.mCreationEngine == null) {
            return false;
        }
        ViewParent parent = getParent();
        if (parent instanceof SpannedEditLayout) {
            SpannedEditLayout spannedEditLayout = (SpannedEditLayout) parent;
            if (spannedEditLayout.getSpannedEditModel().isEnableEdit() || spannedEditLayout.getSpannedEditModel().isEnableEditTransient()) {
                return false;
            }
        }
        return this.mCreationEngine.onHandleEvent(motionEvent, z);
    }

    private void initLoadingIndicatorCallback() {
        this.loadingIndicatorCallback = new LoadingIndicatorCallback() { // from class: com.miui.libhandwritingpage.view.PageSurfaceView.1
            @Override // xc.engine.api.listener.LoadingIndicatorCallback
            public void showLoadingIndicator() {
                PageSurfaceView.this.mPageItemView.handleLoading(true);
            }

            @Override // xc.engine.api.listener.LoadingIndicatorCallback
            public void hideLoadingIndicator() {
                PageSurfaceView.this.mPageItemView.handleLoading(false);
            }
        };
    }

    public void release() {
        this.loadingIndicatorCallback = null;
        ICreationEngine iCreationEngine = this.mCreationEngine;
        if (iCreationEngine == null || iCreationEngine.getCurveEngine() == null) {
            return;
        }
        this.mCreationEngine.getCurveEngine().setLoadingIndicatorCallback(null);
    }

    public void bindItemView(PageItemView pageItemView) {
        this.mPageItemView = pageItemView;
        bindPenEngine();
        this.mCreationEngine.getCurveEngine().setLoadingIndicatorCallback(this.loadingIndicatorCallback);
    }

    public ICreationEngine getCreationEngine() {
        return this.mCreationEngine;
    }

    public void setCanvasSize(float f, float f2) {
        ICreationEngine iCreationEngine = this.mCreationEngine;
        if (iCreationEngine != null) {
            iCreationEngine.getCanvasOperator().setCanvasSize(f, f2);
        }
    }

    @Override // xc.engine.core.graphics.engine.core.GLViewportEventListener
    public void onScrollBack() {
        this.mPageItemView.model.onLayout();
    }

    @Override // xc.engine.core.graphics.engine.core.GLViewportEventListener
    public void onTopPageChanged(int i) {
        Log.d(TAG, "xl-- onTopPageChanged: " + i);
        this.mPageItemView.onCurrentPageChanged(i);
    }

    @Override // xc.engine.core.graphics.engine.core.GLViewportEventListener
    public void onTitleChanged() {
        this.mPageItemView.model.updateTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EngineErrorListener implements IEngineErrorListener {
        private final WeakReference<PageSurfaceView> ref;

        public EngineErrorListener(PageSurfaceView pageSurfaceView) {
            this.ref = new WeakReference<>(pageSurfaceView);
        }

        @Override // xc.engine.api.listener.IEngineErrorListener
        public void onError(final int i, String str) {
            CreationPageSDK.with(this.ref.get() == null ? null : this.ref.get().getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageSurfaceView$EngineErrorListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m866x26450162(i);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onError$0$com-miui-libhandwritingpage-view-PageSurfaceView$EngineErrorListener, reason: not valid java name */
        /* synthetic */ void m866x26450162(int i) {
            if (CreationPageSDK.sErrorCallback != null) {
                if (i != 0) {
                    if (i == 1) {
                        CreationPageSDK.sErrorCallback.onError(1);
                    }
                } else {
                    CreationPageSDK.sErrorCallback.onError(0);
                    if (this.ref.get() != null) {
                        this.ref.get().mPageItemView.onPointLimit();
                    }
                }
            }
        }

        @Override // xc.engine.api.listener.IEngineErrorListener
        public void onSuccess() {
            if (CreationPageSDK.sErrorCallback != null) {
                CreationPageSDK.sErrorCallback.onSuccess();
            }
        }
    }
}
