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
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0007\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0015\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0007H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0018"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/AdjustNumericDeviceStateItemByPercentageParams;", "", "key", "", "itemizationKeys", "", "percentageAdjustment", "", "requestInitiatedWhileUnlocked", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;ILjava/lang/Boolean;)V", "getKey", "()Ljava/lang/String;", "getItemizationKeys", "()Ljava/util/List;", "getPercentageAdjustment", "()I", "getRequestInitiatedWhileUnlocked", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AdjustNumericDeviceStateItemByPercentageParams {
    private final List<String> itemizationKeys;
    private final String key;
    private final int percentageAdjustment;
    private final Boolean requestInitiatedWhileUnlocked;

    public AdjustNumericDeviceStateItemByPercentageParams(String key, List<String> itemizationKeys, int i, Boolean bool) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(itemizationKeys, "itemizationKeys");
        this.key = key;
        this.itemizationKeys = itemizationKeys;
        this.percentageAdjustment = i;
        this.requestInitiatedWhileUnlocked = bool;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ AdjustNumericDeviceStateItemByPercentageParams(String str, List list, int i, Boolean bool, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        this(str, list, i, (i2 & 8) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof AdjustNumericDeviceStateItemByPercentageParams)) {
            return false;
        }
        AdjustNumericDeviceStateItemByPercentageParams adjustNumericDeviceStateItemByPercentageParams = (AdjustNumericDeviceStateItemByPercentageParams) other;
        return Intrinsics.areEqual(this.key, adjustNumericDeviceStateItemByPercentageParams.key) && Intrinsics.areEqual(this.itemizationKeys, adjustNumericDeviceStateItemByPercentageParams.itemizationKeys) && this.percentageAdjustment == adjustNumericDeviceStateItemByPercentageParams.percentageAdjustment && Intrinsics.areEqual(this.requestInitiatedWhileUnlocked, adjustNumericDeviceStateItemByPercentageParams.requestInitiatedWhileUnlocked);
    }

    public final List<String> getItemizationKeys() {
        return this.itemizationKeys;
    }

    public final String getKey() {
        return this.key;
    }

    public final int getPercentageAdjustment() {
        return this.percentageAdjustment;
    }

    public final Boolean getRequestInitiatedWhileUnlocked() {
        return this.requestInitiatedWhileUnlocked;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.itemizationKeys, Integer.valueOf(this.percentageAdjustment), this.requestInitiatedWhileUnlocked);
    }
}
