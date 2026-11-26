package com.miui.aiengine.common;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NetConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/miui/aiengine/common/TimeOutConfig;", "", "connectTimeout", "", "readTimeout", "writeTimeout", "callTimeout", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIII)V", "getConnectTimeout", "()I", "getReadTimeout", "getWriteTimeout", "getCallTimeout", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class TimeOutConfig {
    private final int callTimeout;
    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;

    public TimeOutConfig() {
        this(0, 0, 0, 0, 15, null);
    }

    public static /* synthetic */ TimeOutConfig copy$default(TimeOutConfig timeOutConfig, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = timeOutConfig.connectTimeout;
        }
        if ((i5 & 2) != 0) {
            i2 = timeOutConfig.readTimeout;
        }
        if ((i5 & 4) != 0) {
            i3 = timeOutConfig.writeTimeout;
        }
        if ((i5 & 8) != 0) {
            i4 = timeOutConfig.callTimeout;
        }
        return timeOutConfig.copy(i, i2, i3, i4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getConnectTimeout() {
        return this.connectTimeout;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getReadTimeout() {
        return this.readTimeout;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getWriteTimeout() {
        return this.writeTimeout;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getCallTimeout() {
        return this.callTimeout;
    }

    public final TimeOutConfig copy(int connectTimeout, int readTimeout, int writeTimeout, int callTimeout) {
        return new TimeOutConfig(connectTimeout, readTimeout, writeTimeout, callTimeout);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimeOutConfig)) {
            return false;
        }
        TimeOutConfig timeOutConfig = (TimeOutConfig) other;
        return this.connectTimeout == timeOutConfig.connectTimeout && this.readTimeout == timeOutConfig.readTimeout && this.writeTimeout == timeOutConfig.writeTimeout && this.callTimeout == timeOutConfig.callTimeout;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.connectTimeout) * 31) + Integer.hashCode(this.readTimeout)) * 31) + Integer.hashCode(this.writeTimeout)) * 31) + Integer.hashCode(this.callTimeout);
    }

    public String toString() {
        return "TimeOutConfig(connectTimeout=" + this.connectTimeout + ", readTimeout=" + this.readTimeout + ", writeTimeout=" + this.writeTimeout + ", callTimeout=" + this.callTimeout + ")";
    }

    public TimeOutConfig(int i, int i2, int i3, int i4) {
        this.connectTimeout = i;
        this.readTimeout = i2;
        this.writeTimeout = i3;
        this.callTimeout = i4;
    }

    public /* synthetic */ TimeOutConfig(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 300 : i, (i5 & 2) != 0 ? 300 : i2, (i5 & 4) != 0 ? 300 : i3, (i5 & 8) != 0 ? 600 : i4);
    }

    public final int getCallTimeout() {
        return this.callTimeout;
    }

    public final int getConnectTimeout() {
        return this.connectTimeout;
    }

    public final int getReadTimeout() {
        return this.readTimeout;
    }

    public final int getWriteTimeout() {
        return this.writeTimeout;
    }
}
