package com.miui.notes.doodle.feature;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.utils.DisplayUtils;
import com.miui.doodle.utils.RomUtils;
import com.miui.doodle.utils.UIUtils;
import com.miui.notes.R;
import com.miui.support.cardview.CardView;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.property.ViewProperty;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: PadDoodleFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J&\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\tH\u0016J\"\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007H\u0016J\u0006\u0010\u0014\u001a\u00020\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0006\u0010\u0017\u001a\u00020\u0005J\u0006\u0010\u0018\u001a\u00020\u0005J\u0006\u0010\u0019\u001a\u00020\u0005J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J$\u0010\u001d\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#H\u0016¨\u0006$"}, d2 = {"Lcom/miui/notes/doodle/feature/PadDoodleFragment;", "Lcom/miui/notes/doodle/feature/DoodleFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", "bundle", "Landroid/os/Bundle;", "onInflateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "initLayout", "rootView", "initToolPanel", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "updatePadPaletteContainerMargin", "getTransY", "", "updateWindowStatus", "updateToolContainerMargin", "updatePaletteContainerMargin", "updateDoodleBackground", "resId", "", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadDoodleFragment extends DoodleFragment {
    @Override // com.miui.notes.doodle.feature.DoodleFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        updateWindowStatus();
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        if (RomUtils.INSTANCE.isFoldDevice()) {
            return inflater.inflate(R.layout.pad_doodle_layout_full, container, false);
        }
        return inflater.inflate(R.layout.pad_doodle_layout, container, false);
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment
    public void initLayout(View rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        super.initLayout(rootView);
        if (RomUtils.INSTANCE.isFoldDevice()) {
            DisplayUtils displayUtils = DisplayUtils.INSTANCE;
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            int statusBarHeight = displayUtils.getStatusBarHeight(contextRequireContext);
            if (getActionBarContainer() != null) {
                View actionBarContainer = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer);
                View actionBarContainer2 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer2);
                int paddingStart = actionBarContainer2.getPaddingStart();
                View actionBarContainer3 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer3);
                int paddingEnd = actionBarContainer3.getPaddingEnd();
                View actionBarContainer4 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer4);
                actionBarContainer.setPaddingRelative(paddingStart, statusBarHeight, paddingEnd, actionBarContainer4.getPaddingBottom());
            }
            ViewGroup.LayoutParams layoutParams = getBtnUnImmerse().getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
            layoutParams2.topMargin += statusBarHeight;
            getBtnUnImmerse().setLayoutParams(layoutParams2);
            UIUtils uIUtils = UIUtils.INSTANCE;
            Window window = requireActivity().getWindow();
            Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
            uIUtils.setNavigationColor(window, -1);
        }
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment
    public void initToolPanel(Context context, View rootView, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        super.initToolPanel(context, rootView, savedInstanceState);
        if (RomUtils.INSTANCE.isFoldDevice()) {
            updateToolContainerMargin();
            updatePaletteContainerMargin();
            updateBackgroundContainerMargin();
            return;
        }
        updatePadPaletteContainerMargin();
    }

    public final void updatePadPaletteContainerMargin() {
        ViewGroup.LayoutParams layoutParams;
        int i = getScreenSpec().screenMode;
        if (i == 4100 || i == 4103) {
            if (getBrushConfigContainer() != null) {
                View brushConfigContainer = getBrushConfigContainer();
                layoutParams = brushConfigContainer != null ? brushConfigContainer.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_float));
                marginLayoutParams.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_float));
                View brushConfigContainer2 = getBrushConfigContainer();
                if (brushConfigContainer2 != null) {
                    brushConfigContainer2.setLayoutParams(marginLayoutParams);
                    return;
                }
                return;
            }
            return;
        }
        if (getBrushConfigContainer() != null) {
            View brushConfigContainer3 = getBrushConfigContainer();
            layoutParams = brushConfigContainer3 != null ? brushConfigContainer3.getLayoutParams() : null;
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams2.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
            marginLayoutParams2.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
            View brushConfigContainer4 = getBrushConfigContainer();
            if (brushConfigContainer4 != null) {
                brushConfigContainer4.setLayoutParams(marginLayoutParams2);
            }
        }
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment
    public float getTransY() {
        if (!isAdded()) {
            return 0.0f;
        }
        if (RomUtils.INSTANCE.isFoldDevice()) {
            return requireContext().getResources().getDimension(R.dimen.tool_select_tran_y_full);
        }
        return requireContext().getResources().getDimension(R.dimen.tool_select_tran_y);
    }

    public final void updateWindowStatus() {
        requireActivity().getWindow().addFlags(67108864);
        boolean zIsInMultiWindowMode = UIUtils.INSTANCE.isInMultiWindowMode(requireActivity());
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        boolean zIsNightMode = uIUtils.isNightMode(contextRequireContext);
        UIUtils uIUtils2 = UIUtils.INSTANCE;
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
        boolean zIsInFullWindowGestureMode = uIUtils2.isInFullWindowGestureMode(contextRequireContext2);
        if (zIsNightMode) {
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(256);
            if (zIsInMultiWindowMode) {
                UIUtils uIUtils3 = UIUtils.INSTANCE;
                Window window = requireActivity().getWindow();
                Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
                uIUtils3.setNavigationColor(window, 0);
                requireActivity().getWindow().clearFlags(DataSpace.RANGE_FULL);
                return;
            }
            if (!zIsInFullWindowGestureMode) {
                UIUtils uIUtils4 = UIUtils.INSTANCE;
                Window window2 = requireActivity().getWindow();
                Intrinsics.checkNotNullExpressionValue(window2, "getWindow(...)");
                uIUtils4.setNavigationColor(window2, -16777216);
                requireActivity().getWindow().clearFlags(DataSpace.RANGE_FULL);
                return;
            }
            UIUtils uIUtils5 = UIUtils.INSTANCE;
            Window window3 = requireActivity().getWindow();
            Intrinsics.checkNotNullExpressionValue(window3, "getWindow(...)");
            uIUtils5.setNavigationColor(window3, 0);
            requireActivity().getWindow().addFlags(DataSpace.RANGE_FULL);
            return;
        }
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(R2.dimen.miuix_appcompat_pairing_dialog_closable_icon_width);
        if (zIsInMultiWindowMode) {
            UIUtils uIUtils6 = UIUtils.INSTANCE;
            Window window4 = requireActivity().getWindow();
            Intrinsics.checkNotNullExpressionValue(window4, "getWindow(...)");
            uIUtils6.setNavigationColor(window4, 0);
            requireActivity().getWindow().clearFlags(DataSpace.RANGE_FULL);
            return;
        }
        if (!zIsInFullWindowGestureMode) {
            UIUtils uIUtils7 = UIUtils.INSTANCE;
            Window window5 = requireActivity().getWindow();
            Intrinsics.checkNotNullExpressionValue(window5, "getWindow(...)");
            uIUtils7.setNavigationColor(window5, -1);
            requireActivity().getWindow().clearFlags(DataSpace.RANGE_FULL);
            return;
        }
        UIUtils uIUtils8 = UIUtils.INSTANCE;
        Window window6 = requireActivity().getWindow();
        Intrinsics.checkNotNullExpressionValue(window6, "getWindow(...)");
        uIUtils8.setNavigationColor(window6, 0);
        requireActivity().getWindow().addFlags(DataSpace.RANGE_FULL);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0063  */
    public final void updateToolContainerMargin() {
        int dimensionPixelSize;
        if (!isAdded() || getColorView() == null || getBtnPanelImmerse() == null) {
            return;
        }
        View colorView = getColorView();
        ViewGroup.LayoutParams layoutParams = colorView != null ? colorView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        CardView btnPanelImmerse = getBtnPanelImmerse();
        ViewGroup.LayoutParams layoutParams2 = btnPanelImmerse != null ? btnPanelImmerse.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
        if (RomUtils.INSTANCE.isFoldDevice()) {
            UIUtils uIUtils = UIUtils.INSTANCE;
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            if (uIUtils.isInNarrowScreen(contextRequireContext)) {
                dimensionPixelSize = requireContext().getResources().getDimensionPixelSize(R.dimen.palette_panel_margin_start_narrow);
            } else {
                dimensionPixelSize = requireContext().getResources().getDimensionPixelSize(R.dimen.palette_panel_margin_start_full);
            }
        } else {
            dimensionPixelSize = requireContext().getResources().getDimensionPixelSize(R.dimen.palette_panel_margin_start_full);
        }
        marginLayoutParams.setMarginStart(dimensionPixelSize);
        marginLayoutParams.setMarginEnd(dimensionPixelSize);
        marginLayoutParams2.setMarginStart(dimensionPixelSize);
        marginLayoutParams2.setMarginEnd(dimensionPixelSize);
        View colorView2 = getColorView();
        if (colorView2 != null) {
            colorView2.setLayoutParams(marginLayoutParams);
        }
        CardView btnPanelImmerse2 = getBtnPanelImmerse();
        if (btnPanelImmerse2 != null) {
            btnPanelImmerse2.setLayoutParams(marginLayoutParams2);
        }
    }

    public final void updatePaletteContainerMargin() {
        ViewGroup.LayoutParams layoutParams;
        if (isAdded()) {
            if (getScreenSpec().type == 1) {
                if (getPaletteContainer() != null) {
                    View paletteContainer = getPaletteContainer();
                    ViewGroup.LayoutParams layoutParams2 = paletteContainer != null ? paletteContainer.getLayoutParams() : null;
                    Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                    marginLayoutParams.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
                    marginLayoutParams.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
                    View paletteContainer2 = getPaletteContainer();
                    if (paletteContainer2 != null) {
                        paletteContainer2.setLayoutParams(marginLayoutParams);
                    }
                }
                if (getBrushConfigContainer() != null) {
                    View brushConfigContainer = getBrushConfigContainer();
                    ViewGroup.LayoutParams layoutParams3 = brushConfigContainer != null ? brushConfigContainer.getLayoutParams() : null;
                    Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams3;
                    marginLayoutParams2.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
                    marginLayoutParams2.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_narrow));
                    View brushConfigContainer2 = getBrushConfigContainer();
                    if (brushConfigContainer2 != null) {
                        brushConfigContainer2.setLayoutParams(marginLayoutParams2);
                    }
                }
                if (getActionBarContainer() != null) {
                    int dimensionPixelSize = requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_margin_start_narrow);
                    int dimensionPixelSize2 = requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_margin_end_narrow);
                    View actionBarContainer = getActionBarContainer();
                    Intrinsics.checkNotNull(actionBarContainer);
                    View actionBarContainer2 = getActionBarContainer();
                    Intrinsics.checkNotNull(actionBarContainer2);
                    int paddingTop = actionBarContainer2.getPaddingTop();
                    View actionBarContainer3 = getActionBarContainer();
                    Intrinsics.checkNotNull(actionBarContainer3);
                    actionBarContainer.setPaddingRelative(dimensionPixelSize, paddingTop, dimensionPixelSize2, actionBarContainer3.getPaddingBottom());
                    View btnRedo = getBtnRedo();
                    ViewGroup.LayoutParams layoutParams4 = btnRedo != null ? btnRedo.getLayoutParams() : null;
                    Intrinsics.checkNotNull(layoutParams4, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams4;
                    marginLayoutParams3.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_btn_margin_narrow));
                    View btnRedo2 = getBtnRedo();
                    if (btnRedo2 != null) {
                        btnRedo2.setLayoutParams(marginLayoutParams3);
                    }
                    View btnImmerse = getBtnImmerse();
                    layoutParams = btnImmerse != null ? btnImmerse.getLayoutParams() : null;
                    Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginLayoutParams4.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_btn_margin_narrow));
                    View btnImmerse2 = getBtnImmerse();
                    if (btnImmerse2 != null) {
                        btnImmerse2.setLayoutParams(marginLayoutParams4);
                        return;
                    }
                    return;
                }
                return;
            }
            if (getPaletteContainer() != null) {
                View paletteContainer3 = getPaletteContainer();
                ViewGroup.LayoutParams layoutParams5 = paletteContainer3 != null ? paletteContainer3.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                ViewGroup.MarginLayoutParams marginLayoutParams5 = (ViewGroup.MarginLayoutParams) layoutParams5;
                marginLayoutParams5.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_full));
                marginLayoutParams5.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_panel_margin_full));
                View paletteContainer4 = getPaletteContainer();
                if (paletteContainer4 != null) {
                    paletteContainer4.setLayoutParams(marginLayoutParams5);
                }
            }
            if (getBrushConfigContainer() != null) {
                View brushConfigContainer3 = getBrushConfigContainer();
                ViewGroup.LayoutParams layoutParams6 = brushConfigContainer3 != null ? brushConfigContainer3.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams6, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                ViewGroup.MarginLayoutParams marginLayoutParams6 = (ViewGroup.MarginLayoutParams) layoutParams6;
                marginLayoutParams6.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_brush_panel_margin_full));
                marginLayoutParams6.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.pad_brush_panel_margin_full));
                View brushConfigContainer4 = getBrushConfigContainer();
                if (brushConfigContainer4 != null) {
                    brushConfigContainer4.setLayoutParams(marginLayoutParams6);
                }
            }
            if (getActionBarContainer() != null) {
                int dimensionPixelSize3 = requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_margin_start_large);
                int dimensionPixelSize4 = requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_margin_end_large);
                View actionBarContainer4 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer4);
                View actionBarContainer5 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer5);
                int paddingTop2 = actionBarContainer5.getPaddingTop();
                View actionBarContainer6 = getActionBarContainer();
                Intrinsics.checkNotNull(actionBarContainer6);
                actionBarContainer4.setPaddingRelative(dimensionPixelSize3, paddingTop2, dimensionPixelSize4, actionBarContainer6.getPaddingBottom());
                View btnRedo3 = getBtnRedo();
                ViewGroup.LayoutParams layoutParams7 = btnRedo3 != null ? btnRedo3.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams7, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                ViewGroup.MarginLayoutParams marginLayoutParams7 = (ViewGroup.MarginLayoutParams) layoutParams7;
                marginLayoutParams7.setMarginStart(requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_btn_margin_large));
                View btnRedo4 = getBtnRedo();
                if (btnRedo4 != null) {
                    btnRedo4.setLayoutParams(marginLayoutParams7);
                }
                View btnImmerse3 = getBtnImmerse();
                layoutParams = btnImmerse3 != null ? btnImmerse3.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                ViewGroup.MarginLayoutParams marginLayoutParams8 = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams8.setMarginEnd(requireContext().getResources().getDimensionPixelSize(R.dimen.tool_bar_panel_btn_margin_large));
                View btnImmerse4 = getBtnImmerse();
                if (btnImmerse4 != null) {
                    btnImmerse4.setLayoutParams(marginLayoutParams8);
                }
            }
        }
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment
    public void updateDoodleBackground(int resId) {
        getDoodleContainer().setBackgroundResource(resId);
    }

    @Override // com.miui.notes.doodle.feature.DoodleFragment, miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        if (screenSpec != null) {
            setScreenSpec(screenSpec);
            if (isAdded()) {
                DisplayUtils displayUtils = DisplayUtils.INSTANCE;
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                int realWidth = displayUtils.getRealWidth(contextRequireContext);
                boolean z = realWidth != getDisplayWidth();
                setDisplayWidth(realWidth);
                if (RomUtils.INSTANCE.isFoldDevice()) {
                    updatePaletteContainerMargin();
                    updateToolContainerMargin();
                    updateBackgroundContainerMargin();
                    if (z) {
                        reloadDoodleOnWindowChanged();
                        return;
                    }
                    return;
                }
                updatePadPaletteContainerMargin();
                if (getActionBarContainer() != null) {
                    if (getScreenSpec().screenMode == 4097) {
                        IStateStyle iStateStyleState = Folme.useAt(getActionBarContainer()).state();
                        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
                        UIUtils uIUtils = UIUtils.INSTANCE;
                        Context contextRequireContext2 = requireContext();
                        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
                        iStateStyleState.to(viewProperty, Float.valueOf(uIUtils.dip2px(contextRequireContext2, 13.0f)));
                    } else if (getScreenSpec().screenMode == 4098 && getResources().getConfiguration().orientation == 1) {
                        IStateStyle iStateStyleState2 = Folme.useAt(getActionBarContainer()).state();
                        ViewProperty viewProperty2 = ViewProperty.TRANSLATION_Y;
                        UIUtils uIUtils2 = UIUtils.INSTANCE;
                        Context contextRequireContext3 = requireContext();
                        Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext(...)");
                        iStateStyleState2.to(viewProperty2, Float.valueOf(uIUtils2.dip2px(contextRequireContext3, 13.0f)));
                    } else {
                        Folme.useAt(getActionBarContainer()).state().to(ViewProperty.TRANSLATION_Y, 0);
                    }
                }
                updateWindowStatus();
            }
        }
    }
}
