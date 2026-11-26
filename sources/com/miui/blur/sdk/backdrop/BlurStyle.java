package com.miui.blur.sdk.backdrop;

import android.graphics.BlendMode;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class BlurStyle {
    private static final boolean BACKGROUND_BLUR_SUPPORTED;
    public static final BlurStyle DEFAULT_DARK;
    public static final BlurStyle DEFAULT_LIGHT;
    private static final BlurStyle DUMMY;
    public static final BlurStyle HEAVY_DARK;
    public static final BlurStyle HEAVY_LIGHT;
    public static final BlurStyle THIN_DARK;
    public static final BlurStyle THIN_LIGHT;
    private final BlendConfig[] mBlendConfigs;
    private final int mBlurRadius;

    static {
        BlurStyle blurStyle = new BlurStyle(0);
        DUMMY = blurStyle;
        boolean z = BlurManager.BACKDROP_SAMPLING_ENABLED || BlurManager.GOOGLE_BACKGROUND_BLUR_SUPPORTED;
        BACKGROUND_BLUR_SUPPORTED = z;
        THIN_LIGHT = z ? new Builder().setBlurRadius(8).addBlendLayer(-2074585000, BlendMode.COLOR_DODGE).addBlendLayer(1088676835, null).build() : blurStyle;
        DEFAULT_LIGHT = z ? new Builder().setBlurRadius(10).addBlendLayer(-1889509280, BlendMode.COLOR_DODGE).addBlendLayer(-1544359182, null).build() : blurStyle;
        HEAVY_LIGHT = z ? new Builder().setBlurRadius(12).addBlendLayer(1970500467, BlendMode.COLOR_DODGE).addBlendLayer(-856295947, null).build() : blurStyle;
        THIN_DARK = z ? new Builder().setBlurRadius(8).addBlendLayer(1636469386, BlendMode.COLOR_BURN).addBlendLayer(1296187970, null).build() : blurStyle;
        DEFAULT_DARK = z ? new Builder().setBlurRadius(10).addBlendLayer(1970500467, BlendMode.COLOR_BURN).addBlendLayer(-1977211354, null).build() : blurStyle;
        if (z) {
            blurStyle = new Builder().setBlurRadius(12).addBlendLayer(2136759388, BlendMode.COLOR_BURN).addBlendLayer(-1088479457, null).build();
        }
        HEAVY_DARK = blurStyle;
    }

    BlurStyle(int i) {
        this.mBlurRadius = i;
        this.mBlendConfigs = null;
    }

    BlurStyle(int i, BlendConfig... blendConfigArr) {
        this.mBlurRadius = i;
        this.mBlendConfigs = blendConfigArr;
    }

    final BlendConfig[] getConfigs() {
        return this.mBlendConfigs;
    }

    final int getBlurRadius() {
        return this.mBlurRadius;
    }

    public static class Builder {
        private final List<BlendConfig> mBlendConfigs;
        private int mRadius;

        public Builder() {
            this.mBlendConfigs = new ArrayList();
            this.mRadius = 10;
        }

        public Builder(BlurStyle blurStyle) {
            this.mBlendConfigs = new ArrayList();
            this.mRadius = blurStyle.mBlurRadius;
            if (BlurManager.BACKDROP_SAMPLING_ENABLED) {
                for (BlendConfig blendConfig : blurStyle.mBlendConfigs) {
                    addBlendLayer(blendConfig.mColor, blendConfig.mBlendMode);
                }
            }
        }

        public Builder setBlurRadius(int i) {
            this.mRadius = i;
            return this;
        }

        public Builder addBlendLayer(int i, BlendMode blendMode) {
            this.mBlendConfigs.add(new BlendConfig(i, blendMode));
            return this;
        }

        public BlurStyle build() {
            return BlurStyle.BACKGROUND_BLUR_SUPPORTED ? new BlurStyle(this.mRadius, (BlendConfig[]) this.mBlendConfigs.toArray(new BlendConfig[0])) : BlurStyle.DUMMY;
        }
    }

    static class BlendConfig {
        final BlendMode mBlendMode;
        final int mColor;

        BlendConfig(int i, BlendMode blendMode) {
            this.mColor = i;
            this.mBlendMode = blendMode;
        }
    }
}
