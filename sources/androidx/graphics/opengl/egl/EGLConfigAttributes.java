package androidx.graphics.opengl.egl;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: EGLConfigAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0015\n\u0002\b\n\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\n\u001a\u00020\u0003R\u001c\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "", "attrs", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([I)V", "getAttrs$annotations", "()V", "getAttrs", "()[I", "toArray", "Builder", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EGLConfigAttributes {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int EGL_COLOR_COMPONENT_TYPE_EXT = 13113;
    public static final int EGL_COLOR_COMPONENT_TYPE_FIXED_EXT = 13114;
    public static final int EGL_COLOR_COMPONENT_TYPE_FLOAT_EXT = 13115;
    public static final EGLConfigAttributes RGBA_1010102;
    public static final EGLConfigAttributes RGBA_8888;
    public static final EGLConfigAttributes RGBA_F16;
    private final int[] attrs;

    public static /* synthetic */ void getAttrs$annotations() {
    }

    public EGLConfigAttributes(int[] attrs) {
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.attrs = attrs;
    }

    public final int[] getAttrs() {
        return this.attrs;
    }

    public final int[] toArray() {
        return (int[]) this.attrs.clone();
    }

    /* JADX INFO: compiled from: EGLConfigAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\b\u001a\u00020\t*\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0086\u0004J\u0016\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010R*\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/graphics/opengl/egl/EGLConfigAttributes$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "attrs", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "to", "", "that", "setAttribute", "attribute", "value", "include", "attributes", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "build", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private final HashMap<Integer, Integer> attrs = new HashMap<>();

        /* JADX INFO: renamed from: setAttribute, reason: merged with bridge method [inline-methods] */
        public final Builder to(int attribute, int value) {
            this.attrs.put(Integer.valueOf(attribute), Integer.valueOf(value));
            return this;
        }

        public final void include(EGLConfigAttributes attributes) {
            Intrinsics.checkNotNullParameter(attributes, "attributes");
            int[] attrs = attributes.getAttrs();
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, attrs.length - 1), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step <= 0 || first > last) && (step >= 0 || last > first)) {
                return;
            }
            while (true) {
                this.attrs.put(Integer.valueOf(attrs[first]), Integer.valueOf(attrs[first + 1]));
                if (first == last) {
                    return;
                } else {
                    first += step;
                }
            }
        }

        public final EGLConfigAttributes build() {
            Set<Map.Entry<Integer, Integer>> setEntrySet = this.attrs.entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            int[] iArr = new int[(setEntrySet.size() * 2) + 1];
            int i = 0;
            for (Map.Entry<Integer, Integer> entry : setEntrySet) {
                Intrinsics.checkNotNullExpressionValue(entry, "next(...)");
                Map.Entry<Integer, Integer> entry2 = entry;
                Integer key = entry2.getKey();
                Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
                iArr[i] = key.intValue();
                Integer value = entry2.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
                iArr[i + 1] = value.intValue();
                i += 2;
            }
            iArr[i] = 12344;
            return new EGLConfigAttributes(iArr);
        }
    }

    /* JADX INFO: compiled from: EGLConfigAttributes.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0003R\u0016\u0010\u000b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\u0003R\u0016\u0010\r\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u0003¨\u0006\u000f"}, d2 = {"Landroidx/graphics/opengl/egl/EGLConfigAttributes$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "EGL_COLOR_COMPONENT_TYPE_EXT", "", "EGL_COLOR_COMPONENT_TYPE_FIXED_EXT", "EGL_COLOR_COMPONENT_TYPE_FLOAT_EXT", "RGBA_8888", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "getRGBA_8888$annotations", "RGBA_1010102", "getRGBA_1010102$annotations", "RGBA_F16", "getRGBA_F16$annotations", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getRGBA_1010102$annotations() {
        }

        public static /* synthetic */ void getRGBA_8888$annotations() {
        }

        public static /* synthetic */ void getRGBA_F16$annotations() {
        }

        private Companion() {
        }
    }

    static {
        Builder builder = new Builder();
        builder.to(R2.id.TOP_START, 4);
        builder.to(R2.drawable.v8_theme_white_share_bg, 8);
        builder.to(R2.drawable.v8_theme_star_grid_fg, 8);
        builder.to(R2.drawable.v8_theme_star_bg_top, 8);
        builder.to(R2.drawable.v8_theme_star_bg_bottom, 8);
        builder.to(R2.drawable.v8_theme_white_share_bg_1, 0);
        builder.to(R2.drawable.vertical_line, R2.id.META);
        builder.to(R2.drawable.v8_theme_white_share_bg_2, 0);
        builder.to(R2.id.ALT, 4);
        RGBA_8888 = builder.build();
        Builder builder2 = new Builder();
        builder2.to(R2.id.TOP_START, 4);
        builder2.to(R2.drawable.v8_theme_white_share_bg, 10);
        builder2.to(R2.drawable.v8_theme_star_grid_fg, 10);
        builder2.to(R2.drawable.v8_theme_star_bg_top, 10);
        builder2.to(R2.drawable.v8_theme_star_bg_bottom, 2);
        builder2.to(R2.drawable.v8_theme_white_share_bg_1, 0);
        builder2.to(R2.drawable.v8_theme_white_share_bg_2, 0);
        builder2.to(R2.id.ALT, 4);
        RGBA_1010102 = builder2.build();
        Builder builder3 = new Builder();
        builder3.to(R2.id.TOP_START, 4);
        builder3.to(13113, 13115);
        builder3.to(R2.drawable.v8_theme_white_share_bg, 16);
        builder3.to(R2.drawable.v8_theme_star_grid_fg, 16);
        builder3.to(R2.drawable.v8_theme_star_bg_top, 16);
        builder3.to(R2.drawable.v8_theme_star_bg_bottom, 16);
        builder3.to(R2.drawable.v8_theme_white_share_bg_1, 0);
        builder3.to(R2.drawable.v8_theme_white_share_bg_2, 0);
        builder3.to(R2.id.ALT, 4);
        RGBA_F16 = builder3.build();
    }
}
