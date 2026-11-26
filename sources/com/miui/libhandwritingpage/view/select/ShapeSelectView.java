package com.miui.libhandwritingpage.view.select;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import java.util.ArrayList;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.TouchEventUtils;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeSelectView extends BaseSelectView {
    private final String TAG;
    private List<RectF> mAcmeList;
    protected boolean mIsSelect;
    private boolean mIsShowEditPoint;
    private PointF mOffPointF;
    private RectF mOperatingRect;
    private int mShapeEditType;
    private ShapeProp mShapeProp;
    private Path mTouchPath;
    private float[] mTouchPoint;

    public ShapeSelectView(Context context) {
        super(context);
        this.TAG = "ShapeSelectView";
        this.mIsSelect = false;
        this.mShapeSelectRectF = new RectF();
        this.mAcmeList = new ArrayList();
        this.mOperatingRect = new RectF();
        this.mIsShowEditPoint = false;
        this.mOffPointF = new PointF();
        this.mTouchPath = new Path();
        this.mShapeEditType = 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Path path;
        super.onDraw(canvas);
        updatePoint();
        if (this.mTouchPoint != null && (path = this.mTouchPath) != null && !path.isEmpty() && this.mIsSelect) {
            this.mSelectTouchPath = null;
            this.mIsShowEditPoint = this.mPageItemView.isShowEditPoint();
            if (!isSelectEnableFollow() || this.mSelectListener == null) {
                return;
            }
            this.mSelectListener.drawShapeAntLine(canvas, this.mTouchPath, this.mTouchPoint, this.mIsShowEditPoint, this.mPageItemView.isCloseShape());
            return;
        }
        if (isNeedOperateAntLine() && this.mSelectTouchPath != null && isSelectEnableFollow()) {
            drawFollowAntLine(canvas);
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onUp(MotionEvent motionEvent) {
        if (this.mCurSelectAction == 0 && !isSelectRectNotEmpty()) {
            this.mPageItemView.setSearchRectList(true, 0);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            pointF.offset(-this.mLayoutOffsetX, -this.mLayoutOffsetY);
            transformAndScalePointF(pointF);
            this.mTouchPoints.add(new TouchPosition(pointF.x, pointF.y, motionEvent.getEventTime()));
        } else if (this.mCurSelectAction == 1) {
            if (this.mPageItemView != null) {
                this.mPageItemView.cancelSelect(false);
            }
            this.mShapeSelectRectF.setEmpty();
        } else if ((this.mCurSelectAction == 4 || this.mCurSelectAction == 8 || this.mCurSelectAction == 16 || this.mCurSelectAction == 28) && TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent) && this.mSelectListener != null) {
            this.mShowSelectPopWindow = true;
            this.mSelectListener.onSelectPopWindow(this.mShapeSelectRectF, true, this.isSingleBitmap, this.mIsSingleText);
        }
        this.mPageInkViewModel.checkSelectViewPosition(this.mTouchPoint);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void setAction(int i) {
        this.mCurSelectAction = i;
        if (this.mPageItemView != null) {
            this.mPageItemView.setSelectType(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public int findSelectTypeBy(float f, float f2) {
        float[] fArr;
        char c;
        int i;
        int i2;
        char c2;
        char c3 = 0;
        this.mShapeEditType = 0;
        PointF pointF = new PointF();
        PointF pointF2 = new PointF();
        float[] fArr2 = this.mTouchPoint;
        if (fArr2 != null) {
            fArr = new float[fArr2.length];
            int i3 = 0;
            while (true) {
                float[] fArr3 = this.mTouchPoint;
                if (i3 >= fArr3.length) {
                    break;
                }
                if (i3 % 2 == 0) {
                    fArr[i3] = fArr3[i3] - this.mLayoutOffsetX;
                } else {
                    fArr[i3] = fArr3[i3] - this.mLayoutOffsetY;
                }
                i3++;
            }
        } else {
            fArr = null;
        }
        if (this.mAcmeList.isEmpty() || fArr == null) {
            c = 0;
            i = 1;
        } else {
            int i4 = 0;
            i = 1;
            while (i4 < this.mAcmeList.size()) {
                RectF rectF = new RectF(this.mAcmeList.get(i4));
                if (rectF.contains(f, f2)) {
                    float[] fArr4 = this.mTouchPoint;
                    if (rectF.contains(fArr4[c3], fArr4[1])) {
                        this.mShapeEditType = 1;
                        pointF.x = fArr[c3];
                        pointF.y = fArr[1];
                        pointF2.x = fArr[4];
                        pointF2.y = fArr[5];
                        c2 = c3;
                    } else {
                        float[] fArr5 = this.mTouchPoint;
                        c2 = c3;
                        if (rectF.contains(fArr5[2], fArr5[3])) {
                            this.mShapeEditType = 3;
                            pointF.x = fArr[2];
                            pointF.y = fArr[3];
                            pointF2.x = fArr[6];
                            pointF2.y = fArr[7];
                        } else {
                            float[] fArr6 = this.mTouchPoint;
                            if (rectF.contains(fArr6[4], fArr6[5])) {
                                this.mShapeEditType = 4;
                                pointF.x = fArr[4];
                                pointF.y = fArr[5];
                                pointF2.x = fArr[c2];
                                pointF2.y = fArr[1];
                            } else {
                                float[] fArr7 = this.mTouchPoint;
                                if (rectF.contains(fArr7[6], fArr7[7])) {
                                    this.mShapeEditType = 2;
                                    pointF.x = fArr[6];
                                    pointF.y = fArr[7];
                                    pointF2.x = fArr[2];
                                    pointF2.y = fArr[3];
                                } else {
                                    float[] fArr8 = this.mTouchPoint;
                                    if (rectF.contains((fArr8[c2] + fArr8[2]) / 2.0f, (fArr8[1] + fArr8[3]) / 2.0f)) {
                                        this.mShapeEditType = 6;
                                        pointF.x = (fArr[c2] + fArr[2]) / 2.0f;
                                        pointF.y = (fArr[1] + fArr[3]) / 2.0f;
                                        pointF2.x = (fArr[4] + fArr[6]) / 2.0f;
                                        pointF2.y = (fArr[5] + fArr[7]) / 2.0f;
                                    } else {
                                        float[] fArr9 = this.mTouchPoint;
                                        if (rectF.contains((fArr9[2] + fArr9[4]) / 2.0f, (fArr9[3] + fArr9[5]) / 2.0f)) {
                                            this.mShapeEditType = 7;
                                            pointF.x = (fArr[2] + fArr[4]) / 2.0f;
                                            pointF.y = (fArr[3] + fArr[5]) / 2.0f;
                                            pointF2.x = (fArr[c2] + fArr[6]) / 2.0f;
                                            pointF2.y = (fArr[1] + fArr[7]) / 2.0f;
                                        } else {
                                            float[] fArr10 = this.mTouchPoint;
                                            if (rectF.contains((fArr10[4] + fArr10[6]) / 2.0f, (fArr10[5] + fArr10[7]) / 2.0f)) {
                                                this.mShapeEditType = 8;
                                                pointF.x = (fArr[4] + fArr[6]) / 2.0f;
                                                pointF.y = (fArr[5] + fArr[7]) / 2.0f;
                                                pointF2.x = (fArr[c2] + fArr[2]) / 2.0f;
                                                pointF2.y = (fArr[1] + fArr[3]) / 2.0f;
                                            } else {
                                                float[] fArr11 = this.mTouchPoint;
                                                if (rectF.contains((fArr11[6] + fArr11[c2]) / 2.0f, (fArr11[7] + fArr11[1]) / 2.0f)) {
                                                    this.mShapeEditType = 5;
                                                    pointF.x = (fArr[6] + fArr[c2]) / 2.0f;
                                                    pointF.y = (fArr[7] + fArr[1]) / 2.0f;
                                                    pointF2.x = (fArr[2] + fArr[4]) / 2.0f;
                                                    pointF2.y = (fArr[3] + fArr[5]) / 2.0f;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i = 132;
                } else {
                    c2 = c3;
                }
                i4++;
                c3 = c2;
            }
            c = c3;
        }
        RectF rectF2 = this.mOperatingRect;
        if (rectF2 == null || rectF2.isEmpty() || fArr == null || !new RectF(this.mOperatingRect).contains(f, f2)) {
            i2 = i;
        } else {
            this.mShapeEditType = 9;
            pointF.x = fArr[10];
            pointF.y = fArr[11];
            pointF2.x = fArr[12];
            pointF2.y = fArr[13];
            i2 = 132;
        }
        if (this.mPageItemView != null) {
            this.mPageItemView.setShapeEditType(this.mShapeEditType, pointF, pointF2);
        }
        if (i2 == 1 && this.mTouchPath != null) {
            Region region = new Region();
            RectF rectF3 = new RectF();
            this.mTouchPath.computeBounds(rectF3, c);
            region.setPath(this.mTouchPath, new Region((int) rectF3.left, (int) rectF3.top, (int) rectF3.right, (int) rectF3.bottom));
            if (region.contains((int) f, (int) f2)) {
                return 4;
            }
        }
        return i2;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean isSelectRectNotEmpty() {
        return !this.mShapeSelectRectF.isEmpty();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onPaste(float f, float f2, byte[] bArr, List<SpannedData> list, boolean z) {
        if (bArr != null) {
            this.mPageItemView.setPaste(bArr, f + (this.mLayoutRectF.left > 0.0f ? -this.mLayoutRectF.left : 0.0f), f2 + (this.mLayoutRectF.top > 0.0f ? -this.mLayoutRectF.top : 0.0f));
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onDelete(boolean z) {
        onDelete(z, false);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onDelete(boolean z, boolean z2) {
        setAction(67);
        this.mPageItemView.doDelete(z, z2);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCopy(boolean z) {
        setAction(66);
        this.mPageItemView.doCopy(z);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCopyPaste(boolean z) {
        setAction(64);
        this.mPageItemView.doCopyAndPaste(z);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCut(boolean z) {
        setAction(65);
        this.mPageItemView.doCopy(z);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list) {
        onObjectSelected(sRectF, list, false);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list, boolean z) {
        boolean z2 = true;
        this.isReady = true;
        if (this.isAloneDelete == 2) {
            this.isAloneDelete = 0;
            onDelete(true, z);
            return;
        }
        this.mShapeSelectRectF.set(sRectF);
        if (this.mSelectListener != null) {
            RectF rectFScaleAndTranslateRectF = scaleAndTranslateRectF(this.mShapeSelectRectF);
            ArrayList arrayList = new ArrayList(this.mTouchPoints);
            if (this.mPageItemView.getCreationEngine().getSelectOperator().isPointSelected()) {
                arrayList.clear();
                if (this.mTouchPoints.size() > 0) {
                    arrayList.add(new TouchPosition(this.mTouchPoints.get(0).x, this.mTouchPoints.get(0).y, this.mTouchPoints.get(0).time));
                } else if (!rectFScaleAndTranslateRectF.isEmpty()) {
                    arrayList.add(new TouchPosition(rectFScaleAndTranslateRectF.centerX(), rectFScaleAndTranslateRectF.centerY(), 0L));
                }
            }
            this.mSelectListener.onSelectSaveRectF(rectFScaleAndTranslateRectF, arrayList, this.mPageItemView.getIndex(), list, this.mOriginTouchPoint);
            this.mOriginTouchPoint.clear();
            this.mTouchPoints.clear();
        }
        if (sRectF.isEmpty()) {
            cancelSelect();
        } else {
            ShapeProp shapeData = this.mPageItemView.getShapeData();
            this.mShapeProp = shapeData;
            if (shapeData != null && (shapeData.getShapeType() == 2 || this.mShapeProp.getShapeType() == 1 || this.mShapeProp.getShapeType() == 5)) {
                z2 = false;
            }
            this.mIsShowEditPoint = z2;
            this.mShapeSelectRectF.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
            this.mOffPointF.set(0.0f, 0.0f);
        }
        postInvalidate();
        this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selected();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void moveSelectPath(float f, float f2, Path path) {
        if (f != 0.0f || f2 != 0.0f) {
            if (this.mTouchPoint != null) {
                int i = 0;
                while (true) {
                    float[] fArr = this.mTouchPoint;
                    if (i >= fArr.length) {
                        break;
                    }
                    fArr[i] = fArr[i] + (i % 2 == 0 ? f : f2);
                    i++;
                }
            }
            Path path2 = this.mTouchPath;
            if (path2 != null) {
                path2.offset(f, f2);
            }
            this.mOffPointF.set(f, f2);
        }
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onDoingAction(int i, SRectF sRectF) {
        if (i != 4) {
            this.mOffPointF.set(0.0f, 0.0f);
            postInvalidate();
        }
    }

    private void updatePoint() {
        if (this.mOffPointF.x == 0.0f && this.mOffPointF.y == 0.0f) {
            float[] editPosition = this.mPageItemView.getEditPosition();
            this.mTouchPoint = editPosition;
            if (editPosition != null) {
                int i = 0;
                while (true) {
                    float[] fArr = this.mTouchPoint;
                    if (i >= fArr.length) {
                        break;
                    }
                    fArr[i] = fArr[i] + (i % 2 == 0 ? this.mLayoutOffsetX : this.mLayoutOffsetY);
                    i++;
                }
            }
            Path selectPath = this.mPageItemView.getSelectPath();
            this.mTouchPath = selectPath;
            if (selectPath != null) {
                selectPath.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
            }
        }
    }

    public void setShapeMenuSize(List<RectF> list, RectF rectF) {
        this.mAcmeList = list;
        this.mOperatingRect = rectF;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public RectF getRectF() {
        return this.mShapeSelectRectF;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void cancelSelect() {
        reset();
        this.mIsSelect = false;
        this.mIsEdit = true;
        this.mTouchPoint = null;
        Path path = this.mTouchPath;
        if (path != null) {
            path.reset();
        }
        this.mShapeSelectRectF.setEmpty();
        this.mOffPointF.set(0.0f, 0.0f);
        this.mShowSelectPopWindow = false;
        if (this.mSelectListener != null) {
            this.mSelectListener.dismissSelectPopWindow();
        }
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void finishSelect() {
        this.mShowSelectPopWindow = false;
        postInvalidate();
        if (this.mSelectListener != null) {
            this.mSelectListener.dismissSelectPopWindow();
        }
    }

    public ShapeProp getShapeData() {
        if (this.mPageItemView == null) {
            return null;
        }
        return this.mPageItemView.getShapeData();
    }

    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        if (this.mPageItemView != null) {
            this.mPageItemView.setSelectShapeAttr(shapeEditAttr, z);
        }
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public int getType() {
        return isSelectRectNotEmpty() ? 0 : -1;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public Bitmap getBitmap(boolean z) {
        if (this.mPageItemView == null) {
            return null;
        }
        return this.mPageItemView.getBitmap();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public Bitmap getBitmap(boolean z, boolean z2) {
        return getBitmap(z);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void replaceView(List<TouchPosition> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (!this.mShapeSelectRectF.isEmpty()) {
            this.isAloneDelete = 0;
            onDelete(true, true);
            return;
        }
        this.isAloneDelete = 2;
        this.mListPoint.clear();
        for (int i = 0; i < list.size(); i++) {
            PointF pointF = new PointF(list.get(i).x, list.get(i).y);
            toDrawPointF(pointF);
            this.mListPoint.add(new TouchPoint(pointF.x, pointF.y, list.get(i).time));
        }
        if (this.mPageItemView != null) {
            this.mPageItemView.getCreationEngine().getSelectOperator().setExtraSelectPoint(this.mListPoint, true, true);
        }
    }

    public boolean isOnAction() {
        int i = this.mCurSelectAction;
        return i == 4 || i == 132 || this.mShapeEditType != 0;
    }

    public void setIsSelect(boolean z) {
        this.mIsSelect = z;
    }
}
