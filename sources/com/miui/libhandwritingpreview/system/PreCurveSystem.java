package com.miui.libhandwritingpreview.system;

import android.graphics.RectF;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.manager.PreDataManager;
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
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: PreCurveSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u000e\u0018\u0000 22\u00020\u0001:\u0003234B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0019\u001a\u00020\u001aH\u0014J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0011J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0010J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u0011J\"\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020\u00142\b\b\u0002\u0010#\u001a\u00020\u0014J\u0016\u0010(\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010)\u001a\u00020'J\u0006\u0010*\u001a\u00020\u001aJ\u0006\u0010+\u001a\u00020\u001aJ\u000e\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u0010J\u0006\u0010.\u001a\u00020\u001aJ\u000e\u0010/\u001a\u00020\u001a2\u0006\u00100\u001a\u00020\u0011J\u0006\u00101\u001a\u00020\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010$\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreCurveSystem;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineStruct;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "mainCurveLayer", "Lcom/miui/libhandwritingpreview/system/PreTileSystem;", "getMainCurveLayer", "()Lcom/miui/libhandwritingpreview/system/PreTileSystem;", "markCurveLayer", "getMarkCurveLayer", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "unstableAreaMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Landroid/graphics/RectF;", "unstableAreaId", "needReRender", "", "getNeedReRender", "()Z", "setNeedReRender", "(Z)V", "onDestroy", "", "addUnstableArea", a.e, "removeUnstableArea", "id", "isAreaUnstable", "updateArea", "areaWS", "bUpdateFullScreen", "bUpdateTiles", "currentDebugId", "lastEraserRect", "lastEraserArea", "", "updateAreaLocallyForEraser", "erasingMaxPenSize", "initUpdate", "update", "onEntLoaded", "pageIndex", "clearAllTiles", "renderLayer", "screenRect", "shouldShowFullscreenTile", "Companion", "MainCurveFetcher", "MarkCurveFetcher", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreCurveSystem extends PreBaseEngineStruct {
    private static final String TAG = "PreCurveSystem";
    private int currentDebugId;
    private float lastEraserArea;
    private RectF lastEraserRect;
    private final CoroutineScope mScope;
    private final PreTileSystem mainCurveLayer;
    private final PreTileSystem markCurveLayer;
    private boolean needReRender;
    private int unstableAreaId;
    private final ConcurrentHashMap<Integer, RectF> unstableAreaMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreCurveSystem(PreMiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        PreTileSystem preTileSystem = new PreTileSystem(engine, new MainCurveFetcher(getCurveEngine()));
        this.mainCurveLayer = preTileSystem;
        PreTileSystem preTileSystem2 = new PreTileSystem(engine, new MarkCurveFetcher(getCurveEngine()));
        this.markCurveLayer = preTileSystem2;
        preTileSystem.setSystemName("MainCurveLayer");
        preTileSystem2.setSystemName("MarkCurveLayer");
        this.mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.unstableAreaMap = new ConcurrentHashMap<>();
        this.needReRender = true;
        this.lastEraserRect = new RectF();
    }

    /* JADX INFO: compiled from: PreCurveSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreCurveSystem$MainCurveFetcher;", "Lcom/miui/libhandwritingpreview/system/PreTileSystem$CurveFetcher;", "curveEngine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "getCurveEngine", "()Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", "getDesiredCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "inSaveRectF", "Landroid/graphics/RectF;", "pageIndex", "", "doesAreaHasDesiredCurve", "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MainCurveFetcher extends PreTileSystem.CurveFetcher {
        private final PreMiCurveEngine curveEngine;

        public MainCurveFetcher(PreMiCurveEngine curveEngine) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
            this.curveEngine = curveEngine;
        }

        public final PreMiCurveEngine getCurveEngine() {
            return this.curveEngine;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean getDesiredCurves$lambda$0(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() != PenType.MARK;
        }

        @Override // com.miui.libhandwritingpreview.system.PreTileSystem.CurveFetcher
        public List<DataCurve> getDesiredCurves(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            List<DataCurve> desiredCurvesInArea = this.curveEngine.getPenEngine().mCurveDataManager.getDesiredCurvesInArea(inSaveRectF, new Predicate() { // from class: com.miui.libhandwritingpreview.system.PreCurveSystem$MainCurveFetcher$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PreCurveSystem.MainCurveFetcher.getDesiredCurves$lambda$0((DataCurve) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(desiredCurvesInArea, "getDesiredCurvesInArea(...)");
            return desiredCurvesInArea;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean doesAreaHasDesiredCurve$lambda$1(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() != PenType.MARK;
        }

        @Override // com.miui.libhandwritingpreview.system.PreTileSystem.CurveFetcher
        public boolean doesAreaHasDesiredCurve(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return this.curveEngine.getPenEngine().mCurveDataManager.doesAreaHasCurve(inSaveRectF, new Predicate() { // from class: com.miui.libhandwritingpreview.system.PreCurveSystem$MainCurveFetcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PreCurveSystem.MainCurveFetcher.doesAreaHasDesiredCurve$lambda$1((DataCurve) obj);
                }
            });
        }
    }

    /* JADX INFO: compiled from: PreCurveSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreCurveSystem$MarkCurveFetcher;", "Lcom/miui/libhandwritingpreview/system/PreTileSystem$CurveFetcher;", "curveEngine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "getCurveEngine", "()Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", "getDesiredCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "inSaveRectF", "Landroid/graphics/RectF;", "pageIndex", "", "doesAreaHasDesiredCurve", "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MarkCurveFetcher extends PreTileSystem.CurveFetcher {
        private final PreMiCurveEngine curveEngine;

        public MarkCurveFetcher(PreMiCurveEngine curveEngine) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
            this.curveEngine = curveEngine;
        }

        public final PreMiCurveEngine getCurveEngine() {
            return this.curveEngine;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean getDesiredCurves$lambda$0(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() == PenType.MARK;
        }

        @Override // com.miui.libhandwritingpreview.system.PreTileSystem.CurveFetcher
        public List<DataCurve> getDesiredCurves(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            List<DataCurve> desiredCurvesInArea = this.curveEngine.getPenEngine().mCurveDataManager.getDesiredCurvesInArea(inSaveRectF, new Predicate() { // from class: com.miui.libhandwritingpreview.system.PreCurveSystem$MarkCurveFetcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PreCurveSystem.MarkCurveFetcher.getDesiredCurves$lambda$0((DataCurve) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(desiredCurvesInArea, "getDesiredCurvesInArea(...)");
            return desiredCurvesInArea;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean doesAreaHasDesiredCurve$lambda$1(DataCurve dataCurve) {
            return dataCurve.mCurveProp.getPenType() == PenType.MARK;
        }

        @Override // com.miui.libhandwritingpreview.system.PreTileSystem.CurveFetcher
        public boolean doesAreaHasDesiredCurve(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return this.curveEngine.getPenEngine().mCurveDataManager.doesAreaHasCurve(inSaveRectF, new Predicate() { // from class: com.miui.libhandwritingpreview.system.PreCurveSystem$MarkCurveFetcher$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PreCurveSystem.MarkCurveFetcher.doesAreaHasDesiredCurve$lambda$1((DataCurve) obj);
                }
            });
        }
    }

    public final PreTileSystem getMainCurveLayer() {
        return this.mainCurveLayer;
    }

    public final PreTileSystem getMarkCurveLayer() {
        return this.markCurveLayer;
    }

    public final boolean getNeedReRender() {
        return this.needReRender;
    }

    public final void setNeedReRender(boolean z) {
        this.needReRender = z;
    }

    @Override // com.miui.libhandwritingpreview.system.BaseStruct
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
        PreCurveSystem preCurveSystem;
        RectF rectFRemove;
        if (!this.unstableAreaMap.containsKey(Integer.valueOf(id)) || (rectFRemove = this.unstableAreaMap.remove(Integer.valueOf(id))) == null) {
            preCurveSystem = this;
        } else {
            preCurveSystem = this;
            updateArea$default(preCurveSystem, rectFRemove, false, false, 6, null);
        }
        Log.e(TAG, "xl-- removeUnstableArea count: " + preCurveSystem.unstableAreaMap.size());
        preCurveSystem.lastEraserRect = new RectF();
        preCurveSystem.lastEraserArea = 0.0f;
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

    public static /* synthetic */ void updateArea$default(PreCurveSystem preCurveSystem, RectF rectF, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        preCurveSystem.updateArea(rectF, z, z2);
    }

    public final void updateArea(RectF areaWS, boolean bUpdateFullScreen, boolean bUpdateTiles) {
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        PreTileSystem.updateArea$default(this.mainCurveLayer, areaWS, bUpdateFullScreen, bUpdateTiles, false, 8, null);
        PreTileSystem.updateArea$default(this.markCurveLayer, areaWS, bUpdateFullScreen, bUpdateTiles, false, 8, null);
        PreDataManager.updateInfiniteBoundary$default(getCurveEngine().getDataManager(), areaWS, false, 2, null);
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

    public final void initUpdate() {
        this.mainCurveLayer.setDataReady();
        this.mainCurveLayer.update();
        this.markCurveLayer.setDataReady();
        this.markCurveLayer.update();
    }

    /* JADX INFO: renamed from: com.miui.libhandwritingpreview.system.PreCurveSystem$update$1, reason: invalid class name */
    /* JADX INFO: compiled from: PreCurveSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.libhandwritingpreview.system.PreCurveSystem$update$1", f = "PreCurveSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PreCurveSystem.this.new AnonymousClass1(continuation);
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
            PreCurveSystem.this.getMainCurveLayer().update();
            PreCurveSystem.this.getMarkCurveLayer().update();
            return Unit.INSTANCE;
        }
    }

    public final void update() {
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new AnonymousClass1(null), 3, null);
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
