package com.google.android.appfunctions.schema.types.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0012"}, d2 = {"Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "", "hours", "", "minutes", "seconds", "nanos", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIII)V", "getHours", "()I", "getMinutes", "getSeconds", "getNanos", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class TimeOfDay {
    private final int hours;
    private final int minutes;
    private final int nanos;
    private final int seconds;

    public TimeOfDay(int i, int i2, int i3, int i4) {
        this.hours = i;
        this.minutes = i2;
        this.seconds = i3;
        this.nanos = i4;
    }

    public boolean equals(Object other) {
        if (!(other instanceof TimeOfDay)) {
            return false;
        }
        TimeOfDay timeOfDay = (TimeOfDay) other;
        return this.hours == timeOfDay.hours && this.minutes == timeOfDay.minutes && this.seconds == timeOfDay.seconds && this.nanos == timeOfDay.nanos;
    }

    public final int getHours() {
        return this.hours;
    }

    public final int getMinutes() {
        return this.minutes;
    }

    public final int getNanos() {
        return this.nanos;
    }

    public final int getSeconds() {
        return this.seconds;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.hours), Integer.valueOf(this.minutes), Integer.valueOf(this.seconds), Integer.valueOf(this.nanos));
    }
}
