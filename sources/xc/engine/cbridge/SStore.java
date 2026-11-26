package xc.engine.cbridge;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;

/* JADX INFO: compiled from: SStore.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b$\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u0005H\u0087 J\u0013\u0010,\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u0005H\u0087 J\t\u0010-\u001a\u00020*H\u0086 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lxc/engine/cbridge/SStore;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "RENDER_VF_LINE", "", "RENDER_V_TILE_WITH_FIX_ANGLE", "RENDER_V_TILE_WITHOUT_FIX_ANGLE", "RENDER_F_TILE", "RENDER_VF_BRUSH", "RENDER_VF_PENCIL", "RENDER_VF_BALL", "RENDER_VF_INK", "RENDER_VF_MARK", "RENDER_VF_WATERCOLOR", "RENDER_F_WATERCOLOR_BLEND", "RENDER_V_QUAD_FULL_WITH_FIX_ANGLE", "RENDER_V_QUAD_FULL", "RENDER_V_QUAD_FULL_WITH_MATRIX", "RENDER_V_QUAD_FREE", "RENDER_F_ALPHA_BLEND", "RENDER_F_BG_STYLE", "RENDER_F_BG_TILE", "RENDER_F_SHAPE_CIRCLE", "RENDER_F_SHAPE_RECT", "RENDER_F_SHAPE_TRIANGLE", "RENDER_F_SHAPE_STAR", "RENDER_F_SHAPE_STAR_NEW", "RENDER_F_SHAPE_BIG_ARROW", "RENDER_F_SHAPE_ARROW", "RENDER_F_SHAPE_ROUNDED_LINE", "RENDER_F_DEBUG_SHAPE", "RENDER_VF_IMAGE", "RENDER_VF_IMAGE_BATCH", "RENDER_F_IMAGE_CROP", "RENDER_VF_DASHED_LINE", "RENDER_VF_ANCHOR_BOX", "RENDER_F_BLEND", "RENDER_F_PAGE_MASK", "RENDER_F_SWAP_CHANNEL", "RENDER_VF_EFFECT_SCAN", "s1", "", "type", "s2", "nGetDisplayOrientation", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SStore {
    public static final SStore INSTANCE = new SStore();
    public static final int RENDER_F_ALPHA_BLEND = 210;
    public static final int RENDER_F_BG_STYLE = 211;
    public static final int RENDER_F_BG_TILE = 212;
    public static final int RENDER_F_BLEND = 320;
    public static final int RENDER_F_DEBUG_SHAPE = 3070;
    public static final int RENDER_F_IMAGE_CROP = 3102;
    public static final int RENDER_F_PAGE_MASK = 321;
    public static final int RENDER_F_SHAPE_ARROW = 306;
    public static final int RENDER_F_SHAPE_BIG_ARROW = 305;
    public static final int RENDER_F_SHAPE_CIRCLE = 301;
    public static final int RENDER_F_SHAPE_RECT = 302;
    public static final int RENDER_F_SHAPE_ROUNDED_LINE = 307;
    public static final int RENDER_F_SHAPE_STAR = 304;
    public static final int RENDER_F_SHAPE_STAR_NEW = 3041;
    public static final int RENDER_F_SHAPE_TRIANGLE = 303;
    public static final int RENDER_F_SWAP_CHANNEL = 322;
    public static final int RENDER_F_TILE = 202;
    public static final int RENDER_F_WATERCOLOR_BLEND = 208;
    public static final int RENDER_VF_ANCHOR_BOX = 312;
    public static final int RENDER_VF_BALL = 204;
    public static final int RENDER_VF_BRUSH = 2029;
    public static final int RENDER_VF_DASHED_LINE = 311;
    public static final int RENDER_VF_EFFECT_SCAN = 330;
    public static final int RENDER_VF_IMAGE = 310;
    public static final int RENDER_VF_IMAGE_BATCH = 3100;
    public static final int RENDER_VF_INK = 205;
    public static final int RENDER_VF_LINE = 201;
    public static final int RENDER_VF_MARK = 206;
    public static final int RENDER_VF_PENCIL = 203;
    public static final int RENDER_VF_WATERCOLOR = 207;
    public static final int RENDER_V_QUAD_FREE = 211;
    public static final int RENDER_V_QUAD_FULL = 2100;
    public static final int RENDER_V_QUAD_FULL_WITH_FIX_ANGLE = 210;
    public static final int RENDER_V_QUAD_FULL_WITH_MATRIX = 2101;
    public static final int RENDER_V_TILE_WITHOUT_FIX_ANGLE = 2021;
    public static final int RENDER_V_TILE_WITH_FIX_ANGLE = 202;

    @JvmStatic
    public static final native String s1(int type);

    @JvmStatic
    public static final native String s2(int type);

    public final native String nGetDisplayOrientation();

    private SStore() {
    }

    static {
        System.loadLibrary("xnative");
    }
}
