package com.google.android.appfunctions.schema.nearbysharing.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u000bR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0006\u0010\u0010¨\u0006\u0017"}, d2 = {"Lcom/google/android/appfunctions/schema/nearbysharing/v1/NearbyDevice;", "", "nearbyDeviceId", "", "deviceName", "deviceType", "isMyDevice", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)V", "getNearbyDeviceId", "()Ljava/lang/String;", "getDeviceName", "getDeviceType$annotations", "()V", "getDeviceType", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.nearbysharing.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class NearbyDevice {
    public static final String DEVICE_TYPE_PC = "pc";
    public static final String DEVICE_TYPE_PHONE = "phone";
    public static final String DEVICE_TYPE_TABLET = "tablet";
    private final String deviceName;
    private final String deviceType;
    private final Boolean isMyDevice;
    private final String nearbyDeviceId;

    public NearbyDevice(String nearbyDeviceId, String deviceName, String str, Boolean bool) {
        Intrinsics.checkNotNullParameter(nearbyDeviceId, "nearbyDeviceId");
        Intrinsics.checkNotNullParameter(deviceName, "deviceName");
        this.nearbyDeviceId = nearbyDeviceId;
        this.deviceName = deviceName;
        this.deviceType = str;
        this.isMyDevice = bool;
    }

    public /* synthetic */ NearbyDevice(String str, String str2, String str3, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof NearbyDevice)) {
            return false;
        }
        NearbyDevice nearbyDevice = (NearbyDevice) other;
        return Intrinsics.areEqual(this.nearbyDeviceId, nearbyDevice.nearbyDeviceId) && Intrinsics.areEqual(this.deviceName, nearbyDevice.deviceName) && Intrinsics.areEqual(this.deviceType, nearbyDevice.deviceType) && Intrinsics.areEqual(this.isMyDevice, nearbyDevice.isMyDevice);
    }

    public final String getDeviceName() {
        return this.deviceName;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final String getNearbyDeviceId() {
        return this.nearbyDeviceId;
    }

    public int hashCode() {
        return Objects.hash(this.nearbyDeviceId, this.deviceName, this.deviceType, this.isMyDevice);
    }

    /* JADX INFO: renamed from: isMyDevice, reason: from getter */
    public final Boolean getIsMyDevice() {
        return this.isMyDevice;
    }
}
