package com.xiaomi.micloudsdk.request.utils;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.opensdk.exception.UnretriableException;

/* JADX INFO: loaded from: classes4.dex */
public class ParamChecker {
    private static final String TAG = "ParamChecker";

    public static boolean checkParamNeed(int i) {
        return i > 0;
    }

    public static boolean checkParamNeed(long j) {
        return j > 0;
    }

    public static void checkParamValid(String str, Integer num) throws UnretriableException {
        Log.d(TAG, "checkParamValid() paramName:" + str + " paramValue:" + num);
        if (num.intValue() <= 0) {
            throw new UnretriableException("Invalid Int Param:" + str + " Value:" + num);
        }
    }

    public static void checkParamValid(String str, Long l) throws UnretriableException {
        Log.d(TAG, "checkParamValid() paramName:" + str + " paramValue:" + l);
        if (l.longValue() <= 0) {
            throw new UnretriableException("Invalid Long Param:" + str + " Value:" + l);
        }
    }

    public static void checkParamValid(String str, String str2) throws UnretriableException {
        Log.d(TAG, "checkParamValid() paramName:" + str + " paramValue:" + str2);
        if (TextUtils.isEmpty(str2)) {
            throw new UnretriableException("Invalid Empty String Param:" + str);
        }
    }

    public static void checkParamValid(String str, Object obj) throws UnretriableException {
        Log.d(TAG, "checkParamValid() paramName:" + str + " paramValue:" + obj);
        if (obj == null) {
            throw new UnretriableException("Invalid Null Param:" + str);
        }
    }

    public static boolean checkParamNeed(String str) {
        return !TextUtils.isEmpty(str);
    }
}
