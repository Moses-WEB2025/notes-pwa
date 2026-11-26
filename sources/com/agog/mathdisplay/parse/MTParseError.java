package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTParseError.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000f\u001a\u00020\u0010J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0010\u0010\u0014\u001a\u00020\u00102\b\u0010\u0015\u001a\u0004\u0018\u00010\u0000J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lcom/agog/mathdisplay/parse/MTParseError;", "", "errorcode", "Lcom/agog/mathdisplay/parse/MTParseErrors;", "errordesc", "", "(Lcom/agog/mathdisplay/parse/MTParseErrors;Ljava/lang/String;)V", "getErrorcode", "()Lcom/agog/mathdisplay/parse/MTParseErrors;", "setErrorcode", "(Lcom/agog/mathdisplay/parse/MTParseErrors;)V", "getErrordesc", "()Ljava/lang/String;", "setErrordesc", "(Ljava/lang/String;)V", "clear", "", "component1", "component2", "copy", "copyFrom", "src", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class MTParseError {
    private MTParseErrors errorcode;
    private String errordesc;

    public MTParseError() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ MTParseError copy$default(MTParseError mTParseError, MTParseErrors mTParseErrors, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            mTParseErrors = mTParseError.errorcode;
        }
        if ((i & 2) != 0) {
            str = mTParseError.errordesc;
        }
        return mTParseError.copy(mTParseErrors, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final MTParseErrors getErrorcode() {
        return this.errorcode;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getErrordesc() {
        return this.errordesc;
    }

    public final MTParseError copy(MTParseErrors errorcode, String errordesc) {
        Intrinsics.checkNotNullParameter(errorcode, "errorcode");
        Intrinsics.checkNotNullParameter(errordesc, "errordesc");
        return new MTParseError(errorcode, errordesc);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MTParseError)) {
            return false;
        }
        MTParseError mTParseError = (MTParseError) other;
        return this.errorcode == mTParseError.errorcode && Intrinsics.areEqual(this.errordesc, mTParseError.errordesc);
    }

    public int hashCode() {
        return (this.errorcode.hashCode() * 31) + this.errordesc.hashCode();
    }

    public String toString() {
        return "MTParseError(errorcode=" + this.errorcode + ", errordesc=" + this.errordesc + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public MTParseError(MTParseErrors errorcode, String errordesc) {
        Intrinsics.checkNotNullParameter(errorcode, "errorcode");
        Intrinsics.checkNotNullParameter(errordesc, "errordesc");
        this.errorcode = errorcode;
        this.errordesc = errordesc;
    }

    public /* synthetic */ MTParseError(MTParseErrors mTParseErrors, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? MTParseErrors.ErrorNone : mTParseErrors, (i & 2) != 0 ? "" : str);
    }

    public final MTParseErrors getErrorcode() {
        return this.errorcode;
    }

    public final String getErrordesc() {
        return this.errordesc;
    }

    public final void setErrorcode(MTParseErrors mTParseErrors) {
        Intrinsics.checkNotNullParameter(mTParseErrors, "<set-?>");
        this.errorcode = mTParseErrors;
    }

    public final void setErrordesc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.errordesc = str;
    }

    public final void copyFrom(MTParseError src) {
        if (src != null) {
            this.errorcode = src.errorcode;
            this.errordesc = src.errordesc;
        }
    }

    public final void clear() {
        this.errorcode = MTParseErrors.ErrorNone;
        this.errordesc = "";
    }
}
