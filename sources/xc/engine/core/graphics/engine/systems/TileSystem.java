package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.util.a;
import defpackage.R2;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.apache.commons.lang3.StringUtils;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.core.DataManager;
import xc.engine.core.graphics.engine.core.ScreenTexture;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.engine.structs.CurveTask;
import xc.engine.core.graphics.engine.structs.FullscreenTile;
import xc.engine.core.graphics.engine.structs.Tile;
import xc.engine.core.graphics.engine.structs.TileCoord;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: TileSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\r\b\u0016\u0018\u0000 r2\u00020\u0001:\u0007rstuvwxB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0014J\b\u00100\u001a\u00020-H\u0014J\b\u00101\u001a\u00020-H\u0016J\u0006\u00102\u001a\u00020-J\u000e\u00103\u001a\u00020-2\u0006\u00104\u001a\u00020\u001eJ\u0016\u00105\u001a\u00020-2\u0006\u00106\u001a\u00020\u00162\u0006\u00107\u001a\u000208J6\u00109\u001a\u00020-2\u0006\u0010:\u001a\u00020\u00162\b\b\u0002\u0010;\u001a\u00020\u001b2\b\b\u0002\u0010<\u001a\u00020\u001b2\b\b\u0002\u0010=\u001a\u00020\u001b2\b\b\u0002\u0010>\u001a\u00020\u001bJ\u000e\u0010?\u001a\u00020-2\u0006\u0010:\u001a\u00020\u0016J\b\u0010@\u001a\u00020-H\u0002J\u0010\u0010A\u001a\u00020-2\b\b\u0002\u0010B\u001a\u00020\u001bJ\u0010\u0010C\u001a\u00020-2\u0006\u0010B\u001a\u00020\u001bH\u0002J\b\u0010D\u001a\u00020-H\u0002J\u0010\u0010E\u001a\u00020\u001b2\u0006\u0010F\u001a\u00020\u0016H\u0014J\u0018\u0010G\u001a\u00020\u001b2\u0006\u0010H\u001a\u00020&2\u0006\u0010I\u001a\u00020 H\u0002J(\u0010J\u001a\u00020-2\u0006\u0010H\u001a\u00020\"2\u0006\u0010K\u001a\u00020\u001e2\u0006\u0010L\u001a\u00020\u001e2\u0006\u0010M\u001a\u00020\u001eH\u0002JD\u0010N\u001a\u00020-2\u0006\u0010O\u001a\u00020\u00162\u0006\u0010K\u001a\u00020\u001e2\u0006\u0010L\u001a\u00020\u001e2\u0006\u0010M\u001a\u00020\u001e2\b\b\u0002\u0010P\u001a\u00020\u001e2\b\b\u0002\u0010Q\u001a\u00020\u001e2\b\b\u0002\u0010R\u001a\u00020\u001bJ\u0006\u0010S\u001a\u00020-J\u000e\u0010T\u001a\u00020-2\u0006\u0010U\u001a\u00020%J\u001e\u0010V\u001a\u00020-2\u0006\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u001e2\u0006\u0010Y\u001a\u00020\u0019J\u000e\u0010Z\u001a\u00020\u00162\u0006\u0010H\u001a\u00020&J\u0010\u0010Z\u001a\u00020\u00162\u0006\u0010U\u001a\u00020%H\u0002J\u0018\u0010Z\u001a\u00020\u00162\u0006\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u001eH\u0002J\u0018\u0010[\u001a\u00020\u001b2\u0006\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u001eH\u0002J\u0010\u0010[\u001a\u00020\u001b2\u0006\u0010U\u001a\u00020%H\u0002J\u0016\u0010\\\u001a\u00020\u001b2\u0006\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u001eJ\u0018\u0010]\u001a\u0004\u0018\u00010&2\u0006\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u001eJ\u0016\u0010^\u001a\b\u0012\u0004\u0012\u0002080_2\u0006\u0010`\u001a\u00020\u0016H\u0002J(\u0010a\u001a\u00020-2\u0006\u0010O\u001a\u00020\u00162\u0018\u0010b\u001a\u0014\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020-0cJ\u0010\u0010d\u001a\u00020\u001b2\u0006\u0010U\u001a\u00020%H\u0002J\u000e\u0010e\u001a\u00020\u001b2\u0006\u0010`\u001a\u00020\u0016J\u0018\u0010f\u001a\u00020\u001b2\u0006\u0010g\u001a\u00020\u00162\u0006\u0010h\u001a\u00020\u0016H\u0002J\u0018\u0010i\u001a\u00020\u00162\u0006\u0010j\u001a\u00020\u00162\u0006\u0010k\u001a\u00020lH\u0002J\u0006\u0010m\u001a\u00020\u001bJ\u0006\u0010n\u001a\u00020-J\u0006\u0010o\u001a\u00020\u001bJ\u0006\u0010p\u001a\u00020\u0016J\u0006\u0010q\u001a\u00020\u0016R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020&0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00190$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006y"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "inCurveFetcher", "Lxc/engine/core/graphics/engine/systems/TileSystem$CurveFetcher;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;Lxc/engine/core/graphics/engine/systems/TileSystem$CurveFetcher;)V", "systemName", "", "getSystemName", "()Ljava/lang/String;", "setSystemName", "(Ljava/lang/String;)V", "curveFetcher", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "preloadRect", "Landroid/graphics/RectF;", "destroyRect", "clearTimeInMilli", "", "bIsDataReady", "", "bIsEmpty", "vao", "", "tileRenderRes", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "fullscreenTile", "Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "tilesMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lxc/engine/core/graphics/engine/structs/TileCoord;", "Lxc/engine/core/graphics/engine/structs/Tile;", "curveTasksToExecuteInMainThread", "Ljava/util/LinkedList;", "Lxc/engine/core/graphics/engine/structs/CurveTask;", "tileLastUpdateTimeMap", "fullscreenTileLastUpdateTime", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "onScreenResize", "clearAllTiles", "onEntLoaded", "pageIndex", "updateNewCurve", "saveRect", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "updateArea", "rectWS", "bUpdateFullScreen", "bUpdateTiles", "isAsyncTask", "bCheckUnstableArea", "updateAreaLocallyForEraser", "updateSceneRect", "update", "skipExistedTile", "internalUpdate", "renderAdditiveCurveInMainThread", "onRenderLayer", "screenRect", "renderTile", "tile", "res", "renderScreenTile", "framebufferId", "tmpFramebufferId", "tmpTextureId", "renderArea", a.e, "widthPixel", "heightPixel", "bNeedClear", "recycleFullscreenTile", "recycleTile", "coord", "updateTileLastUpdateTime", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "timeInMilli", "getTileArea", "doesTileNeedDestroy", "hasTile", "getTile", "getCurvesByRectF", "", "inRectF", "loopAreaWithTileCoord", "operation", "Lkotlin/Function2;", "doesTileCoordHasCurve", "doesAreaHasCurve", "isIntersect", "rect1", "rect2", "getScaledRect", "rect", "scaleValue", "", "shouldShowFullscreenTile", "setDataReady", "isEmpty", "getPreloadArea", "getDestroyArea", "Companion", "TaskCallback", "CurveFetcher", "AsyncTileTask", "AsyncBatchTileTask", "AsyncScreenTask", "AsyncUpdateAreaTask", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class TileSystem extends BaseEngineLayer {
    private static final float FULLSCREEN_TILE_SHOW_THRESHOLD = 1.01f;
    private static final boolean SHOW_TILE_DEBUG_BORDER = false;
    public static final float TILE_SIZEF = 1024.0f;
    public static final int TILE_TEXTURE_SIZE = 1024;
    private boolean bIsDataReady;
    private boolean bIsEmpty;
    private long clearTimeInMilli;
    private final CurveFetcher curveFetcher;
    private final CurveRenderer curveRenderer;
    private final LinkedList<CurveTask> curveTasksToExecuteInMainThread;
    private RectF destroyRect;
    private final FullscreenTile fullscreenTile;
    private long fullscreenTileLastUpdateTime;
    private RectF preloadRect;
    private final ShapeRenderer shapeRenderer;
    private String systemName;
    private final ConcurrentHashMap<TileCoord, Long> tileLastUpdateTimeMap;
    private final TileRenderResource tileRenderRes;
    private final TileRenderer tileRenderer;
    private final ConcurrentHashMap<TileCoord, Tile> tilesMap;
    private int vao;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TileSystem";

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$TaskCallback;", "", "onTaskCompleted", "", "task", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface TaskCallback {
        void onTaskCompleted(BaseAsyncGLTask task);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSystem(MiCurveEngine engine, CurveFetcher inCurveFetcher) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        Intrinsics.checkNotNullParameter(inCurveFetcher, "inCurveFetcher");
        this.systemName = "";
        this.curveFetcher = inCurveFetcher;
        this.curveRenderer = engine.getCurveRenderer();
        this.shapeRenderer = engine.getShapeRenderer();
        this.tileRenderer = engine.getTileRenderer();
        this.preloadRect = new RectF();
        this.destroyRect = new RectF();
        this.bIsEmpty = true;
        this.tileRenderRes = new TileRenderResource(0, 1, null);
        this.fullscreenTile = new FullscreenTile();
        this.tilesMap = new ConcurrentHashMap<>();
        this.curveTasksToExecuteInMainThread = new LinkedList<>();
        this.tileLastUpdateTimeMap = new ConcurrentHashMap<>();
        this.fullscreenTileLastUpdateTime = Instant.now().toEpochMilli();
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ \u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\nR\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "TILE_SIZEF", "", "TILE_TEXTURE_SIZE", "", "FULLSCREEN_TILE_SHOW_THRESHOLD", "SHOW_TILE_DEBUG_BORDER", "", "combineCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "curves", "canCombine", "lastCurve", "currentCurve", "totalPointsCount", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<List<DataCurve>> combineCurves(List<? extends DataCurve> curves) {
            Intrinsics.checkNotNullParameter(curves, "curves");
            ArrayList arrayList = new ArrayList();
            DataCurve dataCurve = null;
            int size = 0;
            for (DataCurve dataCurve2 : curves) {
                if (dataCurve2.mDataState != 3) {
                    if (dataCurve == null || !TileSystem.INSTANCE.canCombine(dataCurve, dataCurve2, size)) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(dataCurve2);
                        arrayList.add(arrayList2);
                        size = dataCurve2.size(dataCurve2.mOriginPoints);
                    } else {
                        ((ArrayList) CollectionsKt.last((List) arrayList)).add(dataCurve2);
                        size += dataCurve2.size(dataCurve2.mOriginPoints);
                    }
                    dataCurve = dataCurve2;
                }
            }
            return arrayList;
        }

        public final boolean canCombine(DataCurve lastCurve, DataCurve currentCurve, int totalPointsCount) {
            Intrinsics.checkNotNullParameter(currentCurve, "currentCurve");
            if ((lastCurve != null && lastCurve.getType() == 28) || currentCurve.getType() == 28 || lastCurve == null || lastCurve.getType() != currentCurve.getType() || lastCurve.getPenType() != currentCurve.getPenType()) {
                return false;
            }
            if (currentCurve.getPenType() == PenType.MARK && lastCurve.getRotateAngle() != currentCurve.getRotateAngle()) {
                return false;
            }
            boolean z = lastCurve.getCurveColor() == currentCurve.getCurveColor() && lastCurve.getAlpha() == currentCurve.getAlpha();
            boolean z2 = lastCurve.getTime() == currentCurve.getTime();
            if (z) {
                return (z2 || currentCurve.getAlpha() >= 255) && totalPointsCount + currentCurve.size(currentCurve.mOriginPoints) < 524288;
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0016¨\u0006\r"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$CurveFetcher;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getDesiredCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "inSaveRectF", "Landroid/graphics/RectF;", "pageIndex", "", "doesAreaHasDesiredCurve", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static class CurveFetcher {
        public boolean doesAreaHasDesiredCurve(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return false;
        }

        public static /* synthetic */ List getDesiredCurves$default(CurveFetcher curveFetcher, RectF rectF, int i, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getDesiredCurves");
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return curveFetcher.getDesiredCurves(rectF, i);
        }

        public List<DataCurve> getDesiredCurves(RectF inSaveRectF, int pageIndex) {
            Intrinsics.checkNotNullParameter(inSaveRectF, "inSaveRectF");
            return new ArrayList();
        }

        public static /* synthetic */ boolean doesAreaHasDesiredCurve$default(CurveFetcher curveFetcher, RectF rectF, int i, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: doesAreaHasDesiredCurve");
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return curveFetcher.doesAreaHasDesiredCurve(rectF, i);
        }
    }

    public final String getSystemName() {
        return this.systemName;
    }

    public final void setSystemName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.systemName = str;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onInit(context);
        int[] iArr = new int[1];
        GLES30.glGenVertexArrays(1, iArr, 0);
        GLES30.glBindVertexArray(0);
        this.vao = iArr[0];
        this.tileRenderRes.init(getCurveEngine().getTexturePool());
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        this.tileRenderRes.destroy(getCurveEngine().getTexturePool());
        clearAllTiles();
        recycleFullscreenTile();
        GLES30.glDeleteVertexArrays(1, new int[]{this.vao}, 0);
        super.onDestroy();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        super.onScreenResize();
        recycleFullscreenTile();
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncTileTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inTileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "inCoord", "Lxc/engine/core/graphics/engine/structs/TileCoord;", CallMethod.ARG_CALLBACK, "Lxc/engine/core/graphics/engine/systems/TileSystem$TaskCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/TileSystem;Lxc/engine/core/graphics/engine/structs/TileCoord;Lxc/engine/core/graphics/engine/systems/TileSystem$TaskCallback;)V", "tile", "Lxc/engine/core/graphics/engine/structs/Tile;", "tileSystem", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AsyncTileTask extends BaseAsyncGLTask {
        private final TaskCallback callback;
        private final TexturePool texturePool;
        private final Tile tile;
        private final TileSystem tileSystem;

        public /* synthetic */ AsyncTileTask(TileSystem tileSystem, TileCoord tileCoord, TaskCallback taskCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSystem, tileCoord, (i & 4) != 0 ? null : taskCallback);
        }

        public AsyncTileTask(TileSystem inTileSystem, TileCoord inCoord, TaskCallback taskCallback) {
            Intrinsics.checkNotNullParameter(inTileSystem, "inTileSystem");
            Intrinsics.checkNotNullParameter(inCoord, "inCoord");
            this.callback = taskCallback;
            this.tile = new Tile(inCoord, false, 2, null);
            this.tileSystem = inTileSystem;
            this.texturePool = inTileSystem.getTexturePool();
        }

        /* JADX WARN: Code duplicated, block: B:10:0x0051  */
        /* JADX WARN: Code duplicated, block: B:8:0x0047  */
        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            boolean z = false;
            if (getCreateTimeInMilli() >= this.tileSystem.clearTimeInMilli) {
                if (this.tileSystem.tileLastUpdateTimeMap.containsKey(this.tile.getCoord())) {
                    long createTimeInMilli = getCreateTimeInMilli();
                    Object obj = this.tileSystem.tileLastUpdateTimeMap.get(this.tile.getCoord());
                    Intrinsics.checkNotNull(obj);
                    if (createTimeInMilli >= ((Number) obj).longValue()) {
                        if (this.tileSystem.renderTile(this.tile, res)) {
                            setSyncHandle(GLES30.glFenceSync(37143, 0));
                            z = true;
                        }
                    }
                } else if (this.tileSystem.renderTile(this.tile, res)) {
                    setSyncHandle(GLES30.glFenceSync(37143, 0));
                    z = true;
                }
            }
            TaskCallback taskCallback = this.callback;
            if (taskCallback != null) {
                taskCallback.onTaskCompleted(this);
            }
            return z;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Tile tile;
            if (getCreateTimeInMilli() >= this.tileSystem.clearTimeInMilli) {
                if (this.tileSystem.tilesMap.containsKey(this.tile.getCoord()) && (tile = (Tile) this.tileSystem.tilesMap.get(this.tile.getCoord())) != null) {
                    this.texturePool.recycleTileTexture(tile.getTextureId());
                }
                this.tileSystem.tilesMap.put(this.tile.getCoord(), this.tile);
                if (getBNeedRefreshRender()) {
                    this.tileSystem.markDirty();
                }
                DataManager.updateInfiniteBoundary$default(this.tileSystem.getCurveEngine().getDataManager(), this.tileSystem.getTileArea(this.tile), false, 2, null);
                return;
            }
            this.texturePool.recycleTileTexture(this.tile.getTextureId());
        }
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncBatchTileTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "tileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "tiles", "", "Lxc/engine/core/graphics/engine/structs/Tile;", CallMethod.ARG_CALLBACK, "Lxc/engine/core/graphics/engine/systems/TileSystem$TaskCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/TileSystem;Ljava/util/List;Lxc/engine/core/graphics/engine/systems/TileSystem$TaskCallback;)V", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "bRequestRenderWhenNoGLCommandExecuted", "", "getBRequestRenderWhenNoGLCommandExecuted", "()Z", "setBRequestRenderWhenNoGLCommandExecuted", "(Z)V", "startTime", "", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AsyncBatchTileTask extends BaseAsyncGLTask {
        private boolean bRequestRenderWhenNoGLCommandExecuted;
        private final TaskCallback callback;
        private long startTime;
        private final TexturePool texturePool;
        private final TileSystem tileSystem;
        private final List<Tile> tiles;

        public /* synthetic */ AsyncBatchTileTask(TileSystem tileSystem, List list, TaskCallback taskCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSystem, list, (i & 4) != 0 ? null : taskCallback);
        }

        public AsyncBatchTileTask(TileSystem tileSystem, List<Tile> tiles, TaskCallback taskCallback) {
            Intrinsics.checkNotNullParameter(tileSystem, "tileSystem");
            Intrinsics.checkNotNullParameter(tiles, "tiles");
            this.tileSystem = tileSystem;
            this.tiles = tiles;
            this.callback = taskCallback;
            this.texturePool = tileSystem.getTexturePool();
            this.bRequestRenderWhenNoGLCommandExecuted = true;
        }

        protected final boolean getBRequestRenderWhenNoGLCommandExecuted() {
            return this.bRequestRenderWhenNoGLCommandExecuted;
        }

        protected final void setBRequestRenderWhenNoGLCommandExecuted(boolean z) {
            this.bRequestRenderWhenNoGLCommandExecuted = z;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            boolean z = false;
            if (getCreateTimeInMilli() >= this.tileSystem.clearTimeInMilli) {
                this.startTime = Instant.now().toEpochMilli();
                boolean z2 = false;
                for (Tile tile : this.tiles) {
                    if (!this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.tileSystem.getTileArea(tile)) && this.tileSystem.renderTile(tile, res)) {
                        z2 = true;
                    }
                }
                if (z2) {
                    setSyncHandle(GLES30.glFenceSync(37143, 0));
                } else {
                    this.tileSystem.markDirty();
                    if (this.bRequestRenderWhenNoGLCommandExecuted) {
                        this.tileSystem.getCurveEngine().requestMultiBufferRender(MiCurveEngine.ERefreshReason.Force);
                    }
                }
                z = z2;
            }
            TaskCallback taskCallback = this.callback;
            if (taskCallback != null) {
                taskCallback.onTaskCompleted(this);
            }
            return z;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Tile tile;
            if (getCreateTimeInMilli() >= this.tileSystem.clearTimeInMilli) {
                RectF rectF = new RectF();
                for (Tile tile2 : this.tiles) {
                    if (tile2.getTextureId() > 0) {
                        if (!this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.tileSystem.getTileArea(tile2))) {
                            if (this.tileSystem.tilesMap.containsKey(tile2.getCoord()) && (tile = (Tile) this.tileSystem.tilesMap.get(tile2.getCoord())) != null) {
                                this.texturePool.recycleTileTexture(tile.getTextureId());
                            }
                            this.tileSystem.tilesMap.put(tile2.getCoord(), tile2);
                            rectF.union(this.tileSystem.getTileArea(tile2));
                        } else {
                            this.texturePool.recycleTileTexture(tile2.getTextureId());
                        }
                    }
                }
                DataManager.updateInfiniteBoundary$default(this.tileSystem.getCurveEngine().getDataManager(), rectF, false, 2, null);
                Instant.now().toEpochMilli();
                this.tileSystem.markDirty();
                return;
            }
            Iterator<T> it = this.tiles.iterator();
            while (it.hasNext()) {
                this.texturePool.recycleTileTexture(((Tile) it.next()).getTextureId());
            }
        }
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncScreenTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inTileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/TileSystem;)V", "screenTile", "Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "getScreenTile", "()Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "tileSystem", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AsyncScreenTask extends BaseAsyncGLTask {
        private final FullscreenTile screenTile;
        private final TexturePool texturePool;
        private final TileSystem tileSystem;

        public AsyncScreenTask(TileSystem inTileSystem) {
            Intrinsics.checkNotNullParameter(inTileSystem, "inTileSystem");
            this.screenTile = new FullscreenTile();
            this.tileSystem = inTileSystem;
            this.texturePool = inTileSystem.getTexturePool();
            inTileSystem.fullscreenTileLastUpdateTime = getCreateTimeInMilli();
        }

        public final FullscreenTile getScreenTile() {
            return this.screenTile;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            Intrinsics.checkNotNullParameter(res, "res");
            if (this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.screenTile.getScreenRect()) || getCreateTimeInMilli() < this.tileSystem.fullscreenTileLastUpdateTime) {
                return false;
            }
            Log.d(TileSystem.TAG, "xl-- fullscreen execute: " + this.screenTile.getScreenRect());
            this.screenTile.setTexture(this.tileSystem.acquireScreenTexture());
            this.screenTile.setScreenTmpTex(this.tileSystem.acquireScreenTexture());
            res.bindTileTexToFramebuffer(this.screenTile.getTexture().getTexId());
            GLUtils.INSTANCE.bindTextureToFramebuffer(res.getTmpScreenFramebufferId(), this.screenTile.getScreenTmpTex().getTexId());
            this.tileSystem.renderScreenTile(this.screenTile, res.getCurrentFramebufferId(), res.getTmpScreenFramebufferId(), this.screenTile.getScreenTmpTex().getTexId());
            setSyncHandle(GLES30.glFenceSync(37143, 0));
            return true;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            if (getCreateTimeInMilli() >= this.tileSystem.clearTimeInMilli && !this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.screenTile.getScreenRect())) {
                if (this.tileSystem.fullscreenTile.getTexture().isValid()) {
                    this.texturePool.recycleScreenTexture(this.tileSystem.fullscreenTile.getTexture());
                }
                this.tileSystem.fullscreenTile.setTexture(this.screenTile.getTexture());
                this.tileSystem.fullscreenTile.setScreenRect(this.screenTile.getScreenRect());
                this.texturePool.recycleScreenTexture(this.screenTile.getScreenTmpTex());
                Log.e(TileSystem.TAG, "xl- fullscreen render end");
                this.tileSystem.markDirty();
                return;
            }
            this.texturePool.recycleScreenTexture(this.screenTile.getTexture());
            this.texturePool.recycleScreenTexture(this.screenTile.getScreenTmpTex());
        }
    }

    public final void clearAllTiles() {
        this.clearTimeInMilli = Instant.now().toEpochMilli();
        recycleFullscreenTile();
        getTexturePool().recycleScreenTexture(getLayerTexture());
        setLayerTexture(new ScreenTexture(0, 0, 0, 7, null));
        for (Map.Entry<TileCoord, Tile> entry : this.tilesMap.entrySet()) {
            entry.getKey();
            getTexturePool().recycleTileTexture(entry.getValue().getTextureId());
        }
        this.tilesMap.clear();
        markDirty();
    }

    public final void onEntLoaded(int pageIndex) {
        if (getCurveEngine().isInfinite()) {
            return;
        }
        RectF rectF = new RectF(getCurveEngine().getPageSystem().getPageData(pageIndex).getArea());
        if (rectF.isEmpty()) {
            return;
        }
        RectF rectF2 = new RectF();
        for (Map.Entry<TileCoord, Tile> entry : this.tilesMap.entrySet()) {
            TileCoord key = entry.getKey();
            entry.getValue();
            RectF tileArea = getTileArea(key);
            if (RectF.intersects(tileArea, rectF)) {
                rectF2.union(tileArea);
            }
        }
        if (rectF2.isEmpty()) {
            return;
        }
        updateArea$default(this, rectF2, false, false, false, false, 30, null);
    }

    public final void updateNewCurve(RectF saveRect, DataCurve dataCurve) {
        Intrinsics.checkNotNullParameter(saveRect, "saveRect");
        Intrinsics.checkNotNullParameter(dataCurve, "dataCurve");
        this.clearTimeInMilli = 0L;
        float saveRatio = getCurveEngine().getSaveRatio();
        final RectF rectF = new RectF(saveRect.left / saveRatio, saveRect.top / saveRatio, saveRect.right / saveRatio, saveRect.bottom / saveRatio);
        float f = -((dataCurve.mCurveProp.getPenSize() * 2.0f) + 1.0f);
        rectF.inset(f, f);
        String str = TAG;
        Log.d(str, "xl- updateNewCurve: " + rectF + " penSize: " + dataCurve.mCurveProp.getPenSize() + " offset: " + dataCurve.renderOffsetX + StringUtils.SPACE + dataCurve.renderOffsetY);
        final CurveTask curveTask = new CurveTask();
        curveTask.setDataCurve(dataCurve);
        curveTask.setBHasFullScreenTile(shouldShowFullscreenTile() && (this.fullscreenTile.getTexture().isValid() || doesAreaHasCurve(rectF)));
        RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
        if (curveTask.getBHasFullScreenTile() && !Intrinsics.areEqual(this.fullscreenTile.getScreenRect(), screenAreaWS)) {
            curveTask.setBHasFullScreenTile(false);
            AsyncScreenTask asyncScreenTask = new AsyncScreenTask(this);
            asyncScreenTask.getScreenTile().setScreenRect(screenAreaWS);
            getCurveEngine().getTaskManager().enqueueTask(asyncScreenTask);
            Log.d(str, "xl-- updateNewCurve: fullscreen buffer has wrong position, recreate it.");
        }
        if (!curveTask.getBHasFullScreenTile()) {
            recycleFullscreenTile();
        }
        loopAreaWithTileCoord(rectF, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TileSystem.updateNewCurve$lambda$2(this.f$0, rectF, curveTask, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
            }
        });
        this.curveTasksToExecuteInMainThread.offer(curveTask);
        markDirty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit updateNewCurve$lambda$2(TileSystem tileSystem, RectF rectF, CurveTask curveTask, int i, int i2) {
        if (tileSystem.isIntersect(rectF, tileSystem.getTileArea(new TileCoord(i, i2)))) {
            curveTask.getTiles().add(new Tile(i, i2));
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void updateArea$default(TileSystem tileSystem, RectF rectF, boolean z, boolean z2, boolean z3, boolean z4, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateArea");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        if ((i & 8) != 0) {
            z3 = true;
        }
        if ((i & 16) != 0) {
            z4 = false;
        }
        tileSystem.updateArea(rectF, z, z2, z3, z4);
    }

    public final void updateArea(RectF rectWS, boolean bUpdateFullScreen, boolean bUpdateTiles, boolean isAsyncTask, boolean bCheckUnstableArea) {
        Intrinsics.checkNotNullParameter(rectWS, "rectWS");
        this.clearTimeInMilli = 0L;
        if (!shouldShowFullscreenTile()) {
            recycleFullscreenTile();
        }
        if (!this.destroyRect.isEmpty()) {
            rectWS.intersect(this.destroyRect);
        }
        AsyncUpdateAreaTask asyncUpdateAreaTask = new AsyncUpdateAreaTask(this, rectWS, getCurveEngine().getScreenSpaceRectFromWorldSpaceRect(rectWS), getCurveEngine().getScreenAreaWS(), bUpdateFullScreen, bUpdateTiles, bCheckUnstableArea);
        if (isAsyncTask) {
            getCurveEngine().getTaskManager().enqueueTask(asyncUpdateAreaTask);
        } else {
            MiCurveEngine.addSyncTask$default(getCurveEngine(), asyncUpdateAreaTask, false, 2, null);
        }
    }

    public final void updateAreaLocallyForEraser(RectF rectWS) {
        Intrinsics.checkNotNullParameter(rectWS, "rectWS");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        loopAreaWithTileCoord(rectWS, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TileSystem.updateAreaLocallyForEraser$lambda$3(this.f$0, booleanRef, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
            }
        });
        if (booleanRef.element) {
            getCurveEngine().getTaskManager().enqueueTask(new AsyncUpdateAreaTask(this, rectWS, getCurveEngine().getScreenSpaceRectFromWorldSpaceRect(rectWS), getCurveEngine().getScreenAreaWS(), shouldShowFullscreenTile(), !shouldShowFullscreenTile(), true));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit updateAreaLocallyForEraser$lambda$3(TileSystem tileSystem, Ref.BooleanRef booleanRef, int i, int i2) {
        if (tileSystem.hasTile(i, i2)) {
            booleanRef.element = true;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: compiled from: TileSystem.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncUpdateAreaTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "tileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "inRectWS", "Landroid/graphics/RectF;", "inRectSS", "screenRectWS", "bFullscreen", "", "bIncludeTiles", "bCheckUnstableArea", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/TileSystem;Landroid/graphics/RectF;Landroid/graphics/RectF;Landroid/graphics/RectF;ZZZ)V", "renderAreaWS", "renderAreaSS", "newlyCreatedTile", "Ljava/util/ArrayList;", "Lxc/engine/core/graphics/engine/structs/Tile;", "Lkotlin/collections/ArrayList;", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "getAlignedRect", "inRect", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static class AsyncUpdateAreaTask extends BaseAsyncGLTask {
        private final boolean bCheckUnstableArea;
        private final boolean bFullscreen;
        private final boolean bIncludeTiles;
        private final RectF inRectSS;
        private final RectF inRectWS;
        private final ArrayList<Tile> newlyCreatedTile;
        private final RectF renderAreaSS;
        private final RectF renderAreaWS;
        private final RectF screenRectWS;
        private final TileSystem tileSystem;

        public /* synthetic */ AsyncUpdateAreaTask(TileSystem tileSystem, RectF rectF, RectF rectF2, RectF rectF3, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSystem, rectF, rectF2, rectF3, z, z2, (i & 64) != 0 ? false : z3);
        }

        public AsyncUpdateAreaTask(TileSystem tileSystem, RectF inRectWS, RectF inRectSS, RectF screenRectWS, boolean z, boolean z2, boolean z3) {
            Intrinsics.checkNotNullParameter(tileSystem, "tileSystem");
            Intrinsics.checkNotNullParameter(inRectWS, "inRectWS");
            Intrinsics.checkNotNullParameter(inRectSS, "inRectSS");
            Intrinsics.checkNotNullParameter(screenRectWS, "screenRectWS");
            this.tileSystem = tileSystem;
            this.inRectWS = inRectWS;
            this.inRectSS = inRectSS;
            this.screenRectWS = screenRectWS;
            this.bFullscreen = z;
            this.bIncludeTiles = z2;
            this.bCheckUnstableArea = z3;
            this.renderAreaWS = getAlignedRect(inRectWS);
            this.renderAreaSS = getAlignedRect(inRectSS);
            this.newlyCreatedTile = new ArrayList<>();
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            Intrinsics.checkNotNullParameter(res, "res");
            boolean z = this.bFullscreen;
            if (!z && !this.bIncludeTiles) {
                return false;
            }
            RectF rectF = z ? this.renderAreaSS : this.renderAreaWS;
            Point limitedTextureSize$default = GLUtils.Companion.getLimitedTextureSize$default(GLUtils.INSTANCE, rectF.width(), rectF.height(), 0.0f, 4, null);
            int i = limitedTextureSize$default.x;
            int i2 = limitedTextureSize$default.y;
            if (this.bCheckUnstableArea && this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.renderAreaWS)) {
                return false;
            }
            final int iCreateTexture = GLUtils.INSTANCE.createTexture(i, i2);
            int iCreateTexture2 = GLUtils.INSTANCE.createTexture(i, i2);
            int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture);
            final int iCreateFramebufferFromTexture2 = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture2);
            TileSystem.renderArea$default(this.tileSystem, this.renderAreaWS, iCreateFramebufferFromTexture, iCreateFramebufferFromTexture2, iCreateTexture2, i, i2, false, 64, null);
            if (this.bCheckUnstableArea && this.tileSystem.getCurveEngine().getCurveSystem().isAreaUnstable(this.renderAreaWS)) {
                GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture2);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture2);
                XLog.i(TileSystem.TAG, "---- updateArea: unstable area", new Object[0]);
                return false;
            }
            if (this.bFullscreen && this.tileSystem.fullscreenTile.getTexture().isValid() && !Intrinsics.areEqual(this.tileSystem.fullscreenTile.getScreenRect(), this.screenRectWS)) {
                Log.d(TileSystem.TAG, "xl-- AsyncUpdateAreaTask: fullscreen buffer has wrong position, recreate it.");
                AsyncScreenTask asyncScreenTask = new AsyncScreenTask(this.tileSystem);
                asyncScreenTask.getScreenTile().setScreenRect(this.screenRectWS);
                this.tileSystem.getCurveEngine().getTaskManager().enqueueTask(asyncScreenTask);
            }
            if (this.bFullscreen && this.tileSystem.fullscreenTile.getTexture().isValid() && Intrinsics.areEqual(this.tileSystem.fullscreenTile.getScreenRect(), this.screenRectWS)) {
                GLUtils.INSTANCE.bindTextureToFramebuffer(iCreateFramebufferFromTexture2, this.tileSystem.fullscreenTile.getTexture().getTexId());
                GLES30.glViewport(0, 0, this.tileSystem.getCurveEngine().getScreenWidthInPixel(), this.tileSystem.getCurveEngine().getScreenHeightInPixel());
                GLES30.glBlendFuncSeparate(1, 0, 1, 0);
                TileRenderer.drawRect$default(this.tileSystem.tileRenderer, this.renderAreaWS, new RectF(this.screenRectWS.left, this.screenRectWS.bottom, this.screenRectWS.right, this.screenRectWS.top), iCreateTexture, false, false, 0.0f, 48, null);
                GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
            }
            if (this.bIncludeTiles) {
                this.tileSystem.loopAreaWithTileCoord(this.renderAreaWS, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$AsyncUpdateAreaTask$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return TileSystem.AsyncUpdateAreaTask.execute$lambda$0(this.f$0, iCreateFramebufferFromTexture2, iCreateTexture, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
                    }
                });
            }
            GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
            GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture2);
            GLUtils.INSTANCE.deleteTexture(iCreateTexture);
            GLUtils.INSTANCE.deleteTexture(iCreateTexture2);
            createSyncHandle();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:12:0x0044  */
        public static final Unit execute$lambda$0(AsyncUpdateAreaTask asyncUpdateAreaTask, int i, int i2, int i3, int i4) {
            boolean z;
            int textureId;
            TileCoord tileCoord = new TileCoord(i3, i4);
            if (asyncUpdateAreaTask.tileSystem.doesTileCoordHasCurve(tileCoord) && !asyncUpdateAreaTask.tileSystem.doesTileNeedDestroy(i3, i4)) {
                if (asyncUpdateAreaTask.tileSystem.tilesMap.containsKey(tileCoord)) {
                    Object obj = asyncUpdateAreaTask.tileSystem.tilesMap.get(tileCoord);
                    Intrinsics.checkNotNull(obj);
                    if (((Tile) obj).getBDummy()) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    z = false;
                }
                if (asyncUpdateAreaTask.tileSystem.tilesMap.containsKey(tileCoord) && !z) {
                    if (asyncUpdateAreaTask.tileSystem.tilesMap.containsKey(tileCoord)) {
                        Object obj2 = asyncUpdateAreaTask.tileSystem.tilesMap.get(tileCoord);
                        Intrinsics.checkNotNull(obj2);
                        textureId = ((Tile) obj2).getTextureId();
                    } else {
                        textureId = -1;
                    }
                } else {
                    Tile tile = new Tile(i3, i4);
                    tile.setTextureId(asyncUpdateAreaTask.tileSystem.getTexturePool().acquireTileTexture());
                    int textureId2 = tile.getTextureId();
                    GLUtils.INSTANCE.bindTextureToFramebuffer(i, tile.getTextureId());
                    GLES30.glViewport(0, 0, 1024, 1024);
                    GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES30.glClear(16384);
                    asyncUpdateAreaTask.newlyCreatedTile.add(tile);
                    textureId = textureId2;
                }
                if (textureId > 0) {
                    GLUtils.INSTANCE.bindTextureToFramebuffer(i, textureId);
                    GLES30.glViewport(0, 0, 1024, 1024);
                    GLES30.glBlendFuncSeparate(1, 0, 1, 0);
                    RectF tileArea = asyncUpdateAreaTask.tileSystem.getTileArea(i3, i4);
                    TileRenderer.drawRect$default(asyncUpdateAreaTask.tileSystem.tileRenderer, asyncUpdateAreaTask.renderAreaWS, new RectF(tileArea.left, tileArea.bottom, tileArea.right, tileArea.top), i2, false, false, 0.0f, 48, null);
                    GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
                }
            } else {
                asyncUpdateAreaTask.tileSystem.recycleTile(tileCoord);
            }
            return Unit.INSTANCE;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Tile tile;
            for (Tile tile2 : this.newlyCreatedTile) {
                if (this.tileSystem.tilesMap.containsKey(tile2.getCoord()) && (tile = (Tile) this.tileSystem.tilesMap.get(tile2.getCoord())) != null) {
                    this.tileSystem.getTexturePool().recycleTileTexture(tile.getTextureId());
                }
                this.tileSystem.tilesMap.put(tile2.getCoord(), tile2);
            }
            this.tileSystem.markDirty();
        }

        private final RectF getAlignedRect(RectF inRect) {
            return new RectF((float) Math.floor(inRect.left), (float) Math.floor(inRect.top), (float) Math.ceil(inRect.right), (float) Math.ceil(inRect.bottom));
        }
    }

    private final void updateSceneRect() {
        float fMin = 1024.0f / Math.min(getCurveEngine().getCamera().getFactorScale(), 1.0f);
        this.preloadRect = new RectF(getCurveEngine().getScreenAreaWS());
        if (getCurveEngine().isInfinite()) {
            float f = (-fMin) * 2;
            this.preloadRect.inset(f, f);
        } else {
            float f2 = -fMin;
            this.preloadRect.inset(2 * f2, f2 * 7);
        }
        RectF rectF = new RectF(this.preloadRect);
        this.destroyRect = rectF;
        float f3 = (-fMin) * 2;
        rectF.inset(f3, f3);
    }

    public static /* synthetic */ void update$default(TileSystem tileSystem, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: update");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        tileSystem.update(z);
    }

    public final void update(boolean skipExistedTile) {
        if (this.bIsDataReady && getCurveEngine().isReady()) {
            synchronized (this) {
                internalUpdate(skipExistedTile);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    private final void internalUpdate(final boolean skipExistedTile) {
        updateSceneRect();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<TileCoord, Tile> entry : this.tilesMap.entrySet()) {
            TileCoord key = entry.getKey();
            Tile value = entry.getValue();
            if (doesTileNeedDestroy(key)) {
                arrayList.add(key);
                getTexturePool().recycleTileTexture(value.getTextureId());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.tilesMap.remove((TileCoord) it.next());
        }
        if (shouldShowFullscreenTile()) {
            AsyncScreenTask asyncScreenTask = new AsyncScreenTask(this);
            asyncScreenTask.getScreenTile().setScreenRect(getCurveEngine().getScreenAreaWS());
            getCurveEngine().getTaskManager().enqueueTask(asyncScreenTask);
        }
        final ArrayList arrayList2 = new ArrayList();
        loopAreaWithTileCoord(this.preloadRect, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TileSystem.internalUpdate$lambda$7(this.f$0, skipExistedTile, arrayList2, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
            }
        });
        if (arrayList2.isEmpty()) {
            return;
        }
        Log.w(TAG, "xl-- " + this.systemName + " update Create tile count: " + arrayList2.size());
        ArrayList<TileCoord> arrayList3 = arrayList2;
        for (TileCoord tileCoord : arrayList3) {
            if (!this.tilesMap.containsKey(tileCoord)) {
                this.tilesMap.put(tileCoord, new Tile(tileCoord, true));
            }
        }
        if (arrayList2.size() > 0) {
            getCurveEngine().startTaskBatch(1);
        }
        TaskCallback taskCallback = new TaskCallback() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$internalUpdate$taskCallback$1
            @Override // xc.engine.core.graphics.engine.systems.TileSystem.TaskCallback
            public void onTaskCompleted(BaseAsyncGLTask task) {
                Intrinsics.checkNotNullParameter(task, "task");
                this.this$0.getCurveEngine().completeTask();
            }
        };
        if (getCurveEngine().isInfinite()) {
            TileCoord tileCoord2 = (TileCoord) CollectionsKt.lastOrNull((List) arrayList2);
            for (TileCoord tileCoord3 : arrayList3) {
                boolean zAreEqual = Intrinsics.areEqual(tileCoord3, tileCoord2);
                AsyncTileTask asyncTileTask = new AsyncTileTask(this, tileCoord3, zAreEqual ? taskCallback : null);
                asyncTileTask.setBNeedRefreshRender(zAreEqual);
                if (zAreEqual) {
                    XLog.v("", "Add Task [%s] @%d Callback", asyncTileTask.getClass().getSimpleName(), Integer.valueOf(asyncTileTask.getTaskId()));
                }
                getCurveEngine().getTaskManager().enqueueTask(asyncTileTask);
                this.tileLastUpdateTimeMap.put(tileCoord3, Long.valueOf(asyncTileTask.getCreateTimeInMilli()));
            }
            return;
        }
        HashMap map = new HashMap();
        for (TileCoord tileCoord4 : arrayList3) {
            if (!map.containsKey(Integer.valueOf(tileCoord4.getY()))) {
                map.put(Integer.valueOf(tileCoord4.getY()), new ArrayList());
            }
            ArrayList arrayList4 = (ArrayList) map.get(Integer.valueOf(tileCoord4.getY()));
            if (arrayList4 != null) {
                arrayList4.add(tileCoord4);
            }
        }
        Set setKeySet = map.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        SortedSet<Integer> sortedSet = CollectionsKt.toSortedSet(setKeySet);
        Integer num = (Integer) sortedSet.last();
        for (Integer num2 : sortedSet) {
            boolean zAreEqual2 = Intrinsics.areEqual(num2, num);
            ArrayList arrayList5 = new ArrayList();
            AsyncBatchTileTask asyncBatchTileTask = new AsyncBatchTileTask(this, arrayList5, zAreEqual2 ? taskCallback : null);
            asyncBatchTileTask.setBNeedRefreshRender(zAreEqual2);
            ArrayList<TileCoord> arrayList6 = (ArrayList) map.get(num2);
            if (arrayList6 != null) {
                for (TileCoord tileCoord5 : arrayList6) {
                    arrayList5.add(new Tile(tileCoord5, false, 2, null));
                    this.tileLastUpdateTimeMap.put(tileCoord5, Long.valueOf(asyncBatchTileTask.getCreateTimeInMilli()));
                }
            }
            getCurveEngine().getTaskManager().enqueueTask(asyncBatchTileTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit internalUpdate$lambda$7(TileSystem tileSystem, boolean z, ArrayList arrayList, int i, int i2) {
        TileCoord tileCoord = new TileCoord(i, i2);
        if ((!tileSystem.tilesMap.containsKey(tileCoord) || !z) && tileSystem.doesTileCoordHasCurve(tileCoord)) {
            arrayList.add(tileCoord);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Failed to calculate best type for var: r12v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v5 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r12v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v5 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r12v6 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v6 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r12v8 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v8 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v9 ??, new type: xc.engine.core.graphics.engine.structs.Tile
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r3v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v9 ??, new type: xc.engine.core.graphics.engine.structs.Tile
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v18 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v18 ??, new type: java.util.concurrent.ConcurrentHashMap<xc.engine.core.graphics.engine.structs.TileCoord, xc.engine.core.graphics.engine.structs.Tile>
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v18 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v18 ??, new type: java.util.concurrent.ConcurrentHashMap<xc.engine.core.graphics.engine.structs.TileCoord, xc.engine.core.graphics.engine.structs.Tile>
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v28 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v28 ??, new type: xc.engine.core.graphics.engine.structs.Tile
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r5v28 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v28 ??, new type: xc.engine.core.graphics.engine.structs.Tile
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v5 ??, new type: int
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
        	... 5 more
        */
    private final void renderAdditiveCurveInMainThread() {
        /*
            Method dump skipped, instruction units count: 577
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xc.engine.core.graphics.engine.systems.TileSystem.renderAdditiveCurveInMainThread():void");
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    protected boolean onRenderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        renderAdditiveCurveInMainThread();
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glBindFramebuffer(36160, getLayerFramebufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        this.bIsEmpty = true;
        for (Map.Entry<TileCoord, Tile> entry : this.tilesMap.entrySet()) {
            entry.getKey();
            Tile value = entry.getValue();
            RectF tileArea = getTileArea(value);
            if (!value.getBDummy() && value.getTextureId() > 0 && RectF.intersects(tileArea, screenRect)) {
                TileRenderer.drawRect$default(this.tileRenderer, tileArea, screenRect, value.getTextureId(), false, false, 0.0f, 32, null);
                this.bIsEmpty = false;
            }
        }
        if (!this.bIsEmpty && shouldShowFullscreenTile() && this.fullscreenTile.getTexture().isValid()) {
            GLES30.glBlendFuncSeparate(1, 0, 1, 0);
            TileRenderer.drawRect$default(this.tileRenderer, this.fullscreenTile.getScreenRect(), screenRect, this.fullscreenTile.getTexture().getTexId(), false, false, 0.0f, 32, null);
            GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean renderTile(Tile tile, TileRenderResource res) throws Throwable {
        if (!doesTileCoordHasCurve(tile.getCoord())) {
            recycleTile(tile.getCoord());
            return false;
        }
        if (tile.getTextureId() <= 0) {
            tile.setTextureId(getTexturePool().acquireTileTexture());
        }
        res.bindTileTexToFramebuffer(tile.getTextureId());
        renderArea$default(this, getTileArea(tile.getCoord()), res.getCurrentFramebufferId(), res.getTmpFramebufferId(), res.getTmpTextureId(), 0, 0, false, 112, null);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderScreenTile(FullscreenTile tile, int framebufferId, int tmpFramebufferId, int tmpTextureId) throws Throwable {
        renderArea$default(this, tile.getScreenRect(), framebufferId, tmpFramebufferId, tmpTextureId, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel(), false, 64, null);
    }

    public static /* synthetic */ void renderArea$default(TileSystem tileSystem, RectF rectF, int i, int i2, int i3, int i4, int i5, boolean z, int i6, Object obj) throws Throwable {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderArea");
        }
        if ((i6 & 16) != 0) {
            i4 = 1024;
        }
        if ((i6 & 32) != 0) {
            i5 = 1024;
        }
        if ((i6 & 64) != 0) {
            z = true;
        }
        tileSystem.renderArea(rectF, i, i2, i3, i4, i5, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void renderArea(RectF area, int framebufferId, int tmpFramebufferId, int tmpTextureId, int widthPixel, int heightPixel, boolean bNeedClear) throws Throwable {
        char c;
        char c2;
        long j;
        char c3;
        List<DataCurve> list;
        area = area;
        Intrinsics.checkNotNullParameter(area, "area");
        if (getCurveEngine().getIsDestroyCalled().get()) {
            return;
        }
        Instant.now().toEpochMilli();
        List<DataCurve> curvesByRectF = getCurvesByRectF(area);
        float fWidth = widthPixel / area.width();
        framebufferId = framebufferId;
        GLES30.glBindFramebuffer(36160, framebufferId);
        char c4 = 0;
        GLES30.glViewport(0, 0, widthPixel, heightPixel);
        if (bNeedClear) {
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
        }
        Iterator<List<DataCurve>> it = INSTANCE.combineCurves(curvesByRectF).iterator();
        while (it.hasNext()) {
            List<DataCurve> next = it.next();
            if (getCurveEngine().getIsDestroyCalled().get()) {
                return;
            }
            short type = ((DataCurve) CollectionsKt.first((List) next)).getType();
            char c5 = 1;
            if (type == 1) {
                Instant.now().toEpochMilli();
                long j2 = 0;
                long jNativeGenCurveVertexBuffer = 0;
                for (DataCurve dataCurve : next) {
                    if (getCurveEngine().getIsDestroyCalled().get()) {
                        break;
                    }
                    if (dataCurve.mOriginPoints != j2) {
                        Object mLockObj = dataCurve.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                        synchronized (mLockObj) {
                            float value = dataCurve.getCurveProp().getPenType().getValue();
                            float penSize = dataCurve.getCurveProp().getPenSize();
                            float drawRatio = dataCurve.getDrawRatio();
                            c2 = c5;
                            float f = area.left;
                            j = j2;
                            float f2 = area.top;
                            float f3 = area.right;
                            c3 = c4;
                            float f4 = area.bottom;
                            float sensitivity = dataCurve.getSensitivity();
                            float tipSize = dataCurve.getTipSize();
                            list = next;
                            float[] fArr = new float[10];
                            fArr[c3] = value;
                            fArr[c2] = penSize;
                            fArr[2] = drawRatio;
                            fArr[3] = fWidth;
                            fArr[4] = f;
                            fArr[5] = f2;
                            fArr[6] = f3;
                            fArr[7] = f4;
                            fArr[8] = sensitivity;
                            fArr[9] = tipSize;
                            jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve.mOriginPoints, fArr, jNativeGenCurveVertexBuffer, jNativeGenCurveVertexBuffer != j ? c2 : c3);
                            Unit unit = Unit.INSTANCE;
                        }
                        c5 = c2;
                        j2 = j;
                        c4 = c3;
                        next = list;
                    } else {
                        j2 = j2;
                    }
                }
                c = c4;
                Instant.now().toEpochMilli();
                CurveRenderer.renderCurve$default(this.curveRenderer, (DataCurve) CollectionsKt.first((List) next), area, framebufferId, tmpFramebufferId, tmpTextureId, fWidth, jNativeGenCurveVertexBuffer, false, 128, null);
            } else {
                float f5 = fWidth;
                c = c4;
                if (type == 28) {
                    ShapeRenderer shapeRenderer = this.shapeRenderer;
                    Object objFirst = CollectionsKt.first((List<? extends Object>) next);
                    Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type xc.engine.data.pagedata.DataShape");
                    ShapeRenderer.render$default(shapeRenderer, (DataShape) objFirst, framebufferId, area, getCurveEngine().getDrawRatio(), 0.0f, 0.0f, 48, null);
                }
                fWidth = f5;
            }
            c4 = c;
        }
    }

    public final void recycleFullscreenTile() {
        getTexturePool().recycleScreenTexture(this.fullscreenTile.getTexture());
        this.fullscreenTile.setTexture(new ScreenTexture(0, 0, 0, 7, null));
    }

    public final void recycleTile(TileCoord coord) {
        Intrinsics.checkNotNullParameter(coord, "coord");
        Tile tileRemove = this.tilesMap.remove(coord);
        if (tileRemove != null) {
            getTexturePool().recycleTileTexture(tileRemove.getTextureId());
        }
    }

    public final void updateTileLastUpdateTime(int x, int y, long timeInMilli) {
        if (this.tileLastUpdateTimeMap.containsKey(new TileCoord(x, y))) {
            this.tileLastUpdateTimeMap.put(new TileCoord(x, y), Long.valueOf(timeInMilli));
        }
    }

    public final RectF getTileArea(Tile tile) {
        Intrinsics.checkNotNullParameter(tile, "tile");
        return getTileArea(tile.getCoord());
    }

    private final RectF getTileArea(TileCoord coord) {
        return getTileArea(coord.getX(), coord.getY());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RectF getTileArea(int x, int y) {
        return new RectF(x * 1024.0f, y * 1024.0f, (x + 1) * 1024.0f, (y + 1) * 1024.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean doesTileNeedDestroy(int x, int y) {
        return doesTileNeedDestroy(new TileCoord(x, y));
    }

    private final boolean doesTileNeedDestroy(TileCoord coord) {
        RectF tileArea = getTileArea(coord);
        if (!getCurveEngine().isInfinite()) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            booleanRef.element = true;
            getCurveEngine().getPageSystem().loopPagesInTargetArea(tileArea, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return TileSystem.doesTileNeedDestroy$lambda$18(this.f$0, booleanRef, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
                }
            });
            return booleanRef.element;
        }
        return !isIntersect(tileArea, this.destroyRect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit doesTileNeedDestroy$lambda$18(TileSystem tileSystem, Ref.BooleanRef booleanRef, PageSystem.PageInfo pageInfo, PageSystem.PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        if (RectF.intersects(pageData.getArea(), tileSystem.destroyRect)) {
            booleanRef.element = false;
        }
        return Unit.INSTANCE;
    }

    public final boolean hasTile(int x, int y) {
        return this.tilesMap.containsKey(new TileCoord(x, y));
    }

    public final Tile getTile(int x, int y) {
        if (hasTile(x, y)) {
            return this.tilesMap.get(new TileCoord(x, y));
        }
        return null;
    }

    private final List<DataCurve> getCurvesByRectF(final RectF inRectF) {
        CanvasOperator canvasOperator = getCurveEngine().getPenEngine().mCanvasOperator;
        Intrinsics.checkNotNull(canvasOperator);
        final float saveRatio = canvasOperator.getSaveRatio();
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Paginated) {
            return CurveFetcher.getDesiredCurves$default(this.curveFetcher, new RectF(inRectF.left * saveRatio, inRectF.top * saveRatio, inRectF.right * saveRatio, inRectF.bottom * saveRatio), 0, 2, null);
        }
        final ArrayList arrayList = new ArrayList();
        getCurveEngine().getPageSystem().loopPagesInTargetArea(inRectF, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TileSystem.getCurvesByRectF$lambda$20(inRectF, saveRatio, this, arrayList, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getCurvesByRectF$lambda$20(RectF rectF, float f, TileSystem tileSystem, ArrayList arrayList, PageSystem.PageInfo pageInfo, PageSystem.PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        RectF rectF2 = new RectF(rectF);
        rectF2.intersect(pageData.getArea());
        Log.e(TAG, "xl-- getCurvesByRectF: " + rectF + ", " + rectF.width() + " * " + rectF.height());
        RectF rectF3 = new RectF(rectF2);
        rectF3.left -= pageData.getOriginXInWS();
        rectF3.right -= pageData.getOriginXInWS();
        rectF3.top -= pageData.getOriginYInWS();
        rectF3.bottom -= pageData.getOriginYInWS();
        List<DataCurve> desiredCurves = tileSystem.curveFetcher.getDesiredCurves(new RectF(rectF3.left * f, rectF3.top * f, rectF3.right * f, rectF3.bottom * f), pageData.getId());
        for (DataCurve dataCurve : desiredCurves) {
            dataCurve.renderOffsetX = pageData.getOriginXInWS();
            dataCurve.renderOffsetY = pageData.getOriginYInWS();
        }
        arrayList.addAll(desiredCurves);
        Log.e(TAG, "xl-- getCurves page[" + pageData.getId() + "] space: " + rectF3 + ", curve count: " + desiredCurves.size());
        return Unit.INSTANCE;
    }

    public final void loopAreaWithTileCoord(RectF area, Function2<? super Integer, ? super Integer, Unit> operation) {
        Intrinsics.checkNotNullParameter(area, "area");
        Intrinsics.checkNotNullParameter(operation, "operation");
        int iFloor = (int) Math.floor(area.left / 1024.0f);
        int iFloor2 = ((int) Math.floor(area.right / 1024.0f)) + 1;
        int iFloor3 = ((int) Math.floor(area.bottom / 1024.0f)) + 1;
        for (int iFloor4 = (int) Math.floor(area.top / 1024.0f); iFloor4 < iFloor3; iFloor4++) {
            for (int i = iFloor; i < iFloor2; i++) {
                if (RectF.intersects(area, getTileArea(i, iFloor4))) {
                    operation.invoke(Integer.valueOf(i), Integer.valueOf(iFloor4));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean doesTileCoordHasCurve(TileCoord coord) {
        return doesAreaHasCurve(getTileArea(coord));
    }

    public final boolean doesAreaHasCurve(final RectF inRectF) {
        Intrinsics.checkNotNullParameter(inRectF, "inRectF");
        CanvasOperator canvasOperator = getCurveEngine().getPenEngine().mCanvasOperator;
        Intrinsics.checkNotNull(canvasOperator);
        final float saveRatio = canvasOperator.getSaveRatio();
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Paginated) {
            return CurveFetcher.doesAreaHasDesiredCurve$default(this.curveFetcher, new RectF(inRectF.left * saveRatio, inRectF.top * saveRatio, inRectF.right * saveRatio, inRectF.bottom * saveRatio), 0, 2, null);
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        getCurveEngine().getPageSystem().loopPagesInTargetArea(inRectF, new Function2() { // from class: xc.engine.core.graphics.engine.systems.TileSystem$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TileSystem.doesAreaHasCurve$lambda$21(inRectF, saveRatio, this, booleanRef, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
            }
        });
        return booleanRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit doesAreaHasCurve$lambda$21(RectF rectF, float f, TileSystem tileSystem, Ref.BooleanRef booleanRef, PageSystem.PageInfo pageInfo, PageSystem.PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        RectF rectF2 = new RectF(rectF);
        rectF2.intersect(pageData.getArea());
        RectF rectF3 = new RectF(rectF2);
        rectF3.left -= pageData.getOriginXInWS();
        rectF3.right -= pageData.getOriginXInWS();
        rectF3.top -= pageData.getOriginYInWS();
        rectF3.bottom -= pageData.getOriginYInWS();
        if (CurveFetcher.doesAreaHasDesiredCurve$default(tileSystem.curveFetcher, new RectF(rectF3.left * f, rectF3.top * f, rectF3.right * f, rectF3.bottom * f), 0, 2, null)) {
            booleanRef.element = true;
        }
        return Unit.INSTANCE;
    }

    private final boolean isIntersect(RectF rect1, RectF rect2) {
        return rect1.left <= rect2.right && rect1.top <= rect2.bottom && rect1.right >= rect2.left && rect1.bottom >= rect2.top;
    }

    private final RectF getScaledRect(RectF rect, float scaleValue) {
        float f = scaleValue - 1.0f;
        float fWidth = rect.width() * f * 0.5f;
        float fHeight = rect.height() * f * 0.5f;
        return new RectF(rect.left - fWidth, rect.top - fHeight, rect.right + fWidth, rect.bottom + fHeight);
    }

    public final boolean shouldShowFullscreenTile() {
        return getCurveEngine().getCamera().getFactorScale() > FULLSCREEN_TILE_SHOW_THRESHOLD;
    }

    public final void setDataReady() {
        this.bIsDataReady = true;
    }

    /* JADX INFO: renamed from: isEmpty, reason: from getter */
    public final boolean getBIsEmpty() {
        return this.bIsEmpty;
    }

    public final RectF getPreloadArea() {
        return new RectF(this.preloadRect);
    }

    public final RectF getDestroyArea() {
        return new RectF(this.destroyRect);
    }
}
