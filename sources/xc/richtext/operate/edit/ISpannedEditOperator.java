package xc.richtext.operate.edit;

import android.graphics.Path;
import android.graphics.RectF;
import java.util.List;
import xc.richtext.api.OnSpannedHightListener;
import xc.richtext.data.SpannedData;
import xc.richtext.data.TextInfo;

/* JADX INFO: loaded from: classes.dex */
public interface ISpannedEditOperator {
    SpannedData addBitmap(String str, RectF rectF, float f);

    SpannedData addBitmap(String str, RectF rectF, float f, boolean z);

    SpannedData addText(RectF rectF, String str);

    SpannedData addText(TextInfo textInfo);

    boolean canRedo();

    boolean canUndo();

    void clearAll();

    void clearPage(int i);

    void deletePage(int i);

    void doCopy(boolean z);

    void doCopyAndPast(float f, float f2);

    void doCut(boolean z);

    void doEditClick(int i, float f, float f2);

    void doPast(List<SpannedData> list, float f, float f2);

    void doPathSelect(Path path);

    void doPathSelect(Path path, boolean z);

    void doPointSelect(float f, float f2);

    void doPointSelect(float f, float f2, int i);

    void doPointSelect(float f, float f2, boolean z);

    void forceChildLayout(boolean z, int i, int i2, int i3, int i4);

    void forceReDraw();

    List<SpannedData> getAllSpanDataList(int i, RectF rectF);

    List<SpannedData> getAllSpanDataList(RectF rectF);

    float getEditTextCursorHeight();

    RectF getSpanAreaInCanvas(RectF rectF);

    RectF getSpanAreaInCanvasWithoutRadio(RectF rectF);

    boolean isDark();

    SpannedData moveEditText(int i, int i2);

    void redo(boolean z);

    void resetViewData();

    void setHightText(String str, int i, int i2, OnSpannedHightListener onSpannedHightListener);

    void setMinLimitSize(int i, int i2);

    void setTextBgColor(int i);

    void undo(boolean z);
}
