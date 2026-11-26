package com.google.android.appfunctions.schema.tasks.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0007\u0018\u00010\u0005¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001f\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/UpdateTaskCategoryParams;", "", "taskCategoryId", "", "title", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "taskIds", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getTaskCategoryId", "()Ljava/lang/String;", "getTitle", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getTaskIds", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateTaskCategoryParams {
    private final String taskCategoryId;
    private final SetField<List<String>> taskIds;
    private final SetField<String> title;

    /* JADX WARN: Multi-variable type inference failed */
    public UpdateTaskCategoryParams(String taskCategoryId, SetField<String> setField, SetField<? extends List<String>> setField2) {
        Intrinsics.checkNotNullParameter(taskCategoryId, "taskCategoryId");
        this.taskCategoryId = taskCategoryId;
        this.title = setField;
        this.taskIds = setField2;
    }

    public /* synthetic */ UpdateTaskCategoryParams(String str, SetField setField, SetField setField2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateTaskCategoryParams)) {
            return false;
        }
        UpdateTaskCategoryParams updateTaskCategoryParams = (UpdateTaskCategoryParams) other;
        return Intrinsics.areEqual(this.taskCategoryId, updateTaskCategoryParams.taskCategoryId) && Intrinsics.areEqual(this.title, updateTaskCategoryParams.title) && Intrinsics.areEqual(this.taskIds, updateTaskCategoryParams.taskIds);
    }

    public final String getTaskCategoryId() {
        return this.taskCategoryId;
    }

    public final SetField<List<String>> getTaskIds() {
        return this.taskIds;
    }

    public final SetField<String> getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.taskCategoryId, this.title, this.taskIds);
    }
}
