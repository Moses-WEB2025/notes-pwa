package com.miui.doodle.feature.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.DoodleRotatableItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.action.ErasedBrushPathInfo;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.doodle.utils.UIUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import miui.cloud.CloudPushConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleBrushPath.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 Ç\u00012\u00020\u00012\u00020\u0002:\u0002Ç\u0001BU\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010¢\u0006\u0004\b\u0011\u0010\u0012J\b\u0010g\u001a\u00020hH\u0014J\u0018\u0010i\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\nH\u0002J\u0018\u0010l\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\nH\u0002J>\u0010m\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\n2\u0006\u0010n\u001a\u00020\n2\u0006\u0010o\u001a\u00020\n2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010p\u001a\u00020'H\u0002J>\u0010q\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\n2\u0006\u0010n\u001a\u00020\n2\u0006\u0010o\u001a\u00020\n2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010p\u001a\u00020'H\u0002J\u000e\u0010u\u001a\u00020h2\u0006\u0010v\u001a\u00020\u0019J\b\u0010w\u001a\u00020hH\u0002J\u0006\u0010x\u001a\u00020yJ\u0006\u0010z\u001a\u00020hJ\u0014\u0010{\u001a\u00020h2\f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00190}J\u0014\u0010~\u001a\u00020h2\f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00190}J\u0018\u0010\u007f\u001a\u00020h2\u0007\u0010\u0080\u0001\u001a\u00020L2\u0007\u0010\u0081\u0001\u001a\u00020\u0019J\u001b\u0010\u0082\u0001\u001a\u00020h2\u0007\u0010\u0080\u0001\u001a\u00020L2\u0007\u0010\u0081\u0001\u001a\u00020\u0019H\u0002J5\u0010\u0083\u0001\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0007\u0010\u0084\u0001\u001a\u00020\n2\u0007\u0010\u0085\u0001\u001a\u00020\n2\u0007\u0010\u0086\u0001\u001a\u00020\n2\u0007\u0010\u0087\u0001\u001a\u00020\nH\u0002J9\u0010\u0088\u0001\u001a\u00020h2\u0007\u0010\u0080\u0001\u001a\u00020L2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010R2\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010'2\t\u0010\u008b\u0001\u001a\u0004\u0018\u00010'H\u0014¢\u0006\u0003\u0010\u008c\u0001J\t\u0010\u008d\u0001\u001a\u00020hH\u0016J\t\u0010\u008e\u0001\u001a\u00020hH\u0016J\u0012\u0010\u008f\u0001\u001a\u00020h2\u0007\u0010\u0090\u0001\u001a\u00020LH\u0016J#\u0010\u0091\u0001\u001a\u00020h2\u0007\u0010\u0090\u0001\u001a\u00020L2\t\u0010\u008b\u0001\u001a\u0004\u0018\u00010'H\u0016¢\u0006\u0003\u0010\u0092\u0001J\u001b\u0010\u0093\u0001\u001a\u00020h2\u0007\u0010\u0094\u0001\u001a\u00020\u00102\u0007\u0010\u0095\u0001\u001a\u00020\u0010H\u0016J\u001f\u0010\u0096\u0001\u001a\u00020h2\u0007\u0010\u0080\u0001\u001a\u00020L2\u000b\b\u0002\u0010\u0089\u0001\u001a\u0004\u0018\u00010RH\u0002J\u0010\u0010\u0097\u0001\u001a\u00020h2\u0007\u0010\u0098\u0001\u001a\u00020FJ\u0019\u0010\u0099\u0001\u001a\u00020h2\u0007\u0010\u0094\u0001\u001a\u00020\u00102\u0007\u0010\u0095\u0001\u001a\u00020\u0010J\u001a\u0010 \u0001\u001a\u00020h2\u0007\u0010\u0081\u0001\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\nH\u0002J:\u0010¡\u0001\u001a\t\u0012\u0004\u0012\u00020\u00190¢\u00012\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\n2\u0007\u0010£\u0001\u001a\u00020\n2\b\u0010¤\u0001\u001a\u00030¥\u00012\u0007\u0010¦\u0001\u001a\u00020'J5\u0010§\u0001\u001a\t\u0012\u0004\u0012\u00020\u00190¢\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u00192\u0007\u0010¦\u0001\u001a\u00020'2\u0006\u0010p\u001a\u00020'2\t\b\u0002\u0010¨\u0001\u001a\u00020'H\u0004J,\u0010©\u0001\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\n2\u0007\u0010£\u0001\u001a\u00020\n2\b\u0010¤\u0001\u001a\u00030¥\u0001H\u0016J\u0007\u0010ª\u0001\u001a\u00020hJ\u0019\u0010«\u0001\u001a\u00020h2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\nH\u0002J\u001b\u0010¬\u0001\u001a\u00020'2\u0007\u0010\u00ad\u0001\u001a\u00020\n2\u0007\u0010®\u0001\u001a\u00020XH\u0002J\u0019\u0010¯\u0001\u001a\u00020'2\u0006\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020\nH\u0016J\u0013\u0010¯\u0001\u001a\u00020'2\b\u0010°\u0001\u001a\u00030±\u0001H\u0016J\u0012\u0010³\u0001\u001a\u00020h2\u0007\u0010°\u0001\u001a\u00020RH\u0014J\u0019\u0010³\u0001\u001a\u00020h2\u0007\u0010¶\u0001\u001a\u00020\n2\u0007\u0010·\u0001\u001a\u00020\nJ\u0007\u0010³\u0001\u001a\u00020hJ\t\u0010¸\u0001\u001a\u00020\u0000H\u0016J\t\u0010»\u0001\u001a\u00020hH\u0002J\n\u0010¼\u0001\u001a\u00030º\u0001H\u0016J\t\u0010½\u0001\u001a\u00020\bH\u0016J\u0007\u0010¾\u0001\u001a\u00020hJ\u0007\u0010¿\u0001\u001a\u00020hJ\u0007\u0010À\u0001\u001a\u00020hJ\u0007\u0010Á\u0001\u001a\u00020hJ\u0007\u0010Â\u0001\u001a\u00020hR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\"\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010!\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010(\"\u0004\b-\u0010*R\u000e\u0010.\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\u000200X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u001c\u00103\u001a\u0004\u0018\u00010\u0019X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001c\u00108\u001a\u0004\u0018\u00010\u0019X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\u000e\u0010;\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001c\u0010E\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001c\u0010K\u001a\u0004\u0018\u00010LX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u0010\u0010Q\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010T\u001a\u00020\nX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010B\"\u0004\bV\u0010DR\u000e\u0010W\u001a\u00020XX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010Y\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010H\"\u0004\b[\u0010JR\u001a\u0010\\\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u001c\u0010a\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010H\"\u0004\bc\u0010JR\u001a\u0010d\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010^\"\u0004\bf\u0010`R\u001a\u0010r\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bs\u0010(\"\u0004\bt\u0010*R\u001d\u0010\u009a\u0001\u001a\u00020\nX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009b\u0001\u0010B\"\u0005\b\u009c\u0001\u0010DR\u0010\u0010\u009d\u0001\u001a\u00030\u009e\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u009f\u0001\u001a\u00020XX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010²\u0001\u001a\u00020'8F¢\u0006\u0007\u001a\u0005\b²\u0001\u0010(R\u001d\u0010´\u0001\u001a\u00020'X\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b´\u0001\u0010(\"\u0005\bµ\u0001\u0010*R\u0012\u0010¹\u0001\u001a\u0005\u0018\u00010º\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010Ã\u0001\u001a\u00030Ä\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÅ\u0001\u0010Æ\u0001¨\u0006È\u0001"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "Lcom/miui/doodle/base/DoodleRotatableItemBase;", "Lkotlinx/coroutines/CoroutineScope;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "id", "", "itemRotate", "", "itemScale", "location", "Landroid/graphics/PointF;", "pivotPoint", "alpha", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Lcom/miui/doodle/feature/draw/DoodlePen;Ljava/lang/String;FFLandroid/graphics/PointF;Landroid/graphics/PointF;I)V", "getAlpha", "()I", "setAlpha", "(I)V", "originDots", "", "Lcom/miui/doodle/document/Dot;", "getOriginDots", "()Ljava/util/List;", DoodleBrushPath.KEY_DOTS, "getDots", "eraserDots", "", "getEraserDots", "()Ljava/util/Set;", "willRemoveDots", "getWillRemoveDots", "setWillRemoveDots", "(Ljava/util/Set;)V", "isShape", "", "()Z", "setShape", "(Z)V", "willEnd", "getWillEnd", "setWillEnd", "minTouchGap", "path", "Landroid/graphics/Path;", "getPath", "()Landroid/graphics/Path;", "startDot", "getStartDot", "()Lcom/miui/doodle/document/Dot;", "setStartDot", "(Lcom/miui/doodle/document/Dot;)V", "lastDot", "getLastDot", "setLastDot", "lastVelocity", "lastScale", "random", "Ljava/util/Random;", "spacing", "pathWidth", "getPathWidth", "()F", "setPathWidth", "(F)V", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "bitmapCanvas", "Landroid/graphics/Canvas;", "getBitmapCanvas", "()Landroid/graphics/Canvas;", "setBitmapCanvas", "(Landroid/graphics/Canvas;)V", "onDrawCanvasRect", "Landroid/graphics/Rect;", "oldPt", "lastDrawDistance", "getLastDrawDistance", "setLastDrawDistance", "tempXYV", "", "cacheBitmapBeforeChanged", "getCacheBitmapBeforeChanged", "setCacheBitmapBeforeChanged", "cacheImageKeyBeforeChanged", "getCacheImageKeyBeforeChanged", "()Ljava/lang/String;", "setCacheImageKeyBeforeChanged", "(Ljava/lang/String;)V", "cacheBitmapAfterChanged", "getCacheBitmapAfterChanged", "setCacheBitmapAfterChanged", "cacheImageKeyAfterChanged", "getCacheImageKeyAfterChanged", "setCacheImageKeyAfterChanged", "initLayerAndCanvas", "", "moveToThread", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "moveToAction", "addSpot", "tipScale", "tipAlpha", "needRefresh", "addEraseSpot", "needTrimEraserDots", "getNeedTrimEraserDots", "setNeedTrimEraserDots", "erase", "eraserDot", "redrawEraserBitmap", "createEraserBrushPathInfo", "Lcom/miui/doodle/feature/action/ErasedBrushPathInfo;", "finishChangeAction", "removeEraserDots", "removedDots", "", "restoreEraserDots", "drawDot", "canvas", "dot", "drawEraseDot", "getBrushSpotAngle", "oldX", "oldY", "curX", "curY", "doDraw", Notes.Data.DIRTY, "isElementRedraw", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "clear", "cache", "drawToView", "viewCanvas", "elementRedraw", "(Landroid/graphics/Canvas;Ljava/lang/Boolean;)V", "elementRedrawToBitmap", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "drawToCanvas", "updateBitmap", "newBm", "rebuildBitmap", "totalDistance", "getTotalDistance", "setTotalDistance", "mPathMeasure", "Landroid/graphics/PathMeasure;", "tempPos", "startPath", "updatePath", "", "z", Constants.TIMESTAMP, "", "isLast", "updatePathInner", "needScale", "endPath", "trimEraserDots", "onTouchDown", "getXYVAtDistance", "distance", "xyv", "contains", "rect", "Landroid/graphics/RectF;", "isEraserPath", "resetBounds", "isSelected", "setSelected", "offsetX", "offsetY", "copy", "cachedJSONObject", "Lorg/json/JSONObject;", "cacheJSONObject", "toJSONObject", "toString", "cacheImageBeforeChanged", "cacheImageBeforeChangedToFile", "cacheImageAfterChanged", "cacheImageAfterChangedToFile", "releaseCacheImageBeforeAndAfterChanged", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleBrushPath extends DoodleRotatableItemBase implements CoroutineScope {
    private static final int CACHED_SIZE = 200;
    public static final String KEY_ALPHA = "alpha";
    public static final String KEY_DOTS = "dots";
    public static final String KEY_ERASER_DOTS = "eraser_dots";
    public static final String KEY_ORIGINAL_DOTS = "original_dots";
    private static final String TAG = "DoodleBrushPath";
    private static final float TwoPi = 6.2831855f;
    private static Bitmap brushGlobalCacheBitmap;
    private static final Paint clearPaint;
    private static final Paint dotTempPaint;
    private static final Paint dstInPaint;
    private static Canvas globalCacheCanvas;
    private static final Paint normalPaint;
    private static final Paint srcPaint;
    private final /* synthetic */ CoroutineScope $$delegate_0;
    private int alpha;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Bitmap cacheBitmapAfterChanged;
    private Bitmap cacheBitmapBeforeChanged;
    private String cacheImageKeyAfterChanged;
    private String cacheImageKeyBeforeChanged;
    private JSONObject cachedJSONObject;
    private final List<Dot> dots;
    private final Set<Dot> eraserDots;
    private boolean isSelected;
    private boolean isShape;
    private Dot lastDot;
    private float lastDrawDistance;
    private float lastScale;
    private float lastVelocity;
    private final PathMeasure mPathMeasure;
    private float minTouchGap;
    private boolean needTrimEraserDots;
    private PointF oldPt;
    private Rect onDrawCanvasRect;
    private final List<Dot> originDots;
    private final Path path;
    private float pathWidth;
    private Random random;
    private float spacing;
    private Dot startDot;
    private final float[] tempPos;
    private final float[] tempXYV;
    private float totalDistance;
    private boolean willEnd;
    private Set<Dot> willRemoveDots;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final HashMap<String, Bitmap> sColoredTextureCache = new HashMap<>();

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cache() {
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    public final void rebuildBitmap(int width, int height) {
    }

    public /* synthetic */ DoodleBrushPath(IDoodle iDoodle, DoodlePen doodlePen, String str, float f, float f2, PointF pointF, PointF pointF2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, (i2 & 2) != 0 ? DoodlePenManager.INSTANCE.getPencil() : doodlePen, (i2 & 4) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i2 & 8) != 0 ? 0.0f : f, (i2 & 16) != 0 ? 1.0f : f2, (i2 & 32) != 0 ? new PointF() : pointF, (i2 & 64) != 0 ? new PointF() : pointF2, (i2 & 128) != 0 ? 255 : i);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public int getAlpha() {
        return this.alpha;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setAlpha(int i) {
        this.alpha = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleBrushPath(IDoodle doodle, DoodlePen pen, String id, float f, float f2, PointF location, PointF pivotPoint, int i) {
        super(doodle, pen, id, f, f2, location, pivotPoint);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(pen, "pen");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.$$delegate_0 = CoroutineScopeKt.MainScope();
        this.alpha = i;
        this.originDots = new ArrayList();
        this.dots = new ArrayList();
        this.eraserDots = new LinkedHashSet();
        this.minTouchGap = 1.0f;
        this.path = new Path();
        this.random = new Random();
        this.oldPt = new PointF();
        this.tempXYV = new float[2];
        this.cacheImageKeyBeforeChanged = "";
        this.cacheImageKeyAfterChanged = "";
        this.onDrawCanvasRect = new Rect();
        this.oldPt = new PointF();
        this.pathWidth = getPen().getFinalSize();
        this.spacing = getPen().getSpacing() * this.pathWidth;
        setAlpha((int) (pen.getAlpha() * getPen().getColorPatchAlpha() * 255.0f));
        this.mPathMeasure = new PathMeasure();
        this.tempPos = new float[2];
    }

    public final List<Dot> getOriginDots() {
        return this.originDots;
    }

    public final List<Dot> getDots() {
        return this.dots;
    }

    protected final Set<Dot> getEraserDots() {
        return this.eraserDots;
    }

    public final Set<Dot> getWillRemoveDots() {
        return this.willRemoveDots;
    }

    public final void setWillRemoveDots(Set<Dot> set) {
        this.willRemoveDots = set;
    }

    /* JADX INFO: renamed from: isShape, reason: from getter */
    public final boolean getIsShape() {
        return this.isShape;
    }

    public final void setShape(boolean z) {
        this.isShape = z;
    }

    public final boolean getWillEnd() {
        return this.willEnd;
    }

    public final void setWillEnd(boolean z) {
        this.willEnd = z;
    }

    protected final Path getPath() {
        return this.path;
    }

    protected final Dot getStartDot() {
        return this.startDot;
    }

    protected final void setStartDot(Dot dot) {
        this.startDot = dot;
    }

    protected final Dot getLastDot() {
        return this.lastDot;
    }

    protected final void setLastDot(Dot dot) {
        this.lastDot = dot;
    }

    public final float getPathWidth() {
        return this.pathWidth;
    }

    public final void setPathWidth(float f) {
        this.pathWidth = f;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final Canvas getBitmapCanvas() {
        return this.bitmapCanvas;
    }

    public final void setBitmapCanvas(Canvas canvas) {
        this.bitmapCanvas = canvas;
    }

    protected final float getLastDrawDistance() {
        return this.lastDrawDistance;
    }

    protected final void setLastDrawDistance(float f) {
        this.lastDrawDistance = f;
    }

    public final Bitmap getCacheBitmapBeforeChanged() {
        return this.cacheBitmapBeforeChanged;
    }

    public final void setCacheBitmapBeforeChanged(Bitmap bitmap) {
        this.cacheBitmapBeforeChanged = bitmap;
    }

    public final String getCacheImageKeyBeforeChanged() {
        return this.cacheImageKeyBeforeChanged;
    }

    public final void setCacheImageKeyBeforeChanged(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.cacheImageKeyBeforeChanged = str;
    }

    public final Bitmap getCacheBitmapAfterChanged() {
        return this.cacheBitmapAfterChanged;
    }

    public final void setCacheBitmapAfterChanged(Bitmap bitmap) {
        this.cacheBitmapAfterChanged = bitmap;
    }

    public final String getCacheImageKeyAfterChanged() {
        return this.cacheImageKeyAfterChanged;
    }

    public final void setCacheImageKeyAfterChanged(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.cacheImageKeyAfterChanged = str;
    }

    protected void initLayerAndCanvas() {
        if (brushGlobalCacheBitmap == null) {
            brushGlobalCacheBitmap = Bitmap.createBitmap(getDoodle().getDoodleWidth(), getDoodle().getDoodleHeight(), Bitmap.Config.ARGB_8888);
            Bitmap bitmap = brushGlobalCacheBitmap;
            Intrinsics.checkNotNull(bitmap);
            globalCacheCanvas = new Canvas(bitmap);
        }
        Canvas canvas = globalCacheCanvas;
        this.bitmapCanvas = canvas;
        this.bitmap = brushGlobalCacheBitmap;
        Intrinsics.checkNotNull(canvas);
        canvas.drawPaint(clearPaint);
    }

    private final void moveToThread(float x, float y) {
        moveToAction(x, y);
    }

    private final void moveToAction(float x, float y) {
        this.oldPt.set(x, y);
    }

    private final void addSpot(float x, float y, float tipScale, float tipAlpha, List<Dot> dots, boolean needRefresh) {
        Canvas canvas;
        int i = getPen().getAirBrushMode() ? 3 : 1;
        float fSin = y;
        int i2 = 0;
        float fCos = x;
        while (i2 < i) {
            if (getPen().getSpread() > 0.0f) {
                float fNextFloat = this.random.nextFloat() * TwoPi;
                fCos += (float) Math.cos(getPen().getSpread() * fNextFloat * this.pathWidth);
                fSin += (float) Math.sin(fNextFloat * getPen().getSpread() * this.pathWidth);
            }
            float f = fCos;
            float f2 = fSin;
            Dot dot = new Dot(f, f2, 0.0f, 0L, this.pathWidth, getPen().nextMaskId(), getBrushSpotAngle(getPen(), this.oldPt.x, this.oldPt.y, x, y), getPen().getColorInt(), (int) (getPen().getAlpha() * getPen().getColorPatchAlpha() * tipAlpha * 255.0f), tipScale, 12, null);
            this.dots.add(dot);
            dots.add(dot);
            if (isEraserPath()) {
                getDoodle().eraserPathItem(dot);
            } else if (needRefresh && (canvas = this.bitmapCanvas) != null) {
                drawDot(canvas, dot);
            }
            i2++;
            fCos = f;
            fSin = f2;
        }
        this.oldPt.set(x, y);
    }

    private final void addEraseSpot(float x, float y, float tipScale, float tipAlpha, List<Dot> dots, boolean needRefresh) {
        float f;
        float fSin;
        if (getPen().getSpread() > 0.0f) {
            float fNextFloat = this.random.nextFloat() * TwoPi;
            float fCos = x + ((float) Math.cos(getPen().getSpread() * fNextFloat * this.pathWidth));
            fSin = y + ((float) Math.sin(fNextFloat * getPen().getSpread() * this.pathWidth));
            f = fCos;
        } else {
            f = x;
            fSin = y;
        }
        float f2 = fSin;
        float f3 = f;
        Dot dot = new Dot(f3, f2, 0.0f, 0L, this.pathWidth, getPen().nextMaskId(), getBrushSpotAngle(getPen(), this.oldPt.x, this.oldPt.y, x, y), getPen().getColorInt(), (int) (getPen().getAlpha() * getPen().getColorPatchAlpha() * tipAlpha * 255.0f), tipScale, 12, null);
        this.dots.add(dot);
        dots.add(dot);
        if (isEraserPath()) {
            getDoodle().eraserPathItem(dot);
        }
        this.oldPt.set(x, y);
    }

    public final boolean getNeedTrimEraserDots() {
        return this.needTrimEraserDots;
    }

    public final void setNeedTrimEraserDots(boolean z) {
        this.needTrimEraserDots = z;
    }

    public final void erase(Dot eraserDot) {
        Intrinsics.checkNotNullParameter(eraserDot, "eraserDot");
        if (!getIsDirty()) {
            this.willRemoveDots = new LinkedHashSet();
            setDirty(true);
        }
        this.needTrimEraserDots = true;
        synchronized (this.eraserDots) {
            this.eraserDots.add(eraserDot);
        }
        Set<Dot> set = this.willRemoveDots;
        if (set != null) {
            set.add(eraserDot);
        }
        this.cachedJSONObject = null;
    }

    private final void redrawEraserBitmap() {
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        DoodleCacheManager.INSTANCE.removeKey(getId());
        this.bitmap = null;
    }

    public final ErasedBrushPathInfo createEraserBrushPathInfo() {
        List list;
        if (!this.eraserDots.isEmpty()) {
            synchronized (this.eraserDots) {
                list = CollectionsKt.toList(this.eraserDots);
                Unit unit = Unit.INSTANCE;
            }
            Bitmap bitmap = DoodleCacheManager.INSTANCE.get(getId());
            if (bitmap != null) {
                synchronized (bitmap) {
                    if (!bitmap.isRecycled()) {
                        Canvas canvas = new Canvas(bitmap);
                        this.bitmapCanvas = canvas;
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            drawEraseDot(canvas, (Dot) it.next());
                        }
                        this.bitmapCanvas = null;
                    }
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        }
        setDirty(false);
        Set<Dot> set = this.willRemoveDots;
        Intrinsics.checkNotNull(set);
        return new ErasedBrushPathInfo(this, set);
    }

    public final void finishChangeAction() {
        setDirty(false);
        releaseCacheImageBeforeAndAfterChanged();
    }

    public final void removeEraserDots(Set<Dot> removedDots) {
        Intrinsics.checkNotNullParameter(removedDots, "removedDots");
        synchronized (this.eraserDots) {
            try {
                this.eraserDots.removeAll(removedDots);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Undo remove eraseDots error " + e);
            }
            cacheJSONObject();
            Unit unit = Unit.INSTANCE;
        }
        redrawEraserBitmap();
    }

    public final void restoreEraserDots(Set<Dot> removedDots) {
        Intrinsics.checkNotNullParameter(removedDots, "removedDots");
        synchronized (this.eraserDots) {
            this.eraserDots.addAll(removedDots);
            cacheJSONObject();
            Unit unit = Unit.INSTANCE;
        }
        redrawEraserBitmap();
    }

    public final void drawDot(Canvas canvas, Dot dot) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(dot, "dot");
        float halfSize = dot.getHalfSize();
        canvas.save();
        canvas.translate(dot.getX(), dot.getY());
        canvas.scale(dot.getScale(), dot.getScale());
        canvas.rotate((float) Math.toDegrees(dot.getAngle()));
        if (getPen().isEraser()) {
            canvas.drawCircle(0.0f, 0.0f, halfSize, clearPaint);
        } else if (getPen().getUseSimpleTexture()) {
            Paint paint = dotTempPaint;
            if (paint.getColor() != dot.getColor()) {
                paint.setColor(dot.getColor());
            }
            canvas.drawCircle(0.0f, 0.0f, halfSize, paint);
        } else {
            Bitmap textureByDot = INSTANCE.getTextureByDot(dot);
            if (textureByDot != null) {
                float f = -halfSize;
                canvas.drawBitmap(textureByDot, f, f, dotTempPaint);
            }
        }
        canvas.restore();
    }

    private final void drawEraseDot(Canvas canvas, Dot dot) {
        float halfSize = dot.getHalfSize();
        canvas.save();
        canvas.translate(dot.getX(), dot.getY());
        canvas.scale(dot.getScale(), dot.getScale());
        canvas.rotate((float) Math.toDegrees(dot.getAngle()));
        canvas.drawCircle(0.0f, 0.0f, halfSize, clearPaint);
        canvas.restore();
    }

    private final float getBrushSpotAngle(DoodlePen pen, float oldX, float oldY, float curX, float curY) {
        float angle = pen.getAngle() * TwoPi;
        if (pen.getUseFlowingAngle()) {
            angle += ((float) Math.atan2(curY - oldY, curX - oldX)) - 1.5707964f;
        }
        return pen.getAngleJitter() > 0.0f ? angle + ((this.random.nextFloat() - 0.5f) * TwoPi * pen.getAngleJitter()) : angle;
    }

    @Override // com.miui.doodle.base.DoodleItemBase
    protected void doDraw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        Intrinsics.checkNotNull(isElementRedraw);
        if (isElementRedraw.booleanValue()) {
            elementRedraw(canvas, isDrawToView);
        } else {
            drawToView(canvas);
        }
        canvas.restore();
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void clear() {
        Bitmap bitmap;
        super.clear();
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            Boolean boolValueOf = bitmap2 != null ? Boolean.valueOf(bitmap2.isRecycled()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (!boolValueOf.booleanValue() && (bitmap = this.bitmap) != null) {
                bitmap.recycle();
            }
        }
        this.bitmapCanvas = null;
    }

    public void drawToView(Canvas viewCanvas) {
        Intrinsics.checkNotNullParameter(viewCanvas, "viewCanvas");
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            Paint paint = normalPaint;
            if (paint.getAlpha() != getAlpha()) {
                paint.setAlpha(getAlpha());
            }
            System.currentTimeMillis();
            viewCanvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return;
        }
        System.currentTimeMillis();
        Iterator<T> it = this.dots.iterator();
        while (it.hasNext()) {
            drawDot(viewCanvas, (Dot) it.next());
        }
        Iterator it2 = CollectionsKt.toList(this.eraserDots).iterator();
        while (it2.hasNext()) {
            drawEraseDot(viewCanvas, (Dot) it2.next());
        }
    }

    public void elementRedraw(Canvas viewCanvas, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(viewCanvas, "viewCanvas");
        Paint paint = normalPaint;
        if (paint.getAlpha() != getAlpha()) {
            paint.setAlpha(getAlpha());
        }
        Bitmap bitmap = DoodleCacheManager.INSTANCE.get(getId());
        if (bitmap != null && !bitmap.isRecycled()) {
            viewCanvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return;
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            Intrinsics.checkNotNull(bitmap2);
            if (!bitmap2.isRecycled()) {
                Bitmap bitmap3 = this.bitmap;
                Intrinsics.checkNotNull(bitmap3);
                viewCanvas.drawBitmap(bitmap3, 0.0f, 0.0f, paint);
                Intrinsics.checkNotNull(isDrawToView);
                if (isDrawToView.booleanValue()) {
                    return;
                }
                DoodleCacheManager.INSTANCE.put(getId(), this.bitmap);
                return;
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(viewCanvas.getWidth(), viewCanvas.getHeight(), Bitmap.Config.ARGB_8888);
        this.bitmap = bitmapCreateBitmap;
        if (bitmapCreateBitmap != null) {
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Iterator<T> it = this.dots.iterator();
            while (it.hasNext()) {
                drawDot(canvas, (Dot) it.next());
            }
            Iterator it2 = CollectionsKt.toList(this.eraserDots).iterator();
            while (it2.hasNext()) {
                drawEraseDot(canvas, (Dot) it2.next());
            }
            viewCanvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, normalPaint);
        }
        Intrinsics.checkNotNull(isDrawToView);
        if (isDrawToView.booleanValue()) {
            return;
        }
        DoodleCacheManager.INSTANCE.put(getId(), this.bitmap);
    }

    public void elementRedrawToBitmap(int width, int height) {
        Bitmap bitmap = DoodleCacheManager.INSTANCE.get(getId());
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.bitmap = bitmapCreateBitmap;
            if (bitmapCreateBitmap != null) {
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                Iterator<T> it = this.dots.iterator();
                while (it.hasNext()) {
                    drawDot(canvas, (Dot) it.next());
                }
                Iterator it2 = CollectionsKt.toList(this.eraserDots).iterator();
                while (it2.hasNext()) {
                    drawEraseDot(canvas, (Dot) it2.next());
                }
            }
        }
    }

    static /* synthetic */ void drawToCanvas$default(DoodleBrushPath doodleBrushPath, Canvas canvas, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawToCanvas");
        }
        if ((i & 2) != 0) {
            rect = null;
        }
        doodleBrushPath.drawToCanvas(canvas, rect);
    }

    private final void drawToCanvas(Canvas canvas, Rect dirty) {
        canvas.save();
        System.currentTimeMillis();
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            Paint paint = normalPaint;
            paint.setAlpha(getAlpha());
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            paint.setAlpha(255);
        } else if (dirty != null) {
            for (Dot dot : this.dots) {
                if (UIUtils.INSTANCE.detectIntersect(dirty, dot.getRect())) {
                    drawDot(canvas, dot);
                }
            }
        } else {
            Iterator<T> it = this.dots.iterator();
            while (it.hasNext()) {
                drawDot(canvas, (Dot) it.next());
            }
        }
        for (Dot dot2 : this.eraserDots) {
            if (dirty != null) {
                if (UIUtils.INSTANCE.detectIntersect(dirty, dot2.getRect())) {
                    drawEraseDot(canvas, dot2);
                }
            } else {
                drawEraseDot(canvas, dot2);
            }
        }
        System.currentTimeMillis();
        canvas.restore();
    }

    public final void updateBitmap(Bitmap newBm) {
        Intrinsics.checkNotNullParameter(newBm, "newBm");
        Bitmap.Config config = newBm.getConfig();
        Intrinsics.checkNotNull(config);
        Bitmap bitmapCopy = newBm.copy(config, true);
        this.bitmap = bitmapCopy;
        this.bitmapCanvas = bitmapCopy != null ? new Canvas(bitmapCopy) : null;
        DoodleCacheManager.INSTANCE.put(getId(), this.bitmap);
        this.bitmapCanvas = null;
        setDirty(false);
    }

    protected final float getTotalDistance() {
        return this.totalDistance;
    }

    protected final void setTotalDistance(float f) {
        this.totalDistance = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startPath(Dot dot, float minTouchGap) {
        this.path.reset();
        this.path.moveTo(dot.getX(), dot.getY());
        this.startDot = dot;
        this.dots.add(dot);
        this.lastDot = dot;
        this.lastVelocity = 0.0f;
        this.lastScale = 1.0f;
        this.originDots.clear();
        this.originDots.add(dot);
        this.totalDistance = 0.0f;
        onTouchDown(dot.getX(), dot.getY());
        if (!isEraserPath()) {
            initLayerAndCanvas();
        }
        this.minTouchGap = minTouchGap;
    }

    public final List<Dot> updatePath(float x, float y, float z, long t, boolean isLast) {
        Dot dot = new Dot(x, y, z, t, 0.0f, null, 0.0f, 0, 0, 0.0f, 1008, null);
        this.originDots.add(dot);
        Dot dot2 = this.lastDot;
        if (dot2 == null) {
            this.lastDot = dot;
        } else if (Math.abs(dot2.getT() - dot.getT()) > 5 || isLast) {
            this.path.quadTo(dot2.getX(), dot2.getY(), (dot2.getX() + dot.getX()) / 2.0f, (dot2.getY() + dot.getY()) / 2.0f);
            return updatePathInner(dot, isLast, true, getPen().getLineEndSpeedLength() > 0.0f);
        }
        return CollectionsKt.emptyList();
    }

    public static /* synthetic */ List updatePathInner$default(DoodleBrushPath doodleBrushPath, Dot dot, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updatePathInner");
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        return doodleBrushPath.updatePathInner(dot, z, z2, z3);
    }

    protected final List<Dot> updatePathInner(Dot dot, boolean isLast, boolean needRefresh, boolean needScale) {
        int iCeil;
        int i;
        float fMax;
        float z;
        Intrinsics.checkNotNullParameter(dot, "dot");
        ArrayList arrayList = new ArrayList();
        this.mPathMeasure.setPath(this.path, false);
        float length = this.mPathMeasure.getLength();
        float f = length - this.totalDistance;
        long t = dot.getT();
        Dot dot2 = this.lastDot;
        long t2 = t - (dot2 != null ? dot2.getT() : 0L);
        this.totalDistance = length;
        float f2 = t2 == 0 ? 0.0f : ((f / t2) * 0.2f) + (this.lastVelocity * 0.8f);
        float f3 = 1.0f;
        float fExp = f2 == 0.0f ? 1.0f : (float) Math.exp(((double) (-f2)) * 0.3d);
        double d = f;
        if (d > this.minTouchGap && (iCeil = (int) Math.ceil(d / ((double) this.spacing))) > 0) {
            int i2 = 0;
            while (getXYVAtDistance(this.lastDrawDistance, this.tempXYV)) {
                float[] fArr = this.tempXYV;
                float f4 = fArr[0];
                float f5 = fArr[1];
                if (!needScale) {
                    i = i2;
                    fMax = 1.0f;
                    z = 1.0f;
                } else if (dot.getZ() > 0.0f) {
                    fMax = Math.max(dot.getZ(), 0.5f);
                    z = dot.getZ();
                    i = i2;
                } else {
                    float f6 = this.lastScale;
                    fMax = f6 + ((fExp - f6) * (i2 / iCeil));
                    i = i2 + 1;
                    z = 1.0f;
                }
                addSpot(f4, f5, fMax, z, arrayList, needRefresh);
                this.lastDrawDistance += this.spacing;
                i2 = i;
            }
        }
        if (this.lastDrawDistance == 0.0f && isLast) {
            addSpot(dot.getX(), dot.getY(), 1.0f, 1.0f, arrayList, needRefresh);
        } else {
            if (isEraserPath()) {
                addEraseSpot(dot.getX(), dot.getY(), 1.0f, 1.0f, arrayList, needRefresh);
            }
            f3 = fExp;
        }
        this.lastScale = f3;
        this.lastDot = dot;
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0032  */
    public void endPath(float x, float y, float z, long t) {
        DoodleBrushPath doodleBrushPath;
        Dot dot = this.startDot;
        Intrinsics.checkNotNull(dot);
        if (Math.abs(x - dot.getX()) <= 20.0f) {
            Dot dot2 = this.startDot;
            Intrinsics.checkNotNull(dot2);
            if (Math.abs(y - dot2.getY()) <= 20.0f) {
                doodleBrushPath = this;
                doodleBrushPath.updatePath(x, y, z, t, true);
            } else {
                doodleBrushPath = this;
            }
        } else {
            doodleBrushPath = this;
        }
        doodleBrushPath.willEnd = true;
        BuildersKt__Builders_commonKt.async$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, doodleBrushPath.new AnonymousClass1(null), 2, null);
        doodleBrushPath.resetBounds(doodleBrushPath.getBounds());
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.DoodleBrushPath$endPath$1, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleBrushPath.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleBrushPath$endPath$1", f = "DoodleBrushPath.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DoodleBrushPath.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws JSONException {
            Bitmap bitmapCopy;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DoodleBrushPath doodleBrushPath = DoodleBrushPath.this;
            Bitmap brushGlobalCacheBitmap = DoodleBrushPath.INSTANCE.getBrushGlobalCacheBitmap();
            if (brushGlobalCacheBitmap != null) {
                Bitmap.Config config = brushGlobalCacheBitmap.getConfig();
                Intrinsics.checkNotNull(config);
                bitmapCopy = brushGlobalCacheBitmap.copy(config, true);
            } else {
                bitmapCopy = null;
            }
            doodleBrushPath.setBitmap(bitmapCopy);
            DoodleCacheManager.INSTANCE.put(DoodleBrushPath.this.getId(), DoodleBrushPath.this.getBitmap());
            DoodleBrushPath.this.setBitmapCanvas(null);
            if (!DoodleBrushPath.this.isEraserPath()) {
                DoodleBrushPath.this.cacheJSONObject();
            }
            return Unit.INSTANCE;
        }
    }

    public final void trimEraserDots() {
        this.needTrimEraserDots = false;
        if (isEraserPath()) {
            return;
        }
        this.eraserDots.size();
        Set<Dot> set = this.eraserDots;
        final Function1 function1 = new Function1() { // from class: com.miui.doodle.feature.draw.DoodleBrushPath$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(DoodleBrushPath.trimEraserDots$lambda$24(this.f$0, (Dot) obj));
            }
        };
        set.removeIf(new Predicate() { // from class: com.miui.doodle.feature.draw.DoodleBrushPath$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DoodleBrushPath.trimEraserDots$lambda$25(function1, obj);
            }
        });
        this.eraserDots.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean trimEraserDots$lambda$25(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean trimEraserDots$lambda$24(DoodleBrushPath doodleBrushPath, Dot eraserDot) {
        Intrinsics.checkNotNullParameter(eraserDot, "eraserDot");
        Iterator<T> it = doodleBrushPath.dots.iterator();
        while (it.hasNext()) {
            if (UIUtils.INSTANCE.detectIntersect(((Dot) it.next()).getRect(), eraserDot.getRect())) {
                return false;
            }
        }
        return true;
    }

    private final void onTouchDown(float x, float y) {
        this.lastDrawDistance = 0.0f;
        moveToThread(x, y);
    }

    private final boolean getXYVAtDistance(float distance, float[] xyv) {
        float f = this.totalDistance;
        if (f == 0.0f || distance > f) {
            return false;
        }
        this.mPathMeasure.getPosTan(distance, this.tempPos, null);
        float[] fArr = this.tempPos;
        xyv[0] = fArr[0];
        xyv[1] = fArr[1];
        return true;
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase, com.miui.doodle.base.core.IDoodleSelectableItem
    public boolean contains(float x, float y) {
        Iterator<T> it = this.dots.iterator();
        while (it.hasNext()) {
            if (((Dot) it.next()).getRect().contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase, com.miui.doodle.base.core.IDoodleSelectableItem
    public boolean contains(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Iterator<T> it = this.dots.iterator();
        while (it.hasNext()) {
            if (UIUtils.INSTANCE.detectIntersect(((Dot) it.next()).getRect(), rect)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isEraserPath() {
        return getPen().isEraser();
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase
    protected void resetBounds(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        rect.set(0, 0, 0, 0);
        for (Dot dot : this.dots) {
            rect.union((int) dot.getRect().left, (int) dot.getRect().top, (int) dot.getRect().right, (int) dot.getRect().bottom);
        }
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase, com.miui.doodle.base.core.IDoodleSelectableItem
    /* JADX INFO: renamed from: isSelected, reason: from getter */
    public boolean getIsSelected() {
        return this.isSelected;
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase, com.miui.doodle.base.core.IDoodleSelectableItem
    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public final void resetBounds(float offsetX, float offsetY) {
        for (Dot dot : this.dots) {
            dot.getRect().offset(offsetX, offsetY);
            dot.setX(dot.getX() + offsetX);
            dot.setY(dot.getY() + offsetY);
        }
        resetBounds();
    }

    public final void resetBounds() {
        resetBounds(getBounds());
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public DoodleBrushPath copy() {
        DoodleBrushPath doodleBrushPath = new DoodleBrushPath(getDoodle(), getPen().copy(), getId(), getItemRotate(), 0.0f, getLocation(), getPivotPoint(), 0, 144, null);
        doodleBrushPath.setAlpha(getAlpha());
        doodleBrushPath.getBounds().set(getBounds());
        doodleBrushPath.dots.addAll(this.dots);
        doodleBrushPath.originDots.addAll(this.originDots);
        doodleBrushPath.eraserDots.addAll(this.eraserDots);
        return doodleBrushPath;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cacheJSONObject() throws JSONException {
        JSONObject jSONObject = super.toJSONObject();
        JSONArray jSONArray = new JSONArray();
        jSONObject.put("alpha", getAlpha());
        Iterator it = CollectionsKt.toList(this.dots).iterator();
        while (it.hasNext()) {
            jSONArray.put(((Dot) it.next()).toJSONObject());
        }
        jSONObject.put(KEY_DOTS, jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        Iterator it2 = CollectionsKt.toList(this.originDots).iterator();
        while (it2.hasNext()) {
            jSONArray2.put(((Dot) it2.next()).toJSONObject());
        }
        jSONObject.put(KEY_ORIGINAL_DOTS, jSONArray2);
        JSONArray jSONArray3 = new JSONArray();
        Logger.INSTANCE.d(TAG, "cacheJSONObject eraserDots size " + this.eraserDots.size() + StringUtils.SPACE + getId() + StringUtils.SPACE + getPen());
        Iterator it3 = CollectionsKt.toList(this.eraserDots).iterator();
        while (it3.hasNext()) {
            jSONArray3.put(((Dot) it3.next()).toJSONObject());
        }
        jSONObject.put(KEY_ERASER_DOTS, jSONArray3);
        jSONObject.put("type", 0);
        this.cachedJSONObject = jSONObject;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() throws JSONException {
        if (this.cachedJSONObject == null) {
            cacheJSONObject();
        }
        JSONObject jSONObject = this.cachedJSONObject;
        Intrinsics.checkNotNull(jSONObject);
        return jSONObject;
    }

    public String toString() {
        return super.toString() + "_type:" + getPen().getType();
    }

    public final void cacheImageBeforeChanged() {
        Bitmap bitmap = this.cacheBitmapBeforeChanged;
        if (bitmap != null) {
            Boolean boolValueOf = bitmap != null ? Boolean.valueOf(bitmap.isRecycled()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (!boolValueOf.booleanValue()) {
                return;
            }
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            Bitmap.Config config = bitmap2.getConfig();
            Intrinsics.checkNotNull(config);
            this.cacheBitmapBeforeChanged = bitmap2.copy(config, true);
        }
    }

    public final void cacheImageBeforeChangedToFile() {
        this.cacheImageKeyBeforeChanged = DoodleCacheManager.INSTANCE.saveImageToFile(this.cacheBitmapBeforeChanged, DoodleCacheManager.HIS_CACHE_HEAD);
    }

    public final void cacheImageAfterChanged() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            Bitmap.Config config = bitmap.getConfig();
            Intrinsics.checkNotNull(config);
            this.cacheBitmapAfterChanged = bitmap.copy(config, true);
        }
    }

    public final void cacheImageAfterChangedToFile() {
        this.cacheImageKeyAfterChanged = DoodleCacheManager.INSTANCE.saveImageToFile(this.cacheBitmapAfterChanged, DoodleCacheManager.HIS_CACHE_HEAD);
    }

    public final void releaseCacheImageBeforeAndAfterChanged() {
        this.cacheBitmapBeforeChanged = null;
        this.cacheImageKeyBeforeChanged = "";
        this.cacheBitmapAfterChanged = null;
        this.cacheImageKeyAfterChanged = "";
    }

    /* JADX INFO: compiled from: DoodleBrushPath.kt */
    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\n2\u0006\u0010$\u001a\u00020%J.\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0007J\u0006\u0010/\u001a\u000200J\u0090\u0001\u00101\u001a\u0002002\u0006\u00102\u001a\u00020\u00052\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00072\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u00072\u0006\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u00072\u0006\u0010;\u001a\u00020\u000e2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020%0=2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020%0=2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020%0=2\u0006\u0010@\u001a\u00020'2\u0006\u0010A\u001a\u00020\u000e2\u0006\u0010B\u001a\u00020\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u001f\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleBrushPath$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "TwoPi", "", "sColoredTextureCache", "Ljava/util/HashMap;", "Landroid/graphics/Bitmap;", "getSColoredTextureCache", "()Ljava/util/HashMap;", "CACHED_SIZE", "", "KEY_DOTS", "KEY_ORIGINAL_DOTS", "KEY_ERASER_DOTS", "KEY_ALPHA", "dotTempPaint", "Landroid/graphics/Paint;", "normalPaint", "getNormalPaint", "()Landroid/graphics/Paint;", "clearPaint", "getClearPaint", "srcPaint", "dstInPaint", "brushGlobalCacheBitmap", "getBrushGlobalCacheBitmap", "()Landroid/graphics/Bitmap;", "setBrushGlobalCacheBitmap", "(Landroid/graphics/Bitmap;)V", "globalCacheCanvas", "Landroid/graphics/Canvas;", "getTextureByDot", "dot", "Lcom/miui/doodle/document/Dot;", "toPath", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, Constants.TIMESTAMP, "", "minTouchGap", "clearCache", "", "createBrushItem", "id", "hasAdded", "", "locationX", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", "alpha", DoodleBrushPath.KEY_DOTS, "", "originDots", "eraserDots", CloudPushConstants.XML_ITEM, Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HashMap<String, Bitmap> getSColoredTextureCache() {
            return DoodleBrushPath.sColoredTextureCache;
        }

        public final Paint getNormalPaint() {
            return DoodleBrushPath.normalPaint;
        }

        public final Paint getClearPaint() {
            return DoodleBrushPath.clearPaint;
        }

        public final Bitmap getBrushGlobalCacheBitmap() {
            return DoodleBrushPath.brushGlobalCacheBitmap;
        }

        public final void setBrushGlobalCacheBitmap(Bitmap bitmap) {
            DoodleBrushPath.brushGlobalCacheBitmap = bitmap;
        }

        public final Bitmap getTextureByDot(Dot dot) {
            Intrinsics.checkNotNullParameter(dot, "dot");
            String key = dot.getKey();
            Bitmap bitmap = getSColoredTextureCache().get(key);
            if (bitmap != null) {
                return bitmap;
            }
            Logger.INSTANCE.d(DoodleBrushPath.TAG, "drawDot: create texture cache. Key: " + key);
            int iCoerceAtLeast = RangesKt.coerceAtLeast((int) dot.getSize(), 1);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iCoerceAtLeast, iCoerceAtLeast, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            Rect rect = new Rect(0, 0, iCoerceAtLeast, iCoerceAtLeast);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmapCreateBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            canvas.drawColor(dot.getColor(), PorterDuff.Mode.SRC);
            Bitmap texture = dot.getTexture();
            if (texture != null) {
                canvas.drawBitmap(texture, (Rect) null, rect, DoodleBrushPath.dstInPaint);
            }
            getSColoredTextureCache().put(key, bitmapCreateBitmap);
            return bitmapCreateBitmap;
        }

        public final DoodleBrushPath toPath(IDoodle doodle, float x, float y, long t, float minTouchGap) {
            Intrinsics.checkNotNullParameter(doodle, "doodle");
            DoodleBrushPath doodleBrushPath = new DoodleBrushPath(doodle, doodle.getPen().copy(), null, 0.0f, 0.0f, null, null, 0, R2.attr.actionBarEditIcon, null);
            doodleBrushPath.startPath(new Dot(x, y, 0.0f, t, doodleBrushPath.isEraserPath() ? doodleBrushPath.getPathWidth() : 0.0f, null, 0.0f, 0, 0, 0.0f, R2.attr.eclipseOffsetY, null), minTouchGap);
            return doodleBrushPath;
        }

        public final void clearCache() {
            getSColoredTextureCache().clear();
            Bitmap brushGlobalCacheBitmap = getBrushGlobalCacheBitmap();
            if (brushGlobalCacheBitmap != null) {
                brushGlobalCacheBitmap.recycle();
            }
            setBrushGlobalCacheBitmap(null);
            DoodleBrushPath.globalCacheCanvas = null;
        }

        public final void createBrushItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, int alpha, List<Dot> dots, List<Dot> originDots, List<Dot> eraserDots, DoodleBrushPath item, int width, int height) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(dots, "dots");
            Intrinsics.checkNotNullParameter(originDots, "originDots");
            Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
            Intrinsics.checkNotNullParameter(item, "item");
            DoodleRotatableItemBase.INSTANCE.createBaseItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, item);
            item.getDots().clear();
            item.getOriginDots().clear();
            item.getEraserDots().clear();
            item.setAlpha(alpha);
            item.getDots().addAll(dots);
            item.getOriginDots().addAll(originDots);
            item.getEraserDots().addAll(eraserDots);
            item.resetBounds();
        }
    }

    static {
        Paint paint = new Paint(3);
        dotTempPaint = paint;
        Paint paint2 = new Paint(3);
        normalPaint = paint2;
        Paint paint3 = new Paint(3);
        clearPaint = paint3;
        Paint paint4 = new Paint(2);
        srcPaint = paint4;
        Paint paint5 = new Paint(2);
        dstInPaint = paint5;
        paint.setTypeface(null);
        paint.setAntiAlias(true);
        paint2.setTypeface(null);
        paint2.setAntiAlias(true);
        paint4.setTypeface(null);
        paint4.setAntiAlias(true);
        paint5.setTypeface(null);
        paint5.setAntiAlias(true);
        paint3.setTypeface(null);
        paint3.setAntiAlias(true);
        paint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }
}
