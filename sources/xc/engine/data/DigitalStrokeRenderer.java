package xc.engine.data;

import android.util.Log;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class DigitalStrokeRenderer {
    private static final String LOG_TAG = "DigitCurves";
    private static final int MAX_CACHE_ENTRIES = 24;
    private static final String TAG = "DigitalStrokeRenderer";
    private final MiCurveEngine mCurveEngine;
    private int mCacheEvictCount = 0;
    private final Map<String, List<DataCurve>> mCurveCache = new LinkedHashMap(24, 0.75f, true);
    private final CurveProp mDefaultCurveProp = new CurveProp(PenType.INK, -32768, 21.0f, 255);
    private float mScaleFactor = 1.0f;

    public DigitalStrokeRenderer(MiCurveEngine miCurveEngine) {
        this.mCurveEngine = miCurveEngine;
    }

    public void setScaleFactor(float f) {
        this.mScaleFactor = f;
    }

    public void setCurveProp(PenType penType, int i, float f, int i2) {
        this.mDefaultCurveProp.setPenType(penType);
        this.mDefaultCurveProp.setPenColor(i);
        this.mDefaultCurveProp.setPenSize(f);
        this.mDefaultCurveProp.setPenAlpha(i2);
    }

    private List<List<MyPair<Float, Float>>> convertKotlinPairsToJava(List<List<Pair<Float, Float>>> list) {
        ArrayList arrayList = new ArrayList();
        for (List<Pair<Float, Float>> list2 : list) {
            ArrayList arrayList2 = new ArrayList();
            for (Pair<Float, Float> pair : list2) {
                arrayList2.add(new MyPair(pair.getFirst(), pair.getSecond()));
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    public List<DataCurve> createDigitCurves(String str, float f, float f2, float f3, float f4, float f5, CurveProp curveProp) {
        List<DataCurve> list;
        DigitalStrokeRenderer digitalStrokeRenderer = this;
        if (str == null || str.isEmpty()) {
            return new ArrayList();
        }
        String strBuildCacheKey = buildCacheKey(str, f, f2, f3, f4, f5, curveProp);
        synchronized (digitalStrokeRenderer.mCurveCache) {
            list = digitalStrokeRenderer.mCurveCache.get(strBuildCacheKey);
        }
        if (list != null) {
            Log.d(LOG_TAG, "cache HIT text='" + str + "' w=" + f3 + " h=" + f4 + " size=" + list.size() + " cacheSize=" + digitalStrokeRenderer.mCurveCache.size());
            return list;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        float f6 = f3 / (length + ((length - 1) * f5));
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            float f7 = f + (i * ((f6 * f5) + f6));
            List<List<Pair<Float, Float>>> strokesForChar = HandwrittenDigitPaths.INSTANCE.getStrokesForChar(cCharAt);
            if (strokesForChar.isEmpty()) {
                Log.w(TAG, "No stroke data found for character: " + cCharAt);
            } else {
                int i2 = 0;
                for (List<List<MyPair<Float, Float>>> listConvertKotlinPairsToJava = digitalStrokeRenderer.convertKotlinPairsToJava(strokesForChar); i2 < listConvertKotlinPairsToJava.size(); listConvertKotlinPairsToJava = listConvertKotlinPairsToJava) {
                    List<MyPair<Float, Float>> list2 = listConvertKotlinPairsToJava.get(i2);
                    if (!list2.isEmpty()) {
                        Long lValueOf = Long.valueOf(System.currentTimeMillis());
                        List<MyPair<Float, Float>> listSmoothInterpolateStroke = digitalStrokeRenderer.smoothInterpolateStroke(list2);
                        Log.d(TAG, "Stroke interpolation time: " + (Long.valueOf(System.currentTimeMillis()).longValue() - lValueOf.longValue()) + " ms");
                        digitalStrokeRenderer = this;
                        f7 = f7;
                        arrayList.add(digitalStrokeRenderer.createDataCurveForOpenGL(listSmoothInterpolateStroke, f7, f2, f6, f4, curveProp));
                    }
                    i2++;
                }
            }
        }
        digitalStrokeRenderer.putCacheWithEvict(strBuildCacheKey, arrayList);
        Log.d(LOG_TAG, "cache MISS text='" + str + "' w=" + f3 + " h=" + f4 + " curves=" + arrayList.size() + " buildMs=" + (System.currentTimeMillis() - jCurrentTimeMillis) + " cacheSize=" + digitalStrokeRenderer.mCurveCache.size() + " evicted=" + digitalStrokeRenderer.mCacheEvictCount);
        return arrayList;
    }

    private List<MyPair<Float, Float>> interpolateStroke(List<MyPair<Float, Float>> list) {
        if (list.size() < 2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.add(list.get(0));
        while (true) {
            if (i < list.size() - 1) {
                MyPair<Float, Float> myPair = list.get(i);
                i++;
                MyPair<Float, Float> myPair2 = list.get(i);
                float fFloatValue = myPair2.first.floatValue() - myPair.first.floatValue();
                float fFloatValue2 = myPair2.second.floatValue() - myPair.second.floatValue();
                int iMin = Math.min(Math.max(1, (int) (((float) Math.sqrt((fFloatValue * fFloatValue) + (fFloatValue2 * fFloatValue2))) * 50.0f)), 10);
                for (int i2 = 1; i2 <= iMin; i2++) {
                    float f = i2 / (iMin + 1);
                    arrayList.add(new MyPair(Float.valueOf(myPair.first.floatValue() + (fFloatValue * f)), Float.valueOf(myPair.second.floatValue() + (f * fFloatValue2))));
                }
                arrayList.add(myPair2);
            } else {
                Log.d(TAG, "Stroke interpolation: " + list.size() + " -> " + arrayList.size() + " points");
                return arrayList;
            }
        }
    }

    public DataCurve createDataCurveForOpenGL(List<MyPair<Float, Float>> list, float f, float f2, float f3, float f4, CurveProp curveProp) {
        DataCurve dataCurve = new DataCurve(new CurveProp(curveProp));
        dataCurve.clearPointsData();
        dataCurve.setDrawRatio(this.mCurveEngine.getDrawRatio());
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = 0;
        while (i < list.size()) {
            MyPair<Float, Float> myPair = list.get(i);
            float fFloatValue = (f + (myPair.first.floatValue() * f3)) * this.mScaleFactor;
            float fFloatValue2 = (f2 + (myPair.second.floatValue() * f4)) * this.mScaleFactor;
            float fCalculatePressureForPoint = calculatePressureForPoint(i, list.size());
            int i2 = i;
            long j = jCurrentTimeMillis + ((long) (i2 * 5));
            dataCurve.insertPoint(dataCurve.mSavePoints, fFloatValue, fFloatValue2, fCalculatePressureForPoint, 0.0f, 0.0f, 0.0f, j, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mOriginPoints, fFloatValue, fFloatValue2, fCalculatePressureForPoint, 0.0f, 0.0f, 0.0f, j, (short) 1, 0.0f);
            i = i2 + 1;
        }
        dataCurve.calculateBoundingRect();
        return dataCurve;
    }

    private String buildCacheKey(String str, float f, float f2, float f3, float f4, float f5, CurveProp curveProp) {
        MiCurveEngine miCurveEngine = this.mCurveEngine;
        return str + "|" + f + "|" + f2 + "|" + f3 + "|" + f4 + "|" + f5 + "|sf=" + this.mScaleFactor + "|dr=" + (miCurveEngine != null ? miCurveEngine.getDrawRatio() : 1.0f) + "|p=" + (curveProp != null ? curveProp.hashCode() : 0);
    }

    private void putCacheWithEvict(String str, List<DataCurve> list) {
        synchronized (this.mCurveCache) {
            this.mCurveCache.put(str, list);
            while (this.mCurveCache.size() > 24) {
                String next = this.mCurveCache.keySet().iterator().next();
                this.mCurveCache.remove(next);
                this.mCacheEvictCount++;
                Log.d(LOG_TAG, "cache EVICT eldestKey=" + next + " cacheSize=" + this.mCurveCache.size() + " evicted=" + this.mCacheEvictCount);
            }
        }
    }

    private float calculatePressureForPoint(int i, int i2) {
        float fSin;
        float f = 0.6f;
        if (i2 <= 1) {
            return 0.6f;
        }
        float f2 = i / (i2 - 1);
        if (f2 < 0.1f) {
            f = 0.3f;
            fSin = (f2 / 0.1f) * 0.3f;
        } else {
            if (f2 > 0.9f) {
                return 0.6f - (((f2 - 0.9f) / 0.1f) * 0.4f);
            }
            fSin = ((float) Math.sin(((double) f2) * 3.141592653589793d * 4.0d)) * 0.1f;
        }
        return fSin + f;
    }

    private List<MyPair<Float, Float>> smoothInterpolateStroke(List<MyPair<Float, Float>> list) {
        MyPair<Float, Float> myPair;
        if (list.size() < 3) {
            return interpolateStroke(list);
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.add(list.get(0));
        int i2 = 0;
        while (i2 < list.size() - 1) {
            MyPair<Float, Float> myPair2 = i2 == 0 ? list.get(i) : list.get(i2 - 1);
            MyPair<Float, Float> myPair3 = list.get(i2);
            int i3 = i2 + 1;
            MyPair<Float, Float> myPair4 = list.get(i3);
            int i4 = i2 + 2;
            if (i4 >= list.size()) {
                myPair = list.get(list.size() - 1);
            } else {
                myPair = list.get(i4);
            }
            float fFloatValue = myPair4.first.floatValue() - myPair3.first.floatValue();
            float fFloatValue2 = myPair4.second.floatValue() - myPair3.second.floatValue();
            int iMax = Math.max(20, Math.min(50, (int) (((float) Math.sqrt((fFloatValue * fFloatValue) + (fFloatValue2 * fFloatValue2))) * 110.0f)));
            for (int i5 = 1; i5 <= iMax; i5++) {
                float f = i5 / (iMax + 1);
                float f2 = f * f;
                float f3 = f2 * f;
                float f4 = f * 0.5f;
                float f5 = (((-0.5f) * f3) + f2) - f4;
                float f6 = ((1.5f * f3) - (2.5f * f2)) + 1.0f;
                float f7 = ((-1.5f) * f3) + (2.0f * f2) + f4;
                float f8 = (f3 * 0.5f) - (f2 * 0.5f);
                arrayList.add(new MyPair(Float.valueOf(Math.max(0.0f, Math.min(1.0f, (myPair2.first.floatValue() * f5) + (myPair3.first.floatValue() * f6) + (myPair4.first.floatValue() * f7) + (myPair.first.floatValue() * f8)))), Float.valueOf(Math.max(0.0f, Math.min(1.0f, (f5 * myPair2.second.floatValue()) + (f6 * myPair3.second.floatValue()) + (f7 * myPair4.second.floatValue()) + (f8 * myPair.second.floatValue()))))));
            }
            if (i2 < list.size() - 1) {
                arrayList.add(myPair4);
            }
            i2 = i3;
            i = 0;
        }
        return arrayList;
    }

    public static class MyPair<F, S> {
        public final F first;
        public final S second;

        public MyPair(F f, S s) {
            this.first = f;
            this.second = s;
        }
    }
}
