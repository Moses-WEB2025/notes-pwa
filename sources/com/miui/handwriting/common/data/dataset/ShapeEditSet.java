package com.miui.handwriting.common.data.dataset;

import android.content.Context;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.bean.ShapeEditBean;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeEditSet {
    private static List<Integer> colors;
    private static ShapeEditBean curShape;
    private static int curShapePos;
    private static List<ShapeEditBean> shapeEditBeans;

    private static void init() {
        ArrayList arrayList = new ArrayList();
        shapeEditBeans = arrayList;
        arrayList.add(new ShapeEditBean(2));
        shapeEditBeans.add(new ShapeEditBean(3));
        shapeEditBeans.add(new ShapeEditBean(4));
        shapeEditBeans.add(new ShapeEditBean(5));
        shapeEditBeans.add(new ShapeEditBean(6));
        shapeEditBeans.add(new ShapeEditBean(7));
    }

    public static List<ShapeEditBean> getShapeEditBeans() {
        if (shapeEditBeans == null) {
            init();
        }
        return shapeEditBeans;
    }

    public static ShapeEditBean getCurShape() {
        if (curShape == null) {
            curShape = getShapeEditBeans().get(curShapePos);
        }
        return curShape;
    }

    public static void setCurShape(ShapeEditBean shapeEditBean) {
        curShape = shapeEditBean;
    }

    public static int getCurShapePos() {
        for (int i = 0; i < getShapeEditBeans().size(); i++) {
            if (curShape.getShapeType() == getShapeEditBeans().get(i).getShapeType()) {
                curShapePos = i;
                break;
            }
        }
        return curShapePos;
    }

    public static ShapeEditBean switchCurShape(int i) {
        for (int i2 = 0; i2 < getShapeEditBeans().size(); i2++) {
            if (i == getShapeEditBeans().get(i2).getShapeType()) {
                curShape = getShapeEditBeans().get(i2);
                break;
            }
        }
        return curShape;
    }

    public static void updateShapeEditBean(ShapeEditBean shapeEditBean) {
        for (int i = 0; i < getShapeEditBeans().size(); i++) {
            if (shapeEditBean.getShapeType() == getShapeEditBeans().get(i).getShapeType()) {
                getShapeEditBeans().set(i, shapeEditBean);
                return;
            }
        }
    }

    public static ShapeEditBean getShapeEditBeanByType(int i) {
        for (int i2 = 0; i2 < getShapeEditBeans().size(); i2++) {
            if (i == getShapeEditBeans().get(i2).getShapeType()) {
                return getShapeEditBeans().get(i2);
            }
        }
        return getShapeEditBeans().get(0);
    }

    private static int[] getColorArray(Context context, int i) {
        return context.getResources().getIntArray(i);
    }

    public static void initDefaultColorArray(Context context) {
        getShapeEditBeanByType(3).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(6).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(2).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(7).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(4).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(1).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(5).setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
        getShapeEditBeanByType(3).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(6).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(2).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(7).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(4).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(1).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        getShapeEditBeanByType(5).setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
        initDefaultColor();
    }

    public static void initDefaultColor() {
        getShapeEditBeanByType(3).setShapeDefaultFillColor();
        getShapeEditBeanByType(6).setShapeDefaultFillColor();
        getShapeEditBeanByType(2).setShapeDefaultFillColor();
        getShapeEditBeanByType(7).setShapeDefaultFillColor();
        getShapeEditBeanByType(4).setShapeDefaultFillColor();
        getShapeEditBeanByType(1).setShapeDefaultFillColor();
        getShapeEditBeanByType(5).setShapeDefaultFillColor();
        getShapeEditBeanByType(3).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(6).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(2).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(7).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(4).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(1).setShapeDefaultStrokeColor();
        getShapeEditBeanByType(5).setShapeDefaultStrokeColor();
    }

    public static void initShapeEditBeans(Context context) {
        for (ShapeEditBean shapeEditBean : getShapeEditBeans()) {
            shapeEditBean.setDefaultFillColorArray(getColorArray(context, R.array.creation_shape_fill_default_color_item));
            shapeEditBean.setDefaultStrokeColorArray(getColorArray(context, R.array.creation_shape_stroke_default_color_item));
            shapeEditBean.setShapeDefaultFillColor();
            shapeEditBean.setShapeDefaultStrokeColor();
            shapeEditBean.setCheckedFillColorIndex(0);
            shapeEditBean.setCheckedStrokeColorIndex(0);
            shapeEditBean.setStrokeAlpha(ShapeEditBean.getStrokeAlpha(shapeEditBean.getShapeType()));
            shapeEditBean.setStrokeSize(ShapeEditBean.getStrokeSize(shapeEditBean.getShapeType()));
            shapeEditBean.setFillAlpha(ShapeEditBean.getFillAlpha(shapeEditBean.getShapeType()));
        }
    }
}
