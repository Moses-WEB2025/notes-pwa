package org.apache.commons.lang3.builder;

import java.lang.reflect.Field;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
final class Reflection {
    Reflection() {
    }

    static Object getUnchecked(Field field, Object obj) {
        try {
            return ((Field) Objects.requireNonNull(field, "field")).get(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
