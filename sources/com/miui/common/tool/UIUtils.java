package com.miui.common.tool;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.google.common.net.HttpHeaders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.notes.widget.NoteWidgetProvider_2x1_Notes;
import com.miui.notes.widget.NoteWidgetProvider_2x1_Todo;
import com.miui.notes.widget.NoteWidgetProvider_2x2;
import com.miui.notes.widget.NoteWidgetProvider_4x2;
import com.miui.notes.widget.NoteWidgetProvider_4x4;
import defpackage.R2;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.app.MiuiFreeFormManager;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.core.util.SystemProperties;
import miuix.core.util.WindowUtils;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: UIUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0015\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0007H\u0007J\u0012\u0010-\u001a\u00020\u00072\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u001a\u00100\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/2\b\u00101\u001a\u0004\u0018\u00010\u0014H\u0007J\u001a\u00102\u001a\u00020\r2\b\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\u001dH\u0007J\u0010\u00106\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/J\u0012\u00107\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0012\u00108\u001a\u00020\u00072\b\u00103\u001a\u0004\u0018\u000104H\u0007J\u0012\u00108\u001a\u00020\u00072\b\u00109\u001a\u0004\u0018\u00010:H\u0007J\u0012\u0010;\u001a\u00020\u00072\b\u00109\u001a\u0004\u0018\u00010:H\u0007J\u0006\u0010<\u001a\u00020\u001dJ\u0006\u0010=\u001a\u00020\u001dJ\u0012\u0010>\u001a\u00020\u001d2\b\u00103\u001a\u0004\u0018\u000104H\u0007J\u0014\u0010?\u001a\u0004\u0018\u00010@2\b\u0010A\u001a\u0004\u0018\u00010BH\u0007J \u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020\u0007H\u0007J(\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020\u00072\u0006\u0010E\u001a\u00020FH\u0007JB\u0010G\u001a\u00020B2\u0006\u0010.\u001a\u00020/2\b\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020\u00072\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00072\u0006\u0010K\u001a\u00020\u0007H\u0007J\u0012\u0010L\u001a\u00020\r2\b\u0010M\u001a\u0004\u0018\u00010@H\u0007J@\u0010N\u001a\u00020B2\u0006\u0010.\u001a\u00020/2\u0006\u0010M\u001a\u00020@2\u0006\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020\u00072\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00072\u0006\u0010K\u001a\u00020\u0007H\u0007J.\u0010O\u001a\u0004\u0018\u00010@2\b\u0010P\u001a\u0004\u0018\u00010@2\u0006\u0010Q\u001a\u00020\u00072\u0006\u0010R\u001a\u00020\u00072\b\b\u0002\u0010S\u001a\u00020\u001dH\u0007J\u0018\u0010T\u001a\u00020@2\u0006\u0010M\u001a\u00020@2\u0006\u0010U\u001a\u00020VH\u0007J\u0018\u0010W\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/2\u0006\u0010X\u001a\u00020IH\u0007J\u0010\u0010Y\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0007J\u0012\u0010Z\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0010\u0010[\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/H\u0007J\u0012\u0010\\\u001a\u00020\u001d2\b\u00103\u001a\u0004\u0018\u000104H\u0007J\u0012\u0010]\u001a\u00020\r2\b\u00103\u001a\u0004\u0018\u00010^H\u0007J\u000e\u0010_\u001a\u00020\r2\u0006\u00103\u001a\u00020^J\u0016\u0010`\u001a\u00020\r2\u0006\u00109\u001a\u00020:2\u0006\u0010a\u001a\u00020\u0007J\u0010\u0010b\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0007J\u0010\u0010c\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0007J\u0016\u0010d\u001a\u00020\r2\u0006\u0010e\u001a\u00020\u00142\u0006\u0010f\u001a\u00020\u0007J\u0018\u0010g\u001a\u00020\r2\u0006\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020\u0007H\u0007J\u0016\u0010k\u001a\u00020\r2\u0006\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020\u0007J\u0018\u0010l\u001a\u00020\u001d2\b\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010m\u001a\u00020\u001dJ\u0012\u0010n\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0010\u0010u\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/H\u0007J\u0010\u0010v\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/H\u0007J\u0018\u0010x\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010y\u001a\u00020VJ\u001a\u0010z\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010y\u001a\u00020VH\u0007J\u0010\u0010{\u001a\u00020\r2\u0006\u0010.\u001a\u00020/H\u0007J\u000e\u0010|\u001a\u00020\r2\u0006\u0010.\u001a\u00020/J\u0018\u0010}\u001a\u00020\r2\u0006\u0010.\u001a\u00020/2\u0006\u0010~\u001a\u00020\u0007H\u0007J\"\u0010\u007f\u001a\u00020\r2\u0006\u0010.\u001a\u00020/2\u0007\u0010\u0080\u0001\u001a\u00020\u00072\u0007\u0010\u0081\u0001\u001a\u00020\u0007H\u0007J\u001b\u0010\u0082\u0001\u001a\u00020\r2\u0006\u0010.\u001a\u00020/2\n\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001J\u001b\u0010\u0085\u0001\u001a\u00020\r2\u0006\u0010.\u001a\u00020/2\n\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001J\u001b\u0010\u0086\u0001\u001a\u00020\r2\u0006\u0010.\u001a\u00020/2\n\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001J\u0011\u0010\u0089\u0001\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/H\u0007J\u0011\u0010\u008a\u0001\u001a\u00020\r2\u0006\u0010.\u001a\u00020/H\u0007J\u0011\u0010\u008b\u0001\u001a\u00020\r2\u0006\u0010e\u001a\u00020\u0014H\u0007J\u0011\u0010\u008c\u0001\u001a\u00020\r2\u0006\u0010e\u001a\u00020\u0014H\u0007J\u0011\u0010\u008d\u0001\u001a\u00020\u00142\u0006\u0010e\u001a\u00020\u0014H\u0007J\u0012\u0010\u008e\u0001\u001a\u00020\u00142\u0007\u0010\u008f\u0001\u001a\u00020\u0014H\u0007J\u0013\u0010\u0090\u0001\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0013\u0010\u0091\u0001\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u001d\u0010\u0092\u0001\u001a\u00020\u00072\b\u0010.\u001a\u0004\u0018\u00010/2\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H\u0007J\u0011\u0010\u0095\u0001\u001a\u00020\r2\u0006\u00103\u001a\u000204H\u0007J\u0013\u0010\u0096\u0001\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0013\u0010\u0097\u0001\u001a\u00020\u001d2\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0015\u0010\u0098\u0001\u001a\u00020\u001d2\n\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u0001H\u0007J\u0012\u0010\u009d\u0001\u001a\u00020\u00072\u0007\u0010\u009e\u0001\u001a\u00020/H\u0007J\u0012\u0010\u009f\u0001\u001a\u00020\u00072\u0007\u0010\u009e\u0001\u001a\u00020/H\u0007J\u0015\u0010 \u0001\u001a\u0004\u0018\u0001042\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0015\u0010¡\u0001\u001a\u0004\u0018\u0001042\b\u0010e\u001a\u0004\u0018\u00010\u0014H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0003\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001e\u0010\u0003\u001a\u0004\b\u001c\u0010\u001fR\u001a\u0010 \u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0003\u001a\u0004\b \u0010\u001fR\u001a\u0010\"\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b#\u0010\u0003\u001a\u0004\b\"\u0010\u001fR\u001a\u0010$\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0003\u001a\u0004\b$\u0010\u001fR\u0011\u0010&\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b&\u0010\u001fR\u001a\u0010'\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b(\u0010\u0003\u001a\u0004\b'\u0010\u001fR\u001a\u0010)\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b*\u0010\u0003\u001a\u0004\b)\u0010\u001fR\u001a\u0010+\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u0003\u001a\u0004\b+\u0010\u001fR\u001a\u0010o\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bp\u0010\u0003\u001a\u0004\bo\u0010\u001fR\u001a\u0010q\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\br\u0010\u0003\u001a\u0004\bq\u0010\u001fR\u001a\u0010s\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bt\u0010\u0003\u001a\u0004\bs\u0010\u001fR\u001a\u0010v\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\f\u0012\u0004\bw\u0010\u0003\u001a\u0004\bv\u0010\u001fR\u0014\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0005\n\u0003\u0010\u0088\u0001R\u001d\u0010\u009b\u0001\u001a\u00020\u001d8FX\u0087\u0004¢\u0006\u000e\u0012\u0005\b\u009c\u0001\u0010\u0003\u001a\u0005\b\u009b\u0001\u0010\u001f¨\u0006¢\u0001"}, d2 = {"Lcom/miui/common/tool/UIUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sHandler", "Landroid/os/Handler;", "FREEFORM_MIUI12", "", "MINI_FREEFORM", "MIUI_DKT_MODE", "ELDERLY_MAM_ENABLE", "ELDERLY_MAM_DISABLE", "runOnUIThread", "", "runnable", "Ljava/lang/Runnable;", "delay", "", "removeRunnable", "inflateView", "Landroid/view/View;", "parent", "Landroid/view/ViewGroup;", "layoutRes", "miuiVersion", "getMiuiVersion$annotations", "getMiuiVersion", "()I", "isMiuiXSdkSupported", "", "isMiuiXSdkSupported$annotations", "()Z", "isMiuiXISdkSupported", "isMiuiXISdkSupported$annotations", "isMiuiXIISdkSupported", "isMiuiXIISdkSupported$annotations", "isMiuiXII_H_SdkSupported", "isMiuiXII_H_SdkSupported$annotations", "isMiuiXIIISdkSupported", "isMIUI14AndAbove", "isMIUI14AndAbove$annotations", "isHyperOsAndAbove", "isHyperOsAndAbove$annotations", "isMiproFontSupported", "isMiproFontSupported$annotations", "getNavigationBarHeight", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getNavigationBarHeightByWindow", "rootView", "setFullScreenMode", "activity", "Landroid/app/Activity;", "isFullScreen", "isMiuiDktMode", "isElderlyManEnable", "getRealNavigationBarHeight", "window", "Landroid/view/Window;", "getAppHeight", "hasNotchScreen", "hasHideNotchScreenMode2", "isMiniSmallWindowMode", "drawableToBitmap", "Landroid/graphics/Bitmap;", "drawable", "Landroid/graphics/drawable/Drawable;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "config", "Landroid/graphics/Bitmap$Config;", "drawableToRound", "radius", "", "borderColor", "borderWidth", "releaseBitmap", DoodleBitmap.KEY_BITMAP, "bitmapToRound", "scaleBitmap", HttpHeaders.ReferrerPolicyValues.ORIGIN, "newWidth", "newHeight", "recycleOld", "cropBitmap", "cropRect", "Landroid/graphics/Rect;", "dip2px", "dpValue", "getStatusBarHeight", "isInFullWindowGestureMode", "hasNavigationBar", "isInMultiWindowMode", "setNavigationTrans", "Lcom/miui/common/base/BaseActivity;", "setForceNavigationTrans", "setNavigationColor", "color", "getRealHeight", "getRealWidth", "setBottomMargin", "view", "bottomMargin", "setDialogFullScreen", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/app/Dialog;", "addBottom", "setDialogPaddingBottom", "setStatusBarLightMode", "isFontColorDark", "isNightMode", "isAboveAndroidR", "isAboveAndroidR$annotations", "isAboveAndroidS", "isAboveAndroidS$annotations", "isAboveAndroidV", "isAboveAndroidV$annotations", "isPcDensity", "isSmallWindowMode", "isSmallWindowMode$annotations", "isInMultiScreenTop", "windowVisibleFrame", "isInMultiScreenBottom", "updateWidgetNotes", "updateWidgetTodo", "updateWidgetTodoNums", "unFinishTodoNums", "updateWidget", "widgetId", "widgetType", "updateWidget2x2", "widgetIds", "", "updateWidget4x2", "updateWidget4x4", "IS_MIUI_WIDGET_SUPPORT", "Ljava/lang/Boolean;", "isMiuiWidgetSupported", "setMiuiWidgetSupported", "setFolmeHoverEffect", "setFolmeCommonTouchEffect", "getActionBarOverlayLayout", "getActionBarOverlayLayoutByDecorView", "decorView", "isInNarrowScreen", "isInJ18NarrowScreen", "getScreenStatus", "originScreenSpec", "Lmiuix/responsive/map/ScreenSpec;", "checkOrientationInFoldDevice", "isFoldInternalScreen", "isBrowseMode", "isFreeFormScreen", "configuration", "Landroid/content/res/Configuration;", "isNeedRefreshWidget", "isNeedRefreshWidget$annotations", "getWindowHeight", "mContext", "getWindowWidth", "getActivity", "getActivityFromView", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class UIUtils {
    public static final int ELDERLY_MAM_DISABLE = 0;
    public static final int ELDERLY_MAM_ENABLE = 1;
    public static final int FREEFORM_MIUI12 = 2;
    private static Boolean IS_MIUI_WIDGET_SUPPORT = null;
    public static final int MINI_FREEFORM = 1;
    public static final int MIUI_DKT_MODE = 1;
    public static final UIUtils INSTANCE = new UIUtils();
    private static final Handler sHandler = new Handler(BaseNoteApp.INSTANCE.getInstance().getMainLooper());

    @JvmStatic
    public static /* synthetic */ void getMiuiVersion$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isAboveAndroidR$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isAboveAndroidS$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isAboveAndroidV$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isHyperOsAndAbove$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMIUI14AndAbove$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiproFontSupported$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiuiXIISdkSupported$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiuiXII_H_SdkSupported$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiuiXISdkSupported$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiuiXSdkSupported$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isNeedRefreshWidget$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSmallWindowMode$annotations() {
    }

    @JvmStatic
    public static final Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        return scaleBitmap$default(bitmap, i, i2, false, 8, null);
    }

    private UIUtils() {
    }

    @JvmStatic
    public static final void runOnUIThread(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        sHandler.post(runnable);
    }

    @JvmStatic
    public static final void runOnUIThread(Runnable runnable, long delay) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        sHandler.postDelayed(runnable, delay);
    }

    @JvmStatic
    public static final void removeRunnable(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        sHandler.removeCallbacks(runnable);
    }

    @JvmStatic
    public static final View inflateView(ViewGroup parent, int layoutRes) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    public static final int getMiuiVersion() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0);
    }

    public static final boolean isMiuiXSdkSupported() {
        return getMiuiVersion() >= 8;
    }

    public static final boolean isMiuiXISdkSupported() {
        return getMiuiVersion() >= 9;
    }

    public static final boolean isMiuiXIISdkSupported() {
        return getMiuiVersion() >= 10;
    }

    public static final boolean isMiuiXII_H_SdkSupported() {
        return getMiuiVersion() >= 11;
    }

    public final boolean isMiuiXIIISdkSupported() {
        return getMiuiVersion() >= 12;
    }

    public static final boolean isMIUI14AndAbove() {
        return getMiuiVersion() >= 14;
    }

    public static final boolean isHyperOsAndAbove() {
        return getMiuiVersion() > 14;
    }

    public static final boolean isMiproFontSupported() {
        if (RomUtils.isInternationalBuild()) {
            return false;
        }
        return isMiuiXISdkSupported();
    }

    @JvmStatic
    public static final int getNavigationBarHeight(Context context) {
        int navigationBarHeightFromProp;
        UIUtils uIUtils = INSTANCE;
        if (isInFullWindowGestureMode(context) && !uIUtils.isMiuiDktMode(context)) {
            navigationBarHeightFromProp = (isMiuiXIISdkSupported() && DisplayUtils.isSupportGestureLine(context) && DisplayUtils.isEnableGestureLine(context)) ? DisplayUtils.getNavigationBarHeightFromProp(context) : 0;
        } else {
            navigationBarHeightFromProp = DisplayUtils.getNavigationBarHeightFromProp(context);
        }
        int i = navigationBarHeightFromProp >= 0 ? navigationBarHeightFromProp : 0;
        Logger.INSTANCE.i("UIUtils", "getNavigationBarHeight = " + i);
        return i;
    }

    @JvmStatic
    public static final int getNavigationBarHeightByWindow(Context context, View rootView) {
        Intrinsics.checkNotNullParameter(context, "context");
        int navigationBarHeight = 0;
        if (rootView != null && Build.VERSION.SDK_INT >= 30) {
            navigationBarHeight = rootView.getRootWindowInsets() != null ? rootView.getRootWindowInsets().getInsets(WindowInsets.Type.navigationBars()).bottom : 0;
            DisplayCutout cutout = WindowUtils.getDisplay(context).getCutout();
            if (cutout != null) {
                if (cutout.getBoundingRectBottom().height() > 0) {
                    navigationBarHeight += cutout.getBoundingRectBottom().height();
                } else if (cutout.getBoundingRectRight().width() > 0 && navigationBarHeight - cutout.getBoundingRectRight().width() > 0) {
                    navigationBarHeight -= cutout.getBoundingRectRight().width();
                }
            }
        }
        if (navigationBarHeight == 0) {
            navigationBarHeight = getNavigationBarHeight(context);
        }
        Logger.INSTANCE.i("UIUtils", "getNavigationBarHeightByWindow = " + navigationBarHeight);
        return navigationBarHeight;
    }

    @JvmStatic
    public static final void setFullScreenMode(Activity activity, boolean isFullScreen) {
        if (activity == null) {
            return;
        }
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView().setSystemUiVisibility(isFullScreen ? systemUiVisibility | R2.color.theme_default_high_light_color : systemUiVisibility & (-5895));
    }

    public final boolean isMiuiDktMode(Context context) {
        ContentResolver contentResolver;
        if (context != null) {
            try {
                contentResolver = context.getContentResolver();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            contentResolver = null;
        }
        return Settings.System.getInt(contentResolver, "miui_dkt_mode") == 1;
    }

    @JvmStatic
    public static final boolean isElderlyManEnable(Context context) {
        ContentResolver contentResolver;
        if (context != null) {
            try {
                contentResolver = context.getContentResolver();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            contentResolver = null;
        }
        return Settings.System.getInt(contentResolver, "elderly_mode", 0) == 1;
    }

    @JvmStatic
    public static final int getRealNavigationBarHeight(Activity activity) {
        if (isInMultiWindowMode(activity)) {
            return 0;
        }
        return getNavigationBarHeight(activity);
    }

    @JvmStatic
    public static final int getRealNavigationBarHeight(Window window) {
        if (window == null) {
            return 0;
        }
        return getNavigationBarHeight(window.getContext());
    }

    @JvmStatic
    public static final int getAppHeight(Window window) {
        View decorView;
        Rect rect = new Rect();
        if (window != null && (decorView = window.getDecorView()) != null) {
            decorView.getWindowVisibleDisplayFrame(rect);
        }
        return rect.height();
    }

    public final boolean hasNotchScreen() {
        int i;
        try {
            i = SystemProperties.getInt("ro.miui.notch", 0);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        return i > 0;
    }

    public final boolean hasHideNotchScreenMode2() {
        return Settings.Global.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "force_black_v2", 0) == 1;
    }

    @JvmStatic
    public static final boolean isMiniSmallWindowMode(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (MiuiFreeFormManager.getMiuiFreeformVersion() == 2) {
            return 1 == MiuiFreeFormManager.getMiuiFreeformStackShowState(activity);
        }
        MiuiFreeFormManager.MiuiFreeFormStackInfo freeFormStackInfoByActivity = MiuiFreeFormManager.getFreeFormStackInfoByActivity(activity);
        return freeFormStackInfoByActivity != null && 1 == freeFormStackInfoByActivity.windowState;
    }

    @JvmStatic
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            Logger.INSTANCE.d("AudioSpanDebug", "drawableToBitmap drawable size zero ");
        }
        int iMax = (int) Math.max(drawable.getIntrinsicWidth(), 1.0d);
        int iMax2 = (int) Math.max(drawable.getIntrinsicHeight(), 1.0d);
        drawable.setBounds(0, 0, iMax, iMax2);
        if (drawable.getOpacity() != -1) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
        } else {
            Bitmap.Config config2 = Bitmap.Config.RGB_565;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMax, iMax2, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        drawable.getIntrinsicWidth();
        drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, height);
        if (drawable.getOpacity() != -1) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
        } else {
            Bitmap.Config config2 = Bitmap.Config.RGB_565;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final Bitmap drawableToBitmap(Drawable drawable, int width, int height, Bitmap.Config config) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        Intrinsics.checkNotNullParameter(config, "config");
        drawable.setBounds(0, 0, width, height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final Drawable drawableToRound(Context context, Drawable drawable, int width, int height, float radius, int borderColor, int borderWidth) {
        Intrinsics.checkNotNullParameter(context, "context");
        UIUtils uIUtils = INSTANCE;
        Bitmap bitmapDrawableToBitmap = drawableToBitmap(drawable);
        Intrinsics.checkNotNull(bitmapDrawableToBitmap);
        return uIUtils.bitmapToRound(context, bitmapDrawableToBitmap, width, height, radius, borderColor, borderWidth);
    }

    @JvmStatic
    public static final void releaseBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public final Drawable bitmapToRound(Context context, Bitmap bitmap, int width, int height, float radius, int borderColor, int borderWidth) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        float f = width;
        int height2 = (int) (bitmap.getHeight() * ((1.0f * f) / bitmap.getWidth()));
        if (height2 < height) {
            height = height2;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Bitmap bitmapScaleBitmap$default = scaleBitmap$default(bitmap, width, height2, false, 8, null);
        Intrinsics.checkNotNull(bitmapScaleBitmap$default);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(0.0f, 0.0f, f, height);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Path path = new Path();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapScaleBitmap$default, 0.0f, 0.0f, paint);
        if (borderWidth > 0) {
            paint.setXfermode(null);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            float f2 = radius + 2;
            path.addRoundRect(rectF, f2, f2, Path.Direction.CW);
            canvas.drawPath(path, paint);
        }
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmapCreateBitmap);
        if (bitmapScaleBitmap$default != null) {
            bitmapScaleBitmap$default.isRecycled();
        }
        bitmapCreateBitmap.isRecycled();
        return bitmapDrawable;
    }

    public static /* synthetic */ Bitmap scaleBitmap$default(Bitmap bitmap, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            z = true;
        }
        return scaleBitmap(bitmap, i, i2, z);
    }

    @JvmStatic
    public static final Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight, boolean recycleOld) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / width, newHeight / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        if (!origin.isRecycled() && recycleOld) {
            origin.recycle();
        }
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final Bitmap cropBitmap(Bitmap bitmap, Rect cropRect) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(cropRect, "cropRect");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, cropRect.left, cropRect.top, cropRect.width(), cropRect.height(), (Matrix) null, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final int dip2px(Context context, float dpValue) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @JvmStatic
    public static final int getStatusBarHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object objNewInstance = cls.newInstance();
            Intrinsics.checkNotNullExpressionValue(objNewInstance, "newInstance(...)");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(objNewInstance).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @JvmStatic
    public static final boolean isInFullWindowGestureMode(Context context) {
        if (context == null) {
            context = BaseNoteApp.INSTANCE.getInstance();
        }
        return Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
    }

    @JvmStatic
    public static final boolean hasNavigationBar(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isInFullWindowGestureMode(context)) {
            return false;
        }
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            Intrinsics.checkNotNull(objInvoke);
            Object objInvoke2 = objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]));
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke2).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    @JvmStatic
    public static final boolean isInMultiWindowMode(Activity activity) {
        if (activity == null) {
            return false;
        }
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("isInMultiWindowMode", new Class[0]);
            declaredMethod.setAccessible(true);
            Boolean bool = (Boolean) declaredMethod.invoke(activity, new Object[0]);
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        } catch (IllegalAccessException e) {
            Logger logger = Logger.INSTANCE;
            String message = e.getMessage();
            Intrinsics.checkNotNull(message);
            logger.d("UIUtils", message);
            return false;
        } catch (NoSuchMethodException e2) {
            Logger logger2 = Logger.INSTANCE;
            String message2 = e2.getMessage();
            Intrinsics.checkNotNull(message2);
            logger2.d("UIUtils", message2);
            return false;
        } catch (InvocationTargetException e3) {
            Logger logger3 = Logger.INSTANCE;
            String message3 = e3.getMessage();
            Intrinsics.checkNotNull(message3);
            logger3.d("UIUtils", message3);
            return false;
        }
    }

    @JvmStatic
    public static final void setNavigationTrans(BaseActivity activity) {
        Window window;
        Window window2;
        Window window3;
        if (isInFullWindowGestureMode(activity)) {
            if (activity != null && (window = activity.getWindow()) != null) {
                window.addFlags(DataSpace.RANGE_FULL);
            }
        } else if (activity != null && (window3 = activity.getWindow()) != null) {
            window3.clearFlags(DataSpace.RANGE_FULL);
        }
        if (activity == null || (window2 = activity.getWindow()) == null) {
            return;
        }
        window2.addFlags(67108864);
    }

    public final void setForceNavigationTrans(BaseActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (!isInFullWindowGestureMode(activity)) {
            activity.getWindow().clearFlags(DataSpace.RANGE_FULL);
        } else {
            activity.getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        activity.getWindow().addFlags(67108864);
    }

    public final void setNavigationColor(Window window, int color) {
        Intrinsics.checkNotNullParameter(window, "window");
        window.setNavigationBarColor(color);
    }

    @Deprecated(message = "")
    @JvmStatic
    public static final int getRealHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Deprecated(message = "")
    @JvmStatic
    public static final int getRealWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public final void setBottomMargin(View view, int bottomMargin) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.bottomMargin = bottomMargin;
        view.setLayoutParams(marginLayoutParams);
    }

    @JvmStatic
    public static final void setDialogFullScreen(Dialog dialog, int addBottom) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        UIUtils uIUtils = INSTANCE;
        if (isAboveAndroidR()) {
            return;
        }
        try {
            if (DualScreenManager.INSTANCE.isPcActivityLiving()) {
                return;
            }
            Window window = dialog.getWindow();
            Intrinsics.checkNotNull(window);
            window.addFlags(Integer.MIN_VALUE);
            uIUtils.setDialogPaddingBottom(dialog, addBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setDialogPaddingBottom(Dialog dialog, int addBottom) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Window window = dialog.getWindow();
        Intrinsics.checkNotNull(window);
        View viewFindViewById = window.getDecorView().findViewById(R.id.content);
        viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), viewFindViewById.getPaddingBottom() + addBottom);
    }

    public final boolean setStatusBarLightMode(Window window, boolean isFontColorDark) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (isFontColorDark) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @JvmStatic
    public static final boolean isNightMode(Context context) {
        return (BaseNoteApp.INSTANCE.getInstance().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static final boolean isAboveAndroidR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public static final boolean isAboveAndroidS() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public static final boolean isAboveAndroidV() {
        return Build.VERSION.SDK_INT >= 35;
    }

    @JvmStatic
    public static final boolean isPcDensity(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDisplayMetrics().density == 1.0f || context.getResources().getDisplayMetrics().density <= 1.31f;
    }

    @JvmStatic
    public static final boolean isSmallWindowMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Configuration configuration = context.getResources().getConfiguration();
        try {
            Field declaredField = configuration.getClass().getDeclaredField("windowConfiguration");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(configuration);
            Intrinsics.checkNotNull(obj);
            Object objInvoke = obj.getClass().getDeclaredMethod("getWindowingMode", new Class[0]).invoke(obj, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Int");
            return ((Integer) objInvoke).intValue() == 5;
        } catch (Exception e) {
            Logger.INSTANCE.d("SmallWindowUtils", "error in isSmallWindowMode " + e);
        }
    }

    public static final boolean isSmallWindowMode() {
        return isSmallWindowMode(BaseNoteApp.INSTANCE.getInstance());
    }

    public final boolean isInMultiScreenTop(Context context, Rect windowVisibleFrame) {
        Intrinsics.checkNotNullParameter(windowVisibleFrame, "windowVisibleFrame");
        DisplayMetrics realScreenSize = DisplayUtils.getRealScreenSize(context);
        if (windowVisibleFrame.left == 0 && windowVisibleFrame.right == realScreenSize.widthPixels) {
            int i = (int) (realScreenSize.heightPixels * 0.75f);
            if (windowVisibleFrame.top >= 0 && windowVisibleFrame.bottom <= i) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final boolean isInMultiScreenBottom(Context context, Rect windowVisibleFrame) {
        Intrinsics.checkNotNullParameter(windowVisibleFrame, "windowVisibleFrame");
        DisplayMetrics realScreenSize = DisplayUtils.getRealScreenSize(context);
        if (windowVisibleFrame.left == 0 && windowVisibleFrame.right == realScreenSize.widthPixels) {
            if (windowVisibleFrame.top >= ((int) (realScreenSize.heightPixels * 0.2f))) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final void updateWidgetNotes(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NoteWidgetProvider_2x1_Notes.Companion companion = NoteWidgetProvider_2x1_Notes.INSTANCE;
        Intrinsics.checkNotNull(appWidgetManager);
        companion.updateWidget(context, appWidgetManager);
    }

    public final void updateWidgetTodo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NoteWidgetProvider_2x1_Todo.Companion companion = NoteWidgetProvider_2x1_Todo.INSTANCE;
        Intrinsics.checkNotNull(appWidgetManager);
        companion.updateWidget(context, appWidgetManager, -1);
    }

    @JvmStatic
    public static final void updateWidgetTodoNums(Context context, int unFinishTodoNums) {
        Intrinsics.checkNotNullParameter(context, "context");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NoteWidgetProvider_2x1_Todo.Companion companion = NoteWidgetProvider_2x1_Todo.INSTANCE;
        Intrinsics.checkNotNull(appWidgetManager);
        companion.updateWidget(context, appWidgetManager, unFinishTodoNums);
    }

    @JvmStatic
    public static final void updateWidget(Context context, int widgetId, int widgetType) {
        Intrinsics.checkNotNullParameter(context, "context");
        NoteWidgetProvider.Companion companion = NoteWidgetProvider.INSTANCE;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Intrinsics.checkNotNullExpressionValue(appWidgetManager, "getInstance(...)");
        companion.updateWidgetView(context, appWidgetManager, widgetId, widgetType);
        Logger.INSTANCE.i("NoteWidgetProvider", "user updateWidget widgetId=" + widgetId + " widgetType=" + widgetType);
    }

    public final void updateWidget2x2(Context context, int[] widgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setClass(context, NoteWidgetProvider_2x2.class);
        intent.putExtra("appWidgetIds", widgetIds);
        context.sendBroadcast(intent);
    }

    public final void updateWidget4x2(Context context, int[] widgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setClass(context, NoteWidgetProvider_4x2.class);
        intent.putExtra("appWidgetIds", widgetIds);
        context.sendBroadcast(intent);
    }

    public final void updateWidget4x4(Context context, int[] widgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setClass(context, NoteWidgetProvider_4x4.class);
        intent.putExtra("appWidgetIds", widgetIds);
        context.sendBroadcast(intent);
    }

    @JvmStatic
    public static final boolean isMiuiWidgetSupported(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (IS_MIUI_WIDGET_SUPPORT == null) {
            setMiuiWidgetSupported(context);
        }
        Boolean bool = IS_MIUI_WIDGET_SUPPORT;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    @JvmStatic
    public static final void setMiuiWidgetSupported(Context context) {
        boolean z;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://com.miui.personalassistant.widget.external"), "isMiuiWidgetSupported", (String) null, (Bundle) null);
            z = bundleCall != null ? bundleCall.getBoolean("isMiuiWidgetSupported") : false;
        } catch (Exception e) {
            Logger logger = Logger.INSTANCE;
            String message = e.getMessage();
            Intrinsics.checkNotNull(message);
            logger.i("NoteWidgetProvider", message);
        }
        Logger.INSTANCE.i("NoteWidgetProvider", "isMiuiWidgetSupported:" + z);
        IS_MIUI_WIDGET_SUPPORT = Boolean.valueOf(z);
    }

    @JvmStatic
    public static final void setFolmeHoverEffect(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(view, new AnimConfig[0]);
    }

    @JvmStatic
    public static final void setFolmeCommonTouchEffect(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    @JvmStatic
    public static final View getActionBarOverlayLayout(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        View rootView = view.getRootView();
        Intrinsics.checkNotNullExpressionValue(rootView, "getRootView(...)");
        return getActionBarOverlayLayoutByDecorView(rootView);
    }

    @JvmStatic
    public static final View getActionBarOverlayLayoutByDecorView(View decorView) {
        Intrinsics.checkNotNullParameter(decorView, "decorView");
        ViewGroup viewGroup = (ViewGroup) decorView.findViewById(ReflectUtil.INSTANCE.getMiuixAppcompatViewId("action_bar_overlay_layout"));
        if (viewGroup == null) {
            viewGroup = (ViewGroup) decorView;
        }
        return viewGroup;
    }

    @JvmStatic
    public static final boolean isInNarrowScreen(Context context) {
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(context);
        int iMin = (int) Math.min(screenSizeDp[0], screenSizeDp[1]);
        Logger.INSTANCE.d("isInNarrowScreen", "realWidth:" + iMin);
        return iMin <= 394;
    }

    @JvmStatic
    public static final boolean isInJ18NarrowScreen(Context context) {
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(context);
        int iMin = (int) Math.min(screenSizeDp[0], screenSizeDp[1]);
        Logger.INSTANCE.d("isInNarrowScreen", "realWidth:" + iMin);
        return iMin <= 350;
    }

    @JvmStatic
    public static final int getScreenStatus(Context context, ScreenSpec originScreenSpec) {
        Intrinsics.checkNotNullParameter(originScreenSpec, "originScreenSpec");
        if (RomUtils.isFoldDevice() && isInNarrowScreen(context) && originScreenSpec.screenMode == 4103) {
            return 4097;
        }
        return originScreenSpec.screenMode;
    }

    @JvmStatic
    public static final void checkOrientationInFoldDevice(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (RomUtils.isPadDevice()) {
            return;
        }
        if (RomUtils.isFoldDevice() && !Intrinsics.areEqual(Build.DEVICE, "cetus")) {
            if (!isFoldInternalScreen(activity)) {
                activity.setRequestedOrientation(1);
                return;
            } else {
                activity.setRequestedOrientation(-1);
                return;
            }
        }
        activity.setRequestedOrientation(1);
    }

    @JvmStatic
    public static final boolean isFoldInternalScreen(Context context) {
        DisplayMetrics realScreenSize = DisplayUtils.getRealScreenSize(context);
        return realScreenSize.widthPixels > 1500 && realScreenSize.heightPixels > 1500;
    }

    @JvmStatic
    public static final boolean isBrowseMode(Context context) {
        if ((context instanceof Activity) && !((Activity) context).isInMultiWindowMode()) {
            return false;
        }
        if (context == null) {
            context = BaseNoteApp.INSTANCE.getInstance();
        }
        return (RomUtils.isPadMode() || !isFreeFormScreen(context.getResources().getConfiguration())) && !isSmallWindowMode() && (!RomUtils.isPadMode() || isInNarrowScreen(context)) && (DisplayUtils.getRealScreenOrientation(context) == 2 || context.getResources().getConfiguration().screenHeightDp < 300);
    }

    @JvmStatic
    public static final boolean isFreeFormScreen(Configuration configuration) {
        if (configuration != null) {
            String string = configuration.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (StringsKt.contains$default((CharSequence) string, (CharSequence) "mWindowingMode=freeform", false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isNeedRefreshWidget() {
        return RomUtils.isInternationalBuild() && !RomUtils.isPadMode() && BaseNoteApp.INSTANCE.getInstance().getResources().getDisplayMetrics().densityDpi >= 600;
    }

    @JvmStatic
    public static final int getWindowHeight(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Object systemService = mContext.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Rect bounds = null;
        WindowMetrics currentWindowMetrics = Build.VERSION.SDK_INT >= 30 ? ((WindowManager) systemService).getCurrentWindowMetrics() : null;
        if (currentWindowMetrics != null && Build.VERSION.SDK_INT >= 30) {
            bounds = currentWindowMetrics.getBounds();
        }
        if (bounds != null) {
            return bounds.height();
        }
        return 0;
    }

    @JvmStatic
    public static final int getWindowWidth(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Object systemService = mContext.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Rect bounds = null;
        WindowMetrics currentWindowMetrics = Build.VERSION.SDK_INT >= 30 ? ((WindowManager) systemService).getCurrentWindowMetrics() : null;
        if (currentWindowMetrics != null && Build.VERSION.SDK_INT >= 30) {
            bounds = currentWindowMetrics.getBounds();
        }
        if (bounds != null) {
            return bounds.width();
        }
        return 0;
    }

    @JvmStatic
    public static final Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        if (context instanceof ContextThemeWrapper) {
            return getActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        com.miui.notes.base.utils.Logger.INSTANCE.e("UIUtils", "Didn't find Activity!");
        return null;
    }

    @JvmStatic
    public static final Activity getActivityFromView(View view) {
        if (view == null) {
            return null;
        }
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }
}
