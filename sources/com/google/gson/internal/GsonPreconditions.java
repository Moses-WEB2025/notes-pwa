package com.google.gson.internal;

/* JADX INFO: loaded from: classes2.dex */
public final class GsonPreconditions {
    private GsonPreconditions() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <T> T checkNotNull(T t) {
        t.getClass();
        return t;
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
