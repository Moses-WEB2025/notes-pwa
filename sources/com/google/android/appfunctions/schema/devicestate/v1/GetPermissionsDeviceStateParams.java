package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0013\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/GetPermissionsDeviceStateParams;", "", "requestInitiatedWhileUnlocked", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Boolean;)V", "getRequestInitiatedWhileUnlocked", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class GetPermissionsDeviceStateParams {
    private final Boolean requestInitiatedWhileUnlocked;

    public GetPermissionsDeviceStateParams() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public GetPermissionsDeviceStateParams(Boolean bool) {
        this.requestInitiatedWhileUnlocked = bool;
    }

    public /* synthetic */ GetPermissionsDeviceStateParams(Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : bool);
    }

    public boolean equals(Object other) {
        return (other instanceof GetPermissionsDeviceStateParams) && Intrinsics.areEqual(this.requestInitiatedWhileUnlocked, ((GetPermissionsDeviceStateParams) other).requestInitiatedWhileUnlocked);
    }

    public final Boolean getRequestInitiatedWhileUnlocked() {
        return this.requestInitiatedWhileUnlocked;
    }

    public int hashCode() {
        return Objects.hash(this.requestInitiatedWhileUnlocked);
    }
}
