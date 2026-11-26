package com.miui.libhandwritingpreview.engine;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import androidx.graphics.lowlatency.BufferInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpreview.PreCurveEngineRenderService;
import com.miui.libhandwritingpreview.manager.PreAsyncGLTaskManager;
import com.miui.libhandwritingpreview.manager.PreDataManager;
import com.miui.libhandwritingpreview.render.PreBgRenderer;
import com.miui.libhandwritingpreview.render.PreCropFrameRenderer;
import com.miui.libhandwritingpreview.render.PreDashedLineRenderer;
import com.miui.libhandwritingpreview.render.PrePageMaskRender;
import com.miui.libhandwritingpreview.system.PreBaseEngineStruct;
import com.miui.libhandwritingpreview.system.PreBgSystem;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.libhandwritingpreview.system.PreComposeSystem;
import com.miui.libhandwritingpreview.system.PreCurveSystem;
import com.miui.libhandwritingpreview.system.PreExportSystem;
import com.miui.libhandwritingpreview.system.PreHighLightSystem;
import com.miui.libhandwritingpreview.system.PreImageSystem;
import com.miui.libhandwritingpreview.system.PrePageSystem;
import com.miui.libhandwritingpreview.system.PreTextSystem;
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
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.listener.LoadingIndicatorCallback;
import xc.engine.api.listener.RequestDrawCallback;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.renderers.BlendRenderer;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.core.graphics.opengl.GLRenderUtil;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.utils.XLog;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: PreMiCurveEngine.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0082\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 ÷\u00012\u00020\u0001:\u0006÷\u0001ø\u0001ù\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0011\u0010®\u0001\u001a\u00030¬\u00012\u0007\u0010¯\u0001\u001a\u00020\nJ\b\u0010°\u0001\u001a\u00030¬\u0001J\u0014\u0010±\u0001\u001a\u00030¬\u00012\n\u0010²\u0001\u001a\u0005\u0018\u00010\u0097\u0001J\u0012\u0010µ\u0001\u001a\u00030¬\u00012\b\u0010¶\u0001\u001a\u00030·\u0001J\b\u0010¸\u0001\u001a\u00030¬\u0001J\b\u0010¹\u0001\u001a\u00030¬\u0001J\u001a\u0010º\u0001\u001a\u00030¬\u00012\u0007\u0010»\u0001\u001a\u00020\n2\u0007\u0010¼\u0001\u001a\u00020\nJ\u0012\u0010½\u0001\u001a\u00030¬\u00012\b\u0010¾\u0001\u001a\u00030\u0095\u0001J\u0012\u0010¿\u0001\u001a\u00030¬\u00012\b\u0010À\u0001\u001a\u00030Á\u0001J\u0012\u0010Â\u0001\u001a\u00030¬\u00012\b\u0010À\u0001\u001a\u00030Á\u0001J\b\u0010Ã\u0001\u001a\u00030\u008b\u0001J\u0011\u0010Ä\u0001\u001a\u00030¬\u00012\u0007\u0010Å\u0001\u001a\u00020\nJ\n\u0010Æ\u0001\u001a\u00030¬\u0001H\u0002J\b\u0010Ç\u0001\u001a\u00030¬\u0001J\n\u0010È\u0001\u001a\u00030¬\u0001H\u0002J\u001b\u0010É\u0001\u001a\u00030¬\u00012\u000f\u0010Ê\u0001\u001a\n\u0012\u0005\u0012\u00030¬\u00010«\u0001H\u0002J\u0014\u0010Ë\u0001\u001a\u00030¬\u00012\n\b\u0002\u0010Ì\u0001\u001a\u00030Í\u0001J\u0012\u0010Î\u0001\u001a\u00030¬\u00012\b\u0010Ï\u0001\u001a\u00030Ð\u0001J\n\u0010Ñ\u0001\u001a\u00030¬\u0001H\u0002J\b\u0010Ò\u0001\u001a\u00030\u008b\u0001J\b\u0010Ó\u0001\u001a\u00030Ô\u0001J\b\u0010Õ\u0001\u001a\u00030Ô\u0001J\n\u0010Ö\u0001\u001a\u0005\u0018\u00010×\u0001J\b\u0010Ø\u0001\u001a\u00030\u008b\u0001J\b\u0010Ù\u0001\u001a\u00030\u008b\u0001J\b\u0010Ú\u0001\u001a\u00030\u009d\u0001J\b\u0010Û\u0001\u001a\u00030\u009d\u0001J\b\u0010Ü\u0001\u001a\u00030\u009d\u0001J\b\u0010Ý\u0001\u001a\u00030\u009d\u0001J\b\u0010Þ\u0001\u001a\u00030\u009d\u0001J\u0011\u0010ß\u0001\u001a\u00030¬\u00012\u0007\u0010à\u0001\u001a\u00020\nJ\b\u0010á\u0001\u001a\u00030\u009d\u0001J\b\u0010â\u0001\u001a\u00030ã\u0001J\u0012\u0010ä\u0001\u001a\u00030Ô\u00012\b\u0010å\u0001\u001a\u00030Ô\u0001J\u001c\u0010æ\u0001\u001a\u00030ã\u00012\b\u0010ç\u0001\u001a\u00030\u009d\u00012\b\u0010è\u0001\u001a\u00030\u009d\u0001J\u0012\u0010æ\u0001\u001a\u00030ã\u00012\b\u0010é\u0001\u001a\u00030ã\u0001J\u0012\u0010ê\u0001\u001a\u00030Ô\u00012\b\u0010å\u0001\u001a\u00030Ô\u0001J\u0012\u0010ë\u0001\u001a\u00030ã\u00012\b\u0010é\u0001\u001a\u00030ã\u0001J\u0012\u0010ì\u0001\u001a\u00030Ô\u00012\b\u0010í\u0001\u001a\u00030Ô\u0001J\u0012\u0010î\u0001\u001a\u00030Ô\u00012\b\u0010í\u0001\u001a\u00030Ô\u0001J\u0012\u0010ï\u0001\u001a\u00030Ô\u00012\b\u0010å\u0001\u001a\u00030Ô\u0001J\u0011\u0010ð\u0001\u001a\f\u0012\u0005\u0012\u00030¨\u0001\u0018\u00010§\u0001J\b\u0010ñ\u0001\u001a\u00030\u008b\u0001J\b\u0010ò\u0001\u001a\u00030¬\u0001J\b\u0010ó\u0001\u001a\u00030¬\u0001J\u0011\u0010ô\u0001\u001a\f\u0012\u0005\u0012\u00030¨\u0001\u0018\u00010§\u0001J\b\u0010õ\u0001\u001a\u00030\u009d\u0001J\b\u0010ö\u0001\u001a\u00030\u009d\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082.¢\u0006\u0002\n\u0000R*\u0010\u001b\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00198F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\"\"\u0004\b'\u0010$R$\u0010)\u001a\u00020(2\u0006\u0010\u001a\u001a\u00020(@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0011\u0010.\u001a\u00020/¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R*\u00102\u001a\u0012\u0012\u0004\u0012\u00020403j\b\u0012\u0004\u0012\u000204`5X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0011\u0010:\u001a\u00020;¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0011\u0010>\u001a\u00020?¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0011\u0010B\u001a\u00020C¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0011\u0010F\u001a\u00020G¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0011\u0010J\u001a\u00020K¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u0011\u0010N\u001a\u00020O¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0011\u0010R\u001a\u00020S¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0011\u0010V\u001a\u00020W¢\u0006\b\n\u0000\u001a\u0004\bX\u0010YR\u0011\u0010Z\u001a\u00020[¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010]R\u0011\u0010^\u001a\u00020_¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u0011\u0010b\u001a\u00020c¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u0011\u0010f\u001a\u00020g¢\u0006\b\n\u0000\u001a\u0004\bh\u0010iR\u0011\u0010j\u001a\u00020k¢\u0006\b\n\u0000\u001a\u0004\bl\u0010mR\u0011\u0010n\u001a\u00020o¢\u0006\b\n\u0000\u001a\u0004\bp\u0010qR\u0011\u0010r\u001a\u00020s¢\u0006\b\n\u0000\u001a\u0004\bt\u0010uR\u0011\u0010v\u001a\u00020w¢\u0006\b\n\u0000\u001a\u0004\bx\u0010yR\u0011\u0010z\u001a\u00020{¢\u0006\b\n\u0000\u001a\u0004\b|\u0010}R\u0013\u0010~\u001a\u00020\u007f¢\u0006\n\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R\u0015\u0010\u0082\u0001\u001a\u00030\u0083\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0015\u0010\u0086\u0001\u001a\u00030\u0087\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001R\u0010\u0010\u008a\u0001\u001a\u00030\u008b\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u008c\u0001\u001a\u00030\u008b\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u008d\u0001\u001a\u00030\u008b\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u008e\u0001\u001a\u00030\u008f\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0090\u0001\u001a\u00030\u0091\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0092\u0001\u001a\u00030\u008f\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0093\u0001\u001a\n\u0012\u0005\u0012\u00030\u0095\u00010\u0094\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0096\u0001\u001a\u0005\u0018\u00010\u0097\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001\"\u0006\b\u009a\u0001\u0010\u009b\u0001R\u0010\u0010\u009c\u0001\u001a\u00030\u009d\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u009e\u0001\u001a\u00030\u009f\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010 \u0001\u001a\u0005\u0018\u00010¡\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¢\u0001\u0010£\u0001\"\u0006\b¤\u0001\u0010¥\u0001R\u0019\u0010¦\u0001\u001a\f\u0012\u0005\u0012\u00030¨\u0001\u0018\u00010§\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010©\u0001\u001a\u0011\u0012\f\u0012\n\u0012\u0005\u0012\u00030¬\u00010«\u00010ª\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u00ad\u0001\u001a\u00030\u009d\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010³\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u001a\u001a\u00030\u008b\u0001@BX\u0086\u000e¢\u0006\n\n\u0000\u001a\u0006\b³\u0001\u0010´\u0001¨\u0006ú\u0001"}, d2 = {"Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", "", "penEngine", "Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PenPreEngine;)V", "getPenEngine", "()Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", "setPenEngine", "screenRotation", "", "glContext", "Ljavax/microedition/khronos/egl/EGLContext;", "getGlContext", "()Ljavax/microedition/khronos/egl/EGLContext;", "setGlContext", "(Ljavax/microedition/khronos/egl/EGLContext;)V", "renderService", "Lcom/miui/libhandwritingpreview/PreCurveEngineRenderService;", "getRenderService", "()Lcom/miui/libhandwritingpreview/PreCurveEngineRenderService;", "setRenderService", "(Lcom/miui/libhandwritingpreview/PreCurveEngineRenderService;)V", "spanModelRef", "Ljava/lang/ref/WeakReference;", "Lxc/richtext/view/SpannedLayoutModel;", "value", "spannedLayoutModel", "getSpannedLayoutModel", "()Lxc/richtext/view/SpannedLayoutModel;", "setSpannedLayoutModel", "(Lxc/richtext/view/SpannedLayoutModel;)V", "screenWidthInPixel", "getScreenWidthInPixel", "()I", "setScreenWidthInPixel", "(I)V", "screenHeightInPixel", "getScreenHeightInPixel", "setScreenHeightInPixel", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$EPageMode;", "pageMode", "getPageMode", "()Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$EPageMode;", "setPageMode", "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$EPageMode;)V", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "getTexturePool", "()Lxc/engine/core/graphics/engine/core/TexturePool;", "components", "Ljava/util/ArrayList;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineStruct;", "Lkotlin/collections/ArrayList;", "getComponents", "()Ljava/util/ArrayList;", "setComponents", "(Ljava/util/ArrayList;)V", UserAvatarUpdateActivity.CAMERA, "Lcom/miui/libhandwritingpreview/system/PreCamera;", "getCamera", "()Lcom/miui/libhandwritingpreview/system/PreCamera;", "dataManager", "Lcom/miui/libhandwritingpreview/manager/PreDataManager;", "getDataManager", "()Lcom/miui/libhandwritingpreview/manager/PreDataManager;", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "getCurveRenderer", "()Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "getShapeRenderer", "()Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "getTileRenderer", "()Lxc/engine/core/graphics/engine/TileRenderer;", "bgRenderer", "Lcom/miui/libhandwritingpreview/render/PreBgRenderer;", "getBgRenderer", "()Lcom/miui/libhandwritingpreview/render/PreBgRenderer;", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "getImgRenderer", "()Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "blendRenderer", "Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "getBlendRenderer", "()Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "pageMaskRenderer", "Lcom/miui/libhandwritingpreview/render/PrePageMaskRender;", "getPageMaskRenderer", "()Lcom/miui/libhandwritingpreview/render/PrePageMaskRender;", "dashedLineRenderer", "Lcom/miui/libhandwritingpreview/render/PreDashedLineRenderer;", "getDashedLineRenderer", "()Lcom/miui/libhandwritingpreview/render/PreDashedLineRenderer;", "cropFrameRenderer", "Lcom/miui/libhandwritingpreview/render/PreCropFrameRenderer;", "getCropFrameRenderer", "()Lcom/miui/libhandwritingpreview/render/PreCropFrameRenderer;", "curveSystem", "Lcom/miui/libhandwritingpreview/system/PreCurveSystem;", "getCurveSystem", "()Lcom/miui/libhandwritingpreview/system/PreCurveSystem;", "bgSystem", "Lcom/miui/libhandwritingpreview/system/PreBgSystem;", "getBgSystem", "()Lcom/miui/libhandwritingpreview/system/PreBgSystem;", "imgSystem", "Lcom/miui/libhandwritingpreview/system/PreImageSystem;", "getImgSystem", "()Lcom/miui/libhandwritingpreview/system/PreImageSystem;", "textSystem", "Lcom/miui/libhandwritingpreview/system/PreTextSystem;", "getTextSystem", "()Lcom/miui/libhandwritingpreview/system/PreTextSystem;", "composeSystem", "Lcom/miui/libhandwritingpreview/system/PreComposeSystem;", "getComposeSystem", "()Lcom/miui/libhandwritingpreview/system/PreComposeSystem;", "pageSystem", "Lcom/miui/libhandwritingpreview/system/PrePageSystem;", "getPageSystem", "()Lcom/miui/libhandwritingpreview/system/PrePageSystem;", "exportSystem", "Lcom/miui/libhandwritingpreview/system/PreExportSystem;", "getExportSystem", "()Lcom/miui/libhandwritingpreview/system/PreExportSystem;", "highLightSystem", "Lcom/miui/libhandwritingpreview/system/PreHighLightSystem;", "getHighLightSystem", "()Lcom/miui/libhandwritingpreview/system/PreHighLightSystem;", "taskManager", "Lcom/miui/libhandwritingpreview/manager/PreAsyncGLTaskManager;", "getTaskManager", "()Lcom/miui/libhandwritingpreview/manager/PreAsyncGLTaskManager;", "bIsDarkMode", "", "bScreenInitialized", "bDataReady", "bSceneReadyCalled", "Ljava/util/concurrent/atomic/AtomicBoolean;", "renderRes", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "pendingRefresh", "mSyncTask", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "renderCallback", "Lxc/engine/api/listener/RequestDrawCallback;", "getRenderCallback", "()Lxc/engine/api/listener/RequestDrawCallback;", "setRenderCallback", "(Lxc/engine/api/listener/RequestDrawCallback;)V", "toolbarHeight", "", "taskTotalCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "loadingIndicatorCallback", "Lxc/engine/api/listener/LoadingIndicatorCallback;", "getLoadingIndicatorCallback", "()Lxc/engine/api/listener/LoadingIndicatorCallback;", "setLoadingIndicatorCallback", "(Lxc/engine/api/listener/LoadingIndicatorCallback;)V", "spannedData", "", "Lxc/richtext/data/SpannedData;", "readyListeners", "", "Lkotlin/Function0;", "", "mPointMaxSize", "startTaskBatch", "taskCount", "completeTask", "setRequestDrawCallback", CallMethod.ARG_CALLBACK, "isInitialized", "()Z", "init", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "destroy", "registerLoadFinishCallback", "onScreenResize", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "addSyncTask", "task", "onDrawTextureViewFrame", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "onRenderMultiBuffer", "isReady", "onPageDataLoaded", "pageIndex", "onSceneReady", "startHighlight", "notifyReadyListeners", "addReadyListener", "listener", "requestMultiBufferRender", "reason", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$ERefreshReason;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "checkDarkMode", "isDarkMode", "getScreenAreaWS", "Landroid/graphics/RectF;", "getScreenAreaSS", "getShapeEditPoint", "", "isCloseShape", "isShowEditPoint", "getFrontBufferFixAngle", "getSaveRatio", "getDrawRatio", "getAspectRatio", "getToolBarHeight", "setToolBarHeight", Layer.KEY_HEIGHT, "getScaleRatio", "getViewportOffset", "Landroid/graphics/PointF;", "getWorldSpaceRectFromScreenSpaceRect", "rect", "getWorldSpacePointFromScreenSpacePoint", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "point", "getScreenSpaceRectFromWorldSpaceRect", "getScreenSpacePointFromWorldSpacePoint", "getRectSSFromSaveRect", "saveRect", "getRectWSFromSaveRect", "getSaveRect", "getSelectedSpanDataList", "isInfinite", "initSpanData", "releaseSpanData", "getSpanData", "getPointMaxSizeFromGLES", "getPointMaxSize", "Companion", "EPageMode", "ERefreshReason", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreMiCurveEngine {
    private static final String TAG = "PreMiCurveEngine";
    private boolean bDataReady;
    private boolean bIsDarkMode;
    private AtomicBoolean bSceneReadyCalled;
    private boolean bScreenInitialized;
    private final PreBgRenderer bgRenderer;
    private final PreBgSystem bgSystem;
    private final BlendRenderer blendRenderer;
    private final PreCamera camera;
    private ArrayList<PreBaseEngineStruct> components;
    private final PreComposeSystem composeSystem;
    private final PreCropFrameRenderer cropFrameRenderer;
    private final CurveRenderer curveRenderer;
    private final PreCurveSystem curveSystem;
    private final PreDashedLineRenderer dashedLineRenderer;
    private final PreDataManager dataManager;
    private final PreExportSystem exportSystem;
    public EGLContext glContext;
    private final PreHighLightSystem highLightSystem;
    private final ImageRenderer imgRenderer;
    private final PreImageSystem imgSystem;
    private volatile boolean isInitialized;
    private LoadingIndicatorCallback loadingIndicatorCallback;
    private float mPointMaxSize;
    private final ConcurrentLinkedQueue<BaseAsyncGLTask> mSyncTask;
    private final PrePageMaskRender pageMaskRenderer;
    private EPageMode pageMode;
    private final PrePageSystem pageSystem;
    private PenPreEngine penEngine;
    private final AtomicBoolean pendingRefresh;
    private final List<Function0<Unit>> readyListeners;
    private RequestDrawCallback renderCallback;
    private final TileRenderResource renderRes;
    private PreCurveEngineRenderService renderService;
    private int screenHeightInPixel;
    private int screenRotation;
    private int screenWidthInPixel;
    private final ShapeRenderer shapeRenderer;
    private WeakReference<SpannedLayoutModel> spanModelRef;
    private List<? extends SpannedData> spannedData;
    private SpannedLayoutModel spannedLayoutModel;
    private final PreAsyncGLTaskManager taskManager;
    private final AtomicInteger taskTotalCount;
    private final PreTextSystem textSystem;
    private final TexturePool texturePool;
    private final TileRenderer tileRenderer;
    private float toolbarHeight;

    /* JADX INFO: compiled from: PreMiCurveEngine.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$EPageMode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Infinite", "Paginated", "Paginated2", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum EPageMode {
        Infinite,
        Paginated,
        Paginated2;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EPageMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: PreMiCurveEngine.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine$ERefreshReason;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Force", "FinishSelect", "ExitTextEdit", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum ERefreshReason {
        Force,
        FinishSelect,
        ExitTextEdit;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ERefreshReason> getEntries() {
            return $ENTRIES;
        }
    }

    public PreMiCurveEngine(PenPreEngine penEngine) {
        Intrinsics.checkNotNullParameter(penEngine, "penEngine");
        this.penEngine = penEngine;
        this.renderService = new PreCurveEngineRenderService(this);
        this.screenWidthInPixel = 1;
        this.screenHeightInPixel = 1;
        this.pageMode = EPageMode.Infinite;
        this.texturePool = new TexturePool();
        this.components = new ArrayList<>();
        this.camera = new PreCamera(this);
        this.dataManager = new PreDataManager(this);
        this.curveRenderer = new CurveRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.tileRenderer = new TileRenderer();
        this.bgRenderer = new PreBgRenderer();
        this.imgRenderer = new ImageRenderer();
        this.blendRenderer = new BlendRenderer();
        this.pageMaskRenderer = new PrePageMaskRender(this);
        this.dashedLineRenderer = new PreDashedLineRenderer(this);
        this.cropFrameRenderer = new PreCropFrameRenderer(this);
        this.curveSystem = new PreCurveSystem(this);
        this.bgSystem = new PreBgSystem(this);
        this.imgSystem = new PreImageSystem(this);
        this.textSystem = new PreTextSystem(this);
        this.composeSystem = new PreComposeSystem(this);
        this.pageSystem = new PrePageSystem(this);
        this.exportSystem = new PreExportSystem(this);
        this.highLightSystem = new PreHighLightSystem(this);
        this.taskManager = new PreAsyncGLTaskManager(this);
        this.bSceneReadyCalled = new AtomicBoolean(false);
        this.renderRes = new TileRenderResource(0, 1, null);
        this.pendingRefresh = new AtomicBoolean(false);
        this.mSyncTask = new ConcurrentLinkedQueue<>();
        this.taskTotalCount = new AtomicInteger(0);
        this.readyListeners = new ArrayList();
    }

    public final PenPreEngine getPenEngine() {
        return this.penEngine;
    }

    public final void setPenEngine(PenPreEngine penPreEngine) {
        Intrinsics.checkNotNullParameter(penPreEngine, "<set-?>");
        this.penEngine = penPreEngine;
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

    public final PreCurveEngineRenderService getRenderService() {
        return this.renderService;
    }

    public final void setRenderService(PreCurveEngineRenderService preCurveEngineRenderService) {
        Intrinsics.checkNotNullParameter(preCurveEngineRenderService, "<set-?>");
        this.renderService = preCurveEngineRenderService;
    }

    public final void setSpannedLayoutModel(SpannedLayoutModel spannedLayoutModel) {
        this.spannedLayoutModel = null;
        this.spanModelRef = new WeakReference<>(spannedLayoutModel);
        if (spannedLayoutModel != null) {
            spannedLayoutModel.registerScreenRenderProvider(this.renderService.getProvider());
        }
        if (spannedLayoutModel != null) {
            final PreCamera preCamera = this.camera;
            spannedLayoutModel.setCameraScaleCallback(new DoubleSupplier() { // from class: com.miui.libhandwritingpreview.engine.PreMiCurveEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    return preCamera.getScaleToDouble();
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

    public final ArrayList<PreBaseEngineStruct> getComponents() {
        return this.components;
    }

    public final void setComponents(ArrayList<PreBaseEngineStruct> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.components = arrayList;
    }

    public final PreCamera getCamera() {
        return this.camera;
    }

    public final PreDataManager getDataManager() {
        return this.dataManager;
    }

    public final CurveRenderer getCurveRenderer() {
        return this.curveRenderer;
    }

    public final ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public final TileRenderer getTileRenderer() {
        return this.tileRenderer;
    }

    public final PreBgRenderer getBgRenderer() {
        return this.bgRenderer;
    }

    public final ImageRenderer getImgRenderer() {
        return this.imgRenderer;
    }

    public final BlendRenderer getBlendRenderer() {
        return this.blendRenderer;
    }

    public final PrePageMaskRender getPageMaskRenderer() {
        return this.pageMaskRenderer;
    }

    public final PreDashedLineRenderer getDashedLineRenderer() {
        return this.dashedLineRenderer;
    }

    public final PreCropFrameRenderer getCropFrameRenderer() {
        return this.cropFrameRenderer;
    }

    public final PreCurveSystem getCurveSystem() {
        return this.curveSystem;
    }

    public final PreBgSystem getBgSystem() {
        return this.bgSystem;
    }

    public final PreImageSystem getImgSystem() {
        return this.imgSystem;
    }

    public final PreTextSystem getTextSystem() {
        return this.textSystem;
    }

    public final PreComposeSystem getComposeSystem() {
        return this.composeSystem;
    }

    public final PrePageSystem getPageSystem() {
        return this.pageSystem;
    }

    public final PreExportSystem getExportSystem() {
        return this.exportSystem;
    }

    public final PreHighLightSystem getHighLightSystem() {
        return this.highLightSystem;
    }

    public final PreAsyncGLTaskManager getTaskManager() {
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
        XLog.v(TAG, "One task batch finish TotalTaskCount[" + iDecrementAndGet + ']', new Object[0]);
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
        checkDarkMode();
        this.curveRenderer.init(context);
        this.shapeRenderer.init(context);
        this.tileRenderer.init(context);
        this.bgRenderer.init(context);
        this.imgRenderer.init(context);
        this.blendRenderer.init(context);
        Iterator<T> it = this.components.iterator();
        while (it.hasNext()) {
            ((PreBaseEngineStruct) it.next()).init();
        }
        this.isInitialized = true;
        notifyReadyListeners();
        this.renderRes.init(this.texturePool);
        registerLoadFinishCallback();
    }

    public final void destroy() {
        if (this.isInitialized) {
            this.loadingIndicatorCallback = null;
            WeakReference<SpannedLayoutModel> weakReference = this.spanModelRef;
            if (weakReference == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spanModelRef");
                weakReference = null;
            }
            SpannedLayoutModel spannedLayoutModel = weakReference.get();
            if (spannedLayoutModel != null) {
                spannedLayoutModel.registerLoadFinishCallback(null);
            }
            WeakReference<SpannedLayoutModel> weakReference2 = this.spanModelRef;
            if (weakReference2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spanModelRef");
                weakReference2 = null;
            }
            SpannedLayoutModel spannedLayoutModel2 = weakReference2.get();
            if (spannedLayoutModel2 != null) {
                spannedLayoutModel2.setCameraScaleCallback(null);
            }
            Iterator it = CollectionsKt.reversed(this.components).iterator();
            while (it.hasNext()) {
                ((PreBaseEngineStruct) it.next()).destroy();
            }
            this.blendRenderer.destroy();
            this.imgRenderer.destroy();
            this.bgRenderer.destroy();
            this.tileRenderer.destroy();
            this.shapeRenderer.destroy();
            this.curveRenderer.destroy();
            this.renderRes.destroy(this.texturePool);
            this.texturePool.onDestroy();
        }
    }

    public final void registerLoadFinishCallback() {
        SpannedLayoutModel.OnSpannedLoadListener onSpannedLoadListener = new SpannedLayoutModel.OnSpannedLoadListener() { // from class: com.miui.libhandwritingpreview.engine.PreMiCurveEngine$registerLoadFinishCallback$callback$1
            @Override // xc.richtext.view.SpannedLayoutModel.OnSpannedLoadListener
            public void onSpannedLoadFinish() {
                RequestDrawCallback renderCallback = this.this$0.getRenderCallback();
                if (renderCallback != null) {
                    renderCallback.requestRender();
                }
            }

            @Override // xc.richtext.view.SpannedLayoutModel.OnSpannedLoadListener
            public void onRefreshImage(final int mid) {
                PreMiCurveEngine preMiCurveEngine = this.this$0;
                final PreMiCurveEngine preMiCurveEngine2 = this.this$0;
                preMiCurveEngine.addSyncTask(new BaseAsyncGLTask() { // from class: com.miui.libhandwritingpreview.engine.PreMiCurveEngine$registerLoadFinishCallback$callback$1$onRefreshImage$1
                    @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
                    public boolean execute(TileRenderResource res) {
                        Intrinsics.checkNotNullParameter(res, "res");
                        preMiCurveEngine2.getImgSystem().refreshCacheSpanTextureId(mid);
                        preMiCurveEngine2.getImgSystem().markDirty();
                        return super.execute(res);
                    }
                });
            }
        };
        WeakReference<SpannedLayoutModel> weakReference = this.spanModelRef;
        if (weakReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spanModelRef");
            weakReference = null;
        }
        SpannedLayoutModel spannedLayoutModel = weakReference.get();
        if (spannedLayoutModel != null) {
            spannedLayoutModel.registerLoadFinishCallback(onSpannedLoadListener);
        }
    }

    public final void onScreenResize(int w, int h) {
        if (this.screenWidthInPixel != w || this.screenHeightInPixel != h) {
            Log.d(TAG, "xl-- [Engine] onScreenResize: " + w + ", " + h);
            this.screenWidthInPixel = w;
            this.screenHeightInPixel = h;
            Iterator<T> it = this.components.iterator();
            while (it.hasNext()) {
                ((PreBaseEngineStruct) it.next()).onScreenResize();
            }
            this.camera.setRealtimeDrawRatio((isInfinite() ? w : Math.max(w, h + this.toolbarHeight)) / 1000.0f);
            this.camera.setScreenSize(w, h);
            this.texturePool.onScreenResize(w, h);
            this.curveSystem.update();
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
        notifyReadyListeners();
        onSceneReady();
    }

    public final void addSyncTask(BaseAsyncGLTask task) {
        Intrinsics.checkNotNullParameter(task, "task");
        synchronized (this.mSyncTask) {
            this.mSyncTask.add(task);
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
                if (baseAsyncGLTaskPoll != null && baseAsyncGLTaskPoll.execute(this.renderRes)) {
                    baseAsyncGLTaskPoll.onRenderCompleted();
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        RectF screenAreaWS = getScreenAreaWS();
        if (this.pageMode != EPageMode.Infinite) {
            this.pageSystem.renderLayer(screenAreaWS);
        }
        this.bgSystem.renderLayer(screenAreaWS);
        this.imgSystem.renderLayer(screenAreaWS);
        this.textSystem.renderLayer(screenAreaWS);
        this.curveSystem.renderLayer(screenAreaWS);
        this.composeSystem.beginComposeLayer();
        boolean z = this.pageSystem.getIsPdfMode() && !this.curveSystem.getMarkCurveLayer().getBIsEmpty() && this.curveSystem.getMarkCurveLayer().getLayerOutTextureId() > 0;
        if (z) {
            PreComposeSystem.blendLayer$default(this.composeSystem, this.bgSystem.getLayerOutTextureId(), null, 2, null);
        }
        if (!this.imgSystem.getBIsEmpty()) {
            PreComposeSystem.blendLayer$default(this.composeSystem, this.imgSystem.getLayerOutTextureId(), null, 2, null);
        }
        if (!this.textSystem.getBIsEmpty()) {
            PreComposeSystem.blendLayer$default(this.composeSystem, this.textSystem.getLayerOutTextureId(), null, 2, null);
        }
        this.highLightSystem.drawHighLight();
        if (!this.curveSystem.getMarkCurveLayer().getBIsEmpty() && this.curveSystem.getMarkCurveLayer().getLayerOutTextureId() > 0) {
            if (!z && this.imgSystem.getBIsEmpty() && this.textSystem.getBIsEmpty()) {
                PreComposeSystem.blendLayer$default(this.composeSystem, this.curveSystem.getMarkCurveLayer().getLayerOutTextureId(), null, 2, null);
            } else {
                this.composeSystem.blendLayer(this.curveSystem.getMarkCurveLayer().getLayerOutTextureId(), PreComposeSystem.EBlendMode.Multiply);
            }
        }
        if (!z) {
            this.composeSystem.blendLayerBelow(this.bgSystem.getLayerOutTextureId());
        }
        if (!this.curveSystem.getMainCurveLayer().getBIsEmpty() && this.curveSystem.getMainCurveLayer().getLayerOutTextureId() > 0) {
            PreComposeSystem.blendLayer$default(this.composeSystem, this.curveSystem.getMainCurveLayer().getLayerOutTextureId(), null, 2, null);
        }
        if (this.pageMode != EPageMode.Infinite) {
            PreComposeSystem.blendLayer$default(this.composeSystem, this.pageSystem.getLayerOutTextureId(), null, 2, null);
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
            notifyReadyListeners();
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
            PreDataManager.calculateInfiniteBoundary$default(this.dataManager, false, 1, null);
        }
    }

    public final void startHighlight() {
        addReadyListener(new Function0() { // from class: com.miui.libhandwritingpreview.engine.PreMiCurveEngine$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PreMiCurveEngine.startHighlight$lambda$5(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startHighlight$lambda$5(PreMiCurveEngine preMiCurveEngine) {
        preMiCurveEngine.highLightSystem.startHighlight();
        return Unit.INSTANCE;
    }

    private final void notifyReadyListeners() {
        if (isReady()) {
            Iterator<T> it = this.readyListeners.iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            this.readyListeners.clear();
        }
    }

    private final void addReadyListener(Function0<Unit> listener) {
        if (isReady()) {
            listener.invoke();
        } else {
            this.readyListeners.add(listener);
        }
    }

    public static /* synthetic */ void requestMultiBufferRender$default(PreMiCurveEngine preMiCurveEngine, ERefreshReason eRefreshReason, int i, Object obj) {
        if ((i & 1) != 0) {
            eRefreshReason = ERefreshReason.Force;
        }
        preMiCurveEngine.requestMultiBufferRender(eRefreshReason);
    }

    public final void requestMultiBufferRender(ERefreshReason reason) {
        RequestDrawCallback requestDrawCallback;
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (isReady() && ERefreshReason.Force == reason && (requestDrawCallback = this.renderCallback) != null) {
            requestDrawCallback.requestRender();
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
        int i = this.screenRotation;
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
        return floatBufferCreateFloatBuffer.get(1);
    }

    /* JADX INFO: renamed from: getPointMaxSize, reason: from getter */
    public final float getMPointMaxSize() {
        return this.mPointMaxSize;
    }
}
