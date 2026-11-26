package com.google.android.appfunctions.schema.nearbysharing.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B?\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u0019\u001a\u00020\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\nH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u000eR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0005\u0010\u0012R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/nearbysharing/v1/FindNearbyDevicesParams;", "", SmartAction.Feature.QUERY, "", "deviceType", "isMyDevice", "", "maxSearchTimeMillis", "", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Long;I)V", "getQuery", "()Ljava/lang/String;", "getDeviceType$annotations", "()V", "getDeviceType", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getMaxSearchTimeMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getMaxCount", "()I", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.nearbysharing.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindNearbyDevicesParams {
    private final String deviceType;
    private final Boolean isMyDevice;
    private final int maxCount;
    private final Long maxSearchTimeMillis;
    private final String query;

    public FindNearbyDevicesParams(String str, String str2, Boolean bool, Long l, int i) {
        this.query = str;
        this.deviceType = str2;
        this.isMyDevice = bool;
        this.maxSearchTimeMillis = l;
        this.maxCount = i;
    }

    public /* synthetic */ FindNearbyDevicesParams(String str, String str2, Boolean bool, Long l, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : str2, (i2 & 4) != 0 ? null : bool, (i2 & 8) != 0 ? null : l, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindNearbyDevicesParams)) {
            return false;
        }
        FindNearbyDevicesParams findNearbyDevicesParams = (FindNearbyDevicesParams) other;
        return Intrinsics.areEqual(this.query, findNearbyDevicesParams.query) && Intrinsics.areEqual(this.deviceType, findNearbyDevicesParams.deviceType) && Intrinsics.areEqual(this.isMyDevice, findNearbyDevicesParams.isMyDevice) && Intrinsics.areEqual(this.maxSearchTimeMillis, findNearbyDevicesParams.maxSearchTimeMillis) && this.maxCount == findNearbyDevicesParams.maxCount;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final Long getMaxSearchTimeMillis() {
        return this.maxSearchTimeMillis;
    }

    public final String getQuery() {
        return this.query;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.deviceType, this.isMyDevice, this.maxSearchTimeMillis, Integer.valueOf(this.maxCount));
    }

    /* JADX INFO: renamed from: isMyDevice, reason: from getter */
    public final Boolean getIsMyDevice() {
        return this.isMyDevice;
    }
}
