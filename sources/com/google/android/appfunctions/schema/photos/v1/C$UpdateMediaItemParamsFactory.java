package com.google.android.appfunctions.schema.photos.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.photos.v1.$UpdateMediaItemParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/photos/v1/$UpdateMediaItemParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/photos/v1/UpdateMediaItemParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$UpdateMediaItemParamsFactory implements AppFunctionSerializableFactory<UpdateMediaItemParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public UpdateMediaItemParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.photos.v1.UpdateMediaItemParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        Class cls = Boolean.TYPE;
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.Boolean?>");
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(cls));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("mediaItemId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("title");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("starred");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("albumId");
        return new UpdateMediaItemParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, appFunctionData4 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData4) : null);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(UpdateMediaItemParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        Class cls = Boolean.TYPE;
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.Boolean?>");
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(cls));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.photos.v1.UpdateMediaItemParams");
        appFunctionDataBuilder.setString("mediaItemId", appFunctionSerializable.getMediaItemId());
        SetField<String> title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setAppFunctionData("title", c$SetFieldFactory.toAppFunctionData((SetField) title));
        }
        SetField<Boolean> starred = appFunctionSerializable.getStarred();
        if (starred != null) {
            appFunctionDataBuilder.setAppFunctionData("starred", c$SetFieldFactory2.toAppFunctionData((SetField) starred));
        }
        SetField<String> albumId = appFunctionSerializable.getAlbumId();
        if (albumId != null) {
            appFunctionDataBuilder.setAppFunctionData("albumId", c$SetFieldFactory3.toAppFunctionData((SetField) albumId));
        }
        return appFunctionDataBuilder.build();
    }
}
