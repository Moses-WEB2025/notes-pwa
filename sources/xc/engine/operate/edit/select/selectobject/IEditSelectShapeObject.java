package xc.engine.operate.edit.select.selectobject;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.ShapeEditAttr;

/* JADX INFO: loaded from: classes6.dex */
public interface IEditSelectShapeObject {
    void doRadiusEdit(float f, float f2);

    void doShapeAttrEdit(ShapeEditAttr shapeEditAttr, boolean z);

    void doShapeEdit(int i, PointF pointF, float f, float f2);

    float[] getEditPoint();

    Path getSelectPath();

    RectF getShapeDrawRect();

    ShapeProp getShapeProp();

    boolean showEditPoint();
}
