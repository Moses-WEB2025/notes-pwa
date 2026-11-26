package com.google.android.appfunctions.schema.tasks.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u001b\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u001b\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u001b\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u001b\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/UpdateTaskParams;", "", CallMethod.ARG_TASK_ID, "", "title", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "description", "completed", "", "dateTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "allDay", "recurrenceSchedule", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getTaskId", "()Ljava/lang/String;", "getTitle", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getDescription", "getCompleted", "getDateTime", "getAllDay", "getRecurrenceSchedule", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateTaskParams {
    private final SetField<Boolean> allDay;
    private final SetField<Boolean> completed;
    private final SetField<DateTime> dateTime;
    private final SetField<String> description;
    private final SetField<String> recurrenceSchedule;
    private final String taskId;
    private final SetField<String> title;

    public UpdateTaskParams(String taskId, SetField<String> setField, SetField<String> setField2, SetField<Boolean> setField3, SetField<DateTime> setField4, SetField<Boolean> setField5, SetField<String> setField6) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        this.taskId = taskId;
        this.title = setField;
        this.description = setField2;
        this.completed = setField3;
        this.dateTime = setField4;
        this.allDay = setField5;
        this.recurrenceSchedule = setField6;
    }

    public /* synthetic */ UpdateTaskParams(String str, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3, (i & 16) != 0 ? null : setField4, (i & 32) != 0 ? null : setField5, (i & 64) != 0 ? null : setField6);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateTaskParams)) {
            return false;
        }
        UpdateTaskParams updateTaskParams = (UpdateTaskParams) other;
        return Intrinsics.areEqual(this.taskId, updateTaskParams.taskId) && Intrinsics.areEqual(this.title, updateTaskParams.title) && Intrinsics.areEqual(this.description, updateTaskParams.description) && Intrinsics.areEqual(this.completed, updateTaskParams.completed) && Intrinsics.areEqual(this.dateTime, updateTaskParams.dateTime) && Intrinsics.areEqual(this.allDay, updateTaskParams.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, updateTaskParams.recurrenceSchedule);
    }

    public final SetField<Boolean> getAllDay() {
        return this.allDay;
    }

    public final SetField<Boolean> getCompleted() {
        return this.completed;
    }

    public final SetField<DateTime> getDateTime() {
        return this.dateTime;
    }

    public final SetField<String> getDescription() {
        return this.description;
    }

    public final SetField<String> getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final String getTaskId() {
        return this.taskId;
    }

    public final SetField<String> getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.taskId, this.title, this.description, this.completed, this.dateTime, this.allDay, this.recurrenceSchedule);
    }
}
