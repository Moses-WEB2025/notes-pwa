package xc.richtext;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import xc.engine.api.CreationPageSDK;
import xc.engine.utils.FeatureManager;
import xc.richtext.api.ItemSpannedData;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.api.SpannedLayoutInterface;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.scale.ScaleOperator;
import xc.richtext.operate.step.LocalStepManager;

/* JADX INFO: loaded from: classes6.dex */
public class EngineBindContext {
    private static final String TAG = "EngineBindContext";
    public Context mContext;
    public final LocalStepManager mLocalStepManager;
    public SpannedEditOperator mSpannedEditOperator;
    private final SpannedMap mSpannedMap;
    public ScreenRenderProvider screenRenderProvider;
    private SpannedLayoutInterface spannedLayoutCallBack;
    public final ScaleOperator mScaleOperator = new ScaleOperator(this);
    private ConcurrentHashMap<Integer, SpannedMap> mSpannedMapEx = new ConcurrentHashMap<>();
    private final List<String> deleteImages = new ArrayList();

    public EngineBindContext(Context context, ScreenRenderProvider screenRenderProvider) {
        this.mContext = context;
        this.screenRenderProvider = screenRenderProvider;
        this.mSpannedEditOperator = new SpannedEditOperator(this, screenRenderProvider);
        LocalStepManager localStepManager = new LocalStepManager(this);
        this.mLocalStepManager = localStepManager;
        SpannedMap spannedMapCreateInstance = SpannedMap.createInstance(localStepManager);
        this.mSpannedMap = spannedMapCreateInstance;
        insertSpannedMap(0, spannedMapCreateInstance);
        spannedMapCreateInstance.setPageIndex(0);
    }

    public void setCallBack(SpannedLayoutInterface spannedLayoutInterface) {
        this.spannedLayoutCallBack = spannedLayoutInterface;
    }

    public SpannedLayoutInterface getSpannedLayoutCallBack() {
        return this.spannedLayoutCallBack;
    }

    public int getSpannedMapCount() {
        Log.d(TAG, "getSpannedMapCount:" + this.mSpannedMapEx.size());
        return this.mSpannedMapEx.size();
    }

    public SpannedMap getSpannedMap() {
        return getSpannedMap(0);
    }

    public SpannedMap getSpannedMap(int i) {
        if (this.mSpannedMapEx.containsKey(Integer.valueOf(i))) {
            return this.mSpannedMapEx.get(Integer.valueOf(i));
        }
        SpannedMap spannedMapCreateInstance = SpannedMap.createInstance(this.mLocalStepManager);
        spannedMapCreateInstance.setDataDrawRatio(this.mSpannedMap.getDrawRatio());
        spannedMapCreateInstance.mSpannedDataLoadCallBack = this.mSpannedMap.mSpannedDataLoadCallBack;
        insertSpannedMap(i, spannedMapCreateInstance);
        spannedMapCreateInstance.setPageIndex(i);
        return spannedMapCreateInstance;
    }

    public void addDeleteImages(List<SpannedData> list) {
        for (SpannedData spannedData : list) {
            if (spannedData.getType() == 7) {
                this.deleteImages.add(spannedData.mBitmapPath);
            }
        }
    }

    public void removeDeleteImages(List<SpannedData> list) {
        for (SpannedData spannedData : list) {
            if (spannedData.getType() == 7) {
                this.deleteImages.remove(spannedData.mBitmapPath);
            }
        }
    }

    public List<String> getDeleteImages() {
        return new ArrayList(this.deleteImages);
    }

    public void insertSpannedMap(int i, SpannedMap spannedMap) {
        if (this.mSpannedMapEx.containsKey(Integer.valueOf(i))) {
            return;
        }
        this.mSpannedMapEx.put(Integer.valueOf(i), spannedMap);
    }

    public void removeSpannedMap(int i) {
        this.mSpannedMapEx.remove(Integer.valueOf(i));
    }

    public boolean removePage(int i) {
        this.mSpannedEditOperator.deletePage(i);
        SpannedMap spannedMapRemove = this.mSpannedMapEx.remove(Integer.valueOf(i));
        if (spannedMapRemove == null) {
            return false;
        }
        spannedMapRemove.clear();
        Log.i(TAG, "[removePage] remove and clear page: " + i);
        return true;
    }

    static /* synthetic */ boolean lambda$shiftPages$0(int i, Integer num) {
        return num.intValue() > i;
    }

