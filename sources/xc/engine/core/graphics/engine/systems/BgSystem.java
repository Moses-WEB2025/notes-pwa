package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.sync.FamilyInfo;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.BgRenderer;

/* JADX INFO: compiled from: BgSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 &2\u00020\u0001:\u0002&'B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0015H\u0014J\u0016\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0013H\u0014J\u001e\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0013J\u0010\u0010$\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002J\u0006\u0010%\u001a\u00020\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lxc/engine/core/graphics/engine/systems/BgSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "bgRenderer", "Lxc/engine/core/graphics/engine/renderers/BgRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "bgStyle", "Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "dotTextureId", "", "lineTextureId", "gridTextureId", "bgTileArea", "Landroid/graphics/RectF;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "updateStyles", "color", "style", "onRenderLayer", "", "screenRect", "renderArea", "framebufferId", "framebufferSize", "Landroid/graphics/Point;", "areaWS", "getStyleTextureId", "getBgColor", "Companion", "EBgStyle", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BgSystem extends BaseEngineLayer {
    private static final int BG_TILE_SIZE = 256;
    private static final String TAG = "BgSystem";
    private Color bgColor;
    private final BgRenderer bgRenderer;
    private EBgStyle bgStyle;
    private final RectF bgTileArea;
    private int dotTextureId;
    private int gridTextureId;
    private int lineTextureId;
    private final TileRenderer tileRenderer;

    /* JADX INFO: compiled from: BgSystem.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", FamilyInfo.ROLE_NONE, "Dot", "HorizontalLine", "Grid", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum EBgStyle {
        None,
        Dot,
        HorizontalLine,
        Grid;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EBgStyle> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: BgSystem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EBgStyle.values().length];
            try {
                iArr[EBgStyle.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EBgStyle.Dot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EBgStyle.HorizontalLine.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[EBgStyle.Grid.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BgSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.bgRenderer = engine.getBgRenderer();
        this.tileRenderer = engine.getTileRenderer();
        this.bgStyle = EBgStyle.None;
        this.bgColor = new Color();
        this.bgTileArea = new RectF(0.0f, 0.0f, 120.0f, 120.0f);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.dotTextureId = GLUtils.INSTANCE.createTexture(256, 256);
        this.lineTextureId = GLUtils.INSTANCE.createTexture(256, 256);
        this.gridTextureId = GLUtils.INSTANCE.createTexture(256, 256);
        int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(this.dotTextureId);
        RectF rectF = new RectF(-1.0f, 1.0f, 1.0f, -1.0f);
        GLES30.glViewport(0, 0, 256, 256);
        GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
        BgRenderer.renderStyle$default(this.bgRenderer, rectF, this.bgTileArea, Color.INSTANCE.getTRANSPARENT(), EBgStyle.Dot, 0.3125f, null, 32, null);
        GLUtils.INSTANCE.bindTextureToFramebuffer(iCreateFramebufferFromTexture, this.lineTextureId);
        BgRenderer.renderStyle$default(this.bgRenderer, rectF, this.bgTileArea, Color.INSTANCE.getTRANSPARENT(), EBgStyle.HorizontalLine, 0.3125f, null, 32, null);
        GLUtils.INSTANCE.bindTextureToFramebuffer(iCreateFramebufferFromTexture, this.gridTextureId);
        BgRenderer.renderStyle$default(this.bgRenderer, rectF, this.bgTileArea, Color.INSTANCE.getTRANSPARENT(), EBgStyle.Grid, 0.3125f, null, 32, null);
        GLUtils.INSTANCE.generateMipmap(this.dotTextureId);
        GLUtils.INSTANCE.generateMipmap(this.lineTextureId);
        GLUtils.INSTANCE.generateMipmap(this.gridTextureId);
        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteTexture(this.dotTextureId);
        GLUtils.INSTANCE.deleteTexture(this.lineTextureId);
        GLUtils.INSTANCE.deleteTexture(this.gridTextureId);
        super.onDestroy();
    }

    public final void updateStyles(int color, int style) {
        EBgStyle eBgStyle;
        this.bgColor = new Color(color);
        if (style == 0) {
            eBgStyle = EBgStyle.None;
        } else if (style == 1) {
            eBgStyle = EBgStyle.HorizontalLine;
        } else if (style == 3) {
            eBgStyle = EBgStyle.Grid;
        } else if (style == 4) {
            eBgStyle = EBgStyle.Dot;
        } else {
            eBgStyle = EBgStyle.None;
        }
        this.bgStyle = eBgStyle;
        markDirty();
        getCurveEngine().getTextSystem().markDirty();
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    protected boolean onRenderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        renderArea(getLayerFramebufferId(), new Point(getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel()), screenRect);
        return true;
    }

    public final void renderArea(int framebufferId, Point framebufferSize, final RectF areaWS) {
        Intrinsics.checkNotNullParameter(framebufferSize, "framebufferSize");
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        GLES30.glViewport(0, 0, framebufferSize.x, framebufferSize.y);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES30.glClear(16384);
        float saveRatio = 120.0f / (getCurveEngine().getSaveRatio() / 0.3125f);
        final RectF rectF = new RectF(0.0f, 0.0f, saveRatio, saveRatio);
        if (getCurveEngine().getPageMode() == MiCurveEngine.EPageMode.Infinite) {
            this.bgRenderer.renderTile(new RectF(-1.0f, 1.0f, 1.0f, -1.0f), areaWS, rectF, getStyleTextureId(this.bgStyle), this.bgColor, this.bgStyle);
        } else {
            getCurveEngine().getPageSystem().loopPagesInTargetArea(areaWS, new Function2() { // from class: xc.engine.core.graphics.engine.systems.BgSystem$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return BgSystem.renderArea$lambda$0(areaWS, this, rectF, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderArea$lambda$0(RectF rectF, BgSystem bgSystem, RectF rectF2, PageSystem.PageInfo pageInfo, PageSystem.PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "pageInfo");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        RectF rectF3 = new RectF(pageData.getArea());
        rectF3.inset(-2.0f, -2.0f);
        RectF rectF4 = new RectF(MiMathUtils.INSTANCE.remapRange(rectF3.left, rectF.left, rectF.right, -1.0f, 1.0f), MiMathUtils.INSTANCE.remapRange(rectF3.bottom, rectF.top, rectF.bottom, -1.0f, 1.0f), MiMathUtils.INSTANCE.remapRange(rectF3.right, rectF.left, rectF.right, -1.0f, 1.0f), MiMathUtils.INSTANCE.remapRange(rectF3.top, rectF.top, rectF.bottom, -1.0f, 1.0f));
        if (pageData.getBHasBgImage()) {
            int bgImageId = pageData.getBgImageId() > 0 ? pageData.getBgImageId() : pageData.getBgThumbnailId();
            if (bgImageId > 0) {
                TileRenderer.drawRect$default(bgSystem.tileRenderer, rectF4, new RectF(-1.0f, -1.0f, 1.0f, 1.0f), bgImageId, false, false, 0.0f, 56, null);
            }
        } else {
            rectF3.top -= pageData.getArea().top;
            rectF3.bottom -= pageData.getArea().top;
            bgSystem.bgRenderer.renderTile(rectF4, rectF3, rectF2, bgSystem.getStyleTextureId(pageInfo.getBgStyle()), pageInfo.getBgColor(), pageInfo.getBgStyle());
        }
        return Unit.INSTANCE;
    }

    private final int getStyleTextureId(EBgStyle style) {
        int i = WhenMappings.$EnumSwitchMapping$0[style.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return this.dotTextureId;
        }
        if (i == 3) {
            return this.lineTextureId;
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        return this.gridTextureId;
    }

    public final Color getBgColor() {
        return this.bgColor;
    }
}
