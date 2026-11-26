package com.google.common.io;

import com.google.errorprone.annotations.DoNotMock;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@DoNotMock("Implement it normally")
public interface ByteProcessor<T> {
    @ParametricNullness
    T getResult();

    boolean processBytes(byte[] buf, int off, int len) throws IOException;
}
