package com.google.common.base;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
abstract class CommonMatcher {
    public abstract int end();

    public abstract boolean find();

    public abstract boolean find(int index);

    public abstract boolean matches();

    public abstract String replaceAll(String replacement);

    public abstract int start();

    CommonMatcher() {
    }
}
