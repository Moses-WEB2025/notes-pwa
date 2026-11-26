package com.google.common.base;

import ch.qos.logback.core.CoreConstants;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class MoreObjects {
    public static <T> T firstNonNull(@CheckForNull T first, @CheckForNull T second) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(clazz.getSimpleName());
    }

    public static ToStringHelper toStringHelper(String className) {
        return new ToStringHelper(className);
    }

    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitEmptyValues;
        private boolean omitNullValues;

        private ToStringHelper(String className) {
            ValueHolder valueHolder = new ValueHolder();
            this.holderHead = valueHolder;
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.omitEmptyValues = false;
            this.className = (String) Preconditions.checkNotNull(className);
        }

        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public ToStringHelper add(String name, @CheckForNull Object value) {
            return addHolder(name, value);
        }

        public ToStringHelper add(String name, boolean value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, char value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, double value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, float value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, int value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, long value) {
            return addUnconditionalHolder(name, String.valueOf(value));
        }

        public ToStringHelper addValue(@CheckForNull Object value) {
            return addHolder(value);
        }

        public ToStringHelper addValue(boolean value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(char value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(double value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(float value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(int value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(long value) {
            return addUnconditionalHolder(String.valueOf(value));
        }

        private static boolean isEmpty(Object value) {
            if (value instanceof CharSequence) {
                return ((CharSequence) value).length() == 0;
            }
            if (value instanceof Collection) {
                return ((Collection) value).isEmpty();
            }
            if (value instanceof Map) {
                return ((Map) value).isEmpty();
            }
            if (value instanceof Optional) {
                return !((Optional) value).isPresent();
            }
            return value.getClass().isArray() && Array.getLength(value) == 0;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0032  */
        /* JADX WARN: Code duplicated, block: B:14:0x0039  */
        /* JADX WARN: Code duplicated, block: B:16:0x0046  */
        /* JADX WARN: Code duplicated, block: B:19:0x0062  */
        public String toString() {
            boolean z = this.omitNullValues;
            boolean z2 = this.omitEmptyValues;
            StringBuilder sbAppend = new StringBuilder(32).append(this.className).append(CoreConstants.CURLY_LEFT);
            String str = "";
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                Object obj = valueHolder.value;
                if (valueHolder instanceof UnconditionalValueHolder) {
                    sbAppend.append(str);
                    if (valueHolder.name != null) {
                        sbAppend.append(valueHolder.name).append('=');
                    }
                    if (obj == null && obj.getClass().isArray()) {
                        String strDeepToString = Arrays.deepToString(new Object[]{obj});
                        sbAppend.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
                    } else {
                        sbAppend.append(obj);
                    }
                    str = ", ";
                } else if (obj == null) {
                    if (!z) {
                        sbAppend.append(str);
                        if (valueHolder.name != null) {
                            sbAppend.append(valueHolder.name).append('=');
                        }
                        if (obj == null) {
                            sbAppend.append(obj);
                        } else {
                            sbAppend.append(obj);
                        }
                        str = ", ";
                    }
                } else if (!z2 || !isEmpty(obj)) {
                    sbAppend.append(str);
                    if (valueHolder.name != null) {
                        sbAppend.append(valueHolder.name).append('=');
                    }
                    if (obj == null) {
                        sbAppend.append(obj);
                    } else {
                        sbAppend.append(obj);
                    }
                    str = ", ";
                }
            }
            return sbAppend.append(CoreConstants.CURLY_RIGHT).toString();
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(@CheckForNull Object value) {
            addHolder().value = value;
            return this;
        }

        private ToStringHelper addHolder(String name, @CheckForNull Object value) {
            ValueHolder valueHolderAddHolder = addHolder();
            valueHolderAddHolder.value = value;
            valueHolderAddHolder.name = (String) Preconditions.checkNotNull(name);
            return this;
        }

        private UnconditionalValueHolder addUnconditionalHolder() {
            UnconditionalValueHolder unconditionalValueHolder = new UnconditionalValueHolder();
            this.holderTail.next = unconditionalValueHolder;
            this.holderTail = unconditionalValueHolder;
            return unconditionalValueHolder;
        }

        private ToStringHelper addUnconditionalHolder(Object value) {
            addUnconditionalHolder().value = value;
            return this;
        }

        private ToStringHelper addUnconditionalHolder(String name, Object value) {
            UnconditionalValueHolder unconditionalValueHolderAddUnconditionalHolder = addUnconditionalHolder();
            unconditionalValueHolderAddUnconditionalHolder.value = value;
            unconditionalValueHolderAddUnconditionalHolder.name = (String) Preconditions.checkNotNull(name);
            return this;
        }

        private static class ValueHolder {

            @CheckForNull
            String name;

            @CheckForNull
            ValueHolder next;

            @CheckForNull
            Object value;

            private ValueHolder() {
            }
        }

        private static final class UnconditionalValueHolder extends ValueHolder {
            private UnconditionalValueHolder() {
                super();
            }
        }
    }

    private MoreObjects() {
    }
}
