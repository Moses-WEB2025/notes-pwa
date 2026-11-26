package xc.richtext.operate.step;

import android.text.Editable;

/* JADX INFO: loaded from: classes2.dex */
public interface ITextChangeStep extends ISpannedStep {
    @Override // xc.richtext.operate.step.ISpannedStep
    int getPageIndex();

    int getTextId();

    int[] redo(Editable editable);

    int[] undo(Editable editable);
}
