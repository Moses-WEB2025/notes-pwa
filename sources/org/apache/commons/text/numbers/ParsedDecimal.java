package org.apache.commons.text.numbers;

/* JADX INFO: loaded from: classes6.dex */
final class ParsedDecimal {
    private static final int DECIMAL_RADIX = 10;
    private static final char DECIMAL_SEP_CHAR = '.';
    private static final int ENG_EXPONENT_MOD = 3;
    private static final char EXPONENT_CHAR = 'E';
    private static final char MINUS_CHAR = '-';
    private static final int ROUND_CENTER = 5;
    private static final int THOUSANDS_GROUP_SIZE = 3;
    private static final char ZERO_CHAR = '0';
    int digitCount;
    final int[] digits;
    int exponent;
    final boolean negative;
    private char[] outputChars;
    private int outputIdx;

    interface FormatOptions {
        char getDecimalSeparator();

        char[] getDigits();

        char[] getExponentSeparatorChars();

        char getGroupingSeparator();

        char getMinusSign();

        boolean isAlwaysIncludeExponent();

        boolean isGroupThousands();

        boolean isIncludeFractionPlaceholder();

        boolean isSignedZero();
    }

    private static int digitValue(char c) {
        return c - '0';
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [int] */
    /* JADX WARN: Type inference failed for: r3v4 */
    public static ParsedDecimal from(double d) {
        if (!Double.isFinite(d)) {
            throw new IllegalArgumentException("Double is not finite");
        }
        char[] charArray = Double.toString(d).toCharArray();
        ?? r0 = charArray[0] == '-' ? 1 : 0;
        int[] iArr = new int[(charArray.length - r0) - 1];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        ?? r3 = r0;
        while (r3 < charArray.length) {
            char c = charArray[r3];
            if (c == '.') {
                z = true;
                i2 = i;
            } else {
                if (c == 'E') {
                    break;
                }
                if (c != '0' || i > 0) {
                    int iDigitValue = digitValue(c);
                    int i4 = i + 1;
                    iArr[i] = iDigitValue;
                    if (iDigitValue > 0) {
                        i3 = i4;
                    }
                    i = i4;
                } else if (z) {
                    i2--;
                }
            }
            r3++;
        }
        if (i > 0) {
            return new ParsedDecimal(r0, iArr, i3, ((r3 < charArray.length ? parseExponent(charArray, r3 + 1) : 0) + i2) - i3);
        }
        return new ParsedDecimal(r0, new int[]{0}, 1, 0);
    }

    private static int parseExponent(char[] cArr, int i) {
        int iDigitValue = 0;
        boolean z = cArr[i] == '-';
        int i2 = z ? i2 + 1 : i + 1;
        while (i2 < cArr.length) {
            iDigitValue = (iDigitValue * 10) + digitValue(cArr[i2]);
        }
        return z ? -iDigitValue : iDigitValue;
    }

    private ParsedDecimal(boolean z, int[] iArr, int i, int i2) {
        this.negative = z;
        this.digits = iArr;
        this.digitCount = i;
        this.exponent = i2;
    }

    private void append(char c) {
        char[] cArr = this.outputChars;
        int i = this.outputIdx;
        this.outputIdx = i + 1;
        cArr[i] = c;
    }

    private void append(char[] cArr) {
        for (char c : cArr) {
            append(c);
        }
    }

    private void appendFraction(int i, int i2, FormatOptions formatOptions) {
        char[] digits = formatOptions.getDigits();
        char c = digits[0];
        if (i2 < this.digitCount) {
            append(formatOptions.getDecimalSeparator());
            for (int i3 = 0; i3 < i; i3++) {
                append(c);
            }
            while (i2 < this.digitCount) {
                appendLocalizedDigit(this.digits[i2], digits);
                i2++;
            }
            return;
        }
        if (formatOptions.isIncludeFractionPlaceholder()) {
            append(formatOptions.getDecimalSeparator());
            append(c);
        }
    }

    private void appendLocalizedDigit(int i, char[] cArr) {
        append(cArr[i]);
    }

    private int appendWhole(int i, FormatOptions formatOptions) {
        if (shouldIncludeMinus(formatOptions)) {
            append(formatOptions.getMinusSign());
        }
        char[] digits = formatOptions.getDigits();
        int i2 = 0;
        char c = digits[0];
        int iMax = Math.max(0, Math.min(i, this.digitCount));
        if (iMax <= 0) {
            append(c);
            return iMax;
        }
        while (i2 < iMax) {
            appendLocalizedDigit(this.digits[i2], digits);
            i2++;
        }
        while (i2 < i) {
            append(c);
            i2++;
        }
        return iMax;
    }

    private int appendWholeGrouped(int i, FormatOptions formatOptions) {
        if (shouldIncludeMinus(formatOptions)) {
            append(formatOptions.getMinusSign());
        }
        char[] digits = formatOptions.getDigits();
        int i2 = 0;
        char c = digits[0];
        char groupingSeparator = formatOptions.getGroupingSeparator();
        int iMax = Math.max(0, Math.min(i, this.digitCount));
        if (iMax <= 0) {
            append(c);
            return iMax;
        }
        int i3 = i;
        while (i2 < iMax) {
            appendLocalizedDigit(this.digits[i2], digits);
            if (requiresGroupingSeparatorAfterPosition(i3)) {
                append(groupingSeparator);
            }
            i2++;
            i3--;
        }
        while (i2 < i) {
            append(c);
            if (requiresGroupingSeparatorAfterPosition(i3)) {
                append(groupingSeparator);
            }
            i2++;
            i3--;
        }
        return iMax;
    }

    private int getDigitStringSize(int i, FormatOptions formatOptions) {
        int i2 = this.digitCount;
        if (shouldIncludeMinus(formatOptions)) {
            i2++;
        }
        if (i < 1) {
            return i2 + Math.abs(i) + 2;
        }
        int i3 = this.digitCount;
        if (i < i3) {
            return i2 + 1;
        }
        int i4 = i2 + (i - i3);
        return formatOptions.isIncludeFractionPlaceholder() ? i4 + 2 : i4;
    }

    public int getExponent() {
        return this.exponent;
    }

    private int getPlainStringSize(int i, FormatOptions formatOptions) {
        int digitStringSize = getDigitStringSize(i, formatOptions);
        return (!formatOptions.isGroupThousands() || i <= 0) ? digitStringSize : digitStringSize + ((i - 1) / 3);
    }

    public int getScientificExponent() {
        return (this.digitCount + this.exponent) - 1;
    }

    boolean isZero() {
        return this.digits[0] == 0;
    }

    public void maxPrecision(int i) {
        if (i <= 0 || i >= this.digitCount) {
            return;
        }
        if (shouldRoundUp(i)) {
            roundUp(i);
        } else {
            truncate(i);
        }
    }

    private String outputString() {
        String strValueOf = String.valueOf(this.outputChars);
        this.outputChars = null;
        return strValueOf;
    }

    private void prepareOutput(int i) {
        this.outputChars = new char[i];
        this.outputIdx = 0;
    }

    private boolean requiresGroupingSeparatorAfterPosition(int i) {
        return i > 1 && i % 3 == 1;
    }

    public void round(int i) {
        int i2 = this.exponent;
        if (i > i2) {
            int i3 = this.digitCount + i2;
            if (i < i3) {
                maxPrecision(i3 - i);
            } else if (i == i3 && shouldRoundUp(0)) {
                setSingleDigitValue(1, i);
            } else {
                setSingleDigitValue(0, 0);
            }
        }
    }

    private void roundUp(int i) {
        int i2 = this.digitCount - i;
        int i3 = i - 1;
        while (i3 >= 0) {
            int[] iArr = this.digits;
            int i4 = iArr[i3] + 1;
            if (i4 < 10) {
                iArr[i3] = i4;
                break;
            } else {
                i2++;
                i3--;
            }
        }
        if (i3 < 0) {
            setSingleDigitValue(1, this.exponent + i2);
        } else {
            truncate(this.digitCount - i2);
        }
    }

    private void setSingleDigitValue(int i, int i2) {
        this.digits[0] = i;
        this.digitCount = 1;
        this.exponent = i2;
    }

    private boolean shouldIncludeExponent(int i, FormatOptions formatOptions) {
        return i != 0 || formatOptions.isAlwaysIncludeExponent();
    }

    private boolean shouldIncludeMinus(FormatOptions formatOptions) {
        if (this.negative) {
            return formatOptions.isSignedZero() || !isZero();
        }
        return false;
    }

    private boolean shouldRoundUp(int i) {
        int[] iArr = this.digits;
        int i2 = iArr[i];
        if (i2 <= 5) {
            if (i2 != 5) {
                return false;
            }
            if (i >= this.digitCount - 1 && iArr[i - 1] % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    public String toEngineeringString(FormatOptions formatOptions) {
        return toScientificString(Math.floorMod(getScientificExponent(), 3) + 1, formatOptions);
    }

    public String toPlainString(FormatOptions formatOptions) {
        int iAppendWhole;
        int i = this.digitCount + this.exponent;
        int iAbs = i < 1 ? Math.abs(i) : 0;
        prepareOutput(getPlainStringSize(i, formatOptions));
        if (formatOptions.isGroupThousands()) {
            iAppendWhole = appendWholeGrouped(i, formatOptions);
        } else {
            iAppendWhole = appendWhole(i, formatOptions);
        }
        appendFraction(iAbs, iAppendWhole, formatOptions);
        return outputString();
    }

    public String toScientificString(FormatOptions formatOptions) {
        return toScientificString(1, formatOptions);
    }

    private String toScientificString(int i, FormatOptions formatOptions) {
        int i2 = (this.digitCount + this.exponent) - i;
        int iAbs = Math.abs(i2);
        boolean zShouldIncludeExponent = shouldIncludeExponent(i2, formatOptions);
        boolean z = i2 < 0;
        int digitStringSize = getDigitStringSize(i, formatOptions);
        if (zShouldIncludeExponent) {
            digitStringSize += formatOptions.getExponentSeparatorChars().length + (iAbs > 0 ? 1 + ((int) Math.floor(Math.log10(iAbs))) : 1);
            if (z) {
                digitStringSize++;
            }
        }
        prepareOutput(digitStringSize);
        appendFraction(0, appendWhole(i, formatOptions), formatOptions);
        if (zShouldIncludeExponent) {
            append(formatOptions.getExponentSeparatorChars());
            if (z) {
                append(formatOptions.getMinusSign());
            }
            char[] digits = formatOptions.getDigits();
            for (int i3 = digitStringSize - 1; i3 >= this.outputIdx; i3--) {
                this.outputChars[i3] = digits[iAbs % 10];
                iAbs /= 10;
            }
            this.outputIdx = digitStringSize;
        }
        return outputString();
    }

    private void truncate(int i) {
        for (int i2 = i - 1; i2 > 0 && this.digits[i2] == 0; i2--) {
            i--;
        }
        this.exponent += this.digitCount - i;
        this.digitCount = i;
    }
}
