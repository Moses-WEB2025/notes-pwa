package com.miui.handwriting.common.view.quickMenu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.core.content.res.ResourcesCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.quickMenu.base.PenSizeInfo;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import miuix.internal.util.AttributeResolver;
import xc.engine.core.graphics.pen.PenType;

/* JADX INFO: compiled from: PenMenuRes.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0018\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0012J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u0015J\u0006\u0010\u001f\u001a\u00020\u000fJ\u0006\u0010 \u001a\u00020\u000fJ\u0006\u0010!\u001a\u00020\u000fJ\u0006\u0010\"\u001a\u00020\u000fJ\u0006\u0010#\u001a\u00020\u000fJ\u0006\u0010$\u001a\u00020\u000fJ\u0006\u0010%\u001a\u00020\u000fJ\u0006\u0010&\u001a\u00020\u000fJ\u0006\u0010'\u001a\u00020\u000fJ\u0006\u0010(\u001a\u00020\u000fJ\u0006\u0010)\u001a\u00020\u000fJ\u0006\u0010*\u001a\u00020\u000fJ\u0006\u0010+\u001a\u00020\u000fJ\u0006\u0010,\u001a\u00020\u000fJ\u0006\u0010-\u001a\u00020\u000fJ\u0006\u0010.\u001a\u00020\u000fJ\u0006\u0010/\u001a\u00020\u000fJ\u0006\u00100\u001a\u00020\u000fJ\u0006\u00101\u001a\u00020\u000fJ\u0006\u00102\u001a\u00020\u000fJ\b\u00103\u001a\u0004\u0018\u00010\u0015J\u0006\u00104\u001a\u00020\u000fJ\u0006\u00105\u001a\u00020\u000fJ\u0006\u00106\u001a\u00020\u000fJ\u0006\u00107\u001a\u00020\u0012J\u0006\u00108\u001a\u00020\u0012J\u0006\u00109\u001a\u00020\u0012J\u0006\u0010:\u001a\u00020\u0012J\u0006\u0010;\u001a\u00020\u000fJ\u0006\u0010<\u001a\u00020\u000fJ\u0006\u0010=\u001a\u00020\u0012J\u0006\u0010>\u001a\u00020\u0012J\u000e\u0010?\u001a\u00020\t2\u0006\u0010@\u001a\u00020\bJ\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120BJ\u0006\u0010C\u001a\u00020\u000fJ\u0006\u0010D\u001a\u00020\u000fJ\u0006\u0010E\u001a\u00020\u000fJ\u0006\u0010F\u001a\u00020\u000fJ\u0006\u0010G\u001a\u00020\u000fJ\u0006\u0010H\u001a\u00020\u000fJ\u0006\u0010I\u001a\u00020\u000fJ\u0006\u0010J\u001a\u00020\u000fJ\u0006\u0010K\u001a\u00020\u000fJ\u0006\u0010L\u001a\u00020\u0012J\u0006\u0010M\u001a\u00020\u0012J\u0006\u0010N\u001a\u00020\u000fJ\u0006\u0010O\u001a\u00020\u000fJ\u0006\u0010P\u001a\u00020\u000fJ\u0006\u0010Q\u001a\u00020\u0012J\u0006\u0010R\u001a\u00020\u0012J\u0006\u0010S\u001a\u00020\u000fJ\u0006\u0010T\u001a\u00020\u000fJ\u0006\u0010U\u001a\u00020\u0017J\u0006\u0010V\u001a\u00020\u0017J\u0006\u0010W\u001a\u00020\u0017J\u000e\u0010X\u001a\u00020\u00172\u0006\u0010Y\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "penSizeMap", "", "Lxc/engine/core/graphics/pen/PenType;", "Lcom/miui/handwriting/common/view/quickMenu/base/PenSizeInfo;", "brushResourceMap", "Lcom/miui/handwriting/common/view/quickMenu/utils/BrushResource;", "mScreenModeHelper", "Lcom/miui/handwriting/common/view/quickMenu/utils/ScreenModeHelper;", "scaleRatio", "", "getDimension", "resId", "", "getColor", "getDrawable", "Landroid/graphics/drawable/Drawable;", "getString", "", "getBrushDrawable", "brush", "color", "getEraserIcon", "isSelected", "", "getEraserDrawable", "getOuterCircleRadius", "getInnerCircleRadius", "getBrushOuterCircleRadius", "getBrushInnerCircleRadius", "getMenuGapSize", "getPencilMenuStartAngle", "getPencilMenuEndAngle", "getColorMenuStartAngle", "getColorMenuEndAngle", "getEraserMenuStartAngle", "getEraserMenuEndAngle", "getBrushAngle", "getEraserAngle", "getEraserIconAngle", "getPencilMenuSizeSeekbarStartAngle", "getPencilMenuSizeSeekbarEndAngle", "getPencilMenuAlphaSeekbarStartAngle", "getEraserMenuSizeSeekbarStartAngle", "getEraserMenuSizeSeekbarEndAngle", "getPencilMenuAlphaSeekbarEndAngle", "getAlphaBackground", "getSliderStrokeBold", "getSliderStrokeThin", "getSliderStrokeMiddle", "getSliderRadius", "getPencilSizeSeekbarBackgroundColor", "getSizeSliderStrokeColor", "getAlphaSliderStrokeColor", "getAlphaSeekbarInnerStrokeSize", "getSizeSeekbarInnerStrokeSize", "getAlphaSeekbarInnerStrokeColor", "getAlphaSeekbarGradientStartColor", "getPenSizeInfoByType", "type", "getAlphaRange", "Lkotlin/Pair;", "getBrushPreviewBitmapWidth", "getBrushPreviewBitmapMinHeight", "getBrushPreviewBitmapMaxHeight", "getMenuLimitTop", "getMenuLimitLRB", "getWrapperExtendSize", "getEraserIconSize", "getUniformCircleDiameter", "getEraserDrawTextSize", "getEraserSwitchButtonSelectedColor", "getEraserSwitchButtonUnselectedColor", "getFirstColorSelectButtonAngle", "getSecondColorSelectButtonAngle", "getThirdColorSelectButtonAngle", "getMenuBackgroundColor", "getMenuShadowColor", "getColorMenuHorizontalGap", "getColorPopupWindowGap", "getPenThicknessText", "getPenAlphaText", "getEraserSizeText", "getBrushMenuAccessibilityContentDescription", "penType", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PenMenuRes {
    private Map<PenType, BrushResource> brushResourceMap;
    private final Context context;
    private final ScreenModeHelper mScreenModeHelper;
    private final Map<PenType, PenSizeInfo> penSizeMap;
    private float scaleRatio;

    /* JADX INFO: compiled from: PenMenuRes.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PenType.values().length];
            try {
                iArr[PenType.INK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PenType.MARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PenType.BALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PenType.PENCIL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PenType.WATER_COLOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public PenMenuRes(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.penSizeMap = linkedHashMap;
        this.brushResourceMap = new LinkedHashMap();
        ScreenModeHelper screenModeHelper = new ScreenModeHelper(context);
        this.mScreenModeHelper = screenModeHelper;
        this.scaleRatio = screenModeHelper.transformScale(screenModeHelper.getQuickMenuScale());
        linkedHashMap.put(PenType.INK, new PenSizeInfo(1, 30));
        linkedHashMap.put(PenType.BALL, new PenSizeInfo(1, 22));
        linkedHashMap.put(PenType.PENCIL, new PenSizeInfo(1, 6));
        linkedHashMap.put(PenType.MARK, new PenSizeInfo(5, 65));
        linkedHashMap.put(PenType.WATER_COLOR, new PenSizeInfo(5, 65));
        this.brushResourceMap.put(PenType.INK, new BrushResource(R.drawable.quick_menu_brush_ink_head, R.drawable.quick_menu_brush_ink_body, R.drawable.quick_menu_brush_ink_shadow_s));
        this.brushResourceMap.put(PenType.BALL, new BrushResource(R.drawable.quick_menu_brush_ballpoint_head, R.drawable.quick_menu_brush_ballpoint_body, R.drawable.quick_menu_brush_ballpoint_shadow_s));
        this.brushResourceMap.put(PenType.PENCIL, new BrushResource(R.drawable.quick_menu_brush_pencil_head, R.drawable.quick_menu_brush_pencil_body, R.drawable.quick_menu_brush_pencil_shadow_s));
        this.brushResourceMap.put(PenType.MARK, new BrushResource(R.drawable.quick_menu_brush_markpen_head, R.drawable.quick_menu_brush_markpen_body, R.drawable.quick_menu_brush_markpen_shadow_s));
        this.brushResourceMap.put(PenType.WATER_COLOR, new BrushResource(R.drawable.quick_menu_brush_watercolor_head, R.drawable.quick_menu_brush_watercolor_body, R.drawable.quick_menu_brush_watercolor_shadow_s));
    }

    private final float getDimension(int resId) {
        return this.context.getResources().getDimension(resId);
    }

    private final int getColor(int resId) {
        return this.context.getResources().getColor(resId, null);
    }

    private final Drawable getDrawable(int resId) {
        return ResourcesCompat.getDrawable(this.context.getResources(), resId, null);
    }

    private final String getString(int resId) {
        String string = this.context.getResources().getString(resId);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public final Drawable getBrushDrawable(PenType brush, int color) {
        Intrinsics.checkNotNullParameter(brush, "brush");
        BrushResource brushResource = (BrushResource) MapsKt.getValue(this.brushResourceMap, brush);
        Drawable[] drawableArr = new Drawable[3];
        Drawable drawable = getDrawable(brushResource.getHeadId());
        Drawable drawable2 = getDrawable(brushResource.getBodyId());
        Drawable drawable3 = getDrawable(brushResource.getShadowId());
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(this.context, miuix.appcompat.R.attr.isLightTheme, true);
        if (drawable3 != null && zResolveBoolean) {
            drawableArr[0] = drawable3.mutate();
        }
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            drawableArr[1] = drawableMutate;
            if (drawableMutate != null) {
                drawableMutate.setTint(color);
            }
        }
        if (drawable2 != null) {
            drawableArr[2] = drawable2.mutate();
        }
        return new LayerDrawable(drawableArr);
    }

    public final Drawable getEraserIcon(boolean isSelected) {
        if (!isSelected) {
            return getDrawable(R.drawable.quick_menu_eraser_icon);
        }
        return getDrawable(R.drawable.quick_menu_eraser_icon_selected);
    }

    public final Drawable getEraserDrawable() {
        Drawable[] drawableArr = new Drawable[2];
        Drawable drawable = getDrawable(R.drawable.quick_menu_tool_eraser_body);
        Drawable drawable2 = getDrawable(R.drawable.quick_menu_tool_eraser_shadow_s);
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(this.context, miuix.appcompat.R.attr.isLightTheme, true);
        if (drawable2 != null && zResolveBoolean) {
            drawableArr[0] = drawable2.mutate();
        }
        if (drawable != null) {
            drawableArr[1] = drawable.mutate();
        }
        return new LayerDrawable(drawableArr);
    }

    public final float getOuterCircleRadius() {
        return getDimension(R.dimen.pen_menu_outer_circle_radius) * this.scaleRatio;
    }

    public final float getInnerCircleRadius() {
        return getDimension(R.dimen.pen_menu_inner_circle_radius) * this.scaleRatio;
    }

    public final float getBrushOuterCircleRadius() {
        return getDimension(R.dimen.pen_menu_brush_outer_circle_radius) * this.scaleRatio;
    }

    public final float getBrushInnerCircleRadius() {
        return getDimension(R.dimen.pen_menu_brush_inner_circle_radius) * this.scaleRatio;
    }

    public final float getMenuGapSize() {
        return getDimension(R.dimen.pen_menu_gap_size) * this.scaleRatio;
    }

    public final float getPencilMenuStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_start_angle);
    }

    public final float getPencilMenuEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_end_angle);
    }

    public final float getColorMenuStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_color_start_angle);
    }

    public final float getColorMenuEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_color_end_angle);
    }

    public final float getEraserMenuStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_start_angle);
    }

    public final float getEraserMenuEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_end_angle);
    }

    public final float getBrushAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_brush_angle);
    }

    public final float getEraserAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_angle);
    }

    public final float getEraserIconAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_icon_angle);
    }

    public final float getPencilMenuSizeSeekbarStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_size_seekbar_start_angle);
    }

    public final float getPencilMenuSizeSeekbarEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_size_seekbar_end_angle);
    }

    public final float getPencilMenuAlphaSeekbarStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_alpha_seekbar_start_angle);
    }

    public final float getEraserMenuSizeSeekbarStartAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_size_seekbar_start_angle);
    }

    public final float getEraserMenuSizeSeekbarEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_eraser_size_seekbar_end_angle);
    }

    public final float getPencilMenuAlphaSeekbarEndAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_pencil_alpha_seekbar_end_angle);
    }

    public final Drawable getAlphaBackground() {
        return getDrawable(R.drawable.pen_menu_color_seekbar_bg);
    }

    public final float getSliderStrokeBold() {
        return getDimension(R.dimen.pen_menu_slider_stroke_bold_size);
    }

    public final float getSliderStrokeThin() {
        return getDimension(R.dimen.pen_menu_slider_stroke_thin_size);
    }

    public final float getSliderStrokeMiddle() {
        return getDimension(R.dimen.pen_menu_slider_stroke_middle_size);
    }

    public final int getSliderRadius() {
        return (int) (getDimension(R.dimen.pen_menu_slider_radius) * this.scaleRatio);
    }

    public final int getPencilSizeSeekbarBackgroundColor() {
        return getColor(R.color.pen_menu_pencil_size_seekbar_bg_color);
    }

    public final int getSizeSliderStrokeColor() {
        return getColor(R.color.pen_menu_slider_stroke_color);
    }

    public final int getAlphaSliderStrokeColor() {
        return getColor(R.color.pen_menu_alpha_slider_stroke_color);
    }

    public final float getAlphaSeekbarInnerStrokeSize() {
        return getDimension(R.dimen.pen_menu_alpha_seekbar_inner_stroke_size);
    }

    public final float getSizeSeekbarInnerStrokeSize() {
        return getDimension(R.dimen.pen_menu_size_seekbar_inner_stroke_size);
    }

    public final int getAlphaSeekbarInnerStrokeColor() {
        return getColor(R.color.pen_menu_alpha_seekbar_inner_stroke_color);
    }

    public final int getAlphaSeekbarGradientStartColor() {
        return getColor(R.color.pen_menu_alpha_seekbar_gradient_start_color);
    }

    public final PenSizeInfo getPenSizeInfoByType(PenType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return (PenSizeInfo) MapsKt.getValue(this.penSizeMap, type);
    }

    public final Pair<Integer, Integer> getAlphaRange() {
        return new Pair<>(Integer.valueOf(MathKt.roundToInt(2.5500000000000003d)), 255);
    }

    public final float getBrushPreviewBitmapWidth() {
        return getDimension(R.dimen.pen_menu_brush_preview_bitmap_width) * this.scaleRatio;
    }

    public final float getBrushPreviewBitmapMinHeight() {
        return getDimension(R.dimen.pen_menu_brush_preview_bitmap_min_height) * this.scaleRatio;
    }

    public final float getBrushPreviewBitmapMaxHeight() {
        return getDimension(R.dimen.pen_menu_brush_preview_bitmap_max_height) * this.scaleRatio;
    }

    public final float getMenuLimitTop() {
        return getDimension(R.dimen.pen_menu_limit_top);
    }

    public final float getMenuLimitLRB() {
        return getDimension(R.dimen.pen_menu_limit);
    }

    public final float getWrapperExtendSize() {
        return getDimension(R.dimen.pen_menu_wrapper_extend_size);
    }

    public final float getEraserIconSize() {
        return getDimension(R.dimen.pen_menu_eraser_icon_size) * this.scaleRatio;
    }

    public final float getUniformCircleDiameter() {
        return getDimension(R.dimen.pen_menu_uniform_circle_diameter) * this.scaleRatio;
    }

    public final float getEraserDrawTextSize() {
        return getDimension(R.dimen.pen_menu_eraser_draw_text_size) * this.scaleRatio;
    }

    public final int getEraserSwitchButtonSelectedColor() {
        return getColor(R.color.pen_menu_eraser_button_selected_color);
    }

    public final int getEraserSwitchButtonUnselectedColor() {
        return getColor(R.color.pen_menu_eraser_button_unselected_color);
    }

    public final float getFirstColorSelectButtonAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_first_color_select_angle);
    }

    public final float getSecondColorSelectButtonAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_second_color_select_angle);
    }

    public final float getThirdColorSelectButtonAngle() {
        return this.context.getResources().getFloat(R.dimen.pen_menu_third_color_select_angle);
    }

    public final int getMenuBackgroundColor() {
        return getColor(R.color.pen_menu_background_color);
    }

    public final int getMenuShadowColor() {
        return getColor(R.color.pen_menu_shadow_color);
    }

    public final float getColorMenuHorizontalGap() {
        return getDimension(R.dimen.pen_menu_color_horizontal_limit) * this.scaleRatio;
    }

    public final float getColorPopupWindowGap() {
        return getDimension(R.dimen.pen_menu_color_popup_window_gap);
    }

    public final String getPenThicknessText() {
        return getString(R.string.creation_pen_thickness);
    }

    public final String getPenAlphaText() {
        return getString(R.string.creation_color_picker_popup_alpha_text_transparent);
    }

    public final String getEraserSizeText() {
        return getString(R.string.creation_eraser_size);
    }

    public final String getBrushMenuAccessibilityContentDescription(PenType penType) {
        Intrinsics.checkNotNullParameter(penType, "penType");
        int i = WhenMappings.$EnumSwitchMapping$0[penType.ordinal()];
        if (i == 1) {
            return getString(R.string.creation_tool_ink);
        }
        if (i == 2) {
            return getString(R.string.creation_tool_mark);
        }
        if (i == 3) {
            return getString(R.string.creation_tool_ballpoint);
        }
        if (i == 4) {
            return getString(R.string.creation_tool_pencil);
        }
        if (i != 5) {
            throw new NoWhenBranchMatchedException();
        }
        return getString(R.string.creation_tool_watercolor);
    }
}
