package xc.engine.data.pagedata;

import android.graphics.PointF;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import xc.engine.data.DataAccessImpl;

/* JADX INFO: loaded from: classes6.dex */
public class DataSet implements IDataSet {
    private static final String TAG = "DataSet";
    private DataAccessImpl.GetPageOffsetCallback getPageOffsetCallback;
    public long mExtraTotalPoint;
    Map<Integer, DataSetParams> parmsMap;
    public int mId = 0;
    public int mCount = 0;
    public final List<DataCurve> mDataCurves = new ArrayList(0);
    public final Map<Integer, List<BaseData>> mAllDataMap = new ConcurrentHashMap(0);
    public final List<BaseData> mAllData = new ArrayList(0);

    /* JADX INFO: Access modifiers changed from: private */
    static class DataSetParams {
        public int mDataSize;
        public long mDeleteDataSize;
        public long mDeleteTotalSavePoint;
        public long mTotalVisibleSavePoint;

        private DataSetParams() {
        }
    }

    public DataSet() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(0);
        this.parmsMap = concurrentHashMap;
        ((DataSetParams) concurrentHashMap.computeIfAbsent(0, new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$new$0((Integer) obj);
            }
        })).mTotalVisibleSavePoint = 0L;
        this.mExtraTotalPoint = 0L;
        this.parmsMap.computeIfAbsent(0, new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$new$1((Integer) obj);
            }
        }).mDeleteTotalSavePoint = 0L;
        this.parmsMap.computeIfAbsent(0, new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$new$2((Integer) obj);
            }
        }).mDataSize = 0;
        this.parmsMap.computeIfAbsent(0, new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$new$3((Integer) obj);
            }
        }).mDeleteDataSize = 0L;
    }

    static /* synthetic */ DataSetParams lambda$new$0(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$new$1(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$new$2(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$new$3(Integer num) {
        return new DataSetParams();
    }

    public void addId(int i) {
        this.mId += i;
    }

    public int getCount() {
        int i = this.mCount;
        this.mCount = i + 1;
        return i;
    }

    static /* synthetic */ DataSetParams lambda$getTotalVisibleSavePoint$4(Integer num) {
        return new DataSetParams();
    }

    @Override // xc.engine.data.pagedata.IDataSet
    public long getTotalVisibleSavePoint(int i) {
        return this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$getTotalVisibleSavePoint$4((Integer) obj);
            }
        }).mTotalVisibleSavePoint;
    }

    @Override // xc.engine.data.pagedata.IDataSet
    public void setExtraTotalPoint(long j) {
        this.mExtraTotalPoint = j;
    }

    public void insertDataCurve(DataCurve dataCurve) {
        Log.i(TAG, String.format("[insertDataCurve] page[%d] curveSize[%d] isMathResult[%s]", Integer.valueOf(dataCurve.pageIndex), Integer.valueOf(dataCurve.getDataSize()), Boolean.valueOf(dataCurve.isMathResult)));
        dataCurve.mCount = getCount();
        int iBinarySearch = Collections.binarySearch(this.mDataCurves, dataCurve, new Comparator() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda15
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DataSet.compareByTime((DataCurve) obj, (DataCurve) obj2);
            }
        });
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        } else {
            DataCurve dataCurve2 = this.mDataCurves.get(iBinarySearch);
            while (iBinarySearch < this.mDataCurves.size() && dataCurve2.size(dataCurve2.mSavePoints) != 0 && dataCurve.size(dataCurve.mSavePoints) != 0 && dataCurve2.mTime + ((long) dataCurve2.getCurvePointList(dataCurve2.mSavePoints).getTime(0)) == dataCurve.mTime + ((long) dataCurve.getCurvePointList(dataCurve.mSavePoints).getTime(0))) {
                iBinarySearch++;
                if (iBinarySearch < this.mDataCurves.size()) {
                    dataCurve2 = this.mDataCurves.get(iBinarySearch);
                }
            }
        }
        this.mDataCurves.add(iBinarySearch, dataCurve);
        int i = dataCurve.pageIndex;
        this.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$insertDataCurve$5((Integer) obj);
            }
        }).add(dataCurve);
        int i2 = iBinarySearch + 1;
        if (i2 >= this.mDataCurves.size()) {
            this.mAllData.add(dataCurve);
        } else {
            int iBinarySearch2 = Collections.binarySearch(this.mAllData, this.mDataCurves.get(i2), new Comparator() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda17
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataSet.compareByTime((BaseData) obj, (BaseData) obj2);
                }
            });
            if (iBinarySearch2 < 0) {
                iBinarySearch2 = (-iBinarySearch2) - 1;
            }
            this.mAllData.add(iBinarySearch2, dataCurve);
        }
        dataCurve.mDataState = 1;
        Log.d(TAG, "[insertDataCurve] successfully added curve mId=" + dataCurve.mId + " to page[" + i + "]");
    }

    static /* synthetic */ List lambda$insertDataCurve$5(Integer num) {
        return new ArrayList();
    }

    public void insertDataCurvePro(DataCurve dataCurve) {
        String str = TAG;
        Log.i(str, String.format("[insertDataCurvePro] page[%d] curveSize[%d] isMathResult[%s]", Integer.valueOf(dataCurve.pageIndex), Integer.valueOf(dataCurve.getDataSize()), Boolean.valueOf(dataCurve.isMathResult)));
        dataCurve.mCount = getCount();
        this.mDataCurves.add(this.mDataCurves.size(), dataCurve);
        this.mAllData.add(dataCurve);
        int i = dataCurve.pageIndex;
        this.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$insertDataCurvePro$6((Integer) obj);
            }
        }).add(dataCurve);
        dataCurve.mDataState = 1;
        Log.d(str, "[insertDataCurvePro] successfully added curve mId=" + dataCurve.mId + " to page[" + i + "]");
    }

    static /* synthetic */ List lambda$insertDataCurvePro$6(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ DataSetParams lambda$setDataAndPoint$7(Integer num) {
        return new DataSetParams();
    }

    public void setDataAndPoint(int i, int i2, int i3, int i4, int i5) {
        this.parmsMap.computeIfAbsent(Integer.valueOf(i5), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$setDataAndPoint$7((Integer) obj);
            }
        }).mTotalVisibleSavePoint = i;
        this.parmsMap.computeIfAbsent(Integer.valueOf(i5), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$setDataAndPoint$8((Integer) obj);
            }
        }).mDeleteTotalSavePoint = i2;
        this.parmsMap.computeIfAbsent(Integer.valueOf(i5), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$setDataAndPoint$9((Integer) obj);
            }
        }).mDataSize = i3;
        this.parmsMap.computeIfAbsent(Integer.valueOf(i5), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$setDataAndPoint$10((Integer) obj);
            }
        }).mDeleteDataSize = i4;
    }

    static /* synthetic */ DataSetParams lambda$setDataAndPoint$8(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$setDataAndPoint$9(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$setDataAndPoint$10(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$updateDeleteCount$11(Integer num) {
        return new DataSetParams();
    }

    public void updateDeleteCount(DataCurve dataCurve, int i) {
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateDeleteCount$11((Integer) obj);
            }
        }).mDeleteTotalSavePoint += (long) dataCurve.size(dataCurve.mSavePoints);
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateDeleteCount$12((Integer) obj);
            }
        }).mDeleteDataSize++;
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateDeleteCount$13((Integer) obj);
            }
        }).mTotalVisibleSavePoint -= (long) dataCurve.size(dataCurve.mSavePoints);
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda27
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateDeleteCount$14((Integer) obj);
            }
        }).mDataSize--;
    }

    static /* synthetic */ DataSetParams lambda$updateDeleteCount$12(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$updateDeleteCount$13(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$updateDeleteCount$14(Integer num) {
        return new DataSetParams();
    }

    public void clear() {
        for (int i = 0; i < this.mDataCurves.size(); i++) {
            DataCurve dataCurve = this.mDataCurves.get(i);
            if (dataCurve != null) {
                dataCurve.releasePointList();
            }
        }
        this.mDataCurves.clear();
        this.mAllData.clear();
        this.mAllDataMap.clear();
    }

    static /* synthetic */ DataSetParams lambda$isPointLimit$15(Integer num) {
        return new DataSetParams();
    }

    public boolean isPointLimit(int i, int i2, int i3) {
        return ((long) i) + this.parmsMap.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$isPointLimit$15((Integer) obj);
            }
        }).mTotalVisibleSavePoint <= ((long) i2);
    }

    static /* synthetic */ DataSetParams lambda$updateVisibleCount$16(Integer num) {
        return new DataSetParams();
    }

    public void updateVisibleCount(DataCurve dataCurve, boolean z, int i) {
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda28
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateVisibleCount$16((Integer) obj);
            }
        }).mTotalVisibleSavePoint += (long) dataCurve.size(dataCurve.mSavePoints);
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateVisibleCount$17((Integer) obj);
            }
        }).mDataSize++;
        if (z) {
            return;
        }
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateVisibleCount$18((Integer) obj);
            }
        }).mDeleteTotalSavePoint -= (long) dataCurve.size(dataCurve.mSavePoints);
        this.parmsMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateVisibleCount$19((Integer) obj);
            }
        }).mDeleteDataSize--;
    }

    static /* synthetic */ DataSetParams lambda$updateVisibleCount$17(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$updateVisibleCount$18(Integer num) {
        return new DataSetParams();
    }

    static /* synthetic */ DataSetParams lambda$updateVisibleCount$19(Integer num) {
        return new DataSetParams();
    }

    public boolean deletePageAndHaveShiftPage(final int i) {
        int i2 = i + 1;
        DataSetParams dataSetParams = this.parmsMap.containsKey(Integer.valueOf(i2)) ? this.parmsMap.get(Integer.valueOf(i2)) : null;
        List<BaseData> listRemove = this.mAllDataMap.remove(Integer.valueOf(i));
        if (listRemove != null && !listRemove.isEmpty()) {
            Log.i(TAG, String.format("[deletePage] remove page[%d] curve size[%d]", Integer.valueOf(i), Integer.valueOf(listRemove.size())));
            final Set set = (Set) listRemove.stream().map(new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((BaseData) obj).mId);
                }
            }).collect(Collectors.toSet());
            this.mAllData.removeIf(new Predicate() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return set.contains(Integer.valueOf(((BaseData) obj).mId));
                }
            });
            this.mDataCurves.removeIf(new Predicate() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda21
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return set.contains(Integer.valueOf(((DataCurve) obj).mId));
                }
            });
            Iterator<BaseData> it = listRemove.iterator();
            while (it.hasNext()) {
                it.next().releasePointList();
            }
        }
        this.parmsMap.remove(Integer.valueOf(i));
        if (dataSetParams != null) {
            this.parmsMap.put(Integer.valueOf(i), dataSetParams);
        }
        return this.mAllDataMap.keySet().stream().anyMatch(new Predicate() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DataSet.lambda$deletePageAndHaveShiftPage$23(i, (Integer) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$deletePageAndHaveShiftPage$23(int i, Integer num) {
        return num.intValue() > i;
    }

    public void shiftPages(final int i, float f, float f2) {
        List<Integer> list = (List) this.mAllDataMap.keySet().stream().filter(new Predicate() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DataSet.lambda$shiftPages$24(i, (Integer) obj);
            }
        }).sorted().collect(Collectors.toList());
        Log.i(TAG, "[shiftPages] " + Arrays.toString(list.toArray()));
        HashMap map = new HashMap();
        for (Integer num : list) {
            DataSetParams dataSetParamsRemove = this.parmsMap.remove(num);
            if (dataSetParamsRemove != null) {
                map.put(Integer.valueOf(num.intValue() - 1), dataSetParamsRemove);
            }
        }
        this.parmsMap.putAll(map);
        for (Integer num2 : list) {
            List<BaseData> listRemove = this.mAllDataMap.remove(num2);
            if (listRemove != null) {
                this.mAllDataMap.put(Integer.valueOf(num2.intValue() - 1), listRemove);
            }
            Log.i(TAG, String.format("[shiftPage] movePage[%d] -> [%d] curve size[%d]", num2, Integer.valueOf(num2.intValue() - 1), Integer.valueOf(listRemove == null ? 0 : listRemove.size())));
            if (listRemove != null) {
                if (!listRemove.isEmpty()) {
                    DataCurve dataCurve = (DataCurve) listRemove.get(0);
                    float f3 = dataCurve.renderOffsetX;
                    float f4 = dataCurve.renderOffsetY;
                    int i2 = dataCurve.pageIndex;
                    float f5 = f - f3;
                    float f6 = f2 - f4;
                    for (BaseData baseData : listRemove) {
                        baseData.doMove(f5, f6);
                        DataCurve dataCurve2 = (DataCurve) baseData;
                        dataCurve2.pageIndex = i;
                        dataCurve2.renderOffsetX = f;
                        dataCurve2.renderOffsetY = f2;
                    }
                    i = i2;
                    f = f3;
                    f2 = f4;
                } else {
                    PointF pageOffset = this.getPageOffsetCallback.getPageOffset(num2.intValue());
                    f = pageOffset.x;
                    f2 = pageOffset.y;
                    i = num2.intValue();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$shiftPages$24(int i, Integer num) {
        return num.intValue() > i;
    }

    public boolean updateCurveDataPageIndex(final int i, int i2, int i3, float f, float f2) {
        BaseData baseDataOrElse;
        List<BaseData> list = this.mAllDataMap.get(Integer.valueOf(i2));
        if (list == null || list.isEmpty() || (baseDataOrElse = list.stream().filter(new Predicate() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DataSet.lambda$updateCurveDataPageIndex$25(i, (BaseData) obj);
            }
        }).findFirst().orElse(null)) == null || !list.remove(baseDataOrElse)) {
            return false;
        }
        DataCurve dataCurve = (DataCurve) baseDataOrElse;
        dataCurve.pageIndex = i3;
        dataCurve.renderOffsetX = f;
        dataCurve.renderOffsetY = f2;
        this.mAllDataMap.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: xc.engine.data.pagedata.DataSet$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DataSet.lambda$updateCurveDataPageIndex$26((Integer) obj);
            }
        }).add(baseDataOrElse);
        Log.i(TAG, String.format("updateCurveDataPageIndex: curve[%d] page[%d]->page[%d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        return true;
    }

    static /* synthetic */ boolean lambda$updateCurveDataPageIndex$25(int i, BaseData baseData) {
        return baseData != null && baseData.mId == i;
    }

    static /* synthetic */ List lambda$updateCurveDataPageIndex$26(Integer num) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByTime(DataCurve dataCurve, DataCurve dataCurve2) {
        if (dataCurve.mTime == 0 || dataCurve.size(dataCurve.mSavePoints) == 0 || dataCurve2.size(dataCurve2.mSavePoints) == 0) {
            return -1;
        }
        return Long.compare(dataCurve.mTime + ((long) dataCurve.getCurvePointList(dataCurve.mSavePoints).getTime(0)), dataCurve2.mTime + ((long) dataCurve2.getCurvePointList(dataCurve2.mSavePoints).getTime(0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByTime(BaseData baseData, BaseData baseData2) {
        return Long.compare(baseData.mTime, baseData2.mTime);
    }

    public void setGetPageOffsetCallback(DataAccessImpl.GetPageOffsetCallback getPageOffsetCallback) {
        this.getPageOffsetCallback = getPageOffsetCallback;
    }
}
