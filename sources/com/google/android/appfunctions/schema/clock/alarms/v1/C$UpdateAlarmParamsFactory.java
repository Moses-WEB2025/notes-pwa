package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.C$TimeOfDayFactory;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.google.android.appfunctions.schema.types.v1.TimeOfDay;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$UpdateAlarmParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/clock/alarms/v1/$UpdateAlarmParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/UpdateAlarmParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$UpdateAlarmParamsFactory implements AppFunctionSerializableFactory<UpdateAlarmParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public UpdateAlarmParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.UpdateAlarmParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(TimeOfDay.class, new C$TimeOfDayFactory()));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DayPattern.class, new C$DayPatternFactory()));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("alarmId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData(Todo.LABEL);
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("time");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("alarmStatus");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("dayPattern");
        return new UpdateAlarmParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, appFunctionData5 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData5) : null);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(UpdateAlarmParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(TimeOfDay.class, new C$TimeOfDayFactory()));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DayPattern.class, new C$DayPatternFactory()));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.UpdateAlarmParams");
        appFunctionDataBuilder.setString("alarmId", appFunctionSerializable.getAlarmId());
        SetField<String> label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setAppFunctionData(Todo.LABEL, c$SetFieldFactory.toAppFunctionData((SetField) label));
        }
        SetField<TimeOfDay> time = appFunctionSerializable.getTime();
        if (time != null) {
            appFunctionDataBuilder.setAppFunctionData("time", c$SetFieldFactory2.toAppFunctionData((SetField) time));
        }
        SetField<String> alarmStatus = appFunctionSerializable.getAlarmStatus();
        if (alarmStatus != null) {
            appFunctionDataBuilder.setAppFunctionData("alarmStatus", c$SetFieldFactory.toAppFunctionData((SetField) alarmStatus));
        }
        SetField<DayPattern> dayPattern = appFunctionSerializable.getDayPattern();
        if (dayPattern != null) {
            appFunctionDataBuilder.setAppFunctionData("dayPattern", c$SetFieldFactory3.toAppFunctionData((SetField) dayPattern));
        }
        return appFunctionDataBuilder.build();
    }
}
