package xc.engine.core.graphics.engine;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.util.a;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.systems.BgSystem;
import xc.engine.data.pagedata.DataCurve;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: MiCurveEngineCore.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010N\u001a\u00020OH\u0016R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\nR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001e\"\u0004\b#\u0010 R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010-\"\u0004\b2\u0010/R\u001a\u00103\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010-\"\u0004\b5\u0010/R\u001a\u00106\u001a\u000207X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001a\u0010<\u001a\u00020=X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001c\u0010B\u001a\u0004\u0018\u00010CX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010H\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010\u0018\"\u0004\bJ\u0010\u001aR\u001c\u0010K\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010'\"\u0004\bM\u0010)¨\u0006P"}, d2 = {"Lxc/engine/core/graphics/engine/RenderData;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "curves", "", "Lxc/engine/data/pagedata/DataCurve;", "getCurves", "()Ljava/util/List;", "setCurves", "(Ljava/util/List;)V", "spans", "Lxc/richtext/data/SpannedData;", "getSpans", "setSpans", "outBitmapSize", "Landroid/graphics/Point;", "getOutBitmapSize", "()Landroid/graphics/Point;", "setOutBitmapSize", "(Landroid/graphics/Point;)V", a.e, "Landroid/graphics/RectF;", "getArea", "()Landroid/graphics/RectF;", "setArea", "(Landroid/graphics/RectF;)V", "bUseImageAsBG", "", "getBUseImageAsBG", "()Z", "setBUseImageAsBG", "(Z)V", "bPdfMode", "getBPdfMode", "setBPdfMode", "bgBitmap", "Landroid/graphics/Bitmap;", "getBgBitmap", "()Landroid/graphics/Bitmap;", "setBgBitmap", "(Landroid/graphics/Bitmap;)V", "pageSize", "Landroid/graphics/PointF;", "getPageSize", "()Landroid/graphics/PointF;", "setPageSize", "(Landroid/graphics/PointF;)V", "pageMaxSize", "getPageMaxSize", "setPageMaxSize", "pageOffset", "getPageOffset", "setPageOffset", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "getBgColor", "()Lxc/engine/core/graphics/engine/core/Color;", "setBgColor", "(Lxc/engine/core/graphics/engine/core/Color;)V", "bgStyle", "Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "getBgStyle", "()Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "setBgStyle", "(Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;)V", "highlightPath", "Landroid/graphics/Path;", "getHighlightPath", "()Landroid/graphics/Path;", "setHighlightPath", "(Landroid/graphics/Path;)V", "highlightArea", "getHighlightArea", "setHighlightArea", "outBitmap", "getOutBitmap", "setOutBitmap", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RenderData {
    private boolean bPdfMode;
    private boolean bUseImageAsBG;
    private Bitmap bgBitmap;
    private List<? extends DataCurve> curves;
    private Path highlightPath;
    private Bitmap outBitmap;
    private List<? extends SpannedData> spans;
    private Point outBitmapSize = new Point();
    private RectF area = new RectF();
    private PointF pageSize = new PointF();
    private PointF pageMaxSize = new PointF();
    private PointF pageOffset = new PointF();
    private Color bgColor = new Color();
    private BgSystem.EBgStyle bgStyle = BgSystem.EBgStyle.None;
    private RectF highlightArea = new RectF();

    public final List<DataCurve> getCurves() {
        return this.curves;
    }

    public final void setCurves(List<? extends DataCurve> list) {
        this.curves = list;
    }

    public final List<SpannedData> getSpans() {
        return this.spans;
    }

    public final void setSpans(List<? extends SpannedData> list) {
        this.spans = list;
    }

    public final Point getOutBitmapSize() {
        return this.outBitmapSize;
    }

    public final void setOutBitmapSize(Point point) {
        Intrinsics.checkNotNullParameter(point, "<set-?>");
        this.outBitmapSize = point;
    }

    public final RectF getArea() {
        return this.area;
    }

    public final void setArea(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        this.area = rectF;
    }

    public final boolean getBUseImageAsBG() {
        return this.bUseImageAsBG;
    }

    public final void setBUseImageAsBG(boolean z) {
        this.bUseImageAsBG = z;
    }

    public final boolean getBPdfMode() {
        return this.bPdfMode;
    }

    public final void setBPdfMode(boolean z) {
        this.bPdfMode = z;
    }

    public final Bitmap getBgBitmap() {
        return this.bgBitmap;
    }

    public final void setBgBitmap(Bitmap bitmap) {
        this.bgBitmap = bitmap;
    }

    public final PointF getPageSize() {
        return this.pageSize;
    }

    public final void setPageSize(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.pageSize = pointF;
    }

    public final PointF getPageMaxSize() {
        return this.pageMaxSize;
    }

    public final void setPageMaxSize(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.pageMaxSize = pointF;
    }

    public final PointF getPageOffset() {
        return this.pageOffset;
    }

    public final void setPageOffset(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.pageOffset = pointF;
    }

    public final Color getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(Color color) {
        Intrinsics.checkNotNullParameter(color, "<set-?>");
        this.bgColor = color;
    }

    public final BgSystem.EBgStyle getBgStyle() {
        return this.bgStyle;
    }

    public final void setBgStyle(BgSystem.EBgStyle eBgStyle) {
        Intrinsics.checkNotNullParameter(eBgStyle, "<set-?>");
        this.bgStyle = eBgStyle;
    }

    public final Path getHighlightPath() {
        return this.highlightPath;
    }

    public final void setHighlightPath(Path path) {
        this.highlightPath = path;
    }

    public final RectF getHighlightArea() {
        return this.highlightArea;
    }

    public final void setHighlightArea(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        this.highlightArea = rectF;
    }

    public final Bitmap getOutBitmap() {
        return this.outBitmap;
    }

    public final void setOutBitmap(Bitmap bitmap) {
        this.outBitmap = bitmap;
    }

    public String toString() {
        List<? extends DataCurve> list = this.curves;
        Integer numValueOf = list != null ? Integer.valueOf(list.size()) : null;
        List<? extends SpannedData> list2 = this.spans;
        Integer numValueOf2 = list2 != null ? Integer.valueOf(list2.size()) : null;
        int i = this.outBitmapSize.x;
        int i2 = this.outBitmapSize.y;
        RectF rectF = this.area;
        float fWidth = rectF.width();
        float fHeight = this.area.height();
        Color color = this.bgColor;
        BgSystem.EBgStyle eBgStyle = this.bgStyle;
        boolean z = this.bUseImageAsBG;
        Bitmap bitmap = this.bgBitmap;
        Integer numValueOf3 = bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null;
        Bitmap bitmap2 = this.bgBitmap;
        return "[RenderData] curves:" + numValueOf + ", spans: " + numValueOf2 + ", \n outBitmapSize: " + i + " x " + i2 + ", area: " + rectF + StringUtils.SPACE + fWidth + " x " + fHeight + ", \n bgColor: " + color + ", bgStyle: " + eBgStyle + ", hasBgImage: " + z + ", bgBitmap: " + bitmap + StringUtils.SPACE + numValueOf3 + " x " + (bitmap2 != null ? Integer.valueOf(bitmap2.getHeight()) : null) + " \n bPdfMode: " + this.bPdfMode + ", highlightPath: " + this.highlightPath + ", pageSize: " + this.pageSize + ", pageMaxSize: " + this.pageMaxSize + ", pageOffset: " + this.pageOffset;
    }
}
