package com.google.android.appfunctions.schema.tasks.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.tasks.v1.$UpdateTaskParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/tasks/v1/$UpdateTaskParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/tasks/v1/UpdateTaskParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.tasks.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$UpdateTaskParamsFactory implements AppFunctionSerializableFactory<UpdateTaskParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public UpdateTaskParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.tasks.v1.UpdateTaskParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        Class cls = Boolean.TYPE;
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.Boolean?>");
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(cls));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull(CallMethod.ARG_TASK_ID);
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("title");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("description");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("completed");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("dateTime");
        SetField setFieldFromAppFunctionData4 = appFunctionData5 != null ? c$SetFieldFactory4.fromAppFunctionData(appFunctionData5) : null;
        AppFunctionData appFunctionData6 = appFunctionDataWithSpec.getAppFunctionData("allDay");
        SetField setFieldFromAppFunctionData5 = appFunctionData6 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData6) : null;
        AppFunctionData appFunctionData7 = appFunctionDataWithSpec.getAppFunctionData("recurrenceSchedule");
        return new UpdateTaskParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, setFieldFromAppFunctionData4, setFieldFromAppFunctionData5, appFunctionData7 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData7) : null);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(UpdateTaskParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        Class cls = Boolean.TYPE;
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.Boolean?>");
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(cls));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.tasks.v1.UpdateTaskParams");
        appFunctionDataBuilder.setString(CallMethod.ARG_TASK_ID, appFunctionSerializable.getTaskId());
        SetField<String> title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setAppFunctionData("title", c$SetFieldFactory.toAppFunctionData((SetField) title));
        }
        SetField<String> description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setAppFunctionData("description", c$SetFieldFactory2.toAppFunctionData((SetField) description));
        }
        SetField<Boolean> completed = appFunctionSerializable.getCompleted();
        if (completed != null) {
            appFunctionDataBuilder.setAppFunctionData("completed", c$SetFieldFactory3.toAppFunctionData((SetField) completed));
        }
        SetField<DateTime> dateTime = appFunctionSerializable.getDateTime();
        if (dateTime != null) {
            appFunctionDataBuilder.setAppFunctionData("dateTime", c$SetFieldFactory4.toAppFunctionData((SetField) dateTime));
        }
        SetField<Boolean> allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setAppFunctionData("allDay", c$SetFieldFactory3.toAppFunctionData((SetField) allDay));
        }
        SetField<String> recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setAppFunctionData("recurrenceSchedule", c$SetFieldFactory2.toAppFunctionData((SetField) recurrenceSchedule));
        }
        return appFunctionDataBuilder.build();
    }
}
