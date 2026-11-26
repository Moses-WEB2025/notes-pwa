package com.xiaomi.phonenum;

import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class Constant {
    public static final String BASE_URL;
    public static final String OBTAIN_STRATEGY_URL;
    private static boolean USE_PREVIEW = false;
    public static final String UTF_8 = "utf-8";

    static {
        String str;
        boolean zExists = new File("/data/system/xiaomi_account_preview").exists();
        USE_PREVIEW = zExists;
        if (zExists) {
            str = "http://act.preview.account.xiaomi.com/pass/activator";
        } else {
            str = "https://act.account.xiaomi.com/pass/activator";
        }
        BASE_URL = str;
        OBTAIN_STRATEGY_URL = str + "/getCloudControl";
    }

    public static void setUsePreview(boolean z) {
        USE_PREVIEW = z;
    }
}
