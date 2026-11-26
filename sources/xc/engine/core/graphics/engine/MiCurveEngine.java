package xc.engine.core.graphics.engine;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import android.view.Choreographer;
import androidx.graphics.lowlatency.BufferInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.ui.page.UserAvatarUpdateActivity;
import defpackage.R2;
import java.lang.ref.WeakReference;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleSupplier;
import javax.microedition.khronos.egl.EGLContext;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.PenEngine;
import xc.engine.api.listener.LoadingIndicatorCallback;
import xc.engine.api.listener.RequestDrawCallback;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.androidx.graphics.FrameSyncThread;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.Camera;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.core.DataManager;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.renderers.AnchorBoxRenderer;
import xc.engine.core.graphics.engine.renderers.BgRenderer;
import xc.engine.core.graphics.engine.renderers.BlendRenderer;
import xc.engine.core.graphics.engine.renderers.CropFrameRenderer;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.DashedLineRenderer;
import xc.engine.core.graphics.engine.renderers.DebugDrawRenderer;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.engine.core.graphics.engine.renderers.PageMaskRender;
import xc.engine.core.graphics.engine.renderers.ScanEffectRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.engine.service.CurveEngineRenderService;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.systems.BgSystem;
import xc.engine.core.graphics.engine.systems.ComposeSystem;
import xc.engine.core.graphics.engine.systems.CurveSystem;
import xc.engine.core.graphics.engine.systems.DebugDrawSystem;
import xc.engine.core.graphics.engine.systems.ExportSystem;
import xc.engine.core.graphics.engine.systems.FrontBufferSystem;
import xc.engine.core.graphics.engine.systems.HighLightSystem;
import xc.engine.core.graphics.engine.systems.ImageSystem;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.core.graphics.engine.systems.PageSystem;
import xc.engine.core.graphics.engine.systems.PreviewSystem;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.core.graphics.engine.systems.TextSystem;
import xc.engine.core.graphics.engine.task.AsyncGLTaskManager;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.core.graphics.opengl.GLRenderUtil;
import xc.engine.core.graphics.pen.mark.MarkPropConfig;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.manager.EraserOperationManager;
import xc.engine.math.iink.manager.MathDataManager;
import xc.engine.math.iink.ui.RecognitionModel;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.utils.XLog;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: MiCurveEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ü\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u001b\u0018\u0000 µ\u00022\u00020\u0001:\bµ\u0002¶\u0002·\u0002¸\u0002B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0011\u0010ä\u0001\u001a\u00030å\u00012\u0007\u0010æ\u0001\u001a\u00020\u001fJ\b\u0010ç\u0001\u001a\u00030å\u0001J\u0014\u0010è\u0001\u001a\u00030å\u00012\n\u0010é\u0001\u001a\u0005\u0018\u00010Æ\u0001J\u0012\u0010ì\u0001\u001a\u00030å\u00012\b\u0010í\u0001\u001a\u00030î\u0001J\b\u0010ï\u0001\u001a\u00030å\u0001J\b\u0010ð\u0001\u001a\u00030å\u0001J\u001a\u0010ñ\u0001\u001a\u00030å\u00012\u0007\u0010ò\u0001\u001a\u00020\u001f2\u0007\u0010ó\u0001\u001a\u00020\u001fJ2\u0010ô\u0001\u001a\u00030å\u00012\b\u0010õ\u0001\u001a\u00030ö\u00012\n\u0010÷\u0001\u001a\u0005\u0018\u00010ø\u00012\b\u0010ù\u0001\u001a\u00030ú\u00012\b\u0010û\u0001\u001a\u00030Ì\u0001J\b\u0010ü\u0001\u001a\u00030å\u0001J\u001e\u0010ý\u0001\u001a\u00030å\u00012\b\u0010þ\u0001\u001a\u00030Ä\u00012\n\b\u0002\u0010ÿ\u0001\u001a\u00030¶\u0001J\u0012\u0010\u0080\u0002\u001a\u00030å\u00012\b\u0010õ\u0001\u001a\u00030ö\u0001J\u0012\u0010\u0081\u0002\u001a\u00030å\u00012\b\u0010õ\u0001\u001a\u00030ö\u0001J\b\u0010\u0082\u0002\u001a\u00030¶\u0001J\u0011\u0010\u0083\u0002\u001a\u00030å\u00012\u0007\u0010\u0084\u0002\u001a\u00020\u001fJ\n\u0010\u0085\u0002\u001a\u00030å\u0001H\u0002J\u0014\u0010\u0086\u0002\u001a\u00030å\u00012\n\b\u0002\u0010\u0087\u0002\u001a\u00030\u0088\u0002J\u0012\u0010\u0089\u0002\u001a\u00030å\u00012\b\u0010\u008a\u0002\u001a\u00030\u008b\u0002J\n\u0010\u008c\u0002\u001a\u00030å\u0001H\u0002J\b\u0010\u008d\u0002\u001a\u00030¶\u0001J\b\u0010\u008e\u0002\u001a\u00030\u008f\u0002J\b\u0010\u0090\u0002\u001a\u00030\u008f\u0002J\n\u0010\u0091\u0002\u001a\u0005\u0018\u00010\u0092\u0002J\b\u0010\u0093\u0002\u001a\u00030¶\u0001J\b\u0010\u0094\u0002\u001a\u00030¶\u0001J\b\u0010\u0095\u0002\u001a\u00030Ì\u0001J\b\u0010\u0096\u0002\u001a\u00030Ì\u0001J\b\u0010\u0097\u0002\u001a\u00030Ì\u0001J\b\u0010\u0098\u0002\u001a\u00030Ì\u0001J\b\u0010\u0099\u0002\u001a\u00030Ì\u0001J\u0011\u0010\u009a\u0002\u001a\u00030å\u00012\u0007\u0010\u009b\u0002\u001a\u00020\u001fJ\b\u0010\u009c\u0002\u001a\u00030Ì\u0001J\b\u0010\u009d\u0002\u001a\u00030\u009e\u0002J\u0012\u0010\u009f\u0002\u001a\u00030\u008f\u00022\b\u0010 \u0002\u001a\u00030\u008f\u0002J\u001c\u0010¡\u0002\u001a\u00030\u009e\u00022\b\u0010¢\u0002\u001a\u00030Ì\u00012\b\u0010£\u0002\u001a\u00030Ì\u0001J\u0011\u0010¤\u0002\u001a\u00030\u008f\u00022\u0007\u0010¥\u0002\u001a\u00020\u001fJ\u0012\u0010¡\u0002\u001a\u00030\u009e\u00022\b\u0010¦\u0002\u001a\u00030\u009e\u0002J\u0012\u0010§\u0002\u001a\u00030\u008f\u00022\b\u0010 \u0002\u001a\u00030\u008f\u0002J\u0012\u0010¨\u0002\u001a\u00030\u009e\u00022\b\u0010¦\u0002\u001a\u00030\u009e\u0002J\u0012\u0010©\u0002\u001a\u00030\u008f\u00022\b\u0010ª\u0002\u001a\u00030\u008f\u0002J\u0012\u0010«\u0002\u001a\u00030\u008f\u00022\b\u0010ª\u0002\u001a\u00030\u008f\u0002J\u0012\u0010¬\u0002\u001a\u00030\u008f\u00022\b\u0010 \u0002\u001a\u00030\u008f\u0002J\u0011\u0010\u00ad\u0002\u001a\f\u0012\u0005\u0012\u00030Ö\u0001\u0018\u00010Õ\u0001J\b\u0010®\u0002\u001a\u00030²\u0001J\b\u0010¯\u0002\u001a\u00030¶\u0001J\b\u0010°\u0002\u001a\u00030å\u0001J\b\u0010±\u0002\u001a\u00030å\u0001J\u0011\u0010²\u0002\u001a\f\u0012\u0005\u0012\u00030Ö\u0001\u0018\u00010Õ\u0001J\b\u0010³\u0002\u001a\u00030Ì\u0001J\b\u0010´\u0002\u001a\u00030Ì\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005R\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082.¢\u0006\u0002\n\u0000R*\u0010\u0019\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00178F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#R$\u0010(\u001a\u00020'2\u0006\u0010\u0018\u001a\u00020'@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0011\u0010-\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R*\u00101\u001a\u0012\u0012\u0004\u0012\u00020302j\b\u0012\u0004\u0012\u000203`4X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0011\u00109\u001a\u00020:¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010=\u001a\u00020>¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010A\u001a\u00020B¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010E\u001a\u00020B¢\u0006\b\n\u0000\u001a\u0004\bF\u0010DR\u0011\u0010G\u001a\u00020H¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010K\u001a\u00020L¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0011\u0010O\u001a\u00020P¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0011\u0010S\u001a\u00020T¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u0011\u0010W\u001a\u00020X¢\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0011\u0010[\u001a\u00020\\¢\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u0011\u0010_\u001a\u00020`¢\u0006\b\n\u0000\u001a\u0004\ba\u0010bR\u0011\u0010c\u001a\u00020d¢\u0006\b\n\u0000\u001a\u0004\be\u0010fR\u0011\u0010g\u001a\u00020h¢\u0006\b\n\u0000\u001a\u0004\bi\u0010jR\u0011\u0010k\u001a\u00020l¢\u0006\b\n\u0000\u001a\u0004\bm\u0010nR\u0011\u0010o\u001a\u00020p¢\u0006\b\n\u0000\u001a\u0004\bq\u0010rR\u0011\u0010s\u001a\u00020t¢\u0006\b\n\u0000\u001a\u0004\bu\u0010vR\u0011\u0010w\u001a\u00020x¢\u0006\b\n\u0000\u001a\u0004\by\u0010zR\u0011\u0010{\u001a\u00020|¢\u0006\b\n\u0000\u001a\u0004\b}\u0010~R\u0014\u0010\u007f\u001a\u00030\u0080\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001R\u0015\u0010\u0083\u0001\u001a\u00030\u0084\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001R\u0015\u0010\u0087\u0001\u001a\u00030\u0088\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001R\u0015\u0010\u008b\u0001\u001a\u00030\u008c\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001R\u0015\u0010\u008f\u0001\u001a\u00030\u0090\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001R\u0015\u0010\u0093\u0001\u001a\u00030\u0094\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0096\u0001R\u0015\u0010\u0097\u0001\u001a\u00030\u0098\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0099\u0001\u0010\u009a\u0001R\u0015\u0010\u009b\u0001\u001a\u00030\u009c\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u009d\u0001\u0010\u009e\u0001R\u0015\u0010\u009f\u0001\u001a\u00030 \u0001¢\u0006\n\n\u0000\u001a\u0006\b¡\u0001\u0010¢\u0001R\u0010\u0010£\u0001\u001a\u00030¤\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010¥\u0001\u001a\u00030¦\u0001¢\u0006\n\n\u0000\u001a\u0006\b§\u0001\u0010¨\u0001R\u0015\u0010©\u0001\u001a\u00030ª\u0001¢\u0006\n\n\u0000\u001a\u0006\b«\u0001\u0010¬\u0001R\u0015\u0010\u00ad\u0001\u001a\u00030®\u0001¢\u0006\n\n\u0000\u001a\u0006\b¯\u0001\u0010°\u0001R\u0010\u0010±\u0001\u001a\u00030²\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010³\u0001\u001a\u00030´\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010µ\u0001\u001a\u00030¶\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010·\u0001\u001a\u00030¶\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010¸\u0001\u001a\u00030¶\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010¹\u0001\u001a\u00030º\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010»\u0001\u001a\u00030¼\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010½\u0001\u001a\u0005\u0018\u00010¾\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010¿\u0001\u001a\u00070À\u0001R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010Á\u0001\u001a\u00030º\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010Â\u0001\u001a\n\u0012\u0005\u0012\u00030Ä\u00010Ã\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010Å\u0001\u001a\u0005\u0018\u00010Æ\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÇ\u0001\u0010È\u0001\"\u0006\bÉ\u0001\u0010Ê\u0001R\u0010\u0010Ë\u0001\u001a\u00030Ì\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Í\u0001\u001a\u00030´\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010Î\u0001\u001a\u0005\u0018\u00010Ï\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÐ\u0001\u0010Ñ\u0001\"\u0006\bÒ\u0001\u0010Ó\u0001R\u0019\u0010Ô\u0001\u001a\f\u0012\u0005\u0012\u00030Ö\u0001\u0018\u00010Õ\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010×\u0001\u001a\u00030º\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b×\u0001\u0010Ø\u0001\"\u0006\bÙ\u0001\u0010Ú\u0001R\u0010\u0010Û\u0001\u001a\u00030Ü\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Ý\u0001\u001a\u00030Ì\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bà\u0001\u0010á\u0001\"\u0006\bâ\u0001\u0010ã\u0001R#\u0010ê\u0001\u001a\u00030¶\u00012\u0007\u0010\u0018\u001a\u00030¶\u0001@BX\u0086\u000e¢\u0006\n\n\u0000\u001a\u0006\bê\u0001\u0010ë\u0001¨\u0006¹\u0002"}, d2 = {"Lxc/engine/core/graphics/engine/MiCurveEngine;", "", "penEngine", "Lxc/engine/api/PenEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/api/PenEngine;)V", "getPenEngine", "()Lxc/engine/api/PenEngine;", "setPenEngine", "glContext", "Ljavax/microedition/khronos/egl/EGLContext;", "getGlContext", "()Ljavax/microedition/khronos/egl/EGLContext;", "setGlContext", "(Ljavax/microedition/khronos/egl/EGLContext;)V", "renderService", "Lxc/engine/core/graphics/engine/service/CurveEngineRenderService;", "getRenderService", "()Lxc/engine/core/graphics/engine/service/CurveEngineRenderService;", "setRenderService", "(Lxc/engine/core/graphics/engine/service/CurveEngineRenderService;)V", "spanModelRef", "Ljava/lang/ref/WeakReference;", "Lxc/richtext/view/SpannedLayoutModel;", "value", "spannedLayoutModel", "getSpannedLayoutModel", "()Lxc/richtext/view/SpannedLayoutModel;", "setSpannedLayoutModel", "(Lxc/richtext/view/SpannedLayoutModel;)V", "screenWidthInPixel", "", "getScreenWidthInPixel", "()I", "setScreenWidthInPixel", "(I)V", "screenHeightInPixel", "getScreenHeightInPixel", "setScreenHeightInPixel", "Lxc/engine/core/graphics/engine/MiCurveEngine$EPageMode;", "pageMode", "getPageMode", "()Lxc/engine/core/graphics/engine/MiCurveEngine$EPageMode;", "setPageMode", "(Lxc/engine/core/graphics/engine/MiCurveEngine$EPageMode;)V", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "getTexturePool", "()Lxc/engine/core/graphics/engine/core/TexturePool;", "components", "Ljava/util/ArrayList;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "Lkotlin/collections/ArrayList;", "getComponents", "()Ljava/util/ArrayList;", "setComponents", "(Ljava/util/ArrayList;)V", UserAvatarUpdateActivity.CAMERA, "Lxc/engine/core/graphics/engine/core/Camera;", "getCamera", "()Lxc/engine/core/graphics/engine/core/Camera;", "dataManager", "Lxc/engine/core/graphics/engine/core/DataManager;", "getDataManager", "()Lxc/engine/core/graphics/engine/core/DataManager;", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "getCurveRenderer", "()Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "mathCurveRenderer", "getMathCurveRenderer", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "getShapeRenderer", "()Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "getTileRenderer", "()Lxc/engine/core/graphics/engine/TileRenderer;", "bgRenderer", "Lxc/engine/core/graphics/engine/renderers/BgRenderer;", "getBgRenderer", "()Lxc/engine/core/graphics/engine/renderers/BgRenderer;", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "getImgRenderer", "()Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "dashedLineRenderer", "Lxc/engine/core/graphics/engine/renderers/DashedLineRenderer;", "getDashedLineRenderer", "()Lxc/engine/core/graphics/engine/renderers/DashedLineRenderer;", "blendRenderer", "Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "getBlendRenderer", "()Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "pageMaskRenderer", "Lxc/engine/core/graphics/engine/renderers/PageMaskRender;", "getPageMaskRenderer", "()Lxc/engine/core/graphics/engine/renderers/PageMaskRender;", "cropFrameRenderer", "Lxc/engine/core/graphics/engine/renderers/CropFrameRenderer;", "getCropFrameRenderer", "()Lxc/engine/core/graphics/engine/renderers/CropFrameRenderer;", "debugDrawRenderer", "Lxc/engine/core/graphics/engine/renderers/DebugDrawRenderer;", "getDebugDrawRenderer", "()Lxc/engine/core/graphics/engine/renderers/DebugDrawRenderer;", "scanEffectRenderer", "Lxc/engine/core/graphics/engine/renderers/ScanEffectRenderer;", "getScanEffectRenderer", "()Lxc/engine/core/graphics/engine/renderers/ScanEffectRenderer;", "anchorBoxRenderer", "Lxc/engine/core/graphics/engine/renderers/AnchorBoxRenderer;", "getAnchorBoxRenderer", "()Lxc/engine/core/graphics/engine/renderers/AnchorBoxRenderer;", "curveSystem", "Lxc/engine/core/graphics/engine/systems/CurveSystem;", "getCurveSystem", "()Lxc/engine/core/graphics/engine/systems/CurveSystem;", "frontBufferSystem", "Lxc/engine/core/graphics/engine/systems/FrontBufferSystem;", "getFrontBufferSystem", "()Lxc/engine/core/graphics/engine/systems/FrontBufferSystem;", "bgSystem", "Lxc/engine/core/graphics/engine/systems/BgSystem;", "getBgSystem", "()Lxc/engine/core/graphics/engine/systems/BgSystem;", "imgSystem", "Lxc/engine/core/graphics/engine/systems/ImageSystem;", "getImgSystem", "()Lxc/engine/core/graphics/engine/systems/ImageSystem;", "textSystem", "Lxc/engine/core/graphics/engine/systems/TextSystem;", "getTextSystem", "()Lxc/engine/core/graphics/engine/systems/TextSystem;", "selectSystem", "Lxc/engine/core/graphics/engine/systems/SelectSystem;", "getSelectSystem", "()Lxc/engine/core/graphics/engine/systems/SelectSystem;", "previewSystem", "Lxc/engine/core/graphics/engine/systems/PreviewSystem;", "getPreviewSystem", "()Lxc/engine/core/graphics/engine/systems/PreviewSystem;", "highLightSystem", "Lxc/engine/core/graphics/engine/systems/HighLightSystem;", "getHighLightSystem", "()Lxc/engine/core/graphics/engine/systems/HighLightSystem;", "composeSystem", "Lxc/engine/core/graphics/engine/systems/ComposeSystem;", "getComposeSystem", "()Lxc/engine/core/graphics/engine/systems/ComposeSystem;", "pageSystem", "Lxc/engine/core/graphics/engine/systems/PageSystem;", "getPageSystem", "()Lxc/engine/core/graphics/engine/systems/PageSystem;", "mathSystem", "Lxc/engine/core/graphics/engine/systems/MathSystem;", "getMathSystem", "()Lxc/engine/core/graphics/engine/systems/MathSystem;", "recognitionModel", "Lxc/engine/math/iink/ui/RecognitionModel;", "getRecognitionModel", "()Lxc/engine/math/iink/ui/RecognitionModel;", "debugDrawSystem", "Lxc/engine/core/graphics/engine/systems/DebugDrawSystem;", "exportSystem", "Lxc/engine/core/graphics/engine/systems/ExportSystem;", "getExportSystem", "()Lxc/engine/core/graphics/engine/systems/ExportSystem;", "mathDataManager", "Lxc/engine/math/iink/manager/MathDataManager;", "getMathDataManager", "()Lxc/engine/math/iink/manager/MathDataManager;", "taskManager", "Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager;", "getTaskManager", "()Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager;", "mAnimatorHandle", "Lxc/engine/core/fast/androidx/graphics/FrameSyncThread;", "reqCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "bIsDarkMode", "", "bScreenInitialized", "bDataReady", "bSceneReadyCalled", "Ljava/util/concurrent/atomic/AtomicBoolean;", "renderRes", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "choreographer", "Landroid/view/Choreographer;", "vsyncCallback", "Lxc/engine/core/graphics/engine/MiCurveEngine$VSyncCallback;", "pendingRefresh", "mSyncTask", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "renderCallback", "Lxc/engine/api/listener/RequestDrawCallback;", "getRenderCallback", "()Lxc/engine/api/listener/RequestDrawCallback;", "setRenderCallback", "(Lxc/engine/api/listener/RequestDrawCallback;)V", "toolbarHeight", "", "taskTotalCount", "loadingIndicatorCallback", "Lxc/engine/api/listener/LoadingIndicatorCallback;", "getLoadingIndicatorCallback", "()Lxc/engine/api/listener/LoadingIndicatorCallback;", "setLoadingIndicatorCallback", "(Lxc/engine/api/listener/LoadingIndicatorCallback;)V", "spannedData", "", "Lxc/richtext/data/SpannedData;", "isDestroyCalled", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "setDestroyCalled", "(Ljava/util/concurrent/atomic/AtomicBoolean;)V", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "mPointMaxSize", "pageInkModelInterface", "Lxc/engine/core/graphics/engine/PageInkModelInterface;", "getPageInkModelInterface", "()Lxc/engine/core/graphics/engine/PageInkModelInterface;", "setPageInkModelInterface", "(Lxc/engine/core/graphics/engine/PageInkModelInterface;)V", "startTaskBatch", "", "taskCount", "completeTask", "setRequestDrawCallback", CallMethod.ARG_CALLBACK, "isInitialized", "()Z", "init", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "destroy", "cancelMathAnimation", "onScreenResize", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "onRenderSingleBuffer", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "param", "Lxc/engine/core/fast/data/CurveSegment;", "eraserSize", "refreshFrame", "addSyncTask", "task", "requestRefresh", "onDrawTextureViewFrame", "onRenderMultiBuffer", "isReady", "onPageDataLoaded", "pageIndex", "onSceneReady", "requestMultiBufferRender", "reason", "Lxc/engine/core/graphics/engine/MiCurveEngine$ERefreshReason;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "checkDarkMode", "isDarkMode", "getScreenAreaWS", "Landroid/graphics/RectF;", "getScreenAreaSS", "getShapeEditPoint", "", "isCloseShape", "isShowEditPoint", "getFrontBufferFixAngle", "getSaveRatio", "getDrawRatio", "getAspectRatio", "getToolBarHeight", "setToolBarHeight", Layer.KEY_HEIGHT, "getScaleRatio", "getViewportOffset", "Landroid/graphics/PointF;", "getWorldSpaceRectFromScreenSpaceRect", "rect", "getWorldSpacePointFromScreenSpacePoint", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "getCurPageWorldRect", "position", "point", "getScreenSpaceRectFromWorldSpaceRect", "getScreenSpacePointFromWorldSpacePoint", "getRectSSFromSaveRect", "saveRect", "getRectWSFromSaveRect", "getSaveRect", "getSelectedSpanDataList", "getAnimatorHandle", "isInfinite", "initSpanData", "releaseSpanData", "getSpanData", "getPointMaxSizeFromGLES", "getPointMaxSize", "Companion", "EPageMode", "VSyncCallback", "ERefreshReason", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiCurveEngine {
    private static final String TAG = "MiCurveEngine";
    private final AnchorBoxRenderer anchorBoxRenderer;
    private boolean bDataReady;
    private boolean bIsDarkMode;
    private AtomicBoolean bSceneReadyCalled;
    private boolean bScreenInitialized;
    private Color bgColor;
    private final BgRenderer bgRenderer;
    private final BgSystem bgSystem;
    private final BlendRenderer blendRenderer;
    private final Camera camera;
    private Choreographer choreographer;
    private ArrayList<BaseEngineStruct> components;
    private final ComposeSystem composeSystem;
    private final CropFrameRenderer cropFrameRenderer;
    private final CurveRenderer curveRenderer;
    private final CurveSystem curveSystem;
    private final DashedLineRenderer dashedLineRenderer;
    private final DataManager dataManager;
    private final DebugDrawRenderer debugDrawRenderer;
    private final DebugDrawSystem debugDrawSystem;
    private final ExportSystem exportSystem;
    private final FrontBufferSystem frontBufferSystem;
    public EGLContext glContext;
    private final HighLightSystem highLightSystem;
    private final ImageRenderer imgRenderer;
    private final ImageSystem imgSystem;
    private AtomicBoolean isDestroyCalled;
    private volatile boolean isInitialized;
    private LoadingIndicatorCallback loadingIndicatorCallback;
    private final FrameSyncThread mAnimatorHandle;
    private float mPointMaxSize;
    private final ConcurrentLinkedQueue<BaseAsyncGLTask> mSyncTask;
    private final CurveRenderer mathCurveRenderer;
    private final MathDataManager mathDataManager;
    private final MathSystem mathSystem;
    private PageInkModelInterface pageInkModelInterface;
    private final PageMaskRender pageMaskRenderer;
    private EPageMode pageMode;
    private final PageSystem pageSystem;
    private PenEngine penEngine;
    private final AtomicBoolean pendingRefresh;
    private final PreviewSystem previewSystem;
    private final RecognitionModel recognitionModel;
    private RequestDrawCallback renderCallback;
    private final TileRenderResource renderRes;
    private CurveEngineRenderService renderService;
    private AtomicInteger reqCount;
    private final ScanEffectRenderer scanEffectRenderer;
    private int screenHeightInPixel;
    private int screenWidthInPixel;
    private final SelectSystem selectSystem;
    private final ShapeRenderer shapeRenderer;
    private WeakReference<SpannedLayoutModel> spanModelRef;
    private List<? extends SpannedData> spannedData;
    private SpannedLayoutModel spannedLayoutModel;
    private final AsyncGLTaskManager taskManager;
    private final AtomicInteger taskTotalCount;
    private final TextSystem textSystem;
    private final TexturePool texturePool;
    private final TileRenderer tileRenderer;
    private float toolbarHeight;
    private final VSyncCallback vsyncCallback;

    /* JADX INFO: compiled from: MiCurveEngine.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lxc/engine/core/graphics/engine/MiCurveEngine$EPageMode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Infinite", "Paginated", "Paginated2", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum EPageMode {
        Infinite,
        Paginated,
        Paginated2;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EPageMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: MiCurveEngine.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lxc/engine/core/graphics/engine/MiCurveEngine$ERefreshReason;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Force", "FinishSelect", "ExitTextEdit", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum ERefreshReason {
        Force,
        FinishSelect,
        ExitTextEdit;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ERefreshReason> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: MiCurveEngine.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CurveSegment.EScenario.values().length];
            try {
                iArr[CurveSegment.EScenario.PenHover.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CurveSegment.EScenario.PenDrawing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CurveSegment.EScenario.EraserHover.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CurveSegment.EScenario.EraserTouch.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CurveSegment.EScenario.ExitHover.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ERefreshReason.values().length];
            try {
                iArr2[ERefreshReason.Force.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ERefreshReason.FinishSelect.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[ERefreshReason.ExitTextEdit.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public MiCurveEngine(PenEngine penEngine) {
        Intrinsics.checkNotNullParameter(penEngine, "penEngine");
        this.penEngine = penEngine;
        this.renderService = new CurveEngineRenderService(this);
        this.screenWidthInPixel = 1;
        this.screenHeightInPixel = 1;
        this.pageMode = EPageMode.Infinite;
        this.texturePool = new TexturePool();
        this.components = new ArrayList<>();
        this.camera = new Camera(this);
        this.dataManager = new DataManager(this);
        this.curveRenderer = new CurveRenderer();
        this.mathCurveRenderer = new CurveRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.tileRenderer = new TileRenderer();
        this.bgRenderer = new BgRenderer();
        this.imgRenderer = new ImageRenderer();
        this.dashedLineRenderer = new DashedLineRenderer(this);
        this.blendRenderer = new BlendRenderer();
        this.pageMaskRenderer = new PageMaskRender(this);
        this.cropFrameRenderer = new CropFrameRenderer(this);
        this.debugDrawRenderer = new DebugDrawRenderer(this);
        this.scanEffectRenderer = new ScanEffectRenderer(this);
        this.anchorBoxRenderer = new AnchorBoxRenderer(this);
        this.curveSystem = new CurveSystem(this);
        this.frontBufferSystem = new FrontBufferSystem(this);
        this.bgSystem = new BgSystem(this);
        this.imgSystem = new ImageSystem(this);
        this.textSystem = new TextSystem(this);
        this.selectSystem = new SelectSystem(this);
        this.previewSystem = new PreviewSystem(this);
        this.highLightSystem = new HighLightSystem(this);
        this.composeSystem = new ComposeSystem(this);
        this.pageSystem = new PageSystem(this);
        this.mathSystem = new MathSystem(this);
        this.recognitionModel = new RecognitionModel(this);
        this.debugDrawSystem = new DebugDrawSystem(this);
        this.exportSystem = new ExportSystem(this);
        this.mathDataManager = new MathDataManager();
        this.taskManager = new AsyncGLTaskManager(this);
        this.mAnimatorHandle = new FrameSyncThread("AnimatorSyncThread");
        this.reqCount = new AtomicInteger(0);
        this.bSceneReadyCalled = new AtomicBoolean(false);
        this.renderRes = new TileRenderResource(0, 1, null);
        this.vsyncCallback = new VSyncCallback();
        this.pendingRefresh = new AtomicBoolean(false);
        this.mSyncTask = new ConcurrentLinkedQueue<>();
        this.taskTotalCount = new AtomicInteger(0);
        this.isDestroyCalled = new AtomicBoolean(false);
        this.bgColor = new Color();
        XLog.v(TAG, "init curveEngine:@" + hashCode(), new Object[0]);
    }

    public final PenEngine getPenEngine() {
        return this.penEngine;
    }

    public final void setPenEngine(PenEngine penEngine) {
        Intrinsics.checkNotNullParameter(penEngine, "<set-?>");
        this.penEngine = penEngine;
    }

    public final EGLContext getGlContext() {
        EGLContext eGLContext = this.glContext;
        if (eGLContext != null) {
            return eGLContext;
        }
        Intrinsics.throwUninitializedPropertyAccessException("glContext");
        return null;
    }

    public final void setGlContext(EGLContext eGLContext) {
        Intrinsics.checkNotNullParameter(eGLContext, "<set-?>");
        this.glContext = eGLContext;
    }

    public final CurveEngineRenderService getRenderService() {
        return this.renderService;
    }

    public final void setRenderService(CurveEngineRenderService curveEngineRenderService) {
        Intrinsics.checkNotNullParameter(curveEngineRenderService, "<set-?>");
        this.renderService = curveEngineRenderService;
    }

    public final void setSpannedLayoutModel(SpannedLayoutModel spannedLayoutModel) {
        this.spannedLayoutModel = null;
        this.spanModelRef = new WeakReference<>(spannedLayoutModel);
        if (spannedLayoutModel != null) {
            spannedLayoutModel.registerScreenRenderProvider(this.renderService.getProvider());
        }
        if (spannedLayoutModel != null) {
            final Camera camera = this.camera;
            spannedLayoutModel.setCameraScaleCallback(new DoubleSupplier() { // from class: xc.engine.core.graphics.engine.MiCurveEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    return camera.getScaleToDouble();
                }
            });
        }
    }

    public final SpannedLayoutModel getSpannedLayoutModel() {
        WeakReference<SpannedLayoutModel> weakReference = this.spanModelRef;
        if (weakReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spanModelRef");
            weakReference = null;
        }
        return weakReference.get();
    }

    public final int getScreenWidthInPixel() {
        return this.screenWidthInPixel;
    }

    public final void setScreenWidthInPixel(int i) {
        this.screenWidthInPixel = i;
    }

    public final int getScreenHeightInPixel() {
        return this.screenHeightInPixel;
    }

    public final void setScreenHeightInPixel(int i) {
        this.screenHeightInPixel = i;
    }

    public final EPageMode getPageMode() {
        return this.pageMode;
    }

    public final void setPageMode(EPageMode value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Log.d(TAG, "xl-- [Engine] set page mode: " + value);
        if (this.pageMode != value) {
            this.pageMode = value;
            if (value == EPageMode.Paginated2) {
                this.camera.onUnlimitedToPaginated();
            }
        }
    }

    public final TexturePool getTexturePool() {
        return this.texturePool;
    }

    public final ArrayList<BaseEngineStruct> getComponents() {
        return this.components;
    }

    public final void setComponents(ArrayList<BaseEngineStruct> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.components = arrayList;
    }

    public final Camera getCamera() {
        return this.camera;
    }

    public final DataManager getDataManager() {
        return this.dataManager;
    }

    public final CurveRenderer getCurveRenderer() {
        return this.curveRenderer;
    }

    public final CurveRenderer getMathCurveRenderer() {
        return this.mathCurveRenderer;
    }

    public final ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public final TileRenderer getTileRenderer() {
        return this.tileRenderer;
    }

    public final BgRenderer getBgRenderer() {
        return this.bgRenderer;
    }

    public final ImageRenderer getImgRenderer() {
        return this.imgRenderer;
    }

    public final DashedLineRenderer getDashedLineRenderer() {
        return this.dashedLineRenderer;
    }

    public final BlendRenderer getBlendRenderer() {
        return this.blendRenderer;
    }

    public final PageMaskRender getPageMaskRenderer() {
        return this.pageMaskRenderer;
    }

    public final CropFrameRenderer getCropFrameRenderer() {
        return this.cropFrameRenderer;
    }

    public final DebugDrawRenderer getDebugDrawRenderer() {
        return this.debugDrawRenderer;
    }

    public final ScanEffectRenderer getScanEffectRenderer() {
        return this.scanEffectRenderer;
    }

    public final AnchorBoxRenderer getAnchorBoxRenderer() {
        return this.anchorBoxRenderer;
    }

    public final CurveSystem getCurveSystem() {
        return this.curveSystem;
    }

    public final FrontBufferSystem getFrontBufferSystem() {
        return this.frontBufferSystem;
    }

    public final BgSystem getBgSystem() {
        return this.bgSystem;
    }

    public final ImageSystem getImgSystem() {
        return this.imgSystem;
    }

    public final TextSystem getTextSystem() {
        return this.textSystem;
    }

    public final SelectSystem getSelectSystem() {
        return this.selectSystem;
    }

    public final PreviewSystem getPreviewSystem() {
        return this.previewSystem;
    }

    public final HighLightSystem getHighLightSystem() {
        return this.highLightSystem;
    }

    public final ComposeSystem getComposeSystem() {
        return this.composeSystem;
    }

    public final PageSystem getPageSystem() {
        return this.pageSystem;
    }

    public final MathSystem getMathSystem() {
        return this.mathSystem;
    }

    public final RecognitionModel getRecognitionModel() {
        return this.recognitionModel;
    }

    public final ExportSystem getExportSystem() {
        return this.exportSystem;
    }

    public final MathDataManager getMathDataManager() {
        return this.mathDataManager;
    }

    public final AsyncGLTaskManager getTaskManager() {
        return this.taskManager;
    }

    public final RequestDrawCallback getRenderCallback() {
        return this.renderCallback;
    }

    public final void setRenderCallback(RequestDrawCallback requestDrawCallback) {
        this.renderCallback = requestDrawCallback;
    }

    public final LoadingIndicatorCallback getLoadingIndicatorCallback() {
        return this.loadingIndicatorCallback;
    }

    public final void setLoadingIndicatorCallback(LoadingIndicatorCallback loadingIndicatorCallback) {
        this.loadingIndicatorCallback = loadingIndicatorCallback;
    }

    /* JADX INFO: renamed from: isDestroyCalled, reason: from getter */
    public final AtomicBoolean getIsDestroyCalled() {
        return this.isDestroyCalled;
    }

    public final void setDestroyCalled(AtomicBoolean atomicBoolean) {
        Intrinsics.checkNotNullParameter(atomicBoolean, "<set-?>");
        this.isDestroyCalled = atomicBoolean;
    }

    public final PageInkModelInterface getPageInkModelInterface() {
        return this.pageInkModelInterface;
    }

    public final void setPageInkModelInterface(PageInkModelInterface pageInkModelInterface) {
        this.pageInkModelInterface = pageInkModelInterface;
    }

    public final void startTaskBatch(int taskCount) {
        if (taskCount <= 0) {
            return;
        }
        this.taskTotalCount.addAndGet(taskCount);
        if (this.taskTotalCount.get() > 0) {
            XLog.v(TAG, "One task batch execute && showLoadingIcon TotalTaskCount [%d]", Integer.valueOf(this.taskTotalCount.get()));
            LoadingIndicatorCallback loadingIndicatorCallback = this.loadingIndicatorCallback;
            if (loadingIndicatorCallback != null) {
                loadingIndicatorCallback.showLoadingIndicator();
            }
        }
    }

    public final void completeTask() {
        LoadingIndicatorCallback loadingIndicatorCallback;
        int iDecrementAndGet = this.taskTotalCount.decrementAndGet();
        XLog.v(TAG, "One task batch finish TotalTaskCount[" + iDecrementAndGet + "]", new Object[0]);
        if (iDecrementAndGet > 0 || (loadingIndicatorCallback = this.loadingIndicatorCallback) == null) {
            return;
        }
        loadingIndicatorCallback.hideLoadingIndicator();
    }

    public final void setRequestDrawCallback(RequestDrawCallback callback) {
        this.renderCallback = callback;
    }

    /* JADX INFO: renamed from: isInitialized, reason: from getter */
    public final boolean getIsInitialized() {
        return this.isInitialized;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.isInitialized) {
            return;
        }
        this.mPointMaxSize = getPointMaxSizeFromGLES();
        if (this.penEngine.isSurfaceView) {
            this.choreographer = Choreographer.getInstance();
        }
        checkDarkMode();
        this.curveRenderer.init(context);
        this.mathCurveRenderer.init(context);
        this.shapeRenderer.init(context);
        this.tileRenderer.init(context);
        this.bgRenderer.init(context);
        this.imgRenderer.init(context);
        this.blendRenderer.init(context);
        Iterator<T> it = this.components.iterator();
        while (it.hasNext()) {
            ((BaseEngineStruct) it.next()).init(context);
        }
        this.mAnimatorHandle.start();
        this.isInitialized = true;
        this.mathDataManager.setMathSystem(this.mathSystem);
        EraserOperationManager.getInstance().init(this);
        this.renderRes.init(this.texturePool);
    }

    public final void destroy() {
        if (this.isDestroyCalled.getAndSet(true)) {
            return;
        }
        this.pageInkModelInterface = null;
        this.loadingIndicatorCallback = null;
        Iterator it = CollectionsKt.reversed(this.components).iterator();
        while (it.hasNext()) {
            ((BaseEngineStruct) it.next()).destroy();
        }
        this.blendRenderer.destroy();
        this.imgRenderer.destroy();
        this.bgRenderer.destroy();
        this.tileRenderer.destroy();
        this.shapeRenderer.destroy();
        this.curveRenderer.destroy();
        this.mathCurveRenderer.destroy();
        this.renderRes.destroy(this.texturePool);
        this.mAnimatorHandle.quitSafely();
        this.texturePool.onDestroy();
        this.recognitionModel.dispose();
        WeakReference<SpannedLayoutModel> weakReference = this.spanModelRef;
        if (weakReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spanModelRef");
            weakReference = null;
        }
        SpannedLayoutModel spannedLayoutModel = weakReference.get();
        if (spannedLayoutModel != null) {
            spannedLayoutModel.setCameraScaleCallback(null);
        }
        XLog.v(TAG, "destroy curveEngine:@" + hashCode(), new Object[0]);
    }

    public final void cancelMathAnimation() {
        this.mathSystem.cancelAnimation();
    }

    public final void onScreenResize(int w, int h) {
        Log.d(TAG, "xl-- [Engine] onScreenResize: " + w + ", " + h);
        int i = MarkPropConfig.screenRotation;
        if (i == 4 || i == 7) {
            h = w;
            w = h;
        }
        if (this.screenWidthInPixel != w || this.screenHeightInPixel != h) {
            this.screenWidthInPixel = w;
            this.screenHeightInPixel = h;
            Iterator<T> it = this.components.iterator();
            while (it.hasNext()) {
                ((BaseEngineStruct) it.next()).onScreenResize();
            }
            this.texturePool.onScreenResize(w, h);
            this.curveSystem.update(false);
        }
        if (this.bScreenInitialized) {
            return;
        }
        Log.d(TAG, "xl-- [Engine] screen initialized");
        if (!this.camera.getBViewportInitialized()) {
            this.camera.setRealtimeDrawRatio((isInfinite() ? w : Math.max(w, h + this.toolbarHeight)) / 1000.0f);
            this.camera.setScreenSize(w, h);
        }
        this.bScreenInitialized = true;
        onSceneReady();
    }

    public final void onRenderSingleBuffer(BufferInfo bufferInfo, DataCurve dataCurve, CurveSegment param, float eraserSize) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(param, "param");
        int i = WhenMappings.$EnumSwitchMapping$0[param.getScenario().ordinal()];
        if (i == 1) {
            this.frontBufferSystem.drawHoverBrush(bufferInfo, dataCurve, param, eraserSize, this.bgColor);
            return;
        }
        if (i == 2) {
            FrontBufferSystem frontBufferSystem = this.frontBufferSystem;
            Intrinsics.checkNotNull(dataCurve);
            frontBufferSystem.drawCurve(bufferInfo, dataCurve, param);
        } else if (i == 3) {
            this.frontBufferSystem.drawHoverBrush(bufferInfo, dataCurve, param, eraserSize, this.bgColor);
        } else if (i == 4) {
            this.frontBufferSystem.drawEraser(bufferInfo, param, eraserSize, this.bgColor);
        } else {
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            this.frontBufferSystem.clean(bufferInfo);
        }
    }

    /* JADX INFO: compiled from: MiCurveEngine.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lxc/engine/core/graphics/engine/MiCurveEngine$VSyncCallback;", "Landroid/view/Choreographer$FrameCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "doFrame", "", "frameTimeNanos", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class VSyncCallback implements Choreographer.FrameCallback {
        public VSyncCallback() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            MiCurveEngine.this.pendingRefresh.set(false);
            MiCurveEngine.requestMultiBufferRender$default(MiCurveEngine.this, null, 1, null);
        }
    }

    public final void refreshFrame() {
        if (this.pendingRefresh.compareAndSet(false, true)) {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                choreographer.postFrameCallback(this.vsyncCallback);
            } else {
                this.pendingRefresh.set(false);
            }
        }
    }

    public static /* synthetic */ void addSyncTask$default(MiCurveEngine miCurveEngine, BaseAsyncGLTask baseAsyncGLTask, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        miCurveEngine.addSyncTask(baseAsyncGLTask, z);
    }

    public final void addSyncTask(BaseAsyncGLTask task, boolean requestRefresh) {
        Intrinsics.checkNotNullParameter(task, "task");
        synchronized (this.mSyncTask) {
            this.mSyncTask.add(task);
            XLog.d(TAG, " Add SyncTask[" + task.getClass().getSimpleName() + "]@" + task.getTaskId(), new Object[0]);
            Unit unit = Unit.INSTANCE;
        }
        if (requestRefresh) {
            refreshFrame();
        }
    }

    public final void onDrawTextureViewFrame(BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        if (!this.isInitialized) {
            Context mContext = this.penEngine.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            init(mContext);
        }
        onScreenResize(bufferInfo.getWidth(), bufferInfo.getHeight());
        GLES30.glViewport(0, 0, bufferInfo.getWidth(), bufferInfo.getHeight());
        onRenderMultiBuffer(bufferInfo);
    }

    public final void onRenderMultiBuffer(BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        synchronized (this.mSyncTask) {
            while (!this.mSyncTask.isEmpty()) {
                BaseAsyncGLTask baseAsyncGLTaskPoll = this.mSyncTask.poll();
                if (baseAsyncGLTaskPoll.execute(this.renderRes)) {
                    baseAsyncGLTaskPoll.onRenderCompleted();
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        this.frontBufferSystem.release();
        this.reqCount.set(0);
        RectF screenAreaWS = getScreenAreaWS();
        if (this.pageMode != EPageMode.Infinite) {
            this.pageSystem.renderLayer(screenAreaWS);
        }
        this.bgSystem.renderLayer(screenAreaWS);
        this.bgColor = this.bgSystem.getBgColor();
        this.imgSystem.renderLayer(screenAreaWS);
        this.textSystem.renderLayer(screenAreaWS);
        this.curveSystem.renderLayer(screenAreaWS);
        this.selectSystem.renderLayer(screenAreaWS);
        this.mathSystem.render(screenAreaWS);
        this.composeSystem.beginComposeLayer();
        boolean z = this.pageSystem.getIsPdfMode() && !this.curveSystem.getMarkCurveLayer().getBIsEmpty() && this.curveSystem.getMarkCurveLayer().getLayerOutTextureId() > 0;
        if (z) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.bgSystem.getLayerOutTextureId(), null, 2, null);
        }
        if (!this.imgSystem.getBIsEmpty()) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.imgSystem.getLayerOutTextureId(), null, 2, null);
        }
        if (!this.textSystem.getBIsEmpty()) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.textSystem.getLayerOutTextureId(), null, 2, null);
        }
        this.highLightSystem.drawHighLight();
        if (!this.curveSystem.getMarkCurveLayer().getBIsEmpty() && this.curveSystem.getMarkCurveLayer().getLayerOutTextureId() > 0) {
            if (!z && this.imgSystem.getBIsEmpty() && this.textSystem.getBIsEmpty()) {
                ComposeSystem.blendLayer$default(this.composeSystem, this.curveSystem.getMarkCurveLayer().getLayerOutTextureId(), null, 2, null);
            } else {
                this.composeSystem.blendLayer(this.curveSystem.getMarkCurveLayer().getLayerOutTextureId(), ComposeSystem.EBlendMode.Multiply);
            }
        }
        if (!z) {
            this.composeSystem.blendLayerBelow(this.bgSystem.getLayerOutTextureId());
        }
        if (!this.curveSystem.getMainCurveLayer().getBIsEmpty() && this.curveSystem.getMainCurveLayer().getLayerOutTextureId() > 0) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.curveSystem.getMainCurveLayer().getLayerOutTextureId(), null, 2, null);
        }
        if (this.mathSystem.hasRenderContent() && this.mathSystem.getMainTextureId() > 0) {
            TileRenderer.drawFullScreen$default(this.tileRenderer, this.mathSystem.getMainTextureId(), true, false, 0.0f, 12, null);
        }
        if (this.pageMode != EPageMode.Infinite) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.pageSystem.getLayerOutTextureId(), null, 2, null);
        }
        if (this.selectSystem.isActivated() && this.selectSystem.getOutTextureId() > 0) {
            ComposeSystem.blendLayer$default(this.composeSystem, this.selectSystem.getOutTextureId(), null, 2, null);
        }
        GLES30.glBindFramebuffer(36160, bufferInfo.getFrameBufferId());
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        GLES30.glViewport(0, 0, bufferInfo.getWidth(), bufferInfo.getHeight());
        this.tileRenderer.drawFullScreen(this.composeSystem.getLayerOutTextureId(), true, false, getFrontBufferFixAngle());
    }

    public final boolean isReady() {
        return this.isInitialized && this.bScreenInitialized && this.bDataReady;
    }

    public final void onPageDataLoaded(int pageIndex) {
        Log.d(TAG, "xl-- [Engine] onPageDataLoaded: " + pageIndex);
        if (pageIndex == this.pageSystem.getStartPageIndex()) {
            this.bDataReady = true;
            onSceneReady();
        }
        if (isReady()) {
            this.curveSystem.onEntLoaded(pageIndex);
            this.textSystem.markDirty();
            this.camera.refreshView(true);
        }
    }

    private final void onSceneReady() {
        if (isReady() && !this.bSceneReadyCalled.getAndSet(true)) {
            Log.d(TAG, "xl-- [Engine] onSceneReady");
            this.camera.initViewPort();
            this.curveSystem.initUpdate();
            DataManager.calculateInfiniteBoundary$default(this.dataManager, false, 1, null);
            this.highLightSystem.startHighlight();
        }
    }

    public static /* synthetic */ void requestMultiBufferRender$default(MiCurveEngine miCurveEngine, ERefreshReason eRefreshReason, int i, Object obj) {
        if ((i & 1) != 0) {
            eRefreshReason = ERefreshReason.Force;
        }
        miCurveEngine.requestMultiBufferRender(eRefreshReason);
    }

    public final void requestMultiBufferRender(ERefreshReason reason) {
        boolean zIsActivated;
        GLFrontBufferedWrapperRenderer frontBufferedRenderer;
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (isReady()) {
            int i = WhenMappings.$EnumSwitchMapping$1[reason.ordinal()];
            if (i == 1) {
                zIsActivated = true;
            } else {
                if (i != 2 && i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                zIsActivated = this.selectSystem.isActivated();
            }
            if (zIsActivated) {
                if (this.penEngine.isSurfaceView) {
                    if (this.reqCount.addAndGet(1) != 1 || (frontBufferedRenderer = this.penEngine.getFrontBufferedRenderer()) == null) {
                        return;
                    }
                    frontBufferedRenderer.renderOnMultiBuffer();
                    return;
                }
                RequestDrawCallback requestDrawCallback = this.renderCallback;
                if (requestDrawCallback != null) {
                    requestDrawCallback.requestRender();
                }
            }
        }
    }

    public final void onConfigurationChanged(Configuration newConfig) {
        boolean z;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        int i = newConfig.uiMode & 48;
        if (i != 16) {
            z = i != 32 ? this.bIsDarkMode : true;
        } else {
            z = false;
        }
        if (this.bIsDarkMode != z) {
            this.bIsDarkMode = z;
            this.pageSystem.markDirty();
            requestMultiBufferRender$default(this, null, 1, null);
        }
    }

    private final void checkDarkMode() {
        int i = this.penEngine.mContext.getResources().getConfiguration().uiMode & 48;
        if (i == 16) {
            this.bIsDarkMode = false;
        } else {
            if (i != 32) {
                return;
            }
            this.bIsDarkMode = true;
        }
    }

    /* JADX INFO: renamed from: isDarkMode, reason: from getter */
    public final boolean getBIsDarkMode() {
        return this.bIsDarkMode;
    }

    public final RectF getScreenAreaWS() {
        return this.camera.getViewArea();
    }

    public final RectF getScreenAreaSS() {
        return new RectF(0.0f, 0.0f, this.camera.getSizeInPixel().x, this.camera.getSizeInPixel().y);
    }

    public final float[] getShapeEditPoint() {
        ISelectOperator selectOperator = this.penEngine.getSelectOperator();
        if (selectOperator != null) {
            return selectOperator.getEditPoint();
        }
        return null;
    }

    public final boolean isCloseShape() {
        ISelectOperator selectOperator = this.penEngine.getSelectOperator();
        return selectOperator != null && selectOperator.isCloseShape();
    }

    public final boolean isShowEditPoint() {
        ISelectOperator selectOperator = this.penEngine.getSelectOperator();
        return selectOperator != null && selectOperator.isShowEditPoint();
    }

    public final float getFrontBufferFixAngle() {
        int i = MarkPropConfig.screenRotation;
        if (i == 3) {
            return 180.0f;
        }
        if (i != 4) {
            return i != 7 ? 0.0f : 90.0f;
        }
        return 270.0f;
    }

    public final float getSaveRatio() {
        return 1.0f / this.camera.getRealtimeDrawRatio();
    }

    public final float getDrawRatio() {
        return this.camera.getRealtimeDrawRatio();
    }

    public final float getAspectRatio() {
        float f = this.camera.getSizeInPixel().y;
        float f2 = this.camera.getSizeInPixel().x;
        if (f == 0.0f) {
            return 1.0f;
        }
        return f2 / (f + this.toolbarHeight);
    }

    /* JADX INFO: renamed from: getToolBarHeight, reason: from getter */
    public final float getToolbarHeight() {
        return this.toolbarHeight;
    }

    public final void setToolBarHeight(int height) {
        this.toolbarHeight = height;
    }

    public final float getScaleRatio() {
        return this.camera.getScale();
    }

    public final PointF getViewportOffset() {
        RectF screenAreaWS = getScreenAreaWS();
        float scaleRatio = getScaleRatio();
        return new PointF((-screenAreaWS.left) * scaleRatio, (-screenAreaWS.top) * scaleRatio);
    }

    public final RectF getWorldSpaceRectFromScreenSpaceRect(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        float f = this.camera.getSizeInPixel().x;
        float f2 = this.camera.getSizeInPixel().y;
        RectF screenAreaWS = getScreenAreaWS();
        return new RectF(MiMathUtils.INSTANCE.remapRange(rect.left, 0.0f, f, screenAreaWS.left, screenAreaWS.right), MiMathUtils.INSTANCE.remapRange(rect.top, 0.0f, f2, screenAreaWS.top, screenAreaWS.bottom), MiMathUtils.INSTANCE.remapRange(rect.right, 0.0f, f, screenAreaWS.left, screenAreaWS.right), MiMathUtils.INSTANCE.remapRange(rect.bottom, 0.0f, f2, screenAreaWS.top, screenAreaWS.bottom));
    }

    public final PointF getWorldSpacePointFromScreenSpacePoint(float x, float y) {
        float f = this.camera.getSizeInPixel().x;
        float f2 = this.camera.getSizeInPixel().y;
        RectF screenAreaWS = getScreenAreaWS();
        return new PointF(MiMathUtils.INSTANCE.remapRange(x, 0.0f, f, screenAreaWS.left, screenAreaWS.right), MiMathUtils.INSTANCE.remapRange(y, 0.0f, f2, screenAreaWS.top, screenAreaWS.bottom));
    }

    public final RectF getCurPageWorldRect(int position) {
        RectF area = this.pageSystem.getPageData(position).getArea();
        RectF screenAreaWS = getScreenAreaWS();
        RectF rectF = new RectF(0.0f, 0.0f, this.camera.getSizeInPixel().x, this.camera.getSizeInPixel().y);
        return new RectF(MiMathUtils.INSTANCE.remapRange(area.left, screenAreaWS.left, screenAreaWS.right, rectF.left, rectF.right), MiMathUtils.INSTANCE.remapRange(area.top, screenAreaWS.top, screenAreaWS.bottom, rectF.top, rectF.bottom), MiMathUtils.INSTANCE.remapRange(area.right, screenAreaWS.left, screenAreaWS.right, rectF.left, rectF.right), MiMathUtils.INSTANCE.remapRange(area.bottom, screenAreaWS.top, screenAreaWS.bottom, rectF.top, rectF.bottom));
    }

    public final PointF getWorldSpacePointFromScreenSpacePoint(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        float f = this.camera.getSizeInPixel().x;
        float f2 = this.camera.getSizeInPixel().y;
        RectF screenAreaWS = getScreenAreaWS();
        return new PointF(MiMathUtils.INSTANCE.remapRange(point.x, 0.0f, f, screenAreaWS.left, screenAreaWS.right), MiMathUtils.INSTANCE.remapRange(point.y, 0.0f, f2, screenAreaWS.top, screenAreaWS.bottom));
    }

    public final RectF getScreenSpaceRectFromWorldSpaceRect(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        float f = this.camera.getSizeInPixel().x;
        float f2 = this.camera.getSizeInPixel().y;
        RectF screenAreaWS = getScreenAreaWS();
        if (f == 1.0f || f2 == 1.0f) {
            return new RectF();
        }
        return new RectF(MiMathUtils.INSTANCE.remapRange(rect.left, screenAreaWS.left, screenAreaWS.right, 0.0f, f), MiMathUtils.INSTANCE.remapRange(rect.top, screenAreaWS.top, screenAreaWS.bottom, 0.0f, f2), MiMathUtils.INSTANCE.remapRange(rect.right, screenAreaWS.left, screenAreaWS.right, 0.0f, f), MiMathUtils.INSTANCE.remapRange(rect.bottom, screenAreaWS.top, screenAreaWS.bottom, 0.0f, f2));
    }

    public final PointF getScreenSpacePointFromWorldSpacePoint(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        float f = this.camera.getSizeInPixel().x;
        float f2 = this.camera.getSizeInPixel().y;
        RectF screenAreaWS = getScreenAreaWS();
        return new PointF(MiMathUtils.INSTANCE.remapRange(point.x, screenAreaWS.left, screenAreaWS.right, 0.0f, f), MiMathUtils.INSTANCE.remapRange(point.y, screenAreaWS.top, screenAreaWS.bottom, 0.0f, f2));
    }

    public final RectF getRectSSFromSaveRect(RectF saveRect) {
        Intrinsics.checkNotNullParameter(saveRect, "saveRect");
        float saveRatio = getSaveRatio();
        return getScreenSpaceRectFromWorldSpaceRect(new RectF(saveRect.left / saveRatio, saveRect.top / saveRatio, saveRect.right / saveRatio, saveRect.bottom / saveRatio));
    }

    public final RectF getRectWSFromSaveRect(RectF saveRect) {
        Intrinsics.checkNotNullParameter(saveRect, "saveRect");
        float saveRatio = getSaveRatio();
        return new RectF(saveRect.left / saveRatio, saveRect.top / saveRatio, saveRect.right / saveRatio, saveRect.bottom / saveRatio);
    }

    public final RectF getSaveRect(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        float saveRatio = getSaveRatio();
        return new RectF(rect.left * saveRatio, rect.top * saveRatio, rect.right * saveRatio, rect.bottom * saveRatio);
    }

    public final List<SpannedData> getSelectedSpanDataList() {
        EngineBindContext engineBindContext;
        SpannedEditOperator spannedEditOperator;
        SpannedSelectObject spannedSelectObject;
        SpannedLayoutModel spannedLayoutModel = getSpannedLayoutModel();
        if (spannedLayoutModel == null || (engineBindContext = spannedLayoutModel.getEngineBindContext()) == null || (spannedEditOperator = engineBindContext.mSpannedEditOperator) == null || (spannedSelectObject = spannedEditOperator.mSpannedSelectObject) == null) {
            return null;
        }
        return spannedSelectObject.mSpannedDataList;
    }

    /* JADX INFO: renamed from: getAnimatorHandle, reason: from getter */
    public final FrameSyncThread getMAnimatorHandle() {
        return this.mAnimatorHandle;
    }

    public final boolean isInfinite() {
        return this.pageMode == EPageMode.Infinite;
    }

    public final void initSpanData() {
        EngineBindContext engineBindContext;
        if (this.spannedData == null) {
            SpannedLayoutModel spannedLayoutModel = getSpannedLayoutModel();
            this.spannedData = (spannedLayoutModel == null || (engineBindContext = spannedLayoutModel.getEngineBindContext()) == null) ? null : engineBindContext.getAllDataList();
        }
    }

    public final void releaseSpanData() {
        if (this.spannedData != null) {
            this.spannedData = null;
        }
    }

    public final List<SpannedData> getSpanData() {
        return this.spannedData;
    }

    public final float getPointMaxSizeFromGLES() {
        FloatBuffer floatBufferCreateFloatBuffer = GLRenderUtil.createFloatBuffer(2);
        GLES30.glGetFloatv(33901, floatBufferCreateFloatBuffer);
        Log.d(TAG, "getPointMaxSizeFromGLES: " + floatBufferCreateFloatBuffer.get(1));
        return floatBufferCreateFloatBuffer.get(1);
    }

    /* JADX INFO: renamed from: getPointMaxSize, reason: from getter */
    public final float getMPointMaxSize() {
        return this.mPointMaxSize;
    }
}
