package xc.richtext.operate.edit;

import android.graphics.RectF;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes.dex */
public interface ISpannedEditObject extends ISpannedObject {
    public static final int EDIT_ACTION_DELETE = 2;
    public static final int EDIT_ACTION_NULL = 0;
    public static final int EDIT_ACTION_PARAM = 1;

    void delete();

    void doEditParam(float f);

    void finishEdit();

    SpannedData getEditData();

    RectF getEditRectF();
}
