package xc.engine.math.iink.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.math.iink.bean.BoundingBox;

/* JADX INFO: compiled from: JiixDataModels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lxc/engine/math/iink/data/ParsedMathElement;", "", "blockId", "", "boundingBox", "Lxc/engine/math/iink/bean/BoundingBox;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lxc/engine/math/iink/bean/BoundingBox;)V", "getBlockId", "()Ljava/lang/String;", "getBoundingBox", "()Lxc/engine/math/iink/bean/BoundingBox;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParsedMathElement {
    private final String blockId;
    private final BoundingBox boundingBox;

    public static /* synthetic */ ParsedMathElement copy$default(ParsedMathElement parsedMathElement, String str, BoundingBox boundingBox, int i, Object obj) {
        if ((i & 1) != 0) {
            str = parsedMathElement.blockId;
        }
        if ((i & 2) != 0) {
            boundingBox = parsedMathElement.boundingBox;
        }
        return parsedMathElement.copy(str, boundingBox);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getBlockId() {
        return this.blockId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public final ParsedMathElement copy(String blockId, BoundingBox boundingBox) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        return new ParsedMathElement(blockId, boundingBox);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParsedMathElement)) {
            return false;
        }
        ParsedMathElement parsedMathElement = (ParsedMathElement) other;
        return Intrinsics.areEqual(this.blockId, parsedMathElement.blockId) && Intrinsics.areEqual(this.boundingBox, parsedMathElement.boundingBox);
    }

    public int hashCode() {
        int iHashCode = this.blockId.hashCode() * 31;
        BoundingBox boundingBox = this.boundingBox;
        return iHashCode + (boundingBox == null ? 0 : boundingBox.hashCode());
    }

    public String toString() {
        return "ParsedMathElement(blockId=" + this.blockId + ", boundingBox=" + this.boundingBox + ")";
    }

    public ParsedMathElement(String blockId, BoundingBox boundingBox) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        this.blockId = blockId;
        this.boundingBox = boundingBox;
    }

    public final String getBlockId() {
        return this.blockId;
    }

    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }
}
