package com.miui.notes.ai.floatingwindow;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.ai.text.widget.R;
import com.miui.doodle.document.Layer;
import com.miui.notes.ai.ui.shader.AiShaderEffectRenderer;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.MaterialEffectUtil;
import com.miui.notes.base.utils.FrameUpdateMonitor;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.core.util.MiuiBlurUtils;

/* JADX INFO: compiled from: GlobalAiTextAnimHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b$\u0018\u0000 G2\u00020\u0001:\u0002GHB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\"\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ\u0018\u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ\u001e\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%J8\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020\f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJG\u0010(\u001a\u00020\u001a2\b\u0010)\u001a\u0004\u0018\u00010\u001c2\b\u0010*\u001a\u0004\u0018\u00010\u001c2\b\u0010+\u001a\u0004\u0018\u00010\u001c2\b\u0010,\u001a\u0004\u0018\u00010\u001c2\u0006\u0010-\u001a\u00020%2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010/J\b\u00100\u001a\u00020\u001aH\u0007J\u0006\u00101\u001a\u00020\u001aJ\u0006\u00102\u001a\u00020\u001aJ\u0006\u00103\u001a\u00020\u001aJ\u0006\u00104\u001a\u00020\u001aJ\u0010\u00105\u001a\u00020\u001a2\b\u00106\u001a\u0004\u0018\u00010\u001cJ&\u00107\u001a\u00020\u001a2\b\u00108\u001a\u0004\u0018\u00010\u001c2\b\u00109\u001a\u0004\u0018\u00010\u001c2\b\u0010:\u001a\u0004\u0018\u00010\u001cH\u0007J\b\u0010;\u001a\u00020\u001aH\u0007J.\u0010<\u001a\u00020\u001a2\b\u0010=\u001a\u0004\u0018\u00010\u001c2\b\u0010>\u001a\u0004\u0018\u00010\u001c2\b\u0010\"\u001a\u0004\u0018\u00010\u001c2\u0006\u0010?\u001a\u00020\fH\u0007J&\u0010@\u001a\u00020\u001a2\b\u0010=\u001a\u0004\u0018\u00010\u001c2\b\u0010>\u001a\u0004\u0018\u00010\u001c2\b\u0010\"\u001a\u0004\u0018\u00010\u001cH\u0007J0\u0010A\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\f2\u0006\u0010C\u001a\u00020\fH\u0003J\u000e\u0010D\u001a\u00020\u001a2\u0006\u0010E\u001a\u00020\u0017J\u0006\u0010F\u001a\u00020\u001aR\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiTextAnimHelper;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mContext", "mAiShaderEffectRenderer", "Lcom/miui/notes/ai/ui/shader/AiShaderEffectRenderer;", "mLastGlobalTime", "", "mTime", "", "mDeltaTime", "mPreUpdateTime", "mScreenSize", "Landroid/graphics/Point;", "mBgBoundData", "", "mEffectBgPadding", "", "mShaderState", "mAiPanelAnimCallback", "Lcom/miui/notes/ai/floatingwindow/GlobalAiTextAnimHelper$AiPanelAnimCallback;", "upOffset", "startLoadingPanelAnim", "", "floatingWindow", "Landroid/view/View;", "startY", "transitionListener", "Lmiuix/animation/listener/TransitionListener;", "floatWindowCloseAnim", "inputAnimWithKeyboard", "inputLayout", "imeHeight", "keyboardShow", "", "isFirstShow", "targetHeight", "showTagInputLayoutAnim", "mPanelDividerLine", "mPanelTagListLayout", "mPanelShowInputLayout", "mFeedbackLayout", "canChange", "resCode", "(Landroid/view/View;Landroid/view/View;Landroid/view/View;Landroid/view/View;ZLjava/lang/Integer;)V", "initializeShaderEffect", "updateScreenSize", "tick", "resetTime", "releaseShaderRenderer", "initAdvancedMaterialEffects", "floatingContentView", "onShowSoftInputShaderEffect", "inputContainer", "inputContentView", "inputEffectView", "onHideSoftInputShaderEffect", "animateGradientBgFromPanelToInput", "bgGradientColorView", "floatingRimEffectView", "offsetY", "animateGradientBgFromInputToPanel", "animateGradientBgArea", Layer.KEY_TOP, Layer.KEY_HEIGHT, "setAiPanelAnimCallback", "callBack", "release", "Companion", "AiPanelAnimCallback", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextAnimHelper {
    public static final int SHADER_STATE_INPUT = 2;
    public static final int SHADER_STATE_PANEL = 1;
    public static final String TAG = "GlobalAiTextAnimHelper";
    private AiPanelAnimCallback mAiPanelAnimCallback;
    private AiShaderEffectRenderer mAiShaderEffectRenderer;
    private final float[] mBgBoundData;
    private final Context mContext;
    private float mDeltaTime;
    private int mEffectBgPadding;
    private long mLastGlobalTime;
    private float mPreUpdateTime;
    private final Point mScreenSize;
    private int mShaderState;
    private float mTime;
    private int upOffset;

    /* JADX INFO: compiled from: GlobalAiTextAnimHelper.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH'J2\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH'¨\u0006\u000fÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiTextAnimHelper$AiPanelAnimCallback;", "", "runPanelEffect", "", "render", "Lcom/miui/notes/ai/ui/shader/AiShaderEffectRenderer;", "bgBoundData", "", Constants.JSON_SCREEN_SIZE, "Landroid/graphics/Point;", "time", "", "runInputEffect", "effectBgPadding", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface AiPanelAnimCallback {
        void runInputEffect(AiShaderEffectRenderer render, float[] bgBoundData, Point screenSize, int effectBgPadding, float time);

        void runPanelEffect(AiShaderEffectRenderer render, float[] bgBoundData, Point screenSize, float time);
    }

    public GlobalAiTextAnimHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mContext = context;
        this.mScreenSize = new Point();
        this.mBgBoundData = new float[4];
        this.mShaderState = 1;
        if (Build.VERSION.SDK_INT >= 33) {
            this.mAiShaderEffectRenderer = new AiShaderEffectRenderer(context);
            if (DisplayUtil.isNightMode(context)) {
                AiShaderEffectRenderer aiShaderEffectRenderer = this.mAiShaderEffectRenderer;
                if (aiShaderEffectRenderer != null) {
                    aiShaderEffectRenderer.setWhiteRimSize(0.0f);
                    return;
                }
                return;
            }
            AiShaderEffectRenderer aiShaderEffectRenderer2 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer2 != null) {
                aiShaderEffectRenderer2.setWhiteRimSize(500.0f);
            }
        }
    }

    public final void startLoadingPanelAnim(View floatingWindow, int startY, TransitionListener transitionListener) {
        if (floatingWindow != null) {
            AnimState animStateAdd = new AnimState("loadingPanelShowStart").add(ViewProperty.TRANSLATION_Y, startY, new long[0]);
            AnimState animStateAdd2 = new AnimState("loadingPanelShowEnd").add(ViewProperty.TRANSLATION_Y, 0, new long[0]);
            AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
            if (transitionListener != null) {
                ease.addListeners(transitionListener);
            }
            Folme.useAt(floatingWindow).state().setTo(animStateAdd).to(animStateAdd2, ease);
        }
    }

    public final void floatWindowCloseAnim(View floatingWindow, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(floatingWindow, "floatingWindow");
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
        if (transitionListener != null) {
            ease.addListeners(transitionListener);
        }
        if (!DisplayUtil.hasNavigationBar(this.mContext)) {
            Folme.useAt(floatingWindow).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(floatingWindow.getHeight()), ease);
        } else {
            Folme.useAt(floatingWindow).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(floatingWindow.getHeight() + DisplayUtil.getNavigationBarHeightFromProp(this.mContext)), ease);
        }
    }

    public final void inputAnimWithKeyboard(View inputLayout, int imeHeight, boolean keyboardShow) {
        Intrinsics.checkNotNullParameter(inputLayout, "inputLayout");
        AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper.inputAnimWithKeyboard.1
        });
        if (!keyboardShow) {
            Folme.useAt(inputLayout).state().to(ViewProperty.TRANSLATION_Y, 0, animConfig);
            return;
        }
        int[] iArr = new int[2];
        inputLayout.getLocationOnScreen(iArr);
        Folme.useAt(inputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-RangesKt.coerceAtLeast(0, imeHeight - ((DisplayUtil.getScreenMetrics(this.mContext).heightPixels - iArr[1]) - inputLayout.getHeight()))), animConfig);
    }

    public final void inputAnimWithKeyboard(View inputLayout, int imeHeight, boolean keyboardShow, boolean isFirstShow, float targetHeight, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(inputLayout, "inputLayout");
        AnimConfig animConfig = new AnimConfig();
        if (transitionListener != null) {
            animConfig.addListeners(transitionListener);
        }
        float dimension = this.mContext.getResources().getDimension(R.dimen.global_ai_text_panel_input_height);
        View viewFindViewById = inputLayout.getRootView().findViewById(R.id.global_ai_edit_input_disable_layout);
        View viewFindViewById2 = inputLayout.getRootView().findViewById(R.id.global_ai_edit_input_enable_layout);
        if (!keyboardShow) {
            Folme.useAt(inputLayout).state().to(ViewProperty.TRANSLATION_Y, 0, ViewProperty.ALPHA, Float.valueOf(0.2f), ViewProperty.HEIGHT, Float.valueOf(dimension), animConfig);
            Folme.useAt(viewFindViewById, viewFindViewById2).state().to(ViewProperty.ALPHA, 0, animConfig);
            return;
        }
        ViewGroup.LayoutParams layoutParams = inputLayout.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        int iDip2px = imeHeight - ((DisplayUtil.getScreenMetrics(this.mContext).heightPixels - ((marginLayoutParams != null ? marginLayoutParams.topMargin : 0) + ((int) targetHeight))) - DisplayUtil.dip2px(this.mContext, 20.0f));
        if (iDip2px > 0 && iDip2px < DisplayUtil.dip2px(this.mContext, 70.0f)) {
            this.upOffset += iDip2px;
        } else {
            this.upOffset = iDip2px;
        }
        ViewGroup.LayoutParams layoutParams2 = inputLayout.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
        Log.i(TAG, "showInputPanel upDy=" + iDip2px + ",marginTop=" + (marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0) + " isFirstShow=" + isFirstShow + " translationY=" + inputLayout.getTranslationY() + " y=" + inputLayout.getY());
        Folme.useAt(inputLayout).state().setTo(ViewProperty.HEIGHT, Float.valueOf(dimension)).to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-this.upOffset), ViewProperty.HEIGHT, Float.valueOf(targetHeight), animConfig);
        Folme.useAt(viewFindViewById, viewFindViewById2).state().to(ViewProperty.ALPHA, 1, animConfig);
    }

    public static /* synthetic */ void showTagInputLayoutAnim$default(GlobalAiTextAnimHelper globalAiTextAnimHelper, View view, View view2, View view3, View view4, boolean z, Integer num, int i, Object obj) {
        if ((i & 32) != 0) {
            num = 0;
        }
        globalAiTextAnimHelper.showTagInputLayoutAnim(view, view2, view3, view4, z, num);
    }

    public final void showTagInputLayoutAnim(View mPanelDividerLine, View mPanelTagListLayout, View mPanelShowInputLayout, final View mFeedbackLayout, final boolean canChange, final Integer resCode) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.linear(150L));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper.showTagInputLayoutAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                Integer num;
                super.onComplete(toTag);
                View view = mFeedbackLayout;
                if (view != null) {
                    view.setVisibility((canChange && (num = resCode) != null && num.intValue() == 0) ? 0 : 4);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object toTag) {
                Integer num;
                super.onCancel(toTag);
                View view = mFeedbackLayout;
                if (view != null) {
                    view.setVisibility((canChange && (num = resCode) != null && num.intValue() == 0) ? 0 : 4);
                }
            }
        });
        Folme.useAt(mPanelDividerLine, mPanelTagListLayout, mPanelShowInputLayout).state().to(ViewProperty.ALPHA, Float.valueOf(canChange ? 1.0f : 0.3f), animConfig);
        Folme.useAt(mFeedbackLayout).state().to(ViewProperty.ALPHA, Float.valueOf((canChange && resCode != null && resCode.intValue() == 0) ? 1.0f : 0.0f), animConfig);
    }

    public final void initializeShaderEffect() {
        updateScreenSize();
        final AiShaderEffectRenderer aiShaderEffectRenderer = this.mAiShaderEffectRenderer;
        if (aiShaderEffectRenderer != null) {
            this.mEffectBgPadding = aiShaderEffectRenderer.getInputBgEffectBoundsPadding(this.mContext, false);
            tick();
            aiShaderEffectRenderer.setUniforms(0.0f, 2);
            FrameUpdateMonitor.INSTANCE.startMonitor(new Function1() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GlobalAiTextAnimHelper.initializeShaderEffect$lambda$5$lambda$4(this.f$0, aiShaderEffectRenderer, ((Float) obj).floatValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initializeShaderEffect$lambda$5$lambda$4(GlobalAiTextAnimHelper globalAiTextAnimHelper, AiShaderEffectRenderer aiShaderEffectRenderer, float f) {
        AiPanelAnimCallback aiPanelAnimCallback;
        float f2 = globalAiTextAnimHelper.mPreUpdateTime;
        if (f2 < f && f - f2 > 33.0f) {
            globalAiTextAnimHelper.mPreUpdateTime = f;
            int i = globalAiTextAnimHelper.mShaderState;
            if (i == 1) {
                AiPanelAnimCallback aiPanelAnimCallback2 = globalAiTextAnimHelper.mAiPanelAnimCallback;
                if (aiPanelAnimCallback2 != null) {
                    aiPanelAnimCallback2.runPanelEffect(aiShaderEffectRenderer, globalAiTextAnimHelper.mBgBoundData, globalAiTextAnimHelper.mScreenSize, globalAiTextAnimHelper.mTime);
                }
            } else if (i == 2 && (aiPanelAnimCallback = globalAiTextAnimHelper.mAiPanelAnimCallback) != null) {
                aiPanelAnimCallback.runInputEffect(aiShaderEffectRenderer, globalAiTextAnimHelper.mBgBoundData, globalAiTextAnimHelper.mScreenSize, globalAiTextAnimHelper.mEffectBgPadding, globalAiTextAnimHelper.mTime);
            }
        }
        return Unit.INSTANCE;
    }

    public final void updateScreenSize() {
        DisplayMetrics screenMetrics = DisplayUtil.getScreenMetrics(this.mContext);
        this.mScreenSize.set(screenMetrics.widthPixels, screenMetrics.heightPixels);
    }

    public final void tick() {
        long jNanoTime = System.nanoTime();
        float f = (float) ((jNanoTime - this.mLastGlobalTime) * 1.0E-9d);
        this.mDeltaTime = f;
        this.mTime += f;
        this.mLastGlobalTime = jNanoTime;
    }

    public final void resetTime() {
        this.mLastGlobalTime = System.nanoTime();
        this.mTime = 0.0f;
        this.mDeltaTime = 0.0f;
    }

    public final void releaseShaderRenderer() {
        FrameUpdateMonitor.INSTANCE.stopMonitor();
    }

    public final void initAdvancedMaterialEffects(View floatingContentView) {
        if (MiuiBlurUtils.isEffectEnable(this.mContext)) {
            MaterialEffectUtil.setPassWindowBlurEnabled(floatingContentView, true);
        }
    }

    public final void onShowSoftInputShaderEffect(View inputContainer, View inputContentView, View inputEffectView) {
        if (inputContainer != null && inputContentView != null && inputEffectView != null) {
            tick();
            AiShaderEffectRenderer aiShaderEffectRenderer = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer != null) {
                aiShaderEffectRenderer.setUniforms(this.mTime, 1);
            }
            AiShaderEffectRenderer aiShaderEffectRenderer2 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer2 != null) {
                aiShaderEffectRenderer2.setForePanelWH(inputContentView.getWidth(), inputContentView.getHeight());
            }
            AiShaderEffectRenderer aiShaderEffectRenderer3 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer3 != null) {
                aiShaderEffectRenderer3.setFloatingPanelWH(inputEffectView.getWidth(), inputEffectView.getHeight());
            }
            int[] iArr = new int[2];
            inputContainer.getLocationInWindow(iArr);
            this.mBgBoundData[0] = inputContainer.getLeft() / this.mScreenSize.x;
            this.mBgBoundData[1] = 1.0f - (((iArr[1] + inputContainer.getHeight()) + this.mEffectBgPadding) / this.mScreenSize.y);
            this.mBgBoundData[2] = inputContainer.getWidth() / this.mScreenSize.x;
            this.mBgBoundData[3] = (inputContainer.getHeight() + (this.mEffectBgPadding * 2.0f)) / this.mScreenSize.y;
            float[] fArr = this.mBgBoundData;
            if (fArr[1] < 0.0f) {
                fArr[1] = 1.0f - ((iArr[1] + inputContainer.getHeight()) / this.mScreenSize.y);
                this.mBgBoundData[3] = inputContainer.getHeight() / this.mScreenSize.y;
            }
            AiShaderEffectRenderer aiShaderEffectRenderer4 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer4 != null) {
                aiShaderEffectRenderer4.setBgBound(this.mBgBoundData);
            }
            AiShaderEffectRenderer aiShaderEffectRenderer5 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer5 != null) {
                aiShaderEffectRenderer5.updateMaterial();
            }
            AiShaderEffectRenderer aiShaderEffectRenderer6 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer6 != null) {
                aiShaderEffectRenderer6.applyInputStyleBgColors(false);
            }
            AiShaderEffectRenderer aiShaderEffectRenderer7 = this.mAiShaderEffectRenderer;
            if (aiShaderEffectRenderer7 != null) {
                inputEffectView.setRenderEffect(aiShaderEffectRenderer7.getRenderEffect(10));
            }
        }
        this.mShaderState = 2;
    }

    public final void onHideSoftInputShaderEffect() {
        this.mShaderState = 1;
    }

    public final void animateGradientBgFromPanelToInput(final View bgGradientColorView, final View floatingRimEffectView, View inputLayout, float offsetY) {
        if (floatingRimEffectView == null || inputLayout == null) {
            return;
        }
        final int[] iArr = new int[2];
        final float height = floatingRimEffectView.getHeight();
        final float height2 = inputLayout.getHeight() + (this.mEffectBgPadding * 2);
        floatingRimEffectView.getLocationInWindow(iArr);
        inputLayout.getLocationInWindow(new int[2]);
        float f = iArr[1] + height;
        final float f2 = this.mScreenSize.y - (offsetY - this.mEffectBgPadding);
        final float f3 = f - f2;
        final ValueProperty valueProperty = new ValueProperty("bgLocation", 0.03f);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
        ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper$animateGradientBgFromPanelToInput$1$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object toTag, Collection<UpdateInfo> updateList) {
                Intrinsics.checkNotNullParameter(updateList, "updateList");
                super.onUpdate(toTag, updateList);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(updateList, valueProperty);
                if (updateInfoFindBy != null) {
                    float floatValue = updateInfoFindBy.getFloatValue();
                    float f4 = 1.0f - ((floatValue - f2) / f3);
                    if (f4 <= 0.0f || f4 >= 1.0f) {
                        return;
                    }
                    float f5 = height;
                    float f6 = f5 - (f4 * (f5 - height2));
                    this.mBgBoundData[0] = iArr[0] / this.mScreenSize.x;
                    this.mBgBoundData[1] = 1.0f - (floatValue / this.mScreenSize.y);
                    this.mBgBoundData[2] = floatingRimEffectView.getWidth() / this.mScreenSize.x;
                    this.mBgBoundData[3] = f6 / this.mScreenSize.y;
                    AiShaderEffectRenderer aiShaderEffectRenderer = this.mAiShaderEffectRenderer;
                    if (aiShaderEffectRenderer != null) {
                        View view = bgGradientColorView;
                        GlobalAiTextAnimHelper globalAiTextAnimHelper = this;
                        if (view != null) {
                            aiShaderEffectRenderer.setBgBound(globalAiTextAnimHelper.mBgBoundData);
                            aiShaderEffectRenderer.updateMaterial();
                            view.setRenderEffect(aiShaderEffectRenderer.getRenderEffect(8));
                        }
                    }
                }
            }
        });
        Folme.useValue("bgLocation").setTo(valueProperty, Float.valueOf(f));
        Folme.useValue("bgLocation").to(valueProperty, Float.valueOf(f2), ease);
    }

    public final void animateGradientBgFromInputToPanel(final View bgGradientColorView, final View floatingRimEffectView, final View inputLayout) {
        if (floatingRimEffectView == null || inputLayout == null) {
            return;
        }
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        final float height = floatingRimEffectView.getHeight();
        final float height2 = inputLayout.getHeight() + (this.mEffectBgPadding * 2);
        floatingRimEffectView.getLocationInWindow(iArr);
        inputLayout.getLocationInWindow(iArr2);
        final float f = iArr2[1] - this.mEffectBgPadding;
        float f2 = iArr[1];
        final float f3 = f2 - f;
        final ValueProperty valueProperty = new ValueProperty("bgLocation", 0.03f);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
        ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiTextAnimHelper$animateGradientBgFromInputToPanel$1$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object toTag, Collection<UpdateInfo> updateList) {
                Intrinsics.checkNotNullParameter(updateList, "updateList");
                super.onUpdate(toTag, updateList);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(updateList, valueProperty);
                if (updateInfoFindBy != null) {
                    float floatValue = updateInfoFindBy.getFloatValue();
                    float f4 = (floatValue - f) / f3;
                    float f5 = height2;
                    float f6 = f5 + (f4 * (height - f5));
                    View view = bgGradientColorView;
                    if (view != null) {
                        this.animateGradientBgArea(view, floatingRimEffectView, inputLayout, floatValue, f6);
                    }
                }
            }
        });
        Folme.useValue("bgLocation").setTo(valueProperty, Float.valueOf(f));
        Folme.useValue("bgLocation").to(valueProperty, Float.valueOf(f2), ease);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void animateGradientBgArea(View bgGradientColorView, View floatingRimEffectView, View inputLayout, float top, float height) {
        int[] iArr = new int[2];
        floatingRimEffectView.getLocationInWindow(iArr);
        inputLayout.getLocationInWindow(new int[2]);
        this.mBgBoundData[0] = iArr[0] / this.mScreenSize.x;
        this.mBgBoundData[1] = 1.0f - ((top + height) / this.mScreenSize.y);
        this.mBgBoundData[2] = floatingRimEffectView.getWidth() / this.mScreenSize.x;
        this.mBgBoundData[3] = height / this.mScreenSize.y;
        AiShaderEffectRenderer aiShaderEffectRenderer = this.mAiShaderEffectRenderer;
        if (aiShaderEffectRenderer != null) {
            aiShaderEffectRenderer.setBgBound(this.mBgBoundData);
            aiShaderEffectRenderer.updateMaterial();
            bgGradientColorView.setRenderEffect(aiShaderEffectRenderer.getRenderEffect(8));
        }
    }

    public final void setAiPanelAnimCallback(AiPanelAnimCallback callBack) {
        Intrinsics.checkNotNullParameter(callBack, "callBack");
        this.mAiPanelAnimCallback = callBack;
    }

    public final void release() {
        this.mAiPanelAnimCallback = null;
    }
}
