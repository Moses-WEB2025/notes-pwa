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
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/PerScreenMetadata;", "", "description", "", "intentUri", "itemizationType", "deviceStateItemsMetadata", "", "Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItemMetadata;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getDescription", "()Ljava/lang/String;", "getIntentUri", "getItemizationType", "getDeviceStateItemsMetadata", "()Ljava/util/List;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PerScreenMetadata {
    private final String description;
    private final List<DeviceStateItemMetadata> deviceStateItemsMetadata;
    private final String intentUri;
    private final String itemizationType;

    public PerScreenMetadata(String description, String str, String str2, List<DeviceStateItemMetadata> deviceStateItemsMetadata) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(deviceStateItemsMetadata, "deviceStateItemsMetadata");
        this.description = description;
        this.intentUri = str;
        this.itemizationType = str2;
        this.deviceStateItemsMetadata = deviceStateItemsMetadata;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PerScreenMetadata(String str, String str2, String str3, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 8) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        int i2 = i & 4;
        int i3 = i & 2;
        this(str, i3 != 0 ? null : str2, i2 != 0 ? null : str3, list);
    }

    public boolean equals(Object other) {
        if (!(other instanceof PerScreenMetadata)) {
            return false;
        }
        PerScreenMetadata perScreenMetadata = (PerScreenMetadata) other;
        return Intrinsics.areEqual(this.description, perScreenMetadata.description) && Intrinsics.areEqual(this.intentUri, perScreenMetadata.intentUri) && Intrinsics.areEqual(this.itemizationType, perScreenMetadata.itemizationType) && Intrinsics.areEqual(this.deviceStateItemsMetadata, perScreenMetadata.deviceStateItemsMetadata);
    }

    public final String getDescription() {
        return this.description;
    }

    public final List<DeviceStateItemMetadata> getDeviceStateItemsMetadata() {
        return this.deviceStateItemsMetadata;
    }

    public final String getIntentUri() {
        return this.intentUri;
    }

    public final String getItemizationType() {
        return this.itemizationType;
    }

    public int hashCode() {
        return Objects.hash(this.description, this.intentUri, this.itemizationType, this.deviceStateItemsMetadata);
    }
}
