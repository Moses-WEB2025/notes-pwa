package com.miui.notes.ai.ui;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.ai.Action;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.internal.widget.DialogRootView;
import miuix.autodensity.AutoDensityConfig;
import miuix.autodensity.AutoDensityContextWrapper;
import miuix.autodensity.DensityUtil;
import miuix.core.util.EnvStateManager;

/* JADX INFO: compiled from: FloatingDecorView.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 32\u00020\u0001:\u00040123B\u0013\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0004\u0010\u000bJ\u0010\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\b\u0010\u001d\u001a\u00020\u001cH\u0014J\b\u0010\u001e\u001a\u00020\u001cH\u0014J\u0010\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$H\u0014J0\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\r2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\nH\u0014J\u0010\u0010+\u001a\u00020\u001c2\b\u0010,\u001a\u0004\u0018\u00010\u0010J\u0012\u0010-\u001a\u00020\u001c2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u0010.\u001a\u00020\u001c2\b\u0010/\u001a\u0004\u0018\u00010\u001aR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lcom/miui/notes/ai/ui/FloatingDecorView;", "Landroid/widget/RelativeLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mNotifyConfigChanged", "", "mViewConfigChangedDispatched", "mCallback", "Lmiuix/appcompat/internal/widget/DialogRootView$ConfigurationChangedCallback;", "lastOri", "currentOri", "lastNarrow", "currentNarrow", "mComponentCallbacks", "Landroid/content/ComponentCallbacks;", "recyclingWindowAction", "Lcom/miui/notes/ai/Action;", "onScreenChangeListener", "Lcom/miui/notes/ai/ui/FloatingDecorView$OnScreenChangeListener;", "setRecyclingWindowAction", "", "onAttachedToWindow", "onDetachedFromWindow", "dispatchKeyEvent", "event", "Landroid/view/KeyEvent;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onLayout", "changed", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "setConfigurationChangedCallback", CallMethod.ARG_CALLBACK, "dispatchConfigurationChanged", "setOnScreenChangeListener", "onChangeListener", "ConfigurationChangedCallback", "RootViewSizeChangedCallback", "OnScreenChangeListener", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatingDecorView extends RelativeLayout {
    private static final String TAG = "FloatingDecorView";
    private int currentNarrow;
    private int currentOri;
    private int lastNarrow;
    private int lastOri;
    private DialogRootView.ConfigurationChangedCallback mCallback;
    private final ComponentCallbacks mComponentCallbacks;
    private boolean mNotifyConfigChanged;
    private boolean mViewConfigChangedDispatched;
    private OnScreenChangeListener onScreenChangeListener;
    private Action recyclingWindowAction;

    /* JADX INFO: compiled from: FloatingDecorView.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J2\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007H&¨\u0006\u000bÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/ui/FloatingDecorView$ConfigurationChangedCallback;", "", "onConfigurationChanged", "", "newConfig", "Landroid/content/res/Configuration;", "left", "", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface ConfigurationChangedCallback {
        void onConfigurationChanged(Configuration newConfig, int left, int top, int right, int bottom);
    }

    /* JADX INFO: compiled from: FloatingDecorView.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/ui/FloatingDecorView$OnScreenChangeListener;", "", "setOnScreenChange", "", "isLandScape", "", "onConfigChange", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnScreenChangeListener {
        void onConfigChange();

        void setOnScreenChange(boolean isLandScape);
    }

    /* JADX INFO: compiled from: FloatingDecorView.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H&¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/ui/FloatingDecorView$RootViewSizeChangedCallback;", "", "onSizeChanged", "", AnimatedProperty.PROPERTY_NAME_W, "", AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RootViewSizeChangedCallback {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }

    public FloatingDecorView(Context context) {
        super(context);
        this.mComponentCallbacks = new ComponentCallbacks() { // from class: com.miui.notes.ai.ui.FloatingDecorView$mComponentCallbacks$1
            @Override // android.content.ComponentCallbacks
            public void onLowMemory() {
            }

            @Override // android.content.ComponentCallbacks
            public void onConfigurationChanged(Configuration newConfig) {
                Intrinsics.checkNotNullParameter(newConfig, "newConfig");
                this.this$0.mNotifyConfigChanged = true;
                this.this$0.onConfigurationChanged(newConfig);
                if (this.this$0.mViewConfigChangedDispatched && Intrinsics.areEqual(Looper.myLooper(), this.this$0.getHandler().getLooper())) {
                    this.this$0.requestLayout();
                    return;
                }
                FloatingDecorView floatingDecorView = this.this$0;
                final FloatingDecorView floatingDecorView2 = this.this$0;
                floatingDecorView.post(new Runnable() { // from class: com.miui.notes.ai.ui.FloatingDecorView$mComponentCallbacks$1$onConfigurationChanged$1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (floatingDecorView2.mNotifyConfigChanged && floatingDecorView2.isAttachedToWindow()) {
                            floatingDecorView2.requestLayout();
                        }
                    }
                });
            }
        };
        boolean zIsLandScape = DisplayUtil.isLandScape(context);
        this.lastOri = zIsLandScape ? 1 : 0;
        this.currentOri = zIsLandScape ? 1 : 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public FloatingDecorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        boolean zIsLandScape = DisplayUtil.isLandScape(context);
        this.lastOri = zIsLandScape ? 1 : 0;
        this.currentOri = zIsLandScape ? 1 : 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public FloatingDecorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mComponentCallbacks = new ComponentCallbacks() { // from class: com.miui.notes.ai.ui.FloatingDecorView$mComponentCallbacks$1
            @Override // android.content.ComponentCallbacks
            public void onLowMemory() {
            }

            @Override // android.content.ComponentCallbacks
            public void onConfigurationChanged(Configuration newConfig) {
                Intrinsics.checkNotNullParameter(newConfig, "newConfig");
                this.this$0.mNotifyConfigChanged = true;
                this.this$0.onConfigurationChanged(newConfig);
                if (this.this$0.mViewConfigChangedDispatched && Intrinsics.areEqual(Looper.myLooper(), this.this$0.getHandler().getLooper())) {
                    this.this$0.requestLayout();
                    return;
                }
                FloatingDecorView floatingDecorView = this.this$0;
                final FloatingDecorView floatingDecorView2 = this.this$0;
                floatingDecorView.post(new Runnable() { // from class: com.miui.notes.ai.ui.FloatingDecorView$mComponentCallbacks$1$onConfigurationChanged$1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (floatingDecorView2.mNotifyConfigChanged && floatingDecorView2.isAttachedToWindow()) {
                            floatingDecorView2.requestLayout();
                        }
                    }
                });
            }
        };
        boolean zIsLandScape = DisplayUtil.isLandScape(context);
        this.lastOri = zIsLandScape ? 1 : 0;
        this.currentOri = zIsLandScape ? 1 : 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public final void setRecyclingWindowAction(Action recyclingWindowAction) {
        this.recyclingWindowAction = recyclingWindowAction;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerComponentCallbacks(this.mComponentCallbacks);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterComponentCallbacks(this.mComponentCallbacks);
        AutoDensityContextWrapper autoDensityContextWrapperFindAutoDensityContextWrapper = DensityUtil.findAutoDensityContextWrapper(getContext());
        if (autoDensityContextWrapperFindAutoDensityContextWrapper != null) {
            autoDensityContextWrapperFindAutoDensityContextWrapper.restoreOriginConfig();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        Action action;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getKeyCode() == 4 && event.getAction() == 1 && (action = this.recyclingWindowAction) != null) {
            Intrinsics.checkNotNull(action);
            action.run();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        Configuration originConfiguration;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        this.lastOri = this.currentOri;
        this.currentOri = DisplayUtil.isLandScape(getContext()) ? 1 : 0;
        if (RomUtils.isFoldDevice()) {
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(getContext()) ? 1 : 0;
        }
        Logger.INSTANCE.i(TAG, "onConfigurationChanged from view isInNarrowScreen " + ScreenPosistionUtils.isInNarrowScreen(getContext()) + " newConfig " + newConfig + " ori " + newConfig.orientation);
        AutoDensityContextWrapper autoDensityContextWrapperFindAutoDensityContextWrapper = DensityUtil.findAutoDensityContextWrapper(getContext());
        if (autoDensityContextWrapperFindAutoDensityContextWrapper != null && (originConfiguration = autoDensityContextWrapperFindAutoDensityContextWrapper.getOriginConfiguration()) != null) {
            originConfiguration.setTo(newConfig);
        }
        if (!this.mNotifyConfigChanged) {
            AutoDensityConfig.updateDensityOverrideConfiguration(getContext(), newConfig);
        }
        if (this.onScreenChangeListener != null) {
            if (newConfig.orientation == 2) {
                if (this.currentOri != this.lastOri) {
                    OnScreenChangeListener onScreenChangeListener = this.onScreenChangeListener;
                    Intrinsics.checkNotNull(onScreenChangeListener);
                    onScreenChangeListener.setOnScreenChange(true);
                } else if (RomUtils.isFoldDevice() && this.currentNarrow != this.lastNarrow) {
                    OnScreenChangeListener onScreenChangeListener2 = this.onScreenChangeListener;
                    Intrinsics.checkNotNull(onScreenChangeListener2);
                    onScreenChangeListener2.setOnScreenChange(true);
                }
            } else if (newConfig.orientation == 1) {
                if (this.currentOri != this.lastOri) {
                    OnScreenChangeListener onScreenChangeListener3 = this.onScreenChangeListener;
                    Intrinsics.checkNotNull(onScreenChangeListener3);
                    onScreenChangeListener3.setOnScreenChange(false);
                } else if (RomUtils.isFoldDevice() && this.currentNarrow != this.lastNarrow) {
                    OnScreenChangeListener onScreenChangeListener4 = this.onScreenChangeListener;
                    Intrinsics.checkNotNull(onScreenChangeListener4);
                    onScreenChangeListener4.setOnScreenChange(false);
                }
            }
            OnScreenChangeListener onScreenChangeListener5 = this.onScreenChangeListener;
            if (onScreenChangeListener5 != null) {
                Intrinsics.checkNotNull(onScreenChangeListener5);
                onScreenChangeListener5.onConfigChange();
            }
        }
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mNotifyConfigChanged) {
            this.mViewConfigChangedDispatched = false;
            this.mNotifyConfigChanged = false;
            Configuration configuration = getResources().getConfiguration();
            final AutoDensityContextWrapper autoDensityContextWrapperFindAutoDensityContextWrapper = DensityUtil.findAutoDensityContextWrapper(getContext());
            if (autoDensityContextWrapperFindAutoDensityContextWrapper != null) {
                autoDensityContextWrapperFindAutoDensityContextWrapper.getOriginConfiguration().setTo(configuration);
                AutoDensityConfig.updateDensityOverrideConfiguration(getContext(), configuration);
            }
            final int i = configuration.screenWidthDp;
            final int i2 = configuration.screenHeightDp;
            DialogRootView.ConfigurationChangedCallback configurationChangedCallback = this.mCallback;
            if (configurationChangedCallback != null) {
                Intrinsics.checkNotNull(configurationChangedCallback);
                configurationChangedCallback.onConfigurationChanged(getResources().getConfiguration(), left, top, right, bottom);
            }
            post(new Runnable() { // from class: com.miui.notes.ai.ui.FloatingDecorView.onLayout.1
                @Override // java.lang.Runnable
                public void run() {
                    Configuration configuration2 = FloatingDecorView.this.getResources().getConfiguration();
                    if (autoDensityContextWrapperFindAutoDensityContextWrapper != null) {
                        configuration2 = AutoDensityConfig.updateDensityOverrideConfiguration(FloatingDecorView.this.getContext(), configuration2);
                        Intrinsics.checkNotNullExpressionValue(configuration2, "updateDensityOverrideConfiguration(...)");
                    } else {
                        Intrinsics.checkNotNull(configuration2);
                    }
                    Configuration configuration3 = configuration2;
                    if (configuration3.screenWidthDp == i && configuration3.screenHeightDp == i2) {
                        return;
                    }
                    if (autoDensityContextWrapperFindAutoDensityContextWrapper != null) {
                        EnvStateManager.markWindowInfoDirty(FloatingDecorView.this.getContext());
                    }
                    if (FloatingDecorView.this.mCallback != null) {
                        DialogRootView.ConfigurationChangedCallback configurationChangedCallback2 = FloatingDecorView.this.mCallback;
                        Intrinsics.checkNotNull(configurationChangedCallback2);
                        configurationChangedCallback2.onConfigurationChanged(configuration3, left, top, right, bottom);
                    }
                }
            });
        }
    }

    public final void setConfigurationChangedCallback(DialogRootView.ConfigurationChangedCallback callback) {
        this.mCallback = callback;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchConfigurationChanged(Configuration newConfig) {
        this.mViewConfigChangedDispatched = true;
        super.dispatchConfigurationChanged(newConfig);
    }

    public final void setOnScreenChangeListener(OnScreenChangeListener onChangeListener) {
        this.onScreenChangeListener = onChangeListener;
    }
}
