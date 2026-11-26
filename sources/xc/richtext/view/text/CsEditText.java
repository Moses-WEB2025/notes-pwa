package xc.richtext.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.SpannedEditObject;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: loaded from: classes3.dex */
public class CsEditText extends AppCompatEditText {
    public CsEditViewModel mCsEditViewModel;
    private ScreenRenderProvider screenRenderProvider;

    public CsEditText(Context context, ScreenRenderProvider screenRenderProvider) {
        this(context, null, 0, screenRenderProvider);
    }

    public CsEditText(Context context, AttributeSet attributeSet, int i, ScreenRenderProvider screenRenderProvider) {
        super(context, attributeSet, i);
        this.mCsEditViewModel = new CsEditViewModel(this);
        this.screenRenderProvider = screenRenderProvider;
    }

    public CsEditViewModel getModel() {
        return this.mCsEditViewModel;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        CsEditViewModel csEditViewModel = this.mCsEditViewModel;
        if ((csEditViewModel == null || csEditViewModel.isEditObjectExisted()) && isEnabled()) {
            super.onDraw(canvas);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onFocusChanged(boolean z, int i, Rect rect) {
        SpannedEditOperator spannedEditOperator;
        super.onFocusChanged(z, i, rect);
        CsEditViewModel csEditViewModel = this.mCsEditViewModel;
        if (csEditViewModel != null) {
            EditTextBindContext editTextBindContext = csEditViewModel.mEditTextBindContext;
            if (editTextBindContext != null) {
                SpannedLayoutModel spannedLayoutModel = editTextBindContext.getSpannedLayoutModel();
                if (editTextBindContext.getCsEditText() != null) {
                    SpannedData spannedData = (SpannedData) editTextBindContext.getCsEditText().getTag();
                    if (z) {
                        if (spannedLayoutModel.mSpannedEditObject != null) {
                            spannedLayoutModel.removeOtherEditText(-1);
                        }
                        if (spannedLayoutModel.getEngineBindContext() != null && (spannedEditOperator = spannedLayoutModel.mSpannedEditOperator) != null && spannedEditOperator.mSpannedLayoutChanger != null) {
                            SpannedEditObject spannedEditObject = new SpannedEditObject(spannedEditOperator.mEngineBindContext, editTextBindContext.getCsEditText(), spannedData);
                            spannedEditOperator.mSpannedEditObject = spannedEditObject;
                            if (spannedData.o) {
                                spannedEditObject.mLimitWidth = spannedEditOperator.mMinLimitSize;
                            }
                            spannedEditOperator.mSpannedLayoutChanger.onSpannedEdited(spannedEditObject);
                        }
                        spannedLayoutModel.mSpannedEditLayout.bringChildToFront(editTextBindContext.getCsEditText());
                        spannedLayoutModel.getEngineBindContext().getSpannedMap(getPageIndex(spannedData.mRatioRectF)).refreshData(spannedData);
                        spannedLayoutModel.mDegrees = spannedData.mDegrees;
                        if (spannedData.mDegrees != 0.0f) {
                            spannedData.mDegrees = 0.0f;
                            editTextBindContext.getCsEditText().setRotation(0.0f);
                        }
                    } else {
                        spannedLayoutModel.bringTextToFront(new int[]{spannedData.mId});
                        if (spannedLayoutModel.mFocusId == spannedData.mId) {
                            float f = spannedLayoutModel.mDegrees;
                            if (f != 0.0f) {
                                spannedData.mDegrees = f;
                                editTextBindContext.getCsEditText().setRotation(f);
                                spannedLayoutModel.mSpannedEditLayout.invalidate();
                            }
                            spannedLayoutModel.mFocusId = -1;
                            spannedLayoutModel.mDegrees = 0.0f;
                        }
                    }
                }
            }
            CsEditViewModel csEditViewModel2 = this.mCsEditViewModel;
            if (csEditViewModel2 == null || csEditViewModel2.mEditTextBindContext == null) {
                return;
            }
            SpannedData spannedData2 = this.mCsEditViewModel.mSpannedData;
            this.mCsEditViewModel.mEditTextBindContext.getSpannedLayoutModel().mFocusId = z ? spannedData2 == null ? -1 : spannedData2.mId : -1;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CsEditViewModel csEditViewModel;
        if (motionEvent.getAction() != 0 || (csEditViewModel = this.mCsEditViewModel) == null || (csEditViewModel.c() && isEnabled())) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    @Override // android.view.View
    public void setRotation(float f) {
        super.setRotation(f);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CsEditViewModel csEditViewModel = this.mCsEditViewModel;
        if (csEditViewModel != null) {
            csEditViewModel.release();
        }
        this.mCsEditViewModel = null;
        this.screenRenderProvider = null;
    }

    private int getPageIndex(RectF rectF) {
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider == null) {
            return 0;
        }
        int pageIndexByWorldSpacePoint = screenRenderProvider.getPageIndexByWorldSpacePoint(rectF.left, rectF.top);
        Log.d("CsEditText", "getPageIndex:" + pageIndexByWorldSpacePoint + " rectF:" + rectF);
        return pageIndexByWorldSpacePoint;
    }
}
