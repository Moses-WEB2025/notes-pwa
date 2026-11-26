package androidx.appfunctions;

import android.net.Uri;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: androidx.appfunctions.$AppFunctionUriGrantFactory, reason: invalid class name */
/* JADX INFO: compiled from: $AppFunctionUriGrantFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"androidx/appfunctions/$AppFunctionUriGrantFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Landroidx/appfunctions/AppFunctionUriGrant;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$AppFunctionUriGrantFactory implements AppFunctionSerializableFactory<AppFunctionUriGrant> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionUriGrant fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "androidx.appfunctions.AppFunctionUriGrant");
        C$UriFactory c$UriFactory = new C$UriFactory();
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData(CallMethod.ARG_URI);
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        Uri uriFromAppFunctionData = c$UriFactory.fromAppFunctionData(appFunctionData2);
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("modeFlags");
        if (intOrNull == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        return new AppFunctionUriGrant(uriFromAppFunctionData, intOrNull.intValue());
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(AppFunctionUriGrant appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$UriFactory c$UriFactory = new C$UriFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("androidx.appfunctions.AppFunctionUriGrant");
        appFunctionDataBuilder.setAppFunctionData(CallMethod.ARG_URI, c$UriFactory.toAppFunctionData(appFunctionSerializable.getUri()));
        appFunctionDataBuilder.setInt("modeFlags", appFunctionSerializable.getModeFlags());
        return appFunctionDataBuilder.build();
    }
}
