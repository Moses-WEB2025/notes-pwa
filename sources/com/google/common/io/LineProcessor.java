package com.google.common.io;

import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface LineProcessor<T> {
    @ParametricNullness
    T getResult();

    boolean processLine(String line) throws IOException;
}
