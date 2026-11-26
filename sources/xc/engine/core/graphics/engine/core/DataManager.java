package xc.engine.core.graphics.engine.core;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: DataManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lxc/engine/core/graphics/engine/core/DataManager;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "infiniteBoundary", "Landroid/graphics/RectF;", "calculateInfiniteBoundary", "bUnionScreen", "", "updateInfiniteBoundary", "", "areaWS", "bApplyPenSizeCompensation", "getElementsCount", "", "getInfiniteBoundary", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DataManager extends BaseEngineStruct {
    public static final float PEN_SIZE_COMPENSATION = 100.0f;
    private final RectF infiniteBoundary;
    private static final String TAG = "DataManager";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataManager(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.infiniteBoundary = new RectF();
    }

    public static /* synthetic */ RectF calculateInfiniteBoundary$default(DataManager dataManager, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return dataManager.calculateInfiniteBoundary(z);
    }

    public final RectF calculateInfiniteBoundary(boolean bUnionScreen) {
        EngineBindContext engineBindContext;
        List<SpannedData> allDataList;
        RectF rectF = new RectF();
        if (!getCurveEngine().isInfinite()) {
            return rectF;
        }
        List<DataCurve> mDataCurves = getCurveEngine().getPenEngine().mDataAccess.mDataSet.mDataCurves;
        Intrinsics.checkNotNullExpressionValue(mDataCurves, "mDataCurves");
        boolean z = false;
        for (DataCurve dataCurve : mDataCurves) {
            if (dataCurve.getDataState() != 3) {
                rectF.union(dataCurve.mRect);
                z = true;
            }
        }
        if (z) {
            float saveRatio = getCurveEngine().getSaveRatio() * (-100.0f);
            rectF.inset(saveRatio, saveRatio);
        }
        SpannedLayoutModel spannedLayoutModel = getCurveEngine().getSpannedLayoutModel();
        if (spannedLayoutModel != null && (engineBindContext = spannedLayoutModel.getEngineBindContext()) != null && (allDataList = engineBindContext.getAllDataList()) != null) {
            for (SpannedData spannedData : allDataList) {
                if (spannedData.mSpannedStatus == 0 || spannedData.mSpannedStatus == 1) {
                    rectF.union(spannedData.getRotatedRectF());
                }
            }
        }
        rectF.set(getCurveEngine().getRectWSFromSaveRect(rectF));
        if (bUnionScreen) {
            rectF.union(getCurveEngine().getScreenAreaSS());
        }
        this.infiniteBoundary.set(rectF);
        return rectF;
    }

    public static /* synthetic */ void updateInfiniteBoundary$default(DataManager dataManager, RectF rectF, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        dataManager.updateInfiniteBoundary(rectF, z);
    }

    public final void updateInfiniteBoundary(RectF areaWS, boolean bApplyPenSizeCompensation) {
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        if (getCurveEngine().isInfinite()) {
            if (getElementsCount() < 64) {
                calculateInfiniteBoundary$default(this, false, 1, null);
                return;
            }
            RectF rectF = new RectF(areaWS);
            if (bApplyPenSizeCompensation) {
                rectF.inset(-100.0f, -100.0f);
            }
            this.infiniteBoundary.union(rectF);
        }
    }

    private final int getElementsCount() {
        EngineBindContext engineBindContext;
        int size = getCurveEngine().getPenEngine().mDataAccess.mDataSet.mDataCurves.size();
        SpannedLayoutModel spannedLayoutModel = getCurveEngine().getSpannedLayoutModel();
        if (((spannedLayoutModel == null || (engineBindContext = spannedLayoutModel.getEngineBindContext()) == null) ? null : engineBindContext.getAllDataList()) == null) {
            return size;
        }
        SpannedLayoutModel spannedLayoutModel2 = getCurveEngine().getSpannedLayoutModel();
        Intrinsics.checkNotNull(spannedLayoutModel2);
        return size + spannedLayoutModel2.getEngineBindContext().getAllDataList().size();
    }

    public final RectF getInfiniteBoundary() {
        return new RectF(this.infiniteBoundary);
    }
}
