package xc.richtext.api;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.appcompat.R;
import xc.engine.utils.TouchEventUtils;
import xc.richtext.data.SpannedData;
import xc.richtext.data.TextInfo;
import xc.richtext.data.bean.SpannedScaleInfo;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.ISpannedEditOperator;
import xc.richtext.operate.edit.ISpannedSelectObject;
import xc.richtext.operate.scale.IScaleOperator;
import xc.richtext.utils.SpannedUtils;
import xc.richtext.view.CsSpannedLayoutListener;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedEditModel {
    private boolean enableEdit;
    private boolean enableEditTransient;
    private float mDownX;
    private float mDownY;
    private ISpannedEditObject mEditObject;
    protected ISpannedEditOperator mEditOperator;
    protected OnSpannedEditListener mModeEditListener;
    private float mPrevX;
    private float mPrevY;
    protected IScaleOperator mScaleOperator;
    private final SpannedEditLayout mSpannedLayout;
    private final SpannedSelectModel mSpannedSelectModel;
    private TextPaint mTextPaint;
    public ScreenRenderProvider screenRenderProvider;
    private int mIdEditObjectNeedReset = -1;
    private final RectF mDelRectF = new RectF();
    private final RectF mScaleRectF = new RectF();
    private boolean hadDown = false;
    private boolean hadPointDown = false;
    private int mEditAction = 0;
    private int EDIT_ACTION_DISTANCE = 32;
    private int mLimitWidth = 72;
    private int mPageIndex = 0;
    private boolean enableSelect = false;

    public SpannedSelectModel getSpannedSelectMode() {
        return this.mSpannedSelectModel;
    }

    public SpannedEditModel(SpannedEditLayout spannedEditLayout, ScreenRenderProvider screenRenderProvider) {
        this.screenRenderProvider = screenRenderProvider;
        this.mSpannedLayout = spannedEditLayout;
        SpannedSelectModel spannedSelectModel = new SpannedSelectModel(spannedEditLayout, screenRenderProvider);
        this.mSpannedSelectModel = spannedSelectModel;
        spannedEditLayout.getSpannedLayoutModel().create();
        this.mEditOperator = spannedEditLayout.getSpannedLayoutModel().getSpannedEditOperator();
        this.mScaleOperator = spannedEditLayout.getSpannedLayoutModel().getScaleOperator();
        spannedSelectModel.setSpannedEngine(this.mEditOperator);
        spannedEditLayout.getSpannedLayoutModel().setCsSpannedListener(new CsSpannedLayoutListener() { // from class: xc.richtext.api.SpannedEditModel.1
            @Override // xc.richtext.view.CsSpannedLayoutListener
            public int textColorTransform(int i) {
                return i;
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onStepChanged(int i, int i2, boolean z) {
                if (SpannedEditModel.this.mModeEditListener != null) {
                    SpannedEditModel.this.mModeEditListener.onStepChanged(i, i2, z);
                }
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject) {
                SpannedEditModel.this.mSpannedSelectModel.onSpannedSelected(iSpannedSelectObject);
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean z) {
                SpannedEditModel.this.mSpannedSelectModel.onSpannedSelected(iSpannedSelectObject, z);
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onSpannedEdited(ISpannedEditObject iSpannedEditObject) {
                SpannedEditModel.this.mEditObject = iSpannedEditObject;
                if (SpannedEditModel.this.mEditObject != null) {
                    SpannedEditModel spannedEditModel = SpannedEditModel.this;
                    spannedEditModel.changedTextCursor(spannedEditModel.mEditObject.getEditData().getId());
                } else {
                    SpannedEditModel.this.enableEditTransient(false);
                }
                if (SpannedEditModel.this.mModeEditListener != null) {
                    SpannedEditModel.this.mModeEditListener.onSpannedEdited(iSpannedEditObject);
                }
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onCopyData(List<SpannedData> list) {
                if (SpannedEditModel.this.mModeEditListener != null) {
                    SpannedEditModel.this.mModeEditListener.onCopyData(list);
                }
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onCopyData(boolean z, List<SpannedData> list) {
                if (SpannedEditModel.this.mModeEditListener != null) {
                    SpannedEditModel.this.mModeEditListener.onCopyData(z, list);
                }
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public void onLoadFinish(boolean z) {
                if (SpannedEditModel.this.mModeEditListener != null) {
                    SpannedEditModel.this.mModeEditListener.onLoadFinish(z);
                }
            }

            @Override // xc.richtext.view.CsSpannedLayoutListener
            public boolean isEditAction() {
                return SpannedEditModel.this.mEditAction != 0;
            }
        });
    }

    public void changedTextCursor(int i) {
        SpannedData spannedData;
        if (i < 0) {
            return;
        }
        for (int i2 = 0; i2 < this.mSpannedLayout.getChildCount(); i2++) {
            View childAt = this.mSpannedLayout.getChildAt(i2);
            if (childAt != null && (childAt.getTag() instanceof SpannedData) && (spannedData = (SpannedData) childAt.getTag()) != null && spannedData.getId() == i && spannedData.getType() == 11) {
                EditText editText = (EditText) childAt;
                editText.setTextCursorDrawable(R.drawable.miuix_appcompat_text_cursor_light);
                editText.setHighlightColor(editText.getContext().getColor(R.color.miuix_appcompat_highlighted_text_light));
                editText.setTextSelectHandleLeft(R.drawable.miuix_appcompat_text_select_handle_left_light);
                editText.setTextSelectHandle(R.drawable.miuix_appcompat_text_select_handle_middle_light);
                editText.setTextSelectHandleRight(R.drawable.miuix_appcompat_text_select_handle_right_light);
                return;
            }
        }
    }

    public void setVisibleSize(float f, float f2) {
        IScaleOperator iScaleOperator = this.mScaleOperator;
        if (iScaleOperator != null) {
            iScaleOperator.setVisibleSize(f, f2);
        }
        checkLimitWidth();
    }

    public void setModeEditListener(OnSpannedEditListener onSpannedEditListener) {
        this.mModeEditListener = onSpannedEditListener;
        getSpannedSelectMode().setModeEditListener(onSpannedEditListener);
    }

    public void enableEditMode(boolean z) {
        this.enableEdit = z;
        if (!z && !this.enableEditTransient) {
            finishEdit();
        }
        if (this.enableEditTransient) {
            return;
        }
        this.mSpannedLayout.getSpannedLayoutModel().enableEditMode(z);
    }

    public boolean isEnableEdit() {
        return this.enableEdit;
    }

    public void enableEditTransient(boolean z) {
        this.enableEditTransient = z;
    }

    public boolean isEnableEditTransient() {
        return this.enableEditTransient;
    }

    public void setMenuRectF(RectF rectF, RectF rectF2) {
        if (!rectF.isEmpty() || !rectF2.isEmpty()) {
            this.EDIT_ACTION_DISTANCE = (int) Math.max(rectF.width() / 4.0f, rectF2.width() / 4.0f);
        }
        this.mDelRectF.set(rectF);
        this.mScaleRectF.set(rectF2);
    }

    public void setDispatchTouchEvent(MotionEvent motionEvent) {
        this.mSpannedLayout.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        if (!this.enableEdit && !this.enableEditTransient) {
            return false;
        }
        this.mEditAction = findEditAction(motionEvent.getX(), motionEvent.getY());
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enableEdit && !this.enableEditTransient) {
            return false;
        }
        doEditEvent(motionEvent);
        return true;
    }

    private void doEditEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.hadDown = true;
            this.hadPointDown = false;
            onDown(motionEvent.getX(), motionEvent.getY());
            return;
        }
        if (actionMasked == 1) {
            if (motionEvent.getPointerCount() == 1 && !this.hadPointDown) {
                onUp(motionEvent);
            }
            this.hadDown = false;
            this.hadPointDown = false;
            return;
        }
        if (actionMasked == 2) {
            if (motionEvent.getPointerCount() != 1 || this.hadPointDown) {
                return;
            }
            onMove(motionEvent.getX(), motionEvent.getY());
            return;
        }
        if (actionMasked == 3) {
            this.hadDown = false;
            onCancelEdit();
        } else if (actionMasked == 5) {
            this.hadPointDown = true;
            this.mSpannedLayout.invalidate();
        } else {
            if (actionMasked != 6) {
                return;
            }
            onCancelEdit();
        }
    }

    private void resetData() {
        clearPoint();
        this.hadDown = false;
        this.hadPointDown = false;
        this.mSpannedLayout.postInvalidate();
    }

    private void onDown(float f, float f2) {
        this.mPrevX = f;
        this.mDownX = f;
        this.mPrevY = f2;
        this.mDownY = f2;
        int i = this.mEditAction;
        if (i == 0) {
            if (this.enableSelect) {
                return;
            }
            finishEdit();
            OnSpannedEditListener onSpannedEditListener = this.mModeEditListener;
            if (onSpannedEditListener != null) {
                onSpannedEditListener.cancelSoftKeyboard();
                return;
            }
            return;
        }
        ISpannedEditObject iSpannedEditObject = this.mEditObject;
        if (iSpannedEditObject != null) {
            iSpannedEditObject.startAction(i);
        }
    }

    private void onMove(float f, float f2) {
        this.mPrevX = f;
        this.mPrevY = f2;
        if (this.hadDown) {
            int i = this.mEditAction;
            if (i != 0) {
                ISpannedEditObject iSpannedEditObject = this.mEditObject;
                if (iSpannedEditObject == null || i != 1) {
                    return;
                }
                iSpannedEditObject.doEditParam(f - this.mDownX);
                return;
            }
            this.mSpannedLayout.invalidate();
        }
    }

    private void onUp(MotionEvent motionEvent) {
        if (this.hadDown) {
            this.mPrevX = motionEvent.getX();
            this.mPrevY = motionEvent.getY();
            if (TouchEventUtils.isClickEvent(new PointF(this.mDownX, this.mDownY), motionEvent)) {
                if (this.mEditAction == 2) {
                    deleteText();
                }
                this.mEditAction = 0;
                this.mSpannedLayout.invalidate();
                return;
            }
            int i = this.mEditAction;
            if (i != 0) {
                ISpannedEditObject iSpannedEditObject = this.mEditObject;
                if (iSpannedEditObject != null) {
                    if (i == 1) {
                        iSpannedEditObject.doEditParam(this.mPrevX - this.mDownX);
                    }
                    this.mEditObject.stopAction();
                }
                this.mEditAction = 0;
                this.mSpannedLayout.invalidate();
            }
            clearPoint();
        }
    }

    public void onCancelEdit() {
        if (this.mEditAction != 0) {
            ISpannedEditObject iSpannedEditObject = this.mEditObject;
            if (iSpannedEditObject != null) {
                iSpannedEditObject.stopAction();
            }
            this.mEditAction = 0;
        }
        resetData();
    }

    public int getEditAction() {
        return this.mEditAction;
    }

    private int findEditAction(float f, float f2) {
        if (this.mEditObject == null) {
            return 0;
        }
        int i = this.mDelRectF.contains(f, f2) ? 2 : 0;
        if (this.mScaleRectF.contains(f, f2)) {
            return 1;
        }
        return i;
    }

    public void addText(RectF rectF, String str) {
        if (this.mEditOperator != null) {
            if (rectF.width() >= this.EDIT_ACTION_DISTANCE || rectF.height() >= this.EDIT_ACTION_DISTANCE) {
                checkLimitWidth();
                if (rectF.width() < this.mLimitWidth) {
                    rectF.right = rectF.left + this.mLimitWidth;
                }
                finishEdit();
                doEditClick(this.mEditOperator.addText(rectF, str));
            }
        }
    }

    public void addText(TextInfo textInfo) {
        finishEdit();
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            SpannedData spannedDataAddText = iSpannedEditOperator.addText(textInfo);
            this.mSpannedLayout.getSpannedEditModel().enableEditTransient(true);
            this.mEditOperator.doEditClick(spannedDataAddText.getId(), spannedDataAddText.getDrawRectF(this.screenRenderProvider).centerX(), spannedDataAddText.getDrawRectF(this.screenRenderProvider).centerY());
        }
    }

    private void checkLimitWidth() {
        if (this.mTextPaint == null) {
            this.mTextPaint = new TextPaint();
        }
        this.mTextPaint.setTextSize(34.0f);
        int iCeil = (int) Math.ceil(this.mTextPaint.measureText("汉字"));
        this.mLimitWidth = iCeil;
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.setMinLimitSize(iCeil, iCeil);
        }
    }

    private void doEditClick(SpannedData spannedData) {
        if (spannedData == null || this.mEditOperator == null) {
            return;
        }
        enableEditTransient(true);
        RectF drawRectF = spannedData.getDrawRectF(this.screenRenderProvider);
        this.mEditOperator.doEditClick(spannedData.getId(), drawRectF.centerX(), drawRectF.centerY());
    }

    public void setHightText(String str, int i, int i2, OnSpannedHightListener onSpannedHightListener) {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.setHightText(str, i, i2, onSpannedHightListener);
        }
    }

    protected void deleteText() {
        ISpannedEditObject iSpannedEditObject = this.mEditObject;
        if (iSpannedEditObject != null) {
            iSpannedEditObject.delete();
        }
        if (this.enableEditTransient) {
            this.enableEditTransient = false;
            enableEditMode(false);
        }
    }

    public void dispatchDraw(Canvas canvas) {
        if (this.enableEdit && !this.enableEditTransient && this.mEditObject == null) {
            drawAddPath(canvas);
        }
        if (this.mEditObject != null) {
            drawEditRectF(canvas);
        }
    }

    private void drawAddPath(Canvas canvas) {
        if (this.enableEdit) {
            float f = this.mDownX;
            float f2 = this.mPrevX;
            if (f == f2 && this.mDownY == this.mPrevY) {
                return;
            }
            float[] fArr = new float[12];
            fArr[0] = f;
            fArr[1] = this.mDownY;
            fArr[2] = f2;
            fArr[3] = this.mPrevY;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-16776961);
            paint.setStrokeWidth(3.0f);
            paint.setPathEffect(new DashPathEffect(new float[]{20.0f, 10.0f}, 0.0f));
            canvas.drawLine(fArr[0], fArr[1], fArr[2], fArr[3], paint);
        }
    }

    private void drawEditRectF(Canvas canvas) {
        ISpannedEditObject iSpannedEditObject = this.mEditObject;
        if (iSpannedEditObject == null || iSpannedEditObject.getEditData() == null || this.mEditAction != 0) {
            return;
        }
        SpannedData editData = this.mEditObject.getEditData();
        RectF editRectF = this.mEditObject.getEditRectF();
        float f = this.EDIT_ACTION_DISTANCE;
        editRectF.set(editRectF.left - f, editRectF.top - f, editRectF.right + f, editRectF.bottom + f);
        float[] fArrChangeFloatsByRotate = SpannedUtils.changeFloatsByRotate(editRectF, editData.getDegrees(), editRectF.centerX(), editRectF.centerY());
        Path path = new Path();
        path.moveTo(fArrChangeFloatsByRotate[0], fArrChangeFloatsByRotate[1]);
        path.lineTo(fArrChangeFloatsByRotate[2], fArrChangeFloatsByRotate[3]);
        path.lineTo(fArrChangeFloatsByRotate[4], fArrChangeFloatsByRotate[5]);
        path.lineTo(fArrChangeFloatsByRotate[6], fArrChangeFloatsByRotate[7]);
        path.lineTo(fArrChangeFloatsByRotate[0], fArrChangeFloatsByRotate[1]);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.parseColor("#0D84FF"));
        paint.setStrokeWidth(3.0f);
        paint.setPathEffect(new DashPathEffect(new float[]{20.0f, 10.0f}, 0.0f));
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float f2 = fArrChangeFloatsByRotate[4];
        float f3 = fArrChangeFloatsByRotate[3];
        canvas.drawCircle(f2, f3 + ((fArrChangeFloatsByRotate[5] - f3) / 2.0f), 10.0f, paint);
        float f4 = fArrChangeFloatsByRotate[4];
        float f5 = fArrChangeFloatsByRotate[3];
        float f6 = fArrChangeFloatsByRotate[5];
        setMenuRectF(new RectF(), new RectF(f4 - 25.0f, (((f6 - f5) / 2.0f) + f5) - 25.0f, f4 + 25.0f, f5 + ((f6 - f5) / 2.0f) + 25.0f));
    }

    public void addBitmap(String str, RectF rectF, float f) {
        addBitmap(str, rectF, f, false);
    }

    public void addBitmap(String str, RectF rectF, float f, boolean z) {
        SpannedData spannedDataAddBitmap;
        finishEdit();
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator == null || this.mModeEditListener == null || (spannedDataAddBitmap = iSpannedEditOperator.addBitmap(str, rectF, f, z)) == null) {
            return;
        }
        getSpannedSelectMode().onSelectCount(1);
        getSpannedSelectMode().doPointSelect(spannedDataAddBitmap.getDrawRectF(this.screenRenderProvider).centerX(), spannedDataAddBitmap.getDrawRectF(this.screenRenderProvider).centerY(), 7);
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.selected();
        }
    }

    public void setTextBgColor(int i) {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.setTextBgColor(i);
        }
    }

    public void finishEdit() {
        ISpannedEditObject iSpannedEditObject = this.mEditObject;
        if (iSpannedEditObject != null) {
            this.mEditAction = 0;
            iSpannedEditObject.finishEdit();
            this.mEditObject = null;
            this.enableEditTransient = false;
        }
    }

    public void clearAll() {
        finishEdit();
        getSpannedSelectMode().finishSelect();
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.clearAll();
        }
    }

    public void clearPage(int i, boolean z) {
        finishEdit();
        getSpannedSelectMode().finishSelect();
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator == null || !z) {
            return;
        }
        iSpannedEditOperator.clearPage(i);
    }

    public void removeSpannedItemView() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mSpannedLayout.getChildCount(); i++) {
            if (this.mSpannedLayout.getChildAt(i).getTag() instanceof SpannedData) {
                arrayList.add(this.mSpannedLayout.getChildAt(i));
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mSpannedLayout.removeView((View) it.next());
        }
    }

    public void undo(boolean z) {
        ISpannedEditOperator iSpannedEditOperator;
        ISpannedEditOperator iSpannedEditOperator2 = this.mEditOperator;
        if (iSpannedEditOperator2 == null || !iSpannedEditOperator2.canUndo() || (iSpannedEditOperator = this.mEditOperator) == null) {
            return;
        }
        iSpannedEditOperator.undo(z);
    }

    public void redo(boolean z) {
        ISpannedEditOperator iSpannedEditOperator;
        ISpannedEditOperator iSpannedEditOperator2 = this.mEditOperator;
        if (iSpannedEditOperator2 == null || !iSpannedEditOperator2.canRedo() || (iSpannedEditOperator = this.mEditOperator) == null) {
            return;
        }
        iSpannedEditOperator.redo(z);
    }

    public void forceChildLayout(int i, int i2, int i3, int i4) {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.forceChildLayout(false, i, i2, i3, i4);
        }
    }

    public void setCurrentPageNumber(int i) {
        this.mPageIndex = i;
    }

    public boolean isTextEditing() {
        return this.mEditObject != null;
    }

    public void enableSelectMode(boolean z) {
        this.enableSelect = z;
    }

    public RectF getEditRect() {
        ISpannedEditObject iSpannedEditObject = this.mEditObject;
        if (iSpannedEditObject != null) {
            SpannedData editData = iSpannedEditObject.getEditData();
            if (editData == null || editData.getDrawRectF(this.screenRenderProvider) == null) {
                return new RectF();
            }
            return new RectF(editData.getDrawRectF(this.screenRenderProvider));
        }
        return new RectF();
    }

    public float getEditTextCursorHeight() {
        ISpannedEditOperator iSpannedEditOperator;
        if (this.mEditObject == null || (iSpannedEditOperator = this.mEditOperator) == null) {
            return 0.0f;
        }
        return iSpannedEditOperator.getEditTextCursorHeight();
    }

    public void resetViewData() {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.resetViewData();
        }
    }

    public boolean isInEditAction() {
        return this.mEditAction == 1;
    }

    public void forceReDraw() {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            iSpannedEditOperator.forceReDraw();
        }
    }

    public SpannedScaleInfo getScaleInfo() {
        IScaleOperator iScaleOperator = this.mScaleOperator;
        if (iScaleOperator == null) {
            return null;
        }
        return iScaleOperator.getSpannedScaleInfo();
    }

    public void doScaleReal(float f, float f2, float f3, float f4, float f5) {
        IScaleOperator iScaleOperator = this.mScaleOperator;
        if (iScaleOperator != null) {
            iScaleOperator.doScaleReal(f, new PointF(f2, f3), new PointF(f4, f5));
        }
    }

    public void updateSpannedScaleInfo(float f, float f2, float f3) {
        IScaleOperator iScaleOperator = this.mScaleOperator;
        if (iScaleOperator != null) {
            iScaleOperator.updateSpannedScaleInfo(f, new PointF(f2, f3));
        }
    }

    private void clearPoint() {
        this.mDownX = 0.0f;
        this.mPrevX = 0.0f;
        this.mDownY = 0.0f;
        this.mPrevY = 0.0f;
    }

    public void setUnLimit(boolean z) {
        IScaleOperator iScaleOperator = this.mScaleOperator;
        if (iScaleOperator != null) {
            iScaleOperator.setUnLimit(z);
        }
    }

    public RectF getSpanAreaInCanvas(RectF rectF) {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            return iSpannedEditOperator.getSpanAreaInCanvas(rectF);
        }
        return new RectF();
    }

    public RectF getSpanAreaInCanvasWithoutRadio(RectF rectF) {
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator != null) {
            return iSpannedEditOperator.getSpanAreaInCanvasWithoutRadio(rectF);
        }
        return new RectF();
    }

    public void release() {
        this.screenRenderProvider = null;
        SpannedSelectModel spannedSelectModel = this.mSpannedSelectModel;
        if (spannedSelectModel != null) {
            spannedSelectModel.screenRenderProvider = null;
        }
    }

    public void moveEditText(int i) {
        ISpannedEditObject iSpannedEditObject;
        if (this.mEditOperator == null || (iSpannedEditObject = this.mEditObject) == null) {
            return;
        }
        int id = iSpannedEditObject.getEditData().getId();
        this.mIdEditObjectNeedReset = id;
        doEditClick(this.mEditOperator.moveEditText(id, i));
    }

    public void resetMovedEditText(int i) {
        int i2;
        ISpannedEditOperator iSpannedEditOperator = this.mEditOperator;
        if (iSpannedEditOperator == null || (i2 = this.mIdEditObjectNeedReset) < 0) {
            return;
        }
        iSpannedEditOperator.moveEditText(i2, i);
        this.mIdEditObjectNeedReset = -1;
    }
}
