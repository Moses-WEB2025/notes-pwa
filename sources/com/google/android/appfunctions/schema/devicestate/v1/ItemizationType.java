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
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/ItemizationType;", "", "key", "", "values", "", "Lcom/google/android/appfunctions/schema/devicestate/v1/ItemizationDetail;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;)V", "getKey", "()Ljava/lang/String;", "getValues", "()Ljava/util/List;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ItemizationType {
    private final String key;
    private final List<ItemizationDetail> values;

    public ItemizationType(String key, List<ItemizationDetail> values) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(values, "values");
        this.key = key;
        this.values = values;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ItemizationType(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            list = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        this(str, list);
    }

    public boolean equals(Object other) {
        if (!(other instanceof ItemizationType)) {
            return false;
        }
        ItemizationType itemizationType = (ItemizationType) other;
        return Intrinsics.areEqual(this.key, itemizationType.key) && Intrinsics.areEqual(this.values, itemizationType.values);
    }

    public final String getKey() {
        return this.key;
    }

    public final List<ItemizationDetail> getValues() {
        return this.values;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.values);
    }
}
