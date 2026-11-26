package xc.engine.operate.edit.select.selectInfo;

import android.graphics.PointF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: MultiSelectInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0006\u0010\u0014\u001a\u00020\fJ\u0016\u0010\u0015\u001a\u00020\u00122\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003R\"\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u0006R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"Lxc/engine/operate/edit/select/selectInfo/MultiSelectInfo;", "Lxc/engine/operate/edit/select/selectInfo/AbsSelectInfo;", "dataList", "", "Lxc/engine/data/pagedata/DataCurve;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;)V", "mDataList", "getMDataList", "()Ljava/util/List;", "setMDataList", "mOffsetPointF", "Landroid/graphics/PointF;", "getMOffsetPointF", "()Landroid/graphics/PointF;", "setMOffsetPointF", "(Landroid/graphics/PointF;)V", "applyOffsetToPoint", "", "reset", "getOffsetPointF", "setDataList", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MultiSelectInfo extends AbsSelectInfo {
    private List<? extends DataCurve> mDataList;
    private PointF mOffsetPointF;

    public MultiSelectInfo(List<? extends DataCurve> dataList) {
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        this.mOffsetPointF = new PointF();
        this.mDataList = dataList;
    }

    public final List<DataCurve> getMDataList() {
        return this.mDataList;
    }

    public final void setMDataList(List<? extends DataCurve> list) {
        this.mDataList = list;
    }

    public final PointF getMOffsetPointF() {
        return this.mOffsetPointF;
    }

    public final void setMOffsetPointF(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.mOffsetPointF = pointF;
    }

    @Override // xc.engine.operate.edit.select.selectInfo.AbsSelectInfo
    public void applyOffsetToPoint() {
        this.mOffsetPointF.x += getMOffsetX();
        this.mOffsetPointF.y += getMOffsetY();
        setMOffsetX(0.0f);
        setMOffsetY(0.0f);
    }

    @Override // xc.engine.operate.edit.select.selectInfo.AbsSelectInfo
    public void reset() {
        this.mOffsetPointF.set(0.0f, 0.0f);
        setMOffsetX(0.0f);
        setMOffsetY(0.0f);
        setMScale(1.0f);
        setMDegrees(0.0f);
    }

    public final PointF getOffsetPointF() {
        return this.mOffsetPointF;
    }

    public final void setDataList(List<? extends DataCurve> dataList) {
        this.mDataList = dataList;
    }
}
