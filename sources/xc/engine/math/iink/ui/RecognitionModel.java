package xc.engine.math.iink.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.Observer;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.UninitializedPropertyAccessException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import miui.cloud.CloudPushConstants;
import okio.Utf8;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.data.pagedata.CurveRecognizeManager;
import xc.engine.math.iink.api.IHistoryManager;
import xc.engine.math.iink.api.IInkEditorListener;
import xc.engine.math.iink.api.IInkEngine;
import xc.engine.math.iink.api.IItemIdHelper;
import xc.engine.math.iink.api.IPointerEvent;
import xc.engine.math.iink.api.InkEngineManager;
import xc.engine.math.iink.api.MimeTypeConstants;
import xc.engine.math.iink.bean.BoundingBox;
import xc.engine.math.iink.bean.MathResult;
import xc.engine.math.iink.callback.CurvePropSyncCallback;
import xc.engine.math.iink.data.JiixProcessor;
import xc.engine.math.iink.data.JiixProcessorImpl;
import xc.engine.math.iink.data.ParsedComputationResult;
import xc.engine.math.iink.data.ParsedMathElement;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: compiled from: RecognitionModel.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u009d\u00012\u00020\u0001:\u0002\u009d\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u0002042\u0006\u00106\u001a\u000201H\u0002J%\u00107\u001a\u0002042\u0006\u00108\u001a\u00020\u000f2\u000e\u00109\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\"H\u0002¢\u0006\u0002\u0010:J\u0006\u0010K\u001a\u00020\u001bJ\u0006\u0010L\u001a\u00020\u001bJ\u0006\u0010M\u001a\u000204J!\u0010N\u001a\u0002042\u0006\u0010\b\u001a\u00020\t2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u000f0\"¢\u0006\u0002\u0010PJ\u0010\u0010Q\u001a\u0004\u0018\u00010\t2\u0006\u0010R\u001a\u00020)J\u0018\u0010S\u001a\u0004\u0018\u00010\t2\u0006\u0010R\u001a\u00020)2\u0006\u0010\u0002\u001a\u00020\tJ\u0016\u0010T\u001a\u0002042\u0006\u0010U\u001a\u00020'2\u0006\u0010V\u001a\u00020\tJ\u0016\u0010W\u001a\u00020\u000f2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J!\u0010Y\u001a\b\u0012\u0004\u0012\u00020#0\"2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002¢\u0006\u0002\u0010ZJ3\u0010[\u001a\u0002042\u0006\u0010\\\u001a\u00020\u000f2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010^\u001a\u00020\u00172\u0006\u0010_\u001a\u00020\u0017H\u0002¢\u0006\u0002\u0010`J3\u0010a\u001a\u0002042\u0006\u0010\b\u001a\u00020\t2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010^\u001a\u00020\u00172\u0006\u0010b\u001a\u00020\u000fH\u0002¢\u0006\u0002\u0010cJ\u0010\u0010d\u001a\u0002042\u0006\u0010\b\u001a\u00020\tH\u0002J3\u0010e\u001a\u0002042\u0006\u0010\b\u001a\u00020\t2\u0012\b\u0002\u0010f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000f\u0018\u00010\"2\b\u0010g\u001a\u0004\u0018\u00010\u000fH\u0002¢\u0006\u0002\u0010hJ0\u0010i\u001a\u0002042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010j\u001a\u00020k2\n\b\u0002\u0010l\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010m\u001a\u0004\u0018\u00010\u0007H\u0002J\u0010\u0010n\u001a\u0004\u0018\u00010\u000f2\u0006\u0010o\u001a\u00020\u000fJ\u000e\u0010p\u001a\u0002042\u0006\u00108\u001a\u00020\u000fJ\u0006\u0010q\u001a\u000204J\u000e\u0010r\u001a\u0002042\u0006\u00108\u001a\u00020\u000fJ\b\u0010s\u001a\u00020\u001bH\u0002J\u0006\u0010t\u001a\u000204J\u0006\u0010u\u001a\u000204J\u0006\u0010v\u001a\u000204J\u0006\u0010w\u001a\u000204J\u0006\u0010x\u001a\u000204J\u000e\u0010y\u001a\u0002042\u0006\u0010z\u001a\u00020\u000fJ\u0010\u0010{\u001a\u0002042\b\u0010z\u001a\u0004\u0018\u00010\u000fJ&\u0010|\u001a\u0002042\u000e\u0010}\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010^\u001a\u00020\u00172\u0006\u0010_\u001a\u00020\u0017J\u001b\u0010~\u001a\u0002042\u0011\b\u0002\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u000204\u0018\u00010\u0080\u0001H\u0002J$\u0010\u0081\u0001\u001a\u0002042\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u000f0\"2\u0006\u0010b\u001a\u00020\u000f¢\u0006\u0003\u0010\u0083\u0001J*\u0010\u0081\u0001\u001a\u0002042\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u000f0\"2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015¢\u0006\u0003\u0010\u0084\u0001J\u0014\u0010\u0085\u0001\u001a\u00020\u001b2\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u000fH\u0002J\u0007\u0010\u0087\u0001\u001a\u000204J$\u0010\u0088\u0001\u001a\u00020\u00072\b\u0010\u0089\u0001\u001a\u00030\u008a\u00012\b\u0010\u008b\u0001\u001a\u00030\u008a\u0001H\u0082@¢\u0006\u0003\u0010\u008c\u0001J\"\u0010\u008d\u0001\u001a\u0002042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u00108\u001a\u00020\u000fH\u0082@¢\u0006\u0003\u0010\u008e\u0001J\t\u0010\u008f\u0001\u001a\u000204H\u0002J+\u0010\u0090\u0001\u001a\u0002042\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\u000f0\"2\r\u0010\u0092\u0001\u001a\b\u0012\u0004\u0012\u00020#0\"¢\u0006\u0003\u0010\u0093\u0001J\u0007\u0010\u0094\u0001\u001a\u000204J\u0017\u0010\u0095\u0001\u001a\u0002042\u0006\u0010R\u001a\u00020)2\u0006\u0010U\u001a\u00020'J\u000f\u0010\u0096\u0001\u001a\u00020\u001b2\u0006\u0010z\u001a\u00020\u000fJ\u000f\u0010\u0097\u0001\u001a\u00020\u001b2\u0006\u0010z\u001a\u00020\u000fJ\u0007\u0010\u0098\u0001\u001a\u000204R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u0012\u001a\u001c\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\u0004\u0012\u00020\u00170\u0014\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R,\u0010\u001f\u001a \u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"\u0012\u0004\u0012\u00020\u00170!0 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020.0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020100X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010=\u001a\u0004\u0018\u00010<2\b\u0010;\u001a\u0004\u0018\u00010<8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u000e\u0010B\u001a\u00020CX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010EX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020GX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010H\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\"\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020JX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0099\u0001\u001a\u00030\u009a\u00018VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001¨\u0006\u009e\u0001"}, d2 = {"Lxc/engine/math/iink/ui/RecognitionModel;", "Lkotlinx/coroutines/CoroutineScope;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "mathSystem", "Lxc/engine/core/graphics/engine/systems/MathSystem;", "editor", "Lxc/engine/math/iink/api/IInkEngine;", "getEditor", "()Lxc/engine/math/iink/api/IInkEngine;", "setEditor", "(Lxc/engine/math/iink/api/IInkEngine;)V", "currentEntPath", "", "mainHandler", "Landroid/os/Handler;", "viewModelObserver", "Landroidx/lifecycle/Observer;", "Landroid/util/Pair;", "", "Lxc/engine/operate/touch/TouchPoint;", "", "jiixProcessor", "Lxc/engine/math/iink/data/JiixProcessor;", "isProcessing", "", "isRefreshing", "isDisposed", "lastProcessedStrokeId", "strokeQueue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lkotlin/Triple;", "", "Lxc/engine/math/iink/api/IPointerEvent;", "processedStrokeIds", "", "curveRecognizeManager", "Lxc/engine/data/pagedata/CurveRecognizeManager;", "savedContext", "Landroid/content/Context;", "mathBlockResults", "", "mathBlockExpressions", "mathBlockBoundingBoxes", "Lxc/engine/math/iink/bean/BoundingBox;", "pendingMathResults", "", "Lxc/engine/math/iink/bean/MathResult;", "isMathBlockRefreshingProcessing", "clearAllResults", "", "notifyMathResult", "mathResult", "updateCurvePropForMathBlock", "blockId", "itemIDs", "(Ljava/lang/String;[Ljava/lang/String;)V", "_", "Lxc/engine/math/iink/api/IItemIdHelper;", "itemIdHelper", "getItemIdHelper", "()Lxc/engine/math/iink/api/IItemIdHelper;", "setItemIdHelper", "(Lxc/engine/math/iink/api/IItemIdHelper;)V", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "resultJob", "Lkotlinx/coroutines/Job;", "mainJob", "Lkotlinx/coroutines/CompletableJob;", "pendingContentChanged", "offScreenEditorListener", "Lxc/engine/math/iink/api/IInkEditorListener;", "saveContent", "saveTemp", "closeContent", "contentHaveChange", "blockIds", "(Lxc/engine/math/iink/api/IInkEngine;[Ljava/lang/String;)V", "initializeEngine", CoreConstants.CONTEXT_SCOPE_VALUE, "prepareRecognition", "connectToCurveRecognizeManager", "manager", "offscreenEditor", "generateStrokeId", "touchPoints", "convertTouchPointsToPointerEvents", "(Ljava/util/List;)[Lxc/engine/math/iink/api/IPointerEvent;", "processStrokeWithEditor", "strokeId", "pointerEvents", "key", "curvePageIndex", "(Ljava/lang/String;[Lxc/engine/math/iink/api/IPointerEvent;II)V", "processCurrentStroke", "recordID", "(Lxc/engine/math/iink/api/IInkEngine;[Lxc/engine/math/iink/api/IPointerEvent;ILjava/lang/String;)V", "processQueuedStrokes", "getRecognitionResult", "changedBlockIds", "data", "(Lxc/engine/math/iink/api/IInkEngine;[Ljava/lang/String;Ljava/lang/String;)V", "processMathElement", "mathElement", "Lxc/engine/math/iink/data/ParsedMathElement;", "jiixDataForThisPass", "cachedMathSystem", "getItemsBlockId", "Items", "clearMathBlockResult", "clearAllMathBlockResults", "notifyMathResultClear", "hasIinkContent", "clearIinkContentIfNeeded", "clearEditor", "dispose", "undoWithCallback", "redoWithCallback", "closeSession", "entFilePath", "activateSession", "setStrokeData", "strokePoints", "triggerRecognitionWithCallback", "onComplete", "Lkotlin/Function0;", "erase", "strokeidsToErase", "([Ljava/lang/String;Ljava/lang/String;)V", "([Ljava/lang/String;Ljava/util/List;)V", "isLikelyBinaryGarbled", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "refresh", "waitForMathSystem", "maxWaitTime", "", "checkInterval", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "waitForCurvePropSync", "(Lxc/engine/core/graphics/engine/systems/MathSystem;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cleanupRefresh", "replaceStrokes", "strokes", "pointEvents", "([Ljava/lang/String;[Lxc/engine/math/iink/api/IPointerEvent;)V", "replaceStatus", "setupRealtimeRecognition", "openIinkFile", "createIinkFile", "doDelete", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RecognitionModel implements CoroutineScope {
    private static final String JIIX_DATA_TAG = "RecognitionModel_JIIX";
    private static final String TAG = "RecognitionModel";
    private volatile String currentEntPath;
    private CurveRecognizeManager curveRecognizeManager;
    private IInkEngine editor;
    private final MiCurveEngine engine;
    private volatile boolean isDisposed;
    private boolean isMathBlockRefreshingProcessing;
    private boolean isProcessing;
    private boolean isRefreshing;
    private final JiixProcessor jiixProcessor;
    private String lastProcessedStrokeId;
    private Handler mainHandler;
    private final CompletableJob mainJob;
    private final Map<String, BoundingBox> mathBlockBoundingBoxes;
    private final Map<String, String> mathBlockExpressions;
    private final Map<String, String> mathBlockResults;
    private final MathSystem mathSystem;
    private final Mutex mutex;
    private final IInkEditorListener offScreenEditorListener;
    private Pair<IInkEngine, String[]> pendingContentChanged;
    private final List<MathResult> pendingMathResults;
    private final Set<String> processedStrokeIds;
    private Job resultJob;
    private volatile Context savedContext;
    private final ConcurrentLinkedQueue<Triple<String, IPointerEvent[], Integer>> strokeQueue;
    private Observer<Pair<List<TouchPoint>, Integer>> viewModelObserver;

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel", f = "RecognitionModel.kt", i = {0, 0}, l = {R2.attr.fabCradleRoundedCornerRadius}, m = "waitForCurvePropSync", n = {"mathSystem", "blockId"}, s = {"L$0", "L$1"})
    static final class C04751 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C04751(Continuation<? super C04751> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RecognitionModel.this.waitForCurvePropSync(null, null, this);
        }
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$waitForMathSystem$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel", f = "RecognitionModel.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1}, l = {R2.attr.expandedTitleMarginBottom, R2.attr.extendedFloatingActionButtonSecondaryStyle}, m = "waitForMathSystem", n = {"maxWaitTime", "checkInterval", "maxRetries", IntegerTokenConverter.CONVERTER_KEY, "e", "maxWaitTime", "checkInterval", "maxRetries", IntegerTokenConverter.CONVERTER_KEY}, s = {"J$0", "J$1", "I$0", "I$1", "L$0", "J$0", "J$1", "I$0", "I$1"})
    static final class C04771 extends ContinuationImpl {
        int I$0;
        int I$1;
        long J$0;
        long J$1;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04771(Continuation<? super C04771> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RecognitionModel.this.waitForMathSystem(0L, 0L, this);
        }
    }

    public final IInkEngine initializeEngine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    public final void replaceStatus() {
    }

    public final void setItemIdHelper(IItemIdHelper iItemIdHelper) {
    }

    public RecognitionModel(MiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.engine = engine;
        this.mathSystem = engine.getMathSystem();
        this.jiixProcessor = new JiixProcessorImpl();
        this.lastProcessedStrokeId = "";
        this.strokeQueue = new ConcurrentLinkedQueue<>();
        this.processedStrokeIds = new LinkedHashSet();
        this.mathBlockResults = new LinkedHashMap();
        this.mathBlockExpressions = new LinkedHashMap();
        this.mathBlockBoundingBoxes = new LinkedHashMap();
        this.pendingMathResults = new ArrayList();
        this.mutex = MutexKt.Mutex$default(false, 1, null);
        this.mainJob = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null);
        this.offScreenEditorListener = new IInkEditorListener() { // from class: xc.engine.math.iink.ui.RecognitionModel$offScreenEditorListener$1
            @Override // xc.engine.math.iink.api.IInkEditorListener
            public void partChanged(IInkEngine editor) {
                Intrinsics.checkNotNullParameter(editor, "editor");
            }

            @Override // xc.engine.math.iink.api.IInkEditorListener
            public void contentChanged(IInkEngine editor, String[] blockIds) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(blockIds, "blockIds");
                Log.w("RecognitionModel", " ~~~ contentChanged  ~~~ ");
                String string = Arrays.toString(blockIds);
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                Log.d("RecognitionModel", "Changed blockIds: " + string);
                if (ArraysKt.contains(blockIds, "MainBlock")) {
                    Log.w("RecognitionModel", "MainBlock detected in contentChanged, this might indicate incomplete change notification");
                }
                if (!this.this$0.isRefreshing) {
                    Job job = this.this$0.resultJob;
                    if (job != null) {
                        Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
                    }
                    RecognitionModel recognitionModel = this.this$0;
                    recognitionModel.resultJob = BuildersKt__Builders_commonKt.launch$default(recognitionModel, Dispatchers.getIO(), null, new RecognitionModel$offScreenEditorListener$1$contentChanged$1(this.this$0, editor, blockIds, null), 2, null);
                    return;
                }
                Log.d("RecognitionModel", "contentChanged: isRefreshing=true, saving event for processing after refresh completes");
                this.this$0.pendingContentChanged = new Pair(editor, blockIds);
            }

            @Override // xc.engine.math.iink.api.IInkEditorListener
            public void onError(IInkEngine editor, String blockId, String error, String message) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(blockId, "blockId");
                Intrinsics.checkNotNullParameter(error, "error");
                Intrinsics.checkNotNullParameter(message, "message");
                Log.e("RecognitionModel", "IInkEditorListener error (" + error + "): " + message);
            }
        };
    }

    public final IInkEngine getEditor() {
        return this.editor;
    }

    public final void setEditor(IInkEngine iInkEngine) {
        this.editor = iInkEngine;
    }

    private final void clearAllResults() {
        this.mathBlockResults.clear();
        this.mathBlockBoundingBoxes.clear();
        Log.d(TAG, "Cleared all results");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyMathResult(MathResult mathResult) {
        try {
            String instanceMathResultTag = this.mathSystem.getInstanceMathResultTag();
            this.engine.getMathDataManager().notifyMathResultCallback(instanceMathResultTag, mathResult);
            if (this.pendingMathResults.isEmpty()) {
                return;
            }
            Log.d(TAG, "notifyMathResult: MathSystem ready, notifying " + this.pendingMathResults.size() + " pending results");
            Iterator<T> it = this.pendingMathResults.iterator();
            while (it.hasNext()) {
                try {
                    this.engine.getMathDataManager().notifyMathResultCallback(instanceMathResultTag, (MathResult) it.next());
                } catch (Exception e) {
                    Log.e(TAG, "notifyMathResult: failed to notify pending result", e);
                }
            }
            this.pendingMathResults.clear();
        } catch (UninitializedPropertyAccessException e2) {
            Log.w(TAG, "notifyMathResult: MathSystem.instanceMathResultTag not initialized yet, caching result for later", e2);
            this.pendingMathResults.add(mathResult);
        }
    }

    private final void updateCurvePropForMathBlock(String blockId, String[] itemIDs) {
        if (itemIDs != null) {
            this.mathSystem.updateCurvePropForMathBlock(blockId, itemIDs);
        }
    }

    public final IItemIdHelper getItemIdHelper() {
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine != null) {
            return iInkEngine.createItemIdHelper();
        }
        return null;
    }

    public final boolean saveContent() {
        try {
            Log.d(TAG, "saveContent: invoked");
            if (!this.mathBlockResults.isEmpty() && this.editor != null) {
                for (String str : this.mathBlockResults.keySet()) {
                    IInkEngine iInkEngine = this.editor;
                    Intrinsics.checkNotNull(iInkEngine);
                    String actionOutput = iInkEngine.getActionOutput(str, "numerical-computation", MimeTypeConstants.JIIX);
                    String str2 = actionOutput;
                    if (str2 != null && str2.length() != 0) {
                        JSONObject jSONObject = new JSONObject(actionOutput);
                        IInkEngine iInkEngine2 = this.editor;
                        Intrinsics.checkNotNull(iInkEngine2);
                        String string = jSONObject.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        iInkEngine2.setJIIXData(str, string);
                    }
                }
            }
            IInkEngine iInkEngine3 = this.editor;
            if (iInkEngine3 != null) {
                return iInkEngine3.save();
            }
            return false;
        } catch (Throwable th) {
            Log.e(TAG, "saveContent error", th);
            return false;
        }
    }

    public final boolean saveTemp() {
        try {
            Log.d(TAG, "saveTemp: invoked");
            IInkEngine iInkEngine = this.editor;
            if (iInkEngine != null) {
                return iInkEngine.saveToTemp();
            }
            return false;
        } catch (Throwable th) {
            Log.e(TAG, "saveTemp error", th);
            return false;
        }
    }

    public final void closeContent() {
        Log.d(TAG, "closeContent: invoked");
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine != null) {
            iInkEngine.removeListener(this.offScreenEditorListener);
        }
        IInkEngine iInkEngine2 = this.editor;
        if (iInkEngine2 != null) {
            iInkEngine2.closePackage();
        }
        this.editor = null;
        this.currentEntPath = null;
    }

    public final void contentHaveChange(IInkEngine editor, String[] blockIds) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(blockIds, "blockIds");
        try {
            if (!this.isProcessing) {
                this.isProcessing = true;
                ArrayList arrayList = new ArrayList();
                for (String str : blockIds) {
                    if (!Intrinsics.areEqual(str, "MainBlock")) {
                        arrayList.add(str);
                    }
                }
                ArrayList arrayList2 = arrayList;
                String string = Arrays.toString(arrayList2.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                Log.w("yjg", "cehck blockIds: " + string);
                if (!arrayList2.isEmpty()) {
                    getRecognitionResult(editor, (String[]) arrayList2.toArray(new String[0]), null);
                } else {
                    Log.d(TAG, "Only MainBlock detected in contentChanged, this is likely a delete operation. Skip recognition refresh as erase() already handled cleanup.");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in contentChanged recognition: " + e.getMessage());
        } finally {
            this.isProcessing = false;
            if (!this.strokeQueue.isEmpty()) {
                processQueuedStrokes(editor);
            }
        }
    }

    public final IInkEngine prepareRecognition(Context context, IInkEngine engine) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(engine, "engine");
        try {
            if (this.mainHandler == null) {
                this.mainHandler = new Handler(Looper.getMainLooper());
            }
            Log.d(TAG, "prepareRecognition: Engine prepared, editor will be created when opening iink file");
            return engine;
        } catch (Exception e) {
            Log.e(TAG, "prepareRecognition fail", e);
            return null;
        }
    }

    public final void connectToCurveRecognizeManager(CurveRecognizeManager manager, IInkEngine offscreenEditor) {
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(offscreenEditor, "offscreenEditor");
        this.curveRecognizeManager = manager;
        Log.d(TAG, "Connected CurveRecognizeManager");
    }

    private final String generateStrokeId(List<TouchPoint> touchPoints) {
        if (touchPoints.isEmpty()) {
            return "";
        }
        TouchPoint touchPoint = (TouchPoint) CollectionsKt.first((List) touchPoints);
        TouchPoint touchPoint2 = (TouchPoint) CollectionsKt.last((List) touchPoints);
        float f = 100;
        return ((int) (touchPoint.getX() * f)) + "_" + ((int) (touchPoint.getY() * f)) + "_" + ((int) (touchPoint2.getX() * f)) + "_" + ((int) (touchPoint2.getY() * f)) + "_" + touchPoints.size();
    }

    private final IPointerEvent[] convertTouchPointsToPointerEvents(List<TouchPoint> touchPoints) {
        ArrayList arrayList = new ArrayList();
        if (!touchPoints.isEmpty()) {
            TouchPoint touchPoint = touchPoints.get(0);
            IPointerEvent iPointerEventCreatePointerEvent = InkEngineManager.INSTANCE.createPointerEvent();
            iPointerEventCreatePointerEvent.setX(touchPoint.getX());
            iPointerEventCreatePointerEvent.setY(touchPoint.getY());
            iPointerEventCreatePointerEvent.setT(touchPoint.getTime());
            iPointerEventCreatePointerEvent.setF(touchPoint.getPressure());
            iPointerEventCreatePointerEvent.setEventType(0);
            iPointerEventCreatePointerEvent.setPointerId(0);
            arrayList.add(iPointerEventCreatePointerEvent);
            int size = touchPoints.size() - 1;
            for (int i = 1; i < size; i++) {
                TouchPoint touchPoint2 = touchPoints.get(i);
                IPointerEvent iPointerEventCreatePointerEvent2 = InkEngineManager.INSTANCE.createPointerEvent();
                iPointerEventCreatePointerEvent2.setX(touchPoint2.getX());
                iPointerEventCreatePointerEvent2.setY(touchPoint2.getY());
                iPointerEventCreatePointerEvent2.setT(touchPoint2.getTime());
                iPointerEventCreatePointerEvent2.setF(touchPoint2.getPressure());
                iPointerEventCreatePointerEvent2.setEventType(1);
                iPointerEventCreatePointerEvent2.setPointerId(0);
                arrayList.add(iPointerEventCreatePointerEvent2);
            }
            if (touchPoints.size() > 1) {
                TouchPoint touchPoint3 = touchPoints.get(touchPoints.size() - 1);
                IPointerEvent iPointerEventCreatePointerEvent3 = InkEngineManager.INSTANCE.createPointerEvent();
                iPointerEventCreatePointerEvent3.setX(touchPoint3.getX());
                iPointerEventCreatePointerEvent3.setY(touchPoint3.getY());
                iPointerEventCreatePointerEvent3.setT(touchPoint3.getTime());
                iPointerEventCreatePointerEvent3.setF(touchPoint3.getPressure());
                iPointerEventCreatePointerEvent3.setEventType(2);
                iPointerEventCreatePointerEvent3.setPointerId(0);
                arrayList.add(iPointerEventCreatePointerEvent3);
            }
            return (IPointerEvent[]) arrayList.toArray(new IPointerEvent[0]);
        }
        return (IPointerEvent[]) arrayList.toArray(new IPointerEvent[0]);
    }

    private final void processStrokeWithEditor(String strokeId, IPointerEvent[] pointerEvents, int key, int curvePageIndex) {
        Log.d(TAG, "processStrokeWithEditor: RecordID:" + strokeId + ",isHave:" + this.processedStrokeIds.contains(strokeId) + ",isProcessing:" + this.isProcessing);
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null) {
            Log.w(TAG, "processStrokeWithEditor: editor is null! currentEntPath=" + this.currentEntPath + ", stroke will be queued");
            if (this.processedStrokeIds.contains(strokeId)) {
                return;
            }
            this.strokeQueue.offer(new Triple<>(strokeId, pointerEvents, Integer.valueOf(key)));
            return;
        }
        if (this.processedStrokeIds.contains(strokeId)) {
            return;
        }
        this.lastProcessedStrokeId = strokeId;
        if (this.isProcessing) {
            this.strokeQueue.offer(new Triple<>(strokeId, pointerEvents, Integer.valueOf(key)));
            return;
        }
        this.engine.getMathDataManager().notifyPageDataChanged(curvePageIndex);
        try {
            this.isProcessing = true;
            this.processedStrokeIds.add(strokeId);
            processCurrentStroke(iInkEngine, pointerEvents, key, strokeId);
        } catch (Exception e) {
            Log.e(TAG, "Error processing stroke: " + e.getMessage());
        } finally {
            this.isProcessing = false;
            processQueuedStrokes(iInkEngine);
        }
    }

    private final void processCurrentStroke(IInkEngine editor, IPointerEvent[] pointerEvents, final int key, final String recordID) {
        final String[] strArrAddStrokes;
        Handler handler;
        try {
            if (this.editor == null || (strArrAddStrokes = editor.addStrokes(pointerEvents, false)) == null || strArrAddStrokes.length == 0 || (handler = this.mainHandler) == null) {
                return;
            }
            handler.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RecognitionModel.processCurrentStroke$lambda$3(this.f$0, strArrAddStrokes, key, recordID);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error in processCurrentStroke: " + e.getMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processCurrentStroke$lambda$3(RecognitionModel recognitionModel, String[] strArr, int i, String str) {
        CurveRecognizeManager curveRecognizeManager = recognitionModel.curveRecognizeManager;
        if (curveRecognizeManager != null) {
            curveRecognizeManager.setStrokeIds(strArr, i, str);
        }
    }

    private final void processQueuedStrokes(IInkEngine editor) {
        while (true) {
            Triple<String, IPointerEvent[], Integer> triplePoll = this.strokeQueue.poll();
            if (triplePoll == null) {
                return;
            }
            String strComponent1 = triplePoll.component1();
            IPointerEvent[] iPointerEventArrComponent2 = triplePoll.component2();
            int iIntValue = triplePoll.component3().intValue();
            if (!this.processedStrokeIds.contains(strComponent1)) {
                Log.d(TAG, "Processing queued stroke " + strComponent1);
                this.processedStrokeIds.add(strComponent1);
                this.lastProcessedStrokeId = strComponent1;
                processCurrentStroke(editor, iPointerEventArrComponent2, iIntValue, strComponent1);
            } else {
                Log.d(TAG, "Queued stroke " + strComponent1 + " already processed, skipping");
            }
        }
    }

    static /* synthetic */ void getRecognitionResult$default(RecognitionModel recognitionModel, IInkEngine iInkEngine, String[] strArr, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            strArr = null;
        }
        recognitionModel.getRecognitionResult(iInkEngine, strArr, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getRecognitionResult(IInkEngine editor, String[] changedBlockIds, String data) {
        boolean z;
        long j;
        String strExport;
        String string;
        if (changedBlockIds != null) {
            try {
                z = changedBlockIds.length == 0;
            } catch (Exception e) {
                Log.e(TAG, "getRecognitionResult: ", e);
                return;
            }
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (data == null) {
            j = jCurrentTimeMillis;
            strExport = editor.export(new String[0], MimeTypeConstants.JIIX);
            if (strExport == null) {
                strExport = "";
            }
        } else {
            j = jCurrentTimeMillis;
            strExport = data;
        }
        Log.w("yjg", "RM.getRecognitionResult export_all cost: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms" + (changedBlockIds != null ? " changedBlockIds=" + changedBlockIds.length : ""));
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        ArrayList mathElements = this.jiixProcessor.parseMathElements(strExport);
        Log.w("yjg", "RM.parseMathElements cost: " + (System.currentTimeMillis() - jCurrentTimeMillis3) + "ms, count=" + mathElements.size());
        List<ParsedMathElement> list = mathElements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ParsedMathElement) it.next()).getBlockId());
        }
        Set set = CollectionsKt.toSet(arrayList);
        Log.d(TAG, "==== Recognition Result ====");
        Log.d(TAG, "Rec " + mathElements.size() + " math elements");
        if (!z) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : mathElements) {
                Intrinsics.checkNotNull(changedBlockIds);
                if (ArraysKt.contains(changedBlockIds, ((ParsedMathElement) obj).getBlockId())) {
                    arrayList2.add(obj);
                }
            }
            mathElements = arrayList2;
        }
        int size = mathElements.size();
        if (changedBlockIds != null) {
            string = Arrays.toString(changedBlockIds);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        } else {
            string = null;
        }
        Log.d(TAG, "Elements to process: " + size + ", changedBlockIds: " + string + ", forceFullRefresh: " + z);
        long jCurrentTimeMillis4 = System.currentTimeMillis();
        Iterator<T> it2 = mathElements.iterator();
        while (it2.hasNext()) {
            processMathElement(editor, (ParsedMathElement) it2.next(), strExport, this.mathSystem);
        }
        this.isMathBlockRefreshingProcessing = false;
        Log.w("yjg", "RM.processMathElements cost: " + (System.currentTimeMillis() - jCurrentTimeMillis4) + "ms");
        if (changedBlockIds == null || changedBlockIds.length == 0) {
            for (final String str : this.mathBlockResults.keySet()) {
                if (!set.contains(str)) {
                    Log.d(TAG, "BlockId " + str + " disappeared, clear result");
                    final BoundingBox boundingBox = this.mathBlockBoundingBoxes.get(str);
                    if (boundingBox == null) {
                        boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                    clearMathBlockResult(str);
                    Handler handler = this.mainHandler;
                    if (handler != null) {
                        handler.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                RecognitionModel.getRecognitionResult$lambda$7(this.f$0, str, boundingBox);
                            }
                        });
                    }
                }
            }
        }
        Log.d(TAG, "==================");
        Log.w("yjg", "RM.getRecognitionResult total cost: " + (System.currentTimeMillis() - j) + "ms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getRecognitionResult$lambda$7(RecognitionModel recognitionModel, String str, BoundingBox boundingBox) {
        recognitionModel.notifyMathResult(new MathResult(str, "", "", boundingBox, boundingBox, true, null, 64, null));
    }

    static /* synthetic */ void processMathElement$default(RecognitionModel recognitionModel, IInkEngine iInkEngine, ParsedMathElement parsedMathElement, String str, MathSystem mathSystem, int i, Object obj) {
        if ((i & 4) != 0) {
            str = null;
        }
        if ((i & 8) != 0) {
            mathSystem = null;
        }
        recognitionModel.processMathElement(iInkEngine, parsedMathElement, str, mathSystem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:105:0x027e A[Catch: Exception -> 0x02c5, TryCatch #4 {Exception -> 0x02c5, blocks: (B:64:0x01af, B:66:0x01b6, B:67:0x01be, B:73:0x01d9, B:75:0x01ee, B:77:0x01f4, B:79:0x01fb, B:81:0x0207, B:105:0x027e, B:108:0x0287, B:103:0x0265), top: B:154:0x01af }] */
    /* JADX WARN: Code duplicated, block: B:116:0x02c3  */
    /* JADX WARN: Code duplicated, block: B:72:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:78:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:81:0x0207 A[Catch: Exception -> 0x02c5, TRY_LEAVE, TryCatch #4 {Exception -> 0x02c5, blocks: (B:64:0x01af, B:66:0x01b6, B:67:0x01be, B:73:0x01d9, B:75:0x01ee, B:77:0x01f4, B:79:0x01fb, B:81:0x0207, B:105:0x027e, B:108:0x0287, B:103:0x0265), top: B:154:0x01af }] */
    /* JADX WARN: Code duplicated, block: B:88:0x021f  */
    /* JADX WARN: Code duplicated, block: B:90:0x0222 A[Catch: Exception -> 0x0263, TryCatch #7 {Exception -> 0x0263, blocks: (B:82:0x0210, B:84:0x0216, B:90:0x0222, B:95:0x022f, B:96:0x0252), top: B:159:0x0210 }] */
    /* JADX WARN: Code duplicated, block: B:92:0x022b  */
    /* JADX WARN: Code duplicated, block: B:93:0x022c  */
    /* JADX WARN: Code duplicated, block: B:95:0x022f A[Catch: Exception -> 0x0263, TryCatch #7 {Exception -> 0x0263, blocks: (B:82:0x0210, B:84:0x0216, B:90:0x0222, B:95:0x022f, B:96:0x0252), top: B:159:0x0210 }] */
    public final void processMathElement(IInkEngine editor, ParsedMathElement mathElement, String jiixDataForThisPass, MathSystem cachedMathSystem) {
        String strExport;
        List<String> equalSignStrokeIds;
        boolean zValidateEqualSignRatio;
        String str;
        String str2;
        float f;
        BoundingBox boundingBox;
        final String[] itemsByBlockId;
        BoundingBox smartAlignedBoundingBox;
        String str3;
        boolean z;
        IItemIdHelper iItemIdHelperCreateItemIdHelper;
        float f2;
        BoundingBox boundingBox2;
        final String str4 = MimeTypeConstants.JIIX;
        try {
            String blockId = mathElement.getBlockId();
            BoundingBox boundingBox3 = mathElement.getBoundingBox();
            MathSystem mathSystem = cachedMathSystem == null ? this.mathSystem : cachedMathSystem;
            String[] availableActions = editor.getAvailableActions(blockId);
            String string = Arrays.toString(availableActions);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            Log.d(TAG, "BlockId: " + blockId + ", availableActions: " + string);
            if (!ArraysKt.contains(availableActions, "numerical-computation")) {
                String diagnostic = editor.getDiagnostic(blockId, "numerical-computation");
                Log.w(TAG, "Math " + blockId + " Diagnostic: " + diagnostic);
                if (!this.mathBlockResults.containsKey(blockId) || this.isMathBlockRefreshingProcessing) {
                    return;
                }
                if (boundingBox3 == null) {
                    f2 = 0.0f;
                    boundingBox2 = new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f);
                } else {
                    f2 = 0.0f;
                    boundingBox2 = boundingBox3;
                }
                final MathResult mathResult = new MathResult(blockId, "", "", boundingBox2, boundingBox3 == null ? new BoundingBox(f2, f2, f2, f2) : boundingBox3, true, null, 64, null);
                clearMathBlockResult(blockId);
                Handler handler = this.mainHandler;
                if (handler != null) {
                    handler.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.notifyMathResult(mathResult);
                        }
                    });
                }
                Log.d(TAG, "Clear Math Block due to recognition failure: " + blockId + ", diagnostic: " + diagnostic);
                return;
            }
            try {
                System.currentTimeMillis();
                String actionOutput = editor.getActionOutput(blockId, "numerical-computation", MimeTypeConstants.JIIX);
                String actionOutput2 = editor.getActionOutput(blockId, "numerical-computation", MimeTypeConstants.LATEX);
                JiixProcessor jiixProcessor = this.jiixProcessor;
                if (actionOutput == null) {
                    actionOutput = "";
                }
                ParsedComputationResult andFormatResult = jiixProcessor.parseAndFormatResult(actionOutput);
                try {
                    if (!andFormatResult.isValid() || andFormatResult.getResult().length() <= 0) {
                        str4 = blockId;
                    } else {
                        Log.d(TAG, "Math " + blockId + " calculated Result: " + andFormatResult.getResult());
                        String str5 = this.mathBlockResults.get(blockId);
                        if (str5 == null) {
                            Log.d(TAG, "Math " + blockId + " first calculated");
                        } else if (Intrinsics.areEqual(str5, andFormatResult.getResult())) {
                            str4 = blockId;
                            Log.d(TAG, "Math " + str4 + " unchanged, Skip");
                        } else {
                            Log.d(TAG, "Math " + blockId + " result changed: " + str5 + " -> " + andFormatResult.getResult());
                        }
                        String str6 = jiixDataForThisPass;
                        if (str6 == null || str6.length() == 0) {
                            strExport = editor.export(new String[0], MimeTypeConstants.JIIX);
                            if (strExport == null) {
                                strExport = "";
                            }
                        } else {
                            strExport = jiixDataForThisPass;
                        }
                        boolean z2 = true;
                        if (this.isRefreshing || (equalSignStrokeIds = this.jiixProcessor.getEqualSignStrokeIds(strExport, blockId)) == null || equalSignStrokeIds.size() < 2 || mathSystem == null) {
                            zValidateEqualSignRatio = true;
                        } else {
                            zValidateEqualSignRatio = mathSystem.validateEqualSignRatio(equalSignStrokeIds);
                            if (!zValidateEqualSignRatio) {
                                Log.d(TAG, "Equal sign ratio validation failed for blockId: " + blockId + ", skip displaying result");
                            }
                        }
                        if (!zValidateEqualSignRatio) {
                            Log.d(TAG, "Skip sending result for blockId: " + blockId + " due to invalid equal sign ratio");
                            str4 = blockId;
                        } else if (this.mathBlockResults.containsKey(blockId)) {
                            try {
                                if (this.isMathBlockRefreshingProcessing) {
                                    str = blockId;
                                } else {
                                    str2 = blockId;
                                    try {
                                        if (boundingBox3 == null) {
                                            try {
                                                f = 0.0f;
                                                boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f);
                                            } catch (Exception e) {
                                                e = e;
                                                str4 = str2;
                                                Integer.valueOf(Log.e(TAG, StringUtils.SPACE + str4 + " Result: " + e.getMessage()));
                                                return;
                                            }
                                        } else {
                                            f = 0.0f;
                                            boundingBox = boundingBox3;
                                        }
                                        final MathResult mathResult2 = new MathResult(str2, "", "", boundingBox, boundingBox3 == null ? new BoundingBox(f, f, f, f) : boundingBox3, true, null, 64, null);
                                        str = str2;
                                        try {
                                            clearMathBlockResult(str);
                                            Handler handler2 = this.mainHandler;
                                            if (handler2 != null) {
                                                handler2.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda2
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        this.f$0.notifyMathResult(mathResult2);
                                                    }
                                                });
                                            }
                                            Log.d(TAG, "Clear Math Block: " + str);
                                        } catch (Exception e2) {
                                            e = e2;
                                            str4 = str;
                                            Integer.valueOf(Log.e(TAG, StringUtils.SPACE + str4 + " Result: " + e.getMessage()));
                                            return;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        str = str2;
                                        str4 = str;
                                        Integer.valueOf(Log.e(TAG, StringUtils.SPACE + str4 + " Result: " + e.getMessage()));
                                        return;
                                    }
                                }
                                Log.w("strokeId", "check blockId:" + str);
                                BoundingBox formulaBoundingBox = null;
                                if (editor != null || (iItemIdHelperCreateItemIdHelper = editor.createItemIdHelper()) == null) {
                                    itemsByBlockId = null;
                                } else {
                                    itemsByBlockId = iItemIdHelperCreateItemIdHelper.getItemsByBlockId(str);
                                }
                                if (!this.engine.getMathDataManager().getIsSelect()) {
                                    this.mathBlockResults.put(str, andFormatResult.getResult());
                                }
                                try {
                                    str3 = jiixDataForThisPass;
                                    if (str3 != null || str3.length() == 0) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (z) {
                                        if (strExport.length() > 0) {
                                            z2 = false;
                                        }
                                        if (z2) {
                                            Log.w(TAG, "RM.processMathElement export_all(again) cost: " + (System.currentTimeMillis() - System.currentTimeMillis()) + "ms blockId=" + str);
                                        }
                                    }
                                    smartAlignedBoundingBox = this.jiixProcessor.getSmartAlignedBoundingBox(strExport, str);
                                    try {
                                        formulaBoundingBox = this.jiixProcessor.getFormulaBoundingBox(strExport, str);
                                    } catch (Exception e4) {
                                        e = e4;
                                        Log.e(TAG, "smartBbox caculated error: " + e.getMessage());
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    smartAlignedBoundingBox = null;
                                }
                                BoundingBox boundingBox4 = formulaBoundingBox;
                                if (smartAlignedBoundingBox != null) {
                                    this.mathBlockBoundingBoxes.put(str, smartAlignedBoundingBox);
                                }
                                if (smartAlignedBoundingBox != null || boundingBox4 == null) {
                                    str4 = str;
                                } else {
                                    str2 = str;
                                    final MathResult mathResult3 = new MathResult(str2, "", andFormatResult.getResult(), smartAlignedBoundingBox, boundingBox4, false, actionOutput2);
                                    str4 = str2;
                                    Handler handler3 = this.mainHandler;
                                    if (handler3 != null) {
                                        handler3.postDelayed(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                RecognitionModel.processMathElement$lambda$9(this.f$0, str4, itemsByBlockId, mathResult3);
                                            }
                                        }, 200L);
                                    }
                                    Log.d(TAG, "SEND " + str4 + " RESULT");
                                }
                            } catch (Exception e6) {
                                e = e6;
                                str = blockId;
                            }
                        } else {
                            str = blockId;
                            Log.w("strokeId", "check blockId:" + str);
                            BoundingBox formulaBoundingBox2 = null;
                            if (editor != null) {
                                itemsByBlockId = null;
                            } else {
                                itemsByBlockId = null;
                            }
                            if (!this.engine.getMathDataManager().getIsSelect()) {
                                this.mathBlockResults.put(str, andFormatResult.getResult());
                            }
                            str3 = jiixDataForThisPass;
                            if (str3 != null) {
                                z = true;
                            } else {
                                z = true;
                            }
                            if (z) {
                                if (strExport.length() > 0) {
                                    z2 = false;
                                }
                                if (z2) {
                                    Log.w(TAG, "RM.processMathElement export_all(again) cost: " + (System.currentTimeMillis() - System.currentTimeMillis()) + "ms blockId=" + str);
                                }
                            }
                            smartAlignedBoundingBox = this.jiixProcessor.getSmartAlignedBoundingBox(strExport, str);
                            formulaBoundingBox2 = this.jiixProcessor.getFormulaBoundingBox(strExport, str);
                            BoundingBox boundingBox5 = formulaBoundingBox2;
                            if (smartAlignedBoundingBox != null) {
                                this.mathBlockBoundingBoxes.put(str, smartAlignedBoundingBox);
                            }
                            if (smartAlignedBoundingBox != null) {
                                str4 = str;
                            } else {
                                str4 = str;
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e7) {
                    e = e7;
                }
            } catch (Exception e8) {
                e = e8;
                str4 = blockId;
            }
        } catch (Exception e9) {
            Log.e(TAG, "MATH ERROR: " + e9.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processMathElement$lambda$9(RecognitionModel recognitionModel, String str, String[] strArr, MathResult mathResult) {
        try {
            recognitionModel.updateCurvePropForMathBlock(str, strArr);
            recognitionModel.notifyMathResult(mathResult);
        } catch (Exception e) {
            Log.e(TAG, "Failed to update curve prop for math block " + str, e);
        }
    }

    public final String getItemsBlockId(String Items) {
        Intrinsics.checkNotNullParameter(Items, "Items");
        try {
            IItemIdHelper itemIdHelper = getItemIdHelper();
            String itemParentId = itemIdHelper != null ? itemIdHelper.getItemParentId(Items) : null;
            Log.d(TAG, "getItemsBlockId: " + itemParentId);
            return itemParentId;
        } catch (Exception e) {
            Log.e(TAG, "getItemsBlockId error: " + e.getMessage());
            return null;
        }
    }

    public final void clearMathBlockResult(String blockId) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        this.mathBlockResults.remove(blockId);
        this.mathBlockBoundingBoxes.remove(blockId);
    }

    public final void clearAllMathBlockResults() {
        this.mathBlockResults.clear();
        this.mathBlockBoundingBoxes.clear();
        Log.d(TAG, "clearAllMathBlockResults()");
    }

    public final void notifyMathResultClear(String blockId) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        new MathResult(blockId, "", "", new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f), new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f), true, null, 64, null);
        this.mathSystem.clearResultByMathBlockId(blockId);
        clearMathBlockResult(blockId);
    }

    private final boolean hasIinkContent() {
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null) {
            return false;
        }
        try {
            String strExport = iInkEngine.export(new String[0], MimeTypeConstants.JIIX);
            if (strExport == null) {
                strExport = "";
            }
            List<ParsedMathElement> mathElements = this.jiixProcessor.parseMathElements(strExport);
            boolean z = !mathElements.isEmpty();
            Log.d(TAG, "hasIinkContent: " + z + ", mathElements count: " + mathElements.size());
            return z;
        } catch (Exception e) {
            Log.e(TAG, "hasIinkContent: error checking content", e);
            return false;
        }
    }

    public final void clearIinkContentIfNeeded() {
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null) {
            return;
        }
        try {
            if (!hasIinkContent()) {
                Log.d(TAG, "clearIinkContentIfNeeded: iink file is empty, no need to clear");
                return;
            }
            iInkEngine.clear();
            iInkEngine.save();
            Log.d(TAG, "clearIinkContentIfNeeded: iink content cleared and saved");
        } catch (Exception e) {
            Log.e(TAG, "clearIinkContentIfNeeded: error clearing iink content", e);
        }
    }

    public final void clearEditor() {
        IInkEngine iInkEngine = this.editor;
        if (!this.mathBlockResults.isEmpty() && iInkEngine != null) {
            try {
                for (String str : this.mathBlockResults.keySet()) {
                    String actionOutput = iInkEngine.getActionOutput(str, "numerical-computation", MimeTypeConstants.JIIX);
                    String str2 = actionOutput;
                    if (str2 != null && str2.length() != 0) {
                        try {
                            String string = new JSONObject(actionOutput).toString();
                            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                            iInkEngine.setJIIXData(str, string);
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to parse resultData for blockId: " + str, e);
                        }
                    }
                }
            } catch (Exception e2) {
                Log.e(TAG, "clearEditor: failed to save computation results", e2);
            }
        }
        if (iInkEngine != null) {
            iInkEngine.save();
        }
        clearAllResults();
        this.strokeQueue.clear();
        this.processedStrokeIds.clear();
        this.lastProcessedStrokeId = "";
        Log.d(TAG, "clearEditor()");
    }

    public final void dispose() {
        clearEditor();
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mainHandler = null;
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine != null) {
            iInkEngine.removeListener(this.offScreenEditorListener);
        }
        IInkEngine iInkEngine2 = this.editor;
        if (iInkEngine2 != null) {
            iInkEngine2.closePackage();
        }
        IInkEngine iInkEngine3 = this.editor;
        if (iInkEngine3 != null) {
            iInkEngine3.close();
        }
        this.editor = null;
        clearAllResults();
        this.strokeQueue.clear();
        this.processedStrokeIds.clear();
        this.lastProcessedStrokeId = "";
        this.currentEntPath = null;
        this.savedContext = null;
        this.viewModelObserver = null;
        this.isProcessing = false;
        Log.d(TAG, "dispose: end");
    }

    public final void undoWithCallback() {
        IHistoryManager historyManager;
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null || (historyManager = iInkEngine.getHistoryManager()) == null) {
            return;
        }
        historyManager.undo();
    }

    public final void redoWithCallback() {
        IHistoryManager historyManager;
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null || (historyManager = iInkEngine.getHistoryManager()) == null) {
            return;
        }
        historyManager.redo();
    }

    public final void closeSession(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        if (Intrinsics.areEqual(this.currentEntPath, entFilePath)) {
            IInkEngine iInkEngine = this.editor;
            if (iInkEngine != null) {
                iInkEngine.removeListener(this.offScreenEditorListener);
            }
            IInkEngine iInkEngine2 = this.editor;
            if (iInkEngine2 != null) {
                iInkEngine2.closePackage();
            }
            this.editor = null;
            this.currentEntPath = null;
            clearAllResults();
            this.strokeQueue.clear();
            this.processedStrokeIds.clear();
            this.lastProcessedStrokeId = "";
        }
    }

    public final void activateSession(String entFilePath) {
        String str = entFilePath;
        if (str == null || StringsKt.isBlank(str)) {
            return;
        }
        if (this.editor != null && Intrinsics.areEqual(this.currentEntPath, entFilePath)) {
            Log.d(TAG, "activateSession: editor already exists for " + entFilePath);
            return;
        }
        Log.d(TAG, "activateSession: editor not found for " + entFilePath + ", creating it");
        if (this.savedContext == null) {
            this.engine.getPenEngine().mContext.getApplicationContext();
        }
        if (openIinkFile(entFilePath)) {
            return;
        }
        Log.e(TAG, "activateSession: failed to create editor for " + entFilePath);
    }

    public final void setStrokeData(List<TouchPoint> strokePoints, int key, int curvePageIndex) {
        List<TouchPoint> list = strokePoints;
        if (list == null || list.isEmpty()) {
            return;
        }
        String strGenerateStrokeId = generateStrokeId(strokePoints);
        if (Intrinsics.areEqual(strGenerateStrokeId, this.lastProcessedStrokeId)) {
            Log.e("yjg", "setStrokeData: strokeId == lastProcessedStrokeId ");
            return;
        }
        Log.d(TAG, "RECIVE Curves Data,Points: " + strokePoints.size() + ", ID: " + strGenerateStrokeId + ", KEY: " + key);
        processStrokeWithEditor(strGenerateStrokeId, convertTouchPointsToPointerEvents(strokePoints), key, curvePageIndex);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void triggerRecognitionWithCallback$default(RecognitionModel recognitionModel, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        recognitionModel.triggerRecognitionWithCallback(function0);
    }

    private final void triggerRecognitionWithCallback(final Function0<Unit> onComplete) {
        IInkEngine iInkEngine = this.editor;
        if (iInkEngine == null) {
            return;
        }
        if (!this.isProcessing) {
            BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C04742(iInkEngine, onComplete, null), 2, null);
            return;
        }
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    RecognitionModel.triggerRecognitionWithCallback$lambda$12(onComplete);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerRecognitionWithCallback$lambda$12(Function0 function0) {
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$triggerRecognitionWithCallback$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$triggerRecognitionWithCallback$2", f = "RecognitionModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C04742 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ IInkEngine $currentEditor;
        final /* synthetic */ Function0<Unit> $onComplete;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04742(IInkEngine iInkEngine, Function0<Unit> function0, Continuation<? super C04742> continuation) {
            super(2, continuation);
            this.$currentEditor = iInkEngine;
            this.$onComplete = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RecognitionModel.this.new C04742(this.$currentEditor, this.$onComplete, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04742) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Handler handler;
            Runnable runnable;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    RecognitionModel.this.isProcessing = true;
                    RecognitionModel.this.getRecognitionResult(this.$currentEditor, null, null);
                    Log.d(RecognitionModel.TAG, "Recognition with callback completed");
                    RecognitionModel.this.isProcessing = false;
                    handler = RecognitionModel.this.mainHandler;
                    if (handler != null) {
                        final Function0<Unit> function0 = this.$onComplete;
                        runnable = new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$triggerRecognitionWithCallback$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                RecognitionModel.C04742.invokeSuspend$lambda$0(function0);
                            }
                        };
                        boolean zPost = handler.post(runnable);
                    }
                } catch (Exception e) {
                    Log.e(RecognitionModel.TAG, "Recognition with callback failed: " + e.getMessage());
                    RecognitionModel.this.isProcessing = false;
                    handler = RecognitionModel.this.mainHandler;
                    if (handler != null) {
                        final Function0<Unit> function1 = this.$onComplete;
                        runnable = new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$triggerRecognitionWithCallback$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                RecognitionModel.C04742.invokeSuspend$lambda$0(function1);
                            }
                        };
                    }
                } finally {
                    RecognitionModel.this.isProcessing = false;
                    Handler handler2 = RecognitionModel.this.mainHandler;
                    if (handler2 != null) {
                        final Function0<Unit> function2 = this.$onComplete;
                        Boxing.boxBoolean(handler2.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$triggerRecognitionWithCallback$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                RecognitionModel.C04742.invokeSuspend$lambda$0(function2);
                            }
                        }));
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(Function0 function0) {
            if (function0 != null) {
                function0.invoke();
            }
        }
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$erase$1, reason: invalid class name */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$erase$1", f = "RecognitionModel.kt", i = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {R2.attr.initialExpandedChildrenCount, R2.attr.dialogPreferenceStyle}, m = "invokeSuspend", n = {"$this$withLock_u24default$iv", "$i$f$withLock", "$this$withLock_u24default$iv", "data", "mathElements", "$this$forEach$iv", "element$iv", "element", "blockId", "paddedStrokeIds", "$i$f$withLock", "$i$a$-withLock$default-RecognitionModel$erase$1$1", "$i$f$forEach", "$i$a$-forEach-RecognitionModel$erase$1$1$1"}, s = {"L$0", "I$0", "L$0", "L$2", "L$3", "L$4", "L$6", "L$7", "L$8", "L$9", "I$0", "I$1", "I$2", "I$3"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $recordID;
        final /* synthetic */ String[] $strokeidsToErase;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String[] strArr, String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$strokeidsToErase = strArr;
            this.$recordID = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RecognitionModel.this.new AnonymousClass1(this.$strokeidsToErase, this.$recordID, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:50:0x0170 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TRY_LEAVE, TryCatch #10 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:48:0x016a, B:50:0x0170), top: B:101:0x016a }] */
        /* JADX WARN: Code duplicated, block: B:53:0x01d2  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x01d2 -> B:54:0x01d4). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r21) {
            /*
                Method dump skipped, instruction units count: 587
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: xc.engine.math.iink.ui.RecognitionModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void erase(String[] strokeidsToErase, String recordID) {
        Intrinsics.checkNotNullParameter(strokeidsToErase, "strokeidsToErase");
        Intrinsics.checkNotNullParameter(recordID, "recordID");
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new AnonymousClass1(strokeidsToErase, recordID, null), 2, null);
    }

    public final void erase(String[] strokeidsToErase, List<String> recordID) {
        Intrinsics.checkNotNullParameter(strokeidsToErase, "strokeidsToErase");
        Intrinsics.checkNotNullParameter(recordID, "recordID");
        String string = Arrays.toString(strokeidsToErase);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Log.d(TAG, "deleted strokeId: " + string);
        ArrayList arrayList = new ArrayList();
        for (String str : strokeidsToErase) {
            if (!isLikelyBinaryGarbled(str)) {
                arrayList.add(str);
            }
        }
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        if (strArr.length == 0) {
            return;
        }
        String string2 = Arrays.toString(strArr);
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        Log.d(TAG, "deleted strokeId after filter: " + string2);
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new AnonymousClass2(recordID, strArr, null), 2, null);
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$erase$2, reason: invalid class name */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$erase$2", f = "RecognitionModel.kt", i = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {R2.attr.initialExpandedChildrenCount, R2.attr.drawableSize}, m = "invokeSuspend", n = {"$this$withLock_u24default$iv", "$i$f$withLock", "$this$withLock_u24default$iv", "data", "mathElements", "$this$forEach$iv", "element$iv", "element", "blockId", "paddedStrokeIds", "$i$f$withLock", "$i$a$-withLock$default-RecognitionModel$erase$2$1", "$i$f$forEach", "$i$a$-forEach-RecognitionModel$erase$2$1$1"}, s = {"L$0", "I$0", "L$0", "L$2", "L$3", "L$4", "L$6", "L$7", "L$8", "L$9", "I$0", "I$1", "I$2", "I$3"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String[] $filteredStrokeIdsToErase;
        final /* synthetic */ List<String> $recordID;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(List<String> list, String[] strArr, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$recordID = list;
            this.$filteredStrokeIdsToErase = strArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RecognitionModel.this.new AnonymousClass2(this.$recordID, this.$filteredStrokeIdsToErase, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:52:0x0163 A[Catch: all -> 0x01cf, Exception -> 0x01d4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01d4, all -> 0x01cf, blocks: (B:50:0x015d, B:52:0x0163), top: B:113:0x015d }] */
        /* JADX WARN: Code duplicated, block: B:55:0x01c5  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x01c5 -> B:56:0x01c7). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r21) {
            /*
                Method dump skipped, instruction units count: 618
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: xc.engine.math.iink.ui.RecognitionModel.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final boolean isLikelyBinaryGarbled(String s) {
        if (s == null) {
            return false;
        }
        String str = s;
        if (StringsKt.indexOf$default((CharSequence) str, Utf8.REPLACEMENT_CHARACTER, 0, false, 6, (Object) null) >= 0) {
            return true;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt >= 128) {
                i++;
            }
            if (cCharAt <= 31 && cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\r') {
                i2++;
            }
        }
        return ((float) i) / ((float) s.length()) > 0.3f || i2 >= 3;
    }

    public final void refresh() {
        this.engine.getMathDataManager().setSelect(false);
        this.isRefreshing = true;
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C04721(null), 2, null);
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$refresh$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$refresh$1", f = "RecognitionModel.kt", i = {1, 1}, l = {1020, R2.attr.initialExpandedChildrenCount}, m = "invokeSuspend", n = {"$this$withLock_u24default$iv", "$i$f$withLock"}, s = {"L$0", "I$0"})
    static final class C04721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        Object L$0;
        Object L$1;
        int label;

        C04721(Continuation<? super C04721> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RecognitionModel.this.new C04721(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:23:0x007f A[Catch: all -> 0x0192, TRY_LEAVE, TryCatch #1 {all -> 0x0192, blocks: (B:17:0x0066, B:19:0x006c, B:21:0x0076, B:23:0x007f, B:27:0x008b, B:28:0x00bc, B:30:0x00c2, B:33:0x00d0, B:34:0x00d8, B:36:0x0104, B:37:0x013a, B:39:0x0140, B:40:0x0146, B:43:0x0153, B:44:0x015b, B:46:0x0163), top: B:59:0x0066, outer: #2, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:27:0x008b A[Catch: all -> 0x0192, TRY_ENTER, TryCatch #1 {all -> 0x0192, blocks: (B:17:0x0066, B:19:0x006c, B:21:0x0076, B:23:0x007f, B:27:0x008b, B:28:0x00bc, B:30:0x00c2, B:33:0x00d0, B:34:0x00d8, B:36:0x0104, B:37:0x013a, B:39:0x0140, B:40:0x0146, B:43:0x0153, B:44:0x015b, B:46:0x0163), top: B:59:0x0066, outer: #2, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:30:0x00c2 A[Catch: all -> 0x0192, TryCatch #1 {all -> 0x0192, blocks: (B:17:0x0066, B:19:0x006c, B:21:0x0076, B:23:0x007f, B:27:0x008b, B:28:0x00bc, B:30:0x00c2, B:33:0x00d0, B:34:0x00d8, B:36:0x0104, B:37:0x013a, B:39:0x0140, B:40:0x0146, B:43:0x0153, B:44:0x015b, B:46:0x0163), top: B:59:0x0066, outer: #2, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:33:0x00d0 A[Catch: all -> 0x0192, LOOP:0: B:28:0x00bc->B:33:0x00d0, LOOP_END, TryCatch #1 {all -> 0x0192, blocks: (B:17:0x0066, B:19:0x006c, B:21:0x0076, B:23:0x007f, B:27:0x008b, B:28:0x00bc, B:30:0x00c2, B:33:0x00d0, B:34:0x00d8, B:36:0x0104, B:37:0x013a, B:39:0x0140, B:40:0x0146, B:43:0x0153, B:44:0x015b, B:46:0x0163), top: B:59:0x0066, outer: #2, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:39:0x0140 A[Catch: UninitializedPropertyAccessException -> 0x0163, all -> 0x0192, TRY_LEAVE, TryCatch #0 {UninitializedPropertyAccessException -> 0x0163, blocks: (B:36:0x0104, B:37:0x013a, B:39:0x0140, B:40:0x0146, B:43:0x0153, B:44:0x015b), top: B:57:0x0104, outer: #1 }] */
        /* JADX WARN: Code duplicated, block: B:57:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:62:0x00d8 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:63:0x00ce A[SYNTHETIC] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutex mutex;
            RecognitionModel recognitionModel;
            IInkEngine editor;
            String strExport;
            List<ParsedMathElement> mathElements;
            Iterator<T> it;
            String instanceMathResultTag;
            Iterator it2;
            ParsedMathElement parsedMathElement;
            IInkEngine editor2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (RecognitionModel.this.waitForMathSystem(500L, 20L, this) != coroutine_suspended) {
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                recognitionModel = (RecognitionModel) this.L$1;
                mutex = (Mutex) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            try {
                try {
                    editor = recognitionModel.getEditor();
                    if (editor != null && (strExport = editor.export(new String[0], MimeTypeConstants.JIIX)) != null) {
                        if (strExport.length() == 0) {
                            recognitionModel.cleanupRefresh();
                            Unit unit = Unit.INSTANCE;
                            recognitionModel.cleanupRefresh();
                            mutex.unlock(null);
                            return unit;
                        }
                        mathElements = recognitionModel.jiixProcessor.parseMathElements(strExport);
                        Log.d(RecognitionModel.TAG, "refresh: parsed " + mathElements.size() + " math elements");
                        recognitionModel.isMathBlockRefreshingProcessing = !mathElements.isEmpty();
                        it = mathElements.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Log.d(RecognitionModel.TAG, "refresh: completed for " + mathElements.size() + " math blocks");
                                if (recognitionModel.pendingMathResults.isEmpty()) {
                                    try {
                                        instanceMathResultTag = recognitionModel.mathSystem.getInstanceMathResultTag();
                                        Log.d(RecognitionModel.TAG, "refresh: MathSystem ready, notifying " + recognitionModel.pendingMathResults.size() + " pending results");
                                        it2 = recognitionModel.pendingMathResults.iterator();
                                        while (it2.hasNext()) {
                                            try {
                                                recognitionModel.engine.getMathDataManager().notifyMathResultCallback(instanceMathResultTag, (MathResult) it2.next());
                                            } catch (Exception e) {
                                                Log.e(RecognitionModel.TAG, "refresh: failed to notify pending result", e);
                                            }
                                        }
                                        recognitionModel.pendingMathResults.clear();
                                        break;
                                    } catch (UninitializedPropertyAccessException unused) {
                                        Log.w(RecognitionModel.TAG, "refresh: MathSystem.instanceMathResultTag still not initialized, keeping " + recognitionModel.pendingMathResults.size() + " results cached");
                                        break;
                                    }
                                }
                                break;
                            }
                            parsedMathElement = (ParsedMathElement) it.next();
                            editor2 = recognitionModel.getEditor();
                            if (editor2 == null) {
                                break;
                            }
                            recognitionModel.processMathElement(editor2, parsedMathElement, strExport, recognitionModel.mathSystem);
                        }
                    }
                    recognitionModel.cleanupRefresh();
                    Unit unit2 = Unit.INSTANCE;
                    mutex.unlock(null);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    mutex.unlock(null);
                    throw th;
                }
            } catch (Throwable th2) {
                recognitionModel.cleanupRefresh();
                throw th2;
            }
            RecognitionModel.this.mathSystem.setPageLoading(true);
            Mutex mutex2 = RecognitionModel.this.mutex;
            RecognitionModel recognitionModel2 = RecognitionModel.this;
            this.L$0 = mutex2;
            this.L$1 = recognitionModel2;
            this.I$0 = 0;
            this.label = 2;
            if (mutex2.lock(null, this) != coroutine_suspended) {
                mutex = mutex2;
                recognitionModel = recognitionModel2;
                editor = recognitionModel.getEditor();
                if (editor != null) {
                    if (strExport.length() == 0) {
                        recognitionModel.cleanupRefresh();
                        Unit unit3 = Unit.INSTANCE;
                        recognitionModel.cleanupRefresh();
                        mutex.unlock(null);
                        return unit3;
                    }
                    mathElements = recognitionModel.jiixProcessor.parseMathElements(strExport);
                    Log.d(RecognitionModel.TAG, "refresh: parsed " + mathElements.size() + " math elements");
                    recognitionModel.isMathBlockRefreshingProcessing = !mathElements.isEmpty();
                    it = mathElements.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Log.d(RecognitionModel.TAG, "refresh: completed for " + mathElements.size() + " math blocks");
                            if (recognitionModel.pendingMathResults.isEmpty()) {
                                break;
                            }
                            instanceMathResultTag = recognitionModel.mathSystem.getInstanceMathResultTag();
                            Log.d(RecognitionModel.TAG, "refresh: MathSystem ready, notifying " + recognitionModel.pendingMathResults.size() + " pending results");
                            it2 = recognitionModel.pendingMathResults.iterator();
                            while (it2.hasNext()) {
                                recognitionModel.engine.getMathDataManager().notifyMathResultCallback(instanceMathResultTag, (MathResult) it2.next());
                            }
                            recognitionModel.pendingMathResults.clear();
                            break;
                        }
                        parsedMathElement = (ParsedMathElement) it.next();
                        editor2 = recognitionModel.getEditor();
                        if (editor2 == null) {
                            break;
                            break;
                        }
                        recognitionModel.processMathElement(editor2, parsedMathElement, strExport, recognitionModel.mathSystem);
                    }
                }
                recognitionModel.cleanupRefresh();
                Unit unit4 = Unit.INSTANCE;
                mutex.unlock(null);
                return Unit.INSTANCE;
            }
            return coroutine_suspended;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x006e A[Catch: UninitializedPropertyAccessException -> 0x00ba, TryCatch #1 {UninitializedPropertyAccessException -> 0x00ba, blocks: (B:23:0x0066, B:25:0x006e, B:30:0x008b), top: B:49:0x0066 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0081  */
    /* JADX WARN: Code duplicated, block: B:28:0x0082  */
    /* JADX WARN: Code duplicated, block: B:30:0x008b A[Catch: UninitializedPropertyAccessException -> 0x00ba, TRY_LEAVE, TryCatch #1 {UninitializedPropertyAccessException -> 0x00ba, blocks: (B:23:0x0066, B:25:0x006e, B:30:0x008b), top: B:49:0x0066 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:49:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x0082 -> B:29:0x0088). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x00c5 -> B:44:0x00de). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x00db -> B:44:0x00de). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:30:0x008b
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object waitForMathSystem(long r19, long r21, kotlin.coroutines.Continuation<? super xc.engine.core.graphics.engine.systems.MathSystem> r23) {
        /*
            Method dump skipped, instruction units count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xc.engine.math.iink.ui.RecognitionModel.waitForMathSystem(long, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object waitForCurvePropSync(MathSystem mathSystem, String str, Continuation<? super Unit> continuation) {
        C04751 c04751;
        if (continuation instanceof C04751) {
            c04751 = (C04751) continuation;
            if ((c04751.label & Integer.MIN_VALUE) != 0) {
                c04751.label -= Integer.MIN_VALUE;
            } else {
                c04751 = new C04751(continuation);
            }
        } else {
            c04751 = new C04751(continuation);
        }
        Object objWithTimeoutOrNull = c04751.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04751.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objWithTimeoutOrNull);
            if (mathSystem != null && !mathSystem.hasCurvePropForMathBlock(str)) {
                Log.d(TAG, "refresh: waiting for curveProp sync for blockId=" + str);
                C04762 c04762 = new C04762(mathSystem, str, null);
                c04751.L$0 = SpillingKt.nullOutSpilledVariable(mathSystem);
                c04751.L$1 = str;
                c04751.label = 1;
                objWithTimeoutOrNull = TimeoutKt.withTimeoutOrNull(1000L, c04762, c04751);
                if (objWithTimeoutOrNull == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        str = (String) c04751.L$1;
        ResultKt.throwOnFailure(objWithTimeoutOrNull);
        if (((Boolean) objWithTimeoutOrNull) == null) {
            Boxing.boxInt(Log.w(TAG, "refresh: curveProp sync timeout for blockId=" + str));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$2", f = "RecognitionModel.kt", i = {0}, l = {R2.attr.indicatorSize}, m = "invokeSuspend", n = {"$i$f$suspendCancellableCoroutine"}, s = {"I$0"})
    static final class C04762 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        final /* synthetic */ String $blockId;
        final /* synthetic */ MathSystem $mathSystem;
        int I$0;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04762(MathSystem mathSystem, String str, Continuation<? super C04762> continuation) {
            super(2, continuation);
            this.$mathSystem = mathSystem;
            this.$blockId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C04762(this.$mathSystem, this.$blockId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
            return ((C04762) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r7v0, types: [xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$2$1$callback$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            final MathSystem mathSystem = this.$mathSystem;
            final String str = this.$blockId;
            this.L$0 = mathSystem;
            this.L$1 = str;
            this.I$0 = 0;
            this.label = 1;
            C04762 c04762 = this;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(c04762), 1);
            cancellableContinuationImpl.initCancellability();
            final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final ?? r7 = new CurvePropSyncCallback() { // from class: xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$2$1$callback$1
                @Override // xc.engine.math.iink.callback.CurvePropSyncCallback
                public void onCurvePropSynced(String mathBlockId) {
                    Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
                    if (Intrinsics.areEqual(mathBlockId, str) && atomicBoolean.compareAndSet(false, true)) {
                        CancellableContinuation<Boolean> cancellableContinuation = cancellableContinuationImpl2;
                        Result.Companion companion = Result.INSTANCE;
                        cancellableContinuation.resumeWith(Result.m2532constructorimpl(true));
                    }
                }
            };
            if (!mathSystem.registerCurvePropSyncCallback(str, (CurvePropSyncCallback) r7) && atomicBoolean.compareAndSet(false, true)) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl2.resumeWith(Result.m2532constructorimpl(Boxing.boxBoolean(true)));
            }
            cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: xc.engine.math.iink.ui.RecognitionModel$waitForCurvePropSync$2$1$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    mathSystem.unregisterCurvePropSyncCallback(str, r7);
                }
            });
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(c04762);
            }
            return result == coroutine_suspended ? coroutine_suspended : result;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cleanupRefresh() {
        this.isRefreshing = false;
        Log.d(TAG, "refresh: finished, isRefreshing=false");
        Pair<IInkEngine, String[]> pair = this.pendingContentChanged;
        if (pair != null) {
            Log.d(TAG, "refresh: processing pending contentChanged");
            this.pendingContentChanged = null;
            BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new RecognitionModel$cleanupRefresh$1$1(this, pair, null), 2, null);
        }
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: xc.engine.math.iink.ui.RecognitionModel$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    RecognitionModel.cleanupRefresh$lambda$15(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanupRefresh$lambda$15(RecognitionModel recognitionModel) {
        recognitionModel.mathSystem.setPageLoading(false);
    }

    /* JADX INFO: renamed from: xc.engine.math.iink.ui.RecognitionModel$replaceStrokes$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecognitionModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$replaceStrokes$1", f = "RecognitionModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C04731 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ IPointerEvent[] $pointEvents;
        final /* synthetic */ String[] $strokes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04731(String[] strArr, IPointerEvent[] iPointerEventArr, Continuation<? super C04731> continuation) {
            super(2, continuation);
            this.$strokes = strArr;
            this.$pointEvents = iPointerEventArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RecognitionModel.this.new C04731(this.$strokes, this.$pointEvents, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            IInkEngine editor = RecognitionModel.this.getEditor();
            if (editor != null) {
                editor.replaceStrokes(this.$strokes, this.$pointEvents);
            }
            Log.d(RecognitionModel.TAG, "Replace strokes completed, recognition will be triggered by contentChanged");
            return Unit.INSTANCE;
        }
    }

    public final void replaceStrokes(String[] strokes, IPointerEvent[] pointEvents) {
        Intrinsics.checkNotNullParameter(strokes, "strokes");
        Intrinsics.checkNotNullParameter(pointEvents, "pointEvents");
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C04731(strokes, pointEvents, null), 2, null);
    }

    public final void setupRealtimeRecognition(Context context, CurveRecognizeManager manager) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(manager, "manager");
        this.savedContext = context.getApplicationContext();
        try {
            if (this.mainHandler == null) {
                this.mainHandler = new Handler(Looper.getMainLooper());
            }
            Log.d(TAG, "setupRealtimeRecognition: Main handler initialized");
            this.curveRecognizeManager = manager;
            Log.d(TAG, "Connected CurveRecognizeManager");
            Log.d(TAG, "setupRealtimeRecognition success!");
        } catch (Exception e) {
            Log.e(TAG, "setupRealtimeRecognition fail", e);
        }
    }

    public final boolean openIinkFile(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        try {
            Log.d(TAG, "openIinkFile: entFilePath=" + entFilePath);
            Context applicationContext = this.savedContext;
            if (applicationContext == null) {
                applicationContext = this.engine.getPenEngine().mContext.getApplicationContext();
            }
            if (this.editor != null && Intrinsics.areEqual(this.currentEntPath, entFilePath)) {
                Log.d(TAG, "openIinkFile: editor already exists for " + entFilePath);
                return true;
            }
            IInkEngine iInkEngineCreateEngine = InkEngineManager.INSTANCE.createEngine();
            if (iInkEngineCreateEngine == null) {
                Log.e(TAG, "openIinkFile: failed to create engine");
                return false;
            }
            Intrinsics.checkNotNull(applicationContext);
            if (!iInkEngineCreateEngine.initializeEngine(applicationContext)) {
                Log.e(TAG, "openIinkFile: initializeEngine failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                } catch (Exception e) {
                    Log.e(TAG, "openIinkFile: error closing failed engine", e);
                }
                return false;
            }
            if (!iInkEngineCreateEngine.prepareRecognition(applicationContext)) {
                Log.e(TAG, "openIinkFile: prepareRecognition failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                } catch (Exception e2) {
                    Log.e(TAG, "openIinkFile: error closing failed engine", e2);
                }
                return false;
            }
            boolean zOpenOrCreateForEnt = iInkEngineCreateEngine.openOrCreateForEnt(entFilePath);
            if (zOpenOrCreateForEnt) {
                IInkEngine iInkEngine = this.editor;
                if (iInkEngine != null) {
                    iInkEngine.removeListener(this.offScreenEditorListener);
                }
                IInkEngine iInkEngine2 = this.editor;
                if (iInkEngine2 != null) {
                    iInkEngine2.closePackage();
                }
                IInkEngine iInkEngine3 = this.editor;
                if (iInkEngine3 != null) {
                    iInkEngine3.close();
                }
                this.currentEntPath = entFilePath;
                this.editor = iInkEngineCreateEngine;
                if (iInkEngineCreateEngine != null) {
                    iInkEngineCreateEngine.addListener(this.offScreenEditorListener);
                }
                Log.d(TAG, "openIinkFile: trigger refresh after open");
                refresh();
            } else {
                Log.e(TAG, "openIinkFile: openOrCreateForEnt failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e3) {
                    Integer.valueOf(Log.e(TAG, "openIinkFile: error closing failed engine", e3));
                }
            }
            return zOpenOrCreateForEnt;
            Log.e(TAG, "openIinkFile error", th);
            return false;
        } catch (Throwable th) {
            Log.e(TAG, "openIinkFile error", th);
            return false;
        }
    }

    public final boolean createIinkFile(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        try {
            Log.d(TAG, "createIinkFile: entFilePath=" + entFilePath);
            Context applicationContext = this.savedContext;
            if (applicationContext == null) {
                applicationContext = this.engine.getPenEngine().mContext.getApplicationContext();
            }
            if (this.editor != null && Intrinsics.areEqual(this.currentEntPath, entFilePath)) {
                Log.d(TAG, "createIinkFile: editor already exists for " + entFilePath);
                return true;
            }
            IInkEngine iInkEngineCreateEngine = InkEngineManager.INSTANCE.createEngine();
            if (iInkEngineCreateEngine == null) {
                Log.e(TAG, "createIinkFile: failed to create engine");
                return false;
            }
            Intrinsics.checkNotNull(applicationContext);
            if (!iInkEngineCreateEngine.initializeEngine(applicationContext)) {
                Log.e(TAG, "createIinkFile: initializeEngine failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                } catch (Exception e) {
                    Log.e(TAG, "createIinkFile: error closing failed engine", e);
                }
                return false;
            }
            if (!iInkEngineCreateEngine.prepareRecognition(applicationContext)) {
                Log.e(TAG, "createIinkFile: prepareRecognition failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                } catch (Exception e2) {
                    Log.e(TAG, "createIinkFile: error closing failed engine", e2);
                }
                return false;
            }
            boolean zOpenOrCreateForEnt = iInkEngineCreateEngine.openOrCreateForEnt(entFilePath);
            if (zOpenOrCreateForEnt) {
                IInkEngine iInkEngine = this.editor;
                if (iInkEngine != null) {
                    iInkEngine.removeListener(this.offScreenEditorListener);
                }
                IInkEngine iInkEngine2 = this.editor;
                if (iInkEngine2 != null) {
                    iInkEngine2.closePackage();
                }
                IInkEngine iInkEngine3 = this.editor;
                if (iInkEngine3 != null) {
                    iInkEngine3.close();
                }
                this.currentEntPath = entFilePath;
                this.editor = iInkEngineCreateEngine;
                if (iInkEngineCreateEngine != null) {
                    iInkEngineCreateEngine.addListener(this.offScreenEditorListener);
                }
            } else {
                Log.e(TAG, "createIinkFile: openOrCreateForEnt failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e3) {
                    Integer.valueOf(Log.e(TAG, "createIinkFile: error closing failed engine", e3));
                }
            }
            return zOpenOrCreateForEnt;
            Log.e(TAG, "createIinkFile error", th);
            return false;
        } catch (Throwable th) {
            Log.e(TAG, "createIinkFile error", th);
            return false;
        }
    }

    public final void doDelete() {
        this.engine.getMathDataManager().notifyMathResultDeleteCallBack(MathSystem.INSTANCE.getMATH_RESULT_TAG());
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.mainJob.plus(Dispatchers.getMain());
    }
}
