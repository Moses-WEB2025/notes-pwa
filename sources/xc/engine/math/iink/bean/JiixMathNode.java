package xc.engine.math.iink.bean;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JiixMathNode.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\bHÆ\u0003J?\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lxc/engine/math/iink/bean/JiixMathNode;", "", "type", "", Todo.LABEL, "operands", "", "boundingBox", "Lxc/engine/math/iink/bean/BoundingBox;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lxc/engine/math/iink/bean/BoundingBox;)V", "getType", "()Ljava/lang/String;", "getLabel", "getOperands", "()Ljava/util/List;", "getBoundingBox", "()Lxc/engine/math/iink/bean/BoundingBox;", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class JiixMathNode {
    private final BoundingBox boundingBox;
    private final String label;
    private final List<JiixMathNode> operands;
    private final String type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JiixMathNode copy$default(JiixMathNode jiixMathNode, String str, String str2, List list, BoundingBox boundingBox, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jiixMathNode.type;
        }
        if ((i & 2) != 0) {
            str2 = jiixMathNode.label;
        }
        if ((i & 4) != 0) {
            list = jiixMathNode.operands;
        }
        if ((i & 8) != 0) {
            boundingBox = jiixMathNode.boundingBox;
        }
        return jiixMathNode.copy(str, str2, list, boundingBox);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    public final List<JiixMathNode> component3() {
        return this.operands;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public final JiixMathNode copy(String type, String label, List<JiixMathNode> operands, BoundingBox boundingBox) {
        return new JiixMathNode(type, label, operands, boundingBox);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JiixMathNode)) {
            return false;
        }
        JiixMathNode jiixMathNode = (JiixMathNode) other;
        return Intrinsics.areEqual(this.type, jiixMathNode.type) && Intrinsics.areEqual(this.label, jiixMathNode.label) && Intrinsics.areEqual(this.operands, jiixMathNode.operands) && Intrinsics.areEqual(this.boundingBox, jiixMathNode.boundingBox);
    }

    public int hashCode() {
        String str = this.type;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.label;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        List<JiixMathNode> list = this.operands;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        BoundingBox boundingBox = this.boundingBox;
        return iHashCode3 + (boundingBox != null ? boundingBox.hashCode() : 0);
    }

    public String toString() {
        return "JiixMathNode(type=" + this.type + ", label=" + this.label + ", operands=" + this.operands + ", boundingBox=" + this.boundingBox + ")";
    }

    public JiixMathNode(String str, String str2, List<JiixMathNode> list, BoundingBox boundingBox) {
        this.type = str;
        this.label = str2;
        this.operands = list;
        this.boundingBox = boundingBox;
    }

    public final String getType() {
        return this.type;
    }

    public final String getLabel() {
        return this.label;
    }

    public final List<JiixMathNode> getOperands() {
        return this.operands;
    }

    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }
}
