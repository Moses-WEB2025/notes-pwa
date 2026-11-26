package com.xiaomi.micloudsdk.utils;

import com.xiaomi.micloudsdk.request.utils.ParamChecker;
import com.xiaomi.opensdk.exception.UnretriableException;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class CheckParam {
    public static void checkParamValid(String str, Integer num) throws UnretriableException {
        ParamChecker.checkParamValid(str, num);
    }

    public static void checkParamValid(String str, Long l) throws UnretriableException {
        ParamChecker.checkParamValid(str, l);
    }

    public static void checkParamValid(String str, String str2) throws UnretriableException {
        ParamChecker.checkParamValid(str, str2);
    }

    public static void checkParamValid(String str, Object obj) throws UnretriableException {
        ParamChecker.checkParamValid(str, obj);
    }

    public static boolean checkParamNeed(Integer num) {
        return ParamChecker.checkParamNeed(num.intValue());
    }

    public static boolean checkParamNeed(Long l) {
        return ParamChecker.checkParamNeed(l.longValue());
    }

    public static boolean checkParamNeed(String str) {
        return ParamChecker.checkParamNeed(str);
    }
}
