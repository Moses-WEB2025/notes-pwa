package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import androidx.graphics.lowlatency.BufferInfo;
import com.miui.libhandwritingpage.view.listenerProxy.CanvasOperateListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.GLViewportEventListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.PenPropTransformProxy;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.api.PenEngine;
import xc.engine.api.listener.IEngineErrorListener;
import xc.engine.api.listener.RequestDrawCallback;
import xc.engine.core.graphics.engine.core.GLViewportEventListener;
import xc.engine.data.IPenPropTransform;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.utils.TransformHintInfoHelper;
import xc.engine.utils.XLog;
import xc.richtext.api.SpannedEditLayout;

/* JADX INFO: loaded from: classes3.dex */
public class PageTextureView extends GLTextureView implements ICanvasOperateListener, IPenPropTransform, GLViewportEventListener, RequestDrawCallback {
    private static final String TAG = "PageTextureView";
    BufferInfo mBufferInfo;
    private ICreationEngine mCreationEngine;
    private int mPageIndex;
    private PageItemView mPageItemView;
    private GLTextureView.Renderer mRender;

    @Override // xc.engine.data.IPenPropTransform
    public int transformColor(int i) {
        return i;
    }

    public PageTextureView(Context context) {
        super(context);
        initialize();
    }

    @Override // com.miui.libhandwritingpage.view.GLTextureView, android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        XLog.i(TAG, "onSurfaceTextureUpdated", new Object[0]);
        super.onSurfaceTextureUpdated(surfaceTexture);
    }

    private void initialize() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(false);
        setScaleY(-1.0f);
        GLTextureView.Renderer renderer = new GLTextureView.Renderer() { // from class: com.miui.libhandwritingpage.view.PageTextureView.1
            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onEngineFinished() {
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceChanged(GL10 gl10, int i, int i2) {
                PageTextureView.this.mBufferInfo = new BufferInfo(i, i2, 0);
                PageTextureView.this.getCreationEngine().onSurfaceChanged(i, i2, TransformHintInfoHelper.getBufferTransformHint(PageTextureView.this));
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public boolean onDrawFrame(GL10 gl10) {
                PageTextureView.this.mCreationEngine.getCurveEngine().glContext = PageTextureView.this.mGLThread.mEglHelper.mEglContext;
                GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES30.glClear(16384);
                PageTextureView.this.mCreationEngine.getCurveEngine().onDrawTextureViewFrame(PageTextureView.this.mBufferInfo);
                return false;
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceDestroyed() {
                PageTextureView.this.getCreationEngine().onSurfaceDestory();
                PageTextureView.this.mCreationEngine.getCurveEngine().setRequestDrawCallback(null);
            }
        };
        this.mRender = renderer;
        setRenderer(renderer);
        setRenderMode(0);
    }

    public void bindPenEngine() {
        ICreationEngine iCreationEngineCreateEngine = PenEngine.createEngine(CreationPageSDK.sApplication, CreationPageSDK.getToken(getContext()), new EngineErrorListener(this));
        this.mCreationEngine = iCreationEngineCreateEngine;
        iCreationEngineCreateEngine.isSurfaceView(false);
        this.mCreationEngine.getCanvasOperator().setPenPropTransform(new PenPropTransformProxy(this));
        this.mCreationEngine.getCanvasOperator().setCanvasOperateListener(new CanvasOperateListenerProxy(this));
        this.mCreationEngine.getCurveEngine().getCamera().setViewportEventListener(new GLViewportEventListenerProxy(this));
        this.mCreationEngine.getCurveEngine().setRequestDrawCallback(this);
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
        requestRender();
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

    public void bindItemView(PageItemView pageItemView) {
        this.mPageItemView = pageItemView;
        bindPenEngine();
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
        private final WeakReference<PageTextureView> ref;

        public EngineErrorListener(PageTextureView pageTextureView) {
            this.ref = new WeakReference<>(pageTextureView);
        }

        @Override // xc.engine.api.listener.IEngineErrorListener
        public void onError(final int i, String str) {
            CreationPageSDK.with(this.ref.get() == null ? null : this.ref.get().getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageTextureView$EngineErrorListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m867xe9c1ffd0(i);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onError$0$com-miui-libhandwritingpage-view-PageTextureView$EngineErrorListener, reason: not valid java name */
        /* synthetic */ void m867xe9c1ffd0(int i) {
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
