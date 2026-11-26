package com.miui.notes.tool.util;

import com.miui.common.tool.Logger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class PackageNameUtils {
    private static final String BAIDU_SEARCH_ENCODED_PACKAGE_NAME = "Y29tLmJhaWR1LnNlYXJjaGJveA==";
    private static final String TAG = "PackageNameUtils";
    private static final String WECHAT_ENCODED_PACKAGE_NAME = "Y29tLnRlbmNlbnQubW0=";

    public static boolean isWechatPackage(String str) {
        try {
            boolean zEquals = Objects.equals(new String(Base64.getDecoder().decode(WECHAT_ENCODED_PACKAGE_NAME), StandardCharsets.UTF_8), str);
            Logger.INSTANCE.d(TAG, "isWechatPackage " + zEquals);
            return zEquals;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isWechatPackage error, e: " + e);
            return false;
        }
    }

    public static String getDecodedBaiduSearchPackageName() {
        try {
            return new String(Base64.getDecoder().decode(BAIDU_SEARCH_ENCODED_PACKAGE_NAME), StandardCharsets.UTF_8);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getDecodedBaiduSearchPackageName error, e: " + e);
            return "";
        }
    }
}
