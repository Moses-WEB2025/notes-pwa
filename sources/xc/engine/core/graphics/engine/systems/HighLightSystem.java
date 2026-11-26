package xc.engine.core.graphics.engine.systems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.data.pagedata.ICurve;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: HighLightSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000e\u0018\u0000 82\u00020\u0001:\u000389:B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u001f\u001a\u00020 H\u0014J4\u0010!\u001a\u00020 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\u000e\u0010'\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010#J<\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020\u00162\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00100#2\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-J\b\u0010/\u001a\u00020\fH\u0002J\u0006\u00100\u001a\u00020 J>\u00101\u001a\u00020 2\u0006\u00102\u001a\u00020\t2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020$0#2\u0016\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u0017j\b\u0012\u0004\u0012\u00020\u000e`\u00182\u0006\u00104\u001a\u00020\u0010H\u0002J\u0006\u00105\u001a\u00020 J\u0006\u00106\u001a\u00020 J\b\u00107\u001a\u00020 H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0016\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00100\u0017j\b\u0012\u0004\u0012\u00020\u0010`\u00180\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lxc/engine/core/graphics/engine/systems/HighLightSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "highLightAlpha", "", "highLightColor", "bIsActivated", "", "highlightPath", "Landroid/graphics/Path;", "bitmapRectF", "Landroid/graphics/RectF;", "textureId", "highLightParam", "Lxc/engine/core/graphics/engine/systems/HighLightSystem$HighLightParam;", "spanToHighlightSaveRectListMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lxc/richtext/data/SpannedData;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "spanHighlightColor", "spanHighlightAlpha", "highLightParams", "", "highlightHandler", "Landroid/os/Handler;", "onDestroy", "", "setCurveHighLightRect", "dataList", "", "Landroid/graphics/Rect;", "color", "alpha", "rectKeys", "", "setSpanHighlightRect", "span", "rectList", "staticLayoutWidth", "", "scale", "preHighlight", "startHighlight", "handleCurveHighlightForPage", "pageIndex", "highlightPaths", "maxSaveRect", "drawHighLight", "shutdownHighlight", "release", "Companion", "SetupHighlightTask", "HighLightParam", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HighLightSystem extends BaseEngineStruct {
    private static final String TAG = "HighLightSystem";
    private boolean bIsActivated;
    private RectF bitmapRectF;
    private int highLightAlpha;
    private int highLightColor;
    private HighLightParam highLightParam;
    private final List<HighLightParam> highLightParams;
    private final Handler highlightHandler;
    private final Path highlightPath;
    private int spanHighlightAlpha;
    private int spanHighlightColor;
    private final ConcurrentHashMap<SpannedData, ArrayList<RectF>> spanToHighlightSaveRectListMap;
    private int textureId;
    private final TileRenderer tileRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HighLightSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.tileRenderer = getCurveEngine().getTileRenderer();
        this.highlightPath = new Path();
        this.bitmapRectF = new RectF();
        this.spanToHighlightSaveRectListMap = new ConcurrentHashMap<>();
        this.highLightParams = new ArrayList();
        this.highlightHandler = new Handler(Looper.getMainLooper());
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        release();
    }

    /* JADX INFO: compiled from: HighLightSystem.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lxc/engine/core/graphics/engine/systems/HighLightSystem$SetupHighlightTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inHighLightSystem", "Lxc/engine/core/graphics/engine/systems/HighLightSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/HighLightSystem;)V", "highLightSystem", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SetupHighlightTask extends BaseAsyncGLTask {
        private final MiCurveEngine curveEngine;
        private final HighLightSystem highLightSystem;

        public SetupHighlightTask(HighLightSystem inHighLightSystem) {
            Intrinsics.checkNotNullParameter(inHighLightSystem, "inHighLightSystem");
            this.highLightSystem = inHighLightSystem;
            this.curveEngine = inHighLightSystem.getCurveEngine();
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            final RectF rectF = new RectF();
            if (!this.highLightSystem.highlightPath.isEmpty()) {
                this.highLightSystem.highlightPath.computeBounds(rectF, true);
            }
            ConcurrentHashMap concurrentHashMap = this.highLightSystem.spanToHighlightSaveRectListMap;
            final Function2 function2 = new Function2() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return HighLightSystem.SetupHighlightTask.execute$lambda$1(rectF, this, (SpannedData) obj, (ArrayList) obj2);
                }
            };
            concurrentHashMap.forEach(new BiConsumer() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    function2.invoke(obj, obj2);
                }
            });
            RectF rectF2 = new RectF(this.curveEngine.getScreenAreaWS());
            rectF2.intersect(rectF);
            rectF2.inset(-4.0f, -4.0f);
            Rect rect = new Rect();
            rectF2.roundOut(rect);
            Log.d(HighLightSystem.TAG, "xl-- [highlight] bitmap " + rect.width() + " x " + rect.height());
            if (rect.isEmpty()) {
                return false;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
            this.highLightSystem.bitmapRectF = new RectF(rect);
            final Canvas canvas = new Canvas(bitmapCreateBitmap);
            final Paint paint = new Paint();
            paint.setColor(this.highLightSystem.highLightColor);
            HighLightParam highLightParam = this.highLightSystem.highLightParam;
            boolean zIsBgDark = highLightParam != null ? this.curveEngine.getPageSystem().isBgDark(highLightParam.getPageIndex()) : false;
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
            paint.setAlpha(zIsBgDark ? (int) (((double) this.highLightSystem.spanHighlightAlpha) * 3.5d) : this.highLightSystem.spanHighlightAlpha);
            ConcurrentHashMap concurrentHashMap2 = this.highLightSystem.spanToHighlightSaveRectListMap;
            final Function2 function3 = new Function2() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return HighLightSystem.SetupHighlightTask.execute$lambda$6(canvas, this, paint, (SpannedData) obj, (ArrayList) obj2);
                }
            };
            concurrentHashMap2.forEach(new BiConsumer() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$SetupHighlightTask$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    function3.invoke(obj, obj2);
                }
            });
            if (this.highLightSystem.textureId <= 0) {
                this.highLightSystem.textureId = GLUtils.INSTANCE.createTexture(bitmapCreateBitmap);
            }
            createSyncHandle();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit execute$lambda$1(RectF rectF, SetupHighlightTask setupHighlightTask, SpannedData span, ArrayList rectList) {
            Intrinsics.checkNotNullParameter(span, "span");
            Intrinsics.checkNotNullParameter(rectList, "rectList");
            Iterator it = rectList.iterator();
            while (it.hasNext()) {
                RectF rectF2 = (RectF) it.next();
                if (span.getDegrees() == 0.0f) {
                    rectF.union(setupHighlightTask.curveEngine.getRectWSFromSaveRect(rectF2));
                } else {
                    rectF.union(setupHighlightTask.curveEngine.getRectWSFromSaveRect(MiMathUtils.INSTANCE.getRotatedBoundingBox(rectF2, span.getDegrees(), span.getRectF().centerX(), span.getRectF().centerY())));
                }
            }
            return Unit.INSTANCE;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            this.highLightSystem.bIsActivated = true;
            Log.d(HighLightSystem.TAG, "xl-- [highlight] activate");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit execute$lambda$6(Canvas canvas, SetupHighlightTask setupHighlightTask, Paint paint, SpannedData span, ArrayList rectList) {
            Intrinsics.checkNotNullParameter(span, "span");
            Intrinsics.checkNotNullParameter(rectList, "rectList");
            int iSave = canvas.save();
            try {
                MiCurveEngine miCurveEngine = setupHighlightTask.curveEngine;
                RectF rectF = span.getRectF();
                Intrinsics.checkNotNullExpressionValue(rectF, "getRectF(...)");
                RectF rectWSFromSaveRect = miCurveEngine.getRectWSFromSaveRect(rectF);
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

    /* JADX INFO: compiled from: HighLightSystem.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J7\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\f¨\u0006\u001c"}, d2 = {"Lxc/engine/core/graphics/engine/systems/HighLightSystem$HighLightParam;", "", "pageIndex", "", "dataList", "", "Landroid/graphics/Rect;", "color", "alpha", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/util/List;II)V", "getPageIndex", "()I", "getDataList", "()Ljava/util/List;", "getColor", "getAlpha", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class HighLightParam {
        private final int alpha;
        private final int color;
        private final List<Rect> dataList;
        private final int pageIndex;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ HighLightParam copy$default(HighLightParam highLightParam, int i, List list, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = highLightParam.pageIndex;
            }
            if ((i4 & 2) != 0) {
                list = highLightParam.dataList;
            }
            if ((i4 & 4) != 0) {
                i2 = highLightParam.color;
            }
            if ((i4 & 8) != 0) {
                i3 = highLightParam.alpha;
            }
            return highLightParam.copy(i, list, i2, i3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getPageIndex() {
            return this.pageIndex;
        }

        public final List<Rect> component2() {
            return this.dataList;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getColor() {
            return this.color;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final int getAlpha() {
            return this.alpha;
        }

        public final HighLightParam copy(int pageIndex, List<Rect> dataList, int color, int alpha) {
            Intrinsics.checkNotNullParameter(dataList, "dataList");
            return new HighLightParam(pageIndex, dataList, color, alpha);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof HighLightParam)) {
                return false;
            }
            HighLightParam highLightParam = (HighLightParam) other;
            return this.pageIndex == highLightParam.pageIndex && Intrinsics.areEqual(this.dataList, highLightParam.dataList) && this.color == highLightParam.color && this.alpha == highLightParam.alpha;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.pageIndex) * 31) + this.dataList.hashCode()) * 31) + Integer.hashCode(this.color)) * 31) + Integer.hashCode(this.alpha);
        }

        public String toString() {
            return "HighLightParam(pageIndex=" + this.pageIndex + ", dataList=" + this.dataList + ", color=" + this.color + ", alpha=" + this.alpha + ")";
        }

        public HighLightParam(int i, List<Rect> dataList, int i2, int i3) {
            Intrinsics.checkNotNullParameter(dataList, "dataList");
            this.pageIndex = i;
            this.dataList = dataList;
            this.color = i2;
            this.alpha = i3;
        }

        public final int getAlpha() {
            return this.alpha;
        }

        public final int getColor() {
            return this.color;
        }

        public final List<Rect> getDataList() {
            return this.dataList;
        }

        public final int getPageIndex() {
            return this.pageIndex;
        }
    }

    public final void setCurveHighLightRect(List<Rect> dataList, int color, int alpha, List<String> rectKeys) {
        int iW;
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Log.d(TAG, "xl-- [highlight] on curve  dataList " + dataList + ", color: " + color + ", alpha: " + alpha);
        this.highLightColor = color;
        this.highLightAlpha = alpha;
        this.highLightParams.clear();
        if (rectKeys == null || rectKeys.size() != dataList.size()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = 0;
        for (Object obj : dataList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Rect rect = (Rect) obj;
            try {
                iW = Integer.parseInt((String) StringsKt.split$default((CharSequence) rectKeys.get(i), new String[]{"-"}, false, 0, 6, (Object) null).get(0));
            } catch (Exception e) {
                iW = Log.w(TAG, "e:" + e.getMessage());
            }
            if (!linkedHashMap.containsKey(Integer.valueOf(iW))) {
                linkedHashMap.put(Integer.valueOf(iW), new ArrayList());
            }
            List list = (List) linkedHashMap.get(Integer.valueOf(iW));
            if (list != null) {
                list.add(new Rect(rect));
            }
            i = i2;
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            this.highLightParams.add(new HighLightParam(((Number) entry.getKey()).intValue(), (List) entry.getValue(), color, alpha));
        }
    }

    public final void setSpanHighlightRect(SpannedData span, List<? extends RectF> rectList, int color, int alpha, float staticLayoutWidth, float scale) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(rectList, "rectList");
        Log.d(TAG, "xl-- [highlight] on span[" + span + "] " + span.getText() + " rectCount: " + rectList.size() + " color: " + color + " alpha: " + alpha + ", scale " + scale);
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

    private final boolean preHighlight() {
        if (!this.highLightParams.isEmpty() || !this.spanToHighlightSaveRectListMap.isEmpty()) {
            return true;
        }
        Log.d(TAG, "xl-- [highlight] abort");
        release();
        return false;
    }

    public final void startHighlight() {
        Log.d(TAG, "xl-- [highlight] start");
        if (preHighlight()) {
            this.highlightHandler.postDelayed(new Runnable() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HighLightSystem.startHighlight$lambda$8(this.f$0);
                }
            }, 50L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startHighlight$lambda$8(HighLightSystem highLightSystem) {
        ArrayList<Path> arrayList = new ArrayList<>();
        final RectF rectF = new RectF();
        for (HighLightParam highLightParam : CollectionsKt.toList(highLightSystem.highLightParams)) {
            highLightSystem.handleCurveHighlightForPage(highLightParam.getPageIndex(), highLightParam.getDataList(), arrayList, rectF);
        }
        ConcurrentHashMap<SpannedData, ArrayList<RectF>> concurrentHashMap = highLightSystem.spanToHighlightSaveRectListMap;
        final Function2 function2 = new Function2() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return HighLightSystem.startHighlight$lambda$8$lambda$5(rectF, (SpannedData) obj, (ArrayList) obj2);
            }
        };
        concurrentHashMap.forEach(new BiConsumer() { // from class: xc.engine.core.graphics.engine.systems.HighLightSystem$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                function2.invoke(obj, obj2);
            }
        });
        if (arrayList.isEmpty() && highLightSystem.spanToHighlightSaveRectListMap.isEmpty()) {
            return;
        }
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            highLightSystem.highlightPath.addPath((Path) it.next());
        }
        RectF rectWSFromSaveRect = highLightSystem.getCurveEngine().getRectWSFromSaveRect(rectF);
        highLightSystem.getCurveEngine().getCamera().trySetCenterTo(rectWSFromSaveRect.centerX(), rectWSFromSaveRect.centerY());
        highLightSystem.getCurveEngine().getTaskManager().enqueueTask(new SetupHighlightTask(highLightSystem));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startHighlight$lambda$8$lambda$5(RectF rectF, SpannedData span, ArrayList rectList) {
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

    private final void handleCurveHighlightForPage(int pageIndex, List<Rect> rectList, ArrayList<Path> highlightPaths, RectF maxSaveRect) {
        int currentPageIndex = getCurveEngine().getPageSystem().getCurrentPageIndex();
        try {
            getCurveEngine().getPageSystem().setCurrentPageIndex(pageIndex);
            float offsetX = InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
            float offsetY = InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
            int size = rectList.size();
            for (int i = 0; i < size; i++) {
                Rect rect = new Rect(rectList.get(i));
                rect.offset((int) offsetX, (int) offsetY);
                List<ICurve> curveData = getCurveEngine().getPenEngine().getDataAccess().getCurveData(rect);
                if (curveData.isEmpty()) {
                    Log.d(TAG, " [highlight] no curve data found for rect ");
                } else {
                    Path pathCountDataPath = getCurveEngine().getPenEngine().getAlgorithmInterface().countDataPath(curveData);
                    Intrinsics.checkNotNullExpressionValue(pathCountDataPath, "countDataPath(...)");
                    highlightPaths.add(pathCountDataPath);
                    maxSaveRect.union(new RectF(rect));
                }
            }
        } finally {
            getCurveEngine().getPageSystem().setCurrentPageIndex(currentPageIndex);
        }
    }

    public final void drawHighLight() {
        if (this.bIsActivated && this.textureId > 0) {
            TileRenderer.drawRect$default(this.tileRenderer, this.bitmapRectF, getCurveEngine().getScreenAreaWS(), this.textureId, true, false, 0.0f, 48, null);
        }
    }

    public final void shutdownHighlight() {
        if (this.bIsActivated) {
            this.bIsActivated = false;
            GLUtils.INSTANCE.deleteTexture(this.textureId);
            this.textureId = 0;
            Log.d(TAG, "xl-- [highlight] shutdown");
            MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
            release();
        }
    }

    private final void release() {
        this.highLightParam = null;
        this.spanToHighlightSaveRectListMap.clear();
        this.highlightPath.reset();
    }
}
