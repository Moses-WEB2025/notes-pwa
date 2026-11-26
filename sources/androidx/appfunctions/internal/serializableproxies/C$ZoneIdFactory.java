package androidx.appfunctions.internal.serializableproxies;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.time.ZoneId;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: androidx.appfunctions.internal.serializableproxies.$ZoneIdFactory, reason: invalid class name */
/* JADX INFO: compiled from: $ZoneIdFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"androidx/appfunctions/internal/serializableproxies/$ZoneIdFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Ljava/time/ZoneId;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$ZoneIdFactory implements AppFunctionSerializableFactory<ZoneId> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public ZoneId fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        String stringOrNull = getAppFunctionDataWithSpec(appFunctionData, "java.time.ZoneId").getStringOrNull("zoneID");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        return new AppFunctionZoneId(stringOrNull).toZoneId();
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(ZoneId appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionZoneId appFunctionZoneIdFromZoneId = AppFunctionZoneId.INSTANCE.fromZoneId(appFunctionSerializable);
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("java.time.ZoneId");
        appFunctionDataBuilder.setString("zoneID", appFunctionZoneIdFromZoneId.getZoneID());
        return appFunctionDataBuilder.build();
    }
}
