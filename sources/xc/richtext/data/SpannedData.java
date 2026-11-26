package xc.richtext.data;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.utils.ScreenRenderUtil;
import xc.richtext.utils.SpannedUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedData {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    public int h;
    public int mBgColor;
    public String mBitmapPath;
    public RectF mCropRectF;
    public float mDegrees;
    public String mEntPath;
    public int mId;
    public Point mImagesizepoint;
    public PointF mPointF;
    public float mRatio;
    public RectF mRatioRectF;
    private RectF mRectF;
    public float mScale;
    public float mScaleTextSize;
    public int mSpannedStatus;
    public String mText;
    public int mTextColor;
    public float mTextSize;
    public String mTitle;
    public PointF mTranslatePointF;
    public int mType;
    public boolean o;
    private int pageIndex;
    public CountDownLatch saveRectChangeLatch;
    private ScreenRenderProvider screenRenderProvider;

    public SpannedData() {
        this.mType = 0;
        this.mTextSize = 34.0f;
        this.mTextColor = -16777216;
        this.mTitle = "";
        this.mText = "";
        this.h = 0;
        this.mBgColor = 0;
        this.mSpannedStatus = 0;
        this.mRatio = 1.0f;
        this.mRatioRectF = new RectF();
        this.mScaleTextSize = 34.0f;
        this.mPointF = new PointF();
        this.mTranslatePointF = new PointF();
        this.mScale = 1.0f;
        this.mImagesizepoint = new Point();
        this.mId = generateId();
        this.mCropRectF = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public SpannedData(int i, RectF rectF, String str, ScreenRenderProvider screenRenderProvider) {
        this.mType = 0;
        this.mTextSize = 34.0f;
        this.mTextColor = -16777216;
        this.mTitle = "";
        this.mText = "";
        this.h = 0;
        this.mBgColor = 0;
        this.mSpannedStatus = 0;
        this.mRatio = 1.0f;
        this.mRatioRectF = new RectF();
        this.mScaleTextSize = 34.0f;
        this.mPointF = new PointF();
        this.mTranslatePointF = new PointF();
        this.mScale = 1.0f;
        this.mImagesizepoint = new Point();
        this.mId = generateId();
        this.mType = i;
        this.mRectF = rectF;
        this.mText = str;
        this.mCropRectF = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public SpannedData(RectF rectF, String str, float f, ScreenRenderProvider screenRenderProvider) {
        this.mType = 0;
        this.mTextSize = 34.0f;
        this.mTextColor = -16777216;
        this.mTitle = "";
        this.mText = "";
        this.h = 0;
        this.mBgColor = 0;
        this.mSpannedStatus = 0;
        this.mRatio = 1.0f;
        this.mRatioRectF = new RectF();
        this.mScaleTextSize = 34.0f;
        this.mPointF = new PointF();
        this.mTranslatePointF = new PointF();
        this.mScale = 1.0f;
        this.mImagesizepoint = new Point();
        this.mId = generateId();
        this.mType = 7;
        this.mRectF = rectF;
        this.mBitmapPath = str;
        this.mDegrees = f;
        this.mCropRectF = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public int getType() {
        return this.mType;
    }

    public int getId() {
        return this.mId;
    }

    public RectF getRectF() {
        return new RectF(this.mRectF);
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public float getDegrees() {
        return this.mDegrees;
    }

    public String getText() {
        return this.mText;
    }

    public String getBitmapPath() {
        return this.mBitmapPath;
    }

    public String getEntPath() {
        return this.mEntPath;
    }

    public boolean isVisible() {
        int i = this.mSpannedStatus;
        return (i == 3 || i == 1) ? false : true;
    }

    public RectF getRatioRectF() {
        return this.mRatioRectF;
    }

    public float getRealTextSize() {
        float f = this.mTextSize * this.mRatio;
        this.mScaleTextSize = f;
        return f * this.mScale;
    }

    public void setRectF(RectF rectF) {
        this.mRectF.set(rectF.left / this.mRatio, rectF.top / this.mRatio, rectF.right / this.mRatio, rectF.bottom / this.mRatio);
    }

    public void setSaveRect(RectF rectF) {
        RectF rectF2 = this.mRectF;
        if (rectF2 == null) {
            this.mRectF = rectF;
        } else {
            rectF2.set(rectF);
        }
    }

    public void setSaveRect(float f, float f2, float f3, float f4) {
        RectF rectF = this.mRectF;
        if (rectF == null) {
            this.mRectF = new RectF(f, f2, f3, f4);
        } else {
            rectF.set(f, f2, f3, f4);
        }
    }

    public RectF getSpanDataRectF() {
        return new RectF(this.mRectF);
    }

    public void setCropRectF(RectF rectF) {
        this.mCropRectF = new RectF(rectF);
    }

    public RectF getSpanDataCropRectF() {
        return new RectF(this.mCropRectF);
    }

    public void setRatioData(float f) {
        this.mRatio = f;
        this.mRatioRectF.set(this.mRectF.left * f, this.mRectF.top * f, this.mRectF.right * f, this.mRectF.bottom * f);
        this.mScaleTextSize = this.mTextSize * f;
    }

    public void resetPoint() {
        this.mPointF.set(0.0f, 0.0f);
    }

    public SpannedData copy() {
        SpannedData spannedData = new SpannedData();
        spannedData.mType = this.mType;
        spannedData.mRectF = new RectF(this.mRectF);
        if (this.mType == 11) {
            spannedData.mTextSize = this.mTextSize;
            spannedData.mTextColor = this.mTextColor;
            spannedData.mText = this.mText;
            spannedData.mBgColor = this.mBgColor;
        } else {
            spannedData.mBitmapPath = this.mBitmapPath;
            spannedData.mEntPath = this.mEntPath;
            spannedData.mCropRectF = new RectF(this.mCropRectF);
        }
        spannedData.mDegrees = this.mDegrees;
        spannedData.mSpannedStatus = this.mSpannedStatus;
        return spannedData;
    }

    public RectF getDrawRectFGL(ScreenRenderProvider screenRenderProvider) {
        if (screenRenderProvider == null) {
            return new RectF();
        }
        updateMRatio(screenRenderProvider.getDrawRatio());
        if (Float.compare(this.mRatio, 1.0f) == 0 && screenRenderProvider.isPenEngineReleased()) {
            return new RectF();
        }
        return screenRenderProvider.covertWSRectToSSRect(scaleRectByRatio(this.mRectF));
    }

    public RectF getDrawRectFGL(MiCurveEngine miCurveEngine) {
        if (miCurveEngine == null) {
            return new RectF();
        }
        updateMRatio(miCurveEngine.getDrawRatio());
        if (Float.compare(this.mRatio, 1.0f) == 0 && miCurveEngine.getPenEngine().isRelease()) {
            return new RectF();
        }
        return miCurveEngine.getScreenSpaceRectFromWorldSpaceRect(scaleRectByRatio(this.mRectF));
    }

    private void updateMRatio(float f) {
        if (Float.compare(f, this.mRatio) != 0) {
            this.mRatio = f;
        }
    }

    private RectF scaleRectByRatio(RectF rectF) {
        return new RectF(rectF.left * this.mRatio, rectF.top * this.mRatio, rectF.right * this.mRatio, rectF.bottom * this.mRatio);
    }

    public RectF getOriginDrawRectF(ScreenRenderProvider screenRenderProvider) {
        return translateAndScaleRectF2(getDrawRectFGL(screenRenderProvider));
    }

    public RectF getOriginDrawRectF(MiCurveEngine miCurveEngine) {
        return translateAndScaleRectF2(getDrawRectFGL(miCurveEngine));
    }

    public RectF getDrawRectF(ScreenRenderProvider screenRenderProvider) {
        return translateAndScaleRectF(getDrawRectFGL(screenRenderProvider));
    }

    public RectF getDrawRectF(MiCurveEngine miCurveEngine) {
        return translateAndScaleRectF(getDrawRectFGL(miCurveEngine));
    }

    public RectF translateAndScaleRectF(RectF rectF) {
        if (rectF.width() != 0.0f && rectF.height() != 0.0f) {
            return isHadCrop() ? getCropDrawRectF(rectF) : rectF;
        }
        RectF rectF2 = new RectF(this.mRatioRectF);
        if (isHadCrop()) {
            rectF2 = getCropDrawRectF(rectF2);
        }
        return applyTranslationAndScale(rectF2);
    }

    public RectF translateAndScaleRectF2(RectF rectF) {
        return (rectF.width() == 0.0f || rectF.height() == 0.0f) ? applyTranslationAndScale(new RectF(this.mRatioRectF)) : rectF;
    }

    private RectF applyTranslationAndScale(RectF rectF) {
        PointF pointF = this.mTranslatePointF;
        return (pointF == null || (pointF.x == 0.0f && pointF.y == 0.0f && this.mScale == 1.0f)) ? rectF : SpannedUtils.translateAndScaleRectF(rectF, pointF.x, pointF.y, this.mScale);
    }

    public RectF getRotatedRectF() {
        RectF rectF = this.mRectF;
        return SpannedUtils.getRotatedBoundingBox(rectF, this.mDegrees, rectF.centerX(), this.mRectF.centerY());
    }

    public RectF getRotatedBoundingBox(ScreenRenderProvider screenRenderProvider) {
        RectF drawRectF = getDrawRectF(screenRenderProvider);
        RectF originDrawRectF = getOriginDrawRectF(screenRenderProvider);
        return SpannedUtils.getRotatedBoundingBox(drawRectF, this.mDegrees, originDrawRectF.centerX(), originDrawRectF.centerY());
    }

    public RectF getRotatedBoundingBox() {
        RectF drawRectF = getDrawRectF(ScreenRenderUtil.getProvider());
        RectF originDrawRectF = getOriginDrawRectF(ScreenRenderUtil.getProvider());
        return SpannedUtils.getRotatedBoundingBox(drawRectF, this.mDegrees, originDrawRectF.centerX(), originDrawRectF.centerY());
    }

    public RectF getRotatedCropBoundingBox(RectF rectF, ScreenRenderProvider screenRenderProvider) {
        RectF originDrawRectF = getOriginDrawRectF(screenRenderProvider);
        return SpannedUtils.getRotatedBoundingBox(rectF, this.mDegrees, originDrawRectF.centerX(), originDrawRectF.centerY());
    }

    public boolean isHadCrop() {
        return (this.mCropRectF.left == 0.0f && this.mCropRectF.top == 0.0f && this.mCropRectF.right == 1.0f && this.mCropRectF.bottom == 1.0f) ? false : true;
    }

    private RectF getCropDrawRectF(RectF rectF) {
        return new RectF(rectF.left + (rectF.width() * this.mCropRectF.left), rectF.top + (rectF.height() * this.mCropRectF.top), rectF.left + (rectF.width() * this.mCropRectF.right), rectF.top + (rectF.height() * this.mCropRectF.bottom));
    }

    private int generateId() {
        return idCounter.incrementAndGet();
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int i) {
        this.pageIndex = i;
    }
}
