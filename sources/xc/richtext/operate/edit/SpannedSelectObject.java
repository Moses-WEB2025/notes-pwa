package xc.richtext.operate.edit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import xc.engine.api.CreationPageSDK;
import xc.richtext.EngineBindContext;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpanUpdateInfo;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.scale.ScaleParams;
import xc.richtext.operate.step.CropStep;
import xc.richtext.operate.step.DeleteStep;
import xc.richtext.operate.step.MixedStep;
import xc.richtext.operate.step.MoveStep;
import xc.richtext.utils.CsUtil;
import xc.richtext.utils.ScreenShortUtil;
import xc.richtext.utils.SelectedShortUtil;
import xc.richtext.utils.SpannedUtils;
import xc.richtext.utils.TextLayoutUtil;
import xc.richtext.view.SpannedLayoutChanger;
import xc.richtext.view.SpannedLayoutModel;
import xc.richtext.view.img.CsImageView;
import xc.richtext.view.img.SpannedImageManager;

/* JADX INFO: loaded from: classes.dex */
public class SpannedSelectObject implements ISpannedSelectObject {
    public EngineBindContext mEngineBindContext;
    public float mHeight;
    public Paint mPaint;
    public List<SpannedBitmap> mSpannedBitmapList;
    public List<SpannedData> mSpannedDataList;
    public float mWidth;
    private final String TAG = "SpannedSelectObject";
    public int mSelectAction = 1;
    public RectF mSelectRectF = new RectF();
    public PointF mTranslationPointF = new PointF();
    public float mScale = 1.0f;
    public float mDegrees = 0.0f;
    public PointF mCenterPointF = new PointF();
    public PointF mPointF = new PointF();
    public Bitmap mImageBitmap = null;
    public Bitmap mTextBitmap = null;
    public RectF mDrawRectF = new RectF();
    public int mMaxSize = CreationPageSDK.BITMAP_MAX_BYTE_COUNT;

