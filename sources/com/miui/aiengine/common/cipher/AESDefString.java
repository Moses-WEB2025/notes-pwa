package com.miui.aiengine.common.cipher;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: AESDefString.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\f\u001a\u00020\u0003H\u0016J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/miui/aiengine/common/cipher/AESDefString;", "", "version", "", "iv", "data", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getVersion", "()Ljava/lang/String;", "getIv", "getData", "toString", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class AESDefString {
    private final String data;
    private final String iv;
    private final String version;

    public static /* synthetic */ AESDefString copy$default(AESDefString aESDefString, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = aESDefString.version;
        }
        if ((i & 2) != 0) {
            str2 = aESDefString.iv;
        }
        if ((i & 4) != 0) {
            str3 = aESDefString.data;
        }
        return aESDefString.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getIv() {
        return this.iv;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getData() {
        return this.data;
    }

    public final AESDefString copy(String version, String iv, String data) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(data, "data");
        return new AESDefString(version, iv, data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AESDefString)) {
            return false;
        }
        AESDefString aESDefString = (AESDefString) other;
        return Intrinsics.areEqual(this.version, aESDefString.version) && Intrinsics.areEqual(this.iv, aESDefString.iv) && Intrinsics.areEqual(this.data, aESDefString.data);
    }

    public int hashCode() {
        return (((this.version.hashCode() * 31) + this.iv.hashCode()) * 31) + this.data.hashCode();
    }

    public AESDefString(String version, String iv, String data) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(data, "data");
        this.version = version;
        this.iv = iv;
        this.data = data;
    }

    public final String getData() {
        return this.data;
    }

    public final String getIv() {
        return this.iv;
    }

    public final String getVersion() {
        return this.version;
    }

    public String toString() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%s:%s:%s", Arrays.copyOf(new Object[]{this.version, this.iv, this.data}, 3));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
