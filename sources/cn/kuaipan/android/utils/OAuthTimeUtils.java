package cn.kuaipan.android.utils;

import android.os.SystemClock;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UnknownFormatConversionException;

/* JADX INFO: loaded from: classes.dex */
public class OAuthTimeUtils {
    private static final SimpleDateFormat sFormat;
    private static final StringBuffer sFormatBuffer;
    private static final FieldPosition sFormatFieldPos;
    private static final ParsePosition sFormatPosition;
    private static long sTimeDiff;

    public static long currentTime() {
        if (sTimeDiff == 0) {
            return System.currentTimeMillis();
        }
        return SystemClock.elapsedRealtime() + sTimeDiff;
    }

    public static void setRealTime(long j) {
        sTimeDiff = j - SystemClock.elapsedRealtime();
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sFormat = simpleDateFormat;
        sFormatPosition = new ParsePosition(0);
        sFormatBuffer = new StringBuffer();
        sFormatFieldPos = new FieldPosition(0);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
    }

    public static synchronized Date parser(String str, Date date) {
        if (str == null) {
            return date;
        }
        try {
            ParsePosition parsePosition = sFormatPosition;
            parsePosition.setErrorIndex(-1);
            parsePosition.setIndex(0);
            Date date2 = sFormat.parse(str, parsePosition);
            if (parsePosition.getErrorIndex() != -1) {
                throw new UnknownFormatConversionException("Date: " + str);
            }
            if (parsePosition.getIndex() != 0) {
                date = date2;
            }
            return date;
        } catch (Exception unused) {
            throw new UnknownFormatConversionException("Date:" + str);
        }
    }

    public static synchronized String toString(long j) {
        StringBuffer stringBuffer;
        stringBuffer = sFormatBuffer;
        stringBuffer.delete(0, stringBuffer.length());
        return sFormat.format(new Date(j), stringBuffer, sFormatFieldPos).toString();
    }
}
