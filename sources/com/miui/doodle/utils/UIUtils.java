package com.miui.doodle.utils;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.google.common.net.HttpHeaders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.schema.HtmlParser;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.core.util.SystemProperties;

/* JADX INFO: compiled from: UIUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\fJ\u0006\u0010\u001b\u001a\u00020\fJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u001e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0005J(\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\b\u0010\"\u001a\u0004\u0018\u00010#J>\u0010$\u001a\u00020\u001f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J>\u0010)\u001a\u00020\u001f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J.\u0010+\u001a\u0004\u0018\u00010\u001d2\b\u0010,\u001a\u0004\u0018\u00010\u001d2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u00052\b\b\u0002\u0010/\u001a\u00020\fH\u0007J\u0018\u00100\u001a\u00020\u001d2\b\u0010*\u001a\u0004\u0018\u00010\u001d2\u0006\u00101\u001a\u000202J\u0016\u00103\u001a\u00020&2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u00104\u001a\u00020&J\u000e\u00105\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u00106\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u00107\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u00108\u001a\u00020\f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0010\u00109\u001a\u00020:2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0016\u0010;\u001a\u00020:2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010<\u001a\u00020\u0005J\u000e\u0010=\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010>\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010?\u001a\u00020:2\u0006\u0010@\u001a\u00020\u00072\u0006\u0010A\u001a\u00020\u0005J\u0016\u0010B\u001a\u00020:2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u0005J\u0016\u0010F\u001a\u00020:2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u0005J\u0018\u0010G\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010H\u001a\u00020\fJ\u000e\u0010I\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010J\u001a\u00020\f2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020LJ\u0016\u0010J\u001a\u00020\f2\u0006\u0010K\u001a\u0002022\u0006\u0010M\u001a\u000202J\u0016\u0010J\u001a\u00020\f2\u0006\u0010K\u001a\u0002022\u0006\u0010M\u001a\u00020LJ&\u0010N\u001a\u00020&2\u0006\u0010O\u001a\u00020&2\u0006\u0010P\u001a\u00020&2\u0006\u0010Q\u001a\u00020&2\u0006\u0010R\u001a\u00020&J\b\u0010S\u001a\u00020\fH\u0002J\u0010\u0010T\u001a\u00020\u00052\b\u0010U\u001a\u0004\u0018\u00010VJ\u0010\u0010W\u001a\u00020\u00052\b\u0010U\u001a\u0004\u0018\u00010VJ\u001c\u0010X\u001a\u00020\u00052\b\u0010Y\u001a\u0004\u0018\u00010V2\b\u0010Z\u001a\u0004\u0018\u00010VH\u0002J\u0010\u0010[\u001a\u00020:2\b\u0010@\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\\\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010]\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010^\u001a\u00020\u0005J\u000e\u0010_\u001a\u00020:2\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\u000e\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u000f\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\r¨\u0006`"}, d2 = {"Lcom/miui/doodle/utils/UIUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SYSTEM_UI_FLAG_LIGHT_STATUS_BAR", "", "inflateView", "Landroid/view/View;", "parent", "Landroid/view/ViewGroup;", "layoutRes", "isMiuiXSdkSupported", "", "()Z", "isMiuiXISdkSupported", "isMiuiXIISdkSupported", "isMiproFontSupported", "getNavigationBarHeight", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getRealNavigationBarHeight", "activity", "Landroid/app/Activity;", "window", "Landroid/view/Window;", "getAppHeight", "hasNotchScreen", "hasHideNotchScreenMode2", "drawableToBitmap", "Landroid/graphics/Bitmap;", "drawable", "Landroid/graphics/drawable/Drawable;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "config", "Landroid/graphics/Bitmap$Config;", "drawableToRound", "radius", "", "borderColor", "borderWidth", "bitmapToRound", DoodleBitmap.KEY_BITMAP, "scaleBitmap", HttpHeaders.ReferrerPolicyValues.ORIGIN, "newWidth", "newHeight", "recycleOld", "cropBitmap", "cropRect", "Landroid/graphics/Rect;", "dip2px", "dpValue", "getStatusBarHeight", "isInFullWindowGestureMode", "hasNavigationBar", "isInMultiWindowMode", "setNavigationTrans", "", "setNavigationColor", "color", "getRealHeight", "getRealWidth", "setBottomMargin", "view", "bottomMargin", "setDialogFullScreen", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/app/Dialog;", "addBottom", "setDialogPaddingBottom", "setStatusBarLightMode", "isFontColorDark", "isNightMode", "detectIntersect", HtmlParser.LinkCardElement.TAG, "Landroid/graphics/RectF;", "b", "dotsDistance", "x1", "y1", "x2", "y2", "isAboveAndroidR", "getMiuixAppcompatViewId", "idName", "", "getAndroidInternalViewId", "getReflectField", "className", "fieldName", "setFolmeHoverEffect", "isInNarrowScreen", "getScreenStatus", "originResponsiveLayoutState", "checkOrientationInFoldDevice", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class UIUtils {
    public static final UIUtils INSTANCE = new UIUtils();
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    public final Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        return scaleBitmap$default(this, bitmap, i, i2, false, 8, null);
    }

    private UIUtils() {
    }

    public final View inflateView(ViewGroup parent, int layoutRes) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return viewInflate;
    }

    public final boolean isMiuiXSdkSupported() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0) >= 8;
    }

    public final boolean isMiuiXISdkSupported() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0) >= 9;
    }

    public final boolean isMiuiXIISdkSupported() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0) >= 10;
    }

    public final boolean isMiproFontSupported() {
        if (RomUtils.INSTANCE.isInternationalBuild()) {
            return false;
        }
        return isMiuiXISdkSupported();
    }

    public final int getNavigationBarHeight(Context context) {
        int navigationBarHeightFromProp;
        Intrinsics.checkNotNullParameter(context, "context");
        if (isInFullWindowGestureMode(context)) {
            navigationBarHeightFromProp = (isMiuiXIISdkSupported() && DisplayUtils.isSupportGestureLine(context) && DisplayUtils.isEnableGestureLine(context)) ? DisplayUtils.getNavigationBarHeightFromProp(context) : 0;
        } else {
            navigationBarHeightFromProp = DisplayUtils.getNavigationBarHeightFromProp(context);
        }
        int i = navigationBarHeightFromProp >= 0 ? navigationBarHeightFromProp : 0;
        Logger.INSTANCE.i("UIUtils", "getNavigationBarHeight = " + i);
        return i;
    }

    public final int getRealNavigationBarHeight(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isInMultiWindowMode(activity)) {
            return 0;
        }
        return getNavigationBarHeight(activity);
    }

    public final int getRealNavigationBarHeight(Window window) {
        Intrinsics.checkNotNullParameter(window, "window");
        Context context = window.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return getNavigationBarHeight(context);
    }

    public final int getAppHeight(Window window) {
        Intrinsics.checkNotNullParameter(window, "window");
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
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
        return Settings.Global.getInt(DoodleApplication.INSTANCE.getAppInstance().getContentResolver(), "force_black_v2", 0) == 1;
    }

    public final Bitmap drawableToBitmap(Drawable drawable) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        if (drawable.getOpacity() != -1) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
        } else {
            Bitmap.Config config2 = Bitmap.Config.RGB_565;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public final Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
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

    public final Bitmap drawableToBitmap(Drawable drawable, int width, int height, Bitmap.Config config) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        drawable.setBounds(0, 0, width, height);
        Intrinsics.checkNotNull(config);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public final Drawable drawableToRound(Context context, Drawable drawable, int width, int height, float radius, int borderColor, int borderWidth) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        return bitmapToRound(context, drawableToBitmap(drawable), width, height, radius, borderColor, borderWidth);
    }

    public final Drawable bitmapToRound(Context context, Bitmap bitmap, int width, int height, float radius, int borderColor, int borderWidth) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        float f = width;
        int height2 = (int) (bitmap.getHeight() * ((1.0f * f) / bitmap.getWidth()));
        int i = height;
        if (height2 < i) {
            i = height2;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, i, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Bitmap bitmapScaleBitmap$default = scaleBitmap$default(this, bitmap, width, height2, false, 8, null);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(0.0f, 0.0f, f, i);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Intrinsics.checkNotNull(bitmapScaleBitmap$default);
        canvas.drawBitmap(bitmapScaleBitmap$default, 0.0f, 0.0f, paint);
        if (borderWidth > 0) {
            paint.setXfermode(null);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            float f2 = 2 + radius;
            canvas.drawRoundRect(rectF, f2, f2, paint);
        }
        return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
    }

    public static /* synthetic */ Bitmap scaleBitmap$default(UIUtils uIUtils, Bitmap bitmap, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            z = true;
        }
        return uIUtils.scaleBitmap(bitmap, i, i2, z);
    }

    public final Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight, boolean recycleOld) {
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

    public final Bitmap cropBitmap(Bitmap bitmap, Rect cropRect) {
        Intrinsics.checkNotNullParameter(cropRect, "cropRect");
        Intrinsics.checkNotNull(bitmap);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, cropRect.left, cropRect.top, cropRect.width(), cropRect.height(), (Matrix) null, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        return bitmapCreateBitmap;
    }

    public final float dip2px(Context context, float dpValue) {
        Intrinsics.checkNotNullParameter(context, "context");
        return dpValue * context.getResources().getDisplayMetrics().density;
    }

    public final int getStatusBarHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final boolean isInFullWindowGestureMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
    }

    public final boolean hasNavigationBar(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isInFullWindowGestureMode(context)) {
            return false;
        }
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            Object objInvoke2 = objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]));
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke2).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean isInMultiWindowMode(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("isInMultiWindowMode", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(activity, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke).booleanValue();
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
    public static final void setNavigationTrans(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (!INSTANCE.isInFullWindowGestureMode(activity)) {
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

    public final int getRealHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public final int getRealWidth(Context context) {
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

    public final void setDialogFullScreen(Dialog dialog, int addBottom) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        if (isAboveAndroidR()) {
            return;
        }
        try {
            Window window = dialog.getWindow();
            Intrinsics.checkNotNull(window);
            window.addFlags(Integer.MIN_VALUE);
            setDialogPaddingBottom(dialog, addBottom);
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

    public final boolean isNightMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (context.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final boolean detectIntersect(RectF a, RectF b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return a.left <= b.right && b.left <= a.right && a.top <= b.bottom && b.top <= a.bottom;
    }

    public final boolean detectIntersect(Rect a, Rect b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return a.left <= b.right && b.left <= a.right && a.top <= b.bottom && b.top <= a.bottom;
    }

    public final boolean detectIntersect(Rect a, RectF b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return ((float) a.left) <= b.right && b.left <= ((float) a.right) && ((float) a.top) <= b.bottom && b.top <= ((float) a.bottom);
    }

    public final float dotsDistance(float x1, float y1, float x2, float y2) {
        float f = x1 - x2;
        float f2 = y1 - y2;
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    private final boolean isAboveAndroidR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public final int getMiuixAppcompatViewId(String idName) {
        return getReflectField("miuix.appcompat.R$id", idName);
    }

    public final int getAndroidInternalViewId(String idName) {
        return getReflectField("com.android.internal.R$id", idName);
    }

    private final int getReflectField(String className, String fieldName) {
        try {
            Field field = Class.forName(className).getField(fieldName);
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final void setFolmeHoverEffect(View view) {
        Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(view, new AnimConfig[0]);
    }

    public final boolean isInNarrowScreen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int[] screenSizeDp = DisplayUtils.INSTANCE.getScreenSizeDp(context);
        return Math.min(screenSizeDp[0], screenSizeDp[1]) <= 393;
    }

    public final int getScreenStatus(Context context, int originResponsiveLayoutState) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (RomUtils.INSTANCE.isFoldDevice() && isInNarrowScreen(context) && originResponsiveLayoutState == 4103) {
            return 4097;
        }
        return originResponsiveLayoutState;
    }

    public final void checkOrientationInFoldDevice(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (RomUtils.INSTANCE.isFoldDevice()) {
            if (isInNarrowScreen(activity)) {
                activity.setRequestedOrientation(1);
            } else {
                activity.setRequestedOrientation(-1);
            }
        }
    }
}
