package xc.richtext.api;

import java.util.List;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.ISpannedSelectObject;

/* JADX INFO: loaded from: classes6.dex */
public interface OnSpannedEditListener {
    void cancelSelectAction(float f, float f2);

    void cancelSoftKeyboard();

    void onCopyData(List<SpannedData> list);

    void onCopyData(boolean z, List<SpannedData> list);

    void onFinishSelect();

    void onLoadFinish(boolean z);

    void onSelectCount(int i, String str);

    void onSelectedClick();

    void onSpannedEdited(ISpannedEditObject iSpannedEditObject);

    void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject);

    void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean z);

    void onStartAction();

    void onStepChanged(int i, int i2, boolean z);
}
