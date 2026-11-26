package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.spi.ContextAwareBase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes.dex */
public class RollingCalendar extends GregorianCalendar {
    static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone(TimeZones.GMT_ID);
    private static final HashMap<Character, PeriodicityType> PATTERN_LETTER_TO_PERIODICITY;
    private static final long serialVersionUID = -5937537740925066161L;
    private String datePattern;
    private PeriodicityType periodicityType;

    /* JADX INFO: renamed from: ch.qos.logback.core.rolling.helper.RollingCalendar$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType;

        static {
            int[] iArr = new int[PeriodicityType.values().length];
            $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType = iArr;
            try {
                iArr[PeriodicityType.TOP_OF_HOUR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_DAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_MILLISECOND.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_SECOND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_MINUTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.HALF_DAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[PeriodicityType.TOP_OF_MONTH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        PATTERN_LETTER_TO_PERIODICITY = linkedHashMap;
        linkedHashMap.put('S', PeriodicityType.TOP_OF_MILLISECOND);
        linkedHashMap.put('s', PeriodicityType.TOP_OF_SECOND);
        linkedHashMap.put('m', PeriodicityType.TOP_OF_MINUTE);
        linkedHashMap.put('h', PeriodicityType.TOP_OF_HOUR);
        linkedHashMap.put('K', PeriodicityType.TOP_OF_HOUR);
        linkedHashMap.put('k', PeriodicityType.TOP_OF_HOUR);
        linkedHashMap.put('H', PeriodicityType.TOP_OF_HOUR);
        linkedHashMap.put('a', PeriodicityType.HALF_DAY);
        linkedHashMap.put('u', PeriodicityType.TOP_OF_DAY);
        linkedHashMap.put('E', PeriodicityType.TOP_OF_DAY);
        linkedHashMap.put('F', PeriodicityType.TOP_OF_DAY);
        linkedHashMap.put('d', PeriodicityType.TOP_OF_DAY);
        linkedHashMap.put('D', PeriodicityType.TOP_OF_DAY);
        linkedHashMap.put('W', PeriodicityType.TOP_OF_WEEK);
        linkedHashMap.put('w', PeriodicityType.TOP_OF_WEEK);
        linkedHashMap.put('M', PeriodicityType.TOP_OF_MONTH);
        linkedHashMap.put('Y', PeriodicityType.TOP_OF_WEEK);
    }

    public RollingCalendar(String str) {
        this(str, GMT_TIMEZONE, Locale.US);
    }

    public RollingCalendar(String str, TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.periodicityType = PeriodicityType.ERRONEOUS;
        this.datePattern = str;
        this.periodicityType = computePeriodicityType();
    }

    private boolean collision(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern, Locale.US);
        simpleDateFormat.setTimeZone(GMT_TIMEZONE);
        return simpleDateFormat.format(new Date(0L)).equals(simpleDateFormat.format(new Date(j)));
    }

    private void roundDownTime(Calendar calendar, String str) {
        if (str.indexOf(83) == -1) {
            calendar.roll(14, -calendar.get(14));
        }
        if (str.indexOf(115) == -1) {
            calendar.roll(13, -calendar.get(13));
        }
        if (str.indexOf(109) == -1) {
            calendar.roll(12, -calendar.get(12));
        }
        if (!Pattern.compile("[hKkH]").matcher(str).find()) {
            calendar.roll(11, -calendar.get(11));
        }
        if (!Pattern.compile("[uEFdD]").matcher(str).find()) {
            calendar.set(5, 1);
        }
        if (Pattern.compile("[MDw]").matcher(str).find()) {
            return;
        }
        calendar.set(2, 0);
    }

    public PeriodicityType computePeriodicityType() {
        if (this.datePattern != null) {
            for (Map.Entry<Character, PeriodicityType> entry : PATTERN_LETTER_TO_PERIODICITY.entrySet()) {
                if (this.datePattern.indexOf(entry.getKey().charValue()) > -1) {
                    return entry.getValue();
                }
            }
        }
        return PeriodicityType.ERRONEOUS;
    }

    public Date getEndOfNextNthPeriod(Date date, int i) {
        int i2;
        setTime(date);
        roundDownTime(this, this.datePattern);
        switch (AnonymousClass1.$SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[this.periodicityType.ordinal()]) {
            case 1:
                i2 = 11;
                break;
            case 2:
                i2 = 5;
                break;
            case 3:
                set(7, getFirstDayOfWeek());
                i2 = 3;
                break;
            case 4:
                i2 = 14;
                break;
            case 5:
                i2 = 13;
                break;
            case 6:
                i2 = 12;
                break;
            case 7:
            default:
                throw new IllegalStateException("Unknown periodicity type.");
            case 8:
                i2 = 2;
                break;
        }
        add(i2, i);
        return getTime();
    }

    public Date getNextTriggeringDate(Date date) {
        return getEndOfNextNthPeriod(date, 1);
    }

    public PeriodicityType getPeriodicityType() {
        return this.periodicityType;
    }

    public boolean isCollisionFree() {
        int i = AnonymousClass1.$SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[this.periodicityType.ordinal()];
        if (i == 1) {
            return !collision(43200000L);
        }
        if (i == 2) {
            return (collision(604800000L) || collision(2678400000L) || collision(31536000000L)) ? false : true;
        }
        if (i != 3) {
            return true;
        }
        return (collision(2937600000L) || collision(31622400000L)) ? false : true;
    }

    public Date normalizeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        roundDownTime(calendar, this.datePattern);
        return calendar.getTime();
    }

    public void printPeriodicity(ContextAwareBase contextAwareBase) {
        String str;
        switch (AnonymousClass1.$SwitchMap$ch$qos$logback$core$rolling$helper$PeriodicityType[this.periodicityType.ordinal()]) {
            case 1:
                str = "Roll-over at the top of every hour.";
                break;
            case 2:
                str = "Roll-over at midnight.";
                break;
            case 3:
                str = "Rollover at the start of week.";
                break;
            case 4:
                str = "Roll-over every millisecond.";
                break;
            case 5:
                str = "Roll-over every second.";
                break;
            case 6:
                str = "Roll-over every minute.";
                break;
            case 7:
                str = "Roll-over at midday and midnight.";
                break;
            case 8:
                str = "Rollover at start of every month.";
                break;
            default:
                str = "Unknown periodicity.";
                break;
        }
        contextAwareBase.addInfo(str);
    }
}
