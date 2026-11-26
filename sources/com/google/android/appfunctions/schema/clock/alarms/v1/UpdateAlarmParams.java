package com.google.android.appfunctions.schema.clock.alarms.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
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
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R&\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0010R\u001b\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010¨\u0006\u001b"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/UpdateAlarmParams;", "", "alarmId", "", Todo.LABEL, "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "time", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", "alarmStatus", "dayPattern", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getAlarmId", "()Ljava/lang/String;", "getLabel", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getTime", "getAlarmStatus$annotations", "()V", "getAlarmStatus", "getDayPattern", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateAlarmParams {
    private final String alarmId;
    private final SetField<String> alarmStatus;
    private final SetField<DayPattern> dayPattern;
    private final SetField<String> label;
    private final SetField<TimeOfDay> time;

    public UpdateAlarmParams(String alarmId, SetField<String> setField, SetField<TimeOfDay> setField2, SetField<String> setField3, SetField<DayPattern> setField4) {
        Intrinsics.checkNotNullParameter(alarmId, "alarmId");
        this.alarmId = alarmId;
        this.label = setField;
        this.time = setField2;
        this.alarmStatus = setField3;
        this.dayPattern = setField4;
    }

    public /* synthetic */ UpdateAlarmParams(String str, SetField setField, SetField setField2, SetField setField3, SetField setField4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3, (i & 16) != 0 ? null : setField4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateAlarmParams)) {
            return false;
        }
        UpdateAlarmParams updateAlarmParams = (UpdateAlarmParams) other;
        return Intrinsics.areEqual(this.alarmId, updateAlarmParams.alarmId) && Intrinsics.areEqual(this.label, updateAlarmParams.label) && Intrinsics.areEqual(this.time, updateAlarmParams.time) && Intrinsics.areEqual(this.alarmStatus, updateAlarmParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, updateAlarmParams.dayPattern);
    }

    public final String getAlarmId() {
        return this.alarmId;
    }

    public final SetField<String> getAlarmStatus() {
        return this.alarmStatus;
    }

    public final SetField<DayPattern> getDayPattern() {
        return this.dayPattern;
    }

    public final SetField<String> getLabel() {
        return this.label;
    }

    public final SetField<TimeOfDay> getTime() {
        return this.time;
    }

    public int hashCode() {
        return Objects.hash(this.alarmId, this.label, this.time, this.alarmStatus, this.dayPattern);
    }
}
