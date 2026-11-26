package com.google.android.appfunctions.schema.tasks.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.tasks.v1.$TaskFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/tasks/v1/$TaskFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/tasks/v1/Task;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$TaskFactory implements AppFunctionSerializableFactory<Task> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public Task fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.tasks.v1.Task");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("title");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("description");
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("completed");
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("taskCategoryId");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("dateTime");
        return new Task(stringOrNull, stringOrNull2, stringOrNull3, booleanOrNull, stringOrNull4, appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null, appFunctionDataWithSpec.getBooleanOrNull("allDay"), appFunctionDataWithSpec.getStringOrNull("recurrenceSchedule"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(Task appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.tasks.v1.Task");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("title", appFunctionSerializable.getTitle());
        String description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setString("description", description);
        }
        Boolean completed = appFunctionSerializable.getCompleted();
        if (completed != null) {
            appFunctionDataBuilder.setBoolean("completed", completed.booleanValue());
        }
        String taskCategoryId = appFunctionSerializable.getTaskCategoryId();
        if (taskCategoryId != null) {
            appFunctionDataBuilder.setString("taskCategoryId", taskCategoryId);
        }
        DateTime dateTime = appFunctionSerializable.getDateTime();
        if (dateTime != null) {
            appFunctionDataBuilder.setAppFunctionData("dateTime", c$DateTimeFactory.toAppFunctionData(dateTime));
        }
        Boolean allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setBoolean("allDay", allDay.booleanValue());
        }
        String recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setString("recurrenceSchedule", recurrenceSchedule);
        }
        return appFunctionDataBuilder.build();
    }
}
