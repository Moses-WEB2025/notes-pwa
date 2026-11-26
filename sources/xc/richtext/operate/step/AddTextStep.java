package xc.richtext.operate.step;

import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class AddTextStep implements ITextChangeStep {
    public CharSequence mCharSequence;
    public int mEnd;
    public final EngineBindContext mEngineBindContext;
    public SpannedData mSpannedData;
    public int mStart;

    public AddTextStep(EngineBindContext engineBindContext, SpannedData spannedData, int i, int i2, CharSequence charSequence) {
        this.mCharSequence = "";
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = spannedData;
        this.mStart = Math.max(0, i);
        int iMin = Math.min(i + i2, charSequence.length());
        this.mEnd = iMin;
        this.mCharSequence = charSequence.subSequence(this.mStart, iMin).toString();
    }

    @Override // xc.richtext.operate.step.ITextChangeStep, xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        return this.mSpannedData.getPageIndex();
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mSpannedData.mText);
        deleteSelect(spannableStringBuilder, this.mStart, this.mEnd, this.mCharSequence);
        this.mSpannedData.mText = spannableStringBuilder.toString();
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mSpannedData.mText);
        int length = this.mStart;
        CharSequence charSequence = this.mCharSequence;
        if (length > spannableStringBuilder.length()) {
            length = spannableStringBuilder.length();
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(charSequence);
        Selection.setSelection(spannableStringBuilder, length);
        spannableStringBuilder.insert(length, (CharSequence) spannableStringBuilder2);
        this.mSpannedData.mText = spannableStringBuilder.toString();
        return this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData) ? new int[]{this.mSpannedData.mId} : new int[0];
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] redo(Editable editable) {
        int length = this.mStart;
        if (length > editable.length()) {
            length = editable.length();
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mCharSequence);
        Selection.setSelection(editable, length);
        editable.insert(length, spannableStringBuilder);
        return redo();
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int getTextId() {
        return this.mSpannedData.mId;
    }

    private void deleteSelect(Editable editable, int i, int i2, CharSequence charSequence) {
        if (i < 0) {
            i = 0;
        }
        if (i > editable.length()) {
            i = editable.length();
        }
        if (i2 > editable.length()) {
            i2 = editable.length();
        }
        if (i2 < i) {
            i2 = i;
        }
        if (TextUtils.equals(charSequence, editable.toString().substring(i, i2))) {
            Selection.setSelection(editable, i);
            editable.delete(i, i2);
        }
    }

    @Override // xc.richtext.operate.step.ITextChangeStep
    public int[] undo(Editable editable) {
        deleteSelect(editable, this.mStart, this.mEnd, this.mCharSequence);
        return undo();
    }
}
