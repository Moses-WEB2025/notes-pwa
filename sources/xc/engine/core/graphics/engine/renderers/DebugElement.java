package xc.engine.core.graphics.engine.renderers;

import android.graphics.PointF;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import xc.engine.core.graphics.engine.core.Color;

/* JADX INFO: compiled from: DebugDrawRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010'\u001a\u00020\u0005J\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020\u0015J\u0016\u0010*\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\u001fJ\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\u0015J\u0016\u0010.\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\u001fJ\u000e\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001fJ\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u00020\u0011J\u000e\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u001fJ\u000e\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u001bJ\u001e\u00106\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001f2\u0006\u00108\u001a\u00020\u001f2\u0006\u00109\u001a\u00020\u001fJ\u000e\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020#R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0011@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0015@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0015@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u001e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\u001b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001e\u0010 \u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u001f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001e\u0010$\u001a\u00020#2\u0006\u0010\u0010\u001a\u00020#@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&¨\u0006<"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/DebugElement;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "bActive", "", "getBActive", "()Z", "setBActive", "(Z)V", "shape", "Lxc/engine/core/graphics/engine/renderers/EDebugShape;", "getShape", "()Lxc/engine/core/graphics/engine/renderers/EDebugShape;", "setShape", "(Lxc/engine/core/graphics/engine/renderers/EDebugShape;)V", "value", "Lxc/engine/core/graphics/engine/renderers/EDebugDrawType;", "drawType", "getDrawType", "()Lxc/engine/core/graphics/engine/renderers/EDebugDrawType;", "Landroid/graphics/PointF;", EditStat.ELEMENT_CENTER, "getCenter", "()Landroid/graphics/PointF;", "size", "getSize", "Lxc/engine/core/graphics/engine/core/Color;", "color", "getColor", "()Lxc/engine/core/graphics/engine/core/Color;", "", "outlineWidth", "getOutlineWidth", "()F", "", "textureId", "getTextureId", "()I", "isValid", "getRect", "Landroid/graphics/RectF;", "setCenter", "inCenter", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setSize", "inSize", "setRadius", Constants.RANDOM_LONG, "setDrawType", "inDrawType", "setOutlineWidth", "inOutlineWidth", "setColor", "inColor", CloudPushConstants.WATERMARK_TYPE.GLOBAL, "b", "setTexture", "id", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DebugElement {
    private boolean bActive;
    private int textureId;
    private EDebugShape shape = EDebugShape.Rect;
    private EDebugDrawType drawType = EDebugDrawType.Outline;
    private PointF center = new PointF(0.0f, 0.0f);
    private PointF size = new PointF(0.0f, 0.0f);
    private Color color = Color.INSTANCE.getRED();
    private float outlineWidth = 1.0f;

    /* JADX INFO: compiled from: DebugDrawRenderer.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[EDebugShape.values().length];
            try {
                iArr[EDebugShape.Rect.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EDebugShape.Cycle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[EDebugDrawType.values().length];
            try {
                iArr2[EDebugDrawType.Outline.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[EDebugDrawType.Fill.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public final boolean getBActive() {
        return this.bActive;
    }

    public final void setBActive(boolean z) {
        this.bActive = z;
    }

    public final EDebugShape getShape() {
        return this.shape;
    }

    public final void setShape(EDebugShape eDebugShape) {
        Intrinsics.checkNotNullParameter(eDebugShape, "<set-?>");
        this.shape = eDebugShape;
    }

    public final EDebugDrawType getDrawType() {
        return this.drawType;
    }

    public final PointF getCenter() {
        return this.center;
    }

    public final PointF getSize() {
        return this.size;
    }

    public final Color getColor() {
        return this.color;
    }

    public final float getOutlineWidth() {
        return this.outlineWidth;
    }

    public final int getTextureId() {
        return this.textureId;
    }

    public final boolean isValid() {
        if (!this.bActive) {
            return false;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.shape.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (this.size.x <= 0.0f) {
                return false;
            }
        } else if (this.size.x <= 0.0f || this.size.y <= 0.0f) {
            return false;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$1[this.drawType.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (this.outlineWidth <= 0.0f) {
            return false;
        }
        return true;
    }

    public final RectF getRect() {
        float f = this.size.x * 0.5f;
        float f2 = this.size.y * 0.5f;
        return new RectF(this.center.x - f, this.center.y - f2, this.center.x + f, this.center.y + f2);
    }

    public final DebugElement setCenter(PointF inCenter) {
        Intrinsics.checkNotNullParameter(inCenter, "inCenter");
        this.center = inCenter;
        return this;
    }

    public final DebugElement setCenter(float x, float y) {
        this.center = new PointF(x, y);
        return this;
    }

    public final DebugElement setSize(PointF inSize) {
        Intrinsics.checkNotNullParameter(inSize, "inSize");
        this.size = inSize;
        return this;
    }

    public final DebugElement setSize(float x, float y) {
        this.size = new PointF(x, y);
        return this;
    }

    public final DebugElement setRadius(float r) {
        float f = r * 2.0f;
        this.size = new PointF(f, f);
        return this;
    }

    public final DebugElement setDrawType(EDebugDrawType inDrawType) {
        Intrinsics.checkNotNullParameter(inDrawType, "inDrawType");
        this.drawType = inDrawType;
        return this;
    }

    public final DebugElement setOutlineWidth(float inOutlineWidth) {
        this.outlineWidth = inOutlineWidth;
        return this;
    }

    public final DebugElement setColor(Color inColor) {
        Intrinsics.checkNotNullParameter(inColor, "inColor");
        this.color = inColor;
        return this;
    }

    public final DebugElement setColor(float r, float g, float b) {
        this.color = new Color(r, g, b, 1.0f);
        return this;
    }

    public final DebugElement setTexture(int id) {
        this.textureId = id;
        return this;
    }
}
