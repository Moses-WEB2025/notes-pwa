package org.apache.commons.lang3.time;

import ch.qos.logback.core.CoreConstants;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes6.dex */
final class GmtTimeZone extends TimeZone {
    private static final int HOURS_PER_DAY = 24;
    private static final int MILLISECONDS_PER_MINUTE = 60000;
    private static final int MINUTES_PER_HOUR = 60;
    static final long serialVersionUID = 1;
    private final int offset;
    private final String zoneId;

    @Override // java.util.TimeZone
    public boolean inDaylightTime(Date date) {
        return false;
    }

    @Override // java.util.TimeZone
    public boolean useDaylightTime() {
        return false;
    }

    private static StringBuilder twoDigits(StringBuilder sb, int i) {
        return sb.append((char) ((i / 10) + 48)).append((char) ((i % 10) + 48));
    }

    GmtTimeZone(boolean z, int i, int i2) {
        if (i >= 24) {
            throw new IllegalArgumentException(i + " hours out of range");
        }
        if (i2 >= 60) {
            throw new IllegalArgumentException(i2 + " minutes out of range");
        }
        int i3 = ((i * 60) + i2) * 60000;
        this.offset = z ? -i3 : i3;
        this.zoneId = twoDigits(twoDigits(new StringBuilder(9).append(TimeZones.GMT_ID).append(z ? CoreConstants.DASH_CHAR : '+'), i).append(CoreConstants.COLON_CHAR), i2).toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GmtTimeZone)) {
            return false;
        }
        GmtTimeZone gmtTimeZone = (GmtTimeZone) obj;
        return this.offset == gmtTimeZone.offset && Objects.equals(this.zoneId, gmtTimeZone.zoneId);
    }

    @Override // java.util.TimeZone
    public String getID() {
        return this.zoneId;
    }

    @Override // java.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.offset;
    }

    @Override // java.util.TimeZone
    public int getRawOffset() {
        return this.offset;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.offset), this.zoneId);
    }

    @Override // java.util.TimeZone
    public void setRawOffset(int i) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "[GmtTimeZone id=\"" + this.zoneId + "\",offset=" + this.offset + ']';
    }
}
