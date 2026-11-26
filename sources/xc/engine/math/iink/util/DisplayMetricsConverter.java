package xc.engine.math.iink.util;

import android.util.DisplayMetrics;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DisplayMetricsConverter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u000e\u0010\f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lxc/engine/math/iink/util/DisplayMetricsConverter;", "", "displayMetrics", "Landroid/util/DisplayMetrics;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/util/DisplayMetrics;)V", "x_mm2px", "", "mm", "y_mm2px", "x_px2mm", "px", "y_px2mm", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DisplayMetricsConverter {
    private static final float INCH_TO_MM = 25.4f;
    private final DisplayMetrics displayMetrics;

    public DisplayMetricsConverter(DisplayMetrics displayMetrics) {
        Intrinsics.checkNotNullParameter(displayMetrics, "displayMetrics");
        this.displayMetrics = displayMetrics;
    }

    public final float x_mm2px(float mm) {
        return (mm / INCH_TO_MM) * this.displayMetrics.xdpi;
    }

    public final float y_mm2px(float mm) {
        return (mm / INCH_TO_MM) * this.displayMetrics.ydpi;
    }

    public final float x_px2mm(float px) {
        return (px / this.displayMetrics.xdpi) * INCH_TO_MM;
    }

    public final float y_px2mm(float px) {
        return (px / this.displayMetrics.ydpi) * INCH_TO_MM;
    }
}
