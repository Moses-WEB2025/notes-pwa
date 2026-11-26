package xc.engine.core.graphics.engine.systems;

import android.opengl.GLES30;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.core.ScreenTexture;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.BlendRenderer;

/* JADX INFO: compiled from: ComposeSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001f B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0006\u0010\u0010\u001a\u00020\u000eJ\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J2\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00132\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001cJ2\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00132\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001cR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/graphics/engine/systems/ComposeSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "blendRenderer", "Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "texture1", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "texture2", "onDestroy", "", "onScreenResize", "beginComposeLayer", "blendLayer", "textureId", "", "mode", "Lxc/engine/core/graphics/engine/systems/ComposeSystem$EBlendMode;", "blendLayerBelow", "multiplyBlend", "outFramebuffer", "bgTextureId", "fgTextureId", "bFlipBgY", "", "bFlipFgY", "normalBlend", "Companion", "EBlendMode", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ComposeSystem extends BaseEngineLayer {
    private static final String TAG = "ComposeSystem";
    private final BlendRenderer blendRenderer;
    private ScreenTexture texture1;
    private ScreenTexture texture2;
    private final TileRenderer tileRenderer;

    /* JADX INFO: compiled from: ComposeSystem.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lxc/engine/core/graphics/engine/systems/ComposeSystem$EBlendMode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Normal", "Multiply", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum EBlendMode {
        Normal,
        Multiply;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EBlendMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.tileRenderer = getCurveEngine().getTileRenderer();
        this.blendRenderer = getCurveEngine().getBlendRenderer();
        int i = 0;
        int i2 = 0;
        this.texture1 = new ScreenTexture(0, i, i2, 7, null);
        this.texture2 = new ScreenTexture(i, i2, 0, 7, null);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        getTexturePool().recycleScreenTexture(this.texture1);
        getTexturePool().recycleScreenTexture(this.texture2);
        setLayerTexture(new ScreenTexture(0, 0, 0, 7, null));
        super.onDestroy();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        getTexturePool().recycleScreenTexture(this.texture1);
        getTexturePool().recycleScreenTexture(this.texture2);
        setLayerTexture(new ScreenTexture(0, 0, 0, 7, null));
        super.onScreenResize();
    }

    public final void beginComposeLayer() {
        if (!getLayerTexture().isValid()) {
            this.texture1 = acquireScreenTexture();
            this.texture2 = acquireScreenTexture();
            setLayerTexture(this.texture1);
            if (getLayerFramebufferId() <= 0) {
                setLayerFramebufferId(GLUtils.INSTANCE.createFramebufferFromTexture(getLayerTexture().getTexId()));
            } else {
                GLUtils.INSTANCE.bindTextureToFramebuffer(getLayerFramebufferId(), getLayerTexture().getTexId());
            }
        }
        GLES30.glBindFramebuffer(36160, getLayerFramebufferId());
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
    }

    public static /* synthetic */ void blendLayer$default(ComposeSystem composeSystem, int i, EBlendMode eBlendMode, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            eBlendMode = EBlendMode.Normal;
        }
        composeSystem.blendLayer(i, eBlendMode);
    }

    public final void blendLayer(int textureId, EBlendMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (mode == EBlendMode.Normal) {
            GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
            TileRenderer.drawFullScreen$default(this.tileRenderer, textureId, true, false, 0.0f, 12, null);
        } else if (mode == EBlendMode.Multiply) {
            int texId = getLayerTexture().getTexId();
            setLayerTexture(getLayerTexture().getTexId() == this.texture1.getTexId() ? this.texture2 : this.texture1);
            GLUtils.INSTANCE.bindTextureToFramebuffer(getLayerFramebufferId(), getLayerTexture().getTexId());
            BlendRenderer.doMultiplyBlend$default(this.blendRenderer, getLayerFramebufferId(), texId, textureId, false, false, 24, null);
        }
    }

    public final void blendLayerBelow(int textureId) {
        int texId = getLayerTexture().getTexId();
        setLayerTexture(getLayerTexture().getTexId() == this.texture1.getTexId() ? this.texture2 : this.texture1);
        GLUtils.INSTANCE.bindTextureToFramebuffer(getLayerFramebufferId(), getLayerTexture().getTexId());
        BlendRenderer.doNormalBlend$default(this.blendRenderer, getLayerFramebufferId(), textureId, texId, false, false, 24, null);
    }

    public static /* synthetic */ void multiplyBlend$default(ComposeSystem composeSystem, int i, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            z = false;
        }
        if ((i4 & 16) != 0) {
            z2 = false;
        }
        composeSystem.multiplyBlend(i, i2, i3, z, z2);
    }

    public final void multiplyBlend(int outFramebuffer, int bgTextureId, int fgTextureId, boolean bFlipBgY, boolean bFlipFgY) {
        this.blendRenderer.doMultiplyBlend(outFramebuffer, bgTextureId, fgTextureId, bFlipBgY, bFlipFgY);
    }

    public static /* synthetic */ void normalBlend$default(ComposeSystem composeSystem, int i, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            z = false;
        }
        if ((i4 & 16) != 0) {
            z2 = false;
        }
        composeSystem.normalBlend(i, i2, i3, z, z2);
    }

    public final void normalBlend(int outFramebuffer, int bgTextureId, int fgTextureId, boolean bFlipBgY, boolean bFlipFgY) {
        this.blendRenderer.doNormalBlend(outFramebuffer, bgTextureId, fgTextureId, bFlipBgY, bFlipFgY);
    }
}
