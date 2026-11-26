package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItemResponse;", "", "deviceStateItem", "Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItem;)V", "getDeviceStateItem", "()Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItem;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeviceStateItemResponse {
    private final DeviceStateItem deviceStateItem;

    public DeviceStateItemResponse(DeviceStateItem deviceStateItem) {
        Intrinsics.checkNotNullParameter(deviceStateItem, "deviceStateItem");
        this.deviceStateItem = deviceStateItem;
    }

    public boolean equals(Object other) {
        return (other instanceof DeviceStateItemResponse) && Intrinsics.areEqual(this.deviceStateItem, ((DeviceStateItemResponse) other).deviceStateItem);
    }

    public final DeviceStateItem getDeviceStateItem() {
        return this.deviceStateItem;
    }

    public int hashCode() {
        return Objects.hash(this.deviceStateItem);
    }
}
