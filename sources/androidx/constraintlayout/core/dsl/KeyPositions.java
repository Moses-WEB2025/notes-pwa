package androidx.constraintlayout.core.dsl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class KeyPositions extends Keys {
    private int[] mFrames;
    private String[] mTarget;
    private String mTransitionEasing = null;
    private Type mPositionType = null;
    private float[] mPercentWidth = null;
    private float[] mPercentHeight = null;
    private float[] mPercentX = null;
    private float[] mPercentY = null;

    public enum Type {
        CARTESIAN,
        SCREEN,
        PATH
    }

    public KeyPositions(int i, String... strArr) {
        this.mFrames = null;
        this.mTarget = strArr;
        int[] iArr = new int[i];
        this.mFrames = iArr;
        float length = 100.0f / (iArr.length + 1);
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.mFrames;
            if (i2 >= iArr2.length) {
                return;
            }
            iArr2[i2] = (int) ((i2 * length) + length);
            i2++;
        }
    }

    public String getTransitionEasing() {
        return this.mTransitionEasing;
    }

    public void setTransitionEasing(String str) {
        this.mTransitionEasing = str;
    }

    public int[] getFrames() {
        return this.mFrames;
    }

    public void setFrames(int... iArr) {
        this.mFrames = iArr;
    }

    public float[] getPercentWidth() {
        return this.mPercentWidth;
    }

    public void setPercentWidth(float... fArr) {
        this.mPercentWidth = fArr;
    }

    public float[] getPercentHeight() {
        return this.mPercentHeight;
    }

    public void setPercentHeight(float... fArr) {
        this.mPercentHeight = fArr;
    }

    public float[] getPercentX() {
        return this.mPercentX;
    }

    public void setPercentX(float... fArr) {
        this.mPercentX = fArr;
    }

    public float[] getPercentY() {
        return this.mPercentY;
    }

    public void setPercentY(float... fArr) {
        this.mPercentY = fArr;
    }

    public Type getPositionType() {
        return this.mPositionType;
    }

    public void setPositionType(Type type) {
        this.mPositionType = type;
    }

    public String[] getTarget() {
        return this.mTarget;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyPositions:{\n");
        append(sb, TypedValues.AttributesType.S_TARGET, this.mTarget);
        sb.append("frame:").append(Arrays.toString(this.mFrames)).append(",\n");
        if (this.mPositionType != null) {
            sb.append("type:'").append(this.mPositionType).append("',\n");
        }
        append(sb, "easing", this.mTransitionEasing);
        append(sb, "percentX", this.mPercentX);
        append(sb, "percentX", this.mPercentY);
        append(sb, "percentWidth", this.mPercentWidth);
        append(sb, "percentHeight", this.mPercentHeight);
        sb.append("},\n");
        return sb.toString();
    }
}
