package com.miui.notes.ai.ui.material;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.MaterialEffectUtil;
import com.miui.notes.base.utils.Logger;
import miuix.core.util.MiuiBlurUtils;
import miuix.graphics.shadow.DropShadowConfig;
import miuix.graphics.shadow.DropShadowHelper;
import miuix.smooth.SmoothContainerDrawable2;
import miuix.view.BlurableWidget;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes4.dex */
public class MaterialEffectContainer extends FrameLayout implements BlurableWidget {
    private static final int DARK_MODE = 2;
    private static final int LIGHT_MODE = 1;
    private static final int NO_BINDING_MODE = 0;
    private static final String TAG = "MaterialEffectContainer";
    private boolean isApplyShadow;
    private boolean isBgBlurEnable;
    private boolean isBlurIgnore;
    private boolean isLightTheme;
    private boolean isMaterialFeatureEnable;
    private boolean isSpecialShape;
    private int mBgBlurRadius;
    private int[] mBlendColors;
    private int[] mBlendModes;
    private MiuiBlurUiHelper mBlurUiHelper;
    private int mCornerRadius;
    private int mDefaultBgColor;
    private DropShadowHelper mDropShadowHelper;
    private boolean mIsMaterialRealEnable;
    private int mRendBindMode;
    private float mShadowBlurRadius;
    private int mShadowColor;
    private float mShadowDispersion;
    private float mShadowOffsetX;
    private float mShadowOffsetY;
    private int[] mStrokeColors;
    private int mStrokeWidth;

    public MaterialEffectContainer(Context context) {
        this(context, null);
    }