    public void shiftPages(final int i, float f) {
        List<Integer> list = (List) this.mSpannedMapEx.keySet().stream().filter(new Predicate() { // from class: xc.richtext.EngineBindContext$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EngineBindContext.lambda$shiftPages$0(i, (Integer) obj);
            }
        }).sorted().collect(Collectors.toList());
        Log.i(TAG, String.format("[shiftPages] removePage[%d] shiftPages: %s", Integer.valueOf(i), Arrays.toString(list.toArray())));
        for (Integer num : list) {
            SpannedMap spannedMapRemove = this.mSpannedMapEx.remove(num);
            if (spannedMapRemove != null) {
                for (SpannedData spannedData : spannedMapRemove.getDataListAll()) {
                    RectF rectF = spannedData.mRatioRectF;
                    rectF.offset(0.0f, f);
                    this.mLocalStepManager.updateOffset(f);
                    spannedData.mRatioRectF.set(rectF);
                    spannedData.setRectF(rectF);
                    spannedData.setPageIndex(num.intValue() - 1);
                }
                this.mSpannedMapEx.put(Integer.valueOf(num.intValue() - 1), spannedMapRemove);
                Log.i(TAG, String.format("[shiftPages] page[%d] -> page[%d]", num, Integer.valueOf(num.intValue() - 1)));
            }
        }
    }

    public LocalStepManager getLocalStepManager() {
        return this.mLocalStepManager;
    }

    public List<SpannedData> getAllDataList() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getDataListAll());
        }
        Log.d(TAG, "getAllDataList size:" + arrayList.size());
        return arrayList;
    }

    public List<SpannedData> getAllDataList(int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getDataList(i));
        }
        Log.d(TAG, "getAllDataList type:" + i + ", size:" + arrayList.size());
        return arrayList;
    }

    public List<SpannedData> getAllDataList(RectF rectF) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getDataList(rectF));
        }
        Log.d(TAG, "getAllDataList rectf:" + rectF + ", size:" + arrayList.size());
        return arrayList;
    }

    public List<SpannedData> getAllDataList(RectF rectF, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getDataList(rectF, i));
        }
        Log.d(TAG, "getAllDataList rectf:" + rectF + " type:" + i + ", size:" + arrayList.size());
        return arrayList;
    }

    public List<SpannedData> getPageDataList(int i, int i2) {
        ArrayList arrayList = new ArrayList(getSpannedMap(i).getDataList(i2));
        Log.d(TAG, "getPageDataList type:" + i2 + ", size:" + arrayList.size());
        return arrayList;
    }

    public SpannedData getSpannedData(int i) {
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            for (SpannedData spannedData : it.next().getValue().getDataListAll()) {
                if (i == spannedData.getId()) {
                    return spannedData;
                }
            }
        }
        return null;
    }

    public Integer getSpannedDataPageIndex(int i) {
        for (Map.Entry<Integer, SpannedMap> entry : this.mSpannedMapEx.entrySet()) {
            Iterator<SpannedData> it = entry.getValue().getDataListAll().iterator();
            while (it.hasNext()) {
                if (i == it.next().getId()) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public SpannedData getSpannedData(float f, float f2, int i) {
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            SpannedData spanData = it.next().getValue().getSpanData(f, f2, i, this.screenRenderProvider);
            if (spanData != null) {
                return spanData;
            }
        }
        return null;
    }

    public List<SpannedData> getDataListVisible() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getDataListVisible());
        }
        Log.d(TAG, "getDataListVisible size:" + arrayList.size());
        return arrayList;
    }

    public void setDataDrawRatio(float f) {
        Iterator<Map.Entry<Integer, SpannedMap>> it = this.mSpannedMapEx.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setDataDrawRatio(f);
        }
    }

    private void printSpannedMapData(int i, SpannedMap spannedMap) {
        Log.d(TAG, "SpannedMap index:" + i + " size:" + spannedMap.getDataListAll().size());
        for (SpannedData spannedData : spannedMap.getDataListAll()) {
            Log.d(TAG, "SpannedMap index:" + i + " mType:" + spannedData.mType + " rectf:" + spannedData.getRectF() + " mRatioRectF:" + spannedData.mRatioRectF + " id:" + spannedData.mId + " text:" + spannedData.mText + " mBitmapPath:" + spannedData.mBitmapPath);
        }
    }

    public ItemSpannedData unBindSpannedEngineData() {
        if (this.mSpannedMap == null) {
            return null;
        }
        return new ItemSpannedData(this.mSpannedMap.unBindSpannedInfo());
    }

    public ItemSpannedData unBindSpannedEngineData(int i) {
        SpannedMap spannedMap = this.mSpannedMapEx.get(Integer.valueOf(i));
        if (spannedMap == null) {
            return null;
        }
        return new ItemSpannedData(spannedMap.unBindSpannedInfo());
    }

    public boolean isBitmapSizeMax(int i, int i2) {
        List<SpannedData> pageDataList = getPageDataList(i, 7);
        int i3 = FeatureManager.getInstance().isSupportHighQuality() ? 88080384 : CreationPageSDK.BITMAP_MAX_BYTE_COUNT;
        for (SpannedData spannedData : pageDataList) {
            SpannedMap spannedMap = this.mSpannedMap;
            Point point = spannedMap == null ? new Point() : spannedMap.getBitmapOutSize(spannedData);
            i2 += point.x * point.y * 4;
            if (i2 > i3) {
                Log.i(TAG, String.format("page:%d bitmapSize(%d) is over MaxSize", Integer.valueOf(i), Integer.valueOf(i2)));
                return true;
            }
        }
        return i2 > i3;
    }

    public boolean isBitmapSizeMax(int i, List<SpannedData> list) {
        List<SpannedData> pageDataList = getPageDataList(i, 7);
        int i2 = FeatureManager.getInstance().isSupportHighQuality() ? 88080384 : CreationPageSDK.BITMAP_MAX_BYTE_COUNT;
        int i3 = 0;
        for (SpannedData spannedData : pageDataList) {
            SpannedMap spannedMap = this.mSpannedMap;
            Point point = spannedMap == null ? new Point() : spannedMap.getBitmapOutSize(spannedData);
            i3 += point.x * point.y * 4;
            if (i3 > i2) {
                Log.i(TAG, String.format("page:%d bitmapSize(%d) is over MaxSize", Integer.valueOf(i), Integer.valueOf(i3)));
                return true;
            }
        }
        for (SpannedData spannedData2 : list) {
            if (spannedData2.getType() == 7) {
                SpannedMap spannedMap2 = this.mSpannedMap;
                Point point2 = spannedMap2 == null ? new Point() : spannedMap2.getBitmapOutSize(spannedData2);
                i3 += point2.x * point2.y * 4;
                if (i3 > i2) {
                    Log.i(TAG, String.format("page:%d bitmapSize(%d) is over MaxSize", Integer.valueOf(i), Integer.valueOf(i3)));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTextSizeMax(int i, int i2) {
        return i2 + getPageDataList(i, 11).size() > 100;
    }

    public boolean isTextSizeMax(int i, List<SpannedData> list) {
        int i2;
        if (list != null) {
            Iterator<SpannedData> it = list.iterator();
            i2 = 0;
            while (it.hasNext()) {
                if (it.next().getType() == 11) {
                    i2++;
                }
            }
        } else {
            i2 = 0;
        }
        return i2 + getPageDataList(i, 11).size() > 100;
    }

    public boolean isOnSpannedData(float f, float f2) {
        SpannedMap spannedMap = this.mSpannedMapEx.get(Integer.valueOf(getPageIndex(f, f2)));
        return spannedMap != null && spannedMap.isOnSpannedData(f, f2, this.screenRenderProvider);
    }

    public boolean isOnTextSpannedData(float f, float f2, int i) {
        SpannedMap spannedMap = this.mSpannedMapEx.get(Integer.valueOf(getPageIndex(f, f2)));
        return spannedMap != null && spannedMap.isOnSpannedData(f, f2, i, this.screenRenderProvider);
    }

    public IBufferBean toBufferBean(List<SpannedData> list) {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null) {
            return null;
        }
        return spannedMap.toBufferBean(list);
    }

    public List<SpannedData> fromBufferBean(IBufferBean iBufferBean) {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null) {
            return null;
        }
        return spannedMap.fromBufferBean(iBufferBean, this.screenRenderProvider);
    }

    private int getPageIndex(float f, float f2) {
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider == null) {
            return 0;
        }
        int pageIndexByScreenSpacePoint = screenRenderProvider.getPageIndexByScreenSpacePoint(f, f2);
        Log.d(TAG, "getPageIndex:" + pageIndexByScreenSpacePoint + " point:(" + f + ", " + f2 + ")");
        return pageIndexByScreenSpacePoint;
    }

    public int getPageIndexByWorldSpacePoint(float f, float f2) {
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider == null) {
            return 0;
        }
        int pageIndexByWorldSpacePoint = screenRenderProvider.getPageIndexByWorldSpacePoint(f, f2);
        Log.d(TAG, "getPageIndex:" + pageIndexByWorldSpacePoint + " point:(" + f + ", " + f2 + ")");
        return pageIndexByWorldSpacePoint;
    }

    public void release() {
        this.screenRenderProvider = null;
        SpannedEditOperator spannedEditOperator = this.mSpannedEditOperator;
        if (spannedEditOperator != null) {
            spannedEditOperator.release();
        }
    }
}
