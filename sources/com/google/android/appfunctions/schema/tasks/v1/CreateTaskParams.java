package com.google.android.appfunctions.schema.tasks.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0017\u0010\u0013R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010¨\u0006\u001f"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/CreateTaskParams;", "", "title", "", "description", "completed", "", "dateTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "allDay", "recurrenceSchedule", "externalId", "taskCategoryId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "getDescription", "getCompleted", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getDateTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getAllDay", "getRecurrenceSchedule", "getExternalId", "getTaskCategoryId", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateTaskParams {
    private final Boolean allDay;
    private final Boolean completed;
    private final DateTime dateTime;
    private final String description;
    private final String externalId;
    private final String recurrenceSchedule;
    private final String taskCategoryId;
    private final String title;

    public CreateTaskParams(String title, String str, Boolean bool, DateTime dateTime, Boolean bool2, String str2, String str3, String str4) {
        Intrinsics.checkNotNullParameter(title, "title");
        this.title = title;
        this.description = str;
        this.completed = bool;
        this.dateTime = dateTime;
        this.allDay = bool2;
        this.recurrenceSchedule = str2;
        this.externalId = str3;
        this.taskCategoryId = str4;
    }

    public /* synthetic */ CreateTaskParams(String str, String str2, Boolean bool, DateTime dateTime, Boolean bool2, String str3, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : bool, (i & 8) != 0 ? null : dateTime, (i & 16) != 0 ? null : bool2, (i & 32) != 0 ? null : str3, (i & 64) != 0 ? null : str4, (i & 128) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateTaskParams)) {
            return false;
        }
        CreateTaskParams createTaskParams = (CreateTaskParams) other;
        return Intrinsics.areEqual(this.title, createTaskParams.title) && Intrinsics.areEqual(this.description, createTaskParams.description) && Intrinsics.areEqual(this.completed, createTaskParams.completed) && Intrinsics.areEqual(this.dateTime, createTaskParams.dateTime) && Intrinsics.areEqual(this.allDay, createTaskParams.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, createTaskParams.recurrenceSchedule) && Intrinsics.areEqual(this.externalId, createTaskParams.externalId) && Intrinsics.areEqual(this.taskCategoryId, createTaskParams.taskCategoryId);
    }

    public final Boolean getAllDay() {
        return this.allDay;
    }

    public final Boolean getCompleted() {
        return this.completed;
    }

    public final DateTime getDateTime() {
        return this.dateTime;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final String getTaskCategoryId() {
        return this.taskCategoryId;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.description, this.completed, this.dateTime, this.allDay, this.recurrenceSchedule, this.externalId, this.taskCategoryId);
    }
}
