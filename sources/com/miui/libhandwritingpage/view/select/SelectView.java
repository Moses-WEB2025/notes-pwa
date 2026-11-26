package com.miui.libhandwritingpage.view.select;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import android.view.MotionEvent;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.PageStepHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.manager.MathDataManager;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.edit.select.SelectCopyData;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.TouchEventUtils;
import xc.richtext.api.SpannedSelectInfo;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class SelectView extends BaseSelectView {
    private static final String AI_TAG = "Ai_Tag";
    public static final int ANT_LINE_EDIT = 1;
    public static final int ANT_LINE_MIX_RECTANGULAR = 3;
    public static final int ANT_LINE_NORMAL = 4;
    public static final int ANT_LINE_RECTANGULAR = 2;
    private static final float CROP_AREA_MIN_PIXELS = 16.0f;
    private static final int CROP_AREA_MIN_SIZE = 100;
    public static final float DUPLICATE_OFFSET = 50.0f;
    private static final int RESIZE_EDGE_BOTTOM = 4096;
    private static final int RESIZE_EDGE_LEFT = 1;
    private static final int RESIZE_EDGE_RIGHT = 256;
    private static final int RESIZE_EDGE_TOP = 16;
    private static final int SELECT_CURVE = 0;
    private static final int SELECT_MIX = 3;
    private static final int SELECT_TEXT = 1;
    private static final String TAG = "SelectView";
    private PointF mCopyCurOffset;
    private List<SpannedData> mCopySpannedDataList;
    private PointF mCopySpannedOffset;
    private final RectF mCropArea;
    private final RectF mCropRate;
    private RectF mCurLastRectF;
    private RectF mCurSelectedRectF;
    private final PointF mOffset;
    private Path mOutPath;
    private PointF mPointF;
    private float mResizeEdgeSlop;
    private float[] mSelData;
    private int mSelectCount;
    private PointF mSelectPoint;
    private int mSelectType;
    private int mSelectedHashCode;
    private String mSelectedUuid;
    private SpannedData mSpannedData;
    private RectF mSpannedDrawnRectF;
    private PageStepHelper mStepHelper;
    private RectF mUnionRectF;
    private int selectOffState;

    public SelectView(Context context) {
        super(context);
        this.mCropArea = new RectF();
        this.mOffset = new PointF();
        this.mCropRate = new RectF();
        this.selectOffState = 0;
        this.mCurLastRectF = new RectF();
        this.mCurSelectedRectF = new RectF();
        this.mSpannedSelectedRectF = new RectF();
        this.mCopyCurOffset = new PointF();
        this.mCopySpannedOffset = new PointF();
        this.mSelectCount = 0;
        this.mScaleList = new ArrayList();
        this.mRotateList = new ArrayList();
        this.mMultiSelectRectF = new SRectF();
        this.mSelectType = 0;
        this.mOutPath = new Path();
        this.mSelData = new float[10];
        this.mSelectPoint = new PointF();
        this.mPointF = new PointF();
        this.mUnionRectF = new RectF();
        this.mSelectedUuid = null;
        initCropView();
    }

    private void initCropView() {
        this.mResizeEdgeSlop = getResources().getDimension(R.dimen.creation_editor_crop_menu_bound_touch_width);
        this.mCropGestureListener = new GesListener();
    }

    MiCurveEngine getCurveEngine() {
        if (this.mPageItemView == null) {
            return null;
        }
        return this.mPageItemView.getCurveEngine();
    }

    private boolean shouldDrawSelectPath() {
        MathDataManager mathDataManager = this.mPageItemView.getCreationEngine().getCurveEngine().getMathDataManager();
        Log.d(TAG, "shouldDrawSelectPath: isMathSelect:" + mathDataManager.isMathSelect());
        int i = this.mSelectType;
        return (i == 1 || i == 3 || i == 0 || i == 2 || (i == 4 && this.mPageItemView.isSelectedObjectValid() && !this.mPageItemView.isSelectedContour())) && !mathDataManager.isMathSelect();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        float f2;
        super.onDraw(canvas);
        if (this.mPageItemView != null) {
            canvas.save();
            canvas.clipRect(this.mLayoutRectF);
            canvas.translate(this.mLayoutOffsetX, this.mLayoutOffsetY);
            canvas.restore();
        }
        if (isSelectRectNotEmpty()) {
            if (this.enableSelectedContour && this.mSpannedSelectedRectF.isEmpty() && this.mPageItemView.isSelectedContour() && !this.mIsEdit) {
                if (this.mCurSelectedOutPath != null) {
                    this.mSelectTouchPath = null;
                    this.mOutPath.set(this.mCurSelectedOutPath);
                    this.mOutPath.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
                    canvas.save();
                    canvas.clipRect(this.mLayoutRectF);
                    isSelectEnableFollow();
                    canvas.restore();
                } else if (this.mSelectTouchPath != null) {
                    drawFollowAntLine(canvas);
                }
            } else if (isNeedOperateAntLine()) {
                this.mSelectTouchPath = null;
                Path spanPath = getSpanPath();
                if (spanPath != null && !spanPath.isEmpty()) {
                    float[] spanData = getSpanData();
                    int i = 0;
                    while (true) {
                        float[] fArr = this.mSelData;
                        if (i >= fArr.length) {
                            break;
                        }
                        if (i % 2 == 0) {
                            f = spanData[i];
                            f2 = this.mLayoutOffsetX;
                        } else {
                            f = spanData[i];
                            f2 = this.mLayoutOffsetY;
                        }
                        fArr[i] = f + f2;
                        i++;
                    }
                    canvas.save();
                    canvas.clipRect(this.mLayoutRectF);
                    if (isSelectEnableFollow()) {
                        Log.d(TAG, "onDraw: 蚂蚁线类型:" + this.mSelectType);
                        if (shouldDrawSelectPath()) {
                            moveSelectPath(0.0f, 0.0f, spanPath);
                            this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getOutlineDrawer().setPath(spanPath, 0.0f, 0.0f, true);
                        }
                        if (this.mSpannedMode != null) {
                            this.mSpannedMode.setMixRectangular(this.mSelectType == 3);
                        }
                    }
                    canvas.restore();
                }
            }
            boolean z = this.mPageInkViewModel != null && this.mPageInkViewModel.isAiModel();
            Log.d(TAG, "onDraw: isAiMode" + z);
            drawRotateAndScaleButton(canvas, getType(), this.mSelData, getSpanPath(), this.mIsShowButton && !z);
            return;
        }
        if (isNeedOperateAntLine() && this.mSelectTouchPath != null && isSelectEnableFollow()) {
            drawFollowAntLine(canvas);
        }
    }

    public boolean isItemSelected() {
        return (this.mPageItemView == null || this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getSelectMode() == SelectSystem.SelectMode.None) ? false : true;
    }

    public boolean isDownButton(MotionEvent motionEvent) {
        if (this.isDownSelectRect == null) {
            return false;
        }
        checkDown(motionEvent);
        return this.isDownSelectRect.booleanValue();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onUp(MotionEvent motionEvent) {
        Log.d(TAG, "onUp: " + this.mCurSelectAction);
        if (this.mCurSelectAction == 0) {
            if (isSelectRectNotEmpty()) {
                return;
            }
            this.isReady = false;
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            pointF.offset(-this.mLayoutOffsetX, -this.mLayoutOffsetY);
            transformAndScalePointF(pointF);
            this.mTouchPoints.add(new TouchPosition(pointF.x, pointF.y, motionEvent.getEventTime()));
            this.mPageItemView.setSearchRectList(true, 0);
            return;
        }
        if (this.mCurSelectAction == 1) {
            if (this.mPageItemView != null) {
                this.mPageItemView.cancelSelect(false);
            }
            if (this.mSpannedMode != null) {
                this.mSpannedMode.resetSelect();
                this.mIsEdit = true;
                this.mMultiSelectRectF.setEmpty();
                return;
            }
            return;
        }
        if (this.mCurSelectAction == 4 || this.mCurSelectAction == 8 || this.mCurSelectAction == 16 || this.mCurSelectAction == 28) {
            if (!this.mCurSelectedRectF.isEmpty() && !this.mSpannedSelectedRectF.isEmpty()) {
                onStepCount(2);
            }
            if (this.mCurSelectedRectF.isEmpty() && !this.mSpannedSelectedRectF.isEmpty() && this.mCurSelectAction != 35) {
                setAction(0);
            }
            if (TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent) && this.mSelectListener != null) {
                this.mShowSelectPopWindow = true;
                this.mSelectListener.onSelectPopWindow(this.mMultiSelectRectF, false, this.isSingleBitmap, this.mIsSingleText);
            }
            if (this.mPageInkViewModel != null) {
                this.mPageInkViewModel.checkSelectViewPosition(getSpanData());
            }
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onPaste(float f, float f2, byte[] bArr, List<SpannedData> list, boolean z) {
        boolean z2;
        if (bArr == null && list == null) {
            return;
        }
        if (bArr != null && list != null) {
            onSelectCount(2, "");
            onStepCount(2);
        } else {
            onSelectCount(1, "");
        }
        this.mUnionRectF.setEmpty();
        Log.i(TAG, String.format("onPaste: insert center[%f, %f]", Float.valueOf(f), Float.valueOf(f2)));
        float scale = this.mPageInkViewModel.getScale() * this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        if (list == null || this.mSpannedMode == null) {
            z2 = false;
        } else {
            this.mSpannedMode.doPast(list, (this.mCopySpannedOffset.x * scale) + f, (this.mCopySpannedOffset.y * scale) + f2);
            z2 = true;
        }
        if (bArr != null) {
            this.mPageItemView.setPaste(bArr, f + (this.mCopyCurOffset.x * scale), f2 + (this.mCopyCurOffset.y * scale));
            z2 = true;
        }
        if (z2) {
            int i = this.mSelectType;
            if (i == 1 || i == 2) {
                this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selected();
            }
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean isPastLimit(float f, float f2, byte[] bArr, List<SpannedData> list) {
        boolean zIsPointLimit;
        boolean z = hasCopySpannedBitmap(list) && this.mPageItemView != null && this.mPageItemView.isBitmapSizeMax(list);
        boolean z2 = list != null && list.size() > 0 && this.mPageItemView != null && this.mPageItemView.isTextSizeMax(list);
        if (bArr == null || this.mPageItemView == null) {
            zIsPointLimit = false;
        } else {
            try {
                int size = 0;
                int pageIndexByScreenSpacePoint = 0;
                for (BaseData baseData : (ArrayList) SelectCopyData.readDataFromBytes(bArr, (PenEngine) this.mPageItemView.getCreationEngine()).getDataList()) {
                    if (baseData.getType() == 1 || baseData.getType() == 28) {
                        DataCurve dataCurve = (DataCurve) baseData;
                        size += dataCurve.size(dataCurve.mSavePoints);
                        pageIndexByScreenSpacePoint = this.mPageItemView.getCreationEngine().getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(f, f2));
                    }
                }
                zIsPointLimit = this.mPageItemView.getCreationEngine().getEditOperator().isPointLimit(size, pageIndexByScreenSpacePoint);
            } catch (Exception e) {
                Log.e(TAG, "isLimit Exception  " + e);
                return false;
            }
        }
        if (!z && !zIsPointLimit && !z2) {
            return false;
        }
        if (CreationPageSDK.sErrorCallback != null) {
            CreationPageSDK.sErrorCallback.onError(z ? 2 : zIsPointLimit ? 0 : z2 ? 3 : 2);
        }
        return true;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public int getCount() {
        return this.mCurvePasteCount;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onDelete(boolean z) {
        onDelete(z, false);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onDelete(boolean z, boolean z2) {
        if (!this.mCurSelectedRectF.isEmpty() && !this.mSpannedSelectedRectF.isEmpty()) {
            onStepCount(2);
        }
        if (this.mSpannedMode != null) {
            this.selectOffState = 1;
            this.mSpannedMode.doSelectedDelete(z2);
        }
        setAction(67);
        this.mPageItemView.doDelete(z, z2);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void getSelectedOffset(PointF pointF, PointF pointF2) {
        pointF.set(this.mCopySpannedOffset);
        pointF2.set(this.mCopyCurOffset);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void setSelectedOffset(PointF pointF, PointF pointF2) {
        if (pointF != null) {
            this.mCopySpannedOffset.set(pointF);
        }
        if (pointF != null) {
            this.mCopyCurOffset.set(pointF2);
        }
    }

    void calcOffset() {
        float scale = this.mPageInkViewModel.getScale() * this.mPageItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        this.mCopyCurOffset.set((this.mCurSelectedRectF.centerX() - this.mUnionRectF.centerX()) / scale, (this.mCurSelectedRectF.centerY() - this.mUnionRectF.centerY()) / scale);
        this.mCopySpannedOffset.set((this.mSpannedSelectedRectF.centerX() - this.mUnionRectF.centerX()) / scale, (this.mSpannedSelectedRectF.centerY() - this.mUnionRectF.centerY()) / scale);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCopy(boolean z) {
        this.mCurvePasteCount = 0;
        calcOffset();
        if (this.mSpannedMode != null) {
            this.mSpannedMode.doSelectedDuplicate(z);
        }
        setAction(66);
        this.mPageItemView.doCopy(z);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCopyPaste(boolean z) {
        boolean z2;
        boolean z3;
        this.mCurvePasteCount = 0;
        calcOffset();
        this.mUnionRectF.setEmpty();
        if (this.mCurSelectedOutPath != null) {
            this.mCurSelectedOutPath.offset(50.0f, 50.0f);
        }
        if (this.mCurSelectedRectF.isEmpty() || this.mSpannedSelectedRectF.isEmpty()) {
            onSelectCount((!this.mCurSelectedRectF.isEmpty() ? 1 : 0) + 1, "");
        } else {
            onSelectCount(3, "");
            onStepCount(2);
        }
        if (this.mSpannedMode != null) {
            PointF pointFDoSelectedCopyAndPast = this.mSpannedMode.doSelectedCopyAndPast(50.0f, 50.0f);
            this.mPointF = pointFDoSelectedCopyAndPast;
            pointFDoSelectedCopyAndPast.offset(Math.min(this.mLayoutRectF.left, 0.0f), Math.min(this.mLayoutRectF.top, 0.0f));
            if (!this.mSpannedSelectedRectF.isEmpty()) {
                this.mSpannedMode.finishSelect();
            }
            boolean z4 = hasCopySpannedBitmap(this.mCopySpannedDataList) && this.mPageItemView != null && this.mPageItemView.isBitmapSizeMax(this.mCopySpannedDataList);
            List<SpannedData> list = this.mCopySpannedDataList;
            if (list == null || list.size() <= 0 || this.mPageItemView == null || !this.mPageItemView.isTextSizeMax(this.mCopySpannedDataList)) {
                z3 = false;
                z2 = z4;
            } else {
                z3 = true;
                z2 = z4;
            }
        } else {
            z2 = false;
            z3 = false;
        }
        if (z2 || z3) {
            setAction(66);
            this.mPageItemView.doCopy(z);
            this.mPageItemView.cancelSelect(false);
            reset();
            if (CreationPageSDK.sErrorCallback != null) {
                CreationPageSDK.sErrorCallback.onError(z2 ? 2 : 3);
                return;
            }
            return;
        }
        if (this.mCopySpannedDataList != null && this.mCurSelectedRectF.isEmpty()) {
            float[] lastOffset = this.mPageItemView.getLastOffset();
            this.mSpannedMode.doPast(this.mCopySpannedDataList, this.mPointF.x + (this.mPageInkViewModel.isUnlimited() ? lastOffset[0] : 0.0f), this.mPointF.y + (this.mPageInkViewModel.isUnlimited() ? lastOffset[1] : 0.0f));
            this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selected();
        } else {
            setAction(64);
            this.mPageItemView.doCopyAndPaste(z);
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCut(boolean z) {
        this.mCurvePasteCount = 0;
        calcOffset();
        if (!this.mCurSelectedRectF.isEmpty() && !this.mSpannedSelectedRectF.isEmpty()) {
            onStepCount(2);
        }
        if (this.mSpannedMode != null && !this.mSpannedSelectedRectF.isEmpty()) {
            this.mSpannedMode.doSelectedCut(z);
        }
        if (!this.mCurSelectedRectF.isEmpty()) {
            setAction(65);
            this.mPageItemView.doCopy(z);
            this.mSpannedMode.finishSelect();
        }
        MiCurveEngine curveEngine = this.mPageItemView.getCurveEngine();
        if (curveEngine != null) {
            curveEngine.refreshFrame();
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCrop() {
        setAction(35);
        SpannedData clickImage = getClickImage();
        this.mSpannedData = clickImage;
        if (clickImage != null) {
            this.mSpannedDrawnRectF = clickImage.getOriginDrawRectF(getCurveEngine());
            this.mCropArea.set(this.mSpannedData.getDrawRectF(getCurveEngine()));
        } else {
            this.mCropArea.set(this.mSpannedSelectedRectF);
        }
        if (getCurveEngine() != null) {
            Log.d(TAG, "onCrop call enterImageCropMode");
            getCurveEngine().getSelectSystem().enterImageCropMode();
        }
        if (this.mSpannedData != null && this.mSelectListener != null) {
            this.mSelectListener.showCropPopWindow(this.mSpannedData.getRotatedCropBoundingBox(this.mCropArea, this.mSpannedMode.screenRenderProvider));
        }
        refreshCropUnionPath();
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCropFinish() {
        setAction(39);
        if (this.mSpannedMode.mSpannedSelectObject != null && this.mSpannedData != null) {
            this.mSpannedMode.mSpannedSelectObject.doCrop(this.mSpannedData.mCropRectF, this.mCropRate);
            this.mSpannedData.setCropRectF(this.mCropRate);
        }
        if (getCurveEngine() != null) {
            Log.d(TAG, "onCropFinish call exitImageCropMode");
            getCurveEngine().getSelectSystem().exitImageCropMode();
        }
        refreshCropUnionPath();
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onCropCancel() {
        setAction(47);
        if (getCurveEngine() != null) {
            Log.d(TAG, "onCropCancel call exitImageCropMode");
            getCurveEngine().getSelectSystem().exitImageCropMode();
        }
        refreshCropUnionPath();
        postInvalidate();
    }

    private void refreshCropUnionPath() {
        if (this.mSpannedMode == null || this.mSpannedData == null) {
            return;
        }
        if (this.mCurSelectAction == 35) {
            this.mSpannedMode.setUnionPath(this.mSpannedData.getOriginDrawRectF(getCurveEngine()));
        } else {
            this.mSpannedMode.setUnionPath(this.mSpannedData.getDrawRectF(getCurveEngine()));
        }
        this.mSpannedMode.refreshUnionPath();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void setAction(int i) {
        this.mCurSelectAction = i;
        if (this.mPageItemView != null) {
            this.mPageItemView.setSelectType(i);
        }
        Log.d(TAG, "setAction: " + i);
        if (this.mSpannedMode != null) {
            if (i == 1) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_NULL);
            } else if (i == 4) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_MOVE);
            } else if (i == 8) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_SCALE);
            } else if (i == 16) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_ROTATE);
            } else if (i == 28) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_MIXED);
            }
            if (i == 128) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_TOP);
                return;
            }
            if (i == 129) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_RIGHT);
                return;
            }
            if (i == 130) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_BOTTOM);
                return;
            }
            if (i == 131) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_LEFT);
                return;
            }
            if (i == 35) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_CROP);
            } else if (i == 39 || i == 47) {
                this.mSpannedMode.setSelectAction(SpannedSelectInfo.SELECT_ACTION_NULL);
            }
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean isSelectRectNotEmpty() {
        return (this.mSpannedMode == null || this.mSpannedMode.getUnionSelectRectF().isEmpty()) ? false : true;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void moveSelectPath(float f, float f2, Path path) {
        MathDataManager mathDataManager = this.mPageItemView.getCreationEngine().getCurveEngine().getMathDataManager();
        int i = this.mSelectType;
        if (i == 0 || i == 4 || mathDataManager.isMathSelect()) {
            if (path != null) {
                if (this.mCurSelectedOutPath == null) {
                    this.mCurSelectedOutPath = path;
                    if (this.mCurLastRectF.left == this.mCurSelectedRectF.left && this.mCurLastRectF.top == this.mCurSelectedRectF.top) {
                        this.mCurSelectedOutPath.offset(this.mSelectPoint.x, this.mSelectPoint.y);
                    }
                    this.mSelectPoint.set(0.0f, 0.0f);
                    return;
                }
                this.mCurSelectedOutPath.offset(f, f2);
                return;
            }
            this.mSelectPoint.offset(f, f2);
            if (this.mSelectTouchPath != null) {
                this.mSelectTouchPath.offset(f, f2);
            }
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list) {
        onObjectSelected(sRectF, list, false);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onObjectSelected(final SRectF sRectF, List<DataCurve> list, boolean z) {
        String str = TAG;
        Log.d(str, "onObjectSelected: " + this.mSelectCount);
        this.isReady = true;
        if (sRectF.isEmpty()) {
            this.mCurSelectedOutPath = null;
            this.isReady = true;
            setAction(0);
        } else {
            this.mIsEdit = true;
        }
        this.mCurLastRectF.set(sRectF);
        int i = this.mSelectCount - 1;
        this.mSelectCount = i;
        this.mSelectedUuid = null;
        if (i == 0) {
            this.mSelectTouchPath = null;
            post(new Runnable() { // from class: com.miui.libhandwritingpage.view.select.SelectView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m868x23ee6491();
                }
            });
            if ((!this.mUnionRectF.isEmpty()) | (!sRectF.isEmpty())) {
                Log.d(str, "onObjectSelected: " + this.mUnionRectF.isEmpty());
                if (this.mUnionRectF.isEmpty()) {
                    if (this.mPageItemView.isSingleCurves()) {
                        this.mSelectType = 4;
                    } else {
                        this.mSelectType = 0;
                    }
                } else if (sRectF.isEmpty()) {
                    if (this.mSpannedMode != null) {
                        this.mSelectType = this.mSpannedMode.hadSelectedSingleImage() ? 2 : 1;
                    }
                } else if (!this.mPageItemView.getCreationEngine().getCurveEngine().getMathDataManager().isMathSelect()) {
                    this.mSelectType = 3;
                }
            }
            if (this.mSpannedMode != null) {
                this.mCurSelectedRectF.set(sRectF);
                float singleSelectAngle = this.mSpannedMode.getSingleSelectAngle();
                this.mSpannedMode.setMixRectangular(this.mSelectType == 3);
                if (sRectF.isRectEmpty() && singleSelectAngle != 0.0f) {
                    this.mUnionRectF.set(this.mSpannedMode.getSingleSelectRectF());
                    this.mSpannedMode.checkUnionSelect(this.mUnionRectF);
                    this.mSpannedMode.setSelectInfoRotate(singleSelectAngle);
                } else {
                    this.mUnionRectF.union(sRectF.left, sRectF.top, sRectF.right, sRectF.bottom);
                    this.mSpannedMode.checkUnionSelect(this.mUnionRectF);
                }
                CreationPageSDK.with(getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.select.SelectView$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m869x51c6fef0(sRectF);
                    }
                });
            }
        }
        if (this.isAloneDelete == 2) {
            this.isAloneDelete = 0;
            onDelete(false, z);
            if (this.mSelectListener != null) {
                this.mSelectListener.onDeleteData();
            }
        } else {
            if (this.mSpannedMode != null) {
                this.mMultiSelectRectF = new SRectF(this.mSpannedMode.getUnionSelectRectFNoRotate(), this.mSpannedMode.getTotalAngle());
                this.isSingleBitmap = sRectF.isRectEmpty() && this.mSpannedMode.hadSelectedSingleImage();
                this.mIsSingleText = sRectF.isRectEmpty() && this.mSpannedMode.hadSelectedSingleText();
                if (!sRectF.isEmpty()) {
                    this.mPageItemView.setSelectRectF(this.mMultiSelectRectF);
                }
            }
            if (this.mSelectListener != null) {
                RectF rectFScaleAndTranslateRectF = scaleAndTranslateRectF(this.mMultiSelectRectF);
                ArrayList arrayList = new ArrayList(this.mTouchPoints);
                if (this.mSpannedMode != null && this.mSpannedMode.isHadPointSelected()) {
                    arrayList.clear();
                    arrayList.add(new TouchPosition(rectFScaleAndTranslateRectF.centerX(), rectFScaleAndTranslateRectF.centerY(), 0L));
                }
                Log.d(str, "onObjectSelected: ViewIndex:" + this.mPageItemView.model.getCurrentPosition());
                this.mSelectListener.onSelectSaveRectF(rectFScaleAndTranslateRectF, arrayList, this.mPageItemView.model.getCurrentPosition(), list, this.mOriginTouchPoint);
                this.mOriginTouchPoint.clear();
                this.mTouchPoints.clear();
            }
            if (isSelectRectNotEmpty()) {
                this.mMultiSelectRectF.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
            }
        }
        postInvalidate();
        this.mPageItemView.invalidateDrawView();
        this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selected();
    }

    /* JADX INFO: renamed from: lambda$onObjectSelected$0$com-miui-libhandwritingpage-view-select-SelectView, reason: not valid java name */
    /* synthetic */ void m868x23ee6491() {
        if (this.mSpannedMode != null) {
            this.mSpannedMode.resetSelectViewData();
        }
    }

    /* JADX INFO: renamed from: lambda$onObjectSelected$1$com-miui-libhandwritingpage-view-select-SelectView, reason: not valid java name */
    /* synthetic */ void m869x51c6fef0(SRectF sRectF) {
        if (this.mSpannedMode != null) {
            this.mSpannedMode.externalSelected(!sRectF.isEmpty());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onSpannedSelected(RectF rectF, boolean z) {
        Log.d(TAG, "onSpannedSelected: " + rectF);
        if (rectF != null) {
            char c = 1;
            this.mIsEdit = true;
            this.mSpannedSelectedRectF.set(rectF);
            this.mUnionRectF.setEmpty();
            this.mSelectCount--;
            this.mUnionRectF.union(rectF.left, rectF.top, rectF.right, rectF.bottom);
            float f = 0.0f;
            int i = 0;
            if (this.mSelectCount == 0) {
                post(new Runnable() { // from class: com.miui.libhandwritingpage.view.select.SelectView$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m870x9d0ce883();
                    }
                });
                this.mSpannedMode.checkUnionSelect(this.mUnionRectF);
                this.mMultiSelectRectF = new SRectF(this.mUnionRectF, this.mSpannedMode.getTotalAngle());
                this.isSingleBitmap = this.mSpannedMode.hadSelectedSingleImage();
                this.mIsSingleText = this.mSpannedMode.hadSelectedSingleText();
                float singleSelectAngle = this.mSpannedMode.getSingleSelectAngle();
                if (singleSelectAngle != 0.0f) {
                    this.mUnionRectF.set(this.mSpannedMode.getSingleSelectRectF());
                    this.mSpannedMode.checkUnionSelect(this.mUnionRectF);
                    this.mSpannedMode.setSelectInfoRotate(singleSelectAngle);
                }
                this.mSelectType = this.isSingleBitmap ? 2 : 1;
                if (this.isAloneDelete == 1) {
                    this.isAloneDelete = 0;
                    onDelete(false);
                    if (this.mSelectListener != null) {
                        this.mSelectListener.onDeleteData();
                    }
                } else {
                    RectF rectFScaleAndTranslateRectF = scaleAndTranslateRectF(this.mMultiSelectRectF);
                    ArrayList arrayList = new ArrayList(this.mTouchPoints);
                    if (this.mTouchPoints.size() == 0 && !rectFScaleAndTranslateRectF.isEmpty()) {
                        arrayList.add(new TouchPosition(rectFScaleAndTranslateRectF.centerX(), rectFScaleAndTranslateRectF.centerY(), 0L));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    if (this.mSelectListener != null) {
                        this.mSelectListener.onSelectSaveRectF(rectFScaleAndTranslateRectF, arrayList, this.mPageItemView.getIndex(), arrayList2, this.mOriginTouchPoint);
                    }
                    this.mOriginTouchPoint.clear();
                    this.mTouchPoints.clear();
                    this.mMultiSelectRectF.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
                }
                postInvalidate();
            } else {
                List<SpannedData> selectedList = this.mSpannedMode.getSelectedList();
                List<TouchPoint> arrayList3 = new ArrayList<>();
                for (SpannedData spannedData : selectedList) {
                    RectF drawRectF = spannedData.getDrawRectF(getCurveEngine());
                    Matrix matrix = new Matrix();
                    float[] fArr = new float[12];
                    float f2 = drawRectF.left;
                    float f3 = f;
                    float f4 = drawRectF.top;
                    float f5 = drawRectF.right;
                    char c2 = c;
                    float f6 = drawRectF.top;
                    int i2 = i;
                    float f7 = drawRectF.right;
                    float f8 = drawRectF.bottom;
                    float f9 = drawRectF.left;
                    float f10 = drawRectF.bottom;
                    float fCenterX = drawRectF.centerX();
                    float fCenterY = drawRectF.centerY();
                    float[] fArr2 = new float[12];
                    fArr2[i2] = f2;
                    fArr2[c2] = f4;
                    fArr2[2] = f5;
                    fArr2[3] = f6;
                    fArr2[4] = f7;
                    fArr2[5] = f8;
                    fArr2[6] = f9;
                    fArr2[7] = f10;
                    fArr2[8] = fCenterX;
                    fArr2[9] = fCenterY;
                    fArr2[10] = f3;
                    fArr2[11] = f3;
                    matrix.setRotate(spannedData.getDegrees(), drawRectF.centerX(), drawRectF.centerY());
                    matrix.mapPoints(fArr, fArr2);
                    for (int i3 = i2; i3 < 5; i3++) {
                        int i4 = i3 * 2;
                        arrayList3.add(new TouchPoint(fArr[i4 % 8], fArr[(i4 + 1) % 8], 0L));
                    }
                    f = f3;
                    c = c2;
                    i = i2;
                }
                boolean z2 = c;
                this.mPageItemView.getCreationEngine().getSelectOperator().setExtraSelectPoint(arrayList3, i);
                if (this.isAloneDelete == z2 && !this.mListPoint.isEmpty()) {
                    this.isAloneDelete = 2;
                    this.mPageItemView.getCreationEngine().getSelectOperator().setExtraSelectPoint(this.mListPoint, z2, z);
                }
                this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selectOn(selectedList);
            }
        }
        postInvalidate();
    }

    /* JADX INFO: renamed from: lambda$onSpannedSelected$2$com-miui-libhandwritingpage-view-select-SelectView, reason: not valid java name */
    /* synthetic */ void m870x9d0ce883() {
        if (this.mSpannedMode != null) {
            this.mSpannedMode.resetSelectViewData();
        }
        invalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void cancelSelect() {
        reset();
        this.mIsEdit = true;
        this.mScaleList.clear();
        this.mRotateList.clear();
        this.mSelectType = 0;
        this.mSelectPoint.set(0.0f, 0.0f);
        this.mMultiSelectRectF.setEmpty();
        this.mCurSelectedRectF.setEmpty();
        this.mUnionRectF.setEmpty();
        this.mShowSelectPopWindow = false;
        if (this.mSelectListener != null) {
            this.mSelectListener.dismissSelectPopWindow();
        }
        this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getOutlineDrawer().clear();
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void spannedCancel() {
        if (this.mSpannedMode != null) {
            boolean z = this.mCurSelectedRectF.isEmpty() && !this.mSpannedSelectedRectF.isEmpty();
            this.mSpannedMode.resetSelect();
            if (z && this.mSelectListener != null) {
                this.mSelectListener.onSelectSaveRectF(new RectF(), new ArrayList(), this.mPageItemView.getIndex(), new ArrayList(), this.mOriginTouchPoint);
            }
            this.mIsEdit = true;
            this.mMultiSelectRectF.setEmpty();
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void destroy() {
        if (this.mSpannedMode != null) {
            this.mSpannedMode.finishSelect();
            this.mSpannedMode = null;
        }
    }

    public Path getSpanPath() {
        return this.mSpannedMode == null ? new Path() : this.mSpannedMode.getUnionSelectPath();
    }

    public void onSelectCount(int i, String str) {
        this.mSelectCount = i;
        this.mSelectedUuid = str;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public RectF getRectF() {
        return this.mMultiSelectRectF;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onStepCount(int i) {
        PageStepHelper pageStepHelper = this.mStepHelper;
        if (pageStepHelper != null) {
            pageStepHelper.setStepCount(i);
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean isSelectDouble() {
        return (this.mCurSelectedRectF.isEmpty() || this.mSpannedSelectedRectF.isEmpty()) ? false : true;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public int findSelectTypeBy(float f, float f2) {
        int i;
        Path path;
        int i2;
        if (this.mSpannedMode == null) {
            return 1;
        }
        if (!this.mIsEdit || this.mScaleList.isEmpty()) {
            i = 1;
        } else {
            i = 1;
            for (int i3 = 0; i3 < this.mScaleList.size(); i3++) {
                if (new RectF(this.mScaleList.get(i3)).contains(f, f2)) {
                    i = 8;
                }
            }
        }
        if (this.mIsEdit && !this.mRotateList.isEmpty()) {
            for (int i4 = 0; i4 < this.mRotateList.size(); i4++) {
                if (new RectF(this.mRotateList.get(i4)).contains(f, f2)) {
                    i = 16;
                }
            }
        }
        if (i == 1) {
            Region region = new Region();
            Path path2 = null;
            try {
                if (this.mPageItemView != null && this.mPageItemView.getCreationEngine() != null && this.mPageItemView.getCreationEngine().getCurveEngine() != null && this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem() != null && this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getOutlineDrawer() != null) {
                    path2 = this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getOutlineDrawer().getPath();
                    Log.d(TAG, "findSelectTypeBy: GetOut");
                }
            } catch (Throwable unused) {
            }
            if (this.mCurSelectedOutPath != null && (((i2 = this.mSelectType) == 0 || i2 == 4) && !this.mIsEdit)) {
                path = new Path(this.mCurSelectedOutPath);
            } else if (path2 != null && !path2.isEmpty()) {
                Log.d(TAG, "findSelectTypeBy: Out");
                path = new Path(path2);
            } else {
                path = new Path(this.mSpannedMode.getUnionSelectPath());
            }
            path.offset(this.mLayoutOffsetX, this.mLayoutOffsetY);
            RectF rectF = new RectF();
            path.computeBounds(rectF, false);
            region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            if (region.contains((int) f, (int) f2) && isSelectEnableFollow()) {
                i = 4;
            }
        }
        Log.d(TAG, "action is " + i);
        return i;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void finishSelect() throws InterruptedException {
        this.mSpannedSelectedRectF.setEmpty();
        this.mShowSelectPopWindow = false;
        this.mUnionRectF.setEmpty();
        postInvalidate();
        if (this.mSelectListener != null) {
            this.mSelectListener.dismissSelectPopWindow();
        }
        this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().selectOff(new Function1() { // from class: com.miui.libhandwritingpage.view.select.SelectView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SelectSystem.SelectContent) obj).getCurveCount() == 0);
            }
        }, false, this.selectOffState);
        this.selectOffState = 0;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onSelectedClick() {
        if (this.mPageItemView != null) {
            this.mPageItemView.cancelSelect(false);
        }
    }

    public void setStepHelper(PageStepHelper pageStepHelper) {
        this.mStepHelper = pageStepHelper;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void cancelSelectAction(float f, float f2) {
        if (this.mPageItemView != null) {
            this.mPageItemView.getCreationEngine().getSelectOperator().cancelSelectAction(f, f2);
        }
    }

    public void setSelectState(boolean z) {
        this.mIsEdit = true;
        this.mShowSelectPopWindow = false;
        if (this.mSelectListener != null) {
            this.mSelectListener.dismissSelectPopWindow();
        }
        postInvalidate();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onSelectedHashCode(int i) {
        this.mSelectedHashCode = i;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean hasCopySpannedBitmap(List<SpannedData> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        Iterator<SpannedData> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getType() == 7) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void onStartPasteFromCopy(List<SpannedData> list) {
        if (list == null || this.mSpannedMode == null) {
            return;
        }
        this.mSpannedMode.doPast(list, this.mPointF.x + (this.mPageItemView.isUnLimit() ? this.mPageItemView.getLastOffset()[0] : 0.0f), this.mPointF.y + (this.mPageItemView.isUnLimit() ? this.mPageItemView.getLastOffset()[1] : 0.0f));
    }

    public void onSpannedCopyData(List<SpannedData> list) {
        this.mCopySpannedDataList = list;
    }

    public void clearSpannedCopyData() {
        this.mCopySpannedDataList = null;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public int getType() {
        if (isSelectRectNotEmpty()) {
            return this.mSelectType;
        }
        return -1;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public boolean isFromAi() {
        List<SpannedData> selectedList;
        if (this.mSpannedMode != null && this.mSpannedMode.mSpannedSelectObject != null && (selectedList = this.mSpannedMode.mSpannedSelectObject.getSelectedList()) != null && selectedList.size() == 1 && selectedList.get(0).mBitmapPath != null) {
            return selectedList.get(0).mBitmapPath.contains("Ai_Tag");
        }
        return super.isFromAi();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public List<SpannedData> getAllSelectImageSpanned() {
        if (this.mSpannedMode == null || this.mSpannedMode.mSpannedSelectObject == null) {
            return null;
        }
        return this.mSpannedMode.mSpannedSelectObject.getSelectedList();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public List<SpannedData> getAllFromAISpanned() {
        List<SpannedData> allSpanData;
        if (this.mPageItemView != null && (allSpanData = this.mPageItemView.getAllSpanData()) != null) {
            ArrayList arrayList = new ArrayList();
            for (SpannedData spannedData : allSpanData) {
                if (spannedData.mBitmapPath != null && spannedData.mBitmapPath.contains("Ai_Tag") && spannedData.mSpannedStatus != 3) {
                    arrayList.add(spannedData);
                }
            }
            return arrayList;
        }
        return super.getAllFromAISpanned();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public List<SpannedData> getAllFromInsertSpanned() {
        List<SpannedData> allSpanData;
        if (this.mPageItemView != null && (allSpanData = this.mPageItemView.getAllSpanData()) != null) {
            ArrayList arrayList = new ArrayList();
            for (SpannedData spannedData : allSpanData) {
                if (spannedData.mBitmapPath != null && !spannedData.mBitmapPath.contains("Ai_Tag") && spannedData.mSpannedStatus != 3) {
                    arrayList.add(spannedData);
                }
            }
            return arrayList;
        }
        return super.getAllFromAISpanned();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public String getAiPath() {
        List<SpannedData> selectedList;
        if (this.mSpannedMode != null && this.mSpannedMode.mSpannedSelectObject != null && (selectedList = this.mSpannedMode.mSpannedSelectObject.getSelectedList()) != null && selectedList.size() == 1) {
            return selectedList.get(0).mBitmapPath;
        }
        return super.getAiPath();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public SpannedData getClickImage() {
        List<SpannedData> selectedList;
        if (this.mSpannedMode != null && this.mSpannedMode.mSpannedSelectObject != null && (selectedList = this.mSpannedMode.mSpannedSelectObject.getSelectedList()) != null && selectedList.size() == 1) {
            return selectedList.get(0);
        }
        return super.getClickImage();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public List<TouchPosition> getTouchPositionList() {
        ArrayList arrayList = new ArrayList();
        for (TouchPosition touchPosition : this.mTouchPoints) {
            PointF pointF = new PointF(touchPosition.x, touchPosition.y);
            toOriginPointFIgnoreScale(pointF);
            arrayList.add(new TouchPosition(pointF.x, pointF.y, touchPosition.time));
        }
        return arrayList;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public List<SpannedData> getAllSpanByRectF(RectF rectF) {
        if (this.mSpannedMode != null) {
            return this.mSpannedMode.getAllSpanByRectF(rectF);
        }
        return new ArrayList();
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public Bitmap getBitmap(boolean z) {
        return getBitmap(z, false);
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public Bitmap getBitmap(boolean z, boolean z2) {
        Bitmap bitmapCreateBitmap;
        RectF rectF;
        Bitmap bitmapCreateBitmap2;
        float f = this.mMultiSelectRectF.right - this.mMultiSelectRectF.left;
        float f2 = this.mMultiSelectRectF.bottom - this.mMultiSelectRectF.top;
        if (this.mMultiSelectRectF.mAngle != 0.0f) {
            double[] rotatedRectangleVertices = this.mMultiSelectRectF.getRotatedRectangleVertices();
            double d = Double.MIN_VALUE;
            double d2 = Double.MAX_VALUE;
            double d3 = Double.MIN_VALUE;
            double d4 = Double.MAX_VALUE;
            for (int i = 0; i < rotatedRectangleVertices.length; i += 2) {
                double d5 = rotatedRectangleVertices[i];
                if (d5 < d4) {
                    d4 = d5;
                }
                if (d5 > d) {
                    d = d5;
                }
                double d6 = rotatedRectangleVertices[i + 1];
                if (d6 < d2) {
                    d2 = d6;
                }
                if (d6 > d3) {
                    d3 = d6;
                }
            }
            f = (float) (d - d4);
            f2 = (float) (d3 - d2);
        }
        if (this.mPageItemView != null && !new RectF(this.mPageItemView.getSelectRect()).isEmpty()) {
            Bitmap bitmap = this.mPageItemView.getBitmap();
            if (!z) {
                return bitmap;
            }
            if (bitmap == null) {
                return null;
            }
            Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(bitmapCreateBitmap3);
            canvas.drawColor(this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            return bitmapCreateBitmap3;
        }
        Bitmap bitmapCreateBitmap4 = Bitmap.createBitmap((int) (f + 2.0f), (int) (f2 + 2.0f), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap4);
        if (z) {
            canvas2.drawColor(this.mPageInkViewModel.getItemDataList().get(this.mPageInkViewModel.getCurrentPosition()).bgColor);
        }
        if (this.mSpannedMode != null) {
            rectF = new RectF(this.mSpannedMode.getSelectRectF());
            if (rectF.isEmpty()) {
                bitmapCreateBitmap = null;
                bitmapCreateBitmap2 = null;
            } else {
                bitmapCreateBitmap2 = Bitmap.createBitmap((int) ((rectF.right - rectF.left) + 2.0f), (int) ((rectF.bottom - rectF.top) + 2.0f), Bitmap.Config.ARGB_8888);
                this.mSpannedMode.getSelectBitmap(bitmapCreateBitmap2, rectF, 2, z2);
                bitmapCreateBitmap = Bitmap.createBitmap((int) ((rectF.right - rectF.left) + 2.0f), (int) ((rectF.bottom - rectF.top) + 2.0f), Bitmap.Config.ARGB_8888);
                this.mSpannedMode.getSelectBitmap(bitmapCreateBitmap, rectF, 1);
                rectF.offset(this.mLayoutOffsetX - this.mMultiSelectRectF.left, this.mLayoutOffsetY - this.mMultiSelectRectF.top);
            }
        } else {
            bitmapCreateBitmap = null;
            rectF = null;
            bitmapCreateBitmap2 = null;
        }
        if (bitmapCreateBitmap2 != null) {
            if (this.mMultiSelectRectF.mAngle != 0.0f) {
                canvas2.drawBitmap(bitmapCreateBitmap2, 0.0f, 0.0f, (Paint) null);
            } else {
                canvas2.drawBitmap(bitmapCreateBitmap2, rectF.left, rectF.top, (Paint) null);
            }
        }
        if (bitmapCreateBitmap != null) {
            if (this.mMultiSelectRectF.mAngle != 0.0f) {
                canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, (Paint) null);
                return bitmapCreateBitmap4;
            }
            float f3 = rectF.left;
            float f4 = rectF.top;
            canvas2.drawBitmap(bitmapCreateBitmap, f3, f4, (Paint) null);
        }
        return bitmapCreateBitmap4;
    }

    @Override // com.miui.libhandwritingpage.view.select.AbsSelectView
    public void replaceView(List<TouchPosition> list) {
        Log.d(TAG, "replaceView: " + this.mMultiSelectRectF);
        if (list == null || list.isEmpty()) {
            return;
        }
        if (!this.mMultiSelectRectF.isEmpty()) {
            this.isAloneDelete = 0;
            int i = this.mSelectType;
            if (i != 1 && i != 2) {
                onDelete(true, true);
                return;
            }
            onDelete(false, true);
            if (this.mSelectListener != null) {
                this.mSelectListener.onDeleteData();
                return;
            }
            return;
        }
        this.isAloneDelete = 1;
        this.mListPoint.clear();
        for (int i2 = 0; i2 < list.size(); i2++) {
            PointF pointF = new PointF(list.get(i2).x, list.get(i2).y);
            toDrawPointF(pointF);
            this.mListPoint.add(new TouchPoint(pointF.x, pointF.y, list.get(i2).time));
        }
        if (this.mSpannedMode != null) {
            this.mSpannedMode.doPointSelect(this.mListPoint, true);
        }
    }

    public void cancelSelects() {
        cancelSelects(false);
    }

    public void cancelSelects(boolean z) {
        if (this.isAloneDelete == 1) {
            this.isAloneDelete = 2;
            this.mPageItemView.getCreationEngine().getSelectOperator().setExtraSelectPoint(this.mListPoint, true, z);
        } else if (this.mCurSelectAction != 35) {
            cancelSelect();
        }
    }

    private class GesListener implements BaseSelectView.FeatureGesListener {
        private final ResizeDetector mResizeDetector;
        private final PointF mResizeStart;

        private GesListener() {
            this.mResizeStart = new PointF();
            this.mResizeDetector = new ResizeDetector();
        }

        @Override // com.miui.libhandwritingpage.view.select.BaseSelectView.FeatureGesListener
        public void onDown(MotionEvent motionEvent) {
            if (SelectView.this.mSpannedData == null) {
                Log.d(SelectView.TAG, "onDown: spanned data is null");
                return;
            }
            PointF rotatePoint = SelectView.this.getRotatePoint(motionEvent.getX(), motionEvent.getY(), SelectView.this.mSpannedDrawnRectF.centerX(), SelectView.this.mSpannedDrawnRectF.centerY(), -SelectView.this.mSpannedData.getDegrees());
            float f = rotatePoint.x;
            float f2 = rotatePoint.y;
            ResizeDetector resizeDetector = this.mResizeDetector;
            resizeDetector.mResizeEdge = resizeDetector.resolveResizeEdge(f, f2);
            if (this.mResizeDetector.mResizeEdge == 0) {
                return;
            }
            this.mResizeStart.set(f, f2);
        }

        @Override // com.miui.libhandwritingpage.view.select.BaseSelectView.FeatureGesListener
        public void onMove(MotionEvent motionEvent) {
            if (SelectView.this.mSpannedData == null) {
                Log.d(SelectView.TAG, "onMove: spanned data is null");
                return;
            }
            PointF rotatePoint = SelectView.this.getRotatePoint(motionEvent.getX(), motionEvent.getY(), SelectView.this.mSpannedDrawnRectF.centerX(), SelectView.this.mSpannedDrawnRectF.centerY(), -SelectView.this.mSpannedData.getDegrees());
            PointF pointFOnFreeResize = this.mResizeDetector.onFreeResize(rotatePoint.x - this.mResizeStart.x, rotatePoint.y - this.mResizeStart.y);
            this.mResizeStart.x += pointFOnFreeResize.x;
            this.mResizeStart.y += pointFOnFreeResize.y;
        }

        @Override // com.miui.libhandwritingpage.view.select.BaseSelectView.FeatureGesListener
        public void onActionUp() {
            this.mResizeDetector.mResizeEdge = 0;
            if (SelectView.this.mSpannedData == null || SelectView.this.mSelectListener == null) {
                return;
            }
            SelectView.this.mSelectListener.showCropPopWindow(SelectView.this.mSpannedData.getRotatedCropBoundingBox(SelectView.this.mCropArea, SelectView.this.mSpannedMode.screenRenderProvider));
        }
    }

    private class ResizeDetector {
        private int mResizeEdge;

        private float constraint(float f, float f2) {
            float f3 = f2 + f;
            if (f * f3 < 0.0f) {
                return 0.0f;
            }
            return f3;
        }

        private ResizeDetector() {
            this.mResizeEdge = 0;
        }

        /* JADX WARN: Code duplicated, block: B:24:0x008e  */
        /* JADX WARN: Code duplicated, block: B:26:0x00b4  */
        /* JADX WARN: Code duplicated, block: B:27:0x00b6  */
        /* JADX WARN: Code duplicated, block: B:29:0x00ba  */
        /* JADX WARN: Code duplicated, block: B:31:0x00c7  */
        /* JADX WARN: Code duplicated, block: B:33:0x00cb  */
        /* JADX WARN: Code duplicated, block: B:35:0x00f0  */
        /* JADX WARN: Code duplicated, block: B:36:0x00f2  */
        /* JADX WARN: Code duplicated, block: B:38:0x00f6  */
        /* JADX WARN: Code duplicated, block: B:40:0x0103  */
        /* JADX WARN: Code duplicated, block: B:43:0x010f  */
        /* JADX WARN: Code duplicated, block: B:45:0x0169  */
        /* JADX WARN: Code duplicated, block: B:48:0x017c  */
        public PointF onFreeResize(float f, float f2) {
            boolean z;
            int i;
            float f3;
            float fConstraint;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float fConstraint2;
            RectF rectF = SelectView.this.mSpannedDrawnRectF;
            int iCalculateMinSize = calculateMinSize();
            int i2 = this.mResizeEdge;
            boolean z2 = true;
            if ((i2 & 1) != 0) {
                float f9 = rectF.left - SelectView.this.mCropArea.left;
                float fConstraint3 = constraint(SelectView.this.mCropArea.right - SelectView.this.mCropArea.left, -iCalculateMinSize);
                if (f < f9) {
                    f = f9;
                } else if (f > fConstraint3) {
                    f = fConstraint3;
                }
                SelectView.this.mCropArea.left += f;
            } else {
                if ((i2 & 256) != 0) {
                    float f10 = rectF.right - SelectView.this.mCropArea.right;
                    float fConstraint4 = constraint(SelectView.this.mCropArea.left - SelectView.this.mCropArea.right, iCalculateMinSize);
                    if (f > f10) {
                        f = f10;
                    } else if (f < fConstraint4) {
                        f = fConstraint4;
                    }
                    SelectView.this.mCropArea.right += f;
                } else {
                    z = false;
                }
                i = this.mResizeEdge;
                if ((i & 16) != 0) {
                    f8 = rectF.top - SelectView.this.mCropArea.top;
                    fConstraint2 = constraint(SelectView.this.mCropArea.bottom - SelectView.this.mCropArea.top, -iCalculateMinSize);
                    if (f2 < f8) {
                        f2 = f8;
                    } else if (f2 > fConstraint2) {
                        f2 = fConstraint2;
                    }
                    SelectView.this.mCropArea.top += f2;
                } else if ((i & 4096) != 0) {
                    f3 = rectF.bottom - SelectView.this.mCropArea.bottom;
                    fConstraint = constraint(SelectView.this.mCropArea.top - SelectView.this.mCropArea.bottom, iCalculateMinSize);
                    if (f2 > f3) {
                        f2 = f3;
                    } else if (f2 < fConstraint) {
                        f2 = fConstraint;
                    }
                    SelectView.this.mCropArea.bottom += f2;
                } else {
                    z2 = z;
                }
                SelectView.this.mOffset.set(f, f2);
                if (z2) {
                    f4 = (SelectView.this.mCropArea.left - rectF.left) / (rectF.right - rectF.left);
                    f5 = (SelectView.this.mCropArea.top - rectF.top) / (rectF.bottom - rectF.top);
                    f6 = 1.0f - ((rectF.right - SelectView.this.mCropArea.right) / (rectF.right - rectF.left));
                    f7 = 1.0f - ((rectF.bottom - SelectView.this.mCropArea.bottom) / (rectF.bottom - rectF.top));
                    SelectView.this.mCropRate.set(f4, f5, f6, f7);
                    if (SelectView.this.getCurveEngine() != null) {
                        SelectView.this.getCurveEngine().getSelectSystem().setImageCropValue(f4, f6, f5, f7);
                    }
                    if (SelectView.this.mSelectListener != null) {
                        SelectView.this.mSelectListener.dismissCropPopWindow();
                    }
                }
                return SelectView.this.mOffset;
            }
            z = true;
            i = this.mResizeEdge;
            if ((i & 16) != 0) {
                f8 = rectF.top - SelectView.this.mCropArea.top;
                fConstraint2 = constraint(SelectView.this.mCropArea.bottom - SelectView.this.mCropArea.top, -iCalculateMinSize);
                if (f2 < f8) {
                    f2 = f8;
                } else if (f2 > fConstraint2) {
                    f2 = fConstraint2;
                }
                SelectView.this.mCropArea.top += f2;
            } else if ((i & 4096) != 0) {
                f3 = rectF.bottom - SelectView.this.mCropArea.bottom;
                fConstraint = constraint(SelectView.this.mCropArea.top - SelectView.this.mCropArea.bottom, iCalculateMinSize);
                if (f2 > f3) {
                    f2 = f3;
                } else if (f2 < fConstraint) {
                    f2 = fConstraint;
                }
                SelectView.this.mCropArea.bottom += f2;
            } else {
                z2 = z;
            }
            SelectView.this.mOffset.set(f, f2);
            if (z2) {
                f4 = (SelectView.this.mCropArea.left - rectF.left) / (rectF.right - rectF.left);
                f5 = (SelectView.this.mCropArea.top - rectF.top) / (rectF.bottom - rectF.top);
                f6 = 1.0f - ((rectF.right - SelectView.this.mCropArea.right) / (rectF.right - rectF.left));
                f7 = 1.0f - ((rectF.bottom - SelectView.this.mCropArea.bottom) / (rectF.bottom - rectF.top));
                SelectView.this.mCropRate.set(f4, f5, f6, f7);
                if (SelectView.this.getCurveEngine() != null) {
                    SelectView.this.getCurveEngine().getSelectSystem().setImageCropValue(f4, f6, f5, f7);
                }
                if (SelectView.this.mSelectListener != null) {
                    SelectView.this.mSelectListener.dismissCropPopWindow();
                }
            }
            return SelectView.this.mOffset;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x009c  */
        public int resolveResizeEdge(float f, float f2) {
            int i;
            float fCenterX = SelectView.this.mCropArea.centerX();
            float fCenterY = SelectView.this.mCropArea.centerY();
            float fMin = Math.min(SelectView.this.mResizeEdgeSlop, SelectView.this.mCropArea.height() / 3.0f);
            float fMin2 = Math.min(SelectView.this.mResizeEdgeSlop, SelectView.this.mCropArea.width() / 3.0f);
            if (f2 <= fCenterY - SelectView.this.mResizeEdgeSlop || f2 >= fCenterY + SelectView.this.mResizeEdgeSlop) {
                i = 0;
            } else if (f > SelectView.this.mCropArea.left - SelectView.this.mResizeEdgeSlop && f < SelectView.this.mCropArea.left + fMin2) {
                i = 1;
            } else if (f <= SelectView.this.mCropArea.right - fMin2 || f >= SelectView.this.mCropArea.right + SelectView.this.mResizeEdgeSlop) {
                i = 0;
            } else {
                i = 256;
            }
            if (f > fCenterX - SelectView.this.mResizeEdgeSlop && f < fCenterX + SelectView.this.mResizeEdgeSlop) {
                if (f2 > SelectView.this.mCropArea.top - SelectView.this.mResizeEdgeSlop && f2 < SelectView.this.mCropArea.top + fMin) {
                    i |= 16;
                } else if (f2 > SelectView.this.mCropArea.bottom - fMin && f2 < SelectView.this.mCropArea.bottom + SelectView.this.mResizeEdgeSlop) {
                    i |= 4096;
                }
            }
            if (f2 > SelectView.this.mCropArea.top - SelectView.this.mResizeEdgeSlop && f2 < SelectView.this.mCropArea.top + SelectView.this.mResizeEdgeSlop) {
                if (f > SelectView.this.mCropArea.left - SelectView.this.mResizeEdgeSlop && f < SelectView.this.mCropArea.left + fMin2) {
                    i |= 1;
                } else if (f > SelectView.this.mCropArea.right - fMin2 && f < SelectView.this.mCropArea.right + SelectView.this.mResizeEdgeSlop) {
                    i |= 256;
                }
            }
            if (f2 > SelectView.this.mCropArea.bottom - SelectView.this.mResizeEdgeSlop && f2 < SelectView.this.mCropArea.bottom + SelectView.this.mResizeEdgeSlop) {
                if (f > SelectView.this.mCropArea.left - SelectView.this.mResizeEdgeSlop && f < SelectView.this.mCropArea.left + fMin2) {
                    i |= 1;
                } else if (f > SelectView.this.mCropArea.right - fMin2 && f < SelectView.this.mCropArea.right + SelectView.this.mResizeEdgeSlop) {
                    i |= 256;
                }
            }
            if (f > SelectView.this.mCropArea.left - SelectView.this.mResizeEdgeSlop && f < SelectView.this.mCropArea.left + SelectView.this.mResizeEdgeSlop) {
                if (f2 > SelectView.this.mCropArea.top - SelectView.this.mResizeEdgeSlop && f2 < SelectView.this.mCropArea.top + fMin) {
                    i |= 16;
                } else if (f2 > SelectView.this.mCropArea.bottom - fMin && f2 < SelectView.this.mCropArea.bottom + SelectView.this.mResizeEdgeSlop) {
                    i |= 4096;
                }
            }
            if (f > SelectView.this.mCropArea.right - SelectView.this.mResizeEdgeSlop && f < SelectView.this.mCropArea.right + SelectView.this.mResizeEdgeSlop) {
                if (f2 > SelectView.this.mCropArea.top - SelectView.this.mResizeEdgeSlop && f2 < SelectView.this.mCropArea.top + fMin) {
                    return i | 16;
                }
                if (f2 > SelectView.this.mCropArea.bottom - fMin && f2 < SelectView.this.mCropArea.bottom + SelectView.this.mResizeEdgeSlop) {
                    return i | 4096;
                }
            }
            return i;
        }

        private float calculateMinPixels() {
            return Math.max(SelectView.CROP_AREA_MIN_PIXELS, Math.max(SelectView.this.mSpannedSelectedRectF.height(), SelectView.this.mSpannedSelectedRectF.width()) / 20.0f);
        }

        private int calculateMinSize() {
            return Math.max((int) (SelectView.this.getMatrix().mapRadius(calculateMinPixels()) + 0.5f), 100);
        }
    }

    @Override // com.miui.libhandwritingpage.view.select.BaseSelectView
    protected PointF getRotatePoint(float f, float f2, float f3, float f4, float f5) {
        if (f5 == 0.0f) {
            return new PointF(f, f2);
        }
        float f6 = f - f3;
        float f7 = f2 - f4;
        double radians = (float) Math.toRadians(f5);
        return new PointF(((((float) Math.cos(radians)) * f6) - (((float) Math.sin(radians)) * f7)) + f3, (f6 * ((float) Math.sin(radians))) + (f7 * ((float) Math.cos(radians))) + f4);
    }
}
