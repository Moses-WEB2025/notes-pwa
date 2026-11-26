package com.google.android.appfunctions.schema.types.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.types.v1.$TimeOfDayFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/types/v1/$TimeOfDayFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/types/v1/TimeOfDay;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$TimeOfDayFactory implements AppFunctionSerializableFactory<TimeOfDay> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public TimeOfDay fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.types.v1.TimeOfDay");
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("hours");
        if (intOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue = intOrNull.intValue();
        Integer intOrNull2 = appFunctionDataWithSpec.getIntOrNull("minutes");
        if (intOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue2 = intOrNull2.intValue();
        Integer intOrNull3 = appFunctionDataWithSpec.getIntOrNull("seconds");
        if (intOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue3 = intOrNull3.intValue();
        Integer intOrNull4 = appFunctionDataWithSpec.getIntOrNull("nanos");
        if (intOrNull4 != null) {
            return new TimeOfDay(iIntValue, iIntValue2, iIntValue3, intOrNull4.intValue());
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(TimeOfDay appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.types.v1.TimeOfDay");
        appFunctionDataBuilder.setInt("hours", appFunctionSerializable.getHours());
        appFunctionDataBuilder.setInt("minutes", appFunctionSerializable.getMinutes());
        appFunctionDataBuilder.setInt("seconds", appFunctionSerializable.getSeconds());
        appFunctionDataBuilder.setInt("nanos", appFunctionSerializable.getNanos());
        return appFunctionDataBuilder.build();
    }
}
