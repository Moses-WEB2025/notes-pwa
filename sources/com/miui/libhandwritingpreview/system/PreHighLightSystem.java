package com.miui.libhandwritingpreview.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.data.pagedata.ICurve;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: PreHighLightSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 <2\u00020\u0001:\u0004<=>?B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010!\u001a\u00020\"H\u0014J,\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\t2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&2\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020\tJ<\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020\u00172\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00110&2\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020\t2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.J\u0006\u00100\u001a\u00020\"J\b\u00103\u001a\u00020\"H\u0002J(\u00104\u001a\u00020\"2\u0016\u00105\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u0018j\b\u0012\u0004\u0012\u00020\u000f`\u00192\u0006\u00106\u001a\u00020\u0011H\u0002J\b\u00107\u001a\u00020\fH\u0002J\b\u00108\u001a\u00020\"H\u0002J\u0006\u00109\u001a\u00020\"J\u0006\u0010:\u001a\u00020\"J\b\u0010;\u001a\u00020\"H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000RJ\u0010\u0015\u001a>\u0012\u0004\u0012\u00020\u0017\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00110\u0018j\b\u0012\u0004\u0012\u00020\u0011`\u00190\u0016j\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00110\u0018j\b\u0012\u0004\u0012\u00020\u0011`\u0019`\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreHighLightSystem;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineLayer;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "highLightAlpha", "", "highLightColor", "bIsActivated", "", "bRectSet", "highlightPath", "Landroid/graphics/Path;", "bitmapRectF", "Landroid/graphics/RectF;", "textureId", "highLightParam", "Lcom/miui/libhandwritingpreview/system/PreHighLightSystem$HighLightParam;", "spanToHighlightSaveRectListMap", "Ljava/util/HashMap;", "Lxc/richtext/data/SpannedData;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "Lkotlin/collections/HashMap;", "spanHighlightColor", "spanHighlightAlpha", "highlightScheduled", "highlightHandler", "Landroid/os/Handler;", "isHighlighting", "onDestroy", "", "setCurveHighLightRect", "pageIndex", "dataList", "", "Landroid/graphics/Rect;", "color", "alpha", "setSpanHighlightRect", "span", "rectList", "staticLayoutWidth", "", "scale", "startHighlight", "highlightRunnable", "Ljava/lang/Runnable;", "executeHighlightImmediately", "processCurveData", "highlightPaths", "maxSaveRect", "shouldAbortHighlight", "processHighLightParamOffsets", "drawHighLight", "shutdownHighlight", "release", "Companion", "SetupHighlightTask", "RectWithPageInfo", "HighLightParam", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreHighLightSystem extends PreBaseEngineLayer {
    private static final String TAG = "PreHighLightSystem";
    private boolean bIsActivated;
    private boolean bRectSet;
    private RectF bitmapRectF;
    private int highLightAlpha;
    private int highLightColor;
    private HighLightParam highLightParam;
    private final Handler highlightHandler;
    private final Path highlightPath;
    private final Runnable highlightRunnable;
    private boolean highlightScheduled;
    private boolean isHighlighting;
    private int spanHighlightAlpha;
    private int spanHighlightColor;
    private final HashMap<SpannedData, ArrayList<RectF>> spanToHighlightSaveRectListMap;
    private int textureId;
    private final TileRenderer tileRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreHighLightSystem(PreMiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.tileRenderer = getCurveEngine().getTileRenderer();
        this.highlightPath = new Path();
        this.bitmapRectF = new RectF();
        this.spanToHighlightSaveRectListMap = new HashMap<>();
        this.highlightHandler = new Handler(Looper.getMainLooper());
        this.highlightRunnable = new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PreHighLightSystem.highlightRunnable$lambda$2(this.f$0);
            }
        };
    }

    @Override // com.miui.libhandwritingpreview.system.PreBaseEngineLayer, com.miui.libhandwritingpreview.system.BaseStruct
    protected void onDestroy() {
        release();
    }

    /* JADX INFO: compiled from: PreHighLightSystem.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreHighLightSystem$SetupHighlightTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inHighLightSystem", "Lcom/miui/libhandwritingpreview/system/PreHighLightSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/system/PreHighLightSystem;)V", "highLightSystem", "curveEngine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "computePathBounds", "", "pathBounds", "Landroid/graphics/RectF;", "unionSpanHighlightAreas", "computeClippedRect", "drawHighlightOnCanvas", "canvas", "Landroid/graphics/Canvas;", "onRenderCompleted", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SetupHighlightTask extends BaseAsyncGLTask {
        private final PreMiCurveEngine curveEngine;
        private final PreHighLightSystem highLightSystem;

        public SetupHighlightTask(PreHighLightSystem inHighLightSystem) {
            Intrinsics.checkNotNullParameter(inHighLightSystem, "inHighLightSystem");
            this.highLightSystem = inHighLightSystem;
            this.curveEngine = inHighLightSystem.getCurveEngine();
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            RectF rectF = new RectF();
            computePathBounds(rectF);
            unionSpanHighlightAreas(rectF);
            RectF rectFComputeClippedRect = computeClippedRect(rectF);
            Rect rect = new Rect();
            rectFComputeClippedRect.roundOut(rect);
            Log.d(PreHighLightSystem.TAG, "xl-- [preHighlight] bitmap " + rect.width() + " x " + rect.height());
            if (rect.isEmpty()) {
                return false;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
            this.highLightSystem.bitmapRectF = new RectF(rect);
            drawHighlightOnCanvas(new Canvas(bitmapCreateBitmap));
            if (this.highLightSystem.textureId <= 0) {
                this.highLightSystem.textureId = GLUtils.INSTANCE.createTexture(bitmapCreateBitmap);
            }
            createSyncHandle();
            return true;
        }

        private final void computePathBounds(RectF pathBounds) {
            if (this.highLightSystem.highlightPath.isEmpty()) {
                return;
            }
            this.highLightSystem.highlightPath.computeBounds(pathBounds, true);
        }

        private final void unionSpanHighlightAreas(final RectF pathBounds) {
            HashMap map = this.highLightSystem.spanToHighlightSaveRectListMap;
            final Function2 function2 = new Function2() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return PreHighLightSystem.SetupHighlightTask.unionSpanHighlightAreas$lambda$1(this.f$0, pathBounds, (SpannedData) obj, (ArrayList) obj2);
                }
            };
            map.forEach(new BiConsumer() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    function2.invoke(obj, obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit unionSpanHighlightAreas$lambda$1(SetupHighlightTask setupHighlightTask, RectF rectF, SpannedData span, ArrayList rectList) {
            Intrinsics.checkNotNullParameter(span, "span");
            Intrinsics.checkNotNullParameter(rectList, "rectList");
            Iterator it = rectList.iterator();
            while (it.hasNext()) {
                RectF rectF2 = (RectF) it.next();
                if (span.getDegrees() == 0.0f) {
                    rectF.union(setupHighlightTask.curveEngine.getRectWSFromSaveRect(rectF2));
                } else {
                    rectF.union(setupHighlightTask.curveEngine.getRectWSFromSaveRect(GLUtils.INSTANCE.getRotatedBoundingBox(rectF2, span.getDegrees(), span.getRectF().centerX(), span.getRectF().centerY())));
                }
            }
            return Unit.INSTANCE;
        }

        private final RectF computeClippedRect(RectF pathBounds) {
            RectF rectF = new RectF(this.curveEngine.getScreenAreaWS());
            rectF.intersect(pathBounds);
            rectF.inset(-4.0f, -4.0f);
            return rectF;
        }

        private final void drawHighlightOnCanvas(final Canvas canvas) {
            final Paint paint = new Paint();
            paint.setColor(this.highLightSystem.highLightColor);
            HighLightParam highLightParam = this.highLightSystem.highLightParam;
            boolean zIsBgDark = highLightParam != null ? this.curveEngine.getPageSystem().isBgDark(highLightParam.getDataList().get(0).getPageIndex()) : false;
            int i = this.highLightSystem.highLightAlpha;
            if (zIsBgDark) {
                i = (int) (((double) i) * 3.5d);
            }
            paint.setAlpha(i);
            canvas.translate(-this.highLightSystem.bitmapRectF.left, -this.highLightSystem.bitmapRectF.top);
            if (!this.highLightSystem.highlightPath.isEmpty()) {
                canvas.drawPath(this.highLightSystem.highlightPath, paint);
            }
            paint.setColor(this.highLightSystem.spanHighlightColor);
            int i2 = this.highLightSystem.spanHighlightAlpha;
            if (zIsBgDark) {
                i2 = (int) (((double) i2) * 3.5d);
            }
            paint.setAlpha(i2);
            HashMap map = this.highLightSystem.spanToHighlightSaveRectListMap;
            final Function2 function2 = new Function2() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return PreHighLightSystem.SetupHighlightTask.drawHighlightOnCanvas$lambda$6(canvas, this, paint, (SpannedData) obj, (ArrayList) obj2);
                }
            };
            map.forEach(new BiConsumer() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    function2.invoke(obj, obj2);
                }
            });
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            this.highLightSystem.bIsActivated = true;
            Log.d(PreHighLightSystem.TAG, "xl-- [preHighlight] activate");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit drawHighlightOnCanvas$lambda$6(Canvas canvas, SetupHighlightTask setupHighlightTask, Paint paint, SpannedData span, ArrayList rectList) {
            Intrinsics.checkNotNullParameter(span, "span");
            Intrinsics.checkNotNullParameter(rectList, "rectList");
            int iSave = canvas.save();
            try {
                PreMiCurveEngine preMiCurveEngine = setupHighlightTask.curveEngine;
                RectF rectF = span.getRectF();
                Intrinsics.checkNotNullExpressionValue(rectF, "getRectF(...)");
                RectF rectWSFromSaveRect = preMiCurveEngine.getRectWSFromSaveRect(rectF);
                canvas.translate(rectWSFromSaveRect.centerX(), rectWSFromSaveRect.centerY());
                canvas.rotate(span.getDegrees());
                canvas.translate(-rectWSFromSaveRect.centerX(), -rectWSFromSaveRect.centerY());
                Iterator it = rectList.iterator();
                while (it.hasNext()) {
                    canvas.drawRect(setupHighlightTask.curveEngine.getRectWSFromSaveRect((RectF) it.next()), paint);
                }
                canvas.restoreToCount(iSave);
                return Unit.INSTANCE;
            } catch (Throwable th) {
                canvas.restoreToCount(iSave);
                throw th;
            }
        }
    }

    /* JADX INFO: compiled from: PreHighLightSystem.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreHighLightSystem$RectWithPageInfo;", "", "pageIndex", "", "rect", "Landroid/graphics/Rect;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILandroid/graphics/Rect;)V", "getPageIndex", "()I", "getRect", "()Landroid/graphics/Rect;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class RectWithPageInfo {
        private final int pageIndex;
        private final Rect rect;

        public static /* synthetic */ RectWithPageInfo copy$default(RectWithPageInfo rectWithPageInfo, int i, Rect rect, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = rectWithPageInfo.pageIndex;
            }
            if ((i2 & 2) != 0) {
                rect = rectWithPageInfo.rect;
            }
            return rectWithPageInfo.copy(i, rect);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getPageIndex() {
            return this.pageIndex;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Rect getRect() {
            return this.rect;
        }

        public final RectWithPageInfo copy(int pageIndex, Rect rect) {
            Intrinsics.checkNotNullParameter(rect, "rect");
            return new RectWithPageInfo(pageIndex, rect);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RectWithPageInfo)) {
                return false;
            }
            RectWithPageInfo rectWithPageInfo = (RectWithPageInfo) other;
            return this.pageIndex == rectWithPageInfo.pageIndex && Intrinsics.areEqual(this.rect, rectWithPageInfo.rect);
        }

        public int hashCode() {
            return (Integer.hashCode(this.pageIndex) * 31) + this.rect.hashCode();
        }

        public String toString() {
            return "RectWithPageInfo(pageIndex=" + this.pageIndex + ", rect=" + this.rect + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public RectWithPageInfo(int i, Rect rect) {
            Intrinsics.checkNotNullParameter(rect, "rect");
            this.pageIndex = i;
            this.rect = rect;
        }

        public final int getPageIndex() {
            return this.pageIndex;
        }

        public final Rect getRect() {
            return this.rect;
        }
    }

    /* JADX INFO: compiled from: PreHighLightSystem.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J-\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreHighLightSystem$HighLightParam;", "", "dataList", "", "Lcom/miui/libhandwritingpreview/system/PreHighLightSystem$RectWithPageInfo;", "color", "", "alpha", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;II)V", "getDataList", "()Ljava/util/List;", "getColor", "()I", "getAlpha", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class HighLightParam {
        private final int alpha;
        private final int color;
        private final List<RectWithPageInfo> dataList;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ HighLightParam copy$default(HighLightParam highLightParam, List list, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                list = highLightParam.dataList;
            }
            if ((i3 & 2) != 0) {
                i = highLightParam.color;
            }
            if ((i3 & 4) != 0) {
                i2 = highLightParam.alpha;
            }
            return highLightParam.copy(list, i, i2);
        }

        public final List<RectWithPageInfo> component1() {
            return this.dataList;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getColor() {
            return this.color;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getAlpha() {
            return this.alpha;
        }

        public final HighLightParam copy(List<RectWithPageInfo> dataList, int color, int alpha) {
            Intrinsics.checkNotNullParameter(dataList, "dataList");
            return new HighLightParam(dataList, color, alpha);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof HighLightParam)) {
                return false;
            }
            HighLightParam highLightParam = (HighLightParam) other;
            return Intrinsics.areEqual(this.dataList, highLightParam.dataList) && this.color == highLightParam.color && this.alpha == highLightParam.alpha;
        }

        public int hashCode() {
            return (((this.dataList.hashCode() * 31) + Integer.hashCode(this.color)) * 31) + Integer.hashCode(this.alpha);
        }

        public String toString() {
            return "HighLightParam(dataList=" + this.dataList + ", color=" + this.color + ", alpha=" + this.alpha + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public HighLightParam(List<RectWithPageInfo> dataList, int i, int i2) {
            Intrinsics.checkNotNullParameter(dataList, "dataList");
            this.dataList = dataList;
            this.color = i;
            this.alpha = i2;
        }

        public final int getAlpha() {
            return this.alpha;
        }

        public final int getColor() {
            return this.color;
        }

        public final List<RectWithPageInfo> getDataList() {
            return this.dataList;
        }
    }

    public final void setCurveHighLightRect(int pageIndex, List<Rect> dataList, int color, int alpha) {
        ArrayList arrayList;
        List<RectWithPageInfo> dataList2;
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Log.d(TAG, "xl-- [preHighlight] on curve pageIndex: " + pageIndex + ", dataList " + dataList + ", color: " + color + ", alpha: " + alpha);
        HighLightParam highLightParam = this.highLightParam;
        if (highLightParam == null || (dataList2 = highLightParam.getDataList()) == null || (arrayList = CollectionsKt.toMutableList((Collection) dataList2)) == null) {
            arrayList = new ArrayList();
        }
        Iterator<T> it = dataList.iterator();
        while (it.hasNext()) {
            arrayList.add(new RectWithPageInfo(pageIndex, new Rect((Rect) it.next())));
        }
        this.highLightColor = color;
        this.highLightAlpha = alpha;
        this.highLightParam = new HighLightParam(arrayList, color, alpha);
    }

    public final void setSpanHighlightRect(SpannedData span, List<? extends RectF> rectList, int color, int alpha, float staticLayoutWidth, float scale) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(rectList, "rectList");
        Log.d(TAG, "xl-- [preHighlight] on span[" + span + "] " + span.getText() + " rectCount: " + rectList.size() + " color: " + color + " alpha: " + alpha + ", scale " + scale);
        this.spanHighlightColor = color;
        this.spanHighlightAlpha = alpha;
        RectF rectF = span.getRectF();
        for (RectF rectF2 : rectList) {
            float fHeight = rectF.height() * (staticLayoutWidth / rectF.width());
            float fWidth = rectF.width() / staticLayoutWidth;
            float fHeight2 = rectF.height() / fHeight;
            RectF rectF3 = new RectF(rectF2.left * fWidth, rectF2.top * fHeight2, rectF2.right * fWidth, rectF2.bottom * fHeight2);
            RectF rectF4 = new RectF(rectF.left + rectF3.left, rectF.top + rectF3.top, rectF.left + rectF3.right, rectF.top + rectF3.bottom);
            rectF4.left = Math.max(rectF4.left, rectF.left);
            rectF4.top = Math.max(rectF4.top, rectF.top);
            rectF4.right = Math.min(rectF4.right, rectF.right);
            rectF4.bottom = Math.min(rectF4.bottom, rectF.bottom);
            if (!this.spanToHighlightSaveRectListMap.containsKey(span)) {
                this.spanToHighlightSaveRectListMap.put(span, new ArrayList<>());
            }
            ArrayList<RectF> arrayList = this.spanToHighlightSaveRectListMap.get(span);
            if (arrayList != null) {
                arrayList.add(rectF4);
            }
        }
    }

    public final void startHighlight() {
        Log.d(TAG, "xl-- [preHighlight] start");
        this.highlightHandler.removeCallbacks(this.highlightRunnable);
        this.highlightScheduled = true;
        this.highlightHandler.postDelayed(this.highlightRunnable, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void highlightRunnable$lambda$2(PreHighLightSystem preHighLightSystem) {
        preHighLightSystem.highlightScheduled = false;
        preHighLightSystem.executeHighlightImmediately();
    }

    private final void executeHighlightImmediately() {
        if (this.isHighlighting) {
            return;
        }
        this.isHighlighting = true;
        ThreadsKt.thread$default(false, true, null, "HighlightWorker", 0, new Function0() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PreHighLightSystem.executeHighlightImmediately$lambda$7(this.f$0);
            }
        }, 21, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit executeHighlightImmediately$lambda$7(PreHighLightSystem preHighLightSystem) {
        try {
            if (preHighLightSystem.shouldAbortHighlight()) {
                Log.d(TAG, "xl-- [preHighlight] abort in bg");
                preHighLightSystem.release();
                return Unit.INSTANCE;
            }
            ArrayList<Path> arrayList = new ArrayList<>();
            final RectF rectF = new RectF();
            preHighLightSystem.processHighLightParamOffsets();
            preHighLightSystem.processCurveData(arrayList, rectF);
            HashMap<SpannedData, ArrayList<RectF>> map = preHighLightSystem.spanToHighlightSaveRectListMap;
            final Function2 function2 = new Function2() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return PreHighLightSystem.executeHighlightImmediately$lambda$7$lambda$4(rectF, (SpannedData) obj, (ArrayList) obj2);
                }
            };
            map.forEach(new BiConsumer() { // from class: com.miui.libhandwritingpreview.system.PreHighLightSystem$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    function2.invoke(obj, obj2);
                }
            });
            Log.d(TAG, "xl-- [preHighlight] curve path count: " + arrayList.size() + ", span count: " + preHighLightSystem.spanToHighlightSaveRectListMap.size());
            if (arrayList.isEmpty() && preHighLightSystem.spanToHighlightSaveRectListMap.isEmpty()) {
                return Unit.INSTANCE;
            }
            preHighLightSystem.highlightPath.reset();
            Iterator<T> it = arrayList.iterator();
            while (it.hasNext()) {
                preHighLightSystem.highlightPath.addPath((Path) it.next());
            }
            RectF rectWSFromSaveRect = preHighLightSystem.getCurveEngine().getRectWSFromSaveRect(rectF);
            preHighLightSystem.getCurveEngine().getCamera().trySetCenterTo(rectWSFromSaveRect.centerX(), rectWSFromSaveRect.centerY());
            preHighLightSystem.getCurveEngine().getTaskManager().enqueueTask(new SetupHighlightTask(preHighLightSystem));
            return Unit.INSTANCE;
        } catch (Exception e) {
            Log.w(TAG, "highlight background error : ", e);
            preHighLightSystem.isHighlighting = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit executeHighlightImmediately$lambda$7$lambda$4(RectF rectF, SpannedData span, ArrayList rectList) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(rectList, "rectList");
        Iterator it = rectList.iterator();
        while (it.hasNext()) {
            RectF rectF2 = (RectF) it.next();
            if (rectF.isEmpty()) {
                rectF.union(rectF2);
            }
        }
        return Unit.INSTANCE;
    }

    private final void processCurveData(ArrayList<Path> highlightPaths, RectF maxSaveRect) {
        HighLightParam highLightParam = this.highLightParam;
        if (highLightParam == null) {
            return;
        }
        Intrinsics.checkNotNull(highLightParam);
        int size = highLightParam.getDataList().size();
        for (int i = 0; i < size; i++) {
            HighLightParam highLightParam2 = this.highLightParam;
            Intrinsics.checkNotNull(highLightParam2);
            RectWithPageInfo rectWithPageInfo = highLightParam2.getDataList().get(i);
            List<ICurve> curveData = getCurveEngine().getPenEngine().getDataAccess().getCurveData(new Rect(rectWithPageInfo.getRect()));
            Intrinsics.checkNotNull(curveData);
            if (!curveData.isEmpty()) {
                if (i == 0) {
                    int curveColor = curveData.get(0).getCurveColor();
                    HighLightParam highLightParam3 = this.highLightParam;
                    Intrinsics.checkNotNull(highLightParam3);
                    if (curveColor == highLightParam3.getColor()) {
                        int alpha = curveData.get(0).getAlpha();
                        HighLightParam highLightParam4 = this.highLightParam;
                        Intrinsics.checkNotNull(highLightParam4);
                        if (alpha == highLightParam4.getAlpha()) {
                            HighLightParam highLightParam5 = this.highLightParam;
                            Intrinsics.checkNotNull(highLightParam5);
                            this.highLightAlpha = highLightParam5.getAlpha() / 2;
                        }
                    }
                }
                Path pathCountDataPath = getCurveEngine().getPenEngine().getAlgorithmInterface().countDataPath(curveData);
                Intrinsics.checkNotNullExpressionValue(pathCountDataPath, "countDataPath(...)");
                highlightPaths.add(pathCountDataPath);
                RectF rectF = new RectF(rectWithPageInfo.getRect());
                if (maxSaveRect.isEmpty()) {
                    maxSaveRect.union(rectF);
                }
            }
        }
    }

    private final boolean shouldAbortHighlight() {
        HighLightParam highLightParam = this.highLightParam;
        if (highLightParam != null) {
            Intrinsics.checkNotNull(highLightParam);
            if (!highLightParam.getDataList().isEmpty()) {
                return false;
            }
        }
        return this.spanToHighlightSaveRectListMap.isEmpty();
    }

    private final void processHighLightParamOffsets() {
        HighLightParam highLightParam = this.highLightParam;
        if (highLightParam == null) {
            return;
        }
        Intrinsics.checkNotNull(highLightParam);
        for (RectWithPageInfo rectWithPageInfo : highLightParam.getDataList()) {
            int pageIndex = rectWithPageInfo.getPageIndex();
            rectWithPageInfo.getRect().offset((int) InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex), (int) InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex));
        }
    }

    public final void drawHighLight() {
        if (this.bIsActivated && this.textureId > 0) {
            TileRenderer.drawRect$default(this.tileRenderer, this.bitmapRectF, getCurveEngine().getScreenAreaWS(), this.textureId, true, false, 0.0f, 48, null);
        }
    }

    public final void shutdownHighlight() {
        if (this.bIsActivated) {
            this.bRectSet = false;
            this.bIsActivated = false;
            GLUtils.INSTANCE.deleteTexture(this.textureId);
            this.textureId = 0;
            this.highlightHandler.removeCallbacks(this.highlightRunnable);
            this.highlightScheduled = false;
            Log.d(TAG, "xl-- [preHighlight] shutdown");
            PreMiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
            release();
        }
    }

    private final void release() {
        this.highLightParam = null;
        this.spanToHighlightSaveRectListMap.clear();
        this.highlightPath.reset();
    }
}
