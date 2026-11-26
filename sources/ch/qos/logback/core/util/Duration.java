package ch.qos.logback.core.util;

import com.miui.maml.data.VariableNames;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class Duration {
    static final long DAYS_COEFFICIENT = 86400000;
    private static final int DOUBLE_GROUP = 1;
    private static final String DOUBLE_PART = "([0-9]*(.[0-9]+)?)";
    private static final Pattern DURATION_PATTERN = Pattern.compile("([0-9]*(.[0-9]+)?)\\s*(|milli(second)?|second(e)?|minute|hour|day)s?", 2);
    static final long HOURS_COEFFICIENT = 3600000;
    static final long MINUTES_COEFFICIENT = 60000;
    static final long SECONDS_COEFFICIENT = 1000;
    private static final int UNIT_GROUP = 3;
    private static final String UNIT_PART = "(|milli(second)?|second(e)?|minute|hour|day)s?";
    final long millis;

    public Duration(long j) {
        this.millis = j;
    }

    public static Duration buildByDays(double d) {
        return new Duration((long) (d * 8.64E7d));
    }

    public static Duration buildByHours(double d) {
        return new Duration((long) (d * 3600000.0d));
    }

    public static Duration buildByMilliseconds(double d) {
        return new Duration((long) d);
    }

    public static Duration buildByMinutes(double d) {
        return new Duration((long) (d * 60000.0d));
    }

    public static Duration buildBySeconds(double d) {
        return new Duration((long) (d * 1000.0d));
    }

    public static Duration buildUnbounded() {
        return new Duration(Long.MAX_VALUE);
    }

    public static Duration valueOf(String str) {
        Matcher matcher = DURATION_PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("String value [" + str + "] is not in the expected format.");
        }
        String strGroup = matcher.group(1);
        String strGroup2 = matcher.group(3);
        double dDoubleValue = Double.valueOf(strGroup).doubleValue();
        if (strGroup2.equalsIgnoreCase("milli") || strGroup2.equalsIgnoreCase("millisecond") || strGroup2.length() == 0) {
            return buildByMilliseconds(dDoubleValue);
        }
        if (strGroup2.equalsIgnoreCase(VariableNames.VAR_SECOND) || strGroup2.equalsIgnoreCase("seconde")) {
            return buildBySeconds(dDoubleValue);
        }
        if (strGroup2.equalsIgnoreCase(VariableNames.VAR_MINUTE)) {
            return buildByMinutes(dDoubleValue);
        }
        if (strGroup2.equalsIgnoreCase("hour")) {
            return buildByHours(dDoubleValue);
        }
        if (strGroup2.equalsIgnoreCase("day")) {
            return buildByDays(dDoubleValue);
        }
        throw new IllegalStateException("Unexpected " + strGroup2);
    }

    public long getMilliseconds() {
        return this.millis;
    }

    public String toString() {
        StringBuilder sbAppend;
        String str;
        long j = this.millis;
        if (j < 1000) {
            sbAppend = new StringBuilder().append(this.millis);
            str = " milliseconds";
        } else if (j < 60000) {
            sbAppend = new StringBuilder().append(this.millis / 1000);
            str = " seconds";
        } else if (j < 3600000) {
            sbAppend = new StringBuilder().append(this.millis / 60000);
            str = " minutes";
        } else {
            sbAppend = new StringBuilder().append(this.millis / 3600000);
            str = " hours";
        }
        return sbAppend.append(str).toString();
    }
}
