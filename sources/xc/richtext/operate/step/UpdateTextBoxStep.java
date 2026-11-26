package xc.richtext.operate.step;

import android.graphics.RectF;
import android.text.Editable;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class UpdateTextBoxStep implements ITextChangeStep {
    private static final String TAG = "UpdateTextBoxStep";
    public final EngineBindContext mEngineBindContext;
    public SpannedData mSpannedData;
    public float mWidthDiff;

    public UpdateTextBoxStep(EngineBindContext engineBindContext, SpannedData spannedData, float f) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = spannedData;
        this.mWidthDiff = f;
    }

    @Override // xc.richtext.operate.step.ITextChangeStep, xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        return this.mSpannedData.getPageIndex();
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        RectF rectF = this.mSpannedData.getRectF();
        rectF.right = rectF.left + (rectF.width() - this.mWidthDiff);
        this.mSpannedData.setSaveRect(rectF);
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        RectF rectF = this.mSpannedData.getRectF();
        rectF.right = rectF.left + rectF.width() + this.mWidthDiff;
        this.mSpannedData.setSaveRect(rectF);
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int getTextId() {
        return this.mSpannedData.mId;
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] undo(Editable editable) {
        return new int[0];
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] redo(Editable editable) {
        return new int[0];
    }
}
