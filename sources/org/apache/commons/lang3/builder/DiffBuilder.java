package org.apache.commons.lang3.builder;

import com.miui.common.stat.EditStat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes6.dex */
public class DiffBuilder<T> implements org.apache.commons.lang3.builder.Builder<DiffResult<T>> {
    static final String TO_STRING_FORMAT = "%s differs from %s";
    private final List<Diff<?>> diffs;
    private final boolean equals;
    private final T left;
    private final T right;
    private final ToStringStyle style;
    private final String toStringFormat;

    /* JADX INFO: Access modifiers changed from: private */
    interface SerializableSupplier<T> extends Supplier<T>, Serializable {
    }

    static /* synthetic */ Object lambda$append$9e3d8e65$1(Object obj) {
        return obj;
    }

    static /* synthetic */ Object lambda$append$9e40489f$1(Object obj) {
        return obj;
    }

    static /* synthetic */ Object[] lambda$append$dbd51caa$1(Object[] objArr) {
        return objArr;
    }

    static /* synthetic */ Object[] lambda$append$dbd7d6e4$1(Object[] objArr) {
        return objArr;
    }

    public static final class Builder<T> {
        private T left;
        private T right;
        private ToStringStyle style;
        private boolean testObjectsEquals = true;
        private String toStringFormat = DiffBuilder.TO_STRING_FORMAT;

        public DiffBuilder<T> build() {
            return new DiffBuilder<>(this.left, this.right, this.style, this.testObjectsEquals, this.toStringFormat);
        }

        public Builder<T> setLeft(T t) {
            this.left = t;
            return this;
        }

        public Builder<T> setRight(T t) {
            this.right = t;
            return this;
        }

        public Builder<T> setStyle(ToStringStyle toStringStyle) {
            if (toStringStyle == null) {
                toStringStyle = ToStringStyle.DEFAULT_STYLE;
            }
            this.style = toStringStyle;
            return this;
        }

        public Builder<T> setTestObjectsEquals(boolean z) {
            this.testObjectsEquals = z;
            return this;
        }

        public Builder<T> setToStringFormat(String str) {
            if (str == null) {
                str = DiffBuilder.TO_STRING_FORMAT;
            }
            this.toStringFormat = str;
            return this;
        }
    }

    private static final class SDiff<T> extends Diff<T> {
        private static final long serialVersionUID = 1;
        private final SerializableSupplier<T> leftSupplier;
        private final SerializableSupplier<T> rightSupplier;

