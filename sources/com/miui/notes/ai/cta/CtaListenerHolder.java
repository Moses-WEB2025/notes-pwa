package com.miui.notes.ai.cta;

/* JADX INFO: loaded from: classes4.dex */
public class CtaListenerHolder {
    private static CtaResultListener INSTANCE;

    public static void setInstance(CtaResultListener ctaResultListener) {
        if (INSTANCE == null) {
            INSTANCE = ctaResultListener;
        }
    }

    public static CtaResultListener getInstance() {
        return INSTANCE;
    }
}
