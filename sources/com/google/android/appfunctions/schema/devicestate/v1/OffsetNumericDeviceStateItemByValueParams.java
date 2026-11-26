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
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0015\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/OffsetNumericDeviceStateItemByValueParams;", "", "key", "", "itemizationKeys", "", "valueAdjustment", "", "requestInitiatedWhileUnlocked", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;DLjava/lang/Boolean;)V", "getKey", "()Ljava/lang/String;", "getItemizationKeys", "()Ljava/util/List;", "getValueAdjustment", "()D", "getRequestInitiatedWhileUnlocked", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class OffsetNumericDeviceStateItemByValueParams {
    private final List<String> itemizationKeys;
    private final String key;
    private final Boolean requestInitiatedWhileUnlocked;
    private final double valueAdjustment;

    public OffsetNumericDeviceStateItemByValueParams(String key, List<String> itemizationKeys, double d, Boolean bool) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(itemizationKeys, "itemizationKeys");
        this.key = key;
        this.itemizationKeys = itemizationKeys;
        this.valueAdjustment = d;
        this.requestInitiatedWhileUnlocked = bool;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ OffsetNumericDeviceStateItemByValueParams(String str, List list, double d, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        this(str, list, d, (i & 8) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof OffsetNumericDeviceStateItemByValueParams)) {
            return false;
        }
        OffsetNumericDeviceStateItemByValueParams offsetNumericDeviceStateItemByValueParams = (OffsetNumericDeviceStateItemByValueParams) other;
        return Intrinsics.areEqual(this.key, offsetNumericDeviceStateItemByValueParams.key) && Intrinsics.areEqual(this.itemizationKeys, offsetNumericDeviceStateItemByValueParams.itemizationKeys) && this.valueAdjustment == offsetNumericDeviceStateItemByValueParams.valueAdjustment && Intrinsics.areEqual(this.requestInitiatedWhileUnlocked, offsetNumericDeviceStateItemByValueParams.requestInitiatedWhileUnlocked);
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

    public final double getValueAdjustment() {
        return this.valueAdjustment;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.itemizationKeys, Double.valueOf(this.valueAdjustment), this.requestInitiatedWhileUnlocked);
    }
}
