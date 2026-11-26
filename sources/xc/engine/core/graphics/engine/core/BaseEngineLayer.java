package xc.engine.core.graphics.engine.core;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: BaseEngineStruct.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u001e\u001a\u00020\u001fH\u0014J\b\u0010 \u001a\u00020\u001fH\u0016J\b\u0010!\u001a\u00020\u001fH\u0016J\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001dJ\u0010\u0010$\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001dH\u0014J\u0006\u0010%\u001a\u00020\u0011J\u0006\u0010&\u001a\u00020\u0011J\u0006\u0010'\u001a\u00020\u001fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "getTexturePool", "()Lxc/engine/core/graphics/engine/core/TexturePool;", "layerTexture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "getLayerTexture", "()Lxc/engine/core/graphics/engine/core/ScreenTexture;", "setLayerTexture", "(Lxc/engine/core/graphics/engine/core/ScreenTexture;)V", "layerFramebufferId", "", "getLayerFramebufferId", "()I", "setLayerFramebufferId", "(I)V", "bIsDirty", "", "getBIsDirty", "()Z", "setBIsDirty", "(Z)V", "lastFrameArea", "Landroid/graphics/RectF;", "onDestroy", "", "onScreenResize", "preRenderLayer", "renderLayer", "screenRect", "onRenderLayer", "getLayerOutTextureId", "getLayerTargetFBOId", "markDirty", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseEngineLayer extends BaseEngineStruct {
    private boolean bIsDirty;
    private RectF lastFrameArea;
    private int layerFramebufferId;
    private ScreenTexture layerTexture;
    private final TexturePool texturePool;

    protected boolean onRenderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseEngineLayer(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.texturePool = engine.getTexturePool();
        this.layerTexture = new ScreenTexture(0, 0, 0, 7, null);
        this.bIsDirty = true;
        this.lastFrameArea = new RectF();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final TexturePool getTexturePool() {
        return this.texturePool;
    }

    protected final ScreenTexture getLayerTexture() {
        return this.layerTexture;
    }

    protected final void setLayerTexture(ScreenTexture screenTexture) {
        Intrinsics.checkNotNullParameter(screenTexture, "<set-?>");
        this.layerTexture = screenTexture;
    }

    protected final int getLayerFramebufferId() {
        return this.layerFramebufferId;
    }

    protected final void setLayerFramebufferId(int i) {
        this.layerFramebufferId = i;
    }

    protected final boolean getBIsDirty() {
        return this.bIsDirty;
    }

    protected final void setBIsDirty(boolean z) {
        this.bIsDirty = z;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteFramebuffer(this.layerFramebufferId);
        this.texturePool.recycleScreenTexture(this.layerTexture);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        this.texturePool.recycleScreenTexture(this.layerTexture);
        this.layerTexture = new ScreenTexture(0, 0, 0, 7, null);
        markDirty();
    }

    public void preRenderLayer() {
        if (!Intrinsics.areEqual(this.lastFrameArea, getCurveEngine().getScreenAreaWS())) {
            this.bIsDirty = true;
        }
        if (!this.layerTexture.isValid()) {
            this.layerTexture = acquireScreenTexture();
            if (this.layerFramebufferId <= 0) {
                this.layerFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.layerTexture.getTexId());
                return;
            } else {
                GLUtils.INSTANCE.bindTextureToFramebuffer(this.layerFramebufferId, this.layerTexture.getTexId());
                return;
            }
        }
        if (this.layerTexture.getWidth() == ((int) getCurveEngine().getCamera().getSizeInPixel().x) && this.layerTexture.getHeight() == ((int) getCurveEngine().getCamera().getSizeInPixel().y)) {
            return;
        }
        this.texturePool.recycleScreenTexture(this.layerTexture);
        this.layerTexture = acquireScreenTexture();
    }

    public final void renderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        preRenderLayer();
        if (this.bIsDirty && onRenderLayer(screenRect)) {
            this.lastFrameArea = screenRect;
            this.bIsDirty = false;
        }
    }

    public final int getLayerOutTextureId() {
        return this.layerTexture.getTexId();
    }

    public final int getLayerTargetFBOId() {
        return this.layerFramebufferId;
    }

    public final void markDirty() {
        this.bIsDirty = true;
    }
}
