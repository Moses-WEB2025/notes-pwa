package com.miui.handwriting.common.ai.data;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Result.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001c\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B;\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00028\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001d\u001a\u00020\bHÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\t\u0010\u001f\u001a\u00020\u000bHÆ\u0003J\u000e\u0010 \u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0019JT\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010%\u001a\u00020\bHÖ\u0001J\t\u0010&\u001a\u00020\u0004HÖ\u0001R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\f\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lcom/miui/handwriting/common/ai/data/Result;", ExifInterface.GPS_DIRECTION_TRUE, "", "result", "", "retriable", "", "code", "", "description", "ts", "", "data", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ZILjava/lang/String;JLjava/lang/Object;)V", "getResult", "()Ljava/lang/String;", "getRetriable", "()Z", "getCode", "()I", "getDescription", "getTs", "()J", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/String;ZILjava/lang/String;JLjava/lang/Object;)Lcom/miui/handwriting/common/ai/data/Result;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class Result<T> {
    private final int code;
    private final T data;
    private final String description;
    private final String result;
    private final boolean retriable;
    private final long ts;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Result copy$default(Result result, String str, boolean z, int i, String str2, long j, Object obj, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            str = result.result;
        }
        if ((i2 & 2) != 0) {
            z = result.retriable;
        }
        if ((i2 & 4) != 0) {
            i = result.code;
        }
        if ((i2 & 8) != 0) {
            str2 = result.description;
        }
        if ((i2 & 16) != 0) {
            j = result.ts;
        }
        if ((i2 & 32) != 0) {
            obj = result.data;
        }
        Object obj3 = obj;
        long j2 = j;
        return result.copy(str, z, i, str2, j2, obj3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getRetriable() {
        return this.retriable;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getTs() {
        return this.ts;
    }

    public final T component6() {
        return this.data;
    }

    public final Result<T> copy(String result, boolean retriable, int code, String description, long ts, T data) {
        return new Result<>(result, retriable, code, description, ts, data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Result)) {
            return false;
        }
        Result result = (Result) other;
        return Intrinsics.areEqual(this.result, result.result) && this.retriable == result.retriable && this.code == result.code && Intrinsics.areEqual(this.description, result.description) && this.ts == result.ts && Intrinsics.areEqual(this.data, result.data);
    }

    public int hashCode() {
        String str = this.result;
        int iHashCode = (((((str == null ? 0 : str.hashCode()) * 31) + Boolean.hashCode(this.retriable)) * 31) + Integer.hashCode(this.code)) * 31;
        String str2 = this.description;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + Long.hashCode(this.ts)) * 31;
        T t = this.data;
        return iHashCode2 + (t != null ? t.hashCode() : 0);
    }

    public String toString() {
        return "Result(result=" + this.result + ", retriable=" + this.retriable + ", code=" + this.code + ", description=" + this.description + ", ts=" + this.ts + ", data=" + this.data + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public Result(String str, boolean z, int i, String str2, long j, T t) {
        this.result = str;
        this.retriable = z;
        this.code = i;
        this.description = str2;
        this.ts = j;
        this.data = t;
    }

    public final String getResult() {
        return this.result;
    }

    public final boolean getRetriable() {
        return this.retriable;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getDescription() {
        return this.description;
    }

    public final long getTs() {
        return this.ts;
    }

    public final T getData() {
        return this.data;
    }
}
