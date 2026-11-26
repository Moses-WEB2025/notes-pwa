package com.google.android.appfunctions.schema.types.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/google/android/appfunctions/schema/types/v1/TimeOfDayRange;", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "endTime", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;)V", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "getEndTime", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class TimeOfDayRange {
    private final TimeOfDay endTime;
    private final TimeOfDay startTime;

    public TimeOfDayRange() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public TimeOfDayRange(TimeOfDay timeOfDay, TimeOfDay timeOfDay2) {
        this.startTime = timeOfDay;
        this.endTime = timeOfDay2;
    }

    public /* synthetic */ TimeOfDayRange(TimeOfDay timeOfDay, TimeOfDay timeOfDay2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : timeOfDay, (i & 2) != 0 ? null : timeOfDay2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof TimeOfDayRange)) {
            return false;
        }
        TimeOfDayRange timeOfDayRange = (TimeOfDayRange) other;
        return Intrinsics.areEqual(this.startTime, timeOfDayRange.startTime) && Intrinsics.areEqual(this.endTime, timeOfDayRange.endTime);
    }

    public final TimeOfDay getEndTime() {
        return this.endTime;
    }

    public final TimeOfDay getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime);
    }
}
