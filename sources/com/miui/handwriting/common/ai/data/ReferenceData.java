package com.miui.handwriting.common.ai.data;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReferenceData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\n\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/miui/handwriting/common/ai/data/ReferenceData;", "", "types", "", "Lcom/miui/handwriting/common/ai/data/ReferenceItemData;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;)V", "getTypes", "()Ljava/util/List;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ReferenceData {
    private final List<ReferenceItemData> types;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ReferenceData copy$default(ReferenceData referenceData, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = referenceData.types;
        }
        return referenceData.copy(list);
    }

    public final List<ReferenceItemData> component1() {
        return this.types;
    }

    public final ReferenceData copy(List<ReferenceItemData> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return new ReferenceData(types);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ReferenceData) && Intrinsics.areEqual(this.types, ((ReferenceData) other).types);
    }

    public int hashCode() {
        return this.types.hashCode();
    }

    public String toString() {
        return "ReferenceData(types=" + this.types + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public ReferenceData(List<ReferenceItemData> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        this.types = types;
    }

    public final List<ReferenceItemData> getTypes() {
        return this.types;
    }
}
