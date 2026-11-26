package com.miui.handwriting.common.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.HandWritingViewApplication;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DisplaySizeUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013J,\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u0019\u001a\u00020\fJ.\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u0019\u001a\u00020\fH\u0007J,\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u0019\u001a\u00020\fJ\u000e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u0003J.\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u00162\u0006\u0010\u0002\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u000e\u0010\u0010\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/miui/handwriting/common/tools/DisplaySizeUtil;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "bigWidth", "", "getBigWidth", "()F", "normalWidth", "bigSize", "", "normalSize", "partitySize", "getPartitySize", "partionSelectSize", "calculateDiagonal", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "scaleToFixedDiagonalBig", "Lkotlin/Pair;", "originalWidth", "originalHeight", "fixedDiagonal", "scaleToFixedDiagonalPartionSelect", "scaleToFixedDiagonalNormal", "getScreenWidth", "refreshImageSizeByScreen", "Landroid/app/Activity;", "partionPair", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DisplaySizeUtil {
    private final double bigSize;
    private final float bigWidth;
    private final double normalSize;
    private final float normalWidth;
    private final double partionSelectSize;
    private final float partitySize;

    public final Pair<Integer, Integer> scaleToFixedDiagonalPartionSelect(int i, int i2) {
        return scaleToFixedDiagonalPartionSelect$default(this, i, i2, 0.0d, 4, null);
    }

    public DisplaySizeUtil(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        float fDpToPx = DisplaySizeUtilKt.dpToPx(context, 400.0f);
        this.bigWidth = fDpToPx;
        float fDpToPx2 = DisplaySizeUtilKt.dpToPx(context, 240.0f);
        this.normalWidth = fDpToPx2;
        this.bigSize = Math.sqrt((fDpToPx * fDpToPx) + (fDpToPx * fDpToPx));
        this.normalSize = Math.sqrt((fDpToPx2 * fDpToPx2) + (fDpToPx2 * fDpToPx2));
        float fDpToPx3 = DisplaySizeUtilKt.dpToPx(context, 440.0f);
        this.partitySize = fDpToPx3;
        this.partionSelectSize = (float) Math.sqrt((fDpToPx3 * fDpToPx3) + (fDpToPx3 * fDpToPx3));
    }

    public final float getBigWidth() {
        return this.bigWidth;
    }

    public final float getPartitySize() {
        return this.partitySize;
    }

    public final double calculateDiagonal(int width, int height) {
        return Math.sqrt((width * width) + (height * height));
    }

    public static /* synthetic */ Pair scaleToFixedDiagonalBig$default(DisplaySizeUtil displaySizeUtil, int i, int i2, double d, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            d = displaySizeUtil.bigSize;
        }
        return displaySizeUtil.scaleToFixedDiagonalBig(i, i2, d);
    }

    public final Pair<Integer, Integer> scaleToFixedDiagonalBig(int originalWidth, int originalHeight, double fixedDiagonal) {
        double dCalculateDiagonal = fixedDiagonal / calculateDiagonal(originalWidth, originalHeight);
        return new Pair<>(Integer.valueOf((int) (((double) originalWidth) * dCalculateDiagonal)), Integer.valueOf((int) (((double) originalHeight) * dCalculateDiagonal)));
    }

    public static /* synthetic */ Pair scaleToFixedDiagonalPartionSelect$default(DisplaySizeUtil displaySizeUtil, int i, int i2, double d, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            d = displaySizeUtil.partionSelectSize;
        }
        return displaySizeUtil.scaleToFixedDiagonalPartionSelect(i, i2, d);
    }

    public final Pair<Integer, Integer> scaleToFixedDiagonalPartionSelect(int originalWidth, int originalHeight, double fixedDiagonal) {
        float f;
        double dCalculateDiagonal = calculateDiagonal(originalWidth, originalHeight);
        if (DeviceUtils.isO88() && com.miui.libhandwritingpage.utils.UIUtils.getRealScreenOrientation(HandWritingViewApplication.INSTANCE.getApplication()) == 2) {
            Log.e("DisplaySizeUtil", "is small pad and is landscape");
            f = 0.8f;
        } else {
            f = 1.0f;
        }
        double d = (fixedDiagonal / dCalculateDiagonal) * ((double) f);
        return new Pair<>(Integer.valueOf((int) (((double) originalWidth) * d)), Integer.valueOf((int) (((double) originalHeight) * d)));
    }

    public static /* synthetic */ Pair scaleToFixedDiagonalNormal$default(DisplaySizeUtil displaySizeUtil, int i, int i2, double d, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            d = displaySizeUtil.normalSize;
        }
        return displaySizeUtil.scaleToFixedDiagonalNormal(i, i2, d);
    }

    public final Pair<Integer, Integer> scaleToFixedDiagonalNormal(int originalWidth, int originalHeight, double fixedDiagonal) {
        double dCalculateDiagonal = fixedDiagonal / calculateDiagonal(originalWidth, originalHeight);
        return new Pair<>(Integer.valueOf((int) (((double) originalWidth) * dCalculateDiagonal)), Integer.valueOf((int) (((double) originalHeight) * dCalculateDiagonal)));
    }

    public final int getScreenWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public final Pair<Integer, Integer> refreshImageSizeByScreen(Activity context, Pair<Integer, Integer> partionPair) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(partionPair, "partionPair");
        if (!com.miui.libhandwritingpage.utils.UIUtils.isInMultiWindowMode(context)) {
            return partionPair;
        }
        Activity activity = context;
        int windowWidth = com.miui.libhandwritingpage.utils.UIUtils.getWindowWidth(activity) - com.miui.libhandwritingpage.utils.UIUtils.dip2px(activity, 25.0f);
        if (partionPair.getFirst().intValue() <= windowWidth) {
            return partionPair;
        }
        return new Pair<>(Integer.valueOf(windowWidth), Integer.valueOf((int) (partionPair.getSecond().floatValue() * (windowWidth / partionPair.getFirst().floatValue()))));
    }
}
