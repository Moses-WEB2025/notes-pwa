package com.miui.notes.ai.floatingwindow;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.BundleKt;
import androidx.core.view.GravityCompat;
import ch.qos.logback.core.CoreConstants;
import com.airbnb.lottie.LottieAnimationView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.aiengine.common.result.SSEConst;
import com.miui.doodle.document.Layer;
import com.miui.notes.ai.Action;
import com.miui.notes.ai.GlobalAiTextWidgetService;
import com.miui.notes.ai.GlobalAiTextWidgetServiceHelper;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.editor.GlobalAiEditor;
import com.miui.notes.ai.editor.IWebViewListener;
import com.miui.notes.ai.network.GlobalAiTextRequestHelper;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.network.NetworkError;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.ai.ui.AiToast;
import com.miui.notes.ai.ui.CustomAiTextWidgetEditText;
import com.miui.notes.ai.ui.FloatingDecorView;
import com.miui.notes.ai.ui.GlobalAiPanelPopupWindow;
import com.miui.notes.ai.ui.PopupRvAdapterListener;
import com.miui.notes.ai.ui.RenderViewLayout;
import com.miui.notes.ai.ui.shader.AiShaderEffectRenderer;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.ForegroundInfoUtils;
import com.miui.notes.ai.utils.KeyboardListenerHelper;
import com.miui.notes.ai.utils.NoteSchemaUtil;
import com.miui.notes.ai.utils.PackageNameUtils;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.ReflectUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.SoftInputUtil;
import com.miui.notes.ai.utils.StringUtils;
import com.miui.notes.ai.utils.SystemNavigationKeyClickListener;
import com.miui.notes.ai.utils.TextExtKt;
import com.miui.notes.ai.utils.UIUtils;
import com.miui.notes.ai.utils.WindowTouchRegionHelper;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import miui.process.ForegroundInfo;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: compiled from: GlobalAiTextWidgetWindow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b9\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\b\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f*\u0004ï\u0001ò\u0001\u0018\u0000 \u0097\u00022\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0002\u0097\u0002B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ>\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020\u000e2\u0007\u0010\u0092\u0001\u001a\u00020\u000e2\u0007\u0010\u0093\u0001\u001a\u00020\u000e2\u0007\u0010\u0094\u0001\u001a\u00020b2\u0007\u0010\u0095\u0001\u001a\u00020b2\u0007\u0010\u0096\u0001\u001a\u00020bJ5\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020\u000e2\u0007\u0010\u0092\u0001\u001a\u00020\u000e2\u0007\u0010\u0093\u0001\u001a\u00020\u000e2\u0007\u0010\u0097\u0001\u001a\u00020b2\u0007\u0010\u0098\u0001\u001a\u00020bJ\n\u0010\u0099\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010\u009a\u0001\u001a\u00030\u0090\u0001H\u0002J\u001c\u0010\u009b\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u009c\u0001\u001a\u0002002\u0007\u0010\u009d\u0001\u001a\u000200H\u0002J\u0013\u0010\u009e\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u009f\u0001\u001a\u00020bH\u0002J\n\u0010 \u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010¡\u0001\u001a\u00030\u0090\u0001H\u0002J\u0014\u0010¢\u0001\u001a\u00030\u0090\u00012\b\u0010J\u001a\u0004\u0018\u00010\u001dH\u0002J\b\u0010£\u0001\u001a\u00030\u0090\u0001J\u0014\u0010¤\u0001\u001a\u00030\u0090\u00012\b\u0010_\u001a\u0004\u0018\u00010\u000eH\u0002J\u0014\u0010¥\u0001\u001a\u00030\u0090\u00012\b\u0010_\u001a\u0004\u0018\u00010\u000eH\u0002J\u0013\u0010¦\u0001\u001a\u00030\u0090\u00012\u0007\u0010§\u0001\u001a\u00020\u001bH\u0002J\u001d\u0010¨\u0001\u001a\u00030\u0090\u00012\u0007\u0010§\u0001\u001a\u00020\u001b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\u001e\u0010©\u0001\u001a\u00030\u0090\u00012\u0007\u0010ª\u0001\u001a\u0002082\t\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u000eH\u0002J\u001b\u0010«\u0001\u001a\u00020b2\u0007\u0010ª\u0001\u001a\u0002082\u0007\u0010¬\u0001\u001a\u00020bH\u0002J$\u0010«\u0001\u001a\u00020b2\u0007\u0010ª\u0001\u001a\u0002082\u0007\u0010\u00ad\u0001\u001a\u00020\u000e2\u0007\u0010¬\u0001\u001a\u00020bH\u0002J4\u0010®\u0001\u001a\u00030\u0090\u00012\t\u0010¯\u0001\u001a\u0004\u0018\u00010\u001d2\t\u0010°\u0001\u001a\u0004\u0018\u0001082\t\u0010±\u0001\u001a\u0004\u0018\u00010:2\u0007\u0010²\u0001\u001a\u00020\u000eH\u0002J$\u0010³\u0001\u001a\u00020b2\u0007\u0010´\u0001\u001a\u00020b2\u0007\u0010µ\u0001\u001a\u00020b2\u0007\u0010¶\u0001\u001a\u00020bH\u0002J&\u0010·\u0001\u001a\u00030\u0090\u00012\u0007\u0010¸\u0001\u001a\u0002002\u000b\b\u0002\u0010¹\u0001\u001a\u0004\u0018\u00010bH\u0002¢\u0006\u0003\u0010º\u0001J\u0013\u0010»\u0001\u001a\u00030\u0090\u00012\u0007\u0010¼\u0001\u001a\u000200H\u0002J\u0013\u0010½\u0001\u001a\u00030\u0090\u00012\u0007\u0010¹\u0001\u001a\u00020bH\u0002J\u0013\u0010¾\u0001\u001a\u00030\u0090\u00012\u0007\u0010¿\u0001\u001a\u000200H\u0002J \u0010À\u0001\u001a\u00030\u0090\u00012\t\u0010Á\u0001\u001a\u0004\u0018\u00010\u001d2\t\u0010Â\u0001\u001a\u0004\u0018\u00010\u001dH\u0002J\u0015\u0010Ã\u0001\u001a\u00030\u0090\u00012\t\u0010Á\u0001\u001a\u0004\u0018\u00010\u001dH\u0002J\u001c\u0010Ä\u0001\u001a\u00030\u0090\u00012\u0007\u0010Á\u0001\u001a\u00020\u001d2\u0007\u0010Å\u0001\u001a\u00020IH\u0002J\u0013\u0010Æ\u0001\u001a\u00030\u0090\u00012\u0007\u0010Á\u0001\u001a\u00020\u001dH\u0002J\n\u0010Ç\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010È\u0001\u001a\u00030\u0090\u0001H\u0002J\u001f\u0010É\u0001\u001a\u00030Ê\u00012\t\u0010Ë\u0001\u001a\u0004\u0018\u00010\u001d2\b\u0010Ì\u0001\u001a\u00030Ê\u0001H\u0002J\n\u0010Í\u0001\u001a\u00030\u0090\u0001H\u0003J\n\u0010Î\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010Ï\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010Ð\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010Ñ\u0001\u001a\u00030\u0090\u0001H\u0002J\t\u0010Ò\u0001\u001a\u000200H\u0002J\u0013\u0010Ó\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u009c\u0001\u001a\u000200H\u0002J7\u0010Ó\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0094\u0001\u001a\u00020b2\u0007\u0010\u0096\u0001\u001a\u00020b2\u0007\u0010\u0095\u0001\u001a\u00020b2\u0007\u0010Ô\u0001\u001a\u00020\u000e2\u0007\u0010\u009c\u0001\u001a\u000200H\u0002J.\u0010Ó\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0097\u0001\u001a\u00020b2\u0007\u0010\u0095\u0001\u001a\u00020b2\u0007\u0010Ô\u0001\u001a\u00020\u000e2\u0007\u0010\u009c\u0001\u001a\u000200H\u0002J\n\u0010Õ\u0001\u001a\u00030\u0090\u0001H\u0002J\u0012\u0010Ö\u0001\u001a\u00030\u0090\u00012\u0006\u0010m\u001a\u00020\u000eH\u0002J\u0013\u0010×\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u009c\u0001\u001a\u000200H\u0002J\u0013\u0010Ø\u0001\u001a\u00030\u0090\u00012\u0007\u0010Ù\u0001\u001a\u00020\u000eH\u0002J\n\u0010Ú\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010Û\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010Ü\u0001\u001a\u00030\u0090\u0001H\u0002J\t\u0010Ý\u0001\u001a\u00020\u000eH\u0002J\t\u0010Þ\u0001\u001a\u00020\u000eH\u0002J\u001e\u0010ß\u0001\u001a\u00030\u0090\u00012\u0007\u0010à\u0001\u001a\u00020\u000e2\t\b\u0002\u0010á\u0001\u001a\u000200H\u0002J\n\u0010â\u0001\u001a\u00030\u0090\u0001H\u0002J\u001c\u0010ã\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0095\u0001\u001a\u00020b2\u0007\u0010ä\u0001\u001a\u000200H\u0002J\u0012\u0010å\u0001\u001a\u00030\u0090\u00012\b\u0010x\u001a\u0004\u0018\u00010yJ\b\u0010æ\u0001\u001a\u00030\u0090\u0001J\n\u0010ç\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010è\u0001\u001a\u00030\u0090\u0001H\u0002J\u0015\u0010é\u0001\u001a\u00030\u0090\u00012\t\u0010ê\u0001\u001a\u0004\u0018\u00010\u001dH\u0016J\u001b\u0010ë\u0001\u001a\u00030\u0090\u00012\t\u0010ì\u0001\u001a\u0004\u0018\u00010bH\u0002¢\u0006\u0003\u0010í\u0001J\u0016\u0010ô\u0001\u001a\u00030\u0090\u00012\n\u0010õ\u0001\u001a\u0005\u0018\u00010ö\u0001H\u0016J\u0016\u0010÷\u0001\u001a\u00030\u0090\u00012\n\u0010ø\u0001\u001a\u0005\u0018\u00010ù\u0001H\u0016J\n\u0010ú\u0001\u001a\u00030\u0090\u0001H\u0016J\u001d\u0010û\u0001\u001a\u00030\u0090\u00012\u0011\u0010ü\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0018\u00010\rH\u0016J\n\u0010ý\u0001\u001a\u00030\u0090\u0001H\u0016J\u0013\u0010þ\u0001\u001a\u00030\u0090\u00012\u0007\u0010ÿ\u0001\u001a\u00020bH\u0016J\n\u0010\u0080\u0002\u001a\u00030\u0090\u0001H\u0016J\u0015\u0010\u0081\u0002\u001a\u00030\u0090\u00012\t\u0010ü\u0001\u001a\u0004\u0018\u00010\u000eH\u0016J\n\u0010\u0082\u0002\u001a\u00030\u0090\u0001H\u0016J\n\u0010\u0083\u0002\u001a\u00030\u0090\u0001H\u0016J\t\u0010\u0084\u0002\u001a\u000200H\u0016J\t\u0010\u0085\u0002\u001a\u000200H\u0016J\u0016\u0010\u0086\u0002\u001a\u0004\u0018\u00010\u000e2\t\u0010\u0087\u0002\u001a\u0004\u0018\u00010\u000eH\u0016J\u001f\u0010\u0088\u0002\u001a\u0004\u0018\u00010\u000e2\u0007\u0010\u0089\u0002\u001a\u00020b2\t\u0010\u0087\u0002\u001a\u0004\u0018\u00010\u000eH\u0016J\u0013\u0010\u008a\u0002\u001a\u00030\u0090\u00012\u0007\u0010\u009f\u0001\u001a\u00020bH\u0016J\n\u0010\u008b\u0002\u001a\u00030\u0090\u0001H\u0016J\n\u0010\u008c\u0002\u001a\u00030\u0090\u0001H\u0016J\u001c\u0010\u008d\u0002\u001a\u00030\u0090\u00012\u0007\u0010\u008e\u0002\u001a\u00020b2\u0007\u0010\u008f\u0002\u001a\u000200H\u0016J\u001c\u0010\u0090\u0002\u001a\u00030\u0090\u00012\u0007\u0010\u008e\u0002\u001a\u00020b2\u0007\u0010\u008f\u0002\u001a\u000200H\u0016J\u0015\u0010\u0091\u0002\u001a\u00030\u0090\u00012\t\u0010\u00ad\u0001\u001a\u0004\u0018\u00010\u000eH\u0002J\n\u0010\u0092\u0002\u001a\u00030\u0090\u0001H\u0002J\u0013\u0010\u0093\u0002\u001a\u00030\u0090\u00012\u0007\u0010\u0094\u0002\u001a\u000200H\u0016J\u0014\u0010\u0095\u0002\u001a\u00030\u0090\u00012\b\u0010J\u001a\u0004\u0018\u00010\u001dH\u0002J\n\u0010\u0096\u0002\u001a\u00030\u0090\u0001H\u0016R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010<\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010>\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010@\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010C\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010H\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010J\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010K\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010L\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010N\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010O\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010P\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Q\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010R\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010S\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020]X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010^\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010_\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010`\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010n\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020pX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020pX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020pX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010t\u001a\u001e\u0012\u0004\u0012\u00020b\u0012\u0004\u0012\u00020b0uj\u000e\u0012\u0004\u0012\u00020b\u0012\u0004\u0012\u00020b`vX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010w\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010x\u001a\u0004\u0018\u00010yX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010z\u001a\u0004\u0018\u00010{X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010|\u001a\u0004\u0018\u00010}X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010~\u001a\u0004\u0018\u00010\u007fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0080\u0001\u001a\u00030\u0081\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0085\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0086\u0001\u001a\u00020bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0087\u0001\u001a\u00030\u0088\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0089\u0001\u001a\u00030\u008a\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u008b\u0001\u001a\u00030\u008c\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u008d\u0001\u001a\u00030\u008e\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010î\u0001\u001a\u00030ï\u0001X\u0082\u000e¢\u0006\u0005\n\u0003\u0010ð\u0001R\u0013\u0010ñ\u0001\u001a\u00030ò\u0001X\u0082\u000e¢\u0006\u0005\n\u0003\u0010ó\u0001¨\u0006\u0098\u0002"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow;", "Landroid/view/View$OnClickListener;", "Lcom/miui/notes/ai/utils/KeyboardListenerHelper$IKeyBoardChangedListener;", "Lcom/miui/notes/ai/data/DataListener;", "Lcom/miui/notes/ai/ui/FloatingDecorView$OnScreenChangeListener;", "Lcom/miui/notes/ai/editor/IWebViewListener;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "globalAiTextWidgetService", "Lcom/miui/notes/ai/GlobalAiTextWidgetService;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/notes/ai/GlobalAiTextWidgetService;)V", "mStyleTags", "", "", "mEmojiTags", "mWordsNumTags", "mReviewsStyleTags", "mReviewsWordsNumTags", "mContext", "mAiTextWidgetService", "mWindowManager", "Landroid/view/WindowManager;", "mParams", "Landroid/view/WindowManager$LayoutParams;", "mLoadingText", "mRootView", "Lcom/miui/notes/ai/ui/FloatingDecorView;", "mFloatingView", "Landroid/view/View;", "mWindowTouchRegionHelper", "Lcom/miui/notes/ai/utils/WindowTouchRegionHelper;", "mBgGradientColorView", "mBgGradientColorViewContainer", "Lcom/miui/notes/ai/ui/RenderViewLayout;", "mFloatingContentView", "mFloatingRimEffectView", "mPanelCloseBtn", "mPanelWebView", "Landroid/webkit/WebView;", "mPanelAiEditor", "Lcom/miui/notes/ai/editor/GlobalAiEditor;", "mAiEndTip", "mFeedbackLayout", "mFeedbackLike", "Lcom/airbnb/lottie/LottieAnimationView;", "mFeedbackDisslike", "isLikeSelected", "", "isDisLikeSelected", "mEmptyView", "mPanelDividerLine", "mPanelTagListLayout", "Landroidx/constraintlayout/widget/ConstraintLayout;", "mPanelTagStyleContainer", "mPanelTagStyle", "Landroid/widget/TextView;", "mPanelTagStylePopup", "Lcom/miui/notes/ai/ui/GlobalAiPanelPopupWindow;", "mPanelTagWordNumContainer", "mPanelTagWordNum", "mPanelTagWordNumPopup", "mPanelTagEmoticonNumContainer", "mPanelTagEmoticonNum", "mPanelTagEmoticonNumPopup", "mPanelTagCommentContainer", "mPanelTagComment", "mPanelTagCommentPopup", "mPanelTagCommentNumContainer", "mPanelTagCommentNum", "mPanelTagCommentNumPopup", "mPanelShowInputLayout", "mPanelShowInputEditText", "Landroid/widget/EditText;", "mPanelEditInputLayout", "mPanelEditInputContentView", "mPanelEditInputRimEffectView", "mPanelEditInputEditText", "mEnableBtn", "mEnableBtnLayout", "mDisableBtnLayout", "mPanelStopGenerateBtn", "mPanelRegenerateBtn", "mPanelPasteBtn", "mDuringRemoveView", "mIsAiEditorPrepared", "mIsFirstShowEditInput", "mIsFirstStart", "mFirst", "isConfigChanged", "mCloudIsDevice", "screenReceiverRegistered", "mLastEditInputHeight", "", "mQueryID", "mCurrentPanelType", "mPackageName", "mStyle", "", "mEmoticonsNum", "mWordsNum", "mComment", "mCommentWordsNum", "mUserPrompt", "mTitle", "mContent", "mStreamContent", "aiSessionId", "resultStatus", "aiEndWay", "mAiTraceId", "mStartRequestTime", "", "endRequestTime", "mAiOpenTime", "maxTagLines", "textLinesMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "mAiSavedData", "mWindowCloseListener", "Lcom/miui/notes/ai/floatingwindow/AiFloatingWindow$WindowCloseListener;", "mGlobalAiTextRequestHelper", "Lcom/miui/notes/ai/network/GlobalAiTextRequestHelper;", "mKeyboardListenerHelper", "Lcom/miui/notes/ai/utils/KeyboardListenerHelper;", "mInputGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "mHandler", "Landroid/os/Handler;", "mAppObserver", "Lcom/miui/notes/ai/floatingwindow/AiFloatingWindow$ForegroundWindowObserver;", "mAiToast", "Lcom/miui/notes/ai/ui/AiToast;", "mImeHeight", "mGlobalAiTextAnimHelper", "Lcom/miui/notes/ai/floatingwindow/GlobalAiTextAnimHelper;", "mGlobalAiStateManager", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager;", "recyclingWindowAction", "Lcom/miui/notes/ai/Action;", "mScreenStatusReceiver", "Landroid/content/BroadcastReceiver;", "showAiFloatingWindow", "", GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, "packageName", "panelType", "style", "wordNum", "emoticonsNum", "comment", "commentWordsNum", "initAndShowFloatingWindow", "setStateCallback", "setLoadingPanelCallbackImpl", "isFirstStart", "isAiEditorPrepared", "showInputPanelCallbackImpl", "imeHeight", "inputPanelAnimListenerImpl", "showCompletePanelFromInputImpl", "completePanelFromInputListenerImpl", "initView", "initCopyPublishPopupWindow", "initProductCommentPopupWindow", "findViewsInit", "rootView", "initListener", "fixTagsLines", "textView", "getTextLines", "lineWidth", "text", "initTagView", "panelTagContainer", "panelTagContent", "panelTagPopupWindow", "showContent", "getTagIndex", "currentValue", "tagListSize", "startOffset", "setCustomSettingState", "canChange", "resCode", "(ZLjava/lang/Integer;)V", "canPasteToPage", "canPaste", "handleResCode", "isEditInputEndBtnActive", "isActive", "positionEditeInput", "panelEditInputLayout", "targetView", "resetEditInput", "addEditInputLayoutObserver", "panelEditInputEditText", "removeEditInputLayoutObserver", "addToWindow", "setTouchableRegion", "getRegionFromView", "Landroid/graphics/Rect;", "view", Constants.RANDOM_LONG, "handleTouchEvent", "handleImeChanged", "registerBackEventReceiver", "registerNavigationKeyEventReceiver", "unregisterNavigationKeyEventReceiver", "registerScreenStatusReceiver", "requestAiData", "userPrompt", "trackReportAiOpen", "trackReportAiClose", "trackReportAiStart", "trackReportAiClick", "feedbackType", "trackReportAiEnd", "trackReportAiUsed", "trackReportAiCanceled", "getAiTraceType", "getContentStatus", "stopGenerating", "endTip", "needChangePanelState", "pasteToPage", "pasteContentToPage", "isAddEndtip", "setWindowCloseListener", "dismissWindow", "noticeWindowClosed", "detachFromWindow", "onClick", "v", "onClickEvent", "id", "(Ljava/lang/Integer;)V", "likeListener", "com/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow$likeListener$1", "Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow$likeListener$1;", "dislikeListener", "com/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow$dislikeListener$1", "Lcom/miui/notes/ai/floatingwindow/GlobalAiTextWidgetWindow$dislikeListener$1;", "listenString", "result", "Lcom/miui/notes/ai/data/output/AiOutputData;", "netError", "networkError", "Lcom/miui/notes/ai/network/NetworkError;", "finishListen", "listenRecommendData", "data", "onPagePrepared", "onCorrectNumChanged", "changeCount", "onFlowDone", "onSaveData", "notifySetNewContent", "notifyRefreshComplete", "panelNeedTaller", "isNightMode", "getStringFromResource", "key", "getNumberAndStringFromResource", "number", "onShowKeyboard", "onHideKeyboard", "onPrepare", "onKeyBoardChanged", Layer.KEY_HEIGHT, "visible", "onKeyBoardChangedEnd", "showToast", "releaseToast", "setOnScreenChange", "isLandScape", "resetIncludeViewMargin", "onConfigChange", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextWidgetWindow implements View.OnClickListener, KeyboardListenerHelper.IKeyBoardChangedListener, DataListener, FloatingDecorView.OnScreenChangeListener, IWebViewListener {
    public static final String FEEDBACK_NEGATIVE = "negative";
    public static final String FEEDBACK_POSITIVE = "positive";
    public static final float INPUT_TEXT_MAX_HEIGHT = 104.0f;
    public static final int PRODUCT_COMMENT_WORDS_NUM_START = 10;
    public static final String TAG = "GlobalAiFloatingWindow";
    public static final int TEXT_COUNT = 200;
    public static final String TYPE_COPY_PUBLISH = "copy_publish";
    public static final String TYPE_PRODUCT_COMMENT = "product_comment";
    private String aiEndWay;
    private String aiSessionId;
    private GlobalAiTextWidgetWindow$dislikeListener$1 dislikeListener;
    private long endRequestTime;
    private boolean isConfigChanged;
    private boolean isDisLikeSelected;
    private boolean isLikeSelected;
    private GlobalAiTextWidgetWindow$likeListener$1 likeListener;
    private String mAiEndTip;
    private long mAiOpenTime;
    private String mAiSavedData;
    private GlobalAiTextWidgetService mAiTextWidgetService;
    private AiToast mAiToast;
    private String mAiTraceId;
    private AiFloatingWindow.ForegroundWindowObserver mAppObserver;
    private View mBgGradientColorView;
    private RenderViewLayout mBgGradientColorViewContainer;
    private boolean mCloudIsDevice;
    private int mComment;
    private int mCommentWordsNum;
    private String mContent;
    private final Context mContext;
    private String mCurrentPanelType;
    private View mDisableBtnLayout;
    private boolean mDuringRemoveView;
    private final List<String> mEmojiTags;
    private int mEmoticonsNum;
    private View mEmptyView;
    private TextView mEnableBtn;
    private View mEnableBtnLayout;
    private LottieAnimationView mFeedbackDisslike;
    private View mFeedbackLayout;
    private LottieAnimationView mFeedbackLike;
    private boolean mFirst;
    private View mFloatingContentView;
    private View mFloatingRimEffectView;
    private View mFloatingView;
    private final GlobalAiStateManager mGlobalAiStateManager;
    private final GlobalAiTextAnimHelper mGlobalAiTextAnimHelper;
    private GlobalAiTextRequestHelper mGlobalAiTextRequestHelper;
    private Handler mHandler;
    private int mImeHeight;
    private ViewTreeObserver.OnGlobalLayoutListener mInputGlobalLayoutListener;
    private boolean mIsAiEditorPrepared;
    private boolean mIsFirstShowEditInput;
    private boolean mIsFirstStart;
    private KeyboardListenerHelper mKeyboardListenerHelper;
    private float mLastEditInputHeight;
    private String mLoadingText;
    private String mPackageName;
    private GlobalAiEditor mPanelAiEditor;
    private View mPanelCloseBtn;
    private View mPanelDividerLine;
    private View mPanelEditInputContentView;
    private EditText mPanelEditInputEditText;
    private View mPanelEditInputLayout;
    private View mPanelEditInputRimEffectView;
    private TextView mPanelPasteBtn;
    private TextView mPanelRegenerateBtn;
    private EditText mPanelShowInputEditText;
    private View mPanelShowInputLayout;
    private TextView mPanelStopGenerateBtn;
    private TextView mPanelTagComment;
    private View mPanelTagCommentContainer;
    private TextView mPanelTagCommentNum;
    private View mPanelTagCommentNumContainer;
    private GlobalAiPanelPopupWindow mPanelTagCommentNumPopup;
    private GlobalAiPanelPopupWindow mPanelTagCommentPopup;
    private TextView mPanelTagEmoticonNum;
    private View mPanelTagEmoticonNumContainer;
    private GlobalAiPanelPopupWindow mPanelTagEmoticonNumPopup;
    private ConstraintLayout mPanelTagListLayout;
    private TextView mPanelTagStyle;
    private View mPanelTagStyleContainer;
    private GlobalAiPanelPopupWindow mPanelTagStylePopup;
    private TextView mPanelTagWordNum;
    private View mPanelTagWordNumContainer;
    private GlobalAiPanelPopupWindow mPanelTagWordNumPopup;
    private WebView mPanelWebView;
    private WindowManager.LayoutParams mParams;
    private String mQueryID;
    private final List<String> mReviewsStyleTags;
    private final List<String> mReviewsWordsNumTags;
    private FloatingDecorView mRootView;
    private final BroadcastReceiver mScreenStatusReceiver;
    private long mStartRequestTime;
    private String mStreamContent;
    private int mStyle;
    private final List<String> mStyleTags;
    private String mTitle;
    private String mUserPrompt;
    private AiFloatingWindow.WindowCloseListener mWindowCloseListener;
    private WindowManager mWindowManager;
    private WindowTouchRegionHelper mWindowTouchRegionHelper;
    private int mWordsNum;
    private final List<String> mWordsNumTags;
    private int maxTagLines;
    private final Action recyclingWindowAction;
    private String resultStatus;
    private boolean screenReceiverRegistered;
    private HashMap<Integer, Integer> textLinesMap;

    private final int getTagIndex(int currentValue, int tagListSize, int startOffset) {
        int i = currentValue - startOffset;
        if (i < 0 || i >= tagListSize) {
            return 0;
        }
        return i;
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void listenRecommendData(List<String> data) {
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onCorrectNumChanged(int changeCount) {
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onKeyBoardChanged(int height, boolean visible) {
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onKeyBoardChangedEnd(int height, boolean visible) {
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onPrepare() {
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean panelNeedTaller() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r10v7, types: [com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$likeListener$1] */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$dislikeListener$1] */
    public GlobalAiTextWidgetWindow(Context context, GlobalAiTextWidgetService globalAiTextWidgetService) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(globalAiTextWidgetService, "globalAiTextWidgetService");
        String string = context.getString(R.string.ai_tag_all);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String string2 = context.getString(R.string.ai_tag_style_minimalist);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        String string3 = context.getString(R.string.ai_tag_style_humorous);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        String string4 = context.getString(R.string.ai_tag_style_literary);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        String string5 = context.getString(R.string.ai_tag_style_recommend);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        this.mStyleTags = CollectionsKt.listOf((Object[]) new String[]{string, string2, string3, string4, string5});
        String string6 = context.getString(R.string.ai_tag_emote_none);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        String string7 = context.getString(R.string.ai_tag_emote_less);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        String string8 = context.getString(R.string.ai_tag_emote_moderate);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        String string9 = context.getString(R.string.ai_tag_emote_more);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        this.mEmojiTags = CollectionsKt.listOf((Object[]) new String[]{string6, string7, string8, string9});
        String string10 = context.getString(R.string.ai_tag_text_all);
        Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
        String string11 = context.getString(R.string.ai_tag_text_less);
        Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
        String string12 = context.getString(R.string.ai_tag_text_moderate);
        Intrinsics.checkNotNullExpressionValue(string12, "getString(...)");
        String string13 = context.getString(R.string.ai_tag_text_more);
        Intrinsics.checkNotNullExpressionValue(string13, "getString(...)");
        this.mWordsNumTags = CollectionsKt.listOf((Object[]) new String[]{string10, string11, string12, string13});
        String string14 = context.getString(R.string.ai_comment_good);
        Intrinsics.checkNotNullExpressionValue(string14, "getString(...)");
        String string15 = context.getString(R.string.ai_comment_normal);
        Intrinsics.checkNotNullExpressionValue(string15, "getString(...)");
        String string16 = context.getString(R.string.ai_comment_bad);
        Intrinsics.checkNotNullExpressionValue(string16, "getString(...)");
        this.mReviewsStyleTags = CollectionsKt.listOf((Object[]) new String[]{string14, string15, string16});
        String string17 = context.getResources().getString(R.string.ai_tag_words_betweens, "20", "50");
        Intrinsics.checkNotNullExpressionValue(string17, "getString(...)");
        String string18 = context.getResources().getString(R.string.ai_tag_words_betweens, "50", "100");
        Intrinsics.checkNotNullExpressionValue(string18, "getString(...)");
        String strObtainLocalInteger = StringUtils.obtainLocalInteger(100, R.plurals.ai_tag_words_more_than, context);
        Intrinsics.checkNotNullExpressionValue(strObtainLocalInteger, "obtainLocalInteger(...)");
        this.mReviewsWordsNumTags = CollectionsKt.listOf((Object[]) new String[]{string17, string18, strObtainLocalInteger});
        this.mContext = context;
        this.mAiTextWidgetService = globalAiTextWidgetService;
        String string19 = context.getString(R.string.ai_start_loading_title);
        Intrinsics.checkNotNullExpressionValue(string19, "getString(...)");
        this.mLoadingText = string19;
        this.mAiEndTip = "";
        this.mIsFirstShowEditInput = true;
        this.mLastEditInputHeight = context.getResources().getDimension(R.dimen.global_ai_text_panel_input_height);
        this.mUserPrompt = "";
        this.mTitle = "";
        this.mContent = "";
        this.mStreamContent = "";
        this.aiSessionId = "";
        this.resultStatus = "success";
        this.aiEndWay = "success";
        this.textLinesMap = new HashMap<>();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mImeHeight = -1;
        GlobalAiTextAnimHelper globalAiTextAnimHelper = new GlobalAiTextAnimHelper(context);
        this.mGlobalAiTextAnimHelper = globalAiTextAnimHelper;
        this.mGlobalAiStateManager = new GlobalAiStateManager(this.mHandler);
        this.recyclingWindowAction = new Action() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$recyclingWindowAction$1
            @Override // com.miui.notes.ai.Action
            public void run() {
                this.this$0.trackReportAiClose(AITextWidgetStat.AI_BACK_CLOSE);
                this.this$0.detachFromWindow();
            }
        };
        this.mScreenStatusReceiver = new BroadcastReceiver() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$mScreenStatusReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                Log.i(GlobalAiTextWidgetWindow.TAG, "action=" + intent.getAction());
                if (Intrinsics.areEqual("android.intent.action.SCREEN_OFF", intent.getAction()) || Intrinsics.areEqual(intent.getAction(), NoteContentStat.ACTION_BROADCAST_MIAI_DISMISS)) {
                    this.this$0.detachFromWindow();
                }
            }
        };
        if (Build.VERSION.SDK_INT >= 33) {
            globalAiTextAnimHelper.setAiPanelAnimCallback(new GlobalAiTextAnimHelper.AiPanelAnimCallback() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.1
                @Override // com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper.AiPanelAnimCallback
                public void runPanelEffect(AiShaderEffectRenderer render, float[] bgBoundData, Point screenSize, float time) {
                    Intrinsics.checkNotNullParameter(bgBoundData, "bgBoundData");
                    Intrinsics.checkNotNullParameter(screenSize, "screenSize");
                    if (render != null) {
                        GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                        globalAiTextWidgetWindow.mGlobalAiTextAnimHelper.tick();
                        render.setAnimTime(time);
                        int[] iArr = new int[2];
                        View view = globalAiTextWidgetWindow.mFloatingContentView;
                        if (view != null) {
                            render.setForePanelWH(view.getWidth(), view.getHeight());
                        }
                        View view2 = globalAiTextWidgetWindow.mFloatingRimEffectView;
                        if (view2 != null) {
                            render.setFloatingPanelWH(view2.getWidth(), view2.getHeight());
                            view2.getLocationInWindow(iArr);
                            bgBoundData[0] = iArr[0] / screenSize.x;
                            bgBoundData[1] = 1.0f - ((iArr[1] + view2.getHeight()) / screenSize.y);
                            bgBoundData[2] = view2.getWidth() / screenSize.x;
                            bgBoundData[3] = view2.getHeight() / screenSize.y;
                            render.setBgBound(bgBoundData);
                            render.updateMaterial();
                            render.applyPanelBgColors();
                            view2.setRenderEffect(render.getRenderEffect(9));
                        }
                        View view3 = globalAiTextWidgetWindow.mBgGradientColorView;
                        if (view3 != null) {
                            render.setBgEffectViewWH(view3.getWidth(), view3.getHeight());
                            view3.setRenderEffect(render.getRenderEffect(8));
                        }
                    }
                }

                @Override // com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper.AiPanelAnimCallback
                public void runInputEffect(AiShaderEffectRenderer render, float[] bgBoundData, Point screenSize, int effectBgPadding, float time) {
                    GlobalAiTextWidgetWindow globalAiTextWidgetWindow;
                    View view;
                    View view2;
                    View view3;
                    Intrinsics.checkNotNullParameter(bgBoundData, "bgBoundData");
                    Intrinsics.checkNotNullParameter(screenSize, "screenSize");
                    View view4 = GlobalAiTextWidgetWindow.this.mBgGradientColorView;
                    if (view4 == null || (view = (globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this).mPanelEditInputLayout) == null || (view2 = globalAiTextWidgetWindow.mPanelEditInputContentView) == null || (view3 = globalAiTextWidgetWindow.mPanelEditInputRimEffectView) == null || render == null) {
                        return;
                    }
                    globalAiTextWidgetWindow.mGlobalAiTextAnimHelper.tick();
                    render.setAnimTime(time);
                    render.setForePanelWH(view2.getWidth(), view2.getHeight());
                    render.setFloatingPanelWH(view3.getWidth(), view3.getHeight());
                    render.setBgEffectViewWH(view4.getWidth(), view4.getHeight());
                    int[] iArr = new int[2];
                    view.getLocationInWindow(iArr);
                    bgBoundData[0] = view.getLeft() / screenSize.x;
                    bgBoundData[1] = 1.0f - (((iArr[1] + view.getHeight()) + effectBgPadding) / screenSize.y);
                    bgBoundData[2] = view.getWidth() / screenSize.x;
                    bgBoundData[3] = (view.getHeight() + (effectBgPadding * 2.0f)) / screenSize.y;
                    if (bgBoundData[1] < 0.0f) {
                        bgBoundData[1] = 1.0f - ((iArr[1] + view.getHeight()) / screenSize.y);
                        bgBoundData[3] = view.getHeight() / screenSize.y;
                    }
                    render.setBgBound(bgBoundData);
                    render.updateMaterial();
                    render.applyInputStyleBgColors(false);
                    view4.setRenderEffect(render.getRenderEffect(8));
                    view3.setRenderEffect(render.getRenderEffect(10));
                }
            });
        }
        this.likeListener = new Animator.AnimatorListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$likeListener$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.i(GlobalAiTextWidgetWindow.TAG, "begin=====onAnimationStart.");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.i(GlobalAiTextWidgetWindow.TAG, "begin=====end.");
                this.this$0.isLikeSelected = true;
                LottieAnimationView lottieAnimationView = this.this$0.mFeedbackDisslike;
                if (lottieAnimationView != null) {
                    lottieAnimationView.setImageResource(R.drawable.ai_feedback_negative);
                }
                this.this$0.trackReportAiClick(GlobalAiTextWidgetWindow.FEEDBACK_POSITIVE);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.i(GlobalAiTextWidgetWindow.TAG, "begin=====onAnimationCancel.");
                this.this$0.isLikeSelected = false;
            }
        };
        this.dislikeListener = new Animator.AnimatorListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$dislikeListener$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.e(GlobalAiTextWidgetWindow.TAG, "begin---onAnimationStart.");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.e(GlobalAiTextWidgetWindow.TAG, "begin----end.");
                this.this$0.isDisLikeSelected = true;
                LottieAnimationView lottieAnimationView = this.this$0.mFeedbackLike;
                if (lottieAnimationView != null) {
                    lottieAnimationView.setImageResource(R.drawable.ai_feedback_positive);
                }
                this.this$0.trackReportAiClick(GlobalAiTextWidgetWindow.FEEDBACK_NEGATIVE);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Log.e(GlobalAiTextWidgetWindow.TAG, "begin----onAnimationCancel.");
                this.this$0.isDisLikeSelected = false;
            }
        };
    }

    public final void showAiFloatingWindow(String queryID, String packageName, String panelType, int style, int wordNum, int emoticonsNum) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(panelType, "panelType");
        this.mGlobalAiTextAnimHelper.resetTime();
        GlobalAiTextRequestHelper globalAiTextRequestHelper = new GlobalAiTextRequestHelper(this.mContext);
        this.mGlobalAiTextRequestHelper = globalAiTextRequestHelper;
        globalAiTextRequestHelper.initManger();
        this.mQueryID = queryID;
        this.mPackageName = packageName;
        this.mCurrentPanelType = panelType;
        this.mStyle = style;
        this.mWordsNum = wordNum;
        this.mEmoticonsNum = emoticonsNum;
        Log.i(TAG, "startLoadingPanelAnim showAiFloatingWindow enter.");
        initAndShowFloatingWindow();
    }

    public final void showAiFloatingWindow(String queryID, String packageName, String panelType, int comment, int commentWordsNum) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(panelType, "panelType");
        this.mGlobalAiTextAnimHelper.resetTime();
        GlobalAiTextRequestHelper globalAiTextRequestHelper = new GlobalAiTextRequestHelper(this.mContext);
        this.mGlobalAiTextRequestHelper = globalAiTextRequestHelper;
        globalAiTextRequestHelper.initManger();
        this.mQueryID = queryID;
        this.mPackageName = packageName;
        this.mCurrentPanelType = panelType;
        this.mComment = comment;
        this.mCommentWordsNum = commentWordsNum;
        initAndShowFloatingWindow();
    }

    private final void initAndShowFloatingWindow() {
        if (this.mWindowManager == null && this.mRootView == null) {
            initView();
            addToWindow();
            setTouchableRegion();
            handleTouchEvent();
            handleImeChanged();
            registerBackEventReceiver();
            registerNavigationKeyEventReceiver();
            Log.i(TAG, "screenReceiverRegistered begin=" + this.screenReceiverRegistered);
            this.screenReceiverRegistered = registerScreenStatusReceiver();
        }
        setStateCallback();
        Log.i(TAG, "startLoadingPanelAnim,requestAiData");
        requestAiData(true);
    }

    private final void setStateCallback() {
        this.mGlobalAiStateManager.setAiPanelStateCallback(new GlobalAiStateManager.AiPanelStateCallback() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.setStateCallback.1
            @Override // com.miui.notes.ai.floatingwindow.GlobalAiStateManager.AiPanelStateCallback
            public void showLoadingPanel(boolean isAiEditorPrepared, boolean isFirstStart) {
                GlobalAiTextWidgetWindow.this.setLoadingPanelCallbackImpl(isFirstStart, isAiEditorPrepared);
            }

            @Override // com.miui.notes.ai.floatingwindow.GlobalAiStateManager.AiPanelStateCallback
            public void showGeneratingPanel() {
                TextView textView = GlobalAiTextWidgetWindow.this.mPanelStopGenerateBtn;
                if (textView != null) {
                    textView.setVisibility(0);
                }
                TextView textView2 = GlobalAiTextWidgetWindow.this.mPanelRegenerateBtn;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                GlobalAiTextWidgetWindow.setCustomSettingState$default(GlobalAiTextWidgetWindow.this, false, null, 2, null);
                GlobalAiTextWidgetWindow.this.canPasteToPage(false);
            }

            @Override // com.miui.notes.ai.floatingwindow.GlobalAiStateManager.AiPanelStateCallback
            public void showInputPanel(int imeHeight) {
                GlobalAiTextWidgetWindow.this.showInputPanelCallbackImpl(imeHeight);
            }

            @Override // com.miui.notes.ai.floatingwindow.GlobalAiStateManager.AiPanelStateCallback
            public void showCompletePanel(int resCode, boolean isShowEmpty) {
                TextView textView = GlobalAiTextWidgetWindow.this.mPanelStopGenerateBtn;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                TextView textView2 = GlobalAiTextWidgetWindow.this.mPanelRegenerateBtn;
                if (textView2 != null) {
                    textView2.setVisibility(0);
                }
                GlobalAiTextWidgetWindow.this.trackReportAiEnd();
                GlobalAiEditor globalAiEditor = GlobalAiTextWidgetWindow.this.mPanelAiEditor;
                if (globalAiEditor != null) {
                    globalAiEditor.toggleEmptyLoading(GlobalAiTextWidgetWindow.this.mLoadingText, true);
                }
                Log.i(GlobalAiTextWidgetWindow.TAG, "setCustomSettingState isShowEmpty=" + isShowEmpty + " mStreamContent=" + GlobalAiTextWidgetWindow.this.mStreamContent.length());
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                globalAiTextWidgetWindow.canPasteToPage((isShowEmpty && TextUtils.isEmpty(globalAiTextWidgetWindow.mStreamContent)) ? false : true);
                GlobalAiTextWidgetWindow.this.handleResCode(resCode);
            }

            @Override // com.miui.notes.ai.floatingwindow.GlobalAiStateManager.AiPanelStateCallback
            public void showCompletePanelFromInput() {
                GlobalAiTextWidgetWindow.this.showCompletePanelFromInputImpl();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setLoadingPanelCallbackImpl(boolean isFirstStart, boolean isAiEditorPrepared) {
        String string;
        if (isFirstStart) {
            string = this.mContext.getString(R.string.ai_start_loading_title);
            Intrinsics.checkNotNull(string);
        } else {
            string = this.mContext.getString(R.string.ai_waiting_generate);
            Intrinsics.checkNotNull(string);
        }
        this.mLoadingText = string;
        TextView textView = this.mPanelStopGenerateBtn;
        if (textView != null) {
            textView.setVisibility(0);
        }
        TextView textView2 = this.mPanelRegenerateBtn;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        MiuiFolme.goneView(this.mEmptyView);
        MiuiFolme.visibleView(this.mPanelWebView, this.mFeedbackLayout);
        setCustomSettingState$default(this, false, null, 2, null);
        canPasteToPage(false);
        if (isAiEditorPrepared) {
            GlobalAiEditor globalAiEditor = this.mPanelAiEditor;
            if (globalAiEditor != null) {
                globalAiEditor.clearDoc();
            }
            GlobalAiEditor globalAiEditor2 = this.mPanelAiEditor;
            if (globalAiEditor2 != null) {
                globalAiEditor2.toggleEmptyLoading(this.mLoadingText, false);
            }
        }
        this.mIsFirstStart = isFirstStart;
        if (isFirstStart) {
            View view = this.mFloatingView;
            if (view != null) {
                view.setAlpha(0.0f);
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.setLoadingPanelCallbackImpl$lambda$0(this.f$0);
                }
            }, 50L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setLoadingPanelCallbackImpl$lambda$0(final GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        try {
            View view = globalAiTextWidgetWindow.mFloatingView;
            int height = view != null ? view.getHeight() : 0;
            if (height != 0) {
                globalAiTextWidgetWindow.mGlobalAiTextAnimHelper.startLoadingPanelAnim(globalAiTextWidgetWindow.mFloatingView, height, new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$setLoadingPanelCallbackImpl$1$transitionListener$1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object toTag) {
                        super.onBegin(toTag);
                        Log.i(GlobalAiTextWidgetWindow.TAG, "startLoadingPanelAnim anim begin....");
                        View view2 = this.this$0.mFloatingView;
                        if (view2 != null) {
                            view2.setAlpha(1.0f);
                        }
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object toTag) {
                        super.onComplete(toTag);
                        View view2 = this.this$0.mFloatingView;
                        if (view2 != null) {
                            view2.setAlpha(1.0f);
                        }
                        this.this$0.setTouchableRegion();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object toTag) {
                        super.onCancel(toTag);
                        View view2 = this.this$0.mFloatingView;
                        if (view2 != null) {
                            view2.setAlpha(1.0f);
                        }
                    }
                });
                return;
            }
            View view2 = globalAiTextWidgetWindow.mFloatingView;
            if (view2 != null) {
                view2.setAlpha(1.0f);
            }
        } catch (Exception e) {
            View view3 = globalAiTextWidgetWindow.mFloatingView;
            if (view3 != null) {
                view3.setAlpha(1.0f);
            }
            Log.e(TAG, "setPanelState: " + e.getMessage());
            Log.e(TAG, "setPanelState: " + e.getStackTrace());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showInputPanelCallbackImpl(int imeHeight) {
        Log.i(TAG, "showInputPanel mIsFirstShowEditInput=" + this.mIsFirstShowEditInput);
        if (this.mIsFirstShowEditInput) {
            positionEditeInput(this.mPanelEditInputLayout, this.mPanelShowInputLayout);
        }
        View view = this.mPanelEditInputLayout;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        MiuiFolme.visibleView(this.mPanelEditInputLayout, this.mPanelEditInputRimEffectView);
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null) {
            editText.setSingleLine(false);
        }
        EditText editText2 = this.mPanelEditInputEditText;
        if (editText2 != null) {
            editText2.setMaxLines(4);
        }
        EditText editText3 = this.mPanelEditInputEditText;
        if (editText3 != null) {
            editText3.setMaxHeight(DisplayUtil.dip2px(this.mContext, 104.0f));
        }
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$showInputPanelCallbackImpl$transitionListener$1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow;
                EditText editText4;
                Log.i(GlobalAiTextWidgetWindow.TAG, "showInputPanel onComplete");
                this.this$0.inputPanelAnimListenerImpl();
                View view2 = this.this$0.mPanelEditInputLayout;
                if (view2 == null || (editText4 = (globalAiTextWidgetWindow = this.this$0).mPanelEditInputEditText) == null) {
                    return;
                }
                globalAiTextWidgetWindow.addEditInputLayoutObserver(view2, editText4);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object toTag) {
                Log.i(GlobalAiTextWidgetWindow.TAG, "showInputPanel onCancel");
                this.this$0.inputPanelAnimListenerImpl();
                View view2 = this.this$0.mEnableBtnLayout;
                if (view2 != null) {
                    view2.setAlpha(1.0f);
                }
                View view3 = this.this$0.mDisableBtnLayout;
                if (view3 != null) {
                    view3.setAlpha(1.0f);
                }
            }
        };
        View view2 = this.mPanelEditInputLayout;
        if (view2 != null) {
            EditText editText4 = this.mPanelEditInputEditText;
            if (editText4 != null && TextUtils.isEmpty(editText4.getText().toString())) {
                String string = this.mContext.getResources().getString(R.string.ai_input_default_text);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                int textLines = getTextLines(editText4, string, DisplayUtil.dip2px(this.mContext, 230.0f));
                editText4.setGravity(textLines > 1 ? GravityCompat.START : 8388627);
                this.mLastEditInputHeight = this.mContext.getResources().getDimension(R.dimen.global_ai_text_panel_input_height) + ((textLines - 1) * this.mContext.getResources().getDimension(R.dimen.global_ai_panel_edit_height_extra));
                Log.d(TAG, "showInputPanel: inputHeight: " + view2.getHeight() + " inputHeight=" + this.mLastEditInputHeight + " lines=" + textLines);
            }
            this.mGlobalAiTextAnimHelper.inputAnimWithKeyboard(view2, imeHeight, true, this.mIsFirstShowEditInput, this.mLastEditInputHeight, transitionListener);
            AnimConfig animConfig = new AnimConfig();
            animConfig.setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
            Folme.useAt(this.mFloatingRimEffectView, this.mFloatingContentView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ViewProperty.SCALE_X, Float.valueOf(0.95f), ViewProperty.SCALE_Y, Float.valueOf(0.95f), animConfig);
            if (Build.VERSION.SDK_INT >= 33) {
                this.mGlobalAiTextAnimHelper.onShowSoftInputShaderEffect(view2, this.mPanelEditInputContentView, this.mPanelEditInputRimEffectView);
            }
        }
        this.mIsFirstShowEditInput = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void inputPanelAnimListenerImpl() {
        MiuiFolme.goneView(this.mFloatingRimEffectView, this.mFloatingContentView);
        setTouchableRegion();
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null) {
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
        }
        resetEditInput(this.mPanelEditInputLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showCompletePanelFromInputImpl() {
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null) {
            this.mUserPrompt = editText.getText().toString();
            EditText editText2 = this.mPanelShowInputEditText;
            if (editText2 != null) {
                editText2.setText(editText.getText());
            }
        }
        View view = this.mPanelEditInputLayout;
        if (view != null) {
            float height = view.getHeight();
            this.mLastEditInputHeight = height;
            Log.i(TAG, "showInputPanel mPanelEditInputLayout.mLastEditInputHeight=" + height + ",density=" + this.mContext.getResources().getDisplayMetrics().density);
            removeEditInputLayoutObserver(view);
            MiuiFolme.goneView(this.mPanelEditInputRimEffectView);
            EditText editText3 = this.mPanelEditInputEditText;
            if (editText3 != null && editText3.hasFocus()) {
                editText3.setSelection(0);
                editText3.clearFocus();
            }
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            this.mGlobalAiTextAnimHelper.inputAnimWithKeyboard(view, 0, false, this.mIsFirstShowEditInput, this.mLastEditInputHeight, new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$showCompletePanelFromInputImpl$2$transitionListener$1
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object toTag) {
                    super.onBegin(toTag);
                    MiuiFolme.visibleView(this.this$0.mFloatingRimEffectView, this.this$0.mFloatingContentView);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object toTag, Collection<? extends UpdateInfo> updateList) {
                    super.onUpdate(toTag, updateList);
                    EditText editText4 = this.this$0.mPanelEditInputEditText;
                    if (editText4 != null) {
                        Ref.BooleanRef booleanRef2 = booleanRef;
                        UpdateInfo updateInfoFindByName = UpdateInfo.findByName(updateList, "translationY");
                        if (Math.abs(updateInfoFindByName != null ? updateInfoFindByName.getFloatValue() : 10.0f) >= 10.0f || booleanRef2.element) {
                            return;
                        }
                        editText4.setSingleLine(true);
                        booleanRef2.element = true;
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object toTag) {
                    GlobalAiTextWidgetWindow globalAiTextWidgetWindow = this.this$0;
                    globalAiTextWidgetWindow.completePanelFromInputListenerImpl(globalAiTextWidgetWindow.mPanelEditInputLayout);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object toTag) {
                    GlobalAiTextWidgetWindow globalAiTextWidgetWindow = this.this$0;
                    globalAiTextWidgetWindow.completePanelFromInputListenerImpl(globalAiTextWidgetWindow.mPanelEditInputLayout);
                }
            });
            AnimConfig animConfig = new AnimConfig();
            animConfig.setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
            Folme.useAt(this.mFloatingRimEffectView, this.mFloatingContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ViewProperty.SCALE_X, 1, ViewProperty.SCALE_Y, 1, animConfig);
            if (Build.VERSION.SDK_INT >= 33) {
                this.mGlobalAiTextAnimHelper.onHideSoftInputShaderEffect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void completePanelFromInputListenerImpl(View mPanelEditInputLayout) {
        Log.i(TAG, "showInputPanel completePanelFromInputListenerImpl.");
        resetEditInput(mPanelEditInputLayout);
        MiuiFolme.goneView(mPanelEditInputLayout);
        resetIncludeViewMargin(mPanelEditInputLayout);
        View view = this.mFloatingRimEffectView;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        View view2 = this.mFloatingContentView;
        if (view2 != null) {
            view2.setAlpha(1.0f);
        }
        if (mPanelEditInputLayout != null) {
            mPanelEditInputLayout.setTranslationY(0.0f);
        }
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null) {
            editText.setSingleLine(false);
        }
        EditText editText2 = this.mPanelEditInputEditText;
        if (editText2 != null) {
            editText2.setMaxLines(4);
        }
        EditText editText3 = this.mPanelEditInputEditText;
        if (editText3 != null) {
            editText3.setMaxHeight(DisplayUtil.dip2px(this.mContext, 104.0f));
        }
        setTouchableRegion();
    }

    public final void initView() {
        String str;
        CustomAiTextWidgetEditText.padding = (int) this.mContext.getResources().getDimension(R.dimen.global_ai_edit_input_content_margin_vertical);
        CustomAiTextWidgetEditText.viewMaxHeight = ((int) this.mContext.getResources().getDimension(R.dimen.ai_recommend_layout_edit_max_height)) - (CustomAiTextWidgetEditText.padding * 2);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.global_ai_text_widget_window, (ViewGroup) null);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.notes.ai.ui.FloatingDecorView");
        FloatingDecorView floatingDecorView = (FloatingDecorView) viewInflate;
        this.mRootView = floatingDecorView;
        if (floatingDecorView != null) {
            findViewsInit(floatingDecorView);
            this.textLinesMap.clear();
            String str2 = this.mCurrentPanelType;
            if (Intrinsics.areEqual(str2, "copy_publish")) {
                initCopyPublishPopupWindow(this.mCurrentPanelType);
            } else if (Intrinsics.areEqual(str2, TYPE_PRODUCT_COMMENT)) {
                initProductCommentPopupWindow(this.mCurrentPanelType);
            }
            isEditInputEndBtnActive(this.mUserPrompt.length() > 0);
            TextView textView = this.mEnableBtn;
            if (textView != null) {
                textView.setOnClickListener(this);
            }
            FloatingDecorView floatingDecorView2 = this.mRootView;
            if (floatingDecorView2 != null) {
                floatingDecorView2.setOnScreenChangeListener(this);
            }
            if (RomUtils.isInternationalBuild()) {
                str = "\n<text indent=\"1\"><s1-size>" + this.mContext.getString(R.string.ai_content_generates_reminders) + "</s1-size></text>";
            } else {
                str = "\n<text indent=\"1\"><s1-size>" + this.mContext.getString(R.string.ai_generate_tip) + "</s1-size></text>";
            }
            this.mAiEndTip = str;
            initListener(floatingDecorView, this.mRootView);
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            this.aiSessionId = string;
            this.mCloudIsDevice = (PreferenceUtils.isAcceptedMode(this.mContext) || RomUtils.isLLMAIWriting(this.mContext)) ? false : true;
            trackReportAiOpen();
        }
    }

    private final void initCopyPublishPopupWindow(final String mCurrentPanelType) {
        MiuiFolme.visibleView(this.mPanelTagStyleContainer, this.mPanelTagWordNumContainer, this.mPanelTagEmoticonNumContainer);
        MiuiFolme.goneView(this.mPanelTagCommentContainer, this.mPanelTagCommentNumContainer);
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow = new GlobalAiPanelPopupWindow(this.mContext, this.mStyleTags, "copy_publish", new PopupRvAdapterListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initCopyPublishPopupWindow.1
            @Override // com.miui.notes.ai.ui.PopupRvAdapterListener
            public void onItemClick(int position, String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                GlobalAiTextWidgetWindow.this.mStyle = position;
                TextView textView = GlobalAiTextWidgetWindow.this.mPanelTagStyle;
                if (textView != null) {
                    textView.setText(content);
                }
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                TextView textView2 = globalAiTextWidgetWindow.mPanelTagStyle;
                Intrinsics.checkNotNull(textView2);
                globalAiTextWidgetWindow.fixTagsLines(textView2, mCurrentPanelType);
                GlobalAiPanelPopupWindow globalAiPanelPopupWindow2 = GlobalAiTextWidgetWindow.this.mPanelTagStylePopup;
                if (globalAiPanelPopupWindow2 != null) {
                    globalAiPanelPopupWindow2.dismiss();
                }
            }
        }, Integer.valueOf(this.mStyle));
        this.mPanelTagStylePopup = globalAiPanelPopupWindow;
        View view = this.mPanelTagStyleContainer;
        TextView textView = this.mPanelTagStyle;
        List<String> list = this.mStyleTags;
        initTagView(view, textView, globalAiPanelPopupWindow, list.get(getTagIndex(this.mStyle, list.size(), 0)));
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow2 = new GlobalAiPanelPopupWindow(this.mContext, this.mWordsNumTags, "copy_publish", new PopupRvAdapterListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initCopyPublishPopupWindow.2
            @Override // com.miui.notes.ai.ui.PopupRvAdapterListener
            public void onItemClick(int position, String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                GlobalAiTextWidgetWindow.this.mWordsNum = position;
                TextView textView2 = GlobalAiTextWidgetWindow.this.mPanelTagWordNum;
                if (textView2 != null) {
                    String string = GlobalAiTextWidgetWindow.this.mContext.getString(R.string.ai_tag_show_comment_num, content);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    textView2.setText(StringsKt.replaceFirst$default(string, org.apache.commons.lang3.StringUtils.SPACE, "", false, 4, (Object) null));
                }
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                TextView textView3 = globalAiTextWidgetWindow.mPanelTagWordNum;
                Intrinsics.checkNotNull(textView3);
                globalAiTextWidgetWindow.fixTagsLines(textView3, mCurrentPanelType);
                GlobalAiPanelPopupWindow globalAiPanelPopupWindow3 = GlobalAiTextWidgetWindow.this.mPanelTagWordNumPopup;
                if (globalAiPanelPopupWindow3 != null) {
                    globalAiPanelPopupWindow3.dismiss();
                }
            }
        }, Integer.valueOf(this.mWordsNum));
        this.mPanelTagWordNumPopup = globalAiPanelPopupWindow2;
        View view2 = this.mPanelTagWordNumContainer;
        TextView textView2 = this.mPanelTagWordNum;
        Context context = this.mContext;
        int i = R.string.ai_tag_show_comment_num;
        List<String> list2 = this.mWordsNumTags;
        String string = context.getString(i, list2.get(getTagIndex(this.mWordsNum, list2.size(), 0)));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        initTagView(view2, textView2, globalAiPanelPopupWindow2, StringsKt.replaceFirst$default(string, org.apache.commons.lang3.StringUtils.SPACE, "", false, 4, (Object) null));
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow3 = new GlobalAiPanelPopupWindow(this.mContext, this.mEmojiTags, "copy_publish", new PopupRvAdapterListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initCopyPublishPopupWindow.3
            @Override // com.miui.notes.ai.ui.PopupRvAdapterListener
            public void onItemClick(int position, String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                GlobalAiTextWidgetWindow.this.mEmoticonsNum = position;
                TextView textView3 = GlobalAiTextWidgetWindow.this.mPanelTagEmoticonNum;
                if (textView3 != null) {
                    textView3.setText(GlobalAiTextWidgetWindow.this.mContext.getString(R.string.ai_tag_show_emoticons_num, content));
                }
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                TextView textView4 = globalAiTextWidgetWindow.mPanelTagEmoticonNum;
                Intrinsics.checkNotNull(textView4);
                globalAiTextWidgetWindow.fixTagsLines(textView4, mCurrentPanelType);
                GlobalAiPanelPopupWindow globalAiPanelPopupWindow4 = GlobalAiTextWidgetWindow.this.mPanelTagEmoticonNumPopup;
                if (globalAiPanelPopupWindow4 != null) {
                    globalAiPanelPopupWindow4.dismiss();
                }
            }
        }, Integer.valueOf(this.mEmoticonsNum));
        this.mPanelTagEmoticonNumPopup = globalAiPanelPopupWindow3;
        View view3 = this.mPanelTagEmoticonNumContainer;
        TextView textView3 = this.mPanelTagEmoticonNum;
        Context context2 = this.mContext;
        int i2 = R.string.ai_tag_show_emoticons_num;
        List<String> list3 = this.mEmojiTags;
        String string2 = context2.getString(i2, list3.get(getTagIndex(this.mEmoticonsNum, list3.size(), 0)));
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        initTagView(view3, textView3, globalAiPanelPopupWindow3, string2);
    }

    private final void initProductCommentPopupWindow(final String mCurrentPanelType) {
        MiuiFolme.goneView(this.mPanelTagStyleContainer, this.mPanelTagWordNumContainer, this.mPanelTagEmoticonNumContainer);
        MiuiFolme.visibleView(this.mPanelTagCommentContainer, this.mPanelTagCommentNumContainer);
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow = new GlobalAiPanelPopupWindow(this.mContext, this.mReviewsStyleTags, TYPE_PRODUCT_COMMENT, new PopupRvAdapterListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initProductCommentPopupWindow.1
            @Override // com.miui.notes.ai.ui.PopupRvAdapterListener
            public void onItemClick(int position, String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                GlobalAiTextWidgetWindow.this.mComment = position;
                TextView textView = GlobalAiTextWidgetWindow.this.mPanelTagComment;
                if (textView != null) {
                    textView.setText(GlobalAiTextWidgetWindow.this.mContext.getString(R.string.ai_tag_show_comment, content));
                }
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                TextView textView2 = globalAiTextWidgetWindow.mPanelTagComment;
                Intrinsics.checkNotNull(textView2);
                globalAiTextWidgetWindow.fixTagsLines(textView2, mCurrentPanelType);
                GlobalAiPanelPopupWindow globalAiPanelPopupWindow2 = GlobalAiTextWidgetWindow.this.mPanelTagCommentPopup;
                if (globalAiPanelPopupWindow2 != null) {
                    globalAiPanelPopupWindow2.dismiss();
                }
            }
        }, Integer.valueOf(this.mComment));
        this.mPanelTagCommentPopup = globalAiPanelPopupWindow;
        View view = this.mPanelTagCommentContainer;
        TextView textView = this.mPanelTagComment;
        Context context = this.mContext;
        int i = R.string.ai_tag_show_comment;
        List<String> list = this.mReviewsStyleTags;
        String string = context.getString(i, list.get(getTagIndex(this.mComment, list.size(), 0)));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        initTagView(view, textView, globalAiPanelPopupWindow, string);
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow2 = new GlobalAiPanelPopupWindow(this.mContext, this.mReviewsWordsNumTags, TYPE_PRODUCT_COMMENT, new PopupRvAdapterListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initProductCommentPopupWindow.2
            @Override // com.miui.notes.ai.ui.PopupRvAdapterListener
            public void onItemClick(int position, String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                GlobalAiTextWidgetWindow.this.mCommentWordsNum = position + 10;
                TextView textView2 = GlobalAiTextWidgetWindow.this.mPanelTagCommentNum;
                if (textView2 != null) {
                    textView2.setText(GlobalAiTextWidgetWindow.this.mContext.getString(R.string.ai_tag_show_comment_num, content));
                }
                GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                TextView textView3 = globalAiTextWidgetWindow.mPanelTagCommentNum;
                Intrinsics.checkNotNull(textView3);
                globalAiTextWidgetWindow.fixTagsLines(textView3, mCurrentPanelType);
                GlobalAiPanelPopupWindow globalAiPanelPopupWindow3 = GlobalAiTextWidgetWindow.this.mPanelTagCommentNumPopup;
                if (globalAiPanelPopupWindow3 != null) {
                    globalAiPanelPopupWindow3.dismiss();
                }
            }
        }, Integer.valueOf(this.mCommentWordsNum % 10));
        this.mPanelTagCommentNumPopup = globalAiPanelPopupWindow2;
        View view2 = this.mPanelTagCommentNumContainer;
        TextView textView2 = this.mPanelTagCommentNum;
        Context context2 = this.mContext;
        int i2 = R.string.ai_tag_show_comment_num;
        List<String> list2 = this.mReviewsWordsNumTags;
        String string2 = context2.getString(i2, list2.get(getTagIndex(this.mCommentWordsNum, list2.size(), 10)));
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        initTagView(view2, textView2, globalAiPanelPopupWindow2, string2);
    }

    private final void findViewsInit(FloatingDecorView rootView) {
        this.mFloatingView = rootView.findViewById(R.id.global_ai_floating_view);
        this.mFloatingContentView = rootView.findViewById(R.id.global_ai_floating_content_view);
        this.mBgGradientColorViewContainer = (RenderViewLayout) rootView.findViewById(R.id.global_ai_bg_effect_view_container);
        View view = new View(this.mContext);
        this.mBgGradientColorView = view;
        RenderViewLayout renderViewLayout = this.mBgGradientColorViewContainer;
        if (renderViewLayout != null) {
            renderViewLayout.attachView(view, 0.5f);
        }
        this.mFloatingRimEffectView = rootView.findViewById(R.id.global_ai_floating_rim_view);
        View viewFindViewById = rootView.findViewById(R.id.global_ai_close_button);
        this.mPanelCloseBtn = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
        this.mPanelWebView = (WebView) rootView.findViewById(R.id.global_ai_panel_webview);
        this.mPanelAiEditor = new GlobalAiEditor(this.mContext, this.mPanelWebView, this);
        this.mFeedbackLayout = rootView.findViewById(R.id.global_ai_panel_feedback_layout);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) rootView.findViewById(R.id.global_ai_panel_feedback_positive);
        this.mFeedbackLike = lottieAnimationView;
        if (lottieAnimationView != null) {
            lottieAnimationView.setOnClickListener(this);
        }
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) rootView.findViewById(R.id.global_ai_panel_feedback_negative);
        this.mFeedbackDisslike = lottieAnimationView2;
        if (lottieAnimationView2 != null) {
            lottieAnimationView2.setOnClickListener(this);
        }
        this.mEmptyView = rootView.findViewById(R.id.global_ai_panel_empty);
        this.mPanelDividerLine = rootView.findViewById(R.id.global_ai_panel_divider);
        this.mPanelTagListLayout = (ConstraintLayout) rootView.findViewById(R.id.global_ai_panel_tag_list_layout);
        this.mPanelTagStyleContainer = rootView.findViewById(R.id.global_ai_panel_tag_style_container);
        this.mPanelTagStyle = (TextView) rootView.findViewById(R.id.global_ai_panel_tag_style);
        this.mPanelTagWordNumContainer = rootView.findViewById(R.id.global_ai_panel_tag_word_num_container);
        this.mPanelTagWordNum = (TextView) rootView.findViewById(R.id.global_ai_panel_tag_word_num);
        this.mPanelTagEmoticonNumContainer = rootView.findViewById(R.id.global_ai_panel_tag_emoticon_num_container);
        this.mPanelTagEmoticonNum = (TextView) rootView.findViewById(R.id.global_ai_panel_tag_emoticon_num);
        this.mPanelTagCommentContainer = rootView.findViewById(R.id.global_ai_panel_tag_comment_container);
        this.mPanelTagComment = (TextView) rootView.findViewById(R.id.global_ai_panel_tag_comment);
        this.mPanelTagCommentNumContainer = rootView.findViewById(R.id.global_ai_panel_tag_comment_num_container);
        this.mPanelTagCommentNum = (TextView) rootView.findViewById(R.id.global_ai_panel_tag_comment_num);
        this.mPanelShowInputLayout = rootView.findViewById(R.id.global_ai_show_input_layout);
        EditText editText = (EditText) rootView.findViewById(R.id.global_ai_panel_show_input);
        this.mPanelShowInputEditText = editText;
        if (editText != null) {
            TextExtKt.setEditProperty(editText, "400");
        }
        TextView textView = (TextView) rootView.findViewById(R.id.global_ai_panel_stop_generate_btn);
        this.mPanelStopGenerateBtn = textView;
        if (textView != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.global_ai_panel_stop_icon, 0, 0, 0);
        }
        TextView textView2 = this.mPanelStopGenerateBtn;
        if (textView2 != null) {
            textView2.setCompoundDrawablePadding(DisplayUtil.dip2px(this.mContext, 2.0f));
        }
        TextView textView3 = this.mPanelStopGenerateBtn;
        if (textView3 != null) {
            textView3.setOnClickListener(this);
        }
        TextView textView4 = (TextView) rootView.findViewById(R.id.global_ai_panel_regenerate_btn);
        this.mPanelRegenerateBtn = textView4;
        if (textView4 != null) {
            textView4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ai_regenerate_icon, 0, 0, 0);
        }
        TextView textView5 = this.mPanelRegenerateBtn;
        if (textView5 != null) {
            textView5.setOnClickListener(this);
        }
        TextView textView6 = (TextView) rootView.findViewById(R.id.global_ai_panel_paste_btn);
        this.mPanelPasteBtn = textView6;
        if (textView6 != null) {
            textView6.setOnClickListener(this);
        }
        this.mPanelEditInputLayout = rootView.findViewById(R.id.global_ai_edit_input_layout);
        this.mPanelEditInputContentView = rootView.findViewById(R.id.global_input_content_view);
        this.mPanelEditInputRimEffectView = rootView.findViewById(R.id.global_input_rim_effect_view);
        EditText editText2 = (EditText) rootView.findViewById(R.id.global_ai_edit_input);
        this.mPanelEditInputEditText = editText2;
        if (editText2 != null) {
            TextExtKt.setEditProperty(editText2, "400");
        }
        EditText editText3 = this.mPanelEditInputEditText;
        if (editText3 != null) {
            editText3.setHint("  " + this.mContext.getResources().getString(R.string.ai_input_default_text));
        }
        this.mEnableBtn = (TextView) rootView.findViewById(R.id.global_ai_edit_input_end_btn);
        this.mEnableBtnLayout = rootView.findViewById(R.id.global_ai_edit_input_enable_layout);
        this.mDisableBtnLayout = rootView.findViewById(R.id.global_ai_edit_input_disable_layout);
    }

    private final void initListener(FloatingDecorView rootView, final FloatingDecorView mRootView) {
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.initListener.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    EditText editText2 = GlobalAiTextWidgetWindow.this.mPanelEditInputEditText;
                    Integer numValueOf = editText2 != null ? Integer.valueOf(editText2.getHeight()) : null;
                    EditText editText3 = GlobalAiTextWidgetWindow.this.mPanelEditInputEditText;
                    Integer numValueOf2 = editText3 != null ? Integer.valueOf(editText3.getWidth()) : null;
                    View view = GlobalAiTextWidgetWindow.this.mPanelEditInputContentView;
                    Integer numValueOf3 = view != null ? Integer.valueOf(view.getWidth()) : null;
                    View view2 = GlobalAiTextWidgetWindow.this.mPanelEditInputLayout;
                    Log.i(GlobalAiTextWidgetWindow.TAG, "showInputPanel.edit.height=" + numValueOf + " edit.width=" + numValueOf2 + " mPanelEditInputContentView=" + numValueOf3 + " container.width=" + (view2 != null ? Integer.valueOf(view2.getWidth()) : null));
                    EditText editText4 = GlobalAiTextWidgetWindow.this.mPanelEditInputEditText;
                    if (editText4 != null) {
                        GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                        if (editable != null) {
                            if (editable.length() > 200) {
                                globalAiTextWidgetWindow.showToast(StringUtils.obtainLocalInteger(200, R.plurals.ai_cannot_useAI_toast_text, globalAiTextWidgetWindow.mContext));
                                editText4.setText(editable.subSequence(0, 200).toString());
                                editText4.setSelection(200);
                            }
                            if (StringsKt.trim((CharSequence) editable.toString()).toString().length() > 0) {
                                editText4.setHint("");
                            } else {
                                editText4.setHint("  " + globalAiTextWidgetWindow.mContext.getResources().getString(R.string.ai_input_default_text));
                            }
                        }
                        Editable text = editText4.getText();
                        String string = text != null ? text.toString() : null;
                        globalAiTextWidgetWindow.isEditInputEndBtnActive(true ^ (string == null || string.length() == 0));
                    }
                }
            });
        }
        rootView.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.initListener$lambda$8(this.f$0);
            }
        });
        AiFloatingWindow.ForegroundWindowObserver foregroundWindowObserver = new AiFloatingWindow.ForegroundWindowObserver(new AiFloatingWindow.ForegroundWindowObserverCallback() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda2
            @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow.ForegroundWindowObserverCallback
            public final void onForegroundWindowChanged(ForegroundInfo foregroundInfo) {
                GlobalAiTextWidgetWindow.initListener$lambda$10(this.f$0, mRootView, foregroundInfo);
            }
        });
        this.mAppObserver = foregroundWindowObserver;
        ForegroundInfoUtils.registerForegroundWindowListener(foregroundWindowObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListener$lambda$8(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        globalAiTextWidgetWindow.mGlobalAiTextAnimHelper.initAdvancedMaterialEffects(globalAiTextWidgetWindow.mFloatingContentView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListener$lambda$10(final GlobalAiTextWidgetWindow globalAiTextWidgetWindow, final FloatingDecorView floatingDecorView, ForegroundInfo foregroundInfo) {
        Logger.INSTANCE.i(TAG, "onForegroundWindowChanged current:" + foregroundInfo.mForegroundPackageName + " mPackageName:" + globalAiTextWidgetWindow.mPackageName + " ,isConfigChanged:" + globalAiTextWidgetWindow.isConfigChanged);
        if (UIUtils.isFastClick(-1, 500L)) {
            globalAiTextWidgetWindow.isConfigChanged = false;
            return;
        }
        if (TextUtils.equals(foregroundInfo.mForegroundPackageName, globalAiTextWidgetWindow.mPackageName) && ((globalAiTextWidgetWindow.isConfigChanged || !PackageNameUtils.INSTANCE.isWechatPackage(globalAiTextWidgetWindow.mPackageName)) && (!globalAiTextWidgetWindow.isConfigChanged || !PackageNameUtils.INSTANCE.isDianPingPackage(globalAiTextWidgetWindow.mPackageName)))) {
            globalAiTextWidgetWindow.isConfigChanged = false;
        } else if (floatingDecorView != null) {
            floatingDecorView.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.initListener$lambda$10$lambda$9(floatingDecorView, globalAiTextWidgetWindow);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListener$lambda$10$lambda$9(FloatingDecorView floatingDecorView, GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        if (floatingDecorView != null) {
            floatingDecorView.setVisibility(8);
        }
        globalAiTextWidgetWindow.detachFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fixTagsLines(TextView textView, String panelType) {
        Context context;
        float f;
        String str = panelType;
        if (TextUtils.equals(str, "copy_publish")) {
            context = this.mContext;
            f = 58.0f;
        } else {
            context = this.mContext;
            f = 114.0f;
        }
        int textLines = getTextLines(textView, DisplayUtil.dip2px(context, f));
        Log.i(TAG, "fixTagsLines.width=" + textView.getWidth() + " textLines=" + textLines + " text=" + ((Object) textView.getText()) + " maxTagLines=" + this.maxTagLines);
        this.textLinesMap.put(Integer.valueOf(textView.getId()), Integer.valueOf(textLines));
        if (textLines > this.maxTagLines) {
            this.maxTagLines = textLines;
        } else {
            this.maxTagLines = 1;
            Iterator<Map.Entry<Integer, Integer>> it = this.textLinesMap.entrySet().iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().getValue().intValue();
                if (iIntValue > this.maxTagLines) {
                    this.maxTagLines = iIntValue;
                }
            }
            Log.e(TAG, "fixTagsLines,maxTagLines=" + this.maxTagLines);
        }
        if (TextUtils.equals(str, "copy_publish")) {
            TextView textView2 = this.mPanelTagStyle;
            if (textView2 != null) {
                textView2.setLines(this.maxTagLines);
            }
            TextView textView3 = this.mPanelTagWordNum;
            if (textView3 != null) {
                textView3.setLines(this.maxTagLines);
            }
            TextView textView4 = this.mPanelTagEmoticonNum;
            if (textView4 != null) {
                textView4.setLines(this.maxTagLines);
                return;
            }
            return;
        }
        TextView textView5 = this.mPanelTagComment;
        if (textView5 != null) {
            textView5.setLines(this.maxTagLines);
        }
        TextView textView6 = this.mPanelTagCommentNum;
        if (textView6 != null) {
            textView6.setLines(this.maxTagLines);
        }
    }

    private final int getTextLines(TextView textView, int lineWidth) {
        return getTextLines(textView, textView.getText().toString(), lineWidth);
    }

    private final int getTextLines(TextView textView, String text, int lineWidth) {
        float fMeasureText = textView.getPaint().measureText(text);
        Log.i(TAG, "fixTagsLines,contentLength=" + fMeasureText + " lineWidth=" + lineWidth);
        float f = lineWidth;
        float f2 = fMeasureText % f;
        int i = 0;
        if (f2 > 0.0f) {
            i = (fMeasureText / f <= 1.0f || f - f2 > 10.0f || !StringsKt.contains$default((CharSequence) text, (CharSequence) org.apache.commons.lang3.StringUtils.SPACE, false, 2, (Object) null)) ? 1 : 2;
        }
        int i2 = (int) (fMeasureText / f);
        Log.v(TAG, "fixTagsLines " + i2 + " leftLineNum=" + i);
        return i2 + i;
    }

    private final void initTagView(final View panelTagContainer, TextView panelTagContent, final GlobalAiPanelPopupWindow panelTagPopupWindow, String showContent) {
        if (panelTagContent != null) {
            panelTagContent.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ai_tag_icon, 0);
        }
        if (panelTagContent != null) {
            panelTagContent.setCompoundDrawablePadding((int) this.mContext.getResources().getDimension(R.dimen.global_au_text_panel_tag_icon_padding));
        }
        if (panelTagContent != null) {
            panelTagContent.setText(showContent);
        }
        Intrinsics.checkNotNull(panelTagContent);
        fixTagsLines(panelTagContent, this.mCurrentPanelType);
        if (panelTagContainer != null) {
            panelTagContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GlobalAiTextWidgetWindow.initTagView$lambda$13$lambda$12(panelTagPopupWindow, panelTagContainer, this, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTagView$lambda$13$lambda$12(GlobalAiPanelPopupWindow globalAiPanelPopupWindow, View view, GlobalAiTextWidgetWindow globalAiTextWidgetWindow, View view2) {
        if (globalAiPanelPopupWindow != null) {
            globalAiPanelPopupWindow.showPopupWindow(view, globalAiTextWidgetWindow.mFloatingContentView);
        }
    }

    static /* synthetic */ void setCustomSettingState$default(GlobalAiTextWidgetWindow globalAiTextWidgetWindow, boolean z, Integer num, int i, Object obj) {
        if ((i & 2) != 0) {
            num = 0;
        }
        globalAiTextWidgetWindow.setCustomSettingState(z, num);
    }

    private final void setCustomSettingState(boolean canChange, Integer resCode) {
        View view = this.mFeedbackLayout;
        if (view != null) {
            view.setVisibility(0);
        }
        if (canChange) {
            LottieAnimationView lottieAnimationView = this.mFeedbackLike;
            if (lottieAnimationView != null) {
                lottieAnimationView.setImageResource(R.drawable.ai_feedback_positive);
            }
            LottieAnimationView lottieAnimationView2 = this.mFeedbackDisslike;
            if (lottieAnimationView2 != null) {
                lottieAnimationView2.setImageResource(R.drawable.ai_feedback_negative);
            }
        }
        View view2 = this.mPanelTagStyleContainer;
        if (view2 != null) {
            view2.setEnabled(canChange);
        }
        View view3 = this.mPanelTagStyleContainer;
        if (view3 != null) {
            view3.setClickable(canChange);
        }
        View view4 = this.mPanelTagWordNumContainer;
        if (view4 != null) {
            view4.setEnabled(canChange);
        }
        View view5 = this.mPanelTagWordNumContainer;
        if (view5 != null) {
            view5.setClickable(canChange);
        }
        View view6 = this.mPanelTagEmoticonNumContainer;
        if (view6 != null) {
            view6.setEnabled(canChange);
        }
        View view7 = this.mPanelTagEmoticonNumContainer;
        if (view7 != null) {
            view7.setClickable(canChange);
        }
        View view8 = this.mPanelTagCommentContainer;
        if (view8 != null) {
            view8.setEnabled(canChange);
        }
        View view9 = this.mPanelTagCommentContainer;
        if (view9 != null) {
            view9.setClickable(canChange);
        }
        View view10 = this.mPanelTagCommentNumContainer;
        if (view10 != null) {
            view10.setEnabled(canChange);
        }
        View view11 = this.mPanelTagCommentNumContainer;
        if (view11 != null) {
            view11.setClickable(canChange);
        }
        EditText editText = this.mPanelShowInputEditText;
        if (editText != null) {
            editText.setEnabled(canChange);
        }
        EditText editText2 = this.mPanelShowInputEditText;
        if (editText2 != null) {
            editText2.setClickable(canChange);
        }
        this.mGlobalAiTextAnimHelper.showTagInputLayoutAnim(this.mPanelDividerLine, this.mPanelTagListLayout, this.mPanelShowInputLayout, this.mFeedbackLayout, canChange, resCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void canPasteToPage(boolean canPaste) {
        TextView textView = this.mPanelPasteBtn;
        if (textView != null) {
            textView.setEnabled(canPaste);
        }
        TextView textView2 = this.mPanelPasteBtn;
        if (textView2 != null) {
            textView2.setClickable(canPaste);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResCode(int resCode) {
        if (resCode == 0) {
            Log.e(TAG, "setCustomSettingState handleResCode mFeedbackLayout visible");
            TextView textView = this.mPanelRegenerateBtn;
            if (textView != null) {
                textView.setText(this.mContext.getResources().getString(R.string.ai_action_regenerate));
            }
            MiuiFolme.goneView(this.mEmptyView);
            MiuiFolme.visibleView(this.mPanelWebView, this.mFeedbackLayout);
            setCustomSettingState$default(this, true, null, 2, null);
            return;
        }
        if (this.mGlobalAiStateManager.getMFinishFlow()) {
            Log.e(TAG, "setCustomSettingState handleResCode mFeedbackLayout gone code=" + resCode);
            return;
        }
        TextView textView2 = this.mPanelRegenerateBtn;
        if (textView2 != null) {
            textView2.setText(this.mContext.getResources().getString(R.string.global_ai_action_btn_fail));
        }
        if (TextUtils.isEmpty(this.mStreamContent)) {
            MiuiFolme.goneView(this.mPanelWebView, this.mFeedbackLayout);
            MiuiFolme.visibleView(this.mEmptyView);
            setCustomSettingState(true, Integer.valueOf(resCode));
        } else {
            MiuiFolme.goneView(this.mEmptyView);
            MiuiFolme.visibleView(this.mPanelWebView, this.mFeedbackLayout);
            setCustomSettingState$default(this, true, null, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void isEditInputEndBtnActive(boolean isActive) {
        if (isActive) {
            View view = this.mEnableBtnLayout;
            if (view != null) {
                view.setVisibility(0);
            }
            View view2 = this.mDisableBtnLayout;
            if (view2 != null) {
                view2.setVisibility(8);
                return;
            }
            return;
        }
        View view3 = this.mEnableBtnLayout;
        if (view3 != null) {
            view3.setVisibility(8);
        }
        View view4 = this.mDisableBtnLayout;
        if (view4 != null) {
            view4.setVisibility(0);
        }
    }

    private final void positionEditeInput(View panelEditInputLayout, View targetView) {
        if (targetView == null || panelEditInputLayout == null) {
            return;
        }
        int[] iArr = new int[2];
        targetView.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        panelEditInputLayout.getLocationInWindow(iArr2);
        ViewGroup.LayoutParams layoutParams = panelEditInputLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = iArr[1] - iArr2[1];
        panelEditInputLayout.setLayoutParams(layoutParams2);
    }

    private final void resetEditInput(View panelEditInputLayout) {
        if (panelEditInputLayout != null) {
            ViewGroup.LayoutParams layoutParams = panelEditInputLayout.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
            layoutParams2.height = -2;
            panelEditInputLayout.setLayoutParams(layoutParams2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addEditInputLayoutObserver(final View panelEditInputLayout, final EditText panelEditInputEditText) {
        panelEditInputEditText.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.addEditInputLayoutObserver$lambda$19(panelEditInputEditText, this, panelEditInputLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addEditInputLayoutObserver$lambda$19(final EditText editText, final GlobalAiTextWidgetWindow globalAiTextWidgetWindow, final View view) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = editText.getHeight();
        final Ref.IntRef intRef2 = new Ref.IntRef();
        intRef2.element = intRef.element;
        if (globalAiTextWidgetWindow.mInputGlobalLayoutListener == null) {
            globalAiTextWidgetWindow.mInputGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    GlobalAiTextWidgetWindow.addEditInputLayoutObserver$lambda$19$lambda$18(intRef2, intRef, editText, view, globalAiTextWidgetWindow);
                }
            };
            view.getViewTreeObserver().addOnGlobalLayoutListener(globalAiTextWidgetWindow.mInputGlobalLayoutListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addEditInputLayoutObserver$lambda$19$lambda$18(final Ref.IntRef intRef, final Ref.IntRef intRef2, EditText editText, final View view, final GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        intRef.element = intRef2.element;
        intRef2.element = editText.getHeight();
        if (intRef2.element != intRef.element) {
            view.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.addEditInputLayoutObserver$lambda$19$lambda$18$lambda$17(view, intRef2, intRef, globalAiTextWidgetWindow);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addEditInputLayoutObserver$lambda$19$lambda$18$lambda$17(View view, Ref.IntRef intRef, Ref.IntRef intRef2, GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        view.setTranslationY(view.getTranslationY() - (intRef.element - intRef2.element));
        globalAiTextWidgetWindow.setTouchableRegion();
    }

    private final void removeEditInputLayoutObserver(View panelEditInputLayout) {
        if (this.mInputGlobalLayoutListener != null) {
            panelEditInputLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this.mInputGlobalLayoutListener);
            this.mInputGlobalLayoutListener = null;
        }
    }

    private final void addToWindow() {
        View view = this.mFloatingView;
        ViewGroup.LayoutParams layoutParams = view != null ? view.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        if (DisplayUtil.hasNavigationBar(this.mContext)) {
            if (!DisplayUtil.isLandScape(this.mContext)) {
                layoutParams2.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
            } else {
                if (RomUtils.isPadDevice()) {
                    layoutParams2.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                }
                if (RomUtils.isFoldDevice()) {
                    if (ScreenPosistionUtils.isInNarrowScreen(this.mContext)) {
                        layoutParams2.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
                    } else {
                        layoutParams2.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                    }
                }
            }
        } else {
            layoutParams2.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        }
        View view2 = this.mFloatingView;
        if (view2 != null) {
            view2.setLayoutParams(layoutParams2);
        }
        Object systemService = this.mContext.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.mWindowManager = (WindowManager) systemService;
        WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(-1, -1, R2.attr.preferenceCheckableItemSetMaskPaddingEnd, 262944, 1);
        this.mParams = layoutParams3;
        layoutParams3.softInputMode = 48;
        layoutParams3.layoutInDisplayCutoutMode = 1;
        ReflectUtils.callMethod(layoutParams3, "setTrustedOverlay", new Class[0], new Object[0]);
        if (Build.VERSION.SDK_INT >= 33) {
            this.mGlobalAiTextAnimHelper.initializeShaderEffect();
        }
        try {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.addView(this.mRootView, this.mParams);
            }
            FloatingDecorView floatingDecorView = this.mRootView;
            if (floatingDecorView != null) {
                floatingDecorView.setSystemUiVisibility(3840);
            }
        } catch (Exception e) {
            Log.e(TAG, "addToWindow: " + e);
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTouchableRegion() {
        View view = this.mFloatingView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.setTouchableRegion$lambda$21(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setTouchableRegion$lambda$21(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        Rect regionFromView;
        WindowTouchRegionHelper windowTouchRegionHelper;
        if (globalAiTextWidgetWindow.mWindowTouchRegionHelper == null) {
            globalAiTextWidgetWindow.mWindowTouchRegionHelper = new WindowTouchRegionHelper(globalAiTextWidgetWindow.mFloatingView);
        }
        Rect rect = new Rect();
        if (globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentPanelState() instanceof GlobalAiStateManager.PanelState.InInput) {
            regionFromView = globalAiTextWidgetWindow.getRegionFromView(globalAiTextWidgetWindow.mPanelEditInputLayout, rect);
            Logger.INSTANCE.i(TAG, "TouchRegion STATE_INPUT 区域: " + regionFromView + " getLocalVisibleRect, mInputLayout:" + rect);
        } else {
            regionFromView = globalAiTextWidgetWindow.getRegionFromView(globalAiTextWidgetWindow.mFloatingView, rect);
            Logger.INSTANCE.i(TAG, " TouchRegion getLocalVisibleRect, mFloatingView: region:" + regionFromView + " rect=" + rect);
        }
        WindowTouchRegionHelper windowTouchRegionHelper2 = globalAiTextWidgetWindow.mWindowTouchRegionHelper;
        if (windowTouchRegionHelper2 != null) {
            windowTouchRegionHelper2.updateTouchAreaRect(regionFromView);
        }
        WindowTouchRegionHelper windowTouchRegionHelper3 = globalAiTextWidgetWindow.mWindowTouchRegionHelper;
        if (windowTouchRegionHelper3 != null && !windowTouchRegionHelper3.isRegistered() && (windowTouchRegionHelper = globalAiTextWidgetWindow.mWindowTouchRegionHelper) != null) {
            windowTouchRegionHelper.registerOnComputeInternalInsetsListener();
        }
        View view = globalAiTextWidgetWindow.mFloatingView;
        if (view != null) {
            view.invalidate();
        }
    }

    private final Rect getRegionFromView(View view, Rect r) {
        if (view == null) {
            return r;
        }
        view.getLocalVisibleRect(r);
        return new Rect((int) view.getX(), (int) view.getY(), (int) (view.getX() + r.right), (int) (view.getY() + r.bottom));
    }

    private final void handleTouchEvent() {
        FloatingDecorView floatingDecorView = this.mRootView;
        if (floatingDecorView != null) {
            floatingDecorView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda15
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return GlobalAiTextWidgetWindow.handleTouchEvent$lambda$23(this.f$0, view, motionEvent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean handleTouchEvent$lambda$23(GlobalAiTextWidgetWindow globalAiTextWidgetWindow, View v, MotionEvent event) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(event, "event");
        Logger.INSTANCE.d(TAG, "onTouch: " + event);
        if (event.getAction() != 4) {
            return false;
        }
        globalAiTextWidgetWindow.detachFromWindow();
        return false;
    }

    private final void handleImeChanged() {
        KeyboardListenerHelper keyboardListenerHelper = new KeyboardListenerHelper(this.mRootView);
        this.mKeyboardListenerHelper = keyboardListenerHelper;
        keyboardListenerHelper.setKeyBoardChangedListener(this);
        KeyboardListenerHelper keyboardListenerHelper2 = this.mKeyboardListenerHelper;
        if (keyboardListenerHelper2 != null) {
            keyboardListenerHelper2.addListener();
        }
    }

    private final void registerBackEventReceiver() {
        FloatingDecorView floatingDecorView = this.mRootView;
        if (floatingDecorView != null) {
            floatingDecorView.setRecyclingWindowAction(this.recyclingWindowAction);
        }
    }

    private final void registerNavigationKeyEventReceiver() {
        SystemNavigationKeyClickListener systemNavigationKeyClickListener = SystemNavigationKeyClickListener.getInstance();
        Action action = this.recyclingWindowAction;
        systemNavigationKeyClickListener.registerHomeKeyEventReceiver(action, action, this.mContext);
    }

    private final void unregisterNavigationKeyEventReceiver() {
        SystemNavigationKeyClickListener systemNavigationKeyClickListener = SystemNavigationKeyClickListener.getInstance();
        Context context = this.mContext;
        Action action = this.recyclingWindowAction;
        systemNavigationKeyClickListener.unregisterHomeKeyEventReceiver(context, action, action);
    }

    private final boolean registerScreenStatusReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(NoteContentStat.ACTION_BROADCAST_MIAI_DISMISS);
        this.mContext.registerReceiver(this.mScreenStatusReceiver, intentFilter, 2);
        return true;
    }

    private final void requestAiData(boolean isFirstStart) {
        GlobalAiTextWidgetWindow globalAiTextWidgetWindow;
        boolean z;
        String str = this.mCurrentPanelType;
        if (Intrinsics.areEqual(str, "copy_publish")) {
            globalAiTextWidgetWindow = this;
            z = isFirstStart;
            globalAiTextWidgetWindow.requestAiData(this.mStyle, this.mEmoticonsNum, this.mWordsNum, this.mUserPrompt, z);
        } else {
            globalAiTextWidgetWindow = this;
            z = isFirstStart;
            if (Intrinsics.areEqual(str, TYPE_PRODUCT_COMMENT)) {
                int i = globalAiTextWidgetWindow.mComment;
                int i2 = 1;
                if (i != 0) {
                    if (i == 1) {
                        i2 = 3;
                    } else if (i == 2) {
                        i2 = 2;
                    }
                }
                globalAiTextWidgetWindow.requestAiData(i2, globalAiTextWidgetWindow.mCommentWordsNum, globalAiTextWidgetWindow.mUserPrompt, z);
            }
        }
        globalAiTextWidgetWindow.mStartRequestTime = System.currentTimeMillis();
        globalAiTextWidgetWindow.mFirst = false;
        globalAiTextWidgetWindow.trackReportAiStart(z);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0099  */
    /* JADX WARN: Code duplicated, block: B:18:0x00a9  */
    private final void requestAiData(int style, int emoticonsNum, int wordNum, String userPrompt, boolean isFirstStart) {
        Object objValueOf;
        String str = this.mQueryID;
        if (str != null) {
            String str2 = this.mPackageName;
            if (str2 != null) {
                String str3 = this.mCurrentPanelType;
                if (str3 != null) {
                    this.mContent = "";
                    this.mStreamContent = "";
                    this.mAiSavedData = null;
                    this.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.FirstFlow.INSTANCE);
                    this.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Loading(this.mIsAiEditorPrepared, isFirstStart));
                    Log.d(TAG, "requestAiData: queryID " + str + ", packageName " + str2 + ", panelType " + str3 + ", style " + style + ", wordNum " + wordNum + ", emoticonsNum " + emoticonsNum + ", userPrompt " + userPrompt);
                    GlobalAiTextRequestHelper globalAiTextRequestHelper = this.mGlobalAiTextRequestHelper;
                    if (globalAiTextRequestHelper != null) {
                        globalAiTextRequestHelper.stopRequest();
                    }
                    GlobalAiTextRequestHelper globalAiTextRequestHelper2 = this.mGlobalAiTextRequestHelper;
                    if (globalAiTextRequestHelper2 != null) {
                        globalAiTextRequestHelper2.startRequest(str, str2, str3, style, emoticonsNum, wordNum, userPrompt, this);
                        objValueOf = Unit.INSTANCE;
                    } else {
                        objValueOf = null;
                    }
                    if (objValueOf == null) {
                        objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mCurrentPanelType is null"));
                    }
                } else {
                    objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mCurrentPanelType is null"));
                }
                if (objValueOf == null) {
                    objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mPackageName is null"));
                }
            } else {
                objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mPackageName is null"));
            }
            if (objValueOf != null) {
                return;
            }
        }
        Integer.valueOf(Log.d(TAG, "requestAiData: mQueryID is null"));
    }

    /* JADX WARN: Code duplicated, block: B:16:0x008e  */
    /* JADX WARN: Code duplicated, block: B:18:0x009e  */
    private final void requestAiData(int comment, int wordNum, String userPrompt, boolean isFirstStart) {
        Object objValueOf;
        String str = this.mQueryID;
        if (str != null) {
            String str2 = this.mPackageName;
            if (str2 != null) {
                String str3 = this.mCurrentPanelType;
                if (str3 != null) {
                    this.mContent = "";
                    this.mStreamContent = "";
                    this.mAiSavedData = null;
                    this.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.FirstFlow.INSTANCE);
                    this.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Loading(this.mIsAiEditorPrepared, isFirstStart));
                    Log.d(TAG, "requestAiData: queryID " + str + ", packageName " + str2 + ", panelType " + str3 + ", comment " + comment + ", wordNum " + wordNum + ", userPrompt " + userPrompt);
                    GlobalAiTextRequestHelper globalAiTextRequestHelper = this.mGlobalAiTextRequestHelper;
                    if (globalAiTextRequestHelper != null) {
                        globalAiTextRequestHelper.stopRequest();
                    }
                    GlobalAiTextRequestHelper globalAiTextRequestHelper2 = this.mGlobalAiTextRequestHelper;
                    if (globalAiTextRequestHelper2 != null) {
                        globalAiTextRequestHelper2.startRequest(str, str2, str3, comment, wordNum, userPrompt, this);
                        objValueOf = Unit.INSTANCE;
                    } else {
                        objValueOf = null;
                    }
                    if (objValueOf == null) {
                        objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mCurrentPanelType is null"));
                    }
                } else {
                    objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mCurrentPanelType is null"));
                }
                if (objValueOf == null) {
                    objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mPackageName is null"));
                }
            } else {
                objValueOf = Integer.valueOf(Log.d(TAG, "requestAiData: mPackageName is null"));
            }
            if (objValueOf != null) {
                return;
            }
        }
        Integer.valueOf(Log.d(TAG, "requestAiData: mQueryID is null"));
    }

    private final void trackReportAiOpen() {
        this.mAiOpenTime = System.currentTimeMillis();
        String str = this.mPackageName;
        String str2 = this.mCurrentPanelType;
        int length = this.mStreamContent.length();
        String contentStatus = getContentStatus();
        EditText editText = this.mPanelShowInputEditText;
        AITextWidgetStat.reportGlobalAiOpen(str, str2, length, contentStatus, "content", 0, !TextUtils.isEmpty(String.valueOf(editText != null ? editText.getText() : null)), this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void trackReportAiClose(String aiEndWay) {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.mAiOpenTime);
        String str = this.mPackageName;
        String str2 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        AITextWidgetStat.reportGlobalAiClose(str, str2, String.valueOf(editText != null ? editText.getText() : null), this.mStreamContent.length(), AITextWidgetStat.AI_TEXT_SOURCE_INPUT, aiEndWay, iCurrentTimeMillis, this.mContext);
    }

    private final void trackReportAiStart(boolean isFirstStart) {
        String str = this.mPackageName;
        String str2 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        AITextWidgetStat.reportGlobalAiStart(str, str2, String.valueOf(editText != null ? editText.getText() : null), AITextWidgetStat.GLOBAL_AI_TEXT_AI_SCENE, isFirstStart ? AITextWidgetStat.NEW_GENERATE : AITextWidgetStat.REFRESH_GENERATE, AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW, this.mStreamContent.length(), getContentStatus(), this.aiSessionId, "content", AITextWidgetStat.AI_TEXT_SOURCE_INPUT, 0, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void trackReportAiClick(String feedbackType) {
        TextView textView;
        TextView textView2;
        String strValueOf;
        String strValueOf2 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView = this.mPanelTagComment) != null : (textView = this.mPanelTagStyle) != null) ? null : textView.getText());
        String strValueOf3 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView2 = this.mPanelTagCommentNum) != null : (textView2 = this.mPanelTagWordNum) != null) ? null : textView2.getText());
        if (TextUtils.equals(this.mCurrentPanelType, "copy_publish")) {
            TextView textView3 = this.mPanelTagEmoticonNum;
            strValueOf = String.valueOf(textView3 != null ? textView3.getText() : null);
        } else {
            strValueOf = "";
        }
        String str = strValueOf;
        String str2 = this.mPackageName;
        String str3 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        AITextWidgetStat.reportGlobalAiFeekbackClick(str2, str3, String.valueOf(editText != null ? editText.getText() : null), AITextWidgetStat.GLOBAL_AI_TEXT_AI_SCENE, this.mIsFirstStart ? AITextWidgetStat.NEW_GENERATE : AITextWidgetStat.REFRESH_GENERATE, AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW, this.mStreamContent.length(), getContentStatus(), this.aiSessionId, "content", AITextWidgetStat.AI_TEXT_SOURCE_INPUT, 0, strValueOf2, strValueOf3, str, this.resultStatus, this.mAiTraceId, getAiTraceType(), feedbackType, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void trackReportAiEnd() {
        TextView textView;
        TextView textView2;
        String strValueOf;
        String strValueOf2 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView = this.mPanelTagComment) != null : (textView = this.mPanelTagStyle) != null) ? null : textView.getText());
        String strValueOf3 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView2 = this.mPanelTagCommentNum) != null : (textView2 = this.mPanelTagWordNum) != null) ? null : textView2.getText());
        if (TextUtils.equals(this.mCurrentPanelType, "copy_publish")) {
            TextView textView3 = this.mPanelTagEmoticonNum;
            strValueOf = String.valueOf(textView3 != null ? textView3.getText() : null);
        } else {
            strValueOf = "";
        }
        String str = strValueOf;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.mStartRequestTime;
        int i = (int) (jCurrentTimeMillis - j);
        int i2 = (int) (this.endRequestTime - j);
        String str2 = this.mPackageName;
        String str3 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        String strValueOf4 = String.valueOf(editText != null ? editText.getText() : null);
        String str4 = this.mIsFirstStart ? AITextWidgetStat.NEW_GENERATE : AITextWidgetStat.REFRESH_GENERATE;
        int length = this.mStreamContent.length();
        String contentStatus = getContentStatus();
        String str5 = this.aiSessionId;
        String str6 = this.resultStatus;
        AITextWidgetStat.reportGlobalAiEnd(str2, str3, strValueOf4, AITextWidgetStat.GLOBAL_AI_TEXT_AI_SCENE, str4, AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW, length, contentStatus, str5, "content", AITextWidgetStat.AI_TEXT_SOURCE_INPUT, 0, strValueOf2, strValueOf3, str, i, i2, str6, str6, this.mAiTraceId, getAiTraceType(), this.aiEndWay, this.mContext);
    }

    private final void trackReportAiUsed() {
        TextView textView;
        TextView textView2;
        String strValueOf;
        String strValueOf2 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView = this.mPanelTagComment) != null : (textView = this.mPanelTagStyle) != null) ? null : textView.getText());
        String strValueOf3 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView2 = this.mPanelTagCommentNum) != null : (textView2 = this.mPanelTagWordNum) != null) ? null : textView2.getText());
        if (TextUtils.equals(this.mCurrentPanelType, "copy_publish")) {
            TextView textView3 = this.mPanelTagEmoticonNum;
            strValueOf = String.valueOf(textView3 != null ? textView3.getText() : null);
        } else {
            strValueOf = "";
        }
        String str = strValueOf;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.mStartRequestTime;
        int i = (int) (jCurrentTimeMillis - j);
        int i2 = (int) (this.endRequestTime - j);
        String str2 = this.mPackageName;
        String str3 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        String strValueOf4 = String.valueOf(editText != null ? editText.getText() : null);
        String str4 = this.mIsFirstStart ? AITextWidgetStat.NEW_GENERATE : AITextWidgetStat.REFRESH_GENERATE;
        int length = this.mStreamContent.length();
        String contentStatus = getContentStatus();
        String str5 = this.aiSessionId;
        String str6 = this.resultStatus;
        AITextWidgetStat.reportGlobalAiUsed(str2, str3, strValueOf4, AITextWidgetStat.GLOBAL_AI_TEXT_AI_SCENE, str4, AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW, length, contentStatus, str5, "content", AITextWidgetStat.AI_TEXT_SOURCE_INPUT, 0, strValueOf2, strValueOf3, str, i, i2, str6, str6, this.mAiTraceId, getAiTraceType(), this.aiEndWay, this.mContext);
    }

    private final void trackReportAiCanceled() {
        TextView textView;
        TextView textView2;
        String strValueOf;
        String strValueOf2 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView = this.mPanelTagComment) != null : (textView = this.mPanelTagStyle) != null) ? null : textView.getText());
        String strValueOf3 = String.valueOf((!TextUtils.equals(this.mCurrentPanelType, "copy_publish") ? (textView2 = this.mPanelTagCommentNum) != null : (textView2 = this.mPanelTagWordNum) != null) ? null : textView2.getText());
        if (TextUtils.equals(this.mCurrentPanelType, "copy_publish")) {
            TextView textView3 = this.mPanelTagEmoticonNum;
            strValueOf = String.valueOf(textView3 != null ? textView3.getText() : null);
        } else {
            strValueOf = "";
        }
        String str = strValueOf;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.mStartRequestTime;
        int i = (int) (jCurrentTimeMillis - j);
        int i2 = (int) (this.endRequestTime - j);
        String str2 = this.mPackageName;
        String str3 = this.mCurrentPanelType;
        EditText editText = this.mPanelShowInputEditText;
        String strValueOf4 = String.valueOf(editText != null ? editText.getText() : null);
        String str4 = this.mIsFirstStart ? AITextWidgetStat.NEW_GENERATE : AITextWidgetStat.REFRESH_GENERATE;
        int length = this.mStreamContent.length();
        String contentStatus = getContentStatus();
        String str5 = this.aiSessionId;
        String str6 = this.resultStatus;
        AITextWidgetStat.reportGlobalAiCanceled(str2, str3, strValueOf4, AITextWidgetStat.GLOBAL_AI_TEXT_AI_SCENE, str4, AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW, length, contentStatus, str5, "content", AITextWidgetStat.AI_TEXT_SOURCE_INPUT, 0, strValueOf2, strValueOf3, str, i, i2, str6, str6, this.mAiTraceId, getAiTraceType(), this.aiEndWay, this.mContext);
    }

    private final String getAiTraceType() {
        return this.mCloudIsDevice ? AITextWidgetStat.AI_CLOUD_TYPE : AITextWidgetStat.AI_LOCAL_TYPE;
    }

    private final String getContentStatus() {
        return AITextWidgetStat.EMPTY_STATUS;
    }

    static /* synthetic */ void stopGenerating$default(GlobalAiTextWidgetWindow globalAiTextWidgetWindow, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        globalAiTextWidgetWindow.stopGenerating(str, z);
    }

    private final void stopGenerating(String endTip, boolean needChangePanelState) {
        GlobalAiEditor globalAiEditor = this.mPanelAiEditor;
        if (globalAiEditor != null) {
            if (!TextUtils.isEmpty(endTip) && !this.mGlobalAiStateManager.getMFinishFlow()) {
                globalAiEditor.finishFlow(true, "", "");
            }
            this.mGlobalAiStateManager.setFlowState(new GlobalAiStateManager.FlowState.Interrupt(endTip));
            globalAiEditor.stopInput(endTip);
            globalAiEditor.setEnableEdit(false);
            GlobalAiTextRequestHelper globalAiTextRequestHelper = this.mGlobalAiTextRequestHelper;
            if (globalAiTextRequestHelper != null) {
                globalAiTextRequestHelper.stopRequest();
            }
            if (needChangePanelState) {
                this.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Complete(0, TextUtils.isEmpty(this.mStreamContent)));
            }
        }
    }

    private final void pasteToPage() {
        final int i;
        if (Intrinsics.areEqual(this.mCurrentPanelType, "copy_publish")) {
            i = this.mWordsNum;
        } else {
            i = this.mCommentWordsNum;
        }
        Log.d(TAG, "pasteToPage: mStreamContent " + this.mStreamContent.length());
        Log.d(TAG, "pasteToPage: mQueryID " + this.mQueryID + ", mPackageName " + this.mPackageName + ", mCurrentPanelType " + this.mCurrentPanelType + ", mStyle " + this.mStyle + ", wordNum " + i + ", mEmoticonsNum " + this.mEmoticonsNum + ", mComment " + this.mComment + ", mTitle " + this.mTitle.length() + ", mContent " + this.mContent.length());
        if (this.mContent.length() > 0 && Intrinsics.areEqual(this.mGlobalAiStateManager.getMCurrentFlowState(), GlobalAiStateManager.FlowState.RefreshComplete.INSTANCE)) {
            pasteContentToPage(i, true);
        } else {
            GlobalAiEditor globalAiEditor = this.mPanelAiEditor;
            if (globalAiEditor != null) {
                globalAiEditor.doCopy(new GlobalAiEditor.GlobalAiEditorListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow.pasteToPage.1
                    @Override // com.miui.notes.ai.editor.GlobalAiEditor.GlobalAiEditorListener
                    public void onEditorCopy(String content) {
                        Intrinsics.checkNotNullParameter(content, "content");
                        GlobalAiTextWidgetWindow.this.mContent = content;
                        GlobalAiTextWidgetWindow.this.mTitle = "";
                        if (!TextUtils.isEmpty(GlobalAiTextWidgetWindow.this.mContent)) {
                            Log.i(GlobalAiTextWidgetWindow.TAG, "pasteToPage:doCopy content='" + GlobalAiTextWidgetWindow.this.mContent + "'");
                        }
                        if (GlobalAiTextWidgetWindow.this.mContent.length() == 0) {
                            GlobalAiTextWidgetWindow globalAiTextWidgetWindow = GlobalAiTextWidgetWindow.this;
                            globalAiTextWidgetWindow.mContent = globalAiTextWidgetWindow.mStreamContent;
                        }
                        GlobalAiTextWidgetWindow.this.pasteContentToPage(i, false);
                    }
                });
            }
        }
        trackReportAiUsed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pasteContentToPage(int wordNum, boolean isAddEndtip) {
        String str;
        Uri uriBuild = new Uri.Builder().scheme("content").authority("com.hyperos.agent.runtime.AgentProvider").path("CreationAgent").build();
        Intrinsics.checkNotNullExpressionValue(uriBuild, "build(...)");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Pair[] pairArr = new Pair[10];
        pairArr[0] = TuplesKt.to(GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, this.mQueryID);
        pairArr[1] = TuplesKt.to("packageName", this.mPackageName);
        pairArr[2] = TuplesKt.to("pageType", this.mCurrentPanelType);
        pairArr[3] = TuplesKt.to("style", Integer.valueOf(this.mStyle));
        pairArr[4] = TuplesKt.to("wordNums", Integer.valueOf(wordNum));
        pairArr[5] = TuplesKt.to("emoticonsNums", Integer.valueOf(this.mEmoticonsNum));
        pairArr[6] = TuplesKt.to("comment", Integer.valueOf(this.mComment));
        pairArr[7] = TuplesKt.to("title", this.mTitle);
        String str2 = this.mContent;
        if (RomUtils.isInternationalBuild() || !isAddEndtip) {
            str = "";
        } else {
            str = org.apache.commons.lang3.StringUtils.LF + this.mContext.getString(R.string.ai_generate_tip);
        }
        pairArr[8] = TuplesKt.to("content", str2 + str);
        pairArr[9] = TuplesKt.to(GlobalAiTextWidgetServiceHelper.KEY_COMMAND_METHOD, "fill");
        contentResolver.call(uriBuild, "method_command", uriBuild.toString(), BundleKt.bundleOf(pairArr));
    }

    public final void setWindowCloseListener(AiFloatingWindow.WindowCloseListener mWindowCloseListener) {
        this.mWindowCloseListener = mWindowCloseListener;
    }

    public final void dismissWindow() {
        WindowManager.LayoutParams layoutParams;
        if (this.mDuringRemoveView) {
            return;
        }
        Logger.INSTANCE.i(TAG, "dismissWindow start");
        this.mDuringRemoveView = true;
        View view = this.mPanelEditInputLayout;
        if (view != null) {
            removeEditInputLayoutObserver(view);
        }
        unregisterNavigationKeyEventReceiver();
        Log.i(TAG, "screenReceiverRegistered=" + this.screenReceiverRegistered);
        if (this.screenReceiverRegistered) {
            this.mContext.unregisterReceiver(this.mScreenStatusReceiver);
            this.screenReceiverRegistered = false;
        }
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null && (layoutParams = this.mParams) != null) {
            layoutParams.flags |= 8;
            windowManager.updateViewLayout(this.mRootView, layoutParams);
            windowManager.removeView(this.mRootView);
        }
        AiFloatingWindow.WindowCloseListener windowCloseListener = this.mWindowCloseListener;
        if (windowCloseListener != null) {
            windowCloseListener.onWindowClosed();
        }
        releaseToast();
        AiFloatingWindow.ForegroundWindowObserver foregroundWindowObserver = this.mAppObserver;
        if (foregroundWindowObserver != null) {
            foregroundWindowObserver.destroy();
            ForegroundInfoUtils.unregisterForegroundWindowListener(foregroundWindowObserver);
        }
        WindowTouchRegionHelper windowTouchRegionHelper = this.mWindowTouchRegionHelper;
        if (windowTouchRegionHelper != null && windowTouchRegionHelper.isRegistered()) {
            WindowTouchRegionHelper windowTouchRegionHelper2 = this.mWindowTouchRegionHelper;
            if (windowTouchRegionHelper2 != null) {
                windowTouchRegionHelper2.unRegisterOnComputeInternalInsetsListener();
            }
            this.mWindowTouchRegionHelper = null;
        }
        this.mWindowManager = null;
        this.mRootView = null;
        this.mGlobalAiTextAnimHelper.releaseShaderRenderer();
        this.mGlobalAiTextAnimHelper.release();
        this.mGlobalAiStateManager.release();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mDuringRemoveView = false;
        noticeWindowClosed();
        Logger.INSTANCE.i(TAG, "dismissWindow end");
    }

    private final void noticeWindowClosed() {
        Uri uriBuild = new Uri.Builder().scheme("content").authority("com.hyperos.agent.runtime.AgentProvider").path("CreationAgent").build();
        Intrinsics.checkNotNullExpressionValue(uriBuild, "build(...)");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        try {
            contentResolver.call(uriBuild, "method_command", uriBuild.toString(), BundleKt.bundleOf(TuplesKt.to(GlobalAiTextWidgetServiceHelper.KEY_COMMAND_METHOD, "dismiss_notes")));
        } catch (Exception unused) {
            Logger.INSTANCE.e(TAG, "dismiss_notes call fail.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void detachFromWindow() {
        this.mAiTextWidgetService = null;
        GlobalAiTextRequestHelper globalAiTextRequestHelper = this.mGlobalAiTextRequestHelper;
        if (globalAiTextRequestHelper != null) {
            globalAiTextRequestHelper.stopRequest();
        }
        GlobalAiTextRequestHelper globalAiTextRequestHelper2 = this.mGlobalAiTextRequestHelper;
        if (globalAiTextRequestHelper2 != null) {
            globalAiTextRequestHelper2.cancelRequest();
        }
        this.mGlobalAiTextRequestHelper = null;
        if (this.mFloatingView == null || this.mDuringRemoveView) {
            return;
        }
        if (Intrinsics.areEqual(this.mGlobalAiStateManager.getMCurrentFlowState(), GlobalAiStateManager.FlowState.Generating.INSTANCE)) {
            stopGenerating("", false);
        }
        final View view = this.mFloatingView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.detachFromWindow$lambda$42$lambda$41(this.f$0, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void detachFromWindow$lambda$42$lambda$41(final GlobalAiTextWidgetWindow globalAiTextWidgetWindow, final View view) {
        try {
            globalAiTextWidgetWindow.mGlobalAiTextAnimHelper.floatWindowCloseAnim(view, new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$detachFromWindow$1$1$transitionListener$1
                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object toTag, Collection<UpdateInfo> updateList) {
                    super.onUpdate(toTag, updateList);
                    view.setVisibility(8);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object toTag) {
                    super.onComplete(toTag);
                    MiuiFolme.cleanFolme(view);
                    Logger.INSTANCE.i(GlobalAiTextWidgetWindow.TAG, "dismissWindow-2222");
                    globalAiTextWidgetWindow.dismissWindow();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object toTag) {
                    super.onCancel(toTag);
                    MiuiFolme.cleanFolme(view);
                    Logger.INSTANCE.e(GlobalAiTextWidgetWindow.TAG, "dismissWindow-1111");
                    globalAiTextWidgetWindow.dismissWindow();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "detachFromWindow: " + e.getMessage());
            Log.e(TAG, "detachFromWindow: " + e.getStackTrace());
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Context context;
        int i;
        String string;
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        int i2 = R.id.global_ai_close_button;
        if (numValueOf != null && numValueOf.intValue() == i2) {
            this.aiEndWay = AITextWidgetStat.CLOSURE_WINDOW;
            trackReportAiCanceled();
            trackReportAiClose(AITextWidgetStat.AI_BTN_CLOSE);
            detachFromWindow();
            return;
        }
        int i3 = R.id.global_ai_panel_stop_generate_btn;
        if (numValueOf != null && numValueOf.intValue() == i3) {
            if (this.mGlobalAiStateManager.getMOnFlowDone()) {
                return;
            }
            TextView textView = this.mPanelStopGenerateBtn;
            if ((textView == null || textView.getVisibility() != 8) && !Intrinsics.areEqual(this.mGlobalAiStateManager.getMCurrentFlowState(), GlobalAiStateManager.FlowState.StartRefresh.INSTANCE)) {
                if (this.mGlobalAiStateManager.getMFirstInvokeFlow()) {
                    string = "";
                } else {
                    if (RomUtils.isInternationalBuild()) {
                        context = this.mContext;
                        i = R.string.ai_content_generates_reminders;
                    } else {
                        context = this.mContext;
                        i = R.string.ai_generate_tip;
                    }
                    string = context.getString(i);
                    Intrinsics.checkNotNull(string);
                }
                this.resultStatus = AITextWidgetStat.GENERATE_STOP;
                this.aiEndWay = AITextWidgetStat.GENERATE_STOP;
                stopGenerating$default(this, string, false, 2, null);
                return;
            }
            return;
        }
        int i4 = R.id.global_ai_panel_regenerate_btn;
        if (numValueOf != null && numValueOf.intValue() == i4) {
            if (this.mGlobalAiStateManager.getMCurrentPanelState() instanceof GlobalAiStateManager.PanelState.Complete) {
                requestAiData(false);
                return;
            }
            return;
        }
        int i5 = R.id.global_ai_panel_paste_btn;
        if (numValueOf != null && numValueOf.intValue() == i5) {
            pasteToPage();
            trackReportAiClose(AITextWidgetStat.AI_USED_CLOSE);
            detachFromWindow();
            return;
        }
        onClickEvent(numValueOf);
    }

    private final void onClickEvent(Integer id) {
        int i = R.id.global_ai_edit_input_end_btn;
        if (id != null && id.intValue() == i) {
            SoftInputUtil.hideSoftInput(this.mContext, this.mPanelEditInputEditText);
            return;
        }
        int i2 = R.id.global_ai_panel_feedback_positive;
        if (id != null && id.intValue() == i2) {
            Log.i(TAG, "begin=====");
            if (this.isLikeSelected) {
                LottieAnimationView lottieAnimationView = this.mFeedbackLike;
                if (lottieAnimationView != null) {
                    lottieAnimationView.pauseAnimation();
                }
                LottieAnimationView lottieAnimationView2 = this.mFeedbackLike;
                if (lottieAnimationView2 != null) {
                    lottieAnimationView2.setImageResource(R.drawable.ai_feedback_positive);
                }
                this.isLikeSelected = false;
                return;
            }
            this.isLikeSelected = true;
            LottieAnimationView lottieAnimationView3 = this.mFeedbackDisslike;
            if (lottieAnimationView3 != null) {
                lottieAnimationView3.pauseAnimation();
            }
            LottieAnimationView lottieAnimationView4 = this.mFeedbackDisslike;
            if (lottieAnimationView4 != null) {
                lottieAnimationView4.setImageResource(R.drawable.ai_feedback_negative);
            }
            this.isDisLikeSelected = false;
            LottieAnimationView lottieAnimationView5 = this.mFeedbackLike;
            if (lottieAnimationView5 != null) {
                lottieAnimationView5.setAnimation("like_lottie_data.json");
            }
            LottieAnimationView lottieAnimationView6 = this.mFeedbackLike;
            if (lottieAnimationView6 != null) {
                lottieAnimationView6.playAnimation();
            }
            LottieAnimationView lottieAnimationView7 = this.mFeedbackLike;
            if (lottieAnimationView7 != null) {
                lottieAnimationView7.addAnimatorListener(this.likeListener);
                return;
            }
            return;
        }
        int i3 = R.id.global_ai_panel_feedback_negative;
        if (id != null && id.intValue() == i3) {
            Log.e(TAG, "begin----");
            if (this.isDisLikeSelected) {
                LottieAnimationView lottieAnimationView8 = this.mFeedbackDisslike;
                if (lottieAnimationView8 != null) {
                    lottieAnimationView8.pauseAnimation();
                }
                LottieAnimationView lottieAnimationView9 = this.mFeedbackDisslike;
                if (lottieAnimationView9 != null) {
                    lottieAnimationView9.setImageResource(R.drawable.ai_feedback_negative);
                }
                this.isDisLikeSelected = false;
                return;
            }
            this.isDisLikeSelected = true;
            LottieAnimationView lottieAnimationView10 = this.mFeedbackLike;
            if (lottieAnimationView10 != null) {
                lottieAnimationView10.pauseAnimation();
            }
            LottieAnimationView lottieAnimationView11 = this.mFeedbackLike;
            if (lottieAnimationView11 != null) {
                lottieAnimationView11.setImageResource(R.drawable.ai_feedback_positive);
            }
            this.isLikeSelected = false;
            LottieAnimationView lottieAnimationView12 = this.mFeedbackDisslike;
            if (lottieAnimationView12 != null) {
                lottieAnimationView12.setAnimation("dislike_lottie_data.json");
            }
            LottieAnimationView lottieAnimationView13 = this.mFeedbackDisslike;
            if (lottieAnimationView13 != null) {
                lottieAnimationView13.playAnimation();
            }
            LottieAnimationView lottieAnimationView14 = this.mFeedbackDisslike;
            if (lottieAnimationView14 != null) {
                lottieAnimationView14.addAnimatorListener(this.dislikeListener);
            }
        }
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void listenString(final AiOutputData result) {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.listenString$lambda$45(result, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void listenString$lambda$45(AiOutputData aiOutputData, GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        if (aiOutputData != null) {
            if (!globalAiTextWidgetWindow.mFirst) {
                globalAiTextWidgetWindow.endRequestTime = System.currentTimeMillis();
                globalAiTextWidgetWindow.mFirst = true;
            }
            globalAiTextWidgetWindow.mAiTraceId = aiOutputData.traceId;
            GlobalAiEditor globalAiEditor = globalAiTextWidgetWindow.mPanelAiEditor;
            if (globalAiEditor != null) {
                String str = aiOutputData.resultContentStream;
                if (Intrinsics.areEqual(GlobalAiTextRequestManager.FLOW_DONE_TAG, str)) {
                    String str2 = aiOutputData.resultContent;
                    Log.d(TAG, "listenString: content is null? " + TextUtils.isEmpty(str2));
                    String str3 = aiOutputData.theme;
                    Log.d(TAG, "listenString: theme is null? " + TextUtils.isEmpty(str3));
                    if (str3 == null) {
                        str3 = "";
                    }
                    globalAiTextWidgetWindow.mTitle = str3;
                    if (str2 == null) {
                        str2 = "";
                    }
                    globalAiTextWidgetWindow.mContent = str2;
                    return;
                }
                if (globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentPanelState() instanceof GlobalAiStateManager.PanelState.Loading) {
                    Log.i(TAG, "listenString: first char.");
                    globalAiTextWidgetWindow.mGlobalAiStateManager.setPanelState(GlobalAiStateManager.PanelState.Generating.INSTANCE);
                }
                globalAiEditor.addText(str, globalAiTextWidgetWindow.mGlobalAiStateManager.getMFirstInvokeFlow(), false);
                globalAiTextWidgetWindow.mStreamContent += str;
                globalAiTextWidgetWindow.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.Generating.INSTANCE);
                if (TextUtils.isEmpty(NoteSchemaUtil.hasDeleteLabel(str))) {
                    return;
                }
                globalAiTextWidgetWindow.resultStatus = AITextWidgetStat.AI_Identify_Refuse;
            }
        }
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void netError(final NetworkError networkError) {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.netError$lambda$47(this.f$0, networkError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void netError$lambda$47(GlobalAiTextWidgetWindow globalAiTextWidgetWindow, NetworkError networkError) {
        Context context;
        int i;
        String string;
        if (Intrinsics.areEqual(globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentFlowState(), GlobalAiStateManager.FlowState.Generating.INSTANCE)) {
            if (globalAiTextWidgetWindow.mGlobalAiStateManager.getMFirstInvokeFlow()) {
                string = "";
            } else {
                if (RomUtils.isInternationalBuild()) {
                    context = globalAiTextWidgetWindow.mContext;
                    i = R.string.ai_content_generates_reminders;
                } else {
                    context = globalAiTextWidgetWindow.mContext;
                    i = R.string.ai_generate_tip;
                }
                string = context.getString(i);
                Intrinsics.checkNotNull(string);
            }
            globalAiTextWidgetWindow.stopGenerating(string, false);
        }
        globalAiTextWidgetWindow.resultStatus = "fail";
        globalAiTextWidgetWindow.aiEndWay = "fail";
        if (networkError != null) {
            globalAiTextWidgetWindow.mAiTraceId = networkError.getTraceId();
            globalAiTextWidgetWindow.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Complete(networkError.getErrorCodeNumber(), true));
        }
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void finishListen() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.finishListen$lambda$49(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void finishListen$lambda$49(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        GlobalAiEditor globalAiEditor;
        if (globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentRequestState() == GlobalAiStateManager.RequestState.STATE_ERROR || (globalAiEditor = globalAiTextWidgetWindow.mPanelAiEditor) == null) {
            return;
        }
        Log.d(TAG, "finishListen: ");
        globalAiEditor.finishFlow(true, "", "");
        globalAiTextWidgetWindow.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.DataFinish.INSTANCE);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onPagePrepared() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.onPagePrepared$lambda$50(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onPagePrepared$lambda$50(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        Log.d(TAG, "onPagePrepared: ");
        if (globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentPanelState() instanceof GlobalAiStateManager.PanelState.Loading) {
            GlobalAiStateManager.PanelState mCurrentPanelState = globalAiTextWidgetWindow.mGlobalAiStateManager.getMCurrentPanelState();
            Intrinsics.checkNotNull(mCurrentPanelState, "null cannot be cast to non-null type com.miui.notes.ai.floatingwindow.GlobalAiStateManager.PanelState.Loading");
            if (!((GlobalAiStateManager.PanelState.Loading) mCurrentPanelState).isAiEditorPrepared()) {
                GlobalAiEditor globalAiEditor = globalAiTextWidgetWindow.mPanelAiEditor;
                if (globalAiEditor != null) {
                    globalAiEditor.clearDoc();
                }
                GlobalAiEditor globalAiEditor2 = globalAiTextWidgetWindow.mPanelAiEditor;
                if (globalAiEditor2 != null) {
                    globalAiEditor2.toggleEmptyLoading(globalAiTextWidgetWindow.mLoadingText, false);
                }
            }
        }
        GlobalAiEditor globalAiEditor3 = globalAiTextWidgetWindow.mPanelAiEditor;
        if (globalAiEditor3 != null) {
            globalAiEditor3.setEnableEdit(false);
        }
        globalAiTextWidgetWindow.mIsAiEditorPrepared = true;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onFlowDone() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.onFlowDone$lambda$51(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFlowDone$lambda$51(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        GlobalAiEditor globalAiEditor;
        Log.d(TAG, "onFlowDone: ");
        if (!globalAiTextWidgetWindow.mGlobalAiStateManager.getMHasStopGenerating() && (globalAiEditor = globalAiTextWidgetWindow.mPanelAiEditor) != null) {
            globalAiEditor.beforeRefreshNewContent();
        }
        globalAiTextWidgetWindow.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.FlowDone.INSTANCE);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onSaveData(String data) {
        this.mAiSavedData = data;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void notifySetNewContent() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.notifySetNewContent$lambda$54(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifySetNewContent$lambda$54(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        String str;
        Log.d(TAG, "notifySetNewContent: ");
        List listSplit$default = StringsKt.split$default((CharSequence) StringsKt.replace$default(globalAiTextWidgetWindow.mContent, SSEConst.MSG_SPLIT, org.apache.commons.lang3.StringUtils.LF, false, 4, (Object) null), new String[]{org.apache.commons.lang3.StringUtils.LF}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add("<text indent=\"1\">" + ((String) it.next()) + "</text>");
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, org.apache.commons.lang3.StringUtils.LF, null, null, 0, null, null, 62, null);
        GlobalAiEditor globalAiEditor = globalAiTextWidgetWindow.mPanelAiEditor;
        if (globalAiEditor != null) {
            if (globalAiTextWidgetWindow.mAiSavedData == null) {
                str = globalAiTextWidgetWindow.mAiEndTip;
            } else if (TextUtils.isEmpty(globalAiTextWidgetWindow.mTitle)) {
                str = strJoinToString$default + globalAiTextWidgetWindow.mAiEndTip;
            } else {
                str = "<text indent=\"1\"><size><b>" + globalAiTextWidgetWindow.mTitle + "</b></size></text>\n" + strJoinToString$default + globalAiTextWidgetWindow.mAiEndTip;
            }
            globalAiTextWidgetWindow.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.StartRefresh.INSTANCE);
            globalAiEditor.setDataWithConfig(str, "");
            globalAiEditor.refreshNewContent();
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void notifyRefreshComplete() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextWidgetWindow.notifyRefreshComplete$lambda$55(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifyRefreshComplete$lambda$55(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        Log.d(TAG, "notifyRefreshComplete: ");
        globalAiTextWidgetWindow.mGlobalAiStateManager.setFlowState(GlobalAiStateManager.FlowState.RefreshComplete.INSTANCE);
        globalAiTextWidgetWindow.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Complete(0, TextUtils.isEmpty(globalAiTextWidgetWindow.mStreamContent)));
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean isNightMode() {
        return DisplayUtil.isNightMode(this.mContext);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getStringFromResource(String key) {
        return this.mContext.getResources().getString(StringUtils.getResId(key, R.string.class));
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getNumberAndStringFromResource(int number, String key) {
        return StringUtils.obtainLocalInteger(number, StringUtils.getResId(key, R.plurals.class), this.mContext);
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onShowKeyboard(int imeHeight) {
        Log.d(TAG, "showInputPanel onShowKeyboard imeHeight=" + imeHeight + " mImeHeight=" + this.mImeHeight + " mCurrentPanelState=" + this.mGlobalAiStateManager.getMCurrentPanelState());
        EditText editText = this.mPanelShowInputEditText;
        if (editText != null) {
            boolean zHasFocus = editText.hasFocus();
            EditText editText2 = this.mPanelEditInputEditText;
            Boolean boolValueOf = editText2 != null ? Boolean.valueOf(editText2.hasFocus()) : null;
            Log.i(TAG, "showInputPanel onShowKeyboard HasFocus=" + zHasFocus + " isEditFours=" + boolValueOf + " mCurrentPanelState=" + this.mGlobalAiStateManager.getMCurrentPanelState());
            if (imeHeight != this.mImeHeight) {
                if (zHasFocus || Intrinsics.areEqual((Object) boolValueOf, (Object) true)) {
                    this.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.InInput(imeHeight));
                    this.mImeHeight = imeHeight;
                }
            }
        }
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard");
        if (this.mPanelShowInputEditText != null && (this.mGlobalAiStateManager.getMCurrentPanelState() instanceof GlobalAiStateManager.PanelState.InInput)) {
            this.mGlobalAiStateManager.setPanelState(new GlobalAiStateManager.PanelState.Complete(0, TextUtils.isEmpty(this.mStreamContent)));
        }
        this.mImeHeight = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showToast(String text) {
        if (this.mAiToast == null) {
            this.mAiToast = new AiToast(this.mContext, this.mRootView);
        }
        AiToast aiToast = this.mAiToast;
        if (aiToast != null) {
            aiToast.showToast(text, 2000L, this.mImeHeight);
        }
    }

    private final void releaseToast() {
        AiToast aiToast = this.mAiToast;
        if (aiToast != null) {
            aiToast.release();
        }
        this.mAiToast = null;
    }

    @Override // com.miui.notes.ai.ui.FloatingDecorView.OnScreenChangeListener, com.miui.notes.ai.AiTextWidgetService.OnScreenChangeListener
    public void setOnScreenChange(boolean isLandScape) {
        if (RomUtils.isPhoneDevice() && isLandScape) {
            Logger.INSTANCE.i(TAG, "When the mobile phone is landscape, detachFromWindow");
            detachFromWindow();
            return;
        }
        this.isConfigChanged = true;
        Log.i(TAG, "setOnScreenChange=" + isLandScape);
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow = this.mPanelTagStylePopup;
        if (globalAiPanelPopupWindow != null) {
            globalAiPanelPopupWindow.dismiss();
        }
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow2 = this.mPanelTagWordNumPopup;
        if (globalAiPanelPopupWindow2 != null) {
            globalAiPanelPopupWindow2.dismiss();
        }
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow3 = this.mPanelTagEmoticonNumPopup;
        if (globalAiPanelPopupWindow3 != null) {
            globalAiPanelPopupWindow3.dismiss();
        }
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow4 = this.mPanelTagCommentPopup;
        if (globalAiPanelPopupWindow4 != null) {
            globalAiPanelPopupWindow4.dismiss();
        }
        GlobalAiPanelPopupWindow globalAiPanelPopupWindow5 = this.mPanelTagCommentNumPopup;
        if (globalAiPanelPopupWindow5 != null) {
            globalAiPanelPopupWindow5.dismiss();
        }
        this.mGlobalAiTextAnimHelper.updateScreenSize();
        EditText editText = this.mPanelEditInputEditText;
        if (editText != null && editText.hasFocus()) {
            Log.i(TAG, "isConfigChanged hideSoftInput");
            SoftInputUtil.hideSoftInput(this.mContext, this.mPanelEditInputEditText);
            return;
        }
        Log.i(TAG, "isConfigChanged resetIncludeViewMargin");
        View view = this.mPanelEditInputLayout;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextWidgetWindow$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalAiTextWidgetWindow.setOnScreenChange$lambda$58(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setOnScreenChange$lambda$58(GlobalAiTextWidgetWindow globalAiTextWidgetWindow) {
        globalAiTextWidgetWindow.resetIncludeViewMargin(globalAiTextWidgetWindow.mPanelEditInputLayout);
    }

    private final void resetIncludeViewMargin(View mPanelEditInputLayout) {
        int[] iArr = new int[2];
        View view = this.mPanelShowInputLayout;
        if (view != null) {
            view.getLocationInWindow(iArr);
        }
        if (mPanelEditInputLayout != null) {
            ViewGroup.LayoutParams layoutParams = mPanelEditInputLayout.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
            int i = marginLayoutParams != null ? marginLayoutParams.topMargin : 0;
            if (i != iArr[1]) {
                ViewGroup.LayoutParams layoutParams2 = mPanelEditInputLayout.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) layoutParams2;
                layoutParams3.topMargin = iArr[1];
                mPanelEditInputLayout.setLayoutParams(layoutParams3);
            }
            Log.i(TAG, "resetIncludeViewMargin.x=" + iArr[0] + " y=" + iArr[1] + " marginTop=" + i + " onComplete");
        }
    }

    @Override // com.miui.notes.ai.ui.FloatingDecorView.OnScreenChangeListener, com.miui.notes.ai.AiTextWidgetService.OnScreenChangeListener
    public void onConfigChange() {
        Log.i(TAG, "isConfigChanged");
        setTouchableRegion();
    }
}
