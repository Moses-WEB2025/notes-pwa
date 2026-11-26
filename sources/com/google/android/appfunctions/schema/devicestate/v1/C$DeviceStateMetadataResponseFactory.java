package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$DeviceStateMetadataResponseFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/devicestate/v1/$DeviceStateMetadataResponseFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateMetadataResponse;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$DeviceStateMetadataResponseFactory implements AppFunctionSerializableFactory<DeviceStateMetadataResponse> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public DeviceStateMetadataResponse fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse");
        C$PerScreenMetadataFactory c$PerScreenMetadataFactory = new C$PerScreenMetadataFactory();
        C$ItemizationTypeFactory c$ItemizationTypeFactory = new C$ItemizationTypeFactory();
        List<AppFunctionData> appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("perScreenMetadata");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list = appFunctionDataList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenMetadataFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("deviceLocale");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("globalHintText");
        List<AppFunctionData> appFunctionDataList2 = appFunctionDataWithSpec.getAppFunctionDataList("itemizationTypes");
        if (appFunctionDataList2 == null) {
            appFunctionDataList2 = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list2 = appFunctionDataList2;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(c$ItemizationTypeFactory.fromAppFunctionData((AppFunctionData) it2.next()));
        }
        return new DeviceStateMetadataResponse(arrayList2, stringOrNull, stringOrNull2, arrayList3);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(DeviceStateMetadataResponse appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$PerScreenMetadataFactory c$PerScreenMetadataFactory = new C$PerScreenMetadataFactory();
        C$ItemizationTypeFactory c$ItemizationTypeFactory = new C$ItemizationTypeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse");
        List<PerScreenMetadata> perScreenMetadata = appFunctionSerializable.getPerScreenMetadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(perScreenMetadata, 10));
        Iterator<T> it = perScreenMetadata.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenMetadataFactory.toAppFunctionData((PerScreenMetadata) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("perScreenMetadata", arrayList);
        appFunctionDataBuilder.setString("deviceLocale", appFunctionSerializable.getDeviceLocale());
        String globalHintText = appFunctionSerializable.getGlobalHintText();
        if (globalHintText != null) {
            appFunctionDataBuilder.setString("globalHintText", globalHintText);
        }
        List<ItemizationType> itemizationTypes = appFunctionSerializable.getItemizationTypes();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(itemizationTypes, 10));
        Iterator<T> it2 = itemizationTypes.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$ItemizationTypeFactory.toAppFunctionData((ItemizationType) it2.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("itemizationTypes", arrayList2);
        return appFunctionDataBuilder.build();
    }
}
