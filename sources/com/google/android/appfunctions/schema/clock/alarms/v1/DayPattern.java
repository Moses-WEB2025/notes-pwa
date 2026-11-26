package com.google.android.appfunctions.schema.clock.alarms.v1;

import com.google.android.appfunctions.schema.types.v1.Date;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "", "date", "Lcom/google/android/appfunctions/schema/types/v1/Date;", "recurrencePattern", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/Date;Ljava/lang/String;)V", "getDate", "()Lcom/google/android/appfunctions/schema/types/v1/Date;", "getRecurrencePattern", "()Ljava/lang/String;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DayPattern {
    private final Date date;
    private final String recurrencePattern;

    public DayPattern() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public DayPattern(Date date, String str) {
        this.date = date;
        this.recurrencePattern = str;
    }

    public /* synthetic */ DayPattern(Date date, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : date, (i & 2) != 0 ? null : str);
    }

    public boolean equals(Object other) {
        if (!(other instanceof DayPattern)) {
            return false;
        }
        DayPattern dayPattern = (DayPattern) other;
        return Intrinsics.areEqual(this.date, dayPattern.date) && Intrinsics.areEqual(this.recurrencePattern, dayPattern.recurrencePattern);
    }

    public final Date getDate() {
        return this.date;
    }

    public final String getRecurrencePattern() {
        return this.recurrencePattern;
    }

    public int hashCode() {
        return Objects.hash(this.date, this.recurrencePattern);
    }
}
