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
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/CreateTaskCategoryParams;", "", "title", "", "taskIds", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;)V", "getTitle", "()Ljava/lang/String;", "getTaskIds", "()Ljava/util/List;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateTaskCategoryParams {
    private final List<String> taskIds;
    private final String title;

    public CreateTaskCategoryParams(String title, List<String> taskIds) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(taskIds, "taskIds");
        this.title = title;
        this.taskIds = taskIds;
    }

    public /* synthetic */ CreateTaskCategoryParams(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateTaskCategoryParams)) {
            return false;
        }
        CreateTaskCategoryParams createTaskCategoryParams = (CreateTaskCategoryParams) other;
        return Intrinsics.areEqual(this.title, createTaskCategoryParams.title) && Intrinsics.areEqual(this.taskIds, createTaskCategoryParams.taskIds);
    }

    public final List<String> getTaskIds() {
        return this.taskIds;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.taskIds);
    }
}
