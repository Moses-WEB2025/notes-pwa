package org.apache.commons.lang3.builder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ToStringStyle implements Serializable {
    private static final long serialVersionUID = -2587890625525655916L;
    private boolean fieldSeparatorAtEnd;
    private boolean fieldSeparatorAtStart;
    private boolean useShortClassName;
    public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
    public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
    public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
    public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
    public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
    public static final ToStringStyle NO_CLASS_NAME_STYLE = new NoClassNameToStringStyle();
    public static final ToStringStyle JSON_STYLE = new JsonToStringStyle();
    private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY = ThreadLocal.withInitial(new Supplier() { // from class: org.apache.commons.lang3.builder.ToStringStyle$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ToStringStyle.m4561$r8$lambda$MSbZIt9Svlzq1LgR6lqIQHlf6s();
        }
    });
    private boolean useFieldNames = true;
    private boolean useClassName = true;
    private boolean useIdentityHashCode = true;
    private String contentStart = "[";
    private String contentEnd = "]";
    private String fieldNameValueSeparator = "=";
    private String fieldSeparator = ",";
    private String arrayStart = "{";
    private String arraySeparator = ",";
    private boolean arrayContentDetail = true;
    private String arrayEnd = StringSubstitutor.DEFAULT_VAR_END;
    private boolean defaultFullDetail = true;
    private String nullText = "<null>";
    private String sizeStartText = "<size=";
    private String sizeEndText = ">";
    private String summaryObjectStartText = "<";
    private String summaryObjectEndText = ">";

    /* JADX INFO: renamed from: $r8$lambda$MSb-ZIt9Svlzq1LgR6lqIQHlf6s, reason: not valid java name */
    public static /* synthetic */ WeakHashMap m4561$r8$lambda$MSbZIt9Svlzq1LgR6lqIQHlf6s() {
        return new WeakHashMap();
    }

    private static final class DefaultToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        DefaultToStringStyle() {
        }

        private Object readResolve() {
            return DEFAULT_STYLE;
        }
    }

    private static final class JsonToStringStyle extends ToStringStyle {
        private static final String FIELD_NAME_QUOTE = "\"";
        private static final long serialVersionUID = 1;

        JsonToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
            setContentStart("{");
            setContentEnd(StringSubstitutor.DEFAULT_VAR_END);
            setArrayStart("[");
            setArrayEnd("]");
            setFieldSeparator(",");
            setFieldNameValueSeparator(":");
            setNullText("null");
            setSummaryObjectStartText("\"<");
            setSummaryObjectEndText(">\"");
            setSizeStartText("\"<size=");
            setSizeEndText(">\"");
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, boolean[] zArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, zArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, byte[] bArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, bArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, char[] cArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, cArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, double[] dArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, dArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, float[] fArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, fArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, int[] iArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, iArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, long[] jArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, jArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, Object obj, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, obj, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, Object[] objArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, objArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void append(StringBuffer stringBuffer, String str, short[] sArr, Boolean bool) {
            checkAppendInput(str, bool);
            super.append(stringBuffer, str, sArr, bool);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        protected void appendDetail(StringBuffer stringBuffer, String str, char c) {
            appendValueAsString(stringBuffer, String.valueOf(c));
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        protected void appendDetail(StringBuffer stringBuffer, String str, Collection<?> collection) {
            if (collection != null && !collection.isEmpty()) {
                stringBuffer.append(getArrayStart());
                Iterator<?> it = collection.iterator();
                int i = 0;
                while (it.hasNext()) {
                    appendDetail(stringBuffer, str, i, it.next());
                    i++;
                }
                stringBuffer.append(getArrayEnd());
                return;
            }
            stringBuffer.append(collection);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        protected void appendDetail(StringBuffer stringBuffer, String str, Map<?, ?> map) {
            if (map != null && !map.isEmpty()) {
                stringBuffer.append(getContentStart());
                boolean z = true;
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String string = Objects.toString(entry.getKey(), null);
                    if (string != null) {
                        if (z) {
                            z = false;
                        } else {
                            appendFieldEnd(stringBuffer, string);
                        }
                        appendFieldStart(stringBuffer, string);
                        Object value = entry.getValue();
                        if (value == null) {
                            appendNullText(stringBuffer, string);
                        } else {
                            appendInternal(stringBuffer, string, value, true);
                        }
                    }
                }
                stringBuffer.append(getContentEnd());
                return;
            }
            stringBuffer.append(map);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        protected void appendDetail(StringBuffer stringBuffer, String str, Object obj) {
            if (obj == null) {
                appendNullText(stringBuffer, str);
                return;
            }
            if ((obj instanceof String) || (obj instanceof Character)) {
                appendValueAsString(stringBuffer, obj.toString());
                return;
            }
            if ((obj instanceof Number) || (obj instanceof Boolean)) {
                stringBuffer.append(obj);
                return;
            }
            String string = obj.toString();
            if (isJsonObject(string) || isJsonArray(string)) {
                stringBuffer.append(obj);
            } else {
                appendDetail(stringBuffer, str, string);
            }
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        protected void appendFieldStart(StringBuffer stringBuffer, String str) {
            checkFieldName(str);
            super.appendFieldStart(stringBuffer, FIELD_NAME_QUOTE + StringEscapeUtils.escapeJson(str) + FIELD_NAME_QUOTE);
        }

        private void appendValueAsString(StringBuffer stringBuffer, String str) {
            stringBuffer.append('\"').append(StringEscapeUtils.escapeJson(str)).append('\"');
        }

        private void checkAppendInput(String str, Boolean bool) {
            checkFieldName(str);
            checkIsFullDetail(bool);
        }

        private void checkFieldName(String str) {
            if (str == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
        }

        private void checkIsFullDetail(Boolean bool) {
            if (!isFullDetail(bool)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
        }

        private boolean isJsonArray(String str) {
            return str.startsWith(getArrayStart()) && str.endsWith(getArrayEnd());
        }

        private boolean isJsonObject(String str) {
            return str.startsWith(getContentStart()) && str.endsWith(getContentEnd());
        }

        private Object readResolve() {
            return JSON_STYLE;
        }
    }

    private static final class MultiLineToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        MultiLineToStringStyle() {
            setContentStart("[");
            setFieldSeparator(System.lineSeparator() + "  ");
            setFieldSeparatorAtStart(true);
            setContentEnd(System.lineSeparator() + "]");
        }

        private Object readResolve() {
            return MULTI_LINE_STYLE;
        }
    }

    private static final class NoClassNameToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        NoClassNameToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
        }

        private Object readResolve() {
            return NO_CLASS_NAME_STYLE;
        }
    }

    private static final class NoFieldNameToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        NoFieldNameToStringStyle() {
            setUseFieldNames(false);
        }

        private Object readResolve() {
            return NO_FIELD_NAMES_STYLE;
        }
    }

    private static final class ShortPrefixToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        ShortPrefixToStringStyle() {
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
        }

        private Object readResolve() {
            return SHORT_PREFIX_STYLE;
        }
    }

    private static final class SimpleToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1;

        SimpleToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
            setUseFieldNames(false);
            setContentStart("");
            setContentEnd("");
        }

        private Object readResolve() {
            return SIMPLE_STYLE;
        }
    }

    public static Map<Object, Object> getRegistry() {
        return REGISTRY.get();
    }

    static boolean isRegistered(Object obj) {
        return getRegistry().containsKey(obj);
    }

    static void register(Object obj) {
        if (obj != null) {
            getRegistry().put(obj, null);
        }
    }

    static void unregister(Object obj) {
        if (obj != null) {
            Map<Object, Object> registry = getRegistry();
            registry.remove(obj);
            if (registry.isEmpty()) {
                REGISTRY.remove();
            }
        }
    }

    protected ToStringStyle() {
    }

    public void append(StringBuffer stringBuffer, String str, boolean z) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, z);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, boolean[] zArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (zArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, zArr);
        } else {
            appendSummary(stringBuffer, str, zArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, byte b) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, b);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, byte[] bArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (bArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, bArr);
        } else {
            appendSummary(stringBuffer, str, bArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, char c) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, c);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, char[] cArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (cArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, cArr);
        } else {
            appendSummary(stringBuffer, str, cArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, double d) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, d);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, double[] dArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (dArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, dArr);
        } else {
            appendSummary(stringBuffer, str, dArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, float f) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, f);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, float[] fArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (fArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, fArr);
        } else {
            appendSummary(stringBuffer, str, fArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, int i) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, i);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, int[] iArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (iArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, iArr);
        } else {
            appendSummary(stringBuffer, str, iArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, long j) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, j);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, long[] jArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (jArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, jArr);
        } else {
            appendSummary(stringBuffer, str, jArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, Object obj, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (obj == null) {
            appendNullText(stringBuffer, str);
        } else {
            appendInternal(stringBuffer, str, obj, isFullDetail(bool));
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, Object[] objArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (objArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, objArr);
        } else {
            appendSummary(stringBuffer, str, objArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, short s) {
        appendFieldStart(stringBuffer, str);
        appendDetail(stringBuffer, str, s);
        appendFieldEnd(stringBuffer, str);
    }

    public void append(StringBuffer stringBuffer, String str, short[] sArr, Boolean bool) {
        appendFieldStart(stringBuffer, str);
        if (sArr == null) {
            appendNullText(stringBuffer, str);
        } else if (isFullDetail(bool)) {
            appendDetail(stringBuffer, str, sArr);
        } else {
            appendSummary(stringBuffer, str, sArr);
        }
        appendFieldEnd(stringBuffer, str);
    }

    protected void appendClassName(StringBuffer stringBuffer, Object obj) {
        if (!this.useClassName || obj == null) {
            return;
        }
        register(obj);
        if (this.useShortClassName) {
            stringBuffer.append(getShortClassName(obj.getClass()));
        } else {
            stringBuffer.append(obj.getClass().getName());
        }
    }

    protected void appendContentEnd(StringBuffer stringBuffer) {
        stringBuffer.append(this.contentEnd);
    }

    protected void appendContentStart(StringBuffer stringBuffer) {
        stringBuffer.append(this.contentStart);
    }

    protected void appendCyclicObject(StringBuffer stringBuffer, String str, Object obj) {
        ObjectUtils.identityToString(stringBuffer, obj);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, boolean z) {
        stringBuffer.append(z);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, boolean[] zArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < zArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, zArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, byte b) {
        stringBuffer.append((int) b);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, byte[] bArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < bArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, bArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, char c) {
        stringBuffer.append(c);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, char[] cArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < cArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, cArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, Collection<?> collection) {
        stringBuffer.append(collection);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, double d) {
        stringBuffer.append(d);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, double[] dArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < dArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, dArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, float f) {
        stringBuffer.append(f);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, float[] fArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < fArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, fArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, int i) {
        stringBuffer.append(i);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, int i, Object obj) {
        if (i > 0) {
            stringBuffer.append(this.arraySeparator);
        }
        if (obj == null) {
            appendNullText(stringBuffer, str);
        } else {
            appendInternal(stringBuffer, str, obj, this.arrayContentDetail);
        }
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, int[] iArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, iArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, long j) {
        stringBuffer.append(j);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, long[] jArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < jArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, jArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, Map<?, ?> map) {
        stringBuffer.append(map);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, Object obj) {
        stringBuffer.append(obj);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, Object[] objArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < objArr.length; i++) {
            appendDetail(stringBuffer, str, i, objArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, short s) {
        stringBuffer.append((int) s);
    }

    protected void appendDetail(StringBuffer stringBuffer, String str, short[] sArr) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < sArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            appendDetail(stringBuffer, str, sArr[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    public void appendEnd(StringBuffer stringBuffer, Object obj) {
        if (!this.fieldSeparatorAtEnd) {
            removeLastFieldSeparator(stringBuffer);
        }
        appendContentEnd(stringBuffer);
        unregister(obj);
    }

    protected void appendFieldEnd(StringBuffer stringBuffer, String str) {
        appendFieldSeparator(stringBuffer);
    }

    protected void appendFieldSeparator(StringBuffer stringBuffer) {
        stringBuffer.append(this.fieldSeparator);
    }

    protected void appendFieldStart(StringBuffer stringBuffer, String str) {
        if (!this.useFieldNames || str == null) {
            return;
        }
        stringBuffer.append(str);
        stringBuffer.append(this.fieldNameValueSeparator);
    }

    protected void appendIdentityHashCode(StringBuffer stringBuffer, Object obj) {
        if (!isUseIdentityHashCode() || obj == null) {
            return;
        }
        register(obj);
        stringBuffer.append('@');
        stringBuffer.append(ObjectUtils.identityHashCodeHex(obj));
    }

    protected void appendInternal(StringBuffer stringBuffer, String str, Object obj, boolean z) {
        if (isRegistered(obj) && !(obj instanceof Number) && !(obj instanceof Boolean) && !(obj instanceof Character)) {
            appendCyclicObject(stringBuffer, str, obj);
            return;
        }
        register(obj);
        try {
            if (obj instanceof Collection) {
                if (z) {
                    appendDetail(stringBuffer, str, (Collection<?>) obj);
                } else {
                    appendSummarySize(stringBuffer, str, ((Collection) obj).size());
                }
            } else if (obj instanceof Map) {
                if (z) {
                    appendDetail(stringBuffer, str, (Map<?, ?>) obj);
                } else {
                    appendSummarySize(stringBuffer, str, ((Map) obj).size());
                }
            } else if (obj instanceof long[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (long[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (long[]) obj);
                }
            } else if (obj instanceof int[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (int[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (int[]) obj);
                }
            } else if (obj instanceof short[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (short[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (short[]) obj);
                }
            } else if (obj instanceof byte[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (byte[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (byte[]) obj);
                }
            } else if (obj instanceof char[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (char[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (char[]) obj);
                }
            } else if (obj instanceof double[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (double[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (double[]) obj);
                }
            } else if (obj instanceof float[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (float[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (float[]) obj);
                }
            } else if (obj instanceof boolean[]) {
                if (z) {
                    appendDetail(stringBuffer, str, (boolean[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (boolean[]) obj);
                }
            } else if (ObjectUtils.isArray(obj)) {
                if (z) {
                    appendDetail(stringBuffer, str, (Object[]) obj);
                } else {
                    appendSummary(stringBuffer, str, (Object[]) obj);
                }
            } else if (z) {
                appendDetail(stringBuffer, str, obj);
            } else {
                appendSummary(stringBuffer, str, obj);
            }
        } finally {
            unregister(obj);
        }
    }

    protected void appendNullText(StringBuffer stringBuffer, String str) {
        stringBuffer.append(this.nullText);
    }

    public void appendStart(StringBuffer stringBuffer, Object obj) {
        if (obj != null) {
            appendClassName(stringBuffer, obj);
            appendIdentityHashCode(stringBuffer, obj);
            appendContentStart(stringBuffer);
            if (this.fieldSeparatorAtStart) {
                appendFieldSeparator(stringBuffer);
            }
        }
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, boolean[] zArr) {
        appendSummarySize(stringBuffer, str, zArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, byte[] bArr) {
        appendSummarySize(stringBuffer, str, bArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, char[] cArr) {
        appendSummarySize(stringBuffer, str, cArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, double[] dArr) {
        appendSummarySize(stringBuffer, str, dArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, float[] fArr) {
        appendSummarySize(stringBuffer, str, fArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, int[] iArr) {
        appendSummarySize(stringBuffer, str, iArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, long[] jArr) {
        appendSummarySize(stringBuffer, str, jArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, Object obj) {
        stringBuffer.append(this.summaryObjectStartText);
        stringBuffer.append(getShortClassName(obj.getClass()));
        stringBuffer.append(this.summaryObjectEndText);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, Object[] objArr) {
        appendSummarySize(stringBuffer, str, objArr.length);
    }

    protected void appendSummary(StringBuffer stringBuffer, String str, short[] sArr) {
        appendSummarySize(stringBuffer, str, sArr.length);
    }

    protected void appendSummarySize(StringBuffer stringBuffer, String str, int i) {
        stringBuffer.append(this.sizeStartText);
        stringBuffer.append(i);
        stringBuffer.append(this.sizeEndText);
    }

    public void appendSuper(StringBuffer stringBuffer, String str) {
        appendToString(stringBuffer, str);
    }

    public void appendToString(StringBuffer stringBuffer, String str) {
        int iIndexOf;
        int iLastIndexOf;
        if (str == null || (iIndexOf = str.indexOf(this.contentStart) + this.contentStart.length()) == (iLastIndexOf = str.lastIndexOf(this.contentEnd)) || iIndexOf < 0 || iLastIndexOf < 0) {
            return;
        }
        if (this.fieldSeparatorAtStart) {
            removeLastFieldSeparator(stringBuffer);
        }
        stringBuffer.append((CharSequence) str, iIndexOf, iLastIndexOf);
        appendFieldSeparator(stringBuffer);
    }

    protected String getArrayEnd() {
        return this.arrayEnd;
    }

    protected String getArraySeparator() {
        return this.arraySeparator;
    }

    protected String getArrayStart() {
        return this.arrayStart;
    }

    protected String getContentEnd() {
        return this.contentEnd;
    }

    protected String getContentStart() {
        return this.contentStart;
    }

    protected String getFieldNameValueSeparator() {
        return this.fieldNameValueSeparator;
    }

    protected String getFieldSeparator() {
        return this.fieldSeparator;
    }

    protected String getNullText() {
        return this.nullText;
    }

    protected String getShortClassName(Class<?> cls) {
        return ClassUtils.getShortClassName(cls);
    }

    protected String getSizeEndText() {
        return this.sizeEndText;
    }

    protected String getSizeStartText() {
        return this.sizeStartText;
    }

    protected String getSummaryObjectEndText() {
        return this.summaryObjectEndText;
    }

    protected String getSummaryObjectStartText() {
        return this.summaryObjectStartText;
    }

    protected boolean isArrayContentDetail() {
        return this.arrayContentDetail;
    }

    protected boolean isDefaultFullDetail() {
        return this.defaultFullDetail;
    }

    protected boolean isFieldSeparatorAtEnd() {
        return this.fieldSeparatorAtEnd;
    }

    protected boolean isFieldSeparatorAtStart() {
        return this.fieldSeparatorAtStart;
    }

    protected boolean isFullDetail(Boolean bool) {
        if (bool == null) {
            return this.defaultFullDetail;
        }
        return bool.booleanValue();
    }

    protected boolean isUseClassName() {
        return this.useClassName;
    }

    protected boolean isUseFieldNames() {
        return this.useFieldNames;
    }

    protected boolean isUseIdentityHashCode() {
        return this.useIdentityHashCode;
    }

    protected boolean isUseShortClassName() {
        return this.useShortClassName;
    }

    protected void reflectionAppendArrayDetail(StringBuffer stringBuffer, String str, Object obj) {
        stringBuffer.append(this.arrayStart);
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            appendDetail(stringBuffer, str, i, Array.get(obj, i));
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void removeLastFieldSeparator(StringBuffer stringBuffer) {
        if (Strings.CS.endsWith(stringBuffer, this.fieldSeparator)) {
            stringBuffer.setLength(stringBuffer.length() - this.fieldSeparator.length());
        }
    }

    protected void setArrayContentDetail(boolean z) {
        this.arrayContentDetail = z;
    }

    protected void setArrayEnd(String str) {
        if (str == null) {
            str = "";
        }
        this.arrayEnd = str;
    }

    protected void setArraySeparator(String str) {
        if (str == null) {
            str = "";
        }
        this.arraySeparator = str;
    }

    protected void setArrayStart(String str) {
        if (str == null) {
            str = "";
        }
        this.arrayStart = str;
    }

    protected void setContentEnd(String str) {
        if (str == null) {
            str = "";
        }
        this.contentEnd = str;
    }

    protected void setContentStart(String str) {
        if (str == null) {
            str = "";
        }
        this.contentStart = str;
    }

    protected void setDefaultFullDetail(boolean z) {
        this.defaultFullDetail = z;
    }

    protected void setFieldNameValueSeparator(String str) {
        if (str == null) {
            str = "";
        }
        this.fieldNameValueSeparator = str;
    }

    protected void setFieldSeparator(String str) {
        if (str == null) {
            str = "";
        }
        this.fieldSeparator = str;
    }

    protected void setFieldSeparatorAtEnd(boolean z) {
        this.fieldSeparatorAtEnd = z;
    }

    protected void setFieldSeparatorAtStart(boolean z) {
        this.fieldSeparatorAtStart = z;
    }

    protected void setNullText(String str) {
        if (str == null) {
            str = "";
        }
        this.nullText = str;
    }

    protected void setSizeEndText(String str) {
        if (str == null) {
            str = "";
        }
        this.sizeEndText = str;
    }

    protected void setSizeStartText(String str) {
        if (str == null) {
            str = "";
        }
        this.sizeStartText = str;
    }

    protected void setSummaryObjectEndText(String str) {
        if (str == null) {
            str = "";
        }
        this.summaryObjectEndText = str;
    }

    protected void setSummaryObjectStartText(String str) {
        if (str == null) {
            str = "";
        }
        this.summaryObjectStartText = str;
    }

    protected void setUseClassName(boolean z) {
        this.useClassName = z;
    }

    protected void setUseFieldNames(boolean z) {
        this.useFieldNames = z;
    }

    protected void setUseIdentityHashCode(boolean z) {
        this.useIdentityHashCode = z;
    }

    protected void setUseShortClassName(boolean z) {
        this.useShortClassName = z;
    }
}
