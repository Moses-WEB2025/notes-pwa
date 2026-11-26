package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B+\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateResponse;", "", "perScreenDeviceStates", "", "Lcom/google/android/appfunctions/schema/devicestate/v1/PerScreenDeviceStates;", "deviceLocale", "", "globalHintText", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getPerScreenDeviceStates", "()Ljava/util/List;", "getDeviceLocale", "()Ljava/lang/String;", "getGlobalHintText", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeviceStateResponse {
    private final String deviceLocale;
    private final String globalHintText;
    private final List<PerScreenDeviceStates> perScreenDeviceStates;

    public DeviceStateResponse(List<PerScreenDeviceStates> perScreenDeviceStates, String deviceLocale, String str) {
        Intrinsics.checkNotNullParameter(perScreenDeviceStates, "perScreenDeviceStates");
        Intrinsics.checkNotNullParameter(deviceLocale, "deviceLocale");
        this.perScreenDeviceStates = perScreenDeviceStates;
        this.deviceLocale = deviceLocale;
        this.globalHintText = str;
    }

    public /* synthetic */ DeviceStateResponse(List list, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, str, (i & 4) != 0 ? null : str2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeviceStateResponse)) {
            return false;
        }
        DeviceStateResponse deviceStateResponse = (DeviceStateResponse) other;
        return Intrinsics.areEqual(this.perScreenDeviceStates, deviceStateResponse.perScreenDeviceStates) && Intrinsics.areEqual(this.deviceLocale, deviceStateResponse.deviceLocale) && Intrinsics.areEqual(this.globalHintText, deviceStateResponse.globalHintText);
    }

    public final String getDeviceLocale() {
        return this.deviceLocale;
    }

    public final String getGlobalHintText() {
        return this.globalHintText;
    }

    public final List<PerScreenDeviceStates> getPerScreenDeviceStates() {
        return this.perScreenDeviceStates;
    }

    public int hashCode() {
        return Objects.hash(this.perScreenDeviceStates, this.deviceLocale, this.globalHintText);
    }
}
