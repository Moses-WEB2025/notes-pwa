package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$TimeOfDayFactory;
import com.google.android.appfunctions.schema.types.v1.TimeOfDay;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$CreateAlarmParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/clock/alarms/v1/$CreateAlarmParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/CreateAlarmParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$CreateAlarmParamsFactory implements AppFunctionSerializableFactory<CreateAlarmParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public CreateAlarmParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.CreateAlarmParams");
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull(Todo.LABEL);
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("time");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        TimeOfDay timeOfDayFromAppFunctionData = c$TimeOfDayFactory.fromAppFunctionData(appFunctionData2);
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("alarmStatus");
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("dayPattern");
        return new CreateAlarmParams(stringOrNull, timeOfDayFromAppFunctionData, stringOrNull2, appFunctionData3 != null ? c$DayPatternFactory.fromAppFunctionData(appFunctionData3) : null, appFunctionDataWithSpec.getStringOrNull("externalId"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(CreateAlarmParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.CreateAlarmParams");
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString(Todo.LABEL, label);
        }
        appFunctionDataBuilder.setAppFunctionData("time", c$TimeOfDayFactory.toAppFunctionData(appFunctionSerializable.getTime()));
        String alarmStatus = appFunctionSerializable.getAlarmStatus();
        if (alarmStatus != null) {
            appFunctionDataBuilder.setString("alarmStatus", alarmStatus);
        }
        DayPattern dayPattern = appFunctionSerializable.getDayPattern();
        if (dayPattern != null) {
            appFunctionDataBuilder.setAppFunctionData("dayPattern", c$DayPatternFactory.toAppFunctionData(dayPattern));
        }
        String externalId = appFunctionSerializable.getExternalId();
        if (externalId != null) {
            appFunctionDataBuilder.setString("externalId", externalId);
        }
        return appFunctionDataBuilder.build();
    }
}
