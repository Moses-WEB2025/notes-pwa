package com.google.android.appfunctions.schema.clock.timers.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.timers.v1.$CreateTimerParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/clock/timers/v1/$CreateTimerParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/clock/timers/v1/CreateTimerParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.timers.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$CreateTimerParamsFactory implements AppFunctionSerializableFactory<CreateTimerParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public CreateTimerParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.timers.v1.CreateTimerParams");
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull(Todo.LABEL);
        Long longOrNull = appFunctionDataWithSpec.getLongOrNull("originalDurationMillis");
        if (longOrNull != null) {
            return new CreateTimerParams(stringOrNull, longOrNull.longValue(), appFunctionDataWithSpec.getStringOrNull("timerState"), appFunctionDataWithSpec.getStringOrNull("externalId"), appFunctionDataWithSpec.getStringOrNull("id"));
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(CreateTimerParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.timers.v1.CreateTimerParams");
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString(Todo.LABEL, label);
        }
        appFunctionDataBuilder.setLong("originalDurationMillis", appFunctionSerializable.getOriginalDurationMillis());
        String timerState = appFunctionSerializable.getTimerState();
        if (timerState != null) {
            appFunctionDataBuilder.setString("timerState", timerState);
        }
        String externalId = appFunctionSerializable.getExternalId();
        if (externalId != null) {
            appFunctionDataBuilder.setString("externalId", externalId);
        }
        String id = appFunctionSerializable.getId();
        if (id != null) {
            appFunctionDataBuilder.setString("id", id);
        }
        return appFunctionDataBuilder.build();
    }
}
