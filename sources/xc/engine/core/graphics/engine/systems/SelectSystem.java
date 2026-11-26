package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import defpackage.R2;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miui.cloud.sync.FamilyInfo;
import org.apache.commons.lang3.StringUtils;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.DataManager;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.core.ScreenTexture;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.AnchorBoxRenderer;
import xc.engine.core.graphics.engine.renderers.CropFrameRenderer;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.DashedLineRenderer;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.core.graphics.opengl.GLRenderUtil;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.utils.XLog;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.scale.ScaleOperator;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: SelectSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 \u0089\u00012\u00020\u0001:\u001a\u0089\u0001\u008a\u0001\u008b\u0001\u008c\u0001\u008d\u0001\u008e\u0001\u008f\u0001\u0090\u0001\u0091\u0001\u0092\u0001\u0093\u0001\u0094\u0001\u0095\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0014J\b\u0010G\u001a\u00020DH\u0014J\b\u0010H\u001a\u00020DH\u0016J\u0006\u0010I\u001a\u00020\u001fJ\u0006\u0010J\u001a\u000201J\b\u0010K\u001a\u0004\u0018\u000106J\u0006\u0010L\u001a\u00020\u0015J\u0006\u0010M\u001a\u00020=J\u0006\u0010N\u001a\u000201J6\u0010O\u001a\u00020D2\u0006\u0010P\u001a\u00020=2\u0006\u0010Q\u001a\u00020=2\u0006\u0010R\u001a\u00020=2\u0006\u0010S\u001a\u00020!2\u0006\u0010T\u001a\u00020=2\u0006\u0010U\u001a\u00020=J\u001e\u0010V\u001a\u00020D2\u0006\u0010W\u001a\u00020=2\u0006\u0010X\u001a\u00020!2\u0006\u0010Y\u001a\u00020ZJ\u001c\u0010[\u001a\u00020D2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020^0]2\u0006\u0010_\u001a\u00020!J\u0014\u0010[\u001a\u00020D2\f\u0010`\u001a\b\u0012\u0004\u0012\u0002080]J\u0006\u0010a\u001a\u00020DJ2\u0010c\u001a\u00020D2\u0016\b\u0002\u0010d\u001a\u0010\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u001f\u0018\u00010e2\b\b\u0002\u0010f\u001a\u00020\u001f2\b\b\u0002\u0010g\u001a\u00020=J\u0010\u0010h\u001a\u00020D2\u0006\u0010i\u001a\u00020jH\u0002J\u0012\u0010k\u001a\u00020D2\b\b\u0002\u0010l\u001a\u00020\u001fH\u0002J\u0016\u0010m\u001a\u00020D2\u0006\u0010n\u001a\u00020;2\u0006\u0010o\u001a\u00020;J\u001e\u0010p\u001a\u00020D2\u0006\u0010*\u001a\u00020;2\u0006\u0010q\u001a\u00020;2\u0006\u0010r\u001a\u00020;J\u000e\u0010s\u001a\u00020D2\u0006\u0010t\u001a\u00020;J\u000e\u0010u\u001a\u00020D2\u0006\u0010v\u001a\u00020;J\u0006\u0010w\u001a\u00020DJ\u0006\u0010x\u001a\u00020DJ\u0006\u0010y\u001a\u00020DJ\u0012\u0010z\u001a\u00020D2\b\b\u0002\u0010{\u001a\u00020=H\u0002J\u0006\u0010|\u001a\u00020DJ(\u0010}\u001a\u00020D2\u0006\u0010~\u001a\u00020;2\u0006\u0010\u007f\u001a\u00020;2\u0007\u0010\u0080\u0001\u001a\u00020;2\u0007\u0010\u0081\u0001\u001a\u00020;J\u0007\u0010\u0082\u0001\u001a\u00020DJ\t\u0010\u0083\u0001\u001a\u00020!H\u0002J\u0010\u0010\u0084\u0001\u001a\u00020D2\u0007\u0010\u0085\u0001\u001a\u00020!J\u0007\u0010\u0086\u0001\u001a\u00020DJ\n\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u0001R\u0015\u0010\u0006\u001a\u00060\u0007R\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010+\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010)8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u000e\u00100\u001a\u000201X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020;X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020AX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0096\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "outlineDrawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;", "getOutlineDrawer", "()Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;", "anchorBoxDrawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$AnchorBoxDrawer;", "getAnchorBoxDrawer", "()Lxc/engine/core/graphics/engine/systems/SelectSystem$AnchorBoxDrawer;", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "imgSystem", "Lxc/engine/core/graphics/engine/systems/ImageSystem;", "textSystem", "Lxc/engine/core/graphics/engine/systems/TextSystem;", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "dashedLineRenderer", "Lxc/engine/core/graphics/engine/renderers/DashedLineRenderer;", "cropFrameRenderer", "Lxc/engine/core/graphics/engine/renderers/CropFrameRenderer;", "anchorBoxRenderer", "Lxc/engine/core/graphics/engine/renderers/AnchorBoxRenderer;", "curveSystem", "Lxc/engine/core/graphics/engine/systems/CurveSystem;", "bIsActivated", "", "selectedAreaInScreenSpace", "Landroid/graphics/RectF;", "selectedAreaInWorldSpace", "transformInfo", "Lxc/engine/core/graphics/engine/systems/SelectSystem$TransformInfo;", "screenRectWhenSelected", "updateContentLock", "Ljava/util/concurrent/locks/ReentrantLock;", "_renderTaskListener", "Lxc/engine/core/graphics/engine/systems/SelectSystem$RenderTaskListener;", "value", "renderTaskListener", "getRenderTaskListener", "()Lxc/engine/core/graphics/engine/systems/SelectSystem$RenderTaskListener;", "setRenderTaskListener", "(Lxc/engine/core/graphics/engine/systems/SelectSystem$RenderTaskListener;)V", "selectMode", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectMode;", "selectContent", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", "incomingSelectContent", "selectedShape", "Lxc/engine/data/pagedata/DataShape;", "selectedSpan", "Lxc/richtext/data/SpannedData;", "lastFrameSpanRect", "lastFrameSpanAngle", "", "selectTextureId", "", "selectTextureWidth", "selectTextureHeight", "finalTexture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "finalFramebufferId", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "onScreenResize", "isActivated", "getSelectMode", "getSelectedShape", "getShapeRenderer", "getOutTextureId", "getCurrentSelectMode", "drawSelect", "finalFb", "tmpFb", "tmpTexId", "worldArea", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "renderSelectedOverlay", "fbTarget", "areaWS", "exportSize", "Landroid/graphics/Point;", "selectOn", "curves", "", "Lxc/engine/data/pagedata/DataCurve;", "screenSpaceArea", "spannedDataList", "selected", "deselectTimes", "selectOff", "condition", "Lkotlin/Function1;", "isDeleteShape", "state", "setupSelect", "drawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContentDrawer;", "exitSelect", "bRefresh", "onMove", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "onScale", "centerX", "centerY", "onRotate", "angle", "onRotateDelta", "deltaAngle", "clearTransformInfo", "enterImageCropMode", "handelDoShapeEdit", "doShapeEdit", "editStatic", "exitImageCropMode", "setImageCropValue", "left", EditStat.ELEMENT_RIGHT, Layer.KEY_TOP, "bottom", "setEdit", "calculateWorldSpaceSelectArea", "renderLayer", "screenRect", "onShapeMoveEnd", "getSelectContentBitmap", "Landroid/graphics/Bitmap;", "Companion", "SelectMode", "SelectContent", "TransformInfo", "OutlineDrawer", "SelectContentDrawer", "AnchorBoxDrawer", "SelectOnTask", "SelectOffTask", "UpdateSelectTask", "ExportSelectContentBitmap", "ExportShapeBitmapTask", "RenderTaskListener", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SelectSystem extends BaseEngineStruct {
    private static final int SINGLE_CURVE_MAX_POINTS = 40960;
    private RenderTaskListener _renderTaskListener;
    private final AnchorBoxDrawer anchorBoxDrawer;
    private final AnchorBoxRenderer anchorBoxRenderer;
    private boolean bIsActivated;
    private final CropFrameRenderer cropFrameRenderer;
    private final CurveSystem curveSystem;
    private final DashedLineRenderer dashedLineRenderer;
    private int deselectTimes;
    private int finalFramebufferId;
    private ScreenTexture finalTexture;
    private final ImageRenderer imgRenderer;
    private final ImageSystem imgSystem;
    private SelectContent incomingSelectContent;
    private float lastFrameSpanAngle;
    private RectF lastFrameSpanRect;
    private final OutlineDrawer outlineDrawer;
    private RectF screenRectWhenSelected;
    private SelectContent selectContent;
    private SelectMode selectMode;
    private int selectTextureHeight;
    private int selectTextureId;
    private int selectTextureWidth;
    private RectF selectedAreaInScreenSpace;
    private RectF selectedAreaInWorldSpace;
    private DataShape selectedShape;
    private SpannedData selectedSpan;
    private final ShapeRenderer shapeRenderer;
    private final TextSystem textSystem;
    private final TransformInfo transformInfo;
    private final ReentrantLock updateContentLock;
    private static final String TAG = "SelectSystem";

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$RenderTaskListener;", "", "onRendering", "", "onRenderCompleted", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RenderTaskListener {
        void onRenderCompleted();

        void onRendering();
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectMode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", FamilyInfo.ROLE_NONE, "PureCurve", "SingleShape", "SingleSpan", "Multi", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum SelectMode {
        None,
        PureCurve,
        SingleShape,
        SingleSpan,
        Multi;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<SelectMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SelectMode.values().length];
            try {
                iArr[SelectMode.SingleShape.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.outlineDrawer = new OutlineDrawer(this, this);
        this.anchorBoxDrawer = new AnchorBoxDrawer(this);
        this.imgRenderer = getCurveEngine().getImgRenderer();
        this.imgSystem = getCurveEngine().getImgSystem();
        this.textSystem = getCurveEngine().getTextSystem();
        this.shapeRenderer = getCurveEngine().getShapeRenderer();
        this.dashedLineRenderer = getCurveEngine().getDashedLineRenderer();
        this.cropFrameRenderer = getCurveEngine().getCropFrameRenderer();
        this.anchorBoxRenderer = getCurveEngine().getAnchorBoxRenderer();
        this.curveSystem = getCurveEngine().getCurveSystem();
        this.selectedAreaInScreenSpace = new RectF();
        this.selectedAreaInWorldSpace = new RectF();
        this.transformInfo = new TransformInfo();
        this.screenRectWhenSelected = new RectF();
        this.updateContentLock = new ReentrantLock();
        this.selectMode = SelectMode.None;
        this.selectContent = new SelectContent(this);
        this.incomingSelectContent = new SelectContent(this);
        this.lastFrameSpanRect = new RectF();
        this.selectTextureId = -1;
        this.selectTextureWidth = 1;
        this.selectTextureHeight = 1;
        this.finalTexture = new ScreenTexture(0, 0, 0, 7, null);
        this.finalFramebufferId = -1;
    }

    public final OutlineDrawer getOutlineDrawer() {
        return this.outlineDrawer;
    }

    public final AnchorBoxDrawer getAnchorBoxDrawer() {
        return this.anchorBoxDrawer;
    }

    /* JADX INFO: renamed from: getRenderTaskListener, reason: from getter */
    public final RenderTaskListener get_renderTaskListener() {
        return this._renderTaskListener;
    }

    public final void setRenderTaskListener(RenderTaskListener renderTaskListener) {
        this._renderTaskListener = renderTaskListener;
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010F\u001a\u00020,2\u0006\u0010G\u001a\u00020\u0000J\u0006\u0010H\u001a\u00020IJ\u000e\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020LJ\b\u0010M\u001a\u00020NH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\f\"\u0004\b\u0012\u0010\u000eR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u001a\u0010%\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001c\"\u0004\b'\u0010\u001eR\u001a\u0010(\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001c\"\u0004\b*\u0010\u001eR\u001a\u0010+\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010.\"\u0004\b3\u00100R\u001a\u00104\u001a\u000205X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010.\"\u0004\b<\u00100R\u001a\u0010=\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010.\"\u0004\b?\u00100R\u001a\u0010@\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010.\"\u0004\bB\u00100R\u001a\u0010C\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010.\"\u0004\bE\u00100¨\u0006O"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", "", "selectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;)V", "getSelectSystem", "()Lxc/engine/core/graphics/engine/systems/SelectSystem;", "curves", "", "Lxc/engine/data/pagedata/DataCurve;", "getCurves", "()Ljava/util/List;", "setCurves", "(Ljava/util/List;)V", "spans", "Lxc/richtext/data/SpannedData;", "getSpans", "setSpans", "selectedAreaInScreenSpace", "Landroid/graphics/RectF;", "getSelectedAreaInScreenSpace", "()Landroid/graphics/RectF;", "setSelectedAreaInScreenSpace", "(Landroid/graphics/RectF;)V", "curveCount", "", "getCurveCount", "()I", "setCurveCount", "(I)V", "shapeCount", "getShapeCount", "setShapeCount", "spanCount", "getSpanCount", "setSpanCount", "imgCount", "getImgCount", "setImgCount", "textCount", "getTextCount", "setTextCount", "bHasMarkCurve", "", "getBHasMarkCurve", "()Z", "setBHasMarkCurve", "(Z)V", "bHasOtherCurve", "getBHasOtherCurve", "setBHasOtherCurve", "selectMode", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectMode;", "getSelectMode", "()Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectMode;", "setSelectMode", "(Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectMode;)V", "bNeedRedraw", "getBNeedRedraw", "setBNeedRedraw", "bUseCachedImage", "getBUseCachedImage", "setBUseCachedImage", "bHasCallOff", "getBHasCallOff", "setBHasCallOff", "bAnalysed", "getBAnalysed", "setBAnalysed", "sameCountWith", "content", "analyse", "", "applyTransformToRect", "transform", "Lxc/engine/core/graphics/engine/systems/SelectSystem$TransformInfo;", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SelectContent {
        private boolean bAnalysed;
        private boolean bHasCallOff;
        private boolean bHasMarkCurve;
        private boolean bHasOtherCurve;
        private boolean bNeedRedraw;
        private boolean bUseCachedImage;
        private int curveCount;
        private List<? extends DataCurve> curves;
        private int imgCount;
        private SelectMode selectMode;
        private final SelectSystem selectSystem;
        private RectF selectedAreaInScreenSpace;
        private int shapeCount;
        private int spanCount;
        private List<? extends SpannedData> spans;
        private int textCount;

        public SelectContent(SelectSystem selectSystem) {
            Intrinsics.checkNotNullParameter(selectSystem, "selectSystem");
            this.selectSystem = selectSystem;
            this.selectedAreaInScreenSpace = new RectF();
            this.selectMode = SelectMode.None;
        }

        public final SelectSystem getSelectSystem() {
            return this.selectSystem;
        }

        public final List<DataCurve> getCurves() {
            return this.curves;
        }

        public final void setCurves(List<? extends DataCurve> list) {
            this.curves = list;
        }

        public final List<SpannedData> getSpans() {
            return this.spans;
        }

        public final void setSpans(List<? extends SpannedData> list) {
            this.spans = list;
        }

        public final RectF getSelectedAreaInScreenSpace() {
            return this.selectedAreaInScreenSpace;
        }

        public final void setSelectedAreaInScreenSpace(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.selectedAreaInScreenSpace = rectF;
        }

        public final int getCurveCount() {
            return this.curveCount;
        }

        public final void setCurveCount(int i) {
            this.curveCount = i;
        }

        public final int getShapeCount() {
            return this.shapeCount;
        }

        public final void setShapeCount(int i) {
            this.shapeCount = i;
        }

        public final int getSpanCount() {
            return this.spanCount;
        }

        public final void setSpanCount(int i) {
            this.spanCount = i;
        }

        public final int getImgCount() {
            return this.imgCount;
        }

        public final void setImgCount(int i) {
            this.imgCount = i;
        }

        public final int getTextCount() {
            return this.textCount;
        }

        public final void setTextCount(int i) {
            this.textCount = i;
        }

        public final boolean getBHasMarkCurve() {
            return this.bHasMarkCurve;
        }

        public final void setBHasMarkCurve(boolean z) {
            this.bHasMarkCurve = z;
        }

        public final boolean getBHasOtherCurve() {
            return this.bHasOtherCurve;
        }

        public final void setBHasOtherCurve(boolean z) {
            this.bHasOtherCurve = z;
        }

        public final SelectMode getSelectMode() {
            return this.selectMode;
        }

        public final void setSelectMode(SelectMode selectMode) {
            Intrinsics.checkNotNullParameter(selectMode, "<set-?>");
            this.selectMode = selectMode;
        }

        public final boolean getBNeedRedraw() {
            return this.bNeedRedraw;
        }

        public final void setBNeedRedraw(boolean z) {
            this.bNeedRedraw = z;
        }

        public final boolean getBUseCachedImage() {
            return this.bUseCachedImage;
        }

        public final void setBUseCachedImage(boolean z) {
            this.bUseCachedImage = z;
        }

        public final boolean getBHasCallOff() {
            return this.bHasCallOff;
        }

        public final void setBHasCallOff(boolean z) {
            this.bHasCallOff = z;
        }

        public final boolean getBAnalysed() {
            return this.bAnalysed;
        }

        public final void setBAnalysed(boolean z) {
            this.bAnalysed = z;
        }

        public final boolean sameCountWith(SelectContent content) {
            Intrinsics.checkNotNullParameter(content, "content");
            return this.curveCount == content.curveCount && this.shapeCount == content.shapeCount && this.imgCount == content.imgCount && this.textCount == content.textCount;
        }

        public final void analyse() {
            List<? extends SpannedData> list;
            this.bAnalysed = false;
            this.bHasMarkCurve = false;
            this.bHasOtherCurve = false;
            this.imgCount = 0;
            this.textCount = 0;
            List<? extends SpannedData> list2 = this.spans;
            if (list2 == null || list2.isEmpty()) {
                this.spanCount = 0;
            } else {
                List<? extends SpannedData> list3 = this.spans;
                Intrinsics.checkNotNull(list3);
                this.spanCount = list3.size();
                List<? extends SpannedData> list4 = this.spans;
                Intrinsics.checkNotNull(list4);
                Iterator<T> it = list4.iterator();
                while (it.hasNext()) {
                    int type = ((SpannedData) it.next()).getType();
                    if (type == 7) {
                        this.imgCount++;
                    } else if (type == 11) {
                        this.textCount++;
                    }
                }
            }
            this.curveCount = 0;
            this.shapeCount = 0;
            List<? extends DataCurve> list5 = this.curves;
            if (list5 != null && !list5.isEmpty()) {
                List<? extends DataCurve> list6 = this.curves;
                Intrinsics.checkNotNull(list6);
                for (DataCurve dataCurve : list6) {
                    short type2 = dataCurve.getType();
                    if (type2 == 1) {
                        this.curveCount++;
                        if (dataCurve.mCurveProp.getPenType() == PenType.MARK) {
                            this.bHasMarkCurve = true;
                        } else {
                            this.bHasOtherCurve = true;
                        }
                    } else if (type2 == 28) {
                        this.shapeCount++;
                        this.bHasOtherCurve = true;
                    }
                }
            }
            int i = this.spanCount;
            if (i == 1 && this.curveCount == 0 && this.shapeCount == 0) {
                this.selectMode = SelectMode.SingleSpan;
            } else if (i > 1) {
                this.selectMode = SelectMode.Multi;
            } else {
                int i2 = this.shapeCount;
                if (i2 == 1 && this.curveCount == 0 && i == 0) {
                    this.selectMode = SelectMode.SingleShape;
                } else {
                    int i3 = this.curveCount;
                    if (i3 > 0 && i2 == 0 && i == 0) {
                        this.selectMode = SelectMode.PureCurve;
                    } else if ((i3 > 0 && i2 > 0) || ((i3 > 0 && i > 0) || (i2 > 0 && i > 0))) {
                        this.selectMode = SelectMode.Multi;
                    } else if (i3 == 0 && i2 > 1 && i == 0) {
                        this.selectMode = SelectMode.Multi;
                    }
                }
            }
            if (this.curveCount + this.shapeCount > 0) {
                this.bNeedRedraw = true;
            }
            if (this.selectMode == SelectMode.Multi || this.selectMode == SelectMode.PureCurve) {
                this.bUseCachedImage = true;
            }
            if (this.curveCount + this.shapeCount == 0) {
                this.selectedAreaInScreenSpace = new RectF();
            }
            if (this.spanCount > 0 && (list = this.spans) != null) {
                for (SpannedData spannedData : list) {
                    MiCurveEngine curveEngine = this.selectSystem.getCurveEngine();
                    float saveRatio = curveEngine.getSaveRatio();
                    RectF rectF = spannedData.getRectF();
                    RectF screenSpaceRectFromWorldSpaceRect = curveEngine.getScreenSpaceRectFromWorldSpaceRect(new RectF(rectF.left / saveRatio, rectF.top / saveRatio, rectF.right / saveRatio, rectF.bottom / saveRatio));
                    if (Math.abs(spannedData.getDegrees()) > 0.01f) {
                        float fCenterX = screenSpaceRectFromWorldSpaceRect.centerX();
                        float fCenterY = screenSpaceRectFromWorldSpaceRect.centerY();
                        float f = screenSpaceRectFromWorldSpaceRect.left - fCenterX;
                        float f2 = screenSpaceRectFromWorldSpaceRect.top - fCenterY;
                        float f3 = screenSpaceRectFromWorldSpaceRect.bottom - fCenterY;
                        float fSin = (float) Math.sin(MiMathUtils.INSTANCE.degreesToRadians(spannedData.getDegrees()));
                        float fCos = (float) Math.cos(MiMathUtils.INSTANCE.degreesToRadians(spannedData.getDegrees()));
                        float f4 = f * fCos;
                        float f5 = f4 - (f2 * fSin);
                        float f6 = f * fSin;
                        float f7 = (f2 * fCos) + f6;
                        float f8 = f4 - (fSin * f3);
                        float f9 = (f3 * fCos) + f6;
                        float fMax = Math.max(Math.abs(f5), Math.abs(f8));
                        float fMax2 = Math.max(Math.abs(f7), Math.abs(f9));
                        this.selectedAreaInScreenSpace.union(new RectF(fCenterX - fMax, fCenterY - fMax2, fCenterX + fMax, fCenterY + fMax2));
                    } else {
                        this.selectedAreaInScreenSpace.union(screenSpaceRectFromWorldSpaceRect);
                    }
                }
            }
            this.bAnalysed = true;
        }

        public final void applyTransformToRect(TransformInfo transform) {
            Intrinsics.checkNotNullParameter(transform, "transform");
            if (transform.hasTransform()) {
                Log.e(SelectSystem.TAG, "xl-- [select] applyTransformToRect " + transform);
                this.selectedAreaInScreenSpace.offset(transform.getOffsetX(), transform.getOffsetY());
                this.selectedAreaInScreenSpace = MiMathUtils.INSTANCE.getScaledRectF(this.selectedAreaInScreenSpace, transform.getScale());
                if (transform.getAngle() == 0.0f) {
                    return;
                }
                this.selectedAreaInScreenSpace = MiMathUtils.INSTANCE.getRotatedBoundingBox(this.selectedAreaInScreenSpace, transform.getAngle(), this.selectedAreaInScreenSpace.centerX(), this.selectedAreaInScreenSpace.centerY());
            }
        }

        public String toString() {
            return "[SelectContent] curveCount: " + this.curveCount + ", shapeCount: " + this.shapeCount + ", spanCount: " + this.spanCount + ", imgCount: " + this.imgCount + ", textCount: " + this.textCount;
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.finalTexture = acquireScreenTexture();
        this.finalFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.finalTexture.getTexId());
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteFramebuffer(this.finalFramebufferId);
        getCurveEngine().getTexturePool().recycleScreenTexture(this.finalTexture);
        this.finalTexture = new ScreenTexture(0, 0, 0, 7, null);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        getCurveEngine().getTexturePool().recycleScreenTexture(this.finalTexture);
        this.finalTexture = new ScreenTexture(0, 0, 0, 7, null);
    }

    /* JADX INFO: renamed from: isActivated, reason: from getter */
    public final boolean getBIsActivated() {
        return this.bIsActivated;
    }

    public final SelectMode getSelectMode() {
        return this.selectMode;
    }

    public final DataShape getSelectedShape() {
        return this.selectedShape;
    }

    public final ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public final int getOutTextureId() {
        return this.finalTexture.getTexId();
    }

    /* JADX INFO: renamed from: getCurrentSelectMode, reason: from getter */
    public final SelectMode getSelectMode() {
        return this.selectMode;
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0000J\u0006\u0010.\u001a\u00020,J\u0006\u0010/\u001a\u00020&J\b\u00100\u001a\u000201H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*¨\u00062"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$TransformInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "offsetX", "", "getOffsetX", "()F", "setOffsetX", "(F)V", "offsetY", "getOffsetY", "setOffsetY", "scale", "getScale", "setScale", "centerX", "getCenterX", "setCenterX", "centerY", "getCenterY", "setCenterY", "angle", "getAngle", "setAngle", "centerXToShader", "getCenterXToShader", "setCenterXToShader", "centerYToShader", "getCenterYToShader", "setCenterYToShader", "cropRect", "Landroid/graphics/RectF;", "getCropRect", "()Landroid/graphics/RectF;", "setCropRect", "(Landroid/graphics/RectF;)V", "bIsInCropMode", "", "getBIsInCropMode", "()Z", "setBIsInCropMode", "(Z)V", "copyFrom", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "reset", "hasTransform", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TransformInfo {
        private float angle;
        private boolean bIsInCropMode;
        private float centerX;
        private float centerXToShader;
        private float centerY;
        private float centerYToShader;
        private float offsetX;
        private float offsetY;
        private float scale = 1.0f;
        private RectF cropRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);

        public final float getOffsetX() {
            return this.offsetX;
        }

        public final void setOffsetX(float f) {
            this.offsetX = f;
        }

        public final float getOffsetY() {
            return this.offsetY;
        }

        public final void setOffsetY(float f) {
            this.offsetY = f;
        }

        public final float getScale() {
            return this.scale;
        }

        public final void setScale(float f) {
            this.scale = f;
        }

        public final float getCenterX() {
            return this.centerX;
        }

        public final void setCenterX(float f) {
            this.centerX = f;
        }

        public final float getCenterY() {
            return this.centerY;
        }

        public final void setCenterY(float f) {
            this.centerY = f;
        }

        public final float getAngle() {
            return this.angle;
        }

        public final void setAngle(float f) {
            this.angle = f;
        }

        public final float getCenterXToShader() {
            return this.centerXToShader;
        }

        public final void setCenterXToShader(float f) {
            this.centerXToShader = f;
        }

        public final float getCenterYToShader() {
            return this.centerYToShader;
        }

        public final void setCenterYToShader(float f) {
            this.centerYToShader = f;
        }

        public final RectF getCropRect() {
            return this.cropRect;
        }

        public final void setCropRect(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.cropRect = rectF;
        }

        public final boolean getBIsInCropMode() {
            return this.bIsInCropMode;
        }

        public final void setBIsInCropMode(boolean z) {
            this.bIsInCropMode = z;
        }

        public final void copyFrom(TransformInfo other) {
            Intrinsics.checkNotNullParameter(other, "other");
            this.offsetX = other.offsetX;
            this.offsetY = other.offsetY;
            this.scale = other.scale;
            this.centerX = other.centerX;
            this.centerY = other.centerY;
            this.angle = other.angle;
            this.centerXToShader = other.centerXToShader;
            this.centerYToShader = other.centerYToShader;
            this.cropRect = new RectF(other.cropRect);
            this.bIsInCropMode = other.bIsInCropMode;
        }

        public final void reset() {
            this.offsetX = 0.0f;
            this.offsetY = 0.0f;
            this.scale = 1.0f;
            this.centerX = 0.0f;
            this.centerY = 0.0f;
            this.angle = 0.0f;
            this.centerXToShader = 0.0f;
            this.centerYToShader = 0.0f;
            this.cropRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
            this.bIsInCropMode = false;
        }

        public final boolean hasTransform() {
            return (this.offsetX == 0.0f && this.offsetY == 0.0f && this.scale == 1.0f && this.angle == 0.0f) ? false : true;
        }

        public String toString() {
            return "[TransformInfo] offset: " + this.offsetX + StringUtils.SPACE + this.offsetY + ", scale: " + this.scale + ", angle: " + this.angle;
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001:\u0001 B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J&\u0010\u000b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u0015J\u0006\u0010\u001b\u001a\u00020\u0016J\u0006\u0010\u001c\u001a\u00020\u0016J\u0006\u0010\u001d\u001a\u00020\u0016J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0015R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;", "", "inSelectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Lxc/engine/core/graphics/engine/systems/SelectSystem;)V", "selectSystem", "path", "Landroid/graphics/Path;", "getPath", "()Landroid/graphics/Path;", "setPath", "(Landroid/graphics/Path;)V", "pathOffsetX", "", "pathOffsetY", "vertexBuffer", "Ljava/nio/FloatBuffer;", "vertexCount", "", "bIsShowAntLine", "", "", "inPath", "offX", "offY", "showAntLine", "clearPath", "clear", "draw", "setIsDrawAntLine", "isDraw", "UploadPathTask", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class OutlineDrawer {
        private boolean bIsShowAntLine;
        private Path path;
        private float pathOffsetX;
        private float pathOffsetY;
        private final SelectSystem selectSystem;
        final /* synthetic */ SelectSystem this$0;
        private final FloatBuffer vertexBuffer;
        private int vertexCount;

        public OutlineDrawer(SelectSystem selectSystem, SelectSystem inSelectSystem) {
            Intrinsics.checkNotNullParameter(inSelectSystem, "inSelectSystem");
            this.this$0 = selectSystem;
            this.selectSystem = inSelectSystem;
            this.path = new Path();
            FloatBuffer floatBufferCreateFloatBuffer = GLRenderUtil.createFloatBuffer(81920);
            Intrinsics.checkNotNullExpressionValue(floatBufferCreateFloatBuffer, "createFloatBuffer(...)");
            this.vertexBuffer = floatBufferCreateFloatBuffer;
        }

        public final Path getPath() {
            return this.path;
        }

        public final void setPath(Path path) {
            Intrinsics.checkNotNullParameter(path, "<set-?>");
            this.path = path;
        }

        /* JADX INFO: compiled from: SelectSystem.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0007\u001a\u00060\u0003R\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer$UploadPathTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inOutlineDrawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;Lxc/engine/core/graphics/engine/systems/SelectSystem$OutlineDrawer;)V", "drawer", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public final class UploadPathTask extends BaseAsyncGLTask {
            private final OutlineDrawer drawer;
            final /* synthetic */ OutlineDrawer this$0;

            public UploadPathTask(OutlineDrawer outlineDrawer, OutlineDrawer inOutlineDrawer) {
                Intrinsics.checkNotNullParameter(inOutlineDrawer, "inOutlineDrawer");
                this.this$0 = outlineDrawer;
                this.drawer = inOutlineDrawer;
            }

            @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
            public boolean execute(TileRenderResource res) {
                Intrinsics.checkNotNullParameter(res, "res");
                PathMeasure pathMeasure = new PathMeasure(this.drawer.getPath(), true);
                float[] fArr = new float[2];
                this.drawer.vertexCount = 0;
                float length = pathMeasure.getLength();
                float f = 0.0f;
                while (f < length && this.drawer.vertexCount < SelectSystem.SINGLE_CURVE_MAX_POINTS) {
                    pathMeasure.getPosTan(f, fArr, null);
                    this.drawer.vertexBuffer.put(this.drawer.vertexCount * 2, fArr[0]);
                    this.drawer.vertexBuffer.put((this.drawer.vertexCount * 2) + 1, fArr[1]);
                    f += 1.0f;
                    this.drawer.vertexCount++;
                }
                return true;
            }
        }

        public final void setPath(Path inPath, float offX, float offY, boolean showAntLine) {
            Intrinsics.checkNotNullParameter(inPath, "inPath");
            this.bIsShowAntLine = showAntLine;
            if (!Intrinsics.areEqual(inPath, this.path)) {
                this.path.set(inPath);
                this.selectSystem.getCurveEngine().getTaskManager().enqueueTask(new UploadPathTask(this, this));
            } else {
                this.pathOffsetX += offX;
                this.pathOffsetY += offY;
            }
        }

        public final void clearPath() {
            this.path.reset();
        }

        public final void clear() {
            this.vertexCount = 0;
            this.pathOffsetX = 0.0f;
            this.pathOffsetY = 0.0f;
        }

        public final void draw() {
            int i = this.vertexCount;
            if (i <= 0 || i > SelectSystem.SINGLE_CURVE_MAX_POINTS || !this.bIsShowAntLine) {
                return;
            }
            this.selectSystem.dashedLineRenderer.render(this.vertexBuffer, this.vertexCount, this.pathOffsetX, this.pathOffsetY);
        }

        public final void setIsDrawAntLine(boolean isDraw) {
            this.bIsShowAntLine = isDraw;
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContentDrawer;", "", "selectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "selectContent", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;)V", "getSelectSystem", "()Lxc/engine/core/graphics/engine/systems/SelectSystem;", "getSelectContent", "()Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", "selectTextureId", "", "getSelectTextureId", "()I", "setSelectTextureId", "(I)V", "selectFramebufferId", "tmpTextureId", "tmpFramebufferId", "drawContent", "", "destroy", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SelectContentDrawer {
        private final SelectContent selectContent;
        private int selectFramebufferId;
        private final SelectSystem selectSystem;
        private int selectTextureId;
        private int tmpFramebufferId;
        private int tmpTextureId;

        public SelectContentDrawer(SelectSystem selectSystem, SelectContent selectContent) {
            Intrinsics.checkNotNullParameter(selectSystem, "selectSystem");
            Intrinsics.checkNotNullParameter(selectContent, "selectContent");
            this.selectSystem = selectSystem;
            this.selectContent = selectContent;
        }

        public final SelectContent getSelectContent() {
            return this.selectContent;
        }

        public final SelectSystem getSelectSystem() {
            return this.selectSystem;
        }

        public final int getSelectTextureId() {
            return this.selectTextureId;
        }

        public final void setSelectTextureId(int i) {
            this.selectTextureId = i;
        }

        public final void drawContent() throws Throwable {
            RectF worldSpaceRectFromScreenSpaceRect = this.selectSystem.getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(this.selectContent.getSelectedAreaInScreenSpace());
            Point limitedTextureSize$default = GLUtils.Companion.getLimitedTextureSize$default(GLUtils.INSTANCE, this.selectContent.getSelectedAreaInScreenSpace().width(), this.selectContent.getSelectedAreaInScreenSpace().height(), 0.0f, 4, null);
            int i = limitedTextureSize$default.x;
            int i2 = limitedTextureSize$default.y;
            this.selectSystem.selectTextureWidth = i;
            this.selectSystem.selectTextureHeight = i2;
            if (this.selectTextureId > 0) {
                GLUtils.INSTANCE.deleteTexture(this.selectTextureId);
            }
            if (this.tmpTextureId > 0) {
                GLUtils.INSTANCE.deleteTexture(this.tmpTextureId);
            }
            if (this.selectFramebufferId > 0) {
                GLUtils.INSTANCE.deleteFramebuffer(this.selectFramebufferId);
            }
            if (this.tmpFramebufferId > 0) {
                GLUtils.INSTANCE.deleteFramebuffer(this.tmpFramebufferId);
            }
            this.selectTextureId = GLUtils.INSTANCE.createTexture(i, i2);
            this.tmpTextureId = GLUtils.INSTANCE.createTexture(i, i2);
            this.selectFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.selectTextureId);
            int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(this.tmpTextureId);
            this.tmpFramebufferId = iCreateFramebufferFromTexture;
            this.selectSystem.drawSelect(this.selectFramebufferId, iCreateFramebufferFromTexture, this.tmpTextureId, worldSpaceRectFromScreenSpaceRect, i, i2);
        }

        public final void destroy() {
            GLUtils.INSTANCE.deleteFramebuffer(this.selectFramebufferId);
            GLUtils.INSTANCE.deleteFramebuffer(this.tmpFramebufferId);
            GLUtils.INSTANCE.deleteTexture(this.tmpTextureId);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void drawSelect(int finalFb, int tmpFb, int tmpTexId, RectF worldArea, int w, int h) throws Throwable {
        float f;
        char c;
        short s;
        char c2;
        char c3;
        short s2;
        char c4;
        RectF worldArea2 = worldArea;
        Intrinsics.checkNotNullParameter(worldArea2, "worldArea");
        char c5 = 0;
        GLES30.glViewport(0, 0, w, h);
        int i = finalFb;
        GLES30.glBindFramebuffer(36160, i);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        short s3 = 1;
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        List<SpannedData> spans = this.selectContent.getSpans();
        char c6 = 7;
        if (spans != null) {
            for (SpannedData spannedData : spans) {
                int cachedSpanTextureId = spannedData.getType() == 7 ? this.imgSystem.getCachedSpanTextureId(spannedData.getId()) : 0;
                if (cachedSpanTextureId > 0) {
                    MiCurveEngine curveEngine = getCurveEngine();
                    RectF rectF = spannedData.getRectF();
                    Intrinsics.checkNotNullExpressionValue(rectF, "getRectF(...)");
                    RectF rectWSFromSaveRect = curveEngine.getRectWSFromSaveRect(rectF);
                    RectF rectF2 = new RectF(worldArea2.left, worldArea2.bottom, worldArea2.right, worldArea2.top);
                    if (spannedData.getType() == 7) {
                        ImageRenderer imageRenderer = this.imgRenderer;
                        float degrees = spannedData.getDegrees();
                        RectF mCropRectF = spannedData.mCropRectF;
                        Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
                        imageRenderer.renderCrop(rectF2, cachedSpanTextureId, rectWSFromSaveRect, degrees, false, 0.0f, 0.0f, mCropRectF, 1);
                    } else {
                        ImageRenderer.render$default(this.imgRenderer, rectF2, cachedSpanTextureId, rectWSFromSaveRect, spannedData.getDegrees(), false, 0.0f, 0.0f, 112, null);
                    }
                }
            }
        }
        List<SpannedData> spans2 = this.selectContent.getSpans();
        if (spans2 != null) {
            for (SpannedData spannedData2 : spans2) {
                int cachedTextTextureId = spannedData2.getType() == 11 ? this.textSystem.getCachedTextTextureId(spannedData2) : 0;
                if (cachedTextTextureId > 0) {
                    MiCurveEngine curveEngine2 = getCurveEngine();
                    RectF rectF3 = spannedData2.getRectF();
                    Intrinsics.checkNotNullExpressionValue(rectF3, "getRectF(...)");
                    ImageRenderer.render$default(this.imgRenderer, new RectF(worldArea2.left, worldArea2.bottom, worldArea2.right, worldArea2.top), cachedTextTextureId, curveEngine2.getRectWSFromSaveRect(rectF3), spannedData2.getDegrees(), false, 0.0f, 0.0f, 112, null);
                }
            }
        }
        float fWidth = w / worldArea2.width();
        ArrayList arrayList = new ArrayList();
        List<DataCurve> curves = this.selectContent.getCurves();
        if (curves != null) {
            for (DataCurve dataCurve : curves) {
                if (dataCurve.getType() == 1 && dataCurve.mCurveProp.getPenType() == PenType.MARK) {
                    arrayList.add(dataCurve);
                }
            }
        }
        List<DataCurve> curves2 = this.selectContent.getCurves();
        if (curves2 != null) {
            for (DataCurve dataCurve2 : curves2) {
                if (dataCurve2.getType() != 1 || dataCurve2.mCurveProp.getPenType() != PenType.MARK) {
                    arrayList.add(dataCurve2);
                }
            }
        }
        Iterator<T> it = TileSystem.INSTANCE.combineCurves(arrayList).iterator();
        while (it.hasNext()) {
            List<DataCurve> list = (List) it.next();
            short type = ((DataCurve) CollectionsKt.first(list)).getType();
            if (type == s3) {
                long jNativeGenCurveVertexBuffer = 0;
                for (DataCurve dataCurve3 : list) {
                    if (dataCurve3.size(dataCurve3.mOriginPoints) == 0) {
                        getCurveEngine().getPenEngine().mCurveDataManager.executeCurveGenTask(dataCurve3);
                    }
                    Object mLockObj = dataCurve3.mLockObj;
                    Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                    synchronized (mLockObj) {
                        float value = dataCurve3.getCurveProp().getPenType().getValue();
                        float penSize = dataCurve3.getCurveProp().getPenSize();
                        float drawRatio = dataCurve3.getDrawRatio();
                        float f2 = worldArea2.left;
                        float f3 = worldArea2.top;
                        c3 = c5;
                        float f4 = worldArea2.right;
                        s2 = s3;
                        float f5 = worldArea2.bottom;
                        float sensitivity = dataCurve3.getSensitivity();
                        float tipSize = dataCurve3.getTipSize();
                        c4 = c6;
                        float[] fArr = new float[10];
                        fArr[c3] = value;
                        fArr[s2] = penSize;
                        fArr[2] = drawRatio;
                        fArr[3] = fWidth;
                        fArr[4] = f2;
                        fArr[5] = f3;
                        fArr[6] = f4;
                        fArr[c4] = f5;
                        fArr[8] = sensitivity;
                        fArr[9] = tipSize;
                        jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve3.mOriginPoints, fArr, jNativeGenCurveVertexBuffer, jNativeGenCurveVertexBuffer != 0 ? s2 : c3);
                        Unit unit = Unit.INSTANCE;
                    }
                    c5 = c3;
                    s3 = s2;
                    c6 = c4;
                }
                c = c5;
                s = s3;
                c2 = c6;
                CurveRenderer.renderCurve$default(getCurveEngine().getCurveRenderer(), (DataCurve) CollectionsKt.first(list), worldArea2, i, tmpFb, tmpTexId, fWidth, jNativeGenCurveVertexBuffer, false, 128, null);
                f = fWidth;
            } else {
                f = fWidth;
                c = c5;
                s = s3;
                c2 = c6;
                if (type == 28) {
                    ShapeRenderer shapeRenderer = this.shapeRenderer;
                    Object objFirst = CollectionsKt.first((List<? extends Object>) list);
                    Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type xc.engine.data.pagedata.DataShape");
                    ShapeRenderer.render$default(shapeRenderer, (DataShape) objFirst, finalFb, worldArea, getCurveEngine().getDrawRatio(), 0.0f, 0.0f, 48, null);
                }
            }
            i = finalFb;
            worldArea2 = worldArea;
            fWidth = f;
            c5 = c;
            s3 = s;
            c6 = c2;
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0017\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J,\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002000/2\u0006\u00101\u001a\u00020\b2\u0006\u00102\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u00103\u001a\u000204H\u0002J\u001e\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\b2\u0006\u00107\u001a\u00020&2\u0006\u00108\u001a\u00020&J\u0006\u00109\u001a\u000204J\u000e\u0010:\u001a\u0002042\u0006\u0010;\u001a\u00020&J\u001a\u0010<\u001a\u0002042\b\b\u0002\u0010=\u001a\u00020\u00162\b\b\u0002\u0010>\u001a\u00020\u0016R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0012\"\u0004\b\u001b\u0010\u0014R\u000e\u0010\u001c\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)R\u001a\u0010,\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010'\"\u0004\b-\u0010)¨\u0006?"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$AnchorBoxDrawer;", "", "inSelectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;)V", "selectSystem", "editPointArray", "", "vertexBuffer", "Ljava/nio/FloatBuffer;", "indexBuffer", "Ljava/nio/ShortBuffer;", "_editStatic", "", "value", "editStatic", "getEditStatic", "()I", "setEditStatic", "(I)V", "editPointRadius", "", "getEditPointRadius", "()F", "editPointsCount", "getEditPointsCount", "setEditPointsCount", "pathOffsetX", "pathOffsetY", "vertexCount", "indexCount", "editPointRotateM", "getEditPointRotateM", "()[F", "setEditPointRotateM", "([F)V", "isShowEditPoint", "", "()Z", "setShowEditPoint", "(Z)V", "isClose", "setClose", "isDrawAnchorBox", "setDrawAnchorBox", "createEditFrameWithLocalCoordsIndexed", "Lkotlin/Pair;", "", "corners", "lineWidth", "updateAnchorVertexData", "", "setEditPoints", "points", "showEditPoint", "closeShape", "clear", "enableAnchorBoxDraw", "isDraw", "draw", "posOffsetX", "posOffsetY", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnchorBoxDrawer {
        private int _editStatic;
        private final float[] editPointArray;
        private final float editPointRadius;
        private float[] editPointRotateM;
        private int editPointsCount;
        private final ShortBuffer indexBuffer;
        private int indexCount;
        private boolean isClose;
        private boolean isDrawAnchorBox;
        private boolean isShowEditPoint;
        private float pathOffsetX;
        private float pathOffsetY;
        private final SelectSystem selectSystem;
        private final FloatBuffer vertexBuffer;
        private int vertexCount;

        public AnchorBoxDrawer(SelectSystem inSelectSystem) {
            Intrinsics.checkNotNullParameter(inSelectSystem, "inSelectSystem");
            this.selectSystem = inSelectSystem;
            this.editPointArray = new float[14];
            FloatBuffer floatBufferCreateFloatBuffer = GLRenderUtil.createFloatBuffer(81920);
            Intrinsics.checkNotNullExpressionValue(floatBufferCreateFloatBuffer, "createFloatBuffer(...)");
            this.vertexBuffer = floatBufferCreateFloatBuffer;
            ShortBuffer shortBufferCreateShortBuffer = GLRenderUtil.createShortBuffer(SelectSystem.SINGLE_CURVE_MAX_POINTS);
            Intrinsics.checkNotNullExpressionValue(shortBufferCreateShortBuffer, "createShortBuffer(...)");
            this.indexBuffer = shortBufferCreateShortBuffer;
            this.editPointRadius = inSelectSystem.getCurveEngine().getPenEngine().mDensity * 8.0f;
            this.editPointRotateM = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
            this.isDrawAnchorBox = true;
        }

        /* JADX INFO: renamed from: getEditStatic, reason: from getter */
        public final int get_editStatic() {
            return this._editStatic;
        }

        public final void setEditStatic(int i) {
            this._editStatic = i;
        }

        public final float getEditPointRadius() {
            return this.editPointRadius;
        }

        public final int getEditPointsCount() {
            return this.editPointsCount;
        }

        public final void setEditPointsCount(int i) {
            this.editPointsCount = i;
        }

        public final float[] getEditPointRotateM() {
            return this.editPointRotateM;
        }

        public final void setEditPointRotateM(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.editPointRotateM = fArr;
        }

        /* JADX INFO: renamed from: isShowEditPoint, reason: from getter */
        public final boolean getIsShowEditPoint() {
            return this.isShowEditPoint;
        }

        public final void setShowEditPoint(boolean z) {
            this.isShowEditPoint = z;
        }

        /* JADX INFO: renamed from: isClose, reason: from getter */
        public final boolean getIsClose() {
            return this.isClose;
        }

        public final void setClose(boolean z) {
            this.isClose = z;
        }

        /* JADX INFO: renamed from: isDrawAnchorBox, reason: from getter */
        public final boolean getIsDrawAnchorBox() {
            return this.isDrawAnchorBox;
        }

        public final void setDrawAnchorBox(boolean z) {
            this.isDrawAnchorBox = z;
        }

        private final Pair<float[], short[]> createEditFrameWithLocalCoordsIndexed(float[] corners, float lineWidth, int editPointsCount) {
            float f = lineWidth / 2.0f;
            PointF[] pointFArr = new PointF[2];
            for (int i = 0; i < 2; i++) {
                int i2 = (1 - i) * 2;
                int i3 = (2 - i) * 2;
                float f2 = corners[i2] - corners[i3];
                float f3 = corners[i2 + 1] - corners[i3 + 1];
                float fSqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3));
                pointFArr[i] = new PointF((f2 * f) / fSqrt, (f3 * f) / fSqrt);
            }
            float f4 = pointFArr[0].x / f;
            float f5 = (-pointFArr[0].y) / f;
            this.editPointRotateM = new float[]{f5, f4, -f4, f5};
            return new Pair<>(new float[]{corners[0] + pointFArr[0].x + pointFArr[1].x, corners[1] + pointFArr[0].y + pointFArr[1].y, -1.0f, -1.0f, (corners[2] + pointFArr[0].x) - pointFArr[1].x, (corners[3] + pointFArr[0].y) - pointFArr[1].y, 1.0f, -1.0f, (corners[4] - pointFArr[0].x) - pointFArr[1].x, (corners[5] - pointFArr[0].y) - pointFArr[1].y, -1.0f, -1.0f, (corners[6] - pointFArr[0].x) + pointFArr[1].x, (corners[7] - pointFArr[0].y) + pointFArr[1].y, 1.0f, -1.0f, (corners[0] - pointFArr[0].x) - pointFArr[1].x, (corners[1] - pointFArr[0].y) - pointFArr[1].y, -1.0f, 1.0f, (corners[2] - pointFArr[0].x) + pointFArr[1].x, (corners[3] - pointFArr[0].y) + pointFArr[1].y, 1.0f, 1.0f, corners[4] + pointFArr[0].x + pointFArr[1].x, corners[5] + pointFArr[0].y + pointFArr[1].y, -1.0f, 1.0f, (corners[6] + pointFArr[0].x) - pointFArr[1].x, (corners[7] + pointFArr[0].y) - pointFArr[1].y, 1.0f, 1.0f}, new short[]{0, 4, 1, 5, 2, 6, 3, 7, 0, 4});
        }

        private final synchronized void updateAnchorVertexData() {
            PointF pointF;
            if (this.isClose) {
                Pair<float[], short[]> pairCreateEditFrameWithLocalCoordsIndexed = createEditFrameWithLocalCoordsIndexed(this.editPointArray, 3.0f, this.editPointsCount);
                float[] fArrComponent1 = pairCreateEditFrameWithLocalCoordsIndexed.component1();
                short[] sArrComponent2 = pairCreateEditFrameWithLocalCoordsIndexed.component2();
                FloatBuffer floatBuffer = this.vertexBuffer;
                floatBuffer.position(0);
                floatBuffer.put(fArrComponent1);
                ShortBuffer shortBuffer = this.indexBuffer;
                shortBuffer.position(0);
                shortBuffer.put(sArrComponent2);
                shortBuffer.position(0);
                this.vertexCount = fArrComponent1.length / 4;
                this.indexCount = sArrComponent2.length;
            } else {
                this.editPointRotateM = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
                this.vertexBuffer.position(0);
                this.indexBuffer.position(0);
                this.vertexCount = 0;
                this.indexCount = 0;
            }
            PointF[] pointFArr = new PointF[2];
            for (int i = 0; i < 2; i++) {
                float[] fArr = this.editPointArray;
                int i2 = (2 - i) * 2;
                int i3 = (1 - i) * 2;
                float f = fArr[i2] - fArr[i3];
                float f2 = fArr[i2 + 1] - fArr[i3 + 1];
                float f3 = (f * f) + (f2 * f2);
                float fSqrt = f3 <= 0.0f ? 0.0f : (float) Math.sqrt(f3);
                if (fSqrt <= 0.0f) {
                    Log.i(SelectSystem.TAG, "execute: normalAB[" + i + "] is zero point");
                    pointF = new PointF(0.0f, 0.0f);
                } else {
                    float f4 = this.editPointRadius;
                    pointF = new PointF((f * f4) / fSqrt, (f2 * f4) / fSqrt);
                }
                pointFArr[i] = pointF;
            }
            PointF pointF2 = new PointF(pointFArr[0].x + pointFArr[1].x, pointFArr[0].y + pointFArr[1].y);
            float[] fArr2 = this.editPointArray;
            float f5 = fArr2[0];
            float f6 = fArr2[2];
            float f7 = fArr2[1];
            float f8 = fArr2[3];
            float f9 = fArr2[4];
            float f10 = fArr2[5];
            float f11 = fArr2[6];
            float f12 = fArr2[7];
            float f13 = fArr2[0];
            float[] fArr3 = this.editPointArray;
            float[] fArr4 = {(f5 + f6) * 0.5f, (f7 + f8) * 0.5f, (f6 + f9) * 0.5f, (f8 + f10) * 0.5f, (f9 + f11) * 0.5f, (f10 + f12) * 0.5f, (f11 + f13) * 0.5f, (f12 + fArr2[1]) * 0.5f, f13 - pointF2.x, this.editPointArray[1] - pointF2.y, (this.editPointArray[2] - pointFArr[0].x) + pointFArr[1].x, (this.editPointArray[3] - pointFArr[0].y) + pointFArr[1].y, this.editPointArray[4] + pointF2.x, this.editPointArray[5] + pointF2.y, (this.editPointArray[6] + pointFArr[0].x) - pointFArr[1].x, (this.editPointArray[7] + pointFArr[0].y) - pointFArr[1].y, fArr3[8], fArr3[9]};
            if (!this.isClose) {
                fArr4[2] = fArr4[2] + pointFArr[1].x;
                fArr4[3] = fArr4[3] + pointFArr[1].y;
                fArr4[6] = fArr4[6] - pointFArr[1].x;
                fArr4[7] = fArr4[7] - pointFArr[1].y;
            }
            FloatBuffer floatBuffer2 = this.vertexBuffer;
            floatBuffer2.put(fArr4);
            floatBuffer2.position(0);
        }

        public final void setEditPoints(float[] points, boolean showEditPoint, boolean closeShape) {
            Intrinsics.checkNotNullParameter(points, "points");
            this.editPointsCount = points.length / 2;
            this.isShowEditPoint = showEditPoint;
            this.isDrawAnchorBox = true;
            this.isClose = closeShape;
            ArraysKt.copyInto$default(points, this.editPointArray, 0, 0, 0, 14, (Object) null);
            this.selectSystem.anchorBoxRenderer.setLineColor(0.05098f, 0.51765f, 1.0f);
            this.selectSystem.anchorBoxRenderer.resetOffsetInfo();
            updateAnchorVertexData();
        }

        public final void clear() {
            this.editPointsCount = 0;
            this.pathOffsetX = 0.0f;
            this.pathOffsetY = 0.0f;
            this.indexCount = 0;
            this.vertexCount = 0;
        }

        public final void enableAnchorBoxDraw(boolean isDraw) {
            this.isDrawAnchorBox = isDraw;
        }

        public static /* synthetic */ void draw$default(AnchorBoxDrawer anchorBoxDrawer, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                f = 0.0f;
            }
            if ((i & 2) != 0) {
                f2 = 0.0f;
            }
            anchorBoxDrawer.draw(f, f2);
        }

        public final synchronized void draw(float posOffsetX, float posOffsetY) {
            if (this.isDrawAnchorBox) {
                this.selectSystem.anchorBoxRenderer.render(this.vertexBuffer, this.vertexCount, this.indexBuffer, this.indexCount, this.isShowEditPoint, this.isClose, posOffsetX, posOffsetY, this.editPointRadius * 2.0f);
            }
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectOnTask;", "Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncUpdateAreaTask;", "selectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "tileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "selContent", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", "bDrawContent", "", "bSetup", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Lxc/engine/core/graphics/engine/systems/TileSystem;Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;ZZ)V", "selectContentDrawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContentDrawer;", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SelectOnTask extends TileSystem.AsyncUpdateAreaTask {
        private final boolean bDrawContent;
        private final boolean bSetup;
        private final SelectContent selContent;
        private final SelectContentDrawer selectContentDrawer;
        private final SelectSystem selectSystem;
        private final TileSystem tileSystem;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SelectOnTask(SelectSystem selectSystem, TileSystem tileSystem, SelectContent selContent, boolean z, boolean z2) {
            super(tileSystem, selectSystem.getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(selContent.getSelectedAreaInScreenSpace()), selContent.getSelectedAreaInScreenSpace(), selectSystem.getCurveEngine().getScreenAreaWS(), tileSystem.shouldShowFullscreenTile(), true, false, 64, null);
            Intrinsics.checkNotNullParameter(selectSystem, "selectSystem");
            Intrinsics.checkNotNullParameter(tileSystem, "tileSystem");
            Intrinsics.checkNotNullParameter(selContent, "selContent");
            this.selectSystem = selectSystem;
            this.tileSystem = tileSystem;
            this.selContent = selContent;
            this.bDrawContent = z;
            this.bSetup = z2;
            this.selectContentDrawer = new SelectContentDrawer(selectSystem, selContent);
            setBNeedRefreshRender(z2);
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.AsyncUpdateAreaTask, xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            Intrinsics.checkNotNullParameter(res, "res");
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] on execute");
            if (this.selContent.getBUseCachedImage() && this.bDrawContent) {
                this.selectContentDrawer.drawContent();
            }
            super.execute(res);
            if (this.bDrawContent) {
                this.selectContentDrawer.destroy();
            }
            if (!this.bSetup) {
                return true;
            }
            this.selectSystem.setupSelect(this.selectContentDrawer);
            return true;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.AsyncUpdateAreaTask, xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] on render completed");
            super.onRenderCompleted();
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectOffTask;", "Lxc/engine/core/graphics/engine/systems/TileSystem$AsyncUpdateAreaTask;", "selectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "tileSystem", "Lxc/engine/core/graphics/engine/systems/TileSystem;", "bExit", "", "renderLatch", "Ljava/util/concurrent/CountDownLatch;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Lxc/engine/core/graphics/engine/systems/TileSystem;ZLjava/util/concurrent/CountDownLatch;)V", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SelectOffTask extends TileSystem.AsyncUpdateAreaTask {
        private final boolean bExit;
        private final CountDownLatch renderLatch;
        private final SelectSystem selectSystem;
        private final TileSystem tileSystem;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SelectOffTask(SelectSystem selectSystem, TileSystem tileSystem, boolean z, CountDownLatch renderLatch) {
            super(tileSystem, selectSystem.getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(selectSystem.selectContent.getSelectedAreaInScreenSpace()), selectSystem.selectContent.getSelectedAreaInScreenSpace(), selectSystem.getCurveEngine().getScreenAreaWS(), tileSystem.shouldShowFullscreenTile(), true, false, 64, null);
            Intrinsics.checkNotNullParameter(selectSystem, "selectSystem");
            Intrinsics.checkNotNullParameter(tileSystem, "tileSystem");
            Intrinsics.checkNotNullParameter(renderLatch, "renderLatch");
            this.selectSystem = selectSystem;
            this.tileSystem = tileSystem;
            this.bExit = z;
            this.renderLatch = renderLatch;
            setBNeedRefreshRender(z);
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.AsyncUpdateAreaTask, xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] off execute");
            if (super.execute(res)) {
                return true;
            }
            if (this.bExit) {
                SelectSystem.exitSelect$default(this.selectSystem, false, 1, null);
            }
            this.renderLatch.countDown();
            return false;
        }

        @Override // xc.engine.core.graphics.engine.systems.TileSystem.AsyncUpdateAreaTask, xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] off render completed");
            super.onRenderCompleted();
            if (this.bExit) {
                SelectSystem.exitSelect$default(this.selectSystem, false, 1, null);
            }
            this.renderLatch.countDown();
        }
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$UpdateSelectTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inSelectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "selectContent", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContent;)V", "selectSystem", "selectContentDrawer", "Lxc/engine/core/graphics/engine/systems/SelectSystem$SelectContentDrawer;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class UpdateSelectTask extends BaseAsyncGLTask {
        private final SelectContentDrawer selectContentDrawer;
        private final SelectSystem selectSystem;

        public UpdateSelectTask(SelectSystem inSelectSystem, SelectContent selectContent) {
            Intrinsics.checkNotNullParameter(inSelectSystem, "inSelectSystem");
            Intrinsics.checkNotNullParameter(selectContent, "selectContent");
            this.selectSystem = inSelectSystem;
            this.selectContentDrawer = new SelectContentDrawer(inSelectSystem, selectContent);
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            Intrinsics.checkNotNullParameter(res, "res");
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] [Update] execute");
            this.selectContentDrawer.drawContent();
            setSyncHandle(GLES30.glFenceSync(37143, 0));
            return true;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            Log.d(SelectSystem.TAG, "xl-- [Select System] [Task] [Update] onRenderCompleted");
            this.selectContentDrawer.destroy();
            this.selectSystem.setupSelect(this.selectContentDrawer);
            RenderTaskListener renderTaskListener = this.selectSystem._renderTaskListener;
            if (renderTaskListener != null) {
                renderTaskListener.onRenderCompleted();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00a8  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v17, types: [xc.engine.core.graphics.engine.gl.GLUtils$Companion] */
    /* JADX WARN: Type inference failed for: r12v4, types: [xc.engine.core.graphics.engine.gl.GLUtils$Companion] */
    /* JADX WARN: Type inference failed for: r13v1, types: [xc.engine.core.graphics.engine.gl.GLUtils$Companion] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10, types: [xc.engine.core.graphics.engine.gl.GLUtils$Companion] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13, types: [int] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void renderSelectedOverlay(int fbTarget, RectF areaWS, Point exportSize) {
        List<SpannedData> spans;
        Throwable th;
        ?? r2;
        int iCreateFramebufferFromTexture;
        int iCreateFramebufferFromTexture2;
        ?? r3;
        int i;
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        Intrinsics.checkNotNullParameter(exportSize, "exportSize");
        if (!this.bIsActivated || exportSize.x <= 0 || exportSize.y <= 0 || areaWS.width() <= 0.0f || areaWS.height() <= 0.0f) {
            return;
        }
        List<DataCurve> curves = this.selectContent.getCurves();
        if ((curves == null || curves.isEmpty()) && ((spans = this.selectContent.getSpans()) == null || spans.isEmpty())) {
            return;
        }
        int i2 = 0;
        try {
            int iCreateTexture = GLUtils.INSTANCE.createTexture(exportSize.x, exportSize.y);
            try {
                iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture);
                try {
                    int iCreateTexture2 = GLUtils.INSTANCE.createTexture(exportSize.x, exportSize.y);
                    try {
                        ?? r4 = GLUtils.INSTANCE;
                        iCreateFramebufferFromTexture2 = r4.createFramebufferFromTexture(iCreateTexture2);
                        try {
                            if (iCreateTexture <= 0 || iCreateFramebufferFromTexture <= 0 || iCreateTexture2 <= 0 || iCreateFramebufferFromTexture2 <= 0) {
                                r4 = iCreateTexture2;
                                Log.w(TAG, "renderSelectedOverlay: create FBO/Texture failed, skip");
                                if (iCreateFramebufferFromTexture > 0) {
                                    GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                                }
                                if (iCreateTexture > 0) {
                                    GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                                }
                                if (iCreateFramebufferFromTexture2 > 0) {
                                    GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture2);
                                }
                                if (r4 <= 0) {
                                    return;
                                }
                            } else {
                                try {
                                    drawSelect(iCreateFramebufferFromTexture, iCreateFramebufferFromTexture2, iCreateTexture2, areaWS, exportSize.x, exportSize.y);
                                    r4 = iCreateTexture2;
                                    if (fbTarget > 0) {
                                        try {
                                            GLES30.glBindFramebuffer(36160, fbTarget);
                                            GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
                                            if (iCreateTexture > 0) {
                                                i = iCreateTexture;
                                                TileRenderer.drawFullScreen$default(getCurveEngine().getTileRenderer(), i, false, false, 0.0f, 14, null);
                                            } else {
                                                i = iCreateTexture;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            th = th;
                                            r3 = r4;
                                            i2 = iCreateTexture;
                                            r2 = r3;
                                            try {
                                                Log.e(TAG, "renderSelectedOverlay crash protected", th);
                                                if (r2 > 0) {
                                                    return;
                                                } else {
                                                    return;
                                                }
                                            } finally {
                                                if (iCreateFramebufferFromTexture > 0) {
                                                    GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                                                }
                                                if (i2 > 0) {
                                                    GLUtils.INSTANCE.deleteTexture(i2);
                                                }
                                                if (iCreateFramebufferFromTexture2 > 0) {
                                                    GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture2);
                                                }
                                                if (r2 > 0) {
                                                    GLUtils.INSTANCE.deleteTexture(r2);
                                                }
                                            }
                                        }
                                    } else {
                                        i = iCreateTexture;
                                    }
                                    if (iCreateFramebufferFromTexture > 0) {
                                        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                                    }
                                    if (i > 0) {
                                        GLUtils.INSTANCE.deleteTexture(i);
                                    }
                                    if (iCreateFramebufferFromTexture2 > 0) {
                                        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture2);
                                    }
                                    if (r4 <= 0) {
                                        return;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    r4 = iCreateTexture2;
                                }
                            }
                            GLUtils.INSTANCE.deleteTexture(r4);
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (Throwable th5) {
                        r3 = iCreateTexture2;
                        th = th5;
                        iCreateFramebufferFromTexture2 = 0;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    r3 = 0;
                    iCreateFramebufferFromTexture2 = 0;
                }
            } catch (Throwable th7) {
                th = th7;
                r3 = 0;
                iCreateFramebufferFromTexture = 0;
                iCreateFramebufferFromTexture2 = 0;
            }
        } catch (Throwable th8) {
            th = th8;
            r2 = 0;
            iCreateFramebufferFromTexture = 0;
            iCreateFramebufferFromTexture2 = 0;
        }
    }

    public final void selectOn(List<? extends DataCurve> curves, RectF screenSpaceArea) {
        Intrinsics.checkNotNullParameter(curves, "curves");
        Intrinsics.checkNotNullParameter(screenSpaceArea, "screenSpaceArea");
        XLog.d(TAG, "xl-- [Select System] [On] " + curves.size() + " curves: " + screenSpaceArea.width() + " x " + screenSpaceArea.height() + " analysed: " + this.selectContent.getBAnalysed() + " activated: " + this.bIsActivated, new Object[0]);
        if (this.bIsActivated || !this.selectContent.getBAnalysed()) {
            this.incomingSelectContent.setCurves(curves);
            this.incomingSelectContent.setSelectedAreaInScreenSpace(screenSpaceArea);
            if (this.bIsActivated && this.selectMode == SelectMode.SingleShape) {
                this.selectedAreaInScreenSpace = this.incomingSelectContent.getSelectedAreaInScreenSpace();
            }
        }
    }

    public final void selectOn(List<? extends SpannedData> spannedDataList) {
        Intrinsics.checkNotNullParameter(spannedDataList, "spannedDataList");
        Log.d(TAG, "xl-- [Select System] [On] " + spannedDataList.size() + " spans, analysed: " + this.selectContent.getBAnalysed() + " activated: " + this.bIsActivated);
        this.incomingSelectContent.setSpans(spannedDataList);
    }

    public final void selected() {
        GLFrontBufferedWrapperRenderer frontBufferedRenderer;
        EngineBindContext engineBindContext;
        ScaleOperator scaleOperator;
        this.incomingSelectContent.setSpans(getCurveEngine().getSelectedSpanDataList());
        List<DataCurve> curves = this.incomingSelectContent.getCurves();
        if (curves == null || curves.isEmpty()) {
            List<SpannedData> spans = this.incomingSelectContent.getSpans();
            if (spans == null || spans.isEmpty()) {
                Log.d(TAG, "xl-- [Select System] empty selection, exit");
                return;
            }
        }
        this.screenRectWhenSelected = getCurveEngine().getScreenAreaWS();
        this.incomingSelectContent.analyse();
        long epochMilli = Instant.now().toEpochMilli();
        boolean z = false;
        while (this.selectContent.getBHasCallOff()) {
            if (!z) {
                XLog.w(TAG, "xl-- [Select System] waiting for last selection exit...", new Object[0]);
            }
            Thread.sleep(4L);
            if (Instant.now().toEpochMilli() - epochMilli > 1000) {
                z = true;
                break;
            }
            z = true;
        }
        if (z) {
            XLog.w(TAG, "xl-- [Select System] waiting for last selection exit end.", new Object[0]);
        }
        if (this.bIsActivated && !this.incomingSelectContent.sameCountWith(this.selectContent)) {
            Log.d(TAG, "xl-- [Select System] selected content changed, call select off");
            selectOff$default(this, null, false, 0, 7, null);
            return;
        }
        this.selectContent = this.incomingSelectContent;
        this.incomingSelectContent = new SelectContent(this);
        if (this.bIsActivated || this.selectMode != this.selectContent.getSelectMode()) {
            SelectMode selectMode = this.selectContent.getSelectMode();
            this.selectMode = selectMode;
            String str = TAG;
            List<DataCurve> curves2 = this.selectContent.getCurves();
            Integer numValueOf = curves2 != null ? Integer.valueOf(curves2.size()) : null;
            List<SpannedData> spans2 = this.selectContent.getSpans();
            Log.d(str, "xl-- [select] -> " + selectMode + " <- curves: " + numValueOf + " spans: " + (spans2 != null ? Integer.valueOf(spans2.size()) : null));
            getCurveEngine().getHighLightSystem().shutdownHighlight();
            if (this.selectContent.getSpanCount() > 0) {
                SpannedLayoutModel spannedLayoutModel = getCurveEngine().getSpannedLayoutModel();
                if (spannedLayoutModel != null && (engineBindContext = spannedLayoutModel.getEngineBindContext()) != null && (scaleOperator = engineBindContext.mScaleOperator) != null) {
                    scaleOperator.doScaleReal(getCurveEngine().getScaleRatio(), getCurveEngine().getViewportOffset(), new PointF(0.0f, 0.0f));
                }
                List<SpannedData> spans3 = this.selectContent.getSpans();
                if (spans3 != null) {
                    for (SpannedData spannedData : spans3) {
                        MiCurveEngine curveEngine = getCurveEngine();
                        RectF rotatedRectF = spannedData.getRotatedRectF();
                        Intrinsics.checkNotNullExpressionValue(rotatedRectF, "getRotatedRectF(...)");
                        DataManager.updateInfiniteBoundary$default(getCurveEngine().getDataManager(), curveEngine.getRectWSFromSaveRect(rotatedRectF), false, 2, null);
                    }
                }
            }
            if (this.bIsActivated) {
                if (this.selectContent.getBUseCachedImage()) {
                    if (Math.abs(this.selectContent.getSelectedAreaInScreenSpace().width() - this.selectedAreaInScreenSpace.width()) > 1.1f || Math.abs(this.selectContent.getSelectedAreaInScreenSpace().height() - this.selectedAreaInScreenSpace.height()) > 1.1f || Math.abs(this.transformInfo.getAngle()) > 0.0f) {
                        getCurveEngine().getTaskManager().enqueueTask(new UpdateSelectTask(this, this.selectContent));
                    } else {
                        this.selectedAreaInScreenSpace = this.selectContent.getSelectedAreaInScreenSpace();
                        this.transformInfo.reset();
                        calculateWorldSpaceSelectArea();
                    }
                }
                if (this.selectMode == SelectMode.SingleSpan) {
                    this.transformInfo.reset();
                    calculateWorldSpaceSelectArea();
                    MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
                    return;
                } else {
                    if (this.selectMode == SelectMode.SingleShape) {
                        doShapeEdit$default(this, 0, 1, null);
                        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
                        return;
                    }
                    return;
                }
            }
            if (this.selectContent.getBNeedRedraw()) {
                if (this.selectContent.getBHasMarkCurve()) {
                    boolean z2 = !this.selectContent.getBHasOtherCurve();
                    getCurveEngine().getTaskManager().enqueueTask(new SelectOnTask(this, this.curveSystem.getMarkCurveLayer(), this.selectContent, z2, z2));
                }
                if (this.selectContent.getBHasOtherCurve()) {
                    getCurveEngine().getTaskManager().enqueueTask(new SelectOnTask(this, this.curveSystem.getMainCurveLayer(), this.selectContent, true, true));
                }
            } else if (this.selectMode == SelectMode.Multi) {
                getCurveEngine().getTaskManager().enqueueTask(new UpdateSelectTask(this, this.selectContent));
            }
            if (this.selectMode == SelectMode.SingleShape) {
                List<DataCurve> curves3 = this.selectContent.getCurves();
                DataCurve dataCurve = curves3 != null ? curves3.get(0) : null;
                Intrinsics.checkNotNull(dataCurve, "null cannot be cast to non-null type xc.engine.data.pagedata.DataShape");
                this.selectedShape = (DataShape) dataCurve;
                this.transformInfo.reset();
                doShapeEdit$default(this, 0, 1, null);
                return;
            }
            if (this.selectMode != SelectMode.SingleSpan || (frontBufferedRenderer = getCurveEngine().getPenEngine().getFrontBufferedRenderer()) == null) {
                return;
            }
            frontBufferedRenderer.execOnGLThread(new Runnable() { // from class: xc.engine.core.graphics.engine.systems.SelectSystem$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SelectSystem.selected$lambda$8(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void selected$lambda$8(SelectSystem selectSystem) {
        List<SpannedData> spans = selectSystem.selectContent.getSpans();
        selectSystem.selectedSpan = spans != null ? spans.get(0) : null;
        selectSystem.transformInfo.reset();
        if (selectSystem.selectContent.getImgCount() > 0) {
            selectSystem.getCurveEngine().getImgSystem().markDirty();
        }
        if (selectSystem.selectContent.getTextCount() > 0) {
            selectSystem.getCurveEngine().getTextSystem().markDirty();
        }
        MiCurveEngine.requestMultiBufferRender$default(selectSystem.getCurveEngine(), null, 1, null);
        selectSystem.bIsActivated = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void selectOff$default(SelectSystem selectSystem, Function1 function1, boolean z, int i, int i2, Object obj) throws InterruptedException {
        if ((i2 & 1) != 0) {
            function1 = null;
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        selectSystem.selectOff(function1, z, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v14, types: [boolean, int] */
    public final void selectOff(Function1<? super SelectContent, Boolean> condition, boolean isDeleteShape, int state) throws InterruptedException {
        if (this.bIsActivated && !this.selectContent.getBHasCallOff() && this.selectContent.getBAnalysed()) {
            if (condition == null || condition.invoke(this.selectContent).booleanValue()) {
                this.deselectTimes++;
                if (this.selectContent.getShapeCount() <= 0 || this.selectContent.getSpanCount() <= 0 || this.selectContent.getCurveCount() != 0 || state == 1 || this.deselectTimes >= 2) {
                    if (this.selectContent.getShapeCount() <= 1 || this.selectContent.getCurveCount() != 0 || state == 2 || this.deselectTimes >= 2) {
                        this.deselectTimes = 0;
                        this.anchorBoxDrawer.enableAnchorBoxDraw(false);
                        this.selectContent.setBHasCallOff(true);
                        Log.e(TAG, "xl-- [Select System] [Off] " + this.selectedAreaInScreenSpace);
                        if (this.selectMode == SelectMode.Multi && this.selectContent.getShapeCount() > 0) {
                            Thread.sleep(10L);
                        }
                        if (this.selectContent.getBNeedRedraw() && !isDeleteShape) {
                            ?? bHasMarkCurve = this.selectContent.getBHasMarkCurve();
                            int i = bHasMarkCurve;
                            if (this.selectContent.getBHasOtherCurve()) {
                                i = bHasMarkCurve + 1;
                            }
                            CountDownLatch countDownLatch = new CountDownLatch(i);
                            this.selectContent.applyTransformToRect(this.transformInfo);
                            if (this.selectContent.getBHasMarkCurve()) {
                                getCurveEngine().getTaskManager().enqueueTask(new SelectOffTask(this, this.curveSystem.getMarkCurveLayer(), !this.selectContent.getBHasOtherCurve(), countDownLatch));
                            }
                            if (this.selectContent.getBHasOtherCurve()) {
                                getCurveEngine().getTaskManager().enqueueTask(new SelectOffTask(this, this.curveSystem.getMainCurveLayer(), true, countDownLatch));
                            }
                            countDownLatch.await();
                            return;
                        }
                        exitSelect(true);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setupSelect(SelectContentDrawer drawer) {
        this.updateContentLock.lock();
        if (this.selectMode != SelectMode.SingleShape) {
            if (this.selectTextureId > 0) {
                GLUtils.INSTANCE.deleteTexture(this.selectTextureId);
            }
            this.selectTextureId = drawer.getSelectTextureId();
            drawer.setSelectTextureId(0);
        }
        if (!this.bIsActivated && this.selectContent.getImgCount() > 0) {
            getCurveEngine().getImgSystem().markDirty();
        }
        if (!this.bIsActivated && this.selectContent.getTextCount() > 0) {
            getCurveEngine().getTextSystem().markDirty();
        }
        this.selectedAreaInScreenSpace = drawer.getSelectContent().getSelectedAreaInScreenSpace();
        this.transformInfo.reset();
        calculateWorldSpaceSelectArea();
        this.bIsActivated = true;
        this.updateContentLock.unlock();
        Log.e(TAG, "xl-- [Select] [setup]");
    }

    static /* synthetic */ void exitSelect$default(SelectSystem selectSystem, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        selectSystem.exitSelect(z);
    }

    private final void exitSelect(final boolean bRefresh) {
        getCurveEngine().getPenEngine().getFrontBufferedRenderer().execOnGLThread(new Runnable() { // from class: xc.engine.core.graphics.engine.systems.SelectSystem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SelectSystem.exitSelect$lambda$9(this.f$0, bRefresh);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void exitSelect$lambda$9(SelectSystem selectSystem, boolean z) {
        if (selectSystem.selectContent.getImgCount() > 0) {
            selectSystem.getCurveEngine().getImgSystem().markDirty();
        }
        if (selectSystem.selectContent.getTextCount() > 0) {
            selectSystem.getCurveEngine().getTextSystem().markDirty();
        }
        selectSystem.bIsActivated = false;
        selectSystem.selectMode = SelectMode.None;
        selectSystem.selectedShape = null;
        selectSystem.selectedSpan = null;
        selectSystem.incomingSelectContent = new SelectContent(selectSystem);
        selectSystem.selectContent = new SelectContent(selectSystem);
        selectSystem.outlineDrawer.clear();
        selectSystem.anchorBoxDrawer.clear();
        GLUtils.INSTANCE.deleteTexture(selectSystem.selectTextureId);
        selectSystem.selectTextureId = 0;
        selectSystem.selectTextureWidth = 1;
        selectSystem.selectTextureHeight = 1;
        selectSystem.selectedAreaInScreenSpace = new RectF();
        Log.e(TAG, "xl-- [Select] [exit]");
        if (z) {
            MiCurveEngine.requestMultiBufferRender$default(selectSystem.getCurveEngine(), null, 1, null);
        }
    }

    public final void onMove(float x, float y) {
        this.anchorBoxDrawer.enableAnchorBoxDraw(false);
        this.outlineDrawer.setIsDrawAntLine(false);
        this.transformInfo.setOffsetX(x);
        this.transformInfo.setOffsetY(y);
        calculateWorldSpaceSelectArea();
        getCurveEngine().refreshFrame();
    }

    public final void onScale(float value, float centerX, float centerY) {
        this.outlineDrawer.setIsDrawAntLine(false);
        this.transformInfo.setScale(value);
        this.transformInfo.setCenterX(centerX);
        this.transformInfo.setCenterY(centerY);
        calculateWorldSpaceSelectArea();
        getCurveEngine().refreshFrame();
    }

    public final void onRotate(float angle) {
        this.outlineDrawer.setIsDrawAntLine(false);
        this.transformInfo.setAngle(angle);
        calculateWorldSpaceSelectArea();
        getCurveEngine().refreshFrame();
    }

    public final void onRotateDelta(float deltaAngle) {
        this.outlineDrawer.setIsDrawAntLine(false);
        TransformInfo transformInfo = this.transformInfo;
        transformInfo.setAngle(transformInfo.getAngle() + deltaAngle);
        getCurveEngine().refreshFrame();
    }

    public final void clearTransformInfo() {
        this.transformInfo.reset();
    }

    public final void enterImageCropMode() {
        this.transformInfo.setBIsInCropMode(true);
        SpannedData spannedData = this.selectedSpan;
        if (spannedData != null) {
            TransformInfo transformInfo = this.transformInfo;
            Intrinsics.checkNotNull(spannedData);
            RectF mCropRectF = spannedData.mCropRectF;
            Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
            transformInfo.setCropRect(mCropRectF);
        }
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final void handelDoShapeEdit() {
        doShapeEdit$default(this, 0, 1, null);
    }

    static /* synthetic */ void doShapeEdit$default(SelectSystem selectSystem, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        selectSystem.doShapeEdit(i);
    }

    private final void doShapeEdit(int editStatic) {
        this.anchorBoxDrawer.setEditStatic(editStatic);
        float[] shapeEditPoint = getCurveEngine().getShapeEditPoint();
        if (shapeEditPoint != null) {
            this.anchorBoxDrawer.setEditPoints(shapeEditPoint, getCurveEngine().isShowEditPoint(), getCurveEngine().isCloseShape());
        }
    }

    public final void exitImageCropMode() {
        this.transformInfo.setBIsInCropMode(false);
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final void setImageCropValue(float left, float right, float top, float bottom) {
        this.transformInfo.setCropRect(new RectF(left, top, right, bottom));
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final void setEdit() {
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    private final RectF calculateWorldSpaceSelectArea() {
        RectF rectF = new RectF(this.selectedAreaInScreenSpace);
        if (Math.abs(this.transformInfo.getScale() - 1.0f) > 1.0E-4f) {
            rectF.left = this.transformInfo.getCenterX() - ((this.transformInfo.getCenterX() - rectF.left) * this.transformInfo.getScale());
            rectF.right = this.transformInfo.getCenterX() + ((rectF.right - this.transformInfo.getCenterX()) * this.transformInfo.getScale());
            rectF.top = this.transformInfo.getCenterY() - ((this.transformInfo.getCenterY() - rectF.top) * this.transformInfo.getScale());
            rectF.bottom = this.transformInfo.getCenterY() + ((rectF.bottom - this.transformInfo.getCenterY()) * this.transformInfo.getScale());
        }
        rectF.offset(this.transformInfo.getOffsetX(), this.transformInfo.getOffsetY());
        this.transformInfo.setCenterXToShader(MiMathUtils.INSTANCE.remapRange(this.transformInfo.getCenterX(), this.selectedAreaInScreenSpace.left, this.selectedAreaInScreenSpace.right, -1.0f, 1.0f));
        this.transformInfo.setCenterYToShader(MiMathUtils.INSTANCE.remapRange(this.transformInfo.getCenterY(), this.selectedAreaInScreenSpace.top, this.selectedAreaInScreenSpace.bottom, -1.0f, 1.0f));
        RectF worldSpaceRectFromScreenSpaceRect = getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(rectF);
        this.selectedAreaInWorldSpace = worldSpaceRectFromScreenSpaceRect;
        return worldSpaceRectFromScreenSpaceRect;
    }

    public final void renderLayer(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        if (this.bIsActivated) {
            if (!this.finalTexture.isValid()) {
                this.finalTexture = acquireScreenTexture();
                GLUtils.INSTANCE.bindTextureToFramebuffer(this.finalFramebufferId, this.finalTexture.getTexId());
            }
            GLES30.glBindFramebuffer(36160, this.finalFramebufferId);
            int cachedSpanTextureId = 0;
            GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
            GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
            float scaleRatio = getCurveEngine().getScaleRatio();
            if (this.selectMode == SelectMode.SingleSpan) {
                SpannedData spannedData = this.selectedSpan;
                if (spannedData != null) {
                    MiCurveEngine curveEngine = getCurveEngine();
                    RectF rectF = spannedData.getRectF();
                    Intrinsics.checkNotNullExpressionValue(rectF, "getRectF(...)");
                    RectF rectSSFromSaveRect = curveEngine.getRectSSFromSaveRect(rectF);
                    if (!Intrinsics.areEqual(rectSSFromSaveRect, this.lastFrameSpanRect)) {
                        this.transformInfo.reset();
                    }
                    this.lastFrameSpanRect = rectSSFromSaveRect;
                    int type = spannedData.getType();
                    if (type == 7) {
                        cachedSpanTextureId = this.imgSystem.getCachedSpanTextureId(spannedData.getId());
                    } else if (type == 11) {
                        cachedSpanTextureId = this.textSystem.getCachedTextTextureId(spannedData);
                    }
                    int i = cachedSpanTextureId;
                    if (i > 0) {
                        float fWidth = rectSSFromSaveRect.width() * this.transformInfo.getScale() * 0.5f;
                        float fHeight = rectSSFromSaveRect.height() * this.transformInfo.getScale() * 0.5f;
                        RectF worldSpaceRectFromScreenSpaceRect = getCurveEngine().getWorldSpaceRectFromScreenSpaceRect(new RectF(rectSSFromSaveRect.centerX() - fWidth, rectSSFromSaveRect.centerY() - fHeight, rectSSFromSaveRect.centerX() + fWidth, rectSSFromSaveRect.centerY() + fHeight));
                        RectF rectF2 = new RectF(screenRect);
                        rectF2.offset((-this.transformInfo.getOffsetX()) / scaleRatio, (-this.transformInfo.getOffsetY()) / scaleRatio);
                        float degrees = spannedData.getDegrees() + this.transformInfo.getAngle();
                        if (this.lastFrameSpanAngle != spannedData.getDegrees()) {
                            degrees = spannedData.getDegrees();
                        }
                        float f = degrees;
                        if (this.transformInfo.getBIsInCropMode()) {
                            ImageRenderer.renderCrop$default(this.imgRenderer, rectF2, i, worldSpaceRectFromScreenSpaceRect, f, false, 0.0f, 0.0f, this.transformInfo.getCropRect(), 0, 256, null);
                            this.cropFrameRenderer.render(getCurveEngine().getScreenSpaceRectFromWorldSpaceRect(worldSpaceRectFromScreenSpaceRect), this.transformInfo.getCropRect(), spannedData.getDegrees());
                        } else {
                            ImageRenderer imageRenderer = this.imgRenderer;
                            RectF mCropRectF = spannedData.mCropRectF;
                            Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
                            imageRenderer.renderCrop(rectF2, i, worldSpaceRectFromScreenSpaceRect, f, false, 0.0f, 0.0f, mCropRectF, 1);
                        }
                    }
                    if (spannedData.getType() == 11 && Intrinsics.areEqual(this.screenRectWhenSelected, screenRect)) {
                        this.outlineDrawer.draw();
                    }
                    this.lastFrameSpanAngle = spannedData.getDegrees();
                    return;
                }
                return;
            }
            this.updateContentLock.lock();
            if (this.selectMode == SelectMode.SingleShape) {
                DataShape dataShape = this.selectedShape;
                if (dataShape != null) {
                    this.shapeRenderer.render(dataShape, this.finalFramebufferId, new RectF(screenRect.left, screenRect.bottom, screenRect.right, screenRect.top), getCurveEngine().getDrawRatio(), this.transformInfo.getOffsetX() / scaleRatio, this.transformInfo.getOffsetY() / scaleRatio);
                    if (getCurveEngine().getMathDataManager().getCurrentSelectPenType() != 8) {
                        this.anchorBoxDrawer.draw(this.transformInfo.getOffsetX(), this.transformInfo.getOffsetY());
                    }
                }
            } else {
                this.imgRenderer.render(screenRect, this.selectTextureId, this.selectedAreaInWorldSpace, this.transformInfo.getAngle(), true, this.transformInfo.getCenterXToShader(), this.transformInfo.getCenterYToShader());
            }
            if ((this.selectMode == SelectMode.PureCurve || this.selectMode == SelectMode.Multi) && Intrinsics.areEqual(this.screenRectWhenSelected, screenRect)) {
                this.outlineDrawer.draw();
            }
            this.updateContentLock.unlock();
        }
    }

    public final void onShapeMoveEnd() {
        if (this.bIsActivated && this.selectContent.getSelectMode() == SelectMode.Multi) {
            return;
        }
        this.transformInfo.reset();
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$ExportSelectContentBitmap;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inSelectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "inLatch", "Ljava/util/concurrent/CountDownLatch;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Ljava/util/concurrent/CountDownLatch;)V", "selectSystem", "countDownLatch", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ExportSelectContentBitmap extends BaseAsyncGLTask {
        private Bitmap bitmap;
        private final CountDownLatch countDownLatch;
        private final SelectSystem selectSystem;

        public ExportSelectContentBitmap(SelectSystem inSelectSystem, CountDownLatch inLatch) {
            Intrinsics.checkNotNullParameter(inSelectSystem, "inSelectSystem");
            Intrinsics.checkNotNullParameter(inLatch, "inLatch");
            this.selectSystem = inSelectSystem;
            this.countDownLatch = inLatch;
        }

        public final Bitmap getBitmap() {
            return this.bitmap;
        }

        public final void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            if (this.selectSystem.bIsActivated && this.selectSystem.selectContent.getBUseCachedImage() && this.selectSystem.selectTextureId > 0) {
                int iCreateTexture = GLUtils.INSTANCE.createTexture(this.selectSystem.selectTextureWidth, this.selectSystem.selectTextureHeight);
                int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture);
                GLES30.glViewport(0, 0, this.selectSystem.selectTextureWidth, this.selectSystem.selectTextureHeight);
                this.selectSystem.getCurveEngine().getTileRenderer().drawRGBAtoBGRA(iCreateFramebufferFromTexture, this.selectSystem.selectTextureId, true);
                this.bitmap = GLUtils.INSTANCE.getBitmapFromFramebuffer(iCreateFramebufferFromTexture, this.selectSystem.selectTextureWidth, this.selectSystem.selectTextureHeight);
                GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                this.countDownLatch.countDown();
                return false;
            }
            this.countDownLatch.countDown();
            return false;
        }
    }

    public final Bitmap getSelectContentBitmap() throws InterruptedException {
        if (!this.bIsActivated) {
            return null;
        }
        if (WhenMappings.$EnumSwitchMapping$0[this.selectMode.ordinal()] == 1) {
            if (this.selectedShape == null) {
                return null;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ExportShapeBitmapTask exportShapeBitmapTask = new ExportShapeBitmapTask(this, countDownLatch);
            getCurveEngine().getTaskManager().enqueueTask(exportShapeBitmapTask);
            countDownLatch.await();
            return exportShapeBitmapTask.getBitmap();
        }
        if (!this.selectContent.getBUseCachedImage() || this.selectTextureId <= 0) {
            return null;
        }
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        ExportSelectContentBitmap exportSelectContentBitmap = new ExportSelectContentBitmap(this, countDownLatch2);
        getCurveEngine().getTaskManager().enqueueTask(exportSelectContentBitmap);
        countDownLatch2.await();
        return exportSelectContentBitmap.getBitmap();
    }

    /* JADX INFO: compiled from: SelectSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lxc/engine/core/graphics/engine/systems/SelectSystem$ExportShapeBitmapTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inSelectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "inLatch", "Ljava/util/concurrent/CountDownLatch;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/SelectSystem;Ljava/util/concurrent/CountDownLatch;)V", "selectSystem", "countDownLatch", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "flipBitmapVertically", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ExportShapeBitmapTask extends BaseAsyncGLTask {
        private Bitmap bitmap;
        private final CountDownLatch countDownLatch;
        private final SelectSystem selectSystem;

        public ExportShapeBitmapTask(SelectSystem inSelectSystem, CountDownLatch inLatch) {
            Intrinsics.checkNotNullParameter(inSelectSystem, "inSelectSystem");
            Intrinsics.checkNotNullParameter(inLatch, "inLatch");
            this.selectSystem = inSelectSystem;
            this.countDownLatch = inLatch;
        }

        public final Bitmap getBitmap() {
            return this.bitmap;
        }

        public final void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            char c;
            int i;
            Intrinsics.checkNotNullParameter(res, "res");
            DataShape dataShape = this.selectSystem.selectedShape;
            if (dataShape != null) {
                float scaleRatio = this.selectSystem.getCurveEngine().getScaleRatio();
                RectF drawRect = dataShape.getDrawRect();
                float f = 20.0f / scaleRatio;
                RectF rectF = new RectF(drawRect.left - f, drawRect.top - f, drawRect.right + f, drawRect.bottom + f);
                int iCoerceAtLeast = RangesKt.coerceAtLeast((int) (rectF.width() * scaleRatio), 1);
                int iCoerceAtLeast2 = RangesKt.coerceAtLeast((int) (rectF.height() * scaleRatio), 1);
                int iCreateTexture = GLUtils.INSTANCE.createTexture(iCoerceAtLeast, iCoerceAtLeast2);
                int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture);
                int[] iArr = new int[1];
                GLES30.glGetIntegerv(36006, iArr, 0);
                int[] iArr2 = new int[4];
                GLES30.glGetIntegerv(R2.color.audio_bubble_trash_unselected_color_geometry, iArr2, 0);
                try {
                    GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
                    GLES30.glViewport(0, 0, iCoerceAtLeast, iCoerceAtLeast2);
                    GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES30.glClear(16384);
                    c = 0;
                    i = 36160;
                    try {
                        this.selectSystem.shapeRenderer.render(dataShape, iCreateFramebufferFromTexture, rectF, this.selectSystem.getCurveEngine().getDrawRatio(), 0.0f, 0.0f);
                        Bitmap bitmapFromFramebuffer = GLUtils.INSTANCE.getBitmapFromFramebuffer(iCreateFramebufferFromTexture, iCoerceAtLeast, iCoerceAtLeast2);
                        this.bitmap = flipBitmapVertically(bitmapFromFramebuffer);
                        bitmapFromFramebuffer.recycle();
                        GLES30.glBindFramebuffer(36160, iArr[0]);
                        GLES30.glViewport(iArr2[0], iArr2[1], iArr2[2], iArr2[3]);
                        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                        GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                        this.countDownLatch.countDown();
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        GLES30.glBindFramebuffer(i, iArr[c]);
                        GLES30.glViewport(iArr2[c], iArr2[1], iArr2[2], iArr2[3]);
                        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
                        GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                        this.countDownLatch.countDown();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    c = 0;
                    i = 36160;
                }
            } else {
                this.countDownLatch.countDown();
                return false;
            }
        }

        private final Bitmap flipBitmapVertically(Bitmap bitmap) {
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, -1.0f);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            return bitmapCreateBitmap;
        }
    }
}
