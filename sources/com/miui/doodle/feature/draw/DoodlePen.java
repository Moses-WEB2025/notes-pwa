package com.miui.doodle.feature.draw;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Arrays;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodlePen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b*\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0086\b\u0018\u0000 ]2\u00020\u0001:\u0001]B¡\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\f\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\f\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015¢\u0006\u0004\b\u0016\u0010\u0017J\u0006\u0010C\u001a\u00020\u0003J\u0006\u0010H\u001a\u00020\u0000J\u0013\u0010I\u001a\u00020\f2\b\u0010J\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010K\u001a\u00020\u0007H\u0016J\t\u0010L\u001a\u00020\u0003HÆ\u0003J\t\u0010M\u001a\u00020\u0005HÆ\u0003J\t\u0010N\u001a\u00020\u0007HÆ\u0003J\t\u0010O\u001a\u00020\u0007HÆ\u0003J\t\u0010P\u001a\u00020\u0005HÆ\u0003J\t\u0010Q\u001a\u00020\u0005HÆ\u0003J\t\u0010R\u001a\u00020\fHÆ\u0003J\t\u0010S\u001a\u00020\u0005HÆ\u0003J\t\u0010T\u001a\u00020\u0005HÆ\u0003J\t\u0010U\u001a\u00020\u0005HÆ\u0003J\t\u0010V\u001a\u00020\u0005HÆ\u0003J\t\u0010W\u001a\u00020\fHÆ\u0003J\t\u0010X\u001a\u00020\u0005HÆ\u0003J\t\u0010Y\u001a\u00020\fHÆ\u0003J\u0014\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015HÆ\u0003¢\u0006\u0002\u0010;Jª\u0001\u0010H\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\f2\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015HÆ\u0001¢\u0006\u0002\u0010[J\t\u0010\\\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001f\"\u0004\b#\u0010!R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001b\"\u0004\b%\u0010\u001dR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001b\"\u0004\b'\u0010\u001dR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010\u001dR\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001b\"\u0004\b/\u0010\u001dR\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001b\"\u0004\b1\u0010\u001dR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u001b\"\u0004\b3\u0010\u001dR\u001a\u0010\u0011\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010)\"\u0004\b5\u0010+R\u001a\u0010\u0012\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u001b\"\u0004\b7\u0010\u001dR\u001a\u0010\u0013\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010)\"\u0004\b9\u0010+R\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015X\u0086\u000e¢\u0006\u0010\n\u0002\u0010>\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u000e\u0010?\u001a\u00020@X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010A\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\bB\u0010\u001bR\u0011\u0010D\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bD\u0010)R\u0011\u0010E\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bE\u0010)R\u0011\u0010F\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bF\u0010)R\u0011\u0010G\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bG\u0010)¨\u0006^"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodlePen;", "", "type", "", "alpha", "", "colorInt", "", "strokeLevel", "angle", "angleJitter", "useFlowingAngle", "", "size", "spacing", "colorPatchAlpha", "spread", "airBrushMode", "lineEndSpeedLength", "useSimpleTexture", "maskBitmapIds", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;FIIFFZFFFFZFZ[Ljava/lang/String;)V", "getType", "()Ljava/lang/String;", "getAlpha", "()F", "setAlpha", "(F)V", "getColorInt", "()I", "setColorInt", "(I)V", "getStrokeLevel", "setStrokeLevel", "getAngle", "setAngle", "getAngleJitter", "setAngleJitter", "getUseFlowingAngle", "()Z", "setUseFlowingAngle", "(Z)V", "getSize", "setSize", "getSpacing", "setSpacing", "getColorPatchAlpha", "setColorPatchAlpha", "getSpread", "setSpread", "getAirBrushMode", "setAirBrushMode", "getLineEndSpeedLength", "setLineEndSpeedLength", "getUseSimpleTexture", "setUseSimpleTexture", "getMaskBitmapIds", "()[Ljava/lang/String;", "setMaskBitmapIds", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "random", "Ljava/util/Random;", "finalSize", "getFinalSize", "nextMaskId", "isEraser", "isElementEraser", "isPenEditable", "isBrushPen", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "(Ljava/lang/String;FIIFFZFFFFZFZ[Ljava/lang/String;)Lcom/miui/doodle/feature/draw/DoodlePen;", "toString", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class DoodlePen {
    public static final String TYPE_BITMAP = "Bitmap_01";
    public static final String TYPE_BRUSH = "Brush_01";
    public static final String TYPE_ELEMENT_ERASER = "Eraser_02";
    public static final String TYPE_ERASER = "Eraser_01";
    public static final String TYPE_MARK_PEN = "MarkPen_01";
    public static final String TYPE_NORMAL = "Normal_01";
    public static final String TYPE_PENCIL = "Pencil_01";
    public static final String TYPE_TEXT = "Text_01";
    public static final String TYPE_WATERCOLOR = "WaterColor_01";
    private boolean airBrushMode;
    private float alpha;
    private float angle;
    private float angleJitter;
    private int colorInt;
    private float colorPatchAlpha;
    private float lineEndSpeedLength;
    private String[] maskBitmapIds;
    private Random random;
    private float size;
    private float spacing;
    private float spread;
    private int strokeLevel;
    private final String type;
    private boolean useFlowingAngle;
    private boolean useSimpleTexture;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final float getColorPatchAlpha() {
        return this.colorPatchAlpha;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final float getSpread() {
        return this.spread;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final boolean getAirBrushMode() {
        return this.airBrushMode;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final float getLineEndSpeedLength() {
        return this.lineEndSpeedLength;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final boolean getUseSimpleTexture() {
        return this.useSimpleTexture;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final String[] getMaskBitmapIds() {
        return this.maskBitmapIds;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getAlpha() {
        return this.alpha;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getColorInt() {
        return this.colorInt;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getStrokeLevel() {
        return this.strokeLevel;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final float getAngle() {
        return this.angle;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final float getAngleJitter() {
        return this.angleJitter;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getUseFlowingAngle() {
        return this.useFlowingAngle;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final float getSize() {
        return this.size;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final float getSpacing() {
        return this.spacing;
    }

    public final DoodlePen copy(String type, float alpha, int colorInt, int strokeLevel, float angle, float angleJitter, boolean useFlowingAngle, float size, float spacing, float colorPatchAlpha, float spread, boolean airBrushMode, float lineEndSpeedLength, boolean useSimpleTexture, String[] maskBitmapIds) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(maskBitmapIds, "maskBitmapIds");
        return new DoodlePen(type, alpha, colorInt, strokeLevel, angle, angleJitter, useFlowingAngle, size, spacing, colorPatchAlpha, spread, airBrushMode, lineEndSpeedLength, useSimpleTexture, maskBitmapIds);
    }

    public String toString() {
        return "DoodlePen(type=" + this.type + ", alpha=" + this.alpha + ", colorInt=" + this.colorInt + ", strokeLevel=" + this.strokeLevel + ", angle=" + this.angle + ", angleJitter=" + this.angleJitter + ", useFlowingAngle=" + this.useFlowingAngle + ", size=" + this.size + ", spacing=" + this.spacing + ", colorPatchAlpha=" + this.colorPatchAlpha + ", spread=" + this.spread + ", airBrushMode=" + this.airBrushMode + ", lineEndSpeedLength=" + this.lineEndSpeedLength + ", useSimpleTexture=" + this.useSimpleTexture + ", maskBitmapIds=" + Arrays.toString(this.maskBitmapIds) + ")";
    }

    public DoodlePen(String type, float f, int i, int i2, float f2, float f3, boolean z, float f4, float f5, float f6, float f7, boolean z2, float f8, boolean z3, String[] maskBitmapIds) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(maskBitmapIds, "maskBitmapIds");
        this.type = type;
        this.alpha = f;
        this.colorInt = i;
        this.strokeLevel = i2;
        this.angle = f2;
        this.angleJitter = f3;
        this.useFlowingAngle = z;
        this.size = f4;
        this.spacing = f5;
        this.colorPatchAlpha = f6;
        this.spread = f7;
        this.airBrushMode = z2;
        this.lineEndSpeedLength = f8;
        this.useSimpleTexture = z3;
        this.maskBitmapIds = maskBitmapIds;
        this.random = new Random(System.currentTimeMillis());
    }

    public /* synthetic */ DoodlePen(String str, float f, int i, int i2, float f2, float f3, boolean z, float f4, float f5, float f6, float f7, boolean z2, float f8, boolean z3, String[] strArr, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? 1.0f : f, (i3 & 4) != 0 ? -16777216 : i, (i3 & 8) != 0 ? 1 : i2, (i3 & 16) != 0 ? 0.0f : f2, (i3 & 32) != 0 ? 0.0f : f3, (i3 & 64) != 0 ? false : z, (i3 & 128) != 0 ? 0.0f : f4, (i3 & 256) != 0 ? 0.0f : f5, (i3 & 512) != 0 ? 0.0f : f6, (i3 & 1024) != 0 ? 0.0f : f7, (i3 & 2048) != 0 ? false : z2, (i3 & 4096) == 0 ? f8 : 0.0f, (i3 & 8192) != 0 ? false : z3, (i3 & 16384) != 0 ? new String[0] : strArr);
    }

    public final String getType() {
        return this.type;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final int getColorInt() {
        return this.colorInt;
    }

    public final void setColorInt(int i) {
        this.colorInt = i;
    }

    public final int getStrokeLevel() {
        return this.strokeLevel;
    }

    public final void setStrokeLevel(int i) {
        this.strokeLevel = i;
    }

    public final float getAngle() {
        return this.angle;
    }

    public final void setAngle(float f) {
        this.angle = f;
    }

    public final float getAngleJitter() {
        return this.angleJitter;
    }

    public final void setAngleJitter(float f) {
        this.angleJitter = f;
    }

    public final boolean getUseFlowingAngle() {
        return this.useFlowingAngle;
    }

    public final void setUseFlowingAngle(boolean z) {
        this.useFlowingAngle = z;
    }

    public final float getSize() {
        return this.size;
    }

    public final void setSize(float f) {
        this.size = f;
    }

    public final float getSpacing() {
        return this.spacing;
    }

    public final void setSpacing(float f) {
        this.spacing = f;
    }

    public final float getColorPatchAlpha() {
        return this.colorPatchAlpha;
    }

    public final void setColorPatchAlpha(float f) {
        this.colorPatchAlpha = f;
    }

    public final float getSpread() {
        return this.spread;
    }

    public final void setSpread(float f) {
        this.spread = f;
    }

    public final boolean getAirBrushMode() {
        return this.airBrushMode;
    }

    public final void setAirBrushMode(boolean z) {
        this.airBrushMode = z;
    }

    public final float getLineEndSpeedLength() {
        return this.lineEndSpeedLength;
    }

    public final void setLineEndSpeedLength(float f) {
        this.lineEndSpeedLength = f;
    }

    public final boolean getUseSimpleTexture() {
        return this.useSimpleTexture;
    }

    public final void setUseSimpleTexture(boolean z) {
        this.useSimpleTexture = z;
    }

    public final String[] getMaskBitmapIds() {
        return this.maskBitmapIds;
    }

    public final void setMaskBitmapIds(String[] strArr) {
        Intrinsics.checkNotNullParameter(strArr, "<set-?>");
        this.maskBitmapIds = strArr;
    }

    public final float getFinalSize() {
        return this.strokeLevel * this.size;
    }

    public final String nextMaskId() {
        String[] strArr = this.maskBitmapIds;
        if (strArr.length == 0) {
            return "";
        }
        if (strArr.length == 1) {
            return strArr[0];
        }
        return strArr[this.random.nextInt(strArr.length)];
    }

    public final boolean isEraser() {
        return Intrinsics.areEqual(this.type, TYPE_ERASER);
    }

    public final boolean isElementEraser() {
        return Intrinsics.areEqual(this.type, TYPE_ELEMENT_ERASER);
    }

    public final boolean isPenEditable() {
        return Intrinsics.areEqual(this.type, TYPE_TEXT) || Intrinsics.areEqual(this.type, TYPE_BITMAP);
    }

    public final boolean isBrushPen() {
        return Intrinsics.areEqual(this.type, TYPE_PENCIL) || Intrinsics.areEqual(this.type, TYPE_NORMAL) || Intrinsics.areEqual(this.type, TYPE_MARK_PEN) || Intrinsics.areEqual(this.type, TYPE_WATERCOLOR);
    }

    public final DoodlePen copy() {
        return new DoodlePen(this.type, this.alpha, this.colorInt, this.strokeLevel, this.angle, this.angleJitter, this.useFlowingAngle, this.size, this.spacing, this.colorPatchAlpha, this.spread, this.airBrushMode, this.lineEndSpeedLength, this.useSimpleTexture, this.maskBitmapIds);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodlePen");
        DoodlePen doodlePen = (DoodlePen) other;
        return Intrinsics.areEqual(this.type, doodlePen.type) && this.alpha == doodlePen.alpha && this.colorInt == doodlePen.colorInt && this.strokeLevel == doodlePen.strokeLevel && this.angle == doodlePen.angle && this.angleJitter == doodlePen.angleJitter && this.useFlowingAngle == doodlePen.useFlowingAngle && this.size == doodlePen.size && this.spacing == doodlePen.spacing && this.colorPatchAlpha == doodlePen.colorPatchAlpha && this.spread == doodlePen.spread && this.airBrushMode == doodlePen.airBrushMode && this.lineEndSpeedLength == doodlePen.lineEndSpeedLength && this.useSimpleTexture == doodlePen.useSimpleTexture && Arrays.equals(this.maskBitmapIds, doodlePen.maskBitmapIds);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((this.type.hashCode() * 31) + Float.hashCode(this.alpha)) * 31) + this.colorInt) * 31) + this.strokeLevel) * 31) + Float.hashCode(this.angle)) * 31) + Float.hashCode(this.angleJitter)) * 31) + Boolean.hashCode(this.useFlowingAngle)) * 31) + Float.hashCode(this.size)) * 31) + Float.hashCode(this.spacing)) * 31) + Float.hashCode(this.colorPatchAlpha)) * 31) + Float.hashCode(this.spread)) * 31) + Boolean.hashCode(this.airBrushMode)) * 31) + Float.hashCode(this.lineEndSpeedLength)) * 31) + Boolean.hashCode(this.useSimpleTexture)) * 31) + Arrays.hashCode(this.maskBitmapIds);
    }
}
