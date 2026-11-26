package xc.engine.math.iink.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JiixDataModels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u0013"}, d2 = {"Lxc/engine/math/iink/data/ParsedComputationResult;", "", "result", "", "isValid", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Z)V", "getResult", "()Ljava/lang/String;", "()Z", "component1", "component2", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParsedComputationResult {
    private final boolean isValid;
    private final String result;

    public static /* synthetic */ ParsedComputationResult copy$default(ParsedComputationResult parsedComputationResult, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = parsedComputationResult.result;
        }
        if ((i & 2) != 0) {
            z = parsedComputationResult.isValid;
        }
        return parsedComputationResult.copy(str, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsValid() {
        return this.isValid;
    }

    public final ParsedComputationResult copy(String result, boolean isValid) {
        Intrinsics.checkNotNullParameter(result, "result");
        return new ParsedComputationResult(result, isValid);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParsedComputationResult)) {
            return false;
        }
        ParsedComputationResult parsedComputationResult = (ParsedComputationResult) other;
        return Intrinsics.areEqual(this.result, parsedComputationResult.result) && this.isValid == parsedComputationResult.isValid;
    }

    public int hashCode() {
        return (this.result.hashCode() * 31) + Boolean.hashCode(this.isValid);
    }

    public String toString() {
        return "ParsedComputationResult(result=" + this.result + ", isValid=" + this.isValid + ")";
    }

    public ParsedComputationResult(String result, boolean z) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.result = result;
        this.isValid = z;
    }

    public final String getResult() {
        return this.result;
    }

    public final boolean isValid() {
        return this.isValid;
    }
}
