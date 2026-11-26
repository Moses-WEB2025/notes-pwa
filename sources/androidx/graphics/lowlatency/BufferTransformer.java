package androidx.graphics.lowlatency;

import android.opengl.Matrix;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;

/* JADX INFO: compiled from: BufferTransformer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\nJ\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u001e\u0010\u0012\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u001e\u0010\u0014\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\r¨\u0006\u001c"}, d2 = {"Landroidx/graphics/lowlatency/BufferTransformer;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mViewTransform", "", "transform", "getTransform", "()[F", "value", "", "logicalWidth", "getLogicalWidth", "()I", "logicalHeight", "getLogicalHeight", "bufferWidth", "getBufferWidth", "bufferHeight", "getBufferHeight", "computedTransform", "getComputedTransform", "invertBufferTransform", "computeTransform", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "transformHint", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BufferTransformer {
    private int bufferHeight;
    private int bufferWidth;
    private int logicalHeight;
    private int logicalWidth;
    private final float[] mViewTransform = new float[16];
    private int computedTransform = -1;

    public final int invertBufferTransform(int transform) {
        if (transform == 0) {
            return 0;
        }
        if (transform == 7) {
            return 4;
        }
        if (transform != 3) {
            return transform != 4 ? -1 : 7;
        }
        return 3;
    }

    /* JADX INFO: renamed from: getTransform, reason: from getter */
    public final float[] getMViewTransform() {
        return this.mViewTransform;
    }

    public final int getLogicalWidth() {
        return this.logicalWidth;
    }

    public final int getLogicalHeight() {
        return this.logicalHeight;
    }

    public final int getBufferWidth() {
        return this.bufferWidth;
    }

    public final int getBufferHeight() {
        return this.bufferHeight;
    }

    public final int getComputedTransform() {
        return this.computedTransform;
    }

    public final void computeTransform(int width, int height, int transformHint) {
        float f = width;
        float f2 = height;
        this.logicalWidth = width;
        this.logicalHeight = height;
        this.bufferWidth = width;
        this.bufferHeight = height;
        this.computedTransform = transformHint;
        if (transformHint == 0) {
            Matrix.setIdentityM(this.mViewTransform, 0);
            return;
        }
        if (transformHint == 7) {
            Matrix.setRotateM(this.mViewTransform, 0, 90.0f, 0.0f, 0.0f, 1.0f);
            Matrix.translateM(this.mViewTransform, 0, 0.0f, -f2, 0.0f);
            this.bufferWidth = height;
            this.bufferHeight = width;
            return;
        }
        if (transformHint == 3) {
            Matrix.setRotateM(this.mViewTransform, 0, 180.0f, 0.0f, 0.0f, 1.0f);
            Matrix.translateM(this.mViewTransform, 0, -f, -f2, 0.0f);
        } else {
            if (transformHint == 4) {
                Matrix.setRotateM(this.mViewTransform, 0, -90.0f, 0.0f, 0.0f, 1.0f);
                Matrix.translateM(this.mViewTransform, 0, -f, 0.0f, 0.0f);
                this.bufferWidth = height;
                this.bufferHeight = width;
                return;
            }
            this.computedTransform = -1;
            Matrix.setIdentityM(this.mViewTransform, 0);
        }
    }
}
