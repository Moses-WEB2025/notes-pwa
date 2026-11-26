package com.xiaomi.dist.handoff;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes4.dex */
public final class CommonConstants {

    @Retention(RetentionPolicy.SOURCE)
    public @interface HandoffFlag {
        public static final int DEFAULT = 0;
        public static final int HANDOFF_ONLY = 1;
        public static final int WEB_VIEW_COMPATIBLE = 2;
    }
}
