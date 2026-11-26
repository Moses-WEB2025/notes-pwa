package org.apache.commons.lang3.time;

import ch.qos.logback.core.CoreConstants;
import com.google.android.appfunctions.schema.calendar.v1.CalendarKt;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.LocaleUtils;

/* JADX INFO: loaded from: classes6.dex */
public class FastDateParser implements DateParser, Serializable {
    private static final long serialVersionUID = 3;
    private final int century;
    private final Locale locale;
    private final String pattern;
    private transient List<StrategyAndWidth> patterns;
    private final int startYear;
    private final TimeZone timeZone;
    static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
    private static final Comparator<String> LONGER_FIRST_LOWERCASE = Comparator.reverseOrder();
    private static final ConcurrentMap<Locale, Strategy>[] CACHES = new ConcurrentMap[17];
    private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1) { // from class: org.apache.commons.lang3.time.FastDateParser.1
        @Override // org.apache.commons.lang3.time.FastDateParser.NumberStrategy
        int modify(FastDateParser fastDateParser, int i) {
            return i < 100 ? fastDateParser.adjustYear(i) : i;
        }
    };
    private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2) { // from class: org.apache.commons.lang3.time.FastDateParser.2
        @Override // org.apache.commons.lang3.time.FastDateParser.NumberStrategy
        int modify(FastDateParser fastDateParser, int i) {
            return i - 1;
        }
    };
    private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
    private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
    private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
    private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
    private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
    private static final Strategy DAY_OF_WEEK_STRATEGY = new NumberStrategy(7) { // from class: org.apache.commons.lang3.time.FastDateParser.3
        @Override // org.apache.commons.lang3.time.FastDateParser.NumberStrategy
        int modify(FastDateParser fastDateParser, int i) {
            if (i == 7) {
                return 1;
            }
            return i + 1;
        }
    };
    private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
    private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
    private static final Strategy HOUR24_OF_DAY_STRATEGY = new NumberStrategy(11) { // from class: org.apache.commons.lang3.time.FastDateParser.4
        @Override // org.apache.commons.lang3.time.FastDateParser.NumberStrategy
        int modify(FastDateParser fastDateParser, int i) {
            if (i == 24) {
                return 0;
            }
            return i;
        }
    };
    private static final Strategy HOUR12_STRATEGY = new NumberStrategy(10) { // from class: org.apache.commons.lang3.time.FastDateParser.5
        @Override // org.apache.commons.lang3.time.FastDateParser.NumberStrategy
        int modify(FastDateParser fastDateParser, int i) {
            if (i == 12) {
                return 0;
            }
            return i;
        }
    };
    private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
    private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
    private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
    private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);

    private static final class CaseInsensitiveTextStrategy extends PatternStrategy {
        private final int field;
        private final Map<String, Integer> lKeyValues;
        private final Locale locale;

        CaseInsensitiveTextStrategy(int i, Calendar calendar, Locale locale) {
            super();
            this.field = i;
            this.locale = LocaleUtils.toLocale(locale);
            StringBuilder sb = new StringBuilder();
            sb.append("((?iu)");
            this.lKeyValues = FastDateParser.appendDisplayNames(calendar, locale, i, sb);
            sb.setLength(sb.length() - 1);
            sb.append(")");
            createPattern(sb);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.PatternStrategy
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            String lowerCase = str.toLowerCase(this.locale);
            Integer num = this.lKeyValues.get(lowerCase);
            if (num == null) {
                num = this.lKeyValues.get(lowerCase + '.');
            }
            if (9 != this.field || num.intValue() <= 1) {
                calendar.set(this.field, num.intValue());
            }
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.PatternStrategy
        public String toString() {
            return "CaseInsensitiveTextStrategy [field=" + this.field + ", locale=" + this.locale + ", lKeyValues=" + this.lKeyValues + ", pattern=" + this.pattern + "]";
        }
    }

    private static final class CopyQuotedStrategy extends Strategy {
        private final String formatField;

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean isNumber() {
            return false;
        }

        CopyQuotedStrategy(String str) {
            super();
            this.formatField = str;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            for (int i2 = 0; i2 < this.formatField.length(); i2++) {
                int index = parsePosition.getIndex() + i2;
                if (index == str.length()) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
                if (this.formatField.charAt(i2) != str.charAt(index)) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
            }
            parsePosition.setIndex(this.formatField.length() + parsePosition.getIndex());
            return true;
        }

        public String toString() {
            return "CopyQuotedStrategy [formatField=" + this.formatField + "]";
        }
    }

    private static final class ISO8601TimeZoneStrategy extends PatternStrategy {
        private static final Strategy ISO_8601_1_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}))");
        private static final Strategy ISO_8601_2_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}\\d{2}))");
        private static final Strategy ISO_8601_3_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");

        static Strategy getStrategy(int i) {
            if (i == 1) {
                return ISO_8601_1_STRATEGY;
            }
            if (i == 2) {
                return ISO_8601_2_STRATEGY;
            }
            if (i == 3) {
                return ISO_8601_3_STRATEGY;
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        ISO8601TimeZoneStrategy(String str) {
            super();
            createPattern(str);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.PatternStrategy
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            calendar.setTimeZone(FastTimeZone.getGmtTimeZone(str));
        }
    }

    private static class NumberStrategy extends Strategy {
        private final int field;

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean isNumber() {
            return true;
        }

        int modify(FastDateParser fastDateParser, int i) {
            return i;
        }

        NumberStrategy(int i) {
            super();
            this.field = i;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            int index = parsePosition.getIndex();
            int length = str.length();
            if (i == 0) {
                while (index < length && Character.isWhitespace(str.charAt(index))) {
                    index++;
                }
                parsePosition.setIndex(index);
            } else {
                int i2 = i + index;
                if (length > i2) {
                    length = i2;
                }
            }
            while (index < length && Character.isDigit(str.charAt(index))) {
                index++;
            }
            if (parsePosition.getIndex() == index) {
                parsePosition.setErrorIndex(index);
                return false;
            }
            int i3 = Integer.parseInt(str.substring(parsePosition.getIndex(), index));
            parsePosition.setIndex(index);
            calendar.set(this.field, modify(fastDateParser, i3));
            return true;
        }

        public String toString() {
            return "NumberStrategy [field=" + this.field + "]";
        }
    }

    private static abstract class PatternStrategy extends Strategy {
        Pattern pattern;

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean isNumber() {
            return false;
        }

        abstract void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str);

        private PatternStrategy() {
            super();
        }

        void createPattern(String str) {
            this.pattern = Pattern.compile(str);
        }

        void createPattern(StringBuilder sb) {
            createPattern(sb.toString());
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.Strategy
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            Matcher matcher = this.pattern.matcher(str.substring(parsePosition.getIndex()));
            if (!matcher.lookingAt()) {
                parsePosition.setErrorIndex(parsePosition.getIndex());
                return false;
            }
            parsePosition.setIndex(parsePosition.getIndex() + matcher.end(1));
            setCalendar(fastDateParser, calendar, matcher.group(1));
            return true;
        }

        public String toString() {
            return getClass().getSimpleName() + " [pattern=" + this.pattern + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Strategy {
        boolean isNumber() {
            return false;
        }

        abstract boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i);

        private Strategy() {
        }
    }

    private static final class StrategyAndWidth {
        final Strategy strategy;
        final int width;

        StrategyAndWidth(Strategy strategy, int i) {
            this.strategy = (Strategy) Objects.requireNonNull(strategy, "strategy");
            this.width = i;
        }

        int getMaxWidth(ListIterator<StrategyAndWidth> listIterator) {
            if (this.strategy.isNumber() && listIterator.hasNext()) {
                Strategy strategy = listIterator.next().strategy;
                listIterator.previous();
                if (strategy.isNumber()) {
                    return this.width;
                }
            }
            return 0;
        }

        public String toString() {
            return "StrategyAndWidth [strategy=" + this.strategy + ", width=" + this.width + "]";
        }
    }

    private final class StrategyParser {
        private int currentIdx;
        private final Calendar definingCalendar;

        StrategyParser(Calendar calendar) {
            this.definingCalendar = (Calendar) Objects.requireNonNull(calendar, "definingCalendar");
        }

        StrategyAndWidth getNextStrategy() {
            if (this.currentIdx >= FastDateParser.this.pattern.length()) {
                return null;
            }
            char cCharAt = FastDateParser.this.pattern.charAt(this.currentIdx);
            if (CharUtils.isAsciiAlpha(cCharAt)) {
                return letterPattern(cCharAt);
            }
            return literal();
        }

        private StrategyAndWidth letterPattern(char c) {
            int i = this.currentIdx;
            do {
                int i2 = this.currentIdx + 1;
                this.currentIdx = i2;
                if (i2 >= FastDateParser.this.pattern.length()) {
                    break;
                }
            } while (FastDateParser.this.pattern.charAt(this.currentIdx) == c);
            int i3 = this.currentIdx - i;
            return new StrategyAndWidth(FastDateParser.this.getStrategy(c, i3, this.definingCalendar), i3);
        }

        private StrategyAndWidth literal() {
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            while (this.currentIdx < FastDateParser.this.pattern.length()) {
                char cCharAt = FastDateParser.this.pattern.charAt(this.currentIdx);
                if (!z && CharUtils.isAsciiAlpha(cCharAt)) {
                    break;
                }
                if (cCharAt == '\'') {
                    int i = this.currentIdx + 1;
                    this.currentIdx = i;
                    if (i == FastDateParser.this.pattern.length() || FastDateParser.this.pattern.charAt(this.currentIdx) != '\'') {
                        z = !z;
                    }
                }
                this.currentIdx++;
                sb.append(cCharAt);
            }
            if (z) {
                throw new IllegalArgumentException("Unterminated quote");
            }
            String string = sb.toString();
            return new StrategyAndWidth(new CopyQuotedStrategy(string), string.length());
        }
    }

    static class TimeZoneStrategy extends PatternStrategy {
        private static final String GMT_OPTION = "GMT[+-]\\d{1,2}:\\d{2}";
        private static final int ID = 0;
        private static final String RFC_822_TIME_ZONE = "[+-]\\d{4}";
        private final Locale locale;
        private final Map<String, TzInfo> tzNames;

        private static final class TzInfo {
            final int dstOffset;
            final TimeZone zone;

            TzInfo(TimeZone timeZone, boolean z) {
                this.zone = timeZone;
                this.dstOffset = z ? timeZone.getDSTSavings() : 0;
            }

            public String toString() {
                return "TzInfo [zone=" + this.zone + ", dstOffset=" + this.dstOffset + "]";
            }
        }

        TimeZoneStrategy(Locale locale) {
            super();
            this.tzNames = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            this.locale = LocaleUtils.toLocale(locale);
            final StringBuilder sb = new StringBuilder();
            sb.append("((?iu)[+-]\\d{4}|GMT[+-]\\d{1,2}:\\d{2}");
            TreeSet treeSet = new TreeSet(FastDateParser.LONGER_FIRST_LOWERCASE);
            for (String[] strArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
                String str = strArr[0];
                if (!str.equalsIgnoreCase(TimeZones.GMT_ID)) {
                    TimeZone timeZone = TimeZone.getTimeZone(str);
                    TzInfo tzInfo = new TzInfo(timeZone, false);
                    TzInfo tzInfo2 = tzInfo;
                    for (int i = 1; i < strArr.length; i++) {
                        if (i == 3) {
                            tzInfo2 = new TzInfo(timeZone, true);
                        } else if (i == 5) {
                            tzInfo2 = tzInfo;
                        }
                        String str2 = strArr[i];
                        if (str2 != null && treeSet.add(str2)) {
                            this.tzNames.put(str2, tzInfo2);
                        }
                    }
                }
            }
            for (String str3 : (String[]) ArraySorter.sort(TimeZone.getAvailableIDs())) {
                if (!str3.equalsIgnoreCase(TimeZones.GMT_ID)) {
                    TimeZone timeZone2 = TimeZone.getTimeZone(str3);
                    String displayName = timeZone2.getDisplayName(locale);
                    if (treeSet.add(displayName)) {
                        this.tzNames.put(displayName, new TzInfo(timeZone2, timeZone2.observesDaylightTime()));
                    }
                }
            }
            treeSet.forEach(new Consumer() { // from class: org.apache.commons.lang3.time.FastDateParser$TimeZoneStrategy$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FastDateParser.simpleQuote(sb.append('|'), (String) obj);
                }
            });
            sb.append(")");
            createPattern(sb);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.PatternStrategy
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            TimeZone gmtTimeZone = FastTimeZone.getGmtTimeZone(str);
            if (gmtTimeZone != null) {
                calendar.setTimeZone(gmtTimeZone);
                return;
            }
            TzInfo tzInfo = this.tzNames.get(str);
            if (tzInfo == null && (tzInfo = this.tzNames.get(str + '.')) == null) {
                char[] charArray = str.toCharArray();
                throw new IllegalStateException(String.format("Can't find time zone '%s' (%d %s) in %s", str, Integer.valueOf(charArray.length), Arrays.toString(charArray), new TreeSet(this.tzNames.keySet())));
            }
            calendar.set(16, tzInfo.dstOffset);
            calendar.set(15, tzInfo.zone.getRawOffset());
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.PatternStrategy
        public String toString() {
            return "TimeZoneStrategy [locale=" + this.locale + ", tzNames=" + this.tzNames + ", pattern=" + this.pattern + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, Integer> appendDisplayNames(Calendar calendar, Locale locale, int i, final StringBuilder sb) {
        Objects.requireNonNull(calendar, CalendarKt.APP_FUNCTION_SCHEMA_CATEGORY_CALENDAR);
        final HashMap map = new HashMap();
        final Locale locale2 = LocaleUtils.toLocale(locale);
        Map<String, Integer> displayNames = calendar.getDisplayNames(i, 0, locale2);
        final TreeSet treeSet = new TreeSet(LONGER_FIRST_LOWERCASE);
        displayNames.forEach(new BiConsumer() { // from class: org.apache.commons.lang3.time.FastDateParser$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FastDateParser.lambda$appendDisplayNames$0(locale2, treeSet, map, (String) obj, (Integer) obj2);
            }
        });
        treeSet.forEach(new Consumer() { // from class: org.apache.commons.lang3.time.FastDateParser$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FastDateParser.simpleQuote(sb, (String) obj).append('|');
            }
        });
        return map;
    }

    static /* synthetic */ void lambda$appendDisplayNames$0(Locale locale, TreeSet treeSet, Map map, String str, Integer num) {
        String lowerCase = str.toLowerCase(locale);
        if (treeSet.add(lowerCase)) {
            map.put(lowerCase, num);
        }
    }

    static void clear() {
        Stream.of((Object[]) CACHES).filter(new Predicate() { // from class: org.apache.commons.lang3.time.FastDateParser$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((ConcurrentMap) obj);
            }
        }).forEach(new Consumer() { // from class: org.apache.commons.lang3.time.FastDateParser$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ConcurrentMap) obj).clear();
            }
        });
    }

    private static ConcurrentMap<Locale, Strategy> getCache(int i) {
        ConcurrentMap<Locale, Strategy> concurrentMap;
        ConcurrentMap<Locale, Strategy>[] concurrentMapArr = CACHES;
        synchronized (concurrentMapArr) {
            if (concurrentMapArr[i] == null) {
                concurrentMapArr[i] = new ConcurrentHashMap(3);
            }
            concurrentMap = concurrentMapArr[i];
        }
        return concurrentMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    public static StringBuilder simpleQuote(StringBuilder sb, String str) {
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '$' || cCharAt == '.' || cCharAt == '?' || cCharAt == '^' || cCharAt == '[' || cCharAt == '\\' || cCharAt == '{' || cCharAt == '|') {
                sb.append(CoreConstants.ESCAPE_CHAR);
            } else {
                switch (cCharAt) {
                    case '(':
                    case ')':
                    case '*':
                    case '+':
                        sb.append(CoreConstants.ESCAPE_CHAR);
                        break;
                }
            }
            sb.append(cCharAt);
        }
        if (sb.charAt(sb.length() - 1) == '.') {
            sb.append('?');
        }
        return sb;
    }

    protected FastDateParser(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    protected FastDateParser(String str, TimeZone timeZone, Locale locale, Date date) {
        int i;
        this.pattern = (String) Objects.requireNonNull(str, "pattern");
        this.timeZone = (TimeZone) Objects.requireNonNull(timeZone, "timeZone");
        Locale locale2 = LocaleUtils.toLocale(locale);
        this.locale = locale2;
        Calendar calendar = Calendar.getInstance(timeZone, locale2);
        if (date != null) {
            calendar.setTime(date);
            i = calendar.get(1);
        } else if (locale2.equals(JAPANESE_IMPERIAL)) {
            i = 0;
        } else {
            calendar.setTime(new Date());
            i = calendar.get(1) - 80;
        }
        int i2 = (i / 100) * 100;
        this.century = i2;
        this.startYear = i - i2;
        init(calendar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int adjustYear(int i) {
        int i2 = this.century + i;
        return i >= this.startYear ? i2 : i2 + 100;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateParser)) {
            return false;
        }
        FastDateParser fastDateParser = (FastDateParser) obj;
        return this.pattern.equals(fastDateParser.pattern) && this.timeZone.equals(fastDateParser.timeZone) && this.locale.equals(fastDateParser.locale);
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.locale;
    }

    private Strategy getLocaleSpecificStrategy(final int i, final Calendar calendar) {
        return getCache(i).computeIfAbsent(this.locale, new Function() { // from class: org.apache.commons.lang3.time.FastDateParser$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4575xdc739664(i, calendar, (Locale) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getLocaleSpecificStrategy$2$org-apache-commons-lang3-time-FastDateParser, reason: not valid java name */
    /* synthetic */ Strategy m4575xdc739664(int i, Calendar calendar, Locale locale) {
        return i == 15 ? new TimeZoneStrategy(this.locale) : new CaseInsensitiveTextStrategy(i, calendar, this.locale);
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.pattern;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Strategy getStrategy(char c, int i, Calendar calendar) {
        if (c == 'S') {
            return MILLISECOND_STRATEGY;
        }
        if (c == 'a') {
            return getLocaleSpecificStrategy(9, calendar);
        }
        if (c == 'd') {
            return DAY_OF_MONTH_STRATEGY;
        }
        if (c == 'h') {
            return HOUR12_STRATEGY;
        }
        if (c == 'k') {
            return HOUR24_OF_DAY_STRATEGY;
        }
        if (c == 'm') {
            return MINUTE_STRATEGY;
        }
        if (c == 's') {
            return SECOND_STRATEGY;
        }
        if (c == 'u') {
            return DAY_OF_WEEK_STRATEGY;
        }
        if (c == 'w') {
            return WEEK_OF_YEAR_STRATEGY;
        }
        if (c != 'y') {
            if (c != 'z') {
                switch (c) {
                    case 'D':
                        return DAY_OF_YEAR_STRATEGY;
                    case 'E':
                        return getLocaleSpecificStrategy(7, calendar);
                    case 'F':
                        return DAY_OF_WEEK_IN_MONTH_STRATEGY;
                    case 'G':
                        return getLocaleSpecificStrategy(0, calendar);
                    case 'H':
                        return HOUR_OF_DAY_STRATEGY;
                    default:
                        switch (c) {
                            case 'K':
                                return HOUR_STRATEGY;
                            case 'L':
                            case 'M':
                                return i >= 3 ? getLocaleSpecificStrategy(2, calendar) : NUMBER_MONTH_STRATEGY;
                            default:
                                switch (c) {
                                    case 'W':
                                        return WEEK_OF_MONTH_STRATEGY;
                                    case 'X':
                                        return ISO8601TimeZoneStrategy.getStrategy(i);
                                    case 'Y':
                                        break;
                                    case 'Z':
                                        if (i == 2) {
                                            return ISO8601TimeZoneStrategy.ISO_8601_3_STRATEGY;
                                        }
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Format '" + c + "' not supported");
                                }
                                break;
                        }
                        break;
                }
            }
            return getLocaleSpecificStrategy(15, calendar);
        }
        return i > 2 ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public int hashCode() {
        return this.pattern.hashCode() + ((this.timeZone.hashCode() + (this.locale.hashCode() * 13)) * 13);
    }

    private void init(Calendar calendar) {
        this.patterns = new ArrayList();
        StrategyParser strategyParser = new StrategyParser(calendar);
        while (true) {
            StrategyAndWidth nextStrategy = strategyParser.getNextStrategy();
            if (nextStrategy == null) {
                return;
            } else {
                this.patterns.add(nextStrategy);
            }
        }
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = parse(str, parsePosition);
        if (date != null) {
            return date;
        }
        if (this.locale.equals(JAPANESE_IMPERIAL)) {
            throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\nUnparseable date: \"" + str, parsePosition.getErrorIndex());
        }
        throw new ParseException("Unparseable date: " + str, parsePosition.getErrorIndex());
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.clear();
        if (parse(str, parsePosition, calendar)) {
            return calendar.getTime();
        }
        return null;
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        ListIterator<StrategyAndWidth> listIterator = this.patterns.listIterator();
        while (listIterator.hasNext()) {
            StrategyAndWidth next = listIterator.next();
            FastDateParser fastDateParser = this;
            String str2 = str;
            ParsePosition parsePosition2 = parsePosition;
            Calendar calendar2 = calendar;
            if (!next.strategy.parse(fastDateParser, calendar2, str2, parsePosition2, next.getMaxWidth(listIterator))) {
                return false;
            }
            this = fastDateParser;
            calendar = calendar2;
            str = str2;
            parsePosition = parsePosition2;
        }
        return true;
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str) throws ParseException {
        return parse(str);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init(Calendar.getInstance(this.timeZone, this.locale));
    }

    public String toString() {
        return "FastDateParser[" + this.pattern + ", " + this.locale + ", " + this.timeZone.getID() + "]";
    }

    public String toStringAll() {
        return "FastDateParser [pattern=" + this.pattern + ", timeZone=" + this.timeZone + ", locale=" + this.locale + ", century=" + this.century + ", startYear=" + this.startYear + ", patterns=" + this.patterns + "]";
    }
}
