package com.miui.aiengine.common;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Response.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0004HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00018\u0000HÆ\u0003¢\u0006\u0002\u0010\u0013JN\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00018\u0000HÆ\u0001¢\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0004HÖ\u0001J\t\u0010 \u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0015\u0010\t\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lcom/miui/aiengine/common/Response;", ExifInterface.GPS_DIRECTION_TRUE, "", "code", "", "message", "", "traceId", "id", "data", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "getTraceId", "getId", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component3", "component4", "component5", "copy", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Lcom/miui/aiengine/common/Response;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class Response<T> {
    private final int code;
    private final T data;
    private final String id;
    private final String message;
    private final String traceId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Response copy$default(Response response, int i, String str, String str2, String str3, Object obj, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            i = response.code;
        }
        if ((i2 & 2) != 0) {
            str = response.message;
        }
        if ((i2 & 4) != 0) {
            str2 = response.traceId;
        }
        if ((i2 & 8) != 0) {
            str3 = response.id;
        }
        if ((i2 & 16) != 0) {
            obj = response.data;
        }
        Object obj3 = obj;
        String str4 = str2;
        return response.copy(i, str, str4, str3, obj3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTraceId() {
        return this.traceId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getId() {
        return this.id;
    }

    public final T component5() {
        return this.data;
    }

    public final Response<T> copy(int code, String message, String traceId, String id, T data) {
        return new Response<>(code, message, traceId, id, data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Response)) {
            return false;
        }
        Response response = (Response) other;
        return this.code == response.code && Intrinsics.areEqual(this.message, response.message) && Intrinsics.areEqual(this.traceId, response.traceId) && Intrinsics.areEqual(this.id, response.id) && Intrinsics.areEqual(this.data, response.data);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.code) * 31;
        String str = this.message;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.traceId;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.id;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        T t = this.data;
        return iHashCode4 + (t != null ? t.hashCode() : 0);
    }

    public String toString() {
        return "Response(code=" + this.code + ", message=" + this.message + ", traceId=" + this.traceId + ", id=" + this.id + ", data=" + this.data + ")";
    }

    public Response(int i, String str, String str2, String str3, T t) {
        this.code = i;
        this.message = str;
        this.traceId = str2;
        this.id = str3;
        this.data = t;
    }

    public /* synthetic */ Response(int i, String str, String str2, String str3, Object obj, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3, (i2 & 16) != 0 ? null : obj);
    }

    public final int getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }

    public final String getTraceId() {
        return this.traceId;
    }

    public final String getId() {
        return this.id;
    }

    public final T getData() {
        return this.data;
    }
}
