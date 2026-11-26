package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$FindAlarmsParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/clock/alarms/v1/$FindAlarmsParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/FindAlarmsParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$FindAlarmsParamsFactory implements AppFunctionSerializableFactory<FindAlarmsParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public FindAlarmsParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.FindAlarmsParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endTime");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull(Todo.LABEL);
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("alarmStatus");
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("dayPattern");
        DayPattern dayPatternFromAppFunctionData = appFunctionData4 != null ? c$DayPatternFactory.fromAppFunctionData(appFunctionData4) : null;
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("maxCount");
        if (intOrNull != null) {
            return new FindAlarmsParams(dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringOrNull, stringOrNull2, dayPatternFromAppFunctionData, intOrNull.intValue(), appFunctionDataWithSpec.getStringOrNull("sortingOrder"));
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(FindAlarmsParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.FindAlarmsParams");
        DateTime startTime = appFunctionSerializable.getStartTime();
        if (startTime != null) {
            appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(startTime));
        }
        DateTime endTime = appFunctionSerializable.getEndTime();
        if (endTime != null) {
            appFunctionDataBuilder.setAppFunctionData("endTime", c$DateTimeFactory.toAppFunctionData(endTime));
        }
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString(Todo.LABEL, label);
        }
        String alarmStatus = appFunctionSerializable.getAlarmStatus();
        if (alarmStatus != null) {
            appFunctionDataBuilder.setString("alarmStatus", alarmStatus);
        }
        DayPattern dayPattern = appFunctionSerializable.getDayPattern();
        if (dayPattern != null) {
            appFunctionDataBuilder.setAppFunctionData("dayPattern", c$DayPatternFactory.toAppFunctionData(dayPattern));
        }
        appFunctionDataBuilder.setInt("maxCount", appFunctionSerializable.getMaxCount());
        String sortingOrder = appFunctionSerializable.getSortingOrder();
        if (sortingOrder != null) {
            appFunctionDataBuilder.setString("sortingOrder", sortingOrder);
        }
        return appFunctionDataBuilder.build();
    }
}
