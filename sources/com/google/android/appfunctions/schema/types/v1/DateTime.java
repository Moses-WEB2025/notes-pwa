package com.google.android.appfunctions.schema.types.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "", "timeZone", "", "date", "Lcom/google/android/appfunctions/schema/types/v1/Date;", "timeOfDay", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/Date;Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;)V", "getTimeZone", "()Ljava/lang/String;", "getDate", "()Lcom/google/android/appfunctions/schema/types/v1/Date;", "getTimeOfDay", "()Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DateTime {
    private final Date date;
    private final TimeOfDay timeOfDay;
    private final String timeZone;

    public DateTime(String timeZone, Date date, TimeOfDay timeOfDay) {
        Intrinsics.checkNotNullParameter(timeZone, "timeZone");
        Intrinsics.checkNotNullParameter(date, "date");
        Intrinsics.checkNotNullParameter(timeOfDay, "timeOfDay");
        this.timeZone = timeZone;
        this.date = date;
        this.timeOfDay = timeOfDay;
    }

    public boolean equals(Object other) {
        if (!(other instanceof DateTime)) {
            return false;
        }
        DateTime dateTime = (DateTime) other;
        return Intrinsics.areEqual(this.timeZone, dateTime.timeZone) && Intrinsics.areEqual(this.date, dateTime.date) && Intrinsics.areEqual(this.timeOfDay, dateTime.timeOfDay);
    }

    public final Date getDate() {
        return this.date;
    }

    public final TimeOfDay getTimeOfDay() {
        return this.timeOfDay;
    }

    public final String getTimeZone() {
        return this.timeZone;
    }

    public int hashCode() {
        return Objects.hash(this.timeZone, this.date, this.timeOfDay);
    }
}
