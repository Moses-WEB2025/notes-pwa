package xc.engine.core.graphics.engine.core;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: Color.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bB\t\b\u0016¢\u0006\u0004\b\u0007\u0010\tB\u0011\b\u0016\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0007\u0010\fJ\u0006\u0010\u0017\u001a\u00020\u000bJ\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010¨\u0006\u001f"}, d2 = {"Lxc/engine/core/graphics/engine/core/Color;", "", Constants.RANDOM_LONG, "", CloudPushConstants.WATERMARK_TYPE.GLOBAL, "b", HtmlParser.LinkCardElement.TAG, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FFFF)V", "()V", "color", "", "(I)V", "getR", "()F", "setR", "(F)V", "getG", "setG", "getB", "setB", "getA", "setA", "getIntValue", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Color {
    private float a;
    private float b;
    private float g;
    private float r;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    private static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    private static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    private static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    private static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    private static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    public Color(float f, float f2, float f3, float f4) {
        this.r = f;
        this.g = f2;
        this.b = f3;
        this.a = f4;
    }

    public final float getR() {
        return this.r;
    }

    public final void setR(float f) {
        this.r = f;
    }

    public final float getG() {
        return this.g;
    }

    public final void setG(float f) {
        this.g = f;
    }

    public final float getB() {
        return this.b;
    }

    public final void setB(float f) {
        this.b = f;
    }

    public final float getA() {
        return this.a;
    }

    public final void setA(float f) {
        this.a = f;
    }

    /* JADX INFO: compiled from: Color.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0011\u0010\u0012\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007¨\u0006\u0014"}, d2 = {"Lxc/engine/core/graphics/engine/core/Color$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "WHITE", "Lxc/engine/core/graphics/engine/core/Color;", "getWHITE", "()Lxc/engine/core/graphics/engine/core/Color;", "BLACK", "getBLACK", "RED", "getRED", "GREEN", "getGREEN", "BLUE", "getBLUE", "YELLOW", "getYELLOW", "TRANSPARENT", "getTRANSPARENT", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Color getWHITE() {
            return Color.WHITE;
        }

        public final Color getBLACK() {
            return Color.BLACK;
        }

        public final Color getRED() {
            return Color.RED;
        }

        public final Color getGREEN() {
            return Color.GREEN;
        }

        public final Color getBLUE() {
            return Color.BLUE;
        }

        public final Color getYELLOW() {
            return Color.YELLOW;
        }

        public final Color getTRANSPARENT() {
            return Color.TRANSPARENT;
        }
    }

    public Color() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Color(int i) {
        this(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f);
    }

    public final int getIntValue() {
        return (((int) (this.a * 255.0f)) << 24) + (((int) (this.r * 255.0f)) << 16) + (((int) (this.g * 255.0f)) << 8) + ((int) (this.b * 255.0f));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Color)) {
            return false;
        }
        Color color = (Color) other;
        return this.r == color.r && this.g == color.g && this.b == color.b && this.a == color.a;
    }

    public int hashCode() {
        return (((((Float.hashCode(this.r) * 31) + Float.hashCode(this.g)) * 31) + Float.hashCode(this.b)) * 31) + Float.hashCode(this.a);
    }

    public String toString() {
        return "Color [" + this.r + ", " + this.g + ", " + this.b + ", " + this.a + "]";
    }
}
