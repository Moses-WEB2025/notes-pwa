package ch.qos.logback.core.util;

import com.xiaomi.onetrack.util.ab;
import java.text.DateFormatSymbols;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
class CharSequenceToRegexMapper {
    DateFormatSymbols symbols = DateFormatSymbols.getInstance();

    CharSequenceToRegexMapper() {
    }

    static int[] findMinMaxLengthsInSymbols(String[] strArr) {
        int iMin = Integer.MAX_VALUE;
        int iMax = 0;
        for (String str : strArr) {
            int length = str.length();
            if (length != 0) {
                iMin = Math.min(iMin, length);
                iMax = Math.max(iMax, length);
            }
        }
        return new int[]{iMin, iMax};
    }

    private String getRegexForAmPms() {
        return symbolArrayToRegex(this.symbols.getAmPmStrings());
    }

    private String getRegexForLongDaysOfTheWeek() {
        return symbolArrayToRegex(this.symbols.getWeekdays());
    }

    private String getRegexForLongMonths() {
        return symbolArrayToRegex(this.symbols.getMonths());
    }

    private String getRegexForShortDaysOfTheWeek() {
        return symbolArrayToRegex(this.symbols.getShortWeekdays());
    }

    private String number(int i) {
        return "\\d{" + i + StringSubstitutor.DEFAULT_VAR_END;
    }

    private String symbolArrayToRegex(String[] strArr) {
        int[] iArrFindMinMaxLengthsInSymbols = findMinMaxLengthsInSymbols(strArr);
        return ".{" + iArrFindMinMaxLengthsInSymbols[0] + "," + iArrFindMinMaxLengthsInSymbols[1] + StringSubstitutor.DEFAULT_VAR_END;
    }

    String getRegexForShortMonths() {
        return symbolArrayToRegex(this.symbols.getShortMonths());
    }

    String toRegex(CharSequenceState charSequenceState) {
        StringBuilder sb;
        StringBuilder sbAppend;
        int i = charSequenceState.occurrences;
        char c = charSequenceState.c;
        char c2 = charSequenceState.c;
        if (c2 != 'y') {
            if (c2 == 'z') {
                return ".*";
            }
            switch (c2) {
                case '\'':
                    if (i == 1) {
                        return "";
                    }
                    throw new IllegalStateException("Too many single quotes");
                case '.':
                    return ab.a;
                case 'K':
                case 'S':
                case 'W':
                case 'd':
                case 'h':
                case 'k':
                case 'm':
                case 's':
                case 'w':
                    break;
                case 'M':
                    if (i <= 2) {
                        return number(i);
                    }
                    return i == 3 ? getRegexForShortMonths() : getRegexForLongMonths();
                case 'Z':
                    return "(\\+|-)\\d{4}";
                case '\\':
                    throw new IllegalStateException("Forward slashes are not allowed");
                case 'a':
                    return getRegexForAmPms();
                default:
                    switch (c2) {
                        case 'D':
                        case 'F':
                        case 'H':
                            break;
                        case 'E':
                            return i >= 4 ? getRegexForLongDaysOfTheWeek() : getRegexForShortDaysOfTheWeek();
                        case 'G':
                            return ".*";
                        default:
                            if (i == 1) {
                                sb = new StringBuilder("");
                                sbAppend = sb.append(c);
                            } else {
                                sb = new StringBuilder();
                                sbAppend = sb.append(c).append("{").append(i).append(StringSubstitutor.DEFAULT_VAR_END);
                            }
                            return sbAppend.toString();
                    }
                    break;
            }
        }
        return number(i);
    }
}
