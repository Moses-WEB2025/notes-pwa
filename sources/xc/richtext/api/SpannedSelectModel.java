package xc.richtext.api;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.TouchEventUtils;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.ISpannedEditOperator;
import xc.richtext.operate.edit.ISpannedSelectObject;
import xc.richtext.utils.ScreenShortUtil;
import xc.richtext.utils.SpannedUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedSelectModel {
    private boolean isAiModel;
    private boolean isCropScale;
    private boolean isMixRectangular;
    private float mDownAngle;
    private float mDownDistance;
    private float mDownX;
    private float mDownY;
    private float mMoveDistance;
    private float mPreX;
    private float mPreY;
    protected OnSpannedEditListener mSpanEditListener;
    protected ISpannedEditOperator mSpannedEditOperator;
    private final SpannedEditLayout mSpannedLayout;
    public ISpannedSelectObject mSpannedSelectObject;
    public ScreenRenderProvider screenRenderProvider;
    private final String TAG = "SpannedSelectModel";
    private final SpannedSelectInfo mSelInfo = new SpannedSelectInfo();
    private final Matrix mMatrix = new Matrix();
    private boolean hadDown = false;
    private final int CLICK_DISTANCE = 32;
    private final PointF mSelectCenter = new PointF();
    private float[] mSelectData = new float[10];
    private float[] mData = new float[10];
    private final Path mSelectPath = new Path();
    protected boolean isLassoPen = false;
    private final PointF mZoomCenter = new PointF();
    private final PointF mPreZoomCenter = new PointF();
    private final PointF mPointDownCenter = new PointF();
    private float mTotalAngle = 0.0f;
    private float mSpannedScale = 1.0f;
    private float mSpannedLastUpScale = 1.0f;
    private boolean mIsEnableClick = true;
    private boolean isEnableSelect = false;
    private boolean mSelectedFinish = false;
    private final PointF mDownPoint = new PointF();
    private final PointF mParamCenter = new PointF();
    RectF mSelRectFNoRotate = new RectF();
    private final RectF mUnionRectF = new RectF();
    private boolean isNeedResetSelect = false;
    private float mCurveLimitScale = 1.0f;
    private boolean isSelectPopWindowShowing = false;
    private boolean mHadPointSelected = false;
    private boolean isMathAutoSelect = false;

    public void setMixRectangular(boolean z) {
        this.isMixRectangular = z;
    }

    public void setMathAutoSelect(boolean z) {
        this.isMathAutoSelect = z;
    }

    public SpannedSelectModel(SpannedEditLayout spannedEditLayout, ScreenRenderProvider screenRenderProvider) {
        this.mSpannedLayout = spannedEditLayout;
        this.screenRenderProvider = screenRenderProvider;
    }

    public void setSpannedEngine(ISpannedEditOperator iSpannedEditOperator) {
        this.mSpannedEditOperator = iSpannedEditOperator;
    }

    public void setModeEditListener(OnSpannedEditListener onSpannedEditListener) {
        this.mSpanEditListener = onSpannedEditListener;
    }

    public void enableSelectMode(boolean z, boolean z2) {
        this.isAiModel = z2;
        this.isLassoPen = z;
        this.mSpannedLayout.enableSelectMode(z);
        if (this.isLassoPen) {
            return;
        }
        stopSelectAction();
        finishSelect();
        this.mSelInfo.reset();
        this.mSpannedLayout.invalidate();
    }

    public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject) {
        onSpannedSelected(iSpannedSelectObject, false);
    }

    public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean z) {
        this.mSpannedSelectObject = iSpannedSelectObject;
        OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
        if (onSpannedEditListener != null) {
            onSpannedEditListener.onSpannedSelected(iSpannedSelectObject, z);
        }
    }

    public void stopSelectAction() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.stopAction();
        }
    }

    private void cancelSelectAction(float f, float f2) {
        Log.d("MathSelect", " private void cancelSelectAction(float x, float y)");
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.cancelAction();
        }
        OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
        if (onSpannedEditListener != null) {
            onSpannedEditListener.cancelSelectAction(f, f2);
        }
        if (this.isMathAutoSelect) {
            this.isMathAutoSelect = false;
            OnSpannedEditListener onSpannedEditListener2 = this.mSpanEditListener;
            if (onSpannedEditListener2 != null) {
                onSpannedEditListener2.onFinishSelect();
            }
        }
    }

    public void finishSelect() {
        Log.d("MathSelect", " public void finishSelect()");
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.finishSelect();
            this.mSpannedSelectObject = null;
        }
        if (this.isMathAutoSelect) {
            this.isMathAutoSelect = false;
            OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
            if (onSpannedEditListener != null) {
                onSpannedEditListener.onFinishSelect();
            }
        } else {
            OnSpannedEditListener onSpannedEditListener2 = this.mSpanEditListener;
            if (onSpannedEditListener2 != null) {
                onSpannedEditListener2.onFinishSelect();
            }
        }
        final SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        Objects.requireNonNull(spannedEditLayout);
        spannedEditLayout.post(new Runnable() { // from class: xc.richtext.api.SpannedSelectModel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                spannedEditLayout.requestLayout();
            }
        });
        this.mTotalAngle = 0.0f;
        this.mSelInfo.stopAction();
        this.mSelInfo.reset();
        this.mSelectData = new float[10];
        this.mData = new float[10];
        this.mSelectPath.reset();
        this.mUnionRectF.setEmpty();
        this.mSelRectFNoRotate.setEmpty();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 1 || !this.isLassoPen) {
            return false;
        }
        selectTouchEvent(motionEvent);
        return true;
    }

    private void selectTouchEvent(MotionEvent motionEvent) {
        SpannedSelectModel spannedSelectModel;
        if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_CROP) == SpannedSelectInfo.SELECT_ACTION_CROP) {
            return;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_SCALE) == SpannedSelectInfo.SELECT_ACTION_SCALE) {
            SpannedData cropSpannedData = getCropSpannedData();
            this.isCropScale = cropSpannedData != null && cropSpannedData.isHadCrop();
        } else {
            this.isCropScale = false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            spannedSelectModel = this;
            spannedSelectModel.mDownPoint.set(x, y);
            spannedSelectModel.mDownX = motionEvent.getX();
            spannedSelectModel.mDownY = motionEvent.getY();
            spannedSelectModel.hadDown = true;
            spannedSelectModel.onSelectDown(motionEvent);
        } else if (actionMasked == 1) {
            spannedSelectModel = this;
            spannedSelectModel.onSelectUp(motionEvent);
            spannedSelectModel.hadDown = false;
        } else if (actionMasked == 2) {
            spannedSelectModel = this;
            spannedSelectModel.onSelectMove(motionEvent);
        } else if (actionMasked == 5) {
            spannedSelectModel = this;
            spannedSelectModel.onSelectPointerDown(motionEvent.getX(), motionEvent.getY(), motionEvent.getX(1), motionEvent.getY(1));
        } else if (actionMasked != 6) {
            spannedSelectModel = this;
        } else {
            spannedSelectModel = this;
            spannedSelectModel.onSelectPointUp(motionEvent.getActionIndex(), motionEvent.getX(), motionEvent.getY(), motionEvent.getX(1), motionEvent.getY(1));
        }
        spannedSelectModel.mPreX = motionEvent.getX();
        spannedSelectModel.mPreY = motionEvent.getY();
    }

    private void onSelectDown(MotionEvent motionEvent) {
        this.mMoveDistance = 0.0f;
        this.isNeedResetSelect = false;
        this.mCurveLimitScale = 1.0f;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.isLassoPen) {
            this.mSelectPath.reset();
            if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_NULL) == SpannedSelectInfo.SELECT_ACTION_NULL) {
                this.mSelectPath.moveTo(x, y);
            } else {
                ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
                if (iSpannedSelectObject != null) {
                    iSpannedSelectObject.startAction(this.mSelInfo.getSelectTypeMask());
                }
                OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
                if (onSpannedEditListener != null) {
                    onSpannedEditListener.onStartAction();
                }
                PointF pointF = this.mSelectCenter;
                float[] fArr = this.mSelectData;
                pointF.set(fArr[8], fArr[9]);
                this.mSelInfo.getCenter().set(this.mSelectCenter.x, this.mSelectCenter.y);
                this.mDownDistance = (float) Math.sqrt(Math.pow(x - this.mSelectCenter.x, 2.0d) + Math.pow(y - this.mSelectCenter.y, 2.0d));
                this.mDownAngle = countAngle(x - this.mSelInfo.getCenter().x, y - this.mSelInfo.getCenter().y);
            }
            this.mSpannedLayout.invalidate();
        }
    }

    private float countAngle(float f, float f2) {
        return (float) Math.toDegrees(Math.atan2(f2, f));
    }

    public void resetSelect() {
        this.mSelectPath.reset();
        this.mMatrix.reset();
        finishSelect();
    }

    private void onSelectMove(MotionEvent motionEvent) {
        if (this.hadDown) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.mMoveDistance < 32.0f) {
                this.mMoveDistance = (float) Math.sqrt(Math.pow(x - this.mDownX, 2.0d) + Math.pow(y - this.mDownY, 2.0d));
            }
            if (this.isLassoPen) {
                if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_NULL) == SpannedSelectInfo.SELECT_ACTION_NULL) {
                    this.mSelectPath.quadTo((this.mPreX + x) / 2.0f, (this.mPreY + y) / 2.0f, x, y);
                } else {
                    if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_MOVE) == SpannedSelectInfo.SELECT_ACTION_MOVE && !this.isAiModel) {
                        doMove(x - this.mDownX, y - this.mDownY);
                        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
                        if (iSpannedSelectObject != null) {
                            iSpannedSelectObject.doMove(x - this.mDownX, y - this.mDownY);
                        }
                    }
                    if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_SCALE) == SpannedSelectInfo.SELECT_ACTION_SCALE) {
                        float fSqrt = ((float) Math.sqrt(Math.pow(x - this.mSelectCenter.x, 2.0d) + Math.pow(y - this.mSelectCenter.y, 2.0d))) / this.mDownDistance;
                        this.mSpannedScale = fSqrt;
                        if (this.mSpannedSelectObject != null) {
                            fSqrt = checkScaleLimit(fSqrt);
                        }
                        if (this.isNeedResetSelect) {
                            fSqrt = this.mCurveLimitScale;
                        }
                        doScale(fSqrt);
                        SpannedData cropSpannedData = getCropSpannedData();
                        if (cropSpannedData != null && cropSpannedData.isHadCrop() && !this.isMixRectangular) {
                            RectF rotatedBoundingBox = cropSpannedData.getRotatedBoundingBox(this.screenRenderProvider);
                            float f = fSqrt - 1.0f;
                            float fCenterX = (this.mSelectCenter.x - rotatedBoundingBox.centerX()) * f;
                            float fCenterY = (this.mSelectCenter.y - rotatedBoundingBox.centerY()) * f;
                            doMove(fCenterX, fCenterY);
                            ISpannedSelectObject iSpannedSelectObject2 = this.mSpannedSelectObject;
                            if (iSpannedSelectObject2 != null) {
                                iSpannedSelectObject2.doMove(fCenterX, fCenterY);
                            }
                        }
                    }
                    if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_ROTATE) == SpannedSelectInfo.SELECT_ACTION_ROTATE) {
                        float fCountAngle = countAngle(this.mPreX - this.mSelInfo.getCenter().x, this.mPreY - this.mSelInfo.getCenter().y);
                        float fCountAngle2 = countAngle(x - this.mSelInfo.getCenter().x, y - this.mSelInfo.getCenter().y);
                        doScale(1.0f);
                        doRotate(fCountAngle2 - fCountAngle);
                        SpannedData cropSpannedData2 = getCropSpannedData();
                        if (cropSpannedData2 != null && cropSpannedData2.isHadCrop() && !this.isMixRectangular) {
                            RectF rotatedBoundingBox2 = cropSpannedData2.getRotatedBoundingBox(this.screenRenderProvider);
                            float[] rotatePointF = SpannedUtils.getRotatePointF(new float[]{this.mSelectCenter.x, this.mSelectCenter.y}, this.mSelInfo.getTotalAngle(), rotatedBoundingBox2.centerX(), rotatedBoundingBox2.centerY());
                            doMove(rotatePointF[0] - this.mSelectCenter.x, rotatePointF[1] - this.mSelectCenter.y);
                            ISpannedSelectObject iSpannedSelectObject3 = this.mSpannedSelectObject;
                            if (iSpannedSelectObject3 != null) {
                                iSpannedSelectObject3.doMove(rotatePointF[0] - this.mSelectCenter.x, rotatePointF[1] - this.mSelectCenter.y);
                            }
                        }
                        this.mSelInfo.rotateInfo(fCountAngle2 - this.mDownAngle);
                        ISpannedSelectObject iSpannedSelectObject4 = this.mSpannedSelectObject;
                        if (iSpannedSelectObject4 != null) {
                            iSpannedSelectObject4.doRotate(this.mSelInfo.getTotalAngle(), this.mSelInfo.getCenter().x, this.mSelInfo.getCenter().y);
                        }
                    }
                    onMoveParam();
                    refreshUnionPath(false);
                }
                this.mSpannedLayout.requestLayout();
            }
        }
    }

    private void onMoveParam() {
        this.mParamCenter.set(this.mSelectCenter);
    }

    public float getCurTotalScale() {
        return this.hadDown ? this.mSelInfo.getTotalScale() : this.mSpannedLastUpScale;
    }

    private void onSelectPointerDown(float f, float f2, float f3, float f4) {
        float f5 = (f3 + f) / 2.0f;
        float f6 = (f4 + f2) / 2.0f;
        this.mPointDownCenter.set(f5, f6);
        this.mZoomCenter.set(f5, f6);
        this.mPreZoomCenter.set(f5, f6);
    }

    private void onSelectPointUp(int i, float f, float f2, float f3, float f4) {
        if (i == 0) {
            this.mPreX = f3;
            this.mPreY = f4;
        } else if (i == 1) {
            this.mPreX = f;
            this.mPreY = f2;
        }
    }

    private void onSelectUp(MotionEvent motionEvent) {
        if (this.hadDown) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.mMoveDistance < 32.0f) {
                this.mMoveDistance = (float) Math.sqrt(Math.pow(x - this.mDownX, 2.0d) + Math.pow(y - this.mDownY, 2.0d));
            }
            if (this.isLassoPen) {
                if (TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent)) {
                    this.mSelectPath.reset();
                    Log.w("MathSelect", "onSelectUp(MotionEvent event)");
                    cancelSelectAction(x, y);
                    this.mMatrix.reset();
                    refreshUnionPath(true);
                    this.mSelInfo.cancel();
                    if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_NULL) == SpannedSelectInfo.SELECT_ACTION_NULL) {
                        if (this.mIsEnableClick) {
                            doUnionPointSelect(x, y);
                        } else {
                            this.mIsEnableClick = true;
                            this.mSpannedSelectObject = null;
                            OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
                            if (onSpannedEditListener != null) {
                                onSpannedEditListener.onSpannedSelected(null);
                            }
                        }
                    } else if (this.isSelectPopWindowShowing) {
                        doClick(x, y);
                    }
                    this.mSpannedLayout.invalidate();
                    return;
                }
                if ((this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_NULL) == SpannedSelectInfo.SELECT_ACTION_NULL) {
                    if (this.isEnableSelect) {
                        doUnionPathSelect();
                    }
                    this.mSelectPath.reset();
                    return;
                }
                if (this.isNeedResetSelect && (this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_SCALE) == SpannedSelectInfo.SELECT_ACTION_SCALE) {
                    float f = this.mCurveLimitScale;
                    this.mSpannedScale = f;
                    doScale(f);
                } else {
                    onSelectMove(motionEvent);
                }
                this.mSelInfo.getSelectTypeMask();
                this.isNeedResetSelect = false;
                this.mSpannedLastUpScale = this.mSelInfo.getTotalScale();
                this.mSelInfo.stopAction();
                this.mSelInfo.reset();
                refreshUnionPath(true);
                stopSelectAction();
                this.mSpannedLayout.invalidate();
            }
        }
    }

    private void doClick(float f, float f2) {
        int clickTextId;
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || (clickTextId = iSpannedSelectObject.getClickTextId(f, f2)) == -1) {
            return;
        }
        finishSelect();
        OnSpannedEditListener onSpannedEditListener = this.mSpanEditListener;
        if (onSpannedEditListener != null) {
            onSpannedEditListener.onSelectedClick();
        }
        this.mSpannedLayout.getSpannedEditModel().enableEditTransient(true);
        this.mSpannedLayout.getSpannedLayoutModel().getSpannedEditOperator().doEditClick(clickTextId, f, f2);
    }

    private void doMove(float f, float f2) {
        this.mMatrix.setTranslate(f, f2);
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.doMove(f, f2);
        }
    }

    private void doRotate(float f) {
        this.mTotalAngle += f;
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.doRotate(f);
        }
    }

    private void doScale(float f) {
        this.mMatrix.setScale(f, f, this.mSelectCenter.x, this.mSelectCenter.y);
        this.mSelInfo.scaleInfo(f);
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null) {
            return;
        }
        iSpannedSelectObject.doScale(this.mSelInfo.getTotalScale(), this.mSelInfo.getCenter().x, this.mSelInfo.getCenter().y);
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.doScale(this.mSelInfo.getTotalScale(), this.mSelInfo.getCenter().x, this.mSelInfo.getCenter().y);
        }
    }

    public RectF getSingleSelectRectF() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null) {
            return new RectF();
        }
        if (iSpannedSelectObject.getSelectedList().size() == 1) {
            return this.mSpannedSelectObject.getSelectedList().get(0).getDrawRectF(this.screenRenderProvider);
        }
        return new RectF();
    }

    private PointF getOriginSingleSelectCenter() {
        SpannedData spannedData;
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || iSpannedSelectObject.getSelectedList().size() != 1 || (spannedData = this.mSpannedSelectObject.getSelectedList().get(0)) == null || !spannedData.isHadCrop()) {
            return null;
        }
        RectF originDrawRectF = spannedData.getOriginDrawRectF(this.screenRenderProvider);
        return new PointF(originDrawRectF.centerX(), originDrawRectF.centerY());
    }

    private SpannedData getCropSpannedData() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || iSpannedSelectObject.getSelectedList().size() != 1) {
            return null;
        }
        return this.mSpannedSelectObject.getSelectedList().get(0);
    }

    public float getSingleSelectAngle() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || iSpannedSelectObject.getSelectedList().size() != 1) {
            return 0.0f;
        }
        return this.mSpannedSelectObject.getSelectedList().get(0).getDegrees();
    }

    public void setSelectInfoRotate(float f) {
        this.mTotalAngle = f;
        refreshUnionPath(true);
    }

    public void checkUnionSelect(RectF rectF) {
        this.mUnionRectF.set(rectF);
        Path path = new Path();
        path.addRect(this.mUnionRectF, Path.Direction.CCW);
        this.mSelInfo.setSelectPath(path);
        setUnionPath(this.mUnionRectF);
        this.mSelInfo.setSelectData(this.mSelectData);
        this.mUnionRectF.setEmpty();
        this.mSpannedLayout.postInvalidate();
        this.mSelRectFNoRotate.set(rectF);
    }

    public void setUnionPath(RectF rectF) {
        PointF originSingleSelectCenter = getOriginSingleSelectCenter();
        if (originSingleSelectCenter != null && !this.isMixRectangular) {
            this.mSelectCenter.set(originSingleSelectCenter.x, originSingleSelectCenter.y);
        } else {
            this.mSelectCenter.set(rectF.centerX(), rectF.centerY());
        }
        this.mSelInfo.getCenter().set(this.mSelectCenter.x, this.mSelectCenter.y);
        this.mSelectData[0] = rectF.left;
        this.mSelectData[1] = rectF.top;
        this.mSelectData[2] = rectF.right;
        this.mSelectData[3] = rectF.top;
        this.mSelectData[4] = rectF.right;
        this.mSelectData[5] = rectF.bottom;
        this.mSelectData[6] = rectF.left;
        this.mSelectData[7] = rectF.bottom;
        this.mSelectData[8] = this.mSelectCenter.x;
        this.mSelectData[9] = this.mSelectCenter.y;
        float[] fArr = this.mSelectData;
        this.mData = Arrays.copyOf(fArr, fArr.length);
    }

    public void refreshUnionPath() {
        refreshUnionPath(true);
    }

    private void refreshUnionPath(boolean z) {
        float[] fArr = new float[9];
        this.mMatrix.getValues(fArr);
        float f = fArr[0];
        if (this.isCropScale && this.mSpannedScale != 1.0f && !this.isMixRectangular) {
            Matrix matrix = new Matrix();
            float f2 = this.mSpannedScale;
            matrix.postScale(f2, f2, this.mSelectCenter.x, this.mSelectCenter.y);
            matrix.postTranslate(fArr[2], fArr[5]);
            matrix.mapPoints(this.mSelectData, this.mData);
        } else {
            this.mMatrix.mapPoints(this.mSelectData, this.mData);
        }
        if (f == 1.0f && z) {
            PointF pointF = this.mSelectCenter;
            float[] fArr2 = this.mSelectData;
            pointF.set(fArr2[8], fArr2[9]);
        }
        this.mSelInfo.getCenter().set(this.mSelectCenter.x, this.mSelectCenter.y);
        Path path = new Path();
        path.reset();
        float[] fArr3 = this.mSelectData;
        path.moveTo(fArr3[0], fArr3[1]);
        float[] fArr4 = this.mSelectData;
        path.lineTo(fArr4[2], fArr4[3]);
        float[] fArr5 = this.mSelectData;
        path.lineTo(fArr5[4], fArr5[5]);
        float[] fArr6 = this.mSelectData;
        path.lineTo(fArr6[6], fArr6[7]);
        float[] fArr7 = this.mSelectData;
        path.lineTo(fArr7[0], fArr7[1]);
        path.computeBounds(this.mSelRectFNoRotate, false);
        if (z) {
            float[] fArr8 = this.mSelectData;
            this.mData = Arrays.copyOf(fArr8, fArr8.length);
            if (this.mTotalAngle != 0.0f) {
                Matrix matrix2 = new Matrix();
                matrix2.setRotate(this.mTotalAngle, this.mSelectCenter.x, this.mSelectCenter.y);
                matrix2.mapPoints(this.mSelectData, this.mData);
            }
            this.mSelInfo.setSelectData(this.mSelectData);
            this.mMatrix.reset();
        }
    }

    public boolean isSpannedSelectLimit() {
        if (this.hadDown) {
            return (this.mSpannedSelectObject == null || (this.mSelInfo.getSelectTypeMask() & SpannedSelectInfo.SELECT_ACTION_SCALE) != SpannedSelectInfo.SELECT_ACTION_SCALE || this.mSelInfo.getTotalScale() == 0.0f || checkScaleLimit(this.mSpannedScale) == this.mSpannedScale) ? false : true;
        }
        return checkScaleLimit(this.mSpannedScale) != this.mSpannedScale;
    }

    private float checkScaleLimit(float f) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null && iSpannedSelectObject.getSelectedList().size() != 0) {
            float fMin = 1.0f;
            float fMax = 1.0f;
            for (SpannedData spannedData : this.mSpannedSelectObject.getSelectedList()) {
                float fWidth = spannedData.getRatioRectF().width();
                if (fWidth < 150.0f) {
                    fWidth = Math.max(fWidth, spannedData.getRatioRectF().width());
                }
                fMin = Math.min(fMin, 150.0f / fWidth);
                fMax = Math.max(fMax, (float) Math.sqrt(293601280 / (((spannedData.getRectF().width() * 18.0f) * spannedData.getRectF().height()) * 18.0f)));
            }
            if (f < fMin) {
                return fMin;
            }
            if (f > fMax) {
                return fMax;
            }
        }
        return f;
    }

    public void setSelectAction(int i) {
        this.mSelInfo.setSelectType(i);
    }

    public RectF getUnionSelectRectF() {
        RectF rectF = new RectF();
        Path selectPath = this.mSelInfo.getSelectPath();
        if (selectPath != null) {
            selectPath.computeBounds(rectF, false);
        }
        return rectF;
    }

    public RectF getUnionSelectRectFNoRotate() {
        return this.mSelRectFNoRotate;
    }

    public float getTotalAngle() {
        return this.mTotalAngle;
    }

    public void onSelectCount(int i) {
        if (this.mSpanEditListener != null) {
            this.mSpanEditListener.onSelectCount(i, UUID.randomUUID().toString());
        }
    }

    private void doUnionPointSelect(float f, float f2) {
        if (this.mSpanEditListener != null) {
            this.mSpanEditListener.onSelectCount(2, UUID.randomUUID().toString());
        }
        this.mSelectedFinish = false;
        this.mHadPointSelected = true;
        this.mSpannedLayout.getSpannedLayoutModel().getSpannedEditOperator().doPointSelect(f, f2);
    }

    private void doUnionPathSelect() {
        if (this.mSpanEditListener != null) {
            this.mSpanEditListener.onSelectCount(2, UUID.randomUUID().toString());
        }
        this.mSelectedFinish = false;
        this.mHadPointSelected = false;
        this.mSpannedLayout.getSpannedLayoutModel().getSpannedEditOperator().doPathSelect(this.mSelectPath);
    }

    public void doPointSelect(List<TouchPoint> list) {
        doPointSelect(list, false);
    }

    public void doPointSelect(List<TouchPoint> list, boolean z) {
        ISpannedEditOperator iSpannedEditOperator;
        this.mSelectPath.reset();
        if (this.mSpanEditListener != null) {
            this.mSpanEditListener.onSelectCount(2, UUID.randomUUID().toString());
        }
        if (list.size() > 1) {
            this.mSelectPath.moveTo(list.get(0).getX(), list.get(0).getY());
            for (int i = 1; i < list.size(); i++) {
                this.mSelectPath.quadTo((list.get(i).getX() + this.mPreX) / 2.0f, (list.get(i).getY() + this.mPreY) / 2.0f, list.get(i).getX(), list.get(i).getY());
                this.mPreX = list.get(i).getX();
                this.mPreY = list.get(i).getY();
            }
            ISpannedEditOperator iSpannedEditOperator2 = this.mSpannedEditOperator;
            if (iSpannedEditOperator2 != null) {
                iSpannedEditOperator2.doPathSelect(this.mSelectPath, z);
            }
        } else if (list.size() == 1 && (iSpannedEditOperator = this.mSpannedEditOperator) != null) {
            iSpannedEditOperator.doPointSelect(list.get(0).getX(), list.get(0).getY(), z);
        }
        this.mSelectedFinish = false;
    }

    public boolean isSelectMode() {
        return this.isLassoPen;
    }

    public Path getUnionSelectPath() {
        Path path = new Path();
        float[] fArr = this.mSelectData;
        path.moveTo(fArr[0], fArr[1]);
        float[] fArr2 = this.mSelectData;
        path.lineTo(fArr2[2], fArr2[3]);
        float[] fArr3 = this.mSelectData;
        path.lineTo(fArr3[4], fArr3[5]);
        float[] fArr4 = this.mSelectData;
        path.lineTo(fArr4[6], fArr4[7]);
        float[] fArr5 = this.mSelectData;
        path.lineTo(fArr5[0], fArr5[1]);
        path.close();
        return path;
    }

    public float[] getSelectData() {
        return this.mSelectData;
    }

    public void externalSelected(boolean z) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.externalSelected(z);
        }
    }

    public PointF doSelectedCopyAndPast(float f, float f2) {
        if (this.mSpannedEditOperator != null && this.mSpannedSelectObject != null) {
            PointF pointF = new PointF(this.mSpannedSelectObject.getSelectRectF().centerX(), this.mSpannedSelectObject.getSelectRectF().centerY());
            this.mSpannedEditOperator.doCopyAndPast(pointF.x + f, pointF.y + f2);
            return new PointF(pointF.x + f, pointF.y + f2);
        }
        return new PointF();
    }

    public void doSelectedDelete() {
        doSelectedDelete(false);
    }

    public void doSelectedDelete(boolean z) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.doDelete(z);
        }
        finishSelect();
    }

    public void doSelectedDuplicate(boolean z) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.doCopy(z);
        }
    }

    public void doSelectedCut(boolean z) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.doCut(z);
        }
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.refreshSpan();
        }
        finishSelect();
    }

    public void doPast(List<SpannedData> list, float f, float f2) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator == null || list == null) {
            return;
        }
        iSpannedEditOperator.doPast(list, f, f2);
    }

    public void resetSelectViewData() {
        this.mSelectedFinish = false;
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            this.mSelectedFinish = true;
            iSpannedSelectObject.resetSelectViewData();
        }
    }

    public void getSelectBitmap(Bitmap bitmap, RectF rectF, int i) {
        getSelectBitmap(bitmap, rectF, i, false);
    }

    public void getSelectBitmap(Bitmap bitmap, RectF rectF, int i, boolean z) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.getSelectBitmap(bitmap, rectF, i, z);
        }
    }

    public void getSelectAndMaskBitmap(Bitmap bitmap, Bitmap bitmap2, RectF rectF, int i, int i2, boolean z) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.getSelectAndMaskBitmap(bitmap, bitmap2, rectF, i, i2, z);
        }
    }

    public List<SpannedData> getAllSpanByRectF(RectF rectF) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator != null) {
            return iSpannedEditOperator.getAllSpanDataList(rectF);
        }
        return new ArrayList();
    }

    public void getBitmapByRectF(Bitmap bitmap, RectF rectF, int i, int i2) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator != null) {
            new ScreenShortUtil().screenToBitmap(bitmap, rectF, iSpannedEditOperator.getAllSpanDataList(i2, rectF), this.mSpannedEditOperator.isDark(), i, this.screenRenderProvider);
        }
    }

    public RectF getSelectRectF() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            return iSpannedSelectObject.getSelectRectF();
        }
        return null;
    }

    public void setLimitTotalScale(float f, boolean z) {
        this.isNeedResetSelect = z;
        if (!z) {
            f = 1.0f;
        }
        this.mCurveLimitScale = f;
    }

    public float calCurTotalScale(float f, float f2) {
        return ((float) Math.sqrt(Math.pow(f - this.mSelectCenter.x, 2.0d) + Math.pow(f2 - this.mSelectCenter.y, 2.0d))) / this.mDownDistance;
    }

    public void doPointSelect(float f, float f2, int i) {
        ISpannedEditOperator iSpannedEditOperator = this.mSpannedEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.doPointSelect(f, f2, i);
        }
    }

    public boolean isHadPointSelected() {
        return this.mSpannedSelectObject != null && this.mHadPointSelected;
    }

    public void onDrawSelect(Canvas canvas) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || !this.mSelectedFinish) {
            return;
        }
        iSpannedSelectObject.onDraw(canvas);
    }

    public void onDrawSelectText(Canvas canvas) {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject == null || !this.mSelectedFinish) {
            return;
        }
        iSpannedSelectObject.onDrawText(canvas);
    }

    public List<SpannedData> getSelectedList() {
        if (this.mSpannedSelectObject != null) {
            return new ArrayList(this.mSpannedSelectObject.getSelectedList());
        }
        return new ArrayList();
    }

    public boolean hadSelectedSingleImage() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        return iSpannedSelectObject != null && iSpannedSelectObject.getSelectedList().size() == 1 && this.mSpannedSelectObject.getSelectedList().get(0).getType() == 7;
    }

    public boolean hadSelectedSingleText() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        return iSpannedSelectObject != null && iSpannedSelectObject.getSelectedList().size() == 1 && this.mSpannedSelectObject.getSelectedList().get(0).getType() == 11;
    }

    public void setSelectPopWindowShowing(boolean z) {
        this.isSelectPopWindowShowing = z;
    }

    public void setEnableClick(boolean z) {
        this.mIsEnableClick = z;
    }

    public void setEnableSelect(boolean z) {
        this.isEnableSelect = z;
    }

    public void forceRedraw() {
        ISpannedSelectObject iSpannedSelectObject = this.mSpannedSelectObject;
        if (iSpannedSelectObject != null) {
            iSpannedSelectObject.forceRedraw();
        }
    }
}
