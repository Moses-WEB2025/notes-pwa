package xc.richtext.data;

import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.step.LocalStepManager;
import xc.richtext.utils.SpannedUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedMap {
    private static final String TAG = "SpannedMap";
    public SpannedDataLoadCallBack mSpannedDataLoadCallBack;
    private LocalStepManager mStepManager;
    private List<SpannedData> mSpannedDataList = new ArrayList(0);
    private float mDrawRatio = 1.0f;
    private int pageIndex = 0;

    public interface SpannedDataLoadCallBack {
        void onLoadFinish(boolean z);
    }

    public SpannedMap(LocalStepManager localStepManager) {
        this.mStepManager = localStepManager;
    }

    public static SpannedMap createInstance() {
        return new SpannedMap(null);
    }

    public static SpannedMap createInstance(LocalStepManager localStepManager) {
        return new SpannedMap(localStepManager);
    }

    public final boolean hasIntersection(SpannedData spannedData, Path path, RectF rectF, float f, float f2) {
        RectF rectF2 = new RectF();
        Path rotatedRectPath = SpannedUtils.getRotatedRectPath(rectF, spannedData.getDegrees(), f, f2);
        rotatedRectPath.computeBounds(rectF2, false);
        Rect rect = new Rect((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
        Region region = new Region();
        region.setPath(rotatedRectPath, new Region(rect));
        Region region2 = new Region();
        region2.setPath(path, new Region(rect));
        return region2.op(region, Region.Op.INTERSECT);
    }

    public void loadTextFromBuffer(String str, int i, int[] iArr, List<byte[]> list) {
        SpannedMap spannedMap;
        clear();
        try {
            spannedMap = this;
            try {
                new SpannedBufferHelper().readTextByBuffer(spannedMap, str, i, iArr, list);
            } catch (IOException e) {
                e = e;
                Log.e(TAG, e.toString());
            }
        } catch (IOException e2) {
            e = e2;
            spannedMap = this;
        }
        SpannedDataLoadCallBack spannedDataLoadCallBack = spannedMap.mSpannedDataLoadCallBack;
        if (spannedDataLoadCallBack != null) {
            spannedDataLoadCallBack.onLoadFinish(true);
        }
    }

    public IBufferBean getSpannedBuffer(String str, int i) {
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : getDataListVisible()) {
            if (spannedData.getType() != 11 || !TextUtils.isEmpty(spannedData.getText())) {
                arrayList.add(spannedData);
            }
        }
        try {
            return new SpannedBufferHelper().readBufferBean(str, i, arrayList);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public void add(SpannedData spannedData) {
        spannedData.setRatioData(this.mDrawRatio);
        spannedData.setPageIndex(this.pageIndex);
        this.mSpannedDataList.add(spannedData);
    }

    public void remove(final SpannedData spannedData) {
        Log.d(TAG, "remove id:" + spannedData.mId);
        this.mSpannedDataList.removeIf(new Predicate() { // from class: xc.richtext.data.SpannedMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SpannedMap.lambda$remove$0(spannedData, (SpannedData) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$remove$0(SpannedData spannedData, SpannedData spannedData2) {
        return spannedData2.mId == spannedData.mId;
    }

    public void add(List<SpannedData> list) {
        Iterator<SpannedData> it = list.iterator();
        while (it.hasNext()) {
            it.next().setPageIndex(this.pageIndex);
        }
        this.mSpannedDataList.addAll(list);
    }

    public List<SpannedData> getDataListAll() {
        final ArrayList arrayList = new ArrayList();
        this.mSpannedDataList.forEach(new Consumer() { // from class: xc.richtext.data.SpannedMap$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SpannedMap.lambda$getDataListAll$1(arrayList, (SpannedData) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ void lambda$getDataListAll$1(List list, SpannedData spannedData) {
        if (spannedData.mSpannedStatus != 3) {
            list.add(spannedData);
        }
    }

    public List<SpannedData> getImageListAll() {
        final ArrayList arrayList = new ArrayList();
        this.mSpannedDataList.forEach(new Consumer() { // from class: xc.richtext.data.SpannedMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SpannedMap.lambda$getImageListAll$2(arrayList, (SpannedData) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ void lambda$getImageListAll$2(List list, SpannedData spannedData) {
        if (spannedData.mSpannedStatus == 3 || spannedData.getType() != 7) {
            return;
        }
        list.add(spannedData);
    }

    public void refreshList(List<SpannedData> list) {
        this.mSpannedDataList.clear();
        this.mSpannedDataList.addAll(list);
    }

    public void refreshData(SpannedData spannedData) {
        if (this.mSpannedDataList.remove(spannedData)) {
            this.mSpannedDataList.add(spannedData);
        }
    }

    public List<SpannedData> getDataList(int i) {
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.getType() == i && spannedData.mSpannedStatus != 3) {
                arrayList.add(spannedData);
            }
        }
        return arrayList;
    }

    public List<SpannedData> getDataList(RectF rectF) {
        ArrayList arrayList = new ArrayList();
        Region region = new Region();
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.isVisible()) {
                region.setEmpty();
                region.setPath(SpannedUtils.getRotatedRectPath(spannedData.getRatioRectF(), spannedData.getDegrees(), spannedData.getRatioRectF().centerX(), spannedData.getRatioRectF().centerY()), new Region(new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom)));
                if (!region.getBounds().isEmpty()) {
                    arrayList.add(spannedData);
                }
            }
        }
        return arrayList;
    }

    public List<SpannedData> getDataList(RectF rectF, int i) {
        ArrayList arrayList = new ArrayList();
        Region region = new Region();
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.isVisible()) {
                region.setEmpty();
                region.setPath(SpannedUtils.getRotatedRectPath(spannedData.getRatioRectF(), spannedData.getDegrees(), spannedData.getRatioRectF().centerX(), spannedData.getRatioRectF().centerY()), new Region(new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom)));
                if (!region.getBounds().isEmpty() && spannedData.getType() == i && spannedData.mSpannedStatus != 3) {
                    arrayList.add(spannedData);
                }
            }
        }
        return arrayList;
    }

    public SpannedData getSpannedData(int i) {
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (i == spannedData.getId()) {
                return spannedData;
            }
        }
        return null;
    }

    public SpannedData getSpanData(float f, float f2, int i, ScreenRenderProvider screenRenderProvider) {
        ArrayList arrayList = (ArrayList) getDataList(i);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((SpannedData) arrayList.get(size)).isVisible() && (((SpannedData) arrayList.get(size)).getType() != 11 || !TextUtils.isEmpty(((SpannedData) arrayList.get(size)).getText()))) {
                RectF drawRectF = ((SpannedData) arrayList.get(size)).getDrawRectF(screenRenderProvider);
                drawRectF.offset(0.0f, -f2);
                if (SpannedUtils.isPointInPath(SpannedUtils.getRotatedRectPath(drawRectF, ((SpannedData) arrayList.get(size)).getDegrees(), drawRectF.centerX(), drawRectF.centerY()), f, 0.0f)) {
                    return (SpannedData) arrayList.get(size);
                }
            }
        }
        return null;
    }

    public List<SpannedData> getDataListVisible() {
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.isVisible() || spannedData.mSpannedStatus == 1) {
                arrayList.add(spannedData);
            }
        }
        return arrayList;
    }

    public float getDrawRatio() {
        return this.mDrawRatio;
    }

    public void setDataDrawRatio(float f) {
        Log.d(TAG, "xwx setDataDrawRatio: " + f);
        this.mDrawRatio = f;
        Iterator<SpannedData> it = this.mSpannedDataList.iterator();
        while (it.hasNext()) {
            it.next().setRatioData(this.mDrawRatio);
        }
    }

    public void clear() {
        this.mSpannedDataList.clear();
    }

    public boolean isContentEmpty() {
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.mType != 11 || !TextUtils.isEmpty(spannedData.mText)) {
                if (spannedData.isVisible() || spannedData.mSpannedStatus == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public RectF getContentRange() {
        RectF rectF = new RectF();
        for (SpannedData spannedData : getDataListVisible()) {
            if (rectF.isEmpty()) {
                rectF.set(spannedData.getRotatedBoundingBox());
            } else {
                rectF.union(spannedData.getRotatedBoundingBox());
            }
        }
        return rectF;
    }

    public LocalStepManager getStepManager() {
        return this.mStepManager;
    }

    public SpannedInfo unBindSpannedInfo() {
        return new SpannedInfo(this);
    }

    public void bindSpannedInfo(SpannedInfo spannedInfo) {
        this.mSpannedDataList.clear();
        this.mSpannedDataList.addAll(new ArrayList(spannedInfo.mSpannedMap.mSpannedDataList));
        updateUndoList(spannedInfo);
        updateRedoList(spannedInfo);
    }

    private void updateRedoList(SpannedInfo spannedInfo) {
        Collection arrayList;
        if (this.mStepManager != null) {
            LocalStepManager localStepManager = spannedInfo.mSpannedMap.mStepManager;
            if (localStepManager == null) {
                arrayList = new ArrayList();
            } else {
                arrayList = localStepManager.mRedoSteplist;
            }
            ArrayList arrayList2 = new ArrayList(arrayList);
            this.mStepManager.mRedoSteplist.clear();
            this.mStepManager.mRedoSteplist.addAll(arrayList2);
        }
    }

    private void updateUndoList(SpannedInfo spannedInfo) {
        Collection arrayList;
        if (this.mStepManager != null) {
            LocalStepManager localStepManager = spannedInfo.mSpannedMap.mStepManager;
            if (localStepManager == null) {
                arrayList = new ArrayList();
            } else {
                arrayList = localStepManager.mUndoStepList;
            }
            ArrayList arrayList2 = new ArrayList(arrayList);
            this.mStepManager.mUndoStepList.clear();
            this.mStepManager.mUndoStepList.addAll(arrayList2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0075 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:16:0x0076 A[RETURN] */
    public boolean isOnSpannedData(float f, float f2, ScreenRenderProvider screenRenderProvider) {
        SpannedData spannedData;
        for (int size = this.mSpannedDataList.size() - 1; size >= 0; size--) {
            if (this.mSpannedDataList.get(size).isVisible() && (this.mSpannedDataList.get(size).getType() != 11 || !TextUtils.isEmpty(this.mSpannedDataList.get(size).getText()))) {
                RectF drawRectF = this.mSpannedDataList.get(size).getDrawRectF(screenRenderProvider);
                drawRectF.offset(0.0f, -f2);
                if (SpannedUtils.isPointInPath(SpannedUtils.getRotatedRectPath(drawRectF, this.mSpannedDataList.get(size).getDegrees(), drawRectF.centerX(), drawRectF.centerY()), f, 0.0f)) {
                    spannedData = this.mSpannedDataList.get(size);
                    if (spannedData != null) {
                        return true;
                    }
                    return false;
                }
            }
        }
        spannedData = null;
        if (spannedData != null) {
            return true;
        }
        return false;
    }

    public boolean isOnSpannedData(float f, float f2, int i, ScreenRenderProvider screenRenderProvider) {
        return getSpanData(f, f2, i, screenRenderProvider) != null;
    }

    public Point getBitmapOutSize(SpannedData spannedData) {
        if (spannedData.mType == 7 && !TextUtils.isEmpty(spannedData.mBitmapPath)) {
            Point point = spannedData.mImagesizepoint;
            if (point.x <= 0 || point.y <= 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(spannedData.mBitmapPath, options);
                if (options.outWidth != 0 && options.outHeight != 0) {
                    spannedData.mImagesizepoint.set(options.outWidth, options.outHeight);
                }
            }
            return spannedData.mImagesizepoint;
        }
        return new Point();
    }

    public IBufferBean toBufferBean(List<SpannedData> list) {
        try {
            return SpannedBytesUtil.convertSpanDataToBufferBean(list);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public List<SpannedData> fromBufferBean(IBufferBean iBufferBean, ScreenRenderProvider screenRenderProvider) {
        int[] types = iBufferBean.getTypes();
        List<byte[]> buffers = iBufferBean.getBuffers();
        ArrayList arrayList = new ArrayList(types.length);
        try {
            SpannedBytesUtil.convertBuffersToSpanDatas(arrayList, types, buffers, screenRenderProvider);
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPageIndex(int i) {
        this.pageIndex = i;
    }
}
