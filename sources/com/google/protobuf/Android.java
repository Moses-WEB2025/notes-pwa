package com.google.protobuf;

/* JADX INFO: loaded from: classes2.dex */
final class Android {
    private static final boolean IS_ROBOLECTRIC;
    private static final Class<?> MEMORY_CLASS = getClassForName("libcore.io.Memory");

    Android() {
    }

    static {
        IS_ROBOLECTRIC = getClassForName("org.robolectric.Robolectric") != null;
    }

    static boolean isOnAndroidDevice() {
        return (MEMORY_CLASS == null || IS_ROBOLECTRIC) ? false : true;
    }

    static Class<?> getMemoryClass() {
        return MEMORY_CLASS;
    }

    private static <T> Class<T> getClassForName(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
