package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B;\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006\u0017"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateMetadataResponse;", "", "perScreenMetadata", "", "Lcom/google/android/appfunctions/schema/devicestate/v1/PerScreenMetadata;", "deviceLocale", "", "globalHintText", "itemizationTypes", "Lcom/google/android/appfunctions/schema/devicestate/v1/ItemizationType;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getPerScreenMetadata", "()Ljava/util/List;", "getDeviceLocale", "()Ljava/lang/String;", "getGlobalHintText", "getItemizationTypes", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeviceStateMetadataResponse {
    private final String deviceLocale;
    private final String globalHintText;
    private final List<ItemizationType> itemizationTypes;
    private final List<PerScreenMetadata> perScreenMetadata;

    public DeviceStateMetadataResponse(List<PerScreenMetadata> perScreenMetadata, String deviceLocale, String str, List<ItemizationType> itemizationTypes) {
        Intrinsics.checkNotNullParameter(perScreenMetadata, "perScreenMetadata");
        Intrinsics.checkNotNullParameter(deviceLocale, "deviceLocale");
        Intrinsics.checkNotNullParameter(itemizationTypes, "itemizationTypes");
        this.perScreenMetadata = perScreenMetadata;
        this.deviceLocale = deviceLocale;
        this.globalHintText = str;
        this.itemizationTypes = itemizationTypes;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ DeviceStateMetadataResponse(List list, String str, String str2, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        str2 = (i & 4) != 0 ? null : str2;
        if ((i & 8) != 0) {
            list2 = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list2, "emptyList(...)");
        }
        this(list, str, str2, list2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeviceStateMetadataResponse)) {
            return false;
        }
        DeviceStateMetadataResponse deviceStateMetadataResponse = (DeviceStateMetadataResponse) other;
        return Intrinsics.areEqual(this.perScreenMetadata, deviceStateMetadataResponse.perScreenMetadata) && Intrinsics.areEqual(this.deviceLocale, deviceStateMetadataResponse.deviceLocale) && Intrinsics.areEqual(this.globalHintText, deviceStateMetadataResponse.globalHintText) && Intrinsics.areEqual(this.itemizationTypes, deviceStateMetadataResponse.itemizationTypes);
    }

    public final String getDeviceLocale() {
        return this.deviceLocale;
    }

    public final String getGlobalHintText() {
        return this.globalHintText;
    }

    public final List<ItemizationType> getItemizationTypes() {
        return this.itemizationTypes;
    }

    public final List<PerScreenMetadata> getPerScreenMetadata() {
        return this.perScreenMetadata;
    }

    public int hashCode() {
        return Objects.hash(this.perScreenMetadata, this.deviceLocale, this.globalHintText, this.itemizationTypes);
    }
}
