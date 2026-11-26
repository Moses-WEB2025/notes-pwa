package xc.engine.operate.canvas.draw.infinite;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class PageItem {
    public static final int EMPTY = 0;
    public static final int INVALID = -1;
    public static final int RENDERED = 1;
    private List<DataCurve> intersectingCurves;
    private volatile Bitmap mBitmap;
    private int mColumnIndex;
    private int mId;
    private Rect mPosRect;
    private int mRowIndex;
    private int mStatus;

    public PageItem(int i, Rect rect, Bitmap bitmap) {
        this.mStatus = i;
        this.mPosRect = rect;
        this.mBitmap = bitmap;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setPositionRect(Rect rect) {
        this.mPosRect.set(rect);
    }

    public Rect getPositionRect() {
        return this.mPosRect;
    }

    public List<DataCurve> getIntersectingCurves() {
        return this.intersectingCurves;
    }

    public void setIntersectingCurves(List<DataCurve> list) {
        this.intersectingCurves = list;
    }

    public int getId() {
        return this.mId;
    }

    public int getRowIndex() {
        return this.mRowIndex;
    }

    public int getColumnIndex() {
        return this.mColumnIndex;
    }

    public void updateIndex(int i, int i2) {
        this.mRowIndex = i;
        this.mColumnIndex = i2;
        this.mId = generateUniqueKey(i, i2);
    }

    public static int generateUniqueKey(int i, int i2) {
        if (i > 65535 || i2 > 65535) {
            throw new IllegalArgumentException("PageFrag: Row or column index is out of bounds");
        }
        return (i << 16) | i2;
    }

    public String getIdString() {
        return this.mRowIndex + " / " + this.mColumnIndex;
    }

    public static int[] deconstructKey(int i) {
        return new int[]{i >> 16, i & 65535};
    }

    public void clearCurvesData() {
        List<DataCurve> list = this.intersectingCurves;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (DataCurve dataCurve : list) {
            if (dataCurve.size(dataCurve.mOriginPoints) != 0) {
                synchronized (dataCurve.mLockObj) {
                    dataCurve.clearPoints(dataCurve.mOriginPoints);
                }
            }
        }
    }
}
