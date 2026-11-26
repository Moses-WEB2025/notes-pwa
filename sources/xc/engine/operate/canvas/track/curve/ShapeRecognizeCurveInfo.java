package xc.engine.operate.canvas.track.curve;

import android.graphics.Path;
import java.util.ArrayList;
import java.util.List;
import xc.engine.core.graphics.curve.info.CurveInfo;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ShapeRecognizeCurveInfo extends CurveInfo {
    private static final float PRESSURE_MAX = 1.0f;
    private static final float PRESSURE_MIN = 0.079832f;
    private static final List<Short> sAcuteAngleCurveType;
    public final DataCurve mOriginDataCurve;
    public DataCurve mRecognizedDataCurve;

    static {
        ArrayList arrayList = new ArrayList();
        sAcuteAngleCurveType = arrayList;
        arrayList.add((short) 6);
        arrayList.add((short) 22);
        arrayList.add((short) 15);
        arrayList.add((short) 16);
        arrayList.add((short) 17);
        arrayList.add((short) 18);
        arrayList.add((short) 12);
        arrayList.add((short) 13);
        arrayList.add((short) 19);
        arrayList.add((short) 20);
        arrayList.add((short) 21);
        arrayList.add((short) 14);
        arrayList.add((short) 11);
    }

    public ShapeRecognizeCurveInfo(DataCurve dataCurve, boolean z) {
        enableScale(z);
        this.mOriginDataCurve = dataCurve;
        this.mRecognizedDataCurve = null;
    }

    private float calculatePressure() {
        DataCurve dataCurve = this.mOriginDataCurve;
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        int size = curvePointList.size();
        float pressure = 0.0f;
        for (int i = 0; i < size; i++) {
            pressure += curvePointList.get(i).getPressure();
        }
        float f = pressure / size;
        if (Float.isNaN(f) || f < PRESSURE_MIN) {
            return PRESSURE_MIN;
        }
        if (Float.isInfinite(f)) {
            return 1.0f;
        }
        return f;
    }

    private float calculateTilt() {
        DataCurve dataCurve = this.mOriginDataCurve;
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        int size = curvePointList.size();
        float tilt = 0.0f;
        for (int i = 0; i < size; i++) {
            tilt += curvePointList.get(i).getTilt();
        }
        return tilt / size;
    }

    private float calculateOrientation() {
        DataCurve dataCurve = this.mOriginDataCurve;
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        int size = curvePointList.size();
        float orientation = 0.0f;
        for (int i = 0; i < size; i++) {
            orientation += curvePointList.get(i).getOrientation();
        }
        float f = orientation / size;
        return (Float.isNaN(f) || Float.isInfinite(f)) ? (float) Math.toRadians(45.0d) : f;
    }

    public void generateCurvePoints(Path path) {
        DataCurve dataCurve = this.mRecognizedDataCurve;
        if (dataCurve == null) {
            return;
        }
        if (sAcuteAngleCurveType.contains(Short.valueOf(dataCurve.mCurveType))) {
            PenEngineUtils.calculatePathSavePoints(path, this.mOriginDataCurve.mDrawRatio, this.mRecognizedDataCurve, true, 0L, calculateOrientation(), calculatePressure(), calculateTilt(), 0.4f, 0.0f);
        } else {
            PenEngineUtils.calculatePathSavePoints(path, this.mOriginDataCurve.mDrawRatio, this.mRecognizedDataCurve, false, 0L, calculateOrientation(), calculatePressure(), calculateTilt(), 0.4f, 0.0f);
        }
    }

    public final void ReleaseRecognizedDataCurve() {
        DataCurve dataCurve = this.mRecognizedDataCurve;
        if (dataCurve != null) {
            dataCurve.releasePointList();
        }
    }
}
