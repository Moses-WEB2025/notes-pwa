package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import androidx.graphics.lowlatency.BufferInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpage.view.GLTextureView;
import com.miui.libhandwritingpage.view.listenerProxy.CanvasOperateListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.PenPropTransformProxy;
import com.miui.libhandwritingpreview.PreGLViewportEventListenerProxy;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.system.GLViewportEventListener;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.listener.LoadingIndicatorCallback;
import xc.engine.api.listener.RequestDrawCallback;
import xc.engine.data.IPenPropTransform;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.utils.XLog;
import xc.richtext.api.SpannedEditLayout;

/* JADX INFO: compiled from: PagePreTextureView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u0000 42\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u00014B\u0011\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0019H\u0016J\u001a\u0010!\u001a\u00020\u001d2\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020\u0019H\u0016J\"\u0010!\u001a\u00020\u001d2\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0019H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016J\u0018\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020'J\u0010\u0010+\u001a\u00020\u001d2\b\u0010,\u001a\u0004\u0018\u00010\u0011J\u0016\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/J\b\u00101\u001a\u00020\u001dH\u0016J\u0010\u00102\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u0019H\u0016J\b\u00103\u001a\u00020\u001dH\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/miui/libhandwritingpreview/view/PagePreTextureView;", "Lcom/miui/libhandwritingpage/view/GLTextureView;", "Lxc/engine/operate/canvas/ICanvasOperateListener;", "Lxc/engine/data/IPenPropTransform;", "Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;", "Lxc/engine/api/listener/RequestDrawCallback;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "creationEngine", "Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "getCreationEngine", "()Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "setCreationEngine", "(Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;)V", "mPageItemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "mBufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "getMBufferInfo", "()Landroidx/graphics/lowlatency/BufferInfo;", "setMBufferInfo", "(Landroidx/graphics/lowlatency/BufferInfo;)V", "mPageIndex", "", "mRender", "Lcom/miui/libhandwritingpage/view/GLTextureView$Renderer;", "initialize", "", "bindPenEngine", "transformColor", "color", "onCanvasDataChanged", "rectF", "Landroid/graphics/RectF;", "canvasChangedType", "pageIndex", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "needDelSelectCurves", "bindItemView", "itemView", "setCanvasSize", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "onScrollBack", "onTopPageChanged", "onTitleChanged", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreTextureView extends GLTextureView implements ICanvasOperateListener, IPenPropTransform, GLViewportEventListener, RequestDrawCallback {
    private static final String TAG;
    private PreICreationEngine creationEngine;
    private BufferInfo mBufferInfo;
    private final int mPageIndex;
    private PagePreItemView mPageItemView;
    private GLTextureView.Renderer mRender;

    @Override // xc.engine.data.IPenPropTransform
    public int transformColor(int color) {
        return color;
    }

    public PagePreTextureView(Context context) {
        super(context);
        initialize();
    }

    public final PreICreationEngine getCreationEngine() {
        return this.creationEngine;
    }

    public final void setCreationEngine(PreICreationEngine preICreationEngine) {
        this.creationEngine = preICreationEngine;
    }

    public final BufferInfo getMBufferInfo() {
        return this.mBufferInfo;
    }

    public final void setMBufferInfo(BufferInfo bufferInfo) {
        this.mBufferInfo = bufferInfo;
    }

    private final void initialize() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(false);
        setScaleY(-1.0f);
        GLTextureView.Renderer renderer = new GLTextureView.Renderer() { // from class: com.miui.libhandwritingpreview.view.PagePreTextureView.initialize.1
            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                PreMiCurveEngine curveEngine;
                Intrinsics.checkNotNullParameter(gl, "gl");
                Intrinsics.checkNotNullParameter(config, "config");
                PreICreationEngine creationEngine = PagePreTextureView.this.getCreationEngine();
                if (creationEngine == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
                    return;
                }
                EGLContext mEglContext = PagePreTextureView.this.mGLThread.mEglHelper.mEglContext;
                Intrinsics.checkNotNullExpressionValue(mEglContext, "mEglContext");
                curveEngine.setGlContext(mEglContext);
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                Intrinsics.checkNotNullParameter(gl, "gl");
                XLog.v(PagePreTextureView.TAG, "onSurfaceChanged: " + width + ' ' + height, new Object[0]);
                PagePreTextureView.this.setMBufferInfo(new BufferInfo(width, height, 0));
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public boolean onDrawFrame(GL10 var1) {
                PreMiCurveEngine curveEngine;
                Intrinsics.checkNotNullParameter(var1, "var1");
                PreICreationEngine creationEngine = PagePreTextureView.this.getCreationEngine();
                if (creationEngine == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
                    return false;
                }
                BufferInfo mBufferInfo = PagePreTextureView.this.getMBufferInfo();
                Intrinsics.checkNotNull(mBufferInfo);
                curveEngine.onDrawTextureViewFrame(mBufferInfo);
                return false;
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onEngineFinished() {
                PreMiCurveEngine curveEngine;
                PreMiCurveEngine curveEngine2;
                XLog.v(PagePreTextureView.TAG, "onEngineFinished: ", new Object[0]);
                PreICreationEngine creationEngine = PagePreTextureView.this.getCreationEngine();
                if (creationEngine != null && (curveEngine2 = creationEngine.getCurveEngine()) != null) {
                    curveEngine2.setRequestDrawCallback(null);
                }
                PreICreationEngine creationEngine2 = PagePreTextureView.this.getCreationEngine();
                if (creationEngine2 == null || (curveEngine = creationEngine2.getCurveEngine()) == null) {
                    return;
                }
                curveEngine.destroy();
            }

            @Override // com.miui.libhandwritingpage.view.GLTextureView.Renderer
            public void onSurfaceDestroyed() {
                XLog.v(PagePreTextureView.TAG, "onSurfaceDestroyed: ", new Object[0]);
                PreICreationEngine creationEngine = PagePreTextureView.this.getCreationEngine();
                if (creationEngine != null) {
                    creationEngine.onSurfaceDestory();
                }
            }
        };
        this.mRender = renderer;
        setRenderer(renderer);
        setRenderMode(0);
    }

    private final void bindPenEngine() {
        PreICreationEngine preICreationEngineCreateEngine = PenPreEngine.createEngine(getContext().getApplicationContext(), CreationPageSDK.getToken(getContext()));
        this.creationEngine = preICreationEngineCreateEngine;
        if (preICreationEngineCreateEngine != null) {
            ICanvasOperator canvasOperator = preICreationEngineCreateEngine.getCanvasOperator();
            if (canvasOperator != null) {
                canvasOperator.setPenPropTransform(new PenPropTransformProxy(this));
                canvasOperator.setCanvasOperateListener(new CanvasOperateListenerProxy(this));
            }
            PreMiCurveEngine curveEngine = preICreationEngineCreateEngine.getCurveEngine();
            if (curveEngine != null) {
                curveEngine.getCamera().setViewportEventListener(new PreGLViewportEventListenerProxy(this));
                curveEngine.setRequestDrawCallback(this);
            }
        }
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int canvasChangedType) {
        onCanvasDataChanged(rectF, canvasChangedType, -1);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int canvasChangedType, int pageIndex) {
        PagePreItemView pagePreItemView = this.mPageItemView;
        if (pagePreItemView != null) {
            pagePreItemView.onCanvasChanged(canvasChangedType, pageIndex);
        }
        requestRender();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.creationEngine == null) {
            return false;
        }
        ViewParent parent = getParent();
        if (parent instanceof SpannedEditLayout) {
            SpannedEditLayout spannedEditLayout = (SpannedEditLayout) parent;
            if (spannedEditLayout.getSpannedEditModel().isEnableEdit() || spannedEditLayout.getSpannedEditModel().isEnableEditTransient()) {
                return false;
            }
        }
        PreICreationEngine preICreationEngine = this.creationEngine;
        Intrinsics.checkNotNull(preICreationEngine);
        return preICreationEngine.onHandleEvent(event, true);
    }

    public final boolean onTouchEvent(MotionEvent event, boolean needDelSelectCurves) {
        if (this.creationEngine == null) {
            return false;
        }
        ViewParent parent = getParent();
        if (parent instanceof SpannedEditLayout) {
            SpannedEditLayout spannedEditLayout = (SpannedEditLayout) parent;
            if (spannedEditLayout.getSpannedEditModel().isEnableEdit() || spannedEditLayout.getSpannedEditModel().isEnableEditTransient()) {
                return false;
            }
        }
        PreICreationEngine preICreationEngine = this.creationEngine;
        if (preICreationEngine != null) {
            return preICreationEngine.onHandleEvent(event, needDelSelectCurves);
        }
        return false;
    }

    public final void bindItemView(PagePreItemView itemView) {
        PreMiCurveEngine curveEngine;
        this.mPageItemView = itemView;
        bindPenEngine();
        PreICreationEngine preICreationEngine = this.creationEngine;
        if (preICreationEngine == null || (curveEngine = preICreationEngine.getCurveEngine()) == null) {
            return;
        }
        curveEngine.setLoadingIndicatorCallback(new LoadingIndicatorCallback() { // from class: com.miui.libhandwritingpreview.view.PagePreTextureView.bindItemView.1
            @Override // xc.engine.api.listener.LoadingIndicatorCallback
            public void showLoadingIndicator() {
                PagePreItemView pagePreItemView = PagePreTextureView.this.mPageItemView;
                if (pagePreItemView != null) {
                    pagePreItemView.handleLoading(true);
                }
            }

            @Override // xc.engine.api.listener.LoadingIndicatorCallback
            public void hideLoadingIndicator() {
                PagePreItemView pagePreItemView = PagePreTextureView.this.mPageItemView;
                if (pagePreItemView != null) {
                    pagePreItemView.handleLoading(false);
                }
            }
        });
    }

    public final void setCanvasSize(float width, float height) {
        ICanvasOperator canvasOperator;
        PreICreationEngine preICreationEngine = this.creationEngine;
        if (preICreationEngine == null || (canvasOperator = preICreationEngine.getCanvasOperator()) == null) {
            return;
        }
        canvasOperator.setCanvasSize(width, height);
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onScrollBack() {
        PageInkPreModel pageInkPreModel;
        PagePreItemView pagePreItemView = this.mPageItemView;
        if (pagePreItemView == null || (pageInkPreModel = pagePreItemView.model) == null) {
            return;
        }
        pageInkPreModel.onLayout();
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onTopPageChanged(int pageIndex) {
        PageInkPreModel pageInkPreModel;
        Log.d(TAG, "xl-- onTopPageChanged: " + pageIndex);
        PagePreItemView pagePreItemView = this.mPageItemView;
        if (pagePreItemView == null || (pageInkPreModel = pagePreItemView.model) == null) {
            return;
        }
        pageInkPreModel.onCurrentPageChanged(pageIndex);
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onTitleChanged() {
        PageInkPreModel pageInkPreModel;
        PagePreItemView pagePreItemView = this.mPageItemView;
        if (pagePreItemView == null || (pageInkPreModel = pagePreItemView.model) == null) {
            return;
        }
        pageInkPreModel.updateTitle();
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PagePreTextureView", "getSimpleName(...)");
        TAG = "PagePreTextureView";
    }
}
