package com.miui.handwriting.common.ai.data;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VersionData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lcom/miui/handwriting/common/ai/data/VersionData;", "", "version", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getVersion", "()Ljava/lang/String;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class VersionData {
    private final String version;

    public static /* synthetic */ VersionData copy$default(VersionData versionData, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = versionData.version;
        }
        return versionData.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    public final VersionData copy(String version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return new VersionData(version);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof VersionData) && Intrinsics.areEqual(this.version, ((VersionData) other).version);
    }

    public int hashCode() {
        return this.version.hashCode();
    }

    public String toString() {
        return "VersionData(version=" + this.version + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public VersionData(String version) {
        Intrinsics.checkNotNullParameter(version, "version");
        this.version = version;
    }

    public final String getVersion() {
        return this.version;
    }
}
