package com.miui.doodle.feature.color;

import android.view.View;
import android.view.ViewGroup;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: ColorItemInPagePicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00122\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0002J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/doodle/feature/color/ColorItemInPagePicker;", "Lcom/miui/doodle/feature/color/IColorItemInPagePicker;", "pageContainer", "Landroid/view/ViewGroup;", "colorData", "", "indexInPage", "", "listener", "Lcom/miui/doodle/feature/color/ColorPickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/ViewGroup;[IILcom/miui/doodle/feature/color/ColorPickListener;)V", "colorItemsInPage", "Ljava/util/ArrayList;", "Lcom/miui/doodle/feature/color/IColorItem;", "lastColorInt", "pickListener", "initItems", "", "updateItems", "setData", "setColor", "selectData", "selectColor", "newIndexInPage", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorItemInPagePicker implements IColorItemInPagePicker {
    private int[] colorData;
    private final ArrayList<IColorItem> colorItemsInPage;
    private int indexInPage;
    private int lastColorInt;
    private final ViewGroup pageContainer;
    private final ColorPickListener pickListener;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(View view) {
    }

    public ColorItemInPagePicker(ViewGroup pageContainer, int[] colorData, int i, ColorPickListener colorPickListener) {
        Intrinsics.checkNotNullParameter(pageContainer, "pageContainer");
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        this.pageContainer = pageContainer;
        this.colorData = colorData;
        this.indexInPage = i;
        this.colorItemsInPage = new ArrayList<>();
        pageContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorItemInPagePicker$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorItemInPagePicker._init_$lambda$0(view);
            }
        });
        this.pickListener = colorPickListener;
        initItems();
    }

    private final void initItems() {
        int length = this.colorData.length;
        for (int i = 0; i < length; i++) {
            View viewFindViewById = this.pageContainer.findViewById(this.pageContainer.getContext().getResources().getIdentifier(CloudPushConstants.XML_ITEM + i, "id", this.pageContainer.getContext().getPackageName()));
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            int[] iArr = this.colorData;
            this.colorItemsInPage.add(new ColorItemComponent(viewFindViewById, i, iArr.length, iArr[i], new Function1() { // from class: com.miui.doodle.feature.color.ColorItemInPagePicker$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ColorItemInPagePicker.initItems$lambda$1(this.f$0, ((Integer) obj).intValue());
                }
            }));
        }
        this.colorItemsInPage.get(this.indexInPage).select();
        this.lastColorInt = this.colorItemsInPage.get(this.indexInPage).getColorInt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initItems$lambda$1(ColorItemInPagePicker colorItemInPagePicker, int i) {
        colorItemInPagePicker.selectColor(i);
        return Unit.INSTANCE;
    }

    private final void updateItems(int[] colorData) {
        Intrinsics.checkNotNull(colorData);
        int length = colorData.length;
        for (int i = 0; i < length; i++) {
            IColorItem iColorItem = this.colorItemsInPage.get(i);
            Intrinsics.checkNotNullExpressionValue(iColorItem, "get(...)");
            iColorItem.setColor(colorData[i]);
        }
        this.pageContainer.invalidate();
    }

    @Override // com.miui.doodle.feature.color.IColorItemInPagePicker
    public void setData(int[] colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        this.colorData = colorData;
        updateItems(colorData);
        setColor(this.indexInPage);
    }

    @Override // com.miui.doodle.feature.color.IColorItemInPagePicker
    public void setColor(int indexInPage) {
        int colorInt = this.colorItemsInPage.get(indexInPage).getColorInt();
        if (colorInt == this.lastColorInt) {
            return;
        }
        this.lastColorInt = colorInt;
        if (this.indexInPage != indexInPage) {
            this.colorItemsInPage.get(indexInPage).unselect();
            this.colorItemsInPage.get(indexInPage).select();
            this.indexInPage = indexInPage;
        }
    }

    @Override // com.miui.doodle.feature.color.IColorItemInPagePicker
    public void selectData(int[] colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        this.colorData = colorData;
        updateItems(colorData);
        selectColor(colorData[0] != -16777216 ? 3 : 0);
    }

    @Override // com.miui.doodle.feature.color.IColorItemInPagePicker
    public void selectColor(int newIndexInPage) {
        int colorInt = this.colorItemsInPage.get(newIndexInPage).getColorInt();
        if (colorInt == this.lastColorInt) {
            return;
        }
        this.lastColorInt = colorInt;
        int i = this.indexInPage;
        if (i != newIndexInPage) {
            this.colorItemsInPage.get(i).unselect();
            this.colorItemsInPage.get(newIndexInPage).select();
            this.indexInPage = newIndexInPage;
        }
        ColorPickListener colorPickListener = this.pickListener;
        if (colorPickListener != null) {
            colorPickListener.onColorPick(colorInt);
        }
    }
}
