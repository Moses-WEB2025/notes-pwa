package org.apache.commons.text;

import java.util.function.IntUnaryOperator;

/* JADX INFO: loaded from: classes6.dex */
public interface TextRandomProvider extends IntUnaryOperator {
    @Deprecated
    int nextInt(int i);

    @Override // java.util.function.IntUnaryOperator
    default int applyAsInt(int i) {
        return nextInt(i);
    }
}
