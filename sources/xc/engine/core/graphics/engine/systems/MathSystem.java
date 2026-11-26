package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.doodle.document.Layer;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.fast.androidx.graphics.XCAnimator;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.PageInkModelInterface;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.DigitalStrokeRenderer;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.api.IItemIdHelper;
import xc.engine.math.iink.bean.BoundingBox;
import xc.engine.math.iink.bean.MathResult;
import xc.engine.math.iink.callback.CurvePropSyncCallback;
import xc.engine.math.iink.callback.MathResultCallback;
import xc.engine.math.iink.ui.RecognitionModel;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: MathSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b*\u0002Ã\u0001\u0018\u0000 Ö\u00012\u00020\u00012\u00020\u00022\u00020\u0003:\fÖ\u0001×\u0001Ø\u0001Ù\u0001Ú\u0001Û\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020&J\u000e\u0010L\u001a\u00020J2\u0006\u0010M\u001a\u00020&J\u0006\u0010A\u001a\u00020&J\u0006\u0010N\u001a\u00020&J\u0006\u0010O\u001a\u00020PJ\u0006\u0010Q\u001a\u00020\u0005J\u0006\u0010R\u001a\u00020JJ\u0006\u0010S\u001a\u00020JJ\u000e\u0010Y\u001a\u00020J2\u0006\u0010Z\u001a\u00020&J\u000e\u0010[\u001a\u00020J2\u0006\u0010\\\u001a\u00020&J\u000e\u0010]\u001a\u00020J2\u0006\u0010^\u001a\u00020&J\u000e\u0010_\u001a\u00020J2\u0006\u0010`\u001a\u00020&J\u0006\u0010X\u001a\u00020&J\u0006\u0010a\u001a\u00020JJ\b\u0010b\u001a\u00020&H\u0002J\u0010\u0010c\u001a\u00020J2\u0006\u0010d\u001a\u00020eH\u0014J\b\u0010f\u001a\u00020JH\u0016J\u0006\u0010g\u001a\u00020JJ\b\u0010h\u001a\u00020JH\u0002J\u0006\u0010i\u001a\u00020JJ\b\u0010j\u001a\u00020JH\u0002J\b\u0010k\u001a\u00020JH\u0002J\b\u0010l\u001a\u00020JH\u0002J\b\u0010m\u001a\u00020JH\u0014J\b\u0010n\u001a\u00020JH\u0002J\b\u0010o\u001a\u00020JH\u0002J\u000e\u0010p\u001a\u00020J2\u0006\u0010q\u001a\u00020/J\b\u0010r\u001a\u00020JH\u0002J\u0010\u0010s\u001a\u00020/2\u0006\u0010t\u001a\u00020uH\u0002J\u000e\u0010f\u001a\u00020J2\u0006\u0010v\u001a\u00020GJ\u0016\u0010w\u001a\b\u0012\u0004\u0012\u00020E0D2\u0006\u0010t\u001a\u00020uH\u0002J \u0010x\u001a\u00020J2\u0006\u0010t\u001a\u00020u2\u0006\u0010q\u001a\u00020/2\u0006\u0010y\u001a\u00020/H\u0002J\u0010\u0010z\u001a\u00020u2\u0006\u0010{\u001a\u000204H\u0002J\u0012\u0010|\u001a\u0004\u0018\u00010\u001e2\u0006\u0010}\u001a\u00020\u0010H\u0002J%\u0010~\u001a\u00020J2\u0006\u0010}\u001a\u00020\u00102\u000f\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0080\u0001¢\u0006\u0003\u0010\u0081\u0001J\u000f\u0010\u0082\u0001\u001a\u00020&2\u0006\u0010}\u001a\u00020\u0010J\u0018\u0010\u0083\u0001\u001a\u00020&2\u0006\u0010}\u001a\u00020\u00102\u0007\u0010\u0084\u0001\u001a\u00020>J\u0018\u0010\u0085\u0001\u001a\u00020J2\u0006\u0010}\u001a\u00020\u00102\u0007\u0010\u0084\u0001\u001a\u00020>J\u0011\u0010\u0086\u0001\u001a\u00020J2\u0006\u0010}\u001a\u00020\u0010H\u0002J'\u0010\u0087\u0001\u001a\u00020&2\u0006\u0010}\u001a\u00020\u00102\n\b\u0002\u0010\u0088\u0001\u001a\u00030\u0089\u00012\n\b\u0002\u0010\u008a\u0001\u001a\u00030\u0089\u0001J\u0018\u0010\u008b\u0001\u001a\u00020J2\u0006\u0010}\u001a\u00020\u00102\u0007\u0010\u008c\u0001\u001a\u00020\u001eJ\t\u0010\u008d\u0001\u001a\u00020JH\u0002J\t\u0010\u008e\u0001\u001a\u00020JH\u0002J\t\u0010\u008f\u0001\u001a\u00020JH\u0002J\u0012\u0010\u0090\u0001\u001a\u00020G2\u0007\u0010\u0091\u0001\u001a\u00020EH\u0002J\t\u0010\u0092\u0001\u001a\u00020JH\u0002J\u0019\u0010\u0093\u0001\u001a\u00020/2\u0007\u0010\u0094\u0001\u001a\u00020/2\u0007\u0010\u0095\u0001\u001a\u00020/J$\u0010\u0096\u0001\u001a\u00020G2\u0007\u0010\u0097\u0001\u001a\u00020G2\u0007\u0010\u0098\u0001\u001a\u00020G2\u0007\u0010\u0099\u0001\u001a\u00020GH\u0002J7\u0010\u009a\u0001\u001a\u00020J2\u0007\u0010\u009b\u0001\u001a\u00020/2\b\u0010\u0094\u0001\u001a\u00030\u009c\u00012\u0007\u0010\u0095\u0001\u001a\u00020/2\u0007\u0010\u009d\u0001\u001a\u00020\r2\u0007\u0010\u009e\u0001\u001a\u00020\rH\u0002J\t\u0010\u009f\u0001\u001a\u00020JH\u0002J\t\u0010 \u0001\u001a\u00020&H\u0002J\t\u0010¡\u0001\u001a\u00020JH\u0002J\u001c\u0010¢\u0001\u001a\u00020J2\u0006\u0010{\u001a\u0002042\t\b\u0002\u0010£\u0001\u001a\u00020&H\u0002J\u0013\u0010¤\u0001\u001a\u0004\u0018\u00010\u00102\u0006\u0010}\u001a\u00020\u0010H\u0002J\r\u0010¥\u0001\u001a\b\u0012\u0004\u0012\u00020E0DJ\u000f\u0010¦\u0001\u001a\b\u0012\u0004\u0012\u00020E0DH\u0002J\u000f\u0010§\u0001\u001a\u00020J2\u0006\u0010}\u001a\u00020\u0010J\u0007\u0010¨\u0001\u001a\u00020JJ\u0018\u0010¨\u0001\u001a\u00020J2\t\u0010©\u0001\u001a\u0004\u0018\u00010\r¢\u0006\u0003\u0010ª\u0001J\u0007\u0010«\u0001\u001a\u00020&J\u0007\u0010¬\u0001\u001a\u00020\rJ\u000f\u0010\u00ad\u0001\u001a\u00020&2\u0006\u0010}\u001a\u00020\u0010J\u0007\u0010®\u0001\u001a\u00020\rJ\u0017\u0010¯\u0001\u001a\b\u0012\u0004\u0012\u00020E0D2\u0006\u0010{\u001a\u000204H\u0002J\u0016\u0010°\u0001\u001a\u00020&2\r\u0010±\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100DJ4\u0010²\u0001\u001a\u00020J2\u0007\u0010³\u0001\u001a\u00020\r2\b\u0010´\u0001\u001a\u00030µ\u00012\u0007\u0010¶\u0001\u001a\u00020/2\u0007\u0010·\u0001\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\rJ\u0011\u0010¸\u0001\u001a\u00020&2\u0006\u0010{\u001a\u000204H\u0002J\u0011\u0010¹\u0001\u001a\u00020&2\u0006\u0010{\u001a\u000204H\u0002J\u0011\u0010º\u0001\u001a\u00020J2\u0006\u0010{\u001a\u000204H\u0002J\t\u0010»\u0001\u001a\u00020JH\u0002J\u0017\u0010¼\u0001\u001a\b\u0012\u0004\u0012\u00020E0D2\u0006\u0010}\u001a\u00020\u0010H\u0002J\u0011\u0010½\u0001\u001a\u00020J2\u0006\u0010{\u001a\u000204H\u0002J\t\u0010¾\u0001\u001a\u00020\rH\u0002J\t\u0010¿\u0001\u001a\u00020JH\u0002J\u0011\u0010À\u0001\u001a\u00020J2\b\u0010Á\u0001\u001a\u00030\u009c\u0001J\u0011\u0010Æ\u0001\u001a\u00020J2\u0006\u0010{\u001a\u000204H\u0016J\t\u0010Ç\u0001\u001a\u00020JH\u0016J\u001d\u0010Ë\u0001\u001a\u00020&2\b\u0010Ì\u0001\u001a\u00030\u009c\u00012\b\u0010Í\u0001\u001a\u00030\u009c\u0001H\u0002J\u0012\u0010Î\u0001\u001a\u00020&2\u0007\u0010Ï\u0001\u001a\u00020&H\u0002J\u0007\u0010Ð\u0001\u001a\u00020JJ\u0014\u0010Ñ\u0001\u001a\u0005\u0018\u00010Ò\u00012\b\u0010Ó\u0001\u001a\u00030Ô\u0001J\u000f\u0010Õ\u0001\u001a\u00020J2\u0006\u0010{\u001a\u000204R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010(\u001a\u00060)R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010*\u001a\u00060+R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020403X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020706X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020706X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010;\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001e06X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010=\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0306X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u000104X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010DX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020GX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010Â\u0001\u001a\u00030Ã\u0001X\u0082\u0004¢\u0006\u0005\n\u0003\u0010Ä\u0001R\u0012\u0010Å\u0001\u001a\u0005\u0018\u00010\u009c\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010È\u0001\u001a\u00020GX\u0086D¢\u0006\n\n\u0000\u001a\u0006\bÉ\u0001\u0010Ê\u0001¨\u0006Ü\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "Lxc/engine/math/iink/callback/MathResultCallback;", "Lkotlinx/coroutines/CoroutineScope;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "RT_WIDTH", "", "RT_HEIGHT", "instanceMathResultTag", "", "getInstanceMathResultTag", "()Ljava/lang/String;", "setInstanceMathResultTag", "(Ljava/lang/String;)V", "mainTextureId", "mainFramebufferId", "tmpTextureId", "tmpFramebufferId", "aniTextureId", "aniFramebufferId", "digitalRenderer", "Lxc/engine/data/DigitalStrokeRenderer;", "curvePropData", "Lxc/engine/data/CurveProp;", "getCurvePropData", "()Lxc/engine/data/CurveProp;", "setCurvePropData", "(Lxc/engine/data/CurveProp;)V", "scannerAnimator", "Lxc/engine/core/fast/androidx/graphics/XCAnimator;", "doAnimator", "", "isAnimatorFirstFrame", "scannerEffectInfo", "Lxc/engine/core/graphics/engine/systems/MathSystem$ScannerEffectInfo;", "mathAnimatorPropInfo", "Lxc/engine/core/graphics/engine/systems/MathSystem$MathAnimatorPropInfo;", "getMathAnimatorPropInfo$libXCEngine_release", "()Lxc/engine/core/graphics/engine/systems/MathSystem$MathAnimatorPropInfo;", "mScreenRect", "Landroid/graphics/RectF;", "screenRectFlip", "renderTargetSizeChanged", "mathResultList", "", "Lxc/engine/math/iink/bean/MathResult;", "renderItemsMap", "", "Lxc/engine/core/graphics/engine/systems/MathSystem$RenderItem;", "prepareRenderItemsMapForAnimation", "renderItemsLock", "", "mathBlockCurvePropMap", "curvePropLock", "pendingCurvePropSyncCallbacks", "Lxc/engine/math/iink/callback/CurvePropSyncCallback;", "pendingCallbacksLock", "isSaveBtnClicked", "isRenderingEnabled", "lastAutoSelectMathResult", "lastAutoSelectCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "fixedScreenAspectRatio", "", "isWideScreen", "setSaveBtnClicked", "", "clicked", "setRenderingEnabled", "enabled", "getisSaveBtnClicked", "getRecognitionModel", "Lxc/engine/math/iink/ui/RecognitionModel;", "getTheCurveEngine", "cancelAnimation", "resetSaveBtnClicked", "isPageInteracting", "isPageLoading", "isFromRefresh", "isFromUndoRedo", "hasJustCleared", "setPageInteracting", "interacting", "setPageLoading", "loading", "setFromUndoRedo", "fromUndoRedo", "setHasJustCleared", "cleared", "finishEdit", "shouldPlayAnimation", "onInit", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onScreenResize", "checkRenderTarget", "initAnimator", "getCurveProp", "destroyRenderTarget", "createAndInitMainRenderTarget", "createAndInitAnimationRenderTarget", "onDestroy", "updateTextureSize", "initializeRenderTarget", "render", "screenRect", "renderAllItems", "calculateItemBoundingBox", "params", "Lxc/engine/core/graphics/engine/systems/MathSystem$RenderParams;", ToDoStat.RADIO, "generateMathResultCurves", "renderSingleItem", "nScreenRect", "createRenderParamsFromMathResult", "mathResult", "getCurvePropForMathBlock", "mathBlockId", "updateCurvePropForMathBlock", "itemIDs", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "hasCurvePropForMathBlock", "registerCurvePropSyncCallback", CallMethod.ARG_CALLBACK, "unregisterCurvePropSyncCallback", "notifyCurvePropSyncCallbacks", "waitForCurvePropSync", "maxWaitTime", "", "checkInterval", "syncCurvePropForMathBlock", "curveProp", "animatorRender", "clearColor", "genOrUpdateAniTexture", "getMathResultScaleFactor", "curve", "renderMathOnTexture", "calculateNormalizedRect", "rectA", "rectB", "normalizeValue", "value", "fromMin", "fromMax", "calculateNormalizedBoundingBox", "res", "Lxc/engine/math/iink/bean/BoundingBox;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "initScanEffectProp", "hasInkPenCurve", "preStartAnimation", "startAnimator", "start", "extractNumericIdFromMathBlockId", "getResultCurves", "getResultCurvesInternal", "clearResultByMathBlockId", "clearAllResults", "pageIndex", "(Ljava/lang/Integer;)V", "hasRenderContent", "getRenderItemCount", "hasMathBlockRenderItem", "getOutTextureId", "getFormulaCurves", "validateEqualSignRatio", "strokeIds", "renderArea", "fbId", "fbSize", "Landroid/graphics/Point;", "areaWS", "tmpFbId", "isResultWithinPageBounds", "isResultWithinScreenBounds", "handleOutOfBoundsResult", "tryReTriggerAutoSelect", "getMathCurves", "proceedWithNormalRendering", "getPreviousFrameOffset", "updateTextureChannel", "onResultRadio", "box", "outBoundBoxCheck", "xc/engine/core/graphics/engine/systems/MathSystem$outBoundBoxCheck$1", "Lxc/engine/core/graphics/engine/systems/MathSystem$outBoundBoxCheck$1;", "lastBoundingBox", "onMathResult", "doDelete", "delta", "getDelta", "()F", "isApproximatelyEqual", HtmlParser.LinkCardElement.TAG, "b", "isCanStartAnimation", "isNeed", "moveCurveToRenderList", "findLatexByPoint", "Lxc/engine/core/graphics/engine/systems/MathSystem$FormulaLatex;", "point", "Landroid/graphics/PointF;", "recordHaveSelectMathResult", "Companion", "RenderParams", "RenderItem", "FormulaLatex", "ScannerEffectInfo", "MathAnimatorPropInfo", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MathSystem extends BaseEngineStruct implements MathResultCallback, CoroutineScope {
    private static final float ANIMATION_DURATION = 1000.0f;
    private static final float RESULT_MARGIN = 15.0f;
    private int RT_HEIGHT;
    private int RT_WIDTH;
    private int aniFramebufferId;
    private int aniTextureId;
    private CurveProp curvePropData;
    private final Object curvePropLock;
    private final float delta;
    private DigitalStrokeRenderer digitalRenderer;
    private boolean doAnimator;
    private float fixedScreenAspectRatio;
    private boolean hasJustCleared;
    public String instanceMathResultTag;
    private boolean isAnimatorFirstFrame;
    private boolean isFromRefresh;
    private boolean isFromUndoRedo;
    private boolean isPageInteracting;
    private boolean isPageLoading;
    private boolean isRenderingEnabled;
    private boolean isSaveBtnClicked;
    private boolean isWideScreen;
    private List<? extends DataCurve> lastAutoSelectCurves;
    private MathResult lastAutoSelectMathResult;
    private BoundingBox lastBoundingBox;
    private RectF mScreenRect;
    private int mainFramebufferId;
    private int mainTextureId;
    private final MathAnimatorPropInfo mathAnimatorPropInfo;
    private final Map<String, CurveProp> mathBlockCurvePropMap;
    private List<MathResult> mathResultList;
    private final MathSystem$outBoundBoxCheck$1 outBoundBoxCheck;
    private final Object pendingCallbacksLock;
    private final Map<String, List<CurvePropSyncCallback>> pendingCurvePropSyncCallbacks;
    private final Map<String, RenderItem> prepareRenderItemsMapForAnimation;
    private final Object renderItemsLock;
    private final Map<String, RenderItem> renderItemsMap;
    private boolean renderTargetSizeChanged;
    private XCAnimator scannerAnimator;
    private final ScannerEffectInfo scannerEffectInfo;
    private RectF screenRectFlip;
    private int tmpFramebufferId;
    private int tmpTextureId;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "MathSystem";
    private static String MATH_RESULT_TAG = "MathSystem";

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PenType.values().length];
            try {
                iArr[PenType.INK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PenType.BALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PenType.PENCIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final float normalizeValue(float value, float fromMin, float fromMax) {
        if (fromMax == fromMin) {
            return 0.0f;
        }
        return (value - fromMin) / (fromMax - fromMin);
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "MATH_RESULT_TAG", "getMATH_RESULT_TAG", "()Ljava/lang/String;", "setMATH_RESULT_TAG", "(Ljava/lang/String;)V", "RESULT_MARGIN", "", "ANIMATION_DURATION", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getMATH_RESULT_TAG() {
            return MathSystem.MATH_RESULT_TAG;
        }

        public final void setMATH_RESULT_TAG(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            MathSystem.MATH_RESULT_TAG = str;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MathSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.RT_WIDTH = -1;
        this.RT_HEIGHT = -1;
        this.scannerEffectInfo = new ScannerEffectInfo();
        this.mathAnimatorPropInfo = new MathAnimatorPropInfo();
        this.screenRectFlip = new RectF();
        this.mathResultList = new ArrayList();
        this.renderItemsMap = new LinkedHashMap();
        this.prepareRenderItemsMapForAnimation = new LinkedHashMap();
        this.renderItemsLock = new Object();
        this.mathBlockCurvePropMap = new LinkedHashMap();
        this.curvePropLock = new Object();
        this.pendingCurvePropSyncCallbacks = new LinkedHashMap();
        this.pendingCallbacksLock = new Object();
        this.isRenderingEnabled = true;
        this.isWideScreen = true;
        this.outBoundBoxCheck = new MathSystem$outBoundBoxCheck$1();
        this.delta = 0.1f;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain());
    }

    public final String getInstanceMathResultTag() {
        String str = this.instanceMathResultTag;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("instanceMathResultTag");
        return null;
    }

    public final void setInstanceMathResultTag(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.instanceMathResultTag = str;
    }

    public final CurveProp getCurvePropData() {
        return this.curvePropData;
    }

    public final void setCurvePropData(CurveProp curveProp) {
        this.curvePropData = curveProp;
    }

    /* JADX INFO: renamed from: getMathAnimatorPropInfo$libXCEngine_release, reason: from getter */
    public final MathAnimatorPropInfo getMathAnimatorPropInfo() {
        return this.mathAnimatorPropInfo;
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0004\b\u000e\u0010\u000fJ\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\rHÆ\u0003Je\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rHÆ\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020/HÖ\u0001J\t\u00100\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u00061"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$RenderParams;", "", "text", "", "startX", "", "startY", "digitWidth", "digitHeight", "spacing", "strokeWidth", "source", "curveProp", "Lxc/engine/data/CurveProp;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;FFFFFFLjava/lang/String;Lxc/engine/data/CurveProp;)V", "getText", "()Ljava/lang/String;", "getStartX", "()F", "setStartX", "(F)V", "getStartY", "setStartY", "getDigitWidth", "setDigitWidth", "getDigitHeight", "setDigitHeight", "getSpacing", "getStrokeWidth", "getSource", "getCurveProp", "()Lxc/engine/data/CurveProp;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final /* data */ class RenderParams {
        private final CurveProp curveProp;
        private float digitHeight;
        private float digitWidth;
        private final String source;
        private final float spacing;
        private float startX;
        private float startY;
        private final float strokeWidth;
        private final String text;

        public static /* synthetic */ RenderParams copy$default(RenderParams renderParams, String str, float f, float f2, float f3, float f4, float f5, float f6, String str2, CurveProp curveProp, int i, Object obj) {
            if ((i & 1) != 0) {
                str = renderParams.text;
            }
            if ((i & 2) != 0) {
                f = renderParams.startX;
            }
            if ((i & 4) != 0) {
                f2 = renderParams.startY;
            }
            if ((i & 8) != 0) {
                f3 = renderParams.digitWidth;
            }
            if ((i & 16) != 0) {
                f4 = renderParams.digitHeight;
            }
            if ((i & 32) != 0) {
                f5 = renderParams.spacing;
            }
            if ((i & 64) != 0) {
                f6 = renderParams.strokeWidth;
            }
            if ((i & 128) != 0) {
                str2 = renderParams.source;
            }
            if ((i & 256) != 0) {
                curveProp = renderParams.curveProp;
            }
            String str3 = str2;
            CurveProp curveProp2 = curveProp;
            float f7 = f5;
            float f8 = f6;
            float f9 = f4;
            float f10 = f2;
            return renderParams.copy(str, f, f10, f3, f9, f7, f8, str3, curveProp2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getText() {
            return this.text;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final float getStartX() {
            return this.startX;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final float getStartY() {
            return this.startY;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final float getDigitWidth() {
            return this.digitWidth;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final float getDigitHeight() {
            return this.digitHeight;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final float getSpacing() {
            return this.spacing;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final float getStrokeWidth() {
            return this.strokeWidth;
        }

        /* JADX INFO: renamed from: component8, reason: from getter */
        public final String getSource() {
            return this.source;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final CurveProp getCurveProp() {
            return this.curveProp;
        }

        public final RenderParams copy(String text, float startX, float startY, float digitWidth, float digitHeight, float spacing, float strokeWidth, String source, CurveProp curveProp) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(source, "source");
            return new RenderParams(text, startX, startY, digitWidth, digitHeight, spacing, strokeWidth, source, curveProp);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RenderParams)) {
                return false;
            }
            RenderParams renderParams = (RenderParams) other;
            return Intrinsics.areEqual(this.text, renderParams.text) && Float.compare(this.startX, renderParams.startX) == 0 && Float.compare(this.startY, renderParams.startY) == 0 && Float.compare(this.digitWidth, renderParams.digitWidth) == 0 && Float.compare(this.digitHeight, renderParams.digitHeight) == 0 && Float.compare(this.spacing, renderParams.spacing) == 0 && Float.compare(this.strokeWidth, renderParams.strokeWidth) == 0 && Intrinsics.areEqual(this.source, renderParams.source) && Intrinsics.areEqual(this.curveProp, renderParams.curveProp);
        }

        public int hashCode() {
            int iHashCode = ((((((((((((((this.text.hashCode() * 31) + Float.hashCode(this.startX)) * 31) + Float.hashCode(this.startY)) * 31) + Float.hashCode(this.digitWidth)) * 31) + Float.hashCode(this.digitHeight)) * 31) + Float.hashCode(this.spacing)) * 31) + Float.hashCode(this.strokeWidth)) * 31) + this.source.hashCode()) * 31;
            CurveProp curveProp = this.curveProp;
            return iHashCode + (curveProp == null ? 0 : curveProp.hashCode());
        }

        public String toString() {
            return "RenderParams(text=" + this.text + ", startX=" + this.startX + ", startY=" + this.startY + ", digitWidth=" + this.digitWidth + ", digitHeight=" + this.digitHeight + ", spacing=" + this.spacing + ", strokeWidth=" + this.strokeWidth + ", source=" + this.source + ", curveProp=" + this.curveProp + ")";
        }

        public RenderParams(String text, float f, float f2, float f3, float f4, float f5, float f6, String source, CurveProp curveProp) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(source, "source");
            this.text = text;
            this.startX = f;
            this.startY = f2;
            this.digitWidth = f3;
            this.digitHeight = f4;
            this.spacing = f5;
            this.strokeWidth = f6;
            this.source = source;
            this.curveProp = curveProp;
        }

        public final String getText() {
            return this.text;
        }

        public final float getStartX() {
            return this.startX;
        }

        public final void setStartX(float f) {
            this.startX = f;
        }

        public final float getStartY() {
            return this.startY;
        }

        public final void setStartY(float f) {
            this.startY = f;
        }

        public final float getDigitWidth() {
            return this.digitWidth;
        }

        public final void setDigitWidth(float f) {
            this.digitWidth = f;
        }

        public final float getDigitHeight() {
            return this.digitHeight;
        }

        public final void setDigitHeight(float f) {
            this.digitHeight = f;
        }

        public final float getSpacing() {
            return this.spacing;
        }

        public final float getStrokeWidth() {
            return this.strokeWidth;
        }

        public final String getSource() {
            return this.source;
        }

        public final CurveProp getCurveProp() {
            return this.curveProp;
        }
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J3\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001d"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$RenderItem;", "", "mathBlockId", "", "params", "Lxc/engine/core/graphics/engine/systems/MathSystem$RenderParams;", "timestamp", "", "latex", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lxc/engine/core/graphics/engine/systems/MathSystem$RenderParams;JLjava/lang/String;)V", "getMathBlockId", "()Ljava/lang/String;", "getParams", "()Lxc/engine/core/graphics/engine/systems/MathSystem$RenderParams;", "getTimestamp", "()J", "getLatex", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final /* data */ class RenderItem {
        private final String latex;
        private final String mathBlockId;
        private final RenderParams params;
        private final long timestamp;

        public static /* synthetic */ RenderItem copy$default(RenderItem renderItem, String str, RenderParams renderParams, long j, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = renderItem.mathBlockId;
            }
            if ((i & 2) != 0) {
                renderParams = renderItem.params;
            }
            if ((i & 4) != 0) {
                j = renderItem.timestamp;
            }
            if ((i & 8) != 0) {
                str2 = renderItem.latex;
            }
            String str3 = str2;
            return renderItem.copy(str, renderParams, j, str3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getMathBlockId() {
            return this.mathBlockId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final RenderParams getParams() {
            return this.params;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final long getTimestamp() {
            return this.timestamp;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getLatex() {
            return this.latex;
        }

        public final RenderItem copy(String mathBlockId, RenderParams params, long timestamp, String latex) {
            Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
            Intrinsics.checkNotNullParameter(params, "params");
            return new RenderItem(mathBlockId, params, timestamp, latex);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RenderItem)) {
                return false;
            }
            RenderItem renderItem = (RenderItem) other;
            return Intrinsics.areEqual(this.mathBlockId, renderItem.mathBlockId) && Intrinsics.areEqual(this.params, renderItem.params) && this.timestamp == renderItem.timestamp && Intrinsics.areEqual(this.latex, renderItem.latex);
        }

        public int hashCode() {
            int iHashCode = ((((this.mathBlockId.hashCode() * 31) + this.params.hashCode()) * 31) + Long.hashCode(this.timestamp)) * 31;
            String str = this.latex;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            return "RenderItem(mathBlockId=" + this.mathBlockId + ", params=" + this.params + ", timestamp=" + this.timestamp + ", latex=" + this.latex + ")";
        }

        public RenderItem(String mathBlockId, RenderParams params, long j, String str) {
            Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
            Intrinsics.checkNotNullParameter(params, "params");
            this.mathBlockId = mathBlockId;
            this.params = params;
            this.timestamp = j;
            this.latex = str;
        }

        public final String getMathBlockId() {
            return this.mathBlockId;
        }

        public final RenderParams getParams() {
            return this.params;
        }

        public /* synthetic */ RenderItem(String str, RenderParams renderParams, long j, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, renderParams, (i & 4) != 0 ? System.currentTimeMillis() : j, (i & 8) != 0 ? null : str2);
        }

        public final long getTimestamp() {
            return this.timestamp;
        }

        public final String getLatex() {
            return this.latex;
        }
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$FormulaLatex;", "", "latexString", "", "resultRectF", "Landroid/graphics/RectF;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Landroid/graphics/RectF;)V", "getLatexString", "()Ljava/lang/String;", "getResultRectF", "()Landroid/graphics/RectF;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class FormulaLatex {
        private final String latexString;
        private final RectF resultRectF;

        public static /* synthetic */ FormulaLatex copy$default(FormulaLatex formulaLatex, String str, RectF rectF, int i, Object obj) {
            if ((i & 1) != 0) {
                str = formulaLatex.latexString;
            }
            if ((i & 2) != 0) {
                rectF = formulaLatex.resultRectF;
            }
            return formulaLatex.copy(str, rectF);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getLatexString() {
            return this.latexString;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final RectF getResultRectF() {
            return this.resultRectF;
        }

        public final FormulaLatex copy(String latexString, RectF resultRectF) {
            Intrinsics.checkNotNullParameter(latexString, "latexString");
            Intrinsics.checkNotNullParameter(resultRectF, "resultRectF");
            return new FormulaLatex(latexString, resultRectF);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FormulaLatex)) {
                return false;
            }
            FormulaLatex formulaLatex = (FormulaLatex) other;
            return Intrinsics.areEqual(this.latexString, formulaLatex.latexString) && Intrinsics.areEqual(this.resultRectF, formulaLatex.resultRectF);
        }

        public int hashCode() {
            return (this.latexString.hashCode() * 31) + this.resultRectF.hashCode();
        }

        public String toString() {
            return "FormulaLatex(latexString=" + this.latexString + ", resultRectF=" + this.resultRectF + ")";
        }

        public FormulaLatex(String latexString, RectF resultRectF) {
            Intrinsics.checkNotNullParameter(latexString, "latexString");
            Intrinsics.checkNotNullParameter(resultRectF, "resultRectF");
            this.latexString = latexString;
            this.resultRectF = resultRectF;
        }

        public final String getLatexString() {
            return this.latexString;
        }

        public final RectF getResultRectF() {
            return this.resultRectF;
        }
    }

    public final void setSaveBtnClicked(boolean clicked) {
        this.isSaveBtnClicked = clicked;
    }

    public final void setRenderingEnabled(boolean enabled) {
        this.isRenderingEnabled = enabled;
        Log.d(TAG, "MathSystem rendering ".concat(enabled ? "enabled" : "disabled"));
    }

    /* JADX INFO: renamed from: isRenderingEnabled, reason: from getter */
    public final boolean getIsRenderingEnabled() {
        return this.isRenderingEnabled;
    }

    /* JADX INFO: renamed from: getisSaveBtnClicked, reason: from getter */
    public final boolean getIsSaveBtnClicked() {
        return this.isSaveBtnClicked;
    }

    public final RecognitionModel getRecognitionModel() {
        return getCurveEngine().getRecognitionModel();
    }

    public final MiCurveEngine getTheCurveEngine() {
        return getCurveEngine();
    }

    public final void cancelAnimation() {
        if (this.doAnimator) {
            Log.d(TAG, "cancelMathAnimation");
            XCAnimator xCAnimator = this.scannerAnimator;
            if (xCAnimator != null) {
                xCAnimator.cancel();
            }
        }
    }

    public final void resetSaveBtnClicked() {
        this.isSaveBtnClicked = false;
        Log.d(TAG, "resetSaveBtnClicked:  isSaveBtnClicked = false");
    }

    public final void setPageInteracting(boolean interacting) {
        String str = TAG;
        Log.d(str, "setPageInteracting: " + interacting);
        this.isPageInteracting = interacting;
        if (interacting && this.doAnimator) {
            Log.d(str, "setPageInteracting: stopping running animation");
            XCAnimator xCAnimator = this.scannerAnimator;
            if (xCAnimator != null) {
                xCAnimator.cancel();
            }
            moveCurveToRenderList();
        }
    }

    public final void setPageLoading(boolean loading) {
        this.isPageLoading = loading;
        if (loading) {
            this.isFromRefresh = true;
            XCAnimator xCAnimator = this.scannerAnimator;
            if (xCAnimator != null) {
                xCAnimator.cancel();
            }
            moveCurveToRenderList();
            return;
        }
        this.isFromRefresh = false;
    }

    public final void setFromUndoRedo(boolean fromUndoRedo) {
        this.isFromUndoRedo = fromUndoRedo;
        if (fromUndoRedo && this.doAnimator) {
            XCAnimator xCAnimator = this.scannerAnimator;
            if (xCAnimator != null) {
                xCAnimator.cancel();
            }
            moveCurveToRenderList();
        }
    }

    public final void setHasJustCleared(boolean cleared) {
        this.hasJustCleared = cleared;
    }

    /* JADX INFO: renamed from: hasJustCleared, reason: from getter */
    public final boolean getHasJustCleared() {
        return this.hasJustCleared;
    }

    public final void finishEdit() {
        getCurveEngine().getTextSystem().markDirty();
    }

    private final boolean shouldPlayAnimation() {
        if (this.isPageInteracting) {
            Log.d(TAG, "shouldPlayAnimation: false (page interacting)");
            return false;
        }
        if (this.isPageLoading) {
            Log.d(TAG, "shouldPlayAnimation: false (page loading)");
            return false;
        }
        if (this.isFromUndoRedo) {
            return false;
        }
        Log.d(TAG, "shouldPlayAnimation: true");
        return true;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        setInstanceMathResultTag("MathSystem" + hashCode());
        MATH_RESULT_TAG = getInstanceMathResultTag();
        this.digitalRenderer = new DigitalStrokeRenderer(getCurveEngine());
        getCurveEngine().getMathDataManager().registerMathResultCallback(getInstanceMathResultTag(), this);
        if (FeatureManager.getInstance().isEnableMathRecognition()) {
            initAnimator();
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        updateTextureSize();
        initializeRenderTarget();
    }

    public final void checkRenderTarget() {
        if (this.renderTargetSizeChanged) {
            initializeRenderTarget();
            this.renderTargetSizeChanged = false;
        }
    }

    private final void initAnimator() {
        this.scannerAnimator = new XCAnimator(getCurveEngine().getMAnimatorHandle()).setDuration(ANIMATION_DURATION).addUpdateListener(new XCAnimator.XCAnimatorListener() { // from class: xc.engine.core.graphics.engine.systems.MathSystem.initAnimator.1
            @Override // xc.engine.core.fast.androidx.graphics.XCAnimator.XCAnimatorListener
            public void onAnimationStart(float passTime) {
                MathSystem.this.preStartAnimation();
                MathSystem.this.doAnimator = true;
                MathSystem.this.isAnimatorFirstFrame = true;
                Log.d(MathSystem.TAG, "onAnimationStart: " + passTime);
                MiCurveEngine.requestMultiBufferRender$default(MathSystem.this.getCurveEngine(), null, 1, null);
            }

            @Override // xc.engine.core.fast.androidx.graphics.XCAnimator.XCAnimatorListener
            public void onAnimationUpdate(float passTime) {
                MathSystem.this.scannerEffectInfo.setCurrentProcess(MiMathUtils.INSTANCE.smoothEaseSinInOut(MathSystem.this.scannerEffectInfo.getStartProcess(), MathSystem.this.scannerEffectInfo.getEndProcess(), passTime / MathSystem.ANIMATION_DURATION));
                MiCurveEngine.requestMultiBufferRender$default(MathSystem.this.getCurveEngine(), null, 1, null);
            }

            @Override // xc.engine.core.fast.androidx.graphics.XCAnimator.XCAnimatorListener
            public void onAnimationCancel(float passTime) {
                MathSystem.this.doAnimator = false;
                MathSystem.this.getMathAnimatorPropInfo().setOrder(!MathSystem.this.getMathAnimatorPropInfo().getIsOrder());
                MathSystem.this.getMathAnimatorPropInfo().clearCurvesCache();
                MathSystem.this.moveCurveToRenderList();
                MiCurveEngine.requestMultiBufferRender$default(MathSystem.this.getCurveEngine(), null, 1, null);
                Log.d(MathSystem.TAG, "onAnimationCancel");
            }

            @Override // xc.engine.core.fast.androidx.graphics.XCAnimator.XCAnimatorListener
            public void onAnimationEnd(float passTime) {
                MathSystem.this.doAnimator = false;
                MathSystem.this.getMathAnimatorPropInfo().setOrder(!MathSystem.this.getMathAnimatorPropInfo().getIsOrder());
                MathSystem.this.getMathAnimatorPropInfo().clearCurvesCache();
                MathSystem.this.scannerEffectInfo.setCurrentProcess(MiMathUtils.INSTANCE.smoothEaseSinInOut(MathSystem.this.scannerEffectInfo.getStartProcess(), MathSystem.this.scannerEffectInfo.getEndProcess(), passTime / MathSystem.ANIMATION_DURATION));
                Log.d(MathSystem.TAG, "onAnimationEnd: currentProcess:" + MathSystem.this.scannerEffectInfo.getCurrentProcess() + " endProcess:" + MathSystem.this.scannerEffectInfo.getEndProcess());
                MiCurveEngine.requestMultiBufferRender$default(MathSystem.this.getCurveEngine(), null, 1, null);
            }
        });
    }

    public final void getCurveProp() {
        this.curvePropData = getCurveEngine().getPenEngine().mCurveRecognizeManager.getcurentCurveProp();
    }

    private final void destroyRenderTarget() {
        if (this.mainTextureId > 0) {
            GLUtils.INSTANCE.deleteTexture(this.mainTextureId);
            this.mainTextureId = 0;
        }
        if (this.tmpTextureId > 0) {
            GLUtils.INSTANCE.deleteTexture(this.tmpTextureId);
            this.tmpTextureId = 0;
        }
        if (this.aniTextureId > 0) {
            GLUtils.INSTANCE.deleteTexture(this.aniTextureId);
            this.aniTextureId = 0;
        }
        int i = this.mainFramebufferId;
        if (i > 0) {
            GLES30.glDeleteFramebuffers(1, new int[]{i}, 0);
            this.mainFramebufferId = 0;
        }
        int i2 = this.tmpFramebufferId;
        if (i2 > 0) {
            GLES30.glDeleteFramebuffers(1, new int[]{i2}, 0);
            this.tmpFramebufferId = 0;
        }
        int i3 = this.aniFramebufferId;
        if (i3 > 0) {
            GLES30.glDeleteFramebuffers(1, new int[]{i3}, 0);
            this.aniFramebufferId = 0;
        }
    }

    private final void createAndInitMainRenderTarget() {
        this.mainTextureId = GLUtils.INSTANCE.createTexture(this.RT_WIDTH, this.RT_HEIGHT);
        this.tmpTextureId = GLUtils.INSTANCE.createTexture(this.RT_WIDTH, this.RT_HEIGHT);
        this.mainFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.mainTextureId);
        this.tmpFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.tmpTextureId);
        GLES30.glBindFramebuffer(36160, this.mainFramebufferId);
        clearColor();
    }

    private final void createAndInitAnimationRenderTarget() {
        this.aniTextureId = GLUtils.INSTANCE.createTexture(this.RT_WIDTH, this.RT_HEIGHT * 2);
        int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(this.aniTextureId);
        this.aniFramebufferId = iCreateFramebufferFromTexture;
        GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
        clearColor();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        destroyRenderTarget();
        getCurveEngine().getMathDataManager().removeMathResultCallback(getInstanceMathResultTag());
    }

    private final void updateTextureSize() {
        int screenWidthInPixel = getCurveEngine().getScreenWidthInPixel() / 2;
        int screenHeightInPixel = getCurveEngine().getScreenHeightInPixel() / 2;
        XLog.v(TAG, "updateTextureSize [%d/%d] to [%d/%d]", Integer.valueOf(this.RT_WIDTH), Integer.valueOf(this.RT_HEIGHT), Integer.valueOf(screenWidthInPixel), Integer.valueOf(screenHeightInPixel));
        this.RT_WIDTH = screenWidthInPixel;
        this.RT_HEIGHT = screenHeightInPixel;
        this.renderTargetSizeChanged = true;
    }

    private final void initializeRenderTarget() {
        destroyRenderTarget();
        createAndInitMainRenderTarget();
        createAndInitAnimationRenderTarget();
    }

    public final void render(RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        if (!this.isRenderingEnabled) {
            Log.d(TAG, "MathSystem rendering is disabled, skipping render");
            return;
        }
        if (hasRenderContent()) {
            this.mScreenRect = screenRect;
            this.screenRectFlip.set(screenRect.left, screenRect.bottom, screenRect.right, screenRect.top);
            renderAllItems();
            GLES30.glEnable(R2.color.audio_share_time_color_green);
            GLES30.glBlendEquationSeparate(32774, 32774);
            GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
            if (this.doAnimator) {
                if (this.isAnimatorFirstFrame) {
                    genOrUpdateAniTexture();
                    this.isAnimatorFirstFrame = false;
                }
                animatorRender();
            }
        }
    }

    private final void renderAllItems() {
        List<RenderItem> list;
        checkRenderTarget();
        GLES30.glBindFramebuffer(36160, this.mainFramebufferId);
        GLES30.glViewport(0, 0, this.RT_WIDTH, this.RT_HEIGHT);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        RectF rectF = this.mScreenRect;
        if (rectF == null) {
            return;
        }
        synchronized (this.renderItemsLock) {
            list = CollectionsKt.toList(this.renderItemsMap.values());
        }
        list.size();
        for (RenderItem renderItem : list) {
            if (RectF.intersects(calculateItemBoundingBox(renderItem.getParams()), rectF)) {
                renderSingleItem(renderItem.getParams(), rectF, this.screenRectFlip);
            }
        }
    }

    private final RectF calculateItemBoundingBox(RenderParams params) {
        float digitHeight = params.getDigitHeight();
        String text = params.getText();
        int length = text.length();
        float spacing = 0.0f;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == 'e') {
                spacing += (params.getDigitWidth() / 1.5f) + params.getSpacing();
                digitHeight = Math.max(digitHeight, params.getDigitHeight() / 2.0f);
            } else {
                spacing += params.getDigitWidth() + params.getSpacing();
            }
        }
        if (params.getText().length() > 0) {
            spacing -= params.getSpacing();
        }
        return new RectF(params.getStartX(), params.getStartY(), params.getStartX() + spacing, params.getStartY() + digitHeight);
    }

    public final void onScreenResize(float radio) {
        int i = 0;
        for (Object obj : this.renderItemsMap.values()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            RenderItem renderItem = (RenderItem) obj;
            RenderParams params = renderItem.getParams();
            params.setStartX(params.getStartX() * radio);
            RenderParams params2 = renderItem.getParams();
            params2.setStartY(params2.getStartY() * radio);
            RenderParams params3 = renderItem.getParams();
            params3.setDigitWidth(params3.getDigitWidth() * radio);
            RenderParams params4 = renderItem.getParams();
            params4.setDigitHeight(params4.getDigitHeight() * radio);
            i = i2;
        }
    }

    private final List<DataCurve> generateMathResultCurves(RenderParams params) {
        List<DataCurve> listCreateDigitCurves;
        float startX = params.getStartX();
        ArrayList arrayList = new ArrayList();
        String text = params.getText();
        int length = text.length();
        float digitWidth = startX;
        for (int i = 0; i < length; i++) {
            char cCharAt = text.charAt(i);
            if (cCharAt == 'e') {
                float digitHeight = params.getDigitHeight() / 2.0f;
                float digitWidth2 = params.getDigitWidth() / 1.5f;
                float startY = params.getStartY() + digitHeight;
                DigitalStrokeRenderer digitalStrokeRenderer = this.digitalRenderer;
                listCreateDigitCurves = digitalStrokeRenderer != null ? digitalStrokeRenderer.createDigitCurves(String.valueOf(cCharAt), digitWidth, startY, digitWidth2, digitHeight, params.getStrokeWidth(), params.getCurveProp()) : null;
                digitWidth += digitWidth2 + params.getSpacing();
                if (listCreateDigitCurves != null) {
                    arrayList.addAll(listCreateDigitCurves);
                }
            } else {
                DigitalStrokeRenderer digitalStrokeRenderer2 = this.digitalRenderer;
                listCreateDigitCurves = digitalStrokeRenderer2 != null ? digitalStrokeRenderer2.createDigitCurves(String.valueOf(cCharAt), digitWidth, params.getStartY(), params.getDigitWidth(), params.getDigitHeight(), params.getStrokeWidth(), params.getCurveProp()) : null;
                digitWidth += params.getDigitWidth() + params.getSpacing();
                if (listCreateDigitCurves != null) {
                    arrayList.addAll(listCreateDigitCurves);
                }
            }
        }
        return arrayList;
    }

    private final void renderSingleItem(RenderParams params, RectF screenRect, RectF nScreenRect) {
        RectF rectF = screenRect;
        List<DataCurve> listGenerateMathResultCurves = generateMathResultCurves(params);
        if (listGenerateMathResultCurves.isEmpty()) {
            return;
        }
        float fWidth = this.RT_WIDTH / rectF.width();
        long jNativeGenCurveVertexBuffer = 0;
        for (DataCurve dataCurve : listGenerateMathResultCurves) {
            dataCurve.isMathResult = true;
            Object mLockObj = dataCurve.mLockObj;
            Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
            synchronized (mLockObj) {
                jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve.mSavePoints, new float[]{dataCurve.getCurveProp().getPenType().getValue(), dataCurve.getCurveProp().getPenSize(), dataCurve.getDrawRatio(), fWidth, rectF.left, rectF.top, rectF.right, rectF.bottom, dataCurve.getSensitivity(), dataCurve.getTipSize()}, jNativeGenCurveVertexBuffer, jNativeGenCurveVertexBuffer != 0);
                Unit unit = Unit.INSTANCE;
            }
            rectF = screenRect;
        }
        CurveRenderer.renderCurve$default(getCurveEngine().getMathCurveRenderer(), (DataCurve) CollectionsKt.first((List) listGenerateMathResultCurves), nScreenRect, this.mainFramebufferId, this.tmpFramebufferId, this.tmpTextureId, fWidth, jNativeGenCurveVertexBuffer, false, 128, null);
    }

    private final RenderParams createRenderParamsFromMathResult(MathResult mathResult) {
        BoundingBox boundingBox = mathResult.getBoundingBox();
        String result = mathResult.getResult();
        float height = boundingBox.getHeight();
        return new RenderParams(result, boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), height, 0.1f, 0.1f, "MathResult", getCurvePropForMathBlock(mathResult.getMathBlockId()));
    }

    private final CurveProp getCurvePropForMathBlock(String mathBlockId) {
        CurveProp curvePropClone;
        CurveProp curvePropByStrokeId;
        synchronized (this.curvePropLock) {
            CurveProp curveProp = this.mathBlockCurvePropMap.get(mathBlockId);
            if (curveProp != null) {
                Log.d(TAG, "getCurvePropForMathBlock: found cached curveProp for " + mathBlockId + ", penType=" + curveProp.getPenType() + ", penSize=" + curveProp.getPenSize());
                return curveProp;
            }
            Unit unit = Unit.INSTANCE;
            IItemIdHelper itemIdHelper = getCurveEngine().getRecognitionModel().getItemIdHelper();
            String[] itemsByBlockId = itemIdHelper != null ? itemIdHelper.getItemsByBlockId(mathBlockId) : null;
            if (itemsByBlockId != null && itemsByBlockId.length != 0) {
                Log.d(TAG, "mCurveRecognizeManager.getCurvePropByStrokeId()");
                String str = (String) ArraysKt.lastOrNull(itemsByBlockId);
                if (str != null && (curvePropByStrokeId = getCurveEngine().getPenEngine().mCurveRecognizeManager.getCurvePropByStrokeId(str)) != null) {
                    synchronized (this.curvePropLock) {
                        this.mathBlockCurvePropMap.put(mathBlockId, curvePropByStrokeId);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    return curvePropByStrokeId;
                }
            }
            getCurveProp();
            try {
                CurveProp curveProp2 = this.curvePropData;
                curvePropClone = curveProp2 != null ? curveProp2.clone() : null;
            } catch (Exception unused) {
                curvePropClone = this.curvePropData;
            }
            if (curvePropClone != null) {
                synchronized (this.curvePropLock) {
                    this.mathBlockCurvePropMap.put(mathBlockId, curvePropClone);
                    Unit unit3 = Unit.INSTANCE;
                }
                return curvePropClone;
            }
            Log.w(TAG, "getCurvePropForMathBlock: no curveProp found for " + mathBlockId + ", skipping rendering");
            return null;
        }
    }

    public final void updateCurvePropForMathBlock(String mathBlockId, String[] itemIDs) {
        RenderItem renderItem;
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        if (itemIDs == null || itemIDs.length == 0) {
            Log.w(TAG, "updateCurvePropForMathBlock: itemIDs is null or empty for mathBlockId=" + mathBlockId);
            return;
        }
        String str = (String) ArraysKt.lastOrNull(itemIDs);
        if (str != null) {
            CurveProp curvePropByStrokeId = getCurveEngine().getPenEngine().mCurveRecognizeManager.getCurvePropByStrokeId(str);
            if (curvePropByStrokeId != null) {
                synchronized (this.curvePropLock) {
                    this.mathBlockCurvePropMap.put(mathBlockId, curvePropByStrokeId);
                    Unit unit = Unit.INSTANCE;
                }
                String str2 = TAG;
                Log.d(str2, "updateCurvePropForMathBlock: cached curveProp for mathBlockId=" + mathBlockId + ", penType=" + curvePropByStrokeId.getPenType() + ", penSize=" + curvePropByStrokeId.getPenSize() + ", penColor=" + curvePropByStrokeId.getPenColor() + ", strokeId=" + str);
                notifyCurvePropSyncCallbacks(mathBlockId);
                synchronized (this.renderItemsLock) {
                    renderItem = this.renderItemsMap.get(mathBlockId);
                }
                if (renderItem != null) {
                    RenderItem renderItemCopy$default = RenderItem.copy$default(renderItem, null, RenderParams.copy$default(renderItem.getParams(), null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, curvePropByStrokeId, 255, null), 0L, null, 13, null);
                    synchronized (this.renderItemsLock) {
                        this.renderItemsMap.put(mathBlockId, renderItemCopy$default);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    Log.d(str2, "updateCurvePropForMathBlock: updated renderItem for math block " + mathBlockId + " with strokeId " + str);
                    MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
                    return;
                }
                Log.d(str2, "updateCurvePropForMathBlock: cached curveProp for math block " + mathBlockId + " (no render item yet, will be used when renderItem created)");
                return;
            }
            Log.w(TAG, "updateCurvePropForMathBlock: getCurvePropByStrokeId returned null for strokeId=" + str + ", mathBlockId=" + mathBlockId);
            return;
        }
        String str3 = TAG;
        String string = Arrays.toString(itemIDs);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Log.w(str3, "updateCurvePropForMathBlock: lastStrokeId is null for mathBlockId=" + mathBlockId + ", itemIDs=" + string);
    }

    public final boolean hasCurvePropForMathBlock(String mathBlockId) {
        boolean zContainsKey;
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        synchronized (this.curvePropLock) {
            zContainsKey = this.mathBlockCurvePropMap.containsKey(mathBlockId);
        }
        return zContainsKey;
    }

    public final boolean registerCurvePropSyncCallback(String mathBlockId, CurvePropSyncCallback callback) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.pendingCallbacksLock) {
            if (hasCurvePropForMathBlock(mathBlockId)) {
                Log.d(TAG, "registerCurvePropSyncCallback: curveProp already synced for " + mathBlockId + ", calling callback immediately");
                callback.onCurvePropSynced(mathBlockId);
                return true;
            }
            Map<String, List<CurvePropSyncCallback>> map = this.pendingCurvePropSyncCallbacks;
            ArrayList arrayList = map.get(mathBlockId);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(mathBlockId, arrayList);
            }
            arrayList.add(callback);
            Log.d(TAG, "registerCurvePropSyncCallback: registered callback for " + mathBlockId + ", waiting for sync");
            return false;
        }
    }

    public final void unregisterCurvePropSyncCallback(String mathBlockId, CurvePropSyncCallback callback) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.pendingCallbacksLock) {
            List<CurvePropSyncCallback> list = this.pendingCurvePropSyncCallbacks.get(mathBlockId);
            if (list != null) {
                list.remove(callback);
            }
            List<CurvePropSyncCallback> list2 = this.pendingCurvePropSyncCallbacks.get(mathBlockId);
            if (list2 != null && list2.isEmpty()) {
                this.pendingCurvePropSyncCallbacks.remove(mathBlockId);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void notifyCurvePropSyncCallbacks(String mathBlockId) {
        synchronized (this.pendingCallbacksLock) {
            List<CurvePropSyncCallback> listRemove = this.pendingCurvePropSyncCallbacks.remove(mathBlockId);
            if (listRemove != null && !listRemove.isEmpty()) {
                Log.d(TAG, "notifyCurvePropSyncCallbacks: notifying " + listRemove.size() + " callbacks for " + mathBlockId);
                Iterator<T> it = listRemove.iterator();
                while (it.hasNext()) {
                    try {
                        ((CurvePropSyncCallback) it.next()).onCurvePropSynced(mathBlockId);
                    } catch (Exception e) {
                        Log.e(TAG, "notifyCurvePropSyncCallbacks: error calling callback for " + mathBlockId, e);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public static /* synthetic */ boolean waitForCurvePropSync$default(MathSystem mathSystem, String str, long j, long j2, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 500;
        }
        long j3 = j;
        if ((i & 4) != 0) {
            j2 = 20;
        }
        return mathSystem.waitForCurvePropSync(str, j3, j2);
    }

    public final boolean waitForCurvePropSync(String mathBlockId, long maxWaitTime, long checkInterval) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        int i = (int) (maxWaitTime / checkInterval);
        for (int i2 = 0; i2 < i; i2++) {
            if (hasCurvePropForMathBlock(mathBlockId)) {
                Log.d(TAG, "waitForCurvePropSync: curveProp synced for " + mathBlockId + " after " + (((long) i2) * checkInterval) + "ms");
                return true;
            }
            if (i2 < i - 1) {
                try {
                    Thread.sleep(checkInterval);
                } catch (InterruptedException unused) {
                    Log.w(TAG, "waitForCurvePropSync: interrupted while waiting for " + mathBlockId);
                    return false;
                }
            }
        }
        Log.w(TAG, "waitForCurvePropSync: curveProp not synced for " + mathBlockId + " after " + maxWaitTime + "ms");
        return false;
    }

    public final void syncCurvePropForMathBlock(String mathBlockId, CurveProp curveProp) {
        RenderItem renderItem;
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        Intrinsics.checkNotNullParameter(curveProp, "curveProp");
        if (mathBlockId.length() == 0) {
            Log.w(TAG, "syncCurvePropForMathBlock: invalid params, mathBlockId=" + mathBlockId + ", curveProp=" + curveProp);
            return;
        }
        try {
            synchronized (this.curvePropLock) {
                this.mathBlockCurvePropMap.put(mathBlockId, curveProp);
                Unit unit = Unit.INSTANCE;
            }
            String str = TAG;
            Log.d(str, "syncCurvePropForMathBlock: cached curveProp for mathBlockId=" + mathBlockId + ", penType=" + curveProp.getPenType() + ", penSize=" + curveProp.getPenSize() + ", penColor=" + curveProp.getPenColor());
            notifyCurvePropSyncCallbacks(mathBlockId);
            synchronized (this.renderItemsLock) {
                renderItem = this.renderItemsMap.get(mathBlockId);
            }
            if (renderItem != null) {
                RenderItem renderItemCopy$default = RenderItem.copy$default(renderItem, null, RenderParams.copy$default(renderItem.getParams(), null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, curveProp, 255, null), 0L, null, 13, null);
                synchronized (this.renderItemsLock) {
                    this.renderItemsMap.put(mathBlockId, renderItemCopy$default);
                    Unit unit2 = Unit.INSTANCE;
                }
                Log.d(str, "syncCurvePropForMathBlock: updated renderItem for math block " + mathBlockId + " from ent file");
                MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
                return;
            }
            Log.d(str, "syncCurvePropForMathBlock: cached curveProp for math block " + mathBlockId + " (no render item yet, will be used when renderItem created)");
        } catch (Exception e) {
            Log.e(TAG, "syncCurvePropForMathBlock: error syncing curve prop for math block " + mathBlockId, e);
        }
    }

    private final void animatorRender() {
        getCurveEngine().getScanEffectRenderer().updateEffect(this.mainFramebufferId, this.aniTextureId, this.scannerEffectInfo);
    }

    private final void clearColor() {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
    }

    private final void genOrUpdateAniTexture() {
        String str = TAG;
        Log.d(str, "=== genOrUpdateAniTexture START ===");
        renderMathOnTexture();
        updateTextureChannel();
        initScanEffectProp();
        Log.d(str, "=== genOrUpdateAniTexture END ===");
    }

    private final float getMathResultScaleFactor(DataCurve curve) {
        PenType penType = curve.getCurveProp().getPenType();
        int i = penType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[penType.ordinal()];
        return (i == 1 || i == 2) ? 1.8f : 1.0f;
    }

    private final void renderMathOnTexture() {
        GLES30.glBindFramebuffer(36160, this.aniFramebufferId);
        if (this.mathAnimatorPropInfo.getIsOrder()) {
            int i = this.RT_HEIGHT;
            GLES30.glViewport(0, i, this.RT_WIDTH, i);
        } else {
            GLES30.glViewport(0, 0, this.RT_WIDTH, this.RT_HEIGHT);
        }
        clearColor();
        for (DataCurve dataCurve : this.mathAnimatorPropInfo.getCachedCurves()) {
            if (dataCurve.getType() == 1) {
                try {
                    float penSizeScaleInPage = this.mathAnimatorPropInfo.getPenSizeScaleInPage();
                    if (dataCurve.isMathResult) {
                        penSizeScaleInPage *= 1.05f;
                    }
                    float f = penSizeScaleInPage;
                    ScannerEffectInfo scannerEffectInfo = this.scannerEffectInfo;
                    scannerEffectInfo.setBounderSize(Math.max(scannerEffectInfo.getBounderSize(), (int) (dataCurve.getCurveProp().getPenSize() * dataCurve.getDrawRatio() * f)));
                    getCurveEngine().getCurveRenderer().renderMathCurve(this.aniFramebufferId, dataCurve, this.mathAnimatorPropInfo.getScreenRectInCurveCoordFlip(), f, getCurveEngine().getCurveRenderer().nativeGenerateVertexHandle(dataCurve, dataCurve.mOriginPoints, this.mathAnimatorPropInfo.getScreenRectInCurveCoord()), 1.0f);
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e) {
                    Integer.valueOf(Log.e(TAG, "DEBUG: Error rendering curve " + dataCurve.strokeId + ": " + e.getMessage(), e));
                }
            } else {
                Log.v(TAG, "DEBUG: Skipping curve " + dataCurve.strokeId + " - not CURVE type (getType=" + ((int) dataCurve.getType()) + ", expected=1)");
            }
        }
        Log.d(TAG, "=== renderMathOnTexture END ===");
    }

    public final RectF calculateNormalizedRect(RectF rectA, RectF rectB) {
        Intrinsics.checkNotNullParameter(rectA, "rectA");
        Intrinsics.checkNotNullParameter(rectB, "rectB");
        return new RectF(normalizeValue(rectA.left, rectB.left, rectB.right), normalizeValue(rectA.top, rectB.top, rectB.bottom), normalizeValue(rectA.right, rectB.left, rectB.right), normalizeValue(rectA.bottom, rectB.top, rectB.bottom));
    }

    private final void calculateNormalizedBoundingBox(RectF res, BoundingBox rectA, RectF rectB, int width, int height) {
        float fNormalizeValue = normalizeValue(rectA.getX(), rectB.left, rectB.right);
        float fNormalizeValue2 = normalizeValue(rectA.getX() + rectA.getWidth(), rectB.left, rectB.right);
        float fNormalizeValue3 = normalizeValue(rectA.getY(), rectB.top, rectB.bottom);
        float fNormalizeValue4 = normalizeValue(rectA.getY() + rectA.getHeight(), rectB.top, rectB.bottom);
        float f = width;
        float f2 = height;
        res.set(fNormalizeValue * f, fNormalizeValue3 * f2, fNormalizeValue2 * f, fNormalizeValue4 * f2);
    }

    private final void initScanEffectProp() {
        this.scannerEffectInfo.setShadowL(((getCurveEngine().getPenEngine().mDensity * 62) / this.RT_WIDTH) * 0.5f);
        this.scannerEffectInfo.setShadowR(((getCurveEngine().getPenEngine().mDensity * 12) / this.RT_WIDTH) * 0.5f);
        this.scannerEffectInfo.setShowWidth2(((getCurveEngine().getPenEngine().mDensity * 26) / this.RT_WIDTH) * 0.5f);
        int bounderSize = this.scannerEffectInfo.getBounderSize() + (hasInkPenCurve() ? (int) (this.scannerEffectInfo.getBounderSize() * 0.6f) : 0);
        float f = bounderSize;
        this.scannerEffectInfo.getMathRect().left -= f;
        this.scannerEffectInfo.getMathRect().right += f;
        this.scannerEffectInfo.getMathRect().top -= f;
        this.scannerEffectInfo.getMathRect().bottom += f;
        float fWidth = this.scannerEffectInfo.getMathRect().width() / this.RT_WIDTH;
        float shadowL = this.scannerEffectInfo.getShadowL() + this.scannerEffectInfo.getShadowR() + this.scannerEffectInfo.getShowWidth2();
        this.scannerEffectInfo.setShowWidth1(Math.max(fWidth - shadowL, 0.0f));
        ScannerEffectInfo scannerEffectInfo = this.scannerEffectInfo;
        scannerEffectInfo.setStartProcess(scannerEffectInfo.getMathRect().left / this.RT_WIDTH);
        ScannerEffectInfo scannerEffectInfo2 = this.scannerEffectInfo;
        scannerEffectInfo2.setEndProcess(scannerEffectInfo2.getStartProcess() + fWidth + shadowL + this.scannerEffectInfo.getShowWidth1());
        ScannerEffectInfo scannerEffectInfo3 = this.scannerEffectInfo;
        scannerEffectInfo3.setResultEndProcess(scannerEffectInfo3.getStartProcess() + fWidth);
        ScannerEffectInfo scannerEffectInfo4 = this.scannerEffectInfo;
        scannerEffectInfo4.setCurrentProcess(scannerEffectInfo4.getStartProcess());
    }

    private final boolean hasInkPenCurve() {
        List<DataCurve> cachedCurves = this.mathAnimatorPropInfo.getCachedCurves();
        if ((cachedCurves instanceof Collection) && cachedCurves.isEmpty()) {
            return false;
        }
        for (DataCurve dataCurve : cachedCurves) {
            if (dataCurve.getType() == 1) {
                CurveProp curveProp = dataCurve.getCurveProp();
                if ((curveProp != null ? curveProp.getPenType() : null) == PenType.INK) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preStartAnimation() {
        RenderParams params;
        MathResult mathResult = this.mathAnimatorPropInfo.getMathResult();
        if (mathResult == null) {
            return;
        }
        RectF screenAreaWS = this.mScreenRect;
        if (screenAreaWS == null) {
            screenAreaWS = getCurveEngine().getScreenAreaWS();
        }
        RectF rectF = screenAreaWS;
        this.mathAnimatorPropInfo.setPenSizeScaleInPage(this.RT_WIDTH / rectF.width());
        BoundingBox boundingBox = new BoundingBox(mathResult.getBoundingBox().getX(), mathResult.getBoundingBox().getY(), mathResult.getBoundingBox().getWidth() * mathResult.getResult().length(), mathResult.getBoundingBox().getHeight());
        calculateNormalizedBoundingBox(this.scannerEffectInfo.getMathRect(), new BoundingBox(mathResult.getFormulaBox().getX(), mathResult.getFormulaBox().getY(), (boundingBox.getX() - mathResult.getFormulaBox().getX()) + boundingBox.getWidth(), mathResult.getFormulaBox().getHeight() + boundingBox.getHeight()), rectF, this.RT_WIDTH, this.RT_HEIGHT);
        calculateNormalizedBoundingBox(this.mathAnimatorPropInfo.getMathBackPartRect(), boundingBox, rectF, this.RT_WIDTH, this.RT_HEIGHT);
        float drawRatio = 1.0f / getCurveEngine().getDrawRatio();
        BoundingBox formulaBox = mathResult.getFormulaBox();
        this.mathAnimatorPropInfo.getSavaCurveRect().set(formulaBox.getX() * drawRatio, formulaBox.getY() * drawRatio, (formulaBox.getX() + formulaBox.getWidth()) * drawRatio, (formulaBox.getY() + formulaBox.getHeight()) * drawRatio);
        this.mathAnimatorPropInfo.getScreenRectInCurveCoord().set(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.mathAnimatorPropInfo.getScreenRectInCurveCoordFlip().set(rectF.left, rectF.bottom, rectF.right, rectF.top);
        List<? extends DataCurve> mutableList = CollectionsKt.toMutableList((Collection) getFormulaCurves(mathResult));
        RenderItem renderItem = this.prepareRenderItemsMapForAnimation.get(mathResult.getMathBlockId());
        if (renderItem != null && (params = renderItem.getParams()) != null) {
            List<DataCurve> listGenerateMathResultCurves = generateMathResultCurves(params);
            Iterator<DataCurve> it = listGenerateMathResultCurves.iterator();
            while (it.hasNext()) {
                it.next().isMathResult = true;
            }
            mutableList.addAll(listGenerateMathResultCurves);
        }
        if (this.mathAnimatorPropInfo.needsCurvesUpdate(mutableList)) {
            this.mathAnimatorPropInfo.updateCurvesCache(mutableList);
        } else {
            Log.d(TAG, "DEBUG: Curves cache not updated (no changes)");
        }
    }

    static /* synthetic */ void startAnimator$default(MathSystem mathSystem, MathResult mathResult, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        mathSystem.startAnimator(mathResult, z);
    }

    private final void startAnimator(MathResult mathResult, boolean start) {
        if (start && FeatureManager.getInstance().isEnableMathRecognition()) {
            this.mathAnimatorPropInfo.setMathResult(mathResult);
            if (this.mathAnimatorPropInfo.getMathResult() == null) {
                return;
            }
            if (shouldPlayAnimation()) {
                XCAnimator xCAnimator = this.scannerAnimator;
                if (xCAnimator != null) {
                    xCAnimator.start();
                    return;
                }
                return;
            }
            this.doAnimator = false;
        }
    }

    private final String extractNumericIdFromMathBlockId(String mathBlockId) {
        String str = mathBlockId;
        if (str.length() == 0) {
            return null;
        }
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) InternalZipConstants.ZIP_FILE_SEPARATOR, false, 2, (Object) null)) {
            mathBlockId = mathBlockId.substring(StringsKt.lastIndexOf$default((CharSequence) str, InternalZipConstants.ZIP_FILE_SEPARATOR, 0, false, 6, (Object) null) + 1);
            Intrinsics.checkNotNullExpressionValue(mathBlockId, "substring(...)");
        }
        try {
            Long.parseLong(mathBlockId);
            return mathBlockId;
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder();
            int length = mathBlockId.length();
            for (int i = 0; i < length; i++) {
                char cCharAt = mathBlockId.charAt(i);
                if (Character.isDigit(cCharAt)) {
                    sb.append(cCharAt);
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
            return null;
        }
    }

    public final List<DataCurve> getResultCurves() {
        return getResultCurvesInternal();
    }

    private final List<DataCurve> getResultCurvesInternal() {
        List<RenderItem> list;
        int pageIndexByWorldSpacePoint;
        String str;
        int i;
        float f;
        ArrayList arrayList;
        float saveRatio = getCurveEngine().getSaveRatio();
        ArrayList arrayList2 = new ArrayList();
        synchronized (this.renderItemsLock) {
            list = CollectionsKt.toList(this.renderItemsMap.values());
        }
        for (RenderItem renderItem : list) {
            if (!StringsKt.isBlank(renderItem.getParams().getText())) {
                float startX = renderItem.getParams().getStartX() * saveRatio;
                int i2 = 0;
                try {
                    pageIndexByWorldSpacePoint = getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(renderItem.getParams().getStartX(), renderItem.getParams().getStartY()));
                    if (pageIndexByWorldSpacePoint < 0) {
                        Log.w(TAG, "getResultCurves: cannot determine pageIndex for mathBlockId=" + renderItem.getMathBlockId() + ", using 0");
                        pageIndexByWorldSpacePoint = 0;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "getResultCurves: error determining pageIndex for mathBlockId=" + renderItem.getMathBlockId(), e);
                }
                DigitalStrokeRenderer digitalStrokeRenderer = this.digitalRenderer;
                if (digitalStrokeRenderer != null) {
                    CurveProp curveProp = null;
                    try {
                        CurveProp curveProp2 = renderItem.getParams().getCurveProp();
                        if (curveProp2 != null) {
                            curveProp = curveProp2.clone();
                        }
                    } catch (Exception unused) {
                        CurveProp curveProp3 = renderItem.getParams().getCurveProp();
                        if (curveProp3 != null) {
                            curveProp = new CurveProp(curveProp3.getPenType(), curveProp3.getPenColor(), curveProp3.getPenSize(), curveProp3.getPenAlpha());
                        }
                    }
                    CurveProp curveProp4 = curveProp;
                    ArrayList arrayList3 = new ArrayList();
                    String text = renderItem.getParams().getText();
                    int length = text.length();
                    while (i2 < length) {
                        char cCharAt = text.charAt(i2);
                        if (cCharAt == 'e') {
                            String str2 = text;
                            f = startX;
                            arrayList = arrayList3;
                            i = length;
                            str = str2;
                            arrayList.addAll(digitalStrokeRenderer.createDigitCurves(String.valueOf(cCharAt), f, (renderItem.getParams().getStartY() * saveRatio) + ((renderItem.getParams().getDigitHeight() * saveRatio) / 2.0f), (renderItem.getParams().getDigitWidth() * saveRatio) / 1.5f, (renderItem.getParams().getDigitHeight() * saveRatio) / 2.0f, 0.1f, curveProp4));
                        } else {
                            str = text;
                            i = length;
                            f = startX;
                            arrayList = arrayList3;
                            arrayList.addAll(digitalStrokeRenderer.createDigitCurves(String.valueOf(cCharAt), f, renderItem.getParams().getStartY() * saveRatio, renderItem.getParams().getDigitWidth() * saveRatio, renderItem.getParams().getDigitHeight() * saveRatio, 0.1f, curveProp4));
                        }
                        i2++;
                        arrayList3 = arrayList;
                        startX = (renderItem.getParams().getDigitWidth() * saveRatio) + 0.1f + f;
                        text = str;
                        length = i;
                    }
                    ArrayList<DataCurve> arrayList4 = arrayList3;
                    for (DataCurve dataCurve : arrayList4) {
                        dataCurve.mCurveType = (short) 1;
                        dataCurve.mathBlockId = extractNumericIdFromMathBlockId(renderItem.getMathBlockId());
                        dataCurve.pageIndex = pageIndexByWorldSpacePoint;
                    }
                    arrayList2.addAll(arrayList4);
                }
            }
        }
        return arrayList2;
    }

    public final void clearResultByMathBlockId(String mathBlockId) {
        RenderItem renderItemRemove;
        RenderItem renderItemRemove2;
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        synchronized (this.renderItemsLock) {
            renderItemRemove = this.renderItemsMap.remove(mathBlockId);
        }
        synchronized (this.renderItemsLock) {
            renderItemRemove2 = this.prepareRenderItemsMapForAnimation.remove(mathBlockId);
        }
        boolean z = (renderItemRemove == null && renderItemRemove2 == null) ? false : true;
        this.mathBlockCurvePropMap.remove(mathBlockId);
        if (z) {
            Log.d(TAG, "Remove MathBlock: " + mathBlockId + ", Left: " + this.renderItemsMap.size() + ",LeftItems: " + this.renderItemsMap.keySet());
            MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
            return;
        }
        Log.d(TAG, "NO id: " + mathBlockId);
    }

    public final void clearAllResults() {
        Log.d(TAG, "clear all results, original size: " + this.renderItemsMap.size());
        synchronized (this.renderItemsLock) {
            this.renderItemsMap.clear();
            Unit unit = Unit.INSTANCE;
        }
        this.mathBlockCurvePropMap.clear();
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final void clearAllResults(Integer pageIndex) {
        List<Pair> list;
        if (pageIndex == null) {
            clearAllResults();
            return;
        }
        if (getCurveEngine().isInfinite()) {
            clearAllResults();
            return;
        }
        synchronized (this.renderItemsLock) {
            list = MapsKt.toList(this.renderItemsMap);
        }
        ArrayList<String> arrayList = new ArrayList();
        for (Pair pair : list) {
            String str = (String) pair.component1();
            RenderItem renderItem = (RenderItem) pair.component2();
            try {
                int pageIndexByWorldSpacePoint = getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(renderItem.getParams().getStartX(), renderItem.getParams().getStartY()));
                if (pageIndex != null && pageIndexByWorldSpacePoint == pageIndex.intValue()) {
                    arrayList.add(str);
                }
            } catch (Exception e) {
                Log.e(TAG, "clearAllResults: error getting pageIndex for mathBlockId=" + str, e);
            }
        }
        synchronized (this.renderItemsLock) {
            for (String str2 : arrayList) {
                this.renderItemsMap.remove(str2);
                this.mathBlockCurvePropMap.remove(str2);
                this.prepareRenderItemsMapForAnimation.remove(str2);
            }
            Unit unit = Unit.INSTANCE;
        }
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final boolean hasRenderContent() {
        boolean z;
        synchronized (this.renderItemsLock) {
            z = (this.renderItemsMap.isEmpty() && this.prepareRenderItemsMapForAnimation.isEmpty()) ? false : true;
        }
        return z;
    }

    public final int getRenderItemCount() {
        return this.renderItemsMap.size();
    }

    public final boolean hasMathBlockRenderItem(String mathBlockId) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        return this.renderItemsMap.containsKey(mathBlockId);
    }

    /* JADX INFO: renamed from: getOutTextureId, reason: from getter */
    public final int getMainTextureId() {
        return this.mainTextureId;
    }

    private final List<DataCurve> getFormulaCurves(MathResult mathResult) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        IItemIdHelper itemIdHelper = getCurveEngine().getRecognitionModel().getItemIdHelper();
        String[] itemsByBlockId = itemIdHelper != null ? itemIdHelper.getItemsByBlockId(mathResult.getMathBlockId()) : null;
        if (itemsByBlockId == null || itemsByBlockId.length == 0) {
            Log.w(TAG, "No items found for math block: " + mathResult.getMathBlockId());
            return CollectionsKt.emptyList();
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        ICurveDataManager iCurveDataManager = getCurveEngine().getPenEngine().mCurveDataManager;
        Intrinsics.checkNotNull(iCurveDataManager, "null cannot be cast to non-null type xc.engine.core.graphics.curve.CurveDataManager");
        List<DataCurve> listScanIntersectDataCurve = ((CurveDataManager) iCurveDataManager).scanIntersectDataCurve(this.mathAnimatorPropInfo.getSavaCurveRect(), false);
        Log.w("yjg", "MS.scanIntersectDataCurve cost: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms, total=" + listScanIntersectDataCurve.size());
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        Intrinsics.checkNotNull(listScanIntersectDataCurve);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listScanIntersectDataCurve) {
            DataCurve dataCurve = (DataCurve) obj;
            if (dataCurve.strokeId != null && ArraysKt.contains(itemsByBlockId, dataCurve.strokeId)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        Log.w("yjg", "MS.filterByStrokeIds cost: " + (System.currentTimeMillis() - jCurrentTimeMillis3) + "ms, matched=" + arrayList2.size());
        XLog.d(TAG, "getFormulaCurves [Find %d in %d strokes] From %s  passTime: %dms", Integer.valueOf(arrayList2.size()), Integer.valueOf(listScanIntersectDataCurve.size()), this.mathAnimatorPropInfo.getSavaCurveRect(), Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
        Log.w("yjg", "MS.getFormulaCurves total cost: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
        return arrayList2;
    }

    public final boolean validateEqualSignRatio(List<String> strokeIds) {
        List<DataCurve> cachedCurveList;
        Integer numValueOf;
        Object next;
        Intrinsics.checkNotNullParameter(strokeIds, "strokeIds");
        if (strokeIds.size() < 2) {
            Log.w(TAG, "validateEqualSignRatio: strokeIds count < 2, return true");
            return true;
        }
        try {
            ICurveDataManager iCurveDataManager = getCurveEngine().getPenEngine().mCurveDataManager;
            CurveDataManager curveDataManager = iCurveDataManager instanceof CurveDataManager ? (CurveDataManager) iCurveDataManager : null;
            if (curveDataManager != null && (cachedCurveList = curveDataManager.getCachedCurveList()) != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : strokeIds) {
                    Iterator<T> it = cachedCurveList.iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!Intrinsics.areEqual(((DataCurve) next).strokeId, str));
                    DataCurve dataCurve = (DataCurve) next;
                    if (dataCurve != null) {
                        arrayList.add(dataCurve);
                    }
                }
                ArrayList<DataCurve> arrayList2 = arrayList;
                if (arrayList2.size() < 2) {
                    Log.w(TAG, "validateEqualSignRatio: found " + arrayList2.size() + " curves, need 2, return true");
                    return true;
                }
                ArrayList arrayList3 = new ArrayList();
                for (DataCurve dataCurve2 : arrayList2) {
                    try {
                        Object mLockObj = dataCurve2.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                        synchronized (mLockObj) {
                            try {
                                int size = dataCurve2.size(dataCurve2.mSavePoints);
                                numValueOf = size > 0 ? Integer.valueOf(size) : null;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "validateEqualSignRatio: error getting point count for strokeId " + dataCurve2.strokeId, e);
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        arrayList3.add(numValueOf);
                    }
                }
                ArrayList arrayList4 = arrayList3;
                if (arrayList4.size() < 2) {
                    Log.w(TAG, "validateEqualSignRatio: valid point counts < 2, return true");
                    return true;
                }
                int iIntValue = ((Number) arrayList4.get(0)).intValue();
                int iIntValue2 = ((Number) arrayList4.get(1)).intValue();
                return (iIntValue > iIntValue2 ? ((float) iIntValue) / ((float) iIntValue2) : ((float) iIntValue2) / ((float) iIntValue)) <= 5.0f;
            }
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "validateEqualSignRatio error: " + e2.getMessage(), e2);
            return true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void renderArea(int fbId, Point fbSize, RectF areaWS, int tmpFbId, int tmpTextureId) throws Throwable {
        List list;
        char c;
        Object obj;
        MathSystem mathSystem = this;
        Intrinsics.checkNotNullParameter(fbSize, "fbSize");
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        synchronized (mathSystem.renderItemsLock) {
            list = CollectionsKt.toList(mathSystem.renderItemsMap.values());
        }
        if (list.isEmpty()) {
            return;
        }
        int i = fbId;
        GLES30.glBindFramebuffer(36160, i);
        char c2 = 0;
        GLES30.glViewport(0, 0, fbSize.x, fbSize.y);
        float fWidth = fbSize.x / areaWS.width();
        RectF rectF = new RectF(areaWS.left, areaWS.bottom, areaWS.right, areaWS.top);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List<DataCurve> listGenerateMathResultCurves = mathSystem.generateMathResultCurves(((RenderItem) it.next()).getParams());
            if (listGenerateMathResultCurves.isEmpty()) {
                c = c2;
            } else {
                long jNativeGenCurveVertexBuffer = 0;
                for (DataCurve dataCurve : listGenerateMathResultCurves) {
                    dataCurve.isMathResult = true;
                    Object mLockObj = dataCurve.mLockObj;
                    char c3 = c2;
                    Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                    synchronized (mLockObj) {
                        try {
                            float value = dataCurve.getCurveProp().getPenType().getValue();
                            float penSize = dataCurve.getCurveProp().getPenSize();
                            float drawRatio = dataCurve.getDrawRatio();
                            float f = areaWS.left;
                            float f2 = areaWS.top;
                            float f3 = areaWS.right;
                            float f4 = areaWS.bottom;
                            float sensitivity = dataCurve.getSensitivity();
                            float tipSize = dataCurve.getTipSize();
                            float[] fArr = new float[10];
                            fArr[c3] = value;
                            fArr[1] = penSize;
                            fArr[2] = drawRatio;
                            fArr[3] = fWidth;
                            fArr[4] = f;
                            fArr[5] = f2;
                            fArr[6] = f3;
                            fArr[7] = f4;
                            fArr[8] = sensitivity;
                            fArr[9] = tipSize;
                            long j = dataCurve.mSavePoints;
                            obj = mLockObj;
                            try {
                                jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(j, fArr, jNativeGenCurveVertexBuffer, jNativeGenCurveVertexBuffer != 0 ? 1 : c3);
                                Unit unit = Unit.INSTANCE;
                                c2 = c3;
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            obj = mLockObj;
                        }
                    }
                }
                c = c2;
                CurveRenderer.renderCurve$default(getCurveEngine().getMathCurveRenderer(), (DataCurve) CollectionsKt.first((List) listGenerateMathResultCurves), rectF, i, tmpFbId, tmpTextureId, fWidth, jNativeGenCurveVertexBuffer, false, 128, null);
            }
            mathSystem = this;
            i = fbId;
            c2 = c;
        }
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0014\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0007\"\u0004\b'\u0010\tR\u001a\u0010(\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\tR\u001a\u0010+\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0007\"\u0004\b-\u0010\tR\u001a\u0010.\u001a\u00020/X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u00020/X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00101\"\u0004\b6\u00103R\u001a\u00107\u001a\u00020/X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00101\"\u0004\b9\u00103¨\u0006:"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$ScannerEffectInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/MathSystem;)V", "currentProcess", "", "getCurrentProcess", "()F", "setCurrentProcess", "(F)V", "showWidth1", "getShowWidth1", "setShowWidth1", "showWidth2", "getShowWidth2", "setShowWidth2", "textureChannel", "getTextureChannel", "setTextureChannel", "shadowL", "getShadowL", "setShadowL", "shadowR", "getShadowR", "setShadowR", "mathRect", "Landroid/graphics/RectF;", "getMathRect", "()Landroid/graphics/RectF;", "setMathRect", "(Landroid/graphics/RectF;)V", "bounderSize", "", "getBounderSize", "()I", "setBounderSize", "(I)V", "startProcess", "getStartProcess", "setStartProcess", "resultEndProcess", "getResultEndProcess", "setResultEndProcess", "endProcess", "getEndProcess", "setEndProcess", "color1", "", "getColor1", "()[F", "setColor1", "([F)V", "color2", "getColor2", "setColor2", "color3", "getColor3", "setColor3", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class ScannerEffectInfo {
        private int bounderSize;
        private float currentProcess;
        private float resultEndProcess;
        private float startProcess;
        private float textureChannel;
        private float showWidth1 = 0.16f;
        private float showWidth2 = 0.16f;
        private float shadowL = 0.01f;
        private float shadowR = 0.01f;
        private RectF mathRect = new RectF();
        private float endProcess = 1.0f;
        private float[] color1 = {1.0f, 0.5216f, 0.0784f, 1.0f};
        private float[] color2 = {1.0f, 0.7294f, 0.1412f, 1.0f};
        private float[] color3 = {1.0f, 0.8196f, 0.1686f, 1.0f};

        public ScannerEffectInfo() {
        }

        public final float getCurrentProcess() {
            return this.currentProcess;
        }

        public final void setCurrentProcess(float f) {
            this.currentProcess = f;
        }

        public final float getShowWidth1() {
            return this.showWidth1;
        }

        public final void setShowWidth1(float f) {
            this.showWidth1 = f;
        }

        public final float getShowWidth2() {
            return this.showWidth2;
        }

        public final void setShowWidth2(float f) {
            this.showWidth2 = f;
        }

        public final float getTextureChannel() {
            return this.textureChannel;
        }

        public final void setTextureChannel(float f) {
            this.textureChannel = f;
        }

        public final float getShadowL() {
            return this.shadowL;
        }

        public final void setShadowL(float f) {
            this.shadowL = f;
        }

        public final float getShadowR() {
            return this.shadowR;
        }

        public final void setShadowR(float f) {
            this.shadowR = f;
        }

        public final RectF getMathRect() {
            return this.mathRect;
        }

        public final void setMathRect(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.mathRect = rectF;
        }

        public final int getBounderSize() {
            return this.bounderSize;
        }

        public final void setBounderSize(int i) {
            this.bounderSize = i;
        }

        public final float getStartProcess() {
            return this.startProcess;
        }

        public final void setStartProcess(float f) {
            this.startProcess = f;
        }

        public final float getResultEndProcess() {
            return this.resultEndProcess;
        }

        public final void setResultEndProcess(float f) {
            this.resultEndProcess = f;
        }

        public final float getEndProcess() {
            return this.endProcess;
        }

        public final void setEndProcess(float f) {
            this.endProcess = f;
        }

        public final float[] getColor1() {
            return this.color1;
        }

        public final void setColor1(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.color1 = fArr;
        }

        public final float[] getColor2() {
            return this.color2;
        }

        public final void setColor2(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.color2 = fArr;
        }

        public final float[] getColor3() {
            return this.color3;
        }

        public final void setColor3(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.color3 = fArr;
        }
    }

    /* JADX INFO: compiled from: MathSystem.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u00101\u001a\u00020\u00052\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0014\u00103\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0006\u00106\u001a\u000204R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001bR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0019\"\u0004\b'\u0010\u001bR\u001a\u0010(\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0019\"\u0004\b*\u0010\u001bR\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u00067"}, d2 = {"Lxc/engine/core/graphics/engine/systems/MathSystem$MathAnimatorPropInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/MathSystem;)V", "isOrder", "", "()Z", "setOrder", "(Z)V", "cachedCurves", "", "Lxc/engine/data/pagedata/DataCurve;", "getCachedCurves", "()Ljava/util/List;", "setCachedCurves", "(Ljava/util/List;)V", "curvesHash", "", "getCurvesHash", "()I", "setCurvesHash", "(I)V", "mathBackPartRect", "Landroid/graphics/RectF;", "getMathBackPartRect", "()Landroid/graphics/RectF;", "setMathBackPartRect", "(Landroid/graphics/RectF;)V", "savaCurveRect", "getSavaCurveRect", "setSavaCurveRect", "penSizeScaleInPage", "", "getPenSizeScaleInPage", "()F", "setPenSizeScaleInPage", "(F)V", "screenRectInCurveCoord", "getScreenRectInCurveCoord", "setScreenRectInCurveCoord", "screenRectInCurveCoordFlip", "getScreenRectInCurveCoordFlip", "setScreenRectInCurveCoordFlip", "mathResult", "Lxc/engine/math/iink/bean/MathResult;", "getMathResult", "()Lxc/engine/math/iink/bean/MathResult;", "setMathResult", "(Lxc/engine/math/iink/bean/MathResult;)V", "needsCurvesUpdate", "newCurves", "updateCurvesCache", "", "curves", "clearCurvesCache", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class MathAnimatorPropInfo {
        private int curvesHash;
        private MathResult mathResult;
        private boolean isOrder = true;
        private List<? extends DataCurve> cachedCurves = CollectionsKt.emptyList();
        private RectF mathBackPartRect = new RectF();
        private RectF savaCurveRect = new RectF();
        private float penSizeScaleInPage = 1.0f;
        private RectF screenRectInCurveCoord = new RectF();
        private RectF screenRectInCurveCoordFlip = new RectF();

        public MathAnimatorPropInfo() {
        }

        /* JADX INFO: renamed from: isOrder, reason: from getter */
        public final boolean getIsOrder() {
            return this.isOrder;
        }

        public final void setOrder(boolean z) {
            this.isOrder = z;
        }

        public final List<DataCurve> getCachedCurves() {
            return this.cachedCurves;
        }

        public final void setCachedCurves(List<? extends DataCurve> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.cachedCurves = list;
        }

        public final int getCurvesHash() {
            return this.curvesHash;
        }

        public final void setCurvesHash(int i) {
            this.curvesHash = i;
        }

        public final RectF getMathBackPartRect() {
            return this.mathBackPartRect;
        }

        public final void setMathBackPartRect(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.mathBackPartRect = rectF;
        }

        public final RectF getSavaCurveRect() {
            return this.savaCurveRect;
        }

        public final void setSavaCurveRect(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.savaCurveRect = rectF;
        }

        public final float getPenSizeScaleInPage() {
            return this.penSizeScaleInPage;
        }

        public final void setPenSizeScaleInPage(float f) {
            this.penSizeScaleInPage = f;
        }

        public final RectF getScreenRectInCurveCoord() {
            return this.screenRectInCurveCoord;
        }

        public final void setScreenRectInCurveCoord(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.screenRectInCurveCoord = rectF;
        }

        public final RectF getScreenRectInCurveCoordFlip() {
            return this.screenRectInCurveCoordFlip;
        }

        public final void setScreenRectInCurveCoordFlip(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.screenRectInCurveCoordFlip = rectF;
        }

        public final MathResult getMathResult() {
            return this.mathResult;
        }

        public final void setMathResult(MathResult mathResult) {
            this.mathResult = mathResult;
        }

        public final boolean needsCurvesUpdate(List<? extends DataCurve> newCurves) {
            Intrinsics.checkNotNullParameter(newCurves, "newCurves");
            return this.curvesHash != newCurves.hashCode();
        }

        public final void updateCurvesCache(List<? extends DataCurve> curves) {
            Intrinsics.checkNotNullParameter(curves, "curves");
            this.cachedCurves = curves;
            this.curvesHash = curves.hashCode();
        }

        public final void clearCurvesCache() {
            this.mathResult = null;
            this.savaCurveRect = new RectF();
            this.cachedCurves = CollectionsKt.emptyList();
            this.curvesHash = 0;
        }
    }

    private final boolean isResultWithinPageBounds(MathResult mathResult) {
        boolean z;
        try {
            PageSystem pageSystem = getCurveEngine().getPageSystem();
            int pageIndexByWorldSpacePoint = pageSystem.getPageIndexByWorldSpacePoint(new PointF(mathResult.getBoundingBox().getX(), mathResult.getBoundingBox().getY()));
            String str = TAG;
            Log.d(str, "Current page index: " + pageIndexByWorldSpacePoint);
            if (pageIndexByWorldSpacePoint < 0) {
                Log.d(str, "Invalid page index, treating as out of bounds");
                return false;
            }
            RectF area = pageSystem.getPageData(pageIndexByWorldSpacePoint).getArea();
            int length = mathResult.getResult().length();
            BoundingBox boundingBox = mathResult.getBoundingBox();
            BoundingBox formulaBox = mathResult.getFormulaBox();
            float f = area.left;
            z = true;
            try {
                Log.d(str, "Page area: left=" + f + ", top=" + area.top + ", right=" + area.right + ", bottom=" + area.bottom);
                Log.d(str, "Result bbox(single): x=" + boundingBox.getX() + ", y=" + boundingBox.getY() + ", width=" + boundingBox.getWidth() + ", height=" + boundingBox.getHeight());
                float f2 = length;
                Log.w(str, "Result bounds: x=" + boundingBox.getX() + ", y=" + boundingBox.getY() + ", right=" + (boundingBox.getX() + (boundingBox.getWidth() * f2)) + ", bottom=" + (boundingBox.getY() + boundingBox.getHeight()));
                float x = boundingBox.getX() + (boundingBox.getWidth() * f2);
                boundingBox.getY();
                boundingBox.getHeight();
                boolean z2 = x <= area.right;
                boolean z3 = x <= getCurveEngine().getScreenAreaWS().right;
                boolean z4 = ((pageIndexByWorldSpacePoint != 0 || formulaBox.getY() >= area.top) && (pageIndexByWorldSpacePoint != pageSystem.getPageCount() + (-1) || formulaBox.getY() + formulaBox.getHeight() <= area.bottom)) ? z2 && z3 : false;
                Log.w(str, "Within page: " + z2 + ", within screen: " + z3 + ", final within bounds: " + z4);
                return z4;
            } catch (Exception e) {
                e = e;
                Log.e(TAG, "Error checking page bounds: " + e.getMessage());
                return z;
            }
        } catch (Exception e2) {
            e = e2;
            z = true;
        }
    }

    private final boolean isResultWithinScreenBounds(MathResult mathResult) {
        try {
            int length = mathResult.getResult().length();
            BoundingBox boundingBox = mathResult.getBoundingBox();
            return boundingBox.getX() + (boundingBox.getWidth() * ((float) length)) <= getCurveEngine().getScreenAreaWS().right;
        } catch (Exception e) {
            Log.e(TAG, "Error checking page bounds: " + e.getMessage());
            return true;
        }
    }

    private final void handleOutOfBoundsResult(MathResult mathResult) {
        try {
            String str = TAG;
            Log.d(str, "=======handleOutOfBoundsResult=======");
            Log.d(str, "BoundingBox: x=" + mathResult.getBoundingBox().getX() + ", y=" + mathResult.getBoundingBox().getY() + ", width=" + mathResult.getBoundingBox().getWidth() + ", height=" + mathResult.getBoundingBox().getHeight());
            Log.d(str, "FormulaBox: x=" + mathResult.getFormulaBox().getX() + ", y=" + mathResult.getFormulaBox().getY() + ", width=" + mathResult.getFormulaBox().getWidth() + ", height=" + mathResult.getFormulaBox().getHeight());
            PageSystem pageSystem = getCurveEngine().getPageSystem();
            pageSystem.getPageData(pageSystem.getPageIndexByWorldSpacePoint(new PointF(mathResult.getBoundingBox().getX(), mathResult.getBoundingBox().getY()))).getArea();
            List<DataCurve> mathCurves = getMathCurves(mathResult.getMathBlockId());
            BoundingBox formulaBox = mathResult.getFormulaBox();
            PointF pointF = new PointF(formulaBox.getX(), formulaBox.getY());
            PointF pointF2 = new PointF(formulaBox.getX() + formulaBox.getWidth(), formulaBox.getY() + formulaBox.getHeight());
            PointF screenSpacePointFromWorldSpacePoint = getCurveEngine().getScreenSpacePointFromWorldSpacePoint(pointF);
            PointF screenSpacePointFromWorldSpacePoint2 = getCurveEngine().getScreenSpacePointFromWorldSpacePoint(pointF2);
            RectF rectF = new RectF(screenSpacePointFromWorldSpacePoint.x, screenSpacePointFromWorldSpacePoint.y, screenSpacePointFromWorldSpacePoint2.x, screenSpacePointFromWorldSpacePoint2.y);
            Log.d(str, "World space formulaBox: x=" + formulaBox.getX() + ", y=" + formulaBox.getY() + ", w=" + formulaBox.getWidth() + ", h=" + formulaBox.getHeight());
            Log.d(str, "Screen space selectRect: " + rectF);
            getCurveEngine().getMathDataManager().isMathAutoSelect = true;
            this.lastAutoSelectMathResult = mathResult;
            this.lastAutoSelectCurves = mathCurves;
            if (this.isPageInteracting) {
                Log.d(str, "handleOutOfBoundsResult: page is interacting, skip notify and cache only");
            } else {
                getCurveEngine().getMathDataManager().notifyMathSelectCallBack(mathCurves, rectF);
            }
            Log.d(str, "=======handleOutOfBoundsResult finished=======");
        } catch (Exception e) {
            Log.e(TAG, "Error handling out-of-bounds result: " + e.getMessage(), e);
            proceedWithNormalRendering(mathResult);
        }
    }

    private final void tryReTriggerAutoSelect() {
        MathResult mathResult = this.lastAutoSelectMathResult;
        if (mathResult == null) {
            return;
        }
        List<? extends DataCurve> mathCurves = this.lastAutoSelectCurves;
        if (mathCurves == null) {
            mathCurves = getMathCurves(mathResult.getMathBlockId());
        }
        try {
            BoundingBox formulaBox = mathResult.getFormulaBox();
            PointF pointF = new PointF(formulaBox.getX(), formulaBox.getY());
            PointF pointF2 = new PointF(formulaBox.getX() + formulaBox.getWidth(), formulaBox.getY() + formulaBox.getHeight());
            PointF screenSpacePointFromWorldSpacePoint = getCurveEngine().getScreenSpacePointFromWorldSpacePoint(pointF);
            PointF screenSpacePointFromWorldSpacePoint2 = getCurveEngine().getScreenSpacePointFromWorldSpacePoint(pointF2);
            RectF rectF = new RectF(screenSpacePointFromWorldSpacePoint.x, screenSpacePointFromWorldSpacePoint.y, screenSpacePointFromWorldSpacePoint2.x, screenSpacePointFromWorldSpacePoint2.y);
            String str = TAG;
            Log.d(str, "tryReTriggerAutoSelect: selectRect=" + rectF + ", curves=" + mathCurves.size());
            if (!this.isPageInteracting) {
                getCurveEngine().getMathDataManager().notifyMathSelectCallBack(mathCurves, rectF);
            } else {
                Log.d(str, "tryReTriggerAutoSelect: page is interacting, skip notify");
            }
        } catch (Exception e) {
            Log.e(TAG, "tryReTriggerAutoSelect fail: " + e.getMessage(), e);
        }
    }

    private final List<DataCurve> getMathCurves(String mathBlockId) {
        ArrayList arrayList = new ArrayList();
        try {
            IItemIdHelper itemIdHelper = getCurveEngine().getRecognitionModel().getItemIdHelper();
            String[] itemsByBlockId = itemIdHelper != null ? itemIdHelper.getItemsByBlockId(mathBlockId) : null;
            if (itemsByBlockId != null && itemsByBlockId.length != 0) {
                Iterator<DataCurve> it = getCurveEngine().getPenEngine().mCurveDataManager.getCachedCurveList().iterator();
                while (it.hasNext()) {
                    DataCurve next = it.next();
                    if ((next != null ? next.strokeId : null) != null && ArraysKt.contains(itemsByBlockId, next.strokeId)) {
                        arrayList.add(next);
                    }
                }
                Log.d(TAG, "getMathCurves: Finally get " + arrayList.size() + "curves");
                return arrayList;
            }
            Log.w(TAG, "getMathCurves: No mathBlock items!");
            return arrayList;
        } catch (Exception e) {
            Log.e(TAG, "getMathCurves: fail", e);
            return arrayList;
        }
    }

    private final void proceedWithNormalRendering(MathResult mathResult) {
        String str = TAG;
        Log.d(str, "proceedWithNormalRendering: ");
        RenderParams renderParamsCreateRenderParamsFromMathResult = createRenderParamsFromMathResult(mathResult);
        if (renderParamsCreateRenderParamsFromMathResult.getCurveProp() == null) {
            Log.w(str, "Skipping math result rendering due to missing curveProp for " + mathResult.getMathBlockId());
            return;
        }
        RenderItem renderItem = new RenderItem(mathResult.getMathBlockId(), renderParamsCreateRenderParamsFromMathResult, 0L, mathResult.getFormulaLatex(), 4, null);
        synchronized (this.renderItemsLock) {
            this.renderItemsMap.put(mathResult.getMathBlockId(), renderItem);
            Unit unit = Unit.INSTANCE;
        }
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
        if (this.isFromRefresh) {
            return;
        }
        startAnimator(mathResult, false);
    }

    private final int getPreviousFrameOffset() {
        if (this.mathAnimatorPropInfo.getIsOrder()) {
            return this.RT_HEIGHT;
        }
        return 0;
    }

    private final void updateTextureChannel() {
        this.scannerEffectInfo.setTextureChannel(this.mathAnimatorPropInfo.getIsOrder() ? 1.0f : 0.0f);
    }

    public final void onResultRadio(BoundingBox box) {
        Intrinsics.checkNotNullParameter(box, "box");
        float drawRatio = getCurveEngine().getDrawRatio();
        box.setX(box.getX() * drawRatio);
        box.setY(box.getY() * drawRatio);
        box.setWidth(box.getWidth() * drawRatio);
        box.setHeight(box.getHeight() * drawRatio);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x00e4  */
    @Override // xc.engine.math.iink.callback.MathResultCallback
    public void onMathResult(MathResult mathResult) {
        boolean z;
        Intrinsics.checkNotNullParameter(mathResult, "mathResult");
        if (mathResult.getShouldClear()) {
            Log.d(TAG, "onMathResult: clear result");
            clearResultByMathBlockId(mathResult.getMathBlockId());
            return;
        }
        BoundingBox boundingBox = mathResult.getBoundingBox();
        onResultRadio(boundingBox);
        onResultRadio(mathResult.getFormulaBox());
        String str = TAG;
        Log.d(str, "Receive BlockID=" + mathResult.getMathBlockId() + ", " + mathResult.getExpression() + "=" + mathResult.getResult() + " ResultsBox(single result):" + boundingBox + " FormulaBox:" + mathResult.getFormulaBox() + ", Mode:" + getCurveEngine().getPageMode() + ", iSelect:" + getCurveEngine().getMathDataManager().getIsSelect() + ",LastBoundBox:" + this.lastBoundingBox);
        int pageIndexByWorldSpacePoint = getCurveEngine().getPageSystem().getPageIndexByWorldSpacePoint(new PointF(mathResult.getBoundingBox().getX(), mathResult.getBoundingBox().getY()));
        BoundingBox boundingBox2 = this.lastBoundingBox;
        if (boundingBox2 != null) {
            Intrinsics.checkNotNull(boundingBox2);
            if (isApproximatelyEqual(boundingBox2, boundingBox)) {
                PageInkModelInterface pageInkModelInterface = getCurveEngine().getPageInkModelInterface();
                if (pageInkModelInterface != null) {
                    pageInkModelInterface.mathDataHaveChange(pageIndexByWorldSpacePoint);
                }
            } else {
                this.lastBoundingBox = boundingBox;
            }
        } else {
            this.lastBoundingBox = boundingBox;
        }
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite) {
            if (!getCurveEngine().getMathDataManager().getHasUserStartedDrawing()) {
                Log.d(str, "User hasn't started drawing yet - skipping auto selection");
                proceedWithNormalRendering(mathResult);
                return;
            }
            if (this.isFromRefresh) {
                Log.d(str, "From refresh - skipping auto selection for existing results");
                proceedWithNormalRendering(mathResult);
                return;
            } else if (isResultWithinPageBounds(mathResult)) {
                z = false;
            } else {
                Object orDefault = this.outBoundBoxCheck.getOrDefault((Object) boundingBox, (Object) 0);
                Intrinsics.checkNotNullExpressionValue(orDefault, "getOrDefault(...)");
                int iIntValue = ((Number) orDefault).intValue();
                Log.d(str, "Math result outside page bounds, triggering selection for repositioning,Count:" + iIntValue);
                if (iIntValue == 0) {
                    this.outBoundBoxCheck.put(boundingBox, 1);
                    handleOutOfBoundsResult(mathResult);
                    return;
                }
                z = true;
            }
        } else {
            if (!getCurveEngine().getMathDataManager().getHasUserStartedDrawing()) {
                Log.d(str, "User hasn't started drawing yet - skipping auto selection");
                proceedWithNormalRendering(mathResult);
                return;
            }
            if (this.isFromRefresh) {
                Log.d(str, "From refresh - skipping auto selection for existing results");
                proceedWithNormalRendering(mathResult);
                return;
            }
            if (isResultWithinScreenBounds(mathResult)) {
                z = false;
            } else {
                Object orDefault2 = this.outBoundBoxCheck.getOrDefault((Object) boundingBox, (Object) 0);
                Intrinsics.checkNotNullExpressionValue(orDefault2, "getOrDefault(...)");
                int iIntValue2 = ((Number) orDefault2).intValue();
                Log.d(str, "Math result outside Screen bounds, triggering selection for repositioning, Count:" + iIntValue2);
                if (iIntValue2 == 0) {
                    this.outBoundBoxCheck.put(boundingBox, 1);
                    handleOutOfBoundsResult(mathResult);
                    return;
                }
                z = true;
            }
            Log.d(str, "Infinite canvas mode - skipping boundary check");
        }
        getCurveEngine().getMathDataManager().isMathAutoSelect = false;
        if (!getCurveEngine().getMathDataManager().getIsSelect()) {
            this.mathResultList.clear();
            if (isCanStartAnimation(z)) {
                proceedWithNormalRendering(mathResult);
                return;
            }
            RenderParams renderParamsCreateRenderParamsFromMathResult = createRenderParamsFromMathResult(mathResult);
            if (renderParamsCreateRenderParamsFromMathResult.getCurveProp() == null) {
                Log.w(str, "Skipping math result rendering due to missing curveProp for " + mathResult.getMathBlockId());
                return;
            }
            RenderItem renderItem = new RenderItem(mathResult.getMathBlockId(), renderParamsCreateRenderParamsFromMathResult, System.currentTimeMillis(), mathResult.getFormulaLatex());
            synchronized (this.renderItemsLock) {
                this.prepareRenderItemsMapForAnimation.put(mathResult.getMathBlockId(), renderItem);
                Unit unit = Unit.INSTANCE;
            }
            startAnimator(mathResult, true);
            MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
            return;
        }
        recordHaveSelectMathResult(mathResult);
    }

    @Override // xc.engine.math.iink.callback.MathResultCallback
    public void doDelete() {
        Log.d(TAG, "doDelete: " + this.mathResultList.size());
        for (MathResult mathResult : this.mathResultList) {
            RenderParams renderParamsCreateRenderParamsFromMathResult = createRenderParamsFromMathResult(mathResult);
            if (renderParamsCreateRenderParamsFromMathResult.getCurveProp() == null) {
                Log.w(TAG, "Skipping math result rendering due to missing curveProp for " + mathResult.getMathBlockId());
            } else {
                RenderItem renderItem = new RenderItem(mathResult.getMathBlockId(), renderParamsCreateRenderParamsFromMathResult, System.currentTimeMillis(), mathResult.getFormulaLatex());
                synchronized (this.renderItemsLock) {
                    this.prepareRenderItemsMapForAnimation.put(mathResult.getMathBlockId(), renderItem);
                    Unit unit = Unit.INSTANCE;
                }
                startAnimator(mathResult, true);
                MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
            }
        }
        this.mathResultList.clear();
    }

    public final float getDelta() {
        return this.delta;
    }

    private final boolean isApproximatelyEqual(BoundingBox a, BoundingBox b) {
        return Math.abs(a.getX() - b.getX()) < this.delta && Math.abs(a.getY() - b.getY()) < this.delta && Math.abs(a.getWidth() - b.getWidth()) < this.delta && Math.abs(a.getHeight() - b.getHeight()) < this.delta;
    }

    private final boolean isCanStartAnimation(boolean isNeed) {
        return this.isFromRefresh || this.isPageInteracting || getCurveEngine().getCamera().getCurrentIsScrollToTopOrBottom() || isNeed || this.isFromUndoRedo || getCurveEngine().getCamera().getBIsFlinging();
    }

    public final void moveCurveToRenderList() {
        if (this.prepareRenderItemsMapForAnimation.isEmpty()) {
            return;
        }
        synchronized (this.renderItemsLock) {
            for (Map.Entry<String, RenderItem> entry : this.prepareRenderItemsMapForAnimation.entrySet()) {
                this.renderItemsMap.put(entry.getKey(), entry.getValue());
            }
            this.prepareRenderItemsMapForAnimation.clear();
            Unit unit = Unit.INSTANCE;
        }
        MiCurveEngine.requestMultiBufferRender$default(getCurveEngine(), null, 1, null);
    }

    public final FormulaLatex findLatexByPoint(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        for (Map.Entry<String, RenderItem> entry : this.renderItemsMap.entrySet()) {
            RenderParams params = entry.getValue().getParams();
            RectF rectF = new RectF(params.getStartX(), params.getStartY(), params.getStartX() + (params.getText().length() * params.getDigitWidth()), params.getStartY() + params.getDigitHeight());
            if (rectF.contains(point.x, point.y)) {
                String latex = entry.getValue().getLatex();
                if (latex == null) {
                    latex = "";
                }
                if (Intrinsics.areEqual(latex, "")) {
                    break;
                }
                return new FormulaLatex(latex, rectF);
            }
        }
        return null;
    }

    public final void recordHaveSelectMathResult(MathResult mathResult) {
        Intrinsics.checkNotNullParameter(mathResult, "mathResult");
        this.mathResultList.add(mathResult);
    }
}
