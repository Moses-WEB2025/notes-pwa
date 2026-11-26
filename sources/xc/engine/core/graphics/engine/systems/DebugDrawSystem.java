package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.RectF;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.renderers.DebugDrawRenderer;
import xc.engine.core.graphics.engine.renderers.DebugElement;
import xc.engine.core.graphics.engine.renderers.EDebugDrawType;
import xc.engine.core.graphics.engine.renderers.EDebugShape;
import xc.engine.core.graphics.engine.structs.Tile;

/* JADX INFO: compiled from: DebugDrawSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/systems/DebugDrawSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "debugDrawRenderer", "Lxc/engine/core/graphics/engine/renderers/DebugDrawRenderer;", "elements", "Ljava/util/ArrayList;", "Lxc/engine/core/graphics/engine/renderers/DebugElement;", "Lkotlin/collections/ArrayList;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "preRenderLayer", "onRenderLayer", "", "screenRect", "Landroid/graphics/RectF;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DebugDrawSystem extends BaseEngineLayer {
    public static final int ELEMENT_COUNT = 64;
    public static final boolean ENABLE_DEBUG_DRAW = false;
    private static DebugDrawSystem instance;
    private final DebugDrawRenderer debugDrawRenderer;
    private final ArrayList<DebugElement> elements;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "DebugDrawSystem";
    private static RectF debugRectWS0 = new RectF();

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    protected boolean onRenderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DebugDrawSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.debugDrawRenderer = getCurveEngine().getDebugDrawRenderer();
        this.elements = new ArrayList<>();
    }

    /* JADX INFO: compiled from: DebugDrawSystem.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u0016\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u0016\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nJ\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\nJ\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006$"}, d2 = {"Lxc/engine/core/graphics/engine/systems/DebugDrawSystem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "ENABLE_DEBUG_DRAW", "", "ELEMENT_COUNT", "", "instance", "Lxc/engine/core/graphics/engine/systems/DebugDrawSystem;", "draw", "Lxc/engine/core/graphics/engine/renderers/DebugElement;", "id", "drawRect", "rect", "Landroid/graphics/RectF;", "fillRect", "drawCircle", "fillCircle", "clear", "", "deactivate", "debugRectWS0", "getDebugRectWS0", "()Landroid/graphics/RectF;", "setDebugRectWS0", "(Landroid/graphics/RectF;)V", "debugDraw", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "debugDrawLayers", "debugDrawInfiniteBorder", "debugDrawMultiPageArea", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void debugDraw(MiCurveEngine curveEngine) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
        }

        private Companion() {
        }

        private final DebugElement draw(int id) {
            ArrayList arrayList;
            if (id < 0 || 64 < id) {
                id = 64;
            }
            DebugDrawSystem debugDrawSystem = DebugDrawSystem.instance;
            DebugElement debugElement = (debugDrawSystem == null || (arrayList = debugDrawSystem.elements) == null) ? null : (DebugElement) arrayList.get(id);
            Intrinsics.checkNotNull(debugElement);
            debugElement.setBActive(true);
            debugElement.setDrawType(EDebugDrawType.Outline);
            debugElement.setColor(Color.INSTANCE.getBLACK());
            debugElement.setOutlineWidth(2.0f);
            return debugElement;
        }

        public final DebugElement drawRect(int id) {
            DebugElement debugElementDraw = draw(id);
            debugElementDraw.setShape(EDebugShape.Rect);
            return debugElementDraw;
        }

        public final DebugElement drawRect(int id, RectF rect) {
            Intrinsics.checkNotNullParameter(rect, "rect");
            return drawRect(id).setCenter(rect.centerX(), rect.centerY()).setSize(rect.width(), rect.height());
        }

        public final DebugElement fillRect(int id) {
            return drawRect(id).setDrawType(EDebugDrawType.Fill);
        }

        public final DebugElement fillRect(int id, RectF rect) {
            Intrinsics.checkNotNullParameter(rect, "rect");
            return fillRect(id).setCenter(rect.centerX(), rect.centerY()).setSize(rect.width(), rect.height());
        }

        public final DebugElement drawCircle(int id) {
            DebugElement debugElementDraw = draw(id);
            debugElementDraw.setShape(EDebugShape.Cycle);
            return debugElementDraw;
        }

        public final DebugElement fillCircle(int id) {
            return drawCircle(id).setDrawType(EDebugDrawType.Fill);
        }

        public final void clear() {
            ArrayList arrayList;
            DebugDrawSystem debugDrawSystem = DebugDrawSystem.instance;
            if (debugDrawSystem == null || (arrayList = debugDrawSystem.elements) == null) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((DebugElement) it.next()).setBActive(false);
            }
        }

        public final void deactivate(int id) {
            ArrayList arrayList;
            DebugElement debugElement;
            DebugDrawSystem debugDrawSystem = DebugDrawSystem.instance;
            if (debugDrawSystem == null || (arrayList = debugDrawSystem.elements) == null || (debugElement = (DebugElement) arrayList.get(id)) == null) {
                return;
            }
            debugElement.setBActive(false);
        }

        public final RectF getDebugRectWS0() {
            return DebugDrawSystem.debugRectWS0;
        }

        public final void setDebugRectWS0(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            DebugDrawSystem.debugRectWS0 = rectF;
        }

        private final void debugDrawLayers(MiCurveEngine curveEngine) {
            fillRect(0, new RectF(0.0f, 0.0f, 210.0f, 1000.0f)).setColor(0.1f, 0.5f, 0.1f);
            drawRect(1, new RectF(10.0f, 0.0f, 200.0f, 200.0f)).setTexture(curveEngine.getBgSystem().getLayerOutTextureId());
            drawRect(2, new RectF(10.0f, 210.0f, 200.0f, 410.0f)).setTexture(curveEngine.getCurveSystem().getMainCurveLayer().getLayerOutTextureId());
            drawRect(3, new RectF(10.0f, 420.0f, 200.0f, 620.0f)).setTexture(curveEngine.getPageSystem().getLayerOutTextureId());
            drawRect(4, new RectF(10.0f, 630.0f, 200.0f, 840.0f)).setTexture(curveEngine.getComposeSystem().getLayerOutTextureId());
        }

        private final void debugDrawInfiniteBorder(MiCurveEngine curveEngine) {
            if (curveEngine.isInfinite()) {
                drawRect(0, curveEngine.getScreenSpaceRectFromWorldSpaceRect(curveEngine.getDataManager().getInfiniteBoundary()));
            }
        }

        private final void debugDrawMultiPageArea(final MiCurveEngine curveEngine) {
            if (curveEngine.isInfinite()) {
                return;
            }
            final RectF destroyArea = curveEngine.getCurveSystem().getMainCurveLayer().getDestroyArea();
            final RectF rectF = new RectF(0.0f, 0.0f, 500.0f, (destroyArea.height() * 500.0f) / destroyArea.width());
            RectF screenAreaWS = curveEngine.getScreenAreaWS();
            RectF rectFRemapArea = MiMathUtils.INSTANCE.remapArea(screenAreaWS, destroyArea, rectF);
            RectF rectFRemapArea2 = MiMathUtils.INSTANCE.remapArea(curveEngine.getCurveSystem().getMainCurveLayer().getPreloadArea(), destroyArea, rectF);
            drawRect(1, rectF).setColor(Color.INSTANCE.getRED());
            drawRect(2, rectFRemapArea2).setColor(Color.INSTANCE.getBLUE());
            drawRect(3, rectFRemapArea).setColor(Color.INSTANCE.getGREEN()).setOutlineWidth(1.5f);
            if (curveEngine.isInfinite()) {
                return;
            }
            final Ref.IntRef intRef = new Ref.IntRef();
            intRef.element = 4;
            curveEngine.getPageSystem().loopPagesInTargetArea(destroyArea, new Function2() { // from class: xc.engine.core.graphics.engine.systems.DebugDrawSystem$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return DebugDrawSystem.Companion.debugDrawMultiPageArea$lambda$1(destroyArea, rectF, intRef, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
                }
            });
            curveEngine.getCurveSystem().getMainCurveLayer().loopAreaWithTileCoord(screenAreaWS, new Function2() { // from class: xc.engine.core.graphics.engine.systems.DebugDrawSystem$Companion$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return DebugDrawSystem.Companion.debugDrawMultiPageArea$lambda$2(intRef, curveEngine, destroyArea, rectF, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
                }
            });
            if (intRef.element <= 40) {
                for (int i = intRef.element; i < 41; i++) {
                    deactivate(i);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit debugDrawMultiPageArea$lambda$1(RectF rectF, RectF rectF2, Ref.IntRef intRef, PageSystem.PageInfo pageInfo, PageSystem.PageData pageData) {
            Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
            Intrinsics.checkNotNullParameter(pageData, "pageData");
            DebugDrawSystem.INSTANCE.drawRect(intRef.element, MiMathUtils.INSTANCE.remapArea(pageData.getArea(), rectF, rectF2)).setColor(Color.INSTANCE.getBLACK());
            intRef.element++;
            int i = intRef.element;
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit debugDrawMultiPageArea$lambda$2(Ref.IntRef intRef, MiCurveEngine miCurveEngine, RectF rectF, RectF rectF2, int i, int i2) {
            if (intRef.element <= 40 && miCurveEngine.getCurveSystem().getMainCurveLayer().hasTile(i, i2)) {
                Tile tile = miCurveEngine.getCurveSystem().getMainCurveLayer().getTile(i, i2);
                MiMathUtils.Companion companion = MiMathUtils.INSTANCE;
                TileSystem mainCurveLayer = miCurveEngine.getCurveSystem().getMainCurveLayer();
                Intrinsics.checkNotNull(tile);
                RectF rectFRemapArea = companion.remapArea(mainCurveLayer.getTileArea(tile), rectF, rectF2);
                rectFRemapArea.inset(2.0f, 2.0f);
                DebugDrawSystem.INSTANCE.drawRect(intRef.element, rectFRemapArea).setColor(tile.getBDummy() ? Color.INSTANCE.getYELLOW() : Color.INSTANCE.getBLUE());
                intRef.element++;
                int i3 = intRef.element;
            }
            return Unit.INSTANCE;
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        instance = this;
        for (int i = 0; i < 65; i++) {
            this.elements.add(new DebugElement());
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    public void preRenderLayer() {
        setBIsDirty(true);
    }
}