    public SpannedSelectObject(List<SpannedData> list, EngineBindContext engineBindContext) {
        this.mSpannedDataList = new ArrayList(list);
        this.mEngineBindContext = engineBindContext;
        this.mWidth = engineBindContext.mScaleOperator.getWidth();
        this.mHeight = engineBindContext.mScaleOperator.getHeight();
        calculateRectUnion();
        this.mDrawRectF.set(getSelectRectF());
        moveSpannedDataListToLast();
        initPaint();
        refreshSpanList();
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void onDraw(Canvas canvas) {
        Bitmap bitmap = this.mImageBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        try {
            canvas.save();
            if (this.mTranslationPointF.x != 0.0f || this.mTranslationPointF.y != 0.0f) {
                canvas.translate(this.mTranslationPointF.x, this.mTranslationPointF.y);
            }
            float f = this.mScale;
            if (f != 1.0f) {
                canvas.scale(f, f, this.mCenterPointF.x, this.mCenterPointF.y);
            }
            float f2 = this.mDegrees;
            if (f2 != 0.0f) {
                canvas.rotate(f2, this.mCenterPointF.x, this.mCenterPointF.y);
            }
            canvas.drawBitmap(this.mImageBitmap, (Rect) null, this.mDrawRectF, this.mPaint);
            canvas.restore();
        } catch (Exception unused) {
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void onDrawText(Canvas canvas) {
        Bitmap bitmap = this.mTextBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        try {
            canvas.save();
            if (this.mTranslationPointF.x != 0.0f || this.mTranslationPointF.y != 0.0f) {
                canvas.translate(this.mTranslationPointF.x, this.mTranslationPointF.y);
            }
            float f = this.mScale;
            if (f != 1.0f) {
                canvas.scale(f, f, this.mCenterPointF.x, this.mCenterPointF.y);
            }
            float f2 = this.mDegrees;
            if (f2 != 0.0f) {
                canvas.rotate(f2, this.mCenterPointF.x, this.mCenterPointF.y);
            }
            canvas.drawBitmap(this.mTextBitmap, (Rect) null, this.mDrawRectF, this.mPaint);
            canvas.restore();
        } catch (Exception unused) {
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedObject
    public void startAction(int i) {
        this.mSelectAction = i;
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doMove(float f, float f2) {
        this.mTranslationPointF.set(f, f2);
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        calculateRectUnion();
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, false, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doScale(float f, float f2, float f3) {
        SpannedLayoutChanger spannedLayoutChanger;
        this.mScale = f;
        unTranslateAndScale(f2, f3);
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        calculateRectUnion();
        if ((this.mSelectAction & 1) == 1 || (spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger) == null) {
            return;
        }
        spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doRotate(float f, float f2, float f3) {
        SpannedLayoutChanger spannedLayoutChanger;
        unTranslateAndScale(f2, f3);
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            RectF rectF = getRectF(spannedData);
            RectF rotatedBoundingBox = SpannedUtils.getRotatedBoundingBox(rectF, f - this.mDegrees, rectF.centerX(), rectF.centerY());
            RectF rotatedBoundingBox2 = SpannedUtils.getRotatedBoundingBox(rectF, f - this.mDegrees, f2, f3);
            PointF pointF = spannedData.mPointF;
            pointF.set(pointF.x + (rotatedBoundingBox2.left - rotatedBoundingBox.left), pointF.y + (rotatedBoundingBox2.top - rotatedBoundingBox.top));
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        this.mDegrees = f;
        calculateRectUnion();
        if ((this.mSelectAction & 1) == 1 || (spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger) == null) {
            return;
        }
        spannedLayoutChanger.onSpannedDataChanged(iArr, false, false);
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doCrop(RectF rectF, RectF rectF2) {
        List<SpannedData> list = this.mSpannedDataList;
        if (list == null || list.size() != 1) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mSpannedDataList.size());
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            arrayList.add(new SpanUpdateInfo(spannedData.mId, new RectF(rectF), new RectF(rectF2), spannedData.mDegrees, spannedData.mDegrees, this.mScale));
        }
        this.mEngineBindContext.mLocalStepManager.addStep(new CropStep(this.mEngineBindContext, this.mSpannedDataList, arrayList));
    }

    @Override // xc.richtext.operate.edit.ISpannedObject
    public void stopAction() {
        int[] iArr;
        float f = this.mScale;
        float f2 = this.mDegrees;
        int i = this.mSelectAction;
        int i2 = 0;
        if ((i & 4) == 4 || (i & 8) == 8) {
            ArrayList arrayList = new ArrayList(this.mSpannedDataList.size());
            int[] iArr2 = new int[this.mSpannedDataList.size()];
            while (i2 < this.mSpannedDataList.size()) {
                SpannedData spannedData = this.mSpannedDataList.get(i2);
                float f3 = spannedData.mDegrees;
                RectF rectF = new RectF(spannedData.getRectF());
                if (spannedData.mType == 11) {
                    spannedData.mTextSize *= this.mScale;
                }
                spannedData.mDegrees += this.mDegrees;
                RectF rectF2 = new RectF(spannedData.mRatioRectF);
                Matrix matrix = new Matrix();
                float f4 = this.mScale;
                matrix.setScale(f4, f4, this.mPointF.x, this.mPointF.y);
                matrix.mapRect(rectF2);
                RectF rotatedBoundingBox = SpannedUtils.getRotatedBoundingBox(rectF2, this.mDegrees, rectF2.centerX(), rectF2.centerY());
                RectF rotatedBoundingBox2 = SpannedUtils.getRotatedBoundingBox(rectF2, this.mDegrees, this.mPointF.x, this.mPointF.y);
                rectF2.offset(rotatedBoundingBox2.left - rotatedBoundingBox.left, rotatedBoundingBox2.top - rotatedBoundingBox.top);
                float f5 = this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
                rectF2.offset(this.mTranslationPointF.x / f5, this.mTranslationPointF.y / f5);
                spannedData.resetPoint();
                spannedData.mRatioRectF.set(rectF2);
                spannedData.setRectF(spannedData.mRatioRectF);
                arrayList.add(new SpanUpdateInfo(spannedData.mId, rectF, spannedData.getRectF(), f3, spannedData.mDegrees, this.mScale));
                iArr2[i2] = spannedData.mId;
                i2++;
            }
            this.mScale = 1.0f;
            this.mDegrees = 0.0f;
            this.mTranslationPointF.set(0.0f, 0.0f);
            this.mEngineBindContext.mLocalStepManager.addStep(new MixedStep(this.mEngineBindContext, this.mSpannedDataList, arrayList));
            iArr = iArr2;
        } else if ((i & 2) == 2) {
            iArr = new int[this.mSpannedDataList.size()];
            float f6 = 0.0f;
            float f7 = 0.0f;
            while (i2 < this.mSpannedDataList.size()) {
                SpannedData spannedData2 = this.mSpannedDataList.get(i2);
                RectF rectF3 = new RectF(spannedData2.mRatioRectF);
                RectF rectF4 = spannedData2.mRatioRectF;
                float f8 = this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
                rectF4.offset(this.mTranslationPointF.x / f8, this.mTranslationPointF.y / f8);
                spannedData2.mRatioRectF.set(rectF4);
                spannedData2.setRectF(rectF4);
                if (i2 == 0) {
                    RectF rectF5 = new RectF(spannedData2.mRatioRectF);
                    float f9 = rectF5.left - rectF3.left;
                    f7 = rectF5.top - rectF3.top;
                    f6 = f9;
                }
                iArr[i2] = spannedData2.mId;
                i2++;
            }
            this.mTranslationPointF.set(0.0f, 0.0f);
            this.mEngineBindContext.mLocalStepManager.addStep(new MoveStep(this.mEngineBindContext, this.mSpannedDataList, new PointF(f6, f7)));
        } else {
            iArr = null;
        }
        if ((this.mSelectAction & 1) != 1) {
            calculateRectUnion();
            if (f != 1.0f || f2 != 0.0f) {
                initSelectBitmap(iArr);
            }
        }
        this.mDrawRectF.set(getSelectRectF());
        this.mSelectAction = 1;
        this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger.updateSpanDataPageIndex(this.mSpannedDataList);
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void cancelAction() {
        SpannedLayoutChanger spannedLayoutChanger;
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            spannedData.resetPoint();
            iArr[i] = spannedData.mId;
        }
        resetVariables();
        if ((this.mSelectAction & 1) == 1 || (spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger) == null) {
            return;
        }
        spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
    }

    public List<SpannedData> doCopy() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedDataCopy = this.mSpannedDataList.get(i).copy();
            spannedDataCopy.setRatioData(this.mEngineBindContext.mScaleOperator.getDrawRatio());
            arrayList.add(spannedDataCopy);
        }
        return arrayList;
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public int getClickTextId(float f, float f2) {
        SpannedData spannedData;
        int size = this.mSpannedDataList.size() - 1;
        while (true) {
            if (size < 0) {
                spannedData = null;
                break;
            }
            SpannedData spannedData2 = this.mSpannedDataList.get(size);
            if (spannedData2.mSpannedStatus != 3) {
                RectF rectF = getRectF(spannedData2);
                if (SpannedUtils.isPointInPath(SpannedUtils.getRotatedRectPath(rectF, this.mSpannedDataList.get(size).getDegrees(), rectF.centerX(), rectF.centerY()), f, f2)) {
                    spannedData = this.mSpannedDataList.get(size);
                    break;
                }
            }
            size--;
        }
        SpannedData spannedData3 = spannedData;
        if (spannedData3 == null || spannedData3.mType != 11) {
            return -1;
        }
        return spannedData3.mId;
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doDelete() {
        doDelete(false);
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void doDelete(boolean z) {
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            spannedData.mSpannedStatus = 3;
            arrayList.add(spannedData);
            iArr[i] = spannedData.mId;
        }
        if (z) {
            this.mEngineBindContext.mLocalStepManager.addStepForAI(new DeleteStep(this.mEngineBindContext, arrayList));
        } else {
            this.mEngineBindContext.mLocalStepManager.addStep(new DeleteStep(this.mEngineBindContext, arrayList));
        }
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void finishSelect() {
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            if (spannedData != null && spannedData.mSpannedStatus == 1) {
                spannedData.mSpannedStatus = 0;
            }
        }
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            SpannedLayoutModel spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel;
            List<SpannedBitmap> list = this.mSpannedBitmapList;
            if (list != null && spannedLayoutModel != null) {
                Iterator<SpannedBitmap> it = list.iterator();
                for (int i2 = 0; i2 < this.mSpannedBitmapList.size(); i2++) {
                    SpannedData spannedData2 = spannedLayoutModel.getEngineBindContext().getSpannedData(it.next().mId);
                    if (spannedData2 != null) {
                        if (spannedData2.getType() == 7) {
                            for (int i3 = 0; i3 < spannedLayoutModel.mSpannedEditLayout.getChildCount(); i3++) {
                                View childAt = spannedLayoutModel.mSpannedEditLayout.getChildAt(i3);
                                if (CsUtil.isSpanView(childAt) && spannedData2.getId() == ((SpannedData) childAt.getTag()).getId()) {
                                    break;
                                }
                            }
                        } else if (spannedData2.getType() == 11) {
                            spannedLayoutModel.updateSpannedByIdSingle(spannedData2.getId(), false, false);
                        }
                    }
                }
                int[] iArr = new int[this.mSpannedBitmapList.size()];
                for (int i4 = 0; i4 < this.mSpannedBitmapList.size(); i4++) {
                    iArr[i4] = this.mSpannedBitmapList.get(i4).mId;
                }
                spannedLayoutModel.bringTextToFront(iArr);
            }
            if (spannedLayoutModel != null) {
                SpannedEditOperator spannedEditOperator = spannedLayoutModel.mSpannedEditOperator;
                if (spannedEditOperator != null) {
                    spannedEditOperator.mSpannedSelectObject = null;
                }
                spannedLayoutModel.bringItemViewToFront(spannedLayoutModel.getEngineBindContext().getDataListVisible());
                spannedLayoutModel.mSpannedSelectObject = null;
            }
        }
        this.mSpannedDataList.clear();
        this.mSelectRectF.setEmpty();
        resetVariables();
        recycleBitmaps();
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public RectF getSelectRectF() {
        return this.mSelectRectF;
    }

    public boolean isContainId(int i) {
        List<SpannedData> list = this.mSpannedDataList;
        if (list != null) {
            Iterator<SpannedData> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().getId() == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public RectF getRectFById(int i) {
        List<SpannedData> list = this.mSpannedDataList;
        if (list != null) {
            for (SpannedData spannedData : list) {
                if (spannedData.getId() == i) {
                    return getRectF(spannedData);
                }
            }
        }
        return new RectF();
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public List<SpannedData> getSelectedList() {
        return this.mSpannedDataList;
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void externalSelected(boolean z) {
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.updateSpannedLayout(z, this.mSpannedDataList);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void resetSelectViewData() {
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            this.mSpannedDataList.get(i).mSpannedStatus = 1;
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, false, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void forceRedraw() {
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        initSelectBitmap(iArr);
    }

    private void recycleBitmaps() {
        Bitmap bitmap = this.mImageBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mImageBitmap.recycle();
            this.mImageBitmap = null;
        }
        Bitmap bitmap2 = this.mTextBitmap;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.mTextBitmap.recycle();
        this.mTextBitmap = null;
    }

    private void resetVariables() {
        this.mTranslationPointF.set(0.0f, 0.0f);
        this.mScale = 1.0f;
        this.mDegrees = 0.0f;
        this.mCenterPointF.set(0.0f, 0.0f);
        this.mPointF.set(0.0f, 0.0f);
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    private void moveSpannedDataListToLast() {
        for (SpannedData spannedData : this.mSpannedDataList) {
            int spannedMapCount = this.mEngineBindContext.getSpannedMapCount();
            for (int i = 0; i < spannedMapCount; i++) {
                List<SpannedData> dataListAll = this.mEngineBindContext.getSpannedMap(i).getDataListAll();
                if (dataListAll.contains(spannedData)) {
                    dataListAll.remove(spannedData);
                    dataListAll.add(dataListAll.size(), spannedData);
                }
            }
        }
    }

    private void calculateRectUnion() {
        this.mSelectRectF.setEmpty();
        ScreenRenderProvider screenRenderProvider = this.mEngineBindContext.screenRenderProvider;
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (this.mSelectRectF.isEmpty()) {
                this.mSelectRectF.set(spannedData.getRotatedBoundingBox(screenRenderProvider));
            } else {
                this.mSelectRectF.union(spannedData.getRotatedBoundingBox(screenRenderProvider));
            }
        }
    }

    private RectF getRectF(SpannedData spannedData) {
        RectF rectF = new RectF(spannedData.mRatioRectF);
        ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
        PointF pointF = new PointF(this.mCenterPointF.x, this.mCenterPointF.y);
        PointF pointF2 = scaleParams.mTranslatePointF;
        if (pointF2.x != 0.0f || pointF2.y != 0.0f || scaleParams.mScaleReal != 1.0f) {
            SpannedUtils.unTranslateAndScalePointF(pointF, pointF2.x, pointF2.y, scaleParams.mScaleReal);
        }
        float f = this.mScale;
        if (f != 1.0f) {
            rectF.set(SpannedUtils.getPerpendicularFoot(rectF, f, pointF));
        }
        float f2 = this.mTranslationPointF.x + spannedData.mPointF.x;
        float f3 = this.mTranslationPointF.y + spannedData.mPointF.y;
        if (f2 != 0.0f || f3 != 0.0f) {
            float f4 = scaleParams.mScaleReal;
            rectF.offset(f2 / f4, f3 / f4);
        }
        if (scaleParams.mTranslatePointF.x == 0.0f && scaleParams.mTranslatePointF.y == 0.0f && scaleParams.mScaleReal == 1.0f) {
            return rectF;
        }
        rectF.set(SpannedUtils.translateAndScaleRectF(rectF, scaleParams.mTranslatePointF.x, scaleParams.mTranslatePointF.y, scaleParams.mScaleReal));
        return rectF;
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void getSelectBitmap(Bitmap bitmap, RectF rectF, int i, boolean z) {
        if (bitmap != null) {
            new ScreenShortUtil().screenToBitmap(bitmap, rectF, this.mSpannedDataList, this.mEngineBindContext.mSpannedEditOperator.isDark(), i, z, this.mEngineBindContext.screenRenderProvider);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedSelectObject
    public void getSelectAndMaskBitmap(Bitmap bitmap, Bitmap bitmap2, RectF rectF, int i, int i2, boolean z) {
        if (bitmap != null) {
            new ScreenShortUtil().screenToViewAndMaskBitmap(bitmap, bitmap2, rectF, this.mSpannedDataList, this.mEngineBindContext.mSpannedEditOperator.isDark(), i, i2, z, this.mEngineBindContext.screenRenderProvider);
        }
    }

    private void refreshSpanList() {
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            iArr[i] = this.mSpannedDataList.get(i).getId();
        }
        initSelectBitmap(iArr);
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, false, false);
        }
    }

    private void initSelectBitmap(int[] iArr) {
        RectF selectRectF = getSelectRectF();
        if (selectRectF == null || selectRectF.isEmpty() || Float.isNaN(selectRectF.left)) {
            recycleBitmaps();
            return;
        }
        int iWidth = (int) selectRectF.width();
        int iHeight = (int) selectRectF.height();
        int i = iWidth * iHeight * 4;
        int i2 = this.mMaxSize;
        float fSqrt = i > i2 ? (float) Math.sqrt(i / i2) : 1.0f;
        int i3 = (int) (iWidth / fSqrt);
        int i4 = (int) (iHeight / fSqrt);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        drawSpan(bitmapCreateBitmap, selectRectF, iArr, 2);
        recycleBitmaps();
        this.mImageBitmap = bitmapCreateBitmap;
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        drawSpan(bitmapCreateBitmap2, selectRectF, iArr, 1);
        this.mTextBitmap = bitmapCreateBitmap2;
    }

    private void drawSpan(Bitmap bitmap, RectF rectF, int[] iArr, int i) {
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            SpannedLayoutModel spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel;
            this.mSpannedBitmapList = null;
            if (spannedLayoutModel != null) {
                this.mSpannedBitmapList = getSpannedBitmap(iArr, spannedLayoutModel);
            }
            if (bitmap.getWidth() == ((int) rectF.width())) {
                drawSelectWidthBitmap(bitmap, rectF, i);
            } else {
                drawUnSelectWidthBitmap(bitmap, rectF, i);
            }
        }
    }

    private void drawSelectWidthBitmap(Bitmap bitmap, RectF rectF, int i) {
        SpannedBitmap spannedBitmap;
        float f;
        float f2;
        SelectedShortUtil selectedShortUtil = new SelectedShortUtil(this.mEngineBindContext.mSpannedEditOperator.isDark());
        Canvas canvas = new Canvas(bitmap);
        ScreenRenderProvider screenRenderProvider = this.mEngineBindContext.screenRenderProvider;
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (i == 2 || spannedData.getType() != 11) {
                if (i != 1 && spannedData.getType() == 7) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.mSpannedBitmapList.size()) {
                            spannedBitmap = null;
                            break;
                        } else {
                            if (this.mSpannedBitmapList.get(i2).mId == spannedData.getId()) {
                                spannedBitmap = this.mSpannedBitmapList.get(i2);
                                break;
                            }
                            i2++;
                        }
                    }
                    RectF drawRectF = spannedData.getDrawRectF(screenRenderProvider);
                    RectF rectF2 = new RectF(drawRectF);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(spannedData.mDegrees, rectF2.centerX(), rectF2.centerY());
                    matrix.mapRect(rectF2);
                    rectF2.offset(-rectF.left, -rectF.top);
                    Bitmap drawBitmap = spannedBitmap == null ? null : spannedBitmap.mBitmap;
                    if (drawBitmap == null) {
                        drawBitmap = SpannedUtils.getDrawBitmap(spannedData.mBitmapPath, rectF2.width());
                    }
                    if (drawBitmap != null) {
                        if (spannedBitmap != null) {
                            spannedBitmap.mBitmap = drawBitmap;
                        }
                        canvas.save();
                        rectF2.set(drawRectF.left, drawRectF.top, drawRectF.right, drawRectF.bottom);
                        rectF2.offset(-rectF.left, -rectF.top);
                        if (spannedData.mDegrees != 0.0f) {
                            canvas.rotate(spannedData.mDegrees, rectF2.centerX(), rectF2.centerY());
                        }
                        canvas.drawBitmap(drawBitmap, new Rect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), rectF2, selectedShortUtil.dPaint);
                        canvas.restore();
                    }
                }
            } else if (!TextUtils.isEmpty(spannedData.getText())) {
                RectF drawRectF2 = spannedData.getDrawRectF(screenRenderProvider);
                selectedShortUtil.mTextPaint.setTextSize(spannedData.getRealTextSize());
                selectedShortUtil.mTextPaint.setColor(CsUtil.getRealTextColor(spannedData.mTextColor, selectedShortUtil.mIsDark));
                StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(spannedData, selectedShortUtil.mIsDark);
                if (builderObtainBuilder != null) {
                    StaticLayout staticLayoutBuild = builderObtainBuilder.build();
                    RectF drawRectF3 = spannedData.getDrawRectF(screenRenderProvider);
                    if (spannedData.mDegrees != 0.0f) {
                        float fCenterX = drawRectF3.centerX();
                        float fCenterY = drawRectF3.centerY();
                        RectF rectF3 = new RectF(drawRectF2);
                        Matrix matrix2 = new Matrix();
                        matrix2.setRotate(spannedData.mDegrees, fCenterX, fCenterY);
                        matrix2.mapRect(rectF3);
                        matrix2.reset();
                        RectF rectF4 = new RectF(drawRectF2);
                        Matrix matrix3 = new Matrix();
                        matrix3.setRotate(spannedData.mDegrees, drawRectF3.width() / 2.0f, drawRectF3.height() / 2.0f);
                        matrix3.mapRect(rectF4);
                        matrix3.reset();
                        f = (-rectF.left) + (rectF4.left - rectF3.left);
                        f2 = (rectF4.top - rectF3.top) + (-rectF.top);
                    } else {
                        f = -rectF.left;
                        f2 = -rectF.top;
                    }
                    if (spannedData.mType == 11) {
                        canvas.save();
                        Path rotatedRectPath = SpannedUtils.getRotatedRectPath(drawRectF3, spannedData.mDegrees, drawRectF3.centerX(), drawRectF3.centerY());
                        rotatedRectPath.offset(-rectF.left, -rectF.top);
                        selectedShortUtil.fPaint.setColor(spannedData.mBgColor);
                        canvas.drawPath(rotatedRectPath, selectedShortUtil.fPaint);
                        canvas.restore();
                    }
                    float fWidth = spannedData.getDrawRectF(screenRenderProvider).width() / staticLayoutBuild.getWidth();
                    canvas.save();
                    canvas.translate(f + drawRectF3.left, f2 + drawRectF3.top);
                    if (spannedData.mDegrees != 0) {
                        canvas.rotate(spannedData.mDegrees, drawRectF3.centerX(), drawRectF3.centerY());
                    }
                    canvas.scale(fWidth, fWidth);
                    staticLayoutBuild.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    private void drawUnSelectWidthBitmap(Bitmap bitmap, RectF rectF, int i) {
        SpannedBitmap spannedBitmap;
        StaticLayout staticLayoutBuild;
        RectF rectF2 = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        SelectedShortUtil selectedShortUtil = new SelectedShortUtil(this.mEngineBindContext.mSpannedEditOperator.isDark());
        float fWidth = rectF2.width() / rectF.width();
        RectF rectF3 = new RectF(rectF);
        Matrix matrix = new Matrix();
        matrix.setScale(fWidth, fWidth, rectF.centerX(), rectF.centerY());
        matrix.mapRect(rectF3);
        selectedShortUtil.dx = rectF2.left - rectF3.left;
        selectedShortUtil.dy = rectF2.top - rectF3.top;
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.translate(selectedShortUtil.dx, selectedShortUtil.dy);
        canvas.scale(fWidth, fWidth, rectF.centerX(), rectF.centerY());
        ScreenRenderProvider screenRenderProvider = this.mEngineBindContext.screenRenderProvider;
        for (SpannedData spannedData : this.mSpannedDataList) {
            if (spannedData.getType() != 11 || !TextUtils.isEmpty(spannedData.getText())) {
                if (i != 2 && spannedData.getType() == 11) {
                    RectF drawRectF = spannedData.getDrawRectF(screenRenderProvider);
                    String str = spannedData.mText;
                    selectedShortUtil.mTextPaint.setTextSize(spannedData.getRealTextSize());
                    selectedShortUtil.mTextPaint.setColor(CsUtil.getRealTextColor(spannedData.mTextColor, selectedShortUtil.mIsDark));
                    if (TextUtils.isEmpty(str)) {
                        staticLayoutBuild = null;
                    } else {
                        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(str, 0, str.length(), selectedShortUtil.mTextPaint, (int) drawRectF.width());
                        builderObtain.setLineSpacing(0.0f, 1.0f).setUseLineSpacingFromFallbacks(true).setTextDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
                        staticLayoutBuild = builderObtain.build();
                    }
                    RectF drawRectF2 = spannedData.getDrawRectF(screenRenderProvider);
                    if (spannedData.mType == 11) {
                        Path rotatedRectPath = SpannedUtils.getRotatedRectPath(drawRectF2, spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                        selectedShortUtil.fPaint.setColor(spannedData.mBgColor);
                        canvas.save();
                        canvas.drawPath(rotatedRectPath, selectedShortUtil.fPaint);
                        canvas.restore();
                    }
                    canvas.save();
                    if (spannedData.mDegrees != 0.0f) {
                        canvas.rotate(spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                    }
                    canvas.translate(drawRectF2.left, drawRectF2.top);
                    if (staticLayoutBuild != null) {
                        staticLayoutBuild.draw(canvas);
                    }
                    canvas.restore();
                } else if (i != 1 && spannedData.getType() == 7) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.mSpannedBitmapList.size()) {
                            spannedBitmap = null;
                            break;
                        }
                        spannedBitmap = this.mSpannedBitmapList.get(i2);
                        if (spannedBitmap.mId == spannedData.getId()) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    Bitmap drawBitmap = spannedBitmap == null ? null : spannedBitmap.mBitmap;
                    RectF drawRectF3 = spannedData.getDrawRectF(screenRenderProvider);
                    if (drawBitmap == null) {
                        drawBitmap = SpannedUtils.getDrawBitmap(spannedData.mBitmapPath, drawRectF3.width());
                    }
                    if (drawBitmap != null) {
                        if (spannedBitmap != null) {
                            spannedBitmap.mBitmap = drawBitmap;
                        }
                        canvas.save();
                        if (spannedData.mDegrees != 0.0f) {
                            canvas.rotate(spannedData.mDegrees, drawRectF3.centerX(), drawRectF3.centerY());
                        }
                        canvas.drawBitmap(drawBitmap, new Rect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), drawRectF3, selectedShortUtil.dPaint);
                        canvas.restore();
                    }
                }
            }
        }
        canvas.restore();
    }

    private List<SpannedBitmap> getSpannedBitmap(int[] iArr, SpannedLayoutModel spannedLayoutModel) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            SpannedBitmap spannedBitmap = new SpannedBitmap(i);
            int childCount = spannedLayoutModel.mSpannedEditLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = spannedLayoutModel.mSpannedEditLayout.getChildAt(i2);
                if ((childAt instanceof CsImageView) && ((SpannedData) childAt.getTag()).mId == spannedBitmap.mId) {
                    SpannedImageManager.LoadRunnable loadRunnable = ((CsImageView) childAt).getImageViewModel().mLoadRunable;
                    Bitmap bitmap = null;
                    if (loadRunnable != null && loadRunnable.isNeedRelease && loadRunnable.mBitmap != null) {
                        Bitmap bitmap2 = loadRunnable.mBitmap;
                        if (!bitmap2.isRecycled()) {
                            bitmap = bitmap2;
                        }
                    }
                    spannedBitmap.mBitmap = bitmap;
                }
            }
            arrayList.add(spannedBitmap);
        }
        return arrayList;
    }

    private void unTranslateAndScale(float f, float f2) {
        this.mCenterPointF.set(f, f2);
        this.mPointF.set(f, f2);
        ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
        if (scaleParams.mTranslatePointF.x == 0.0f && scaleParams.mTranslatePointF.y == 0.0f && scaleParams.mScaleReal == 1.0f) {
            return;
        }
        SpannedUtils.unTranslateAndScalePointF(this.mPointF, scaleParams.mTranslatePointF.x, scaleParams.mTranslatePointF.y, scaleParams.mScaleReal);
    }

    private static class SpannedBitmap {
        public Bitmap mBitmap;
        public int mId;

        public SpannedBitmap(int i) {
            this.mId = i;
        }
    }
}