        private SDiff(String str, SerializableSupplier<T> serializableSupplier, SerializableSupplier<T> serializableSupplier2, Class<T> cls) {
            super(str, cls);
            this.leftSupplier = (SerializableSupplier) Objects.requireNonNull(serializableSupplier);
            this.rightSupplier = (SerializableSupplier) Objects.requireNonNull(serializableSupplier2);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public T getLeft() {
            return this.leftSupplier.get();
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public T getRight() {
            return this.rightSupplier.get();
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Deprecated
    public DiffBuilder(T t, T t2, ToStringStyle toStringStyle) {
        this(t, t2, toStringStyle, true);
    }

    @Deprecated
    public DiffBuilder(T t, T t2, ToStringStyle toStringStyle, boolean z) {
        this(t, t2, toStringStyle, z, TO_STRING_FORMAT);
    }

    private DiffBuilder(T t, T t2, ToStringStyle toStringStyle, boolean z, String str) {
        this.left = (T) Objects.requireNonNull(t, "left");
        this.right = (T) Objects.requireNonNull(t2, EditStat.ELEMENT_RIGHT);
        this.diffs = new ArrayList();
        this.toStringFormat = str;
        this.style = toStringStyle == null ? ToStringStyle.DEFAULT_STYLE : toStringStyle;
        this.equals = z && Objects.equals(t, t2);
    }

    private <F> DiffBuilder<T> add(String str, SerializableSupplier<F> serializableSupplier, SerializableSupplier<F> serializableSupplier2, Class<F> cls) {
        this.diffs.add(new SDiff(str, serializableSupplier, serializableSupplier2, cls));
        return this;
    }

    public DiffBuilder<T> append(String str, boolean z, boolean z2) {
        return (this.equals || z == z2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda5(z), new DiffBuilder$$ExternalSyntheticLambda6(z2), Boolean.class);
    }

    public DiffBuilder<T> append(String str, boolean[] zArr, boolean[] zArr2) {
        return (this.equals || Arrays.equals(zArr, zArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda24(zArr), new DiffBuilder$$ExternalSyntheticLambda25(zArr2), Boolean[].class);
    }

    public DiffBuilder<T> append(String str, byte b, byte b2) {
        return (this.equals || b == b2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda15(b), new DiffBuilder$$ExternalSyntheticLambda16(b2), Byte.class);
    }

    public DiffBuilder<T> append(String str, byte[] bArr, byte[] bArr2) {
        return (this.equals || Arrays.equals(bArr, bArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda12(bArr), new DiffBuilder$$ExternalSyntheticLambda13(bArr2), Byte[].class);
    }

    public DiffBuilder<T> append(String str, char c, char c2) {
        return (this.equals || c == c2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda1(c), new DiffBuilder$$ExternalSyntheticLambda2(c2), Character.class);
    }

    public DiffBuilder<T> append(String str, char[] cArr, char[] cArr2) {
        return (this.equals || Arrays.equals(cArr, cArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda9(cArr), new DiffBuilder$$ExternalSyntheticLambda10(cArr2), Character[].class);
    }

    public DiffBuilder<T> append(final String str, DiffResult<?> diffResult) {
        Objects.requireNonNull(diffResult, "diffResult");
        if (this.equals) {
            return this;
        }
        diffResult.getDiffs().forEach(new Consumer() { // from class: org.apache.commons.lang3.builder.DiffBuilder$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m4558lambda$append$0$orgapachecommonslang3builderDiffBuilder(str, (Diff) obj);
            }
        });
        return this;
    }

    /* JADX INFO: renamed from: lambda$append$0$org-apache-commons-lang3-builder-DiffBuilder, reason: not valid java name */
    /* synthetic */ void m4558lambda$append$0$orgapachecommonslang3builderDiffBuilder(String str, Diff diff) {
        append(str + "." + diff.getFieldName(), diff.getLeft(), diff.getRight());
    }

    public DiffBuilder<T> append(String str, double d, double d2) {
        return (this.equals || Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda31(d), new DiffBuilder$$ExternalSyntheticLambda32(d2), Double.class);
    }

    public DiffBuilder<T> append(String str, double[] dArr, double[] dArr2) {
        return (this.equals || Arrays.equals(dArr, dArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda26(dArr), new DiffBuilder$$ExternalSyntheticLambda27(dArr2), Double[].class);
    }

    public DiffBuilder<T> append(String str, float f, float f2) {
        return (this.equals || Float.floatToIntBits(f) == Float.floatToIntBits(f2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda33(f), new DiffBuilder$$ExternalSyntheticLambda34(f2), Float.class);
    }

    public DiffBuilder<T> append(String str, float[] fArr, float[] fArr2) {
        return (this.equals || Arrays.equals(fArr, fArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda17(fArr), new DiffBuilder$$ExternalSyntheticLambda18(fArr2), Float[].class);
    }

    public DiffBuilder<T> append(String str, int i, int i2) {
        return (this.equals || i == i2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda21(i), new DiffBuilder$$ExternalSyntheticLambda23(i2), Integer.class);
    }

    public DiffBuilder<T> append(String str, int[] iArr, int[] iArr2) {
        return (this.equals || Arrays.equals(iArr, iArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda28(iArr), new DiffBuilder$$ExternalSyntheticLambda29(iArr2), Integer[].class);
    }

    public DiffBuilder<T> append(String str, long j, long j2) {
        return (this.equals || j == j2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda7(j), new DiffBuilder$$ExternalSyntheticLambda8(j2), Long.class);
    }

    public DiffBuilder<T> append(String str, long[] jArr, long[] jArr2) {
        return (this.equals || Arrays.equals(jArr, jArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda3(jArr), new DiffBuilder$$ExternalSyntheticLambda4(jArr2), Long[].class);
    }

    public DiffBuilder<T> append(String str, Object obj, Object obj2) {
        if (this.equals || obj == obj2) {
            return this;
        }
        Object obj3 = obj != null ? obj : obj2;
        if (!ObjectUtils.isArray(obj3)) {
            return Objects.equals(obj, obj2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda19(obj), new DiffBuilder$$ExternalSyntheticLambda20(obj2), Object.class);
        }
        if (obj3 instanceof boolean[]) {
            return append(str, (boolean[]) obj, (boolean[]) obj2);
        }
        if (obj3 instanceof byte[]) {
            return append(str, (byte[]) obj, (byte[]) obj2);
        }
        if (obj3 instanceof char[]) {
            return append(str, (char[]) obj, (char[]) obj2);
        }
        if (obj3 instanceof double[]) {
            return append(str, (double[]) obj, (double[]) obj2);
        }
        if (obj3 instanceof float[]) {
            return append(str, (float[]) obj, (float[]) obj2);
        }
        if (obj3 instanceof int[]) {
            return append(str, (int[]) obj, (int[]) obj2);
        }
        if (obj3 instanceof long[]) {
            return append(str, (long[]) obj, (long[]) obj2);
        }
        if (obj3 instanceof short[]) {
            return append(str, (short[]) obj, (short[]) obj2);
        }
        return append(str, (Object[]) obj, (Object[]) obj2);
    }

    public DiffBuilder<T> append(String str, Object[] objArr, Object[] objArr2) {
        return (this.equals || Arrays.equals(objArr, objArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda0(objArr), new DiffBuilder$$ExternalSyntheticLambda11(objArr2), Object[].class);
    }

    public DiffBuilder<T> append(String str, short s, short s2) {
        return (this.equals || s == s2) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda35(s), new DiffBuilder$$ExternalSyntheticLambda36(s2), Short.class);
    }

    public DiffBuilder<T> append(String str, short[] sArr, short[] sArr2) {
        return (this.equals || Arrays.equals(sArr, sArr2)) ? this : add(str, new DiffBuilder$$ExternalSyntheticLambda22(sArr), new DiffBuilder$$ExternalSyntheticLambda30(sArr2), Short[].class);
    }

    @Override // org.apache.commons.lang3.builder.Builder
    public DiffResult<T> build() {
        return new DiffResult<>(this.left, this.right, this.diffs, this.style, this.toStringFormat);
    }

    T getLeft() {
        return this.left;
    }

    T getRight() {
        return this.right;
    }
}
