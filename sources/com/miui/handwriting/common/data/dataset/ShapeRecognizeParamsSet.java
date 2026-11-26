package com.miui.handwriting.common.data.dataset;

import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeRecognizeParamsSet {
    private static final float DEFAULT_DISTANCE_OFFSET = 16.0f;
    private static final int DEFAULT_MIN_LINE_LENGTH = 50;
    private static final int DEFAULT_TIME = 800;
    private static boolean autoSelect = false;
    private static ParamsRecognizeShape curShapeRecognizeParams = null;
    private static boolean isEnable = true;
    private static boolean shapeUpEnable = false;

    public static ParamsRecognizeShape getCurShapeRecognizeParams() {
        if (curShapeRecognizeParams == null) {
            curShapeRecognizeParams = new ParamsRecognizeShape(800, DEFAULT_DISTANCE_OFFSET, 50.0f);
        }
        return curShapeRecognizeParams;
    }

    public static void setCurShapeRecognizeParams(ParamsRecognizeShape paramsRecognizeShape) {
        curShapeRecognizeParams = paramsRecognizeShape;
    }

    public static boolean isEnable() {
        return isEnable;
    }

    public static void setEnable(boolean z) {
        isEnable = z;
    }

    public static boolean isShapeUpEnable() {
        return shapeUpEnable;
    }

    public static void setShapeUpEnable(boolean z) {
        shapeUpEnable = z;
    }

    public static boolean isAutoSelect() {
        return autoSelect;
    }

    public static void setAutoSelect(boolean z) {
        autoSelect = z;
    }

    public static void clear() {
        setEnable(true);
        setCurShapeRecognizeParams(null);
    }
}
