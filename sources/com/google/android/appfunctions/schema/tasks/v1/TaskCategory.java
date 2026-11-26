package com.google.android.appfunctions.schema.tasks.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0012\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0007\u0010\u0010¨\u0006\u0017"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/TaskCategory;", "", "id", "", "title", "taskIds", "", "isReadOnly", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/Boolean;)V", "getId", "()Ljava/lang/String;", "getTitle", "getTaskIds", "()Ljava/util/List;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class TaskCategory {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.tasks.v1.TaskCategoryForeground";
    private final String id;
    private final Boolean isReadOnly;
    private final List<String> taskIds;
    private final String title;

    public TaskCategory(String id, String title, List<String> taskIds, Boolean bool) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(taskIds, "taskIds");
        this.id = id;
        this.title = title;
        this.taskIds = taskIds;
        this.isReadOnly = bool;
    }

    public /* synthetic */ TaskCategory(String str, String str2, List list, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof TaskCategory)) {
            return false;
        }
        TaskCategory taskCategory = (TaskCategory) other;
        return Intrinsics.areEqual(this.id, taskCategory.id) && Intrinsics.areEqual(this.title, taskCategory.title) && Intrinsics.areEqual(this.taskIds, taskCategory.taskIds) && Intrinsics.areEqual(this.isReadOnly, taskCategory.isReadOnly);
    }

    public final String getId() {
        return this.id;
    }

    public final List<String> getTaskIds() {
        return this.taskIds;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.taskIds, this.isReadOnly);
    }

    /* JADX INFO: renamed from: isReadOnly, reason: from getter */
    public final Boolean getIsReadOnly() {
        return this.isReadOnly;
    }
}
