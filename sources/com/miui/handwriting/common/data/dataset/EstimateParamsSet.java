package com.miui.handwriting.common.data.dataset;

import xc.engine.core.algorithm.estimate.ParamsEstimate;

/* JADX INFO: loaded from: classes3.dex */
public class EstimateParamsSet {
    private static final int DEFALUE_HISTORY_MiniTrace = 200;
    private static final int DEFAULT_FORECAST_POINT = 5;
    private static final int DEFAULT_FORECAST_TIME = 37;
    public static final int MAX_FORECAST_POINT = 50;
    public static final int MAX_FORECAST_TIME = 200;
    public static final int MAX_HISTORY_MiniTrace = 500;
    public static final int MIN_FORECAST_POINT = 5;
    public static final int MIN_FORECAST_TIME = 0;
    public static final int MIN_HISTORY_MiniTrace = 100;
    static ParamsEstimate curEstimateParams = null;
    private static boolean isEnable = true;

    public static ParamsEstimate getCurEstimateParams() {
        if (curEstimateParams == null) {
            curEstimateParams = new ParamsEstimate(37, 5, 200);
        }
        return curEstimateParams;
    }

    public static void setCurEstimateParams(ParamsEstimate paramsEstimate) {
        curEstimateParams = paramsEstimate;
    }

    public static boolean isEnable() {
        return isEnable;
    }

    public static void setEnable(boolean z) {
        isEnable = z;
    }

    public static void clear() {
        setEnable(true);
        setCurEstimateParams(null);
    }
}
