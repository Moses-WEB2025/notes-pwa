package com.miui.notes.doodle.feature;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.fragment.app.FragmentActivity;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.stat.EditStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.doodle.base.IDoodleListener;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItemListener;
import com.miui.doodle.base.core.IDoodleSelectableItem;
import com.miui.doodle.component.AppGuideDialog;
import com.miui.doodle.document.DocumentInfo;
import com.miui.doodle.document.DocumentReader;
import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.feature.DoodleEditTextDialog;
import com.miui.doodle.feature.color.ColorBtnComponent;
import com.miui.doodle.feature.color.ColorPickListener;
import com.miui.doodle.feature.color.IColorBtn;
import com.miui.doodle.feature.color.IPalette;
import com.miui.doodle.feature.color.PaletteComponent;
import com.miui.doodle.feature.color.PaletteListener;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.miui.doodle.feature.draw.DoodleOnTouchGestureListener;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.doodle.feature.draw.DoodlePenManager;
import com.miui.doodle.feature.draw.DoodleText;
import com.miui.doodle.feature.draw.DoodleTouchDetector;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.feature.tools.BrushConfigComponent;
import com.miui.doodle.feature.tools.BrushConfigListener;
import com.miui.doodle.feature.tools.IBrushConfig;
import com.miui.doodle.utils.DisplayUtils;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.doodle.utils.RomUtils;
import com.miui.doodle.utils.SharedPreferenceUtil;
import com.miui.doodle.utils.UIUtils;
import com.miui.doodle.utils.Utils;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import com.miui.support.cardview.CardView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.math.MathKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.Fragment;
import miuix.appcompat.app.ProgressDialog;
import miuix.popupwidget.widget.GuidePopupWindow;
import miuix.responsive.map.ScreenSpec;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: DoodleFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000²\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 ±\u00022\u00020\u00012\u00020\u0002:\u0006¯\u0002°\u0002±\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0002J\u0010\u0010F\u001a\u00020C2\u0006\u0010G\u001a\u00020EH\u0002J\b\u0010J\u001a\u00020CH\u0002J\u0010\u0010K\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0002J$\u0010L\u001a\u00020C2\b\u0010M\u001a\u0004\u0018\u00010N2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010O\u001a\u00020\u0016H\u0016J\b\u0010P\u001a\u00020CH\u0002J\t\u0010¸\u0001\u001a\u00020\u0016H\u0002J\t\u0010¹\u0001\u001a\u00020EH\u0002J\u0015\u0010º\u0001\u001a\u00020C2\n\u0010»\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0016J\u0013\u0010½\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u0001H\u0016J\u0015\u0010¿\u0001\u001a\u00020C2\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0016J-\u0010Á\u0001\u001a\u0004\u0018\u00010E2\b\u0010Â\u0001\u001a\u00030Ã\u00012\n\u0010Ä\u0001\u001a\u0005\u0018\u00010Å\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0016J\u0013\u0010Æ\u0001\u001a\u00020C2\b\u0010Ç\u0001\u001a\u00030¼\u0001H\u0016J\t\u0010È\u0001\u001a\u00020CH\u0016J\t\u0010É\u0001\u001a\u00020CH\u0016J\u001e\u0010Ê\u0001\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0007\u0010Ë\u0001\u001a\u00020\u0016H\u0002J\u0012\u0010Ì\u0001\u001a\u00020C2\u0007\u0010Í\u0001\u001a\u00020EH\u0016J(\u0010Î\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010Í\u0001\u001a\u00020E2\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0016J\u0013\u0010Ï\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u0001H\u0002J\u0017\u0010Ð\u0001\u001a\u0004\u0018\u00010\u00062\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u0015\u0010Ñ\u0001\u001a\u00020\u00062\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u001f\u0010Ò\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u0012\u0010Ó\u0001\u001a\u00020C2\u0007\u0010Ô\u0001\u001a\u00020\u0006H\u0002J*\u0010Õ\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u00012\t\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0006H\u0002J(\u0010×\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u00012\u0007\u0010Ö\u0001\u001a\u00020\u0006H\u0002J\u0013\u0010Ø\u0001\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u0001H\u0002J\u001f\u0010Ù\u0001\u001a\u00020C2\b\u0010Ú\u0001\u001a\u00030Û\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u001f\u0010Ü\u0001\u001a\u00020C2\b\u0010Ú\u0001\u001a\u00030Û\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u0015\u0010Ý\u0001\u001a\u00020C2\n\u0010À\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0002J\u0013\u0010Þ\u0001\u001a\u00020C2\b\u0010Ú\u0001\u001a\u00030Û\u0001H\u0002J\u0012\u0010ß\u0001\u001a\u00020E2\u0007\u0010Ö\u0001\u001a\u00020\u0006H\u0002J\u001d\u0010à\u0001\u001a\u00020C2\b\u0010á\u0001\u001a\u00030â\u00012\b\u0010ã\u0001\u001a\u00030Å\u0001H\u0002J\n\u0010ä\u0001\u001a\u00030å\u0001H\u0016J\u001b\u0010X\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010æ\u0001\u001a\u00020EH\u0002J\t\u0010ç\u0001\u001a\u00020CH\u0002J\t\u0010è\u0001\u001a\u00020\u0016H\u0002J\t\u0010é\u0001\u001a\u00020CH\u0002J\t\u0010ê\u0001\u001a\u00020CH\u0002J\t\u0010ë\u0001\u001a\u00020CH\u0002J\t\u0010ì\u0001\u001a\u00020CH\u0002J\u0012\u0010ï\u0001\u001a\u00020C2\u0007\u0010ð\u0001\u001a\u00020\u0016H\u0002J\u0012\u0010ò\u0001\u001a\u00020C2\u0007\u0010ó\u0001\u001a\u00020\u0016H\u0002J\u0012\u0010ô\u0001\u001a\u00020C2\u0007\u0010ó\u0001\u001a\u00020\u0016H\u0002J\u0012\u0010õ\u0001\u001a\u00020C2\u0007\u0010ð\u0001\u001a\u00020\u0016H\u0002J\t\u0010ö\u0001\u001a\u00020CH\u0002J\u0012\u0010÷\u0001\u001a\u00020C2\u0007\u0010ø\u0001\u001a\u00020*H\u0016J\t\u0010ù\u0001\u001a\u00020CH\u0016J\t\u0010ú\u0001\u001a\u00020CH\u0002J\t\u0010û\u0001\u001a\u00020CH\u0002J\t\u0010ü\u0001\u001a\u00020CH\u0016J\u0012\u0010ý\u0001\u001a\u00020C2\u0007\u0010þ\u0001\u001a\u00020\u0016H\u0002J\t\u0010ÿ\u0001\u001a\u00020CH\u0002J-\u0010\u0080\u0002\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0007\u0010\u0081\u0002\u001a\u00020\u00062\b\u0010\u0082\u0002\u001a\u00030Û\u00012\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J#\u0010\u0084\u0002\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0007\u0010\u0081\u0002\u001a\u00020\u00062\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0085\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0086\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0087\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0088\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0089\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008a\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008b\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008c\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008d\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008e\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u008f\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J\u0012\u0010\u0090\u0002\u001a\u00020C2\u0007\u0010\u0083\u0002\u001a\u00020\u0016H\u0002J&\u0010\u0091\u0002\u001a\u00030\u0092\u00022\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010\u0081\u0002\u001a\u00020\u00062\u0007\u0010\u0093\u0002\u001a\u00020*H\u0002J\u001d\u0010\u0094\u0002\u001a\u00030\u0092\u00022\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010\u0081\u0002\u001a\u00020\u0006H\u0002J\u001d\u0010\u0095\u0002\u001a\u00030\u0092\u00022\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010\u0081\u0002\u001a\u00020\u0006H\u0002J'\u0010\u0096\u0002\u001a\u00020C2\u0007\u0010\u0097\u0002\u001a\u00020*2\u0007\u0010\u0098\u0002\u001a\u00020*2\n\u0010\u0099\u0002\u001a\u0005\u0018\u00010\u009a\u0002H\u0016J(\u0010\u009b\u0002\u001a\u0005\u0018\u00010\u0092\u00022\b\u0010¾\u0001\u001a\u00030·\u00012\u0007\u0010ó\u0001\u001a\u00020\u00162\u0007\u0010\u0093\u0002\u001a\u00020*H\u0002J\u0007\u0010\u009c\u0002\u001a\u00020CJ\u0013\u0010\u009e\u0002\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u0001H\u0002J\t\u0010\u009f\u0002\u001a\u00020CH\u0002J\u001d\u0010 \u0002\u001a\u00020C2\b\u0010¾\u0001\u001a\u00030·\u00012\b\u0010¡\u0002\u001a\u00030¢\u0002H\u0002J\t\u0010£\u0002\u001a\u00020CH\u0002J\t\u0010¤\u0002\u001a\u00020CH\u0016J\t\u0010¥\u0002\u001a\u00020CH\u0016J\u0012\u0010¦\u0002\u001a\u00020C2\u0007\u0010§\u0002\u001a\u00020NH\u0016J\t\u0010¨\u0002\u001a\u00020CH\u0002J\t\u0010©\u0002\u001a\u00020CH\u0016J\t\u0010ª\u0002\u001a\u00020CH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u000e\u0010/\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R)\u00105\u001a\u0010\u0012\f\u0012\n 7*\u0004\u0018\u00010\u00000\u0000068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b8\u00109R\u001f\u0010<\u001a\u00060=j\u0002`>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bA\u0010;\u001a\u0004\b?\u0010@R\u000e\u0010H\u001a\u00020IX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010Q\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010S\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010T\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010U\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010V\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010W\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010X\u001a\u0004\u0018\u00010EX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010Y\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u001a\u0010^\u001a\u00020_X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b`\u0010a\"\u0004\bb\u0010cR\u001c\u0010d\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010[\"\u0004\bf\u0010]R\u001c\u0010g\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010[\"\u0004\bi\u0010]R\u001c\u0010j\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010[\"\u0004\bl\u0010]R\u001c\u0010m\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010[\"\u0004\bo\u0010]R\u001c\u0010p\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010[\"\u0004\br\u0010]R\u001a\u0010s\u001a\u00020EX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010[\"\u0004\bu\u0010]R\u001c\u0010v\u001a\u0004\u0018\u00010wX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bx\u0010y\"\u0004\bz\u0010{R\u000e\u0010|\u001a\u00020}X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010~\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u007f\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010[\"\u0005\b\u0081\u0001\u0010]R\u000f\u0010\u0082\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0083\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0084\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0085\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0086\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0087\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0088\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0089\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008a\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008b\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008c\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008d\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008e\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u008f\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0090\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u0091\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u001f\u0010\u0092\u0001\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0093\u0001\u0010[\"\u0005\b\u0094\u0001\u0010]R\u001f\u0010\u0095\u0001\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010[\"\u0005\b\u0097\u0001\u0010]R\u001f\u0010\u0098\u0001\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0099\u0001\u0010[\"\u0005\b\u009a\u0001\u0010]R\u0011\u0010\u009b\u0001\u001a\u0004\u0018\u00010EX\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u009c\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u009d\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u009e\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010\u009f\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010 \u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010¡\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010¢\u0001\u001a\u00030£\u0001X\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010¤\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010¥\u0001\u001a\u00030£\u0001X\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010¦\u0001\u001a\u00020EX\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010§\u0001\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010¨\u0001\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010©\u0001\u001a\u00030ª\u0001X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010«\u0001\u001a\u00030¬\u0001X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u00ad\u0001\u001a\u00030®\u0001X\u0082.¢\u0006\u0002\n\u0000R\u000f\u0010¯\u0001\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010°\u0001\u001a\u00020EX\u0084.¢\u0006\u0010\n\u0000\u001a\u0005\b±\u0001\u0010[\"\u0005\b²\u0001\u0010]R\u0012\u0010³\u0001\u001a\u0005\u0018\u00010´\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010µ\u0001\u001a\n\u0018\u00010=j\u0004\u0018\u0001`>X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010¶\u0001\u001a\u0005\u0018\u00010·\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010í\u0001\u001a\u00030î\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010ñ\u0001\u001a\u00030î\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u009d\u0002\u001a\u0005\u0018\u00010´\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010«\u0002\u001a\u00030¬\u0002X\u0096\u0005¢\u0006\b\u001a\u0006\b\u00ad\u0002\u0010®\u0002¨\u0006²\u0002"}, d2 = {"Lcom/miui/notes/doodle/feature/DoodleFragment;", "Lmiuix/appcompat/app/Fragment;", "Lkotlinx/coroutines/CoroutineScope;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "imagePath", "", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "getDoodleView", "()Lcom/miui/doodle/feature/draw/DoodleView;", "setDoodleView", "(Lcom/miui/doodle/feature/draw/DoodleView;)V", "doodleParams", "Lcom/miui/doodle/base/DoodleParams;", "touchGestureListener", "Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener;", "doodleEditTextDialog", "Lcom/miui/doodle/feature/DoodleEditTextDialog;", "isChoosingImage", "", "isSaved", "saveBeforeExit", "getSaveBeforeExit", "()Z", "setSaveBeforeExit", "(Z)V", "myDoodleDocument", "Lcom/miui/doodle/document/DoodleDocument;", "getMyDoodleDocument", "()Lcom/miui/doodle/document/DoodleDocument;", "setMyDoodleDocument", "(Lcom/miui/doodle/document/DoodleDocument;)V", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "getScreenSpec", "()Lmiuix/responsive/map/ScreenSpec;", "setScreenSpec", "(Lmiuix/responsive/map/ScreenSpec;)V", "displayWidth", "", "getDisplayWidth", "()I", "setDisplayWidth", "(I)V", "mIsInit", "unselectedAccessibilityDelegate", "Lcom/miui/notes/doodle/feature/DoodleFragment$UnselectedAccessibilityDelegate;", "appGuideIsShowed", "selectedElementEraser", "currentBackgroundType", "handler", "Lcom/miui/notes/base/app/MainHandler;", "kotlin.jvm.PlatformType", "getHandler", "()Lcom/miui/notes/base/app/MainHandler;", "handler$delegate", "Lkotlin/Lazy;", "saveTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "getSaveTask", "()Ljava/lang/Runnable;", "saveTask$delegate", "announceToolSelected", "", "view", "Landroid/view/View;", "resetOtherToolsAccessibility", "selectedView", "toolClickListener", "Landroid/view/View$OnClickListener;", "addNewLayerBitmap", "showBrushConfigGuide", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "responsiveLayoutStateChanged", "toggleElementContainer", "elementEraserContainerAnimate", "Landroid/view/ViewPropertyAnimator;", "pixelEraserContainerAnimate", "toolPanelContainerAnimate", "colorViewAnimate", "tipViewAnimate", "tipViewAnimate1", "activeTool", "actionBarContainer", "getActionBarContainer", "()Landroid/view/View;", "setActionBarContainer", "(Landroid/view/View;)V", "doodleContainer", "Landroid/widget/FrameLayout;", "getDoodleContainer", "()Landroid/widget/FrameLayout;", "setDoodleContainer", "(Landroid/widget/FrameLayout;)V", "mRootView", "getMRootView", "setMRootView", "btnUndo", "getBtnUndo", "setBtnUndo", "btnRedo", "getBtnRedo", "setBtnRedo", "btnImmerse", "getBtnImmerse", "setBtnImmerse", "btnDone", "getBtnDone", "setBtnDone", "btnUnImmerse", "getBtnUnImmerse", "setBtnUnImmerse", "btnPanelImmerse", "Lcom/miui/support/cardview/CardView;", "getBtnPanelImmerse", "()Lcom/miui/support/cardview/CardView;", "setBtnPanelImmerse", "(Lcom/miui/support/cardview/CardView;)V", "btnPanelImmerseIcon", "Landroid/widget/ImageView;", "doodlePanelContainer", "toolPanelContainer", "getToolPanelContainer", "setToolPanelContainer", "brushPencil", "brushNormal", "brushMarkPen", "brushWaterColor", "brushEraser", "toolText", "toolPicture", "toolBackground", "brushPencilTouch", "brushNormalTouch", "brushMarkPenTouch", "brushWaterColorTouch", "brushEraserTouch", "toolTextTouch", "toolPictureTouch", "toolBackgroundTouch", "colorView", "getColorView", "setColorView", "paletteContainer", "getPaletteContainer", "setPaletteContainer", "brushConfigContainer", "getBrushConfigContainer", "setBrushConfigContainer", "backgroundConfigContainer", "backgroundWhiteConfigContainer", "backgroundDotConfigContainer", "elementEraserContainer", "pixelEraser", "elementEraser", "pixelTipView", "pixelTipText", "Landroid/widget/TextView;", "elementTipView", "elementTipText", "pixelEraserContainer", "isInImmerseMode", "isPanelInImmerseMode", "colorBtn", "Lcom/miui/doodle/feature/color/IColorBtn;", "palette", "Lcom/miui/doodle/feature/color/IPalette;", "brushConfig", "Lcom/miui/doodle/feature/tools/IBrushConfig;", "isShowBackgroundConfig", "alphaBg", "getAlphaBg", "setAlphaBg", "loadDataDialog", "Lmiuix/appcompat/app/ProgressDialog;", "showLoadingDialogTask", "mContext", "Landroid/content/Context;", "dismissConfig", "getPaintView", "onCreate", "bundle", "Landroid/os/Bundle;", "onAttach", CoreConstants.CONTEXT_SCOPE_VALUE, "onActivityCreated", "savedInstanceState", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onSaveInstanceState", "outState", "onStart", "onResume", "loadDoodleDocFromFile", "fromSnapshot", "initLayout", "rootView", "initToolPanel", "initToolView", "getSavedPenType", "getSavedBackgroundType", "restorePenToolbarState", "restoreBackgroundState", "backgroundType", "restorePenState", "penType", "restorePenWithType", "restoreDefaultPenState", "restoreColorAndSizePen", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "restoreSizeOnlyPen", "restoreEraserSpecificState", "updatePenUI", "getActiveViewForPenType", "initDoodleView", "activity", "Landroid/app/Activity;", "parentView", "getTransY", "", "newActiveView", "hideBackgroundContainer", "isShowBackgroundContainer", "showBackgroundContainer", "updateBrushViewColor", "updateActiveToolView", "updateUnSelectToolView", "actionBarDismissListener", "Lmiuix/animation/listener/TransitionListener;", "showActionBarView", "isShow", "toolPanelDismissListener", "switchImmerseMode", "isToImmerse", "setPanelImmerseState", "showPaletteImmerseEntry", "togglePaletteImmerseEntryState", "updateDoodleBackground", "resId", "reloadDoodleOnWindowChanged", "resetEditTextDialog", "resetUndo", "updateBackgroundContainerMargin", "resetDoodleDocument", "isFilePathNotEmpty", "resetTranslation", "updateBrushViewState", "toolName", "doodlePen", "isSelect", "updateToolViewState", "updatePencilView", "updatePencilViewWithCurrentColor", "updateWaterColorView", "updateWaterColorViewWithCurrentColor", "updateMarkPenView", "updateMarkPenViewWithCurrentColor", "updateNormalPenView", "updateNormalPenViewWithCurrentColor", "updateEraserView", "updateBackgroundView", "updatePictureToolView", "updateTextToolView", "createBrushFg", "Landroid/graphics/drawable/Drawable;", "colorInt", "createBrushBg", "createToolBg", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "createPanelImmerseIcon", "saveDoodle", "progressDialog", "showSaveProgressDialog", "hideSaveProgressDialog", "showProcessingDialog", "delay", "", "hideProcessingDialog", "onDestroyView", "onDestroy", "onConfigurationChanged", "newConfig", "clearViewAnimator", "onStop", "smallWindowAdapter", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "UnselectedAccessibilityDelegate", "DoodleViewWrapper", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleFragment extends Fragment implements CoroutineScope {
    public static final String KEY_EDIT_IMAGE_ID = "key_edit_image_id";
    public static final String KEY_IMAGE_NAME = "key_image_name";
    public static final String KEY_IMAGE_NULL = "key_image_null";
    public static final String KEY_PARAMS = "key_doodle_params";
    private static final int REQUEST_CODE_GALLERY = 0;
    private static final String TAG = "DoodleFragment";
    private View actionBarContainer;
    private View activeTool;
    protected View alphaBg;
    private boolean appGuideIsShowed;
    private View backgroundConfigContainer;
    private View backgroundDotConfigContainer;
    private View backgroundWhiteConfigContainer;
    private IBrushConfig brushConfig;
    private View brushConfigContainer;
    private View brushEraser;
    private View brushEraserTouch;
    private View brushMarkPen;
    private View brushMarkPenTouch;
    private View brushNormal;
    private View brushNormalTouch;
    private View brushPencil;
    private View brushPencilTouch;
    private View brushWaterColor;
    private View brushWaterColorTouch;
    private View btnDone;
    private View btnImmerse;
    private CardView btnPanelImmerse;
    private ImageView btnPanelImmerseIcon;
    private View btnRedo;
    protected View btnUnImmerse;
    private View btnUndo;
    private IColorBtn colorBtn;
    private View colorView;
    private ViewPropertyAnimator colorViewAnimate;
    private int displayWidth;
    private IDoodle doodle;
    protected FrameLayout doodleContainer;
    private DoodleEditTextDialog doodleEditTextDialog;
    private View doodlePanelContainer;
    private DoodleParams doodleParams;
    private DoodleView doodleView;
    private View elementEraser;
    private View elementEraserContainer;
    private ViewPropertyAnimator elementEraserContainerAnimate;
    private TextView elementTipText;
    private View elementTipView;
    private String imagePath;
    private boolean isChoosingImage;
    private boolean isInImmerseMode;
    private boolean isPanelInImmerseMode;
    private boolean isSaved;
    private boolean isShowBackgroundConfig;
    private ProgressDialog loadDataDialog;
    private Context mContext;
    private boolean mIsInit;
    private View mRootView;
    private DoodleDocument myDoodleDocument;
    private IPalette palette;
    private View paletteContainer;
    private View pixelEraser;
    private View pixelEraserContainer;
    private ViewPropertyAnimator pixelEraserContainerAnimate;
    private TextView pixelTipText;
    private View pixelTipView;
    private ProgressDialog progressDialog;
    private boolean saveBeforeExit;
    private boolean selectedElementEraser;
    private Runnable showLoadingDialogTask;
    private ViewPropertyAnimator tipViewAnimate;
    private ViewPropertyAnimator tipViewAnimate1;
    private View toolBackground;
    private View toolBackgroundTouch;
    private View toolPanelContainer;
    private ViewPropertyAnimator toolPanelContainerAnimate;
    private View toolPicture;
    private View toolPictureTouch;
    private View toolText;
    private View toolTextTouch;
    private DoodleOnTouchGestureListener touchGestureListener;
    private final /* synthetic */ CoroutineScope $$delegate_0 = CoroutineScopeKt.MainScope();
    private ScreenSpec screenSpec = new ScreenSpec();
    private final UnselectedAccessibilityDelegate unselectedAccessibilityDelegate = new UnselectedAccessibilityDelegate();
    private String currentBackgroundType = "dot";

    /* JADX INFO: renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda16
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DoodleFragment.handler_delegate$lambda$0(this.f$0);
        }
    });

    /* JADX INFO: renamed from: saveTask$delegate, reason: from kotlin metadata */
    private final Lazy saveTask = LazyKt.lazy(new Function0() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda17
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DoodleFragment.saveTask_delegate$lambda$2(this.f$0);
        }
    });
    private final View.OnClickListener toolClickListener = new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda18
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DoodleFragment.toolClickListener$lambda$7(this.f$0, view);
        }
    };
    private final TransitionListener actionBarDismissListener = new TransitionListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$actionBarDismissListener$1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object toTag) {
            super.onComplete(toTag);
            View actionBarContainer = this.this$0.getActionBarContainer();
            if (actionBarContainer != null) {
                actionBarContainer.setVisibility(8);
            }
        }
    };
    private final TransitionListener toolPanelDismissListener = new TransitionListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$toolPanelDismissListener$1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object toTag) {
            super.onComplete(toTag);
            View toolPanelContainer = this.this$0.getToolPanelContainer();
            if (toolPanelContainer != null) {
                toolPanelContainer.setVisibility(8);
            }
        }
    };

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    public final DoodleView getDoodleView() {
        return this.doodleView;
    }

    public final void setDoodleView(DoodleView doodleView) {
        this.doodleView = doodleView;
    }

    public final boolean getSaveBeforeExit() {
        return this.saveBeforeExit;
    }

    public final void setSaveBeforeExit(boolean z) {
        this.saveBeforeExit = z;
    }

    public final DoodleDocument getMyDoodleDocument() {
        return this.myDoodleDocument;
    }

    public final void setMyDoodleDocument(DoodleDocument doodleDocument) {
        this.myDoodleDocument = doodleDocument;
    }

    public final ScreenSpec getScreenSpec() {
        return this.screenSpec;
    }

    public final void setScreenSpec(ScreenSpec screenSpec) {
        Intrinsics.checkNotNullParameter(screenSpec, "<set-?>");
        this.screenSpec = screenSpec;
    }

    public final int getDisplayWidth() {
        return this.displayWidth;
    }

    public final void setDisplayWidth(int i) {
        this.displayWidth = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MainHandler<DoodleFragment> getHandler() {
        return (MainHandler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MainHandler handler_delegate$lambda$0(DoodleFragment doodleFragment) {
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        return new MainHandler(mainLooper, new WeakReference(doodleFragment));
    }

    private final Runnable getSaveTask() {
        return (Runnable) this.saveTask.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Runnable saveTask_delegate$lambda$2(final DoodleFragment doodleFragment) {
        return new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.saveDoodle();
            }
        };
    }

    private final void announceToolSelected(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.notes.doodle.feature.DoodleFragment.announceToolSelected.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setSelected(true);
                info.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", host.getContext().getString(R.string.button_talkback));
            }
        });
        resetOtherToolsAccessibility(view);
    }

    private final void resetOtherToolsAccessibility(View selectedView) {
        Integer[] numArr = {Integer.valueOf(R.id.brush_pencil_touch), Integer.valueOf(R.id.brush_normal_touch), Integer.valueOf(R.id.brush_markpen_touch), Integer.valueOf(R.id.brush_watercolor_touch), Integer.valueOf(R.id.brush_eraser_touch), Integer.valueOf(R.id.tool_background_touch)};
        for (int i = 0; i < 6; i++) {
            View viewFindViewById = selectedView.getRootView().findViewById(numArr[i].intValue());
            if (viewFindViewById != null) {
                if (Intrinsics.areEqual(viewFindViewById, selectedView)) {
                    viewFindViewById = null;
                }
                if (viewFindViewById != null) {
                    viewFindViewById.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.notes.doodle.feature.DoodleFragment$resetOtherToolsAccessibility$1$2$1
                        @Override // android.view.View.AccessibilityDelegate
                        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                            Intrinsics.checkNotNullParameter(host, "host");
                            Intrinsics.checkNotNullParameter(info, "info");
                            super.onInitializeAccessibilityNodeInfo(host, info);
                            info.setSelected(false);
                            info.setCheckable(true);
                            info.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", host.getContext().getString(R.string.button_talkback));
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/miui/notes/doodle/feature/DoodleFragment$UnselectedAccessibilityDelegate;", "Landroid/view/View$AccessibilityDelegate;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onInitializeAccessibilityNodeInfo", "", "host", "Landroid/view/View;", Constants.JSON_FILTER_INFO, "Landroid/view/accessibility/AccessibilityNodeInfo;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class UnselectedAccessibilityDelegate extends View.AccessibilityDelegate {
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(info, "info");
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setCheckable(true);
            info.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", host.getContext().getString(R.string.button_talkback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static final void toolClickListener$lambda$7(DoodleFragment doodleFragment, View view) {
        Context context;
        IPalette iPalette;
        if (doodleFragment.isInImmerseMode && doodleFragment.isPanelInImmerseMode) {
            return;
        }
        DoodleView doodleView = doodleFragment.doodleView;
        View view2 = null;
        Boolean boolValueOf = doodleView != null ? Boolean.valueOf(doodleView.getMIsScrollingDoodle()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue() || (context = doodleFragment.getContext()) == null) {
            return;
        }
        if (doodleFragment.colorBtn != null && (iPalette = doodleFragment.palette) != null) {
            if (iPalette == null) {
                Intrinsics.throwUninitializedPropertyAccessException("palette");
                iPalette = null;
            }
            if (iPalette.getIsShow()) {
                IColorBtn iColorBtn = doodleFragment.colorBtn;
                if (iColorBtn == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                    iColorBtn = null;
                }
                iColorBtn.dismissPalette();
            }
        }
        switch (view.getId()) {
            case R.id.brush_eraser_touch /* 2131362092 */:
                View view3 = doodleFragment.elementEraserContainer;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                    view3 = null;
                }
                if (view3.getVisibility() != 0) {
                    NotesTextStat.reportDoodleClickEvent(NotesTextStat.ERASER_TOOL);
                    DoodlePen elementEraser = doodleFragment.selectedElementEraser ? DoodlePenManager.INSTANCE.getElementEraser() : DoodlePenManager.INSTANCE.getEraser();
                    IDoodle iDoodle = doodleFragment.doodle;
                    if (iDoodle == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle = null;
                    }
                    if (!Intrinsics.areEqual(iDoodle.getPen().getType(), elementEraser.getType())) {
                        IDoodle iDoodle2 = doodleFragment.doodle;
                        if (iDoodle2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                            iDoodle2 = null;
                        }
                        iDoodle2.setPen(elementEraser.copy());
                    }
                    IColorBtn iColorBtn2 = doodleFragment.colorBtn;
                    if (iColorBtn2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn2 = null;
                    }
                    IDoodle iDoodle3 = doodleFragment.doodle;
                    if (iDoodle3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle3 = null;
                    }
                    iColorBtn2.setColorInt(iDoodle3.getPen().getColorInt());
                    IPalette iPalette2 = doodleFragment.palette;
                    if (iPalette2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette2 = null;
                    }
                    IDoodle iDoodle4 = doodleFragment.doodle;
                    if (iDoodle4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle4 = null;
                    }
                    iPalette2.setColor(iDoodle4.getPen().getColorInt());
                    doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                    View view4 = doodleFragment.pixelEraserContainer;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
                        view4 = null;
                    }
                    view4.findViewById(R.id.pixel_eraser_entry).setSelected(!doodleFragment.selectedElementEraser);
                    View view5 = doodleFragment.elementEraserContainer;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                        view5 = null;
                    }
                    view5.findViewById(R.id.element_eraser_entry).setSelected(doodleFragment.selectedElementEraser);
                    doodleFragment.toggleElementContainer();
                    View view6 = doodleFragment.brushEraser;
                    if (view6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
                        view6 = null;
                    }
                    doodleFragment.activeTool(context, view6);
                    View view7 = doodleFragment.brushEraserTouch;
                    if (view7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushEraserTouch");
                    } else {
                        view2 = view7;
                    }
                    doodleFragment.announceToolSelected(view2);
                }
                Unit unit = Unit.INSTANCE;
                break;
            case R.id.brush_markpen_touch /* 2131362094 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.BRUSH_PEN_TOOL);
                IDoodle iDoodle5 = doodleFragment.doodle;
                if (iDoodle5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle5 = null;
                }
                if (!Intrinsics.areEqual(iDoodle5.getPen().getType(), DoodlePen.TYPE_MARK_PEN)) {
                    IDoodle iDoodle6 = doodleFragment.doodle;
                    if (iDoodle6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle6 = null;
                    }
                    iDoodle6.setPen(DoodlePenManager.INSTANCE.getMarkPen().copy());
                    IColorBtn iColorBtn3 = doodleFragment.colorBtn;
                    if (iColorBtn3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn3 = null;
                    }
                    IDoodle iDoodle7 = doodleFragment.doodle;
                    if (iDoodle7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle7 = null;
                    }
                    iColorBtn3.setColorInt(iDoodle7.getPen().getColorInt());
                    IPalette iPalette3 = doodleFragment.palette;
                    if (iPalette3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette3 = null;
                    }
                    IDoodle iDoodle8 = doodleFragment.doodle;
                    if (iDoodle8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle8 = null;
                    }
                    iPalette3.setColor(iDoodle8.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn4 = doodleFragment.colorBtn;
                    if (iColorBtn4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn4 = null;
                    }
                    IDoodle iDoodle9 = doodleFragment.doodle;
                    if (iDoodle9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle9 = null;
                    }
                    iColorBtn4.setColorInt(iDoodle9.getPen().getColorInt());
                    IPalette iPalette4 = doodleFragment.palette;
                    if (iPalette4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette4 = null;
                    }
                    IDoodle iDoodle10 = doodleFragment.doodle;
                    if (iDoodle10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle10 = null;
                    }
                    iPalette4.setColor(iDoodle10.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view8 = doodleFragment.brushMarkPen;
                if (view8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
                    view8 = null;
                }
                doodleFragment.activeTool(context, view8);
                doodleFragment.addNewLayerBitmap();
                View view9 = doodleFragment.brushMarkPenTouch;
                if (view9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushMarkPenTouch");
                } else {
                    view2 = view9;
                }
                doodleFragment.announceToolSelected(view2);
                Unit unit2 = Unit.INSTANCE;
                break;
            case R.id.brush_normal_touch /* 2131362096 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.PEN_TOOL);
                IDoodle iDoodle11 = doodleFragment.doodle;
                if (iDoodle11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle11 = null;
                }
                if (!Intrinsics.areEqual(iDoodle11.getPen().getType(), DoodlePen.TYPE_NORMAL)) {
                    IDoodle iDoodle12 = doodleFragment.doodle;
                    if (iDoodle12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle12 = null;
                    }
                    iDoodle12.setPen(DoodlePenManager.INSTANCE.getNormal().copy());
                    IColorBtn iColorBtn5 = doodleFragment.colorBtn;
                    if (iColorBtn5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn5 = null;
                    }
                    IDoodle iDoodle13 = doodleFragment.doodle;
                    if (iDoodle13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle13 = null;
                    }
                    iColorBtn5.setColorInt(iDoodle13.getPen().getColorInt());
                    IPalette iPalette5 = doodleFragment.palette;
                    if (iPalette5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette5 = null;
                    }
                    IDoodle iDoodle14 = doodleFragment.doodle;
                    if (iDoodle14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle14 = null;
                    }
                    iPalette5.setColor(iDoodle14.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn6 = doodleFragment.colorBtn;
                    if (iColorBtn6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn6 = null;
                    }
                    IDoodle iDoodle15 = doodleFragment.doodle;
                    if (iDoodle15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle15 = null;
                    }
                    iColorBtn6.setColorInt(iDoodle15.getPen().getColorInt());
                    IPalette iPalette6 = doodleFragment.palette;
                    if (iPalette6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette6 = null;
                    }
                    IDoodle iDoodle16 = doodleFragment.doodle;
                    if (iDoodle16 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle16 = null;
                    }
                    iPalette6.setColor(iDoodle16.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view10 = doodleFragment.brushNormal;
                if (view10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
                    view10 = null;
                }
                doodleFragment.activeTool(context, view10);
                doodleFragment.addNewLayerBitmap();
                View view11 = doodleFragment.brushNormalTouch;
                if (view11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushNormalTouch");
                } else {
                    view2 = view11;
                }
                doodleFragment.announceToolSelected(view2);
                Unit unit3 = Unit.INSTANCE;
                break;
            case R.id.brush_pencil_touch /* 2131362099 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.PENCIL_TOOL);
                IDoodle iDoodle17 = doodleFragment.doodle;
                if (iDoodle17 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle17 = null;
                }
                if (!Intrinsics.areEqual(iDoodle17.getPen().getType(), DoodlePen.TYPE_PENCIL)) {
                    IDoodle iDoodle18 = doodleFragment.doodle;
                    if (iDoodle18 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle18 = null;
                    }
                    iDoodle18.setPen(DoodlePenManager.INSTANCE.getPencil().copy());
                    IColorBtn iColorBtn7 = doodleFragment.colorBtn;
                    if (iColorBtn7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn7 = null;
                    }
                    IDoodle iDoodle19 = doodleFragment.doodle;
                    if (iDoodle19 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle19 = null;
                    }
                    iColorBtn7.setColorInt(iDoodle19.getPen().getColorInt());
                    IPalette iPalette7 = doodleFragment.palette;
                    if (iPalette7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette7 = null;
                    }
                    IDoodle iDoodle20 = doodleFragment.doodle;
                    if (iDoodle20 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle20 = null;
                    }
                    iPalette7.setColor(iDoodle20.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn8 = doodleFragment.colorBtn;
                    if (iColorBtn8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn8 = null;
                    }
                    IDoodle iDoodle21 = doodleFragment.doodle;
                    if (iDoodle21 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle21 = null;
                    }
                    iColorBtn8.setColorInt(iDoodle21.getPen().getColorInt());
                    IPalette iPalette8 = doodleFragment.palette;
                    if (iPalette8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette8 = null;
                    }
                    IDoodle iDoodle22 = doodleFragment.doodle;
                    if (iDoodle22 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle22 = null;
                    }
                    iPalette8.setColor(iDoodle22.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view12 = doodleFragment.brushPencil;
                if (view12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
                    view12 = null;
                }
                doodleFragment.activeTool(context, view12);
                doodleFragment.addNewLayerBitmap();
                View view13 = doodleFragment.brushPencilTouch;
                if (view13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushPencilTouch");
                } else {
                    view2 = view13;
                }
                doodleFragment.announceToolSelected(view2);
                Unit unit4 = Unit.INSTANCE;
                break;
            case R.id.brush_watercolor_touch /* 2131362104 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.MARK_TOOL);
                IDoodle iDoodle23 = doodleFragment.doodle;
                if (iDoodle23 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle23 = null;
                }
                if (!Intrinsics.areEqual(iDoodle23.getPen().getType(), DoodlePen.TYPE_WATERCOLOR)) {
                    IDoodle iDoodle24 = doodleFragment.doodle;
                    if (iDoodle24 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle24 = null;
                    }
                    iDoodle24.setPen(DoodlePenManager.INSTANCE.getWatercolor().copy());
                    IColorBtn iColorBtn9 = doodleFragment.colorBtn;
                    if (iColorBtn9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn9 = null;
                    }
                    IDoodle iDoodle25 = doodleFragment.doodle;
                    if (iDoodle25 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle25 = null;
                    }
                    iColorBtn9.setColorInt(iDoodle25.getPen().getColorInt());
                    IPalette iPalette9 = doodleFragment.palette;
                    if (iPalette9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette9 = null;
                    }
                    IDoodle iDoodle26 = doodleFragment.doodle;
                    if (iDoodle26 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle26 = null;
                    }
                    iPalette9.setColor(iDoodle26.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn10 = doodleFragment.colorBtn;
                    if (iColorBtn10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn10 = null;
                    }
                    IDoodle iDoodle27 = doodleFragment.doodle;
                    if (iDoodle27 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle27 = null;
                    }
                    iColorBtn10.setColorInt(iDoodle27.getPen().getColorInt());
                    IPalette iPalette10 = doodleFragment.palette;
                    if (iPalette10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette10 = null;
                    }
                    IDoodle iDoodle28 = doodleFragment.doodle;
                    if (iDoodle28 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle28 = null;
                    }
                    iPalette10.setColor(iDoodle28.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view14 = doodleFragment.brushWaterColor;
                if (view14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
                    view14 = null;
                }
                doodleFragment.activeTool(context, view14);
                doodleFragment.addNewLayerBitmap();
                View view15 = doodleFragment.brushWaterColorTouch;
                if (view15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushWaterColorTouch");
                } else {
                    view2 = view15;
                }
                doodleFragment.announceToolSelected(view2);
                Unit unit5 = Unit.INSTANCE;
                break;
            case R.id.element_eraser_entry /* 2131362483 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.ERASER_TOOL_STROKE);
                IDoodle iDoodle29 = doodleFragment.doodle;
                if (iDoodle29 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle29 = null;
                }
                iDoodle29.setPen(DoodlePenManager.INSTANCE.getElementEraser().copy());
                view.setSelected(true);
                View view16 = doodleFragment.pixelEraserContainer;
                if (view16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
                } else {
                    view2 = view16;
                }
                view2.findViewById(R.id.pixel_eraser_entry).setSelected(false);
                doodleFragment.selectedElementEraser = true;
                Unit unit6 = Unit.INSTANCE;
                break;
            case R.id.pixel_eraser_entry /* 2131363204 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.ERASER_TOOL_PX);
                IDoodle iDoodle30 = doodleFragment.doodle;
                if (iDoodle30 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle30 = null;
                }
                iDoodle30.setPen(DoodlePenManager.INSTANCE.getEraser().copy());
                view.setSelected(true);
                View view17 = doodleFragment.elementEraserContainer;
                if (view17 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                } else {
                    view2 = view17;
                }
                view2.findViewById(R.id.element_eraser_entry).setSelected(false);
                doodleFragment.selectedElementEraser = false;
                Unit unit7 = Unit.INSTANCE;
                break;
            case R.id.tool_background_touch /* 2131363654 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.TEXTURE_TOOL);
                IColorBtn iColorBtn11 = doodleFragment.colorBtn;
                if (iColorBtn11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                    iColorBtn11 = null;
                }
                IDoodle iDoodle31 = doodleFragment.doodle;
                if (iDoodle31 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle31 = null;
                }
                iColorBtn11.setColorInt(iDoodle31.getPen().getColorInt());
                IPalette iPalette11 = doodleFragment.palette;
                if (iPalette11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("palette");
                    iPalette11 = null;
                }
                IDoodle iDoodle32 = doodleFragment.doodle;
                if (iDoodle32 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle32 = null;
                }
                iPalette11.setColor(iDoodle32.getPen().getColorInt());
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view18 = doodleFragment.toolBackground;
                if (view18 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolBackground");
                    view18 = null;
                }
                doodleFragment.activeTool(context, view18);
                View view19 = doodleFragment.toolBackgroundTouch;
                if (view19 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolBackgroundTouch");
                } else {
                    view2 = view19;
                }
                doodleFragment.announceToolSelected(view2);
                Unit unit8 = Unit.INSTANCE;
                break;
            case R.id.tool_picture_touch /* 2131363660 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.ADD_IMAGE);
                IDoodle iDoodle33 = doodleFragment.doodle;
                if (iDoodle33 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle33 = null;
                }
                if (!Intrinsics.areEqual(iDoodle33.getPen().getType(), DoodlePen.TYPE_BITMAP)) {
                    IDoodle iDoodle34 = doodleFragment.doodle;
                    if (iDoodle34 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle34 = null;
                    }
                    iDoodle34.setPen(DoodlePenManager.INSTANCE.getBitmap().copy());
                    IColorBtn iColorBtn12 = doodleFragment.colorBtn;
                    if (iColorBtn12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn12 = null;
                    }
                    IDoodle iDoodle35 = doodleFragment.doodle;
                    if (iDoodle35 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle35 = null;
                    }
                    iColorBtn12.setColorInt(iDoodle35.getPen().getColorInt());
                    IPalette iPalette12 = doodleFragment.palette;
                    if (iPalette12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette12 = null;
                    }
                    IDoodle iDoodle36 = doodleFragment.doodle;
                    if (iDoodle36 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle36 = null;
                    }
                    iPalette12.setColor(iDoodle36.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn13 = doodleFragment.colorBtn;
                    if (iColorBtn13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn13 = null;
                    }
                    IDoodle iDoodle37 = doodleFragment.doodle;
                    if (iDoodle37 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle37 = null;
                    }
                    iColorBtn13.setColorInt(iDoodle37.getPen().getColorInt());
                    IPalette iPalette13 = doodleFragment.palette;
                    if (iPalette13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette13 = null;
                    }
                    IDoodle iDoodle38 = doodleFragment.doodle;
                    if (iDoodle38 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle38 = null;
                    }
                    iPalette13.setColor(iDoodle38.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view20 = doodleFragment.toolPicture;
                if (view20 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolPicture");
                } else {
                    view2 = view20;
                }
                doodleFragment.activeTool(context, view2);
                Unit unit9 = Unit.INSTANCE;
                break;
            case R.id.tool_text_touch /* 2131363662 */:
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.ADD_TEXT);
                IDoodle iDoodle39 = doodleFragment.doodle;
                if (iDoodle39 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle39 = null;
                }
                if (!Intrinsics.areEqual(iDoodle39.getPen().getType(), DoodlePen.TYPE_TEXT)) {
                    IDoodle iDoodle40 = doodleFragment.doodle;
                    if (iDoodle40 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle40 = null;
                    }
                    iDoodle40.setPen(DoodlePenManager.INSTANCE.getText().copy());
                    IColorBtn iColorBtn14 = doodleFragment.colorBtn;
                    if (iColorBtn14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn14 = null;
                    }
                    IDoodle iDoodle41 = doodleFragment.doodle;
                    if (iDoodle41 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle41 = null;
                    }
                    iColorBtn14.setColorInt(iDoodle41.getPen().getColorInt());
                    IPalette iPalette14 = doodleFragment.palette;
                    if (iPalette14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette14 = null;
                    }
                    IDoodle iDoodle42 = doodleFragment.doodle;
                    if (iDoodle42 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle42 = null;
                    }
                    iPalette14.setColor(iDoodle42.getPen().getColorInt());
                } else {
                    IColorBtn iColorBtn15 = doodleFragment.colorBtn;
                    if (iColorBtn15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn15 = null;
                    }
                    IDoodle iDoodle43 = doodleFragment.doodle;
                    if (iDoodle43 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle43 = null;
                    }
                    iColorBtn15.setColorInt(iDoodle43.getPen().getColorInt());
                    IPalette iPalette15 = doodleFragment.palette;
                    if (iPalette15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("palette");
                        iPalette15 = null;
                    }
                    IDoodle iDoodle44 = doodleFragment.doodle;
                    if (iDoodle44 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle44 = null;
                    }
                    iPalette15.setColor(iDoodle44.getPen().getColorInt());
                }
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
                View view21 = doodleFragment.toolText;
                if (view21 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolText");
                } else {
                    view2 = view21;
                }
                doodleFragment.activeTool(context, view2);
                Unit unit10 = Unit.INSTANCE;
                break;
            default:
                Unit unit11 = Unit.INSTANCE;
                break;
        }
        Unit unit12 = Unit.INSTANCE;
        Unit unit13 = Unit.INSTANCE;
    }

    private final void addNewLayerBitmap() {
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.addNewLayerBitmap();
        }
    }

    private final void showBrushConfigGuide(View view) {
        float dimension;
        Context context = getContext();
        if (context != null) {
            GuidePopupWindow guidePopupWindow = new GuidePopupWindow(context);
            guidePopupWindow.setArrowMode(18);
            guidePopupWindow.setGuideText(R.string.doodle_guide_brush_config_hint);
            if (RomUtils.INSTANCE.isFoldDevice()) {
                dimension = context.getResources().getDimension(R.dimen.fold_brush_config_guide_x);
            } else {
                dimension = context.getResources().getDimension(R.dimen.phone_brush_config_guide_x);
            }
            int i = (int) dimension;
            int dimension2 = (int) context.getResources().getDimension(R.dimen.brush_config_guide_y);
            guidePopupWindow.setArrowMode(18);
            if (!Utils.INSTANCE.isRTL()) {
                i = -i;
            }
            guidePopupWindow.show(view, i, dimension2, false);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        IPalette iPalette;
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        if (!RomUtils.INSTANCE.isPadMode() && this.mIsInit) {
            switchImmerseMode(this.isInImmerseMode);
        }
        View view = null;
        if (this.colorBtn != null && (iPalette = this.palette) != null) {
            if (iPalette == null) {
                Intrinsics.throwUninitializedPropertyAccessException("palette");
                iPalette = null;
            }
            if (iPalette.getIsShow()) {
                getHandler().postDelayed(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        DoodleFragment.onResponsiveLayout$lambda$9(this.f$0);
                    }
                }, 200L);
                IPalette iPalette2 = this.palette;
                if (iPalette2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("palette");
                    iPalette2 = null;
                }
                if (iPalette2.getIsShow()) {
                    IColorBtn iColorBtn = this.colorBtn;
                    if (iColorBtn == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                        iColorBtn = null;
                    }
                    iColorBtn.dismissPalette();
                }
            }
        }
        IBrushConfig iBrushConfig = this.brushConfig;
        if (iBrushConfig != null && iBrushConfig != null) {
            if (iBrushConfig == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                iBrushConfig = null;
            }
            if (iBrushConfig.getIsShow()) {
                IBrushConfig iBrushConfig2 = this.brushConfig;
                if (iBrushConfig2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    iBrushConfig2 = null;
                }
                iBrushConfig2.dismiss();
                View view2 = this.doodlePanelContainer;
                if (view2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                    view2 = null;
                }
                view2.setOnClickListener(null);
                View view3 = this.doodlePanelContainer;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                } else {
                    view = view3;
                }
                view.setClickable(false);
            }
        }
        resetTranslation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResponsiveLayout$lambda$9(DoodleFragment doodleFragment) {
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null) {
            doodleView.setShowConfigPopupWindow(false);
        }
    }

    private final void toggleElementContainer() {
        View view = this.elementEraserContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view = null;
        }
        this.elementEraserContainerAnimate = view.animate();
        View view3 = this.pixelEraserContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view3 = null;
        }
        this.pixelEraserContainerAnimate = view3.animate();
        View view4 = this.elementEraserContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view4 = null;
        }
        if (view4.getVisibility() == 0) {
            View view5 = this.elementEraserContainer;
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view5 = null;
            }
            view5.setAlpha(1.0f);
            ViewPropertyAnimator viewPropertyAnimator = this.elementEraserContainerAnimate;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.setDuration(10L).alpha(0.0f).withEndAction(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        DoodleFragment.toggleElementContainer$lambda$11$lambda$10(this.f$0);
                    }
                }).start();
            }
            View view6 = this.pixelEraserContainer;
            if (view6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            } else {
                view2 = view6;
            }
            view2.setAlpha(1.0f);
            ViewPropertyAnimator viewPropertyAnimator2 = this.pixelEraserContainerAnimate;
            if (viewPropertyAnimator2 != null) {
                viewPropertyAnimator2.setDuration(10L).alpha(0.0f).withEndAction(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        DoodleFragment.toggleElementContainer$lambda$13$lambda$12(this.f$0);
                    }
                }).start();
                return;
            }
            return;
        }
        View view7 = this.elementEraserContainer;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view7 = null;
        }
        view7.setAlpha(0.0f);
        View view8 = this.elementEraserContainer;
        if (view8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view8 = null;
        }
        view8.setVisibility(0);
        ViewPropertyAnimator viewPropertyAnimator3 = this.elementEraserContainerAnimate;
        if (viewPropertyAnimator3 != null) {
            viewPropertyAnimator3.setDuration(10L).alpha(1.0f).setListener(null).start();
        }
        View view9 = this.pixelEraserContainer;
        if (view9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view9 = null;
        }
        view9.setAlpha(0.0f);
        View view10 = this.pixelEraserContainer;
        if (view10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view10 = null;
        }
        view10.setVisibility(0);
        ViewPropertyAnimator viewPropertyAnimator4 = this.pixelEraserContainerAnimate;
        if (viewPropertyAnimator4 != null) {
            viewPropertyAnimator4.setDuration(10L).alpha(1.0f).setListener(null).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleElementContainer$lambda$11$lambda$10(DoodleFragment doodleFragment) {
        View view = doodleFragment.elementEraserContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view = null;
        }
        view.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleElementContainer$lambda$13$lambda$12(DoodleFragment doodleFragment) {
        View view = doodleFragment.pixelEraserContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view = null;
        }
        view.setVisibility(4);
    }

    protected final View getActionBarContainer() {
        return this.actionBarContainer;
    }

    protected final void setActionBarContainer(View view) {
        this.actionBarContainer = view;
    }

    protected final FrameLayout getDoodleContainer() {
        FrameLayout frameLayout = this.doodleContainer;
        if (frameLayout != null) {
            return frameLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("doodleContainer");
        return null;
    }

    protected final void setDoodleContainer(FrameLayout frameLayout) {
        Intrinsics.checkNotNullParameter(frameLayout, "<set-?>");
        this.doodleContainer = frameLayout;
    }

    protected final View getMRootView() {
        return this.mRootView;
    }

    protected final void setMRootView(View view) {
        this.mRootView = view;
    }

    protected final View getBtnUndo() {
        return this.btnUndo;
    }

    protected final void setBtnUndo(View view) {
        this.btnUndo = view;
    }

    protected final View getBtnRedo() {
        return this.btnRedo;
    }

    protected final void setBtnRedo(View view) {
        this.btnRedo = view;
    }

    protected final View getBtnImmerse() {
        return this.btnImmerse;
    }

    protected final void setBtnImmerse(View view) {
        this.btnImmerse = view;
    }

    protected final View getBtnDone() {
        return this.btnDone;
    }

    protected final void setBtnDone(View view) {
        this.btnDone = view;
    }

    protected final View getBtnUnImmerse() {
        View view = this.btnUnImmerse;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("btnUnImmerse");
        return null;
    }

    protected final void setBtnUnImmerse(View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.btnUnImmerse = view;
    }

    protected final CardView getBtnPanelImmerse() {
        return this.btnPanelImmerse;
    }

    protected final void setBtnPanelImmerse(CardView cardView) {
        this.btnPanelImmerse = cardView;
    }

    protected final View getToolPanelContainer() {
        return this.toolPanelContainer;
    }

    protected final void setToolPanelContainer(View view) {
        this.toolPanelContainer = view;
    }

    protected final View getColorView() {
        return this.colorView;
    }

    protected final void setColorView(View view) {
        this.colorView = view;
    }

    protected final View getPaletteContainer() {
        return this.paletteContainer;
    }

    protected final void setPaletteContainer(View view) {
        this.paletteContainer = view;
    }

    protected final View getBrushConfigContainer() {
        return this.brushConfigContainer;
    }

    protected final void setBrushConfigContainer(View view) {
        this.brushConfigContainer = view;
    }

    protected final View getAlphaBg() {
        View view = this.alphaBg;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
        return null;
    }

    protected final void setAlphaBg(View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.alphaBg = view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean dismissConfig() {
        getHandler().postDelayed(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment.dismissConfig$lambda$16(this.f$0);
            }
        }, 200L);
        IPalette iPalette = this.palette;
        View view = null;
        if (iPalette == null) {
            Intrinsics.throwUninitializedPropertyAccessException("palette");
            iPalette = null;
        }
        if (iPalette.getIsShow()) {
            IColorBtn iColorBtn = this.colorBtn;
            if (iColorBtn == null) {
                Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                iColorBtn = null;
            }
            iColorBtn.dismissPalette();
        }
        IBrushConfig iBrushConfig = this.brushConfig;
        if (iBrushConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
            iBrushConfig = null;
        }
        if (iBrushConfig.getIsShow()) {
            IBrushConfig iBrushConfig2 = this.brushConfig;
            if (iBrushConfig2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                iBrushConfig2 = null;
            }
            iBrushConfig2.dismiss();
            View view2 = this.doodlePanelContainer;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                view2 = null;
            }
            view2.setOnClickListener(null);
            View view3 = this.doodlePanelContainer;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
            } else {
                view = view3;
            }
            view.setClickable(false);
        }
        if (!getIsShowBackgroundConfig()) {
            return false;
        }
        hideBackgroundContainer();
        setPanelImmerseState(this.isPanelInImmerseMode);
        Context context = getContext();
        if (context == null) {
            return true;
        }
        activeTool(context, getPaintView());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dismissConfig$lambda$16(DoodleFragment doodleFragment) {
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null) {
            doodleView.setShowConfigPopupWindow(false);
        }
    }

    private final View getPaintView() {
        IDoodle iDoodle = this.doodle;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        DoodlePen pen = iDoodle.getPen();
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getPencil())) {
            View view = this.brushPencil;
            if (view != null) {
                return view;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
            return null;
        }
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getNormal())) {
            View view2 = this.brushNormal;
            if (view2 != null) {
                return view2;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
            return null;
        }
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getMarkPen())) {
            View view3 = this.brushMarkPen;
            if (view3 != null) {
                return view3;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
            return null;
        }
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getWatercolor())) {
            View view4 = this.brushWaterColor;
            if (view4 != null) {
                return view4;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
            return null;
        }
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getEraser())) {
            toggleElementContainer();
            View view5 = this.brushEraser;
            if (view5 != null) {
                return view5;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
            return null;
        }
        if (Intrinsics.areEqual(pen, DoodlePenManager.INSTANCE.getElementEraser())) {
            toggleElementContainer();
            View view6 = this.brushEraser;
            if (view6 != null) {
                return view6;
            }
            Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
            return null;
        }
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle2 = null;
        }
        iDoodle2.setPen(DoodlePenManager.INSTANCE.getPencil());
        IColorBtn iColorBtn = this.colorBtn;
        if (iColorBtn == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
            iColorBtn = null;
        }
        IDoodle iDoodle3 = this.doodle;
        if (iDoodle3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle3 = null;
        }
        iColorBtn.setColorInt(iDoodle3.getPen().getColorInt());
        IPalette iPalette = this.palette;
        if (iPalette == null) {
            Intrinsics.throwUninitializedPropertyAccessException("palette");
            iPalette = null;
        }
        IDoodle iDoodle4 = this.doodle;
        if (iDoodle4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle4 = null;
        }
        iPalette.setColor(iDoodle4.getPen().getColorInt());
        View view7 = this.brushPencil;
        if (view7 != null) {
            return view7;
        }
        Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
        return null;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = requireContext();
        DisplayUtils displayUtils = DisplayUtils.INSTANCE;
        Context context = this.mContext;
        Intrinsics.checkNotNull(context);
        this.displayWidth = displayUtils.getRealWidth(context);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback() { // from class: com.miui.notes.doodle.feature.DoodleFragment.onAttach.1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Logger.INSTANCE.d(DoodleFragment.TAG, "onBackPressed");
                DoodleFragment.this.saveDoodle();
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.DONE_BACK_SAVE);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.INSTANCE.i("DoodleLoadTest", "onActivityCreated");
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            this.screenSpec.screenMode = 4103;
            this.screenSpec.type = getResponsiveState().getType();
            Bundle extras = activity.getIntent().getExtras();
            DoodleParams doodleParams = null;
            DoodleParams doodleParams2 = extras != null ? (DoodleParams) extras.getParcelable(KEY_PARAMS) : null;
            if (doodleParams2 == null) {
                Logger.INSTANCE.e(TAG, "mDoodleParams is null!");
                activity.finish();
                return;
            }
            activity.setIntent(null);
            this.doodleParams = doodleParams2;
            if (doodleParams2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
            } else {
                doodleParams = doodleParams2;
            }
            this.imagePath = doodleParams.getImagePath();
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            if (this.imagePath == null) {
                Logger.INSTANCE.e(TAG, "mImagePath is null!");
            }
            final View view = getView();
            if (view != null) {
                initLayout(view);
                getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        DoodleFragment.onActivityCreated$lambda$20$lambda$19$lambda$18(this.f$0, activity, view, savedInstanceState, objectRef);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$20$lambda$19$lambda$18(DoodleFragment doodleFragment, FragmentActivity fragmentActivity, View view, Bundle bundle, Ref.ObjectRef objectRef) {
        doodleFragment.initDoodleView(fragmentActivity, doodleFragment.getDoodleContainer());
        doodleFragment.initToolPanel(fragmentActivity, view, bundle);
        if (doodleFragment.isAdded()) {
            Context contextRequireContext = doodleFragment.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            doodleFragment.showProcessingDialog(contextRequireContext, 500L);
            BuildersKt__Builders_commonKt.launch$default(doodleFragment, Dispatchers.getIO(), null, new DoodleFragment$onActivityCreated$1$1$1$1(bundle, doodleFragment, objectRef, fragmentActivity, null), 2, null);
        }
        doodleFragment.mIsInit = true;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.doodle_layout, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.saveTempDoodleFile(outState);
        }
        IDoodle iDoodle = this.doodle;
        IDoodle iDoodle2 = null;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        outState.putString("selected_pen_type", iDoodle.getPen().getType());
        IDoodle iDoodle3 = this.doodle;
        if (iDoodle3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle3 = null;
        }
        outState.putInt("pen_color", iDoodle3.getPen().getColorInt());
        IDoodle iDoodle4 = this.doodle;
        if (iDoodle4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle4 = null;
        }
        outState.putFloat("pen_alpha", iDoodle4.getPen().getAlpha());
        IDoodle iDoodle5 = this.doodle;
        if (iDoodle5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
        } else {
            iDoodle2 = iDoodle5;
        }
        outState.putInt("pen_stroke_level", iDoodle2.getPen().getStrokeLevel());
        outState.putBoolean("selected_element_eraser", this.selectedElementEraser);
        outState.putString("background_type", this.currentBackgroundType);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.INSTANCE.d(TAG, "onStart: ");
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        if (!sharedPreferenceUtil.hasShowGuideDialog(contextRequireContext)) {
            AppGuideDialog appGuideDialog = new AppGuideDialog(this.mContext, 2131951646);
            if (!this.appGuideIsShowed) {
                this.appGuideIsShowed = true;
                appGuideDialog.show();
                appGuideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda19
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        DoodleFragment.onStart$lambda$21(this.f$0, dialogInterface);
                    }
                });
            }
        }
        this.isChoosingImage = false;
        if (this.doodle != null) {
            getHandler().removeCallbacks(getSaveTask());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStart$lambda$21(DoodleFragment doodleFragment, DialogInterface dialogInterface) {
        if (doodleFragment.isAdded()) {
            SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.INSTANCE;
            Context contextRequireContext = doodleFragment.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            sharedPreferenceUtil.setShowGuideDialog(contextRequireContext);
            View view = doodleFragment.brushPencil;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
                view = null;
            }
            doodleFragment.showBrushConfigGuide(view);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        smallWindowAdapter();
        Logger.INSTANCE.d(TAG, "onResume: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoodleDocument loadDoodleDocFromFile(String imagePath, boolean fromSnapshot) throws Throwable {
        Bitmap bitmapCreateBitmap;
        String str;
        DoodleDocument doodleDocument;
        int iMax;
        Window window;
        View decorView;
        DocumentInfo documentInfo;
        File file;
        DocumentInfo documentInfo2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean editImage = false;
        IDoodle iDoodle = null;
        if (!TextUtils.isEmpty(imagePath) && DocumentReader.INSTANCE.isDoodleFile(imagePath)) {
            if (!fromSnapshot) {
                IDoodle iDoodle2 = this.doodle;
                if (iDoodle2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle2 = null;
                }
                iDoodle2.setCreateNewDoodle(false);
                File file2 = new File(Utils.INSTANCE.getDoodleCacheDir(), "doodle_snapshot_" + System.currentTimeMillis());
                Utils utils = Utils.INSTANCE;
                Intrinsics.checkNotNull(imagePath);
                String absolutePath = file2.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                file = utils.copyFile(imagePath, absolutePath);
                DoodleView doodleView = this.doodleView;
                if (doodleView != null) {
                    doodleView.setTempDoodleFilePath(file != null ? file.getAbsolutePath() : null);
                }
            } else {
                file = new File(imagePath);
            }
            Intrinsics.checkNotNull(file);
            DocumentReader documentReader = new DocumentReader(file);
            Context context = getContext();
            if (context != null) {
                IDoodle iDoodle3 = this.doodle;
                if (iDoodle3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle3 = null;
                }
                doodleDocument = documentReader.read(context, iDoodle3, false);
            } else {
                doodleDocument = null;
            }
            if (!fromSnapshot) {
                DoodleParams doodleParams = this.doodleParams;
                if (doodleParams == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
                    doodleParams = null;
                }
                if (doodleParams.getSavePath() != null && doodleDocument != null) {
                    DoodleParams doodleParams2 = this.doodleParams;
                    if (doodleParams2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
                        doodleParams2 = null;
                    }
                    doodleDocument.setEditImageId(doodleParams2.getSavePath());
                }
            }
            if (doodleDocument != null && (documentInfo2 = doodleDocument.getDocumentInfo()) != null) {
                editImage = documentInfo2.getEditImage();
            }
            IDoodle iDoodle4 = this.doodle;
            if (iDoodle4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            } else {
                iDoodle = iDoodle4;
            }
            iDoodle.setClipImageOnSave(!editImage);
        } else {
            IDoodle iDoodle5 = this.doodle;
            if (iDoodle5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle5 = null;
            }
            iDoodle5.setCreateNewDoodle(true);
            FragmentActivity activity = getActivity();
            View viewFindViewById = (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null) ? null : decorView.findViewById(android.R.id.content);
            try {
                IDoodle iDoodle6 = this.doodle;
                if (iDoodle6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle6 = null;
                }
                iDoodle6.setClipImageOnSave(false);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, options);
                options.inSampleSize = MathKt.roundToInt(options.outWidth / 1080.0f);
                options.inJustDecodeBounds = false;
                bitmapCreateBitmap = BitmapFactory.decodeFile(imagePath, options);
            } catch (Exception e) {
                e.printStackTrace();
                bitmapCreateBitmap = null;
            }
            if (bitmapCreateBitmap == null) {
                int i = 2;
                if (viewFindViewById != null) {
                    int iMax2 = Math.max(viewFindViewById.getWidth(), 2);
                    iMax = Math.max(viewFindViewById.getHeight(), 2);
                    i = iMax2;
                } else {
                    iMax = 2;
                }
                bitmapCreateBitmap = Bitmap.createBitmap(i, iMax, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.eraseColor(0);
                IDoodle iDoodle7 = this.doodle;
                if (iDoodle7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                } else {
                    iDoodle = iDoodle7;
                }
                iDoodle.setClipImageOnSave(true);
                str = "";
            } else {
                String strValueOf = String.valueOf(bitmapCreateBitmap.hashCode());
                File file3 = new File(Utils.INSTANCE.getDoodleCacheDir(), strValueOf);
                DoodleView doodleView2 = this.doodleView;
                if (doodleView2 != null) {
                    Utils utils2 = Utils.INSTANCE;
                    Intrinsics.checkNotNull(imagePath);
                    String absolutePath2 = file3.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
                    doodleView2.setTempDoodleFilePath(String.valueOf(utils2.copyFile(imagePath, absolutePath2)));
                }
                if (TextUtils.isEmpty(file3.getAbsolutePath()) || !file3.exists()) {
                    str = strValueOf;
                } else {
                    Logger.INSTANCE.d(TAG, "loadDoodleDocFromFile cacheBgKey  " + strValueOf);
                    str = strValueOf;
                    editImage = true;
                }
            }
            Intrinsics.checkNotNull(bitmapCreateBitmap);
            DoodleDocument doodleDocument2 = new DoodleDocument(bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), false, 4, null);
            doodleDocument2.setBg(bitmapCreateBitmap);
            if (editImage) {
                doodleDocument2.cacheBgImageToFileFromFile(str);
                DocumentInfo documentInfo3 = doodleDocument2.getDocumentInfo();
                if (documentInfo3 != null) {
                    documentInfo3.setEditImage(true);
                }
                doodleDocument2.setMIsNeedCacheBgImageToFile(true);
            }
            doodleDocument = doodleDocument2;
        }
        Logger.INSTANCE.d(TAG, "loadDoodleDocFromFile  cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
        if (doodleDocument != null && (documentInfo = doodleDocument.getDocumentInfo()) != null) {
            documentInfo.setBackgroundType(this.currentBackgroundType);
        }
        return doodleDocument;
    }

    public void initLayout(View rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        this.mRootView = rootView;
        smallWindowAdapter();
        this.actionBarContainer = rootView.findViewById(R.id.action_bar_container);
        View viewFindViewById = rootView.findViewById(R.id.undo);
        this.btnUndo = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setAccessibilityTraversalBefore(R.id.redo);
        }
        View view = this.btnUndo;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DoodleFragment.initLayout$lambda$23(this.f$0, view2);
                }
            });
        }
        View view2 = this.btnUndo;
        if (view2 != null) {
            view2.setEnabled(false);
        }
        Folme.useAt(this.btnUndo).touch().handleTouchOf(this.btnUndo, new AnimConfig[0]);
        UIUtils.INSTANCE.setFolmeHoverEffect(this.btnUndo);
        View viewFindViewById2 = rootView.findViewById(R.id.redo);
        this.btnRedo = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setAccessibilityTraversalBefore(R.id.immerse);
        }
        View view3 = this.btnRedo;
        if (view3 != null) {
            view3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view4) {
                    DoodleFragment.initLayout$lambda$24(this.f$0, view4);
                }
            });
        }
        View view4 = this.btnRedo;
        if (view4 != null) {
            view4.setEnabled(false);
        }
        Folme.useAt(this.btnRedo).touch().handleTouchOf(this.btnRedo, new AnimConfig[0]);
        UIUtils.INSTANCE.setFolmeHoverEffect(this.btnRedo);
        View viewFindViewById3 = rootView.findViewById(R.id.immerse);
        this.btnImmerse = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setAccessibilityTraversalBefore(R.id.done);
        }
        View view5 = this.btnImmerse;
        if (view5 != null) {
            view5.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda22
                @Override // android.view.View.OnClickListener
                public final void onClick(View view6) {
                    DoodleFragment.initLayout$lambda$25(this.f$0, view6);
                }
            });
        }
        Folme.useAt(this.btnImmerse).touch().handleTouchOf(this.btnImmerse, new AnimConfig[0]);
        UIUtils.INSTANCE.setFolmeHoverEffect(this.btnImmerse);
        View viewFindViewById4 = rootView.findViewById(R.id.unimmerse);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        setBtnUnImmerse(viewFindViewById4);
        getBtnUnImmerse().setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view6) {
                DoodleFragment.initLayout$lambda$26(this.f$0, view6);
            }
        });
        Folme.useAt(getBtnUnImmerse()).touch().handleTouchOf(getBtnUnImmerse(), new AnimConfig[0]);
        UIUtils.INSTANCE.setFolmeHoverEffect(getBtnUnImmerse());
        View viewFindViewById5 = rootView.findViewById(R.id.done);
        this.btnDone = viewFindViewById5;
        if (viewFindViewById5 != null) {
            viewFindViewById5.setAccessibilityTraversalBefore(R.id.palette_entry);
        }
        View view6 = this.btnDone;
        if (view6 != null) {
            view6.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda25
                @Override // android.view.View.OnClickListener
                public final void onClick(View view7) {
                    DoodleFragment.initLayout$lambda$27(this.f$0, view7);
                }
            });
        }
        Folme.useAt(this.btnDone).touch().handleTouchOf(this.btnDone, new AnimConfig[0]);
        UIUtils.INSTANCE.setFolmeHoverEffect(this.btnDone);
        CardView cardView = (CardView) rootView.findViewById(R.id.panel_immerse_entry);
        this.btnPanelImmerse = cardView;
        Intrinsics.checkNotNull(cardView);
        View viewFindViewById6 = cardView.findViewById(R.id.panel_immerse_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        ImageView imageView = (ImageView) viewFindViewById6;
        this.btnPanelImmerseIcon = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPanelImmerseIcon");
            imageView = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(imageView);
        CardView cardView2 = this.btnPanelImmerse;
        if (cardView2 != null) {
            cardView2.setAlpha(0.0f);
        }
        CardView cardView3 = this.btnPanelImmerse;
        if (cardView3 != null) {
            cardView3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda26
                @Override // android.view.View.OnClickListener
                public final void onClick(View view7) {
                    DoodleFragment.initLayout$lambda$28(this.f$0, view7);
                }
            });
        }
        CardView cardView4 = this.btnPanelImmerse;
        if (cardView4 != null) {
            cardView4.setClickable(false);
        }
        View viewFindViewById7 = rootView.findViewById(R.id.doodle_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
        setDoodleContainer((FrameLayout) viewFindViewById7);
        View viewFindViewById8 = rootView.findViewById(R.id.doodle_tools_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
        this.doodlePanelContainer = viewFindViewById8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$23(DoodleFragment doodleFragment, View view) {
        View view2;
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView == null || !doodleView.getIsDrawing()) {
            IDoodle iDoodle = doodleFragment.doodle;
            if (iDoodle == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle = null;
            }
            if (!iDoodle.undo()) {
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.UNDO);
                View view3 = doodleFragment.btnUndo;
                if (view3 != null) {
                    view3.setEnabled(false);
                }
            }
            View view4 = doodleFragment.btnRedo;
            Intrinsics.checkNotNull(view4);
            if (view4.isEnabled() || (view2 = doodleFragment.btnRedo) == null) {
                return;
            }
            view2.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$24(DoodleFragment doodleFragment, View view) {
        View view2;
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView == null || !doodleView.getIsDrawing()) {
            IDoodle iDoodle = doodleFragment.doodle;
            if (iDoodle == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle = null;
            }
            if (!iDoodle.redo(1)) {
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.REDO);
                View view3 = doodleFragment.btnRedo;
                if (view3 != null) {
                    view3.setEnabled(false);
                }
            }
            View view4 = doodleFragment.btnUndo;
            Intrinsics.checkNotNull(view4);
            if (view4.isEnabled() || (view2 = doodleFragment.btnUndo) == null) {
                return;
            }
            view2.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$25(DoodleFragment doodleFragment, View view) {
        NotesTextStat.reportDoodleClickEvent(NotesTextStat.FULL_SCREEN);
        doodleFragment.switchImmerseMode(true);
        doodleFragment.dismissConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$26(DoodleFragment doodleFragment, View view) {
        NotesTextStat.reportDoodleClickEvent(NotesTextStat.OUT_FULL_SCREEN);
        doodleFragment.switchImmerseMode(false);
        doodleFragment.dismissConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$27(DoodleFragment doodleFragment, View view) {
        NotesTextStat.reportDoodleClickEvent(NotesTextStat.DONE_CLICK_RIGHT);
        view.setSelected(true);
        doodleFragment.saveBeforeExit = true;
        doodleFragment.saveDoodle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initLayout$lambda$28(DoodleFragment doodleFragment, View view) {
        doodleFragment.togglePaletteImmerseEntryState();
        doodleFragment.dismissConfig();
    }

    public void initToolPanel(Context context, View rootView, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        this.toolPanelContainer = rootView.findViewById(R.id.tool_panel_container);
        View viewFindViewById = rootView.findViewById(R.id.brush_pencil);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.brushPencil = viewFindViewById;
        View viewFindViewById2 = rootView.findViewById(R.id.brush_pencil_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.brushPencilTouch = viewFindViewById2;
        View view = null;
        if (viewFindViewById2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencilTouch");
            viewFindViewById2 = null;
        }
        viewFindViewById2.setOnClickListener(this.toolClickListener);
        View view2 = this.brushPencilTouch;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencilTouch");
            view2 = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(view2);
        View viewFindViewById3 = rootView.findViewById(R.id.brush_normal);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.brushNormal = viewFindViewById3;
        View viewFindViewById4 = rootView.findViewById(R.id.brush_normal_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.brushNormalTouch = viewFindViewById4;
        if (viewFindViewById4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormalTouch");
            viewFindViewById4 = null;
        }
        viewFindViewById4.setOnClickListener(this.toolClickListener);
        View view3 = this.brushNormalTouch;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormalTouch");
            view3 = null;
        }
        view3.setAccessibilityDelegate(this.unselectedAccessibilityDelegate);
        View viewFindViewById5 = rootView.findViewById(R.id.brush_markpen);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.brushMarkPen = viewFindViewById5;
        View viewFindViewById6 = rootView.findViewById(R.id.brush_markpen_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.brushMarkPenTouch = viewFindViewById6;
        if (viewFindViewById6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPenTouch");
            viewFindViewById6 = null;
        }
        viewFindViewById6.setOnClickListener(this.toolClickListener);
        View view4 = this.brushMarkPenTouch;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPenTouch");
            view4 = null;
        }
        view4.setAccessibilityDelegate(this.unselectedAccessibilityDelegate);
        View viewFindViewById7 = rootView.findViewById(R.id.brush_watercolor);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
        this.brushWaterColor = viewFindViewById7;
        View viewFindViewById8 = rootView.findViewById(R.id.brush_watercolor_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
        this.brushWaterColorTouch = viewFindViewById8;
        if (viewFindViewById8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColorTouch");
            viewFindViewById8 = null;
        }
        viewFindViewById8.setOnClickListener(this.toolClickListener);
        View view5 = this.brushWaterColorTouch;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColorTouch");
            view5 = null;
        }
        view5.setAccessibilityDelegate(this.unselectedAccessibilityDelegate);
        View viewFindViewById9 = rootView.findViewById(R.id.brush_eraser);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(...)");
        this.brushEraser = viewFindViewById9;
        View viewFindViewById10 = rootView.findViewById(R.id.brush_eraser_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(...)");
        this.brushEraserTouch = viewFindViewById10;
        if (viewFindViewById10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushEraserTouch");
            viewFindViewById10 = null;
        }
        viewFindViewById10.setOnClickListener(this.toolClickListener);
        View view6 = this.brushEraserTouch;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushEraserTouch");
            view6 = null;
        }
        view6.setAccessibilityDelegate(this.unselectedAccessibilityDelegate);
        View viewFindViewById11 = rootView.findViewById(R.id.tool_picture);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(...)");
        this.toolPicture = viewFindViewById11;
        View viewFindViewById12 = rootView.findViewById(R.id.tool_picture_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(...)");
        this.toolPictureTouch = viewFindViewById12;
        if (viewFindViewById12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolPictureTouch");
            viewFindViewById12 = null;
        }
        viewFindViewById12.setOnClickListener(this.toolClickListener);
        View view7 = this.toolPictureTouch;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolPictureTouch");
            view7 = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(view7);
        View viewFindViewById13 = rootView.findViewById(R.id.tool_background);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(...)");
        this.toolBackground = viewFindViewById13;
        View viewFindViewById14 = rootView.findViewById(R.id.tool_background_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(...)");
        this.toolBackgroundTouch = viewFindViewById14;
        if (viewFindViewById14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolBackgroundTouch");
            viewFindViewById14 = null;
        }
        viewFindViewById14.setOnClickListener(this.toolClickListener);
        View view8 = this.toolBackgroundTouch;
        if (view8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolBackgroundTouch");
            view8 = null;
        }
        view8.setAccessibilityDelegate(this.unselectedAccessibilityDelegate);
        View viewFindViewById15 = rootView.findViewById(R.id.tool_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "findViewById(...)");
        this.toolText = viewFindViewById15;
        View viewFindViewById16 = rootView.findViewById(R.id.tool_text_touch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "findViewById(...)");
        this.toolTextTouch = viewFindViewById16;
        if (viewFindViewById16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolTextTouch");
            viewFindViewById16 = null;
        }
        viewFindViewById16.setOnClickListener(this.toolClickListener);
        View view9 = this.toolTextTouch;
        if (view9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolTextTouch");
            view9 = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(view9);
        View viewFindViewById17 = rootView.findViewById(R.id.palette_entry);
        this.colorView = viewFindViewById17;
        if (viewFindViewById17 != null) {
            viewFindViewById17.setAccessibilityTraversalBefore(R.id.brush_pencil_touch);
        }
        View view10 = this.colorView;
        if (view10 != null) {
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(view10);
        }
        View viewFindViewById18 = rootView.findViewById(R.id.alpha_bg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById18, "findViewById(...)");
        setAlphaBg(viewFindViewById18);
        View view11 = this.colorView;
        Intrinsics.checkNotNull(view11);
        ColorBtnComponent colorBtnComponent = new ColorBtnComponent(view11, new C02932(), 0, 4, null);
        this.colorBtn = colorBtnComponent;
        IDoodle iDoodle = this.doodle;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        colorBtnComponent.setColorInt(iDoodle.getPen().getColorInt());
        this.paletteContainer = rootView.findViewById(R.id.palette_container);
        View view12 = this.paletteContainer;
        Intrinsics.checkNotNull(view12);
        PaletteComponent paletteComponent = new PaletteComponent(context, view12, 0, 4, null);
        this.palette = paletteComponent;
        paletteComponent.addPickListener(new ColorPickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment.initToolPanel.3
            @Override // com.miui.doodle.feature.color.ColorPickListener
            public void onColorPick(int colorInt) {
                NotesTextStat.reportDoodleClickEvent(NotesTextStat.USE_COLOR);
                IColorBtn iColorBtn = DoodleFragment.this.colorBtn;
                IDoodle iDoodle2 = null;
                if (iColorBtn == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
                    iColorBtn = null;
                }
                iColorBtn.setColorInt(colorInt);
                IBrushConfig iBrushConfig = DoodleFragment.this.brushConfig;
                if (iBrushConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    iBrushConfig = null;
                }
                iBrushConfig.setColorInt(colorInt);
                IDoodle iDoodle3 = DoodleFragment.this.doodle;
                if (iDoodle3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                } else {
                    iDoodle2 = iDoodle3;
                }
                iDoodle2.getPen().setColorInt(colorInt);
                DoodleFragment.this.updateBrushViewColor();
                DoodleFragment doodleFragment = DoodleFragment.this;
                doodleFragment.setPanelImmerseState(doodleFragment.isPanelInImmerseMode);
            }
        });
        this.backgroundConfigContainer = rootView.findViewById(R.id.tool_background_container);
        View viewFindViewById19 = rootView.findViewById(R.id.fl_background_white);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById19, "findViewById(...)");
        this.backgroundWhiteConfigContainer = viewFindViewById19;
        if (viewFindViewById19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
            viewFindViewById19 = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(viewFindViewById19);
        View view13 = this.backgroundWhiteConfigContainer;
        if (view13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
            view13 = null;
        }
        view13.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view14) {
                DoodleFragment.initToolPanel$lambda$30(this.f$0, view14);
            }
        });
        View viewFindViewById20 = rootView.findViewById(R.id.fl_background_dot);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById20, "findViewById(...)");
        this.backgroundDotConfigContainer = viewFindViewById20;
        if (viewFindViewById20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
            viewFindViewById20 = null;
        }
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(viewFindViewById20);
        View view14 = this.backgroundDotConfigContainer;
        if (view14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
            view14 = null;
        }
        view14.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view15) {
                DoodleFragment.initToolPanel$lambda$31(this.f$0, view15);
            }
        });
        this.brushConfigContainer = rootView.findViewById(R.id.brush_config_container);
        View view15 = this.brushConfigContainer;
        Intrinsics.checkNotNull(view15);
        BrushConfigComponent brushConfigComponent = new BrushConfigComponent(view15, 0.0f, 0, 0, new BrushConfigListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment.initToolPanel.6
            @Override // com.miui.doodle.feature.tools.BrushConfigListener
            public void onSetAlpha(float alpha) {
                IDoodle iDoodle2 = DoodleFragment.this.doodle;
                if (iDoodle2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle2 = null;
                }
                iDoodle2.getPen().setAlpha(alpha);
            }

            @Override // com.miui.doodle.feature.tools.BrushConfigListener
            public void onSetStrokeLevel(int level) {
                IDoodle iDoodle2 = DoodleFragment.this.doodle;
                if (iDoodle2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle2 = null;
                }
                iDoodle2.getPen().setStrokeLevel(level);
            }
        }, 14, null);
        this.brushConfig = brushConfigComponent;
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle2 = null;
        }
        brushConfigComponent.setColorInt(iDoodle2.getPen().getColorInt());
        View viewFindViewById21 = rootView.findViewById(R.id.element_eraser_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById21, "findViewById(...)");
        this.elementEraserContainer = viewFindViewById21;
        View viewFindViewById22 = rootView.findViewById(R.id.pixel_eraser_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById22, "findViewById(...)");
        this.pixelEraserContainer = viewFindViewById22;
        if (viewFindViewById22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            viewFindViewById22 = null;
        }
        View viewFindViewById23 = viewFindViewById22.findViewById(R.id.pixel_eraser_entry);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById23, "findViewById(...)");
        this.pixelEraser = viewFindViewById23;
        View view16 = this.elementEraserContainer;
        if (view16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view16 = null;
        }
        View viewFindViewById24 = view16.findViewById(R.id.element_eraser_entry);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById24, "findViewById(...)");
        this.elementEraser = viewFindViewById24;
        View view17 = this.elementEraserContainer;
        if (view17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view17 = null;
        }
        View viewFindViewById25 = view17.findViewById(R.id.tips_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById25, "findViewById(...)");
        this.elementTipView = viewFindViewById25;
        View view18 = this.elementEraserContainer;
        if (view18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view18 = null;
        }
        View viewFindViewById26 = view18.findViewById(R.id.element_tips_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById26, "findViewById(...)");
        this.elementTipText = (TextView) viewFindViewById26;
        View view19 = this.pixelEraserContainer;
        if (view19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view19 = null;
        }
        View viewFindViewById27 = view19.findViewById(R.id.tips_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById27, "findViewById(...)");
        this.pixelTipView = viewFindViewById27;
        View view20 = this.pixelEraserContainer;
        if (view20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view20 = null;
        }
        View viewFindViewById28 = view20.findViewById(R.id.pixel_tips_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById28, "findViewById(...)");
        this.pixelTipText = (TextView) viewFindViewById28;
        View view21 = this.elementEraserContainer;
        if (view21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view21 = null;
        }
        view21.findViewById(R.id.element_eraser_entry).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view22) {
                return DoodleFragment.initToolPanel$lambda$33(this.f$0, view22);
            }
        });
        View view22 = this.elementEraserContainer;
        if (view22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view22 = null;
        }
        view22.findViewById(R.id.element_eraser_entry).setOnClickListener(this.toolClickListener);
        View view23 = this.pixelEraserContainer;
        if (view23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view23 = null;
        }
        view23.findViewById(R.id.pixel_eraser_entry).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda8
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view24) {
                return DoodleFragment.initToolPanel$lambda$35(this.f$0, view24);
            }
        });
        View view24 = this.pixelEraserContainer;
        if (view24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
        } else {
            view = view24;
        }
        view.findViewById(R.id.pixel_eraser_entry).setOnClickListener(this.toolClickListener);
        initToolView(context);
        restorePenToolbarState(context, savedInstanceState);
        setPanelImmerseState(this.isPanelInImmerseMode);
    }

    /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$initToolPanel$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0017J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/miui/notes/doodle/feature/DoodleFragment$initToolPanel$2", "Lcom/miui/doodle/feature/color/PaletteListener;", "show", "", "dismiss", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C02932 implements PaletteListener {
        C02932() {
        }

        @Override // com.miui.doodle.feature.color.PaletteListener
        public void show() {
            IBrushConfig iBrushConfig = DoodleFragment.this.brushConfig;
            View view = null;
            if (iBrushConfig == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                iBrushConfig = null;
            }
            if (iBrushConfig.getIsShow()) {
                IBrushConfig iBrushConfig2 = DoodleFragment.this.brushConfig;
                if (iBrushConfig2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    iBrushConfig2 = null;
                }
                iBrushConfig2.dismiss();
            }
            IPalette iPalette = DoodleFragment.this.palette;
            if (iPalette == null) {
                Intrinsics.throwUninitializedPropertyAccessException("palette");
                iPalette = null;
            }
            iPalette.show();
            DoodleView doodleView = DoodleFragment.this.getDoodleView();
            if (doodleView != null) {
                doodleView.setShowConfigPopupWindow(true);
            }
            DoodleFragment doodleFragment = DoodleFragment.this;
            View toolPanelContainer = doodleFragment.getToolPanelContainer();
            Intrinsics.checkNotNull(toolPanelContainer);
            doodleFragment.toolPanelContainerAnimate = toolPanelContainer.animate();
            ViewPropertyAnimator viewPropertyAnimator = DoodleFragment.this.toolPanelContainerAnimate;
            if (viewPropertyAnimator != null) {
                DoodleFragment doodleFragment2 = DoodleFragment.this;
                ViewPropertyAnimator viewPropertyAnimatorAlpha = viewPropertyAnimator.setDuration(200L).alpha(0.0f);
                View toolPanelContainer2 = doodleFragment2.getToolPanelContainer();
                Intrinsics.checkNotNull(toolPanelContainer2);
                viewPropertyAnimatorAlpha.translationY(toolPanelContainer2.getHeight()).start();
            }
            View view2 = DoodleFragment.this.doodlePanelContainer;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
            } else {
                view = view2;
            }
            final DoodleFragment doodleFragment3 = DoodleFragment.this;
            view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$initToolPanel$2$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    return DoodleFragment.C02932.show$lambda$1(doodleFragment3, view3, motionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean show$lambda$1(DoodleFragment doodleFragment, View view, MotionEvent motionEvent) {
            return doodleFragment.dismissConfig();
        }

        @Override // com.miui.doodle.feature.color.PaletteListener
        public void dismiss() {
            View view = DoodleFragment.this.doodlePanelContainer;
            IPalette iPalette = null;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                view = null;
            }
            view.setOnTouchListener(null);
            if (!DoodleFragment.this.isPanelInImmerseMode) {
                DoodleFragment doodleFragment = DoodleFragment.this;
                View colorView = doodleFragment.getColorView();
                Intrinsics.checkNotNull(colorView);
                doodleFragment.colorViewAnimate = colorView.animate();
                ViewPropertyAnimator viewPropertyAnimator = DoodleFragment.this.colorViewAnimate;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.translationX(0.0f).alpha(1.0f).setDuration(150L).start();
                }
                DoodleFragment doodleFragment2 = DoodleFragment.this;
                View toolPanelContainer = doodleFragment2.getToolPanelContainer();
                Intrinsics.checkNotNull(toolPanelContainer);
                doodleFragment2.toolPanelContainerAnimate = toolPanelContainer.animate();
                ViewPropertyAnimator viewPropertyAnimator2 = DoodleFragment.this.toolPanelContainerAnimate;
                if (viewPropertyAnimator2 != null) {
                    viewPropertyAnimator2.setDuration(200L).alpha(1.0f).translationY(0.0f).start();
                }
            }
            IPalette iPalette2 = DoodleFragment.this.palette;
            if (iPalette2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("palette");
            } else {
                iPalette = iPalette2;
            }
            iPalette.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolPanel$lambda$30(DoodleFragment doodleFragment, View view) {
        DoodleDocument document;
        DocumentInfo documentInfo;
        View view2 = doodleFragment.backgroundWhiteConfigContainer;
        View view3 = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
            view2 = null;
        }
        view2.setBackgroundResource(R.drawable.ic_background_selected);
        View view4 = doodleFragment.backgroundDotConfigContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
        } else {
            view3 = view4;
        }
        view3.setBackgroundResource(android.R.color.transparent);
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null && (document = doodleView.getDocument()) != null && (documentInfo = document.getDocumentInfo()) != null) {
            documentInfo.setBackgroundType("white");
        }
        NotesTextStat.reportDoodleClickEvent(NotesTextStat.TEXTURE_TOOL_WITHE_PAPER);
        DoodleView doodleView2 = doodleFragment.doodleView;
        if (doodleView2 != null) {
            doodleView2.clearBackgroundCache();
        }
        doodleFragment.updateDoodleBackground(android.R.color.white);
        doodleFragment.currentBackgroundType = "white";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolPanel$lambda$31(DoodleFragment doodleFragment, View view) {
        DoodleDocument document;
        DocumentInfo documentInfo;
        View view2 = doodleFragment.backgroundDotConfigContainer;
        View view3 = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
            view2 = null;
        }
        view2.setBackgroundResource(R.drawable.ic_background_selected);
        View view4 = doodleFragment.backgroundWhiteConfigContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
        } else {
            view3 = view4;
        }
        view3.setBackgroundResource(android.R.color.transparent);
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null && (document = doodleView.getDocument()) != null && (documentInfo = document.getDocumentInfo()) != null) {
            documentInfo.setBackgroundType("dot");
        }
        NotesTextStat.reportDoodleClickEvent(NotesTextStat.TEXTURE_TOOL_POINT_PAPER);
        DoodleView doodleView2 = doodleFragment.doodleView;
        if (doodleView2 != null) {
            doodleView2.clearBackgroundCache();
        }
        doodleFragment.updateDoodleBackground(R.drawable.doodle_canvas_bg);
        doodleFragment.currentBackgroundType = "dot";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initToolPanel$lambda$33(final DoodleFragment doodleFragment, View view) {
        doodleFragment.getBtnUnImmerse().setImportantForAccessibility(2);
        View view2 = doodleFragment.elementTipView;
        View view3 = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementTipView");
            view2 = null;
        }
        view2.setAlpha(0.0f);
        View view4 = doodleFragment.elementTipView;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementTipView");
            view4 = null;
        }
        view4.setClickable(false);
        View view5 = doodleFragment.elementTipView;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementTipView");
        } else {
            view3 = view5;
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view3.animate();
        doodleFragment.tipViewAnimate = viewPropertyAnimatorAnimate;
        if (viewPropertyAnimatorAnimate == null) {
            return true;
        }
        viewPropertyAnimatorAnimate.alpha(1.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$initToolPanel$7$1$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                View view6 = this.this$0.elementTipView;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("elementTipView");
                    view6 = null;
                }
                view6.setVisibility(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                ViewPropertyAnimator viewPropertyAnimator = this.this$0.tipViewAnimate;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.alpha(0.0f).setListener(new DoodleFragment$initToolPanel$7$1$1$onAnimationEnd$1$1(this.this$0)).setStartDelay(500L);
                }
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initToolPanel$lambda$35(final DoodleFragment doodleFragment, View view) {
        doodleFragment.getBtnUnImmerse().setImportantForAccessibility(2);
        View view2 = doodleFragment.pixelTipView;
        View view3 = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
            view2 = null;
        }
        view2.setAlpha(0.0f);
        View view4 = doodleFragment.pixelTipView;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
            view4 = null;
        }
        view4.setClickable(false);
        View view5 = doodleFragment.pixelTipView;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
        } else {
            view3 = view5;
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view3.animate();
        doodleFragment.tipViewAnimate1 = viewPropertyAnimatorAnimate;
        if (viewPropertyAnimatorAnimate == null) {
            return true;
        }
        viewPropertyAnimatorAnimate.alpha(1.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$initToolPanel$8$1$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                View view6 = this.this$0.pixelTipView;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
                    view6 = null;
                }
                view6.setVisibility(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                ViewPropertyAnimator viewPropertyAnimator = this.this$0.tipViewAnimate1;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.alpha(0.0f).setListener(new DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1(this.this$0)).setStartDelay(500L);
                }
            }
        }).start();
        return true;
    }

    private final void initToolView(Context context) {
        View view = this.brushPencil;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
            view = null;
        }
        view.setForeground(createBrushFg(context, "pencil", DoodlePenManager.INSTANCE.getPencil().getColorInt()));
        View view3 = this.brushPencil;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
            view3 = null;
        }
        view3.setBackground(createBrushBg(context, "pencil"));
        View view4 = this.brushWaterColor;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
            view4 = null;
        }
        view4.setForeground(createBrushFg(context, "watercolor", DoodlePenManager.INSTANCE.getWatercolor().getColorInt()));
        View view5 = this.brushWaterColor;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
            view5 = null;
        }
        view5.setBackground(createBrushBg(context, "watercolor"));
        View view6 = this.brushMarkPen;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
            view6 = null;
        }
        view6.setForeground(createBrushFg(context, "markpen", DoodlePenManager.INSTANCE.getMarkPen().getColorInt()));
        View view7 = this.brushMarkPen;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
            view7 = null;
        }
        view7.setBackground(createBrushBg(context, "markpen"));
        View view8 = this.brushNormal;
        if (view8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
            view8 = null;
        }
        view8.setForeground(createBrushFg(context, "pen", DoodlePenManager.INSTANCE.getNormal().getColorInt()));
        View view9 = this.brushNormal;
        if (view9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
            view9 = null;
        }
        view9.setBackground(createBrushBg(context, "pen"));
        View view10 = this.brushPencilTouch;
        if (view10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencilTouch");
        } else {
            view2 = view10;
        }
        view2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.notes.doodle.feature.DoodleFragment.initToolView.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setSelected(true);
            }
        });
        updateEraserView(false);
        updatePictureToolView(false);
        updateTextToolView(false);
        updateBackgroundView(false);
    }

    private final String getSavedPenType(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getString("selected_pen_type");
        }
        return null;
    }

    private final String getSavedBackgroundType(Bundle savedInstanceState) {
        String string;
        return (savedInstanceState == null || (string = savedInstanceState.getString("background_type", "dot")) == null) ? "dot" : string;
    }

    private final void restorePenToolbarState(Context context, Bundle savedInstanceState) {
        DoodleDocument document;
        DocumentInfo documentInfo;
        String savedPenType = getSavedPenType(savedInstanceState);
        String savedBackgroundType = getSavedBackgroundType(savedInstanceState);
        this.currentBackgroundType = savedBackgroundType;
        DoodleView doodleView = this.doodleView;
        if (doodleView != null && (document = doodleView.getDocument()) != null && (documentInfo = document.getDocumentInfo()) != null) {
            documentInfo.setBackgroundType(savedBackgroundType);
        }
        restoreBackgroundState(savedBackgroundType);
        restorePenState(context, savedInstanceState, savedPenType);
    }

    private final void restoreBackgroundState(String backgroundType) {
        View view = null;
        if (Intrinsics.areEqual(backgroundType, "white")) {
            View view2 = this.backgroundWhiteConfigContainer;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
                view2 = null;
            }
            view2.setBackgroundResource(R.drawable.ic_background_selected);
            View view3 = this.backgroundDotConfigContainer;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
            } else {
                view = view3;
            }
            view.setBackgroundResource(android.R.color.transparent);
            updateDoodleBackground(android.R.color.white);
            return;
        }
        View view4 = this.backgroundWhiteConfigContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
            view4 = null;
        }
        view4.setBackgroundResource(android.R.color.transparent);
        View view5 = this.backgroundDotConfigContainer;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
        } else {
            view = view5;
        }
        view.setBackgroundResource(R.drawable.ic_background_selected);
        updateDoodleBackground(R.drawable.doodle_canvas_bg);
    }

    private final void restorePenState(Context context, Bundle savedInstanceState, String penType) {
        if (penType != null) {
            restorePenWithType(context, savedInstanceState, penType);
        } else {
            restoreDefaultPenState(context);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:14:0x002d  */
    /* JADX WARN: Code duplicated, block: B:27:0x0058  */
    /* JADX WARN: Code duplicated, block: B:28:0x005c  */
    private final void restorePenWithType(Context context, Bundle savedInstanceState, String penType) {
        DoodlePen doodlePenCopy = DoodlePenManager.INSTANCE.get(penType).copy();
        switch (penType) {
            case "Normal_01":
            case "MarkPen_01":
            case "WaterColor_01":
            case "Pencil_01":
                restoreColorAndSizePen(doodlePenCopy, savedInstanceState);
                break;
            case "Eraser_01":
            case "Eraser_02":
                restoreSizeOnlyPen(doodlePenCopy, savedInstanceState);
                restoreEraserSpecificState(savedInstanceState);
                break;
            case "Text_01":
                restoreColorAndSizePen(doodlePenCopy, savedInstanceState);
                break;
            default:
                restoreColorAndSizePen(doodlePenCopy, savedInstanceState);
                break;
        }
        IDoodle iDoodle = this.doodle;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        iDoodle.setPen(doodlePenCopy);
        updatePenUI(doodlePenCopy);
        activeTool(context, getActiveViewForPenType(penType));
        updateBrushViewColor();
    }

    private final void restoreDefaultPenState(Context context) {
        DoodlePen doodlePenCopy = DoodlePenManager.INSTANCE.getPencil().copy();
        IDoodle iDoodle = this.doodle;
        View view = null;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        iDoodle.setPen(doodlePenCopy);
        updatePenUI(doodlePenCopy);
        View view2 = this.brushPencil;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
        } else {
            view = view2;
        }
        activeTool(context, view);
        updateBrushViewColor();
    }

    private final void restoreColorAndSizePen(DoodlePen pen, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            pen.setColorInt(savedInstanceState.getInt("pen_color", pen.getColorInt()));
            pen.setAlpha(savedInstanceState.getFloat("pen_alpha", pen.getAlpha()));
            pen.setStrokeLevel(savedInstanceState.getInt("pen_stroke_level", pen.getStrokeLevel()));
        }
    }

    private final void restoreSizeOnlyPen(DoodlePen pen, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            pen.setAlpha(savedInstanceState.getFloat("pen_alpha", pen.getAlpha()));
            pen.setStrokeLevel(savedInstanceState.getInt("pen_stroke_level", pen.getStrokeLevel()));
        }
    }

    private final void restoreEraserSpecificState(Bundle savedInstanceState) {
        this.selectedElementEraser = savedInstanceState != null ? savedInstanceState.getBoolean("selected_element_eraser", false) : false;
        View view = this.elementEraserContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view = null;
        }
        view.findViewById(R.id.element_eraser_entry).setSelected(this.selectedElementEraser);
        View view3 = this.pixelEraserContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
        } else {
            view2 = view3;
        }
        view2.findViewById(R.id.pixel_eraser_entry).setSelected(!this.selectedElementEraser);
        toggleElementContainer();
    }

    private final void updatePenUI(DoodlePen pen) {
        IColorBtn iColorBtn = this.colorBtn;
        IBrushConfig iBrushConfig = null;
        if (iColorBtn == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorBtn");
            iColorBtn = null;
        }
        iColorBtn.setColorInt(pen.getColorInt());
        IPalette iPalette = this.palette;
        if (iPalette == null) {
            Intrinsics.throwUninitializedPropertyAccessException("palette");
            iPalette = null;
        }
        iPalette.setColor(pen.getColorInt());
        IBrushConfig iBrushConfig2 = this.brushConfig;
        if (iBrushConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
        } else {
            iBrushConfig = iBrushConfig2;
        }
        iBrushConfig.setColorInt(pen.getColorInt());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:66:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:68:0x00ac A[RETURN] */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r4.equals(com.miui.doodle.feature.draw.DoodlePen.TYPE_ELEMENT_ERASER) == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0032, code lost:
    
        if (r4.equals(com.miui.doodle.feature.draw.DoodlePen.TYPE_ERASER) == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0036, code lost:
    
        r3 = r3.brushEraser;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        if (r3 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003a, code lost:
    
        kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0040, code lost:
    
        return r3;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final android.view.View getActiveViewForPenType(java.lang.String r4) {
        /*
            Method dump skipped, instruction units count: 208
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.notes.doodle.feature.DoodleFragment.getActiveViewForPenType(java.lang.String):android.view.View");
    }

    private final void initDoodleView(final Activity activity, ViewGroup parentView) {
        DoodleViewWrapper doodleViewWrapper = new DoodleViewWrapper(this, activity, new IDoodleListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment.initDoodleView.1
            @Override // com.miui.doodle.base.IDoodleListener
            public void onReady(IDoodle doodle) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
            }

            @Override // com.miui.doodle.base.IDoodleListener
            public void onSaved(IDoodle doodle, File doodleFile, Runnable callback) {
                DoodleDocument document;
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                DoodleFragment.this.hideSaveProgressDialog();
                if (doodleFile == null) {
                    Intent intent = new Intent();
                    intent.putExtra(DoodleFragment.KEY_IMAGE_NAME, "");
                    intent.putExtra(DoodleFragment.KEY_IMAGE_NULL, true);
                    activity.setResult(-1, intent);
                    activity.finish();
                    return;
                }
                if (TextUtils.isEmpty(doodleFile.getAbsolutePath())) {
                    return;
                }
                Intent intent2 = new Intent();
                intent2.putExtra(DoodleFragment.KEY_IMAGE_NAME, doodleFile.getAbsolutePath());
                DoodleView doodleView = DoodleFragment.this.getDoodleView();
                String editImageId = (doodleView == null || (document = doodleView.getDocument()) == null) ? null : document.getEditImageId();
                if (editImageId != null) {
                    intent2.putExtra(DoodleFragment.KEY_EDIT_IMAGE_ID, editImageId);
                }
                activity.setResult(-1, intent2);
                activity.finish();
            }

            @Override // com.miui.doodle.base.IDoodleListener
            public void onNoChangeAndExit(IDoodle doodle, Runnable callback) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                DoodleFragment.this.hideSaveProgressDialog();
                Intent intent = new Intent();
                intent.putExtra(DoodleFragment.KEY_IMAGE_NAME, DoodleApplication.SaveFlagNoChange);
                activity.setResult(-1, intent);
                activity.finish();
            }

            @Override // com.miui.doodle.base.IDoodleListener
            public void onProcessing(boolean isFinished) {
                if (isFinished) {
                    DoodleFragment.this.hideProcessingDialog();
                    return;
                }
                DoodleFragment doodleFragment = DoodleFragment.this;
                Context contextRequireContext = doodleFragment.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                doodleFragment.showProcessingDialog(contextRequireContext, 500L);
            }
        }, false, false, false, 28, null);
        this.doodleView = doodleViewWrapper;
        Intrinsics.checkNotNull(doodleViewWrapper);
        DoodleViewWrapper doodleViewWrapper2 = doodleViewWrapper;
        this.doodle = doodleViewWrapper2;
        doodleViewWrapper2.setMarkDoodleOperation(AnonymousClass2.INSTANCE);
        DoodleView doodleView = this.doodleView;
        Intrinsics.checkNotNull(doodleView);
        this.touchGestureListener = new DoodleOnTouchGestureListener(doodleView, new DoodleOnTouchGestureListener.ISelectionListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment.initDoodleView.3
            private IDoodleItemListener mIDoodleItemListener;
            private DoodlePen mLastPen;

            @Override // com.miui.doodle.feature.draw.DoodleOnTouchGestureListener.ISelectionListener
            public void onCreateSelectableItem(IDoodle doodle, float x, float y) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
            }

            {
                this.mIDoodleItemListener = new IDoodleItemListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$initDoodleView$3$mIDoodleItemListener$1
                    @Override // com.miui.doodle.base.core.IDoodleItemListener
                    public void onPropertyChanged(int property) {
                        DoodleOnTouchGestureListener doodleOnTouchGestureListener = doodleFragment.touchGestureListener;
                        if (doodleOnTouchGestureListener == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                            doodleOnTouchGestureListener = null;
                        }
                        doodleOnTouchGestureListener.getSelectedItem();
                    }
                };
            }

            public final DoodlePen getMLastPen() {
                return this.mLastPen;
            }

            public final void setMLastPen(DoodlePen doodlePen) {
                this.mLastPen = doodlePen;
            }

            public final IDoodleItemListener getMIDoodleItemListener() {
                return this.mIDoodleItemListener;
            }

            public final void setMIDoodleItemListener(IDoodleItemListener iDoodleItemListener) {
                Intrinsics.checkNotNullParameter(iDoodleItemListener, "<set-?>");
                this.mIDoodleItemListener = iDoodleItemListener;
            }

            @Override // com.miui.doodle.feature.draw.DoodleOnTouchGestureListener.ISelectionListener
            public void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                Intrinsics.checkNotNullParameter(selectableItem, "selectableItem");
                if (selected) {
                    IDoodle iDoodle = null;
                    if (this.mLastPen == null) {
                        IDoodle iDoodle2 = DoodleFragment.this.doodle;
                        if (iDoodle2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                            iDoodle2 = null;
                        }
                        this.mLastPen = iDoodle2.getPen();
                    }
                    DoodleView doodleView2 = DoodleFragment.this.getDoodleView();
                    if (doodleView2 != null) {
                        doodleView2.setEditMode(true);
                    }
                    if (selectableItem instanceof DoodleText) {
                        IDoodle iDoodle3 = DoodleFragment.this.doodle;
                        if (iDoodle3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        } else {
                            iDoodle = iDoodle3;
                        }
                        iDoodle.setPen(DoodlePenManager.INSTANCE.getText());
                        DoodleView doodleView3 = DoodleFragment.this.getDoodleView();
                        if (doodleView3 != null) {
                            onTextEdit(doodleView3, (DoodleText) selectableItem);
                        }
                    } else if (selectableItem instanceof DoodleBitmap) {
                        IDoodle iDoodle4 = DoodleFragment.this.doodle;
                        if (iDoodle4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        } else {
                            iDoodle = iDoodle4;
                        }
                        iDoodle.setPen(DoodlePenManager.INSTANCE.getBitmap());
                    }
                    selectableItem.addItemListener(this.mIDoodleItemListener);
                    return;
                }
                selectableItem.removeItemListener(this.mIDoodleItemListener);
            }

            @Override // com.miui.doodle.feature.draw.DoodleOnTouchGestureListener.ISelectionListener
            public void onDeleteItem(IDoodle doodle, IDoodleSelectableItem selectableItem) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                Intrinsics.checkNotNullParameter(selectableItem, "selectableItem");
                doodle.removeItem(selectableItem, true);
                DoodleOnTouchGestureListener doodleOnTouchGestureListener = DoodleFragment.this.touchGestureListener;
                if (doodleOnTouchGestureListener == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                    doodleOnTouchGestureListener = null;
                }
                doodleOnTouchGestureListener.setSelectedItem(null);
                IDoodle.refresh$default(doodle, null, 1, null);
            }

            @Override // com.miui.doodle.feature.draw.DoodleOnTouchGestureListener.ISelectionListener
            public void onDeleteItemByButton(IDoodle doodle, IDoodleSelectableItem selectableItem) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                Intrinsics.checkNotNullParameter(selectableItem, "selectableItem");
                doodle.removeItem(selectableItem, true);
            }

            @Override // com.miui.doodle.feature.draw.DoodleOnTouchGestureListener.ISelectionListener
            public void onTextEdit(IDoodle doodle, DoodleText doodleText) {
                Intrinsics.checkNotNullParameter(doodle, "doodle");
                Intrinsics.checkNotNullParameter(doodleText, "doodleText");
                if (DoodleFragment.this.doodleEditTextDialog != null) {
                    DoodleEditTextDialog doodleEditTextDialog = DoodleFragment.this.doodleEditTextDialog;
                    Intrinsics.checkNotNull(doodleEditTextDialog);
                    if (doodleEditTextDialog.isShowing()) {
                        return;
                    }
                }
                DoodleFragment doodleFragment = DoodleFragment.this;
                Context context = doodleFragment.mContext;
                doodleFragment.doodleEditTextDialog = context != null ? new DoodleEditTextDialog(context, doodle, doodleText) : null;
                DoodleEditTextDialog doodleEditTextDialog2 = DoodleFragment.this.doodleEditTextDialog;
                Intrinsics.checkNotNull(doodleEditTextDialog2);
                doodleEditTextDialog2.show();
            }
        });
        Application application = activity.getApplication();
        Intrinsics.checkNotNullExpressionValue(application, "getApplication(...)");
        Application application2 = application;
        DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.touchGestureListener;
        DoodleParams doodleParams = null;
        if (doodleOnTouchGestureListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
            doodleOnTouchGestureListener = null;
        }
        DoodleTouchDetector doodleTouchDetector = new DoodleTouchDetector(application2, doodleOnTouchGestureListener);
        DoodleView doodleView2 = this.doodleView;
        if (doodleView2 != null) {
            doodleView2.setDefaultTouchDetector(doodleTouchDetector);
        }
        IDoodle iDoodle = this.doodle;
        if (iDoodle == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            iDoodle = null;
        }
        DoodleParams doodleParams2 = this.doodleParams;
        if (doodleParams2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
        } else {
            doodleParams = doodleParams2;
        }
        iDoodle.setDrawableOutside(doodleParams.getIsDrawableOutside());
        parentView.addView(this.doodleView, 0, new FrameLayout.LayoutParams(-1, -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void initDoodleView$markDoodleOperation(int i) {
        NotesTextStat.markDoodleOperation(i);
        return null;
    }

    /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$initDoodleView$2, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    static final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Integer, Void> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1, Intrinsics.Kotlin.class, "markDoodleOperation", "initDoodleView$markDoodleOperation(I)Ljava/lang/Void;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Void invoke(Integer num) {
            return invoke(num.intValue());
        }

        public final Void invoke(int i) {
            return DoodleFragment.initDoodleView$markDoodleOperation(i);
        }
    }

    public float getTransY() {
        return requireContext().getResources().getDimension(R.dimen.tool_select_tran_y);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0196  */
    private final void activeTool(Context context, View newActiveView) {
        boolean z;
        if (isAdded()) {
            float transY = getTransY();
            View view = this.activeTool;
            View view2 = null;
            IBrushConfig iBrushConfig = null;
            View view3 = null;
            IBrushConfig iBrushConfig2 = null;
            if (view == newActiveView) {
                View view4 = this.toolBackground;
                if (view4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolBackground");
                    view4 = null;
                }
                if (view == view4) {
                    return;
                }
                IDoodle iDoodle = this.doodle;
                if (iDoodle == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle = null;
                }
                if (iDoodle.getPen().isBrushPen()) {
                    IBrushConfig iBrushConfig3 = this.brushConfig;
                    if (iBrushConfig3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                        iBrushConfig3 = null;
                    }
                    if (iBrushConfig3.getIsShow()) {
                        View view5 = this.doodlePanelContainer;
                        if (view5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                            view5 = null;
                        }
                        view5.setOnClickListener(null);
                        View view6 = this.doodlePanelContainer;
                        if (view6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                            view6 = null;
                        }
                        view6.setClickable(false);
                        IBrushConfig iBrushConfig4 = this.brushConfig;
                        if (iBrushConfig4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                        } else {
                            iBrushConfig = iBrushConfig4;
                        }
                        iBrushConfig.dismiss();
                        return;
                    }
                    IDoodle iDoodle2 = this.doodle;
                    if (iDoodle2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                        iDoodle2 = null;
                    }
                    DoodlePen pen = iDoodle2.getPen();
                    IBrushConfig iBrushConfig5 = this.brushConfig;
                    if (iBrushConfig5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                        iBrushConfig5 = null;
                    }
                    iBrushConfig5.updateParams(pen.getColorInt(), pen.getAlpha(), pen.getStrokeLevel());
                    IBrushConfig iBrushConfig6 = this.brushConfig;
                    if (iBrushConfig6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                        iBrushConfig6 = null;
                    }
                    iBrushConfig6.show();
                    DoodleView doodleView = this.doodleView;
                    if (doodleView != null) {
                        doodleView.setShowConfigPopupWindow(true);
                    }
                    View view7 = this.doodlePanelContainer;
                    if (view7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                    } else {
                        view3 = view7;
                    }
                    view3.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda27
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view8, MotionEvent motionEvent) {
                            return DoodleFragment.activeTool$lambda$38(this.f$0, view8, motionEvent);
                        }
                    });
                    return;
                }
                IBrushConfig iBrushConfig7 = this.brushConfig;
                if (iBrushConfig7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    iBrushConfig7 = null;
                }
                if (iBrushConfig7.getIsShow()) {
                    View view8 = this.doodlePanelContainer;
                    if (view8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                        view8 = null;
                    }
                    view8.setOnClickListener(null);
                    View view9 = this.doodlePanelContainer;
                    if (view9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                        view9 = null;
                    }
                    view9.setClickable(false);
                    IBrushConfig iBrushConfig8 = this.brushConfig;
                    if (iBrushConfig8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    } else {
                        iBrushConfig2 = iBrushConfig8;
                    }
                    iBrushConfig2.dismiss();
                }
                updateActiveToolView();
                return;
            }
            IBrushConfig iBrushConfig9 = this.brushConfig;
            if (iBrushConfig9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                iBrushConfig9 = null;
            }
            if (iBrushConfig9.getIsShow()) {
                View view10 = this.doodlePanelContainer;
                if (view10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                    view10 = null;
                }
                view10.setOnClickListener(null);
                View view11 = this.doodlePanelContainer;
                if (view11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                    view11 = null;
                }
                view11.setClickable(false);
                IBrushConfig iBrushConfig10 = this.brushConfig;
                if (iBrushConfig10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
                    iBrushConfig10 = null;
                }
                iBrushConfig10.dismiss();
            }
            hideBackgroundContainer();
            View view12 = this.activeTool;
            View view13 = this.brushEraser;
            if (view13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
            } else {
                view2 = view13;
            }
            if (view12 == view2) {
                toggleElementContainer();
            }
            View view14 = this.activeTool;
            if (view14 != null) {
                Folme.useAt(view14).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(transY));
            }
            updateUnSelectToolView();
            Folme.useAt(newActiveView).state().to(ViewProperty.TRANSLATION_Y, 0);
            this.activeTool = newActiveView;
            updateActiveToolView();
            View view15 = this.colorView;
            Intrinsics.checkNotNull(view15);
            View view16 = this.activeTool;
            Intrinsics.checkNotNull(view16);
            if (view16.getId() != R.id.brush_eraser) {
                View view17 = this.activeTool;
                Intrinsics.checkNotNull(view17);
                if (view17.getId() != R.id.tool_picture) {
                    View view18 = this.activeTool;
                    Intrinsics.checkNotNull(view18);
                    z = view18.getId() != R.id.tool_background;
                }
            }
            view15.setClickable(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean activeTool$lambda$38(DoodleFragment doodleFragment, View view, MotionEvent motionEvent) {
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null) {
            doodleView.setShowConfigPopupWindow(false);
        }
        return doodleFragment.dismissConfig();
    }

    private final void hideBackgroundContainer() {
        if (isAdded() && getIsShowBackgroundConfig()) {
            View view = this.doodlePanelContainer;
            View view2 = null;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                view = null;
            }
            view.setOnClickListener(null);
            View view3 = this.doodlePanelContainer;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
            } else {
                view2 = view3;
            }
            view2.setClickable(false);
            Folme.useAt(this.backgroundConfigContainer).state().to(ViewProperty.TRANSLATION_Y, 20).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
            this.isShowBackgroundConfig = false;
            getHandler().postDelayed(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.hideBackgroundContainer$lambda$39(this.f$0);
                }
            }, 210L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideBackgroundContainer$lambda$39(DoodleFragment doodleFragment) {
        View view = doodleFragment.backgroundConfigContainer;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: isShowBackgroundContainer, reason: from getter */
    private final boolean getIsShowBackgroundConfig() {
        return this.isShowBackgroundConfig;
    }

    private final void showBackgroundContainer() {
        if (isAdded() && !getIsShowBackgroundConfig()) {
            DoodleView doodleView = this.doodleView;
            if (doodleView != null) {
                doodleView.setShowConfigPopupWindow(true);
            }
            this.isShowBackgroundConfig = true;
            View view = this.doodlePanelContainer;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodlePanelContainer");
                view = null;
            }
            view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda10
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return DoodleFragment.showBackgroundContainer$lambda$40(this.f$0, view2, motionEvent);
                }
            });
            getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.showBackgroundContainer$lambda$41(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean showBackgroundContainer$lambda$40(DoodleFragment doodleFragment, View view, MotionEvent motionEvent) {
        DoodleView doodleView = doodleFragment.doodleView;
        if (doodleView != null) {
            doodleView.setShowConfigPopupWindow(false);
        }
        return doodleFragment.dismissConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBackgroundContainer$lambda$41(DoodleFragment doodleFragment) {
        View view = doodleFragment.backgroundConfigContainer;
        if (view != null) {
            view.setVisibility(0);
        }
        View view2 = doodleFragment.backgroundConfigContainer;
        if (view2 != null) {
            view2.setTranslationY(20.0f);
        }
        View view3 = doodleFragment.backgroundConfigContainer;
        if (view3 != null) {
            view3.setAlpha(0.0f);
        }
        Folme.useAt(doodleFragment.backgroundConfigContainer).state().to(ViewProperty.TRANSLATION_Y, 0).to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateBrushViewColor() {
        if (isAdded()) {
            View view = this.activeTool;
            Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_pencil) {
                updatePencilViewWithCurrentColor(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_watercolor) {
                updateWaterColorViewWithCurrentColor(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_markpen) {
                updateMarkPenViewWithCurrentColor(true);
            } else if (numValueOf != null && numValueOf.intValue() == R.id.brush_normal) {
                updateNormalPenViewWithCurrentColor(true);
            }
        }
    }

    private final void updateActiveToolView() {
        if (isAdded()) {
            View view = this.activeTool;
            Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_pencil) {
                updatePencilView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_watercolor) {
                updateWaterColorView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_markpen) {
                updateMarkPenView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_normal) {
                updateNormalPenView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_eraser) {
                updateEraserView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.tool_picture) {
                updatePictureToolView(true);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.tool_text) {
                updateTextToolView(true);
            } else if (numValueOf != null && numValueOf.intValue() == R.id.tool_background) {
                updateBackgroundView(true);
            }
        }
    }

    private final void updateUnSelectToolView() {
        if (isAdded()) {
            View view = this.activeTool;
            Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_pencil) {
                updatePencilView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_watercolor) {
                updateWaterColorView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_markpen) {
                updateMarkPenView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_normal) {
                updateNormalPenView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.brush_eraser) {
                updateEraserView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.tool_picture) {
                updatePictureToolView(false);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == R.id.tool_text) {
                updateTextToolView(false);
            } else if (numValueOf != null && numValueOf.intValue() == R.id.tool_background) {
                updateBackgroundView(false);
            }
        }
    }

    private final void showActionBarView(boolean isShow) {
        if (isShow) {
            View view = this.actionBarContainer;
            if (view != null) {
                view.setVisibility(0);
            }
            Folme.useAt(this.actionBarContainer).state().setTo(ViewProperty.ALPHA, Float.valueOf(1.0f));
            return;
        }
        View view2 = this.actionBarContainer;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        Folme.useAt(this.actionBarContainer).state().setTo(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.actionBarDismissListener));
    }

    private final void switchImmerseMode(boolean isToImmerse) {
        this.isInImmerseMode = isToImmerse;
        this.isPanelInImmerseMode = isToImmerse;
        if (isToImmerse) {
            DisplayUtils displayUtils = DisplayUtils.INSTANCE;
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            final Rect displayCutout = displayUtils.getDisplayCutout(fragmentActivityRequireActivity);
            getBtnUnImmerse().setImportantForAccessibility(1);
            CardView cardView = this.btnPanelImmerse;
            if (cardView != null) {
                cardView.setImportantForAccessibility(1);
            }
            getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.switchImmerseMode$lambda$42(displayCutout, this);
                }
            });
            showActionBarView(false);
            showPaletteImmerseEntry(true);
            this.isPanelInImmerseMode = true;
            setPanelImmerseState(true);
            return;
        }
        getBtnUnImmerse().setImportantForAccessibility(2);
        CardView cardView2 = this.btnPanelImmerse;
        if (cardView2 != null) {
            cardView2.setImportantForAccessibility(2);
        }
        Folme.useAt(getBtnUnImmerse()).state().setTo(ViewProperty.ALPHA, Float.valueOf(0.0f));
        getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment.switchImmerseMode$lambda$43(this.f$0);
            }
        });
        showPaletteImmerseEntry(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchImmerseMode$lambda$42(Rect rect, DoodleFragment doodleFragment) {
        if (!rect.isEmpty() && doodleFragment.getBtnUnImmerse().getX() > rect.left && doodleFragment.getBtnUnImmerse().getX() < rect.right) {
            doodleFragment.getBtnUnImmerse().setTranslationX(-((doodleFragment.getBtnUnImmerse().getX() - rect.left) + doodleFragment.getBtnUnImmerse().getWidth()));
        }
        Folme.useAt(doodleFragment.getBtnUnImmerse()).state().setTo(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchImmerseMode$lambda$43(DoodleFragment doodleFragment) {
        doodleFragment.showActionBarView(true);
        doodleFragment.getBtnUnImmerse().setTranslationX(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setPanelImmerseState(boolean isToImmerse) {
        final Context context = getContext();
        if (context != null) {
            if (isToImmerse) {
                CardView cardView = this.btnPanelImmerse;
                if (cardView != null) {
                    cardView.setBackgroundResource(R.drawable.doodle_immerse_entry_show_bg);
                }
                CardView cardView2 = this.btnPanelImmerse;
                if (cardView2 != null) {
                    cardView2.setContentDescription(context.getString(R.string.doodle_action_show_tool_panel));
                }
                CardView cardView3 = this.btnPanelImmerse;
                if (cardView3 != null) {
                    cardView3.setCardElevation(10.0f);
                }
            } else {
                CardView cardView4 = this.btnPanelImmerse;
                if (cardView4 != null) {
                    cardView4.setBackgroundResource(R.drawable.doodle_immerse_entry_hide_bg);
                }
                CardView cardView5 = this.btnPanelImmerse;
                if (cardView5 != null) {
                    cardView5.setContentDescription(context.getString(R.string.doodle_action_hide_tool_panel));
                }
                CardView cardView6 = this.btnPanelImmerse;
                if (cardView6 != null) {
                    cardView6.setCardElevation(0.0f);
                }
            }
            CardView cardView7 = this.btnPanelImmerse;
            if (cardView7 != null) {
                ViewCompat.setAccessibilityDelegate(cardView7, new AccessibilityDelegateCompat() { // from class: com.miui.notes.doodle.feature.DoodleFragment$setPanelImmerseState$1$1$1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                        Intrinsics.checkNotNullParameter(host, "host");
                        Intrinsics.checkNotNullParameter(info, "info");
                        super.onInitializeAccessibilityNodeInfo(host, info);
                        info.setRoleDescription(context.getString(R.string.button_talkback));
                    }
                });
            }
            DoodleView doodleView = this.doodleView;
            if (doodleView != null) {
                ImageView imageView = this.btnPanelImmerseIcon;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnPanelImmerseIcon");
                    imageView = null;
                }
                imageView.setImageDrawable(createPanelImmerseIcon(context, isToImmerse, doodleView.getPen().getColorInt()));
            }
        }
    }

    private final void showPaletteImmerseEntry(boolean isShow) {
        Logger.INSTANCE.d(TAG, "showPaletteImmerseEntry: " + isShow);
        Float fValueOf = Float.valueOf(1.0f);
        Float fValueOf2 = Float.valueOf(0.0f);
        View view = null;
        if (isShow) {
            CardView cardView = this.btnPanelImmerse;
            if (cardView != null) {
                Folme.useAt(cardView).state().to(ViewProperty.ALPHA, fValueOf);
            }
            CardView cardView2 = this.btnPanelImmerse;
            if (cardView2 != null) {
                cardView2.setClickable(true);
            }
            View view2 = this.colorView;
            if (view2 != null) {
                Folme.useAt(view2).state().to(ViewProperty.ALPHA, fValueOf2, ViewProperty.TRANSLATION_X, Float.valueOf(-view2.getWidth()));
            }
            View view3 = this.toolPanelContainer;
            if (view3 != null) {
                view3.setClickable(false);
            }
            View view4 = this.toolPanelContainer;
            if (view4 != null) {
                Folme.useAt(view4).state().to(ViewProperty.ALPHA, fValueOf2, ViewProperty.TRANSLATION_Y, Float.valueOf(view4.getHeight()), new AnimConfig().addListeners(this.toolPanelDismissListener));
            }
            View view5 = this.elementEraserContainer;
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view5 = null;
            }
            view5.setClickable(false);
            View[] viewArr = new View[1];
            View view6 = this.elementEraserContainer;
            if (view6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view6 = null;
            }
            viewArr[0] = view6;
            Folme.useAt(viewArr).state().setTo(ViewProperty.ALPHA, fValueOf2, Float.valueOf(10.0f));
            View view7 = this.pixelEraserContainer;
            if (view7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
                view7 = null;
            }
            view7.setClickable(false);
            View[] viewArr2 = new View[1];
            View view8 = this.pixelEraserContainer;
            if (view8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            } else {
                view = view8;
            }
            viewArr2[0] = view;
            Folme.useAt(viewArr2).state().setTo(ViewProperty.ALPHA, fValueOf2);
            return;
        }
        CardView cardView3 = this.btnPanelImmerse;
        if (cardView3 != null) {
            cardView3.setClickable(false);
        }
        CardView cardView4 = this.btnPanelImmerse;
        if (cardView4 != null) {
            Folme.useAt(cardView4).state().to(ViewProperty.ALPHA, fValueOf2);
        }
        View view9 = this.colorView;
        if (view9 != null) {
            Folme.useAt(view9).state().to(ViewProperty.ALPHA, fValueOf, ViewProperty.TRANSLATION_X, fValueOf2);
        }
        View view10 = this.toolPanelContainer;
        if (view10 != null) {
            view10.setClickable(true);
        }
        View view11 = this.toolPanelContainer;
        if (view11 != null) {
            view11.setVisibility(0);
        }
        View view12 = this.toolPanelContainer;
        if (view12 != null) {
            Folme.useAt(view12).state().to(ViewProperty.ALPHA, fValueOf, ViewProperty.TRANSLATION_Y, fValueOf2);
        }
        View view13 = this.elementEraserContainer;
        if (view13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view13 = null;
        }
        view13.setClickable(true);
        View[] viewArr3 = new View[1];
        View view14 = this.elementEraserContainer;
        if (view14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view14 = null;
        }
        viewArr3[0] = view14;
        Folme.useAt(viewArr3).state().setTo(ViewProperty.ALPHA, fValueOf);
        View view15 = this.pixelEraserContainer;
        if (view15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view15 = null;
        }
        view15.setClickable(true);
        View[] viewArr4 = new View[1];
        View view16 = this.pixelEraserContainer;
        if (view16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
        } else {
            view = view16;
        }
        viewArr4[0] = view;
        Folme.useAt(viewArr4).state().setTo(ViewProperty.ALPHA, fValueOf);
    }

    private final void togglePaletteImmerseEntryState() {
        this.isPanelInImmerseMode = !this.isPanelInImmerseMode;
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$togglePaletteImmerseEntryState$mColorViewTransitionListener$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object toTag) {
                super.onBegin(toTag);
                Logger.INSTANCE.d("DoodleFragment", "onBegin: ");
                View colorView = this.this$0.getColorView();
                if (colorView != null) {
                    colorView.setClickable(false);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                View view;
                View view2;
                Intrinsics.checkNotNullParameter(toTag, "toTag");
                super.onComplete(toTag);
                Logger.INSTANCE.d("DoodleFragment", "onComplete: ");
                View colorView = this.this$0.getColorView();
                if (colorView != null) {
                    View view3 = this.this$0.activeTool;
                    colorView.setClickable((view3 == null || view3.getId() != R.id.brush_eraser) && ((view = this.this$0.activeTool) == null || view.getId() != R.id.tool_picture) && ((view2 = this.this$0.activeTool) == null || view2.getId() != R.id.tool_background));
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object toTag) {
                View view;
                View view2;
                super.onCancel(toTag);
                Logger.INSTANCE.d("DoodleFragment", "onCancel: ");
                View colorView = this.this$0.getColorView();
                if (colorView != null) {
                    View view3 = this.this$0.activeTool;
                    colorView.setClickable((view3 == null || view3.getId() != R.id.brush_eraser) && ((view = this.this$0.activeTool) == null || view.getId() != R.id.tool_picture) && ((view2 = this.this$0.activeTool) == null || view2.getId() != R.id.tool_background));
                }
            }
        };
        boolean z = this.isPanelInImmerseMode;
        Float fValueOf = Float.valueOf(0.0f);
        View view = null;
        if (z) {
            NotesTextStat.reportDoodleClickEvent(NotesTextStat.PEN_BAG_PACK_UP);
            IStateStyle iStateStyleState = Folme.useAt(this.colorView).state();
            ViewProperty viewProperty = ViewProperty.ALPHA;
            ViewProperty viewProperty2 = ViewProperty.TRANSLATION_X;
            View view2 = this.colorView;
            Intrinsics.checkNotNull(view2);
            iStateStyleState.to(viewProperty, fValueOf, viewProperty2, Float.valueOf(-view2.getWidth())).addListener(transitionListener);
            View view3 = this.toolPanelContainer;
            if (view3 != null) {
                view3.setClickable(false);
            }
            IStateStyle iStateStyleState2 = Folme.useAt(this.toolPanelContainer).state();
            ViewProperty viewProperty3 = ViewProperty.ALPHA;
            ViewProperty viewProperty4 = ViewProperty.TRANSLATION_Y;
            View view4 = this.toolPanelContainer;
            Intrinsics.checkNotNull(view4);
            iStateStyleState2.to(viewProperty3, fValueOf, viewProperty4, Float.valueOf(view4.getHeight()), new AnimConfig().addListeners(this.toolPanelDismissListener));
            View view5 = this.elementEraserContainer;
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view5 = null;
            }
            view5.setClickable(false);
            View[] viewArr = new View[1];
            View view6 = this.elementEraserContainer;
            if (view6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view6 = null;
            }
            viewArr[0] = view6;
            Folme.useAt(viewArr).state().setTo(ViewProperty.ALPHA, fValueOf);
            View view7 = this.pixelEraserContainer;
            if (view7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
                view7 = null;
            }
            view7.setClickable(false);
            View[] viewArr2 = new View[1];
            View view8 = this.pixelEraserContainer;
            if (view8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            } else {
                view = view8;
            }
            viewArr2[0] = view;
            Folme.useAt(viewArr2).state().setTo(ViewProperty.ALPHA, fValueOf);
        } else {
            NotesTextStat.reportDoodleClickEvent(NotesTextStat.PEN_BAG_EXPAND);
            Folme.useAt(this.colorView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ViewProperty.TRANSLATION_X, fValueOf).addListener(transitionListener);
            View view9 = this.toolPanelContainer;
            if (view9 != null) {
                view9.setClickable(true);
            }
            View view10 = this.toolPanelContainer;
            if (view10 != null) {
                view10.setVisibility(0);
            }
            Folme.useAt(this.toolPanelContainer).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ViewProperty.TRANSLATION_Y, fValueOf);
            View view11 = this.elementEraserContainer;
            if (view11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view11 = null;
            }
            view11.setClickable(true);
            View[] viewArr3 = new View[1];
            View view12 = this.elementEraserContainer;
            if (view12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
                view12 = null;
            }
            viewArr3[0] = view12;
            Folme.useAt(viewArr3).state().setTo(ViewProperty.ALPHA, Float.valueOf(1.0f));
            View view13 = this.pixelEraserContainer;
            if (view13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
                view13 = null;
            }
            view13.setClickable(true);
            View[] viewArr4 = new View[1];
            View view14 = this.pixelEraserContainer;
            if (view14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            } else {
                view = view14;
            }
            viewArr4[0] = view;
            Folme.useAt(viewArr4).state().setTo(ViewProperty.ALPHA, Float.valueOf(1.0f));
        }
        setPanelImmerseState(this.isPanelInImmerseMode);
    }

    public void updateDoodleBackground(int resId) {
        Window window;
        View decorView;
        View viewFindViewById;
        FragmentActivity activity = getActivity();
        if (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null || (viewFindViewById = decorView.findViewById(android.R.id.content)) == null) {
            return;
        }
        viewFindViewById.setBackgroundResource(resId);
    }

    public void reloadDoodleOnWindowChanged() {
        DoodleView doodleView;
        Deferred<Object> autoSaveJob;
        if (this.showLoadingDialogTask != null) {
            Logger.INSTANCE.d("DoodleLoadTest", "has reload task");
            return;
        }
        resetEditTextDialog();
        DoodleView doodleView2 = this.doodleView;
        boolean z = true;
        if (TextUtils.isEmpty(doodleView2 != null ? doodleView2.getTempDoodleFilePath() : null) && (doodleView = this.doodleView) != null && (autoSaveJob = doodleView.getAutoSaveJob()) != null && autoSaveJob.isCompleted()) {
            z = false;
        }
        resetDoodleDocument(z);
        resetTranslation();
        resetUndo();
        DoodleCacheManager.INSTANCE.clear();
    }

    private final void resetEditTextDialog() {
        DoodleEditTextDialog doodleEditTextDialog = this.doodleEditTextDialog;
        if (doodleEditTextDialog == null || !doodleEditTextDialog.isShowing()) {
            return;
        }
        doodleEditTextDialog.dismiss();
    }

    private final void resetUndo() {
        View view = this.btnRedo;
        if (view != null) {
            view.setEnabled(false);
        }
        View view2 = this.btnUndo;
        if (view2 != null) {
            view2.setEnabled(false);
        }
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.clearStack();
        }
    }

    public void updateBackgroundContainerMargin() {
        View view;
        if (isAdded() && (view = this.backgroundConfigContainer) != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (this.screenSpec.type == 1) {
                marginLayoutParams.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_background_container_margin_full));
            } else {
                View view2 = this.colorView;
                View view3 = null;
                Integer numValueOf = view2 != null ? Integer.valueOf(view2.getWidth()) : null;
                Intrinsics.checkNotNull(numValueOf);
                int iIntValue = numValueOf.intValue() + requireContext().getResources().getDimensionPixelSize(R.dimen.palette_panel_margin_start_full) + requireContext().getResources().getDimensionPixelSize(R.dimen.tool_panel_container_margin_start_full);
                View view4 = this.toolBackground;
                if (view4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolBackground");
                } else {
                    view3 = view4;
                }
                marginLayoutParams.setMarginEnd(this.displayWidth - ((iIntValue + view3.getRight()) - requireContext().getResources().getDimensionPixelSize(R.dimen.pad_background_container_margin_full)));
            }
            view.setLayoutParams(marginLayoutParams);
        }
    }

    private final void resetDoodleDocument(final boolean isFilePathNotEmpty) {
        DoodleBrushPath.INSTANCE.clearCache();
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.removeAllViews();
        }
        getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment.resetDoodleDocument$lambda$57(isFilePathNotEmpty, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void resetDoodleDocument$lambda$57(boolean z, final DoodleFragment doodleFragment) {
        Logger.INSTANCE.d("DoodleLoadTest", "view post");
        if (z) {
            doodleFragment.showLoadingDialogTask = new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.resetDoodleDocument$lambda$57$lambda$55(this.f$0);
                }
            };
        }
        Runnable runnable = doodleFragment.showLoadingDialogTask;
        if (runnable != null) {
            doodleFragment.getHandler().postDelayed(runnable, 500L);
        }
        BuildersKt__Builders_commonKt.launch$default(doodleFragment, Dispatchers.getIO(), null, new DoodleFragment$resetDoodleDocument$1$3(doodleFragment, z, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void resetDoodleDocument$lambda$57$lambda$55(DoodleFragment doodleFragment) {
        if (doodleFragment.getContext() != null) {
            ProgressDialog progressDialog = new ProgressDialog(doodleFragment.getContext());
            doodleFragment.loadDataDialog = progressDialog;
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("...");
            progressDialog.show();
        }
    }

    private final void resetTranslation() {
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.setDoodleTranslationX(0.0f);
        }
        DoodleView doodleView2 = this.doodleView;
        if (doodleView2 != null) {
            doodleView2.setDoodleTranslationY(0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007¢\u0006\u0004\b\n\u0010\u000bJ\b\u0010\f\u001a\u00020\u0007H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/doodle/feature/DoodleFragment$DoodleViewWrapper;", "Lcom/miui/doodle/feature/draw/DoodleView;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "listener", "Lcom/miui/doodle/base/IDoodleListener;", "isClipImageOnSave", "", "isCreateNewDoodle", "isFromRebuild", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/doodle/feature/DoodleFragment;Landroid/content/Context;Lcom/miui/doodle/base/IDoodleListener;ZZZ)V", NotesTextStat.UNDO, "clear", "", "onSaveToHistory", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    final class DoodleViewWrapper extends DoodleView {
        final /* synthetic */ DoodleFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DoodleViewWrapper(DoodleFragment doodleFragment, Context context, IDoodleListener listener, boolean z, boolean z2, boolean z3) {
            super(context, null, listener, null, z, false, false, 96, null);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.this$0 = doodleFragment;
        }

        public /* synthetic */ DoodleViewWrapper(DoodleFragment doodleFragment, Context context, IDoodleListener iDoodleListener, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(doodleFragment, context, iDoodleListener, (i & 4) != 0 ? true : z, (i & 8) != 0 ? true : z2, (i & 16) != 0 ? false : z3);
        }

        @Override // com.miui.doodle.feature.draw.DoodleView, com.miui.doodle.base.core.IDoodle
        public boolean undo() {
            View btnUndo;
            DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.this$0.touchGestureListener;
            IDoodle iDoodle = null;
            if (doodleOnTouchGestureListener == null) {
                Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                doodleOnTouchGestureListener = null;
            }
            doodleOnTouchGestureListener.setSelectedItem(null);
            boolean zUndo = super.undo();
            View btnRedo = this.this$0.getBtnRedo();
            if (btnRedo != null) {
                IDoodle iDoodle2 = this.this$0.doodle;
                if (iDoodle2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle2 = null;
                }
                btnRedo.setEnabled(iDoodle2.getRedoItemCount() > 0);
            }
            IDoodle iDoodle3 = this.this$0.doodle;
            if (iDoodle3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
            } else {
                iDoodle = iDoodle3;
            }
            if (iDoodle.getUndoItemCount() < 1 && (btnUndo = this.this$0.getBtnUndo()) != null) {
                btnUndo.setEnabled(false);
            }
            return zUndo;
        }

        @Override // com.miui.doodle.feature.draw.DoodleView, com.miui.doodle.base.core.IDoodle
        public void clear() {
            super.clear();
            try {
                DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.this$0.touchGestureListener;
                if (doodleOnTouchGestureListener == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                    doodleOnTouchGestureListener = null;
                }
                doodleOnTouchGestureListener.setSelectedItem(null);
            } catch (Exception unused) {
            }
            View btnRedo = this.this$0.getBtnRedo();
            if (btnRedo != null) {
                btnRedo.setEnabled(false);
            }
            View btnUndo = this.this$0.getBtnUndo();
            if (btnUndo != null) {
                btnUndo.setEnabled(false);
            }
        }

        @Override // com.miui.doodle.feature.draw.DoodleView
        public void onSaveToHistory() {
            super.onSaveToHistory();
            Handler handler = getHandler();
            final DoodleFragment doodleFragment = this.this$0;
            handler.post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$DoodleViewWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.DoodleViewWrapper.onSaveToHistory$lambda$0(doodleFragment);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSaveToHistory$lambda$0(DoodleFragment doodleFragment) {
            View btnUndo = doodleFragment.getBtnUndo();
            if (btnUndo != null) {
                btnUndo.setEnabled(true);
            }
            View btnRedo = doodleFragment.getBtnRedo();
            if (btnRedo != null) {
                btnRedo.setEnabled(false);
            }
        }
    }

    private final void updateBrushViewState(View view, String toolName, DoodlePen doodlePen, boolean isSelect) {
        Context context = getContext();
        if (context != null) {
            view.setForeground(createBrushFg(context, toolName, doodlePen.getColorInt()));
            view.setBackground(createBrushBg(context, toolName));
            Drawable background = view.getBackground();
            Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.TransitionDrawable");
            TransitionDrawable transitionDrawable = (TransitionDrawable) background;
            if (isSelect) {
                transitionDrawable.startTransition(200);
                DoodleView doodleView = this.doodleView;
                if (doodleView != null) {
                    doodleView.setEditMode(false);
                }
                DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.touchGestureListener;
                if (doodleOnTouchGestureListener == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                    doodleOnTouchGestureListener = null;
                }
                doodleOnTouchGestureListener.setSelectedItem(null);
                return;
            }
            transitionDrawable.startTransition(0);
            transitionDrawable.reverseTransition(200);
        }
    }

    private final void updateToolViewState(View view, String toolName, boolean isSelect) {
        Context context = getContext();
        if (context != null) {
            view.setBackground(createToolBg(context, toolName));
            Drawable background = view.getBackground();
            Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.TransitionDrawable");
            TransitionDrawable transitionDrawable = (TransitionDrawable) background;
            if (isSelect) {
                transitionDrawable.startTransition(200);
            } else {
                transitionDrawable.startTransition(0);
                transitionDrawable.reverseTransition(200);
            }
        }
    }

    private final void updatePencilView(boolean isSelect) {
        View view = this.brushPencil;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
            view = null;
        }
        updateBrushViewState(view, "pencil", DoodlePenManager.INSTANCE.getPencil(), isSelect);
    }

    private final void updatePencilViewWithCurrentColor(boolean isSelect) {
        View view = this.brushPencil;
        IDoodle iDoodle = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPencil");
            view = null;
        }
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
        } else {
            iDoodle = iDoodle2;
        }
        updateBrushViewState(view, "pencil", iDoodle.getPen(), isSelect);
    }

    private final void updateWaterColorView(boolean isSelect) {
        View view = this.brushWaterColor;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
            view = null;
        }
        updateBrushViewState(view, "watercolor", DoodlePenManager.INSTANCE.getWatercolor(), isSelect);
    }

    private final void updateWaterColorViewWithCurrentColor(boolean isSelect) {
        View view = this.brushWaterColor;
        IDoodle iDoodle = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushWaterColor");
            view = null;
        }
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
        } else {
            iDoodle = iDoodle2;
        }
        updateBrushViewState(view, "watercolor", iDoodle.getPen(), isSelect);
    }

    private final void updateMarkPenView(boolean isSelect) {
        View view = this.brushMarkPen;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
            view = null;
        }
        updateBrushViewState(view, "markpen", DoodlePenManager.INSTANCE.getMarkPen(), isSelect);
    }

    private final void updateMarkPenViewWithCurrentColor(boolean isSelect) {
        View view = this.brushMarkPen;
        IDoodle iDoodle = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushMarkPen");
            view = null;
        }
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
        } else {
            iDoodle = iDoodle2;
        }
        updateBrushViewState(view, "markpen", iDoodle.getPen(), isSelect);
    }

    private final void updateNormalPenView(boolean isSelect) {
        View view = this.brushNormal;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
            view = null;
        }
        updateBrushViewState(view, "pen", DoodlePenManager.INSTANCE.getNormal(), isSelect);
    }

    private final void updateNormalPenViewWithCurrentColor(boolean isSelect) {
        View view = this.brushNormal;
        IDoodle iDoodle = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushNormal");
            view = null;
        }
        IDoodle iDoodle2 = this.doodle;
        if (iDoodle2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
        } else {
            iDoodle = iDoodle2;
        }
        updateBrushViewState(view, "pen", iDoodle.getPen(), isSelect);
    }

    private final void updateEraserView(boolean isSelect) {
        View view = this.brushEraser;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushEraser");
            view = null;
        }
        updateToolViewState(view, "eraser", isSelect);
        if (isSelect) {
            DoodleView doodleView = this.doodleView;
            if (doodleView != null) {
                doodleView.setEditMode(false);
            }
            DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.touchGestureListener;
            if (doodleOnTouchGestureListener == null) {
                Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                doodleOnTouchGestureListener = null;
            }
            doodleOnTouchGestureListener.setSelectedItem(null);
        }
    }

    private final void updateBackgroundView(boolean isSelect) {
        View view = this.toolBackground;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolBackground");
            view = null;
        }
        updateToolViewState(view, "background", isSelect);
        if (isSelect) {
            showBackgroundContainer();
        }
    }

    private final void updatePictureToolView(boolean isSelect) {
        View view = this.toolPicture;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolPicture");
            view = null;
        }
        updateToolViewState(view, "picture", isSelect);
        if (isSelect) {
            Utils.INSTANCE.selectImage(this, 1, 0);
            this.isChoosingImage = true;
        }
    }

    private final void updateTextToolView(boolean isSelect) {
        IDoodle iDoodle;
        String string;
        if (getContext() != null) {
            View view = this.toolText;
            DoodleOnTouchGestureListener doodleOnTouchGestureListener = null;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("toolText");
                view = null;
            }
            updateToolViewState(view, "text", isSelect);
            if (isSelect) {
                IDoodle iDoodle2 = this.doodle;
                if (iDoodle2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle = null;
                } else {
                    iDoodle = iDoodle2;
                }
                Context context = getContext();
                if (context == null || (string = context.getString(R.string.doodle_tool_text_hint)) == null) {
                    string = "";
                }
                String str = string;
                IDoodle iDoodle3 = this.doodle;
                if (iDoodle3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle3 = null;
                }
                float finalSize = iDoodle3.getPen().getFinalSize();
                IDoodle iDoodle4 = this.doodle;
                if (iDoodle4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle4 = null;
                }
                DoodleText doodleText = new DoodleText(iDoodle, str, iDoodle4.getPen().getColorInt(), finalSize, null, 0.0f, 0.0f, null, null, 0, 1008, null);
                DoodleView doodleView = this.doodleView;
                if (doodleView != null) {
                    DoodleOnTouchGestureListener doodleOnTouchGestureListener2 = this.touchGestureListener;
                    if (doodleOnTouchGestureListener2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                    } else {
                        doodleOnTouchGestureListener = doodleOnTouchGestureListener2;
                    }
                    doodleView.addTextElement(doodleText, doodleOnTouchGestureListener);
                }
            }
        }
    }

    private final Drawable createBrushFg(Context context, String toolName, int colorInt) {
        String packageName = context.getPackageName();
        String str = "doodle_ic_brush_" + toolName;
        String str2 = str + "_head";
        int identifier = context.getResources().getIdentifier(str2, "drawable", packageName);
        int identifier2 = context.getResources().getIdentifier(str + "_body", "drawable", packageName);
        Drawable[] drawableArr = new Drawable[2];
        Drawable drawable = ContextCompat.getDrawable(context, identifier);
        Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
        drawableArr[0] = drawableMutate;
        if (drawableMutate != null) {
            drawableMutate.setTint(colorInt);
        }
        Drawable drawable2 = ContextCompat.getDrawable(context, identifier2);
        drawableArr[1] = drawable2 != null ? drawable2.mutate() : null;
        return new LayerDrawable(drawableArr);
    }

    private final Drawable createBrushBg(Context context, String toolName) {
        String packageName = context.getPackageName();
        String str = "doodle_ic_brush_" + toolName;
        String str2 = str + "_shadow_s";
        int identifier = context.getResources().getIdentifier(str2, "drawable", packageName);
        Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(str + "_shadow_u", "drawable", packageName));
        Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
        Drawable drawable2 = ContextCompat.getDrawable(context, identifier);
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{drawableMutate, drawable2 != null ? drawable2.mutate() : null});
        transitionDrawable.setCrossFadeEnabled(true);
        return transitionDrawable;
    }

    private final Drawable createToolBg(Context context, String toolName) {
        String packageName = context.getPackageName();
        String str = "doodle_ic_tool_" + toolName;
        String str2 = str + "_shadow_s";
        String str3 = str + "_shadow_u";
        int identifier = context.getResources().getIdentifier(str2, "drawable", packageName);
        int identifier2 = context.getResources().getIdentifier(str3, "drawable", packageName);
        int identifier3 = context.getResources().getIdentifier(str + "_body", "drawable", packageName);
        Drawable[] drawableArr = new Drawable[2];
        Drawable[] drawableArr2 = new Drawable[2];
        Drawable drawable = ContextCompat.getDrawable(context, identifier2);
        drawableArr[0] = drawable != null ? drawable.mutate() : null;
        Drawable drawable2 = ContextCompat.getDrawable(context, identifier);
        drawableArr2[0] = drawable2 != null ? drawable2.mutate() : null;
        Drawable drawable3 = ContextCompat.getDrawable(context, identifier3);
        drawableArr[1] = drawable3 != null ? drawable3.mutate() : null;
        Drawable drawable4 = ContextCompat.getDrawable(context, identifier3);
        drawableArr2[1] = drawable4 != null ? drawable4.mutate() : null;
        return new TransitionDrawable(new Drawable[]{new LayerDrawable(drawableArr), new LayerDrawable(drawableArr2)});
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        View view;
        View view2;
        DoodleView doodleView;
        super.onActivityResult(requestCode, resultCode, data);
        View view3 = this.colorView;
        Intrinsics.checkNotNull(view3);
        View view4 = this.activeTool;
        view3.setClickable((view4 == null || view4.getId() != R.id.brush_eraser) && ((view = this.activeTool) == null || view.getId() != R.id.tool_picture) && ((view2 = this.activeTool) == null || view2.getId() != R.id.tool_background));
        if (resultCode == -1 && requestCode == 0) {
            this.isChoosingImage = false;
            DoodleOnTouchGestureListener doodleOnTouchGestureListener = null;
            Uri data2 = data != null ? data.getData() : null;
            if (data2 == null || this.doodle == null || getContext() == null || (doodleView = this.doodleView) == null) {
                return;
            }
            DoodleOnTouchGestureListener doodleOnTouchGestureListener2 = this.touchGestureListener;
            if (doodleOnTouchGestureListener2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
            } else {
                doodleOnTouchGestureListener = doodleOnTouchGestureListener2;
            }
            doodleView.addImageElementByUri(data2, doodleOnTouchGestureListener);
        }
    }

    private final Drawable createPanelImmerseIcon(Context context, boolean isToImmerse, int colorInt) {
        Drawable drawable;
        if (isToImmerse) {
            drawable = ContextCompat.getDrawable(context, R.drawable.doodle_ic_panel_immerse_entry);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.doodle_ic_panel_unimmerse_entry);
        }
        if (drawable != null) {
            drawable.setTint(colorInt);
        }
        return drawable;
    }

    public final void saveDoodle() {
        com.miui.common.tool.Logger.INSTANCE.d(TAG, "saveDoodle");
        this.isSaved = true;
        Context context = getContext();
        if (context != null) {
            showSaveProgressDialog(context);
            IDoodle iDoodle = this.doodle;
            if (iDoodle != null) {
                if (iDoodle == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                    iDoodle = null;
                }
                iDoodle.save();
            }
        }
    }

    private final void showSaveProgressDialog(Context context) {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            if (progressDialog.isShowing()) {
                return;
            }
        }
        ProgressDialog progressDialog2 = new ProgressDialog(context, 2131951646);
        progressDialog2.setIndeterminate(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.setCancelable(false);
        progressDialog2.setMessage(context.getResources().getString(R.string.doodle_saving));
        progressDialog2.show();
        this.progressDialog = progressDialog2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideSaveProgressDialog() {
        getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment.hideSaveProgressDialog$lambda$64(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideSaveProgressDialog$lambda$64(DoodleFragment doodleFragment) {
        ProgressDialog progressDialog = doodleFragment.progressDialog;
        if (progressDialog != null && doodleFragment.isAdded() && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        doodleFragment.progressDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showProcessingDialog(Context context, long delay) {
        Logger.INSTANCE.d(TAG, "showProcessingDialog " + this + " delay " + delay + StringUtils.SPACE);
        this.showLoadingDialogTask = new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment.showProcessingDialog$lambda$66(this.f$0);
            }
        };
        this.loadDataDialog = new ProgressDialog(context);
        if (this.showLoadingDialogTask != null) {
            MainHandler<DoodleFragment> handler = getHandler();
            Runnable runnable = this.showLoadingDialogTask;
            Intrinsics.checkNotNull(runnable);
            handler.postDelayed(runnable, delay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showProcessingDialog$lambda$66(DoodleFragment doodleFragment) {
        ProgressDialog progressDialog = doodleFragment.loadDataDialog;
        if (progressDialog != null) {
            Logger.INSTANCE.d(TAG, "do showProcessingDialog");
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("...");
            progressDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideProcessingDialog() {
        ProgressDialog progressDialog;
        Logger.INSTANCE.d(TAG, "hideProcessingDialog ");
        getDoodleContainer().removeCallbacks(this.showLoadingDialogTask);
        if (isAdded() && (progressDialog = this.loadDataDialog) != null && progressDialog.isShowing()) {
            final ProgressDialog progressDialog2 = this.loadDataDialog;
            this.loadDataDialog = null;
            getHandler().post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleFragment.hideProcessingDialog$lambda$67(progressDialog2);
                }
            });
        } else {
            ProgressDialog progressDialog3 = this.loadDataDialog;
            if (progressDialog3 != null) {
                progressDialog3.dismissWithoutAnimation();
            }
        }
        this.loadDataDialog = null;
        this.showLoadingDialogTask = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideProcessingDialog$lambda$67(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Error dismissing dialog: " + e.getMessage());
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        Deferred<File> mDeferredDoodleFile;
        ProgressDialog progressDialog;
        ProgressDialog progressDialog2;
        super.onDestroyView();
        Runnable runnable = this.showLoadingDialogTask;
        if (runnable != null) {
            getHandler().removeCallbacks(runnable);
        }
        if (isAdded() && (progressDialog = this.loadDataDialog) != null && progressDialog.isShowing() && (progressDialog2 = this.loadDataDialog) != null) {
            progressDialog2.dismiss();
        }
        DoodleView doodleView = this.doodleView;
        if (doodleView != null && (mDeferredDoodleFile = doodleView.getMDeferredDoodleFile()) != null) {
            Job.DefaultImpls.cancel$default((Job) mDeferredDoodleFile, (CancellationException) null, 1, (Object) null);
        }
        this.loadDataDialog = null;
        this.showLoadingDialogTask = null;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Logger.INSTANCE.d(TAG, "onDestroy: ");
        DoodleView doodleView = this.doodleView;
        if (doodleView != null) {
            doodleView.clearAutoSaveTask();
        }
        DoodleView doodleView2 = this.doodleView;
        if (doodleView2 != null) {
            doodleView2.clear();
        }
        DoodleOnTouchGestureListener doodleOnTouchGestureListener = this.touchGestureListener;
        if (doodleOnTouchGestureListener != null) {
            if (doodleOnTouchGestureListener == null) {
                Intrinsics.throwUninitializedPropertyAccessException("touchGestureListener");
                doodleOnTouchGestureListener = null;
            }
            doodleOnTouchGestureListener.clearViewAnimator();
        }
        clearViewAnimator();
        getHandler().removeCallbacksAndMessages(null);
        Logger.INSTANCE.d(TAG, "onDestroy,removeAllMessagesAndCallbacks ");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        smallWindowAdapter();
    }

    private final void clearViewAnimator() {
        ViewPropertyAnimator viewPropertyAnimator = this.elementEraserContainerAnimate;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        ViewPropertyAnimator viewPropertyAnimator2 = this.pixelEraserContainerAnimate;
        if (viewPropertyAnimator2 != null) {
            viewPropertyAnimator2.cancel();
        }
        ViewPropertyAnimator viewPropertyAnimator3 = this.toolPanelContainerAnimate;
        if (viewPropertyAnimator3 != null) {
            viewPropertyAnimator3.cancel();
        }
        ViewPropertyAnimator viewPropertyAnimator4 = this.colorViewAnimate;
        if (viewPropertyAnimator4 != null) {
            viewPropertyAnimator4.cancel();
        }
        View view = this.colorView;
        if (view != null) {
            view.clearAnimation();
        }
        ViewPropertyAnimator viewPropertyAnimator5 = this.tipViewAnimate;
        if (viewPropertyAnimator5 != null) {
            viewPropertyAnimator5.cancel();
        }
        ViewPropertyAnimator viewPropertyAnimator6 = this.tipViewAnimate1;
        if (viewPropertyAnimator6 != null) {
            viewPropertyAnimator6.cancel();
        }
        View view2 = this.elementEraserContainer;
        IBrushConfig iBrushConfig = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementEraserContainer");
            view2 = null;
        }
        view2.clearAnimation();
        View view3 = this.pixelEraserContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraserContainer");
            view3 = null;
        }
        view3.clearAnimation();
        View view4 = this.toolPanelContainer;
        if (view4 != null) {
            view4.clearAnimation();
        }
        View view5 = this.elementTipView;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("elementTipView");
            view5 = null;
        }
        view5.clearAnimation();
        View view6 = this.pixelTipView;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
            view6 = null;
        }
        view6.clearAnimation();
        IPalette iPalette = this.palette;
        if (iPalette == null) {
            Intrinsics.throwUninitializedPropertyAccessException("palette");
            iPalette = null;
        }
        iPalette.clearViewAnimator();
        IBrushConfig iBrushConfig2 = this.brushConfig;
        if (iBrushConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushConfig");
        } else {
            iBrushConfig = iBrushConfig2;
        }
        iBrushConfig.clearViewAnimator();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.d(TAG, "onStop: " + this.isSaved);
        if (this.isSaved || this.doodle == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "post saveRunnable");
        if (ActivityManager.isUserAMonkey()) {
            getHandler().post(getSaveTask());
        } else {
            if (this.isChoosingImage) {
                return;
            }
            getHandler().postDelayed(getSaveTask(), 30000L);
        }
    }

    private final void smallWindowAdapter() {
        final View view = this.mRootView;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.doodle.feature.DoodleFragment$smallWindowAdapter$1$1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    Intrinsics.checkNotNullParameter(v, "v");
                    Intrinsics.checkNotNullParameter(insets, "insets");
                    Activity activity = com.miui.common.tool.UIUtils.getActivity(this.this$0.getContext());
                    if (activity != null && com.miui.common.tool.UIUtils.isInMultiWindowMode(activity)) {
                        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(v);
                        if (rootWindowInsets == null) {
                            return insets;
                        }
                        Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        Intrinsics.checkNotNullExpressionValue(insetsIgnoringVisibility, "getInsetsIgnoringVisibility(...)");
                        view.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                        return insets;
                    }
                    view.setPadding(0, 0, 0, 0);
                    return insets;
                }
            });
        }
    }
}
