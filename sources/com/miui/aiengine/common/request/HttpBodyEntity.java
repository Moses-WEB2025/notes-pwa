package com.miui.aiengine.common.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HttpBodyEntity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012&\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007¢\u0006\u0004\b\b\u0010\tJ\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J)\u0010\u000f\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007HÆ\u0003J?\u0010\u0010\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032(\b\u0002\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR1\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lcom/miui/aiengine/common/request/HttpBodyEntity;", "", "type", "Lcom/miui/aiengine/common/request/HttpBodyEntityType;", "elements", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/aiengine/common/request/HttpBodyEntityType;Ljava/util/HashMap;)V", "getType", "()Lcom/miui/aiengine/common/request/HttpBodyEntityType;", "getElements", "()Ljava/util/HashMap;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class HttpBodyEntity {
    private final HashMap<String, String> elements;
    private final HttpBodyEntityType type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HttpBodyEntity copy$default(HttpBodyEntity httpBodyEntity, HttpBodyEntityType httpBodyEntityType, HashMap map, int i, Object obj) {
        if ((i & 1) != 0) {
            httpBodyEntityType = httpBodyEntity.type;
        }
        if ((i & 2) != 0) {
            map = httpBodyEntity.elements;
        }
        return httpBodyEntity.copy(httpBodyEntityType, map);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final HttpBodyEntityType getType() {
        return this.type;
    }

    public final HashMap<String, String> component2() {
        return this.elements;
    }

    public final HttpBodyEntity copy(HttpBodyEntityType type, HashMap<String, String> elements) {
        return new HttpBodyEntity(type, elements);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpBodyEntity)) {
            return false;
        }
        HttpBodyEntity httpBodyEntity = (HttpBodyEntity) other;
        return this.type == httpBodyEntity.type && Intrinsics.areEqual(this.elements, httpBodyEntity.elements);
    }

    public int hashCode() {
        HttpBodyEntityType httpBodyEntityType = this.type;
        int iHashCode = (httpBodyEntityType == null ? 0 : httpBodyEntityType.hashCode()) * 31;
        HashMap<String, String> map = this.elements;
        return iHashCode + (map != null ? map.hashCode() : 0);
    }

    public String toString() {
        return "HttpBodyEntity(type=" + this.type + ", elements=" + this.elements + ")";
    }

    public HttpBodyEntity(HttpBodyEntityType httpBodyEntityType, HashMap<String, String> map) {
        this.type = httpBodyEntityType;
        this.elements = map;
    }

    public /* synthetic */ HttpBodyEntity(HttpBodyEntityType httpBodyEntityType, HashMap map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : httpBodyEntityType, map);
    }

    public final HashMap<String, String> getElements() {
        return this.elements;
    }

    public final HttpBodyEntityType getType() {
        return this.type;
    }
}
