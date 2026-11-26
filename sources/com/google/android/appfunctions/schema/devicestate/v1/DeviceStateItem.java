package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItem;", "", "key", "", "purpose", "name", "Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;", "jsonValue", "lastUpdatedEpochMillis", "", "hintText", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)V", "getKey", "()Ljava/lang/String;", "getPurpose", "getName", "()Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;", "getJsonValue", "getLastUpdatedEpochMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getHintText", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeviceStateItem {
    private final String hintText;
    private final String jsonValue;
    private final String key;
    private final Long lastUpdatedEpochMillis;
    private final LocalizedString name;
    private final String purpose;

    public DeviceStateItem(String key, String str, LocalizedString localizedString, String str2, Long l, String str3) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.key = key;
        this.purpose = str;
        this.name = localizedString;
        this.jsonValue = str2;
        this.lastUpdatedEpochMillis = l;
        this.hintText = str3;
    }

    public /* synthetic */ DeviceStateItem(String str, String str2, LocalizedString localizedString, String str3, Long l, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : localizedString, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : l, (i & 32) != 0 ? null : str4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeviceStateItem)) {
            return false;
        }
        DeviceStateItem deviceStateItem = (DeviceStateItem) other;
        return Intrinsics.areEqual(this.key, deviceStateItem.key) && Intrinsics.areEqual(this.purpose, deviceStateItem.purpose) && Intrinsics.areEqual(this.name, deviceStateItem.name) && Intrinsics.areEqual(this.jsonValue, deviceStateItem.jsonValue) && Intrinsics.areEqual(this.lastUpdatedEpochMillis, deviceStateItem.lastUpdatedEpochMillis) && Intrinsics.areEqual(this.hintText, deviceStateItem.hintText);
    }

    public final String getHintText() {
        return this.hintText;
    }

    public final String getJsonValue() {
        return this.jsonValue;
    }

    public final String getKey() {
        return this.key;
    }

    public final Long getLastUpdatedEpochMillis() {
        return this.lastUpdatedEpochMillis;
    }

    public final LocalizedString getName() {
        return this.name;
    }

    public final String getPurpose() {
        return this.purpose;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.purpose, this.name, this.jsonValue, this.lastUpdatedEpochMillis, this.hintText);
    }
}
