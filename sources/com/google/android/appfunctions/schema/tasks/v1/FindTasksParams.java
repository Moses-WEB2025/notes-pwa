package com.google.android.appfunctions.schema.tasks.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001BK\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\u000bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/tasks/v1/FindTasksParams;", "", SmartAction.Feature.QUERY, "", "completed", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", "taskCategoryId", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/Boolean;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;I)V", "getQuery", "()Ljava/lang/String;", "getCompleted", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getTaskCategoryId", "getMaxCount", "()I", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindTasksParams {
    private final Boolean completed;
    private final DateTime endTime;
    private final int maxCount;
    private final String query;
    private final DateTime startTime;
    private final String taskCategoryId;

    public FindTasksParams(String str, Boolean bool, DateTime dateTime, DateTime dateTime2, String str2, int i) {
        this.query = str;
        this.completed = bool;
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.taskCategoryId = str2;
        this.maxCount = i;
    }

    public /* synthetic */ FindTasksParams(String str, Boolean bool, DateTime dateTime, DateTime dateTime2, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : bool, (i2 & 4) != 0 ? null : dateTime, (i2 & 8) != 0 ? null : dateTime2, (i2 & 16) != 0 ? null : str2, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindTasksParams)) {
            return false;
        }
        FindTasksParams findTasksParams = (FindTasksParams) other;
        return Intrinsics.areEqual(this.query, findTasksParams.query) && Intrinsics.areEqual(this.completed, findTasksParams.completed) && Intrinsics.areEqual(this.startTime, findTasksParams.startTime) && Intrinsics.areEqual(this.endTime, findTasksParams.endTime) && Intrinsics.areEqual(this.taskCategoryId, findTasksParams.taskCategoryId) && this.maxCount == findTasksParams.maxCount;
    }

    public final Boolean getCompleted() {
        return this.completed;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public final String getTaskCategoryId() {
        return this.taskCategoryId;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.completed, this.startTime, this.endTime, this.taskCategoryId, Integer.valueOf(this.maxCount));
    }
}
