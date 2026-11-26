package xc.engine.core.graphics.engine.systems;

import android.graphics.RectF;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.util.a;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.DataManager;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: CurveSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 82\u00020\u0001:\u000389:B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0019\u001a\u00020\u001aH\u0014J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0011J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0010J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u0011J,\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020\u00142\b\b\u0002\u0010#\u001a\u00020\u00142\b\b\u0002\u0010$\u001a\u00020\u0014J\u0016\u0010)\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010*\u001a\u00020(J\u0016\u0010+\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020.J\u0006\u0010/\u001a\u00020\u001aJ\u0010\u00100\u001a\u00020\u001a2\b\b\u0002\u00101\u001a\u00020\u0014J\u000e\u00102\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u0010J\u0006\u00104\u001a\u00020\u001aJ\u000e\u00105\u001a\u00020\u001a2\u0006\u00106\u001a\u00020\u0011J\u0006\u00107\u001a\u00020\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010%\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lxc/engine/core/graphics/engine/systems/CurveSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "mainCurveLayer", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "getMainCurveLayer", "()Lxc/engine/core/graphics/engine/systems/TileSystem;", "markCurveLayer", "getMarkCurveLayer", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "unstableAreaMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Landroid/graphics/RectF;", "unstableAreaId", "needReRender", "", "getNeedReRender", "()Z", "setNeedReRender", "(Z)V", "onDestroy", "", "addUnstableArea", a.e, "removeUnstableArea", "id", "isAreaUnstable", "updateArea", "areaWS", "bUpdateFullScreen", "bUpdateTiles", "isAsyncTask", "currentDebugId", "lastEraserRect", "lastEraserArea", "", "updateAreaLocallyForEraser", "erasingMaxPenSize", "updateNewCurve", "saveRect", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "initUpdate", "update", "skipExistedTile", "onEntLoaded", "pageIndex", "clearAllTiles", "renderLayer", "screenRect", "shouldShowFullscreenTile", "Companion", "MainCurveFetcher", "MarkCurveFetcher", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CurveSystem extends BaseEngineStruct {
    private static final String TAG = "CurveSystem";
    private int currentDebugId;
    private float lastEraserArea;
    private RectF lastEraserRect;
    private final CoroutineScope mScope;
    private final TileSystem mainCurveLayer;
    private final TileSystem markCurveLayer;
    private boolean needReRender;
    private int unstableAreaId;
    private final ConcurrentHashMap<Integer, RectF> unstableAreaMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurveSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        TileSystem tileSystem = new TileSystem(engine, new MainCurveFetcher(getCurveEngine()));
        this.mainCurveLayer = tileSystem;
        TileSystem tileSystem2 = new TileSystem(engine, new MarkCurveFetcher(getCurveEngine()));
        this.markCurveLayer = tileSystem2;
        tileSystem.setSystemName("MainCurveLayer");
        tileSystem2.setSystemName("MarkCurveLayer");
        this.mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.unstableAreaMap = new ConcurrentHashMap<>();
        this.needReRender = true;
        this.lastEraserRect = new RectF();
    }

    /* JADX INFO: compiled from: CurveSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lxc/engine/core/graphics/engine/systems/CurveSystem$MainCurveFetcher;", "Lxc/engine/core/graphics/engine/systems/TileSystem$CurveFetcher;", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "getCurveEngine", "()Lxc/engine/core/graphics/engine/MiCurveEngine;", "getDesiredCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "inSaveRectF", "Landroid/graphics/RectF;", "pageIndex", "", "doesAreaHasDesiredCurve", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MainCurveFetcher extends TileSystem.CurveFetcher {
        private final MiCurveEngine curveEngine;

        public MainCurveFetcher(MiCurveEngine curveEngine) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
            this.curveEngine = curveEngine;
        }

        public final MiCurveEngine getCurveEngine() {
            return this.curveEngine;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean getDesiredCurves$lambda$0(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() != PenType.MARK;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.CurveFetcher
        public List<DataCurve> getDesiredCurves(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            List<DataCurve> desiredCurvesInArea = this.curveEngine.getPenEngine().mCurveDataManager.getDesiredCurvesInArea(inSaveRectF, new Predicate() { // from class: xc.engine.core.graphics.engine.systems.CurveSystem$MainCurveFetcher$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CurveSystem.MainCurveFetcher.getDesiredCurves$lambda$0((DataCurve) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(desiredCurvesInArea, "getDesiredCurvesInArea(...)");
            return desiredCurvesInArea;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean doesAreaHasDesiredCurve$lambda$1(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() != PenType.MARK;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.CurveFetcher
        public boolean doesAreaHasDesiredCurve(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return this.curveEngine.getPenEngine().mCurveDataManager.doesAreaHasCurve(inSaveRectF, new Predicate() { // from class: xc.engine.core.graphics.engine.systems.CurveSystem$MainCurveFetcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CurveSystem.MainCurveFetcher.doesAreaHasDesiredCurve$lambda$1((DataCurve) obj);
                }
            });
        }
    }

    /* JADX INFO: compiled from: CurveSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lxc/engine/core/graphics/engine/systems/CurveSystem$MarkCurveFetcher;", "Lxc/engine/core/graphics/engine/systems/TileSystem$CurveFetcher;", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "getCurveEngine", "()Lxc/engine/core/graphics/engine/MiCurveEngine;", "getDesiredCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "inSaveRectF", "Landroid/graphics/RectF;", "pageIndex", "", "doesAreaHasDesiredCurve", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MarkCurveFetcher extends TileSystem.CurveFetcher {
        private final MiCurveEngine curveEngine;

        public MarkCurveFetcher(MiCurveEngine curveEngine) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
            this.curveEngine = curveEngine;
        }

        public final MiCurveEngine getCurveEngine() {
            return this.curveEngine;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean getDesiredCurves$lambda$0(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() == PenType.MARK;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.CurveFetcher
        public List<DataCurve> getDesiredCurves(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            List<DataCurve> desiredCurvesInArea = this.curveEngine.getPenEngine().mCurveDataManager.getDesiredCurvesInArea(inSaveRectF, new Predicate() { // from class: xc.engine.core.graphics.engine.systems.CurveSystem$MarkCurveFetcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CurveSystem.MarkCurveFetcher.getDesiredCurves$lambda$0((DataCurve) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(desiredCurvesInArea, "getDesiredCurvesInArea(...)");
            return desiredCurvesInArea;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean doesAreaHasDesiredCurve$lambda$1(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() == PenType.MARK;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.CurveFetcher
        public boolean doesAreaHasDesiredCurve(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return this.curveEngine.getPenEngine().mCurveDataManager.doesAreaHasCurve(inSaveRectF, new Predicate() { // from class: xc.engine.core.graphics.engine.systems.CurveSystem$MarkCurveFetcher$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CurveSystem.MarkCurveFetcher.doesAreaHasDesiredCurve$lambda$1((DataCurve) obj);
                }
            });
        }
    }

    public final TileSystem getMainCurveLayer() {
        return this.mainCurveLayer;
    }

    public final TileSystem getMarkCurveLayer() {
        return this.markCurveLayer;
    }

    public final boolean getNeedReRender() {
        return this.needReRender;
    }

    public final void setNeedReRender(boolean z) {
        this.needReRender = z;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        CoroutineScopeKt.cancel$default(this.mScope, null, 1, null);
    }

    public final int addUnstableArea(RectF area) {
        Intrinsics.checkNotNullParameter(area, "area");
        int i = this.unstableAreaId + 1;
        this.unstableAreaId = i;
        this.unstableAreaMap.put(Integer.valueOf(i), area);
        Log.e(TAG, "xl-- addUnstableArea: " + area + " count: " + this.unstableAreaMap.size());
        return this.unstableAreaId;
    }

    public final void removeUnstableArea(int id) {
        CurveSystem curveSystem;
        RectF rectFRemove;
        if (!this.unstableAreaMap.containsKey(Integer.valueOf(id)) || (rectFRemove = this.unstableAreaMap.remove(Integer.valueOf(id))) == null) {
            curveSystem = this;
        } else {
            curveSystem = this;
            updateArea$default(curveSystem, rectFRemove, false, false, false, 14, null);
        }
        Log.e(TAG, "xl-- removeUnstableArea count: " + curveSystem.unstableAreaMap.size());
        curveSystem.lastEraserRect = new RectF();
        curveSystem.lastEraserArea = 0.0f;
    }

    public final boolean isAreaUnstable(RectF area) {
        Intrinsics.checkNotNullParameter(area, "area");
        ConcurrentHashMap<Integer, RectF> concurrentHashMap = this.unstableAreaMap;
        if (concurrentHashMap.isEmpty()) {
            return false;
        }
        Iterator<Map.Entry<Integer, RectF>> it = concurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            if (RectF.intersects(area, it.next().getValue())) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ void updateArea$default(CurveSystem curveSystem, RectF rectF, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        if ((i & 8) != 0) {
            z3 = true;
        }
        curveSystem.updateArea(rectF, z, z2, z3);
    }

    public final void updateArea(RectF areaWS, boolean bUpdateFullScreen, boolean bUpdateTiles, boolean isAsyncTask) {
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        TileSystem.updateArea$default(this.mainCurveLayer, areaWS, bUpdateFullScreen, bUpdateTiles, isAsyncTask, false, 16, null);
        TileSystem.updateArea$default(this.markCurveLayer, areaWS, bUpdateFullScreen, bUpdateTiles, isAsyncTask, false, 16, null);
        DataManager.updateInfiniteBoundary$default(getCurveEngine().getDataManager(), areaWS, false, 2, null);
    }

    public final void updateAreaLocallyForEraser(RectF areaWS, float erasingMaxPenSize) {
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        if (!this.lastEraserRect.isEmpty() && this.lastEraserArea > 0.0f && RectF.intersects(this.lastEraserRect, areaWS)) {
            RectF rectF = new RectF(Math.max(this.lastEraserRect.left, areaWS.left), Math.max(this.lastEraserRect.top, areaWS.top), Math.min(this.lastEraserRect.right, areaWS.right), Math.min(this.lastEraserRect.bottom, areaWS.bottom));
            if ((rectF.width() * rectF.height()) / this.lastEraserArea > 0.9f) {
                return;
            }
        }
        float f = -(Math.max(36.0f, erasingMaxPenSize) * getCurveEngine().getDrawRatio());
        areaWS.inset(f, f);
        if (!this.lastEraserRect.isEmpty()) {
            areaWS.union(this.lastEraserRect.centerX(), this.lastEraserRect.centerY());
        }
        this.mainCurveLayer.updateAreaLocallyForEraser(areaWS);
        this.markCurveLayer.updateAreaLocallyForEraser(areaWS);
        RectF rectF2 = new RectF(areaWS);
        this.lastEraserRect = rectF2;
        this.lastEraserArea = rectF2.width() * this.lastEraserRect.height();
    }

    public final void updateNewCurve(RectF saveRect, DataCurve dataCurve) {
        Intrinsics.checkNotNullParameter(saveRect, "saveRect");
        Intrinsics.checkNotNullParameter(dataCurve, "dataCurve");
        if (dataCurve.mCurveProp.getPenType() == PenType.MARK) {
            this.markCurveLayer.updateNewCurve(saveRect, dataCurve);
        } else {
            this.mainCurveLayer.updateNewCurve(saveRect, dataCurve);
        }
        getCurveEngine().getDataManager().updateInfiniteBoundary(getCurveEngine().getRectWSFromSaveRect(saveRect), true);
    }

    public final void initUpdate() {
        this.mainCurveLayer.setDataReady();
        TileSystem.update$default(this.mainCurveLayer, false, 1, null);
        this.markCurveLayer.setDataReady();
        TileSystem.update$default(this.markCurveLayer, false, 1, null);
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.CurveSystem$update$1, reason: invalid class name */
    /* JADX INFO: compiled from: CurveSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.CurveSystem$update$1", f = "CurveSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $skipExistedTile;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(boolean z, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$skipExistedTile = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CurveSystem.this.new AnonymousClass1(this.$skipExistedTile, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CurveSystem.this.getMainCurveLayer().update(this.$skipExistedTile);
            CurveSystem.this.getMarkCurveLayer().update(this.$skipExistedTile);
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void update$default(CurveSystem curveSystem, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        curveSystem.update(z);
    }

    public final void update(boolean skipExistedTile) {
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new AnonymousClass1(skipExistedTile, null), 3, null);
    }

    public final void onEntLoaded(int pageIndex) {
        this.mainCurveLayer.onEntLoaded(pageIndex);
        this.markCurveLayer.onEntLoaded(pageIndex);
    }

    public final void clearAllTiles() {
        this.mainCurveLayer.clearAllTiles();
        this.markCurveLayer.clearAllTiles();
    }

    public final void renderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        this.mainCurveLayer.renderLayer(screenRect);
        this.markCurveLayer.renderLayer(screenRect);
    }

    public final boolean shouldShowFullscreenTile() {
        return this.mainCurveLayer.shouldShowFullscreenTile();
    }
}
