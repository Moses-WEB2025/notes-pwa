package com.miui.doodle.feature.color;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.utils.EaseManager;

/* JADX INFO: compiled from: PaletteComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 42\u00020\u0001:\u00014B#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020#H\u0016J\u0010\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020\u0007H\u0016J\u0010\u0010'\u001a\u00020#2\u0006\u0010&\u001a\u00020\u0007H\u0016J\u0012\u0010(\u001a\u00020#2\b\u0010)\u001a\u0004\u0018\u00010\u0012H\u0016J\u0012\u0010*\u001a\u00020#2\b\u0010)\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010+\u001a\u00020#2\u0006\u0010&\u001a\u00020\u0007H\u0002J\u0010\u0010.\u001a\u00020\u000b2\u0006\u0010/\u001a\u00020\u0007H\u0002J\b\u00100\u001a\u00020#H\u0016J\u0016\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010 \u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001f@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010,\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\r¨\u00065"}, d2 = {"Lcom/miui/doodle/feature/color/PaletteComponent;", "Lcom/miui/doodle/feature/color/IPalette;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "parentView", "Landroid/view/View;", "mColor", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/view/View;I)V", "pColorTable", "", "getPColorTable", "()[I", "setPColorTable", "([I)V", "mColorPickListeners", "", "Lcom/miui/doodle/feature/color/ColorPickListener;", "mPage", "mIndexInPage", "mColorGroupPicker", "Lcom/miui/doodle/feature/color/IColorGroupPicker;", "mColorItemPicker", "Lcom/miui/doodle/feature/color/IColorItemInPagePicker;", "getMColorItemPicker", "()Lcom/miui/doodle/feature/color/IColorItemInPagePicker;", "container", "containerAnimator", "Landroid/view/ViewPropertyAnimator;", "value", "", "isShow", "()Z", "show", "", "dismiss", "setColor", "colorInt", "pickColor", "addPickListener", "listener", "removePickListener", "selectColorItem", "colorGroupTable", "getColorGroupTable", "getColorTableByGroup", "groupIndex", "clearViewAnimator", "getColorName", "", "index", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PaletteComponent implements IPalette {
    private static final int ITEMS_OF_PAGE = 7;
    private static final int PAGES = 13;
    private View container;
    private ViewPropertyAnimator containerAnimator;
    private boolean isShow;
    private final IColorGroupPicker mColorGroupPicker;
    private final IColorItemInPagePicker mColorItemPicker;
    private final List<ColorPickListener> mColorPickListeners;
    private int mIndexInPage;
    private int mPage;
    private int[] pColorTable;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PaletteComponent(Context context, View parentView) {
        this(context, parentView, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parentView, "parentView");
    }

    public PaletteComponent(final Context context, View parentView, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parentView, "parentView");
        this.pColorTable = context.getResources().getIntArray(R.array.table_default);
        this.mColorPickListeners = new ArrayList();
        selectColorItem(i);
        View viewFindViewById = parentView.findViewById(R.id.color_items_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mColorItemPicker = new ColorItemInPagePicker((ViewGroup) viewFindViewById, getColorTableByGroup(this.mPage), this.mIndexInPage, new ColorPickListener() { // from class: com.miui.doodle.feature.color.PaletteComponent.1
            @Override // com.miui.doodle.feature.color.ColorPickListener
            public void onColorPick(int colorInt) {
                PaletteComponent.this.selectColorItem(colorInt);
                PaletteComponent.this.pickColor(colorInt);
            }
        });
        ColorGroupPicker colorGroupPicker = new ColorGroupPicker(parentView, context, getColorGroupTable(), this.mPage, new ColorGroupPickListener() { // from class: com.miui.doodle.feature.color.PaletteComponent.2
            @Override // com.miui.doodle.feature.color.ColorGroupPickListener
            public void onGroupPick(int groupIndex) {
                ((ColorItemInPagePicker) PaletteComponent.this.getMColorItemPicker()).selectData(PaletteComponent.this.getColorTableByGroup(groupIndex));
            }

            @Override // com.miui.doodle.feature.color.ColorGroupPickListener
            public void onGroupPickEnd(int groupIndex) {
                AccessibilityManager accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(context, AccessibilityManager.class);
                Intrinsics.checkNotNull(accessibilityManager);
                if (accessibilityManager.isTouchExplorationEnabled()) {
                    Context context2 = context;
                    Toast.makeText(context2, context2.getString(R.string.doodle_color_pick_talkback, PaletteComponent.this.getColorName(groupIndex, context)), 0).show();
                }
            }
        });
        this.mColorGroupPicker = colorGroupPicker;
        this.container = colorGroupPicker.getView();
    }

    public /* synthetic */ PaletteComponent(Context context, View view, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, view, (i2 & 4) != 0 ? -16777216 : i);
    }

    public final int[] getPColorTable() {
        return this.pColorTable;
    }

    public final void setPColorTable(int[] iArr) {
        this.pColorTable = iArr;
    }

    public final IColorItemInPagePicker getMColorItemPicker() {
        return this.mColorItemPicker;
    }

    @Override // com.miui.doodle.feature.color.IPalette
    /* JADX INFO: renamed from: isShow, reason: from getter */
    public boolean getIsShow() {
        return this.isShow;
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void show() {
        this.isShow = true;
        View view = this.container;
        view.setAlpha(0.0f);
        view.setScaleX(0.7f);
        view.setScaleY(0.7f);
        view.setVisibility(0);
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.containerAnimator = viewPropertyAnimatorAnimate;
        if (viewPropertyAnimatorAnimate != null) {
            viewPropertyAnimatorAnimate.alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(200L).setInterpolator(new EaseManager.SpringInterpolator()).setListener(null).start();
        }
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void dismiss() {
        this.isShow = false;
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.container.animate();
        this.containerAnimator = viewPropertyAnimatorAnimate;
        if (viewPropertyAnimatorAnimate != null) {
            viewPropertyAnimatorAnimate.alpha(0.0f).scaleX(0.7f).scaleY(0.7f).setDuration(200L).setInterpolator(new EaseManager.SpringInterpolator()).setListener(new Animator.AnimatorListener() { // from class: com.miui.doodle.feature.color.PaletteComponent$dismiss$1$1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                    this.this$0.container.setVisibility(8);
                }
            }).start();
        }
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void setColor(int colorInt) {
        selectColorItem(colorInt);
        this.mColorGroupPicker.setGroup(this.mPage);
        this.mColorItemPicker.setData(getColorTableByGroup(this.mPage));
        this.mColorItemPicker.setColor(this.mIndexInPage);
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void pickColor(int colorInt) {
        if (this.mColorPickListeners.size() > 0) {
            for (ColorPickListener colorPickListener : this.mColorPickListeners) {
                Intrinsics.checkNotNull(colorPickListener);
                colorPickListener.onColorPick(colorInt);
            }
        }
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void addPickListener(ColorPickListener listener) {
        this.mColorPickListeners.add(listener);
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void removePickListener(ColorPickListener listener) {
        this.mColorPickListeners.remove(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectColorItem(int colorInt) {
        int[] iArr = this.pColorTable;
        Intrinsics.checkNotNull(iArr);
        int length = iArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr2 = this.pColorTable;
            Intrinsics.checkNotNull(iArr2);
            if (iArr2[i2] == colorInt) {
                i = i2;
                break;
            }
        }
        int i3 = i / 7;
        this.mPage = i3;
        this.mIndexInPage = i - (i3 * 7);
    }

    private final int[] getColorGroupTable() {
        int[] iArr = new int[13];
        for (int i = 0; i < 13; i++) {
            int[] iArr2 = this.pColorTable;
            Intrinsics.checkNotNull(iArr2);
            iArr[i] = iArr2[(i * 7) + 3];
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int[] getColorTableByGroup(int groupIndex) {
        int[] iArr = new int[7];
        int i = groupIndex * 7;
        for (int i2 = 0; i2 < 7; i2++) {
            int[] iArr2 = this.pColorTable;
            Intrinsics.checkNotNull(iArr2);
            iArr[i2] = iArr2[i + i2];
        }
        return iArr;
    }

    @Override // com.miui.doodle.feature.color.IPalette
    public void clearViewAnimator() {
        ViewPropertyAnimator viewPropertyAnimator = this.containerAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final String getColorName(int index, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        switch (index) {
            case 0:
                String string = context.getString(R.string.doodle_color_pick_red_talkback);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return string;
            case 1:
                String string2 = context.getString(R.string.doodle_color_pick_orange_red_talkback);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return string2;
            case 2:
                String string3 = context.getString(R.string.doodle_color_pick_orange_talkback);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                return string3;
            case 3:
                String string4 = context.getString(R.string.doodle_color_pick_orange_yellow_talkback);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                return string4;
            case 4:
                String string5 = context.getString(R.string.doodle_color_pick_yellow_talkback);
                Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
                return string5;
            case 5:
                String string6 = context.getString(R.string.doodle_color_pick_yellow_green_talkback);
                Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
                return string6;
            case 6:
                String string7 = context.getString(R.string.doodle_color_pick_green_talkback);
                Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
                return string7;
            case 7:
                String string8 = context.getString(R.string.doodle_color_pick_green_blue_talkback);
                Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
                return string8;
            case 8:
                String string9 = context.getString(R.string.doodle_color_pick_light_blue_talkback);
                Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
                return string9;
            case 9:
                String string10 = context.getString(R.string.doodle_color_pick_blue_talkback);
                Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
                return string10;
            case 10:
                String string11 = context.getString(R.string.doodle_color_pick_purple_talkback);
                Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
                return string11;
            case 11:
                String string12 = context.getString(R.string.doodle_color_pick_pink_talkback);
                Intrinsics.checkNotNullExpressionValue(string12, "getString(...)");
                return string12;
            case 12:
                String string13 = context.getString(R.string.doodle_color_pick_black_talkback);
                Intrinsics.checkNotNullExpressionValue(string13, "getString(...)");
                return string13;
            default:
                String string14 = context.getString(R.string.doodle_color_pick_unknown_talkback);
                Intrinsics.checkNotNullExpressionValue(string14, "getString(...)");
                return string14;
        }
    }
}
