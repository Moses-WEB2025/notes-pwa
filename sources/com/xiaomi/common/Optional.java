package com.xiaomi.common;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes4.dex */
public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    private Optional() {
        this.value = null;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    private Optional(T t) {
        t.getClass();
        this.value = t;
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<>(t);
    }

    public static <T> Optional<T> ofNullable(T t) {
        return t == null ? empty() : of(t);
    }

    public T get() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public T getOrDefault(T t) {
        T t2 = this.value;
        return t2 == null ? t : t2;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Optional)) {
            return false;
        }
        T t = this.value;
        T t2 = ((Optional) obj).value;
        return t == t2 || (t != null && t.equals(t2));
    }

    public int hashCode() {
        T t = this.value;
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }

    public String toString() {
        T t = this.value;
        return t != null ? String.format("Optional[%s]", t) : "Optional.empty";
    }
}
