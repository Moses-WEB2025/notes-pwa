package xc.engine.operate.edit.select.selectInfo;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: compiled from: ShapeSelectInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\u0003J\u0006\u0010\u0011\u001a\u00020\u0007J\"\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u0010\u0010\u0015\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u00010\u0016J\b\u0010\u0018\u001a\u00020\u0013H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0005¨\u0006\u0019"}, d2 = {"Lxc/engine/operate/edit/select/selectInfo/ShapeSelectInfo;", "Lxc/engine/operate/edit/select/selectInfo/AbsSelectInfo;", "shapeData", "Lxc/engine/data/pagedata/DataShape;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/data/pagedata/DataShape;)V", "mShapeSaveRectF", "Landroid/graphics/RectF;", "getMShapeSaveRectF", "()Landroid/graphics/RectF;", "setMShapeSaveRectF", "(Landroid/graphics/RectF;)V", "mShapeData", "getMShapeData", "()Lxc/engine/data/pagedata/DataShape;", "setMShapeData", "getShapeData", "getShapeSaveRectF", "reset", "", "rectF", "dataCurveList", "", "Lxc/engine/data/pagedata/DataCurve;", "applyOffsetToPoint", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShapeSelectInfo extends AbsSelectInfo {
    private DataShape mShapeData;
    private RectF mShapeSaveRectF;

    @Override // xc.engine.operate.edit.select.selectInfo.AbsSelectInfo
    public void applyOffsetToPoint() {
    }

    public ShapeSelectInfo(DataShape shapeData) {
        Intrinsics.checkNotNullParameter(shapeData, "shapeData");
        this.mShapeData = shapeData;
        RectF saveRect = shapeData.getSaveRect();
        Intrinsics.checkNotNullExpressionValue(saveRect, "getSaveRect(...)");
        this.mShapeSaveRectF = saveRect;
        shapeData.translateRectByPenPoint(saveRect, false);
    }

    public final RectF getMShapeSaveRectF() {
        return this.mShapeSaveRectF;
    }

    public final void setMShapeSaveRectF(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        this.mShapeSaveRectF = rectF;
    }

    public final DataShape getMShapeData() {
        return this.mShapeData;
    }

    public final void setMShapeData(DataShape dataShape) {
        Intrinsics.checkNotNullParameter(dataShape, "<set-?>");
        this.mShapeData = dataShape;
    }

    public final DataShape getShapeData() {
        return this.mShapeData;
    }

    public final RectF getShapeSaveRectF() {
        return this.mShapeSaveRectF;
    }

    public final void reset(RectF rectF, List<? extends DataCurve> dataCurveList) {
        setMOffsetX(0.0f);
        setMOffsetY(0.0f);
        setMScale(1.0f);
        setMDegrees(0.0f);
        setMCenterX(0.0f);
        setMCenterY(0.0f);
    }

    @Override // xc.engine.operate.edit.select.selectInfo.AbsSelectInfo
    public void reset() {
        setMOffsetX(0.0f);
        setMOffsetY(0.0f);
        setMScale(1.0f);
        setMDegrees(0.0f);
        setMCenterX(0.0f);
        setMCenterY(0.0f);
        RectF saveRect = this.mShapeData.getSaveRect();
        Intrinsics.checkNotNullExpressionValue(saveRect, "getSaveRect(...)");
        this.mShapeSaveRectF = saveRect;
        this.mShapeData.translateRectByPenPoint(saveRect, false);
    }
}
