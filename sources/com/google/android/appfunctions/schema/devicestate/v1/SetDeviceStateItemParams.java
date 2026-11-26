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
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0013\u001a\u00020\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/SetDeviceStateItemParams;", "", "key", "", "itemizationKeys", "", "value", "requestInitiatedWhileUnlocked", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Boolean;)V", "getKey", "()Ljava/lang/String;", "getItemizationKeys", "()Ljava/util/List;", "getValue", "getRequestInitiatedWhileUnlocked", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SetDeviceStateItemParams {
    private final List<String> itemizationKeys;
    private final String key;
    private final Boolean requestInitiatedWhileUnlocked;
    private final String value;

    public SetDeviceStateItemParams(String key, List<String> itemizationKeys, String value, Boolean bool) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(itemizationKeys, "itemizationKeys");
        Intrinsics.checkNotNullParameter(value, "value");
        this.key = key;
        this.itemizationKeys = itemizationKeys;
        this.value = value;
        this.requestInitiatedWhileUnlocked = bool;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ SetDeviceStateItemParams(String str, List list, String str2, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        this(str, list, str2, (i & 8) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof SetDeviceStateItemParams)) {
            return false;
        }
        SetDeviceStateItemParams setDeviceStateItemParams = (SetDeviceStateItemParams) other;
        return Intrinsics.areEqual(this.key, setDeviceStateItemParams.key) && Intrinsics.areEqual(this.itemizationKeys, setDeviceStateItemParams.itemizationKeys) && Intrinsics.areEqual(this.value, setDeviceStateItemParams.value) && Intrinsics.areEqual(this.requestInitiatedWhileUnlocked, setDeviceStateItemParams.requestInitiatedWhileUnlocked);
    }

    public final List<String> getItemizationKeys() {
        return this.itemizationKeys;
    }

    public final String getKey() {
        return this.key;
    }

    public final Boolean getRequestInitiatedWhileUnlocked() {
        return this.requestInitiatedWhileUnlocked;
    }

    public final String getValue() {
        return this.value;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.itemizationKeys, this.value, this.requestInitiatedWhileUnlocked);
    }
}
