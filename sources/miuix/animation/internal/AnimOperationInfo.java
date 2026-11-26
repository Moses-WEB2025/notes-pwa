package miuix.animation.internal;

import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miuix.animation.IAnimTarget;
import miuix.animation.ValueTarget;
import miuix.animation.property.FloatProperty;

/* JADX INFO: loaded from: classes5.dex */
class AnimOperationInfo {
    public final byte op;
    public final List<FloatProperty> propList;
    public volatile long sendTime;
    public final IAnimTarget target;
    public int usedCount = 0;

    AnimOperationInfo(IAnimTarget iAnimTarget, byte b, String[] strArr, FloatProperty[] floatPropertyArr) {
        this.op = b;
        this.target = iAnimTarget;
        if (strArr == null || !(iAnimTarget instanceof ValueTarget)) {
            if (floatPropertyArr != null) {
                this.propList = Arrays.asList(floatPropertyArr);
                return;
            } else {
                this.propList = null;
                return;
            }
        }
        this.propList = new ArrayList();
        for (String str : strArr) {
            this.propList.add(ValueTarget.getFloatProperty(str));
        }
    }

    boolean isUsed() {
        List<FloatProperty> list = this.propList;
        int size = list == null ? 0 : list.size();
        int i = this.usedCount;
        if (size == 0) {
            return i > 0;
        }
        return i == size;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("AnimOperationInfo{op=").append((int) this.op).append(", propList=");
        List<FloatProperty> list = this.propList;
        return sbAppend.append(list != null ? Arrays.toString(list.toArray()) : null).append(", ").append(this.target).append(CoreConstants.CURLY_RIGHT).toString();
    }
}
