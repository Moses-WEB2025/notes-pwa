package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateFactory;
import com.google.android.appfunctions.schema.types.v1.Date;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$DayPatternFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/clock/alarms/v1/$DayPatternFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$DayPatternFactory implements AppFunctionSerializableFactory<DayPattern> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public DayPattern fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.DayPattern");
        C$DateFactory c$DateFactory = new C$DateFactory();
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("date");
        return new DayPattern(appFunctionData2 != null ? c$DateFactory.fromAppFunctionData(appFunctionData2) : null, appFunctionDataWithSpec.getStringOrNull("recurrencePattern"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(DayPattern appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateFactory c$DateFactory = new C$DateFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.DayPattern");
        Date date = appFunctionSerializable.getDate();
        if (date != null) {
            appFunctionDataBuilder.setAppFunctionData("date", c$DateFactory.toAppFunctionData(date));
        }
        String recurrencePattern = appFunctionSerializable.getRecurrencePattern();
        if (recurrencePattern != null) {
            appFunctionDataBuilder.setString("recurrencePattern", recurrencePattern);
        }
        return appFunctionDataBuilder.build();
    }
}
