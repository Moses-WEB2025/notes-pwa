package com.miui.aiengine.common;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Response.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0014"}, d2 = {"Lcom/miui/aiengine/common/SUCCESS;", "Lcom/miui/aiengine/common/Result;", "data", "", "id", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getData", "()Ljava/lang/String;", "getId", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class SUCCESS extends Result {
    private final String data;
    private final String id;

    public static /* synthetic */ SUCCESS copy$default(SUCCESS success, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = success.data;
        }
        if ((i & 2) != 0) {
            str2 = success.id;
        }
        return success.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getData() {
        return this.data;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    public final SUCCESS copy(String data, String id) {
        Intrinsics.checkNotNullParameter(data, "data");
        return new SUCCESS(data, id);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SUCCESS)) {
            return false;
        }
        SUCCESS success = (SUCCESS) other;
        return Intrinsics.areEqual(this.data, success.data) && Intrinsics.areEqual(this.id, success.id);
    }

    public int hashCode() {
        int iHashCode = this.data.hashCode() * 31;
        String str = this.id;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "SUCCESS(data=" + this.data + ", id=" + this.id + ")";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SUCCESS(String data, String str) {
        super(null);
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.id = str;
    }

    public /* synthetic */ SUCCESS(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? "null" : str2);
    }

    public final String getData() {
        return this.data;
    }

    public final String getId() {
        return this.id;
    }
}
