package com.miui.notes.ai.request;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResponseData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0013\u001a\u00020\u0004HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\u000e\u0010\u0016\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0011J>\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0004HÖ\u0001J\t\u0010\u001d\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\b\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lcom/miui/notes/ai/request/ResponseData;", ExifInterface.GPS_DIRECTION_TRUE, "", "code", "", "message", "", "traceId", "data", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "getTraceId", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component3", "component4", "copy", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Lcom/miui/notes/ai/request/ResponseData;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ResponseData<T> {
    private final int code;
    private final T data;
    private final String message;
    private final String traceId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ResponseData copy$default(ResponseData responseData, int i, String str, String str2, Object obj, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            i = responseData.code;
        }
        if ((i2 & 2) != 0) {
            str = responseData.message;
        }
        if ((i2 & 4) != 0) {
            str2 = responseData.traceId;
        }
        if ((i2 & 8) != 0) {
            obj = responseData.data;
        }
        return responseData.copy(i, str, str2, obj);
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

    public final T component4() {
        return this.data;
    }

    public final ResponseData<T> copy(int code, String message, String traceId, T data) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        return new ResponseData<>(code, message, traceId, data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResponseData)) {
            return false;
        }
        ResponseData responseData = (ResponseData) other;
        return this.code == responseData.code && Intrinsics.areEqual(this.message, responseData.message) && Intrinsics.areEqual(this.traceId, responseData.traceId) && Intrinsics.areEqual(this.data, responseData.data);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.code) * 31;
        String str = this.message;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.traceId.hashCode()) * 31;
        T t = this.data;
        return iHashCode2 + (t != null ? t.hashCode() : 0);
    }

    public String toString() {
        return "ResponseData(code=" + this.code + ", message=" + this.message + ", traceId=" + this.traceId + ", data=" + this.data + ")";
    }

    public ResponseData(int i, String str, String traceId, T t) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        this.code = i;
        this.message = str;
        this.traceId = traceId;
        this.data = t;
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

    public final T getData() {
        return this.data;
    }
}
