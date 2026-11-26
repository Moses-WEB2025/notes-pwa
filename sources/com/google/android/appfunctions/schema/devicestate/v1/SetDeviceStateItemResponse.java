package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/SetDeviceStateItemResponse;", "", "isSuccessful", "", "currentValue", "", "failureReason", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLjava/lang/String;Ljava/lang/String;)V", "()Z", "getCurrentValue", "()Ljava/lang/String;", "getFailureReason", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SetDeviceStateItemResponse {
    private final String currentValue;
    private final String failureReason;
    private final boolean isSuccessful;

    public SetDeviceStateItemResponse(boolean z, String currentValue, String str) {
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        this.isSuccessful = z;
        this.currentValue = currentValue;
        this.failureReason = str;
    }

    public /* synthetic */ SetDeviceStateItemResponse(boolean z, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, str, (i & 4) != 0 ? null : str2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof SetDeviceStateItemResponse)) {
            return false;
        }
        SetDeviceStateItemResponse setDeviceStateItemResponse = (SetDeviceStateItemResponse) other;
        return this.isSuccessful == setDeviceStateItemResponse.isSuccessful && Intrinsics.areEqual(this.currentValue, setDeviceStateItemResponse.currentValue) && Intrinsics.areEqual(this.failureReason, setDeviceStateItemResponse.failureReason);
    }

    public final String getCurrentValue() {
        return this.currentValue;
    }

    public final String getFailureReason() {
        return this.failureReason;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.isSuccessful), this.currentValue, this.failureReason);
    }

    /* JADX INFO: renamed from: isSuccessful, reason: from getter */
    public final boolean getIsSuccessful() {
        return this.isSuccessful;
    }
}
