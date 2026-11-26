package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.listener.StepChangedListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.listenerProxy.OnExtraDataListenerProxy;
import com.miui.libhandwritingpreview.PreCurveEngineRenderService;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.system.PreBgSystem;
import com.miui.libhandwritingpreview.system.PreCurveSystem;
import com.miui.libhandwritingpreview.system.PreHighLightSystem;
import com.miui.libhandwritingpreview.system.PreImageSystem;
import com.miui.libhandwritingpreview.system.PreTextSystem;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import miuix.androidbasewidget.widget.ProgressBar;
import xc.engine.api.CreationPageSDK;
import xc.engine.data.CsBufferBean;
import xc.engine.data.IDataAccess;
import xc.engine.data.IDataListener;
import xc.engine.data.OnExtraDataListener;
import xc.engine.data.PathInfo;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.FileUtil;
import xc.engine.utils.XLog;
import xc.richtext.EngineBindContext;
import xc.richtext.api.OnSpannedEditListener;
import xc.richtext.api.OnSpannedHightListener;
import xc.richtext.api.SpannedEditLayout;
import xc.richtext.api.SpannedEditModel;
import xc.richtext.api.SpannedLayoutInterface;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.ISpannedSelectObject;

/* JADX INFO: compiled from: PagePreItemView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008e\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 Ô\u00012\u00020\u00012\u00020\u0002:\u0002Ô\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010W\u001a\u00020X2\b\u0010Y\u001a\u0004\u0018\u00010\u0010J\u0010\u0010Z\u001a\u00020X2\u0006\u0010[\u001a\u00020\\H\u0002J\u0010\u0010]\u001a\u00020X2\u0006\u0010^\u001a\u00020_H\u0014J\u000e\u0010`\u001a\u00020X2\u0006\u0010a\u001a\u00020\nJ\u000e\u0010b\u001a\u00020X2\u0006\u0010c\u001a\u00020\nJ\u000e\u0010d\u001a\u00020X2\u0006\u0010e\u001a\u00020\nJ\u000e\u0010f\u001a\u00020X2\u0006\u0010C\u001a\u00020DJ\u0010\u0010g\u001a\u00020X2\u0006\u0010[\u001a\u00020\\H\u0002J(\u0010l\u001a\u00020X2\u0006\u00107\u001a\u00020\u001e2\b\u0010m\u001a\u0004\u0018\u00010\u00182\u0006\u0010n\u001a\u00020,2\u0006\u0010o\u001a\u00020,J\u001e\u0010p\u001a\u00020X2\u0006\u0010q\u001a\u00020\u001e2\u0006\u0010r\u001a\u00020\u001e2\u0006\u0010s\u001a\u00020\u001eJ\u0016\u0010t\u001a\u00020X2\u0006\u0010q\u001a\u00020\u001e2\u0006\u0010r\u001a\u00020\u001eJ\u0010\u0010u\u001a\u00020X2\b\u0010v\u001a\u0004\u0018\u00010wJ\u0010\u0010x\u001a\u00020X2\b\u0010y\u001a\u0004\u0018\u00010\u001cJ\u0018\u0010~\u001a\u00020X2\b\u0010\u007f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010.\u001a\u00020\u001eJ\u0018\u0010\u0080\u0001\u001a\u00020X2\u0007\u0010\u0081\u0001\u001a\u00020,2\u0006\u0010s\u001a\u00020\u001eJ\u0018\u0010\u0082\u0001\u001a\u00020X2\u0007\u0010\u0081\u0001\u001a\u00020,2\u0006\u0010s\u001a\u00020\u001eJ\u0012\u0010\u0083\u0001\u001a\u00020X2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u000eJ\u0013\u0010\u0085\u0001\u001a\u00020X2\n\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001J\u0011\u0010\u0088\u0001\u001a\u00020X2\b\u0010\u0089\u0001\u001a\u00030\u0087\u0001J\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001J\u0010\u0010\u008c\u0001\u001a\u00020X2\u0007\u0010\u008d\u0001\u001a\u00020\u001eJ\u0011\u0010\u008e\u0001\u001a\u00020X2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001J8\u0010\u008f\u0001\u001a\u00020X2\u0007\u0010\u0090\u0001\u001a\u00020\u001e2\n\u0010\u0091\u0001\u001a\u0005\u0018\u00010\u0092\u00012\u0007\u0010\u008d\u0001\u001a\u00020\u001e2\u000f\u0010\u0093\u0001\u001a\n\u0012\u0005\u0012\u00030\u0095\u00010\u0094\u0001H\u0016J\u0013\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u008d\u0001\u001a\u00020\u001eH\u0016J\u0012\u0010\u0098\u0001\u001a\u00020\u001e2\u0007\u0010\u0099\u0001\u001a\u00020\u001eH\u0002J\u0013\u0010\u009a\u0001\u001a\u00020\u001e2\b\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0002J\u0007\u0010\u009d\u0001\u001a\u00020XJ\t\u0010\u009e\u0001\u001a\u00020XH\u0002J\u0017\u0010\u009f\u0001\u001a\u00020X2\u0006\u0010q\u001a\u00020,2\u0006\u0010r\u001a\u00020,J\u0010\u0010 \u0001\u001a\u00020\n2\u0007\u0010\u008d\u0001\u001a\u00020\u001eJ\u0010\u0010¡\u0001\u001a\u00020\n2\u0007\u0010\u008d\u0001\u001a\u00020\u001eJ\u0010\u0010¢\u0001\u001a\u00020\n2\u0007\u0010\u008d\u0001\u001a\u00020\u001eJ\u0019\u0010£\u0001\u001a\u00020X2\u0007\u0010¤\u0001\u001a\u00020\n2\u0007\u0010¥\u0001\u001a\u00020\u001eJ5\u0010¦\u0001\u001a\u00020X2\t\u0010§\u0001\u001a\u0004\u0018\u00010\u000e2\n\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u0087\u00012\t\u0010¨\u0001\u001a\u0004\u0018\u00010\u00002\n\u0010©\u0001\u001a\u0005\u0018\u00010ª\u0001J\u0007\u0010«\u0001\u001a\u00020XJ\u000f\u0010¬\u0001\u001a\u00020X2\u0006\u0010c\u001a\u00020\nJ\u0010\u0010\u00ad\u0001\u001a\u00020X2\u0007\u0010®\u0001\u001a\u00020,J\u0013\u0010¯\u0001\u001a\u00020X2\b\u0010°\u0001\u001a\u00030±\u0001H\u0014J\u0019\u0010²\u0001\u001a\u00020X2\u0007\u0010³\u0001\u001a\u00020\u001e2\u0007\u0010\u008d\u0001\u001a\u00020\u001eJ\u0012\u0010´\u0001\u001a\u00020X2\u0007\u0010µ\u0001\u001a\u00020\u001eH\u0002J\u0007\u0010\u0080\u0001\u001a\u00020XJ\u001c\u0010¶\u0001\u001a\u00020X2\t\u0010·\u0001\u001a\u0004\u0018\u00010\u000e2\b\u0010¸\u0001\u001a\u00030\u0087\u0001J:\u0010l\u001a\u00020X2\u0006\u00107\u001a\u00020\u001e2\t\u0010½\u0001\u001a\u0004\u0018\u00010\u00182\u0007\u0010\u0081\u0001\u001a\u00020,2\u0006\u0010s\u001a\u00020\u001e2\u0006\u0010K\u001a\u00020,2\u0006\u0010L\u001a\u00020,J\u000f\u0010¾\u0001\u001a\u00020X2\u0006\u0010r\u001a\u00020,J\u0010\u0010¿\u0001\u001a\u00020X2\u0007\u0010À\u0001\u001a\u00020\nJ\"\u0010Á\u0001\u001a\u00020X2\u0007\u0010\u008d\u0001\u001a\u00020\u001e2\u0007\u0010Â\u0001\u001a\u00020,2\u0007\u0010Ã\u0001\u001a\u00020,J\u0019\u0010Ä\u0001\u001a\u00020X2\u0007\u0010Å\u0001\u001a\u00020,2\u0007\u0010Æ\u0001\u001a\u00020,J\u0019\u0010Ç\u0001\u001a\u00020X2\u0007\u0010Å\u0001\u001a\u00020,2\u0007\u0010Æ\u0001\u001a\u00020,J\u0017\u0010Ê\u0001\u001a\u00020X2\u0006\u0010q\u001a\u00020\u001e2\u0006\u0010r\u001a\u00020\u001eJ\u0006\u0010M\u001a\u00020\nJ\u000f\u0010Ë\u0001\u001a\u00020X2\u0006\u0010q\u001a\u00020\u001eJ\u000f\u0010Ì\u0001\u001a\u00020X2\u0006\u0010h\u001a\u00020\nJ\u0019\u0010Í\u0001\u001a\u00020X2\u0007\u0010Î\u0001\u001a\u00020,2\u0007\u0010Ï\u0001\u001a\u00020,J \u0010Ð\u0001\u001a\n\u0012\u0005\u0012\u00030Ñ\u00010\u0094\u00012\u0006\u0010.\u001a\u00020\u001e2\u0007\u0010¥\u0001\u001a\u00020\u001eJ\u0010\u0010Ò\u0001\u001a\u00020\n2\u0007\u0010Ó\u0001\u001a\u00020\u001eR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R\u000e\u0010&\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010.\u001a\u00020\u001e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00107\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010 \"\u0004\b9\u0010\"R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010BX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020DX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010F\u001a\u00020\u001e2\u0006\u0010F\u001a\u00020\u001e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010 \"\u0004\bH\u0010\"R\u000e\u0010I\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010N\u001a\u0004\u0018\u00010O8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001c\u0010P\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u001a\"\u0004\bR\u0010SR\u000e\u0010T\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020VX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010h\u001a\u0004\u0018\u00010i8F¢\u0006\u0006\u001a\u0004\bj\u0010kR\u0013\u0010z\u001a\u0004\u0018\u00010{8F¢\u0006\u0006\u001a\u0004\b|\u0010}R\u0014\u0010¹\u0001\u001a\u00020,8F¢\u0006\b\u001a\u0006\bº\u0001\u0010»\u0001R\u0014\u0010\u0081\u0001\u001a\u00020,8F¢\u0006\b\u001a\u0006\b¼\u0001\u0010»\u0001R\u0015\u0010È\u0001\u001a\u0004\u0018\u00010\u00188F¢\u0006\u0007\u001a\u0005\bÉ\u0001\u0010\u001a¨\u0006Õ\u0001"}, d2 = {"Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "Landroid/widget/RelativeLayout;", "Lxc/engine/data/OnExtraDataListener;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mContextRef", "Ljava/lang/ref/WeakReference;", "isUnLimitToNormal", "", "mSpannedLayout", "Lxc/richtext/api/SpannedEditLayout;", "entFilePath", "", "mDrawView", "Lcom/miui/libhandwritingpreview/view/PagePreDrawView;", "mProgressBar", "Lmiuix/androidbasewidget/widget/ProgressBar;", "isLoading", "()Z", "setLoading", "(Z)V", "layout", "Landroid/graphics/RectF;", "getLayout", "()Landroid/graphics/RectF;", "stepChangedListener", "Lcom/miui/libhandwritingpage/listener/StepChangedListener;", "visWidth", "", "getVisWidth", "()I", "setVisWidth", "(I)V", "visHeight", "getVisHeight", "setVisHeight", "viewWidth", "isVisible", "searchDataList", "", "Landroid/graphics/Rect;", "filletRadius", "", "drawState", "index", "searchColor", "searchAlpha", "isInitNotFinished", "canvasPath", "Landroid/graphics/Path;", "canvasRadius", "", "isEngineLoading", "position", "getPosition", "setPosition", "scaleInfo", "Lxc/engine/operate/canvas/draw/scale/ScaleInfo;", "lastScaleInfo", "isScaleWaitEnd", "lastOffsetX", "lastOffsetY", "lastScale", "mTvLoading", "Landroid/widget/TextView;", "searchWrapper", "Lcom/miui/libhandwritingpage/newbean/SearchWrapper;", "bottomShieldHeight", "shieldWidth", "getShieldWidth", "setShieldWidth", "unLimitWidth", "unLimitHeight", "curX", "curY", "isUnLimit", "model", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "pageLocation", "getPageLocation", "setPageLocation", "(Landroid/graphics/RectF;)V", "isBindSurfaceView", "mHandler", "Landroid/os/Handler;", "init", "", "pageDrawView", "initDrawView", "layoutParams", "Landroid/widget/RelativeLayout$LayoutParams;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "initLoadingIcon", "useDark", "showLoadingBar", "show", "onPDFOrEngineLoadFinish", "isPdf", "setSearchWrapper", "initSpanned", "mode", "Lxc/richtext/api/SpannedEditModel;", "getMode", "()Lxc/richtext/api/SpannedEditModel;", "update", "rectF", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "updateLayout", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "standardWidth", "setVisibleSize", "setEngineTouchConfig", "multiEngineConfig", "Lxc/engine/utils/EngineConfig;", "setStepChangedCallback", "listener", "creationEngine", "Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "getCreationEngine", "()Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "setPdfData", "pdfPath", "doScale", "scale", "onScrollEnd", "setEntPath", "filePath", "bindEngineData", "data", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "bindSpannedEngineData", "itemData", "unBindEngineData", "Lxc/engine/data/PathInfo;", "spannedEngineClear", "pageIndex", "save", "onLoadListener", "version", "types", "", "bytes", "", "", "onSaveExtraData", "Lxc/engine/data/CsBufferBean;", "changedToCurveType", "spannedType", "changedToSpannedType", "dataType", "", "release", "releaseSpannedLayout", "setCanvasSize", "isCurveEmpty", "isSpanEmpty", "isContentEmpty", "setSearchRectList", "isNull", "type", "load", "inkFilePath", "itemView", "dataListener", "Lxc/engine/data/IDataListener;", "unBind", "handleLoading", "setRadius", "radius", "dispatchDraw", "canvas", "Landroid/graphics/Canvas;", "onCanvasChanged", "screenType", "setSearchHighLine", "selectType", "updateBackground", "dirPath", "multiItemData", "cursorHeight", "getCursorHeight", "()F", "getScale", "layoutRectF", "setBottomShieldHeight", "setUnLimit", "unLimit", "moveAllSpanAfterPage", "curPageOffsetX", "lastPageOffsetY", "moveAllSpan", "offsetX", "offsetY", "moveAllCurve", "allSpanBound", "getAllSpanBound", "setUnlimitedSize", "setSpannedLayoutFixWidth", "setSpannedLayoutFixMode", "setSpanVisibleSize", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "getDataList", "Lxc/richtext/data/SpannedData;", "isBitmapSizeMax", "addByteCount", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreItemView extends RelativeLayout implements OnExtraDataListener {
    private static final String TAG;
    private float bottomShieldHeight;
    private final Path canvasPath;
    private final float[] canvasRadius;
    private float curX;
    private float curY;
    private int drawState;
    private String entFilePath;
    private float filletRadius;
    public int index;
    private boolean isBindSurfaceView;
    private boolean isEngineLoading;
    private boolean isInitNotFinished;
    private boolean isLoading;
    private boolean isScaleWaitEnd;
    private boolean isUnLimit;
    public boolean isUnLimitToNormal;
    private boolean isVisible;
    private float lastOffsetX;
    private float lastOffsetY;
    private float lastScale;
    private final ScaleInfo lastScaleInfo;
    private final RectF layout;
    private final WeakReference<Context> mContextRef;
    private PagePreDrawView mDrawView;
    private final Handler mHandler;
    private ProgressBar mProgressBar;
    private SpannedEditLayout mSpannedLayout;
    private TextView mTvLoading;
    public PageInkPreModel model;
    private RectF pageLocation;
    private int position;
    private final ScaleInfo scaleInfo;
    private int searchAlpha;
    private int searchColor;
    private final List<Rect> searchDataList;
    private SearchWrapper searchWrapper;
    private int shieldWidth;
    private StepChangedListener stepChangedListener;
    private int unLimitHeight;
    private int unLimitWidth;
    private int viewWidth;
    private int visHeight;
    private int visWidth;

    private final int changedToCurveType(int spannedType) {
        if (spannedType != 7) {
            return spannedType != 11 ? 1 : 11;
        }
        return 7;
    }

    private final int changedToSpannedType(short dataType) {
        if (dataType == 11) {
            return 11;
        }
        return dataType == 7 ? 7 : 0;
    }

    public final void setEngineTouchConfig(EngineConfig multiEngineConfig) {
    }

    public final void setPdfData(String pdfPath, int index) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PagePreItemView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.layout = new RectF();
        this.searchDataList = new ArrayList();
        this.index = -1;
        this.searchWrapper = new SearchWrapper();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.searchColor = -1;
        this.searchAlpha = 255;
        this.canvasPath = new Path();
        this.canvasRadius = new float[8];
        this.position = -1;
        this.scaleInfo = new ScaleInfo(1.0f, new PointF(), new PointF());
        this.lastScaleInfo = new ScaleInfo(1.0f, new PointF(), new PointF());
        this.mContextRef = new WeakReference<>(context);
    }

    /* JADX INFO: renamed from: isLoading, reason: from getter */
    public final boolean getIsLoading() {
        return this.isLoading;
    }

    public final void setLoading(boolean z) {
        this.isLoading = z;
    }

    public final RectF getLayout() {
        return this.layout;
    }

    public final int getVisWidth() {
        return this.visWidth;
    }

    public final void setVisWidth(int i) {
        this.visWidth = i;
    }

    public final int getVisHeight() {
        return this.visHeight;
    }

    public final void setVisHeight(int i) {
        this.visHeight = i;
    }

    public final int getPosition() {
        return this.position;
    }

    public final void setPosition(int i) {
        this.position = i;
    }

    public final int getShieldWidth() {
        return this.shieldWidth;
    }

    public final void setShieldWidth(int i) {
        this.shieldWidth = (int) Math.max(0.0d, i);
    }

    public final RectF getPageLocation() {
        return this.pageLocation;
    }

    public final void setPageLocation(RectF rectF) {
        this.pageLocation = rectF;
    }

    public final void init(PagePreDrawView pageDrawView) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        Log.d(TAG, "init");
        this.isInitNotFinished = true;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.mDrawView = pageDrawView;
        Intrinsics.checkNotNull(pageDrawView);
        pageDrawView.getView().setLayoutParams(layoutParams);
        PagePreDrawView pagePreDrawView = this.mDrawView;
        Intrinsics.checkNotNull(pagePreDrawView);
        pagePreDrawView.bindItemView(this);
        this.isBindSurfaceView = true;
        initDrawView(layoutParams);
        initSpanned(layoutParams);
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        if (pagePreDrawView2 == null || (creationEngine = pagePreDrawView2.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
            return;
        }
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        curveEngine.setSpannedLayoutModel(spannedEditLayout.getSpannedLayoutModel());
    }

    private final void initDrawView(RelativeLayout.LayoutParams layoutParams) {
        PreICreationEngine creationEngine;
        ICanvasOperator canvasOperator;
        PreICreationEngine creationEngine2;
        ICanvasOperator canvasOperator2;
        PreICreationEngine creationEngine3;
        ICanvasOperator canvasOperator3;
        PreICreationEngine creationEngine4;
        IDataAccess dataAccess;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine4 = pagePreDrawView.getCreationEngine()) != null && (dataAccess = creationEngine4.getDataAccess()) != null) {
            dataAccess.setOnExtraDataListener(new OnExtraDataListenerProxy(this));
        }
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        boolean z = true;
        if (pagePreDrawView2 != null && (creationEngine3 = pagePreDrawView2.getCreationEngine()) != null && (canvasOperator3 = creationEngine3.getCanvasOperator()) != null) {
            canvasOperator3.setScaleMode(true);
        }
        PagePreDrawView pagePreDrawView3 = this.mDrawView;
        if (pagePreDrawView3 != null && (creationEngine2 = pagePreDrawView3.getCreationEngine()) != null && (canvasOperator2 = creationEngine2.getCanvasOperator()) != null) {
            if (!this.isUnLimit && !this.isUnLimitToNormal) {
                z = false;
            }
            canvasOperator2.setInfiniteCanvasMode(z);
        }
        PagePreDrawView pagePreDrawView4 = this.mDrawView;
        if (pagePreDrawView4 == null || (creationEngine = pagePreDrawView4.getCreationEngine()) == null || (canvasOperator = creationEngine.getCanvasOperator()) == null) {
            return;
        }
        canvasOperator.setInfiniteCanvasDebuggable(false);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PagePreDrawView pagePreDrawView;
        PreICreationEngine creationEngine2;
        PreMiCurveEngine curveEngine2;
        PreCurveSystem curveSystem;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        TextView textView = this.mTvLoading;
        if (textView != null) {
            Intrinsics.checkNotNull(textView);
            textView.setText(R.string.creation_edit_loading);
        }
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        if (pagePreDrawView2 == null || (creationEngine = pagePreDrawView2.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || curveEngine.isInfinite() || (pagePreDrawView = this.mDrawView) == null || (creationEngine2 = pagePreDrawView.getCreationEngine()) == null || (curveEngine2 = creationEngine2.getCurveEngine()) == null || (curveSystem = curveEngine2.getCurveSystem()) == null) {
            return;
        }
        curveSystem.update();
    }

    public final void initLoadingIcon(boolean useDark) {
        TextView textView = this.mTvLoading;
        if (textView != null) {
            removeView(textView);
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            removeView(progressBar);
        }
        if (getContext() == null) {
            return;
        }
        this.mProgressBar = new ProgressBar(new ContextThemeWrapper(getContext(), useDark ? miuix.appcompat.R.style.Theme_Dark : miuix.appcompat.R.style.Theme_Light));
        this.mTvLoading = new TextView(new ContextThemeWrapper(getContext(), useDark ? miuix.appcompat.R.style.Theme_Dark : miuix.appcompat.R.style.Theme_Light));
        ProgressBar progressBar2 = this.mProgressBar;
        Intrinsics.checkNotNull(progressBar2);
        progressBar2.setLayoutParams(new RelativeLayout.LayoutParams(-1, getContext().getResources().getDimensionPixelSize(R.dimen.creation_loading_icon_height)));
        TextView textView2 = this.mTvLoading;
        Intrinsics.checkNotNull(textView2);
        textView2.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        TextView textView3 = this.mTvLoading;
        Intrinsics.checkNotNull(textView3);
        textView3.setText(R.string.creation_edit_loading);
        TextView textView4 = this.mTvLoading;
        Intrinsics.checkNotNull(textView4);
        textView4.setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.creation_loading_icon_text_padding), 0, 0);
        this.isLoading = true;
        handleLoading(true);
        addView(this.mProgressBar);
        addView(this.mTvLoading);
    }

    public final void showLoadingBar(boolean show) {
        this.isLoading = show;
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            int i = show ? 0 : 4;
            if (i != progressBar.getVisibility()) {
                progressBar.setVisibility(i);
                TextView textView = this.mTvLoading;
                if (textView != null) {
                    textView.setVisibility(i);
                }
            }
        }
    }

    public final void onPDFOrEngineLoadFinish(boolean isPdf) {
        handleLoading(false);
    }

    public final void setSearchWrapper(SearchWrapper searchWrapper) {
        Intrinsics.checkNotNullParameter(searchWrapper, "searchWrapper");
        this.searchWrapper = searchWrapper;
    }

    private final void initSpanned(RelativeLayout.LayoutParams layoutParams) {
        SpannedEditModel spannedEditModel;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCurveEngineRenderService renderService;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        SpannedEditLayout spannedEditLayout = new SpannedEditLayout(this.mContextRef.get(), (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (renderService = curveEngine.getRenderService()) == null) ? null : renderService.getProvider());
        this.mSpannedLayout = spannedEditLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        spannedEditLayout.setUnLimit(this.isUnLimit);
        SpannedEditLayout spannedEditLayout2 = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout2);
        spannedEditLayout2.setGetToolViewHeight(new SpannedLayoutInterface() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView.initSpanned.1
            @Override // xc.richtext.api.SpannedLayoutInterface
            public int getToolBarHeight() {
                PageInkPreModel pageInkPreModel = PagePreItemView.this.model;
                if (pageInkPreModel != null) {
                    return pageInkPreModel.getToolViewHeight();
                }
                return 1;
            }
        });
        SpannedEditLayout spannedEditLayout3 = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout3);
        spannedEditLayout3.setLayoutParams(layoutParams);
        if (this.isBindSurfaceView) {
            SpannedEditLayout spannedEditLayout4 = this.mSpannedLayout;
            Intrinsics.checkNotNull(spannedEditLayout4);
            PagePreDrawView pagePreDrawView2 = this.mDrawView;
            Intrinsics.checkNotNull(pagePreDrawView2);
            spannedEditLayout4.addView(pagePreDrawView2.getView());
        }
        addView(this.mSpannedLayout);
        SpannedEditLayout spannedEditLayout5 = this.mSpannedLayout;
        if (spannedEditLayout5 == null || (spannedEditModel = spannedEditLayout5.getSpannedEditModel()) == null) {
            return;
        }
        spannedEditModel.setModeEditListener(new OnSpannedEditListener() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView.initSpanned.2
            @Override // xc.richtext.api.OnSpannedEditListener
            public void cancelSelectAction(float x, float y) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void cancelSoftKeyboard() {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onCopyData(List<? extends SpannedData> spannedData) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onCopyData(boolean isUnLimit, List<? extends SpannedData> spannedData) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onFinishSelect() {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSelectCount(int count, String selectedUuid) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSelectedClick() {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedEdited(ISpannedEditObject iSpannedEditObject) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean isFromAI) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onStartAction() {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onStepChanged(int spannedStepType, int pageIndex, boolean dismissLoadingDialog) {
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onLoadFinish(boolean success) {
                if (PagePreItemView.this.drawState == 0) {
                    PagePreItemView.this.drawState = 2;
                } else if (PagePreItemView.this.drawState == 1) {
                    PagePreItemView.this.setSearchRectList(true, 4);
                }
            }
        });
    }

    public final SpannedEditModel getMode() {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        return spannedEditLayout.getSpannedEditModel();
    }

    public final void update(int position, RectF rectF, float x, float y) {
        this.curX = x;
        this.curY = y;
        if (this.position != position) {
            this.searchDataList.clear();
        }
        if (rectF == null || rectF.isEmpty()) {
            this.layout.setEmpty();
        } else {
            this.layout.set(0.0f, (float) Math.max(rectF.top, 0.0d), this.unLimitWidth, this.unLimitHeight);
        }
        postInvalidate();
        SpannedEditModel mode = getMode();
        Intrinsics.checkNotNull(mode);
        mode.setCurrentPageNumber(position);
    }

    /* JADX WARN: Code duplicated, block: B:43:0x0120  */
    public final void updateLayout(int width, int height, int standardWidth) {
        SpannedEditLayout spannedEditLayout;
        SpannedEditModel spannedEditModel;
        SpannedEditModel spannedEditModel2;
        if (width == 0 || height == 0) {
            return;
        }
        this.unLimitWidth = width;
        this.unLimitHeight = height;
        int i = this.layout.left < 0.0f ? 0 : (int) this.layout.left;
        int i2 = this.layout.top < 0.0f ? 0 : (int) this.layout.top;
        float f = width;
        int i3 = this.layout.right > f ? width : (int) this.layout.right;
        float f2 = height;
        int i4 = this.layout.bottom > f2 ? height : (int) this.layout.bottom;
        if (this.layout.left < f && 0.0f < this.layout.right && this.layout.top < f2 && 0.0f < this.layout.bottom) {
            this.isVisible = true;
            if (!this.isUnLimit) {
                RectF rectF = this.pageLocation;
                if (rectF == null) {
                    this.pageLocation = new RectF(i, i2, i3, i4);
                } else {
                    Intrinsics.checkNotNull(rectF);
                    rectF.set(i, i2, i3, i4);
                }
            }
            this.viewWidth = i3 - i;
            int i5 = i4 - i2;
            ProgressBar progressBar = this.mProgressBar;
            if (progressBar != null) {
                Intrinsics.checkNotNull(progressBar);
                if (i5 > progressBar.getHeight() * 2) {
                    ProgressBar progressBar2 = this.mProgressBar;
                    Intrinsics.checkNotNull(progressBar2);
                    int i6 = this.viewWidth;
                    ProgressBar progressBar3 = this.mProgressBar;
                    Intrinsics.checkNotNull(progressBar3);
                    int width2 = (i6 - progressBar3.getWidth()) / 2;
                    ProgressBar progressBar4 = this.mProgressBar;
                    Intrinsics.checkNotNull(progressBar4);
                    int height2 = (i5 - progressBar4.getHeight()) / 2;
                    int i7 = this.viewWidth;
                    ProgressBar progressBar5 = this.mProgressBar;
                    Intrinsics.checkNotNull(progressBar5);
                    int width3 = (i7 + progressBar5.getWidth()) / 2;
                    ProgressBar progressBar6 = this.mProgressBar;
                    Intrinsics.checkNotNull(progressBar6);
                    progressBar2.layout(width2, height2, width3, (progressBar6.getHeight() + i5) / 2);
                    TextView textView = this.mTvLoading;
                    if (textView != null) {
                        int i8 = this.viewWidth;
                        Intrinsics.checkNotNull(textView);
                        int width4 = (i8 - textView.getWidth()) / 2;
                        ProgressBar progressBar7 = this.mProgressBar;
                        Intrinsics.checkNotNull(progressBar7);
                        int height3 = (progressBar7.getHeight() + i5) / 2;
                        int i9 = this.viewWidth;
                        TextView textView2 = this.mTvLoading;
                        Intrinsics.checkNotNull(textView2);
                        int width5 = (i9 + textView2.getWidth()) / 2;
                        ProgressBar progressBar8 = this.mProgressBar;
                        Intrinsics.checkNotNull(progressBar8);
                        int height4 = (i5 + progressBar8.getHeight()) / 2;
                        TextView textView3 = this.mTvLoading;
                        Intrinsics.checkNotNull(textView3);
                        textView.layout(width4, height3, width5, height4 + textView3.getHeight());
                    }
                } else {
                    handleLoading(false);
                }
            } else {
                handleLoading(false);
            }
        } else {
            this.isVisible = false;
            this.viewWidth = i3 - i;
        }
        this.visWidth = width;
        this.visHeight = height;
        if (this.layout.width() > standardWidth) {
            standardWidth = (int) Math.min(width, (int) this.layout.width());
        }
        if (this.isUnLimit) {
            SpannedEditLayout spannedEditLayout2 = this.mSpannedLayout;
            if (spannedEditLayout2 == null || (spannedEditModel2 = spannedEditLayout2.getSpannedEditModel()) == null) {
                return;
            }
            spannedEditModel2.forceChildLayout((int) this.curX, (int) this.curY, standardWidth, height);
            return;
        }
        SpannedEditLayout spannedEditLayout3 = this.mSpannedLayout;
        if (spannedEditLayout3 != null && (spannedEditModel = spannedEditLayout3.getSpannedEditModel()) != null) {
            spannedEditModel.forceChildLayout(0, 0, standardWidth, height);
        }
        if (getContext() != null) {
            if ((!UIUtils.isInMultiWindowMode(Utils.getActivity(getContext())) && !UIUtils.isSmallWindowMode(getContext())) || UIUtils.isActiveDekstopMode(getContext()) || (spannedEditLayout = this.mSpannedLayout) == null) {
                return;
            }
            spannedEditLayout.layout(0, 0, standardWidth, height);
        }
    }

    public final void setVisibleSize(int width, int height) {
        PreICreationEngine creationEngine;
        ICanvasOperator canvasOperator;
        handleLoading(true);
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (canvasOperator = creationEngine.getCanvasOperator()) == null) {
            return;
        }
        canvasOperator.setVisibleSize(width, height);
    }

    public final void setStepChangedCallback(StepChangedListener listener) {
        this.stepChangedListener = listener;
    }

    public final PreICreationEngine getCreationEngine() {
        PagePreDrawView pagePreDrawView;
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        if ((pagePreDrawView2 != null ? pagePreDrawView2.getCreationEngine() : null) == null || (pagePreDrawView = this.mDrawView) == null) {
            return null;
        }
        return pagePreDrawView.getCreationEngine();
    }

    public final void doScale(float scale, int standardWidth) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreHighLightSystem highLightSystem;
        PreICreationEngine creationEngine2;
        ICanvasOperator canvasOperator;
        if (this.visWidth == 0) {
            return;
        }
        if (!this.isScaleWaitEnd) {
            this.isScaleWaitEnd = true;
        }
        float scaleRate = scale / this.lastScaleInfo.getScaleRate();
        if (this.isUnLimit || this.isUnLimitToNormal) {
            if (this.isUnLimitToNormal) {
                this.lastOffsetX = (float) Math.min(this.layout.left, 0.0d);
                this.lastOffsetY = (float) Math.min(this.curY, 0.0d);
            } else {
                this.lastOffsetY = this.curY;
                this.lastOffsetX = this.curX;
            }
        } else {
            this.lastOffsetX = (float) Math.min(this.layout.left, 0.0d);
            this.lastOffsetY = this.isVisible ? (float) Math.min(this.layout.top, 0.0d) : 0.0f;
        }
        this.scaleInfo.setScaleRate(scaleRate);
        this.scaleInfo.getScaleCenter().set(0.0f, 0.0f);
        this.scaleInfo.getTranslate().x = (this.lastOffsetX / scaleRate) - this.lastScaleInfo.getTranslate().x;
        this.scaleInfo.getTranslate().y = (this.lastOffsetY / scaleRate) - this.lastScaleInfo.getTranslate().y;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if ((pagePreDrawView != null ? pagePreDrawView.getCreationEngine() : null) != null) {
            PagePreDrawView pagePreDrawView2 = this.mDrawView;
            if (pagePreDrawView2 != null && (creationEngine2 = pagePreDrawView2.getCreationEngine()) != null && (canvasOperator = creationEngine2.getCanvasOperator()) != null) {
                canvasOperator.doScale(this.scaleInfo);
            }
            PagePreDrawView pagePreDrawView3 = this.mDrawView;
            if (pagePreDrawView3 != null && (creationEngine = pagePreDrawView3.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (highLightSystem = curveEngine.getHighLightSystem()) != null) {
                highLightSystem.shutdownHighlight();
            }
        }
        PagePreDrawView pagePreDrawView4 = this.mDrawView;
        if (pagePreDrawView4 != null) {
            Intrinsics.checkNotNull(pagePreDrawView4);
            pagePreDrawView4.onCanvasDataChanged(null, 11);
        }
        this.lastScale = scale;
    }

    public final void onScrollEnd(float scale, int standardWidth) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PagePreDrawView pagePreDrawView;
        PreICreationEngine creationEngine2;
        PreMiCurveEngine curveEngine2;
        PreCurveSystem curveSystem;
        doScale(scale, standardWidth);
        this.lastScaleInfo.setScaleRate(this.lastScale);
        this.lastScaleInfo.getTranslate().set(this.lastOffsetX, this.lastOffsetY);
        if (this.mDrawView == null) {
            return;
        }
        handleLoading(false);
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        Intrinsics.checkNotNull(pagePreDrawView2);
        pagePreDrawView2.onCanvasDataChanged(null, 11);
        this.isScaleWaitEnd = false;
        PagePreDrawView pagePreDrawView3 = this.mDrawView;
        if (pagePreDrawView3 == null || (creationEngine = pagePreDrawView3.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || !curveEngine.isInfinite() || (pagePreDrawView = this.mDrawView) == null || (creationEngine2 = pagePreDrawView.getCreationEngine()) == null || (curveEngine2 = creationEngine2.getCurveEngine()) == null || (curveSystem = curveEngine2.getCurveSystem()) == null) {
            return;
        }
        curveSystem.update();
    }

    public final void setEntPath(String filePath) {
        String entPathByDir = FileUtil.getEntPathByDir(filePath);
        if (entPathByDir != null) {
            filePath = entPathByDir;
        }
        this.entFilePath = filePath;
    }

    public final void bindEngineData(PageItemData data) {
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        if ((data != null ? data.engineData : null) != null) {
            this.isEngineLoading = true;
            PagePreDrawView pagePreDrawView = this.mDrawView;
            if (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (dataAccess = creationEngine.getDataAccess()) == null) {
                return;
            }
            dataAccess.loadPathInfo(data.engineData, data.redoList, data.undoList, data.getPageIndex());
        }
    }

    public final void bindSpannedEngineData(PageItemData itemData) {
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        spannedEditLayout.getEngineBindContext().getSpannedMap(itemData.getPageIndex()).bindSpannedInfo(itemData.spannedData.getSpannedInfo());
    }

    public final PathInfo unBindEngineData() {
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (dataAccess = creationEngine.getDataAccess()) == null) {
            return null;
        }
        return dataAccess.getCurrentPathInfo();
    }

    public final void spannedEngineClear(int pageIndex) {
        SpannedEditModel mode = getMode();
        Intrinsics.checkNotNull(mode);
        mode.removeSpannedItemView();
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        spannedEditLayout.getEngineBindContext().getSpannedMap(pageIndex).clear();
        SpannedEditModel mode2 = getMode();
        Intrinsics.checkNotNull(mode2);
        mode2.resetViewData();
    }

    public final void save(PageItemData data) {
        PagePreDrawView pagePreDrawView;
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        Intrinsics.checkNotNullParameter(data, "data");
        Object lock = data.lock;
        Intrinsics.checkNotNullExpressionValue(lock, "lock");
        synchronized (lock) {
            PreICreationEngine creationEngine2 = getCreationEngine();
            if (creationEngine2 != null) {
                creationEngine2.waitForIdle();
            }
            String str = data.filePath;
            String str2 = TAG;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str3 = String.format("save page[%d]", Arrays.copyOf(new Object[]{Integer.valueOf(data.getPageIndex())}, 1));
            Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
            Log.i(str2, str3);
            if (!TextUtils.isEmpty(str)) {
                PagePreDrawView pagePreDrawView2 = this.mDrawView;
                if ((pagePreDrawView2 != null ? pagePreDrawView2.getCreationEngine() : null) != null && (pagePreDrawView = this.mDrawView) != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (dataAccess = creationEngine.getDataAccess()) != null && dataAccess.saveEntFileSync(str, data.getPageIndex())) {
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    String str4 = String.format("save set page[%d] modified[false]", Arrays.copyOf(new Object[]{Integer.valueOf(data.getPageIndex())}, 1));
                    Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
                    Log.e(str2, str4);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // xc.engine.data.OnExtraDataListener
    public void onLoadListener(int version, final short[] types, final int pageIndex, final List<byte[]> bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getItemDataList().size() < pageIndex || pageIndex < 0) {
            return;
        }
        PageInkPreModel pageInkPreModel2 = this.model;
        Intrinsics.checkNotNull(pageInkPreModel2);
        final PageItemData pageItemData = pageInkPreModel2.getItemDataList().get(pageIndex);
        if (types == null || types.length == 0) {
            pageItemData.hadSpannedLoad = true;
        } else {
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PagePreItemView.onLoadListener$lambda$2(types, this, pageIndex, bytes, pageItemData);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadListener$lambda$2(short[] sArr, PagePreItemView pagePreItemView, int i, List list, PageItemData pageItemData) {
        PreMiCurveEngine curveEngine;
        PreImageSystem imgSystem;
        PreMiCurveEngine curveEngine2;
        PreTextSystem textSystem;
        int[] iArr = new int[sArr.length];
        int length = sArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = pagePreItemView.changedToSpannedType(sArr[i2]);
        }
        File file = new File(pagePreItemView.entFilePath);
        SpannedEditLayout spannedEditLayout = pagePreItemView.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        spannedEditLayout.getEngineBindContext().getSpannedMap(i).loadTextFromBuffer(file.getParent(), i, iArr, list);
        pageItemData.hadUnBindSpanned = false;
        pageItemData.hadSpannedLoad = true;
        PreICreationEngine creationEngine = pagePreItemView.getCreationEngine();
        if (creationEngine != null && (curveEngine2 = creationEngine.getCurveEngine()) != null && (textSystem = curveEngine2.getTextSystem()) != null) {
            textSystem.markDirty();
        }
        PreICreationEngine creationEngine2 = pagePreItemView.getCreationEngine();
        if (creationEngine2 == null || (curveEngine = creationEngine2.getCurveEngine()) == null || (imgSystem = curveEngine.getImgSystem()) == null) {
            return;
        }
        imgSystem.markDirty();
    }

    @Override // xc.engine.data.OnExtraDataListener
    public CsBufferBean onSaveExtraData(int pageIndex) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        IBufferBean spannedBuffer = spannedEditLayout.getEngineBindContext().getSpannedMap(pageIndex).getSpannedBuffer(this.entFilePath, pageIndex);
        int length = spannedBuffer.getTypes().length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = changedToCurveType(spannedBuffer.getTypes()[i]);
        }
        return new CsBufferBean(iArr, spannedBuffer.getBuffers());
    }

    public final void release() {
        PreICreationEngine creationEngine;
        handleLoading(true);
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null) {
            creationEngine.release();
        }
        this.mDrawView = null;
        this.mHandler.removeCallbacksAndMessages(null);
        setSearchRectList(true, 0);
        releaseSpannedLayout();
        this.mContextRef.clear();
    }

    private final void releaseSpannedLayout() {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            Intrinsics.checkNotNull(spannedEditLayout);
            spannedEditLayout.release();
            SpannedEditLayout spannedEditLayout2 = this.mSpannedLayout;
            Intrinsics.checkNotNull(spannedEditLayout2);
            spannedEditLayout2.removeAllViews();
            SpannedEditLayout spannedEditLayout3 = this.mSpannedLayout;
            Intrinsics.checkNotNull(spannedEditLayout3);
            if (spannedEditLayout3.getParent() != null) {
                SpannedEditLayout spannedEditLayout4 = this.mSpannedLayout;
                Intrinsics.checkNotNull(spannedEditLayout4);
                ViewParent parent = spannedEditLayout4.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
                ((ViewGroup) parent).removeView(this.mSpannedLayout);
            }
            SpannedEditLayout spannedEditLayout5 = this.mSpannedLayout;
            Intrinsics.checkNotNull(spannedEditLayout5);
            spannedEditLayout5.setGetToolViewHeight(null);
        }
    }

    public final void setCanvasSize(float width, float height) {
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null) {
            Intrinsics.checkNotNull(pagePreDrawView);
            pagePreDrawView.setCanvasSize(width, height);
        }
    }

    public final boolean isCurveEmpty(int pageIndex) {
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        return (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (dataAccess = creationEngine.getDataAccess()) == null || dataAccess.hasValidCurVeData(pageIndex)) ? false : true;
    }

    public final boolean isSpanEmpty(int pageIndex) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        return spannedEditLayout.getEngineBindContext().getSpannedMap(pageIndex).isContentEmpty();
    }

    public final boolean isContentEmpty(int pageIndex) {
        return isCurveEmpty(pageIndex) && isSpanEmpty(pageIndex);
    }

    public final void setSearchRectList(boolean isNull, int type) {
        SpannedEditLayout spannedEditLayout;
        SpannedEditModel spannedEditModel;
        Rect rect;
        if (!isNull) {
            if (type == 0 || type == 1) {
                this.searchDataList.clear();
                if (this.searchWrapper.searchRectMap != null) {
                    for (String str : this.searchWrapper.searchRectMap.keySet()) {
                        Intrinsics.checkNotNull(str);
                        if (this.position == Integer.parseInt((String) StringsKt.split$default((CharSequence) str, new String[]{"-"}, false, 0, 6, (Object) null).get(0)) && (rect = this.searchWrapper.searchRectMap.get(str)) != null) {
                            this.searchDataList.add(rect);
                        }
                    }
                }
                this.searchColor = this.searchWrapper.searchColor;
            }
            this.searchAlpha = this.searchWrapper.searchAlpha;
        } else if (TextUtils.isEmpty(this.searchWrapper.searchText)) {
            this.searchDataList.clear();
            this.searchWrapper.searchRectMap = null;
            this.searchWrapper.searchText = null;
        }
        if ((type != 0 && type != 4) || (spannedEditLayout = this.mSpannedLayout) == null || (spannedEditModel = spannedEditLayout.getSpannedEditModel()) == null) {
            return;
        }
        spannedEditModel.setHightText(this.searchWrapper.searchText, this.searchWrapper.searchColor, this.searchAlpha, new OnSpannedHightListener() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView.setSearchRectList.2
            @Override // xc.richtext.api.OnSpannedHightListener
            public void onSpannedHightChanged(SpannedData mSpannedData, List<? extends RectF> rectList, float staticLayoutWidth, float scale) {
                PreICreationEngine creationEngine;
                PreMiCurveEngine curveEngine;
                PreICreationEngine creationEngine2;
                PreMiCurveEngine curveEngine2;
                PreHighLightSystem highLightSystem;
                Intrinsics.checkNotNullParameter(mSpannedData, "mSpannedData");
                Intrinsics.checkNotNullParameter(rectList, "rectList");
                PagePreDrawView pagePreDrawView = PagePreItemView.this.mDrawView;
                if (pagePreDrawView != null && (creationEngine2 = pagePreDrawView.getCreationEngine()) != null && (curveEngine2 = creationEngine2.getCurveEngine()) != null && (highLightSystem = curveEngine2.getHighLightSystem()) != null) {
                    highLightSystem.setSpanHighlightRect(mSpannedData, rectList, PagePreItemView.this.searchWrapper.searchColor, PagePreItemView.this.searchWrapper.searchAlpha, staticLayoutWidth, scale);
                }
                PagePreDrawView pagePreDrawView2 = PagePreItemView.this.mDrawView;
                if (pagePreDrawView2 == null || (creationEngine = pagePreDrawView2.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
                    return;
                }
                curveEngine.startHighlight();
            }
        });
    }

    public final void load(String inkFilePath, PageItemData itemData, PagePreItemView itemView, IDataListener dataListener) {
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        PreICreationEngine creationEngine2;
        IDataAccess dataAccess2;
        PreICreationEngine creationEngine3;
        IDataAccess dataAccess3;
        if (this.mDrawView != null) {
            if (inkFilePath != null) {
                this.isEngineLoading = true;
                if (itemData != null) {
                    itemData.hadSpannedLoad = false;
                }
                PagePreDrawView pagePreDrawView = this.mDrawView;
                if (pagePreDrawView != null && (creationEngine3 = pagePreDrawView.getCreationEngine()) != null && (dataAccess3 = creationEngine3.getDataAccess()) != null) {
                    dataAccess3.setIDataListener(dataListener);
                }
                PagePreDrawView pagePreDrawView2 = this.mDrawView;
                if (pagePreDrawView2 == null || (creationEngine2 = pagePreDrawView2.getCreationEngine()) == null || (dataAccess2 = creationEngine2.getDataAccess()) == null) {
                    return;
                }
                dataAccess2.loadEntFile(inkFilePath, itemData != null ? itemData.getPageIndex() : 0);
                return;
            }
            handleLoading(false);
            PagePreDrawView pagePreDrawView3 = this.mDrawView;
            if (pagePreDrawView3 == null || (creationEngine = pagePreDrawView3.getCreationEngine()) == null || (dataAccess = creationEngine.getDataAccess()) == null) {
                return;
            }
            dataAccess.loadPathInfo(null, null, null);
        }
    }

    public final void unBind() {
        PreICreationEngine creationEngine;
        IDataAccess dataAccess;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView == null || pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (dataAccess = creationEngine.getDataAccess()) == null) {
            return;
        }
        dataAccess.clearEngineData();
    }

    public final void handleLoading(final boolean show) {
        if (show) {
            XLog.v("X", "show loading icon", new Object[0]);
        } else {
            XLog.v("X", "hide loading icon", new Object[0]);
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PagePreItemView.handleLoading$lambda$4(this.f$0, show);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleLoading$lambda$4(PagePreItemView pagePreItemView, boolean z) {
        PageInkPreModel pageInkPreModel;
        pagePreItemView.showLoadingBar(z);
        if (z || (pageInkPreModel = pagePreItemView.model) == null) {
            return;
        }
        Intrinsics.checkNotNull(pageInkPreModel);
        pageInkPreModel.pageLoadingFinish();
    }

    public final void setRadius(float radius) {
        this.filletRadius = radius;
        Arrays.fill(this.canvasRadius, radius);
        postInvalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (!this.canvasPath.isEmpty()) {
            canvas.clipPath(this.canvasPath);
        }
        super.dispatchDraw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCanvasChanged$lambda$5(PagePreItemView pagePreItemView) {
        pagePreItemView.doScale();
        int i = pagePreItemView.drawState;
        if (i == 0) {
            pagePreItemView.drawState = 1;
        } else if (i == 2) {
            pagePreItemView.setSearchHighLine(0);
        }
    }

    public final void onCanvasChanged(int screenType, final int pageIndex) {
        if (screenType != 12) {
            if (screenType != 13) {
                return;
            }
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    PagePreItemView.onCanvasChanged$lambda$6(this.f$0, pageIndex);
                }
            });
            PagePreDrawView pagePreDrawView = this.mDrawView;
            if (pagePreDrawView != null) {
                pagePreDrawView.onCanvasDataChanged(null, 11);
                return;
            }
            return;
        }
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PagePreItemView.onCanvasChanged$lambda$5(this.f$0);
            }
        });
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        if (pagePreDrawView2 != null) {
            pagePreDrawView2.onCanvasDataChanged(null, 11);
        }
        if (this.isInitNotFinished) {
            return;
        }
        handleLoading(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCanvasChanged$lambda$6(PagePreItemView pagePreItemView, int i) {
        PreICreationEngine creationEngine;
        pagePreItemView.doScale();
        pagePreItemView.isEngineLoading = false;
        pagePreItemView.onPDFOrEngineLoadFinish(false);
        PagePreDrawView pagePreDrawView = pagePreItemView.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null) {
            creationEngine.onPageDataLoaded(i);
        }
        pagePreItemView.setSearchHighLine(1);
        pagePreItemView.isInitNotFinished = false;
    }

    private final void setSearchHighLine(int selectType) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreICreationEngine creationEngine2;
        PreMiCurveEngine curveEngine2;
        PreHighLightSystem highLightSystem;
        setSearchRectList(false, selectType);
        if (TextUtils.isEmpty(this.searchWrapper.searchText) || this.searchWrapper.searchRectMap == null) {
            return;
        }
        Map<String, Rect> searchRectMap = this.searchWrapper.searchRectMap;
        Intrinsics.checkNotNullExpressionValue(searchRectMap, "searchRectMap");
        for (Map.Entry<String, Rect> entry : searchRectMap.entrySet()) {
            String key = entry.getKey();
            Rect value = entry.getValue();
            Intrinsics.checkNotNull(key);
            int i = Integer.parseInt((String) StringsKt.split$default((CharSequence) key, new String[]{"-"}, false, 0, 6, (Object) null).get(0));
            PagePreDrawView pagePreDrawView = this.mDrawView;
            if (pagePreDrawView != null && (creationEngine2 = pagePreDrawView.getCreationEngine()) != null && (curveEngine2 = creationEngine2.getCurveEngine()) != null && (highLightSystem = curveEngine2.getHighLightSystem()) != null) {
                highLightSystem.setCurveHighLightRect(i, CollectionsKt.listOf(value), this.searchWrapper.searchColor, this.searchWrapper.searchAlpha);
            }
        }
        PagePreDrawView pagePreDrawView2 = this.mDrawView;
        if (pagePreDrawView2 == null || (creationEngine = pagePreDrawView2.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
            return;
        }
        curveEngine.startHighlight();
    }

    public final void doScale() {
        PreICreationEngine creationEngine;
        ICanvasOperator canvasOperator;
        PreICreationEngine creationEngine2;
        ICanvasOperator canvasOperator2;
        PreICreationEngine creationEngine3;
        ICanvasOperator canvasOperator3;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if ((pagePreDrawView != null ? pagePreDrawView.getCreationEngine() : null) != null) {
            ScaleInfo scaleInfo = new ScaleInfo();
            scaleInfo.setScaleRate(this.lastScaleInfo.getScaleRate());
            scaleInfo.getTranslate().set(this.lastScaleInfo.getTranslate().x / this.lastScaleInfo.getScaleRate(), this.lastScaleInfo.getTranslate().y / this.lastScaleInfo.getScaleRate());
            PagePreDrawView pagePreDrawView2 = this.mDrawView;
            if (pagePreDrawView2 != null && (creationEngine3 = pagePreDrawView2.getCreationEngine()) != null && (canvasOperator3 = creationEngine3.getCanvasOperator()) != null) {
                canvasOperator3.resetScale();
            }
            PagePreDrawView pagePreDrawView3 = this.mDrawView;
            if (pagePreDrawView3 != null && (creationEngine2 = pagePreDrawView3.getCreationEngine()) != null && (canvasOperator2 = creationEngine2.getCanvasOperator()) != null) {
                canvasOperator2.doScale(scaleInfo);
            }
            PagePreDrawView pagePreDrawView4 = this.mDrawView;
            if (pagePreDrawView4 == null || (creationEngine = pagePreDrawView4.getCreationEngine()) == null || (canvasOperator = creationEngine.getCanvasOperator()) == null) {
                return;
            }
            canvasOperator.doScaleUp();
        }
    }

    public final void updateBackground(String dirPath, PageItemData multiItemData) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreBgSystem bgSystem;
        Intrinsics.checkNotNullParameter(multiItemData, "multiItemData");
        if (multiItemData.bgConfig == null) {
            multiItemData.bgConfig = new BgGridConfiguration();
        }
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (bgSystem = curveEngine.getBgSystem()) != null) {
            bgSystem.updateStyles(multiItemData.bgColor, multiItemData.bgGrid);
        }
        if (TextUtils.isEmpty(multiItemData.bgImage) && TextUtils.isEmpty(multiItemData.pdfId)) {
            initLoadingIcon(false);
        } else {
            setShieldWidth(0);
            setBottomShieldHeight(0.0f);
        }
    }

    public final float getCursorHeight() {
        if (getMode() == null) {
            return 0.0f;
        }
        SpannedEditModel mode = getMode();
        Intrinsics.checkNotNull(mode);
        return mode.getEditTextCursorHeight();
    }

    public final float getScale() {
        return this.layout.width() / this.visWidth;
    }

    public final void update(int position, RectF layoutRectF, float scale, int standardWidth, float curX, float curY) {
        update(position, layoutRectF, curX, curY);
        doScale(scale, standardWidth);
    }

    public final void setBottomShieldHeight(float height) {
        this.bottomShieldHeight = (float) Math.max(0.0d, height);
    }

    public final void setUnLimit(boolean unLimit) {
        PreICreationEngine creationEngine;
        ICanvasOperator canvasOperator;
        this.isUnLimit = unLimit;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (canvasOperator = creationEngine.getCanvasOperator()) != null) {
            canvasOperator.setInfiniteCanvasMode(unLimit);
        }
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            Intrinsics.checkNotNull(spannedEditLayout);
            spannedEditLayout.setUnLimit(unLimit);
        }
    }

    public final void moveAllSpanAfterPage(final int pageIndex, float curPageOffsetX, float lastPageOffsetY) {
        if (curPageOffsetX != 0.0f) {
            SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
            Intrinsics.checkNotNull(spannedEditLayout);
            for (SpannedData spannedData : spannedEditLayout.getEngineBindContext().getSpannedMap(pageIndex).getDataListAll()) {
                RectF rectF = spannedData.mRatioRectF;
                rectF.offset(curPageOffsetX, 0.0f);
                spannedData.mRatioRectF.set(rectF);
                spannedData.setRectF(rectF);
            }
        }
        SpannedEditLayout spannedEditLayout2 = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout2);
        Stream<SpannedData> stream = spannedEditLayout2.getEngineBindContext().getAllDataList().stream();
        final Function1 function1 = new Function1() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(PagePreItemView.moveAllSpanAfterPage$lambda$8(pageIndex, (SpannedData) obj));
            }
        };
        for (SpannedData spannedData2 : (List) stream.filter(new Predicate() { // from class: com.miui.libhandwritingpreview.view.PagePreItemView$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PagePreItemView.moveAllSpanAfterPage$lambda$9(function1, obj);
            }
        }).collect(Collectors.toList())) {
            RectF rectF2 = spannedData2.mRatioRectF;
            rectF2.offset(0.0f, lastPageOffsetY);
            spannedData2.mRatioRectF.set(rectF2);
            spannedData2.setRectF(rectF2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean moveAllSpanAfterPage$lambda$8(int i, SpannedData spannedData) {
        Intrinsics.checkNotNullParameter(spannedData, "spannedData");
        return spannedData.getPageIndex() > i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean moveAllSpanAfterPage$lambda$9(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    public final void moveAllSpan(float offsetX, float offsetY) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        for (SpannedData spannedData : spannedEditLayout.getEngineBindContext().getAllDataList()) {
            RectF rectF = spannedData.mRatioRectF;
            rectF.offset(offsetX, offsetY);
            spannedData.mRatioRectF.set(rectF);
            spannedData.setRectF(rectF);
        }
    }

    public final void moveAllCurve(float offsetX, float offsetY) {
        PreICreationEngine creationEngine = getCreationEngine();
        if (creationEngine != null) {
            creationEngine.moveAllCurve(offsetX, offsetY);
        }
    }

    public final RectF getAllSpanBound() {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        List<SpannedData> allDataList = spannedEditLayout.getEngineBindContext().getAllDataList();
        if (allDataList.isEmpty()) {
            return null;
        }
        RectF rectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        for (SpannedData spannedData : allDataList) {
            if (spannedData.mSpannedStatus != 3) {
                rectF.union(new RectF(spannedData.mRatioRectF.left, spannedData.mRatioRectF.top, spannedData.mRatioRectF.right, spannedData.mRatioRectF.bottom));
            }
        }
        return rectF;
    }

    public final void setUnlimitedSize(int width, int height) {
        this.unLimitWidth = width;
        this.unLimitHeight = height;
    }

    /* JADX INFO: renamed from: isUnLimit, reason: from getter */
    public final boolean getIsUnLimit() {
        return this.isUnLimit;
    }

    public final void setSpannedLayoutFixWidth(int width) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            Intrinsics.checkNotNull(spannedEditLayout);
            spannedEditLayout.setFixWidth(width);
        }
    }

    public final void setSpannedLayoutFixMode(boolean mode) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            Intrinsics.checkNotNull(spannedEditLayout);
            spannedEditLayout.setFixMode(mode);
        }
    }

    public final void setSpanVisibleSize(float w, float h) {
        if (getMode() != null) {
            SpannedEditModel mode = getMode();
            Intrinsics.checkNotNull(mode);
            mode.setVisibleSize(w, h);
        }
    }

    public final List<SpannedData> getDataList(int index, int type) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        List<SpannedData> dataList = spannedEditLayout.getEngineBindContext().getSpannedMap(index).getDataList(type);
        Intrinsics.checkNotNullExpressionValue(dataList, "getDataList(...)");
        return dataList;
    }

    public final boolean isBitmapSizeMax(int addByteCount) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Intrinsics.checkNotNull(spannedEditLayout);
        EngineBindContext engineBindContext = spannedEditLayout.getEngineBindContext();
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        return engineBindContext.isBitmapSizeMax(pageInkPreModel.getCurrentPosition(), addByteCount);
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PagePreItemView", "getSimpleName(...)");
        TAG = "PagePreItemView";
    }
}
