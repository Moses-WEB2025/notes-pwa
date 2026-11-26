package com.miui.common.tool;

import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.util.HapticFeedbackCompat;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: compiled from: HapticFeedbackTool.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u0011\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\u0012\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0006\u0010\u0013\u001a\u00020\u0007J\u0016\u0010\u0014\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\u0018\u001a\u00020\u0007J\u001a\u0010\u001b\u001a\u00020\f2\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001c\u001a\u00020\u0016H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lcom/miui/common/tool/HapticFeedbackTool;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNewHapticFeedbackUtil", "Lmiuix/util/HapticFeedbackCompat;", "performListItemClick", "", "view", "Landroid/view/View;", "performDialogButtonClick", "isPositive", "", "performTouchUp", "performHold", "performMoveToFolder", "performSwapItem", "performFloatWindowOpen", "performFinishOneTodo", "performFinishAllTodo", "performHapticByType", "hapticType", "", "performHapticLongPress", "release", "isSupportLinearMotorVibrate", "()Z", "doesSupportHaptic", "id", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HapticFeedbackTool {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static HapticFeedbackTool mInstance;
    private final HapticFeedbackCompat mNewHapticFeedbackUtil;

    public /* synthetic */ HapticFeedbackTool(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final HapticFeedbackTool getInstance() {
        return INSTANCE.getInstance();
    }

    private HapticFeedbackTool() {
        this.mNewHapticFeedbackUtil = new HapticFeedbackCompat(BaseNoteApp.INSTANCE.getInstance());
    }

    public final void performListItemClick(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_BUTTON_SMALL, HapticFeedbackConstants.MIUI_TAP_LIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performDialogButtonClick(View view, boolean isPositive) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (HapticCompat.supportLinearMotor(HapticFeedbackConstants.MIUI_TAP_NORMAL)) {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_TAP_NORMAL);
            } else {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_TAP_LIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performTouchUp(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (doesSupportHaptic(view, HapticFeedbackConstants.MIUI_BOUNDARY_SPATIAL)) {
                return;
            }
            if (HapticCompat.supportLinearMotor(HapticFeedbackConstants.MIUI_MESH_LIGHT)) {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_LIGHT);
            } else {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_LONG_PRESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performHold(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_HOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performMoveToFolder(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (isSupportLinearMotorVibrate()) {
                if (this.mNewHapticFeedbackUtil.isSupportExtHapticFeedback(R2.array.ai_select_partial_button_colors)) {
                    this.mNewHapticFeedbackUtil.m4364x85658b2f(R2.array.ai_select_partial_button_colors);
                    return;
                }
                return;
            }
            HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_HOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performSwapItem(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (doesSupportHaptic(view, HapticFeedbackConstants.MIUI_BOUNDARY_SPATIAL)) {
                return;
            }
            if (HapticCompat.supportLinearMotor(HapticFeedbackConstants.MIUI_MESH_NORMAL)) {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_NORMAL);
            } else {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_TAP_LIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performFloatWindowOpen(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (doesSupportHaptic(view, HapticFeedbackConstants.MIUI_BOUNDARY_SPATIAL)) {
                return;
            }
            if (HapticCompat.supportLinearMotor(HapticFeedbackConstants.MIUI_MESH_HEAVY)) {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_HEAVY);
            } else {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_TAP_LIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performFinishOneTodo(View view) {
        try {
            if (!doesSupportHaptic(null, 206) && isSupportLinearMotorVibrate() && this.mNewHapticFeedbackUtil.isSupportExtHapticFeedback(R2.array.ai_send_button_gradient_border_colors)) {
                this.mNewHapticFeedbackUtil.m4364x85658b2f(R2.array.ai_send_button_gradient_border_colors);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performFinishAllTodo() {
        try {
            if (!doesSupportHaptic(null, 205) && isSupportLinearMotorVibrate() && this.mNewHapticFeedbackUtil.isSupportExtHapticFeedback(R2.array.ai_shader_panel_rim_colors)) {
                this.mNewHapticFeedbackUtil.m4364x85658b2f(R2.array.ai_shader_panel_rim_colors);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performHapticByType(View view, int hapticType) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (!doesSupportHaptic(view, HapticFeedbackConstants.MIUI_BUTTON_SMALL) && isSupportLinearMotorVibrate() && HapticCompat.supportLinearMotor(hapticType)) {
                HapticCompat.performHapticFeedback(view, hapticType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void performHapticLongPress(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        try {
            if (doesSupportHaptic(view, HapticFeedbackConstants.MIUI_BOUNDARY_TIME)) {
                return;
            }
            view.performHapticFeedback(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void release() {
        this.mNewHapticFeedbackUtil.stop();
    }

    private final boolean isSupportLinearMotorVibrate() {
        return this.mNewHapticFeedbackUtil.supportLinearMotor();
    }

    private final boolean doesSupportHaptic(View view, int id) {
        if (!HapticCompat.doesSupportHaptic("2.0")) {
            return false;
        }
        if (view != null) {
            HapticCompat.performHapticFeedback(view, id);
            return true;
        }
        this.mNewHapticFeedbackUtil.m4364x85658b2f(id);
        return true;
    }

    /* JADX INFO: compiled from: HapticFeedbackTool.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/miui/common/tool/HapticFeedbackTool$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mInstance", "Lcom/miui/common/tool/HapticFeedbackTool;", "instance", "getInstance$annotations", "getInstance", "()Lcom/miui/common/tool/HapticFeedbackTool;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final HapticFeedbackTool getInstance() {
            if (HapticFeedbackTool.mInstance == null) {
                HapticFeedbackTool.mInstance = new HapticFeedbackTool(null);
            }
            HapticFeedbackTool hapticFeedbackTool = HapticFeedbackTool.mInstance;
            Intrinsics.checkNotNull(hapticFeedbackTool);
            return hapticFeedbackTool;
        }
    }
}
