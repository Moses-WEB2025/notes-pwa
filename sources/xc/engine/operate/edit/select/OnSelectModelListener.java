package xc.engine.operate.edit.select;

import android.graphics.Path;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface OnSelectModelListener extends onSelectStepChangedListener {
    void finishSelect();

    boolean isPagePointLimit(int i);

    void isPointLimit();

    void onCancelSelect();

    void onCanvasChanged();

    void onDataCopied(int i, byte[] bArr, int i2);

    void onDoingAction(int i, SRectF sRectF);

    void onEndAction(int i);

    void onObjectSelected(SRectF sRectF, List<DataCurve> list);

    void onObjectSelected(SRectF sRectF, List<DataCurve> list, boolean z);

    void onObjectSelectedPath(float f, float f2, Path path, boolean z);

    void onObjectSelectedPathForOutLine(float f, float f2, Path path, boolean z);

    void onSelectLimit(int i, float f);

    void onSelectedDeletedEnd();

    void onSelectedHashCode(int i);

    void onStartAction(int i);

    void onStartPasteFromCopy();

    void setSelectStateI(boolean z);
}
