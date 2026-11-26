package xc.richtext.operate.scale;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.data.bean.SpannedScaleInfo;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.view.SpannedLayoutChanger;
import xc.richtext.view.img.CsImageDrawModel;

/* JADX INFO: loaded from: classes2.dex */
public class ScaleOperator implements IScaleOperator {
    public CsImageDrawModel mCsImageDrawModel;
    private final EngineBindContext mEngineBindContext;
    public float mHeight;
    private int[] mIdArray;
    public ScaleParams mScaleParams = new ScaleParams();
    public float mWidth;
    private boolean unLimit;

    public ScaleOperator(EngineBindContext engineBindContext) {
        this.mEngineBindContext = engineBindContext;
    }

    private RectF getRectF() {
        return new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public void setVisibleSize(float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
        this.mEngineBindContext.setDataDrawRatio(getDrawRatio());
        Iterator<SpannedData> it = this.mEngineBindContext.getAllDataList().iterator();
        while (it.hasNext()) {
            it.next().setRatioData(getDrawRatio());
        }
        SpannedSelectObject spannedSelectObject = this.mEngineBindContext.mSpannedEditOperator.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            float f3 = spannedSelectObject.mWidth == 0.0f ? 1.0f : f / spannedSelectObject.mWidth;
            spannedSelectObject.mWidth = f;
            spannedSelectObject.mHeight = f2;
            Matrix matrix = new Matrix();
            matrix.postScale(f3, f3);
            matrix.mapRect(spannedSelectObject.mSelectRectF);
            matrix.mapRect(spannedSelectObject.mDrawRectF);
            SpannedLayoutChanger spannedLayoutChanger = spannedSelectObject.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedSelected(spannedSelectObject);
            }
        }
        updateVisibleList(true);
    }

    private List<SpannedData> getVisibleList(boolean z) {
        ArrayList arrayList = new ArrayList();
        RectF rectF = getRectF();
        rectF.top -= this.mHeight * 3.0f;
        rectF.bottom += this.mHeight * 3.0f;
        for (SpannedData spannedData : this.mEngineBindContext.getAllDataList()) {
            if ((this.unLimit && spannedData.mSpannedStatus != 3) || z) {
                spannedData.mSpannedStatus = 0;
                arrayList.add(spannedData);
            } else if (spannedData.getRotatedBoundingBox(this.mEngineBindContext.screenRenderProvider).intersect(rectF)) {
                arrayList.add(spannedData);
            }
        }
        return arrayList;
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public float getDrawRatio() {
        EngineBindContext engineBindContext = this.mEngineBindContext;
        if (engineBindContext == null || engineBindContext.getSpannedLayoutCallBack() == null) {
            return 1.0f;
        }
        int toolBarHeight = this.mEngineBindContext.getSpannedLayoutCallBack().getToolBarHeight();
        float fMax = this.mWidth;
        if (!this.unLimit) {
            fMax = Math.max(fMax, this.mHeight + toolBarHeight);
        }
        return fMax / 1000.0f;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public void doScaleReal(float f, PointF pointF, PointF pointF2) {
        ScaleParams scaleParams = this.mScaleParams;
        scaleParams.mTranslatePointF.set(pointF);
        scaleParams.mScaleReal = f;
        updateSpanList();
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(this.mIdArray, true, false);
        }
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public void updateSpannedScaleInfo(float f, PointF pointF) {
        ScaleParams scaleParams = this.mScaleParams;
        scaleParams.mTranslatePointF.set(pointF);
        scaleParams.mScaleReal = f;
        updateSpanList();
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public void endScaleReal() {
        updateSpanList();
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(this.mIdArray, true, true);
        }
    }

    private void updateSpanList() {
        List<SpannedData> allDataList = this.mEngineBindContext.getAllDataList();
        if (allDataList.isEmpty()) {
            return;
        }
        this.mIdArray = new int[allDataList.size()];
        for (int i = 0; i < allDataList.size(); i++) {
            SpannedData spannedData = allDataList.get(i);
            PointF pointF = this.mScaleParams.mTranslatePointF;
            spannedData.mTranslatePointF.set(pointF.x, pointF.y);
            spannedData.mScale = this.mScaleParams.mScaleReal;
            this.mIdArray[i] = allDataList.get(i).getId();
        }
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public SpannedScaleInfo getSpannedScaleInfo() {
        ScaleParams scaleParams = this.mScaleParams;
        PointF pointF = scaleParams.mTranslatePointF;
        return new SpannedScaleInfo(pointF.x, pointF.y, scaleParams.mScaleReal);
    }

    @Override // xc.richtext.operate.scale.IScaleOperator
    public void setUnLimit(boolean z) {
        this.unLimit = z;
    }

    public void updateVisibleBlankImage(String str) {
        for (SpannedData spannedData : (ArrayList) getVisibleList(false)) {
            if (spannedData.getType() == 7 && spannedData.mBitmapPath.endsWith(str)) {
                this.mCsImageDrawModel.refreshBitmap(spannedData);
            }
        }
    }

    public void updateVisibleList(boolean z) {
        ArrayList arrayList = (ArrayList) getVisibleList(z);
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((SpannedData) arrayList.get(i)).getId();
        }
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, true, true);
        }
    }

    public void updateVisibleImgList() {
        refreshImageList(getVisibleList(false));
    }

    public void refreshImageList(List<SpannedData> list) {
        CopyOnWriteArrayList<BitmapBean> copyOnWriteArrayList = this.mCsImageDrawModel.mBitmapBeanList;
        List listEmptyList = Collections.emptyList();
        if (!copyOnWriteArrayList.isEmpty()) {
            listEmptyList = (List) copyOnWriteArrayList.stream().map(new Function() { // from class: xc.richtext.operate.scale.ScaleOperator$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((BitmapBean) obj).mId);
                }
            }).collect(Collectors.toList());
        }
        for (SpannedData spannedData : list) {
            if (spannedData.getType() == 7) {
                if (!listEmptyList.contains(Integer.valueOf(spannedData.mId))) {
                    copyOnWriteArrayList.add(new BitmapBean(spannedData.mId, spannedData.mBitmapPath, spannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider), spannedData.mDegrees, spannedData.mSpannedStatus));
                }
                this.mCsImageDrawModel.refreshBitmap(spannedData);
            }
        }
    }

    public List<SpannedData> intersectData(List<SpannedData> list) {
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : list) {
            spannedData.getRotatedBoundingBox(this.mEngineBindContext.screenRenderProvider);
            spannedData.mSpannedStatus = 0;
            arrayList.add(spannedData);
        }
        return arrayList;
    }

    public boolean intersectData(SpannedData spannedData) {
        boolean zIntersect = spannedData.getRotatedBoundingBox(this.mEngineBindContext.screenRenderProvider).intersect(getRectF());
        if (spannedData.mSpannedStatus != 3) {
            spannedData.mSpannedStatus = zIntersect ? 0 : 2;
        }
        return zIntersect;
    }
}
