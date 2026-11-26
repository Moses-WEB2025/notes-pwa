package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;

/* JADX INFO: loaded from: classes4.dex */
public class ag {
    private static final int a = 1;
    private static final int b = 2;
    private static final int c = 4;
    private static final int d = 8;
    private static final int e = 16;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    public static int a(Configuration configuration) {
        if (configuration == null) {
            return 0;
        }
        boolean zIsGAIDEnable = configuration.isGAIDEnable();
        ?? r0 = zIsGAIDEnable;
        if (configuration.isIMSIEnable()) {
            r0 = (zIsGAIDEnable ? 1 : 0) | 2;
        }
        ?? r1 = r0;
        if (configuration.isIMEIEnable()) {
            r1 = (r0 == true ? 1 : 0) | 4;
        }
        ?? r2 = r1;
        if (configuration.isExceptionCatcherEnable()) {
            r2 = (r1 == true ? 1 : 0) | 8;
        }
        return configuration.isOverrideMiuiRegionSetting() ? r2 | 16 : r2;
    }
}
