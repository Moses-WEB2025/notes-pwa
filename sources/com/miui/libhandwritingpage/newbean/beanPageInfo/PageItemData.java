package com.miui.libhandwritingpage.newbean.beanPageInfo;

import android.graphics.RectF;
import android.util.SparseArray;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.data.PathInfo;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.step.type.IStep;
import xc.engine.utils.FileUtil;
import xc.richtext.api.ItemSpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class PageItemData {
    public int bgColor;
    public BgGridConfiguration bgConfig;
    public int bgGrid;
    public String bgImage;
    public int bgImageMode;
    public String bookmark;
    public String covePath;
    public long curvTotalSavePoint;
    public volatile PathInfo engineData;
    public int exportBottom;
    public int exportLeft;
    public int exportRight;
    public int exportTop;
    public String filePath;
    public float fixWidth;
    public boolean hadLoad;
    public boolean hadSpannedLoad;
    public boolean hadUnBindSpanned;
    public int height;
    public int heightMM;
    public boolean isAIAdd;
    public boolean isAdd;
    public boolean isBgChanged;
    public boolean isCustom;
    public boolean isFixSize;
    public boolean isUnLimit;
    public int maxHeight;
    public int maxWidth;
    public int orientation;
    public int pageSizeType;
    public String pdfId;
    public int pdfIndex;
    public List<IStep> redoList;
    public boolean serialized;
    public int sizeUnit;
    public ItemSpannedData spannedData;
    public int standardWidth;
    public int unLimitBottom;
    public int unLimitEnd;
    public int unLimitHeight;
    public int unLimitStart;
    public boolean unLimitToNormal;
    public int unLimitTop;
    public int unLimitWidth;
    public List<IStep> undoList;
    public boolean useNewSize;
    public int viewId;
    public int width;
    public int widthMM;
    private final String TAG = getClass().getSimpleName();
    public volatile String unbindDataPath = null;
    public boolean isContentEmpty = true;
    public int position = -1;
    public RectF layoutRectF = new RectF();
    public final Object lock = new Object();
    private int pageIndex = 0;
    public boolean isModified = true;

    public float getItemHeight(int i, float f) {
        return this.useNewSize ? this.maxHeight * f : ((i * this.height) * f) / this.width;
    }

    public void release() {
        this.engineData = null;
        this.spannedData = null;
        if (this.unbindDataPath != null) {
            try {
                FileUtil.deleteFile(new File(this.unbindDataPath));
            } catch (Exception unused) {
            }
        }
        this.unbindDataPath = null;
    }

    public boolean isEngineDataEmpty() {
        return this.engineData != null;
    }

    public void serialize() {
        List<IStep> list = this.redoList;
        if (list != null) {
            Iterator<IStep> it = list.iterator();
            while (it.hasNext()) {
                it.next().serialize();
            }
        }
        List<IStep> list2 = this.undoList;
        if (list2 != null) {
            Iterator<IStep> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().serialize();
            }
        }
    }

    public void unSerialize(SparseArray<DataCurve> sparseArray) {
        List<IStep> list = this.redoList;
        if (list != null) {
            Iterator<IStep> it = list.iterator();
            while (it.hasNext()) {
                it.next().unserialize(sparseArray);
            }
        }
        List<IStep> list2 = this.undoList;
        if (list2 != null) {
            Iterator<IStep> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().unserialize(sparseArray);
            }
        }
    }

    public void resetStep(List<IStep> list, List<IStep> list2) {
        if (this.redoList == null) {
            this.redoList = new ArrayList();
        }
        if (this.undoList == null) {
            this.undoList = new ArrayList();
        }
        this.redoList.clear();
        this.undoList.clear();
        if (list != null) {
            this.redoList.addAll(list);
        }
        if (list2 != null) {
            this.undoList.addAll(list2);
        }
    }

    public PageItemData() {
    }

    public PageItemData(BgGridConfiguration bgGridConfiguration, String str, String str2, int i, String str3, int i2, int i3, int i4, int i5, int i6, int i7, String str4) {
        this.bgConfig = bgGridConfiguration;
        this.covePath = str;
        this.bookmark = str2;
        this.pdfIndex = i;
        this.bgImage = str3;
        this.bgImageMode = i2;
        this.orientation = i3;
        this.bgColor = i4;
        this.width = i5;
        this.height = i6;
        this.viewId = i7;
        this.pdfId = str4;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int i) {
        this.pageIndex = i;
    }
}
