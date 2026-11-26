package org.apache.commons.lang3.concurrent;

import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class ConstantInitializer<T> implements ConcurrentInitializer<T> {
    private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
    private final T object;

    public boolean isInitialized() {
        return true;
    }

    public ConstantInitializer(T t) {
        this.object = t;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ConstantInitializer) {
            return Objects.equals(getObject(), ((ConstantInitializer) obj).getObject());
        }
        return false;
    }

    @Override // org.apache.commons.lang3.function.FailableSupplier
    public T get() throws ConcurrentException {
        return getObject();
    }

    public final T getObject() {
        return this.object;
    }

    public int hashCode() {
        return Objects.hashCode(this.object);
    }

    public String toString() {
        return String.format(FMT_TO_STRING, Integer.valueOf(System.identityHashCode(this)), getObject());
    }
}
