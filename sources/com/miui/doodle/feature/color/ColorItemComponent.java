package com.miui.doodle.feature.color;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.support.cardview.CardView;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: ColorItemComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u0005H\u0016J\b\u0010\u001b\u001a\u00020\nH\u0016J\b\u0010\u001c\u001a\u00020\nH\u0016J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/miui/doodle/feature/color/ColorItemComponent;", "Lcom/miui/doodle/feature/color/IColorItem;", "view", "Landroid/view/View;", "mIndex", "", "colorSize", "colorInt", "itemClickListener", "Lkotlin/Function1;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;IIILkotlin/jvm/functions/Function1;)V", "getView", "()Landroid/view/View;", "itemBg", "Lcom/miui/support/cardview/CardView;", CloudPushConstants.XML_ITEM, "value", "getColorInt", "()I", "itemFolme", "Lmiuix/animation/IFolme;", "itemBgFolme", "isSelected", "", "setColor", SmartAction.Feature.SELECT, "unselect", "announceButtonSelected", "announceButtonUnselected", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorItemComponent implements IColorItem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<Integer> LIGHT_COLORS = CollectionsKt.listOf((Object[]) new Integer[]{-1, 16448250, 16119285});
    private int colorInt;
    private boolean isSelected;
    private final View item;
    private final CardView itemBg;
    private final IFolme itemBgFolme;
    private final Function1<Integer, Unit> itemClickListener;
    private final IFolme itemFolme;
    private final int mIndex;
    private final View view;

    /* JADX WARN: Multi-variable type inference failed */
    public ColorItemComponent(View view, int i, int i2, int i3, Function1<? super Integer, Unit> function1) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.view = view;
        this.mIndex = i;
        this.itemClickListener = function1;
        View viewFindViewById = getView().findViewById(R.id.color_item_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.item = viewFindViewById;
        viewFindViewById.setClipToOutline(true);
        viewFindViewById.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.doodle.feature.color.ColorItemComponent.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                Intrinsics.checkNotNullParameter(view2, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setOval(new Rect(0, 0, view2.getWidth(), view2.getHeight()));
            }
        });
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorItemComponent$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ColorItemComponent._init_$lambda$0(this.f$0, view2);
            }
        });
        View viewFindViewById2 = getView().findViewById(R.id.color_item_select_bg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        CardView cardView = (CardView) viewFindViewById2;
        this.itemBg = cardView;
        IFolme iFolmeUseAt = Folme.useAt(cardView);
        Intrinsics.checkNotNullExpressionValue(iFolmeUseAt, "useAt(...)");
        this.itemBgFolme = iFolmeUseAt;
        iFolmeUseAt.touch().setScale(0.8f, ITouchStyle.TouchType.DOWN).setScale(1.0f, ITouchStyle.TouchType.UP).setAlpha(0.0f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP);
        cardView.setAlpha(0.0f);
        iFolmeUseAt.touch().setTouchDown();
        IFolme iFolmeUseAt2 = Folme.useAt(viewFindViewById);
        Intrinsics.checkNotNullExpressionValue(iFolmeUseAt2, "useAt(...)");
        this.itemFolme = iFolmeUseAt2;
        iFolmeUseAt2.touch().setScale(0.8f, ITouchStyle.TouchType.DOWN);
        iFolmeUseAt2.touch().handleTouchOf(viewFindViewById, new AnimConfig[0]);
        viewFindViewById.setContentDescription("深度 " + (i2 - i));
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(viewFindViewById);
        setColor(i3);
    }

    @Override // com.miui.doodle.feature.color.IColorItem
    public View getView() {
        return this.view;
    }

    @Override // com.miui.doodle.feature.color.IColorItem
    public int getColorInt() {
        return this.colorInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(ColorItemComponent colorItemComponent, View view) {
        Function1<Integer, Unit> function1 = colorItemComponent.itemClickListener;
        if (function1 != null) {
            function1.invoke(Integer.valueOf(colorItemComponent.mIndex));
        }
    }

    @Override // com.miui.doodle.feature.color.IColorItem
    public void setColor(int colorInt) {
        this.colorInt = colorInt;
        if (LIGHT_COLORS.contains(Integer.valueOf(colorInt))) {
            this.item.setBackgroundResource(R.drawable.doodle_color_pick_panel_white);
        } else {
            this.item.setBackgroundColor(getColorInt());
        }
    }

    @Override // com.miui.doodle.feature.color.IColorItem
    public void select() {
        this.item.setElevation(6.0f);
        this.item.post(new Runnable() { // from class: com.miui.doodle.feature.color.ColorItemComponent$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ColorItemComponent.select$lambda$1(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void select$lambda$1(ColorItemComponent colorItemComponent) {
        colorItemComponent.itemBgFolme.touch().touchUp(new AnimConfig[0]);
        colorItemComponent.itemBg.setCardElevation(6.0f);
        if (colorItemComponent.isSelected) {
            return;
        }
        colorItemComponent.announceButtonSelected(colorItemComponent.item);
        colorItemComponent.isSelected = true;
    }

    @Override // com.miui.doodle.feature.color.IColorItem
    public void unselect() {
        this.itemBg.setCardElevation(0.0f);
        this.itemBgFolme.touch().touchDown(new AnimConfig[0]);
        this.item.setElevation(0.0f);
        if (this.isSelected) {
            announceButtonUnselected(this.item);
            this.isSelected = false;
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.item);
        }
    }

    private final void announceButtonSelected(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.doodle.feature.color.ColorItemComponent.announceButtonSelected.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setSelected(true);
            }
        });
    }

    private final void announceButtonUnselected(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.doodle.feature.color.ColorItemComponent.announceButtonUnselected.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setSelected(false);
            }
        });
    }

    /* JADX INFO: compiled from: ColorItemComponent.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/doodle/feature/color/ColorItemComponent$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "LIGHT_COLORS", "", "", "getLIGHT_COLORS", "()Ljava/util/List;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<Integer> getLIGHT_COLORS() {
            return ColorItemComponent.LIGHT_COLORS;
        }
    }
}
