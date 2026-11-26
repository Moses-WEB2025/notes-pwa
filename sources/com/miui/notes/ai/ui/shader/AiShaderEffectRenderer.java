package com.miui.notes.ai.ui.shader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;
import android.util.Log;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import miuix.appcompat.view.menu.HyperMenuUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.SystemProperties;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AiShaderEffectRenderer {
    public static final int BG_EFFECT = 8;
    public static final int BG_EFFECT_CASE = 3;
    public static final int INPUT_CASE = 1;
    public static final int INPUT_EFFECT = 10;
    public static final int PAD_TYPE = 20;
    public static final int PANEL_CASE = 2;
    public static final int PANEL_EFFECT = 9;
    public static final int PHONE_TYPE = 21;
    private static final String TAG = "AiShaderEffectRenderer";
    RuntimeShader backgroundRuntimeShader;
    RuntimeShader inputRuntimeShader;
    Context mContext;
    Resources mResources;
    RuntimeShader panelRuntimeShader;
    private float uInputShadowCornerRadius;
    private float uPanelShadowCornerRadius;
    private float[] uRimMaskColor;
    private float bgProgress = 1.0f;
    private float uiProgress = 0.0f;
    private float uAnimTime = System.nanoTime() / 1.0E9f;
    private float[] uBgBound = {0.0f, 0.0f, 1.0f, 1.0f};
    private float uTranslateY = 0.0f;
    private float[] uPoints = {0.1f, 0.22f, 1.0f, 0.3f, 0.55f, 1.0f, 0.83f, 0.65f, 1.0f, 0.73f, 0.05f, 1.0f};
    private float[] uColors = {0.56f, 0.41f, 1.0f, 0.35f, 1.0f, 0.56f, 0.89f, 0.5f, 0.22f, 0.6f, 1.0f, 0.5f, 1.0f, 0.48f, 0.06f, 0.4f};
    private float[] uDarkColors = {0.643f, 0.486f, 1.0f, 0.3f, 0.996f, 0.56f, 0.89f, 0.3f, 0.22f, 0.6f, 1.0f, 0.3f, 0.992f, 0.753f, 0.44f, 0.3f};
    private float[] mRecommendBgColors = {0.343f, 0.386f, 1.0f, 0.3f, 1.0f, 0.56f, 0.89f, 0.5f, 1.0f, 0.48f, 0.06f, 0.3f, 0.22f, 0.6f, 1.0f, 0.4f};
    private float[] mInputBgColors = new float[16];
    private float[] mBgColors = new float[16];
    private float[] mPanelRimColors = new float[16];
    private float[] mInputRimColors = new float[16];
    private float[] mInputRecommendColors = new float[16];
    private float uAlphaMulti = 1.0f;
    private float uNoiseScale = 1.5f;
    private float uPointOffset = 0.1f;
    private float uPointRadiusMulti = 1.0f;
    private float uSaturateOffset = 0.2f;
    private float uLightOffset = 0.2f;
    private float uAlphaOffset = 0.5f;
    private float uShadowColorMulti = 0.3f;
    private float uShadowColorOffset = 0.3f;
    private float uShadowNoiseScale = 5.0f;
    private float uShadowOffset = 0.01f;
    private float[] forePanelWH = {1288.0f, 1235.0f};
    private float uRimSize = 150.0f;
    private float[] floatingPanelWH = {1372.0f, 1319.0f};
    private float[] bgEffectViewWH = {1440.0f, 3200.0f};
    private float uWhiteRimIntensity = 0.5f;
    private boolean mIsDebugMode = false;
    private RenderData mRenderData = new RenderData();
    private int mDeviceType = 21;

    public AiShaderEffectRenderer(Context context) {
        init(context);
    }

    private String loadShader(Resources resources, int i) {
        try {
            InputStream inputStreamOpenRawResource = resources.openRawResource(i);
            try {
                Scanner scanner = new Scanner(inputStreamOpenRawResource);
                try {
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine()).append(StringUtils.LF);
                    }
                    String string = sb.toString();
                    scanner.close();
                    if (inputStreamOpenRawResource != null) {
                        inputStreamOpenRawResource.close();
                    }
                    return string;
                } catch (Throwable th) {
                    try {
                        scanner.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e("NoteApp", "Error loading shader resource ID: " + i + ", e:" + e.getMessage());
            return "";
        }
    }

    public void init(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        loadShaderEffectColors();
        this.backgroundRuntimeShader = new RuntimeShader(loadShader(this.mResources, R.raw.bg_frag));
        this.panelRuntimeShader = new RuntimeShader(loadShader(this.mResources, R.raw.panel_frag));
        this.inputRuntimeShader = new RuntimeShader(loadShader(this.mResources, R.raw.input_frag));
        this.backgroundRuntimeShader.setFloatUniform("uTranslateY", this.mRenderData.uTranslateY);
        this.backgroundRuntimeShader.setFloatUniform("uNoiseScale", this.mRenderData.uNoiseScale);
        this.backgroundRuntimeShader.setFloatUniform("uPointRadiusMulti", this.mRenderData.uPointRadiusMulti);
        this.backgroundRuntimeShader.setFloatUniform("uSaturateOffset", this.mRenderData.uSaturateOffset);
        this.backgroundRuntimeShader.setFloatUniform("uColors", this.mBgColors);
        this.panelRuntimeShader.setFloatUniform("uTranslateY", this.mRenderData.uTranslateY);
        this.panelRuntimeShader.setFloatUniform("uNoiseScale", this.mRenderData.uNoiseScale);
        this.panelRuntimeShader.setFloatUniform("uPointRadiusMulti", this.mRenderData.uPointRadiusMulti);
        this.panelRuntimeShader.setFloatUniform("uSaturateOffset", this.mRenderData.uSaturateOffset);
        this.panelRuntimeShader.setFloatUniform("uColors", this.mPanelRimColors);
        this.inputRuntimeShader.setFloatUniform("uTranslateY", this.mRenderData.uTranslateY);
        this.inputRuntimeShader.setFloatUniform("uNoiseScale", this.mRenderData.uNoiseScale);
        this.inputRuntimeShader.setFloatUniform("uPointRadiusMulti", this.mRenderData.uPointRadiusMulti);
        this.inputRuntimeShader.setFloatUniform("uSaturateOffset", this.mRenderData.uSaturateOffset);
        this.inputRuntimeShader.setFloatUniform("uColors", this.mInputRimColors);
        adaptColorLocForDevice();
        applyInputRimMaskConfig();
        setRimEffectSize(3);
        setRimEffectSize(2);
        setRimEffectSize(1);
        adaptLightOffsetConfig();
        this.uInputShadowCornerRadius = this.mResources.getDimension(R.dimen.ai_input_corner_mask_radius);
        this.uPanelShadowCornerRadius = this.mResources.getDimension(R.dimen.ai_panel_card_corner_radius);
        this.inputRuntimeShader.setFloatUniform("rimRadius", this.uInputShadowCornerRadius);
        this.panelRuntimeShader.setFloatUniform("rimRadius", this.uPanelShadowCornerRadius);
    }

    private void setPhoneCaseParams() {
        this.mRenderData.uPoints = new float[]{0.25f, 0.2f, 0.65f, 0.25f, 0.7f, 0.65f, 0.7f, 0.7f, 0.65f, 0.7f, 0.2f, 0.65f};
    }

    private void setPadCaseParams() {
        this.mRenderData.uPoints = new float[]{0.35f, 0.2f, 0.6f, 0.35f, 0.65f, 0.6f, 0.65f, 0.65f, 0.6f, 0.7f, 0.2f, 0.6f};
    }

    public void setInputCaseParams() {
        this.backgroundRuntimeShader.setFloatUniform("uPoints", this.mRenderData.uPoints);
        this.backgroundRuntimeShader.setFloatUniform("uAlphaMulti", this.mRenderData.bgState.uAlphaMulti);
        this.backgroundRuntimeShader.setFloatUniform("uPointOffset", this.mRenderData.bgState.uPointOffset);
        this.backgroundRuntimeShader.setFloatUniform("uLightOffset", this.mRenderData.bgState.uLightOffset);
        this.inputRuntimeShader.setFloatUniform("uPoints", this.mRenderData.uPoints);
        this.inputRuntimeShader.setFloatUniform("uBound", this.mRenderData.inputState.uBound);
        this.inputRuntimeShader.setFloatUniform("uAlphaMulti", this.mRenderData.inputState.uAlphaMulti);
        this.inputRuntimeShader.setFloatUniform("uPointOffset", this.mRenderData.inputState.uPointOffset);
        this.inputRuntimeShader.setFloatUniform("sdfOffset", this.mRenderData.inputState.sdfOffset);
        this.inputRuntimeShader.setFloatUniform("rimSize", this.mRenderData.inputState.rimSize);
        this.inputRuntimeShader.setFloatUniform("whiteRimOffset", this.mRenderData.inputState.whiteRimOffset);
        this.inputRuntimeShader.setFloatUniform("whiteRimIntensity", this.mRenderData.inputState.whiteRimIntensity);
        this.inputRuntimeShader.setFloatUniform("whiteAlpha", this.mRenderData.inputState.whiteAlpha);
    }

    public void setPanelCaseParams() {
        this.backgroundRuntimeShader.setFloatUniform("uPoints", this.mRenderData.uPoints);
        this.backgroundRuntimeShader.setFloatUniform("uAlphaMulti", this.mRenderData.bgState.uAlphaMulti);
        this.backgroundRuntimeShader.setFloatUniform("uPointOffset", this.mRenderData.bgState.uPointOffset);
        this.backgroundRuntimeShader.setFloatUniform("uLightOffset", this.mRenderData.bgState.uLightOffset);
        this.panelRuntimeShader.setFloatUniform("uPoints", this.mRenderData.uPoints);
        this.panelRuntimeShader.setFloatUniform("uBound", this.mRenderData.panelState.uBound);
        this.panelRuntimeShader.setFloatUniform("uAlphaMulti", this.mRenderData.panelState.uAlphaMulti);
        this.panelRuntimeShader.setFloatUniform("uPointOffset", this.mRenderData.panelState.uPointOffset);
        this.panelRuntimeShader.setFloatUniform("sdfOffset", this.mRenderData.panelState.sdfOffset);
        this.panelRuntimeShader.setFloatUniform("rimSize", this.mRenderData.panelState.rimSize);
    }

    private void adaptColorLocForDevice() {
        boolean z = RomUtils.isPadDevice() || RomUtils.isFoldDevice();
        if (z) {
            this.mDeviceType = 20;
            setPhoneCaseParams();
        } else {
            setPhoneCaseParams();
        }
        Logger.INSTANCE.d(TAG, "adaptColorLocForDevice, isPad: " + z);
    }

    public void setUniforms(float f, int i) {
        if (i == 1) {
            setInputCaseParams();
        } else if (i == 2) {
            setPanelCaseParams();
        }
    }

    public RenderEffect getRenderEffect(int i) {
        if (i == 8) {
            return RenderEffect.createRuntimeShaderEffect(this.backgroundRuntimeShader, "uTex");
        }
        if (i == 9) {
            return RenderEffect.createRuntimeShaderEffect(this.panelRuntimeShader, "uTex");
        }
        if (i == 10) {
            return RenderEffect.createRuntimeShaderEffect(this.inputRuntimeShader, "uTex");
        }
        return null;
    }

    public void updateMaterial() {
        this.backgroundRuntimeShader.setFloatUniform("uAnimTime", this.mRenderData.uAnimTime);
        this.backgroundRuntimeShader.setFloatUniform("bgProgress", this.bgProgress);
        this.backgroundRuntimeShader.setFloatUniform("uResolution", this.bgEffectViewWH);
        this.backgroundRuntimeShader.setFloatUniform("uBound", this.mRenderData.bgState.uBound);
        this.panelRuntimeShader.setFloatUniform("uAnimTime", this.mRenderData.uAnimTime);
        this.panelRuntimeShader.setFloatUniform("forePanelWH", this.forePanelWH);
        this.panelRuntimeShader.setFloatUniform("uResolution", this.floatingPanelWH);
        this.inputRuntimeShader.setFloatUniform("uAnimTime", this.mRenderData.uAnimTime);
        this.inputRuntimeShader.setFloatUniform("forePanelWH", this.forePanelWH);
        this.inputRuntimeShader.setFloatUniform("uResolution", this.floatingPanelWH);
    }

    public void applyPanelBgColors() {
        this.backgroundRuntimeShader.setFloatUniform("uColors", this.mBgColors);
    }

    public void applyInputStyleBgColors(boolean z) {
        if (z) {
            this.backgroundRuntimeShader.setFloatUniform("uColors", this.mRecommendBgColors);
        } else {
            this.backgroundRuntimeShader.setFloatUniform("uColors", this.mInputBgColors);
        }
    }

    public void setUiProgress(float f) {
        this.uiProgress = f;
    }

    public void setBgProgress(float f) {
        this.bgProgress = f;
    }

    public void setAnimTime(float f) {
        RenderData renderData = this.mRenderData;
        renderData.uAnimTime = f * renderData.gradientSpeed;
    }

    public void setForePanelWH(float f, float f2) {
        float[] fArr = this.forePanelWH;
        fArr[0] = f;
        fArr[1] = f2;
    }

    public void setFloatingPanelWH(float f, float f2) {
        float[] fArr = this.floatingPanelWH;
        fArr[0] = f;
        fArr[1] = f2;
    }

    public void setBgEffectViewWH(float f, float f2) {
        float[] fArr = this.bgEffectViewWH;
        fArr[0] = f;
        fArr[1] = f2;
    }

    public void setWhiteAlpha(float f) {
        this.mRenderData.inputState.whiteAlpha = f;
    }

    public void setWhiteRimSize(float f) {
        if (this.inputRuntimeShader != null) {
            Log.i(TAG, "setWhiteRimSize=" + f);
            this.inputRuntimeShader.setFloatUniform("whiteRimSize", f);
        }
    }

    private void checkIfApplyColorFromSysConfig(boolean z) {
        String str = SystemProperties.get("persist.sys.note.shader.colors", "");
        if (str.isEmpty()) {
            return;
        }
        String[] strArrSplit = str.replace("0x", "").toUpperCase().split(",");
        if (strArrSplit.length != 4) {
            Logger.INSTANCE.w(TAG, "applyColorFromSysConfig, err color format");
            return;
        }
        try {
            float[] fArr = z ? this.uDarkColors : this.uColors;
            int[] iArr = new int[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                int color = Color.parseColor(HyperMenuUtils.GROUP_TITLE_START + strArrSplit[i]);
                float[] rgba = toRGBA(color);
                for (int i2 = 0; i2 < rgba.length; i2++) {
                    fArr[i + i2] = rgba[i2];
                }
                iArr[i] = color;
            }
            Logger.INSTANCE.d(TAG, "applyColorFromSysConfig, loaded colors: " + Arrays.toString(fArr));
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "applyColorFromSysConfig" + e);
        }
    }

    private float[] toRGBA(int i) {
        return new float[]{((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, (i >>> 24) / 255.0f};
    }

    public void setBgBound(float[] fArr) {
        this.mRenderData.bgState.uBound = fArr;
    }

    private void loadShaderEffectColors() {
        this.mBgColors = transformGLColors(this.mContext.getResources().getIntArray(R.array.ai_shader_effect_bg_colors));
        this.mPanelRimColors = transformGLColors(this.mContext.getResources().getIntArray(R.array.ai_shader_panel_rim_colors));
        this.mInputRimColors = transformGLColors(this.mContext.getResources().getIntArray(R.array.ai_shader_input_rim_colors));
        this.mInputBgColors = transformGLColors(this.mContext.getResources().getIntArray(R.array.ai_shader_input_bg_colors));
        this.mRecommendBgColors = transformGLColors(this.mContext.getResources().getIntArray(R.array.ai_shader_recommend_bg_colors));
        if (this.mIsDebugMode) {
            loadColorConfig(this.mBgColors, "persist.sys.note.shader.bg_colors");
            loadColorConfig(this.mPanelRimColors, "persist.sys.note.shader.panel_colors");
            loadColorConfig(this.mInputRimColors, "persist.sys.note.shader.input_colors");
            loadColorConfig(this.mInputBgColors, "persist.sys.note.shader.input_bg_colors");
            loadColorConfig(this.mRecommendBgColors, "persist.sys.note.shader.recommend_bg_colors");
        }
    }

    private void loadColorConfig(float[] fArr, String str) {
        String str2 = SystemProperties.get(str, "");
        if (str2.isEmpty()) {
            return;
        }
        String[] strArrSplit = str2.replace("0x", "").toUpperCase().split(",");
        if (strArrSplit.length != 4) {
            Logger.INSTANCE.w(TAG, "loadColorConfig, err color format");
            return;
        }
        for (int i = 0; i < strArrSplit.length; i++) {
            try {
                float[] rgba = toRGBA(Color.parseColor(HyperMenuUtils.GROUP_TITLE_START + strArrSplit[i]));
                for (int i2 = 0; i2 < rgba.length; i2++) {
                    fArr[(i * 4) + i2] = rgba[i2];
                }
            } catch (Exception e) {
                Logger.INSTANCE.w(TAG, "loadColorConfig" + e);
                return;
            }
        }
        Logger.INSTANCE.d(TAG, "loadColorConfig, loaded colors: " + Arrays.toString(fArr));
    }

    private float[] transformGLColors(int[] iArr) {
        float[] fArr = new float[iArr.length * 4];
        for (int i = 0; i < iArr.length; i++) {
            float[] rgba = toRGBA(iArr[i]);
            for (int i2 = 0; i2 < rgba.length; i2++) {
                fArr[(i * 4) + i2] = rgba[i2];
            }
        }
        return fArr;
    }

    public void setRimSize(int i, float f) {
        if (i == 2) {
            this.panelRuntimeShader.setFloatUniform("rimSize", f);
        } else if (i == 1) {
            this.inputRuntimeShader.setFloatUniform("rimSize", f);
        } else if (i == 3) {
            this.backgroundRuntimeShader.setFloatUniform("rimSize", f);
        }
    }

    private void setRimEffectSize(int i) {
        if (i == 2) {
            this.panelRuntimeShader.setFloatUniform("rimSize", this.mIsDebugMode ? SystemProperties.getInt("persist.sys.note.shader.panel_sdf", 40) : 40.0f);
        } else if (i == 1) {
            this.inputRuntimeShader.setFloatUniform("rimSize", this.mIsDebugMode ? SystemProperties.getInt("persist.sys.note.shader.input_sdf", 40) : 40.0f);
        }
    }

    public int getInputBgEffectBoundsPadding(Context context, boolean z) {
        if (z) {
            return this.mIsDebugMode ? SystemProperties.getInt("persist.sys.note.shader.bg_padding_recommend", MiuixUIUtils.dp2px(context, 60.0f)) : MiuixUIUtils.dp2px(context, 60.0f);
        }
        return this.mIsDebugMode ? SystemProperties.getInt("persist.sys.note.shader.bg_padding_input", MiuixUIUtils.dp2px(context, 70.0f)) : MiuixUIUtils.dp2px(context, 70.0f);
    }

    private void applyInputRimMaskConfig() {
        float f;
        this.uRimMaskColor = toRGBA(DisplayUtil.isNightMode(this.mContext) ? -16777216 : -1);
        if (this.mIsDebugMode) {
            String str = SystemProperties.get("persist.sys.note.shader.input_mask_color", "");
            if (!str.isEmpty()) {
                this.uRimMaskColor = toRGBA(Color.parseColor(HyperMenuUtils.GROUP_TITLE_START + str.replace("0x", "")));
            }
            f = SystemProperties.getInt("persist.sys.note.shader.input_mask_sdf", 500);
        } else {
            f = 500.0f;
        }
        Log.i(TAG, "setWhiteRimSize=rimRadius=" + f);
        this.inputRuntimeShader.setFloatUniform("uRimMaskColor", this.uRimMaskColor);
        this.inputRuntimeShader.setFloatUniform("whiteRimSize", f);
    }

    private void adaptLightOffsetConfig() {
        boolean zIsNightMode = DisplayUtil.isNightMode(this.mContext);
        float f = zIsNightMode ? 0.2f : 0.4f;
        this.inputRuntimeShader.setFloatUniform("uLightOffset", zIsNightMode ? 0.2f : 0.1f);
        this.panelRuntimeShader.setFloatUniform("uLightOffset", f);
    }

    public class StateArguments {
        float[] uBound = {0.0f, 0.0f, 1.0f, 1.0f};
        float uAlphaMulti = 1.0f;
        float uPointOffset = 0.1f;
        float uLightOffset = 0.15f;
        float sdfOffset = 0.0f;
        float rimSize = 150.0f;
        float rimRadius = 10.0f;
        float whiteRimSize = 500.0f;
        float whiteRimOffset = 0.0f;
        float whiteRimIntensity = 0.2f;
        float whiteAlpha = 1.0f;

        public StateArguments() {
        }
    }

    public class RenderData {
        StateArguments bgState;
        float gradientSpeed;
        StateArguments inputState;
        StateArguments panelState;
        float uAnimTime;
        float[] uColors;
        float uNoiseScale;
        float uPointRadiusMulti;
        float[] uPoints;
        float uSaturateOffset;
        float uTranslateY;

        public RenderData() {
            this.bgState = AiShaderEffectRenderer.this.new StateArguments();
            this.panelState = AiShaderEffectRenderer.this.new StateArguments();
            this.inputState = AiShaderEffectRenderer.this.new StateArguments();
            this.bgState.uPointOffset = 0.1f;
            this.bgState.uLightOffset = 0.1f;
            this.panelState.uPointOffset = 0.1f;
            this.panelState.uLightOffset = 0.4f;
            this.panelState.sdfOffset = 0.0f;
            this.panelState.rimSize = 100.0f;
            this.panelState.uBound = new float[]{0.032f, 0.032f, 0.937f, 0.937f};
            this.inputState.uAlphaMulti = 0.85f;
            this.inputState.uPointOffset = 0.03f;
            this.inputState.uLightOffset = 0.1f;
            this.inputState.sdfOffset = 0.0f;
            this.inputState.rimSize = 50.0f;
            this.inputState.uBound = new float[]{0.0f, 0.0f, 0.937f, 0.937f};
            this.inputState.whiteRimSize = 250.0f;
            this.inputState.whiteRimOffset = 0.0f;
            this.inputState.whiteRimIntensity = 0.5f;
            this.inputState.whiteAlpha = 1.0f;
            this.uAnimTime = System.nanoTime() / 1.0E9f;
            this.uTranslateY = 0.0f;
            this.uPoints = new float[]{0.25f, 0.2f, 0.65f, 0.25f, 0.7f, 0.65f, 0.7f, 0.7f, 0.65f, 0.7f, 0.2f, 0.65f};
            this.uColors = new float[]{0.64f, 0.49f, 1.0f, 0.5f, 1.0f, 0.55f, 0.82f, 0.5f, 0.22f, 0.6f, 1.0f, 0.4f, 0.99f, 0.76f, 0.43f, 0.5f};
            this.uNoiseScale = 1.5f;
            this.uPointRadiusMulti = 1.0f;
            this.uSaturateOffset = 0.2f;
            this.gradientSpeed = 1.5f;
        }
    }
}
