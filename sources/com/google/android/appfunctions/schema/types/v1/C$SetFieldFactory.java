package com.google.android.appfunctions.schema.types.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.types.v1.$SetFieldFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0015\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\u00020\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"com/google/android/appfunctions/schema/types/v1/$SetFieldFactory", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "tTypeParameter", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory$TypeParameter;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/appfunctions/internal/AppFunctionSerializableFactory$TypeParameter;)V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$SetFieldFactory<T> implements AppFunctionSerializableFactory<SetField<? extends T>> {
    private final AppFunctionSerializableFactory.TypeParameter zza;

    public C$SetFieldFactory(AppFunctionSerializableFactory.TypeParameter<T> tTypeParameter) {
        Intrinsics.checkNotNullParameter(tTypeParameter, "tTypeParameter");
        this.zza = tTypeParameter;
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public SetField<T> fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        return new SetField<>(this.zza.getFromAppFunctionData(getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.types.v1.SetField"), "value"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(SetField<? extends T> appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.types.v1.SetField");
        this.zza.setValueInAppFunctionData(appFunctionDataBuilder, "value", appFunctionSerializable.getValue());
        return appFunctionDataBuilder.build();
    }
}
