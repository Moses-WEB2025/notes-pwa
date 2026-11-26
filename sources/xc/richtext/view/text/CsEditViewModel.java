package xc.richtext.view.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.step.AddTextStep;
import xc.richtext.operate.step.LocalStepManager;
import xc.richtext.operate.step.ReduceTextStep;
import xc.richtext.utils.TextLayoutUtil;
import xc.richtext.view.CsSpannedLayoutListener;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: loaded from: classes3.dex */
public class CsEditViewModel {
    public CsEditText mCsEditText;
    public EditTextBindContext mEditTextBindContext;
    public EngineBindContext mEngineBindContext;
    public Paint mPaint;
    public SpannedData mSpannedData;
    public StaticLayout mStaticLayout = null;
    public List<RectF> mRectFList = new ArrayList();
    public boolean isIgnoreStep = true;

    public class CsTextWatcher implements TextWatcher {
        public CharSequence mCharSequence = null;

        public CsTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (CsEditViewModel.this.isIgnoreStep || !(charSequence instanceof Editable)) {
                return;
            }
            CsEditViewModel.this.mSpannedData.o = false;
            this.mCharSequence = charSequence.subSequence(0, charSequence.length());
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (CsEditViewModel.this.isIgnoreStep || !(charSequence instanceof Editable)) {
                return;
            }
            LocalStepManager localStepManager = CsEditViewModel.this.mEngineBindContext.getLocalStepManager();
            if (i2 == i3) {
                if (i2 != 0) {
                    int i4 = i + i2;
                    if (charSequence.subSequence(i, i4).toString().equals(this.mCharSequence.subSequence(i, i4).toString())) {
                        return;
                    }
                    localStepManager.addStep(new ReduceTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i2, this.mCharSequence));
                    localStepManager.addStep(new AddTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i3, charSequence));
                    return;
                }
                return;
            }
            if (i2 < i3) {
                if (i2 == 0) {
                    localStepManager.addStep(new AddTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i3, charSequence));
                    return;
                }
                int i5 = i + i2;
                if (charSequence.subSequence(i, i5).toString().equals(this.mCharSequence.subSequence(i, i5).toString())) {
                    localStepManager.addStep(new AddTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i5, i3 - i2, charSequence));
                    return;
                } else {
                    localStepManager.addStep(new ReduceTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i2, this.mCharSequence));
                    localStepManager.addStep(new AddTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i3, charSequence));
                    return;
                }
            }
            if (i3 == 0) {
                localStepManager.addStep(new ReduceTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i2, this.mCharSequence));
                return;
            }
            int i6 = i + i3;
            if (charSequence.subSequence(i, i6).toString().equals(this.mCharSequence.subSequence(i, i6).toString())) {
                localStepManager.addStep(new ReduceTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i6, i2 - i3, this.mCharSequence));
            } else {
                localStepManager.addStep(new ReduceTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i2, this.mCharSequence));
                localStepManager.addStep(new AddTextStep(CsEditViewModel.this.mEngineBindContext, CsEditViewModel.this.mSpannedData, i, i3, charSequence));
            }
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            ((SpannedData) CsEditViewModel.this.mCsEditText.getTag()).saveRectChangeLatch = new CountDownLatch(1);
            final MovementMethod movementMethod = CsEditViewModel.this.mCsEditText.getMovementMethod();
            CsEditViewModel.this.mCsEditText.setMovementMethod(null);
            CsEditViewModel.this.updateTextLayout();
            CsEditViewModel.this.mCsEditText.postDelayed(new Runnable() { // from class: xc.richtext.view.text.CsEditViewModel$CsTextWatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4670x3a3c16bb(movementMethod);
                }
            }, CsEditViewModel.this.isIgnoreStep ? 200L : 1L);
            this.mCharSequence = null;
            CsEditViewModel.this.isIgnoreStep = false;
        }

        /* JADX INFO: renamed from: lambda$afterTextChanged$0$xc-richtext-view-text-CsEditViewModel$CsTextWatcher, reason: not valid java name */
        /* synthetic */ void m4670x3a3c16bb(MovementMethod movementMethod) {
            if (CsEditViewModel.this.mCsEditText != null) {
                CsEditViewModel.this.mCsEditText.setMovementMethod(movementMethod);
            }
        }
    }

    public CsEditViewModel(CsEditText csEditText) {
        this.mCsEditText = csEditText;
        csEditText.addTextChangedListener(new CsTextWatcher());
        this.mCsEditText.setPadding(0, 0, 0, 0);
        this.mCsEditText.setGravity(8388659);
        this.mCsEditText.setEnabled(true);
        this.mCsEditText.setFocusableInTouchMode(true);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextLayoutRunable() {
        CsEditText csEditText = this.mCsEditText;
        if (csEditText == null) {
            return;
        }
        SpannedData spannedData = (SpannedData) csEditText.getTag();
        Editable text = this.mCsEditText.getText();
        spannedData.mText = text == null ? "" : text.toString();
        RectF rectF = spannedData.mRatioRectF;
        float fHeight = rectF.height();
        if (!TextUtils.isEmpty(this.mCsEditText.getText())) {
            fHeight = getHeight(spannedData) + this.mCsEditText.getPaddingTop() + this.mCsEditText.getPaddingBottom();
        }
        RectF rectF2 = spannedData.mRatioRectF;
        rectF.bottom = rectF2.top + fHeight;
        float fWidth = rectF2.width();
        if (this.mStaticLayout != null) {
            int i = 0;
            while (true) {
                if (i < this.mStaticLayout.getLineCount()) {
                    if (this.mStaticLayout.getLineEnd(i) > this.mStaticLayout.getLineStart(i) + 1) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    new TextPaint().setTextSize(spannedData.mTextSize);
                    RectF drawRectF = spannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider);
                    float width = (((int) drawRectF.right) - ((int) drawRectF.left)) / this.mStaticLayout.getWidth();
                    float width2 = this.mStaticLayout.getWidth() * width;
                    int lineCount = this.mStaticLayout.getLineCount();
                    float fMax = width2;
                    for (int i2 = 0; i2 < lineCount; i2++) {
                        fMax = Math.max(fMax, this.mStaticLayout.getLineWidth(i2) * width);
                    }
                    if (width2 >= fMax) {
                        break;
                    }
                    fWidth = fMax / this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
                    break;
                }
            }
        }
        if (rectF.width() < fWidth) {
            rectF.right = rectF.left + fWidth;
        }
        spannedData.mRatioRectF.set(rectF);
        spannedData.setRectF(rectF);
        spannedData.saveRectChangeLatch.countDown();
        refreshStaticLayout();
        CsEditText csEditText2 = this.mEditTextBindContext.getCsEditText();
        RectF drawRectF2 = ((SpannedData) csEditText2.getTag()).getDrawRectF(this.mEngineBindContext.screenRenderProvider);
        ViewGroup.LayoutParams layoutParams = csEditText2.getLayoutParams();
        layoutParams.width = (int) drawRectF2.width();
        layoutParams.height = -2;
        csEditText2.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(this.mCsEditText.getText()) || this.mSpannedData.o || this.mEngineBindContext.mSpannedEditOperator.mSpannedEditObject != null || this.mCsEditText.getParent() == null) {
            return;
        }
        ((ViewGroup) this.mCsEditText.getParent()).removeView(this.mCsEditText);
    }

    public final int getHeight(SpannedData spannedData) {
        refreshStaticLayout();
        float cameraScale = this.mEditTextBindContext.getSpannedLayoutModel().getCameraScale();
        if (cameraScale == 0.0f) {
            cameraScale = this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
        }
        if (this.mStaticLayout == null) {
            Layout layout = this.mCsEditText.getLayout();
            if (layout == null) {
                return 0;
            }
            return (int) Math.ceil(layout.getHeight() / cameraScale);
        }
        RectF drawRectF = spannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider);
        return (int) ((this.mStaticLayout.getHeight() * (new Rect((int) drawRectF.left, (int) drawRectF.top, (int) drawRectF.right, (int) drawRectF.bottom).width() / this.mStaticLayout.getWidth())) / cameraScale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextLayout() {
        if (this.mEditTextBindContext != null) {
            this.mCsEditText.post(new Runnable() { // from class: xc.richtext.view.text.CsEditViewModel$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.updateTextLayoutRunable();
                }
            });
        }
    }

    public boolean c() {
        EditTextBindContext editTextBindContext = this.mEditTextBindContext;
        if (editTextBindContext != null) {
            CsSpannedLayoutListener csSpannedLayoutListener = editTextBindContext.getSpannedLayoutModel().mSpannedLayoutListener;
            boolean z = csSpannedLayoutListener != null && csSpannedLayoutListener.isEditAction();
            SpannedLayoutModel spannedLayoutModel = this.mEditTextBindContext.getSpannedLayoutModel();
            if ((spannedLayoutModel.isEnableEdit || spannedLayoutModel.g) && !z) {
                return true;
            }
        }
        return false;
    }

    public boolean isEditObjectExisted() {
        EditTextBindContext editTextBindContext = this.mEditTextBindContext;
        return (editTextBindContext == null || editTextBindContext.getSpannedLayoutModel().mSpannedEditObject == null) ? false : true;
    }

    public void refreshStaticLayout() {
        StaticLayout staticLayout = this.mStaticLayout;
        if (staticLayout != null) {
            TextUtils.equals(staticLayout.getText(), this.mSpannedData.mText);
        }
        StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(this.mSpannedData, this.mEngineBindContext.mSpannedEditOperator.isDark());
        this.mStaticLayout = builderObtainBuilder == null ? null : builderObtainBuilder.build();
    }

    public void release() {
        this.mCsEditText = null;
        this.mEngineBindContext = null;
        this.mSpannedData = null;
        this.mEditTextBindContext = null;
        if (this.mStaticLayout != null) {
            this.mStaticLayout = null;
        }
    }
}
