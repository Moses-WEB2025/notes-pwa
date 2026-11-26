package org.apache.commons.text.numbers;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.aicr.constant.Constants;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes6.dex */
public enum DoubleFormat {
    PLAIN(new Function() { // from class: org.apache.commons.text.numbers.DoubleFormat$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return new DoubleFormat.PlainDoubleFormat((DoubleFormat.Builder) obj);
        }
    }),
    SCIENTIFIC(new Function() { // from class: org.apache.commons.text.numbers.DoubleFormat$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return new DoubleFormat.ScientificDoubleFormat((DoubleFormat.Builder) obj);
        }
    }),
    ENGINEERING(new Function() { // from class: org.apache.commons.text.numbers.DoubleFormat$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return new DoubleFormat.EngineeringDoubleFormat((DoubleFormat.Builder) obj);
        }
    }),
    MIXED(new Function() { // from class: org.apache.commons.text.numbers.DoubleFormat$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return new DoubleFormat.MixedDoubleFormat((DoubleFormat.Builder) obj);
        }
    });

    private final Function<Builder, DoubleFunction<String>> factory;

    private static abstract class AbstractDoubleFormat implements DoubleFunction<String>, ParsedDecimal.FormatOptions {
        private final boolean alwaysIncludeExponent;
        private final char decimalSeparator;
        private final char[] digits;
        private final char[] exponentSeparatorChars;
        private final boolean fractionPlaceholder;
        private final boolean groupThousands;
        private final char groupingSeparator;
        private final int maxPrecision;
        private final int minDecimalExponent;
        private final char minusSign;
        private final String nan;
        private final String negativeInfinity;
        private final String positiveInfinity;
        private final boolean signedZero;

        protected abstract String applyFiniteInternal(ParsedDecimal parsedDecimal);

        AbstractDoubleFormat(Builder builder) {
            this.maxPrecision = builder.maxPrecision;
            this.minDecimalExponent = builder.minDecimalExponent;
            this.positiveInfinity = builder.infinity;
            this.negativeInfinity = builder.minusSign + builder.infinity;
            this.nan = builder.nan;
            this.fractionPlaceholder = builder.fractionPlaceholder;
            this.signedZero = builder.signedZero;
            this.digits = builder.digits.toCharArray();
            this.decimalSeparator = builder.decimalSeparator;
            this.groupingSeparator = builder.groupingSeparator;
            this.groupThousands = builder.groupThousands;
            this.minusSign = builder.minusSign;
            this.exponentSeparatorChars = builder.exponentSeparator.toCharArray();
            this.alwaysIncludeExponent = builder.alwaysIncludeExponent;
        }

        @Override // java.util.function.DoubleFunction
        public String apply(double d) {
            if (Double.isFinite(d)) {
                return applyFinite(d);
            }
            if (Double.isInfinite(d)) {
                return d > 0.0d ? this.positiveInfinity : this.negativeInfinity;
            }
            return this.nan;
        }

        private String applyFinite(double d) {
            ParsedDecimal parsedDecimalFrom = ParsedDecimal.from(d);
            int iMax = Math.max(parsedDecimalFrom.getExponent(), this.minDecimalExponent);
            if (this.maxPrecision > 0) {
                iMax = Math.max((parsedDecimalFrom.getScientificExponent() - this.maxPrecision) + 1, iMax);
            }
            parsedDecimalFrom.round(iMax);
            return applyFiniteInternal(parsedDecimalFrom);
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public char getDecimalSeparator() {
            return this.decimalSeparator;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public char[] getDigits() {
            return this.digits;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public char[] getExponentSeparatorChars() {
            return this.exponentSeparatorChars;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public char getGroupingSeparator() {
            return this.groupingSeparator;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public char getMinusSign() {
            return this.minusSign;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public boolean isAlwaysIncludeExponent() {
            return this.alwaysIncludeExponent;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public boolean isGroupThousands() {
            return this.groupThousands;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public boolean isIncludeFractionPlaceholder() {
            return this.fractionPlaceholder;
        }

        @Override // org.apache.commons.text.numbers.ParsedDecimal.FormatOptions
        public boolean isSignedZero() {
            return this.signedZero;
        }
    }

    public static final class Builder implements Supplier<DoubleFunction<String>> {
        private static final String DEFAULT_DECIMAL_DIGITS = "0123456789";
        private static final int DEFAULT_PLAIN_FORMAT_MAX_DECIMAL_EXPONENT = 6;
        private static final int DEFAULT_PLAIN_FORMAT_MIN_DECIMAL_EXPONENT = -3;
        private boolean alwaysIncludeExponent;
        private char decimalSeparator;
        private String digits;
        private String exponentSeparator;
        private final Function<Builder, DoubleFunction<String>> factory;
        private boolean fractionPlaceholder;
        private boolean groupThousands;
        private char groupingSeparator;
        private String infinity;
        private int maxPrecision;
        private int minDecimalExponent;
        private char minusSign;
        private String nan;
        private int plainFormatMaxDecimalExponent;
        private int plainFormatMinDecimalExponent;
        private boolean signedZero;

        private static String getDigitString(DecimalFormatSymbols decimalFormatSymbols) {
            int zeroDigit = decimalFormatSymbols.getZeroDigit() - DEFAULT_DECIMAL_DIGITS.charAt(0);
            char[] cArr = new char[DEFAULT_DECIMAL_DIGITS.length()];
            for (int i = 0; i < DEFAULT_DECIMAL_DIGITS.length(); i++) {
                cArr[i] = (char) (DEFAULT_DECIMAL_DIGITS.charAt(i) + zeroDigit);
            }
            return String.valueOf(cArr);
        }

        private Builder(Function<Builder, DoubleFunction<String>> function) {
            this.minDecimalExponent = Integer.MIN_VALUE;
            this.plainFormatMaxDecimalExponent = 6;
            this.plainFormatMinDecimalExponent = -3;
            this.infinity = "Infinity";
            this.nan = Constants.NFC_INPUT_PLACEHOLDER;
            this.fractionPlaceholder = true;
            this.signedZero = true;
            this.digits = DEFAULT_DECIMAL_DIGITS;
            this.decimalSeparator = '.';
            this.groupingSeparator = CoreConstants.COMMA_CHAR;
            this.minusSign = CoreConstants.DASH_CHAR;
            this.exponentSeparator = ExifInterface.LONGITUDE_EAST;
            this.factory = function;
        }

        public Builder allowSignedZero(boolean z) {
            this.signedZero = z;
            return this;
        }

        public Builder alwaysIncludeExponent(boolean z) {
            this.alwaysIncludeExponent = z;
            return this;
        }

        @Deprecated
        public DoubleFunction<String> build() {
            return get();
        }

        public Builder decimalSeparator(char c) {
            this.decimalSeparator = c;
            return this;
        }

        public Builder digits(String str) {
            Objects.requireNonNull(str, "digits");
            if (str.length() != DEFAULT_DECIMAL_DIGITS.length()) {
                throw new IllegalArgumentException("Digits string must contain exactly " + DEFAULT_DECIMAL_DIGITS.length() + " characters.");
            }
            this.digits = str;
            return this;
        }

        public Builder exponentSeparator(String str) {
            this.exponentSeparator = (String) Objects.requireNonNull(str, "exponentSeparator");
            return this;
        }

        public Builder formatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
            Objects.requireNonNull(decimalFormatSymbols, "symbols");
            return digits(getDigitString(decimalFormatSymbols)).decimalSeparator(decimalFormatSymbols.getDecimalSeparator()).groupingSeparator(decimalFormatSymbols.getGroupingSeparator()).minusSign(decimalFormatSymbols.getMinusSign()).exponentSeparator(decimalFormatSymbols.getExponentSeparator()).infinity(decimalFormatSymbols.getInfinity()).nan(decimalFormatSymbols.getNaN());
        }

        @Override // java.util.function.Supplier
        public DoubleFunction<String> get() {
            return this.factory.apply(this);
        }

        public Builder groupingSeparator(char c) {
            this.groupingSeparator = c;
            return this;
        }

        public Builder groupThousands(boolean z) {
            this.groupThousands = z;
            return this;
        }

        public Builder includeFractionPlaceholder(boolean z) {
            this.fractionPlaceholder = z;
            return this;
        }

        public Builder infinity(String str) {
            this.infinity = (String) Objects.requireNonNull(str, "infinity");
            return this;
        }

        public Builder maxPrecision(int i) {
            this.maxPrecision = i;
            return this;
        }

        public Builder minDecimalExponent(int i) {
            this.minDecimalExponent = i;
            return this;
        }

        public Builder minusSign(char c) {
            this.minusSign = c;
            return this;
        }

        public Builder nan(String str) {
            this.nan = (String) Objects.requireNonNull(str, "nan");
            return this;
        }

        public Builder plainFormatMaxDecimalExponent(int i) {
            this.plainFormatMaxDecimalExponent = i;
            return this;
        }

        public Builder plainFormatMinDecimalExponent(int i) {
            this.plainFormatMinDecimalExponent = i;
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class EngineeringDoubleFormat extends AbstractDoubleFormat {
        EngineeringDoubleFormat(Builder builder) {
            super(builder);
        }

        @Override // org.apache.commons.text.numbers.DoubleFormat.AbstractDoubleFormat
        public String applyFiniteInternal(ParsedDecimal parsedDecimal) {
            return parsedDecimal.toEngineeringString(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MixedDoubleFormat extends AbstractDoubleFormat {
        private final int plainMaxExponent;
        private final int plainMinExponent;

        MixedDoubleFormat(Builder builder) {
            super(builder);
            this.plainMaxExponent = builder.plainFormatMaxDecimalExponent;
            this.plainMinExponent = builder.plainFormatMinDecimalExponent;
        }

        @Override // org.apache.commons.text.numbers.DoubleFormat.AbstractDoubleFormat
        protected String applyFiniteInternal(ParsedDecimal parsedDecimal) {
            int scientificExponent = parsedDecimal.getScientificExponent();
            if (scientificExponent <= this.plainMaxExponent && scientificExponent >= this.plainMinExponent) {
                return parsedDecimal.toPlainString(this);
            }
            return parsedDecimal.toScientificString(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PlainDoubleFormat extends AbstractDoubleFormat {
        PlainDoubleFormat(Builder builder) {
            super(builder);
        }

        @Override // org.apache.commons.text.numbers.DoubleFormat.AbstractDoubleFormat
        protected String applyFiniteInternal(ParsedDecimal parsedDecimal) {
            return parsedDecimal.toPlainString(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ScientificDoubleFormat extends AbstractDoubleFormat {
        ScientificDoubleFormat(Builder builder) {
            super(builder);
        }

        @Override // org.apache.commons.text.numbers.DoubleFormat.AbstractDoubleFormat
        public String applyFiniteInternal(ParsedDecimal parsedDecimal) {
            return parsedDecimal.toScientificString(this);
        }
    }

    DoubleFormat(Function function) {
        this.factory = function;
    }

    public Builder builder() {
        return new Builder(this.factory);
    }
}
