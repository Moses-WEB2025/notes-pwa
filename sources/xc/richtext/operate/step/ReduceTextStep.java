package xc.richtext.operate.step;

import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class ReduceTextStep implements ITextChangeStep {
    public int c;
    public int d;
    public CharSequence mCharSequence;
    public final EngineBindContext mEngineBindContext;
    public SpannedData mSpannedData;

    public ReduceTextStep(EngineBindContext engineBindContext, SpannedData spannedData, int i, int i2, CharSequence charSequence) {
        this.mCharSequence = "";
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = spannedData;
        this.c = i;
        this.d = i + i2;
        a(charSequence.length());
        this.mCharSequence = charSequence.subSequence(this.c, this.d).toString();
    }

    @Override // xc.richtext.operate.step.ITextChangeStep, xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        return this.mSpannedData.getPageIndex();
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mSpannedData.mText);
        int iMin = Math.min(this.c, spannableStringBuilder.length());
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(this.mCharSequence);
        Selection.setSelection(spannableStringBuilder, iMin);
        spannableStringBuilder.insert(iMin, (CharSequence) spannableStringBuilder2);
        this.mSpannedData.mText = spannableStringBuilder.toString();
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mSpannedData.mText);
        a(spannableStringBuilder, this.c, this.d, this.mCharSequence);
        this.mSpannedData.mText = spannableStringBuilder.toString();
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int getTextId() {
        return this.mSpannedData.mId;
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] redo(Editable editable) {
        a(editable, this.c, this.d, this.mCharSequence);
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    private void a(int i) {
        this.c = Math.max(0, this.c);
        this.d = Math.min(this.d, i);
    }

    private void a(Editable editable, int i, int i2, CharSequence charSequence) {
        if (i < 0) {
            i = 0;
        }
        if (i2 > editable.length()) {
            i2 = editable.length();
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i > editable.length() || i2 > editable.length() || !TextUtils.equals(charSequence, editable.toString().substring(i, i2))) {
            return;
        }
        Selection.setSelection(editable, i);
        editable.delete(i, i2);
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] undo(Editable editable) {
        int iMin = Math.min(this.c, editable.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mCharSequence);
        Selection.setSelection(editable, iMin);
        editable.insert(iMin, spannableStringBuilder);
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }
}
