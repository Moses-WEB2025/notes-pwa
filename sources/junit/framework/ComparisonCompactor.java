package junit.framework;

/* JADX INFO: loaded from: classes5.dex */
public class ComparisonCompactor {
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private static final String ELLIPSIS = "...";
    private String fActual;
    private int fContextLength;
    private String fExpected;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactor(int i, String str, String str2) {
        this.fContextLength = i;
        this.fExpected = str;
        this.fActual = str2;
    }

    public String compact(String str) {
        if (this.fExpected == null || this.fActual == null || areStringsEqual()) {
            return Assert.format(str, this.fExpected, this.fActual);
        }
        findCommonPrefix();
        findCommonSuffix();
        return Assert.format(str, compactString(this.fExpected), compactString(this.fActual));
    }

    private String compactString(String str) {
        String str2 = DELTA_START + str.substring(this.fPrefix, (str.length() - this.fSuffix) + 1) + DELTA_END;
        if (this.fPrefix > 0) {
            str2 = computeCommonPrefix() + str2;
        }
        return this.fSuffix > 0 ? str2 + computeCommonSuffix() : str2;
    }

    private void findCommonPrefix() {
        this.fPrefix = 0;
        int iMin = Math.min(this.fExpected.length(), this.fActual.length());
        while (true) {
            int i = this.fPrefix;
            if (i >= iMin || this.fExpected.charAt(i) != this.fActual.charAt(this.fPrefix)) {
                return;
            } else {
                this.fPrefix++;
            }
        }
    }

    private void findCommonSuffix() {
        int length = this.fExpected.length() - 1;
        int length2 = this.fActual.length() - 1;
        while (true) {
            int i = this.fPrefix;
            if (length2 < i || length < i || this.fExpected.charAt(length) != this.fActual.charAt(length2)) {
                break;
            }
            length2--;
            length--;
        }
        this.fSuffix = this.fExpected.length() - length;
    }

    private String computeCommonPrefix() {
        return (this.fPrefix > this.fContextLength ? ELLIPSIS : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
    }

    private String computeCommonSuffix() {
        int iMin = Math.min((this.fExpected.length() - this.fSuffix) + 1 + this.fContextLength, this.fExpected.length());
        StringBuilder sb = new StringBuilder();
        String str = this.fExpected;
        return sb.append(str.substring((str.length() - this.fSuffix) + 1, iMin)).append((this.fExpected.length() - this.fSuffix) + 1 < this.fExpected.length() - this.fContextLength ? ELLIPSIS : "").toString();
    }

    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }
}
