package xc.richtext.view;

import java.util.List;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.ISpannedSelectObject;

/* JADX INFO: loaded from: classes3.dex */
public interface CsSpannedLayoutListener {
    boolean isEditAction();

    void onCopyData(List<SpannedData> list);

    void onCopyData(boolean z, List<SpannedData> list);

    void onLoadFinish(boolean z);

    void onSpannedEdited(ISpannedEditObject iSpannedEditObject);

    void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject);

    void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean z);

    void onStepChanged(int i, int i2, boolean z);

    int textColorTransform(int i);
}
