package org.apache.commons.lang3.time;

import java.util.TimeZone;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes6.dex */
public class TimeZones {
    public static final String GMT_ID = "GMT";
    public static final TimeZone GMT = TimeZone.getTimeZone(GMT_ID);

    public static TimeZone toTimeZone(TimeZone timeZone) {
        return (TimeZone) ObjectUtils.getIfNull(timeZone, (Supplier<TimeZone>) new Supplier() { // from class: org.apache.commons.lang3.time.TimeZones$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return TimeZone.getDefault();
            }
        });
    }

    private TimeZones() {
    }
}
