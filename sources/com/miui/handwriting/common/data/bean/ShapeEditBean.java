package com.miui.handwriting.common.data.bean;

import android.content.Context;
import android.content.SharedPreferences;
import ch.qos.logback.core.CoreConstants;
import com.miui.handwriting.common.tools.GsonUtils;
import com.miui.handwriting.common.tools.constants.Constants;
import java.util.HashMap;
import java.util.Map;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.ShapeEditAttr;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeEditBean extends MiuiShapeProp {
    public static final int ARROW_SHAPE_FILL_ALPHA = 255;
    public static final int ARROW_SHAPE_STROKE_ALPHA = 255;
    public static final float ARROW_SHAPE_STROKE_SIZE = 0.0f;
    private static final int ARROW_SHAPE_STROKE_TYPE = 0;
    public static final float ARROW_STROKE_MAX_SIZE = 10.0f;
    public static final float ARROW_STROKE_MIN_SIZE = 2.0f;
    public static final int BIGARROW_SHAPE_FILL_ALPHA = 255;
    public static final int BIGARROW_SHAPE_STROKE_ALPHA = 255;
    public static final float BIGARROW_SHAPE_STROKE_SIZE = 0.0f;
    private static final int BIGARROW_SHAPE_STROKE_TYPE = 0;
    public static final float BIGARROW_STROKE_MAX_SIZE = 10.0f;
    public static final float BIGARROW_STROKE_MIN_SIZE = 0.0f;
    public static final int CIRCLE_SHAPE_FILL_ALPHA = 255;
    public static final int CIRCLE_SHAPE_STROKE_ALPHA = 255;
    public static final float CIRCLE_SHAPE_STROKE_SIZE = 0.0f;
    private static final int CIRCLE_SHAPE_STROKE_TYPE = 0;
    public static final float CIRCLE_STROKE_MAX_SIZE = 10.0f;
    public static final float CIRCLE_STROKE_MIN_SIZE = 0.0f;
    private static final int DEFAULT_SHAPE_FILL_ALPHA = 255;
    private static final int DEFAULT_SHAPE_STROKE_ALPHA = 255;
    private static final float DEFAULT_SHAPE_STROKE_MAX_SIZE = 10.0f;
    private static final float DEFAULT_SHAPE_STROKE_MIN_SIZE = 0.0f;
    private static final float DEFAULT_SHAPE_STROKE_SIZE = 0.0f;
    private static final int DEFAULT_SHAPE_STROKE_TYPE = 0;
    public static final int LINE_SHAPE_FILL_ALPHA = 255;
    public static final int LINE_SHAPE_STROKE_ALPHA = 255;
    public static final float LINE_SHAPE_STROKE_SIZE = 0.0f;
    private static final int LINE_SHAPE_STROKE_TYPE = 0;
    public static final float LINE_STROKE_MAX_SIZE = 10.0f;
    public static final float LINE_STROKE_MIN_SIZE = 0.0f;
    public static final int MODE_EDIT_FILL = 0;
    public static final int MODE_EDIT_STROKE = 1;
    public static final int RECT_SHAPE_FILL_ALPHA = 255;
    public static final int RECT_SHAPE_STROKE_ALPHA = 255;
    public static final float RECT_SHAPE_STROKE_SIZE = 0.0f;
    private static final int RECT_SHAPE_STROKE_TYPE = 0;
    public static final float RECT_STROKE_MAX_SIZE = 10.0f;
    public static final float RECT_STROKE_MIN_SIZE = 0.0f;
    private static final Map<Integer, Integer> SHAPE_FILL_ALPHA_MAP;
    private static final Map<Integer, Integer> SHAPE_STROKE_ALPHA_MAP;
    private static final Map<Integer, Float> SHAPE_STROKE_MAX_SIZE_MAP;
    private static final Map<Integer, Float> SHAPE_STROKE_MIN_SIZE_MAP;
    private static final Map<Integer, Float> SHAPE_STROKE_SIZE_MAP;
    private static final Map<Integer, Integer> SHAPE_STROKE_TYPE;
    public static final int STAR_SHAPE_FILL_ALPHA = 255;
    public static final int STAR_SHAPE_STROKE_ALPHA = 255;
    public static final float STAR_SHAPE_STROKE_SIZE = 0.0f;
    private static final int STAR_SHAPE_STROKE_TYPE = 0;
    public static final float STAR_STROKE_MAX_SIZE = 10.0f;
    public static final float STAR_STROKE_MIN_SIZE = 0.0f;
    public static final int TRIANGLE_SHAPE_FILL_ALPHA = 255;
    public static final int TRIANGLE_SHAPE_STROKE_ALPHA = 255;
    public static final float TRIANGLE_SHAPE_STROKE_SIZE = 0.0f;
    private static final int TRIANGLE_SHAPE_STROKE_TYPE = 0;
    public static final float TRIANGLE_STROKE_MAX_SIZE = 10.0f;
    public static final float TRIANGLE_STROKE_MIN_SIZE = 0.0f;
    public static int mDefaultColor;
    private int mCheckedFillColorIndex;
    private int mCheckedStrokeColorIndex;
    private int[] mDefaultFillColorArray;
    private int[] mDefaultStrokeColorArray;
    private int mEditMode;
    private float maxSize;
    private float minSize;

    static {
        HashMap map = new HashMap();
        SHAPE_STROKE_SIZE_MAP = map;
        HashMap map2 = new HashMap();
        SHAPE_STROKE_ALPHA_MAP = map2;
        HashMap map3 = new HashMap();
        SHAPE_FILL_ALPHA_MAP = map3;
        HashMap map4 = new HashMap();
        SHAPE_STROKE_TYPE = map4;
        HashMap map5 = new HashMap();
        SHAPE_STROKE_MIN_SIZE_MAP = map5;
        HashMap map6 = new HashMap();
        SHAPE_STROKE_MAX_SIZE_MAP = map6;
        Float fValueOf = Float.valueOf(0.0f);
        map.put(1, fValueOf);
        map.put(2, fValueOf);
        map.put(3, fValueOf);
        map.put(4, fValueOf);
        map.put(6, fValueOf);
        map.put(5, fValueOf);
        map.put(7, fValueOf);
        map2.put(1, 255);
        map2.put(2, 255);
        map2.put(3, 255);
        map2.put(4, 255);
        map2.put(6, 255);
        map2.put(5, 255);
        map2.put(7, 255);
        map3.put(1, 255);
        map3.put(2, 255);
        map3.put(3, 255);
        map3.put(4, 255);
        map3.put(6, 255);
        map3.put(5, 255);
        map3.put(7, 255);
        map4.put(1, 0);
        map4.put(2, 0);
        map4.put(3, 0);
        map4.put(4, 0);
        map4.put(6, 0);
        map4.put(5, 0);
        map4.put(7, 0);
        map5.put(1, fValueOf);
        map5.put(2, Float.valueOf(2.0f));
        map5.put(3, fValueOf);
        map5.put(4, fValueOf);
        map5.put(5, fValueOf);
        map5.put(6, fValueOf);
        map5.put(7, fValueOf);
        Float fValueOf2 = Float.valueOf(10.0f);
        map6.put(1, fValueOf2);
        map6.put(2, fValueOf2);
        map6.put(3, fValueOf2);
        map6.put(4, fValueOf2);
        map6.put(5, fValueOf2);
        map6.put(6, fValueOf2);
        map6.put(7, fValueOf2);
        mDefaultColor = -12539393;
    }

    public ShapeEditBean(int i) {
        super(i, mDefaultColor, getStrokeSize(i), getStrokeAlpha(i), mDefaultColor, getFillAlpha(i), getStrokeType(i));
        this.mDefaultFillColorArray = new int[]{-12539393, -45745, -22208, -5059, -9187011, -2904585, -1, -16777216, 0};
        this.mDefaultStrokeColorArray = new int[]{-16159271, -3206366, -2856184, -2838266, -13066739, -11330133, -1, -16777216, 0};
        this.mEditMode = 0;
        this.minSize = getMinStrokeSize(i);
        this.maxSize = getMaxStrokeSize(i);
    }

    public float getMinSize() {
        return this.minSize;
    }

    public float getMaxSize() {
        return this.maxSize;
    }

    public void setMinSize(float f) {
        this.minSize = f;
    }

    public void setMaxSize(float f) {
        this.maxSize = f;
    }

    public int getEditMode() {
        return this.mEditMode;
    }

    public void setEditMode(int i) {
        this.mEditMode = i;
    }

    public void setDefaultFillColorArray(int i, int i2) {
        this.mDefaultFillColorArray[i] = i2;
    }

    public int[] getDefaultFillColorArray() {
        return this.mDefaultFillColorArray;
    }

    public void setDefaultFillColorArray(int[] iArr) {
        this.mDefaultFillColorArray = iArr;
    }

    public void setShapeDefaultFillColor() {
        int[] iArr = this.mDefaultFillColorArray;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        this.mCheckedStrokeColorIndex = 0;
        setFillColor(iArr[0]);
    }

    public void setDefaultStrokeColorArray(int i, int i2) {
        this.mDefaultStrokeColorArray[i] = i2;
    }

    public int[] getDefaultStrokeColorArray() {
        return this.mDefaultStrokeColorArray;
    }

    public void setDefaultStrokeColorArray(int[] iArr) {
        this.mDefaultStrokeColorArray = iArr;
    }

    public void setShapeDefaultStrokeColor() {
        int[] iArr = this.mDefaultStrokeColorArray;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        this.mCheckedStrokeColorIndex = 0;
        setStrokeColor(iArr[0]);
    }

    public int getCheckedFillColorIndex() {
        return this.mCheckedFillColorIndex;
    }

    public void setCheckedFillColorIndex(int i) {
        this.mCheckedFillColorIndex = i;
    }

    public int getCheckedStrokeColorIndex() {
        return this.mCheckedStrokeColorIndex;
    }

    public void setCheckedStrokeColorIndex(int i) {
        this.mCheckedStrokeColorIndex = i;
    }

    public static float getStrokeSize(int i) {
        return SHAPE_STROKE_SIZE_MAP.getOrDefault(Integer.valueOf(i), Float.valueOf(0.0f)).floatValue();
    }

    public static int getStrokeAlpha(int i) {
        return SHAPE_STROKE_ALPHA_MAP.getOrDefault(Integer.valueOf(i), 255).intValue();
    }

    public static int getStrokeType(int i) {
        return SHAPE_STROKE_TYPE.getOrDefault(Integer.valueOf(i), 0).intValue();
    }

    public static int getFillAlpha(int i) {
        return SHAPE_FILL_ALPHA_MAP.getOrDefault(Integer.valueOf(i), 255).intValue();
    }

    public static float getMinStrokeSize(int i) {
        return SHAPE_STROKE_MIN_SIZE_MAP.getOrDefault(Integer.valueOf(i), Float.valueOf(0.0f)).floatValue();
    }

    public static float getMaxStrokeSize(int i) {
        return SHAPE_STROKE_MAX_SIZE_MAP.getOrDefault(Integer.valueOf(i), Float.valueOf(10.0f)).floatValue();
    }

    public static ShapeEditBean getShapeEditBeanFromLocal(Context context, int i) {
        String string;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.CREATION_SHPREF, 0);
        switch (i) {
            case 1:
                string = sharedPreferences.getString(Constants.SHAPE_LINE, "");
                break;
            case 2:
                string = sharedPreferences.getString(Constants.SHAPE_ARROW, "");
                break;
            case 3:
                string = sharedPreferences.getString(Constants.SHAPE_RECT, "");
                break;
            case 4:
                string = sharedPreferences.getString(Constants.SHAPE_TRIANGLE, "");
                break;
            case 5:
                string = sharedPreferences.getString(Constants.SHAPE_CIRCLE, "");
                break;
            case 6:
                string = sharedPreferences.getString(Constants.SHAPE_STAR, "");
                break;
            default:
                string = sharedPreferences.getString(Constants.SHAPE_BIGARROW, "");
                break;
        }
        return (ShapeEditBean) GsonUtils.getGsonInstance().fromJson(string, ShapeEditBean.class);
    }

    public ShapeProp convert() {
        return new ShapeProp(this.shapeType, this.strokeColor, this.strokeSize, this.strokeAlpha, this.fillColor, this.fillAlpha, this.strokeType);
    }

    public ShapeEditAttr convertAttr() {
        ShapeEditAttr shapeEditAttr = new ShapeEditAttr();
        shapeEditAttr.setStrokeSize(this.strokeSize);
        shapeEditAttr.setStrokeColor(this.strokeColor);
        shapeEditAttr.setStrokeAlpha(this.strokeAlpha);
        shapeEditAttr.setFillColor(this.fillColor);
        shapeEditAttr.setFillAlpha(this.fillAlpha);
        shapeEditAttr.setStrokeType(this.strokeType);
        return shapeEditAttr;
    }

    public void update(ShapeEditAttr shapeEditAttr) {
        if (shapeEditAttr.getStrokeSize() != 0.0f) {
            setDefaultFillColorArray(getCheckedFillColorIndex(), shapeEditAttr.getFillColor());
            setDefaultStrokeColorArray(getCheckedStrokeColorIndex(), shapeEditAttr.getStrokeColor());
        }
        setStrokeSize(shapeEditAttr.getStrokeSize());
        setStrokeColor(shapeEditAttr.getStrokeColor());
        setStrokeAlpha(shapeEditAttr.getStrokeAlpha());
        setFillColor(shapeEditAttr.getFillColor());
        setFillAlpha(shapeEditAttr.getFillAlpha());
        setStrokeType(0);
    }

    public void update(ShapeProp shapeProp) {
        setDefaultStrokeColorArray(getCheckedStrokeColorIndex(), shapeProp.getPenColor());
        setDefaultFillColorArray(getCheckedFillColorIndex(), shapeProp.getFillColor());
        setStrokeSize(shapeProp.getPenSize());
        setStrokeColor(shapeProp.getPenColor());
        setStrokeAlpha(shapeProp.getPenAlpha());
        setFillColor(shapeProp.getFillColor());
        setFillAlpha(shapeProp.getFillAlpha());
        setStrokeType(shapeProp.getStrokeType());
    }

    public String toString() {
        return "ShapeEditBean{fillColor=" + this.fillColor + ", fillAlpha=" + this.fillAlpha + ", strokeColor=" + this.strokeColor + ", strokeSize=" + this.strokeSize + ", strokeAlpha=" + this.strokeAlpha + ", fillColorWheel=" + this.fillColorWheel + ", strokeType=" + this.strokeType + CoreConstants.CURLY_RIGHT;
    }
}
