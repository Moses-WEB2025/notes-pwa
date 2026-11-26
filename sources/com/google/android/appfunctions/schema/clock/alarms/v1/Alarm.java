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
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\rR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/Alarm;", "", "id", "", Todo.LABEL, "time", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "alarmStatus", "dayPattern", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;Ljava/lang/String;Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;)V", "getId", "()Ljava/lang/String;", "getLabel", "getTime", "()Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "getAlarmStatus$annotations", "()V", "getAlarmStatus", "getDayPattern", "()Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Alarm {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.clock.alarms.v1.AlarmForeground";
    public static final String STATUS_DISMISSED = "DISMISSED";
    public static final String STATUS_FIRING = "FIRING";
    public static final String STATUS_MISSED = "MISSED";
    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_SNOOZED = "SNOOZED";
    public static final String STATUS_UNKNOWN = "UNKNOWN";
    private final String alarmStatus;
    private final DayPattern dayPattern;
    private final String id;
    private final String label;
    private final TimeOfDay time;

    public Alarm(String id, String str, TimeOfDay time, String str2, DayPattern dayPattern) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(time, "time");
        this.id = id;
        this.label = str;
        this.time = time;
        this.alarmStatus = str2;
        this.dayPattern = dayPattern;
    }

    public /* synthetic */ Alarm(String str, String str2, TimeOfDay timeOfDay, String str3, DayPattern dayPattern, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, timeOfDay, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : dayPattern);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Alarm)) {
            return false;
        }
        Alarm alarm = (Alarm) other;
        return Intrinsics.areEqual(this.id, alarm.id) && Intrinsics.areEqual(this.label, alarm.label) && Intrinsics.areEqual(this.time, alarm.time) && Intrinsics.areEqual(this.alarmStatus, alarm.alarmStatus) && Intrinsics.areEqual(this.dayPattern, alarm.dayPattern);
    }

    public final String getAlarmStatus() {
        return this.alarmStatus;
    }

    public final DayPattern getDayPattern() {
        return this.dayPattern;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLabel() {
        return this.label;
    }

    public final TimeOfDay getTime() {
        return this.time;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.label, this.time, this.alarmStatus, this.dayPattern);
    }
}
