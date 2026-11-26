package ch.qos.logback.core.spi;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public interface PreSerializationTransformer<E> {
    Serializable transform(E e);
}