    public MaterialEffectContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialEffectContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isApplyShadow = true;
        this.isSpecialShape = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialEffectContainer, i, 0);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialEffectContainer_material_stroke_width, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MaterialEffectContainer_material_stroke_colors, 0);
        if (resourceId != 0) {
            this.mStrokeColors = getResources().getIntArray(resourceId);
        }
        this.mCornerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialEffectContainer_material_corner_radius, 18);
        this.mShadowOffsetX = typedArrayObtainStyledAttributes.getFloat(R.styleable.MaterialEffectContainer_material_shadow_x, 0.0f);
        this.mShadowOffsetY = typedArrayObtainStyledAttributes.getFloat(R.styleable.MaterialEffectContainer_material_shadow_y, 0.0f);
        this.mShadowBlurRadius = typedArrayObtainStyledAttributes.getFloat(R.styleable.MaterialEffectContainer_material_shadow_radius, 0.0f);
        this.mShadowDispersion = typedArrayObtainStyledAttributes.getFloat(R.styleable.MaterialEffectContainer_material_shadow_dispersion, 1.0f);
        this.mShadowColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MaterialEffectContainer_material_shadow_color, 0);
        this.mBgBlurRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialEffectContainer_material_blur_radius, 50);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MaterialEffectContainer_material_blend_modes, 0);
        if (resourceId2 != 0) {
            this.mBlendModes = getResources().getIntArray(resourceId2);
        } else {
            this.mBlendModes = new int[]{100, 106};
        }
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MaterialEffectContainer_material_blend_colors, 0);
        if (resourceId3 != 0) {
            this.mBlendColors = getResources().getIntArray(resourceId3);
        }
        this.isLightTheme = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialEffectContainer_material_is_light_theme, true);
        this.isBgBlurEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialEffectContainer_material_bg_blur_enable, false);
        this.isMaterialFeatureEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialEffectContainer_material_feature_enable, true);
        this.mDefaultBgColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MaterialEffectContainer_material_default_bg_color, 0);
        this.mRendBindMode = typedArrayObtainStyledAttributes.getInt(R.styleable.MaterialEffectContainer_material_binding_render_mode, 0);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        boolean zIsNightMode = DisplayUtil.isNightMode(getContext());
        MiuiBlurUiHelper miuiBlurUiHelper = new MiuiBlurUiHelper(getContext(), this, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: com.miui.notes.ai.ui.material.MaterialEffectContainer.1
            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public void onBlurEnableStateChanged(boolean z) {
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public void onCreateBlurParams(MiuiBlurUiHelper miuiBlurUiHelper2) {
                if (MaterialEffectContainer.this.mBlendColors == null || MaterialEffectContainer.this.mBlendModes == null) {
                    return;
                }
                if (MaterialEffectContainer.this.isBlurIgnore) {
                    miuiBlurUiHelper2.setBlurParams(MaterialEffectContainer.this.mBlendColors, MaterialEffectContainer.this.mBlendModes, 0);
                } else {
                    miuiBlurUiHelper2.setBlurParams(MaterialEffectContainer.this.mBlendColors, MaterialEffectContainer.this.mBlendModes, MaterialEffectContainer.this.mBgBlurRadius);
                }
            }

            @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
            public void onBlurApplyStateChanged(boolean z) {
                MaterialEffectContainer.this.applyGradientBorder();
            }
        });
        this.mBlurUiHelper = miuiBlurUiHelper;
        miuiBlurUiHelper.enableBlurSelfAsBackground(this.isBgBlurEnable);
        Context context = getContext();
        DropShadowConfig.Builder offsetYDp = new DropShadowConfig.Builder(this.mShadowBlurRadius).setOffsetXDp((int) this.mShadowOffsetX).setOffsetYDp((int) this.mShadowOffsetY);
        int i = this.mShadowColor;
        DropShadowHelper dropShadowHelper = new DropShadowHelper(context, offsetYDp.setColor(i, i).create(), this.isLightTheme);
        this.mDropShadowHelper = dropShadowHelper;
        dropShadowHelper.setEnableMiShadow(true);
        if (!this.mDropShadowHelper.isEnableMiShadow() && this.mShadowColor != 0) {
            setOutlineAmbientShadowColor(-16777216);
            setOutlineSpotShadowColor(this.mShadowColor);
        }
        if (MiuiBlurUtils.isEnable() && this.isMaterialFeatureEnable && isMatchesBindingMode(zIsNightMode)) {
            setSupportBlur(true);
            if (MiuiBlurUtils.isEffectEnable(getContext())) {
                this.mIsMaterialRealEnable = true;
                setEnableBlur(true);
                applyBlur(true);
            } else {
                setEnableBlur(false);
                applyBlur(false);
            }
        } else {
            setSupportBlur(false);
            setEnableBlur(false);
            applyBlur(false);
        }
        Logger.INSTANCE.d(TAG, "init advanced material effect => " + (getId() == -1 ? "" : getResources().getResourceName(getId())) + ", realEnable: " + this.mIsMaterialRealEnable);
        applyGradientBorder();
        if (this.isBlurIgnore) {
            MaterialEffectUtil.setMixEffectEnable(this, true);
        }
    }

    private boolean isMatchesBindingMode(boolean z) {
        int i = this.mRendBindMode;
        return i == 0 || (z && i == 2) || (!z && i == 1);
    }

    private void applyShadowEffect() {
        if (this.isApplyShadow && MaterialEffectUtil.supportMiShadow()) {
            MaterialEffectUtil.setMiShadow(this, this.mShadowColor, this.mShadowOffsetX, this.mShadowOffsetY, this.mShadowBlurRadius, this.mShadowDispersion);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        DropShadowHelper dropShadowHelper = this.mDropShadowHelper;
        if (dropShadowHelper != null) {
            dropShadowHelper.updateShadowRect(i, i2, i3, i4);
            if (this.mShadowBlurRadius > 0.0f) {
                this.mDropShadowHelper.enableViewShadow(this, true, 2);
            } else {
                this.mDropShadowHelper.enableViewShadow(this, false, 2);
            }
        }
    }

    @Override // miuix.view.BlurableWidget
    public void setSupportBlur(boolean z) {
        this.mBlurUiHelper.setSupportBlur(z);
    }

    @Override // miuix.view.BlurableWidget
    public boolean isSupportBlur() {
        return this.mBlurUiHelper.isSupportBlur();
    }

    @Override // miuix.view.BlurableWidget
    public void setEnableBlur(boolean z) {
        this.mBlurUiHelper.setEnableBlur(z);
    }

    @Override // miuix.view.BlurableWidget
    public boolean isEnableBlur() {
        return this.mBlurUiHelper.isEnableBlur();
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        this.mBlurUiHelper.applyBlur(z);
    }

    @Override // miuix.view.BlurableWidget
    public boolean isApplyBlur() {
        return this.mBlurUiHelper.isApplyBlur();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.onConfigChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyGradientBorder() {
        SmoothContainerDrawable2 smoothContainerDrawable2;
        int[] iArr;
        if (this.mIsMaterialRealEnable) {
            smoothContainerDrawable2 = null;
        } else {
            smoothContainerDrawable2 = new SmoothContainerDrawable2();
            smoothContainerDrawable2.setChildDrawable(new ColorDrawable(this.mDefaultBgColor));
            smoothContainerDrawable2.setCornerRadius(this.mCornerRadius);
        }
        int i = this.mStrokeWidth;
        if (i == 0 || (iArr = this.mStrokeColors) == null) {
            return;
        }
        ShapeDrawable shapeDrawableCreateGradientBorderDrawable = createGradientBorderDrawable(iArr, this.mCornerRadius, i);
        RoundedRectangleShapeDrawable roundedRectangleShapeDrawable = new RoundedRectangleShapeDrawable(this.mCornerRadius, this.mStrokeWidth, this.mStrokeColors);
        roundedRectangleShapeDrawable.setBorderShape(shapeDrawableCreateGradientBorderDrawable);
        if (smoothContainerDrawable2 != null) {
            setBackground(new LayerDrawable(new Drawable[]{smoothContainerDrawable2, roundedRectangleShapeDrawable}));
        } else {
            setBackground(roundedRectangleShapeDrawable);
        }
    }

    public ShapeDrawable createGradientBorderDrawable(final int[] iArr, float f, float f2) {
        float f3 = f - f2;
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{f, f, f, f, f, f, f, f}, new RectF(f2, f2, f2, f2), new float[]{f3, f3, f3, f3, f3, f3, f3, f3});
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() { // from class: com.miui.notes.ai.ui.material.MaterialEffectContainer.2
            @Override // android.graphics.drawable.ShapeDrawable.ShaderFactory
            public Shader resize(int i, int i2) {
                return new LinearGradient(0.0f, 0.0f, 0.0f, i2, iArr, (float[]) null, Shader.TileMode.CLAMP);
            }
        };
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.setShaderFactory(shaderFactory);
        return shapeDrawable;
    }
}
