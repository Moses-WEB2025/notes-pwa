package com.miui.doodle.feature.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.R;
import com.miui.doodle.base.DoodleSelectableItemBase;
import com.miui.doodle.base.IDoodleListener;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleSelectableItem;
import com.miui.doodle.base.core.IDoodleTouchDetector;
import com.miui.doodle.document.DocumentWriter;
import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.action.BaseAction;
import com.miui.doodle.feature.action.IAction;
import com.miui.doodle.feature.action.RemoveLayerBitmapAction;
import com.miui.doodle.utils.BitmapDecoder;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.UIUtils;
import com.miui.doodle.utils.Utils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.note.algorithm.common.GraphType;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.xiaomi.onetrack.b.e;
import defpackage.R2;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: DoodleView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ò\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b,\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0017\u0018\u0000 ×\u00022\u00020\u00012\u00020\u00022\u00020\u0003:\u0006Ö\u0002×\u0002Ø\u0002BO\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\r¢\u0006\u0004\b\u0010\u0010\u0011J\u000e\u0010]\u001a\u00020^2\u0006\u0010\u0006\u001a\u00020\u0007J-\u0010\u0094\u0001\u001a\u00020^2\u0007\u0010\u0095\u0001\u001a\u00020 2\u0007\u0010\u0096\u0001\u001a\u00020 2\u0007\u0010\u0097\u0001\u001a\u00020 2\u0007\u0010\u0098\u0001\u001a\u00020 H\u0014J\u0013\u0010\u009d\u0001\u001a\u00020\r2\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0016J\u0013\u0010 \u0001\u001a\u00020^2\b\u0010¡\u0001\u001a\u00030\u009c\u0001H\u0017J\u0011\u0010¢\u0001\u001a\u00020^2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0013\u0010§\u0001\u001a\u00020^2\b\u0010¨\u0001\u001a\u00030©\u0001H\u0014J\u0013\u0010±\u0001\u001a\u00020^2\b\u0010¨\u0001\u001a\u00030©\u0001H\u0002J\u0010\u0010¸\u0001\u001a\u00020`2\u0007\u0010¹\u0001\u001a\u00020`J\u0010\u0010º\u0001\u001a\u00020`2\u0007\u0010»\u0001\u001a\u00020`J\u0012\u0010¼\u0001\u001a\u00020`2\u0007\u0010½\u0001\u001a\u00020`H\u0002J\u0012\u0010¾\u0001\u001a\u00020`2\u0007\u0010¿\u0001\u001a\u00020`H\u0002J\u001b\u0010À\u0001\u001a\u00020`2\u0007\u0010¹\u0001\u001a\u00020`2\u0007\u0010Á\u0001\u001a\u00020`H\u0002J\u001b\u0010Â\u0001\u001a\u00020`2\u0007\u0010»\u0001\u001a\u00020`2\u0007\u0010Ã\u0001\u001a\u00020`H\u0002J\u0010\u0010Ä\u0001\u001a\u00020^2\u0007\u0010Å\u0001\u001a\u00020\rJ\t\u0010Æ\u0001\u001a\u00020^H\u0016J\u0015\u0010Ç\u0001\u001a\u00020^2\n\u0010È\u0001\u001a\u0005\u0018\u00010É\u0001H\u0016J\u001e\u0010Ê\u0001\u001a\u00020^2\n\u0010È\u0001\u001a\u0005\u0018\u00010É\u00012\u0007\u0010Ë\u0001\u001a\u00020\rH\u0016J\u0018\u0010Ì\u0001\u001a\u00020^2\u000f\u0010Í\u0001\u001a\n\u0012\u0005\u0012\u00030Ï\u00010Î\u0001J\u001f\u0010Ð\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030Ò\u00012\f\b\u0002\u0010È\u0001\u001a\u0005\u0018\u00010É\u0001J\u001f\u0010Ó\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030Ô\u00012\f\b\u0002\u0010È\u0001\u001a\u0005\u0018\u00010É\u0001J\u001b\u0010Õ\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030Ö\u00012\b\u0010×\u0001\u001a\u00030Ø\u0001J\u001b\u0010Ù\u0001\u001a\u00020^2\b\u0010Ú\u0001\u001a\u00030Û\u00012\b\u0010×\u0001\u001a\u00030Ø\u0001J\u0013\u0010Ü\u0001\u001a\u0004\u0018\u00010,2\b\u0010Ú\u0001\u001a\u00030Û\u0001J\u001c\u0010Ý\u0001\u001a\u00020^2\t\u0010Þ\u0001\u001a\u0004\u0018\u00010,2\b\u0010×\u0001\u001a\u00030Ø\u0001J\t\u0010ß\u0001\u001a\u00020^H\u0002J\t\u0010à\u0001\u001a\u00020`H\u0014J\u0011\u0010á\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u0001J\u0011\u0010ã\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u0001J\u001c\u0010ã\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u00012\t\u0010ä\u0001\u001a\u0004\u0018\u00010,J\u0011\u0010å\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u0001J\u0011\u0010æ\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u0001J%\u0010ç\u0001\u001a\u00020^2\b\u0010è\u0001\u001a\u00030â\u00012\b\u0010é\u0001\u001a\u00030â\u00012\b\u0010ê\u0001\u001a\u00030ë\u0001J\u0011\u0010ì\u0001\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u0001J\u0007\u0010í\u0001\u001a\u00020^J\u0011\u0010î\u0001\u001a\u00020^2\b\u0010ï\u0001\u001a\u00030ð\u0001J\t\u0010ñ\u0001\u001a\u00020^H\u0016J\t\u0010õ\u0001\u001a\u00020^H\u0016J\t\u0010ö\u0001\u001a\u00020^H\u0002J\u0007\u0010÷\u0001\u001a\u00020^J\u0014\u0010ø\u0001\u001a\u0004\u0018\u00010S2\u0007\u0010ù\u0001\u001a\u00020\rH\u0002J\u0014\u0010ú\u0001\u001a\u0004\u0018\u00010,2\u0007\u0010û\u0001\u001a\u00020,H\u0002J\u000b\u0010ü\u0001\u001a\u0004\u0018\u00010SH\u0002J\u0007\u0010ý\u0001\u001a\u00020^J\u000b\u0010þ\u0001\u001a\u0004\u0018\u00010&H\u0002J\u0013\u0010ÿ\u0001\u001a\u00020^2\n\u0010\u0080\u0002\u001a\u0005\u0018\u00010\u0081\u0002J\u0007\u0010\u0082\u0002\u001a\u00020^J\u0007\u0010\u008a\u0002\u001a\u00020^J\u000b\u0010\u008b\u0002\u001a\u0004\u0018\u00010,H\u0002J\t\u0010\u008c\u0002\u001a\u00020^H\u0016J\u0012\u0010\u008d\u0002\u001a\u00020\r2\u0007\u0010\u008e\u0002\u001a\u00020 H\u0016J\u0012\u0010\u008f\u0002\u001a\u00020\r2\u0007\u0010\u008e\u0002\u001a\u00020 H\u0016J\t\u0010\u008d\u0002\u001a\u00020\rH\u0016J\u0010\u0010\u0090\u0002\u001a\u00020^2\u0007\u0010\u0091\u0002\u001a\u00020rJ\u0010\u0010\u0092\u0002\u001a\u00020^2\u0007\u0010\u0091\u0002\u001a\u00020rJ\u0007\u0010\u0093\u0002\u001a\u00020rJ\u0007\u0010\u0094\u0002\u001a\u00020rJ\u0007\u0010\u0095\u0002\u001a\u00020^J\u0007\u0010\u0096\u0002\u001a\u00020^J#\u0010j\u001a\u00020^2\u0007\u0010\u0097\u0002\u001a\u00020`2\u0007\u0010\u0098\u0002\u001a\u00020`2\u0007\u0010\u0099\u0002\u001a\u00020`H\u0016J\u001b\u0010\u009a\u0002\u001a\u00020^2\u0007\u0010\u009b\u0002\u001a\u00020`2\u0007\u0010\u009c\u0002\u001a\u00020`H\u0016J\u0010\u0010¬\u0002\u001a\u00020^2\u0007\u0010\u00ad\u0002\u001a\u00020\rJ'\u0010°\u0002\u001a\u0005\u0018\u00010±\u00022\u0007\u0010½\u0001\u001a\u00020`2\u0007\u0010¿\u0001\u001a\u00020`2\t\b\u0002\u0010²\u0002\u001a\u00020\rJ\u000f\u0010³\u0002\u001a\n\u0012\u0005\u0012\u00030â\u00010Î\u0001J\u001c\u0010´\u0002\u001a\u00020^2\b\u0010µ\u0002\u001a\u00030â\u00012\u0007\u0010¶\u0002\u001a\u00020\rH\u0016J\u001c\u0010·\u0002\u001a\u00020^2\b\u0010Ñ\u0001\u001a\u00030â\u00012\u0007\u0010¶\u0002\u001a\u00020\rH\u0016J%\u0010¸\u0002\u001a\u00020^2\b\u0010ê\u0001\u001a\u00030ë\u00012\u0007\u0010¹\u0002\u001a\u00020 2\u0007\u0010¶\u0002\u001a\u00020\rH\u0016J#\u0010º\u0002\u001a\u00020^2\b\u0010»\u0002\u001a\u00030¤\u00012\u0007\u0010¹\u0001\u001a\u00020`2\u0007\u0010»\u0001\u001a\u00020`J\u0013\u0010¼\u0002\u001a\u00020^2\b\u0010Í\u0001\u001a\u00030Ï\u0001H\u0016J\u0014\u0010Ä\u0002\u001a\u0004\u0018\u00010,2\u0007\u0010Þ\u0001\u001a\u00020,H\u0002J\u0015\u0010Å\u0002\u001a\u00020\r2\t\u0010Þ\u0001\u001a\u0004\u0018\u00010,H\u0086 J\u0014\u0010Ì\u0002\u001a\u00020^2\t\b\u0002\u0010Í\u0002\u001a\u00020\rH\u0002J\u0007\u0010Î\u0002\u001a\u00020^J\u0007\u0010Ï\u0002\u001a\u00020^J\u0007\u0010Ð\u0002\u001a\u00020^J\u0007\u0010Ñ\u0002\u001a\u00020^R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u000e\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0016\"\u0004\b\u0019\u0010\u0018R\u001a\u0010\u000f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u0014\u0010\u001b\u001a\u00020\rX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\u00020\rX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001c\u0010%\u001a\u0004\u0018\u00010&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001c\u00101\u001a\u00020 8VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\"\"\u0004\b3\u0010$R\u001c\u00104\u001a\u00020 8VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\"\"\u0004\b6\u0010$R(\u00107\u001a\u0010\u0012\u0004\u0012\u00020 \u0012\u0006\u0012\u0004\u0018\u00010908X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u0012\u0010>\u001a\u0004\u0018\u00010?X\u0082\u000e¢\u0006\u0004\n\u0002\u0010@R\u0012\u0010A\u001a\u0004\u0018\u00010?X\u0082\u000e¢\u0006\u0004\n\u0002\u0010@R\u000e\u0010B\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010C\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u0016\"\u0004\bD\u0010\u0018R\u001c\u0010E\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001c\u0010K\u001a\u0004\u0018\u00010LX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR$\u0010Q\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010S\u0018\u00010RX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR(\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010X\u001a\u0004\u0018\u00010\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u000e\u0010_\u001a\u00020`X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010a\u001a\u00020`2\u0006\u0010X\u001a\u00020`@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bb\u0010cR\u001e\u0010d\u001a\u00020 2\u0006\u0010X\u001a\u00020 @BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\be\u0010\"R\u000e\u0010f\u001a\u00020`X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020`X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010h\u001a\u00020`2\u0006\u0010X\u001a\u00020`@TX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010c\"\u0004\bj\u0010kR\u000e\u0010l\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010m\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010\u0016\"\u0004\bo\u0010\u0018R\u0014\u0010p\u001a\b\u0012\u0004\u0012\u00020r0qX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010s\u001a\b\u0012\u0004\u0012\u00020r0qX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010t\u001a\u00020u2\u0006\u0010t\u001a\u00020u@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010w\"\u0004\bx\u0010yR\u001a\u0010z\u001a\u00020`X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010c\"\u0004\b|\u0010kR\u0014\u0010}\u001a\u00020 X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\"R\u001b\u0010\u007f\u001a\n\u0012\u0005\u0012\u00030\u0081\u00010\u0080\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001R\u0016\u0010\u0084\u0001\u001a\u0004\u0018\u00010 8F¢\u0006\b\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001R\u0017\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0081\u00018F¢\u0006\b\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001R-\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u000b2\b\u0010X\u001a\u0004\u0018\u00010\u000b@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R\u0010\u0010\u008f\u0001\u001a\u00030\u0090\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0091\u0001\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0092\u0001\u001a\u00020\rX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0016\"\u0005\b\u0093\u0001\u0010\u0018R\u0010\u0010\u0099\u0001\u001a\u00030\u009a\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u009b\u0001\u001a\u0005\u0018\u00010\u009c\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010£\u0001\u001a\u00030¤\u00018F¢\u0006\n\n\u0000\u001a\u0006\b¥\u0001\u0010¦\u0001R\u0015\u0010ª\u0001\u001a\u00030«\u0001¢\u0006\n\n\u0000\u001a\u0006\b¬\u0001\u0010\u00ad\u0001R\u000f\u0010®\u0001\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010¯\u0001\u001a\u00030\u0090\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010°\u0001\u001a\u00030«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010²\u0001\u001a\u00020`8F¢\u0006\u0007\u001a\u0005\b³\u0001\u0010cR\u0013\u0010´\u0001\u001a\u00020`8F¢\u0006\u0007\u001a\u0005\bµ\u0001\u0010cR\u0013\u0010¶\u0001\u001a\u00020`8F¢\u0006\u0007\u001a\u0005\b·\u0001\u0010cR\u001d\u0010ò\u0001\u001a\u00020\rX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bó\u0001\u0010\u0016\"\u0005\bô\u0001\u0010\u0018R-\u0010\u0083\u0002\u001a\u0010\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030\u0084\u00020R0\u0080\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0085\u0002\u0010\u0083\u0001\"\u0006\b\u0086\u0002\u0010\u0087\u0002R\u001d\u0010\u0088\u0002\u001a\u00020\rX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0088\u0002\u0010\u0016\"\u0005\b\u0089\u0002\u0010\u0018R\u001d\u0010\u009d\u0002\u001a\u00020`X\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009e\u0002\u0010c\"\u0005\b\u009f\u0002\u0010kR\u001d\u0010 \u0002\u001a\u00020`X\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¡\u0002\u0010c\"\u0005\b¢\u0002\u0010kR(\u0010¤\u0002\u001a\u00020`2\u0007\u0010£\u0002\u001a\u00020`@FX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¥\u0002\u0010c\"\u0005\b¦\u0002\u0010kR\u001d\u0010§\u0002\u001a\u00020\rX\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b§\u0002\u0010\u0016\"\u0005\b¨\u0002\u0010\u0018R\u001d\u0010©\u0002\u001a\u00020\rX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bª\u0002\u0010\u0016\"\u0005\b«\u0002\u0010\u0018R\u0016\u0010®\u0002\u001a\u00020`8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¯\u0002\u0010cR\u0016\u0010½\u0002\u001a\u00020 8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¾\u0002\u0010\"R\u0016\u0010¿\u0002\u001a\u00020 8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\bÀ\u0002\u0010\"R(\u0010Â\u0002\u001a\u00020\r2\u0007\u0010Á\u0002\u001a\u00020\r@FX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÂ\u0002\u0010\u0016\"\u0005\bÃ\u0002\u0010\u0018R\u0012\u0010Æ\u0002\u001a\u0005\u0018\u00010Ç\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010È\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010É\u0002\u001a\u000b\u0012\u0005\u0012\u00030\u0084\u0002\u0018\u00010RX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÊ\u0002\u0010U\"\u0005\bË\u0002\u0010WR\u0016\u0010Ò\u0002\u001a\u00030Ó\u0002X\u0096\u0005¢\u0006\b\u001a\u0006\bÔ\u0002\u0010Õ\u0002¨\u0006Ù\u0002"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleView;", "Landroid/widget/FrameLayout;", "Lcom/miui/doodle/base/core/IDoodle;", "Lkotlinx/coroutines/CoroutineScope;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "document", "Lcom/miui/doodle/document/DoodleDocument;", "doodleListener", "Lcom/miui/doodle/base/IDoodleListener;", "defaultDetector", "Lcom/miui/doodle/base/core/IDoodleTouchDetector;", "isClipImageOnSave", "", "isCreateNewDoodle", "isFromRebuild", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/document/DoodleDocument;Lcom/miui/doodle/base/IDoodleListener;Lcom/miui/doodle/base/core/IDoodleTouchDetector;ZZZ)V", "getDoodleListener", "()Lcom/miui/doodle/base/IDoodleListener;", "setDoodleListener", "(Lcom/miui/doodle/base/IDoodleListener;)V", "()Z", "setClipImageOnSave", "(Z)V", "setCreateNewDoodle", "setFromRebuild", "doAutoSave", "getDoAutoSave", "doSaveHistory", "getDoSaveHistory", "MAX_HISTORY_STEPS", "", "getMAX_HISTORY_STEPS", "()I", "setMAX_HISTORY_STEPS", "(I)V", "tempDoodleFilePath", "", "getTempDoodleFilePath", "()Ljava/lang/String;", "setTempDoodleFilePath", "(Ljava/lang/String;)V", "doodleBitmap", "Landroid/graphics/Bitmap;", "getDoodleBitmap", "()Landroid/graphics/Bitmap;", "setDoodleBitmap", "(Landroid/graphics/Bitmap;)V", "doodleWidth", "getDoodleWidth", "setDoodleWidth", "doodleHeight", "getDoodleHeight", "setDoodleHeight", "markDoodleOperation", "Lkotlin/Function1;", "Ljava/lang/Void;", "getMarkDoodleOperation", "()Lkotlin/jvm/functions/Function1;", "setMarkDoodleOperation", "(Lkotlin/jvm/functions/Function1;)V", "drawTime", "", "Ljava/lang/Long;", "lastCachedDrawTime", "willRebuild", "isDrawing", "setDrawing", "brushCacheView", "Lcom/miui/doodle/feature/draw/BrushCacheView;", "getBrushCacheView", "()Lcom/miui/doodle/feature/draw/BrushCacheView;", "setBrushCacheView", "(Lcom/miui/doodle/feature/draw/BrushCacheView;)V", "addImageTask", "Lcom/miui/doodle/feature/draw/DoodleView$AddImageTask;", "getAddImageTask", "()Lcom/miui/doodle/feature/draw/DoodleView$AddImageTask;", "setAddImageTask", "(Lcom/miui/doodle/feature/draw/DoodleView$AddImageTask;)V", "mDeferredDoodleFile", "Lkotlinx/coroutines/Deferred;", "Ljava/io/File;", "getMDeferredDoodleFile", "()Lkotlinx/coroutines/Deferred;", "setMDeferredDoodleFile", "(Lkotlinx/coroutines/Deferred;)V", "value", "getDocument", "()Lcom/miui/doodle/document/DoodleDocument;", "setDocument", "(Lcom/miui/doodle/document/DoodleDocument;)V", "initDocument", "", "centerScale", "", "centerHeight", "getCenterHeight", "()F", "centerWidth", "getCenterWidth", "centreTranY", "centreTranX", "doodleScale", "getDoodleScale", "setDoodleScale", "(F)V", "ready", "doTouch", "getDoTouch", "setDoTouch", "historyItemStack", "Ljava/util/Stack;", "Lcom/miui/doodle/feature/action/IAction;", "redoItemStack", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "getPen", "()Lcom/miui/doodle/feature/draw/DoodlePen;", "setPen", "(Lcom/miui/doodle/feature/draw/DoodlePen;)V", "unitSize", "getUnitSize", "setUnitSize", "doodleRotation", "getDoodleRotation", "layerViewList", "", "Lcom/miui/doodle/feature/draw/LayerView;", "getLayerViewList", "()Ljava/util/List;", "currentLayerIndex", "getCurrentLayerIndex", "()Ljava/lang/Integer;", "currentLayerView", "getCurrentLayerView", "()Lcom/miui/doodle/feature/draw/LayerView;", "defaultTouchDetector", "getDefaultTouchDetector", "()Lcom/miui/doodle/base/core/IDoodleTouchDetector;", "setDefaultTouchDetector", "(Lcom/miui/doodle/base/core/IDoodleTouchDetector;)V", "tempPoint", "Landroid/graphics/PointF;", "isSaving", "isShowConfigPopupWindow", "setShowConfigPopupWindow", "onSizeChanged", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "mTouchEventMatrix", "Landroid/graphics/Matrix;", "mOnTouchListener", "Landroid/view/View$OnTouchListener;", "dispatchTouchEvent", "event", "Landroid/view/MotionEvent;", "setOnTouchListener", e.a, "init", "doodleBound", "Landroid/graphics/RectF;", "getDoodleBound", "()Landroid/graphics/RectF;", "dispatchDraw", "canvas", "Landroid/graphics/Canvas;", "layerPaint", "Landroid/graphics/Paint;", "getLayerPaint", "()Landroid/graphics/Paint;", "showEraserCircle", "eraserPoint", "eraserCirclePaint", "drawEraserCircle", "allScale", "getAllScale", "allTranX", "getAllTranX", "allTranY", "getAllTranY", "toX", "touchX", "toY", "touchY", "toTouchX", AnimatedProperty.PROPERTY_NAME_X, "toTouchY", AnimatedProperty.PROPERTY_NAME_Y, "toTransX", "doodleX", "toTransY", "doodleY", "onProcessing", "isFinished", "invalidate", "refresh", Notes.Data.DIRTY, "Landroid/graphics/Rect;", "reset", "fast", "eraserLayerBitmap", "eraserDot", "", "Lcom/miui/doodle/document/Dot;", "resetShape", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "resetTextOrBitmap", "Lcom/miui/doodle/base/DoodleSelectableItemBase;", "addTextElement", "Lcom/miui/doodle/feature/draw/DoodleText;", "touchGestureListener", "Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener;", "addImageElementByUri", AITextWidgetStat.TYPE_IMAGE, "Landroid/net/Uri;", "getBitmapByUri", "addImageElement", DoodleBitmap.KEY_BITMAP, "cancelAddImageTask", "getDefaultTextItemLocalY", "markItemToOptimizeDrawing", "Lcom/miui/doodle/base/core/IDoodleItem;", "notifyItemFinishedDrawing", "oldLayerBitmap", "notifyItemFinishedErasing", "notifyItemTempRemove", "notifyItemFinishedReplacing", "oldItem", "newItem", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "notifySelectedItemLayoutFinished", "notifyElementFinishRemove", "pushToActionHistory", "newAction", "Lcom/miui/doodle/feature/action/BaseAction;", "onSaveToHistory", "mNeedChange", "getMNeedChange", "setMNeedChange", "save", "clearCurrentLayerCache", "clearBackgroundCache", "saveDoodleBitmap", "ignoreCache", "processPreviewBitmap", "previewBitmap", "saveTempDoodleBitmap", "addNewLayerBitmap", "saveSyncState", "saveTempDoodleFile", "saveInstance", "Landroid/os/Bundle;", "saveTempDoodle", "laseSnapShotJobs", "", "getLaseSnapShotJobs", "setLaseSnapShotJobs", "(Ljava/util/List;)V", "isDoCacheSaving", "setDoCacheSaving", "cacheDoodleSnapshot", "createPreviewImage", "clear", NotesTextStat.UNDO, "step", NotesTextStat.REDO, "addNewHistoryAction", "action", "addRedoHistoryAction", "popHistoryAction", "popRedoAction", "clearRedoStack", "clearStack", "scale", "pivotX", "pivotY", "setDoodleTranslation", "transX", "transY", "doodleTranslationX", "getDoodleTranslationX", "setDoodleTranslationX", "doodleTranslationY", "getDoodleTranslationY", "setDoodleTranslationY", "paintSize", "size", "getSize", "setSize", "isDrawableOutside", "setDrawableOutside", "mIsScrollingDoodle", "getMIsScrollingDoodle", "setMIsScrollingDoodle", "setScrollingDoodle", "scrollingDoodle", "screenDensity", "getScreenDensity", "getSelectableItemInThisPoint", "Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "skipBrushPath", "getStraightLineItems", "addItem", "doodleItem", "needSaveToHistory", "removeItem", "removeLayerBitmap", "index", "removeItemByElementEraser", "eraserRect", "eraserPathItem", "redoItemCount", "getRedoItemCount", "undoItemCount", "getUndoItemCount", "editMode", "isEditMode", "setEditMode", "smartClip", "isEmptyBitmap", "cacheHandler", "Lcom/miui/doodle/feature/draw/DoodleView$CacheHandler;", "saveJobScope", "autoSaveJob", "getAutoSaveJob", "setAutoSaveJob", "asyncSaveDoodle", "instant", "autoSave", "cancelAutoSave", "cancelAutoSaveJob", "clearAutoSaveTask", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "AddImageTask", "Companion", "CacheHandler", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleView extends FrameLayout implements IDoodle, CoroutineScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final float MAX_SCALE = 5.0f;
    public static final float MIN_SCALE = 0.5f;
    public static final String TAG = "DoodleView";
    private final /* synthetic */ CoroutineScope $$delegate_0;
    private int MAX_HISTORY_STEPS;
    private AddImageTask addImageTask;
    private Deferred<? extends Object> autoSaveJob;
    private BrushCacheView brushCacheView;
    private CacheHandler cacheHandler;
    private float centerHeight;
    private float centerScale;
    private int centerWidth;
    private float centreTranX;
    private float centreTranY;
    private IDoodleTouchDetector defaultTouchDetector;
    private final boolean doAutoSave;
    private final boolean doSaveHistory;
    private boolean doTouch;
    private DoodleDocument document;
    private Bitmap doodleBitmap;
    private final RectF doodleBound;
    private int doodleHeight;
    private IDoodleListener doodleListener;
    private final int doodleRotation;
    private float doodleScale;
    private float doodleTranslationX;
    private float doodleTranslationY;
    private int doodleWidth;
    private Long drawTime;
    private final Paint eraserCirclePaint;
    private final PointF eraserPoint;
    private final Stack<IAction> historyItemStack;
    private boolean isClipImageOnSave;
    private boolean isCreateNewDoodle;
    private boolean isDoCacheSaving;
    private boolean isDrawableOutside;
    private boolean isDrawing;
    private boolean isEditMode;
    private boolean isFromRebuild;
    private boolean isSaving;
    private boolean isShowConfigPopupWindow;
    private List<Deferred<Object>> laseSnapShotJobs;
    private Long lastCachedDrawTime;
    private final Paint layerPaint;
    private final List<LayerView> layerViewList;
    private Deferred<? extends File> mDeferredDoodleFile;
    private boolean mIsScrollingDoodle;
    private boolean mNeedChange;
    private View.OnTouchListener mOnTouchListener;
    private final Matrix mTouchEventMatrix;
    public Function1<? super Integer, Void> markDoodleOperation;
    private DoodlePen pen;
    private boolean ready;
    private final Stack<IAction> redoItemStack;
    private final CoroutineScope saveJobScope;
    private boolean showEraserCircle;
    private float size;
    private String tempDoodleFilePath;
    private final PointF tempPoint;
    private float unitSize;
    private boolean willRebuild;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener) {
        this(context, doodleDocument, iDoodleListener, null, false, false, false, 120, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener, IDoodleTouchDetector iDoodleTouchDetector) {
        this(context, doodleDocument, iDoodleListener, iDoodleTouchDetector, false, false, false, 112, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener, IDoodleTouchDetector iDoodleTouchDetector, boolean z) {
        this(context, doodleDocument, iDoodleListener, iDoodleTouchDetector, z, false, false, 96, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener, IDoodleTouchDetector iDoodleTouchDetector, boolean z, boolean z2) {
        this(context, doodleDocument, iDoodleListener, iDoodleTouchDetector, z, z2, false, 64, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void addItem(IDoodleItem doodleItem, boolean needSaveToHistory) {
        Intrinsics.checkNotNullParameter(doodleItem, "doodleItem");
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    public final native boolean isEmptyBitmap(Bitmap bitmap);

    public void onSaveToHistory() {
    }

    public /* synthetic */ DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener, IDoodleTouchDetector iDoodleTouchDetector, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, doodleDocument, iDoodleListener, (i & 8) != 0 ? null : iDoodleTouchDetector, (i & 16) != 0 ? true : z, (i & 32) != 0 ? true : z2, (i & 64) != 0 ? false : z3);
    }

    public final IDoodleListener getDoodleListener() {
        return this.doodleListener;
    }

    public final void setDoodleListener(IDoodleListener iDoodleListener) {
        this.doodleListener = iDoodleListener;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    /* JADX INFO: renamed from: isClipImageOnSave, reason: from getter */
    public boolean getIsClipImageOnSave() {
        return this.isClipImageOnSave;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setClipImageOnSave(boolean z) {
        this.isClipImageOnSave = z;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    /* JADX INFO: renamed from: isCreateNewDoodle, reason: from getter */
    public boolean getIsCreateNewDoodle() {
        return this.isCreateNewDoodle;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setCreateNewDoodle(boolean z) {
        this.isCreateNewDoodle = z;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    /* JADX INFO: renamed from: isFromRebuild, reason: from getter */
    public boolean getIsFromRebuild() {
        return this.isFromRebuild;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setFromRebuild(boolean z) {
        this.isFromRebuild = z;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleView(Context context, DoodleDocument doodleDocument, IDoodleListener iDoodleListener, IDoodleTouchDetector iDoodleTouchDetector, boolean z, boolean z2, boolean z3) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.$$delegate_0 = CoroutineScopeKt.MainScope();
        this.doodleListener = iDoodleListener;
        this.isClipImageOnSave = z;
        this.isCreateNewDoodle = z2;
        this.isFromRebuild = z3;
        this.doAutoSave = true;
        this.doSaveHistory = true;
        this.MAX_HISTORY_STEPS = 50;
        this.doodleWidth = 1;
        this.doodleHeight = 1;
        this.document = doodleDocument;
        this.doodleScale = 1.0f;
        this.historyItemStack = new Stack<>();
        this.redoItemStack = new Stack<>();
        this.pen = DoodlePenManager.INSTANCE.getPencil().copy();
        this.unitSize = 1.0f;
        this.layerViewList = new ArrayList();
        this.defaultTouchDetector = iDoodleTouchDetector;
        this.tempPoint = new PointF();
        setClipChildren(false);
        setDocument(doodleDocument);
        this.mTouchEventMatrix = new Matrix();
        this.doodleBound = new RectF();
        this.layerPaint = new Paint(3);
        this.eraserPoint = new PointF();
        this.eraserCirclePaint = new Paint();
        this.laseSnapShotJobs = new ArrayList();
        this.size = context.getResources().getDimension(R.dimen.brush_stoke_base);
        this.saveJobScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault());
    }

    public final boolean getDoAutoSave() {
        return this.doAutoSave;
    }

    public final boolean getDoSaveHistory() {
        return this.doSaveHistory;
    }

    public final int getMAX_HISTORY_STEPS() {
        return this.MAX_HISTORY_STEPS;
    }

    public final void setMAX_HISTORY_STEPS(int i) {
        this.MAX_HISTORY_STEPS = i;
    }

    public final String getTempDoodleFilePath() {
        return this.tempDoodleFilePath;
    }

    public final void setTempDoodleFilePath(String str) {
        this.tempDoodleFilePath = str;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public Bitmap getDoodleBitmap() {
        return this.doodleBitmap;
    }

    public void setDoodleBitmap(Bitmap bitmap) {
        this.doodleBitmap = bitmap;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setDoodleWidth(int i) {
        this.doodleWidth = i;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public int getDoodleWidth() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            return doodleDocument.getWidth();
        }
        return 1;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setDoodleHeight(int i) {
        this.doodleHeight = i;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public int getDoodleHeight() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            return doodleDocument.getHeight();
        }
        return 1;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public Function1<Integer, Void> getMarkDoodleOperation() {
        Function1 function1 = this.markDoodleOperation;
        if (function1 != null) {
            return function1;
        }
        Intrinsics.throwUninitializedPropertyAccessException("markDoodleOperation");
        return null;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setMarkDoodleOperation(Function1<? super Integer, Void> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.markDoodleOperation = function1;
    }

    /* JADX INFO: renamed from: isDrawing, reason: from getter */
    public final boolean getIsDrawing() {
        return this.isDrawing;
    }

    public final void setDrawing(boolean z) {
        this.isDrawing = z;
    }

    public final BrushCacheView getBrushCacheView() {
        return this.brushCacheView;
    }

    public final void setBrushCacheView(BrushCacheView brushCacheView) {
        this.brushCacheView = brushCacheView;
    }

    public final AddImageTask getAddImageTask() {
        return this.addImageTask;
    }

    public final void setAddImageTask(AddImageTask addImageTask) {
        this.addImageTask = addImageTask;
    }

    public final Deferred<File> getMDeferredDoodleFile() {
        return this.mDeferredDoodleFile;
    }

    public final void setMDeferredDoodleFile(Deferred<? extends File> deferred) {
        this.mDeferredDoodleFile = deferred;
    }

    public final DoodleDocument getDocument() {
        return this.document;
    }

    public final void setDocument(DoodleDocument doodleDocument) {
        List<Layer> layers;
        Bitmap backgroundBitmap;
        Bitmap previewBitmap;
        int i = getContext().getResources().getDisplayMetrics().densityDpi;
        this.document = doodleDocument;
        if (doodleDocument != null && (previewBitmap = doodleDocument.getPreviewBitmap()) != null) {
            previewBitmap.setDensity(i);
        }
        DoodleDocument doodleDocument2 = this.document;
        if (doodleDocument2 != null && (backgroundBitmap = doodleDocument2.getBackgroundBitmap()) != null) {
            backgroundBitmap.setDensity(i);
        }
        DoodleDocument doodleDocument3 = this.document;
        if (doodleDocument3 != null && (layers = doodleDocument3.getLayers()) != null) {
            for (Layer layer : layers) {
                Bitmap backgroundBitmap2 = layer.getBackgroundBitmap();
                if (backgroundBitmap2 != null) {
                    backgroundBitmap2.setDensity(i);
                }
                for (LayerBitmap layerBitmap : layer.getLayerBitmapList()) {
                    Bitmap bitmap = layerBitmap.getBitmap();
                    if (bitmap != null) {
                        bitmap.setDensity(i);
                    }
                    Canvas canvas = layerBitmap.getCanvas();
                    if (canvas != null) {
                        canvas.setDensity(i);
                    }
                }
            }
        }
        if (!this.layerViewList.isEmpty()) {
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                removeView((LayerView) it.next());
            }
            this.layerViewList.clear();
        }
        if (doodleDocument != null) {
            for (Layer layer2 : doodleDocument.getLayers()) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                LayerView layerView = new LayerView(context, this, this.defaultTouchDetector, layer2);
                this.layerViewList.add(layerView);
                addView(layerView, new ViewGroup.LayoutParams(-1, -1));
            }
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            BrushCacheView brushCacheView = new BrushCacheView(context2, this);
            this.brushCacheView = brushCacheView;
            addView(brushCacheView, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    public final void initDocument(DoodleDocument document) {
        Intrinsics.checkNotNullParameter(document, "document");
        setDocument(document);
        DoodleDocument doodleDocument = this.document;
        boolean z = (doodleDocument == null || doodleDocument.getSnapShotPath() == null) ? false : true;
        Iterator<T> it = this.historyItemStack.iterator();
        while (it.hasNext()) {
            ((IAction) it.next()).destroy();
        }
        this.historyItemStack.clear();
        init(document);
        for (Layer layer : document.getLayers()) {
            if (z) {
                layer.refreshDoodle(this);
            }
        }
        Iterator<T> it2 = document.getLayers().iterator();
        boolean z2 = false;
        while (it2.hasNext()) {
            if (((Layer) it2.next()).getNeedReset()) {
                z2 = true;
            }
        }
        if (z2) {
            IDoodle.reset$default(this, null, false, 3, null);
        }
        this.cacheHandler = new CacheHandler(this);
    }

    public final float getCenterHeight() {
        return this.centerHeight;
    }

    public final int getCenterWidth() {
        return this.centerWidth;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public float getDoodleScale() {
        return this.doodleScale;
    }

    protected void setDoodleScale(float f) {
        this.doodleScale = f;
    }

    public final boolean getDoTouch() {
        return this.doTouch;
    }

    public final void setDoTouch(boolean z) {
        this.doTouch = z;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public DoodlePen getPen() {
        return this.pen;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setPen(DoodlePen pen) {
        Intrinsics.checkNotNullParameter(pen, "pen");
        this.pen = pen;
        IDoodle.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public float getUnitSize() {
        return this.unitSize;
    }

    public void setUnitSize(float f) {
        this.unitSize = f;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public int getDoodleRotation() {
        return this.doodleRotation;
    }

    public final List<LayerView> getLayerViewList() {
        return this.layerViewList;
    }

    public final Integer getCurrentLayerIndex() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            return Integer.valueOf(doodleDocument.getCurrentLayerIndex());
        }
        return null;
    }

    public final LayerView getCurrentLayerView() {
        Integer currentLayerIndex = getCurrentLayerIndex();
        if (currentLayerIndex == null) {
            return null;
        }
        int iIntValue = currentLayerIndex.intValue();
        if (this.layerViewList.size() == 0) {
            return null;
        }
        if (iIntValue >= this.layerViewList.size()) {
            iIntValue = this.layerViewList.size() - 1;
        }
        return this.layerViewList.get(iIntValue);
    }

    public final IDoodleTouchDetector getDefaultTouchDetector() {
        return this.defaultTouchDetector;
    }

    public final void setDefaultTouchDetector(IDoodleTouchDetector iDoodleTouchDetector) {
        this.defaultTouchDetector = iDoodleTouchDetector;
        if (iDoodleTouchDetector != null) {
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).setDefaultTouchDetector(iDoodleTouchDetector);
            }
        }
    }

    /* JADX INFO: renamed from: isShowConfigPopupWindow, reason: from getter */
    public final boolean getIsShowConfigPopupWindow() {
        return this.isShowConfigPopupWindow;
    }

    public final void setShowConfigPopupWindow(boolean z) {
        this.isShowConfigPopupWindow = z;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            init(doodleDocument);
        }
        if (this.ready) {
            return;
        }
        IDoodleListener iDoodleListener = this.doodleListener;
        if (iDoodleListener != null) {
            iDoodleListener.onReady(this);
        }
        this.ready = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!isEnabled()) {
            return true;
        }
        this.doTouch = true;
        boolean zOnTouchEvent = false;
        if (getPen().isEraser() || getPen().isElementEraser()) {
            this.showEraserCircle = event.getAction() == 2 || event.getAction() == 0;
            this.eraserPoint.set(event.getX(), event.getY());
            invalidate();
        }
        View.OnTouchListener onTouchListener = this.mOnTouchListener;
        if (onTouchListener != null) {
            Intrinsics.checkNotNull(onTouchListener);
            if (onTouchListener.onTouch(this, event)) {
                return true;
            }
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(event);
        this.mTouchEventMatrix.reset();
        this.mTouchEventMatrix.setRotate(-getDoodleRotation(), getDoodleWidth() / 2.0f, getDoodleHeight() / 2.0f);
        motionEventObtain.transform(this.mTouchEventMatrix);
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            Intrinsics.checkNotNull(motionEventObtain);
            zOnTouchEvent = currentLayerView.onTouchEvent(motionEventObtain);
        }
        motionEventObtain.recycle();
        return zOnTouchEvent;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        this.mOnTouchListener = l;
        super.setOnTouchListener(l);
    }

    private final void init(DoodleDocument document) {
        int width = document.getBackgroundBitmap().getWidth();
        int height = document.getBackgroundBitmap().getHeight();
        float f = width;
        float width2 = (f * 1.0f) / getWidth();
        float f2 = height;
        float height2 = (f2 * 1.0f) / getHeight();
        if (height > width) {
            if (width2 > height2) {
                float f3 = 1 / height2;
                this.centerScale = f3;
                this.centerWidth = (int) (f * f3);
                this.centerHeight = getHeight();
            } else {
                this.centerScale = 1 / width2;
                this.centerWidth = getWidth();
                this.centerHeight = f2 * this.centerScale;
            }
        } else if (height2 > width2) {
            this.centerScale = 1 / width2;
            this.centerWidth = getWidth();
            this.centerHeight = f2 * this.centerScale;
        } else {
            float f4 = 1 / height2;
            this.centerScale = f4;
            this.centerWidth = (int) (f * f4);
            this.centerHeight = getHeight();
        }
        this.centreTranX = (getWidth() - this.centerWidth) / 2.0f;
        this.centreTranY = (getHeight() - this.centerHeight) / 2.0f;
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        setUnitSize(uIUtils.dip2px(context, 1.0f) / this.centerScale);
        setDoodleScale(1.0f);
        if (this.centerScale > 0.0f) {
            DoodlePenManager doodlePenManager = DoodlePenManager.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            doodlePenManager.setScale(context2, 1 / this.centerScale);
        }
        IDoodle.refresh$default(this, null, 1, null);
    }

    public final RectF getDoodleBound() {
        float doodleScale = this.centerWidth * getDoodleScale();
        float doodleScale2 = this.centerHeight * getDoodleScale();
        Bitmap doodleBitmap = getDoodleBitmap();
        float height = doodleBitmap != null ? doodleBitmap.getHeight() : 1;
        Bitmap doodleBitmap2 = getDoodleBitmap();
        float width = doodleBitmap2 != null ? doodleBitmap2.getWidth() : 1;
        if (getDoodleRotation() % 90 == 0) {
            int doodleRotation = getDoodleRotation();
            if (doodleRotation != 0) {
                if (doodleRotation == 90) {
                    this.tempPoint.x = toTouchX(0.0f);
                    this.tempPoint.y = toTouchY(height);
                } else if (doodleRotation == 180) {
                    this.tempPoint.x = toTouchX(width);
                    this.tempPoint.y = toTouchY(height);
                } else if (doodleRotation == 270) {
                    this.tempPoint.x = toTouchX(width);
                    this.tempPoint.y = toTouchY(0.0f);
                }
                doodleScale2 = doodleScale;
                doodleScale = doodleScale2;
            } else {
                this.tempPoint.x = toTouchX(0.0f);
                this.tempPoint.y = toTouchY(0.0f);
            }
            DrawUtil.INSTANCE.rotatePoint(this.tempPoint, getDoodleRotation(), this.tempPoint.x, this.tempPoint.y, getWidth() / 2.0f, getHeight() / 2.0f);
            this.doodleBound.set(this.tempPoint.x, this.tempPoint.y, this.tempPoint.x + doodleScale, this.tempPoint.y + doodleScale2);
        } else {
            float touchX = toTouchX(0.0f);
            float touchY = toTouchY(0.0f);
            float touchX2 = toTouchX(width);
            float touchY2 = toTouchY(height);
            float touchX3 = toTouchX(0.0f);
            float touchY3 = toTouchY(height);
            float touchX4 = toTouchX(width);
            float touchY4 = toTouchY(0.0f);
            DrawUtil.INSTANCE.rotatePoint(this.tempPoint, getDoodleRotation(), touchX, touchY, getWidth() / 2.0f, getHeight() / 2.0f);
            float f = this.tempPoint.x;
            float f2 = this.tempPoint.y;
            DrawUtil.INSTANCE.rotatePoint(this.tempPoint, getDoodleRotation(), touchX2, touchY2, getWidth() / 2.0f, getHeight() / 2.0f);
            float f3 = this.tempPoint.x;
            float f4 = this.tempPoint.y;
            DrawUtil.INSTANCE.rotatePoint(this.tempPoint, getDoodleRotation(), touchX3, touchY3, getWidth() / 2.0f, getHeight() / 2.0f);
            float f5 = this.tempPoint.x;
            float f6 = this.tempPoint.y;
            DrawUtil.INSTANCE.rotatePoint(this.tempPoint, getDoodleRotation(), touchX4, touchY4, getWidth() / 2.0f, getHeight() / 2.0f);
            float f7 = this.tempPoint.x;
            float f8 = this.tempPoint.y;
            this.doodleBound.left = RangesKt.coerceAtMost(RangesKt.coerceAtMost(f, f3), RangesKt.coerceAtMost(f5, f7));
            this.doodleBound.top = RangesKt.coerceAtMost(RangesKt.coerceAtMost(f2, f4), RangesKt.coerceAtMost(f6, f8));
            this.doodleBound.right = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(f, f3), RangesKt.coerceAtLeast(f5, f7));
            this.doodleBound.bottom = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(f2, f4), RangesKt.coerceAtLeast(f6, f8));
        }
        return this.doodleBound;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Bitmap doodleBitmap = getDoodleBitmap();
        if (doodleBitmap == null || !doodleBitmap.isRecycled()) {
            this.drawTime = Long.valueOf(System.currentTimeMillis());
            int iSave = canvas.save();
            super.dispatchDraw(canvas);
            canvas.restoreToCount(iSave);
            if ((getPen().isEraser() || getPen().isElementEraser()) && this.showEraserCircle) {
                drawEraserCircle(canvas);
            }
        }
    }

    public final Paint getLayerPaint() {
        return this.layerPaint;
    }

    private final void drawEraserCircle(Canvas canvas) {
        this.eraserCirclePaint.setStrokeWidth(1.0f);
        this.eraserCirclePaint.setColor(-16777216);
        this.eraserCirclePaint.setAlpha(51);
        this.eraserCirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(this.eraserPoint.x, this.eraserPoint.y, (getContext().getResources().getDimension(R.dimen.doodle_icon_erase_size) / 2.0f) * getAllScale(), this.eraserCirclePaint);
    }

    public final float getAllScale() {
        return this.centerScale * getDoodleScale();
    }

    public final float getAllTranX() {
        return this.centreTranX + getDoodleTranslationX();
    }

    public final float getAllTranY() {
        return this.centreTranY + getDoodleTranslationY();
    }

    public final float toX(float touchX) {
        return (touchX - getAllTranX()) / getAllScale();
    }

    public final float toY(float touchY) {
        return (touchY - getAllTranY()) / getAllScale();
    }

    private final float toTouchX(float x) {
        return (x * getAllScale()) + getAllTranX();
    }

    private final float toTouchY(float y) {
        return (y * getAllScale()) + getAllTranY();
    }

    private final float toTransX(float touchX, float doodleX) {
        return touchX - ((doodleX * getAllScale()) + this.centreTranX);
    }

    private final float toTransY(float touchY, float doodleY) {
        return touchY - ((doodleY * getAllScale()) + this.centreTranY);
    }

    public final void onProcessing(boolean isFinished) {
        IDoodleListener iDoodleListener = this.doodleListener;
        if (iDoodleListener != null) {
            iDoodleListener.onProcessing(isFinished);
        }
    }

    @Override // android.view.View
    public void invalidate() {
        refresh(null);
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void refresh(Rect dirty) {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            super.invalidate();
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).invalidate();
            }
            BrushCacheView brushCacheView = this.brushCacheView;
            if (brushCacheView != null) {
                brushCacheView.invalidate();
                return;
            }
            return;
        }
        super.postInvalidate();
        Iterator<T> it2 = this.layerViewList.iterator();
        while (it2.hasNext()) {
            ((LayerView) it2.next()).postInvalidate();
        }
        BrushCacheView brushCacheView2 = this.brushCacheView;
        if (brushCacheView2 != null) {
            brushCacheView2.postInvalidate();
        }
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void reset(Rect dirty, boolean fast) {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            super.invalidate();
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).reset(dirty, fast);
            }
            return;
        }
        super.postInvalidate();
        Iterator<T> it2 = this.layerViewList.iterator();
        while (it2.hasNext()) {
            ((LayerView) it2.next()).postReset(dirty, fast);
        }
    }

    public final void eraserLayerBitmap(List<Dot> eraserDot) {
        Intrinsics.checkNotNullParameter(eraserDot, "eraserDot");
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            super.invalidate();
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).eraserLayerBitmap(eraserDot);
            }
            return;
        }
        super.postInvalidate();
        Iterator<T> it2 = this.layerViewList.iterator();
        while (it2.hasNext()) {
            ((LayerView) it2.next()).postEraserLayerBitmap(eraserDot);
        }
    }

    public static /* synthetic */ void resetShape$default(DoodleView doodleView, DoodleBrushPath doodleBrushPath, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resetShape");
        }
        if ((i & 2) != 0) {
            rect = null;
        }
        doodleView.resetShape(doodleBrushPath, rect);
    }

    public final void resetShape(DoodleBrushPath item, Rect dirty) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            super.invalidate();
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).resetShape(item, dirty);
            }
            return;
        }
        super.postInvalidate();
        Iterator<T> it2 = this.layerViewList.iterator();
        while (it2.hasNext()) {
            ((LayerView) it2.next()).postResetShape(item, dirty);
        }
    }

    public static /* synthetic */ void resetTextOrBitmap$default(DoodleView doodleView, DoodleSelectableItemBase doodleSelectableItemBase, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resetTextOrBitmap");
        }
        if ((i & 2) != 0) {
            rect = null;
        }
        doodleView.resetTextOrBitmap(doodleSelectableItemBase, rect);
    }

    public final void resetTextOrBitmap(DoodleSelectableItemBase item, Rect dirty) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            Iterator<T> it = this.layerViewList.iterator();
            while (it.hasNext()) {
                ((LayerView) it.next()).resetTextOrBitmap(item, dirty);
            }
        } else {
            Iterator<T> it2 = this.layerViewList.iterator();
            while (it2.hasNext()) {
                ((LayerView) it2.next()).postResetTextOrBitmap(item, dirty);
            }
        }
        asyncSaveDoodle(false);
    }

    public final void addTextElement(DoodleText item, DoodleOnTouchGestureListener touchGestureListener) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        LayerBitmap layerBitmap;
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(touchGestureListener, "touchGestureListener");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null && (layer = currentLayerView.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null && (layerBitmap = (LayerBitmap) CollectionsKt.last((List) layerBitmapList)) != null) {
            layerBitmap.cacheImageBeforeChanged();
        }
        item.setLocation(new PointF((getDoodleWidth() - item.getBounds().width()) / 2.0f, toY(getDefaultTextItemLocalY())));
        DoodleText doodleText = item;
        markItemToOptimizeDrawing(doodleText);
        this.doTouch = true;
        notifySelectedItemLayoutFinished(doodleText);
        asyncSaveDoodle(true);
        touchGestureListener.setSelectedItem(item);
        item.cacheTransitInfoAfterChanged();
    }

    public final void addImageElementByUri(Uri image, DoodleOnTouchGestureListener touchGestureListener) {
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(touchGestureListener, "touchGestureListener");
        cancelAddImageTask();
        AddImageTask addImageTask = new AddImageTask(image, this, touchGestureListener);
        this.addImageTask = addImageTask;
        addImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final Bitmap getBitmapByUri(Uri image) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        LayerBitmap layerBitmap;
        Intrinsics.checkNotNullParameter(image, "image");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null && (layer = currentLayerView.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null && (layerBitmap = (LayerBitmap) CollectionsKt.last((List) layerBitmapList)) != null) {
            layerBitmap.cacheImageBeforeChanged();
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BitmapDecoder bitmapDecoder = new BitmapDecoder(context, image);
        float fMin = Math.min(getDoodleWidth() / bitmapDecoder.getWidth(), getDoodleHeight() / bitmapDecoder.getHeight());
        return bitmapDecoder.getScaledBitmap((int) (bitmapDecoder.getWidth() * fMin), (int) (bitmapDecoder.getHeight() * fMin));
    }

    public final void addImageElement(Bitmap bitmap, DoodleOnTouchGestureListener touchGestureListener) {
        Intrinsics.checkNotNullParameter(touchGestureListener, "touchGestureListener");
        if (bitmap != null) {
            DoodleBitmap doodleBitmap = new DoodleBitmap(this, bitmap, null, 0.0f, 0.0f, null, null, 0, R2.attr.actionBarEditIcon, null);
            DoodleBitmap doodleBitmap2 = doodleBitmap;
            markItemToOptimizeDrawing(doodleBitmap2);
            this.doTouch = true;
            notifySelectedItemLayoutFinished(doodleBitmap2);
            touchGestureListener.setSelectedItem(doodleBitmap);
            doodleBitmap.cacheTransitInfoAfterChanged();
        }
    }

    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0016\u0010\u000f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0010\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u0014R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleView$AddImageTask;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "Landroid/graphics/Bitmap;", AITextWidgetStat.TYPE_IMAGE, "Landroid/net/Uri;", "container", "Lcom/miui/doodle/feature/draw/DoodleView;", "touchGestureListener", "Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/net/Uri;Lcom/miui/doodle/feature/draw/DoodleView;Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener;)V", "mWkContainer", "Ljava/lang/ref/WeakReference;", "doInBackground", "params", "", "([Ljava/lang/Void;)Landroid/graphics/Bitmap;", "onPostExecute", "", "result", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AddImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final Uri image;
        private final WeakReference<DoodleView> mWkContainer;
        private final DoodleOnTouchGestureListener touchGestureListener;

        public AddImageTask(Uri image, DoodleView container, DoodleOnTouchGestureListener touchGestureListener) {
            Intrinsics.checkNotNullParameter(image, "image");
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(touchGestureListener, "touchGestureListener");
            this.mWkContainer = new WeakReference<>(container);
            this.image = image;
            this.touchGestureListener = touchGestureListener;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... params) {
            Intrinsics.checkNotNullParameter(params, "params");
            Logger.INSTANCE.d(DoodleView.TAG, "doInBackground...addImageTask...run");
            DoodleView doodleView = this.mWkContainer.get();
            if (doodleView != null) {
                return doodleView.getBitmapByUri(this.image);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            DoodleView doodleView = this.mWkContainer.get();
            if (doodleView != null) {
                doodleView.addImageElement(result, this.touchGestureListener);
            }
        }
    }

    private final void cancelAddImageTask() {
        AddImageTask addImageTask = this.addImageTask;
        if (addImageTask != null) {
            addImageTask.cancel(true);
        }
    }

    protected float getDefaultTextItemLocalY() {
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return uIUtils.dip2px(context, 168.72f);
    }

    public final void markItemToOptimizeDrawing(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item instanceof DoodleShape) {
            BrushCacheView brushCacheView = this.brushCacheView;
            if (brushCacheView != null) {
                brushCacheView.setAlpha(getPen().getColorPatchAlpha());
            }
        } else {
            BrushCacheView brushCacheView2 = this.brushCacheView;
            if (brushCacheView2 != null) {
                brushCacheView2.setAlpha(1.0f);
            }
        }
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.markItemToOptimizeDrawing(item);
        }
        IDoodle.refresh$default(this, null, 1, null);
    }

    public final void notifyItemFinishedDrawing(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.notifyItemFinishedDrawing(item);
        }
    }

    public final void notifyItemFinishedDrawing(IDoodleItem item, Bitmap oldLayerBitmap) {
        Intrinsics.checkNotNullParameter(item, "item");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.notifyItemFinishedDrawing(item, oldLayerBitmap);
        }
        asyncSaveDoodle(false);
    }

    public final void notifyItemFinishedErasing(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView == null || !currentLayerView.notifyItemFinishedErasing(item)) {
            return;
        }
        asyncSaveDoodle(true);
    }

    public final void notifyItemTempRemove(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.notifyItemTempRemove(item);
        }
    }

    public final void notifyItemFinishedReplacing(IDoodleItem oldItem, IDoodleItem newItem, LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(oldItem, "oldItem");
        Intrinsics.checkNotNullParameter(newItem, "newItem");
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.notifyItemFinishedReplacing(oldItem, newItem, layerBitmap);
        }
        asyncSaveDoodle(false);
    }

    public final void notifySelectedItemLayoutFinished(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.notifySelectedItemLayoutFinished(item);
        }
        asyncSaveDoodle(true);
    }

    public final void notifyElementFinishRemove() {
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.finishRemoveAction();
        }
        asyncSaveDoodle(false);
    }

    public final void pushToActionHistory(BaseAction newAction) {
        Intrinsics.checkNotNullParameter(newAction, "newAction");
        if (this.doSaveHistory) {
            addNewHistoryAction(newAction);
            clearRedoStack();
            onSaveToHistory();
        }
    }

    public final boolean getMNeedChange() {
        return this.mNeedChange;
    }

    public final void setMNeedChange(boolean z) {
        this.mNeedChange = z;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void save() {
        DoodleDocument doodleDocument;
        cancelAutoSave();
        boolean z = this.isDoCacheSaving;
        if (z) {
            this.isDoCacheSaving = false;
            clearBackgroundCache();
        }
        Logger.INSTANCE.d(TAG, "save() ignoreCache " + z);
        if (this.isSaving) {
            return;
        }
        if (getIsClipImageOnSave() && (doodleDocument = this.document) != null && doodleDocument.isEmpty()) {
            LayerView currentLayerView = getCurrentLayerView();
            List<IDoodleItem> itemStackOnViewCanvas = currentLayerView != null ? currentLayerView.getItemStackOnViewCanvas() : null;
            if (itemStackOnViewCanvas == null || itemStackOnViewCanvas.isEmpty()) {
                LayerView currentLayerView2 = getCurrentLayerView();
                List<IDoodleItem> pendingItemsDrawToBitmap = currentLayerView2 != null ? currentLayerView2.getPendingItemsDrawToBitmap() : null;
                if (pendingItemsDrawToBitmap == null || pendingItemsDrawToBitmap.isEmpty()) {
                    BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C02171(null), 2, null);
                    IDoodleListener iDoodleListener = this.doodleListener;
                    if (iDoodleListener != null) {
                        iDoodleListener.onSaved(this, null, null);
                        return;
                    }
                    return;
                }
            }
        }
        this.isSaving = true;
        if ((getIsCreateNewDoodle() && getIsFromRebuild()) || this.doTouch || this.historyItemStack.size() != 0) {
            BuildersKt__Builders_commonKt.launch$default(this, null, null, new AnonymousClass2(z, null), 3, null);
            return;
        }
        IDoodleListener iDoodleListener2 = this.doodleListener;
        if (iDoodleListener2 != null) {
            iDoodleListener2.onNoChangeAndExit(this, null);
        }
        Logger.INSTANCE.d(TAG, "save() onNoChangeAndExit");
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$save$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$save$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C02171(Continuation<? super C02171> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02171(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Utils.INSTANCE.clearDoodleCacheDir();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$save$2, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$save$2", f = "DoodleView.kt", i = {0, 0, 0}, l = {R2.attr.colorOnSurfaceSecondary}, m = "invokeSuspend", n = {"$this$launch", "deferredDoodleFile", "startSaveTime"}, s = {"L$0", "L$1", "J$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $ignoreCache;
        long J$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(boolean z, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$ignoreCache = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = DoodleView.this.new AnonymousClass2(this.$ignoreCache, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long j;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long jCurrentTimeMillis = System.currentTimeMillis();
                Logger.INSTANCE.d(DoodleView.TAG, "start save");
                Deferred<? extends File> deferredAsync$default = BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new DoodleView$save$2$deferredDoodleFile$1(DoodleView.this, this.$ignoreCache, null), 2, null);
                DoodleView.this.setMDeferredDoodleFile(deferredAsync$default);
                this.L$0 = coroutineScope;
                this.L$1 = SpillingKt.nullOutSpilledVariable(deferredAsync$default);
                this.J$0 = jCurrentTimeMillis;
                this.label = 1;
                obj = deferredAsync$default.await(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                j = jCurrentTimeMillis;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j = this.J$0;
                ResultKt.throwOnFailure(obj);
            }
            File file = (File) obj;
            Logger.INSTANCE.d(DoodleView.TAG, "end saveAll cost " + (System.currentTimeMillis() - j));
            DoodleView.this.setMDeferredDoodleFile(null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
            IDoodleListener doodleListener = DoodleView.this.getDoodleListener();
            if (doodleListener != null) {
                doodleListener.onSaved(DoodleView.this, file, null);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$save$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: DoodleView.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$save$2$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(continuation);
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
                Utils.INSTANCE.clearDoodleCacheDir();
                return Unit.INSTANCE;
            }
        }
    }

    private final void clearCurrentLayerCache() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            doodleDocument.getCurrentLayer().setCacheImageKey(null);
            Layer currentLayer = doodleDocument.getCurrentLayer();
            currentLayer.setCacheDataInfoKey(null);
            currentLayer.setCacheImageKey(null);
            Iterator<T> it = currentLayer.getLayerBitmapList().iterator();
            while (it.hasNext()) {
                ((LayerBitmap) it.next()).setCacheImageKey("");
            }
        }
    }

    public final void clearBackgroundCache() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument == null || !getIsClipImageOnSave()) {
            return;
        }
        doodleDocument.setCacheBgImageKey(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File saveDoodleBitmap(boolean ignoreCache) {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument == null) {
            return null;
        }
        Logger.INSTANCE.d(TAG, "do save createPreviewImage start");
        Bitmap bitmapCreatePreviewImage = createPreviewImage();
        if (bitmapCreatePreviewImage == null) {
            return null;
        }
        doodleDocument.setPreviewBitmap(processPreviewBitmap(bitmapCreatePreviewImage));
        doodleDocument.getDocumentInfo().setEditImage(!getIsClipImageOnSave());
        File file = new File(getContext().getCacheDir(), "doodle_" + System.currentTimeMillis());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        new DocumentWriter(context, doodleDocument, absolutePath).write(ignoreCache);
        Bitmap previewBitmap = doodleDocument.getPreviewBitmap();
        if (previewBitmap != null) {
            previewBitmap.recycle();
        }
        doodleDocument.clearBitmap();
        return file;
    }

    private final Bitmap processPreviewBitmap(Bitmap previewBitmap) {
        return getIsClipImageOnSave() ? smartClip(previewBitmap) : previewBitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File saveTempDoodleBitmap() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument == null) {
            return null;
        }
        if (!this.isDoCacheSaving) {
            Logger.INSTANCE.d(TAG, "saveTempDoodleBitmap cancel");
            return null;
        }
        Logger.INSTANCE.d(TAG, "do cache save createPreviewImage start ");
        doodleDocument.setPreviewBitmap(createPreviewImage());
        File file = new File(Utils.INSTANCE.getDoodleCacheDir(), "doodle_snapshot_" + hashCode());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        DocumentWriter documentWriter = new DocumentWriter(context, doodleDocument, absolutePath);
        documentWriter.setIsNeedCacheBgImageToFile();
        documentWriter.write(false);
        return file;
    }

    public final void addNewLayerBitmap() {
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.addNewLayerBitmap();
        }
    }

    private final String saveSyncState() {
        cancelAutoSave();
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument == null || (!this.doTouch && !this.mNeedChange && this.tempDoodleFilePath != null)) {
            Logger.INSTANCE.d(TAG, "saveSyncState Not Changed");
            return this.tempDoodleFilePath;
        }
        try {
            Intrinsics.checkNotNull(doodleDocument);
            Bitmap bitmapCreatePreviewImage = createPreviewImage();
            if (bitmapCreatePreviewImage == null) {
                Logger.INSTANCE.w(TAG, "saveSyncState: createPreviewImage = null");
                return null;
            }
            doodleDocument.setPreviewBitmap(bitmapCreatePreviewImage);
            doodleDocument.getDocumentInfo().setEditImage(!getIsClipImageOnSave());
            File file = new File(Utils.INSTANCE.getDoodleCacheDir(), "doodle_sync_snapshot_" + System.currentTimeMillis());
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            DocumentWriter documentWriter = new DocumentWriter(context, doodleDocument, absolutePath);
            documentWriter.setIsNeedCacheBgImageToFile();
            documentWriter.write(false);
            doodleDocument.setPreviewBitmap(null);
            Logger.INSTANCE.i(TAG, "saveSyncState: Successfully saved to " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "saveSyncState: Failed to save state. Exception: " + e);
            return null;
        }
    }

    public final void saveTempDoodleFile(Bundle saveInstance) {
        String strSaveSyncState = saveSyncState();
        if (strSaveSyncState != null) {
            this.tempDoodleFilePath = strSaveSyncState;
        }
        Logger.INSTANCE.d(TAG, "saveTempDoodleFile: Putting path to Bundle: " + this.tempDoodleFilePath);
        this.willRebuild = true;
        if (saveInstance != null) {
            saveInstance.putString("doodle_snapshot", this.tempDoodleFilePath);
        }
        if (saveInstance != null) {
            saveInstance.putBoolean("doodle_is_create_new", getIsCreateNewDoodle());
        }
        DoodleDocument doodleDocument = this.document;
        if ((doodleDocument != null ? doodleDocument.getEditImageId() : null) == null || saveInstance == null) {
            return;
        }
        DoodleDocument doodleDocument2 = this.document;
        saveInstance.putString("doodle_edit_image_id", doodleDocument2 != null ? doodleDocument2.getEditImageId() : null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$saveTempDoodle$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$saveTempDoodle$1", f = "DoodleView.kt", i = {0, 0, 0}, l = {R2.attr.dateTimePickerStyle}, m = "invokeSuspend", n = {"$this$runBlocking", "deferredDoodleFile", "startSaveTime"}, s = {"L$0", "L$1", "J$0"})
    static final class C02181 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        long J$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        C02181(Continuation<? super C02181> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02181 c02181 = DoodleView.this.new C02181(continuation);
            c02181.L$0 = obj;
            return c02181;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02181) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long j;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long jCurrentTimeMillis = System.currentTimeMillis();
                Logger.INSTANCE.d(DoodleView.TAG, "saveTempDoodle start save");
                Deferred deferredAsync$default = BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new DoodleView$saveTempDoodle$1$deferredDoodleFile$1(DoodleView.this, null), 2, null);
                this.L$0 = coroutineScope;
                this.L$1 = SpillingKt.nullOutSpilledVariable(deferredAsync$default);
                this.J$0 = jCurrentTimeMillis;
                this.label = 1;
                obj = deferredAsync$default.await(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                j = jCurrentTimeMillis;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j = this.J$0;
                ResultKt.throwOnFailure(obj);
            }
            File file = (File) obj;
            if (DoodleView.this.getIsDoCacheSaving() && CoroutineScopeKt.isActive(coroutineScope)) {
                DoodleView.this.setTempDoodleFilePath(file != null ? file.getAbsolutePath() : null);
                DoodleView.this.setDoCacheSaving(false);
                Logger.INSTANCE.d(DoodleView.TAG, "saveTempDoodle end saveAll cost " + (System.currentTimeMillis() - j) + " fileName " + DoodleView.this.getTempDoodleFilePath());
            } else {
                Logger.INSTANCE.d(DoodleView.TAG, "saveTempDoodle cancel");
            }
            return Unit.INSTANCE;
        }
    }

    public final void saveTempDoodle() throws InterruptedException {
        BuildersKt__BuildersKt.runBlocking$default(null, new C02181(null), 1, null);
    }

    public final List<Deferred<Object>> getLaseSnapShotJobs() {
        return this.laseSnapShotJobs;
    }

    public final void setLaseSnapShotJobs(List<Deferred<Object>> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.laseSnapShotJobs = list;
    }

    /* JADX INFO: renamed from: isDoCacheSaving, reason: from getter */
    public final boolean getIsDoCacheSaving() {
        return this.isDoCacheSaving;
    }

    public final void setDoCacheSaving(boolean z) {
        this.isDoCacheSaving = z;
    }

    public final void cacheDoodleSnapshot() throws InterruptedException {
        if (Intrinsics.areEqual(this.drawTime, this.lastCachedDrawTime)) {
            Logger.INSTANCE.d(TAG, "doodle not refresh, won't cache");
            return;
        }
        this.lastCachedDrawTime = this.drawTime;
        Logger.INSTANCE.d(TAG, "doodle do cache");
        long jCurrentTimeMillis = System.currentTimeMillis();
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            BuildersKt__BuildersKt.runBlocking$default(null, new DoodleView$cacheDoodleSnapshot$1$1(this, jCurrentTimeMillis, doodleDocument, null), 1, null);
        }
    }

    private final Bitmap createPreviewImage() {
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(1, doodleDocument.getWidth()), Math.max(1, doodleDocument.getHeight()), Bitmap.Config.ARGB_8888);
                Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
                draw(new Canvas(bitmapCreateBitmap));
                if (isEmptyBitmap(bitmapCreateBitmap) && !this.mNeedChange) {
                    Logger.INSTANCE.d(TAG, "isEmptyBitmap = true");
                    return null;
                }
                return bitmapCreateBitmap;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "do createPreviewImage failed." + e);
            }
        }
        return null;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void clear() {
        String str;
        if (!this.willRebuild && (str = this.tempDoodleFilePath) != null && !TextUtils.isEmpty(str)) {
            File file = new File(this.tempDoodleFilePath);
            if (file.exists()) {
                file.delete();
            }
        }
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument != null) {
            doodleDocument.clear();
        }
        Iterator<T> it = this.historyItemStack.iterator();
        while (it.hasNext()) {
            ((IAction) it.next()).destroy();
        }
        Iterator<T> it2 = this.redoItemStack.iterator();
        while (it2.hasNext()) {
            ((IAction) it2.next()).destroy();
        }
        clearRedoStack();
        Iterator<T> it3 = this.layerViewList.iterator();
        while (it3.hasNext()) {
            ((LayerView) it3.next()).clear();
        }
        cancelAddImageTask();
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public boolean undo(int step) {
        if (this.historyItemStack.isEmpty()) {
            return false;
        }
        while (step > 0 && !this.historyItemStack.isEmpty()) {
            IAction iActionPopHistoryAction = popHistoryAction();
            addRedoHistoryAction(iActionPopHistoryAction);
            iActionPopHistoryAction.undo(this);
            step--;
            asyncSaveDoodle(true);
        }
        return !this.historyItemStack.isEmpty();
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public boolean redo(int step) {
        synchronized (this.redoItemStack) {
            if (this.redoItemStack.isEmpty()) {
                return false;
            }
            while (step > 0 && !this.redoItemStack.isEmpty()) {
                IAction iActionPopRedoAction = popRedoAction();
                addNewHistoryAction(iActionPopRedoAction);
                iActionPopRedoAction.redo(this);
                step--;
                asyncSaveDoodle(true);
            }
            return !this.redoItemStack.isEmpty();
        }
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public boolean undo() {
        return undo(1);
    }

    public final void addNewHistoryAction(IAction action) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (!this.historyItemStack.isEmpty()) {
            this.historyItemStack.lastElement().cacheToFile();
            this.historyItemStack.lastElement().releaseJobs();
        }
        this.historyItemStack.add(action);
        if (this.historyItemStack.size() > this.MAX_HISTORY_STEPS) {
            IAction iActionFirstElement = this.historyItemStack.firstElement();
            this.historyItemStack.remove(iActionFirstElement);
            iActionFirstElement.destroy();
        }
    }

    public final void addRedoHistoryAction(IAction action) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (!this.redoItemStack.isEmpty()) {
            this.redoItemStack.lastElement().releaseJobs();
        }
        synchronized (this.redoItemStack) {
            this.redoItemStack.push(action);
        }
    }

    public final IAction popHistoryAction() {
        IAction iActionPop = this.historyItemStack.pop();
        if (!this.historyItemStack.isEmpty()) {
            this.historyItemStack.lastElement().restoreFromFile();
        }
        Intrinsics.checkNotNull(iActionPop);
        return iActionPop;
    }

    public final IAction popRedoAction() {
        IAction iActionPop = this.redoItemStack.pop();
        if (!this.redoItemStack.isEmpty()) {
            this.redoItemStack.lastElement().restoreFromFile();
        }
        Intrinsics.checkNotNull(iActionPop);
        return iActionPop;
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$clearRedoStack$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$clearRedoStack$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02161 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C02161(Continuation<? super C02161> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DoodleView.this.new C02161(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Stack stack = DoodleView.this.redoItemStack;
                DoodleView doodleView = DoodleView.this;
                synchronized (stack) {
                    for (IAction iAction : doodleView.redoItemStack) {
                        if (!doodleView.historyItemStack.contains(iAction)) {
                            iAction.destroy();
                        }
                    }
                    doodleView.redoItemStack.clear();
                    Unit unit = Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void clearRedoStack() {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C02161(null), 2, null);
    }

    public final void clearStack() {
        this.historyItemStack.clear();
        this.redoItemStack.clear();
    }

    /* JADX WARN: Code duplicated, block: B:4:0x000e A[PHI: r2
  0x000e: PHI (r2v2 float) = (r2v0 float), (r2v1 float) binds: [B:3:0x000c, B:6:0x0014] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.miui.doodle.base.core.IDoodle
    public void setDoodleScale(float scale, float pivotX, float pivotY) {
        float touchX = toTouchX(pivotX);
        float touchY = toTouchY(pivotY);
        float f = 0.5f;
        if (scale < 0.5f) {
            scale = f;
        } else {
            f = 5.0f;
            if (scale > 5.0f) {
                scale = f;
            }
        }
        setDoodleScale(scale);
        setDoodleTranslation(toTransX(touchX, pivotX), toTransY(touchY, pivotY));
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setDoodleTranslation(float transX, float transY) {
        setDoodleTranslationX(transX);
        setDoodleTranslationY(transY);
        IDoodle.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public float getDoodleTranslationX() {
        return this.doodleTranslationX;
    }

    public void setDoodleTranslationX(float f) {
        this.doodleTranslationX = f;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public float getDoodleTranslationY() {
        return this.doodleTranslationY;
    }

    public void setDoodleTranslationY(float f) {
        this.doodleTranslationY = f;
    }

    public final float getSize() {
        return this.size;
    }

    public final void setSize(float f) {
        this.size = f;
        IDoodle.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodle
    /* JADX INFO: renamed from: isDrawableOutside, reason: from getter */
    public boolean getIsDrawableOutside() {
        return this.isDrawableOutside;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void setDrawableOutside(boolean z) {
        this.isDrawableOutside = z;
    }

    public final boolean getMIsScrollingDoodle() {
        return this.mIsScrollingDoodle;
    }

    public final void setMIsScrollingDoodle(boolean z) {
        this.mIsScrollingDoodle = z;
    }

    public final void setScrollingDoodle(boolean scrollingDoodle) {
        this.mIsScrollingDoodle = scrollingDoodle;
        IDoodle.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public float getScreenDensity() {
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return uIUtils.dip2px(context, 1.0f);
    }

    public static /* synthetic */ IDoodleSelectableItem getSelectableItemInThisPoint$default(DoodleView doodleView, float f, float f2, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSelectableItemInThisPoint");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return doodleView.getSelectableItemInThisPoint(f, f2, z);
    }

    public final IDoodleSelectableItem getSelectableItemInThisPoint(float x, float y, boolean skipBrushPath) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView == null || (layer = currentLayerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) {
            return null;
        }
        for (LayerBitmap layerBitmap : layerBitmapList) {
            int size = layerBitmap.getItems().size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    IDoodleItem iDoodleItem = layerBitmap.getItems().get(size);
                    if (iDoodleItem.isDoodleEditable() && (iDoodleItem instanceof IDoodleSelectableItem) && (!skipBrushPath || !(iDoodleItem instanceof DoodleBrushPath))) {
                        IDoodleSelectableItem iDoodleSelectableItem = (IDoodleSelectableItem) iDoodleItem;
                        if (iDoodleSelectableItem.contains(x, y)) {
                            return iDoodleSelectableItem;
                        }
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
        }
        return null;
    }

    public final List<IDoodleItem> getStraightLineItems() {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        ArrayList arrayList = new ArrayList();
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null && (layer = currentLayerView.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null) {
            Iterator<T> it = layerBitmapList.iterator();
            while (it.hasNext()) {
                List<IDoodleItem> items = ((LayerBitmap) it.next()).getItems();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : items) {
                    IDoodleItem iDoodleItem = (IDoodleItem) obj;
                    if ((iDoodleItem instanceof DoodleShape) && ((DoodleShape) iDoodleItem).getGraphType() == GraphType.STRAIGHT_LINE) {
                        arrayList2.add(obj);
                    }
                }
                arrayList.addAll(arrayList2);
            }
        }
        return arrayList;
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void removeItem(IDoodleItem item, boolean needSaveToHistory) {
        Intrinsics.checkNotNullParameter(item, "item");
        if ((item instanceof DoodleBitmap) || (item instanceof DoodleText)) {
            LayerView currentLayerView = getCurrentLayerView();
            if (currentLayerView != null) {
                currentLayerView.removeItem(item, null);
            }
            LayerView currentLayerView2 = getCurrentLayerView();
            if (currentLayerView2 != null) {
                currentLayerView2.finishRemoveAction();
            }
            asyncSaveDoodle(true);
        }
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void removeLayerBitmap(LayerBitmap layerBitmap, int index, boolean needSaveToHistory) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView != null) {
            currentLayerView.removeLayerBitmap(layerBitmap);
        }
        Iterator<T> it = layerBitmap.getItems().iterator();
        while (it.hasNext()) {
            ((IDoodleItem) it.next()).onRemove();
        }
        if (needSaveToHistory) {
            Integer currentLayerIndex = getCurrentLayerIndex();
            pushToActionHistory(new RemoveLayerBitmapAction(layerBitmap, currentLayerIndex != null ? currentLayerIndex.intValue() : CollectionsKt.indexOf((List<? extends LayerView>) this.layerViewList, getCurrentLayerView()), index));
        }
    }

    public final void removeItemByElementEraser(RectF eraserRect, float touchX, float touchY) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Intrinsics.checkNotNullParameter(eraserRect, "eraserRect");
        System.currentTimeMillis();
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView == null || (layer = currentLayerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) {
            return;
        }
        Iterator<T> it = layerBitmapList.iterator();
        while (it.hasNext()) {
            List<IDoodleItem> items = ((LayerBitmap) it.next()).getItems();
            ArrayList arrayList = new ArrayList();
            for (Object obj : items) {
                IDoodleItem iDoodleItem = (IDoodleItem) obj;
                if (((iDoodleItem instanceof DoodleBrushPath) && ((DoodleBrushPath) iDoodleItem).contains(eraserRect)) || ((iDoodleItem instanceof DoodleSelectableItemBase) && ((DoodleSelectableItemBase) iDoodleItem).contains(touchX, touchY))) {
                    arrayList.add(obj);
                }
            }
            ArrayList<IDoodleItem> arrayList2 = arrayList;
            arrayList2.isEmpty();
            for (IDoodleItem iDoodleItem2 : arrayList2) {
                LayerView currentLayerView2 = getCurrentLayerView();
                if (currentLayerView2 != null) {
                    currentLayerView2.removeItem(iDoodleItem2, null);
                }
            }
        }
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public void eraserPathItem(Dot eraserDot) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Intrinsics.checkNotNullParameter(eraserDot, "eraserDot");
        LayerView currentLayerView = getCurrentLayerView();
        if (currentLayerView == null || (layer = currentLayerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) {
            return;
        }
        Iterator<T> it = layerBitmapList.iterator();
        while (it.hasNext()) {
            for (IDoodleItem iDoodleItem : ((LayerBitmap) it.next()).getItems()) {
                if (iDoodleItem instanceof DoodleBrushPath) {
                    DoodleBrushPath doodleBrushPath = (DoodleBrushPath) iDoodleItem;
                    if (UIUtils.INSTANCE.detectIntersect(doodleBrushPath.getBounds(), eraserDot.getRect())) {
                        doodleBrushPath.erase(eraserDot);
                    }
                }
            }
        }
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public int getRedoItemCount() {
        return this.redoItemStack.size();
    }

    @Override // com.miui.doodle.base.core.IDoodle
    public int getUndoItemCount() {
        return this.historyItemStack.size();
    }

    /* JADX INFO: renamed from: isEditMode, reason: from getter */
    public final boolean getIsEditMode() {
        return this.isEditMode;
    }

    public final void setEditMode(boolean z) {
        this.isEditMode = z;
        IDoodle.refresh$default(this, null, 1, null);
    }

    private final Bitmap smartClip(Bitmap bitmap) {
        int i;
        DoodleDocument doodleDocument = this.document;
        if (doodleDocument == null) {
            return null;
        }
        Rect rect = new Rect();
        Iterator<T> it = doodleDocument.getLayers().iterator();
        while (it.hasNext()) {
            rect.union(((Layer) it.next()).getBoundsInCanvas());
        }
        int width = bitmap.getWidth();
        int i2 = 40;
        if (rect.top < 0) {
            rect.top = 0;
            i = 0;
        } else {
            i = 40;
        }
        if (rect.bottom > doodleDocument.getHeight()) {
            rect.bottom = doodleDocument.getHeight();
            i2 = 0;
        }
        rect.set(0, rect.top, width, rect.bottom);
        int iHeight = rect.height() + 80;
        Canvas canvas = new Canvas();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(1, width), Math.max(1, iHeight), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        bitmapCreateBitmap.eraseColor(-1);
        canvas.setBitmap(bitmapCreateBitmap);
        Rect rect2 = new Rect();
        rect2.set(0, i, rect.width(), i2 + rect.height());
        canvas.drawBitmap(bitmap, rect, rect2, (Paint) null);
        bitmap.recycle();
        canvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleView$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "MAX_SCALE", "", "MIN_SCALE", "rotate", "Landroid/graphics/Bitmap;", DoodleBitmap.KEY_BITMAP, "degree", "", "isRecycle", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Bitmap rotate(Bitmap bitmap, int degree, boolean isRecycle) {
            Intrinsics.checkNotNullParameter(bitmap, "bitmap");
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            if (isRecycle) {
                bitmap.recycle();
            }
            return bitmapCreateBitmap;
        }
    }

    static {
        System.loadLibrary("doodle-lib");
    }

    public final Deferred<Object> getAutoSaveJob() {
        return this.autoSaveJob;
    }

    public final void setAutoSaveJob(Deferred<? extends Object> deferred) {
        this.autoSaveJob = deferred;
    }

    static /* synthetic */ void asyncSaveDoodle$default(DoodleView doodleView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: asyncSaveDoodle");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        doodleView.asyncSaveDoodle(z);
    }

    private final void asyncSaveDoodle(boolean instant) {
        if (this.doAutoSave) {
            Logger.INSTANCE.d(TAG, "asyncSaveDoodle() instant " + instant);
            cancelAutoSaveJob();
            if (this.cacheHandler == null) {
                return;
            }
            Logger logger = Logger.INSTANCE;
            CacheHandler cacheHandler = this.cacheHandler;
            Intrinsics.checkNotNull(cacheHandler);
            logger.d(TAG, "asyncSaveDoodle() hasMessage " + cacheHandler.hasMessages(0));
            CacheHandler cacheHandler2 = this.cacheHandler;
            Intrinsics.checkNotNull(cacheHandler2);
            if (cacheHandler2.hasMessages(0)) {
                return;
            }
            Logger.INSTANCE.d(TAG, "asyncSaveDoodle() sendMessage");
            if (instant) {
                CacheHandler cacheHandler3 = this.cacheHandler;
                Intrinsics.checkNotNull(cacheHandler3);
                cacheHandler3.sendEmptyMessage(0);
            } else {
                CacheHandler cacheHandler4 = this.cacheHandler;
                Intrinsics.checkNotNull(cacheHandler4);
                cacheHandler4.sendEmptyMessageDelayed(0, 500L);
            }
        }
    }

    public final void autoSave() {
        if (this.doAutoSave) {
            this.autoSaveJob = BuildersKt__Builders_commonKt.async$default(this.saveJobScope, null, null, new AnonymousClass1(null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleView$autoSave$1, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$autoSave$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = DoodleView.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws InterruptedException {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                Logger.INSTANCE.d(DoodleView.TAG, "autoSave start " + coroutineScope);
                DoodleView.this.cacheDoodleSnapshot();
                if (!CoroutineScopeKt.isActive(coroutineScope)) {
                    Logger.INSTANCE.d(DoodleView.TAG, "autoSave cancel and clearBackgroundCache " + coroutineScope);
                    DoodleView.this.clearBackgroundCache();
                }
                Logger.INSTANCE.d(DoodleView.TAG, "autoSave end " + coroutineScope);
            } catch (CancellationException unused) {
                Logger.INSTANCE.d(DoodleView.TAG, "autoSave cancel in CancellationException and clearBackgroundCache " + coroutineScope);
                DoodleView.this.clearBackgroundCache();
                Logger.INSTANCE.d(DoodleView.TAG, "autoSave canceled " + coroutineScope);
            }
            return Unit.INSTANCE;
        }
    }

    public final void cancelAutoSave() {
        clearAutoSaveTask();
        cancelAutoSaveJob();
    }

    public final void cancelAutoSaveJob() {
        try {
            Iterator<T> it = this.laseSnapShotJobs.iterator();
            while (it.hasNext()) {
                Deferred deferred = (Deferred) it.next();
                if (!deferred.isCompleted()) {
                    Job.DefaultImpls.cancel$default((Job) deferred, (CancellationException) null, 1, (Object) null);
                }
            }
            Deferred<? extends Object> deferred2 = this.autoSaveJob;
            if (deferred2 != null) {
                deferred2.cancel(new CancellationException());
            }
        } catch (Exception unused) {
            Deferred<? extends Object> deferred3 = this.autoSaveJob;
            if (deferred3 != null) {
                deferred3.cancel(new CancellationException());
            }
        } catch (Throwable th) {
            Deferred<? extends Object> deferred4 = this.autoSaveJob;
            if (deferred4 != null) {
                deferred4.cancel(new CancellationException());
            }
            throw th;
        }
    }

    public final void clearAutoSaveTask() {
        Logger.INSTANCE.d(TAG, "asyncSaveDoodle() clearAutoSaveTask");
        CacheHandler cacheHandler = this.cacheHandler;
        if (cacheHandler != null) {
            cacheHandler.removeMessages(0);
        }
    }

    /* JADX INFO: compiled from: DoodleView.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleView$CacheHandler;", "Landroid/os/Handler;", "container", "Lcom/miui/doodle/feature/draw/DoodleView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/draw/DoodleView;)V", "mWkContainer", "Ljava/lang/ref/WeakReference;", "handleMessage", "", "msg", "Landroid/os/Message;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class CacheHandler extends Handler {
        private final WeakReference<DoodleView> mWkContainer;

        public CacheHandler(DoodleView container) {
            Intrinsics.checkNotNullParameter(container, "container");
            this.mWkContainer = new WeakReference<>(container);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            DoodleView doodleView;
            Intrinsics.checkNotNullParameter(msg, "msg");
            super.handleMessage(msg);
            if (msg.what != 0 || (doodleView = this.mWkContainer.get()) == null) {
                return;
            }
            doodleView.autoSave();
        }
    }
}
