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
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/PerScreenDeviceStates;", "", "description", "", "intentUri", "deviceStateItems", "", "Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getDescription", "()Ljava/lang/String;", "getIntentUri", "getDeviceStateItems", "()Ljava/util/List;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PerScreenDeviceStates {
    private final String description;
    private final List<DeviceStateItem> deviceStateItems;
    private final String intentUri;

    public PerScreenDeviceStates(String description, String str, List<DeviceStateItem> deviceStateItems) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(deviceStateItems, "deviceStateItems");
        this.description = description;
        this.intentUri = str;
        this.deviceStateItems = deviceStateItems;
    }

    public /* synthetic */ PerScreenDeviceStates(String str, String str2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    public boolean equals(Object other) {
        if (!(other instanceof PerScreenDeviceStates)) {
            return false;
        }
        PerScreenDeviceStates perScreenDeviceStates = (PerScreenDeviceStates) other;
        return Intrinsics.areEqual(this.description, perScreenDeviceStates.description) && Intrinsics.areEqual(this.intentUri, perScreenDeviceStates.intentUri) && Intrinsics.areEqual(this.deviceStateItems, perScreenDeviceStates.deviceStateItems);
    }

    public final String getDescription() {
        return this.description;
    }

    public final List<DeviceStateItem> getDeviceStateItems() {
        return this.deviceStateItems;
    }

    public final String getIntentUri() {
        return this.intentUri;
    }

    public int hashCode() {
        return Objects.hash(this.description, this.intentUri, this.deviceStateItems);
    }
}
