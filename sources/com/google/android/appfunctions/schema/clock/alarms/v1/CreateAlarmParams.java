package com.google.android.appfunctions.schema.clock.alarms.v1;

import com.google.android.appfunctions.schema.types.v1.TimeOfDay;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B?\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\r¨\u0006\u001b"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/CreateAlarmParams;", "", Todo.LABEL, "", "time", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "alarmStatus", "dayPattern", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "externalId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;Ljava/lang/String;Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getTime", "()Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "getAlarmStatus$annotations", "()V", "getAlarmStatus", "getDayPattern", "()Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "getExternalId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateAlarmParams {
    private final String alarmStatus;
    private final DayPattern dayPattern;
    private final String externalId;
    private final String label;
    private final TimeOfDay time;

    public CreateAlarmParams(String str, TimeOfDay time, String str2, DayPattern dayPattern, String str3) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.label = str;
        this.time = time;
        this.alarmStatus = str2;
        this.dayPattern = dayPattern;
        this.externalId = str3;
    }

    public /* synthetic */ CreateAlarmParams(String str, TimeOfDay timeOfDay, String str2, DayPattern dayPattern, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : str, timeOfDay, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : dayPattern, (i & 16) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateAlarmParams)) {
            return false;
        }
        CreateAlarmParams createAlarmParams = (CreateAlarmParams) other;
        return Intrinsics.areEqual(this.label, createAlarmParams.label) && Intrinsics.areEqual(this.time, createAlarmParams.time) && Intrinsics.areEqual(this.alarmStatus, createAlarmParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, createAlarmParams.dayPattern) && Intrinsics.areEqual(this.externalId, createAlarmParams.externalId);
    }

    public final String getAlarmStatus() {
        return this.alarmStatus;
    }

    public final DayPattern getDayPattern() {
        return this.dayPattern;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getLabel() {
        return this.label;
    }

    public final TimeOfDay getTime() {
        return this.time;
    }

    public int hashCode() {
        return Objects.hash(this.label, this.time, this.alarmStatus, this.dayPattern, this.externalId);
    }
}
