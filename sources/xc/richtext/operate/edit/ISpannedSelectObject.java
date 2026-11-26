package xc.richtext.operate.edit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import java.util.List;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes.dex */
public interface ISpannedSelectObject extends ISpannedObject {
    public static final int MIX = 12;
    public static final int MOVE = 2;
    public static final int NULL = 1;
    public static final int ROTATE = 8;
    public static final int SCALE = 4;

    void cancelAction();

    void doCrop(RectF rectF, RectF rectF2);

    void doDelete();

    void doDelete(boolean z);

    void doMove(float f, float f2);

    void doRotate(float f, float f2, float f3);

    void doScale(float f, float f2, float f3);

    void externalSelected(boolean z);

    void finishSelect();

    void forceRedraw();

    int getClickTextId(float f, float f2);

    void getSelectAndMaskBitmap(Bitmap bitmap, Bitmap bitmap2, RectF rectF, int i, int i2, boolean z);

    void getSelectBitmap(Bitmap bitmap, RectF rectF, int i, boolean z);

    RectF getSelectRectF();

    List<SpannedData> getSelectedList();

    void onDraw(Canvas canvas);

    void onDrawText(Canvas canvas);

    void resetSelectViewData();
}
