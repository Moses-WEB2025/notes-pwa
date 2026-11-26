package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J)\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001e"}, d2 = {"Lcom/agog/mathdisplay/parse/MTEnvProperties;", "", "envName", "", "ended", "", "numRows", "", "(Ljava/lang/String;ZJ)V", "getEnded", "()Z", "setEnded", "(Z)V", "getEnvName", "()Ljava/lang/String;", "setEnvName", "(Ljava/lang/String;)V", "getNumRows", "()J", "setNumRows", "(J)V", "component1", "component2", "component3", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class MTEnvProperties {
    private boolean ended;
    private String envName;
    private long numRows;

    public static /* synthetic */ MTEnvProperties copy$default(MTEnvProperties mTEnvProperties, String str, boolean z, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = mTEnvProperties.envName;
        }
        if ((i & 2) != 0) {
            z = mTEnvProperties.ended;
        }
        if ((i & 4) != 0) {
            j = mTEnvProperties.numRows;
        }
        return mTEnvProperties.copy(str, z, j);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getEnvName() {
        return this.envName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getEnded() {
        return this.ended;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long getNumRows() {
        return this.numRows;
    }

    public final MTEnvProperties copy(String envName, boolean ended, long numRows) {
        return new MTEnvProperties(envName, ended, numRows);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MTEnvProperties)) {
            return false;
        }
        MTEnvProperties mTEnvProperties = (MTEnvProperties) other;
        return Intrinsics.areEqual(this.envName, mTEnvProperties.envName) && this.ended == mTEnvProperties.ended && this.numRows == mTEnvProperties.numRows;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    public int hashCode() {
        String str = this.envName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        boolean z = this.ended;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        return ((iHashCode + r1) * 31) + Long.hashCode(this.numRows);
    }

    public String toString() {
        return "MTEnvProperties(envName=" + ((Object) this.envName) + ", ended=" + this.ended + ", numRows=" + this.numRows + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public MTEnvProperties(String str, boolean z, long j) {
        this.envName = str;
        this.ended = z;
        this.numRows = j;
    }

    public /* synthetic */ MTEnvProperties(String str, boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? 0L : j);
    }

    public final boolean getEnded() {
        return this.ended;
    }

    public final String getEnvName() {
        return this.envName;
    }

    public final long getNumRows() {
        return this.numRows;
    }

    public final void setEnded(boolean z) {
        this.ended = z;
    }

    public final void setEnvName(String str) {
        this.envName = str;
    }

    public final void setNumRows(long j) {
        this.numRows = j;
    }
}
