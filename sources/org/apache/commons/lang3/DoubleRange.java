package org.apache.commons.lang3;

/* JADX INFO: loaded from: classes6.dex */
public final class DoubleRange extends NumberRange<Double> {
    private static final long serialVersionUID = 1;

    public static DoubleRange of(double d, double d2) {
        return of(Double.valueOf(d), Double.valueOf(d2));
    }

    public static DoubleRange of(Double d, Double d2) {
        return new DoubleRange(d, d2);
    }

    private DoubleRange(Double d, Double d2) {
        super(d, d2, null);
    }
}
