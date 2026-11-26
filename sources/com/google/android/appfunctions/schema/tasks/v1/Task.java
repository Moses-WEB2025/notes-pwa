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
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0019\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010¨\u0006 "}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/Task;", "", "id", "", "title", "description", "completed", "", "taskCategoryId", "dateTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "allDay", "recurrenceSchedule", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/Boolean;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getTitle", "getDescription", "getCompleted", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getTaskCategoryId", "getDateTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getAllDay", "getRecurrenceSchedule", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Task {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.tasks.v1.TaskForeground";
    private final Boolean allDay;
    private final Boolean completed;
    private final DateTime dateTime;
    private final String description;
    private final String id;
    private final String recurrenceSchedule;
    private final String taskCategoryId;
    private final String title;

    public Task(String id, String title, String str, Boolean bool, String str2, DateTime dateTime, Boolean bool2, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        this.id = id;
        this.title = title;
        this.description = str;
        this.completed = bool;
        this.taskCategoryId = str2;
        this.dateTime = dateTime;
        this.allDay = bool2;
        this.recurrenceSchedule = str3;
    }

    public /* synthetic */ Task(String str, String str2, String str3, Boolean bool, String str4, DateTime dateTime, Boolean bool2, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : bool, (i & 16) != 0 ? null : str4, (i & 32) != 0 ? null : dateTime, (i & 64) != 0 ? null : bool2, (i & 128) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Task)) {
            return false;
        }
        Task task = (Task) other;
        return Intrinsics.areEqual(this.id, task.id) && Intrinsics.areEqual(this.title, task.title) && Intrinsics.areEqual(this.description, task.description) && Intrinsics.areEqual(this.completed, task.completed) && Intrinsics.areEqual(this.taskCategoryId, task.taskCategoryId) && Intrinsics.areEqual(this.dateTime, task.dateTime) && Intrinsics.areEqual(this.allDay, task.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, task.recurrenceSchedule);
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

    public final String getId() {
        return this.id;
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
        return Objects.hash(this.id, this.title, this.description, this.completed, this.taskCategoryId, this.dateTime, this.allDay, this.recurrenceSchedule);
    }
}
